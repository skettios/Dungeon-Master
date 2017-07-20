package xyz.skettios.dungeonmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import xyz.skettios.dungeonmaster.command.CommandRegistry;
import xyz.skettios.dungeonmaster.event.EventReady;

public class DungeonMaster
{
    private static DungeonMaster instance;

    private ClientBuilder clientBuilder;
    private IDiscordClient discordClient;

    public final Logger LOGGER = LoggerFactory.getLogger("Dungeon Master");

    public final CommandRegistry commandRegistry;

    private DungeonMaster()
    {
        clientBuilder = new ClientBuilder();
        commandRegistry = new CommandRegistry();
    }

    public static DungeonMaster getInstance()
    {
        if (instance == null)
            instance = new DungeonMaster();

        return instance;
    }

    public void login(String token)
    {
        clientBuilder.withToken(token);
        discordClient = clientBuilder.login();

        initialize();
    }

    private void initialize()
    {
        EventDispatcher dispatcher = discordClient.getDispatcher();
        dispatcher.registerListener(new EventReady());
    }
}
