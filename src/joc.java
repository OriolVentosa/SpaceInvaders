import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class joc {
	Finestra f;
	//Guarda totes les imatges del joc
	TextureLoader tl;
	//Jugador
	Player p;
	//Imatge del jugador(per les vides)
	Sprite pSprite;
	//UI del joc
	UI g_ui;
	
	Enemy enemies[][] = new Enemy[11][5];
	Barrier[] bars = new Barrier[4];
	
	ArrayList<playerBullet> p_bullets = new ArrayList<playerBullet>();
	ArrayList<enemyBullet> e_bullets = new ArrayList<enemyBullet>();
	
	//Explosions dels monstres
	ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	
	//Sons
	Sound e_death;
	Sound move;
	Sound fast_move;
	Sound last_move;
	Sound shoot;
	Sound p_death;

	//Timer moviment del enemic
	long e_move_delay = 1000;
	long move_time = 0;
	
	//Timer dispar del enemic
	long e_fire_delay = 500;
	long e_fire_time = 0;

	//Timer dispar del jugador
	long fire_delay = 1000;
	long fire_time = 0;
	
	//Velocitat del jugador
	int p_speed = 3;
	
	//Velocitat i direccio dels enemics
	int side_speed = 10;
	int down_speed = 13;
	int dir = -1;
	boolean w_down = false;
	
	//Distancia entre naus enemigues
	int[] e_space = {6,6};
	
	//Tamany naus enemigues
	int[] p_size = {15,10};
	int[] e_size = {15,15};
	
	//Tamany bales
	int[] e_b_size = {2,7};
	int[] p_b_size = {3,5};
	
	//Variables per controlar timers
	long delta_time;
	long last_time;
	
	//Variables explosinons
	long ex_time = 250;
	int ex_size = 15;
	
	//Score
	int score = 0;
	int hiscore = 0;
	
	int e_count = 52;
	
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
		String player = "nau";
		String[] enemies = {"enemy_1_1", "enemy_1_2",
							"enemy_2_1", "enemy_2_2",
							"enemy_3_1", "enemy_3_2"};
		String[] e_bullets = {"bala_1", "bala_2"};
		String explosion = "explosio";
		tl = new TextureLoader(player, enemies,e_bullets, explosion);
	}
	
	private void inicialitzacio() {
		last_time = System.currentTimeMillis();
		p = new Player(f.getWidth()/2,(int)(f.getHeight()*0.8), p_size[0]*f.scale, p_size[1]*f.scale, 3, tl.getPlayerImage());
		pSprite = new Sprite(tl.getPlayerImage(), 0, 0, p_size[0]*f.scale, p_size[1]*f.scale);
		initEnemies();
		initBarriers();
		initUI();
		scaleBullets();
		loadSounds();
	}


	public void initEnemies() {
		dir = -1;
		w_down = false;
		for(int i =0 ; i<enemies.length; i++) {
			for(int j = 0; j<enemies[0].length; j++ ) {
				enemies[i][j] = new Enemy(8 + i*(e_size[0] + e_space[0])*f.scale, (int)(f.getHeight()*0.25)+ j*(e_size[1] + e_space[1])*f.scale, 
											e_size[0]*f.scale,e_size[1]*f.scale, 1,1, tl.getEnemiesImages(1));
			}
		}
	}
	
	public void initBarriers() {
		for(int i = 0; i<bars.length; i++) {
			bars[i] = new Barrier((int) (f.getWidth()*0.15 + i * f.getWidth() * 0.2),(int)(f.getHeight()*0.7),f.scale);
		}
	}
	
	public void initUI() {
		g_ui = new UI(f.getWidth(), f.getHeight(), f.g, f.font, pSprite);
	}
	
	private void scaleBullets() {
		e_b_size[0] = e_b_size[0]*f.scale;
		e_b_size[1] = e_b_size[1]*f.scale;
		p_b_size[1] = p_b_size[1]*f.scale;
	}
	
	private void loadSounds() {
		e_death = new Sound("e_death.wav", 0);
		move = new Sound("move.wav",0);
		fast_move = new Sound("400_move.wav", -1);
		last_move = new Sound("last_move.wav", -1);
		shoot = new Sound("shoot.wav",0);
		p_death = new Sound("p_death.wav",0);
	}
	
	
	private void updateTimers() {
		delta_time = System.currentTimeMillis()-last_time;
		last_time = System.currentTimeMillis();
		move_time += delta_time;
		fire_time += delta_time;
		e_fire_time += delta_time;
		for(Explosion ex: explosions) ex.updateExplosionTime(delta_time);
		for(enemyBullet b: e_bullets) b.updateTimer(delta_time);
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
		boolean has_hit = false;
		for(Bullet p_bullet: p_bullets) {
			p_bullet.move(1);
			p_bullet.pintar(f.g);
			int[] bullet_pos = p_bullet.getPos();
			resta_x = bullet_pos[0] - f_enemy_pos[0];
			resta_y = bullet_pos[1] - f_enemy_pos[1];
			if(resta_x+p_b_size[0]>0 && resta_y+p_b_size[1]>0) {
				index_x = (resta_x+e_space[0]*f.scale/2)/((e_space[0]+e_size[0])*f.scale);
				index_y = (resta_y+e_space[1]*f.scale/2)/((e_space[1]+e_size[1])*f.scale);
				if(index_x<enemies.length && index_y<enemies[0].length) {
					has_hit = enemies[index_x][index_y].handleCollision(bullet_pos, p_b_size, p_bullet.alive);
					if(has_hit) {
						p_bullet.alive =false;
						int[] ex_pos = enemies[index_x][index_y].getPosition();
						explosions.add(new Explosion(tl.getExplosionImage(), ex_pos, ex_time, ex_size * f.scale));
						e_death.restart();
						score+=10;
						e_count+=1;
					}
				}
			}
			for(Barrier b: bars) {
				has_hit = b.handleCollision(bullet_pos, p_b_size, p_bullet.alive, false);
				if(has_hit) p_bullet.alive =false;
				has_hit = false;
			}
		}
		p_bullets.removeIf(bullet -> bullet.alive == false);
	}
	
	private void enemyBullets() {
		boolean has_hit = false;
		for(Bullet e_bullet: e_bullets) {
			e_bullet.move(-1);
			e_bullet.pintar(f.g);
			int[] bullet_pos = e_bullet.getPos();
			has_hit = p.handleCollision(bullet_pos, e_b_size, e_bullet.alive); 
			if(has_hit) {
				e_bullet.alive = false;
				p_death.restart();
			}
			for(Barrier b: bars) {
				has_hit = b.handleCollision(bullet_pos, e_b_size, e_bullet.alive, true);
				if(has_hit) e_bullet.alive =false;
				has_hit = false;
			}
			if(bullet_pos[1]>f.getHeight()*0.8) e_bullet.alive = false;
		}
		e_bullets.removeIf(bullet-> bullet.alive == false);
	}
	
	
	private void updateEnemies() {
		//Fer que es moguin cada vegada mes rapid		
		if(e_move_delay<move_time) {
			moveEnemies();
			if(e_move_delay>400) {
				move.restart();
			}
			else {
				if(e_count<54) {
					move.stop();
					fast_move.play();
				}
				else {
					fast_move.stop();
					last_move.play();
				}

			}
		}
		
		if(e_fire_delay<e_fire_time) {
			enemiesFire();
		}
	}
	
	private void moveEnemies(){
		
		//Variable auxiliar per trencar el bucle quan troba un enemic viu
		boolean b = false;
		
		//Posició del primer alien de la fila
		int pos_p[] = new int [2];
		//Posicio de l'ultim alien de la fila
		int pos_f[] = new int [2];

		for(int i = 0; i<enemies.length; i++) {
			for(int j=0; j<enemies[0].length;j++) {
				if(enemies[i][j].alive) {
					pos_p = enemies[i][j].getPosition();
					b=true;
					break;
				}
			}
			if(b) break;
		}
		b=false;
		
		for(int i = enemies.length-1; i>-1; i--) {
			for(int j=0; j<enemies[0].length;j++) {
				if(enemies[i][j].alive) {
					pos_f = enemies[i][j].getPosition();
					b=true;
					break;
				}
			}
			if(b) break;
		}
		
		if(pos_p[0]<10 || pos_f[0]>f.AMPLE-10-enemies[0][0].width) downMove();
		else sideMove();

		move_time = 0;
	}
	
	private void downMove() {
		if(!w_down) {
			for(int i = 0; i<enemies.length; i++) {
				for(int j = 0; j < enemies[0].length; j++) {
					enemies[i][j].incPosition(0, down_speed);
					enemies[i][j].changeSprite();
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
					enemies[i][j].changeSprite();
				}
			}
		}
	}
	
	public void sideMove() {
		for(int i = 0; i<enemies.length; i++) {
			for(int j = 0; j < enemies[0].length; j++) {
				enemies[i][j].incPosition(dir*side_speed, 0);
				enemies[i][j].changeSprite();
			}
		}
	}
	
	
	private void enemiesFire() {
		ArrayList<int[]> e_alive = new ArrayList<int[]>();
		//Mirar quins enemics estan vius
		for(int i = 0; i<enemies.length; i++) {
			for(int j = 0; j < enemies[0].length; j++) {
				if(enemies[i][j].alive) {
					int[] index= {i,j};
					e_alive.add(index);
				}
			}
		}
		if(e_alive.size()>0) {
			//Agafa un alien viu aleatori
		    Random rand = new Random();
		    int[] randomElement = e_alive.get(rand.nextInt(e_alive.size()));
		    int[] pos = enemies[randomElement[0]][randomElement[1]].getPosition();
			e_bullets.add(new enemyBullet(pos[0] + p.width/2, pos[1], 
					e_b_size[0], e_b_size[1], f.scale * 5, tl.getEnemyBulletsImages()));
		}
		e_fire_time = 0;
	}
	
	//S'haura de canviar per quan implementi modificar el tamany de la finestra
	public void KeyPressed(char key) {
		int pos[] = p.getPosition();
		switch(key) {
			case 'a':
				if(pos[0]>10) p.incPosition(-p_speed*f.scale,0);
				else p.setPosition(8, pos[1]);
				break;
			case 'd':
				if(pos[0]<f.AMPLE-8-p_size[0]*f.scale) p.incPosition(p_speed*f.scale,0);
				else p.setPosition(f.AMPLE-8-p_size[0]*f.scale, pos[1]);
				break;
			case ' ':
				if(fire_time>fire_delay) {
					p_bullets.add(new playerBullet(pos[0] + p.width/2, pos[1], 3, p_b_size[1],f.scale*5));
					fire_time = 0;
					shoot.restart();
				}
				break;
		}
	}
	
	 void pintarPantalla() {
		f.g.setColor(Color.black);
		f.g.fillRect(0,0, f.AMPLE,f.ALT);
		p.mostraImatge(f.g,0);
		showEnemies();
		showExplosions();
		showBarriers();
		showScoreAndLives();
		f.repaint();
	}
	
	private void showEnemies() {
		for(int i =0 ; i<enemies.length; i++) {
			for(int j = 0; j<enemies[0].length; j++ ) {
				enemies[i][j].mostraEnemic(f.g);
			}
		}
	}
	
	private void showBarriers() {
		for(Barrier b: bars) {
			b.showBarrier(f.g);
		}
	}
	
	private void showExplosions() {
		for(Explosion ex: explosions) {
			ex.showExplosion(f.g);
		}
		explosions.removeIf(ex -> ex.dead == true);
	}
	
	void showScoreAndLives() {
		g_ui.showScore(score, hiscore);
		g_ui.showVides(p.getHealth(), f.scale);
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
