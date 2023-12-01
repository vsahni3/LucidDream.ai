package entity;

/**
 * PageFactory is a class that provides a method to create instances of the Page class.
 * It encapsulates the instantiation logic for creating Page objects with specified
 * attributes such as text contents, page number, image, and page ID.
 *
 * <p>The class follows the factory pattern, serving as a centralized point for creating
 * Page instances. It ensures that the creation of Page objects adheres to specific requirements,
 * such as valid input parameters.
 *
 * <p>Instances of PageFactory are used to simplify the process of creating Page objects
 * within a larger application context, promoting consistency and maintainability in the
 * instantiation of individual page entities.
 */

public class PageFactory {
    /**
     * Requires:  is valid.
     * @param textContents
     * @param pageNumber
     * @param pageID
     * @return
     */

    public Page create(String textContents, Integer pageNumber, byte[] image, Integer pageID) {
        return new Page(textContents, pageNumber, image, pageID);

    }
}
