package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/**
 * The main class
 * @author Adam
 * @author Fredrik
 */

public class GomokuMain {
  
  /** 
   * Main function, entry point of the program
   * 
   * @param args
   */
  public static void main(String[] args) {
    // A single argument providing a port number should be given
    if (args.length > 1) {
      throw new IllegalArgumentException("No more than one argument may be provided");
    }
    
    int port = 4000;
    if (args.length == 1) {
      port = Integer.parseInt(args[0]);
    }

    GomokuClient client = new GomokuClient(port);
    GomokuGameState gamestate = new GomokuGameState(client);
    GomokuGUI GUI = new GomokuGUI(gamestate, client);
  }
}
