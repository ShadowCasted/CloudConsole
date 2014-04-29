package com.shadowcasted.cloudconsole.magic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class Console extends ChatInputStuff{

	
	
	private int stage = 0;
	@Override
	public int getStage(){return stage;}
	@Override
	public void setStage(int a){stage = a;}

	@Override
	public void doSomething(ChatInputMap map, String username, String message, PlayerChatEvent event){
		event.setCancelled(true);
		if(getPlayer(username) == null){
			map.removePlayer(username);
		}
		//Tell user: Please Enter a Username
		if(stage == 0){ //stage 1
			if(message.equals(">help")){
				getPlayer(username).sendMessage("There are no help options currently.");
				return;
			}
			if(message.equals(">exit")){
				map.removePlayer(username);
				return;
			}
		}


		

	}
	
	public Player getPlayer(String name){
		return Bukkit.getPlayer(name);
	}

	@Override
	public void doSomething(ChatInputMap map, Player p, String message, PlayerChatEvent event){
		doSomething(map,p.getName().toString(),message,event);
	}

	@Override
	public void cleanup() {

	}
}
