package use_case.narrate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NarrateInteractorTest {

    @Test
    void successTest() {
        NarrateInputData inputData = new NarrateInputData("hello there");

        NarrateOutputBoundary successPresenter = new NarrateOutputBoundary() {
            @Override
            public void prepareView() {
                assert true;
            }

        };

        NarrateInputBoundary interactor = new NarrateInteractor(successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void exceptionTest() {
        NarrateInputData inputData = new NarrateInputData("");

        NarrateOutputBoundary successPresenter = new NarrateOutputBoundary() {
            @Override
            public void prepareView() {
                assert false;
            }

        };

        NarrateInputBoundary interactor = new NarrateInteractor(successPresenter);
        Exception exception = assertThrows(
                RuntimeException.class, () -> interactor.execute(inputData)
        );

        assertEquals("java.lang.IllegalArgumentException: Must have text", exception.getMessage());
    }

}