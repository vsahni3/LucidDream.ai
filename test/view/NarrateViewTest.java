package view;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.downloadPDF.DownloadPDFViewModel;
import interface_adapter.lookup.LookupViewModel;
import interface_adapter.narrate.NarrateController;
import interface_adapter.narrate.NarratePresenter;
import interface_adapter.narrate.NarrateViewModel;
import interface_adapter.read_story.ReadStoryState;
import interface_adapter.read_story.ReadStoryViewModel;
import org.junit.jupiter.api.Test;
import use_case.login.LoginUserDataAccessInterface;
import use_case.narrate.NarrateInputBoundary;
import use_case.narrate.NarrateInteractor;
import use_case.narrate.NarrateOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class NarrateViewTest {

    static boolean popUpDiscovered = false;

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



        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ReadStoryViewModel readStoryViewModel = new ReadStoryViewModel();
        NarrateViewModel narrateViewModel = new NarrateViewModel();


        NarrateOutputBoundary narratePresenter = new NarratePresenter(narrateViewModel);
        NarrateInputBoundary narrateInteractor = new NarrateInteractor(narratePresenter);
        NarrateController narrateController = new NarrateController(narrateInteractor);
        LookupViewModel lookupViewModel = new LookupViewModel();
        DownloadPDFViewModel downloadPDFViewModel = new DownloadPDFViewModel();

        ReadStoryView readStoryView = new ReadStoryView(viewManagerModel, readStoryViewModel, narrateViewModel, narrateController, null, null, lookupViewModel, downloadPDFViewModel);

        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        new ViewManager(views, cardLayout, viewManagerModel);

        views.add(readStoryView, readStoryView.viewName);

        ReadStoryState readStoryState = readStoryViewModel.getState();
        readStoryState.setPageTexts(new String[]{"abc", "def"});
        readStoryViewModel.setState(readStoryState);

        viewManagerModel.setActiveView(readStoryView.viewName);
        viewManagerModel.firePropertyChanged();

        app.pack();

        app.setVisible(true);

        JPanel buttons = (JPanel) readStoryView.getComponent(6);

        JButton narrateButton = (JButton) buttons.getComponent(2);

        createCloseTimer().start();

        narrateButton.doClick();

        assert(popUpDiscovered);



    }

    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {

                    if (window instanceof JDialog) {

                        JDialog dialog = (JDialog)window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
                            System.out.println("message = " + s);

                            // store the information we got from the JDialog
                            NarrateViewTest.popUpDiscovered = true;

                            System.out.println("disposing of..." + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }

        };

        Timer t = new Timer(3000, close);
        t.setRepeats(false);
        return t;
    }


}