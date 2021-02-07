import java.awt.Color;
import java.awt.Graphics;

public class Barrier {
	int width = 14;
	int height = 12;
	int scale;
	boolean not_broken[][];
	int[] forma = {
				0,0,0,0,1,1,1,1,1,1,0,0,0,0,
				0,0,0,1,1,1,1,1,1,1,1,0,0,0,
				0,0,1,1,1,1,1,1,1,1,1,1,0,0,
				0,1,1,1,1,1,1,1,1,1,1,1,1,0,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,0,0,0,0,0,0,1,1,1,1,
				1,1,1,0,0,0,0,0,0,0,0,1,1,1,
				1,1,0,0,0,0,0,0,0,0,0,0,1,1,
				1,1,0,0,0,0,0,0,0,0,0,0,1,1};
	
	int explosion_radius = 8;
	
	//No arriba mai a les 4 ultimes files
	int[][] explosion = {
				{1,0,1,0,1,1,1,0,0,0,1,0,0,1,1,1,1},
				{1,1,0,1,0,1,1,0,0,0,1,1,1,0,1,1,1},
				{1,0,1,1,1,1,1,0,0,0,1,1,1,0,1,0,1},
				{1,1,0,0,1,1,1,0,0,0,1,0,1,1,0,1,0},
				{1,1,0,1,0,1,1,0,0,0,0,1,1,0,0,1,1},
				{1,1,0,1,0,1,1,0,0,0,1,0,1,1,1,1,0},
				{1,1,0,1,0,1,0,0,0,0,1,1,0,0,1,0,0},
				{1,0,1,0,1,0,1,0,0,0,0,1,1,1,0,0,1},
				{1,0,1,1,0,1,1,0,0,0,1,1,0,0,1,1,1},
				{1,1,0,1,0,1,1,0,0,0,0,0,1,1,1,0,1},
				{0,1,0,1,1,0,1,0,0,0,1,0,1,1,1,1,1},
				{1,0,1,1,1,1,1,0,0,0,0,1,1,0,1,0,0},
				{1,1,0,0,1,0,1,0,0,0,1,0,1,0,1,1,1},
				{1,0,1,1,1,1,1,0,0,0,0,1,0,1,0,1,1},
				{0,0,1,0,0,1,1,0,0,0,0,1,0,1,0,1,1},
				{1,0,1,0,1,0,1,0,0,0,1,1,0,1,0,1,1},
				{1,1,0,1,1,1,1,0,0,0,1,1,0,1,0,1,1}
	};
	
	int x;
	int y;	
	Barrier(int x, int y, int scale){
		this.x = x;
		this.y = y;
		not_broken = new boolean[width * 2 * scale][height * 2 * scale];
		for(int i = 0; i<width * 2 * scale; i++) { 
			for(int j= 0; j<height * 2 * scale; j++) {
				int index_i  = i/(scale*2);
				int index_j = j/(scale*2);
				int index = index_j*width + index_i;
				if(forma[index] == 1) not_broken[i][j] = true;
			}
		}
		this.scale = scale;
		this.width = width * 2 * scale;
		this.height = height * 2 * scale;
	}
	
	void showBarrier(Graphics g) {
		for(int i = 0; i<width; i++) {
			for(int j= 0; j<height; j++) {
				if(not_broken[i][j]) {
					g.setColor(Color.green);
			        g.fillRect(x+i, y+j, 1, 1);
				}
			}
		}
	}
	
	public boolean handleCollision(int[] b_pos, int[] b_size, boolean b_alive, boolean enemy) {
		if(b_alive) {
			Boolean overlap = true;
			int l1[] = {x,y};
			int l2[] = {b_pos[0], b_pos[1]};
			int r1[] = {x+width,  y+height};
			int r2[] = {b_pos[0]+b_size[0],b_pos[1]+b_size[1]};
			if (l1[0] > r2[0] || l2[0] > r1[0]) overlap = false;
			if (l1[1] > r2[1] || l2[1] > r1[1]) overlap =false;
			if(overlap) {
				overlap = dealDamage(b_pos, b_size, enemy);
			}
			return overlap;
		}
		return false;	
	}
	
	boolean dealDamage(int[] b_pos, int[] b_size, boolean enemy) {
		boolean hit = false;
		int local_pos_x = b_pos[0] - x;
		int local_pos_y = b_pos[1] - y;
		
		for(int i = 0; i < b_size[0]; i++) {
			for(int j = 0 ; j < b_size[1]; j++) {
				if(local_pos_x+i>-1 && local_pos_y + j>-1 && 
				   local_pos_x+i<width && local_pos_y + j <height ) {
					if(not_broken[local_pos_x+i][local_pos_y+j]) {
						not_broken[local_pos_x+i][local_pos_y+j] = false;
						hit = true;
					}
				}
			}
		}
		
		if(hit) {
			//Arreglo cutre perque les bales dels enemics no feien gaire mal a la barrera
			//Nomes agafava la part de més baixa de l'explosio
			if(enemy) {
				local_pos_y += 3+b_size[1]/3;
			}
			handleExplosionRadius(local_pos_x+b_size[0]/2, local_pos_y+b_size[1]/2);
		}
		return hit;		
	}
	
	private void handleExplosionRadius(int local_pos_x, int local_pos_y) {
		int scaled_i;
		int scaled_j;

		for(int i = -explosion_radius*scale; i < explosion_radius*scale; i++) {
			for(int j = -explosion_radius; j < explosion_radius; j++) {
				scaled_i = i/scale;
				scaled_j = j/scale;
				scaled_i = scaled_i+explosion_radius;
				scaled_j = scaled_j+explosion_radius;
				if(explosion[scaled_j][scaled_i]== 0) {
					if(local_pos_x+i>-1 && local_pos_y + j>-1 && 
					local_pos_x+i<width && local_pos_y + j <height) {
						not_broken[local_pos_x+i][local_pos_y+j] = false;
					}
				}
			}
		}
	}
}
