package com.sam.hex;

import com.sam.hex.ai.bee.BeeGameAI;
import com.sam.hex.ai.will.GameAI;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class GameObject implements Runnable {
	private boolean game=true;
	private boolean threadAlive=true;

	public GameObject() {
		Global.gameThread = new Thread(this, "runningGame"); // (1) Create a new thread.
		System.out.println(Global.gameThread.getName()); 
		
		//(2)setup new game variables
		Global.moveNumber=1;
		Global.moveList= new MoveList();
		Global.currentPlayer = 1;

		if(Global.player1Type==0) Global.player1=new PlayerObject((byte)1);
		else if(Global.player1Type==1) Global.player1=new GameAI((byte)1,(byte)1);
		else if(Global.player1Type==2) Global.player1=new BeeGameAI(1);
			
		if(Global.player2Type==0) Global.player2=new PlayerObject((byte)2);
		else if(Global.player2Type==1) Global.player2=new GameAI((byte)2,(byte)1);
		else if(Global.player2Type==2) Global.player2=new BeeGameAI(2);
		 
		start();
		Global.gameThread.start(); // (3) Start the thread.
	}
	
	public GameObject(boolean undo) {
		Global.gameThread = new Thread(this, "runningGame"); // (1) Create a new thread.
		start();
		System.out.println(Global.gameThread.getName());
		Global.gameThread.start(); // (2) Start the thread.
	}
	
	public void start(){
		Global.gameOver = false;
		game=true;
	}
	
	public void stop(){
		Global.gameOver=true;
		game=false;
		threadAlive=false;
		Global.player1.quit();
		Global.player2.quit();
	}
	
	public void run() {
		while(threadAlive){//Keeps the thread alive even if the game has ended
			while(game){//Loop the game
				if(Global.currentPlayer == 1){
					Global.player1.getPlayerTurn();
					if (GameAction.checkWinPlayer(1)){
						Global.gameOver=true;
						game=false;
						Global.player1.win();
						Global.player2.lose();
						DialogBoxes.announceWinner(1);
					}
					Global.currentPlayer = 2;
				} 
				else {
					Global.player2.getPlayerTurn();
					if (GameAction.checkWinPlayer(2)){
						Global.gameOver=true;
						game=false;
						Global.player1.lose();
						Global.player2.win();
						DialogBoxes.announceWinner(2);
					}
					Global.currentPlayer = 1;
					GameAction.checkedFlagReset();
				}
				Global.moveNumber++;
			}
		
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}