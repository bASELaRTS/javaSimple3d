package javaSimple3d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
  private int m_width;
  private int m_height;
  private int[] m_data;
  
  public Texture(int w, int h) {
    this.setSize(w, h);
  }
  
  public Texture(String filename) {
    try {
      this.load(ImageIO.read(new File(filename)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public Texture(BufferedImage image) {
    this.load(image);
  }
  
  private void load(BufferedImage image) {
    this.setSize(image.getWidth(), image.getHeight());
    for(int j=0;j<this.getHeight();j++) {
      for(int i=0;i<this.getWidth();i++) {
        this.setPixel(i, j, image.getRGB(i, j));
      }
    }
  }
  
  public void setPixel(int x, int y, int c) {
    int index = y*this.getWidth()+x;
    if ((index>=0)&&(index<this.m_data.length)) {
      this.m_data[index]=c;
    }
  }
  
  public int getPixel(int x, int y) {
    int index = y*this.getWidth()+x;
    if ((index>=0)&&(index<this.m_data.length)) {
      return this.m_data[index];
    }
    return 0;
  }
  
  public void setSize(int w, int h) {
    this.setWidth(w);
    this.setHeight(h);
    this.m_data = new int[w*h];
  }
  public void setWidth(int i) {this.m_width=i;}
  public int getWidth() {return this.m_width;}
  public void setHeight(int i) {this.m_height=i;}
  public int getHeight() {return this.m_height;}
}
