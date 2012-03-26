package com.sam.hex.ai.bee;

import java.awt.Point;

import com.sam.hex.GameAction;
import com.sam.hex.PlayingEntity;

public class BeeGameAI implements PlayingEntity {
	int team = 1;
	
	public BeeGameAI(int team){
		this.team = team;
		new Thread(new Bee(team), "bee").start();
	}

	@Override
	public void getPlayerTurn() {
		BeeMove.move=null;
		while(BeeMove.move==null){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		GameAction.makeMove(this, (byte) team, BeeMove.move);
		BeeMove.move = null;
	}
	
	public void undo(Point hex) {
		//Do nothing
	}

	@Override
	public void getPlayerTurn(byte[][] gameBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoCalled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String errorName) {
		// TODO Auto-generated method stub
		
	}
	
}