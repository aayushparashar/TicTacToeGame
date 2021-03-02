package com.tictactoe.game;

import java.util.Random;

public class Game {
	private Board board;
	private Random rand;

	Game() {
		intializeGame();
		displayBoard();
		makeFirstMove();
		playGame();
		checkStatus();
	}

	private void playGame() {
		while (board.isRunning()) {
			board.makeUserInput();
			if (!board.isRunning())
				break;
			board.callMinimax(0, Player.COMPUTER);
			for(Cell cell: board.getRootValues())
				System.out.println("Cell value: "+cell+" minmax value: "+cell.getMinimaxValue());
			board.move(Player.COMPUTER, board.getBestMove());
			displayBoard();

		}
	}

	private void checkStatus() {
		if (board.isWinning(Player.COMPUTER)) {
			System.out.println("Computer WON!");
		} else if (board.isWinning(Player.USER)) {
			System.out.println("User WON!");
		} else {
			System.out.println("DRAW....");
		}

	}

	private void makeFirstMove() {
		System.out.println("First Move? 1. Computer 2. User");
		int x = board.getScn().nextInt();
		if (x == 1) {
			int rx = rand.nextInt(3);
			int ry = rand.nextInt(3);
			board.move(Player.COMPUTER, new Cell(rx, ry));
			displayBoard();
		}

	}

	private void displayBoard() {
		board.displayBoard();
	}

	private void intializeGame() {
		this.board = new Board();
		rand = new Random();
		this.board.setupBoard();

	}

}
