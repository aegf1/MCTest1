package com.JosephB.maxwellcraft.utility.trackers;

import java.util.HashSet;
import java.util.Iterator;

import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagnetTracker
{
	private HashSet<MagnetInstance> magnets = new HashSet(100);
	
	public MagnetTracker()
	{
		super();
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @return true if added successfully
	 */
	public boolean add(int x, int y, int z, IBlockState state)
	{
		MagnetInstance m = new MagnetInstance(x, y, z, (IBlockState)state);
		remove(x,y,z);
		return magnets.add(m);
//		System.out.println(m.getX()+","+m.getY()+","+m.getZ());
//		System.out.println(m.getState().getValue(PropertyDirection.create("facing")));
	}
	
	/**
	 * @param pos
	 * @param state
	 * @return true if was added successfully
	 */
	public boolean add(BlockPos pos, IBlockState state)
	{
		return add(pos.getX(), pos.getY(), pos.getZ(), state);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return true if an entry with these coords is removed
	 */
	public boolean remove(int x, int y, int z)
	{
		HashSet<MagnetInstance> magnets1 = new HashSet(magnets);
		for(MagnetInstance current: magnets1)
		{
			if(current.getX()==x && current.getY()==y && current.getZ()==z)
			{
				return magnets.remove(current);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param pos
	 * @return true if an entry with these coords is removed
	 */
	public boolean remove(BlockPos pos)
	{
		return remove(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public int getNumOfMagnets()
	{
		return magnets.size();
	}
	
	public boolean verifyAllLocations()
	{
		int numRemoved = 0;
		HashSet<MagnetInstance> magnets1 = new HashSet(magnets);
		for(MagnetInstance current: magnets1)
		{
			BlockPos pos = new BlockPos(current.getX(), current.getY(), current.getZ());
			if(!(DimensionManager.getWorld(0).getBlockState(pos).getBlock() instanceof BlockMagnet))
			{
				if(remove(pos)){numRemoved++;}
			}
		}
//		LogHelper.info("Magnet Tracker checked over. "+numRemoved+" entries removed");
		
		if(numRemoved==0){return false;}
		else{return true;}
	}
	
	/**
	 * gets a 2d array containing the x,y,z,orientation of every magnet
	 * (x1, y1, z1, facing1)
	 * (x2, y2, z2, facing2)
	 * (x3, y3, z3, facing3)
	 * ....
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public int[][] get2DArray()
	{
		HashSet<MagnetInstance> magnets1 = new HashSet();
		try {
			magnets1 = new HashSet(magnets);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int[][] arr = new int[magnets1.size()][4];
		int i = 0;
		for(MagnetInstance current: magnets1)
		{
			arr[i] = current.toArray();
			i++;
		}
		return arr;
	}
	
	public Vector3 getTotalFacingVector()
	{
		Vector3 vec = new Vector3(0,0,0);
		HashSet<MagnetInstance> magnets1 = new HashSet();
		try {
			magnets1 = new HashSet(magnets);
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(MagnetInstance current: magnets1)
		{
			vec.increaseBy(current.getFacingVector());
		}
		return vec;
	}
	
}
