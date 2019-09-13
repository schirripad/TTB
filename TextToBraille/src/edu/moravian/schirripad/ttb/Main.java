package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class Main {

	private static Hashtable<Character, Image> characterSet = new Hashtable<Character, Image>();
	private static final File out = new File("output");

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		if (args.length != 1) {
			System.out.println("Not enough arguments");
			return;
		}
		if (!out.exists()) {
			out.mkdir();
		} else if (!out.isDirectory()) {
			System.err.println("\"./output\" is not a directory");
			System.exit(1);
		}
		try {
			loadCharacterSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PDFConverter.convert(args[0]);
	}

	private static void loadCharacterSet() throws IOException {
		File excl = new File("assets/characters/exclaimation.png");
		characterSet.put('!', loadImage(excl));
		File space = new File("assets/characters/space.png");
		characterSet.put(' ', loadImage(space));
		File quote = new File("assets/characters/quotation.png");
		characterSet.put('\"', loadImage(quote));
		File pound = new File("assets/characters/pound.png");
		characterSet.put('#', loadImage(pound));
		File dollarSign = new File("assets/characters/dollarsign.png");
		characterSet.put('$', loadImage(dollarSign));
		File percent = new File("assets/characters/percent.png");
		characterSet.put('%', loadImage(percent));
		File ampersand = new File("assets/characters/ampersand.png");
		characterSet.put('&', loadImage(ampersand));
		File singleQuote = new File("assets/characters/singlequote.png");
		characterSet.put('\'', loadImage(singleQuote));
		File leftPar = new File("assets/characters/leftparentheses.png");
		characterSet.put('(', loadImage(leftPar));
		File rightPar = new File("assets/characters/rightparenthese.png");
		characterSet.put(')', loadImage(rightPar));
		File asterisk = new File("assets/characters/asterisk.png");
		characterSet.put('*', loadImage(asterisk));
		File plus = new File("assets/characters/plus.png");
		characterSet.put('+', loadImage(plus));
		File comma = new File("assets/characters/comma.png");
		characterSet.put(',', loadImage(comma));
		File minus = new File("assets/characters/minus.png");
		characterSet.put('-', loadImage(minus));
		File period = new File("assets/characters/period.png");
		characterSet.put('.', loadImage(period));
		File slash = new File("assets/characters/slash.png");
		characterSet.put('/', loadImage(slash));
		File zero = new File("assets/characters/0.png");
		characterSet.put('0', loadImage(zero));
		File one = new File("assets/characters/1.png");
		characterSet.put('1', loadImage(one));
		File two = new File("assets/characters/2.png");
		characterSet.put('2', loadImage(two));
		File three = new File("assets/characters/3.png");
		characterSet.put('3', loadImage(three));
		File four = new File("assets/characters/4.png");
		characterSet.put('4', loadImage(four));
		File five = new File("assets/characters/5.png");
		characterSet.put('5', loadImage(five));
		File six = new File("assets/characters/6.png");
		characterSet.put('6', loadImage(six));
		File seven = new File("assets/characters/7.png");
		characterSet.put('7', loadImage(seven));
		File eight = new File("assets/characters/8.png");
		characterSet.put('8', loadImage(eight));
		File nine = new File("assets/characters/9.png");
		characterSet.put('9', loadImage(nine));
		File colon = new File("assets/characters/colon.png");
		characterSet.put(':', loadImage(colon));
		File semiColon = new File("assets/characters/semicolon.png");
		characterSet.put(';', loadImage(semiColon));
		File lessThan = new File("assets/characters/lessthan.png");
		characterSet.put('<', loadImage(lessThan));
		File equal = new File("assets/characters/equal.png");
		characterSet.put('=', loadImage(equal));
		File greaterThan = new File("assets/characters/greaterthan.png");
		characterSet.put('>', loadImage(greaterThan));
		File questionMark = new File("assets/characters/question.png");
		characterSet.put('?', loadImage(questionMark));
		File atSign = new File("assets/characters/at.png");
		characterSet.put('@', loadImage(atSign));
		File a = new File("assets/characters/a.png");
		characterSet.put('a', loadImage(a));
		File b = new File("assets/characters/b.png");
		characterSet.put('b', loadImage(b));
		File c = new File("assets/characters/c.png");
		characterSet.put('c', loadImage(c));
		File d = new File("assets/characters/d.png");
		characterSet.put('d', loadImage(d));
		File e = new File("assets/characters/e.png");
		characterSet.put('e', loadImage(e));
		File f = new File("assets/characters/f.png");
		characterSet.put('f', loadImage(f));
		File g = new File("assets/characters/g.png");
		characterSet.put('g', loadImage(g));
		File h = new File("assets/characters/h.png");
		characterSet.put('h', loadImage(h));
		File i = new File("assets/characters/i.png");
		characterSet.put('i', loadImage(i));
		File j = new File("assets/characters/j.png");
		characterSet.put('j', loadImage(j));
		File k = new File("assets/characters/k.png");
		characterSet.put('k', loadImage(k));
		File l = new File("assets/characters/l.png");
		characterSet.put('l', loadImage(l));
		File m = new File("assets/characters/m.png");
		characterSet.put('m', loadImage(m));
		File n = new File("assets/characters/n.png");
		characterSet.put('n', loadImage(n));
		File o = new File("assets/characters/o.png");
		characterSet.put('o', loadImage(o));
		File p = new File("assets/characters/p.png");
		characterSet.put('p', loadImage(p));
		File q = new File("assets/characters/q.png");
		characterSet.put('q', loadImage(q));
		File r = new File("assets/characters/r.png");
		characterSet.put('r', loadImage(r));
		File s = new File("assets/characters/s.png");
		characterSet.put('s', loadImage(s));
		File t = new File("assets/characters/t.png");
		characterSet.put('t', loadImage(t));
		File u = new File("assets/characters/u.png");
		characterSet.put('u', loadImage(u));
		File v = new File("assets/characters/v.png");
		characterSet.put('v', loadImage(v));
		File w = new File("assets/characters/w.png");
		characterSet.put('w', loadImage(w));
		File x = new File("assets/characters/x.png");
		characterSet.put('x', loadImage(x));
		File y = new File("assets/characters/y.png");
		characterSet.put('y', loadImage(y));
		File z = new File("assets/characters/z.png");
		characterSet.put('z', loadImage(z));
	}

	private static Image loadImage(File f) throws IOException {
		if (!f.exists()) {
			throw new FileNotFoundException("Failed to load " + f.getName() + "! File not found!");
		}
		if (f.isDirectory()) {
			throw new FileNotFoundException("Failed to load " + f.getName() + "! File is directory!");
		}
		return ImageIO.read(f);
	}

	public static Hashtable<Character, Image> getCharSet() {
		return characterSet;
	}

	private static int pCount = 0;

	public static void pageDone(Image page) {
		System.out.println("Writing: output/" + pCount + ".png");
		try {
			ImageIO.write((RenderedImage) page, "PNG", new FileOutputStream(new File("output/" + pCount + ".png")));
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
