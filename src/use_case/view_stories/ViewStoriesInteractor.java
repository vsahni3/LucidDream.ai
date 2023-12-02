package use_case.view_stories;

import entity.PageFactory;
import entity.StoryBook;
import entity.StoryBookFactory;
import use_case.generate.GenerateOutputBoundary;
import use_case.generate.GenerateUserDataAccessInterface;

import java.util.ArrayList;

/**
 * ViewStoriesInteractor is responsible for handling the retrieval of user's storybooks based on the username.
 * It interacts with data access objects and a presenter to accomplish this task.
 */
public class ViewStoriesInteractor implements ViewStoriesInputBoundary{

    final ViewStoriesDataAccessInterface storybookDataAccessObject;
    final ViewStoriesOutputBoundary viewStoriesPresenter;

    /**
     * Constructs a new GenerateInteractor with the specified data access object, output boundary,
     * storybook factory, and page factory.
     *
     * @param storybookDataAccessObject Interface for storybook data access operations.
     * @param viewStoriesPresenter  Interface for accessing output boundaries.
     */
    public ViewStoriesInteractor(ViewStoriesDataAccessInterface storybookDataAccessObject,
                              ViewStoriesOutputBoundary viewStoriesPresenter) {
        this.storybookDataAccessObject = storybookDataAccessObject;
        this.viewStoriesPresenter = viewStoriesPresenter;
    }

    /**
     * Executes the storybook retrieval process based on the input username.
     * Retrieves the user's collection of storybooks, and prepares the output view.
     *
     * @param viewStoriesInputData Data required to generate a new storybook, including username and prompt.
     */
    @Override
    public void execute(ViewStoriesInputData viewStoriesInputData) {

        String username = viewStoriesInputData.getUsername();

        ArrayList<StoryBook> storyBooks = storybookDataAccessObject.getStoryBooks(username);

        System.out.println("View Stories Interactor called");

        ViewStoriesOutputData viewStoriesOutputData = new ViewStoriesOutputData(storyBooks);
        viewStoriesPresenter.prepareSuccessView(viewStoriesOutputData);
    }
}
