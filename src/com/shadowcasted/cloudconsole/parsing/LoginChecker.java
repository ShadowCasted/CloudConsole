package com.shadowcasted.cloudconsole.parsing;

import com.shadowcasted.cloudconsole.client.ClientCluster;
import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class LoginChecker {

	private ClientCluster CC;
	public LoginChecker(ClientCluster cc){CC = cc;}
	
	public void checkLogin(String msg){
		try{
			if(containsMoreThanNeeded(msg)){
				CC.getDataTransfer().sendMessage("Sorry but that was an invalid username/password");
				CC.Terminate();
				return;
			}
			msg = msg.replaceFirst("Login ", "");
			String[] usernpass = msg.split(":");
			if(!ClientHandler.getClientMap().isLoggedIn(usernpass[0]) && ClientHandler.getDataQueuer().isRealUser(usernpass[0], usernpass[1])){
				CC.getClient().setUsername(usernpass[0]);
				//System.out.println("Setting the username to " + usernpass[0]);
				CC.getClient().setPassword(usernpass[1]);
				//System.out.println("Setting the password to " + usernpass[1]);
				CC.getDataTransfer().sendMessage("Your Account Has Been Verified!");
				System.out.println(usernpass[0]+" Has Been Logged In!");
			}else{
				CC.getDataTransfer().sendMessage("Sorry but that was an invalid username/password");
				CC.Terminate();
			}
		}catch(Exception e){
			try{CC.getDataTransfer().sendMessage("Sorry but that was an invalid username/password");CC.Terminate();
			}catch(Exception ex){}
		}
	}
	
	
	public boolean containsMoreThanNeeded(String msg){
		int n = 0;
		if(msg.length() > 300){
			return true;
		}
		for(char a: msg.toCharArray()){
			if(a == ':'){
				n ++;
			}
		}
		if(n > 1 || n == 0){
			return true;
		}
		return false;
	}

}
