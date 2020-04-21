package javaSimple3d;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Vector;

public class GameGraphics {
	private int m_width;
	private int m_height;
	
	private BufferedImage m_image;
	private int[] m_data;
	private double[] m_zbuffer;
	
	public GameGraphics(int w, int h) {
		this.setSize(w, h);
		
		this.m_image = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		this.m_data = ((DataBufferInt)this.m_image.getRaster().getDataBuffer()).getData();
		this.m_zbuffer = new double[w*h];
	}

	public void setPixel(int x, int y, int c) {
		int index = (y*this.getWidth()+x);
		if ((index>=0)&&(index<this.m_data.length)) {
			this.m_data[index]=c;
		}
	}
	public int getPixel(int x, int y) {
		int index = (y*this.getWidth()+x);
		if ((index>=0)&&(index<this.m_data.length)) {
			return this.m_data[index];
		}
		return 0;
	}
	
	public void clear(int c) {
		for(int i=0;i<this.m_data.length;i++) {
			this.m_data[i] = c;
		}
	}
	
	public void drawLine(int x0, int y0, int x1, int y1, int c) {
    boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
    int i = 0;
    int dx;
    int dy;
    int ystep;
    
    if (steep) {
      i = x0;
      x0 = y0;
      y0 = i;
      
      i = x1;
      x1 = y1;
      y1 = i;
    }

    if (x0 > x1) {
      i = x0;
      x0 = x1;
      x1 = i;
      
      i = y0;
      y0 = y1;
      y1 = i;
    }

    dx = x1 - x0;
    dy = Math.abs(y1 - y0);

    int err = dx / 2;

    if (y0 < y1) {
      ystep = 1;
    } else {
      ystep = -1;
    }

    for (; x0<=x1; x0++) {
      if (steep) {
        this.setPixel(y0,x0,c);
      } else {
        this.setPixel(x0,y0,c);
      }
      
      err -= dy;
      if (err < 0) {
        y0 += ystep;
        err += dx;
      }
    }
  }
	
	public void drawHLine(int x1, int y, int x2, int c) {
	  int i,s,e;
	  int index;
	  s = x1;
	  e = x2;
	  if ((y>=0)&&(y<this.getHeight())) {
	    if (x1<0) s=0;
	    if (x2>=this.getWidth()) e=this.getWidth()-1;
	    index = y*this.getWidth()+s;
	    for(i=s;i<e;i++) {
	      this.m_data[index]=c;
	      index++;
	    }
	  }
	}
	
	public void drawHLineTextured(int x1, int y1, double u1, double v1, int x2, double u2, double v2, Texture texture) {	  	
	  int x,y;
	  int c;
	  double u,v;
	  double du;
	  double dv;
	  
	  c = x2-x1;
	  if (c!=0) {
	    du = (u2-u1)/(double)c;
	    dv = (v2-v1)/(double)c;	    
	  } else {
	    du = 0;
	    dv = 0;
	  }
	  
	  u = u1;
	  v = v1;
	  for(int i=x1;i<x2;i++) {
	    x = (int)(u*texture.getWidth());
	    y = texture.getHeight() - (int)(v*texture.getHeight());	    
	    if (x>=texture.getWidth())x=texture.getWidth()-1;
	    if (y>=texture.getHeight())y=texture.getHeight()-1;
	    c = texture.getPixel(x, y);
	    this.setPixel(i, y1, c);
	    u+=du;
	    v+=dv;
	  }	  
	}
	
	public void drawHLineTextured(Vertex v1, Vertex v2, Texture texture) {
	  double d;
	  Vector2 duv = new Vector2();
	  Vector2 uv = new Vector2();
	  Vector2 vector2 = new Vector2();
	  int x,y,c;
	  int x1,x2,y1;
	  double dz,z;

	  d = v2.getPoint().x - v1.getPoint().x;
	  if (d!=0.0) {
	    dz = (v2.getPoint().z - v1.getPoint().z)/d;
	    duv.setCoordinates((v2.getUV().x-v1.getUV().x)/d, (v2.getUV().y-v1.getUV().y)/d);
	  } else {
	    dz = 0;
	    duv.setCoordinates(0, 0);
	  }
	  
    x1 = (int)v1.getPoint().x;
    x2 = (int)v2.getPoint().x;
    y1 = (int)v1.getPoint().y;
	  uv.setVector(v1.getUV());
	  z = v1.getPoint().z;
	  for(int i=x1;i<x2;i++) {
	    d = this.m_zbuffer[y1*this.getWidth()+i];
	    if (z<d) {
	      this.m_zbuffer[y1*this.getWidth()+i]=z;
	      x = (int)(uv.x*texture.getWidth());
	      y = texture.getHeight() - (int)(uv.y*texture.getHeight());     
	      if (x>=texture.getWidth())x=texture.getWidth()-1;
	      if (y>=texture.getHeight())y=texture.getHeight()-1;
	      c = texture.getPixel(x, y);
	      this.setPixel(i, y1, c);
	    }
	    Vector2.add(uv, duv, vector2);
	    uv.setVector(vector2);
	    z += dz;
	  }
	}
		
	public void fillRect(int x, int y, int w, int h, int c) {
    int i;
	  int x2 = (x+w);
	  int y2 = (y+h);
	  if (x<0) x=0;
	  if (x2>=this.getWidth()) x2=this.getWidth()-1;
	  if (y<0) y=0;
	  if (y2>=this.getHeight()) y2=this.getHeight()-1;
	  for(i=y;i<y2;i++) {
	    this.drawHLine(x, i, x2, c);
	  }
	}
	
	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int c) {
	  int i,j,k;
	  double dxac;
	  double dx;	  
	  double sac;
	  double s;
	  
	  if (y1>y3) {
	    i = x1;
	    x1 = x3;
	    x3 = i;
	    
	    i = y1;
	    y1 = y3;
	    y3 = i;
	  }
	  
	  if (y2>y3) {
	    i = x2;
	    x2 = x3;
	    x3 = i;
	    
	    i = y2;
	    y2 = y3;
	    y3 = i;
	  }
	  
	  if (y1>y2) {
	    i = x1;
	    x1 = x2;
	    x2 = i;
	    
	    i = y1;
	    y1 = y2;
	    y2 = i;
	  }
	  
	  // edge buffer
	  int xs[] = new int[y3-y1];
	  int xe[] = new int[y3-y1];
	  
	  // calculate slopes
	  sac = (x3-x1)/(double)(y3-y1);
	  
	  // slope ab
    s = (x2-x1)/(double)(y2-y1);
    dxac = x1;
    dx = x1;
    j = 0;
	  for(i=y1;i<y2;i++) {
	    xs[j]=(int)dxac;
	    xe[j]=(int)dx;
	    
	    if (xs[j]>xe[j]) {
	      k = xs[j];
	      xs[j] = xe[j];
	      xe[j] = k;
	    }
	    
	    dxac+=sac;
	    dx+=s;
	    j++;
	  }
	  
    // slope bc
    s = (x3-x2)/(double)(y3-y2);
    dx = x2;
    for(i=y2;i<y3;i++) {
      xs[j]=(int)dxac;
      xe[j]=(int)dx;
      
      if (xs[j]>xe[j]) {
        k = xs[j];
        xs[j] = xe[j];
        xe[j] = k;
      }
      
      dxac+=sac;
      dx+=s;
      j++;
    }
    
    // draw
    j = y1;
    for(i=0;i<xs.length;i++) {
      this.drawHLine(xs[i], j, xe[i], c);
      j++;
    }
	}
	
  public void fillTriangleTextured(int x1, int y1, Vector2 t1, int x2, int y2, Vector2 t2, int x3, int y3, Vector2 t3, Texture texture) {
    int i,j,k;
    double d;
    Vector2 v2 = new Vector2();
    double dxac;
    double dx;    
    double sac;
    double s;
    double suac;
    double svac;
    double su;
    double sv;
    double duac;
    double dvac;
    double du;
    double dv;
    
    if (y1>y3) {
      i = x1;
      x1 = x3;
      x3 = i;
      
      i = y1;
      y1 = y3;
      y3 = i;
      
      v2.setVector(t1);
      t1.setVector(t3);
      t3.setVector(v2);
    }
    
    if (y2>y3) {
      i = x2;
      x2 = x3;
      x3 = i;
      
      i = y2;
      y2 = y3;
      y3 = i;

      v2.setVector(t2);
      t2.setVector(t3);
      t3.setVector(v2);
    }
    
    if (y1>y2) {
      i = x1;
      x1 = x2;
      x2 = i;
      
      i = y1;
      y1 = y2;
      y2 = i;
      
      v2.setVector(t1);
      t1.setVector(t2);
      t2.setVector(v2);
    }
    
    // edge buffer
    int c = y3-y1;
    int xs[] = new int[c];
    int xe[] = new int[c];
    double us[] = new double[c];
    double vs[] = new double[c];
    double ue[] = new double[c];
    double ve[] = new double[c];
    
    j = 0;

    // calculate slopes
    if (c!=0) {
      sac = (x3-x1)/(double)c;
      suac = (t3.x-t1.x)/(double)c;
      svac = (t3.y-t1.y)/(double)c;      
    } else {
      sac = 0;
      suac = 0;
      svac = 0;
    }

    dxac = x1;
    duac = t1.x;
    dvac = t1.y;
    
    // slope ab
    c = y2-y1;
    if (c!=0) {
      s = (x2-x1)/(double)c;
      su = (t2.x-t1.x)/(double)c;
      sv = (t2.y-t1.y)/(double)c;      
    } else {
      s = 0;
      su = 0;
      sv = 0;
    }
    dx = x1;
    du = t1.x;
    dv = t1.y;
    for(i=y1;i<y2;i++) {
      xs[j]=(int)dxac;
      xe[j]=(int)dx;
      us[j]=duac;
      vs[j]=dvac;
      ue[j]=du;
      ve[j]=dv;
            
      if (xs[j]>xe[j]) {
        k = xs[j];
        xs[j] = xe[j];
        xe[j] = k;
        
        d = us[j];
        us[j] = ue[j];
        ue[j] = d;
        d = vs[j];
        vs[j] = ve[j];
        ve[j] = d;
      }
      
      dxac+=sac;
      dx+=s;
      
      duac+=suac;
      dvac+=svac;
      du+=su;
      dv+=sv;
      
      j++;
    }
    
    // slope bc
    c = y3-y2;
    if (c!=0) {
      s = (x3-x2)/(double)c;
      su = (t3.x-t2.x)/(double)c;
      sv = (t3.y-t2.y)/(double)c;      
    } else {
      s = 0;
      su = 0;
      sv = 0;
    }
    dx = x2;
    du = t2.x;
    dv = t2.y;
    for(i=y2;i<y3;i++) {
      xs[j]=(int)dxac;
      xe[j]=(int)dx;
      us[j]=duac;
      vs[j]=dvac;
      ue[j]=du;
      ve[j]=dv;
            
      if (xs[j]>xe[j]) {
        k = xs[j];
        xs[j] = xe[j];
        xe[j] = k;
        
        d = us[j];
        us[j] = ue[j];
        ue[j] = d;
        d = vs[j];
        vs[j] = ve[j];
        ve[j] = d;
      }
      
      dxac+=sac;
      dx+=s;
      
      duac+=suac;
      dvac+=svac;
      du+=su;
      dv+=sv;
      
      j++;
    }
    
    // draw
    j = y1;
    for(i=0;i<xs.length;i++) {
      this.drawHLineTextured(xs[i], j, us[i], vs[i], xe[i], ue[i], ve[i], texture);
      j++;
    }
  }
  
  public void fillTriangleTextured(Vertex v1, Vertex v2, Vertex v3, Texture texture) {
    if (v1.getPoint().y>v3.getPoint().y) Vertex.swap(v1, v3);
    if (v2.getPoint().y>v3.getPoint().y) Vertex.swap(v2, v3);
    if (v1.getPoint().y>v2.getPoint().y) Vertex.swap(v1, v2);
    
    Vector<Vertex> edgeStart = new Vector<Vertex>();
    Vector<Vertex> edgeEnd = new Vector<Vertex>(); 
    Vertex vertexSlopeAC = new Vertex();
    Vertex vertexSlope = new Vertex();
    Vertex vertexInterpolationAC = new Vertex();
    Vertex vertexInterpolation = new Vertex();
    Vertex vertex = new Vertex();
    double dcount; 
    int i;
    int y1, y2, y3;
    
    y1 = (int)v1.getPoint().y;
    y2 = (int)v2.getPoint().y;
    y3 = (int)v3.getPoint().y;
    
    dcount = y3-y1;
    if (dcount!=0.0) {
      vertexInterpolationAC.getPoint().setCoordinates(
            (v3.getPoint().x - v1.getPoint().x)/dcount
          , 0
          , (v3.getPoint().z - v1.getPoint().z)/dcount
      );
      vertexInterpolationAC.getUV().setCoordinates((v3.getUV().x - v1.getUV().x)/dcount, (v3.getUV().y - v1.getUV().y)/dcount);      
    } else {
      vertexInterpolationAC.getPoint().setCoordinates(0, 0, 0);
      vertexInterpolationAC.getUV().setCoordinates(0,0);      
    }
    vertexSlopeAC.setVertex(v1);
    
    dcount = y2-y1;
    if (dcount!=0.0) {
      vertexInterpolation.getPoint().setCoordinates(
            (v2.getPoint().x - v1.getPoint().x)/dcount
          , 0
          , (v2.getPoint().z - v1.getPoint().z)/dcount
      );
      vertexInterpolation.getUV().setCoordinates((v2.getUV().x - v1.getUV().x)/dcount, (v2.getUV().y - v1.getUV().y)/dcount);
    } else {
      vertexInterpolation.getPoint().setCoordinates(0, 0, 0);
      vertexInterpolation.getUV().setCoordinates(0,0);
    }
    vertexSlope.setVertex(v1);    
    for(i=y1;i<y2;i++) {
      if ((i>=0)&&(i<this.getHeight())) {
        Vertex vertexStart = new Vertex(vertexSlopeAC);
        Vertex vertexEnd = new Vertex(vertexSlope);
        vertexStart.getPoint().y=i;
        vertexEnd.getPoint().y=i;
        if (vertexStart.getPoint().x>vertexEnd.getPoint().x) Vertex.swap(vertexStart, vertexEnd);
        edgeStart.add(vertexStart);
        edgeEnd.add(vertexEnd);        
        Vertex.add(vertexSlopeAC, vertexInterpolationAC, vertex);
        vertexSlopeAC.setVertex(vertex);
        Vertex.add(vertexSlope, vertexInterpolation, vertex);
        vertexSlope.setVertex(vertex);
      }
    }

    dcount = y3-y2;
    if (dcount!=0.0) {
      vertexInterpolation.getPoint().setCoordinates(
            (v3.getPoint().x - v2.getPoint().x)/dcount
          , 0
          , (v3.getPoint().z - v2.getPoint().z)/dcount
      );
      vertexInterpolation.getUV().setCoordinates((v3.getUV().x - v2.getUV().x)/dcount, (v3.getUV().y - v2.getUV().y)/dcount);
    } else {
      vertexInterpolation.getPoint().setCoordinates(0, 0, 0);
      vertexInterpolation.getUV().setCoordinates(0,0);
    }
    vertexSlope.setVertex(v2);    
    for(i=y2;i<y3;i++) {
      if ((i>=0)&&(i<this.getHeight())) {
        Vertex vertexStart = new Vertex(vertexSlopeAC);
        Vertex vertexEnd = new Vertex(vertexSlope);
        vertexStart.getPoint().y=i;
        vertexEnd.getPoint().y=i;
        if (vertexStart.getPoint().x>vertexEnd.getPoint().x) Vertex.swap(vertexStart, vertexEnd);
        edgeStart.add(vertexStart);
        edgeEnd.add(vertexEnd);        
        Vertex.add(vertexSlopeAC, vertexInterpolationAC, vertex);
        vertexSlopeAC.setVertex(vertex);
        Vertex.add(vertexSlope, vertexInterpolation, vertex);
        vertexSlope.setVertex(vertex);
      }
    }
    
    for(i=0;i<edgeStart.size();i++) {
      Vertex vertexStart = edgeStart.elementAt(i);
      Vertex vertexEnd = edgeEnd.elementAt(i);

      if (texture==null) {
        int x1 = (int)vertexStart.getPoint().x;
        int x2 = (int)vertexEnd.getPoint().x;
        int y = (int)vertexStart.getPoint().y;
        int c = (int)vertexStart.getColor();
        this.drawHLine(x1, y, x2, c);
      } else {
        this.drawHLineTextured(vertexStart, vertexEnd, texture);
      }
    }
  }
  
  public void drawTexture(Texture texture, int x, int y) {
    for(int j=0;j<texture.getHeight();j++) {
      for(int i=0;i<texture.getWidth();i++) {
        this.setPixel(i+x, j+y, texture.getPixel(i, j));
      }
    }
  }

  public void resetZbuffer() {
    int i;
    for(i=0;i<this.m_zbuffer.length;i++) {
      this.m_zbuffer[i] = Double.MAX_VALUE;
    }
  }
  public void paint() {}
	public BufferedImage getImage() {return this.m_image;}
	
	public void setSize(int w, int h) {
		this.setWidth(w);
		this.setHeight(h);
	}
	public void setWidth(int i) {this.m_width=i;}
	public int getWidth() {return this.m_width;}
	public void setHeight(int i) {this.m_height=i;}
	public int getHeight() {return this.m_height;}
}
