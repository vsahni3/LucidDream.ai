package entity;

import java.util.List;
import java.util.ArrayList;
import java.sql.Blob;

public class StoryBookFactory {

    private final PageFactory pageFactory;

    public StoryBookFactory(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    /* TODO: change for loop to while loop, create prompt*/
    public StoryBook create(String prompt) {
        String storyText = generateText(prompt);

        int charLimit = 250;
        int storyChars = storyText.length();

        List<String> pagesText = new ArrayList<>();

        for (int i=0; i < storyChars; i += charLimit) {
            try {
                pagesText.add(storyText.substring(i, i + charLimit));
            }
            catch (StringIndexOutOfBoundsException e) {
                pagesText.add(storyText.substring(i));
            }
        }

        List<Page> pages = new ArrayList<>();

        for (int i = 0; i < pagesText.size(); i++) {
            String pageText = pagesText.get(i);
            Blob image = generateImage(pageText);
            pages.add(pageFactory.create(pageText, i + 1, image));
        }

        return new StoryBook(title, pages);
    }

    private String generateText(String prompt) {
        ...
    }

    private Blob generateImage(String pageText) {
        ...
    }
}
