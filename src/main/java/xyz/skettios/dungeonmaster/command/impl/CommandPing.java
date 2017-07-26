package xyz.skettios.dungeonmaster.command.impl;

import xyz.skettios.dungeonmaster.command.CommandContext;
import xyz.skettios.dungeonmaster.command.ICommand;

public class CommandPing implements ICommand
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"ping"};
    }

    @Override
    public void execute(CommandContext ctx)
    {
        ctx.getChannel().sendMessage("Pong!");
    }
}
