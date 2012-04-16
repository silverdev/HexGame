package com.sam.hex.lan;

import java.awt.Point;
import java.util.ArrayList;

public class LANGlobal {
	public static ArrayList<LocalNetworkObject> localObjects = new ArrayList<LocalNetworkObject>();
	public static LocalNetworkObject localPlayer = new LocalNetworkObject("", null);
	public static String LANipAddress;
	public final static String MULTICASTADDRESS = "234.235.236.237"; 
	
	public final static int MULTICASTPORT = 4080;
	public final static int PLAYERPORT = 4081;
	public final static int CHALLENGERPORT = 4082;
	
	/**
	 * A point parsed from the LAN player
	 * */
	public static Point hex;
	/**
	 * A flag for undo. Checked if undo originated on the other phone
	 * */
	public static boolean undoRequested = false;
	
	public static String playerName;
	public static int playerColor;
	public static int gridSize;
	
	public static int undoNumber = 0;
}