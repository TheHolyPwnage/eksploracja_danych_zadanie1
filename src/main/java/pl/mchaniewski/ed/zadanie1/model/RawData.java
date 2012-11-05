package pl.mchaniewski.ed.zadanie1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawData implements Cloneable {
	private List<List<Double>> data;
	private Map<Integer, Double> minimumValues;
	private Map<Integer, Double> maximumValues;

	public RawData() {
		data = new ArrayList<List<Double>>();
		minimumValues = new HashMap<Integer, Double>();
		maximumValues = new HashMap<Integer, Double>();
	}

	public RawData(List<List<Double>> data) {
		this.data = new ArrayList<List<Double>>(data);
	}

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

	private class AttributeComparator implements Comparator<List<Double>> {
		private final int attributeNumber;

		public AttributeComparator(int attributeNumber) {
			this.attributeNumber = attributeNumber;
		}

		public int compare(List<Double> o1, List<Double> o2) {
			return o1.get(attributeNumber).compareTo(o2.get(attributeNumber));
		}

	}

	public Double getRowsClass(int row) {
		return data.get(row).get(data.get(row).size() - 1);
	}

	public void sortDataByAttribute(int attributeNumber) {
		Collections.sort(data, new AttributeComparator(attributeNumber));
	}

	public RawData dataWithoutSelectedRows(int rows, boolean top) {
		if (rows >= data.size()) {
			return new RawData();
		}

		if (rows < 0) {
			return this;
		}

		if (top) {
			return new RawData(data.subList(rows, data.size()));
		} else {
			return new RawData(data.subList(0, rows + 1));
		}
	}

	public List<Double> getDistinctClasses() {
		List<Double> classes = new ArrayList<Double>();
		for (int i = 0; i < getRowCount(); ++i) {
			if (!classes.contains(this.getRowsClass(i))) {
				classes.add(this.getRowsClass(i));
			}
		}

		return classes;
	}

	public Double getAttributesMaximumValue(int attributeIndex) {
		if (!maximumValues.containsKey(attributeIndex)) {
			Double max = null;
			for (int row = 0; row < getRowCount(); ++row) {
				if (max == null || getCell(row, attributeIndex) > max) {
					max = getCell(row, attributeIndex);
				}
			}

			maximumValues.put(attributeIndex, max);
		}

		return maximumValues.get(attributeIndex);
	}

	public Double getAttributesMinimumValue(int attributeIndex) {
		if (!minimumValues.containsKey(attributeIndex)) {
			Double min = null;
			for (int row = 0; row < getRowCount(); ++row) {
				if (min == null || getCell(row, attributeIndex) < min) {
					min = getCell(row, attributeIndex);
				}
			}

			minimumValues.put(attributeIndex, min);
		}

		return minimumValues.get(attributeIndex);
	}

	public boolean isAllObjectSingleClass() {
		boolean singleClass = true;

		Double clazz = null;
		for (int row = 0; row < getRowCount(); ++row) {
			if (clazz == null) {
				clazz = getRowsClass(row);
				continue;
			}

			if (!clazz.equals(getRowsClass(row))) {
				singleClass = false;
				break;
			}
		}

		return singleClass;
	}

	public String rowToString(int rowNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("Row #").append(rowNumber);
		for (Double val : data.get(rowNumber)) {
			sb.append(" ").append(val);
		}

		return sb.toString();
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
