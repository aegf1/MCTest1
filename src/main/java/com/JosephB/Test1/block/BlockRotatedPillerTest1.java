package com.josephb.test1.block;

import com.josephb.test1.Test1;
import com.josephb.test1.creativetab.CreativeTabTest1;
import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.trackers.TrackerHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRotatedPillerTest1 extends BlockTest1
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	private static final String __OBFID = "CL_00000302";
	
	/**
	 * Constructor that lets you define block material
	 * @param material
	 */
	public BlockRotatedPillerTest1(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabTest1.Test1_Tab);
	}

	/**
	 * Constructor that sets block material to iron
	 */
	public BlockRotatedPillerTest1()
	{
		this(Material.iron);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.SOLID;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}
	
	@Override
	public boolean isFullCube()
	{
		return true;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}

	
	public static EnumFacing getFacing(int meta)
    {
        int j = meta & 7;
        return j > 5 ? null : EnumFacing.getFront(j);
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
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	byte b0 = 0;
        int i = b0 | ((EnumFacing)state.getValue(FACING)).getIndex();
		return i;
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	IBlockState state = this.getDefaultState().withProperty(FACING, getFacingFromEntity(worldIn, pos, placer));
    	
    	if (FMLCommonHandler.instance().getEffectiveSide().isServer()) 
    	{
			if(TrackerHelper.track(this, pos.getX(), pos.getY(), pos.getZ(), state))
			{
//	    		LogHelper.info("Adding block to list. "+pos.getX()+", "+pos.getY()+", "+pos.getZ()+", "+state.getValue(PropertyDirection.create("facing")));
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets());
			}
		}
		return state;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	if (FMLCommonHandler.instance().getEffectiveSide().isServer()) 
    	{
			if(TrackerHelper.remove(this, pos.getX(), pos.getY(), pos.getZ()))
			{
//				LogHelper.info("Removing block from list. "+pos.getX()+", "+pos.getY()+", "+pos.getZ()+", "+state.getValue(PropertyDirection.create("facing")));
//				LogHelper.info(Test1.magnetTracker.getNumOfMagnets());
			}
		}
		super.breakBlock(worldIn, pos, state);
    }
    
    
    public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn)
    {
        if (MathHelper.abs((float)entityIn.posX - (float)clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F)
        {
            double d0 = entityIn.posY + (double)entityIn.getEyeHeight();

            if (d0 - (double)clickedBlock.getY() > 2.0D)
            {
                return EnumFacing.UP;
            }

            if ((double)clickedBlock.getY() - d0 > 0.0D)
            {
                return EnumFacing.DOWN;
            }
        }

        return entityIn.getHorizontalFacing().getOpposite();
    }
}
