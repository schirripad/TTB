package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.util.Hashtable;
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
	public static LinkedList<LinkedList<Image>> parseString(String text) {
		text = text.toLowerCase();
		char[] asChars = text.toCharArray();
		LinkedList<LinkedList<Image>> total = new LinkedList<LinkedList<Image>>();
		LinkedList<Image> line = new LinkedList<Image>();
		final Hashtable<Character, Image> charSet = CharacterSetLoader.getCharacterSet();
		for (char c : asChars) {
			if (c == '\n') {
				total.add(line);
				line = new LinkedList<Image>();
			}
			if (charSet.containsKey(c)) {
				line.add(charSet.get(c));
			}
		}
		asChars = null;
		text = null;
		return total;
	}

}
