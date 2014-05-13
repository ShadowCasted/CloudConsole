package com.shadowcasted.cloudconsole.parsing;

import org.bukkit.Bukkit;

import com.shadowcasted.cloudconsole.client.ClientCluster;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class Command {

	public Command(String command, ClientCluster c){
		try{
			System.out.println("["+c.getID()+"] "+c.getClient().getUsername()+") " + command);
			command = command.replaceFirst("Command ", "");
			if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
				if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).hasPermission("Console.Commands")){
					if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).canUseCommand(rawCommand(command))||ClientHandler.getDataQueuer().getUserConfig(c.getClient()).canUseCommand("all")){
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
					}else{
						c.getDataTransfer().sendMessage("Sorry but you don't have permissions for doing that command!");
					}
				}else{
					c.getDataTransfer().sendMessage("Sorry but you don't have permissions for doing commands!");
				}
			}
			
		}catch(Exception e){
			try{
				c.getDataTransfer().sendMessage("Failed to do your command!");
			}catch(Exception ex){}
		}
	}
	
	public String rawCommand(String command){
		try{
			command = command.replaceFirst("Command ", "");
			String[] temp = command.split(" ");
			return temp[0];
		}catch(Exception e){}
		return "lolnopenotacommand";
	}
}
