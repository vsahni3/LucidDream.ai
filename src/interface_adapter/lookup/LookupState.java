package interface_adapter.lookup;

import jdk.dynalink.linker.support.Lookup;

public class LookupState {

    private String error;

    private String definition = "";
    private String word = "";

    public LookupState(LookupState copy) {
        definition = copy.definition;
        word = copy.word;
        error = copy.error;
    }

    public LookupState() {}

    public void clearState() {
        definition = "";
        error = null;
        word = "";
    }

    public String getDefinition() { return definition; }

    public void setDefinition(String definition) { this.definition = definition; }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

}
