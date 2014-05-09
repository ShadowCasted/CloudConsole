package com.shadowcasted.cloudconsole.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("deprecation")
public abstract class ChatTerminal implements Listener {

	
	public abstract void onAction();
	public abstract String getName();

	
	private static HashMap<String, ChatTerminal> map = new HashMap<String, ChatTerminal>();
	public static HashMap<String, ChatTerminal> getMap(){return map;}


	public static boolean terminate(ChatTerminal t){
		if(map.containsKey(t.getName())){map.remove(t.getName()); return true;}
		else{return false;}
	}
	
	
	public static void addTerminal(ChatTerminal terminal){
		map.put(terminal.getName(), terminal);
	}
	
	private static Plugin plugin;
	public static Plugin getPlugin(){return plugin;}
	
	public static void startListener(Plugin plugin){
		Bukkit.getPluginManager().registerEvents(new Listenerz(), plugin);
		ChatTerminal.plugin = plugin;
	}
	
	
	public static class Listenerz implements Listener{
		@EventHandler
		public void onChatEvent(PlayerChatEvent event){
			if(map.containsKey(event.getPlayer().getName())){
				ChatTerminal.event = event;
				map.get(event.getPlayer().getName()).onAction();
			}
		}
		
		@EventHandler
		public void onPlayerDisconnect(PlayerQuitEvent event){
			if(map.containsKey(event.getPlayer().getName())){map.remove(event.getPlayer().getName());}
		}
		
	
	}
	
	private static PlayerChatEvent event;
	public static PlayerChatEvent getEvent(){return event;}
	
	public static Player getPlayer(){return event.getPlayer();}
	public static String getMessage(){return event.getMessage();}
	
	
}
