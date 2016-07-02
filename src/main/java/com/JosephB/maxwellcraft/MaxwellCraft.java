package com.JosephB.maxwellcraft;

import com.JosephB.maxwellcraft.commands.GetBField;
import com.JosephB.maxwellcraft.commands.GetEField;
import com.JosephB.maxwellcraft.commands.StartDataRecord;
import com.JosephB.maxwellcraft.commands.StopDataRecord;
import com.JosephB.maxwellcraft.commands.TestCommand;
import com.JosephB.maxwellcraft.handler.ConfigurationHandler;
import com.JosephB.maxwellcraft.handler.MaxwellCraftEventHandler;
import com.JosephB.maxwellcraft.init.ModBlocks;
import com.JosephB.maxwellcraft.init.ModEntities;
import com.JosephB.maxwellcraft.init.ModItems;
import com.JosephB.maxwellcraft.init.ModTileEntities;
import com.JosephB.maxwellcraft.init.Recipes;
import com.JosephB.maxwellcraft.proxy.IProxy;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.trackers.BlockTracker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Main mod file. Triggers loading of all objects in mod.
 * @author Joseph Brownless
 */
@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME , version=Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class MaxwellCraft 
{
	@Mod.Instance(Reference.MOD_ID)
	public static MaxwellCraft Instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS, modId= Reference.MOD_ID)
	public static IProxy proxy;

	/**
	 * Create trackers for each block type.
	 * Used to calculate forces on entities.
	 * Initialised in {@link ModBlocks#init()}
	 */
	public static BlockTracker magnetTracker;
	public static BlockTracker chargeTracker;

	/**
	 * Unused?
	 */
	private int ticks = 0;

	/**
	 * Called in 1st phase of launching game
	 * Initialise config menu, items, blocks, entities, tileEntities and entity renderers
	 * @param event FML pre-initialisation event
	 */
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//Code for config, blocks, items etc

		LogHelper.info("PreInit Stage");

		ConfigurationHandler.init(event.getSuggestedConfigurationFile());		//init. config handler
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());	//register config handler on event bus

		//Initialise all items
		ModItems.init();

		//Initialise all blocks
		ModBlocks.init();

		//Initialise all entities
		ModEntities.init(this);

		//Initialise all tile entities
		ModTileEntities.init();

		//Register Server tick handler (For trackers refresher)
		FMLCommonHandler.instance().bus().register(new MaxwellCraftEventHandler());
		
		if(event.getSide() == Side.CLIENT)
		{
			//Initialise all entity renders
			ModEntities.initRenders();
		}
	}

	/**
	 * Called in 2nd phase of launching game.
	 * Initialises item + block renderers, and recipes.
	 * @param event FML initialisation event
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		//Code for GUIs, tile entities, recipes etc

		LogHelper.info("Init Stage");

		if(event.getSide() == Side.CLIENT)
		{
			//Initialise all item renders
			ModItems.initRenders();

			//Initialise all block item renders
			ModBlocks.initRenders();
		}

		//Initialise recipes
		Recipes.init();
	}

	/**
	 * Called in 3rd phase of launching game.
	 * Does nothing right now.
	 * @param event FML post-initialisation event
	 */
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		// ??

		LogHelper.info("PostInit Stage");
	}

	/**
	 * Called when loading a server.
	 * Initialises server commands
	 * @param event FML server starting event
	 */
	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new TestCommand());
		event.registerServerCommand(new StartDataRecord());
		event.registerServerCommand(new StopDataRecord());
		event.registerServerCommand(new GetEField());
		event.registerServerCommand(new GetBField());
	}
}
