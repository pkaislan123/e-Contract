package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.RowFilter.ComparisonType;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaEscolhaRelatorioRomaneios;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
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
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import net.miginfocom.swing.MigLayout;

public class TelaRomaneios extends JFrame {
	private FileChooser file_chooser;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JDialog tela_pai;
	private ArrayList<CadastroRomaneio> lista_romaneios = new ArrayList<>();

	private JTable table_nfs;
	private TelaRomaneios isto;
	private JButton btnSelecionarNota;
	private JDialog telaPai;
	private CadastroCliente cliente_selecionado;
	private int contador = 0;
	private JFileChooser fileChooser_global;
	private ArrayList<String> listadeArquivos = new ArrayList<>();

	private final JPanel painelPrincipal = new JPanel();

	private RomaneioTableModel modelo_romaneios = new RomaneioTableModel();
	private TableRowSorter<RomaneioTableModel> sorter;

	private JTextField entChavePesquisa;
	private JButton btnVizualizarNF;
	private JButton btnExportar;
	private CadastroCliente cliente_global;
	private JTextField entProduto;
	private JButton btnFiltrar;
	private JLabel lblNewLabel;
	private JLabel lblRemetente;
	private JTextField entRemetente;
	private JLabel lblNatureza;
	private JTextField entNatureza;
	private JLabel lblProduto;
	private JLabel lblNewLabel_1;
	private JLabel lblD;
	private JLabel lblAt;
	private JTextField entMenorData;
	private JTextField entMaiorData;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JButton btnImportar;
	private JButton btnNewButton_1;
	private String servidor_unidade;
	private JButton btnNewButton_2;
	private JButton btnReleitura;
	private JTextField entCodigo;
	private JTextField entIdentificacaoDestinatario;
	private JTextField entIdentificacaoRemetente;
	private JLabel lblPesoLiquidoTotal;
	private JLabel lblPesoBrutoTotal, lblPesoTaraTotal;
	private JLabel lblNewLabel_3_1_2;
	private JLabel lblNumeroTotalRomaneios;
	private JPanel panel_4;
	private JLabel lblNomeMotorista;
	private JTextField entNomeMotorista;
	private JLabel lblCpf;
	private JTextField entCpfMotorista;
	private JLabel lblPlaca;
	private JTextField entPlaca;
	private JPanel panel_5;
	private JLabel lblDocEntrada;
	private JTextField entDocEntrada;
	private JLabel lblAmostra;
	private JTextField entAmostra;
	private JLabel lblSilo;
	private JTextField entSilo;
	private JLabel lblTransgenia;
	private JTextField entTransgeniaDefinida;
	private JLabel lblClassificador;
	private JTextField entClassificador;
	private JLabel lblNewLabel_3_1_3;
	private JLabel lblPesoTotalDesconto;
	private JLabel lblNewLabel_3_1_4;
	private JLabel lblPesoLiquidoTotalSemDesconto;
	private JLabel lblNewLabel_5;
	private JLabel lblDescontoTotalUmidade;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblDescontoTotalAvariado;
	private JLabel lblDescontoTotalImpureza;
	private JButton btnExportarPlanilha;

	public TelaRomaneios(int flag_tipo_tela, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(TelaRomaneios.class.getResource("/imagens/icone_notas_fiscais.png")));
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Romaneios");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		isto = this;
		DadosGlobais dados = DadosGlobais.getInstance();

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][10px][grow]", "[][][grow][]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 0 2 3 1,grow");

		table_nfs = new JTable(modelo_romaneios);
		sorter = new TableRowSorter<RomaneioTableModel>(modelo_romaneios);

		table_nfs.setRowSorter(sorter);

		table_nfs.setBackground(new Color(255, 255, 255));

		table_nfs.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table_nfs.getColumnModel().getColumn(0).setPreferredWidth(80);
		table_nfs.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_nfs.getColumnModel().getColumn(2).setPreferredWidth(250);
		table_nfs.getColumnModel().getColumn(3).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(4).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_nfs.getColumnModel().getColumn(6).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(7).setPreferredWidth(250);
		table_nfs.getColumnModel().getColumn(8).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(9).setPreferredWidth(100);
		table_nfs.getColumnModel().getColumn(10).setPreferredWidth(120);
		table_nfs.getColumnModel().getColumn(11).setPreferredWidth(120);
		table_nfs.setRowHeight(30);
		panel.setLayout(null);
		panel.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPaneNFs = new JScrollPane(table_nfs);
		panel.add(scrollPaneNFs);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(TelaRomaneios.class.getResource("/imagens/icone_notas_fiscais.png")));
		painelPrincipal.add(lblNewLabel_2, "cell 0 0,alignx left,aligny top");

		lblNewLabel_4 = new JLabel("     Romaneios");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(lblNewLabel_4, "cell 0 0,alignx left,aligny bottom");

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 2 3,alignx left,aligny top");
		panel_1.setLayout(new MigLayout("", "[248px][81px][167px][][][][4px][]", "[33px][21px][33px][]"));

		btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton_1, "cell 3 0,alignx left,aligny top");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir o Romaneio?", "Excluir Romaneio",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					int rowSel = table_nfs.getSelectedRow();// pega o indice da linha na tabela
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice
																								// do model
					ManipularTxt manipular = new ManipularTxt();
					boolean apagado = manipular
							.apagarArquivo(servidor_unidade + lista_romaneios.get(indexRowModel).getCaminho_arquivo());
					if (apagado) {

						// remover do banco de dados
						GerenciarBancoRomaneios gerenciar = new GerenciarBancoRomaneios();
						boolean excluir = gerenciar
								.removerRomaneio(lista_romaneios.get(indexRowModel).getId_romaneio());
						if (excluir) {
							JOptionPane.showMessageDialog(isto, "Romaneio Excluido");
							modelo_romaneios.onRemove(lista_romaneios.get(indexRowModel));

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir este Romaneio\nConsulte o administrador");

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nConsulte o administrador");
					}
				}
			}
		});

		btnVizualizarNF = new JButton("Vizualizar");
		btnVizualizarNF.setBackground(new Color(0, 0, 51));
		btnVizualizarNF.setForeground(Color.WHITE);
		btnVizualizarNF.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnVizualizarNF, "cell 4 0,alignx left,aligny top");
		btnVizualizarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowSel = table_nfs.getSelectedRow();// pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do
																							// model
				CadastroRomaneio nota_vizualizar = lista_romaneios.get(indexRowModel);

				if (Desktop.isDesktopSupported()) {
					try {
						Desktop desktop = Desktop.getDesktop();
						File myFile = new File(servidor_unidade + nota_vizualizar.getCaminho_arquivo());
						desktop.open(myFile);
					} catch (IOException ex) {
					}
				}
			}
		});

		JButton btnEditarRomaneio = new JButton("Editar");
		btnEditarRomaneio.setBackground(new Color(0, 51, 102));
		btnEditarRomaneio.setForeground(Color.WHITE);
		btnEditarRomaneio.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditarRomaneio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_nfs.getSelectedRow();// pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);

				TelaCadastroRomaneio tela = new TelaCadastroRomaneio(lista_romaneios.get(indexRowModel), isto);
				tela.setVisible(true);
			}
		});
		panel_1.add(btnEditarRomaneio, "cell 5 0,alignx left,aligny top");

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBackground(new Color(0, 51, 0));
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnSelecionar, "cell 7 0,alignx left,aligny top");

		btnExportarPlanilha = new JButton("Exportar Dados");
		btnExportarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<CadastroRomaneio> romaneios_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = table_nfs.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(indice);

					CadastroRomaneio rom = lista_romaneios.get(indexRowModel);
					romaneios_selecionados.add(rom);
				}

				
				TelaEscolhaRelatorioRomaneios escolha_opcoes = new TelaEscolhaRelatorioRomaneios(romaneios_selecionados,isto);
				escolha_opcoes.setVisible(true);
			
			

			}
				
			
		});
		btnExportarPlanilha.setForeground(Color.WHITE);
		btnExportarPlanilha.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExportarPlanilha.setBackground(new Color(102, 102, 0));
		panel_1.add(btnExportarPlanilha, "cell 3 1");

		JButton btnNewButton = new JButton("Exportar Arquivos");
		btnNewButton.setBackground(new Color(102, 51, 204));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton, "cell 4 1 2 1,alignx right,aligny top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportar();

			}
		});

		btnImportar = new JButton("Importar");
		btnImportar.setBackground(new Color(0, 102, 102));
		btnImportar.setForeground(Color.WHITE);
		btnImportar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnImportar, "cell 7 1,alignx center,aligny top");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnNewButton_2 = new JButton("Excluir Todos os Romaneios");
		btnNewButton_2.setBackground(new Color(255, 102, 0));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton_2, "cell 3 2 5 1,alignx center,aligny top");
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir todos os Romaneios?", "Excluir Romaneios",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					try {
						for (CadastroRomaneio rom : lista_romaneios) {
							ManipularTxt manipular = new ManipularTxt();
							boolean apagado = manipular.apagarArquivo(rom.getCaminho_arquivo());
							if (apagado) {
								modelo_romaneios.onRemove(rom);

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir este Romaneio\nConsulte o administrador");
							}
						}
						JOptionPane.showMessageDialog(isto, "Romaneios Excluidos");

					} catch (Exception i) {
						JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nConsulte o administrador");

					}
				}
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		painelPrincipal.add(panel_2, "cell 0 3,grow");
		panel_2.setLayout(new MigLayout("", "[][][][][][][]", "[][][][][][]"));

		lblNewLabel_3_1_2 = new JLabel("Romaneios:");
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1_2, "cell 0 0,alignx right");

		lblNumeroTotalRomaneios = new JLabel("0.0000");
		lblNumeroTotalRomaneios.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNumeroTotalRomaneios, "cell 1 0");

		JLabel lblNewLabel_3 = new JLabel("P.B.T:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3, "cell 0 1,alignx right");

		lblPesoBrutoTotal = new JLabel("0.0000");
		lblPesoBrutoTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoBrutoTotal, "cell 1 1");

		lblNewLabel_5 = new JLabel("Desc Umi:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_5, "cell 3 1");

		lblDescontoTotalUmidade = new JLabel("0.0000");
		lblDescontoTotalUmidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblDescontoTotalUmidade, "cell 4 1");

		JLabel lblNewLabel_3_1 = new JLabel("P. Tara:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1, "cell 0 2,alignx right");

		lblPesoTaraTotal = new JLabel("0.0000");
		lblPesoTaraTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTaraTotal, "cell 1 2");

		lblNewLabel_6 = new JLabel("Desc Imp:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_6, "cell 3 2");

		lblDescontoTotalImpureza = new JLabel("0.0000");
		lblDescontoTotalImpureza.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblDescontoTotalImpureza, "cell 4 2");

		lblNewLabel_3_1_4 = new JLabel("P. Liquido S/ Desc");
		lblNewLabel_3_1_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1_4, "cell 0 3");

		lblPesoLiquidoTotalSemDesconto = new JLabel("0.0000");
		lblPesoLiquidoTotalSemDesconto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoLiquidoTotalSemDesconto, "cell 1 3");

		lblNewLabel_7 = new JLabel("Desc Avr:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_7, "cell 3 3");

		lblDescontoTotalAvariado = new JLabel("0.0000");
		lblDescontoTotalAvariado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblDescontoTotalAvariado, "cell 4 3");

		lblNewLabel_3_1_3 = new JLabel("Desc Total:");
		lblNewLabel_3_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1_3, "cell 3 4,alignx right");

		lblPesoTotalDesconto = new JLabel("0.0000");
		lblPesoTotalDesconto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalDesconto, "cell 4 4");

		JLabel lblNewLabel_3_1_1 = new JLabel("P. Liquido Final:");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1_1, "cell 0 5,alignx right");

		lblPesoLiquidoTotal = new JLabel("0.0000");
		lblPesoLiquidoTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoLiquidoTotal, "cell 1 5");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelPrincipal.add(panel_3, "cell 0 1 3 1,growx,aligny top");
		panel_3.setLayout(new MigLayout("", "[][][grow][66px][grow][48px][][61px][4px][63px]",
				"[20px][20px][23px][23px,grow][grow]"));

		lblRemetente = new JLabel("Depositante:");
		panel_3.add(lblRemetente, "cell 0 0,alignx left,aligny top");
		lblRemetente.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entRemetente = new JTextField();
		panel_3.add(entRemetente, "cell 1 0 2 1,growx,aligny top");
		entRemetente.setColumns(10);

		JLabel lblCpfcnpj_1 = new JLabel("CPF/CNPJ:");
		panel_3.add(lblCpfcnpj_1, "cell 3 0,alignx left,aligny top");
		lblCpfcnpj_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entIdentificacaoRemetente = new JTextField();
		panel_3.add(entIdentificacaoRemetente, "cell 4 0,growx,aligny top");
		entIdentificacaoRemetente.setColumns(10);

		lblNewLabel_1 = new JLabel("Periodo");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_1, "cell 6 0,alignx center,aligny center");

		lblNewLabel = new JLabel("Destinatario:");
		panel_3.add(lblNewLabel, "cell 0 1,alignx left,aligny top");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entChavePesquisa = new JTextField();
		panel_3.add(entChavePesquisa, "cell 1 1 2 1,growx,aligny top");
		entChavePesquisa.setColumns(10);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		panel_3.add(lblCpfcnpj, "cell 3 1,alignx left,aligny top");
		lblCpfcnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entIdentificacaoDestinatario = new JTextField();
		panel_3.add(entIdentificacaoDestinatario, "cell 4 1,growx,aligny top");
		entIdentificacaoDestinatario.setColumns(10);

		lblD = new JLabel("De:");
		panel_3.add(lblD, "cell 5 1,alignx right,aligny top");
		lblD.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entMenorData = new JTextField();
		panel_3.add(entMenorData, "cell 6 1,growx,aligny top");
		entMenorData.setColumns(10);

		lblProduto = new JLabel("Produto:");
		panel_3.add(lblProduto, "cell 0 2,alignx right,aligny center");
		lblProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entProduto = new JTextField();
		panel_3.add(entProduto, "cell 1 2 2 1,growx,aligny center");
		entProduto.setColumns(10);

		lblNatureza = new JLabel("Operação:");
		panel_3.add(lblNatureza, "cell 3 2,alignx left,aligny center");
		lblNatureza.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entNatureza = new JTextField();
		panel_3.add(entNatureza, "cell 4 2,growx,aligny center");
		entNatureza.setColumns(10);

		lblAt = new JLabel("Até:");
		panel_3.add(lblAt, "cell 5 2,alignx right,aligny top");
		lblAt.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entMaiorData = new JTextField();
		panel_3.add(entMaiorData, "cell 6 2,growx,aligny center");
		entMaiorData.setColumns(10);

		btnReleitura = new JButton("Refazer Pesquisar");
		btnReleitura.setBackground(new Color(0, 51, 0));
		btnReleitura.setForeground(Color.WHITE);
		btnReleitura.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(btnReleitura, "cell 7 2 3 1,growx,aligny top");
		btnReleitura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarTodosOsRomaneios();

			}
		});

		panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_3.add(panel_4, "cell 0 3 5 1,grow");
		panel_4.setLayout(new MigLayout("", "[][grow][][grow][][grow]", "[]"));

		lblNomeMotorista = new JLabel("Nome Motorista:");
		lblNomeMotorista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblNomeMotorista, "cell 0 0,alignx trailing");

		entNomeMotorista = new JTextField();
		entNomeMotorista.setColumns(10);
		panel_4.add(entNomeMotorista, "cell 1 0,growx");

		lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblCpf, "cell 2 0,alignx trailing");

		entCpfMotorista = new JTextField();
		entCpfMotorista.setColumns(10);
		panel_4.add(entCpfMotorista, "cell 3 0,growx");

		lblPlaca = new JLabel("Placa:");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblPlaca, "cell 4 0,alignx trailing");

		entPlaca = new JTextField();
		entPlaca.setColumns(10);
		panel_4.add(entPlaca, "cell 5 0,growx");

		JLabel lblCdigo = new JLabel("Código:");
		panel_3.add(lblCdigo, "cell 5 3,alignx right,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entCodigo = new JTextField();
		panel_3.add(entCodigo, "cell 6 3,growx,aligny center");
		entCodigo.setColumns(10);

		JButton btnFiltrar_1 = new JButton("Filtrar");
		btnFiltrar_1.setBackground(new Color(51, 0, 0));
		btnFiltrar_1.setForeground(Color.WHITE);
		btnFiltrar_1.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(btnFiltrar_1, "cell 7 3,alignx center,aligny center");
		btnFiltrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBackground(new Color(204, 0, 0));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(btnLimpar, "cell 9 3,alignx center,aligny center");

		panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_3.add(panel_5, "cell 0 4 10 1,grow");
		panel_5.setLayout(new MigLayout("", "[][grow][][grow][][grow][][grow][][grow]", "[][]"));

		lblDocEntrada = new JLabel("Doc Entrada:");
		lblDocEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblDocEntrada, "cell 0 0,alignx trailing");

		entDocEntrada = new JTextField();
		entDocEntrada.setColumns(10);
		panel_5.add(entDocEntrada, "cell 1 0,growx");

		lblAmostra = new JLabel("Amostra:");
		lblAmostra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblAmostra, "cell 2 0,alignx trailing");

		entAmostra = new JTextField();
		entAmostra.setColumns(10);
		panel_5.add(entAmostra, "cell 3 0,growx");

		lblSilo = new JLabel("Silo:");
		lblSilo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblSilo, "cell 4 0,alignx trailing");

		entSilo = new JTextField();
		entSilo.setColumns(10);
		panel_5.add(entSilo, "cell 5 0,growx");

		lblTransgenia = new JLabel("Transgenia:");
		lblTransgenia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblTransgenia, "cell 6 0,alignx trailing");

		entTransgeniaDefinida = new JTextField();
		entTransgeniaDefinida.setColumns(10);
		panel_5.add(entTransgeniaDefinida, "cell 7 0,growx");

		lblClassificador = new JLabel("Classificador:");
		lblClassificador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_5.add(lblClassificador, "cell 8 0,alignx trailing");

		entClassificador = new JTextField();
		entClassificador.setColumns(10);
		panel_5.add(entClassificador, "cell 9 0,growx");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limpar();
			}
		});

		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_nfs.getSelectedRow();// pega o indice da linha na tabela
				int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice do
																							// model
				if (telaPai instanceof TelaConfirmarRecebimento) {
					((TelaConfirmarRecebimento) telaPai).setRomaneio(lista_romaneios.get(indexRowModel));
				} else if (telaPai instanceof TelaConfirmarCarregamento) {
					((TelaConfirmarCarregamento) telaPai).setRomaneio(lista_romaneios.get(indexRowModel));

				}
				isto.dispose();

			}
		});

		if (flag_tipo_tela == 1) {
			btnSelecionar.setVisible(false);
			btnSelecionar.setEnabled(false);
		}

		this.setLocationRelativeTo(janela_pai);

	}

	public void exportar() {

		ManipularTxt manipular = new ManipularTxt();

		if (table_nfs.getSelectedRows().length > 0) {
			if (table_nfs.getSelectedRows().length == 1) {

				try {
					JOptionPane.showMessageDialog(isto,
							"Na próxima tela, selecione o local e escreva o nome do arquivo sem extensão");
					File pasta_salvar = getDiretorioSalvar();
					int rowSel = table_nfs.getSelectedRows()[0];
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice
																								// do model
					CadastroRomaneio rom = lista_romaneios.get(indexRowModel);

					try {
						boolean copiar = manipular.copiarNFe(rom.getCaminho_arquivo(),
								pasta_salvar.getAbsolutePath() + ".pdf");
						if (copiar) {
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");
						}

					} catch (IOException e) {
						JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");
						e.printStackTrace();
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");

				}
				JOptionPane.showMessageDialog(null, "Sucesso ao exportar NF");

			} else {

				try {
					JOptionPane.showMessageDialog(isto,
							"Na próxima tela, selecione o local e escreva o nome da pasta para salvar os arquivos");

					File pasta_salvar = getDiretorioSalvar();

					int indices[] = table_nfs.getSelectedRows();

					if (!pasta_salvar.exists())
						manipular.criarDiretorio(pasta_salvar.getAbsolutePath());

					for (int i = 0; i < indices.length; i++) {
						int rowSel = indices[i];
						int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																									// indice do model
						CadastroRomaneio rom = lista_romaneios.get(indexRowModel);

						try {
							boolean copiar = manipular.copiarNFe(rom.getCaminho_arquivo(),
									pasta_salvar.getAbsolutePath() + "\\romaneio" + rom.getNumero_romaneio() + ".pdf");
							if (copiar) {

							} else {
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");

							e.printStackTrace();
						}

					}
					JOptionPane.showMessageDialog(isto, "Sucesso ao exportar notas fiscais");

				} catch (Exception t) {
					JOptionPane.showMessageDialog(isto, "Erro ao exportar notas fiscais");

				}

			}
		} else {
			JOptionPane.showMessageDialog(isto, "Nenhuma linha selecionada");
		}

	}

	public File getDiretorioSalvar() {
		// Mostra a dialog de save file
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setPreferredSize(new Dimension(800, 600));

		fileChooser.showSaveDialog(isto);

		File pasta_selecionada = fileChooser.getSelectedFile();

		JOptionPane.showMessageDialog(null, "Pasta para salvar: " + pasta_selecionada.getAbsolutePath());
		return pasta_selecionada;

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		servidor_unidade = configs_globais.getServidorUnidade();
		// usuario logado
		login = dados.getLogin();

	}

	public void setPai(JDialog _pai) {
		this.tela_pai = _pai;
	}

	public void pesquisarTodosOsRomaneios() {
		new Thread() {
			@Override
			public void run() {
				GerenciarBancoRomaneios gerenciar = new GerenciarBancoRomaneios();
				lista_romaneios.clear();
				modelo_romaneios.onRemoveAll();

				ArrayList<CadastroRomaneio> romaneios;

				if (cliente_selecionado == null) {
					romaneios = gerenciar.listarRomaneiosMaisRapido();
				} else {
					romaneios = gerenciar.listarRomaneiosPorCliente(cliente_selecionado.getId());

				}
				for (CadastroRomaneio rom : romaneios) {
					modelo_romaneios.onAdd(rom);
					lista_romaneios.add(rom);
				}

				calcular();
			}
		}.start();
	}

	public static class RomaneioTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int numero_romaneio = 0;
		private final int operacao = 1;

		private final int data = 2;
		private final int produto = 3;
		private final int transgenia = 4;

		private final int safra = 5;
		private final int nome_remetente = 6;
		private final int id_remetente = 7;
		private final int nome_destinatario = 8;
		private final int id_destinatario = 9;
		private final int peso_bruto = 10;
		private final int tara = 11;
		private final int peso_liquido_sem_desconto = 12;
		private final int peso_desconto_umidade = 13;
		private final int peso_desconto_impureza = 14;
		private final int peso_desconto_avariado = 15;
		private final int peso_desconto_total = 16;
		private final int peso_liquido = 17;
		private final int peso_recepcao = 18;
		private final int umidade = 19;
		private final int impureza = 20;
		private final int ardidos = 21;
		private final int avariados = 22;
		private final int cfop = 23;
		private final int descricao = 24;

		private final int motorista = 25;
		private final int cpf_motorista = 26;
		private final int placa = 27;
		private final int doc_entrada = 28;
		private final int amostra = 29;
		private final int silo = 30;
		private final int transgenese = 31;

		private final String colunas[] = { "Número", "Operação", "Data:", "Produto:", "Transgenia:", "Safra:",
				"Depositante:", "CPF/CNPJ Depositante", "Rementente/Destinatario", "CPF/CNPJ Rementente/Destinatario",
				"Peso Bruto:", "Tara:", "Peso Sem Desconto", "Desconto Umidade", "Desconto Impureza",
				"Desconto Avariados", "Desconto Total", "Peso Final:", "Recepção", "Umidade:", "Impureza:", "Ardidos",
				"Avariados", "CFOP", "Descrição", "Motorista", "CPF MOTORISTA", "Placa", "Doc Entrada", "Amostra",
				"Silo", "Transgenese" };
		private final ArrayList<CadastroRomaneio> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public RomaneioTableModel() {

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
			case numero_romaneio:
				return int.class;
			case operacao:
				return String.class;
			case data:
				return Date.class;
			case produto:
				return String.class;
			case transgenia:
				return String.class;
			case safra:
				return String.class;
			case nome_remetente:
				return String.class;
			case id_remetente:
				return String.class;
			case nome_destinatario:
				return String.class;
			case id_destinatario:
				return String.class;

			case peso_bruto:
				return Double.class;
			case tara:
				return Double.class;
			case peso_liquido_sem_desconto:
				return Double.class;
			case peso_desconto_umidade:
				return Double.class;
			case peso_desconto_impureza:
				return Double.class;
			case peso_desconto_avariado:
				return Double.class;
			case peso_desconto_total:
				return Double.class;
			case peso_liquido:
				return Double.class;
			case peso_recepcao:
				return Double.class;
			case umidade:
				return Double.class;
			case impureza:
				return Double.class;
			case ardidos:
				return Double.class;
			case avariados:
				return Double.class;
			case cfop:
				return String.class;
			case descricao:
				return String.class;
			case motorista:
				return String.class;
			case cpf_motorista:
				return String.class;
			case placa:
				return String.class;
			case doc_entrada:
				return String.class;
			case amostra:
				return String.class;
			case silo:
				return String.class;
			case transgenese:
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

			// pega o dados corrente da linha
			CadastroRomaneio romaneio = dados.get(rowIndex);
			CadastroCliente remetente = romaneio.getRemetente();
			CadastroCliente destinatario = romaneio.getDestinatario();

			// retorna o valor da coluna
			switch (columnIndex) {
			case numero_romaneio:
				return romaneio.getNumero_romaneio();
			case operacao:
				return romaneio.getOperacao().toUpperCase();
			case data:
				return romaneio.getData();
			case produto: {
				try {
					CadastroProduto prod = romaneio.getProduto();
					return prod.getNome_produto();
				} catch (Exception h) {
					// JOptionPane.showMessageDialog(null, "O romaneio codigo: " +
					// romaneio.getNumero_romaneio() + " possui erro no produto");
				}
			}
			case transgenia:
				return romaneio.getProduto().getTransgenia().toUpperCase();
			case safra: {
				CadastroSafra safra = romaneio.getSafra();
				return safra.getAno_plantio() + "/" + safra.getAno_colheita();

			}
			case nome_remetente: {
				try {
					String nome_cliente = "";

					if (remetente != null) {
						if (remetente.getTipo_pessoa() == 0) {
							nome_cliente = remetente.getNome_empresarial().toUpperCase();
						} else
							nome_cliente = remetente.getNome_fantaia().toUpperCase();
					}
					return nome_cliente;
				} catch (Exception e) {
					return "";
				}
			}
			case id_remetente: {
				try {
					if (remetente.getTipo_pessoa() == 0)
						return remetente.getCpf();
					else
						return remetente.getCnpj();
				} catch (Exception e) {
					return "";
				}
			}
			case nome_destinatario: {
				try {
					String nome_cliente = "";

					if (destinatario != null) {
						if (destinatario.getTipo_pessoa() == 0) {
							nome_cliente = destinatario.getNome_empresarial().toUpperCase();
						} else
							nome_cliente = destinatario.getNome_fantaia().toUpperCase();
					}
					return nome_cliente;
				} catch (Exception e) {
					return "";
				}
			}
			case id_destinatario: {
				try {
					if (destinatario.getTipo_pessoa() == 0)
						return destinatario.getCpf();
					else
						return destinatario.getCnpj();
				} catch (Exception e) {
					return "";
				}
			}
			case peso_bruto:
				return romaneio.getPeso_bruto();
			case tara:
				return romaneio.getTara();
			case peso_liquido_sem_desconto:
				return romaneio.getPeso_liquido_sem_descontos();
			case peso_desconto_umidade:
				return romaneio.getPeso_desconto_umidade();
			case peso_desconto_impureza:
				return romaneio.getPeso_desconto_impureza();
			case peso_desconto_avariado:
				return romaneio.getPeso_desconto_avariados();
			case peso_desconto_total:
				return romaneio.getPeso_desconto_total();
			case peso_liquido:
				return romaneio.getPeso_liquido();
			case peso_recepcao:
				return romaneio.getDespesa_recepcao();
			case umidade:
				return romaneio.getUmidade();
			case impureza:
				return romaneio.getInpureza();
			case ardidos:
				return romaneio.getArdidos();
			case avariados:
				return romaneio.getAvariados();
			case cfop: {
				try {
					if (romaneio.getCfop() != null) {
						return romaneio.getCfop();

					} else {
						return "";
					}
				} catch (Exception e) {
					return "";

				}
			}
			case descricao: {
				try {
					if (romaneio.getDescricao_cfop().toUpperCase() != null) {
						return romaneio.getDescricao_cfop().toUpperCase();
					} else {
						return "";

					}
				} catch (Exception e) {
					return "";

				}
			}
			case motorista: {
				return romaneio.getNome_motorista();
			}
			case cpf_motorista: {
				return romaneio.getCpf_motorista();
			}
			case placa: {
				return romaneio.getPlaca();
			}
			case doc_entrada:
				return romaneio.getDoc_entrada();
			case amostra:
				return romaneio.getAmostra();
			case silo:
				return romaneio.getSilo();
			case transgenese:
				return romaneio.getTransgenia();
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
			CadastroRomaneio nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroRomaneio getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroRomaneio nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroRomaneio nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroRomaneio> dadosIn) {
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
		public void onRemove(CadastroRomaneio nota) {
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

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void setDadosPesquisa(String destinatario, String remetente, String natureza, String produto,
			String codigo) {

		sorter.setRowFilter(RowFilter.regexFilter(""));

		entChavePesquisa.setText(destinatario);
		entRemetente.setText(remetente);
		entNatureza.setText(natureza);
		entProduto.setText(produto);
		entCodigo.setText(codigo);

		filtrar();
	}

	public void filtrar() {
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String produto = entProduto.getText().toUpperCase();
		String destinatario = entChavePesquisa.getText().toUpperCase();
		String remetente = entRemetente.getText().toUpperCase();
		String natureza = entNatureza.getText().toUpperCase();
		String codigo = entCodigo.getText().toUpperCase();
		String identificacaoRemetente = entIdentificacaoRemetente.getText().toUpperCase();
		String identificacaoDestinatario = entIdentificacaoDestinatario.getText().toUpperCase();
		String nome_motorista = entNomeMotorista.getText().toUpperCase();
		String cpf_motorista = entCpfMotorista.getText().toUpperCase();
		String placa = entPlaca.getText().toUpperCase();
		String amostra = entAmostra.getText().toUpperCase();
		String silo = entSilo.getText().toUpperCase();
		String classificador = entSilo.getText().toUpperCase();
		String doc_entrada = entDocEntrada.getText().toUpperCase();
		String transgenese = entTransgeniaDefinida.getText().toUpperCase();

		String menor = entMenorData.getText();
		String maior = entMaiorData.getText();

		if (checkString(menor) && checkString(maior)) {
			Date data_menor = null;
			Date data_maior = null;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(menor);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(maior);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}

			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 2));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 2));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 2));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 2));
			filters.add(RowFilter.orFilter(datas_maior));
		}

		if (checkString(remetente))
			filters.add(RowFilter.regexFilter(remetente, 6));

		if (checkString(natureza))
			filters.add(RowFilter.regexFilter(natureza, 1));

		if (checkString(destinatario))
			filters.add(RowFilter.regexFilter(destinatario, 8));

		if (checkString(produto))
			filters.add(RowFilter.regexFilter(produto, 3));

		if (checkString(codigo))
			filters.add(RowFilter.regexFilter(codigo, 0));

		if (checkString(identificacaoRemetente))
			filters.add(RowFilter.regexFilter(identificacaoRemetente, 7));

		if (checkString(identificacaoDestinatario))
			filters.add(RowFilter.regexFilter(identificacaoDestinatario, 9));

		if (checkString(nome_motorista))
			filters.add(RowFilter.regexFilter(nome_motorista, 25));

		if (checkString(cpf_motorista))
			filters.add(RowFilter.regexFilter(cpf_motorista, 26));

		if (checkString(placa))
			filters.add(RowFilter.regexFilter(placa, 27));

		if (checkString(amostra))
			filters.add(RowFilter.regexFilter(amostra, 29));

		if (checkString(silo))
			filters.add(RowFilter.regexFilter(silo, 30));

		if (checkString(doc_entrada))
			filters.add(RowFilter.regexFilter(doc_entrada, 28));

		if (checkString(transgenese))
			filters.add(RowFilter.regexFilter(transgenese, 31));

		sorter.setRowFilter(RowFilter.andFilter(filters));
		calcular();
	}

	public void limpar() {
		sorter.setRowFilter(RowFilter.regexFilter(""));
		calcular();
	}

	public void setClienteSelecionado(CadastroCliente cliente) {
		this.cliente_selecionado = cliente;
	}

	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
	}

	public void calcular() {

		NumberFormat z = NumberFormat.getNumberInstance();

		int numero_romaneios = 0;

		double peso_bruto_total = 0, peso_tara_total = 0, peso_liquido_total_sem_desconto = 0, peso_liquido_total = 0;
		double peso_desconto_umidade = 0;
		double peso_desconto_impureza = 0;
		double peso_desconto_avariado = 0;
		double peso_desconto_total = 0;
		double peso_recepcao = 0;

		for (int row = 0; row < table_nfs.getRowCount(); row++) {

			int index = table_nfs.convertRowIndexToModel(row);
			CadastroRomaneio romaneio = modelo_romaneios.getValue(index);

			peso_bruto_total += romaneio.getPeso_bruto();
			peso_tara_total += romaneio.getTara();
			peso_liquido_total += romaneio.getPeso_liquido();
			peso_liquido_total_sem_desconto += romaneio.getPeso_liquido_sem_descontos();

			peso_desconto_umidade += romaneio.getPeso_desconto_umidade();
			peso_desconto_impureza += romaneio.getPeso_desconto_impureza();
			peso_desconto_avariado += romaneio.getPeso_desconto_avariados();
			peso_desconto_total += romaneio.getPeso_desconto_total();
			peso_recepcao += romaneio.getDespesa_recepcao();

			numero_romaneios++;

		}

		// valores

		lblPesoBrutoTotal.setText(z.format(peso_bruto_total) + " Kgs | " + z.format(peso_bruto_total / 60) + " sacos");
		lblPesoLiquidoTotal
				.setText(z.format(peso_liquido_total) + " Kgs | " + z.format(peso_liquido_total / 60) + " sacos");
		lblPesoLiquidoTotalSemDesconto.setText(z.format(peso_liquido_total_sem_desconto) + " Kgs | "
				+ z.format(peso_liquido_total_sem_desconto / 60) + " sacos");

		lblDescontoTotalUmidade
				.setText(z.format(peso_desconto_umidade) + " Kgs | " + z.format(peso_desconto_umidade / 60) + " sacos");
		lblDescontoTotalImpureza.setText(
				z.format(peso_desconto_impureza) + " Kgs | " + z.format(peso_desconto_impureza / 60) + " sacos");
		lblDescontoTotalAvariado.setText(
				z.format(peso_desconto_avariado) + " Kgs | " + z.format(peso_desconto_avariado / 60) + " sacos");
		lblPesoTotalDesconto
				.setText(z.format(peso_desconto_total) + " Kgs | " + z.format(peso_desconto_total / 60) + " sacos");

		lblPesoTaraTotal.setText(z.format(peso_tara_total) + " Kgs | " + z.format(peso_tara_total / 60) + " sacos");
		lblNumeroTotalRomaneios.setText(numero_romaneios + " Romaneios");

	}
	
	

	
}