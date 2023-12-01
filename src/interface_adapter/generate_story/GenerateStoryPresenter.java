package interface_adapter.generate_story;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.read_story.ReadStoryState;
import interface_adapter.read_story.ReadStoryViewModel;
import use_case.generate.GenerateOutputBoundary;
import use_case.generate.GenerateOutputData;

public class GenerateStoryPresenter implements GenerateOutputBoundary {

    private final GenerateStoryViewModel generateStoryViewModel;
    private final ReadStoryViewModel readStoryViewModel;
    private ViewManagerModel viewManagerModel;

    public GenerateStoryPresenter(ViewManagerModel viewManagerModel,
                          ReadStoryViewModel readStoryViewModel,
                          GenerateStoryViewModel generateStoryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.readStoryViewModel = readStoryViewModel;
        this.generateStoryViewModel = generateStoryViewModel;
    }

    @Override
    public void prepareSuccessView(GenerateOutputData book) {
        ReadStoryState readStoryState = readStoryViewModel.getState();
        readStoryState.setStoryBook(book.getStoryBook());
        this.readStoryViewModel.setState(readStoryState); // CHANGE THIS
        this.readStoryViewModel.firePropertyChanged();
        this.viewManagerModel.setActiveView(readStoryViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
