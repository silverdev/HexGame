package com.sam.hex;

import java.util.ArrayList;
import java.util.List;



public class GameAI implements PlayingEntity { 
	byte team;// 1 is left-right, 2 is top-down
	byte difficalty;
	byte[][] gameBoard;
	int[] n={BoardTools.teamGrid().length-1,BoardTools.teamGrid().length-2},m = {0,0};//n is the leftmost AI move, m is the rightmost AI move
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ArrayList<List<List<Integer>>> pairs = new ArrayList();//List of pair-pieces
	//GRAAAGHAGHBAGBLAKSJHLDF
	
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
		willMove();
	}
	private void willMove(){
		/**
		 * Will's AI
		 * */
		//Still needs fixing: Not creating pair on wall, unable to detect when pairs are attacked, STILL overwriting player
		
		//Play in the middle if possible
		int mid = (gameBoard.length-1)/2;
		if(gameBoard[mid][mid]==0){
			n[0] = mid;//horizontal
			n[1] = mid;//vertical
			m[0] = mid;
			m[1] = mid;
			Global.gamePiece[mid][mid].setTeam(team);
			
			return;
		}
		else if(gameBoard[mid][mid]!=0 && gameBoard[mid][mid]!=team && gameBoard[mid+1][mid]==0){
			n[0] = mid+1;//horizontal
			n[1] = mid;//vertical
			m[0] = mid+1;
			m[1] = mid;
			Global.gamePiece[mid+1][mid].setTeam(team);
			
			return;
		}
		
		//Check if one of our pairs is being attacked, and fill in the alternate if so 
		for(int x=0; x<pairs.size(); x++){ 
			if(gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]!=0 && gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]!=team && gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]==0){
				Global.gamePiece[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)].setTeam(team);
				pairs.remove(x);
				return;
			}
			else if(gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]!=0 && gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]!=team && gameBoard[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)]==0){
				Global.gamePiece[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)].setTeam(team);
				pairs.remove(x);
				return;
			}
		}
		//Check if we should extend to the left (up?)
				if((n[1]-1) > 0){ 
					if(gameBoard[n[0]+1][n[1]-2]==0){
						Global.gamePiece[n[0]+1][n[1]-2].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();
						
						cord.add(n[0]);
						cord.add(n[1]-1);
						pair.add(cord);
						cord.clear();
						
						cord.add(n[0]+1);
						cord.add(n[1]-1);
						pair.add(cord);
						pairs.add(pair);
						
						n[1] = n[1]-2;
						n[0] = n[0]+1;
						
						return;
					}
					else if(gameBoard[n[0]-1][n[1]-1]==0){ //Should check for more spaces!
						Global.gamePiece[n[0]-1][n[1]-1].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();
						
						cord.add(n[0]);
						cord.add(n[1]-1);
						pair.add(cord);
						cord.clear();
						
						cord.add(n[0]-1);
						cord.add(n[1]);
						pair.add(cord);
						pairs.add(pair);
						
						n[1] = n[1]-1;
						n[0] = n[0]-1;
						
						return;
					}
				}
				
				//Check if we should extend to the right (down?)
				if((m[1]+1) < gameBoard.length-1){ 
					if(gameBoard[m[0]-1][m[1]+2]==0){
						Global.gamePiece[m[0]-1][m[1]+2].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();
						//TODO Get cords for pair-pieces
						
						cord.add(m[0]-1);
						cord.add(m[1]+1);
						pair.add(cord);
						cord.clear();
						
						cord.add(m[0]);
						cord.add(m[1]+1);
						pair.add(cord);
						pairs.add(pair);
						
						m[1] = m[1]+2;
						m[0] = m[0]-1;
						
						return;
					}
					else{ //Also check more than once!
						Global.gamePiece[m[0]+1][m[1]+1].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();
						//TODO Get cords for pair-pieces
						
						cord.add(m[0]);
						cord.add(m[1]+1);
						pair.add(cord);
						cord.clear();
						
						cord.add(m[0]+1);
						cord.add(m[1]);
						pair.add(cord);
						pairs.add(pair);
						
						m[1] = m[1]+1;
						m[0] = m[0]+1;
						
						return;
					}
				}
				
				//Fill in the pairs after we've reached both sides of the map
				if( ((n[1]-2) < 0) && ((m[1]+2) > gameBoard.length-1)){
					//Play a random pair
					Global.gamePiece[pairs.get(0).get(1).get(0)][pairs.get(0).get(1).get(1)].setTeam(team);
					pairs.remove(0);
					
					return;
				}
				
				//We shouldn't have gotten here. Error.
				System.out.println("Oops.");
		
		
	} 
	
	private void badMove(){

		
		int moves=1;
		for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]==0) moves++;
			}
		}
		moves*=Math.random();
		for(int x=0; x<gameBoard.length; x++)
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
	/*  Bah, ignore this for now.
	private void nickMove(){
		int[][] moves=new int[gameBoard.length][gameBoard.length];
		 
		 for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]!=0){
					moves[x][y]=-10000;
				}
				else{
					//Actually calculate value
				}
				if(gameBoard[x][y]!=0 && gameBoard[x][y]!=team){
					
				}
				if(gameBoard[x][y]==team){
					//Look through all pieces and find distance from my piece
					for(int i=0; i<gameBoard.length; i++)
						for(int j=0; j<gameBoard[i].length; j++){
							if(gameBoard[i][j]==0){
								//TODO: calculate distance
								if(team==1){
									
								}
								if(team==2){
									
								}
							}
							
						}
				}
			}
		}
		 
		 int max=moves[0][0];
		 ArrayList<RegularPolygonGameObject> possible=new ArrayList<RegularPolygonGameObject>();
		 possible.add(Global.gamePiece[0][0]);
		 for(int x=0; x<gameBoard.length; x++){
				for(int y=0; y<gameBoard[x].length; y++){
					if (moves[x][y]==max){
						possible.add(Global.gamePiece[x][y]);
					}
					else if(moves[x][y]>max){
						 max=moves[x][y];
						 possible=new ArrayList<RegularPolygonGameObject>();
						 possible.add(Global.gamePiece[x][y]);
					}
				}
			}
		 int move=(int)(possible.size()*Math.random());
		 possible.get(move).setTeam(team);
	} */ 
}

