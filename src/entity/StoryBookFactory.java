package entity;

import java.util.List;
import java.util.ArrayList;
import java.sql.Blob;

public class StoryBookFactory {

    private final PageFactory pageFactory;

    public StoryBookFactory(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }


    public StoryBook create(String prompt) {
        String storyText = generateText(prompt);

        List<String> pagesText = getPagesText(storyText);

        List<Page> pages = new ArrayList<>();

        for (int i = 0; i < pagesText.size(); i++) {
            String pageText = pagesText.get(i);
            Blob image = generateImage(pageText);
            pages.add(pageFactory.create(pageText, i + 1, image));
        }

        return new StoryBook(title, pages);
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
        ...
    }

    private static Blob generateImage(String pageText) {
        ...
    }
}
