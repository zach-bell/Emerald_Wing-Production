package com.wav;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
	
	public static void main(String[] args) {

		String wavFileL = "src/res/a.wav";
		
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

			int framesRead;
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;

			String write = "src/res/output.txt";
			
			FileWriter f1 = new FileWriter(write, true);
			
			BufferedWriter b1 = new BufferedWriter(f1);
			
			do {
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer, 100);

				// Loop through frames and look for minimum and maximum value
				for (int s = 0; s < framesRead * numChannels; s++) {
					if (buffer[s] > max)
						max = buffer[s];
					if (buffer[s] < min)
						min = buffer[s];
					
					b1.write(String.valueOf(Math.round(buffer[s] * 1000)/1000.0d));
					b1.newLine();
				}
				
			} while (framesRead != 0);
			
			System.out.println("Buffer array length: "+buffer.length);
			b1.close();
			// Close the wavFile
			wavFile.close();

			// Output the minimum and maximum value
			System.out.printf("Min: %f, Max: %f\n", min, max);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}