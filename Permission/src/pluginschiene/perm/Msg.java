package pluginschiene.perm;

public class Msg {
	
	
	public final String pre = "§7[§bServer§7] ";
	
	public final String noperm = pre+"Keine Berechtigung";
	public final String sethelp = pre+"/perm set [Spieler] [Gruppe]";
	public final String noplayer	= pre+"Dieser Spieler war noch nie auf dem Server";
	public final String grouphelp = pre+"/group (set, remove) [Spieler]";
	public final String nogroup = pre+"Diese Gruppe existiert nicht";
	public final String notime = pre+"Die Zeit muss mit Wert:Wert (Tage:Stunden) angegeben werden";
	public final String higher_rank = pre+"Dieser Spieler hat bereits einen höheren Rang LifeTime";
	public final String lifetime = pre+"Dieser Spieler hat den Rang LifeTime";
	public final String norankg = pre+"Dieser Spieler hat kein Rang";
	
	public String groupset(String name, String gruppe){
		return pre+"Der Spieler §e"+name+" §7ist nun §e"+gruppe
	;}
}
