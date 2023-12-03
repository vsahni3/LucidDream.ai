package entity;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.intellijava.core.controller.RemoteImageModel;
import com.intellijava.core.model.input.ImageModelInput;
import okhttp3.*;

/**
 * StoryBookFactory is responsible for creating StoryBook objects.
 * It uses input prompts and a PageFactory to generate and assemble the pages of a StoryBook.
 */
public class StoryBookFactory {

    public StoryBook create(String title, ArrayList<Page> pages) {
        return new StoryBook(title, pages);
    }

    /**
     * Creates a StoryBook based on the given prompt and using the specified PageFactory.
     * It generates text for the story, splits it into pages, and then uses the PageFactory to create each page.
     * It also handles image generation for each page of the storybook.
     *
     * @param prompt      The prompt used as the basis for generating the story text.
     * @param pageFactory The factory used for creating individual pages of the storybook.
     * @return A new StoryBook object containing the generated story and pages.
     */
    public StoryBook create(String prompt, PageFactory pageFactory) {
        String entireText;
        try {
            entireText = generateText(prompt);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
//        Should be in the form Title: title \n Story: story..
        int split = entireText.indexOf("Story:") + 6;
//        Exclude the 'Title:' and 'Story:' strings
        String title = entireText.substring(6, split - 6).strip();

        // Removes any unncecessary quotes
        title = (title != null && title.startsWith("\"") && title.endsWith("\"")) ?
                title.substring(1, title.length() - 1) : title;

        String storyText = entireText.substring(split).strip();

        List<String> pagesText = getPagesText(storyText);

        ArrayList<Page> pages = new ArrayList<>();

        for (int i = 0; i < pagesText.size(); i++) {
            String pageText = pagesText.get(i);
            byte[] image;
            try {
                image = generateImage(pageText);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            pages.add(pageFactory.create(pageText, i + 1, image, -1));
        }


        return new StoryBook(title, pages);
    }

    /**
     * Splits a given story text into pages, each with a character limit.
     * The method breaks the story text into smaller parts, ensuring each part ends with a complete sentence.
     *
     * @param storyText The complete story text to be split into pages.
     * @return A List of strings, each representing the text for a single page.
     */
    private static List<String> getPagesText(String storyText) {
        int charLimit = 250;
        int storyChars = storyText.length();

        List<String> pagesText = new ArrayList<>();

        int i = 0;

        while (i < storyChars) {
            int nextSentenceIndex = 0;

            if (i + charLimit >= storyChars) {
                pagesText.add(storyText.substring(i).strip());
            } else {
                nextSentenceIndex = storyText.substring(i + charLimit).indexOf(".");
                // add 1 to include the period
                pagesText.add(storyText.substring(i, i + charLimit + nextSentenceIndex + 1).strip());
            }

            i += charLimit + nextSentenceIndex + 1;

        }
        return pagesText;
    }

    /**
     * Generates story text based on a given prompt using an AI model.
     * This method sends a request to an AI service to generate story text based on the provided prompt.
     *
     * @param prompt The prompt to be used for generating the story.
     * @return The generated story text.
     * @throws JSONException If there is an error in parsing the JSON response.
     * @throws IOException If there is an IO error during the HTTP request.
     */
    private static String generateText(String prompt) throws JSONException, IOException {
        String apiURL = "https://api.openai.com/v1/chat/completions";
        String apiToken = System.getenv("openai_key");

        String newPrompt = "Write the script for a storybook using the prompt:" + prompt + "\n\nUse the format of \nTitle:\nStory:\n\nDo not include page numbers;";

        JSONObject jsonBodyObj = new JSONObject();
        jsonBodyObj.put("model", "gpt-4");

        JSONArray messages = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a storybook generator.");
        messages.put(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", newPrompt);
        messages.put(userMessage);

        jsonBodyObj.put("messages", messages);
        String jsonBody = jsonBodyObj.toString();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Request request = new Request.Builder()
                .url(apiURL)
                .addHeader("authorization", "Bearer " + apiToken)
                .addHeader("content-type", "application/json")
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS) // Increase connect timeout
                .readTimeout(60, TimeUnit.SECONDS)    // Increase read timeout
                .writeTimeout(60, TimeUnit.SECONDS)   // Increase write timeout
                .build();

        Response response = client.newCall(request).execute();

        JSONObject responseObject = new JSONObject(response.body().string());

        JSONArray completions = responseObject.getJSONArray("choices");

        return completions.getJSONObject(0).getJSONObject("message").getString("content");
    }

    /**
     * Generates an image based on the provided page text.
     * This method sends a request to an image generation service to create an image relevant to the page text.
     *
     * @param pageText The text for which the image needs to be generated.
     * @return A byte array representing the generated image.
     * @throws IOException If there is an IO error during the image retrieval.
     */
    private static byte[] generateImage(String pageText) throws IOException {
        String imageLink = generateImageLink(pageText);
        URL url = new URL(imageLink);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (InputStream in = url.openStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }

        return baos.toByteArray();
    }

    /**
     * Generates a link to an image based on the provided page text.
     * This method communicates with an image generation service to get a link for an image corresponding to the page text.
     *
     * @param pageText The text for which the image link needs to be generated.
     * @return The link to the generated image.
     * @throws IOException If there is an IO error during the image link generation.
     */
    private static String generateImageLink(String pageText) throws IOException {
        String apiKey = System.getenv("openai_key");
        int imageNumber = 1;

        RemoteImageModel imageModel = new RemoteImageModel(apiKey, "openai");

        // prepare the input parameters
        ImageModelInput imageInput = new ImageModelInput.Builder(pageText)
                .setNumberOfImages(imageNumber).setImageSize("1024x1024").build();

        // call the model
        List<String> images = imageModel.generateImages(imageInput);

        return images.get(0);

    }
}
