package com.josephb.test1.entity;

import java.util.Iterator;
import java.util.List;

import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.physics.EMField;
import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityChargedParticle extends EntityThrowable
{
	private float explosionRadius = 2F;
	private float charge = 1F;
	private float mass = 1F;

	protected double ticksInAir;

	public EntityChargedParticle(World world, float mIn, float qIn, float speedIn)
	{
		super(world);
		this.motionX*=speedIn;
		this.motionY*=speedIn;
		this.motionZ*=speedIn;
		ticksInAir = 0;
		setMass(mIn);
		setCharge(qIn);
	}

	public EntityChargedParticle(World world, EntityLivingBase player, float mIn, float qIn, float speedIn)
	{
		super(world, player);
		this.motionX*=speedIn;
		this.motionY*=speedIn;
		this.motionZ*=speedIn;
		ticksInAir = 0;
		setMass(mIn);
		setCharge(qIn);
	}

	public EntityChargedParticle(World world, Vector3 pos, Vector3 vel, float mIn, float qIn)
	{
		super(world, pos.getX(), pos.getY(), pos.getZ());
		this.motionX*=vel.getX();
		this.motionY*=vel.getY();
		this.motionZ*=vel.getZ();
		ticksInAir = 0;
		setMass(mIn);
		setCharge(qIn);
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
				
		//moves entity using built-in methods
		//this.moveEntity(this.motionX, this.motionY, this.motionZ);

		//current and next positions
		Vector3 currentPos = new Vector3(this.posX, this.posY, this.posZ);
		
		// ***update velocity***:
		// v_(n+1) = v_(n) + a * dt
		Vector3 acc = EMField.lorentzForce(currentPos, new Vector3(this.motionX, this.motionY, this.motionZ), charge);
		acc.scaleBy(1/mass);
		this.motionX += acc.getX();
		this.motionY += acc.getY();
		this.motionZ += acc.getZ();
		
		Vector3 nextPos = new Vector3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		
		// detects if it hits a block?
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(currentPos.getVec3(), nextPos.getVec3());

		//again for some reason
		currentPos = new Vector3(this.posX, this.posY, this.posZ);
		nextPos = new Vector3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (movingobjectposition != null)
		{		// if it hits a block, sets final position as hit coord.
			nextPos = new Vector3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}
		if (!this.worldObj.isRemote)
		{
			Entity hitEntity = null;

			// get all entities in/near bounding box (part from this one)
			AxisAlignedBB AABB = this.getEntityBoundingBox();
			AABB.addCoord(this.motionX, this.motionY, this.motionZ); 
			AABB.expand(1.0D, 1.0D, 1.0D);
			List entitiesInAABBList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AABB);
			
			double d0 = 0.0D;
			final Iterator<?> entitiesIterator = entitiesInAABBList.iterator();

			while (entitiesIterator.hasNext())	//iterate over all 'hit' entities
			{
				final Entity thisEntity = (Entity) entitiesIterator.next();

				//if Both:(can collide with this entity) and(Either(entity is NOT thrower) or (been in air for over 5 ticks))
				if (thisEntity.canBeCollidedWith() && (thisEntity != this.getThrower() || this.ticksInAir >= 5))
				{
					//Some sort of value to scale hit entity's BB by?
					final float scale = 0.01F;
					// Expanded BB of hit entity
					final AxisAlignedBB hitBB = thisEntity.getEntityBoundingBox().expand(scale, scale, scale);
					// ??
					final MovingObjectPosition movingobjectposition1 = hitBB.calculateIntercept(currentPos.getVec3(), nextPos.getVec3());

					if (movingobjectposition1 != null)
					{
						final double distToHitVec = currentPos.getVec3().distanceTo(movingobjectposition1.hitVec);

						if (distToHitVec < d0 || d0 == 0.0D)
						{
							hitEntity = thisEntity;	//closest hit entity
							d0 = distToHitVec;
						}	// Finds closest 'hit' entity to current position
					}
				}
			}

			if (hitEntity != null)
			{
				movingobjectposition = new MovingObjectPosition(hitEntity);
			}
		}
		
		
		// r_(n+1) = r_(n) + v(n+1) * dt
		this.posX += this.motionX;	//this.motion is treated as v*dt
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        
        this.ticksInAir++;

		if (movingobjectposition != null)
		{
			this.onImpact(movingobjectposition);
		}	//does onImpact on hitEntity

		if (this.posY <= -20 || this.posY >= 400 || this.ticksInAir > 200)
		{
			this.setDead();
		}	// kills entity if outside world or existed for >10s
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) 
	{
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
				(float)this.explosionRadius, false);
		this.setDead();
	}

	@Override
	protected float getGravityVelocity()
	{
		return 0F;
	}

	public double getTicksInAir() {
		return ticksInAir;
	}

	public void setTicksInAir(double ticksInAir) {
		this.ticksInAir = ticksInAir;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getCharge() {
		return charge;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}
}
