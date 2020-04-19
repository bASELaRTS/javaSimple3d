package javaSimple3d;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements WindowListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private GamePanel m_panel;
	
	public GameFrame(Game game) {
		super();
		
		this.m_panel = new GamePanel(game);
		
		this.addWindowListener(this);
		this.addKeyListener(this);
		
		this.setTitle("GameFrame");
		this.setLayout(new BorderLayout());
		this.add(this.m_panel,BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);		
		this.setVisible(true);
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		this.m_panel.setThreadRunning(false);
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode()==KeyEvent.VK_UP) {			
			this.m_panel.getGame().getCamera().getPosition().z++;
		} else if (arg0.getKeyCode()==KeyEvent.VK_DOWN) {
			this.m_panel.getGame().getCamera().getPosition().z--;
		} else if (arg0.getKeyCode()==KeyEvent.VK_LEFT) {
			this.m_panel.getGame().getCamera().getPosition().x--;
		} else if (arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
			this.m_panel.getGame().getCamera().getPosition().x++;
		} else if (arg0.getKeyCode()==KeyEvent.VK_A) {
			this.m_panel.getGame().getCamera().getPosition().y++;
		} else if (arg0.getKeyCode()==KeyEvent.VK_Z) {
			this.m_panel.getGame().getCamera().getPosition().y--;
		} else if (arg0.getKeyCode()==KeyEvent.VK_Q) {
			this.m_panel.getGame().getCamera().setRoll(this.m_panel.getGame().getCamera().getRoll()-1);
		} else if (arg0.getKeyCode()==KeyEvent.VK_E) {
			this.m_panel.getGame().getCamera().setRoll(this.m_panel.getGame().getCamera().getRoll()+1);
		} else if (arg0.getKeyCode()==KeyEvent.VK_R) {
			this.m_panel.getGame().getCamera().getPosition().setCoordinates(0, 0, -5);
		}
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
