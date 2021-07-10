package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.swing.border.EmptyBorder;
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
import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.cadastros.RomaneioCompleto;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTarefaGeral;
import main.java.graficos.GraficoLinha;
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
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;

public class TelaRomaneios extends JFrame {
	private FileChooser file_chooser;

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<CadastroTarefaGeral> lista_tarefas = new ArrayList<>();
	private TarefaTableModel modelo_tarefa = new TarefaTableModel();
	private JDialog tela_pai;
	private ArrayList<CadastroRomaneio> lista_romaneios = new ArrayList<>();
	private JTable table_tarefas;

	private JTable table_nfs;
	private TelaRomaneios isto;
	private JButton btnSelecionarNota;
	private JDialog telaPai;
	private CadastroCliente cliente_selecionado;
	private int contador = 0;
	private JFileChooser fileChooser_global;
	private ArrayList<String> listadeArquivos = new ArrayList<>();
	private ChartPanel chartPanel;
	private DefaultCategoryDataset dataset;
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
	private JLabel lblD;
	private JLabel lblAt;
	private JTextField entMenorData;
	private JTextField entMaiorData;
	private JButton btnImportar;
	private JButton btnNewButton_1;
	private String servidor_unidade;
	private JButton btnReleitura;
	private JTextField entCodigo;
	private JTextField entIdentificacaoDestinatario;
	private JTextField entIdentificacaoRemetente;
	private JLabel lblPesoLiquidoTotal;
	private JLabel lblPesoBrutoTotal, lblPesoTaraTotal;
	private JLabel lblNewLabel_3_1_2;
	private JLabel lblNumeroTotalRomaneios;
	private JLabel lblNomeMotorista;
	private JTextField entNomeMotorista;
	private JLabel lblCpf;
	private JTextField entCpfMotorista;
	private JLabel lblPlaca;
	private JTextField entPlaca;
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
	private JPanel panel_6;
	private JTabbedPane painelAbas;
	private JPanel painelTarefas = new JPanel();
	private JPanel painelDashBoard = new JPanel();
	private JLabel lblNewLabel_8;
	private JScrollPane scrollPaneTarefas;
	private JButton btnAdcionarTarefa;
	private JButton btnResponder;
	private JButton btnExcluirTarefa;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JLabel lblUmidadeMedia;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_13;
	private JLabel lblImpurezaMedia;
	private JLabel lblAvariadosMedia;

	private GraficoLinha linha = null;
	private JPanel painelGraficoLinha;
	private JPanel painelGraficoClassificacao;
	private JScrollPane scrollGrafico;
	private JTabbedPane abasGraficos;
	private JLabel lblMonsanto;
	private JTextField entStatusMonsanto;
	private JLabel lblNewLabel_15;
	private JLabel lblNewLabel_14;
	private JLabel lblTotalRecepcao;
	private JLabel lblNewLabel_16;
	private JLabel lblPesoTotalRoyalt;
	private JScrollPane scrollPane;
	private JPanel panel_4;
	private JLabel lblPerodo;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JLabel lblPesoTotalParticipante;
	private JLabel lblPesoTotalParticular;
	private JLabel lblNewLabel_17;
	private JLabel lblNewLabel_18;
	private JLabel lblPesoTotalDeclarado;
	private JLabel lblPesoTotalADeclarar;
	private JLabel lblNewLabel_19;
	private JLabel lblPesoTotalOutrosParticular;
	private JLabel lblPesoTotalOutrosParticipante;
	private JLabel lblNewLabel_20;
	private JPanel panel_5;
	private JPanel panel_7;
	private JLabel lblNewLabel_21;
	private JLabel lblNewLabel_22;
	private JLabel lblNewLabel_23;
	private JLabel lblNewLabel_24;
	private JLabel lblPesoTaraTotalTara;
	private JLabel lblPesoLiquidoTotalSemDescontoExtrato;
	private JLabel lblNewLabel_25;
	private JLabel lblNewLabel_26;
	private JLabel lblNewLabel_27;
	private JLabel lblNewLabel_28;
	private JLabel lblDescontoTotalImpurezaExtrato;
	private JLabel lblDescontoTotalUmidadeExtrato;
	private JLabel lblDescontoTotalAvariadoExtrato, lblPesoBrutoTotalExtrato, lblTotalEntradaGeral;
	private JLabel lblPesoTotalDescontoExtrato;
	private JLabel lblNewLabel_3_1_5;
	private JLabel lblNewLabel_3_1_6;
	private JLabel lblPesoFisicoFinal;
	private JLabel lblNewLabel_29;
	private JLabel lblTranferenciaEntrada;
	private JLabel lblNewLabel_3_1_7;
	private JLabel lblNewLabel_30;
	private JLabel lblNewLabel_31;
	private JLabel lblNewLabel_32;
	private JLabel lblTransferenciaSaida;
	private JLabel lblQuebraTecnica;
	private JLabel lblNewLabel_3_1_8;
	private JLabel lblTotalSaida;
	private JLabel lblNewLabel_33;
	private JLabel lblNewLabel_34;
	private JLabel lblNewLabel_35;
	private JLabel lblIntacta;
	private JLabel lblArmazenagem;
	private JLabel lblNewLabel_3_1_9;
	private JLabel lblSaldoFinal;
	private JLabel lblNewLabel_36;
	private JLabel lblSaida;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel panel_10;
	private JLabel lblNewLabel_37;
	private JPanel panel_11;
	private JPanel panel_12;
	private JLabel lblNewLabel_38;
	private JLabel lblRecepcao;

	public TelaRomaneios(int flag_tipo_tela, Window janela_pai) {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(TelaRomaneios.class.getResource("/imagens/icone_notas_fiscais.png")));
		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;
		getDadosGlobais();
		setResizable(true);
		setTitle("E-Contract - Romaneios");
		painelAbas = new JTabbedPane();

		painelAbas.setBackground(Color.WHITE);
		painelAbas.setBorder(new EmptyBorder(5, 5, 5, 5));

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
		setContentPane(painelAbas);

		painelDashBoard.setBackground(new Color(255, 255, 255));

		painelAbas.addTab("Romaneios", painelPrincipal);

		panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelAbas.addTab("Extrato", null, panel_5, null);
		panel_5.setLayout(new MigLayout("", "[grow]", "[grow]"));

		panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_5.add(panel_7, "cell 0 0,alignx left,growy");
		panel_7.setLayout(new MigLayout("", "[][][][grow]",
				"[grow][grow][][][grow][][][][][grow][][][grow][][][][][][][][][][][][][][][][][][][][][][][][][][]"));

		panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_9.setBackground(Color.WHITE);
		panel_7.add(panel_9, "cell 0 0 3 1,grow");
		panel_9.setLayout(new MigLayout("", "[][]", "[][][][][]"));

		lblNewLabel_21 = new JLabel("Entradas(+)");
		panel_9.add(lblNewLabel_21, "cell 0 0");
		lblNewLabel_21.setFont(new Font("SansSerif", Font.BOLD, 14));

		lblNewLabel_22 = new JLabel("Peso Bruto:");
		panel_9.add(lblNewLabel_22, "cell 0 1,alignx right");
		lblNewLabel_22.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblPesoBrutoTotalExtrato = new JLabel("0.0000");
		panel_9.add(lblPesoBrutoTotalExtrato, "cell 1 1");
		lblPesoBrutoTotalExtrato.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_23 = new JLabel("Tara:");
		panel_9.add(lblNewLabel_23, "cell 0 2,alignx right");
		lblNewLabel_23.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblPesoTaraTotalTara = new JLabel("0.0000");
		panel_9.add(lblPesoTaraTotalTara, "cell 1 2");
		lblPesoTaraTotalTara.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_24 = new JLabel("Liq. Pesagem:");
		panel_9.add(lblNewLabel_24, "cell 0 3,alignx right");
		lblNewLabel_24.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblPesoLiquidoTotalSemDescontoExtrato = new JLabel("0.0000");
		panel_9.add(lblPesoLiquidoTotalSemDescontoExtrato, "cell 1 3");
		lblPesoLiquidoTotalSemDescontoExtrato.setFont(new Font("Tahoma", Font.BOLD, 16));

		panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.setBackground(Color.WHITE);
		panel_7.add(panel_8, "cell 3 0,grow");
		panel_8.setLayout(new MigLayout("", "[][]", "[][][][][]"));

		lblNewLabel_25 = new JLabel("Descontos(-)");
		panel_8.add(lblNewLabel_25, "cell 0 0");
		lblNewLabel_25.setFont(new Font("SansSerif", Font.BOLD, 14));

		lblNewLabel_26 = new JLabel("Impureza:");
		panel_8.add(lblNewLabel_26, "cell 0 1,alignx right");
		lblNewLabel_26.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblDescontoTotalImpurezaExtrato = new JLabel("0.0000");
		panel_8.add(lblDescontoTotalImpurezaExtrato, "cell 1 1");
		lblDescontoTotalImpurezaExtrato.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_27 = new JLabel("Umidade:");
		panel_8.add(lblNewLabel_27, "cell 0 2,alignx right");
		lblNewLabel_27.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblDescontoTotalUmidadeExtrato = new JLabel("0.000");
		panel_8.add(lblDescontoTotalUmidadeExtrato, "cell 1 2");
		lblDescontoTotalUmidadeExtrato.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_28 = new JLabel("Avariados:");
		panel_8.add(lblNewLabel_28, "cell 0 3,alignx right");
		lblNewLabel_28.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblDescontoTotalAvariadoExtrato = new JLabel("0.0000");
		panel_8.add(lblDescontoTotalAvariadoExtrato, "cell 1 3");
		lblDescontoTotalAvariadoExtrato.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_3_1_5 = new JLabel("Desc Total:");
		panel_8.add(lblNewLabel_3_1_5, "cell 0 4,alignx right");
		lblNewLabel_3_1_5.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblPesoTotalDescontoExtrato = new JLabel("0.0000");
		panel_8.add(lblPesoTotalDescontoExtrato, "cell 1 4");
		lblPesoTotalDescontoExtrato.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblNewLabel_3_1_6 = new JLabel("Total Entrada Fisico:");
		lblNewLabel_3_1_6.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblNewLabel_3_1_6, "cell 1 2");

		lblPesoFisicoFinal = new JLabel("0.0000");
		lblPesoFisicoFinal.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblPesoFisicoFinal, "cell 2 2");

		panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBackground(Color.WHITE);
		panel_7.add(panel_10, "cell 0 4 3 1,grow");
		panel_10.setLayout(new MigLayout("", "[][]", "[][][]"));

		lblNewLabel_37 = new JLabel("Transferências(+)");
		lblNewLabel_37.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_10.add(lblNewLabel_37, "cell 0 0");

		lblNewLabel_29 = new JLabel("Transf. Entrada:");
		panel_10.add(lblNewLabel_29, "cell 0 1,alignx right");
		lblNewLabel_29.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblTranferenciaEntrada = new JLabel("0.0000");
		panel_10.add(lblTranferenciaEntrada, "cell 1 1");
		lblTranferenciaEntrada.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_3_1_7 = new JLabel("Total Entrada Geral:");
		lblNewLabel_3_1_7.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblNewLabel_3_1_7, "cell 1 6");

		lblTotalEntradaGeral = new JLabel("0.0000");
		lblTotalEntradaGeral.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblTotalEntradaGeral, "cell 2 6");

		panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11.setBackground(Color.WHITE);
		panel_7.add(panel_11, "cell 0 8 3 1,grow");
		panel_11.setLayout(new MigLayout("", "[][]", "[][][][][]"));

		lblNewLabel_30 = new JLabel("Saídas(-)");
		panel_11.add(lblNewLabel_30, "cell 0 0");
		lblNewLabel_30.setFont(new Font("SansSerif", Font.BOLD, 14));

		lblNewLabel_36 = new JLabel("Saída Normal:");
		panel_11.add(lblNewLabel_36, "cell 0 1,alignx right");
		lblNewLabel_36.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblSaida = new JLabel("0.0000");
		panel_11.add(lblSaida, "cell 1 1");
		lblSaida.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_31 = new JLabel("Transf. Saída:");
		panel_11.add(lblNewLabel_31, "cell 0 2,alignx right");
		lblNewLabel_31.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblTransferenciaSaida = new JLabel("0.0000");
		panel_11.add(lblTransferenciaSaida, "cell 1 2");
		lblTransferenciaSaida.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_32 = new JLabel("Quebra Técnica:");
		panel_11.add(lblNewLabel_32, "cell 0 3");
		lblNewLabel_32.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblQuebraTecnica = new JLabel("0.0000");
		panel_11.add(lblQuebraTecnica, "cell 1 3");
		lblQuebraTecnica.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_3_1_8 = new JLabel("Saída Total:");
		lblNewLabel_3_1_8.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblNewLabel_3_1_8, "cell 1 10,alignx right");

		lblTotalSaida = new JLabel("0.0000");
		lblTotalSaida.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblTotalSaida, "cell 2 10");

		panel_12 = new JPanel();
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_12.setBackground(Color.WHITE);
		panel_7.add(panel_12, "cell 0 12 3 1,grow");
		panel_12.setLayout(new MigLayout("", "[][]", "[][][][][]"));

		lblNewLabel_33 = new JLabel("Outros(-)");
		panel_12.add(lblNewLabel_33, "cell 0 0");
		lblNewLabel_33.setFont(new Font("SansSerif", Font.BOLD, 14));

		lblNewLabel_34 = new JLabel("Intacta 7.5%:");
		panel_12.add(lblNewLabel_34, "cell 0 1,alignx right");
		lblNewLabel_34.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblIntacta = new JLabel("0.0000");
		panel_12.add(lblIntacta, "cell 1 1");
		lblIntacta.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_35 = new JLabel("Armazenagem:");
		panel_12.add(lblNewLabel_35, "cell 0 2");
		lblNewLabel_35.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblArmazenagem = new JLabel("0.0000");
		panel_12.add(lblArmazenagem, "cell 1 2");
		lblArmazenagem.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblNewLabel_38 = new JLabel("Recepção:");
		lblNewLabel_38.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_12.add(lblNewLabel_38, "cell 0 3,alignx right");

		lblRecepcao = new JLabel("0.0000");
		lblRecepcao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_12.add(lblRecepcao, "cell 1 3");

		lblNewLabel_3_1_9 = new JLabel("Saldo Final:");
		lblNewLabel_3_1_9.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblNewLabel_3_1_9, "cell 1 14,alignx right");

		lblSaldoFinal = new JLabel("0.0000");
		lblSaldoFinal.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_7.add(lblSaldoFinal, "cell 2 14");
		painelAbas.addTab("DashBoard", painelDashBoard);
		painelDashBoard.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));

		abasGraficos = new JTabbedPane(JTabbedPane.TOP);
		painelDashBoard.add(abasGraficos, "cell 0 0 1 2,grow");

		painelGraficoLinha = new JPanel();
		// painelDashBoard.add(painelGraficoLinha, "cell 0 1,grow");
		painelGraficoLinha.setBackground(Color.WHITE);
		painelGraficoLinha.setLayout(new MigLayout("", "[grow]", "[grow]"));

		painelGraficoClassificacao = new JPanel();
		// painelDashBoard.add(painelGraficoLinha, "cell 0 1,grow");
		painelGraficoClassificacao.setBackground(Color.WHITE);
		painelGraficoClassificacao.setLayout(new MigLayout("", "[grow]", "[grow]"));

		abasGraficos.addTab("Romaneios", painelGraficoLinha);
		abasGraficos.addTab("Classificação 1", painelGraficoClassificacao);

		painelPrincipal.setLayout(new MigLayout("", "[grow][][grow][10px][grow]", "[][][235.00,grow][][][]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 0 2 5 1,grow");

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

		JPanel panel_1 = new JPanel();
		painelPrincipal.add(panel_1, "cell 0 3 5 1,alignx right");
		panel_1.setBackground(Color.WHITE);

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

				TelaEscolhaRelatorioRomaneios escolha_opcoes = new TelaEscolhaRelatorioRomaneios(romaneios_selecionados,
						isto);
				escolha_opcoes.setVisible(true);

			}

		});
		panel_1.setLayout(new MigLayout("", "[167px][148px][81px][103px][104px][73px][170px]", "[33px]"));

		JButton btnNewButton = new JButton("Exportar Arquivos");
		btnNewButton.setBackground(new Color(51, 153, 255));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton, "cell 0 0,alignx left,aligny top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportar();

			}
		});
		btnExportarPlanilha.setForeground(Color.WHITE);
		btnExportarPlanilha.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExportarPlanilha.setBackground(new Color(102, 102, 0));
		panel_1.add(btnExportarPlanilha, "cell 1 0,alignx left,aligny top");

		btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton_1, "cell 2 0,alignx left,aligny top");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir o Romaneio?", "Excluir Romaneio",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					int rowSel = table_nfs.getSelectedRow();// pega o indice da linha na tabela
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice
																								// do model
					ManipularTxt manipular = new ManipularTxt();
					String caminho_completo = servidor_unidade + lista_romaneios.get(indexRowModel).getCaminho_arquivo();
					File arquivo = new File(caminho_completo);
					
					if(arquivo.exists()) {
						
					boolean apagado = manipular
							.apagarArquivo(caminho_completo);
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
						JOptionPane.showMessageDialog(isto, "Erro ao excluir este Romaneio\nO arquivo fisico não pode ser apagado");
					}
				}
				else {
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
				}
			}
			}
		});

		btnVizualizarNF = new JButton("Vizualizar");
		btnVizualizarNF.setBackground(new Color(0, 0, 51));
		btnVizualizarNF.setForeground(Color.WHITE);
		btnVizualizarNF.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnVizualizarNF, "cell 3 0,alignx left,aligny top");
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

		btnImportar = new JButton("Importar");
		btnImportar.setBackground(new Color(0, 102, 102));
		btnImportar.setForeground(Color.WHITE);
		btnImportar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnImportar, "cell 4 0,growx,aligny top");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_1.add(btnEditarRomaneio, "cell 5 0,alignx left,aligny top");

		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBackground(new Color(0, 51, 0));
		btnSelecionar.setForeground(Color.WHITE);
		btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnSelecionar, "cell 6 0,growx,aligny top");

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

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_2.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][]", "[][][][][][][][][][][][][]"));

		scrollPane = new JScrollPane(panel_2);
		scrollPane.getViewport().setBackground(Color.white);
		painelPrincipal.add(scrollPane, "cell 0 4 5 2,grow");

		lblNewLabel_3_1_2 = new JLabel("Romaneios:");
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblNewLabel_3_1_2, "cell 0 0,alignx right");

		lblNumeroTotalRomaneios = new JLabel("0.0000");
		lblNumeroTotalRomaneios.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNumeroTotalRomaneios, "cell 1 0");

		lblNewLabel_9 = new JLabel("Descontos");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblNewLabel_9, "cell 3 0 2 1,alignx center");

		lblNewLabel_10 = new JLabel("Médias");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblNewLabel_10, "cell 6 0 2 1,alignx center");

		lblNewLabel_15 = new JLabel("Recepção:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblNewLabel_15, "cell 9 0 3 1,alignx center");

		lblNewLabel_16 = new JLabel("Monsanto");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblNewLabel_16, "cell 13 0 2 1,alignx center");

		JLabel lblNewLabel_3 = new JLabel("P.B.T:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3, "cell 0 1,alignx right");

		lblPesoBrutoTotal = new JLabel("0.0000");
		lblPesoBrutoTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoBrutoTotal, "cell 1 1");

		lblNewLabel_5 = new JLabel("Desc Umi:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_5, "cell 3 1");

		lblDescontoTotalUmidade = new JLabel("0.000");
		lblDescontoTotalUmidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblDescontoTotalUmidade, "cell 4 1");

		lblNewLabel_11 = new JLabel("Umidade Média:");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_11, "cell 6 1");

		lblUmidadeMedia = new JLabel("00.00 %");
		lblUmidadeMedia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblUmidadeMedia, "cell 7 1");

		lblNewLabel_14 = new JLabel("Total Recepção:");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_14, "cell 10 1");

		lblTotalRecepcao = new JLabel("0.0");
		lblTotalRecepcao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblTotalRecepcao, "cell 11 1");

		lblNewLabel_2 = new JLabel("Total Royalties Participante:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_2, "cell 13 1,alignx right");

		lblPesoTotalParticipante = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalParticipante.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalParticipante, "cell 14 1");

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

		lblNewLabel_12 = new JLabel("Impureza Média:");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_12, "cell 6 2");

		lblImpurezaMedia = new JLabel("0.00");
		lblImpurezaMedia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblImpurezaMedia, "cell 7 2");

		lblNewLabel_4 = new JLabel("Total Royalties Particular:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_4, "cell 13 2,alignx right");

		lblPesoTotalParticular = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalParticular.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalParticular, "cell 14 2");

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

		lblNewLabel_13 = new JLabel("Avariados Média:");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_13, "cell 6 3");

		lblAvariadosMedia = new JLabel("0");
		lblAvariadosMedia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblAvariadosMedia, "cell 7 3");

		lblNewLabel_1 = new JLabel("Total Royalties:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_1, "cell 13 3,alignx right");

		lblPesoTotalRoyalt = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalRoyalt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalRoyalt, "cell 14 3");

		lblNewLabel_3_1_3 = new JLabel("Desc Total:");
		lblNewLabel_3_1_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1_3, "cell 3 4,alignx right");

		lblPesoTotalDesconto = new JLabel("0.0000");
		lblPesoTotalDesconto.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblPesoTotalDesconto, "cell 4 4");

		JLabel lblNewLabel_3_1_1 = new JLabel("P. Liquido Final:");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_3_1_1, "cell 0 5,alignx right");

		lblPesoLiquidoTotal = new JLabel("0.0000");
		lblPesoLiquidoTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_2.add(lblPesoLiquidoTotal, "cell 1 5");

		lblNewLabel_17 = new JLabel("Total ITS Baixado:");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_17, "cell 13 5,alignx right");

		lblPesoTotalDeclarado = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalDeclarado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalDeclarado, "cell 14 5");

		lblNewLabel_18 = new JLabel("Total ITS a Baixar:");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_18, "cell 13 6,alignx right");

		lblPesoTotalADeclarar = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalADeclarar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalADeclarar, "cell 14 6");

		lblNewLabel_20 = new JLabel("Total Outros Participantes:");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_20, "cell 13 8,alignx right");

		lblPesoTotalOutrosParticipante = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalOutrosParticipante.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalOutrosParticipante, "cell 14 8");

		lblNewLabel_19 = new JLabel("Total Outros Particular:");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_19, "cell 13 9,alignx right");

		lblPesoTotalOutrosParticular = new JLabel("000.000.000.000/000.000.000");
		lblPesoTotalOutrosParticular.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblPesoTotalOutrosParticular, "cell 14 9");
		scrollPane.getViewport().setBackground(Color.white);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelPrincipal.add(panel_3, "cell 0 0 5 2,alignx center,aligny top");
		panel_3.setLayout(new MigLayout("", "[][][grow][][][][grow][66px][grow][48px][][61px][4px][63px]",
				"[20px][20px][23px][23px,grow][grow]"));

		lblRemetente = new JLabel("Depositante:");
		panel_3.add(lblRemetente, "cell 0 0,alignx right,aligny top");
		lblRemetente.setFont(new Font("Tahoma", Font.BOLD, 12));

		entRemetente = new JTextField();
		entRemetente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entRemetente, "cell 1 0,growx,aligny top");
		entRemetente.setColumns(10);

		JLabel lblCpfcnpj_1 = new JLabel("CPF/CNPJ:");
		panel_3.add(lblCpfcnpj_1, "cell 2 0,alignx right,aligny top");
		lblCpfcnpj_1.setFont(new Font("Tahoma", Font.BOLD, 12));

		entIdentificacaoRemetente = new JTextField();
		entIdentificacaoRemetente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entIdentificacaoRemetente, "cell 3 0 2 1,grow");
		entIdentificacaoRemetente.setColumns(10);

		lblNewLabel = new JLabel("Destinatario:");
		panel_3.add(lblNewLabel, "cell 5 0,alignx right,aligny top");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));

		entChavePesquisa = new JTextField();
		entChavePesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entChavePesquisa, "cell 6 0,growx,aligny top");
		entChavePesquisa.setColumns(10);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		panel_3.add(lblCpfcnpj, "cell 7 0,alignx left,aligny top");
		lblCpfcnpj.setFont(new Font("Tahoma", Font.BOLD, 12));

		entIdentificacaoDestinatario = new JTextField();
		entIdentificacaoDestinatario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entIdentificacaoDestinatario, "cell 8 0,growx,aligny top");
		entIdentificacaoDestinatario.setColumns(10);

		lblDocEntrada = new JLabel("Doc Entrada:");
		panel_3.add(lblDocEntrada, "cell 9 0");
		lblDocEntrada.setFont(new Font("Tahoma", Font.BOLD, 12));

		entDocEntrada = new JTextField();
		entDocEntrada.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entDocEntrada, "cell 10 0");
		entDocEntrada.setColumns(10);

		JLabel lblCdigo = new JLabel("Código:");
		panel_3.add(lblCdigo, "cell 11 0,alignx right,aligny center");
		lblCdigo.setFont(new Font("Tahoma", Font.BOLD, 12));

		entCodigo = new JTextField();
		entCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entCodigo, "cell 13 0,growx,aligny center");
		entCodigo.setColumns(10);

		lblProduto = new JLabel("Produto:");
		panel_3.add(lblProduto, "cell 0 1,alignx right,aligny center");
		lblProduto.setFont(new Font("Tahoma", Font.BOLD, 12));

		entProduto = new JTextField();
		entProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				filtrar();

			}
		});
		panel_3.add(entProduto, "cell 1 1,growx,aligny center");
		entProduto.setColumns(10);

		lblNatureza = new JLabel("Operação:");
		panel_3.add(lblNatureza, "cell 2 1,alignx right,aligny center");
		lblNatureza.setFont(new Font("Tahoma", Font.BOLD, 12));

		entNatureza = new JTextField();
		entNatureza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entNatureza, "cell 3 1 2 1,growx,aligny center");
		entNatureza.setColumns(10);

		lblAmostra = new JLabel("Amostra:");
		panel_3.add(lblAmostra, "cell 5 1,alignx right");
		lblAmostra.setFont(new Font("Tahoma", Font.BOLD, 12));

		entAmostra = new JTextField();
		entAmostra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entAmostra, "cell 6 1,growx");
		entAmostra.setColumns(10);

		lblTransgenia = new JLabel("Transgenia:");
		panel_3.add(lblTransgenia, "cell 7 1");
		lblTransgenia.setFont(new Font("Tahoma", Font.BOLD, 12));

		entTransgeniaDefinida = new JTextField();
		entTransgeniaDefinida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entTransgeniaDefinida, "cell 8 1");
		entTransgeniaDefinida.setColumns(10);

		lblClassificador = new JLabel("Classificador:");
		panel_3.add(lblClassificador, "cell 9 1");
		lblClassificador.setFont(new Font("Tahoma", Font.BOLD, 12));

		entClassificador = new JTextField();
		entClassificador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entClassificador, "cell 10 1");
		entClassificador.setColumns(10);

		lblMonsanto = new JLabel("Monsanto:");
		panel_3.add(lblMonsanto, "cell 11 1");
		lblMonsanto.setFont(new Font("Tahoma", Font.BOLD, 12));

		entStatusMonsanto = new JTextField();
		entStatusMonsanto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entStatusMonsanto, "cell 13 1");
		entStatusMonsanto.setColumns(10);

		lblNomeMotorista = new JLabel("Nome Motorista:");
		panel_3.add(lblNomeMotorista, "cell 0 2");
		lblNomeMotorista.setFont(new Font("Tahoma", Font.BOLD, 12));

		entNomeMotorista = new JTextField();
		entNomeMotorista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entNomeMotorista, "cell 1 2");
		entNomeMotorista.setColumns(10);

		lblCpf = new JLabel("CPF:");
		panel_3.add(lblCpf, "cell 2 2,alignx right");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 12));

		entCpfMotorista = new JTextField();
		entCpfMotorista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entCpfMotorista, "cell 3 2 2 1,growx");
		entCpfMotorista.setColumns(10);

		lblPlaca = new JLabel("Placa:");
		panel_3.add(lblPlaca, "cell 5 2,alignx right");
		lblPlaca.setFont(new Font("Tahoma", Font.BOLD, 12));

		entPlaca = new JTextField();
		entPlaca.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entPlaca, "cell 6 2,growx");
		entPlaca.setColumns(10);

		lblSilo = new JLabel("Silo:");
		panel_3.add(lblSilo, "cell 7 2,alignx right");
		lblSilo.setFont(new Font("Tahoma", Font.BOLD, 12));

		entSilo = new JTextField();
		entSilo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				filtrar();
			}
		});
		panel_3.add(entSilo, "cell 8 2");
		entSilo.setColumns(10);

		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBackground(Color.WHITE);
		panel_3.add(panel_4, "cell 9 2 5 1,grow");
		panel_4.setLayout(new MigLayout("", "[][][][][]", "[]"));

		lblPerodo = new JLabel("Período:");
		lblPerodo.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_4.add(lblPerodo, "cell 0 0");

		lblD = new JLabel("De:");
		panel_4.add(lblD, "cell 1 0");
		lblD.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entMenorData = new JTextField();
		
		panel_4.add(entMenorData, "cell 2 0");
		entMenorData.setColumns(10);

		lblAt = new JLabel("Até:");
		panel_4.add(lblAt, "cell 3 0");
		lblAt.setFont(new Font("Tahoma", Font.PLAIN, 14));

		entMaiorData = new JTextField();
		
		panel_4.add(entMaiorData, "cell 4 0");
		entMaiorData.setColumns(10);

		JButton btnFiltrar_1 = new JButton("Filtrar");
		btnFiltrar_1.setBackground(new Color(51, 0, 0));
		btnFiltrar_1.setForeground(Color.WHITE);
		btnFiltrar_1.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(btnFiltrar_1, "cell 9 3,alignx center,aligny center");
		btnFiltrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBackground(new Color(204, 0, 0));
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(btnLimpar, "cell 10 3,alignx center,aligny center");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limpar();
			}
		});

		btnReleitura = new JButton("Refazer Pesquisar");
		btnReleitura.setBackground(new Color(0, 51, 0));
		btnReleitura.setForeground(Color.WHITE);
		btnReleitura.setFont(new Font("Arial", Font.BOLD, 16));
		panel_3.add(btnReleitura, "cell 11 3 3 1,growx,aligny top");
		btnReleitura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarTodosOsRomaneios();

			}
		});

		painelTarefas.setBackground(new Color(0, 102, 102));
		painelAbas.addTab("Tarefas", painelTarefas);
		painelTarefas.setLayout(new MigLayout("", "[][][grow]", "[][grow][]"));

		lblNewLabel_8 = new JLabel("Lista de Tarefas");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("SansSerif", Font.BOLD, 24));
		painelTarefas.add(lblNewLabel_8, "cell 0 0 3 1");

		table_tarefas = new JTable(modelo_tarefa);
		table_tarefas.setBackground(Color.WHITE);
		table_tarefas.setRowHeight(30);

		table_tarefas.setBackground(Color.white);

		scrollPaneTarefas = new JScrollPane(table_tarefas);
		scrollPaneTarefas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setOpaque(true);
		scrollPaneTarefas.getViewport().setBackground(Color.white);
		scrollPaneTarefas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setBackground(Color.WHITE);
		scrollPaneTarefas.setAutoscrolls(true);
		painelTarefas.add(scrollPaneTarefas, "cell 0 1 3 1,grow");

		btnExcluirTarefa = new JButton("Excluir");
		btnExcluirTarefa.setBackground(new Color(204, 51, 51));
		btnExcluirTarefa.setForeground(Color.WHITE);
		btnExcluirTarefa.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelTarefas.add(btnExcluirTarefa, "flowx,cell 2 2,alignx right");

		btnResponder = new JButton("Responder");
		btnResponder.setBackground(new Color(0, 51, 0));
		btnResponder.setForeground(Color.WHITE);
		btnResponder.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelTarefas.add(btnResponder, "cell 2 2,alignx right");

		btnAdcionarTarefa = new JButton("Adicionar");
		btnAdcionarTarefa.setBackground(new Color(0, 0, 204));
		btnAdcionarTarefa.setForeground(Color.WHITE);
		btnAdcionarTarefa.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelTarefas.add(btnAdcionarTarefa, "cell 2 2,alignx right");

		if (flag_tipo_tela == 1) {
			btnSelecionar.setVisible(false);
			btnSelecionar.setEnabled(false);
		}

		pesquisar_tarefas();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

	}

	public void exportar() {

		ManipularTxt manipular = new ManipularTxt();

		if (table_nfs.getSelectedRows().length > 0) {
			if (table_nfs.getSelectedRows().length == 1) {

				try {
					JOptionPane.showMessageDialog(isto, "Na próxima tela, selecione a pasta para salvar o romaneio");
					File pasta_salvar = getDiretorioSalvar();
					int rowSel = table_nfs.getSelectedRows()[0];
					int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro indice
																								// do model
					CadastroRomaneio rom = lista_romaneios.get(indexRowModel);

					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + rom.getCaminho_arquivo(),
								pasta_salvar.getAbsolutePath() + "\\" + rom.getNumero_romaneio() + ".pdf");

						if (copiar) {
							JOptionPane.showMessageDialog(isto, "Romaneio Exportado");

							if (Desktop.isDesktopSupported()) {
								try {
									Desktop desktop = Desktop.getDesktop();
									File myFile = new File(pasta_salvar.getAbsolutePath());
									desktop.open(myFile);
								} catch (IOException ex) {
								}
							}

						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao exportar o romaneio");
						}

					} catch (IOException e) {
						JOptionPane.showMessageDialog(isto, "Erro Fatal ao exportar o romaneio");
						e.printStackTrace();
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(isto, "Erro ao exportar romaneio");

				}

			} else {

				try {
					JOptionPane.showMessageDialog(isto,
							"Na próxima tela, selecione o local e escreva o nome da pasta para salvar os arquivos");

					File pasta_salvar = getDiretorioSalvar();
					boolean todos_romaneios_salvos = false;

					int indices[] = table_nfs.getSelectedRows();

					for (int i = 0; i < indices.length; i++) {
						int rowSel = indices[i];
						int indexRowModel = table_nfs.getRowSorter().convertRowIndexToModel(rowSel);// converte pro
																									// indice do model
						CadastroRomaneio rom = lista_romaneios.get(indexRowModel);

						try {
							boolean copiar = manipular.copiarNFe(servidor_unidade + rom.getCaminho_arquivo(),
									pasta_salvar.getAbsolutePath() + "\\romaneio_" + rom.getNumero_romaneio() + ".pdf");
							if (copiar) {

							} else {
								JOptionPane.showMessageDialog(null,
										"Erro ao exportar o romaneio: " + rom.getNumero_romaneio());
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(isto, "Erro fatal ao exportar romaneios");

							e.printStackTrace();
						}

					}
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop desktop = Desktop.getDesktop();
							File myFile = new File(pasta_salvar.getAbsolutePath());
							desktop.open(myFile);
						} catch (IOException ex) {
						}
					}

				} catch (Exception t) {
					JOptionPane.showMessageDialog(isto, "Erro fataol ao exportar romaneios selecionados");

				}

			}
		} else {
			JOptionPane.showMessageDialog(isto, "Nenhuma linha selecionada");
		}

	}

	public File getDiretorioSalvar() {
		// Mostra a dialog de save file
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setPreferredSize(new Dimension(800, 600));

		fileChooser.showSaveDialog(isto);

		File pasta_selecionada = fileChooser.getSelectedFile();

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
		private final int classificador = 32;
		private final int umidade2 = 33;
		private final int impureza2 = 34;
		private final int status_monsanto = 35;
		private final int royalties = 36;

		private final String colunas[] = { "Número", "Operação", "Data:", "Produto:", "Transgenia:", "Safra:",
				"Depositante:", "CPF/CNPJ Depositante", "Rementente/Destinatario", "CPF/CNPJ Rementente/Destinatario",
				"Peso Bruto:", "Tara:", "Peso Sem Desconto", "Desconto Umidade", "Desconto Impureza",
				"Desconto Avariados", "Desconto Total", "Peso Final:", "Recepção", "Umidade:", "Impureza:", "Ardidos",
				"Avariados", "CFOP", "Descrição", "Motorista", "CPF MOTORISTA", "Placa", "Doc Entrada", "Amostra",
				"Silo", "Transgenese", "Classificador", "Umidade 2", "Impureza 2", "STATUS MONSANTO", "ROYALTIES" };
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
				return Integer.class;
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
			case classificador:
				return String.class;
			case umidade2:
				return Double.class;
			case impureza2:
				return Double.class;
			case status_monsanto:
				return String.class;
			case royalties:
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
					return prod.getNome_produto().toUpperCase();
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
							if(remetente.getNome_empresarial() == null || remetente.getNome_empresarial().equals("") || remetente.getNome_empresarial().equals(" ")  || remetente.getNome_empresarial().length() <= 3) {
								nome_cliente = remetente.getNome().toUpperCase() + " " + remetente.getSobrenome().toUpperCase();

							}else {
							
							nome_cliente = remetente.getNome_empresarial().toUpperCase();
							if(nome_cliente == null || nome_cliente.equals("") || nome_cliente.length() <= 3) {
								nome_cliente = remetente.getNome().toUpperCase() + " " + remetente.getSobrenome().toUpperCase();
							}
							}
						} else {
							nome_cliente = remetente.getNome_fantaia().toUpperCase();
							if(nome_cliente == null || nome_cliente.equals("") || nome_cliente.length() <= 3) {
								nome_cliente = remetente.getNome().toUpperCase() + " " + remetente.getSobrenome().toUpperCase();
							}
						}
					}else {
						nome_cliente = "";
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
							
							if(destinatario.getNome_empresarial() == null || destinatario.getNome_empresarial().equals("") || destinatario.getNome_empresarial().equals(" ")  || destinatario.getNome_empresarial().length() <= 3) {
								nome_cliente = destinatario.getNome().toUpperCase() + " " + destinatario.getSobrenome().toUpperCase();

							}else {
							
							nome_cliente = destinatario.getNome_empresarial().toUpperCase();
							if(nome_cliente == null || nome_cliente.equals("") || nome_cliente.length() <= 3) {
								nome_cliente = destinatario.getNome().toUpperCase() + " " + destinatario.getSobrenome().toUpperCase();
							}
							}
						} else
							nome_cliente = destinatario.getNome_fantaia().toUpperCase();
						if(nome_cliente == null || nome_cliente.equals("") || nome_cliente.length() <= 3) {
							nome_cliente = destinatario.getNome().toUpperCase() + " " + destinatario.getSobrenome().toUpperCase();
						}
					}else {
						nome_cliente = "";
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
			case classificador:
				return romaneio.getClassificador();
			case umidade2:
				return romaneio.getUmidade2();
			case impureza2:
				return romaneio.getImpureza2();
			case status_monsanto: {
				int sts_monsanto = romaneio.getStatus_monsanto();
				if (sts_monsanto == 0)
					return "FALTA ITS";
				else if (sts_monsanto == 1)
					return "OK ITS";
				else if (sts_monsanto == 2)
					return "PARTICIPANTE";
				else if (sts_monsanto == 3)
					return "NÃO APLICÁVEL";
			}
			case royalties: {
				if (romaneio.getRoyalties() == 0) {
					return "NÃO";
				} else {
					return "SIM";
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
		String status_monsanto = entStatusMonsanto.getText().toUpperCase();
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

		/*
		 * if (checkString(remetente))
		 * 
		 * filters.add(RowFilter.regexFilter(remetente, 6));
		 */

		if (checkString(remetente))
			filters.add(RowFilter.regexFilter(remetente, 6));

		if (checkString(natureza))
			filters.add(RowFilter.regexFilter(natureza, 1));
		/*
		 * if (checkString(destinatario))
		 * filters.add(RowFilter.regexFilter(destinatario, 8));
		 */

		if (checkString(destinatario)) {
			filters.add(RowFilter.regexFilter(destinatario, 8));

		}
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

		if (checkString(status_monsanto))
			filters.add(RowFilter.regexFilter(status_monsanto, 35));

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
		ArrayList<CadastroRomaneio> filtrados = new ArrayList<>();
		int numero_romaneios = 0;
		int numero_romaneios_maior_0 = 0;

		double peso_bruto_total = 0, peso_tara_total = 0, peso_liquido_total_sem_desconto = 0, peso_liquido_total = 0;
		double peso_desconto_umidade = 0;
		double peso_desconto_impureza = 0;
		double peso_desconto_avariado = 0;
		double peso_desconto_total = 0;
		double peso_recepcao = 0;

		double peso_total_royalties = 0;
		double peso_total_participante = 0;
		double peso_total_paticular = 0;
		double peso_total_outros_particular = 0;
		double peso_total_outros_participante = 0;

		double peso_total_its_declarado = 0;
		double peso_total__its_a_declarar = 0;

		double avariados_media = 0;
		double impureza_media = 0;
		double umidade_media = 0;

		for (int row = 0; row < table_nfs.getRowCount(); row++) {

			int index = table_nfs.convertRowIndexToModel(row);
			CadastroRomaneio romaneio = modelo_romaneios.getValue(index);
			filtrados.add(romaneio);
			peso_bruto_total += romaneio.getPeso_bruto();
			peso_tara_total += romaneio.getTara();
			peso_liquido_total += romaneio.getPeso_liquido();
			peso_liquido_total_sem_desconto += romaneio.getPeso_liquido_sem_descontos();

			peso_desconto_umidade += romaneio.getPeso_desconto_umidade();
			peso_desconto_impureza += romaneio.getPeso_desconto_impureza();
			peso_desconto_avariado += romaneio.getPeso_desconto_avariados();
			peso_desconto_total += romaneio.getPeso_desconto_total();
			peso_recepcao += romaneio.getDespesa_recepcao();

			// classificacao
			if (romaneio.getUmidade() > 0 && romaneio.getInpureza() > 0) {
				umidade_media += romaneio.getUmidade();

				impureza_media += romaneio.getInpureza();

				avariados_media += romaneio.getAvariados();
				numero_romaneios_maior_0++;

			}

			// monsanto
			if (romaneio.getRoyalties() == 1) {
				// e monsanto
				peso_total_royalties += romaneio.getPeso_liquido();

				// particular
				if (romaneio.getStatus_monsanto() == 0 || romaneio.getStatus_monsanto() == 1) {
					peso_total_paticular += romaneio.getPeso_liquido();

					if (romaneio.getStatus_monsanto() == 0) {
						// falta its
						peso_total__its_a_declarar += romaneio.getPeso_liquido();

					} else if (romaneio.getStatus_monsanto() == 1) {
						// ok its
						peso_total_its_declarado += romaneio.getPeso_liquido();

					}

				}
				if (romaneio.getStatus_monsanto() == 2) {
					// participante cj
					peso_total_participante += romaneio.getPeso_liquido();
				} else if (romaneio.getStatus_monsanto() == 3) {
					// não aplicavel
				}

			} else {
				if (romaneio.getStatus_monsanto() == 2) {
					// participante cj
					peso_total_outros_participante += romaneio.getPeso_liquido();
				} else {
					peso_total_outros_particular += romaneio.getPeso_liquido();
				}
			}

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
		lblTotalRecepcao.setText(z.format(peso_recepcao) + " Kgs | " + z.format(peso_recepcao / 60) + " sacos");
		// medias

		umidade_media = umidade_media / numero_romaneios_maior_0;
		avariados_media = avariados_media / numero_romaneios_maior_0;
		impureza_media = impureza_media / numero_romaneios_maior_0;

		DecimalFormat deci = new DecimalFormat("0.00");

		lblUmidadeMedia.setText(deci.format(umidade_media) + " %");
		lblAvariadosMedia.setText(deci.format(avariados_media) + " %");
		lblImpurezaMedia.setText(deci.format(impureza_media) + " %");

		// monsanto
		lblPesoTotalRoyalt
				.setText(z.format(peso_total_royalties) + " Kgs | " + z.format(peso_total_royalties / 60) + " sacos");
		lblPesoTotalParticipante.setText(
				z.format(peso_total_participante) + " Kgs | " + z.format(peso_total_participante / 60) + " sacos");
		lblPesoTotalParticular
				.setText(z.format(peso_total_paticular) + " Kgs | " + z.format(peso_total_paticular / 60) + " sacos");
		lblPesoTotalDeclarado.setText(
				z.format(peso_total_its_declarado) + " Kgs | " + z.format(peso_total_its_declarado / 60) + " sacos");
		lblPesoTotalADeclarar.setText(z.format(peso_total__its_a_declarar) + " Kgs | "
				+ z.format(peso_total__its_a_declarar / 60) + " sacos");

		lblPesoTotalOutrosParticular.setText(z.format(peso_total_outros_particular) + " Kgs | "
				+ z.format(peso_total_outros_particular / 60) + " sacos");
		lblPesoTotalOutrosParticipante.setText(z.format(peso_total_outros_participante) + " Kgs | "
				+ z.format(peso_total_outros_participante / 60) + " sacos");

		calcularExtrato();

		filtrados.sort(new Comparator<CadastroRomaneio>() {

			@Override
			public int compare(CadastroRomaneio o1, CadastroRomaneio o2) {
				// Add null check
				return o1.getData().compareTo(o2.getData());
			}
		});

		Map<Date, Double> somaPorData = filtrados.stream().collect(Collectors.groupingBy(CadastroRomaneio::getData,
				Collectors.summingDouble(CadastroRomaneio::getPeso_liquido)));
		atualizarGrafico(somaPorData);

		somaPorData = filtrados.stream().collect(Collectors.groupingBy(CadastroRomaneio::getData,
				Collectors.summingDouble(CadastroRomaneio::getUmidade)));
		atualizarGraficoClassificacao(somaPorData);

	}

	public void calcularExtrato() {

		NumberFormat z = NumberFormat.getNumberInstance();
		ArrayList<CadastroRomaneio> filtrados = new ArrayList<>();
		int numero_romaneios = 0;
		int numero_romaneios_maior_0 = 0;

		double peso_bruto_total = 0, peso_tara_total = 0, peso_liquido_total_sem_desconto = 0, peso_liquido_total = 0;
		double peso_desconto_umidade = 0;
		double peso_desconto_impureza = 0;
		double peso_desconto_avariado = 0;
		double peso_desconto_total = 0;
		double peso_recepcao = 0;

		double peso_transferencia_entrada = 0;

		double peso_transferencia_saida = 0;
		double peso_quebra_tecnica = 0;

		double peso_saida_normal = 0;

		double peso_intacta = 0;

		double peso_total_royalties = 0;
		double peso_total_participante = 0;
		double peso_total_paticular = 0;
		double peso_total_outros_particular = 0;
		double peso_total_outros_participante = 0;

		double peso_total_its_declarado = 0;
		double peso_total__its_a_declarar = 0;

		for (int row = 0; row < table_nfs.getRowCount(); row++) {

			int index = table_nfs.convertRowIndexToModel(row);
			CadastroRomaneio romaneio = modelo_romaneios.getValue(index);
			filtrados.add(romaneio);

			if (romaneio.getOperacao().equalsIgnoreCase("ENTRADA NORMAL")) {

				peso_bruto_total += romaneio.getPeso_bruto();
				peso_tara_total += romaneio.getTara();
				peso_liquido_total += romaneio.getPeso_liquido();
				peso_liquido_total_sem_desconto += romaneio.getPeso_liquido_sem_descontos();

				peso_desconto_umidade += romaneio.getPeso_desconto_umidade();
				peso_desconto_impureza += romaneio.getPeso_desconto_impureza();
				peso_desconto_avariado += romaneio.getPeso_desconto_avariados();
				peso_desconto_total += romaneio.getPeso_desconto_total();
				peso_recepcao += romaneio.getDespesa_recepcao();

				// monsanto
				if (romaneio.getRoyalties() == 1) {
					// e monsanto
					peso_total_royalties += romaneio.getPeso_liquido();

					// particular
					if (romaneio.getStatus_monsanto() == 0 || romaneio.getStatus_monsanto() == 1) {
						peso_total_paticular += romaneio.getPeso_liquido();

						if (romaneio.getStatus_monsanto() == 0) {
							// falta its
							peso_total__its_a_declarar += romaneio.getPeso_liquido();
							peso_intacta += (romaneio.getPeso_liquido() * 0.075);

						} else if (romaneio.getStatus_monsanto() == 1) {
							// ok its
							peso_total_its_declarado += romaneio.getPeso_liquido();

						}

					}
					if (romaneio.getStatus_monsanto() == 2) {
						// participante cj
						peso_total_participante += romaneio.getPeso_liquido();
					} else if (romaneio.getStatus_monsanto() == 3) {
						// não aplicavel
					}

				} else {
					if (romaneio.getStatus_monsanto() == 2) {
						// participante cj
						peso_total_outros_participante += romaneio.getPeso_liquido();
					} else {
						peso_total_outros_particular += romaneio.getPeso_liquido();
					}
				}

				numero_romaneios++;

			} else if (romaneio.getOperacao().equalsIgnoreCase("ENTRADA TRANSFERENCIA")) {
				peso_transferencia_entrada += romaneio.getPeso_liquido();
			} else if (romaneio.getOperacao().equalsIgnoreCase("SAÍDA TRANSFERENCIA")) {
				peso_transferencia_saida += romaneio.getPeso_liquido();
			} else if (romaneio.getOperacao().equalsIgnoreCase("SAÍDA NORMAL")) {
				peso_saida_normal += romaneio.getPeso_liquido();

			} else if (romaneio.getOperacao().equalsIgnoreCase("SAÍDA QUEBRA TECNICA")) {
				peso_quebra_tecnica += romaneio.getPeso_liquido();

			}

		}

		lblPesoBrutoTotalExtrato
				.setText(z.format(peso_bruto_total) + " Kgs | " + z.format(peso_bruto_total / 60) + " sacos");
		lblPesoTaraTotalTara.setText(z.format(peso_tara_total) + " Kgs | " + z.format(peso_tara_total / 60) + " sacos");
		lblPesoLiquidoTotalSemDescontoExtrato.setText(z.format(peso_liquido_total_sem_desconto) + " Kgs | "
				+ z.format(peso_liquido_total_sem_desconto / 60) + " sacos");

		// descontos
		lblDescontoTotalImpurezaExtrato.setText(
				z.format(peso_desconto_impureza) + " Kgs | " + z.format(peso_desconto_impureza / 60) + " sacos");
		lblDescontoTotalUmidadeExtrato
				.setText(z.format(peso_desconto_umidade) + " Kgs | " + z.format(peso_desconto_umidade / 60) + " sacos");
		lblDescontoTotalAvariadoExtrato.setText(
				z.format(peso_desconto_avariado) + " Kgs | " + z.format(peso_desconto_avariado / 60) + " sacos");

		lblPesoTotalDescontoExtrato
				.setText(z.format(peso_desconto_total) + " Kgs | " + z.format(peso_desconto_total / 60) + " sacos");

		lblPesoFisicoFinal
				.setText(z.format(peso_liquido_total) + " Kgs | " + z.format(peso_liquido_total / 60) + " sacos");

		// transferencia de entrada
		lblTranferenciaEntrada.setText(z.format(peso_transferencia_entrada) + " Kgs | "
				+ z.format(peso_transferencia_entrada / 60) + " sacos");

		double total_entrada_geral = peso_liquido_total + peso_transferencia_entrada;
		// total entrada geral
		lblTotalEntradaGeral
				.setText(z.format(total_entrada_geral) + " Kgs | " + z.format(total_entrada_geral / 60) + " sacos");

		// transferencia de saida
		lblQuebraTecnica
				.setText(z.format(peso_quebra_tecnica) + " Kgs | " + z.format(peso_quebra_tecnica / 60) + " sacos");

		// quebra tecnica
		lblTransferenciaSaida.setText(
				z.format(peso_transferencia_saida) + " Kgs | " + z.format(peso_transferencia_saida / 60) + " sacos");

		// saida normal
		lblSaida.setText(z.format(peso_saida_normal) + " Kgs | " + z.format(peso_saida_normal / 60) + " sacos");

		double saida_total = peso_saida_normal + peso_transferencia_saida + peso_quebra_tecnica;
		// total saida
		lblTotalSaida.setText(z.format(saida_total) + " Kgs | " + z.format(saida_total / 60) + " sacos");

		// intacta
		lblIntacta.setText(z.format(peso_intacta) + " Kgs | " + z.format(peso_intacta / 60) + " sacos");

		
		lblRecepcao.setText(z.format(peso_recepcao) + " Kgs | " + z.format(peso_recepcao / 60) + " sacos");
		
		double saida_outros = peso_intacta + peso_recepcao;

		double saldo_final = total_entrada_geral - saida_total - saida_outros;
		// saldo final
		lblSaldoFinal.setText(z.format(saldo_final) + " Kgs | " + z.format(saldo_final / 60) + " sacos");

	}

	public class TarefaTableModel extends AbstractTableModel {
		/*
		 * modelo_tarefas.addColumn("Id Tarefas"); modelo_tarefas.addColumn("Status");
		 * 
		 * modelo_tarefas.addColumn("Nome"); modelo_tarefas.addColumn("Descrição");
		 * modelo_tarefas.addColumn("Mensagem"); modelo_tarefas.addColumn("Resposta");
		 * 
		 * modelo_tarefas.addColumn("Data");
		 * 
		 * modelo_tarefas.addColumn("Hora"); modelo_tarefas.addColumn("Criador");
		 * 
		 * modelo_tarefas.addColumn("Executor");
		 * 
		 * modelo_tarefas.addColumn("Hora Agendada");
		 * modelo_tarefas.addColumn("Data Agendada");
		 * modelo_tarefas.addColumn("Prioridade");
		 */
		// constantes p/identificar colunas
		private final int id_tarefa = 0;
		private final int status = 1;
		private final int nome = 2;
		private final int descricao = 3;
		private final int mensagem = 4;
		private final int resposta = 5;
		private final int data = 6;
		private final int hora = 7;
		private final int criador = 8;
		private final int executor = 9;

		private final int hora_agendada = 10;

		private final int data_agendada = 11;
		private final int prioridade = 12;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Status", "Nome", "Descrição", "Mensagem", "Resposta", "Data", "Hora",
				"Criador", "Executor", "Hora Agendada", "Data Agendada", "Prioridade" };

		private final ArrayList<CadastroTarefaGeral> dados = new ArrayList<>();// usamos como dados uma lista genérica
																				// de
																				// nfs

		public TarefaTableModel() {

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
			case id_tarefa: {
				return Integer.class;

			}
			case status: {
				return String.class;
			}
			case nome: {
				return String.class;
			}
			case descricao: {
				return String.class;
			}
			case mensagem: {
				return String.class;
			}
			case resposta: {
				return String.class;
			}
			case data: {
				return String.class;
			}
			case hora: {
				return String.class;
			}
			case criador: {
				return String.class;
			}
			case executor: {
				return String.class;
			}

			case hora_agendada: {
				return String.class;
			}
			case data_agendada: {
				return String.class;
			}
			case prioridade: {
				return String.class;
			}
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
			CadastroTarefaGeral dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_tarefa: {
				dado.getId_tarefa();
			}
			case status: {
				if (dado.getStatus_tarefa() == 1) {
					return "Concluida";

				} else if (dado.getStatus_tarefa() == 2) {
					return "Em Andamento";

				}
			}
			case nome: {
				return dado.getNome_tarefa();
			}
			case descricao: {
				return dado.getDescricao_tarefa();
			}
			case mensagem: {
				return dado.getMensagem();
			}
			case resposta: {
				return dado.getResposta();
			}
			case data: {
				return dado.getData();
			}
			case hora: {
				return dado.getHora();
			}
			case criador: {
				return dado.getNome_criador();
			}
			case executor: {
				return dado.getNome_executor();
			}

			case hora_agendada: {
				return dado.getHora_agendada();
			}
			case data_agendada: {
				return dado.getData_agendada();
			}
			case prioridade: {

				if (dado.getPrioridade() == 1) {
					return "Imediata - Neste Momento";
				} else if (dado.getPrioridade() == 2) {
					return "Urgente - Nesta Hora";
				} else if (dado.getPrioridade() == 3) {
					return "Quanto Antes - Ainda Hoje";
				} else if (dado.getPrioridade() == 4) {
					return "Média - Ainda essa semana";
				} else if (dado.getPrioridade() == 5) {
					return "Leve - Ainda esse mês";
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

			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroTarefaGeral ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroTarefaGeral getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroTarefaGeral dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroTarefaGeral dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroTarefaGeral> dadosIn) {
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
		public void onRemove(CadastroTarefaGeral dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroTarefaGeral onGet(int row) {
			return dados.get(row);
		}
	}

	public void pesquisar_tarefas() {
		GerenciarBancoTarefaGeral gerenciar = new GerenciarBancoTarefaGeral();
		lista_tarefas.clear();
		modelo_tarefa.onRemoveAll();

		for (CadastroTarefaGeral tarefa : gerenciar.consultaTarefasRomaneios()) {

			modelo_tarefa.onAdd(tarefa);
			lista_tarefas.add(tarefa);

		}
	}

	public void atualizarGrafico(Map<Date, Double> lista_cargas) {

		painelGraficoLinha.removeAll();
		lista_cargas = new TreeMap<>(lista_cargas);

		dataset = new DefaultCategoryDataset();
		for (Map.Entry<Date, Double> pair : lista_cargas.entrySet()) {
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			String value = f.format(pair.getKey());

			dataset.addValue(pair.getValue() / 60, "", value);
		}

		linha = new GraficoLinha();
		linha.setDataset(dataset);
		chartPanel = linha.getGraficoLinha(painelGraficoLinha.getWidth(), painelGraficoLinha.getHeight(), "Data",
				"Romaneios", "Quantidade de Sacos");
		chartPanel.setBackground(Color.white);
		painelGraficoLinha.add(chartPanel);
	}

	public void atualizarGraficoClassificacao(Map<Date, Double> lista_cargas) {

		painelGraficoClassificacao.removeAll();
		lista_cargas = new TreeMap<>(lista_cargas);

		dataset = new DefaultCategoryDataset();
		for (Map.Entry<Date, Double> pair : lista_cargas.entrySet()) {
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			String value = f.format(pair.getKey());

			dataset.addValue(pair.getValue(), "", value);
		}

		linha = new GraficoLinha();
		linha.setDataset(dataset);
		chartPanel = linha.getGraficoLinha(painelGraficoClassificacao.getWidth(),
				painelGraficoClassificacao.getHeight(), "Data", "Classificação", "Umidade");
		chartPanel.setBackground(Color.white);
		painelGraficoClassificacao.add(chartPanel);
	}
	
	
	public void pesquisarTodosOsRomaneiosTransportador() {
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
					romaneios = gerenciar.listarRomaneiosPorTransportador(cliente_selecionado.getCpf());

				}
				for (CadastroRomaneio rom : romaneios) {
					modelo_romaneios.onAdd(rom);
					lista_romaneios.add(rom);
				}

				calcular();
			}
		}.start();
	}

}