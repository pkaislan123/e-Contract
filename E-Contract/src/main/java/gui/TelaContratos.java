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
import main.java.views_personalizadas.TelaEscolhaRelatorioContratos;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
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
import javax.swing.JRadioButton;

public class TelaContratos extends JFrame {

	private static ArrayList<CadastroContrato> lista_contratos = new ArrayList<>();
	private JDialog telaPai;
	private boolean finalizado = false;
	private JTable tabela;
	private boolean nulo = false;
	private File file_selecionado;
	private final JPanel painelPrincipal = new JPanel();
	private JRadioButton rdContratos, rdSubContratos;
	private ArrayList<TelaGerenciarContrato> lista_telas_contratos = new ArrayList<>();
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private JLabel lblTotalContratosEmAnalise, lblTotalContratosAssinar, lblTotalContratosConcluidos,
			lblTotalContratosAssinados;

	private TelaContratos isto;
	private JTextField entNomeComprador;
	private JTextField entNomeVendedor;
	private JLabel lblTotalSacosKGs, lblNumContratos;

	private ContratoTableModel modelo_contratos = new ContratoTableModel();
	private TableRowSorter<ContratoTableModel> sorter;
	private JTextField entProduto;
	private JTextField entSafra;
	private JTextField entCodigo;
	private JTextField entStatus;
	private int id_contrato_pai_para_replica_global = 0;

	private int flag_retorno_global;
	private JTextField entTransgenia;
	private FileChooser d;
	private JTextField entLocalRetirada;
	private JTextField entStatusRecebimento;
	private JTextField entStatusCarregamento;
	private JTextField entStatusPagamento;
	private JLabel lblTotalContratado, lblTotalContratosCancelados, lblTotalRecebido, lblTotalCarregado, lblTotalPago,
			lblValorTotalContratos;

	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaContratos(int flag_retorno, Window janela_pai) {

		// setModal(true);
		// setAlwaysOnTop(true);

		isto = this;
		flag_retorno_global = flag_retorno;
		// setResizable(false);
		setTitle("E-Contract - Contratos");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

		setContentPane(painelPrincipal);

		EvenOddRenderer renderer = new EvenOddRenderer();
		sorter = new TableRowSorter<ContratoTableModel>(modelo_contratos);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[grow][632px,grow][grow]", "[][grow][][]"));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrincipal.add(panel_5, "cell 0 0 3 1,alignx center,aligny center");
		panel_5.setLayout(
				new MigLayout("", "[58px][274px][48px][306px,grow][90px][199px,grow][67px,grow][grow][126px][59px]",
						"[28px,grow][28px][28px][]"));

		JLabel lblNewLabel = new JLabel("Comprador:");
		panel_5.add(lblNewLabel, "cell 0 0,alignx right,aligny center");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JPanel panel_4 = new JPanel();
		painelPrincipal.add(panel_4, "cell 0 2 3 1,alignx right");
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(new MigLayout("", "[73px][152px][74px][87px][81px][106px]", "[36px]"));

		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.setBackground(new Color(0, 51, 255));
		btnAbrir.setForeground(Color.WHITE);
		btnAbrir.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(btnAbrir, "cell 4 0,alignx left,aligny top");
		btnAbrir.setIcon(new ImageIcon(TelaCliente.class.getResource("/imagens/lista.png")));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = tabela.getSelectedRow();

				int id_contrato_selecionado = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
				GerenciarBancoContratos gerenciar_cont = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar_cont.getContrato(id_contrato_selecionado);
				lista_telas_contratos.add(new TelaGerenciarContrato(contrato_selecionado, null));

			}
		});

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBackground(new Color(51, 0, 102));
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 14));

		entNomeComprador = new JTextField();
		entNomeComprador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_5.add(entNomeComprador, "cell 1 0,growx,aligny top");
		entNomeComprador.setColumns(10);

		entNomeVendedor = new JTextField();
		entNomeVendedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});

		JLabel lblVendedor = new JLabel("Vendedor:");
		panel_5.add(lblVendedor, "cell 2 0,alignx right,aligny center");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(entNomeVendedor, "cell 3 0,growx,aligny top");
		entNomeVendedor.setColumns(10);

		JLabel lblLocalRetirada = new JLabel("Local Retirada:");
		panel_5.add(lblLocalRetirada, "cell 4 0,alignx right,aligny center");
		lblLocalRetirada.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entLocalRetirada = new JTextField();
		entLocalRetirada.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		panel_5.add(entLocalRetirada, "cell 5 0,growx,aligny top");
		entLocalRetirada.setColumns(10);

		JLabel lblCdigo = new JLabel("Código:");
		panel_5.add(lblCdigo, "cell 6 0,alignx right,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entCodigo = new JTextField();
		entCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		panel_5.add(entCodigo, "cell 7 0,growx,aligny top");
		entCodigo.setColumns(10);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_5.add(panel_6, "cell 8 0 2 1,alignx center,growy");
		panel_6.setLayout(new MigLayout("", "[76px][102px]", "[18px]"));

		rdContratos = new JRadioButton("Contratos");
		rdContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdContratos.setSelected(true);
				rdSubContratos.setSelected(false);
			}
		});
		rdContratos.setSelected(true);
		panel_6.add(rdContratos, "cell 0 0,alignx left,aligny top");

		rdSubContratos = new JRadioButton("Sub-Contratos");
		rdSubContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdContratos.setSelected(false);
				rdSubContratos.setSelected(true);
			}
		});
		panel_6.add(rdSubContratos, "cell 1 0,alignx left,aligny top");

		JLabel lblStatus = new JLabel("Status:");
		panel_5.add(lblStatus, "cell 0 1,alignx right,aligny center");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entStatus = new JTextField();
		entStatus.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		panel_5.add(entStatus, "cell 1 1,growx,aligny top");
		entStatus.setColumns(10);

		JLabel lblStatusRecebimento = new JLabel("Status Recebimento:");
		lblStatusRecebimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatusRecebimento, "cell 2 1,alignx trailing");

		entStatusRecebimento = new JTextField();
		entStatusRecebimento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		entStatusRecebimento.setColumns(10);
		panel_5.add(entStatusRecebimento, "cell 3 1,growx");

		JLabel lblStatusCarregamento = new JLabel("Status Carregamento:");
		lblStatusCarregamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatusCarregamento, "cell 4 1,alignx trailing");

		entStatusCarregamento = new JTextField();
		entStatusCarregamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		entStatusCarregamento.setColumns(10);
		panel_5.add(entStatusCarregamento, "cell 5 1,growx");

		JLabel lblStatusPagamento = new JLabel("Status Pagamento:");
		lblStatusPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblStatusPagamento, "cell 6 1,alignx trailing");

		entStatusPagamento = new JTextField();
		entStatusPagamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		entStatusPagamento.setColumns(10);
		panel_5.add(entStatusPagamento, "cell 7 1,growx");

		JLabel lblProduto = new JLabel("Produto:");
		panel_5.add(lblProduto, "cell 0 2,alignx right,aligny center");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entProduto = new JTextField();
		entProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		panel_5.add(entProduto, "cell 1 2,growx,aligny top");
		entProduto.setColumns(10);

		JLabel lblSafra = new JLabel("Safra:");
		panel_5.add(lblSafra, "cell 2 2,alignx right,aligny center");
		lblSafra.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entSafra = new JTextField();
		entSafra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				filtrar();

			}
		});
		panel_5.add(entSafra, "cell 3 2,growx,aligny top");
		entSafra.setColumns(10);

		JLabel lblTransgnese = new JLabel("Transgênese:");
		panel_5.add(lblTransgnese, "cell 4 2,alignx right,aligny center");
		lblTransgnese.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entTransgenia = new JTextField();
		entTransgenia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();

			}
		});
		panel_5.add(entTransgenia, "cell 5 2,growx,aligny top");
		entTransgenia.setColumns(10);

		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entCodigo.setText("");
				entNomeComprador.setText("");
				entNomeVendedor.setText("");
				entLocalRetirada.setText("");
				entTransgenia.setText("");
				entSafra.setText("");
				entProduto.setText("");
				entStatus.setText("");

			}
		});

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 51, 0));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(btnRefazerPesquisa, "cell 9 2,alignx left,aligny top");
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (flag_retorno == 3 || flag_retorno == 4) {
					pesquisar_sub_contratos(id_contrato_pai_para_replica_global);

				} else if (rdSubContratos.isSelected()) {
					pesquisar_sub_contratos();
				} else {
					pesquisar();

				}
			}
		});

		JButton btnLimparFiltros = new JButton("Limpar Pesquisa");
		btnLimparFiltros.setBackground(new Color(255, 51, 51));
		btnLimparFiltros.setForeground(Color.WHITE);
		btnLimparFiltros.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(btnLimparFiltros, "cell 6 3,alignx left,aligny top");
		btnLimparFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));

				calcular();

			}
		});
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparCampos.setBackground(new Color(204, 0, 0));
		panel_5.add(btnLimparCampos, "cell 7 3,growx");

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 51, 255));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(btnFiltrar, "cell 9 3,growx,aligny top");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				filtrar();
			}
		});

		JButton btnExportar = new JButton("Exportar Dados");
		btnExportar.setBackground(new Color(51, 0, 51));
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(btnExportar, "cell 0 0,alignx left,aligny center");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaRelatorioContratos tela = new TelaEscolhaRelatorioContratos(getContratosSelecionados(),
						isto);
				tela.setVisible(true);
			}
		});

		JButton btnImportarManualmente = new JButton("Importar Manualmente");
		btnImportarManualmente.setBackground(new Color(102, 51, 0));
		btnImportarManualmente.setForeground(Color.WHITE);
		btnImportarManualmente.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(btnImportarManualmente, "cell 1 0,alignx left,aligny center");
		btnImportarManualmente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo\ndo contrato de terceiros");

				new JFXPanel();
				Platform.runLater(() -> {

					// pegar ultima pasta
					ManipularTxt manipular_ultima_pasta = new ManipularTxt();
					String ultima_pasta = manipular_ultima_pasta
							.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
					FileChooser d = new FileChooser();
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
					d.getExtensionFilters().add(extFilter);
					
					
					d.setInitialDirectory(new File(ultima_pasta));
					File file = d.showOpenDialog(new Stage());
					String caminho_arquivo = "";
					if (file != null) {
						caminho_arquivo = file.getAbsolutePath();

						manipular_ultima_pasta.rescreverArquivo(
								new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
						TelaImportarContratoManual tela = new TelaImportarContratoManual(4, null, 0, file, isto);
						tela.setVisible(true);

					}

				});

				isto.dispose();
			}

		});

		JButton btnImportarTerceiros = new JButton("Importar");
		btnImportarTerceiros.setBackground(new Color(51, 153, 102));
		btnImportarTerceiros.setForeground(Color.WHITE);
		btnImportarTerceiros.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(btnImportarTerceiros, "cell 2 0,alignx left,aligny center");
		btnImportarTerceiros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importarContratoTerceiros();
				isto.dispose();
			}
		});

		panel_4.add(btnSelecionar, "cell 3 0,alignx left,aligny center");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela.getSelectedRow();
				int id_contrato = Integer.parseInt(tabela.getValueAt(indiceDaLinha, 0).toString());
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato contrato_selecionado = gerenciar.getContrato(id_contrato);

				if (flag_retorno == 1) {

					((TelaConfirmarCarregamento) telaPai).setContratoCarregamento(contrato_selecionado);
					isto.dispose();

				} else if (flag_retorno == 2) {
					if (contrato_selecionado.getStatus_contrato() != 3
							&& contrato_selecionado.getStatus_contrato() != 4) {
						((TelaConfirmarTransferenciaPagamentoContratual) telaPai)
								.setContratoDestintario(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel transferir um pagamento para um contrato finalizado ou cancelado");
					}

				} else if (flag_retorno == 3) {

					if (contrato_selecionado.getStatus_contrato() != 3
							&& contrato_selecionado.getStatus_contrato() != 4) {
						((TelaReplicarCarregamento) telaPai).setSubContrato(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel replicar um carregamento para um contrato finalizado ou cancelado");
					}

				} else if (flag_retorno == 4) {

					if (contrato_selecionado.getStatus_contrato() != 3
							&& contrato_selecionado.getStatus_contrato() != 4) {

						((TelaReplicarPagamento) telaPai).setSubContrato(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel replicar um pagamento para um contrato finalizado ou cancelado");
					}

				} else if (flag_retorno == 5) {

					if (contrato_selecionado.getStatus_contrato() != 3
							&& contrato_selecionado.getStatus_contrato() != 4) {

						((TelaConfirmarTransferenciaCarga) telaPai).setContratoDestintario(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel transferir volume para um contrato finalizado ou cancelado");
					}

				} else if (flag_retorno == 6) {

					if (contrato_selecionado.getStatus_contrato() != 3
							&& contrato_selecionado.getStatus_contrato() != 4) {

						((TelaReplicarRecebimento) telaPai).setContratoDestintario(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel replicar um pagamento para um contrato finalizado ou cancelado");
					}

				}
				isto.dispose();
			}
		});

		JButton btnContrato = new JButton("Novo Contrato");
		btnContrato.setBackground(new Color(0, 51, 0));
		btnContrato.setForeground(Color.WHITE);
		btnContrato.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(btnContrato, "cell 5 0,alignx left,aligny center");
		btnContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(0, null, 0, isto);
				isto.dispose();
			}
		});
		// btnContrato.setIcon(new
		// ImageIcon(TelaContratos.class.getResource("/imagens/add_contrato.png")));
		btnContrato.setToolTipText("Adicionar Novo Contrato");

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelPrincipal.add(panel_2, "cell 0 3,alignx center,aligny center");
		panel_2.setLayout(new MigLayout("", "[48px][115px][24px]", "[19px][19px][19px][19px][]"));

		JLabel lblNewLabel_4 = new JLabel("___");
		panel_2.add(lblNewLabel_4, "cell 0 0,grow");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(255, 69, 0));

		JLabel lblNewLabel_5 = new JLabel("Contrato em Análise:");
		panel_2.add(lblNewLabel_5, "cell 1 0,alignx center,aligny center");

		lblTotalContratosEmAnalise = new JLabel("999");
		panel_2.add(lblTotalContratosEmAnalise, "cell 2 0,alignx left,aligny center");
		lblTotalContratosEmAnalise.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_1 = new JLabel("___");
		panel_2.add(lblNewLabel_4_1, "cell 0 1,grow");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setBackground(Color.yellow);

		JLabel lblNewLabel_5_1 = new JLabel("Recolher Assinatura:");
		panel_2.add(lblNewLabel_5_1, "cell 1 1,alignx center,aligny center");

		lblTotalContratosAssinar = new JLabel("999");
		panel_2.add(lblTotalContratosAssinar, "cell 2 1,alignx left,aligny center");
		lblTotalContratosAssinar.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_2 = new JLabel("___");
		panel_2.add(lblNewLabel_4_2, "cell 0 2,grow");
		lblNewLabel_4_2.setOpaque(true);
		lblNewLabel_4_2.setBackground(new Color(95, 159, 159));

		JLabel lblNewLabel_5_2 = new JLabel("Assinado:");
		panel_2.add(lblNewLabel_5_2, "cell 1 2,alignx right,aligny center");

		lblTotalContratosAssinados = new JLabel("999");
		panel_2.add(lblTotalContratosAssinados, "cell 2 2,alignx left,aligny center");
		lblTotalContratosAssinados.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_3 = new JLabel("___");
		panel_2.add(lblNewLabel_4_3, "cell 0 3,grow");
		lblNewLabel_4_3.setOpaque(true);
		lblNewLabel_4_3.setBackground(new Color(0, 100, 0));

		JLabel lblNewLabel_5_3 = new JLabel("Concluido:");
		panel_2.add(lblNewLabel_5_3, "cell 1 3,alignx right,aligny center");

		lblTotalContratosConcluidos = new JLabel("999");
		panel_2.add(lblTotalContratosConcluidos, "cell 2 3,alignx left,aligny center");
		lblTotalContratosConcluidos.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_3_1 = new JLabel("___");
		lblNewLabel_4_3_1.setOpaque(true);
		lblNewLabel_4_3_1.setBackground(new Color(153, 0, 0));
		panel_2.add(lblNewLabel_4_3_1, "cell 0 4,grow");

		JLabel lblNewLabel_5_3_1 = new JLabel("Cancelado:");
		panel_2.add(lblNewLabel_5_3_1, "cell 1 4,alignx right");

		lblTotalContratosCancelados = new JLabel("999");
		lblTotalContratosCancelados.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_2.add(lblTotalContratosCancelados, "cell 2 4");

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		painelPrincipal.add(panel_7, "cell 1 3,grow");
		panel_7.setLayout(new MigLayout("", "[][]", "[][][][]"));

		JLabel lblNewLabel_6_2 = new JLabel("Total Contratado:");
		lblNewLabel_6_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblNewLabel_6_2, "cell 0 0,alignx right");

		lblTotalContratado = new JLabel("99.999,66 SCs / 999999999999 KGs");
		lblTotalContratado.setForeground(Color.BLACK);
		lblTotalContratado.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_7.add(lblTotalContratado, "cell 1 0");

		JLabel lblNewLabel_6 = new JLabel("Total Recebido:");
		lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblNewLabel_6, "cell 0 1,alignx right");

		lblTotalRecebido = new JLabel("99.999,66 SCs / 999999999999 KGs");
		lblTotalRecebido.setForeground(Color.BLACK);
		lblTotalRecebido.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_7.add(lblTotalRecebido, "cell 1 1");

		JLabel lblNewLabel_6_1 = new JLabel("Total Carregado:");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblNewLabel_6_1, "cell 0 2,alignx right");

		lblTotalCarregado = new JLabel("99.999,66 SCs / 999999999999 KGs");
		lblTotalCarregado.setForeground(Color.BLACK);
		lblTotalCarregado.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_7.add(lblTotalCarregado, "cell 1 2");

		JLabel lblNewLabel_6_1_1 = new JLabel("Total Pago:");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblNewLabel_6_1_1, "cell 0 3,alignx right");

		lblTotalPago = new JLabel("R$ 999.999.999,00");
		lblTotalPago.setForeground(Color.BLACK);
		lblTotalPago.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_7.add(lblTotalPago, "cell 1 3");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelPrincipal.add(panel_3, "cell 2 3,alignx center,aligny center");
		panel_3.setLayout(new MigLayout("", "[239px][152px,grow][333px]", "[39.00px,grow][50px]"));

		JPanel panel_1 = new JPanel();
		panel_3.add(panel_1, "cell 0 0,alignx left,growy");
		panel_1.setBackground(new Color(0, 153, 51));
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));

		JLabel lblNewLabel_1 = new JLabel("Número de Contratos:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_1, "cell 0 0");

		lblNumContratos = new JLabel("999");
		lblNumContratos.setForeground(Color.WHITE);
		lblNumContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1.add(lblNumContratos, "flowx,cell 1 0");

		JLabel lblNewLabel_3 = new JLabel("Contratos");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_3, "cell 1 0");

		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBackground(new Color(0, 153, 51));
		panel_3.add(panel_1_2, "cell 1 0,grow");
		panel_1_2.setLayout(new MigLayout("", "[][]", "[]"));

		JLabel lblNewLabel_1_1 = new JLabel("Valor Total:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_1_2.add(lblNewLabel_1_1, "cell 0 0");

		lblValorTotalContratos = new JLabel("999");
		lblValorTotalContratos.setForeground(Color.WHITE);
		lblValorTotalContratos.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1_2.add(lblValorTotalContratos, "cell 1 0");

		JPanel panel_1_1 = new JPanel();
		panel_3.add(panel_1_1, "cell 2 0,alignx left,aligny top");
		panel_1_1.setBackground(new Color(51, 153, 102));
		panel_1_1.setLayout(new MigLayout("", "[][]", "[]"));

		JLabel lblNewLabel_2 = new JLabel("Total Sacos:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel_2.setForeground(Color.WHITE);
		panel_1_1.add(lblNewLabel_2, "cell 0 0");

		lblTotalSacosKGs = new JLabel("99.999,66 SCs / 999999999999 KGs");
		lblTotalSacosKGs.setForeground(Color.WHITE);
		lblTotalSacosKGs.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_1_1.add(lblTotalSacosKGs, "cell 1 0");

		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 1 3 1,grow");

		tabela = new JTable(modelo_contratos);
		tabela.setDefaultRenderer(Object.class, renderer);

		// tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(7).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(8).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(9).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(10).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(11).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(12).setPreferredWidth(50);

		tabela.getColumnModel().getColumn(5).setCellRenderer(new StatusRecebimentoCellRender());
		tabela.getColumnModel().getColumn(6).setCellRenderer(new StatusCarregamentoCellRender());
		tabela.getColumnModel().getColumn(7).setCellRenderer(new StatusPagamentoCellRender());

		tabela.setRowSorter(sorter);

		tabela.setBackground(new Color(255, 255, 255));
		// tabela.setPreferredSize(new Dimension(0, 200));
		// tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela);

		panel.setLayout(new BorderLayout(0, 0));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setAutoscrolls(true);

		panel.add(scrollPane);

		if (flag_retorno == 1 || flag_retorno == 2 || flag_retorno == 3 || flag_retorno == 4 || flag_retorno == 5
				|| flag_retorno == 6) {
			// selecionar contrato para carregamento
			btnAbrir.setEnabled(false);
			btnAbrir.setVisible(false);

		} else {
			btnSelecionar.setEnabled(false);
			btnSelecionar.setVisible(false);
		}

		if (flag_retorno == 3 || flag_retorno == 4) {

		} else
			pesquisar();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// this.pack();

		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void calcular() {

		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		double valor_total_contratos = 0;

		double total_carregado = 0, total_recebido = 0, total_contratado = 0, total_comissao = 0, total_pago = 0;

		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;
		int total_contratos_cancelados = 0;

		NumberFormat z = NumberFormat.getNumberInstance();

		for (int row = 0; row < tabela.getRowCount(); row++) {

			int index = tabela.convertRowIndexToModel(row);
			CadastroContrato contrato = modelo_contratos.getValue(index);

			if (contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 3 || contrato.getSub_contrato() == 4
					|| contrato.getSub_contrato() == 5) {

				if (contrato.getStatus_contrato() != 4) {

					double quantidade_sacos = 0;
					double quantidade_kg = 0;
					double quantidade_paga = contrato.getTotal_pago();
					double quantidade_comissao_paga = contrato.getTotal_comissao();
					double valor_total_local = contrato.getValor_a_pagar().doubleValue();

					if (contrato.getMedida().equalsIgnoreCase("SACOS")) {
						quantidade_sacos = contrato.getQuantidade();
						quantidade_kg = contrato.getQuantidade() * 60;

					} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
						quantidade_kg = contrato.getQuantidade();
						quantidade_sacos = quantidade_kg / 60;
					}

					if (contrato.getStatus_contrato() == 0) {
						total_contratos_em_analise++;
					} else if (contrato.getStatus_contrato() == 1) {
						total_contratos_assinar++;
					} else if (contrato.getStatus_contrato() == 2) {
						total_contratos_assinado++;
					} else if (contrato.getStatus_contrato() == 3) {
						total_contratos_concluido++;
					}
					quantidade_sacos_total += quantidade_sacos;
					quantidade_kg_total += quantidade_kg;

					total_contratado += quantidade_kg;

					total_recebido += contrato.getQuantidade_recebida();

					total_carregado += contrato.getQuantidade_carregada();

					total_pago += quantidade_paga;

					total_comissao += quantidade_comissao_paga;

					valor_total_contratos += valor_total_local;
				} else {
					total_contratos_cancelados++;
				}
			}

		}
		Locale ptBr = new Locale("pt", "BR");

		lblNumContratos.setText(tabela.getRowCount() + "");
		lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");
		lblValorTotalContratos.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_contratos));

		lblTotalContratosEmAnalise.setText(total_contratos_em_analise + " - "
				+ ((int) (((double) ((double) total_contratos_em_analise / (double) tabela.getRowCount()) * 100)))
				+ "%");
		lblTotalContratosAssinar.setText(total_contratos_assinar + " - "
				+ ((int) (((double) ((double) total_contratos_assinar / (double) tabela.getRowCount()) * 100))) + "%");
		lblTotalContratosConcluidos.setText(total_contratos_concluido + " - "
				+ ((int) (((double) ((double) total_contratos_concluido / (double) tabela.getRowCount())) * 100))
				+ "%");
		lblTotalContratosAssinados.setText(total_contratos_assinado + " - "
				+ ((int) (((double) ((double) total_contratos_assinado / (double) tabela.getRowCount()) * 100))) + "%");
		lblTotalContratosCancelados.setText(total_contratos_cancelados + " - "
				+ ((int) (((double) ((double) total_contratos_cancelados / (double) tabela.getRowCount()) * 100)))
				+ "%");

		lblTotalContratado.setText(z.format(total_contratado) + " Kgs | " + z.format(total_contratado / 60) + " Sacos");
		lblTotalRecebido.setText(z.format(total_recebido * 60) + " Kgs | " + z.format(total_recebido) + " Sacos");
		lblTotalCarregado.setText(z.format(total_carregado * 60) + " Kgs | " + z.format(total_carregado) + " Sacos");
		lblTotalPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(total_pago));

	}

	public void pesquisar() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_contratos.clear();
		modelo_contratos.onRemoveAll();

		lista_contratos = gerenciar.getContratos();

		for (CadastroContrato contrato : lista_contratos) {

			if (contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 3 || contrato.getSub_contrato() == 4
					|| contrato.getSub_contrato() == 5) {

				modelo_contratos.onAdd(contrato);

			}

		}

		calcular();

	}

	public void pesquisar_contratos_e_sub_contratos() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_contratos.clear();
		modelo_contratos.onRemoveAll();
		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;

		NumberFormat z = NumberFormat.getNumberInstance();

		for (CadastroContrato contrato : gerenciar.getContratos()) {

			modelo_contratos.onAdd(contrato);
			double quantidade_sacos = 0;
			double quantidade_kg = 0;

			if (contrato.getMedida().equalsIgnoreCase("SACOS")) {
				quantidade_sacos = contrato.getQuantidade();
				quantidade_kg = contrato.getQuantidade() * 60;

			} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
				quantidade_kg = contrato.getQuantidade();
				quantidade_sacos = quantidade_kg / 60;
			}

			if (contrato.getStatus_contrato() == 0) {
				total_contratos_em_analise++;
			} else if (contrato.getStatus_contrato() == 1) {
				total_contratos_assinar++;
			} else if (contrato.getStatus_contrato() == 2) {
				total_contratos_assinado++;
			} else if (contrato.getStatus_contrato() == 3) {
				total_contratos_concluido++;
			}
			quantidade_sacos_total += quantidade_sacos;
			quantidade_kg_total += quantidade_kg;
			lista_contratos.add(contrato);

		}

		lblNumContratos.setText(lista_contratos.size() + "");
		lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");

		lblTotalContratosEmAnalise.setText(total_contratos_em_analise + " - "
				+ ((int) (((double) ((double) total_contratos_em_analise / (double) tabela.getRowCount()) * 100)))
				+ "%");
		lblTotalContratosAssinar.setText(total_contratos_assinar + " - "
				+ ((int) (((double) ((double) total_contratos_assinar / (double) tabela.getRowCount()) * 100))) + "%");
		lblTotalContratosConcluidos.setText(total_contratos_concluido + " - "
				+ ((int) (((double) ((double) total_contratos_concluido / (double) tabela.getRowCount())) * 100))
				+ "%");
		lblTotalContratosAssinados.setText(total_contratos_assinado + " - "
				+ ((int) (((double) ((double) total_contratos_assinado / (double) tabela.getRowCount()) * 100))) + "%");

	}

	public void pesquisar_sub_contratos(int id_contrato_pai) {

		id_contrato_pai_para_replica_global = id_contrato_pai;
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_contratos.clear();
		modelo_contratos.onRemoveAll();
		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;
		NumberFormat z = NumberFormat.getNumberInstance();

		ArrayList<CadastroContrato> sub_contratos = gerenciar.getInfoSubContratos(id_contrato_pai);

		for (CadastroContrato contrato : sub_contratos) {

			if (contrato.getSub_contrato() == 1) {

				modelo_contratos.onAdd(contrato);
				double quantidade_sacos = 0;
				double quantidade_kg = 0;

				if (contrato.getMedida().equalsIgnoreCase("SACOS")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = contrato.getQuantidade() * 60;

				} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}

				if (contrato.getStatus_contrato() == 0) {
					total_contratos_em_analise++;
				} else if (contrato.getStatus_contrato() == 1) {
					total_contratos_assinar++;
				} else if (contrato.getStatus_contrato() == 2) {
					total_contratos_assinado++;
				} else if (contrato.getStatus_contrato() == 3) {
					total_contratos_concluido++;
				}
				quantidade_sacos_total += quantidade_sacos;
				quantidade_kg_total += quantidade_kg;
				lista_contratos.add(contrato);
			}

		}

		lblNumContratos.setText(lista_contratos.size() + "");
		lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");

		lblTotalContratosEmAnalise.setText(total_contratos_em_analise + " - "
				+ ((int) (((double) ((double) total_contratos_em_analise / (double) tabela.getRowCount()) * 100)))
				+ "%");
		lblTotalContratosAssinar.setText(total_contratos_assinar + " - "
				+ ((int) (((double) ((double) total_contratos_assinar / (double) tabela.getRowCount()) * 100))) + "%");
		lblTotalContratosConcluidos.setText(total_contratos_concluido + " - "
				+ ((int) (((double) ((double) total_contratos_concluido / (double) tabela.getRowCount())) * 100))
				+ "%");
		lblTotalContratosAssinados.setText(total_contratos_assinado + " - "
				+ ((int) (((double) ((double) total_contratos_assinado / (double) tabela.getRowCount()) * 100))) + "%");

	}

	public void pesquisar_sub_contratos() {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_contratos.clear();
		modelo_contratos.onRemoveAll();
		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;
		NumberFormat z = NumberFormat.getNumberInstance();

		ArrayList<CadastroContrato> sub_contratos = gerenciar.getInfoSubContratos();

		for (CadastroContrato contrato : sub_contratos) {

			if (contrato.getSub_contrato() == 1) {

				modelo_contratos.onAdd(contrato);
				double quantidade_sacos = 0;
				double quantidade_kg = 0;

				if (contrato.getMedida().equalsIgnoreCase("SACOS")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = contrato.getQuantidade() * 60;

				} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				}

				if (contrato.getStatus_contrato() == 0) {
					total_contratos_em_analise++;
				} else if (contrato.getStatus_contrato() == 1) {
					total_contratos_assinar++;
				} else if (contrato.getStatus_contrato() == 2) {
					total_contratos_assinado++;
				} else if (contrato.getStatus_contrato() == 3) {
					total_contratos_concluido++;
				}
				quantidade_sacos_total += quantidade_sacos;
				quantidade_kg_total += quantidade_kg;
				lista_contratos.add(contrato);
			}

		}
		lblNumContratos.setText(lista_contratos.size() + "");
		lblTotalSacosKGs.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");

		lblTotalContratosEmAnalise.setText(total_contratos_em_analise + " - "
				+ ((int) (((double) ((double) total_contratos_em_analise / (double) tabela.getRowCount()) * 100)))
				+ "%");
		lblTotalContratosAssinar.setText(total_contratos_assinar + " - "
				+ ((int) (((double) ((double) total_contratos_assinar / (double) tabela.getRowCount()) * 100))) + "%");
		lblTotalContratosConcluidos.setText(total_contratos_concluido + " - "
				+ ((int) (((double) ((double) total_contratos_concluido / (double) tabela.getRowCount())) * 100))
				+ "%");
		lblTotalContratosAssinados.setText(total_contratos_assinado + " - "
				+ ((int) (((double) ((double) total_contratos_assinado / (double) tabela.getRowCount()) * 100))) + "%");

	}

	public boolean testeConexao() {
		try {
			Thread.sleep(10000);
			URL url = new URL("http://www.google.com.br");

			System.out.println("Tentando conexao!");

			URLConnection connection = url.openConnection();
			connection.connect();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erro ao se conectar a internet!");
			return false;
		}
	}

	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
	}

	public void importarContratoTerceiros() {

		JOptionPane.showMessageDialog(isto, "Na próxima tela, importe os arquivos\ndo contrato de terceiros");

		new JFXPanel();
		Platform.runLater(() -> {

			// pegar ultima pasta
			ManipularTxt manipular_ultima_pasta = new ManipularTxt();
			String ultima_pasta = manipular_ultima_pasta
					.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
			FileChooser d = new FileChooser();

			d.setInitialDirectory(new File(ultima_pasta));

			File file = d.showOpenDialog(new Stage());
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				manipular_ultima_pasta.rescreverArquivo(
						new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());

				ManipularArquivoTerceiros manipular = new ManipularArquivoTerceiros();
				CadastroContrato contrato_importar = manipular.filtrar(file);

			} else {
				JOptionPane.showMessageDialog(isto, "Nenhum arquivo selecionado");
			}

		});

	}

	public void filtrar() {

		NumberFormat z = NumberFormat.getNumberInstance();

		double quantidade_sacos_total = 0;
		double quantidade_kg_total = 0;
		int total_contratos_em_analise = 0;
		int total_contratos_assinar = 0;
		int total_contratos_assinado = 0;
		int total_contratos_concluido = 0;
		int total_contratos = 0;
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		/*
		 * private final int status_recebimento = 5; private final int
		 * status_carregamento = 6; private final int status_pagamento = 7;
		 * 
		 */

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String status = entStatus.getText().toUpperCase();
		String status_rec = entStatusRecebimento.getText().toUpperCase();
		String status_carg = entStatusCarregamento.getText().toUpperCase();
		String status_pag = entStatusPagamento.getText().toUpperCase();

		String transgenese = entTransgenia.getText().toUpperCase();
		String local_retirada = entLocalRetirada.getText().toUpperCase();

		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 1));

		if (checkString(comprador))
			filters.add(RowFilter.regexFilter(comprador, 2));

		if (checkString(vendedor))
			filters.add(RowFilter.regexFilter(vendedor, 3));

		if (checkString(status))
			filters.add(RowFilter.regexFilter(status, 4));

		if (checkString(produto))
			filters.add(RowFilter.regexFilter(produto, 10));

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 11));

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 12));

		if (checkString(local_retirada))
			filters.add(RowFilter.regexFilter(local_retirada, 17));

		// status
		if (checkString(status_rec))
			filters.add(RowFilter.regexFilter(status_rec, 5));

		if (checkString(status_carg))
			filters.add(RowFilter.regexFilter(status_carg, 6));

		if (checkString(status_pag))
			filters.add(RowFilter.regexFilter(status_pag, 7));

		sorter.setRowFilter(RowFilter.andFilter(filters));

		calcular();

	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
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
				if (dados.equalsIgnoreCase("ASSINAR")) {
					renderer.setBackground(Color.yellow);

				} else if (dados.equalsIgnoreCase("A APROVAR")) {
					renderer.setBackground(new Color(255, 69, 0)); // laranja

				} else if (dados.equalsIgnoreCase("Assinado")) {
					renderer.setBackground(new Color(95, 159, 159)); // verde

				} else if (dados.equalsIgnoreCase("CONCLUIDO")) {
					renderer.setBackground(new Color(0, 100, 0)); // verde

				} else if (dados.equalsIgnoreCase("CANCELADO")) {
					renderer.setBackground(new Color(153, 0, 0)); // vermelho

				}

			}

			return renderer;
		}
	}

	class StatusRecebimentoCellRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status_recebimento = (String) table.getValueAt(row, 5);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status_recebimento.equalsIgnoreCase("RECEBENDO")) {
					renderer.setBackground(Color.yellow);
					renderer.setForeground(Color.black);

				} else if (status_recebimento.equalsIgnoreCase("A RECEBER")) {
					renderer.setBackground(new Color(255, 69, 0)); // laranja
					renderer.setForeground(Color.white);

				} else if (status_recebimento.equalsIgnoreCase("CONCLUIDO")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}

	class StatusCarregamentoCellRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status_recebimento = (String) table.getValueAt(row, 6);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status_recebimento.equalsIgnoreCase("CARREGADO PARC")) {
					renderer.setBackground(Color.yellow);
					renderer.setForeground(Color.black);

				} else if (status_recebimento.equalsIgnoreCase("A CARREGAR")
						|| status_recebimento.equalsIgnoreCase("AG RECEBER")) {
					renderer.setBackground(new Color(255, 69, 0)); // laranja
					renderer.setForeground(Color.white);

				} else if (status_recebimento.equalsIgnoreCase("CONCLUIDO")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}

	class StatusPagamentoCellRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String status_recebimento = (String) table.getValueAt(row, 7);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
				renderer.setForeground(Color.white);

			} else {
				if (status_recebimento.equalsIgnoreCase("PAGANDO")) {
					renderer.setBackground(Color.yellow);
					renderer.setForeground(Color.black);

				} else if (status_recebimento.equalsIgnoreCase("A PAGAR")) {
					renderer.setBackground(new Color(255, 69, 0)); // laranja
					renderer.setForeground(Color.white);

				} else if (status_recebimento.equalsIgnoreCase("CONCLUIDO")) {
					renderer.setBackground(new Color(0, 51, 0)); // verde
					renderer.setForeground(Color.white);

				}

			}

			return renderer;
		}
	}

	public class ContratoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int codigo = 1;
		private final int compradores = 2;
		private final int vendedores = 3;
		private final int status = 4;
		private final int status_recebimento = 5;
		private final int status_carregamento = 6;
		private final int status_pagamento = 7;

		private final int quantidade = 8;
		private final int medida = 9;
		private final int produto = 10;
		private final int transgenia = 11;
		private final int safra = 12;
		private final int valor_produto = 13;
		private final int valor_total = 14;
		private final int corretores = 15;
		private final int data_contrato = 16;
		private final int local_retirada = 17;
		private final int bruto_livre = 18;
		private final int penhor = 19;
		private final int optante_folha = 20;
		private final int localizacao = 21;
		private final int fertilizante = 22;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Código", "Compradores:", "Vendedores:", "Status:",
				"Status Recebimento:", "Status Carregamento", "Status Pagamento", "Quantidade:", "Medida:", "Produto:",
				"Transgênese", "Safra:", "Valor Produto:", "Valor Total:", "Corretores:", "Data Contrato",
				"Local Retirada", "BrutoXLivre", "Penhor", "Optante Folha", "Localização", "Fertilizante" };
		private final ArrayList<CadastroContrato> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public ContratoTableModel() {

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
			case id:
				return Integer.class;
			case codigo:
				return String.class;
			case compradores:
				return String.class;
			case vendedores:
				return String.class;
			case status:
				return String.class;
			case status_recebimento:
				return String.class;
			case status_carregamento:
				return String.class;
			case status_pagamento:
				return String.class;
			case quantidade:
				return String.class;
			case medida:
				return String.class;
			case produto:
				return String.class;
			case transgenia:
				return String.class;
			case safra:
				return String.class;
			case valor_produto:
				return String.class;
			case valor_total:
				return String.class;
			case corretores:
				return String.class;
			case data_contrato:
				return String.class;
			case local_retirada:
				return String.class;
			case bruto_livre:
				return String.class;
			case penhor:
				return String.class;
			case optante_folha:
				return String.class;
			case localizacao:
				return String.class;
			case fertilizante:
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
			Locale ptBr = new Locale("pt", "BR");
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroContrato contrato = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return contrato.getId();
			case codigo:
				return contrato.getCodigo();
			case compradores: {

				return trimar(encutarNomes(contrato.getNomes_compradores().toUpperCase()));

			}
			case vendedores: {
				return trimar(encutarNomes(contrato.getNomes_vendedores().toUpperCase()));
			}
			case status: {
				int status = contrato.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					return "ASSINAR".toUpperCase();

				} else if (status == 2) {
					return "ASSINADO".toUpperCase();

				} else if (status == 3) {
					return "CONCLUIDO".toUpperCase();

				} else if (status == 0) {
					return "A APROVAR".toUpperCase();

				} else if (status == 4) {
					return "CANCELADO".toUpperCase();

				}
			}
			case status_recebimento: {
				double quantidade_recebida = contrato.getQuantidade_recebida();
				double quantidade_sacos_sub = 0;
				double quantidade_quilogramas_sub = 0;

				if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos_sub = contrato.getQuantidade();
					quantidade_quilogramas_sub = contrato.getQuantidade() * 60;
				} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_quilogramas_sub = contrato.getQuantidade();
					quantidade_sacos_sub = contrato.getQuantidade() / 60;

				}

				if (quantidade_recebida >= (quantidade_sacos_sub - 0.5)) {
					return "CONCLUIDO";
				} else if (quantidade_recebida < quantidade_sacos_sub && quantidade_recebida > 0) {
					return "RECEBENDO";

				} else if (quantidade_recebida == 0) {
					return "A RECEBER";

				}
			}
			case status_carregamento: {
				double quantidade_recebida = contrato.getQuantidade_recebida();
				double quantidade_carregada = contrato.getQuantidade_carregada();

				double quantidade_sacos_sub = 0;
				double quantidade_quilogramas_sub = 0;

				if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos_sub = contrato.getQuantidade();
					quantidade_quilogramas_sub = contrato.getQuantidade() * 60;
				} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_quilogramas_sub = contrato.getQuantidade();
					quantidade_sacos_sub = contrato.getQuantidade() / 60;

				}

				if (quantidade_recebida >= (quantidade_sacos_sub - 0.5)) {
					// recebimento concluido
					if (quantidade_carregada >= (quantidade_recebida - 0.5)) {
						return "CONCLUIDO";
					} else if (quantidade_carregada < quantidade_recebida && quantidade_carregada > 0) {
						return "CARREGANDO";

					} else if (quantidade_carregada == 0)
						return "A CARREGAR";

				} else if (quantidade_recebida < quantidade_sacos_sub && quantidade_recebida > 0) {
					if (quantidade_carregada >= (quantidade_recebida - 0.5)) {
						return "CARREGADO PARC";
					} else if (quantidade_carregada < quantidade_recebida
							&& quantidade_carregada > (quantidade_recebida / 2)) {
						return "CARREGANDO";

					} else if (quantidade_carregada == 0) {
						return "A CARREGAR";

					}
				} else if (quantidade_recebida == 0) {
					if (quantidade_carregada > 0) {
						return "CARREGANDO";
					} else if (quantidade_carregada <= 0) {
						return "AG RECEBER";
					}

				}

			}
			case status_pagamento: {
				double quantidade_paga = contrato.getTotal_pago();

				double valor_total = contrato.getValor_a_pagar().doubleValue();

				if (quantidade_paga >= (valor_total - 0.5)) {
					return "CONCLUIDO";
				} else if (quantidade_paga < valor_total && quantidade_paga > 0) {
					return "PAGANDO";

				} else if (quantidade_paga == 0) {
					return "A PAGAR";

				}
			}
			case quantidade: {
				/*
				 * double quantidade_sacos = 0; double quantidade_kg = 0;
				 * 
				 * if(contrato.getMedida().equalsIgnoreCase("SACOS")) { quantidade_sacos =
				 * contrato.getQuantidade(); }else
				 * if(contrato.getMedida().equalsIgnoreCase("KG")) { quantidade_kg =
				 * contrato.getQuantidade(); quantidade_sacos = quantidade_kg / 60; }
				 */
				String t = z.format(contrato.getQuantidade());
				return t;
			}
			case medida:
				return contrato.getMedida();
			case produto:
				return contrato.getProduto().toUpperCase();
			case transgenia: {
				return contrato.getModelo_safra().getProduto().getTransgenia().toUpperCase();
			}
			case safra:
				return contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita();
			case valor_produto: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto());
				return valorString;

			}
			case valor_total: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar());
				return valorString;

			}
			case corretores: {
				String corretores = "";
				if (contrato.getNomes_corretores() != null) {
					corretores = contrato.getNomes_corretores().toUpperCase();
				}
				return corretores;
			}
			case data_contrato:
				return contrato.getData_contrato();
			case local_retirada: {
				GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
				if (contrato.getId_local_retirada() > 0) {
					CadastroCliente local_retirar = gerenciar.getCliente(contrato.getId_local_retirada());
					if (local_retirar != null) {
						if (local_retirar.getTipo_pessoa() == 0) {
							// pessoa fisica
							return local_retirar.getNome_empresarial();
						} else {
							return local_retirar.getNome_fantaia();
						}
					} else {
						return "Não Especificado";
					}
				} else {
					return "Não Especificado";

				}

			}
			case bruto_livre:
				return contrato.getBruto_livre();
			case penhor: {
				return contrato.getStatus_penhor();
			}
			case optante_folha: {
				if (contrato.getOptante_folha() == 0) {
					return "Não Optante";
				} else {

					return contrato.getStatus_optante_folha();
				}

			}
			case localizacao:
				return contrato.getLocalizacao();
			case fertilizante:
				return contrato.getFertilizante();
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
			CadastroContrato contrato = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroContrato getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato contrato) {
			return dados.indexOf(contrato);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroContrato contrato) {
			dados.add(contrato);
			fireTableRowsInserted(indexOf(contrato), indexOf(contrato));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato> dadosIn) {
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
		public void onRemove(CadastroContrato contrato) {
			int indexBefore = indexOf(contrato);// pega o indice antes de apagar
			dados.remove(contrato);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroContrato onGet(int row) {
			return dados.get(row);
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

	public ArrayList<CadastroContrato> getContratosSelecionados() {
		ArrayList<CadastroContrato> contratos_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = tabela.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																								// indice do
																								// model
			contratos_selecionados.add(modelo_contratos.getValue(indice));
		}

		return contratos_selecionados;
	}

	public String encutarNomes(String nomes) {

		String nomes_encurtados = "";
		nomes = trimar(nomes);
		
		String nomes_compradores_separados[] =  nomes.split(",");
		
	
		
		
		for (String nome_individual_para_encurtar : nomes_compradores_separados) {

			String nome_destinatario_quebrado[] = nome_individual_para_encurtar.split(" ");
			
			try {

				if (nome_destinatario_quebrado.length > 2) {
					if (nome_destinatario_quebrado[2].length() > 3) {
						nome_individual_para_encurtar = nome_destinatario_quebrado[0] + " "
								+ nome_destinatario_quebrado[2];
					} else {
						if (nome_destinatario_quebrado[3].length() > 3) {
							nome_individual_para_encurtar = nome_destinatario_quebrado[0] + " "
									+ nome_destinatario_quebrado[3];

						} else {
							nome_individual_para_encurtar = nome_destinatario_quebrado[0] + " "
									+ nome_destinatario_quebrado[1];

						}
					}
				}

			} catch (Exception v) {
				nome_individual_para_encurtar = nome_individual_para_encurtar;
			}

			nomes_encurtados += (nome_individual_para_encurtar + ", ");
		}

		return nomes_encurtados;
	}

}
