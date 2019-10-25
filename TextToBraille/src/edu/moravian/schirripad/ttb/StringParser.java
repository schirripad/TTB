package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import edu.moravian.schirripad.ttb.characters.CharacterSetLoader;

public class StringParser {

	/**
	 * Takes a string object, and converts it into a series of braille characters
	 * 
	 * @param text
	 *            The String to convert
	 * @return A LinkedList of LinkedLists, each being a separate line
	 */
	public static LinkedList<LinkedList<Image>> parseString(String fullText, String pageDelineator) {
		System.out.println("Stripping");
		// TODO Bind characters based on page, using delineator provided
		LinkedList<LinkedList<Image>> total = new LinkedList<LinkedList<Image>>();
		String[] full = fullText.split(pageDelineator);
		for (String text : full) {
			text = text.toLowerCase();
			char[] asChars = text.toCharArray();
			LinkedList<Image> line = new LinkedList<Image>();
			final Hashtable<Character, Image> charSet = CharacterSetLoader.getCharacterSet();
			for (char c : asChars) {
				if (charSet.containsKey(c)) {
					line.add(charSet.get(c));
				}
			}
			total.add(line);
			asChars = null;
			text = null;
		}

		return total;
	}

	//Use for image extracted datasets
	public static LinkedList<LinkedList<Image>> parseString(Hashtable<Integer, LinkedList<PositionedObject>> all) {
		System.out.println("Stripping");
		// TODO Bind characters based on page, using delineator provided
		final Hashtable<Character, Image> charSet = CharacterSetLoader.getCharacterSet();
		LinkedList<LinkedList<Image>> total = new LinkedList<LinkedList<Image>>();
		Iterator<Integer> it = all.keySet().iterator();
		while (it.hasNext()) {
			LinkedList<PositionedObject> tot = all.get(it.next());
			LinkedList<Image> lines = new LinkedList<Image>();
			for (PositionedObject o : tot) {
				if (o instanceof PositionedText) {
					PositionedText text = (PositionedText) o;
					char[] string = text.getText().toCharArray();
					for (char c : string)
						if (charSet.containsKey(c))
							lines.add(charSet.get(c));
				} else {
					lines.add(((PositionedImage) o).getImg());
				}
			}
			total.add(lines);
		}
		total.get(0).add(0, charSet.get(' '));
		return total;
	}

}
