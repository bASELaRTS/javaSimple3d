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

	public void keyPressed(KeyEvent arg0) {this.m_panel.getGame().getInput().getKeyboard().setState(arg0.getKeyCode(), true);}
	public void keyReleased(KeyEvent arg0) {this.m_panel.getGame().getInput().getKeyboard().setState(arg0.getKeyCode(), false);}
	public void keyTyped(KeyEvent arg0) {}
}
