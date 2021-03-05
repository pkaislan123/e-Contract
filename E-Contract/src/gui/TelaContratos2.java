package gui;

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

import cadastros.CadastroAditivo;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroContrato.CadastroPagamentoContratual;
import cadastros.CadastroContrato.CadastroTarefa;
import cadastros.CadastroDistrato;
import cadastros.CadastroDocumento;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroNFe;
import cadastros.CadastroPontuacao;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import cadastros.CadastroSafra;
import cadastros.ContaBancaria;
import cadastros.Registros;
import classesExtras.CBProdutoPersonalizado;
import conexaoBanco.GerenciarBancoAditivos;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoDistratos;
import conexaoBanco.GerenciarBancoDocumento;
import conexaoBanco.GerenciarBancoLogin;
import conexaoBanco.GerenciarBancoPontuacao;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoTransferencias;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.EditarWord;
import manipular.ManipularNotasFiscais;
import manipular.ManipularRomaneios;
import manipular.ManipularTxt;
import manipular.Nuvem;
import outros.DadosGlobais;
import outros.GetData;
import outros.JPanelTransparent;
import outros.JTextFieldPersonalizado;
import relatoria.RelatorioContratoIndividual;
import relatoria.RelatorioContratoIndividualExcel;
import tratamento_proprio.Log;
import views_personalizadas.TelaEscolha;

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
import java.awt.Point;
import java.awt.PopupMenu;

import graficos.JPanelGrafico;
import graficos.JPanelGraficoPadrao;
import gui.TelaContratos.EvenOddRenderer;
import gui.TelaRomaneios.RomaneioTableModel;
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

public class TelaContratos2 extends JFrame {

	private JDialog tela_pai;
	private ArrayList<CadastroRomaneio> romaneios_disponivel = new ArrayList<>();
	private JButton btnSelecionarNota, btnCriarDistrato;
	private JTable tabela_sub_contratos;
	private JLabel lblStatusAdicionandoNotas;
	private int contador = 0;
	private FileChooser d;
	private JFileChooser fileChooser_global;
	private ArrayList<String> listadeArquivos = new ArrayList<>();
	private double total_sacos_recebidos = 0, total_kg_recebidos;
	private int total_romaneios_entrada = 0;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JTree arvore_documentos;
	private JTree arvore_contratos;
	private ArrayList<CadastroRomaneio> lista_romaneios_carregamento = new ArrayList<>();
	private ArrayList<CadastroNFe> lista_nf_interna_carregamento = new ArrayList<>();
	private ArrayList<CadastroNFe> lista_nf_venda1_carregamento = new ArrayList<>();
	private ArrayList<CadastroNFe> lista_nf_complemento_carregamento = new ArrayList<>();
	
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

	private DefaultMutableTreeNode no_selecionado;
	InputStream stream = null;
	private JPanel painel_vizualizar;
	private CadastroContrato contrato_local;
	private ArrayList<CadastroContrato> lista_sub_contratos = new ArrayList<>();
	private SwingController controller = null;
	private SwingViewBuilder factory;
	private TelaContratos2 isto;
	private static String servidor_unidade;
	private CadastroCliente cliente_carregamento;
	private CadastroContrato contrato_carregamento;
	private JPanel painelSubContratos = new JPanel();
	private Registros.RegistroCarregamento registro_carregamento_global;
	private Registros.RegistroRecebimento registro_recebimento_global;

	private CadastroNFe nota_fiscal;
	private CadastroCliente transportador = new CadastroCliente();
	private CadastroProduto produto = new CadastroProduto();
	private CadastroContrato.Carregamento carregamento_confirmar = new CadastroContrato.Carregamento();
	private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;
	private ArrayList<CadastroContrato.Carregamento> lista_carregamentos = null;
	private ArrayList<CadastroContrato.Recebimento> lista_recebimentos = null;

	private ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos_contratuais = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_remetente = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_destinatario = null;
	private JPopupMenu jPopupMenuTabelCarregamento;
	private JPopupMenu jPopupMenuTabelPagamentos;
	private JPopupMenu jPopupMenuDocumentos;
	private JPopupMenu jPopupMenuTabelAditivos;
	private JPopupMenu jPopupMenuTabelDistratos;

	private Double peso_total_cargas_nf_venda1 = 0.0 , peso_total_cargas_nf_complemento = 0.0;
	private Double peso_total_cargas = 0.0;
	private JLabel lblNoSelecionado;

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

	
	DefaultTableModel modelo_pagamentos_contratuais = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private JComboBox cBTipoDocumento;
	private JTable table_aditivos;
	private JTable table_distratos;

	private ArrayList<CadastroAditivo> lista_aditivos = new ArrayList<>();
	private ArrayList<CadastroDistrato> lista_distratos = new ArrayList<>();

	public Rectangle getCurrentScreenBounds(Component component) {
		return component.getGraphicsConfiguration().getBounds();
	}

	public TelaContratos2(int i, Window janela_pai) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				Rectangle currentScreen = getCurrentScreenBounds(isto);
				int currentScreenWidth = currentScreen.width; // current screen width
				int currentScreenHeight = currentScreen.height; // current screen height
				// absolute coordinate of current screen > 0 if left of this screen are further
				// screens
				int xOfCurrentScreen = currentScreen.x;

				if (currentScreen.height > 970 || currentScreen.width > 1400)
					isto.setResizable(false);
				else
					isto.setResizable(true);

			}
		});


		// setModal(true);
		// setAlwaysOnTop(true);

		isto = this;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GraphicsConfiguration config = isto.getGraphicsConfiguration();

		GraphicsDevice myScreen = config.getDevice();

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

		DisplayMode dm = myScreen.getDisplayMode();

		if (dm.getHeight() > 800)
			setBounds(0, 0, 1400, 970);
		else
			setBounds(0, 0, 1371, 735);

		

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		GetData data = new GetData();


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

		

		
		modelo_pagamentos_contratuais.addColumn("Id Pagamento");
		modelo_pagamentos_contratuais.addColumn("Descrição");

		modelo_pagamentos_contratuais.addColumn("Data");

		modelo_pagamentos_contratuais.addColumn("Valor");
		modelo_pagamentos_contratuais.addColumn("Depositante");
		modelo_pagamentos_contratuais.addColumn("Conta Depositante");
		modelo_pagamentos_contratuais.addColumn("Favorecido");
		modelo_pagamentos_contratuais.addColumn("Conta Favorecido");

		JPanel odin = new JPanel();
		odin.setBackground(Color.white);
		odin.setBounds(0, 0, 1366, 735);

		// getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		setContentPane(odin);
		odin.setLayout(new BorderLayout(0, 0));
		painelPrincipal = new JTabbedPane();
		odin.add(painelPrincipal);

		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelDadosIniciais.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);

		

		painelDadosIniciais.setLayout(null);
		painelDadosIniciais.setLayout(new BorderLayout(0, 0));

		JPanel painel_pai_di = new JPanel();

		JScrollPane scroolPainelDadosIniciasi = new JScrollPane(painel_pai_di);
		painelDadosIniciais.add(scroolPainelDadosIniciasi);
		GridBagLayout gbl_painel_pai_di = new GridBagLayout();
		gbl_painel_pai_di.columnWidths = new int[]{1343, 0};
		gbl_painel_pai_di.rowHeights = new int[]{876, 0};
		gbl_painel_pai_di.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_painel_pai_di.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		painel_pai_di.setLayout(gbl_painel_pai_di);
		
				JPanel panel_2 = new JPanel();
				panel_2.setBackground(new Color(255, 255, 255));
				panel_2.setLayout(null);
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				painel_pai_di.add(panel_2, gbc_panel_2);


		

		/*
		 * setarInformacoesPainelPrincipal(); setarInformacoesPainelCarregamentos();
		 * pesquisar_carregamentos(); pesquisar_pagamentos();
		 * 
		 * 
		 * 
		 * setSubContratos(contrato_local); if (contrato_local.getSub_contrato() == 0) {
		 * setarPainelGanhosPotenciais();
		 * 
		 * } setInformacoesDocumentos();
		 * 
		 * setInformacoesAditivos();
		 * 
		 * travarContrato();
		 */

		
		
		  System.out.println("");
/*
		  setarInformacoesPainelPrincipal();
		  
		  //recebimentos 
		  pesquisar_recebimentos();
		  setarInformacoesPainelRecebimentos();

		  //carregamentos
		  setarInformacoesPainelCarregamentos();
		  pesquisar_carregamentos();
		  
		  //pagamentos
		  pesquisar_pagamentos();
		 
		  //subContratos
		    setSubContratos(contrato_local);
		   
		  
		  if (contrato_local.getSub_contrato() == 0 || contrato_local.getSub_contrato() == 5 ) { 
		  setarPainelGanhosPotenciais();
		  
		  }
		  
		   setInformacoesDocumentos();
		  
		  criarTabelaAditivos(); 
		  setInformacoesAditivos(); 
		  criarTabelaDistratos();
		  setInformacoesDistratos(); 
		  travarContrato();
		
*/
		this.pack();

		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);
		this.setVisible(true);

	}

	
}
