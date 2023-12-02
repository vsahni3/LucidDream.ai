package use_case.narrate;

public class NarrateInteractor implements NarrateInputBoundary{

    final NarrateOutputBoundary narratePresenter;

    public NarrateInteractor(NarrateOutputBoundary narratePresenter) {
        this.narratePresenter = narratePresenter;
    }

    @Override
    public void execute(NarrateInputData narrateInputData) {
        String text = narrateInputData.getText();

        narratePresenter.prepareView();
    }
}
