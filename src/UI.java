import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class UI {
	int width, height;
	Graphics2D g2;
	Sprite player;
	
	UI(int width, int height, Graphics g, Font f,  Sprite p){
		this.width=width;
		this.height=height;
		g2 = (Graphics2D)g;
		g2.setFont(f);
		player = p;
	}
	
	void showScore(int score, int hiscore) {
		g2.setColor(Color.white);
		
		//Mostrar score
		String s = String.valueOf(score);
		String s2 = String.valueOf(hiscore);
		
		if(score<10) {
			s = "000"+s;
		}
		else if(score<100) {
			s = "00"+s;
		}
		else if(score<1000) {
			s= "0"+s;
		}
		
		if(hiscore<10) {
			s2 = "000"+s2;
		}
		else if(hiscore<100) {
			s2 = "00"+s2;
		}
		else if(hiscore<1000) {
			s2= "0"+s2;
		}
		
		g2.drawString("SCORE< 1 >  HI-SCORE   SCORE<2>", (int)(width*0.05) , (int)(height*0.1));
		g2.drawString(s, (int)(width*0.10) , (int)(height*0.15));
		g2.drawString(s2, (int)(width*0.40) , (int)(height*0.15));
	}
	
	void showVides(int vides, int scale) {
		int pHealth = vides;
		
		String s = String.valueOf(pHealth);
		g2.drawString(s, (int)(width*0.05) , (int)(height*0.95));
		
		if(pHealth > 1) {
			player.setPos((int)(width*0.10), (int)(height*0.92));
			player.mostraImatge(g2, 0);
		}
		
		if(pHealth > 2) {
			player.setPos((int)(width*0.18), (int)(height*0.92));
			player.mostraImatge(g2, 0);
		}

		g2.setColor(Color.green);
		g2.fillRect(0, (int)(height*0.9), width, scale);
	}
}
