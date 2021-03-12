package Elora.nlp;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;

import java.io.IOException;
import java.io.InputStream;

public class POS {
    private POSTaggerME posTagger;

//    NN – noun, singular or mass
//    DT – determiner
//    VB – verb, base form
//    VBD – verb, past tense
//    VBZ – verb, third person singular present
//    IN – preposition or subordinating conjunction
//    NNP – proper noun, singular
//    TO – the word “to”
//    JJ – adjective

    public POS() throws IOException {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        InputStream inputStreamPOSTagger = getClass().getClassLoader().getResourceAsStream("models/en-pos-perceptron.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        this.posTagger = new POSTaggerME(posModel);
    }

    public String[] tag(String[] tokens) {
        return posTagger.tag(tokens);
    }
}
