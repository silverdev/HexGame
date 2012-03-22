package com.sam.hex.errors;

import com.sam.hex.PlayingEntity;

public class TimeSyncError extends Exception {

	public TimeSyncError(PlayingEntity player) {
		System.out.println("the new turn is marked out of order with the old turn!");
		System.out.println("turns must be made in order. ie 1, 2, 3 ,4");
		player.error("TimeSyncError");
	}

}
