package com.JosephB.maxwellcraft.block;

import com.JosephB.maxwellcraft.tileentity.TileEntityMagnet;
import com.JosephB.maxwellcraft.block.BlockRotatedPillerMaxwellCraft;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Block representing a magnetic dipole, with the associated magnetic field.
 * Contains a tile entity, to register and update its position to the tracker.
 * @author Joseph Brownless
 */
public class BlockMagnet extends BlockRotatedPillerMaxwellCraft implements ITileEntityProvider
{
	private final String name = "Magnet";
	
	/**
	 * Creates new instance of this block type. 
	 * Only called when initialising blocks. When block is actually placed, {@link BlockRotatedPillerMaxwellCraft#onBlockPlaced} is called.
	 */
	public BlockMagnet()
	{
		super(Material.IRON);
		this.setUnlocalizedName(name);
	}
	
	/**
	 * @return Block type name
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
		return (TileEntity) new TileEntityMagnet();
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
	
	/**
	 * Get rendering type of block
	 */
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.SOLID;
    }
	
	
/*	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		return tileEntity == null? false : tileEntity.receiveClientEvent(eventID, eventParam);
	}	*/
}
