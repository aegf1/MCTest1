package com.josephb.test1.utility.trackers;

import com.josephb.test1.Test1;
import com.josephb.test1.block.BlockMagnet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		else
		{
			return false;
		}
	}
}
