import java.awt.Color;
import java.awt.Graphics;

public class cotxe {
	int x,y,v;
	int width  = 30;
	int height = 10;
	public cotxe(int x, int y, int v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}
	void moure() {
			x+= v;
	}
	
	void pinta(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x,y, width, height);
		g.drawLine(x+(int)(width*0.75), y, x+(int)(width*0.75), y+height);
	}
}
