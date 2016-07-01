package com.JosephB.maxwellcraft.renderers;

import com.JosephB.maxwellcraft.entity.EntityThrownAntiProton;
import com.JosephB.maxwellcraft.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

/**
 * Render for thrown antiProtons
 * @author Joseph Brownless
 */
public class RenderEntityAntiProton extends RenderEntityChargedParticle
{
	/**
	 * Creates render using default minecraft rendermanager
	 */
	public RenderEntityAntiProton()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
	/**
	 * Creates render using given renderManager
	 * @param renderManager
	 */
	public RenderEntityAntiProton(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	/**
	 * Called every tick, to render antiParticle
	 */
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.doRender((EntityThrownAntiProton)entity, x, y, z, entityYaw, partialTicks);
     /* DEBUG
        System.out.println("Rendering on client side ="+entity.worldObj.isRemote);
        System.out.println("entity position ="+entity.posX+", "+entity.posY+", "+entity.posZ);
        System.out.println("entity motion ="+entity.motionX+", "+entity.motionY+", "+entity.motionZ); */
    }

	/**
	 * Get entity texture
	 */
	@Override
	public TextureAtlasSprite getTextureAtlasSprite()
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.antiProton);
	}
}
