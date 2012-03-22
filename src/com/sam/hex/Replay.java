package com.sam.hex;

public class Replay implements Runnable {

	@Override
	public void run() {
		GameAction.stopGame();
		Global.window.initRegular();
		GameAction.fullUpdateBoard();
		Global.moveList.replay(900);
		GameAction.checkedFlagReset();
		if(GameAction.checkWinPlayer1()){DialogBoxes.announceWinner(1);return;}
		if(GameAction.checkWinPlayer2()){DialogBoxes.announceWinner(2);return;}
		GameAction.hex=null;
		new GameObject(true);
	}

}
