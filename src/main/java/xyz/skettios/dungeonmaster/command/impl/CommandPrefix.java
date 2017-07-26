package xyz.skettios.dungeonmaster.command.impl;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.command.CommandContext;
import xyz.skettios.dungeonmaster.command.ICommand;

public class CommandPrefix implements ICommand
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"set_prefix", "reset_prefix"};
    }

    @Override
    public void execute(CommandContext ctx)
    {
        IGuild guild = ctx.getGuild();
        IUser user = ctx.getAuthor();

        if (ctx.getCommand().equals("set_prefix"))
        {
            DungeonMaster.getInstance().CMD_REG.overwritePrefix(guild, ctx.getArgs().get(0));
            ctx.getChannel().sendMessage(String.format("%s has changed the prefix to %s!",
                    user.mention(), ctx.getArgs().get(0)));
        }
        else if (ctx.getCommand().equals("reset_prefix"))
        {
            DungeonMaster.getInstance().CMD_REG.resetPrefix(guild);
            ctx.getChannel().sendMessage(String.format("%s has reset the prefix to %s", user.mention(),
                    DungeonMaster.getInstance().CMD_REG.getDefaultPrefix()));
        }
    }
}
