package com.sam.hex.replay;

import com.sam.hex.DialogBoxes;
import com.sam.hex.GameAction;
import com.sam.hex.GameObject;
import com.sam.hex.Global;

public class Replay implements Runnable {

	@Override
	public void run() {
		GameAction.stopGame();
		Global.window.initRegular();
		GameAction.fullUpdateBoard();
		Global.moveList.replay(900);
		GameAction.checkedFlagReset();
		if(GameAction.checkWinPlayer(1)){DialogBoxes.announceWinner(1);return;}
		if(GameAction.checkWinPlayer(2)){DialogBoxes.announceWinner(2);return;}
		GameAction.hex=null;
		new GameObject(true);
	}

}
