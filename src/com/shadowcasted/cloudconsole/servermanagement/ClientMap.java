package com.shadowcasted.cloudconsole.servermanagement;

import java.util.HashMap;

import com.shadowcasted.cloudconsole.client.ClientCluster;

public class ClientMap {

	private HashMap<Integer, ClientCluster> ClientClusterMap = new HashMap<Integer, ClientCluster>();
	public synchronized HashMap<Integer, ClientCluster> getClientMap(){return ClientClusterMap;}

	
	public synchronized void addClientCluster(int id, ClientCluster cluster){
		System.out.println("ClientClusterMap) Trying To Add " + id);
		try{
			Debug("ClientClusterMap) Point A " + id);
			if(!ClientClusterMap.containsKey(id)){
				Debug("ClientClusterMap) Point B " + id);
				getClientMap().put(id, cluster);
				Debug("ClientClusterMap) Point C " + id);
				cluster.setSetup(true);
				Debug("ClientClusterMap) Added ID " + id);
			}else{
				Debug("ClientClusterMap) Point D " + id);
				getClientMap().remove(id);
				Debug("ClientClusterMap) Point E " + id);
				getClientMap().put(id, cluster);
				Debug("ClientClusterMap) Point F " + id);
				cluster.setSetup(true);
				Debug("ClientClusterMap) Point G " + id);
				Debug("ClientClusterMap) Added ID " + id);
			}
		}catch(Exception e){e.printStackTrace();Debug("ClientClusterMap) Something Failed In addClientCluster()");}
	}

	public synchronized void removeClientCluster(int id){
		Debug("removeClientCluster() from ClientMap");
		try{
			if(getClientMap().containsKey(id)){getClientMap().remove(id); Debug("ClientClusterMap) Removed ID " + id);}
			else{Debug("ClientClusterMap) The ID " + id + " Doesn't Exist, So Cannot Remove It!");}
		}catch(Exception e){e.printStackTrace();Debug("ClientClusterMap) Something Failed In removeClientCluster()");}
	}

	public void close(){
		try{
			for(ClientCluster c: ClientClusterMap.values()){
				c.getDataTransfer().sendMessage("Alert CloudConsole Stopped!");
				c.Terminate();
			}
		}catch(Exception e){e.printStackTrace();Debug("Error Occured But Was Caught");}
	}


	public void tellAll(final String message){
		new Thread(new Runnable(){
			public void run(){
				try{
					for(ClientCluster c: ClientClusterMap.values()){
						if(c.Alive()){
							if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
								c.getDataTransfer().sendMessage(message);
							}
						}
					}
				}catch(Exception e){}
			}
		}).start();
	}

	public void ConsoleOutput(final String message){
		new Thread(new Runnable(){
			public void run(){
				for(ClientCluster c: ClientClusterMap.values()){
					if(c.Alive()){
						if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
							if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).hasPermission("Console.View")){
								c.getDataTransfer().sendMessage("Console "+message);
							}
						}
					}
				}
			}
		}).start();
	}

	
	public void Debug(final String message){
		new Thread(new Runnable(){
			public void run(){
				for(ClientCluster c: ClientClusterMap.values()){
					if(c.Alive()){
						if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
							if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).hasPermission("Console.Debug")){
								c.getDataTransfer().sendMessage("Debug "+message);
							}
						}
					}
				}

			}
		}).start();
	}
	
	public void ChatOutput(final String message){
		new Thread(new Runnable(){
			public void run(){
				for(ClientCluster c: ClientClusterMap.values()){
					if(c.Alive()){
						if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
							if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).hasPermission("Chat.View")){
								c.getDataTransfer().sendMessage("Chat "+message);
							}
						}
					}
				}

			}
		}).start();
	}

	public synchronized void tellTo(final String message, final String permission){
	//	System.out.println("tellTo() from ClientMap");
		new Thread(new Runnable(){
			public void run(){
				try{
					for(ClientCluster c: ClientClusterMap.values()){
						if(c.Alive()){
							if(ClientHandler.getDataQueuer().isRealUser(c.getClient())){
								if(ClientHandler.getDataQueuer().getUserConfig(c.getClient()).hasPermission(permission)){
									c.getDataTransfer().sendMessage(message);
								}
							}
						}
					}
				}catch(Exception e){}//e.printStackTrace();System.out.println("Error Occured But Was Caught");}
			}
		}).start();;
	}



	public synchronized boolean ClusterExist(int ID){
		//System.out.println("ClusterExist() from ClientMap");
		try{
			if(getClientMap().containsKey(ID)){return true;}
			else{return false;}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public synchronized boolean checkUsername(String name){
		//System.out.println("checkUsername() from ClientMap");
		try{
			for(ClientCluster CC: getClientMap().values()){
				if(CC != null){
					if(CC.getClient().getUsername().equals(name)){
						return true;
					}
				}
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public synchronized ClientCluster getClusterFromName(String name){
	//	System.out.println("getClusterFromName() from ClientMap");
		try{
			for(ClientCluster CC: getClientMap().values()){
				if(CC!= null){
					if(CC.getClient().getUsername().equals(name)){
						return CC;
					}
				}
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public synchronized boolean isLoggedIn(String name){Debug("isLoggedIn() from ClientMap");return checkUsername(name);}

	public synchronized int aliveCount(){
	//	System.out.println("aliveCount() from ClientMap");
		int counter = 0;
		try{
			for(ClientCluster CC: getClientMap().values()){
				if(CC != null){
					if(CC.Alive()){
						counter++;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Debug("ClientMap) Currently there are " + counter +" clients alive!");
		return counter;
	}




}
