package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFConverter {

	public static void convert(String file) throws InvalidPasswordException, IOException {
		PDDocument pdf = PDDocument.load(new File(file));
		
		
		
		Iterable<COSName> names = pdf.getPage(0).getResources().getXObjectNames();
		Iterator<COSName> it = names.iterator();
		while(it.hasNext()) {
			COSName name = it.next();
			PDXObject obj = pdf.getPage(0).getResources().getXObject(name);
			if(obj instanceof PDImageXObject) {
				System.out.println(name.getName());
				PDImageXObject img = (PDImageXObject) obj;
				//img.
			}
		}
		
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
			System.err.println("StringParser returned null, please check that the pdf is valid, and contains text");
			System.exit(-1);
		}
		binder.bindCharacters(text);
	}

}
