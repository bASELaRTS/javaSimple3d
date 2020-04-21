package javaSimple3d;

public class Camera { 
	private Vector3 m_position;
	private Vector3 m_target;
	private double m_roll;
	
	// uvn
	private Vector3 m_v;
	private Vector3 m_u;
	private Vector3 m_n;
	private double[] m_matrixUVN;
	private double[] m_matrixTranslate;
	private double[] m_matrixCamera;
	
	private double m_distance;
	private double m_fieldOfView;
	private double m_aspectRatio;
	private int m_screenWidth;
	private int m_screenHeight;
	
	public Camera(int w, int h) {
		this.m_position = new Vector3(0,0,-10);
		this.m_target = new Vector3(0,0,0);
		
		this.m_v = new Vector3(0,1,0);
		this.m_u = new Vector3(1,0,0);
		this.m_n = new Vector3(0,0,1);
		
		this.m_matrixUVN = Matrix4.create();Matrix4.identity(this.m_matrixUVN);
		this.m_matrixTranslate = Matrix4.create();Matrix4.identity(this.m_matrixTranslate);
		this.m_matrixCamera = Matrix4.create();Matrix4.identity(this.m_matrixCamera);
		
		this.setScreenSize(w, h);
		this.setFieldOfView(90);
		this.setRoll(0);
	}
	
	public void update() {
		this.m_v.x = Math.sin(Helper.deg2rad(this.getRoll()));
		this.m_v.y =-Math.cos(Helper.deg2rad(this.getRoll()));
		this.m_v.z = 0;
		
		Vector3.subtract(this.getTarget(), this.getPosition(), this.m_n);
		this.getForward().normalize();
		
		Vector3.cross(this.m_v, this.m_n, this.m_u);
		this.getRight().normalize();
		
		Vector3.cross(this.m_u, this.m_n, this.m_v);
		this.getUp().normalize();
		
		Matrix4.set(this.m_matrixUVN,
		    this.m_u.x,this.m_u.y,this.m_u.z,0.0,
        this.m_v.x,this.m_v.y,this.m_v.z,0.0,
        this.m_n.x,this.m_n.y,this.m_n.z,0.0,
        0,0,0,1
		);
		
		Matrix4.identity(this.m_matrixTranslate);
		this.m_matrixTranslate[ 3]=-this.getPosition().x;
		this.m_matrixTranslate[ 7]=-this.getPosition().y;
		this.m_matrixTranslate[11]=-this.getPosition().z;
		
		Matrix4.multiply(this.m_matrixUVN, this.m_matrixTranslate, this.m_matrixCamera);
	}
	
	public void toCamera(Vector3 v1, Vector3 v2) {
		Vector4 v41 = new Vector4();
		Vector4 v42 = new Vector4();
		v41.setCoordinates(v1.x, v1.y, v1.z, 1.0);
		Matrix4.multiply(this.getMatrixCamera(), v41, v42);
		v2.setCoordinates(v42.x, v42.y, v42.z);
	}
	
	public void toPerspective(Vector3 v1, Vector3 v2) {
		v2.x =                      this.m_distance * v1.x / v1.z;
		v2.y = this.m_aspectRatio * this.m_distance * v1.y / v1.z;
		v2.z = v1.z;
	}
	
	public void toScreen(Vector3 v1, Vector3 v2) {
		int cw = (int)(this.getScreenWidth()*0.5);
		int ch = (int)(this.getScreenHeight()*0.5);
		v2.x = cw + (v1.x*cw);
		v2.y = ch - (v1.y*ch);
		v2.z = v1.z;
	}
	public void setScreenWidth(int i) {this.m_screenWidth=i;}
	public int getScreenWidth() {return this.m_screenWidth;}
	public void setScreenHeight(int i) {this.m_screenHeight=i;}
	public int getScreenHeight() {return this.m_screenHeight;}
	public void setScreenSize(int w, int h) {
		this.setScreenWidth(w);
		this.setScreenHeight(h);
		this.setFieldOfView(this.getFieldOfView());
	}
	public void setFieldOfView(double fov) {
		this.m_fieldOfView = fov;		
		this.m_aspectRatio = this.getScreenWidth()/(double)this.getScreenHeight();
		//this.m_distance = (this.getScreenWidth()*0.5) / Math.sqrt(Helper.deg2rad(fov*0.5);
		this.m_distance = 1.0 / Math.sqrt(Helper.deg2rad(fov*0.5));
	}
	public double getFieldOfView() {return this.m_fieldOfView;}
	public double getDistance() {return this.m_distance;}
	public Vector3 getPosition() {return this.m_position;}
	public Vector3 getTarget() {return this.m_target;}
	public void setRoll(double d) {this.m_roll=d;}
	public double getRoll() {return this.m_roll;}
	public Vector3 getUp() {return this.m_v;}
	public Vector3 getForward() {return this.m_n;}
	public Vector3 getRight() {return this.m_u;}
	public double[] getMatrixUVN() {return this.m_matrixUVN;}
	public double[] getMatrixTranslate() {return this.m_matrixTranslate;}
	public double[] getMatrixCamera() {return this.m_matrixCamera;}
}
