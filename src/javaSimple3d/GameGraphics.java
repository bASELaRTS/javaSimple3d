package javaSimple3d;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameGraphics {
	private int m_width;
	private int m_height;
	
	private BufferedImage m_image;
	private int[] m_data;
	
	public GameGraphics(int w, int h) {
		this.setSize(w, h);
		
		this.m_image = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		this.m_data = ((DataBufferInt)this.m_image.getRaster().getDataBuffer()).getData();
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
