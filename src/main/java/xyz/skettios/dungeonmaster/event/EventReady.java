package xyz.skettios.dungeonmaster.event;

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

            DungeonMaster.getInstance().LOGGER.info("Bot Ready!");
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }
    }
}
