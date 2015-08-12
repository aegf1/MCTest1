package com.josephb.test1.entity;

import com.josephb.test1.Test1;
import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityThrownAntiProton extends EntityRelChargedParticle
{
	private static final float protonCharge = 1F;
	private static final float protonMass = 1F;
	private static final float speed = 5F;	// in blocks/second
	private int tickCountParticleSpawn = 0;

	public EntityThrownAntiProton(World world, EntityLivingBase player) 
	{
		super(world, player, protonMass, -1*protonCharge, speed);
		/* DEBUG
        System.out.println("Constructing on client side ="+worldObj.isRemote);
        System.out.println("Constructor: entity position ="+posX+", "+posY+", "+posZ);
        System.out.println("Constructor: entity motion ="+motionX+", "+motionY+", "+motionZ); */
	}
	
	public EntityThrownAntiProton(World world) 
	{
		super(world, protonMass, -1*protonCharge, speed);
	}
	
	public EntityThrownAntiProton(World world, Vector3 pos, Vector3 vel)
	{
		super(world, pos, vel, protonMass, -1*protonCharge);
		this.motionX*=vel.getX();
		this.motionY*=vel.getY();
		this.motionZ*=vel.getZ();
		setTicksInAir(0);
		setMass(protonMass);
		setCharge(-1*protonCharge);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (tickCountParticleSpawn >= 2) 
		{
			Test1.proxy.spawnAntiProtonParticle(this.worldObj, posX, posY, posZ, 0, 0, 0);
			tickCountParticleSpawn = 0;
//			LogHelper.info(posX+", "+posY+", "+posZ);
		}
		tickCountParticleSpawn++;
	}
}
