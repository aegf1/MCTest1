package com.josephb.test1.utility.physics;

import com.josephb.test1.entity.EntityChargedParticle;
import com.josephb.test1.reference.Reference;

public class UpdateMethods 
{
	public static Vector3 updatePosEC(Vector3 pos, Vector3 vel, double dt)
	{
		return Vector3.add(pos, Vector3.scale(vel, dt));	// x(n+1) = x(n) + v*t
	}

	public static Vector3 updateVelEC(Vector3 vel, Vector3 acc, double dt)
	{
		return Vector3.add(vel, Vector3.scale(acc, dt));	// v(n+1) = v(n) + a*t
	}
	
	public static Vector3[] updateManyTimes(EntityChargedParticle particle)
	{
		Vector3 thisPos = new Vector3(particle.posX, particle.posY, particle.posZ);
		Vector3 thisVel = new Vector3(particle.motionX, particle.motionY, particle.motionZ);
		
		Vector3 prevPos;
		Vector3 prevVel;
		
		double dt = 1.0D/(20.0D*Reference.ITERATIONS_PER_TICK);
		
		for(int i=0; i<Reference.ITERATIONS_PER_TICK;i++)
		{
			prevPos = new Vector3(thisPos);
			prevVel = new Vector3(thisVel);
			
			thisVel = UpdateMethods.updateVelEC(prevVel, particle.getAcc(), dt);
			thisPos = UpdateMethods.updatePosEC(prevPos, thisVel, dt);
		}
		return new Vector3[]{thisPos,thisVel};
	}
}
