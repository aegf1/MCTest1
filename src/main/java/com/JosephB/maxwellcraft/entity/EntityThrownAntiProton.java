package com.JosephB.maxwellcraft.entity;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * 
 * @author Joseph Brownless
 */
public class EntityThrownAntiProton extends EntityRelChargedParticle
{
	private int tickCountParticleSpawn = 0;

	/**
	 * Creates entity with no defined position or motion
	 * @param world
	 */
	public EntityThrownAntiProton(World world) 
	{
		super(world, Reference.aProtonMass, Reference.aProtonCharge, Reference.aProtonSpeed/20F);
	}
	
	/**
	 * Creates entity as thrown by player (or similar)
	 * @param world
	 * @param player thrower
	 */
	public EntityThrownAntiProton(World world, EntityLivingBase player) 
	{
		super(world, player, Reference.aProtonMass, Reference.aProtonCharge, Reference.aProtonSpeed/20F);
	}

	/**
	 * Creates entity with defined position and velocity
	 * @param world
	 * @param pos position
	 * @param vel velocity
	 */
	public EntityThrownAntiProton(World world, Vector3 pos, Vector3 vel)
	{
		super(world, pos, vel, Reference.aProtonMass, Reference.aProtonCharge);
		this.motionX*=vel.getX();
		this.motionY*=vel.getY();
		this.motionZ*=vel.getZ();
		setTicksInAir(0);
	}
	
	/**
	 * Called every tick. Does motion defined in super, and spawns particles every 2 ticks
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (tickCountParticleSpawn >= 2) 
		{
			MaxwellCraft.proxy.spawnAntiProtonParticle(this.worldObj, posX, posY, posZ, 0, 0, 0);
			tickCountParticleSpawn = 0;
//			LogHelper.info(posX+", "+posY+", "+posZ);
		}
		tickCountParticleSpawn++;
	}
}
