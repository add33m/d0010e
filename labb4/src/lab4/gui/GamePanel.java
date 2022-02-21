package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;
import util.Placed;

/**
 * A panel providing a graphical view of the game board
 * @author Adam
 * @author Fredrik
 */

public class GamePanel extends JPanel implements Observer {

  private final int UNIT_SIZE = 20;
  private GameGrid grid;

  /**
   * The constructor
   * 
   * @param grid The grid that is to be displayed
   */
  public GamePanel(GameGrid grid) {
    this.grid = grid;
    grid.addObserver(this);
    Dimension d = new Dimension(grid.getSize() * UNIT_SIZE + 1, grid.getSize() * UNIT_SIZE + 1);
    this.setPreferredSize(new Dimension(UNIT_SIZE * grid.getSize(), UNIT_SIZE * grid.getSize()));
    this.setBackground(Color.WHITE);
  }

  /**
   * Returns a grid position given pixel coordinates
   * of the panel
   * 
   * @param x the x coordinates
   * @param y the y coordinates
   * @return an integer array containing the [x, y] grid position
   */
  public int[] getGridPosition(int x, int y) {
    int xpos = Math.floorDiv(x, UNIT_SIZE);
    int ypos = Math.floorDiv(y, UNIT_SIZE);
    return new int[] { xpos, ypos };
  }

  
  /**
   * Method predefined by Håkan to repaint the grid when an update is received
   * 
   * @param arg0
   * @param arg1
   */
  public void update(Observable arg0, Object arg1) {
    this.repaint();
  }

  /**
   * Called when the JPanel GUI object is to be redrawn
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawGrid(g);
  }

  /**
   * Private method used to draw up the grid
   * @param g The graphics object used to draw
   */
  private void drawGrid(Graphics g) {
    int size = grid.getSize();

    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        g.setColor(Color.black);
        g.drawRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

        // Draw cross inset by .1 unit
        if (grid.getLocation(x, y) == Placed.ME) {
          g.setColor(Color.red);

          int inset = Math.round(UNIT_SIZE*.1f);

          // ritar linje topleft till bottom right
          g.drawLine(x*UNIT_SIZE + inset, y*UNIT_SIZE + inset, 
                    (x+1)*UNIT_SIZE - inset, (y+1)*UNIT_SIZE  - inset);
          // ritar linje från bottomleft till top right
          g.drawLine(x*UNIT_SIZE + inset, (y+1)*UNIT_SIZE - inset,
                    (x+1)*UNIT_SIZE - inset, y*UNIT_SIZE + inset);
        }

        // Draw circle inset by .1 unit
        else if(grid.getLocation(x, y) == Placed.OTHER){
          g.setColor(Color.blue);
          
          int inset = Math.round(UNIT_SIZE*.1f);
          g.drawOval(x*UNIT_SIZE + inset, y*UNIT_SIZE + inset, UNIT_SIZE - 2*inset, UNIT_SIZE - 2*inset);
        }
      }
    }
  }

}
