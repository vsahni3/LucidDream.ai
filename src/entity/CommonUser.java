package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

class CommonUser implements User {

    private String userName;
    private String password;
    private ArrayList<StoryBook> storybooks;

    /**
     * Requires: password is valid.
     *
     * @param userName
     * @param password
     */
    public CommonUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.storybooks = new ArrayList<StoryBook>();
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public ArrayList<StoryBook> getStoryBooks() { return storybooks; }

    @Override
    public void setUserName(String newUserName) { userName = newUserName; }

    @Override
    public void setPassword(String newPassword) { password = newPassword; }

    @Override
    public void setStoryBooks(ArrayList<StoryBook> newStoryBooks) { storybooks = newStoryBooks; }

    @Override
    public void addStoryBook(StoryBook newStoryBook) { storybooks.add(newStoryBook); }

    @Override
    public void deleteStoryBook(StoryBook deletedStoryBook) {
        for (StoryBook book : storybooks) {
            if ( book == deletedStoryBook) { storybooks.remove(book); }
        }
    }
}
