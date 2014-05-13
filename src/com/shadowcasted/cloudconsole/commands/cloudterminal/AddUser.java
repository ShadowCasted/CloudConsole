package com.shadowcasted.cloudconsole.commands.cloudterminal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.shadowcasted.cloudconsole.datamanagement.UserConfigClass;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class AddUser {

	
	int stage = 0;
	private UserConfigClass ucc;
	private String user;
	private String pass;
	
	private boolean active = true;
	public boolean stillAlive(){return active;}
	
	
	public boolean Actions(String message, Player username){

		//Tell user: Please Enter a Username
		if(stage == 0){ //stage 1
			user = message;
			if((new UserConfigClass(user)).isReal()){
				username.sendMessage(ChatColor.RED+"That Username Already Exist! Canceling Creation!");
				stage = 69;
				return false;
			}
			stage ++;
			username.sendMessage(ChatColor.GREEN+"Please Enter The Desired Password Next");
			return true;
		}



		if(stage == 1){
			pass = message;
			stage ++;
			username.sendMessage(ChatColor.YELLOW+""+ChatColor.UNDERLINE+"Are You Sure You Want To Create This User?");
			return true;
		}



		if(stage == 2){
			if(message.equalsIgnoreCase("yes")){
				username.sendMessage(ChatColor.RESET+""+ChatColor.GREEN+"Okay, Now Onto Permissions. "
						+ "Please Enter A Desired Permission. If You Are Unsure Of The Permissions, Then Enter "+ChatColor.BLUE+""+ChatColor.BOLD+"PermList"+ChatColor.RESET+""+ChatColor.GREEN+". Or You Can Enter "+ChatColor.BLUE+""+ChatColor.BOLD+"None" + ChatColor.RESET+""+ChatColor.GREEN+" If You Don't Want Them Having Permissions");
				ucc = new UserConfigClass(ClientHandler.getPlugin(),true,user,pass);
				stage++;
			}else{
				username.sendMessage(ChatColor.RED+"Okay, Canceling Creation Then!");
				stage = 69;
				return false;
			}
			return true;
		}



		if(stage == 3){
			if(message.equalsIgnoreCase("None")){
				username.sendMessage(ChatColor.GREEN+"Okay, Moving Onto Commands. Please Enter A Desired Command. Simply Type In The Command "+ChatColor.BOLD+ChatColor.RED+"Without The Slash"+ChatColor.RESET+""+ChatColor.GREEN+" Like It Would Be In Console. If You Don't Want Them Having Any Commands Then Just Enter "+ChatColor.BLUE+ChatColor.BOLD+"None"+ChatColor.RESET+ChatColor.GREEN+".");
				stage+=2;
				return true;
			}
			if(message.equalsIgnoreCase("PermList")||message.equalsIgnoreCase("PermsList")){
				username.sendMessage(ChatColor.GOLD+"===PermsList===\n"+Permissions.AllPermissions());
			}else{
				if(isValidPerm(message)){
					ucc.addPermission(message);
					username.sendMessage(ChatColor.YELLOW+"Do You Want To Enter Another Permission?");
					stage++;
				}else{
					username.sendMessage(ChatColor.RED+"That Isn't A Valid Permission, Please Try Again!");
				}
			}
			return true;
		}

		if(stage == 4){
			if(message.equalsIgnoreCase("yes")){
				username.sendMessage(ChatColor.GREEN+"Please Enter Another Permission");
				stage =3;
				return true;
			}else{
				username.sendMessage(ChatColor.GREEN+"Okay, Moving Onto Commands. Please Enter A Desired Command. Simply Type In The Command "+ChatColor.BOLD+ChatColor.RED+"Without The Slash"+ChatColor.RESET+""+ChatColor.GREEN+" Like It Would Be In Console. If You Don't Want Them Having Any Commands Then Just Enter "+ChatColor.BLUE+ChatColor.BOLD+"None"+ChatColor.RESET+ChatColor.GREEN+".");
				stage++;
				return true;
			}
		}

		if(stage == 5){
			if(message.equalsIgnoreCase("None")){
				username.sendMessage(ChatColor.LIGHT_PURPLE+"Alright It Should Be Set Up!");
				stage = 69;
				return false;
			}
			ucc.addCommand(message);
			username.sendMessage(ChatColor.YELLOW+"Do You Want To Enter Another Command?");
			stage++;
			return true;
		}

		if(stage == 6){
			if(message.equalsIgnoreCase("yes")){
				username.sendMessage(ChatColor.GREEN+"Please Enter Another Command");
				stage =5;
				return true;
			}else{
				username.sendMessage(ChatColor.LIGHT_PURPLE+"Alright It Should Be Set Up!");
				stage = 69;
				return false;
			}
		}


		return true;
	}
	
	
	public boolean isValidPerm(String perm){
		return Permissions.isValidPerm(perm);
	}
}
