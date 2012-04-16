package com.sam.hex;

import java.awt.Point;

public class PlayerObject implements PlayingEntity {
	
	byte[][] gameBoard; 
	byte team;
	
	public PlayerObject(byte i) {
		this.team=i;//Set the player's team
	}
	
	public void getPlayerTurn() {
		GameAction.hex = null;
		looper: while (true) {
			Point hex = GameAction.hex;
			while (hex == null) {
				hex = GameAction.hex;
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(Global.gameOver) break looper;
			}
			if (hex.equals(new Point(-1,-1))){
				GameAction.hex = null;
				break;
			}
			if (GameAction.makeMove(this, team, hex)) {
				GameAction.hex = null;
				break;
			}
			GameAction.hex = null;
		}
	}
	
	public void undoCalled(){
		GameAction.hex = new Point(-1,-1);
	}

	public void newgameCalled() {
		GameAction.hex = new Point(-1,-1);
	}

	@Override
	public boolean supportsUndo() {
		return true;
	}

	@Override
	public boolean supportsNewgame() {
		return true;
	}

	@Override
	public void colorChanged() {
	}

	@Override
	public void nameChanged() {
	}

	@Override
	public void quit() {
		GameAction.hex = new Point(-1,-1);
	}

	@Override
	public void win() {
	}

	@Override
	public void lose() {
	}

	@Override
	public boolean supportsSave() {
		return true;
	}
}