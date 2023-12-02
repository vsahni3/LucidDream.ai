package interface_adapter.lookup;


import use_case.lookup.LookupInputBoundary;
import use_case.lookup.LookupInputData;

public class LookupController {

    final LookupInputBoundary lookupInteractor;

    public LookupController(LookupInputBoundary lookupInteractor) {
        this.lookupInteractor = lookupInteractor;
    }

    public void execute(String word) {
        LookupInputData lookupInputData = new LookupInputData(word);

        lookupInteractor.execute(lookupInputData);

    }
}