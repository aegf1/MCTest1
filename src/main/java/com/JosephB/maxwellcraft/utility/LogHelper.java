package com.JosephB.maxwellcraft.utility;

import org.apache.logging.log4j.Level;

import com.JosephB.maxwellcraft.reference.Reference;

import net.minecraftforge.fml.common.FMLLog;

/**
 * Makes logging easier
 * @author Joseph Brownless
 */
public class LogHelper {

	/**
	 * Outputs a string (or object.toString) to the log with a certain level
	 * @param logLevel Level
	 * @param object
	 */
	public static void log(Level logLevel, Object object)
	{
		FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
	}
	
	/***
	 * OFF (most specific)
	•FATAL
	•ERROR
	•WARN
	•INFO
	•DEBUG
	•TRACE
	•ALL */

	/** Log with level 'all' */
	public static void all(Object object) { log(Level.ALL, object); }
	/** Log with level 'trace' */
	public static void trace(Object object) { log(Level.TRACE, object); }
	/** Log with level 'debug' */
	public static void debug(Object object) { log(Level.DEBUG, object); }
	/** Log with level 'info' */
	public static void info(Object object) { log(Level.INFO, object); }
	/** Log with level 'warn' */
	public static void warn(Object object) { log(Level.WARN, object); }
	/** Log with level 'error' */
	public static void error(Object object) { log(Level.ERROR, object); }
	/** Log with level 'fatal' */
	public static void fatal(Object object) { log(Level.FATAL, object); }
	/** Log with level 'off' */
	public static void off(Object object) { log(Level.OFF, object); }
}
