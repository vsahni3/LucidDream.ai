package use_case.lookup;
/**
 * The LookupInputData class serves as a data transfer object for word lookup operations.
 * It encapsulates the data required for performing word lookup in the system, primarily the word to be looked up.
 * This class is designed to be used in use cases where a word needs to be searched or analyzed,
 * providing a clean and simple interface for inputting the necessary data.
 *
 * The class contains a single private final field 'word', which represents the word to be looked up.
 * It includes a constructor to initialize this field and a public getter method to access the word.
 *
 * Key functionality:
 * - Encapsulation of the word lookup input data.
 * - Providing an immutable representation of the input word, ensuring data consistency and thread safety.
 */
public class LookupInputData {

    final private String word;

    /**
     * Constructs a new LookupInputData object with the specified word.
     * @param word the word to be looked up or analyzed in the system.
     */
    public LookupInputData(String word) {
        this.word = word;
    }
    /**
     * Retrieves the word associated with this LookupInputData.
     * @return the word to be looked up.
     */
    public String getWord() {
        return word;
    }
}
