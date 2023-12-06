package use_case.signup;


/**
 * Encapsulates the output data from a successful signup process.
 * This class holds essential information that is generated as a result of a successful user signup.
 * It's used to pass this data to the components
 * responsible for handling the post-signup process, such as generating a success message or updating UI elements.
 */
public class SignupOutputData {

    private final String username;

    /**
     * Constructs a new instance of SignupOutputData with the specified username.
     * This constructor initializes the data object with the username of the newly registered user.
     *
     * @param username The username of the user who has successfully signed up.
     */
    public SignupOutputData(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username of the user.
     * This method returns the username associated with the successful signup.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

}
