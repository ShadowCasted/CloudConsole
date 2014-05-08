package com.shadowcasted.cloudconsole.commands;


import java.io.IOException;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.shadowcasted.cloudconsole.magic.AddUser;
import com.shadowcasted.cloudconsole.magic.Console;
import com.shadowcasted.cloudconsole.magic.DeleteUser;
import com.shadowcasted.cloudconsole.magic.EditPort;
import com.shadowcasted.cloudconsole.magic.PortChecker;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class CC implements CommandExecutor{

	
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		try{
			
			if(args[0].equals("checkport")){ 
				new PortChecker(sender.getName(),args[1],Integer.parseInt(args[2]));
				return true;
			}
			
			
			if(ClientHandler.getCM() == null){
				sender.sendMessage("Handler is null!");
				return true;
			}
			if(ClientHandler.getDataQueuer()== null){
				sender.sendMessage("DataQueuer is null!");
				return true;
			}
			
			
			if(sender instanceof Player){

				
				if(args[0].equalsIgnoreCase("Stop")){
					if(sender instanceof Player){
						sender.sendMessage(ChatColor.RED+"Tried Stopping The Service!");
						ClientHandler.getCH().Stop();
					}else{
						System.out.println("Stopping The Service From Console Has Been Not Implemented");
					}
					return true;
				}
				
				if(args[0].equalsIgnoreCase("Restart")){
					if(sender instanceof Player){
						Bukkit.getServer().reload();
						sender.sendMessage(ChatColor.LIGHT_PURPLE+"Tried Restarting The Service!");
					}else{
						System.out.println("Restarting The Service From Console Has Not Been Implemented");
					}
					return true;
				}
						
				if(args[0].equalsIgnoreCase("Start")){
					if(sender instanceof Player){
						Bukkit.getServer().reload();
						sender.sendMessage(ChatColor.GREEN+"Tried Starting The Service!");
					}else{
						System.out.println("Starting The Service From Console Has Not Been Implemented");
					}
					return true;
				}
				
			}
			
			
			
			
			
			
			
			
			
			if(args[0].equals("test")){
				sender.sendMessage(available(Integer.parseInt(args[1]))+"");
				return true;
			}
			
			
			
			
			
			if(args[0].equalsIgnoreCase("editport")){
				if(sender instanceof Player){
					sender.sendMessage(ChatColor.GREEN+"Please Enter The Desired Port In Chat.");
					ClientHandler.getCM().addToMap((Player)sender, new EditPort());
				}
			}
			
			
			
			if(args[0].equalsIgnoreCase("listaccounts")){
				if(sender instanceof Player){
					ClientHandler.getDataQueuer().ListAccounts((Player)sender);
					return true;
				}else{
					System.out.println("Not Implemented For Console");
				}
			}
			
			if(args[0].equalsIgnoreCase("delete")){
				if(sender instanceof Player){
					sender.sendMessage(ChatColor.RED+"Please Enter The Username Of The Account That You Want To Delete In Chat. Or Enter " + ChatColor.BOLD +"List" + ChatColor.RESET + ChatColor.RED+" If You Want The List Of Users.");
					ClientHandler.getCM().addToMap((Player)sender, new DeleteUser());
					return true;
				}else{
					/*
					 * Create Console Here
					 */
					System.out.println("Not Implemented For Console");
				}
			}
			
			if(args[0].equals("Shadow'sConsole")){
				if(!(sender instanceof Player)){return false;}
					ClientHandler.getCM().addToMap((Player)sender, new Console());
					sender.sendMessage("You Have Managed To Get Into The Dev Console");
					return true;
			}
			
			if(args[0].equalsIgnoreCase("CreateUser")){
				if(sender instanceof Player){
					sender.sendMessage(ChatColor.GREEN+"Please Enter A Username For This Account");
					ClientHandler.getCM().addToMap((Player)sender, new AddUser());
					return true;
				}else{
					/*
					 * Create User By ConsoleHere
					 */
					System.out.println("Not Implemented For Console");
					
				}
			}
			
		}catch(Exception e){e.printStackTrace();}
		return false;
	}

	

	/*
	 *  /cc addUser <username>:<password>
	 *  	ask for a password
	 *  	ask for permissions
	 *  		ask to add more permissions
	 *  	ask for commands
	 *  		ask to add more commands
	 *  
	 *  /cc addPerm <username> <permission>
	 *  
	 *  /cc addCommand <username> <command>
	 *  
	 *  /cc passwd <username> 
	 *  	Ask for a password	
	 *  
	 *  /cc deleteuser <username>
	 *  	ask if you are sure
	 *  	ask again
	 *  
	 *  
	 *  que list
	 *  HashMap<String, DataObject>
	 *  
	 *  if(map.contains(event.getPlayer().getName())){
	 *  	object.doSomething(event.getMessage());
	 *  }
	 *  
	 *  public class something{
	 *  	
	 *  	int stage = 0;
	 *  
	 *  	public doSomething(){
	 *  		if(stage == 0){
	 *  			//do stuff based off of the stage
	 *  			//if closing value, then stage ++;
	 *  			return
	 *  		}
	 *  	}
	 *  
	 *  
	 *  }
	 *  
	 *  
	 */
	
	private static boolean available(int port) {
	    System.out.println("--------------Testing port " + port);
	    Socket s = null;
	    try {
	        s = new Socket("24.207.226.175", port);

	        // If the code makes it this far without an exception it means
	        // something is using the port and has responded.
	        System.out.println("--------------Port " + port + " is not available");
	        return false;
	    } catch (IOException e) {
	        System.out.println("--------------Port " + port + " is available");
	        return true;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("You should ClientHandler this error." , e);
	            }
	        }
	    }
	}
	
}
