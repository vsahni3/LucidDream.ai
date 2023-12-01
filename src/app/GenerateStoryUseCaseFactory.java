package app;

import entity.PageFactory;
import entity.StoryBookFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.generate_story.GenerateStoryPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.read_story.ReadStoryViewModel;
import use_case.generate.GenerateInputBoundary;
import use_case.generate.GenerateInteractor;
import use_case.generate.GenerateOutputBoundary;
import use_case.generate.GenerateUserDataAccessInterface;

import view.LoggedInView;

import javax.swing.*;
import java.io.IOException;

public class GenerateStoryUseCaseFactory {

    /** Prevent instantiation. */
    private GenerateStoryUseCaseFactory() {}

    public static LoggedInView create(
            ViewManagerModel viewManagerModel,
            ReadStoryViewModel readStoryViewModel,
            LoggedInViewModel loggedInViewModel,
            GenerateUserDataAccessInterface userDataAccessObject) {

        try {
            GenerateStoryController generateStoryController = createGenerateStoryUseCase(viewManagerModel, readStoryViewModel, loggedInViewModel, userDataAccessObject);
            return new LoggedInView(loggedInViewModel, viewManagerModel, generateStoryController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static GenerateStoryController createGenerateStoryUseCase(
            ViewManagerModel viewManagerModel,
            ReadStoryViewModel readStoryViewModel,
            LoggedInViewModel loggedInViewModel,
            GenerateUserDataAccessInterface userDataAccessObject) throws IOException {

        GenerateOutputBoundary generateOutputBoundary = new GenerateStoryPresenter(viewManagerModel, readStoryViewModel, loggedInViewModel);

        StoryBookFactory storyBookFactory = new StoryBookFactory();
        PageFactory pageFactory = new PageFactory();

        GenerateInputBoundary generateInteractor = new GenerateInteractor(
                userDataAccessObject, generateOutputBoundary, storyBookFactory, pageFactory);

        return new GenerateStoryController(generateInteractor);
    }
}
