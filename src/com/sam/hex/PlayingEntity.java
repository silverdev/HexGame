/**
 * 
 */
package com.sam.hex;

/**
 * @author Sam Laane
 *
 */
public interface PlayingEntity {

	public void getPlayerTurn(byte[][] gameBoard);
	public void getPlayerTurn();
	public void undoCalled();
	public void error(String errorName);
	
	
	
}
