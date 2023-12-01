package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** A JPanel extension representing the Login View for Lucid Dream AI.
 * @author Eugene Cho
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "log in";

    final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    final JButton logIn;
    final JButton cancel;

    private final Font textFieldFont = new Font("SansSerif", Font.PLAIN, 20);

    private final LoginViewModel loginViewModel;
    private final LoginController loginController;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a Login View.
     * @param loginViewModel View model for the Login use case.
     * @param controller Controller for the Login use case.
     * @param viewManagerModel
     */
    public LoginView(LoginViewModel loginViewModel, LoginController controller, ViewManagerModel viewManagerModel) {

        this.loginViewModel = loginViewModel;
        this.loginController = controller;
        this.viewManagerModel = viewManagerModel;


        // Create and style the header label
        JLabel title = new JLabel("Lucid Dream AI");
        title.setFont(new Font("SansSerif", Font.BOLD, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Create input container for input fields
        JPanel inputContainer = new JPanel();
        inputContainer.setLayout(new BoxLayout(inputContainer, BoxLayout.Y_AXIS));
        inputContainer.setAlignmentX(Component.CENTER_ALIGNMENT);


        usernameInputField.setFont(textFieldFont);
        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField);


        passwordInputField.setFont(textFieldFont);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);


        // Add input fields to the input container
        inputContainer.add(usernameInfo);
        inputContainer.add(Box.createRigidArea(new Dimension(0, 16)));
        inputContainer.add(passwordInfo);


        // Create container for buttons
        JPanel buttons = new JPanel();

        logIn = new JButton("Login");
        logIn.setPreferredSize(new Dimension(150, 50));
        logIn.setBackground(new Color(0xDDAF37));
        logIn.setForeground(Color.WHITE);


        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(150, 50));
        cancel.setBackground(Color.GRAY);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);


        // Add the buttons to the buttons container
        buttons.add(logIn);
        // White space between the buttons
        buttons.add(Box.createRigidArea(new Dimension(50, 0)));
        buttons.add(cancel);

        // Add all the elements to the Login View panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(inputContainer);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(buttons);
        this.add(Box.createVerticalGlue());


        // Listens for button clicks to the Login Button to start the use case
        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        // Listens for updates to the username input field and updates the state of the login view model
        usernameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText() + e.getKeyChar());
                loginViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });


        // Listens for updates to the password input field and updates the state of the login view model
        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        LoginState currentState = loginViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        loginViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });
    }

    /**
     * Reacts to a click to the Cancel button, which sends the user back to the Landing Page view.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        viewManagerModel.setActiveView("landing page");
        viewManagerModel.firePropertyChanged();

    }


    /**
     * React to a property change that results in evt. Runs when login use case fires a property change.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();

        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
            usernameInputField.setText(null);
            passwordInputField.setText(null);
            state.clearState();

        } else if (state.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getPasswordError());
            state.setPassword("");
            state.setPasswordError(null);
            passwordInputField.setText(null);
        }
    }
}