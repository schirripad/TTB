package edu.moravian.schirripad.ttb;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import edu.moravian.edu.ttb.book.Book;
import edu.moravian.edu.ttb.logging.Logger;
import edu.moravian.schirripad.ttb.characters.CharacterSetLoader;

public class PDFConverter {
	public static boolean debug;
	private static Logger log = new Logger("ConverterEngine");

	public static void convert(String file, String output) throws InvalidPasswordException, IOException {
		convert(new File(file), new File(output));
	}

	public static void convert(File f, File output) throws InvalidPasswordException, IOException {
		convert(f, output, 210, 297, null, true);
	}

	public static void doDebug(boolean dbg) {
		debug = dbg;
	}

	/**
	 * Convert a pdf file to braille, and export the outcome
	 * 
	 * @param f
	 *            The PDF File to convert
	 * @param output
	 *            A directory where output should be exported to
	 * @param width
	 *            The width of each braille page in millimeters
	 * @param height
	 *            The height of each braille page in millimeters
	 * @param convImages
	 *            Whether or not to convert images along with text
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static void convert(File f, File output, int width, int height, File charSet, boolean convImages)
			throws InvalidPasswordException, IOException {
		// TODO Separate image extraction and string based processing rather than doing
		// both and only using part of the data
		try {
			CharacterSetLoader.loadCharacterSet(charSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Load pdf
		PDDocument pdf = PDDocument.load(f);
		// Strip the text from the pdf
		// PDFTextStripper textStripper = new PDFTextStripper();
		// System.out.println("Stripping Text");
		// String pdfText = textStripper.getText(pdf);

		// Get all images from the pdf, assigned by their page number
		PDFImageExtractor extractor = new PDFImageExtractor();
		log.debug("Extracting Images");
		Hashtable<Integer, LinkedList<PositionedObject>> images = extractor.extractImages(pdf);
		// Cleanup
		log.debug("Cleaning Up");
		extractor = null;
		pdf.close();
		pdf = null;
		System.runFinalization();
		System.gc();
		// CODEAT PDF->String done
		// Attempt to parse the string representation of the pdf text into a series of
		// braille characters
		log.debug("Parsing Characters");
		Book text = StringParser.parseString(images);
		// textStripper = null;
		// Bind the characters together into one image
		log.debug("Binding");
		CharacterBinder binder = new CharacterBinder(width, height, output);
		if (text == null || text.size() == 0) {
			log.err("StringParser returned null, please check that the pdf is valid, and contains text");
			throw new NullPointerException("StringParser returned null");
		}
		if (convImages) {
			log.debug("Include Images");
			binder.bindObjects(text);
		} else
			binder.bindCharacters(text);
	}

}
