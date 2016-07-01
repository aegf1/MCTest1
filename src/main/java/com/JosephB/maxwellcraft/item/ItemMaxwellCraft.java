package com.JosephB.maxwellcraft.item;

import com.JosephB.maxwellcraft.creativetab.CreativeTabMaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Base item class for mod items
 * @author Joseph Brownless
 */
public class ItemMaxwellCraft extends Item
{
	/**
	 * Creates default item, in mod's creative tab
	 */
	public ItemMaxwellCraft()
	{
		super();
		this.setCreativeTab(CreativeTabMaxwellCraft.MaxwellCraft_Tab);
	}

	/**
	 * @return Unlocalised name of item
	 */
	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	/**
	 * @return Unlocalised name of item
	 */
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	

	/**
	 * @return Unlocalised name of item without item. at start
	 */
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	/**
	 * @return Name of item
	 */
	public String getName()
	{
		return null;
	}
	
}
