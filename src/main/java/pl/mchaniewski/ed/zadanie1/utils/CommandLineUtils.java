package pl.mchaniewski.ed.zadanie1.utils;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class CommandLineUtils {
	private static Options options;

	public static String FILE_NAME_OPTION = "file";
	public static String ENCODING_OPTION = "enc";
	public static String GENERATE_MAIN_FILE_SHORT_OPTION = "sl";
	public static String GENERATE_MAIN_FILE_LONG_OPTION = "save-lines";
	public static String GENERATE_STEP_FILES_SHORT_OPTION = "ss";
	public static String GENERATE_STEP_FILES_LONG_OPTION = "save-steps";
	public static String HELP_SHORT_OPTION = "h";
	public static String HELP_LONG_OPTION = "help";
	public static String WRITE_OUT_LINE_OPTION = "lines";
	public static String MANUAL_CLASSIFICATION_OPTION = "manual";

	private CommandLineUtils() {
		// Blokujemy możliwość tworzenia obiektu.
	}

	@SuppressWarnings("static-access")
	public static Options getOptions() {
		if (options == null) {
			Option optionFileName = OptionBuilder.withArgName("fileName")
					.hasArg().withDescription("Nazwa pliku z danymi")
					.create(FILE_NAME_OPTION);
			Option optionEncoding = OptionBuilder.withArgName("encoding")
					.hasArg().withDescription("Kodowanie pliku z danymi")
					.create(ENCODING_OPTION);
			Option optionGenerateMainFile = new Option(
					GENERATE_MAIN_FILE_SHORT_OPTION,
					GENERATE_MAIN_FILE_LONG_OPTION, false,
					"Zapisuje graf z liniami w postaci pliku graficznego.");
			Option optionGenerateStepFiles = new Option(
					GENERATE_STEP_FILES_SHORT_OPTION,
					GENERATE_STEP_FILES_LONG_OPTION, false,
					"Zapisuje kolejne kroki w postaci plików graficznych.");
			Option optionWriteOutLine = new Option(WRITE_OUT_LINE_OPTION,
					false,
					"Po zakończeniu obliczeń wypisuje dane wszystkich linii.");
			Option optionManualClassification = new Option(
					MANUAL_CLASSIFICATION_OPTION, false,
					"Umożliwia klasyfikowanie ręcznie dodanych obiektów.");
			Option optionHelp = new Option(HELP_SHORT_OPTION, HELP_LONG_OPTION,
					false, "Wyświetlenie pomocy.");

			Options optionList = new Options();
			optionList.addOption(optionFileName);
			optionList.addOption(optionEncoding);
			optionList.addOption(optionGenerateMainFile);
			optionList.addOption(optionGenerateStepFiles);
			optionList.addOption(optionWriteOutLine);
			optionList.addOption(optionManualClassification);
			optionList.addOption(optionHelp);

			options = optionList;
		}

		return options;
	}

	public static void getHelp(String applicationName) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(applicationName, options);
	}
}
