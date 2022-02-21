package lab4.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

/**
 * The GUI class that handles the game window and its components
 * @author Adam
 * @author Fredrik
 */

public class GomokuGUI implements Observer {

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
   * Creates the gamewindow and all its button as well as well as implements its functions
   * @param gs The game state that the GUI will visualize
   * @param c  The client that is responsible for the communication
   */
  public GomokuGUI(GomokuGameState gs, GomokuClient c) {
    this.client = c;
    this.gamestate = gs;
    client.addObserver(this);
    gamestate.addObserver(this);

    frame = new JFrame("Gomoku");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    gameGridPanel = new GamePanel(gs.getGameGrid());
    gameGridPanel.addMouseListener(mouseListener);
    
    messageLabel = new JLabel("Message");

    // When connect button is pressed, open a connection window
    connectButton = new JButton("Connect");
    connectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Create a ConnectionWindow to allow the player to connect to another person's computer
        ConnectionWindow cw = new ConnectionWindow(c);
      }
    });

    // When new game button is pressed, call newgame in gamestate
    newGameButton = new JButton("New game");
    newGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gamestate.newGame();
      }
    });

    // When disconnect button is pressed, call disconnect in gamestate
    disconnectButton = new JButton("Disconnect");
    disconnectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gamestate.disconnect();
      }
    });

    // Vertical boxlayout contains:
    // [ gameGridPanel ]
    // [ [ connectButton, newGameButton, disconnectButton ] ] <--- this is a horizontal box layout inside the vbox
    // [ messageLabel ]

    Box vBox = Box.createVerticalBox();
    vBox.add(gameGridPanel);

    Box hBox = Box.createHorizontalBox();
    hBox.setAlignmentX(0); // Align towards left border of the window
    hBox.add(connectButton);
    hBox.add(newGameButton);
    hBox.add(disconnectButton);

    vBox.add(hBox);
    vBox.add(messageLabel);

    frame.getContentPane().add(vBox);

    frame.setLocation(0, 0);
    frame.setVisible(true);
    // vBox contains all elements properly aligned, so its preferred size is the minimum necessary size
    frame.setSize(vBox.getPreferredSize().width, vBox.getPreferredSize().height + hBox.getPreferredSize().height);
  }

  
  /**
   * Method predefined by Håkan to update the buttons in the GUI based on the connection status
   * 
   * @param arg0
   * @param arg1
   */
  public void update(Observable arg0, Object arg1) {

    // Update the buttons if the connection status has changed
    if (arg0 == client) {
      if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
        connectButton.setEnabled(true);
        newGameButton.setEnabled(false);
        disconnectButton.setEnabled(false);
      } else {
        connectButton.setEnabled(false);
        newGameButton.setEnabled(true);
        disconnectButton.setEnabled(true);
      }
    }

    // Update the status text if the gamestate has changed
    if (arg0 == gamestate) {
      messageLabel.setText(gamestate.getMessageString());
    }
  }
  
  MouseListener mouseListener = new MouseListener() {

    public void mouseClicked(MouseEvent e) {
      // Get game grid coordinates of the mouse click
      int[] gridCoords = gameGridPanel.getGridPosition(e.getX(), e.getY());
      
      // Tell game state that user wants to make a move
      gamestate.move(gridCoords[0], gridCoords[1]);
    }

    // Below methods are irrelevant for this application so just leave them empty

    public void mousePressed(MouseEvent e) {
      
    }

    public void mouseReleased(MouseEvent e) {
      
    }

    public void mouseEntered(MouseEvent e) {
      
    }

    public void mouseExited(MouseEvent e) {
      
    }
    
  };

}
