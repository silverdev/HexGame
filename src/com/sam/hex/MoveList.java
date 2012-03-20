package com.sam.hex;

public class MoveList {
	
	Move thisMove;
	MoveList nextMove;
	public MoveList(){
		
	}
	
	public MoveList(int x,int y,byte teamNumber){
		thisMove= new Move(x,y,teamNumber,Global.moveNumber);
	}
	
	public MoveList(MoveList oldMove,int x,int y,byte teamNumber){
		thisMove= new Move(x,y,teamNumber,Global.moveNumber);
		nextMove=oldMove;
	}
	public Move getmove(){
		return thisMove;
	}
	/* do not use makeMove might not work with
	 * base cases and is not tested*/
	public void makeMove(int x,int y,byte teamNumber){
		nextMove=new MoveList(nextMove,thisMove.x,thisMove.y,teamNumber);
		thisMove= new Move(x,y,teamNumber,Global.moveNumber);
	}
	public void undo(){
		if (thisMove==null) return; 
		Global.gamePiece[thisMove.x][thisMove.y].setTeam((byte) 0);
		thisMove=nextMove.thisMove;
		nextMove=nextMove.nextMove;
		
	}
	public void undoTwo(){
		if (thisMove==null)return;
		nextMove.undo();
		undo();
	}
	//for replays
	public void replay(int time){
		if (thisMove==null) return;
		//if (time!=0)GameAction.updateBoard();
		if (nextMove!=null) nextMove.replay(time);
		Global.gamePiece[thisMove.x][thisMove.y].setTeam(thisMove.team);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
}
