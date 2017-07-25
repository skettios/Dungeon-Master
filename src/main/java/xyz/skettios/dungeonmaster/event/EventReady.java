package xyz.skettios.dungeonmaster.event;

import com.darichey.discord.Command;
import com.darichey.discord.api.Command;
import com.darichey.discord.api.CommandRegistry;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import xyz.skettios.dungeonmaster.DungeonMaster;

import java.sql.SQLException;

public class EventReady
{
    @EventSubscriber
    public void onReady(ReadyEvent event)
    {
        try
        {
            DungeonMaster.getInstance().cmdReg.initialize();

            DungeonMaster.getInstance().db.build();

            EventDispatcher dispatcher = event.getClient().getDispatcher();
            dispatcher.registerListener(new EventCommand());
            dispatcher.registerListener(new EventGuild());

            CommandRegistry.getForClient(event.getClient()).setPrefix("dm!");
            CommandRegistry.getForClient(event.getClient()).register(Command.builder())
/*
            Schema schema = DungeonMaster.getInstance().db.getSchema("dungeon_master");
            Table player_profiles = schema.getTable("player_profiles");
            Table player_stats = schema.getTable("player_stats");
            Statement statement = schema.createStatement();
            for (IGuild guild : event.getClient().getGuilds())
            {
                for (IUser user : guild.getUsers())
                {
                    if (user.isBot())
                        continue;

                    statement.executeUpdate(String.format("INSERT IGNORE INTO %s (ID, NAME, LEVEL) VALUES ('%s', %s, 1);",
                            player_profiles.getName(), user.getStringID(), user.getName()));
                    statement.executeUpdate(String.format("INSERT IGNORE INTO %s (ID, HP, MP, STR, DEX, INTEL, PDEF, MDEF, " +
                                    "MND, SPD, LUK) VALUES ('%s', %d, %d, %d, %d, %d, %d, %d, %d, %d, %d)",
                            player_stats.getName(), user.getStringID(), 500, 100, 5, 5, 5, 5, 5, 10, 10, 5, 5));

                }
            }
            statement.close();
*/
            DungeonMaster.getInstance().LOGGER.info("Bot Ready!");
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }
    }
}
