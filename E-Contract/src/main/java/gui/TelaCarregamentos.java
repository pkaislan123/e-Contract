
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

import javax.swing.table.TableCellRenderer;
import java.io.IOException;
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
import java.util.stream.IntStream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
import main.java.cadastros.CarregamentoCompleto;
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
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
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

public class TelaCarregamentos extends JFrame {

	private static ArrayList<CarregamentoCompleto> lista_carregamentos = new ArrayList<>();
	private JDialog telaPai;
	private boolean finalizado = false;
	private JTable tabela;
	private boolean nulo = false;
	private File file_selecionado;
	private final JPanel painelPrincipal = new JPanel();

	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private TelaCarregamentos isto;
	private JTextField entNomeComprador;
	private JTextField entNomeVendedor;
	private JComboBox cbStatus;

	private CarregamentoTableModel modelo_carregamentos = new CarregamentoTableModel();
	private TableRowSorter<CarregamentoTableModel> sorter;
	private JTextField entProduto;
	private JTextField entSafra;
	private JTextField entCodigo;
	private JTextField entStatus;
	private int id_contrato_pai_para_replica_global = 0;

	private int flag_retorno_global;
	private JTextField entTransgenia;
	private FileChooser d;
	private JLabel lblTotalCarregamentos, lblTotalCarregamentosOk, lblTotalCarregamentosNFVenda,
			lblTotalCarregamentosNFRemessaVenda, lblFaltaNFRemessa;
	private JLabel lblPesoTotalRomaneios, lblPesoTotalNFVenda, lblPesoTotalNFRemessa;

	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaCarregamentos(Window janela_pai) {

		// setModal(true);
		// setAlwaysOnTop(true);

		isto = this;
		setResizable(true);
		setTitle("E-Contract - Carregamentos");

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
		panel_5.setLayout(new MigLayout("", "[58px][274px][48px][306px][90px][199px,grow][67px][126px][59px]",
				"[28px][28px][28px]"));

		JLabel lblNewLabel = new JLabel("Comprador:");
		panel_5.add(lblNewLabel, "cell 0 0,alignx right,aligny center");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeComprador = new JTextField();
		entNomeComprador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entNomeComprador, "cell 1 0,growx,aligny top");
		entNomeComprador.setColumns(10);

		JLabel lblCdigo = new JLabel("Código:");
		panel_5.add(lblCdigo, "cell 2 0,alignx right,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entCodigo = new JTextField();
		entCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entCodigo, "cell 3 0,growx,aligny top");
		entCodigo.setColumns(10);

		JLabel lblVendedor = new JLabel("Vendedor:");
		panel_5.add(lblVendedor, "cell 0 1,alignx right,aligny center");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeVendedor = new JTextField();
		entNomeVendedor.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entNomeVendedor, "cell 1 1,growx,aligny top");
		entNomeVendedor.setColumns(10);

		JLabel lblStatus = new JLabel("Status:");
		panel_5.add(lblStatus, "cell 2 1,alignx right,aligny center");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entStatus = new JTextField();
		entStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entStatus, "cell 3 1,growx,aligny top");
		entStatus.setColumns(10);

		JLabel lblStatus_1 = new JLabel("Status:");
		lblStatus_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatus_1, "cell 4 1,alignx right");

		cbStatus = new JComboBox();
		cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbStatus.addItem("TODOS");
		cbStatus.addItem("OK");
		cbStatus.addItem("FALTA NF VENDA");
		cbStatus.addItem("FALTA NF REMESSA E VENDA");
		cbStatus.addItem("FALTA NF REMESSA");

		panel_5.add(cbStatus, "cell 5 1,growx");

		JLabel lblProduto = new JLabel("Produto:");
		panel_5.add(lblProduto, "cell 0 2,alignx right,aligny center");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entProduto = new JTextField();
		entProduto.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entProduto, "cell 1 2,growx,aligny top");
		entProduto.setColumns(10);

		JLabel lblSafra = new JLabel("Safra:");
		panel_5.add(lblSafra, "cell 2 2,alignx right,aligny center");
		lblSafra.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entSafra = new JTextField();
		entSafra.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entSafra, "cell 3 2,growx,aligny top");
		entSafra.setColumns(10);

		JLabel lblTransgnese = new JLabel("Transgênese:");
		panel_5.add(lblTransgnese, "cell 4 2,alignx right,aligny center");
		lblTransgnese.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entTransgenia = new JTextField();
		entTransgenia.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(entTransgenia, "cell 5 2,growx,aligny top");
		entTransgenia.setColumns(10);

		JButton btnLimparFiltros = new JButton("Limpar");
		btnLimparFiltros.setBackground(new Color(204, 51, 0));
		btnLimparFiltros.setForeground(Color.WHITE);
		btnLimparFiltros.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnLimparFiltros, "cell 6 2,alignx left,aligny top");
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
		panel_5.add(btnFiltrar, "cell 7 2,growx,aligny top");

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 51, 0));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_5.add(btnRefazerPesquisa, "cell 8 2,alignx left,aligny top");
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
		painelPrincipal.add(panel_8, "cell 1 5,grow");
		panel_8.setBackground(Color.WHITE);
		panel_8.setLayout(new MigLayout("", "[27.00][26.00][][31.00][][][][][]", "[][][][][][][][]"));

		JLabel ads = new JLabel("Total Carregamentos:");
		ads.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(ads, "cell 1 0");

		lblTotalCarregamentos = new JLabel("0000");
		lblTotalCarregamentos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalCarregamentos, "cell 2 0");

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
		panel_8.add(lblTotalCarregamentosOk, "cell 2 1");

		JLabel lblPesoTotalRomaneios12 = new JLabel("Peso Total Romaneios:");
		lblPesoTotalRomaneios12.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalRomaneios12, "cell 4 1");

		
		
		 lblPesoTotalRomaneios = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalRomaneios.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalRomaneios.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalRomaneios, "cell 5 1 4 1");

		JLabel lblNewLabel_33_2_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_2_1, "cell 0 2,growx");
		lblNewLabel_33_2_1.setOpaque(true);
		lblNewLabel_33_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_2_1.setBackground(Color.ORANGE);

		JLabel lblNewLabel_34_2_1 = new JLabel("Falta NF Venda ");
		panel_8.add(lblNewLabel_34_2_1, "cell 1 2,alignx left");
		lblNewLabel_34_2_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_2_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblTotalCarregamentosNFVenda = new JLabel("0000");
		lblTotalCarregamentosNFVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalCarregamentosNFVenda, "cell 2 2");

		JLabel lblPesoTotalNf1 = new JLabel("Peso Total NF Venda:");
		lblPesoTotalNf1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf1, "cell 4 2");

		 lblPesoTotalNFVenda = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalNFVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFVenda.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFVenda, "cell 5 2");

		JLabel lblNewLabel_33_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1, "cell 0 3,growx");
		lblNewLabel_33_1_1.setOpaque(true);
		lblNewLabel_33_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1.setBackground(Color.gray);

		JLabel lblNewLabel_34_1_1 = new JLabel("Falta NF Remessa e Venda");
		panel_8.add(lblNewLabel_34_1_1, "cell 1 3,alignx left");
		lblNewLabel_34_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		lblTotalCarregamentosNFRemessaVenda = new JLabel("0000");
		lblTotalCarregamentosNFRemessaVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalCarregamentosNFRemessaVenda, "cell 2 3");

		JLabel lblPesoTotalNf_2 = new JLabel("Peso Total NF Remessa:");
		lblPesoTotalNf_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_8.add(lblPesoTotalNf_2, "cell 4 3");

		 lblPesoTotalNFRemessa = new JLabel("000.000.000.000,00/000.000,00");
		lblPesoTotalNFRemessa.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblPesoTotalNFRemessa.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_8.add(lblPesoTotalNFRemessa, "cell 5 3");

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
		panel_8.add(lblFaltaNFRemessa, "cell 2 4");

		JButton btnAbrir = new JButton("Abrir");
		painelPrincipal.add(btnAbrir, "cell 3 5,alignx right,aligny top");
		btnAbrir.setForeground(Color.WHITE);
		btnAbrir.setBackground(new Color(0, 0, 153));
		btnAbrir.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = tabela.getSelectedRow();

				int id_contrato_selecionado = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
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
		lista_carregamentos.clear();
		modelo_carregamentos.onRemoveAll();

		NumberFormat z = NumberFormat.getNumberInstance();

		for (CarregamentoCompleto carga : gerenciar.getCarregamentos()) {
			modelo_carregamentos.onAdd(carga);
			lista_carregamentos.add(carga);

		}

	}

	public void calcular() {
		
		
		
		
	}

	public static boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void filtrar() {
		/*
		 * // constantes p/identificar colunas private final int id_contrato = 0;
		 * private final int codigo_contrato = 1; private final int compradores = 2;
		 * private final int vendedores = 3; private final int produto = 4; private
		 * final int transgenia = 5; private final int safra = 6; private final int
		 * id_romaneio = 7; private final int data = 8; private final int
		 * codigo_romaneio = 9;
		 * 
		 * private final int peso_romaneio = 10; private final int codigo_nf_venda = 11;
		 * private final int peso_nf_venda = 12; private final int valor_nf_venda = 13;
		 * 
		 * private final int codigo_nf_remessa = 14; private final int peso_nf_remessa =
		 * 15; private final int valor_nf_remessa = 16;
		 */
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String status = cbStatus.getSelectedItem().toString().toUpperCase();
		String transgenese = entTransgenia.getText().toUpperCase();

		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 1));

		if (checkString(comprador))
			filters.add(RowFilter.regexFilter(comprador, 2));

		if (checkString(vendedor))
			filters.add(RowFilter.regexFilter(vendedor, 3));

		if (checkString(produto))
			filters.add(RowFilter.regexFilter(produto, 4));

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 5));

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 6));

		if (checkString(status) && !(status.equalsIgnoreCase("TODOS")))
			filters.add(RowFilter.regexFilter(status, 19));

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
		private final int id_contrato = 0;
		private final int codigo_contrato = 1;
		private final int compradores = 2;
		private final int vendedores = 3;
		private final int produto = 4;
		private final int transgenia = 5;
		private final int safra = 6;
		private final int id_romaneio = 7;
		private final int data = 8;
		private final int codigo_romaneio = 9;

		private final int peso_romaneio = 10;
		
		private final int codigo_nf_venda1 = 11;
		private final int peso_nf_venda1 = 12;
		private final int valor_nf_venda1 = 13;

		private final int codigo_nf_compl = 14;
		private final int peso_nf_compl  = 15;
		private final int valor_nf_compl  = 16;
		
		private final int codigo_nf_interna = 17;
		private final int peso_nf_interna  = 18;
		private final int status = 19;

		private final String colunas[] = { "ID Contrato", "Codigo", "Compradores", "Vendedores", "Produto",
				"Transgenia", "Safra", "ID:", "Data:", "Código Romaneio:", "Peso Romaneio:", "Código NF Venda 1:",
				"Peso NF Venda 1:", "Valor NF Venda 1:", "Código NF Venda Compl.:", "Peso NF Compl.:", "Valor NF Compl.:",
				 "Código NF Interna.:", "Peso NF Interna.:", "Status" };
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
			case id_contrato:
				return int.class;
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
			case id_romaneio:
				return int.class;
			case data:
				return String.class;
			case codigo_romaneio:
				return String.class;
			case peso_romaneio:
				return String.class;
			case codigo_nf_venda1:
				return String.class;
			case peso_nf_venda1:
				return String.class;
			case valor_nf_venda1:
				return String.class;
				
			case codigo_nf_compl:
				return String.class;
			case peso_nf_compl:
				return String.class;
			case valor_nf_compl:
				return String.class;
				
			case codigo_nf_interna:
				return String.class;
			case peso_nf_interna:
				return String.class;
		
			case status:
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
			case id_contrato: {
				return contrato.getId();
			}
			case codigo_contrato: {
				return contrato.getCodigo();
			}
			case compradores: {
				return contrato.getNomes_compradores();
			}
			case vendedores: {
				return contrato.getNomes_vendedores();
			}
			case produto: {
				return contrato.getModelo_produto().getNome_produto();
			}
			case transgenia: {
				return contrato.getModelo_produto().getTransgenia();
			}
			case safra: {
				return contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita();
			}
			case id_romaneio:
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
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(carregamento.getValor_nf_venda1());
					return valorString;

				} else {
					return "Não Aplicável";
				}
			}
			case codigo_nf_compl: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					return carregamento.getCodigo_nf_complemento();

				} else {
					return "Não Aplicável";
				}
			}
			case peso_nf_compl: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					double peso_nf_remessa = carregamento.getPeso_nf_complemento();
					return z.format(peso_nf_remessa) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}
			case valor_nf_compl: {
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
					double peso_nf_remessa = carregamento.getPeso_nf_interna();
					return z.format(peso_nf_remessa) + " KGs";

				} else {
					return "Não Aplicável";
				}
			}
			
			
			case status: {
				
				return "";
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
			CarregamentoCompleto recebimento = dados.get(rowIndex);

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
		public int indexOf(CarregamentoCompleto nota) {
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
		public void onRemove(CarregamentoCompleto nota) {
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



}
