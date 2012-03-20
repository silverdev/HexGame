package com.sam.hex.lan;

import java.awt.Color;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.sam.hex.Global;

public class MulticastListener implements Runnable {
	Thread thread;
	boolean run = true;
	MulticastSocket socket;
	
	public MulticastListener(MulticastSocket socket) {
		this.socket = socket;
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
	    		System.out.println(message);
	    		InetAddress address = packet.getAddress();        		
        		
	    		if(message.contains("Let's play Hex.")){
	    			//Full message looks like: Let's play Hex. I'm _playername_
	    			String name = message.substring(20);//Grab the name from the end of the message
	        		LocalNetworkObject lno = new LocalNetworkObject(name,address);
	        		if(!Global.localObjects.contains(lno)){
	        			Global.localObjects.add(lno);
	        		}
//	        		handler.post(updateUI);
	    		}
	    		else if(message.contains("challenges you. Grid size: ")){
	    			boolean flag = true;
	    			for(int i=0;i<Global.localObjects.size();i++){
	        			if(Global.localObjects.get(i).ip.equals(address)){
	        				flag = false;
	        				LocalLobbyWindow.lno = Global.localObjects.get(i);
	        				LocalLobbyWindow.lno.firstMove = true;
	        				//Full message looks like: _playername_ challenges you. Grid size: _gridsize_
	        				LocalLobbyWindow.lno.gridSize = Integer.decode(message.substring(message.lastIndexOf("Grid size: ")+11));//Grab the grid size from the end of the message
//	        				handler.post(challenger);
	        				break;
	        			}
	        		}
	    			if(flag){
	    				LocalLobbyWindow.lno.playerName = message.substring(0, message.lastIndexOf(" challenges you"));
	    				LocalLobbyWindow.lno.firstMove = true;
	    				LocalLobbyWindow.lno.gridSize = Integer.decode(message.substring(message.lastIndexOf("Grid size: ")+11));
//	    				handler.post(challenger);
	    			}
	    		}
	    		else if(message.contains("It's on! My color's ") && LocalLobbyWindow.lno.ip.equals(address)){
	    			//Full message looks like: It's on! My color's _playercolor_
	    			LocalLobbyWindow.lno.playerColor = new Color(Integer.decode(message.substring(20)));//Grab the color from the end of the message
	    			
	    			//Send our color over
	    			new LANMessage("My color is "+Global.player1Color, LocalLobbyWindow.lno.ip, 4080);
	    			
//	    			handler.post(startGame);
    				break;
	    		}
	    		else if(message.contains("My color is ") && LocalLobbyWindow.lno.ip.equals(address)){
	    			//Full message looks like: My color is _playercolor_
	    			Global.localPlayer.playerColor = new Color(Integer.decode(message.substring(12)));//Grab the color from the end of the message
	    			
//	    			handler.post(startGame);
	    			break;
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