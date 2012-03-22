package com.sam.hex;

import java.io.Serializable;

public class Move implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -7439386690818203133L;
private  int x;
private int y;
private int time;
private byte team;
public Move(int x, int y, byte team, int time){
	
	this.setX(x);
	this.setY(y);
	this.setTime(time);
	this.setTeam(team);
}
protected void setTime(int time) {
	this.time = time;
}
public int getTime() {
	return time;
}
protected void setX(int x) {
	this.x = x;
}
public int getX() {
	return x;
}
protected void setY(int y) {
	this.y = y;
}
public int getY() {
	return y;
}
protected void setTeam(byte team) {
	this.team = team;
}
public byte getTeam() {
	return team;
}

}
