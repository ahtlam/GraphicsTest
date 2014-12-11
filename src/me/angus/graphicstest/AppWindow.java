package me.angus.graphicstest;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class AppWindow {
    
    private JFrame frame;
    private Canvas canvas;
    
    private BufferStrategy buffer;
    
    private int width, height;
    
    public AppWindow(int width, int height) {
        this.width = width;
        this.height = height;
        
        init();
    }
    
    public void init() {
        frame = new JFrame("GraphicsTest");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setResizable(false);
        
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas = new Canvas();
        
        frame.add(canvas);
        frame.pack();
        
        frame.setVisible(true);
        
        canvas.createBufferStrategy(2);
        this.buffer = canvas.getBufferStrategy();
        
    }
    
    public BufferStrategy getBuffer() {
        return this.buffer;
    }
    
    public Graphics getGraphics() {
        return this.buffer.getDrawGraphics();
    }
    
}
