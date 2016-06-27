package com.JosephB.maxwellcraft.utility.trackers;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.block.BlockNegCharge;
import com.JosephB.maxwellcraft.block.BlockPosCharge;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class TrackerHelper 
{

	public static boolean track(Block block, int x, int y, int z, IBlockState state)
	{
		if(block instanceof BlockMagnet)
		{
			return MaxwellCraft.magnetTracker.add(x, y, z, (IBlockState) state);
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
	
	public static void verifyAll()
	{
		MaxwellCraft.magnetTracker.verifyAllLocations();
		MaxwellCraft.posChargeTracker.verifyAllLocations();
		MaxwellCraft.negChargeTracker.verifyAllLocations();
	}
}
