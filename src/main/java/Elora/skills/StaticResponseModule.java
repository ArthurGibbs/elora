package Elora.skills;

import Elora.Response;
import Elora.WeightComparitor;
import Elora.nlp.ParsedMessageModel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticResponseModule implements Skill{
    List<Pair> patterns = new ArrayList<>();

    public StaticResponseModule() {
        patterns.add(new Pair( Pattern.compile("hello"), "Hello ::SENDER::", (float) 0.2));
        patterns.add(new Pair( Pattern.compile("hell."), "Hello ::SENDER::", (float) 0.1));

        patterns.add(new Pair( Pattern.compile("help"),
                " currently I can cope with the current forms of message."+
                "when will (?<obj>\\\\S+) (?<prop>\\\\S+)\"" +
                "\"(?<obj>\\\\S+) will (?<prop>\\\\S+) on (?<value>\\\\S+)\"", (float) 0));
    }

    @Override
    public Optional<Response> perform(ParsedMessageModel parsedMessageModel) {
        List<Response> responses = new ArrayList<Response>();
        for (Pair p : patterns) {
            Matcher matcher = p.getPattern().matcher(String.join(",",parsedMessageModel.getSimpleText()).trim());
            if (matcher.find()) {
                String result = p.getReplacement();
                result = result.replaceAll("::SENDER::", parsedMessageModel.getSender().getUsername());
                List<String> remarks = Arrays.asList(result);
                responses.add(new Response(p.getWeight(),remarks));
            }
        }
        Collections.sort(responses, new WeightComparitor());
        if (responses.size() > 0) {
            return Optional.of(responses.get(0));
        }
        return Optional.empty();
    }

    class Pair {
        private Pattern pattern;
        private String replacement;
        private Float weight;

        public Pair(Pattern p, String replacement, Float weight) {
            this.pattern = p;
            this.replacement = replacement;
            this.weight = weight;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String getReplacement() {
            return replacement;
        }

        public Float getWeight() {
            return weight;
        }
    }
}


