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
                String word = lookupOutputData.getWord();
                assertNotNull(definition);
                assertNotNull(word);

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");

            }

        };

        LookupInputBoundary interactor = new LookupInteractor(lookupPresenter);
        interactor.execute(inputData);
    }

    @Test
    void FailTest() {
        LookupInputData inputData = new LookupInputData(null);

        LookupOutputBoundary lookupPresenter = new LookupOutputBoundary() {
            @Override
            public void prepareSuccessView(LookupOutputData lookupOutputData) {
                fail("Use case success is unexpected.");

            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertNotNull(errorMessage);

            }

        };

        LookupInputBoundary interactor = new LookupInteractor(lookupPresenter);
        interactor.execute(inputData);
    }

}