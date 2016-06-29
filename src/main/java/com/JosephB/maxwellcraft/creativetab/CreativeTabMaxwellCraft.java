package com.JosephB.maxwellcraft.creativetab;

import com.JosephB.maxwellcraft.init.ModItems;
import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * 
 * @author Joseph Brownless
 */
public class CreativeTabMaxwellCraft 
{
	public static final CreativeTabs MaxwellCraft_Tab = new CreativeTabs(Reference.MOD_ID.toLowerCase())
	{
		@Override
		public Item getTabIconItem()
		{
			return ModItems.proton;
		}
		
		
	};

	
}
