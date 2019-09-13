package edu.moravian.schirripad.ttb.characters;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BrailleDot {
	private BufferedImage img;

	public BrailleDot(int diameter) throws FileNotFoundException, IOException {
		img = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		float[] dist = { 0.0f, 0.5f, 1.0f };
		Color[] colors = { Color.BLACK, Color.WHITE, Color.WHITE };
		RadialGradientPaint p = new RadialGradientPaint((diameter/2), (diameter/2), diameter, dist, colors);
		g.setPaint(p);
		g.fillRect(0, 0, diameter, diameter);
		File f = new File("img.png");
		if (!f.exists()) {
			f.createNewFile();
		} else {
			f.delete();
			f.createNewFile();
		}
		//ImageIO.write(img, "PNG", new FileOutputStream(f));
	}

	public BufferedImage getDot() {
		return img;
	}

}
