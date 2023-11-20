package use_case.generate;

/**
 * GenerateOutputBoundary is an interface defining the contract for handling
 * the output of the story generation process. Implementations of this interface
 * should define how to prepare and present the results of the story generation.
 */
public interface GenerateOutputBoundary {
    /**
     * Prepares the view or presentation logic for a successful story generation.
     * This method is called when the story generation process has completed successfully,
     * and the output data is ready to be displayed or processed further.
     *
     * @param user The GenerateOutputData object containing the generated story and associated data.
     */
    void prepareSuccessView(GenerateOutputData user);
}
