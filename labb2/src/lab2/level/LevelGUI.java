package lab2.level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LevelGUI implements Observer {

  private Level lv;
  private Display d;

  public LevelGUI(Level level, String name) {

    this.lv = level;

    JFrame frame = new JFrame(name);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    d = new Display(lv, 500, 500);

    frame.getContentPane().add(d);
    frame.pack();
    frame.setLocation(0, 0);
    frame.setVisible(true);
  }

  public void update(Observable arg0, Object arg1) {

  }

  private class Display extends JPanel {
    Level lv;

    public Display(Level fp, int x, int y) {
      this.lv = fp;

      addKeyListener(new Listener());

      setBackground(Color.GREEN);
      setPreferredSize(new Dimension(x + 20, y + 20));
      setFocusable(true);
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      makeRoom(g);
      makeCorridor(g);
      makePlayer(g);
    }

    private void makeRoom(Graphics g) {
      for (Room room : this.lv.rooms) {
        g.setColor(Color.BLACK);
        g.drawRect(room.x, room.y, room.width, room.height);
        g.setColor(room.floorColor);
        g.fillRect(room.x, room.y, room.width, room.height);
      }
    }

    private void makeCorridor(Graphics g) {
      for (Room room : this.lv.rooms) {

        if (room.westDoor != null) {
          g.setColor(room.westDoor.floorColor); //Sätter dörren till nästa rums färg
          g.fillRect(room.x, room.y + 4 * (room.height / 10), 3, 2 * (room.height / 10)); //Ritar dörr
        }
        if (room.eastDoor != null) {
          g.setColor(room.eastDoor.floorColor);
          g.fillRect(room.x + room.width - 3, room.y + 4 * (room.height / 10), 3, 2 * (room.height / 10));
        }
        if (room.northDoor != null) {
          g.setColor(room.northDoor.floorColor);
          g.fillRect(room.x + 4 * (room.width / 10), room.y, 3 * (room.width / 10), 3);
        }
        if (room.southDoor != null) {
          g.setColor(room.southDoor.floorColor);
          g.fillRect(room.x + 4 * (room.width / 10), room.y + room.height - 3, 3 * (room.width / 10), 3);
        }
      }
    }

    private void makePlayer(Graphics g){ //skapar en liten cirkel för att visa vart spelaren är
      g.setColor(Color.BLACK);
      g.drawOval(lv.currentRoom.x + lv.currentRoom.width/2, lv.currentRoom.y + lv.currentRoom.height/2, 10, 10);
      Color dataGul = new Color(255, 204, 17);
      g.setColor(dataGul);
      g.fillOval(lv.currentRoom.x + lv.currentRoom.width/2, lv.currentRoom.y + lv.currentRoom.height/2, 10, 10);

    }



    private class Listener implements KeyListener {

      public void keyPressed(KeyEvent arg0) {
      }

      public void keyReleased(KeyEvent arg0) {
      }

      public void keyTyped(KeyEvent event) {
      }
    }

  }

}
