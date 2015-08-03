package com.josephb.test1.utility;

import com.josephb.test1.Test1;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class ServerTickHandler
{
	private Minecraft mc;
	private int ticks = 0;
	
	@SubscribeEvent
	public boolean onServerTick(ServerTickEvent event)
	{
		ticks++;
//		System.out.println("Hello World");
		if (ticks>=200) 
		{
			Test1.magnetTracker.verifyAllLocations();
//			LogHelper.info(Test1.magnetTracker.getNumOfMagnets() + " magnet(s) in stored list");
//			LogHelper.info(Test1.magnetTracker.get2DArray().length + " magnet(s) in stored list");
//			LogHelper.info(Test1.magnetTracker.getTotalFacingVector().toString());
			ticks=0;
		}
		return true;
	}
}
