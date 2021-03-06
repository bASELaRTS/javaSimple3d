package javaSimple3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Test extends Game {
	private Vector3 m_rotation;
	
	public Test() {
		super();
		
		this.setKey("Test");
		
		// initialize keyboard
		this.getInput().getKeyboard().add(KeyEvent.VK_LEFT);
    this.getInput().getKeyboard().add(KeyEvent.VK_RIGHT);
    this.getInput().getKeyboard().add(KeyEvent.VK_UP);
    this.getInput().getKeyboard().add(KeyEvent.VK_DOWN);
    this.getInput().getKeyboard().add(KeyEvent.VK_A);
    this.getInput().getKeyboard().add(KeyEvent.VK_B);
    this.getInput().getKeyboard().add(KeyEvent.VK_Z);
    this.getInput().getKeyboard().add(KeyEvent.VK_COMMA);
    this.getInput().getKeyboard().add(KeyEvent.VK_PERIOD);
		
    this.getCamera().getPosition().setCoordinates(0.0, 0.0, -5.0);
		this.m_rotation = new Vector3();
		
		Mesh mesh;		
		
		/*
		mesh = Mesh.createPlaneX();
		mesh.getTextures().add(new Texture("data\\RE2Claire\\EMD49.png"));
    mesh.setBackfaceCulling(false);
		for(int i=0;i<mesh.getFaces().size();i++) {
		  mesh.getFaces().elementAt(i).setTextureIndex(0);
		}
    this.getCamera().getPosition().setCoordinates(0, 0, -5);
		/**/
		
		/*
		mesh = Mesh.createCube();
    mesh.getTextures().add(new Texture("data\\RE2Claire\\EMD49.png"));
    for(int i=0;i<mesh.getFaces().size();i++) {
      mesh.getFaces().elementAt(i).setTextureIndex(0);
    }
		mesh.setBackfaceCulling(true);
		this.getCamera().getPosition().setCoordinates(0, 0, -5);
		/**/
		
		/*
		mesh = new Mesh();
		mesh.load("data\\RE2Leon\\leon.obj");
    mesh.getTextures().add(new Texture("data\\RE2Leon\\EMD48.png"));
    for(int i=0;i<mesh.getFaces().size();i++) {
      mesh.getFaces().elementAt(i).setTextureIndex(0);
    }
    mesh.setBackfaceCulling(true);
    mesh.setInvertedBackfaceCulling(false);
		this.getCamera().getPosition().setCoordinates(0.0, 27, -50);
		/**/
		
		/*
		mesh = new Mesh();
		mesh.load("data\\RE2Claire\\claire.obj");
    mesh.getTextures().add(new Texture("data\\RE2Claire\\EMD49.png"));
    for(int i=0;i<mesh.getFaces().size();i++) {
      mesh.getFaces().elementAt(i).setTextureIndex(0);
    }
    mesh.setBackfaceCulling(true);
    mesh.setInvertedBackfaceCulling(false);
		this.getCamera().getPosition().setCoordinates(0, 27, -50);
		/**/
		
		//*
		mesh = new Mesh();
		mesh.load("data\\RE2Tyrant\\em12b.obj");
    mesh.getTextures().add(new Texture("data\\RE2Tyrant\\EM12B.png"));
    for(int i=0;i<mesh.getFaces().size();i++) {
      mesh.getFaces().elementAt(i).setTextureIndex(0);
    }
    mesh.setBackfaceCulling(true);
    mesh.setInvertedBackfaceCulling(false);
		this.getCamera().getPosition().setCoordinates(0.0, 35, -100);
		/**/
		
    /*
    mesh = new Mesh();
    mesh.load("data\\DSSM64Penguin\\penguin.obj");
    mesh.getTextures().add(new Texture("data\\DSSM64Penguin\\penguin_cmp4.png"));
    for(int i=0;i<mesh.getFaces().size();i++) {
      mesh.getFaces().elementAt(i).setTextureIndex(0);
    }
    mesh.setBackfaceCulling(true);
    this.getCamera().getPosition().setCoordinates(0.0, 1, -5);
    /**/

		//mesh = new Mesh();mesh.load("D:\\bASEL\\Projects\\Java\\javaMGSRipper\\25.obj");
		//mesh = new Mesh();mesh.load("data\\Ratchet\\Armor0.obj");this.getCamera().getPosition().setCoordinates(0.0, 5, -10);
		//mesh = new Mesh();mesh.load("data\\LoUEllieDefault\\Ellie_MainOutfit.obj");this.getCamera().getPosition().setCoordinates(0.0, 0.008, -0.015);
    //mesh = new Mesh();mesh.load("data\\SMGMario\\mario.obj");this.getCamera().getPosition().setCoordinates(0.0, 70, -140);
		this.getCamera().getTarget().y = this.getCamera().getPosition().y;
		this.getMeshes().add(mesh);		
	}	
	
	protected void transformLocal(Mesh mesh) {
		int i;
		Vector4 v41 = new Vector4();
		Vector4 v42 = new Vector4();
		Vector3 v31 = new Vector3();
		double[] mx = Matrix4.create();
		double[] my = Matrix4.create();
		double[] mz = Matrix4.create();
		double[] m1 = Matrix4.create();
		double[] m2 = Matrix4.create();
		
		Matrix4.rotationX(Helper.deg2rad(this.m_rotation.x), mx);
		Matrix4.rotationY(Helper.deg2rad(this.m_rotation.y), my);
		Matrix4.rotationZ(Helper.deg2rad(this.m_rotation.z), mz);
		
		Matrix4.identity(m1);
		Matrix4.multiply(m1, mx, m2);
		Matrix4.multiply(m2, my, m1);
		Matrix4.multiply(m1, mz, m2);
		Matrix4.copy(m2, m1);		
				
		for(i=0;i<mesh.getVerticesTransformed().size();i++) {
			v31.setVector(mesh.getVerticesTransformed().elementAt(i));
			v41.setCoordinates(v31.x,v31.y,v31.z,1.0);
			Matrix4.multiply(m1, v41, v42);
			v31.setCoordinates(v42.x,v42.y,v42.z);
			mesh.getVerticesTransformed().elementAt(i).setVector(v31);
		}		
	}
	
	public void update() {
    super.update();

    double speed = 360 / 5000.0;		
		//this.m_rotation.x += (this.getTimer().getElapsed()*speed)%360;
		//this.m_rotation.y = (this.m_rotation.y + (this.getTimer().getElapsed()*speed))%360;
		//this.m_rotation.z += (this.getTimer().getElapsed()*speed)%360;

		Camera camera = this.getCamera();
		Keyboard keyboard = this.getInput().getKeyboard();
		
		if (keyboard.getState(KeyEvent.VK_COMMA)) {
		  this.m_rotation.y = (this.m_rotation.y+(this.getTimer().getElapsed()*speed))%360;
		} else if (keyboard.getState(KeyEvent.VK_PERIOD)) {
      this.m_rotation.y = (this.m_rotation.y-(this.getTimer().getElapsed()*speed))%360;		  
		}
		
		if (keyboard.getState(KeyEvent.VK_LEFT)) {
		  camera.getPosition().x--;
		} else if (keyboard.getState(KeyEvent.VK_RIGHT)) {
      camera.getPosition().x++;		  
		}
    if (keyboard.getState(KeyEvent.VK_A)) {
      camera.getPosition().y++;      
    } else if (keyboard.getState(KeyEvent.VK_Z)) {
      camera.getPosition().y--;      
    }
    if (keyboard.getState(KeyEvent.VK_UP)) {
      camera.getPosition().z++;      
    } else if (keyboard.getState(KeyEvent.VK_DOWN)) {
      camera.getPosition().z--;      
    }		      
    
    if (keyboard.getState(KeyEvent.VK_B)) {
      keyboard.setState(KeyEvent.VK_B, false);
      Mesh mesh = this.getMeshes().elementAt(0);
      mesh.setBackfaceCulling(!mesh.getBackfaceCulling());
    }
	}
	
	public void paint() {
		super.paint();
		
		//this.getGraphics().drawTexture(this.m_texture, 16, 16);

		Mesh mesh = this.getMeshes().elementAt(0);		
		Graphics g = this.getGraphics().getImage().getGraphics();
		g.setColor(Color.yellow);
		g.drawString("FPS:" + Integer.toString(this.getTimer().getFPS()), 2, 12);
		g.drawString("Mesh Vertices:" + Integer.toString(mesh.getVertices().size()), 2, 22);
		g.drawString("Mesh Faces:" + Integer.toString(mesh.getFaces().size()), 2, 32);
		g.drawString("Mesh Backface culling:" + Boolean.toString(mesh.getBackfaceCulling()), 2, 42);
		g.drawString("Renderlist:" + Integer.toString(this.getRenderlist().count()), 2, 52);
		g.drawString("Camera Pos:" + this.getCamera().getPosition().toString(), 2, 62);
		g.drawString("Rotation:" + this.m_rotation.toString(), 2, 72);
		//g.drawString("Mesh info:" + mesh.getVerticesTransformed().elementAt(0).toString(), 2, 82);
	}
	
	public static void main(String[] args) {
		new GameFrame(new Test());
	}
}
