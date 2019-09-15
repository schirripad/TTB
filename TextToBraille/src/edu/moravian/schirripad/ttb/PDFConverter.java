package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFConverter {

	public static void convert(String file, String output) throws InvalidPasswordException, IOException {
		convert(new File(file), new File(output));
	}

	public static void convert(File f, File output) throws InvalidPasswordException, IOException {
		// Load pdf
		PDDocument pdf = PDDocument.load(f);
		// Strip the text from the pdf
		PDFTextStripper textStripper = new PDFTextStripper();
		String pdfText = textStripper.getText(pdf);
		// Get all images from the pdf, assigned by their page number
		Hashtable<Integer, LinkedList<Image>> images = PDFImageExtractor.extractImages(pdf);
		// Cleanup
		pdf.close();
		pdf = null;
		textStripper = null;
		System.runFinalization();
		System.gc();
		// CODEAT PDF->String done
		// Attempt to parse the string representation of the pdf text into a series of
		// braille characters
		LinkedList<LinkedList<Image>> text = StringParser.parseString(pdfText);
		// Bind the characters together into one image
		CharacterBinder binder = new CharacterBinder(210, 297, output);
		if (text == null) {
			System.err.println("StringParser returned null, please check that the pdf is valid, and contains text");
			System.exit(-1);
		}
		binder.bindCharacters(text);
	}

}
