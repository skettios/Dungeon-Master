package xyz.skettios.dungeonmaster.command.impl;

import sx.blah.discord.handle.obj.IUser;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.command.CommandContext;
import xyz.skettios.dungeonmaster.command.ICommand;
import xyz.skettios.dungeonmaster.database.Schema;
import xyz.skettios.dungeonmaster.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandTest implements ICommand
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"level", "hp", "mp"};
    }

    @Override
    public void execute(CommandContext ctx)
    {
        try
        {
            IUser user = ctx.getAuthor();

            Schema schema = DungeonMaster.getInstance().db.getSchema("dungeon_master");
            Table player_profiles = schema.getTable("player_profiles");
            Table player_stats = schema.getTable("player_stats");
            Statement statement_profiles = player_profiles.createStatement();
            Statement statement_stats = player_stats.createStatement();
            ResultSet rs_profile = statement_profiles.executeQuery(String.format("SELECT * FROM %s WHERE ID = %d;",
                    player_profiles.getName(), user.getLongID()));
            ResultSet rs_stats = statement_stats.executeQuery(String.format("SELECT * FROM %s WHERE ID = %d;",
                    player_stats.getName(), user.getLongID()));

            if (rs_profile.next() && rs_stats.next())
            {
                switch (ctx.getCommand())
                {
                    case "level":
                        ctx.getChannel().sendMessage(String.format("%s, your level is %d.",
                                user.mention(), rs_profile.getInt("LEVEL")));
                        break;
                    case "hp":
                        ctx.getChannel().sendMessage(String.format("%s, your hp is %d.",
                                user.mention(), rs_stats.getInt("HP")));
                        break;
                    case "mp":
                        ctx.getChannel().sendMessage(String.format("%s, your mp is %d.",
                                user.mention(), rs_stats.getInt("MP")));
                        break;
                    default:
                        break;
                }
            }

            statement_profiles.close();
            statement_stats.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
