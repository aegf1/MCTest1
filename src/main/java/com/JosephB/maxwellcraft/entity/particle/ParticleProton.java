package com.JosephB.maxwellcraft.entity.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleProton extends Particle
{
    private static final String __OBFID = "CL_00000907";

    public ParticleProton(World worldIn, double x, double y, double z, double XMov, double YMov, double ZMov)
    {
        super(worldIn, x, y, z, XMov, YMov, ZMov);
        this.motionX = this.motionY = this.motionZ = 0;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleMaxAge = 200;
        this.field_190017_n = true;
        this.setParticleTextureIndex(48);
        this.particleTextureJitterX = 0;
        this.particleTextureJitterY = 0;
        this.particleScale = 1.5F;
    }

//	public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
//	{
//		float f6 = ((float)this.particleAge + p_180434_3_) / (float)this.particleMaxAge;
//		super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
//	}

    public int getBrightnessForRender(float p_70070_1_)
    {
        float f1 = ((float)this.particleAge + p_70070_1_) / (float)this.particleMaxAge;
        f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(p_70070_1_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
        {
            private static final String __OBFID = "CL_00002602";

            public Particle getEntityFX(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int ... p_178902_15_)
            {
                return new ParticleProton(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            }
        }
}