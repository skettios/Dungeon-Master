package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IMessage;
import xyz.skettios.dungeonmaster.DungeonMaster;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry
{
    public static final String COMMAND_PREFIX = "dm!";

    private Map<String, ICommand> commandMap = new HashMap<>();

    public void initialize()
    {
        registerCommand(new CommandTest());
        registerCommand(new CommandHelp());
    }

    public void executeCommand(IMessage message, String command, String... args)
    {
        if (commandMap.containsKey(command))
            commandMap.get(command).execute(message, args);
    }

    private void registerCommand(ICommand command)
    {
        CommandAlias ca = command.getClass().getAnnotation(CommandAlias.class);
        for (String alias : ca.aliases())
        {
            if (commandMap.containsKey(alias))
            {
                DungeonMaster.getInstance().LOGGER.warn("Alias conflict on {}!", alias);
                continue;
            }

            commandMap.put(alias, command);
        }
    }
}
