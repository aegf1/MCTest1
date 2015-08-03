package com.josephb.test1.entity.particle;

import com.josephb.test1.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleAntiProton  extends EntityFX
{
	private static ResourceLocation loc = new ResourceLocation(Reference.MOD_ID_LC, "textures/item/AntiProton.png");
	
	public ParticleAntiProton(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		super(world, x, y, z, vx, vy, vz);
		this.particleTextureIndexX = 0;
        this.particleTextureIndexY = 0;
        this.noClip = true;
        this.particleAge = 0;
        this.particleMaxAge = 500;
        this.particleScale *= 3.4F;
        this.particleRed = this.particleGreen = this.particleBlue = 0.1F;
        this.particleAlpha = 1;
	}
	
    @Override
    public void onUpdate() {
        // ...
    }
    @Override
    public void func_180434_a(WorldRenderer worldRenderer, Entity e, float f1, float f2, float f3, float f4, float f5, float f6) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        worldRenderer.startDrawingQuads();
        super.func_180434_a(worldRenderer, e, f1, f2, f3, f4, f5, f6);
        Tessellator.getInstance().draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }
    @Override
    public int getFXLayer() {
        return 3; // THE IMPORTANT PART
    }
}
