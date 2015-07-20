package com.josephb.test1.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Recipes 
{
	public static void init()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.magnet), " g ", "gIg", " g ",
			'g', "ingotGold", 'I', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.proton, 16), "rrr", "rcr", "rpr",
			'r', "dustRedstone",'c', "gemQuartz", 'p', new ItemStack(Items.ender_pearl)));
	}

}
