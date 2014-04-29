package com.shadowcasted.cloudconsole.client;

import java.util.Random;

public class Pinger extends Thread{

	private String lastStoredMessage = "null";
	public synchronized String getLastMessage(){return lastStoredMessage;}
	public synchronized void setLastMessage(String message){lastStoredMessage = message;}

	private String lastSentMessage;

	private boolean isalive = true;
	public synchronized boolean getAlive(){return isalive;}
	public synchronized void setAlive(Boolean b){isalive = b;}
	
	private ClientCluster cc;
	public synchronized ClientCluster getCC(){return cc;}

	public Pinger(ClientCluster c){
		cc = c;
		lastSentMessage = getPingMessage();
		cc.getDataTransfer().sendMessage(lastSentMessage);
	}

	private Random random = new Random();
	private int getRandom(){return random.nextInt(9999)+1;}
	private String getPingMessage(){return "PING p"+getRandom();}

	@Override public void run(){
		try{
			/*
			while(cc.Alive()){
				Thread.sleep(10000);
				if(lastStoredMessage.equals(lastSentMessage)){
					lastSentMessage = getPingMessage();
					cc.getDataTransfer().sendMessage(lastSentMessage);
				}else{
					try{
						cc.getDataTransfer().sendMessage("You Don't Have A Proper Ping\nLast Ping: "+lastStoredMessage+"\nLooking For:"+lastSentMessage);
					}catch(Exception e){}
					try{
						cc.Terminate();
					}catch(Exception e){}
					break;
				}
			}

			 */
			int counter = 0;
			while(true){
				do{
					try{Thread.sleep(25);}catch(Exception e){}
					counter++;
					if(!isalive){break;}
				}while(counter < 100);
				counter = 0;
				if(!isalive){break;}
				else{
					if(lastStoredMessage.equals(lastSentMessage)){
						lastSentMessage = getPingMessage();
						cc.getDataTransfer().sendMessage(lastSentMessage);
					}else{
						try{
							cc.getDataTransfer().sendMessage("You Don't Have A Proper Ping\nLast Ping: "+lastStoredMessage+"\nLooking For:"+lastSentMessage);
						}catch(Exception e){}
						try{
							cc.Terminate();
						}catch(Exception e){}
						break;
					}

				}
			}

		}catch(Exception e){}
		System.out.println(cc.getID()+") Pinger Died");
	}



}
