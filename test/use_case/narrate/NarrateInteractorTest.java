package use_case.narrate;

import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for NarrateInteractor.
 * This class contains tests to validate the functionality of the narrate use case.
 */
class NarrateInteractorTest {

    /**
     * Test to ensure successful execution of the Narrate use case.
     * Verifies that the interactor can process valid input data without errors.
     */
    @Test
    void successTest() {
        // Create input data for narrating text
        NarrateInputData inputData = new NarrateInputData("hello there");

        // Mock implementation of NarrateOutputBoundary for success scenario
        NarrateOutputBoundary successPresenter = new NarrateOutputBoundary() {
            @Override
            public void prepareView() {
                // Assert true to signify that the method was called successfully
                assert true;
            }
        };

        // Create the interactor with the mocked presenter
        NarrateInputBoundary interactor = new NarrateInteractor(successPresenter);


        // Execute the interactor with valid input data
        interactor.execute(inputData);

    }

    /**
     * Test to ensure that an exception is thrown for invalid input data.
     * Verifies that the interactor correctly handles empty input data by throwing an exception.
     */
    @Test
    void exceptionTest() {
        // Create input data with an empty string to trigger an exception
        NarrateInputData inputData = new NarrateInputData("");

        // Mock implementation of NarrateOutputBoundary for exception scenario
        NarrateOutputBoundary successPresenter = new NarrateOutputBoundary() {
            @Override
            public void prepareView() {
                // Assert false to signify that this method should not be called in this test
                assert false;
            }
        };

        // Create the interactor with the mocked presenter
        NarrateInputBoundary interactor = new NarrateInteractor(successPresenter);

        // Assert that an IllegalArgumentException is thrown when executing with invalid input
        Exception exception = assertThrows(
                RuntimeException.class, () -> interactor.execute(inputData)
        );

        // Verify that the exception message is as expected
        assertEquals("java.lang.IllegalArgumentException: Must have text", exception.getMessage());
    }

}