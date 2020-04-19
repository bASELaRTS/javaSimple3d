package javaSimple3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Mesh {
	private Vector<Vector3> m_vertices;
	private Vector<Vector3> m_verticesTransformed;
	private Vector<Vector3> m_normals;
	private Vector<Vector2> m_uvs;
	private Vector<MeshFace> m_faces;
	
	private boolean m_backfaceCulling;
	
	public Mesh() {
		this.m_vertices = new Vector<Vector3>();
		this.m_verticesTransformed = new Vector<Vector3>();
		this.m_normals = new Vector<Vector3>();
		this.m_uvs = new Vector<Vector2>();
		this.m_faces = new Vector<MeshFace>();
		
		this.setBackfaceCulling(false);
	}
			
	public void load(String filename) {
		java.io.File file = new File(filename);
		java.io.FileReader freader;
		try {
			freader = new FileReader(file);
			this.load(new java.io.BufferedReader(freader));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	public void load(java.io.BufferedReader reader) {
		int i,j;
		String line;	
		String line2;
		String strs[];
		String strs2[];
		Vector3 v3;
		Vector2 v2;
		
		this.getVertices().clear();
		this.getVerticesTransformed().clear();
		this.getNormals().clear();
		this.getFaces().clear();
		
		try {
			line = reader.readLine();
			while(line!=null) {
				line = line.trim();				
				if (line.length()>0) {
					line2 = "";
					
					i = line.indexOf(" ");
					if (i>0) {
						line2 = line.substring(0,i).trim();
					}
					
					if (line2.equals("v")) {	
						line2 = line.substring(i).trim();
						strs = line2.split(" ");
						
						v3 = new Vector3();
						v3.x = Double.parseDouble(strs[0].trim());
						v3.y = Double.parseDouble(strs[1].trim());
						v3.z = Double.parseDouble(strs[2].trim());
						this.getVertices().add(v3);
					} else if (line2.equals("vn")) {
						line2 = line.substring(i).trim();
						strs = line2.split(" ");
						
						v3 = new Vector3();
						v3.x = Double.parseDouble(strs[0].trim());
						v3.y = Double.parseDouble(strs[1].trim());
						v3.z = Double.parseDouble(strs[2].trim());
						this.getNormals().add(v3);
					} else if (line2.equals("vt")) {
						line2 = line.substring(i).trim();
						strs = line2.split(" ");
						
						v2 = new Vector2();
						v2.x = Double.parseDouble(strs[0].trim());
						v2.y = Double.parseDouble(strs[1].trim());
						this.getUVs().add(v2);
					} else if (line2.equals("f")) {
						line2 = line.substring(i).trim();
						strs = line2.split(" ");
						
						MeshFace face;
						face = new MeshFace();
						for(i=0;i<strs.length;i++) {
							
							strs2 = strs[i].split("/");
							if (strs2.length==1) {
								j = Integer.parseInt(strs2[0]);
								face.getVerticesIndex().add(j-1);
							} else if (strs2.length==2) {
								j = Integer.parseInt(strs2[0]);
								face.getVerticesIndex().add(j-1);
								j = Integer.parseInt(strs2[1]);
								face.getUVs().add(j-1);
							} else if (strs2.length==3) {
								j = Integer.parseInt(strs2[0]);
								face.getVerticesIndex().add(j-1);
								j = Integer.parseInt(strs2[1]);
								face.getUVs().add(j-1);
							}							
						}
						this.getFaces().add(face);
					}
				}
				
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector3 vmax = new Vector3(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
		Vector3 vmin = new Vector3(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
		for(i=0;i<this.getVertices().size();i++) {
			v3 = this.getVertices().elementAt(i);
			this.getVerticesTransformed().add(new Vector3(v3));
			if (v3.x>vmax.x) vmax.x=v3.x;
			if (v3.y>vmax.y) vmax.y=v3.y;
			if (v3.z>vmax.z) vmax.z=v3.z;
			if (v3.x<vmin.x) vmin.x=v3.x;
			if (v3.y<vmin.y) vmin.y=v3.y;
			if (v3.z<vmin.z) vmin.z=v3.z;
		}
		System.out.println(vmin.toString());
		System.out.println(vmax.toString());
	}
	
	public Vector<Vector3> getVertices(){return this.m_vertices;}
	public Vector<Vector3> getVerticesTransformed(){return this.m_verticesTransformed;}
	public Vector<Vector3> getNormals(){return this.m_normals;}
	public Vector<Vector2> getUVs(){return this.m_uvs;}
	public Vector<MeshFace> getFaces(){return this.m_faces;}
	public void setBackfaceCulling(boolean b) {this.m_backfaceCulling=b;}
	public boolean getBackfaceCulling() {return this.m_backfaceCulling;}
	
	public static Mesh createPlaneX() {
		Mesh mesh;
		mesh = new Mesh();
		
		mesh.getVertices().add(new Vector3(-1, 1, 0)); 
		mesh.getVertices().add(new Vector3( 1, 1, 0));
		mesh.getVertices().add(new Vector3( 1,-1, 0));
		mesh.getVertices().add(new Vector3(-1,-1, 0));
		
		for(int i=0;i<mesh.getVertices().size();i++) {
			mesh.getVerticesTransformed().add(new Vector3(mesh.getVertices().elementAt(i)));
		}
		
		mesh.getUVs().add(new Vector2(0,1));
		mesh.getUVs().add(new Vector2(1,1));
		mesh.getUVs().add(new Vector2(1,0));
		mesh.getUVs().add(new Vector2(0,0));
		
		// front
		mesh.getFaces().add(MeshFace.create3(0, 2, 1, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(0, 3, 2, 0, 3, 2));
		
		return mesh;
	}
	
	public static Mesh createCube() {
		Mesh mesh;
		mesh = new Mesh();
		
		mesh.getVertices().add(new Vector3(-1, 1,-1)); 
		mesh.getVertices().add(new Vector3( 1, 1,-1));
		mesh.getVertices().add(new Vector3( 1,-1,-1));
		mesh.getVertices().add(new Vector3(-1,-1,-1));

		mesh.getVertices().add(new Vector3(-1, 1, 1));
		mesh.getVertices().add(new Vector3( 1, 1, 1));
		mesh.getVertices().add(new Vector3( 1,-1, 1));
		mesh.getVertices().add(new Vector3(-1,-1, 1));
		
		for(int i=0;i<mesh.getVertices().size();i++) {
			mesh.getVerticesTransformed().add(new Vector3(mesh.getVertices().elementAt(i)));
		}
		
		mesh.getUVs().add(new Vector2(0,1));
		mesh.getUVs().add(new Vector2(1,1));
		mesh.getUVs().add(new Vector2(1,0));
		mesh.getUVs().add(new Vector2(0,0));
		
		// front
		mesh.getFaces().add(MeshFace.create3(0, 2, 1, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(0, 3, 2, 0, 3, 2));
		// side
		mesh.getFaces().add(MeshFace.create3(1, 6, 5, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(1, 2, 6, 0, 3, 2));
		// back
		mesh.getFaces().add(MeshFace.create3(5, 7, 4, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(5, 6, 7, 0, 3, 2));
		// side
		mesh.getFaces().add(MeshFace.create3(4, 3, 0, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(4, 7, 3, 0, 3, 2));
		// top
		mesh.getFaces().add(MeshFace.create3(4, 1, 5, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(4, 0, 1, 0, 3, 2));
		// bottom
		mesh.getFaces().add(MeshFace.create3(3, 6, 2, 0, 2, 1));
		mesh.getFaces().add(MeshFace.create3(3, 7, 6, 0, 3, 2));
		
		return mesh;
	}
	
}
