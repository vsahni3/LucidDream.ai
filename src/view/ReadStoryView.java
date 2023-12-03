package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.download_story.DownloadController;
import interface_adapter.lookup.LookupController;
import interface_adapter.lookup.LookupViewModel;
import interface_adapter.narrate.NarrateController;
import interface_adapter.narrate.NarrateViewModel;
import interface_adapter.read_story.ReadStoryViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** A JPanel extension representing the ReadStory View for Lucid Dream AI.
 * @author Eugene Cho
 */
public class ReadStoryView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "read story";

    private JLabel imageLabel, title;
    private JButton leftButton, rightButton, downloadButton, narrateButton, backButton, dictionaryButton;
    private JTextArea pageText;

    private int numLoaded = 0;
    private int currentIndex = 0;
    private final ViewManagerModel viewManagerModel;
    private final ReadStoryViewModel readStoryViewModel;

    private final NarrateViewModel narrateViewModel;

    private final NarrateController narrateController;
    private final LookupController lookupController;
    private final DownloadController downloadController;
    private final LookupViewModel lookupViewModel;

    /**
     * Constructs a ReadStory View.
     * @param viewManagerModel
     * @param readStoryViewModel
     * @param narrateViewModel
     * @param narrateController
     * @param downloadController
     * @param lookupController
     * @param lookupViewModel
     */
    public ReadStoryView(ViewManagerModel viewManagerModel, ReadStoryViewModel readStoryViewModel, NarrateViewModel narrateViewModel, NarrateController narrateController, LookupController lookupController, DownloadController downloadController, LookupViewModel lookupViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.readStoryViewModel = readStoryViewModel;
        this.narrateViewModel = narrateViewModel;
        this.narrateController = narrateController;
        this.lookupController = lookupController;
        this.downloadController = downloadController;
        this.lookupViewModel = lookupViewModel;


        readStoryViewModel.addPropertyChangeListener(this);
        narrateViewModel.addPropertyChangeListener(this);
        lookupViewModel.addPropertyChangeListener(this);
        ImageIcon placeholderImage = new ImageIcon("narrate_icon.png");

        // Set up the page image display
        imageLabel = new JLabel(placeholderImage);
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
        this.title = new JLabel("placeholder title");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Create back button
        ImageIcon backIcon = new ImageIcon("back_icon.png");
        this.backButton = new JButton(resizeImageIcon(backIcon, 40, 40));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(this);

        // Create header container and add back button and title
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(50, 0)));
        header.add(title);


        // Page text
        this.pageText = new JTextArea("place holder text");
        pageText.setMargin(new Insets(30,40,30,40));
        pageText.setFont(new Font("SansSerif", Font.PLAIN, 18));
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
        this.add(Box.createRigidArea(new Dimension(0, 40)));
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
                        String text = readStoryViewModel.getState().getPageText(currentIndex);
                        if (evt.getSource().equals(narrateButton)) {
                            narrateController.execute(text);
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

                                lookupController.execute(firstWord);

                            } else {
                                lookupController.execute(null);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        readStoryViewModel.getState().clearState();
        viewManagerModel.setActiveView("logged in");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("state")) {
            if (readStoryViewModel.getState().isActive()) {
                imageLabel.setIcon(readStoryViewModel.getState().getPageImage(currentIndex));
                pageText.setText(readStoryViewModel.getState().getPageText(currentIndex));
                title.setText(readStoryViewModel.getState().getTitle());
            }
        } else if (evt.getPropertyName().equals("narrate")) {
            JOptionPane.showMessageDialog(this, "Narrating page text.");

        } else if (evt.getPropertyName().equals("lookup")) {
            JOptionPane.showMessageDialog(this, "Definition for: " + lookupViewModel.getState().getWord() + "\n" + lookupViewModel.getState().getDefinition());

        }


    }
}
