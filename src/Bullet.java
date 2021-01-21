import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	int x,y;
	int width = 10;
	int height = 30;
	int speed = 10;
	
	boolean alive = true;

	//Si es del enemic
	Boolean enemy;
	
	Bullet(int x, int y, int width, int height, Boolean enemy){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enemy = enemy;
	}
	
	void move() {
		y-=speed;
	}
	
	void pintar(Graphics g){
		if(!alive) return;
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
	}
	
	int[] getPos() {
		int[] pos = {x,y};
		return pos;
	}
}
