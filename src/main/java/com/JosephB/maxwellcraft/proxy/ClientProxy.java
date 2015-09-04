package com.josephb.maxwellcraft.proxy;

import com.josephb.maxwellcraft.entity.particle.ParticleAntiProton;
import com.josephb.maxwellcraft.entity.particle.ParticleProton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy 
{

	private static Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		EntityFX proton = new ParticleProton(world, x, y, z, vx, vy, vz);
		mc.effectRenderer.addEffect(proton);
	}
	
	@Override
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		EntityFX aproton = new ParticleAntiProton(world, x, y, z, vx, vy, vz);
		mc.effectRenderer.addEffect(aproton);
	}
	
}
