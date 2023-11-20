package entity;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Blob;
import org.json.JSONException;
import org.json.JSONObject;
import com.intellijava.core.controller.RemoteImageModel;
import com.intellijava.core.model.input.ImageModelInput;
import okhttp3.*;

public class StoryBookFactory {


    public StoryBook create(String prompt, PageFactory pageFactory) {
        String entireText = generateText(prompt);

        int split = entireText.indexOf("Story:") + 6;

        String title = entireText.substring(6, split).strip();

        String storyText = entireText.substring(split).strip();

        List<String> pagesText = getPagesText(storyText);

        List<Page> pages = new ArrayList<>();

        for (int i = 0; i < pagesText.size(); i++) {
            String pageText = pagesText.get(i);
            Blob image = null;
            try {
                image = generateImage(pageText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pages.add(pageFactory.create(pageText, i + 1, image));
        }

        Page[] pageArray = new Page[pages.size()];
        pages.toArray(pageArray);

        return new StoryBook(title, pageArray);
    }

    private static List<String> getPagesText(String storyText) {
        int charLimit = 250;
        int storyChars = storyText.length();

        List<String> pagesText = new ArrayList<>();

        int i = 0;

        while (i < storyChars) {
            if (i + charLimit >= storyChars) {
                pagesText.add(storyText.substring(i));
            }
            else {
                int nextSentenceIndex = storyText.substring(i + charLimit).indexOf(".");
                pagesText.add(storyText.substring(i, i + charLimit + nextSentenceIndex));
            }

            i += charLimit;

        }
        return pagesText;
    }

    private static String generateText(String prompt) {
        String apiURL = "https://api.cohere.ai/v1/generate";
        String apiToken = System.getenv("cohere_key");

        String newPrompt = "Write the script for a storybook using the given prompt. Use the format of \nTitle:\nStory:\n\nHere is the prompt: " + prompt;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        String jsonBody = "{\n" +
                "  \"max_tokens\": 20,\n" +
                "  \"truncate\": \"END\",\n" +
                "  \"return_likelihoods\": \"NONE\",\n" +
                "  \"prompt\": \"" + newPrompt + "\"\n" +
                "}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Request request = new Request.Builder()
                .url(apiURL)
                .addHeader("authorization", "Bearer " + apiToken)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
            JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.getJSONArray("generations").getJSONObject(0).get("text").toString();

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }


    }

    private static Blob generateImage(String pageText) throws IOException {
        imageLink = generateImageLink(pageText);
//        TODO: convert image link to image
        ...
    }

    private static String generateImageLink(String pageText) throws IOException {
        String apiKey = System.getenv("openai_key");
        int imageNumber = 1;

        RemoteImageModel imageModel = new RemoteImageModel(apiKey, "openai");

        // prepare the input parameters
        ImageModelInput imageInput = new ImageModelInput.Builder(prompt)
                .setNumberOfImages(imageNumber).setImageSize("1024x1024").build();

        // call the model
        List<String> images = imageModel.generateImages(imageInput);

        return images.get(0);
    }
}
