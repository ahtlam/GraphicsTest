/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.angus.graphicstest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class GraphicsTest implements Runnable {
    public AppWindow window = null;
    private boolean isRunning = false;
    
    // These are final variables so we don't have to worry about any other ups/fps for now. We can eventually get it working.
	public static final double FPS_TARGET = 60D; // Amount of frame rendering each second.
	public static final double OPTIMAL_FPS_TIME = 1000000000 / FPS_TARGET; // Determines how many ns are in between frame rendering.
	
	public static int UPS = 0;
	public static int FPS = 0;
	
    public static void main(String[] args) {
        new GraphicsTest();
    }

    public GraphicsTest() {
        window = new AppWindow(800, 600);
        isRunning = true;
        
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        long prevTime = System.nanoTime();
		long clockCounter = System.currentTimeMillis(); // Used to check what the application's FPS and UPS is.
		
		long lag = 0; // Determine how many ns we're behind and apply additional updating if needed.
		
		int updates = 0, frames = 0;
		
		while (isRunning) { // Game loop.
			long currTime = System.nanoTime(); // Find the new time in this new loop.
			long elapsed = currTime - prevTime; // Find the amount of ns passed since the last check.
			
			prevTime = currTime; // Switch the old time with the current time for the next loop.
			lag += elapsed;
			
			process();
			
			// This updates the game accordingly if the system is falling behind the clock, and applies the needed updates.
			// This shouldn't be a problem in smaller games.
			while (lag >= OPTIMAL_FPS_TIME) {
				update();
				lag -= OPTIMAL_FPS_TIME;
				updates++;
			}
			
			render();
			frames++;
			
			// Updates the class's UPS and FPS internal timer.
			long clockNow = System.currentTimeMillis();
			if (clockNow >= clockCounter + 1000) { // Checks if a second has passed.
				clockCounter = clockNow;
				
				FPS = frames;
				UPS = updates;
				
				frames = 0;
				updates = 0;
			}
			
			// Prevent the program from running too fast.
			try {
				Thread.sleep(Math.max(0, (long) ((System.nanoTime() - prevTime + OPTIMAL_FPS_TIME) / 1000000)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
    }

    private void render() {
        Graphics g = window.getGraphics();
        
        try {
        	g.setColor(Color.BLACK); // Black should never be rendered.
            g.fillRect(0, 0, 800, 600);
        	
            g.setColor(Color.getHSBColor(new Random().nextFloat(), 1.0F, 1.0F)); // Pick a random color.
            g.fillRect(0, 0, 800, 600);
            
            g.setColor(Color.getHSBColor(new Random().nextFloat(), 0.5F, 1.0F)); // Pick a random color.
            g.drawString(GraphicsTest.FPS + " fps, " + GraphicsTest.UPS + " ups", 10, 20);

            if (!window.getBuffer().contentsLost()) {
                window.getBuffer().show();
            }

            Thread.yield();
            
        } finally {
            if (g != null) {
                g.dispose();
            }
        }
    }

    private void process() {
    }

    private void update() {
    }
    
}
