public class Player extends Entity{
	public Player(int x, int y, int width, int height, int nLives) {
		super(x,y,width, height, nLives, "nau_prov.png");
	}
	
	
	Boolean handleCollision() {
		return true;
	}
}

