package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LandingView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "landing page";


    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    private final ViewManagerModel viewManagerModel;

    final JButton logIn;

    final JButton signUp;

    public LandingView(SignupViewModel signupViewModel, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {

        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;

        JLabel title = new JLabel("Landing Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        logIn = new JButton("Login");
        buttons.add(logIn);
        signUp = new JButton("Sign Up");
        buttons.add(signUp);

        logIn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {

                            viewManagerModel.setActiveView(loginViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
//                            LandingPageSelectState currentState = landingPageSelectViewModel.getState();
//                            currentState.setSelection("Log in");
//
//                            landingPageSelectController.execute(
//                                    currentState.getSelection()
//                            );

                        }
                    }
                }
        );

        signUp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {

                            viewManagerModel.setActiveView(signupViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();

                        }

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(buttons);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("YUH");

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("HAHA");

    }
}
