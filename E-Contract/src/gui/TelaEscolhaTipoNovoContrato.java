package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cadastros.CadastroModelo;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import gui.TelaNovoContratoInformal;

public class TelaEscolhaTipoNovoContrato extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	ArrayList<CadastroModelo> modelos = new ArrayList<>();
 
	public void pesquisarModelos()
	{
		 GerenciarBancoContratos listaModelos = new GerenciarBancoContratos();
		 modelos = listaModelos.getModelos();
		    
	}

	public TelaEscolhaTipoNovoContrato() {
		setModal(true);

		TelaEscolhaTipoNovoContrato isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Tipo Contrato");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 459, 209);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipo.setForeground(Color.BLACK);
		lblTipo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblTipo.setBackground(Color.ORANGE);
		lblTipo.setBounds(51, 50, 64, 33);
		painelPrincipal.add(lblTipo);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(153, 52, 266, 33);
		
		comboBox.addItem("Padrão - Formal");
		comboBox.addItem("Padrão - Informal");

		painelPrincipal.add(comboBox);
		
		JButton btnCriarContrato = new JButton("OK");
		btnCriarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                   String opcao = comboBox.getSelectedItem().toString();
  
                   if(opcao.equals("Padrão - Formal"))
                   {
                	   
                   }
                   if(opcao.equals("Padrão - Informal"))
                   {
                	  TelaNovoContratoInformal contrato = new TelaNovoContratoInformal(modelos.get(0));
                	   // TelaNovoContrato contrato = new TelaNovoContrato("C:\\Users\\Aislan\\Documents\\modelo_informal_padrao.xlsx");
                	   isto.dispose();
                   }
                   
			}
		});
		btnCriarContrato.setBounds(220, 123, 89, 23);
		painelPrincipal.add(btnCriarContrato);
		
		JButton btnCancelarCriarContrato = new JButton("Cancelar");
		btnCancelarCriarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnCancelarCriarContrato.setBounds(330, 123, 89, 23);
		painelPrincipal.add(btnCancelarCriarContrato);
		
		pesquisarModelos();
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
}
