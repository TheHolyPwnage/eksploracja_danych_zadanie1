package pl.mchaniewski.ed.zadanie1.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.mchaniewski.ed.zadanie1.model.RawData;

public class Algorithm {
	private static final Logger log = LoggerFactory.getLogger(Algorithm.class);

	private List<SingleStep> steps;
	private RawData data;

	public Algorithm(RawData data) {
		this.data = data;
		steps = new ArrayList<SingleStep>();
	}

	public void run() {
		RawData tmpData = data;
		while (tmpData != null && tmpData.getRowCount() > 0) {
			SingleStep ss = null;

			for (int i = 0; i < tmpData.getColumnCount() - 1; ++i) {
				SingleStep tmpStep = new SingleStep(tmpData, i);
				tmpStep.doStep();

				if (ss == null
						|| ss.getCutoffPoint().getConcentration() < tmpStep
								.getCutoffPoint().getConcentration()) {
					ss = tmpStep;
				}
			}

			tmpData = ss.getNewData();
			steps.add(ss);

			log.debug("Znaleziono linię:\n{}", ss.getCutoffPoint().toString());
		}

		log.debug("Znaleziono {} linii.", (steps.size() - 1));
	}

	public List<SingleStep> getSteps() {
		return steps;
	}
}
