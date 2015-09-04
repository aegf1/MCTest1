package com.josephb.maxwellcraft.item;

import com.josephb.maxwellcraft.entity.EntityThrownProton;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemProton extends ItemMaxwellCraft
{
	
	private final String name = "Proton";
	
	public ItemProton()
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

        worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 1.2F));

        if (!worldIn.isRemote && worldIn instanceof WorldServer)
        {
            worldIn.spawnEntityInWorld(new EntityThrownProton(worldIn, playerIn));
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStackIn;
    }
}
