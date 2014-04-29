package com.shadowcasted.cloudconsole.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	private ClientCluster clientcluster;
	public synchronized void setClientCluster(ClientCluster cluster){clientcluster = cluster;}
	public synchronized ClientCluster getClientCluster(){return clientcluster;}
	
	private Socket sock = null;
	public synchronized void setSocket(Socket sock){this.sock = sock;}
	public synchronized Socket getSocket(){return sock;}
	
	private boolean active;
	public synchronized void setActive(boolean active){this.active = active;}
	public synchronized boolean getActive(){return active;}
	
	private String username = "Not Logged In";
	public synchronized void setUsername(String username){this.username = username;}
	public synchronized String getUsername(){return username;}
	
	private String password = "Not Valid Password";
	public synchronized void setPassword(String password){this.password = password;}
	public synchronized String getPassword(){return password;}
	
	private BufferedWriter writer = null;
	public synchronized void setBufferedWriter(BufferedWriter writer){System.out.println("setBufferedWriter() from Client");this.writer = writer;}
	public synchronized void setSocketWriter(){try {writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));} catch (IOException e) {}}
	public synchronized BufferedWriter getWriter(){return writer;}
	
	private BufferedReader reader = null;
	public synchronized void setBufferedReader(BufferedReader reader){this.reader = reader;}
	public synchronized void setSocketReader(){try{reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));}catch(IOException e){}}
	public synchronized BufferedReader getReader(){return reader;}
	
	public Client(ClientCluster cluster, Socket sock){
		setClientCluster(cluster);
		setSocket(sock);
		setSocketWriter();
		setSocketReader();
		setActive(true);
	}
	
	
	/*
	 * Shortcut :P
	 */
	public synchronized boolean sendMessage(String msg){return getClientCluster().getDataTransfer().sendMessage(msg);}
	
	
}
