package com.josephb.test1.init;

import com.josephb.test1.item.ItemAntiProton;
import com.josephb.test1.item.ItemProton;
import com.josephb.test1.item.ItemTest1;
import com.josephb.test1.reference.Reference;

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
	public static final ItemTest1 proton = new ItemProton();
	public static final ItemTest1 antiProton = new ItemAntiProton();
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
	public static void registerItemRender(ItemTest1 item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register((Item) item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemTest1) item).getName(), "inventory"));
	}
}
