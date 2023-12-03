package use_case.lookup;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;


import java.io.IOException;

import java.util.concurrent.TimeUnit;

/**
 * The LookupInteractor class implements the LookupInputBoundary interface, handling the logic for word lookup operations.
 * This class is responsible for communicating with external dictionary API services to fetch the definition of a given word.
 * It encapsulates the necessary network operations and JSON parsing to retrieve and process the word data.
 *
 * Key functionalities include:
 * - Executing the word lookup process using data provided by LookupInputData.
 * - Communicating with the external dictionary API.
 * - Parsing the response and extracting the required information.
 * - Handling network and parsing exceptions.
 * - Providing the lookup results to the LookupOutputBoundary for further processing or display.
 *
 * This class works in conjunction with a LookupOutputBoundary (lookupPresenter) to deliver the results of the lookup operation.
 */
public class LookupInteractor implements LookupInputBoundary {

    private final LookupOutputBoundary lookupPresenter;


    /**
     * Constructs a new LookupInteractor with the specified LookupOutputBoundary.
     * @param lookupPresenter the presenter to handle the output of the lookup process.
     */
    public LookupInteractor(LookupOutputBoundary lookupPresenter) {
        this.lookupPresenter = lookupPresenter;
    }


    /**
     * Executes the word lookup process for the given word.
     * This method uses the OkHttpClient to send a request to the dictionary API and parses the response.
     * The result is then passed to the LookupOutputBoundary.
     *
     * @param lookupInputData The LookupInputData object containing the word to be looked up.
     */
    @Override
    public void execute(LookupInputData lookupInputData) {


        String word = lookupInputData.getWord();



        String apiURL = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;

        Request request = new Request.Builder()
                .url(apiURL)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response);
                return;
            }


            JSONArray jsonArray = new JSONArray(response.body().string());

            String definition = jsonArray.getJSONObject(0).getJSONArray("meanings").getJSONObject(0).getJSONArray("definitions").getJSONObject(0).getString("definition");
            LookupOutputData lookupOutputData = new LookupOutputData(definition);
            lookupPresenter.prepareSuccessView(lookupOutputData);
        } catch (IOException e) {
            System.err.println("Error during request: " + e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
}
