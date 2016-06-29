package com.JosephB.maxwellcraft.item;

import com.JosephB.maxwellcraft.creativetab.CreativeTabMaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * 
 * @author Joseph Brownless
 */
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
