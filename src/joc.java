import java.awt.Color;

public class joc {
	Finestra f;
	cotxe c[];
	Sprite s;
	Player p;
	Enemy enemies[][];
	Sound song;
	
	joc(Finestra f){
		this.f = f;
	}
	
	void run() {
		inicialitzacio();
		while(true) {
			ferMoviments();
			detectarXocs();
			pintarPantalla();
			sleep();
		}
	}
	
	private void detectarXocs() {
		// TODO Auto-generated method stub
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
	
	private void ferMoviments() {
		for(cotxe Cotxe: c)
			Cotxe.moure();
	}
	
	private void inicialitzacio() {
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
	
	public void moveEnemies() {
		
	}
	
	
}
