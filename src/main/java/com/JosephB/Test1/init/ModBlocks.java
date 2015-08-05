package com.josephb.test1.init;

import com.josephb.test1.Test1;
import com.josephb.test1.block.BlockMagnet;
import com.josephb.test1.block.BlockNegCharge;
import com.josephb.test1.block.BlockPosCharge;
import com.josephb.test1.block.BlockTest1;
import com.josephb.test1.reference.Reference;
import com.josephb.test1.utility.trackers.MagnetTracker;
import com.josephb.test1.utility.trackers.NegChargeTracker;
import com.josephb.test1.utility.trackers.PosChargeTracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class with methods to initialise all blocks in mod
 * @author Joseph
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks 
{
	public static final BlockTest1 magnet = new BlockMagnet();
	public static final BlockTest1 posCharge = new BlockPosCharge();
	public static final BlockTest1 negCharge = new BlockNegCharge();
	//Do this for every block

	public static void init()
	{
		GameRegistry.registerBlock(magnet, magnet.getName());
		GameRegistry.registerBlock(posCharge, posCharge.getName());
		GameRegistry.registerBlock(negCharge, negCharge.getName());
		//Do this for every block

		Test1.magnetTracker = new MagnetTracker();
		Test1.posChargeTracker = new PosChargeTracker();
		Test1.negChargeTracker = new NegChargeTracker();
	}

	public static void initRenders()
	{
		registerBlockItemRender(magnet);
		registerBlockItemRender(posCharge);
		registerBlockItemRender(negCharge);
		//Do this for every block
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockItemRender(BlockTest1 block)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((BlockTest1) block).getName(), "inventory"));
	}
}
