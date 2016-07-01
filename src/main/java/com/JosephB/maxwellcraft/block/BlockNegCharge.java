package com.JosephB.maxwellcraft.block;

import com.JosephB.maxwellcraft.tileentity.TileEntityNegCharge;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Block representing a negative electric charge, with the associated field.
 * Contains a tile entity, to register and update its position to the tracker.
 * @author Joseph Brownless
 */
public class BlockNegCharge extends BlockMaxwellCraft implements ITileEntityProvider
{
	private final String name = "NegCharge";
	
	/**
	 * Default constructor.
	 * Only called when initialising blocks. When block is actually placed, {@link BlockMaxwellCraft#onBlockPlaced} is called.
	 */
	public BlockNegCharge()
	{
		super(Material.IRON);
		this.setUnlocalizedName(name);
		this.isBlockContainer = true;
	}
	
	/**
	 * @return Block Name
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * @return Associated tile entity for this block
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return (TileEntity) new TileEntityNegCharge();
	}
	
	/**
	 * Called when breaking block of this type. Removes tile entity.
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
}
