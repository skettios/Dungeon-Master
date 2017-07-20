package xyz.skettios.dungeonmaster.database;

import xyz.skettios.dungeonmaster.DungeonMaster;

import java.sql.SQLException;
import java.sql.Statement;

public class Table
{
    private Schema owner;
    private String name;
    private String columns;

    Table(Schema owner, String name, String columns)
    {
        this.owner = owner;
        this.name = name;
        this.columns = columns;
    }

    void build() throws SQLException
    {
        Statement statement = createStatement();
        statement.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s %s;", name, columns));
        statement.close();
    }

    public String getName()
    {
        return name;
    }

    public Statement createStatement()
    {
        return owner.createStatement();
    }
}
