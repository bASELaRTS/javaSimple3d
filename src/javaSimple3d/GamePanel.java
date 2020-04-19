package javaSimple3d;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private boolean m_threadRunning;
  private Thread m_thread;

  private Game m_game;
	
	public GamePanel(Game game) {
		super();
		
		this.m_game = game;
		
		int w = this.getGame().getWidth();
		int h = this.getGame().getHeight();
		this.setPreferredSize(new Dimension(w,h));
		
    this.m_thread = new Thread(this);
    this.m_thread.start();		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if (this.getGame()!=null) {
			this.getGame().getTimer().update();
			this.getGame().update();
			this.getGame().paint();
			g.drawImage(this.getGame().getGraphics().getImage(),0,0,this.getGame().getGraphics().getWidth(),this.getGame().getGraphics().getHeight(),null);
		}
	}

	public void run() {
		this.m_threadRunning = true;
		while (this.m_threadRunning) {			
			try {
				if (this.getGame()!=null) {
					this.repaint();
				}
				
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setGame(Game game) {this.m_game=game;}
	public Game getGame() {return this.m_game;}
	public void setThreadRunning(boolean b) {this.m_threadRunning=b;}
	public boolean getThreadRunning() {return this.m_threadRunning;}
}
