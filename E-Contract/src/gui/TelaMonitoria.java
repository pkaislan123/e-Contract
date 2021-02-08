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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
import outros.GetData;
import outros.JPanelBackground;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

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
import cadastros.CadastroSafra;
import classesExtras.ComboBoxPersonalizado;
import classesExtras.ComboBoxRenderPersonalizado;
import classesExtras.RenderizadorPontuacao;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoPontuacao;
import conexaoBanco.GerenciarBancoSafras;
import manipular.MonitorarRomaneios;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import java.awt.Component;

public class TelaMonitoria extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelRecebimento;
	private final JPanel painelCarregamento;
	private TelaMonitoria isto;
	private JCheckBox chckbxHoje;
	private JDialog telaPai;
	private JTextField entDataInformada;
	private JTabbedPane painelAbas;
	private JTable table_romaeios_entrada;
	private JTable table_romaneios_saida;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private JComboBox cbCarregamentoPorSafra;
	private ArrayList<CadastroRomaneio> romaneios_entrada = new ArrayList<>();
	private DefaultTableModel modelo_romaneios_entrada = new DefaultTableModel();
	private CadastroSafra safra_selecionada;
	private ArrayList<CadastroRomaneio> romaneios_saida = new ArrayList<>();
	private DefaultTableModel modelo_romaneios_saida = new DefaultTableModel();
	private DefaultTableModel modelo_romaneios_entrada_aba_recebimento = new DefaultTableModel();
	private DefaultTableModel modelo_romaneios_saida_aba_carregamentos = new DefaultTableModel();

	private JLabel lblUmidadeMediaDescargaAbaRecebimento, lblNumTotalCargasAbaRecebimento,lblUmidadeMediaCargaAbaCarregamento,lblTotalSacosCargaAbaRecebimento,lblNumTotalKGCargaAbaRecebimento,lblImpurezaMediaAbaCarregamentos,lblAvariadosMediaAbaCarregamentos;
	private JLabel lblUmidadeMedia, lblKgDescarga, lblNumSacosDescarga, lblNumDescargas,lblUmidadeMediaAbaRecebimento,lblImpurezaMediaAbaRecebimento ,lblAvariadosMediaAbaRecebimento;
	private int num_descargas = 0;
	private double total_sacos_descarga;
	private double total_kg_descarga;
	private double umidade_media;
	private double avariados_media;
	private double impureza_media;
	private double avariados_media_carregamento;
	private double impureza_media_carregamento;
	private boolean todas_as_safras = true;
	private boolean todo_periodo = false;
	// variaveis para carregamento
	private JLabel lblUmidadeMediaCarga, lblNumTotalCargas, lblTotalSacosCarga, lblNumTotalKGCarga,lblNumDescargasAbaRecebimento, lblKgDescargaAbaRecebimento, lblNumSacosDescargaAbaRecebimento;

	private int num_cargas = 0;
	private double total_sacos_carga;
	private double total_kg_carga;
	private double umidade_media_saida;
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();

	private NumberFormat z = NumberFormat.getNumberInstance();
	private JTable table;
	private JTable table_1;

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}

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

		painelAbas = new JTabbedPane();
		painelAbas.setBackground(new Color(255, 255, 255));
		painelAbas.setBorder(new EmptyBorder(5, 5, 5, 5));

		setBounds(100, 100, d.width, d.height - taskBarHeight);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(null);

		painelAbas.addTab("Monitor Principal", painelPrincipal);
		
		painelRecebimento = new JPanel();
		painelRecebimento.setBackground(Color.WHITE);
		painelRecebimento.setLayout(null);
		painelAbas.addTab("Monitor Recebimento", painelRecebimento);
		
		JLabel lblNewLabel_28_2 = new JLabel("");
		lblNewLabel_28_2.setBounds(256, 6, 240, 107);
		painelRecebimento.add(lblNewLabel_28_2);
		
		JLabel lblNewLabel_4_1_2 = new JLabel("     Recebimento");
		lblNewLabel_4_1_2.setOpaque(true);
		lblNewLabel_4_1_2.setForeground(Color.WHITE);
		lblNewLabel_4_1_2.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1_2.setBackground(new Color(0, 51, 0));
		lblNewLabel_4_1_2.setBounds(0, 29, 283, 42);
		painelRecebimento.add(lblNewLabel_4_1_2);
		
		 lblNumSacosDescargaAbaRecebimento = new JLabel("00.000");
		lblNumSacosDescargaAbaRecebimento.setForeground(new Color(0, 102, 51));
		lblNumSacosDescargaAbaRecebimento.setFont(new Font("Arial", Font.BOLD, 120));
		lblNumSacosDescargaAbaRecebimento.setBounds(46, 125, 368, 139);
		painelRecebimento.add(lblNumSacosDescargaAbaRecebimento);
		
		JPanel panel_2_2 = new JPanel();
		panel_2_2.setLayout(null);
		panel_2_2.setBackground(new Color(0, 102, 51));
		panel_2_2.setBounds(494, 125, 259, 81);
		painelRecebimento.add(panel_2_2);
		
		JLabel lblNewLabel_4_4 = new JLabel("Umidade");
		lblNewLabel_4_4.setForeground(Color.WHITE);
		lblNewLabel_4_4.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_4.setBounds(6, 15, 77, 26);
		panel_2_2.add(lblNewLabel_4_4);
		
		JLabel lblNewLabel_5_3 = new JLabel("média:");
		lblNewLabel_5_3.setForeground(Color.WHITE);
		lblNewLabel_5_3.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_3.setBounds(50, 44, 59, 26);
		panel_2_2.add(lblNewLabel_5_3);
		
		 lblUmidadeMediaAbaRecebimento = new JLabel("00,0%");
		lblUmidadeMediaAbaRecebimento.setForeground(new Color(0, 0, 153));
		lblUmidadeMediaAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblUmidadeMediaAbaRecebimento.setBounds(110, 6, 142, 64);
		panel_2_2.add(lblUmidadeMediaAbaRecebimento);
		
		JPanel panel_3_2 = new JPanel();
		panel_3_2.setLayout(null);
		panel_3_2.setBackground(new Color(0, 0, 153));
		panel_3_2.setBounds(494, 221, 260, 81);
		painelRecebimento.add(panel_3_2);
		
		JLabel lblNewLabel_4_2_2 = new JLabel("Total");
		lblNewLabel_4_2_2.setForeground(Color.WHITE);
		lblNewLabel_4_2_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_2_2.setBounds(36, 12, 77, 26);
		panel_3_2.add(lblNewLabel_4_2_2);
		
		JLabel lblNewLabel_5_1_2 = new JLabel("de descargas:");
		lblNewLabel_5_1_2.setForeground(Color.WHITE);
		lblNewLabel_5_1_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_1_2.setBounds(45, 38, 126, 26);
		panel_3_2.add(lblNewLabel_5_1_2);
		
		 lblNumDescargasAbaRecebimento = new JLabel("0");
		lblNumDescargasAbaRecebimento.setForeground(new Color(153, 0, 51));
		lblNumDescargasAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblNumDescargasAbaRecebimento.setBounds(185, 6, 56, 64);
		panel_3_2.add(lblNumDescargasAbaRecebimento);
		
		JLabel lblNewLabel_1 = new JLabel("sacos");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(338, 250, 62, 32);
		painelRecebimento.add(lblNewLabel_1);
		
		 lblKgDescargaAbaRecebimento = new JLabel("0.000.000,00 KG");
		lblKgDescargaAbaRecebimento.setBounds(56, 262, 136, 16);
		painelRecebimento.add(lblKgDescargaAbaRecebimento);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(Color.CYAN);
		panel_4.setBounds(6, 320, 1328, 320);
		painelRecebimento.add(panel_4);
		
	
		modelo_romaneios_entrada_aba_recebimento.addColumn("Produtor");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Safra");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Bruto");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Total");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Liquido");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Umidade");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Impureza");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Avariados");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Data");

		
		JTable table_aba_recebimento = new JTable(modelo_romaneios_entrada_aba_recebimento);
		table_aba_recebimento.setFont(new Font("SansSerif", Font.PLAIN, 20));

		
		table_aba_recebimento.getColumnModel().getColumn(0)
        .setPreferredWidth(600);
		table_aba_recebimento.getColumnModel().getColumn(1)
        .setPreferredWidth(300);
		table_aba_recebimento.getColumnModel().getColumn(2)
        .setPreferredWidth(250);
        table_aba_recebimento.getColumnModel().getColumn(3)
        .setPreferredWidth(225);
        table_aba_recebimento.getColumnModel().getColumn(4)
        .setPreferredWidth(180);
        table_aba_recebimento.getColumnModel().getColumn(5)
        .setPreferredWidth(100);
        table_aba_recebimento.getColumnModel().getColumn(6)
        .setPreferredWidth(100);
        table_aba_recebimento.getColumnModel().getColumn(7)
        .setPreferredWidth(100);
        table_aba_recebimento.getColumnModel().getColumn(8)
        .setPreferredWidth(200);
        table_aba_recebimento.setRowHeight (30); 
        
		JScrollPane scrollPane_2 = new JScrollPane(table_aba_recebimento);
		scrollPane_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		scrollPane_2.setBounds(0, 0, 1328, 320);
		panel_4.add(scrollPane_2);
		
		JPanel panel_2_2_1 = new JPanel();
		panel_2_2_1.setLayout(null);
		panel_2_2_1.setBackground(new Color(0, 102, 51));
		panel_2_2_1.setBounds(765, 125, 259, 81);
		painelRecebimento.add(panel_2_2_1);
		
		JLabel lblNewLabel_4_4_1 = new JLabel("Impureza");
		lblNewLabel_4_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_4_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_4_1.setBounds(6, 15, 81, 26);
		panel_2_2_1.add(lblNewLabel_4_4_1);
		
		JLabel lblNewLabel_5_3_1 = new JLabel("média:");
		lblNewLabel_5_3_1.setForeground(Color.WHITE);
		lblNewLabel_5_3_1.setBounds(50, 44, 59, 26);
		panel_2_2_1.add(lblNewLabel_5_3_1);
		
		 lblImpurezaMediaAbaRecebimento = new JLabel("00,0%");
		lblImpurezaMediaAbaRecebimento.setForeground(new Color(0, 0, 153));
		lblImpurezaMediaAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblImpurezaMediaAbaRecebimento.setBounds(110, 6, 142, 64);
		panel_2_2_1.add(lblImpurezaMediaAbaRecebimento);
		
		JPanel panel_2_2_1_1 = new JPanel();
		panel_2_2_1_1.setLayout(null);
		panel_2_2_1_1.setBackground(new Color(0, 102, 51));
		panel_2_2_1_1.setBounds(766, 218, 259, 81);
		painelRecebimento.add(panel_2_2_1_1);
		
		JLabel lblNewLabel_4_4_1_1 = new JLabel("Avariados");
		lblNewLabel_4_4_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_4_1_1.setBounds(6, 15, 87, 26);
		panel_2_2_1_1.add(lblNewLabel_4_4_1_1);
		
		JLabel lblNewLabel_5_3_1_1 = new JLabel("média:");
		lblNewLabel_5_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_3_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_3_1_1.setBounds(50, 44, 59, 26);
		panel_2_2_1_1.add(lblNewLabel_5_3_1_1);
		
		 lblAvariadosMediaAbaRecebimento = new JLabel("00,0%");
		lblAvariadosMediaAbaRecebimento.setForeground(new Color(0, 0, 153));
		lblAvariadosMediaAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblAvariadosMediaAbaRecebimento.setBounds(110, 6, 142, 64);
		panel_2_2_1_1.add(lblAvariadosMediaAbaRecebimento);
		
		painelCarregamento = new JPanel();
		painelCarregamento.setBackground(Color.WHITE);
		painelCarregamento.setLayout(null);
		painelAbas.addTab("Monitor Carregamento", painelCarregamento);
		
		JPanel panel_2_1_1 = new JPanel();
		panel_2_1_1.setLayout(null);
		panel_2_1_1.setBackground(new Color(0, 0, 153));
		panel_2_1_1.setBounds(19, 110, 259, 81);
		painelCarregamento.add(panel_2_1_1);
		
		JLabel lblNewLabel_4_3_1 = new JLabel("Umidade");
		lblNewLabel_4_3_1.setForeground(Color.WHITE);
		lblNewLabel_4_3_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_3_1.setBounds(6, 15, 77, 26);
		panel_2_1_1.add(lblNewLabel_4_3_1);
		
		JLabel lblNewLabel_5_2_1 = new JLabel("média:");
		lblNewLabel_5_2_1.setForeground(Color.WHITE);
		lblNewLabel_5_2_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_2_1.setBounds(50, 44, 59, 26);
		panel_2_1_1.add(lblNewLabel_5_2_1);
		
		 lblUmidadeMediaCargaAbaCarregamento = new JLabel("00,0%");
		lblUmidadeMediaCargaAbaCarregamento.setForeground(Color.RED);
		lblUmidadeMediaCargaAbaCarregamento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblUmidadeMediaCargaAbaCarregamento.setBackground(Color.WHITE);
		lblUmidadeMediaCargaAbaCarregamento.setBounds(110, 6, 142, 64);
		panel_2_1_1.add(lblUmidadeMediaCargaAbaCarregamento);
		
		JLabel lblNewLabel_4_1_1_1 = new JLabel("  Carregamento");
		lblNewLabel_4_1_1_1.setOpaque(true);
		lblNewLabel_4_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_1_1_1.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1_1_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_4_1_1_1.setBounds(0, 32, 283, 42);
		painelCarregamento.add(lblNewLabel_4_1_1_1);
		
		JLabel lblNewLabel_28_1_1 = new JLabel("");
		lblNewLabel_28_1_1.setBounds(331, 28, 142, 107);
		painelCarregamento.add(lblNewLabel_28_1_1);
		
		JPanel panel_3_1_1 = new JPanel();
		panel_3_1_1.setLayout(null);
		panel_3_1_1.setBackground(new Color(0, 102, 51));
		panel_3_1_1.setBounds(18, 206, 260, 81);
		painelCarregamento.add(panel_3_1_1);
		
		JLabel lblNewLabel_4_2_1_1 = new JLabel("Total");
		lblNewLabel_4_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_2_1_1.setBounds(33, 12, 77, 26);
		panel_3_1_1.add(lblNewLabel_4_2_1_1);
		
		JLabel lblNewLabel_5_1_1_1 = new JLabel("de cargas:");
		lblNewLabel_5_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_1_1_1.setBounds(42, 38, 126, 26);
		panel_3_1_1.add(lblNewLabel_5_1_1_1);
		
		 lblNumTotalCargasAbaRecebimento = new JLabel("0");
		lblNumTotalCargasAbaRecebimento.setForeground(new Color(0, 0, 102));
		lblNumTotalCargasAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblNumTotalCargasAbaRecebimento.setBounds(165, 6, 56, 64);
		panel_3_1_1.add(lblNumTotalCargasAbaRecebimento);
		
		 lblTotalSacosCargaAbaRecebimento = new JLabel("00.000");
		lblTotalSacosCargaAbaRecebimento.setForeground(new Color(255, 102, 51));
		lblTotalSacosCargaAbaRecebimento.setFont(new Font("Arial", Font.BOLD, 120));
		lblTotalSacosCargaAbaRecebimento.setBounds(808, 110, 368, 139);
		painelCarregamento.add(lblTotalSacosCargaAbaRecebimento);
		
		 lblNumTotalKGCargaAbaRecebimento = new JLabel("0.000.000,00 KG");
		lblNumTotalKGCargaAbaRecebimento.setBounds(818, 247, 136, 16);
		painelCarregamento.add(lblNumTotalKGCargaAbaRecebimento);
		
		JLabel lblNewLabel_7_1 = new JLabel("sacos");
		lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_7_1.setBounds(1099, 232, 62, 32);
		painelCarregamento.add(lblNewLabel_7_1);
		
		modelo_romaneios_saida_aba_carregamentos.addColumn("Destinatario");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Safra");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Bruto");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Total");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Liquido");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Umidade");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Impureza");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Avariados");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Data");

		
		JTable table_carga_aba_carregamentos = new JTable(modelo_romaneios_saida_aba_carregamentos);
		table_carga_aba_carregamentos.setFont(new Font("SansSerif", Font.PLAIN, 20));


		table_carga_aba_carregamentos.getColumnModel().getColumn(0)
        .setPreferredWidth(600);
		table_carga_aba_carregamentos.getColumnModel().getColumn(1)
        .setPreferredWidth(250);
		table_carga_aba_carregamentos.getColumnModel().getColumn(2)
        .setPreferredWidth(250);
		table_carga_aba_carregamentos.getColumnModel().getColumn(3)
        .setPreferredWidth(225);
		table_carga_aba_carregamentos.getColumnModel().getColumn(4)
        .setPreferredWidth(220);
		table_carga_aba_carregamentos.getColumnModel().getColumn(5)
        .setPreferredWidth(100);
		table_carga_aba_carregamentos.getColumnModel().getColumn(6)
        .setPreferredWidth(100);
		table_carga_aba_carregamentos.getColumnModel().getColumn(7)
        .setPreferredWidth(100);
		table_carga_aba_carregamentos.getColumnModel().getColumn(8)
        .setPreferredWidth(200);
		table_carga_aba_carregamentos.setRowHeight (30); 
        
		
	
		

		JScrollPane scrollPane_1_1 = new JScrollPane(table_carga_aba_carregamentos);
		scrollPane_1_1.setBounds(18, 323, 1316, 320);
		painelCarregamento.add(scrollPane_1_1);
		
		JPanel panel_2_2_1_2 = new JPanel();
		panel_2_2_1_2.setLayout(null);
		panel_2_2_1_2.setBackground(new Color(0, 102, 51));
		panel_2_2_1_2.setBounds(290, 110, 259, 81);
		painelCarregamento.add(panel_2_2_1_2);
		
		JLabel lblNewLabel_4_4_1_2 = new JLabel("Impureza");
		lblNewLabel_4_4_1_2.setForeground(Color.WHITE);
		lblNewLabel_4_4_1_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_4_1_2.setBounds(6, 15, 81, 26);
		panel_2_2_1_2.add(lblNewLabel_4_4_1_2);
		
		JLabel lblNewLabel_5_3_1_2 = new JLabel("média:");
		lblNewLabel_5_3_1_2.setForeground(Color.WHITE);
		lblNewLabel_5_3_1_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_3_1_2.setBounds(50, 44, 59, 26);
		panel_2_2_1_2.add(lblNewLabel_5_3_1_2);
		
		 lblImpurezaMediaAbaCarregamentos = new JLabel("00,0%");
		lblImpurezaMediaAbaCarregamentos.setForeground(new Color(0, 0, 153));
		lblImpurezaMediaAbaCarregamentos.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblImpurezaMediaAbaCarregamentos.setBounds(110, 6, 142, 64);
		panel_2_2_1_2.add(lblImpurezaMediaAbaCarregamentos);
		
		JPanel panel_2_2_1_1_1 = new JPanel();
		panel_2_2_1_1_1.setLayout(null);
		panel_2_2_1_1_1.setBackground(new Color(0, 102, 51));
		panel_2_2_1_1_1.setBounds(290, 206, 259, 81);
		painelCarregamento.add(panel_2_2_1_1_1);
		
		JLabel lblNewLabel_4_4_1_1_1 = new JLabel("Avariados");
		lblNewLabel_4_4_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_4_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_4_1_1_1.setBounds(6, 15, 87, 26);
		panel_2_2_1_1_1.add(lblNewLabel_4_4_1_1_1);
		
		JLabel lblNewLabel_5_3_1_1_1 = new JLabel("média:");
		lblNewLabel_5_3_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_3_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_3_1_1_1.setBounds(50, 44, 59, 26);
		panel_2_2_1_1_1.add(lblNewLabel_5_3_1_1_1);
		
		 lblAvariadosMediaAbaCarregamentos = new JLabel("00,0%");
		lblAvariadosMediaAbaCarregamentos.setForeground(new Color(0, 0, 153));
		lblAvariadosMediaAbaCarregamentos.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblAvariadosMediaAbaCarregamentos.setBounds(110, 6, 142, 64);
		panel_2_2_1_1_1.add(lblAvariadosMediaAbaCarregamentos);

		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setBackground(new Color(0, 0, 153));
		lblNewLabel_8.setBounds(670, 0, 8, 407);
		painelPrincipal.add(lblNewLabel_8);

		lblNumSacosDescarga = new JLabel("00.000");
		lblNumSacosDescarga.setForeground(new Color(0, 102, 51));
		lblNumSacosDescarga.setFont(new Font("Arial", Font.BOLD, 120));
		lblNumSacosDescarga.setBounds(19, 125, 368, 139);
		painelPrincipal.add(lblNumSacosDescarga);

		JLabel lblNewLabel_28 = new JLabel("");
		lblNewLabel_28
				.setIcon(new ImageIcon(TelaMonitoria.class.getResource("/imagens/icone_caminhao_descarregando4.png")));
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
		lblNewLabel.setBounds(311, 250, 62, 32);
		painelPrincipal.add(lblNewLabel);

		lblKgDescarga = new JLabel("0.000.000,00 KG");
		lblKgDescarga.setBounds(29, 262, 136, 16);
		painelPrincipal.add(lblKgDescarga);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 255));
		panel.setBounds(6, 320, 515, 320);
		painelPrincipal.add(panel);
		panel.setLayout(null);

		modelo_romaneios_entrada.addColumn("Produtor");
		modelo_romaneios_entrada.addColumn("Safra");
		modelo_romaneios_entrada.addColumn("Liquido");
		
		table_romaeios_entrada = new JTable(modelo_romaneios_entrada);
		table_romaeios_entrada.setFont(new Font("Arial", Font.PLAIN, 18));

		table_romaeios_entrada.getColumnModel().getColumn(0)
        .setPreferredWidth(250);
		table_romaeios_entrada.getColumnModel().getColumn(1)
        .setPreferredWidth(160);
		table_romaeios_entrada.getColumnModel().getColumn(2)
        .setPreferredWidth(250);
		
		JScrollPane scrollPane = new JScrollPane(table_romaeios_entrada);
		scrollPane.setBounds(0, 0, 515, 320);
		panel.add(scrollPane);

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(549, 500, 57, 28);
		painelPrincipal.add(lblNewLabel_3);

		entDataInformada = new JTextField();
		entDataInformada.setEnabled(false);
		entDataInformada.setFont(new Font("SansSerif", Font.PLAIN, 30));
		entDataInformada.setText("03/01/2021");
		entDataInformada.setBounds(610, 485, 209, 51);
		painelPrincipal.add(entDataInformada);
		entDataInformada.setColumns(10);

		JLabel lblNewLabel_6_1 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				todas_as_safras = true;
				vigilante_todos_os_romaneios();

			}
		});
		lblNewLabel_6_1.setOpaque(true);
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_6_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6_1.setBackground(new Color(0, 206, 209));
		lblNewLabel_6_1.setBounds(594, 549, 212, 39);
		painelPrincipal.add(lblNewLabel_6_1);

		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();

		cbCarregamentoPorSafra = new JComboBox();
		cbCarregamentoPorSafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					safra_selecionada = (CadastroSafra) modelSafra.getSelectedItem();

					// procura no banco os contratos de acordo com a safra selecionada

					// atualizar o grafico
					todas_as_safras = false;
					vigilante_todos_os_romaneios();
				} catch (Exception t) {

				}
			}
		});
		cbCarregamentoPorSafra.setFont(new Font("Tahoma", Font.BOLD, 30));
		cbCarregamentoPorSafra.setBounds(533, 593, 293, 47);

		cbCarregamentoPorSafra.setModel(modelSafra);
		cbCarregamentoPorSafra.setRenderer(cBSafraPersonalizado);
		painelPrincipal.add(cbCarregamentoPorSafra);

		JLabel lblNewLabel_4_1_1 = new JLabel("  Carregamento");
		lblNewLabel_4_1_1.setOpaque(true);
		lblNewLabel_4_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_1_1.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_4_1_1.setBounds(670, 29, 283, 42);
		painelPrincipal.add(lblNewLabel_4_1_1);

		JLabel lblNewLabel_28_1 = new JLabel("");
		lblNewLabel_28_1
				.setIcon(new ImageIcon(TelaMonitoria.class.getResource("/imagens/icone_caminhao_carregado2.png")));
		lblNewLabel_28_1.setBounds(1001, 25, 142, 107);
		painelPrincipal.add(lblNewLabel_28_1);

		lblTotalSacosCarga = new JLabel("00.000");
		lblTotalSacosCarga.setForeground(new Color(255, 102, 51));
		lblTotalSacosCarga.setFont(new Font("Arial", Font.BOLD, 120));
		lblTotalSacosCarga.setBounds(959, 144, 368, 139);
		painelPrincipal.add(lblTotalSacosCarga);

		JLabel lblNewLabel_7 = new JLabel("sacos");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 24));
		lblNewLabel_7.setBounds(1250, 266, 62, 32);
		painelPrincipal.add(lblNewLabel_7);

		lblNumTotalKGCarga = new JLabel("0.000.000,00 KG");
		lblNumTotalKGCarga.setBounds(969, 281, 136, 16);
		painelPrincipal.add(lblNumTotalKGCarga);

		modelo_romaneios_saida.addColumn("Comprador");
		modelo_romaneios_saida.addColumn("Safra");
		modelo_romaneios_saida.addColumn("Liquido");
		
	

		table_romaneios_saida = new JTable(modelo_romaneios_saida);
		table_romaneios_saida.setFont(new Font("Arial", Font.PLAIN, 18));

		  
			table_romaneios_saida.getColumnModel().getColumn(0)
	        .setPreferredWidth(250);
			table_romaneios_saida.getColumnModel().getColumn(1)
	        .setPreferredWidth(200);
			table_romaneios_saida.getColumnModel().getColumn(2)
	        .setPreferredWidth(150);
			
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		panel_1.setBounds(831, 320, 497, 320);
		painelPrincipal.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane(table_romaneios_saida);
		scrollPane_1.setBounds(0, 0, 497, 320);
		panel_1.add(scrollPane_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 102, 51));
		panel_2.setBounds(399, 144, 259, 81);
		painelPrincipal.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Umidade");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(6, 15, 77, 26);
		panel_2.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel lblNewLabel_5 = new JLabel("média:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(50, 44, 59, 26);
		panel_2.add(lblNewLabel_5);
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 20));

		lblUmidadeMedia = new JLabel("00,0%");
		lblUmidadeMedia.setForeground(new Color(0, 0, 153));
		lblUmidadeMedia.setBounds(110, 6, 142, 64);
		panel_2.add(lblUmidadeMedia);
		lblUmidadeMedia.setFont(new Font("SansSerif", Font.PLAIN, 50));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 153));
		panel_3.setBounds(399, 240, 260, 68);
		painelPrincipal.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_4_2 = new JLabel("Total");
		lblNewLabel_4_2.setForeground(Color.WHITE);
		lblNewLabel_4_2.setBounds(38, 6, 77, 26);
		panel_3.add(lblNewLabel_4_2);
		lblNewLabel_4_2.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel lblNewLabel_5_1 = new JLabel("de descargas:");
		lblNewLabel_5_1.setForeground(Color.WHITE);
		lblNewLabel_5_1.setBounds(47, 32, 126, 26);
		panel_3.add(lblNewLabel_5_1);
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.PLAIN, 20));

		lblNumDescargas = new JLabel("0");
		lblNumDescargas.setForeground(new Color(153, 0, 51));
		lblNumDescargas.setBounds(187, 0, 56, 64);
		panel_3.add(lblNumDescargas);
		lblNumDescargas.setFont(new Font("SansSerif", Font.PLAIN, 50));

		JPanel panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
		panel_3_1.setBackground(new Color(0, 102, 51));
		panel_3_1.setBounds(687, 240, 260, 68);
		painelPrincipal.add(panel_3_1);

		JLabel lblNewLabel_4_2_1 = new JLabel("Total");
		lblNewLabel_4_2_1.setForeground(Color.WHITE);
		lblNewLabel_4_2_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_2_1.setBounds(38, 6, 77, 26);
		panel_3_1.add(lblNewLabel_4_2_1);

		JLabel lblNewLabel_5_1_1 = new JLabel("de cargas:");
		lblNewLabel_5_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_1_1.setBounds(47, 32, 126, 26);
		panel_3_1.add(lblNewLabel_5_1_1);

		lblNumTotalCargas = new JLabel("0");
		lblNumTotalCargas.setForeground(new Color(0, 0, 102));
		lblNumTotalCargas.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblNumTotalCargas.setBounds(170, 0, 56, 64);
		panel_3_1.add(lblNumTotalCargas);

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(new Color(0, 0, 153));
		panel_2_1.setBounds(688, 144, 259, 81);
		painelPrincipal.add(panel_2_1);

		JLabel lblNewLabel_4_3 = new JLabel("Umidade");
		lblNewLabel_4_3.setForeground(Color.WHITE);
		lblNewLabel_4_3.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_4_3.setBounds(6, 15, 77, 26);
		panel_2_1.add(lblNewLabel_4_3);

		JLabel lblNewLabel_5_2 = new JLabel("média:");
		lblNewLabel_5_2.setForeground(Color.WHITE);
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel_5_2.setBounds(50, 44, 59, 26);
		panel_2_1.add(lblNewLabel_5_2);

		lblUmidadeMediaCarga = new JLabel("00,0%");
		lblUmidadeMediaCarga.setBackground(Color.WHITE);
		lblUmidadeMediaCarga.setForeground(Color.RED);
		lblUmidadeMediaCarga.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblUmidadeMediaCarga.setBounds(110, 6, 142, 64);
		panel_2_1.add(lblUmidadeMediaCarga);

		JCheckBox cBTodoPeriodo = new JCheckBox("Todo o Periodo");
		cBTodoPeriodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cBTodoPeriodo.isSelected()) {
					cBTodoPeriodo.setSelected(true);
					entDataInformada.setEditable(false);
					entDataInformada.setEnabled(false);
					todo_periodo = true;
					
					 chckbxHoje.setSelected(false);
					 entDataInformada.setEditable(false);
					 entDataInformada.setEnabled(false);

				} else {
					cBTodoPeriodo.setSelected(false);
					entDataInformada.setEditable(true);
					entDataInformada.setEnabled(true);
					todo_periodo = false;
					
					 chckbxHoje.setSelected(true);
					 entDataInformada.setEditable(true);
					 entDataInformada.setEnabled(true);
				}
			}
		});
		cBTodoPeriodo.setFont(new Font("SansSerif", Font.PLAIN, 20));
		cBTodoPeriodo.setBounds(628, 419, 158, 26);
		painelPrincipal.add(cBTodoPeriodo);
		
		 chckbxHoje = new JCheckBox("Hoje");
		chckbxHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chckbxHoje.isSelected()) {
					
					chckbxHoje.setSelected(true);
					cBTodoPeriodo.setSelected(false);
					todo_periodo = false;
					
				
					Date date_hoje = new Date();

					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				        String dataFormatada = dateFormat.format(date_hoje);

					entDataInformada.setText(dataFormatada);
					entDataInformada.setEditable(true);
					entDataInformada.setEnabled(true);

			
			
				}else {
					
					chckbxHoje.setSelected(false);
					cBTodoPeriodo.setSelected(true);
					todo_periodo = true;
					
				
					Date date_hoje = new Date();

					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				        String dataFormatada = dateFormat.format(date_hoje);

					entDataInformada.setEditable(false);
					entDataInformada.setEnabled(true);
					
				}
				
				
			}
		});
		chckbxHoje.setSelected(true);
		chckbxHoje.setFont(new Font("SansSerif", Font.PLAIN, 20));
		chckbxHoje.setBounds(628, 447, 158, 26);
		painelPrincipal.add(chckbxHoje);

		getContentPane().add(painelAbas, BorderLayout.CENTER);
		pesquisarSafras();

		for (CadastroSafra safra : safras) {

			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);

		}
		todo_periodo = false;
		
		
		Date date_hoje = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        String dataFormatada = dateFormat.format(date_hoje);

		entDataInformada.setText(dataFormatada);
		entDataInformada.setEditable(true);
		entDataInformada.setEnabled(true);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(TelaMonitoria.class.getResource("/imagens/icone_sacaria.png")));
		lblNewLabel_2.setBounds(280, 250, 32, 32);
		painelPrincipal.add(lblNewLabel_2);
		
		this.setLocationRelativeTo(null);

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

	public void vigilante_todos_os_romaneios() {

		new Thread() {

			@Override
			public void run() {
				modelo_romaneios_entrada.setNumRows(0);
				modelo_romaneios_entrada_aba_recebimento.setNumRows(0);

				umidade_media = 0;
				impureza_media = 0;
				avariados_media = 0;
				num_descargas = 0;
				total_sacos_descarga = 0;
				total_kg_descarga = 0;

				modelo_romaneios_saida.setNumRows(0);

				umidade_media_saida = 0;
				num_cargas = 0;
				total_sacos_carga = 0;
				total_kg_carga = 0;

				ArrayList<CadastroRomaneio> romaneios = new MonitorarRomaneios().vigiarTodosRomaneios();
				/*
				 * 
				 * modelo_romaneios_entrada.addColumn("Produtor");
				 * modelo_romaneios_entrada.addColumn("Safra");
				 * 
				 * modelo_romaneios_entrada.addColumn("Liquido");
				 * modelo_romaneios_entrada.addColumn("Umidade");
				 * 
				 * 
				 */

				for (CadastroRomaneio rom : romaneios) {


					String nome_remetente, nome_destinatario, safra;
					double bruto, tara, liquido, umidade;

					CadastroCliente remetente = rom.getRemetente();
					if (remetente.getTipo_pessoa() == 0) {
						nome_remetente = remetente.getNome_empresarial().toUpperCase();
					} else {
						nome_remetente = remetente.getNome_fantaia().toUpperCase();

					}
					
					String nome_remetente_completo = nome_remetente;

					
					String nome_remetente_quebrado[] = nome_remetente.split(" ");
					//rodrigo cesar de moura
					//[0] rodrigo [1] cesar [2] de  [3] moura
					if(nome_remetente_quebrado.length > 1) {
					    if(nome_remetente_quebrado[2].length() > 2) {
					    	nome_remetente = nome_remetente_quebrado[0] + " "+ nome_remetente_quebrado[2];
					    }else {
					    	if(nome_remetente_quebrado[3].length() > 1) {
						    	nome_remetente = nome_remetente_quebrado[0]+ " " + nome_remetente_quebrado[3];

					    	}else {
						    	nome_remetente = nome_remetente_quebrado[0] + " "+ nome_remetente_quebrado[1];

					    	}
					    }
					}
					
					CadastroCliente destinatario = rom.getDestinatario();
					if (destinatario.getTipo_pessoa() == 0) {
						nome_destinatario = destinatario.getNome_empresarial().toUpperCase();
					} else {
						nome_destinatario = destinatario.getNome_fantaia().toUpperCase();

					}
					String nome_destinatario_completo = nome_destinatario;

                     	String nome_destinatario_quebrado[] = nome_remetente.split(" ");
					
					if(nome_destinatario_quebrado.length > 2) {
					    if(nome_destinatario_quebrado[2].length() > 1) {
					    	nome_destinatario = nome_destinatario_quebrado[0] + " " + nome_destinatario_quebrado[2];
					    }else {
					    	if(nome_destinatario_quebrado[3].length() > 1) {
					    		nome_destinatario = nome_destinatario_quebrado[0] + " "+ nome_destinatario_quebrado[3];

					    	}else {
					    		nome_destinatario = nome_destinatario_quebrado[0] + " "+ nome_destinatario_quebrado[1];

					    	}
					    }
					}
                     String ano_plantio = Integer.toString( rom.getSafra().getAno_plantio());
                     ano_plantio = ano_plantio.substring(2, 4);
                     
                     String ano_colheita = Integer.toString( rom.getSafra().getAno_colheita());
                     ano_colheita = ano_colheita.substring(2, 4);
                     
					safra = rom.getProduto().getNome_produto().toUpperCase() + " - " + ano_plantio + "/"
							+ ano_colheita;

					double d_peso_liquido = rom.getPeso_liquido();
					String data_informada = entDataInformada.getText();
					Date date = null;
					try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(data_informada);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				       
					
					
					if(!todo_periodo) {
					if (rom.getData().equals(date)) {
						if (rom.getOperacao().equals("Prestação de Serviços")) {

							if (todas_as_safras) {
								modelo_romaneios_entrada.addRow(
										new Object[] { nome_remetente, safra, d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs"});
								modelo_romaneios_entrada_aba_recebimento.addRow(
										new Object[] { nome_remetente_completo, safra, rom.getPeso_bruto() + " KG" , rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza()+ "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
								num_descargas++;

								umidade_media = umidade_media + rom.getUmidade();
								avariados_media = avariados_media + rom.getAvariados();
								impureza_media = impureza_media + rom.getInpureza();
								total_sacos_descarga = total_sacos_descarga + (d_peso_liquido / 60);
								total_kg_descarga = total_kg_descarga + d_peso_liquido;
							} else {

								if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {

									modelo_romaneios_entrada.addRow(
											new Object[] { nome_remetente, safra, d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs"});
									modelo_romaneios_entrada_aba_recebimento.addRow(
											new Object[] { nome_remetente_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
									
									num_descargas++;

									umidade_media = umidade_media + rom.getUmidade();
									avariados_media = avariados_media + rom.getAvariados();
									impureza_media = impureza_media + rom.getInpureza();
									total_sacos_descarga = total_sacos_descarga + (d_peso_liquido / 60);
									total_kg_descarga = total_kg_descarga + d_peso_liquido;
								}

							}
						} else if (rom.getOperacao().equalsIgnoreCase("Saída")) {
							if (todas_as_safras) {
								modelo_romaneios_saida.addRow(
										new Object[] { nome_destinatario, safra, d_peso_liquido});
								num_cargas++;

								modelo_romaneios_saida_aba_carregamentos.addRow(
										new Object[] { nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
								
								
								umidade_media_saida = umidade_media_saida + rom.getUmidade();
								avariados_media_carregamento = avariados_media_carregamento + rom.getAvariados();
								impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();
								
								

								
								total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
								total_kg_carga = total_kg_carga + d_peso_liquido;
							} else {
								if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {
									modelo_romaneios_saida.addRow(new Object[] { nome_destinatario, safra,
											d_peso_liquido });
									num_cargas++;
									modelo_romaneios_saida_aba_carregamentos.addRow(
											new Object[] { nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
									
									umidade_media_saida = umidade_media_saida + rom.getUmidade();


									total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
									total_kg_carga = total_kg_carga + d_peso_liquido;
									avariados_media_carregamento = avariados_media_carregamento + rom.getAvariados();
									impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();
								}

							}
						}
					} // fim do if de data
					}else {
						if (rom.getOperacao().equals("Prestação de Serviços")) {

							
							if (todas_as_safras) {
								modelo_romaneios_entrada.addRow(
										new Object[] { nome_remetente, safra, d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs"});
								modelo_romaneios_entrada_aba_recebimento.addRow(
										new Object[] { nome_remetente_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
								
								num_descargas++;

								umidade_media = umidade_media + rom.getUmidade();
								avariados_media = avariados_media + rom.getAvariados();
								impureza_media = impureza_media + rom.getInpureza();
								total_sacos_descarga = total_sacos_descarga + (d_peso_liquido / 60);
								total_kg_descarga = total_kg_descarga + d_peso_liquido;
							} else {

								if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {

									modelo_romaneios_entrada.addRow(
											new Object[] { nome_remetente, safra, d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs"});
									modelo_romaneios_entrada_aba_recebimento.addRow(
											new Object[] { nome_remetente_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
									
									num_descargas++;

									umidade_media = umidade_media + rom.getUmidade();
									avariados_media = avariados_media + rom.getAvariados();
									impureza_media = impureza_media + rom.getInpureza();
									total_sacos_descarga = total_sacos_descarga + (d_peso_liquido / 60);
									total_kg_descarga = total_kg_descarga + d_peso_liquido;
								}

							}
						} else if (rom.getOperacao().equalsIgnoreCase("Saída")) {
							if (todas_as_safras) {
								modelo_romaneios_saida.addRow(
										new Object[] { nome_destinatario, safra, d_peso_liquido });
								modelo_romaneios_saida_aba_carregamentos.addRow(
										new Object[] { nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
								
								num_cargas++;

								umidade_media_saida = umidade_media_saida + rom.getUmidade();
								total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
								total_kg_carga = total_kg_carga + d_peso_liquido;
								avariados_media_carregamento = avariados_media_carregamento + rom.getAvariados();
								impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();
							} else {
								if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {
									modelo_romaneios_saida.addRow(new Object[] { nome_destinatario, safra,
											d_peso_liquido});
									modelo_romaneios_saida_aba_carregamentos.addRow(
											new Object[] { nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG", rom.getTara()+ " KG", d_peso_liquido+ " KG", rom.getUmidade() + "%", rom.getInpureza() + "%", rom.getAvariados() + "%", dateFormat.format(rom.getData())});
									
									num_cargas++;

									umidade_media_saida = umidade_media_saida + rom.getUmidade();
									total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
									total_kg_carga = total_kg_carga + d_peso_liquido;
									avariados_media_carregamento = avariados_media_carregamento + rom.getAvariados();
									impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();
								}

							}
						}
						
						
					}

				}

				umidade_media = umidade_media / num_descargas;
				umidade_media_saida = umidade_media_saida / num_cargas;
				
                impureza_media = impureza_media / num_descargas;
                avariados_media = avariados_media / num_descargas;
                
                avariados_media_carregamento = avariados_media_carregamento / num_cargas;
				impureza_media_carregamento = impureza_media_carregamento / num_cargas;
				
				setRomaneiosRecebimento();
				setRomaneiosCarregamento();

			}

		}.start();

	}

	
	

	
	
	public void setRomaneiosRecebimento() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				DecimalFormat fmt = new DecimalFormat("0.0");

				String string = fmt.format(umidade_media);
				lblUmidadeMedia.setText(string + "%");
				lblUmidadeMediaAbaRecebimento.setText(string + "%");
				
				 string = fmt.format(impureza_media);
				lblImpurezaMediaAbaRecebimento.setText(string + "%");
				
				 string = fmt.format(avariados_media);
				lblAvariadosMediaAbaRecebimento.setText(string + "%");

				String string2 = z.format((int) total_sacos_descarga);
				lblNumSacosDescarga.setText(string2);
				lblNumSacosDescargaAbaRecebimento.setText(string2);

				lblNumDescargas.setText(Integer.toString(num_descargas));
				lblNumDescargasAbaRecebimento.setText(Integer.toString(num_descargas));

				lblKgDescarga.setText(z.format(total_kg_descarga) + " KG");
				lblKgDescargaAbaRecebimento.setText(z.format(total_kg_descarga) + " KG");

			}
		});
	}

	public void setRomaneiosCarregamento() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				DecimalFormat fmt = new DecimalFormat("0.0");

				String string = fmt.format(umidade_media_saida);
				lblUmidadeMediaCarga.setText(string + "%");

				String string2 = z.format((int) total_sacos_carga);
				lblTotalSacosCarga.setText(string2);
				lblNumTotalCargas.setText(Integer.toString(num_cargas));

				lblNumTotalKGCarga.setText(z.format(total_kg_carga) + " KG");

				
				//aba carregamento
				lblUmidadeMediaCargaAbaCarregamento.setText(string + "%");
				lblNumTotalCargasAbaRecebimento.setText(Integer.toString(num_cargas));
				
				lblTotalSacosCargaAbaRecebimento.setText(string2);
				lblNumTotalKGCargaAbaRecebimento.setText(z.format(total_kg_carga) + " KG");
				
				 string = fmt.format(impureza_media_carregamento);
				lblImpurezaMediaAbaCarregamentos.setText(string + "%");

				 string = fmt.format(avariados_media_carregamento);

				lblAvariadosMediaAbaCarregamentos.setText(string + "%");
			}
		});
	}

	
}
