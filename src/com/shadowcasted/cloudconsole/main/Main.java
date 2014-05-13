package com.shadowcasted.cloudconsole.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.shadowcasted.cloudconsole.commands.ChatTerminal;
import com.shadowcasted.cloudconsole.commands.CloudCommand;
import com.shadowcasted.cloudconsole.commands.ShadowCommand;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class Main extends JavaPlugin{

	
	public static void main(String[] args){
		
	}
	
	private ClientHandler c;
	public ClientHandler getClientHandler(){return c;}
	
	@Override
	public void onEnable(){
		c = new ClientHandler(this);
		ChatTerminal.startListener(this);
		c.Start();
		ShadowCommand.startListener(this);
		ShadowCommand.addCommand(new CloudCommand());
		
	}
	
	
	@Override
	public void onDisable(){
		c.Stop();
	}
}
