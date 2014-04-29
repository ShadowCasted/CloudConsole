package com.shadowcasted.cloudconsole.magic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import com.shadowcasted.cloudconsole.datamanagement.UserConfigClass;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

@SuppressWarnings("deprecation")
public class AddUser extends ChatInputStuff{

	private String username;


	private int stage = 0;
	@Override
	public int getStage(){return stage;}
	@Override
	public void setStage(int a){stage = a;}

	private UserConfigClass ucc;

	private String user;
	private String pass;

	@Override
	public void doSomething(ChatInputMap map, String username, String message, PlayerChatEvent event){
		event.setCancelled(true);
		this.username = username;
		//Requires to send a message to the user before hand...


		//Tell user: Please Enter a Username
		if(stage == 0){ //stage 1
			user = message;
			if((new UserConfigClass(user)).isReal()){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.RED+"That Username Already Exist! Canceling Creation!");
				map.removePlayer(username);
				stage = 69;
				return;
			}
			stage ++;
			Bukkit.getPlayer(this.username).sendMessage(ChatColor.GREEN+"Please Enter The Desired Password Next");
			return;
		}



		if(stage == 1){
			pass = message;
			stage ++;
			Bukkit.getPlayer(this.username).sendMessage(ChatColor.YELLOW+""+ChatColor.UNDERLINE+"Are You Sure You Want To Create This User?");
			return;
		}



		if(stage == 2){
			if(message.equalsIgnoreCase("yes")){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.RESET+""+ChatColor.GREEN+"Okay, Now Onto Permissions. "
						+ "Please Enter A Desired Permission. If You Are Unsure Of The Permissions, Then Enter "+ChatColor.BLUE+""+ChatColor.BOLD+"PermList"+ChatColor.RESET+""+ChatColor.GREEN+". Or You Can Enter "+ChatColor.BLUE+""+ChatColor.BOLD+"None" + ChatColor.RESET+""+ChatColor.GREEN+" If You Don't Want Them Having Permissions");
				ucc = new UserConfigClass(ClientHandler.getPlugin(),true,user,pass);
				stage++;
			}else{
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.RED+"Okay, Canceling Creation Then!");
				stage = 69;
				map.removePlayer(username);
			}
			return;
		}



		if(stage == 3){
			if(message.equalsIgnoreCase("None")){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.GREEN+"Okay, Moving Onto Commands. Please Enter A Desired Command. Simply Type In The Command "+ChatColor.BOLD+ChatColor.RED+"Without The Slash"+ChatColor.RESET+""+ChatColor.GREEN+" Like It Would Be In Console. If You Don't Want Them Having Any Commands Then Just Enter "+ChatColor.BLUE+ChatColor.BOLD+"None"+ChatColor.RESET+ChatColor.GREEN+".");
				stage+=2;
				return;
			}
			if(message.equalsIgnoreCase("PermList")||message.equalsIgnoreCase("PermsList")){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.GOLD+"===PermsList===\n"+Permissions.AllPermissions());
			}else{
				if(isValidPerm(message)){
					ucc.addPermission(message);
					Bukkit.getPlayer(this.username).sendMessage(ChatColor.YELLOW+"Do You Want To Enter Another Permission?");
					stage++;
				}else{
					Bukkit.getPlayer(this.username).sendMessage(ChatColor.RED+"That Isn't A Valid Permission, Please Try Again!");
				}
			}
			return;
		}

		if(stage == 4){
			if(message.equalsIgnoreCase("yes")){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.GREEN+"Please Enter Another Permission");
				stage =3;
				return;
			}else{
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.GREEN+"Okay, Moving Onto Commands. Please Enter A Desired Command. Simply Type In The Command "+ChatColor.BOLD+ChatColor.RED+"Without The Slash"+ChatColor.RESET+""+ChatColor.GREEN+" Like It Would Be In Console. If You Don't Want Them Having Any Commands Then Just Enter "+ChatColor.BLUE+ChatColor.BOLD+"None"+ChatColor.RESET+ChatColor.GREEN+".");
				stage++;
				return;
			}
		}

		if(stage == 5){
			if(message.equalsIgnoreCase("None")){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.LIGHT_PURPLE+"Alright It Should Be Set Up!");
				map.removePlayer(username);
				stage = 69;
				return;
			}
			ucc.addCommand(message);
			Bukkit.getPlayer(this.username).sendMessage(ChatColor.YELLOW+"Do You Want To Enter Another Command?");
			stage++;
			return;
		}

		if(stage == 6){
			if(message.equalsIgnoreCase("yes")){
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.GREEN+"Please Enter Another Command");
				stage =5;
				return;
			}else{
				Bukkit.getPlayer(this.username).sendMessage(ChatColor.LIGHT_PURPLE+"Alright It Should Be Set Up!");
				map.removePlayer(username);
				stage = 69;
				return;
			}
		}

		if(stage >= 10){
			map.removePlayer(username);
			return;
		}



	}

	@Override
	public void doSomething(ChatInputMap map, Player p, String message, PlayerChatEvent event){
		doSomething(map, p.getName().toString(),message,event);
	}

	@Override
	public void cleanup() {

	}


	public boolean isValidPerm(String perm){
		return Permissions.isValidPerm(perm);
	}


}
