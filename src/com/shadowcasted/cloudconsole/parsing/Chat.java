package com.shadowcasted.cloudconsole.parsing;


import org.bukkit.Bukkit;

import com.shadowcasted.cloudconsole.client.ClientCluster;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class Chat {

	public Chat(String text, ClientCluster c){
		try{
			System.out.println("["+c.getID()+"] "+c.getClient().getUsername()+") " + text);
			if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
				if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).hasPermission("Chat.Talk")){
					Bukkit.broadcastMessage(ClientHandler.getDataQueuer().getChatFormat(c.getClient())+" "+text);
				}else{
					c.getDataTransfer().sendMessage("Sorry but you don't have permissions for doing commands!");
				}
			}
		}catch(Exception e){}
	}
}
