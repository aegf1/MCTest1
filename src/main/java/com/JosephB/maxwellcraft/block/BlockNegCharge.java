package com.josephb.maxwellcraft.block;

import com.josephb.maxwellcraft.tileentity.TileEntityNegCharge;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNegCharge extends BlockMaxwellCraft implements ITileEntityProvider
{
	private final String name = "NegCharge";
	
	public BlockNegCharge()
	{
		super(Material.iron);
		this.setUnlocalizedName(name);
		this.isBlockContainer = true;
	}
	
	
	@Override
	public String getName()
	{
		return name;
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return (TileEntity) new TileEntityNegCharge();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
}
