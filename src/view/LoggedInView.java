package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    final JTextField promptInputField = new JTextField(40);
    private final ViewManagerModel viewManagerModel;
    private final GenerateStoryController generateStoryController;
    JLabel username;
    final JButton create;
    final JButton viewStories;
    final JButton logOut;


    /**
     * A window with a title and a JButton.
     */
    public LoggedInView(LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel, GenerateStoryController generateStoryController) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.generateStoryController = generateStoryController;

        this.loggedInViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("LucidDream A.I.");
        title.setFont(new Font("SansSerif", Font.BOLD, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameInfo = new JLabel("Hello, ");
        usernameInfo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        username = new JLabel();
        username.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // Create FlowLayout to contain current user
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userPanel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        userPanel.add(usernameInfo);
        userPanel.add(username);
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create FlowLayout to contain the prompt input field & create button
        JPanel createPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        promptInputField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        LabelTextPanel storyPrompt = new LabelTextPanel(
                new JLabel(loggedInViewModel.PROMPT_LABEL), promptInputField);
        create = new JButton(loggedInViewModel.CREATE_BUTTON_LABEL);
        create.setPreferredSize(new Dimension(120, 50));
        create.setFont(new Font("Arial", Font.BOLD, 16));
        create.setBackground(new Color(0xDDAF37));
        create.setForeground(Color.WHITE);
        createPanel.add(storyPrompt);
        createPanel.add(create);
        createPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons panel for external navigation
        JPanel buttons = new JPanel();
        viewStories = new JButton(loggedInViewModel.VIEW_STORIES_BUTTON_LABEL);
        viewStories.setPreferredSize(new Dimension(120, 38));
        viewStories.setForeground(Color.WHITE);
        viewStories.setBackground(Color.GRAY);
        buttons.add(viewStories);
        logOut = new JButton(loggedInViewModel.LOGOUT_BUTTON_LABEL);
        logOut.setPreferredSize(new Dimension(120, 38));
        logOut.setForeground(Color.WHITE);
        logOut.setBackground(Color.GRAY);
        buttons.add(logOut);
        logOut.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Add flexible space that expands or contracts to fill the available vertical space within its container.
        this.add(Box.createVerticalGlue());

        this.add(title);
        this.add(userPanel);
        this.add(createPanel);
        this.add(buttons);

        // Add flexible space that expands or contracts to fill the available vertical space within its container.
        this.add(Box.createVerticalGlue());

        // Listens for updates to the story prompt input field and updates the state of the logged in view model
        promptInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoggedInState currentState = loggedInViewModel.getState();
                currentState.setStoryPrompt(promptInputField.getText() + e.getKeyChar());
                loggedInViewModel.setState(currentState);
                System.out.println("Set prompt to: " + currentState.getStoryPrompt());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // Listen for button clicks to the Create story Button to start the use case
        create.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(create)) {
                            LoggedInState currentState = loggedInViewModel.getState();

                            generateStoryController.execute(currentState.getStoryPrompt(), currentState.getUsername());
                        }
                    }
                }
        );
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        viewManagerModel.setActiveView("landing page");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        username.setText(state.getUsername());
    }
}