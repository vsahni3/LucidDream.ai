/**
 * This class contains tests for the NarrateView functionality.
 * It is designed to ensure that the narrate dialog opens correctly
 * and interacts as expected within the user interface.
 */

package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.downloadPDF.DownloadPDFViewModel;
import interface_adapter.lookup.LookupViewModel;
import interface_adapter.narrate.NarrateController;
import interface_adapter.narrate.NarratePresenter;
import interface_adapter.narrate.NarrateViewModel;
import interface_adapter.read_story.ReadStoryState;
import interface_adapter.read_story.ReadStoryViewModel;
import org.junit.jupiter.api.Test;
import use_case.narrate.NarrateInputBoundary;
import use_case.narrate.NarrateInteractor;
import use_case.narrate.NarrateOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Test class for testing the functionality of NarrateView

/**
 * Tests if the Narrate dialog opens correctly upon interaction.
 * The test sets up a simulated environment with necessary view models and controllers.
 * It then simulates a user interaction to open the Narrate dialog and checks
 * if the dialog is correctly opened and discovered.
 */
class NarrateViewTest {

    // Static variable to track if a pop-up dialog is discovered
    static boolean popUpDiscovered = false;

    // Test method to verify the opening of the narrate dialog
    @Test
    public void testOpensNarrateDialog() {
        // Initialize view models for the components of the MVC architecture
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ReadStoryViewModel readStoryViewModel = new ReadStoryViewModel();
        NarrateViewModel narrateViewModel = new NarrateViewModel();
        LookupViewModel lookupViewModel = new LookupViewModel();
        DownloadPDFViewModel downloadPDFViewModel = new DownloadPDFViewModel();


        // Set up the Narrate feature with MVC components
        NarrateOutputBoundary narratePresenter = new NarratePresenter(narrateViewModel);
        NarrateInputBoundary narrateInteractor = new NarrateInteractor(narratePresenter);
        NarrateController narrateController = new NarrateController(narrateInteractor);


        // Create the ReadStoryView which is to be tested

        ReadStoryView readStoryView = new ReadStoryView(viewManagerModel, readStoryViewModel, narrateViewModel, narrateController, null, null, lookupViewModel, downloadPDFViewModel);

        // Set up the main application frame for testing
        JFrame app = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        app.add(views);

        // Manage different views in the application
        new ViewManager(views, cardLayout, viewManagerModel);

        // Add the readStoryView to the application
        views.add(readStoryView, readStoryView.viewName);

        // Set the state of the ReadStoryViewModel for testing
        ReadStoryState readStoryState = readStoryViewModel.getState();
        readStoryState.setPageTexts(new String[]{"abc", "def"});
        readStoryViewModel.setState(readStoryState);

        // Activate the readStoryView and trigger property change listeners
        viewManagerModel.setActiveView(readStoryView.viewName);
        viewManagerModel.firePropertyChanged();

        // Prepare and display the application window
        app.pack();
        app.setVisible(true);

        // Access the buttons panel from the readStoryView
        JPanel buttons = (JPanel) readStoryView.getComponent(6);

        // Retrieve the narrate button for simulation
        JButton narrateButton = (JButton) buttons.getComponent(2);

        // Start a timer that closes open dialogs after a delay
        createCloseTimer().start();

        // Simulate a click on the narrate button
        narrateButton.doClick();

        // Assert that a pop-up dialog was discovered
        assert(popUpDiscovered);
    }

    /**
     * Creates a timer that closes any open JDialogs after a specified delay.
     * This method is used within tests to automatically close dialogs and verify their content.
     *
     * @return Timer object set to close JDialogs after a delay.
     */
    private Timer createCloseTimer() {
        // Define an action listener for the timer
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Get all open windows in the application
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    // Check and process only JDialog instances
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog)window;
                        // Close the dialog if it's currently visible
                        if (dialog.isVisible()) {
                            // Extract and print the dialog's message
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
                            System.out.println("message = " + s);

                            // Flag that a pop-up dialog was discovered
                            NarrateViewTest.popUpDiscovered = true;

                            // Print the class type and dispose of the window
                            System.out.println("disposing of..." + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }
        };

        // Create and configure the timer with a delay of 3000 milliseconds (3 seconds)
        Timer t = new Timer(3000, close);
        t.setRepeats(false); // Ensure the timer only runs once
        return t; // Return the created timer
    }
}
