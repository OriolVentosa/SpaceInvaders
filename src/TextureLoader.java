import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TextureLoader {
	BufferedImage player;
	BufferedImage explosion;
	ArrayList<BufferedImage> enemies = new ArrayList<BufferedImage>();
	
	void Player(String image_name) {
	    try {
	        player = ImageIO.read(new File("/SpaceInvaders/data/images/" + image_name +  ".png"));
	   } catch (IOException ex) {
	       System.out.println("No s'ha carregat correctament la imatge");
	       System.exit(0);
	   }
	}
	
	void Enemies(String[] image_names) {
		BufferedImage prov_e;
		for(String image_name: image_names) {
			try {
		        prov_e= ImageIO.read(new File("/SpaceInvaders/data/images/" + image_name + ".png"));
		        enemies.add(prov_e);
		   } catch (IOException ex) {
		       System.out.println("No s'ha carregat correctament la imatge");
		       System.exit(0);
		   }
		}
	}

	void Explosion(String image_name) {
	    try {
	        explosion = ImageIO.read(new File("/SpaceInvaders/data/images/" + image_name +  ".png"));
	   } catch (IOException ex) {
	       System.out.println("No s'ha carregat correctament la imatge");
	       System.exit(0);
	   }
	}
	
	TextureLoader(String player, String[] enemies, String explosion){
		Player(player);
		Enemies(enemies);
		Explosion(explosion);
	}
	
	BufferedImage[] getPlayerImage() {
		BufferedImage[] p = new BufferedImage[1];
		p[0] = player;
		return p;
	}
	
	BufferedImage[] getEnemiesImages(int type){
		BufferedImage[] e = new BufferedImage[2];
		e[0] = enemies.get((type-1)*2);
		e[1] = enemies.get((type-1)*2+1);
		return e;
	}
	
	BufferedImage[] getExplosionImage() {
		BufferedImage[] ex = new BufferedImage[1];
		ex[0] = explosion;
		return ex;
	}
}
