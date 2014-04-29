package com.shadowcasted.cloudconsole.magic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings("deprecation")
public class ExampleParser extends ChatInputStuff {

	private int stage = 0;
	@Override public int getStage(){return stage;}
	@Override public void setStage(int a){stage = a;}
	
	@Override public void doSomething(ChatInputMap map, String username, String message, PlayerChatEvent event){
		event.setCancelled(true);
		/*
		 * Note this requires something to inform them. This only works after they say something, not before.
		 */
		if(stage == 0){
			 Bukkit.getPlayer(username).sendMessage("The Thing you just said was " + message+", right?");
			stage++;
			return;
		}else if(stage == 2){
			if(message.toLowerCase().contains("yes")){
				Bukkit.getPlayer(username).sendMessage("Okay, so I did hear you correctly :)");
			}else{
				Bukkit.getPlayer(username).sendMessage("Huh, my hearing must be off :(");	
			}
			Bukkit.getPlayer(username).sendMessage("Anyways, how are you?");	
			stage++;
			return;
		}else if(stage >= 3){
			Bukkit.getPlayer("Ah Okay. Anyways I gtg. Ttyl!");
			stage = 9001;
			map.removePlayer(username);
		}

	}

	@Override
	public void doSomething(ChatInputMap map, Player p, String message, PlayerChatEvent event){
		doSomething(map, p.getName(),message,event);
	}

	@Override
	public void cleanup() {

	}

}
