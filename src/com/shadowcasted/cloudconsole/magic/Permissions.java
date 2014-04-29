package com.shadowcasted.cloudconsole.magic;

import org.bukkit.ChatColor;

public class Permissions {

	public static String AllPermissions(){
		return    ChatColor.GOLD+""+ChatColor.BOLD+"====Console====\n"
				+ ChatColor.RESET+ChatColor.AQUA+"Console.Commands\n"
				+ ChatColor.AQUA+"Console.View\n"
				+ ChatColor.AQUA+ChatColor.STRIKETHROUGH+"Console.ConfigEdit\n"
				+ChatColor.RESET+ChatColor.GOLD+ChatColor.BOLD+"====Chat====\n"
				+ChatColor.RESET+ChatColor.AQUA+"Chat.View\n"
				+ChatColor.AQUA+"Chat.Talk\n";
	}
	
	public static String[] perms = {
			"Console.Commands",
			"Console.View",
			"Console.ConfigEdit",
			"Chat.View",
			"Chat.Talk"
	};
	
	public static boolean isValidPerm(String perm){
		for(String p: perms){
			if(p.equals(perm)){
				return true;
			}
		}
		return false;
	}
}
