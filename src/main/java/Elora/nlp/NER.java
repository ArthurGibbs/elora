package Elora.nlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;

import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NER {
    private List<NameFinderME> models = new ArrayList<>();

    public NER(String role,String userid) throws IOException {
        addModel("models/en-ner-person.bin");
        addModel("models/en-ner-date.bin");
        addModel("models/en-ner-time.bin");
    }

    private void addModel(String modelFile) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(modelFile);
        TokenNameFinderModel model = new TokenNameFinderModel(resourceAsStream);
        this.models.add(new NameFinderME(model));
    }

    public List<List<Span>> getNer(String[] tokens)   {
        List<List<Span>> out = new ArrayList<>();
        for (NameFinderME m : models) {
            out.add(Arrays.asList(m.find(tokens)));
        }
        return out;
    }

}


