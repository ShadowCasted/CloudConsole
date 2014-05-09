package com.shadowcasted.cloudconsole.commands;

import org.bukkit.entity.Player;

public class CloudTerminal extends ChatTerminal{

	public int stage = 0;
	
	@Override
	public void onAction() {
		if(stage == 0){
			if(getMessage().equalsIgnoreCase("config")||getMessage().contains("1")){
				
				
			}
			if(getMessage().equalsIgnoreCase("users")||getMessage().contains("2")){
				
				
			}
			if(getMessage().equalsIgnoreCase("server")||getMessage().contains("3")){
				getPlayer().sendMessage("Nothing Here Yet."+"\n"+getStage0Message());
				
			}
			if(getMessage().equalsIgnoreCase("exit")||getMessage().contains("4")){
				getPlayer().sendMessage("Ending CloudTerminal");
				terminate(this);
				return;
			}
		}
		
	}

	
	
	
	
	
	
	
	
	public String getStage1Message(){
		return "";
	}
	
	
	public String getStartingMessage(){
		return "Welcome to the CloudTerminal. From here you can access any part of the plugin, without having to remember commands. \nPlease Pick An Option:\n"
				+ "1) Config\n"
				+ "2) Users\n"
				+ "3) Server\n"
				+ "4) Exit";
	}

	public String getStage0Message(){
		return("Welcome to the CloudTerminal.\n"
				+ "1) Config\n"
				+ "2) Users\n"
				+ "3) Server\n"
				+ "4) Exit");
	}


	private String username = "";
	public CloudTerminal(Player player){
		username = player.getName();
		ChatTerminal.addTerminal(this);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return username;
	}
	
	
}
