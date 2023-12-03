package use_case.lookup;


import entity.*;
import org.junit.jupiter.api.Test;
import use_case.generate.*;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


class LookupInteractorTest {

    @Test
    void successTest() {
        LookupInputData inputData = new LookupInputData("sardonic");

        LookupOutputBoundary lookupPresenter = new LookupOutputBoundary() {
            @Override
            public void prepareSuccessView(LookupOutputData lookupOutputData) {
                String definition = lookupOutputData.getDefinition();
                assertNotNull(definition);

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

        };

        LookupInputBoundary interactor = new LookupInteractor(lookupPresenter);
        interactor.execute(inputData);
    }

}