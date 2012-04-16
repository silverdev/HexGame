package com.sam.hex.lan;

import java.awt.Color;
import java.awt.Point;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.prefs.Preferences;

import com.sam.hex.GameAction;
import com.sam.hex.Global;
import com.sam.hex.MoveList;

public class PlayerUnicastListener implements Runnable {
	Thread thread;
	boolean run = true;
	DatagramSocket socket;
	Preferences prefs;
	int team;
	
	public PlayerUnicastListener(int team) {
		this.team = team;
		try {
			this.socket = new DatagramSocket(LANGlobal.PLAYERPORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
//		prefs = PreferenceManager.getDefaultSharedPreferences(Global.board.getContext());
		thread = new Thread(this, "LANscan"); //Create a new thread.
		thread.start(); //Start the thread.
	}
	
	public void run() {
		//Listen for other players
		byte[] data = new byte[1024];
//	    while(run)
//	    {
//	    	try {
//	    		DatagramPacket packet = new DatagramPacket(data, data.length);
//	    		socket.receive(packet);
//	    		String message = new String(data, 0, packet.getLength());
//	    		InetAddress address = packet.getAddress();
//        		
//	    		if(!LANGlobal.localPlayer.ip.equals(address)) continue;
//	    		
//	    		System.out.println(message);
//	    		if(message.contains("Move: ")){
//	    			int x = Integer.decode(message.substring(6,message.indexOf(",")));
//					int y = Integer.decode(message.substring(message.indexOf(",")+1));
//					LANGlobal.hex = new Point(x,y);
//	    		}
//	    		else if(message.contains("I changed my color to ")){
//	    			//Full message looks like: I changed my color to _color_
//	    			LANGlobal.localPlayer.playerColor = Integer.decode(message.substring(22));
//	    			HexGame.setColors(prefs);
//	    			Global.moveList.replay(0);
//	    			Global.board.postInvalidate();
//	    		}
//	    		else if(message.contains("I changed my name to ")){
//	    			//Full message looks like: I changed my name to _name_
//	    			LANGlobal.localPlayer.playerName = message.substring(21);
//	    			HexGame.setNames(prefs);
//	    			Global.board.postInvalidate();
//	    		}
//	    		else if(message.equals("Quitting")){
//	    			HexGame.stopGame();
//	    			if(!Global.gameOver)
//		    			new DialogBox(Global.board.getContext(), 
//		    					Global.board.getContext().getString(R.string.playerQuit), 
//		    					null, 
//		    	    	    	Global.board.getContext().getString(R.string.okay));
//	    		}
//	    		else if(message.equals("Want to play a new game?")){
//	    			new DialogBox(Global.board.getContext(), 
//	    					GameAction.InsertName.insert(Global.board.getContext().getString(R.string.newLANGame), LANGlobal.localPlayer.playerName), 
//	    					new DialogInterface.OnClickListener() {
//	    	    	    	    public void onClick(DialogInterface dialog, int which) {
//	    	    	    	        switch (which){
//	    	    	    	        case DialogInterface.BUTTON_POSITIVE:
//	    	    	    	            //Yes button clicked
//	    	    	    	        	new LANMessage("Sure, let's play again", LANGlobal.localPlayer.ip, LANGlobal.PLAYERPORT);
//	    	    	    	        	initalizeNewGame();
//	    	    	    	            break;
//	    	    	    	        case DialogInterface.BUTTON_NEGATIVE:
//	    	    	    	            //No button clicked
//	    	    	    	        	new LANMessage("No, I don't want to play again", LANGlobal.localPlayer.ip, LANGlobal.PLAYERPORT);
//	    	    	    	            break;
//	    	    	    	        }
//	    	    	    	    }
//	    	    	    	}, 
//	    	    	    	Global.board.getContext().getString(R.string.yes), 
//	    	    	    	Global.board.getContext().getString(R.string.no));
//	    		}
//	    		else if(message.equals("Sure, let's play again")){
//	    			initalizeNewGame();
//	    			new DialogBox(Global.board.getContext(), 
//	    					Global.board.getContext().getString(R.string.LANplayAgain), 
//	    					null, 
//	    					Global.board.getContext().getString(R.string.okay));
//	    	    }
//	    		else if(message.equals("No, I don't want to play again")){
//	    			new DialogBox(Global.board.getContext(), 
//	    					Global.board.getContext().getString(R.string.LANdontPlayAgain), 
//	    					null, 
//	    					Global.board.getContext().getString(R.string.okay));
//	    		}
//	    		else if(message.equals("Can I undo?")){
//	    			new DialogBox(Global.board.getContext(), 
//	    					GameAction.InsertName.insert(Global.board.getContext().getString(R.string.LANUndo), LANGlobal.localPlayer.playerName), 
//	    					new DialogInterface.OnClickListener() {
//	    	    	    	    public void onClick(DialogInterface dialog, int which) {
//	    	    	    	        switch (which){
//	    	    	    	        case DialogInterface.BUTTON_POSITIVE:
//	    	    	    	            //Yes button clicked
//	    	    		    			LANGlobal.undoRequested = true;
//	    	    	    	        	GameAction.undo();
//	    	    	    	        	new LANMessage("Sure, undo"+LANGlobal.undoNumber, LANGlobal.localPlayer.ip, LANGlobal.PLAYERPORT);
//	    	    	    	        	new LANMessage("Sure, undo"+LANGlobal.undoNumber, LANGlobal.localPlayer.ip, LANGlobal.PLAYERPORT);
//	    	    	    	        	new LANMessage("Sure, undo"+LANGlobal.undoNumber, LANGlobal.localPlayer.ip, LANGlobal.PLAYERPORT);
//	    	    	    	        	LANGlobal.undoNumber++;
//	    	    	    	            break;
//	    	    	    	        case DialogInterface.BUTTON_NEGATIVE:
//	    	    	    	            //No button clicked
//	    	    	    	        	new LANMessage("No, you cannot undo", LANGlobal.localPlayer.ip, LANGlobal.PLAYERPORT);
//	    	    	    	            break;
//	    	    	    	        }
//	    	    	    	    }
//	    	    	    	}, 
//	    					Global.board.getContext().getString(R.string.yes), 
//	    					Global.board.getContext().getString(R.string.no));
//	    		}
//	    		else if(message.contains("Sure, undo")){
//	    			int num = Integer.parseInt(message.substring(10));
//	    			if(num==LANGlobal.undoNumber){
//	    				LANGlobal.undoNumber++;
//	    				GameAction.undo();
//	    				new DialogBox(Global.board.getContext(), 
//		    					Global.board.getContext().getString(R.string.LANundoAccepted), 
//		    					null, 
//		    					Global.board.getContext().getString(R.string.okay));
//	    			}
//	    		}
//	    		else if(message.equals("No, you cannot undo")){
//	    			new DialogBox(Global.board.getContext(), 
//	    					Global.board.getContext().getString(R.string.LANundoDenied), 
//	    					null, 
//	    					Global.board.getContext().getString(R.string.okay));
//	    		}
//			}
//	    	catch (Exception e) {
//				e.printStackTrace();
//			}
//	    }

	}
	
	public void stop() {
		run = false;
	}
	
	private void initalizeNewGame(){
		if(Global.gameOver){
			Global.game.start();
			
			//Make sure defaults are set
	    	Global.moveList=new MoveList();
	    	Global.currentPlayer = 1;
	    	Global.moveNumber = 1;
	    	
	    	//Clear the board
	    	for(int x=0;x<Global.gridSize;x++){
				for(int y=0;y<Global.gridSize;y++){
					Global.gamePiece[x][y].setColor(Color.WHITE);
					Global.gamePiece[x][y].setTeam((byte)0);
				}
			}
    	}
		else{
			int turn = Global.currentPlayer;
			if(turn==2){
				if(LANGlobal.localPlayer.firstMove){
					GameAction.hex = new Point(-1,-1);
		    		while(turn == Global.currentPlayer){
						try {
							Thread.sleep(80);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
		    	else{
		    		LANGlobal.hex = new Point(-1,-1);
		    		while(turn == Global.currentPlayer){
						try {
							Thread.sleep(80);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
		    	}
			}
			
			//Make sure defaults are set
	    	Global.moveList=new MoveList();
	    	Global.currentPlayer = 1;
	    	GameAction.hex = null;
	    	Global.moveNumber = 1;
	    	
	    	//Clear the board
	    	for(int x=0;x<Global.gridSize;x++){
				for(int y=0;y<Global.gridSize;y++){
					Global.gamePiece[x][y].setColor(Color.WHITE);
					Global.gamePiece[x][y].setTeam((byte)0);
				}
			}
		}
	}
}