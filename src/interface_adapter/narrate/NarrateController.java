package interface_adapter.narrate;

import use_case.narrate.NarrateInputBoundary;

public class NarrateController {

    final NarrateInputBoundary narrateUseCaseInteractor;


    public NarrateController(NarrateInputBoundary narrateUseCaseInteractor) {
        this.narrateUseCaseInteractor = narrateUseCaseInteractor;
    }
    public void execute() {
        narrateUseCaseInteractor.execute();
    }
}
