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
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;

public class GraficoMultiplaLinhaTemporal {

	private boolean aplicarSimbolos = false;

	private XYDataset  dataSetGlobal;
	public GraficoMultiplaLinhaTemporal() {

	}

	
	public boolean isAplicarSimbolos() {
		return aplicarSimbolos;
	}




	public void setAplicarSimbolos(boolean aplicarSimbolos) {
		this.aplicarSimbolos = aplicarSimbolos;
	}


	public ChartPanel getGraficoLinha(int x, int y, String periodo, String titulo, String legenda_vertical, int padrao) {

		JFreeChart xyChart = ChartFactory.createTimeSeriesChart(titulo, periodo, legenda_vertical, dataSetGlobal,
				  true, true, true);

		ChartPanel chartPanel = new ChartPanel(xyChart);
        XYPlot plot = (XYPlot) xyChart.getPlot();
        XYLineAndShapeRenderer  renderer = (XYLineAndShapeRenderer ) plot.getRenderer();

       
        DecimalFormat decimalformat1 = null;

        
        if(padrao == 0) {
        	decimalformat1 =  new DecimalFormat("#,##0.00");
        }else if(padrao == 1) {
        	decimalformat1 =  new DecimalFormat("#,##0.00 sacos");

        }else if(padrao == 2) {
        	decimalformat1 =  new DecimalFormat("#,##0.00 U");

        }else if(padrao == 3) {
        	decimalformat1 =  new DecimalFormat("R$ #,##0.00");

        }
        
        XYItemLabelGenerator generator = new StandardXYItemLabelGenerator("{2}", decimalformat1, decimalformat1);
        
        renderer.setSeriesItemLabelGenerator(0, generator);
        renderer.setSeriesItemLabelsVisible(0, true);
        
        renderer.setSeriesItemLabelGenerator(1, generator);
        renderer.setSeriesItemLabelsVisible(1, true);

        renderer.setSeriesShape(0, new Rectangle(-2, -2, 4, 4));
        renderer.setSeriesShape(1, new Rectangle(-2, -2, 4, 4));

        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);

        if(aplicarSimbolos) {
       	 String[] grade =  new String[13];
    	    grade[0] = "";
       	    grade[1] = "janeiro";
       	    grade[2] = "fevereiro";
       	    grade[3] = "março";
       	    grade[4] = "abril";
       	    grade[5] = "maio";
       	    grade[6] = "junho";
       	    grade[7] = "julho";
       	    grade[8] = "agosto";
       	    grade[9] = "setembro";
       	    grade[10] = "outubro";
       	    grade[11] = "novembro";
       	    grade[12] = "dezembro";

       	    
       	    SymbolAxis rangeAxis = new SymbolAxis(periodo, grade);

       	    rangeAxis.setTickUnit(new NumberTickUnit(1));
       	    rangeAxis.setRange(0,grade.length);
       	    plot.setDomainAxis(rangeAxis); 

       }
        
        if(aplicarSimbolos) {
        	  plot.getRenderer().setSeriesPaint(0, Color.RED);
              plot.getRenderer().setSeriesPaint(1, Color.GREEN);
        }else {
        	  plot.getRenderer().setSeriesPaint(0, Color.BLUE);
              plot.getRenderer().setSeriesPaint(1, Color.GREEN);
        }
       
      


		plot.setRenderer(renderer);

		//plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		 
		//Define the format to the value to the draw
	
		        
		chartPanel.setPreferredSize(new java.awt.Dimension(x, y));
		chartPanel.setBackground(Color.white);
		chartPanel.setBounds(0, 0, x+100, y+100);
		
		
		
		return chartPanel;
	}
	
	public void setDataset(XYDataset  dataset) {
		this.dataSetGlobal = dataset;
		
		
	}

	
	
	
	
}