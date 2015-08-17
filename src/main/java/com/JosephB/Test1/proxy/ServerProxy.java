package com.josephb.test1.proxy;

import com.josephb.test1.utility.LogHelper;
import com.josephb.test1.utility.dataoutput.OutputHelper;
import com.josephb.test1.utility.physics.Vector3;

public class ServerProxy extends CommonProxy 
{

	@Override
	public boolean recordParticleDataPoint(Vector3 point) 
	{
//		LogHelper.info("Recording position"+point.toString());
		return OutputHelper.record(point);
	}
}
