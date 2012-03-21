package com.sam.hex;

public class Replay implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Global.runningGame.stop();
		GameAction.stopGame();
		Global.window.initRegular();
		GameAction.fullUpdateBoard();
		Global.moveList.replay(500);
		GameAction.checkedFlagReset();
		if(GameAction.checkWinPlayer1()){HexGameWindow.announceWinner(Global.playerturn);return;}
		if(GameAction.checkWinPlayer2()){HexGameWindow.announceWinner(Global.playerturn);return;}
		GameAction.hex=null;
		new GameObject(true);
		
	}

}
