package interfaces;

import java.util.NoSuchElementException;

public interface Queue {
  public void add(Object item);
  public void removeFirst() throws NoSuchElementException;
  public Object first() throws NoSuchElementException;
  public int maxSize();
  public boolean isEmpty();
  public int size();
  public String toString();
  public boolean equals(Object f) throws ClassCastException;
}
