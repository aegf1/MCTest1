package com.josephb.test1.init;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.josephb.test1.entity.EntityThrownProton;
import com.josephb.test1.reference.Reference;
import com.josephb.test1.renderers.RenderEntityProton;

/**
 * Class with methods to register all entities in mod
 * @author Joseph
 *
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModEntities 
{
	
	public static void init(Object mod)
	{
		EntityRegistry.registerModEntity(EntityThrownProton.class, "Proton", 1, mod, 200, 3, true );
		//Do this for every entity
	}
	
	@SideOnly(Side.CLIENT)
	public static void initRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownProton.class,new RenderEntityProton());
		//Do this for every entity
	}
	
}
