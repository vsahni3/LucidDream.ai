package view;

import entity.StoryBook;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.view_stories.ViewStoriesController;
import interface_adapter.view_stories.ViewStoriesState;
import interface_adapter.view_stories.ViewStoriesViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ViewStoriesView extends JPanel {

    public final String viewName = "view stories";

    private final ViewStoriesViewModel viewStoriesViewModel;

    private final ViewStoriesController viewStoriesController;

    private final ViewManagerModel viewManagerModel;

    public ViewStoriesView(ViewStoriesViewModel viewStoriesViewModel, ViewStoriesController viewStoriesController, ViewManagerModel viewManagerModel) {

        this.viewStoriesViewModel = viewStoriesViewModel;
        this.viewStoriesController = viewStoriesController;
        this.viewManagerModel = viewManagerModel;

        JLabel title = new JLabel(viewStoriesViewModel.TITLE_LABEL);
        title.setFont(new Font("SansSerif", Font.PLAIN, 55));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel with a vertical BoxLayout to contain the list of storybook cards
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JPanel stories = new JPanel();
        stories.setLayout(new BoxLayout(stories, BoxLayout.X_AXIS));

        try {
            for (int i = 0; i < viewStoriesViewModel.storyBooks.size(); i++) {
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

                // For this storybook, retrieve the 1st page's image attribute
                byte[] pageImage = viewStoriesViewModel.storyBooks.get(i).getPages().get(0).getImage();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(pageImage);
                Image image = ImageIO.read(inputStream);
                // Convert into image icon
                ImageIcon pageImgIcon = new ImageIcon(image);

                ImageIcon resized = resizeImageIcon(pageImgIcon,350,350);
                JLabel bookCover = new JLabel(resized);
                bookCover.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(bookCover);
                card.add(Box.createVerticalStrut(20));

                // Replace with StoryBook.getTitle()
                JLabel titleLabel = new JLabel(viewStoriesViewModel.storyBooks.get(i).getTitle());
                titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 35));
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(titleLabel);
                card.add(Box.createVerticalStrut(10));

                JButton selectButton = new JButton(viewStoriesViewModel.SELECT_BUTTON_LABEL);
                selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                selectButton.setPreferredSize(new Dimension(120, 38));
                selectButton.setFont(new Font("SansSerif", Font.BOLD, 13));
                selectButton.setForeground(Color.WHITE);
                selectButton.setBackground(Color.GRAY);
                // Add action listener to the selectButton

                StoryBook finalBook = viewStoriesViewModel.storyBooks.get(i);  // To make the variable effectively final for lambda expression
                selectButton.addActionListener(e -> handleSelectButtonClick(finalBook));
                card.add(selectButton);

                card.setBorder(new EmptyBorder(0, 30, 0, 30));

                stories.add(card);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        // Create a scroll pane to house the content
        JScrollPane scrollPane = new JScrollPane(stories);

        // Set the scroll bar policy
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1000, 600));

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
        String selectedStoryTitle = viewStoriesViewModel.getState().getSelected() == null ? "" : viewStoriesViewModel.getState().getSelected().getTitle();
        JLabel selected = new JLabel("CURRENTLY SELECTED: " + selectedStoryTitle);
        selected.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JButton viewStory = new JButton(viewStoriesViewModel.READ_BUTTON_LABEL);
        viewStory.setFont(new Font("SansSerif", Font.BOLD, 20));
        viewStory.setBackground(new Color(0xDDAF37));
        viewStory.setForeground(Color.WHITE);

        selectionPanel.add(selected);
        selectionPanel.add(Box.createHorizontalStrut(20));
        selectionPanel.add(viewStory);

        wrapper.add(Box.createVerticalStrut(60));
        wrapper.add(title);
        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(scrollPane);
        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(selectionPanel);

        this.add(wrapper);

        // Listen for button clicks to the Create story Button to start the use case
        viewStory.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(viewStory)) {
                            ViewStoriesState currentState = viewStoriesViewModel.getState();
                            // Call the read story controller here
                        }
                    }
                }
        );

    }

    // Given an ImageIcon and desired width and height, the function rescales the image to the desired resolution.
    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void handleSelectButtonClick(StoryBook selectedStoryBook) {
        // Here, you can update the state or perform any other actions based on the selected storybook
        System.out.println("Selected Storybook Title: " + selectedStoryBook.getTitle());
        // Update state as needed
        ViewStoriesState currentState = viewStoriesViewModel.getState();
        currentState.setSelected(selectedStoryBook);
        viewStoriesViewModel.firePropertyChanged();
    }

}
