package com.shadowcasted.cloudconsole.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.shadowcasted.cloudconsole.servermanagement.ClientHandler;

public class firePit extends Thread{

	private Socket socket;
	public synchronized void closeSocket(){try{socket.close();}catch(IOException e){}}
	private BufferedWriter reader;
	private String DeathMessage;
	//Threaded release

	@Override
	public void run(){
		try{
			try{
				reader.write(DeathMessage);
				reader.newLine();
				reader.flush();
			}catch(Exception e){}
			socket.close();
		} catch (IOException e) {}
	}

	public firePit(Socket sock){
		try{
			socket = sock;
			reader = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			DeathMessage = ClientHandler.getDataQueuer().getDeathMessage();
			if(ClientHandler.getDataQueuer().ThreadedRelease()){System.out.println("Running firePut as thread");this.start();}
			else{System.out.println("Running firePit as method");this.run();}
		}catch(Exception e){}
	}



}
