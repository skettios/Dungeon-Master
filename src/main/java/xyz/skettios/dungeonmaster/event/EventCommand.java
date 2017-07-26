package xyz.skettios.dungeonmaster.event;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import xyz.skettios.dungeonmaster.command.CommandContext;
import xyz.skettios.dungeonmaster.command.CommandRegistry;

public class EventCommand
{
    private final CommandRegistry registry;

    public EventCommand(CommandRegistry registry)
    {
        this.registry = registry;
    }

    @EventSubscriber
    public void onCommandReceived(MessageReceivedEvent event)
    {
        String content = event.getMessage().getContent();
        String prefix = registry.getEffectivePrefix(event.getGuild().getLongID());

        if (content.startsWith(prefix))
        {
            String prefixRemoved = content.substring(prefix.length());
            if (prefixRemoved.isEmpty())
                return;

            int spaceIndex = prefixRemoved.indexOf(" ");
            int subIndex = spaceIndex == -1 ? prefixRemoved.length() : spaceIndex;
            String command = prefixRemoved.substring(0, subIndex);
            String args = command.length() == prefixRemoved.length() ? "" : prefixRemoved.substring(command.length() + 1);
            registry.call(command, new CommandContext(event, command, args));
        }
    }
}
