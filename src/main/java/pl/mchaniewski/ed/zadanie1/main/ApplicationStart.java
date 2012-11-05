package pl.mchaniewski.ed.zadanie1.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.mchaniewski.ed.zadanie1.algorithm.Algorithm;
import pl.mchaniewski.ed.zadanie1.algorithm.SingleStep;
import pl.mchaniewski.ed.zadanie1.chart.Chart;
import pl.mchaniewski.ed.zadanie1.classificator.ObjectClassificator;
import pl.mchaniewski.ed.zadanie1.model.RawData;
import pl.mchaniewski.ed.zadanie1.parser.CsvParser;
import pl.mchaniewski.ed.zadanie1.parser.Parser;
import pl.mchaniewski.ed.zadanie1.parser.Parser.InvalidLineLengthException;
import pl.mchaniewski.ed.zadanie1.parser.TxtParser;
import pl.mchaniewski.ed.zadanie1.utils.CommandLineUtils;

public class ApplicationStart {
	private static final Logger log = LoggerFactory
			.getLogger(ApplicationStart.class);
	private static final String APPLICATION_NAME = "zadanie1";

	public static void main(String[] args) {
		if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
			CommandLineParser clp = new GnuParser();
			String fileName = "";
			String encoding = "";
			boolean generateMainFile = false;
			boolean generateStepFiles = false;
			boolean writeOutLines = false;
			boolean manualClassification = false;

			try {
				CommandLine commandLine = clp.parse(
						CommandLineUtils.getOptions(), args);

				if (commandLine.hasOption(CommandLineUtils.HELP_SHORT_OPTION)
						|| commandLine
								.hasOption(CommandLineUtils.HELP_LONG_OPTION)) {
					log.debug("Wyświetlam pomoc.");

					CommandLineUtils.getHelp(APPLICATION_NAME);
					return;
				}

				if (commandLine.hasOption(CommandLineUtils.FILE_NAME_OPTION)) {
					fileName = commandLine
							.getOptionValue(CommandLineUtils.FILE_NAME_OPTION);
				} else {
					System.out
							.println("Nie została podana nazwa pliku wejściowego.");

					log.debug("Brak pliku wejściowego.");

					return;
				}

				if (commandLine.hasOption(CommandLineUtils.ENCODING_OPTION)) {
					encoding = commandLine
							.getOptionValue(CommandLineUtils.ENCODING_OPTION);
				}

				if (commandLine
						.hasOption(CommandLineUtils.GENERATE_MAIN_FILE_SHORT_OPTION)
						|| commandLine
								.hasOption(CommandLineUtils.GENERATE_MAIN_FILE_LONG_OPTION)) {
					generateMainFile = true;
				}

				if (commandLine
						.hasOption(CommandLineUtils.GENERATE_STEP_FILES_SHORT_OPTION)
						|| commandLine
								.hasOption(CommandLineUtils.GENERATE_STEP_FILES_LONG_OPTION)) {
					generateStepFiles = true;
				}

				if (commandLine
						.hasOption(CommandLineUtils.WRITE_OUT_LINE_OPTION)) {
					writeOutLines = true;
				}

				if (commandLine
						.hasOption(CommandLineUtils.MANUAL_CLASSIFICATION_OPTION)) {
					manualClassification = true;
				}
			} catch (ParseException e) {
				System.out.println("Nieprawidłowe parametry aplikacji.");

				log.debug("Próba użycia nieprawidłowych parametrów aplikacji.",
						e);
			}

			Parser parser = null;
			if (StringUtils.endsWithIgnoreCase(fileName, Parser.TXT_EXTENSION)) {
				parser = new TxtParser(fileName, encoding);
			} else if (StringUtils.endsWithIgnoreCase(fileName,
					Parser.CSV_EXTENSION)) {
				parser = new CsvParser(fileName, encoding);
			} else {
				System.out
						.println("Aplikacja obsługuje jedynie rozszerzenie .txt oraz .csv");

				log.debug("Próba odczytu niewspieranego formatu pliku: {}",
						fileName);

				return;
			}

			RawData data = null;
			try {
				data = parser.parse();
			} catch (FileNotFoundException e) {
				System.out
						.println("Odczytanie pliku zakończyło się niepowodzeniem. Sprawdź, czy podany plik istnieje.");

				log.debug("Brak pliku.", e);
			} catch (InvalidLineLengthException e) {
				System.out
						.println("Format pliku jest nieprawdłowy. Każda linia musi posiadać identyczną ilość atrybutów.");

				log.debug("Nieprawidłowy format pliku.", e);

				return;
			}

			Algorithm alg = new Algorithm(data);
			log.debug("TEST: " + data.toString());
			alg.run();

			if (generateMainFile) {
				log.debug("Generowanie głównego pliku.");
				System.out.println("Generowanie głównego pliku z liniami.");

				Chart chart = new Chart(data, 0, 1, alg.getSteps());
				chart.setFileName(fileName + "_linie");
				chart.run();
			}

			if (generateStepFiles) {
				int counter = 0;
				for (SingleStep step : alg.getSteps()) {
					++counter;
					if (step.getNewData() == null) {
						break;
					}

					log.debug("Generowanie pliku kroku numer {}.", counter);
					System.out.println("Generowanie pliku dla odcięcia numer "
							+ counter);

					Chart chart = new Chart(step.getNewData(), 0, 1);
					chart.setFileName(fileName + "_krok_" + counter);
					chart.run();
				}
			}

			if (writeOutLines) {
				System.out.println("Lista znalezionych linii ( łącznie "
						+ (alg.getSteps().size() - 1) + " ):");
				System.out.println("--------------------------");
				int counter = 0;
				for (SingleStep step : alg.getSteps()) {
					++counter;

					System.out.println("Linia numer: " + counter);
					System.out.println(step.getCutoffPoint().toString());

					if (alg.getSteps().size() - 1 == counter) {
						break;
					}
				}
			}

			if (manualClassification) {
				int attributeCount = data.getColumnCount() - 1;
				List<Double> newObj = new ArrayList<Double>();

				System.out.println("Ilość atrybutów: " + attributeCount);
				System.out.println("-----------------------------------");

				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				for (int val = 0; val < attributeCount; ++val) {
					Double input = null;
					while (input == null) {
						System.out
								.println("Proszę wpisać wartość atrybutu numer "
										+ val
										+ " (część ułamkową oddzielamy kropką):");

						try {
							input = Double.valueOf(in.readLine());
						} catch (Exception ex) {
							System.out.println("Wpisano niepoprawną wartość.");
						}
					}

					newObj.add(input);
				}
				try {
					in.close();
				} catch (Exception e) {
					log.debug("Problem z zamknięciem strumienia odczytu.");
				}

				System.out.println("-----------------------------------");
				System.out.println("Obiekt został przypisany do klasy: "
						+ ObjectClassificator.classificate(alg.getSteps(),
								newObj));
			}

			System.out.println("Aplikacja zakończyła działanie.");
		}
	}
}
