package pl.mchaniewski.ed.zadanie1.model;

public class CutoffPoint {
	private Integer attribute;
	private int concentration;
	private Double objClass;
	private Double point;
	private boolean minusSide;
	private boolean plusSide;

	public CutoffPoint() {
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public boolean isMinusSide() {
		return minusSide;
	}

	public void setMinusSide(boolean minusSide) {
		this.minusSide = minusSide;
	}

	public boolean isPlusSide() {
		return plusSide;
	}

	public void setPlusSide(boolean plusSide) {
		this.plusSide = plusSide;
	}

	public Double getObjClass() {
		return objClass;
	}

	public void setObjClass(Double objClass) {
		this.objClass = objClass;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration(int concentration) {
		this.concentration = concentration;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Odcięcie dla atrybutu: ").append(attribute).append("\n");
		sb.append("Punkt odcięcia: ").append(point).append("\n");
		sb.append("Ilość odciętych obiektów: ").append(concentration)
				.append("\n");
		sb.append("Klasa odciętych obiektów: ").append(objClass).append("\n");
		sb.append("Strona odciętych obiektów: ");
		if (minusSide) {
			sb.append("minusowa").append("\n");
		} else if (plusSide) {
			sb.append("plusowa").append("\n");
		} else {
			sb.append("żadna ze stron (ostatni obszar)").append("\n");
		}

		return sb.toString();
	}

}
