package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.util.concurrent.TimeUnit;

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
import java.awt.Window;
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
import conexaoBanco.GerenciarBancoRomaneios;
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
import java.awt.Insets;

public class TelaMonitoria extends JFrame {
	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelRecebimento;
	private final JPanel painelCarregamento;
	private TelaMonitoria isto;
	private JCheckBox chckbxHoje;
	private JTable table_carga_aba_carregamentos;
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

	private JLabel lblUmidadeMediaDescargaAbaRecebimento, lblNumTotalCargasAbaRecebimento,
			lblUmidadeMediaCargaAbaCarregamento, lblTotalSacosCargaAbaRecebimento, lblNumTotalKGCargaAbaRecebimento,
			lblImpurezaMediaAbaCarregamentos, lblAvariadosMediaAbaCarregamentos;
	private JLabel lblUmidadeMedia, lblKgDescarga, lblNumSacosDescarga, lblNumDescargas, lblUmidadeMediaAbaRecebimento,
			lblImpurezaMediaAbaRecebimento, lblAvariadosMediaAbaRecebimento;
	private int num_descargas = 0;
	private double total_sacos_descarga;
	private double total_kg_descarga;
	private double umidade_media = 0;
	private double avariados_media;
	private double impureza_media;
	private double avariados_media_carregamento;
	private double impureza_media_carregamento;
	private boolean todas_as_safras = true;
	private boolean todo_periodo = false;
	// variaveis para carregamento
	private JLabel lblUmidadeMediaCarga, lblNumTotalCargas, lblTotalSacosCarga, lblNumTotalKGCarga,
			lblNumDescargasAbaRecebimento, lblKgDescargaAbaRecebimento, lblNumSacosDescargaAbaRecebimento;

	private int num_cargas = 0;
	private double total_sacos_carga;
	private double total_kg_carga;
	private double umidade_media_saida = 0;
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();

	private NumberFormat z = NumberFormat.getNumberInstance();
	private JTable table;
	private JTable table_1;
	private ArrayList<CadastroRomaneio> romaneios_locais = new ArrayList<>();

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}

	public TelaMonitoria(Window janela_pai) {

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

		setBounds(100, 100, 1377, 728);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		painelAbas.addTab("Monitor Principal", painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][382px][5px][63px][5px][321px][5px][118px][5px][382px]",
				"[44px][182px][387px,grow]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel, "cell 1 0,alignx center,growy");
		panel.setLayout(new MigLayout("", "[]", "[][]"));

		JLabel lblNewLabel_2 = new JLabel("Recebimento");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel.add(lblNewLabel_2, "cell 0 1,alignx center");

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(panel_1, "cell 9 0,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[][][]", "[]"));

		JLabel lblNewLabel_3 = new JLabel("Carregamento");
		lblNewLabel_3.setBackground(new Color(0, 51, 0));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel_1.add(lblNewLabel_3, "cell 2 0,alignx center");
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.WHITE);
		painelPrincipal.add(panel_10, "cell 1 1,grow");
		panel_10.setLayout(new MigLayout("", "[92px][181px][95px]", "[139px][4px][20px]"));

		lblNumSacosDescarga = new JLabel("00.000");
		panel_10.add(lblNumSacosDescarga, "cell 0 0 3 1,alignx center,aligny top");
		lblNumSacosDescarga.setForeground(new Color(0, 102, 51));
		lblNumSacosDescarga.setFont(new Font("Arial", Font.BOLD, 120));

		lblKgDescarga = new JLabel("0.000.000,00 KG");
		panel_10.add(lblKgDescarga, "cell 0 2,alignx left,aligny top");

		JLabel lblNewLabel = new JLabel("sacos");
		panel_10.add(lblNewLabel, "cell 2 0 1 3,alignx left,aligny bottom");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));

		JPanel panel_14 = new JPanel();
		panel_14.setBackground(Color.WHITE);
		painelPrincipal.add(panel_14, "cell 3 1 5 1,grow");
		panel_14.setLayout(new MigLayout("", "[382px][39px][108px][23px][120px][12px][248px][385px]",
				"[78px][12px][96px][352px]"));

		JPanel panel_2 = new JPanel();
		panel_14.add(panel_2, "cell 2 0 3 1,grow");
		panel_2.setBackground(new Color(0, 102, 51));
		panel_2.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4 = new JLabel("Umidade");
		lblNewLabel_4.setForeground(Color.WHITE);
		panel_2.add(lblNewLabel_4, "cell 0 0,alignx left,aligny bottom");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel lblNewLabel_5 = new JLabel("média:");
		lblNewLabel_5.setForeground(Color.WHITE);
		panel_2.add(lblNewLabel_5, "cell 0 2,alignx right,aligny top");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 20));

		lblUmidadeMedia = new JLabel("00,0%");
		lblUmidadeMedia.setForeground(new Color(0, 0, 153));
		panel_2.add(lblUmidadeMedia, "cell 1 0 1 3,alignx left,aligny top");
		lblUmidadeMedia.setFont(new Font("SansSerif", Font.PLAIN, 50));

		JPanel panel_3 = new JPanel();
		panel_14.add(panel_3, "cell 2 2 3 1,growx,aligny top");
		panel_3.setBackground(new Color(0, 0, 153));
		panel_3.setLayout(new MigLayout("", "[135px][56px]", "[32px][32px]"));

		JLabel lblNewLabel_4_2 = new JLabel("Total");
		lblNewLabel_4_2.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_4_2, "cell 0 0,alignx left,aligny bottom");
		lblNewLabel_4_2.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel lblNewLabel_5_1 = new JLabel("de descargas:");
		lblNewLabel_5_1.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_5_1, "cell 0 1,alignx right,aligny top");
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.PLAIN, 20));

		lblNumDescargas = new JLabel("0");
		lblNumDescargas.setForeground(new Color(153, 0, 51));
		panel_3.add(lblNumDescargas, "cell 1 0 1 2,growx,aligny top");
		lblNumDescargas.setFont(new Font("SansSerif", Font.PLAIN, 50));

		JPanel panel_3_1 = new JPanel();
		panel_14.add(panel_3_1, "cell 6 2,growx,aligny top");
		panel_3_1.setBackground(new Color(0, 102, 51));
		panel_3_1.setLayout(new MigLayout("", "[77px][63px][56px]", "[30px][4px][30px]"));

		JLabel lblNewLabel_4_2_1 = new JLabel("Total");
		lblNewLabel_4_2_1.setForeground(Color.WHITE);
		lblNewLabel_4_2_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_3_1.add(lblNewLabel_4_2_1, "cell 0 0,growx,aligny bottom");

		JLabel lblNewLabel_5_1_1 = new JLabel("de cargas:");
		lblNewLabel_5_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_3_1.add(lblNewLabel_5_1_1, "cell 0 2 3 1,alignx center,aligny top");

		lblNumTotalCargas = new JLabel("0");
		lblNumTotalCargas.setForeground(new Color(0, 0, 102));
		lblNumTotalCargas.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_3_1.add(lblNumTotalCargas, "cell 2 0 1 3,growx,aligny top");

		JPanel panel_2_1 = new JPanel();
		panel_14.add(panel_2_1, "cell 6 0,grow");
		panel_2_1.setBackground(new Color(0, 0, 153));
		panel_2_1.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_3 = new JLabel("Umidade");
		lblNewLabel_4_3.setForeground(Color.WHITE);
		lblNewLabel_4_3.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_1.add(lblNewLabel_4_3, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_2 = new JLabel("média:");
		lblNewLabel_5_2.setForeground(Color.WHITE);
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_1.add(lblNewLabel_5_2, "cell 0 2,alignx right,aligny top");

		lblUmidadeMediaCarga = new JLabel("00,0%");
		lblUmidadeMediaCarga.setBackground(Color.WHITE);
		lblUmidadeMediaCarga.setForeground(Color.RED);
		lblUmidadeMediaCarga.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_2_1.add(lblUmidadeMediaCarga, "cell 1 0 1 3,alignx left,aligny top");

		JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.WHITE);
		painelPrincipal.add(panel_13, "cell 9 1,grow");
		panel_13.setLayout(new MigLayout("", "[92px][238px][62px]", "[139px][5px][20px]"));

		lblTotalSacosCarga = new JLabel("00.000");
		lblTotalSacosCarga.setBackground(Color.WHITE);
		panel_13.add(lblTotalSacosCarga, "cell 0 0 3 1,alignx left,aligny top");
		lblTotalSacosCarga.setForeground(new Color(255, 102, 51));
		lblTotalSacosCarga.setFont(new Font("Arial", Font.BOLD, 120));

		JLabel lblNewLabel_7 = new JLabel("sacos");
		panel_13.add(lblNewLabel_7, "cell 2 0 1 3,alignx left,aligny bottom");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 24));

		lblNumTotalKGCarga = new JLabel("0.000.000,00 KG");
		panel_13.add(lblNumTotalKGCarga, "cell 0 2,alignx left,aligny top");

		JPanel panel_16 = new JPanel();
		painelPrincipal.add(panel_16, "cell 0 2 10 1,grow");
		panel_16.setLayout(null);
		JPanel panel_15 = new JPanel();
		panel_15.setBounds(7, 7, 468, 377);
		panel_16.add(panel_15);
		panel_15.setBackground(new Color(0, 51, 102));

		table_romaeios_entrada = new JTable(modelo_romaneios_entrada);
		table_romaeios_entrada.setFont(new Font("Arial", Font.PLAIN, 18));

		modelo_romaneios_entrada.addColumn("Produtor");
		modelo_romaneios_entrada.addColumn("Safra");
		modelo_romaneios_entrada.addColumn("Liquido");
		
		table_romaeios_entrada.getColumnModel().getColumn(0).setPreferredWidth(250);
		table_romaeios_entrada.getColumnModel().getColumn(1).setPreferredWidth(160);
		table_romaeios_entrada.getColumnModel().getColumn(2).setPreferredWidth(250);
		panel_15.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(table_romaeios_entrada);
		panel_15.add(scrollPane);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(487, 85, 337, 219);
		panel_16.add(panel_7);
		panel_7.setBackground(Color.WHITE);
		panel_7.setLayout(null);

		JCheckBox cBTodoPeriodo = new JCheckBox("Todo o Periodo");
		cBTodoPeriodo.setBounds(80, 7, 158, 26);
		panel_7.add(cBTodoPeriodo);
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

		chckbxHoje = new JCheckBox("Hoje");
		chckbxHoje.setBounds(175, 37, 61, 26);
		panel_7.add(chckbxHoje);
		chckbxHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxHoje.isSelected()) {

					chckbxHoje.setSelected(true);
					cBTodoPeriodo.setSelected(false);
					todo_periodo = false;

					Date date_hoje = new Date();

					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String dataFormatada = dateFormat.format(date_hoje);

					entDataInformada.setText(dataFormatada);
					entDataInformada.setEditable(true);
					entDataInformada.setEnabled(true);

				} else {

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

		JLabel lblNewLabel_31 = new JLabel("Data:");
		lblNewLabel_31.setBounds(7, 79, 57, 28);
		panel_7.add(lblNewLabel_31);
		lblNewLabel_31.setFont(new Font("Arial", Font.PLAIN, 24));

		entDataInformada = new JTextField();
		entDataInformada.setBounds(80, 67, 250, 51);
		panel_7.add(entDataInformada);
		entDataInformada.setEnabled(false);
		entDataInformada.setFont(new Font("SansSerif", Font.PLAIN, 30));
		entDataInformada.setText("03/01/2021");
		entDataInformada.setColumns(10);
		Date date_hoje = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = dateFormat.format(date_hoje);

		entDataInformada.setText(dataFormatada);
		entDataInformada.setEditable(true);
		entDataInformada.setEnabled(true);

		JLabel lblNewLabel_6_1 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6_1.setBounds(7, 122, 323, 39);
		panel_7.add(lblNewLabel_6_1);
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

		cbCarregamentoPorSafra = new JComboBox();
		cbCarregamentoPorSafra.setBounds(7, 165, 323, 47);
		panel_7.add(cbCarregamentoPorSafra);
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
		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();

		cbCarregamentoPorSafra.setModel(modelSafra);
		cbCarregamentoPorSafra.setRenderer(cBSafraPersonalizado);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(836, 7, 485, 377);
		panel_16.add(panel_1_1);
		panel_1_1.setBackground(new Color(0, 51, 0));

		table_romaneios_saida = new JTable(modelo_romaneios_saida);
		table_romaneios_saida.setFont(new Font("Arial", Font.PLAIN, 18));

		modelo_romaneios_saida.addColumn("Comprador");
		modelo_romaneios_saida.addColumn("Safra");
		modelo_romaneios_saida.addColumn("Liquido");

		
		table_romaneios_saida.getColumnModel().getColumn(0).setPreferredWidth(250);
		table_romaneios_saida.getColumnModel().getColumn(1).setPreferredWidth(160);
		table_romaneios_saida.getColumnModel().getColumn(2).setPreferredWidth(250);
		panel_1_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane(table_romaneios_saida);
		panel_1_1.add(scrollPane_1);

		/*
		 * JPanel panel_14 = new JPanel(); panel_14.setLayout(new MigLayout("", "[]",
		 * "[]"));
		 */

		/*
		 * JPanel panel_15 = new JPanel(); panel_15.setBackground(Color.WHITE);
		 * panel_15.setLayout(new BorderLayout(0, 0));
		 */

		



	
		painelRecebimento = new JPanel();
		painelRecebimento.setBackground(Color.WHITE);
		painelAbas.addTab("Monitor Recebimento", painelRecebimento);
		painelRecebimento.setLayout(new MigLayout("", "[516.00px,grow][632px][696.00px,grow]",
				"[][153.00px,grow][641.00px,grow][165.00px]"));

		/*
		 * JPanel panel_11 = new JPanel(); painelRecebimento.add(panel_11,
		 * "cell 2 1,grow"); panel_11.setLayout(new MigLayout("", "[]", "[]"));
		 */

		/*
		 * JPanel panel_12 = new JPanel(); panel_12.setLayout(new MigLayout("", "[]",
		 * "[]"));
		 */

		JLabel lblNewLabel_4_1_2 = new JLabel("     Recebimento");
		lblNewLabel_4_1_2.setOpaque(true);
		lblNewLabel_4_1_2.setForeground(Color.WHITE);
		lblNewLabel_4_1_2.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1_2.setBackground(new Color(0, 51, 0));
		painelRecebimento.add(lblNewLabel_4_1_2, "cell 1 0 2 1,alignx center");
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.WHITE);
		panel_12.setBounds(108, 107, 448, 189);
		painelRecebimento.add(panel_12, "cell 0 1 2 1,alignx center,aligny center");
		panel_12.setLayout(new MigLayout("", "[368px][62px]", "[139px][32px]"));

		JLabel lblNewLabel_1 = new JLabel("sacos");
		panel_12.add(lblNewLabel_1, "cell 1 1,alignx left,aligny top");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 24));

		lblNumSacosDescargaAbaRecebimento = new JLabel("000.000.000");
		panel_12.add(lblNumSacosDescargaAbaRecebimento, "cell 0 0,alignx left,aligny top");
		lblNumSacosDescargaAbaRecebimento.setForeground(new Color(0, 102, 51));
		lblNumSacosDescargaAbaRecebimento.setFont(new Font("Arial", Font.BOLD, 120));

		lblKgDescargaAbaRecebimento = new JLabel("0.000.000,00 KG");
		panel_12.add(lblKgDescargaAbaRecebimento, "cell 0 1,alignx left,aligny top");

		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.WHITE);
		panel_11.setBounds(694, 54, 506, 184);
		painelRecebimento.add(panel_11, "cell 2 1,grow");
		panel_11.setLayout(new MigLayout("", "[][]", "[][]"));

		JPanel panel_2_2 = new JPanel();
		panel_11.add(panel_2_2, "cell 0 0");
		panel_2_2.setBackground(new Color(0, 102, 51));
		panel_2_2.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_4 = new JLabel("Umidade");
		lblNewLabel_4_4.setForeground(Color.WHITE);
		lblNewLabel_4_4.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2.add(lblNewLabel_4_4, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_3 = new JLabel("média:");
		lblNewLabel_5_3.setForeground(Color.WHITE);
		lblNewLabel_5_3.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2.add(lblNewLabel_5_3, "cell 0 2,alignx right,aligny top");

		lblUmidadeMediaAbaRecebimento = new JLabel("00,0%");
		lblUmidadeMediaAbaRecebimento.setForeground(new Color(0, 0, 153));
		lblUmidadeMediaAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_2_2.add(lblUmidadeMediaAbaRecebimento, "cell 1 0 1 3,alignx left,aligny top");

		JPanel panel_2_2_1 = new JPanel();
		panel_11.add(panel_2_2_1, "cell 1 0");
		panel_2_2_1.setBackground(new Color(0, 102, 51));
		panel_2_2_1.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_4_1 = new JLabel("Impureza");
		lblNewLabel_4_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_4_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1.add(lblNewLabel_4_4_1, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_3_1 = new JLabel("média:");
		lblNewLabel_5_3_1.setForeground(Color.WHITE);
		panel_2_2_1.add(lblNewLabel_5_3_1, "cell 0 2,alignx right,growy");

		lblImpurezaMediaAbaRecebimento = new JLabel("00,0%");
		lblImpurezaMediaAbaRecebimento.setForeground(new Color(0, 0, 153));
		lblImpurezaMediaAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_2_2_1.add(lblImpurezaMediaAbaRecebimento, "cell 1 0 1 3,alignx left,aligny top");

		JPanel panel_3_2 = new JPanel();
		panel_11.add(panel_3_2, "cell 0 1,grow");
		panel_3_2.setBackground(new Color(0, 0, 153));
		panel_3_2.setLayout(new MigLayout("", "[135px][56px]", "[32px][32px]"));

		JLabel lblNewLabel_4_2_2 = new JLabel("Total");
		lblNewLabel_4_2_2.setForeground(Color.WHITE);
		lblNewLabel_4_2_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_3_2.add(lblNewLabel_4_2_2, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_1_2 = new JLabel("de descargas:");
		lblNewLabel_5_1_2.setForeground(Color.WHITE);
		lblNewLabel_5_1_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_3_2.add(lblNewLabel_5_1_2, "cell 0 1,alignx right,aligny top");

		lblNumDescargasAbaRecebimento = new JLabel("0");
		lblNumDescargasAbaRecebimento.setForeground(new Color(153, 0, 51));
		lblNumDescargasAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_3_2.add(lblNumDescargasAbaRecebimento, "cell 1 0 1 2,growx,aligny top");

		JPanel panel_2_2_1_1 = new JPanel();
		panel_11.add(panel_2_2_1_1, "cell 1 1");
		panel_2_2_1_1.setBackground(new Color(0, 102, 51));
		panel_2_2_1_1.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_4_1_1 = new JLabel("Avariados");
		lblNewLabel_4_4_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1_1.add(lblNewLabel_4_4_1_1, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_3_1_1 = new JLabel("média:");
		lblNewLabel_5_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_3_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1_1.add(lblNewLabel_5_3_1_1, "cell 0 2,alignx right,aligny top");

		lblAvariadosMediaAbaRecebimento = new JLabel("00,0%");
		lblAvariadosMediaAbaRecebimento.setForeground(new Color(0, 0, 153));
		lblAvariadosMediaAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_2_2_1_1.add(lblAvariadosMediaAbaRecebimento, "cell 1 0 1 3,alignx left,aligny top");

		/*
		 * JPanel panel_4 = new JPanel(); panel_4.setLayout(null);
		 * panel_4.setBackground(Color.CYAN);
		 */
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(7, 307, 1332, 320);
		panel_4.setBackground(Color.WHITE);
		painelRecebimento.add(panel_4, "cell 0 2 3 1,grow");

		modelo_romaneios_entrada_aba_recebimento.addColumn("Depositante");
		modelo_romaneios_entrada_aba_recebimento.addColumn("Remetente");
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

		table_aba_recebimento.getColumnModel().getColumn(0).setPreferredWidth(600);
		table_aba_recebimento.getColumnModel().getColumn(1).setPreferredWidth(300);
		table_aba_recebimento.getColumnModel().getColumn(2).setPreferredWidth(250);
		table_aba_recebimento.getColumnModel().getColumn(3).setPreferredWidth(225);
		table_aba_recebimento.getColumnModel().getColumn(4).setPreferredWidth(180);
		table_aba_recebimento.getColumnModel().getColumn(5).setPreferredWidth(100);
		table_aba_recebimento.getColumnModel().getColumn(6).setPreferredWidth(100);
		table_aba_recebimento.getColumnModel().getColumn(7).setPreferredWidth(100);
		table_aba_recebimento.getColumnModel().getColumn(8).setPreferredWidth(200);
		panel_4.setLayout(new BorderLayout(0, 0));
		table_aba_recebimento.setRowHeight(30);

		JScrollPane scrollPane_2 = new JScrollPane(table_aba_recebimento);
		scrollPane_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_4.add(scrollPane_2);

		painelCarregamento = new JPanel();
		painelCarregamento.setBackground(Color.WHITE);
		painelAbas.addTab("Monitor Carregamento", painelCarregamento);
		painelCarregamento.setLayout(new MigLayout("", "[516.00px,grow][632px,grow][696.00px]",
				"[][206.00px,grow][20.00,grow][641.00px,grow,fill][165.00px]"));

		/*
		 * JPanel panel_6 = new JPanel(); panel_6.setBackground(Color.WHITE);
		 * panel_6.setLayout(new MigLayout("", "[]", "[]"));
		 */

		JLabel lblNewLabel_4_1_1_1 = new JLabel("  Carregamento");
		lblNewLabel_4_1_1_1.setOpaque(true);
		lblNewLabel_4_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_1_1_1.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_4_1_1_1.setBackground(new Color(0, 51, 0));
		painelCarregamento.add(lblNewLabel_4_1_1_1, "cell 0 0 2 1,alignx center");

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		painelCarregamento.add(panel_8, "cell 0 2,grow");
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		painelCarregamento.add(panel_6, "cell 0 1,grow");
		panel_6.setLayout(new MigLayout("", "[][]", "[][]"));

		JPanel panel_2_1_1 = new JPanel();
		panel_6.add(panel_2_1_1, "cell 0 0");
		panel_2_1_1.setBackground(new Color(0, 0, 153));
		panel_2_1_1.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_3_1 = new JLabel("Umidade");
		lblNewLabel_4_3_1.setForeground(Color.WHITE);
		lblNewLabel_4_3_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_1_1.add(lblNewLabel_4_3_1, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_2_1 = new JLabel("média:");
		lblNewLabel_5_2_1.setForeground(Color.WHITE);
		lblNewLabel_5_2_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_1_1.add(lblNewLabel_5_2_1, "cell 0 2,alignx right,aligny top");

		lblUmidadeMediaCargaAbaCarregamento = new JLabel("00,0%");
		lblUmidadeMediaCargaAbaCarregamento.setForeground(Color.RED);
		lblUmidadeMediaCargaAbaCarregamento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		lblUmidadeMediaCargaAbaCarregamento.setBackground(Color.WHITE);
		panel_2_1_1.add(lblUmidadeMediaCargaAbaCarregamento, "cell 1 0 1 3,alignx left,aligny top");

		JPanel panel_2_2_1_2 = new JPanel();
		panel_6.add(panel_2_2_1_2, "cell 1 0");
		panel_2_2_1_2.setBackground(new Color(0, 102, 51));
		panel_2_2_1_2.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_4_1_2 = new JLabel("Impureza");
		lblNewLabel_4_4_1_2.setForeground(Color.WHITE);
		lblNewLabel_4_4_1_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1_2.add(lblNewLabel_4_4_1_2, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_3_1_2 = new JLabel("média:");
		lblNewLabel_5_3_1_2.setForeground(Color.WHITE);
		lblNewLabel_5_3_1_2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1_2.add(lblNewLabel_5_3_1_2, "cell 0 2,alignx right,aligny top");

		lblImpurezaMediaAbaCarregamentos = new JLabel("00,0%");
		lblImpurezaMediaAbaCarregamentos.setForeground(new Color(0, 0, 153));
		lblImpurezaMediaAbaCarregamentos.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_2_2_1_2.add(lblImpurezaMediaAbaCarregamentos, "cell 1 0 1 3,alignx left,aligny top");

		JPanel panel_3_1_1 = new JPanel();
		panel_6.add(panel_3_1_1, "cell 0 1,growx");
		panel_3_1_1.setBackground(new Color(0, 102, 51));
		panel_3_1_1.setLayout(new MigLayout("", "[77px][55px][56px]", "[32px][32px]"));

		JLabel lblNewLabel_4_2_1_1 = new JLabel("Total");
		lblNewLabel_4_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_3_1_1.add(lblNewLabel_4_2_1_1, "cell 0 0,growx,aligny bottom");

		JLabel lblNewLabel_5_1_1_1 = new JLabel("de cargas:");
		lblNewLabel_5_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_3_1_1.add(lblNewLabel_5_1_1_1, "cell 0 1 3 1,alignx center,aligny top");

		lblNumTotalCargasAbaRecebimento = new JLabel("0");
		lblNumTotalCargasAbaRecebimento.setForeground(new Color(0, 0, 102));
		lblNumTotalCargasAbaRecebimento.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_3_1_1.add(lblNumTotalCargasAbaRecebimento, "cell 2 0 1 2,alignx center,aligny top");

		JPanel panel_2_2_1_1_1 = new JPanel();
		panel_6.add(panel_2_2_1_1_1, "cell 1 1");
		panel_2_2_1_1_1.setBackground(new Color(0, 102, 51));
		panel_2_2_1_1_1.setLayout(new MigLayout("", "[103px][142px]", "[35px][3px][26px]"));

		JLabel lblNewLabel_4_4_1_1_1 = new JLabel("Avariados");
		lblNewLabel_4_4_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_4_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1_1_1.add(lblNewLabel_4_4_1_1_1, "cell 0 0,alignx left,aligny bottom");

		JLabel lblNewLabel_5_3_1_1_1 = new JLabel("média:");
		lblNewLabel_5_3_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_5_3_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_2_2_1_1_1.add(lblNewLabel_5_3_1_1_1, "cell 0 2,alignx right,aligny top");

		lblAvariadosMediaAbaCarregamentos = new JLabel("00,0%");
		lblAvariadosMediaAbaCarregamentos.setForeground(new Color(0, 0, 153));
		lblAvariadosMediaAbaCarregamentos.setFont(new Font("SansSerif", Font.PLAIN, 50));
		panel_2_2_1_1_1.add(lblAvariadosMediaAbaCarregamentos, "cell 1 0 1 3,alignx left,aligny top");

		/*
		 * JPanel panel_5 = new JPanel(); panel_5.setBackground(Color.WHITE);
		 * panel_5.setLayout(new MigLayout("", "[]", "[]"));
		 */

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelCarregamento.add(panel_5, "cell 1 1 2 1,alignx center,aligny center");
		panel_5.setLayout(new MigLayout("", "[92px][79px][197px]", "[139px][4px][20px]"));

		lblTotalSacosCargaAbaRecebimento = new JLabel("00.000.000");
		panel_5.add(lblTotalSacosCargaAbaRecebimento, "cell 0 0 3 1,alignx center,growy");
		lblTotalSacosCargaAbaRecebimento.setForeground(new Color(255, 102, 51));
		lblTotalSacosCargaAbaRecebimento.setFont(new Font("Arial", Font.BOLD, 120));

		lblNumTotalKGCargaAbaRecebimento = new JLabel("0.000.000,00 KG");
		panel_5.add(lblNumTotalKGCargaAbaRecebimento, "cell 0 2,alignx left,aligny top");

		JLabel lblNewLabel_7_1 = new JLabel("sacos");
		panel_5.add(lblNewLabel_7_1, "cell 2 0 1 3,alignx right,aligny bottom");
		lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 24));

		modelo_romaneios_saida_aba_carregamentos.addColumn("Destinatario");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Safra");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Bruto");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Total");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Liquido");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Umidade");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Impureza");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Avariados");
		modelo_romaneios_saida_aba_carregamentos.addColumn("Data");

		/*
		 * JPanel panel_9 = new JPanel(); panel_9.setBackground(Color.WHITE);
		 * painelCarregamento.add(panel_9, "cell 0 3 3 2,grow"); panel_9.setLayout(new
		 * BorderLayout(0, 0));
		 */JPanel panel_9 = new JPanel();
		painelCarregamento.add(panel_9, "cell 0 3 3 2,growx,aligny center");

		table_carga_aba_carregamentos = new JTable(modelo_romaneios_saida_aba_carregamentos);
		table_carga_aba_carregamentos.setFont(new Font("SansSerif", Font.PLAIN, 20));

		table_carga_aba_carregamentos.getColumnModel().getColumn(0).setPreferredWidth(600);
		table_carga_aba_carregamentos.getColumnModel().getColumn(1).setPreferredWidth(250);
		table_carga_aba_carregamentos.getColumnModel().getColumn(2).setPreferredWidth(250);
		table_carga_aba_carregamentos.getColumnModel().getColumn(3).setPreferredWidth(225);
		table_carga_aba_carregamentos.getColumnModel().getColumn(4).setPreferredWidth(220);
		table_carga_aba_carregamentos.getColumnModel().getColumn(5).setPreferredWidth(100);
		table_carga_aba_carregamentos.getColumnModel().getColumn(6).setPreferredWidth(100);
		table_carga_aba_carregamentos.getColumnModel().getColumn(7).setPreferredWidth(100);
		table_carga_aba_carregamentos.getColumnModel().getColumn(8).setPreferredWidth(200);
		panel_9.setLayout(new BorderLayout(0, 0));
		table_carga_aba_carregamentos.setRowHeight(30);

		JScrollPane scrollPane_1_1 = new JScrollPane(table_carga_aba_carregamentos);
		panel_9.add(scrollPane_1_1);

		getContentPane().add(painelAbas, BorderLayout.CENTER);
		pesquisarSafras();

		for (CadastroSafra safra : safras) {

			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);

		}
		todo_periodo = false;

		slide();
		this.setLocationRelativeTo(janela_pai);

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

	public void vigilante_todos_os_romaneios() {

		new Thread() {

			@Override
			public void run() {
				limpar();

					modelo_romaneios_entrada.setNumRows(0);
					modelo_romaneios_entrada_aba_recebimento.setNumRows(0);
					modelo_romaneios_saida_aba_carregamentos.setNumRows(0);
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

					ArrayList<CadastroRomaneio> romaneios = new GerenciarBancoRomaneios().listarRomaneios();

					romaneios_locais.clear();

					for (CadastroRomaneio rom : romaneios) {
						
						boolean ja_esta_na_lista = false;
						
						for(CadastroRomaneio rom_na_lista : romaneios_locais) {
							if(rom_na_lista.getNumero_romaneio() == rom.getNumero_romaneio()) {
								ja_esta_na_lista = true;
								break;
							}
						}
						
						if(!ja_esta_na_lista) {

						String nome_remetente, safra;
						double bruto, tara, liquido, umidade;

						CadastroCliente remetente = rom.getRemetente();
						if (remetente.getTipo_pessoa() == 0) {
							nome_remetente = remetente.getNome_empresarial().toUpperCase();
						} else {
							nome_remetente = remetente.getNome_fantaia().toUpperCase();

						}

						String nome_remetente_completo = nome_remetente;

						String nome_remetente_quebrado[] = nome_remetente.split(" ");
						// rodrigo cesar de moura
						// [0] rodrigo [1] cesar [2] de [3] moura
						try {
							if (nome_remetente_quebrado.length > 1) {
								if (nome_remetente_quebrado[2].length() > 2) {
									nome_remetente = nome_remetente_quebrado[0] + " " + nome_remetente_quebrado[2];
								} else {
									if (nome_remetente_quebrado[3].length() > 1) {
										nome_remetente = nome_remetente_quebrado[0] + " " + nome_remetente_quebrado[3];

									} else {
										nome_remetente = nome_remetente_quebrado[0] + " " + nome_remetente_quebrado[1];

									}
								}
							}
						} catch (Exception y) {
							nome_remetente = nome_remetente_completo;
						}

						String nome_destinatario = "";
						if (rom.getDestinatario() != null) {
							CadastroCliente destinatario = rom.getDestinatario();
							if (destinatario.getTipo_pessoa() == 0) {
								nome_destinatario = destinatario.getNome_empresarial().toUpperCase();
							} else {
								nome_destinatario = destinatario.getNome_fantaia().toUpperCase();

							}
						}
						String nome_destinatario_completo = nome_destinatario;

						String nome_destinatario_quebrado[] = nome_remetente.split(" ");
						try {

							if (nome_destinatario_quebrado.length > 2) {
								if (nome_destinatario_quebrado[2].length() > 1) {
									nome_destinatario = nome_destinatario_quebrado[0] + " "
											+ nome_destinatario_quebrado[2];
								} else {
									if (nome_destinatario_quebrado[3].length() > 1) {
										nome_destinatario = nome_destinatario_quebrado[0] + " "
												+ nome_destinatario_quebrado[3];

									} else {
										nome_destinatario = nome_destinatario_quebrado[0] + " "
												+ nome_destinatario_quebrado[1];

									}
								}
							}

						} catch (Exception v) {
							nome_destinatario = nome_destinatario_completo;
						}

						String ano_plantio = Integer.toString(rom.getSafra().getAno_plantio());
						ano_plantio = ano_plantio.substring(2, 4);

						String ano_colheita = Integer.toString(rom.getSafra().getAno_colheita());
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

						if (!todo_periodo) {
							if (rom.getData().equals(date)) {
								if (rom.getOperacao().equals("ENTRADA")) {

									if (todas_as_safras) {
										modelo_romaneios_entrada.addRow(new Object[] { nome_remetente, safra,
												d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs" });
										modelo_romaneios_entrada_aba_recebimento.addRow(new Object[] {
												nome_remetente_completo, nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG",
												rom.getTara() + " KG", d_peso_liquido + " KG", rom.getUmidade() + "%",
												rom.getInpureza() + "%", rom.getAvariados() + "%",
												dateFormat.format(rom.getData()) });
										num_descargas++;

										umidade_media = umidade_media + rom.getUmidade();
										avariados_media = avariados_media + rom.getAvariados();
										impureza_media = impureza_media + rom.getInpureza();
										total_sacos_descarga = total_sacos_descarga + (d_peso_liquido / 60);
										total_kg_descarga = total_kg_descarga + d_peso_liquido;
									} else {

										if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {

											modelo_romaneios_entrada.addRow(new Object[] { nome_remetente, safra,
													d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs" });
											modelo_romaneios_entrada_aba_recebimento.addRow(new Object[] {
													nome_remetente_completo, nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG",
													rom.getTara() + " KG", d_peso_liquido + " KG",
													rom.getUmidade() + "%", rom.getInpureza() + "%",
													rom.getAvariados() + "%", dateFormat.format(rom.getData()) });

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
												new Object[] { nome_remetente_completo, safra, d_peso_liquido });
										num_cargas++;

										modelo_romaneios_saida_aba_carregamentos.addRow(new Object[] {
												nome_remetente_completo, safra, rom.getPeso_bruto() + " KG",
												rom.getTara() + " KG", d_peso_liquido + " KG", rom.getUmidade() + "%",
												rom.getInpureza() + "%", rom.getAvariados() + "%",
												dateFormat.format(rom.getData()) });

										umidade_media_saida = umidade_media_saida + rom.getUmidade();
										avariados_media_carregamento = avariados_media_carregamento
												+ rom.getAvariados();
										impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();

										total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
										total_kg_carga = total_kg_carga + d_peso_liquido;
									} else {
										if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {
											modelo_romaneios_saida.addRow(
													new Object[] { nome_remetente_completo, safra, d_peso_liquido });
											num_cargas++;
											modelo_romaneios_saida_aba_carregamentos.addRow(new Object[] {
													nome_remetente_completo, safra, rom.getPeso_bruto() + " KG",
													rom.getTara() + " KG", d_peso_liquido + " KG",
													rom.getUmidade() + "%", rom.getInpureza() + "%",
													rom.getAvariados() + "%", dateFormat.format(rom.getData()) });

											umidade_media_saida = umidade_media_saida + rom.getUmidade();

											total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
											total_kg_carga = total_kg_carga + d_peso_liquido;
											avariados_media_carregamento = avariados_media_carregamento
													+ rom.getAvariados();
											impureza_media_carregamento = impureza_media_carregamento
													+ rom.getInpureza();
										}

									}
								}
							} // fim do if de data
						} else {
							if (rom.getOperacao().equals("ENTRADA")) {

								if (todas_as_safras) {
									modelo_romaneios_entrada.addRow(new Object[] { nome_remetente, safra,
											d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs" });
									modelo_romaneios_entrada_aba_recebimento.addRow(
											new Object[] { nome_remetente_completo, nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG",
													rom.getTara() + " KG", d_peso_liquido + " KG",
													rom.getUmidade() + "%", rom.getInpureza() + "%",
													rom.getAvariados() + "%", dateFormat.format(rom.getData()) });

									num_descargas++;

									umidade_media = umidade_media + rom.getUmidade();
									avariados_media = avariados_media + rom.getAvariados();
									impureza_media = impureza_media + rom.getInpureza();
									total_sacos_descarga = total_sacos_descarga + (d_peso_liquido / 60);
									total_kg_descarga = total_kg_descarga + d_peso_liquido;
								} else {

									if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {

										modelo_romaneios_entrada.addRow(new Object[] { nome_remetente, safra,
												d_peso_liquido + " KG / " + ((int) d_peso_liquido / 60) + " SCs" });
										modelo_romaneios_entrada_aba_recebimento.addRow(new Object[] {
												nome_remetente_completo, nome_destinatario_completo, safra, rom.getPeso_bruto() + " KG",
												rom.getTara() + " KG", d_peso_liquido + " KG", rom.getUmidade() + "%",
												rom.getInpureza() + "%", rom.getAvariados() + "%",
												dateFormat.format(rom.getData()) });

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
									modelo_romaneios_saida
											.addRow(new Object[] { nome_remetente_completo, safra, d_peso_liquido });
									modelo_romaneios_saida_aba_carregamentos.addRow(
											new Object[] { nome_remetente_completo, safra, rom.getPeso_bruto() + " KG",
													rom.getTara() + " KG", d_peso_liquido + " KG",
													rom.getUmidade() + "%", rom.getInpureza() + "%",
													rom.getAvariados() + "%", dateFormat.format(rom.getData()) });

									num_cargas++;

									umidade_media_saida = umidade_media_saida + rom.getUmidade();
									total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
									total_kg_carga = total_kg_carga + d_peso_liquido;
									avariados_media_carregamento = avariados_media_carregamento + rom.getAvariados();
									impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();
								} else {
									if (rom.getSafra().getId_safra() == safra_selecionada.getId_safra()) {
										modelo_romaneios_saida.addRow(
												new Object[] { nome_remetente_completo, safra, d_peso_liquido });
										modelo_romaneios_saida_aba_carregamentos.addRow(new Object[] {
												nome_remetente_completo, safra, rom.getPeso_bruto() + " KG",
												rom.getTara() + " KG", d_peso_liquido + " KG", rom.getUmidade() + "%",
												rom.getInpureza() + "%", rom.getAvariados() + "%",
												dateFormat.format(rom.getData()) });

										num_cargas++;

										umidade_media_saida = umidade_media_saida + rom.getUmidade();
										total_sacos_carga = total_sacos_carga + (d_peso_liquido / 60);
										total_kg_carga = total_kg_carga + d_peso_liquido;
										avariados_media_carregamento = avariados_media_carregamento
												+ rom.getAvariados();
										impureza_media_carregamento = impureza_media_carregamento + rom.getInpureza();
									}

								}
							}

						}
						romaneios_locais.add(rom);

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

				// aba carregamento
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

	public void limpar() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				lblUmidadeMedia.setText("");
				lblUmidadeMediaAbaRecebimento.setText("");

				lblImpurezaMediaAbaRecebimento.setText("");

				lblAvariadosMediaAbaRecebimento.setText("");

				lblNumSacosDescarga.setText("");
				lblNumSacosDescargaAbaRecebimento.setText("");

				lblNumDescargas.setText("");
				lblNumDescargasAbaRecebimento.setText("");

				lblKgDescarga.setText("");
				lblKgDescargaAbaRecebimento.setText("");

				lblUmidadeMediaCarga.setText("");

				lblTotalSacosCarga.setText("");
				lblNumTotalCargas.setText(Integer.toString(num_cargas));

				lblNumTotalKGCarga.setText("");

				// aba carregamento
				lblUmidadeMediaCargaAbaCarregamento.setText("");
				lblNumTotalCargasAbaRecebimento.setText("");

				lblTotalSacosCargaAbaRecebimento.setText("");
				lblNumTotalKGCargaAbaRecebimento.setText("");

				lblImpurezaMediaAbaCarregamentos.setText("");

				lblAvariadosMediaAbaCarregamentos.setText("");

				modelo_romaneios_entrada.setNumRows(0);
				modelo_romaneios_entrada_aba_recebimento.setNumRows(0);
			}
		});

	}

	public void slide() {
/*
		new Thread() {
			@Override
			public void run() {

				while (true) {

					for (int i = 0; i < 3; i++) {
						painelAbas.setSelectedIndex(i);
						System.out.println("mudando de tela");
						try {
							Thread.sleep(60000);
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}

					}

				}

			}
		}.start();
        */
	}
}
