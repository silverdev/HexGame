package com.sam.hex;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class GameObject implements Runnable {
	Thread theGameRunner;

	public GameObject() {
		theGameRunner = new Thread(this, "runningGame"); // (1) Create a new
		// thread.
		System.out.println(theGameRunner.getName());
		theGameRunner.start(); // (2) Start the thread.
	}

	public void run() {
		playingEntity player1=new PlayerObject((byte)1);
		playingEntity player2=new GameAI((byte)2,(byte)1);
		byte player = 1;
		while (true) {

			System.out.println("test");
			if (player == 1) {
				if(Global.gameType<2) GameAction.getPlayerTurn(player);
				else GameAction.getAITurn(player);
				if (GameAction.checkWinPlayer1())
					break;
				player = 2;
			} else {
				if((Global.gameType+1)%2>0) GameAction.getPlayerTurn(player);
				else GameAction.getAITurn(player);
				if (GameAction.checkWinPlayer2())
					break;
				player = 1;
				GameAction.checkedFlagReset();
			}

		}

	}

}
