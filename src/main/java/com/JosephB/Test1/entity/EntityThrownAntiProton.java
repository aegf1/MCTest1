package com.josephb.test1.entity;

import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityThrownAntiProton extends EntityChargedParticle
{
	private static final float protonCharge = 1F;
	private static final float protonMass = 1F;
	private static final float speed = 1F;

	public EntityThrownAntiProton(World world, EntityLivingBase player) 
	{
		super(world, player, protonMass, -1*protonCharge, speed);
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
}
