import java.awt.Graphics;

public class Enemy extends Entity{
	public Enemy(int x, int y, int width, int height, int nLives) {
		super(x,y,width,height,nLives,"nau_prov.png");
	}
	
	public boolean handleCollision(int[] b_pos, int[] b_size) {
		if(alive) {
			Boolean overlap = true;
			int l1[] = {x,y};
			int l2[] = {b_pos[0], b_pos[1]};
			int r1[] = {x+width,  y+height};
			int r2[] = {b_pos[0]+b_size[0],b_pos[1]+b_size[1]};
			//System.out.println("Coord nau. l1:" + x+ " " + y + " r1: " + r1[0] + " " + " " +r1[1]);
			//System.out.println("Coord bala. l2:" + b_pos[0]+ " " + b_pos[1] + " r2: " + r2[0] + " " + " " +r2[1]);
			if (l1[0] > r2[0] || l2[0] > r1[0]) overlap = false;
			if (l1[1] > r2[1] || l2[1] > r1[1]) overlap =false;
			if(overlap) {
				nLives-=1;
				if(nLives == 0) alive = false;
			}
			return overlap;
		}
		return false;	
	}
	
	void mostraEnemic(Graphics g) {
		if(alive) super.mostraImatge(g);
	}
}
