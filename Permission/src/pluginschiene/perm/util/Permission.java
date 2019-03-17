package pluginschiene.perm.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pluginschiene.perm.PermMain;
import pluginschiene.perm.util.enums.Group;

public class Permission {
	
	
	public static boolean existPlayer(Player p){
		String uuid = p.getUniqueId().toString();
		ResultSet rs = PermMain.mysql.query("SELECT * FROM Permission WHERE UUID = '"+uuid+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	public static boolean existPlayer(String name){
		ResultSet rs = PermMain.mysql.query("SELECT * FROM Permission WHERE Name = '"+name+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static void registerPlayer(Player p){
		if(existPlayer(p) == false){
		String uuid = p.getUniqueId().toString();
		String name = p.getName();
		String group = "Spieler";
		PermMain.mysql.update("INSERT INTO Permission (UUID, Name, Gruppe) VALUES ('"+uuid+"', '"+name+"', '"+group+"')");
		}else{
			refreshName(p);
		}
	}
	
	public static void setGroup(Player p, Group gruppe){
		Bukkit.getPluginManager().callEvent(new GroupChangeEvent(p, p.getUniqueId().toString(), get(p), gruppe));
		String uuid = p.getUniqueId().toString();
		PermMain.mysql.update("UPDATE Permission SET Gruppe = '"+gruppe.getName()+"' WHERE UUID = '"+uuid+"'");
	}
	
	public static boolean hasTemp(Player p){
		String uuid = p.getUniqueId().toString();
		ResultSet rs = PermMain.mysql.query("SELECT * FROM PermissionTemp WHERE UUID = '"+uuid+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static boolean hasTemp(String uuid){
		ResultSet rs = PermMain.mysql.query("SELECT * FROM PermissionTemp WHERE UUID = '"+uuid+"'");
		try {
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static void addGroupTemp(Player p, Group gruppe, Integer tage, Integer stunden){
		String uuid = p.getUniqueId().toString();
		if(!hasTemp(p)){
		Bukkit.getPluginManager().callEvent(new GroupChangeEvent(p, p.getUniqueId().toString(), get(p), gruppe));
		PermMain.mysql.update("UPDATE PermissionTemp SET Gruppe = '"+gruppe.getName()+"' WHERE UUID = '"+uuid+"'");
		
	    
	        LocalDateTime now = LocalDateTime.now();
	        now = now.plusHours(24*tage);
	        now = now.plusHours(stunden);
	        DateTimeFormatter df;
	        df = DateTimeFormatter.BASIC_ISO_DATE;    // 20160131
	        df = DateTimeFormatter.ISO_LOCAL_DATE;    // 2016-18-31
	        df = DateTimeFormatter.ISO_DATE_TIME;     // 2016-01-31T20:07:07.095
	        df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");     // 31.01.2016 20:07
	        
			PermMain.mysql.update("INSERT INTO PermissionTemp (UUID, Gruppe, Datum) VALUES ('"+uuid+"', '"+gruppe.getName()+"', '"+now.format(df)+"')");
		}else{
			removeTemp(p);
			LocalDateTime bis = getTempTime(p);
			bis = bis.plusHours(24*tage);
			bis = bis.plusHours(stunden);
			DateTimeFormatter df;
	        df = DateTimeFormatter.BASIC_ISO_DATE;    // 20160131
	        df = DateTimeFormatter.ISO_LOCAL_DATE;    // 2016-18-31
	        df = DateTimeFormatter.ISO_DATE_TIME;     // 2016-01-31T20:07:07.095
	        df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");     // 31.01.2016 20:07
			PermMain.mysql.update("UPDATE PermissionTemp SET Datum = '"+bis.format(df)+"' WHERE UUID = '"+uuid+"'");
		}
	}
	
	public static void setGroup(String name, Group gruppe){
		if(Bukkit.getPlayer(name) != null){
			Player p = Bukkit.getPlayer(name);
			Bukkit.getPluginManager().callEvent(new GroupChangeEvent(p, getUUID(name), get(name), gruppe));
		}else{
		Bukkit.getPluginManager().callEvent(new GroupChangeEvent(null, getUUID(name), get(name), gruppe));
		}
		PermMain.mysql.update("UPDATE Permission SET Gruppe = '"+gruppe.getName()+"' WHERE Name = '"+name+"'");
	}
	
	public static void addGroupTemp(String name, Group gruppe, Integer tage, Integer stunden){
		
		String uuid = getUUID(name);
		if(!hasTemp(uuid)){
			
			if(Bukkit.getPlayer(name) != null){
				Player p = Bukkit.getPlayer(name);
				Bukkit.getPluginManager().callEvent(new GroupChangeEvent(p, uuid, get(name), gruppe));
			}else{
			Bukkit.getPluginManager().callEvent(new GroupChangeEvent(null, uuid, get(name), gruppe));
			}
			
		        LocalDateTime now = LocalDateTime.now();
		        now = now.plusHours(24*tage);
		        now = now.plusHours(stunden);
		        DateTimeFormatter df;
		        df = DateTimeFormatter.BASIC_ISO_DATE;    // 20160131
		        df = DateTimeFormatter.ISO_LOCAL_DATE;    // 2016-18-31
		        df = DateTimeFormatter.ISO_DATE_TIME;     // 2016-01-31T20:07:07.095
		        df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");     // 31.01.2016 20:07
		        
				PermMain.mysql.update("INSERT INTO PermissionTemp (UUID, Gruppe, Datum) VALUES ('"+uuid+"', '"+gruppe.getName()+"', '"+now.format(df)+"')");
			}else{
				removeTemp(uuid);
				LocalDateTime bis = getTempTime(uuid);
				bis = bis.plusHours(24*tage);
				bis = bis.plusHours(stunden);
				DateTimeFormatter df;
		        df = DateTimeFormatter.BASIC_ISO_DATE;    // 20160131
		        df = DateTimeFormatter.ISO_LOCAL_DATE;    // 2016-18-31
		        df = DateTimeFormatter.ISO_DATE_TIME;     // 2016-01-31T20:07:07.095
		        df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");     // 31.01.2016 20:07
				PermMain.mysql.update("UPDATE PermissionTemp SET Datum = '"+bis.format(df)+"' WHERE UUID = '"+uuid+"'");
			}
	}
	
	public static void removeTemp(Player p){
		if(hasTemp(p)){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime bis = getTempTime(p);
		if(now.isAfter(bis)){
			PermMain.mysql.update("DELETE FROM PermissionTemp WHERE UUID = '"+p.getUniqueId().toString()+"'");
		}
		}
	}
	
	public static void removeTemp(String uuid){
		if(hasTemp(uuid)){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime bis = getTempTime(uuid);
		if(now.isAfter(bis)){
			PermMain.mysql.update("DELETE FROM PermissionTemp WHERE UUID = '"+uuid+"'");
		}
		}
	}
	
	public static LocalDateTime getTempTime(Player p){
		String s = getDateString(p); // 31.01.2016 20:07
		String[] split = s.split(Pattern.quote(".")); // [0] 31, [1] 01, [2] 2016
		int dayOfMonth = Integer.parseInt(split[0]); //31
		int month = Integer.parseInt(split[1]); //1
		String y = split[2];
		split = y.split(" ");
		int year = Integer.parseInt(split[0]); //2016
		
		String h = s.substring(11, 13); //20
		String m = s.substring(14, 16); //07
		
		int hour = Integer.parseInt(h);
		int minute = Integer.parseInt(m);
		
		LocalDateTime bis = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		return bis;
	}
	
	public static LocalDateTime getTempTime(String uuid){
		String s = getDateString(uuid); // 31.01.2016 20:07
		String[] split = s.split(Pattern.quote(".")); // [0] 31, [1] 01, [2] 2016
		int dayOfMonth = Integer.parseInt(split[0]); //31
		int month = Integer.parseInt(split[1]); //1
		String y = split[2];
		split = y.split(" ");
		int year = Integer.parseInt(split[0]); //2016
		
		String h = s.substring(11, 13); //20
		String m = s.substring(14, 16); //07
		
		int hour = Integer.parseInt(h);
		int minute = Integer.parseInt(m);
		
		LocalDateTime bis = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		return bis;
	}
	
	
	private static String getDateString(Player p){
		String uuid = p.getUniqueId().toString();
		ResultSet rs = PermMain.mysql.query("SELECT Datum FROM PermissionTemp WHERE UUID = '"+uuid+"'");
		try {
			while (rs.next()){
			return rs.getString("Datum");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getDateString(String uuid){
		ResultSet rs = PermMain.mysql.query("SELECT Datum FROM PermissionTemp WHERE UUID = '"+uuid+"'");
		try {
			while (rs.next()){
			return rs.getString("Datum");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private static void refreshName(Player p){
		String name = p.getName();
		String uuid = p.getUniqueId().toString();
		PermMain.mysql.update("UPDATE Permission SET Name = '"+name+"' WHERE UUID = '"+uuid+"'");
	}
	
	private static String getGroup(Player p){
		String uuid = p.getUniqueId().toString();
		ResultSet rs = PermMain.mysql.query("SELECT Gruppe FROM Permission WHERE UUID = '"+uuid+"'");
		
		try {
			while (rs.next()){
			return rs.getString("Gruppe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Spieler";
	}
	
	private static String getGroup(String uuid){
		ResultSet rs = PermMain.mysql.query("SELECT Gruppe FROM Permission WHERE UUID = '"+uuid+"'");
		
		try {
			while (rs.next()){
			return rs.getString("Gruppe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Spieler";
	}
	
	private static String getGroupFrom(String name){
		ResultSet rs = PermMain.mysql.query("SELECT Gruppe FROM Permission WHERE Name = '"+name+"'");
		
		try {
			while (rs.next()){
			return rs.getString("Gruppe");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Spieler";
	}
	
	public static boolean hasLifeTime(Player p){
		String group = getGroup(p);
		if(!group.equalsIgnoreCase("Spieler")){
			return true;
		}
		return false;
	}
	
	public static boolean hasLifeTime(String uuid){
		String group = getGroup(uuid);
		if(!group.equalsIgnoreCase("Spieler")){
			return true;
		}
		return false;
	}
	
	public static Group get(Player p){
		String group =  getGroup(p);
		if(group.equalsIgnoreCase("Admin")){
			return Group.ADMIN;
		}else if(group.equalsIgnoreCase("HeadDeveloper")){
			return Group.HEADDEV;
		}else if(group.equalsIgnoreCase("SrModerator")){
			return Group.SRMODERATOR;
		}else if(group.equalsIgnoreCase("Moderator")){
			return Group.MODERATOR;
		}else if(group.equalsIgnoreCase("Developer")){
			return Group.DEVELOPER;
		}else if(group.equalsIgnoreCase("Supporter")){
			return Group.SUPPORTER;
		}else if(group.equalsIgnoreCase("SupporterTest")){
			return Group.SUPPORTERTEST;
		}else if(group.equalsIgnoreCase("HeadBuilder")){
			return Group.HEADBUILDER;
		}else if(group.equalsIgnoreCase("Builder")){
			return Group.BUILDER;
		}else if(group.equalsIgnoreCase("Youtuber")){
			return Group.YOUTUBER;
		}else if(group.equalsIgnoreCase("PremiumPlus")){
			return Group.PREMIUMPLUS;
		}else if(group.equalsIgnoreCase("Premium") || hasTemp(p)){
			return Group.PREMIUM;
		}else{
			return Group.SPIELER;
		}
	}
	
	public static Group get(String uuid){
		String group =  getGroup(uuid);
		if(group.equalsIgnoreCase("Admin")){
			return Group.ADMIN;
		}else if(group.equalsIgnoreCase("HeadDeveloper")){
			return Group.HEADDEV;
		}else if(group.equalsIgnoreCase("SrModerator")){
			return Group.SRMODERATOR;
		}else if(group.equalsIgnoreCase("Moderator")){
			return Group.MODERATOR;
		}else if(group.equalsIgnoreCase("Developer")){
			return Group.DEVELOPER;
		}else if(group.equalsIgnoreCase("Supporter")){
			return Group.SUPPORTER;
		}else if(group.equalsIgnoreCase("SupporterTest")){
			return Group.SUPPORTERTEST;
		}else if(group.equalsIgnoreCase("HeadBuilder")){
			return Group.HEADBUILDER;
		}else if(group.equalsIgnoreCase("Builder")){
			return Group.BUILDER;
		}else if(group.equalsIgnoreCase("Youtuber")){
			return Group.YOUTUBER;
		}else if(group.equalsIgnoreCase("PremiumPlus")){
			return Group.PREMIUMPLUS;
		}else if(group.equalsIgnoreCase("Premium") || hasTemp(uuid)){
			return Group.PREMIUM;
		}else{
			return Group.SPIELER;
		}
		
	}
	
	public static Group getFrom(String name){
		String group =  getGroupFrom(name);
		String uuid = getUUID(name);
		if(group.equalsIgnoreCase("Admin")){
			return Group.ADMIN;
		}else if(group.equalsIgnoreCase("HeadDeveloper")){
			return Group.HEADDEV;
		}else if(group.equalsIgnoreCase("SrModerator")){
			return Group.SRMODERATOR;
		}else if(group.equalsIgnoreCase("Moderator")){
			return Group.MODERATOR;
		}else if(group.equalsIgnoreCase("Developer")){
			return Group.DEVELOPER;
		}else if(group.equalsIgnoreCase("Supporter")){
			return Group.SUPPORTER;
		}else if(group.equalsIgnoreCase("SupporterTest")){
			return Group.SUPPORTERTEST;
		}else if(group.equalsIgnoreCase("HeadBuilder")){
			return Group.HEADBUILDER;
		}else if(group.equalsIgnoreCase("Builder")){
			return Group.BUILDER;
		}else if(group.equalsIgnoreCase("Youtuber")){
			return Group.YOUTUBER;
		}else if(group.equalsIgnoreCase("PremiumPlus")){
			return Group.PREMIUMPLUS;
		}else if(group.equalsIgnoreCase("Premium") || hasTemp(uuid)){
			return Group.PREMIUM;
		}else{
			return Group.SPIELER;
		}
	}
	
	public static boolean has(Player p, Group group){
		Group grp = get(p);
		return grp.Ishigher(group);
	}
	
	public static boolean is(Player p, Group group){
		Group grp = get(p);
		return grp.equals(group);
	}
	
	public static boolean is(String uuid, Group group){
		Group grp = get(uuid);
		return grp.equals(group);
	}
	
	public static boolean has(String uuid, Group group){
		Group grp = get(uuid);
		return grp.Ishigher(group);
	}
	
	public static boolean Has(String name, Group group){
		Group grp = getFrom(name);
		return grp.Ishigher(group);
	}
	
	
	public static String getName(String uuid){
		ResultSet rs = PermMain.mysql.query("SELECT Name FROM Permission WHERE UUID = '"+uuid+"'");
		try {
			while(rs.next()){
				return rs.getString("Name");
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	public static String getUUID(String name){
		ResultSet rs = PermMain.mysql.query("SELECT UUID FROM Permission WHERE Name = '"+name+"'");
		try {
			while (rs.next()){
				String s = rs.getString("UUID");
				return s;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
