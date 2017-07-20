package xyz.skettios.dungeonmaster.event;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.command.CommandRegistry;

//TODO(skettios): something better, just not this lul
public class EventCommand
{
    @EventSubscriber
    public void onCommandReceived(MessageReceivedEvent event)
    {
        String message = event.getMessage().getContent();
        if (message.regionMatches(false, 0, CommandRegistry.COMMAND_PREFIX, 0, CommandRegistry.COMMAND_PREFIX.toCharArray().length))
        {
            String[] commandSplit = message.substring(3).split(" ");
            String command = commandSplit[0];
            if (commandSplit.length >= 1)
            {
                String[] commandArgs = new String[commandSplit.length - 1];
                for (int i = 1; i <= commandArgs.length; i++)
                    commandArgs[i - 1] = commandSplit[i];

                DungeonMaster.getInstance().cmdReg.executeCommand(event.getMessage(), command, commandArgs);
            }
            else
            {
                DungeonMaster.getInstance().cmdReg.executeCommand(event.getMessage(), command);
            }
        }
    }
}
