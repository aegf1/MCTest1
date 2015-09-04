package com.josephb.maxwellcraft.block;

import com.josephb.maxwellcraft.tileentity.TileEntityMagnet;
import com.josephb.maxwellcraft.tileentity.TileEntityPosCharge;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPosCharge extends BlockMaxwellCraft implements ITileEntityProvider
{
	private final String name = "PosCharge";
	
	public BlockPosCharge()
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
		return (TileEntity) new TileEntityPosCharge();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
}
