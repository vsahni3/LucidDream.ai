package view;

import entity.Page;
import entity.StoryBook;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.read_story.ReadStoryState;
import interface_adapter.read_story.ReadStoryViewModel;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the View for the View Stories module, responsible for displaying a
 * list of story books and handling user interactions.
 */
public class ViewStoriesView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "view stories";

    ArrayList<StoryBook> userStoryBooks = new ArrayList<>();

    private JPanel selectedStoryPanel;

    private JButton backButton;

    private JPanel stories;

    private final ViewStoriesViewModel viewStoriesViewModel;

    private final ViewStoriesController viewStoriesController;

    private final ViewManagerModel viewManagerModel;

    private final ReadStoryViewModel readStoryViewModel;

    /**
     * Constructs a new ViewStoriesView with the specified ViewModels and Controller.
     *
     * @param viewStoriesViewModel The ViewModel for the View Stories module.
     * @param viewStoriesController The Controller for the View Stories module.
     * @param viewManagerModel The View Manager Model for controlling view transitions.
     * @param readStoryViewModel The ViewModel for the Read Story module.
     */
    public ViewStoriesView(ViewStoriesViewModel viewStoriesViewModel, ViewStoriesController viewStoriesController, ViewManagerModel viewManagerModel, ReadStoryViewModel readStoryViewModel) {

        this.viewStoriesViewModel = viewStoriesViewModel;
        this.viewStoriesController = viewStoriesController;
        this.viewManagerModel = viewManagerModel;
        this.readStoryViewModel = readStoryViewModel;

        viewStoriesViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(viewStoriesViewModel.TITLE_LABEL);
        title.setFont(new Font("SansSerif", Font.PLAIN, 55));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel with a vertical BoxLayout to contain the list of storybook cards
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        stories = new JPanel();
        stories.setLayout(new BoxLayout(stories, BoxLayout.X_AXIS));

        // Create a scroll pane to house the content
        JScrollPane scrollPane = new JScrollPane(stories);

        // Set the scroll bar policy
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1000, 550));

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
        String selectedStoryTitle = "TEST SELECT";
//        String selectedStoryTitle = viewStoriesViewModel.getState().getSelected() == null ? "" : viewStoriesViewModel.getState().getSelected().getTitle();

        // Create back button
        ImageIcon backIcon = new ImageIcon("back_icon.png");
        this.backButton = new JButton(resizeImageIcon(backIcon, 40, 40));
        backButton.addActionListener(this);

        selectedStoryPanel = new JPanel();
        selectedStoryPanel.setLayout(new BoxLayout(selectedStoryPanel, BoxLayout.X_AXIS));
        JLabel selected = new JLabel("CURRENTLY SELECTED: ");
        selected.setFont(new Font("SansSerif", Font.PLAIN, 20));
        selectedStoryPanel.add(selected);

        JButton viewStory = new JButton(viewStoriesViewModel.READ_BUTTON_LABEL);
        viewStory.setFont(new Font("SansSerif", Font.BOLD, 20));
        viewStory.setBackground(new Color(0xDDAF37));
        viewStory.setForeground(Color.WHITE);

        selectionPanel.add(backButton);
        selectionPanel.add(Box.createHorizontalStrut(20));
        selectionPanel.add(selectedStoryPanel);
        selectionPanel.add(Box.createHorizontalStrut(20));
        selectionPanel.add(viewStory);

        wrapper.add(Box.createVerticalStrut(20));
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
                            StoryBook selectedStoryBook = currentState.getSelected();

                            String title = selectedStoryBook.getTitle();
                            ArrayList<Page> pages = selectedStoryBook.getPages();
                            ArrayList<String> tempPageTexts = new ArrayList<>();
                            ArrayList<ImageIcon> tempPageImages = new ArrayList<>();

                            pages.forEach((page) -> tempPageTexts.add(page.getTextContents()));

                            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                            int screenWidth = screenSize.width;
                            int screenHeight = screenSize.height;

                            // Calculate proportional size for imageLabel
                            int imageWidth = screenWidth / 2;  // Example: half of the screen width
                            int imageHeight = screenHeight / 2;  // Example: quarter of the screen height

                            try {
                                for (int i = 0; i < pages.size(); i++) {

                                    // Converting byte[] to ImageIcon
                                    byte[] pageImage = pages.get(i).getImage();
                                    ByteArrayInputStream inputStream = new ByteArrayInputStream(pageImage);
                                    Image image = ImageIO.read(inputStream);

                                    ImageIcon pageImgIcon = new ImageIcon(image);
                                    pageImgIcon = resizeImageIcon(pageImgIcon, imageWidth, imageHeight);
                                    tempPageImages.add(pageImgIcon);

                                }

                            } catch (Exception error) {
                                System.out.println(error);
                            }

                            // Converting the ArrayList<String> to a String[]
                            String[] pageTexts = new String[tempPageTexts.size()];
                            pageTexts = tempPageTexts.toArray(pageTexts);


                            // Converting the ArrayList<ImageIcon> to ImageIcon[]
                            ImageIcon[] pageImages = new ImageIcon[tempPageImages.size()];
                            pageImages = tempPageImages.toArray(pageImages);

                            // Update state
                            ReadStoryState readStoryState = readStoryViewModel.getState();
                            readStoryState.setTitle(title);
                            readStoryState.setPageTexts(pageTexts);
                            readStoryState.setPageImages(pageImages);

                            readStoryViewModel.setState(readStoryState); // CHANGE THIS
                            readStoryViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(readStoryViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        viewStoriesViewModel.getState().clearState();
        viewManagerModel.setActiveView("logged in");
        viewManagerModel.firePropertyChanged();
        stories.removeAll();
        selectedStoryPanel.removeAll();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewStoriesState state = (ViewStoriesState) evt.getNewValue();
        // Additional condition check to prevent a duplicate story card from being added when SELECT is clicked
        if (evt.getPropertyName().equals("view-stories") && stories.getComponents().length < state.getStoryBooks().size()) {
            userStoryBooks = state.getStoryBooks();

            try {
                for (int i = 0; i < userStoryBooks.size(); i++) {
                    JPanel card = new JPanel();
                    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

                    // For this storybook, retrieve the 1st page's image attribute
                    byte[] pageImage = userStoryBooks.get(i).getPages().get(0).getImage();
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
                    JLabel titleLabel = new JLabel(userStoryBooks.get(i).getTitle());
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
                    StoryBook finalBook = viewStoriesViewModel.getState().getStoryBooks().get(i);  // To make the variable effectively final for lambda expression
                    selectButton.addActionListener(e -> handleSelectButtonClick(finalBook));

                    card.add(selectButton);
                    card.setBorder(new EmptyBorder(0, 30, 0, 30));

                    stories.add(card);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    // Given an ImageIcon and desired width and height, the function rescales the image to the desired resolution.
    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void handleSelectButtonClick(StoryBook selectedStoryBook) {
        // Update state as needed
        ViewStoriesState currentState = viewStoriesViewModel.getState();
        currentState.setSelected(selectedStoryBook);
        System.out.println("Selected Storybook Title: " + currentState.getSelected().getTitle());
        JLabel storyTitleLabel = new JLabel(currentState.getSelected().getTitle());
        storyTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel selectedLabel = new JLabel("CURRENTLY SELECTED: ");
        selectedLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        selectedStoryPanel.removeAll();
        selectedStoryPanel.add(selectedLabel);
        selectedStoryPanel.add(storyTitleLabel);
        selectedStoryPanel.repaint();
        selectedStoryPanel.revalidate();
        viewStoriesViewModel.firePropertyChanged();
    }

}
