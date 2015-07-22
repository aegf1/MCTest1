package com.josephb.test1.entity;

import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityThrownProton extends EntityChargedParticle
{
	private static final float protonCharge = 1F;
	private static final float protonMass = 1F;
	private static final float speed = 0.5F;
	
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
}
