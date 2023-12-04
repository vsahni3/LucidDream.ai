package use_case.login;

import data_access.InMemoryDAO;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("bob", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryDAO();

        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "password");
        userRepository.save(user);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {

            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("bob", user.getUsername());
                assertEquals("password", userRepository.getUser(user.getUsername()).getPassword());
                assertTrue(userRepository.existsUser("bob"));
            }

            @Override
            public void prepareFailView(String errorType, String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureInvalidPasswordTest() {
        LoginInputData inputData = new LoginInputData("bob", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryDAO();

        UserFactory factory = new CommonUserFactory();
        User user = factory.create("bob", "pwd");
        userRepository.save(user);

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {

            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorType, String error) {
                assertEquals("password error", errorType);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("bob", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryDAO();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {

            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorType, String error) {
                assertEquals("username error", errorType);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

}