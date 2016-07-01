package com.JosephB.maxwellcraft.reference;

/**
 * Contains constants and other stuff usefull in other classes.
 * @author Joseph Brownless
 */
public class Reference {

	// Constants for mod
	
	/**
	 * Unique ID of mod, used for item/block/etc names, and identifying mod to FML
	 */
	public static final String MOD_ID = "MaxwellCraft";	
	/**
	 * Mod ID in lowercase
	 */
	public static final String MOD_ID_LC = "maxwellcraft";
	/**
	 * Mod name, as seen by users
	 */
	public static final String MOD_NAME = "MaxwellCraft";
	/**
	 * Version of mod
	 */
	public static final String VERSION = "2.1";
	/**
	 * Location of client proxy
	 */
	public static final String CLIENT_PROXY_CLASS = "com.JosephB.maxwellcraft.proxy.ClientProxy";
	/**
	 * Location of server proxy
	 */
	public static final String SERVER_PROXY_CLASS = "com.JosephB.maxwellcraft.proxy.ServerProxy";	
	/**
	 * Location of GUI factory. (For config screen only atm)
	 */
	public static final String GUI_FACTORY_CLASS = "com.JosephB.maxwellcraft.client.gui.GuiFactory";
	/**
	 * Number of update iterations performed per ingame 'tick' (0.05s).
	 */
	public static final int ITERATIONS_PER_TICK = 50;
	// Below all set using config file. Listed values are defaults.
	/**
	 * Determines strength of field of magnetic dipole blocks
	 */
	public static float magDipFieldConst = 5F;
	/**
	 * Determines strength of field of electric charge blocks
	 */
	public static float eFieldConst = 20F;
	/**
	 * Speed of light, used in calculating relativistic entity motion.
	 * In blocks/second
	 */
	public static float lightSpeed = 100F;
	/**
	 * Initial speed of thrown protons
	 * In blocks/second
	 */
	public static float protonSpeed = 5F;
	/**
	 * Initial speed of thrown antiprotons
	 * In blocks/second
	 */
	public static float aProtonSpeed = 5F;
	/**
	 * Charge of protons
	 */
	public static float protonCharge = 1F;
	/**
	 * Charge of antiprotons
	 */
	public static float aProtonCharge = -1F;
	/**
	 * Mass of protons
	 */
	public static float protonMass = 1F;
	/**
	 * Mass of antiprotons
	 */
	public static float aProtonMass = 1F;
	/**
	 * Lifetime of protons + antiprotons, in ticks
	 */
	public static int thrownParticleLifetime = 1000;
	/**
	 * Radius of explosions when entities collide with something
	 */
	public static float particleExplosionRad = 1F;
}
