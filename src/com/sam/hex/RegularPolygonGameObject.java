package com.sam.hex;

import java.awt.Color;
import sl.shapes.RegularPolygon;

public class RegularPolygonGameObject extends RegularPolygon {
 private byte teamNumber;
 private Color objectColor;
 
	public RegularPolygonGameObject(int x, int y, int r, int vertexCount) {
		super(x, y, r, vertexCount);
		
	}
	public RegularPolygonGameObject(int x, int y, int r, int vertexCount, double startAngle) {
		super(x, y, r, vertexCount);
	}
	public void setTeam(byte t){
		teamNumber=t;
	}
	public static boolean checkWin(int x, int y){
		return false;
	} 
	public void setColor(Color c){
		objectColor=c;
	}
	public Color gitColor(Color c){
		return objectColor;
	}
}
