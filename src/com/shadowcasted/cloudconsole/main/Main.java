package com.shadowcasted.cloudconsole.main;

import org.bukkit.plugin.java.JavaPlugin;

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
		//System.out.println("Main) Created ClientHandler.. Now Starting it!");
		c.Start();
		ShadowCommand.startListener(this);
		ShadowCommand.addCommand(new CloudCommand());
		//getCommand("CC").setExecutor(new CC());
		
	}
	
	
	@Override
	public void onDisable(){
		c.Stop();
	}
}
