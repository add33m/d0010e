package lab4;

import lab4.client.GomokuClient;

public class GomokuMain {
  // A single argument providing a port number should be given
  public static void main(String[] args) {
    
    GomokuClient client = new GomokuClient(1234);
  }
}
