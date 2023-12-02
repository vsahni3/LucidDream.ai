package use_case.generate;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GenerateInteractorTest {

    @Test
    void successTest() {
        GenerateInputData inputData = new GenerateInputData("magical forest", "bob");
        GenerateUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");
        userRepository.save(user);

        GenerateOutputBoundary successPresenter = new GenerateOutputBoundary() {
            @Override
            public void prepareSuccessView(GenerateOutputData story) {
                StoryBook storyBook = story.getStoryBook();
                assertNotNull(storyBook);
                assertNotEquals("", storyBook.getTitle());
                for (Page page: storyBook.getPages()) {
                    assertNotEquals("", page.getTextContents());
                }
            }

        };

        GenerateInputBoundary interactor = new GenerateInteractor(userRepository, successPresenter, new StoryBookFactory(), new PageFactory());
        interactor.execute(inputData);
    }

}