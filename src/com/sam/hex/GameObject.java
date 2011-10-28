package com.sam.hex;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class GameObject implements Runnable {
	Thread theGameRunner;
	
	public GameObject() {
		theGameRunner = new Thread(this, "runningGame"); // (1) Create a new thread.
		System.out.println(theGameRunner.getName());
		theGameRunner.start(); // (2) Start the thread.
        }
        public void run() {
        	
        	 byte player=1;
             while(true){
          	   
            	 System.out.println("test");
                if(player==1){
              	  GameAction.getPlayerTurn(player);
              	  GameAction.checkWinPlayer1();
              	  player=2;
                }
                else{
              	  GameAction.getPlayerTurn(player);
              	  GameAction.checkWinPlayer2();
              	  player=1;
              	  GameAction.checkedFlagReset();
                }
      		
      		
      		
        	
        }
  
	}
	
}
