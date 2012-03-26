package com.sam.hex.ai.bee;

import java.util.HashMap;

public class AIHistoryObject{
	int[][] pieces;
    HashMap lookUpTable;
	
	public AIHistoryObject(int[][] pieces, HashMap lookUpTable) {
		this.pieces = new int[pieces.length][pieces.length];
		for(int i=0;i<pieces.length;i++){
			for(int j=0;j<pieces.length;j++){
				this.pieces[i][j] = pieces[i][j];
			}
		}
		this.lookUpTable = lookUpTable;
	}
}