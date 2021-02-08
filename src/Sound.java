import java.io.File; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 


public class Sound {
	
	Long currentFrame;
	
	//Clip carrega l'audio en comptes de fer stream del fitxer directament, de manera que es pot accedir a
	//qualsevol segment del audio
	Clip clip;
	
	String filePath;
	int nLoops;
	
	AudioInputStream audioInputStream;
	
	//nLoops indica el nombre de vegades que es repeteix el so, -1 perque sigui infinit
	public Sound(String soundName, int nLoops) {
		try {
			filePath = "/SpaceInvaders/data/sounds/" + soundName;
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			this.nLoops = nLoops;
			clip.stop();
		}
		catch(Exception e) {
			System.out.println("Error carregant audio " + soundName);
		}
	}
	
	public void play() {
		clip.loop(nLoops);
	}

	public void restart() {
		clip.stop();
		clip.setMicrosecondPosition(0);
		this.play();
	}
	
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	
}
