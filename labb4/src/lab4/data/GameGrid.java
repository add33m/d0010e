package lab4.data;

import java.util.Observable;

import util.Placed;
import util.Player;

/**
 * Represents the 2-d game grid
 * @author Adam
 * @author Fredrik
 */

public class GameGrid extends Observable {

	private int size;
	private Placed[][] grid;

	private final int INROW = 5;

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.size = size;
		grid = new Placed[size][size];

		// Example of 2D array: (x, y are flipped from the below example)
		// grid = new Placed[][]{
		// new Placed[]{Placed.EMPTY, Placed.EMPTY, Placed.EMPTY},
		// new Placed[]{Placed.EMPTY, Placed.EMPTY, Placed.EMPTY},
		// new Placed[]{Placed.EMPTY, Placed.EMPTY, Placed.EMPTY},
		// };

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				grid[x][y] = Placed.EMPTY;
			}
		}
	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public Placed getLocation(int x, int y) {
		return grid[x][y];

	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x      the x position
	 * @param y      the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, Player player) {
		if (!checkXY(x, y)) {
			return false;
		}
		
		if (grid[x][y] != Placed.EMPTY) {
			return false;
		} else if (player == Player.ME) {
			grid[x][y] = Placed.ME;
			return true;
		} else {
			grid[x][y] = Placed.OTHER;
			return true;
		}
	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				grid[x][y] = Placed.EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(Player player) {
		// TODO: Implement this!
		return false;
	}

	// Private method to check if x and y are valid positions within the game board
	private boolean checkXY(int x, int y) {
		return (x >= 0 && x < this.size &&
				y >= 0 && y < this.size);
	}

}
