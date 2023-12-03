package use_case.lookup;

/**
 * The LookupOutputBoundary interface defines the contract for classes that handle the presentation or output side of word lookup operations.
 * It is a part of the system's boundary layer and serves as a receiver for the results obtained from the LookupInteractor.
 * Classes implementing this interface are responsible for processing the results of word lookups and preparing the data for presentation to the user interface.
 *
 * Key functionalities include:
 * - Receiving lookup results encapsulated in LookupOutputData.
 * - Preparing the data for display or further processing, such as rendering it in a view or passing it to another system component.
 *
 * This interface is designed to be implemented by presenters or similar components that deal with the application's user interface or output logic, ensuring a clean separation of concerns between data fetching and data presentation.
 */
public interface LookupOutputBoundary {

    /**
     * Prepares the view or response for a successful word lookup operation.
     * Implementing classes should define how the lookup results, encapsulated in LookupOutputData, are processed and presented.
     *
     * @param lookupOutputData The LookupOutputData object containing the result of the word lookup operation.
     */
    void prepareSuccessView(LookupOutputData lookupOutputData);
    void prepareFailView(String errorMessage);
}
