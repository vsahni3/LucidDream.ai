package use_case.generate;

import entity.PageFactory;
import entity.StoryBook;
import entity.StoryBookFactory;
import entity.User;

import java.util.List;

/**
 * GenerateInteractor is responsible for handling the generation of new storybooks based on user inputs.
 * It interacts with data access objects, storybook and page factories, and a presenter to accomplish this task.
 */
public class GenerateInteractor implements GenerateInputBoundary {
    final GenerateUserDataAccessInterface userDataAccessObject;
    final GenerateOutputBoundary generatePresenter;
    final StoryBookFactory storyBookFactory;

    final PageFactory pageFactory;

    /**
     * Constructs a new GenerateInteractor with the specified data access object, output boundary,
     * storybook factory, and page factory.
     *
     * @param userDataAccessInterface Interface for user data access operations.
     * @param generateOutputBoundary  Interface for accessing output boundaries.
     * @param storyBookFactory        Factory for creating new storybooks.
     * @param pageFactory             Factory for creating new pages.
     */
    public GenerateInteractor(GenerateUserDataAccessInterface userDataAccessInterface,
                              GenerateOutputBoundary generateOutputBoundary, StoryBookFactory storyBookFactory,
                              PageFactory pageFactory) {
        this.userDataAccessObject = userDataAccessInterface;
        this.generatePresenter = generateOutputBoundary;
        this.storyBookFactory = storyBookFactory;
        this.pageFactory = pageFactory;
    }

    /**
     * Executes the storybook generation process based on the input data.
     * Retrieves the user, generates a storybook, updates user stories, and prepares the output view.
     *
     * @param generateInputData Data required to generate a new storybook, including username and prompt.
     */
    @Override
    public void execute(GenerateInputData generateInputData) {


        String username = generateInputData.getUsername();
        String prompt = generateInputData.getPrompt();


        User user = userDataAccessObject.getUser(username);
        StoryBook outputStoryBook = storyBookFactory.create(prompt, pageFactory);
        List<StoryBook> userStories = user.getStories();

        userStories.add(outputStoryBook);
        userDataAccessObject.save(user);

        GenerateOutputData generateOutputData = new GenerateOutputData(outputStoryBook);
        generatePresenter.prepareSuccessView(generateOutputData);


    }
}
