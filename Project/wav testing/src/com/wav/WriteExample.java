package com.wav;

import java.io.*;

public class WriteExample {
	
	public static void main(String args[]){
		System.out.println("running...");
		try {
			
			String file = "new.wav";
			long sampleRate = 44100;
			double duration = 5;

			// Calculate the number of frames required for specified duration
			long numFrames = (long) (duration * sampleRate);

			// Create a wav file with the name specified as the first argument
			WavFile wavFile = WavFile.newWavFile(new File(file), 2, numFrames, 16, sampleRate);

			// Create a buffer of 100 frames
			double[][] buffer = new double[2][100];
			
			// Initialise a local frame counter
			long frameCounter = 0;
			
			// Loop until all frames written
			while (frameCounter < numFrames) {
				// Determine how many frames to write, up to a maximum of the
				// buffer size
				long remaining = wavFile.getFramesRemaining();
				int toWrite = (remaining > 100) ? 100 : (int) remaining;

				// Fill the buffer, one tone per channel
				for (int s = 0; s < toWrite; s++, frameCounter++) {
					buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
					buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
				}
				
				// Write the buffer
				wavFile.writeFrames(buffer, toWrite);
			}

			// Close the wavFile
			wavFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("finnished!");
		
		
	}
}