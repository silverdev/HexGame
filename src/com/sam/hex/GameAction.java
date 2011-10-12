package com.sam.hex;


import java.awt.Shape;

import sl.shapes.RegularPolygon;

public class GameAction {

	public static void updateBoard() {
		double radius;
		Shape[][] hexes = Global.hexes;
		 radius =BoardTools.radiusCalculator(HexGameWindow.cPolygons.getWidth(),HexGameWindow.cPolygons.getHeight(), 7);
	//	radius = BoardTools.radiusCalculator(400,400, 7);
		double hrad = radius * Math.sqrt(3) / 2; // Horizontal radius
		for (int xc = 0; xc < Global.hexes.length; xc++) {
			for (int yc = 0; yc < hexes[0].length; yc++)
				hexes[xc][yc] = new RegularPolygon((int) (hrad + yc * hrad + 2
						* hrad * xc), (int) (1.5 * radius * yc + radius),
						(int) radius, 6, Math.PI / 2);
	}
		
	
         		HexGameWindow.cPolygons.revalidate();
         		HexGameWindow.cPolygons.repaint();
	
	
	}
}
