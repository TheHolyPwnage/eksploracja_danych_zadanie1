package pl.mchaniewski.ed.zadanie1.parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import pl.mchaniewski.ed.zadanie1.model.RawData;

public class CsvParser extends Parser {
	private static String DELIMITER = ",";

	public CsvParser(String fileName, String fileEncoding) {
		super(fileName, fileEncoding);
	}

	@Override
	public RawData parse() throws FileNotFoundException,
			InvalidLineLengthException {
		Scanner scanner = getScanner();
		RawData data = new RawData();

		try {
			int lineNumber = 1;
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (StringUtils.isEmpty(line)) {
					continue;
				}

				Scanner doubleScanner = new Scanner(line);
				doubleScanner.useDelimiter(DELIMITER);

				List<Double> rowData = new ArrayList<Double>();
				try {
					while (doubleScanner.hasNextDouble()) {
						rowData.add(doubleScanner.nextDouble());
					}
				} finally {
					doubleScanner.close();
				}
				
				if (rowData.isEmpty()) {
					continue;
				}

				if (data.getColumnCount() > 0
						&& rowData.size() != data.getColumnCount()) {
					throw new InvalidLineLengthException(
							String.format(
									"Ilość wartości (%d) w linii numer %d nie pokrywa się z ilością wartości w poprzednich liniach (%d).",
									rowData.size(), lineNumber,
									data.getColumnCount()));
				} else {
					data.addRow(rowData);
				}

				++lineNumber;
			}
		} finally {
			scanner.close();
		}

		return data;
	}
}
