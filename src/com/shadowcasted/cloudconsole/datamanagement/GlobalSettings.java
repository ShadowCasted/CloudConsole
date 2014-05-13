package com.shadowcasted.cloudconsole.datamanagement;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class GlobalSettings {

	private static Plugin plugin;
	public static void setPlugin(Plugin plugin){GlobalSettings.plugin = plugin;}
	
	private FileConfiguration config;
	public FileConfiguration getConfig(){return config;}

	private File settingsFile;
	public File getPlayerFile(){return settingsFile;}

	public boolean save(){try{config.save(settingsFile);return true;}catch(Exception e){ClientHandler.getClientMap().Debug("Couldn't Save File");return false;}}
	
	
	public GlobalSettings(){
		try{
			
			settingsFile = new File(plugin.getDataFolder()+File.separator+"settings"+File.separator+"Settings.yml");
			settingsFile.getParentFile().mkdirs();
			config = YamlConfiguration.loadConfiguration(settingsFile);
			config.options().copyDefaults(true);
			if(!config.contains("Port")){config.addDefault("Port", 1337);}
			if(!config.contains("MaxConnections")){config.addDefault("MaxConnections", 5);}
			if(!config.contains("FullMessage")){config.addDefault("FullMessage", "There Are No Open Slots");}
			if(!config.contains("ThreadedRelease")){config.addDefault("ThreadedRelease", true);}
			if(!config.contains("InGameConfigEditing")){config.addDefault("InGameConfigEditing", true);}
			if(!config.contains("AllowEditingFromConsole")){config.addDefault("AllowEditingFromConsole", false);}
			if(!config.contains("AllowCommandStartStopRestart")){config.addDefault("AllowCommandStartStopRestart", true);}
			
			save();

		}catch(Exception e){ClientHandler.getClientMap().Debug("Really fucked up");}

	}
	
	
	
	
	public int getPort(){return config.getInt("Port");}
	public GlobalSettings setPort(int port){config.set("Port", port);save();ClientHandler.getClientMap().Debug("\n\nThe Port For CC Was Changed. Please Restart The Server For This To Take Into Effect!\n\n");return this;}
	
	public int getMC(){return config.getInt("MaxConnections");}
	public GlobalSettings setMaxConnections(int mc){config.set("MaxConnections", mc);save();return this;}
	
	public String getFM(){return config.getString("FullMessage");}
	public GlobalSettings setFullMessage(String message){config.set("FullMessage", message); save();return this;}
	
	public boolean getTR(){return config.getBoolean("ThreadedRelease");}
	public GlobalSettings setThreadedRelease(Boolean b){config.set("ThreadedRelease", b);save();return this;}

	
	
	
}
