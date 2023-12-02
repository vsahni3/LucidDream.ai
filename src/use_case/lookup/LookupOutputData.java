package use_case.lookup;



/**
 * The LookupOutputData class serves as a data transfer object for conveying the results of a word lookup operation.
 * This class encapsulates the result of a word lookup, primarily the definition of the word that was looked up.
 * It is designed to be used in use cases where the result of a word lookup needs to be transferred or processed further,
 * providing a clean and encapsulated way of handling the lookup result.
 *
 * The class contains a single private final field 'definition', which holds the definition of the word.
 * It includes a constructor for initializing this field and a public getter method for accessing the definition.
 */
public class LookupOutputData {

    private final String definition;

    /**
     * Constructs a new LookupOutputData object with the specified definition.
     * @param definition the definition of the word as a result of the lookup operation.
     */
    public LookupOutputData(String definition) {
        this.definition = definition;
    }

    /**
     * Retrieves the definition associated with this LookupOutputData.
     * @return the definition of the word.
     */
    public String getDefinition() {
        return this.definition;
    }
}
