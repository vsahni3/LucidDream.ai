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

/**
 * Factory class for creating the ViewStoriesView with its associated use cases.
 * This class encapsulates the creation and wiring of the components needed for the view story gallery use case,
 * including scrolling through the storybook collection, selecting a story to read, and logging back out.
 */
public class ViewStoriesUseCaseFactory {

    /** Prevent instantiation. */
    private ViewStoriesUseCaseFactory() {}

    /**
     * Creates and returns a ReadStoryView with all necessary dependencies and controllers.
     *
     * @param viewManagerModel The model for managing different views.
     * @param readStoryViewModel The view model for reading stories.
     * @param viewStoriesViewModel The view model for viewing stories.
     * @param storybookDataAccessObject The data access object for storybooks.
     * @return ViewStoriesView with all dependencies set up.
     */
    public static ViewStoriesView create(
            ViewManagerModel viewManagerModel,
            ViewStoriesViewModel viewStoriesViewModel,
            ReadStoryViewModel readStoryViewModel,
            ViewStoriesDataAccessInterface storybookDataAccessObject) {
        ViewStoriesController viewStoriesController = createViewStoriesUseCase(viewManagerModel, viewStoriesViewModel, storybookDataAccessObject);
        return new ViewStoriesView(viewStoriesViewModel, viewStoriesController, viewManagerModel, readStoryViewModel);
    }

    /**
     * Creates a DownloadPDFController for handling PDF download use cases.
     *
     * @param viewManagerModel The view model for managing views.
     * @param viewStoriesViewModel The view model for viewing stories.
     * @param storybookDataAccessObject The data access object for storybooks.
     * @return DownloadPDFController configured with the necessary interactor and presenter.
     */
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
