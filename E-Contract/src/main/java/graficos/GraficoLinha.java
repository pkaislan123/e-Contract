package main.java.graficos;
import org.jfree.chart.ChartPanel;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoLinha {

	private DefaultCategoryDataset dataSetGlobal;
	
	public GraficoLinha() {

	}



	public ChartPanel getGraficoLinha(int x, int y, String periodo, String titulo, String legenda_vertical, int formato) {

		
		
		JFreeChart lineChart = ChartFactory.createLineChart(titulo, periodo, legenda_vertical, dataSetGlobal,
				PlotOrientation.VERTICAL, true, true, true);

		ChartPanel chartPanel = new ChartPanel(lineChart);
	
		CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
       
        DecimalFormat decimalformat1 = null;

        
        if(formato == 0) {
        	decimalformat1 =  new DecimalFormat("#,##0.00");
        }else if(formato == 1) {
        	decimalformat1 =  new DecimalFormat("#,##0.00 sacos");

        }else if(formato == 2) {
        	decimalformat1 =  new DecimalFormat("#,##0.00 U");

        }
       
        
       
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
        renderer.setSeriesShape(0, new Rectangle(-2, -2, 4, 4));
        renderer.setSeriesShapesVisible(0, true);
      
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultSeriesVisible(true);
        plot.getRenderer().setSeriesPaint(0, Color.BLUE);


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