package com.JosephB.maxwellcraft.utility.trackers;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.block.BlockNegCharge;
import com.JosephB.maxwellcraft.block.BlockPosCharge;
import com.JosephB.maxwellcraft.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;

/**
 * Helps track blocks
 * @author Joseph Brownless
 */
public class TrackerHelper 
{

	/**
	 * Tracks a block at a position with a state. (Just magnets for now)
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @return True if added to a tracker
	 */
	public static boolean track(Block block, int x, int y, int z, IBlockState state)
	{
		if(block instanceof BlockMagnet)
		{
			Boolean added = MaxwellCraft.magnetTracker.add(x, y, z, (IBlockState) state);
			return added;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Remove a block at a location from all trackers
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @return True if removed successfully
	 */
	public static boolean remove(Block block, int x, int y, int z)
	{
		if(block instanceof BlockMagnet)
		{
			return MaxwellCraft.magnetTracker.remove(x, y, z);
		}
		else if(block instanceof BlockPosCharge)
		{
			return MaxwellCraft.posChargeTracker.remove(x, y, z);
		}
		else if(block instanceof BlockNegCharge)
		{
			return MaxwellCraft.negChargeTracker.remove(x, y, z);
		}
		else
		{
			return false;
		}
	}

	/**
	 * Tracks a block at a position. 
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @return True if added to a tracker
	 */
	public static boolean track(Block block, int x, int y, int z) 
	{
		if(block instanceof BlockPosCharge)
		{
			return MaxwellCraft.posChargeTracker.add(x, y, z);
		}
		else if(block instanceof BlockNegCharge)
		{
			return MaxwellCraft.negChargeTracker.add(x, y, z);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Verify the contents of all trackers
	 */
	public static void verifyAll()
	{
		MaxwellCraft.magnetTracker.verifyAllLocations();
		MaxwellCraft.posChargeTracker.verifyAllLocations();
		MaxwellCraft.negChargeTracker.verifyAllLocations();
	}
}
