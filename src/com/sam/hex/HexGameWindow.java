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
        setSize(800, 600);
       initRegular();

       
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
    	HexGameWindow fr = new HexGameWindow();
        fr.setVisible(true);
    }

    protected void initRegular() {
    	double radus;
    	Shape[][] shapes = new Shape[7][7];
        radus =BoardTools.radusCaluator(600,800, 7);
        for(int xc =0; xc<shapes.length;xc++){
        	for(int yc =0; yc<shapes[0].length;yc++)
        
        	shapes[xc][yc] = new RegularPolygon(0, 0, (int) radus, 6, Math.PI / 2);
        }
        cPolygons.setShapes(shapes, Color.blue);
    }

   

    protected static class Canvas extends JPanel {
        Shape[][] shapes;
        Color color;
        public void setShapes(Shape[][] shapes, Color color) {
            this.shapes = shapes;
            this.color = color;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(color);
            for (int i = 0; i < shapes.length; i++) {
                ( (Graphics2D) g).draw(shapes[0][i]);

            }
 
        }
    }
}
