package pl.mchaniewski.ed.zadanie1.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.mchaniewski.ed.zadanie1.model.CutoffPoint;
import pl.mchaniewski.ed.zadanie1.model.RawData;

public class SingleStep {
	private static final Logger log = LoggerFactory.getLogger(SingleStep.class);

	private RawData data;
	private CutoffPoint cutoffPoint;
	private RawData newData;

	protected SingleStep(RawData data, int attribute) {
		this.data = data;
		cutoffPoint = new CutoffPoint();
		cutoffPoint.setAttribute(attribute);
	}

	public void doStep() {
		data.sortDataByAttribute(cutoffPoint.getAttribute());

		TopPart upper = new TopPart();
		upper.start();
		BottomPart bottom = new BottomPart();
		bottom.start();

		try {
			bottom.join();
			upper.join();
		} catch (InterruptedException e) {
			if (log.isErrorEnabled()) {
				log.error("Wystąpił problem związay z wielowątkowością.", e);
			}
		}

		if (upper.getConcentration() == bottom.getConcentration()
				&& data.isAllObjectSingleClass()) {
			cutoffPoint.setPoint(null);
			cutoffPoint.setMinusSide(false);
			cutoffPoint.setPlusSide(false);
			cutoffPoint.setObjClass(upper.getObjClass());
			cutoffPoint.setConcentration(upper.getConcentration());
			newData = null;

			log.debug(
					"Znaleziono ostatni obszar, który zawiera elementy jednej klasy '{}'.",
					cutoffPoint.getAttribute());
		} else if (upper.getConcentration() >= bottom.getConcentration()) {
			cutoffPoint.setPoint(data.getCell(upper.getRow() - 1,
					cutoffPoint.getAttribute()));
			cutoffPoint.setMinusSide(true);
			cutoffPoint.setPlusSide(false);
			cutoffPoint.setObjClass(upper.getObjClass());
			cutoffPoint.setConcentration(upper.getConcentration());
			newData = data.dataWithoutSelectedRows(upper.getRow(), true);

			log.debug(
					"Górny obszar atrybutu {} jest dominujący. Skupienie górnego: {} Skupienie dolnego: {}",
					cutoffPoint.getAttribute(), upper.getConcentration(),
					bottom.getConcentration());
		} else if (upper.getConcentration() < bottom.getConcentration()) {
			cutoffPoint.setPoint(data.getCell(bottom.getRow() + 1,
					cutoffPoint.getAttribute()));
			cutoffPoint.setMinusSide(false);
			cutoffPoint.setPlusSide(true);
			cutoffPoint.setObjClass(bottom.getObjClass());
			cutoffPoint.setConcentration(bottom.getConcentration());
			newData = data.dataWithoutSelectedRows(bottom.getRow(), false);

			log.debug(
					"Dolny obszar atrybutu {} jest dominujący. Skupienie górnego: {} Skupienie dolnego: {}",
					cutoffPoint.getAttribute(), upper.getConcentration(),
					bottom.getConcentration());
		}
	}

	public RawData getData() {
		return data;
	}

	public void setData(RawData data) {
		this.data = data;
	}

	public CutoffPoint getCutoffPoint() {
		return cutoffPoint;
	}

	public void setCutoffPoint(CutoffPoint cutoffPoint) {
		this.cutoffPoint = cutoffPoint;
	}

	public RawData getNewData() {
		return newData;
	}

	public void setNewData(RawData newData) {
		this.newData = newData;
	}

	private class TopPart extends Thread {
		private int concentration;
		private Double objClass;
		private int row;

		public TopPart() {
			concentration = 0;
			objClass = null;
			row = data.getRowCount() - 1;
		}

		@Override
		public void run() {
			for (int row = 0; row < data.getRowCount(); ++row) {
				if (row == 0) {
					objClass = data.getRowsClass(row);
				}

				if (!data.getRowsClass(row).equals(objClass)) {
					Double value = data
							.getCell(row, cutoffPoint.getAttribute());
					if (row - 1 >= 0
							&& value.equals(data.getCell(row - 1,
									cutoffPoint.getAttribute()))) {
						log.debug(
								"Znalezniono miejsce w górnym obszarze w którym dla wartości {} atrybutu {} obiekty mają różne klasy.",
								value, cutoffPoint.getAttribute());
						log.debug("Wartości: {}", data.rowToString(row));

						int i = row;
						while (i >= 0
								&& value.equals(data.getCell(i,
										cutoffPoint.getAttribute()))) {
							--i;
							--concentration;
						}
						// do {
						// --i;
						// --concentration;
						// } while ((i - 1) >= 0
						// && value.equals(data.getCell(i,
						// cutoffPoint.getAttribute())));

						this.row = i;
					}

					this.row = row;
					break;
				}

				++concentration;
			}
		}

		public int getConcentration() {
			return concentration;
		}

		public Double getObjClass() {
			return objClass;
		}

		public int getRow() {
			return row;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Koncentracja: ").append(concentration).append("\n");
			sb.append("Wiersz: ").append(row).append("\n");

			return sb.toString();
		}
	}

	private class BottomPart extends Thread {
		private int concentration;
		private Double objClass;
		private int row;

		public BottomPart() {
			concentration = 0;
			objClass = null;
			row = 0;
		}

		@Override
		public void run() {
			for (int row = data.getRowCount() - 1; row >= 0; --row) {
				if (row == data.getRowCount() - 1) {
					objClass = data.getRowsClass(row);
				}

				if (!data.getRowsClass(row).equals(objClass)) {
					Double value = data
							.getCell(row, cutoffPoint.getAttribute());
					if (row + 1 < data.getRowCount()
							&& value.equals(data.getCell(row + 1,
									cutoffPoint.getAttribute()))) {
						log.debug(
								"Znalezniono miejsce w dolnym obszarze w którym dla wartości {} atrybutu {} obiekty mają różne klasy.",
								value, cutoffPoint.getAttribute());
						log.debug("Wartości: {}", data.rowToString(row));

						int i = row;
						while (i < data.getRowCount()
								&& value.equals(data.getCell(i,
										cutoffPoint.getAttribute()))) {
							++i;
							--concentration;
						}
						// do {
						// ++i;
						// --concentration;
						// lub i < data.getRowCount()
						// } while ((i + 1) < data.getRowCount()
						// && value.equals(data.getCell(i,
						// cutoffPoint.getAttribute())));

						this.row = i;
					}

					this.row = row;
					break;
				}

				++concentration;
			}
		}

		public int getConcentration() {
			return concentration;
		}

		public Double getObjClass() {
			return objClass;
		}

		public int getRow() {
			return row;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Koncentracja: ").append(concentration).append("\n");
			sb.append("Wiersz: ").append(row).append("\n");

			return sb.toString();
		}
	}

}
