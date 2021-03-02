package com.tictactoe.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	private List<Cell> emptyCells;
	private List<Cell> rootValues;
	private Player[][] board;
	Scanner scn;

	Board() {
		initializeBoard();
	}

	public void initializeBoard() {
		this.rootValues = new ArrayList<>();
		this.board = new Player[Constants.boardSize][Constants.boardSize];
		scn = new Scanner(System.in);

	}

	public boolean isRunning() {
		if (isWinning(Player.COMPUTER) || isWinning(Player.USER) || getEmptyCells().isEmpty())
			return false;
		return true;
	}

	public List<Cell> getEmptyCells() {
		this.emptyCells = new ArrayList<>();
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				if (board[i][j] == Player.EMPTY)
					this.emptyCells.add(new Cell(i, j));
		return this.emptyCells;
	}

	public void setEmptyCells(List<Cell> emptyCells) {
		this.emptyCells = emptyCells;
	}

	public List<Cell> getRootValues() {
		return rootValues;
	}

	public void setRootValues(List<Cell> rootValues) {
		this.rootValues = rootValues;
	}

	public Player[][] getBoard() {
		return board;
	}

	public void setBoard(Player[][] board) {
		this.board = board;
	}

	public Scanner getScn() {
		return scn;
	}

	public void setScn(Scanner scn) {
		this.scn = scn;
	}

	public boolean isWinning(Player player) {
		if (board[0][0] == player && board[1][1] == player && this.board[2][2] == player)
			return true;
		if (board[2][0] == player && board[1][1] == player && this.board[0][2] == player)
			return true;
		for (int i = 0; i <= 2; i++) {
			if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
				return true;
			if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
				return true;
		}

		return false;
	}

	public void move(Player player, Cell cell) {
		this.board[cell.getX()][cell.getY()] = player;
	}

	public void displayBoard() {
		System.out.println();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++)
				System.out.print(board[i][j] + " ");
			System.out.println();
		}
	}

	public Cell getBestMove() {
		int max = Integer.MIN_VALUE;
		int best = Integer.MIN_VALUE;
		for (int i = 0; i < rootValues.size(); i++)
			if (max < rootValues.get(i).getMinimaxValue()) {
				max = rootValues.get(i).getMinimaxValue();
				best = i;
			}
		return rootValues.get(best);
	}

	public void makeUserInput() {
		System.out.println("User's move: ");
		int x = scn.nextInt();
		int y = scn.nextInt();
		Cell curr = new Cell(x, y);
		move(Player.USER, curr);
		displayBoard();
	}

	public void setupBoard() {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				board[i][j] = Player.EMPTY;
	}

	public int returnMin(List<Integer> list) {
		int minIdx = 0;
		for (int i = 1; i < list.size(); i++)
			if (list.get(minIdx) > list.get(i))
				minIdx = i;
		return list.get(minIdx);
	}

	public int returnMax(List<Integer> list) {
		int maxIdx = 0;
		for (int i = 1; i < list.size(); i++)
			if (list.get(maxIdx) < list.get(i))
				maxIdx = i;
		return list.get(maxIdx);
	}

	public void callMinimax(int depth, Player player) {
		rootValues.clear();
		minimax(depth, player);
	}

	private int minimax(int depth, Player player) {

		if (isWinning(Player.COMPUTER))
			return 1;
		if (isWinning(Player.USER))
			return -1;
		List<Cell> availableCells = getEmptyCells();
		if (availableCells.isEmpty())
			return 0;
		List<Integer> scores = new ArrayList<>();
		for (Cell currCell : availableCells) {
			if (player == Player.COMPUTER) {
				move(Player.COMPUTER, currCell);
				int currScore = minimax(depth + 1, Player.USER);
				scores.add(currScore);
				if (depth == 0) {
					currCell.setMinimaxValue(currScore);
					rootValues.add(currCell);
				}
			} else if (player == Player.USER) {
				move(Player.USER, currCell);
				int currScore = minimax(depth + 1, Player.COMPUTER);
				scores.add(currScore);
			}
			board[currCell.getX()][currCell.getY()] = Player.EMPTY;
		}
		if (Player.COMPUTER == player)
			return returnMax(scores);
		else
			return returnMin(scores);

	}

}
