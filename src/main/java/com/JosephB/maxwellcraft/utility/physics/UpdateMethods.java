package com.JosephB.maxwellcraft.utility.physics;

import com.JosephB.maxwellcraft.entity.EntityChargedParticle;
import com.JosephB.maxwellcraft.reference.Reference;

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

	public static Vector3[] updateManyTimesEC(EntityChargedParticle particle)
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

	public static Vector3[] updateRK4(EntityChargedParticle particle, Vector3 pos, Vector3 vel, double dt)
	{
		Vector3 startPos = new Vector3(pos);
		Vector3 startVel = new Vector3(vel);
		
		/** 
		 * X=(r,v)
		 * dX/dt = f(t,X)= f(X)
		 * f(X)=(v,a) 
		 * k1 = dt*f(X_n)
		 * k2 = dt*f(X_n+k1/2)
		 * k3 = dt*f(X_n+k2/2)
		 * k4 = dt*f(X_n+k3)
		 * X_n+1 =X_n + 1/6*(k1 + 2k2 + 2k3 + k4)
		 */
		
		Vector3[] Xn = new Vector3[]{startPos,startVel};			// Xn = (r_n,v_n)

		// k1 = dt*{ v_n , a(r_n,v_n) }
		Vector3[] k1 = new Vector3[]{
				new Vector3(startVel),
				particle.getAcc(startPos, startVel)
		};	
		k1[0].scaleBy(dt); k1[1].scaleBy(dt);

		// k2 = dt*{ v_n+k1v/2 , a(r_n+k1r/2,v_n+k1v/2) }
		Vector3[] k2 = new Vector3[]{
				Vector3.add(startVel, Vector3.scale(k1[1],0.5)),
				particle.getAcc(Vector3.add(startPos, Vector3.scale(k1[0],0.5)), Vector3.add(startVel, Vector3.scale(k1[1],0.5)))
		};
		k2[0].scaleBy(dt); k2[1].scaleBy(dt);

		// k3 = dt*{ v_n+k2v/2 , a(r_n+k2r/2,v_n+k2v/2) }
		Vector3[] k3 = new Vector3[]{
				Vector3.add(startVel, Vector3.scale(k2[1],0.5)),
				particle.getAcc(Vector3.add(startPos, Vector3.scale(k2[0],0.5)), Vector3.add(startVel, Vector3.scale(k2[1],0.5)))
		};
		k3[0].scaleBy(dt); k3[1].scaleBy(dt);

		// k4 = dt*{ v_n+k3v , a(r_n+k3r,v_n+k3v) }
		Vector3[] k4 = new Vector3[]{
				Vector3.add(startVel, k3[1]),
				particle.getAcc(Vector3.add(startPos, k3[0]), Vector3.add(startVel, k3[1]))
		};
		k4[0].scaleBy(dt); k4[1].scaleBy(dt);
		
		// X_n+1 =X_n + 1/6*(k1 + 2k2 + 2k3 + k4)
/*		Vector3 rChange = new Vector3(k1[0]); 
		rChange.increaseBy(Vector3.scale(k2[0],2.0D)); rChange.increaseBy(Vector3.scale(k3[0],2.0D)); rChange.increaseBy(k4[0]);
		rChange.scaleBy(1/6);
		Vector3 vChange = new Vector3(k1[1]); 
		vChange.increaseBy(Vector3.scale(k2[1],2.0D)); vChange.increaseBy(Vector3.scale(k3[1],2.0D)); vChange.increaseBy(k4[1]);
		vChange.scaleBy(1/6);
*/		
		
		Vector3 rChange1 = Vector3.add(k1[0], Vector3.scale(k2[0], 2.0));
		Vector3 rChange2 = Vector3.add(k4[0], Vector3.scale(k3[0], 2.0));
		Vector3 rChange = Vector3.add(rChange1, rChange2);
		rChange.scaleBy(1.0/6.0D);
		
		Vector3 vChange1 = Vector3.add(k1[1], Vector3.scale(k2[1], 2.0));
		Vector3 vChange2 = Vector3.add(k4[1], Vector3.scale(k3[1], 2.0));
		Vector3 vChange = Vector3.add(vChange1, vChange2);
		vChange.scaleBy(1.0/6.0D);
		
		return new Vector3[]{
				Vector3.add(startPos, rChange),
				Vector3.add(startVel, vChange)
		};
	}
	
	public static Vector3[] updateManyTimesRK4(EntityChargedParticle particle)
	{
		Vector3 thisPos = new Vector3(particle.posX, particle.posY, particle.posZ);
		Vector3 thisVel = new Vector3(particle.motionX, particle.motionY, particle.motionZ);

		Vector3[] thisRV = new Vector3[2];
		
		Vector3 prevPos;
		Vector3 prevVel;

		double dt = 1.0D/(20.0D*Reference.ITERATIONS_PER_TICK);

		for(int i=0; i<Reference.ITERATIONS_PER_TICK;i++)
		{
			prevPos = new Vector3(thisPos);
			prevVel = new Vector3(thisVel);
			thisRV = updateRK4(particle, prevPos, prevVel, dt);
			thisPos = new Vector3(thisRV[0]); thisVel = new Vector3(thisRV[1]);
			
		}
		return new Vector3[]{thisPos,thisVel};
	}
}
