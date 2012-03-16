package com.sam.hex;

import java.lang.Integer;

public class Hexgame {

	public static void main(String[] args){
		if (args.length > 3)
			Global.set(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
					Integer.parseInt(args[2]), Byte.parseByte(args[3]));
		else {
			Global.set(7, 600, 800);
		}
		HexGameWindow fr = new HexGameWindow();

		fr.setVisible(true);
		@SuppressWarnings("unused")
		GameObject RunningGame = new GameObject(); // GameObject is very much in
													// use, do not delete!

		while (true) {
			GameAction.updateBoard();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
