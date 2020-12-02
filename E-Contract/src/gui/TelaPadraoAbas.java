package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class TelaPadraoAbas extends JDialog {

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelEmpresa = new JPanel();
	
	 
	public TelaPadraoAbas() {
		setModal(true);

		TelaPadraoAbas isto = this;
		
		setResizable(false);
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Tela Padrao Abas");
		setBounds(100, 100, 993, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelEmpresa.setBackground(new Color(255, 255, 255));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		painelPrincipal.addTab("Empresa", painelEmpresa);
		painelEmpresa.setLayout(null);
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}

	 
}
