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

	public int makeMove(){
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
			
			return 1;
		}
		
		//Check if one of our pairs is being attacked, and fill in the alternate if so
		for(int x=0; x<pairs.size(); x++){
			System.out.println("Pair listing: "+pairs);
			if(gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]==0 || gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]==0){
				if(gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]!=0){
					System.out.println("Oh no, they played here: "+pairs.get(x).get(0).get(0)+","+pairs.get(x).get(0).get(1));
					System.out.println("I'll be playing here: "+pairs.get(x).get(1).get(0)+","+pairs.get(x).get(1).get(1));
					Global.gamePiece[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)].setTeam(team);
					pairs.remove(x);
					
					return 1;
				}
				else if(gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]!=0){
					System.out.println("Oh no, they played here: "+pairs.get(x).get(1).get(0)+","+pairs.get(x).get(1).get(1));
					System.out.println("I'll be playing here: "+pairs.get(x).get(0).get(0)+","+pairs.get(x).get(0).get(1));
					Global.gamePiece[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)].setTeam(team);
					pairs.remove(x);
					
					return 1;
				}
			}
			else{
				pairs.remove(x);
			}
		}
		
		//Check if they were sneaky and played in front of us
		if(m[0]+2 <= gameBoard.length-1 && gameBoard[m[0]+1][m[1]]!=0){
			if(gameBoard[m[0]+1][m[1]-1]==0){
				Global.gamePiece[m[0]+1][m[1]-1].setTeam(team);
				
				m[0] = m[0]+1;
				m[1] = m[1]-1;
				
				return 1;
			}
			else if(gameBoard[m[0]][m[1]+1]==0){
				Global.gamePiece[m[0]][m[1]+1].setTeam(team);
				
				m[0] = m[0];
				m[1] = m[1]+1;
				
				return 1;
			}
		}
		//Check if they were sneakier and played behind us
		if(n[0]-2 >= 0 && gameBoard[n[0]-1][n[1]]!=0){
			if(gameBoard[n[0]-1][n[1]+1]==0){
				Global.gamePiece[n[0]-1][n[1]+1].setTeam(team);
				
				n[0] = n[0]-1;
				n[1] = n[1]+1;
				
				return 1;
			}
			else if(gameBoard[n[0]][n[1]-1]==0){
				Global.gamePiece[n[0]][n[1]-1].setTeam(team);
				
				n[0] = n[0];
				n[1] = n[1]-1;
				
				return 1;
			}
		}
		
		//Check if we should extend to the left
		if((n[0]-2) >= 0){
			if(gameBoard[n[0]-1][n[1]-1]!=0 && gameBoard[n[0]-2][n[1]+1]==0){
				Global.gamePiece[n[0]-2][n[1]+1].setTeam(team);
				ArrayList<List<Integer>> pair = new ArrayList();
				ArrayList<Integer> cord1 = new ArrayList();
				ArrayList<Integer> cord2 = new ArrayList();
				cord1.add(n[0]-1);
				cord1.add(n[1]);
				pair.add(cord1);
				cord2.add(n[0]-1);
				cord2.add(n[1]+1);
				pair.add(cord2);
				pairs.add(pair);
				
				n[0] = n[0]-2;
				n[1] = n[1]+1;
				
				return 1;
			}
			else if(gameBoard[n[0]-2][n[1]+1]!=0 && gameBoard[n[0]-1][n[1]-1]==0){
				Global.gamePiece[n[0]-1][n[1]-1].setTeam(team);
				ArrayList<List<Integer>> pair = new ArrayList();
				ArrayList<Integer> cord1 = new ArrayList();
				ArrayList<Integer> cord2 = new ArrayList();
				cord1.add(n[0]);
				cord1.add(n[1]-1);
				pair.add(cord1);
				cord2.add(n[0]-1);
				cord2.add(n[1]);
				pair.add(cord2);
				pairs.add(pair);

				n[0] = n[0]-1;
				n[1] = n[1]-1;
				
				return 1;
			}
		}
		
		//Check if we should extend to the right
		if((m[0]+2) <= gameBoard.length-1){
			System.out.println("Heading right");
			if(gameBoard[m[0]+2][m[1]-1]!=0 && gameBoard[m[0]+1][m[1]+1]==0){
				Global.gamePiece[m[0]+1][m[1]+1].setTeam(team);
				ArrayList<List<Integer>> pair = new ArrayList();
				ArrayList<Integer> cord1 = new ArrayList();
				ArrayList<Integer> cord2 = new ArrayList();
				cord1.add(m[0]+1);
				cord1.add(m[1]);
				pair.add(cord1);
				cord2.add(m[0]);
				cord2.add(m[1]+1);
				pair.add(cord2);
				pairs.add(pair);

				m[0] = m[0]+1;
				m[1] = m[1]+1;
				
				return 1;
			}
			else if(gameBoard[m[0]+2][m[1]-1]==0){
				Global.gamePiece[m[0]+2][m[1]-1].setTeam(team);
				ArrayList<List<Integer>> pair = new ArrayList();
				ArrayList<Integer> cord1 = new ArrayList();
				ArrayList<Integer> cord2 = new ArrayList();
				cord1.add(m[0]+1);
				cord1.add(m[1]);
				pair.add(cord1);
				cord2.add(m[0]+1);
				cord2.add(m[1]-1);
				pair.add(cord2);
				pairs.add(pair);

				m[0] = m[0]+2;
				m[1] = m[1]-1;
				
				return 1;
			}
		}
		
		//Extend left if we haven't gone right
		if((n[0]-2) >= 0 && gameBoard[n[0]-2][n[1]+1]==0){
			System.out.println("Heading left");
			Global.gamePiece[n[0]-2][n[1]+1].setTeam(team);
			ArrayList<List<Integer>> pair = new ArrayList();
			ArrayList<Integer> cord1 = new ArrayList();
			ArrayList<Integer> cord2 = new ArrayList();
			cord1.add(n[0]-1);
			cord1.add(n[1]);
			pair.add(cord1);
			cord2.add(n[0]-1);
			cord2.add(n[1]+1);
			pair.add(cord2);
			pairs.add(pair);

			n[0] = n[0]-2;
			n[1] = n[1]+1;
			
			return 1;
		}
		
		//Fill in the edges after we've reached both sides of the map
		if(n[0]-1 == 0){
			if(gameBoard[n[0]-1][n[1]]==0){
				Global.gamePiece[n[0]-1][n[1]].setTeam(team);
				
				n[0] = n[0]-1;
				n[1] = n[1];
				
				return 1;
			}
			else if(gameBoard[n[0]-1][n[1]+1]==0){
				Global.gamePiece[n[0]-1][n[1]+1].setTeam(team);
				
				n[0] = n[0]-1;
				n[1] = n[1]+1;
				
				return 1;
			}
		}
		if(m[0]+1 == gameBoard.length-1){
			if(gameBoard[m[0]+1][m[1]]==0){
				Global.gamePiece[m[0]+1][m[1]].setTeam(team);
				
				m[0] = m[0]+1;
				m[1] = m[1];
				
				return 1;
			}
			else if(gameBoard[m[0]-1][m[1]+1]==0){
				Global.gamePiece[m[0]-1][m[1]+1].setTeam(team);
				
				m[0] = m[0]-1;
				m[1] = m[1]+1;
				
				return 1;
			}
		}
		
		//Fill in the pairs after we've reached both sides of the map
		if( (n[0]-2) < 0 && (m[0]+2) > gameBoard.length-1 && pairs.size() > 0){
			//Play a random pair
			Global.gamePiece[pairs.get(0).get(1).get(0)][pairs.get(0).get(1).get(1)].setTeam(team);
			pairs.remove(0);
			
			return 1;
		}
		else{
			//TODO Add the leftmost and rightmost pieces to win the game (Or just add them as a pair)
		}
		
		//Sam's AI
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
		}
		
		//We shouldn't have gotten here. Error.
		return 0;
	}	
}

