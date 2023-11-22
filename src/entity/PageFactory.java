package entity;

public class PageFactory {
    /**
     * Requires: password is valid.
     * @param textContents
     * @param pageNumber
     * @param pageID
     * @return
     */

    public Page create(String textContents, Integer pageNumber, byte[] image, Integer pageID) {
        return new Page(textContents, pageNumber, image, pageID);

    }
}
