/**
 * 
 */
package com.sam.hex;

/**
 * @author Sam Laane
 *
 */
public interface PlayingEntity {
	//gets the turn
	public void getPlayerTurn();
	//Notifies the ai an undo was Called
	public void undoCalled();
	
	//alows ai to control what players can and cant do.
	public boolean supportsUndo();
	public boolean supportsSave();
	
	
	
	
}
