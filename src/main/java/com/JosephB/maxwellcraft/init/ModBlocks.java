package com.JosephB.maxwellcraft.init;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.block.BlockMaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockNegCharge;
import com.JosephB.maxwellcraft.block.BlockPosCharge;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.trackers.BlockTracker;

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

	/**
	 * Registers all blocks and itemblocks, and all block trackers
	 */
	public static void init()
	{
		registerBlock(magnet);
		registerBlock(posCharge);
		registerBlock(negCharge);
		
		//Do this for every block

		MaxwellCraft.magnetTracker = new BlockTracker(BlockMagnet.class, null);
		MaxwellCraft.chargeTracker = new BlockTracker(BlockPosCharge.class, BlockNegCharge.class);
	}
	
	private static void registerBlock(BlockMaxwellCraft block)
	{
		GameRegistry.register(block.setRegistryName(block.getName()));				// Register name of block, then register block
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));	//Same for each itemblock
		
		
	}

	/**
	 * Registers all itemBlock renders
	 */
	public static void initRenders()
	{
		registerBlockItemRender(magnet);
		registerBlockItemRender(posCharge);
		registerBlockItemRender(negCharge);
		//Do this for every block
	}

	/**
	 * Registers an itemBlock render
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockItemRender(BlockMaxwellCraft block)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((BlockMaxwellCraft) block).getName(), "inventory"));
	}
}
