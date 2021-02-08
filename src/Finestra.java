import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class Finestra extends JFrame implements KeyListener, WindowListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	joc j;
	Graphics g;
	Image im;
	String font_name = "space_invaders.ttf";
	Font font;
	int score = 0, hiscore = 0;

	public int scale = 3;
	int AMPLE = 262*scale, ALT = 315*scale;
	public static void main(String[] args) {
		new Finestra();
	}
	
	Finestra(){
		loadFont(font_name);
		addKeyListener(this);
		addWindowListener(this);
		setSize(AMPLE,ALT);
		setVisible(true);
		
		im = createImage(AMPLE, ALT);
		g = im.getGraphics();
		while(true) {
			j = new joc(this, score, hiscore);
			j.run();
			
			if(j.won) {
				score = j.score;
			}
			else {
				score = 0;
				hiscore = j.hiscore;
			}
		}

	}
	
	void loadFont(String font_name) {
		try {
			//create the font to use. Specify the size!
		    font = Font.createFont(Font.TRUETYPE_FONT, new File("/SpaceInvaders/data/font/" + font_name)).deriveFont(scale*12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(font);
		} catch (IOException|FontFormatException e) {
			System.out.println("No s'ha carregat la font");
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(im, 0, 0, null);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		j.KeyPressed(e.getKeyChar());
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);		
	}

	@Override
	public void windowClosed(WindowEvent e) {		
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}
}
