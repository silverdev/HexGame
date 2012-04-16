/**
 * 
 */
package com.sam.hex;

/**
 * @author Sam Laane
 *
 */
public interface PlayingEntity {
	/**
	 * Must call GameAction.makeMove() eventually
	 * 
	 * Logic for making a move is here.
	 * */
	public void getPlayerTurn();	
	
	/**
	 * Will you allow an undo?
	 * Return true if your PlayingEntity supports undo
	 * Return false if it doesn't or if you want an asynchronous undo
	 * (such as in LAN or Net play)
	 * */
	public boolean supportsUndo();
	
	/**
	 * Undo has been applied. The last move is blank again.
	 * If you're an AI and keep a bunch of variables, roll them back
	 * */
	public void undoCalled();
	
	/**
	 * Will you allow a new game?
	 * Return true if your PlayingEntity supports new games
	 * Return false if it doesn't or if you want an asynchronous new game
	 * (such as in LAN or Net play)
	 * */
	public boolean supportsNewgame();
	
	/**
	 * New game has been applied. The entire game board is wiped. 
	 * Do not call GameAction.makeMove() and quit as soon as possible.
	 * */
	public void newgameCalled();
	
	/**
	 * The player has decided they want to save this game.
	 * Return true if your PlayingEntity supports saving.
	 * Return false if it doesn't.
	 * If you return false, the default human player will be saved instead.
	 * */
	public boolean supportsSave();
	
	/**
	 * The player's color has been changed!
	 * Locally, all the pieces have been swapped to the new color.
	 * This is only useful for LAN play.
	 * */
	public void colorChanged();
	
	/**
	 * The player's name has been changed!
	 * Locally, the new name has been switched to.
	 * This is only useful for LAN play.
	 * */
	public void nameChanged();
	
	/**
	 * The game is over. Die gracefully.
	 * */
	public void quit();
	
	/**
	 * You won the game!
	 * Use this to handle any final actions such as sending
	 * final moves or comparing game boards to make sure 
	 * no one cheated. Remember, you're still alive 
	 * until quit() is called.
	 **/
	public void win();
	
	/**
	 * You lost the game!
	 * Use this to handle any final actions such as sending
	 * final moves or comparing game boards to make sure 
	 * no one cheated. Remember, you're still alive 
	 * until quit() is called.
	 **/
	public void lose();
}
