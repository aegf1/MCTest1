package com.JosephB.maxwellcraft.proxy;

import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.dataoutput.OutputHelper;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.world.World;

/**
 * Implements IProxy methods for use on server side
 * @author Joseph Brownless
 */
public class ServerProxy implements IProxy
{
	/**
	 * Spawn proton particle (fire texture)
	 */
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		return;
	}
	
	/**
	 * Spawn antiProton particle (smoke texture)
	 */
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		return;
	}

}
