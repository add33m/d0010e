package lab2.level;

import java.util.Observable;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Level extends Observable {
  ArrayList<Room> rooms;
  Room currentRoom;
  boolean blockRooms = false; // komma ihåg om firstLocation() har körts

  public Level() {
    this.rooms = new ArrayList<Room>();
  }

  public boolean place(Room r, int x, int y) {
    // Don't allow more rooms after firstLocation() has been called
    if (blockRooms) {
      return false;
    }

    // Check if room collides with an existing room
    Rectangle newRoomBounds = new Rectangle(x, y, r.width, r.height);
    for (Room room : this.rooms) {
      Rectangle roomBounds = new Rectangle(room.x, room.y, room.width, room.height);
      if (newRoomBounds.intersects(roomBounds)) {
        return false;
      }
    }
    // If it doesn't collide with any existing rooms, add the new room and save its
    // coordinates
    r.x = x;
    r.y = y;
    this.rooms.add(r);
    return true;
  }

  public void firstLocation(Room r) {
    if (!blockRooms) {
      this.currentRoom = r;
    }
    this.blockRooms = true;
  }

  void move(char direction) { // Flyttar spelare till nästa rum
    switch (direction) {
      case 'n':
        if (currentRoom.northDoor != null) {
          currentRoom = currentRoom.northDoor;
          setChanged();
          notifyObservers();
        }
        break;
      case 's':
        if (currentRoom.southDoor != null) {
          currentRoom = currentRoom.southDoor;
          setChanged();
          notifyObservers();
        }
        break;
      case 'e':
        if (currentRoom.eastDoor != null) {
          currentRoom = currentRoom.eastDoor;
          setChanged();
          notifyObservers();
        }
        break;
      case 'w':
        if (currentRoom.westDoor != null) {
          currentRoom = currentRoom.westDoor;
          setChanged();
          notifyObservers();
        }
        break;
      default:
        break;
    }

  }

}
