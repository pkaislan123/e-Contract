package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;

public class TelaParaTeste extends JDialog{

	private final JPanel painelPrincipal = new JPanel();
	private JTextField entMensagem;


	public TelaParaTeste() {
		setModal(true);

		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 330, 377);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
         JPanel painel_msg = new JPanel();
         painel_msg.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		painel_msg.setBackground(new Color(255, 255, 204));
		painel_msg.setBounds(0, 0, 800, 72);
		painel_msg.setLayout(new MigLayout("", "[][][][][grow][][][][][][][][][][]", "[grow]"));
	
		
		
		
		JLabel lblCodigoContrato = new JLabel("4444.6.5.6666");
		lblCodigoContrato.setBackground(new Color(153, 204, 0));
		lblCodigoContrato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painel_msg.add(lblCodigoContrato, "cell 0 0");
		
		JLabel lblCliente = new JLabel("Francisco Guimaraes Pereire e Outros");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		painel_msg.add(lblCliente, "cell 1 0");
		
		JLabel lblPontos = new JLabel("5");
		lblPontos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		painel_msg.add(lblPontos, "cell 2 0");
		
		JLabel lblMotivo = new JLabel("Não entregara a quantidade de sacos combinada, porem tem producao");
		painel_msg.add(lblMotivo, "cell 3 0");
		
		JTextArea textAreaDescricao = new JTextArea();
		textAreaDescricao.setBackground(new Color(255, 255, 204));
		textAreaDescricao.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 11));
		textAreaDescricao.setText("Não entergará a quantiade de sacos combinada");
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
		painel_msg.add(textAreaDescricao, "cell 4 0,grow");
		
	
		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
}
