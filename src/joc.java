import java.awt.Color;
import java.util.ArrayList;

public class joc {
	Finestra f;
	cotxe c[];
	Sprite s;
	Player p;
	Enemy enemies[][];
	
	ArrayList<Bullet> p_bullets = new ArrayList<Bullet>();
	
	Sound song;

	//Timers per moviment i dispars
	long delay = 1000;
	long fire_delay = 1000;
	long move_time = 0;
	long fire_time = 0;
	
	//Variables de moviment
	int side_speed = 10;
	int down_speed = 13;
	int dir = -1;
	boolean w_down;
	
	//Distancia entre naus enemigues
	int[] e_space = {30,30};
	
	//Tamany naus enemigues
	int[] e_size = {30,30};
	
	//Tamany bales
	int[] b_size = {10,20};
	
	//Variables per controlar timers
	long delta_time;
	long last_time;
	
	joc(Finestra f){
		this.f = f;
	}
	
	void run() {
		inicialitzacio();
		while(true) {
			updateTimers();
			detectarXocs();
			updateEnemies();
			pintarPantalla();
			sleep();
		}
	}
	
	private void updateTimers() {
		delta_time = System.currentTimeMillis()-last_time;
		last_time = System.currentTimeMillis();
		move_time += delta_time;
		fire_time += delta_time;
	}

	private void detectarXocs() {
		int resta_x;
		int resta_y;
		
		int index_x;
		int index_y;		
		
		int[] f_enemy_pos = enemies[0][0].getPosition();
		
		for(Bullet p_bullet: p_bullets) {
			p_bullet.move();
			int[] bullet_pos = p_bullet.getPos();
			resta_x = bullet_pos[0] - f_enemy_pos[0];
			resta_y = bullet_pos[1] - f_enemy_pos[1];
			if(resta_x+b_size[0]>0 && resta_y+b_size[1]>0) {
				index_x = (resta_x+e_space[0]/2)/(e_space[0]+e_size[0]);
				index_y = (resta_y+e_space[1]/2)/(e_space[1]+e_size[1]);
				if(index_x<enemies.length && index_y<enemies[0].length) {
					enemies[index_x][index_y].handleColision(bullet_pos, b_size);
				}
			}
		}

	}
	
	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	
	private void pintarPantalla() {
		f.g.setColor(Color.white);
		f.g.fillRect(0,0, f.AMPLE,f.ALT);
		for(int  i=0; i<c.length;i++)
			c[i].pinta(f.g);
		p.mostraImatge(f.g);
		showEnemies();
		for(Bullet p_bullet: p_bullets) p_bullet.pintar(f.g);
		f.repaint();
	}
	
	private void updateEnemies() {
		if(delay<move_time) {
			//Posició del primer alien de la fila
			int pos_p[] = enemies[0][0].getPosition();
			//Posició de l'últim alien de la fila
			int pos_f[] = enemies[enemies.length-1][0].getPosition();
			//Tractar el cas en que estigui a un borde
			if(pos_p[0]<10 || pos_f[0]>f.AMPLE-10-enemies[0][0].width) downMove();
			else sideMove();
			move_time = 0;
		}
	}
	
	private void downMove() {
		if(!w_down) {
			for(int i = 0; i<enemies.length; i++) {
				for(int j = 0; j < enemies[0].length; j++) {
					enemies[i][j].incPosition(0, down_speed);
				}
			}
			w_down = true;
		}
		else {
			dir*=-1;
			w_down = false;
			for(int i = 0; i<enemies.length; i++) {
				for(int j = 0; j < enemies[0].length; j++) {
					enemies[i][j].incPosition(dir*side_speed, 0);
				}
			}
		}
	}
	
	public void sideMove() {
		for(int i = 0; i<enemies.length; i++) {
			for(int j = 0; j < enemies[0].length; j++) {
				enemies[i][j].incPosition(dir*side_speed, 0);
			}
		}
	}
	

	private void inicialitzacio() {
		last_time = System.currentTimeMillis();
		c = new cotxe[3];
		for(int  i=0; i<c.length;i++)
			c[i]= new cotxe(30,50+80 * i, 2 *(i+1));
		song = new Sound("mixkit-retro-emergency-tones-2971.wav",2);
		//song.play();
		p = new Player(300,f.getHeight()-100, 100, 100, 3);
		initEnemies();
	}
	
	//S'haura de canviar per quan implementi modificar el tamany de la finestra
	public void KeyPressed(char key) {
		int pos[] = p.getPosition();

		switch(key) {
			case 'a':
				if(pos[0]>10) p.incPosition(-2,0);
				else p.setPosition(8, pos[1]);
				break;
			case 'd':
				if(pos[0]<690) p.incPosition(2,0);
				else p.setPosition(692, pos[1]);
				break;
			case ' ':
				if(fire_time>fire_delay) {
					p_bullets.add(new Bullet(pos[0] + p.width/2, pos[1], 10, 30, true));
					fire_time = 0;
				}
				break;
		}
	}
	
	//Fer dependre del tamany de la pantalla
	public void initEnemies() {
		dir = -1;
		w_down = false;
		enemies = new Enemy[4][5];
		for(int i =0 ; i<4; i++) {
			for(int j = 0; j<5; j++ ) {
				enemies[i][j] = new Enemy(84 + i*(e_size[0] + e_space[0]), 36+ j*(e_size[1] + e_space[1]), 
											e_size[0],e_size[1], 1);
			}
		}
	}
	
	public void showEnemies() {
		for(int i =0 ; i<4; i++) {
			for(int j = 0; j<5; j++ ) {
				enemies[i][j].mostraImatge(f.g);
			}
		}
	}	
}
