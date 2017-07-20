package xyz.skettios.dungeonmaster.event;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.member.NicknameChangedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.IUser;
import xyz.skettios.dungeonmaster.util.DatabaseHelper;

public class EventGuild
{
    @EventSubscriber
    public void onBotJoin(GuildCreateEvent event)
    {
        DatabaseHelper.createTable(DatabaseHelper.DB, event.getGuild().getStringID(),
                "(" +
                        "ID varchar(128) NOT NULL KEY, " +
                        "NAME varchar(128) NOT NULL," +
                        "LEVEL INT NOT NULL," +
                        "TOTAL_EXP INT NOT NULL" +
                        ");");
        for (IUser user : event.getGuild().getUsers())
        {
            if (user.isBot())
                continue;
            DatabaseHelper.executeUpdate(DatabaseHelper.DB,
                    String.format("INSERT INTO guild_%s " +
                            "(ID, NAME, LEVEL, TOTAL_EXP)" +
                            "VALUES ('%s', '%s', 1, 0);", event.getGuild().getStringID(), user.getStringID(), user.getDisplayName(event.getGuild())));
        }
    }

    @EventSubscriber
    public void onUserJoin(UserJoinEvent event)
    {
        DatabaseHelper.executeUpdate(DatabaseHelper.DB,
                String.format("INSERT INTO IF NOT EXISTS guild_%s " +
                        "(ID, NAME, LEVEL, TOTAL_EXP)" +
                        "VALUES ('%s', '%s', 1, 0);", event.getGuild().getStringID(), event.getUser().getStringID(), event.getUser().getDisplayName(event.getGuild())));
    }

    @EventSubscriber
    public void onNicknameChange(NicknameChangedEvent event)
    {
        DatabaseHelper.executeUpdate(DatabaseHelper.DB,
                String.format("UPDATE guild_%s SET NAME = '%s' WHERE ID = '%s';",
                        event.getGuild().getStringID(), event.getNewNickname().isPresent() ? event.getNewNickname().get() : event.getUser().getName(), event.getUser().getStringID()));
    }
}
