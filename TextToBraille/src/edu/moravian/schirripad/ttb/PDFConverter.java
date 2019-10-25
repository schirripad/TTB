package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import edu.moravian.schirripad.ttb.characters.CharacterSetLoader;

public class PDFConverter {

	public static void convert(String file, String output) throws InvalidPasswordException, IOException {
		convert(new File(file), new File(output));
	}

	public static void convert(File f, File output) throws InvalidPasswordException, IOException {
		convert(f, output, 210, 297, null, true);
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
		try {
			CharacterSetLoader.loadCharacterSet(charSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Load pdf
		PDDocument pdf = PDDocument.load(f);
		// Strip the text from the pdf
		//PDFTextStripper textStripper = new PDFTextStripper();
		//System.out.println("Stripping Text");
		//String pdfText = textStripper.getText(pdf);

		// Get all images from the pdf, assigned by their page number
		PDFImageExtractor extractor = new PDFImageExtractor();
		System.out.println("Extracting Images");
		Hashtable<Integer, LinkedList<PositionedObject>> images = extractor.extractImages(pdf);
		// Cleanup
		System.out.println("Cleaning Up");
		extractor = null;
		pdf.close();
		pdf = null;
		System.runFinalization();
		System.gc();
		// CODEAT PDF->String done
		// Attempt to parse the string representation of the pdf text into a series of
		// braille characters
		System.out.println("Parsing Characters");
		LinkedList<LinkedList<Image>> text = StringParser.parseString(images);
		//textStripper = null;
		// Bind the characters together into one image
		System.out.println("Binding");
		CharacterBinder binder = new CharacterBinder(width, height, output);
		if (text == null || text.size() == 0) {
			System.err.println("StringParser returned null, please check that the pdf is valid, and contains text");
			throw new NullPointerException("StringParser returned null");
		}
		if (convImages) {
			System.out.println("Include Images");
			binder.bindObjects(text);
		} else
			binder.bindCharacters(text);
	}

}
