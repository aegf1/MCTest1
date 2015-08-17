package com.josephb.test1.utility;

import com.josephb.test1.Test1;
import com.josephb.test1.utility.dataoutput.OutputHelper;
import com.josephb.test1.utility.trackers.TrackerHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class Test1EventHandler
{
	private Minecraft mc;
	private int ticks = 0;

	@SubscribeEvent
	public boolean onServerTick(ServerTickEvent event)
	{
		if (event.phase == Phase.START) {
			ticks++;
//			System.out.println("Hello World");
			if (ticks >= 200) {
				TrackerHelper.verifyAll();
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets() + " magnet(s) in stored list");
//				LogHelper.info(Test1.magnetTracker.get2DArray().length + " magnet(s) in stored list");
//				LogHelper.info(Test1.magnetTracker.getTotalFacingVector().toString());
				ticks = 0;
			}
			if (event.side == Side.SERVER) {
				OutputHelper.tick();
//				System.out.println(event.side.toString());
			} 
		}
		return true;
	}
}
