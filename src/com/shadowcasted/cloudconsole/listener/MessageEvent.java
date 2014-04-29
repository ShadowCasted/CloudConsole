package com.shadowcasted.cloudconsole.listener;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.shadowcasted.cloudconsole.client.ClientCluster;

public class MessageEvent extends Event{

	    private static final HandlerList handlers = new HandlerList();
	    private String message;
	    private ClientCluster client;
	    
	    public MessageEvent(String example, ClientCluster client) {
	        message = example;
	        this.client = client;
	    }
	 
	    public ClientCluster getClientCluster(){
	    	return client;
	    }
	    
	    public String getMessage() {
	        return message;
	    }
	 
	    public HandlerList getHandlers() {
	        return handlers;
	    }
	 
	    public static HandlerList getHandlerList() {
	        return handlers;
	    }
	
}
