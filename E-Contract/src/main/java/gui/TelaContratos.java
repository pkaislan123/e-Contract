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
	private JRadioButton rdContratos,rdSubContratos;
	private ArrayList<TelaGerenciarContrato>  lista_telas_contratos = new ArrayList<>(); 
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
		painelPrincipal.setLayout(new MigLayout("", "[grow][632px][grow]", "[153.00px][grow][165.00px]"));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrincipal.add(panel_5, "cell 0 0 3 1,alignx center,aligny center");
		panel_5.setLayout(
				new MigLayout("", "[58px][274px][48px][306px][90px][199px][67px,grow][126px][59px]", "[28px,grow][28px][28px]"));

		JLabel lblNewLabel = new JLabel("Comprador:");
		panel_5.add(lblNewLabel, "cell 0 0,alignx left,aligny center");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeComprador = new JTextField();
		entNomeComprador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_5.add(entNomeComprador, "cell 1 0,growx,aligny top");
		entNomeComprador.setColumns(10);

		JLabel lblCdigo = new JLabel("Código:");
		panel_5.add(lblCdigo, "cell 2 0,alignx left,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entCodigo = new JTextField();
		entCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_5.add(entCodigo, "cell 3 0,growx,aligny top");
		entCodigo.setColumns(10);

		JLabel lblLocalRetirada = new JLabel("Local Retirada:");
		panel_5.add(lblLocalRetirada, "cell 4 0,alignx left,aligny center");
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
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_5.add(panel_6, "cell 6 0 3 1,alignx center,growy");
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

		JLabel lblVendedor = new JLabel("Vendedor:");
		panel_5.add(lblVendedor, "cell 0 1,alignx left,aligny center");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNomeVendedor = new JTextField();
		entNomeVendedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_5.add(entNomeVendedor, "cell 1 1,growx,aligny top");
		entNomeVendedor.setColumns(10);

		JLabel lblStatus = new JLabel("Status:");
		panel_5.add(lblStatus, "cell 2 1,alignx left,aligny center");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entStatus = new JTextField();
		entStatus.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_5.add(entStatus, "cell 3 1,growx,aligny top");
		entStatus.setColumns(10);

		JButton btnRefazerPesquisa = new JButton("Refazer Pesquisa");
		btnRefazerPesquisa.setBackground(new Color(0, 51, 0));
		btnRefazerPesquisa.setForeground(Color.WHITE);
		btnRefazerPesquisa.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(btnRefazerPesquisa, "cell 7 1,alignx left,aligny top");
		btnRefazerPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (flag_retorno == 3 || flag_retorno == 4 ) {
					pesquisar_sub_contratos(id_contrato_pai_para_replica_global);

				} else if(rdSubContratos.isSelected()){
					pesquisar_sub_contratos();
				}else {
					pesquisar();

				}
			}
		});

		JLabel lblProduto = new JLabel("Produto:");
		panel_5.add(lblProduto, "cell 0 2,alignx left,aligny center");
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
		panel_5.add(lblSafra, "cell 2 2,alignx left,aligny center");
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
		panel_5.add(lblTransgnese, "cell 4 2,alignx left,aligny center");
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

		JButton btnLimparFiltros = new JButton("Limpar Pesquisa");
		btnLimparFiltros.setBackground(new Color(255, 51, 51));
		btnLimparFiltros.setForeground(Color.WHITE);
		btnLimparFiltros.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(btnLimparFiltros, "cell 6 2,alignx left,aligny top");
		btnLimparFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));

				NumberFormat z = NumberFormat.getNumberInstance();

				double quantidade_sacos_total = 0;
				double quantidade_kg_total = 0;

				int total_contratos_em_analise = 0;
				int total_contratos_assinar = 0;
				int total_contratos_assinado = 0;
				int total_contratos_concluido = 0;
				for (int row = 0; row < tabela.getRowCount(); row++) {

					int index = tabela.convertRowIndexToModel(row);
					CadastroContrato contrato = modelo_contratos.getValue(index);
					double quantidade_sacos = 0;
					double quantidade_kg = 0;

					if (contrato.getMedida().equalsIgnoreCase("SACOS")) {
						quantidade_sacos = contrato.getQuantidade();
						quantidade_kg = contrato.getQuantidade() * 60;

					} else if (contrato.getMedida().equalsIgnoreCase("KG")) {
						quantidade_kg = contrato.getQuantidade();
						quantidade_sacos = quantidade_kg / 60;
					}

					quantidade_sacos_total += quantidade_sacos;
					quantidade_kg_total += quantidade_kg;

					if (contrato.getStatus_contrato() == 0) {
						total_contratos_em_analise++;
					} else if (contrato.getStatus_contrato() == 1) {
						total_contratos_assinar++;
					} else if (contrato.getStatus_contrato() == 2) {
						total_contratos_assinado++;
					} else if (contrato.getStatus_contrato() == 4) {
						total_contratos_concluido++;
					}

				}
				lblNumContratos.setText(tabela.getRowCount() + "");
				lblTotalSacosKGs
						.setText(z.format(quantidade_sacos_total) + " Scs | " + z.format(quantidade_kg_total) + " Kgs");
				/*
				 * lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
				 * lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
				 * lblTotalContratosConcluidos.setText(total_contratos_assinado+ "");
				 * lblTotalContratosAssinados.setText(total_contratos_concluido + "");
				 */

				lblTotalContratosEmAnalise.setText(total_contratos_em_analise + " - "
						+ ((int) (((double) ((double) total_contratos_em_analise / (double) tabela.getRowCount())
								* 100)))
						+ "%");
				lblTotalContratosAssinar.setText(total_contratos_assinar + " - "
						+ ((int) (((double) ((double) total_contratos_assinar / (double) tabela.getRowCount()) * 100)))
						+ "%");
				lblTotalContratosConcluidos.setText(total_contratos_concluido + " - "
						+ ((int) (((double) ((double) total_contratos_concluido / (double) tabela.getRowCount()))
								* 100))
						+ "%");
				lblTotalContratosAssinados.setText(total_contratos_assinado + " - "
						+ ((int) (((double) ((double) total_contratos_assinado / (double) tabela.getRowCount()) * 100)))
						+ "%");

			}
		});
		
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
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLimparCampos.setBackground(new Color(204, 0, 0));
		panel_5.add(btnLimparCampos, "cell 7 2,growx");

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBackground(new Color(0, 51, 255));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(btnFiltrar, "cell 8 2,growx,aligny top");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				filtrar();
			}
		});

		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 1 3 1,grow");

		tabela = new JTable(modelo_contratos);
		tabela.setDefaultRenderer(Object.class, renderer);

		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(180);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(50);
		tabela.getColumnModel().getColumn(7).setPreferredWidth(70);
		tabela.getColumnModel().getColumn(8).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(9).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(10).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(11).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(12).setPreferredWidth(120);

		tabela.setRowSorter(sorter);

		tabela.setBackground(new Color(255, 255, 255));
		// tabela.setPreferredSize(new Dimension(0, 200));
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela.setRowHeight(30);

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

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelPrincipal.add(panel_2, "cell 0 2,alignx center,aligny center");
		panel_2.setLayout(new MigLayout("", "[48px][115px][24px]", "[19px][19px][19px][19px]"));

		JLabel lblNewLabel_4 = new JLabel("");
		panel_2.add(lblNewLabel_4, "cell 0 0,grow");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(255, 69, 0));

		JLabel lblNewLabel_5 = new JLabel("Contrato em Análise:");
		panel_2.add(lblNewLabel_5, "cell 1 0,alignx center,aligny center");

		lblTotalContratosEmAnalise = new JLabel("999");
		panel_2.add(lblTotalContratosEmAnalise, "cell 2 0,alignx left,aligny center");
		lblTotalContratosEmAnalise.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_1 = new JLabel("");
		panel_2.add(lblNewLabel_4_1, "cell 0 1,grow");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setBackground(Color.yellow);

		JLabel lblNewLabel_5_1 = new JLabel("Recolher Assinatura:");
		panel_2.add(lblNewLabel_5_1, "cell 1 1,alignx center,aligny center");

		lblTotalContratosAssinar = new JLabel("999");
		panel_2.add(lblTotalContratosAssinar, "cell 2 1,alignx left,aligny center");
		lblTotalContratosAssinar.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_2 = new JLabel("");
		panel_2.add(lblNewLabel_4_2, "cell 0 2,grow");
		lblNewLabel_4_2.setOpaque(true);
		lblNewLabel_4_2.setBackground(new Color(95, 159, 159));

		JLabel lblNewLabel_5_2 = new JLabel("Assinado:");
		panel_2.add(lblNewLabel_5_2, "cell 1 2,alignx right,aligny center");

		lblTotalContratosAssinados = new JLabel("999");
		panel_2.add(lblTotalContratosAssinados, "cell 2 2,alignx left,aligny center");
		lblTotalContratosAssinados.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_4_3 = new JLabel("");
		panel_2.add(lblNewLabel_4_3, "cell 0 3,grow");
		lblNewLabel_4_3.setOpaque(true);
		lblNewLabel_4_3.setBackground(new Color(0, 100, 0));

		JLabel lblNewLabel_5_3 = new JLabel("Concluido:");
		panel_2.add(lblNewLabel_5_3, "cell 1 3,alignx right,aligny center");

		lblTotalContratosConcluidos = new JLabel("999");
		panel_2.add(lblTotalContratosConcluidos, "cell 2 3,alignx left,aligny center");
		lblTotalContratosConcluidos.setFont(new Font("SansSerif", Font.BOLD, 14));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelPrincipal.add(panel_3, "cell 2 2,alignx center,aligny center");
		panel_3.setLayout(new MigLayout("", "[239px][152px][333px]", "[39.00px][50px]"));

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

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_3.add(panel_4, "cell 0 1 3 1,grow");
		panel_4.setLayout(new MigLayout("", "[73px][152px][74px][87px][81px][106px]", "[36px]"));

		JButton btnExportar = new JButton("Exportar Dados");
		btnExportar.setBackground(new Color(51, 0, 51));
		btnExportar.setForeground(Color.WHITE);
		btnExportar.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(btnExportar, "cell 0 0,alignx left,aligny center");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Contratos");

							
				// Definindo alguns padroes de layout
				//sheet.setDefaultColumnWidth(10);
				sheet.setDefaultRowHeight((short) 400);
				sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
				sheet.getPrintSetup().setLandscape(false);
				sheet.setMargin(Sheet.RightMargin, 0);
				sheet.setMargin(Sheet.LeftMargin, 0);
				sheet.setMargin(Sheet.TopMargin, 0);
				sheet.setMargin(Sheet.BottomMargin, 0);

				
				int rownum = 0;
				int cellnum = 0;
				Cell cell;
				Row row;

				// Configurando estilos de células (Cores, alinhamento, formatação, etc..)
				HSSFDataFormat numberFormat = workbook.createDataFormat();

				CellStyle headerStyle = workbook.createCellStyle();
				headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				// headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				headerStyle.setAlignment(HorizontalAlignment.CENTER);
				headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				headerStyle.setBorderTop(BorderStyle.MEDIUM);
				headerStyle.setBorderBottom(BorderStyle.MEDIUM);
				headerStyle.setBorderLeft(BorderStyle.MEDIUM);
				headerStyle.setBorderRight(BorderStyle.MEDIUM);
				
				HSSFFont newFontNegrita = workbook.createFont();
				newFontNegrita.setBold(true);
				newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
				newFontNegrita.setFontName("Arial");
				newFontNegrita.setItalic(false);
				newFontNegrita.setFontHeightInPoints((short)10);


				headerStyle.setFont(newFontNegrita);

				CellStyle textStyle = workbook.createCellStyle();
				// textStyle.setAlignment(Alignment.CENTER);
				textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				textStyle.setBorderTop(BorderStyle.MEDIUM);
				textStyle.setBorderBottom(BorderStyle.MEDIUM);
				textStyle.setBorderLeft(BorderStyle.MEDIUM);
				textStyle.setBorderRight(BorderStyle.MEDIUM);
				
				HSSFFont newFontNormal = workbook.createFont();
				newFontNormal.setBold(false);
				newFontNormal.setColor(IndexedColors.BLACK.getIndex());
				newFontNormal.setFontName("Arial");
				newFontNormal.setItalic(false);
				newFontNormal.setFontHeightInPoints((short)10);
				textStyle.setFont(newFontNormal);

				CellStyle numberStyle = workbook.createCellStyle();
				numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
				numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				numberStyle.setFont(newFontNormal);
				numberStyle.setBorderTop(BorderStyle.MEDIUM);
				numberStyle.setBorderBottom(BorderStyle.MEDIUM);
				numberStyle.setBorderLeft(BorderStyle.MEDIUM);
				numberStyle.setBorderRight(BorderStyle.MEDIUM);
				
				CellStyle valorStyle = workbook.createCellStyle();
				valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
				valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				valorStyle.setFont(newFontNormal);
				valorStyle.setBorderTop(BorderStyle.MEDIUM);
				valorStyle.setBorderBottom(BorderStyle.MEDIUM);
				valorStyle.setBorderLeft(BorderStyle.MEDIUM);
				valorStyle.setBorderRight(BorderStyle.MEDIUM);
				
				// Configurando Header
				row = sheet.createRow(rownum++);
				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Código");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Comprador");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Vendedores");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Status");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Volume");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Uni");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Produto");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Transgenia");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Safra");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Preço");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Total");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Data CTR");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Local Retirada");

				double valor_total = 0;
				double quantidade_total_sacos = 0;
				double quantidade_total_kgs = 0;

				ArrayList<CadastroContrato> contratos_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = tabela.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = tabela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																										// indice do
																										// model
					CadastroContrato contrato_selecionados = lista_contratos.get(indice);
					contratos_selecionados.add(contrato_selecionados);
				}

				for (CadastroContrato cadastro : contratos_selecionados) {
					row = sheet.createRow(rownum++);
					cellnum = 0;
					/*
					 * codigo compradores vendedores status quantidade medida produto transgenia
					 * safra valor_produto valor_total data_contrato local_retirada
					 */
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(cadastro.getCodigo());

					CadastroCliente corretor[] = cadastro.getCorretores();
					CadastroCliente compradores[] = cadastro.getCompradores();
					CadastroCliente vendedores[] = cadastro.getVendedores();

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(trimar(cadastro.getNomes_compradores().toUpperCase()));


					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(trimar(cadastro.getNomes_vendedores().toUpperCase()));


					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);

					String status = "";
					int status_contrato = cadastro.getStatus_contrato();

					if (status_contrato == 1) {
						status = "RECOLHER ASSINATURAS";
					} else if (status_contrato == 0) {
						status = "Em Aprovação";
					} else if (status_contrato == 2) {
						status = "Assinado";
					} else if (status_contrato == 3) {
						status = "CONCLUIDO";
					}

					cell.setCellValue(status);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(cadastro.getQuantidade());

					double quantidade_local_kg = 0;
					double quantidade_local_sacos = 0;

					if (cadastro.getMedida().equalsIgnoreCase("KG")) {
						quantidade_local_kg = cadastro.getQuantidade();
						quantidade_local_sacos = quantidade_local_kg / 60;
					} else if (cadastro.getMedida().equalsIgnoreCase("Sacos")) {
						quantidade_local_sacos = cadastro.getQuantidade();
						quantidade_local_kg = cadastro.getQuantidade() * 60;
					}

					quantidade_total_sacos = quantidade_total_sacos += quantidade_local_sacos;
					quantidade_total_kgs = quantidade_total_kgs += quantidade_local_kg;

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					String medida = "";
					if(cadastro.getMedida().equalsIgnoreCase("Sacos")) {
						medida = "SC";
					}else if(cadastro.getMedida().equalsIgnoreCase("Kg")) {
						medida = "KG";

					}
					cell.setCellValue(medida);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					String produto = "";
					cell.setCellValue(cadastro.getProduto().replaceAll(" NON-GMO", "").replaceAll("GMO", ""));

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					String transgenia = "";
					if(cadastro.getModelo_safra().getProduto().getTransgenia().contains("CONVENCIONAL")) {
						transgenia = "CONVEN.";
					}else {
						transgenia = "TRANSG.";

					}
					cell.setCellValue(transgenia);

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					String ano_plantio = Integer.toString(cadastro.getModelo_safra().getAno_plantio()).replaceAll("[^0-9]","");
					String ano_colheita =  Integer.toString(cadastro.getModelo_safra().getAno_colheita()).replaceAll("[^0-9]","");

					cell.setCellValue( ano_plantio.substring(ano_plantio.length() - 2) + "/" + ano_colheita.substring(ano_plantio.length() - 2) );
					/*
					 * codigo compradores vendedores status quantidade medida produto transgenia
					 * safra valor_produto valor_total data_contrato local_retirada
					 */
					cell = row.createCell(cellnum++);
					cell.setCellStyle(valorStyle);
					cell.setCellValue(cadastro.getValor_produto());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(valorStyle);
					cell.setCellValue(cadastro.getValor_a_pagar().doubleValue());
					valor_total = valor_total += cadastro.getValor_a_pagar().doubleValue();

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(cadastro.getData_contrato());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);

					GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
					CadastroCliente local_retirada = gerenciar.getCliente(cadastro.getId_local_retirada());
					String s_local_retirada = "";
					if (local_retirada != null) {
						if (local_retirada.getTipo_pessoa() == 0) {
							s_local_retirada = local_retirada.getNome_empresarial();
						} else {
							s_local_retirada = local_retirada.getNome_fantaia();
						}
					}
					cell.setCellValue(s_local_retirada);

				}


				// registra total de sacos, quilogramas, e valor total
				// total de contratos
				row = sheet.createRow(rownum += 2);
				cellnum = 10;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Total Contratos:");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue(contratos_selecionados.size());

				// Quantidade de sacos
				row = sheet.createRow(rownum += 1);
				cellnum = 10;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Total Sacos:");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_sacos);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("sacos");

				// Quantidade de quilogramas
				row = sheet.createRow(rownum += 1);
				cellnum = 10;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Total Kgs:");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("quilogramas");

				// valor total
				row = sheet.createRow(rownum += 1);
				cellnum = 10;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Valor Total:");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(valor_total);

				// criar os filtros
				sheet.setAutoFilter(CellRangeAddress.valueOf("A1:M1"));
				sheet.setAutobreaks(true);
				  sheet.setFitToPage(true);
				  PrintSetup printSetup = sheet.getPrintSetup();
				  printSetup.setFitHeight((short)0);
				  printSetup.setFitWidth((short)1);	
				 
					IntStream.range(0, cellnum).forEach((columnIndex) -> sheet.autoSizeColumn(columnIndex));

				try {

					new JFXPanel();
					Platform.runLater(() -> {

						// pegar ultima pasta
						ManipularTxt manipular_ultima_pasta = new ManipularTxt();
						String ultima_pasta = manipular_ultima_pasta
								.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));

						FileChooser d = new FileChooser();

						d.setInitialDirectory(new File(ultima_pasta));
						d.getExtensionFilters().addAll(

								new FileChooser.ExtensionFilter("Excel", "*.xls"));
						File file = d.showSaveDialog(new Stage());
						String caminho_arquivo = "";
						if (file != null) {
							caminho_arquivo = file.getAbsolutePath();

							manipular_ultima_pasta.rescreverArquivo(
									new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"),
									file.getParent());
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

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBackground(new Color(51, 0, 102));
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 14));
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
					if (contrato_selecionado.getStatus_contrato() != 3) {
						((TelaConfirmarTransferenciaPagamentoContratual) telaPai)
								.setContratoDestintario(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel transferir um pagamento para um contrato finalizado");
					}

				} else if (flag_retorno == 3) {

					if (contrato_selecionado.getStatus_contrato() != 3) {
						((TelaReplicarCarregamento) telaPai).setSubContrato(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel replicar um carregamento para um contrato finalizado");
					}

				} else if (flag_retorno == 4) {

					if (contrato_selecionado.getStatus_contrato() != 3) {

						((TelaReplicarPagamento) telaPai).setSubContrato(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel replicar um pagamento para um contrato finalizado");
					}

				}else if (flag_retorno == 5) {

					if (contrato_selecionado.getStatus_contrato() != 3) {

						((TelaConfirmarTransferenciaCarga) telaPai).setContratoDestintario(contrato_selecionado);
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Não é possivel transferir volume para um contrato finalizado");
					}

				}
				 else if (flag_retorno == 6) {

						if (contrato_selecionado.getStatus_contrato() != 3) {

							((TelaReplicarRecebimento) telaPai).setContratoDestintario(contrato_selecionado);
							isto.dispose();
						} else {
							JOptionPane.showMessageDialog(isto,
									"Não é possivel replicar um pagamento para um contrato finalizado");
						}

					}
				isto.dispose();
			}
		});

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

		if (flag_retorno == 1 || flag_retorno == 2 || flag_retorno == 3 || flag_retorno == 4 ||  flag_retorno == 5  ||  flag_retorno == 6) {
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

		//this.pack();

		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {
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

			if (contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 3 || contrato.getSub_contrato() == 4
					|| contrato.getSub_contrato() == 5) {

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

		/*
		 * lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
		 * lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
		 * lblTotalContratosConcluidos.setText(total_contratos_concluido + "");
		 * lblTotalContratosAssinados.setText(total_contratos_assinado + "");
		 */

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

		/*
		 * lblTotalContratosEmAnalise.setText(total_contratos_em_analise + "");
		 * lblTotalContratosAssinar.setText(total_contratos_assinar+ "");
		 * lblTotalContratosConcluidos.setText(total_contratos_concluido + "");
		 * lblTotalContratosAssinados.setText(total_contratos_assinado + "");
		 */

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

		String produto = entProduto.getText().toUpperCase();
		String comprador = entNomeComprador.getText().toUpperCase();
		String vendedor = entNomeVendedor.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String safra = entSafra.getText().toUpperCase();
		String status = entStatus.getText().toUpperCase();
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
			filters.add(RowFilter.regexFilter(produto, 7));

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 8));

		if (checkString(safra))
			filters.add(RowFilter.regexFilter(safra, 8));

		if (checkString(local_retirada))
			filters.add(RowFilter.regexFilter(local_retirada, 14));

		sorter.setRowFilter(RowFilter.andFilter(filters));

		for (int row = 0; row < tabela.getRowCount(); row++) {

			int index = tabela.convertRowIndexToModel(row);
			CadastroContrato contrato = modelo_contratos.getValue(index);
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

			total_contratos++;

		}
		lblNumContratos.setText(tabela.getRowCount() + "");
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

	public class ContratoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int codigo = 1;
		private final int compradores = 2;
		private final int vendedores = 3;
		private final int status = 4;
		private final int quantidade = 5;
		private final int medida = 6;
		private final int produto = 7;
		private final int transgenia = 8;
		private final int safra = 9;
		private final int valor_produto = 10;
		private final int valor_total = 11;
		private final int corretores = 12;
		private final int data_contrato = 13;
		private final int local_retirada = 14;
		private final int bruto_livre = 15;
		private final int penhor = 16;
		private final int optante_folha = 17;
		private final int localizacao = 18;
		private final int fertilizante = 19;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Código", "Compradores:", "Vendedores:", "Status:", "Quantidade:",
				"Medida:", "Produto:", "Transgênese", "Safra:", "Valor Produto:", "Valor Total:", "Corretores:",
				"Data Contrato", "Local Retirada", "BrutoXLivre", "Penhor", "Optante Folha", "Localização",
				"Fertilizante" };
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

				return trimar(contrato.getNomes_compradores().toUpperCase());

			}
			case vendedores: {
				return trimar(contrato.getNomes_vendedores().toUpperCase());
			}
			case status: {
				int status = contrato.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					return "Recolher Assinaturas".toUpperCase();

				} else if (status == 2) {
					return "Assinado".toUpperCase();

				} else if (status == 3) {
					return "CONCLUIDO".toUpperCase();

				} else if (status == 0) {
					return "Em Aprovação".toUpperCase();

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



}
