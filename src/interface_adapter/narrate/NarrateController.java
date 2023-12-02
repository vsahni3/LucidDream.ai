package interface_adapter.narrate;

import use_case.narrate.NarrateInputBoundary;
import use_case.narrate.NarrateInputData;

public class NarrateController {

    final NarrateInputBoundary narrateUseCaseInteractor;


    public NarrateController(NarrateInputBoundary narrateUseCaseInteractor) {
        this.narrateUseCaseInteractor = narrateUseCaseInteractor;
    }
    public void execute(String text) {
        NarrateInputData narrateInputData = new NarrateInputData(text);
        narrateUseCaseInteractor.execute(narrateInputData);
    }
}
