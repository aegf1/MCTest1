package com.josephb.test1;

import com.josephb.test1.handler.ConfigurationHandler;
import com.josephb.test1.init.*;
import com.josephb.test1.proxy.IProxy;
import com.josephb.test1.reference.Reference;
import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.ServerTickHandler;
import com.josephb.test1.utility.trackers.MagnetTracker;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME , version=Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Test1 
{
	
	@Mod.Instance(Reference.MOD_ID)
	public static Test1 Instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	//Do this for every block
	public static MagnetTracker magnetTracker;
	
	private int ticks = 0;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//Code for config, blocks, items etc
		
		LogHelper.info("PreInit Stage");
		
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());		//init. config handler
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());	//register config handler on event bus
		
		//Initialise all items
		ModItems.init();
		
		//Initialise all blocks
		ModBlocks.init();
		
		//Initialise all entities
		ModEntities.init(this);
		
		//Initialise all tile entities
		ModTileEntities.init();
		
		//Register Server tick handler (For magnet tracker refresher)
		FMLCommonHandler.instance().bus().register(new ServerTickHandler());
	}
	
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
			
			//Initialise all entity renders
			ModEntities.initRenders();
		}
		
		//Initialise recipes
		Recipes.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		// ??
		
		LogHelper.info("PostInit Stage");
	}
}
