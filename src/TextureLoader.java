import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TextureLoader {
	BufferedImage player;
	BufferedImage explosion;
	ArrayList<BufferedImage> enemies = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> enemy_bullets = new ArrayList<BufferedImage>();

	
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
	
	void EnemyBullets(String[] image_names) {
		BufferedImage prov_e;
		for(String image_name: image_names) {
			try {
		        prov_e= ImageIO.read(new File("/SpaceInvaders/data/images/" + image_name + ".png"));
		        enemy_bullets.add(prov_e);
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
	
	
	TextureLoader(String player, String[] enemies, String[] enemy_bullets, String explosion){
		Player(player);
		Enemies(enemies);
		EnemyBullets(enemy_bullets);
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
	
	BufferedImage[] getEnemyBulletsImages() {
		BufferedImage[] b = new BufferedImage[2];
		b[0] = enemy_bullets.get(0);
		b[1] = enemy_bullets.get(1);
		return b;
	}
	
	BufferedImage[] getExplosionImage() {
		BufferedImage[] ex = new BufferedImage[1];
		ex[0] = explosion;
		return ex;
	}
}
