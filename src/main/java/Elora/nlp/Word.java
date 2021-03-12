package Elora.nlp;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private String base;
    private String pos;
    private String lem;
    private List<NamedEntity> ner = new ArrayList<>();

    public Word(String base) {
        this.base = base;
    }

    public void addPOS(String pos) {
        this.pos = pos;
    }

    public void addLem(String lem) {
        this.lem = lem;
    }

    public void addNamedEntity(String name, double prob) {
        NamedEntity e = new NamedEntity(prob, name);
        this.ner.add(e);
    }

    public String getLemOrBase() {
        return (lem.equals("O")? base : lem);
    }

    public String toString() {
        String s = "[" + base + ":"+ pos +":"+lem+"]";
        for (NamedEntity n : ner) {
            s+= "("+ n.getName() + ":" + String.valueOf(n.getProbability()) + ")";
        }
        return s;
    }

    public String getBase() {
        return base;
    }
}


