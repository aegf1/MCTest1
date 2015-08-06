package com.josephb.test1.utility.trackers;

import java.util.HashSet;

import com.josephb.test1.block.BlockNegCharge;
import com.josephb.test1.utility.LogHelper;

import net.minecraft.util.BlockPos;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NegChargeTracker
{
	private HashSet<BlockPos> negCharges = new HashSet(100);
	
	public NegChargeTracker()
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
		return negCharges.add(new BlockPos(x,y,z));
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
		HashSet<BlockPos> negCharges1 = new HashSet(negCharges);
		for(BlockPos current: negCharges1)
		{
			if(current.getX()==x && current.getY()==y && current.getZ()==z)
			{
//				LogHelper.info(x+", "+y+", "+z);
				negCharges.remove(current);
				return true;
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
	
	public int getNumOfnegCharges()
	{
		return negCharges.size();
	}
	
	public boolean verifyAllLocations()
	{
		int numRemoved = 0;
		HashSet<BlockPos> negCharges1 = new HashSet(negCharges);
		for(BlockPos current: negCharges1)
		{
			if(!(DimensionManager.getWorld(0).getBlockState(current).getBlock() instanceof BlockNegCharge))
			{
				if(remove(current))
				{
//					LogHelper.info("Removing NegCharge, "+pos.toString());
					
					numRemoved++;
				}
			}
		}
//		LogHelper.info("negCharge Tracker checked over. "+numRemoved+" entries removed");
//		LogHelper.info(negCharges1.size());
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
		HashSet<BlockPos> negCharges1 = new HashSet();
		try {
			negCharges1 = new HashSet(negCharges);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int[][] arr = new int[negCharges1.size()][4];
		int i = 0;
		for(BlockPos current: negCharges1)
		{
			arr[i] = new int[]{current.getX(),current.getY(),current.getZ()};
			i++;
		}
		return arr;
	}
}
