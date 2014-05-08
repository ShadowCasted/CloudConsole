package com.shadowcasted.cloudconsole.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class CloudCommand extends ShadowCommand{

	@Override
	public String CommandString() {return "cc";}


	@Override
	public boolean ActUpon() {
		if(getArguments()!= null){

			if(getArguments()[0].equalsIgnoreCase("Stop")){
				if(hasPermission("cloud.server.stop")){
					getPlayer().sendMessage(ChatColor.RED+"Tried Stopping The Service!");
					ClientHandler.getCH().Stop();
					return true;
				}else{noPermissions(); return true;}
			}
			if(getArguments()[0].equalsIgnoreCase("Restart")){
				if(hasPermission("cloud.server.restart")){
					Bukkit.getServer().reload();
					getPlayer().sendMessage(ChatColor.LIGHT_PURPLE+"Tried Restarting The Service!");
					return true;
				}else{noPermissions(); return true;}
			}
			if(getArguments()[0].equalsIgnoreCase("Start")){
				if(hasPermission("cloud.server.start")){
					Bukkit.getServer().reload();
					getPlayer().sendMessage(ChatColor.GREEN+"Tried Starting The Service!");
					return true;
				}else{noPermissions();return true;}
			}
			
			

			if(getArguments()[0].equalsIgnoreCase("edit")){
				if(getArguments().length > 2){
					
					if(getArguments()[1].equalsIgnoreCase("console")){
						
					}
					
				}
			}




		}
		return true;
	}
	
	public void noPermissions(){
		getPlayer().sendMessage(ChatColor.RED+"You Don't Have Permissions For Ths Command");
	}

	public boolean hasPermission(String perm){
		boolean op = getPlayer().isOp();
		getPlayer().setOp(false);
		boolean check = getPlayer().hasPermission(perm);
		getPlayer().setOp(op);
		return check;
	}

}
