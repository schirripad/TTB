package edu.moravian.schirripad.ttb;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class PDFImageExtractor extends PDFStreamEngine {
	private Hashtable<Integer, LinkedList<PositionedImage>> images;
	private int page = 0;

	public Hashtable<Integer, LinkedList<PositionedImage>> extractImages(PDDocument pdf) throws IOException {
		// Hashtable<Integer, LinkedList<Image>> images = new Hashtable<Integer,
		// LinkedList<Image>>();
		images = new Hashtable<Integer, LinkedList<PositionedImage>>();
		PDFStreamEngine pEngine = new PDFImageExtractor();
		PDPageTree tree = pdf.getPages();
		Iterator<PDPage> pages = tree.iterator();
		while (pages.hasNext()) {
			PDPage page = pages.next();
			pEngine.processPage(page);
			this.page++;
		}
		return images;
	}

	@Override
	protected void processOperator(Operator o, List<COSBase> operands) throws IOException {
		if (o.getName().equals("Do")) {
			COSName objName = (COSName) operands.get(0);
			PDXObject obj = getResources().getXObject(objName);
			if (obj instanceof PDImageXObject) {
				PDImageXObject image = (PDImageXObject) obj;
				Matrix mat = getGraphicsState().getCurrentTransformationMatrix();
				float x = mat.getTranslateX();
				float y = mat.getTranslateY();
				PositionedImage pi = new PositionedImage(x, y, image.getImage());
				if (images.get(page) == null) {
					images.put(page, new LinkedList<PositionedImage>());
				}
				images.get(page).add(pi);
			}
		}
	}

}
