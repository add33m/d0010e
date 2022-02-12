package interfaces;

import java.util.NoSuchElementException;

public interface Graph {
  public void addNode(int id, int x, int y);
  public void addEdge(int id1, int id2, int weight) throws NoSuchElementException;
}
