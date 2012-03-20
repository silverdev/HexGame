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
		Global.runningGame.stop();
		Global.window.initRegular();
		GameAction.fullUpdateBoard();
		Global.moveList.replay(1000);
		GameAction.checkedFlagReset();
		if(GameAction.checkWinPlayer1()){HexGameWindow.announceWinner(Global.playerturn);return;}
		if(GameAction.checkWinPlayer2()){HexGameWindow.announceWinner(Global.playerturn);return;}
		new GameObject(true);
		
	}

}
