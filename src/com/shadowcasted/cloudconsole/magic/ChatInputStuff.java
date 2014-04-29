package com.shadowcasted.cloudconsole.magic;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public abstract class ChatInputStuff {

	private int stage;
	public int getStage(){return stage;}
	public void setStage(int a){stage = a;}
	
	private String message;
	public String getMessage(){return message;}
	
	private String user;
	public String getUser(){return user;}
	
	public Player getPlayer(){return Bukkit.getPlayer(user);}
	
	public void doSomething(ChatInputMap map, String username, String message, PlayerChatEvent event){
		
	}
	
	public void doSomething(ChatInputMap map, Player p, String message, PlayerChatEvent event){
		
	}
	
	public void cleanup() {
		
	}
	
}
