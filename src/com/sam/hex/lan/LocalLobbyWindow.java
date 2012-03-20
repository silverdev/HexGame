package com.sam.hex.lan;

import java.net.MulticastSocket;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.sam.hex.Global;

public class LocalLobbyWindow extends JFrame {
	private static final long serialVersionUID = -4844955816069056271L;
	MulticastListener listener;
	MulticastSender sender;
	MulticastSocket socket;
	public static LocalNetworkObject lno = new LocalNetworkObject("", null);
	
	public LocalLobbyWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		setSize(Global.windowWidth, Global.windowHeight);

		setLocationRelativeTo(null);
		
		setVisible(true);
	}
}