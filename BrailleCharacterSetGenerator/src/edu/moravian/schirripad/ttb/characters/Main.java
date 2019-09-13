package edu.moravian.schirripad.ttb.characters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Main {
	// [0] [1]
	// [2] [3]
	// [4] [5]
	final static int[][] letters = { { 0, 0, 0, 0, 0, 0 }, // space (32)
			{ 0, 0, 1, 1, 1, 0 }, // !
			{ 0, 0, 0, 1, 1, 1 }, // " (or maybe 001011?)
			{ 0, 1, 0, 1, 1, 1 }, // # (actually number prefix)
			{ 0, 1, 0, 1, 0, 0 }, // $ (actually currency prefix)
			{ 0, 0, 0, 0, 0, 0 }, // % // TODO
			{ 1, 1, 1, 0, 1, 1 }, // & (actually literal and)
			{ 0, 0, 0, 0, 1, 0 }, // '
			{ 0, 0, 1, 1, 1, 1 }, // ( (open and close are the same)
			{ 0, 0, 1, 1, 1, 1 }, // )
			{ 0, 0, 0, 0, 0, 0 }, // * // TODO
			{ 0, 0, 0, 0, 0, 0 }, // + // TODO
			{ 0, 0, 1, 0, 0, 0 }, // ,
			{ 0, 0, 0, 0, 0, 0 }, // -
			{ 0, 0, 1, 1, 0, 1 }, // .
			{ 0, 0, 0, 0, 0, 0 }, // / // TODO
			{ 0, 1, 1, 1, 0, 0 }, // 0 (numbers need the number sign in front of them or offset)
			{ 1, 0, 0, 0, 0, 0 }, // 1
			{ 1, 0, 1, 0, 0, 0 }, // 2
			{ 1, 1, 0, 0, 0, 0 }, // 3
			{ 1, 1, 0, 1, 0, 0 }, // 4
			{ 1, 0, 0, 1, 0, 0 }, // 5
			{ 1, 1, 1, 0, 0, 0 }, // 6
			{ 1, 1, 1, 1, 0, 0 }, // 7
			{ 1, 0, 1, 1, 0, 0 }, // 8
			{ 0, 1, 1, 0, 0, 0 }, // 9
			{ 0, 0, 1, 1, 0, 0 }, // :
			{ 0, 0, 1, 0, 1, 0 }, // ;
			{ 0, 0, 0, 0, 0, 0 }, // < // TODO
			{ 0, 0, 0, 0, 0, 0 }, // = // TODO
			{ 0, 0, 0, 0, 0, 0 }, // > // TODO
			{ 0, 0, 1, 0, 1, 1 }, // ? (shared with "?)
			{ 0, 1, 0, 0, 0, 0 }, // @
			{ 1, 0, 0, 0, 0, 0 }, // a (these are actually the capital ASCII characters)
			{ 1, 0, 1, 0, 0, 0 }, // b
			{ 1, 1, 0, 0, 0, 0 }, // c
			{ 1, 1, 0, 1, 0, 0 }, // d
			{ 1, 0, 0, 1, 0, 0 }, // e
			{ 1, 1, 1, 0, 0, 0 }, // f
			{ 1, 1, 1, 1, 0, 0 }, // g
			{ 1, 0, 1, 1, 0, 0 }, // h
			{ 0, 1, 1, 0, 0, 0 }, // i
			{ 0, 1, 1, 1, 0, 0 }, // j
			{ 1, 0, 0, 0, 1, 0 }, // k
			{ 1, 0, 1, 0, 1, 0 }, // l
			{ 1, 1, 0, 0, 1, 0 }, // m
			{ 1, 1, 0, 1, 1, 0 }, // n
			{ 1, 0, 0, 1, 1, 0 }, // o
			{ 1, 1, 1, 0, 1, 0 }, // p
			{ 1, 1, 1, 1, 1, 0 }, // q
			{ 1, 0, 1, 1, 1, 0 }, // r
			{ 0, 1, 1, 0, 1, 0 }, // s
			{ 0, 1, 1, 1, 1, 0 }, // t
			{ 1, 0, 0, 0, 1, 1 }, // u
			{ 1, 0, 1, 1, 1, 0 }, // v
			{ 0, 1, 1, 1, 0, 1 }, // w
			{ 1, 1, 0, 0, 1, 1 }, // x
			{ 1, 1, 0, 1, 1, 1 }, // y
			{ 1, 0, 0, 1, 1, 1 }, // z
	};

	static BrailleDot dot;
	static LinkedList<BufferedImage> characters = new LinkedList<BufferedImage>();

	static int dotSpacing, cellSpacingSides, cellSpacingTop;
	static double mm;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		int diam = 33;
		if (args.length > 0) {
			try {
				diam = Integer.parseInt(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Please enter a number");
			}
		}
		mm = diam / (1.44); // Size of one millimeter based on given diameter
		dotSpacing = (int) (0.9 * mm);
		cellSpacingSides = (int) (6.2 * mm);
		cellSpacingTop = (int) (10 * mm);
		int height = (int) (15.4 * mm);
		int width = (int) ((6.2 + 3.78) * mm);
		dot = new BrailleDot(diam);

		System.out.println("Using diameter size of " + diam + " pixels...");
		System.out.println("Generating charset...");
		for (int j = 0; j < letters.length; j++) {
			BufferedImage cell = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) cell.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			int[] code = letters[j];
			for (int i = 0; i < code.length; i++) {
				if (code[i] == 1) {
					addDot(g, i + 1, diam);
				}
			}
			characters.add(cell);
		}
		System.out.println("Generation complete, saving charset...");
		String[] titles = { "space", "exclaimation", "quotation", "pound", "dollarsign", "percent", "ampersand",
				"singlequote", "leftparentheses", "rightparenthese", "asterisk", "plus", "comma", "minus", "period",
				"slash", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "colon", "semicolon", "lessthan", "equal",
				"greaterthan", "question", "at", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		int titleIndex = 0;
		for (BufferedImage img : characters) {
			File f = new File("characters/" + titles[titleIndex] + ".png");
			if (f.exists()) {
				f.delete();
				f.createNewFile();
			} else {
				if (!f.getParentFile().exists())
					f.getParentFile().mkdir();
				f.createNewFile();
			}
			ImageIO.write(img, "PNG", new FileOutputStream(f));
			titleIndex++;
		}
		System.out.println("Completed saving charset!");
	}

	public static void addDot(Graphics2D g, int pos, int diam) {
		int space = dotSpacing + diam;
		int sides = cellSpacingSides / 2;
		int top = cellSpacingTop / 2;
		switch (pos) {
		case 1:
			g.drawImage(dot.getDot(), sides, top, diam, diam, null);
			break;
		case 2:
			g.drawImage(dot.getDot(), sides + space, top, diam, diam, null);
			break;
		case 3:
			g.drawImage(dot.getDot(), sides, top + space, diam, diam, null);
			break;
		case 4:
			g.drawImage(dot.getDot(), sides + space, top + space, diam, diam, null);
			break;
		case 5:
			g.drawImage(dot.getDot(), sides, top + (space * 2), diam, diam, null);
			break;
		case 6:
			g.drawImage(dot.getDot(), sides + space, top + (space * 2), diam, diam, null);
			break;
		}
	}
}
