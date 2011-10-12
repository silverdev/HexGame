package com.sam.hex;

public class Hexgame {

	public static void main(String[] args){
		 HexGameWindow fr = new HexGameWindow();
          fr.setVisible(true);
          try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          GameAction.updateBoard();
		
	}
}
