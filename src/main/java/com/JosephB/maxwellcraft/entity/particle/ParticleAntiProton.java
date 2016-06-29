package com.JosephB.maxwellcraft.entity.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author Joseph Brownless
 */
@SideOnly(Side.CLIENT)
public class ParticleAntiProton extends Particle
{
	/**
	 * Creates particle with defined position and motion
	 * @param worldIn
	 * @param x
	 * @param y
	 * @param z
	 * @param xSpeed
	 * @param ySpeed
	 * @param zSpeed
	 */
    public ParticleAntiProton(World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        super(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        this.motionX = this.motionY = this.motionZ = 0;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleMaxAge = 200;
        this.field_190017_n = true;
        this.setParticleTextureIndex(4);
        this.particleTextureJitterX = 0;
        this.particleTextureJitterY = 0;
        this.particleScale = 1.5F;
    }

//	public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
//	{
//		float f6 = ((float)this.particleAge + p_180434_3_) / (float)this.particleMaxAge;
//		super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
//	}

    /**
     * Get particle brightness. Depends on particle age, and some randomness
     */
    public int getBrightnessForRender(float partialTicks)
    {
        float f1 = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
        f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(partialTicks);
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

    /**
     * Generates particles
     * @author Joseph Brownless
     *
     */
    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
        {
            /**
             * Gives out particles 
             */
            public Particle getEntityFX(int id, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int ... parameters)
            {
                return new ParticleAntiProton(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            }
        }
}