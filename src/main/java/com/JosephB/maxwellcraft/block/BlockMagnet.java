package com.JosephB.maxwellcraft.block;

import com.JosephB.maxwellcraft.tileentity.TileEntityMagnet;
import com.JosephB.maxwellcraft.block.BlockRotatedPillerMaxwellCraft;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author Joseph Brownless
 */
public class BlockMagnet extends BlockRotatedPillerMaxwellCraft implements ITileEntityProvider
{
	private final String name = "Magnet";
	
	public BlockMagnet()
	{
		super(Material.IRON);
		this.setUnlocalizedName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
		return (TileEntity) new TileEntityMagnet();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
/*	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		return tileEntity == null? false : tileEntity.receiveClientEvent(eventID, eventParam);
	}	*/
}
