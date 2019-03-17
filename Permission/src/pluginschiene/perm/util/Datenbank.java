package pluginschiene.perm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;

public class Datenbank
{
  private String Host = "";
  private String Port = "";
  private String Database = "";
  private String Username = "";
  private String Passwort = "";
  private Connection con;
  
  public Datenbank(String host, String port, String database, String username, String passwort)
  {
    this.Host = host;
    this.Port = port;
    this.Database = database;
    this.Username = username;
    this.Passwort = passwort;
    connect();
  }
  
  private void connect()
  {
    try
    {
      this.con = DriverManager.getConnection("jdbc:mysql://" + this.Host + ":" + this.Port + "/" + this.Database + "?autoReconnect=true", this.Username, this.Passwort);
      Bukkit.getConsoleSender().sendMessage("MySQL Verbindung aufgebaut!");
    }
    catch (SQLException e)
    {
      Bukkit.getConsoleSender().sendMessage("MySQL Verbindung fehlgeschlagen!");
      e.printStackTrace();
    }
  }
  
  public void disconnect()
  {
    if (this.con != null) {
      try
      {
        this.con.close();
        Bukkit.getConsoleSender().sendMessage("Verbindung zur Datenbank beendet");
      }
      catch (SQLException e)
      {
        Bukkit.getConsoleSender().sendMessage("Verbindung zur Datenbank konnte nicht beendet werden!");
        
        e.printStackTrace();
      }
    }
  }
  
  public void update(String qry)
  {
    try
    {
      PreparedStatement ps = this.con.prepareStatement(qry);
      ps.executeUpdate(qry);
      ps.close();
    }
    catch (SQLException e)
    {
      connect();
      e.printStackTrace();
    }
  }
  
  public ResultSet query(String qry)
  {
    ResultSet rs = null;
    try
    {
      PreparedStatement ps = this.con.prepareStatement(qry);
      rs = ps.executeQuery(qry);
    }
    catch (SQLException e)
    {
      connect();
      e.printStackTrace();
    }
    return rs;
  }
}
