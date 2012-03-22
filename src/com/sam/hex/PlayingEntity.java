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
	public void getPlayerTurn(byte[][] gameBoard);
	//gets the turn
	public void getPlayerTurn();
	//Notifies the ai an undo was Called
	public void undoCalled();
	//Notifies the ai of an error and its name
	public void error(String errorName);
	
	
	
}
