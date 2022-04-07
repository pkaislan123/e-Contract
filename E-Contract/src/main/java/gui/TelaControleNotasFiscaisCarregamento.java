package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static java.util.stream.Collectors.toCollection;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import javax.swing.table.TableCellRenderer;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.TreeSet;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import net.miginfocom.swing.MigLayout;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Carregamento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RecebimentoCompleto;
import main.java.cadastros.CarregamentoCompleto;
import main.java.cadastros.RegistroQuantidade;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.gui.TelaGerenciarContrato.CarregamentoCellRender;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoCarregamentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.relatoria.RelatorioNotasFiscais;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaEscolhaRelatorioRomaneios;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class TelaControleNotasFiscaisCarregamento extends JFrame {

	private static ArrayList<CarregamentoCompleto> lista_carregamentos = new ArrayList<>();
	private JDialog telaPai;
	private boolean finalizado = false;
	private JTable tabela;
	private boolean nulo = false;
	private File file_selecionado;
	private final JPanel painelPrincipal = new JPanel();
	private FileChooser fileChooser;
	private JLabel lblValorTotalNFVenda1, lblValorTotalNFComplemento;
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private TelaControleNotasFiscaisCarregamento isto;
	private JTextField entNomeComprador;
	private JTextField entNomeVendedor;
	private JComboBox cbStatus;

	private CarregamentoTableModel modelo_carregamentos = new CarregamentoTableModel();
	private TableRowSorter<CarregamentoTableModel> sorter;
	private JTextField entProduto;
	private JTextField entSafra;
	private JTextField entCodigo;
	private int id_contrato_pai_para_replica_global = 0;

	private int flag_retorno_global;
	private JTextField entTransgenia;
	private FileChooser d;
	private JLabel lblTotalCarregamentos, lblTotalCarregamentosOk, lblFaltaNFVenda1,
			lblFaltaNFComplemento, lblFaltaNFInterna;
	private JLabel lblPesoTotalRomaneios, lblPesoTotalNFVenda1, lblPesoTotalNFComplemento;
	private JTextField entNomeRemetenteNFVenda1;
	private JTextField entNomeDestinatarioNFVenda1;
	private JTextField entNomeRemetenteNFVendaComp;
	private JTextField entNomeDestinatarioNFVendaComp;
	private JTextField entCodigoNFVenda1;
	private JTextField entCodigoNFVendaComp;
	private JTextField entCodigoRomaneio;
	private JLabel lblPesoTotalNFInterna;
	private int duplicatas_nf_venda1 = 0;
	private int duplicatas_nf_venda_comp = 0;
	private int duplicatas_nf_interna = 0;

	private int flag_tipo_contrato = 2;
	private JTextField entCodigoNFInterna;

	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaControleNotasFiscaisCarregamento(Window janela_pai) {

		// setModal(true);
		// setAlwaysOnTop(true);

		isto = this;
		setResizable(true);
		setTitle("E-Contract - Controle de Notas Fiscais - Carregamentos");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		/*
		 * GraphicsConfiguration config = isto.getGraphicsConfiguration();
		 * 
		 * GraphicsDevice myScreen = config.getDevice();
		 * 
		 * GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 * 
		 * DisplayMode dm = myScreen.getDisplayMode();
		 */

		setContentPane(painelPrincipal);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		System.out.println("Screen width = " + dim.width);
		System.out.println("Screen height = " + dim.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, dim.width, dim.height - taskBarHeight);

		sorter = new TableRowSorter<CarregamentoTableModel>(modelo_carregamentos);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[][660px][grow][grow]", "[133px][grow][31px][1px][12px][]"));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrincipal.add(panel_5, "cell 0 0 4 1,grow");
		panel_5.setLayout(new MigLayout("", "[58px][274px,grow][48px][306px,grow][90px][199px,grow][67px][126px][59px]",
				"[][][][][][][][28px][28px][][28px]"));

		JLabel lblCdigoNfVenda = new JLabel("Código NF Venda 1:");
		lblCdigoNfVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblCdigoNfVenda, "cell 0 0,alignx trailing");

		entCodigoNFVenda1 = new JTextField();
		entCodigoNFVenda1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entCodigoNFVenda1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCodigoNFVenda1.setColumns(10);
		panel_5.add(entCodigoNFVenda1, "cell 1 0,growx");

		JLabel lblRemetenteNfRemessa = new JLabel("Remetente NF Venda 1:");
		lblRemetenteNfRemessa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblRemetenteNfRemessa, "cell 2 0,alignx trailing");

		entNomeRemetenteNFVenda1 = new JTextField();
		entNomeRemetenteNFVenda1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeRemetenteNFVenda1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeRemetenteNFVenda1.setColumns(10);
		panel_5.add(entNomeRemetenteNFVenda1, "cell 3 0 2 1,growx");

		JLabel lblDestinatarioNfRemessa = new JLabel("Destinatario NF Venda 1:");
		lblDestinatarioNfRemessa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblDestinatarioNfRemessa, "cell 5 0,alignx trailing");

		entNomeDestinatarioNFVenda1 = new JTextField();
		entNomeDestinatarioNFVenda1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeDestinatarioNFVenda1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeDestinatarioNFVenda1.setColumns(10);
		panel_5.add(entNomeDestinatarioNFVenda1, "cell 6 0 3 1,growx");

		JLabel lblStatus_1_1 = new JLabel("Duplicados:");
		lblStatus_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1_1, "cell 0 1,alignx right");

		JRadioButton rdbtnIncluirNFVenda1Duplicados = new JRadioButton("Incluir Duplicados");
		JRadioButton rdbtnNoIncluirNFVenda1Duplicados = new JRadioButton("Não Incluir Duplicados");
		JRadioButton rdbtnSomenteNFVenda1Duplicados = new JRadioButton("Somente Duplicados");

		rdbtnSomenteNFVenda1Duplicados.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				rdbtnSomenteNFVenda1Duplicados.setSelected(true);

				rdbtnNoIncluirNFVenda1Duplicados.setSelected(false);
				rdbtnIncluirNFVenda1Duplicados.setSelected(false);

				duplicatas_nf_venda1 = 2;

			}
		});

		rdbtnIncluirNFVenda1Duplicados.setSelected(true);
		rdbtnIncluirNFVenda1Duplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnIncluirNFVenda1Duplicados.setSelected(true);

				rdbtnSomenteNFVenda1Duplicados.setSelected(false);
				rdbtnNoIncluirNFVenda1Duplicados.setSelected(false);

				duplicatas_nf_venda1 = 0;
			}
		});
		panel_5.add(rdbtnIncluirNFVenda1Duplicados, "cell 1 1");

		rdbtnNoIncluirNFVenda1Duplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnNoIncluirNFVenda1Duplicados.setSelected(true);

				rdbtnIncluirNFVenda1Duplicados.setSelected(false);
				rdbtnSomenteNFVenda1Duplicados.setSelected(false);

				duplicatas_nf_venda1 = 1;
			}
		});
		panel_5.add(rdbtnNoIncluirNFVenda1Duplicados, "cell 2 1");

		panel_5.add(rdbtnSomenteNFVenda1Duplicados, "cell 3 1");

		JLabel lblCdigoNfRemessa = new JLabel("Código NF Comp:");
		lblCdigoNfRemessa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblCdigoNfRemessa, "cell 0 2,alignx trailing");

		entCodigoNFVendaComp = new JTextField();
		entCodigoNFVendaComp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entCodigoNFVendaComp.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCodigoNFVendaComp.setColumns(10);
		panel_5.add(entCodigoNFVendaComp, "cell 1 2,growx");

		JLabel lblRemetenteNfRemessa_3 = new JLabel("Remetente NF Comp.:");
		lblRemetenteNfRemessa_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblRemetenteNfRemessa_3, "cell 2 2,alignx trailing");

		entNomeRemetenteNFVendaComp = new JTextField();
		entNomeRemetenteNFVendaComp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeRemetenteNFVendaComp.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeRemetenteNFVendaComp.setColumns(10);
		panel_5.add(entNomeRemetenteNFVendaComp, "cell 3 2 2 1,growx");

		JLabel lblDestinatarioNfRemessa_2 = new JLabel("Destinatario NF Comp.:");
		lblDestinatarioNfRemessa_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblDestinatarioNfRemessa_2, "cell 5 2,alignx trailing");

		entNomeDestinatarioNFVendaComp = new JTextField();
		entNomeDestinatarioNFVendaComp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeDestinatarioNFVendaComp.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeDestinatarioNFVendaComp.setColumns(10);
		panel_5.add(entNomeDestinatarioNFVendaComp, "cell 6 2 3 1,growx");

		JLabel lblStatus_1_1_2 = new JLabel("Duplicados:");
		lblStatus_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1_1_2, "cell 0 3,alignx right");

		JRadioButton rdbtnIncluirNFVendaCompDuplicados = new JRadioButton("Incluir Duplicados");
		JRadioButton rdbtnSomenteNFVendaCompDuplicados = new JRadioButton("Somente Duplicados");
		JRadioButton rdbtnNoIncluirNFVendaCompDuplicados = new JRadioButton("Não Incluir Duplicados");

		rdbtnIncluirNFVendaCompDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnIncluirNFVendaCompDuplicados.setSelected(true);

				rdbtnSomenteNFVendaCompDuplicados.setSelected(false);
				rdbtnNoIncluirNFVendaCompDuplicados.setSelected(false);

				duplicatas_nf_venda_comp = 0;
			}
		});

		rdbtnNoIncluirNFVendaCompDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNoIncluirNFVendaCompDuplicados.setSelected(true);

				rdbtnSomenteNFVendaCompDuplicados.setSelected(false);
				rdbtnIncluirNFVendaCompDuplicados.setSelected(false);

				duplicatas_nf_venda_comp = 1;
			}
		});

		rdbtnIncluirNFVendaCompDuplicados.setSelected(true);
		panel_5.add(rdbtnIncluirNFVendaCompDuplicados, "cell 1 3");

		panel_5.add(rdbtnNoIncluirNFVendaCompDuplicados, "cell 2 3");

		rdbtnSomenteNFVendaCompDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnSomenteNFVendaCompDuplicados.setSelected(true);

				rdbtnIncluirNFVendaCompDuplicados.setSelected(false);
				rdbtnNoIncluirNFVendaCompDuplicados.setSelected(false);

				duplicatas_nf_venda_comp = 2;
			}
		});
		panel_5.add(rdbtnSomenteNFVendaCompDuplicados, "cell 3 3");

		JLabel lblCdigoNfInterna = new JLabel("Código NF Interna:");
		lblCdigoNfInterna.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblCdigoNfInterna, "cell 0 4,alignx trailing");

		entCodigoNFInterna = new JTextField();
		entCodigoNFInterna.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCodigoNFInterna.setColumns(10);
		panel_5.add(entCodigoNFInterna, "cell 1 4,growx");

		JLabel lblStatus_1_1_2_1 = new JLabel("Duplicados:");
		lblStatus_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1_1_2_1, "cell 0 5,alignx right");

		JRadioButton rdbtnIncluirNFInternaDuplicados = new JRadioButton("Incluir Duplicados");
		JRadioButton rdbtnNoIncluirNFInternaDuplicados = new JRadioButton("Não Incluir Duplicados");
		JRadioButton rdbtnSomenteNFInternaDuplicados = new JRadioButton("Somente Duplicados");

		rdbtnSomenteNFInternaDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnSomenteNFInternaDuplicados.setSelected(true);

				rdbtnIncluirNFInternaDuplicados.setSelected(false);
				rdbtnNoIncluirNFInternaDuplicados.setSelected(false);

				duplicatas_nf_interna = 2;
			}
		});

		rdbtnNoIncluirNFInternaDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnNoIncluirNFInternaDuplicados.setSelected(true);

				rdbtnIncluirNFInternaDuplicados.setSelected(false);
				rdbtnSomenteNFInternaDuplicados.setSelected(false);

				duplicatas_nf_interna = 1;
			}
		});

		rdbtnIncluirNFInternaDuplicados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnIncluirNFInternaDuplicados.setSelected(true);

				rdbtnNoIncluirNFInternaDuplicados.setSelected(false);
				rdbtnSomenteNFInternaDuplicados.setSelected(false);

				duplicatas_nf_interna = 0;

			}
		});
		rdbtnIncluirNFInternaDuplicados.setSelected(true);
		panel_5.add(rdbtnIncluirNFInternaDuplicados, "cell 1 5");

		panel_5.add(rdbtnNoIncluirNFInternaDuplicados, "cell 2 5");

		panel_5.add(rdbtnSomenteNFInternaDuplicados, "cell 3 5");

		JLabel lblRemetenteNfRemessa_1 = new JLabel((String) null);
		lblRemetenteNfRemessa_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblRemetenteNfRemessa_1, "cell 0 6");

		JLabel lblCdigo = new JLabel("Código Contrato:");
		panel_5.add(lblCdigo, "cell 0 7,alignx right,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entCodigo = new JTextField();
		entCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entCodigo, "cell 1 7,growx,aligny top");
		entCodigo.setColumns(10);

		entNomeVendedor = new JTextField();
		entNomeVendedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});

		JLabel lblCdigoRomaneio = new JLabel("Código Romaneio:");
		lblCdigoRomaneio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblCdigoRomaneio, "cell 2 7,alignx trailing");

		entCodigoRomaneio = new JTextField();
		entCodigoRomaneio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entCodigoRomaneio.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCodigoRomaneio.setColumns(10);
		panel_5.add(entCodigoRomaneio, "cell 3 7 2 1,growx");
		entNomeVendedor.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entNomeVendedor, "cell 3 8 2 1,growx,aligny top");
		entNomeVendedor.setColumns(10);

		JLabel lblNewLabel = new JLabel("Comprador:");
		panel_5.add(lblNewLabel, "cell 0 8,alignx right,aligny center");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeComprador = new JTextField();
		entNomeComprador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeComprador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entNomeComprador, "cell 1 8,growx,aligny top");
		entNomeComprador.setColumns(10);

		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1, "cell 5 7,alignx right");

		cbStatus = new JComboBox();
		cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbStatus.addItem("TODOS");
		cbStatus.addItem("OK");
		cbStatus.addItem("FALTA NF VENDA");
		cbStatus.addItem("FALTA NF REMESSA E VENDA");
		cbStatus.addItem("FALTA NF REMESSA");

		panel_5.add(cbStatus, "cell 6 7 3 1,growx");

		JLabel lblVendedor = new JLabel("Vendedor:");
		panel_5.add(lblVendedor, "cell 2 8,alignx right,aligny center");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblStatus_1_1_1 = new JLabel("Tipo do Contrato:");
		lblStatus_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1_1_1, "cell 5 9,alignx right");

		JRadioButton rdbtnCtrsOriginais = new JRadioButton("Originais");
		JRadioButton rdbtnCtrsSubContratos = new JRadioButton("Sub-Contratos");
		JRadioButton rdbtnCtrsAmbos = new JRadioButton("Ambos");

		rdbtnCtrsOriginais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnCtrsOriginais.setSelected(true);
				rdbtnCtrsSubContratos.setSelected(false);
				rdbtnCtrsAmbos.setSelected(false);

				flag_tipo_contrato = 0;
			}
		});
		panel_5.add(rdbtnCtrsOriginais, "cell 6 9");

		rdbtnCtrsSubContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnCtrsOriginais.setSelected(false);
				rdbtnCtrsSubContratos.setSelected(true);
				rdbtnCtrsAmbos.setSelected(false);

				flag_tipo_contrato = 1;

			}
		});
		panel_5.add(rdbtnCtrsSubContratos, "cell 7 9");

		rdbtnCtrsAmbos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnCtrsOriginais.setSelected(false);
				rdbtnCtrsSubContratos.setSelected(false);
				rdbtnCtrsAmbos.setSelected(true);

				flag_tipo_contrato = 2;

			}
		});
		rdbtnCtrsAmbos.setSelected(true);
		panel_5.add(rdbtnCtrsAmbos, "cell 8 9");

		JLabel lblProduto = new JLabel("Produto:");
		panel_5.add(lblProduto, "cell 0 10,alignx right,aligny center");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entProduto = new JTextField();
		entProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entProduto.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entProduto, "cell 1 10,growx,aligny top");
		entProduto.setColumns(10);

		JLabel lblSafra = new JLabel("Safra:");
		panel_5.add(lblSafra, "cell 2 10,alignx right,aligny center");
		lblSafra.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entSafra = new JTextField();
		entSafra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entSafra.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entSafra, "cell 3 10,growx,aligny top");
		entSafra.setColumns(10);

		JLabel lblTransgnese = new JLabel("Transgênese:");
		panel_5.add(lblTransgnese, "cell 4 10,alignx right,aligny center");
		lblTransgnese.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entTransgenia = new JTextField();
		entTransgenia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entTransgenia.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entTransgenia, "cell 5 10,growx,aligny top");
		entTransgenia.setColumns(10);

		JButton btnLimparFiltros = new JButton("Limpar");
		btnLimparFiltros.setBackground(new Color(204, 51, 0));
		btnLimparFiltros.setForeground(Color.WHITE);
		btnLimparFiltros.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnLimparFiltros, "cell 6 10,alignx left,aligny top");
		btnLimparFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));
				calcular();
			}
		});

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 0, 153));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnFiltrar, "cell 7 10,growx,aligny top");

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 51, 0));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnRefazerPesquisa, "cell 8 10,alignx left,aligny top");
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
				filtrar();
				calcular();
			}
		});
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
				calcular();
			}
		});

		tabela = new JTable(modelo_carregamentos);

		tabela.setRowSorter(sorter);

		tabela.setBackground(new Color(255, 255, 255));
		// tabela.setPreferredSize(new Dimension(0, 200));

		tabela.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela);
		painelPrincipal.add(scrollPane, "cell 0 1 4 4,grow");
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// pesquisar();
			}
		});
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setAutoscrolls(true);

		JPanel panel_8 = new JPanel();
		painelPrincipal.add(panel_8, "cell 1 5 2 1,grow");
		panel_8.setBackground(Color.WHITE);
		panel_8.setLayout(
				new MigLayout("", "[27.00][26.00][][31.00,grow][][grow][grow][grow][grow]", "[][][][][][][][]"));

		JLabel ads = new JLabel("Total Carregamentos:");
		ads.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(ads, "cell 1 0");

		lblTotalCarregamentos = new JLabel("0000");
		lblTotalCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalCarregamentos, "cell 2 0 2 1,growx");

		JLabel lblNewLabel_33_2 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_2, "cell 0 1,grow");
		lblNewLabel_33_2.setOpaque(true);
		lblNewLabel_33_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_2.setBackground(Color.green);

		JLabel lblNewLabel_34_2 = new JLabel("OK");
		panel_8.add(lblNewLabel_34_2, "cell 1 1,alignx left");
		lblNewLabel_34_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblTotalCarregamentosOk = new JLabel("0000");
		lblTotalCarregamentosOk.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalCarregamentosOk, "cell 2 1 2 1");

		JLabel lblPesoTotalRomaneios12 = new JLabel("Peso Total Romaneios:");
		lblPesoTotalRomaneios12.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalRomaneios12, "cell 4 1");

		lblPesoTotalRomaneios = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalRomaneios.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalRomaneios.setBorder(null);
		panel_8.add(lblPesoTotalRomaneios, "cell 5 1 2 1,growx");

		JLabel lblNewLabel_33_2_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_2_1, "cell 0 2,growx");
		lblNewLabel_33_2_1.setOpaque(true);
		lblNewLabel_33_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_2_1.setBackground(Color.ORANGE);

		JLabel lblNewLabel_34_2_1 = new JLabel("Falta NF Venda 1");
		panel_8.add(lblNewLabel_34_2_1, "cell 1 2,alignx left");
		lblNewLabel_34_2_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_2_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblFaltaNFVenda1 = new JLabel("0000");
		lblFaltaNFVenda1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblFaltaNFVenda1, "cell 2 2 2 1");

		JLabel lblPesoTotalNf1 = new JLabel("Peso Total NF Venda 1:");
		lblPesoTotalNf1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf1, "cell 4 2");

		lblPesoTotalNFVenda1 = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalNFVenda1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFVenda1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFVenda1, "cell 5 2 2 1");

		JLabel lblValorTotalNf = new JLabel("Valor Total NF Venda 1:");
		lblValorTotalNf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblValorTotalNf, "cell 7 2,alignx right");

		lblValorTotalNFVenda1 = new JLabel("R$ 0.0");
		lblValorTotalNFVenda1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblValorTotalNFVenda1.setBorder(null);
		panel_8.add(lblValorTotalNFVenda1, "cell 8 2,growx");

		JLabel lblNewLabel_33_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1, "cell 0 3,growx");
		lblNewLabel_33_1_1.setOpaque(true);
		lblNewLabel_33_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1.setBackground(Color.gray);

		JLabel lblNewLabel_34_1_1 = new JLabel("Falta NF Complemento");
		panel_8.add(lblNewLabel_34_1_1, "cell 1 3,alignx left");
		lblNewLabel_34_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblFaltaNFComplemento = new JLabel("0000");
		lblFaltaNFComplemento.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblFaltaNFComplemento, "cell 2 3 2 1");

		JLabel lblPesoTotalNf_2 = new JLabel("Peso Total NF Comp:");
		lblPesoTotalNf_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf_2, "cell 4 3,alignx right");

		lblPesoTotalNFComplemento = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalNFComplemento.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFComplemento.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFComplemento, "cell 5 3 2 1");

		JLabel lblValorTotalNf_2 = new JLabel("Valor Total NF Comp:");
		lblValorTotalNf_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblValorTotalNf_2, "cell 7 3,alignx right");

		lblValorTotalNFComplemento = new JLabel("R$ 0.0");
		lblValorTotalNFComplemento.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblValorTotalNFComplemento.setBorder(null);
		panel_8.add(lblValorTotalNFComplemento, "cell 8 3");

		JLabel lblNewLabel_33_1_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1_1, "cell 0 4,growx");
		lblNewLabel_33_1_1_1.setOpaque(true);
		lblNewLabel_33_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1_1.setBackground(Color.YELLOW);

		JLabel lblNewLabel_34_1_1_1 = new JLabel("Falta NF Interna");
		panel_8.add(lblNewLabel_34_1_1_1, "cell 1 4,alignx left");
		lblNewLabel_34_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_1_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblFaltaNFInterna = new JLabel("0000");
		lblFaltaNFInterna.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblFaltaNFInterna, "cell 2 4 2 1");
		
		JLabel lblPesoTotalNf_2_1 = new JLabel("Peso Total NF Interna:");
		lblPesoTotalNf_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf_2_1, "cell 4 4,alignx right");
		
		 lblPesoTotalNFInterna = new JLabel("0 Kgs | 0 Sacos");
		lblPesoTotalNFInterna.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFInterna.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFInterna, "cell 5 4 2 1");

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExportar.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnExportar, "flowx,cell 3 5,alignx right,aligny top");

		JButton btnAbrir = new JButton("Abrir");
		painelPrincipal.add(btnAbrir, "cell 3 5,alignx right,aligny top");
		btnAbrir.setForeground(Color.WHITE);
		btnAbrir.setBackground(new Color(0, 0, 153));
		btnAbrir.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnAbrir.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));

		pesquisar();
		calcular();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// this.pack();

		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_carregamentos.clear();
		modelo_carregamentos.onRemoveAll();

		NumberFormat z = NumberFormat.getNumberInstance();
		ArrayList<CarregamentoCompleto> carregamentos = gerenciar.getCarregamentos();

		if (flag_tipo_contrato == 0) {
			// somente ctrs

			ArrayList<CarregamentoCompleto> carregamentos_com_ctr_original = new ArrayList<>();
			for (CarregamentoCompleto carregamento : carregamentos) {

				int tipo_contrato = carregamento.getContrato().getSub_contrato();
				boolean e_ctr_original = false;

				if (tipo_contrato == 0 || tipo_contrato == 3 || tipo_contrato == 4 || tipo_contrato == 5) {
					e_ctr_original = true;
				}

				if (e_ctr_original) {
					carregamentos_com_ctr_original.add(carregamento);
				}

			}

			carregamentos = carregamentos_com_ctr_original;

		} else if (flag_tipo_contrato == 1) {
			// somente sub-ctrs

			ArrayList<CarregamentoCompleto> carregamentos_com_subctr = new ArrayList<>();
			for (CarregamentoCompleto carregamento : carregamentos) {

				int tipo_contrato = carregamento.getContrato().getSub_contrato();
				boolean e_sub = false;

				if (tipo_contrato == 1 || tipo_contrato == 2 || tipo_contrato == 6 || tipo_contrato == 7
						|| tipo_contrato == 8)
					e_sub = true;
				if (e_sub) {
					carregamentos_com_subctr.add(carregamento);
				}

			}

			carregamentos = carregamentos_com_subctr;

		} else {

		}

		if (duplicatas_nf_venda1 == 0) {

		} else if (duplicatas_nf_venda1 == 1) {
			// nao incluir duplicados de nf venda
			// remove duplicados de nf_venda
			Set<String> set = new HashSet<>(carregamentos.size());
			carregamentos.removeIf(p -> !set.add(p.getCodigo_nf_venda1()));

		} else if (duplicatas_nf_venda1 == 2) {

			ArrayList<CarregamentoCompleto> carregamentos_duplicados = new ArrayList<>();
			for (CarregamentoCompleto carregamento : carregamentos) {

				String nf_venda1 = carregamento.getCodigo_nf_venda1();
				boolean tem_duplicata = false;

				for (CarregamentoCompleto carregamento_busca : carregamentos) {
					if (carregamento_busca.getId_carregamento() != carregamento.getId_carregamento()) {
						if (carregamento_busca.getCodigo_nf_venda1().equalsIgnoreCase(nf_venda1)) {
							tem_duplicata = true;
							break;
						}
					}
				}

				if (tem_duplicata) {
					carregamentos_duplicados.add(carregamento);
				}

			}

			carregamentos = carregamentos_duplicados;

		}

		if (duplicatas_nf_venda_comp == 0) {

		} else if (duplicatas_nf_venda_comp == 1) {
			// nao incluir duplicados de nf venda
			// remove duplicados de nf_venda
			Set<String> set = new HashSet<>(carregamentos.size());
			carregamentos.removeIf(p -> !set.add(p.getCodigo_nf_complemento()));

		} else if (duplicatas_nf_venda_comp == 2) {

			ArrayList<CarregamentoCompleto> carregamentos_duplicados = new ArrayList<>();
			for (CarregamentoCompleto carregamento : carregamentos) {

				String nf_venda_comp = carregamento.getCodigo_nf_complemento();
				boolean tem_duplicata = false;

				for (CarregamentoCompleto carregamento_busca : carregamentos) {
					if (carregamento_busca.getId_carregamento() != carregamento.getId_carregamento()) {
						if (carregamento_busca.getCodigo_nf_complemento().equalsIgnoreCase(nf_venda_comp)) {
							tem_duplicata = true;
							break;
						}
					}
				}

				if (tem_duplicata) {
					carregamentos_duplicados.add(carregamento);
				}

			}

			carregamentos = carregamentos_duplicados;

		}

		if (duplicatas_nf_interna == 0) {

		} else if (duplicatas_nf_interna == 1) {
			// nao incluir duplicados de nf venda
			// remove duplicados de nf_venda
			Set<String> set = new HashSet<>(carregamentos.size());
			carregamentos.removeIf(p -> !set.add(p.getCodigo_nf_interna()));

		} else if (duplicatas_nf_interna == 2) {

			ArrayList<CarregamentoCompleto> carregamentos_duplicados = new ArrayList<>();
			for (CarregamentoCompleto carregamento : carregamentos) {

				String nf_interna = carregamento.getCodigo_nf_interna();
				boolean tem_duplicata = false;

				for (CarregamentoCompleto carregamento_busca : carregamentos) {
					if (carregamento_busca.getId_carregamento() != carregamento.getId_carregamento()) {
						if (carregamento_busca.getCodigo_nf_interna().equalsIgnoreCase(nf_interna)) {
							tem_duplicata = true;
							break;
						}
					}
				}

				if (tem_duplicata) {
					carregamentos_duplicados.add(carregamento);
				}

			}

			carregamentos = carregamentos_duplicados;

		}

		for (CarregamentoCompleto carregamento : carregamentos) {

			modelo_carregamentos.onAdd(carregamento);
			lista_carregamentos.add(carregamento);
		}

	}

	public void calcular() {

		double peso_total_romaneios = 0, peso_total_nf_venda1 = 0, peso_total_nf_interna = 0,
				peso_total_nf_complemento = 0;

		int numero_carregamentos = 0;
		int carregamentos_ok = 0;
		int carregamentos_falta_nf_venda1 = 0;
		int carregamentos_falta_nf_complemento = 0;
		int carregamentos_falta_nf_interna = 0;
		
		

		for (int row = 0; row < tabela.getRowCount(); row++) {

			int index = tabela.convertRowIndexToModel(row);
			CarregamentoCompleto carregamento = modelo_carregamentos.getValue(index);

			String codigo_nf_venda = carregamento.getCodigo_nf_venda1();
			String codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
			String codigo_nf_interna = carregamento.getCodigo_nf_interna();

			peso_total_romaneios += carregamento.getPeso_romaneio();
			peso_total_nf_venda1 += carregamento.getPeso_nf_venda1();
			peso_total_nf_complemento += carregamento.getPeso_nf_complemento();
			peso_total_nf_interna += carregamento.getPeso_nf_interna();

			
			numero_carregamentos++;
			
			if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1
					&& carregamento.getNf_interna_aplicavel() == 1) {
				// todas as notas ativas

				if (checkString(codigo_nf_venda) && checkString(codigo_nf_complemento)
						&& checkString(codigo_nf_interna)) {
					// ok
					carregamentos_ok++;
				} else if (!checkString(codigo_nf_venda) && checkString(codigo_nf_complemento)
						&& checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_venda1++;
				} else if (checkString(codigo_nf_venda) && !checkString(codigo_nf_complemento)
						&& checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_complemento++;
				} else if (checkString(codigo_nf_venda) && checkString(codigo_nf_complemento)
						&& !checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_interna++;
				}

				else if (!checkString(codigo_nf_venda) && !checkString(codigo_nf_complemento)
						&& checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_venda1++;
					carregamentos_falta_nf_complemento++;

				}

				else if (checkString(codigo_nf_venda) && !checkString(codigo_nf_complemento)
						&& !checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_interna++;
					carregamentos_falta_nf_complemento++;

				}

				else if (!checkString(codigo_nf_venda) && checkString(codigo_nf_complemento)
						&& !checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_interna++;
					carregamentos_falta_nf_venda1++;

				}

				else if (!checkString(codigo_nf_venda) && !checkString(codigo_nf_complemento)
						&& !checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_interna++;
					carregamentos_falta_nf_venda1++;
					carregamentos_falta_nf_complemento++;

				}

			} else if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 0
					&& carregamento.getNf_interna_aplicavel() == 0) {
				// nf de venda 1 aplicavel
				if (!checkString(codigo_nf_venda))
					carregamentos_falta_nf_venda1++;
				else
					carregamentos_ok++;

			} else if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 1
					&& carregamento.getNf_interna_aplicavel() == 0) {

				if (checkString(codigo_nf_complemento) && checkString(codigo_nf_venda)) {
					carregamentos_ok++;
				} else if (!checkString(codigo_nf_complemento) && checkString(codigo_nf_venda)) {
					carregamentos_falta_nf_complemento++;

				} else if (checkString(codigo_nf_complemento) && !checkString(codigo_nf_venda)) {
					carregamentos_falta_nf_venda1++;

				} else if (!checkString(codigo_nf_complemento) && !checkString(codigo_nf_venda)) {
					carregamentos_falta_nf_venda1++;
					carregamentos_falta_nf_complemento++;

				}

			} else if (carregamento.getNf_venda1_aplicavel() == 1 && carregamento.getNf_complemento_aplicavel() == 0
					&& carregamento.getNf_interna_aplicavel() == 1) {

				if (checkString(codigo_nf_interna) && checkString(codigo_nf_venda)) {
					carregamentos_ok++;
				} else if (!checkString(codigo_nf_interna) && checkString(codigo_nf_venda)) {
					carregamentos_falta_nf_interna++;

				} else if (checkString(codigo_nf_interna) && !checkString(codigo_nf_venda)) {
					carregamentos_falta_nf_venda1++;

				} else if (!checkString(codigo_nf_interna) && !checkString(codigo_nf_venda)) {
					carregamentos_falta_nf_interna++;
					carregamentos_falta_nf_venda1++;

				}

			}

			else if (carregamento.getNf_venda1_aplicavel() == 0 && carregamento.getNf_complemento_aplicavel() == 1
					&& carregamento.getNf_interna_aplicavel() == 0) {
				if (!checkString(codigo_nf_complemento))
					carregamentos_falta_nf_complemento++;
				else
					carregamentos_ok++;

			}

			else if (carregamento.getNf_venda1_aplicavel() == 0 && carregamento.getNf_complemento_aplicavel() == 0
					&& carregamento.getNf_interna_aplicavel() == 1) {
				if (!checkString(codigo_nf_interna))
					carregamentos_falta_nf_interna++;
				else
					carregamentos_ok++;

			} else if (carregamento.getNf_venda1_aplicavel() == 0 && carregamento.getNf_complemento_aplicavel() == 1
					&& carregamento.getNf_interna_aplicavel() == 1) {

				if (checkString(codigo_nf_complemento) && checkString(codigo_nf_interna)) {
					carregamentos_ok++;
				} else if (!checkString(codigo_nf_complemento) && checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_complemento++;

				} else if (checkString(codigo_nf_complemento) && !checkString(codigo_nf_interna)) {
					carregamentos_falta_nf_interna++;

				} else if (!checkString(codigo_nf_complemento) && !checkString(codigo_nf_interna)) {

					carregamentos_falta_nf_interna++;
					carregamentos_falta_nf_complemento++;
				}

			}

			else if (carregamento.getNf_venda1_aplicavel() == 0 && carregamento.getNf_complemento_aplicavel() == 0
					&& carregamento.getNf_interna_aplicavel() == 0) {
				// nenhum aplicavel
				carregamentos_ok++;
			}
		}

		lblTotalCarregamentos.setText(numero_carregamentos + "");
		lblTotalCarregamentosOk.setText(carregamentos_ok + "");
		lblFaltaNFInterna.setText(carregamentos_falta_nf_interna + "");
		lblFaltaNFComplemento.setText(carregamentos_falta_nf_complemento + "");
		lblFaltaNFVenda1.setText(carregamentos_falta_nf_venda1 + "");

		NumberFormat z = NumberFormat.getNumberInstance();

		lblPesoTotalRomaneios
				.setText(z.format(peso_total_romaneios) + " Kgs | " + z.format(peso_total_romaneios / 60) + " Sacos");
		lblPesoTotalNFVenda1
				.setText(z.format(peso_total_nf_venda1) + " Kgs | " + z.format(peso_total_nf_venda1 / 60) + " Sacos");
		lblPesoTotalNFComplemento.setText(
				z.format(peso_total_nf_complemento) + " Kgs | " + z.format(peso_total_nf_complemento / 60) + " Sacos");
		lblPesoTotalNFInterna
		.setText(z.format(peso_total_nf_interna) + " Kgs | " + z.format(peso_total_nf_interna / 60) + " Sacos");


	}

	public static boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void filtrar() {

		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		ArrayList<RowFilter<Object, Object>> filters_nome_destinatario_nf_venda1 = new ArrayList<RowFilter<Object, Object>>(
				2);
		ArrayList<RowFilter<Object, Object>> filters_nome_remetente_nf_venda1 = new ArrayList<RowFilter<Object, Object>>(
				2);

		ArrayList<RowFilter<Object, Object>> filters_nome_remetente_nf_venda_comp = new ArrayList<RowFilter<Object, Object>>(
				2);
		ArrayList<RowFilter<Object, Object>> filters_nome_destinatario_nf_venda_comp = new ArrayList<RowFilter<Object, Object>>(
				2);

		ArrayList<RowFilter<Object, Object>> filters_produto = new ArrayList<RowFilter<Object, Object>>(2);

		RowFilter<Object, Object> destinatario_nf_venda1_filters;
		RowFilter<Object, Object> destinatario_nf_venda_comp_filters;
		RowFilter<Object, Object> destinatario_nf_interna_filters;

		RowFilter<Object, Object> remetente_nf_venda1_filters;
		RowFilter<Object, Object> remetente_nf_venda_comp_filters;
		RowFilter<Object, Object> remetente_nf_interna_filters;

		RowFilter<Object, Object> produto_filters;

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String status = cbStatus.getSelectedItem().toString().toUpperCase();
		String transgenese = entTransgenia.getText().toUpperCase();

		String codigo_nf_venda1 = entCodigoNFVenda1.getText().toUpperCase();
		String nome_remetente_nf_venda1 = entNomeRemetenteNFVenda1.getText().toUpperCase();
		String nome_destinatario_nf_venda1 = entNomeDestinatarioNFVenda1.getText().toUpperCase();

		String codigo_nf_comp = entCodigoNFVendaComp.getText().toUpperCase();
		String nome_remetente_nf_venda_comp = entNomeRemetenteNFVendaComp.getText().toUpperCase();
		String nome_destinatario_nf_venda_comp = entNomeDestinatarioNFVendaComp.getText().toUpperCase();

		String codigo_nf_interna = entCodigoNFInterna.getText().toUpperCase();

		String codigo_romaneio = entCodigoRomaneio.getText().toUpperCase();

		// filtrar codigo romaneio
		if (checkString(codigo_romaneio))
			filters.add(RowFilter.regexFilter(codigo_romaneio, 2));

		// filtrar codigo nf venda
		if (checkString(codigo_nf_venda1))
			filters.add(RowFilter.regexFilter(codigo_nf_venda1, 4));

		// filtrar codigo nf comp
		if (checkString(codigo_nf_comp))
			filters.add(RowFilter.regexFilter(codigo_nf_comp, 9));

		// filtrar codigo nf interna
		if (checkString(codigo_nf_interna))
			filters.add(RowFilter.regexFilter(codigo_nf_interna, 14));

		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 18));

		if (checkString(comprador))
			filters.add(RowFilter.regexFilter(comprador, 19));

		if (checkString(vendedor))
			filters.add(RowFilter.regexFilter(vendedor, 20));

		// filtro de produto

		String produtos[] = produto.split(",");
		for (String prod : produtos) {
			filters_produto.add(RowFilter.regexFilter(prod, 21));

		}
		produto_filters = RowFilter.orFilter(filters_produto);

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 22));

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 23));

		if (checkString(status) && !(status.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(status, 16));

		// remetente da nf de venda'
		String remetentes_nf_venda[] = nome_remetente_nf_venda1.split(",");
		for (String nome : remetentes_nf_venda) {
			filters_nome_remetente_nf_venda1.add(RowFilter.regexFilter(nome, 5));

		}
		remetente_nf_venda1_filters = RowFilter.orFilter(filters_nome_remetente_nf_venda1);

		// destinatario da nf de venda'
		String destinatarios_nf_venda[] = nome_destinatario_nf_venda1.split(",");
		for (String nome : destinatarios_nf_venda) {
			filters_nome_destinatario_nf_venda1.add(RowFilter.regexFilter(nome, 6));

		}
		destinatario_nf_venda1_filters = RowFilter.orFilter(filters_nome_destinatario_nf_venda1);

		// remetente da nf de complemento
		String remetentes_nf_venda_comp[] = nome_remetente_nf_venda_comp.split(",");
		for (String nome : remetentes_nf_venda_comp) {
			filters_nome_remetente_nf_venda_comp.add(RowFilter.regexFilter(nome, 10));

		}
		remetente_nf_venda_comp_filters = RowFilter.orFilter(filters_nome_remetente_nf_venda_comp);

		// destinatario da nf de complemento
		String destinatarios_nf_venda_comp[] = nome_destinatario_nf_venda_comp.split(",");
		for (String nome : destinatarios_nf_venda_comp) {
			filters_nome_destinatario_nf_venda_comp.add(RowFilter.regexFilter(nome, 11));

		}
		destinatario_nf_venda_comp_filters = RowFilter.orFilter(filters_nome_destinatario_nf_venda_comp);

		filters.add(remetente_nf_venda1_filters);
		filters.add(destinatario_nf_venda1_filters);

		filters.add(remetente_nf_venda_comp_filters);
		filters.add(destinatario_nf_venda_comp_filters);
		filters.add(produto_filters);

		// sorter.setRowFilter(RowFilter.orFilter(filters_nome_destinatario_nf_venda));
		sorter.setRowFilter(RowFilter.andFilter(filters));

	}

	public String trimar(String texto) {
		String aplicar_rtrim = texto.replaceAll("\\s+$", "");
		String aplicar_ltrim = aplicar_rtrim.replaceAll("^\\s+", "");
		return aplicar_ltrim;

	}

	public String reduzirNome(String texto) {

		String nome_remetente_completo = texto;

		String nome_remetente_quebrado[] = texto.split(" ");
		String nome_remetente = null;
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

		return nome_remetente;

	}

	public static class CarregamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int id_carregamento = 0;
		private final int data = 1;
		private final int codigo_romaneio = 2;
		private final int peso_romaneio = 3;

		private final int codigo_nf_venda1 = 4;
		private final int remetente_nf_venda1 = 5;
		private final int destinatario_nf_venda1 = 6;
		private final int peso_nf_venda1 = 7;
		private final int valor_nf_venda1 = 8;

		private final int codigo_nf_venda_compl = 9;
		private final int remetente_nf_venda_compl = 10;
		private final int destinatario_nf_venda_compl = 11;
		private final int peso_nf_venda_compl = 12;
		private final int valor_nf_venda_compl = 13;

		private final int codigo_nf_interna = 14;
		private final int peso_nf_interna = 15;

		private final int status = 16;

		private final int id_contrato = 17;
		private final int codigo_contrato = 18;
		private final int compradores = 19;
		private final int vendedores = 20;
		private final int produto = 21;
		private final int transgenia = 22;
		private final int safra = 23;

		private final String colunas[] = { "ID:", "Data:", "Código Romaneio:", "Peso Romaneio:", "Código NF Venda 1:",
				"Remetente NF Venda 1'", "Destinatario NF Venda 1", "Peso NF Venda 1", "Valor NF Venda 1",
				"Código NF Venda Comp.", "Remetente NF Venda Comp.", "Destinatario NF Venda Comp.",
				"Peso NF Venda Comp.:", "Valor NF Venda Comp.:", "Código NF Interna", "Peso NF Interna", "Status",
				"ID Contrato", "Codigo", "Compradores", "Vendedores", "Produto", "Transgenia", "Safra" };
		private final ArrayList<CarregamentoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																				// genérica de
		// nfs

		public CarregamentoTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			/*
			 * private final int data = 0; private final int codigo_romaneio = 1;
			 * 
			 * private final int peso_romaneio = 2; private final int codigo_nf_venda = 3;
			 * private final int peso_nf_venda = 4;
			 * 
			 * private final int codigo_nf_remessa = 5;
			 * 
			 */

			case id_carregamento:
				return Integer.class;
			case data:
				return String.class;
			case codigo_romaneio:
				return String.class;
			case peso_romaneio:
				return String.class;

			case codigo_nf_venda1:
				return String.class;
			case remetente_nf_venda1:
				return String.class;
			case destinatario_nf_venda1:
				return String.class;
			case peso_nf_venda1:
				return String.class;
			case valor_nf_venda1:
				return String.class;

			case codigo_nf_venda_compl:
				return String.class;
			case remetente_nf_venda_compl:
				return String.class;
			case destinatario_nf_venda_compl:
				return String.class;
			case peso_nf_venda_compl:
				return String.class;
			case valor_nf_venda_compl:
				return String.class;

			case codigo_nf_interna:
				return String.class;

			case peso_nf_interna:
				return String.class;
			case status:
				return String.class;
			case id_contrato:
				return Integer.class;
			case codigo_contrato:
				return int.class;
			case compradores:
				return String.class;
			case vendedores:
				return String.class;
			case produto:
				return String.class;
			case transgenia:
				return String.class;
			case safra:
				return String.class;

			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return colunas[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// retorna o valor conforme a coluna e linha
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CarregamentoCompleto carregamento = dados.get(rowIndex);
			CadastroContrato contrato = carregamento.getContrato();

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_carregamento:
				return carregamento.getId_carregamento();
			case data:
				return carregamento.getData();

			case codigo_romaneio:
				return carregamento.getCodigo_romaneio();
			case peso_romaneio:
				double peso_romaneio = carregamento.getPeso_romaneio();
				return z.format(peso_romaneio) + " KGs";

			case codigo_nf_venda1: {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					return carregamento.getCodigo_nf_venda1();

				} else {
					return "Não Aplicável";
				}
			}

			case remetente_nf_venda1: {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					String nome_remetente = carregamento.getNome_remetente_nf_venda1();
					if (checkString(nome_remetente)) {
						return nome_remetente.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case destinatario_nf_venda1: {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					String nome_destinatario = carregamento.getNome_destinatario_nf_venda1();
					if (checkString(nome_destinatario)) {
						return nome_destinatario.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case peso_nf_venda1: {

				if (carregamento.getNf_venda1_aplicavel() == 1) {
					double peso_nf_venda = carregamento.getPeso_nf_venda1();
					return z.format(peso_nf_venda) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}
			case valor_nf_venda1: {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(carregamento.getValor_nf_venda1());
					return valorString;

				} else {
					return "Não Aplicável";
				}
			}

			case codigo_nf_venda_compl: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					return carregamento.getCodigo_nf_complemento();

				} else {
					return "Não Aplicável";
				}
			}

			case remetente_nf_venda_compl: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					String nome_remetente = carregamento.getNome_remetente_nf_complemento();
					if (checkString(nome_remetente)) {
						return nome_remetente.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case destinatario_nf_venda_compl: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					String nome_destinatario = carregamento.getNome_destinatario_nf_complemento();
					if (checkString(nome_destinatario)) {
						return nome_destinatario.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case peso_nf_venda_compl: {

				if (carregamento.getNf_complemento_aplicavel() == 1) {
					double peso_nf_venda = carregamento.getPeso_nf_complemento();
					return z.format(peso_nf_venda) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}
			case valor_nf_venda_compl: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(carregamento.getValor_nf_complemento());
					return valorString;

				} else {
					return "Não Aplicável";
				}
			}

			case codigo_nf_interna: {
				if (carregamento.getNf_interna_aplicavel() == 1) {
					return carregamento.getCodigo_nf_interna();

				} else {
					return "Não Aplicável";
				}
			}

			case peso_nf_interna: {

				if (carregamento.getNf_interna_aplicavel() == 1) {
					double peso_nf_venda = carregamento.getPeso_nf_interna();
					return z.format(peso_nf_venda) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}

			case status: {
				return "";
			}

			case id_contrato: {
				return contrato.getId();
			}
			case codigo_contrato: {
				return contrato.getCodigo();
			}
			case compradores: {
				return contrato.getNomes_compradores().toUpperCase();
			}
			case vendedores: {
				return contrato.getNomes_vendedores().toUpperCase();
			}
			case produto: {
				return contrato.getModelo_produto().getNome_produto().toUpperCase();
			}
			case transgenia: {
				return contrato.getModelo_produto().getTransgenia().toUpperCase();
			}
			case safra: {
				return contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita();
			}
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroContrato.Carregamento carregamento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CarregamentoCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato.Carregamento nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CarregamentoCompleto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CarregamentoCompleto> dadosIn) {
			dados.addAll(dadosIn);
			fireTableDataChanged();
		}

		/**
		 * remove um registro da lista, através do indice
		 * 
		 * @param rowIndex
		 */
		public void onRemove(int rowIndex) {
			dados.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		/**
		 * remove um registro da lista, através do objeto
		 * 
		 * @param empregado
		 */
		public void onRemove(CadastroContrato.Carregamento nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public void gerarExcel(HSSFWorkbook workbook) {
		try {

			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (fileChooser == null) {
					fileChooser = new FileChooser();
				}
				fileChooser.setInitialDirectory(new File(ultima_pasta));
				fileChooser.getExtensionFilters().addAll(

						new FileChooser.ExtensionFilter("Excel", "*.xls"));
				File file = fileChooser.showSaveDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
					// Escrevendo o arquivo em disco
					FileOutputStream out;
					try {
						out = new FileOutputStream(file);
						workbook.write(out);
						workbook.close();
						out.close();
						// workbook.close();

						Runtime.getRuntime().exec("explorer " + file.getAbsolutePath());

						System.out.println("Success!!");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			});

		} catch (Exception k) {
			k.printStackTrace();
		}
	}

}
