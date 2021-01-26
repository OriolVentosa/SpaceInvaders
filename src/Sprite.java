import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Sprite{
    BufferedImage[] bi;
    int x,y,width,height;
	
	public Sprite(BufferedImage[] images, int x, int y, int width, int height) {
       bi = images;
       
       this.height = height;
       this.width = width;
       this.x = x;
       this.y = y;
	}
	
	public void mostraImatge(Graphics g, int index) {
        Graphics2D g2 = (Graphics2D)g;
        TexturePaint paint = new TexturePaint(bi[index],
                new Rectangle2D.Double(x,y,width,height));
        g2.setPaint(paint);
        g2.fillRect(x, y, width, height);
	}
}
