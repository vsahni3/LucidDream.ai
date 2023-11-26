package use_case.generate;


public interface GenerateInputBoundary {

    /**
     * Executes the storybook generation process based on the input data.
     * Retrieves the user, generates a storybook, updates user stories, and prepares the output view.
     *
     * @param generateInputData Data required to generate a new storybook, including username and prompt.
     */
    void execute(GenerateInputData generateInputData);
}
