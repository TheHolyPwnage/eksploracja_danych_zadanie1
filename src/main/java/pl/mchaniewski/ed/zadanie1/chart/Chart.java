package pl.mchaniewski.ed.zadanie1.chart;

import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.mchaniewski.ed.zadanie1.algorithm.SingleStep;
import pl.mchaniewski.ed.zadanie1.model.RawData;

public class Chart extends Thread {
	private RawData data;
	private List<SingleStep> steps;
	private String fileName;

	private int xAxisIndex;
	private int yAxisIndex;
	private JFreeChart chart;

	public Chart(RawData data, int xAxisIndex, int yAxisIndex) {
		this.data = data;
		this.xAxisIndex = xAxisIndex;
		this.yAxisIndex = yAxisIndex;
	}

	public Chart(RawData data, int xAxisIndex, int yAxisIndex,
			List<SingleStep> steps) {
		this.data = data;
		this.xAxisIndex = xAxisIndex;
		this.yAxisIndex = yAxisIndex;
		this.steps = steps;
	}

	public JFreeChart generateChart() {
		if (chart == null) {
			chart = ChartFactory.createScatterPlot("Wykres rozproszenia",
					"Atrybut " + xAxisIndex, "Atrybut" + yAxisIndex,
					generateScatterDataSet(), PlotOrientation.VERTICAL, true,
					true, false);

			XYPlot xyPlot = (XYPlot) chart.getPlot();
			xyPlot.setDomainCrosshairVisible(true);
			xyPlot.setRangeCrosshairVisible(true);

			if (steps != null) {
				generateLineDataSet(xyPlot);
			}
		}

		return chart;
	}

	@Override
	public void run() {
		try {
			ChartUtilities.saveChartAsPNG(new File(fileName + ".png"),
					generateChart(), 1440, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private XYDataset generateScatterDataSet() {
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

		for (Double clazz : data.getDistinctClasses()) {
			XYSeries series = new XYSeries("Klasa " + clazz);

			for (int row = 0; row < data.getRowCount(); ++row) {
				if (data.getRowsClass(row).equals(clazz)) {
					series.add(data.getCell(row, xAxisIndex),
							data.getCell(row, yAxisIndex));
				}
			}
			xySeriesCollection.addSeries(series);
		}
		return xySeriesCollection;
	}

	private void generateLineDataSet(XYPlot plot) {
		int counter = 1;
		for (SingleStep step : steps) {
			if (step.getCutoffPoint().getPoint() == null) {
				break;
			}

			++counter;

			XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
			XYSeries series = new XYSeries("Linia numer " + (counter - 1));
			if (step.getCutoffPoint().getAttribute() == xAxisIndex) {
				series.add(step.getCutoffPoint().getPoint(),
						data.getAttributesMinimumValue(yAxisIndex));
				series.add(step.getCutoffPoint().getPoint(),
						data.getAttributesMaximumValue(yAxisIndex));
			} else if (step.getCutoffPoint().getAttribute() == yAxisIndex) {
				series.add(data.getAttributesMinimumValue(xAxisIndex), step
						.getCutoffPoint().getPoint());
				series.add(data.getAttributesMaximumValue(xAxisIndex), step
						.getCutoffPoint().getPoint());
			}
			xySeriesCollection.addSeries(series);

			XYItemRenderer lineRenderer = new XYLineAndShapeRenderer(true,
					false);
			lineRenderer.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND, 1.0f, new float[] { 10.0f, 6.0f },
					0.0f));

			plot.setDataset(counter, xySeriesCollection);
			plot.setRenderer(counter, lineRenderer);
		}

	}
}
