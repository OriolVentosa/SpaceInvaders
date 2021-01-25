abstract class Entity extends Sprite{
	int nLives;
	Boolean alive = true;
	
	Entity(int x, int y, int width, int height, int nLives, String imageName){
		super(imageName,x,y, width, height);

		this.width = width;
		this.height = height;
		this.nLives = nLives;
	}
	
	public void setPosition(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public void incPosition(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public int[] getPosition() {
		int pos[] = new int[2];
		pos[0] =x;
		pos[1] = y;
		return pos;
	}
	
	public boolean handleCollision(int[] b_pos, int[] b_size, boolean b_alive) {
		if(alive && b_alive) {
			Boolean overlap = true;
			int l1[] = {x,y};
			int l2[] = {b_pos[0], b_pos[1]};
			int r1[] = {x+width,  y+height};
			int r2[] = {b_pos[0]+b_size[0],b_pos[1]+b_size[1]};
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
	
}
