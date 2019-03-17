package pluginschiene.perm.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pluginschiene.perm.util.enums.Group;

public class GroupChangeEvent extends Event{

	
    private static HandlerList handlers = new HandlerList();
   
    private final Player player;
    private final Group oldgroup;
    private final Group newgroup;
    private final String uuid;
    
    public GroupChangeEvent(Player player, String uuid, Group oldgroup, Group newgroup){
    	this.player = player;
    	this.oldgroup = oldgroup;
    	this.newgroup = newgroup;
    	this.uuid = uuid;
    }
     
    /**
     * @return null if the player is new
     */
    public Group getOldGroup(){
    	return this.oldgroup;
    }
    /**
     * 
     * @return the new players group
     */
    public Group getGroup(){
    	return this.newgroup;
    }
    /**
     * 
     * @return the Player where change the group
     * 		   null if the Player is not online
     */
    public Player getPlayer(){
    	return this.player;
    }
    /**
     * 
     * @return the players UUID as String
     */
    public String getUUID(){
    	return this.uuid;
    }
    /**
     * 
     * @return Handlerlist
     */
    public static HandlerList getHandlerList(){
        return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
	        return handlers;
	}
    
}
