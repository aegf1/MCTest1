package com.josephb.test1.utility.physics;

import com.josephb.test1.Test1;
import com.josephb.test1.block.BlockMagnet;
import com.josephb.test1.reference.Reference;
import com.josephb.test1.utility.LogHelper;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

public class EMField 
{
	private static Vector3 BDir = new Vector3(0,0,0);
	private static double BStrength = 2D;
	private static Vector3 EDir = new Vector3(1,0,0);
	private static double EStrength = 0D;
	
	public static Vector3 getEField(Vector3 position)
	{
		return getTotalEFiedPointCharges(position);
	}
	
	public static Vector3 getTotalEFiedPointCharges(Vector3 pos)
	{
		Vector3 e = new Vector3(0,0,0);
		int[][] posCharges = Test1.posChargeTracker.get2DArray();
		if (posCharges.length>=1) 
		{
//			System.out.println(magnets.length);
//			System.out.println(magnets[0].length);
			for (int i = 0; i < posCharges.length; i++) 
			{
				Vector3 thisChargePos = new Vector3(posCharges[i][0]+0.5, posCharges[i][1]+0.5, posCharges[i][2]+0.5);
				e.increaseBy(getEFieldPointCharge(pos, thisChargePos, 1D));
			} 
		}
		
		int[][] negCharges = Test1.negChargeTracker.get2DArray();
		if (negCharges.length>=1) 
		{
//			System.out.println(magnets.length);
//			System.out.println(magnets[0].length);
			for (int i = 0; i < negCharges.length; i++) 
			{
				Vector3 thisChargePos = new Vector3(negCharges[i][0]+0.5, negCharges[i][1]+0.5, negCharges[i][2]+0.5);
				e.increaseBy(getEFieldPointCharge(pos, thisChargePos, -1D));
			} 
		}
		
		return e;
	}
	
	public static Vector3 getEFieldPointCharge(Vector3 pos, Vector3 chargePos, double charge)
	{
		// E = const*q/((r-R)^3) * (r-R)
		Vector3 relPos = Vector3.subtract(pos, chargePos);			// r-R
		double number = Reference.ELECTRIC_FIELD_CONSTANT * charge/Math.pow(relPos.magnitude(), 3);	// const * q/((r-R)^3)
		return Vector3.scale(relPos, number);
	}
	
	public static Vector3 getBField(Vector3 position)
	{
		return getTotalMagDipoleField(position);
		
		//Just constant for now. ToDo: make this method get the field from all nearby magnet blocks
	}
	
	/**
	 *  Calculates magnetic field at a point 'pos' due to a dipole with 'dipoleVector' at 'magnetPos'
	 * @param pos Position to calculate field at
	 * @param magnetPos position of magnetic dipole
	 * @param dipoleVector dipole vector of magnet
	 * @return magnetic field due to dipole at that point
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
		
		double d2 = -1.0/(Math.pow(relPos.magnitude(), 5));	// d2 = -1/(|r|^3)
		Vector3 v2 = Vector3.scale(dipoleVector, d2);		// v2 = -d/(|r|^3)
		
		v1.increaseBy(v2);									// v1 = 3(d.r)*r/(|r|^5) - d/(|r|^3)
		v1.scaleBy(Reference.MAGNETIC_DIPOLE_CONSTANT);
		return v1;
	}
	
	public static Vector3 getTotalMagDipoleField(Vector3 pos)
	{
		Vector3 b = new Vector3(0,0,0);
		int[][] magnets = Test1.magnetTracker.get2DArray();
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
//		LogHelper.info(Vector3.crossProduct(vel, B).toString());
		force.scaleBy(charge);							// F = q(E + v X B)
		return force;
	}
}
