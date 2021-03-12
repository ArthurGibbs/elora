package Elora.nlp;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

import java.io.IOException;
import java.io.InputStream;

public class Chunker {
    private ChunkerME chunker;
    public Chunker() throws IOException {
        InputStream inputStreamChunker = getClass().getClassLoader().getResourceAsStream("models/en-chunker.bin");
        ChunkerModel chunkerModel = new ChunkerModel(inputStreamChunker);
        this.chunker = new ChunkerME(chunkerModel);
    }

    public String[] chunk(String[] tokens, String[] tags) {
        return chunker.chunk(tokens, tags);
    }
}
