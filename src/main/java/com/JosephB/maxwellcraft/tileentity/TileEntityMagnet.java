package com.JosephB.maxwellcraft.tileentity;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.trackers.TrackerHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityMagnet extends TileEntity implements ITickable
{
	public static final String publicName = "TileEntityMagnet";
	private String name = "TileEntityMagnet";
	
	int ticks = 0;
	
	public TileEntityMagnet()
	{
		super();
//		LogHelper.info("Creating magnet TileEntity");
	}
	
	public String getName() {
		return name;
	}
	
	public void update()
	{
		if(!worldObj.isRemote)
		{
			
			if(ticks==20 || ticks==0)
			{
				if(MaxwellCraft.magnetTracker.add(this.pos, this.getBlockType().getStateFromMeta(this.getBlockMetadata())))
				{	// Change above to:
					//	if(TrackerHelper.track(blockType, pos.getX(), pos.getY(), pos.getZ(), this.getBlockType().getStateFromMeta(getBlockMetadata())))

//					LogHelper.info("Magnet recorded successfully. " + 
//					Test1.magnetTracker.getNumOfMagnets() + " magnet(s) in stored list");
//		    		LogHelper.info(pos.getX()+", "+pos.getY()+", "+pos.getZ()+", " +
//					this.getBlockType().getStateFromMeta(this.getBlockMetadata()).getValue(PropertyDirection.create("facing")));
				}
				ticks=0;
			}
			ticks++;
		}
	}

}
