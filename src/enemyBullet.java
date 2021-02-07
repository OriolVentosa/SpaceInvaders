import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class enemyBullet extends Bullet{
	int index = 0;
	long timer = 80;
	long c_time = 0;
	BufferedImage[] bullet_images;
	
	enemyBullet(int x, int y, int width, int height, int speed, BufferedImage[] bullet_images) {
		super(x, y, width, height, speed);
		this.bullet_images = bullet_images;
		// TODO Auto-generated constructor stub
	}
	
	void updateTimer(long delta_time) {
		c_time+=delta_time;
		if(c_time>timer) {
			if(index == 0) index = 1;
			else index = 0;
			c_time = 0;
		}
	}

	void pintar(Graphics g)
	{
		if(!alive) return;
		Graphics2D g2 = (Graphics2D)g;
		
		TexturePaint paint = new TexturePaint(bullet_images[index],
				new Rectangle2D.Double(x,y,width,height));
		
		g2.setPaint(paint);
		g2.fillRect(x, y, width, height);
	}
}
