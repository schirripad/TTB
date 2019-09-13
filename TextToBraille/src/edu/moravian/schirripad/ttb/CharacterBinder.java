package edu.moravian.schirripad.ttb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class CharacterBinder {

	/**
	 * 
	 * @param width
	 *            Width of pages to bind to in millimeters
	 * @param height
	 *            Height of pages to bind in millimeters
	 */
	private final float CELL_WIDTH = 9.98f, CELL_HEIGHT = 15.4f; // Each cells width in mm
	private int width, height, columnCap, rowCap;

	public CharacterBinder(int width, int height) {
		this.width = width;
		this.height = height;
		columnCap = (int) ((int) height / CELL_HEIGHT);
		rowCap = (int) ((int) width / CELL_WIDTH);
	}

	// TODO Return null, fire an event upon page completion, with the page as an
	// argument so that it can be saved incrementally, so as to free up memory
	public void bindCharacters(LinkedList<LinkedList<Image>> chars) {
		double ratio = chars.get(0).get(0).getHeight(null) / CELL_HEIGHT;
		LinkedList<Image> bound = new LinkedList<Image>();
		Image page = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) page.getGraphics();
		int wcount = 0, hcount = 0;
		for (LinkedList<Image> line : chars) {
			for (Image i : line) {
				if (wcount > rowCap) {
					wcount = 0;
					hcount++;
					if (hcount > columnCap) {
						// bound.add(page);
						Main.pageDone(page);
						page = new BufferedImage((int) (width * ratio), (int) (height * ratio),
								BufferedImage.TYPE_INT_RGB);
						g = (Graphics2D) page.getGraphics();
						wcount = 0;
						hcount = 0;
					}
				}
				g.drawImage(i, wcount * i.getWidth(null), hcount * i.getHeight(null), null);
				wcount++;
			}
		}
	}
}
