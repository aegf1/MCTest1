package com.josephb.test1.proxy;

import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.world.World;

public interface IProxy 
{
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz);
	
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz);
	
	public boolean recordParticleDataPoint(Vector3 point);

}
