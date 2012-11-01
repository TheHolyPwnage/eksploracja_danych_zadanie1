package pl.mchaniewski.ed.zadanie1.model;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<List<Double>> data;

	public Data() {
		data = new ArrayList<List<Double>>();
	}

	/**
	 * Zwraca wartość komórki.
	 * 
	 * @param row
	 *            wiersz
	 * @param column
	 *            kolumna
	 * @return null lub Double
	 */
	public Double getCell(int row, int column) {
		try {
			return data.get(row).get(column);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public Integer getRowCount() {
		return data.size();
	}

	public Integer getColumnCount() {
		try {
			return data.get(0).size();
		} catch (IndexOutOfBoundsException ex) {
			return 0;
		} catch (NullPointerException ex) {
			return 0;
		}
	}

	public void addRow(List<Double> row) {
		data.add(row);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Ilość kolumn: ").append(getColumnCount()).append("\n");
		sb.append("Ilość wierszy: ").append(getRowCount()).append("\n");
		sb.append("---------------------------------------").append("\n");

		for (List<Double> row : data) {
			for (Double cell : row) {
				sb.append(cell).append(" ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}
