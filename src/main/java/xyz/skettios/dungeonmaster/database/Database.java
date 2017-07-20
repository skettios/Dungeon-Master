package xyz.skettios.dungeonmaster.database;

import xyz.skettios.dungeonmaster.DungeonMaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database
{
    private Connection connection;
    private Map<String, Schema> schemas;

    public Database(String user, String pass)
    {
        try
        {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://localhost/?user=%s&password=%s", user, pass));
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }

        schemas = new HashMap<>();
    }

    public void build() throws SQLException
    {
        for (Schema schema : schemas.values())
        {
            schema.build();
        }
    }

    public Statement createStatement()
    {
        Statement ret = null;

        try
        {
            ret = connection.createStatement();
        }
        catch (SQLException e)
        {
            DungeonMaster.getInstance().LOGGER.error(e.getMessage());
        }

        return ret;
    }

    public List<Schema> getSchemas()
    {
        return new ArrayList<>(schemas.values());
    }

    public Schema getSchema(String name)
    {
        return schemas.get(name);
    }

    public Schema addSchema(String name)
    {
        schemas.put(name, new Schema(this, name));
        return schemas.get(name);
    }
}
