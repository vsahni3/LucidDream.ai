package app;

import data_access.*;
import entity.*;
import interface_adapter.downloadPDF.DownloadPDFViewModel;
import interface_adapter.download_story.DownloadController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.lookup.LookupController;
import interface_adapter.lookup.LookupViewModel;
import interface_adapter.narrate.NarrateController;
import interface_adapter.narrate.NarrateViewModel;
import interface_adapter.read_story.ReadStoryViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.view_stories.ViewStoriesViewModel;
import jdk.dynalink.linker.support.Lookup;
import use_case.generate.GenerateUserDataAccessInterface;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {



        // The main application window.
        JFrame application = new JFrame("Lucid Dream AI");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        ViewStoriesViewModel viewStoriesViewModel = new ViewStoriesViewModel();
        ReadStoryViewModel readStoryViewModel = new ReadStoryViewModel();
        NarrateViewModel narrateViewModel = new NarrateViewModel();
        LookupViewModel lookupViewModel = new LookupViewModel();
        DownloadPDFViewModel downloadPDFViewModel = new DownloadPDFViewModel();

        SqlUserDataAccessObject userDAO;
        CombinedDAO mainDAO;
        SqlPageDataAccessObject pageDAO;
        SqlBookDataAccessObject bookDAO;


        try {

            userDAO = new SqlUserDataAccessObject(new CommonUserFactory());
            bookDAO = new SqlBookDataAccessObject(new StoryBookFactory());
            pageDAO = new SqlPageDataAccessObject(new PageFactory());
            mainDAO = new CombinedDAO(userDAO, bookDAO, pageDAO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        LandingView landingView = new LandingView(signupViewModel, loginViewModel, viewManagerModel);
        views.add(landingView, landingView.viewName);

        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loggedInViewModel, signupViewModel, mainDAO);
        views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel, mainDAO);
        views.add(loginView, loginView.viewName);

        LoggedInView loggedInView = GenerateStoryUseCaseFactory.create(viewManagerModel, readStoryViewModel, loggedInViewModel, viewStoriesViewModel, mainDAO, mainDAO);
        views.add(loggedInView, loggedInView.viewName);


        ReadStoryView readStoryView = ReadStoryUseCaseFactory.create(viewManagerModel, readStoryViewModel, narrateViewModel, lookupViewModel, downloadPDFViewModel, mainDAO);
        views.add(readStoryView, readStoryView.viewName);


        ViewStoriesView viewStoriesView = ViewStoriesUseCaseFactory.create(viewManagerModel, viewStoriesViewModel, mainDAO);
        views.add(viewStoriesView, viewStoriesView.viewName);


        viewManagerModel.setActiveView(landingView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        // Disable window resizing
        application.setResizable(false);

        // Maximizes the screen
        application.setExtendedState(JFrame.MAXIMIZED_BOTH);

        application.setVisible(true);

    }
}