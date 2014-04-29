package com.shadowcasted.cloudconsole.magic;

import java.net.ServerSocket;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class PortChecker {

	public ServerSocket server;
	
	public PortChecker(String userchecking, String ip, int port){
		if(Bukkit.getPlayer(userchecking)== null){
			return;
		}
		if(ClientHandler.alive){
			Bukkit.getPlayer(userchecking).sendMessage(ChatColor.RED+"Please Stop CloudConsole Before Checking Ports!");
			return;
		}
		if(Bukkit.getPort() == port){
			Bukkit.getPlayer(userchecking).sendMessage(ChatColor.RED+"Minecraft Is Already Running On That Port!");
			return;
		}
		new Server(ip,userchecking,port, this).start();
		
		
	}
	
	
	public static class Server extends Thread{
		public int port;
		public String ip, username;
		public PortChecker portchecker;
		public Server(String ip, String username, int port, PortChecker p){
			this.port = port;
			this.ip = ip;
			this.username = username;
			this.portchecker = p;
		}
		public void run(){
			try{
				portchecker.server = new ServerSocket(port);
				new Client(ip, username, port, portchecker).start();
				Socket s = portchecker.server.accept();
			}catch(Exception e){}
			System.out.println("Thread For Server For PortChecker Died");
		}
	}
	public static class Client extends Thread{
		public String ip, username;
		public int port;
		public PortChecker c;
		public Client(String ip, String username, int port, PortChecker p){
			this.username = username;
			this.ip = ip;
			this.port = port;
			c = p;
		}
		
		public void run(){
			try{
				Socket sock = new Socket(ip,port);
				sock.close();
				c.server.close();
				Bukkit.getScheduler().runTask(ClientHandler.getPlugin(), new Runnable(){
					public void run(){
						if(Bukkit.getPlayer(username)!= null){
							Bukkit.getPlayer(username).sendMessage(ChatColor.GREEN+"Port Is Open");
						}
					}
				});
			}catch(Exception e){
				Bukkit.getScheduler().runTask(ClientHandler.getPlugin(), new Runnable(){
					public void run(){
						if(Bukkit.getPlayer(username)!= null){
							Bukkit.getPlayer(username).sendMessage(ChatColor.RED+"Port Isnt Open");
						}
					}
				});
				e.printStackTrace();
				try{
					c.server.close();
				}catch(Exception ex){}
			}

			System.out.println("Thread For Client For PortChecker Died");
		}
		
	}
}
