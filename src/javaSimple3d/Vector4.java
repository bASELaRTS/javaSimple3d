package javaSimple3d;

public class Vector4 {
	public double x;
	public double y;
	public double z;
	public double w;
	
	public Vector4() {
		this.setCoordinates(0, 0, 0, 1.0);
  }
	public Vector4(double dx, double dy, double dz, double dw) {
		this.setCoordinates(dx, dy, dz, dw);
	}
	
	public void setCoordinates(double dx, double dy, double dz, double dw) {
		this.x = dx;
		this.y = dy;
		this.z = dz;
		this.w = dw;
	}
	
	public void setVector(Vector4 v) {this.setCoordinates(v.x, v.y, v.z, v.w);}
	public void setVector(Vector3 v) {this.setCoordinates(v.x, v.y, v.z, 1.0);}
}
