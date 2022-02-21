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
  private int lastPlacedX = 0; // 0 is used because a default value will be necessary
  private int lastPlacedY = 0;

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
    if (!checkXY(x, y)) {
      return null;
    }

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
    } else {
      
      if (player == Player.ME) {
        grid[x][y] = Placed.ME;
      } else {
        grid[x][y] = Placed.OTHER;
      }
      
      // Remember that this is the latest square to be placed
      lastPlacedX = x;
      lastPlacedY = y;

      setChanged();
      notifyObservers();

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
   * Check if a player has X in row
   * 
   * @param player the player to check for
   * @return true if player has 5 in row, false otherwise
   */
  public boolean isWinner(Player player) {
    // Only check the latest placed square, since that's the only place where a winning move could have occurred
    return (
      isWinnerAlongAxis(player, lastPlacedX, lastPlacedY, 1, 0) ||
      isWinnerAlongAxis(player, lastPlacedX, lastPlacedY, 0, 1) ||
      isWinnerAlongAxis(player, lastPlacedX, lastPlacedY, 1, 1) ||
      isWinnerAlongAxis(player, lastPlacedX, lastPlacedY, 1, -1)
    );
  }

  
  /**
   * Looks for the win condition around the defined point (x0, y0) along the given axis (xstep, ystep)
   * 
   * @param player
   * @param x0
   * @param y0
   * @param xstep
   * @param ystep
   * @return boolean
   */
  private boolean isWinnerAlongAxis(Player player, int x0, int y0, int xstep, int ystep) {
    int winCounter = 0;

    for (int pos=(-INROW+1); pos<INROW; pos++) {
      int x = x0 - pos*xstep;
      int y = y0 - pos*ystep;

      if (player == Player.ME && getLocation(x, y) == Placed.ME ||
          player == Player.OTHER && getLocation(x, y) == Placed.OTHER
      ) {
        // If the right player is found, increment the counter
        winCounter++;

        // If the win condition is met, a winner is found!
        if (winCounter >= INROW) {
          return true;
        }

      } else {
        // If the wrong player or an empty square is found, reset the counter
        winCounter = 0;
      }
    }

    return false;
  }

  
  /** 
   * Private method to check if x and y are valid positions within the game board
   * 
   * @param x
   * @param y
   * @return boolean
   */
  private boolean checkXY(int x, int y) {
    return (x >= 0 && x < this.size &&
        y >= 0 && y < this.size);
  }

}
