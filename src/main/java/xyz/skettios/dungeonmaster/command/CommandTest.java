package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@CommandAlias(aliases = {"test"})
public class CommandTest implements ICommand
{
    @Override
    public void execute(IMessage message, String... args)
    {
        try
        {
            if (args.length <= 0)
                return;

            IChannel channel = message.getChannel();
            IUser user = message.getAuthor();
            Table player_profiles = DungeonMaster.getInstance().db.getSchema("dungeon_master").getTable("player_profiles");
            Table player_stats = DungeonMaster.getInstance().db.getSchema("dungeon_master").getTable("player_stats");
            Statement statement = player_profiles.createStatement();

            ResultSet set = statement.executeQuery(String.format("SELECT * FROM %s WHERE ID = '%s';",
                    player_stats.getName(), user.getStringID()));

            switch (args[0])
            {
                case "level":
                    ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE ID = '%s';",
                            player_profiles.getName(), user.getStringID()));
                    rs.next();

                    channel.sendMessage(user.mention() + " your level is " + rs.getInt("LEVEL"));
                    break;
                case "hp":
                    if (set.next())
                    {
                        channel.sendMessage(user.mention() + " your hp is " + set.getInt("HP"));
                    }
                case "mp":
                    if (set.next())
                    {
                        channel.sendMessage(user.mention() + " your mp is " + set.getInt("MP"));
                    }
                default:
                    break;
            }
            statement.close();
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }
    }
}
