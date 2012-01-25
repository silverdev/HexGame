package com.sam.hex;

import java.util.ArrayList;
import java.util.List;



public class GameAI implements PlayingEntity { 
	byte team;
	byte difficalty;
	byte[][] gameBoard;
	int[] n={0,0},m = {0,0};//n is the leftmost AI move, m is the rightmost AI move
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ArrayList<List<List<Integer>>> pairs = new ArrayList();//List of pair-pieces
	
	public GameAI(byte teamNumberT,byte difficaltyT){
		team=teamNumberT;
		difficalty=difficaltyT;
		
	}

	public void getPlayerTurn(byte[][] gameBoard) { // for net play

		 this.gameBoard=gameBoard;
		 makeMove();
	}

	@Override
	public void getPlayerTurn() { // with out net play
		this.gameBoard=BoardTools.teamGrid();
		makeMove();
	}

	public void makeMove(){
		/**
		 * Will's AI
		 * */
		
		//Play in the middle if possible
		int mid = (gameBoard.length-1)/2;
		if(gameBoard[mid][mid]==0){
			n[0] = mid;//horizontal
			n[1] = mid;//vertical
			m[0] = mid;
			m[1] = mid;
			Global.gamePiece[mid][mid].setTeam(team);
		}
		else if( ((n[0]-2) < 0) && ((m[0]+2) > gameBoard.length-1)){
			boolean played = false;
			//Check if one of our pairs is being attacked, and fill in the alternate if so
			for(int x=0; x<pairs.size(); x++){
				if(gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]!=0){
					Global.gamePiece[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)].setTeam(team);
					pairs.remove(x);
				}
				else if(gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]!=0){
					Global.gamePiece[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)].setTeam(team);
					pairs.remove(x);
				}
			}
			//Play a random pair
			if(!played){
				Global.gamePiece[pairs.get(0).get(1).get(0)][pairs.get(0).get(1).get(1)].setTeam(team);
				pairs.remove(0);
			}
		}
		else{
			//Check if one of our pairs is being attacked, and fill in the alternate if so
			for(int x=0; x<pairs.size(); x++){
				if(gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]!=0){
					Global.gamePiece[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)].setTeam(team);
					pairs.remove(x);
				}
				else if(gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]!=0){
					Global.gamePiece[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)].setTeam(team);
					pairs.remove(x);
				}
			}
			
			//Check if we should extend to the left
			if((n[0]-2) > 0){
				if(gameBoard[n[1]-1][n[0]-1]!=0){
					Global.gamePiece[n[1]+1][n[0]-2].setTeam(team);
					ArrayList<List<Integer>> pair = new ArrayList();
					ArrayList<Integer> cord = new ArrayList();
					//TODO Get cords for pair-pieces
					cord.add(0);
					pair.add(cord);
					cord.clear();
					cord.add(0);
					pair.add(cord);
					pairs.add(pair);
				}
				else if(gameBoard[n[1]+1][n[0]-2]!=0){
					Global.gamePiece[n[1]-1][n[0]-1].setTeam(team);
					ArrayList<List<Integer>> pair = new ArrayList();
					ArrayList<Integer> cord = new ArrayList();
					//TODO Get cords for pair-pieces
					cord.add(0);
					pair.add(cord);
					cord.clear();
					cord.add(0);
					pair.add(cord);
					pairs.add(pair);
				}
			}
			
			//Check if we should extend to the right
			if((m[0]+2) > gameBoard.length-1){
				if(gameBoard[m[1]-1][m[0]+2]!=0){
					Global.gamePiece[m[1]+1][m[0]+1].setTeam(team);
					ArrayList<List<Integer>> pair = new ArrayList();
					ArrayList<Integer> cord = new ArrayList();
					//TODO Get cords for pair-pieces
					cord.add(0);
					pair.add(cord);
					cord.clear();
					cord.add(0);
					pair.add(cord);
					pairs.add(pair);
				}
				else if(gameBoard[m[1]+1][m[0]+1]!=0){
					Global.gamePiece[m[1]-1][m[0]+2].setTeam(team);
					ArrayList<List<Integer>> pair = new ArrayList();
					ArrayList<Integer> cord = new ArrayList();
					//TODO Get cords for pair-pieces
					cord.add(0);
					pair.add(cord);
					cord.clear();
					cord.add(0);
					pair.add(cord);
					pairs.add(pair);
				}
			}
		}
		
		/*
>>>>>>> 16cd4a96f5bfb3f12819a9302f8097525e26e952
		int moves=1;
		for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]==0) moves++;
			}
		}
		moves*=Math.random();
		for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]==0) {
					moves--;
				}
				if(moves==0) {
					Global.gamePiece[x][y].setTeam(team);
					moves=-10;
				}	
			}
		}*/
	}
	
}

