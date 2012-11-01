package pl.mchaniewski.ed.zadanie1.utils;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class CommandLineUtils {
	public static String FILE_NAME_OPTION = "file";
	public static String ENCODING_OPTION = "enc";

	private CommandLineUtils() {
		// Blokujemy możliwość tworzenia obiektu.
	}

	@SuppressWarnings("static-access")
	public static Options getOptions() {
		Option optionFileName = OptionBuilder.withArgName("fileName").hasArg()
				.withDescription("Nazwa pliku z danymi")
				.create(FILE_NAME_OPTION);
		Option optionEncoding = OptionBuilder.withArgName("encoding").hasArg()
				.withDescription("Kodowanie pliku z danymi")
				.create(ENCODING_OPTION);

		Options options = new Options();
		options.addOption(optionFileName);
		options.addOption(optionEncoding);

		return options;
	}

}
