package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PageWriter {

	private static int pCount = 0;

	/**
	 * Writes each individual Image to the output directory
	 * 
	 * @param page
	 *            Image representing full page of braille text
	 * @param output
	 *            Directory which to write to
	 */
	public static void pageDone(Image page, File output) {
		System.out.println("Writing: " + output.getPath() + "/" + pCount + ".png");
		try {
			ImageIO.write((RenderedImage) page, "PNG", new FileOutputStream(new File(output, pCount + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		page = null;
		System.runFinalization();
		System.gc();
		pCount++;
	}

}
