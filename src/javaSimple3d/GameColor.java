package javaSimple3d;

public class GameColor {
	public int a;
	public int r;
	public int g;
	public int b;
	
	public GameColor() {
		this.setARGB(255, 0, 0, 0);
	}
	public GameColor(int a, int r, int g, int b) {
		this.setARGB(a, r, g, b);
	}
	public GameColor(int c) {
		this.setARGB(c);
	}
	
	public void setColor(GameColor c) {
	  this.setARGB(c.a, c.r, c.g, c.b);
	}
	
	public void setARGB(int a, int r, int g, int b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setARGB(int c) {
		int a = (c>>24)&0xff;
		int r = (c>>16)&0xff;
		int g = (c>>8)&0xff;
		int b = (c)&0xff;
		
		this.setARGB(a, r, g, b);
	}
	
	public int getInt() {return GameColor.ARGB(this.a, this.r, this.g, this.b);}
	
	public static int ARGB(int a, int r, int g, int b) {
		int c = 0;
		c |= (a&0xff)<<24;
		c |= (r&0xff)<<16;
		c |= (g&0xff)<<8;
		c |= (b&0xff);
		return c;
	}
}
