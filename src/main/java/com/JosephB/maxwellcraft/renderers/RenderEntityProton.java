package com.JosephB.maxwellcraft.renderers;

import com.JosephB.maxwellcraft.entity.EntityThrownProton;
import com.JosephB.maxwellcraft.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

/**
 * 
 * @author Joseph Brownless
 */
public class RenderEntityProton extends RenderEntityChargedParticle
{
	private float scale = 1F;

	public RenderEntityProton()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
	public RenderEntityProton(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityThrownProton)entity, x, y, z, entityYaw, partialTicks);
    }


	@Override
	public TextureAtlasSprite getTextureAtlasSprite()
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.proton);
	}
}
