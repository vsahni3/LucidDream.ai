package view;

import app.SignupUseCaseFactory;
import data_access.InMemoryDAO;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_stories.ViewStoriesController;
import use_case.generate.GenerateInputBoundary;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.signup.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import use_case.view_stories.ViewStoriesInputBoundary;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class SignupViewTest {

    @Test
    public void testSignupView() {

        SignupInputBoundary sib = null;
        SignupController signupController = new SignupController(sib);

        SignupViewModel signupViewModel = new SignupViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SignupView signupView = new SignupView(signupController, signupViewModel, viewManagerModel);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(signupView, signupView.viewName);

        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        app.setVisible(true);

        JPanel panel = (JPanel) signupView.getComponent(3);

        LabelTextPanel passwordLabelTextPanel = (LabelTextPanel) panel.getComponent(2);
        JPasswordField pwdField = (JPasswordField) passwordLabelTextPanel.getComponent(2);

        LabelTextPanel repeatPasswordLabelTextPanel = (LabelTextPanel) panel.getComponent(4);
        JPasswordField repeatPwdField = (JPasswordField) repeatPasswordLabelTextPanel.getComponent(2);


        KeyEvent pwdEvent = new KeyEvent(
                pwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'y');


        KeyEvent rptPwdEvent = new KeyEvent(
                repeatPwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'y');


        panel.dispatchEvent(pwdEvent);
        panel.dispatchEvent(rptPwdEvent);


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


        KeyEvent rptPwdEventRight = new KeyEvent(
                repeatPwdField,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_RIGHT,
                KeyEvent.CHAR_UNDEFINED
        );

        panel.dispatchEvent(pwdEventRight);
        panel.dispatchEvent(rptPwdEventRight);

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

        // type a second character
        KeyEvent rptPwdEvent2 = new KeyEvent(
                repeatPwdField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'z');


        panel.dispatchEvent(pwdEvent2);
        panel.dispatchEvent(rptPwdEvent2);


        // pause execution for 3 seconds
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // assert that the values are as expected.
        assertEquals("yz", new String(pwdField.getPassword()));
        assertEquals("yz", signupViewModel.getState().getPassword());
        assertEquals("yz", new String(repeatPwdField.getPassword()));
        assertEquals("yz", signupViewModel.getState().getRepeatPassword());
    }


    @Test
    public void testOpensLoggedInView() {

        SignupUserDataAccessInterface userRepository = new InMemoryDAO();
        UserFactory factory = new CommonUserFactory();

        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        SignupOutputBoundary signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loggedInViewModel);

        SignupInputBoundary sib = new SignupInteractor(userRepository, signupPresenter, factory);
        SignupController signupController = new SignupController(sib);
        SignupView signupView = new SignupView(signupController, signupViewModel, viewManagerModel);


        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);


        ViewStoriesInputBoundary vib = null;
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel, viewManagerModel, generateStoryController, viewStoriesController);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(signupView, signupView.viewName);
        views.add(loggedInView, loggedInView.viewName);

        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        JPanel buttons = (JPanel) signupView.getComponent(5);
        JButton signupButton = (JButton) buttons.getComponent(0);

        SignupState signupState = signupViewModel.getState();
        signupState.setUsername("bob");

        signupButton.doClick();
        assertEquals(viewManagerModel.getActiveView(), "logged in");
        assertEquals(signupViewModel.getState().getUsername(), "");
        assertEquals(signupViewModel.getState().getPassword(), "");
        assertEquals(signupViewModel.getState().getRepeatPassword(), "");
        assertEquals(loggedInViewModel.getState().getUsername(), "bob");

    }


    @Test
    public void testOpenLandingView() {

        SignupViewModel signupViewModel = new SignupViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        SignupInputBoundary sib = null;
        SignupController signupController = new SignupController(sib);
        SignupView signupView = new SignupView(signupController, signupViewModel, viewManagerModel);

        LandingView landingView = new LandingView(signupViewModel, new LoginViewModel(), viewManagerModel);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(signupView, signupView.viewName);
        views.add(landingView, landingView.viewName);

        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();

        JPanel buttons = (JPanel) signupView.getComponent(5);
        JButton cancelButton = (JButton) buttons.getComponent(2);

        cancelButton.doClick();
        assertEquals(viewManagerModel.getActiveView(), "landing page");
        assertEquals(signupViewModel.getState().getUsername(), "");
        assertEquals(signupViewModel.getState().getPassword(), "");
        assertEquals(signupViewModel.getState().getRepeatPassword(), "");

    }
}