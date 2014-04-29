package com.shadowcasted.cloudconsole.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.shadowcasted.cloudconsole.parsing.Command;
import com.shadowcasted.cloudconsole.parsing.LoginChecker;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

@SuppressWarnings("deprecation")
public class cListener implements Listener{

	public cListener(){
		System.out.println("Listener Started!");
	}
	
	@EventHandler
	public void MessageParsing(MessageEvent event){
		
		if(event.getMessage().startsWith("Login ")){
			new LoginChecker(event.getClientCluster().getClient().getClientCluster()).checkLogin(event.getMessage());
		}else
		if(ClientHandler.getDataQueuer().isRealUser(event.getClientCluster().getClient())){
			if(event.getMessage().startsWith("Message ")){
				System.out.println("["+event.getClientCluster().getID()+"] "+event.getClientCluster().getClient().getUsername()+") " +event.getMessage().replaceFirst("Message ", ""));
			}
			if(event.getMessage().startsWith("Command ")){
				new Command(event.getMessage(),event.getClientCluster());
			}
			if(event.getMessage().startsWith("PING ")){
				event.getClientCluster().getPinger().setLastMessage(event.getMessage());
				//System.out.println(event.getClientCluster().getID()+") Ping Message Was Set");
			}
			
		}else{
			event.getClientCluster().getDataTransfer().sendMessage("Sorry But You Are Not A Valid User");event.getClientCluster().Terminate();
		}
	}
	
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		if(ClientHandler.getCM().PlayerInChat(event.getPlayer().getName())){
			ClientHandler.getCM().doSomething(event.getPlayer().getName(), event.getMessage(), event);
			return;
		}
		ClientHandler.getClientMap().ChatOutput("<"+event.getPlayer().getName().toString()+"> "+event.getMessage());
	}
	
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event){
		if(ClientHandler.getCM().PlayerInChat(event.getPlayer().getName())){
			ClientHandler.getCM().removePlayer(event.getPlayer().getName());
		}
	}
	
	
}
