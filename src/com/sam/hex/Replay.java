package com.sam.hex;

public class Replay implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Global.runningGame.stop();
		GameAction.stopGame();
		Global.window.initRegular();
		GameAction.fullUpdateBoard();
		Global.moveList.replay(1000);
		GameAction.checkedFlagReset();
		if(GameAction.checkWinPlayer1()){DialogBoxes.announceWinner(Global.playerturn);return;}
		if(GameAction.checkWinPlayer2()){DialogBoxes.announceWinner(Global.playerturn);return;}
		GameAction.hex=null;
		new GameObject(true);
	}

}
