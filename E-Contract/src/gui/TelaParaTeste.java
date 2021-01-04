package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
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
		
		String nameAba = "";
		
		JPanel panelGuiaMensagens = new JPanel();
		panelGuiaMensagens.setBounds(0, 0, 314, 338);
		painelPrincipal.add(panelGuiaMensagens);
		painelPrincipal.setName(nameAba);
		panelGuiaMensagens.setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		panelGuiaMensagens.add(scrollPane, "cell 0 0 2 1,grow");
		scrollPane.setLayout(null);
		
		entMensagem = new JTextField();
		panelGuiaMensagens.add(entMensagem, "cell 0 1,growx");
		entMensagem.setColumns(10);
		
		JButton btnEnviarMensagem = new JButton("Enviar");
		panelGuiaMensagens.add(btnEnviarMensagem, "cell 1 1");

		
		

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
	}
}
