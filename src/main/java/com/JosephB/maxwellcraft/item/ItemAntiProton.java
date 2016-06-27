package com.JosephB.maxwellcraft.item;

import com.JosephB.maxwellcraft.entity.EntityThrownAntiProton;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemAntiProton extends ItemMaxwellCraft
{
	
	private final String name = "AntiProton";
	
	public ItemAntiProton()
	{
		super();
		this.setUnlocalizedName(name);
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, 
        		SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 0.5F, 0.8F / (itemRand.nextFloat() * 0.4F + 1.2F));

        if (!worldIn.isRemote)
        {
            EntityThrownAntiProton ap = new EntityThrownAntiProton(worldIn, playerIn);
        	worldIn.spawnEntityInWorld(ap);
        	
            System.out.println("Spawning on client side ="+worldIn.isRemote);
            System.out.println("On spawn: entity position ="+ap.posX+", "+ap.posY+", "+ap.posZ);
            System.out.println("On spawn: entity motion ="+ap.motionX+", "+ap.motionY+", "+ap.motionZ); 
        }

        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}
