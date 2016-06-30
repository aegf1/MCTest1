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
 * 
 * @author Joseph Brownless
 */
public class EntityRelChargedParticle extends EntityChargedParticle
{
	// NOTE that motionX etc are per TICK (1/20 sec). So, velocity is 20*(motionX,motionY,motionZ)
	
	protected Vector3 momentum;
	protected static final DataParameter<Float> XMOM = EntityDataManager.<Float>createKey(EntityRelChargedParticle.class, DataSerializers.FLOAT);
	protected static final DataParameter<Float> YMOM = EntityDataManager.<Float>createKey(EntityRelChargedParticle.class, DataSerializers.FLOAT);
	protected static final DataParameter<Float> ZMOM = EntityDataManager.<Float>createKey(EntityRelChargedParticle.class, DataSerializers.FLOAT);

	/**
	 * Creates particle with undefined position and motion
	 * @param world
	 * @param mIn mass
	 * @param qIn charge
	 * @param speedIn speed (per tick)
	 */
	public EntityRelChargedParticle(World world, float mIn, float qIn, float speedIn)
	{
		super(world, mIn, qIn, speedIn);	// Speed is per TICK
		initMom();
	}

	/**
	 * Creates particle, as thrown by a player (or similar)
	 * @param world 
	 * @param player thrower
	 * @param mIn mass
	 * @param qIn charge
	 * @param speedIn speed (per tick)
	 */
	public EntityRelChargedParticle(World world, EntityLivingBase player, float mIn, float qIn, float speedIn)
	{
		super(world, player, mIn, qIn, speedIn);	// Speed is per TICK
		initMom();
		LogHelper.info("Throwing particle with charge of "+qIn);
	}

	/**
	 * Creates particle with defined position and velocity
	 * @param world
	 * @param pos position
	 * @param vel velocity (per second)
	 * @param mIn mass
	 * @param qIn charge
	 */
	public EntityRelChargedParticle(World world, Vector3 posIn, Vector3 velIn, float mIn, float qIn)
	{
		super(world, posIn, velIn, mIn, qIn);	// velocity is per second
		initMom();
	}

	/**
	 * Called at start of all constructors. Sets up datamanager entries
	 */
	protected void entityInit()
	{
		super.entityInit();
 
	    this.dataManager.register(XMOM, Float.valueOf(0f));
	    this.dataManager.register(YMOM, Float.valueOf(0f));
	    this.dataManager.register(ZMOM, Float.valueOf(0f));
	    
	}
	
	/**
	 * Called at end of all constructors. Sets up relativistic properties of entity.
	 */
	private void initMom()
	{
		momentum = new Vector3();
		Vector3 vel = Vector3.scale(new Vector3(this.motionX,this.motionY,this.motionZ), 20D);
		double gam = 1.0 / Math.sqrt(1 - Math.pow((vel.magnitude()/Reference.lightSpeed) , 2));	
		setMomentum(Vector3.scale(vel, gam*mass));
	}
	
	/**
	 * Updates entity position
	 */
	@Override
	public void onUpdate()
	{		
		position = new Vector3(getPositionVec3());	//current position
		momentum = new Vector3(getMomentumVec3());	//current momentum
		
		// Recording previous position
		this.prevPosX = position.getX();
		this.prevPosY = position.getY();
		this.prevPosZ = position.getZ();
		
		// Calculate next position + velocity
		Vector3[] nextPosMom = updateManyTimesRK4(position, momentum);
		Vector3 nextPos = new Vector3(nextPosMom[0]);
		Vector3 nextMom = new Vector3(nextPosMom[1]);
		
		// *****ASSIGNING NEXT VELOCITY TO ENTITY*****
		Vector3 nextVel = calcVelocity(nextMom);
		this.motionX = nextVel.getX()/20D;
		this.motionY = nextVel.getY()/20D;
		this.motionZ = nextVel.getZ()/20D;
		
/*		***Old Hit Detection***
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
		
		if (movingobjectposition != null)
		{
			this.onImpact(movingobjectposition);
		}	//does onImpact on hitEntity
		
		*/
		
		// New RayTrace system
		RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, this.ticksInAir >= 25, this.getThrower());
		if (raytraceresult != null && !this.worldObj.isRemote)
		{
//			LogHelper.info("Hit something!");
			this.onImpact(raytraceresult);
		}
		
		// *****ASSIGNING NEXT POSITION + MOMENTUM TO ENTITY*****
		setPosition(nextPos);
		setMomentum(nextMom);
			
		// Check if entity should be dead
        this.ticksInAir++;

		if (this.posY <= -20 || this.posY >= 400 || this.ticksInAir >= lifetime)
		{
			this.setDead();
		}	
		
		// Output data
		if (!this.worldObj.isRemote)
		{
			OutputHelper.record4(nextPos, nextMom, EMField.getEField(nextPos), EMField.getBField(nextPos));
		}
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
		return Math.sqrt(1.0 + Math.pow(momMag/(this.mass * Reference.lightSpeed), 2));
	}
	
	/**
	 * Calculates the velocity from the momentum and mass
	 * @param mom the momentum to be used
	 * @return the velocity
	 */
	public Vector3 calcVelocity(Vector3 mIn)
	{
		Vector3 mom = new Vector3(mIn);
		//	v = p / (m*gamma)
		return Vector3.scale( mom , 1.0 / (this.mass * calcGamma(mom)) );
	}
	
	/**
	 * Calculates the force in this particle at a given position with a given momentum
	 * Converts momentum to velocity, then uses @link {@link EntityChargedParticle#calcForce(Vector3, Vector3)}
	 * (Note: Does not use the entity's locally stored pos/mom, so that complex numerical methods can be used)
	 * @param pos Entity position
	 * @param mom Entity momentum
	 * @return the force
	 */
	public Vector3 calcForceMom(Vector3 pos, Vector3 mom)
	{
		return calcForce(pos, calcVelocity(mom));
	}
	
	/**
	 * Uses the RK4 method to calculate position and momentum of this particle after given timestep dt, with given initial pos + mom
	 * @param pos initial position
	 * @param mom initial momentum
	 * @param dt timestep
	 * @return array of vector3, with 1st entry giving final pos and 2nd giving final mom
	 */
	private Vector3[] updateRK4(Vector3 pos, Vector3 mom, double dt)
	{
		Vector3 startPos = new Vector3(pos);
		Vector3 startMom = new Vector3(mom);
		
		Vector3[] Xn = new Vector3[]{startPos, startMom};
		
		Vector3[] k1 = new Vector3[]
			{
				calcVelocity(startMom),
				calcForceMom(startPos, startMom)
			};
		k1[0].scaleBy(dt); k1[1].scaleBy(dt);
		
		Vector3[] k2 = new Vector3[]
			{
				calcVelocity(Vector3.add(startMom, Vector3.scale(k1[1], 0.5))),
				calcForceMom(Vector3.add(startPos, Vector3.scale(k1[0], 0.5)), Vector3.add(startMom, Vector3.scale(k1[1], 0.5)))
			};
		k2[0].scaleBy(dt); k2[1].scaleBy(dt);
		
		Vector3[] k3 = new Vector3[]
			{
				calcVelocity(Vector3.add(startMom, Vector3.scale(k2[1], 0.5))),
				calcForceMom(Vector3.add(startPos, Vector3.scale(k2[0], 0.5)), Vector3.add(startMom, Vector3.scale(k2[1], 0.5)))
			};
		k3[0].scaleBy(dt); k3[1].scaleBy(dt);
			
		Vector3[] k4 = new Vector3[]
			{
				calcVelocity(Vector3.add(startMom, k3[1])),
				calcForceMom(Vector3.add(startPos, k3[0]), Vector3.add(startMom, k3[1]))
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
	
	/**
	 * Calculates final pos and mom after applying RK4 many times to a particle.
	 * Total timestep = 1 tick (0.05sec), with {@link Reference#ITERATIONS_PER_TICK} giving num of RK4 iterations performed
	 * @param pos initial position
	 * @param mom initial momentum
	 * @return array of 2 vector3s, with 1st entry giving final pos and 2nd giving final mom
	 */
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
	
	/**
	 * Sets momentum of the entity, locally and using the datamanager
	 * @param x x component of mom
	 * @param y y component of mom
	 * @param z z component of mom
	 */
	public void setMomentum(double x, double y, double z)
	{
		
		this.momentum.setX(x); 
		this.momentum.setY(y); 
		this.momentum.setZ(z);
		if(!this.worldObj.isRemote)
		{
			this.dataManager.set(XMOM, Float.valueOf((float) x));
			this.dataManager.set(YMOM, Float.valueOf((float) y));
			this.dataManager.set(ZMOM, Float.valueOf((float) z));
			
//			LogHelper.info("writing momentum to datawatcher");
//			LogHelper.info("entity momentum = "+momentum.toString());
		}
	}
	
	/**
	 * Sets momentum of the entity, locally and using the datamanager
	 * @param mom new momentum
	 */
	public void setMomentum(Vector3 mom)
	{
		setMomentum(mom.getX(), mom.getY(), mom.getZ());
	}
	
	/**
	 * gets entity momentum, from datamanager
	 * @return the momentum
	 */
	public Vector3 getMomentumVec3()
	{
		return new Vector3(
				((Float) this.dataManager.get(XMOM)).doubleValue(),
				((Float) this.dataManager.get(YMOM)).doubleValue(),
				((Float) this.dataManager.get(ZMOM)).doubleValue()
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
