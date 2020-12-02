package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaContratos extends JDialog {

	private final JPanel painelPrincipal = new JPanel();


	public TelaContratos() {
		setModal(true);

		TelaContratos isto = this;
		
		setResizable(false);
		setTitle("E-Contract - Contratos");

		

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnContrato = new JButton("Novo Contrato");
		btnContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato();
			}
		});
		//btnContrato.setIcon(new ImageIcon(TelaContratos.class.getResource("/imagens/add_contrato.png")));
		btnContrato.setToolTipText("Adicionar Novo Contrato");
		btnContrato.setBounds(437, 82, 172, 33);
		painelPrincipal.add(btnContrato);
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}
}
