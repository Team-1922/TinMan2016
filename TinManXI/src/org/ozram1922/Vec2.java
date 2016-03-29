package org.ozram1922;

public class Vec2 {
	public Vec2(double ix, double iy)
	{
		ix = x;
		iy = y;
	}
	public double x = 0;
	public double y = 0;
	public double Mag()
	{
		return Math.pow(x*x + y*y, .5);
	}
}
