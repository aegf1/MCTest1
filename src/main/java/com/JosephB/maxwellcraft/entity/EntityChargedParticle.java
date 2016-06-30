package com.JosephB.maxwellcraft.entity;

import java.util.Iterator;
import java.util.List;

import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;
import com.JosephB.maxwellcraft.utility.physics.EMField;
import com.JosephB.maxwellcraft.utility.physics.UpdateMethods;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * Represents any thrown charged particle.
 * Some methods in this class are always overwritten by {@link EntityRelChargedParticle}, so this class may be merged into that one in the future
 * @author Joseph Brownless
 *
 */
public class EntityChargedParticle extends EntityThrowable
{
	protected float explosionRadius = Reference.particleExplosionRad;
	protected float charge;
	protected float mass;
	protected int lifetime = Reference.thrownParticleLifetime; 		// lifetime in ticks

	protected Vector3 position = new Vector3();
	protected static final DataParameter<Float> XPOS = EntityDataManager.<Float>createKey(EntityRelChargedParticle.class, DataSerializers.FLOAT);
	protected static final DataParameter<Float> YPOS = EntityDataManager.<Float>createKey(EntityRelChargedParticle.class, DataSerializers.FLOAT);
	protected static final DataParameter<Float> ZPOS = EntityDataManager.<Float>createKey(EntityRelChargedParticle.class, DataSerializers.FLOAT);
	
	
	protected double ticksInAir;

	/**
	 * Creates particle with undefined position and motion
	 * @param world
	 * @param mIn mass
	 * @param qIn charge
	 * @param speedIn speed (per tick)
	 */
	public EntityChargedParticle(World world, float mIn, float qIn, float speedIn)
	{
		super(world);
		normaliseMotion();
		if(speedIn >= Reference.lightSpeed)
		{
			LogHelper.warn("Trying to throw particle faster than light! Reducing speed");
			speedIn = (float) (Reference.lightSpeed*0.99);
		}
		this.motionX*=speedIn;
		this.motionY*=speedIn;
		this.motionZ*=speedIn;
		ticksInAir = 0;
		setMass(mIn);
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
		super(world, player);

		this.setPosition(new Vector3(player.posX, player.posY + (double)player.getEyeHeight() - 0.1D, player.posZ));
		if(speedIn >= Reference.lightSpeed)
		{
			LogHelper.warn("Trying to throw particle faster than light! Reducing speed");
			speedIn = (float) (Reference.lightSpeed*0.99);
		}
		this.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, (float) speedIn, 0.0F);
		
		ticksInAir = 0;
		setMass(mIn);
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
	public EntityChargedParticle(World world, Vector3 pos, Vector3 vel, float mIn, float qIn)
	{
		super(world, pos.getX(), pos.getY(), pos.getZ());
		this.setPosition(pos);
		normaliseMotion();
		if(vel.magnitude() >= Reference.lightSpeed*0.9999)
		{
			LogHelper.warn("Trying to throw particle faster than light! Reducing speed");
			vel = Vector3.scale(vel.getUnitVector(), Reference.lightSpeed*0.99);
		}
		this.motionX =vel.getX()/20D;
		this.motionY =vel.getY()/20D;
		this.motionZ =vel.getZ()/20D;
		ticksInAir = 0;
		setMass(mIn);
		setCharge(qIn);
	}
	
	/**
	 * Called at start of all constructors. Sets up datamanager entries
	 */
	protected void entityInit()
	{
        this.dataManager.register(XPOS, Float.valueOf((float) this.posX));
        this.dataManager.register(YPOS, Float.valueOf((float) this.posY));
        this.dataManager.register(ZPOS, Float.valueOf((float) this.posZ));
	}

	/**
	 * Not currently used. See {@link EntityRelChargedParticle#onUpdate}
	 */
	@Override
	public void onUpdate()
	{		
		
		// Recording previous positions
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		// current position and velocity
		Vector3 currentPos = new Vector3(this.posX, this.posY, this.posZ);
		Vector3 currentVel = new Vector3(this.motionX, this.motionY, this.motionZ);
		
		// new pos + vel
		Vector3[] nextPosVel = UpdateMethods.updateManyTimesRK4(this);
		Vector3 nextVel = new Vector3(nextPosVel[1]);
		Vector3 nextPos = new Vector3(nextPosVel[0]);
		
		
		// *****ASSIGNING NEXT VELOCITY TO ENTITY*****
		this.motionX = nextVel.getX();
		this.motionY = nextVel.getY();
		this.motionZ = nextVel.getZ();
		
/*		***Old Hit Detection***
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
	
	/**
	 * Calculates the force on this entity, with a given position and velocity
	 * (Note: Does not use the entity's locally stored pos/vel, so that complex numerical methods can be used)
	 * @param pos particle position
	 * @param vel particle velocity
	 * @return the force
	 */
	public Vector3 calcForce(Vector3 pos, Vector3 vel)
	{
		return EMField.lorentzForce(pos, vel, this.charge);
	}
	
	/**
	 * Calculates the acceleration currently experienced by this entity, with its current position and velocity.
	 * No longer used, due to relativistic equations of motion relying instead on force
	 * @return the acceleration
	 */
	@Deprecated
	public Vector3 getAcc()
	{
		Vector3 currentPos = new Vector3(this.posX, this.posY, this.posZ);
		Vector3 currentVel = Vector3.scale(new Vector3(this.motionX, this.motionY, this.motionZ), 20D);
		Vector3 acc = calcForce(currentPos, currentVel);
		acc.scaleBy(1/mass);
		return acc;
	}
	
	/**
	 * Calculates acceleration on this entity at given position and velocity.
	 * No longer used, due to relativistic equations of motion relying instead on force
	 * @param pos entity position
	 * @param vel entity momentum
	 * @return the acceleration
	 */
	@Deprecated
	public Vector3 getAcc(Vector3 pos,Vector3 vel)
	{
		Vector3 acc = calcForce(pos, vel);
		acc.scaleBy(1/mass);
		return acc;
	}

	/**
	 * Called when entity collides with an entity or block.
	 * Makes boom.
	 */
	@Override
	protected void onImpact(RayTraceResult pos) 
	{
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
				(float)this.explosionRadius, false);
		this.setDead();
	}

	/**
	 * Gravity doesn't exist. Wake up, sheeple.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0F;
	}

	/**
	 * 
	 * @return ticks entity has existed for
	 */
	public double getTicksInAir() {
		return ticksInAir;
	}

	/**
	 * Sets ticks particle has existed for
	 * @param ticksInAir 
	 */
	public void setTicksInAir(double ticksInAir) {
		this.ticksInAir = ticksInAir;
	}

	/**
	 * 
	 * @return Mass
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * Set mass
	 * @param mass
	 */
	public void setMass(float mass) {
		this.mass = mass;
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
	
	/**
	 * Sets position of entity, locally and using datamanager.
	 * @param pos New position
	 */
	public void setPosition(Vector3 pos)
	{
		setPosition(pos.getX(), pos.getY(), pos.getZ());
		if(!this.worldObj.isRemote)
		{
			this.dataManager.set(XPOS, Float.valueOf((float) pos.getX()));
			this.dataManager.set(YPOS, Float.valueOf((float) pos.getY()));
			this.dataManager.set(ZPOS, Float.valueOf((float) pos.getZ()));
		}
		position = new Vector3(pos.getX(), pos.getY(), pos.getZ());

	}
	
	/**
	 * Get current entity position from datamanager
	 * @return Entity position
	 */
	public Vector3 getPositionVec3()
	{
		return new Vector3(
				((Float) this.dataManager.get(XPOS)).doubleValue(),
				((Float) this.dataManager.get(YPOS)).doubleValue(),
				((Float) this.dataManager.get(ZPOS)).doubleValue()
		);
	}
	
	/**
	 * Normalises motion of particle, to have a magnitude of 1 (PER TICK!!)
	 */
	public void normaliseMotion()
	{
		Vector3 motion = new Vector3(this.motionX, this.motionY, this.motionZ);
		Vector3 motionNorm = motion.getUnitVector();
		this.motionX = motionNorm.getX();
		this.motionY = motionNorm.getY();
		this.motionZ = motionNorm.getZ();
	}
			
}
