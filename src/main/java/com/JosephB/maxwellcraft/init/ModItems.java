package com.josephb.maxwellcraft.init;

import com.josephb.maxwellcraft.item.ItemAntiProton;
import com.josephb.maxwellcraft.item.ItemProton;
import com.josephb.maxwellcraft.item.ItemMaxwellCraft;
import com.josephb.maxwellcraft.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
		GameRegistry.registerItem(proton, proton.getName());
		GameRegistry.registerItem(antiProton, antiProton.getName());
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
