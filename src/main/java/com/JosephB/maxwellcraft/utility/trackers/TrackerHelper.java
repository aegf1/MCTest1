package com.JosephB.maxwellcraft.utility.trackers;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.block.BlockNegCharge;
import com.JosephB.maxwellcraft.block.BlockPosCharge;
import com.JosephB.maxwellcraft.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Helps track blocks
 * @author Joseph Brownless
 */
public class TrackerHelper 
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	/**
	 * Tracks a block at a position with a state.
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 * @return True if added to a tracker
	 */
	public static boolean track(Block block, int x, int y, int z, IBlockState state)
	{
		
		if (state.getProperties().get(FACING) != null) 					// If has a facing property
		{			
			return track(block, x, y, z, state.getValue(FACING).getIndex());	// Track as a directional block
		}
		else
		{
			return track(block, x, y, z, 0);					// Track as directionless block
		}
	}
	
	public static boolean track(Block block, BlockPos pos, IBlockState state)
	{
		
		return track(block, pos.getX(), pos.getY(), pos.getZ(), state);
	}
	
	/**
	 * Tracks a block at a position with an index.
	 * For directional blocks, index 0-5 represents Down,Up,North,South,East,West
	 * For directionless blocks, doesn't matter (but should be 0).
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @param index
	 * @return True if added to a tracker
	 */
	public static boolean track(Block block, int x, int y, int z, int index)
	{
		if(block instanceof BlockMagnet)
		{
			Boolean added = MaxwellCraft.magnetTracker.add(x, y, z, index);
			return added;
		}
		else if(block instanceof BlockPosCharge)
		{
			return MaxwellCraft.chargeTracker.add(x, y, z, 1);
		}
		else if(block instanceof BlockNegCharge)
		{
			return MaxwellCraft.chargeTracker.add(x, y, z, 0);
		}
		else
		{
			return false;
		}
	}
	
	public static boolean track(Block block, BlockPos pos, int index)
	{
		
		return track(block, pos.getX(), pos.getY(), pos.getZ(), index);
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
			return MaxwellCraft.chargeTracker.remove(x, y, z);
		}
		else if(block instanceof BlockNegCharge)
		{
			return MaxwellCraft.chargeTracker.remove(x, y, z);
		}
		else
		{
			return false;
		}
	}
	
	public static boolean remove(Block block, BlockPos pos)
	{
		
		return remove(block, pos.getX(), pos.getY(), pos.getZ());
	}
	
	/**
	 * Verify the contents of all trackers
	 */
	public static void verifyAll()
	{
		MaxwellCraft.magnetTracker.verifyAllLocations();
		MaxwellCraft.chargeTracker.verifyAllLocations();
	}
}
