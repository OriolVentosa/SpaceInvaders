import java.awt.Color;

public class joc {
	Finestra f;
	cotxe c[];
	Sprite s;
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
		s.mostraImatge(f.g);
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
		s = new Sprite("yes.PNG",100,300, 100, 300);
		song = new Sound("mixkit-retro-emergency-tones-2971.wav",2);
		//song.play();
	}
}
