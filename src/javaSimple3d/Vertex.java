package javaSimple3d;

public class Vertex {
  private Vector3 m_point;
  private Vector2 m_uv;
  private int m_color;
  
  public Vertex() {
    this.m_point = new Vector3();
    this.m_uv = new Vector2();
    this.setColor(GameColor.ARGB(255, 255, 255, 255));
  }
  
  public Vertex(Vertex v) {
    this.m_point = new Vector3();
    this.m_uv = new Vector2();
    this.setVertex(v);
  }
  
  public void setVertex(Vertex v) {
    this.getPoint().setVector(v.getPoint());
    this.getUV().setVector(v.getUV());
    this.setColor(v.getColor());
  }
  
  public Vector3 getPoint() {return this.m_point;}
  public Vector2 getUV() {return this.m_uv;}
  public void setColor(int i) {this.m_color=i;}
  public int getColor() {return this.m_color;}
  
  public static void add(Vertex v1, Vertex v2, Vertex v3) {
    Vector3.add(v1.getPoint(), v2.getPoint(), v3.getPoint());
    Vector2.add(v1.getUV(), v2.getUV(), v3.getUV());
    
    // implement adding of color;
  }
  
  public static void swap(Vertex v1, Vertex v2) {
    Vertex v = new Vertex(v1);    
    v1.setVertex(v2);
    v2.setVertex(v);
  }
}
