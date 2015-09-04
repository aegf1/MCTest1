package com.josephb.maxwellcraft.handler;

import com.josephb.maxwellcraft.MaxwellCraft;
import com.josephb.maxwellcraft.utility.dataoutput.OutputHelper;
import com.josephb.maxwellcraft.utility.trackers.TrackerHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MaxwellCraftEventHandler
{
	private Minecraft mc;
	private int ticks = 0;

	@SubscribeEvent
	public boolean onServerTick(ServerTickEvent event)
	{
		if (event.phase == Phase.START) {
			ticks++;
//			System.out.println("Hello World");
			if (ticks >= 20) {
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