package com.JosephB.maxwellcraft.block;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.creativetab.CreativeTabMaxwellCraft;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.trackers.TrackerHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Represents blocks such as magnetic dipole, which have a 'top', 'bottom', and 4 identical sides.
 * @author Joseph Brownless
 */
public class BlockRotatedPillerMaxwellCraft extends BlockMaxwellCraft
{
	/**
	 * Creates the property of facing for this block type. Doesn't store the actual direction.
	 */
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	/**
	 * Constructor that lets you define block material.
	 * Sets default facing to north, but actual facing is created when block is placed, using {@link #onBlockPlaced}
	 * @param material
	 */
	public BlockRotatedPillerMaxwellCraft(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabMaxwellCraft.MaxwellCraft_Tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.isBlockContainer = true;
	}

	/**
	 * Constructor that sets block material to iron
	 */
	public BlockRotatedPillerMaxwellCraft()
	{
		this(Material.IRON);
	}
	
	/**
	 * Converts metadata values into facing
	 * 0-6 -> Down,Up,North,South,East,West,NULL
	 * @param meta
	 * @return Facing
	 */
	public static EnumFacing getFacing(int meta)
    {
        int j = meta & 7;
        return j > 5 ? null : EnumFacing.getFront(j);
    }
	
	/**
	 * Convert facing direction into the metadata value
	 * @param facing
	 * @return
	 */
	public static int getMeta(EnumFacing facing)
	{
		return facing.getIndex();
	}

    /**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
    	return this.getDefaultState().withProperty(FACING, getFacing(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value, based on the facing direction
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	byte b0 = 0;
        int i = b0 | ((EnumFacing)state.getValue(FACING)).getIndex();
		return i;
    }

    /**
     * Creates the state, and adds the 'facing' property
     */
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    /**
     * Creates an itemstack of this block
     */
    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    /**
     * Called when block is placed in world. Creates the state, and defines the facing direction using the direction the placer is facing.
     * Also adds block to the trackers.
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	IBlockState state = this.getDefaultState().withProperty(FACING, getFacingFromEntity(worldIn, pos, placer));
    	EnumFacing blockFacing = getFacingFromEntity(worldIn, pos, placer);
    	
    	if (!worldIn.isRemote) 
    	{
			if(TrackerHelper.track(this, pos.getX(), pos.getY(), pos.getZ(), blockFacing.getIndex()))
			{
//	    		LogHelper.info("Adding block to list. "+pos.getX()+", "+pos.getY()+", "+pos.getZ()+", "+state.getValue(PropertyDirection.create("facing")));
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets());
			}
		}
		return this.getDefaultState().withProperty(FACING, blockFacing);
    }
    
    /**
     * Called when block is broken. Removes from trackers.
     */
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
    
    /**
     * Uses the facing direction of the placer to determin the facing direction of a placed block.
     * If placer >2 blocks higher clicked block, places facing up.
     * If placer below clicked block, places facing down.
     * Otherwise, places facing placer.
     * @param worldIn
     * @param clickedBlock Block clicked on when placing
     * @param entityIn Placer
     * @return Facing of placed block
     */
    public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn)
    {
        if (MathHelper.abs((float)entityIn.posX - (float)clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F)
        {
            double placerHeight = entityIn.posY + (double)entityIn.getEyeHeight();

            if (placerHeight - (double)clickedBlock.getY() > 2.0D)
            {
                return EnumFacing.UP;
            }

            if ((double)clickedBlock.getY() - placerHeight > 0.0D)
            {
                return EnumFacing.DOWN;
            }
        }

        return entityIn.getHorizontalFacing().getOpposite();
    }
}
