package edu.moravian.schirripad.ttb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.Vector;

import edu.moravian.edu.ttb.logging.Logger;

public class PDFImageExtractor extends PDFStreamEngine {
	private Hashtable<Integer, LinkedList<PositionedObject>> images;
	private Logger log = new Logger("Extractor");
	private int page = 0;
	private Vector curDisp;

	public Hashtable<Integer, LinkedList<PositionedObject>> extractImages(PDDocument pdf) throws IOException {
		images = new Hashtable<Integer, LinkedList<PositionedObject>>();
		PDPageTree tree = pdf.getPages();
		Iterator<PDPage> pages = tree.iterator();
		while (pages.hasNext()) {
			PDPage page = pages.next();
			this.processPage(page);
			this.page++;
		}
		return images;
	}

	@Override
	protected void processOperator(Operator o, List<COSBase> operands) throws IOException {
		// Process all operator commands
		// Tj - Unformatted Text
		if (o.getName().equals("Tj")) {
			for (COSBase obj : operands) {
				if (obj instanceof COSString) {
					String text = ((COSString) obj).getString();
					PositionedText t = new PositionedText(text, 0, 0);
					if (images.get(page) == null) {
						images.put(page, new LinkedList<PositionedObject>());
					}
					log.debug("page:" + page + ":" + text);
					for (COSBase op : operands) {
						log.debug("op:" + op.toString());
					}
					images.get(page).add(t);
				}
			}
		}
		// TJ - formatted text
		if (o.getName().equals("TJ")) {
			for (int i = 0; i < operands.size(); i++) {
				COSArray ar = (COSArray) operands.get(i);
				for (COSBase obj : ar) {
					if (obj instanceof COSString) {
						// Strip text from formatting and send through again
						List<COSBase> args = new ArrayList<COSBase>();
						args.add(obj);
						processOperator("Tj", args);
					}
				}
			}
		}
		// Image
		if (o.getName().equals("Do")) {
			COSName objName = (COSName) operands.get(0);
			PDXObject obj = getResources().getXObject(objName);
			if (obj instanceof PDImageXObject) {
				// Extract the image
				PDImageXObject image = (PDImageXObject) obj;
				// Obtain the images matrix
				Matrix mat = getGraphicsState().getCurrentTransformationMatrix();
				// Get the images (x,y) state
				float x = mat.getTranslateX();
				float y = mat.getTranslateY();
				// Create a new positioned object with the (x,y) pair and Image object
				PositionedImage pi = new PositionedImage(x, y, image.getImage());
				// Check if the page has already been created
				if (images.get(page) == null) {
					images.put(page, new LinkedList<PositionedObject>());
				}
				// Add positioned object to page
				images.get(page).add(pi);
			}
		}
	}

	@Override
	protected void showGlyph(Matrix textRenderingMatrix, PDFont font, int code, String unicode, Vector displacement) {
		System.out.println("Text: " + unicode);
		if (unicode != null) {
			images.get(page).add(new PositionedText(unicode, displacement.getX(), displacement.getY()));
		}
	}

	@Override
	protected void showFontGlyph(Matrix textRenderingMatrix, PDFont font, int code, String unicode,
			Vector displacement) {
		showGlyph(textRenderingMatrix, font, code, unicode, displacement);
	}

}
