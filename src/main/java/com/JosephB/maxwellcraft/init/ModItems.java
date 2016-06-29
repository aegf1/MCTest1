package com.JosephB.maxwellcraft.init;

import com.JosephB.maxwellcraft.item.ItemAntiProton;
import com.JosephB.maxwellcraft.item.ItemProton;
import com.JosephB.maxwellcraft.item.ItemMaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class with methods to initialise all items in mod
 * @author Joseph
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems 
{
	public static final ItemMaxwellCraft proton = new ItemProton();
	public static final ItemMaxwellCraft antiProton = new ItemAntiProton();
		//Do this for every item
	
	public static void init()
	{
		proton.setRegistryName(proton.getName());
		GameRegistry.register(proton);
		antiProton.setRegistryName(antiProton.getName());
		GameRegistry.register(antiProton);
		//Do this for every item
	}
	
	public static void initRenders()
	{
		registerItemRender(proton);
		registerItemRender(antiProton);
		//Do this for every item
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRender(ItemMaxwellCraft item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register((Item) item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemMaxwellCraft) item).getName(), "inventory"));
	}
}
