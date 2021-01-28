import java.awt.Color;
import java.awt.Graphics;

public class Barrier {
	int width = 13;
	int height = 9;
	int scale;
	boolean not_broken[][];
	int[] forma = {
				0,0,0,0,0,1,1,1,0,0,0,0,0,
				0,0,0,1,1,1,1,1,1,1,0,0,0,
				0,0,1,1,1,1,1,1,1,1,1,0,0,
				0,1,1,1,1,1,1,1,1,1,1,1,0,
				1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,0,0,0,0,0,1,1,1,1,
				1,1,1,0,0,0,0,0,0,0,1,1,1,
				1,1,0,0,0,0,0,0,0,0,0,1,1,
				1,1,0,0,0,0,0,0,0,0,0,1,1,
				};
	int x;
	int y;
	
	int explosion_radius = 10;
	
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
						if(hit) {
							handleExplosinRadius(b_pos, local_pos_x+b_size[0]/2, local_pos_y+b_size[1]/2);
						}
					}
				}
			}
		}
		return hit;		
	}
	
	// Intentar que sembli un cercle, fer destruccio mes aleatoria
	private void handleExplosinRadius(int[] b_pos, int local_pos_x, int local_pos_y) {
		for(int i = -explosion_radius; i < explosion_radius; i++) {
			for(int j = -explosion_radius; j < explosion_radius; j++) {
				if(i*i+j*j<explosion_radius*explosion_radius && (i+j)%5 == 0) {
					if(local_pos_x+i>-1 && local_pos_y + j>-1 && 
					local_pos_x+i<width && local_pos_y + j <height) {
						not_broken[local_pos_x+i][local_pos_y+j] = false;
					}
				}
			}
		}
	}
}
