package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.graficos.GraficoLinha;

import javax.swing.border.LineBorder;



public class TelaVizualizarGrafico extends JDialog {

	private final JPanel painelPrincipal = new JPanel();


	public TelaVizualizarGrafico(DefaultCategoryDataset dataset) {
		setModal(true);

		TelaVizualizarGrafico isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 679, 418);
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		 String hoje   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 String semana   = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		 Map<String, Double> lista_cargas = gerenciar.getCarregamentosPorData(semana, hoje, 0);
		  dataset = new DefaultCategoryDataset();
			
			
		 for (Map.Entry<String,Double> pair : lista_cargas.entrySet()) {
			   
			    System.out.println(pair.getKey());
			    System.out.println(pair.getValue());
				
				dataset.addValue(pair.getValue(), "", pair.getKey());
				
			}
		 
			

			
		
		
		
		GraficoLinha linha = new GraficoLinha();
		linha.setDataset(dataset);
		ChartPanel chartPanel = linha.getGraficoLinha(panel.getWidth()-100, panel.getHeight()-100, "Últimos 30 dias", "Romaneios", "Quantidad de Sacos");
		panel.add(chartPanel);
	
		
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
}
