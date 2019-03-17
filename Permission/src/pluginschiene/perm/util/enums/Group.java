package pluginschiene.perm.util.enums;

public enum Group {
	ADMIN(0, "Admin"),
	HEADDEV(1, "HeadDeveloper"),
	SRMODERATOR(2, "SrModerator"),
	MODERATOR(3, "Moderator"),
	DEVELOPER(4, "Developer"),
	SUPPORTER(5, "Supporter"),
	SUPPORTERTEST(6, "SupporterTest"),
	HEADBUILDER(7, "HeadBuilder"),
	BUILDER(8, "Builder"),
	YOUTUBER(9, "Youtuber"),
	PREMIUMPLUS(10, "PremiumPlus"),
	PREMIUM(11, "Premium"),
	SPIELER(12, "Spieler"),
	;
	
	final int grp;
	final String name;
	Group(final Integer i, String name){
		grp = i;
		this.name = name;
	}
	
	public String getFullName(){
		String pre = "";
		if(this.equals(ADMIN)){
			pre = "§4";
		}else if(this.equals(BUILDER)){
			pre = "§e";
		}else if(this.equals(DEVELOPER)){
			pre = "§b";
		}else if(this.equals(HEADBUILDER)){
			pre = "§e";
		}else if(this.equals(HEADDEV)){
			pre = "§b";
		}else if(this.equals(MODERATOR)){
			pre = "§c";
		}else if(this.equals(PREMIUM)){
			pre = "§6";
		}else if(this.equals(PREMIUMPLUS)){
			pre = "§6";
		}else if(this.equals(SPIELER)){
			pre = "§7";
		}else if(this.equals(SRMODERATOR)){
			pre = "§c";
		}else if(this.equals(SUPPORTER)){
			pre = "§a";
		}else if(this.equals(SUPPORTERTEST)){
			pre = "§a";
		}else if(this.equals(YOUTUBER)){
			pre = "§5";
		}
		return pre+this.name;
	}
	
	public static Group get(String name){
		if(is(name)){
			if(name.equalsIgnoreCase(Group.ADMIN.name)){
				return Group.ADMIN;
			}
			if(name.equalsIgnoreCase(Group.BUILDER.name)){
				return Group.BUILDER;
			}
			if(name.equalsIgnoreCase(Group.DEVELOPER.name)){
				return Group.DEVELOPER;
			}
			if(name.equalsIgnoreCase(Group.HEADBUILDER.name)){
				return Group.HEADBUILDER;
			}
			if(name.equalsIgnoreCase(Group.HEADDEV.name)){
				return Group.HEADDEV;
			}
			if(name.equalsIgnoreCase(Group.MODERATOR.name)){
				return Group.MODERATOR;
			}
			if(name.equalsIgnoreCase(Group.PREMIUM.name)){
				return Group.PREMIUM;
			}
			if(name.equalsIgnoreCase(Group.PREMIUMPLUS.name)){
				return Group.PREMIUMPLUS;
			}
			if(name.equalsIgnoreCase(Group.SPIELER.name)){
				return Group.SPIELER;
			}
			if(name.equalsIgnoreCase(Group.SRMODERATOR.name)){
				return Group.SRMODERATOR;
			}
			if(name.equalsIgnoreCase(Group.SUPPORTER.name)){
				return Group.SUPPORTER;
			}
			if(name.equalsIgnoreCase(Group.SUPPORTERTEST.name)){
				return Group.SUPPORTERTEST;
			}
			if(name.equalsIgnoreCase(Group.YOUTUBER.name)){
				return Group.YOUTUBER;
			}
		}
		return null;
	}
	
	
	public static boolean is(String name){
		if(name.equalsIgnoreCase(Group.ADMIN.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.BUILDER.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.DEVELOPER.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.HEADBUILDER.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.HEADDEV.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.MODERATOR.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.PREMIUM.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.PREMIUMPLUS.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.SPIELER.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.SRMODERATOR.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.SUPPORTER.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.SUPPORTERTEST.name)){
			return true;
		}
		if(name.equalsIgnoreCase(Group.YOUTUBER.name)){
			return true;
		}
		return false;
	}
	
	
	public boolean Ishigher(Group gruppe){
		if(this.grp<=gruppe.grp){
			return true;
		}
		return false;
	}
	
	public String getName(){
		return this.name;
	}
}
