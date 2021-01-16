import java.awt.Color;

public class joc {
	Finestra f;
	cotxe c[];
	Sprite s;
	Player p;
	Enemy enemies[][];
	
	long delay = 1000;
	int side_speed = 10;
	int down_speed = 13;
	
	long move_time = 0;
	
	int dir = -1;
	boolean w_down;
	Sound song;
	
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
	}

	private void detectarXocs() {
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
		if(key=='a') {
			if(pos[0]>10) p.incPosition(-2,0);
			else p.setPosition(8, pos[1]);
		}
		if(key == 'd') {
			if(pos[0]<690) p.incPosition(2,0);
			else p.setPosition(692, pos[1]);
		}
	}
	
	//Fer dependre del tamany de la pantalla
	public void initEnemies() {
		dir = -1;
		w_down = false;
		enemies = new Enemy[4][5];
		for(int i =0 ; i<4; i++) {
			for(int j = 0; j<5; j++ ) {
				enemies[i][j] = new Enemy(84 + i*2*76, 36+ j*2*36, 76,36, 1);
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
