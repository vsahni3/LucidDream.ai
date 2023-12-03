package interface_adapter.narrate;

import use_case.narrate.NarrateInputBoundary;
import use_case.narrate.NarrateInputData;

/**
 * Controller class for handling narration-related requests.
 * This class acts as an intermediary between the user interface and the narrate use case interactor.
 */
public class NarrateController {

    final NarrateInputBoundary narrateUseCaseInteractor;

    /**
     * Constructs a NarrateController with the specified narrate use case interactor.
     *
     * @param narrateUseCaseInteractor The interactor for the narrate use case.
     */
    public NarrateController(NarrateInputBoundary narrateUseCaseInteractor) {
        this.narrateUseCaseInteractor = narrateUseCaseInteractor;
    }

    /**
     * Executes the narrate use case with the provided text.
     * This method prepares the input data for the narrate interactor and initiates the execution.
     *
     * @param text The text to be narrated.
     */
    public void execute(String text) {
        NarrateInputData narrateInputData = new NarrateInputData(text);
        narrateUseCaseInteractor.execute(narrateInputData);
    }
}
