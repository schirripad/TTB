package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFImageExtractor {

	public static Hashtable<Integer, LinkedList<Image>> extractImages(PDDocument pdf) throws IOException {
		Hashtable<Integer, LinkedList<Image>> images = new Hashtable<Integer, LinkedList<Image>>();

		PDPageTree tree = pdf.getPages();
		Iterator<PDPage> pages = tree.iterator();
		int index = 0;
		while (pages.hasNext()) {
			PDPage page = pages.next();
			Iterator<COSName> it = page.getResources().getXObjectNames().iterator();
			while (it.hasNext()) {
				COSName name = it.next();
				PDXObject obj = pdf.getPage(0).getResources().getXObject(name);
				if (obj instanceof PDImageXObject) {
					PDImageXObject img = (PDImageXObject) obj;
					if (images.get(index) == null) {
						images.put(index, new LinkedList<Image>());
					}
					images.get(index).add(img.getImage());
					// Store images with index of page, so to be called later when new page is
					// created
				}
			}
			index++;
		}
		return images;
	}

}
