package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IMessage;

public interface ICommand
{
    //TODO(skettios): args should be vararg of Object
    void execute(IMessage message, String... args);
}
