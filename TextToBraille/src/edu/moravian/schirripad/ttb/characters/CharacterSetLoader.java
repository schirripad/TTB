package edu.moravian.schirripad.ttb.characters;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class CharacterSetLoader {
	private static Hashtable<Character, Image> characterSet;
	private static final File defaultDir = new File("assets/characters");

	public static void loadCharacterSet(File dir) throws IOException {
		characterSet = new Hashtable<Character, Image>();
		if (dir == null || !dir.exists() || !dir.isDirectory()) {
			dir = defaultDir;
		}
		File excl = new File(dir, "/exclaimation.png");
		characterSet.put('!', loadImage(excl));
		File space = new File(dir, "/space.png");
		characterSet.put(' ', loadImage(space));
		File quote = new File(dir, "/quotation.png");
		characterSet.put('\"', loadImage(quote));
		File pound = new File(dir, "/pound.png");
		characterSet.put('#', loadImage(pound));
		File dollarSign = new File(dir, "/dollarsign.png");
		characterSet.put('$', loadImage(dollarSign));
		File percent = new File(dir, "/percent.png");
		characterSet.put('%', loadImage(percent));
		File ampersand = new File(dir, "/ampersand.png");
		characterSet.put('&', loadImage(ampersand));
		File singleQuote = new File(dir, "/singlequote.png");
		characterSet.put('\'', loadImage(singleQuote));
		File leftPar = new File(dir, "/leftparentheses.png");
		characterSet.put('(', loadImage(leftPar));
		File rightPar = new File(dir, "/rightparenthese.png");
		characterSet.put(')', loadImage(rightPar));
		File asterisk = new File(dir, "/asterisk.png");
		characterSet.put('*', loadImage(asterisk));
		File plus = new File(dir, "/plus.png");
		characterSet.put('+', loadImage(plus));
		File comma = new File(dir, "/comma.png");
		characterSet.put(',', loadImage(comma));
		File minus = new File(dir, "/minus.png");
		characterSet.put('-', loadImage(minus));
		File period = new File(dir, "/period.png");
		characterSet.put('.', loadImage(period));
		File slash = new File(dir, "/slash.png");
		characterSet.put('/', loadImage(slash));
		File zero = new File(dir, "/0.png");
		characterSet.put('0', loadImage(zero));
		File one = new File(dir, "/1.png");
		characterSet.put('1', loadImage(one));
		File two = new File(dir, "/2.png");
		characterSet.put('2', loadImage(two));
		File three = new File(dir, "/3.png");
		characterSet.put('3', loadImage(three));
		File four = new File(dir, "/4.png");
		characterSet.put('4', loadImage(four));
		File five = new File(dir, "/5.png");
		characterSet.put('5', loadImage(five));
		File six = new File(dir, "/6.png");
		characterSet.put('6', loadImage(six));
		File seven = new File(dir, "/7.png");
		characterSet.put('7', loadImage(seven));
		File eight = new File(dir, "/8.png");
		characterSet.put('8', loadImage(eight));
		File nine = new File(dir, "/9.png");
		characterSet.put('9', loadImage(nine));
		File colon = new File(dir, "/colon.png");
		characterSet.put(':', loadImage(colon));
		File semiColon = new File(dir, "/semicolon.png");
		characterSet.put(';', loadImage(semiColon));
		File lessThan = new File(dir, "/lessthan.png");
		characterSet.put('<', loadImage(lessThan));
		File equal = new File(dir, "/equal.png");
		characterSet.put('=', loadImage(equal));
		File greaterThan = new File(dir, "/greaterthan.png");
		characterSet.put('>', loadImage(greaterThan));
		File questionMark = new File(dir, "/question.png");
		characterSet.put('?', loadImage(questionMark));
		File atSign = new File(dir, "/at.png");
		characterSet.put('@', loadImage(atSign));
		File a = new File(dir, "/a.png");
		characterSet.put('a', loadImage(a));
		File b = new File(dir, "/b.png");
		characterSet.put('b', loadImage(b));
		File c = new File(dir, "/c.png");
		characterSet.put('c', loadImage(c));
		File d = new File(dir, "/d.png");
		characterSet.put('d', loadImage(d));
		File e = new File(dir, "/e.png");
		characterSet.put('e', loadImage(e));
		File f = new File(dir, "/f.png");
		characterSet.put('f', loadImage(f));
		File g = new File(dir, "/g.png");
		characterSet.put('g', loadImage(g));
		File h = new File(dir, "/h.png");
		characterSet.put('h', loadImage(h));
		File i = new File(dir, "/i.png");
		characterSet.put('i', loadImage(i));
		File j = new File(dir, "/j.png");
		characterSet.put('j', loadImage(j));
		File k = new File(dir, "/k.png");
		characterSet.put('k', loadImage(k));
		File l = new File(dir, "/l.png");
		characterSet.put('l', loadImage(l));
		File m = new File(dir, "/m.png");
		characterSet.put('m', loadImage(m));
		File n = new File(dir, "/n.png");
		characterSet.put('n', loadImage(n));
		File o = new File(dir, "/o.png");
		characterSet.put('o', loadImage(o));
		File p = new File(dir, "/p.png");
		characterSet.put('p', loadImage(p));
		File q = new File(dir, "/q.png");
		characterSet.put('q', loadImage(q));
		File r = new File(dir, "/r.png");
		characterSet.put('r', loadImage(r));
		File s = new File(dir, "/s.png");
		characterSet.put('s', loadImage(s));
		File t = new File(dir, "/t.png");
		characterSet.put('t', loadImage(t));
		File u = new File(dir, "/u.png");
		characterSet.put('u', loadImage(u));
		File v = new File(dir, "/v.png");
		characterSet.put('v', loadImage(v));
		File w = new File(dir, "/w.png");
		characterSet.put('w', loadImage(w));
		File x = new File(dir, "/x.png");
		characterSet.put('x', loadImage(x));
		File y = new File(dir, "/y.png");
		characterSet.put('y', loadImage(y));
		File z = new File(dir, "/z.png");
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

	public static Hashtable<Character, Image> getCharacterSet() {
		return characterSet;
	}
}
