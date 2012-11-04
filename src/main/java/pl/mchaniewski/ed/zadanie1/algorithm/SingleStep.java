package pl.mchaniewski.ed.zadanie1.algorithm;

import pl.mchaniewski.ed.zadanie1.model.CutoffPoint;
import pl.mchaniewski.ed.zadanie1.model.RawData;

/**
 * Jedna z najgorszych klas jakie w życiu napisałem... ffs...
 * 
 */
public class SingleStep {
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

		TopPart upper = new TopPart(data);
		upper.start();
		BottomPart bottom = new BottomPart(data);
		bottom.start();

		try {
			bottom.join();
			upper.join();
		} catch (InterruptedException e) {
			// TODO error....
			e.printStackTrace();
		}

		if (upper.getConcentration() >= bottom.getConcentration()) {
			cutoffPoint.setPoint(data.getCell(upper.getRow() - 1,
					cutoffPoint.getAttribute()));
			cutoffPoint.setMinusSide(true);
			cutoffPoint.setPlusSide(false);
			cutoffPoint.setObjClass(upper.getObjClass());
			cutoffPoint.setConcentration(upper.getConcentration());
			newData = data.dataWithoutSelectedRows(upper.getRow(), true);
		} else {
			cutoffPoint.setPoint(data.getCell(bottom.getRow() + 1,
					cutoffPoint.getAttribute()));
			cutoffPoint.setMinusSide(false);
			cutoffPoint.setPlusSide(true);
			cutoffPoint.setObjClass(bottom.getObjClass());
			cutoffPoint.setConcentration(bottom.getConcentration());
			newData = data.dataWithoutSelectedRows(bottom.getRow(), false);
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
		private final RawData data;
		private int concentration;
		private Double objClass;
		private int row;

		public TopPart(RawData data) {
			this.data = data;
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
		private final RawData data;
		private int concentration;
		private Double objClass;
		private int row;

		public BottomPart(RawData data) {
			this.data = data;
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
