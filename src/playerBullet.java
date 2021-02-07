import java.awt.Color;
import java.awt.Graphics;

public class playerBullet extends Bullet{
	playerBullet(int x, int y, int width, int height, int speed) {
		super(x, y, width, height, speed);
		// TODO Auto-generated constructor stub
	}

	void pintar(Graphics g)
	{
		if(!alive) return;
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
}
