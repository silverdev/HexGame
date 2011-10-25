package com.sam.hex;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import sl.shapes.RegularPolygon;

public class RegularPolygonGameObject implements Shape  {
 
 RegularPolygon Hex;
 private byte teamNumber; //1 is left-right, 2 is top-down
 private Color objectColor=Color.white;
 boolean checkedflage=false;
 
 
 
 
	public RegularPolygonGameObject(int x, int y, int r, int vertexCount) {
		Hex =new RegularPolygon(x, y, r, vertexCount);
		
		
	}
	public RegularPolygonGameObject(int x, int y, int r, int vertexCount, double startAngle) {
		Hex =new RegularPolygon(x, y, r, vertexCount,startAngle);
	}
	
	public void update(int x, int y, int r, int vertexCount, double startAngle){
		Hex =new RegularPolygon(x, y, r, vertexCount,startAngle);
	}
	
	public void setTeam(byte t){
		teamNumber=t;
		if (teamNumber==1) setColor(Color.blue);
		else setColor(Color.red);
	}

	
	public byte getTeam(){
		return teamNumber;
	}
	
	public boolean checkpece(byte team,int x, int y,RegularPolygonGameObject[][] gamePeace){
		if (team==teamNumber&&!checkedflage){checkedflage=!checkedflage; if(checkSpot(team,x,y)||checkWinTeam(team,x,y,gamePeace)){objectColor=Color.green; return true;}}
		return false;
		
	}
	
	
	public static boolean checkWinTeam(byte team,int x, int y,RegularPolygonGameObject[][] gamePeace){
		if (y<gamePeace.length&&x-1>=0&&gamePeace[x-1][y].checkpece(team,x-1,y,gamePeace)){return true;}
		if (y<gamePeace.length&&x+1<gamePeace.length&&gamePeace[x+1][y].checkpece(team,x+1,y,gamePeace)){return true;}
		if (x<gamePeace.length&&y-1>=0&&gamePeace[x][y-1].checkpece(team,x,y-1,gamePeace)){return true;}
		if (x<gamePeace.length&&y+1<gamePeace.length&&gamePeace[x][y+1].checkpece(team,x,y+1,gamePeace)){return true;}
		if (y+1<gamePeace.length&&x-1>=0&&gamePeace[x-1][y+1].checkpece(team,x-1,y+1,gamePeace)){return true;}
		if (y-1<gamePeace.length&&x+1<gamePeace.length&&y-1>=0&&gamePeace[x+1][y-1].checkpece(team,x+1,y-1,gamePeace)){return true;}
			
  
		return false;
	} 
	public static boolean checkSpot(byte team,int x, int y){
		if (team==1&&x==0){return true;}
		if (team==2&&y==0){return true;}
		return false;
	}

	public void setColor(Color c){
		objectColor=c;
	}
	public Color getColor(){
		return objectColor;
	}
	@Override
	public boolean contains(Point2D p) {
		
		return Hex.contains(p);
	}
	@Override
	public boolean contains(Rectangle2D r) {
		
		return Hex.contains(r);
	}
	@Override
	public boolean contains(double x, double y) {
		
		return Hex.contains(x,y);
	}
	@Override
	public boolean contains(double x, double y, double w, double h) {
		
		return Hex.contains(x,y,w,h);
	}
	@Override
	public Rectangle getBounds() {
		
		return Hex.getBounds();
	}
	@Override
	public Rectangle2D getBounds2D() {
		
		return Hex.getBounds2D();
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		
		return Hex.getPathIterator(at);
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		
		return Hex.getPathIterator(at,flatness);
	}
	@Override
	public boolean intersects(Rectangle2D r) {
		
		return Hex.intersects(r);
	}
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		
		return Hex.intersects(x,y,w,h);
	}
}
