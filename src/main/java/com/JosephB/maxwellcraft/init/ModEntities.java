package com.JosephB.maxwellcraft.init;

import com.JosephB.maxwellcraft.entity.EntityThrownAntiProton;
import com.JosephB.maxwellcraft.entity.EntityThrownProton;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.renderers.AntiProtonRenderFactory;
import com.JosephB.maxwellcraft.renderers.ProtonRenderFactory;
import com.JosephB.maxwellcraft.renderers.RenderEntityAntiProton;
import com.JosephB.maxwellcraft.renderers.RenderEntityProton;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class with methods to register all entities in mod
 * @author Joseph
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModEntities 
{
	/**
	 * Registers all entities
	 * @param mod
	 */
	public static void init(Object mod)
	{
		EntityRegistry.registerModEntity(EntityThrownProton.class, "Proton", 1, mod, 200, 1, true );
		EntityRegistry.registerModEntity(EntityThrownAntiProton.class, "AntiProton", 2, mod, 200, 1, true );

		//Do this for every entity
	}
	
	/**
	 * Registers all entity renderers
	 */
	@SideOnly(Side.CLIENT)
	public static void initRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownProton.class,new ProtonRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownAntiProton.class,new AntiProtonRenderFactory());
		//Do this for every entity
	}
	
}
