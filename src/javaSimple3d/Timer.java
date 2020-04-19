package javaSimple3d;

public class Timer {
	private int m_elapsed;
	private long m_elapsedTimestamp;
	
	private int m_fps;
	private int m_fpsCounter;
	private long m_fpsTimestamp;
	
	public Timer() {
		long dtm = System.currentTimeMillis();
		this.setElapsed(0);
		this.m_elapsedTimestamp = dtm;
		this.setFPS(0);
		this.m_fpsCounter = 0;
		this.m_fpsTimestamp = dtm;
	}
	
	public void update() {
		long dtm = System.currentTimeMillis();
		
		this.setElapsed((int)(dtm-this.m_elapsedTimestamp));
		this.m_elapsedTimestamp=dtm;
		
		if ((dtm-this.m_fpsTimestamp)>=1000) {
			this.m_fpsTimestamp = dtm;
			this.m_fps = this.m_fpsCounter;
			this.m_fpsCounter=0;
		}
		this.m_fpsCounter++;
	}
		
	public void setElapsed(int i) {this.m_elapsed=i;}
	public int getElapsed() {return this.m_elapsed;}
	
	public void setFPS(int i) {this.m_fps=i;}
	public int getFPS() {return this.m_fps;}
}
