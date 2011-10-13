package com.sam.hex;


import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import sl.shapes.*;



public class HexGameWindow extends JFrame {
   public static Canvas cPolygons = new Canvas();
    
    public HexGameWindow() {
        super("Hexgrid");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        
       
        getContentPane().add(cPolygons, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 5, 5));
        setSize(Global.windowWidth,Global.windowHeight);
       initRegular();

       
        setLocationRelativeTo(null);
    }
/*
    public static void main(String[] args) {
    	HexGameWindow fr = new HexGameWindow();
        fr.setVisible(true);
        
    }*/

    protected void initRegular() {

    	double radius;
    	Shape[][] hexes=Global.hexes;
        //radius =BoardTools.radiusCalculator(Canvas.HEIGHT,Canvas.WIDTH, 7);
    	radius =BoardTools.radiusCalculator(Global.windowWidth,Global.windowHeight,7);
        double hrad= radius*Math.sqrt(3)/2; //Horizontal radius
        for(int xc =0; xc<Global.hexes.length;xc++){
        	for(int yc =0; yc<hexes[0].length;yc++)
        	hexes[xc][yc] =  new RegularPolygonGameObject( (int) (hrad+yc*hrad+2*hrad*xc), (int) (1.5*radius*yc+radius), (int) radius, 6, Math.PI / 2);
        }
        cPolygons.setShapes(hexes, Color.blue);
    }

   

    protected static class Canvas extends JPanel {
        Shape[][] hexes;
        Color color;
        public void setShapes(Shape[][] shapes, Color color) {
            this.hexes = shapes;
            this.color = color;
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth()-1 , getHeight()-1 );
            g.setColor(Color.black);
            for (int i = 0; i < hexes.length; i++) {
            	for(int q=0; q<hexes[i].length; q++){
            		 g.setColor(((RegularPolygonGameObject)hexes[i][q]).getColor());
                     ( (Graphics2D) g).fill(hexes[i][q]);
                     g.setColor(Color.black);
            		( (Graphics2D) g).draw(hexes[i][q]);
               
            	}

            }
 
        }
    }
}
