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
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
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
import java.util.stream.IntStream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Desktop;
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
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

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
import main.java.cadastros.PagamentoCompleto;
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
import java.awt.Point;
import java.awt.GridLayout;
import javax.swing.JComboBox;

public class TelaPagamentos extends JFrame {

	private static ArrayList<PagamentoCompleto> lista_pagamentos = new ArrayList<>();
	private JDialog telaPai;
	NumberFormat z = NumberFormat.getNumberInstance();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

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
	static Locale ptBr = new Locale("pt", "BR");

	private TelaPagamentos isto;
	private JTextField entNomeComprador;
	private JTextField entNomeVendedor;

	private PagamentoTableModel modelo_pagamentos = new PagamentoTableModel();
	private TableRowSorter<PagamentoTableModel> sorter;
	private JTextField entProduto;
	private JTextField entSafra;
	private JTextField entCodigo;
	private JTextField entStatus;
	private int id_contrato_pai_para_replica_global = 0;
	private JComboBox cbGrupoParticular;

	private int flag_retorno_global;
	private JTextField entTransgenia;
	private FileChooser d;
	private JLabel lblTotalPagamentos,lblValorTotalPagamentos,lblTotalCobertura;
	private JTextField entTipoPagamento;
	private JTextField entDepositante;
	private JTextField entFavorecido;
	
	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaPagamentos(Window janela_pai) {

		// setModal(true);
		// setAlwaysOnTop(true);
		getDadosGlobais();
		isto = this;
		// setResizable(false);
		setTitle("E-Contract - Pagamentos");

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

		EvenOddRenderer renderer = new EvenOddRenderer();
		sorter = new TableRowSorter<PagamentoTableModel>(modelo_pagamentos);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[grow][grow][grow]", "[88px][grow][grow]"));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrincipal.add(panel_5, "cell 0 0 3 1,alignx center,growy");
		panel_5.setLayout(new MigLayout("", "[58px][274px,grow][48px][306px][90px][199px,grow][67px][126px][59px]", "[][][][][][28px][28px][28px]"));
		
		JLabel lblNewLabel_1_1 = new JLabel("Filtros relacionados a pagamento");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panel_5.add(lblNewLabel_1_1, "cell 0 0 9 1");
		
				JLabel lblStatus_1 = new JLabel("Tipo:");
				lblStatus_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel_5.add(lblStatus_1, "cell 0 1,alignx trailing");
		
		entTipoPagamento = new JTextField();
		entTipoPagamento.setColumns(10);
		panel_5.add(entTipoPagamento, "cell 1 1,growx");
		
		JLabel lblDepositante = new JLabel("Depositante:");
		lblDepositante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblDepositante, "cell 0 2,alignx trailing");
		
		entDepositante = new JTextField();
		entDepositante.setColumns(10);
		panel_5.add(entDepositante, "cell 1 2,growx");
		
		JLabel lblFavorecido = new JLabel("Favorecido:");
		lblFavorecido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblFavorecido, "cell 0 3,alignx trailing");
		
		entFavorecido = new JTextField();
		entFavorecido.setColumns(10);
		panel_5.add(entFavorecido, "cell 1 3,growx");
		
		JLabel lblNewLabel_1 = new JLabel("Filtros relacionados a contrato");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panel_5.add(lblNewLabel_1, "cell 0 4 9 1");

		JLabel lblNewLabel = new JLabel("Comprador:");
		panel_5.add(lblNewLabel, "cell 0 5,alignx left,aligny center");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeComprador = new JTextField();
		panel_5.add(entNomeComprador, "cell 1 5,growx,aligny top");
		entNomeComprador.setColumns(10);

		JLabel lblCdigo = new JLabel("Código:");
		panel_5.add(lblCdigo, "cell 2 5,alignx left,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entCodigo = new JTextField();
		panel_5.add(entCodigo, "cell 3 5,growx,aligny top");
		entCodigo.setColumns(10);
		
		JLabel lblGrupoparticular = new JLabel("Grupo/Particular:");
		lblGrupoparticular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblGrupoparticular, "cell 4 5,alignx trailing");
		
		 cbGrupoParticular = new JComboBox();
		 cbGrupoParticular.addItem("TODOS");
		 cbGrupoParticular.addItem("GRUPO");
		 cbGrupoParticular.addItem("PARTICULAR");
		panel_5.add(cbGrupoParticular, "cell 5 5,growx");

		JLabel lblVendedor = new JLabel("Vendedor:");
		panel_5.add(lblVendedor, "cell 0 6,alignx left,aligny center");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeVendedor = new JTextField();
		panel_5.add(entNomeVendedor, "cell 1 6,growx,aligny top");
		entNomeVendedor.setColumns(10);

		JLabel lblStatus = new JLabel("Status:");
		panel_5.add(lblStatus, "cell 2 6,alignx left,aligny center");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entStatus = new JTextField();
		panel_5.add(entStatus, "cell 3 6,growx,aligny top");
		entStatus.setColumns(10);

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 0, 153));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_5.add(btnRefazerPesquisa, "cell 7 6,growx,aligny top");
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
				calcular();
			}
		});

		JLabel lblProduto = new JLabel("Produto:");
		panel_5.add(lblProduto, "cell 0 7,alignx left,aligny center");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entProduto = new JTextField();
		panel_5.add(entProduto, "cell 1 7,growx,aligny top");
		entProduto.setColumns(10);

		JLabel lblSafra = new JLabel("Safra:");
		panel_5.add(lblSafra, "cell 2 7,alignx left,aligny center");
		lblSafra.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entSafra = new JTextField();
		panel_5.add(entSafra, "cell 3 7,growx,aligny top");
		entSafra.setColumns(10);

		JLabel lblTransgnese = new JLabel("Transgênese:");
		panel_5.add(lblTransgnese, "cell 4 7,alignx left,aligny center");
		lblTransgnese.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entTransgenia = new JTextField();
		panel_5.add(entTransgenia, "cell 5 7,growx,aligny top");
		entTransgenia.setColumns(10);

		JButton btnLimparFiltros = new JButton("Limpar Busca");
		btnLimparFiltros.setBackground(new Color(255, 153, 0));
		btnLimparFiltros.setForeground(Color.WHITE);
		btnLimparFiltros.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_5.add(btnLimparFiltros, "cell 6 7,growx,aligny top");
		btnLimparFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));
				calcular();
			}
		});

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 0, 153));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_5.add(btnFiltrar, "cell 8 7,growx,aligny top");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
				calcular();
			}
		});

		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 1 3 1,grow");

		tabela = new JTable(modelo_pagamentos);
		tabela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(7).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(8).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(9).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(10).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(11).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(12).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(13).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(14).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(15).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(16).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(17).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(18).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(19).setPreferredWidth(100);

		tabela.setRowSorter(sorter);

		tabela.setBackground(new Color(255, 255, 255));
		// tabela.setPreferredSize(new Dimension(0, 200));

		tabela.setRowHeight(30);

		
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItemVizualizar = new JMenuItem();

		jMenuItemVizualizar.setText("Vizualizar");

		jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
			
				

            	int indiceDaLinha = tabela.getSelectedRow();
            	PagamentoCompleto pag = (PagamentoCompleto) modelo_pagamentos.getValue(indiceDaLinha);

            	String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_completo = unidade_base_dados + "\\" + pag.getContrato_remetente().getCaminho_diretorio_contrato()
						+ "\\" + "comprovantes\\" + pag.getNome_comprovante();
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop desktop = Desktop.getDesktop();
						File myFile = new File(caminho_completo);
						desktop.open(myFile);
					} catch (IOException ex) {
					}
				}
            	
			
			}
		});

		

		jPopupMenu.add(jMenuItemVizualizar);

		tabela.setComponentPopupMenu(jPopupMenu);

		
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// pesquisar();
			}
		});
		panel.setLayout(new BorderLayout(0, 0));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setAutoscrolls(true);

		panel.add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 0 2,grow");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelPrincipal.add(panel_3, "cell 2 2,alignx right,growy");
		panel_3.setLayout(new MigLayout("", "[97px]", "[50px]"));

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_3.add(panel_4, "cell 0 0,alignx right,growy");
		panel_4.setLayout(new MigLayout("", "[83px]", "[33px]"));

		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			/*	
				int rowSel = -1;
				int indiceDaLinha = -1;
				
				 rowSel = tabela.getSelectedRow();//pega o indice da linha na tabela
				 indiceDaLinha = tabela.getRowSorter().convertRowIndexToModel(rowSel);//converte pro indice do model				
				

				int id_contrato_selecionado = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 10).toString());
				GerenciarBancoContratos gerenciar_cont = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar_cont.getContrato(id_contrato_selecionado);
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado, isto);
*/
				int indiceDaLinha = tabela.getSelectedRow();

				int id_contrato_selecionado = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 13).toString());
				GerenciarBancoContratos gerenciar_cont = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar_cont.getContrato(id_contrato_selecionado);

				new TelaGerenciarContrato(contrato_selecionado, null);
				
				//isto.dispose();
			}
		});
		panel_4.add(btnAbrir, "cell 0 0,alignx left,aligny top");
		btnAbrir.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		panel_1.setLayout(new MigLayout("", "[433px]", "[][139px]"));

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		panel_1.add(panel_8, "cell 0 1,alignx left,aligny top");
		panel_8.setLayout(new MigLayout("", "[27.00][26.00][][31.00][][][][][][][]", "[][][][][][][][]"));

		JLabel ads = new JLabel("N.º Pagamentos:");
		ads.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(ads, "cell 1 0,alignx right");

		 lblTotalPagamentos = new JLabel("0000");
		lblTotalPagamentos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblTotalPagamentos, "cell 2 0");

		JLabel lblNewLabel_34_2 = new JLabel("Valor Total Pagamentos Efetuados:");
		panel_8.add(lblNewLabel_34_2, "cell 1 1,alignx left");
		lblNewLabel_34_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel_34_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		 lblValorTotalPagamentos = new JLabel("0000");
		lblValorTotalPagamentos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_8.add(lblValorTotalPagamentos, "cell 2 1");
		 
		 		JLabel lblNewLabel_34_2_1 = new JLabel(" Quantidade Total de Sacos Pago:");
		 		panel_8.add(lblNewLabel_34_2_1, "cell 4 1,alignx left");
		 		lblNewLabel_34_2_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		 		lblNewLabel_34_2_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		
				 lblTotalCobertura = new JLabel("0000");
				 lblTotalCobertura.setFont(new Font("SansSerif", Font.BOLD, 16));
				 panel_8.add(lblTotalCobertura, "cell 5 1");

		pesquisar();
		calcular();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// this.pack();

		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_pagamentos.clear();
		modelo_pagamentos.onRemoveAll();

		NumberFormat z = NumberFormat.getNumberInstance();

		for (PagamentoCompleto pag : gerenciar.getPagamentosContratuaisParaRelatorio()) {
			modelo_pagamentos.onAdd(pag);
			lista_pagamentos.add(pag);

		}

	}

	public void calcular() {
		
		
		int numero_pagamentos = 0;
		BigDecimal valor_total_pagamenos = BigDecimal.ZERO;
		double quantidade_total_sacos_pago = 0;
		double cobertura_total = 0;

		for (int row = 0; row < tabela.getRowCount(); row++) {

			int index = tabela.convertRowIndexToModel(row);
			PagamentoCompleto pagamento = modelo_pagamentos.getValue(index);
			CadastroContrato contrato = pagamento.getContrato_remetente();
			valor_total_pagamenos = valor_total_pagamenos.add(new BigDecimal(pagamento.getValor_pagamento()));
			double cobertura = pagamento.getValor_pagamento() / contrato.getValor_produto();
			
			if(contrato.getMedida().equalsIgnoreCase("KG"))
				cobertura = cobertura / 60;
			
			cobertura_total += cobertura;
			
		}

		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamenos);

		lblTotalPagamentos.setText(tabela.getRowCount() + "");
		lblValorTotalPagamentos.setText(valorString);
		lblTotalCobertura.setText(z.format(cobertura_total) + " sacos | " + z.format(cobertura_total * 60) + " Kgs");
		
	}

	public static boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void filtrar() {
	/*
	 * private final int id_pagamento = 0;
		private final int data_pagamento = 1;
		private final int tipo = 2;
		private final int descricao = 3;
		private final int valor_pago = 4;
		private final int valor_produto = 5;
		private final int cobertura = 6;
		private final int depositante = 7;
		private final int conta_depositante = 8;
		private final int favorecido = 9;
		private final int conta_favorecido = 10;
		private final int contrato_remetente = 11;
		private final int contrato_destinatario = 12;
		private final int id_contrato = 13;
		private final int codigo_contrato = 14;
		private final int compradores = 15;
		private final int vendedores = 16;
		private final int produto = 17;
		private final int transgenia = 18;
		private final int safra = 19;
		private final int quantidade = 20;
		private final int valor_a_pagar = 21;
	 * 
	 */
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String transgenese = entTransgenia.getText().toUpperCase();
		String tipo_pagamento = entTipoPagamento.getText().toUpperCase();
		String depositante = entDepositante.getText().toUpperCase();
		String favorecido = entFavorecido.getText().toUpperCase();
		String grupo_particular = cbGrupoParticular.getSelectedItem().toString().toUpperCase();


		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 14));

		if (checkString(comprador))
			filters.add(RowFilter.regexFilter(comprador, 15));

		if (checkString(vendedor))
			filters.add(RowFilter.regexFilter(vendedor, 16));

		if (checkString(produto))
			filters.add(RowFilter.regexFilter(produto, 17));

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 18));

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 19));
		
		if(!(grupo_particular.equalsIgnoreCase("TODOS"))) {
		if (checkString(grupo_particular))
			filters.add(RowFilter.regexFilter(grupo_particular, 22));
		}
		if (checkString(tipo_pagamento))
			filters.add(RowFilter.regexFilter(tipo_pagamento, 2));

		if (checkString(depositante))
			filters.add(RowFilter.regexFilter(depositante, 7));
		
		if (checkString(favorecido))
			filters.add(RowFilter.regexFilter(favorecido, 9));
		
		
		sorter.setRowFilter(RowFilter.andFilter(filters));
	}

	class EvenOddRenderer implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String dados = (String) table.getValueAt(row, 4);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom

			} else {
				if (dados.equalsIgnoreCase("RECOLHER ASSINATURAS")) {
					renderer.setBackground(Color.yellow);

				} else if (dados.equalsIgnoreCase("Em Aprovação")) {
					renderer.setBackground(new Color(255, 69, 0)); // laranja

				} else if (dados.equalsIgnoreCase("Assinado")) {
					renderer.setBackground(new Color(95, 159, 159)); // verde

				} else if (dados.equalsIgnoreCase("CONCLUIDO")) {
					renderer.setBackground(new Color(0, 100, 0)); // verde

				}

			}

			return renderer;
		}
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

	public static class PagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_pagamento = 0;
		private final int data_pagamento = 1;
		private final int tipo = 2;
		private final int descricao = 3;
		private final int valor_pago = 4;
		private final int valor_produto = 5;
		private final int cobertura = 6;
		private final int depositante = 7;
		private final int conta_depositante = 8;
		private final int favorecido = 9;
		private final int conta_favorecido = 10;
		private final int contrato_remetente = 11;
		private final int contrato_destinatario = 12;
		private final int id_contrato = 13;
		private final int codigo_contrato = 14;
		private final int compradores = 15;
		private final int vendedores = 16;
		private final int produto = 17;
		private final int transgenia = 18;
		private final int safra = 19;
		private final int quantidade = 20;
		private final int valor_a_pagar = 21;
		private final int grupo_particular = 22;

	
		

		/*private final String colunas[] = { "ID Contrato", "Codigo", "Compradores", "Vendedores", "Produto",
				"Transgenia", "Safra", "Quantidade", "Valor por Unidade", "Valor a Pagar", "ID:", "Data:", "Tipo",
				"Descricao", "Depositante", "Conta Depositante", "Favorecido", "Conta Favorecido", "Valor Pago", "Cobertura" };
		*/
		private final String colunas[] = { "ID:", "Data:", "Tipo",
				"Descricao","Valor Pago","Valor por Unidade", "Cobertura", "Depositante", "Conta Depositante",
				"Favorecido", "Conta Favorecido","Contrato Remetente", "Contrato Destinatario","ID Contrato", "Codigo", "Compradores", "Vendedores", "Produto",
				"Transgenia", "Safra", "Quantidade", "Valor a Pagar", "Grupo/Particular"};
		
		private final ArrayList<PagamentoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																				// genérica de
		// nfs

		public PagamentoTableModel() {

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
			case quantidade:
				return int.class;
			case valor_produto:
				return String.class;
			case valor_a_pagar:
				return String.class;
			case id_pagamento:
				return String.class;
			case data_pagamento:
				return String.class;
			case tipo:
				return String.class;
			case descricao:
				return String.class;
			case depositante:
				return String.class;
			case conta_depositante:
				return String.class;
			case favorecido:
				return String.class;
			case conta_favorecido:
				return String.class;
			case valor_pago:
				return String.class;
			case cobertura:
				return String.class;
			case contrato_remetente:
				return String.class;
			case contrato_destinatario:
				return String.class;
			case grupo_particular:
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
			PagamentoCompleto pagamento = dados.get(rowIndex);
			CadastroContrato ct_remetente = pagamento.getContrato_remetente();
			CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();


			// retorna o valor da coluna
			switch (columnIndex) {
			case id_contrato: {
				return ct_remetente.getId();
			}
			case codigo_contrato: {
				return ct_remetente.getCodigo();
			}
			case compradores: {
				return ct_remetente.getNomes_compradores().toUpperCase();
			}
			case vendedores: {
				return ct_remetente.getNomes_vendedores().toUpperCase();
			}
			case produto: {
				return ct_remetente.getModelo_produto().getNome_produto().toUpperCase();
			}
			case transgenia: {
				return ct_remetente.getModelo_produto().getTransgenia().toUpperCase();
			}
			case safra: {
				return ct_remetente.getModelo_safra().getAno_plantio() + "/" + ct_remetente.getModelo_safra().getAno_colheita();
			}
			case quantidade:{
				
				double quantidade_sacos = 0;
				if(ct_remetente.getMedida().equalsIgnoreCase("KG")) {
					quantidade_sacos = ct_remetente.getQuantidade() / 60;
				}else if(ct_remetente.getMedida().equalsIgnoreCase("Sacos")){
					quantidade_sacos = ct_remetente.getQuantidade();
				}
				
				return z.format(quantidade_sacos) + " sacos";
			}
			case valor_produto:{
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());

				return valorString;
			}
			case valor_a_pagar:{
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format( ct_remetente.getValor_a_pagar());

				return valorString;
				
			}
			case id_pagamento:{
				return pagamento.getId_pagamento();
			}
			case data_pagamento:{
				return pagamento.getData_pagamento();
			}
			case tipo:{
				if (pagamento.getTipo() == 1) {
					return "NORMAL";
				}else if(pagamento.getTipo() == 2) {
					return "COMISSÃO";
				}
				else if(pagamento.getTipo() == 3) {

				//é uma transferencia
					if(pagamento.getId_contrato_remetente() == ct_remetente.getId()) {
						return "-Transferencia".toUpperCase();
					}else if(pagamento.getId_contrato_destinatario() == ct_remetente.getId()) {
						//é uma transferencia positiva
						return "+Transferencia".toUpperCase();
					}else {
						return "Transferencia".toUpperCase();
					}
				}
			
			}
			case descricao:{
				if(pagamento.getDescricao() != null)
				return pagamento.getDescricao().toUpperCase();
			}
			case depositante:{
				if(pagamento.getDepositante() != null)
				return pagamento.getDepositante().toUpperCase();
			}
			case conta_depositante:{
				return pagamento.getConta_bancaria_depositante();
			}
			case favorecido:{
				if(pagamento.getDepositante() != null)
				return pagamento.getFavorecido().toUpperCase();
			}
			case conta_favorecido:{
				return pagamento.getConta_bancaria_favorecido();
			}
			case valor_pago:{
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format( pagamento.getValor_pagamento());

				return valorString;
				
			}
			case cobertura:{
				
				
				
				double cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if(ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;
				
				return z.format(cobertura)  + " sacos";
			}
			case contrato_remetente:{
				return pagamento.getContrato_remetente().getCodigo();
			}
			case contrato_destinatario:{
				return pagamento.getContrato_destinatario().getCodigo();
			}
			case grupo_particular:{
				if(ct_remetente.getGrupo_particular() == 0) {
					return "GRUPO";
				}else if(ct_remetente.getGrupo_particular() == 1) {
					return "PARTICULAR";
				}
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
			PagamentoCompleto recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public PagamentoCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(PagamentoCompleto nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(PagamentoCompleto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<PagamentoCompleto> dadosIn) {
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
		public void onRemove(PagamentoCompleto nota) {
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

	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
		
	}
	

}
