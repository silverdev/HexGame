package com.sam.hex;


import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import sl.shapes.*;



public class HexGameWindow extends JFrame {
    Canvas cPolygons = new Canvas();
    
    public HexGameWindow() {
        super("Hexgrid");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        
       
        getContentPane().add(cPolygons, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 5, 5));
        setSize(800, 100);
       initRegular();

       
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
    	HexGameWindow fr = new HexGameWindow();
        fr.setVisible(true);
    }

    protected void initRegular() {
    	double radius;
    	Shape[][] hexes = new Shape[7][7];
        radius =BoardTools.radiusCalculator(100,800, 7);
        double hrad= radius*Math.sqrt(3)/2; //Horizontal radius
        for(int xc =0; xc<hexes.length;xc++){
        	for(int yc =0; yc<hexes[0].length;yc++)
        	hexes[xc][yc] = new RegularPolygon( (int) (hrad+yc*hrad+2*hrad*xc), (int) (1.5*radius*yc+radius), (int) radius, 6, Math.PI / 2);
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

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(color);
            for (int i = 0; i < hexes.length; i++) {
            	for(int q=0; q<hexes[i].length; q++)
                ( (Graphics2D) g).draw(hexes[i][q]);

            }
 
        }
    }
}
