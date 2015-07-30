package com.josephb.test1.utility.physics;

import com.josephb.test1.Test1;
import com.josephb.test1.utility.LogHelper;

public class EMField 
{
	private static Vector3 BDir = new Vector3(0,0,0);
	private static double BStrength = 2D;
	private static Vector3 EDir = new Vector3(1,0,0);
	private static double EStrength = 0D;
	
	public static Vector3 getBField(Vector3 position)
	{
		Vector3 dir = Test1.magnetTracker.getTotalFacingVector();
		dir.scaleBy(BStrength);
		return dir;
		
		//Just constant for now. ToDo: make this method get the field from all nearby magnet blocks
	}
	
	public static Vector3 getEField(Vector3 position)
	{
		Vector3 dir = EDir.getUnitVector();
		dir.scaleBy(EStrength);
		return dir;
		
		//Just constant for now. ToDo: make this method get the field from all nearby charged blocks
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
		LogHelper.info(Vector3.crossProduct(vel, B).toString());
		force.scaleBy(charge);							// F = q(E + v X B)
		return force;
	}
}
