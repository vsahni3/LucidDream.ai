package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_stories.ViewStoriesController;
import interface_adapter.view_stories.ViewStoriesViewModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;

public class ViewStoriesView extends JPanel {

    public final String viewName = "view stories";

    private final ViewStoriesViewModel viewStoriesViewModel;

    private final ViewStoriesController viewStoriesController;

    final JButton viewStories;

    final JButton export;

    private final ViewManagerModel viewManagerModel;

    public ViewStoriesView(ViewStoriesViewModel viewStoriesViewModel, ViewStoriesController viewStoriesController, ViewManagerModel viewManagerModel) {

        this.viewStoriesViewModel = viewStoriesViewModel;
        this.viewStoriesController = viewStoriesController;
        this.viewManagerModel = viewManagerModel;

        JLabel title = new JLabel("Storybooks");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewStories = new JButton(viewStoriesViewModel.VIEW_BUTTON_LABEL);
        export = new JButton(viewStoriesViewModel.EXPORT_BUTTON_LABEL);

        // Panel with a vertical BoxLayout to contain the list of storybook cards
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a JScrollPane to enable vertical scrolling
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // CREATE PLACEHOLDER CARDS
        JPanel cardPanel1 = new JPanel();
        cardPanel1.setBorder(BorderFactory.createEtchedBorder());
        cardPanel1.setPreferredSize(new Dimension(200, 50));

        JLabel label1 = new JLabel("Story Title");
        cardPanel1.add(label1);

        JPanel cardPanel2 = new JPanel();
        cardPanel2.setBorder(BorderFactory.createEtchedBorder());
        cardPanel2.setPreferredSize(new Dimension(200, 50));

        JLabel label2 = new JLabel("Story Title");
        cardPanel2.add(label2);

        // add cards to panel
        panel.add(cardPanel1);
        panel.add(cardPanel2);

    }

}
