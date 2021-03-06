package com.JosephB.maxwellcraft.client.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

/**
 * Creates gui screens for mod. 
 * Currently, only config screen
 * @author Joseph Brownless
 */
public class GuiFactory implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) {

		
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		
		return ModGuiConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {

		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(
			RuntimeOptionCategoryElement element) {

		return null;
	}

}
