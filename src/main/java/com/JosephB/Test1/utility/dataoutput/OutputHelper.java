package com.josephb.test1.utility.dataoutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.physics.Vector3;

public class OutputHelper 
{
	public static boolean running = false;
	public static PrintWriter dataOutput;
	public static int ticksRunning = 0;
	
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
	        
	        return true;
		}
		else {return false;}
	}
	
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
	
	public static boolean record(Vector3 point)
	{
		if((point != null) && (Math.abs(point.magnitude()) > 1e-34) && (running))
		{
			dataOutput.println((ticksRunning*0.05)+" "+point.getX()+" "+point.getY()+" "+point.getZ());
		}
		System.out.println(ticksRunning);
		
		return running;
	}
}
