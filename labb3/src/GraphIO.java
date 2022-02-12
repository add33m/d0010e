import java.util.NoSuchElementException;
import java.util.Scanner;

public class GraphIO implements Graph {

  public void addEdge(int arg0, int arg1, int arg2) throws NoSuchElementException {
    // TODO Auto-generated method stub
    
  }

  public void addNode(int arg0, int arg1, int arg2) {
    // TODO Auto-generated method stub
    
  }

  static public void readFile(Graph g, String filename) {
    try {
      Scanner s = new Scanner(filename);
      
      s.nextLine();  
    } catch (Exception e) {
      //TODO: handle exception
    }
  }
  
}
