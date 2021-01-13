import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

public class Finestra extends Frame {
	joc j;
	Graphics g;
	Image im;
	int AMPLE = 800, ALT = 600;
	public static void main(String[] args) {
		new Finestra();
	}
	Finestra(){
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
}
