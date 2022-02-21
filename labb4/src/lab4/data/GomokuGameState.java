/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import util.GameState;
import util.Placed;
import util.Player;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

	// Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

	private GameState currentState;

	private GomokuClient client;

	private String message;

	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc) {
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = GameState.NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return message;
	}

	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * First, check if the square coordinates is valid
	 * Then check if the the correct game state is active (game running, our turn)
	 * Then check if the move is legal (square is empty)
	 * If something fails, set message to something appropriate and notify observers to update
	 * If nothing fails, make the move in gameGrid
	 * 
	 * If move makes the game end (by winning), end the game!
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y) {
		// Check if coordinates are valid
		if (x < 0 || x >= gameGrid.getSize() || y < 0 || y >= gameGrid.getSize()) {
			return;
		}

		// Check if game is in the right state
		if (currentState != GameState.MY_TURN) {
			message = "It's not your turn to move";
			setChanged();
			notifyObservers();

			return;
		}


		// Check if square is empty
		if (gameGrid.getLocation(x, y) == Placed.EMPTY) {
			// Everything ok, make move
			gameGrid.move(x, y, Player.ME);

			// Check if move was a winning move, and provide an appropriate response
			if (gameGrid.isWinner(Player.ME)) {
				client.sendMoveMessage(x, y);
				message = "You won";
				currentState = GameState.FINISHED;
			} else {
				client.sendMoveMessage(x, y);
				message = "Opponent's turn";
				currentState = GameState.OTHER_TURN;
			}
		} else {
			message = "You can only make moves in empty squares";
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		if (currentState != GameState.NOT_STARTED) {
			gameGrid.clearGrid();
			currentState = GameState.OTHER_TURN;
			message = "New game started, opponent's turn";
			client.sendNewGameMessage();
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Other player has requested a new game, so the
	 * game state is changed accordingly
	 */
	public void receivedNewGame() {
		gameGrid.clearGrid();
		currentState = GameState.MY_TURN;
		message = "A new game has been started, Its your Turn";
		setChanged();
		notifyObservers();
	}

	/**
	 * The connection to the other player is lost,
	 * so the game is interrupted
	 */
	public void otherGuyLeft() {
		gameGrid.clearGrid();
		currentState = GameState.MY_TURN;
		message = "The other player has disconnected, game was interrupted";
		setChanged();
		notifyObservers();
	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		gameGrid.clearGrid();
		message = "You have disconnected from the game";
		setChanged();
		notifyObservers();
		
		client.disconnect();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		gameGrid.move(x, y, Player.OTHER);
		
		if (gameGrid.isWinner(Player.OTHER)) {
			message = "Your opponent has won the game";
			currentState = GameState.FINISHED;
		} else {
			message = "Your turn";
			currentState = GameState.MY_TURN;
		}

		setChanged();
		notifyObservers();
	}

	public void update(Observable o, Object arg) {

		switch (client.getConnectionStatus()) {
			case GomokuClient.CLIENT:
				message = "Game started, it is your turn!";
				currentState = GameState.MY_TURN;
				break;
			case GomokuClient.SERVER:
				message = "Game started, waiting for other player...";
				currentState = GameState.OTHER_TURN;
				break;
		}
		setChanged();
		notifyObservers();
	}

}
