package interface_adapter.generate_story;

import use_case.generate.GenerateInputBoundary;
import use_case.generate.GenerateInputData;

public class GenerateStoryController {

    final GenerateInputBoundary generateUseCaseInteractor;

    public GenerateStoryController(GenerateInputBoundary generateUseCaseInteractor) {
        this.generateUseCaseInteractor = generateUseCaseInteractor;
    }

    public void execute(String prompt, String username) {
        GenerateInputData generateInputData = new GenerateInputData(prompt, username);

        generateUseCaseInteractor.execute(generateInputData);

    }
}
