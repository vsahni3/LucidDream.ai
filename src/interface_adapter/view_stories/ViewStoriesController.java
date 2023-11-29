package interface_adapter.view_stories;

import use_case.view_stories.ViewStoriesInputBoundary;

public class ViewStoriesController {

    final ViewStoriesInputBoundary viewStoriesUseCaseInteractor;

    public ViewStoriesController(ViewStoriesInputBoundary viewStoriesUseCaseInteractor) {
        this.viewStoriesUseCaseInteractor = viewStoriesUseCaseInteractor;
    }

    public void execute() {

    }
}
