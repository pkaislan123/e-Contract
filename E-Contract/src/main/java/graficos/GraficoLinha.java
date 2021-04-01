package main.java.graficos;
import org.jfree.chart.ChartPanel;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoLinha {

	private DefaultCategoryDataset dataSetGlobal;
	public GraficoLinha() {

	}

	public ChartPanel getGraficoLinha(int x, int y, String periodo) {

		
		JFreeChart lineChart = ChartFactory.createLineChart("", periodo, "Quantidade de Sacos", dataSetGlobal,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
	
		CategoryPlot plot = lineChart.getCategoryPlot();
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		plot.setRenderer(renderer);
		chartPanel.setPreferredSize(new java.awt.Dimension(x, y));
		chartPanel.setBackground(Color.white);
		chartPanel.setBounds(0, 0, x+100, y+100);

		return chartPanel;
	}
	
	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataSetGlobal = dataset;
		
		
	}

	private DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		
		
		
		dataset.addValue(15, "schools", "1970");
		dataset.addValue(30, "schools", "1980");
		dataset.addValue(60, "schools", "1990");
		dataset.addValue(120, "schools", "2000");
		dataset.addValue(240, "schools", "2010");
		dataset.addValue(300, "schools", "2014");
		return dataset;
	}
	
	
	
	
}