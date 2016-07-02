package com.JosephB.maxwellcraft.tileentity;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.trackers.TrackerHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Tile entity contained in negative charge blocks. Keeps block location updated with tracker. 
 * (Mainly useful when loading world with pre-existing blocks.
 * @author Joseph Brownless
 */
public class TileEntityNegCharge extends TileEntity implements ITickable
{
	public static final String publicName = "TileEntityNegCharge";
	private String name = "TileEntityNegCharge";
	
	int ticks = 0;
	
	/**
	 * 
	 * @return Tile entity name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Called every tick. Adds block location to tracker.
	 * (Tracker adds it to list if it's not already there)
	 */
	public void update()
	{
		if(!worldObj.isRemote)
		{
			
			if(ticks==20 || ticks==0)
			{
				if(TrackerHelper.track(getBlockType(), this.getPos(), getBlockMetadata()));
				{
//					LogHelper.info("Magnet recorded successfully. " + 
//						Test1.magnetTracker.getNumOfMagnets() + " magnet(s) in stored list");
//		    		LogHelper.info(pos.getX()+", "+pos.getY()+", "+pos.getZ()+", " +
//						this.getBlockType().getStateFromMeta(this.getBlockMetadata()).getValue(PropertyDirection.create("facing")));
				}
				ticks=0;
			}
			ticks++;
		}
	}

}
