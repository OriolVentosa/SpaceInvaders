import java.io.File; 
import java.io.IOException; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class Sound {
	
	Long currentFrame;
	
	//Clip carrega l'audio en comptes de fer stream del fitxer directament, de manera que es pot accedir a
	//qualsevol segment del audio
	Clip clip;
	
	String status;
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
		status = "play";
	}
	
	public void pause() {
		if(status.equals("paused")) {
			return;
		}
		
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	public void resume() {
		if(status.equals("play")) {
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}
	
	public void restart() {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}
	
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	
	public void resetAudioStream() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
			clip.open(audioInputStream);
			clip.loop(nLoops);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
