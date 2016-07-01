package com.JosephB.maxwellcraft.creativetab;

import com.JosephB.maxwellcraft.init.ModItems;
import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Defines a creative-mode tab containing all items for this mod
 * @author Joseph Brownless
 */
public class CreativeTabMaxwellCraft 
{
	/**
	 * Creates the creative-mode tab for this mod, to which all items (including itemBlocks) are added.
	 */
	public static final CreativeTabs MaxwellCraft_Tab = new CreativeTabs(Reference.MOD_ID.toLowerCase())
	{
		/**
		 * Uses the the proton texture as the tab icon
		 */
		@Override
		public Item getTabIconItem()
		{
			return ModItems.proton;
		}
		
		
	};

	
}
