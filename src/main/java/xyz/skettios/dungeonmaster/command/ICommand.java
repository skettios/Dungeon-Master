package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IMessage;

public interface ICommand
{
    void execute(IMessage message, String... args);
}
