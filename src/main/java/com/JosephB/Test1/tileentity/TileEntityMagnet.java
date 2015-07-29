package com.josephb.test1.tileentity;

import com.josephb.test1.Test1;
import com.josephb.test1.utility.LogHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMagnet extends TileEntity implements IUpdatePlayerListBox
{
	public static final String publicName = "TileEntityMagnet";
	private String name = "TileEntityMagnet";
	
	int ticks = 0;
	
	public String getName() {
		return name;
	}
	
	@Override
	public void update()
	{
		if(!worldObj.isRemote)
		{
			ticks++;
			if(ticks==20 || ticks==0)
			{
				if(Test1.magnetTracker.add(
						this.pos, 
						this.getBlockType().getStateFromMeta(this.getBlockMetadata())
						))
				{
//					LogHelper.info("Magnet recorded successfully. " + 
//						Test1.magnetTracker.getNumOfMagnets() + " magnet(s) in stored list");
//		    		LogHelper.info(pos.getX()+", "+pos.getY()+", "+pos.getZ()+", " +
//						this.getBlockType().getStateFromMeta(this.getBlockMetadata()).getValue(PropertyDirection.create("facing")));
				}
				ticks=0;
			}
		}
	}

}
