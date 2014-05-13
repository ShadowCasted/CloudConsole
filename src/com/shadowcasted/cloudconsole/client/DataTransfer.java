package com.shadowcasted.cloudconsole.client;

import org.bukkit.Bukkit;

import com.shadowcasted.cloudconsole.listener.MessageEvent;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class DataTransfer extends Thread{

	private String ThreadName = null;
	public synchronized void setThreadName(String name){ThreadName = name; this.setName(name);}
	public synchronized String getThreadName(){return ThreadName;}

	public boolean getActive(){return true;}

	private ClientCluster cluster;
	public synchronized void setClientCluster(ClientCluster clientcluster){cluster = clientcluster;}
	public synchronized ClientCluster getClientCluster(){return cluster;}

	private Client getClient(){return cluster.getClient();}

	public boolean alive = true;
	public synchronized boolean Alive(){return alive;}
	public synchronized void setAlive(Boolean b){alive = b;}
	
	public DataTransfer(ClientCluster clientcluster){
		setClientCluster(clientcluster);				 	
		//setClient(clientcluster.getClient());
		setThreadName("Msg4"+cluster.getID());							  	
	}

	public synchronized boolean sendMessage(String msg){
		try{
			getClient().getWriter().append(msg);
			getClient().getWriter().newLine();
			getClient().getWriter().flush();
			return true;
		}catch(Exception e){
			try{
				cluster.Terminate();
			}catch(Exception ex){}
			return false;
		}
	}



	//ben2525 github
	@Override
	public void run(){
		int ID = cluster.getID() + 0;
		try{
			String msg = "";
			setThreadName("Msg4"+cluster.getID());
			while(alive){
				try{
					while((msg = getClient().getReader().readLine())!= null){
						//getParser().parse(msg);
						try{
							Bukkit.getServer().getPluginManager().callEvent(new MessageEvent(msg,cluster));
						}catch(Exception e){e.printStackTrace();}
					}
				}catch(Exception e){if(cluster!= null){cluster.Terminate();}ClientHandler.getClientMap().Debug(ID+") Ending Messager");break;}
			}
		}catch(Exception e){e.printStackTrace();}
		ClientHandler.getClientMap().Debug(ID+") "+ThreadName+"Stopped!");

	}




}
