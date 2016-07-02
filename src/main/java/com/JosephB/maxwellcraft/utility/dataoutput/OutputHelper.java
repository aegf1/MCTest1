package com.JosephB.maxwellcraft.utility.dataoutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.JosephB.maxwellcraft.MaxwellCraft;
import com.JosephB.maxwellcraft.block.BlockMagnet;
import com.JosephB.maxwellcraft.utility.physics.Vector3;

import net.minecraft.util.EnumFacing;

/**
 * Outputs provided data to an external file
 * @author Joseph Brownless
 */
public class OutputHelper 
{
	public static boolean running = false;
	public static PrintWriter dataOutput;
	public static int ticksRunning = 0;
	
	/**
	 * Starts outputting entity position + momentum data every tick
	 * @return true if wasn't running, false otherwise
	 */
	public static boolean startOutput()
	{
		if(!running)
		{
			running = true;
			ticksRunning = 0;
			
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd', 'HH.mm.ss.SSS");
	        try {
				dataOutput = new PrintWriter(new File(sdf.format(cal.getTime()))+".txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        
	        int[][] charges = MaxwellCraft.chargeTracker.get2DArray();
	        if (charges.length>=1) 
			{
		        dataOutput.println("PosCharges:");
				for (int i = 0; i < charges.length; i++) 
				{
					if(charges[i][3] == 1)
					{
						dataOutput.println("("+(charges[i][0]+0.5)+", "+(charges[i][1]+0.5)+", "+(charges[i][2]+0.5)+")");
					}
				} 
				
				dataOutput.println("NegCharges:");
				for (int i = 0; i < charges.length; i++) 
				{
					if(charges[i][3] == 0)
					{
						dataOutput.println("("+(charges[i][0]+0.5)+", "+(charges[i][1]+0.5)+", "+(charges[i][2]+0.5)+")");
					}
				} 
			}
			
	       
			
			dataOutput.println("Magnets: pos(x,y,z), direction(x,y,z) (towards north)");
			int[][] magnets = MaxwellCraft.magnetTracker.get2DArray();
			if (magnets.length>=1) 
			{
//				System.out.println(magnets.length);
//				System.out.println(magnets[0].length);
				for (int i = 0; i < magnets.length; i++) 
				{
					Vector3 thisDir = new Vector3(((EnumFacing) BlockMagnet.getFacing(magnets[i][3])).getDirectionVec());
					dataOutput.println("("+(magnets[i][0]+0.5)+", "+(magnets[i][1]+0.5)+", "+(magnets[i][2]+0.5)+"), ("+thisDir.getX()+", "+thisDir.getY()+", "+thisDir.getZ()+")");
				} 
			}
			
			dataOutput.println("******************");
			dataOutput.println("Begin particle data. ");
			dataOutput.println("Format: time, posX posY posZ, momX momY momZ");
			dataOutput.println("******************");
	        
	        return true;
		}
		else {return false;}
	}
	
	/**
	 * Stop outputting data
	 * @return true if was running
	 */
	public static boolean finishOutput()
	{
		if(running)
		{
			running = false;
			
			dataOutput.close();
			
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public static boolean tick()
	{
		if (running) 
		{
			ticksRunning++;
		}
		return running;
	}
	
	/**
	 * Outputs a string to the file
	 * @param string String to be output
	 * @return True if was running
	 */
	public static boolean record(String string)
	{
		if(running && (string!=null))
		{
			dataOutput.println(string);
		}
//		System.out.println(ticksRunning);
		
		return running;
	}
	
	/**
	 * Outputs the components of a Vector3, along with the time since starting
	 * @param point Point to be output
	 * @return True if was running
	 */
	public static boolean record(Vector3 point)
	{
		if((point != null) && (Math.abs(point.magnitude()) > 1e-34) && (running))
		{
			dataOutput.println((ticksRunning*0.05)+", "+point.getX()+" "+point.getY()+" "+point.getZ());
		}
//		System.out.println(ticksRunning);
		
		return running;
	}
	
	/**
	 * Outputs the components of 2 Vector3s, along with the time since starting
	 * @param point1 First point to be output
	 * @param point2 Second point to be output
	 * @return True if was running
	 */
	public static boolean record2(Vector3 point1, Vector3 point2)
	{
		if((point1 != null) && (Math.abs(point1.magnitude()) > 1e-34) && (running) && (point2 != null) && (Math.abs(point2.magnitude()) > 1e-34))
		{
			dataOutput.println((ticksRunning*0.05)+" "+point1.getX()+" "+point1.getY()+" "+point1.getZ()+", "+point2.getX()+" "+point2.getY()+" "+point2.getZ());
		}
//		System.out.println(ticksRunning);
		
		return running;
	}
	
	/**
	 * Outputs the components of 4 Vector3s, along with the time since starting
	 * @return True if was running
	 */
	public static boolean record4(Vector3 point1, Vector3 point2, Vector3 point3, Vector3 point4)
	{
		if((running) && (point1 != null)  && (point2 != null) && (point3 != null)  && (point4 != null))
		{
			dataOutput.println((ticksRunning*0.05)
					+" "+point1.getX()+" "+point1.getY()+" "+point1.getZ()
					+" "+point2.getX()+" "+point2.getY()+" "+point2.getZ()
					+" "+point3.getX()+" "+point3.getY()+" "+point3.getZ()
					+" "+point4.getX()+" "+point4.getY()+" "+point4.getZ());
		}
//		System.out.println(ticksRunning);
		
		return running;
	}
}