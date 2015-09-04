package com.josephb.maxwellcraft.tileentity;

import com.josephb.maxwellcraft.MaxwellCraft;
import com.josephb.maxwellcraft.utility.LogHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityNegCharge extends TileEntity implements IUpdatePlayerListBox
{
	public static final String publicName = "TileEntityNegCharge";
	private String name = "TileEntityNegCharge";
	
	int ticks = 0;
	
	public String getName() {
		return name;
	}
	
	@Override
	public void update()
	{
		if(!worldObj.isRemote)
		{
			
			if(ticks==20 || ticks==0)
			{
				if(MaxwellCraft.negChargeTracker.add(this.pos))
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
