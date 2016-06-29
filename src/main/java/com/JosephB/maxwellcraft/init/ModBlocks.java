package com.JosephB.maxwellcraft.init;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.block.BlockNegCharge;
import com.JosephB.maxwellcraft.block.BlockPosCharge;
import com.JosephB.maxwellcraft.block.BlockMaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.trackers.MagnetTracker;
import com.JosephB.maxwellcraft.utility.trackers.NegChargeTracker;
import com.JosephB.maxwellcraft.utility.trackers.PosChargeTracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
		GameRegistry.register(magnet.setRegistryName(magnet.getName()));				// Register name of each block, then register block
		GameRegistry.register(new ItemBlock(magnet).setRegistryName(magnet.getRegistryName()));	//Same for each itemblock
		GameRegistry.register(posCharge.setRegistryName(posCharge.getName()));
		GameRegistry.register(new ItemBlock(posCharge).setRegistryName(posCharge.getRegistryName()));
		GameRegistry.register(negCharge.setRegistryName(negCharge.getName()));
		GameRegistry.register(new ItemBlock(negCharge).setRegistryName(negCharge.getRegistryName()));
		
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
