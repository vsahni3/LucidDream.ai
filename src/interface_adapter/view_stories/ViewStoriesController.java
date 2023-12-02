package interface_adapter.view_stories;

import use_case.view_stories.ViewStoriesInputBoundary;
import use_case.view_stories.ViewStoriesInputData;

public class ViewStoriesController {

    final ViewStoriesInputBoundary viewStoriesUseCaseInteractor;

    public ViewStoriesController(ViewStoriesInputBoundary viewStoriesUseCaseInteractor) {
        this.viewStoriesUseCaseInteractor = viewStoriesUseCaseInteractor;
    }

    public void execute(String username) {
        ViewStoriesInputData viewStoriesInputData = new ViewStoriesInputData(username);

        viewStoriesUseCaseInteractor.execute(viewStoriesInputData);
    }
}
