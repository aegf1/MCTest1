package com.josephb.test1.utility.physics;
/**
 * Represents a 3d vector
 * 
 * Created before i knew that the existing 'Vec3' class (which is terrible) is required for all the
 * bounding box and ray tracing stuff
 * Not used atm, but may be modified to instead perform functions Vec3 doesn't.
 * 
 * @author Joseph Brownless
 *
 */
public class Vector3 
{
	private double vectorComponents[] = new double[3];	// (x,y,z)
	
	public Vector3()
	{
		vectorComponents[0] = 0;
		vectorComponents[1] = 0;
		vectorComponents[2] = 0;
	}
	
	public Vector3(double x, double y, double z)
	{
		vectorComponents[0] = x;
		vectorComponents[1] = y;
		vectorComponents[2] = z;
	}
	
	public Vector3(double[] components)
	{
		if( components.length >= 3)
		{
			vectorComponents[0] = components[0];
			vectorComponents[1] = components[1];
			vectorComponents[2] = components[2];
		}
		else
		{
			this.setVector(new Vector3());
		}
	}
	
	public Vector3(Vector3 v)
	{
		this.setVector(v);
	}

	public double getX() 
	{
		return vectorComponents[0];
	}

	public double getY() 
	{
		return vectorComponents[1];
	}

	public double getZ() 
	{
		return vectorComponents[2];
	}
	
	public void setX(double x)
	{
		vectorComponents[0] = x;
	}
	
	public void setY(double y)
	{
		vectorComponents[1] = y;
	}
	
	public void setZ(double z)
	{
		vectorComponents[2] = z;
	}

	public void setVector(Vector3 v) 
	{
		vectorComponents[0] = v.getX();
		vectorComponents[1] = v.getY();
		vectorComponents[2] = v.getZ();
		
	}
	
	public void setVector(double x, double y, double z)
	{
		vectorComponents[0] = x;
		vectorComponents[1] = y;
		vectorComponents[2] = z;
	}
	
	public void setVector(double[] components)
	{
		if( components.length >= 3)
		{
			vectorComponents[0] = components[0];
			vectorComponents[1] = components[1];
			vectorComponents[2] = components[2];
		}
		else
		{
			this.setVector(new Vector3());
		}
	}
	
	public void increaseBy(Vector3 v)
	{
		vectorComponents[0] += v.getX();
		vectorComponents[1] += v.getY();
		vectorComponents[2] += v.getZ();
	}
	
	public void decreaseBy(Vector3 v)
	{
		vectorComponents[0] -= v.getX();
		vectorComponents[1] -= v.getY();
		vectorComponents[2] -= v.getZ();
	}
	
	public double magnitude()
	{
		return Math.sqrt(vectorComponents[0]*vectorComponents[0]
				+ vectorComponents[1]*vectorComponents[1] + vectorComponents[2]*vectorComponents[2]);
	}
	
	public Vector3 getUnitVector()
	{
		Vector3 unit = new Vector3(this);
		double mag = unit.magnitude();
		if(Math.abs(mag) > 1e-34)	// to avoid dividing by 0
		{
			unit.scaleBy(1/mag);
		}
		else
		{
			unit = new Vector3();
		}
		return unit;
	}

	public void scaleBy(double d) 
	{
		vectorComponents[0] *= d;
		vectorComponents[1] *= d;
		vectorComponents[2] *= d;
	}
	
	public static Vector3 scale(Vector3 v, double d)
	{
		Vector3 v1 = new Vector3(v);
		v1.scaleBy(d);
		return v1;
	}
	
	public static double dotProduct(Vector3 v1, Vector3 v2)
	{
		return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
	}
	
	public static Vector3 crossProduct(Vector3 v1, Vector3 v2)
	{
		return new Vector3(
				v1.getY()*v2.getZ()-v1.getZ()*v2.getY(),
				v1.getZ()*v2.getX()-v1.getX()*v2.getZ(),
				v1.getX()*v2.getY()-v1.getY()*v2.getX()
					) ;
	}
	
	public static Vector3 add(Vector3 v1, Vector3 v2)
	{
		Vector3 sum = new Vector3(v1);
		sum.increaseBy(v2);
		return sum;
	}
	
	public static Vector3 subtract(Vector3 v1, Vector3 v2)
	{
		Vector3 diff = new Vector3(v1);
		diff.decreaseBy(v2);
		return diff;
	}
	
	public boolean equals(Vector3 v)
	{
		Vector3 diff = new Vector3(this);
		diff.decreaseBy(v);
		if(diff.magnitude()<1e-34) { return true; }
		else { return false; }
	}
}
