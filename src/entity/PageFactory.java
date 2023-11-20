package entity;

public class PageFactory {
    /**
     * Requires: password is valid.
     * @param textContents
     * @param pageNumber
     * @param image
     * @return
     */

    public Page create(String textContents, Integer pageNumber, byte[] image) {
        return new Page(textContents, pageNumber, image);
    }
}
