package xyz.skettios.dungeonmaster.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper
{
    public static String USER = "";
    public static String PASS = "";
    public static final String URL = "jdbc:mysql://localhost/?user=%s&password=%s";
    public static final String DB = "DungeonMaster";

    private static String getURL()
    {
        return String.format(URL, USER, PASS);
    }

    private static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(getURL());
    }

    public static void createDatabase(String dbName)
    {
        try
        {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate(String.format("CREATE DATABASE IF NOT EXISTS %s;", dbName));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void createTable(String dbName, String tableName, String columns)
    {
        try
        {
            Statement statement = getConnection().createStatement();
            statement.execute(String.format("USE %s;", dbName));
            statement.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS guild_%s %s;", tableName, columns));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(String dbName, String command)
    {
        try
        {
            Statement statement = getConnection().createStatement();
            statement.execute(String.format("USE %s;", dbName));
            statement.executeUpdate(command);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
