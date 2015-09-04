package com.josephb.maxwellcraft.renderers;

import com.josephb.maxwellcraft.entity.EntityThrownProton;
import com.josephb.maxwellcraft.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

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
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((EntityThrownProton)entity, x, y, z, p_76986_8_, partialTicks);
    }


	@Override
	public TextureAtlasSprite getTextureAtlasSprite()
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.proton);
	}
}
