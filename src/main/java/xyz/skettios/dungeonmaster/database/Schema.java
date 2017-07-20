package xyz.skettios.dungeonmaster.database;

import xyz.skettios.dungeonmaster.DungeonMaster;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema
{
    private Database owner;
    private String name;

    private Map<String, Table> tables;

    Schema(Database owner, String name)
    {
        this.owner = owner;
        this.name = name;

        tables = new HashMap<>();
    }

    void build() throws SQLException
    {
        Statement statement = owner.createStatement();
        statement.executeUpdate(String.format("CREATE DATABASE IF NOT EXISTS %s;", name));
        statement.close();
        for (Table table : tables.values())
        {
            table.build();
        }
    }

    public Table addTable(String name, String columns)
    {
        tables.put(name, new Table(this, name, columns));
        return tables.get(name);
    }

    public String getName()
    {
        return name;
    }

    public List<Table> getTables()
    {
        return new ArrayList<>(tables.values());
    }

    public Table getTable(String name)
    {
        return tables.get(name);
    }

    public Statement createStatement()
    {
        Statement ret = null;

        try
        {
            ret = owner.createStatement();
            ret.execute(String.format("USE %s;", name));
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }

        return ret;
    }
}
