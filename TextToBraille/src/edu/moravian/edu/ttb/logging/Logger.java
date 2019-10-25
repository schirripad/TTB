package edu.moravian.edu.ttb.logging;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.time.LocalDateTime;

import edu.moravian.schirripad.ttb.PDFConverter;

/**
 * Resource class which provides writing to the default Output, or a redirected
 * OutputStream. Loggers should be obtained through the LogManager.
 * 
 * @author dan
 * 
 */
public class Logger implements Serializable {
	final private transient String name;
	private boolean debug = PDFConverter.debug;
	private transient PrintStream out = System.out;

	/**
	 * Create a Logger with this numerical ID and Display Name
	 * 
	 * @param name
	 *            Display Name
	 * @param id
	 *            Numerical ID
	 */
	public Logger(String name) {
		this.name = name;
	}

	/**
	 * Write a String out to the current OutputStream. This String will be checked
	 * for variables within the String unless otherwise specified.
	 * 
	 * @param s
	 *            String to write.
	 */
	public void log(String s) {
		actual(s);
	}

	/**
	 * Write an array of Strings out to the current OutputStream. This Array will be
	 * checked for variables within itself unless otherwise specified.
	 * 
	 * @param s
	 *            Array Of String to be written.
	 */
	public void log(String[] s) {
		actual(s);
	}

	private void actual(String[] s) {
		print("[" + name + "]");
		for (int i = 0; i < s.length; i++) {
			print(s[i] + " ");
		}
		endln();
	}

	private void actual(String s) {
		print("[" + name + "]");
		print(s);
		endln();
	}

	/**
	 * End the current line.
	 */
	public void endln() {
		out.print("\n");
		return;
	}

	/**
	 * Write out to this Stream temporarily. Only one line will be written here
	 * 
	 * @param s
	 *            String to be written.
	 * @param out
	 *            PrintStream to write out to.
	 */
	public void log(String s, PrintStream out) {
		out.print("[" + name + "] " + s + "\n");
	}

	/**
	 * Print this String without ending the line, and without any Display Name.
	 * 
	 * @param s
	 *            String to be written.
	 */
	public void print(String s) {
		out.print(s);
		return;
	}

	/**
	 * Write an Error, this goes to System.err.
	 * 
	 * @param s
	 *            String to be written.
	 */
	public void err(String s) {
		System.err.print("[" + name + "] " + s + "\n");
	}

	public void debug(String s, int level) {
		debug(s, out);
	}

	public void doDebug(boolean debug) {
		this.debug = debug;
	}

	public void debug(String s, int level, PrintStream out) {
		// TODO Add if statements to clarify debug verbosity
		debug(s, out);
	}

	/**
	 * Debug stream, only prints if "debug" is marked true in launch configuration
	 * 
	 * @param s
	 *            String to be written
	 */
	public void debug(String s) {
		debug(s, out);
	}

	/**
	 * Debug stream, only prints if "debug" is marked true in launch configuration
	 * 
	 * @param s
	 *            String to be written
	 * @param out
	 *            Stream to write to
	 */
	public void debug(String s, PrintStream out) {
		if (debug)
			log("[DEBUG] " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":"
					+ LocalDateTime.now().getSecond() + "> " + s, out);
	}

	/**
	 * Obtain the Display Name of this Logger.
	 * 
	 * @return The Logger's Display Name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Clear the console
	 * 
	 */
	public void clear() {
		try {
			int col = Integer.parseInt(System.getenv("COLUMNS"));
			for (int i = 0; i < col; i++) {
				out.println();
			}
		} catch (Exception e) {
			final int col = 80;
			for (int i = 0; i < col; i++) {
				out.println();
			}
		}
	}

	/**
	 * Obtain the currently used OutputStream to write to.
	 * 
	 * @return The current OutputStream.
	 */
	public OutputStream getOutputStream() {
		return out;
	}

	/**
	 * Change the current OutputStream.
	 * 
	 * @param out
	 *            The OutputStream to be written to.
	 */
	public void setOutputStream(OutputStream out) {
		this.out = new PrintStream(out);
	}

}
