package com.shadowcasted.cloudconsole.servermanagement;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.shadowcasted.cloudconsole.client.ClientCluster;
import com.shadowcasted.cloudconsole.client.firePit;
import com.shadowcasted.cloudconsole.datamanagement.DataQueuer;
import com.shadowcasted.cloudconsole.listener.cListener;

public class ClientHandler extends Thread{

	private static ClientHandler ch = null;
	public static ClientHandler getCH(){return ch;}
	
	private static ServerSocket server;
	public synchronized static void setServerSocket(ServerSocket server){ClientHandler.server = server;}
	public synchronized static ServerSocket getServerSocket(){return server;}
	
	private static ClientMap clientmap;
	public synchronized static void setClientMap(ClientMap map){clientmap = map;}
	public synchronized static ClientMap getClientMap(){return clientmap;}
	
	public static boolean alive = true;
	public synchronized static void setAlive(Boolean setting){alive = setting;};
	public synchronized static boolean getAlive(){return alive;}
	
	private static DataQueuer DQ;
	public synchronized static void setDataQueuer(DataQueuer DQ){ClientHandler.DQ = DQ;}
	public synchronized static DataQueuer getDataQueuer(){return DQ;}
	
	private static Plugin plugin;
	public synchronized static Plugin getPlugin(){return plugin;}
	
	public ClientHandler(Plugin plugin){
		if(!alreadyRunning()){
			ch = this;
			ClientHandler.plugin = plugin;
			setDataQueuer(new DataQueuer());
			setClientMap(new ClientMap());
			
			Bukkit.getServer().getPluginManager().registerEvents(new cListener(), plugin);
		 	addFilter();
		}else{
			Debug("ClientHandler) Cloud Console Is Already Running... Please Do Something To Stop It, Such As Force Restarting The Server!");
		}
	}
	
	public void Stop(){
		try{
			if(!alreadyRunning()){
				Debug("ClientHandler Wasn't Running");
				return;
			}
			alive = false;
			server.close();
			clientmap.close();
			Debug("ClientHandler Successfully Closed!");
			
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	private static void Debug(String text){clientmap.Debug(text);}
	
	public void Start(){
		if(!alreadyRunning()){
			try{
				this.start();
			}catch(Exception e){e.printStackTrace();}
		}else{
			Debug("Already Running");
		}
	}
	
	public boolean alreadyRunning(){
		for(Thread t: Thread.getAllStackTraces().keySet()){
			if(t.getName().contains("CloudConsole_Thingy_Please_Don't_End_This")){
				return true;
			}
		}
		return false;
	}
	
	public synchronized void Terminate(int ID){
		clientmap.removeClientCluster(ID);
	}
	
	/*
	public synchronized void tellAll(String msg){
		clientmap.tellAll(msg);
	}
	
	public synchronized void chatOutput(String string) {
		clientmap.ChatOutput(string);
	}
	*/
	public void run(){
		try {
			this.setName("CloudConsole_Thingy_Please_Don't_End_This");
			
			setServerSocket(new ServerSocket(getDataQueuer().getPort()));
			Debug("ClientHandler) Server Started On " + getDataQueuer().getPort());
			System.out.println("CloudConsole Started On Port " + getDataQueuer().getPort());
			int id = 1;
			while(getAlive()){
				try{
					Socket tempSock = getServerSocket().accept();
					if(getClientMap().aliveCount() < getDataQueuer().getMaxConnections()){
						ClientCluster c = new ClientCluster(id, tempSock);
						getClientMap().addClientCluster(id, c);
						c.setup();
						Debug("ClientHandler) ClientCluster Added!");
						id++;
					}else{
						new firePit(tempSock);
						Debug("ClientHandler) Another one bites the dust");
					}
				}catch(Exception e){Debug("Error Caught at ClientHandler(129)");}
			}
		} catch (Exception e) {Debug("Error Caught at ClientHandler(131)");}
		Debug("ClientHandler Thread Died");
		System.out.println("CloudConsole's Server Stopped!");
	}
	
	
	
	public void addFilter(){
		((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(filter);
	}
	
	private Filter filter = new Filter() {
		
		public Result filter(LogEvent event) { 
			try{
				//Utilities2.say2all( "["+event.getLevel().toString()+"] "+event.getMessage());
				getClientMap().ConsoleOutput("["+event.getLevel().toString()+"] " +event.getMessage().getFormattedMessage().toString());
			}catch(Exception e){}
			return null;
		}
		
		public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object... arg4) {
			return null;
		}
		
		public Result filter(Logger arg0, Level arg1, Marker arg2, Object arg3, Throwable arg4) {
			return null;
		}
		
		public Result filter(Logger arg0, Level arg1, Marker arg2, Message arg3, Throwable arg4) {
			return null;
		}
		 
		public Result getOnMatch() {
			return null;
		}
		
		public Result getOnMismatch() {
			return null;
		}
		
	};
	
}
