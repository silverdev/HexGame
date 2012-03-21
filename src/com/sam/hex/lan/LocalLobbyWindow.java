package com.sam.hex.lan;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.net.MulticastSocket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sam.hex.Global;

public class LocalLobbyWindow extends JFrame {
	private static final long serialVersionUID = -4844955816069056271L;
	MulticastListener listener;
	MulticastSender sender;
	MulticastSocket socket;
	public static LocalNetworkObject lno = new LocalNetworkObject("", null);
	
	public LocalLobbyWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Global.windowWidth, Global.windowHeight);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		top.setBackground(Color.black);
		JLabel welcome = new JLabel("Local Lobby");
		Button manualIP = new Button("Enter a manual ip");
		top.add(welcome);
		top.add(manualIP);
		ArrayList<JLabel> players = new ArrayList<JLabel>();
		
		JPanel center = new JPanel();
		center.setBackground(Color.black);
		players.add(new JLabel("Dogs"));
		players.add(new JLabel("Cats"));
		players.add(new JLabel("Lizards"));
		players.add(new JLabel("Parots"));
		getContentPane().add(top, BorderLayout.NORTH);
		for(int i=0;i<players.size();i++){
			center.add(players.get(i));
		}
		getContentPane().add(center, BorderLayout.CENTER);
		
		setVisible(true);
	}
}