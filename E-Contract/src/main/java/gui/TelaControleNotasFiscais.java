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
import main.java.cadastros.CadastroContrato.Recebimento;
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
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
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
import main.java.gui.TelaGerenciarContrato.RecebimentoCellRender;
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
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
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

public class TelaControleNotasFiscais extends JFrame {

	private static ArrayList<RecebimentoCompleto> lista_recebimentos = new ArrayList<>();
	private JDialog telaPai;
	private boolean finalizado = false;
	private JTable tabela;
	private boolean nulo = false;
	private File file_selecionado;
	private final JPanel painelPrincipal = new JPanel();
	private FileChooser fileChooser;
	private JLabel lblValorTotalNFVenda ,  lblValorTotalNFRemessa;
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private TelaControleNotasFiscais isto;
	private JTextField entNomeComprador;
	private JTextField entNomeVendedor;
	private JComboBox cbStatus;

	private RecebimentoTableModel modelo_recebimentos = new RecebimentoTableModel();
	private TableRowSorter<RecebimentoTableModel> sorter;
	private JTextField entProduto;
	private JTextField entSafra;
	private JTextField entCodigo;
	private int id_contrato_pai_para_replica_global = 0;

	private int flag_retorno_global;
	private JTextField entTransgenia;
	private FileChooser d;
	private JLabel lblTotalRecebimentos, lblTotalRecebimentosOk, lblTotalRecebimentosNFVenda,
			lblTotalRecebimentosNFRemessaVenda, lblFaltaNFRemessa;
	private JLabel lblPesoTotalRomaneios, lblPesoTotalNFVenda, lblPesoTotalNFRemessa;
	private JTextField entNomeRemetenteNFVenda;
	private JTextField entNomeDestinatarioNFVenda;
	private JTextField entNomeRemetenteNFRemessa;
	private JTextField entNomeDestinatarioNFRemessa;
	private JTextField entCodigoNFVenda;
	private JTextField entCodigoNFRemessa;
	private JTextField entCodigoRomaneio;

	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaControleNotasFiscais(Window janela_pai) {

		// setModal(true);
		// setAlwaysOnTop(true);

		isto = this;
		setResizable(true);
		setTitle("E-Contract - Recebimentos");

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

		sorter = new TableRowSorter<RecebimentoTableModel>(modelo_recebimentos);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[][660px][grow][grow]", "[133px][grow][31px][1px][12px][]"));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrincipal.add(panel_5, "cell 0 0 4 1,grow");
		panel_5.setLayout(new MigLayout("", "[58px][274px,grow][48px][306px,grow][90px][199px,grow][67px][126px][59px]", "[][][][28px][28px][28px]"));

		JLabel lblCdigoNfVenda = new JLabel("Código NF Venda:");
		lblCdigoNfVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblCdigoNfVenda, "cell 0 0,alignx trailing");

		entCodigoNFVenda = new JTextField();
		entCodigoNFVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			filtrar();
			calcular();
			}
		});
		entCodigoNFVenda.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCodigoNFVenda.setColumns(10);
		panel_5.add(entCodigoNFVenda, "cell 1 0,growx");

		JLabel lblRemetenteNfRemessa = new JLabel("Remetente NF Venda:");
		lblRemetenteNfRemessa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblRemetenteNfRemessa, "cell 2 0,alignx trailing");

		entNomeRemetenteNFVenda = new JTextField();
		entNomeRemetenteNFVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeRemetenteNFVenda.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeRemetenteNFVenda.setColumns(10);
		panel_5.add(entNomeRemetenteNFVenda, "cell 3 0 2 1,growx");

		JLabel lblDestinatarioNfRemessa = new JLabel("Destinatario NF Venda:");
		lblDestinatarioNfRemessa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblDestinatarioNfRemessa, "cell 5 0,alignx trailing");

		entNomeDestinatarioNFVenda = new JTextField();
		entNomeDestinatarioNFVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeDestinatarioNFVenda.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeDestinatarioNFVenda.setColumns(10);
		panel_5.add(entNomeDestinatarioNFVenda, "cell 6 0 3 1,growx");

		JLabel lblCdigoNfRemessa = new JLabel("Código NF Remessa");
		lblCdigoNfRemessa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblCdigoNfRemessa, "cell 0 1,alignx trailing");

		entCodigoNFRemessa = new JTextField();
		entCodigoNFRemessa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entCodigoNFRemessa.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entCodigoNFRemessa.setColumns(10);
		panel_5.add(entCodigoNFRemessa, "cell 1 1,growx");

		JLabel lblRemetenteNfRemessa_3 = new JLabel("Remetente NF Remessa:");
		lblRemetenteNfRemessa_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblRemetenteNfRemessa_3, "cell 2 1,alignx trailing");

		entNomeRemetenteNFRemessa = new JTextField();
		entNomeRemetenteNFRemessa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeRemetenteNFRemessa.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeRemetenteNFRemessa.setColumns(10);
		panel_5.add(entNomeRemetenteNFRemessa, "cell 3 1 2 1,growx");

		JLabel lblDestinatarioNfRemessa_2 = new JLabel("Destinatario NF Remessa:");
		lblDestinatarioNfRemessa_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblDestinatarioNfRemessa_2, "cell 5 1,alignx trailing");

		entNomeDestinatarioNFRemessa = new JTextField();
		entNomeDestinatarioNFRemessa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
				calcular();
			}
		});
		entNomeDestinatarioNFRemessa.setFont(new Font("SansSerif", Font.PLAIN, 16));
		entNomeDestinatarioNFRemessa.setColumns(10);
		panel_5.add(entNomeDestinatarioNFRemessa, "cell 6 1 3 1,growx");

		JLabel lblRemetenteNfRemessa_1 = new JLabel((String) null);
		lblRemetenteNfRemessa_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblRemetenteNfRemessa_1, "cell 0 2");

		JLabel lblCdigo = new JLabel("Código Contrato:");
		panel_5.add(lblCdigo, "cell 0 3,alignx right,aligny center");
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
		panel_5.add(entCodigo, "cell 1 3,growx,aligny top");
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
		panel_5.add(lblCdigoRomaneio, "cell 2 3,alignx trailing");
		
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
		panel_5.add(entCodigoRomaneio, "cell 3 3,growx");
		entNomeVendedor.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entNomeVendedor, "cell 3 4 2 1,growx,aligny top");
		entNomeVendedor.setColumns(10);

		JLabel lblNewLabel = new JLabel("Comprador:");
		panel_5.add(lblNewLabel, "cell 0 4,alignx right,aligny center");
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
		panel_5.add(entNomeComprador, "cell 1 4,growx,aligny top");
		entNomeComprador.setColumns(10);

		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1, "cell 5 3,alignx right");

		cbStatus = new JComboBox();
		cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbStatus.addItem("TODOS");
		cbStatus.addItem("OK");
		cbStatus.addItem("FALTA NF VENDA");
		cbStatus.addItem("FALTA NF REMESSA E VENDA");
		cbStatus.addItem("FALTA NF REMESSA");

		panel_5.add(cbStatus, "cell 6 3 3 1,growx");

		JLabel lblVendedor = new JLabel("Vendedor:");
		panel_5.add(lblVendedor, "cell 2 4,alignx right,aligny center");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblProduto = new JLabel("Produto:");
		panel_5.add(lblProduto, "cell 0 5,alignx right,aligny center");
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
		panel_5.add(entProduto, "cell 1 5,growx,aligny top");
		entProduto.setColumns(10);

		JLabel lblSafra = new JLabel("Safra:");
		panel_5.add(lblSafra, "cell 2 5,alignx right,aligny center");
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
		panel_5.add(entSafra, "cell 3 5,growx,aligny top");
		entSafra.setColumns(10);

		JLabel lblTransgnese = new JLabel("Transgênese:");
		panel_5.add(lblTransgnese, "cell 4 5,alignx right,aligny center");
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
		panel_5.add(entTransgenia, "cell 5 5,growx,aligny top");
		entTransgenia.setColumns(10);

		JButton btnLimparFiltros = new JButton("Limpar");
		btnLimparFiltros.setBackground(new Color(204, 51, 0));
		btnLimparFiltros.setForeground(Color.WHITE);
		btnLimparFiltros.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnLimparFiltros, "cell 6 5,alignx left,aligny top");
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
		panel_5.add(btnFiltrar, "cell 7 5,growx,aligny top");

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 51, 0));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnRefazerPesquisa, "cell 8 5,alignx left,aligny top");
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
		RecebimentoCellRender renderer_recebimentos = new RecebimentoCellRender();

		tabela = new JTable(modelo_recebimentos);
		tabela.setDefaultRenderer(Object.class, renderer_recebimentos);

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
		panel_8.setLayout(new MigLayout("", "[27.00][26.00][][31.00,grow][][grow][grow][grow][grow]", "[][][][][][][][]"));

		JLabel ads = new JLabel("Total Recebimentos:");
		ads.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(ads, "cell 1 0");

		lblTotalRecebimentos = new JLabel("0000");
		lblTotalRecebimentos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalRecebimentos, "cell 2 0 2 1,growx");

		JLabel lblNewLabel_33_2 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_2, "cell 0 1,grow");
		lblNewLabel_33_2.setOpaque(true);
		lblNewLabel_33_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_2.setBackground(Color.green);

		JLabel lblNewLabel_34_2 = new JLabel("OK");
		panel_8.add(lblNewLabel_34_2, "cell 1 1,alignx left");
		lblNewLabel_34_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblTotalRecebimentosOk = new JLabel("0000");
		lblTotalRecebimentosOk.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalRecebimentosOk, "cell 2 1 2 1");

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

		JLabel lblNewLabel_34_2_1 = new JLabel("Falta NF Venda");
		panel_8.add(lblNewLabel_34_2_1, "cell 1 2,alignx left");
		lblNewLabel_34_2_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_2_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblTotalRecebimentosNFVenda = new JLabel("0000");
		lblTotalRecebimentosNFVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalRecebimentosNFVenda, "cell 2 2 2 1");

		JLabel lblPesoTotalNf1 = new JLabel("Peso Total NF Venda:");
		lblPesoTotalNf1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf1, "cell 4 2");

		lblPesoTotalNFVenda = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalNFVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFVenda.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFVenda, "cell 5 2 2 1");
		
		JLabel lblValorTotalNf = new JLabel("Valor Total NF Venda:");
		lblValorTotalNf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblValorTotalNf, "cell 7 2,alignx right");
		
		 lblValorTotalNFVenda = new JLabel("R$ 0.0");
		lblValorTotalNFVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblValorTotalNFVenda.setBorder(null);
		panel_8.add(lblValorTotalNFVenda, "cell 8 2,growx");

		JLabel lblNewLabel_33_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1, "cell 0 3,growx");
		lblNewLabel_33_1_1.setOpaque(true);
		lblNewLabel_33_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1.setBackground(Color.gray);

		JLabel lblNewLabel_34_1_1 = new JLabel("Falta NF Remessa e Venda");
		panel_8.add(lblNewLabel_34_1_1, "cell 1 3,alignx left");
		lblNewLabel_34_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblTotalRecebimentosNFRemessaVenda = new JLabel("0000");
		lblTotalRecebimentosNFRemessaVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalRecebimentosNFRemessaVenda, "cell 2 3 2 1");

		JLabel lblPesoTotalNf_2 = new JLabel("Peso Total NF Remessa:");
		lblPesoTotalNf_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf_2, "cell 4 3");

		lblPesoTotalNFRemessa = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalNFRemessa.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFRemessa.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFRemessa, "cell 5 3 2 1");
		
		JLabel lblValorTotalNf_2 = new JLabel("Valor Total NF Remessa:");
		lblValorTotalNf_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblValorTotalNf_2, "cell 7 3,alignx right");
		
		 lblValorTotalNFRemessa = new JLabel("R$ 0.0");
		lblValorTotalNFRemessa.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblValorTotalNFRemessa.setBorder(null);
		panel_8.add(lblValorTotalNFRemessa, "cell 8 3");

		JLabel lblNewLabel_33_1_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1_1, "cell 0 4,growx");
		lblNewLabel_33_1_1_1.setOpaque(true);
		lblNewLabel_33_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1_1.setBackground(Color.YELLOW);

		JLabel lblNewLabel_34_1_1_1 = new JLabel("Falta NF Remessa");
		panel_8.add(lblNewLabel_34_1_1_1, "cell 1 4,alignx left");
		lblNewLabel_34_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_1_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblFaltaNFRemessa = new JLabel("0000");
		lblFaltaNFRemessa.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblFaltaNFRemessa, "cell 2 4 2 1");
		
				JButton btnExportar = new JButton("Exportar");
				btnExportar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						ArrayList<RecebimentoCompleto> notas_selecionadas = new ArrayList<>();
						int linhas_selecionadas[] = tabela.getSelectedRows();// pega o indice da linha na tabela

						for (int i = 0; i < linhas_selecionadas.length; i++) {

							int indice = linhas_selecionadas[i];//
							int indexRowModel = tabela.getRowSorter().convertRowIndexToModel(indice);

							RecebimentoCompleto rec = lista_recebimentos.get(indexRowModel);
							notas_selecionadas.add(rec);
						}
						RelatorioNotasFiscais relatorio_excel = new RelatorioNotasFiscais(notas_selecionadas);

						gerarExcel(relatorio_excel.prepararExcel());

						;

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

				int rowSel = tabela.getSelectedRow();// pega o indice da linha na tabela
				int indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);
				int id_contrato_selecionado = lista_recebimentos.get(indiceDaLinha).getId_contrato_recebimento();

				GerenciarBancoContratos gerenciar_cont = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar_cont.getContrato(id_contrato_selecionado);
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado, isto);

				// isto.dispose();
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
		lista_recebimentos.clear();
		modelo_recebimentos.onRemoveAll();

		NumberFormat z = NumberFormat.getNumberInstance();
		ArrayList<RecebimentoCompleto> recebimentos = gerenciar.getRecebimentos();

		Set<String> set = new HashSet<>(recebimentos.size());
		recebimentos.removeIf(p -> !set.add(p.getCodigo_nf_venda()));

		// recebimentos.stream().filter(p ->
		// set.add(p.getCodigo_nf_venda())).collect(Collectors.toList());

		for (RecebimentoCompleto recebimento : recebimentos) {
			CadastroContrato contrato = recebimento.getContrato();
			modelo_recebimentos.onAdd(recebimento);
			lista_recebimentos.add(recebimento);

		}

	}

	public void calcular() {

		Locale ptBr = new Locale("pt", "BR");
		NumberFormat z = NumberFormat.getNumberInstance();
		
		
		int num_recebimentos = 0;
		double peso_total_romaneios = 0, peso_total_nf_venda = 0, peso_total_nf_remessa = 0;
		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		int numero_recebimentos = 0;
		int recebimentos_ok = 0;
		int recebimentos_falta_nf_venda = 0;
		int recebimentos_falta_nf_remessa = 0;
		int recebimentos_falta_nf_venda_remessa = 0;

		for (int row = 0; row < tabela.getRowCount(); row++) {

			int index = tabela.convertRowIndexToModel(row);
			RecebimentoCompleto recebimento = modelo_recebimentos.getValue(index);

			String codigo_nf_venda = recebimento.getCodigo_nf_venda();
			String codigo_nf_remessa = recebimento.getCodigo_nf_remessa();

			peso_total_romaneios += recebimento.getPeso_romaneio();
			peso_total_nf_venda += recebimento.getPeso_nf_venda();
			peso_total_nf_remessa += recebimento.getPeso_nf_remessa();

			try {
				valor_total_nf_venda = valor_total_nf_venda.add(recebimento.getValor_nf_venda());
			}catch(Exception e) {
				
			}
			
			try {
				valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());
			}catch(Exception e) {
				
			}
			
			
			if (recebimento.getNf_venda_aplicavel() == 1 && recebimento.getNf_remessa_aplicavel() == 1) {

				if (checkString(codigo_nf_venda) && checkString(codigo_nf_remessa)) {
					// ok
					recebimentos_ok++;

				}

				else if (!(checkString(codigo_nf_venda)) && !(checkString(codigo_nf_remessa))) {
					// falta duas notas
					recebimentos_falta_nf_venda_remessa++;

				} else if (!(checkString(codigo_nf_venda)) && checkString(codigo_nf_remessa)) {
					// falta apenas nf de venda
					recebimentos_falta_nf_venda++;

				} else if (!(checkString(codigo_nf_remessa)) && checkString(codigo_nf_venda)) {
					// falta apenas nf remessa
					recebimentos_falta_nf_remessa++;

				}
			} else if (recebimento.getNf_venda_aplicavel() == 1 && recebimento.getNf_remessa_aplicavel() == 0) {
				// apenas de venda aplicavel
				if (checkString(codigo_nf_venda)) {
					// ok
					recebimentos_ok++;

				} else {
					recebimentos_falta_nf_venda++;
				}

			} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 1) {
				// apenas a de remessa e aplicavel
				if (checkString(codigo_nf_remessa)) {
					// ok
					recebimentos_ok++;
				} else {
					recebimentos_falta_nf_remessa++;

				}
			} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 0) {
				// nenhum aplicavel
				recebimentos_ok++;

			}
			num_recebimentos++;
		}

		lblTotalRecebimentos.setText(num_recebimentos + "");
		lblTotalRecebimentosOk.setText(recebimentos_ok + "");
		lblTotalRecebimentosNFVenda.setText(recebimentos_falta_nf_venda + "");
		lblTotalRecebimentosNFRemessaVenda.setText(recebimentos_falta_nf_venda_remessa + "");
		lblFaltaNFRemessa.setText(recebimentos_falta_nf_remessa + "");


		lblPesoTotalRomaneios
				.setText(z.format(peso_total_romaneios) + " Kgs | " + z.format(peso_total_romaneios / 60) + " Sacos");
		lblPesoTotalNFVenda
				.setText(z.format(peso_total_nf_venda) + " Kgs | " + z.format(peso_total_nf_venda / 60) + " Sacos");
		lblPesoTotalNFRemessa
				.setText(z.format(peso_total_nf_remessa) + " Kgs | " + z.format(peso_total_nf_remessa / 60) + " Sacos");
		
		String valorNFVendaString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda);
		String valorNFRemessaString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa);

		lblValorTotalNFVenda.setText(valorNFVendaString);
		lblValorTotalNFRemessa.setText(valorNFRemessaString);
		

	}

	public static boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void filtrar() {

		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
		ArrayList<RowFilter<Object, Object>> filters_nome_destinatario_nf_venda = new ArrayList<RowFilter<Object, Object>>(
				2);
		ArrayList<RowFilter<Object, Object>> filters_nome_remetente_nf_venda = new ArrayList<RowFilter<Object, Object>>(
				2);
		ArrayList<RowFilter<Object, Object>> filters_nome_remetente_nf_remessa = new ArrayList<RowFilter<Object, Object>>(
				2);
		ArrayList<RowFilter<Object, Object>> filters_nome_destinatario_nf_remessa = new ArrayList<RowFilter<Object, Object>>(
				2);
		ArrayList<RowFilter<Object, Object>> filters_produto = new ArrayList<RowFilter<Object, Object>>(2);

		RowFilter<Object, Object> destinatario_nf_venda_filters;
		RowFilter<Object, Object> destinatario_nf_remessa_filters;
		RowFilter<Object, Object> remetente_nf_venda_filters;
		RowFilter<Object, Object> remetente_nf_remessa_filters;

		RowFilter<Object, Object> produto_filters;

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String status = cbStatus.getSelectedItem().toString().toUpperCase();
		String transgenese = entTransgenia.getText().toUpperCase();

		String nome_remetente_nf_venda = entNomeRemetenteNFVenda.getText().toUpperCase();
		String nome_destinatari_nf_venda = entNomeDestinatarioNFVenda.getText().toUpperCase();
		String nome_remetente_nf_remessa = entNomeRemetenteNFRemessa.getText().toUpperCase();
		String nome_destinatari_nf_remessa = entNomeDestinatarioNFRemessa.getText().toUpperCase();

		String codigo_nf_venda = entCodigoNFVenda.getText().toUpperCase();
		String codigo_nf_remessa = entCodigoNFRemessa.getText().toUpperCase();
		
		String codigo_romaneio = entCodigoRomaneio.getText().toUpperCase();

		// filtrar codigo  romaneio
				if (checkString(codigo_romaneio))
					filters.add(RowFilter.regexFilter(codigo_romaneio, 2));
		
		// filtrar codigo nf venda
		if (checkString(codigo_nf_venda))
			filters.add(RowFilter.regexFilter(codigo_nf_venda, 4));

		// filtrar codigo nf venda
		if (checkString(codigo_nf_remessa))
			filters.add(RowFilter.regexFilter(codigo_nf_remessa, 9));

		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 16));

		if (checkString(comprador))
			filters.add(RowFilter.regexFilter(comprador, 17));

		if (checkString(vendedor))
			filters.add(RowFilter.regexFilter(vendedor, 18));

		// filtro de produto

		String produtos[] = produto.split(",");
		for (String prod : produtos) {
			filters_produto.add(RowFilter.regexFilter(prod, 19));

		}
		produto_filters = RowFilter.orFilter(filters_produto);

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 20));

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 21));

		if (checkString(status) && !(status.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(status, 14));

		// remetente da nf de venda
		String remetentes_nf_venda[] = nome_remetente_nf_venda.split(",");
		for (String nome : remetentes_nf_venda) {
			filters_nome_remetente_nf_venda.add(RowFilter.regexFilter(nome, 5));

		}
		remetente_nf_venda_filters = RowFilter.orFilter(filters_nome_remetente_nf_venda);

		// destinatario da nf de venda
		String destinatarios_nf_venda[] = nome_destinatari_nf_venda.split(",");
		for (String nome : destinatarios_nf_venda) {
			filters_nome_destinatario_nf_venda.add(RowFilter.regexFilter(nome, 6));

		}
		destinatario_nf_venda_filters = RowFilter.orFilter(filters_nome_destinatario_nf_venda);

		// remetente da nf de remessa
		String remetentes_nf_remessa[] = nome_remetente_nf_remessa.split(",");
		for (String nome : remetentes_nf_remessa) {
			filters_nome_remetente_nf_remessa.add(RowFilter.regexFilter(nome, 10));

		}
		remetente_nf_remessa_filters = RowFilter.orFilter(filters_nome_remetente_nf_remessa);

		// destinatario da nf de remessa
		String destinatarios_nf_remessa[] = nome_destinatari_nf_remessa.split(",");
		for (String nome : destinatarios_nf_remessa) {
			filters_nome_destinatario_nf_remessa.add(RowFilter.regexFilter(nome, 11));

		}
		destinatario_nf_remessa_filters = RowFilter.orFilter(filters_nome_destinatario_nf_remessa);

		filters.add(remetente_nf_venda_filters);
		filters.add(destinatario_nf_venda_filters);

		filters.add(remetente_nf_remessa_filters);
		filters.add(destinatario_nf_remessa_filters);
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

	public static class RecebimentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int id_recebimento = 0;
		private final int data = 1;
		private final int codigo_romaneio = 2;
		private final int peso_romaneio = 3;
		private final int codigo_nf_venda = 4;
		private final int remetente_nf_venda = 5;
		private final int destinatario_nf_venda = 6;
		private final int peso_nf_venda = 7;
		private final int valor_nf_venda = 8;
		private final int codigo_nf_remessa = 9;
		private final int remetente_nf_remessa = 10;
		private final int destinatario_nf_remessa = 11;
		private final int peso_nf_remessa = 12;
		private final int valor_nf_remessa = 13;
		private final int status = 14;
		private final int id_contrato = 15;
		private final int codigo_contrato = 16;
		private final int compradores = 17;
		private final int vendedores = 18;
		private final int produto = 19;
		private final int transgenia = 20;
		private final int safra = 21;

		private final String colunas[] = { "ID:", "Data:", "Código Romaneio:", "Peso Romaneio:", "Código NF Venda:",
				"Remetente NF Venda", "Destinatario NF Venda", "Peso NF Venda:", "Valor NF Venda:",
				"Código NF Remessa:", "Remetente NF Remessa", "Destinatario NF Remessa", "Peso NF Remessa:",
				"Valor NF Remessa:", "Status", "ID Contrato", "Codigo", "Compradores", "Vendedores", "Produto",
				"Transgenia", "Safra" };
		private final ArrayList<RecebimentoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																				// genérica de
		// nfs

		public RecebimentoTableModel() {

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

			case id_recebimento:
				return Integer.class;
			case data:
				return String.class;
			case codigo_romaneio:
				return String.class;
			case peso_romaneio:
				return String.class;
			case codigo_nf_venda:
				return String.class;
			case remetente_nf_venda:
				return String.class;
			case destinatario_nf_venda:
				return String.class;
			case peso_nf_venda:
				return String.class;
			case valor_nf_venda:
				return String.class;
			case codigo_nf_remessa:
				return String.class;
			case remetente_nf_remessa:
				return String.class;
			case destinatario_nf_remessa:
				return String.class;
			case peso_nf_remessa:
				return String.class;
			case valor_nf_remessa:
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
			RecebimentoCompleto recebimento = dados.get(rowIndex);
			CadastroContrato contrato = recebimento.getContrato();

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_recebimento:
				return recebimento.getId_recebimento();
			case data:
				return recebimento.getData_recebimento();

			case codigo_romaneio:
				return recebimento.getCodigo_romaneio();
			case peso_romaneio:
				double peso_romaneio = recebimento.getPeso_romaneio();
				return z.format(peso_romaneio) + " KGs";
			case codigo_nf_venda: {
				if (recebimento.getNf_venda_aplicavel() == 1) {
					return recebimento.getCodigo_nf_venda();

				} else {
					return "Não Aplicável";
				}
			}

			case remetente_nf_venda: {
				if (recebimento.getNf_venda_aplicavel() == 1) {
					String nome_remetente = recebimento.getNome_remetente_nf_venda();
					if (checkString(nome_remetente)) {
						return nome_remetente.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case destinatario_nf_venda: {
				if (recebimento.getNf_venda_aplicavel() == 1) {
					String nome_destinatario = recebimento.getNome_destinatario_nf_venda();
					if (checkString(nome_destinatario)) {
						return nome_destinatario.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case peso_nf_venda: {

				if (recebimento.getNf_venda_aplicavel() == 1) {
					double peso_nf_venda = recebimento.getPeso_nf_venda();
					return z.format(peso_nf_venda) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}
			case valor_nf_venda: {
				if (recebimento.getNf_venda_aplicavel() == 1) {
					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(recebimento.getValor_nf_venda());
					return valorString;

				} else {
					return "Não Aplicável";
				}
			}
			case codigo_nf_remessa: {
				if (recebimento.getNf_remessa_aplicavel() == 1) {
					return recebimento.getCodigo_nf_remessa();

				} else {
					return "Não Aplicável";
				}
			}

			case remetente_nf_remessa: {
				if (recebimento.getNf_remessa_aplicavel() == 1) {
					String nome_remetente = recebimento.getNome_remetente_nf_remessa();
					if (checkString(nome_remetente)) {
						return nome_remetente.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}
			case destinatario_nf_remessa: {
				if (recebimento.getNf_remessa_aplicavel() == 1) {
					String nome_destinatario = recebimento.getNome_destinatario_nf_remessa();
					if (checkString(nome_destinatario)) {
						return nome_destinatario.toUpperCase();
					} else {
						return "";
					}
				} else {
					return "";
				}
			}

			case peso_nf_remessa: {
				if (recebimento.getNf_remessa_aplicavel() == 1) {
					double peso_nf_remessa = recebimento.getPeso_nf_remessa();
					return z.format(peso_nf_remessa) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}
			case valor_nf_remessa: {
				if (recebimento.getNf_remessa_aplicavel() == 1) {
					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(recebimento.getValor_nf_remessa());
					return valorString;

				} else {
					return "Não Aplicável";
				}
			}
			case status: {
				String codigo_nf_venda = recebimento.getCodigo_nf_venda();
				String codigo_nf_remessa = recebimento.getCodigo_nf_remessa();

				if (recebimento.getNf_venda_aplicavel() == 1 && recebimento.getNf_remessa_aplicavel() == 1) {

					if (checkString(codigo_nf_venda) && checkString(codigo_nf_remessa)) {
						// ok
						return "OK";

					}

					else if (!(checkString(codigo_nf_venda)) && !(checkString(codigo_nf_remessa))) {
						// falta duas notas
						return "FALTA NF REMESSA E VENDA";

					} else if (!(checkString(codigo_nf_venda)) && checkString(codigo_nf_remessa)) {
						// falta apenas nf de venda
						return "FALTA NF VENDA";

					} else if (!(checkString(codigo_nf_remessa)) && checkString(codigo_nf_venda)) {
						// falta apenas nf remessa
						return "FALTA NF REMESSA";

					}
				} else if (recebimento.getNf_venda_aplicavel() == 1 && recebimento.getNf_remessa_aplicavel() == 0) {
					// apenas de venda aplicavel
					if (checkString(codigo_nf_venda)) {
						// ok
						return "OK";

					} else {
						return "FALTA NF VENDA";
					}

				} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 1) {
					// apenas a de remessa e aplicavel
					if (checkString(codigo_nf_remessa)) {
						// ok
						return "OK";
					} else {
						return "FALTA NF REMESSA";

					}
				} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 0) {
					// nenhum aplicavel
					return "OK";

				}

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
			CadastroContrato.Recebimento recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public RecebimentoCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato.Recebimento nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(RecebimentoCompleto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<RecebimentoCompleto> dadosIn) {
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
		public void onRemove(CadastroContrato.Recebimento nota) {
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

	class RecebimentoCellRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			RecebimentoCompleto recebimento = lista_recebimentos.get(row);

			String codigo_nf_venda = (String) table.getValueAt(row, 11);
			String codigo_nf_remessa = (String) table.getValueAt(row, 14);

			if (isSelected) {
				renderer.setBackground(Color.blue);

			} else {

				if (recebimento.getNf_venda_aplicavel() == 1 && recebimento.getNf_remessa_aplicavel() == 1) {

					if (checkString(codigo_nf_venda) && checkString(codigo_nf_remessa)) {
						// ok
						renderer.setBackground(Color.green);

					}

					else if (!(checkString(codigo_nf_venda)) && !(checkString(codigo_nf_remessa))) {
						// falta duas notas
						renderer.setBackground(Color.gray);

					} else if (!(checkString(codigo_nf_venda)) && checkString(codigo_nf_remessa)) {
						// falta apenas nf de venda
						renderer.setBackground(Color.orange);

					} else if (!(checkString(codigo_nf_remessa)) && checkString(codigo_nf_venda)) {
						// falta apenas nf remessa
						renderer.setBackground(Color.yellow);

					}
				} else if (recebimento.getNf_venda_aplicavel() == 1 && recebimento.getNf_remessa_aplicavel() == 0) {
					// apenas de venda aplicavel
					if (checkString(codigo_nf_venda)) {
						// ok
						renderer.setBackground(Color.green);

					} else {
						renderer.setBackground(Color.orange);

					}

				} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 1) {
					// apenas a de remessa e aplicavel
					if (checkString(codigo_nf_remessa)) {
						// ok
						renderer.setBackground(Color.green);

					} else {
						renderer.setBackground(Color.yellow);

					}
				} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 0) {
					// nenhum aplicavel
					renderer.setBackground(Color.green);

				}

			}

			return renderer;
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
