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
		sb.append("Atrybut: ").append(attribute).append("\n");
		sb.append("Punkt: ").append(point).append("\n");
		sb.append("Koncentracja: ").append(concentration).append("\n");
		sb.append("Klasa: ").append(objClass).append("\n");
		sb.append("Strona: ").append(minusSide ? "Strona minusowa" : "Strona plusowa").append("\n");
		
		return sb.toString();
	}

}
