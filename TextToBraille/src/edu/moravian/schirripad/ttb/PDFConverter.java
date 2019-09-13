package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFConverter {

	public static void convert(String file) throws InvalidPasswordException, IOException {
		PDDocument pdf = PDDocument.load(new File(file));
		// pdf.getPage(0).getResourceCache().getXObject(arg0)
		PDFTextStripper textStripper = new PDFTextStripper();
		String pdfText = textStripper.getText(pdf);
		pdf.close();
		pdf = null;
		textStripper = null;
		System.runFinalization();
		System.gc();
		// CODEAT PDF->String done
		LinkedList<LinkedList<Image>> text = StringParser.parseString(pdfText);
		CharacterBinder binder = new CharacterBinder(297, 210);
		// TODO Bind incrementally, create each page and export them separately, so as
		// to free up resources
		if (text == null) {
			System.err.println("StringParsing failed");
			System.exit(-1);
		}
		binder.bindCharacters(text);
	}

}
