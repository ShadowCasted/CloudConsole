package com.shadowcasted.cloudconsole.magic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("deprecation")
public class EditPort extends ChatInputStuff{

	private int stage;
	public int getStage(){return stage;}
	public void setStage(int a){stage = a;}
	
	private String message;
	public String getMessage(){return message;}
	
	private String user;
	public String getUser(){return user;}
	
	public Player getPlayer(String username){return Bukkit.getPlayer(username);}
	
	public void doSomething(ChatInputMap map, String username, String message, PlayerChatEvent event, Plugin plugin){
		try{
			if(stage == 0){
				int port = Integer.parseInt(message);
				
				
			}
		}catch(Exception e){try{getPlayer(username).sendMessage(ChatColor.RED+"That Wasn't A Valid Non-Decimal Number. Please Try Again!");}catch(Exception E){map.removePlayer(username); return;}}
	}
	
	public void doSomething(ChatInputMap map, Player p, String message, PlayerChatEvent event, Plugin plugin){
		doSomething(map, p.getName().toString(),message,event,plugin);
	}
	
	public void cleanup() {
		
	}
	
}
