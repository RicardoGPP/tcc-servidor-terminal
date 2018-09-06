package br.faj.tcc.util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Som
{
	private static final String DIRETORIO = "res";
	
	public static void tocar(String arquivo) throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException
	{
		Clip clip = AudioSystem.getClip();
		File file = new File(DIRETORIO + "/" + arquivo);
		AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		clip.open(stream);
		clip.loop(0);
	}
}
