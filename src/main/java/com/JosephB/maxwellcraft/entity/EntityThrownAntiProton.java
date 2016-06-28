package com.JosephB.maxwellcraft.entity;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityThrownAntiProton extends EntityRelChargedParticle
{
	private int tickCountParticleSpawn = 0;

	public EntityThrownAntiProton(World world, EntityLivingBase player) 
	{
		super(world, player, Reference.aProtonMass, Reference.aProtonCharge, Reference.aProtonSpeed/20F);
		/* DEBUG
        System.out.println("Constructing on client side ="+worldObj.isRemote);
        System.out.println("Constructor: entity position ="+posX+", "+posY+", "+posZ);
        System.out.println("Constructor: entity motion ="+motionX+", "+motionY+", "+motionZ); */
	}
	
	public EntityThrownAntiProton(World world) 
	{
		super(world, Reference.aProtonMass, Reference.aProtonCharge, Reference.aProtonSpeed/20F);
	}
	
	public EntityThrownAntiProton(World world, Vector3 pos, Vector3 vel)
	{
		super(world, pos, vel, Reference.aProtonMass, Reference.aProtonCharge);
		this.motionX*=vel.getX();
		this.motionY*=vel.getY();
		this.motionZ*=vel.getZ();
		setTicksInAir(0);
	}
	
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
