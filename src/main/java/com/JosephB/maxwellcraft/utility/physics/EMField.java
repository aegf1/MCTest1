package com.JosephB.maxwellcraft.utility.physics;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.reference.Reference;
import com.JosephB.maxwellcraft.utility.LogHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

/**
 * Contains methods relavent to electric and magnetic fields
 * @author Joseph Brownless
 */
public class EMField 
{
	/**
	 * Get electric field at a point
	 * @param position Point
	 * @return Electric field
	 */
	public static Vector3 getEField(Vector3 position)
	{
		return getTotalEFiedPointCharges(position);
	}
	
	/**
	 * Calculate total electric field at a point, due to all tracked posCharge and negCharge blocks
	 * @param pos Point
	 * @return Electric field
	 */
	public static Vector3 getTotalEFiedPointCharges(Vector3 pos)
	{
		Vector3 e = new Vector3(0,0,0);
		int[][] charges = MaxwellCraft.chargeTracker.get2DArray();
		if (charges.length>=1) 
		{
//			System.out.println(magnets.length);
//			System.out.println(magnets[0].length);
			for (int i = 0; i < charges.length; i++) 
			{
				Vector3 thisChargePos = new Vector3(charges[i][0]+0.5, charges[i][1]+0.5, charges[i][2]+0.5);
				int thisCharge = 0;
				if(charges[i][3] == 1){ thisCharge = 1;} else if(charges[i][3] == 0){ thisCharge = -1;}
				else {LogHelper.error("Invalid tracker entry");}
				e.increaseBy(getEFieldPointCharge(pos, thisChargePos, thisCharge));
			} 
		}
		
		return e;
	}
	
	/**
	 * Get electric field at a point due to a point charge
	 * @param pos Point for field to be calculated
	 * @param chargePos Position of point charge
	 * @param charge Point charge
	 * @return Electric field
	 */
	public static Vector3 getEFieldPointCharge(Vector3 pos, Vector3 chargePos, double charge)
	{
		// E = const*q/((r-R)^3) * (r-R)
		Vector3 relPos = Vector3.subtract(pos, chargePos);			// r-R
		double number = Reference.eFieldConst * charge/Math.pow(relPos.magnitude(), 3);	// const * q/((r-R)^3)
		return Vector3.scale(relPos, number);
	}
	
	/**
	 * Get magnetic field at a point
	 * @param position Point
	 * @return Magnetic field
	 */
	public static Vector3 getBField(Vector3 position)
	{
		return getTotalMagDipoleField(position);
		
		//Just constant for now. ToDo: make this method get the field from all nearby magnet blocks
	}
	
	/**
	 * Calculate total magnetic field at a point, due to all tracked magnet dipole blocks
	 * @param pos Point
	 * @return Magnetic field
	 */
	public static Vector3 getTotalMagDipoleField(Vector3 pos)
	{
		Vector3 b = new Vector3(0,0,0);
		int[][] magnets = MaxwellCraft.magnetTracker.get2DArray();
		if (magnets.length>=1) 
		{
//			System.out.println(magnets.length);
//			System.out.println(magnets[0].length);
			for (int i = 0; i < magnets.length; i++) 
			{
				Vector3 thisDir = new Vector3(((EnumFacing) BlockMagnet.getFacing(magnets[i][3])).getDirectionVec());
				Vector3 thisMagPos = new Vector3(magnets[i][0]+0.5, magnets[i][1]+0.5, magnets[i][2]+0.5);
				Vector3 thisB = magDipoleField(pos, thisMagPos, thisDir);
				b.increaseBy(thisB);
			} 
		}
		return b;
	}
	
	/**
	 *  Calculates magnetic field at a point due to a dipole
	 * @param pos Position to calculate field at
	 * @param magnetPos position of magnetic dipole
	 * @param dipoleVector dipole vector of magnet
	 * @return magnetic field
	 */
	public static Vector3 magDipoleField(Vector3 pos, Vector3 magnetPos, Vector3 dipoleVector)
	{
		// B = const*[1/(|r|^5) (3(d.r)r] - [d/(r^3)]
//		System.out.println("particle pos: "+pos);
//		System.out.println("magnet pos: "+magnetPos);
//		System.out.println("particle direction: "+dipoleVector);
		
		Vector3 relPos = Vector3.subtract(pos, magnetPos);
		
		double d1 = 1.0/(Math.pow(relPos.magnitude(), 5));
		d1 *= 3*Vector3.dotProduct(dipoleVector, relPos);	// d1 =  3(d.r)/(|r|^5)
		Vector3 v1 = Vector3.scale(relPos, d1);				// v1 =  3(d.r)*r/(|r|^5)
		
		double d2 = -1.0/(Math.pow(relPos.magnitude(), 3));	// d2 = -1/(|r|^3)
		Vector3 v2 = Vector3.scale(dipoleVector, d2);		// v2 = -d/(|r|^3)
		
		v1.increaseBy(v2);									// v1 = 3(d.r)*r/(|r|^5) - d/(|r|^3)
		v1.scaleBy(Reference.magDipFieldConst);
		return v1;
	}
	
	/**
	 * Calculates the total EM force on a charge at a position with a velocity using F=q(E+vXB)
	 * @param pos position
	 * @param vel velocity
	 * @param charge charge
	 */
	public static Vector3 lorentzForce(Vector3 pos, Vector3 vel, double charge)
	{
		Vector3 force = new Vector3(getEField(pos));					// F = E
		force.increaseBy(Vector3.crossProduct(vel, getBField(pos)));	// F = E + v X B
		force.scaleBy(charge);											// F = q(E + v X B)
		return force;
	}
	
	/**
	 * Calculates the total EM force on a charge at a position with a velocity using F=q(E+vXB)
	 * @param pos position
	 * @param vel velocity
	 * @param charge charge
	 * @param E electric field
	 * @param B magnetic field
	 */
	public static Vector3 lorentzForce(Vector3 pos, Vector3 vel, double charge, Vector3 E, Vector3 B)
	{
		Vector3 force = E;								// F = E
		force.increaseBy(Vector3.crossProduct(vel, B));	// F = E + v X B
		force.scaleBy(charge);							// F = q(E + v X B)
		return force;
	}
}
