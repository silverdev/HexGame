package com.sam.hex;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sam.hex.replay.SavedGameObject;



public class DialogBoxes {
	
	public static void resetGameOption() {

		Object[] options = { "Yes, make it so", "Cancel" };
		byte b =(byte) JOptionPane.showOptionDialog(Global.window,
				"are you sure you want to reset all your options\n all your personal settings will be lost",
				"Are you sure", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (b==0){
			//Clear Preferences
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			
			try {
				prefs.clear();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			//stop the old game
			//Global.runningGame.stop();
			GameAction.stopGame();
			
			// restore Defalts
		Hexgame.grabPreferences();
			//restart game
			
			Global.window.initRegular();
			GameAction.fullUpdateBoard();
			new GameObject();
			
			
			
		}

	}

	public static String chooseGameTypePlayer1() {
		Object[] possibilities = { "Human", "Computer (Kinda easy)", "Computer (Kinda hard)" };
		String s;
		
			s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a type of game:\n", "Game Type",
				JOptionPane.PLAIN_MESSAGE, null, possibilities,
				possibilities[Global.player1Type]);
		

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (s=="Human") Global.player1Type=0;
			else if (s=="Computer (Kinda easy)") Global.player1Type=1;
			else if (s=="Computer (Kinda hard)") Global.player1Type=2;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.putInt("player1Type", Global.player1Type);
			return s;
		}

		return null;
	}

	public static String chooseGameTypePlayer2() {
		Object[] possibilities = { "Human", "Computer (Kinda easy)", "Computer (Kinda hard)" };
		String s;
			s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a type of game:\n", "Game Type",
				JOptionPane.PLAIN_MESSAGE, null, possibilities,
				possibilities[Global.player2Type]);
		

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (s=="Human") Global.player2Type=0;
			else if (s=="Computer (Kinda easy)") Global.player2Type=1;
			else if (s=="Computer (Kinda hard)") Global.player2Type=2;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.putInt("player2Type", Global.player2Type);
			return s;
		}

		return null;
	}

	public static String chooseName1() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose name for player one:\n", "Player One",
				JOptionPane.PLAIN_MESSAGE, null, null, Global.player1Name);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			Global.player1Name = s;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.put("player1Name", Global.player1Name);
			return s;
		}

		return null;

	}

	public static int chooseColor1() {
		final JColorChooser chooser = new JColorChooser(Global.player1Color);
		JColorChooser.createDialog(Global.window, "Pick a color", true, chooser, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.player1Color=chooser.getColor();
				Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
				prefs.putInt("player1Color", Global.player1Color.getRGB());
			}}, null).setVisible(true);
		
		return Global.player1Color.getRGB();
	}

	public static String chooseName2() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose name for player two:\n", "Player Two",
				JOptionPane.PLAIN_MESSAGE, null, null, Global.player2Name);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			Global.player2Name = s;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.put("player2Name", Global.player2Name);
			return s;
		}

		return null;

	}

	public static int choseColor2() {
		final JColorChooser chooser = new JColorChooser(Global.player2Color);
		JColorChooser.createDialog(Global.window, "Pick a color", true, chooser, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.player2Color=chooser.getColor();
				Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
				prefs.putInt("player2Color", Global.player2Color.getRGB());
			}}, null).setVisible(true);
		
		return Global.player2Color.getRGB();
	}
	public static int chooseGridsize() {

		String s = (String) JOptionPane.showInputDialog(Global.window,
				"Choose a new grid size:\n", "Grid Size",
				JOptionPane.PLAIN_MESSAGE, null, null, Global.gridSize);
		
		int newSize = Global.gridSize;
		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0) && (s.matches("[0-9]") || s.matches("[0-9][0-9]"))) {
			try {
				newSize = Integer.parseInt(s);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
				newSize = Global.gridSize;
			}
			if (newSize<4){
				newSize=4;
			}
			Global.gridSize=newSize;
			Preferences prefs = Preferences.userNodeForPackage(Hexgame.class);
			prefs.putInt("gridSize", Global.gridSize);
		}
		return newSize;
	}
	
	public static void announceWinner(int team) {
		String name;
		if(team==1) name=Global.player1Name;
		else name=Global.player2Name;
		Object[] options = { "Ok", "Save Replay" };
		byte b =(byte) JOptionPane.showOptionDialog(Global.window,
				name+" wins!",
				"Do you want to save a replay?", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (b==1)saveReplay();
	}
	public static void saveReplay() {
		try {
			File file = saveReplayfile();
			if(file!=null){
				String filePath = file.getPath();
				if(!filePath.toLowerCase().endsWith(".rhex"))
				{
				    file = new File(filePath + ".rhex");
				}
				
				FileOutputStream saveFile = new FileOutputStream(file);
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				SavedGameObject savedGame = new SavedGameObject(Global.player1Color.getRGB(), Global.player2Color.getRGB(), Global.player1Name, Global.player2Name, Global.moveList, Global.gridSize, Global.moveNumber);
				save.writeObject(savedGame);
				save.close();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Global.window,
					"File Not Found Nothing Saved",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Global.window,
					"File Could not be written",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	public static void loadReplay() {
		try {
			File file = loadReplayFile();
			if(file!=null){
				FileInputStream saveFile = new FileInputStream(file);
				ObjectInputStream restore = new ObjectInputStream(saveFile);
				SavedGameObject savedGame = (SavedGameObject) restore.readObject();
				Global.player1Color = new Color(savedGame.player1Color);
				Global.player2Color = new Color(savedGame.player2Color);
				Global.player1Name = savedGame.player1Name;
				Global.player2Name = savedGame.player2Name;
				Global.moveList = savedGame.moveList;
				Global.gridSize = savedGame.gridSize;
				restore.close();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Global.window,
					"File Not Found",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Global.window,
					"File could not be read",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(Global.window,
					"ClassNotFoundException",
					"ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}	
	}
	
	public static File loadReplayFile() {
		JFileChooser theFileToLoad = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Replay Hexboards", "rhex");
	    theFileToLoad.setFileFilter(filter);
	    int returnVal = theFileToLoad.showOpenDialog(Global.window);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	return theFileToLoad.getSelectedFile();
	    }
	    else{
	    	return null;
	    }
		
	}
	public static File saveReplayfile() {
		JFileChooser theFileToSave = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Replay Hexboards", "rhex");
	    theFileToSave.setFileFilter(filter);
	    int returnVal = theFileToSave.showSaveDialog(Global.window);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	return theFileToSave.getSelectedFile();
	    }
	    else{
	    	return null;
	    }
	}
}