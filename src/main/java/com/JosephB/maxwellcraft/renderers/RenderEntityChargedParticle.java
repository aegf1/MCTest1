package com.JosephB.maxwellcraft.renderers;

import com.JosephB.maxwellcraft.entity.EntityChargedParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityChargedParticle extends Render
{
	private float scale = 0.5F;
	
	
	public RenderEntityChargedParticle()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}
	
	public RenderEntityChargedParticle(RenderManager renderManager) 
	{
		super(renderManager);
	}

	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		
		return null;
	}
	
	public void doRender(EntityChargedParticle entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        float f2 = this.scale;
        GlStateManager.scale(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        TextureAtlasSprite textureatlassprite = this.getTextureAtlasSprite();
        		//=Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.proton);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vbuffer = tessellator.getBuffer();
        float f3 = textureatlassprite.getMinU();
        float f4 = textureatlassprite.getMaxU();
        float f5 = textureatlassprite.getMinV();
        float f6 = textureatlassprite.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        
        vbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vbuffer.pos(-0.5D, -0.25D, 0.0D).tex((double)f3, (double)f6).normal(0.0F, 1.0F, 0.0F).endVertex();
        vbuffer.pos(0.5D, -0.25D, 0.0D).tex((double)f4, (double)f6).normal(0.0F, 1.0F, 0.0F).endVertex();
        vbuffer.pos(0.5D, 0.75D, 0.0D).tex((double)f4, (double)f5).normal(0.0F, 1.0F, 0.0F).endVertex();
        vbuffer.pos(-0.5D, 0.75D, 0.0D).tex((double)f3, (double)f5).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public TextureAtlasSprite getTextureAtlasSprite() 
	{
		return null;
	}

	/**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probability, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((EntityChargedParticle)entity, x, y, z, p_76986_8_, partialTicks);
    }
}
