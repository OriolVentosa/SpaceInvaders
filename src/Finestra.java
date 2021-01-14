import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Finestra extends Frame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	joc j;
	Graphics g;
	Image im;
	int AMPLE = 800, ALT = 600;
	public static void main(String[] args) {
		new Finestra();
	}
	Finestra(){
		addKeyListener(this);
		setSize(800,600);
		setVisible(true);
		
		im = createImage(AMPLE, ALT);
		g = im.getGraphics();
		j = new joc(this);
		j.run();
		
	}
	public void paint(Graphics g) {
		g.drawImage(im, 0, 0, null);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		j.KeyPressed(e.getKeyChar());
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
