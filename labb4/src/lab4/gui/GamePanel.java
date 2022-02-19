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
		// this.setMinimumSize(d);
		this.setPreferredSize(new Dimension(300, 300));
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

	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		System.out.println("GamePanel was redrawn");

		super.paintComponent(g);
		drawGrid(g);
	}

	private void drawGrid(Graphics g) {
		int size = grid.getSize();

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				g.setColor(Color.black);
				g.drawRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				// g.setColor(Color.red);
				// g.fillRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

				if (grid.getLocation(x, y) == Placed.ME) {
					g.setColor(Color.red);
					g.drawLine(x*UNIT_SIZE, y*UNIT_SIZE, (x+1)*UNIT_SIZE, (y+1)*UNIT_SIZE); // ritar linje topleft till bottom right
					g.drawLine(x*UNIT_SIZE, (y+1)*UNIT_SIZE, (x+1)*UNIT_SIZE, y*UNIT_SIZE); // ritar linje från bottomleft till top right

				}

				else if(grid.getLocation(x, y) == Placed.OTHER){
					g.drawRect(x*UNIT_SIZE +5, y*UNIT_SIZE + 3, 5, 5);
				}
			}
		}
	}

}
