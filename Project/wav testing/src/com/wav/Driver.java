package com.wav;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
	
	public final static int FRAMERATE = 120;
	
	public static void main(String[] args) {

		String wavFileL = "src/res/untitled.wav";
		
		// Exporting info of wav
		
		try {

			// Open the wav file specified as the first argument
			WavFile wavFile = WavFile.openWavFile(new File(wavFileL));
			
			// Display information about the wav file
			wavFile.display();

			// Get the number of audio channels in the wav file
			int numChannels = wavFile.getNumChannels();

			// Create a buffer of 100 frames
			double[] buffer = new double[100 * numChannels];

			int framesRead, count = 0, counting = 0;
			
			long framesToCalc = wavFile.getSampleRate() / FRAMERATE;
			
			System.out.println("Frames to Actually display: " + framesToCalc);

			String write = "src/res/output.txt";
			
			File file = new File("src/res/output.txt");
			
			if (file.exists() && !file.isDirectory()) { 
			    System.out.println("\n File exists, was it deleted? "+file.delete());;
			}
			
			FileWriter f1 = new FileWriter(write, true);
			
			BufferedWriter b1 = new BufferedWriter(f1);
			
			do {
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);

				for (int i = 0; i < framesRead; i++) {
					count ++;
					if(count >= framesToCalc){
						b1.write(String.valueOf(Math.round(buffer[i] * 1000)/1000.0d));
						b1.newLine();
						count = 0;
						counting ++;
					}
				}
			} while (framesRead != 0);
			
			// Close stuff
			b1.close();
			wavFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}