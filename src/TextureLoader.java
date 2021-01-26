import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TextureLoader {
	BufferedImage player;
	ArrayList<BufferedImage> enemies = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> blocks = new ArrayList<BufferedImage>();;
	
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
	
	void Blocks(String[] image_names) {
		BufferedImage prov_b;
		for(String image_name: image_names) {
			try {
		        prov_b= ImageIO.read(new File("/SpaceInvaders/data/images/" + image_name +  ".png"));
		        enemies.add(prov_b);
		   } catch (IOException ex) {
		       System.out.println("No s'ha carregat correctament la imatge");
		       System.exit(0);
		   }
		}
	}
	
	TextureLoader(String player, String[] enemies, String[] blocks){
		Player(player);
		Enemies(enemies);
		Blocks(blocks);
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
	
	ArrayList<BufferedImage> getBlocksImages(){
		return blocks;
	}
}
