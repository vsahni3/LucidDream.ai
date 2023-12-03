package use_case.narrate;

/**
 * Data class representing the input data for the narrate use case.
 * This class holds the necessary information required to perform a narration operation.
 */
public class NarrateInputData {
    private final String text;

    /**
     * Constructs a new instance of NarrateInputData with the specified text.
     *
     * @param text The text that needs to be narrated.
     */
    public NarrateInputData(String text) {
        this.text = text;
    }

    String getText() {return text;}
}
