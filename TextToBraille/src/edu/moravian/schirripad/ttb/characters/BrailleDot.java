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

	public BrailleDot() throws FileNotFoundException, IOException {
		img = new BufferedImage(17, 17, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		//g.setColor(Color.BLUE);
		//g.drawRect(0, 0, 16, 16);
		float radius = 16;
		float[] dist = { 0.0f, 0.5f, 1.0f };
		Color[] colors = { Color.BLACK, Color.WHITE, Color.WHITE };
		RadialGradientPaint p = new RadialGradientPaint(8, 8, radius, dist, colors);
		g.setPaint(p);
		g.fillRect(0, 0, 17, 17);
		File f = new File("img.png");
		if (!f.exists()) {
			f.createNewFile();
		} else {
			f.delete();
			f.createNewFile();
		}
		ImageIO.write(img, "PNG", new FileOutputStream(f));
	}

	public BufferedImage getDot() {
		return img;
	}

}
