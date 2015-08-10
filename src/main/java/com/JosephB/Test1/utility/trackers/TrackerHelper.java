package com.josephb.test1.utility.trackers;

import com.josephb.test1.Test1;
import com.josephb.test1.block.BlockMagnet;
import com.josephb.test1.block.BlockNegCharge;
import com.josephb.test1.block.BlockPosCharge;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class TrackerHelper 
{

	public static boolean track(Block block, int x, int y, int z, IBlockState state)
	{
		if(block instanceof BlockMagnet)
		{
			return Test1.magnetTracker.add(x, y, z, (IBlockState) state);
		}
		else
		{
			return false;
		}
	}


	public static boolean remove(Block block, int x, int y, int z)
	{
		if(block instanceof BlockMagnet)
		{
			return Test1.magnetTracker.remove(x, y, z);
		}
		else if(block instanceof BlockPosCharge)
		{
			return Test1.posChargeTracker.remove(x, y, z);
		}
		else if(block instanceof BlockNegCharge)
		{
			return Test1.negChargeTracker.remove(x, y, z);
		}
		else
		{
			return false;
		}
	}


	public static boolean track(Block block, int x, int y, int z) 
	{
		if(block instanceof BlockPosCharge)
		{
			return Test1.posChargeTracker.add(x, y, z);
		}
		else if(block instanceof BlockNegCharge)
		{
			return Test1.negChargeTracker.add(x, y, z);
		}
		else
		{
			return false;
		}
	}
	
	public static void verifyAll()
	{
		Test1.magnetTracker.verifyAllLocations();
		Test1.posChargeTracker.verifyAllLocations();
		Test1.negChargeTracker.verifyAllLocations();
	}
}
