package pl.mchaniewski.ed.zadanie1.classificator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.mchaniewski.ed.zadanie1.algorithm.SingleStep;

public class ObjectClassificator {
	private static final Logger log = LoggerFactory
			.getLogger(ObjectClassificator.class);

	public static Double classificate(List<SingleStep> steps,
			List<Double> object) {
		Double classificatedClass = null;
		int lineCount = 0;

		for (SingleStep step : steps) {
			++lineCount;
			if (step.getCutoffPoint().isPlusSide()
					&& step.getCutoffPoint().getPoint() <= object.get(step
							.getCutoffPoint().getAttribute())) {
				classificatedClass = step.getCutoffPoint().getObjClass();

				log.debug(
						"Znaleziono klasę obiektu. Linia numer: {} Strona plusowa. Klasa {}",
						lineCount, classificatedClass);

				break;
			} else if (step.getCutoffPoint().isMinusSide()
					&& step.getCutoffPoint().getPoint() >= object.get(step
							.getCutoffPoint().getAttribute())) {
				classificatedClass = step.getCutoffPoint().getObjClass();

				log.debug(
						"Znaleziono klasę obiektu. Linia numer: {} Strona minusowa. Klasa {}",
						lineCount, classificatedClass);

				break;
			} else if (lineCount == steps.size()) {
				classificatedClass = step.getCutoffPoint().getObjClass();

				log.debug("Ostatni obszar. Klasa {}", lineCount,
						classificatedClass);
			}
		}

		return classificatedClass;
	}
}
