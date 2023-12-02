package interface_adapter.generate_story;

import entity.Page;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.read_story.ReadStoryState;
import interface_adapter.read_story.ReadStoryViewModel;
import use_case.generate.GenerateOutputBoundary;
import use_case.generate.GenerateOutputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * GenerateStoryPresenter is responsible for handling the output data provided by the GenerateInteractor
 * and updates the current active view.
 */
public class GenerateStoryPresenter implements GenerateOutputBoundary {

    private final LoggedInViewModel loggedInViewModel;
    private final ReadStoryViewModel readStoryViewModel;
    private ViewManagerModel viewManagerModel;


    /**
     * Constructs a presenter for the Generate Story use case.
     * @param viewManagerModel
     * @param readStoryViewModel
     * @param loggedInViewModel
     */
    public GenerateStoryPresenter(ViewManagerModel viewManagerModel,
                          ReadStoryViewModel readStoryViewModel,
                          LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.readStoryViewModel = readStoryViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Unpacks the output data and updates the current view to be the ReadStoryView.
     * @param book
     */
    @Override
    public void prepareSuccessView(GenerateOutputData book) {
        ReadStoryState readStoryState = readStoryViewModel.getState();

        String title = book.getStoryBook().getTitle();
        ArrayList<Page> pages = book.getStoryBook().getPages();
        ArrayList<String> tempPageTexts = new ArrayList<>();
        ArrayList<ImageIcon> tempPageImages = new ArrayList<>();

        pages.forEach((page) -> tempPageTexts.add(page.getTextContents()));

        try {
            for (int i = 0; i < pages.size(); i++) {

                // Converting byte[] to ImageIcon
                byte[] pageImage = pages.get(i).getImage();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(pageImage);
                Image image = ImageIO.read(inputStream);

                ImageIcon pageImgIcon = new ImageIcon(image);
                pageImgIcon = resizeImageIcon(pageImgIcon, 900, 500);
                tempPageImages.add(pageImgIcon);

            }

        } catch (Exception e) {
            System.out.println(e);
        }


        // Converting the ArrayList<String> to a String[]
        String[] pageTexts = new String[tempPageTexts.size()];
        pageTexts = tempPageTexts.toArray(pageTexts);


        // Converting the ArrayList<ImageIcon> to ImageIcon[]
        ImageIcon[] pageImages = new ImageIcon[tempPageImages.size()];
        pageImages = tempPageImages.toArray(pageImages);


        // Update the state
        readStoryState.setTitle(title);
        readStoryState.setPageTexts(pageTexts);
        readStoryState.setPageImages(pageImages);


        this.readStoryViewModel.setState(readStoryState); // CHANGE THIS
        this.readStoryViewModel.firePropertyChanged();
        this.viewManagerModel.setActiveView(readStoryViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    // Given an ImageIcon and desired width and height, the function rescales the image to the desired resolution.
    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

}
