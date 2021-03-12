package Elora.nlp;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;

public class Tokenizer {
    private TokenizerME tokenizer;

    public Tokenizer(String tokenBin) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(tokenBin);
        TokenizerModel model = new TokenizerModel(inputStream);
        this.tokenizer = new TokenizerME(model);
    }

    public String[] tokenize(String sentence){
        return tokenizer.tokenize(sentence);
    }
}
