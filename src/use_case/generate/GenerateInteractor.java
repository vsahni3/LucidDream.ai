package use_case.generate;

import entity.PageFactory;
import entity.StoryBook;
import entity.StoryBookFactory;
import entity.User;

import java.util.List;

public class GenerateInteractor implements GenerateInputBoundary {
    final GenerateUserDataAccessInterface userDataAccessObject;
    final GenerateOutputBoundary generatePresenter;
    final StoryBookFactory storyBookFactory;

    final PageFactory pageFactory;

    public GenerateInteractor(GenerateUserDataAccessInterface userDataAccessInterface,
                              GenerateOutputBoundary generateOutputBoundary, StoryBookFactory storyBookFactory,
                              PageFactory pageFactory) {
        this.userDataAccessObject = userDataAccessInterface;
        this.generatePresenter = generateOutputBoundary;
        this.storyBookFactory = storyBookFactory;
        this.pageFactory = pageFactory;
    }


    @Override
    public void execute(GenerateInputData generateInputData) {


        String username = generateInputData.getUsername();
        String prompt = generateInputData.getPrompt();


        User user = userDataAccessObject.get(username);
        StoryBook outputStoryBook = storyBookFactory.create(prompt, pageFactory);
        List<StoryBook> userStories = user.getStories();

        userStories.add(outputStoryBook);
        userDataAccessObject.save(user);

        GenerateOutputData generateOutputData = new GenerateOutputData(outputStoryBook);
        generatePresenter.prepareSuccessView(generateOutputData);


    }
}
