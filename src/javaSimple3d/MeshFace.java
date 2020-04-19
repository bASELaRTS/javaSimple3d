package javaSimple3d;

import java.util.Vector;

public class MeshFace {
	private Vector<Integer> m_vertices;
	private Vector<Integer> m_uvs;
	
	public MeshFace() {
		this.m_vertices = new Vector<Integer>();
		this.m_uvs = new Vector<Integer>();
	}
	
	public Vector<Integer> getVerticesIndex(){return this.m_vertices;}
	public Vector<Integer> getUVs(){return this.m_uvs;}
	
	public static MeshFace create3(int v1, int v2, int v3, int vt1, int vt2, int vt3) {
		MeshFace face = new MeshFace();
		face.getVerticesIndex().add(v1);
		face.getVerticesIndex().add(v2);
		face.getVerticesIndex().add(v3);
		
		face.getUVs().add(vt1);
		face.getUVs().add(vt2);
		face.getUVs().add(vt3);
		
		return face;
	}
}
