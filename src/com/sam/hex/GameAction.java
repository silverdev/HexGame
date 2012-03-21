package com.sam.hex;

import java.awt.Point;

public class GameAction {

	public static Point hex;

	public static boolean checkWinPlayer1() {
		for (int i = 0; i < Global.gridSize - 1; i++) {
			if (RegularPolygonGameObject.checkWinTeam((byte) 1,
					Global.gridSize, i, Global.gamePiece)) {
				System.out.print("Player one wins");
				String path=RegularPolygonGameObject.findShortestPath((byte) 1,
						Global.gridSize, i, Global.gamePiece);
				RegularPolygonGameObject.colorPath(Global.gridSize,i,path);
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkWinPlayer2() {
		for (int i = 0; i < Global.gridSize - 1; i++) {
			if (RegularPolygonGameObject.checkWinTeam((byte) 2, i,
					Global.gridSize, Global.gamePiece)) {
				System.out.print("Player Two wins");
				RegularPolygonGameObject.findShortestPath((byte) 2, i,
				Global.gridSize, Global.gamePiece);
				return true;
			}
		}
		return false;
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
		Global.runningGameIsRuning=false;
		setPiece(new java.awt.Point(-1,-1));
		System.out.print("test");
		//Global.runningGame.stop();
		try {
			Global.runningGame.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void setTeam(byte t,int x,int y) {
		if(!Global.runningGameIsRuning){return;}
		//Global.moveList=new MoveList(Global.moveList,x,y,t);
		Global.moveList.makeMove(x, y, t);
		Global.gamePiece[x][y].setTeam(t);
	}
	
	public static boolean makeMove(byte team, Point hex){
		if(Global.gamePiece[hex.x][hex.y].getTeam() == 0){
			setTeam(team,hex.x,hex.y);
			return true;
		}
		return false;
	}
}