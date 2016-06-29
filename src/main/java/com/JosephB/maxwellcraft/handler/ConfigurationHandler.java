package com.JosephB.maxwellcraft.handler;

import java.io.File;

import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author Joseph Brownless
 */
public class ConfigurationHandler {

	public static Configuration configuration;
	public static boolean testValue = false;
	
	public static void init(File configFile)
	{
		//Create configuration object from given config file
		if (configuration == null)
		{
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
		
	}
	
	@Mod.EventHandler
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
		{
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration()
	{
		testValue = configuration.getBoolean("configValue", Configuration.CATEGORY_GENERAL, false, "This is an example config value");
		
		if (configuration.hasChanged())
		{
			configuration.save();
		}
	}
}
