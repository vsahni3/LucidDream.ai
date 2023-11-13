package use_case.generate;

import entity.User;

public class GenerateInteractor implements GenerateInputBoundary {
    final GenerateUserDataAccessInterface userDataAccessObject;
    final GenerateOutputBoundary loginPresenter;

    public GenerateInteractor(GenerateUserDataAccessInterface userDataAccessInterface,
                              GenerateOutputBoundary generateOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = generateOutputBoundary;
    }


    @Override
    public void execute(GenerateInputData generateInputData) {
        String username = generateInputData.getPrompt();
        String password = generateInputData.getUsername();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView("username error", username + ": Account does not exist.");
        } else {
            String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("password error", "Incorrect password for " + username + ".");
            } else {

                User user = userDataAccessObject.get(generateInputData.getPrompt());

                GenerateOutputData generateOutputData = new GenerateOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(generateOutputData);
            }
        }
    }
}
