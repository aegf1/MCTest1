package com.josephb.maxwellcraft.item;

import com.josephb.maxwellcraft.entity.EntityThrownAntiProton;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
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
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 0.5F, 0.8F / (itemRand.nextFloat() * 0.4F + 1.2F));

        if (!worldIn.isRemote)
        {
            EntityThrownAntiProton ap = new EntityThrownAntiProton(worldIn, playerIn);
        	worldIn.spawnEntityInWorld(ap);
        	/* DEBUG
            System.out.println("Spawning on client side ="+worldIn.isRemote);
            System.out.println("On spawn: entity position ="+ap.posX+", "+ap.posY+", "+ap.posZ);
            System.out.println("On spawn: entity motion ="+ap.motionX+", "+ap.motionY+", "+ap.motionZ); */
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStackIn;
    }
}
