package com.JosephB.maxwellcraft.entity;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * Represents a thrown proton.
 * @author Joseph Brownless
 */
public class EntityThrownProton extends EntityChargedParticle
{
	private static double tickCountParticleSpawn = 0;
	
	/**
	 * Creates entity with no defined position or motion
	 * @param world
	 */
	public EntityThrownProton(World world) 
	{
		super(world, Reference.protonMass, Reference.protonCharge, Reference.protonSpeed/20F);
	}
	
	/**
	 * Creates entity as thrown by player (or similar)
	 * @param world
	 * @param player thrower
	 */
	public EntityThrownProton(World world, EntityLivingBase player) 
	{
		super(world, player, Reference.protonMass, Reference.protonCharge, Reference.protonSpeed/20F);	
	}
	
	/**
	 * Creates entity with defined position and velocity
	 * @param world
	 * @param pos position
	 * @param vel velocity
	 */
	public EntityThrownProton(World world, Vector3 pos, Vector3 vel)
	{
		super(world, pos, vel, Reference.protonMass, Reference.protonCharge);
	}
	
	/**
	 * Called every tick. Does motion defined in super, and spawns particles every 2 ticks
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (tickCountParticleSpawn >= 3) 
		{
			MaxwellCraft.proxy.spawnProtonParticle(this.worldObj, posX, posY, posZ, 0, 0, 0);
//			this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0, 0, 0, 0);
			tickCountParticleSpawn = 0;
//			LogHelper.info(posX+", "+posY+", "+posZ);
		}
		tickCountParticleSpawn++;
	}
}
