package com.core;

public class Box1 extends Shapes {
	
	float startingY = -10000;
	float maxY = 10000;
	float toggleFill = 0;
	
	public Box1(SoundScape scape){
		super(scape);
		position.x = scape.random(-scape.w, 0);
		position.y = scape.random(startingY, maxY);
		position.z = scape.random(0, scape.h);
		
		rotation.x = scape.random(0, 1);
	    rotation.y = scape.random(0, 1);
	    rotation.z = scape.random(0, 1);
	    
	    toggleFill = scape.random(0, 1);
	}

	@Override
	public void display(int fillColor, int strokeColor) {
		scape.pushMatrix();	// Start matrix
		
	// We start a matrix to run translate or rotate and not effect the camera
		scape.translate(position.x, position.y, position.z);
		float size = 0;
		if (scape.song.isPlaying()) {
			scape.rotateX(rotationSum.x);
			scape.rotateY(rotationSum.y);
			scape.rotateZ(rotationSum.z);
			size = (75 + scape.intensity) * 2.5f;
		}
		
		if (toggleFill > 0.7) {
			scape.fill(fillColor,255);
			scape.noStroke();
		} else {
			scape.noFill();
			scape.stroke(strokeColor,255);
		}
		scape.box(size);
		
		scape.popMatrix();	// End matrix for shape
		
		position.y += (1+(scape.intensity/4)+(scape.bandsComb/150));
		if (position.y >= maxY) {
			position.x = scape.random(-scape.width, 0);
			position.y = startingY;
			position.z = scape.random(0, scape.height);
		}
	}

	@Override
	public void update() {
		rotationSum.x += scape.intensity*(rotation.x/200);
		rotationSum.y += scape.intensity*(rotation.y/200);
		rotationSum.z += scape.intensity*(rotation.z/200);
	}

}