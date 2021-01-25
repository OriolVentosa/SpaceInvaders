import java.awt.Graphics;

public class Enemy extends Entity{
	public Enemy(int x, int y, int width, int height, int nLives) {
		super(x,y,width,height,nLives,"nau_prov.png");
	}
	
	void mostraEnemic(Graphics g) {
		if(alive) super.mostraImatge(g);
	}
}
