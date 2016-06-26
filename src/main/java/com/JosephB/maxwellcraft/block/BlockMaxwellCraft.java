package com.JosephB.maxwellcraft.block;

import com.JosephB.maxwellcraft.creativetab.CreativeTabMaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.trackers.TrackerHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class BlockMaxwellCraft extends Block
{
	/**
	 * Constructor that lets you define block material
	 * @param material
	 */
	public BlockMaxwellCraft(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabMaxwellCraft.MaxwellCraft_Tab);
	}

	/**
	 * Constructor that sets block material to iron
	 */
	public BlockMaxwellCraft()
	{
		this(Material.IRON);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	public String getName()
	{
		return null;
	}
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	if (!worldIn.isRemote) 
    	{
			if(TrackerHelper.track(this, pos.getX(), pos.getY(), pos.getZ()))
			{
//	    		LogHelper.info("Adding block to list. "+pos.getX()+", "+pos.getY()+", "+pos.getZ()+", "+state.getValue(PropertyDirection.create("facing")));
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets());
			}
		}
    	return this.getStateFromMeta(meta);
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	if (!worldIn.isRemote) 
    	{
			if(TrackerHelper.remove(this, pos.getX(), pos.getY(), pos.getZ()))
			{
//				LogHelper.info("Removing block from list. "+pos.getX()+", "+pos.getY()+", "+pos.getZ()+", "+state.getValue(PropertyDirection.create("facing")));
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets());
			}
		}
		super.breakBlock(worldIn, pos, state);
    }
	
}
