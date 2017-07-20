package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageTokenizer;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            Table table = DungeonMaster.getInstance().db.getSchema("dungeon_master").getTable("player_profiles");
            Statement statement = table.createStatement();

            switch (args[0])
            {
                case "level":
                    if (args[1] != null)
                    {
                        List<IUser> at = message.getGuild().getUsersByName(args[1]);
                        ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE ID = '%s';",
                                table.getName(), at.get(0).getStringID()));
                        rs.next();

                        channel.sendMessage(args[1] + " your level is " + rs.getInt("LEVEL"));
                    }
                    break;
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
