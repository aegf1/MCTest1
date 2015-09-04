package com.josephb.maxwellcraft.creativetab;

import com.josephb.maxwellcraft.init.ModItems;
import com.josephb.maxwellcraft.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
