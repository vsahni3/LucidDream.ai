package use_case.generate;


/**
 * GenerateInputData encapsulates the input data required for generating a story.
 * It holds the user's prompt and username as the core information for this process.
 */
public class GenerateInputData {

    final private String prompt;
    final private String username;

    /**
     * Constructs a new GenerateInputData object with the specified prompt and username.
     *
     * @param prompt   The prompt based on which the story is to be generated.
     * @param username The username of the user requesting the story generation.
     */
    public GenerateInputData(String prompt, String username) {
        this.prompt = prompt;
        this.username = username;
    }

    /**
     * Retrieves the prompt for the story generation.
     *
     * @return The prompt for the story.
     */
    String getPrompt() {
        return prompt;
    }

    /**
     * Retrieves the username of the user who requested the story generation.
     *
     * @return The username of the user.
     */
    String getUsername() {
        return username;
    }

}
