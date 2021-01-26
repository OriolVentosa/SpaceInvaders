import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
	boolean first;	
	
	public Enemy(int x, int y, int width, int height, int nLives,  int type, BufferedImage[] sprites) {
		super(x,y,width,height,nLives,sprites);
	}
	
	void mostraEnemic(Graphics g) {
		if(alive) {
			if(first) {
				super.mostraImatge(g,0);
			}
			else {
				super.mostraImatge(g,1);
			}
		}
	}
	
	public void changeSprite() {
		first = !first;
	}
	
	
}
