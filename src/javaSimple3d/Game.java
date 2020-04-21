package javaSimple3d;

import java.util.Vector;

public class Game {
	private int m_width;
	private int m_height;
	
	private GameGraphics m_graphics;
	private Timer m_timer;
	private Renderlist m_renderlist;
	private Camera m_camera;
	private Vector<Mesh> m_meshes;
	private Input m_input;

	public Game() {		
		int w = 640;
		int h = 480;
		
		this.m_graphics = new GameGraphics(w,h);
		this.m_timer = new Timer();
		this.m_renderlist = new Renderlist();
		this.m_camera = new Camera(w,h);
		this.m_meshes = new Vector<Mesh>();
		this.m_input = new Input();

		this.setSize(w,h);		
	}

	public void update() {
		int i,j,k;
		int index;
		double d;
		Mesh mesh;
		MeshFace face;
		Polygon polygon;
		Vector3 v31 = new Vector3();
		Vector3 v32 = new Vector3();
		boolean b;

		this.getGraphics().resetZbuffer();
		this.m_renderlist.clear();
		this.getCamera().update();
		
		for(i=0;i<this.m_meshes.size();i++) {
			mesh = this.m_meshes.elementAt(i);
			
			this.setupMesh(mesh);
			this.transformLocal(mesh);
			
			
			
			// transform points
			for(j=0;j<mesh.getVerticesTransformed().size();j++) {			
				v31.setVector(mesh.getVerticesTransformed().elementAt(j));
				
				this.getCamera().toCamera(v31, v32);		
				v31.setVector(v32);
				
				mesh.getVerticesTransformed().elementAt(j).setVector(v31);
			}
				
			// push to renderlist
			for(j=0;j<mesh.getFaces().size();j++) {
				face = mesh.getFaces().elementAt(j);
				
				polygon = new Polygon();
				polygon.getColor().setColor(face.getColor());
				
				// copy texture coordinates to polygon
				if ((mesh.getTextures().size()>0)&&(face.getTextureIndex()>=0)) {
	        polygon.setTexture(mesh.getTextures().elementAt(face.getTextureIndex()));
	        for(k=0;k<face.getUVs().size();k++) {	      
	          polygon.getUVs().add(mesh.getUVs().elementAt(face.getUVs().elementAt(k)));
	        }
				}
				
				// copy transformed vertices to polygon
				for(k=0;k<face.getVerticesIndex().size();k++) {
					index = face.getVerticesIndex().elementAt(k);
					v31.setVector(mesh.getVerticesTransformed().elementAt(index));
					polygon.add(new Vector3(v31));
				}
				
				//polygon.calculateCenter();
				
				if (mesh.getBackfaceCulling()) {
					polygon.calculateNormal();
					
					//Vector3.subtract(polygon.getVector(0),this.getCamera().getPosition(),v31);					
					v31.normalize();
					d = Vector3.dot(v31, polygon.getNormal());
					
					if (mesh.getInvertedBackfaceCulling()) {
					  b = d<0.0;
					} else {
					  b = d>0.0;
					}
					  
					if (b) {
						this.getRenderlist().add(polygon);
					}	
				} else {
					this.getRenderlist().add(polygon);					
				}
				
				/*
				Polygon p = new Polygon();
				p.add(polygon.getCenter());
				this.getRenderlist().add(p);
				/**/
			}
			
			// perform sorting
			
			
			// perform lighting

			// renderlist to perspective -> screen
			for(j=0;j<this.getRenderlist().count();j++) {
				polygon = this.getRenderlist().getPolygon(j);

				for(k=0;k<polygon.count();k++) {
					v31.setVector(polygon.getVector(k));
					this.getCamera().toPerspective(v31, v32);
					this.getCamera().toScreen(v32, v31);
					polygon.getVector(k).setVector(v31);
				}
			}
		}
	}	
	
	protected void setupMesh(Mesh mesh) {
		int i;
		for(i=0;i<mesh.getVertices().size();i++) {
			mesh.getVerticesTransformed().elementAt(i).setVector(mesh.getVertices().elementAt(i));;
		}
	}
	
	protected void transformLocal(Mesh mesh) {}
			
	public void paint() {
		int x,y;
		int i,j;
		int c;
		GameGraphics g = this.getGraphics();
		Vector3 v31 = new Vector3();
		Vector3 v32 = new Vector3();
		Vector3 v33 = new Vector3();
		Polygon polygon;
		
		x = 0;
		y = 0;
		
		g.clear(GameColor.ARGB(255, 0, 0, 0));

		for(i=0;i<this.getRenderlist().count();i++) {
			polygon = this.getRenderlist().getPolygon(i);
			c = polygon.count();
			
			// points
			if (c==1) {				
				for(j=0;j<c;j++) {
					v31.setVector(polygon.getVector(j));
					x = (int)(v31.x);
					y = (int)(v31.y);
					g.setPixel(x, y, GameColor.ARGB(255, 255, 255, 255));
				}
			}
			/**/
						
			if (c==3) {
        v31.setVector(polygon.getVector(0));
        v32.setVector(polygon.getVector(1));
        v33.setVector(polygon.getVector(2));
        // polygon
        /*
        g.fillTriangle(
            (int)(v31.x), (int)(v31.y), 
            (int)(v32.x), (int)(v32.y), 
            (int)(v33.x), (int)(v33.y), 
            polygon.getColor().getInt()
        );
        /**/
        
        //*
        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        Vertex vertex3 = new Vertex();
        vertex1.getPoint().setVector(v31);
        vertex1.getUV().setVector(polygon.getUVs().elementAt(0));
        vertex2.getPoint().setVector(v32);
        vertex2.getUV().setVector(polygon.getUVs().elementAt(1));
        vertex3.getPoint().setVector(v33);
        vertex3.getUV().setVector(polygon.getUVs().elementAt(2));
        g.fillTriangleTextured(vertex1, vertex2, vertex3, polygon.getTexture());
        /**/
        // texture
        /*
        if ((polygon.getTexture()!=null)&&(polygon.getUVs().size()==3)) {
          g.fillTriangleTextured(
              (int)(v31.x), (int)(v31.y),new Vector2(polygon.getUVs().elementAt(0)), 
              (int)(v32.x), (int)(v32.y),new Vector2(polygon.getUVs().elementAt(1)), 
              (int)(v33.x), (int)(v33.y),new Vector2(polygon.getUVs().elementAt(2)),
              polygon.getTexture()
            );
        }
        /**/
			}
			
	     // wireframe
			/*
      if (c>1) {
        for(j=0;j<c;j++) {
          v31.setVector(polygon.getVector(j));
          v32.setVector(polygon.getVector((j+1)%c));
          g.drawLine((int)(v31.x), (int)(v31.y), (int)(v32.x), (int)(v32.y), GameColor.ARGB(255, 128, 128, 128));
        }
      }         
      /**/
		}
	}
	
	/*
	public static void main(String[] args) {
		new GameFrame(new Game());
	}	
	/**/
	
	public void setSize(int w, int h) {
		this.setWidth(w);
		this.setHeight(h);
		this.getCamera().setScreenSize(w, h);
		this.getCamera().setFieldOfView(this.getCamera().getFieldOfView());
	}
	public void setWidth(int i) {this.m_width=i;}
	public int getWidth() {return this.m_width;}
	public void setHeight(int i) {this.m_height=i;}
	public int getHeight() {return this.m_height;}	
	public GameGraphics getGraphics() {return this.m_graphics;}
	public Timer getTimer() {return this.m_timer;}
	public Camera getCamera() {return this.m_camera;}
	public Renderlist getRenderlist() {return this.m_renderlist;}
	public Vector<Mesh> getMeshes(){return this.m_meshes;}
	public Input getInput() {return this.m_input;}
}
