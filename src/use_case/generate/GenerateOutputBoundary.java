package use_case.generate;

public interface GenerateOutputBoundary {
    void prepareSuccessView(GenerateOutputData user);

    void prepareFailView(String errorType, String error);
}
