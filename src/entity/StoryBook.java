package entity;

import java.util.ArrayList;

public class StoryBook {

    private String title;

    private ArrayList<Page> pages;


    public StoryBook(String title, ArrayList<Page> pages) {
        this.title = title;
        this.pages = pages;
    }

    public String getTitle() { return title; }

    public ArrayList<Page> getPages() { return pages; }

    public void setTitle(String newTitle) { title = newTitle; }

    public void setPages(ArrayList<Page> newPages) { pages = newPages; }

    public void addPage(Page newPage) { pages.add(newPage); }

}
