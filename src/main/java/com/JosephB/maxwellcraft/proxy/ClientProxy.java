package com.JosephB.maxwellcraft.proxy;

import com.JosephB.maxwellcraft.entity.particle.ParticleAntiProton;
import com.JosephB.maxwellcraft.entity.particle.ParticleProton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

/**
 * 
 * @author Joseph Brownless
 */
public class ClientProxy extends CommonProxy 
{

	private static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		Particle proton = new ParticleProton(world, x, y, z, vx, vy, vz);
		mc.effectRenderer.addEffect(proton);
	}
	
	@Override
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		Particle aproton = new ParticleAntiProton(world, x, y, z, vx, vy, vz);
		mc.effectRenderer.addEffect(aproton);
	}
	
}
