package com.JosephB.maxwellcraft.entity;

import java.util.Iterator;
import java.util.List;

import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.physics.EMField;
import com.JosephB.maxwellcraft.utility.physics.UpdateMethods;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityChargedParticle extends EntityThrowable
{
	protected float explosionRadius = 2F;
	public float charge = 1F;
	public float mass = 1F;
	protected int lifetime = 1000; 		// lifetime in ticks
	
	protected double ticksInAir;

	public EntityChargedParticle(World world, float mIn, float qIn, float speedIn)
	{
		super(world);
		normaliseMotion();
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
		this.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 0.0F);
		normaliseMotion();
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
		normaliseMotion();
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
		
		// Recording previous positions
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		//current position and velocity
		Vector3 currentPos = new Vector3(this.posX, this.posY, this.posZ);
		Vector3 currentVel = new Vector3(this.motionX, this.motionY, this.motionZ);
		
		// ***update velocity***:
//		Vector3 nextVel = UpdateMethods.updateVelEC(currentVel, getAcc(), 0.05);
		
		// ***update position***:
		// r_(n+1) = r_(n) + v(n+1) * dt
//		Vector3 nextPos = UpdateMethods.updatePosEC(currentPos, nextVel, 0.05);
		
//		System.out.println(currentVel);
		Vector3[] nextPosVel = UpdateMethods.updateManyTimesRK4(this);
		Vector3 nextVel = new Vector3(nextPosVel[1]);
		Vector3 nextPos = new Vector3(nextPosVel[0]);
		
		
		// *****ASSIGNING NEXT VELOCITY TO ENTITY*****
		this.motionX = nextVel.getX();
		this.motionY = nextVel.getY();
		this.motionZ = nextVel.getZ();
		
/*		// detects if it hits a block?
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(currentPos.getVec3(), nextPos.getVec3());

		if (movingobjectposition != null)
		{		// if it hits a block, sets final position as hit coord.
			nextPos = new Vector3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}
		if (!this.worldObj.isRemote)
		{
			Entity hitEntity = null;

			// get all entities in/near bounding box (apart from this one)
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
		
		if (movingobjectposition != null)
		{
			this.onImpact(movingobjectposition);
		}	//does onImpact on hitEntity
		
		*/
		
		// New RayTrace system
		RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, this.ticksInAir >= 25, this.getThrower());

        if (raytraceresult != null)
        {
            this.onImpact(raytraceresult);
        }
		
		// *****ASSIGNING NEXT POSITION TO ENTITY*****
		this.posX = nextPos.getX();
		this.posY = nextPos.getY();
		this.posZ = nextPos.getZ();
        
        this.ticksInAir++;

		if (this.posY <= -20 || this.posY >= 400 || this.ticksInAir >= lifetime)
		{
			this.setDead();
		}	// kills entity if outside world or existed for >10s
	}
	
	public Vector3 getAcc()
	{
		Vector3 currentPos = new Vector3(this.posX, this.posY, this.posZ);
		Vector3 currentVel = new Vector3(this.motionX, this.motionY, this.motionZ);
		Vector3 acc = EMField.lorentzForce(currentPos, currentVel, charge);
		acc.scaleBy(1/mass);
		return acc;
	}
	
	public Vector3 getAcc(Vector3 pos,Vector3 vel)
	{
		Vector3 acc = EMField.lorentzForce(pos, vel, charge);
		acc.scaleBy(1/mass);
		return acc;
	}

	@Override
	protected void onImpact(RayTraceResult pos) 
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
	
	public void normaliseMotion()
	{
		Vector3 motion = new Vector3(this.motionX, this.motionY, this.motionZ);
		Vector3 motionNorm = motion.getUnitVector();
		this.motionX = motionNorm.getX();
		this.motionY = motionNorm.getY();
		this.motionZ = motionNorm.getZ();
	}
			
}
