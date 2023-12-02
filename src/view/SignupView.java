package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** A JPanel extension representing the Sign-Up View for Lucid Dream AI.
 * @author Eugene Cho
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final SignupController signupController;

    private final ViewManagerModel viewManagerModel;
    private final Font textFieldFont = new Font("SansSerif", Font.PLAIN, 16);

    private final JButton signUp;
    private final JButton cancel;

    /**
     * Constructs a Sign-Up View.
     * @param controller Controller for the Sign-Up use case.
     * @param signupViewModel View model for the Sign-Up use case.
     * @param viewManagerModel
     */
    public SignupView(SignupController controller, SignupViewModel signupViewModel, ViewManagerModel viewManagerModel) {

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;

        signupViewModel.addPropertyChangeListener(this);

        // Create and style Title component
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

        repeatPasswordInputField.setFont(textFieldFont);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel("Confirm Password"), repeatPasswordInputField);


        // Add input fields to the input container
        inputContainer.add(usernameInfo);
        inputContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        inputContainer.add(passwordInfo);
        inputContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        inputContainer.add(repeatPasswordInfo);

        // Create a container for the buttons
        JPanel buttons = new JPanel();


        // Sign up button creation and styling
        signUp = new JButton("Sign Up");
        signUp.setPreferredSize(new Dimension(150, 50));
        signUp.setBackground(new Color(0xDDAF37));
        signUp.setForeground(Color.WHITE);


        // Cancel button creation and styling
        cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(150, 50));
        cancel.setBackground(Color.GRAY);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);


        // Add the buttons to the button container
        buttons.add(signUp);
        // White space between the buttons
        buttons.add(Box.createRigidArea(new Dimension(50, 0)));
        buttons.add(cancel);


        // Add all the elements to the Sign up panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(inputContainer);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(buttons);
        this.add(Box.createVerticalGlue());


        // Listens for clicks on the signup button
        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );


        // Updates the username input state based on key presses
        usernameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = usernameInputField.getText() + e.getKeyChar();
                        currentState.setUsername(text);
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });


        // Updates the password input state based on key presses
        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                }
        );

        // Updates the repeat password input state based on key presses
        repeatPasswordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setRepeatPassword(repeatPasswordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState); // Hmm, is this necessary?
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                }
        );
    }

    /**
     * React to a button click to Cancel that results in evt.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        usernameInputField.setText(null);
        passwordInputField.setText(null);
        repeatPasswordInputField.setText(null);

        viewManagerModel.setActiveView("landing page");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * React to a property change that results in evt. Runs when sign up use cases fires a property change.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        SignupState state = (SignupState) evt.getNewValue();

        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
            usernameInputField.setText(null);
            passwordInputField.setText(null);
            repeatPasswordInputField.setText(null);
            state.clearState();

        } else if (state.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getPasswordError());
            state.setPassword("");
            state.setPasswordError(null);
            state.setRepeatPassword("");
            state.setRepeatPasswordError(null);

            passwordInputField.setText(null);
            repeatPasswordInputField.setText(null);

        } else if (state.getRepeatPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getRepeatPasswordError());
            state.setPassword("");
            state.setPasswordError(null);
            state.setRepeatPassword("");
            state.setRepeatPasswordError(null);

            passwordInputField.setText(null);
            repeatPasswordInputField.setText(null);

        } else {
            usernameInputField.setText(null);
            passwordInputField.setText(null);
            repeatPasswordInputField.setText(null);
            state.clearState();
        }
    }
}