package pl.mchaniewski.ed.zadanie1.algorithm;

import java.util.ArrayList;
import java.util.List;

import pl.mchaniewski.ed.zadanie1.chart.Chart;
import pl.mchaniewski.ed.zadanie1.model.RawData;

public class Algorithm {
	private List<SingleStep> steps;
	private RawData data;
	
	public Algorithm(RawData data) {
		this.data = data;
		steps = new ArrayList<SingleStep>();
	}
	
	public void run() {
		RawData tmpData = data;
		int imageCounter = 0;
		while (tmpData.getRowCount() > 0) {
			SingleStep ss = null;
			
//			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
//			System.out.println("PRZED:");
//			System.out.println(tmpData);
			
			for (int i = 0; i < tmpData.getColumnCount() - 1; ++i) {
				SingleStep tmpStep = new SingleStep(tmpData, i);
				tmpStep.doStep();
				
				if (ss == null || ss.getCutoffPoint().getConcentration() <= tmpStep.getCutoffPoint().getConcentration()) {
					ss = tmpStep;
				}
			}

			tmpData = ss.getNewData();
			steps.add(ss);


			Chart chart = new Chart(tmpData, 0, 1);
			chart.generatePng("after_" + ++imageCounter);
//			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
//			System.out.println("PO:");
//			System.out.println(tmpData);
		}
		
		for (SingleStep step : steps) {
			System.out.println(step.getCutoffPoint().toString());
		}
		System.out.println(steps.size());
	}
}
