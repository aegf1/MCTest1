package com.josephb.test1.entity;

import java.util.Iterator;
import java.util.List;

import com.josephb.test1.Test1;
import com.josephb.test1.reference.Reference;
import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.dataoutput.OutputHelper;
import com.josephb.test1.utility.physics.EMField;
import com.josephb.test1.utility.physics.Vector3;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.Mod;

public class EntityRelChargedParticle extends EntityChargedParticle
{
	protected double gamma;
	protected Vector3 position = new Vector3();
	protected Vector3 momentum = new Vector3();
	protected boolean dataWatcherInitialised= false;

	public EntityRelChargedParticle(World world, float mIn, float qIn, float speedIn)
	{
		super(world, mIn, qIn, speedIn);
		Vector3 vel = new Vector3(this.motionX,this.motionY,this.motionZ);
		double gam = 1.0 / Math.sqrt(1 - Math.pow((vel.magnitude()/Reference.SPEED_OF_LIGHT) , 2));	//gamma = 1/sqrt(1-(v/c)^2)
		setMomentum(Vector3.scale(vel, gam*mass));				// p = gamma*m*v
	}

	public EntityRelChargedParticle(World world, EntityLivingBase player, float mIn, float qIn, float speedIn)
	{
		super(world, player, mIn, qIn, speedIn);
		Vector3 vel = new Vector3(this.motionX,this.motionY,this.motionZ);
		double gam = 1.0 / Math.sqrt(1 - Math.pow((vel.magnitude()/Reference.SPEED_OF_LIGHT) , 2));	//gamma = 1/sqrt(1-(v/c)^2)
		setMomentum(Vector3.scale(vel, gam*mass));				// p = gamma*m*v
		this.setPosition(new Vector3(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ));
	}

	public EntityRelChargedParticle(World world, Vector3 posIn, Vector3 velIn, float mIn, float qIn)
	{
		super(world, posIn, velIn, mIn, qIn);
		Vector3 vel = new Vector3(this.motionX,this.motionY,this.motionZ);
		double gam = 1.0 / Math.sqrt(1 - Math.pow((vel.magnitude()/Reference.SPEED_OF_LIGHT) , 2));	//gamma = 1/sqrt(1-(v/c)^2)
		setMomentum(Vector3.scale(vel, gam*mass));				// p = gamma*m*v
		this.setPosition(posIn);
	}

	protected void entityInit()
	{
		super.entityInit();
		if(!dataWatcherInitialised)
		{
			DataWatcher dw = this.getDataWatcher();
			dw.addObject(10, 0f);	// x pos
			dw.addObject(11, 0f);	// y pos
			dw.addObject(12, 0f);	// z pos
			dw.addObject(13, 0f);	// x mom
			dw.addObject(14, 0f);	// y mom
			dw.addObject(15, 0f);	// z mom	
			dataWatcherInitialised = true;
		}
	}
	
	@Override
	public void onUpdate()
	{		
//		System.out.println("updating");
		
//		LogHelper.warn(entityUniqueID.toString());
//		System.out.println("entity position ="+this.posX+", "+this.posY+", "+this.posZ);
//      System.out.println("entity motion ="+this.motionX+", "+this.motionY+", "+this.motionZ);
		
		position = new Vector3(getPositionVec3());	//current position
		momentum = new Vector3(getMomentumVec3());	//current momentum
		
		// Recording previous position
		this.prevPosX = position.getX();
		this.prevPosY = position.getY();
		this.prevPosZ = position.getZ();
		
		Vector3[] nextPosMom = updateManyTimesRK4(position, momentum);
		
		Vector3 nextPos = new Vector3(nextPosMom[0]);
		Vector3 nextMom = new Vector3(nextPosMom[1]);
		
		Vector3 nextVel = calcVelocity(nextMom);
		
		
		// *****ASSIGNING NEXT VELOCITY TO ENTITY*****
		this.motionX = nextVel.getX();
		this.motionY = nextVel.getY();
		this.motionZ = nextVel.getZ();
		
		// detects if it hits a block?
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(position.getVec3(), nextPos.getVec3());

		if (movingobjectposition != null)
		{		// if it hits a block, sets final position as hit coord.
			nextPos = new Vector3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}
		if (!this.worldObj.isRemote)
		{
			Entity hitEntity = null;

			// get all entities in/near bounding box (apart from this one)
			AxisAlignedBB AABB = this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D);
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
					final MovingObjectPosition movingobjectposition1 = hitBB.calculateIntercept(position.getVec3(), nextPos.getVec3());

					if (movingobjectposition1 != null)
					{
						final double distToHitVec = position.getVec3().distanceTo(movingobjectposition1.hitVec);

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
		
		// *****ASSIGNING NEXT POSITION + MOMENTUM TO ENTITY*****
		setPosition(nextPos);
		setMomentum(nextMom);
		
		if (!this.worldObj.isRemote)
		{
			OutputHelper.record(nextPos);
		}
			
        this.ticksInAir++;

		if (movingobjectposition != null)
		{
			this.onImpact(movingobjectposition);
		}	//does onImpact on hitEntity

		if (this.posY <= -20 || this.posY >= 400 || this.ticksInAir >= lifetime)
		{
			this.setDead();
		}	// kills entity if outside world or existed for >10s
	}
	
	/**
	 * Calculates the lorentz factor from the mass and momentum
	 * @param mom the momentum to be used
	 * @return the lorentz factor
	 */
	public double calcGamma(Vector3 mom)
	{
		double momMag = mom.magnitude();
		// sqrt(1+(p/mc)^2)
		return Math.sqrt(1.0 + Math.pow(momMag/(this.mass * Reference.SPEED_OF_LIGHT), 2));
	}
	
	/**
	 * Calculates the velocity from the momentum and mass
	 * @param mom the momentum to be used
	 * @return the velocity
	 */
	public Vector3 calcVelocity(Vector3 mom)
	{
		Vector3 m = new Vector3(mom);
		//	v = p / (m*gamma)
		return Vector3.scale( m , 1.0 / (this.mass * calcGamma(m)) );
	}
	
	public Vector3 calcForce(Vector3 pos, Vector3 mom)
	{
		return EMField.lorentzForce(pos, calcVelocity(mom), this.charge);
	}
	
	private Vector3[] updateRK4(Vector3 pos, Vector3 mom, double dt)
	{
		Vector3 startPos = new Vector3(pos);
		Vector3 startMom = new Vector3(mom);
		
		Vector3[] Xn = new Vector3[]{startPos, startMom};
		
		Vector3[] k1 = new Vector3[]
			{
				calcVelocity(startMom),
				calcForce(startPos, startMom)
			};
		k1[0].scaleBy(dt); k1[1].scaleBy(dt);
		
		Vector3[] k2 = new Vector3[]
			{
				calcVelocity(Vector3.add(startMom, Vector3.scale(k1[1], 0.5))),
				calcForce(Vector3.add(startPos, Vector3.scale(k1[0], 0.5)), Vector3.add(startMom, Vector3.scale(k1[1], 0.5)))
			};
		k2[0].scaleBy(dt); k2[1].scaleBy(dt);
		
		Vector3[] k3 = new Vector3[]
			{
				calcVelocity(Vector3.add(startMom, Vector3.scale(k2[1], 0.5))),
				calcForce(Vector3.add(startPos, Vector3.scale(k2[0], 0.5)), Vector3.add(startMom, Vector3.scale(k2[1], 0.5)))
			};
		k3[0].scaleBy(dt); k3[1].scaleBy(dt);
			
		Vector3[] k4 = new Vector3[]
			{
				calcVelocity(Vector3.add(startMom, k3[1])),
				calcForce(Vector3.add(startPos, k3[0]), Vector3.add(startMom, k3[1]))
			};
		k4[0].scaleBy(dt); k4[1].scaleBy(dt);
		
		Vector3 rChange1 = Vector3.add(k1[0], Vector3.scale(k2[0], 2.0));
		Vector3 rChange2 = Vector3.add(k4[0], Vector3.scale(k3[0], 2.0));
		Vector3 rChange = Vector3.add(rChange1, rChange2);
		rChange.scaleBy(1.0/6.0D);
		
		Vector3 pChange1 = Vector3.add(k1[1], Vector3.scale(k2[1], 2.0));
		Vector3 pChange2 = Vector3.add(k4[1], Vector3.scale(k3[1], 2.0));
		Vector3 pChange = Vector3.add(pChange1, pChange2);
		pChange.scaleBy(1.0/6.0D);
		
		return new Vector3[]
			{
				Vector3.add(startPos, rChange),
				Vector3.add(startMom, pChange)
		};
	}
	
	public Vector3[] updateManyTimesRK4(Vector3 pos, Vector3 mom)
	{
		Vector3 thisPos = new Vector3(pos);
		Vector3 thisMom = new Vector3(mom);

		Vector3[] thisRP = new Vector3[2];
		
		Vector3 prevPos;
		Vector3 prevMom;

		double dt = 1.0D/(20.0D*Reference.ITERATIONS_PER_TICK);

		for(int i=0; i<Reference.ITERATIONS_PER_TICK;i++)
		{
			prevPos = new Vector3(thisPos);
			prevMom = new Vector3(thisMom);
			thisRP = this.updateRK4(prevPos, prevMom, dt);
			thisPos = new Vector3(thisRP[0]); thisMom = new Vector3(thisRP[1]);
			
		}
		return new Vector3[]{thisPos,thisMom};
	}
	
	public void setPosition(Vector3 pos)
	{
		setPosition(pos.getX(), pos.getY(), pos.getZ());
		if(!this.worldObj.isRemote)
		{
			this.dataWatcher.updateObject(10, (float) pos.getX()); 		// x pos
			this.dataWatcher.updateObject(11, (float) pos.getY()); 		// y pos
			this.dataWatcher.updateObject(12, (float) pos.getZ()); 		// z pos

//			LogHelper.info("writing position to datawatcher");
//			LogHelper.info("entity position = "+pos.toString());
		}
		position = new Vector3(pos.getX(), pos.getY(), pos.getZ());

	}
	
	public Vector3 getPositionVec3()
	{
		return new Vector3(
				(double) this.dataWatcher.getWatchableObjectFloat(10),
				(double) this.dataWatcher.getWatchableObjectFloat(11),
				(double) this.dataWatcher.getWatchableObjectFloat(12)
		);
	}
	
	public void setMomentum(double x, double y, double z)
	{
		this.momentum.setX(x); 
		this.momentum.setY(y); 
		this.momentum.setZ(z);
		if(!this.worldObj.isRemote)
		{
			this.dataWatcher.updateObject(13, (float) x); 		// x mom
			this.dataWatcher.updateObject(14, (float) y); 		// y mom
			this.dataWatcher.updateObject(15, (float) z); 		// z mom
			
//			LogHelper.info("writing momentum to datawatcher");
//			LogHelper.info("entity momentum = "+momentum.toString());
		}
	}
	
	public void setMomentum(Vector3 mom)
	{
		setMomentum(mom.getX(), mom.getY(), mom.getZ());
	}
	
	public Vector3 getMomentumVec3()
	{
		return new Vector3(
				(double) this.dataWatcher.getWatchableObjectFloat(13),
				(double) this.dataWatcher.getWatchableObjectFloat(14),
				(double) this.dataWatcher.getWatchableObjectFloat(15)
			);
	}
	
/*	@Mod.EventHandler
	public void handleConstruction(EntityConstructing event){
	    if(event.entity instanceof EntityRelChargedParticle){
	    	DataWatcher dw = this.getDataWatcher();
			dw.addObject(26, 0f);	// x pos
			dw.addObject(27, 0f);	// y pos
			dw.addObject(28, 0f);	// z pos
			dw.addObject(29, 0f);	// x mom
			dw.addObject(30, 0f);	// y mom
			dw.addObject(31, 0f);	// z mom
	    }
	}		*/
}
