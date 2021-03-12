package Elora;

import Elora.dto.User_DTO;
import Elora.nlp.*;
import opennlp.tools.util.Span;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NLPPipeline {
    Sentencer sentencer;
    Tokenizer tokenizer;
    NER ner;
    POS pos;
    Lemitizer lem;
    Chunker chunker;
    Config config;

    public NLPPipeline(Sentencer sentencer, Tokenizer tokenizer, NER ner, POS pos, Lemitizer lem, Chunker chunker, Config config) {
        this.sentencer = sentencer;
        this.tokenizer = tokenizer;
        this.ner = ner;
        this.pos = pos;
        this.lem = lem;
        this.chunker = chunker;
        this.config = config;
    }

    public ParsedMessageModel process(String message, User_DTO sender) {
        List<List<List<Word>>> digest = new ArrayList<>();

        message = message.replaceAll("<@!809791077132271616>", "SELF");
        message = message.replaceAll("<@&809794330486243350>", "SELF");

        List<String> sentences = this.sentencer.sentenceDetect(message);
        for (String sentence : sentences) {

            String[] tokens = this.tokenizer.tokenize(sentence);
            List<Word> words = Arrays.asList(tokens).stream().map(t -> new Word(t)).collect(Collectors.toList());

            List<List<Span>> allspans = this.ner.getNer(tokens);
            for (List<Span> spans : allspans) {
                for (Span s : spans) {
                    for (int i = s.getStart(); i < s.getEnd(); i++) {
                        Word word = words.get(i);
                        word.addNamedEntity(s.getType(), s.getProb());

                    }
                }
            }

            String[] tags = pos.tag(tokens);
            for(int i = 0; i < tags.length; i++) {
                words.get(i).addPOS(tags[i]);
            }

            String[] lems = lem.lemmatize(tokens, tags);
            for(int i = 0; i < lems.length; i++) {
                words.get(i).addLem(lems[i]);
            }

            String[] chunks = this.chunker.chunk(tokens,tags);

            List<List<Word>> chunkedWords = new ArrayList<>();

            int chunkIndex = 0;
            List<Word> chunkGroup = new ArrayList<>();
            for (String chunk : chunks) {
               if (chunk.startsWith("B")) {
                   if (chunkGroup.size()>0) {
                       chunkedWords.add(chunkGroup);
                       chunkGroup = new ArrayList<>();
                   }
                   chunkGroup.add(words.get(chunkIndex));
               } else if(chunk.startsWith("I")) {
                   chunkGroup.add(words.get(chunkIndex));
               } else {
                   if (chunkGroup.size()>0) {
                       chunkedWords.add(chunkGroup);
                       chunkGroup = new ArrayList<>();
                   }
                   chunkedWords.add(Arrays.asList(words.get(chunkIndex)));
               }
               chunkIndex++;
            }
            if (chunkGroup.size()>0) {
                chunkedWords.add(chunkGroup);
            }

            //remove first name
            //digest.get(0).get(0).remove(0);

           digest.add(chunkedWords);

        }
        return new ParsedMessageModel(digest, sender);
    }
}
