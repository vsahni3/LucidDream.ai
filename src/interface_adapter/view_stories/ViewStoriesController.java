package interface_adapter.view_stories;

import use_case.view_stories.ViewStoriesInputBoundary;
import use_case.view_stories.ViewStoriesInputData;

/**
 * Controller class for viewing stories. This class serves as an interface between the user interface
 * and the use case responsible for viewing stories.
 */
public class ViewStoriesController {

    final ViewStoriesInputBoundary viewStoriesUseCaseInteractor;

    /**
     * Constructs a new ViewStoriesController with the provided ViewStoriesInputBoundary.
     *
     * @param viewStoriesUseCaseInteractor The input boundary for the view stories use case.
     */
    public ViewStoriesController(ViewStoriesInputBoundary viewStoriesUseCaseInteractor) {
        this.viewStoriesUseCaseInteractor = viewStoriesUseCaseInteractor;
    }

    /**
     * Executes the view stories use case for the specified username.
     *
     * @param username The username for which stories should be viewed.
     */
    public void execute(String username) {
        ViewStoriesInputData viewStoriesInputData = new ViewStoriesInputData(username);

        viewStoriesUseCaseInteractor.execute(viewStoriesInputData);
    }
}
