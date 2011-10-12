package com.sam.hex;
import sl.shapes.RegularPolygon;

public class RegularPolygonGameObject extends RegularPolygon {
 byte teamNumber;
 
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
}
