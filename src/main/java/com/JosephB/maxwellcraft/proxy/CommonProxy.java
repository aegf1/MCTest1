package com.JosephB.maxwellcraft.proxy;

import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/**
 * 
 * @author Joseph Brownless
 */
public class CommonProxy implements IProxy
{
	public void spawnProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
	}
	
	public void spawnAntiProtonParticle(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		return;
	}

}
