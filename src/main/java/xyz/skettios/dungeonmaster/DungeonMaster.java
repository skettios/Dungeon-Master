package xyz.skettios.dungeonmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import xyz.skettios.dungeonmaster.command.CommandRegistry;
import xyz.skettios.dungeonmaster.database.Database;
import xyz.skettios.dungeonmaster.database.Schema;
import xyz.skettios.dungeonmaster.event.EventReady;

public class DungeonMaster
{
    private static DungeonMaster instance;

    private ClientBuilder clientBuilder;
    private IDiscordClient discordClient;

    public final Logger LOGGER = LoggerFactory.getLogger("Dungeon Master");
    public final CommandRegistry cmdReg;
    public Database db;

    private DungeonMaster()
    {
        clientBuilder = new ClientBuilder();
        cmdReg = new CommandRegistry();
    }

    public static DungeonMaster getInstance()
    {
        if (instance == null)
            instance = new DungeonMaster();

        return instance;
    }

    public void login(String token, String dbUser, String dbPass)
    {
        clientBuilder.withToken(token);
        discordClient = clientBuilder.login();
        db = new Database(dbUser, dbPass);

        initialize();
    }

    private void initialize()
    {
        EventDispatcher dispatcher = discordClient.getDispatcher();

        Schema schema = db.addSchema("dungeon_master");
        schema.addTable("player_profiles",
                "(ID varchar(128) KEY NOT NULL, NAME varchar(128) NOT NULL, LEVEL INT NOT NULL)");

        dispatcher.registerListener(new EventReady());
    }
}
