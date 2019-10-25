package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import edu.moravian.edu.ttb.logging.Logger;
import edu.moravian.schirripad.ttb.characters.CharacterSetLoader;

public class StringParser {
	private final static Logger log = new Logger("StringParser");

	/**
	 * Takes a string object, and converts it into a series of braille characters
	 * 
	 * @param text
	 *            The String to convert
	 * @return A LinkedList of LinkedLists, each being a separate line
	 */
	public static LinkedList<LinkedList<Image>> parseString(String fullText, String pageDelineator) {
		log.debug("Stripping");
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

	// Use for image extracted datasets
	public static LinkedList<LinkedList<Image>> parseString(Hashtable<Integer, LinkedList<PositionedObject>> all) {
		log.debug("Stripping");
		// Load the braille character glyphs
		final Hashtable<Character, Image> charSet = CharacterSetLoader.getCharacterSet();
		// Create a linkedlist representing the entire book
		LinkedList<LinkedList<Image>> total = new LinkedList<LinkedList<Image>>();
		Iterator<Integer> it = all.keySet().iterator();
		// Iterate through all pages, and convert them into glyphs
		while (it.hasNext()) {
			// tot is a complete line of uninterpreted data
			LinkedList<PositionedObject> tot = all.get(it.next());
			// lines will be filled with glyphs and images
			LinkedList<Image> lines = new LinkedList<Image>();
			// Separate Text from Imagery, and deal with them independently, while keeping
			// the original ordering
			for (PositionedObject o : tot) {
				if (o instanceof PositionedText) {
					// If its text, convert to glyphs
					PositionedText text = (PositionedText) o;
					char[] string = text.getText().toCharArray();
					for (char c : string)
						if (charSet.containsKey(c))
							lines.add(charSet.get(c));
				} else {
					lines.add(((PositionedImage) o).getImg());
				}
			}
			// Add a completed interpreted line to the book
			total.add(lines);
		}
		// Add a space character, used later for determining glyph size
		total.get(0).add(0, charSet.get(' '));
		return total;
	}

}
