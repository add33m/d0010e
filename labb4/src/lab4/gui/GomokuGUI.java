package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;

	private JFrame frame;
  
  // Necessary GUI components
  private GamePanel gameGridPanel;
  private JLabel messageLabel;
  private JButton connectButton;
  private JButton newGameButton;
  private JButton disconnectButton;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){

    // TODO: THIS IS BROKEN AAAHH FIX

		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
    gameGridPanel = new GamePanel(g.getGameGrid());
    messageLabel = new JLabel("Message");
    messageLabel.setBounds(20, 20, 100, 30);
	  connectButton = new JButton("Connect");
	  connectButton.setBounds(50,100,100,50);
	  newGameButton = new JButton("New game");
	  disconnectButton = new JButton("Disconnect");

		frame = new JFrame("Gomoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Box vBox = Box.createVerticalBox();
    Box hBox = Box.createHorizontalBox();
    vBox.add(gameGridPanel);

		frame.getContentPane().add(gameGridPanel);
		frame.getContentPane().add(messageLabel);
		frame.getContentPane().add(connectButton);
		frame.getContentPane().add(newGameButton);
		frame.getContentPane().add(disconnectButton);

		frame.setLocation(0, 0);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setSize(400,400);
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
