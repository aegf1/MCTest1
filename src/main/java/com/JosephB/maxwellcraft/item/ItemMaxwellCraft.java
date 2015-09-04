package com.josephb.maxwellcraft.item;

import com.josephb.maxwellcraft.creativetab.CreativeTabMaxwellCraft;
import com.josephb.maxwellcraft.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMaxwellCraft extends Item
{
	public ItemMaxwellCraft()
	{
		super();
		this.setCreativeTab(CreativeTabMaxwellCraft.MaxwellCraft_Tab);
	}

	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	public String getName()
	{
		return null;
	}
	
}