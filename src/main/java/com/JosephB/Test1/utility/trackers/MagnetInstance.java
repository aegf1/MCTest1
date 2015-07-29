package com.josephb.test1.utility.trackers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public class MagnetInstance 
{
	private IBlockState state;
	private int x,y,z;
	
	public MagnetInstance(int xIn, int yIn, int zIn, IBlockState stateIn)
	{
		x=xIn;y=yIn;z=zIn;
		state = (IBlockState)stateIn;
	}
	
	public MagnetInstance(BlockPos pos, IBlockState stateIn)
	{
		x=pos.getX(); y=pos.getY(); z=pos.getZ();
		state = (IBlockState)stateIn;
	}
	
	public IBlockState getState() {
		return state;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}
