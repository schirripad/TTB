package edu.moravian.schirripad.ttb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;

import edu.moravian.edu.ttb.book.Book;

public class CharacterBinder {

	/**
	 * 
	 * @param width
	 *            Width of pages to bind to in millimeters
	 * @param height
	 *            Height of pages to bind in millimeters
	 * @param output
	 *            Directory to write output files to
	 */
	private final float CELL_WIDTH = 9.98f, CELL_HEIGHT = 15.4f; // Each cells width in mm
	private int width, height, columnCap, rowCap;
	private File out;

	public CharacterBinder(int width, int height, File output) {
		out = output;
		this.width = width;
		this.height = height;
		columnCap = (int) ((int) height / CELL_HEIGHT);
		rowCap = (int) ((int) width / CELL_WIDTH);
	}

	/**
	 * Stitch all characters together and export to the output directory as a series
	 * of pages
	 * 
	 * @param chars
	 *            List of Images (glyphs) representing braille characters, in order
	 */
	public void bindCharacters(Book chars) {
		bindCharacters(chars, null);
	}

	// TODO Return null, fire an event upon page completion, with the page as an
	// argument so that it can be saved incrementally, so as to free up memory
	/**
	 * Stitch all characters together and export to the output directory as a series
	 * of pages
	 * 
	 * @param chars
	 *            List of Images (glyphs) representing braille characters, in order
	 * @param images
	 *            Hashtable containing LinkedList values holding images extracted
	 *            from the pdf, so that the key to each value is the page number the
	 *            image was extracted from
	 */
	public void bindCharacters(Book chars, Hashtable<Integer, LinkedList<PositionedObject>> images) {
		// TODO Add images into 'chars' at locations relative to their location in the
		// original document

		// Get the height of a glyph
		double ratio = chars.get(0).get(0).getHeight(null) / CELL_HEIGHT;
		Image page = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) page.getGraphics();
		int wcount = 0, hcount = 0, pcount = 0;
		for (LinkedList<Image> line : chars) {
			for (Image i : line) {
				if (wcount > rowCap) {
					wcount = 0;
					hcount++;
					if (hcount > columnCap) {
						PageWriter.pageDone(page, out);
						page = new BufferedImage((int) (width * ratio), (int) (height * ratio),
								BufferedImage.TYPE_INT_RGB);
						g = (Graphics2D) page.getGraphics();
						wcount = 0;
						hcount = 0;
						pcount++;
					}
				}
				// Draw characters
				g.drawImage(i, wcount * i.getWidth(null), hcount * i.getHeight(null), null);
				wcount++;
			}
		}
	}

	/**
	 * Bind all Images into a book of pages. This includes braille characters and
	 * other Images
	 * 
	 * @param chars
	 *            All Image objects to be bound, actual Imagery and braille glyphs
	 */
	// Bind all objects, images and chars
	public void bindObjects(Book chars) {
		// TODO Add images into 'chars' at locations relative to their location in the
		// original document
		// DONE

		// Get the height width ratio of a glyph
		double ratio = chars.get(0).get(0).getHeight(null) / CELL_HEIGHT;
		// First glyph is always a space, remove it
		chars.remove(0);
		// Create a new page
		Image page = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) page.getGraphics();
		int hcount = 0;
		// Iterate through all lines in the book
		for (LinkedList<Image> line : chars) {
			// TODO Deal with width/height differences when adding full size images:
			// Done/untested...
			// lH - Line Height
			// lW - Line Width
			int lH = 0, lW = 0;
			// Iterate all characters in a line
			for (int j = 0; j < line.size(); j++) {
				Image i = line.get(j);
				int imgH = i.getHeight(null);
				int imgW = i.getWidth(null);

				// If an image is taller than the available tallest object, set the tallest
				// image counter to this value
				if (imgH > lH)
					lH = imgH;
				// Increment the width counter
				lW += imgW;

				// If the object exceeds the line width bounds, start a new line
				if (lW > page.getWidth(null)) {
					lW = 0;
					hcount++;
				}
				if (hcount > columnCap) {
					PageWriter.pageDone(page, out);
					page = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
					g = (Graphics2D) page.getGraphics();
					lW = 0;
					hcount += lH;
					lH = 0;
				}
				// Draw characters
				g.drawImage(i, lW, hcount * i.getHeight(null), null);
			}
		}
	}
}
