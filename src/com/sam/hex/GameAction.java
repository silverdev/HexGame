package com.sam.hex;

import java.awt.Point;

import com.sam.hex.lan.LANGlobal;

public class GameAction {

	public static Point hex;

	public static synchronized boolean checkWinPlayer(int team) {
		if(team==1){
			for (int i = 0; i < Global.gridSize; i++) {
				if (RegularPolygonGameObject.checkWinTeam((byte) 1, Global.gridSize, i, Global.gamePiece)) {
					System.out.println("Player one wins");
					checkedFlagReset();
					String path=RegularPolygonGameObject.findShortestPath((byte) 1, Global.gridSize, i, Global.gamePiece);
					RegularPolygonGameObject.colorPath(Global.gridSize,i,path);
					return true;
				}
			}
			return false;
		}
		else{
			for (int i = 0; i < Global.gridSize; i++) {
				if (RegularPolygonGameObject.checkWinTeam((byte) 2, i, Global.gridSize, Global.gamePiece)) {
					System.out.println("Player two wins");
					checkedFlagReset();
					String path=RegularPolygonGameObject.findShortestPath((byte) 2, i, Global.gridSize, Global.gamePiece);
					RegularPolygonGameObject.colorPath(i,Global.gridSize,path);
					return true;
				}
			}
			return false;
		}
	}

	public static void checkedFlagReset() {
		for (int x = Global.gridSize - 1; x >= 0; x--) {
			for (int y = Global.gridSize - 1; y >= 0; y--) {
				Global.gamePiece[x][y].checkedflage = false;
			}
		}
	}
	public static void updateBoard() {
		if (HexGameWindow.cPolygons.getWidth() != Global.windowWidth
				|| (HexGameWindow.cPolygons.getHeight()-10) != Global.windowHeight) {
			fullUpdateBoard();
		}

		HexGameWindow.cPolygons.revalidate();
		HexGameWindow.cPolygons.repaint();

	}

	public static void fullUpdateBoard() {
		Global.windowWidth = HexGameWindow.cPolygons.getWidth();
		Global.windowHeight = (HexGameWindow.cPolygons.getHeight()-10); //-10 gives an offset to alow the top border to be visable.
		double radius;
		RegularPolygonGameObject[][] gamePeace = Global.gamePiece;
		radius = BoardTools.radiusCalculator(Global.windowWidth,
				Global.windowHeight, Global.gridSize);
		// radius = BoardTools.radiusCalculator(400,400, 7);
		double hrad = radius * Math.sqrt(3) / 2; // Horizontal radius
		int yOffset = (int) ((HexGameWindow.cPolygons.getHeight() - ((3 * radius / 2)
				* (gamePeace[0].length - 1) + 2 * radius)) / 2);
		int xOffset = (int) ((HexGameWindow.cPolygons.getWidth() - (hrad
				* gamePeace.length * 2 + hrad * (gamePeace[0].length - 1))) / 2);

		for (int xc = 0; xc < Global.gamePiece.length; xc++) {
			for (int yc = 0; yc < gamePeace[0].length; yc++)
				gamePeace[xc][yc].update((hrad + yc * hrad + 2 * hrad * xc)
						+ xOffset, (1.5 * radius * yc + radius) + yOffset,
						radius, 6, Math.PI / 2);
		}

		if(Global.windowWidth>0&&(Global.windowHeight+10)>0)BoardTools.setBackground(Global.windowWidth, (Global.windowHeight+10));
		HexGameWindow.cPolygons.revalidate();
		HexGameWindow.cPolygons.repaint();

	}

	public static void setPiece(java.awt.Point nhex) {
		hex=nhex;
	}
	
	public static void stopGame(){
		if (Hexgame.runningGame==null)return;
		Hexgame.runningGame.stop();
		setPiece(new java.awt.Point(-1,-1));
		try {
			Hexgame.runningGame.gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Hexgame.runningGame.start();
	}
	private static void setTeam(byte t,int x,int y) {
		Hexgame.runningGame.moveList.makeMove(x, y, t);
		Hexgame.runningGame.gamePiece[x][y].setTeam(t);
	}
	
	public static boolean makeMove(PlayingEntity player, byte team, Point hex){
		if(player!=null && Hexgame.runningGame.gamePiece[hex.x][hex.y].getTeam() == 0){
			setTeam(team,hex.x,hex.y);
			return true;
		}
		else if(player!=null && Hexgame.runningGame.moveNumber==2 && Hexgame.runningGame.gamePiece[hex.x][hex.y].getTeam() == 1){//Swap rule
			setTeam(team,hex.x,hex.y);
			return true;
		}
		return false;
	}
	
	public static void undo(){
		try{
			if(Hexgame.runningGame.moveNumber>1){
				GameAction.checkedFlagReset();
				
				//Remove the piece from the board and the movelist
				Move lastMove = Hexgame.runningGame.moveList.thisMove;
				Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
				Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
				Hexgame.runningGame.moveList.replay(0);
				Hexgame.runningGame.moveNumber--;
				
				//Determine who is a human
				boolean p1 = Hexgame.runningGame.player1 instanceof PlayerObject;
				boolean p2 = Hexgame.runningGame.player2 instanceof PlayerObject;
				
				if(Global.gameLocation==0){
					if(Hexgame.runningGame.currentPlayer==1 && p1){//It's a human's turn
						Hexgame.runningGame.player2.undoCalled();//Tell the other player we're going back a turn
						
						if(!p2){//If the other person isn't a human, undo again
							if(Hexgame.runningGame.moveNumber>1){
								lastMove = Hexgame.runningGame.moveList.thisMove;
								Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
								Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
								Hexgame.runningGame.moveNumber--;
							}
							else{
								GameAction.hex = new Point(-1,-1);
								Hexgame.runningGame.moveNumber--;
							}
							
							if(Hexgame.runningGame.gameOver) Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
						}
						else{
							//Otherwise, cede the turn to the other player
							GameAction.hex = new Point(-1,-1);
							Hexgame.runningGame.moveNumber--;
						}
					}
					else if(Hexgame.runningGame.currentPlayer==1 && !p1){
						if(!Hexgame.runningGame.gameOver){
							Hexgame.runningGame.player1.undoCalled();
							Hexgame.runningGame.moveNumber--;
						}
					}
					else if(Hexgame.runningGame.currentPlayer==2 && p2){
						Hexgame.runningGame.player1.undoCalled();
						
						//If the other person isn't a (local) human
						if(!p1){
							//Undo again
							if(Hexgame.runningGame.moveNumber>1){
								lastMove = Hexgame.runningGame.moveList.thisMove;
								Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
								Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
								Hexgame.runningGame.moveNumber--;
							}
							else{
								GameAction.hex = new Point(-1,-1);
								Hexgame.runningGame.moveNumber--;
							}
							
							if(Hexgame.runningGame.gameOver) Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
						}
						else{
							//Otherwise, cede the turn to the other player
							GameAction.hex = new Point(-1,-1);
							Hexgame.runningGame.moveNumber--;
						}
					}
					else if(Hexgame.runningGame.currentPlayer==2 && !p2){
						if(!Hexgame.runningGame.gameOver) {
							Hexgame.runningGame.player2.undoCalled();
							Hexgame.runningGame.moveNumber--;
						}
					}
				}
				/*
				if(Hexgame.runningGame.gameLocation==1){//Inside a LAN game
					if(Hexgame.runningGame.currentPlayer==1){//First player's turn
						if(LANHexgame.runningGame.localPlayer.firstMove){//First player is on the network (not local)
							if(LANHexgame.runningGame.undoRequested){//First player requested the undo
								//undo twice, don't switch players
								if(Hexgame.runningGame.moveNumber>1){
									lastMove = Hexgame.runningGame.moveList.thisMove;
									Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
									Hexgame.runningGame.moveNumber--;
								}
								if(Hexgame.runningGame.gameOver) Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
							}
							else{//Second player requested the undo
								//undo once, switch players
								LANHexgame.runningGame.hex = new Point(-1,-1);
								Hexgame.runningGame.moveNumber--;
							}
						}
						else{//First player is local (not on the network)
							if(LANHexgame.runningGame.undoRequested){//Second player requested the undo
								//undo once, switch players
								GameAction.hex = new Point(-1,-1);
								Hexgame.runningGame.moveNumber--;
							}
							else{//First player requested the undo
								//undo twice, don't switch players
								if(Hexgame.runningGame.moveNumber>1){
									lastMove = Hexgame.runningGame.moveList.thisMove;
									Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
									Hexgame.runningGame.moveNumber--;
								}
								if(Hexgame.runningGame.gameOver) Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
							}
						}
					}
					else{//Second player's turn
						if(LANHexgame.runningGame.localPlayer.firstMove){//Second player is local (not on the network)
							if(LANHexgame.runningGame.undoRequested){//First player requested the undo
								//undo once, switch players
								GameAction.hex = new Point(-1,-1);
								Hexgame.runningGame.moveNumber--;
							}
							else{//Second player requested the undo
								//undo twice, don't switch players
								if(Hexgame.runningGame.moveNumber>1){
									lastMove = Hexgame.runningGame.moveList.thisMove;
									Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
									Hexgame.runningGame.moveNumber--;
								}
								if(Hexgame.runningGame.gameOver) Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
							}
						}
						else{//Second player is on the network (not local)
							if(LANHexgame.runningGame.undoRequested){//Second player requested the undo
								//undo twice, don't switch players
								if(Hexgame.runningGame.moveNumber>1){
									lastMove = Hexgame.runningGame.moveList.thisMove;
									Hexgame.runningGame.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Hexgame.runningGame.moveList = Hexgame.runningGame.moveList.nextMove;
									Hexgame.runningGame.moveNumber--;
								}
								if(Hexgame.runningGame.gameOver) Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
							}
							else{//First player requested the undo
								//undo once, switch players
								LANHexgame.runningGame.hex = new Point(-1,-1);
								Hexgame.runningGame.moveNumber--;
							}
						}
					}
					
					LANHexgame.runningGame.undoRequested = false;
				}*/
				
				//Reset the game if it's already ended
				if(Hexgame.runningGame.gameOver){
					Hexgame.runningGame.moveList.replay(0);
					Hexgame.runningGame.currentPlayer = (Hexgame.runningGame.currentPlayer%2)+1;
					Hexgame.runningGame.start();
				}
			}
		}
		catch(NullPointerException e){
			Hexgame.runningGame.moveNumber=1;
			e.printStackTrace();
		}
	}
}