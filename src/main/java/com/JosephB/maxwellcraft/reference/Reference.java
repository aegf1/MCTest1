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
	public static float magDipFieldConst = 5F;
	public static float eFieldConst = 20F;
	public static float lightSpeed = 100F; 	// in blocks/second
	public static float protonSpeed = 5F;			// in blocks/second
	public static float aProtonSpeed = 5F;		// in blocks/second
	public static float protonCharge = 1F;
	public static float aProtonCharge = -1F;
	public static float protonMass = 1F;
	public static float aProtonMass = 1F;
	public static int thrownParticleLifetime = 1000; // in ticks
	public static float particleExplosionRad = 1F;
}
