package view;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import org.junit.jupiter.api.Test;
import use_case.generate.GenerateInputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NarrateViewTest {
    @Test
    public void testOpensNarrateDialog() {

        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");
        Page page = new Page("Hello World", 1, null, -1);
        ArrayList<Page> pages = new ArrayList<>();
        pages.add(page);

        StoryBook storyBook = new StoryBook("Test", pages);
        user.addStoryBook(storyBook);
        userRepository.save(user);


        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        LoginInputBoundary lib = new LoginInteractor(userRepository, loginPresenter);
        LoginController loginController = new LoginController(lib);
        LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);


        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel, viewManagerModel, generateStoryController);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(loginView, loginView.viewName);
        views.add(loggedInView, loggedInView.viewName);

        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();
        app.pack();

        LoginState loginState = loginViewModel.getState();
        loginState.setUsername("bob");
        loginState.setPassword("password");

        JPanel buttons = (JPanel) loginView.getComponent(5);
        JButton loginButton = (JButton) buttons.getComponent(0);

        loginButton.doClick();
        assertEquals(viewManagerModel.getActiveView(), "logged in");
        assertEquals(loginViewModel.getState().getUsername(), "");
        assertEquals(loginViewModel.getState().getPassword(), "");
        assertEquals(loggedInViewModel.getState().getUsername(), "bob");

    }


}