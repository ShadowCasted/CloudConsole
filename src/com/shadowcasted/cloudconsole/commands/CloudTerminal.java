package com.shadowcasted.cloudconsole.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.shadowcasted.cloudconsole.commands.cloudterminal.*;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class CloudTerminal extends ChatTerminal{

	public int stage = 0;



	private AddUser ad = new AddUser();
	private RemoveUser ru = new RemoveUser();


	@Override
	public boolean onAction() {

		if(stag3.equals(Stage.main)){
			if(getMessage().equalsIgnoreCase("config")||getMessage().contains("1")){
				getPlayer().sendMessage(getConfigMessage());
				stag3 = Stage.config;
				return true;
			}

			if(getMessage().equalsIgnoreCase("users")||getMessage().contains("2")){
				getPlayer().sendMessage(getUsersMessage());
				stag3 = Stage.users;
				return true;
			}

			if(getMessage().equalsIgnoreCase("server")||getMessage().contains("3")){
				getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"\nNothing Here Yet.");
				return true;

			}
			if(getMessage().equalsIgnoreCase("exit")||getMessage().contains("4")){
				getPlayer().sendMessage(ChatColor.RED+"Ending CloudTerminal");
				terminate(this);
				return true;
			}
		}

		else if(stag3.equals(Stage.users)){
			if(getMessage().equalsIgnoreCase("list")||getMessage().contains("1")){
				if(hasPermission("cloud.users.list")){
					ClientHandler.getDataQueuer().ListAccounts(getPlayer());
				}else{getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You Do Not Have Permission To List Users!");}
				return true;
			}
			if(getMessage().equalsIgnoreCase("add")||getMessage().contains("2")){
				if(hasPermission("cloud.users.add")){
					getPlayer().sendMessage(ChatColor.GREEN+"Please Enter A Username For This Account");
					ad = new AddUser();
					stag3 = Stage.usersadd;
				}else{getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You Do Not Have Permission To Add Users!");}
				return true;
			}
			if(getMessage().equalsIgnoreCase("remove")||getMessage().contains("3")){
				if(hasPermission("Cloud.users.remove")){
					getPlayer().sendMessage(ChatColor.RED+"Please Enter The Username Of The Account That You Want To Delete In Chat. Or Enter " + ChatColor.BOLD +"List" + ChatColor.RESET + ChatColor.RED+" If You Want The List Of Users.");	
					stag3 = Stage.usersremove;
					ru = new RemoveUser();
				}else{getPlayer().sendMessage(ChatColor.RED+"You Do Not Have Permission To Remove Users!");}
				return true;
			} 
			if(getMessage().equalsIgnoreCase("edit")||getMessage().contains("4")){
				getPlayer().sendMessage(ChatColor.RED+"This Feature Isn't Implemented Yet!");	
				return true;
			} 
			if(getMessage().equalsIgnoreCase("back")||getMessage().contains("5")){
				getPlayer().sendMessage(getStage0Message());
				stag3 = Stage.main;
				return true;
			}
			if(getMessage().equalsIgnoreCase("exit")||getMessage().contains("6")){
				getPlayer().sendMessage(ChatColor.RED+"Exiting CloudTerminal");
				terminate(this);
			}

		}
		else if(stag3.equals(Stage.config)){



			if(getMessage().equalsIgnoreCase("view")||getMessage().contains("1")){
				if(hasPermission("cloud.config.view")){
					ClientHandler.getDataQueuer().ListConfig(getPlayer());
					getPlayer().sendMessage("\n"+getConfigMessage());
				}else{getPlayer().sendMessage(ChatColor.RED+"You Do Not Have Permission To View The Config");}
				return true;
			}
			if(getMessage().equalsIgnoreCase("Edit")||getMessage().contains("2")){
				if(hasPermission("cloud.config.edit")){
					getPlayer().sendMessage(getConfigEditMessage());
					stag3 = Stage.configedit;
					return true;
				}else{getPlayer().sendMessage(ChatColor.RED+"You Do Not Have Permission To Edit The Config");}
				return true;
			}
			if(getMessage().equalsIgnoreCase("back")||getMessage().contains("3")){
				getPlayer().sendMessage(getStage0Message());
				stag3 = Stage.main;
				return true;
			}
			if(getMessage().equalsIgnoreCase("exit")||getMessage().contains("4")){
				getPlayer().sendMessage(ChatColor.RED+"Exiting CloudTerminal");
				terminate(this);
			}

		}


		if(stag3.equals(Stage.configedit)){
			if(getMessage().equalsIgnoreCase("port")||getMessage().contains("1")){
				stag3 = Stage.configedit1;
				stag = ConfigEdit.port;
				getPlayer().sendMessage(ChatColor.GREEN+"\nPlease Enter The Port You Want");
				return true;
			}
			if(getMessage().equalsIgnoreCase("Full Message")||getMessage().contains("2")){
				stag3 = Stage.configedit1;
				stag = ConfigEdit.fullmessage;
				getPlayer().sendMessage(ChatColor.GREEN+"\nPlease Enter What You Want The Full Message To Be");
				return true;
			}
			if(getMessage().equalsIgnoreCase("Max Connection")||getMessage().contains("3")){
				stag3 = Stage.configedit1;
				stag = ConfigEdit.maxconnection;
				getPlayer().sendMessage(ChatColor.GREEN+"\nPlease Enter How Many Clients Should Be Allowed To Connect");
				return true;
			}
			if(getMessage().equalsIgnoreCase("Threaded Release")||getMessage().contains("4")){
				stag3 = Stage.configedit1;
				stag = ConfigEdit.threadedrelease;
				getPlayer().sendMessage(ChatColor.GREEN+"\nPlease Enter What You Want The Threaded Release To Be");
				return true;
			}
			if(getMessage().equalsIgnoreCase("back")||getMessage().contains("5")){
				getPlayer().sendMessage(getConfigMessage());
				stag3 = Stage.config;
				return true;
			}
			if(getMessage().equalsIgnoreCase("exit")||getMessage().contains("6")){
				getPlayer().sendMessage(ChatColor.RED+"Exiting CloudTerminal");
				terminate(this);
			}
		}

		if(stag3.equals(Stage.configedit1)){
			switch(stag){
			case port:
				try{
					int tport = Integer.parseInt(getMessage());
					if(tport < 0){
						getPlayer().sendMessage(ChatColor.RED+"Port Must Be A Positive, Whole Number!\nExiting Port Editing!");
						backToConfigEditing();
						return true;
					}
					if(tport == Bukkit.getServer().getPort()){
						getPlayer().sendMessage(ChatColor.RED+"Port Can Not Be The Same As Minecraft's Port!\nExiting Port Editing!");
						backToConfigEditing();
						return true;
					}
					ClientHandler.getDataQueuer().setPort(tport);
					getPlayer().sendMessage(ChatColor.GREEN+"Port Successfully Changed! A Restart Is Required!");
					backToConfigEditing();
					return true;
				}catch(Exception e){
					getPlayer().sendMessage(ChatColor.RED+"Port Must Be A Positive, Whole Number\nExiting Port Editing!");
					backToConfigEditing();
					return true;
				}
				
			case fullmessage:
				try{
					ClientHandler.getDataQueuer().setFullMessage(getMessage());
					getPlayer().sendMessage(ChatColor.GREEN+"Full Message Set To: " +ChatColor.GOLD+getMessage());
					backToConfigEditing();
					return true;
				}catch(Exception e){
					getPlayer().sendMessage(ChatColor.RED+"Something Wrong Happened. Please Consult The Logs!\nGoing Back To Config Editing!");
					e.printStackTrace();
					backToConfigEditing();
					return true;
				}

			case maxconnection:
				try{
					int mc = Integer.parseInt(getMessage());
					if(mc < 0){
						getPlayer().sendMessage(ChatColor.RED+"The Max Connections Must Be A Positive, Whole Number!\nExiting Port Editing!");
						backToConfigEditing();
						return true;
					}
					ClientHandler.getDataQueuer().setMaxConnections(mc);
					getPlayer().sendMessage(ChatColor.GREEN+"Max Connections Successfully Changed! A Restart May Be Required!");
					backToConfigEditing();
					return true;
				}catch(Exception e){
					getPlayer().sendMessage(ChatColor.RED+"Port Must Be A Positive, Whole Number\nExiting Port Editing!");
					backToConfigEditing();
					return true;
				}

			case threadedrelease:
				try{
					
					boolean b = false;
					if(getMessage().toLowerCase().contains("true")){
						b = true;
					}else if(getMessage().toLowerCase().contains("false")){
						b = false;
					}else{
						getPlayer().sendMessage(ChatColor.RED+"Threaded Release Must Be Set To True Or False!\nGoing Back To Config Editing!");
						backToConfigEditing();
						return true;
					}
					
					ClientHandler.getDataQueuer().setThreadedRelease(b);
					getPlayer().sendMessage(ChatColor.GREEN+"Threaded Release Set To: " +ChatColor.GOLD+b);
					backToConfigEditing();
					return true;
				}catch(Exception e){
					getPlayer().sendMessage(ChatColor.RED+"Something Wrong Happened. Please Consult The Logs!\nGoing Back To Config Editing!");
					e.printStackTrace();
					backToConfigEditing();
					return true;
				}
				
			case NULL:
				getPlayer().sendMessage(ChatColor.RED+"Something Bad Happened! Going Back To ConfigEditing!");
				backToConfigEditing();
				return true;
			}



		}

		if(stag3.equals(Stage.usersadd)){
			if(!ad.Actions(getMessage(), getPlayer())){
				getPlayer().sendMessage(getUsersMessage());
				stag3 = Stage.users;
			}else{return true;}
		}
		if(stag3.equals(Stage.usersremove)){
			if(!ru.Actions(getPlayer(), getMessage())){
				getPlayer().sendMessage(getUsersMessage());
				stag3 = Stage.users;
			}else{return true;}
		}



		return true;

	}








	public void backToConfigEditing(){
		stag3 = Stage.configedit;
		stag = ConfigEdit.NULL;
		getPlayer().sendMessage(getConfigEditMessage());
	}

	public String getStage1Message(){
		return "";
	}

	@Override
	public String getStartingMessage(){
		return ChatColor.GREEN+"\nWelcome to the CloudTerminal. From here you can access any part of the plugin, without having to remember commands. \nPlease Pick An Option:\n"
				+ ChatColor.BLUE+"1)"+ChatColor.AQUA+" Config\n"
				+ ChatColor.BLUE+"2)"+ChatColor.AQUA+" Users\n"
				+ ChatColor.BLUE+"3) "+ChatColor.AQUA+ChatColor.STRIKETHROUGH+"Server\n"
				+ ChatColor.BLUE+"4)"+ChatColor.RED+" Exit\n";
	}

	public String getStage0Message(){
		return ChatColor.GREEN+"\nWelcome to the CloudTerminal.\n"
				+ ChatColor.BLUE+"1)"+ChatColor.AQUA+" Config\n"
				+ ChatColor.BLUE+"2)"+ChatColor.AQUA+" Users\n"
				+ ChatColor.BLUE+"3) "+ChatColor.AQUA+ChatColor.STRIKETHROUGH+"Server\n"
				+ ChatColor.BLUE+"4)"+ChatColor.RED+" Exit\n";}

	public String getUsersMessage(){
		return  ChatColor.GREEN+"\n== User Management ==\n"   
				+ ChatColor.BLUE+"1)"+ChatColor.AQUA+" List\n"
				+ ChatColor.BLUE+"2)"+ChatColor.AQUA+" Add\n"
				+ ChatColor.BLUE+"3)"+ChatColor.AQUA+" Remove\n"
				+ ChatColor.BLUE+"4) "+ChatColor.AQUA+ChatColor.STRIKETHROUGH+"Edit\n"
				+ ChatColor.BLUE+"5)"+ChatColor.GOLD+" Back\n"
				+ ChatColor.BLUE+"6)"+ChatColor.RED+" Exit\n";
	}

	public String getConfigMessage(){
		return  ChatColor.GREEN+"\n== Config Management ==\n"    
				+ ChatColor.BLUE+"1)"+ChatColor.AQUA+" View\n"
				+ ChatColor.BLUE+"2)"+ChatColor.AQUA+" Edit\n"
				+ ChatColor.BLUE+"3)"+ChatColor.GOLD+" Back\n"
				+ ChatColor.BLUE+"4)"+ChatColor.RED+" Exit\n";
	}
	public String getConfigEditMessage(){
		return  ChatColor.GREEN+"\n== Config Editing ==\n"    
				+ ChatColor.BLUE+"1)"+ChatColor.AQUA+" Port\n"
				+ ChatColor.BLUE+"2)"+ChatColor.AQUA+" Full Message\n"
				+ ChatColor.BLUE+"3)"+ChatColor.AQUA+" Max Connections\n"
				+ ChatColor.BLUE+"4)"+ChatColor.AQUA+" Threaded Release\n"
				+ ChatColor.BLUE+"5)"+ChatColor.GOLD+" Back\n"
				+ ChatColor.BLUE+"6)"+ChatColor.RED+ " Exit\n";
	}



	public void Users(int choice){
		switch(choice){



		case 5:
			stag3 = Stage.main;
			getPlayer().sendMessage(getStage0Message());
			return;

		case 6:
			getPlayer().sendMessage("Exiting CloudTerminal");
			terminate(this);
			return;
		}
	}



	private String username = "";
	public CloudTerminal(Player player){
		username = player.getName();
		player.sendMessage(getStartingMessage());
		ChatTerminal.addTerminal(this);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return username;
	}

	public Stage stag3 = Stage.main;
	public ConfigEdit stag = ConfigEdit.NULL;


	public static enum ConfigEdit{
		port,
		maxconnection,
		threadedrelease,
		fullmessage,
		NULL
	}


	public static enum Stage{
		main,
		config, 
		configedit, configedit1,
		server, 
		users,
		usersadd, usersremove
	}

	public boolean hasPermission(String perm){
		boolean op = getPlayer().isOp();
		getPlayer().setOp(false);
		boolean check = getPlayer().hasPermission(perm);
		getPlayer().setOp(op);
		return check;
	}

}
