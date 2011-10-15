package com.sam.hex;

import java.lang.Integer;

public class Hexgame {

	public static void main(String[] args){
		if (args.length>2)
		Global.set(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		else {Global.set(7,600,800);}
		 HexGameWindow fr = new HexGameWindow();
          fr.setVisible(true);
       while(true){
          try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          GameAction.updateBoard();
       }
	}
}
