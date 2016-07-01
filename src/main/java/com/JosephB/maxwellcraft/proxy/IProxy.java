package com.JosephB.maxwellcraft.proxy;

import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/**
 * Interface for implementing methods which are called differently on server/client side.
 * @author Joseph Brownless
 */
public interface IProxy 
{
	/**
	 * Spawn proton particle (fire texture)
	 */
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz);
	
	/**
	 * Spawn antiProton particle (smoke texture)
	 */
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz);

}
