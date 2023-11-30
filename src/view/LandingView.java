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


    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;

    private final ViewManagerModel viewManagerModel;

    public final String viewName = "landing page";

    final JButton logIn;

    final JButton signUp;

    public LandingView(SignupViewModel signupViewModel, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {

        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;


        // Title element styling
        JLabel title = new JLabel("Lucid Dream AI");
        title.setFont(new Font("SansSerif", Font.BOLD, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Blurb element styling
        JLabel blurb = new JLabel("Welcome to the future of story telling.");
        blurb.setFont(new Font("SansSerif", Font.PLAIN, 30));
        blurb.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Create button JPanel
        JPanel buttons = new JPanel();

        // Style Login button
        logIn = new JButton("Login");
        logIn.setPreferredSize(new Dimension(150, 65));
        logIn.setBackground(new Color(0xDDAF37));
        logIn.setForeground(Color.WHITE);
        buttons.add(logIn);

        // White space between the buttons
        buttons.add(Box.createRigidArea(new Dimension(50, 0)));

        // Style Sign up button
        signUp = new JButton("Sign Up");
        signUp.setPreferredSize(new Dimension(150, 65));
        signUp.setBackground(Color.GRAY);
        signUp.setForeground(Color.WHITE);
        buttons.add(signUp);


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(blurb);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(buttons);
        this.add(Box.createVerticalGlue());


        logIn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            viewManagerModel.setActiveView(loginViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();

                        }
                    }
                }
        );

        signUp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            System.out.println("Clicked Sign up");
                            viewManagerModel.setActiveView(signupViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();

                        }

                    }
                }
        );

    }

    // Must modify
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("YUH");

    }


    // Must modify
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("HAHA");

    }
}

