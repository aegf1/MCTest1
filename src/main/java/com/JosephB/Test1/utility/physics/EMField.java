package com.josephb.test1.utility.physics;

public class EMField 
{
	private static Vector3 BDir = new Vector3(0,1,0);
	private static double BStrength = 1;
	private static Vector3 EDir = new Vector3(0,1,0);
	private static double EStrength = 0;
	
	public static Vector3 getBField(Vector3 position)
	{
		Vector3 dir = BDir.getUnitVector();
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
		force.scaleBy(charge);							// F = q(E + v X B)
		return force;
	}
}
