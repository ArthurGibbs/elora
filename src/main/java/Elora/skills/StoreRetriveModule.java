package Elora.skills;

import Elora.DAO.Data;
import Elora.Response;
import Elora.nlp.ParsedMessageModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreRetriveModule implements Skill{
    Data data;

    public StoreRetriveModule(Data data) {
        this.data = data;
    }

    @Override
    public Optional<Response> perform(ParsedMessageModel parsedMessageModel) {
        String simpleText = String.join(",", parsedMessageModel.getSimpleText());
        Optional<Response> question = whenWillXX(simpleText);
        if (question.isPresent()) {
            return question;
        } else {
            return xWillXOnX(simpleText);
        }
    }

    private Optional<Response> whenWillXX(String message) {
        Matcher m = Pattern.compile("when will (?<obj>\\S+) (?<prop>\\S+)").matcher(message);
        if (m.find( )) {
            Optional<String> value = this.data.lookupOPV(m.group(1),m.group(2));
            String result;
            if (value.isPresent()) {
                result = m.group("obj") + " will " + m.group("prop") + " on " + value.get();
            } else {
                result = "I do not know when " + m.group("obj") + " will " + m.group("prop");
            }
            List<String> remarks = Arrays.asList(result);
            return Optional.of(new Response(1,remarks));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Response> xWillXOnX(String message) {
        Matcher m = Pattern.compile("(?<obj>\\S+) will (?<prop>\\S+) on (?<value>\\S+)").matcher(message);
        if (m.find( )) {
            this.data.saveOPV(m.group("obj"),m.group("prop"),m.group("value"));
            List<String> remarks = Arrays.asList("ok");
            return Optional.of(new Response(1,remarks));
        } else {
            return Optional.empty();
        }
    }

}
