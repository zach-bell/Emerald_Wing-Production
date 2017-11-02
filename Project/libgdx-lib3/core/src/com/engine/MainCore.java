package com.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.GridRendering;

public class MainCore extends ApplicationAdapter {
	
	private GridRendering gridT;
	private PerspectiveCamera camera;			// Will display what is rendered
	
	public static int WIDTH,HEIGHT;
	
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private String instruct = "Keys: + or - for speed | Q or A for noise depth | W or S for noise size | Home to reset";
	private String camInstruct = "Camera: LEFT or RIGHT to rotate | UP or DOWN to move up or down";
	
	public MainCore(int width, int height){
		WIDTH = width;
		HEIGHT = height;
	}
	
	public void create () {
		gridT = new GridRendering();
		
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
		
		camera = new PerspectiveCamera(80,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.set(0f, 10f, 10f);		// Set the camera a few units back
		
		camera.lookAt(0f,0f,0f);				// Look at origin
		
		camera.near = 0.1f;						// This is how near we can see
		camera.far = 100f;						// This is how far we can see
		
		gridT.create(camera);
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		update();
		spriteBatch.begin();
		font.draw(spriteBatch, camInstruct, 5, HEIGHT-10);
		font.draw(spriteBatch, instruct, 5, HEIGHT-30);
		font.draw(spriteBatch, "FPS: "+displayFPS, WIDTH-60, HEIGHT-10);
		spriteBatch.end();
	}
	
	
	private long timer = System.currentTimeMillis();
	private int ups = 0, fps = 0, displayFPS = 0;
	
	private long currentTime;
	
	private long lastTime = System.nanoTime();
	private double nextUpdate = 0;
	
	final double frameToRate = 1000000000.0 / 60.0;
	
	public void update() {
		
		currentTime = System.nanoTime();
		
		nextUpdate += ((currentTime - lastTime) / frameToRate);
		
		lastTime = currentTime;
		
		if(nextUpdate >= 1){
			ups++;
			gridT.render();
			fps++;
			nextUpdate--;
		}
		
		if((System.currentTimeMillis()-timer) >= 1000){
			timer = System.currentTimeMillis();
			System.out.println("Ups = " + ups);
			System.out.println("Fps = " + fps + "\n");
			displayFPS = fps;
			
			fps = 0;
			ups = 0;
		}
	}
	
	public void dispose () {}
}