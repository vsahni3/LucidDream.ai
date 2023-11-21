package entity;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
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
     * @param prompt       The prompt used as the basis for generating the story text.
     * @param pageFactory  The factory used for creating individual pages of the storybook.
     * @return             A new StoryBook object containing the generated story and pages.
     */
    public StoryBook create(String prompt, PageFactory pageFactory) {
        String entireText = generateText(prompt);

        int split = entireText.indexOf("Story:") + 6;

        String title = entireText.substring(6, split).strip();

        String storyText = entireText.substring(split).strip();

        List<String> pagesText = getPagesText(storyText);

        List<Page> pages = new ArrayList<>();

        for (int i = 0; i < pagesText.size(); i++) {
            String pageText = pagesText.get(i);
            byte[] image;
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
