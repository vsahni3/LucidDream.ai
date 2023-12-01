package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.download_story.DownloadController;
import interface_adapter.narrate_story.NarrateController;
import interface_adapter.read_story.ReadStoryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** A JPanel extension representing the Login View for Lucid Dream AI.
 * @author Eugene Cho
 */
public class ReadStoryView extends JPanel {
    public final String viewName = "read story";
    private JLabel imageLabel;
    private JButton leftButton, rightButton, downloadButton, narrateButton, backButton, dictionaryButton;
    private JTextArea pageText;

    private int currentIndex = 0;
    private final ViewManagerModel viewManagerModel;
    private final ReadStoryViewModel readStoryViewModel;

    private final NarrateController narrateController;
    private final LookupController lookupController;
    private final DownloadController downloadController;

    public ReadStoryView(ViewManagerModel viewManagerModel, ReadStoryViewModel readStoryViewModel, NarrateController narrateController, LookupController lookupController, DownloadController downloadController) {

        this.viewManagerModel = viewManagerModel;
        this.readStoryViewModel = readStoryViewModel;
        this.narrateController = narrateController;
        this.lookupController = lookupController;
        this.downloadController = downloadController;


        // Set up the page image display
        imageLabel = new JLabel(readStoryViewModel.getState().getPageImage(currentIndex));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setMaximumSize(new Dimension(500, 500));


        // Create and style the arrow buttons
        leftButton = new JButton("<");
        leftButton.setMaximumSize(new Dimension(100, 100));
        leftButton.setBackground(Color.LIGHT_GRAY);
        leftButton.setForeground(Color.WHITE);

        rightButton = new JButton(">");
        rightButton.setMaximumSize(new Dimension(100, 100));
        rightButton.setBackground(Color.LIGHT_GRAY);
        rightButton.setForeground(Color.WHITE);


        // Book title header
        String storyTitle = "Stupidly Long Book Title";
        JLabel title = new JLabel(storyTitle);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Create back button
        ImageIcon backIcon = new ImageIcon("back_icon.png");
        this.backButton = new JButton(resizeImageIcon(backIcon, 40, 40));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        // Create header container and add back button and title
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(50, 0)));
        header.add(title);


        // Page text
        this.pageText = new JTextArea(readStoryViewModel.getState().getPageText(currentIndex));
        pageText.setMargin(new Insets(30,40,30,40));
        pageText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pageText.setAlignmentX(Component.CENTER_ALIGNMENT);
        pageText.setLineWrap(true);
        pageText.setWrapStyleWord(true);
        pageText.setEditable(false);
        pageText.setMaximumSize(new Dimension(1000, 200));


        // Create container for use case actions
        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.X_AXIS));


        // Create download button
        ImageIcon downloadIcon = new ImageIcon("download_icon.png");
        this.downloadButton = new JButton(resizeImageIcon(downloadIcon, 40, 40));
        downloadButton.setBackground(Color.LIGHT_GRAY);


        // Create narrate Button
        ImageIcon narrateIcon = new ImageIcon("narrate_icon.png");
        this.narrateButton = new JButton(resizeImageIcon(narrateIcon, 40, 40));
        narrateButton.setBackground(new Color(0xDDAF37));

        // Create dictionary Button
        ImageIcon dictionaryIcon = new ImageIcon("dictionary_icon.png");
        this.dictionaryButton = new JButton(resizeImageIcon(dictionaryIcon, 40, 40));
        dictionaryButton.setBackground(new Color(0xDDAF37));


        actions.add(downloadButton);
        actions.add(Box.createRigidArea(new Dimension(30, 0)));
        actions.add(narrateButton);
        actions.add(Box.createRigidArea(new Dimension(30, 0)));
        actions.add(dictionaryButton);


        // Container for the book
        JPanel book = new JPanel(new BorderLayout());
        book.add(imageLabel, BorderLayout.CENTER);
        book.add(leftButton, BorderLayout.WEST);
        book.add(rightButton, BorderLayout.EAST);
        book.setMaximumSize(new Dimension(1000, 500));


        // Layout for ReadStoryView
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(header);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(book);
        this.add(pageText);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(actions);
        this.add(Box.createRigidArea(new Dimension(0, 30)));


        // Page navigation action listeners
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int storyLength = readStoryViewModel.getState().getStoryLength();
                currentIndex = (currentIndex - 1 + storyLength) % storyLength;
                ImageIcon pageImage = readStoryViewModel.getState().getPageImage(currentIndex);
                String text = readStoryViewModel.getState().getPageText(currentIndex);

                imageLabel.setIcon(pageImage);
                pageText.setText(text);

            }
        });


        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int storyLength = readStoryViewModel.getState().getStoryLength();
                currentIndex = (currentIndex + 1) % storyLength;
                ImageIcon pageImage = readStoryViewModel.getState().getPageImage(currentIndex);
                String text = readStoryViewModel.getState().getPageText(currentIndex);

                imageLabel.setIcon(pageImage);
                pageText.setText(text);
            }
        });


        // Listens for clicks on the Download Button to start the download use case
        downloadButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(downloadButton)) {
                            System.out.println("Clicked download");
                        }
                    }
                }
        );

        // Listens for clicks on the Narrate Button to start the narrate use case
        narrateButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(narrateButton)) {
                            System.out.println("Clicked narrate");
                        }
                    }
                }
        );


        // Listens for clicks on the back button to navigate back to the LoggedIn view
        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            System.out.println("Clicked back");
                        }
                    }
                }
        );


        // Listens for clicks on the Dictionary button to start the word lookup use case.
        dictionaryButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(dictionaryButton)) {

                            String selectedText = pageText.getSelectedText();


                            // If the user has highlighted text
                            if (selectedText != null) {
                                // Remove white space
                                selectedText = selectedText.strip();

                                // Extract the first word from the highlighted text
                                String firstWord = selectedText.contains(" ") ? selectedText.split(" ")[0] : selectedText;

                                System.out.println(firstWord);

                            } else {
                                System.out.println("No text selected!");
                            }
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

}
