package use_case.login;

/**
 * The LoginInputBoundary interface defines the contract for handling login operations.
 * It acts as an entry point for the login usecase, encapsulating the logic for processing
 * login requests. Implementations of this interface are responsible for taking login-related
 * input data and executing the necessary steps to perform a login operation.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login process with the provided login input data.
     * This method is responsible for taking the login input data, which includes necessary
     * user credentials or other relevant information, and processing it to complete the login
     * operation. The specific implementation of this method will determine the validation,
     * authentication, and any other steps required for the login process.
     *
     * @param loginInputData An instance of LoginInputData containing the necessary information
     *                       for processing- i.e username and password.
     */
    void execute(LoginInputData loginInputData);
}
