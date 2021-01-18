package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
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

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import conexaoBanco.GerenciarBancoContratos;

import javax.swing.border.LineBorder;



public class TelaPadrao extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;


	public TelaPadrao() {
		setModal(true);

		TelaPadrao isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1087, 620);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		
		 JPanel painelContratos = new JPanel();
		 painelContratos.setBackground(Color.ORANGE);
		 painelContratos.setForeground(Color.BLACK);
		 painelContratos.setBounds(197, 154, 865, 356);
		 painelPrincipal.add(painelContratos);
		 painelContratos.setLayout(null);
		 
		 JLabel lblNewLabel = new JLabel("Total Contratos:");
		 lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 lblNewLabel.setBounds(45, 67, 115, 20);
		 painelContratos.add(lblNewLabel);
		 
		 
		  lblTotalContratos = new JLabel("New label");
		 lblTotalContratos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		 lblTotalContratos.setBounds(170, 64, 138, 24);
		 painelContratos.add(lblTotalContratos);
		 
		 JLabel lblAbertos = new JLabel("Abertos:");
		 lblAbertos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 lblAbertos.setBounds(99, 98, 61, 20);
		 painelContratos.add(lblAbertos);
		 
		  lblTotalContratosAbertos = new JLabel("New label");
		 lblTotalContratosAbertos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		 lblTotalContratosAbertos.setBounds(170, 99, 138, 24);
		 painelContratos.add(lblTotalContratosAbertos);
		 
		 JLabel lblConcluidos = new JLabel("Concluidos:");
		 lblConcluidos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 lblConcluidos.setBounds(80, 135, 83, 15);
		 painelContratos.add(lblConcluidos);
		 
		  lblTotalContratosConcluidos = new JLabel("New label");
		 lblTotalContratosConcluidos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		 lblTotalContratosConcluidos.setBounds(170, 129, 138, 24);
		 painelContratos.add(lblTotalContratosConcluidos);
		 
			
		
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
	
	
	public void setInfoContratos() {
		
		//lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
        GerenciarBancoContratos gerenciar  =new GerenciarBancoContratos();
        ArrayList<CadastroContrato>  contratos = gerenciar.getContratosPorCliente(1,  0, contrato_local.getId());
        
		
		
	}
}
