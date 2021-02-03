package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import outros.DadosGlobais;
import outros.JPanelBackground;

import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroPontuacao;
import cadastros.CadastroRomaneio;
import classesExtras.RenderizadorPontuacao;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoPontuacao;
import manipular.MonitorarRomaneios;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class TelaMonitoria extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
    private TelaMonitoria isto;
    private JDialog telaPai;
    private JTextField textField;
    private JTabbedPane painelAbas ;
    private JTable table_romaeios_entrada;
    private  ArrayList<CadastroRomaneio> romaneios_entrada = new ArrayList<>();
	private DefaultTableModel modelo_romaneios_entrada = new DefaultTableModel();


   

    
    
	public TelaMonitoria() {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Monitoria");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		painelAbas.setBackground(new Color(255, 255, 255));
		painelAbas.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelAbas = new JTabbedPane();
		
		setBounds(100, 100, d.width, d.height - taskBarHeight);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(null);
		
		
		painelAbas.addTab("Monitor Principal", painelPrincipal);
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setBackground(new Color(0, 0, 153));
		lblNewLabel_8.setBounds(706, 0, 7, 473);
		painelPrincipal.add(lblNewLabel_8);
		
		JLabel lblNewLabel_1 = new JLabel("28.500");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 120));
		lblNewLabel_1.setBounds(44, 141, 368, 139);
		painelPrincipal.add(lblNewLabel_1);
		
		JLabel lblNewLabel_28 = new JLabel("");
		lblNewLabel_28.setIcon(new ImageIcon(TelaMonitoria.class.getResource("/imagens/icone_caminhao_descarregando3.png")));
		lblNewLabel_28.setBounds(229, 6, 240, 107);
		painelPrincipal.add(lblNewLabel_28);
		
		JLabel lblNewLabel_4_1 = new JLabel("     Recebimento");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_4_1.setBounds(-27, 29, 283, 42);
		painelPrincipal.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel = new JLabel("sacos");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel.setBounds(336, 266, 62, 32);
		painelPrincipal.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("1.710.000,00 KG");
		lblNewLabel_2.setBounds(54, 278, 136, 16);
		painelPrincipal.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 255));
		panel.setBounds(27, 320, 400, 320);
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		
		modelo_romaneios_entrada.addColumn("Produtor");
		modelo_romaneios_entrada.addColumn("Safra");
		modelo_romaneios_entrada.addColumn("Bruto");
		modelo_romaneios_entrada.addColumn("Tara");
		modelo_romaneios_entrada.addColumn("Liquido");
		modelo_romaneios_entrada.addColumn("Umidade");

		
		
		
		table_romaeios_entrada = new JTable(modelo_romaneios_entrada);
		
		
		JScrollPane scrollPane = new JScrollPane(table_romaeios_entrada);
		scrollPane.setBounds(0, 0, 400, 320);
		panel.add(scrollPane);
		

		
		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(552, 485, 57, 28);
		painelPrincipal.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setFont(new Font("SansSerif", Font.PLAIN, 30));
		textField.setText("03/01/2021");
		textField.setBounds(610, 485, 209, 51);
		painelPrincipal.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_6_1 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6_1.setOpaque(true);
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_6_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6_1.setBackground(new Color(0, 206, 209));
		lblNewLabel_6_1.setBounds(594, 549, 212, 39);
		painelPrincipal.add(lblNewLabel_6_1);
		
		JComboBox cbCarregamentoPorSafra = new JComboBox();
		cbCarregamentoPorSafra.setFont(new Font("Tahoma", Font.BOLD, 30));
		cbCarregamentoPorSafra.setBounds(533, 593, 343, 47);
		painelPrincipal.add(cbCarregamentoPorSafra);
		
		JLabel lblNewLabel_4 = new JLabel("Umidade");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(449, 150, 77, 26);
		painelPrincipal.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("média:");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(493, 179, 59, 26);
		painelPrincipal.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("16.5%");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblNewLabel_6.setBounds(553, 141, 142, 64);
		painelPrincipal.add(lblNewLabel_6);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("  Carregamento");
		lblNewLabel_4_1_1.setOpaque(true);
		lblNewLabel_4_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_1_1.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_4_1_1.setBounds(706, 35, 283, 42);
		painelPrincipal.add(lblNewLabel_4_1_1);
		
		JLabel lblNewLabel_28_1 = new JLabel("");
		lblNewLabel_28_1.setIcon(new ImageIcon(TelaMonitoria.class.getResource("/imagens/icone_caminhao_carregado2.png")));
		lblNewLabel_28_1.setBounds(1031, 25, 142, 107);
		painelPrincipal.add(lblNewLabel_28_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("28.500");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 120));
		lblNewLabel_1_1.setBounds(959, 144, 368, 139);
		painelPrincipal.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_7 = new JLabel("sacos");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_7.setBounds(1250, 266, 62, 32);
		painelPrincipal.add(lblNewLabel_7);
		
		JLabel lblNewLabel_2_1 = new JLabel("1.710.000,00 KG");
		lblNewLabel_2_1.setBounds(969, 281, 136, 16);
		painelPrincipal.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_4_2 = new JLabel("Total");
		lblNewLabel_4_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_2.setBounds(449, 246, 77, 26);
		painelPrincipal.add(lblNewLabel_4_2);
		
		JLabel lblNewLabel_5_1 = new JLabel("de descargas:");
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_1.setBounds(458, 272, 126, 26);
		painelPrincipal.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_6_2 = new JLabel("12");
		lblNewLabel_6_2.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblNewLabel_6_2.setBounds(581, 240, 56, 64);
		painelPrincipal.add(lblNewLabel_6_2);
		
		JLabel lblNewLabel_4_2_1 = new JLabel("Total");
		lblNewLabel_4_2_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_2_1.setBounds(746, 246, 77, 26);
		painelPrincipal.add(lblNewLabel_4_2_1);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("de cargas:");
		lblNewLabel_5_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_1_1.setBounds(756, 270, 94, 26);
		painelPrincipal.add(lblNewLabel_5_1_1);
		
		JLabel lblNewLabel_6_2_1 = new JLabel("12");
		lblNewLabel_6_2_1.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblNewLabel_6_2_1.setBounds(848, 240, 56, 64);
		painelPrincipal.add(lblNewLabel_6_2_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		panel_1.setBounds(928, 320, 400, 320);
		painelPrincipal.add(panel_1);
	
		
		getContentPane().add(painelAbas, BorderLayout.CENTER);

		

		this.setLocationRelativeTo(null);

		
		
		
	}
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
	public void vigilante_todos_os_romaneios() {
		
		ArrayList<CadastroRomaneio> romaneios = new MonitorarRomaneios().vigiarTodosRomaneios();
		
		
		
	}
	
	
}
