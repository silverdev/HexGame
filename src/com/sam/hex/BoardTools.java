package com.sam.hex;

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
	
}
