package com.JosephB.maxwellcraft.renderers;

import com.JosephB.maxwellcraft.entity.EntityThrownProton;
import com.JosephB.maxwellcraft.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

/**
 * Render for thrown protons
 * @author Joseph Brownless
 */
public class RenderEntityProton extends RenderEntityMaxwellcraftParticle
{
	/**
	 * Creates render using default minecraft rendermanager
	 */
	public RenderEntityProton()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
	/**
	 * Creates render using given renderManager
	 * @param renderManager
	 */
	public RenderEntityProton(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	/**
	 * Called every tick, to render antiParticle
	 */
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityThrownProton)entity, x, y, z, entityYaw, partialTicks);
    }

	/**
	 * Get entity texture
	 */
	@Override
	public TextureAtlasSprite getTextureAtlasSprite()
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.proton);
	}
	
	
}
