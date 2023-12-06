package use_case.narrate;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineModeDesc;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;


/**
 *
 */
public class NarrateInteractor implements NarrateInputBoundary{

    final NarrateOutputBoundary narratePresenter;

    private static final Object lock = new Object();

    private static Synthesizer currentSynthesizer = null;

    /**
     *
     * @param narratePresenter
     */
    public NarrateInteractor(NarrateOutputBoundary narratePresenter) {
        this.narratePresenter = narratePresenter;
    }


    /**
     * Executes the narration operation with the provided input data.
     * This method initiates text-to-speech conversion and updates the view through the presenter.
     *
     * @param narrateInputData The input data containing the text to be narrated.
     */
    @Override
    public void execute(NarrateInputData narrateInputData) {
        String text = narrateInputData.getText();

        try {
            speak(text);
        } catch (EngineException | AudioException | InterruptedException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        narratePresenter.prepareView();

    }

    /**
     * Retrieves a singleton instance of the Synthesizer.
     * This method initializes the Synthesizer on its first call and returns the same instance for subsequent calls.
     * It uses double-checked locking to ensure that the Synthesizer is initialized only once in a thread-safe manner.
     *
     * @return The singleton Synthesizer instance.
     * @throws EngineException If there is an error initializing the speech synthesis engine.
     */
    private static Synthesizer getInstance() throws EngineException {
        if (currentSynthesizer == null) {
            synchronized (lock) {
                if (currentSynthesizer == null) {
                    System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                    Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
                    EngineModeDesc modeDesc = new SynthesizerModeDesc(Locale.US);
                    currentSynthesizer = Central.createSynthesizer(modeDesc);
                    currentSynthesizer.allocate();
                }
            }
        }
        return currentSynthesizer;
    }
    /**
     * Converts the given text to speech using a text-to-speech synthesizer.
     * Throws an exception if the input text is empty or if any speech synthesis error occurs.
     *
     * @param text The text to be converted to speech.
     * @throws EngineException if there is a problem with the speech engine.
     * @throws AudioException if there is an audio error during speech synthesis.
     * @throws InterruptedException if the speech synthesis thread is interrupted.
     * @throws IllegalArgumentException if the input text is empty.
     */
    private void speak(String text) throws EngineException, AudioException, InterruptedException, IllegalArgumentException {

        if (text.isEmpty()) {
            throw new IllegalArgumentException("Must have text");
        }

        Synthesizer synthesizer = getInstance();
        synthesizer.resume();

        synthesizer.speakPlainText(text, null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
    }
}
