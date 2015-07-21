package com.josephb.test1.entity;

import java.util.Iterator;
import java.util.List;

import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityThrownProton extends EntityThrowable
{
	private float explosionRadius = 2F;
	private float speed = 0.5F;

	private double ticksInAir;

	public EntityThrownProton(World world)
	{
		super(world);
		this.motionX*=speed;
		this.motionY*=speed;
		this.motionZ*=speed;
		ticksInAir = 0;
	}

	public EntityThrownProton(World world, EntityLivingBase player)
	{
		super(world, player);
		this.motionX*=speed;
		this.motionY*=speed;
		this.motionZ*=speed;
		ticksInAir = 0;
	}

	public EntityThrownProton(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.motionX*=speed;
		this.motionY*=speed;
		this.motionZ*=speed;
		ticksInAir = 0;
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
		Vec3 currentPos = new Vec3(this.posX, this.posY, this.posZ);
		Vec3 nextPos = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		// detects if it hits a block?
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(currentPos, nextPos);

		//again for some reason
		currentPos = new Vec3(this.posX, this.posY, this.posZ);
		nextPos = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (movingobjectposition != null)
		{		// if it hits a block, sets final position as hit coord.
			nextPos = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
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
					final MovingObjectPosition movingobjectposition1 = hitBB.calculateIntercept(currentPos, nextPos);

					if (movingobjectposition1 != null)
					{
						final double distToHitVec = currentPos.distanceTo(movingobjectposition1.hitVec);

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
		
		this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        
        this.ticksInAir++;

		if (movingobjectposition != null)
		{
			this.onImpact(movingobjectposition);
		}	//does onImpact on hitEntity

		if (this.posY <= -20 || this.posY >= 400)
		{
			this.setDead();
		}	// kills entity if outside world
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		// TODO Auto-generated method stub
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
				(float)this.explosionRadius, false);
		this.setDead();
	}

	@Override
	protected float getGravityVelocity()
	{
		return 0F;
	}
}
