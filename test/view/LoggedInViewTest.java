package view;

import data_access.InMemoryDAO;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.read_story.ReadStoryViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_stories.ViewStoriesController;
import interface_adapter.view_stories.ViewStoriesPresenter;
import interface_adapter.view_stories.ViewStoriesViewModel;
import org.junit.jupiter.api.Test;
import use_case.generate.GenerateInputBoundary;
import use_case.view_stories.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class LoggedInViewTest {
    @Test
    public void testLoggedInViewDisplays() {

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);
        ViewStoriesInputBoundary vib = null;
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);

        new ViewManager(views, cardLayout, viewManagerModel);
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel,viewManagerModel, generateStoryController, viewStoriesController);

        views.add(loggedInView, loggedInView.viewName);
        viewManagerModel.setActiveView(loggedInView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        assertEquals(viewManagerModel.getActiveView(), "logged in");
    };

    public JButton getViewStoriesButton() {
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
        LoggedInView sv = (LoggedInView) jp2.getComponent(0);
        JPanel buttons = (JPanel) sv.getComponent(4);
        return (JButton) buttons.getComponent(0);
    }

    public JButton getLogOutButton() {
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
        LoggedInView sv = (LoggedInView) jp2.getComponent(0);
        JPanel buttons = (JPanel) sv.getComponent(4);
        return (JButton) buttons.getComponent(1);
    }

    @Test
    public void testViewStoriesButtonPresent() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);
        ViewStoriesInputBoundary vib = null;
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);

        new ViewManager(views, cardLayout, viewManagerModel);
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel,viewManagerModel, generateStoryController, viewStoriesController);

        views.add(loggedInView, loggedInView.viewName);
        viewManagerModel.setActiveView(loggedInView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();

        JButton button = getViewStoriesButton();
        assert(button.getText().equals("MY STORIES"));
    }

    @Test
    public void testLogOutButtonPresent() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);
        ViewStoriesInputBoundary vib = null;
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);

        new ViewManager(views, cardLayout, viewManagerModel);
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel,viewManagerModel, generateStoryController, viewStoriesController);

        views.add(loggedInView, loggedInView.viewName);
        viewManagerModel.setActiveView(loggedInView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();

        JButton button = getLogOutButton();
        assert(button.getText().equals("LOG OUT"));
    }

    public JButton getLogOutButton2() {
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
        return (JButton) buttons.getComponent(1);
    }

    @Test
    public void testOpensViewStoriesView() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);

        ViewStoriesDataAccessInterface userRepo = new InMemoryDAO();

        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");
        ((InMemoryDAO) userRepo).save(user);

        ViewStoriesViewModel viewStoriesViewModel = new ViewStoriesViewModel();
        ReadStoryViewModel readStoryViewModel = new ReadStoryViewModel();

        ViewStoriesOutputBoundary viewStoriesPresenter = new ViewStoriesPresenter(viewManagerModel, viewStoriesViewModel);
        ViewStoriesInputBoundary vib = new ViewStoriesInteractor(userRepo, viewStoriesPresenter);
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);

        loggedInViewModel.getState().setUsername("bob");

        new ViewManager(views, cardLayout, viewManagerModel);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel,viewManagerModel, generateStoryController, viewStoriesController);
        ViewStoriesView viewStoriesView = new ViewStoriesView(viewStoriesViewModel, viewStoriesController, viewManagerModel, readStoryViewModel);

        views.add(loggedInView, loggedInView.viewName);
        views.add(viewStoriesView, viewStoriesView.viewName);

        viewManagerModel.setActiveView(loggedInView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        JPanel buttons = (JPanel) loggedInView.getComponent(4);
        JButton viewStoriesButton = (JButton) buttons.getComponent(0);
        viewStoriesButton.doClick();

        assertEquals(viewManagerModel.getActiveView(), "view stories");
    }

    @Test
    public void testOpensLandingPageView() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);
        ViewStoriesInputBoundary vib = null;
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);
        ViewStoriesViewModel viewStoriesViewModel = new ViewStoriesViewModel();
        ReadStoryViewModel readStoryViewModel = new ReadStoryViewModel();

        new ViewManager(views, cardLayout, viewManagerModel);

        LandingView landingView = new LandingView(new SignupViewModel(), loginViewModel, viewManagerModel);
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel,viewManagerModel, generateStoryController, viewStoriesController);
        ViewStoriesView viewStoriesView = new ViewStoriesView(viewStoriesViewModel, viewStoriesController, viewManagerModel, readStoryViewModel);

        views.add(landingView, landingView.viewName);
        views.add(loggedInView, loggedInView.viewName);
        views.add(viewStoriesView, viewStoriesView.viewName);

        viewManagerModel.setActiveView(loggedInView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();

        JPanel buttons = (JPanel) loggedInView.getComponent(4);
        JButton logOutButton = (JButton) buttons.getComponent(1);
        logOutButton.doClick();

        assertEquals(viewManagerModel.getActiveView(), "landing page");
    }

    @Test
    public void testLoggedInPromptInput() {
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ReadStoryViewModel readStoryViewModel = new ReadStoryViewModel();
        GenerateInputBoundary gib = null;
        GenerateStoryController generateStoryController = new GenerateStoryController(gib);
        ViewStoriesInputBoundary vib = null;
        ViewStoriesController viewStoriesController = new ViewStoriesController(vib);
        ViewStoriesViewModel viewStoriesViewModel = new ViewStoriesViewModel();

        new ViewManager(views, cardLayout, viewManagerModel);

        LandingView landingView = new LandingView(new SignupViewModel(), loginViewModel, viewManagerModel);
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel,viewManagerModel, generateStoryController, viewStoriesController);
        ViewStoriesView viewStoriesView = new ViewStoriesView(viewStoriesViewModel, viewStoriesController, viewManagerModel, readStoryViewModel);

        views.add(landingView, landingView.viewName);
        views.add(loggedInView, loggedInView.viewName);
        views.add(viewStoriesView, viewStoriesView.viewName);

        viewManagerModel.setActiveView(loggedInView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();
        app.setVisible(true);

        JPanel createPanel = (JPanel) loggedInView.getComponent(3);
        LabelTextPanel storyPrompt = (LabelTextPanel) createPanel.getComponent(0);
        // I have no idea why this is n:2 instead of 1 but it is
        JTextField promptInputField = (JTextField) storyPrompt.getComponent(2);

        KeyEvent pwdEvent = new KeyEvent(
                promptInputField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'y');


        createPanel.dispatchEvent(pwdEvent);


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        KeyEvent pwdEventRight = new KeyEvent(
                promptInputField,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_RIGHT,
                KeyEvent.CHAR_UNDEFINED
        );


        createPanel.dispatchEvent(pwdEventRight);

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // type a second character
        KeyEvent pwdEvent2 = new KeyEvent(
                promptInputField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'z');


        createPanel.dispatchEvent(pwdEvent2);

        // pause execution for 3 seconds
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // assert that the values are as expected.
        assertEquals("yz", loggedInViewModel.getState().getStoryPrompt());

    }
}