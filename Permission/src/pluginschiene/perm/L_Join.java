package pluginschiene.perm;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pluginschiene.perm.util.Permission;

public class L_Join implements Listener{

	
	public L_Join(PermMain main){
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		Permission.removeTemp(p);
	}
	
	@EventHandler
	public void register(PlayerJoinEvent e) {
		Permission.registerPlayer(e.getPlayer());
	}
}
