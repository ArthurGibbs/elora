package Elora.nlp;


import Elora.dto.User_DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParsedMessageModel {
    User_DTO sender;
    List<List<List<Word>>> digest;

    public ParsedMessageModel(List<List<List<Word>>> digest, User_DTO sender) {
        this.digest = digest;
        this.sender = sender;
    }

    public List<String> getSimpleText(){
        List<String> simpleText = new ArrayList<>();
        for (List<List<Word>> sentence : this.digest) {
            List<String> sentenceresponse = new ArrayList<>();
            for (List<Word> group : sentence) {
                List<String> collect = group.stream().map(w -> w.getLemOrBase()).collect(Collectors.toList());
                sentenceresponse.add(String.join(" ", collect));
            }
            simpleText.add(String.join(" ", sentenceresponse));
        }
        return simpleText;
    }

    public User_DTO getSender() {
        return sender;
    }

    public List<List<List<Word>>> getDigest() {
        return digest;
    }
}
