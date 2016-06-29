package com.JosephB.maxwellcraft.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * 
 * @author Joseph Brownless
 */
public class Recipes 
{
	public static void init()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.magnet), " g ", "gIg", " g ",
			'g', "ingotGold", 'I', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.proton, 16), "rrr", "rqr", "rpr",
			'r', "dustRedstone",'q', "gemQuartz", 'p', new ItemStack(Items.ENDER_PEARL)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.proton, 16), "ggg", "gqg", "gpg",
				'g', "dustGlowstone",'q', "gemQuartz", 'p', new ItemStack(Items.ENDER_PEARL)));
	}

}
