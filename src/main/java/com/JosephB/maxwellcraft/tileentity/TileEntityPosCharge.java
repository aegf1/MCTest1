package com.JosephB.maxwellcraft.tileentity;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.utility.LogHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPosCharge extends TileEntity implements ITickable
{
	public static final String publicName = "TileEntityPosCharge";
	private String name = "TileEntityPosCharge";
	
	int ticks = 0;
	
	public String getName() {
		return name;
	}

	public void update()
	{
		if(!worldObj.isRemote)
		{
			
			if(ticks==20 || ticks==0)
			{
				if(MaxwellCraft.posChargeTracker.add(this.pos))
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
