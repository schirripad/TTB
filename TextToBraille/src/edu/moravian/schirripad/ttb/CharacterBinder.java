package edu.moravian.schirripad.ttb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.imageio.ImageIO;

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
	public void bindCharacters(LinkedList<LinkedList<Image>> chars) {
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
	public void bindCharacters(LinkedList<LinkedList<Image>> chars, Hashtable<Integer, LinkedList<PositionedImage>> images) {
		double ratio = chars.get(0).get(0).getHeight(null) / CELL_HEIGHT;
		LinkedList<Image> bound = new LinkedList<Image>();
		LinkedList<PositionedImage> imgs = null;
		if (images != null)
			imgs = images.get(0);
		Image page = new BufferedImage((int) (width * ratio), (int) (height * ratio), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) page.getGraphics();
		int wcount = 0, hcount = 0, pcount = 0;
		for (LinkedList<Image> line : chars) {
			for (Image i : line) {
				if (wcount > rowCap) {
					wcount = 0;
					hcount++;
					if (hcount > columnCap) {
						// bound.add(page);
						PageWriter.pageDone(page, out);
						page = new BufferedImage((int) (width * ratio), (int) (height * ratio),
								BufferedImage.TYPE_INT_RGB);
						g = (Graphics2D) page.getGraphics();
						wcount = 0;
						hcount = 0;
						pcount++;
						if (images != null)
							imgs = images.get(pcount);
					}
				}
				if (wcount == 0 &&  hcount  == 0) {
					// TODO REDO, add in code for handling X/Y positioning
					// Draw pictures before text
					if (imgs != null && imgs.size() > 0) {
						int imgTallest = 0;
						int charHeight = i.getHeight(null);
						for (PositionedImage img : imgs) {
							if ((hcount + img.getHeight(null) / charHeight) > columnCap) {
								// Create new page
								PageWriter.pageDone(page, out);
								page = new BufferedImage((int) (width * ratio), (int) (height * ratio),
										BufferedImage.TYPE_INT_RGB);
								g = (Graphics2D) page.getGraphics();
								wcount = 0;
								hcount = 0;
								pcount++;
							}
							g.drawImage(img, hcount, wcount, null);
							int imgHeight = img.getHeight(null);
							if (imgHeight > imgTallest)
								imgTallest = imgHeight;
							// Check width
							if (wcount > page.getWidth(null)) {
								// Need to record tallest image value so as to create the correct variable
								// height reference
								hcount += imgTallest;
								imgTallest = 0;
								wcount = 0;
							}
							wcount += img.getWidth(null);
						}
						hcount = hcount / charHeight;
						wcount = 0;
					}
				}
				// Draw characters
				g.drawImage(i, wcount * i.getWidth(null), hcount * i.getHeight(null), null);
				wcount++;
			}
		}
	}
}
