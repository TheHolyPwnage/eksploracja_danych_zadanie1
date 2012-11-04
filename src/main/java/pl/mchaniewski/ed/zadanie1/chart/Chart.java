package pl.mchaniewski.ed.zadanie1.chart;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.mchaniewski.ed.zadanie1.model.RawData;

public class Chart {
	private RawData data;
	private int xAxisIndex;
	private int yAxisIndex;
	private JFreeChart chart;

	public Chart(RawData data, int xAxisIndex, int yAxisIndex) {
		this.data = data;
		this.xAxisIndex = xAxisIndex;
		this.yAxisIndex = yAxisIndex;
	}

	public JFreeChart generateChart() {
		if (chart == null) {
			chart = ChartFactory.createScatterPlot("Wykres rozproszenia",
					"Atrybut " + xAxisIndex, "Atrybut" + yAxisIndex,
					generateScatterDataSet(), PlotOrientation.VERTICAL, true, true,
					false);
			
			
			XYPlot xyPlot = (XYPlot) chart.getPlot();
			xyPlot.setDomainCrosshairVisible(true);
			xyPlot.setRangeCrosshairVisible(true);
			
			// -------------------------
			
//			XYPlot plot = new XYPlot();

			/* Line */
//			XYDataset scatterData = generateScatterDataSet();
//			XYItemRenderer scatterRenderer = new XYLineAndShapeRenderer(true, false);
//			ValueAxis xAxisTitle = new NumberAxis("Atrybut " + xAxisIndex);
//			ValueAxis yAxisTitle = new NumberAxis("Atrybut " + yAxisIndex);
//
//			xyPlot.setDataset(1, scatterData);
//			xyPlot.setRenderer(1, scatterRenderer);
//			xyPlot.setDomainAxis(1, xAxisTitle);
//			xyPlot.setRangeAxis(1, yAxisTitle);
//			xyPlot.mapDatasetToDomainAxis(1, 0);
//			xyPlot.mapDatasetToRangeAxis(1, 0);
			
			
			
			
			// Map the scatter to the first Domain and first Range
			//plot.mapDatasetToDomainAxis(0, 0);
			//plot.mapDatasetToRangeAxis(0, 0);
			
			/* Line */
			// Create the line data, renderer, and axis
			// XYDataset collection2 = getLinePlotData();
			// XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true,
			// false); // Lines only
			// ValueAxis domain2 = new NumberAxis("Domain2");
			// ValueAxis range2 = new NumberAxis("Range2");

			// Set the line data, renderer, and axis into plot
//			plot.setDataset(1, collection2);
//			plot.setRenderer(1, renderer2);
//			plot.setDomainAxis(1, domain2);
//			plot.setRangeAxis(1, range2);

			// Map the line to the second Domain and second Range
//			plot.mapDatasetToDomainAxis(1, 1);
//			plot.mapDatasetToRangeAxis(1, 1);
		}

		return chart;
	}

	public void generatePng(String fileName) {
		try {
			ChartUtilities.saveChartAsPNG(new File(fileName + ".png"),
					generateChart(), 1440, 768);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
