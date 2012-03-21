package com.sam.hex;

import com.sam.hex.willsai.GameAI;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class GameObject implements Runnable {
	Thread theGameRunner;

	public GameObject() {
		theGameRunner = new Thread(this, "runningGame"); // (1) Create a new thread.
		System.out.println(theGameRunner.getName()); 
		
		//(2)setup new game variables
		Global.moveNumber=0;
		Global.moveList= new MoveList();
		Global.playerturn = 1;
		Global.gameObjectThread=theGameRunner;
		Global.gameOver=false;

		if(Global.player1Type==0) Global.player1=new PlayerObject((byte)1);
		else Global.player1=new GameAI((byte)1,(byte)1);// sets player vs Ai
			
		if(Global.player2Type==0) Global.player2=new PlayerObject((byte)2);
		else Global.player2=new GameAI((byte)2,(byte)1);// sets player vs Ai
		 
		Global.stop_gameObjectThread=true;
		theGameRunner.start(); // (3) Start the thread.
	}
	
	public GameObject(boolean undo) {
		theGameRunner = new Thread(this, "runningGame"); // (1) Create a new thread.
		Global.stop_gameObjectThread=true;
		System.out.println(theGameRunner.getName());
		theGameRunner.start(); // (2) Start the thread.
	}
	
	
	public void run() {
		while (true) {
			if(Global.playerturn == 1){
				Global.player1.getPlayerTurn();
				if (!Global.stop_gameObjectThread) return;
				if (GameAction.checkWinPlayer1())
					break;
				Global.playerturn = 2;
			} 
			else {
				Global.player2.getPlayerTurn();
				if (!Global.stop_gameObjectThread) return;
				if (GameAction.checkWinPlayer2())
					break;
				Global.playerturn = 1;
				GameAction.checkedFlagReset();
				
			}
			Global.moveNumber++;
		}
		
		Global.gameOver=true;
		HexGameWindow.announceWinner(Global.playerturn);
	}
}