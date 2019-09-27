package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.GrayFilter;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFImageExtractor extends PDFStreamEngine {

	public static Hashtable<Integer, LinkedList<Image>> extractImages(PDDocument pdf) throws IOException {
		Hashtable<Integer, LinkedList<Image>> images = new Hashtable<Integer, LinkedList<Image>>();
		PDFStreamEngine pEngine = new PDFImageExtractor();

		PDPageTree tree = pdf.getPages();
		Iterator<PDPage> pages = tree.iterator();
		//int index = 0;
		while (pages.hasNext()) {
			PDPage page = pages.next();
			pEngine.processPage(page);

			/*
			 * Iterator<COSName> it = page.getResources().getXObjectNames().iterator();
			 * while (it.hasNext()) { COSName name = it.next(); PDXObject obj =
			 * pdf.getPage(0).getResources().getXObject(name); if (obj instanceof
			 * PDImageXObject) { PDImageXObject img = (PDImageXObject) obj; if
			 * (images.get(index) == null) { images.put(index, new LinkedList<Image>()); }
			 * Image i = img.getImage(); i = Toolkit.getDefaultToolkit() .createImage(new
			 * FilteredImageSource(i.getSource(), new GrayFilter(true, 50)));
			 * images.get(index).add(i); // Store images with index of page, so to be called
			 * later when new page is // created } } index++;
			 */
		}
		return images;
	}

	@Override
	protected void processOperator(Operator o, List<COSBase> operands) {

	}

}
