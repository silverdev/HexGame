package com.sam.hex;

public class BoardTools {
	static double spaceH;
static double spaceV;

	public static double radusCaluator(int h,int w,int n){
		spaceH=((n-1)*3/2)+2;
		spaceV=n+(n-1)/2; //always bigger.
		spaceH=w/(spaceH*Math.sqrt(3));
		spaceV=h/spaceV;
		if (spaceV>spaceH){return spaceV;}
		return spaceH;
		  
	}
	
}
