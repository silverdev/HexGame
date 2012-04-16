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
		if (Global.game==null)return;
		Global.game.stop();
		setPiece(new java.awt.Point(-1,-1));
		try {
			Global.gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Global.game.start();
	}
	private static void setTeam(byte t,int x,int y) {
		Global.moveList.makeMove(x, y, t);
		Global.gamePiece[x][y].setTeam(t);
	}
	
	public static boolean makeMove(PlayingEntity player, byte team, Point hex){
		if(player!=null && Global.gamePiece[hex.x][hex.y].getTeam() == 0){
			setTeam(team,hex.x,hex.y);
			return true;
		}
		else if(player!=null && Global.moveNumber==2 && Global.gamePiece[hex.x][hex.y].getTeam() == 1){//Swap rule
			setTeam(team,hex.x,hex.y);
			return true;
		}
		return false;
	}
	
	public static void undo(){
		try{
			if(Global.moveNumber>1){
				GameAction.checkedFlagReset();
				
				//Remove the piece from the board and the movelist
				Move lastMove = Global.moveList.thisMove;
				Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
				Global.moveList = Global.moveList.nextMove;
				Global.moveList.replay(0);
				Global.moveNumber--;
				
				//Determine who is a human
				boolean p1 = Global.player1 instanceof PlayerObject;
				boolean p2 = Global.player2 instanceof PlayerObject;
				
				if(Global.gameLocation==0){
					if(Global.currentPlayer==1 && p1){//It's a human's turn
						Global.player2.undoCalled();//Tell the other player we're going back a turn
						
						if(!p2){//If the other person isn't a human, undo again
							if(Global.moveNumber>1){
								lastMove = Global.moveList.thisMove;
								Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
								Global.moveList = Global.moveList.nextMove;
								Global.moveNumber--;
							}
							else{
								GameAction.hex = new Point(-1,-1);
								Global.moveNumber--;
							}
							
							if(Global.gameOver) Global.currentPlayer = (Global.currentPlayer%2)+1;
						}
						else{
							//Otherwise, cede the turn to the other player
							GameAction.hex = new Point(-1,-1);
							Global.moveNumber--;
						}
					}
					else if(Global.currentPlayer==1 && !p1){
						if(!Global.gameOver){
							Global.player1.undoCalled();
							Global.moveNumber--;
						}
					}
					else if(Global.currentPlayer==2 && p2){
						Global.player1.undoCalled();
						
						//If the other person isn't a (local) human
						if(!p1){
							//Undo again
							if(Global.moveNumber>1){
								lastMove = Global.moveList.thisMove;
								Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
								Global.moveList = Global.moveList.nextMove;
								Global.moveNumber--;
							}
							else{
								GameAction.hex = new Point(-1,-1);
								Global.moveNumber--;
							}
							
							if(Global.gameOver) Global.currentPlayer = (Global.currentPlayer%2)+1;
						}
						else{
							//Otherwise, cede the turn to the other player
							GameAction.hex = new Point(-1,-1);
							Global.moveNumber--;
						}
					}
					else if(Global.currentPlayer==2 && !p2){
						if(!Global.gameOver) {
							Global.player2.undoCalled();
							Global.moveNumber--;
						}
					}
				}
				if(Global.gameLocation==1){//Inside a LAN game
					if(Global.currentPlayer==1){//First player's turn
						if(LANGlobal.localPlayer.firstMove){//First player is on the network (not local)
							if(LANGlobal.undoRequested){//First player requested the undo
								//undo twice, don't switch players
								if(Global.moveNumber>1){
									lastMove = Global.moveList.thisMove;
									Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Global.moveList = Global.moveList.nextMove;
									Global.moveNumber--;
								}
								if(Global.gameOver) Global.currentPlayer = (Global.currentPlayer%2)+1;
							}
							else{//Second player requested the undo
								//undo once, switch players
								LANGlobal.hex = new Point(-1,-1);
								Global.moveNumber--;
							}
						}
						else{//First player is local (not on the network)
							if(LANGlobal.undoRequested){//Second player requested the undo
								//undo once, switch players
								GameAction.hex = new Point(-1,-1);
								Global.moveNumber--;
							}
							else{//First player requested the undo
								//undo twice, don't switch players
								if(Global.moveNumber>1){
									lastMove = Global.moveList.thisMove;
									Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Global.moveList = Global.moveList.nextMove;
									Global.moveNumber--;
								}
								if(Global.gameOver) Global.currentPlayer = (Global.currentPlayer%2)+1;
							}
						}
					}
					else{//Second player's turn
						if(LANGlobal.localPlayer.firstMove){//Second player is local (not on the network)
							if(LANGlobal.undoRequested){//First player requested the undo
								//undo once, switch players
								GameAction.hex = new Point(-1,-1);
								Global.moveNumber--;
							}
							else{//Second player requested the undo
								//undo twice, don't switch players
								if(Global.moveNumber>1){
									lastMove = Global.moveList.thisMove;
									Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Global.moveList = Global.moveList.nextMove;
									Global.moveNumber--;
								}
								if(Global.gameOver) Global.currentPlayer = (Global.currentPlayer%2)+1;
							}
						}
						else{//Second player is on the network (not local)
							if(LANGlobal.undoRequested){//Second player requested the undo
								//undo twice, don't switch players
								if(Global.moveNumber>1){
									lastMove = Global.moveList.thisMove;
									Global.gamePiece[lastMove.getX()][lastMove.getY()].setTeam((byte)0);
									Global.moveList = Global.moveList.nextMove;
									Global.moveNumber--;
								}
								if(Global.gameOver) Global.currentPlayer = (Global.currentPlayer%2)+1;
							}
							else{//First player requested the undo
								//undo once, switch players
								LANGlobal.hex = new Point(-1,-1);
								Global.moveNumber--;
							}
						}
					}
					
					LANGlobal.undoRequested = false;
				}
				
				//Reset the game if it's already ended
				if(Global.gameOver){
					Global.moveList.replay(0);
					Global.currentPlayer = (Global.currentPlayer%2)+1;
					Global.game.start();
				}
			}
		}
		catch(NullPointerException e){
			Global.moveNumber=1;
			e.printStackTrace();
		}
	}
}