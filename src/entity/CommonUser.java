package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

class CommonUser implements User {

    private String userName;
    private String password;
    private ArrayList<StoryBook> storybooks;

    /**
     * Constructs a new CommonUser entity
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

    /**
     * Returns user's Username
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * Returns user's password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns all storybooks created by user
     */
    @Override
    public ArrayList<StoryBook> getStoryBooks() { return storybooks; }

    /**
     * Set a new username for a user
     */
    @Override
    public void setUserName(String newUserName) { userName = newUserName; }

    /**
     * Set a new password for a user
     */
    @Override
    public void setPassword(String newPassword) { password = newPassword; }

    /**
     * Set a new collection of Storybooks for a user
     */
    @Override
    public void setStoryBooks(ArrayList<StoryBook> newStoryBooks) { storybooks = newStoryBooks; }

    /**
     * Add a newly generated storybook to a user's collection
     */
    @Override
    public void addStoryBook(StoryBook newStoryBook) { storybooks.add(newStoryBook); }

    /**
     * Delete a storybook from a user's collection
     */
    @Override
    public void deleteStoryBook(StoryBook deletedStoryBook) {
        for (StoryBook book : storybooks) {
            if ( book == deletedStoryBook) { storybooks.remove(book); }
        }
    }
}
