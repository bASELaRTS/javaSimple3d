package javaSimple3d;

import java.util.Vector;

public class Polygon {
	private Vector<Vector3> m_points;
	private Vector3 m_normal;
	private Vector3 m_center;
	
	public Polygon() {
		this.m_points = new Vector<Vector3>();
		this.m_normal = new Vector3();
		this.m_center = new Vector3();
	}
	
	public void calculateNormal() {
		Vector3 v1 = new Vector3();
		Vector3 v2 = new Vector3();
		Vector3 v3 = new Vector3();
		
		Vector3.subtract(this.m_points.elementAt(1),this.m_points.elementAt(0),v1);
		Vector3.subtract(this.m_points.elementAt(2),this.m_points.elementAt(0),v2);
		v1.normalize();
		v2.normalize();
		Vector3.cross(v2, v1, v3);
		v3.normalize();
		
		this.getNormal().setVector(v3);
	}
	
	public void calculateCenter() {
		int i,c;
		Vector3 v1 = new Vector3();
		Vector3 v2 = new Vector3();
		c = this.m_points.size();
		for(i=0;i<c;i++) {
			Vector3.add(v1, this.m_points.elementAt(i), v2);
			v1.setVector(v2);
		}
		Vector3.scale(v1,1.0/c,v2);
		this.getCenter().setVector(v2);
	}
	
	public void add(Vector3 v) {
		this.m_points.add(v);
	}	
	public int count(){return this.m_points.size();}
	public Vector3 getVector(int index) {return this.m_points.elementAt(index);}
	public Vector3 getNormal() {return this.m_normal;}
	public Vector3 getCenter() {return this.m_center;}
}
