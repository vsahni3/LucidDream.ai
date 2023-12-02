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

public class ReadStoryFactory {

    /** Prevent instantiation. */
    private ReadStoryFactory() {}

    public static LoggedInView create(
            ViewManagerModel viewManagerModel,
            ReadStoryViewModel readStoryViewModel,
            LoggedInViewModel loggedInViewModel,
            GenerateUserDataAccessInterface userDataAccessObject) {


        return null;
    }

}
