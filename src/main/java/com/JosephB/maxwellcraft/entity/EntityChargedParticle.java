package com.JosephB.maxwellcraft.entity;

import java.util.Iterator;
import java.util.List;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.dataoutput.OutputHelper;
import com.JosephB.maxwellcraft.utility.physics.EMField;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.Mod;

/**
 * Represents a charged particle
 * @author Joseph Brownless
 */
public class EntityChargedParticle extends EntityMaxwellcraftParticle
{
	protected float charge;
	
	/**
	 * Creates particle with undefined position and motion
	 * @param world
	 * @param mIn mass
	 * @param qIn charge
	 * @param speedIn speed (per tick)
	 */
	public EntityChargedParticle(World world, float mIn, float qIn, float speedIn)
	{
		super(world, mIn, speedIn);	// Speed is per TICK
		setCharge(qIn);
	}

	/**
	 * Creates particle, as thrown by a player (or similar)
	 * @param world 
	 * @param player thrower
	 * @param mIn mass
	 * @param qIn charge
	 * @param speedIn speed (per tick)
	 */
	public EntityChargedParticle(World world, EntityLivingBase player, float mIn, float qIn, float speedIn)
	{
		super(world, player, mIn, speedIn);	// Speed is per TICK
		setCharge(qIn);
	}

	/**
	 * Creates particle with defined position and velocity
	 * @param world
	 * @param pos position
	 * @param vel velocity (per second)
	 * @param mIn mass
	 * @param qIn charge
	 */
	public EntityChargedParticle(World world, Vector3 posIn, Vector3 velIn, float mIn, float qIn)
	{
		super(world, posIn, velIn, mIn);	// velocity is per second
		setCharge(qIn);
	}

	/**
	 * Called at start of all constructors.
	 */
	protected void entityInit()
	{
		super.entityInit();
	}
	
	/**
	 * Calculates the force on this entity, with a given position and velocity
	 * (Note: Does not use the entity's locally stored pos/vel, so that complex numerical methods can be used)
	 * @param pos particle position
	 * @param vel particle velocity
	 * @return the force
	 */
	@Override
	protected Vector3 calcForce(Vector3 pos, Vector3 vel)
	{
		return EMField.lorentzForce(pos, vel, this.charge);
	}
	
	/**
	 * 
	 * @return Charge
	 */
	public float getCharge() {
		return charge;
	}

	/**
	 * Set charge
	 * @param charge
	 */
	public void setCharge(float charge) {
		this.charge = charge;
	}
}
