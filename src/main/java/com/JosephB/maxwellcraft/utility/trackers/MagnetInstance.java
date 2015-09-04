package com.josephb.maxwellcraft.utility.trackers;

import com.josephb.maxwellcraft.utility.physics.Vector3;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

public class MagnetInstance 
{
	private IBlockState state;
	private int x,y,z;
	
	PropertyDirection FACING = PropertyDirection.create("facing");
	
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
	
	public BlockPos getBlockPos()
	{
		return new BlockPos(x,y,z);
	}

	public Vector3 getVector3Pos()
	{
		return new Vector3(x,y,z);
	}
	
	public Vector3 getFacingVector()
	{
		Vec3i vec3i = ((EnumFacing) state.getValue(FACING)).getDirectionVec();
		return new Vector3(new Double(vec3i.getX()),new Double(vec3i.getY()),new Double(vec3i.getZ()));
	}
	
	public int[] toArray()
	{
		int[] arr = new int[4];
		arr[0] = x;
		arr[1] = y;
		arr[2] = z;
		arr[3] = ((EnumFacing)state.getValue(FACING)).getIndex();
		return arr;
	}
	
}
