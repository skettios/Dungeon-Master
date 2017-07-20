package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IMessage;

@CommandAlias(aliases = {"test"})
public class CommandTest implements ICommand
{
    @Override
    public void execute(IMessage message, String... args)
    {
    }
}
