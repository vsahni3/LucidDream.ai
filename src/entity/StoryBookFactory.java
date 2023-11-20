package entity;

import java.util.ArrayList;

public class StoryBookFactory {

    public StoryBook create(String title, ArrayList<Page> pages) {
        return new StoryBook(title, pages);
    }
}
