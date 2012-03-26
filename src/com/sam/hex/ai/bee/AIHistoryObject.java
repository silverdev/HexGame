package com.sam.hex.ai.bee;

import java.util.HashMap;

public class AIHistoryObject{
	int[][] pieces;
    HashMap lookUpTable;
	
	public AIHistoryObject(int[][] pieces, HashMap lookUpTable) {
		this.pieces = new int[pieces.length][pieces.length];
		for(int i=0;i<pieces.length;i++){
			this.pieces[i] = pieces[i];
		}
		this.lookUpTable = lookUpTable;
	}
}