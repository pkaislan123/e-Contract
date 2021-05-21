package main.java.graficos;
import org.jfree.chart.ChartPanel;

import java.awt.Color;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoLinhaDupla {

	private DefaultCategoryDataset dataSetGlobal;
	public GraficoLinhaDupla() {

	}

	public ChartPanel getGraficoLinha(int x, int y, String periodo, String titulo, String legenda_vertical) {

		
		JFreeChart lineChart = ChartFactory.createLineChart(titulo, periodo, legenda_vertical, dataSetGlobal,
				PlotOrientation.VERTICAL, true, true, true);

		ChartPanel chartPanel = new ChartPanel(lineChart);
	
		CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
       
       DecimalFormat decimalformat1 = new DecimalFormat("##");
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));

        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultSeriesVisible(true);

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		 
		//Define the format to the value to the draw
	
		        
		chartPanel.setPreferredSize(new java.awt.Dimension(x, y));
		chartPanel.setBackground(Color.white);
		chartPanel.setBounds(0, 0, x+100, y+100);
		
		
		
		return chartPanel;
	}
	
	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataSetGlobal = dataset;
		
		
	}

	
	
	
	
}