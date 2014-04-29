package com.shadowcasted.cloudconsole.runnables;

import org.bukkit.scheduler.BukkitRunnable;

public class BukkitTask {

	private BukkitRunnable r;
	public BukkitRunnable getBukkitRunnable(){return r;}
	
	public BukkitTask(BukkitRunnable run){
		r = run;
	}
	
	

}
