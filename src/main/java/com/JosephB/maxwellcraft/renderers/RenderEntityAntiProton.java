package com.JosephB.maxwellcraft.renderers;

import com.JosephB.maxwellcraft.entity.EntityThrownAntiProton;
import com.JosephB.maxwellcraft.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

public class RenderEntityAntiProton extends RenderEntityChargedParticle
{
	private float scale = 1F;
	
	public RenderEntityAntiProton()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
	public RenderEntityAntiProton(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityThrownAntiProton)entity, x, y, z, entityYaw, partialTicks);
     /* DEBUG
        System.out.println("Rendering on client side ="+entity.worldObj.isRemote);
        System.out.println("entity position ="+entity.posX+", "+entity.posY+", "+entity.posZ);
        System.out.println("entity motion ="+entity.motionX+", "+entity.motionY+", "+entity.motionZ); */
    }

	@Override
	public TextureAtlasSprite getTextureAtlasSprite()
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.antiProton);
	}
}
