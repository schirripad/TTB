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

}
