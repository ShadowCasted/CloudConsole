package com.shadowcasted.cloudconsole.datamanagement;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class UserConfigClass {

	private static Plugin plugin;
	public static void setPlugin(Plugin plugin){UserConfigClass.plugin = plugin;}
	
	private FileConfiguration config;
	public FileConfiguration getConfig(){return config;}

	private boolean isreal = false;
	public boolean isReal(){
		return isreal;
	}

	private File settingsFile;
	public File getPlayerFile(){return settingsFile;}

	public boolean save(){try{config.save(settingsFile);System.out.println("Saved File ");return true;}catch(Exception e){System.out.println("Couldn't Save File");return false;}}


	public UserConfigClass(String name){
		try{

			if(!(new File(plugin.getDataFolder()+File.separator+"users"+File.separator+name+".yml").exists())){
				isreal = false;
			}else{
				isreal = true;
				settingsFile = new File(plugin.getDataFolder()+File.separator+"users"+File.separator+name+".yml");
				config = YamlConfiguration.loadConfiguration(settingsFile);
				config.options().copyDefaults(true);
			}


		}catch(Exception e){System.out.println("Really fucked up");}

	}

	public UserConfigClass(Plugin plugin, boolean s, String name, String password){
		try{
			if(s){
				settingsFile = new File(plugin.getDataFolder()+File.separator+"users"+File.separator+name+".yml");
				settingsFile.getParentFile().mkdirs();
				config = YamlConfiguration.loadConfiguration(settingsFile);
				config.options().copyDefaults(true);
				config.addDefault("Username", name);	
				config.addDefault("Password", password);
				config.addDefault("Permissions","");
				config.addDefault("Commands", "");
				config.addDefault("ChatAlias", "<[Console]" + name+">");
				config.save(settingsFile);
				isreal = true;
			}else{
				if(!(new File(plugin.getDataFolder()+File.separator+"users"+File.separator+name+".yml").exists())){
					isreal = false;
				}else{
					isreal = true;
					settingsFile = new File(plugin.getDataFolder()+File.separator+"users"+File.separator+name+".yml");
					config = YamlConfiguration.loadConfiguration(settingsFile);
					config.options().copyDefaults(true);
				}

			}
		}catch(Exception e){System.out.println("Really fucked up");}

	}

	public String getChatAlias(){
		return config.getString("ChatAlias");
	}

	public void setChatAlias(String name){
		config.set("ChatAlias", name);
		save();
	}

	public boolean validUsername(String username){
		if(config.getString("Username").equals(username)){return true;}
		else{return false;}
	}

	public boolean validPassword(String password){
		if(config.getString("Password").equals(password)){return true;}
		else{return false;}
	}

	public UserConfigClass setPassword(String password){
		config.set("Password", password);
		save();
		return this;
	}

	public UserConfigClass setUsername(String username){
		config.set("Username", username);
		save();
		return this;
	}

	@SuppressWarnings("unchecked")
	public UserConfigClass addPermission(String perm){
		if(config.getList("Permissions")== null){
			ArrayList<String> permsList = new ArrayList<String>();
			if(!permsList.contains(perm)){permsList.add(perm);}
			config.set("Permissions", permsList);
			save();
		}else{
			ArrayList<String> permslist = (ArrayList<String>) config.getList("Permissions");
			if(!permslist.contains(perm)){permslist.add(perm);}
			config.set("Permissions", permslist);
			save();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public UserConfigClass addCommand(String cmd){
		if(config.getList("Commands")== null){
			ArrayList<String> permslist = new ArrayList<String>();
			if(!permslist.contains(cmd)){permslist.add(cmd);}
			config.set("Commands", permslist);
			save();
		}else{
			ArrayList<String> permslist = (ArrayList<String>) config.getList("Commands");
			if(!permslist.contains(cmd)){permslist.add(cmd);}
			config.set("Commands", permslist);
			save();
		}
		return this;
	}

	public boolean hasPermission(String perm){
		if(isReal()){
			for(String s: getList("Permissions")){
				if(s.equals(perm)){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}



	@SuppressWarnings("unchecked")
	private String[] getList(String path){
		ArrayList<String> permslist = (ArrayList<String>) config.getList(path);
		String[] stockArr = new String[permslist.size()];
		return permslist.toArray(stockArr);
	}


	public boolean canUseCommand(String cmd){
		if(isReal()){
			for(String s: getList("Commands")){
				if(s.equals(cmd)){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}

	public void delete() {
		settingsFile.delete();
		this.isreal = false;
	}

}
