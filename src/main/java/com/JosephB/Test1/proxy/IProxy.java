package com.josephb.test1.proxy;

import net.minecraft.world.World;

public interface IProxy 
{
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz);
	
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz);

}
