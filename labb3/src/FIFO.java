import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FIFO implements interfaces.Queue {
  private ArrayList<Object> queue;
  private int maxSize;

  // Constructor, creates the ArrayList representing the queue
  public FIFO() {
    queue = new ArrayList<Object>();
  }
  
  // This method returns the number of elements in this queue.
  public int size() {
    return queue.size();
  }

  // This method returns the maximum size this queue has had since it was created.
  public int maxSize() {
    return maxSize;
  }

  // This method returns true if, and only if, the size of this queue is 0. Otherwise, false is returned.
  public boolean isEmpty() {
    return this.size() == 0;
  }

  // This method returns the first element in the queue. If the queue is empty, a NoSuchElementException is thrown.
  public Object first() throws NoSuchElementException {
    if (this.isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    return queue.get(0);
  }

  /**
    This method throws a ClassCastException if f is not of the same type as this class. This method
    returns true if, and only if, all the following holds:
    • this and f have the same size.
    • For every position i in this queue:
      – If the element at position i is null, the corresponding element at position i in f also is
        null.
      – If the element at position i instead is a reference to an object obj1, the corresponding
        element at position i in f is also a reference to an object obj2, and obj1.equals(obj2)
        is true.
    Otherwise, this method returns false. In particular, it does not throw any exception, such as a
    ClassCastException.
    */
  public boolean equals(Object f) throws ClassCastException {
    if (!(f instanceof FIFO)) {
      throw new ClassCastException("f is not of the type FIFO");
    }

    // We know other object is of type FIFO, so cast it
    FIFO other = (FIFO) f;

    // Check if sizes are equal
    if (this.size() != other.size()) {
      return false;
    }

    // Check if elements are equal
    for (int i=0; i<this.size(); i++) {
      Object thisObj = this.queue.get(i);
      Object otherObj = other.queue.get(i);

      // Both being null is okay, so skip the below check
      if (thisObj == null && otherObj == null) {
        continue;
      }
      // If object in this is an object, must be the same as in other
      if (thisObj == null || otherObj == null || thisObj != otherObj || !thisObj.equals(otherObj)) {
        return false;
      }
    }

    // If check is passed without returning false, 
    return true;
  }

  /**
    This method returns a string beginning with "Queue: " followed by the following, for each element
    elem in the queue:
      "(" + String.valueOf(elem) + ") "
    Note that the elements must be listed in order. Note that definition forces the string to end with
    a whitespace.
    */
  public String toString() {
    String result = "Queue: ";
    for (Object elem : queue) {
      result += "(" + String.valueOf(elem) + ") ";
    }
    return result;
  }
  
  // This method adds the object item to the end of the queue
  public void add(Object item) {
    queue.add(item);
  }

  // This method removes the first object from the queue. If the queue is empty, a NoSuchElementException is thrown.
  public void removeFirst() throws NoSuchElementException {
    if (this.isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    queue.remove(0);
  }
}
