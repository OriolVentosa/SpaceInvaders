import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class joc {
	Finestra f;
	TextureLoader tl;
	cotxe c[];
	Sprite s;
	Player p;
	Enemy enemies[][];
	
	ArrayList<Bullet> p_bullets = new ArrayList<Bullet>();
	ArrayList<Bullet> e_bullets = new ArrayList<Bullet>();
	Sound song;

	//Timers per moviment i dispars
	long delay = 1000;
	long fire_delay = 1000;
	long e_fire_delay = 3000;
	long move_time = 0;
	long fire_time = 0;
	long e_fire_time = 0;
	//Variables de moviment
	int side_speed = 10;
	int down_speed = 13;
	int dir = -1;
	boolean w_down = false;
	
	//Distancia entre naus enemigues
	int[] e_space = {30,30};
	
	//Tamany naus enemigues
	int[] p_size = {30,30};
	int[] e_size = {30,30};
	
	//Tamany bales
	int[] b_size = {4,10};
	
	//Variables per controlar timers
	long delta_time;
	long last_time;
	
	joc(Finestra f){
		this.f = f;
	}
	
	void run() {
		loadTextures();
		inicialitzacio();
		while(true) {
			updateTimers();
			pintarPantalla();
			handleBullets();
			updateEnemies();
			sleep();
		}
	}
	
	void loadTextures() {
		String player = "nau_prov";
		String[] enemies = {"nau_prov"};
		String[] blocks = {"vida_3", "vida_2", "vida_1"};
		tl = new TextureLoader(player, enemies, blocks);
	}
	
	private void inicialitzacio() {
		last_time = System.currentTimeMillis();
		c = new cotxe[3];
		for(int  i=0; i<c.length;i++)
			c[i]= new cotxe(30,50+80 * i, 2 *(i+1));
		song = new Sound("mixkit-retro-emergency-tones-2971.wav",2);
		//song.play();
		p = new Player(300,f.getHeight()-100, p_size[0], p_size[1], 3);
		initEnemies();
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
	
	private void updateTimers() {
		delta_time = System.currentTimeMillis()-last_time;
		last_time = System.currentTimeMillis();
		move_time += delta_time;
		fire_time += delta_time;
		e_fire_time += delta_time;
	}

	private void handleBullets() {
		playerBullets();
		enemyBullets();
	}
	
	private void playerBullets() {
		int resta_x;
		int resta_y;
		
		int index_x;
		int index_y;		
		
		int[] f_enemy_pos = enemies[0][0].getPosition();
		
		for(Bullet p_bullet: p_bullets) {
			p_bullet.move(1);
			p_bullet.pintar(f.g);
			int[] bullet_pos = p_bullet.getPos();
			resta_x = bullet_pos[0] - f_enemy_pos[0];
			resta_y = bullet_pos[1] - f_enemy_pos[1];
			if(resta_x+b_size[0]>0 && resta_y+b_size[1]>0) {
				index_x = (resta_x+e_space[0]/2)/(e_space[0]+e_size[0]);
				index_y = (resta_y+e_space[1]/2)/(e_space[1]+e_size[1]);
				if(index_x<enemies.length && index_y<enemies[0].length) {
					boolean has_hit = enemies[index_x][index_y].handleCollision(bullet_pos, b_size, p_bullet.alive);
					if(has_hit) p_bullet.alive =false;
				}
			}
		}
		p_bullets.removeIf(bullet -> bullet.alive == false);
	}
	
	private void enemyBullets() {
		for(Bullet e_bullet: e_bullets) {
			e_bullet.move(-1);
			e_bullet.pintar(f.g);
			int[] bullet_pos = e_bullet.getPos();
			boolean hasHit = p.handleCollision(bullet_pos, b_size, e_bullet.alive); 
			if(hasHit) e_bullet.alive = false;
		}
		
		e_bullets.removeIf(bullet-> bullet.alive == false);
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
		
		if(e_fire_delay<e_fire_time) {
			ArrayList<int[]> e_alive = new ArrayList<int[]>();
			//Mirar quins enemics estan vius
			for(int i = 0; i<enemies.length; i++) {
				for(int j = 0; j < enemies[0].length; j++) {
					if(enemies[i][j].alive == true) {
						int[] index= {i,j};
						e_alive.add(index);
					}
				}
			}
			if(e_alive.size()>0) {
			    Random rand = new Random();
			    int[] randomElement = e_alive.get(rand.nextInt(e_alive.size()));
			    int[] pos = enemies[randomElement[0]][randomElement[1]].getPosition();
				e_bullets.add(new Bullet(pos[0] + p.width/2, pos[1], b_size[0], b_size[1], true));
			}
			e_fire_time = 0;
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
					p_bullets.add(new Bullet(pos[0] + p.width/2, pos[1], b_size[0], b_size[1], false));
					fire_time = 0;
				}
				break;
		}
	}
	
	private void pintarPantalla() {
		f.g.setColor(Color.white);
		f.g.fillRect(0,0, f.AMPLE,f.ALT);
		for(int  i=0; i<c.length;i++)
			c[i].pinta(f.g);
		p.mostraImatge(f.g);
		showEnemies();
		f.repaint();
	}
	
	public void showEnemies() {
		for(int i =0 ; i<4; i++) {
			for(int j = 0; j<5; j++ ) {
				enemies[i][j].mostraEnemic(f.g);
			}
		}
	}
	
	private void sleep() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	
}
