package edu.moravian.schirripad.ttb;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class Main {

	private static final File out = new File("output");

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		String path = null;
		boolean debug = false;
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("path=")) {
				path = args[i].substring(5);
			} else if (args[i].startsWith("debug=")) {
				try {
					String bTmp = args[i].substring(6);
					debug = Boolean.parseBoolean(bTmp);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		if (path == null) {
			System.out.println("Not enough arguments usage: path=<path> (debug=<true/false>)");
			return;
		}
		if (!out.exists()) {
			out.mkdir();
		} else if (!out.isDirectory()) {
			System.err.println("\"./output\" is not a directory");
			System.exit(1);
		}
		PDFConverter.doDebug(debug);
		PDFConverter.convert(new File(args[0]), out);
	}

}
