/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import util.GameState;
import util.Player;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

	// Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

	// Possible game states
	private final int NOT_STARTED = 0;
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
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y) {
		gameGrid.move(x, y, Player.ME);
	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		// TODO: Lite osäker på vad som ska göras här, behöver eventuellt skrivas om
		client.sendNewGameMessage();
	}

	/**
	 * Other player has requested a new game, so the
	 * game state is changed accordingly
	 */
	public void receivedNewGame() {
		
	}

	/**
	 * The connection to the other player is lost,
	 * so the game is interrupted
	 */
	public void otherGuyLeft() {
		message = "The other player has disconnected, game was interrupted";
	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		message = "You have disconnected from the game";
		client.disconnect();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		this.gameGrid.move(x, y, Player.OTHER);
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
