package com.josephb.test1.entity;

import com.josephb.test1.Test1;
import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityThrownProton extends EntityChargedParticle
{
	private static final float protonCharge = 1F;
	private static final float protonMass = 1F;
	private static final float speed = 2F;	// in blocks/second
	private static double tickCountParticleSpawn = 0;
	
	public EntityThrownProton(World world, EntityLivingBase player) 
	{
		super(world, player, protonMass, protonCharge, speed);
	}
	
	public EntityThrownProton(World world) 
	{
		super(world, protonMass, protonCharge, speed);
	}
	
	public EntityThrownProton(World world, Vector3 pos, Vector3 vel)
	{
		super(world, pos, vel, protonMass, protonCharge);
		this.motionX*=vel.getX();
		this.motionY*=vel.getY();
		this.motionZ*=vel.getZ();
		setTicksInAir(0);
		setMass(protonMass);
		setCharge(protonCharge);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (tickCountParticleSpawn >= 2) 
		{
			Test1.proxy.spawnProtonParticle(this.worldObj, posX, posY, posZ, 0, 0, 0);
//			this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0, 0, 0, 0);
			tickCountParticleSpawn = 0;
//			LogHelper.info(posX+", "+posY+", "+posZ);
		}
		tickCountParticleSpawn++;
	}
}
