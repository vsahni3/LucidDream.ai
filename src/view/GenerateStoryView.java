package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.generate_story.GenerateStoryController;
import interface_adapter.generate_story.GenerateStoryState;
import interface_adapter.generate_story.GenerateStoryViewModel;
import interface_adapter.login.LoginState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GenerateStoryView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "generate storybook";

    private final GenerateStoryViewModel generateStoryViewModel;

    final JTextField promptInputField = new JTextField(150);

    private final JLabel promptErrorField = new JLabel();

    final JButton create;

    final JButton viewStories;

    private final GenerateStoryController generateStoryController;

    private final ViewManagerModel viewManagerModel;

    public GenerateStoryView(GenerateStoryViewModel generateStoryViewModel, GenerateStoryController generateStoryController, ViewManagerModel viewManagerModel) {

        this.viewManagerModel = viewManagerModel;
        this.generateStoryController = generateStoryController;
        this.generateStoryViewModel = generateStoryViewModel;
        this.generateStoryViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("LucidDream AI");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel storyPrompt = new LabelTextPanel(
                new JLabel("Prompt"), promptInputField);

        JPanel buttons = new JPanel();
        create = new JButton(generateStoryViewModel.CREATE_BUTTON_LABEL);
        buttons.add(create);
        viewStories = new JButton(generateStoryViewModel.VIEW_STORIES_BUTTON_LABEL);
        buttons.add(viewStories);

        create.addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(create)) {
                            GenerateStoryState currentState = generateStoryViewModel.getState();
                            System.out.println("");
//                            generateStoryController.execute(
//                                    currentState.getPrompt(),
//                                    currentState.getUser()
//                            );
                        }
                    }
                }
        );

        promptInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                GenerateStoryState currentState = generateStoryViewModel.getState();
                currentState.setPrompt(promptInputField.getText() + e.getKeyChar());
                generateStoryViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.add(title);
        this.add(storyPrompt);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
