package com.JosephB.maxwellcraft.client.gui;

import com.JosephB.maxwellcraft.handler.ConfigurationHandler;
import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Creates config screen for mod.
 * @author Joseph Brownless
 */
public class ModGuiConfig extends GuiConfig{
	
	/**
	 * Creates config screen, in general catagory.
	 * @param guiScreen
	 */
	public ModGuiConfig(GuiScreen guiScreen)
	{
		super(
			guiScreen,
			new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
			Reference.MOD_ID,
			false,
			false,
			GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString())
			);
	}
}
