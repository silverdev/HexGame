package com.sam.hex;

import java.awt.event.MouseEvent;
import java.lang.Integer;
import java.awt.event.MouseAdapter;

public class Hexgame {

	public static void main(String[] args){
		if (args.length>2)
		Global.set(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		else {Global.set(7,600,800);}
		 HexGameWindow fr = new HexGameWindow();
		 
          fr.setVisible(true);
          byte player=1;
       while(true){
    	   GameAction.updateBoard();
          try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          if(player==1){
        	  GameAction.getPlayerTurn(player);
        	  GameAction.checkWinPlayer1();
        	  player=2;
          }
          else{
        	  GameAction.getPlayerTurn(player);
        	  GameAction.checkWinPlayer2();
        	  player=1;
        	  GameAction.checkedFlageReset();
          }
		
		
		
       }
	}
	
	

}

