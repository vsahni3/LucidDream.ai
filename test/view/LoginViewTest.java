package view;

import app.SignupUseCaseFactory;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.generate.GenerateInputBoundary;
import use_case.login.*;
import use_case.signup.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class LoginViewTest {

    @Test
    public void testLoginView() {

        LoginInputBoundary lib = null;
        LoginController loginController = new LoginController(lib);

        LoginViewModel loginViewModel = new LoginViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(loginView, loginView.viewName);

        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        app.setVisible(true);

        JPanel panel = (JPanel) loginView.getComponent(3);

        LabelTextPanel passwordLabelTextPanel = (LabelTextPanel) panel.getComponent(2);
        JPasswordField pwdField = (JPasswordField) passwordLabelTextPanel.getComponent(2);


        KeyEvent pwdEvent = new KeyEvent(
                pwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'y');


        panel.dispatchEvent(pwdEvent);


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        KeyEvent pwdEventRight = new KeyEvent(
                pwdField,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_RIGHT,
                KeyEvent.CHAR_UNDEFINED
        );


        panel.dispatchEvent(pwdEventRight);

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // type a second character
        KeyEvent pwdEvent2 = new KeyEvent(
                pwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'z');


        panel.dispatchEvent(pwdEvent2);

        // pause execution for 3 seconds
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // assert that the values are as expected.
        assertEquals("yz", new String(pwdField.getPassword()));
        assertEquals("yz", loginViewModel.getState().getPassword());

    }


    @Test
    public void testOpensLoggedInView() {

        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");
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


    @Test
    public void testOpenLandingView() {

        LoginViewModel loginViewModel = new LoginViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        LoginInputBoundary lib = null;
        LoginController loginController = new LoginController(lib);
        LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);

        LandingView landingView = new LandingView(new SignupViewModel(), loginViewModel, viewManagerModel);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(loginView, loginView.viewName);
        views.add(landingView, landingView.viewName);

        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();

        JPanel buttons = (JPanel) loginView.getComponent(5);
        JButton cancelButton = (JButton) buttons.getComponent(2);

        cancelButton.doClick();
        assertEquals(viewManagerModel.getActiveView(), "landing page");
        assertEquals(loginViewModel.getState().getUsername(), "");
        assertEquals(loginViewModel.getState().getPassword(), "");

    }
}