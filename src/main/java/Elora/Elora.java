package Elora;

import Elora.DAO.Data;
import Elora.nlp.*;
import Elora.skills.*;

import java.util.*;

public class Elora {
    DBClient dbClient;
    NLPPipeline nlpPipeline;
    Config config;
    Data data;
    List<Skill> skills = new ArrayList<>();

    public Elora(DBClient dbClient, NLPPipeline pipeline, Config config) {
        this.dbClient = dbClient;
        this.nlpPipeline = pipeline;
        this.config = config;
        this.data = new Data(dbClient);
        skills.add(new StoreRetriveModule(this.data));
        skills.add(new StaticResponseModule());
    }

    public Optional<List<String>> handleMessageEvent(EloraMessageEvent event) {
        String message = event.getMessage();
        boolean role = message.contains("<@&"+this.config.getString("role")+">");
        boolean tag = message.contains("<@!"+this.config.getString("userid")+">");
        if (role || tag) {
            if (role) {
                message = message.replace("<@&"+this.config.getString("role")+">", "");
            }
            if (tag) {
                message = message.replace("<@!"+this.config.getString("userid")+">", "");
            }
            return attemptResponse(event);
        }
        return Optional.empty();
    }

    private Optional<List<String>> attemptResponse(EloraMessageEvent event) {
        ParsedMessageModel parsedMessage = this.nlpPipeline.process(event.getMessage(),event.getSender());

        List<Response> responses = new ArrayList<Response>();
        for(Skill skill : skills) {
            Optional<Response> response = skill.perform(parsedMessage);
            //todo pick best weight
            if (response.isPresent()) {
                responses.add(response.get());
            }
        }

        Collections.sort(responses, new WeightComparitor());
        if (responses.size() > 0) {
            return Optional.of(responses.get(0).remarks);
        }

        return Optional.empty();
    }


}
