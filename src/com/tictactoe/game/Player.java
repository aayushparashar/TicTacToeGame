package com.tictactoe.game;

public enum Player {
	COMPUTER("X"), USER("O"), EMPTY("-");
	Player(String text) {
		this.text = text;
	}

	private final String text;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.text;
	}

}
