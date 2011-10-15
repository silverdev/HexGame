package com.sam.hex;

import java.awt.Color;

public class BoardTools {
static double spaceH; //Horizontal
static double spaceV; //Vertical

	public static double radiusCalculator(int w, int h,int n){
		
		spaceV=(((n-1)*3/2)+2);

		spaceH=n+(n-1)/2; //always bigger.
		spaceH=w/(spaceH*Math.sqrt(3));
		spaceV=h/spaceV;
		if (spaceV<spaceH){return spaceV;}
		return spaceH;
		  
	}
	public static void setBackground(int w, int h){
		for(double x=0;x<w;x++){
			for(double y=0;y<h;y++){
				if((y+x)/(((double)h+(double)w))<.5==((double)h/(double)w>y/x)){Global.background.setRGB((int)x,(int)y,Color.blue.getRGB());}else{Global.background.setRGB((int)x,(int)y,Color.red.getRGB());}
				//(((h*w)-h)>y/x)
			}
		}
		
	}
}
