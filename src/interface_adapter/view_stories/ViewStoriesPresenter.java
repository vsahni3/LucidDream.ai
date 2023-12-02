package interface_adapter.view_stories;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginOutputData;
import use_case.view_stories.ViewStoriesOutputBoundary;
import use_case.view_stories.ViewStoriesOutputData;

/**
 * ViewStoriesPresenter is responsible for handling the output data provided by the ViewStoriesInteractor
 */
public class ViewStoriesPresenter implements ViewStoriesOutputBoundary {

    private final ViewStoriesViewModel viewStoriesViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Constructs a presenter for the view stories use case.
     * @param viewManagerModel
     * @param viewStoriesViewModel
     */
    public ViewStoriesPresenter(ViewManagerModel viewManagerModel,
                          ViewStoriesViewModel viewStoriesViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewStoriesViewModel = viewStoriesViewModel;
    }

    /**
     * Progresses to the story gallery view.
     * @param storybooks
     */
    @Override
    public void prepareSuccessView(ViewStoriesOutputData storybooks) {
        // on button click, switch to the view story gallery view

        ViewStoriesState viewStoriesState = viewStoriesViewModel.getState();
        viewStoriesState.setStoryBooks(storybooks.getStoryBooks());
        this.viewStoriesViewModel.setState(viewStoriesState);
        this.viewStoriesViewModel.firePropertyChanged();
        this.viewManagerModel.setActiveView(viewStoriesViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }



}
