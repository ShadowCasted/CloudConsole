package com.shadowcasted.cloudconsole.client;

import java.net.Socket;

import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;


public class ClientCluster {
	
	private int ID;
	public synchronized void setID(int ID){this.ID = ID;}
	public synchronized int getID(){return ID;}
	
	private Client client;
	public synchronized void setClient(Client client){this.client = client;}
	public synchronized Client getClient() {return client;}

	private DataTransfer datamanager;
	public synchronized void setDataTransfer(DataTransfer datatransfer){datamanager = datatransfer;}
	public synchronized DataTransfer getDataTransfer(){return datamanager;}
	
	private boolean setup = false;
	public synchronized boolean isSetup(){return setup;}
	public synchronized void setSetup(Boolean b){setup = b;}
	
	private boolean active = false;
	public synchronized void setActive(Boolean active){this.active = active;}
	public synchronized boolean getActive(){return active;}
	
	private Pinger ping;
	public synchronized Pinger getPinger(){return ping;}
	public synchronized void setPinger(Pinger p){ping = p;}
	
	public ClientCluster (int id, Socket sock){
		active = true;
		ID = id;
		client = new Client(this, sock);
		
	}
	

	public ClientCluster setup(){
		
		datamanager = new DataTransfer(this);
		datamanager.start();
		
		ping = new Pinger(this);
		ping.start();
		return this;
	}
	
	/*
	public ClientCluster setup(){
		active = true;
		System.out.println("==Setting up Client " + id+"==");
		
		setID(id);
		System.out.println(id+") Stored ID in ClientCluster!");
		
		setClient(new Client(this, sock)); 
		System.out.println(id+") Created Client Object!");
		
		setDataTransfer(new DataTransfer(this));
		getDataTransfer().start();
		
		setPinger(new Pinger(this));
		getPinger().start();
		System.out.println(id+") Created And Started DataTransfer Object!");
		return this;
	}
	*/
	
	
	
	public boolean Alive(){
		if(getClient().getActive() || getDataTransfer().getActive()){return true;}
		else{return false;}
	}
	
	private boolean isBeingTerminated = false;
	public synchronized boolean getTermination(){return isBeingTerminated;}
	public synchronized void setTermination(Boolean b){isBeingTerminated = b;}
	
	public synchronized boolean Terminate(){
		if(setup && !isBeingTerminated){
			try{
				ClientHandler.getClientMap().Debug(ID + ") Attempting to terminate ");
				setTermination(true);
				active = false;
				ping.setAlive(false);
				datamanager.setAlive(false);
				datamanager.setClientCluster(null);
				client.getSocket().close();
			}catch(Exception e){}
				ClientHandler.getCH().Terminate(getID());
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	

}
