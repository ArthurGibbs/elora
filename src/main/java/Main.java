
import Elora.Elora;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Instances instances = new Instances();
        Elora elora = new Elora(
                instances.dbClient,
                instances.pipeline,
                instances.config);

        new DiscordInterface(elora, instances.client).run();
    }

    //todo
    //fix disconnect
    //make ner work
    //change moddel to encompase multi element ners
    //maths module
    //guice



}