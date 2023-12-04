package view;

import app.LoginUseCaseFactory;
import app.SignupUseCaseFactory;
import data_access.InMemoryDAO;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import org.junit.jupiter.api.Test;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;


class LandingViewTest {

    @Test
    public void testLandingViewDisplays() {

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        new ViewManager(views, cardLayout, viewManagerModel);
        LandingView landingView = new LandingView(signupViewModel, loginViewModel, viewManagerModel);

        views.add(landingView, landingView.viewName);
        viewManagerModel.setActiveView(landingView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        app.setVisible(true);
        assertEquals(viewManagerModel.getActiveView(), "landing page");
    };


    public JButton getLoginButton() {
        JFrame app = null;

        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        assertNotNull(app); // found the window?
        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        LandingView sv = (LandingView) jp2.getComponent(0);
        JPanel buttons = (JPanel) sv.getComponent(4);
        return (JButton) buttons.getComponent(0);
    }

    public JButton getSignupButton() {
        JFrame app = null;

        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        assertNotNull(app); // found the window?
        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        LandingView sv = (LandingView) jp2.getComponent(0);
        JPanel buttons = (JPanel) sv.getComponent(4);
        return (JButton) buttons.getComponent(2);
    }


    @Test
    public void testLoginButtonPresent() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LandingView landingView = new LandingView(signupViewModel, loginViewModel, viewManagerModel);
        views.add(landingView, landingView.viewName);

        app.pack();
        app.setVisible(true);

        JButton button = getLoginButton();
        assert(button.getText().equals("Login"));
    }


    @Test
    public void testSignupButtonPresent() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LandingView landingView = new LandingView(signupViewModel, loginViewModel, viewManagerModel);
        views.add(landingView, landingView.viewName);

        app.pack();
        app.setVisible(true);

        JButton button = getSignupButton();
        assert(button.getText().equals("Sign Up"));
    }

    @Test
    public void testOpensLoginView() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        LoginUserDataAccessInterface loginUserDataAccessInterface = new InMemoryDAO();
        new ViewManager(views, cardLayout, viewManagerModel);

        LandingView landingView = new LandingView(signupViewModel, loginViewModel, viewManagerModel);
        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel, loginUserDataAccessInterface);

        views.add(landingView, landingView.viewName);
        views.add(loginView, loginView.viewName);

        viewManagerModel.setActiveView(landingView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        app.setVisible(true);
        JButton button = getLoginButton();
        button.doClick();

        assertEquals(viewManagerModel.getActiveView(), "log in");

    }

    @Test
    public void testOpensSignupView() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupUserDataAccessInterface signupUserDataAccessInterface = new InMemoryDAO();
        new ViewManager(views, cardLayout, viewManagerModel);

        LandingView landingView = new LandingView(signupViewModel, loginViewModel, viewManagerModel);
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loggedInViewModel, signupViewModel,signupUserDataAccessInterface);

        views.add(landingView, landingView.viewName);
        views.add(signupView, signupView.viewName);

        viewManagerModel.setActiveView(landingView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        app.setVisible(true);
        JButton button = getSignupButton();
        button.doClick();

        assertEquals(viewManagerModel.getActiveView(), "sign up");

    }
}







