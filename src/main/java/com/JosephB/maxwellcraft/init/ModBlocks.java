package com.josephb.maxwellcraft.init;

import com.josephb.maxwellcraft.MaxwellCraft;
import com.josephb.maxwellcraft.block.BlockMagnet;
import com.josephb.maxwellcraft.block.BlockNegCharge;
import com.josephb.maxwellcraft.block.BlockPosCharge;
import com.josephb.maxwellcraft.block.BlockMaxwellCraft;
import com.josephb.maxwellcraft.reference.Reference;
import com.josephb.maxwellcraft.utility.trackers.MagnetTracker;
import com.josephb.maxwellcraft.utility.trackers.NegChargeTracker;
import com.josephb.maxwellcraft.utility.trackers.PosChargeTracker;

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
	public static final BlockMaxwellCraft magnet = new BlockMagnet();
	public static final BlockMaxwellCraft posCharge = new BlockPosCharge();
	public static final BlockMaxwellCraft negCharge = new BlockNegCharge();
	//Do this for every block

	public static void init()
	{
		GameRegistry.registerBlock(magnet, magnet.getName());
		GameRegistry.registerBlock(posCharge, posCharge.getName());
		GameRegistry.registerBlock(negCharge, negCharge.getName());
		//Do this for every block

		MaxwellCraft.magnetTracker = new MagnetTracker();
		MaxwellCraft.posChargeTracker = new PosChargeTracker();
		MaxwellCraft.negChargeTracker = new NegChargeTracker();
	}

	public static void initRenders()
	{
		registerBlockItemRender(magnet);
		registerBlockItemRender(posCharge);
		registerBlockItemRender(negCharge);
		//Do this for every block
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockItemRender(BlockMaxwellCraft block)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((BlockMaxwellCraft) block).getName(), "inventory"));
	}
}
