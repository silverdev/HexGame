package com.sam.hex;

import java.awt.Shape;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Global {
public static int gridSize=7;
public static int windowHeight=600;
public static BufferedImage backround;
public static int windowWidth=800;
public static RegularPolygonGameObject[][] gamePeace = new RegularPolygonGameObject[0][0];
public static Shape[][] hexes = gamePeace;
//public static Color[][] backround;
public static void set(int gS,int wH,int wW) {
	if (gS>0&&gS%2==1)  gridSize=gS;
	if (wH>10) windowHeight=wH;
	if (wW>10) windowWidth=wW;
	gamePeace = new RegularPolygonGameObject[gridSize][gridSize];
	hexes = gamePeace;
	backround= new BufferedImage(windowWidth,windowHeight, BufferedImage.TYPE_INT_ARGB ) ;
//	backround=new Color[windowWidth][windowHeight];
}



	//public static int windowHeight=200;
//	public static int windowWidth=400;
}
