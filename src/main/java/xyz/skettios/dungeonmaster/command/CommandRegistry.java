package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IGuild;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.command.impl.CommandPing;
import xyz.skettios.dungeonmaster.command.impl.CommandPrefix;
import xyz.skettios.dungeonmaster.command.impl.CommandTest;
import xyz.skettios.dungeonmaster.database.Schema;
import xyz.skettios.dungeonmaster.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry
{
    private final Map<String, ICommand> commands = new HashMap<>();
    private final String defaultPrefix;

    public CommandRegistry(String defaultPrefix)
    {
        this.defaultPrefix = defaultPrefix;

        registerCommand(new CommandPing());
        registerCommand(new CommandPrefix());
        registerCommand(new CommandTest());
    }

    private void registerCommand(ICommand command)
    {
        for (String alias : command.getAliases())
            commands.put(alias, command);
    }

    public void call(String command, CommandContext ctx)
    {
        getCommand(command).ifPresent(cmd -> call(cmd, ctx));
    }

    private void call(ICommand cmd, CommandContext ctx)
    {
        cmd.execute(ctx);
    }

    public Optional<ICommand> getCommand(String command)
    {
        return Optional.ofNullable(commands.get(command));
    }

    public String getDefaultPrefix()
    {
        return defaultPrefix;
    }

    public void resetPrefix(IGuild guild)
    {
        resetPrefix(guild.getLongID());
    }

    public void resetPrefix(long id)
    {
        try
        {
            Schema schema = DungeonMaster.getInstance().db.getSchema("dungeon_master");
            Table guild_prefixes = schema.getTable("guild_prefixes");
            Statement statement = guild_prefixes.createStatement();
            statement.executeUpdate(String.format("DELETE FROM %s WHERE ID = %d", guild_prefixes.getName(), id));
            statement.close();
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }
    }

    public void overwritePrefix(IGuild guild, String prefix)
    {
        overwritePrefix(guild.getLongID(), prefix);
    }

    public void overwritePrefix(long id, String prefix)
    {
        try
        {
            Schema schema = DungeonMaster.getInstance().db.getSchema("dungeon_master");
            Table guild_prefixes = schema.getTable("guild_prefixes");
            Statement statement = guild_prefixes.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE ID = %d;", guild_prefixes.getName(), id));
            if (!rs.next())
                statement.executeUpdate(String.format("INSERT INTO %s (ID, PREFIX) VALUES (%d, '%s');",
                        guild_prefixes.getName(), id, prefix));
            else
                statement.executeUpdate(String.format("UPDATE %s SET PREFIX='%s' WHERE ID = %d;",
                        guild_prefixes.getName(), prefix, id));
            statement.close();
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }
    }

    public Optional<String> getPrefixForGuild(IGuild guild)
    {
        return getPrefixForGuild(guild.getLongID());
    }

    public Optional<String> getPrefixForGuild(long id)
    {
        try
        {
            Schema schema = DungeonMaster.getInstance().db.getSchema("dungeon_master");
            Table guild_prefixes = schema.getTable("guild_prefixes");
            Statement statement = guild_prefixes.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s WHERE ID = %d",
                    guild_prefixes.getName(), id));
            String prefix = null;
            if (rs.next())
                prefix = rs.getString("PREFIX");
            statement.close();

            return Optional.ofNullable(prefix);
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }

        return Optional.empty();
    }

    public String getEffectivePrefix(IGuild guild)
    {
        return getEffectivePrefix(guild.getLongID());
    }

    public String getEffectivePrefix(long id)
    {
        return getPrefixForGuild(id).orElse(defaultPrefix);
    }
}
