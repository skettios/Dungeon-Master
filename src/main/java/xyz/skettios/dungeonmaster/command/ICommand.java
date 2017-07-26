package xyz.skettios.dungeonmaster.command;

import com.sun.istack.internal.NotNull;

public interface ICommand
{
    @NotNull
    String[] getAliases();

    void execute(CommandContext ctx);
}
