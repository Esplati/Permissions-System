package pluginschiene.perm;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import pluginschiene.perm.commands.CMD_Perm;
import pluginschiene.perm.util.Datenbank;


public class PermMain extends JavaPlugin{
	
	
	public File mfile = new File(getDataFolder().getAbsolutePath()+"/mysql.yml");
	public FileConfiguration mcfg = YamlConfiguration.loadConfiguration(mfile);
	
	public static Datenbank mysql;

	
	public void onEnable(){
		connect();
		registerCommand();
	}
	
	public void onDisable(){
		
	}
	
	public void registerCommand(){
		getCommand("group").setExecutor(new CMD_Perm());
		new L_Join(this);
	}
	
	public void connect(){
		mcfg.options().copyDefaults(true);
		mcfg.addDefault("Host", "Host");
		mcfg.addDefault("Port", "Port");
		mcfg.addDefault("Database", "Database");
		mcfg.addDefault("Username", "Username");
		mcfg.addDefault("Password", "Password");
		
		try {
			mcfg.save(mfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mysql = new Datenbank(mcfg.getString("Host"), mcfg.getString("Port"), mcfg.getString("Database"), mcfg.getString("Username"), mcfg.getString("Password"));	
		mysql.update("CREATE TABLE IF NOT EXISTS Permission (UUID VARCHAR(100), Name VARCHAR(100), Gruppe VARCHAR(100))");
		mysql.update("CREATE TABLE IF NOT EXISTS PermissionTemp (UUID VARCHAR(100), Gruppe VARCHAR(100), Datum VARCHAR(100))");
	}
}
