public class Entity extends Sprite{
	int nLives;
	Boolean alive;
	
	Entity(int x, int y, int width, int height, int nLives, String imageName){
		super(imageName,x,y, width, height);

		this.width = width;
		this.height = height;
		this.nLives = nLives;
	}
	
	public void setPosition(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public void incPosition(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public int[] getPosition() {
		int pos[] = new int[2];
		pos[0] =x;
		pos[1] = y;
		return pos;
	}
}
