import Elora.Elora;
import Elora.dto.User_DTO;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import Elora.EloraMessageEvent;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class DiscordInterface {
    Elora elora;
    DiscordClient dlclient;

    public DiscordInterface(Elora elora, DiscordClient dlclient) {
        this.elora = elora;
        this.dlclient = dlclient;
    }

    public void run() {
        while (true) {
            try {
                GatewayDiscordClient client = dlclient.login().timeout(Duration.ofSeconds(300)).block();

                handleOnReadyEvent(client);
                handleMessage(elora, client);

                client.onDisconnect().block();
                System.out.println("Disconnected!");
            }
            catch (Exception e) {         System.out.println("Ignoring Exception and restarting");}
            finally {                     System.out.println("Restarting discord interface");}
        }
    }

    private void handleOnReadyEvent(GatewayDiscordClient client) {
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    final User self = event.getSelf();
                    System.out.println(String.format(
                            "Logged in as %s#%s", self.getUsername(), self.getDiscriminator()
                    ));
                });
    }

    private void handleMessage(Elora elora, GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    if (!event.getMessage().getAuthor().get().isBot()) {
                        User_DTO sender = new User_DTO(event.getMessage().getAuthor().get().getUsername());
                        EloraMessageEvent eloraMessageEvent = new EloraMessageEvent(
                                event.getMessage().getContent(),
                                sender);

                        Optional<List<String>> resonses = elora.handleMessageEvent(eloraMessageEvent);
                        if (resonses.isPresent()) {
                            for (String response : resonses.get()) {
                                event.getMessage().getChannel().block().createMessage(response).subscribe();
                            }
                        }
                    }
                });
    }
}
