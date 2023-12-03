package use_case.lookup;
/**
 * The LookupInputBoundary interface defines the contract for classes that handle word lookup use cases.
 * This interface is part of the system's boundary layer and serves as a gateway for initiating word lookup processes.
 * Implementers of this interface are responsible for taking the input data for word lookup and executing the necessary logic.
 *
 * Key functionalities include:
 * - Serving as an entry point for word lookup use cases in the system.
 * - Providing a method to execute the lookup process with the given LookupInputData.
 *
 * This interface is designed to be implemented by classes that perform the actual lookup logic, ensuring
 * that they conform to a standard method of accepting input data for word lookup operations.
 */
public interface LookupInputBoundary {

    /**
     * Executes the word lookup process using the provided LookupInputData.
     * Implementing classes should define the specific logic to perform the lookup operation based on the given data.
     *
     * @param lookupInputData The LookupInputData object containing the word to be looked up.
     */
    void execute(LookupInputData lookupInputData);
}
