package javaSimple3d;

import java.util.Vector;

public class Renderlist {
	private Vector<Polygon> m_list;
	public Renderlist() {
		this.m_list = new Vector<Polygon>();
	}
	public void clear() {this.m_list.clear();}
	public void add(Polygon p) {
		this.m_list.add(p);
	}
	public int count() {return this.m_list.size();}
	public Polygon getPolygon(int index) {return this.m_list.elementAt(index);}
}
