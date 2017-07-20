package xyz.skettios.dungeonmaster.event;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.member.NicknameChangedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.IUser;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.database.Schema;
import xyz.skettios.dungeonmaster.database.Table;

import java.sql.SQLException;
import java.sql.Statement;

public class EventGuild
{
    @EventSubscriber
    public void onBotJoin(GuildCreateEvent event)
    {
        try
        {
            Schema schema = DungeonMaster.getInstance().db.getSchema("dungeon_master");
            Table player_profiles = schema.getTable("player_profiles");
            Table player_stats = schema.getTable("player_stats");
            Statement statement = schema.createStatement();
            for (IUser user : event.getGuild().getUsers())
            {
                if (user.isBot())
                    continue;

                statement.executeUpdate(String.format("INSERT IGNORE INTO %s (ID, NAME, LEVEL) VALUES ('%s', '%s', 1);",
                        player_profiles.getName(), user.getStringID(), user.getName()));
                statement.executeUpdate(String.format("INSERT IGNORE INTO %s (ID, HP, MP, STR, DEX, INTEL, PDEF, MDEF, " +
                        "MND, SPD, LUK) VALUES ('%s', %d, %d, %d, %d, %d, %d, %d, %d, %d, %d)",
                        player_stats.getName(), user.getStringID(), 500, 100, 5, 5, 5, 5, 5, 10, 10, 5, 5));
            }
            statement.close();
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }
    }

    @EventSubscriber
    public void onUserJoin(UserJoinEvent event)
    {
    }

    @EventSubscriber
    public void onNicknameChange(NicknameChangedEvent event)
    {
    }
}
