package com.JosephB.maxwellcraft.handler;

import java.io.File;

import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Manages the configuration menu (Under 'Mods' on the MC main menu, or 'Mod settings' in the pause menu)
 * 
 * @author Joseph Brownless
 */
public class ConfigurationHandler {

	public static Configuration configuration;
	
	/**
	 * Called upon launching minecraft. Loads the config file.
	 * @param configFile
	 */
	public static void init(File configFile)
	{
		//Create configuration object from given config file
		if (configuration == null)
		{
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
		
	}

	/**
	 * Called when clicking 'Done' in config menu. Saves any changes to the config file, then loads the values again.
	 * @param event
	 */
    @SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
		{
			saveConfiguration();
			LogHelper.info("Saving config");
			loadConfiguration();
		}
	}
	
    /**
     * Loads values from config file. If entries don't exist in file, creates them.
     */
	private static void loadConfiguration()
	{		
		Reference.magDipFieldConst = configuration.getFloat("Magnetic dipole constant", Configuration.CATEGORY_GENERAL, 5F, 
				0F, 1000F, "Field strength of magnetic dipole blocks");
		Reference.eFieldConst = configuration.getFloat("Electric dipole constant", Configuration.CATEGORY_GENERAL, 20F, 
				0F, 5000F, "Field strength of electric charge blocks");
		Reference.lightSpeed = configuration.getFloat("Speed of light", Configuration.CATEGORY_GENERAL, 100F, 
				1F, 10000F, "Speed of light (In blocks/second)");
		Reference.protonSpeed = configuration.getFloat("Proton speed", Configuration.CATEGORY_GENERAL, 5F, 
				0.1F, 1000F, "Speed of protons when thrown (In blocks/second)");
		Reference.aProtonSpeed = configuration.getFloat("Antiproton speed", Configuration.CATEGORY_GENERAL, 5F, 
				0.1F, 1000F, "Speed of antiprotons when thrown (In blocks/second)");
		Reference.protonCharge = configuration.getFloat("Proton charge", Configuration.CATEGORY_GENERAL, 1F, 
				0.1F, 1000F, "Charge of protons");
		Reference.aProtonCharge = configuration.getFloat("Antiproton charge", Configuration.CATEGORY_GENERAL, -1F, 
				-1000F, -0.1F, "Charge of antiprotons");
		Reference.protonMass = configuration.getFloat("Proton mass", Configuration.CATEGORY_GENERAL, 1F, 
				0.01F, 1000F, "Mass of protons");
		Reference.aProtonMass = configuration.getFloat("Antiproton mass", Configuration.CATEGORY_GENERAL, 1F, 
				0.01F, 1000F, "Mass of antiprotons");
		Reference.thrownParticleLifetime = configuration.getInt("Thrown particle lifetime", Configuration.CATEGORY_GENERAL, 1000, 
				20, 2400, "Lifetime of thrown protons and antiprotons (In ticks)");
		Reference.particleExplosionRad = configuration.getFloat("Particle explosion radius", Configuration.CATEGORY_GENERAL, 1F, 
				0F, 5F, "Radius of explosions when particles collide with something");

		LogHelper.info("Loading config");
	}
	
	/**
	 * Saves changes to config values to file.
	 */
	private static void saveConfiguration()
	{
		if (configuration.hasChanged())
		{
			configuration.save();
		}
	}
}
