package use_case.generate;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for GenerateInteractor.
 * It contains tests to ensure that the story generation functionality works as expected.
 */
class GenerateInteractorTest {

    /**
     * Tests the successful execution of the story generation use case.
     * This test ensures that a story is correctly generated given valid input data.
     */
    @Test
    void successTest() {
        // Setting up input data for story generation
        GenerateInputData inputData = new GenerateInputData("magical forest", "bob");

        // Setting up a user repository with an in-memory implementation
        GenerateUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Creating a user and saving it to the repository for the test
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");
        userRepository.save(user);

        // Setting up a mock presenter to handle the output of the story generation
        GenerateOutputBoundary successPresenter = new GenerateOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateOutputData story) {
                // Asserting that the generated story book is not null and has a title
                StoryBook storyBook = story.getStoryBook();
                assertNotNull(storyBook);
                assertNotEquals("", storyBook.getTitle());

                // Asserting that each page in the story book has text contents
                for (Page page : storyBook.getPages()) {
                    assertNotEquals("", page.getTextContents());
                }
            }
        };

        // Creating the interactor for the generate use case
        GenerateInputBoundary interactor = new GenerateInteractor(userRepository, successPresenter, new StoryBookFactory(), new PageFactory());

        // Executing the interactor with the given input data
        interactor.execute(inputData);
    }

}