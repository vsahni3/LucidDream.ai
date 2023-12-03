package use_case.narrate;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineModeDesc;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;


public class NarrateInteractor implements NarrateInputBoundary{

    final NarrateOutputBoundary narratePresenter;

    private static final Object lock = new Object();

    private static Synthesizer currentSynthesizer = null;

    public NarrateInteractor(NarrateOutputBoundary narratePresenter) {
        this.narratePresenter = narratePresenter;
    }

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
