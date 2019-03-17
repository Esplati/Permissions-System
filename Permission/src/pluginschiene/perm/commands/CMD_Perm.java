package pluginschiene.perm.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pluginschiene.perm.Msg;
import pluginschiene.perm.util.Permission;
import pluginschiene.perm.util.enums.Group;

public class CMD_Perm implements CommandExecutor{

	Msg msg = new Msg();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("group")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(Permission.has(p, Group.ADMIN)){
					if(args.length == 3){
						if(args[0].equalsIgnoreCase("set")){
							if(Permission.existPlayer(args[1])){
								if(Group.is(args[2])){
									String name = args[1];
									Group gruppe = Group.get(args[2]);
									Permission.setGroup(name, gruppe);
									p.sendMessage(msg.groupset(name, gruppe.getFullName()));
								}else{
									p.sendMessage(msg.nogroup);
									p.sendMessage(Group.ADMIN.getFullName());
									p.sendMessage(Group.HEADDEV.getFullName());
									p.sendMessage(Group.SRMODERATOR.getFullName());
									p.sendMessage(Group.MODERATOR.getFullName());
									p.sendMessage(Group.DEVELOPER.getFullName());
									p.sendMessage(Group.SUPPORTER.getFullName());
									p.sendMessage(Group.SUPPORTERTEST.getFullName());
									p.sendMessage(Group.HEADBUILDER.getFullName());
									p.sendMessage(Group.BUILDER.getFullName());
									p.sendMessage(Group.YOUTUBER.getFullName());
									p.sendMessage(Group.PREMIUMPLUS.getFullName());
									p.sendMessage(Group.PREMIUM.getFullName());
									p.sendMessage(Group.SPIELER.getFullName());
								}
							}else{
								p.sendMessage(msg.noplayer);
							}
						}else if(args[0].equalsIgnoreCase("temp")){
							if(Permission.existPlayer(args[1])){
								String name = args[1];
								String uuid = Permission.getUUID(name);
								if(Permission.Has(name, Group.PREMIUMPLUS)){
									p.sendMessage(msg.higher_rank);
									return true;
								}
								if(Permission.hasLifeTime(uuid)){
									p.sendMessage(msg.lifetime);
									return true;
								}
								String time = args[2];
								String[] split = time.split(":");
								if(split.length == 2){
									String d = split[0];
									String h = split[1];
									if(isInteger(d) && isInteger(h)){
										int days = Integer.parseInt(d);
										int hours = Integer.parseInt(h);
										Permission.addGroupTemp(name, Group.PREMIUM, days, hours);
										p.sendMessage(msg.pre+"Die Zeit von §e"+name+" §7wurde um §e"+days+" Tage §7und §e"+hours+" §7Stunden verlängert");
									}else{
										p.sendMessage(msg.notime);
									}
								}else{
									p.sendMessage(msg.notime);
								}
							}else{
								p.sendMessage(msg.noplayer);
							}
						}else{
							p.sendMessage(msg.grouphelp);
						}
					}else if(args.length == 2){
						if(args[0].equalsIgnoreCase("remove")){
							if(Permission.existPlayer(args[1])){
								String uuid = Permission.getUUID(args[1]);
								Permission.setGroup(args[1], Group.SPIELER);
								Permission.removeTemp(uuid);
								p.sendMessage(msg.groupset(args[1], Group.SPIELER.getFullName()));
							}else{
								p.sendMessage(msg.noplayer);
							}
						}else if(args[0].equalsIgnoreCase("temptime")){
							if(Permission.existPlayer(args[1])){
								String uuid = Permission.getUUID(args[1]);
								if(Permission.hasLifeTime(uuid)){
									p.sendMessage(msg.lifetime);
									return true;
								}
								if(!Permission.has(p, Group.PREMIUM)){
									p.sendMessage(msg.norankg);
									return true;
								}
								LocalDateTime bis = Permission.getTempTime(uuid);
								DateTimeFormatter df;
						        df = DateTimeFormatter.BASIC_ISO_DATE;    // 20160131
						        df = DateTimeFormatter.ISO_LOCAL_DATE;    // 2016-18-31
						        df = DateTimeFormatter.ISO_DATE_TIME;     // 2016-01-31T20:07:07.095
						        df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");     // 31.01.2016 20:07
								p.sendMessage(msg.pre+"Premium läuft bis am: §e"+bis.format(df));
							}
						}
						else{
							p.sendMessage(msg.grouphelp);
						}
					}else{
						p.sendMessage(msg.grouphelp);
					}
				}else{
					p.sendMessage(msg.noperm);
				}
			}else{
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("remove")){
						if(Permission.existPlayer(args[1])){
							String uuid = Permission.getUUID(args[1]);
							Permission.setGroup(args[1], Group.SPIELER);
							Permission.removeTemp(uuid);
							sender.sendMessage(msg.groupset(args[1], Group.SPIELER.getFullName()));
						}else{
							sender.sendMessage(msg.noplayer);
						}
					}else{
						sender.sendMessage(msg.grouphelp);
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("set")){
						if(Permission.existPlayer(args[1])){
							if(Group.is(args[2])){
								String name = args[1];
								Group gruppe = Group.get(args[2]);
								Permission.setGroup(name, gruppe);
								sender.sendMessage(msg.groupset(name, gruppe.getFullName()));
							}else{
								sender.sendMessage(msg.nogroup);
								sender.sendMessage(Group.ADMIN.getFullName());
								sender.sendMessage(Group.HEADDEV.getFullName());
								sender.sendMessage(Group.SRMODERATOR.getFullName());
								sender.sendMessage(Group.MODERATOR.getFullName());
								sender.sendMessage(Group.DEVELOPER.getFullName());
								sender.sendMessage(Group.SUPPORTER.getFullName());
								sender.sendMessage(Group.SUPPORTERTEST.getFullName());
								sender.sendMessage(Group.HEADBUILDER.getFullName());
								sender.sendMessage(Group.BUILDER.getFullName());
								sender.sendMessage(Group.YOUTUBER.getFullName());
								sender.sendMessage(Group.PREMIUMPLUS.getFullName());
								sender.sendMessage(Group.PREMIUM.getFullName());
								sender.sendMessage(Group.SPIELER.getFullName());
							}
						}else{
							sender.sendMessage(msg.noplayer);
						}
					}else if(args[0].equalsIgnoreCase("temp")){

						if(Permission.existPlayer(args[1])){
							String name = args[1];
							String uuid = Permission.getUUID(name);
							if(Permission.Has(name, Group.PREMIUMPLUS)){
								sender.sendMessage(msg.higher_rank);
								return true;
							}
							if(Permission.hasLifeTime(uuid)){
								sender.sendMessage(msg.lifetime);
								return true;
							}
							String time = args[2];
							String[] split = time.split(":");
							if(split.length == 2){
								String d = split[0];
								String h = split[1];
								if(isInteger(d) && isInteger(h)){
									int days = Integer.parseInt(d);
									int hours = Integer.parseInt(h);
									Permission.addGroupTemp(name, Group.PREMIUM, days, hours);
									sender.sendMessage(msg.pre+"Die Zeit von §e"+name+" §7wurde um §e"+days+" Tage §7und §e"+hours+" §7Stunden verlängert");
								}else{
									sender.sendMessage(msg.notime);
								}
							}else{
								sender.sendMessage(msg.notime);
							}
						}else{
							sender.sendMessage(msg.noplayer);
						}
					
					}else{
						sender.sendMessage(msg.grouphelp);
					}
					
				}else{
					sender.sendMessage(msg.grouphelp);
				}
			}
		}
		
		return true;
	}
	
	private boolean isInteger(String s){
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
