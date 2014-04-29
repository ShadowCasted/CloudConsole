package com.shadowcasted.cloudconsole.datamanagement;

import java.io.File;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.shadowcasted.cloudconsole.client.Client;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class DataQueuer {

	public DataQueuer(){
		UserConfigClass.setPlugin(ClientHandler.getPlugin());
		GlobalSettings.setPlugin(ClientHandler.getPlugin());
		
		
		gs = new GlobalSettings();
	}
	
	private HashMap<String, UserConfigClass> map = new HashMap<String, UserConfigClass>();
	public synchronized void removeUserFromMap(Client c){
		if(map.containsKey(c.getUsername())){
			map.remove(c.getUsername());
		}
	}
	public synchronized void putUserInMap(Client c){
		System.out.println("Putting " + c.getUsername() + " into the map!");
		map.put(c.getUsername(), new UserConfigClass(ClientHandler.getPlugin(),false,c.getUsername(),c.getPassword()));
		System.out.println("Successfully put " +c.getUsername() + " into the map!");
	}
	
	public synchronized UserConfigClass getUserConfigFromMap(Client c){
		if(map.containsKey(c.getUsername())){
			return map.get(c.getUsername());
		}else{
			putUserInMap(c);
			return map.get(c.getUsername());
		}
	}
	
	public UserConfigClass getUserConfig(Client c){
		return getUserConfigFromMap(c);
		//return new UserConfigClass(plugin,false,c.getUsername(),c.getPassword());
	}
	
	public synchronized boolean isRealUser(Client c){
		return isRealUser(c.getUsername(),c.getPassword());
	}
	
	
	public synchronized boolean isRealUser(String username, String password){
		if(map.containsKey(username)){return map.get(username).validPassword(password) && map.get(username).validUsername(username);}
		UserConfigClass c = new UserConfigClass(username);
		if(c.isReal()){
			if(c.validUsername(username) && c.validPassword(password)){
				System.out.println(username + " was validated and added to the list");
				map.put(username, new UserConfigClass(ClientHandler.getPlugin(), false, username, password));
				return true;
			}
		}
		return false;
	}
	
	
	public synchronized int getMaxConnections(){return getGlobalSettings().getMC();}
	public synchronized void setMaxConnections(int mc){getGlobalSettings().setMaxConnections(mc);}
	
	public synchronized int getPort(){return getGlobalSettings().getPort();}
	public synchronized void setPort(int port){getGlobalSettings().setPort(port);}
	
	public synchronized String getDeathMessage(){return getGlobalSettings().getFM();}
	public synchronized void setFullMessage(String message){getGlobalSettings().setFullMessage(message);}
	
	public synchronized boolean ThreadedRelease(){return getGlobalSettings().getTR();}
	public synchronized void setThreadedRelease(Boolean b){getGlobalSettings().setThreadedRelease(b);}
	
	public synchronized boolean getAIGCE(){return getGlobalSettings().getInGameConfigEditing();}
	public synchronized void setAIGCE(Boolean b){getGlobalSettings().setInGameConfigEditing(b);}
	
	public synchronized void setAEFC(boolean s){getGlobalSettings().setAllowEditingFromConsole(s);}
	public synchronized boolean getAEFC(){return getGlobalSettings().getAllowEditingFromConsole();}
	
	public synchronized void setCSSR(Boolean b){getGlobalSettings().setAllowCommandStopStartRestart(b);}
	public synchronized boolean getCSSR() {return getGlobalSettings().getAllowCommandStartStopRestart();}
	
	
	private GlobalSettings gs;
	public synchronized GlobalSettings getGlobalSettings(){return gs;}
	
	
	
	public synchronized void ListAccounts(Player p){
		if(p==null){return;}
		try{
			File userdir = new File(ClientHandler.getPlugin().getDataFolder()+File.separator+"users");
			if(userdir.exists()){
				if(userdir.isDirectory()){
					p.sendMessage(ChatColor.GOLD+"====User List====");
					for(File f: userdir.listFiles()){
						p.sendMessage(ChatColor.AQUA+f.getName().toString().replace(".yml", ""));
					}
				}
			}
		}catch(Exception e){p.sendMessage("Failed To Get List!");}
	}
	public synchronized String getChatFormat(Client client) {
		String name = client.getUsername();
		if(map.containsKey(name)){return map.get(name).getChatAlias();}
		return client.getUsername();
	}
}
