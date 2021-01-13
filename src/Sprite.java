import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite{
    BufferedImage bi;
    int x,y,width,height;
	
	public Sprite(String image_name, int x, int y, int width, int height) {
       try {
            bi = ImageIO.read(new File("/SpaceInvaders/data/images/" + image_name));
       } catch (IOException ex) {
           System.out.println("No s'ha carregat correctament la imatge");
           System.exit(0);
       }
       
       this.height = height;
       this.width = width;
       this.x = x;
       this.y = y;
	}
	
	public void mostraImatge(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        TexturePaint paint = new TexturePaint(bi,
                new Rectangle2D.Double(x,y,width,height));
        g2.setPaint(paint);
        g2.fillRect(x, y, width, height);
	}
}
