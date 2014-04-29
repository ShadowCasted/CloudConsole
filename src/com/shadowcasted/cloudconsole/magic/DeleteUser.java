package com.shadowcasted.cloudconsole.magic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import com.shadowcasted.cloudconsole.datamanagement.UserConfigClass;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

@SuppressWarnings("deprecation")
public class DeleteUser extends ChatInputStuff {
	
	private int stage = 0;
	@Override
	public int getStage(){return stage;}
	@Override
	public void setStage(int a){stage = a;}

	private UserConfigClass ucc = null;
	
	@Override
	public void doSomething(ChatInputMap map, String username, String message, PlayerChatEvent event){
		event.setCancelled(true);
		if(getPlayer(username) == null){map.removePlayer(username);}
		//Tell user: Please Enter a Username
		if(stage == 0){ //stage 1
			ucc = new UserConfigClass(message); 
			if(message.equalsIgnoreCase("list")){
				ClientHandler.getDataQueuer().ListAccounts(getPlayer(username));
				return;
			}
			if(ucc.isReal()){
				getPlayer(username).sendMessage(ChatColor.RED+"Are You Sure That You Want To Delete This Account?");
				stage++;
			}else{
				System.out.println("Tried to delete \""+message+"\"");
				getPlayer(username).sendMessage(ChatColor.RED+"This Isn't A Valid User, Ending Chat Input...");
				map.removePlayer(username);
			}
			return;
		}
		if(stage == 1){
			if(message.equalsIgnoreCase("yes")){
				getPlayer(username).sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Are You Absolutely Sure?");
				stage++;
			}else{
				getPlayer(username).sendMessage(ChatColor.GREEN+"Okay, Ending Chat Input...");
				map.removePlayer(username);
			}
			return;
		}
		
		if(stage == 2){
			if(message.equalsIgnoreCase("yes")){
				getPlayer(username).sendMessage(ChatColor.RED+"Please Confirm By Saying "+ChatColor.BLUE+ChatColor.BOLD+"[delete]");
				stage++;
			}else{
				getPlayer(username).sendMessage(ChatColor.RESET+""+ChatColor.GREEN+"Okay, Ending Chat Input...");
				map.removePlayer(username);
			}
			return;
		}

		if(stage == 3){
			if(message.equalsIgnoreCase("[delete]")){
				getPlayer(username).sendMessage(ChatColor.GREEN+"Okay Deleting The Account Now.");
				ucc.delete();
			}else{
				
			}
			return;
		}

	}
	
	public Player getPlayer(String name){
		return Bukkit.getPlayer(name);
	}

	@Override
	public void doSomething(ChatInputMap map, Player p, String message, PlayerChatEvent event){
		doSomething(map, p.getName().toString(),message,event);
	}

	@Override
	public void cleanup() {

	}
}
