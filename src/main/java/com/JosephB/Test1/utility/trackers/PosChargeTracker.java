package com.josephb.test1.utility.trackers;

import java.util.HashSet;

import com.josephb.test1.block.BlockPosCharge;
import com.josephb.test1.utility.LogHelper;

import net.minecraft.util.BlockPos;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PosChargeTracker
{
	private HashSet<int[]> posCharges = new HashSet(100);
	
	public PosChargeTracker()
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
	public boolean add(int x, int y, int z)
	{
		remove(x,y,z);
		return posCharges.add(new int[]{x,y,z});
//		System.out.println(m.getX()+","+m.getY()+","+m.getZ());
//		System.out.println(m.getState().getValue(PropertyDirection.create("facing")));
	}
	
	/**
	 * @param pos
	 * @param state
	 * @return true if was added successfully
	 */
	public boolean add(BlockPos pos)
	{
		return add(pos.getX(), pos.getY(), pos.getZ());
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
		HashSet<int[]> posCharges1 = new HashSet(posCharges);
		for(int[] current: posCharges1)
		{
			if(current[0]==x && current[1]==y && current[2]==z)
			{
				return posCharges1.remove(current);
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
	
	public int getNumOfPosCharges()
	{
		return posCharges.size();
	}
	
	public boolean verifyAllLocations()
	{
		int numRemoved = 0;
		HashSet<int[]> posCharges1 = new HashSet(posCharges);
		for(int[] current: posCharges1)
		{
			BlockPos pos = new BlockPos(current[0], current[1], current[2]);
			if(!(DimensionManager.getWorld(0).getBlockState(pos).getBlock() instanceof BlockPosCharge))
			{
				if(remove(pos)){numRemoved++;}
			}
		}
		LogHelper.info("posCharge Tracker checked over. "+numRemoved+" entries removed");
		
		if(numRemoved==0){return false;}
		else{return true;}
	}
	
	/**
	 * gets a 2d array containing the x,y,z of every charge
	 * (x1, y1, z1)
	 * (x2, y2, z2)
	 * (x3, y3, z3)
	 * ....
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public int[][] get2DArray()
	{
		HashSet<int[]> posCharges1 = new HashSet();
		try {
			posCharges1 = new HashSet(posCharges);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int[][] arr = new int[posCharges1.size()][4];
		int i = 0;
		for(int[] current: posCharges1)
		{
			arr[i] = current;
			i++;
		}
		return arr;
	}
}
