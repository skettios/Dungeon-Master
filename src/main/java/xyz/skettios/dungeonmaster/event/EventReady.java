package xyz.skettios.dungeonmaster.event;

import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.util.DatabaseHelper;

public class EventReady
{
    @EventSubscriber
    public void onReady(ReadyEvent event)
    {
        DungeonMaster.getInstance().commandRegistry.initialize();

        EventDispatcher dispatcher = event.getClient().getDispatcher();
        dispatcher.registerListener(new EventCommand());
        dispatcher.registerListener(new EventGuild());

        DatabaseHelper.createDatabase(DatabaseHelper.DB);

        DungeonMaster.getInstance().LOGGER.info("Bot Ready!");
    }
}
