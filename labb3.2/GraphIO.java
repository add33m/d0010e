import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GraphIO {

  static public void readFile(Graph g, String filename) throws IOException {
    try {
      File f = new File(filename);
      Scanner s = new Scanner(f);

      // First, read the int representing number of 
      int nodesLeft = Integer.parseInt(s.nextLine());

      // Continue reading while file has more lines to read
      while (s.hasNext()) {
        // Split line by spaces to produce array of substrings (hopefully containing ints)
        String[] lineData = s.nextLine().split(" ");

        if (nodesLeft > 0) {
          // If a node is read, reduce the amount left
          nodesLeft--;
          g.addNode(Integer.parseInt(lineData[0]), Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]));
        } else {
          // If all nodes have been read, assume this line is an edge
          g.addEdge(Integer.parseInt(lineData[0]), Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]));
        }
      }

      // Scanner must be closed when no longer used
      s.close();

    // Handle defined precondition
    } catch (NullPointerException e) {
      // Filename is null
      throw new IOException("Filename must not be null");
    } catch (FileNotFoundException e) {
      // File wasn't found
      throw new IOException("File not found");
    }
  }
}
