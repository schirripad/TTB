package edu.moravian.schirripad.ttb;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import edu.moravian.schirripad.ttb.characters.CharacterSetLoader;

public class Main {

	private static final File out = new File("output");

	public static void main(String[] args) throws InvalidPasswordException, IOException {
		if (args.length != 1) {
			System.out.println("Not enough arguments");
			return;
		}
		if (!out.exists()) {
			out.mkdir();
		} else if (!out.isDirectory()) {
			System.err.println("\"./output\" is not a directory");
			System.exit(1);
		}
		PDFConverter.convert(new File(args[0]), out);
	}

}
