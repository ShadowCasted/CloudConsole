package com.shadowcasted.cloudconsole.commands.cloudterminal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.shadowcasted.cloudconsole.datamanagement.UserConfigClass;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class RemoveUser {

	
	private int stage = 0;
	private UserConfigClass ucc = null;
	
	public boolean Actions(Player username, String message){
		
		if(stage == 0){ //stage 1
			ucc = new UserConfigClass(message); 
			if(message.equalsIgnoreCase("list")){
				ClientHandler.getDataQueuer().ListAccounts(username);
				return true;
			}
			if(ucc.isReal()){
				username.sendMessage(ChatColor.RED+"Are You Sure That You Want To Delete This Account?");
				stage++;
			}else{
				ClientHandler.getClientMap().Debug("Tried to delete \""+message+"\"");
				username.sendMessage(ChatColor.RED+"This Isn't A Valid User, Ending Chat Input...");
				return false;
			}
			return true;
		}
		if(stage == 1){
			if(message.equalsIgnoreCase("yes")){
				username.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Are You Absolutely Sure?");
				stage++;
			}else{
				username.sendMessage(ChatColor.GREEN+"Okay, Ending Chat Input...");
				return false;
			}
			return true;
		}
		
		if(stage == 2){
			if(message.equalsIgnoreCase("yes")){
				username.sendMessage(ChatColor.RED+"Please Confirm By Saying "+ChatColor.BLUE+ChatColor.BOLD+"[delete]");
				stage++;
			}else{
				username.sendMessage(ChatColor.RESET+""+ChatColor.GREEN+"Okay, Ending Chat Input...");
				return false;
			}
			return true;
		}

		if(stage == 3){
			if(message.equalsIgnoreCase("[delete]")){
				username.sendMessage(ChatColor.GREEN+"Okay Deleting The Account Now.");
				ucc.delete();
			}
			return false;
		}
		return false;

	}
}
