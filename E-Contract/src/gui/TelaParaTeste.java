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
import outros.TratarDados;

import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.border.LineBorder;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaParaTeste extends JDialog implements ComponentListener{

	private final JPanel painelPrincipal = new JPanel();


	private TelaParaTeste isto ;

	 GraphicsEnvironment ge = GraphicsEnvironment .getLocalGraphicsEnvironment();
	 GraphicsDevice[] gds = ge.getScreenDevices();
	 
	public TelaParaTeste(int index_tela_pai, JFrame janelaPai) {
        super(janelaPai, true); 

		//setModal(true);

		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		isto = this;
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 510, 505);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		String conteudo = "Revisão de Contrato";
		String data_hora = "";
	
JPanel painel_msg = new JPanel();
		
		//painel mensagem destinatario a remetente
		
		painel_msg.setBackground(Color.WHITE);
		painel_msg.setBounds(0, 0, 414, 80);
		painel_msg.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow]", "[grow]"));
		
		
		JLabel lblNewLabel = new JLabel("teste de espaçamento");
		lblNewLabel.setBackground(Color.BLUE);
		lblNewLabel.setVisible(false);
		painel_msg.add(lblNewLabel, "cell 0 0 4 1");
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		painel_msg.add(panel, "cell 4 0 9 1,grow");
		panel.setLayout(new MigLayout("", "[grow][][][][][][][]", "[grow][]"));
		
		JTextArea mostrar_conteudo = new JTextArea(conteudo);
		mostrar_conteudo.setBackground(new Color(0, 0, 0, 0));
		mostrar_conteudo.setBorder(null);
		mostrar_conteudo.setOpaque(false);
		mostrar_conteudo.setLineWrap(true);
		mostrar_conteudo.setWrapStyleWord(true);


		panel.add(mostrar_conteudo, "cell 0 0 8 1,grow");

		JLabel mostrar_data_hora = new JLabel(data_hora);
		mostrar_data_hora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(mostrar_data_hora, "cell 7 1");
		
		
		
			
		JButton btnNewButton = new JButton("Clique para acessar o contrato");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		painel_msg.add(btnNewButton, "cell 6 1 2 1");
		
		
		
		
	}
	
	
	
    public void componentMoved(ComponentEvent e) {
    	JDialog d = (JDialog) e.getComponent();
    }

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
