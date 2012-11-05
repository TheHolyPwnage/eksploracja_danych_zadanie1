package pl.mchaniewski.ed.zadanie1.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import pl.mchaniewski.ed.zadanie1.model.RawData;

public abstract class Parser {
	public static String TXT_EXTENSION = ".txt";
	public static String CSV_EXTENSION = ".csv";

	private static String DEFAULT_ENCODING = "UTF-8";
	protected String fileName;
	protected String fileEncoding;

	public Parser(String fileName, String fileEncoding) {
		super();
		this.fileName = fileName;
		this.fileEncoding = fileEncoding;
	}

	protected Scanner getScanner() throws FileNotFoundException {
		return new Scanner(new File(fileName),
				StringUtils.isEmpty(fileEncoding) ? DEFAULT_ENCODING
						: fileEncoding);
	}

	public abstract RawData parse() throws FileNotFoundException,
			InvalidLineLengthException;

	public class InvalidLineLengthException extends Exception {
		private static final long serialVersionUID = -6648460521277805811L;

		public InvalidLineLengthException(String msg) {
			super(msg);
		}
	}

}
