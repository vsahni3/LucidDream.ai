package app;

import entity.PageFactory;
import entity.StoryBookFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.generate_story.GenerateStoryPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.read_story.ReadStoryViewModel;
import interface_adapter.view_stories.ViewStoriesController;
import interface_adapter.view_stories.ViewStoriesPresenter;
import interface_adapter.view_stories.ViewStoriesViewModel;
import use_case.generate.GenerateInputBoundary;
import use_case.generate.GenerateInteractor;
import use_case.generate.GenerateOutputBoundary;
import use_case.generate.GenerateUserDataAccessInterface;

import use_case.view_stories.ViewStoriesDataAccessInterface;
import use_case.view_stories.ViewStoriesInputBoundary;
import use_case.view_stories.ViewStoriesInteractor;
import use_case.view_stories.ViewStoriesOutputBoundary;
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
            ViewStoriesViewModel viewStoriesViewModel,
            GenerateUserDataAccessInterface userDataAccessObject,
            ViewStoriesDataAccessInterface storybookDataAccessObject) {

        try {
            GenerateStoryController generateStoryController = createGenerateStoryUseCase(viewManagerModel, readStoryViewModel, loggedInViewModel, userDataAccessObject);
            ViewStoriesController viewStoriesController = createViewStoriesUseCase(viewManagerModel, viewStoriesViewModel, storybookDataAccessObject);
            return new LoggedInView(loggedInViewModel, viewManagerModel, generateStoryController, viewStoriesController);
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
