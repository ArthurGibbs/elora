package Elora.nlp;

public class NamedEntity {
    private double probability;
    private String name;

    public NamedEntity(double probability, String name) {
        this.probability = probability;
        this.name = name;
    }

    public double getProbability() {
        return probability;
    }

    public String getName() {
        return name;
    }
}