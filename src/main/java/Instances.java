import Elora.Config;
import Elora.DBClient;
import Elora.NLPPipeline;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import Elora.nlp.*;

import java.io.IOException;

public class Instances {
    public Config config;
    public DBClient dbClient;
    public DiscordClient client;
    public NLPPipeline pipeline;


    public Sentencer sentencer;
    public Tokenizer tokenizer;
    public NER ner;
    public POS pos;
    public Lemitizer lem;
    public Chunker chunker;

    public Instances() throws IOException {
        this.config = new Config();

        this.dbClient = new DBClient(
                config.getString("db.host"),
                config.getString("db.port"),
                config.getString("db.schema"),
                config.getString("db.user"),
                config.getString("db.password"));

        this.client = DiscordClientBuilder.create(config.getString("token")).build();

        this.sentencer = new Sentencer(config.getString("sent_bin"));
        this.tokenizer = new Tokenizer(config.getString("tokenBin"));
        this.ner = new NER(this.config.getString("role"),this.config.getString("userid"));
        this.pos = new POS();
        this.lem = new Lemitizer();
        this.chunker = new Chunker();

        this.pipeline =  new NLPPipeline(sentencer,tokenizer,ner,pos, lem, chunker, config);
    }
}
