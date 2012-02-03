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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		makeMove();
	}


	public void makeMove(){
		if(team==1)
		willHorizMove();
		else
		willVertMove();
	}
	private void willVertMove(){
		/**
		 * Will's AI
		 * */
		//Still needs fixing: Not creating pair on wall
		
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
						cord=new ArrayList();

						cord.add(n[0]+1);
						cord.add(n[1]-1);
						pair.add(cord);
						pairs.add(pair);

						n[1] = n[1]-2;
						n[0] = n[0]+1;

						if(n[1]-1==0){ //Check if exactly one away from edge
							pair = new ArrayList();
							cord = new ArrayList();

							cord.add(n[0]);
							cord.add(n[1]-1);
							pair.add(cord);
							cord=new ArrayList();

							cord.add(n[0]+1);
							cord.add(n[1]-1);
							pair.add(cord);
							pairs.add(pair);

							n[1] = n[1]-2;
							n[0] = n[0]+1;
						}

						return;
					}
					else if(gameBoard[n[0]-1][n[1]-1]==0){ //Should check for more spaces!
						Global.gamePiece[n[0]-1][n[1]-1].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();
						cord.add(n[0]);
						cord.add(n[1]-1);
						pair.add(cord);
						cord=new ArrayList();

						cord.add(n[0]-1);
						cord.add(n[1]);
						pair.add(cord);
						pairs.add(pair);

						n[1] = n[1]-1;
						n[0] = n[0]-1;

						if(n[1]-1==0){
							pair = new ArrayList();
							cord = new ArrayList();

							cord.add(n[0]);
							cord.add(n[1]-1);
							pair.add(cord);
							cord=new ArrayList();

							cord.add(n[0]+1);
							cord.add(n[1]-1);
							pair.add(cord);
							pairs.add(pair);

							n[1] = n[1]-2;
							n[0] = n[0]+1;
						}
						return;
					}
				}

				//Check if we should extend to the right (down?)
				if((m[1]+1) < gameBoard.length-1){ 
					if(gameBoard[m[0]-1][m[1]+2]==0){
						Global.gamePiece[m[0]-1][m[1]+2].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();

						cord.add(m[0]-1);
						cord.add(m[1]+1);
						pair.add(cord);
						cord=new ArrayList();

						cord.add(m[0]);
						cord.add(m[1]+1);
						pair.add(cord);
						pairs.add(pair);

						m[1] = m[1]+2;
						m[0] = m[0]-1;
						if(m[1]+1==gameBoard.length-1){
							pair = new ArrayList();
							cord = new ArrayList();

							cord.add(m[0]-1);
							cord.add(m[1]+1);
							pair.add(cord);
							cord=new ArrayList();

							cord.add(m[0]);
							cord.add(m[1]+1);
							pair.add(cord);
							pairs.add(pair);

							m[1] = m[1]+2;
							m[0] = m[0]-1;
						}
						return;
					}
					else{ //Also check more than once!
						Global.gamePiece[m[0]+1][m[1]+1].setTeam(team);
						ArrayList<List<Integer>> pair = new ArrayList();
						ArrayList<Integer> cord = new ArrayList();

						cord.add(m[0]);
						cord.add(m[1]+1);
						pair.add(cord);
						cord=new ArrayList();

						cord.add(m[0]+1);
						cord.add(m[1]);
						pair.add(cord);
						pairs.add(pair);

						m[1] = m[1]+1;
						m[0] = m[0]+1;
						if(m[1]+1==gameBoard.length-1){
							pair = new ArrayList();
							cord = new ArrayList();

							cord.add(m[0]-1);
							cord.add(m[1]+1);
							pair.add(cord);
							cord=new ArrayList();

							cord.add(m[0]);
							cord.add(m[1]+1);
							pair.add(cord);
							pairs.add(pair);

							m[1] = m[1]+2;
							m[0] = m[0]-1;
						}
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
	private void willHorizMove(){
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

			return;
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

					return;
				}
				else if(gameBoard[pairs.get(x).get(1).get(0)][pairs.get(x).get(1).get(1)]!=0){
					System.out.println("Oh no, they played here: "+pairs.get(x).get(1).get(0)+","+pairs.get(x).get(1).get(1));
					System.out.println("I'll be playing here: "+pairs.get(x).get(0).get(0)+","+pairs.get(x).get(0).get(1));
					Global.gamePiece[pairs.get(x).get(0).get(0)][pairs.get(x).get(0).get(1)].setTeam(team);
					pairs.remove(x);

					return;
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

				return;
			}
			else if(gameBoard[m[0]][m[1]+1]==0){
				Global.gamePiece[m[0]][m[1]+1].setTeam(team);

				m[0] = m[0];
				m[1] = m[1]+1;

				return;
			}
		}
		//Check if they were sneakier and played behind us
		if(n[0]-2 >= 0 && gameBoard[n[0]-1][n[1]]!=0){
			if(gameBoard[n[0]-1][n[1]+1]==0){
				Global.gamePiece[n[0]-1][n[1]+1].setTeam(team);

				n[0] = n[0]-1;
				n[1] = n[1]+1;

				return;
			}
			else if(gameBoard[n[0]][n[1]-1]==0){
				Global.gamePiece[n[0]][n[1]-1].setTeam(team);

				n[0] = n[0];
				n[1] = n[1]-1;

				return;
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

				return;
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

				return;
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

				return;
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

				return;
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

			return;
		}

		//Fill in the edges after we've reached both sides of the map
		if(n[0]-1 == 0){
			if(gameBoard[n[0]-1][n[1]]==0){
				Global.gamePiece[n[0]-1][n[1]].setTeam(team);

				n[0] = n[0]-1;
				n[1] = n[1];

				return;
			}
			else if(gameBoard[n[0]-1][n[1]+1]==0){
				Global.gamePiece[n[0]-1][n[1]+1].setTeam(team);

				n[0] = n[0]-1;
				n[1] = n[1]+1;

				return;
			}
		}
		if(m[0]+1 == gameBoard.length-1){
			if(gameBoard[m[0]+1][m[1]]==0){
				Global.gamePiece[m[0]+1][m[1]].setTeam(team);

				m[0] = m[0]+1;
				m[1] = m[1];

				return;
			}
			else if(gameBoard[m[0]-1][m[1]+1]==0){
				Global.gamePiece[m[0]-1][m[1]+1].setTeam(team);

				m[0] = m[0]-1;
				m[1] = m[1]+1;

				return;
			}
		}

		//Fill in the pairs after we've reached both sides of the map
		if( (n[0]-2) < 0 && (m[0]+2) > gameBoard.length-1 && pairs.size() > 0){
			//Play a random pair
			Global.gamePiece[pairs.get(0).get(1).get(0)][pairs.get(0).get(1).get(1)].setTeam(team);
			pairs.remove(0);

			return;
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
		return;
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