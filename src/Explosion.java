import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Explosion extends Sprite{
	long ex_time;
	long current_time = 0;
	boolean dead = false;
	
	Explosion(BufferedImage[] ex_image, int[] pos, long ex_time, int ex_size){
		super(ex_image, pos[0], pos[1], ex_size, ex_size);
		this.ex_time = ex_time;
	}
	
	void updateExplosionTime(long delta_time) {
		current_time+=delta_time;
	}
	
	void showExplosion(Graphics g) {
		if(current_time<ex_time) super.mostraImatge(g, 0);
		else dead = true;
	}
}
