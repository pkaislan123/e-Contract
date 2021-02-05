package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import conexaoBanco.GerenciarBancoContratos;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;



public class TelaHome extends JFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaHome isto;
    private JDialog telaPai;

	public TelaHome(Window janela_pai) {

		 isto = this;
		setResizable(true);
		setTitle("E-Contract - Home");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1087, 620);
		painelPrincipal.kGradientFocus = 2500;
		painelPrincipal.kStartColor = new Color(0, 204, 204);
		painelPrincipal.kEndColor = new Color(153, 102, 0);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JButton btnNewButton = new JButton("Clientes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(1,0, isto);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBounds(120, 216, 89, 23);
		painelPrincipal.add(btnNewButton);
		
		JButton btnTransportadores = new JButton("Transportadores");
		btnTransportadores.setBounds(260, 216, 120, 28);
		painelPrincipal.add(btnTransportadores);
		
		JButton btnContratos = new JButton("Contratos");
		btnContratos.setBounds(120, 278, 120, 28);
		painelPrincipal.add(btnContratos);
	
		
		
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
