package use_case.generate;

public class GenerateInputData {

    final private String prompt;
    final private String username;

    public GenerateInputData(String prompt, String username) {
        this.prompt = prompt;
        this.username = username;
    }

    String getPrompt() {
        return prompt;
    }

    String getUsername() {
        return username;
    }

}
