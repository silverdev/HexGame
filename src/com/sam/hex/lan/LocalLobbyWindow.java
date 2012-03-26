package com.sam.hex.lan;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MulticastSocket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sam.hex.Global;

public class LocalLobbyWindow extends JFrame {
	private static final long serialVersionUID = -4844955816069056271L;
	MulticastListener listener;
	MulticastSender sender;
	MulticastSocket socket;
	JPanel center;
	public static LocalNetworkObject lno = new LocalNetworkObject("", null);
	
	public LocalLobbyWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Global.windowWidth, Global.windowHeight);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().setBackground(Color.black);
		
		//Add the top of the Local Lobby
		JPanel top = new JPanel();
		top.setBackground(Color.black);
		JLabel welcome = new JLabel("Local Lobby");
		Button manualIP = new Button("Enter a manual ip");
		manualIP.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				customIP();
			}
		});
		top.add(welcome);
		top.add(manualIP);
		getContentPane().add(top, BorderLayout.NORTH);
		
		//Add the list of players
		center = new JPanel();
		center.setBackground(Color.white);
		center.add(new JLabel("Scanning..."));
		
		getContentPane().add(center, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private void onClose() {
		//Kill our threads
		try{
			sender.stop();
			listener.stop();
		}
		catch(Exception e){}
        socket.close();
        
        //Clear our cached players from the network
        Global.localObjects = new ArrayList<LocalNetworkObject>();
	}
	
	private void challengeSent(){
		int reply = JOptionPane.showConfirmDialog(getContentPane(), "Do you want to challenge "+lno.toString()+"?", "Send a challenge", JOptionPane.YES_NO_OPTION);
		if(reply==0){
			//They clicked yes
			new LANMessage(Global.player1Name+" challenges you. Grid size: "+Global.gridSize, lno.ip, 4080);
		}
	}
	
	private void challengeRecieved(){}
	
	private void updateResultsInUi(){
		for(int i=0;i<Global.localObjects.size();i++){
			JLabel player = new JLabel(Global.localObjects.get(i).playerName);
			player.addMouseListener(new MouseAdapter()  
			{  
			    public void mouseReleased(MouseEvent e)  
			    {  
			        if (e.isPopupTrigger())  
			        {
			        	challengeSent();
			        }  
			    }  
			}); 
			
			center.add(player);
		}
	}
	
	private void customIP(){
		
	}
}