
public class Entity {
	int nLives;
	Boolean alive;
	Sprite sprite;
	int x,y,width, height;
	
	Entity(int x, int y, int width, int height, int nLives, String imageName){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.nLives = nLives;
		this.sprite = new Sprite(imageName,x,y, width, height);
	}
}
