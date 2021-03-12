package Elora.nlp;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Sentencer {
    private SentenceDetectorME sdetector;

    public Sentencer(String SENT_BIN) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(SENT_BIN);
        SentenceModel model = new SentenceModel(is);

        this.sdetector = new SentenceDetectorME(model);
    }

    public List<String> sentenceDetect(String message) {
        return Arrays.asList(sdetector.sentDetect(message));
    }
}
