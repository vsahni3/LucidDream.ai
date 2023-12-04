package interface_adapter.generate_story;

public class GenerateStoryState {


    private String prompt = "";
    private String promptError = null;

    public GenerateStoryState(GenerateStoryState copy) {
        prompt = copy.prompt;
        promptError = copy.promptError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public GenerateStoryState() {}

    public void clearState() {
        prompt = "";
        promptError = null;
    }

    /**
     * Return the prompt entered by the user for story book generation
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Return the prompt error value
     */
    public String getpromptError() {
        return promptError;
    }

    /**
     * Set a new string value for the prompt
     *
     * @param prompt
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Set a new string value for the prompt error value
     *
     * @param promptError
     */
    public void setPromptError(String promptError) {
        this.promptError = promptError;
    }
}
