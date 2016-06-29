package com.JosephB.maxwellcraft.reference;

/**
 * 
 * @author Joseph Brownless
 */
public class Reference {

	// Constants for mod
	
	public static final String MOD_ID = "MaxwellCraft";	
	public static final String MOD_ID_LC = "maxwellcraft";
	public static final String MOD_NAME = "maxwellcraft";
	public static final String VERSION = "2.1";
	public static final String CLIENT_PROXY_CLASS = "com.JosephB.maxwellcraft.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.JosephB.maxwellcraft.proxy.ServerProxy";	
	public static final String GUI_FACTORY_CLASS = "com.JosephB.maxwellcraft.client.gui.GuiFactory";
	public static final int ITERATIONS_PER_TICK = 50;
	public static final double MAGNETIC_DIPOLE_CONSTANT = 2D;
	public static final double ELECTRIC_FIELD_CONSTANT = 20D;
	public static final double SPEED_OF_LIGHT = 100D; 	// in blocks/second
	public static final float protonSpeed = 5F;			// in blocks/second
	public static final float aProtonSpeed = 5F;		// in blocks/second
	public static final float protonCharge = 1F;
	public static final float aProtonCharge = -1F;
	public static final float protonMass = 1F;
	public static final float aProtonMass = 1F;
	public static final int thrownParticleLifetime = 1000; // in ticks
	public static final float particleExplosionRad = 1F;
}
