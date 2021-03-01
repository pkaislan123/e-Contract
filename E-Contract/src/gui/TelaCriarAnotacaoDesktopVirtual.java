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
import java.awt.Window;
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
import javax.swing.JDesktopPane;
import java.awt.FlowLayout;
import javax.swing.JInternalFrame;

public class TelaCriarAnotacaoDesktopVirtual extends JFrame {

	private final JPanel painelPrincipal = new JPanel();


	private TelaCriarAnotacaoDesktopVirtual isto ;

	 GraphicsEnvironment ge = GraphicsEnvironment .getLocalGraphicsEnvironment();
	 GraphicsDevice[] gds = ge.getScreenDevices();
	 private JDialog telaPai;
	 
	public TelaCriarAnotacaoDesktopVirtual(int index_tela_pai, Window janela_anotacoes) {


		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		isto = this;
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JDesktopPane desktopPane_1 = new JDesktopPane();
		GridBagConstraints gbc_desktopPane_1 = new GridBagConstraints();
		gbc_desktopPane_1.gridheight = 9;
		gbc_desktopPane_1.gridwidth = 7;
		gbc_desktopPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_desktopPane_1.fill = GridBagConstraints.BOTH;
		gbc_desktopPane_1.gridx = 0;
		gbc_desktopPane_1.gridy = 0;
		getContentPane().add(desktopPane_1, gbc_desktopPane_1);
		
		JLabel lblNewLabel = new JLabel("||||");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.BLACK);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.gridheight = 9;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 7;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JDesktopPane desktopPane = new JDesktopPane();
		GridBagConstraints gbc_desktopPane = new GridBagConstraints();
		gbc_desktopPane.gridheight = 9;
		gbc_desktopPane.gridwidth = 7;
		gbc_desktopPane.fill = GridBagConstraints.BOTH;
		gbc_desktopPane.gridx = 8;
		gbc_desktopPane.gridy = 0;
		getContentPane().add(desktopPane, gbc_desktopPane);
	
		TelaCriarNota nota = new TelaCriarNota(1,null, janela_anotacoes);
		nota.setLocation(22, 31);
		desktopPane_1.add(nota);
		
		
		TelaHome home = new TelaHome();
		desktopPane.add(home);
	
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(janela_anotacoes);

	    this.setLocationRelativeTo(janela_anotacoes);
		this.setVisible(true);
		
	}
	
	
	
}
