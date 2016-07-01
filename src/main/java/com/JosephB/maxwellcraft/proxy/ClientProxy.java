package com.JosephB.maxwellcraft.proxy;

import com.JosephB.maxwellcraft.entity.particle.ParticleAntiProton;
import com.JosephB.maxwellcraft.entity.particle.ParticleProton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

/**
 * Implements IProxy methods for use on client side
 * @author Joseph Brownless
 */
public class ClientProxy implements IProxy
{

	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Spawn proton particle (fire texture)
	 */
	@Override
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		Particle proton = new ParticleProton(world, x, y, z, vx, vy, vz);
		mc.effectRenderer.addEffect(proton);
	}
	
	/**
	 * Spawn antiProton particle (smoke texture)
	 */
	@Override
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		Particle aproton = new ParticleAntiProton(world, x, y, z, vx, vy, vz);
		mc.effectRenderer.addEffect(aproton);
	}
	
}
