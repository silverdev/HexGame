package com.sam.hex;

import com.sam.hex.ai.bee.BeeGameAI;
import com.sam.hex.ai.will.GameAI;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class GameObject implements Runnable {

	protected int moveNumber;
	protected MoveList moveList;
	public int currentPlayer;
	
	public boolean gameOver=false;
	

	public boolean game=true;
	public boolean threadAlive=true;
	public PlayingEntity player1;
	public PlayingEntity player2;
	public Thread gameThread;
	public RegularPolygonGameObject[][] gamePiece;
	
	public GameObject() {
		this.gameThread = new Thread(this, "runningGame"); // (1) Create a new thread.
		System.out.println(this.gameThread.getName()); 
		
		//(2)setup new game variables
		this.moveNumber=1;
		this.moveList= new MoveList();
		this.currentPlayer = 1;
		threadAlive=true;
		game=true;
		gameOver=false;
		
	
	}
	public void initGame() {
		Hexgame.window.initRegular();
		if(Global.player1Type==0) this.player1=new PlayerObject((byte)1);
		else if(Global.player1Type==1) this.player1=new GameAI((byte)1,(byte)1);
		else if(Global.player1Type==2) this.player1=new BeeGameAI(1);
			
		if(Global.player2Type==0) this.player2=new PlayerObject((byte)2);
		else if(Global.player2Type==1) this.player2=new GameAI((byte)2,(byte)1);
		else if(Global.player2Type==2) this.player2=new BeeGameAI(2);
		start();
		this.gameThread.start(); // (3) Start the thread.
		
	}
	
	public void restart() {
		this.gameThread = new Thread(this, "runningGame"); // (1) Create a new thread.
		start();
		System.out.println(this.gameThread.getName());
		this.gameThread.start(); // (2) Start the thread.
	}
	
	public void start(){
		this.gameOver = false;
		game=true;
	}
	
	public void stop(){
		this.gameOver=true;
		game=false;
		this.player1.quit();
		this.player2.quit();
	}
	
	public void run() {
		
			while(game){//Loop the game
				if(this.currentPlayer == 1){
					this.player1.getPlayerTurn();
					if (GameAction.checkWinPlayer(1)){
						this.gameOver=true;
						game=false;
						this.player1.win();
						this.player2.lose();
						DialogBoxes.announceWinner(1);
					}
					this.currentPlayer = 2;
				} 
				else {
					this.player2.getPlayerTurn();
					if (GameAction.checkWinPlayer(2)){
						this.gameOver=true;
						game=false;
						this.player1.lose();
						this.player2.win();
						DialogBoxes.announceWinner(2);
					}
					this.currentPlayer = 1;
					GameAction.checkedFlagReset();
				}
				this.moveNumber++;
			}
		
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}
	public int getMoveNumber(){
		return moveNumber;
	}
	public MoveList getMoveList(){
		return moveList;
	}

	
}