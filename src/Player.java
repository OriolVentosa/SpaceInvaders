import java.awt.image.BufferedImage;

public class Player extends Entity{
	public Player(int x, int y, int width, int height, int nLives, BufferedImage[] nau) {
		super(x,y,width, height, nLives, nau);
	}
	
	public int getHealth() {
		return nLives;
	}
}

