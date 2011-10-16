package com.sam.hex;


import java.awt.Shape;

import sl.shapes.RegularPolygon;

public class GameAction {

	public static void updateBoard() {
		double radius;
		RegularPolygonGameObject[][] gamePeace = Global.gamePeace;
		 radius =BoardTools.radiusCalculator(HexGameWindow.cPolygons.getWidth(),HexGameWindow.cPolygons.getHeight(), Global.gridSize);
	//	radius = BoardTools.radiusCalculator(400,400, 7);
		double hrad = radius * Math.sqrt(3) / 2; // Horizontal radius
		int yOffset=(int)((HexGameWindow.cPolygons.getHeight()-((3*radius/2)*(gamePeace[0].length-1)+2*radius))/2);
    	int xOffset=(int)((HexGameWindow.cPolygons.getWidth()-(hrad*gamePeace.length*2+hrad*(gamePeace[0].length-1)))/2);
		
		for (int xc = 0; xc < Global.gamePeace.length; xc++) {
			for (int yc = 0; yc < gamePeace[0].length; yc++)
				gamePeace[xc][yc].update( (int)(hrad + yc * hrad + 2
						* hrad * xc)+xOffset, (int) (1.5 * radius * yc + radius)+yOffset,
						(int) radius, 6, Math.PI / 2);
	}
		
				BoardTools.setBackground(Global.windowWidth,Global.windowHeight);
         		HexGameWindow.cPolygons.revalidate();
         		HexGameWindow.cPolygons.repaint();
	
	
	}
}
