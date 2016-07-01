package com.JosephB.maxwellcraft.handler;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.utility.dataoutput.OutputHelper;
import com.JosephB.maxwellcraft.utility.trackers.TrackerHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Contains methods (well, just 1 method atm) which listen to forge events and execute code.
 * @author Joseph Brownless
 */
public class MaxwellCraftEventHandler
{
	private int ticks = 0;

	/**
	 * Executed every tick on the server side.
	 * Verifies the contents of each tracker every second.
	 * @param event Server tick event
	 * @return true
	 */
	@SubscribeEvent
	public boolean onServerTick(ServerTickEvent event)
	{
		if (event.phase == Phase.START) 
		{
			ticks++;
			if (ticks >= 20) {
				TrackerHelper.verifyAll();
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets() + " magnet(s) in stored list");
//				LogHelper.info(Test1.magnetTracker.get2DArray().length + " magnet(s) in stored list");
//				LogHelper.info(Test1.magnetTracker.getTotalFacingVector().toString());
				ticks = 0;
			}
			if (event.side == Side.SERVER) 
			{
				OutputHelper.tick();
//				System.out.println(event.side.toString());
			} 
		}
		return true;
	}
}
