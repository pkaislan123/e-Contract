package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.ImageIcon;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTransferenciaRecebimento;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDistrato;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.PagamentoCompleto;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.cadastros.Registros;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDistratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferenciaRecebimento;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.graficos.JPanelGraficoPadrao;
import main.java.graficos.JPanelGraficoRecebimento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.MonitorarRomaneios;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
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
import main.java.relatoria.RelatorioContratoIndividual;
import main.java.relatoria.RelatorioContratoIndividualExcel;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaEscolha;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.cadastros.CadastroInformativo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoInformativo;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.List;
import java.awt.Point;
import java.awt.PopupMenu;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.JTree;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

public class TelaGerenciarContrato extends JFrame {

	private JDialog tela_pai;
	private JLabel lblTotalComissao, lblCoberturaComissao;

	private ArrayList<CadastroRomaneio> romaneios_disponivel = new ArrayList<>();
	private JTable table_recebimentos;
	private JButton btnSelecionarNota, btnCriarDistrato;
	private JTable tabela_sub_contratos;
	private JLabel lblStatusAdicionandoNotas;
	private int contador = 0;
	private JLabel lblCoberturaConcluida, lblTotalPagamentosConcuidos;

	private JLabel lblPesoTotalContrato, lblPesoTotalRecebido, lblPesoTotalRestante;
	private JLabel lblNumRomaneiosRecebimento, lblTotalSacosKGsRomaneios, lblPesoTotalNFVendaEntrada,
			lblPesoRestanteNFVendaEntrada, lblValorTotalNFVendaEntrada, lblPesoTotalNFRemessaEntrada,
			lblPesoRestanteNFRemessaEntrada, lblValorTotalNFRemessaEntrada, lblStatusRecebimentoEntrada,
			lblStatusNFRemessaEntrada, lblStatusNFVendaEntrada;
	private JLabel lblStatusPagamento, lblStatusCobertura, lblStatusCarregamentoSaida, lblStatusNFVendaSaida,
			lblStatusNFRemessaSaida, lblNumRomaneiosCarregamento, lblTotalSacosKGsRomaneiosSaida,
			lblPesoTotalNFInternaSaida, lblPesoRestanteNFInternaSaida, lblPesoTotalNFVendaSaida,
			lblPesoRestanteNFVendaSaida;
	private PagamentoTableModel modelo_pagamentos = new PagamentoTableModel();
	private FileChooser d;
	private JPanelGraficoPadrao painelGraficoRecebimento;
	private JFileChooser fileChooser_global;
	private ArrayList<String> listadeArquivos = new ArrayList<>();
	private double total_sacos_recebidos = 0, total_kg_recebidos;
	private int total_romaneios_entrada = 0;
	private RecebimentoTableModel modelo_recebimentos = new RecebimentoTableModel();
	private CarregamentoTableModel modelo_carregamentos = new CarregamentoTableModel();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private JComboBox cBBrutoLivre, cBOptante, cbGrupoParticular;
	private ConfiguracoesGlobais configs_globais;
	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelRecebimentoEntrada = new JPanel();
	private JLabel lblDataContratoRecebimento, lblCorretorRecebimento, lblCompradoresRecebimento,
			lblVendedoresRecebimento, lblValorUnidadeRecebimento, lblQuantidadeRecebimento, lblValorTotalRecebimento,
			lblProdutoRecebimento, lblSafraRecebimento, lblValorTotalNFVendaSaida;
	private JPanel painelVizualizarContrato;
	private ArrayList<CadastroInformativo> informacoes_atuais = new ArrayList<>();
	private JPanel painelCarregamento = new JPanel();
	private JPanel painelListaTarefas = new JPanel();
	private JPanel painelComprovantes = new JPanel();
	private JTree arvore_documentos;
	private JTree arvore_contratos;
	private ArrayList<String> horarios_informativos_ja_executados = new ArrayList<>();
	private ArrayList<CadastroRomaneio> lista_romaneios_carregamento = new ArrayList<>();
	private ArrayList<CadastroNFe> lista_nf_interna_carregamento = new ArrayList<>();
	private ArrayList<CadastroNFe> lista_nf_venda1_carregamento = new ArrayList<>();
	private ArrayList<CadastroNFe> lista_nf_complemento_carregamento = new ArrayList<>();
	private JTextArea textAreaStatusOpcaoFolha, textAreaDescricao, textAreaObservacoes;
	private ArrayList<Integer> informativos_ja_executados = new ArrayList<>();
	private JCheckBox chckbxIncluirRecebimentos;
	private Registros.RegistroPagamento registro_pagamento_global;
	private String caminho_salvar_comprovante_pagamento;
	DefaultMutableTreeNode no_assinaturas;
	DefaultMutableTreeNode no_pagamentos;
	DefaultMutableTreeNode no_carregamentos;
	DefaultMutableTreeNode no_outros;
	DefaultMutableTreeNode no_contratos;
	DefaultMutableTreeNode no_sub_contratos;
	private DefaultMutableTreeNode no_contrato_selecionado;
	private JLabel lblTotalTransferenciasCargaRetiradas, lblTotalTransferenciasCargaRecebidas;
	private JButton btnAlterar;
	private DefaultMutableTreeNode no_selecionado;
	private final JLabel lblStatusContrato = new JLabel("Status do Contrato:");
	private final JLabel lblValorTotalPagamentos, lblTotalTransferenciasRecebidas, lblTotalTransferenciasRetiradas;
	InputStream stream = null;
	private final JButton btnEditarContrato = new JButton("Editar");

	private JPanel painel_vizualizar;
	private final JButton btnEnviarMsg = new JButton("Enviar"), btnCriarAditivo, btnRevogarAssinatura;
	private final JLabel lblNewLabel = new JLabel("     Modelos de Pagamento");
	private CadastroContrato contrato_local;
	private ArrayList<CadastroContrato> lista_sub_contratos = new ArrayList<>();
	private SwingController controller = null;
	private SwingViewBuilder factory;
	ArrayList<JButton> botoes = new ArrayList<>();
	private TelaGerenciarContrato isto;
	private static String servidor_unidade;
	private CadastroCliente cliente_carregamento;
	private CadastroContrato contrato_carregamento;
	private JPanel painelSubContratos = new JPanel();
	private Registros.RegistroCarregamento registro_carregamento_global;
	private Registros.RegistroRecebimento registro_recebimento_global;
	private double peso_total_recebido;
	private CadastroNFe nota_fiscal;
	private CadastroCliente transportador = new CadastroCliente();
	private CadastroProduto produto = new CadastroProduto();
	private CadastroContrato.Carregamento carregamento_confirmar = new CadastroContrato.Carregamento();

	private JButton btnReabrir,	 btnCancelarContrato;
	private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;
	private ArrayList<CadastroContrato.Carregamento> lista_carregamentos = null;
	private ArrayList<CadastroContrato.Recebimento> lista_recebimentos = null;
	private ArrayList<CadastroTransferenciaRecebimento> lista_replicacoes_recebimento = null;

	private ArrayList<PagamentoCompleto> lista_pagamentos_contratuais = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_remetente = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_destinatario = null;

	private ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_transferencias_carga_remetente = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_transferencias_carga_destinatario = null;

	private JPopupMenu jPopupMenuTabelCarregamento;
	private JPopupMenu jPopupMenuTabelPagamentos;
	private JPopupMenu jPopupMenuDocumentos;
	private JPopupMenu jPopupMenuTabelAditivos;
	private JPopupMenu jPopupMenuTabelDistratos;
	private JButton btnAprovar;

	private Double peso_total_cargas_nf_venda1 = 0.0, peso_total_cargas_nf_complemento = 0.0;
	private Double peso_total_cargas = 0.0, peso_total_cargas_nf_interna = 0.0;
	private JLabel lblPesoTotalRealCargas, lblPesoTotal, lblPesoTotalRealRestante, lblNoSelecionado;

	private JPanelGraficoPadrao painelGraficoCarregamento, painelGraficoPagamentos;

	private JTextArea entDescricaoDocumento;
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_aditivos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_distratos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_sub_contratos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_tarefas = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private final JLabel lblNewLabel_1 = new JLabel(
			"*Pagamentos apenas informativos, assim como elaborados no contrato");

	private final JLabel lblTipoContrato = new JLabel("Tipo Contrato:");
	private final JButton btnExcluirContrato = new JButton("Excluir");
	private JTable table_tarefas;
	private JButton btnImportarDeTerceiros, btnImportarDeTerceiros_1, btnSelecionarDocumento, btnAdicionarDocumento;

	private final JButton btnAdicionarCarregamento = new JButton("Adicionar");
	private JButton btnAssinarContrato, btnConcluir;
	private final JScrollPane scrollPaneCarregamento;
	private final JLabel lblNewLabel_4 = new JLabel("     Carregamento");
	private JTable table_carregamento;

	private JLabel lblDataContrato, lblCoberturaTotal, lblCorretor, lblCompradores, lblVendedores, lblValorSaco,
			lblQuantidade, lblValorTotal, lblSafra, lblProduto;
	private JTable table_pagamentos_contratuais;

	private JLabel lblTotalPagamentosRestantes, lblTotalPagamentosEfetuados, lblTotalPagamentos;

	private PainelInformativo painel_informacoes_tab_principal, painel_informacoes_tab_pagamentos;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private JComboBox cBTipoDocumento;
	private JTable table_aditivos;
	private JTable table_distratos;
	private JLabel lblCoberturaEfetuados, lblCoberturaTransferenciaNegativa, lblCoberturaTransferenciaPositiva,
			lblCoberturaRestante;

	private ArrayList<CadastroAditivo> lista_aditivos = new ArrayList<>();
	private ArrayList<CadastroDistrato> lista_distratos = new ArrayList<>();
	private JTextField statusPenhor;
	private JTextField entLocalizacao;
	private JTextField entFertilizante;

	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaGerenciarContrato(CadastroContrato contrato, Window janela_pai) {

		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();

		// setModal(true);
		// setAlwaysOnTop(true);

		contrato_local = contrato;
		isto = this;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setarTituloJanela();

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

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		GetData data = new GetData();

		painel_informacoes_tab_principal = new PainelInformativo();

		modelo.addColumn("Id Pagamento");
		modelo.addColumn("Id Conta");
		modelo.addColumn("CPF");
		modelo.addColumn("Nome");

		modelo.addColumn("Banco");

		modelo.addColumn("Codigo");
		modelo.addColumn("Agência");

		modelo.addColumn("Conta");
		modelo.addColumn("Valor");
		modelo.addColumn("Antecipado");
		modelo.addColumn("Cobre");

		modelo.addColumn("Data Pagamento");

		modelo_tarefas.addColumn("Id Tarefas");
		modelo_tarefas.addColumn("Status");

		modelo_tarefas.addColumn("Nome");
		modelo_tarefas.addColumn("Descrição");
		modelo_tarefas.addColumn("Mensagem");
		modelo_tarefas.addColumn("Resposta");

		modelo_tarefas.addColumn("Data");

		modelo_tarefas.addColumn("Hora");
		modelo_tarefas.addColumn("Criador");

		modelo_tarefas.addColumn("Executor");

		modelo_tarefas.addColumn("Hora Agendada");
		modelo_tarefas.addColumn("Data Agendada");
		modelo_tarefas.addColumn("Prioridade");

		setMenuCarregamento();

		painel_informacoes_tab_pagamentos = new PainelInformativo();

		setMenuPagamentos();

		painelPrincipal = new JTabbedPane();
		painelPrincipal.setBackground(Color.WHITE);

		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelDadosIniciais.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);

		if (contrato.getSub_contrato() == 0 || contrato.getSub_contrato() == 5) {
			// não é um subcontrato
			// criarAbaSubContrato();
			painelSubContratos.setBackground(new Color(255, 255, 255));
			painelPrincipal.addTab("Sub-Contratos", painelSubContratos);
			painelSubContratos.setLayout(null);

			criarAbaSubContrato();

		}
		painelRecebimentoEntrada.setBackground(new Color(255, 255, 255));

		painelDadosIniciais.setLayout(null);

		// adiciona o painel de recebimento
		painelPrincipal.addTab("Recebimento de Entrada", painelRecebimentoEntrada);
		painelRecebimentoEntrada.setLayout(new BorderLayout(0, 0));
		// panelPaiRecebimento.setLayout(new BorderLayout(0, 0));

		JPanel painel_pai_recebimentos = new JPanel();
		painel_pai_recebimentos.setBackground(Color.WHITE);
		// panelPaiRecebimento.add(panel_5);

		JScrollPane scroll_painel_pai_recebimentos = new JScrollPane(painel_pai_recebimentos);

		// painelRecebimentoEntrada.add(painel_pai_recebimentos);
		painelRecebimentoEntrada.add(scroll_painel_pai_recebimentos);
		painel_pai_recebimentos.setLayout(new MigLayout("", "[194px][12px][49px][36px][320px][12px][123px][9px][372px]",
				"[156px][24px][188px,grow][73px][31px][140px]"));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painel_pai_recebimentos.add(panel_5, "cell 0 0,alignx left,growy");
		panel_5.setLayout(new MigLayout("", "[]", "[][]"));

		JLabel lblNewLabel_4_1 = new JLabel("     Recebimento");
		panel_5.add(lblNewLabel_4_1, "cell 0 0");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4_1.setBackground(new Color(0, 51, 0));

		JLabel lblNewLabel_28 = new JLabel("");
		panel_5.add(lblNewLabel_28, "cell 0 1");
		lblNewLabel_28.setIcon(
				new ImageIcon(TelaGerenciarContrato.class.getResource("/imagens/icone_caminhao_descarregando4.png")));

		JPanel panel_13 = new JPanel();
		panel_13.setBackground(new Color(0, 51, 51));
		painel_pai_recebimentos.add(panel_13, "cell 8 0,alignx left,aligny center");
		GridBagLayout gbl_panel_13 = new GridBagLayout();
		gbl_panel_13.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_13.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_13.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_13.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_13.setLayout(gbl_panel_13);

		JLabel lblNewLabel_43 = new JLabel("Status do Recebimento");
		lblNewLabel_43.setForeground(Color.WHITE);
		lblNewLabel_43.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_43 = new GridBagConstraints();
		gbc_lblNewLabel_43.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_43.gridx = 1;
		gbc_lblNewLabel_43.gridy = 0;
		panel_13.add(lblNewLabel_43, gbc_lblNewLabel_43);

		JLabel lblNewLabel_40 = new JLabel("Recebimento:");
		lblNewLabel_40.setForeground(Color.WHITE);
		lblNewLabel_40.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_40 = new GridBagConstraints();
		gbc_lblNewLabel_40.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_40.gridx = 0;
		gbc_lblNewLabel_40.gridy = 2;
		panel_13.add(lblNewLabel_40, gbc_lblNewLabel_40);

		lblStatusRecebimentoEntrada = new JLabel("Aguardando:");
		lblStatusRecebimentoEntrada.setForeground(Color.WHITE);
		lblStatusRecebimentoEntrada.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblStatusRecebimentoEntrada = new GridBagConstraints();
		gbc_lblStatusRecebimentoEntrada.gridwidth = 3;
		gbc_lblStatusRecebimentoEntrada.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatusRecebimentoEntrada.gridx = 1;
		gbc_lblStatusRecebimentoEntrada.gridy = 2;
		panel_13.add(lblStatusRecebimentoEntrada, gbc_lblStatusRecebimentoEntrada);

		JLabel lblNewLabel_40_1 = new JLabel("NF's Venda:");
		lblNewLabel_40_1.setForeground(Color.WHITE);
		lblNewLabel_40_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_40_1 = new GridBagConstraints();
		gbc_lblNewLabel_40_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_40_1.gridx = 0;
		gbc_lblNewLabel_40_1.gridy = 3;
		panel_13.add(lblNewLabel_40_1, gbc_lblNewLabel_40_1);

		lblStatusNFVendaEntrada = new JLabel("Aguardando:");
		lblStatusNFVendaEntrada.setForeground(Color.WHITE);
		lblStatusNFVendaEntrada.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblStatusNFVendaEntrada = new GridBagConstraints();
		gbc_lblStatusNFVendaEntrada.gridwidth = 3;
		gbc_lblStatusNFVendaEntrada.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatusNFVendaEntrada.gridx = 1;
		gbc_lblStatusNFVendaEntrada.gridy = 3;
		panel_13.add(lblStatusNFVendaEntrada, gbc_lblStatusNFVendaEntrada);

		JLabel lblNewLabel_40_2 = new JLabel("NF' Remessa:");
		lblNewLabel_40_2.setForeground(Color.WHITE);
		lblNewLabel_40_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_40_2 = new GridBagConstraints();
		gbc_lblNewLabel_40_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_40_2.gridx = 0;
		gbc_lblNewLabel_40_2.gridy = 4;
		panel_13.add(lblNewLabel_40_2, gbc_lblNewLabel_40_2);

		lblStatusNFRemessaEntrada = new JLabel("Aguardando:");
		lblStatusNFRemessaEntrada.setForeground(Color.WHITE);
		lblStatusNFRemessaEntrada.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblStatusNFRemessaEntrada = new GridBagConstraints();
		gbc_lblStatusNFRemessaEntrada.gridwidth = 3;
		gbc_lblStatusNFRemessaEntrada.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatusNFRemessaEntrada.gridx = 1;
		gbc_lblStatusNFRemessaEntrada.gridy = 4;
		panel_13.add(lblStatusNFRemessaEntrada, gbc_lblStatusNFRemessaEntrada);

		JPanel panel_1 = new JPanel();
		painel_pai_recebimentos.add(panel_1, "cell 0 2 9 1,grow");
		panel_1.setBackground(Color.WHITE);

		table_recebimentos = new JTable(modelo_recebimentos);
		RecebimentoCellRender renderer_recebimentos = new RecebimentoCellRender();
		table_recebimentos.setDefaultRenderer(Object.class, renderer_recebimentos);
		table_recebimentos.setBackground(new Color(255, 255, 255));

		// table_recebimentos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		/*
		 * table_recebimentos.getColumnModel().getColumn(0).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(1).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(2).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(3).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(4).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(5).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(6).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(7).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(8).setPreferredWidth(150);
		 * table_recebimentos.getColumnModel().getColumn(9).setPreferredWidth(150);
		 */
		panel_1.setLayout(new BorderLayout(0, 0));

		table_recebimentos.setRowHeight(30);

		JScrollPane scrollPaneRomaneios = new JScrollPane(table_recebimentos);
		panel_1.add(scrollPaneRomaneios);

		JPanel lbl_produto_aba_recebimento = new JPanel();
		lbl_produto_aba_recebimento.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.setBackground(new Color(0, 102, 102));
		painel_pai_recebimentos.add(lbl_produto_aba_recebimento, "cell 2 0 5 1,growx,aligny top");
		lbl_produto_aba_recebimento.setLayout(
				new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px][]"));

		JLabel lblNewLabel_31 = new JLabel("Data:");
		lblNewLabel_31.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31, "cell 0 0,alignx right");

		lblDataContratoRecebimento = new JLabel("data contrato");
		lblDataContratoRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDataContratoRecebimento.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblDataContratoRecebimento, "cell 1 0,alignx center");

		JLabel lblNewLabel_31_1 = new JLabel("Corretor:");
		lblNewLabel_31_1.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_1, "cell 0 1,alignx right");

		lblCorretorRecebimento = new JLabel("corretor");
		lblCorretorRecebimento.setForeground(Color.WHITE);
		lblCorretorRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblCorretorRecebimento, "cell 1 1,alignx center");

		JLabel lblNewLabel_31_1_1 = new JLabel("Compradores:");
		lblNewLabel_31_1_1.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_1_1, "cell 0 2,alignx right");

		lblCompradoresRecebimento = new JLabel("compradores");
		lblCompradoresRecebimento.setForeground(Color.WHITE);
		lblCompradoresRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblCompradoresRecebimento, "cell 1 2,alignx center");

		JLabel lblNewLabel_31_1_1_1 = new JLabel("Vendedores:");
		lblNewLabel_31_1_1_1.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_1_1_1, "cell 0 3,alignx right");

		lblVendedoresRecebimento = new JLabel("vendedores");
		lblVendedoresRecebimento.setForeground(Color.WHITE);
		lblVendedoresRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblVendedoresRecebimento, "cell 1 3,alignx center");

		JLabel lblNewLabel_31_1_1_1_1 = new JLabel("Valor Unidade:");
		lblNewLabel_31_1_1_1_1.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_1_1_1_1, "cell 0 4,alignx right");

		lblValorUnidadeRecebimento = new JLabel("valor saco");
		lblValorUnidadeRecebimento.setForeground(Color.WHITE);
		lblValorUnidadeRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblValorUnidadeRecebimento, "cell 1 4,alignx center");

		JLabel lblNewLabel_31_2 = new JLabel("Quantidade:");
		lblNewLabel_31_2.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_2, "cell 2 4,alignx right");

		lblQuantidadeRecebimento = new JLabel("quantidade");
		lblQuantidadeRecebimento.setForeground(Color.WHITE);
		lblQuantidadeRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblQuantidadeRecebimento, "cell 3 4,alignx center");

		JLabel lblNewLabel_31_3_1 = new JLabel("Valor Total:");
		lblNewLabel_31_3_1.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_3_1, "cell 4 4,alignx right");

		lblValorTotalRecebimento = new JLabel("valor total pagamento do contrato");
		lblValorTotalRecebimento.setForeground(Color.WHITE);
		lblValorTotalRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblValorTotalRecebimento, "cell 5 4,alignx center");

		JLabel lblNewLabel_31_1_1_1_1_1 = new JLabel("Produto:");
		lblNewLabel_31_1_1_1_1_1.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_1_1_1_1_1, "flowy,cell 0 5,alignx right");

		lblProdutoRecebimento = new JLabel("produto");
		lblProdutoRecebimento.setForeground(Color.WHITE);
		lblProdutoRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblProdutoRecebimento, "cell 1 5,alignx center");

		JLabel lblNewLabel_31_3 = new JLabel("Safra:");
		lblNewLabel_31_3.setForeground(Color.WHITE);
		lbl_produto_aba_recebimento.add(lblNewLabel_31_3, "cell 2 5,alignx right");

		lblSafraRecebimento = new JLabel("safra");
		lblSafraRecebimento.setForeground(Color.WHITE);
		lblSafraRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));
		lbl_produto_aba_recebimento.add(lblSafraRecebimento, "cell 3 5,alignx center");

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		painel_pai_recebimentos.add(panel_9, "cell 8 3,growx,aligny top");
		panel_9.setLayout(new MigLayout("", "[][][][][][][][][][][][][]", "[]"));

		JButton btnExportarRecebimentos_1_1 = new JButton("Replicar");
		btnExportarRecebimentos_1_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int tipo = contrato.getSub_contrato();
				if (tipo == 0 || tipo == 3 || tipo == 4 || tipo == 5) {
					TelaReplicarRecebimento tela = new TelaReplicarRecebimento(contrato_local, isto);
					tela.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(isto, "Somente no contrato original é possivel realizar replicação");
				}
			}
		});
		panel_9.add(btnExportarRecebimentos_1_1, "cell 6 0");

		JButton btnExportarRecebimentos = new JButton("Exportar");
		panel_9.add(btnExportarRecebimentos, "cell 9 0");
		btnExportarRecebimentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Recebimentos");

				// Definindo alguns padroes de layout
				sheet.setDefaultColumnWidth(15);
				sheet.setDefaultRowHeight((short) 400);

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

				// celula para texto alinhado ao centro
				CellStyle textStyle = workbook.createCellStyle();
				textStyle.setAlignment(HorizontalAlignment.CENTER);
				textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// celula para numero alinhado ao centro
				CellStyle numberStyle = workbook.createCellStyle();
				numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
				numberStyle.setAlignment(HorizontalAlignment.CENTER);
				numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// estilo de celula negrito
				CellStyle negrito = workbook.createCellStyle();
				// textStyle.setAlignment(HorizontalAlignment.CENTER);
				negrito.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				negrito.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				negrito.setAlignment(HorizontalAlignment.CENTER);
				negrito.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFontNegrita = workbook.createFont();
				newFontNegrita.setBold(true);
				newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
				newFontNegrita.setFontName("Arial");
				newFontNegrita.setItalic(true);
				newFontNegrita.setFontHeight((short) (11 * 20));

				negrito.setFont(newFontNegrita);

				// estilo para celula texto alinhado a esquerda
				CellStyle negrito_esquerda = workbook.createCellStyle();
				// textStyle.setAlignment(HorizontalAlignment.CENTER);
				negrito_esquerda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				negrito_esquerda.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				negrito_esquerda.setAlignment(HorizontalAlignment.LEFT);
				negrito_esquerda.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFontNegritaEsquerda = workbook.createFont();
				newFontNegritaEsquerda.setBold(true);
				newFontNegritaEsquerda.setColor(IndexedColors.BLACK.getIndex());
				newFontNegritaEsquerda.setFontName("Arial");
				newFontNegritaEsquerda.setItalic(true);
				newFontNegritaEsquerda.setFontHeight((short) (11 * 20));

				negrito_esquerda.setFont(newFontNegritaEsquerda);

				// estilo para celula do tipo numero alinhado ao centro
				CellStyle valorStyle = workbook.createCellStyle();
				valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
				valorStyle.setAlignment(HorizontalAlignment.CENTER);
				valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// estilo para cabecalho fundo laranja
				CellStyle celula_fundo_laranja = workbook.createCellStyle();
				celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_laranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
				celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont = workbook.createFont();
				newFont.setBold(true);
				newFont.setColor(IndexedColors.BLACK.getIndex());
				newFont.setFontName("Calibri");
				newFont.setItalic(false);
				newFont.setFontHeight((short) (11 * 20));

				celula_fundo_laranja.setFont(newFont);

				// celula_number_amarelo_texto_preto
				// estilo para cabecalho fundo laranja
				CellStyle celula_number_amarelo_texto_preto = workbook.createCellStyle();
				celula_number_amarelo_texto_preto.setDataFormat(numberFormat.getFormat("#,##0.00"));
				celula_number_amarelo_texto_preto.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_number_amarelo_texto_preto.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				celula_number_amarelo_texto_preto.setAlignment(HorizontalAlignment.CENTER);
				celula_number_amarelo_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont_blabk = workbook.createFont();
				newFont_blabk.setBold(true);
				newFont_blabk.setColor(IndexedColors.BLACK.getIndex());
				newFont_blabk.setFontName("Calibri");
				newFont_blabk.setItalic(false);
				newFont_blabk.setFontHeight((short) (11 * 20));

				celula_number_amarelo_texto_preto.setFont(newFont_blabk);

				// estilo para cabecalho fundo laranja
				CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
				celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
				celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont_branca = workbook.createFont();
				newFont_branca.setBold(true);
				newFont_branca.setColor(IndexedColors.WHITE.getIndex());
				newFont_branca.setFontName("Calibri");
				newFont_branca.setItalic(false);
				newFont_branca.setFontHeight((short) (11 * 20));

				celula_fundo_laranja_texto_branco.setFont(newFont_branca);

				// Configurando as informacoes
				row = sheet.createRow(rownum++);

				CadastroCliente compradores[] = contrato_local.getCompradores();
				CadastroCliente vendedores[] = contrato_local.getVendedores();

				String nome_compradores;
				String nome_vendedores;
				if (compradores[0].getTipo_pessoa() == 0) {
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();
				}

				if (compradores[1] != null) {
					if (compradores[1].getTipo_pessoa() == 0) {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
					} else {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();
					}
				}

				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedores = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedores = vendedores[0].getNome_fantaia();
				}

				if (vendedores[1] != null) {
					if (vendedores[1].getTipo_pessoa() == 0) {
						nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
					} else {
						nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
					}
				}

				cell = row.createCell(cellnum);
				cell.setCellStyle(celula_fundo_laranja);
				cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
						+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
				sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 5));

				// linha quantidade, safra, sacos, etc
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja);

				double quantidade_kg = 0;
				double quantidade_sacos = 0;

				if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = quantidade_sacos * 60;
				}

				Locale ptBr = new Locale("pt", "BR");
				cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
						+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto()) + " por "
						+ contrato.getMedida() + " no valor total de: "
						+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar()));
				// cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " +
				// quantidade_sacos + " Sacos" );
				sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja);
				cell.setCellValue(contrato.getModelo_safra().getProduto().getNome_produto() + " "
						+ contrato.getModelo_safra().getProduto().getTransgenia() + " "
						+ contrato.getModelo_safra().getAno_plantio() + "/"
						+ contrato.getModelo_safra().getAno_colheita());
				sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

				// Configurando Header
				row = sheet.createRow(rownum++);
				cellnum = 0;
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("DATA");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Romaneio".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso Romaneio".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("NF Venda".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso NF Venda".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Valor NF Venda".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("NF Remessa".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso NF Remessa".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Valor NF Remessa".toUpperCase());

				ArrayList<CadastroContrato.Recebimento> contratos_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = table_recebimentos.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					CadastroContrato.Recebimento contrato_selecionados = lista_recebimentos.get(indice);
					contratos_selecionados.add(contrato_selecionados);
				}

				double quantidade_total_kgs_recebido = 0;
				double quantidade_total_kgs_nf_venda = 0;
				double quantidade_total_kgs_nf_remessa = 0;

				BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
				BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

				// checkagens
				boolean nf_remessa_ativo = false;
				boolean nf_venda_ativo = false;

				// checka se ha no minimo uma nf remessa aplicavel
				for (CadastroContrato.Recebimento recebimento : lista_recebimentos) {
					if (recebimento.getNf_remessa_aplicavel() == 1) {
						nf_remessa_ativo = true;
						break;
					}

				}

				// checka se ha no minimo uma nf venda aplicavel
				for (CadastroContrato.Recebimento recebimento : lista_recebimentos) {
					if (recebimento.getNf_venda_aplicavel() == 1) {
						nf_venda_ativo = true;
						break;
					}

				}

				for (CadastroContrato.Recebimento cadastro : contratos_selecionados) {

					row = sheet.createRow(rownum++);
					cellnum = 0;
					/*
					 * codigo compradores vendedores status quantidade medida produto transgenia
					 * safra valor_produto valor_total data_contrato local_retirada
					 */
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(cadastro.getData_recebimento());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(cadastro.getCodigo_romaneio());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(numberStyle);
					cell.setCellValue(cadastro.getPeso_romaneio());

					if (cadastro.getNf_venda_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(cadastro.getCodigo_nf_venda());

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(cadastro.getPeso_nf_venda());
						quantidade_total_kgs_nf_venda = quantidade_total_kgs_nf_venda + cadastro.getPeso_nf_venda();

						cell = row.createCell(cellnum++);
						cell.setCellStyle(valorStyle);
						cell.setCellValue(cadastro.getValor_nf_venda().doubleValue());
						valor_total_nf_venda = valor_total_nf_venda.add(cadastro.getValor_nf_venda());

					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					if (cadastro.getNf_remessa_aplicavel() == 1) {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(cadastro.getCodigo_nf_remessa());

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(cadastro.getPeso_nf_remessa());
						quantidade_total_kgs_nf_remessa = quantidade_total_kgs_nf_remessa
								+ cadastro.getPeso_nf_remessa();

						cell = row.createCell(cellnum++);
						cell.setCellStyle(valorStyle);
						cell.setCellValue(cadastro.getValor_nf_remessa().doubleValue());
						valor_total_nf_remessa = valor_total_nf_remessa.add(cadastro.getValor_nf_remessa());

					} else {
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue("Não Aplicável");
					}

					quantidade_total_kgs_recebido = quantidade_total_kgs_recebido + cadastro.getPeso_romaneio();

				}
				sheet.setAutoFilter(CellRangeAddress.valueOf("A4:I4"));
				NumberFormat z = NumberFormat.getNumberInstance();

				// somatorias
				row = sheet.createRow(rownum++);

				cell = row.createCell(2);
				cell.setCellStyle(negrito);
				cell.setCellValue(z.format(quantidade_total_kgs_recebido) + " Kgs");

				cell = row.createCell(4);
				cell.setCellStyle(negrito);
				cell.setCellValue(z.format(quantidade_total_kgs_nf_venda) + " Kgs");

				cell = row.createCell(5);
				cell.setCellStyle(negrito);
				cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda.doubleValue()));

				cell = row.createCell(7);
				cell.setCellStyle(negrito);
				cell.setCellValue(z.format(quantidade_total_kgs_nf_remessa) + " Kgs");

				cell = row.createCell(8);
				cell.setCellStyle(negrito);
				cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_remessa.doubleValue()));

				//////////////////////
				row = sheet.createRow(rownum += 2);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso Recebido: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				cell.setCellValue(quantidade_total_kgs_recebido);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso NF's Venda: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				if (nf_venda_ativo)
					cell.setCellValue(quantidade_total_kgs_nf_venda);
				else
					cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Valor NF's Venda: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);

				if (nf_venda_ativo)
					cell.setCellValue(valor_total_nf_venda.doubleValue());
				else
					cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso NF's Remessa: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				if (nf_remessa_ativo)
					cell.setCellValue(quantidade_total_kgs_nf_remessa);
				else
					cell.setCellValue("Não Aplicável");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Valor NF's Remessa: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				if (nf_remessa_ativo)
					cell.setCellValue(valor_total_nf_remessa.doubleValue());
				else
					cell.setCellValue("Não Aplicável");

				row = sheet.createRow(rownum += 2);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(textStyle);
				cell.setCellValue("");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso a Receber: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				cell.setCellValue(quantidade_kg - quantidade_total_kgs_recebido);

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso a emitir: ");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				if (nf_venda_ativo)
					cell.setCellValue(quantidade_kg - quantidade_total_kgs_nf_venda);
				else
					cell.setCellValue("Não Aplicável");

				cell = row.createCell(7);
				cell.setCellStyle(celula_fundo_laranja_texto_branco);
				cell.setCellValue("Peso a emitir: ");

				cell = row.createCell(8);
				cell.setCellStyle(celula_number_amarelo_texto_preto);
				if (nf_remessa_ativo)
					cell.setCellValue(quantidade_kg - quantidade_total_kgs_nf_remessa);
				else
					cell.setCellValue("Não Aplicável");

				row = sheet.createRow(rownum += 2);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(negrito_esquerda);
				cell.setCellValue("Status gerado de forma automatica: ");

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 2));

				row = sheet.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(negrito_esquerda);

				String texto = "";
				double diferenca = quantidade_kg - quantidade_total_kgs_recebido;
				if (diferenca == 0) {
					texto = texto + "Recebimento Concluído";
				} else if (diferenca > 0) {
					texto = texto + "Recebimento Incompleto, falta receber " + z.format(diferenca) + " Kgs";

				} else if (diferenca < 0) {
					texto = texto + "Recebimento Excedido, excedeu " + z.format(diferenca) + " Kgs";

				}

				cell.setCellValue(texto);

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

				// status de nf de venda
				row = sheet.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(negrito_esquerda);

				String texto_nf_venda = "";
				double diferenca_nf_venda = quantidade_kg - quantidade_total_kgs_nf_venda;
				if (diferenca_nf_venda == 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído";
				} else if (diferenca_nf_venda > 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir "
							+ z.format(diferenca_nf_venda) + " Kgs";

				} else if (diferenca_nf_venda < 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu "
							+ z.format(diferenca_nf_venda) + " Kgs";

				}

				if (nf_venda_ativo)
					cell.setCellValue(texto_nf_venda);
				else
					cell.setCellValue("Emissão de NF's Não Aplicável");

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 4));

				// status nf remessa
				row = sheet.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(negrito_esquerda);

				String texto_nf_remessa = "";
				double diferenca_nf_remessa = quantidade_kg - quantidade_total_kgs_nf_remessa;
				if (diferenca_nf_remessa == 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Concluído";
				} else if (diferenca_nf_remessa > 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's de Remessa Incompleto, falta emitir "
							+ z.format(diferenca_nf_remessa) + " Kgs";

				} else if (diferenca_nf_remessa < 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Remessa, excedeu "
							+ z.format(diferenca_nf_remessa) + " Kgs";

				}

				if (nf_remessa_ativo)
					cell.setCellValue(texto_nf_remessa);
				else
					cell.setCellValue("Emissão de NF's Não Aplicável");

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 4));

				try {

					new JFXPanel();
					Platform.runLater(() -> {

						// pegar ultima pasta
						ManipularTxt manipular_ultima_pasta = new ManipularTxt();
						String ultima_pasta = manipular_ultima_pasta
								.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
						if (d == null) {
							d = new FileChooser();
						}
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

		JButton btnExcluirRecebimento = new JButton("Excluir");
		panel_9.add(btnExcluirRecebimento, "cell 10 0");
		btnExcluirRecebimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				int rowSel = table_recebimentos.getSelectedRow();// pega o indice da linha na tabela

				CadastroContrato.Recebimento recebimento_excluir = lista_recebimentos.get(rowSel);

				GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

				String descricao = table_recebimentos.getValueAt(rowSel, 2).toString();

				if (!(descricao.equalsIgnoreCase("Replica"))) {
					boolean excluir = gerenciar_contratos.removerRecebimento(contrato_local.getId(),
							recebimento_excluir.getId_recebimento());

					if (excluir) {

						// deletar o diretorio do recebimento
						String diretorio_contrato = servidor_unidade + contrato_local.getCaminho_diretorio_contrato()
								+ "\\recebimentos";
						String diretorio_recebimento = diretorio_contrato + "\\recebimento_"
								+ recebimento_excluir.getId_recebimento();

						ManipularTxt manipular = new ManipularTxt();
						boolean deletar = manipular.limparDiretorio(new File(diretorio_recebimento));
						if (deletar) {
							JOptionPane.showMessageDialog(isto, "Recebimento Excluido");
						} else {
							JOptionPane.showMessageDialog(isto,
									"Recebimento Excluido, mas a pasta do recebimento ainda existe\nConsulte o administrador");

						}

						if (contrato_local.getCaminho_diretorio_contrato2() != null) {
							// deletar o direotiro do recebimento do contrato 2
							String diretorio_contrato2 = servidor_unidade
									+ contrato_local.getCaminho_diretorio_contrato2() + "\\recebimentos";
							String diretorio_recebimento2 = diretorio_contrato + "\\recebimento_"
									+ recebimento_excluir.getId_recebimento();

							deletar = manipular.limparDiretorio(new File(diretorio_recebimento2));
							if (deletar) {
								JOptionPane.showMessageDialog(isto, "Recebimento Excluido");
							} else {
								JOptionPane.showMessageDialog(isto,
										"Recebimento Excluido, mas a pasta do recebimento ainda existe\nConsulte o administrador");

							}
						}

						pesquisar_recebimentos(true);
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao excluir recebimentos\nConsulte o administrador");

					}
				} else {
					JOptionPane.showMessageDialog(isto, "Exclua essa replica no contrato de origem");
				}

			}
		});
		botoes.add(btnExcluirRecebimento);

		JButton btnEditar = new JButton("Editar");
		panel_9.add(btnEditar, "cell 11 0");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_recebimentos.getSelectedRow();// pega o indice da linha na tabela

				String descricao = table_recebimentos.getValueAt(rowSel, 2).toString();
				int tipo = contrato_local.getSub_contrato();
				if (descricao.equalsIgnoreCase("Replica") && (tipo != 0 || tipo != 3 || tipo != 5 || tipo != 4)) {
					JOptionPane.showMessageDialog(null, "Edite a replica no contrato de origem");
				} else {

					TelaConfirmarRecebimento tela = new TelaConfirmarRecebimento(1, lista_recebimentos.get(rowSel),
							contrato_local, isto);
					tela.setTelaPai(isto);
					tela.setVisible(true);
				}
			}
		});
		botoes.add(btnEditar);

		JButton btnAdicionarRecebimento = new JButton("Adicionar");
		panel_9.add(btnAdicionarRecebimento, "cell 12 0");
		botoes.add(btnAdicionarRecebimento);

		btnAdicionarRecebimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConfirmarRecebimento tela = new TelaConfirmarRecebimento(0, null, contrato_local, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		painel_pai_recebimentos.add(panel_8, "cell 0 3 3 1,alignx left,growy");
		panel_8.setLayout(new MigLayout("", "[27.00][26.00][31.00][][][][][]", "[][][][][]"));

		JLabel lblNewLabel_33_2 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_2, "cell 0 0,grow");
		lblNewLabel_33_2.setOpaque(true);
		lblNewLabel_33_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_2.setBackground(Color.green);

		JLabel lblNewLabel_34_2 = new JLabel("OK");
		panel_8.add(lblNewLabel_34_2, "cell 1 0,alignx left");
		lblNewLabel_34_2.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_33_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1, "cell 2 0,growx");
		lblNewLabel_33_1_1.setOpaque(true);
		lblNewLabel_33_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1.setBackground(Color.gray);

		JLabel lblNewLabel_34_1_1 = new JLabel("Falta NF Remessa e Venda");
		panel_8.add(lblNewLabel_34_1_1, "cell 3 0,alignx left");
		lblNewLabel_34_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_33_2_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_2_1, "cell 0 1,growx");
		lblNewLabel_33_2_1.setOpaque(true);
		lblNewLabel_33_2_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_2_1.setBackground(Color.ORANGE);

		JLabel lblNewLabel_34_2_1 = new JLabel("Falta NF Venda");
		panel_8.add(lblNewLabel_34_2_1, "cell 1 1,alignx left");
		lblNewLabel_34_2_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_2_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_33_1_1_1 = new JLabel("     ");
		panel_8.add(lblNewLabel_33_1_1_1, "cell 2 1,growx");
		lblNewLabel_33_1_1_1.setOpaque(true);
		lblNewLabel_33_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_1_1.setBackground(Color.YELLOW);

		JLabel lblNewLabel_34_1_1_1 = new JLabel("Falta NF Remessa");
		panel_8.add(lblNewLabel_34_1_1_1, "cell 3 1,alignx left");
		lblNewLabel_34_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_1_1_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		painelGraficoRecebimento = new JPanelGraficoPadrao(0, 0, "Recebido:", "a Receber:");
		painelGraficoRecebimento.setLayout(null);
		painel_pai_recebimentos.add(painelGraficoRecebimento, "cell 4 3 1 3,grow");

		JLabel lblNewLabel_7_1 = new JLabel("Recebimentos desse contrato:");
		lblNewLabel_7_1.setForeground(new Color(102, 51, 0));
		lblNewLabel_7_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_7_1.setBackground(new Color(102, 51, 0));
		painel_pai_recebimentos.add(lblNewLabel_7_1, "cell 2 1 3 1,alignx left,aligny top");

		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.WHITE);
		painel_pai_recebimentos.add(panel_11, "cell 0 5 3 1,alignx right,aligny top");
		panel_11.setLayout(new MigLayout("", "[][]", "[][][][][]"));

		JLabel lblNewLabel_3_2 = new JLabel("Recebimentos:");
		panel_11.add(lblNewLabel_3_2, "cell 0 0");

		JLabel lblNewLabel_12_1 = new JLabel("Total:");
		panel_11.add(lblNewLabel_12_1, "cell 0 1");

		lblPesoTotalContrato = new JLabel("0.0 KG");
		panel_11.add(lblPesoTotalContrato, "cell 1 1");
		lblPesoTotalContrato.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalContrato.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_13_3 = new JLabel("Total Recebido:");
		panel_11.add(lblNewLabel_13_3, "cell 0 2");

		lblPesoTotalRecebido = new JLabel("0.0 Kg");
		panel_11.add(lblPesoTotalRecebido, "cell 1 2");
		lblPesoTotalRecebido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRecebido.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_13_1_1 = new JLabel("Restante:");
		panel_11.add(lblNewLabel_13_1_1, "cell 0 3");

		lblPesoTotalRestante = new JLabel("0.0 Kg");
		panel_11.add(lblPesoTotalRestante, "cell 1 3");
		lblPesoTotalRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRestante.setBorder(new LineBorder(new Color(0, 0, 0)));

		JPanel panel_24 = new JPanel();
		panel_24.setBackground(Color.WHITE);
		painel_pai_recebimentos.add(panel_24, "cell 6 3 3 3,growx,aligny center");
		panel_24.setLayout(new MigLayout("", "[][][]", "[]"));

		JPanel panel_6 = new JPanel();
		panel_24.add(panel_6, "cell 0 0");
		panel_6.setBackground(new Color(102, 153, 102));
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_6.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_6.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);

		JLabel lblNewLabel_32_1 = new JLabel("Romaneios");
		lblNewLabel_32_1.setForeground(Color.BLACK);
		lblNewLabel_32_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_32_1 = new GridBagConstraints();
		gbc_lblNewLabel_32_1.gridwidth = 2;
		gbc_lblNewLabel_32_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32_1.gridx = 0;
		gbc_lblNewLabel_32_1.gridy = 0;
		panel_6.add(lblNewLabel_32_1, gbc_lblNewLabel_32_1);

		JLabel lblNewLabel_32 = new JLabel("Num Romaneios:");
		GridBagConstraints gbc_lblNewLabel_32 = new GridBagConstraints();
		gbc_lblNewLabel_32.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32.gridx = 0;
		gbc_lblNewLabel_32.gridy = 2;
		panel_6.add(lblNewLabel_32, gbc_lblNewLabel_32);
		lblNewLabel_32.setForeground(Color.BLACK);
		lblNewLabel_32.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblNumRomaneiosRecebimento = new JLabel("0");
		GridBagConstraints gbc_lblNumRomaneiosRecebimento = new GridBagConstraints();
		gbc_lblNumRomaneiosRecebimento.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumRomaneiosRecebimento.gridx = 1;
		gbc_lblNumRomaneiosRecebimento.gridy = 2;
		panel_6.add(lblNumRomaneiosRecebimento, gbc_lblNumRomaneiosRecebimento);
		lblNumRomaneiosRecebimento.setForeground(Color.BLACK);
		lblNumRomaneiosRecebimento.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_32_2 = new JLabel("Peso Total:");
		GridBagConstraints gbc_lblNewLabel_32_2 = new GridBagConstraints();
		gbc_lblNewLabel_32_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_32_2.gridx = 0;
		gbc_lblNewLabel_32_2.gridy = 3;
		panel_6.add(lblNewLabel_32_2, gbc_lblNewLabel_32_2);
		lblNewLabel_32_2.setForeground(Color.BLACK);
		lblNewLabel_32_2.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblTotalSacosKGsRomaneios = new JLabel("0.0");
		GridBagConstraints gbc_lblTotalSacosKGsRomaneios = new GridBagConstraints();
		gbc_lblTotalSacosKGsRomaneios.gridx = 1;
		gbc_lblTotalSacosKGsRomaneios.gridy = 3;
		panel_6.add(lblTotalSacosKGsRomaneios, gbc_lblTotalSacosKGsRomaneios);
		lblTotalSacosKGsRomaneios.setForeground(Color.BLACK);
		lblTotalSacosKGsRomaneios.setFont(new Font("SansSerif", Font.BOLD, 14));

		JPanel panel_10 = new JPanel();
		panel_24.add(panel_10, "cell 1 0");
		panel_10.setBackground(new Color(204, 153, 102));
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_10.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_10.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_10.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_10.setLayout(gbl_panel_10);

		JLabel lblNewLabel_39 = new JLabel("Notas Fiscais Venda");
		GridBagConstraints gbc_lblNewLabel_39 = new GridBagConstraints();
		gbc_lblNewLabel_39.gridwidth = 2;
		gbc_lblNewLabel_39.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_39.gridx = 0;
		gbc_lblNewLabel_39.gridy = 0;
		panel_10.add(lblNewLabel_39, gbc_lblNewLabel_39);
		lblNewLabel_39.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_32_2_1 = new JLabel("Peso Emitido:");
		GridBagConstraints gbc_lblNewLabel_32_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_32_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32_2_1.gridx = 0;
		gbc_lblNewLabel_32_2_1.gridy = 2;
		panel_10.add(lblNewLabel_32_2_1, gbc_lblNewLabel_32_2_1);
		lblNewLabel_32_2_1.setForeground(Color.BLACK);
		lblNewLabel_32_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblPesoTotalNFVendaEntrada = new JLabel("0.0");
		GridBagConstraints gbc_lblPesoTotalNFVendaEntrada = new GridBagConstraints();
		gbc_lblPesoTotalNFVendaEntrada.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoTotalNFVendaEntrada.gridx = 1;
		gbc_lblPesoTotalNFVendaEntrada.gridy = 2;
		panel_10.add(lblPesoTotalNFVendaEntrada, gbc_lblPesoTotalNFVendaEntrada);
		lblPesoTotalNFVendaEntrada.setForeground(Color.BLACK);
		lblPesoTotalNFVendaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_32_2_1_1 = new JLabel("Peso a Emitir:");
		GridBagConstraints gbc_lblNewLabel_32_2_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_32_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32_2_1_1.gridx = 0;
		gbc_lblNewLabel_32_2_1_1.gridy = 3;
		panel_10.add(lblNewLabel_32_2_1_1, gbc_lblNewLabel_32_2_1_1);
		lblNewLabel_32_2_1_1.setForeground(Color.BLACK);
		lblNewLabel_32_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblPesoRestanteNFVendaEntrada = new JLabel("0.0");
		GridBagConstraints gbc_lblPesoRestanteNFVendaEntrada = new GridBagConstraints();
		gbc_lblPesoRestanteNFVendaEntrada.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoRestanteNFVendaEntrada.gridx = 1;
		gbc_lblPesoRestanteNFVendaEntrada.gridy = 3;
		panel_10.add(lblPesoRestanteNFVendaEntrada, gbc_lblPesoRestanteNFVendaEntrada);
		lblPesoRestanteNFVendaEntrada.setForeground(Color.BLACK);
		lblPesoRestanteNFVendaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblNewLabel_38 = new JLabel("$ Total:");
		GridBagConstraints gbc_lblNewLabel_38 = new GridBagConstraints();
		gbc_lblNewLabel_38.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_38.gridx = 0;
		gbc_lblNewLabel_38.gridy = 4;
		panel_10.add(lblNewLabel_38, gbc_lblNewLabel_38);
		lblNewLabel_38.setFont(new Font("SansSerif", Font.PLAIN, 16));

		lblValorTotalNFVendaEntrada = new JLabel("0.0");
		GridBagConstraints gbc_lblValorTotalNFVendaEntrada = new GridBagConstraints();
		gbc_lblValorTotalNFVendaEntrada.gridx = 1;
		gbc_lblValorTotalNFVendaEntrada.gridy = 4;
		panel_10.add(lblValorTotalNFVendaEntrada, gbc_lblValorTotalNFVendaEntrada);
		lblValorTotalNFVendaEntrada.setForeground(Color.BLACK);
		lblValorTotalNFVendaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));

		JPanel panel_12 = new JPanel();
		panel_24.add(panel_12, "cell 2 0");
		panel_12.setBackground(new Color(204, 153, 153));
		GridBagLayout gbl_panel_12 = new GridBagLayout();
		gbl_panel_12.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_12.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_12.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_12.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_12.setLayout(gbl_panel_12);

		JLabel lblNewLabel_41 = new JLabel("Notas Fiscais Remessa");
		lblNewLabel_41.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_41 = new GridBagConstraints();
		gbc_lblNewLabel_41.gridwidth = 2;
		gbc_lblNewLabel_41.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_41.gridx = 0;
		gbc_lblNewLabel_41.gridy = 0;
		panel_12.add(lblNewLabel_41, gbc_lblNewLabel_41);

		JLabel lblNewLabel_42 = new JLabel("Peso Emitido:");
		lblNewLabel_42.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_42 = new GridBagConstraints();
		gbc_lblNewLabel_42.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_42.gridx = 0;
		gbc_lblNewLabel_42.gridy = 2;
		panel_12.add(lblNewLabel_42, gbc_lblNewLabel_42);

		lblPesoTotalNFRemessaEntrada = new JLabel("0.0");
		lblPesoTotalNFRemessaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPesoTotalNFRemessaEntrada = new GridBagConstraints();
		gbc_lblPesoTotalNFRemessaEntrada.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoTotalNFRemessaEntrada.gridx = 1;
		gbc_lblPesoTotalNFRemessaEntrada.gridy = 2;
		panel_12.add(lblPesoTotalNFRemessaEntrada, gbc_lblPesoTotalNFRemessaEntrada);

		JLabel lblNewLabel_42_1 = new JLabel("Peso a Emitir:");
		lblNewLabel_42_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_42_1 = new GridBagConstraints();
		gbc_lblNewLabel_42_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_42_1.gridx = 0;
		gbc_lblNewLabel_42_1.gridy = 3;
		panel_12.add(lblNewLabel_42_1, gbc_lblNewLabel_42_1);

		lblPesoRestanteNFRemessaEntrada = new JLabel("0.0");
		lblPesoRestanteNFRemessaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPesoRestanteNFRemessaEntrada = new GridBagConstraints();
		gbc_lblPesoRestanteNFRemessaEntrada.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoRestanteNFRemessaEntrada.gridx = 1;
		gbc_lblPesoRestanteNFRemessaEntrada.gridy = 3;
		panel_12.add(lblPesoRestanteNFRemessaEntrada, gbc_lblPesoRestanteNFRemessaEntrada);

		JLabel lblNewLabel_42_1_1 = new JLabel("$ Total:");
		lblNewLabel_42_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_42_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_42_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_42_1_1.gridx = 0;
		gbc_lblNewLabel_42_1_1.gridy = 4;
		panel_12.add(lblNewLabel_42_1_1, gbc_lblNewLabel_42_1_1);

		lblValorTotalNFRemessaEntrada = new JLabel("0.0");
		lblValorTotalNFRemessaEntrada.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblValorTotalNFRemessaEntrada = new GridBagConstraints();
		gbc_lblValorTotalNFRemessaEntrada.gridx = 1;
		gbc_lblValorTotalNFRemessaEntrada.gridy = 4;
		panel_12.add(lblValorTotalNFRemessaEntrada, gbc_lblValorTotalNFRemessaEntrada);
		painelComprovantes.setLayout(new MigLayout("",
				"[431px,grow][52px,grow][47px,grow][12px,grow][59px,grow][9px,grow][27px,grow][4px,grow][200px,grow][10px,grow][89px,grow][24px,grow][1px,grow][330px,grow]",
				"[][24px][][][30px][150px][][22px][24px][][][6px][150px][40px][28px][2px][14px][12px][33px][12px][39px][37px][27px][19px][22px][24px][1px][5px][79px][13px][39px][17px][23px][34px][58px]"));

		btnCriarAditivo = new JButton("Criar Aditivo");
		btnCriarAditivo.setBackground(new Color(0, 51, 0));
		btnCriarAditivo.setForeground(Color.WHITE);
		btnCriarAditivo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnCriarAditivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCriarAditivo tela = new TelaCriarAditivo(contrato_local, isto);

				tela.setTelaPai(isto);
				tela.setVisible(true);

			}
		});

		btnImportarDeTerceiros = new JButton("Importar de Terceiros");
		btnImportarDeTerceiros.setBackground(new Color(0, 0, 102));
		btnImportarDeTerceiros.setForeground(Color.WHITE);
		btnImportarDeTerceiros.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnImportarDeTerceiros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importarAditivo();
			}
		});

		JLabel lblNewLabel_30 = new JLabel("Aditivos:");
		lblNewLabel_30.setForeground(Color.WHITE);
		lblNewLabel_30.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelComprovantes.add(lblNewLabel_30, "cell 0 3 1 2,alignx left,aligny top");
		botoes.add(btnImportarDeTerceiros);
		painelComprovantes.add(btnImportarDeTerceiros, "cell 4 6,alignx right,aligny top");

		botoes.add(btnCriarAditivo);
		painelComprovantes.add(btnCriarAditivo, "cell 8 6,alignx center,aligny top");

		JLabel lblNewLabel_30_1 = new JLabel("Distratos:");
		lblNewLabel_30_1.setForeground(Color.WHITE);
		lblNewLabel_30_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelComprovantes.add(lblNewLabel_30_1, "cell 0 10 1 2,alignx left,aligny top");

		btnCriarDistrato = new JButton("Criar Distrato");
		btnCriarDistrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCriarDistrato tela = new TelaCriarDistrato(contrato_local, isto);

				tela.setTelaPai(isto);
				tela.setVisible(true);

			}
		});
		btnCriarDistrato.setBackground(new Color(0, 51, 0));
		btnCriarDistrato.setForeground(Color.WHITE);
		btnCriarDistrato.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelComprovantes.add(btnCriarDistrato, "cell 8 14,alignx center,aligny top");
		botoes.add(btnCriarDistrato);
		painelComprovantes.setBackground(new Color(0, 102, 153));
		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel painel_pai_di = new JPanel();
		JScrollPane scroll_painel_pai = new JScrollPane(painel_pai_di);

		// painelDadosIniciais.add(painel_pai_di);
		painelDadosIniciais.add(scroll_painel_pai);
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setLayout(new MigLayout("", "[734px, grow][558px, grow]", "[827px, grow]"));

		painel_pai_di.setLayout(new MigLayout("", "[1343px, grow]", "[876px, grow]"));

		painel_pai_di.add(panel_2, "cell 0 0,grow");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_2.add(panel_3, "cell 0 0,grow");
		panel_3.setLayout(new BorderLayout(0, 0));

		painelVizualizarContrato = new JPanel();
		painelVizualizarContrato.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelVizualizarContrato.setBackground(Color.WHITE);
		panel_3.add(painelVizualizarContrato);
		painelVizualizarContrato.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_2.add(panel_7, "cell 1 0,grow");
		panel_7.setLayout(new MigLayout("", "[grow]", "[][][400px,grow][100px,grow]"));
		panel_7.add(lblStatusContrato, "cell 0 0,growx");

		lblStatusContrato.setBackground(new Color(0, 128, 128));
		lblStatusContrato.setOpaque(true);
		lblStatusContrato.setForeground(Color.WHITE);
		lblStatusContrato.setFont(new Font("Arial", Font.PLAIN, 24));
		panel_7.add(lblTipoContrato, "cell 0 1,growx");
		lblTipoContrato.setOpaque(true);
		lblTipoContrato.setForeground(Color.WHITE);
		lblTipoContrato.setFont(new Font("Arial", Font.PLAIN, 24));
		lblTipoContrato.setBackground(new Color(102, 0, 102));

		JPanel panelInformativoPrincipal = new JPanel();
		panel_7.add(panelInformativoPrincipal, "cell 0 2,grow");
		panelInformativoPrincipal.setBackground(new Color(0, 153, 153));
		panelInformativoPrincipal.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInformativoPrincipal.add(painel_informacoes_tab_principal);
		// panelInformativoPrincipal.setBounds(0, 0, 200, 200);
		panelInformativoPrincipal.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JPanel panel_22 = new JPanel();
		panel_22.setBackground(Color.WHITE);
		panel_7.add(panel_22, "cell 0 3,grow");
		panel_22.setLayout(new MigLayout("", "[grow][][][][]", "[][][grow][]"));

		JButton btnNewButton = new JButton("Visão Geral");
		panel_22.add(btnNewButton, "cell 0 0,alignx center");
		btnNewButton.setBackground(new Color(0, 51, 51));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnNewButton.setForeground(Color.WHITE);

		JButton btnVizualizar = new JButton("Vizualizar");
		panel_22.add(btnVizualizar, "cell 1 0,alignx center");
		btnVizualizar.setBackground(new Color(0, 51, 51));
		btnVizualizar.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnVizualizar.setForeground(Color.WHITE);
		panel_22.add(btnEnviarMsg, "cell 2 0,alignx center");
		btnEnviarMsg.setBackground(new Color(0, 51, 51));
		btnEnviarMsg.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnEnviarMsg.setForeground(Color.WHITE);
		botoes.add(btnEnviarMsg);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaAlterarContrato tela = new TelaAlterarContrato(contrato, isto);
				tela.setVisible(true);
				
			}
		});
		btnAlterar.setForeground(Color.WHITE);
		btnAlterar.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnAlterar.setBackground(new Color(0, 51, 51));
		panel_22.add(btnAlterar, "flowx,cell 3 0");

		btnAssinarContrato = new JButton("Assinar");
		panel_22.add(btnAssinarContrato, "cell 3 0,alignx center");
		btnAssinarContrato.setBackground(new Color(0, 51, 51));
		btnAssinarContrato.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnAssinarContrato.setForeground(Color.WHITE);
		botoes.add(btnAssinarContrato);
		btnAssinarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				assinar();
			}
		});
		botoes.add(btnAssinarContrato);

		btnConcluir = new JButton("Concluir");
		panel_22.add(btnConcluir, "cell 4 0,alignx center");
		btnConcluir.setBackground(new Color(0, 51, 51));
		btnConcluir.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnConcluir.setForeground(Color.WHITE);

		btnAprovar = new JButton("Aprovar");
		panel_22.add(btnAprovar, "cell 0 1,alignx center");
		btnAprovar.setBackground(new Color(0, 51, 51));
		btnAprovar.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnAprovar.setForeground(Color.WHITE);
		btnAprovar.setEnabled(false);
		btnAprovar.setVisible(false);
		btnAprovar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aprovar();
			}
		});
		botoes.add(btnAprovar);
		panel_22.add(btnExcluirContrato, "cell 1 1,alignx center");
		btnExcluirContrato.setBackground(new Color(0, 51, 51));
		btnExcluirContrato.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnExcluirContrato.setForeground(Color.WHITE);
		botoes.add(btnExcluirContrato);
		panel_22.add(btnEditarContrato, "cell 2 1,alignx center");
		btnEditarContrato.setBackground(new Color(0, 51, 51));
		btnEditarContrato.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnEditarContrato.setForeground(Color.WHITE);
		botoes.add(btnEditarContrato);
		
		 btnCancelarContrato = new JButton("Cancelar");
		btnCancelarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (contrato_local.getSub_contrato() != 1) {
					// verifique se os sub_contratos desde contratos estao finalizados
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					ArrayList<CadastroContrato> sub_contratos = gerenciar.getSubContratos(contrato_local.getId());

					boolean tem_contratos_nao_cancelados = false;

					for (CadastroContrato sub : sub_contratos) {
						if (sub.getStatus_contrato() != 4) {
							tem_contratos_nao_cancelados = true;
							break;
						}

					}

					if (!tem_contratos_nao_cancelados) {

						cancelar_contrato();

					} else {

						JOptionPane.showMessageDialog(isto,
								"Há sub-contratos deste contrato ainda em abertos!\nCancele-os também antes de continuar");

					}
				} else {
					cancelar_contrato();
				}
				
			}
		});
		btnCancelarContrato.setForeground(Color.WHITE);
		btnCancelarContrato.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnCancelarContrato.setBackground(new Color(102, 0, 0));
		panel_22.add(btnCancelarContrato, "flowx,cell 3 1");

		btnReabrir = new JButton("Desbloquear");
		panel_22.add(btnReabrir, "cell 3 1,alignx center");
		btnReabrir.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnReabrir.setForeground(Color.WHITE);

		btnRevogarAssinatura = new JButton("Revogar");
		panel_22.add(btnRevogarAssinatura, "cell 4 1,alignx center");
		btnRevogarAssinatura.setBackground(new Color(102, 153, 204));
		btnRevogarAssinatura.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnRevogarAssinatura.setForeground(Color.WHITE);

		JPanel panel_23 = new JPanel();
		panel_23.setBackground(Color.WHITE);
		panel_22.add(panel_23, "cell 0 2 5 1,grow");
		panel_23.setLayout(
				new MigLayout("", "[50px][grow]", "[][200px,grow,fill][][200px,grow,fill][][][][][][][][][]"));

		JLabel lblNewLabel_37_2_1 = new JLabel("Descrição:");
		panel_23.add(lblNewLabel_37_2_1, "cell 0 1,alignx right");
		lblNewLabel_37_2_1.setForeground(Color.BLACK);
		lblNewLabel_37_2_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		textAreaDescricao = new JTextArea();
		panel_23.add(textAreaDescricao, "cell 1 1,growx");
		textAreaDescricao.setFont(new Font("Arial", Font.BOLD, 16));
		textAreaDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaDescricao.setForeground(Color.BLACK);

		JLabel lblNewLabel_37_2_1_1 = new JLabel("Observações:");
		panel_23.add(lblNewLabel_37_2_1_1, "cell 0 3,alignx right");
		lblNewLabel_37_2_1_1.setForeground(Color.BLACK);
		lblNewLabel_37_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		textAreaObservacoes = new JTextArea();
		panel_23.add(textAreaObservacoes, "cell 1 3,growx");
		textAreaObservacoes.setFont(new Font("Arial", Font.BOLD, 16));
		textAreaObservacoes.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaObservacoes.setForeground(Color.BLACK);

		JLabel lblNewLabel_37_2_1_1_1 = new JLabel("Opção Relatoria:");
		lblNewLabel_37_2_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_37_2_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_23.add(lblNewLabel_37_2_1_1_1, "cell 0 4,alignx trailing");

		cbGrupoParticular = new JComboBox();
		cbGrupoParticular.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_23.add(cbGrupoParticular, "cell 1 4,growx");
		cbGrupoParticular.addItem("Grupo");
		cbGrupoParticular.addItem("Particular");

		JLabel lblNewLabel_37_2 = new JLabel("Fertilizante:");
		panel_23.add(lblNewLabel_37_2, "cell 0 5,alignx right");
		lblNewLabel_37_2.setForeground(Color.BLACK);
		lblNewLabel_37_2.setFont(new Font("SansSerif", Font.PLAIN, 14));

		entFertilizante = new JTextField();
		panel_23.add(entFertilizante, "cell 1 5,growx");
		entFertilizante.setBorder(new LineBorder(new Color(0, 0, 0)));
		entFertilizante.setForeground(Color.BLACK);
		entFertilizante.setFont(new Font("Arial", Font.BOLD, 16));
		entFertilizante.setColumns(10);

		JLabel lblNewLabel_37 = new JLabel("Bruto x Livre:");
		panel_23.add(lblNewLabel_37, "cell 0 6,alignx right");
		lblNewLabel_37.setForeground(Color.BLACK);
		lblNewLabel_37.setFont(new Font("SansSerif", Font.PLAIN, 14));

		cBBrutoLivre = new JComboBox();
		panel_23.add(cBBrutoLivre, "cell 1 6,growx");
		cBBrutoLivre.setForeground(Color.BLACK);
		cBBrutoLivre.setFont(new Font("SansSerif", Font.BOLD, 16));
		cBBrutoLivre.addItem("Bruto");
		cBBrutoLivre.addItem("Livre");

		JLabel lblNewLabel_37_1 = new JLabel("Penhor:");
		panel_23.add(lblNewLabel_37_1, "cell 0 7,alignx right");
		lblNewLabel_37_1.setForeground(Color.BLACK);
		lblNewLabel_37_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		statusPenhor = new JTextField();
		panel_23.add(statusPenhor, "cell 1 7,growx");
		statusPenhor.setBorder(new LineBorder(new Color(0, 0, 0)));
		statusPenhor.setForeground(Color.BLACK);
		statusPenhor.setFont(new Font("Arial", Font.BOLD, 16));
		statusPenhor.setColumns(10);

		JLabel lblNewLabel_37_1_1 = new JLabel("Opção Folha:");
		panel_23.add(lblNewLabel_37_1_1, "cell 0 8,alignx right");
		lblNewLabel_37_1_1.setForeground(Color.BLACK);
		lblNewLabel_37_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		cBOptante = new JComboBox();
		panel_23.add(cBOptante, "cell 1 8,growx");
		cBOptante.setForeground(Color.BLACK);
		cBOptante.setFont(new Font("SansSerif", Font.BOLD, 16));

		cBOptante.addItem("Não Optante");
		cBOptante.addItem("Optante");

		JLabel lblNewLabel_37_1_1_1 = new JLabel("Status:");
		panel_23.add(lblNewLabel_37_1_1_1, "cell 0 9,alignx right");
		lblNewLabel_37_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_37_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		textAreaStatusOpcaoFolha = new JTextArea();
		textAreaStatusOpcaoFolha.setFont(new Font("Arial", Font.BOLD, 16));
		textAreaStatusOpcaoFolha.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPane_1 = new JScrollPane(textAreaStatusOpcaoFolha);
		panel_23.add(scrollPane_1, "cell 1 9,growx");
		scrollPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.setFont(new Font("SansSerif", Font.BOLD, 12));

		JLabel lblNewLabel_37_1_1_1_1 = new JLabel("Localização:");
		panel_23.add(lblNewLabel_37_1_1_1_1, "cell 0 10,alignx right");
		lblNewLabel_37_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_37_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		entLocalizacao = new JTextField();
		panel_23.add(entLocalizacao, "cell 1 10,growx");
		entLocalizacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		entLocalizacao.setForeground(Color.BLACK);
		entLocalizacao.setFont(new Font("Arial", Font.BOLD, 16));
		entLocalizacao.setColumns(10);

		JButton btnAtualizarInfoAuxiliares = new JButton("Atualizar");
		btnAtualizarInfoAuxiliares.setBackground(new Color(0, 0, 153));
		btnAtualizarInfoAuxiliares.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_23.add(btnAtualizarInfoAuxiliares, "cell 1 11,alignx right");
		btnAtualizarInfoAuxiliares.setForeground(Color.WHITE);
		btnAtualizarInfoAuxiliares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				contrato_local.setDescricao(textAreaDescricao.getText());
				contrato_local.setObservacao(textAreaObservacoes.getText());
				contrato_local.setFertilizante(entFertilizante.getText());
				contrato_local.setGrupo_particular(cbGrupoParticular.getSelectedIndex());
				try {
					contrato_local.setBruto_livre(cBBrutoLivre.getSelectedItem().toString());
				} catch (Exception y) {

				}
				contrato_local.setStatus_penhor(statusPenhor.getText());
				contrato_local.setLocalizacao(entLocalizacao.getText());

				if (cBOptante.getSelectedIndex() == 0) {
					contrato_local.setOptante_folha(0);
					contrato_local.setStatus_optante_folha("");

				} else {
					contrato_local.setOptante_folha(1);
					contrato_local.setStatus_optante_folha(textAreaStatusOpcaoFolha.getText().toString());
				}

				GerenciarBancoContratos gerente = new GerenciarBancoContratos();
				boolean atualizou = gerente.atualizarInfoExtras(contrato_local);
				if (atualizou) {
					JOptionPane.showMessageDialog(isto, "Feito");
					GerenciarBancoInformativo gerenciar = new GerenciarBancoInformativo();
					CadastroInformativo informativo = new CadastroInformativo();
					informativo.setId_contrato(contrato_local.getId());
					informativo.setId_usuario(login.getId());
					informativo.setMensagem("contrato foi atualizado");
					informativo.setHora_completa(new GetData().getHora());

					boolean informou = gerenciar.inserirInformativo(informativo);

				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o administrador");

				}
			}
		});
		btnRevogarAssinatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revogarAssinatura();
			}
		});
		btnReabrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, "Deseja desbloquear o contrato?", "Desbloquear contrato",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if(contrato_local.getStatus_contrato() != 4) {
					
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					boolean atualizou = gerenciar.atualizarStatusContrato(contrato_local.getId(), 2);
					if (atualizou) {
						JOptionPane.showMessageDialog(isto, "Contrato Desbloqueado!");
						// retirar pontucao
						GerenciarBancoPontuacao gerenciar_pontuacao = new GerenciarBancoPontuacao();

						// primeiro verifica se existe uma pontuacao para esta combinacao contrato
						// cliente
						ArrayList<CadastroPontuacao> lista_pontuacao = gerenciar_pontuacao
								.getPontuacaoPorContrato(contrato_local.getId());
						if (lista_pontuacao.size() > 0) {
							for (CadastroPontuacao pontos_antigos : lista_pontuacao) {

								boolean removido = gerenciar_pontuacao
										.removerPontuacao(pontos_antigos.getId_pontuacao());
								if (removido) {
								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro ao remover pontuacao antiga\nConsulte o administrador!");
									break;
								}

							}
						}

						setarInformacoesPainelPrincipal();
						informar_atualizou();
						destravarContrato();

					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao desbloquear o contrato, tente novamente!\nSe o erro persistir, consulte o administrador");
					}

				} 
				else {
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					boolean atualizou = gerenciar.atualizarStatusContrato(contrato_local.getId(), 2);
					if (atualizou) {
						JOptionPane.showMessageDialog(isto, "Contrato Desbloqueado!");
						setarInformacoesPainelPrincipal();
						informar_atualizou();
						destravarContrato();
					}else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao desbloquear o contrato, tente novamente!\nSe o erro persistir, consulte o administrador");
					
					}
				}

			}
			}
		});
		btnEditarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// argumentos(CadastroModelo modelo, int tipoContrato, CadastroContrato
				// contrato_pai, int flag_edicao) {
				// TelaEscolhaTipoNovoContrato(int tipoContrato, CadastroContrato contrato_pai,
				// int flag_edicao) {

				fecharDocumento();
				DadosGlobais dados = DadosGlobais.getInstance();
				dados.setTeraGerenciarContratoPai(isto);
				if (contrato.getSub_contrato() == 0) {
					// e um contrato pai, abre a tela em modo de edicao
					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato(0, contrato_local, 1, isto);
				} else if (contrato.getSub_contrato() == 4 || contrato.getSub_contrato() == 5) {
					TelaImportarContratoManual tela = new TelaImportarContratoManual(contrato.getSub_contrato(),
							contrato, 1, null, isto);
					tela.setVisible(true);
				} else {
					// e um subcontrato, o tipo do contrato e 1, e entra no modo de edicao
					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato(1, contrato_local, 1, isto);

				}
			}
		});
		btnExcluirContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean permitir_exclusao = false;
				// Excluir o contrato
				// verificar se ha pagamentos contratuais relacionados a este contrato
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos = gerenciar
						.getPagamentosContratuais(contrato_local.getId());

				if (pagamentos.size() > 0) {
					permitir_exclusao = false;
					JOptionPane.showMessageDialog(isto,
							"Exclusão não permitida!\nHá pagamentos contratuais adicionados a este contrato\nExclua os antes de tentar a exclusão!");
				} else {
					permitir_exclusao = true;

					// verificar se ha carregamentos relacionados a este contrato
					ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar
							.getCarregamentos(contrato_local.getId());
					if (carregamentos.size() > 0) {
						permitir_exclusao = false;
						JOptionPane.showMessageDialog(isto,
								"Exclusão não permitida!\nHá carregamentos adicionados a este contrato\nExclua os antes de tentar a exclusão!");
					} else {
						permitir_exclusao = true;
						// verificar se ha subcontratos para este contrato

						ArrayList<CadastroContrato> sub_contratos = gerenciar.getSubContratos(contrato_local.getId());
						if (sub_contratos.size() > 0) {
							permitir_exclusao = false;
							JOptionPane.showMessageDialog(isto,
									"Exclusão não permitida!\nHá sub-contratos adicionados a este contrato\nExclua os antes de tentar a exclusão!");
						} else {
							permitir_exclusao = true;
							// JOptionPane.showMessageDialog(isto, "Exclusão permitida!");

							excluir_contrato();

						}

					}

				}

			}
		});
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluir_contrato();

			}
		});

		btnEnviarMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolha escolher = new TelaEscolha(1, contrato, null, isto);
			}
		});
		btnVizualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vizualizarContrato();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaVisaoGeralContrato tela = new TelaVisaoGeralContrato(contrato_local, isto);
				tela.setVisible(true);
			}
		});

		painelCarregamento.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Carregamento de Saída ", painelCarregamento);
		painelPrincipal.setIconAt(2, new ImageIcon(TelaGerenciarContrato.class.getResource("/imagens/caminhao.png")));
		painelPrincipal.setEnabledAt(2, true);
		painelCarregamento.setLayout(new BorderLayout(0, 0));

		// painelCarregamento.add(panel);

		// JPanel panelPaiCarga = new JPanel();
		// panelPaiCarga.setBackground(Color.WHITE);

		// JScrollPane scroll_aba_carregameno = new JScrollPane(panelPaiCarga);
		// painelCarregamento.add(scroll_aba_carregameno);

		// painelCarregamento.add(panelPaiCarga);
		/*
		 * GridBagLayout gbl_panelPaiCarga = new GridBagLayout();
		 * gbl_panelPaiCarga.columnWidths = new int[] { 1349, 0 };
		 * gbl_panelPaiCarga.rowHeights = new int[] { 876, 0 };
		 * gbl_panelPaiCarga.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		 * gbl_panelPaiCarga.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		 * panelPaiCarga.setLayout(gbl_panelPaiCarga);
		 */

		JPanel painel_pai_carregamentos = new JPanel();
		painel_pai_carregamentos.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		painel_pai_carregamentos
				.setLayout(new MigLayout("", "[187px][12px][86px,grow][4px][323px,grow][10px][538px,grow]",
						"[136px][4px][24px][199px,grow][42px][33px][8px][12px][170px]"));

		// painelCarregamento.add(painel_pai_carregamentos);
		JScrollPane scroll_pai_carregamentos = new JScrollPane(painel_pai_carregamentos);

		painelCarregamento.add(scroll_pai_carregamentos);
		JLabel lblNewLabel_7 = new JLabel("Carregamentos desse contrato:");
		lblNewLabel_7.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_7.setForeground(new Color(102, 51, 0));
		lblNewLabel_7.setBackground(new Color(102, 51, 0));
		painel_pai_carregamentos.add(lblNewLabel_7, "cell 0 2 3 1,alignx left,aligny top");

		table_carregamento = new JTable(modelo_carregamentos);
		CarregamentoCellRender renderer = new CarregamentoCellRender();
		table_carregamento.setDefaultRenderer(Object.class, renderer);
		table_carregamento.setBackground(Color.WHITE);

		// table_carregamento.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table_carregamento.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_carregamento.getColumnModel().getColumn(1).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(2).setPreferredWidth(90);
		table_carregamento.getColumnModel().getColumn(3).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(4).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(5).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(6).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(7).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(8).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(9).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(10).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(11).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(12).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(13).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(14).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(15).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(16).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(17).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(18).setPreferredWidth(150);
		table_carregamento.getColumnModel().getColumn(19).setPreferredWidth(400);

		table_carregamento.setRowHeight(30);

		table_carregamento.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuTabelCarregamento.show(table_carregamento, e.getX(), e.getY());
				}
			}
		});

		scrollPaneCarregamento = new JScrollPane(table_carregamento);
		painel_pai_carregamentos.add(scrollPaneCarregamento, "cell 0 3 7 1,grow");
		scrollPaneCarregamento.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCarregamento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCarregamento.setBackground(Color.WHITE);
		scrollPaneCarregamento.setAutoscrolls(true);

		painelGraficoCarregamento = new JPanelGraficoPadrao(0, 0, "Carregado:", "a Carregar:");
		painel_pai_carregamentos.add(painelGraficoCarregamento, "cell 4 4 1 5,grow");
		painelGraficoCarregamento.setLayout(null);

		JPanel panel_14 = new JPanel();
		panel_14.setBackground(Color.WHITE);
		painel_pai_carregamentos.add(panel_14, "cell 0 4 3 3,alignx center,aligny center");
		panel_14.setLayout(new MigLayout("", "[11px][83px]", "[18px][][]"));

		JLabel lblNewLabel_33_1_3 = new JLabel("       ");
		panel_14.add(lblNewLabel_33_1_3, "cell 0 0,alignx center,aligny center");
		lblNewLabel_33_1_3.setOpaque(true);
		lblNewLabel_33_1_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_3.setBackground(Color.ORANGE);

		JLabel lblNewLabel_34_1_3 = new JLabel("Falta NF Venda");
		panel_14.add(lblNewLabel_34_1_3, "cell 1 0,alignx center,aligny center");
		lblNewLabel_34_1_3.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_1_3.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_33_1 = new JLabel("       ");
		panel_14.add(lblNewLabel_33_1, "cell 4 0,alignx center,aligny center");
		lblNewLabel_33_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1.setOpaque(true);
		lblNewLabel_33_1.setBackground(Color.yellow);

		JLabel lblNewLabel_34_1 = new JLabel("Falta NF Complemento");
		panel_14.add(lblNewLabel_34_1, "cell 5 0,alignx center,aligny center");
		lblNewLabel_34_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel_34_1.setFont(new Font("SansSerif", Font.BOLD, 12));

		JLabel lblNewLabel_33_1_4 = new JLabel("       ");
		panel_14.add(lblNewLabel_33_1_4, "cell 0 1,alignx center,aligny center");
		lblNewLabel_33_1_4.setOpaque(true);
		lblNewLabel_33_1_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_4.setBackground(new Color(204, 204, 102));

		JLabel lblNewLabel_34_1_4 = new JLabel("Falta NF Interna");
		panel_14.add(lblNewLabel_34_1_4, "cell 1 1,alignx center,aligny center");
		lblNewLabel_34_1_4.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_1_4.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_33_1_2 = new JLabel("       ");
		panel_14.add(lblNewLabel_33_1_2, "cell 4 1,alignx center,aligny center");
		lblNewLabel_33_1_2.setOpaque(true);
		lblNewLabel_33_1_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33_1_2.setBackground(new Color(204, 153, 0));

		JLabel lblNewLabel_34_1_2 = new JLabel("Transferido");
		panel_14.add(lblNewLabel_34_1_2, "cell 5 1,alignx left,aligny center");
		lblNewLabel_34_1_2.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_34_1_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_33 = new JLabel("       ");
		panel_14.add(lblNewLabel_33, "cell 0 2,alignx center,aligny center");
		lblNewLabel_33.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_33.setOpaque(true);
		lblNewLabel_33.setBackground(new Color(0, 100, 0));

		JLabel lblNewLabel_34 = new JLabel("Concluido");
		panel_14.add(lblNewLabel_34, "cell 1 2,alignx center,aligny center");
		lblNewLabel_34.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel_34.setFont(new Font("SansSerif", Font.BOLD, 12));

		JPanel panel_15 = new JPanel();
		panel_15.setBackground(Color.WHITE);
		painel_pai_carregamentos.add(panel_15, "cell 0 8 3 1,alignx center,aligny center");
		GridBagLayout gbl_panel_15 = new GridBagLayout();
		gbl_panel_15.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_15.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_15.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_15.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_15.setLayout(gbl_panel_15);

		JLabel lblNewLabel_3 = new JLabel("Cargas:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		panel_15.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel lblNewLabel_12 = new JLabel("Total Recebido:");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12.gridx = 0;
		gbc_lblNewLabel_12.gridy = 1;
		panel_15.add(lblNewLabel_12, gbc_lblNewLabel_12);

		lblPesoTotal = new JLabel("0.0 KG");
		GridBagConstraints gbc_lblPesoTotal = new GridBagConstraints();
		gbc_lblPesoTotal.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoTotal.gridx = 1;
		gbc_lblPesoTotal.gridy = 1;
		panel_15.add(lblPesoTotal, gbc_lblPesoTotal);
		lblPesoTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotal.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_14_1_1_2 = new JLabel("Transferencias:(-)");
		GridBagConstraints gbc_lblNewLabel_14_1_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_14_1_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_14_1_1_2.gridx = 0;
		gbc_lblNewLabel_14_1_1_2.gridy = 2;
		panel_15.add(lblNewLabel_14_1_1_2, gbc_lblNewLabel_14_1_1_2);

		lblTotalTransferenciasCargaRetiradas = new JLabel("0.0 KG");
		GridBagConstraints gbc_lblTotalTransferenciasCargaRetiradas = new GridBagConstraints();
		gbc_lblTotalTransferenciasCargaRetiradas.insets = new Insets(0, 0, 5, 0);
		gbc_lblTotalTransferenciasCargaRetiradas.gridx = 1;
		gbc_lblTotalTransferenciasCargaRetiradas.gridy = 2;
		panel_15.add(lblTotalTransferenciasCargaRetiradas, gbc_lblTotalTransferenciasCargaRetiradas);
		lblTotalTransferenciasCargaRetiradas.setForeground(Color.BLACK);
		lblTotalTransferenciasCargaRetiradas.setFont(new Font("Arial", Font.BOLD, 12));
		lblTotalTransferenciasCargaRetiradas.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_14_1_1_1_1 = new JLabel("Transferencias:(+)");
		GridBagConstraints gbc_lblNewLabel_14_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_14_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_14_1_1_1_1.gridx = 0;
		gbc_lblNewLabel_14_1_1_1_1.gridy = 3;
		panel_15.add(lblNewLabel_14_1_1_1_1, gbc_lblNewLabel_14_1_1_1_1);

		lblTotalTransferenciasCargaRecebidas = new JLabel("0.0 KG");
		GridBagConstraints gbc_lblTotalTransferenciasCargaRecebidas = new GridBagConstraints();
		gbc_lblTotalTransferenciasCargaRecebidas.insets = new Insets(0, 0, 5, 0);
		gbc_lblTotalTransferenciasCargaRecebidas.gridx = 1;
		gbc_lblTotalTransferenciasCargaRecebidas.gridy = 3;
		panel_15.add(lblTotalTransferenciasCargaRecebidas, gbc_lblTotalTransferenciasCargaRecebidas);
		lblTotalTransferenciasCargaRecebidas.setForeground(Color.BLACK);
		lblTotalTransferenciasCargaRecebidas.setFont(new Font("Arial", Font.BOLD, 12));
		lblTotalTransferenciasCargaRecebidas.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_13 = new JLabel("Total Carregado:");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 0;
		gbc_lblNewLabel_13.gridy = 4;
		panel_15.add(lblNewLabel_13, gbc_lblNewLabel_13);

		lblPesoTotalRealCargas = new JLabel("");
		GridBagConstraints gbc_lblPesoTotalRealCargas = new GridBagConstraints();
		gbc_lblPesoTotalRealCargas.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoTotalRealCargas.gridx = 1;
		gbc_lblPesoTotalRealCargas.gridy = 4;
		panel_15.add(lblPesoTotalRealCargas, gbc_lblPesoTotalRealCargas);
		lblPesoTotalRealCargas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealCargas.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_13_1 = new JLabel("Restante:");
		GridBagConstraints gbc_lblNewLabel_13_1 = new GridBagConstraints();
		gbc_lblNewLabel_13_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_13_1.gridx = 0;
		gbc_lblNewLabel_13_1.gridy = 5;
		panel_15.add(lblNewLabel_13_1, gbc_lblNewLabel_13_1);

		lblPesoTotalRealRestante = new JLabel("0.0 Kg");
		GridBagConstraints gbc_lblPesoTotalRealRestante = new GridBagConstraints();
		gbc_lblPesoTotalRealRestante.gridx = 1;
		gbc_lblPesoTotalRealRestante.gridy = 5;
		panel_15.add(lblPesoTotalRealRestante, gbc_lblPesoTotalRealRestante);
		lblPesoTotalRealRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealRestante.setBorder(new LineBorder(new Color(0, 0, 0)));

		JPanel panel_16 = new JPanel();
		panel_16.setBackground(Color.WHITE);
		painel_pai_carregamentos.add(panel_16, "cell 6 4,alignx right,aligny top");
		panel_16.setLayout(new MigLayout("", "[73px][81px][60px][64px][79px]", "[28px]"));

		JButton btnExportarCarregamentos = new JButton("Exportar");
		panel_16.add(btnExportarCarregamentos, "cell 0 0,alignx center,aligny center");
		btnExportarCarregamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Carregamentos");

				// Definindo alguns padroes de layout
				sheet.setDefaultColumnWidth(20);
				sheet.setDefaultRowHeight((short) 400);

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

				// celula para texto alinhado ao centro
				CellStyle textStyle = workbook.createCellStyle();
				textStyle.setAlignment(HorizontalAlignment.CENTER);
				textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				HSSFFont newFontforText = workbook.createFont();
				newFontforText.setBold(false);
				newFontforText.setColor(IndexedColors.BLACK.getIndex());
				newFontforText.setFontName("Calibri");
				newFontforText.setItalic(false);
				newFontforText.setFontHeight((short) (11 * 20));
				textStyle.setFont(newFontforText);

				// celula para texto alinhado ao esquerda
				CellStyle textStyleAlinhadoEsquerda = workbook.createCellStyle();
				textStyleAlinhadoEsquerda.setAlignment(HorizontalAlignment.LEFT);
				HSSFFont newFontforTextAlinhaEsquerda = workbook.createFont();
				newFontforTextAlinhaEsquerda.setBold(false);
				newFontforTextAlinhaEsquerda.setColor(IndexedColors.BLACK.getIndex());
				newFontforTextAlinhaEsquerda.setFontName("Calibri");
				newFontforTextAlinhaEsquerda.setItalic(false);
				newFontforTextAlinhaEsquerda.setFontHeight((short) (11 * 20));
				textStyleAlinhadoEsquerda.setFont(newFontforTextAlinhaEsquerda);

				// celula de aviso
				CellStyle textStyleAlinhadoEsquerdaAviso = workbook.createCellStyle();
				textStyleAlinhadoEsquerdaAviso.setAlignment(HorizontalAlignment.LEFT);
				HSSFFont newFontforTextAlinhaEsquerdaAviso = workbook.createFont();
				newFontforTextAlinhaEsquerdaAviso.setBold(false);
				newFontforTextAlinhaEsquerdaAviso.setColor(IndexedColors.RED.getIndex());
				newFontforTextAlinhaEsquerdaAviso.setFontName("Calibri");
				newFontforTextAlinhaEsquerdaAviso.setItalic(false);
				newFontforTextAlinhaEsquerdaAviso.setFontHeight((short) (11 * 20));
				textStyleAlinhadoEsquerdaAviso.setFont(newFontforTextAlinhaEsquerdaAviso);

				// celula de aviso negrito
				CellStyle textStyleAlinhadoEsquerdaNegrito = workbook.createCellStyle();
				textStyleAlinhadoEsquerdaNegrito.setAlignment(HorizontalAlignment.LEFT);
				HSSFFont newFontforTextAlinhaEsquerdaNegrito = workbook.createFont();
				newFontforTextAlinhaEsquerdaNegrito.setBold(true);
				newFontforTextAlinhaEsquerdaNegrito.setColor(IndexedColors.BLACK.getIndex());
				newFontforTextAlinhaEsquerdaNegrito.setFontName("Calibri");
				newFontforTextAlinhaEsquerdaNegrito.setItalic(false);
				newFontforTextAlinhaEsquerdaNegrito.setFontHeight((short) (11 * 20));
				textStyleAlinhadoEsquerdaNegrito.setFont(newFontforTextAlinhaEsquerdaNegrito);

				// celula para numero alinhado ao centro
				CellStyle numberStyle = workbook.createCellStyle();
				numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
				numberStyle.setAlignment(HorizontalAlignment.CENTER);
				numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// estilo para celula do tipo numero alinhado ao centro
				CellStyle valorStyle = workbook.createCellStyle();
				valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
				valorStyle.setAlignment(HorizontalAlignment.CENTER);
				valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// estilo para cabecalho fundo laranja
				CellStyle celula_fundo_laranja = workbook.createCellStyle();
				celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_laranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
				celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont = workbook.createFont();
				newFont.setBold(true);
				newFont.setColor(IndexedColors.BLACK.getIndex());
				newFont.setFontName("Calibri");
				newFont.setItalic(false);
				newFont.setFontHeight((short) (11 * 20));

				celula_fundo_laranja.setFont(newFont);

				// celula_number_amarelo_texto_preto
				// estilo para cabecalho fundo laranja
				CellStyle celula_number_amarelo_texto_preto = workbook.createCellStyle();
				celula_number_amarelo_texto_preto.setDataFormat(numberFormat.getFormat("#,##0.00"));
				celula_number_amarelo_texto_preto.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_number_amarelo_texto_preto.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				celula_number_amarelo_texto_preto.setAlignment(HorizontalAlignment.CENTER);
				celula_number_amarelo_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont_blabk = workbook.createFont();
				newFont_blabk.setBold(true);
				newFont_blabk.setColor(IndexedColors.BLACK.getIndex());
				newFont_blabk.setFontName("Calibri");
				newFont_blabk.setItalic(false);
				newFont_blabk.setFontHeight((short) (11 * 20));

				celula_number_amarelo_texto_preto.setFont(newFont_blabk);
				numberStyle.setFont(newFont_blabk);

				// estilo para cabecalho fundo laranja
				CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
				celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
				celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont_branca = workbook.createFont();
				newFont_branca.setBold(true);
				newFont_branca.setColor(IndexedColors.WHITE.getIndex());
				newFont_branca.setFontName("Calibri");
				newFont_branca.setItalic(false);
				newFont_branca.setFontHeight((short) (11 * 20));

				celula_fundo_laranja_texto_branco.setFont(newFont_branca);

				// estilo para cabecalho fundo verde
				CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
				celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

				celula_fundo_verde_texto_branco.setFont(newFont_branca);

				// Configurando as informacoes
				row = sheet.createRow(rownum++);

				CadastroCliente compradores[] = contrato_local.getCompradores();
				CadastroCliente vendedores[] = contrato_local.getVendedores();

				String nome_compradores;
				String nome_vendedores;
				if (compradores[0].getTipo_pessoa() == 0) {
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();
				}

				if (compradores[1] != null) {
					if (compradores[1].getTipo_pessoa() == 0) {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
					} else {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();
					}
				}

				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedores = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedores = vendedores[0].getNome_fantaia();
				}

				if (vendedores[1] != null) {
					if (vendedores[1].getTipo_pessoa() == 0) {
						nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
					} else {
						nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
					}
				}

				cell = row.createCell(cellnum);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
						+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
				sheet.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 5));

				// linha quantidade, safra, sacos, etc
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);

				double quantidade_kg = 0;
				double quantidade_sacos = 0;
				Locale ptBr = new Locale("pt", "BR");

				if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = quantidade_sacos * 60;
				}

				cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
						+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto()) + " por "
						+ contrato.getMedida() + " no valor total de: "
						+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar()));
				sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

				row = sheet.createRow(rownum++);
				cellnum = 0;
				GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

				double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(contrato_local.getId());
				quantidade_kg = quantidade_kgs_recebidos;
				quantidade_sacos = quantidade_kg / 60;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue(contrato.getModelo_safra().getProduto().getNome_produto() + " "
						+ contrato.getModelo_safra().getProduto().getTransgenia() + " "
						+ contrato.getModelo_safra().getAno_plantio() + "/"
						+ contrato.getModelo_safra().getAno_colheita());
				sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

				// Configurando Header
				row = sheet.createRow(rownum++);
				cellnum = 0;
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("DATA");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("CLIENTE".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("MOTORISTA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("PLACA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("ROMANEIO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("PESO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("NF INTERNA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("PESO NF INTERNA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("NF VENDA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("PESO NF VENDA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("VALOR NF VENDA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("DIFERENÇA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("NF COMPLEMENTO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("PESO NF COMPLEMENTO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("VALOR NF COMPLEMENTO".toUpperCase());

				ArrayList<CadastroContrato.Carregamento> contratos_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = table_carregamento.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//

					String descricao = (String) table_carregamento.getValueAt(indice, 1);

					if (descricao != null && descricao.equalsIgnoreCase("-Transferencia")
							|| descricao.equalsIgnoreCase("+Transferencia")) {
						if (descricao.equalsIgnoreCase("-Transferencia")) {
							CadastroContrato.Carregamento carregamento_selecionado = lista_carregamentos.get(indice);
							contratos_selecionados.add(carregamento_selecionado);
						} else if (descricao.equalsIgnoreCase("+Transferencia")) {
							CadastroContrato.Carregamento carregamento_selecionado = lista_carregamentos.get(indice);
							contratos_selecionados.add(carregamento_selecionado);
						}

					} else {

						CadastroContrato.Carregamento carregamento_selecionado = lista_carregamentos.get(indice);
						contratos_selecionados.add(carregamento_selecionado);
					}

				}

				double quantidade_total_kgs_carregados = 0;
				double quantidade_total_kgs_nf_venda1 = 0;
				double quantidade_total_kgs_nf_complemento = 0;
				double quantidade_total_kgs_diferenca = 0;
				double quantidade_total_kgs_nf_interna = 0;
				BigDecimal valor_total_nf_venda1 = BigDecimal.ZERO;
				BigDecimal valor_total_nf_complemento = BigDecimal.ZERO;

				boolean nf_interna_ativo = false;
				boolean nf_venda_ativo = false;
				boolean nf_complemento_ativo = false;

				// checka se ha no minimo uma nf interna aplicavel
				for (CadastroContrato.Carregamento carregamento : contratos_selecionados) {
					if (carregamento.getNf_interna_aplicavel() == 1) {
						nf_interna_ativo = true;
						break;
					}

				}

				// checka se ha no minimo uma nf venda aplicavel
				for (CadastroContrato.Carregamento carregamento : contratos_selecionados) {
					if (carregamento.getNf_venda1_aplicavel() == 1) {
						nf_venda_ativo = true;
						break;
					}

				}

				// checka se ha no minimo uma nf complemento aplicavel
				for (CadastroContrato.Carregamento carregamento : contratos_selecionados) {
					if (carregamento.getNf_complemento_aplicavel() == 1) {
						nf_complemento_ativo = true;
						break;
					}

				}

				for (CadastroContrato.Carregamento carregamento : contratos_selecionados) {
					if (carregamento.getDescricao() == null) {

						GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
						// pegar dados do contrato
						CadastroContrato contrato_destinatario = gerenciar.getContrato(carregamento.getId_contrato());

						// pegar cliente
						GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
						CadastroCliente cliente_carregamento = gerenciar_clientes
								.getCliente(carregamento.getId_cliente());
						String nome_cliente;
						if (cliente_carregamento.getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_cliente = cliente_carregamento.getNome_empresarial();
						} else {
							nome_cliente = cliente_carregamento.getNome_fantaia();
						}

						String nome_cliente_completo = nome_cliente;

						String nome_cliente_quebrado[] = nome_cliente.split(" ");
						try {

							if (nome_cliente_quebrado.length > 2) {
								if (nome_cliente_quebrado[2].length() > 1) {
									nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[2];
								} else {
									if (nome_cliente_quebrado[3].length() > 1) {
										nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[3];

									} else {
										nome_cliente = nome_cliente_quebrado[0] + " " + nome_cliente_quebrado[1];

									}
								}
							}

						} catch (Exception v) {
							nome_cliente = nome_cliente_completo;
						}

						// pegar vendedor
						CadastroCliente vendedor_carregamento = gerenciar_clientes
								.getCliente(carregamento.getId_vendedor());
						String nome_vendedor;
						if (vendedor_carregamento.getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_vendedor = vendedor_carregamento.getNome_empresarial();
						} else {
							nome_vendedor = vendedor_carregamento.getNome_fantaia();
						}

						String nome_vendedor_completo = nome_vendedor;

						String nome_vendedor_quebrado[] = nome_vendedor.split(" ");
						try {

							if (nome_vendedor_quebrado.length > 2) {
								if (nome_vendedor_quebrado[2].length() > 1) {
									nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[2];
								} else {
									if (nome_vendedor_quebrado[3].length() > 1) {
										nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[3];

									} else {
										nome_vendedor = nome_vendedor_quebrado[0] + " " + nome_vendedor_quebrado[1];

									}
								}
							}

						} catch (Exception v) {
							nome_vendedor = nome_vendedor_completo;
						}

						String nome_transportador = "";
						String placa_trator = "";
						if (carregamento.getId_transportador() > 0) {
							// pegar transportador e veiculo
							CadastroCliente transportador_carregamento = gerenciar_clientes
									.getCliente(carregamento.getId_transportador());

							if (transportador_carregamento != null) {
								if (transportador_carregamento.getTipo_pessoa() == 0) {
									nome_transportador = transportador_carregamento.getNome_empresarial();
								} else {
									nome_transportador = transportador_carregamento.getNome_fantaia();

								}
							}
							CadastroCliente.Veiculo veiculo_carregamento = null;
							if (carregamento.getId_veiculo() > 0) {
								for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
									if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
										veiculo_carregamento = veiculo;
										break;
									}
								}

								if (veiculo_carregamento != null) {
									placa_trator = veiculo_carregamento.getPlaca_trator();
								}
							}

						}
						// pegar o produto
						GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
						CadastroProduto produto_carregamento = gerenciar_produtos
								.getProduto(carregamento.getId_produto());

						// codigos
						String codigo_romaneio = "";
						String codigo_nf_venda1 = "", codigo_nf_complemento = "", codigo_nf_interna = "";
						// pesos

						double peso_romaneio = 0.0;
						double peso_nf_venda1 = 0.0;
						double peso_nf_interna = 0.0;

						BigDecimal valor_nf_venda1 = BigDecimal.ZERO;
						double peso_nf_complemento = 0.0;
						BigDecimal valor_nf_complemento = BigDecimal.ZERO;

						try {
							if (checkString(carregamento.getCodigo_romaneio())) {
								// procurar por romaneio
								if (checkString(carregamento.getCaminho_romaneio())) {
									ManipularRomaneios manipular = new ManipularRomaneios("");

									CadastroRomaneio romaneio = manipular
											.filtrar(new File(servidor_unidade + carregamento.getCaminho_romaneio()));
									codigo_romaneio = Integer.toString(romaneio.getNumero_romaneio());
									peso_romaneio = romaneio.getPeso_liquido();

								} else {
									codigo_romaneio = carregamento.getCodigo_romaneio();
									peso_romaneio = carregamento.getPeso_romaneio();
								}

							}
						} catch (Exception t) {
							// JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
							codigo_romaneio = carregamento.getCodigo_romaneio();
							peso_romaneio = carregamento.getPeso_romaneio();
						}

						// nf venda 1
						try {
							if (checkString(carregamento.getCodigo_nf_venda1())) {
								if (carregamento.getCaminho_nf_venda1().length() > 10) {
									// procurar por nf venda
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_venda = manipular
											.filtrar(new File(servidor_unidade + carregamento.getCodigo_nf_venda1()));
									codigo_nf_venda1 = nota_fiscal_venda.getNfe();
									peso_nf_venda1 = Double.parseDouble(nota_fiscal_venda.getQuantidade());
									try {
										valor_nf_venda1 = new BigDecimal(nota_fiscal_venda.getValor());
									} catch (Exception u) {
										valor_nf_venda1 = BigDecimal.ZERO;
									}

								} else {
									codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
									peso_nf_venda1 = carregamento.getPeso_nf_venda1();
									valor_nf_venda1 = carregamento.getValor_nf_venda1();

								}

							}
						} catch (Exception g) {
							// JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
							codigo_nf_venda1 = carregamento.getCodigo_nf_venda1();
							peso_nf_venda1 = carregamento.getPeso_nf_venda1();
							valor_nf_venda1 = carregamento.getValor_nf_venda1();

						}

						// nf complemento
						try {
							if (checkString(carregamento.getCodigo_nf_complemento())) {
								if (carregamento.getCaminho_nf_complemento().length() > 10) {
									// procurar por nf remessa
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_complemento = manipular.filtrar(
											new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
									codigo_nf_complemento = nota_fiscal_complemento.getNfe();
									peso_nf_complemento = Double.parseDouble(nota_fiscal_complemento.getQuantidade());
									try {
										valor_nf_complemento = new BigDecimal(nota_fiscal_complemento.getValor());
									} catch (Exception l) {
										valor_nf_complemento = BigDecimal.ZERO;
									}

								} else {
									codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
									peso_nf_complemento = carregamento.getPeso_nf_complemento();
									valor_nf_complemento = carregamento.getValor_nf_complemento();

								}

							}
						} catch (Exception y) {
							// JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

							codigo_nf_complemento = carregamento.getCodigo_nf_complemento();
							peso_nf_complemento = carregamento.getPeso_nf_complemento();
							valor_nf_complemento = carregamento.getValor_nf_complemento();

						}

						// nf interna
						try {
							if (checkString(carregamento.getCodigo_nf_interna())) {
								if (carregamento.getCaminho_nf_interna().length() > 10) {
									// procurar por nf remessa
									ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
									CadastroNFe nota_fiscal_interna = manipular.filtrar(
											new File(servidor_unidade + carregamento.getCaminho_nf_complemento()));
									codigo_nf_interna = nota_fiscal_interna.getNfe();
									peso_nf_interna = Double.parseDouble(nota_fiscal_interna.getQuantidade());

								} else {
									codigo_nf_interna = carregamento.getCodigo_nf_interna();
									peso_nf_interna = carregamento.getPeso_nf_interna();

								}

							}
						} catch (Exception y) {
							// JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");

							codigo_nf_interna = carregamento.getCodigo_nf_interna();
							peso_nf_interna = carregamento.getPeso_nf_interna();

						}

						row = sheet.createRow(rownum++);
						cellnum = 0;
						/*
						 * codigo compradores vendedores status quantidade medida produto transgenia
						 * safra valor_produto valor_total data_contrato local_retirada
						 */
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(carregamento.getData());

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(nome_cliente);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(nome_transportador);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(placa_trator);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(codigo_romaneio);

						cell = row.createCell(cellnum++);
						cell.setCellStyle(numberStyle);
						cell.setCellValue(peso_romaneio);

						if (carregamento.getNf_interna_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue(codigo_nf_interna);
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_interna_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(numberStyle);
							cell.setCellValue(peso_nf_interna);
							quantidade_total_kgs_nf_interna += peso_nf_interna;
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_venda1_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue(codigo_nf_venda1);
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_venda1_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(numberStyle);
							cell.setCellValue(peso_nf_venda1);
							quantidade_total_kgs_nf_venda1 += peso_nf_venda1;

						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_venda1_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(valorStyle);
							cell.setCellValue(valor_nf_venda1.doubleValue());
							valor_total_nf_venda1 = valor_total_nf_venda1.add(valor_nf_venda1);

						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_venda1_aplicavel() == 1
								&& carregamento.getNf_complemento_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(numberStyle);
							cell.setCellValue(peso_romaneio - (peso_nf_venda1 + peso_nf_complemento));
						} else if (carregamento.getNf_venda1_aplicavel() == 1
								&& carregamento.getNf_complemento_aplicavel() == 0) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(numberStyle);
							cell.setCellValue(peso_romaneio - (peso_nf_venda1));
						} else if (carregamento.getNf_venda1_aplicavel() == 0
								&& carregamento.getNf_complemento_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(numberStyle);
							cell.setCellValue(peso_romaneio - (peso_nf_complemento));
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_complemento_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue(codigo_nf_complemento);
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_complemento_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(numberStyle);
							cell.setCellValue(peso_nf_complemento);
							quantidade_total_kgs_nf_complemento += peso_nf_complemento;
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						if (carregamento.getNf_complemento_aplicavel() == 1) {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(valorStyle);
							cell.setCellValue(valor_nf_complemento.doubleValue());
							valor_total_nf_complemento = valor_total_nf_complemento.add(valor_nf_complemento);
						} else {
							cell = row.createCell(cellnum++);
							cell.setCellStyle(textStyle);
							cell.setCellValue("Não Aplicável");
						}

						quantidade_total_kgs_carregados += peso_romaneio;
						if (carregamento.getNf_venda1_aplicavel() == 1
								&& carregamento.getNf_complemento_aplicavel() == 1) {
							quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_venda1 + peso_nf_complemento));
						} else if (carregamento.getNf_venda1_aplicavel() == 1
								&& carregamento.getNf_complemento_aplicavel() == 0) {
							quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_venda1));

						} else if (carregamento.getNf_venda1_aplicavel() == 0
								&& carregamento.getNf_complemento_aplicavel() == 1) {
							quantidade_total_kgs_diferenca += (peso_romaneio - (peso_nf_complemento));

						}

					} // fim de configurar carregamento normal
					else if (carregamento.getDescricao().equals("-Transferencia")) {
						row = sheet.createRow(rownum++);
						cellnum = 0;
						/*
						 * codigo compradores vendedores status quantidade medida produto transgenia
						 * safra valor_produto valor_total data_contrato local_retirada
						 */
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(carregamento.getData());

						String texto_detalhado = "";

						GerenciarBancoTransferenciasCarga gerenciar = new GerenciarBancoTransferenciasCarga();
						CadastroContrato.CadastroTransferenciaCarga transferencia = gerenciar
								.getTransferencia(carregamento.getId_carregamento());

						GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
						CadastroContrato remetente = gerencia_contratos
								.getContrato(transferencia.getId_contrato_remetente());
						CadastroContrato destinatario = gerencia_contratos
								.getContrato(transferencia.getId_contrato_destinatario());
						CadastroContrato.Carregamento carga = gerencia_contratos
								.getCarregamento(transferencia.getId_carregamento_remetente());

						CadastroCliente compradores_trans[] = destinatario.getCompradores();
						CadastroCliente vendedores_trans[] = destinatario.getVendedores();

						nome_vendedores = "";
						nome_compradores = "";

						if (compradores_trans[0] != null) {
							if (compradores_trans[0].getTipo_pessoa() == 0) {
								// pessoa fisica
								nome_compradores = compradores_trans[0].getNome_empresarial();
							} else {
								nome_compradores = compradores_trans[0].getNome_fantaia();

							}
						}

						if (compradores_trans[1] != null) {
							if (compradores_trans[1].getTipo_pessoa() == 0) {
								// pessoa fisica
								nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_empresarial();
							} else {
								nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_fantaia();

							}
						}

						for (CadastroCliente vendedor : vendedores_trans) {
							if (vendedor != null) {
								if (vendedor.getTipo_pessoa() == 0) {
									// pessoa fisica
									nome_vendedores += vendedor.getNome_empresarial();
								} else {
									nome_vendedores += vendedor.getNome_fantaia();

								}
								nome_vendedores += " ,";

							}
						}

						double quantidade = Double.parseDouble(transferencia.getQuantidade());

						NumberFormat z = NumberFormat.getNumberInstance();

						texto_detalhado = "Transferência Negativa: Transferência do volume de " + z.format(quantidade)
								+ " kgs | " + z.format(quantidade / 60) + " sacos deste contrato para o contrato ";
						texto_detalhado = texto_detalhado + destinatario.getCodigo() + "\n" + nome_compradores + " X "
								+ nome_vendedores + " " + z.format(destinatario.getQuantidade()) + " "
								+ destinatario.getMedida() + " de "
								+ destinatario.getModelo_safra().getProduto().getNome_produto() + " "
								+ destinatario.getModelo_safra().getProduto().getTransgenia() + " da safra "
								+ destinatario.getModelo_safra().getAno_plantio() + "/"
								+ destinatario.getModelo_safra().getAno_colheita();
						texto_detalhado = texto_detalhado + "";
						sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, 16));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyleAlinhadoEsquerda);

						cell.setCellValue(texto_detalhado);

						quantidade_total_kgs_carregados -= quantidade;

					} else if (carregamento.getDescricao().equals("+Transferencia")) {
						row = sheet.createRow(rownum++);
						cellnum = 0;
						/*
						 * codigo compradores vendedores status quantidade medida produto transgenia
						 * safra valor_produto valor_total data_contrato local_retirada
						 */
						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyle);
						cell.setCellValue(carregamento.getData());

						String texto_detalhado = "";

						GerenciarBancoTransferenciasCarga gerenciar = new GerenciarBancoTransferenciasCarga();
						CadastroContrato.CadastroTransferenciaCarga transferencia = gerenciar
								.getTransferencia(carregamento.getId_carregamento());

						GerenciarBancoContratos gerencia_contratos = new GerenciarBancoContratos();
						CadastroContrato remetente = gerencia_contratos
								.getContrato(transferencia.getId_contrato_remetente());
						CadastroContrato destinatario = gerencia_contratos
								.getContrato(transferencia.getId_contrato_destinatario());
						CadastroContrato.Carregamento carga = gerencia_contratos
								.getCarregamento(transferencia.getId_carregamento_remetente());

						CadastroCliente compradores_trans[] = remetente.getCompradores();
						CadastroCliente vendedores_trans[] = remetente.getVendedores();

						nome_vendedores = "";
						nome_compradores = "";

						if (compradores_trans[0] != null) {
							if (compradores_trans[0].getTipo_pessoa() == 0) {
								// pessoa fisica
								nome_compradores = compradores_trans[0].getNome_empresarial();
							} else {
								nome_compradores = compradores_trans[0].getNome_fantaia();

							}
						}

						if (compradores_trans[1] != null) {
							if (compradores[1].getTipo_pessoa() == 0) {
								// pessoa fisica
								nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_empresarial();
							} else {
								nome_compradores = nome_compradores + ", " + compradores_trans[1].getNome_fantaia();

							}
						}

						for (CadastroCliente vendedor : vendedores_trans) {
							if (vendedor != null) {
								if (vendedor.getTipo_pessoa() == 0) {
									// pessoa fisica
									nome_vendedores += vendedor.getNome_empresarial();
								} else {
									nome_vendedores += vendedor.getNome_fantaia();

								}
								nome_vendedores += ",";

							}
						}

						double quantidade = Double.parseDouble(transferencia.getQuantidade());

						NumberFormat z = NumberFormat.getNumberInstance();

						texto_detalhado = "Transferência Positiva: Recebimento de volume de " + z.format(quantidade)
								+ " kgs | " + z.format(quantidade / 60) + " sacos recebidos do contrato ";
						texto_detalhado = texto_detalhado + remetente.getCodigo() + " " + nome_compradores + " X "
								+ nome_vendedores + " " + z.format(remetente.getQuantidade()) + " "
								+ remetente.getMedida() + " de "
								+ remetente.getModelo_safra().getProduto().getNome_produto() + " "
								+ remetente.getModelo_safra().getProduto().getTransgenia() + " da safra "
								+ remetente.getModelo_safra().getAno_plantio() + "/"
								+ remetente.getModelo_safra().getAno_colheita();
						texto_detalhado = texto_detalhado + "";
						sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, 16));

						cell = row.createCell(cellnum++);
						cell.setCellStyle(textStyleAlinhadoEsquerda);

						cell.setCellValue(texto_detalhado);
						quantidade_total_kgs_carregados += quantidade;

					}
				} // fim do laço for
				sheet.setAutoFilter(CellRangeAddress.valueOf("A4:O4"));
				NumberFormat z = NumberFormat.getNumberInstance();

				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Totais(Kg's):");

				cell = row.createCell(5);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_carregados);

				cell = row.createCell(7);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_nf_interna);

				cell = row.createCell(9);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_nf_venda1);

				cell = row.createCell(10);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(valor_total_nf_venda1.doubleValue());

				cell = row.createCell(11);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_diferenca);

				cell = row.createCell(13);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_nf_complemento);

				cell = row.createCell(14);
				cell.setCellStyle(valorStyle);
				cell.setCellValue(valor_total_nf_complemento.doubleValue());
				row = sheet.createRow(rownum++);

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Totais(sacos):");

				cell = row.createCell(5);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_carregados / 60);

				cell = row.createCell(7);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_nf_interna / 60);

				cell = row.createCell(9);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_nf_venda1 / 60);

				cell = row.createCell(11);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_diferenca / 60);

				cell = row.createCell(13);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(quantidade_total_kgs_nf_complemento / 60);

				// totais
				row = sheet.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso Carregado");

				cell = row.createCell(1);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(z.format(quantidade_total_kgs_carregados) + " Kgs");

				cell = row.createCell(2);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso NF's Interna:");

				cell = row.createCell(3);
				cell.setCellStyle(numberStyle);
				if (nf_interna_ativo) {

					cell.setCellValue(z.format(quantidade_total_kgs_nf_interna) + " Kgs");
				} else {
					cell.setCellValue("Não Aplicável");

				}

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso NF's Venda:");

				cell = row.createCell(5);
				cell.setCellStyle(numberStyle);
				if (nf_venda_ativo || nf_complemento_ativo) {
					cell.setCellValue(
							z.format(quantidade_total_kgs_nf_venda1 + quantidade_total_kgs_nf_complemento) + " Kgs");

				} else {
					cell.setCellValue("Não Aplicável");

				}

				cell = row.createCell(6);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Valor NF's Venda:");

				cell = row.createCell(7);
				cell.setCellStyle(numberStyle);

				BigDecimal soma = valor_total_nf_venda1.add(valor_total_nf_complemento);

				if (nf_venda_ativo || nf_complemento_ativo) {
					cell.setCellValue(NumberFormat.getCurrencyInstance(ptBr).format(soma.doubleValue()));

				} else {
					cell.setCellValue("Não Aplicável");

				}

				rownum++;
				row = sheet.createRow(rownum);

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso a Carregar");

				cell = row.createCell(1);
				cell.setCellStyle(numberStyle);
				cell.setCellValue(z.format(quantidade_kg - quantidade_total_kgs_carregados) + " Kgs");

				cell = row.createCell(2);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso a Emitir:");

				cell = row.createCell(3);
				cell.setCellStyle(numberStyle);
				if (nf_interna_ativo) {
					cell.setCellValue(
							z.format(quantidade_total_kgs_carregados - quantidade_total_kgs_nf_interna) + " Kgs");
				} else {
					cell.setCellValue("Não Aplicável");

				}

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("Peso a Emitir:");

				cell = row.createCell(5);
				cell.setCellStyle(numberStyle);
				if (nf_venda_ativo || nf_complemento_ativo) {
					cell.setCellValue(z.format(quantidade_total_kgs_carregados
							- (quantidade_total_kgs_nf_venda1 + quantidade_total_kgs_nf_complemento)) + " Kgs");

				} else {
					cell.setCellValue("Não Aplicável");

				}

				// status baseado no peso total ja carregado

				rownum += 2;

				row = sheet.createRow(rownum);

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);
				cell.setCellValue(
						"Status parcial gerado de forma automatica calculados a partir do peso total recebido: "
								+ z.format(quantidade_kg) + " kgs |" + z.format(quantidade_sacos) + " sacos");

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 6));

				rownum++;
				row = sheet.createRow(rownum);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);

				String texto = "";
				double diferenca = quantidade_kg - quantidade_total_kgs_carregados;
				if (diferenca == 0) {
					texto = texto + "Carregamento Concluído";
				} else if (diferenca > 0) {
					texto = texto + "Carregamento Incompleto, falta carregar " + z.format(diferenca) + " Kgs";

				} else if (diferenca < 0) {
					texto = texto + "Carregamento Excedido, excedeu " + z.format(diferenca) + " Kgs";

				}

				cell.setCellValue(texto);

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 2));

				// status nf remessa
				rownum += 1;
				row = sheet.createRow(rownum);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);

				String texto_nf_remessa = "";
				double diferenca_nf_remessa = quantidade_total_kgs_carregados - quantidade_total_kgs_nf_interna;
				if (diferenca_nf_remessa == 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Concluído";
				} else if (diferenca_nf_remessa > 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, falta emitir "
							+ z.format(diferenca_nf_remessa) + " Kgs";

				} else if (diferenca_nf_remessa < 0) {
					texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, excedeu "
							+ z.format(diferenca_nf_remessa) + " Kgs";

				}

				if (nf_interna_ativo)
					cell.setCellValue(texto_nf_remessa);
				else
					cell.setCellValue("Emissão de NF's Interna Não Aplicável");

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

				// status de nf de venda
				rownum += 1;
				row = sheet.createRow(rownum);
				cellnum = 0;

				cell = row.createCell(cellnum);
				cell.setCellStyle(textStyleAlinhadoEsquerdaNegrito);

				String texto_nf_venda = "";
				double diferenca_nf_venda = quantidade_total_kgs_carregados
						- (quantidade_total_kgs_nf_venda1 + quantidade_total_kgs_nf_complemento);
				if (diferenca_nf_venda == 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído";
				} else if (diferenca_nf_venda > 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir "
							+ z.format(diferenca_nf_venda) + " Kgs";

				} else if (diferenca_nf_venda < 0) {
					texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu "
							+ z.format(diferenca_nf_venda) + " Kgs";

				}
				if (nf_venda_ativo || nf_complemento_ativo)
					cell.setCellValue(texto_nf_venda);
				else
					cell.setCellValue("Emissão de NF's de Venda Não Aplicável");

				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, cellnum, 3));

				try {

					new JFXPanel();
					Platform.runLater(() -> {

						// pegar ultima pasta
						ManipularTxt manipular_ultima_pasta = new ManipularTxt();
						String ultima_pasta = manipular_ultima_pasta
								.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
						if (d == null) {
							d = new FileChooser();
						}
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

		JButton btnTransferirCarga = new JButton("Transferir");
		panel_16.add(btnTransferirCarga, "cell 1 0,alignx center,aligny center");
		btnTransferirCarga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_carregamento.getSelectedRow();// pega o indice da linha na tabela
				CadastroContrato.Carregamento carga_selecionada = lista_carregamentos.get(rowSel);

				TelaConfirmarTransferenciaCarga tela = new TelaConfirmarTransferenciaCarga(carga_selecionada,
						contrato_local, isto);
				tela.setTelaPag(isto);
				tela.setVisible(true);
			}
		});
		botoes.add(btnTransferirCarga);

		JButton btnEditarCarregamento = new JButton("Editar");
		panel_16.add(btnEditarCarregamento, "cell 2 0,alignx center,aligny center");
		btnEditarCarregamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = table_carregamento.getSelectedRow();// pega o indice da linha na tabela

				TelaConfirmarCarregamento tela = new TelaConfirmarCarregamento(1, contrato_local,
						lista_carregamentos.get(rowSel), isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		botoes.add(btnEditarCarregamento);

		JButton btnExcluirCarregamento = new JButton("Excluir");
		panel_16.add(btnExcluirCarregamento, "cell 3 0,alignx center,aligny center");
		botoes.add(btnExcluirCarregamento);
		btnExcluirCarregamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					int indiceDaLinha = table_carregamento.getSelectedRow();
					String descricao = table_carregamento.getValueAt(indiceDaLinha, 1).toString();
					int id_carregamento_selecionado = Integer
							.parseInt(table_carregamento.getValueAt(indiceDaLinha, 0).toString());
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

					if (!descricao.equalsIgnoreCase("+Transferencia")
							&& !descricao.equalsIgnoreCase("-Transferencia")) {
						if (JOptionPane.showConfirmDialog(isto, "Excluir", "Deseja excluir o carregamento?",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							if (gerenciar.removerCarregamento(contrato_local.getId(), id_carregamento_selecionado)) {
								JOptionPane.showMessageDialog(isto, "Carregamento Excluido!");
								pesquisar_carregamentos(true);

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao remover o carregamento selecionado\nConsulte o administrador do sistema!");
							}
						}
					} else if (descricao.equalsIgnoreCase("-Transferencia")) {
						if (JOptionPane.showConfirmDialog(isto, "Excluir Transferencia?",
								"Deseja excluir essa transferencia?", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							// excluir transferencia
							int id_transferencia_selecionada = Integer
									.parseInt(table_carregamento.getValueAt(indiceDaLinha, 0).toString());
							GerenciarBancoTransferenciasCarga gerenciar_cargas = new GerenciarBancoTransferenciasCarga();

							if (gerenciar_cargas.removerTransferencia(id_transferencia_selecionada)) {
								JOptionPane.showMessageDialog(isto, "Transferencia removida!");
								pesquisar_carregamentos(true);

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao remover a transferencia selecionada\nConsulte o administrador do sistema!");
							}
						} else {

						}
					} else if (descricao.equalsIgnoreCase("+Transferencia")) {
						JOptionPane.showMessageDialog(isto, "Remova essa transferência no contrato de origem");
					}

				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(isto, "Nenhum carregamento selecionado para excluir");

				}

			}
		});
		botoes.add(btnAdicionarCarregamento);
		panel_16.add(btnAdicionarCarregamento, "cell 4 0,alignx center,aligny center");

		JPanel panel_17 = new JPanel();
		panel_17.setBackground(Color.WHITE);
		painel_pai_carregamentos.add(panel_17, "cell 0 0,grow");
		panel_17.setLayout(new MigLayout("", "[]", "[][]"));
		panel_17.add(lblNewLabel_4, "cell 0 0");

		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));

		JLabel lblNewLabel_29 = new JLabel("");
		panel_17.add(lblNewLabel_29, "cell 0 1");
		lblNewLabel_29.setIcon(
				new ImageIcon(TelaGerenciarContrato.class.getResource("/imagens/icone_caminhao_carregado2.png")));

		JPanel panel_18 = new JPanel();
		panel_18.setBackground(Color.WHITE);
		painel_pai_carregamentos.add(panel_18, "cell 6 6 1 3,grow");
		panel_18.setLayout(new MigLayout("", "[][][]", "[]"));

		JPanel panel_6_1 = new JPanel();
		panel_18.add(panel_6_1, "cell 0 0");
		panel_6_1.setBackground(new Color(102, 153, 102));
		GridBagLayout gbl_panel_6_1 = new GridBagLayout();
		gbl_panel_6_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_6_1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_6_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_6_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_6_1.setLayout(gbl_panel_6_1);

		JLabel lblNewLabel_32_1_1 = new JLabel("Romaneios");
		lblNewLabel_32_1_1.setForeground(Color.BLACK);
		lblNewLabel_32_1_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_32_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_32_1_1.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_32_1_1.gridwidth = 2;
		gbc_lblNewLabel_32_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_32_1_1.gridx = 0;
		gbc_lblNewLabel_32_1_1.gridy = 0;
		panel_6_1.add(lblNewLabel_32_1_1, gbc_lblNewLabel_32_1_1);

		JLabel lblNewLabel_32_3 = new JLabel("Num Romaneios:");
		lblNewLabel_32_3.setForeground(Color.BLACK);
		lblNewLabel_32_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_32_3 = new GridBagConstraints();
		gbc_lblNewLabel_32_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32_3.gridx = 0;
		gbc_lblNewLabel_32_3.gridy = 2;
		panel_6_1.add(lblNewLabel_32_3, gbc_lblNewLabel_32_3);

		lblNumRomaneiosCarregamento = new JLabel("0");
		lblNumRomaneiosCarregamento.setForeground(Color.BLACK);
		lblNumRomaneiosCarregamento.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblNumRomaneiosCarregamento = new GridBagConstraints();
		gbc_lblNumRomaneiosCarregamento.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumRomaneiosCarregamento.gridx = 1;
		gbc_lblNumRomaneiosCarregamento.gridy = 2;
		panel_6_1.add(lblNumRomaneiosCarregamento, gbc_lblNumRomaneiosCarregamento);

		JLabel lblNewLabel_32_2_2 = new JLabel("Peso Total:");
		lblNewLabel_32_2_2.setForeground(Color.BLACK);
		lblNewLabel_32_2_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_32_2_2 = new GridBagConstraints();
		gbc_lblNewLabel_32_2_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_32_2_2.gridx = 0;
		gbc_lblNewLabel_32_2_2.gridy = 3;
		panel_6_1.add(lblNewLabel_32_2_2, gbc_lblNewLabel_32_2_2);

		lblTotalSacosKGsRomaneiosSaida = new JLabel("0.0");
		lblTotalSacosKGsRomaneiosSaida.setForeground(Color.BLACK);
		lblTotalSacosKGsRomaneiosSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblTotalSacosKGsRomaneiosSaida = new GridBagConstraints();
		gbc_lblTotalSacosKGsRomaneiosSaida.gridx = 1;
		gbc_lblTotalSacosKGsRomaneiosSaida.gridy = 3;
		panel_6_1.add(lblTotalSacosKGsRomaneiosSaida, gbc_lblTotalSacosKGsRomaneiosSaida);

		JPanel panel_12_1 = new JPanel();
		panel_18.add(panel_12_1, "cell 1 0");
		panel_12_1.setBackground(new Color(204, 153, 153));
		GridBagLayout gbl_panel_12_1 = new GridBagLayout();
		gbl_panel_12_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_12_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_12_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_12_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_12_1.setLayout(gbl_panel_12_1);

		JLabel lblNewLabel_41_1 = new JLabel("Notas Fiscais Interna");
		lblNewLabel_41_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_41_1 = new GridBagConstraints();
		gbc_lblNewLabel_41_1.gridwidth = 2;
		gbc_lblNewLabel_41_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_41_1.gridx = 0;
		gbc_lblNewLabel_41_1.gridy = 0;
		panel_12_1.add(lblNewLabel_41_1, gbc_lblNewLabel_41_1);

		JLabel lblNewLabel_42_2 = new JLabel("Peso Emitido:");
		lblNewLabel_42_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_42_2 = new GridBagConstraints();
		gbc_lblNewLabel_42_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_42_2.gridx = 0;
		gbc_lblNewLabel_42_2.gridy = 2;
		panel_12_1.add(lblNewLabel_42_2, gbc_lblNewLabel_42_2);

		lblPesoTotalNFInternaSaida = new JLabel("0.0");
		lblPesoTotalNFInternaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPesoTotalNFInternaSaida = new GridBagConstraints();
		gbc_lblPesoTotalNFInternaSaida.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoTotalNFInternaSaida.gridx = 1;
		gbc_lblPesoTotalNFInternaSaida.gridy = 2;
		panel_12_1.add(lblPesoTotalNFInternaSaida, gbc_lblPesoTotalNFInternaSaida);

		JLabel lblNewLabel_42_1_2 = new JLabel("Peso a Emitir:");
		lblNewLabel_42_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_42_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_42_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_42_1_2.gridx = 0;
		gbc_lblNewLabel_42_1_2.gridy = 3;
		panel_12_1.add(lblNewLabel_42_1_2, gbc_lblNewLabel_42_1_2);

		lblPesoRestanteNFInternaSaida = new JLabel("0.0");
		lblPesoRestanteNFInternaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPesoRestanteNFInternaSaida = new GridBagConstraints();
		gbc_lblPesoRestanteNFInternaSaida.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoRestanteNFInternaSaida.gridx = 1;
		gbc_lblPesoRestanteNFInternaSaida.gridy = 3;
		panel_12_1.add(lblPesoRestanteNFInternaSaida, gbc_lblPesoRestanteNFInternaSaida);

		JPanel panel_10_1 = new JPanel();
		panel_18.add(panel_10_1, "cell 2 0");
		panel_10_1.setBackground(new Color(204, 153, 102));
		GridBagLayout gbl_panel_10_1 = new GridBagLayout();
		gbl_panel_10_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_10_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_10_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_10_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_10_1.setLayout(gbl_panel_10_1);

		JLabel lblNewLabel_39_1 = new JLabel("Notas Fiscais Venda");
		lblNewLabel_39_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_39_1 = new GridBagConstraints();
		gbc_lblNewLabel_39_1.gridwidth = 2;
		gbc_lblNewLabel_39_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_39_1.gridx = 0;
		gbc_lblNewLabel_39_1.gridy = 0;
		panel_10_1.add(lblNewLabel_39_1, gbc_lblNewLabel_39_1);

		JLabel lblNewLabel_32_2_1_2 = new JLabel("Peso Emitido:");
		lblNewLabel_32_2_1_2.setForeground(Color.BLACK);
		lblNewLabel_32_2_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_32_2_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_32_2_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32_2_1_2.gridx = 0;
		gbc_lblNewLabel_32_2_1_2.gridy = 2;
		panel_10_1.add(lblNewLabel_32_2_1_2, gbc_lblNewLabel_32_2_1_2);

		lblPesoTotalNFVendaSaida = new JLabel("0.0");
		lblPesoTotalNFVendaSaida.setForeground(Color.BLACK);
		lblPesoTotalNFVendaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPesoTotalNFVendaSaida = new GridBagConstraints();
		gbc_lblPesoTotalNFVendaSaida.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoTotalNFVendaSaida.gridx = 1;
		gbc_lblPesoTotalNFVendaSaida.gridy = 2;
		panel_10_1.add(lblPesoTotalNFVendaSaida, gbc_lblPesoTotalNFVendaSaida);

		JLabel lblNewLabel_32_2_1_1_1 = new JLabel("Peso a Emitir:");
		lblNewLabel_32_2_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_32_2_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_32_2_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_32_2_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32_2_1_1_1.gridx = 0;
		gbc_lblNewLabel_32_2_1_1_1.gridy = 3;
		panel_10_1.add(lblNewLabel_32_2_1_1_1, gbc_lblNewLabel_32_2_1_1_1);

		lblPesoRestanteNFVendaSaida = new JLabel("0.0");
		lblPesoRestanteNFVendaSaida.setForeground(Color.BLACK);
		lblPesoRestanteNFVendaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblPesoRestanteNFVendaSaida = new GridBagConstraints();
		gbc_lblPesoRestanteNFVendaSaida.insets = new Insets(0, 0, 5, 0);
		gbc_lblPesoRestanteNFVendaSaida.gridx = 1;
		gbc_lblPesoRestanteNFVendaSaida.gridy = 3;
		panel_10_1.add(lblPesoRestanteNFVendaSaida, gbc_lblPesoRestanteNFVendaSaida);

		JLabel lblNewLabel_38_1 = new JLabel("$ Total:");
		lblNewLabel_38_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_38_1 = new GridBagConstraints();
		gbc_lblNewLabel_38_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_38_1.gridx = 0;
		gbc_lblNewLabel_38_1.gridy = 4;
		panel_10_1.add(lblNewLabel_38_1, gbc_lblNewLabel_38_1);

		lblValorTotalNFVendaSaida = new JLabel("0.0");
		lblValorTotalNFVendaSaida.setForeground(Color.BLACK);
		lblValorTotalNFVendaSaida.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_lblValorTotalNFVendaSaida = new GridBagConstraints();
		gbc_lblValorTotalNFVendaSaida.gridx = 1;
		gbc_lblValorTotalNFVendaSaida.gridy = 4;
		panel_10_1.add(lblValorTotalNFVendaSaida, gbc_lblValorTotalNFVendaSaida);

		JPanel panel_19 = new JPanel();
		panel_19.setBackground(Color.WHITE);
		painel_pai_carregamentos.add(panel_19, "cell 2 0 5 3,growx,aligny top");
		panel_19.setLayout(new MigLayout("", "[][]", "[]"));

		JPanel lbl_produto = new JPanel();
		panel_19.add(lbl_produto, "cell 0 0");
		lbl_produto.setForeground(Color.WHITE);
		lbl_produto.setBackground(new Color(0, 102, 102));
		lbl_produto.setLayout(
				new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px][]"));

		JLabel lblNewLabel_5 = new JLabel("Data:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
		lblNewLabel_5.setBorder(null);
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_5, "cell 0 0,alignx right,aligny center");

		lblDataContrato = new JLabel("data contrato");
		lblDataContrato.setForeground(Color.WHITE);
		lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblDataContrato, "cell 1 0,alignx center,aligny center");

		JLabel lblNewLabel_9 = new JLabel("Corretor:");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_9, "cell 0 1,alignx right,growy");

		lblCorretor = new JLabel("corretor");
		lblCorretor.setForeground(Color.WHITE);
		lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblCorretor, "cell 1 1,alignx center,aligny center");

		JLabel lblNewLabel_6 = new JLabel("Compradores:");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_6, "cell 0 2,alignx right,growy");

		lblCompradores = new JLabel("compradores");
		lblCompradores.setForeground(Color.WHITE);
		lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblCompradores, "cell 1 2,alignx center,aligny center");

		JLabel lblNewLabel_8 = new JLabel("Vendedores:");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_8, "cell 0 3,alignx right,aligny center");

		lblVendedores = new JLabel("vendedores");
		lblVendedores.setForeground(Color.WHITE);
		lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblVendedores, "cell 1 3,alignx center,aligny center");

		JLabel lblnewvalorsaco = new JLabel("Valor Unidade:");
		lblnewvalorsaco.setForeground(Color.WHITE);
		lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblnewvalorsaco, "flowy,cell 0 4,alignx right,aligny center");

		lblValorSaco = new JLabel("valor saco");
		lblValorSaco.setForeground(Color.WHITE);
		lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblValorSaco, "cell 1 4,alignx center,aligny center");

		JLabel lblNewLabel_10 = new JLabel("Quantidade:");
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_10, "cell 2 4,alignx right,aligny center");

		lblQuantidade = new JLabel("quantidade");
		lblQuantidade.setForeground(Color.WHITE);
		lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblQuantidade, "cell 3 4,alignx center,aligny center");

		JLabel lblNewLabel_11 = new JLabel("Valor Total:");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_11, "cell 4 4,alignx right,aligny center");

		lblValorTotal = new JLabel("valor total do pagamento do contrato longo");
		lblValorTotal.setForeground(Color.WHITE);
		lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblValorTotal, "cell 5 4,alignx center,aligny center");

		JLabel lblProdutonm = new JLabel("Produto:");
		lblProdutonm.setForeground(Color.WHITE);
		lblProdutonm.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblProdutonm, "cell 0 5,alignx right");

		lblProduto = new JLabel("valor produto");
		lblProduto.setForeground(Color.WHITE);
		lblProduto.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblProduto, "cell 1 5,alignx center");

		JLabel lblSafran = new JLabel("Safra:");
		lblSafran.setForeground(Color.WHITE);
		lblSafran.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblSafran, "cell 2 5,alignx right");

		lblSafra = new JLabel("valor produto");
		lblSafra.setForeground(Color.WHITE);
		lblSafra.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblSafra, "cell 3 5,alignx center");

		JPanel panel_13_1 = new JPanel();
		panel_19.add(panel_13_1, "cell 1 0");
		panel_13_1.setBackground(new Color(0, 51, 51));
		GridBagLayout gbl_panel_13_1 = new GridBagLayout();
		gbl_panel_13_1.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_13_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_13_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_13_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_13_1.setLayout(gbl_panel_13_1);

		JLabel lblNewLabel_43_1 = new JLabel("Status do Carregamento");
		lblNewLabel_43_1.setForeground(Color.WHITE);
		lblNewLabel_43_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_43_1 = new GridBagConstraints();
		gbc_lblNewLabel_43_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_43_1.gridx = 1;
		gbc_lblNewLabel_43_1.gridy = 0;
		panel_13_1.add(lblNewLabel_43_1, gbc_lblNewLabel_43_1);

		JLabel lblNewLabel_40_3 = new JLabel("Recebimento:");
		lblNewLabel_40_3.setForeground(Color.WHITE);
		lblNewLabel_40_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_40_3 = new GridBagConstraints();
		gbc_lblNewLabel_40_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_40_3.gridx = 0;
		gbc_lblNewLabel_40_3.gridy = 2;
		panel_13_1.add(lblNewLabel_40_3, gbc_lblNewLabel_40_3);

		lblStatusCarregamentoSaida = new JLabel("Aguardando:");
		lblStatusCarregamentoSaida.setForeground(Color.WHITE);
		lblStatusCarregamentoSaida.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblStatusCarregamentoSaida = new GridBagConstraints();
		gbc_lblStatusCarregamentoSaida.gridwidth = 3;
		gbc_lblStatusCarregamentoSaida.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatusCarregamentoSaida.gridx = 1;
		gbc_lblStatusCarregamentoSaida.gridy = 2;
		panel_13_1.add(lblStatusCarregamentoSaida, gbc_lblStatusCarregamentoSaida);

		JLabel lblNewLabel_40_1_1 = new JLabel("NF's Venda:");
		lblNewLabel_40_1_1.setForeground(Color.WHITE);
		lblNewLabel_40_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_40_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_40_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_40_1_1.gridx = 0;
		gbc_lblNewLabel_40_1_1.gridy = 3;
		panel_13_1.add(lblNewLabel_40_1_1, gbc_lblNewLabel_40_1_1);

		lblStatusNFVendaSaida = new JLabel("Aguardando:");
		lblStatusNFVendaSaida.setForeground(Color.WHITE);
		lblStatusNFVendaSaida.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblStatusNFVendaSaida = new GridBagConstraints();
		gbc_lblStatusNFVendaSaida.gridwidth = 3;
		gbc_lblStatusNFVendaSaida.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatusNFVendaSaida.gridx = 1;
		gbc_lblStatusNFVendaSaida.gridy = 3;
		panel_13_1.add(lblStatusNFVendaSaida, gbc_lblStatusNFVendaSaida);

		JLabel lblNewLabel_40_2_1 = new JLabel("NF' Interna:");
		lblNewLabel_40_2_1.setForeground(Color.WHITE);
		lblNewLabel_40_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_40_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_40_2_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_40_2_1.gridx = 0;
		gbc_lblNewLabel_40_2_1.gridy = 4;
		panel_13_1.add(lblNewLabel_40_2_1, gbc_lblNewLabel_40_2_1);

		lblStatusNFRemessaSaida = new JLabel("Aguardando:");
		lblStatusNFRemessaSaida.setForeground(Color.WHITE);
		lblStatusNFRemessaSaida.setFont(new Font("SansSerif", Font.BOLD, 16));
		GridBagConstraints gbc_lblStatusNFRemessaSaida = new GridBagConstraints();
		gbc_lblStatusNFRemessaSaida.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatusNFRemessaSaida.gridwidth = 3;
		gbc_lblStatusNFRemessaSaida.gridx = 1;
		gbc_lblStatusNFRemessaSaida.gridy = 4;
		panel_13_1.add(lblStatusNFRemessaSaida, gbc_lblStatusNFRemessaSaida);

		btnAdicionarCarregamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaConfirmarCarregamento tela_confirmar = new TelaConfirmarCarregamento(0, contrato_local, null, isto);
				tela_confirmar.setTelaPai(isto);
				tela_confirmar.setVisible(true);

			}
		});

		painelPagamentos.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPrincipal.setBackgroundAt(3, Color.GREEN);
		painelPrincipal.setIconAt(3,
				new ImageIcon(TelaGerenciarContrato.class.getResource("/imagens/icone_pagamentos.png")));
		painelPrincipal.setEnabledAt(3, true);
		painelPagamentos.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 255, 255));
		painelPagamentos.add(panel_4);
		panel_4.setLayout(new MigLayout("",
				"[290px,grow][4px,grow][425px,grow][13px,grow][128px,grow][33px,grow][120px,grow][10px,grow][81px,grow][8px,grow][147px,grow][10px,grow][83px,grow]",
				"[22px][16px][18px][16px][107px][16px][17px][12px][22px][166px,grow][28px][42px,grow][204px]"));
		JPanel panelInformativoAbaPagamentos = new JPanel();
		panel_4.add(panelInformativoAbaPagamentos, "cell 6 0 7 9,grow");
		panelInformativoAbaPagamentos.setForeground(Color.WHITE);
		panelInformativoAbaPagamentos.setBackground(new Color(0, 102, 102));
		panelInformativoAbaPagamentos.add(painel_informacoes_tab_pagamentos);
		panelInformativoAbaPagamentos.setLayout(null);
		panel_4.add(lblNewLabel, "cell 0 0,alignx left,aligny top");

		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(0, 51, 0));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));

		JTable tabela_modelo_pagamentos = new JTable(modelo);
		tabela_modelo_pagamentos.setOpaque(false);
		tabela_modelo_pagamentos.setBackground(new Color(0, 0, 0, 0));
		// tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_modelo_pagamentos.setBackground(new Color(255, 255, 255));

		tabela_modelo_pagamentos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela_modelo_pagamentos.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela_modelo_pagamentos.getColumnModel().getColumn(2).setPreferredWidth(170);
		tabela_modelo_pagamentos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabela_modelo_pagamentos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabela_modelo_pagamentos.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela_modelo_pagamentos.getColumnModel().getColumn(6).setPreferredWidth(70);
		tabela_modelo_pagamentos.getColumnModel().getColumn(7).setPreferredWidth(90);
		tabela_modelo_pagamentos.getColumnModel().getColumn(8).setPreferredWidth(90);

		JScrollPane scrollPane = new JScrollPane(tabela_modelo_pagamentos);
		panel_4.add(scrollPane, "cell 0 4 5 1,grow");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(new Color(0, 153, 153));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel_4.add(lblNewLabel_1, "cell 2 2 3 1,alignx right,aligny top");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(204, 102, 0));

		JLabel lblNewLabel_2 = new JLabel("Valor Total dos pagamentos:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_4.add(lblNewLabel_2, "cell 2 6,alignx right,aligny top");

		lblValorTotalPagamentos = new JLabel("");
		panel_4.add(lblValorTotalPagamentos, "cell 4 6,growx,aligny top");
		lblValorTotalPagamentos.setFont(new Font("Tahoma", Font.PLAIN, 14));

		table_pagamentos_contratuais = new JTable(modelo_pagamentos);
		table_pagamentos_contratuais.setOpaque(false);

		table_pagamentos_contratuais.setRowHeight(30);

		table_pagamentos_contratuais.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent t) {
				// Se o botão direito do mouse foi pressionado
				if (t.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuTabelPagamentos.show(table_pagamentos_contratuais, t.getX(), t.getY());
				}
			}
		});

		JScrollPane scrollPanePagamentos = new JScrollPane(table_pagamentos_contratuais);
		scrollPanePagamentos.setOpaque(true);
		scrollPanePagamentos.getViewport().setBackground(Color.white);
		panel_4.add(scrollPanePagamentos, "cell 0 9 13 1,grow");
		scrollPanePagamentos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanePagamentos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanePagamentos.setAutoscrolls(true);
		scrollPanePagamentos.setBackground(new Color(0, 153, 153));

		painelGraficoPagamentos = new JPanelGraficoPadrao(0, 0, "Pago: ", "a Pagar: ");
		panel_4.add(painelGraficoPagamentos, "cell 2 10 1 3,grow");
		painelGraficoPagamentos.setLayout(null);

		JLabel lblPagamentosDesteContrato = new JLabel("Pagamentos deste Contrato:\r\n");
		lblPagamentosDesteContrato.setOpaque(true);
		lblPagamentosDesteContrato.setForeground(Color.WHITE);
		lblPagamentosDesteContrato.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPagamentosDesteContrato.setBackground(new Color(0, 51, 0));
		panel_4.add(lblPagamentosDesteContrato, "cell 0 8,alignx left,aligny top");

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel_4.add(panel, "cell 5 10 8 2,grow");
		panel.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][]", "[]"));

		JButton btnExportarPagamentos = new JButton("Exportar");
		panel.add(btnExportarPagamentos, "cell 11 0");
		btnExportarPagamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet3 = workbook.createSheet("Pagamentos");

				// Definindo alguns padroes de layout
				sheet3.setDefaultColumnWidth(20);
				sheet3.setDefaultRowHeight((short) 400);

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

				// celula para texto alinhado ao centro
				CellStyle textStyle = workbook.createCellStyle();
				// textStyle.setAlignment(HorizontalAlignment.CENTER);
				textStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// celula para numero alinhado ao centro
				CellStyle numberStyle = workbook.createCellStyle();
				numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
				numberStyle.setAlignment(HorizontalAlignment.CENTER);
				numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// estilo para celula do tipo numero alinhado ao centro
				CellStyle valorStyle = workbook.createCellStyle();
				valorStyle.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));
				valorStyle.setAlignment(HorizontalAlignment.CENTER);
				valorStyle.setVerticalAlignment(VerticalAlignment.CENTER);

				// estilo para cabecalho fundo laranja
				CellStyle celula_fundo_laranja = workbook.createCellStyle();
				celula_fundo_laranja.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_laranja.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
				celula_fundo_laranja.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_laranja.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont = workbook.createFont();
				newFont.setBold(true);
				newFont.setColor(IndexedColors.BLACK.getIndex());
				newFont.setFontName("Calibri");
				newFont.setItalic(false);
				newFont.setFontHeight((short) (11 * 20));

				celula_fundo_laranja.setFont(newFont);

				// celula_number_amarelo_texto_preto
				// estilo para cabecalho fundo laranja
				CellStyle celula_number_amarelo_texto_preto = workbook.createCellStyle();
				celula_number_amarelo_texto_preto.setDataFormat(numberFormat.getFormat("#,##0.00"));
				celula_number_amarelo_texto_preto.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_number_amarelo_texto_preto.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				celula_number_amarelo_texto_preto.setAlignment(HorizontalAlignment.CENTER);
				celula_number_amarelo_texto_preto.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont_blabk = workbook.createFont();
				newFont_blabk.setBold(true);
				newFont_blabk.setColor(IndexedColors.BLACK.getIndex());
				newFont_blabk.setFontName("Calibri");
				newFont_blabk.setItalic(false);
				newFont_blabk.setFontHeight((short) (11 * 20));

				celula_number_amarelo_texto_preto.setFont(newFont_blabk);
				numberStyle.setFont(newFont_blabk);

				// estilo para cabecalho fundo laranja
				CellStyle celula_fundo_laranja_texto_branco = workbook.createCellStyle();
				celula_fundo_laranja_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_laranja_texto_branco.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
				celula_fundo_laranja_texto_branco.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_laranja_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFont_branca = workbook.createFont();
				newFont_branca.setBold(true);
				newFont_branca.setColor(IndexedColors.WHITE.getIndex());
				newFont_branca.setFontName("Calibri");
				newFont_branca.setItalic(false);
				newFont_branca.setFontHeight((short) (11 * 20));

				celula_fundo_laranja_texto_branco.setFont(newFont_branca);

				// estilo para cabecalho fundo verde
				CellStyle celula_fundo_verde_texto_branco = workbook.createCellStyle();
				celula_fundo_verde_texto_branco.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				celula_fundo_verde_texto_branco.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				celula_fundo_verde_texto_branco.setAlignment(HorizontalAlignment.CENTER);
				celula_fundo_verde_texto_branco.setVerticalAlignment(VerticalAlignment.CENTER);

				celula_fundo_verde_texto_branco.setFont(newFont_branca);

				// Configurando as informacoes
				row = sheet3.createRow(rownum++);

				CadastroCliente compradores[] = contrato_local.getCompradores();
				CadastroCliente vendedores[] = contrato_local.getVendedores();

				String nome_compradores;
				String nome_vendedores;
				if (compradores[0].getTipo_pessoa() == 0) {
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();
				}

				if (compradores[1] != null) {
					if (compradores[1].getTipo_pessoa() == 0) {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
					} else {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();
					}
				}

				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedores = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedores = vendedores[0].getNome_fantaia();
				}

				if (vendedores[1] != null) {
					if (vendedores[1].getTipo_pessoa() == 0) {
						nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
					} else {
						nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
					}
				}

				cell = row.createCell(cellnum);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue(nome_compradores.toUpperCase() + " X " + nome_vendedores + " CTR "
						+ contrato_local.getCodigo() + " IE.: " + vendedores[0].getIe());
				sheet3.addMergedRegion(new CellRangeAddress(0, 0, cellnum, 5));

				// linha quantidade, safra, sacos, etc
				row = sheet3.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);

				double quantidade_kg = 0;
				double quantidade_sacos = 0;

				if (contrato.getMedida().equalsIgnoreCase("KG")) {
					quantidade_kg = contrato.getQuantidade();
					quantidade_sacos = quantidade_kg / 60;
				} else if (contrato.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_sacos = contrato.getQuantidade();
					quantidade_kg = quantidade_sacos * 60;
				}
				Locale ptBr = new Locale("pt", "BR");

				cell.setCellValue("Quantidade: " + quantidade_kg + " KGS | " + quantidade_sacos + " Sacos no valor de "
						+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_produto()) + " por "
						+ contrato.getMedida() + " no valor total de: "
						+ NumberFormat.getCurrencyInstance(ptBr).format(contrato.getValor_a_pagar()));
				sheet3.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

				row = sheet3.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue(contrato.getModelo_safra().getProduto().getNome_produto() + " "
						+ contrato.getModelo_safra().getProduto().getTransgenia() + " "
						+ contrato.getModelo_safra().getAno_plantio() + "/"
						+ contrato.getModelo_safra().getAno_colheita());
				sheet3.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellnum - 1, 5));

				// Configurando Header
				row = sheet3.createRow(rownum++);
				cellnum = 0;
				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("DATA");

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("TIPO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("DESCRIÇÃO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("VALOR PAGAMENTO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("VALOR UNIDADE".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("COBERTURA".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("DEPOSITANTE".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("CONTA DEPOSITANTE".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("FAVORECIDO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("CONTA FAVORECIDO".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("CONTRATO REMETENTE".toUpperCase());

				cell = row.createCell(cellnum++);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("CONTRATO DESTINATARIO".toUpperCase());

				ArrayList<PagamentoCompleto> pagamentos_selecionados = new ArrayList<>();
				GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

				int linhas_selecionadas[] = table_pagamentos_contratuais.getSelectedRows();// pega o indice da linha na

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					PagamentoCompleto pagamentos_selecionado = lista_pagamentos_contratuais.get(indice);
					pagamentos_selecionados.add(pagamentos_selecionado);
				}

				double soma_total_pagamentos = 0.0;
				// pega os documentos
				GerenciarBancoDocumento gerenciar = new GerenciarBancoDocumento();
				ArrayList<CadastroDocumento> docs = gerenciar.getDocumentos(contrato_local.getId());

				int num_comprovantes = 0;

				for (CadastroDocumento doc : docs) {
					if (doc.getTipo() == 2) {
						// e um pagamento
						num_comprovantes++;
					}
				}

				double valor_total_pagamentos = 0;
				double valor_total_pagamentos_efetuados = 0;
				double valor_total_transferencias_retiradas = 0;
				double valor_total_transferencias_recebidas = 0;
				double valor_total_pagamentos_restantes = 0;
				double valor_total_comissao = 0;
				double peso_total_cobertura = 0;
				double peso_total_cobertura_efetuados = 0;
				double peso_total_cobertura_transferencia_negativa = 0;
				double peso_total_cobertura_transferencia_positiva = 0;
				double peso_total_cobertura_restante = 0;
				double peso_total_cobertura_comissao = 0;

				double quantidade_total_contrato_sacos = 0;
				double valor_por_saco = 0;

				if (contrato_local.getMedida().equalsIgnoreCase("Kg")) {
					quantidade_total_contrato_sacos = contrato_local.getQuantidade() / 60;
					valor_por_saco = contrato_local.getValor_produto() * 60;
				} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
					quantidade_total_contrato_sacos = contrato_local.getQuantidade();
					valor_por_saco = contrato_local.getValor_produto();
				}

				// valor total e cobertura com base no total de sacos recebidos
				double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(contrato_local.getId());
				peso_total_cobertura = quantidade_kgs_recebidos / 60;
				valor_total_pagamentos = peso_total_cobertura * valor_por_saco;

				String valorSaco = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);
				for (PagamentoCompleto pagamento : pagamentos_selecionados) {

					row = sheet3.createRow(rownum++);
					cellnum = 0;
					/*
					 * codigo compradores vendedores status quantidade medida produto transgenia
					 * safra valor_produto valor_total data_contrato local_retirada
					 */
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(pagamento.getData_pagamento());

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					int tipo = pagamento.getTipo();
					String s_tipo = "";
					if (pagamento.getTipo() == 1) {
						s_tipo = "NORMAL";
					} else if (pagamento.getTipo() == 2) {
						s_tipo = "COMISSÃO";
					} else if (pagamento.getTipo() == 3) {
						// é uma transferencia
						if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
							s_tipo = "-Transferencia";
						} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
							// é uma transferencia positiva
							s_tipo = "+Transferencia";
						}

					}

					cell.setCellValue(s_tipo);

					// descricao
					cell = row.createCell(cellnum++);
					cell.setCellStyle(valorStyle);
					cell.setCellValue(pagamento.getDescricao());

					// valor pagamento
					double valor_pagamento = pagamento.getValor_pagamento();

					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
					double cobertura = valor_pagamento / valor_por_saco;

					if (pagamento.getTipo() == 1) {
						valor_total_pagamentos_efetuados += valor_pagamento;
						soma_total_pagamentos += pagamento.getValor_pagamento();

					} else if (pagamento.getTipo() == 2) {
						// é uma comissão
						valor_total_comissao += valor_pagamento;
					} else if (pagamento.getTipo() == 3) {
						// é uma transferencia
						if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
							// é uma transferencia negativa
							valor_total_transferencias_retiradas += valor_pagamento;
							soma_total_pagamentos -= pagamento.getValor_pagamento();

						} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
							// é uma transferencia positiva
							valor_total_transferencias_recebidas += valor_pagamento;
							soma_total_pagamentos += pagamento.getValor_pagamento();

						}

					}
					cell = row.createCell(cellnum++);
					cell.setCellStyle(valorStyle);
					cell.setCellValue(pagamento.getValor_pagamento());

					// valor da unidade
					CadastroContrato ct_remetente = pagamento.getContrato_remetente();
					CadastroContrato ct_destinatario = pagamento.getContrato_destinatario();

					valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());
					if (pagamento.getTipo() == 1) {

					} else if (pagamento.getTipo() == 2) {
						// é uma comissao

					} else if (pagamento.getTipo() == 3) {
						// é uma transferencia
						if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {

						} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
							// é uma transferencia positiva
							// pegar o preco da unidade do contrato que recebeu a transferencia
							valorString = NumberFormat.getCurrencyInstance(ptBr)
									.format(contrato_local.getValor_produto());

						}
					}
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(valorString);

					// cobertura
					cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
					if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
						cobertura = cobertura / 60;
					NumberFormat z = NumberFormat.getNumberInstance();

					String retorno = z.format(cobertura) + " sacos";

					if (pagamento.getTipo() == 1) {
						peso_total_cobertura_efetuados += cobertura;

					} else if (pagamento.getTipo() == 2) {
						peso_total_cobertura_comissao += cobertura;
					} else if (pagamento.getTipo() == 3) {
						// é uma transferencia
						if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
							retorno = "-" + retorno;
							peso_total_cobertura_transferencia_negativa += cobertura;

						} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
							// é uma transferencia positiva
							// pegar o preco da unidade do contrato que recebeu a transferencia

							cobertura = pagamento.getValor_pagamento() / contrato_local.getValor_produto();

							if (contrato_local.getMedida().equalsIgnoreCase("KG"))
								cobertura = cobertura / 60;
							peso_total_cobertura_transferencia_positiva += cobertura;

							retorno = z.format(cobertura) + " sacos";
							retorno = "+" + retorno;
						}

					}

					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(retorno);

					// depositante
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(pagamento.getDepositante().toUpperCase());

					// conta depositante
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(pagamento.getConta_bancaria_depositante());

					// favorecido
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(pagamento.getFavorecido().toUpperCase());

					// conta favorecido
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(pagamento.getConta_bancaria_favorecido());

					// contrato remetente
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(ct_remetente.getCodigo());

					// contrato destinatario
					cell = row.createCell(cellnum++);
					cell.setCellStyle(textStyle);
					cell.setCellValue(ct_destinatario.getCodigo());

				}

				sheet3.setAutoFilter(CellRangeAddress.valueOf("A4:L4"));

				row = sheet3.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(2);
				cell.setCellStyle(textStyle);
				cell.setCellValue("TOTAL CONCLUÍDO:");

				CellStyle negrito = workbook.createCellStyle();
				// textStyle.setAlignment(HorizontalAlignment.CENTER);
				negrito.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				negrito.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				negrito.setAlignment(HorizontalAlignment.CENTER);
				negrito.setVerticalAlignment(VerticalAlignment.CENTER);
				negrito.setDataFormat(numberFormat.getFormat("R$ #,##0.00"));

				HSSFFont newFontNegrita = workbook.createFont();
				newFontNegrita.setBold(true);
				newFontNegrita.setColor(IndexedColors.BLACK.getIndex());
				newFontNegrita.setFontName("Arial");
				newFontNegrita.setItalic(true);
				newFontNegrita.setFontHeight((short) (11 * 20));

				negrito.setFont(newFontNegrita);

				cell = row.createCell(3);
				cell.setCellStyle(negrito);
				// total dos pagamentos
				cell.setCellValue(soma_total_pagamentos);

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("COBERTURA TOTAL:");

				double peso_total_cobertura_concluida = peso_total_cobertura_efetuados
						- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
				NumberFormat z = NumberFormat.getNumberInstance();

				cell = row.createCell(5);
				cell.setCellStyle(negrito);
				cell.setCellValue(z.format(peso_total_cobertura_concluida) + " sacos");

				row = sheet3.createRow(rownum += 1);
				cellnum = 0;

				cell = row.createCell(2);
				cell.setCellStyle(textStyle);
				cell.setCellValue("VALOR RESTANTE:");

				cell = row.createCell(3);
				cell.setCellStyle(negrito);
				// total dos pagamentos restante
				valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
						+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
				String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);

				cell.setCellValue(valor);

				cell = row.createCell(4);
				cell.setCellStyle(textStyle);
				cell.setCellValue("COBERTURA RESTANTE:");

				peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
						+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;

				cell = row.createCell(5);
				cell.setCellStyle(negrito);
				cell.setCellValue(z.format(peso_total_cobertura_restante) + " sacos");

				// adicionais
				/******************* inicio adicionais ***********************/
				rownum = rownum += 2;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_total = "Total do Contrato: ";
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
				texto_total += valor;
				texto_total += " Cobre: ";
				texto_total += z.format(peso_total_cobertura) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_total);

				rownum = rownum += 1;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_efetuados = "Efetuados: ";
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);
				texto_efetuados += valor;
				texto_efetuados += " Cobre: ";
				texto_efetuados += z.format(peso_total_cobertura_efetuados) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_efetuados);

				// status
				cell = row.createCell(3);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue("Status do Pagamento");
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 6));

				// transferencias negativas

				rownum = rownum += 1;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_transferencias_negativas = "Transferencias:(-) ";
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);
				texto_transferencias_negativas += valor;
				texto_transferencias_negativas += " Cobre: ";
				texto_transferencias_negativas += z.format(peso_total_cobertura_transferencia_negativa) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_transferencias_negativas);

				// status
				double diferenca = (valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas
						+ valor_total_transferencias_recebidas) - valor_total_pagamentos;
				String status_pagamento = "Pagamentos: ";
				if (diferenca == 0) {
					status_pagamento += "Pagamento Concluído";
				} else if (diferenca > 0) {
					status_pagamento += "Excedeu em " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca);

				} else if (diferenca < 0) {
					status_pagamento += "Incompleto, falta " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca);

				}

				cell = row.createCell(3);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue(status_pagamento);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 6));

				// transferencias positivas

				rownum = rownum += 1;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_transferencias_positivas = "Transferencias:(+) ";
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);
				texto_transferencias_positivas += valor;
				texto_transferencias_positivas += " Cobre: ";
				texto_transferencias_positivas += z.format(peso_total_cobertura_transferencia_positiva) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_transferencias_positivas);

				String status_cobertura = "Cobertura: ";
				double diferenca_pesos = peso_total_cobertura_restante;

				if (diferenca_pesos == 0 || diferenca_pesos == -0) {
					status_cobertura += "Todos os sacos foram pagos";
				} else if (diferenca_pesos < 0) {
					status_cobertura += "Excedeu em " + z.format(diferenca_pesos) + " Sacos";

				} else if (diferenca_pesos > 0) {
					status_cobertura += "Incompleto, falta pagar " + z.format(diferenca_pesos) + " Sacos";

				}

				cell = row.createCell(3);
				cell.setCellStyle(celula_fundo_verde_texto_branco);
				cell.setCellValue(status_cobertura);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 3, 6));

				// comissao

				rownum = rownum += 1;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_comissao = "Comissão: ";
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);
				texto_comissao += valor;
				texto_comissao += " Cobre: ";
				texto_comissao += z.format(peso_total_cobertura_comissao) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_comissao);

				// concluidos

				rownum = rownum += 1;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_concluida = "Concluída:";

				double valor_total_pagamentos_concluidos = valor_total_pagamentos_efetuados
						- valor_total_transferencias_retiradas + valor_total_transferencias_recebidas;
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);
				texto_concluida += valor;
				texto_concluida += " Cobre: ";
				peso_total_cobertura_concluida = peso_total_cobertura_efetuados
						- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
				texto_concluida += z.format(peso_total_cobertura_concluida) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_concluida);

				// restante

				rownum = rownum += 1;
				row = sheet3.createRow(rownum);
				sheet3.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 2));

				String texto_restante = " Restante:";

				valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
						+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
				valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);
				texto_restante += valor;
				texto_restante += " Cobre: ";
				peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
						+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
				texto_restante += z.format(peso_total_cobertura_restante) + " sacos";

				cell = row.createCell(0);
				cell.setCellStyle(textStyle);
				cell.setCellValue(texto_restante);

				/************* fim adicionai *************************/

				// celula fundo branco em vermelho
				CellStyle aviso = workbook.createCellStyle();
				aviso.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				aviso.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				aviso.setAlignment(HorizontalAlignment.LEFT);
				aviso.setVerticalAlignment(VerticalAlignment.CENTER);

				HSSFFont newFontVermelha = workbook.createFont();
				newFontVermelha.setBold(false);
				newFontVermelha.setColor(IndexedColors.RED.getIndex());
				newFontVermelha.setFontName("Arial");
				newFontVermelha.setItalic(true);
				newFontVermelha.setFontHeight((short) (11 * 20));

				aviso.setFont(newFontVermelha);
				rownum += 1;

				rownum += 2;
				int column = 0;
				int contador_comprovantes = 0;

				// adicionar comprovantes
				boolean linha2_criada = false;
				boolean linha3_criada = false;
				boolean linha4_criada = false;
				boolean linha5_criada = false;
				boolean linha6_criada = false;
				boolean linha7_criada = false;
				boolean linha8_criada = false;
				boolean linha9_criada = false;
				boolean linha10_criada = false;

				for (CadastroContrato.CadastroPagamentoContratual pag : pagamentos_selecionados) {
					// verifica se há algum documento do tipo 2(comprovante de pagamento) para este
					// id de pagamento

					boolean tem_comprovante = false;
					CadastroDocumento comprovante = new CadastroDocumento();

					for (CadastroDocumento doc : docs) {
						if (doc.getId_pai() == pag.getId_pagamento()) {
							comprovante = doc;
							tem_comprovante = true;
							break;
						}
					}

					if (tem_comprovante) {
						// verifica a extensao do arquivo
						String extensaoDoArquivo = FilenameUtils.getExtension(comprovante.getNome_arquivo());
						if (extensaoDoArquivo.equalsIgnoreCase("png")) {

							String unidade_base_dados = configs_globais.getServidorUnidade();
							String caminho_salvar = unidade_base_dados + "\\"
									+ contrato_local.getCaminho_diretorio_contrato();
							String caminho_completo = caminho_salvar + "\\comprovantes\\"
									+ comprovante.getNome_arquivo();

							try {
								if (contador_comprovantes <= 3) {
									rownum = rownum;
								} else if (contador_comprovantes <= 7) {
									if (!linha2_criada) {
										rownum += 13;
										linha2_criada = true;
									}

								} else if (contador_comprovantes <= 11) {
									if (!linha3_criada) {
										rownum += 13;
										linha3_criada = true;
									}

								} else if (contador_comprovantes <= 15) {
									if (!linha4_criada) {
										rownum += 13;
										linha4_criada = true;
									}

								} else if (contador_comprovantes <= 19) {
									if (!linha5_criada) {
										rownum += 13;
										linha5_criada = true;
									}

								} else if (contador_comprovantes <= 23) {
									if (!linha6_criada) {
										rownum += 13;
										linha6_criada = true;
									}

								} else if (contador_comprovantes <= 27) {
									if (!linha7_criada) {
										rownum += 13;
										linha7_criada = true;
									}

								} else if (contador_comprovantes <= 31) {
									if (!linha8_criada) {
										rownum += 13;
										linha8_criada = true;
									}

								} else if (contador_comprovantes <= 35) {
									if (!linha9_criada) {
										rownum += 13;
										linha9_criada = true;
									}

								} else if (contador_comprovantes <= 39) {
									if (!linha10_criada) {
										rownum += 13;
										linha10_criada = true;
									}

								}
								InputStream inputStream = new FileInputStream(caminho_completo);

								byte[] imageBytes = IOUtils.toByteArray(inputStream);

								int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

								inputStream.close();

								CreationHelper helper = workbook.getCreationHelper();

								Drawing drawing = sheet3.createDrawingPatriarch();

								ClientAnchor anchor = helper.createClientAnchor();

								anchor.setCol1(column);
								anchor.setCol2(column + 2);
								anchor.setRow1(rownum);
								anchor.setRow2(rownum + 12);

								drawing.createPicture(anchor, pictureureIdx);

								column += 2;

								if (column > 7)
									column = 0;

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(isto, "Erro ao anexar imagem no xlsx");
								e1.printStackTrace();
							}

						}

					}
					contador_comprovantes++;

				}

				try {

					new JFXPanel();
					Platform.runLater(() -> {

						// pegar ultima pasta
						ManipularTxt manipular_ultima_pasta = new ManipularTxt();
						String ultima_pasta = manipular_ultima_pasta
								.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
						if (d == null) {
							d = new FileChooser();
						}
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

		JButton btnExcluirPagamento = new JButton("Excluir");
		panel.add(btnExcluirPagamento, "cell 13 0");
		botoes.add(btnExcluirPagamento);
		btnExcluirPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					int indiceDaLinha = table_pagamentos_contratuais.getSelectedRow();

					int id_pagamento_selecionado = Integer
							.parseInt(table_pagamentos_contratuais.getValueAt(indiceDaLinha, 0).toString());

					String descricao = table_pagamentos_contratuais.getValueAt(indiceDaLinha, 1).toString();

					if (!descricao.equalsIgnoreCase("+Transferencia")
							&& !descricao.equalsIgnoreCase("-Transferencia")) {

						if (JOptionPane.showConfirmDialog(isto, "Excluir Pagamento?", "Deseja excluir o pagamento?",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

							if (contrato_local.getSub_contrato() == 1 || contrato_local.getSub_contrato() == 6) {
								// é um subcontrato original ou importado
								// excluiir somente a relacao, verifica se este pagamento esta em replica
								boolean vem_de_replica = false;
								CadastroContrato pai = gerenciar.getContratoPai(contrato_local.getId());
								// verifica se o pai possui esse pagamento
								ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos_do_contrato_pai = gerenciar
										.getPagamentosContratuais(pai.getId());
								for (CadastroContrato.CadastroPagamentoContratual pag_cont : pagamentos_do_contrato_pai) {
									if (pag_cont.getId_pagamento() == id_pagamento_selecionado) {
										vem_de_replica = true;
										break;
									}
								}

								if (vem_de_replica) {
									if (gerenciar.remover_contrato_pagamento_contratual(contrato_local.getId(),
											id_pagamento_selecionado)) {
										JOptionPane.showMessageDialog(isto, "Pagamento Excluido!");

										pesquisar_pagamentos(true);

									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro ao remover o relação do pagamento selecionado\nConsulte o administrador do sistema!");
									}
								} else {
									// apaga totalmente o pagamento
									if (gerenciar.removerPagamentoContratual(contrato_local.getId(),
											id_pagamento_selecionado)) {
										JOptionPane.showMessageDialog(isto, "Pagamento Excluido!");
										pesquisar_pagamentos(true);

									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro ao excluir o pagamento\nConsulte o administrador do sistema!");

									}
								}

							} else {
								if (gerenciar.removerPagamentoContratual(contrato_local.getId(),
										id_pagamento_selecionado)) {
									JOptionPane.showMessageDialog(isto, "Pagamento Excluido!");

									boolean tem_replica = false;
									// verifica se esse pagamento esta replicado
									Map<Integer, Integer> pags = gerenciar.getRelacaoReplica(id_pagamento_selecionado);
									if (pags.size() > 0) {
										// este pagamento esta replicado
										tem_replica = true;

									}

									if (tem_replica) {
										// apagar a relacao
										for (Map.Entry<Integer, Integer> pair : pags.entrySet()) {

											System.out.println(pair.getKey());
											System.out.println(pair.getValue());

											boolean remover_replica = gerenciar
													.removerPagamentoContratual(pair.getKey(), pair.getValue());
											if (remover_replica) {
												JOptionPane.showMessageDialog(isto,
														"A replica deste pagamento foi excluida para manter a coesão das operações");

											} else {
												JOptionPane.showMessageDialog(isto,
														"Erro ao excluir a replica deste pagamento\nBanco de Dados Corrompido\nConsulte o administrador!");
											}

										}

									} else {

									}

									pesquisar_pagamentos(true);

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro ao remover o pagamento selecionado\nConsulte o administrador do sistema!");
								}

							}

						} else {

						}
					}

					else if (descricao.equalsIgnoreCase("-Transferencia")) {
						if (JOptionPane.showConfirmDialog(isto, "Excluir Transferencia?",
								"Deseja excluir essa transferencia?", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							// excluir transferencia
							int id_transferencia_selecionada = Integer
									.parseInt(table_pagamentos_contratuais.getValueAt(indiceDaLinha, 0).toString());
							GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

							if (gerenciar.removerPagamentoContratual(contrato_local.getId(),
									id_transferencia_selecionada)) {
								JOptionPane.showMessageDialog(isto, "Transferencia removida!");
								pesquisar_pagamentos(true);

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao remover a transferencia selecionada\nConsulte o administrador do sistema!");
							}
						} else {

						}
					}

					else {
						JOptionPane.showMessageDialog(isto, "Remova essa transfência no contrato de origem");
					}
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(isto, "Nenhum pagamento selecionado para excluir");

				}

			}
		});

		JButton btnTransferir = new JButton("Transferir");
		panel.add(btnTransferir, "cell 15 0");
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				transferirPagamentoContratual();
			}
		});
		botoes.add(btnTransferir);

		JButton btnEditar_1 = new JButton("Editar");
		btnEditar_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = table_pagamentos_contratuais.getSelectedRow();

				int id_pagamento_selecionado = Integer
						.parseInt(table_pagamentos_contratuais.getValueAt(indiceDaLinha, 0).toString());

				String descricao = table_pagamentos_contratuais.getValueAt(indiceDaLinha, 1).toString();

				if (!descricao.equalsIgnoreCase("+Transferencia") && !descricao.equalsIgnoreCase("-Transferencia")) {

					// editar pagamento
					TelaConfirmarPagamentoContratual tela_confirmar = new TelaConfirmarPagamentoContratual(1,
							new GerenciarBancoContratos().getPagamentoContratual(id_pagamento_selecionado),
							contrato_local, isto);
					tela_confirmar.setTelaPai(isto);
					tela_confirmar.setVisible(true);

				} else if (descricao.equalsIgnoreCase("+Transferencia")) {
					JOptionPane.showMessageDialog(null, "Edite esta transferencia no contrato de origem");
				} else if (descricao.equalsIgnoreCase("-Transferencia")) {

					// TelaConfirmarTransferenciaPagamento confirmar = new
					// TelaConfirmarTransferenciaPagamento();

					TelaConfirmarTransferenciaPagamentoContratual tela = new TelaConfirmarTransferenciaPagamentoContratual(
							1, new GerenciarBancoContratos().getPagamentoContratual(id_pagamento_selecionado),
							contrato_local, isto);
					tela.setTelaPag(isto);
					tela.setVisible(true);
				}

			}
		});
		panel.add(btnEditar_1, "cell 16 0");

		JButton btnAdicionarPagamento = new JButton("Novo Pagamento");
		panel.add(btnAdicionarPagamento, "cell 17 0");
		botoes.add(btnAdicionarPagamento);
		btnAdicionarPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConfirmarPagamentoContratual tela_confirmar = new TelaConfirmarPagamentoContratual(0, null,
						contrato_local, isto);
				tela_confirmar.setTelaPai(isto);
				tela_confirmar.setVisible(true);
			}
		});

		JPanel panel_20 = new JPanel();
		panel_20.setBackground(Color.WHITE);
		panel_4.add(panel_20, "cell 0 10 1 3,alignx center,aligny center");
		panel_20.setLayout(
				new MigLayout("", "[126px][20px][][]", "[21px][10px:n][21px][21px][21px][][][][10px:n][21px]"));

		JLabel lblNewLabel_14 = new JLabel("Valor a Receber");
		lblNewLabel_14.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14, "cell 0 0,alignx right,aligny top");

		lblTotalPagamentos = new JLabel("00");
		panel_20.add(lblTotalPagamentos, "cell 1 0,alignx center,aligny center");
		lblTotalPagamentos.setForeground(Color.BLACK);
		lblTotalPagamentos.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalPagamentos.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_47 = new JLabel("New label");
		lblNewLabel_47.setVisible(false);

		JLabel lblNewLabel_14_1_2_4 = new JLabel("Cobre:");
		lblNewLabel_14_1_2_4.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2_4, "cell 2 0");

		lblCoberturaTotal = new JLabel("00");
		lblCoberturaTotal.setForeground(Color.BLACK);
		lblCoberturaTotal.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaTotal.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaTotal, "cell 3 0");
		panel_20.add(lblNewLabel_47, "cell 0 1,alignx center,aligny center");

		JLabel lblNewLabel_14_1 = new JLabel("Efetuados:");
		lblNewLabel_14_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1, "cell 0 2,alignx right,aligny center");

		lblTotalPagamentosEfetuados = new JLabel("00");
		panel_20.add(lblTotalPagamentosEfetuados, "cell 1 2,alignx center,aligny center");
		lblTotalPagamentosEfetuados.setForeground(Color.BLACK);
		lblTotalPagamentosEfetuados.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalPagamentosEfetuados.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_14_1_2 = new JLabel("Cobre:");
		lblNewLabel_14_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2, "cell 2 2");

		lblCoberturaEfetuados = new JLabel("00");
		lblCoberturaEfetuados.setForeground(Color.BLACK);
		lblCoberturaEfetuados.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaEfetuados.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaEfetuados, "cell 3 2");

		JLabel lblNewLabel_14_1_1 = new JLabel("Transferencias:(-)");
		lblNewLabel_14_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_1, "cell 0 3,alignx right,aligny center");

		lblTotalTransferenciasRetiradas = new JLabel("00");
		panel_20.add(lblTotalTransferenciasRetiradas, "cell 1 3,alignx center,aligny center");
		lblTotalTransferenciasRetiradas.setForeground(Color.BLACK);
		lblTotalTransferenciasRetiradas.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalTransferenciasRetiradas.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_14_1_2_1 = new JLabel("Cobre:");
		lblNewLabel_14_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2_1, "cell 2 3");

		lblCoberturaTransferenciaNegativa = new JLabel("00");
		lblCoberturaTransferenciaNegativa.setForeground(Color.BLACK);
		lblCoberturaTransferenciaNegativa.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaTransferenciaNegativa.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaTransferenciaNegativa, "cell 3 3");

		JLabel lblNewLabel_14_1_1_1 = new JLabel("Transferencias:(+)");
		lblNewLabel_14_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_1_1, "cell 0 4,alignx right,aligny center");

		lblTotalTransferenciasRecebidas = new JLabel("00");
		panel_20.add(lblTotalTransferenciasRecebidas, "cell 1 4,alignx center,aligny center");
		lblTotalTransferenciasRecebidas.setForeground(Color.BLACK);
		lblTotalTransferenciasRecebidas.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalTransferenciasRecebidas.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_14_1_2_2 = new JLabel("Cobre:");
		lblNewLabel_14_1_2_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2_2, "cell 2 4");

		lblCoberturaTransferenciaPositiva = new JLabel("00");
		lblCoberturaTransferenciaPositiva.setForeground(Color.BLACK);
		lblCoberturaTransferenciaPositiva.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaTransferenciaPositiva.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaTransferenciaPositiva, "cell 3 4");

		JLabel lblNewLabel_48 = new JLabel("New label");
		lblNewLabel_48.setVisible(false);
		panel_20.add(lblNewLabel_48, "cell 0 5,alignx center");

		JLabel lblNewLabel_14_1_1_1_2_1 = new JLabel("Comissão:");
		lblNewLabel_14_1_1_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_1_1_2_1, "cell 0 6,alignx right");

		lblTotalComissao = new JLabel("00");
		lblTotalComissao.setForeground(Color.BLACK);
		lblTotalComissao.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalComissao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblTotalComissao, "cell 1 6");

		JLabel lblNewLabel_14_1_2_2_2 = new JLabel("Cobre:");
		lblNewLabel_14_1_2_2_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2_2_2, "cell 2 6");

		lblCoberturaComissao = new JLabel("00");
		lblCoberturaComissao.setForeground(Color.BLACK);
		lblCoberturaComissao.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaComissao.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaComissao, "cell 3 6");

		JLabel lblNewLabel_47_1 = new JLabel("New label");
		lblNewLabel_47_1.setVisible(false);
		panel_20.add(lblNewLabel_47_1, "cell 0 7,alignx center,aligny center");

		JLabel lblNewLabel_14_1_1_1_2 = new JLabel("Concluído:");
		lblNewLabel_14_1_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_1_1_2, "cell 0 8,alignx right");

		lblTotalPagamentosConcuidos = new JLabel("00");
		lblTotalPagamentosConcuidos.setForeground(Color.BLACK);
		lblTotalPagamentosConcuidos.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalPagamentosConcuidos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblTotalPagamentosConcuidos, "cell 1 8");

		JLabel lblNewLabel_14_1_2_2_1 = new JLabel("Cobre:");
		lblNewLabel_14_1_2_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2_2_1, "cell 2 8");

		lblCoberturaConcluida = new JLabel("00");
		lblCoberturaConcluida.setForeground(Color.BLACK);
		lblCoberturaConcluida.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaConcluida.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaConcluida, "cell 3 8");

		JLabel lblNewLabel_14_2 = new JLabel("Restante:");
		lblNewLabel_14_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_2, "cell 0 9,alignx right,aligny center");

		lblTotalPagamentosRestantes = new JLabel("00");
		panel_20.add(lblTotalPagamentosRestantes, "cell 1 9,alignx center,aligny center");
		lblTotalPagamentosRestantes.setForeground(Color.BLACK);
		lblTotalPagamentosRestantes.setFont(new Font("Arial", Font.BOLD, 16));
		lblTotalPagamentosRestantes.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_14_1_2_3 = new JLabel("Cobre:");
		lblNewLabel_14_1_2_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_20.add(lblNewLabel_14_1_2_3, "cell 2 9");

		lblCoberturaRestante = new JLabel("00");
		lblCoberturaRestante.setForeground(Color.BLACK);
		lblCoberturaRestante.setFont(new Font("Arial", Font.BOLD, 16));
		lblCoberturaRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_20.add(lblCoberturaRestante, "cell 3 9");

		JPanel panel_21 = new JPanel();
		panel_21.setBackground(new Color(0, 51, 51));
		panel_21.setForeground(Color.WHITE);
		panel_4.add(panel_21, "cell 4 12 9 1,alignx center,aligny center");
		panel_21.setLayout(new MigLayout("", "[][]", "[][][]"));

		JLabel lblNewLabel_44 = new JLabel("Status do Pagamento");
		lblNewLabel_44.setForeground(Color.WHITE);
		lblNewLabel_44.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_21.add(lblNewLabel_44, "cell 0 0 2 1,alignx center,growy");

		JLabel lblNewLabel_45 = new JLabel("Pagamentos:");
		lblNewLabel_45.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_45.setForeground(Color.WHITE);
		panel_21.add(lblNewLabel_45, "cell 0 1");

		lblStatusPagamento = new JLabel("");
		lblStatusPagamento.setForeground(Color.WHITE);
		lblStatusPagamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_21.add(lblStatusPagamento, "cell 1 1");

		JLabel lblNewLabel_45_1 = new JLabel("Cobertura:");
		lblNewLabel_45_1.setForeground(Color.WHITE);
		lblNewLabel_45_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_21.add(lblNewLabel_45_1, "cell 0 2");

		lblStatusCobertura = new JLabel("");
		lblStatusCobertura.setForeground(Color.WHITE);
		lblStatusCobertura.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_21.add(lblStatusCobertura, "cell 1 2");

		painelListaTarefas.setBackground(new Color(0, 153, 153));

		painelPrincipal.addTab("Lista Tarefas", painelListaTarefas);

		table_tarefas = new JTable(modelo_tarefas);
		table_tarefas.setBackground(Color.WHITE);

		table_tarefas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_tarefas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_tarefas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_tarefas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_tarefas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_tarefas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_tarefas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_tarefas.getColumnModel().getColumn(7).setPreferredWidth(90);
		painelListaTarefas.setLayout(new MigLayout("", "[1108px,grow][12px,grow][90px,grow][11px,grow][89px,grow]",
				"[][351px,grow][28px,grow]"));

		JLabel lblNewLabel_46 = new JLabel("Lista de Tarefas Contratuais:");
		lblNewLabel_46.setForeground(Color.WHITE);
		lblNewLabel_46.setFont(new Font("SansSerif", Font.BOLD, 24));
		painelListaTarefas.add(lblNewLabel_46, "cell 0 0");

		table_tarefas.setRowHeight(30);

		table_tarefas.setBackground(Color.white);
		JScrollPane scrollPaneTarefas = new JScrollPane(table_tarefas);
		scrollPaneTarefas.setOpaque(true);
		scrollPaneTarefas.getViewport().setBackground(Color.white);

		scrollPaneTarefas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setBackground(Color.WHITE);
		scrollPaneTarefas.setAutoscrolls(true);
		scrollPaneTarefas.getViewport().setBackground(Color.white);

		painelListaTarefas.add(scrollPaneTarefas, "cell 0 1 5 1,grow");

		JButton btnAdcionarTarefa = new JButton("Adicionar");
		btnAdcionarTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCriarTarefa tarefa = new TelaCriarTarefa(contrato_local, isto);
				tarefa.setTelaPai(isto);
				tarefa.setVisible(true);

			}
		});
		botoes.add(btnAdcionarTarefa);
		painelListaTarefas.add(btnAdcionarTarefa, "cell 4 2,growx,aligny top");

		JButton btnExcluirTarefa = new JButton("Excluir");
		btnExcluirTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = 0;
				indiceDaLinha = table_tarefas.getSelectedRow();

				CadastroTarefa tarefa_selecionada = lista_tarefas.get(indiceDaLinha);
				JOptionPane.showMessageDialog(isto, "Id da tarefa selecionada: " + tarefa_selecionada.getId_tarefa());

				if (login.getId() == tarefa_selecionada.getCriador().getId()) {
					// verifica se a tarefa ja foi concluida
					if (tarefa_selecionada.getStatus_tarefa() != 1) {
						if (JOptionPane.showConfirmDialog(isto, "Deseja excluir a tarefa selecionada", "Excluir Tarefa",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
							// remover relacao contrato tarefa
							boolean remover_relacao = gerenciar.remover_contrato_tarefa(contrato_local.getId(),
									tarefa_selecionada.getId_tarefa());
							if (remover_relacao) {
								boolean remover_tarefa = gerenciar.remover_tarefa(tarefa_selecionada.getId_tarefa());
								if (remover_tarefa) {
									JOptionPane.showMessageDialog(isto, "Tarefa Excluida com sucesso");
									informar_atualizou();
									getTarefas();

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro ao a tarefa!\nBanco Corrompido\nConsulte o administrador");
									getTarefas();

								}

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao remover tarefa!\nConsulte o administrador");
							}

						} else {

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Uma tarefa já concluida não pode ser excluida");

					}

				} else {
					JOptionPane.showMessageDialog(isto, "Apenas o criador da tarefa pode excluí-la!");
				}

			}
		});
		botoes.add(btnExcluirTarefa);
		painelListaTarefas.add(btnExcluirTarefa, "cell 0 2,alignx right,aligny top");

		JButton btnResponder = new JButton("Responder");
		btnResponder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = table_tarefas.getSelectedRow();

				CadastroTarefa tarefa_selecionada = lista_tarefas.get(indiceDaLinha);

				if (tarefa_selecionada.getExecutor().getId() == login.getId()) {

					if (tarefa_selecionada.getStatus_tarefa() == 2) {

						JOptionPane.showMessageDialog(isto,
								"Uma tarefa após ser respondida não pode ser editada nem excluida!");

						TelaCriarTarefaResposta responder = new TelaCriarTarefaResposta(tarefa_selecionada, isto);
						responder.setTelaPai(isto);
						responder.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(isto, "Tarefa já concluida");

					}

				} else {
					JOptionPane.showMessageDialog(isto, "Você só pode responder tarefas designadas a seu usuário!");

				}

			}
		});
		botoes.add(btnResponder);
		painelListaTarefas.add(btnResponder, "cell 2 2,growx,aligny top");

		painelPrincipal.addTab("Documentos, Distratos, Aditivos e Relatórios", painelComprovantes);

		setPagamentos(contrato);

		if (contrato_local.getSub_contrato() == 1) {
			// é um sub contrato
			lblTipoContrato.setText("Tipo Contrato: Sub-Contrato Proprietario Elaborado");
		} else {
			lblTipoContrato.setText("Tipo Contrato: Original Proprietario Elaborado");

		}

		if (contrato_local.getSub_contrato() == 3) {
			// nao permitir edica
			lblTipoContrato.setText("Tipo Contrato: Original de Terceiros Importado Automaticamente");

			btnEditarContrato.setEnabled(false);
			btnEditarContrato.setVisible(false);

			// desativar aba de subcontratos

		} else if (contrato_local.getSub_contrato() == 4) {
			lblTipoContrato.setText("Tipo Contrato: Original de Terceiros Importado Manualmente");

			btnEditarContrato.setEnabled(false);
			btnEditarContrato.setVisible(false);
		} else if (contrato_local.getSub_contrato() == 5) {
			lblTipoContrato.setText("Tipo Contrato: Original Proprietario Importado Manualmente");

			btnEditarContrato.setEnabled(true);
			btnEditarContrato.setVisible(true);
		} else if (contrato_local.getSub_contrato() == 6) {
			lblTipoContrato.setText("Tipo Contrato: Sub-Contrato Proprietario Importado Manualmente");

			btnEditarContrato.setEnabled(true);
			btnEditarContrato.setVisible(true);
		}

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);
		getTarefas();

		System.out.println("");
		/*****************
		 * chamadas a adicionar
		 */
		setPainelRelatoria();
		criarTabelaAditivos();
		setInformacoesDocumentos();
		setInformacoesAditivos();
		criarTabelaDistratos();
		setInformacoesDistratos();

		boolean iniciar_chamadas = true;
		if (iniciar_chamadas) {
			setarInformacoesPainelPrincipal();
			setarInformacoesExtrasAbaPrincipal();
			// recebimentos
			pesquisar_recebimentos(false);
			setarInformacoesPainelRecebimentos();

			// carregamentos
			pesquisar_carregamentos(false);
			setarInformacoesPainelCarregamentos();

			// pagamentos
			pesquisar_pagamentos(false);

			// subContratos
			setSubContratos(contrato_local);

			if (contrato_local.getSub_contrato() == 0 || contrato_local.getSub_contrato() == 5)
				setarPainelGanhosPotenciais();

			travarContratoSemAtualizar();

			new Thread() {

				@Override
				public void run() {
					while (isto != null) {

						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						GerenciarBancoInformativo gerenciar_informativos = new GerenciarBancoInformativo();
						ArrayList<CadastroInformativo> informativos_novos = gerenciar_informativos.getInformacoes();

						if (informativos_novos.equals(informacoes_atuais)) { // nada atualizado

						} else { // atualizou informacoes informacoes_atuais = informativos_novos;

							for (CadastroInformativo informativo : informacoes_atuais) {
								if (informativo.getId_contrato() == contrato_local.getId()) {
									if (informativo.getId_usuario() != login.getId()) { // há uma nova mensagem para
																						// este
																						// contrato
										if (informativo.getMensagem().equals("contrato foi atualizado")) {
											// atualizar este contrato tambem
											boolean ja_usado = false; // verifica se a hora desse informativo nao esta
																		// registrado ja
											for (String hora : horarios_informativos_ja_executados) {
												if (hora.equals(informativo.getHora_completa())) {
													// ja usado
													ja_usado = true;
													break;

												}
											}

											if (!ja_usado) {
												atualizarContratoAposInformativo();
												horarios_informativos_ja_executados.add(informativo.getHora_completa());
											}
										}
									}

								}

							}

						}

					}

				}
			}.start();
		}

		setContentPane(painelPrincipal);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// this.pack();

		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);
		this.setVisible(true);

	}

	public void getTarefas() {
		modelo_tarefas.setNumRows(0);

		if (lista_tarefas != null) {
			lista_tarefas.clear();
		} else {
			lista_tarefas = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_tarefas = gerenciar.getTarefas(contrato_local.getId());

		/*
		 * modelo_tarefas.addColumn("Id Tarefas"); modelo_tarefas.addColumn("Status");
		 * 
		 * modelo_tarefas.addColumn("Nome"); modelo_tarefas.addColumn("Descrição");
		 * modelo_tarefas.addColumn("Mensagem");
		 * 
		 * modelo_tarefas.addColumn("Data");
		 * 
		 * 
		 * modelo_tarefas.addColumn("Hora"); modelo_tarefas.addColumn("Criador");
		 * 
		 * modelo_tarefas.addColumn("Executor");
		 * 
		 * modelo_tarefas.addColumn("Hora Agendada");
		 * modelo_tarefas.addColumn("Data Agendada");
		 * modelo_tarefas.addColumn("Prioridade");
		 * 
		 */
		for (CadastroContrato.CadastroTarefa tarefa : lista_tarefas) {

			String status_tarefa = "";
			String prioridade = "";
			String resposta = "";

			if (tarefa.getStatus_tarefa() == 1) {
				status_tarefa = "Concluida";
				resposta = tarefa.getResposta();
			} else if (tarefa.getStatus_tarefa() == 2) {
				status_tarefa = "Em Andamento";
				resposta = "Executor da tarefa ainda não respondeu";

			}

			if (tarefa.getPrioridade() == 1) {
				prioridade = "Imediata - Neste Momento";
			} else if (tarefa.getPrioridade() == 2) {
				prioridade = "Urgente - Nesta Hora";
			} else if (tarefa.getPrioridade() == 3) {
				prioridade = "Quanto Antes - Ainda Hoje";
			} else if (tarefa.getPrioridade() == 4) {
				prioridade = "Média - Ainda essa semana";
			} else if (tarefa.getPrioridade() == 5) {
				prioridade = "Leve - Ainda esse mês";
			}

			GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
			CadastroLogin criador = gerenciarUsuarios.getLogin(tarefa.getCriador().getId());
			CadastroLogin executor = gerenciarUsuarios.getLogin(tarefa.getExecutor().getId());

			modelo_tarefas.addRow(new Object[] { tarefa.getId_tarefa(), status_tarefa, tarefa.getNome_tarefa(),
					tarefa.getDescricao_tarefa(), tarefa.getMensagem(), resposta, tarefa.getData(), tarefa.getHora(),
					criador.getNome(), executor.getNome(), tarefa.getHora_agendada(), tarefa.getData_agendada(),
					prioridade

			});
		}

	}

	public void setSubContratos(CadastroContrato contrato_na_funcao) {
		modelo_sub_contratos.setNumRows(0);
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_sub_contratos.clear();

		ArrayList<CadastroContrato> lista_sub_contratos_ = gerenciar.getSubContratos(contrato_na_funcao.getId());

		if (lista_sub_contratos_ != null && lista_sub_contratos_.size() > 0) {
			for (CadastroContrato contrato : lista_sub_contratos_) {
				String cpf, cnpj, nome;

				CadastroCliente compradores[] = contrato.getCompradores();
				CadastroCliente vendedores[] = contrato.getVendedores();
				CadastroCliente corretores[] = contrato.getCorretores();

				String nome_corretores = "";
				String nome_vendedores = "";
				String nome_compradores = "";

				if (corretores[0] != null) {
					if (corretores[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_corretores = corretores[0].getNome_empresarial();
					} else {
						nome_corretores = corretores[0].getNome_fantaia();

					}
				}

				contrato.setNomes_corretores(nome_corretores);

				if (compradores[0] != null) {
					if (compradores[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_compradores = compradores[0].getNome_empresarial();
					} else {
						nome_compradores = compradores[0].getNome_fantaia();

					}
				}

				if (compradores[1] != null) {
					if (compradores[1].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
					} else {
						nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

					}
				}
				contrato.setNomes_compradores(nome_compradores);

				for (CadastroCliente vendedor : contrato.getVendedores()) {
					if (vendedor != null) {
						if (vendedor.getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_vendedores += vendedor.getNome_empresarial();
						} else {
							nome_vendedores += vendedor.getNome_fantaia();

						}
					}
				}
				contrato.setNomes_vendedores(nome_vendedores);

				int status = contrato.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					text_status = "RECOLHER ASSINATURAS".toUpperCase();

				} else if (status == 2) {
					text_status = "Assinado".toUpperCase();

				} else if (status == 3) {
					text_status = "Cumprindo".toUpperCase();

				} else if (status == 0) {
					text_status = "REQUISIÇÃO DE APROVAÇÃO".toUpperCase();

				}

				/*
				 * modelo.addColumn("ID"); modelo.addColumn("Código");
				 * modelo.addColumn("Status");
				 * 
				 * modelo.addColumn("Quantidade"); modelo.addColumn("Medida");
				 * modelo.addColumn("Produto"); modelo.addColumn("Safra");
				 * modelo.addColumn("Valor Produto"); modelo.addColumn("Valor Total");
				 * modelo.addColumn("Vendedores"); modelo.addColumn("Compradores");
				 * modelo.addColumn("Corretores"); modelo.addColumn("Data do Contrato");
				 * 
				 */

				if (contrato.getSub_contrato() == 1 || contrato.getSub_contrato() == 6
						|| contrato.getSub_contrato() == 7) {

					modelo_sub_contratos.addRow(new Object[] { contrato.getId(), contrato.getCodigo(), text_status,
							contrato.getQuantidade(), contrato.getMedida().toUpperCase(),
							contrato.getModelo_safra().getProduto().getNome_produto(),
							contrato.getModelo_safra().getAno_colheita() + "/"
									+ contrato.getModelo_safra().getAno_plantio(),
							"R$ " + contrato.getValor_produto(), "R$ " + contrato.getValor_a_pagar(),
							contrato.getNomes_compradores(), contrato.getNomes_vendedores(),
							contrato.getNomes_corretores(), contrato.getData_contrato()

					});
					lista_sub_contratos.add(contrato);
				}
			}
		}

	}

	public void setPagamentos(CadastroContrato contrato) {
		String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, antecipado, cobre = "", data_pagamento;

		modelo.setNumRows(0);
		float valor_total_pagamentos = 0;

		if (contrato.getPagamentos() != null) {
			for (CadastroContrato.CadastroPagamento pag : contrato.getPagamentos()) {

				ContaBancaria conta_bc = pag.getConta();

				if (conta_bc != null) {
					id = Integer.toString(conta_bc.getId_conta());
					cpf = conta_bc.getCpf_titular();
					banco = conta_bc.getBanco();
					nome = conta_bc.getNome();
					codigo = conta_bc.getCodigo();
					agencia = conta_bc.getAgencia();
					conta = conta_bc.getConta();
				} else {
					id = "00";
					cpf = "Há Informar";
					banco = "Há Informar";
					nome = "Há Informar";
					codigo = "Há Informar";
					agencia = "Há Informar";
					conta = "Há Informar";
				}

				if (pag.getPagamento_adiantado() == 1) {
					antecipado = "SIM";
				} else {
					antecipado = "NÃO";
				}

				cobre = calculoCobertura(Double.parseDouble(pag.getValor().toPlainString()));
				valor_total_pagamentos += Float.parseFloat(pag.getValor_string());
				System.out.println("o valor total agora e: " + valor_total_pagamentos);

				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Float.parseFloat(pag.getValor_string()));
				System.out.println(valorString);

				modelo.addRow(new Object[] { pag.getId(), id, cpf, nome, banco, codigo, agencia, conta, valorString,
						antecipado, cobre, pag.getData_pagamento() });
			}
		}

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		System.out.println(valorString);

		lblValorTotalPagamentos.setText(valorString);

	}

	public void carregarDocumento(String url) {
		// build a controller

		if (controller == null) {

			controller = new SwingController();

			PropertiesManager propriedades = new PropertiesManager(System.getProperties(),
					ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
			// Build a SwingViewFactory configured with the controller

			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR, Boolean.TRUE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR, Boolean.TRUE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, Boolean.FALSE);

			propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);

			factory = new SwingViewBuilder(controller, propriedades);
			// Use the factory to build a JPanel that is pre-configured
			// with a complete, active Viewer UI.

			/*
			 * controller.getDocumentViewController().setAnnotationCallback( new
			 * org.icepdf.ri.common.MyAnnotationCallback(controller.
			 * getDocumentViewController()));
			 */
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (painel_vizualizar == null) {

					painel_vizualizar = new JPanel();

					painel_vizualizar = factory.buildViewerPanel();
					controller.openDocument(url);
					// viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
					// viewerComponentPanel.setMaximumSize(new Dimension(400, 370));

					controller.openDocument(url);
					painel_vizualizar.setBounds(0, 0, painel_vizualizar.getWidth(), painel_vizualizar.getHeight());
					painelVizualizarContrato.add(painel_vizualizar);
				} else {
					controller.openDocument(url);
					painel_vizualizar.repaint();
					painel_vizualizar.updateUI();
					painelVizualizarContrato.add(painel_vizualizar);

				}

			}
		});
	}

	public void fecharDocumento() {

		if (controller != null) {
			controller.closeDocument();
		}

	}

	public void atualizarContratoLocal(boolean informar_atualizacao) {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		contrato_local = gerenciar.getContrato(contrato_local.getId());
		setPagamentos(contrato_local);
		setSubContratos(contrato_local);

		// criar acopia temporaria

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);

		// atualiza os paineis
		setarInformacoesPainelPrincipal();
		setarInformacoesExtrasAbaPrincipal();
		setarInformacoesPainelCarregamentos();
		setarInformacoesPainelRecebimentos();

		// atualiza os subcontratos
		if (contrato_local.getSub_contrato() != 3) {
			setSubContratos(contrato_local);
			atualizarArvoreContratos();
		}
		painel_informacoes_tab_principal.setarInformacoesPainelCarregamentosInformativos();
		painel_informacoes_tab_pagamentos.setarInformacoesPainelCarregamentosInformativos();

		if (informar_atualizacao)
			informar_atualizou();

	}

	public void getDadosGlobais() {

		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public String criarCopiaTemporaria(String url, String codigo) throws IOException {
		ManipularTxt manipular = new ManipularTxt();
		return manipular.copiar(url, codigo);
	}

	/*
	 * 
	 * public void pesquisar_carregamentos() {
	 * 
	 * modelo_carregamentos.onRemoveAll();
	 * 
	 * registro_carregamento_global = new Registros.RegistroCarregamento(); double
	 * valor_total_transferencias_retiradas = 0; double
	 * valor_total_transferencias_recebidas = 0; peso_total_cargas_nf_venda1 = 0.0;
	 * peso_total_cargas_nf_complemento = 0.0;
	 * 
	 * peso_total_cargas = 0.0;
	 * 
	 * if (lista_carregamentos != null) { lista_carregamentos.clear(); } else {
	 * lista_carregamentos = new ArrayList<>(); }
	 * 
	 * if (lista_transferencias_carga_remetente != null) {
	 * lista_transferencias_carga_remetente.clear(); } else {
	 * lista_transferencias_carga_remetente = new ArrayList<>(); }
	 * 
	 * if (lista_transferencias_carga_destinatario != null) {
	 * lista_transferencias_carga_destinatario.clear(); } else {
	 * lista_transferencias_carga_destinatario = new ArrayList<>(); }
	 * 
	 * NumberFormat z = NumberFormat.getNumberInstance();
	 * 
	 * 
	 * GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
	 * lista_carregamentos = gerenciar.getCarregamentos(contrato_local.getId());
	 * 
	 * GerenciarBancoTransferenciasCarga gerenciar_transferencias = new
	 * GerenciarBancoTransferenciasCarga(); lista_transferencias_carga_remetente =
	 * gerenciar_transferencias .getTransferenciasRemetente(contrato_local.getId());
	 * lista_transferencias_carga_destinatario = gerenciar_transferencias
	 * .getTransferenciaDestinatario(contrato_local.getId());
	 * 
	 * 
	 * for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
	 * modelo_carregamentos.onAdd(carregamento);
	 * 
	 * peso_total_cargas = peso_total_cargas + carregamento.getPeso_romaneio();
	 * peso_total_cargas_nf_venda1 = peso_total_cargas_nf_venda1 +
	 * carregamento.getPeso_nf_venda1(); peso_total_cargas_nf_complemento =
	 * peso_total_cargas_nf_complemento + carregamento.getPeso_nf_complemento();
	 * 
	 * }
	 * 
	 * //transferencias como remetente // for
	 * (CadastroContrato.CadastroTransferenciaCarga transferencia :
	 * lista_transferencias_carga_remetente) {
	 * 
	 * GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();
	 * 
	 * 
	 * // pegar o pagamento
	 * 
	 * // pegar data do pagmento String data = transferencia.getData(); double
	 * quantidade = Double.parseDouble(transferencia.getQuantidade());
	 * 
	 * // pegar o destinatario CadastroContrato destinatario = gerenciar_contratos
	 * .getContrato(transferencia.getId_contrato_destinatario());
	 * 
	 * CadastroContrato.Carregamento carga_transferencia = new
	 * CadastroContrato.Carregamento();
	 * carga_transferencia.setId_carregamento(transferencia.getId_transferencia());
	 * String texto_obs = "Transferencia negativa de " + z.format(quantidade) +
	 * " kgs enviados ao contrato " + transferencia.getId_contrato_destinatario() ;
	 * carga_transferencia.setObservacao(texto_obs);
	 * carga_transferencia.setDescricao("-Transferencia");
	 * carga_transferencia.setPeso_real_carga(quantidade);
	 * carga_transferencia.setId_contrato(transferencia.getId_contrato_destinatario(
	 * )); carga_transferencia.setData(data);
	 * carga_transferencia.setPeso_romaneio(quantidade);
	 * carga_transferencia.setId_produto(destinatario.getModelo_produto().
	 * getId_produto()); carga_transferencia.setPeso_nf_venda1(0);
	 * carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
	 * carga_transferencia.setPeso_nf_interna(0);
	 * carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
	 * carga_transferencia.setValor_nf_complemento(BigDecimal.ZERO);
	 * carga_transferencia.setPeso_nf_complemento(0);
	 * 
	 * 
	 * modelo_carregamentos.onAdd(carga_transferencia);
	 * 
	 * 
	 * 
	 * valor_total_transferencias_retiradas += quantidade; //
	 * valor_total_pagamentos_efetuados -= valor_pagamento;
	 * 
	 * } // transferencias como destinatario//
	 * 
	 * for (CadastroContrato.CadastroTransferenciaCarga transferencia :
	 * lista_transferencias_carga_destinatario) { GerenciarBancoContratos
	 * gerenciar_contratos = new GerenciarBancoContratos();
	 * 
	 * 
	 * // pegar data do pagmento String data = transferencia.getData(); double
	 * quantidade = Double.parseDouble(transferencia.getQuantidade());
	 * 
	 * // pegar o destinatario CadastroContrato remetente =
	 * gerenciar_contratos.getContrato(transferencia.getId_contrato_remetente()); //
	 * pegar o destinatario CadastroContrato destinatario = gerenciar_contratos
	 * .getContrato(transferencia.getId_contrato_destinatario());
	 * 
	 * CadastroContrato.Carregamento carga_transferencia = new
	 * CadastroContrato.Carregamento();
	 * carga_transferencia.setId_carregamento(transferencia.getId_transferencia());
	 * String texto_obs = "Transferencia positiva de " + z.format(quantidade) +
	 * " kgs oriundas do contrato " + transferencia.getId_contrato_remetente() ;
	 * carga_transferencia.setObservacao(texto_obs);
	 * carga_transferencia.setDescricao("+Transferencia");
	 * carga_transferencia.setPeso_real_carga(quantidade);
	 * carga_transferencia.setId_contrato(transferencia.getId_contrato_remetente());
	 * carga_transferencia.setData(data);
	 * carga_transferencia.setPeso_romaneio(quantidade);
	 * carga_transferencia.setId_produto(destinatario.getModelo_produto().
	 * getId_produto()); carga_transferencia.setPeso_nf_venda1(0);
	 * carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
	 * carga_transferencia.setPeso_nf_interna(0);
	 * carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
	 * carga_transferencia.setValor_nf_complemento(BigDecimal.ZERO);
	 * carga_transferencia.setPeso_nf_complemento(0);
	 * 
	 * 
	 * modelo_carregamentos.onAdd(carga_transferencia);
	 * 
	 * valor_total_transferencias_recebidas += quantidade; //
	 * valor_total_pagamentos_efetuados += valor_pagamento;
	 * 
	 * }
	 * 
	 * 
	 * double peso_total_kg = 0, peso_total_sacos = 0, peso_carregado_kg = 0,
	 * peso_carregado_sacos = 0, peso_restante_kg = 0, peso_restante_sacos = 0;
	 * 
	 * 
	 * if (contrato_local.getMedida().equals("KG")) { peso_total_kg =
	 * contrato_local.getQuantidade(); peso_total_sacos = peso_total_kg / 60;
	 * 
	 * peso_carregado_kg = peso_total_cargas; peso_carregado_sacos =
	 * peso_total_cargas / 60;
	 * 
	 * peso_restante_kg = peso_total_kg - peso_carregado_kg; peso_restante_sacos =
	 * peso_total_sacos - peso_carregado_sacos;
	 * 
	 * } else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
	 * peso_total_sacos = contrato_local.getQuantidade(); peso_total_kg =
	 * peso_total_sacos * 60;
	 * 
	 * peso_carregado_sacos = peso_total_cargas / 60 +
	 * (valor_total_transferencias_recebidas/60) -
	 * (valor_total_transferencias_retiradas/60) ; peso_carregado_kg =
	 * peso_total_cargas + valor_total_transferencias_recebidas -
	 * valor_total_transferencias_retiradas;
	 * 
	 * peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;
	 * peso_restante_kg = peso_total_kg - peso_carregado_kg;
	 * 
	 * }
	 * 
	 * //retiradas
	 * 
	 * lblTotalTransferenciasCargaRetiradas.setText(z.format(
	 * valor_total_transferencias_retiradas) + " Kg " + " | " +
	 * z.format(valor_total_transferencias_retiradas/60) + " Sacos");
	 * 
	 * //recebidas
	 * 
	 * lblTotalTransferenciasCargaRecebidas.setText(z.format(
	 * valor_total_transferencias_recebidas) + " Kg " + " | " +
	 * z.format(valor_total_transferencias_recebidas/60) + " Sacos");
	 * 
	 * //peso total do contrato lblPesoTotal.setText(z.format(peso_total_kg) +
	 * " Kg " + " | " + z.format(peso_total_sacos) + " Sacos");
	 * registro_carregamento_global.setQuantidade_total(peso_total_sacos);
	 * 
	 * // peso total das carregado lblPesoTotalRealCargas
	 * .setText(z.format(peso_carregado_kg) + " Kg" + " | " +
	 * z.format(peso_carregado_sacos) + " Sacos"); // peso total restante
	 * lblPesoTotalRealRestante .setText(z.format(peso_restante_kg) + " Kg" + " | "
	 * + z.format(peso_restante_sacos) + " Sacos");
	 * registro_carregamento_global.setQuantidade_restante(peso_restante_sacos);
	 * 
	 * double peso_total_nf_kg = peso_total_cargas_nf_venda1; double
	 * peso_total_nf_sacos = peso_total_nf_kg / 60;
	 * 
	 * double peso_total_nf_emitidas_kg = peso_total_cargas_nf_complemento; double
	 * peso_total_nf_emitidas_sacos = peso_total_nf_emitidas_kg / 60;
	 * 
	 * double peso_total_nf_kg_restante = peso_total_kg - (peso_total_nf_kg +
	 * peso_total_nf_emitidas_kg); double peso_total_nf_sacos_restante =
	 * peso_total_sacos - (peso_total_nf_sacos + peso_total_nf_emitidas_sacos);
	 * 
	 * lblPesoTotalNotasFiscais .setText(z.format(peso_total_kg) + " Kg" + " | " +
	 * z.format(peso_total_sacos) + " Sacos");
	 * registro_carregamento_global.setQuantidade_total_nf(peso_total_sacos);
	 * 
	 * lblPesoTotalNotasFiscaisEmitidas.setText(z.format(peso_total_nf_kg +
	 * peso_total_nf_emitidas_kg) + " Kg" + " | " + z.format(peso_total_nf_sacos +
	 * peso_total_nf_emitidas_sacos) + " Sacos");
	 * 
	 * lblPesoTotalNotasFiscaisaEmitir.setText(z.format(peso_total_nf_kg_restante) +
	 * " Kg" + " | " + z.format(peso_total_nf_sacos_restante) + " Sacos");
	 * registro_carregamento_global.setQuantidade_restante_nf(
	 * peso_total_nf_sacos_restante);
	 * 
	 * int n1 = (int) peso_total_sacos; int n2 = ((int) peso_carregado_sacos);
	 * 
	 * atualizarGraficoContratos(n1, n2);
	 * 
	 * int n3 = (int) peso_total_sacos; int n4 = n3 - ((int)
	 * peso_total_nf_sacos_restante);
	 * 
	 * atualizarGraficoNFs(n3, n4);
	 * 
	 * }
	 */

	public void informar_atualizou() {
		GerenciarBancoInformativo gerenciar = new GerenciarBancoInformativo();
		CadastroInformativo informativo = new CadastroInformativo();
		informativo.setId_contrato(contrato_local.getId());
		informativo.setId_usuario(login.getId());
		informativo.setMensagem("contrato foi atualizado");
		informativo.setHora_completa(new GetData().getHora());

		boolean informou = gerenciar.inserirInformativo(informativo);
	}

	public void pesquisar_carregamentos(boolean informar_atualizacao) {

		modelo_carregamentos.onRemoveAll();

		registro_carregamento_global = new Registros.RegistroCarregamento();
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		peso_total_cargas_nf_venda1 = 0.0;
		peso_total_cargas_nf_complemento = 0.0;
		peso_total_cargas_nf_interna = 0.0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;

		peso_total_cargas = 0.0;

		if (lista_carregamentos != null) {
			lista_carregamentos.clear();
		} else {
			lista_carregamentos = new ArrayList<>();
		}

		if (lista_transferencias_carga_remetente != null) {
			lista_transferencias_carga_remetente.clear();
		} else {
			lista_transferencias_carga_remetente = new ArrayList<>();
		}

		if (lista_transferencias_carga_destinatario != null) {
			lista_transferencias_carga_destinatario.clear();
		} else {
			lista_transferencias_carga_destinatario = new ArrayList<>();
		}

		NumberFormat z = NumberFormat.getNumberInstance();

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_carregamentos = gerenciar.getCarregamentos(contrato_local.getId());

		GerenciarBancoTransferenciasCarga gerenciar_transferencias = new GerenciarBancoTransferenciasCarga();
		lista_transferencias_carga_remetente = gerenciar_transferencias
				.getTransferenciasRemetente(contrato_local.getId());
		lista_transferencias_carga_destinatario = gerenciar_transferencias
				.getTransferenciaDestinatario(contrato_local.getId());

		boolean nf_interna_ativo = false;
		boolean nf_venda_ativo = false;
		boolean nf_complemento_ativo = false;

		// checka se ha no minimo uma nf interna aplicavel
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
			if (carregamento.getNf_interna_aplicavel() == 1) {
				nf_interna_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
			if (carregamento.getNf_venda1_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf complemento aplicavel
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
			if (carregamento.getNf_complemento_aplicavel() == 1) {
				nf_complemento_ativo = true;
				break;
			}

		}
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {
			modelo_carregamentos.onAdd(carregamento);

			peso_total_cargas = peso_total_cargas + carregamento.getPeso_romaneio();
			peso_total_cargas_nf_venda1 = peso_total_cargas_nf_venda1 + carregamento.getPeso_nf_venda1();
			peso_total_cargas_nf_complemento = peso_total_cargas_nf_complemento + carregamento.getPeso_nf_complemento();
			peso_total_cargas_nf_interna = peso_total_cargas_nf_interna + carregamento.getPeso_nf_interna();
			valor_total_nf_venda = valor_total_nf_venda.add(carregamento.getValor_nf_venda1());
			valor_total_nf_venda = valor_total_nf_venda.add(carregamento.getValor_nf_complemento());

		}

		// transferencias como remetente //
		for (CadastroContrato.CadastroTransferenciaCarga transferencia : lista_transferencias_carga_remetente) {

			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

			// pegar o pagamento

			// pegar data do pagmento
			String data = transferencia.getData();
			double quantidade = Double.parseDouble(transferencia.getQuantidade());

			// pegar o destinatario
			// CadastroContrato destinatario = gerenciar_contratos
			// .getContrato(transferencia.getId_contrato_destinatario());

			CadastroContrato.Carregamento carga_transferencia = new CadastroContrato.Carregamento();
			carga_transferencia.setId_carregamento(transferencia.getId_transferencia());
			String texto_obs = "Transferencia negativa de " + z.format(quantidade) + " kgs enviados ao contrato "
					+ transferencia.getCodigo_destinatario();
			carga_transferencia.setObservacao(texto_obs);
			carga_transferencia.setCodigo_contrato(transferencia.getCodigo_destinatario());
			carga_transferencia.setDescricao("-Transferencia");
			carga_transferencia.setPeso_real_carga(quantidade);
			carga_transferencia.setId_contrato(transferencia.getId_contrato_destinatario());
			carga_transferencia.setData(data);
			carga_transferencia.setPeso_romaneio(quantidade);
			// carga_transferencia.setId_produto(destinatario.getModelo_produto().getId_produto());
			carga_transferencia.setPeso_nf_venda1(0);
			carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
			carga_transferencia.setPeso_nf_interna(0);
			carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
			carga_transferencia.setValor_nf_complemento(BigDecimal.ZERO);
			carga_transferencia.setPeso_nf_complemento(0);

			modelo_carregamentos.onAdd(carga_transferencia);
			lista_carregamentos.add(carga_transferencia);

			valor_total_transferencias_retiradas += quantidade;
			// valor_total_pagamentos_efetuados -= valor_pagamento;

		}
		// transferencias como destinatario//

		for (CadastroContrato.CadastroTransferenciaCarga transferencia : lista_transferencias_carga_destinatario) {
			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

			// pegar data do pagmento
			String data = transferencia.getData();
			double quantidade = Double.parseDouble(transferencia.getQuantidade());

			CadastroContrato.Carregamento carga_transferencia = new CadastroContrato.Carregamento();
			carga_transferencia.setId_carregamento(transferencia.getId_transferencia());
			String texto_obs = "Transferencia positiva de " + z.format(quantidade) + " kgs oriundas do contrato "
					+ transferencia.getCodigo_remetente();
			carga_transferencia.setObservacao(texto_obs);
			carga_transferencia.setCodigo_contrato(transferencia.getCodigo_remetente());

			carga_transferencia.setDescricao("+Transferencia");
			carga_transferencia.setPeso_real_carga(quantidade);
			carga_transferencia.setId_contrato(transferencia.getId_contrato_remetente());
			carga_transferencia.setData(data);
			carga_transferencia.setPeso_romaneio(quantidade);
			// carga_transferencia.setId_produto(destinatario.getModelo_produto().getId_produto());
			carga_transferencia.setPeso_nf_venda1(0);
			carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
			carga_transferencia.setPeso_nf_interna(0);
			carga_transferencia.setValor_nf_venda1(BigDecimal.ZERO);
			carga_transferencia.setValor_nf_complemento(BigDecimal.ZERO);
			carga_transferencia.setPeso_nf_complemento(0);

			modelo_carregamentos.onAdd(carga_transferencia);
			lista_carregamentos.add(carga_transferencia);

			valor_total_transferencias_recebidas += quantidade;
			// valor_total_pagamentos_efetuados += valor_pagamento;

		}

		double peso_total_kg = peso_total_recebido, peso_total_sacos = peso_total_recebido / 60, peso_carregado_kg = 0,
				peso_carregado_sacos = 0, peso_restante_kg = 0, peso_restante_sacos = 0;

		peso_carregado_sacos = peso_total_cargas / 60 + (valor_total_transferencias_recebidas / 60)
				- (valor_total_transferencias_retiradas / 60);
		peso_carregado_kg = peso_total_cargas + valor_total_transferencias_recebidas
				- valor_total_transferencias_retiradas;

		peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;
		peso_restante_kg = peso_total_kg - peso_carregado_kg;

		// retiradas

		lblTotalTransferenciasCargaRetiradas.setText(z.format(valor_total_transferencias_retiradas) + " Kg " + " | "
				+ z.format(valor_total_transferencias_retiradas / 60) + " Sacos");

		// recebidas

		lblTotalTransferenciasCargaRecebidas.setText(z.format(valor_total_transferencias_recebidas) + " Kg " + " | "
				+ z.format(valor_total_transferencias_recebidas / 60) + " Sacos");

		// peso total do contrato
		lblPesoTotal.setText(z.format(peso_total_kg) + " Kg " + " | " + z.format(peso_total_sacos) + " Sacos");
		registro_carregamento_global.setQuantidade_total(peso_total_sacos);

		// peso total das carregado
		lblPesoTotalRealCargas
				.setText(z.format(peso_carregado_kg) + " Kg" + " | " + z.format(peso_carregado_sacos) + " Sacos");
		// peso total restante
		lblPesoTotalRealRestante
				.setText(z.format(peso_restante_kg) + " Kg" + " | " + z.format(peso_restante_sacos) + " Sacos");
		registro_carregamento_global.setQuantidade_restante(peso_restante_sacos);

		double peso_total_nf_kg = peso_total_cargas_nf_venda1;
		double peso_total_nf_sacos = peso_total_nf_kg / 60;

		double peso_total_nf_emitidas_kg = peso_total_cargas_nf_complemento;
		double peso_total_nf_emitidas_sacos = peso_total_nf_emitidas_kg / 60;

		double peso_total_nf_kg_restante = peso_total_kg - (peso_total_nf_kg + peso_total_nf_emitidas_kg);
		double peso_total_nf_sacos_restante = peso_total_sacos - (peso_total_nf_sacos + peso_total_nf_emitidas_sacos);

		registro_carregamento_global.setQuantidade_restante_nf(peso_total_nf_sacos_restante);

		Locale ptBr = new Locale("pt", "BR");

		// atualiza os paineis
		// romaneios
		lblNumRomaneiosCarregamento.setText(lista_carregamentos.size() + "");
		// nfs internas
		if (nf_interna_ativo) {
			lblPesoTotalNFInternaSaida.setText(z.format(peso_total_cargas_nf_interna) + " Kgs | "
					+ z.format(peso_total_cargas_nf_interna / 60) + " sacos");
			lblPesoRestanteNFInternaSaida.setText(z.format(peso_carregado_kg - peso_total_cargas_nf_interna) + " Kgs | "
					+ z.format((peso_carregado_kg - peso_total_cargas_nf_interna) / 60) + " sacos");
		} else {
			lblPesoTotalNFInternaSaida.setText("Não Aplicável");
			lblPesoRestanteNFInternaSaida.setText("Não Aplicável");

		}
		// nfs venda
		if (nf_venda_ativo || nf_complemento_ativo) {
			lblPesoTotalNFVendaSaida.setText(z.format(peso_total_nf_kg + peso_total_nf_emitidas_kg) + " Kgs | "
					+ z.format((peso_total_nf_kg + peso_total_nf_emitidas_kg) / 60) + " sacos");
			lblPesoRestanteNFVendaSaida
					.setText(z.format(peso_carregado_kg - (peso_total_nf_kg + peso_total_nf_emitidas_kg)) + " Kgs | "
							+ z.format((peso_carregado_kg - ((peso_total_nf_kg + peso_total_nf_emitidas_kg))) / 60)
							+ " sacos");
			lblValorTotalNFVendaSaida.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda));
		} else {
			lblPesoTotalNFVendaSaida.setText("Não Aplicável");
			lblPesoRestanteNFVendaSaida.setText("Não Aplicável");
			lblValorTotalNFVendaSaida.setText("Não Aplicável");

		}

		// atualizar status
		String texto = "";
		double diferenca = peso_total_kg - peso_carregado_kg;
		if (diferenca == 0) {
			texto = texto + "Carregamento Concluído";
		} else if (diferenca > 0) {
			texto = texto + "Carregamento Incompleto, falta carregar " + z.format(diferenca) + " Kgs";

		} else if (diferenca < 0) {
			texto = texto + "Carregamento Excedido, excedeu " + z.format(diferenca) + " Kgs";

		}

		lblStatusCarregamentoSaida.setText(texto);

		String texto_nf_remessa = "";
		double diferenca_nf_remessa = peso_carregado_kg - peso_total_cargas_nf_interna;
		if (diferenca_nf_remessa == 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Concluído";
		} else if (diferenca_nf_remessa > 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna Incompleto, falta emitir "
					+ z.format(diferenca_nf_remessa) + " Kgs";

		} else if (diferenca_nf_remessa < 0) {
			texto_nf_remessa = texto_nf_remessa + "Emissão de NF's Interna, excedeu " + z.format(diferenca_nf_remessa)
					+ " Kgs";

		}

		if (nf_interna_ativo)
			lblStatusNFRemessaSaida.setText(texto_nf_remessa);
		else
			lblStatusNFRemessaSaida.setText("Emissão de NF's Interna Não Aplicável");

		// status de nf de venda

		String texto_nf_venda = "";
		double diferenca_nf_venda = peso_carregado_kg - (peso_total_nf_kg + peso_total_nf_emitidas_kg);
		if (diferenca_nf_venda == 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Concluído";
		} else if (diferenca_nf_venda > 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's de Venda Incompleto, falta emitir "
					+ z.format(diferenca_nf_venda) + " Kgs";

		} else if (diferenca_nf_venda < 0) {
			texto_nf_venda = texto_nf_venda + "Emissão de NF's Excedido, excedeu " + z.format(diferenca_nf_venda)
					+ " Kgs";

		}
		if (nf_venda_ativo || nf_complemento_ativo)
			lblStatusNFVendaSaida.setText(texto_nf_venda);
		else
			lblStatusNFVendaSaida.setText("Emissão de NF's de Venda Não Aplicável");

		///////////////////////////////////////

		int n1 = (int) peso_total_sacos;
		int n2 = ((int) peso_carregado_sacos);

		atualizarGraficoContratos(n1, n2);

		if (informar_atualizacao) {
			informar_atualizou();
		}

	}

	public void pesquisar_recebimentos(boolean informar_atualizacao) {

		registro_recebimento_global = new Registros.RegistroRecebimento();
		ArrayList<CadastroNFe> notas_fiscais_carregamentos = new ArrayList<>();
		modelo_recebimentos.onRemoveAll();
		int num_total_romaneios = 0;

		NumberFormat z = NumberFormat.getNumberInstance();

		peso_total_recebido = 0;
		double peso_total_a_receber = 0;
		double peso_total_restante = 0;

		double peso_total_nf_venda_kgs = 0;
		double peso_total_nf_venda_sacos = 0;

		double peso_total_nf_remessa_kgs = 0;
		double peso_total_nf_remessa_sacos = 0;

		BigDecimal valor_total_nf_venda = BigDecimal.ZERO;
		BigDecimal valor_total_nf_remessa = BigDecimal.ZERO;

		peso_total_cargas = 0.0;

		if (lista_recebimentos != null) {
			lista_recebimentos.clear();
		} else {
			lista_recebimentos = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_recebimentos = gerenciar.getRecebimentos(contrato_local.getId());

		GerenciarBancoTransferenciaRecebimento gerenciar_replicacoes_recebimento = new GerenciarBancoTransferenciaRecebimento();
		lista_replicacoes_recebimento = gerenciar_replicacoes_recebimento
				.getTransferenciaDestinatario(contrato_local.getId());

		boolean nf_remessa_ativo = false;
		boolean nf_venda_ativo = false;

		// checka se ha no minimo uma nf remessa aplicavel
		for (CadastroContrato.Recebimento recebimento : lista_recebimentos) {
			if (recebimento.getNf_remessa_aplicavel() == 1) {
				nf_remessa_ativo = true;
				break;
			}

		}

		// checka se ha no minimo uma nf venda aplicavel
		for (CadastroContrato.Recebimento recebimento : lista_recebimentos) {
			if (recebimento.getNf_venda_aplicavel() == 1) {
				nf_venda_ativo = true;
				break;
			}

		}

		for (CadastroContrato.Recebimento recebimento : lista_recebimentos) {

			modelo_recebimentos.onAdd(recebimento);
			peso_total_recebido = peso_total_recebido + recebimento.getPeso_romaneio();
			num_total_romaneios++;

			if (recebimento.getNf_venda_aplicavel() == 1) {
				peso_total_nf_venda_kgs = peso_total_nf_venda_kgs + recebimento.getPeso_nf_venda();

				valor_total_nf_venda = valor_total_nf_venda.add(recebimento.getValor_nf_venda());

			}

			if (recebimento.getNf_remessa_aplicavel() == 1) {

				peso_total_nf_remessa_kgs = peso_total_nf_remessa_kgs + recebimento.getPeso_nf_remessa();

				valor_total_nf_remessa = valor_total_nf_remessa.add(recebimento.getValor_nf_remessa());

			}

		}

		for (CadastroContrato.CadastroTransferenciaRecebimento replica : lista_replicacoes_recebimento) {

			CadastroContrato.Recebimento recebimento = new CadastroContrato.Recebimento();
			recebimento.setId_recebimento(replica.getId_transferencia());
			recebimento.setNf_remessa_aplicavel(0);
			recebimento.setNf_venda_aplicavel(0);
			recebimento.setPeso_romaneio(replica.getQuantidade());
			recebimento.setCodigo_romaneio("Replica");
			recebimento.setData_recebimento(replica.getData());
			lista_recebimentos.add(recebimento);

			modelo_recebimentos.onAdd(recebimento);
			peso_total_recebido = peso_total_recebido + replica.getQuantidade();

		}

		double peso_total_contrato_scs = 0;
		double peso_total_contrato_kgs = 0;

		if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			peso_total_contrato_kgs = contrato_local.getQuantidade();
			peso_total_contrato_scs = peso_total_contrato_scs / 60;

		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			peso_total_contrato_scs = contrato_local.getQuantidade();
			peso_total_contrato_kgs = peso_total_contrato_scs * 60;
		}

		lblPesoTotalContrato.setText(
				z.format(peso_total_contrato_kgs) + " KGs" + " | " + z.format(peso_total_contrato_scs) + " sacos");
		lblPesoTotalRecebido.setText(
				z.format(peso_total_recebido) + " KGs" + " | " + z.format(peso_total_recebido / 60) + " sacos");
		lblPesoTotalRestante.setText(z.format(peso_total_contrato_kgs - peso_total_recebido) + " KGs | "
				+ z.format((peso_total_contrato_kgs - peso_total_recebido) / 60) + " sacos");

		lblNumRomaneiosRecebimento.setText(num_total_romaneios + "");
		lblTotalSacosKGsRomaneios.setText(
				z.format(peso_total_recebido) + " KGs" + " | " + z.format(peso_total_recebido / 60) + " sacos");

		String texto = "";
		double diferenca = peso_total_contrato_kgs - peso_total_recebido;
		if (diferenca == 0) {
			texto = texto + "Concluído";
		} else if (diferenca > 0) {
			texto = texto + "Falta receber " + z.format(diferenca) + " Kgs";

		} else if (diferenca < 0) {
			texto = texto + "Excedeu " + z.format(diferenca) + " Kgs";

		}

		lblStatusRecebimentoEntrada.setText(texto);

		Locale ptBr = new Locale("pt", "BR");

		if (nf_venda_ativo) {
			// pesos nf venda

			lblPesoTotalNFVendaEntrada.setText(z.format(peso_total_nf_venda_kgs) + " KGs" + " | "
					+ z.format(peso_total_nf_venda_kgs / 60) + " sacos");
			lblPesoRestanteNFVendaEntrada.setText(z.format(peso_total_contrato_kgs - peso_total_nf_venda_kgs) + " KGs"
					+ " | " + z.format((peso_total_contrato_kgs - peso_total_nf_venda_kgs) / 60) + " sacos");

			// valores nf venda

			String valor_nf_venda = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_nf_venda.doubleValue());

			lblValorTotalNFVendaEntrada.setText(valor_nf_venda);

			// status de nf de venda

			String texto_nf_venda = "";
			double diferenca_nf_venda = peso_total_contrato_kgs - peso_total_nf_venda_kgs;
			if (diferenca_nf_venda == 0) {
				texto_nf_venda = texto_nf_venda + "Concluído";
			} else if (diferenca_nf_venda > 0) {
				texto_nf_venda = texto_nf_venda + "Falta emitir " + z.format(diferenca_nf_venda) + " Kgs";

			} else if (diferenca_nf_venda < 0) {
				texto_nf_venda = texto_nf_venda + "Excedeu " + z.format(diferenca_nf_venda) + " Kgs";

			}

			lblStatusNFVendaEntrada.setText(texto_nf_venda);
		} else {
			lblPesoTotalNFVendaEntrada.setText("Não Aplicável");
			lblPesoRestanteNFVendaEntrada.setText("Não Aplicável");
			lblValorTotalNFVendaEntrada.setText("Não Aplicável");
			lblStatusNFVendaEntrada.setText("Não Aplicável");
		}

		if (nf_remessa_ativo) {
			// pessos nf remessa
			lblPesoTotalNFRemessaEntrada.setText(z.format(peso_total_nf_remessa_kgs) + " KGs" + " | "
					+ z.format(peso_total_nf_remessa_kgs / 60) + " sacos");
			lblPesoRestanteNFRemessaEntrada.setText(z.format(peso_total_contrato_kgs - peso_total_nf_remessa_kgs)
					+ " KGs" + " | " + z.format((peso_total_contrato_kgs - peso_total_nf_remessa_kgs) / 60) + " sacos");

			// valores nf remessa
			String valor_nf_remessa = NumberFormat.getCurrencyInstance(ptBr)
					.format(valor_total_nf_remessa.doubleValue());
			lblValorTotalNFRemessaEntrada.setText(valor_nf_remessa);

			// atualizar status

			// status nf remessa

			String texto_nf_remessa = "";
			double diferenca_nf_remessa = peso_total_contrato_kgs - peso_total_nf_remessa_kgs;
			if (diferenca_nf_remessa == 0) {
				texto_nf_remessa = texto_nf_remessa + "Concluído";
			} else if (diferenca_nf_remessa > 0) {
				texto_nf_remessa = texto_nf_remessa + "Falta emitir " + z.format(diferenca_nf_remessa) + " Kgs";

			} else if (diferenca_nf_remessa < 0) {
				texto_nf_remessa = texto_nf_remessa + "Excedeu " + z.format(diferenca_nf_remessa) + " Kgs";

			}

			lblStatusNFRemessaEntrada.setText(texto_nf_remessa);
		} else {
			lblPesoTotalNFRemessaEntrada.setText("Não Aplicável");
			lblPesoRestanteNFRemessaEntrada.setText("Não Aplicável");
			lblValorTotalNFRemessaEntrada.setText("Não Aplicável");
			lblStatusNFRemessaEntrada.setText("Não Aplicável");
		}

//////////

		int n1 = (int) peso_total_contrato_scs;
		int n2 = ((int) peso_total_recebido / 60);

		atualizarGraficoRecebimento(n1, n2);

		if (informar_atualizacao) {
			informar_atualizou();
		}

	}

	public static boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void pesquisar_pagamentos(boolean informar_atualizacao) {
		NumberFormat z = NumberFormat.getNumberInstance();
		Locale ptBr = new Locale("pt", "BR");
		GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

		modelo_pagamentos.onRemoveAll();

		registro_pagamento_global = new Registros.RegistroPagamento();
		double valor_total_pagamentos = 0;
		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;
		double valor_total_comissao = 0;
		double peso_total_cobertura = 0;
		double peso_total_cobertura_efetuados = 0;
		double peso_total_cobertura_transferencia_negativa = 0;
		double peso_total_cobertura_transferencia_positiva = 0;
		double peso_total_cobertura_restante = 0;
		double peso_total_cobertura_comissao = 0;

		double quantidade_total_contrato_sacos = 0;
		double valor_por_saco = 0;

		if (contrato_local.getMedida().equalsIgnoreCase("Kg")) {
			quantidade_total_contrato_sacos = contrato_local.getQuantidade() / 60;
			valor_por_saco = contrato_local.getValor_produto() * 60;
		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_total_contrato_sacos = contrato_local.getQuantidade();
			valor_por_saco = contrato_local.getValor_produto();
		}

		// valor total e cobertura com base no total de sacos recebidos
		double quantidade_kgs_recebidos = gerenciar_contratos.getQuantidadeRecebida(contrato_local.getId());
		peso_total_cobertura = quantidade_kgs_recebidos / 60;
		valor_total_pagamentos = peso_total_cobertura * valor_por_saco;

		String valorSaco = NumberFormat.getCurrencyInstance(ptBr).format(valor_por_saco);

		if (lista_pagamentos_contratuais != null) {
			lista_pagamentos_contratuais.clear();
		} else {
			lista_pagamentos_contratuais = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_pagamentos_contratuais = gerenciar.getPagamentosContratuaisParaRelatorio(contrato_local.getId());

		for (PagamentoCompleto pagamento : lista_pagamentos_contratuais) {

			double valor_pagamento = pagamento.getValor_pagamento();

			modelo_pagamentos.onAdd(pagamento);

			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);
			double cobertura = valor_pagamento / valor_por_saco;

			if (pagamento.getTipo() == 1) {
				valor_total_pagamentos_efetuados += valor_pagamento;
				peso_total_cobertura_efetuados += cobertura;
			} else if (pagamento.getTipo() == 2) {
				// é uma comissao
				valor_total_comissao += valor_pagamento;
				peso_total_cobertura_comissao += cobertura;
			} else if (pagamento.getTipo() == 3) {

				// é uma transferencia
				if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
					// é uma transferencia negativa
					valor_total_transferencias_retiradas += valor_pagamento;
					peso_total_cobertura_transferencia_negativa += cobertura;
				} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
					// é uma transferencia positiva
					valor_total_transferencias_recebidas += valor_pagamento;
					peso_total_cobertura_transferencia_positiva += cobertura;
				}

			}

		}

		String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);

		lblTotalPagamentos.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);

		lblTotalPagamentosEfetuados.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);

		lblTotalTransferenciasRetiradas.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);

		lblTotalTransferenciasRecebidas.setText(valor);

		double valor_total_pagamentos_concluidos = valor_total_pagamentos_efetuados
				- valor_total_transferencias_retiradas + valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_concluidos);

		lblTotalPagamentosConcuidos.setText(valor);

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);

		lblTotalPagamentosRestantes.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissao);

		lblTotalComissao.setText(valor);

		lblCoberturaTotal.setText(z.format(peso_total_cobertura) + " sacos");
		lblCoberturaEfetuados.setText(z.format(peso_total_cobertura_efetuados) + " sacos");
		lblCoberturaTransferenciaNegativa.setText(z.format(peso_total_cobertura_transferencia_negativa) + " sacos");
		lblCoberturaTransferenciaPositiva.setText(z.format(peso_total_cobertura_transferencia_positiva) + " sacos");
		lblCoberturaComissao.setText(z.format(peso_total_cobertura_comissao) + " sacos");

		double peso_total_cobertura_concluida = peso_total_cobertura_efetuados
				- peso_total_cobertura_transferencia_negativa + peso_total_cobertura_transferencia_positiva;
		lblCoberturaConcluida.setText(z.format(peso_total_cobertura_concluida) + " sacos");

		peso_total_cobertura_restante = peso_total_cobertura - peso_total_cobertura_efetuados
				+ peso_total_cobertura_transferencia_negativa - peso_total_cobertura_transferencia_positiva;
		lblCoberturaRestante.setText(z.format(peso_total_cobertura_restante) + " sacos");

		double diferenca = (valor_total_pagamentos_efetuados - valor_total_transferencias_retiradas
				+ valor_total_transferencias_recebidas) - valor_total_pagamentos;

		if (diferenca == 0) {
			lblStatusPagamento.setText("Pagamento Concluído");
		} else if (diferenca > 0) {
			lblStatusPagamento.setText("Excedeu em " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca));

		} else if (diferenca < 0) {
			lblStatusPagamento.setText("Incompleto, falta " + NumberFormat.getCurrencyInstance(ptBr).format(diferenca));

		}

		double diferenca_pesos = peso_total_cobertura_restante;

		if (diferenca_pesos == 0 || diferenca_pesos == -0) {
			lblStatusCobertura.setText("Todos os sacos foram pagos");
		} else if (diferenca_pesos < 0) {
			lblStatusCobertura.setText("Excedeu em " + z.format(diferenca_pesos) + " Sacos");

		} else if (diferenca_pesos > 0) {
			lblStatusCobertura.setText("Incompleto, falta pagar " + z.format(diferenca_pesos) + " Sacos");

		}
		int n1 = (int) valor_total_pagamentos;
		int n2 = n1 - ((int) valor_total_pagamentos_restantes);
		atualizarGraficoPagamentos(n1, n2);

		registro_pagamento_global.setValor_total_pagamentos(valor_total_pagamentos);
		registro_pagamento_global.setValor_total_pagamentos_efetuados(valor_total_pagamentos_efetuados);
		registro_pagamento_global.setValor_total_pagamentos_restantes(valor_total_pagamentos_restantes);
		registro_pagamento_global.setValor_total_transferencias_recebidas(valor_total_transferencias_recebidas);
		registro_pagamento_global.setValor_total_transferencias_retiradas(valor_total_transferencias_retiradas);
		if (informar_atualizacao) {
			informar_atualizou();
		}

	}

	public void setarInformacoesPainelCarregamentos() {

		lblDataContrato.setText(contrato_local.getData_contrato());

		CadastroCliente compradores[] = contrato_local.getCompradores();
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		CadastroCliente corretores[] = contrato_local.getCorretores();

		String nome_corretores = "";
		String nome_vendedores = "";
		String nome_compradores = "";

		if (corretores[0] != null) {
			if (corretores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_corretores = corretores[0].getNome_empresarial();
			} else {
				nome_corretores = corretores[0].getNome_fantaia();

			}
		}
		lblCorretor.setText(nome_corretores);

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}

		lblCompradores.setText(nome_compradores);

		for (CadastroCliente vendedor : vendedores) {
			if (vendedor != null) {
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_vendedores += vendedor.getNome_empresarial();
				} else {
					nome_vendedores += vendedor.getNome_fantaia();

				}
				nome_vendedores += " ,";

			}
		}
		lblVendedores.setText(nome_vendedores);

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

		lblValorSaco.setText(valorString);

		NumberFormat z = NumberFormat.getNumberInstance();

		lblQuantidade.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

		String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
		lblValorTotal.setText(valor_total);

		String safra = contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato_local.getModelo_safra().getAno_plantio() + "/"
				+ contrato_local.getModelo_safra().getAno_colheita();

		lblSafra.setText(safra);
		lblProduto.setText(contrato_local.getModelo_safra().getProduto().getNome_produto());

	}

	public void setarInformacoesPainelRecebimentos() {

		lblDataContratoRecebimento.setText(contrato_local.getData_contrato());

		CadastroCliente compradores[] = contrato_local.getCompradores();
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		CadastroCliente corretores[] = contrato_local.getCorretores();

		String nome_corretores = "";
		String nome_vendedores = "";
		String nome_compradores = "";

		if (corretores[0] != null) {
			if (corretores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_corretores = corretores[0].getNome_empresarial();
			} else {
				nome_corretores = corretores[0].getNome_fantaia();

			}
		}
		lblCorretorRecebimento.setText(nome_corretores);

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

			}
		}

		lblCompradoresRecebimento.setText(nome_compradores);

		for (CadastroCliente vendedor : vendedores) {
			if (vendedor != null) {
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_vendedores += vendedor.getNome_empresarial();
				} else {
					nome_vendedores += vendedor.getNome_fantaia();

				}
				nome_vendedores += " ,";

			}
		}
		lblVendedoresRecebimento.setText(nome_vendedores);

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

		/*
		 * private JLabel lblDataContratoRecebimento, lblCorretorRecebimento,
		 * lblCompradoresRecebimento, lblVendedoresRecebimento,
		 * lblValorUnidadeRecebimento, lblQuantidadeRecebimento,
		 * lblValorTotalRecebimento, lblProdutoRecebimento,lblSafraRecebimento;
		 */
		lblValorUnidadeRecebimento.setText(valorString);

		NumberFormat z = NumberFormat.getNumberInstance();

		lblQuantidadeRecebimento.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

		String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
		lblValorTotalRecebimento.setText(valor_total);

		String safra = contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato_local.getModelo_safra().getAno_plantio() + "/"
				+ contrato_local.getModelo_safra().getAno_colheita();

		lblSafraRecebimento.setText(safra);
		lblProdutoRecebimento.setText(contrato_local.getModelo_safra().getProduto().getNome_produto());

	}

	public class PainelInformativo extends JPanel {

		private JLabel _lblDataContrato, _lblCorretor, _lblCompradores, _lblVendedores, _lblValorSaco, _lblQuantidade,
				_lblValorTotal;

		private JLabel lblnew;
		private JLabel _lblnewtra;
		private JLabel _lblnewsafra;
		private JLabel _lblProduto;
		private JLabel _lblTransgenia;
		private JLabel _lblSafra;
		private JLabel _lblnewcomissoa;
		private JLabel _lblComissao;
		private JLabel _lblnewfrete;
		private JLabel _lblnewarmazenagem;
		private JLabel _lblFrete;
		private JLabel _lblArmazenagem;
		private JLabel _lblnewdataentrega;
		private JLabel _lblDataEntrega;
		private JLabel _lblFundoRural;
		private JLabel _lblnewfundorural;

		public PainelInformativo() {

			setBackground(new Color(0, 102, 102));
			setForeground(Color.white);
			setBounds(0, 0, 500, 350);
			setLayout(new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px]"));

			JLabel _lblNewLabel_5 = new JLabel("Data:");
			_lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
			_lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
			_lblNewLabel_5.setBorder(null);
			_lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblNewLabel_5.setForeground(Color.white);

			add(_lblNewLabel_5, "cell 0 0,alignx right,aligny center");

			_lblDataContrato = new JLabel("");
			_lblDataContrato.setFont(new Font("Arial", Font.BOLD, 16));
			_lblDataContrato.setForeground(Color.white);
			add(_lblDataContrato, "cell 1 0,alignx center,aligny center");

			JLabel _lblNewLabel_9 = new JLabel("Corretor:");
			_lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblNewLabel_9.setForeground(Color.white);

			
			add(_lblNewLabel_9, "cell 0 1,alignx right,growy");

			_lblCorretor = new JLabel("");
			_lblCorretor.setFont(new Font("Arial", Font.BOLD, 16));
			_lblCorretor.setForeground(Color.white);
			add(_lblCorretor, "cell 1 1,alignx center,aligny center");

			JLabel _lblNewLabel_6 = new JLabel("Compradores:");
			_lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblNewLabel_6.setForeground(Color.white);

			add(_lblNewLabel_6, "cell 0 2,alignx right,growy");

			_lblCompradores = new JLabel("");
			_lblCompradores.setFont(new Font("Arial", Font.BOLD, 16));
			_lblCompradores.setForeground(Color.white);
			add(_lblCompradores, "cell 1 2,alignx center,aligny center");

			JLabel _lblNewLabel_8 = new JLabel("Vendedores:");
			_lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblNewLabel_8.setForeground(Color.white);
			add(_lblNewLabel_8, "cell 0 3,alignx right,aligny center");

			_lblVendedores = new JLabel("");
			_lblVendedores.setFont(new Font("Arial", Font.BOLD, 16));
			_lblVendedores.setForeground(Color.white);
			add(_lblVendedores, "cell 1 3,alignx center,aligny center");

			JLabel _lblnewvalorsaco = new JLabel("Valor Unidade:");
			_lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblnewvalorsaco.setForeground(Color.white);
			add(_lblnewvalorsaco, "cell 0 4,alignx right,aligny center");

			_lblValorSaco = new JLabel("");
			_lblValorSaco.setFont(new Font("Arial", Font.BOLD, 16));
			_lblValorSaco.setForeground(Color.white);
			add(_lblValorSaco, "cell 1 4,alignx center,aligny center");

			JLabel _lblNewLabel_10 = new JLabel("Quantidade:");
			_lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblNewLabel_10.setForeground(Color.white);
			add(_lblNewLabel_10, "cell 0 5,alignx right,aligny center");

			_lblQuantidade = new JLabel("");
			_lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
			_lblQuantidade.setForeground(Color.white);
			add(_lblQuantidade, "cell 1 5,alignx center,aligny center");

			JLabel _lblNewLabel_11 = new JLabel("Valor Total:");
			_lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblNewLabel_11.setForeground(Color.white);
			add(_lblNewLabel_11, "cell 0 6,alignx right,aligny center");

			_lblValorTotal = new JLabel("");
			_lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
			_lblValorTotal.setForeground(Color.white);
			add(_lblValorTotal, "cell 1 6,alignx center,aligny center");

			lblnew = new JLabel("Produto:");
			lblnew.setFont(new Font("Arial", Font.PLAIN, 16));
			lblnew.setForeground(Color.white);
			add(lblnew, "cell 0 7,alignx right");

			_lblProduto = new JLabel("");
			_lblProduto.setFont(new Font("Arial", Font.BOLD, 14));
			_lblProduto.setForeground(Color.white);
			add(_lblProduto, "cell 1 7,alignx center");

			_lblnewtra = new JLabel("Transgenese:");
			_lblnewtra.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblnewtra.setForeground(Color.white);
			add(_lblnewtra, "cell 0 8,alignx right");

			_lblTransgenia = new JLabel("");
			_lblTransgenia.setFont(new Font("Arial", Font.BOLD, 14));
			_lblTransgenia.setForeground(Color.white);
			add(_lblTransgenia, "cell 1 8,alignx center");

			_lblnewsafra = new JLabel("Safra:");
			_lblnewsafra.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblnewsafra.setForeground(Color.white);
			add(_lblnewsafra, "cell 0 9,alignx right");

			_lblSafra = new JLabel("2020/2021");
			_lblSafra.setFont(new Font("Arial", Font.BOLD, 14));
			_lblSafra.setForeground(Color.white);
			add(_lblSafra, "cell 1 9,alignx center");

			_lblnewcomissoa = new JLabel("Comissão:");
			_lblnewcomissoa.setFont(new Font("Arial", Font.PLAIN, 14));
			_lblnewcomissoa.setForeground(Color.white);
			add(_lblnewcomissoa, "cell 0 10,alignx right");

			_lblComissao = new JLabel("");
			_lblComissao.setFont(new Font("Arial", Font.BOLD, 16));
			_lblComissao.setForeground(Color.white);
			add(_lblComissao, "cell 1 10,alignx center");

			_lblnewfrete = new JLabel("Frete:");
			_lblnewfrete.setFont(new Font("Arial", Font.PLAIN, 16));;
			_lblnewfrete.setForeground(Color.white);
			add(_lblnewfrete, "cell 0 11,alignx right");

			_lblFrete = new JLabel("");
			_lblFrete.setFont(new Font("Arial", Font.BOLD, 16));
			_lblFrete.setForeground(Color.white);
			add(_lblFrete, "cell 1 11,alignx center");

			_lblnewarmazenagem = new JLabel("Armazenagem:");
			_lblnewarmazenagem.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblnewarmazenagem.setForeground(Color.white);
			add(_lblnewarmazenagem, "cell 0 12,alignx right");

			_lblArmazenagem = new JLabel("");
			_lblArmazenagem.setFont(new Font("Arial", Font.BOLD, 16));;
			_lblArmazenagem.setForeground(Color.white);
			add(_lblArmazenagem, "cell 1 12,alignx center");
			
			
			_lblnewfundorural = new JLabel("Fundo Rural:");
			_lblnewfundorural.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblnewfundorural.setForeground(Color.white);
			add(_lblnewfundorural, "cell 0 13,alignx right");

			_lblFundoRural = new JLabel("");
			_lblFundoRural.setFont(new Font("Arial", Font.BOLD, 16));
			_lblFundoRural.setForeground(Color.white);
			add(_lblFundoRural, "cell 1 13,alignx center");

			_lblnewdataentrega = new JLabel("Data Entrega:");
			_lblnewdataentrega.setFont(new Font("Arial", Font.PLAIN, 16));
			_lblnewdataentrega.setForeground(Color.white);
			add(_lblnewdataentrega, "cell 0 14,alignx right");

			_lblDataEntrega = new JLabel("");
			_lblDataEntrega.setFont(new Font("Arial", Font.BOLD, 16));
			_lblDataEntrega.setForeground(Color.white);
			add(_lblDataEntrega, "cell 1 14,alignx center");

			setarInformacoesPainelCarregamentosInformativos();
		}

		public void setarInformacoesPainelCarregamentosInformativos() {

			_lblDataContrato.setText(contrato_local.getData_contrato());

			CadastroCliente compradores[] = contrato_local.getCompradores();
			CadastroCliente vendedores[] = contrato_local.getVendedores();
			CadastroCliente corretores[] = contrato_local.getCorretores();

			String nome_corretores = "";
			String nome_vendedores = "";
			String nome_compradores = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretores = corretores[0].getNome_empresarial();
				} else {
					nome_corretores = corretores[0].getNome_fantaia();

				}
			}
			_lblCorretor.setText(nome_corretores);

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();

				}
			}

			if (compradores[1] != null) {
				if (compradores[1].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
				} else {
					nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

				}
			}

			_lblCompradores.setText(nome_compradores);

			for (CadastroCliente vendedor : vendedores) {
				if (vendedor != null) {
					if (vendedor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_vendedores += vendedor.getNome_empresarial();
					} else {
						nome_vendedores += vendedor.getNome_fantaia();

					}
					nome_vendedores += " ,";

				}
			}
			_lblVendedores.setText(nome_vendedores);

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

			_lblValorSaco.setText(valorString);

			NumberFormat z = NumberFormat.getNumberInstance();

			_lblQuantidade.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

			String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
			_lblValorTotal.setText(valor_total);

			_lblProduto.setText(contrato_local.getModelo_safra().getProduto().getNome_produto());
			_lblTransgenia.setText(contrato_local.getModelo_safra().getProduto().getTransgenia());
			_lblSafra.setText(contrato_local.getModelo_safra().getAno_plantio() + "/"
					+ contrato_local.getModelo_safra().getAno_colheita());
			_lblDataEntrega.setText(contrato_local.getData_entrega());
			if (contrato_local.getComissao() == 1) {
				// tem comissao
				_lblComissao.setText(NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_comissao()));
			} else {
				_lblComissao.setText("NÃO");
			}
			_lblFrete.setText(contrato_local.getFrete());
			_lblArmazenagem.setText(contrato_local.getArmazenagem());
			_lblFundoRural.setText(contrato_local.getFundo_rural());
		}
	}

	public void excluir_contrato() {

		if (JOptionPane.showConfirmDialog(isto, "Deseja realmente excluir o contrato?", "Excluir",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			/*
			 * caminho_diretorio text caminho_arquivo text nome_arquivo text
			 * caminho_diretorio2 text caminho_arquivo2 text nome_arquivo2
			 */

			String caminho_diretorio = contrato_local.getCaminho_diretorio_contrato();
			String caminho_diretorio2 = contrato_local.getCaminho_diretorio_contrato2();

			String caminho_arquivo = contrato_local.getCaminho_arquivo();
			String caminho_arquivo2 = contrato_local.getCaminho_arquivo2();

			String nome_arquivo = contrato_local.getNome_arquivo();
			String nome_arquivo2 = contrato_local.getNome_arquivo2();

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			boolean excluido = false;
			if (contrato_local.getSub_contrato() == 0 || contrato_local.getSub_contrato() == 3
					|| contrato_local.getSub_contrato() == 5 || contrato_local.getSub_contrato() == 4) {
				// é um contrato pai, e um contrato pai tereceirizado
				excluido = gerenciar.remover_contrato_rotina(contrato_local.getId());

			} else if (contrato_local.getSub_contrato() == 1 || contrato_local.getSub_contrato() == 6) {
				excluido = gerenciar.remover_sub_contrato_rotina(contrato_local.getId());

			}
			if (excluido) {

				if (caminho_diretorio != null && nome_arquivo != null) {

					ManipularTxt manipular = new ManipularTxt();

					boolean exclusao_diretorio = manipular
							.limparDiretorio(new File(configs_globais.getServidorUnidade() + caminho_diretorio));
					if (exclusao_diretorio) {
						// JOptionPane.showMessageDialog(isto, "Contrato Excluido!");

						// excluir diretorio da nuvem
						Nuvem nuvem = new Nuvem();
						nuvem.abrir();
						nuvem.testar();
						nuvem.listar();
						boolean exclusao_nuvem = nuvem.deletarArquivo(nome_arquivo);
						if (exclusao_nuvem) {
							JOptionPane.showMessageDialog(isto, "Contrato excluido");
							isto.dispose();

						} else {
							JOptionPane.showMessageDialog(isto,
									"Contrato excluido, diretorio local apagado, mas o arquivo ainda está na nuvem\nConsulte o administrador!");

						}

					} else {
						JOptionPane.showMessageDialog(isto,
								"Contrato excluido, mas o diretorio nao pode ser apagado!\nConsulte o administrador");
					}
				}
				if (caminho_diretorio2 != null && caminho_diretorio2.length() > 20 && nome_arquivo2 != null
						&& nome_arquivo2.length() > 20) {
					// diretorio 2
					ManipularTxt manipular = new ManipularTxt();

					boolean exclusao_diretorio = manipular
							.limparDiretorio(new File(configs_globais.getServidorUnidade() + caminho_diretorio2));
					if (exclusao_diretorio) {
						// JOptionPane.showMessageDialog(isto, "Contrato Excluido!");

						// excluir diretorio da nuvem
						Nuvem nuvem = new Nuvem();
						nuvem.abrir();
						nuvem.testar();
						nuvem.listar();
						boolean exclusao_nuvem = nuvem.deletarArquivo(nome_arquivo2);
						if (exclusao_nuvem) {
							JOptionPane.showMessageDialog(isto, "Contrato excluido");
							isto.dispose();

						} else {
							JOptionPane.showMessageDialog(isto,
									"Contrato excluido, diretorio local apagado, mas o arquivo ainda está na nuvem\nConsulte o administrador!");

						}

					} else {
						JOptionPane.showMessageDialog(isto,
								"Contrato excluido, mas o diretorio nao pode ser apagado!\nConsulte o administrador");
					}

				}

			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao excluir o contrato\nConsulte o administrador!");

			}

		}

	}

	public String calculoCobertura(double valor_pagamento) {
		String retorno = "";
		// quantidade
		double quantidade = contrato_local.getQuantidade();
		double preco = contrato_local.getValor_produto();

		// unidade de medida
		if (contrato_local.getMedida().equalsIgnoreCase("TON")) {
			// unidade em toneladas

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);

		} else if (contrato_local.getMedida().equalsIgnoreCase("SACOS")) {
			// unidade em sacos

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);

		} else if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			// unidade em quilogramas

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);
		}

		return retorno;
	}

	public void abrirSeletor() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				FileDialog dialog = new FileDialog((isto), "Select File to Open");
				dialog.setMode(FileDialog.LOAD);
				dialog.setVisible(true);
				String file = dialog.getFile();
				System.out.println(file + " chosen.");

			}

		});

	}

	public void assinar() {
		JOptionPane.showMessageDialog(isto,
				"Na próxima tela, importe o arquivo digitalizado\ncom assinatura de ambas as partes");
		try {
			new JFXPanel();
			Platform.runLater(() -> {

				// pegar ultima pasta
				ManipularTxt manipular_ultima_pasta = new ManipularTxt();
				String ultima_pasta = manipular_ultima_pasta
						.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
				if (d == null) {
					d = new FileChooser();
				}
				d.setInitialDirectory(new File(ultima_pasta));
				File file = d.showOpenDialog(new Stage());
				String caminho_arquivo = "";
				if (file != null) {
					caminho_arquivo = file.getAbsolutePath();

					manipular_ultima_pasta.rescreverArquivo(
							new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());

					try {
						// copiar o arquivo para a pasta do contrato
						String caminho_diretorio = contrato_local.getCaminho_diretorio_contrato();
						String caminho_diretorio2 = contrato_local.getCaminho_diretorio_contrato2();
						String nome_arquivo_global = "";
						String caminho_completo_global = "";

						boolean movido = false;

						if (caminho_diretorio != null && caminho_diretorio.length() > 20) {

							ManipularTxt manipular = new ManipularTxt();
							String unidade_base_dados = configs_globais.getServidorUnidade();

							String caminho_salvar = unidade_base_dados + caminho_diretorio;
							String nome_arquivo = "comprovante_assinatura_" + contrato_local.getCodigo() + ".pdf";
							;

							manipular.criarDiretorio(caminho_salvar + "\\comprovantes\\");
							String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
							movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);
							nome_arquivo_global = nome_arquivo;
							caminho_completo_global = caminho_completo;
						}
						if (caminho_diretorio2 != null && caminho_diretorio2.length() > 20) {
							ManipularTxt manipular = new ManipularTxt();
							String unidade_base_dados = configs_globais.getServidorUnidade();

							String caminho_salvar = unidade_base_dados + caminho_diretorio2;
							String nome_arquivo = "comprovante_assinatura_" + contrato_local.getCodigo() + ".pdf";
							;

							manipular.criarDiretorio(caminho_salvar + "\\comprovantes\\");
							String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
							movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);
							nome_arquivo_global = nome_arquivo;
							caminho_completo_global = caminho_completo;

						}

						if (movido) {

							// atualizar status do contrato
							GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
							boolean assinou = gerenciar.atualizarStatusContrato(contrato_local.getId(), 2);
							if (assinou) {

								CadastroDocumento novo_documento = new CadastroDocumento();
								novo_documento.setDescricao("Comprovante de Assinatura de Contrato");
								novo_documento.setNome("comprovante_assinatura");

								String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();

								novo_documento.setTipo(1);
								novo_documento.setId_pai(0);
								novo_documento.setNome_arquivo(nome_arquivo_global);
								novo_documento.setId_contrato_pai(contrato_local.getId());

								GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
								int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
								if (cadastrar > 0) {

									JOptionPane.showMessageDialog(isto,
											"Arquivo copiado\nOrigem: " + caminho_arquivo + "\nDestino: "
													+ caminho_completo_global + "\nStatus do Contrato Atualizado");

									atualizarArvoreDocumentos();
									informar_atualizou();
								} else {
									JOptionPane.showMessageDialog(isto,
											"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
									// cancelar operacao e excluir o arquivo
									if (new ManipularTxt().apagarArquivo(caminho_completo_global)) {

									} else {
										JOptionPane.showMessageDialog(isto,
												"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
									}
								}

							} else {
								JOptionPane.showMessageDialog(isto, "Status do Contrato NÃO Atualizado");
								JOptionPane.showMessageDialog(isto,
										"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
								// cancelar operacao e excluir o arquivo
								if (new ManipularTxt().apagarArquivo(caminho_completo_global)) {

								} else {
									JOptionPane.showMessageDialog(isto,
											"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
								}
							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo + "\nDestino: "
											+ caminho_completo_global + "\n Consulte o administrador!");

						}

					} catch (Exception e) {

					}

					setarInformacoesPainelPrincipal();
				} else {
				}

			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao usar javafx");

		}
	}

	public void assinarAditivo(int id_aditivo) {
		JOptionPane.showMessageDialog(isto,
				"Na próxima tela, importe o arquivo digitalizado\ncom assinatura de todas as partes");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String nome_arquivo = "comprovante_assinatura_aditivo_" + id_aditivo + "_" + contrato_local.getCodigo()
					+ ".pdf";
			;

			String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				// atualizar status do contrato
				GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
				boolean assinou = gerenciar.atualizarStatusAditivo(id_aditivo, 2);
				if (assinou) {

					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Comprovante de Assinatura de Aditivo");
					novo_documento.setNome("comprovante_aditivo");

					novo_documento.setTipo(4);
					novo_documento.setId_pai(id_aditivo);
					novo_documento.setNome_arquivo(nome_arquivo);
					novo_documento.setId_contrato_pai(contrato_local.getId());

					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
					int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
					if (cadastrar > 0) {

						JOptionPane.showMessageDialog(isto, "Arquivo copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\nStatus do Aditivo Atualizado");

						setInformacoesAditivos();
						atualizarArvoreDocumentos();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
						// cancelar operacao e excluir o arquivo
						if (manipular.apagarArquivo(caminho_completo)) {

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
						}
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Status do aditivo NÃO Atualizado");
					JOptionPane.showMessageDialog(isto,
							"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}

		} catch (Exception e) {

		}

	}

	public void importarComprovanteCarregamento(int id_carregamento) {
		JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo \ncomprovante!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(new Stage());
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}

			try {
				// copiar o arquivo para a pasta do contrato
				ManipularTxt manipular = new ManipularTxt();
				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
				String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);

				String nome_arquivo = "comprovante_carregamento_" + id_carregamento + "_" + contrato_local.getCodigo()
						+ "." + extensaoDoArquivo;
				;

				String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
				boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

				if (movido) {

					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Comprovante de Carregamento id: " + id_carregamento + "do contrato "
							+ contrato_local.getCodigo());
					novo_documento.setNome("comprovante_assinatura");

					novo_documento.setTipo(3);
					novo_documento.setId_pai(id_carregamento);
					novo_documento.setNome_arquivo(nome_arquivo);
					novo_documento.setId_contrato_pai(contrato_local.getId());

					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
					int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
					if (cadastrar > 0) {

						JOptionPane.showMessageDialog(isto,
								"Arquivo copiado\nOrigem: " + caminho_arquivo + "\nDestino: " + caminho_completo);

						atualizarArvoreDocumentos();
						informar_atualizou();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
						// cancelar operacao e excluir o arquivo
						if (manipular.apagarArquivo(caminho_completo)) {

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
						}
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
							+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

				}

			} catch (Exception e) {

			}

			setarInformacoesPainelPrincipal();

		});
	}

	public void importarComprovantePagamento(int id_pagamento) {

		JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo comprovante!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}

			TelaRecortarImagem2 tela = new TelaRecortarImagem2(caminho_arquivo, isto);
			tela.setTelaPai(isto);
			tela.setVisible(true);

			try {

				String caminho_diretorio = contrato_local.getCaminho_diretorio_contrato();
				String caminho_diretorio2 = contrato_local.getCaminho_diretorio_contrato2();
				String nome_arquivo_global = "";
				String caminho_completo_global = "";
				boolean movido = false;

				if (caminho_diretorio != null) {

					// copiar o arquivo para a pasta do contrato
					ManipularTxt manipular = new ManipularTxt();
					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_salvar = unidade_base_dados + "\\" + caminho_diretorio;

					String nome_arquivo = "comprovante_pagamento_" + id_pagamento + "_" + contrato_local.getCodigo()
							+ "." + "png";
					;

					String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
					movido = manipular.copiarNFe(caminho_salvar_comprovante_pagamento, caminho_completo);
					caminho_completo_global = caminho_completo;
					nome_arquivo_global = nome_arquivo;

					if (movido) {

						CadastroDocumento novo_documento = new CadastroDocumento();
						novo_documento.setDescricao("Comprovante de Pagamento id: " + id_pagamento + "do contrato "
								+ contrato_local.getCodigo());
						novo_documento.setNome("comprovante_assinatura");

						novo_documento.setTipo(2);
						novo_documento.setId_pai(id_pagamento);
						novo_documento.setNome_arquivo(nome_arquivo_global);
						novo_documento.setId_contrato_pai(contrato_local.getId());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
						if (cadastrar > 0) {

							JOptionPane.showMessageDialog(isto, "Arquivo copiado\nOrigem: " + caminho_arquivo
									+ "\nDestino: " + caminho_completo_global);

							atualizarArvoreDocumentos();
						} else {
							JOptionPane.showMessageDialog(isto,
									"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
							// cancelar operacao e excluir o arquivo
							if (new ManipularTxt().apagarArquivo(caminho_completo_global)) {

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
							}
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo_global + "\n Consulte o administrador!");

					}
				}

				if (checkString(caminho_diretorio2) && caminho_diretorio2.length() > 10) {
					// JOptionPane.showMessageDialog(tela, "CAminho do diretorio 2 nao e nulo: " +
					// caminho_diretorio2);

					// copiar o arquivo para a pasta do contrato
					ManipularTxt manipular = new ManipularTxt();
					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_salvar = unidade_base_dados + "\\" + caminho_diretorio2;

					String nome_arquivo = "comprovante_pagamento_" + id_pagamento + "_" + contrato_local.getCodigo()
							+ "." + "png";
					;

					String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
					movido = manipular.copiarNFe(caminho_salvar_comprovante_pagamento, caminho_completo);
					caminho_completo_global = caminho_completo;
					nome_arquivo_global = nome_arquivo;

					if (movido) {

						CadastroDocumento novo_documento = new CadastroDocumento();
						novo_documento.setDescricao("Comprovante de Pagamento id: " + id_pagamento + "do contrato "
								+ contrato_local.getCodigo());
						novo_documento.setNome("comprovante_assinatura");

						novo_documento.setTipo(2);
						novo_documento.setId_pai(id_pagamento);
						novo_documento.setNome_arquivo(nome_arquivo_global);
						novo_documento.setId_contrato_pai(contrato_local.getId());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
						if (cadastrar > 0) {

							JOptionPane.showMessageDialog(isto, "Arquivo copiado\nOrigem: " + caminho_arquivo
									+ "\nDestino: " + caminho_completo_global);

							atualizarArvoreDocumentos();
						} else {
							JOptionPane.showMessageDialog(isto,
									"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
							// cancelar operacao e excluir o arquivo
							if (new ManipularTxt().apagarArquivo(caminho_completo_global)) {

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
							}
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo_global + "\n Consulte o administrador!");

					}
				}

			} catch (Exception e) {

			}

			setarInformacoesPainelPrincipal();

		});
	}

	public void setarInformacoesPainelPrincipal() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				contrato_local = gerenciar.getContrato(contrato_local.getId());

				int status = contrato_local.getStatus_contrato();

				if (status == 1) {
					lblStatusContrato.setText("Status do Contrato: " + "Recolher Assinaturas");

					btnCriarAditivo.setEnabled(false);
					btnCriarAditivo.setVisible(false);

					btnRevogarAssinatura.setEnabled(false);
					btnRevogarAssinatura.setVisible(false);

					btnConcluir.setEnabled(false);
					btnConcluir.setVisible(false);
					/*
					 * if (contrato_local.getSub_contrato() == 3 || contrato_local.getSub_contrato()
					 * == 4) { // contrato de terceiros
					 * 
					 * btnCriarAditivo.setVisible(false); btnCriarAditivo.setEnabled(false);
					 * 
					 * btnCriarDistrato.setVisible(false); btnCriarDistrato.setEnabled(false);
					 * 
					 * } else { btnEditarContrato.setVisible(true);
					 * btnEditarContrato.setEnabled(true);
					 * 
					 * btnCriarAditivo.setVisible(true); btnCriarAditivo.setEnabled(true);
					 * 
					 * btnCriarDistrato.setVisible(true); btnCriarDistrato.setEnabled(true); }
					 */
					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);

					btnCriarAditivo.setVisible(false);
					btnCriarAditivo.setEnabled(false);

					btnCriarDistrato.setVisible(true);
					btnCriarDistrato.setEnabled(true);

					btnExcluirContrato.setVisible(true);
					btnExcluirContrato.setEnabled(true);

					btnAssinarContrato.setVisible(true);
					btnAssinarContrato.setEnabled(true);

					btnReabrir.setEnabled(false);
					btnReabrir.setVisible(false);

					btnAprovar.setEnabled(false);
					btnAprovar.setVisible(false);

					btnAprovar.setEnabled(true);
					btnAprovar.setVisible(true);

					btnEditarContrato.setEnabled(true);
					btnEditarContrato.setVisible(true);

					btnAlterar.setEnabled(false);
					btnAlterar.setVisible(false);

					
					btnCancelarContrato.setEnabled(false);
					btnCancelarContrato.setVisible(false);

					
				} else if (status == 2) {
					lblStatusContrato.setText("Status do Contrato: " + "Assinado");
					btnCriarAditivo.setEnabled(true);
					btnCriarAditivo.setVisible(true);

					btnRevogarAssinatura.setEnabled(true);
					btnRevogarAssinatura.setVisible(true);

					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);

					btnConcluir.setEnabled(true);
					btnConcluir.setVisible(true);

					btnExcluirContrato.setVisible(false);
					btnExcluirContrato.setEnabled(false);

					btnAssinarContrato.setVisible(false);
					btnAssinarContrato.setEnabled(false);

					btnReabrir.setEnabled(false);
					btnReabrir.setVisible(false);

					btnCriarAditivo.setVisible(true);
					btnCriarAditivo.setEnabled(true);

					btnCriarDistrato.setVisible(true);
					btnCriarDistrato.setEnabled(true);

					btnAprovar.setEnabled(false);
					btnAprovar.setVisible(false);

					btnAlterar.setEnabled(true);
					btnAlterar.setVisible(true);
					
					
					btnCancelarContrato.setEnabled(true);
					btnCancelarContrato.setVisible(true);

				} else if (status == 3) {
					lblStatusContrato.setText("Status do Contrato: " + "Cumprindo");
					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);

					btnExcluirContrato.setVisible(false);
					btnExcluirContrato.setEnabled(false);

					btnAssinarContrato.setEnabled(false);
					btnAssinarContrato.setVisible(false);

					btnCriarAditivo.setEnabled(false);
					btnCriarAditivo.setVisible(false);

					btnCriarDistrato.setEnabled(false);
					btnCriarDistrato.setVisible(false);

					btnRevogarAssinatura.setEnabled(false);
					btnRevogarAssinatura.setVisible(false);

					btnConcluir.setEnabled(false);
					btnConcluir.setVisible(false);

					btnReabrir.setEnabled(true);
					btnReabrir.setVisible(true);

					btnAprovar.setEnabled(false);
					btnAprovar.setVisible(false);

					btnAlterar.setEnabled(false);
					btnAlterar.setVisible(false);

					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);
					
					
					btnCancelarContrato.setEnabled(false);
					btnCancelarContrato.setVisible(false);


				} else if (status == 0) {
					lblStatusContrato.setText("Status do Contrato: " + "Requisitar Aprovação de Contrato");
					btnEditarContrato.setVisible(true);
					btnEditarContrato.setEnabled(true);

					btnAprovar.setEnabled(true);
					btnAprovar.setVisible(true);

					btnExcluirContrato.setVisible(true);
					btnExcluirContrato.setEnabled(true);

					btnAssinarContrato.setEnabled(false);
					btnAssinarContrato.setVisible(false);

					btnCriarAditivo.setEnabled(false);
					btnCriarAditivo.setVisible(false);

					btnCriarDistrato.setEnabled(false);
					btnCriarDistrato.setVisible(false);

					btnRevogarAssinatura.setEnabled(false);
					btnRevogarAssinatura.setVisible(false);

					btnConcluir.setEnabled(false);
					btnConcluir.setVisible(false);

					btnReabrir.setEnabled(false);
					btnReabrir.setVisible(false);

					btnEnviarMsg.setVisible(false);
					btnEnviarMsg.setEnabled(false);

					btnAlterar.setEnabled(false);
					btnAlterar.setVisible(false);
					
					btnCancelarContrato.setEnabled(false);
					btnCancelarContrato.setVisible(false);

				}else if(status == 4) {
					lblStatusContrato.setText("Status do Contrato: " + "Cancelado");
					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);

					btnAprovar.setEnabled(false);
					btnAprovar.setVisible(false);

					btnExcluirContrato.setVisible(false);
					btnExcluirContrato.setEnabled(false);

					btnAssinarContrato.setEnabled(false);
					btnAssinarContrato.setVisible(false);

					btnCriarAditivo.setEnabled(false);
					btnCriarAditivo.setVisible(false);

					btnCriarDistrato.setEnabled(false);
					btnCriarDistrato.setVisible(false);

					btnRevogarAssinatura.setEnabled(false);
					btnRevogarAssinatura.setVisible(false);

					btnConcluir.setEnabled(false);
					btnConcluir.setVisible(false);

					btnReabrir.setEnabled(true);
					btnReabrir.setVisible(true);

					btnEnviarMsg.setVisible(false);
					btnEnviarMsg.setEnabled(false);

					btnAlterar.setEnabled(false);
					btnAlterar.setVisible(false);
					
					btnCancelarContrato.setEnabled(false);
					btnCancelarContrato.setVisible(false);
				}

				/*
				 * DadosGlobais dados = DadosGlobais.getInstance(); JFrame telaPrincipal =
				 * dados.getTelaPrincipal(); ((TelaPrincipal)
				 * telaPrincipal).atualizarGraficoContratos();
				 */
			}
		});

	}

	public void atualizarGraficoContratos(int num_total, int num_carregado) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_carregado) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoCarregamento.setDados(num_total, i);
					painelGraficoCarregamento.repaint();
					painelGraficoCarregamento.getParent().repaint();

					i++;
				}

			}
		}.start();

	}

	public void atualizarGraficoRecebimento(int num_total, int num_recebido) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				new Thread() {

					@Override
					public void run() {

						int i = 0;
						while (i <= num_recebido) {

							// System.out.printf("Disponivel e %d\n ", disponivel);
							// System.out.printf("Usado e %d\n", usado);

							painelGraficoRecebimento.setDados(num_total, i);
							painelGraficoRecebimento.repaint();
							painelGraficoRecebimento.updateUI();

							painelGraficoRecebimento.getParent().repaint();

							i++;
						}

					}
				}.start();

			}
		});

	}

	public void atualizarGraficoPagamentos(int num_total, int num_ja_pago) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_ja_pago) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoPagamentos.setDados(num_total, i);
					painelGraficoPagamentos.repaint();

					painelGraficoPagamentos.getParent().repaint();

					i++;
				}

			}
		}.start();

	}

	public void vizualizarContrato() {

		String unidade_base_dados = configs_globais.getServidorUnidade();

		String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_arquivo();
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop desktop = Desktop.getDesktop();
				File myFile = new File(caminho_completo);
				desktop.open(myFile);
			} catch (IOException ex) {
			}
		}
	}

	public void setarTituloJanela() {
		// definir o nome da janela
		String safra = contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato_local.getModelo_safra().getAno_plantio() + "/"
				+ contrato_local.getModelo_safra().getAno_colheita();
		double quantidade = 0;

		if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade = contrato_local.getQuantidade() / 60;
		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade = contrato_local.getQuantidade();

		}

		double valor_produto = contrato_local.getValor_produto();
		BigDecimal valor_a_pagar = contrato_local.getValor_a_pagar();

		Locale ptBr = new Locale("pt", "BR");
		String valor_produto_reais = NumberFormat.getCurrencyInstance(ptBr).format(valor_produto);

		String valor_total_a_pagar_reais = NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar);

		String nome_compradores = "";
		String nome_vendedores = "";

		CadastroCliente compradores[] = contrato_local.getCompradores();
		CadastroCliente vendedores[] = contrato_local.getVendedores();

		if (compradores[0].getTipo_pessoa() == 0) {
			nome_compradores = compradores[0].getNome_empresarial();
		} else {
			nome_compradores = compradores[0].getNome_fantaia();
		}

		if (compradores[1] != null) {
			if (compradores[1].getTipo_pessoa() == 0) {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
			} else {
				nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();
			}
		}

		if (vendedores[0].getTipo_pessoa() == 0) {
			nome_vendedores = vendedores[0].getNome_empresarial();
		} else {
			nome_vendedores = vendedores[0].getNome_fantaia();
		}

		if (vendedores[1] != null) {
			if (vendedores[1].getTipo_pessoa() == 0) {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_empresarial();
			} else {
				nome_vendedores = nome_vendedores + ", " + vendedores[1].getNome_fantaia();
			}
		}

		String de_para = "de " + nome_vendedores + " para " + nome_compradores;

		if (contrato_local.getSub_contrato() == 0) {

			isto.setTitle("E-Contract - Gerenciar Contrato " + contrato_local.getCodigo() + " em quantidade de "
					+ quantidade + " sacos de " + contrato_local.getModelo_safra().getProduto().getNome_produto()
					+ " da safra " + safra + " no valor de " + valor_produto_reais + " por saco," + de_para
					+ " no valor total de " + valor_total_a_pagar_reais);

		} else {

			isto.setTitle("E-Contract - Gerenciar Sub-Contrato " + contrato_local.getCodigo() + " em quantidade de "
					+ quantidade + " sacos de " + contrato_local.getModelo_safra().getProduto().getNome_produto()
					+ " da safra " + safra + " no valor de " + valor_produto_reais + " por saco," + de_para
					+ " no valor total de " + valor_total_a_pagar_reais);

		}

	}

	public void revogarAssinatura() {

		if (JOptionPane.showConfirmDialog(isto, "Revogar Assinatura", "Deseja revogar a assinatura?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			boolean revogado = gerenciar.atualizarStatusContrato(contrato_local.getId(), 1);

			if (revogado) {
				JOptionPane.showMessageDialog(isto, "Assinatura do Contrato Retirada");

			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao revogar a assinatura!\nConsulte o administrador!");

			}

			atualizarContratoLocal(true);
			// setarInformacoesPainelPrincipal();

		} else {

		}

	}

	public void revogarAssinaturaAditivo(int id_aditivo) {

		if (JOptionPane.showConfirmDialog(isto, "Revogar Assinatura", "Deseja revogar a assinatura deste aditivo?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
			boolean revogado = gerenciar.atualizarStatusAditivo(id_aditivo, 1);

			if (revogado) {
				JOptionPane.showMessageDialog(isto, "Assinatura do aditivo Retirada");

			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao revogar a assinatura!\nConsulte o administrador!");

			}

			// atualizarContratoLocal();
			setInformacoesDocumentos();

			setInformacoesAditivos();

		} else {

		}

	}

	public void revogarAssinaturaDistrato(int id_distrato) {

		if (JOptionPane.showConfirmDialog(isto, "Revogar Assinatura", "Deseja revogar a assinatura deste distrato?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoDistratos gerenciar = new GerenciarBancoDistratos();
			boolean revogado = gerenciar.atualizarStatusDistrato(1, id_distrato);

			if (revogado) {
				JOptionPane.showMessageDialog(isto, "Assinatura do distrato Revogada");

			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao revogar a assinatura!\nConsulte o administrador!");

			}

			// atualizarContratoLocal();
			// setInformacoesDocumentos();
			setInformacoesDistratos();
			atualizarArvoreDocumentos();
			informar_atualizou();
			// setInformacoesAditivos();

		} else {

		}

	}

	public void selecionarDocumento() {

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(new Stage());
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				entCaminhoDocumento.setText(caminho_arquivo);
			}

		});

	}

	public void adicionarNovoDocumento() {

		String nome, descricao, nome_arquivo, caminho_arquivo;
		int id_contrato_pai;

		nome = entNomeDocumento.getText();
		descricao = entDescricaoDocumento.getText();
		caminho_arquivo = entCaminhoDocumento.getText();

		String nome_arquivo_original_conteudo[] = caminho_arquivo.split("\"");
		String nome_arquivo_original = nome_arquivo_original_conteudo[nome_arquivo_original_conteudo.length - 1];
		String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

		// copiar o arquivo para a nova pasta

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato() + "\\"
					+ "comprovantes";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
					nome_arquivo = contrato_local.getCodigo() + "_" + nome + "_" + horaString.replaceAll(":", "_") + "."
							+ extensaoDoArquivo;

					String caminho_completo = caminho_salvar + "\\" + nome_arquivo;

					boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

					if (movido) {

						// atualizar status do contrato
						CadastroDocumento novo_documento = new CadastroDocumento();
						novo_documento.setDescricao(descricao);
						novo_documento.setNome(nome);

						String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();
						int tipo_documento = -1;

						if (s_tipo_documento.equalsIgnoreCase("Assinaturas")) {
							tipo_documento = 1;
						} else if (s_tipo_documento.equalsIgnoreCase("Pagamentos")) {
							tipo_documento = 2;
						} else if (s_tipo_documento.equalsIgnoreCase("Carregamentos")) {
							tipo_documento = 3;
						} else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
							tipo_documento = 4;
						}

						novo_documento.setTipo(tipo_documento);
						novo_documento.setId_pai(0);
						novo_documento.setNome_arquivo(nome_arquivo);
						novo_documento.setId_contrato_pai(contrato_local.getId());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
						if (cadastrar > 0) {
							JOptionPane.showMessageDialog(isto, "Arquivo copiado e salvo na base de dados\nOrigem: "
									+ caminho_arquivo + "\nDestino: " + caminho_completo);

							entNomeDocumento.setText("");
							entDescricaoDocumento.setText("");
							entCaminhoDocumento.setText("");

							atualizarArvoreDocumentos();
							informar_atualizou();
						} else {
							JOptionPane.showMessageDialog(isto,
									"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
							// cancelar operacao e excluir o arquivo
							if (manipular.apagarArquivo(caminho_completo)) {

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
							}
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

					}
				} else {
					JOptionPane.showMessageDialog(isto, "Nome do arquivo invalido!");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

		}

	}

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentos(contrato_local.getId());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				JPanel panel = new JPanel();
				panel.setBackground(new Color(255, 255, 204));
				panel.setBounds(34, 373, 421, 268);
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JScrollPane scrolldocumentos = new JScrollPane(panel);

				painelComprovantes.add(scrolldocumentos, "cell 0 18 3 17,grow");

				JLabel lblNewLabel_18 = new JLabel("Documentos deste contrato:");
				lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD, 16));
				panel.add(lblNewLabel_18, "cell 0 0");

				// create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				no_assinaturas = new DefaultMutableTreeNode("Assinaturas");
				no_pagamentos = new DefaultMutableTreeNode("Pagamentos");
				no_carregamentos = new DefaultMutableTreeNode("Carregamentos");
				no_outros = new DefaultMutableTreeNode("Outros");

				// add the child nodes to the root node
				root.add(no_assinaturas);
				root.add(no_pagamentos);
				root.add(no_carregamentos);
				root.add(no_outros);

				// create the tree by passing in the root node
				arvore_documentos = new JTree(root);
				arvore_documentos.setBackground(new Color(255, 255, 204));

				arvore_documentos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						no_selecionado = (DefaultMutableTreeNode) arvore_documentos.getLastSelectedPathComponent();
						// lblNoSelecionado.setText(no_selecionado.getUserObject().toString());
					}
				});

				setMenuDocumentos();

				arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_assinatura = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_assinatura.png"));
					ImageIcon icone_pagamentos = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_pagamentos.png"));
					ImageIcon icone_carregamentos = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_carregamentos.png"));
					ImageIcon icone_outros = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_outros.png"));

					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
							boolean expanded, boolean isLeaf, int row, boolean focused) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
						String s = node.getUserObject().toString();
						if ("Assinaturas".equals(s)) {
							setOpenIcon(icone_assinatura);
							setClosedIcon(icone_assinatura);

						} else if ("Carregamentos".equals(s)) {
							setOpenIcon(icone_carregamentos);
							setClosedIcon(icone_carregamentos);

						} else if ("Pagamentos".equals(s)) {
							setOpenIcon(icone_pagamentos);
							setClosedIcon(icone_pagamentos);

						} else if ("Outros".equals(s)) {
							setOpenIcon(icone_outros);
							setClosedIcon(icone_outros);

						}
						super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, hasFocus);

						return this;
					}

				});

				arvore_documentos.setShowsRootHandles(true);
				arvore_documentos.setRootVisible(false);
				panel.add(arvore_documentos, "cell 0 1,grow");

				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							no_assinaturas.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_pagamentos.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// carregamentos
							no_carregamentos.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 4) {
							// outros
							no_outros.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}

				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

				lblNoSelecionado = new JLabel("");
				painelComprovantes.add(lblNoSelecionado, "cell 0 16,grow");
				/*
				 * JPanel panel_1 = new JPanel(); panel_1.setBounds(24, 36, 752, 121);
				 * painelComprovantes.add(panel_1); panel_1.setLayout(null);
				 * 
				 * modelo_aditivos.addColumn("Id"); modelo_aditivos.addColumn("Status");
				 * modelo_aditivos.addColumn("Data"); modelo_aditivos.addColumn("Texto");
				 * modelo_aditivos.addColumn("Nome Arquivo");
				 * 
				 * table_aditivos = new JTable(modelo_aditivos);
				 * 
				 * setMenuAditivos();
				 * 
				 * JScrollPane scrollPane = new JScrollPane(table_aditivos);
				 * scrollPane.setBounds(10, 11, 732, 110); panel_1.add(scrollPane);
				 */

				entNomeDocumento = new JTextField();
				entNomeDocumento.setFont(new Font("Arial", Font.BOLD, 16));
				painelComprovantes.add(entNomeDocumento, "cell 6 22 5 1,grow");
				entNomeDocumento.setColumns(10);

				JLabel lblNewLabel_15 = new JLabel("Nome:");
				lblNewLabel_15.setFont(new Font("Arial", Font.BOLD, 16));
				lblNewLabel_15.setForeground(Color.WHITE);
				painelComprovantes.add(lblNewLabel_15, "cell 4 22,alignx right,aligny center");

				JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
				lblNewLabel_16_1.setFont(new Font("Arial", Font.BOLD, 16));
				lblNewLabel_16_1.setForeground(Color.WHITE);
				painelComprovantes.add(lblNewLabel_16_1, "cell 4 24,alignx right,aligny center");

				cBTipoDocumento = new JComboBox();
				cBTipoDocumento.setFont(new Font("Arial", Font.BOLD, 16));
				painelComprovantes.add(cBTipoDocumento, "cell 6 24 5 1,grow");
				cBTipoDocumento.addItem("Assinaturas");
				cBTipoDocumento.addItem("Pagamentos");
				cBTipoDocumento.addItem("Carregamentos");
				cBTipoDocumento.addItem("Outros");

				JLabel lblNewLabel_16 = new JLabel("Descrição:");
				lblNewLabel_16.setFont(new Font("Arial", Font.BOLD, 16));
				lblNewLabel_16.setForeground(Color.WHITE);
				painelComprovantes.add(lblNewLabel_16, "cell 4 28,alignx right,aligny top");

				entDescricaoDocumento = new JTextArea();
				entDescricaoDocumento.setFont(new Font("Arial", Font.BOLD, 16));
				painelComprovantes.add(entDescricaoDocumento, "cell 6 26 5 3,grow");

				JLabel lblNewLabel_17 = new JLabel("Arquivo:");
				lblNewLabel_17.setFont(new Font("Arial", Font.BOLD, 16));
				lblNewLabel_17.setForeground(Color.WHITE);
				painelComprovantes.add(lblNewLabel_17, "cell 4 30,alignx right,aligny center");

				entCaminhoDocumento = new JTextField();
				entCaminhoDocumento.setFont(new Font("Arial", Font.BOLD, 16));
				painelComprovantes.add(entCaminhoDocumento, "cell 6 30 3 1,grow");
				entCaminhoDocumento.setColumns(10);

				btnSelecionarDocumento = new JButton("Selecionar");
				btnSelecionarDocumento.setBackground(new Color(0, 0, 102));
				btnSelecionarDocumento.setForeground(Color.WHITE);
				btnSelecionarDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
				painelComprovantes.add(btnSelecionarDocumento, "cell 10 30,alignx left,aligny center");
				botoes.add(btnSelecionarDocumento);

				btnAdicionarDocumento = new JButton("Adicionar");
				btnAdicionarDocumento.setBackground(new Color(0, 51, 0));
				btnAdicionarDocumento.setForeground(Color.WHITE);
				btnAdicionarDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
				painelComprovantes.add(btnAdicionarDocumento, "cell 10 32,grow");
				botoes.add(btnAdicionarDocumento);

				btnImportarDeTerceiros_1 = new JButton("Importar de Terceiros");
				btnImportarDeTerceiros_1.setForeground(Color.WHITE);
				btnImportarDeTerceiros_1.setBackground(new Color(0, 0, 153));
				btnImportarDeTerceiros_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
				btnImportarDeTerceiros_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						importarDistrato();
					}
				});
				botoes.add(btnImportarDeTerceiros_1);

				painelComprovantes.add(btnImportarDeTerceiros_1, "cell 4 14,growx,aligny top");

				JLabel lblNewLabel_35 = new JLabel("Inserir Documentos");
				lblNewLabel_35.setOpaque(true);
				lblNewLabel_35.setBackground(new Color(0, 51, 0));
				lblNewLabel_35.setFont(new Font("SansSerif", Font.BOLD, 18));
				lblNewLabel_35.setForeground(Color.WHITE);
				painelComprovantes.add(lblNewLabel_35, "cell 4 20 7 1,grow");

				JPanel panel_1_1_1 = new JPanel();
				panel_1_1_1.setBackground(new Color(153, 153, 51));
				painelComprovantes.add(panel_1_1_1, "cell 13 30 1 5,growx,aligny top");
				GridBagLayout gbl_panel_1_1_1 = new GridBagLayout();
				gbl_panel_1_1_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
				gbl_panel_1_1_1.rowHeights = new int[] { 0, 0, 0, 0 };
				gbl_panel_1_1_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
						Double.MIN_VALUE };
				gbl_panel_1_1_1.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
				panel_1_1_1.setLayout(gbl_panel_1_1_1);

				JLabel lblNewLabel_36 = new JLabel("Documentos Fisicos");
				lblNewLabel_36.setOpaque(true);
				lblNewLabel_36.setFont(new Font("SansSerif", Font.BOLD, 18));
				lblNewLabel_36.setForeground(Color.WHITE);
				lblNewLabel_36.setBackground(new Color(0, 51, 0));
				GridBagConstraints gbc_lblNewLabel_36 = new GridBagConstraints();
				gbc_lblNewLabel_36.gridwidth = 4;
				gbc_lblNewLabel_36.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_36.gridx = 1;
				gbc_lblNewLabel_36.gridy = 0;
				panel_1_1_1.add(lblNewLabel_36, gbc_lblNewLabel_36);

				JButton btnAbrirPasta = new JButton("Abrir Pasta");
				btnAbrirPasta.setForeground(Color.WHITE);
				btnAbrirPasta.setBackground(new Color(0, 0, 102));
				btnAbrirPasta.setFont(new Font("SansSerif", Font.BOLD, 16));
				GridBagConstraints gbc_btnAbrirPasta = new GridBagConstraints();
				gbc_btnAbrirPasta.insets = new Insets(0, 0, 0, 5);
				gbc_btnAbrirPasta.gridx = 3;
				gbc_btnAbrirPasta.gridy = 2;
				panel_1_1_1.add(btnAbrirPasta, gbc_btnAbrirPasta);
				btnAbrirPasta.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							servidor_unidade = configs_globais.getServidorUnidade();

							Runtime.getRuntime().exec(
									"explorer " + servidor_unidade + contrato_local.getCaminho_diretorio_contrato());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				JButton btnBackup = new JButton("Backup");
				btnBackup.setForeground(Color.WHITE);
				btnBackup.setBackground(new Color(0, 51, 0));
				btnBackup.setFont(new Font("SansSerif", Font.BOLD, 16));
				GridBagConstraints gbc_btnBackup = new GridBagConstraints();
				gbc_btnBackup.insets = new Insets(0, 0, 0, 5);
				gbc_btnBackup.gridx = 5;
				gbc_btnBackup.gridy = 2;
				panel_1_1_1.add(btnBackup, gbc_btnBackup);
				btnAdicionarDocumento.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (contrato_local.getStatus_contrato() != 3)
							adicionarNovoDocumento();

					}
				});
				btnSelecionarDocumento.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (contrato_local.getStatus_contrato() != 3)
							selecionarDocumento();
					}
				});

				setInformacoesAditivos();

			}
		});

	}

	public void setPainelRelatoria() {
		JPanel painelRelatoria = new JPanel();
		painelRelatoria.setBackground(new Color(0, 255, 153));
		painelRelatoria.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelComprovantes.add(painelRelatoria, "cell 13 1 1 28,grow");
		painelRelatoria.setLayout(new MigLayout("",
				"[32px, grow][72px, grow][12px, grow][79px, grow][7px, grow][59px, grow]",
				"[14px, grow][19px, grow][162px, grow][18px, grow][18px, grow][18px, grow][18px, grow][18px, grow][28px, grow][18px, grow][28px, grow]"));

		JLabel lblNewLabel_25 = new JLabel("Relatorios");
		painelRelatoria.add(lblNewLabel_25, "cell 1 0 5 1,alignx center,growy");
		lblNewLabel_25.setFont(new Font("Arial", Font.PLAIN, 20));

		JButton btnSimplificado = new JButton("Gerar");
		btnSimplificado.setBackground(new Color(0, 51, 0));
		btnSimplificado.setForeground(Color.WHITE);
		btnSimplificado.setFont(new Font("Arial", Font.BOLD, 16));
		painelRelatoria.add(btnSimplificado, "cell 5 8,alignx left,aligny center");

		JLabel lblNewLabel_26 = new JLabel("Tipo:");
		lblNewLabel_26.setFont(new Font("Arial", Font.PLAIN, 20));
		painelRelatoria.add(lblNewLabel_26, "cell 0 1,alignx left,aligny top");
		JCheckBox chckbxExterno = new JCheckBox("Externo");
		chckbxExterno.setFont(new Font("SansSerif", Font.BOLD, 16));
		JCheckBox chkbxInterno = new JCheckBox("Interno");
		chkbxInterno.setFont(new Font("SansSerif", Font.BOLD, 16));
		JCheckBox chckbxIncluirGanhosPotenciais = new JCheckBox("Incluir Ganhos Potenciais");
		chckbxIncluirGanhosPotenciais.setFont(new Font("Arial", Font.BOLD, 16));
		JCheckBox chckbxIncluirComprovantesPagamentos = new JCheckBox("Incluir Comprovantes");
		chckbxIncluirComprovantesPagamentos.setFont(new Font("Arial", Font.BOLD, 16));
		chckbxIncluirComprovantesPagamentos.setEnabled(false);
		JCheckBox chckbxIncluirTransferencias = new JCheckBox("Incluir Transferencias");
		chckbxIncluirTransferencias.setFont(new Font("Arial", Font.BOLD, 16));
		JCheckBox chckbxIncluirPagamentos = new JCheckBox("Incluir Pagamentos");
		chckbxIncluirPagamentos.setFont(new Font("Arial", Font.BOLD, 16));

		JPanel painelOpcaoInternas = new JPanel();
		painelOpcaoInternas.setBackground(new Color(51, 255, 204));
		painelOpcaoInternas.setBorder(new LineBorder(new Color(0, 0, 0)));

		chckbxExterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxExterno.isSelected()) {
					chkbxInterno.setSelected(false);
					chckbxExterno.setSelected(true);
					painelOpcaoInternas.setVisible(false);
					chckbxIncluirTransferencias.setEnabled(false);
					chckbxIncluirTransferencias.setSelected(false);

				} else {
					chkbxInterno.setSelected(true);
					chckbxExterno.setSelected(false);
					painelOpcaoInternas.setVisible(true);

					if (chckbxIncluirPagamentos.isSelected()) {
						chckbxIncluirTransferencias.setEnabled(true);

					} else {
						chckbxIncluirTransferencias.setEnabled(false);

					}

				}
			}
		});

		chkbxInterno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkbxInterno.isSelected()) {
					chkbxInterno.setSelected(true);
					chckbxExterno.setSelected(false);

					painelOpcaoInternas.setVisible(true);

				} else {
					chkbxInterno.setSelected(false);
					chckbxExterno.setSelected(true);
					painelOpcaoInternas.setVisible(false);

				}
			}
		});
		painelRelatoria.add(chkbxInterno, "cell 1 1,alignx right,aligny bottom");
		painelRelatoria.add(chckbxExterno, "cell 3 1,alignx left,aligny bottom");
		painelRelatoria.add(painelOpcaoInternas, "cell 0 2 6 1,grow");

		JCheckBox chckbxIncluirSubContratos = new JCheckBox("Incluir Sub-Contratos");
		chckbxIncluirSubContratos.setFont(new Font("Arial", Font.BOLD, 16));
		chckbxIncluirSubContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxIncluirSubContratos.isSelected()) {
					chckbxIncluirSubContratos.setSelected(true);
					chckbxIncluirGanhosPotenciais.setEnabled(true);
				} else {
					chckbxIncluirSubContratos.setSelected(false);
					chckbxIncluirGanhosPotenciais.setEnabled(false);
				}

			}
		});
		painelOpcaoInternas
				.setLayout(new MigLayout("", "[191px, grow]", "[16px, grow][18px, grow][18px, grow][18px, grow]"));
		painelOpcaoInternas.add(chckbxIncluirSubContratos, "cell 0 2,alignx left,aligny top");

		JCheckBox chckbxIncluirComisso = new JCheckBox("Incluir Comissão");
		chckbxIncluirComisso.setFont(new Font("Arial", Font.BOLD, 16));
		painelOpcaoInternas.add(chckbxIncluirComisso, "cell 0 1,alignx left,aligny top");

		chckbxIncluirGanhosPotenciais.setEnabled(false);
		painelOpcaoInternas.add(chckbxIncluirGanhosPotenciais, "cell 0 3,alignx right,aligny top");

		JLabel lblNewLabel_27 = new JLabel("Opções relatorio interno:");
		lblNewLabel_27.setFont(new Font("Arial", Font.PLAIN, 20));
		painelOpcaoInternas.add(lblNewLabel_27, "cell 0 0,alignx left,aligny top");

		JCheckBox chckbxIncluirCarregamento = new JCheckBox("Incluir Carregamentos");
		chckbxIncluirCarregamento.setFont(new Font("Arial", Font.BOLD, 16));
		painelRelatoria.add(chckbxIncluirCarregamento, "cell 1 4 3 1,growx,aligny top");

		chckbxIncluirPagamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxIncluirPagamentos.isSelected()) {
					chckbxIncluirPagamentos.setSelected(true);
					chckbxIncluirComprovantesPagamentos.setEnabled(true);

					if (chkbxInterno.isSelected()) {
						chckbxIncluirTransferencias.setEnabled(true);
					} else {
						chckbxIncluirTransferencias.setEnabled(false);
						chckbxIncluirTransferencias.setSelected(false);

					}

				} else {
					chckbxIncluirPagamentos.setSelected(false);
					chckbxIncluirComprovantesPagamentos.setEnabled(false);
					chckbxIncluirTransferencias.setEnabled(false);
					chckbxIncluirTransferencias.setSelected(false);

				}

			}
		});
		painelRelatoria.add(chckbxIncluirPagamentos, "cell 1 5 3 1,growx,aligny top");
		painelRelatoria.add(chckbxIncluirComprovantesPagamentos, "cell 1 6 5 1,alignx center,aligny top");

		chckbxIncluirTransferencias.setEnabled(false);
		painelRelatoria.add(chckbxIncluirTransferencias, "cell 1 7 5 1,alignx center,aligny top");

		chckbxIncluirRecebimentos = new JCheckBox("Incluir Recebimentos");
		chckbxIncluirRecebimentos.setFont(new Font("Arial", Font.BOLD, 16));
		painelRelatoria.add(chckbxIncluirRecebimentos, "cell 1 3 3 1,growx,aligny top");

		JLabel lblNewLabel_25_1 = new JLabel("Relatorio Editavel(Excel)");
		painelRelatoria.add(lblNewLabel_25_1, "cell 1 9 5 1,alignx left,aligny top");
		lblNewLabel_25_1.setFont(new Font("Arial", Font.BOLD, 16));

		JButton btnEditavel = new JButton("Gerar");
		btnEditavel.setForeground(Color.WHITE);
		btnEditavel.setBackground(new Color(0, 0, 153));
		btnEditavel.setFont(new Font("Arial", Font.BOLD, 16));
		painelRelatoria.add(btnEditavel, "cell 5 10,alignx left,aligny center");
		btnEditavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RelatorioContratoIndividualExcel relatorio = new RelatorioContratoIndividualExcel(contrato_local);

				if (chkbxInterno.isSelected()) {
					relatorio.setExterno(false);
					relatorio.setInterno(true);

					if (chckbxIncluirSubContratos.isSelected()) {
						relatorio.setIncluir_sub_contratos(true);

						if (chckbxIncluirGanhosPotenciais.isSelected()) {
							relatorio.setIncluir_ganhos_potenciais(true);
						} else {
							relatorio.setIncluir_ganhos_potenciais(false);

						}

					} else {
						relatorio.setIncluir_sub_contratos(false);
						relatorio.setIncluir_ganhos_potenciais(false);

					}

					if (chckbxIncluirComisso.isSelected()) {
						relatorio.setIncluir_comissao(true);
					} else {
						relatorio.setIncluir_comissao(false);

					}

				} else if (chckbxExterno.isSelected()) {
					relatorio.setExterno(true);
					relatorio.setInterno(false);

					relatorio.setIncluir_comissao(false);
					relatorio.setIncluir_ganhos_potenciais(false);
					relatorio.setIncluir_sub_contratos(false);

				}

				if (chckbxIncluirRecebimentos.isSelected()) {
					relatorio.setIncluir_recebimentos(true);
				} else {
					relatorio.setIncluir_recebimentos(false);

				}

				if (chckbxIncluirCarregamento.isSelected()) {
					relatorio.setIncluir_carregamento(true);
				} else {
					relatorio.setIncluir_carregamento(false);

				}

				if (chckbxIncluirPagamentos.isSelected()) {
					relatorio.setIncluir_pagamento(true);

					if (chckbxIncluirComprovantesPagamentos.isSelected()) {
						relatorio.setIncluir_comprovantes_pagamentos(true);

					} else {
						relatorio.setIncluir_comprovantes_pagamentos(false);

					}

					if (chckbxIncluirTransferencias.isSelected()) {
						relatorio.setIncluir_transferencias(true);

					} else {
						relatorio.setIncluir_transferencias(false);

					}

				} else {
					relatorio.setIncluir_pagamento(false);
					relatorio.setIncluir_comprovantes_pagamentos(false);
					relatorio.setIncluir_transferencias(false);

				}

				relatorio.gerar();

			}
		});

		btnSimplificado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RelatorioContratoIndividual relatorio = new RelatorioContratoIndividual(contrato_local);

				if (chkbxInterno.isSelected()) {
					relatorio.setExterno(false);
					relatorio.setInterno(true);

					if (chckbxIncluirSubContratos.isSelected()) {
						relatorio.setIncluir_sub_contratos(true);

						if (chckbxIncluirGanhosPotenciais.isSelected()) {
							relatorio.setIncluir_ganhos_potenciais(true);
						} else {
							relatorio.setIncluir_ganhos_potenciais(false);

						}

					} else {
						relatorio.setIncluir_sub_contratos(false);
						relatorio.setIncluir_ganhos_potenciais(false);

					}

					if (chckbxIncluirComisso.isSelected()) {
						relatorio.setIncluir_comissao(true);
					} else {
						relatorio.setIncluir_comissao(false);

					}

				} else if (chckbxExterno.isSelected()) {
					relatorio.setExterno(true);
					relatorio.setInterno(false);

					relatorio.setIncluir_comissao(false);
					relatorio.setIncluir_ganhos_potenciais(false);
					relatorio.setIncluir_sub_contratos(false);

				}

				if (chckbxIncluirRecebimentos.isSelected()) {
					relatorio.setIncluir_recebimentos(true);
				} else {
					relatorio.setIncluir_recebimentos(false);

				}

				if (chckbxIncluirCarregamento.isSelected()) {
					relatorio.setIncluir_carregamento(true);
				} else {
					relatorio.setIncluir_carregamento(false);

				}

				if (chckbxIncluirPagamentos.isSelected()) {
					relatorio.setIncluir_pagamento(true);

					if (chckbxIncluirComprovantesPagamentos.isSelected()) {
						relatorio.setIncluir_comprovantes_pagamentos(true);

					} else {
						relatorio.setIncluir_comprovantes_pagamentos(false);

					}

					if (chckbxIncluirTransferencias.isSelected()) {
						relatorio.setIncluir_transferencias(true);

					} else {
						relatorio.setIncluir_transferencias(false);

					}

				} else {
					relatorio.setIncluir_pagamento(false);
					relatorio.setIncluir_comprovantes_pagamentos(false);
					relatorio.setIncluir_transferencias(false);

				}

				Date hoje = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				contrato_local.setData_contrato(df.format(hoje));
				System.out.println("Preparando para elaborar novo relatorio contrato individual");
				ByteArrayOutputStream contrato_alterado = relatorio.preparar();

				ConverterPdf converter_pdf = new ConverterPdf();
				String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
				TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, contrato_local,
						isto);
			}
		});
	}

	public void atualizarArvoreDocumentos() {

		new Thread() {
			@Override
			public void run() {
				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentos(contrato_local.getId());

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

						root.removeAllChildren();

						no_assinaturas.removeAllChildren();
						no_pagamentos.removeAllChildren();
						no_carregamentos.removeAllChildren();
						no_outros.removeAllChildren();

						no_assinaturas = new DefaultMutableTreeNode("Assinaturas");
						no_pagamentos = new DefaultMutableTreeNode("Pagamentos");
						no_carregamentos = new DefaultMutableTreeNode("Carregamentos");
						no_outros = new DefaultMutableTreeNode("Outros");

						root.add(no_assinaturas);
						root.add(no_pagamentos);
						root.add(no_carregamentos);
						root.add(no_outros);

						if (lista_docs != null && lista_docs.size() > 0) {
							for (CadastroDocumento doc : lista_docs) {
								if (doc.getTipo() == 1) {
									// model.insertNodeInto(new DefaultMutableTreeNode(doc.getNome()), root,
									// root.getChildCount());

									no_assinaturas.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 2) {
									// pagamentos
									no_pagamentos.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 3) {
									// carregamentos
									no_carregamentos.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 4) {
									// outros
									no_outros.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								}

							}
						}
						model.reload(); // this notifies the listeners and changes the GUI

						expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

					}

				});

			}
		}.start();

	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public void criarAbaSubContrato() {

		tabela_sub_contratos = new JTable(modelo_sub_contratos);
		TableCellRenderer renderer = new EvenOddRenderer();
		tabela_sub_contratos.setDefaultRenderer(Object.class, renderer);
		// tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_sub_contratos.setBackground(new Color(255, 255, 255));

		modelo_sub_contratos.addColumn("ID");
		modelo_sub_contratos.addColumn("Código");
		modelo_sub_contratos.addColumn("Status");

		modelo_sub_contratos.addColumn("Quantidade");
		modelo_sub_contratos.addColumn("Medida");
		modelo_sub_contratos.addColumn("Produto");
		modelo_sub_contratos.addColumn("Safra");
		modelo_sub_contratos.addColumn("Valor Produto");
		modelo_sub_contratos.addColumn("Valor Total");
		modelo_sub_contratos.addColumn("Compradores");

		modelo_sub_contratos.addColumn("Vendedores");
		modelo_sub_contratos.addColumn("Corretores");
		modelo_sub_contratos.addColumn("Data do Contrato");

		tabela_sub_contratos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela_sub_contratos.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela_sub_contratos.getColumnModel().getColumn(2).setPreferredWidth(170);
		tabela_sub_contratos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela_sub_contratos.getColumnModel().getColumn(6).setPreferredWidth(70);
		tabela_sub_contratos.getColumnModel().getColumn(7).setPreferredWidth(90);
		tabela_sub_contratos.getColumnModel().getColumn(8).setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(9).setPreferredWidth(150);
		tabela_sub_contratos.getColumnModel().getColumn(10).setPreferredWidth(150);
		tabela_sub_contratos.getColumnModel().getColumn(11).setPreferredWidth(100);
		tabela_sub_contratos.getColumnModel().getColumn(12).setPreferredWidth(80);

		JLabel lblSubcontratos = new JLabel("     Sub-Contratos");
		JButton btnAdicionarSubContrato = new JButton("Adicionar");
		JButton btnSelecionarSubContrato = new JButton("Abrir");

		JScrollPane scrollPaneSubContratos;

		scrollPaneSubContratos = new JScrollPane(tabela_sub_contratos);
		scrollPaneSubContratos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubContratos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubContratos.setBackground(Color.WHITE);
		scrollPaneSubContratos.setAutoscrolls(true);
		scrollPaneSubContratos.setBounds(207, 82, 1004, 132);

		painelSubContratos.add(scrollPaneSubContratos);
		lblSubcontratos.setOpaque(true);
		lblSubcontratos.setForeground(Color.WHITE);
		lblSubcontratos.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSubcontratos.setBackground(new Color(0, 51, 0));
		lblSubcontratos.setBounds(0, 22, 230, 31);

		painelSubContratos.add(lblSubcontratos);
		btnAdicionarSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DadosGlobais dados = DadosGlobais.getInstance();
				dados.setTeraGerenciarContratoPai(isto);
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(1, contrato_local, 0,
						isto);

			}
		});
		btnAdicionarSubContrato.setBounds(1097, 225, 79, 28);

		painelSubContratos.add(btnAdicionarSubContrato);
		btnSelecionarSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela_sub_contratos.getSelectedRow();

				// abre a tela de gerenciar o contrato selecionado na lista de sub contratos
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(
						lista_sub_contratos.get(indiceDaLinha), isto);

			}
		});
		btnSelecionarSubContrato.setBounds(870, 226, 53, 28);

		painelSubContratos.add(btnSelecionarSubContrato);

		JPanel panelPaiArvoreDocumentos = new JPanel();
		panelPaiArvoreDocumentos.setBounds(27, 298, 374, 286);
		painelSubContratos.add(panelPaiArvoreDocumentos);
		panelPaiArvoreDocumentos.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		JLabel lblNewLabel_19 = new JLabel("Arvoré de Contratos");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelPaiArvoreDocumentos.add(lblNewLabel_19, "cell 0 0");

		JPanel panelInformativoSubContrato = new JPanel();
		panelInformativoSubContrato.setBounds(430, 347, 500, 190);
		painelSubContratos.add(panelInformativoSubContrato);

		PainelInformativoSubContratos painelInfo = new PainelInformativoSubContratos();
		panelInformativoSubContrato.add(painelInfo);
		panelInformativoSubContrato.setLayout(null);

		setInformacoesArvoreContratos(panelPaiArvoreDocumentos, painelInfo);

		JButton btnNewButton_3 = new JButton("Importar Sub-Contrato");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo\ndo sub-contrato de terceiros");

				new JFXPanel();
				Platform.runLater(() -> {

					// pegar ultima pasta
					ManipularTxt manipular_ultima_pasta = new ManipularTxt();
					String ultima_pasta = manipular_ultima_pasta
							.lerArquivo(new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"));
					if (d == null) {
						d = new FileChooser();
					}
					d.setInitialDirectory(new File(ultima_pasta));
					File file = d.showOpenDialog(new Stage());
					String caminho_arquivo = "";
					if (file != null) {
						caminho_arquivo = file.getAbsolutePath();

						manipular_ultima_pasta.rescreverArquivo(
								new File("C:\\ProgramData\\E-Contract\\configs\\ultima_pasta.txt"), file.getParent());
						TelaImportarContratoManual tela = new TelaImportarContratoManual(5, contrato_local, 0, file,
								isto);
						tela.setVisible(true);

					}

				});

			}
		});
		btnNewButton_3.setBounds(935, 226, 150, 28);
		painelSubContratos.add(btnNewButton_3);

		painelSubContratos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSubContratos(contrato_local);
			}
		});
	}

	class EvenOddRenderer implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String dados = (String) table.getValueAt(row, 2);

			if (isSelected) {
				renderer.setBackground(Color.blue);

			} else {
				if (dados.equalsIgnoreCase("RECOLHER ASSINATURAS")) {
					renderer.setBackground(Color.white);

				} else if (dados.equalsIgnoreCase("REQUISIÇÃO DE APROVAÇÃO")) {
					renderer.setBackground(Color.yellow);

				}
			}

			return renderer;
		}
	}

	class CarregamentoCellRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			String descricao = (String) table.getValueAt(row, 1);
			String codigo_nf_venda_1 = (String) table.getValueAt(row, 13);
			String codigo_nf_interna = (String) table.getValueAt(row, 11);
			String codigo_nf_complemento = (String) table.getValueAt(row, 16);

			if (descricao != null && descricao.equalsIgnoreCase("-Transferencia")
					|| descricao.equalsIgnoreCase("+Transferencia")) {
				// verifica se é uma transferencia
				if (isSelected) {
					renderer.setBackground(Color.blue);

				} else
					renderer.setBackground(new Color(204, 153, 0));

			} else {
				CadastroContrato.Carregamento carregamento = lista_carregamentos.get(row);

				if (isSelected) {
					renderer.setBackground(Color.blue);

				} else {

					if (carregamento.getNf_complemento_aplicavel() == 1) {

						if (checkString(codigo_nf_complemento)) {
							renderer.setBackground(new Color(0, 100, 0));

						} else {
							renderer.setBackground(Color.yellow);

						}

					} else if (carregamento.getNf_venda1_aplicavel() == 1) {
						if (checkString(codigo_nf_venda_1)) {
							renderer.setBackground(new Color(0, 100, 0));

						} else {
							renderer.setBackground(Color.ORANGE);

						}

					} else if (carregamento.getNf_interna_aplicavel() == 1) {
						if (checkString(codigo_nf_interna)) {
							renderer.setBackground(new Color(0, 100, 0));

						} else {
							renderer.setBackground(new Color(204, 204, 102));
							renderer.setForeground(Color.black);

						}

					} else {
						renderer.setBackground(new Color(0, 100, 0));
						renderer.setForeground(Color.white);

					}

				}
			}
			renderer.setFont(new Font("Tahoma", Font.BOLD, 14));

			return renderer;
		}
	}

	class RecebimentoCellRender implements TableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			((JLabel) renderer).setOpaque(true);

			CadastroContrato.Recebimento recebimento = lista_recebimentos.get(row);

			String codigo_nf_venda = (String) table.getValueAt(row, 4);
			String codigo_nf_remessa = (String) table.getValueAt(row, 7);

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
						renderer.setBackground(Color.white);

					} else {
						renderer.setBackground(Color.yellow);

					}
				} else if (recebimento.getNf_venda_aplicavel() == 0 && recebimento.getNf_remessa_aplicavel() == 0) {
					// nenhum aplicavel
					renderer.setBackground(Color.green);

				}

			}
			renderer.setFont(new Font("Tahoma", Font.BOLD, 16));

			return renderer;
		}
	}

	public void setInformacoesArvoreContratos(JPanel painel_pai, PainelInformativoSubContratos painelInformacoes) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				// create the root node
				DefaultMutableTreeNode root_contratos = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				no_contratos = new DefaultMutableTreeNode("Contrato");
				no_sub_contratos = new DefaultMutableTreeNode("Sub-Contratos");

				// add the child nodes to the root node
				root_contratos.add(no_contratos);
				root_contratos.add(no_sub_contratos);

				// create the tree by passing in the root node
				arvore_contratos = new JTree(root_contratos);

				arvore_contratos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						no_contrato_selecionado = (DefaultMutableTreeNode) arvore_contratos
								.getLastSelectedPathComponent();
						String nome_arquivo = no_contrato_selecionado.getUserObject().toString();
						String quebra[] = nome_arquivo.split("@");
						String id = quebra[0];
						int i_id = Integer.parseInt(id);

						boolean sub_contrato_encontrado = false;

						CadastroContrato cadastro_mostrar = null;

						for (CadastroContrato sub : lista_sub_contratos) {
							if (sub.getId() == i_id) {
								cadastro_mostrar = sub;
								sub_contrato_encontrado = true;
								break;
							}
						}

						if (!sub_contrato_encontrado) {
							painelInformacoes.setInformacoesSubContrato(contrato_local);

						} else {
							painelInformacoes.setInformacoesSubContrato(cadastro_mostrar);

						}
					}
				});

				arvore_contratos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_assinatura = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_assinatura.png"));
					ImageIcon icone_outros = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_outros.png"));

					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
							boolean expanded, boolean isLeaf, int row, boolean focused) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
						String s = node.getUserObject().toString();
						if ("Contrato".equals(s)) {
							setOpenIcon(icone_assinatura);
							setClosedIcon(icone_assinatura);

						} else if ("Sub-Contratos".equals(s)) {
							setOpenIcon(icone_outros);
							setClosedIcon(icone_outros);

						}

						super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, hasFocus);

						return this;
					}

				});

				arvore_contratos.setShowsRootHandles(true);
				arvore_contratos.setRootVisible(false);

				no_contratos.add(new DefaultMutableTreeNode(contrato_local.getId() + "@" + contrato_local.getCodigo()));

				for (CadastroContrato sub : lista_sub_contratos) {
					no_sub_contratos.add(new DefaultMutableTreeNode(sub.getId() + "@" + sub.getCodigo()));

				}

				painel_pai.add(arvore_contratos, "cell 0 1,grow");

				expandAllNodes(arvore_contratos, 0, arvore_contratos.getRowCount());

				painelInformacoes.setInformacoesSubContrato(contrato_local);

			}
		});

	}

	public void atualizarArvoreContratos() {

		new Thread() {
			@Override
			public void run() {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						DefaultTreeModel model = (DefaultTreeModel) arvore_contratos.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

						root.removeAllChildren();

						no_contratos.removeAllChildren();
						no_sub_contratos.removeAllChildren();

						no_contratos = new DefaultMutableTreeNode("Contrato");
						no_sub_contratos = new DefaultMutableTreeNode("Sub-Contratos");

						root.add(no_contratos);
						root.add(no_sub_contratos);

						no_contratos.add(
								new DefaultMutableTreeNode(contrato_local.getId() + "@" + contrato_local.getCodigo()));

						for (CadastroContrato sub : lista_sub_contratos) {
							no_sub_contratos.add(new DefaultMutableTreeNode(sub.getId() + "@" + sub.getCodigo()));

						}

						model.reload(); // this notifies the listeners and changes the GUI

						expandAllNodes(arvore_contratos, 0, arvore_contratos.getRowCount());

					}

				});

			}
		}.start();

	}

	public class PainelInformativoSubContratos extends JPanel {

		private JLabel _lblDataContrato, _lblCorretor, _lblCompradores, _lblVendedores, _lblValorSaco, _lblQuantidade,
				_lblValorTotal;

		private JPanel isto;

		public PainelInformativoSubContratos() {

			setBackground(Color.WHITE);
			setBounds(0, 0, 500, 190);
			setLayout(new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px]"));

			JLabel _lblNewLabel_5 = new JLabel("Data:");
			_lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
			_lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
			_lblNewLabel_5.setBorder(null);
			_lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_5, "cell 0 0,alignx right,aligny center");

			_lblDataContrato = new JLabel("data contrato");
			_lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblDataContrato, "cell 1 0,alignx center,aligny center");

			JLabel _lblNewLabel_9 = new JLabel("Corretor:");
			_lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_9, "cell 0 1,alignx right,growy");

			_lblCorretor = new JLabel("corretor");
			_lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCorretor, "cell 1 1,alignx center,aligny center");

			JLabel _lblNewLabel_6 = new JLabel("Compradores:");
			_lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_6, "cell 0 2,alignx right,growy");

			_lblCompradores = new JLabel("compradores");
			_lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCompradores, "cell 1 2,alignx center,aligny center");

			JLabel _lblNewLabel_8 = new JLabel("Vendedores:");
			_lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_8, "cell 0 3,alignx right,aligny center");

			_lblVendedores = new JLabel("vendedores");
			_lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblVendedores, "cell 1 3,alignx center,aligny center");

			JLabel _lblnewvalorsaco = new JLabel("Valor Unidade:");
			_lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblnewvalorsaco, "cell 0 4,alignx right,aligny center");

			_lblValorSaco = new JLabel("valor saco");
			_lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorSaco, "cell 1 4,alignx center,aligny center");

			JLabel _lblNewLabel_10 = new JLabel("Quantidade:");
			_lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_10, "cell 0 5,alignx right,aligny center");

			_lblQuantidade = new JLabel("quantidade");
			_lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblQuantidade, "cell 1 5,alignx center,aligny center");

			JLabel _lblNewLabel_11 = new JLabel("Valor Total:");
			_lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_11, "cell 0 6,alignx right,aligny center");

			_lblValorTotal = new JLabel("valortotal");
			_lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorTotal, "cell 1 6,alignx center,aligny center");

			isto = this;

			// setarInformacoesPainelCarregamentosInformativos();
		}

		public void setInformacoesSubContrato(CadastroContrato sub_contrato) {

			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {

					_lblDataContrato.setText(sub_contrato.getData_contrato());

					CadastroCliente compradores[] = sub_contrato.getCompradores();
					CadastroCliente vendedores[] = sub_contrato.getVendedores();
					CadastroCliente corretores[] = sub_contrato.getCorretores();

					String nome_corretores = "";
					String nome_vendedores = "";
					String nome_compradores = "";

					if (corretores[0] != null) {
						if (corretores[0].getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_corretores = corretores[0].getNome_empresarial();
						} else {
							nome_corretores = corretores[0].getNome_fantaia();

						}
					}
					_lblCorretor.setText(nome_corretores);

					if (compradores[0] != null) {
						if (compradores[0].getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_compradores = compradores[0].getNome_empresarial();
						} else {
							nome_compradores = compradores[0].getNome_fantaia();

						}
					}

					if (compradores[1] != null) {
						if (compradores[1].getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_compradores = nome_compradores + ", " + compradores[1].getNome_empresarial();
						} else {
							nome_compradores = nome_compradores + ", " + compradores[1].getNome_fantaia();

						}
					}
					_lblCompradores.setText(nome_compradores);

					for (CadastroCliente vendedor : vendedores) {
						if (vendedor != null) {
							if (vendedor.getTipo_pessoa() == 0) {
								// pessoa fisica
								nome_vendedores += vendedor.getNome_empresarial();
							} else {
								nome_vendedores += vendedor.getNome_fantaia();

							}
							nome_vendedores += " ,";

						}
					}
					_lblVendedores.setText(nome_vendedores);

					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(sub_contrato.getValor_produto());

					_lblValorSaco.setText(valorString);

					NumberFormat z = NumberFormat.getNumberInstance();

					_lblQuantidade.setText(z.format(sub_contrato.getQuantidade()) + " " + sub_contrato.getMedida());

					String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(sub_contrato.getValor_a_pagar());
					_lblValorTotal.setText(valor_total);

					isto.repaint();
					isto.updateUI();

				}
			});
		}
	}

	public ChartPanel criarGrafico() {

		DefaultPieDataset pizza = new DefaultPieDataset();

		ArrayList<Double> somatoria_sub_contratos = new ArrayList<>();

		for (CadastroContrato sub : lista_sub_contratos) {

			double quantidade_sacos = 0;
			double quantidade_quilogramas = 0;

			if (sub.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = sub.getQuantidade();
				quantidade_quilogramas = sub.getQuantidade() * 60;
			} else if (sub.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas = sub.getQuantidade();
				quantidade_sacos = sub.getQuantidade() / 60;

			}
			somatoria_sub_contratos.add(quantidade_sacos);
		}

		double somatoria_total = 0;
		for (Double i : somatoria_sub_contratos) {
			somatoria_total += i;
		}

		// aquantidade total do contrato original
		double quantidade_sacos_contrato_original = 0;
		double quantidade_quilogramas_contrato_original = 0;

		if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos_contrato_original = contrato_local.getQuantidade();
			quantidade_quilogramas_contrato_original = contrato_local.getQuantidade() * 60;
		} else if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade_quilogramas_contrato_original = contrato_local.getQuantidade();
			quantidade_sacos_contrato_original = contrato_local.getQuantidade() / 60;

		}

		double diferenca = 0;
		if (somatoria_total == quantidade_sacos_contrato_original) {

		} else {
			diferenca = quantidade_sacos_contrato_original - somatoria_total;
		}

		for (CadastroContrato sub : lista_sub_contratos) {

			double quantidade_sacos = 0;
			double quantidade_quilogramas = 0;

			if (sub.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = sub.getQuantidade();
				quantidade_quilogramas = sub.getQuantidade() * 60;
			} else if (sub.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas = sub.getQuantidade();
				quantidade_sacos = sub.getQuantidade() / 60;

			}
			pizza.setValue(sub.getCodigo(), quantidade_sacos);
		}

		if (diferenca > 0) {
			pizza.setValue("0000000", diferenca);

		}

		JFreeChart grafico = ChartFactory.createPieChart("Sub-Contratos", pizza, true, true, false);
		ChartPanel painel = new ChartPanel(grafico);
		painel.setBackground(Color.white);
		painel.setBounds(0, 0, 360, 280);

		return painel;

	}

	public void setarPainelGanhosPotenciais() {

		double valor_total_contrato_original = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());
		double valor_total_sub_contratos = 0;

		for (CadastroContrato sub : lista_sub_contratos) {

			double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
			valor_total_sub_contratos += valor_local;
		}
		double valor_total_diferenca_contratos = 0;
		if (valor_total_sub_contratos == 0) {
			valor_total_diferenca_contratos = 0;

		} else {
			valor_total_diferenca_contratos = valor_total_sub_contratos - valor_total_contrato_original;

		}

		double valor_total_comissoes = 0;

		if (contrato_local.getComissao() == 1) {
			double valor_local = Double.parseDouble(contrato_local.getValor_comissao().toPlainString());
			valor_total_comissoes += valor_local;
		}

		for (CadastroContrato sub : lista_sub_contratos) {

			if (sub.getComissao() == 1) {
				double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
				valor_total_comissoes += valor_local;
			}

		}

		double valor_total_ganhos_potenciais = valor_total_diferenca_contratos + valor_total_comissoes;

		Locale ptBr = new Locale("pt", "BR");

		JPanel painelInfoGanhos = new JPanel();
		painelInfoGanhos.setBackground(Color.WHITE);
		painelInfoGanhos.setForeground(Color.WHITE);
		painelInfoGanhos.setBounds(964, 347, 360, 165);
		painelSubContratos.add(painelInfoGanhos);
		painelInfoGanhos.setLayout(new MigLayout("", "[][]", "[][][][][][]"));

		JLabel lblNewLabel_20 = new JLabel("Valor Total Contrato Original:");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_20, "cell 0 0");

		JLabel lblValorTotalContrato = new JLabel("R$ 1.000.000.000.00");
		lblValorTotalContrato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_contrato_original);
		lblValorTotalContrato.setText(valorString);
		painelInfoGanhos.add(lblValorTotalContrato, "cell 1 0");

		JLabel lblNewLabel_22 = new JLabel("Valor Total Sub-Contratos:");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_22, "cell 0 1");

		JLabel lblValorTotalSubContratos = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalSubContratos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalSubContratos, "cell 1 1");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_sub_contratos);
		lblValorTotalSubContratos.setText(valorString);

		JLabel lblNewLabel_21 = new JLabel("Diferença entre Contratos:");
		lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_21, "cell 0 2,alignx left");

		JLabel lblValorTotalDiferencaContratos = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalDiferencaContratos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalDiferencaContratos, "cell 1 2");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_diferenca_contratos);
		lblValorTotalDiferencaContratos.setText(valorString);

		JLabel lblNewLabel_23 = new JLabel("Valor Total das Comissões:");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_23, "cell 0 3");

		JLabel lblValorTotalComissoes = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalComissoes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalComissoes, "cell 1 3,alignx left");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissoes);
		lblValorTotalComissoes.setText(valorString);

		JLabel lblNewLabel_24 = new JLabel("Ganhos em potenciais:");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_24, "cell 0 5");

		JLabel lblValorTotalGanhosPotenciais = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalGanhosPotenciais.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalGanhosPotenciais, "cell 1 5");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ganhos_potenciais);
		lblValorTotalGanhosPotenciais.setText(valorString);
	}

	public void setInformacoesAditivos() {

		modelo_aditivos.setNumRows(0);
		GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();

		lista_aditivos = gerenciar.getAditivos(contrato_local.getId());

		/*
		 * modelo_aditivos.addColumn("Id"); modelo_aditivos.addColumn("Status");
		 * modelo_aditivos.addColumn("Data"); modelo_aditivos.addColumn("Texto");
		 * 
		 */
		for (CadastroAditivo aditivo : lista_aditivos) {
			String data = "", texto = "", nome_arquivo;

			int id = aditivo.getId_aditivo();
			int status = aditivo.getStatus();
			data = aditivo.getData();
			texto = aditivo.getTexto();
			nome_arquivo = aditivo.getNome_arquivo();

			String sts = "";
			if (status == 1) {
				sts = "Recolher Assinatura";
			} else {
				sts = "Assinado";
			}

			modelo_aditivos.addRow(new Object[] { id, sts, data, texto, nome_arquivo });

		}
	}

	public void transferirPagamentoContratual() {

		TelaConfirmarTransferenciaPagamentoContratual tela = new TelaConfirmarTransferenciaPagamentoContratual(0, null,
				contrato_local, isto);
		tela.setTelaPag(isto);
		tela.setVisible(true);

	}

	public void caminho_salvar_comprovante_pagamento(String caminho) {
		this.caminho_salvar_comprovante_pagamento = caminho;
	}

	public void atualizarListaTarefas() {
		getTarefas();
	}

	public void concluir_contrato() {

		if (contrato_local.getSub_contrato() != 1) {
			// verifique se os sub_contratos desde contratos estao finalizados
			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> sub_contratos = gerenciar.getSubContratos(contrato_local.getId());

			boolean tem_contratos_nao_finalizados = false;

			for (CadastroContrato sub : sub_contratos) {
				if (sub.getStatus_contrato() != 3) {
					tem_contratos_nao_finalizados = true;
					break;
				}

			}

			if (!tem_contratos_nao_finalizados) {

				concluir_travar_contrato();

			} else {

				JOptionPane.showMessageDialog(isto,
						"Há sub-contratos deste contrato ainda em abertos!\nFinalize-os para poder concluir o contrato original");

			}
		} else {
			concluir_travar_contrato();
		}

	}

	public void concluir_travar_contrato() {
		if (JOptionPane.showConfirmDialog(isto, "Concluir o contrato e bloquea-ló?", "Concluir",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			boolean pagamento_concluido = false;
			boolean carregamento_concluido = false;
			boolean nf_concluido = false;

			if (registro_pagamento_global.getValor_total_pagamentos_restantes() > 0) {
				pagamento_concluido = false;
			} else {
				pagamento_concluido = true;
			}
			// verifica se o carregamento foi concluido
			if (registro_carregamento_global.getQuantidade_restante() > 0) {
				carregamento_concluido = false;
			} else {
				carregamento_concluido = true;
			}

			// verifica se as nf foram concluidas
			if (registro_carregamento_global.getQuantidade_restante_nf() > 0) {
				nf_concluido = false;
			} else {
				nf_concluido = true;
			}

			if (pagamento_concluido && carregamento_concluido && nf_concluido) {

				TelaFinalizarContrato tela = new TelaFinalizarContrato(isto);
				tela.setTelaPai(isto);
				tela.apresentarContratoCompleto(contrato_local, registro_pagamento_global,
						registro_carregamento_global);
				tela.setVisible(true);
			} else {

				if (JOptionPane.showConfirmDialog(null, "Requisitos do contrato não cumpridos, deseja continuar?",
						"Concluir", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					TelaFinalizarContratoIncompleto tela = new TelaFinalizarContratoIncompleto(isto);
					tela.setTelaPai(isto);
					tela.apresentarContratoIncompleto(contrato_local, registro_pagamento_global,
							registro_carregamento_global);
					tela.setVisible(true);

				} else {

				}

			}

		} else {

		}

	}
	
	
	public void cancelar_contrato() {
		if (JOptionPane.showConfirmDialog(isto, "Cancelar o contrato e bloquea-ló?", "Cancelamento",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			boolean fechar = gerenciar.atualizarStatusContrato(contrato_local.getId(), 4);

			if (fechar) {
				JOptionPane.showMessageDialog(isto, "Contrato Cancelado!");
				travarContrato();
			}else {
				JOptionPane.showMessageDialog(isto, "Contrato não Cancelado!\nConsulte o administrador!" );
			}
		}

	}

	public void travarContrato() {
		atualizarContratoLocal(true);

		if (contrato_local.getStatus_contrato() == 3 || contrato_local.getStatus_contrato() == 0 ||  contrato_local.getStatus_contrato() == 4 ) {
			// contrato concluido ou em aprovacao
			for (JButton btn : botoes) {
				btn.setEnabled(false);

			}

			jPopupMenuTabelCarregamento = null;

			jPopupMenuDocumentos = null;

			jPopupMenuTabelPagamentos = null;

			jPopupMenuTabelAditivos = null;

			jPopupMenuTabelDistratos = null;

		}
		btnImportarDeTerceiros.setEnabled(false);
		btnImportarDeTerceiros_1.setEnabled(false);

		btnAdicionarDocumento.setEnabled(false);

		btnSelecionarDocumento.setEnabled(false);

		// setarInformacoesPainelPrincipal();

	}

	public void destravarContrato() {
		// setarInformacoesPainelPrincipal();

		if (contrato_local.getStatus_contrato() == 3 || contrato_local.getStatus_contrato() == 0 ||  contrato_local.getStatus_contrato() == 4 ) {
			// contrato concluido ou em aprovacao
			for (JButton btn : botoes) {
				btn.setEnabled(true);
			}

		}

		setMenuCarregamento();

		setMenuDocumentos();

		setMenuPagamentos();

		setMenuDistratos();

		setMenuAditivos();

	}

	public void setMenuDocumentos() {

		if (contrato_local.getStatus_contrato() != 3) {
			jPopupMenuDocumentos = new JPopupMenu();
			JMenuItem jMenuItemVizualizar = new JMenuItem();
			JMenuItem jMenuItemExcluir = new JMenuItem();
			JMenuItem jMenuItemEnviar = new JMenuItem();
			jMenuItemEnviar.setText("Enviar");

			jMenuItemVizualizar.setText("Vizualizar");
			jMenuItemExcluir.setText("Excluir");

			jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					String nome_arquivo = no_selecionado.getUserObject().toString();

					String quebra[] = nome_arquivo.split("@");

					String nome_official = "";
					for (int i = 1; i < quebra.length; i++) {
						nome_official += quebra[i];
					}

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato()
							+ "\\" + "comprovantes\\" + nome_official;
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

			jMenuItemExcluir.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este comprovante", "Exclusão",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						String nome_arquivo = no_selecionado.getUserObject().toString();
						String quebra[] = nome_arquivo.split("@");
						String id = quebra[0];
						int i_id = Integer.parseInt(id);

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}
						String unidade_base_dados = configs_globais.getServidorUnidade();
						String caminho_completo = unidade_base_dados + "\\"
								+ contrato_local.getCaminho_diretorio_contrato() + "\\" + "comprovantes\\"
								+ nome_official;

						boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
						if (excluido) {

							GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
							boolean excluir_documento = gerenciar_docs.removerDocumento(i_id);

							if (excluir_documento) {
								JOptionPane.showMessageDialog(isto, "Comprovante Excluido!");
								informar_atualizou();

							} else {
								JOptionPane.showMessageDialog(isto,
										"Arquivo fisico apagado, mas as informações\ndeste comprovante ainda estão no \nConsulte o administrador");

							}

							atualizarArvoreDocumentos();
							informar_atualizou();

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir o comprovante\nConsulte o administrador!");
						}

					} else {

					}
				}
			});

			jMenuItemEnviar.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					String nome_arquivo = no_selecionado.getUserObject().toString();

					String quebra[] = nome_arquivo.split("@");

					String nome_official = "";
					for (int i = 1; i < quebra.length; i++) {
						nome_official += quebra[i];
					}

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato()
							+ "\\" + "comprovantes\\" + nome_official;

					File doc = new File(caminho_completo);

					TelaEscolha tela = new TelaEscolha(2, contrato_local, doc, isto);
					tela.setVisible(true);
				}
			});

			jPopupMenuDocumentos.add(jMenuItemVizualizar);
			jPopupMenuDocumentos.add(jMenuItemExcluir);
			jPopupMenuDocumentos.add(jMenuItemEnviar);

			arvore_documentos.addMouseListener(new java.awt.event.MouseAdapter() {
				// Importe a classe java.awt.event.MouseEvent
				public void mouseClicked(MouseEvent e) {
					// Se o botão direito do mouse foi pressionado
					if (e.getButton() == MouseEvent.BUTTON3) {
						// Exibe o popup menu na posição do mouse.
						jPopupMenuDocumentos.show(arvore_documentos, e.getX(), e.getY());
					}
				}
			});

		}
	}

	public void setMenuPagamentos() {
		jPopupMenuTabelPagamentos = new JPopupMenu();
		JMenuItem jMenuItemInserirComprovantePagamento = new JMenuItem();
		JMenuItem jMenuItemReplicarPagamento = new JMenuItem();

		jMenuItemInserirComprovantePagamento.setText("Inserir Comprovante");
		jMenuItemReplicarPagamento.setText("Replicar Pagamento");

		jMenuItemInserirComprovantePagamento.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				int index = table_pagamentos_contratuais.getSelectedRow();
				String id = table_pagamentos_contratuais.getValueAt(index, 0).toString();

				importarComprovantePagamento(Integer.parseInt(id));
			}
		});

		jMenuItemReplicarPagamento.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				if (contrato_local.getSub_contrato() != 1) {
					int index = table_pagamentos_contratuais.getSelectedRow();

					String id = table_pagamentos_contratuais.getValueAt(index, 0).toString();
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					CadastroContrato.CadastroPagamentoContratual pagamento = gerenciar
							.getPagamentoContratual(Integer.parseInt(id));

					if (pagamento != null) {
						TelaReplicarPagamento replicar = new TelaReplicarPagamento(contrato_local, pagamento, isto);
						// replicar.setTelaPai(isto);
						replicar.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao consultar este pagamento!\nConsulte o administrador do sistema!");
					}
				} else {
					JOptionPane.showMessageDialog(isto,
							"Não é possivel replicar um pagamento a partir de um sub-contrato");

				}

			}
		});

		jPopupMenuTabelPagamentos.add(jMenuItemInserirComprovantePagamento);

		jPopupMenuTabelPagamentos.add(jMenuItemReplicarPagamento);
	}

	public void setMenuCarregamento() {
		jPopupMenuTabelCarregamento = new JPopupMenu();
		JMenuItem jMenuItemDetalhar = new JMenuItem();
		// JMenuItem jMenuItemReplicarCarregamento = new JMenuItem();

		jMenuItemDetalhar.setText("Detalhar");

		jMenuItemDetalhar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				int index = table_carregamento.getSelectedRow();
				String id = table_carregamento.getValueAt(index, 0).toString();
				String descricao = table_carregamento.getValueAt(index, 1).toString();

				TelaDetalharTransferenciaCarga tela = new TelaDetalharTransferenciaCarga(Integer.parseInt(id),
						descricao, isto);
				tela.setVisible(true);

			}
		});

		jPopupMenuTabelCarregamento.add(jMenuItemDetalhar);

	}

	public void setMenuAditivos() {
		if (contrato_local.getStatus_contrato() != 3) {
			jPopupMenuTabelAditivos = new JPopupMenu();
			JMenuItem jMenuItemAssinarAditivo = new JMenuItem();
			JMenuItem jMenuItemRevogarAssinatura = new JMenuItem();

			JMenuItem jMenuItemVizualizarAditivo = new JMenuItem();
			JMenuItem jMenuItemExcluirAditivo = new JMenuItem();

			jMenuItemAssinarAditivo.setText("Assinar");
			jMenuItemVizualizarAditivo.setText("Vizualizar");
			jMenuItemExcluirAditivo.setText("Excluir");
			jMenuItemExcluirAditivo.setText("Excluir");
			jMenuItemRevogarAssinatura.setText("Revogar");

			jMenuItemRevogarAssinatura.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();
					String status = table_aditivos.getValueAt(index, 1).toString();
					String id = table_aditivos.getValueAt(index, 0).toString();

					int i_id = Integer.parseInt(id);

					if (status.equals("Recolher Assinatura")) {
						JOptionPane.showMessageDialog(isto, "O aditivo não esta assinado!");
					} else if (status.equals("Assinado")) {
						revogarAssinaturaAditivo(i_id);
						setInformacoesAditivos();
					}

				}
			});

			jMenuItemAssinarAditivo.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();
					String id = table_aditivos.getValueAt(index, 0).toString();
					int i_id = Integer.parseInt(id);

					assinarAditivo(i_id);
				}
			});

			jMenuItemVizualizarAditivo.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String nome_arquivo = table_aditivos.getValueAt(index, 4).toString();
					String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();

					String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;
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

			jMenuItemExcluirAditivo.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();
					String id = table_aditivos.getValueAt(index, 0).toString();
					String status = table_aditivos.getValueAt(index, 1).toString();
					int i_id = Integer.parseInt(id);

					if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este Aditivo?", "Excluir aditivo",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						if (status.equals("Assinado")) {
							JOptionPane.showMessageDialog(isto, "Revogue a assinatura antes de excluir o aditivo");
						} else {
							GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
							boolean excluir = gerenciar.removerAditivo(contrato_local.getId(), i_id);

							if (excluir) {
								// excluir o arquivo
								String unidade_base_dados = configs_globais.getServidorUnidade();
								String nome_arquivo = table_aditivos.getValueAt(index, 4).toString();
								String caminho_salvar = unidade_base_dados + "\\"
										+ contrato_local.getCaminho_diretorio_contrato();

								String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;

								ManipularTxt manipular = new ManipularTxt();
								boolean arquivo_excluido = manipular.apagarArquivo(caminho_completo);
								if (arquivo_excluido) {
									JOptionPane.showMessageDialog(isto, "O aditivo foi excluido com sucesso!");
									setInformacoesAditivos();

								} else {
									JOptionPane.showMessageDialog(isto,
											"O aditivo foi excluido, porem o arquivo fisico ainda esta salvo\nConsulte o adiministrador");

								}

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir o aditivo\nConsulte o adiministrador");

							}
						}

					} else {

					}

				}
			});

			jPopupMenuTabelAditivos.add(jMenuItemAssinarAditivo);
			jPopupMenuTabelAditivos.add(jMenuItemVizualizarAditivo);
			jPopupMenuTabelAditivos.add(jMenuItemExcluirAditivo);
			jPopupMenuTabelAditivos.add(jMenuItemRevogarAssinatura);

			table_aditivos.addMouseListener(new java.awt.event.MouseAdapter() {
				// Importe a classe java.awt.event.MouseEvent
				public void mouseClicked(MouseEvent e) {
					// Se o botão direito do mouse foi pressionado
					if (e.getButton() == MouseEvent.BUTTON3) {
						// Exibe o popup menu na posição do mouse.
						jPopupMenuTabelAditivos.show(table_aditivos, e.getX(), e.getY());
					}
				}
			});
		}
	}

	public static class RecebimentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int data = 1;
		private final int codigo_romaneio = 2;

		private final int peso_romaneio = 3;
		private final int codigo_nf_venda = 4;
		private final int peso_nf_venda = 5;
		private final int valor_nf_venda = 6;

		private final int codigo_nf_remessa = 7;
		private final int peso_nf_remessa = 8;
		private final int valor_nf_remessa = 9;

		private final String colunas[] = { "ID:", "Data:", "Código Romaneio:", "Peso Romaneio:", "Código NF Venda:",
				"Peso NF Venda:", "Valor NF Venda:", "Código NF Remessa:", "Peso NF Remessa:", "Valor NF Remessa:" };
		private final ArrayList<CadastroContrato.Recebimento> dados = new ArrayList<>();// usamos como dados uma lista
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
			case id:
				return int.class;
			case data:
				return String.class;
			case codigo_romaneio:
				return String.class;
			case peso_romaneio:
				return String.class;
			case codigo_nf_venda:
				return String.class;
			case peso_nf_venda:
				return String.class;
			case valor_nf_venda:
				return String.class;
			case codigo_nf_remessa:
				return String.class;
			case peso_nf_remessa:
				return String.class;
			case valor_nf_remessa:
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
			CadastroContrato.Recebimento recebimento = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
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
		public CadastroContrato.Recebimento getValue(int rowIndex) {
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
		public void onAdd(CadastroContrato.Recebimento nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato.Recebimento> dadosIn) {
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

	public class CarregamentoTableModel extends AbstractTableModel {
		/*
		 * modelo_carregamentos.addColumn("Id Carregamento");
		 * modelo_carregamentos.addColumn("Data");
		 * modelo_carregamentos.addColumn("Contrato Destinado");
		 * modelo_carregamentos.addColumn("Comprador");
		 * modelo_carregamentos.addColumn("Vendedor");
		 * 
		 * modelo_carregamentos.addColumn("Transportador");
		 * modelo_carregamentos.addColumn("Veiculo");
		 * modelo_carregamentos.addColumn("Produto");
		 * modelo_carregamentos.addColumn("Romaneio");
		 * modelo_carregamentos.addColumn("Peso Romaneio");
		 * modelo_carregamentos.addColumn("Peso Restante Nota");
		 * modelo_carregamentos.addColumn("Nota Fiscal");
		 * modelo_carregamentos.addColumn("Caminho Nota Fiscal");
		 */
		// constantes p/identificar colunas
		private final int id = 0;
		private final int descricao = 1;
		private final int data = 2;
		private final int contrato = 3;

		private final int comprador = 4;
		private final int vendedor = 5;
		private final int transportador = 6;

		private final int veiculo = 7;
		private final int produto = 8;
		private final int romaneio = 9;
		private final int peso_romaneio = 10;
		private final int nf_interna = 11;
		private final int peso_nf_interna = 12;
		private final int nf_venda1 = 13;
		private final int peso_nf_venda1 = 14;
		private final int valor_nf_venda1 = 15;
		private final int nf_complemento = 16;
		private final int peso_nf_complemento = 17;
		private final int valor_nf_complemento = 18;
		private final int obs = 19;

		private final String colunas[] = { "ID:", "Descrição", "Data:", "Contrato:", "Comprador:", "Vendedor:",
				"Transportador:", "Veiculo:", "Produto:", "Romaneio", "Peso Romaneio", "NF Interna", "Peso NF Interna",
				"NF Venda 1", "Peso NF Venda 1", "Valor NF Venda 1", "NF Complemento", "Peso NF Complemento",
				"Valor NF Complemento", "Observação" };
		private final ArrayList<CadastroContrato.Carregamento> dados = new ArrayList<>();// usamos como dados uma lista
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
			case id:
				return int.class;
			case descricao:
				return String.class;
			case data:
				return String.class;
			case contrato:
				return String.class;
			case comprador:
				return String.class;
			case vendedor:
				return String.class;
			case transportador:
				return String.class;
			case veiculo:
				return String.class;
			case produto:
				return String.class;
			case romaneio:
				return String.class;
			case peso_romaneio:
				return Double.class;
			case nf_interna:
				return String.class;
			case peso_nf_interna:
				return String.class;
			case nf_venda1:
				return String.class;
			case peso_nf_venda1:
				return Double.class;
			case valor_nf_venda1:
				return String.class;
			case nf_complemento:
				return String.class;
			case peso_nf_complemento:
				return Double.class;
			case valor_nf_complemento:
				return String.class;
			case obs:
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
			CadastroContrato.Carregamento carregamento = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return carregamento.getId_carregamento();
			case descricao: {
				String descricao = carregamento.getDescricao();
				try {
					if (descricao == null) {
						return "Carregamento Normal";

					} else {
						return descricao;
					}
				} catch (Exception e) {
					return "Carregamento Normal";
				}
			}
			case data:
				return carregamento.getData();
			case contrato:
				return carregamento.getCodigo_contrato();
			case comprador: {
				return carregamento.getNome_comprador();
			}
			case vendedor: {
				return carregamento.getNome_vendedor();
			}
			case transportador: {

				return carregamento.getNome_transportador();
			}
			case veiculo: {

				return carregamento.getPlaca_veiculo();
			}
			case produto:
				return carregamento.getNome_produto();
			case romaneio: {
				return carregamento.getCodigo_romaneio();
			}
			case peso_romaneio: {

				return carregamento.getPeso_romaneio();
			}
			case nf_interna: {
				if (carregamento.getNf_interna_aplicavel() == 1) {
					return carregamento.getCodigo_nf_interna();

				} else {
					return "Não Aplicável";
				}
			}
			case peso_nf_interna: {
				if (carregamento.getNf_interna_aplicavel() == 1) {
					return carregamento.getPeso_nf_interna();

				} else {
					return "Não Aplicável";
				}
			}
			case nf_venda1: {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					return carregamento.getCodigo_nf_venda1();

				} else {
					return "Não Aplicável";
				}
			}
			case peso_nf_venda1: {
				if (carregamento.getNf_venda1_aplicavel() == 1) {
					return carregamento.getPeso_nf_venda1();

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
			case nf_complemento: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					return carregamento.getCodigo_nf_complemento();

				} else {
					return "Não Aplicável";
				}
			}
			case peso_nf_complemento: {
				if (carregamento.getNf_complemento_aplicavel() == 1) {
					return carregamento.getPeso_nf_complemento();

				} else {
					return "Não Aplicável";
				}
			}
			case valor_nf_complemento: {

				if (carregamento.getNf_complemento_aplicavel() == 1) {
					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(carregamento.getValor_nf_complemento());
					return valorString;
				} else {
					return "Não Aplicável";
				}

			}
			case obs: {
				return carregamento.getObservacao();
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
			CadastroContrato.Carregamento recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroContrato.Carregamento getValue(int rowIndex) {
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
		public void onAdd(CadastroContrato.Carregamento nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato.Carregamento> dadosIn) {
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

	public void atualizarTabelaTarefas() {
		getTarefas();
	}

	public void addRecebimento(CadastroContrato.Recebimento recebimento) {

	}

	public void importarAditivo() {

		GetData hj = new GetData();
		String hora = hj.getHora().replace(":", " ");

		JOptionPane.showMessageDialog(isto, "Na próxima tela, importe o arquivo original \ndo aditivo a ser importado");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo .PDF", "pdf");
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String nome_arquivo = "aditivo_contrato" + contrato_local.getCodigo() + hora + ".pdf";

			File pasta_documentos = new File(caminho_salvar + "\\documentos\\");
			if (!pasta_documentos.exists())
				manipular.criarDiretorio(pasta_documentos.getAbsolutePath());

			String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;
			JOptionPane.showMessageDialog(isto, "Copiando:\nDe: " + caminho_arquivo + "\nPara: " + caminho_completo);
			boolean copiado = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (copiado) {

				CadastroAditivo aditivo = new CadastroAditivo();
				aditivo.setTexto("");
				aditivo.setData(contrato_local.getData_contrato());
				aditivo.setStatus(1);
				aditivo.setId_contrato_pai(contrato_local.getId());
				aditivo.setNome_arquivo(nome_arquivo);

				GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
				int guardar = gerenciar.inserirAditivo(aditivo);

				if (guardar > 0) {

					JOptionPane.showMessageDialog(isto, "Aditivo importado com sucesso");

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao guardar o aditivo na base de dados!\nConsulte o administrador");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao copiar o arquivo fisico!\nConsulte o administrador");

			}

		} catch (Exception e) {

		}

		atualizarArvoreDocumentos();
		informar_atualizou();

		setInformacoesAditivos();

	}

	public void importarDistrato() {

		GetData hj = new GetData();
		String hora = hj.getHora().replace(":", " ");

		JOptionPane.showMessageDialog(isto,
				"Na próxima tela, importe o arquivo original \ndo distrato a ser importado");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo .PDF", "pdf");
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String nome_arquivo = "distrato_contrato" + contrato_local.getCodigo() + hora + ".pdf";

			manipular.criarDiretorio(caminho_salvar + "\\documentos\\");
			String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;
			boolean copiado = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (copiado) {

				CadastroDistrato distrato = new CadastroDistrato();
				distrato.setTexto("");
				distrato.setData(contrato_local.getData_contrato());
				distrato.setStatus(1);
				distrato.setId_contrato_pai(contrato_local.getId());
				distrato.setNome_arquivo(nome_arquivo);

				GerenciarBancoDistratos gerenciar = new GerenciarBancoDistratos();
				int guardar = gerenciar.inserirDistrato(distrato);

				if (guardar > 0) {

					JOptionPane.showMessageDialog(isto, "Distrato importado com sucesso");

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao guardar o distrato na base de dados!\nConsulte o administrador");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Erro ao copiar o arquivo fisico!\nConsulte o administrador");

			}

		} catch (Exception e) {

		}

		atualizarArvoreDocumentos();
		informar_atualizou();

		setInformacoesDistratos();

	}

	public void criarTabelaAditivos() {
		JPanel panelAditivos = new JPanel();
		panelAditivos.setBackground(new Color(0, 153, 153));
		painelComprovantes.add(panelAditivos, "cell 0 5 9 1,grow");

		modelo_aditivos.addColumn("Id");
		modelo_aditivos.addColumn("Status");
		modelo_aditivos.addColumn("Data");
		modelo_aditivos.addColumn("Texto");
		modelo_aditivos.addColumn("Nome Arquivo");

		table_aditivos = new JTable(modelo_aditivos);
		table_aditivos.setBackground(new Color(153, 255, 255));

		setMenuAditivos();
		panelAditivos.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(table_aditivos);
		scrollPane.setBackground(new Color(0, 153, 153));
		panelAditivos.add(scrollPane);

	}

	public void criarTabelaDistratos() {

		JPanel panelDistratos = new JPanel();
		painelComprovantes.add(panelDistratos, "cell 0 12 9 1,grow");

		modelo_distratos.addColumn("Id");
		modelo_distratos.addColumn("Status");
		modelo_distratos.addColumn("Data");
		modelo_distratos.addColumn("Texto");
		modelo_distratos.addColumn("Nome Arquivo");
		panelDistratos.setLayout(new BorderLayout(0, 0));

		table_distratos = new JTable(modelo_distratos);
		table_distratos.setBackground(new Color(153, 255, 255));

		JScrollPane scrollPane = new JScrollPane(table_distratos);
		panelDistratos.add(scrollPane);

		setMenuDistratos();

	}

	public void setMenuDistratos() {
		if (contrato_local.getStatus_contrato() != 3) {
			jPopupMenuTabelDistratos = new JPopupMenu();
			JMenuItem jMenuItemAssinarDistrato = new JMenuItem();
			JMenuItem jMenuItemRevogarAssinatura = new JMenuItem();

			JMenuItem jMenuItemVizualizarDistrato = new JMenuItem();
			JMenuItem jMenuItemExcluirDistrato = new JMenuItem();

			jMenuItemAssinarDistrato.setText("Assinar");
			jMenuItemVizualizarDistrato.setText("Vizualizar");
			jMenuItemExcluirDistrato.setText("Excluir");
			jMenuItemRevogarAssinatura.setText("Revogar");

			jMenuItemRevogarAssinatura.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_distratos.getSelectedRow();
					String status = table_distratos.getValueAt(index, 1).toString();
					String id = table_distratos.getValueAt(index, 0).toString();

					int i_id = Integer.parseInt(id);

					if (status.equals("Recolher Assinatura")) {
						JOptionPane.showMessageDialog(isto, "O distrato não esta assinado!");
					} else if (status.equals("Assinado")) {
						revogarAssinaturaDistrato(i_id);
						setInformacoesDistratos();
					}

				}
			});

			jMenuItemAssinarDistrato.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_distratos.getSelectedRow();
					String id = table_distratos.getValueAt(index, 0).toString();
					int i_id = Integer.parseInt(id);

					assinarDistrato(i_id);
				}
			});

			jMenuItemVizualizarDistrato.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_distratos.getSelectedRow();

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String nome_arquivo = table_distratos.getValueAt(index, 4).toString();
					String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();

					String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;
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

			jMenuItemExcluirDistrato.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_distratos.getSelectedRow();
					String id = table_distratos.getValueAt(index, 0).toString();
					String status = table_distratos.getValueAt(index, 1).toString();
					int i_id = Integer.parseInt(id);

					if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este distrato?", "Excluir aditivo",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						if (status.equals("Assinado")) {
							JOptionPane.showMessageDialog(isto, "Revogue a assinatura antes de excluir o aditivo");
						} else {
							GerenciarBancoDistratos gerenciar = new GerenciarBancoDistratos();
							boolean excluir = gerenciar.removerDistrato(contrato_local.getId(), i_id);

							if (excluir) {
								// excluir o arquivo
								String unidade_base_dados = configs_globais.getServidorUnidade();
								String nome_arquivo = table_distratos.getValueAt(index, 4).toString();
								String caminho_salvar = unidade_base_dados + "\\"
										+ contrato_local.getCaminho_diretorio_contrato();

								String caminho_completo = caminho_salvar + "\\documentos\\" + nome_arquivo;

								ManipularTxt manipular = new ManipularTxt();
								boolean arquivo_excluido = manipular.apagarArquivo(caminho_completo);
								if (arquivo_excluido) {
									JOptionPane.showMessageDialog(isto, "O distrato foi excluido com sucesso!");
									setInformacoesDistratos();

								} else {
									JOptionPane.showMessageDialog(isto,
											"O distrato foi excluido, porem o arquivo fisico ainda esta salvo\nConsulte o adiministrador");

								}

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir o distrato\nConsulte o adiministrador");

							}
						}

					} else {

					}

				}
			});

			jPopupMenuTabelDistratos.add(jMenuItemAssinarDistrato);
			jPopupMenuTabelDistratos.add(jMenuItemVizualizarDistrato);
			jPopupMenuTabelDistratos.add(jMenuItemExcluirDistrato);
			jPopupMenuTabelDistratos.add(jMenuItemRevogarAssinatura);

			table_distratos.addMouseListener(new java.awt.event.MouseAdapter() {
				// Importe a classe java.awt.event.MouseEvent
				public void mouseClicked(MouseEvent e) {
					// Se o botão direito do mouse foi pressionado
					if (e.getButton() == MouseEvent.BUTTON3) {
						// Exibe o popup menu na posição do mouse.
						jPopupMenuTabelDistratos.show(table_distratos, e.getX(), e.getY());
					}
				}
			});
		}
	}

	public void setInformacoesDistratos() {
		modelo_distratos.setNumRows(0);
		GerenciarBancoDistratos gerenciar = new GerenciarBancoDistratos();

		lista_distratos = gerenciar.getDistratos(contrato_local.getId());

		/*
		 * modelo_aditivos.addColumn("Id"); modelo_aditivos.addColumn("Status");
		 * modelo_aditivos.addColumn("Data"); modelo_aditivos.addColumn("Texto");
		 * 
		 */
		for (CadastroDistrato distrato : lista_distratos) {
			String data = "", texto = "", nome_arquivo;

			int id = distrato.getId_distrato();
			int status = distrato.getStatus();
			data = distrato.getData();
			texto = distrato.getTexto();
			nome_arquivo = distrato.getNome_arquivo();

			String sts = "";
			if (status == 1) {
				sts = "Recolher Assinatura";
			} else if (status == 2) {
				sts = "Assinado";
			}

			modelo_distratos.addRow(new Object[] { id, sts, data, texto, nome_arquivo });

		}
	}

	public void assinarDistrato(int id_distrato) {
		JOptionPane.showMessageDialog(isto,
				"Na próxima tela, importe o arquivo digitalizado\ncom assinatura de todas as partes");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String nome_arquivo = "comprovante_assinatura_distrato_" + id_distrato + "_" + contrato_local.getCodigo()
					+ ".pdf";
			;

			String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				// atualizar status do distrato
				GerenciarBancoDistratos gerenciar = new GerenciarBancoDistratos();
				boolean assinou = gerenciar.atualizarStatusDistrato(2, id_distrato);
				if (assinou) {

					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Comprovante de Assinatura de Distrato");
					novo_documento.setNome("comprovante_distrato");

					novo_documento.setTipo(4);
					novo_documento.setId_pai(id_distrato);
					novo_documento.setNome_arquivo(nome_arquivo);
					novo_documento.setId_contrato_pai(contrato_local.getId());

					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
					int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
					if (cadastrar > 0) {

						JOptionPane.showMessageDialog(isto, "Arquivo copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\nStatus do Distrato Atualizado");

						setInformacoesDistratos();
						atualizarArvoreDocumentos();
						informar_atualizou();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
						// cancelar operacao e excluir o arquivo
						if (manipular.apagarArquivo(caminho_completo)) {

						} else {
							JOptionPane.showMessageDialog(isto,
									"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
						}
					}

				} else {
					JOptionPane.showMessageDialog(isto, "Status do distrato NÃO Atualizado");
					JOptionPane.showMessageDialog(isto,
							"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}

		} catch (Exception e) {

		}
	}

	public void abrirAviso(JFrame pai, String mensagem) {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gds = ge.getScreenDevices();
		GraphicsConfiguration gc = gds[getIndiceTela()].getDefaultConfiguration();
		Rectangle rect = gc.getBounds();

		DisplayMode dm = gds[getIndiceTela()].getDisplayMode();

		if (getIndiceTela() > 0 && getIndiceTela() < 2) {
			JOptionPane pane2 = new JOptionPane(mensagem);
			JDialog dialog2 = pane2.createDialog(pai, "Informação");

			dialog2.getContentPane().setLayout(new MigLayout("", "[]", "[]"));
			dialog2.setModal(true);
			int x = dialog2.getWidth();
			int y = dialog2.getHeight();
			dialog2.setLocation((gds[getIndiceTela()].getDefaultConfiguration().getBounds().width * 3 / 2) - x,
					(gds[getIndiceTela()].getDefaultConfiguration().getBounds().height * 1 / 2) - y);

			dialog2.show();

		} else if (getIndiceTela() == 0) {
			JOptionPane pane2 = new JOptionPane(mensagem);
			JDialog dialog2 = pane2.createDialog(pai, "Informação");

			dialog2.getContentPane().setLayout(new MigLayout("", "[]", "[]"));
			dialog2.setModal(true);
			dialog2.setLocationRelativeTo(null);
			dialog2.show();

		}

	}

	public int getIndiceTela() {

		GraphicsConfiguration config = isto.getGraphicsConfiguration();

		GraphicsDevice myScreen = config.getDevice();

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

		GraphicsDevice[] allScreens = env.getScreenDevices();
		int myScreenIndex = -1;
		for (int i = 0; i < allScreens.length; i++) {
			if (allScreens[i].equals(myScreen)) {
				myScreenIndex = i;
				break;
			}
		}

		return myScreenIndex;
	}

	public void aprovar() {

		if (login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
			if (JOptionPane.showConfirmDialog(isto, "Deseja aprovar o contrato", "Aprovar contrato",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				boolean atualizou = gerenciar.atualizarStatusContrato(contrato_local.getId(), 1);
				if (atualizou) {
					destravarContrato();
					JOptionPane.showMessageDialog(isto, "Contrato aprovado");

				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao aprovar o contrato\nContrate o administrador");

				}

			} else {

			}

		} else {
			JOptionPane.showMessageDialog(isto, "Requer Elevação de Direitos");
		}

	}

	public void abrirAbaTarefasContrato() {

		if (contrato_local.getSub_contrato() == 0 || contrato_local.getSub_contrato() == 3)
			painelPrincipal.setSelectedIndex(5);
		else {
			painelPrincipal.setSelectedIndex(4);

		}
	}

	public void abrirAbaTarefasSubContrato(CadastroContrato sub_contrato) {

		painelPrincipal.setSelectedIndex(1);
		TelaGerenciarContrato tela_sub_contrato = new TelaGerenciarContrato(sub_contrato, isto);

	}

	public void setarInformacoesExtrasAbaPrincipal() {
		textAreaDescricao.setText(contrato_local.getDescricao());
		textAreaObservacoes.setText(contrato_local.getObservacao());

		entFertilizante.setText(contrato_local.getFertilizante());

		cBBrutoLivre.setSelectedItem(contrato_local.getBruto_livre());

		if (contrato_local.getOptante_folha() == 0) {
			cBOptante.setSelectedIndex(0);
		} else {
			cBOptante.setSelectedIndex(1);
			textAreaStatusOpcaoFolha.setText(contrato_local.getStatus_optante_folha());
		}

		statusPenhor.setText(contrato_local.getStatus_penhor());
		entLocalizacao.setText(contrato_local.getLocalizacao());
		cbGrupoParticular.setSelectedIndex(contrato_local.getGrupo_particular());

	}

	public void atualizarContratoAposInformativo() {

		// recebimentos
		pesquisar_recebimentos(false);

		// carregamentos
		pesquisar_carregamentos(false);

		// pagamentos
		pesquisar_pagamentos(false);

		atualizarArvoreDocumentos();

		setInformacoesAditivos();
		setInformacoesDistratos();

		// atualizar demais informacoes
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		contrato_local = gerenciar.getContrato(contrato_local.getId());
		setPagamentos(contrato_local);
		setSubContratos(contrato_local);

		// criar acopia temporaria

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);

		// atualiza os paineis
		setarInformacoesPainelPrincipal();
		setarInformacoesExtrasAbaPrincipal();
		setarInformacoesPainelCarregamentos();
		setarInformacoesPainelRecebimentos();

		// atualiza os subcontratos
		if (contrato_local.getSub_contrato() != 3) {
			setSubContratos(contrato_local);
			atualizarArvoreContratos();
		}

		if (contrato_local.getSub_contrato() == 0 || contrato_local.getSub_contrato() == 5) {
			setarPainelGanhosPotenciais();

		}
		painel_informacoes_tab_principal.setarInformacoesPainelCarregamentosInformativos();
		painel_informacoes_tab_pagamentos.setarInformacoesPainelCarregamentosInformativos();
		getTarefas();

		if (contrato_local.getStatus_contrato() == 3 || contrato_local.getStatus_contrato() == 0) {
			travarContratoSemAtualizar();

		} else {
			destravarContrato();

		}
	}

	public void travarContratoSemAtualizar() {
		atualizarTabelaTarefas();
		atualizarContratoLocal(false);

		if (contrato_local.getStatus_contrato() == 3 || contrato_local.getStatus_contrato() == 0) {
			// contrato concluido ou em aprovacao
			try {
				for (JButton btn : botoes) {
					btn.setEnabled(false);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			}

			jPopupMenuTabelCarregamento = null;

			jPopupMenuDocumentos = null;

			jPopupMenuTabelPagamentos = null;

			jPopupMenuTabelAditivos = null;

			jPopupMenuTabelDistratos = null;

		}

	}

	public String incluir_legenda_transferencias_positivas(CadastroTransferenciaPagamentoContratual transferencia) {

		String texto = "";

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_remetente());

		CadastroCliente compradores[] = contrato.getCompradores();

		String nome_comprador = "";
		if (compradores[0].getTipo_pessoa() == 0)
			nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
		else
			nome_comprador = compradores[0].getNome_fantaia().toUpperCase();

		CadastroCliente vendedores[] = contrato.getVendedores();

		String nome_vendedor = "";
		if (vendedores[0].getTipo_pessoa() == 0)
			nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
		else
			nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();

		texto = "(*) Valor recebido do contrato " + contrato.getCodigo() + " entre " + nome_comprador + " x "
				+ nome_vendedor + " " + contrato.getQuantidade() + " " + contrato.getMedida() + " de "
				+ contrato.getModelo_produto().getNome_produto() + " da safra "
				+ contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita()

		;

		return texto;

	}

	public String incluir_legenda_transferencias_negativas(
			CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia) {
		String texto = "";

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		CadastroContrato contrato = gerenciar.getContrato(transferencia.getId_contrato_destinatario());

		CadastroCliente compradores[] = contrato.getCompradores();

		String nome_comprador = "";
		if (compradores[0].getTipo_pessoa() == 0)
			nome_comprador = compradores[0].getNome_empresarial().toUpperCase();
		else
			nome_comprador = compradores[0].getNome_fantaia().toUpperCase();

		CadastroCliente vendedores[] = contrato.getVendedores();

		String nome_vendedor = "";
		if (vendedores[0].getTipo_pessoa() == 0)
			nome_vendedor = vendedores[0].getNome_empresarial().toUpperCase();
		else
			nome_vendedor = vendedores[0].getNome_fantaia().toUpperCase();

		texto = "(*) Valor transferido para o contrato " + contrato.getCodigo() + " entre " + nome_comprador + " x "
				+ nome_vendedor + " " + contrato.getQuantidade() + " " + contrato.getMedida() + " de "
				+ contrato.getModelo_produto().getNome_produto() + " da safra "
				+ contrato.getModelo_safra().getAno_plantio() + "/" + contrato.getModelo_safra().getAno_colheita()

		;

		texto = texto + "\n";

		return texto;

	}

	public void setTelaRecebimentos(int id_recebimento) {

		if (contrato_local.getSub_contrato() == 3)
			painelPrincipal.setSelectedIndex(1);
		else {
			painelPrincipal.setSelectedIndex(2);

		}

	}

	public class PagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_pagamento = 0;
		private final int tipo = 1;
		private final int data_pagamento = 2;
		private final int descricao = 3;
		private final int valor_pago = 4;
		private final int valor_por_unidade = 5;
		private final int cobertura = 6;
		private final int depositante = 7;
		private final int conta_depositante = 8;
		private final int favorecido = 9;
		private final int conta_favorecido = 10;
		private final int contrato_remetente = 11;
		private final int contrato_destinatario = 12;

		/*
		 * private final String colunas[] = { "ID Contrato", "Codigo", "Compradores",
		 * "Vendedores", "Produto", "Transgenia", "Safra", "Quantidade",
		 * "Valor por Unidade", "Valor a Pagar", "ID:", "Data:", "Tipo", "Descricao",
		 * "Depositante", "Conta Depositante", "Favorecido", "Conta Favorecido",
		 * "Valor Pago", "Cobertura" };
		 */
		private final String colunas[] = { "ID", "Tipo:", "Data", "Descricao", "Valor Pago", "Valor por Unidade",
				"Cobertura", "Depositante", "Conta Depositante", "Favorecido", "Conta Favorecido", "Contrato Remetente",
				"Contrato Destinatário" };
		Locale ptBr = new Locale("pt", "BR");

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
			case valor_por_unidade:
				return String.class;
			case cobertura:
				return String.class;
			case contrato_remetente:
				return String.class;
			case contrato_destinatario:
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

			case id_pagamento: {
				return pagamento.getId_pagamento();
			}
			case data_pagamento: {
				return pagamento.getData_pagamento();
			}
			case tipo: {

				if (pagamento.getTipo() == 1) {
					return "NORMAL";
				} else if (pagamento.getTipo() == 2) {
					return "COMISSÃO";
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						return "-Transferencia";
					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						return "+Transferencia";
					}

				}

			}
			case descricao: {
				return pagamento.getDescricao().toUpperCase();
			}
			case depositante: {
				return pagamento.getDepositante().toUpperCase();
			}
			case conta_depositante: {
				return pagamento.getConta_bancaria_depositante();
			}
			case favorecido: {
				return pagamento.getFavorecido().toUpperCase();
			}
			case conta_favorecido: {
				return pagamento.getConta_bancaria_favorecido();
			}
			case valor_pago: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(pagamento.getValor_pagamento());

				if (pagamento.getTipo() == 1) {
					return valorString;
				} else if (pagamento.getTipo() == 2) {
					return valorString;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						return "-" + valorString;
					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						return "+" + valorString;
					}

				}

			}
			case valor_por_unidade: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(ct_remetente.getValor_produto());

				if (pagamento.getTipo() == 1) {
					return valorString;
				} else if (pagamento.getTipo() == 2) {
					return valorString;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						return valorString;
					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia
						valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

						return valorString;
					}
				}

			}
			case cobertura: {

				double cobertura = pagamento.getValor_pagamento() / ct_remetente.getValor_produto();
				if (ct_remetente.getMedida().equalsIgnoreCase("KG"))
					cobertura = cobertura / 60;

				String retorno = z.format(cobertura) + " sacos";

				if (pagamento.getTipo() == 1) {
					return retorno;
				} else if (pagamento.getTipo() == 2) {
					return retorno;
				} else if (pagamento.getTipo() == 3) {
					// é uma transferencia
					if (pagamento.getId_contrato_remetente() == contrato_local.getId()) {
						return "-" + retorno;
					} else if (pagamento.getId_contrato_destinatario() == contrato_local.getId()) {
						// é uma transferencia positiva
						// pegar o preco da unidade do contrato que recebeu a transferencia

						cobertura = pagamento.getValor_pagamento() / contrato_local.getValor_produto();
						if (contrato_local.getMedida().equalsIgnoreCase("KG"))
							cobertura = cobertura / 60;
						retorno = z.format(cobertura) + " sacos";
						return "+" + retorno;
					}

				}

			}
			case contrato_remetente: {
				return ct_remetente.getCodigo();
			}
			case contrato_destinatario: {

				return ct_destinatario.getCodigo();
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

}
