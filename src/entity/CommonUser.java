package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

class CommonUser implements User {

    private final String userName;
    private final String password;
    private final ArrayList<StoryBook> storybooks;

    /**
     * Requires: password is valid.
     *
     * @param userName
     * @param password
     * @param storybooks
     */
    public CommonUser(String userName, String password, ArrayList<StoryBook> storybooks) {
        this.userName = userName;
        this.password = password;
        this.storybooks = storybooks;
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
}
