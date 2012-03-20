package com.sam.hex.lan;

import java.awt.Color;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.prefs.Preferences;

import com.sam.hex.GameAction;
import com.sam.hex.Global;
import com.sam.hex.Hexgame;

public class UnicastListener implements Runnable {
	Thread thread;
	boolean run = true;
	DatagramSocket socket;
	Preferences prefs;
	
	public UnicastListener() {
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		prefs = Preferences.userNodeForPackage(Hexgame.class);
		thread = new Thread(this, "LANscan"); //Create a new thread.
		thread.start(); //Start the thread.
	}
	
	public void run() {
		//Listen for other players
		byte[] data = new byte[1024];
	    while(run)
	    {
	    	try {
	    		DatagramPacket packet = new DatagramPacket(data, data.length);
	    		socket.receive(packet);
	    		String message = new String(data, 0, packet.getLength());
	    		InetAddress address = packet.getAddress();        		
        		
	    		if(!Global.localPlayer.ip.equals(address)) break;
	    		
	    		System.out.println(message);
	    		if(message.contains("Move: ")){
	    			//Full message looks like: Move: _x_,_y_
	    			
	    		}
	    		else if(message.contains("I changed my color to ")){
	    			//Full message looks like: I changed my color to _color_
	    			Global.localPlayer.playerColor = new Color(Integer.decode(message.substring(22)));
	    			Hexgame.setColors(prefs);
	    			GameAction.updateBoard();
	    		}
	    		else if(message.contains("I changed my name to ")){
	    			//Full message looks like: I changed my name to _name_
	    			Global.localPlayer.playerName = message.substring(21);
	    			Hexgame.setNames(prefs);
	    			GameAction.updateBoard();
	    		}
	    		else if(message.equals("I win!")){
	    			
	    		}
	    		else if(message.equals("Cheater.")){
	    			
	    		}
	    		else if(message.equals("Want to play a new game?")){
	    			
	    		}
	    		else if(message.equals("Sure, let's play again")){
	    			
	    		}
	    		else if(message.equals("No, I don't want to play again")){
	    			
	    		}
	    		else if(message.equals("Can I undo?")){
	    			
	    		}
	    		else if(message.equals("Sure, undo")){
	    			
	    		}
	    		else if(message.equals("No, you cannot undo")){
	    			
	    		}
			}
	    	catch (Exception e) {
				e.printStackTrace();
			}
	    }

	}
	
	public void stop() {
		run = false;
	}
}