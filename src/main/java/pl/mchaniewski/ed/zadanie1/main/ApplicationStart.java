package pl.mchaniewski.ed.zadanie1.main;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import pl.mchaniewski.ed.zadanie1.parser.CsvParser;
import pl.mchaniewski.ed.zadanie1.parser.Parser;
import pl.mchaniewski.ed.zadanie1.parser.Parser.InvalidLineLengthException;
import pl.mchaniewski.ed.zadanie1.parser.TxtParser;
import pl.mchaniewski.ed.zadanie1.utils.CommandLineUtils;

public class ApplicationStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
			CommandLineParser clp = new GnuParser();
			String fileName = "";
			String encoding = "";

			try {
				CommandLine commandLine = clp.parse(
						CommandLineUtils.getOptions(), args);

				if (commandLine.hasOption(CommandLineUtils.FILE_NAME_OPTION)) {
					fileName = commandLine
							.getOptionValue(CommandLineUtils.FILE_NAME_OPTION);
				} else {
					// TODO - error, koniec programu
				}

				if (commandLine.hasOption(CommandLineUtils.ENCODING_OPTION)) {
					encoding = commandLine
							.getOptionValue(CommandLineUtils.ENCODING_OPTION);
				}
			} catch (ParseException e) {
				// TODO error - nieprawidłowe argumenty. Pokazać help?
				e.printStackTrace();
			}

			Parser parser = null;
			if (StringUtils.endsWithIgnoreCase(fileName, Parser.TXT_EXTENSION)) {
				parser = new TxtParser(fileName, encoding);
			} else if (StringUtils.endsWithIgnoreCase(fileName,
					Parser.CSV_EXTENSION)) {
				parser = new CsvParser(fileName, encoding);
			} else {
				// TODO error - nieprawidłowe rozszerzenie
			}

			try {
				System.out.print(parser.parse().toString());
			} catch (FileNotFoundException e) {
				// TODO error - błąd z plikiem
				e.printStackTrace();
			} catch (InvalidLineLengthException e) {
				// TODO error - niepoprawny format pliku
				e.printStackTrace();
			}
		}
	}

}
