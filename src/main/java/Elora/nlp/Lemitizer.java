package Elora.nlp;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;

import java.io.IOException;
import java.io.InputStream;

public class Lemitizer {
    private DictionaryLemmatizer lemmatizer;

    public Lemitizer() throws IOException {
        InputStream dictLemmatizer = getClass().getClassLoader().getResourceAsStream("models/en-lemmatizer.dict");
        this.lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
    }

    public String[] lemmatize(String[] tokens, String[] tags) {
       return this.lemmatizer.lemmatize(tokens, tags);
    }
}
