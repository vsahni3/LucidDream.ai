package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.read_story.ReadStoryViewModel;
import interface_adapter.view_stories.ViewStoriesController;
import interface_adapter.view_stories.ViewStoriesPresenter;
import interface_adapter.view_stories.ViewStoriesViewModel;
import use_case.view_stories.ViewStoriesDataAccessInterface;
import use_case.view_stories.ViewStoriesInputBoundary;
import use_case.view_stories.ViewStoriesInteractor;
import use_case.view_stories.ViewStoriesOutputBoundary;
import view.ViewStoriesView;

public class ViewStoriesUseCaseFactory {

    /** Prevent instantiation. */
    private ViewStoriesUseCaseFactory() {}

    public static ViewStoriesView create(
            ViewManagerModel viewManagerModel,
            ViewStoriesViewModel viewStoriesViewModel,
            ReadStoryViewModel readStoryViewModel,
            ViewStoriesDataAccessInterface storybookDataAccessObject) {
        ViewStoriesController viewStoriesController = createViewStoriesUseCase(viewManagerModel, viewStoriesViewModel, storybookDataAccessObject);
        return new ViewStoriesView(viewStoriesViewModel, viewStoriesController, viewManagerModel, readStoryViewModel);
    }

    private static ViewStoriesController createViewStoriesUseCase(
            ViewManagerModel viewManagerModel,
            ViewStoriesViewModel viewStoriesViewModel,
            ViewStoriesDataAccessInterface storybookDataAccessObject) {

        ViewStoriesOutputBoundary viewStoriesOutputBoundary = new ViewStoriesPresenter(viewManagerModel, viewStoriesViewModel);

        ViewStoriesInputBoundary viewStoriesInteractor = new ViewStoriesInteractor(
                storybookDataAccessObject, viewStoriesOutputBoundary);

        return new ViewStoriesController(viewStoriesInteractor);
    }

}
