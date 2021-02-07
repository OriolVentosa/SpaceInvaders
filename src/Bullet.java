import java.awt.Graphics;

abstract class Bullet {
	int x,y;
	int width = 10;
	int height = 30;
	int speed = 10;
	
	boolean alive = true;

	//Si es del enemic
	
	Bullet(int x, int y, int width, int height, int speed){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
	}
	
	void move(int dir) {
		y-=speed*dir;
	}
	
	abstract void pintar(Graphics g);
	
	int[] getPos() {
		int[] pos = {x,y};
		return pos;
	}
}
