package com.josephb.test1.utility.physics;

import net.minecraft.util.Vec3;

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
	private double x,y,z;	// (x,y,z)
	
	public Vector3()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3(double xIn, double yIn, double zIn)
	{
		x = xIn;
		y = yIn;
		z = zIn;
	}
	
	public Vector3(double[] components)
	{
		if( components.length >= 3)
		{
			x = components[0];
			y = components[1];
			z = components[2];
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
		return x;
	}

	public double getY() 
	{
		return y;
	}

	public double getZ() 
	{
		return z;
	}
	
	public void setX(double xIn)
	{
		x = xIn;
	}
	
	public void setY(double yIn)
	{
		y = yIn;
	}
	
	public void setZ(double zIn)
	{
		z = zIn;
	}

	public void setVector(Vector3 v) 
	{
		x = v.getX();
		y = v.getY();
		z = v.getZ();
		
	}
	
	public void setVector(double xIn, double yIn, double zIn)
	{
		x = xIn;
		y = yIn;
		z = zIn;
	}
	
	public void setVector(double[] components)
	{
		if( components.length >= 3)
		{
			x = components[0];
			y = components[1];
			z = components[2];
		}
		else
		{
			this.setVector(new Vector3());
		}
	}
	
	public void increaseBy(Vector3 v)
	{
		x += v.getX();
		y += v.getY();
		z += v.getZ();
	}
	
	public void decreaseBy(Vector3 v)
	{
		x -= v.getX();
		y -= v.getY();
		z -= v.getZ();
	}
	
	public double magnitude()
	{
		return Math.sqrt(x*x + y*y + z*z);
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
		x *= d;
		y *= d;
		z *= d;
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
	
	public Vec3 getVec3()
	{
		return new Vec3(x,this.y,this.z);
	}
	
	public String toString()
	{
		return x + ", " + y + ", " + z;
	}
}
