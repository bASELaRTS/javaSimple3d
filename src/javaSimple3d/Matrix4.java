package javaSimple3d;

public class Matrix4 {
	public static double[] create() {
		double[] m = new double[16];
		return m;
	}
	
	public static void clear(double[] m) {
		for(int i=0;i<m.length;i++) {
			m[i]=0.0;
		}
	}
	
	public static void copy(double[] m1, double[] m2) {
		for(int i=0;i<m1.length;i++) {
			m2[i]=m1[i];
		}
	}
	
	public static void identity(double[] m) {
		Matrix4.clear(m);
		m[ 0]=1.0;
		m[ 5]=1.0;
		m[10]=1.0;
		m[15]=1.0;
	}
	
	public static void multiply(double[] m1, double[] m2, double[] m3) {
		int i,j;
    for (j = 0; j < 4; j++) {
      for (i = 0; i < 4; i++) {
          m3[j*4+i] = 
             m1[j*4+0]*m2[0*4+i]
            +m1[j*4+1]*m2[1*4+i]
            +m1[j*4+2]*m2[2*4+i]
            +m1[j*4+3]*m2[3*4+i]
          ;
      }
    }
	}
	
	public static void _multiply(double[] m, Vector4 v1, Vector4 v2) {
		v2.x = (m[0 * 4 + 0] * v1.x) + (m[1 * 4 + 0] * v1.y) + (m[2 * 4 + 0] * v1.z) + (m[3 * 4 + 0] * v1.w);
    v2.y = (m[0 * 4 + 1] * v1.x) + (m[1 * 4 + 1] * v1.y) + (m[2 * 4 + 1] * v1.z) + (m[3 * 4 + 1] * v1.w);
    v2.z = (m[0 * 4 + 2] * v1.x) + (m[1 * 4 + 2] * v1.y) + (m[2 * 4 + 2] * v1.z) + (m[3 * 4 + 2] * v1.w);
    v2.w = (m[0 * 4 + 3] * v1.x) + (m[1 * 4 + 3] * v1.y) + (m[2 * 4 + 3] * v1.z) + (m[3 * 4 + 3] * v1.w);
  }
	
	public static void multiply(double[] m, Vector4 v1, Vector4 v2) {
		v2.x = (m[0 * 4 + 0] * v1.x) + (m[0 * 4 + 1] * v1.y) + (m[0 * 4 + 2] * v1.z) + (m[0 * 4 + 3] * v1.w);
    v2.y = (m[1 * 4 + 0] * v1.x) + (m[1 * 4 + 1] * v1.y) + (m[1 * 4 + 2] * v1.z) + (m[1 * 4 + 3] * v1.w);
    v2.z = (m[2 * 4 + 0] * v1.x) + (m[2 * 4 + 1] * v1.y) + (m[2 * 4 + 2] * v1.z) + (m[2 * 4 + 3] * v1.w);
    v2.w = (m[3 * 4 + 0] * v1.x) + (m[3 * 4 + 1] * v1.y) + (m[3 * 4 + 2] * v1.z) + (m[3 * 4 + 3] * v1.w);
  }

	public static void rotationX(double angle, double[] m){
    double c = Math.cos(angle);
    double s = Math.sin(angle);
    m[ 0]=1.0;m[ 1]=0.0;m[ 2]=0.0;m[ 3]=0.0;
    m[ 4]=0.0;m[ 5]=  c;m[ 6]=  s;m[ 7]=0.0;
    m[ 8]=0.0;m[ 9]= -s;m[10]=  c;m[11]=0.0;
    m[12]=0.0;m[13]=0.0;m[14]=0.0;m[15]=1.0;
  }
  public static void rotationY(double angle, double[] m){
    double c = Math.cos(angle);
    double s = Math.sin(angle);
    m[ 0]=  c;m[ 1]=0.0;m[ 2]= -s;m[ 3]=0.0;
    m[ 4]=0.0;m[ 5]=1.0;m[ 6]=0.0;m[ 7]=0.0;
    m[ 8]=  s;m[ 9]=0.0;m[10]=  c;m[11]=0.0;
    m[12]=0.0;m[13]=0.0;m[14]=0.0;m[15]=1.0;
  }
  public static void rotationZ(double angle, double[] m){
    double c = Math.cos(angle);
    double s = Math.sin(angle);
    m[ 0]=  c;m[ 1]=  s;m[ 2]=0.0;m[ 3]=0.0;
    m[ 4]= -s;m[ 5]=  c;m[ 6]=0.0;m[ 7]=0.0;
    m[ 8]=0.0;m[ 9]=0.0;m[10]=1.0;m[11]=0.0;
    m[12]=0.0;m[13]=0.0;m[14]=0.0;m[15]=1.0;
  }
}
