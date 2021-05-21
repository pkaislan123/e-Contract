package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import net.miginfocom.swing.MigLayout;



import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
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
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
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
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
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

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import java.awt.Insets;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;



import javax.swing.SwingConstants;
import javax.swing.JComboBox;


import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;



public class TelaMain extends JFrame {
	private ConfiguracoesGlobais configs_globais;
	private JPanelBackground contentPane;
	private JLabel lblUser, lblNovaMensagem;
	private JLabel lblDireitos;
	private TelaContratos telaContratos ;
	private JPanelGraficoCarregamento painelGraficoCarregamentos;
	private JPanel panelGraficoLinha;
	private JLabel lblTotalContratosAssinar, lblTotalContratosAssinados;
	private boolean executou = false;
	private DadosContratos dados_contratos = new DadosContratos();
	private DadosCarregamento dados_carregamentos = new DadosCarregamento();
	private DadosRecebimento dados_recebimentos = new DadosRecebimento();
    private ArrayList<CadastroAviso> lista_avisos = new ArrayList<>();
	private int num_tarefas_nesta_secao = -1;
	private JLabel lblNumeroTarefas;
	private JLabel lblBD, lblBaseDeArquivos, lblNuvem, lblnet, urlBancoDados, imgBaseDados, urlBaseArquivos,
			imgBaseArquivos, urlInternet, imgInternet, urlNuvem, imgNuvem;
	private ArrayList<CadastroLogin> usuarios = new ArrayList<>();
	private JComboBox cbContratosPorSafra;
	private boolean notificando = false;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JLabel lblTotalSacosRetirar, lblTotalSacos, lblTotalSacosRetirados;
	private GraficoLinha linha = null;
	TelaPost telaPost;
	private TelaTodasNotasFiscais telaTodasNotasFiscais ;
	private JLabel lblStatusWhatsapp,imgWhatsapp;
	private JLabel lblTotalSacosRecebidos, lblTotalSacosGraficoRecebimento, lblTotalSacosAReceber;
	private JComboBox cbRecebimentosPorSafra;

	private GerenciarBancoContratos gerenciarAtualizarTarefas, gerenciarDadosCarregamento, gerenciarDadosContrato,
			gerenciarCarregamentoPorPeriodo, gerenciarDadosRecebimento;
	private GerenciarBancoPadrao gerenciarBancoPadrao;
	DefaultTableModel modelo_usuarios = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private ChartPanel chartPanel;
	private TelaChat telaChat;
	private DefaultCategoryDataset dataset;
	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaMain isto;
    private JDialog telaPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private JPanelGrafico painelGraficoContratos;
	private  JPanelGraficoRecebimento painelGraficoRecebimento;
	private JTable tabela_avisos;
	private AvisoTableModel modelo_aviso = new AvisoTableModel();
	
	public TelaMain(Window janela_pai) {

		getDadosGlobais();
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/imagens/logo_icone4.png")));
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & isto.MAXIMIZED_BOTH) == isto.MAXIMIZED_BOTH) {
					// pega a resolucao da tela
					Toolkit tk = Toolkit.getDefaultToolkit();
					Dimension d = tk.getScreenSize();
					System.out.println("Screen width = " + d.width);
					System.out.println("Screen height = " + d.height);
					// pega o tamanho da barra de tarefas
					Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
					java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
							.getMaximumWindowBounds();
					int taskBarHeight = scrnSize.height - winSize.height;
					System.out.printf("Altura: %d\n", taskBarHeight);
				}
			}
		});
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
		setResizable(true);
		DadosGlobais dados = DadosGlobais.getInstance();
		dados.setTelaPrincipal(this);
		setTitle("E-Contract");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DisplayMode display =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		int display_x = display.getWidth();
		int display_y = display.getHeight();
		//setBounds(0, 0, d.width, d.height - taskBarHeight);
		setBounds(0, 0, 1382, 761);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		GridBagLayout gbl_painelPrincipal = new GridBagLayout();
		gbl_painelPrincipal.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_painelPrincipal.rowHeights = new int[]{78, 0, 345, 0, 0};
		gbl_painelPrincipal.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_painelPrincipal.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		painelPrincipal.setLayout(gbl_painelPrincipal);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 22;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		painelPrincipal.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.WHITE);
		panel.add(menuBar);
		
		
		JMenu Dados = new JMenu("Cadastros");
		Dados.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_cadastro_menu.png")));
		Dados.setBackground(Color.WHITE);
		Dados.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(Dados);
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.setMargin(new Insets(0, 10, 0, 0));
		mntmClientes.setBackground(Color.WHITE);
		mntmClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/equipe.png")));
		mntmClientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente clientes = new TelaCliente(1, 0, isto);
				clientes.setVisible(true);
			}
		});
		Dados.add(mntmClientes);
		JMenuItem mntmArmazns = new JMenuItem("Armazéns");
		mntmArmazns.setMargin(new Insets(0, 10, 0, 0));
		mntmArmazns.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/silo(1).png")));
		mntmArmazns.setBackground(Color.WHITE);
		mntmArmazns.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmArmazns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaArmazem tela = new TelaArmazem(isto);
			}
		});
		Dados.add(mntmArmazns);
		JMenuItem mntmSafra = new JMenuItem("Safra");
		mntmSafra.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/cultivo.png")));
		mntmSafra.setMargin(new Insets(0, 10, 0, 0));
		mntmSafra.setBackground(Color.WHITE);
		mntmSafra.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmSafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSafra safra = new TelaSafra(isto);
			}
		});
		Dados.add(mntmSafra);
		JMenuItem mntmProdutos = new JMenuItem("Produtos");
		mntmProdutos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/comida.png")));
		mntmProdutos.setMargin(new Insets(0, 10, 0, 0));
		mntmProdutos.setBackground(Color.WHITE);
		mntmProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaProdutos tela = new TelaProdutos(0, isto);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmProdutos);
		JMenuItem mntmUsurios = new JMenuItem("Usuários");
		mntmUsurios.setMargin(new Insets(0, 10, 0, 0));
		mntmUsurios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/usuarios.png")));
		mntmUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaUsuarios usuarios = new TelaUsuarios(0, isto);
				usuarios.setVisible(true);
			}
		});
		Dados.add(mntmUsurios);
		JMenuItem mntmNewMenuItem = new JMenuItem("Transportadores");
		mntmNewMenuItem.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/caminhao.png")));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores tela = new TelaTransportadores(0, null);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmNewMenuItem);
		
		JMenuItem mntmSilos = new JMenuItem("Silos");
		mntmSilos.setMargin(new Insets(0, 10, 0, 0));
		Dados.add(mntmSilos);
		
		JMenuItem mntmClassificadores = new JMenuItem("Classificadores");
		mntmClassificadores.setMargin(new Insets(0, 10, 0, 0));
		Dados.add(mntmClassificadores);
		JMenu mnContratos = new JMenu("Contratos");
		mnContratos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_contrato_menu.png")));
		mnContratos.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnContratos);
		JMenuItem mntmContratos = new JMenuItem("Contratos");
		mntmContratos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/contrato.png")));
		mntmContratos.setMargin(new Insets(0, 10, 0, 0));
		mntmContratos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos telaContratos = null;
				
				if(telaContratos == null) {
				 telaContratos = new TelaContratos(0, isto);
				 telaContratos.setVisible(true);

				}
				else
				 telaContratos.setVisible(true);
				 
				
				/*
				if(telaContratos2 == null) {
					 telaContratos2 = new TelaContratos2(0, isto);
					 telaContratos2.setVisible(true);

					}
					else
					 telaContratos2.setVisible(true);
					 */
			}
		});
		mnContratos.add(mntmContratos);
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Relatoria");
		mntmNewMenuItem_1.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/grafico.png")));
		mntmNewMenuItem_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRelatoriaContratos tela = new TelaRelatoriaContratos(isto);
			}
		});
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Recebimentos");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRecebimentos tela = new TelaRecebimentos(isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_7.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_7.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Pagamentos");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPagamentos tela = new TelaPagamentos(isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_8.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_8.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_8);
		mnContratos.add(mntmNewMenuItem_1);
		JMenu mnFerramentas = new JMenu("Ferramentas");
		mnFerramentas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/ferramentas-de-reparacao.png")));
		mnFerramentas.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnFerramentas);
		JMenu mnPlanilhasDeControle = new JMenu("Planilhas de Controle");
		mnPlanilhasDeControle
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/aplicativo-de-planilha.png")));
		mnPlanilhasDeControle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnPlanilhasDeControle.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mnPlanilhasDeControle);
		JMenuItem mntmAPartirDe = new JMenuItem("a partir de NFe Siare");
		mntmAPartirDe.setMargin(new Insets(0, 10, 0, 0));
		mntmAPartirDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPlanilhaNFe tela = new TelaPlanilhaNFe();
			}
		});
		mnPlanilhasDeControle.add(mntmAPartirDe);
		JMenuItem mntmAPartirDe_1 = new JMenuItem("a partir de NFe Interna");
		mntmAPartirDe_1.setMargin(new Insets(0, 10, 0, 0));
		mntmAPartirDe_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPlanilhaNFeInternas tela = new TelaPlanilhaNFeInternas();
			}
		});
		mnPlanilhasDeControle.add(mntmAPartirDe_1);
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Monitoria");
		mntmNewMenuItem_2.setMargin(new Insets(0, 10, 2, 0));
		mntmNewMenuItem_2
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/aplicativo-de-monitoria.png")));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaMonitoria monitor = new TelaMonitoria(isto);
				monitor.setVisible(true);
			}
		});
		mnFerramentas.add(mntmNewMenuItem_2);
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Anotações");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaNotas notas;
				if (TelaNotas.instance == null) {
					TelaNotas.instance = new TelaNotas(isto);
					TelaNotas.instance.setVisible(true);
				} else {
					TelaNotas.instance.setVisible(true);
				}
			}
		});
		mntmNewMenuItem_3.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_3.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_notas.png")));
		mnFerramentas.add(mntmNewMenuItem_3);
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Calendário");
		mntmNewMenuItem_4.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_calendario.png")));
		mntmNewMenuItem_4.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_4);
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tarefas");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				
				TelaTarefas tela_tarefas = new TelaTarefas(isto);
					tela_tarefas.getTarefas();
					tela_tarefas.setVisible(true);
				
			}
		});
		mntmNewMenuItem_5.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_tarefas.png")));
		mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Finanças");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiro tela = new TelaFinanceiro(isto);
				
				tela.setVisible(true);
				tela.atualizarGrafico();
			}
		});
		mntmNewMenuItem_6.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/financa.png")));
		mntmNewMenuItem_6.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_6_1 = new JMenuItem("Recursos Humanos");
		mntmNewMenuItem_6_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(isto, "Em Construção!");
			}
		});
		mntmNewMenuItem_6_1.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_6_1);
		JMenu mnNewMenu = new JMenu("Configurações");
		mnNewMenu.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/preferencias.png")));
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);
		JMenuItem mntmPastas = new JMenuItem("Preferências");
		mntmPastas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/definicoes.png")));
		mntmPastas.setMargin(new Insets(0, 10, 0, 0));
		mntmPastas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPreferencias tela = new TelaPreferencias();
			}
		});
		mntmPastas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmPastas);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 153, 51));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 13;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 24;
		gbc_panel_1.gridy = 0;
		painelPrincipal.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new MigLayout("", "[][][][][][][]", "[][-18.00][]"));
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (telaPost == null) {
					telaPost = new TelaPost(isto);
				} else {
					telaPost.setVisible(true);
				}
			}
		});
		panel_1.add(lblNewLabel_8, "cell 0 0");
		lblNewLabel_8.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_anotacoes_tela_principal.png")));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				TelaTarefas	tela_tarefas = new TelaTarefas(isto);
					tela_tarefas.getTarefas();
					tela_tarefas.setVisible(true);
				
			}
		});
		panel_1.add(lblNewLabel_1, "cell 1 0");
		lblNewLabel_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_tarefa.png")));
		
		JLabel lblNewLabel_2 = new JLabel("Você tem:");
		panel_1.add(lblNewLabel_2, "cell 2 0");
		
		 lblNumeroTarefas = new JLabel("0");
		panel_1.add(lblNumeroTarefas, "cell 3 0");
		lblNumeroTarefas.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblNewLabel_4 = new JLabel("tarefas");
		panel_1.add(lblNewLabel_4, "cell 4 0");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 38;
		gbc_panel_2.gridy = 0;
		painelPrincipal.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new MigLayout("", "[188px]", "[22px][][]"));
		
		 lblUser = new JLabel();
		panel_2.add(lblUser, "cell 0 0,alignx left,aligny top");
		lblUser.setText("<dynamic> <dynamic>");
		lblUser.setForeground(Color.BLACK);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUser.setBackground(Color.WHITE);
		
		 lblDireitos = new JLabel();
		panel_2.add(lblDireitos, "cell 0 1");
		lblDireitos.setText("Administrador do Sistema");
		lblDireitos.setForeground(Color.BLACK);
		lblDireitos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDireitos.setBackground(Color.WHITE);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{500, -27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 57, 68, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{286, 309, 223, 19, 1, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel_3.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new MigLayout("", "[1px][474.00,grow]", "[1px][266.00,grow]"));
		
		 painelGraficoContratos = new JPanelGrafico(0, 0);
		painelGraficoContratos.setLayout(null);
		panel_4.add(painelGraficoContratos, "cell 1 1,grow");
		
		 lblTotalContratosAssinados = new JLabel("Assinados: 0");
		lblTotalContratosAssinados.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratosAssinados.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratosAssinados.setBounds(10, 57, 173, 24);
		painelGraficoContratos.add(lblTotalContratosAssinados);
		
		 lblTotalContratosAssinar = new JLabel("Assinar: 0");
		lblTotalContratosAssinar.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratosAssinar.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratosAssinar.setBounds(10, 92, 173, 24);
		painelGraficoContratos.add(lblTotalContratosAssinar);
		
		 lblTotalContratos = new JLabel("Total de Contratos: 0");
		 lblTotalContratos.setFont(new Font("Arial", Font.BOLD, 14));
		 lblTotalContratos.setBorder(new EmptyBorder(0, 0, 0, 0));
		 lblTotalContratos.setBounds(10, 22, 173, 24);
		painelGraficoContratos.add(lblTotalContratos);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_contrato.png")));
		lblNewLabel.setBounds(396, 178, 76, 71);
		painelGraficoContratos.add(lblNewLabel);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.gridwidth = 11;
		gbc_panel_5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel_3.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		JLabel lblNewLabel_6 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6.setBackground(new Color(0, 206, 209));
		panel_5.add(lblNewLabel_6, "cell 0 0,grow");
		lblNewLabel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atualizarGraficoContratos();
			}
		});
		
		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();
		cbContratosPorSafra = new JComboBox();
		cbContratosPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbContratosPorSafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
					// procura no banco os contratos de acordo com a safra selecionada
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					int num_contratos_total = gerenciar.consultaContratos(0, safra.getId_safra());
					int num_contratos_assinar = gerenciar.consultaContratos(1, safra.getId_safra());
					// atualizar o grafico
					atualizarGraficoContratos(num_contratos_total, num_contratos_assinar);
				} catch (Exception t) {
				}
			}
		});
		cbContratosPorSafra.setModel(modelSafra);
		cbContratosPorSafra.setRenderer(cBSafraPersonalizado);
		cbContratosPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel_5.add(cbContratosPorSafra, "cell 0 1,growx");
		
		pesquisarSafras();
		for (CadastroSafra safra : safras) {
			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);
		}
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_silos.png")));
		panel_5.add(lblNewLabel_7, "cell 0 2");
		
		JPanel painelInfoConexao = new JPanel();
		painelInfoConexao.setLayout(null);
		painelInfoConexao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelInfoConexao.setBackground(new Color(51, 153, 153));
		GridBagConstraints gbc_painelInfoConexao = new GridBagConstraints();
		gbc_painelInfoConexao.insets = new Insets(0, 0, 5, 0);
		gbc_painelInfoConexao.gridwidth = 11;
		gbc_painelInfoConexao.fill = GridBagConstraints.BOTH;
		gbc_painelInfoConexao.gridx = 13;
		gbc_painelInfoConexao.gridy = 0;
		panel_3.add(painelInfoConexao, gbc_painelInfoConexao);
		
		JLabel lblInfo = new JLabel("Informações de Conexão");
		lblInfo.setForeground(Color.WHITE);
		lblInfo.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblInfo.setBounds(21, 16, 235, 26);
		painelInfoConexao.add(lblInfo);
		
		 lblnet = new JLabel("Internet:");
		 lblnet.setForeground(Color.WHITE);
		 lblnet.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblnet.setBounds(63, 54, 295, 19);
		painelInfoConexao.add(lblnet);
		
		 lblBaseDeArquivos = new JLabel("Base de Arquivos:");
		 lblBaseDeArquivos.setForeground(Color.WHITE);
		 lblBaseDeArquivos.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblBaseDeArquivos.setBounds(63, 152, 295, 14);
		painelInfoConexao.add(lblBaseDeArquivos);
		
		 lblBD = new JLabel("Banco de Dados:");
		 lblBD.setForeground(Color.WHITE);
		 lblBD.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblBD.setBounds(63, 196, 264, 14);
		painelInfoConexao.add(lblBD);
		
		 urlBancoDados = new JLabel("erro");
		 urlBancoDados.setForeground(Color.WHITE);
		 urlBancoDados.setFont(new Font("SansSerif", Font.BOLD, 14));
		urlBancoDados.setBounds(63, 214, 280, 19);
		painelInfoConexao.add(urlBancoDados);
		
		 urlBaseArquivos = new JLabel("erro");
		 urlBaseArquivos.setForeground(Color.WHITE);
		 urlBaseArquivos.setFont(new Font("SansSerif", Font.BOLD, 14));
		urlBaseArquivos.setBounds(63, 171, 295, 19);
		painelInfoConexao.add(urlBaseArquivos);
		
		 urlInternet = new JLabel("http://www.google.com.br");
		 urlInternet.setForeground(Color.WHITE);
		 urlInternet.setFont(new Font("SansSerif", Font.BOLD, 14));
		urlInternet.setBounds(63, 77, 295, 26);
		painelInfoConexao.add(urlInternet);
		
		 imgBaseDados = new JLabel("New label");
		imgBaseDados.setBounds(21, 196, 32, 32);
		painelInfoConexao.add(imgBaseDados);
		
		 imgBaseArquivos = new JLabel("");
		imgBaseArquivos.setBounds(21, 152, 32, 32);
		painelInfoConexao.add(imgBaseArquivos);
		
		 imgInternet = new JLabel("");
		imgInternet.setBounds(21, 59, 32, 32);
		painelInfoConexao.add(imgInternet);
		
		 imgNuvem = new JLabel("");
		imgNuvem.setBounds(21, 109, 32, 32);
		painelInfoConexao.add(imgNuvem);
		
		 lblNuvem = new JLabel("Nuvem");
		 lblNuvem.setForeground(Color.WHITE);
		 lblNuvem.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNuvem.setBounds(63, 109, 311, 14);
		painelInfoConexao.add(lblNuvem);
		
		 urlNuvem = new JLabel("https://www.dropbox.com/");
		 urlNuvem.setForeground(Color.WHITE);
		 urlNuvem.setFont(new Font("SansSerif", Font.BOLD, 14));
		urlNuvem.setBounds(63, 127, 295, 19);
		painelInfoConexao.add(urlNuvem);
		
		JLabel lblWhatsapp = new JLabel("Whatsapp:");
		lblWhatsapp.setForeground(Color.WHITE);
		lblWhatsapp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblWhatsapp.setBounds(63, 245, 264, 14);
		painelInfoConexao.add(lblWhatsapp);
		
		
		 lblStatusWhatsapp = new JLabel("Status:");
		lblStatusWhatsapp.setForeground(Color.WHITE);
		lblStatusWhatsapp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblStatusWhatsapp.setBounds(63, 263, 280, 19);
		painelInfoConexao.add(lblStatusWhatsapp);
		
		 imgWhatsapp = new JLabel("New label");
		imgWhatsapp.setBounds(21, 245, 32, 32);
		painelInfoConexao.add(imgWhatsapp);
		
		 painelGraficoRecebimento = new JPanelGraficoRecebimento(0, 0);
		painelGraficoRecebimento.setLayout(null);
		GridBagConstraints gbc_painelGraficoRecebimento = new GridBagConstraints();
		gbc_painelGraficoRecebimento.insets = new Insets(0, 0, 5, 5);
		gbc_painelGraficoRecebimento.fill = GridBagConstraints.BOTH;
		gbc_painelGraficoRecebimento.gridx = 0;
		gbc_painelGraficoRecebimento.gridy = 1;
		panel_3.add(painelGraficoRecebimento, gbc_painelGraficoRecebimento);
		
		JLabel lblNewLabel_5 = new JLabel("de recebimento, pode não corresponder  a realidade atual do armazém(Estimativa)");
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(6, 282, 467, 16);
		painelGraficoRecebimento.add(lblNewLabel_5);
		
		lblTotalSacosAReceber  = new JLabel("a Receber(sacos): 0");
		lblTotalSacosAReceber.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosAReceber.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacosAReceber.setBounds(0, 71, 253, 24);
		painelGraficoRecebimento.add(lblTotalSacosAReceber);
		
		
		 lblTotalSacosRecebidos = new JLabel("Recebidos(sacos): 0");
		lblTotalSacosRecebidos.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosRecebidos.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacosRecebidos.setBounds(0, 35, 253, 24);
		painelGraficoRecebimento.add(lblTotalSacosRecebidos);
		
		 lblTotalSacosGraficoRecebimento = new JLabel("Quantidade Total(sacos): 0");
		lblTotalSacosGraficoRecebimento.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosGraficoRecebimento.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacosGraficoRecebimento.setBounds(0, -1, 253, 24);
		painelGraficoRecebimento.add(lblTotalSacosGraficoRecebimento);
		
		JLabel lblNewLabel_9_1 = new JLabel("");
		lblNewLabel_9_1.setBounds(128, 149, 64, 75);
		painelGraficoRecebimento.add(lblNewLabel_9_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("Valores Baseados na soma dos pesos liquidos do controle");
		lblNewLabel_5_1.setForeground(Color.RED);
		lblNewLabel_5_1.setBounds(6, 259, 420, 16);
		painelGraficoRecebimento.add(lblNewLabel_5_1);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.gridwidth = 11;
		gbc_panel_7.insets = new Insets(0, 0, 5, 5);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 1;
		panel_3.add(panel_7, gbc_panel_7);
		panel_7.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][]"));
		
		JLabel lblTodasAsSafrasRecebimento = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblTodasAsSafrasRecebimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getDadosRecebimento();
				atualizarGraficoRecebimentos();
			}
		});
		lblTodasAsSafrasRecebimento.setOpaque(true);
		lblTodasAsSafrasRecebimento.setForeground(Color.WHITE);
		lblTodasAsSafrasRecebimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTodasAsSafrasRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTodasAsSafrasRecebimento.setBackground(new Color(0, 206, 209));
		panel_7.add(lblTodasAsSafrasRecebimento, "cell 0 1,growx");
		
		 cbRecebimentosPorSafra = new JComboBox();
		 cbRecebimentosPorSafra.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		try {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
					// procura no banco os contratos de acordo com a safra selecionada
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					double num_total_sacos = gerenciar.getQuantidadeSacosPorSafra(safra.getId_safra());
					double num_total_sacos_carregados = gerenciar
							.getQuantidadeSacosRecebidosPorSafra(safra.getId_safra());
					// atualizar o grafico
					atualizarGraficoRecebimentos(num_total_sacos, num_total_sacos_carregados);
				} catch (Exception t) {
				}
		 		
		 	}
		 });
		cbRecebimentosPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));
		panel_7.add(cbRecebimentosPorSafra, "cell 0 2,growx");
		cbRecebimentosPorSafra.setModel(modelSafra);
		cbRecebimentosPorSafra.setRenderer(cBSafraPersonalizado);
		
		JLabel lblNewLabel_11 = new JLabel("SAFRA EM EVIDÊNCIA");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_7.add(lblNewLabel_11, "cell 0 5,alignx center");
		
		JLabel lblNewLabel_12 = new JLabel("2020/2021");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_7.add(lblNewLabel_12, "cell 0 6,alignx center");
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.gridwidth = 12;
		gbc_panel_8.insets = new Insets(0, 0, 5, 5);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 12;
		gbc_panel_8.gridy = 1;
		panel_3.add(panel_8, gbc_panel_8);
		panel_8.setLayout(new MigLayout("", "[][grow]", "[][][211.00,grow][]"));
		
		JLabel lblNewLabel_10 = new JLabel("Avisos do Sistema");
		lblNewLabel_10.setOpaque(true);
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBackground(new Color(0, 51, 0));
		lblNewLabel_10.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panel_8.add(lblNewLabel_10, "cell 1 0");
		
	
		
		
	
		tabela_avisos = new JTable(modelo_aviso);

		tabela_avisos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabela_avisos.getColumnModel().getColumn(1).setPreferredWidth(250);
		tabela_avisos.getColumnModel().getColumn(2).setPreferredWidth(800);
		tabela_avisos.setRowHeight(30);
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane(tabela_avisos);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBackground(Color.WHITE);
		panel_8.add(scrollPane_1, "cell 1 2,grow");
		
		JButton btnNewButton = new JButton("Excluir Aviso");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int indiceDaLinha = tabela_avisos.getSelectedRow();
				CadastroAviso aviso_selecionado = lista_avisos.get(indiceDaLinha);
				lista_avisos.remove(aviso_selecionado);
				modelo_aviso.onRemove(indiceDaLinha);
			}
		});
		
		JButton btnLimparAvisos = new JButton("Limpar");
		btnLimparAvisos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabela_avisos.removeAll();
				lista_avisos.clear();
				modelo_aviso.onRemoveAll();
				
			}
		});
		panel_8.add(btnLimparAvisos, "flowx,cell 1 3,alignx right");
		panel_8.add(btnNewButton, "cell 1 3,alignx right");
		
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.gridheight = 2;
		gbc_panel_6.insets = new Insets(0, 0, 5, 5);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 2;
		panel_3.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		 panelGraficoLinha = new JPanel();
		panelGraficoLinha.setLayout(null);
		panelGraficoLinha.setBackground(Color.WHITE);
		panel_6.add(panelGraficoLinha, "cell 0 0 1 2,grow");
		
		 painelGraficoCarregamentos = new JPanelGraficoCarregamento(0, 0);
		painelGraficoCarregamentos.setLayout(null);
		GridBagConstraints gbc_painelGraficoCarregamentos = new GridBagConstraints();
		gbc_painelGraficoCarregamentos.gridheight = 3;
		gbc_painelGraficoCarregamentos.gridwidth = 14;
		gbc_painelGraficoCarregamentos.insets = new Insets(0, 0, 0, 5);
		gbc_painelGraficoCarregamentos.fill = GridBagConstraints.BOTH;
		gbc_painelGraficoCarregamentos.gridx = 1;
		gbc_painelGraficoCarregamentos.gridy = 2;
		panel_3.add(painelGraficoCarregamentos, gbc_painelGraficoCarregamentos);
		
		 lblTotalSacosRetirar = new JLabel("a Carregar(sacos): 0");
		lblTotalSacosRetirar.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosRetirar.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacosRetirar.setBounds(0, 71, 253, 24);
		painelGraficoCarregamentos.add(lblTotalSacosRetirar);
		
		 lblTotalSacosRetirados = new JLabel("Carregados(sacos): 0");
		lblTotalSacosRetirados.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosRetirados.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacosRetirados.setBounds(0, 35, 253, 24);
		painelGraficoCarregamentos.add(lblTotalSacosRetirados);
		
		 lblTotalSacos = new JLabel("Quantidade Total(sacos): 0");
		lblTotalSacos.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacos.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacos.setBounds(0, -1, 253, 24);
		painelGraficoCarregamentos.add(lblTotalSacos);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_caminhao.png")));
		lblNewLabel_9.setBounds(128, 149, 64, 75);
		painelGraficoCarregamentos.add(lblNewLabel_9);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.gridwidth = 9;
		gbc_panel_9.insets = new Insets(0, 0, 5, 0);
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 15;
		gbc_panel_9.gridy = 2;
		panel_3.add(panel_9, gbc_panel_9);
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[]{111, 0, 0, 0, 0, 0};
		gbl_panel_9.rowHeights = new int[]{88, 0, 62, 58, 0};
		gbl_panel_9.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_9.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_9.setLayout(gbl_panel_9);
		
		JLabel lblNewLabel_6_1 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6_1.setOpaque(true);
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6_1.setBackground(new Color(0, 206, 209));
		lblNewLabel_6_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getDadosCarregamento();
				atualizarGraficoCarregamentos();
				atualizarPainelGraficoLinhasTodasSafras();
			}
		});
		GridBagConstraints gbc_lblNewLabel_6_1 = new GridBagConstraints();
		gbc_lblNewLabel_6_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_6_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel_6_1.gridwidth = 5;
		gbc_lblNewLabel_6_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_6_1.gridx = 0;
		gbc_lblNewLabel_6_1.gridy = 0;
		panel_9.add(lblNewLabel_6_1, gbc_lblNewLabel_6_1);
		
		JComboBox cbCarregamentoPorSafra = new JComboBox();
		cbCarregamentoPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbCarregamentoPorSafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
					// procura no banco os contratos de acordo com a safra selecionada
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					double num_total_sacos = gerenciar.getQuantidadeSacosPorSafra(safra.getId_safra());
					double num_total_sacos_carregados = gerenciar
							.getQuantidadeSacosCarregadosPorSafra(safra.getId_safra());
					// atualizar o grafico
					atualizarGraficoCarregamentos(num_total_sacos, num_total_sacos_carregados);
					atualizarPainelGraficoLinhasSafra(safra.getId_safra());
					// getCarregamentoPorPeriodoSafra(safra.getId_safra());
				} catch (Exception t) {
				}
			}
		});
		cbCarregamentoPorSafra.setModel(modelSafra);
		cbCarregamentoPorSafra.setRenderer(cBSafraPersonalizado);
		
		GridBagConstraints gbc_cbCarregamentoPorSafra = new GridBagConstraints();
		gbc_cbCarregamentoPorSafra.gridwidth = 5;
		gbc_cbCarregamentoPorSafra.insets = new Insets(0, 0, 5, 5);
		gbc_cbCarregamentoPorSafra.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbCarregamentoPorSafra.gridx = 0;
		gbc_cbCarregamentoPorSafra.gridy = 1;
		panel_9.add(cbCarregamentoPorSafra, gbc_cbCarregamentoPorSafra);
		
		JPanel painel_mensagens = new JPanel();
		painel_mensagens.setLayout(null);
		painel_mensagens.setBackground(new Color(102, 204, 204));
		GridBagConstraints gbc_painel_mensagens = new GridBagConstraints();
		gbc_painel_mensagens.gridwidth = 5;
		gbc_painel_mensagens.fill = GridBagConstraints.BOTH;
		gbc_painel_mensagens.gridx = 0;
		gbc_painel_mensagens.gridy = 3;
		panel_9.add(painel_mensagens, gbc_painel_mensagens);
		
		JLabel lblNewLabel_3 = new JLabel("     Mensagens");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_3.setBackground(new Color(0, 0, 153));
		lblNewLabel_3.setBounds(133, 17, 161, 28);
		painel_mensagens.add(lblNewLabel_3);
		
		 lblNovaMensagem = new JLabel("");
		lblNovaMensagem.setForeground(Color.WHITE);
		lblNovaMensagem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNovaMensagem.setBounds(33, 17, 32, 32);
		painel_mensagens.add(lblNovaMensagem);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 41;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		painelPrincipal.add(scrollPane, gbc_scrollPane);
		
		
		lblUser.setText(login.getNome() + " " + login.getSobrenome());
		if (login.getConfigs_privilegios().getNivel_privilegios() == 1) {
			if (login.getGenero().equals("Masculino"))
				lblDireitos.setText("Administrador do Sistema");
			else
				lblDireitos.setText("Administradora do Sistema");
		} else if (login.getConfigs_privilegios().getNivel_privilegios() == 2) {
			if (login.getGenero().equals("Masculino"))
				lblDireitos.setText("Gerente Financeiro");
			else
				lblDireitos.setText("Gerente Financeira");
		} else if (login.getConfigs_privilegios().getNivel_privilegios() == 3) {
			if (login.getGenero().equals("Masculino"))
				lblDireitos.setText("Auxiliar Administrativo");
			else
				lblDireitos.setText("Auxiliar Administrativo");
		}
		
		modelo_usuarios.addColumn("Usuario");
		modelo_usuarios.addColumn("Ip");
		modelo_usuarios.addColumn("Status");
		
		
		painel_mensagens.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (telaChat == null) {
					telaChat = new TelaChat(isto);
					telaChat.setTelaPai(isto);
				} else {
					telaChat.setVisible(true);
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							lblNovaMensagem.setIcon(null);
							lblNovaMensagem.repaint();
							lblNovaMensagem.updateUI();
						}
					});
				}
			}
		});
		
		atualizarNumTarefas();
		getDadosCarregamento();
		getDadosRecebimento();
		atualizarGraficoCarregamentos();
		atualizarGraficoRecebimentos();
		getDadosContratos();
		atualizarGraficoContratos();
		getCarregamentoPorPeriodo();
		buscarConexao();
		buscaConexaoBanco();
		buscarConexaoWhatsapp();

		buscaConexaoServidorArquivos();
		buscarConexaoNuvem();
		ThreadGetDadosContratos();
		ThreadGetDadosCarregamento();
		ThreadGetDadosRecebimento();

		

		if (telaChat == null) {
			telaChat = new TelaChat(isto);
			telaChat.setTelaPai(isto);

		} 
		
	
		new Thread() {
			
			
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				if (telaPost == null) {
					telaPost = new TelaPost(isto);
				} else {
					telaPost.setVisible(true);
				}
			}
		}.start();
		
/*
		new Thread() {
		
			@Override
			public void run() {
			if(telaRomaneio == null) {
				telaRomaneio = new TelaRomaneios(0,isto);
				telaRomaneio.pesquisarTodosOsRomaneios(new GerenciarBancoClientes().getClientes(-1, -1, ""));
				DadosGlobais dados = DadosGlobais.getInstance();

				dados.setTelaRomaneios(telaRomaneio);

			}else {
				//telaRomaneio.pesquisarTodosOsRomaneios(clientes_disponiveis);

			}
			
		
			if(telaTodasNotasFiscais == null) {
				telaTodasNotasFiscais = new TelaTodasNotasFiscais(0, 0,isto);
				
				DadosGlobais dados = DadosGlobais.getInstance();

				dados.setTelaTodasNotasFiscais(telaTodasNotasFiscais);

			}else {
				//telaRomaneio.pesquisarTodosOsRomaneios(clientes_disponiveis);

			}
			}
			
		}.start();
			*/
		
		
		vigiarRomaneios();
		

		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
		
		
	}
	
	
	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
		
		
		
		//telaTodasNotasFiscais
		telaTodasNotasFiscais = dados.getTelaTodasNotasFiscais();
	}

	public void getDadosContratos() {
		if (gerenciarDadosContrato == null)
			gerenciarDadosContrato = new GerenciarBancoContratos();
		int numeroTotalContratos = gerenciarDadosContrato.getNumeroTotalContratos();
		int numerosContratosSemAssinar = gerenciarDadosContrato.getNumeroContratosParaAssinar();
		dados_contratos.setNumero_total_contratos(numeroTotalContratos);
		dados_contratos.setNumero_contratos_assinar(numerosContratosSemAssinar);
		dados_contratos.setNumero_contratos_assinados(numeroTotalContratos - numerosContratosSemAssinar);
		System.out.println("Numero total de contrato: " + dados_contratos.getNumero_total_contratos());
		System.out.println("Numero total de contratos para assinar: " + dados_contratos.getNumero_contratos_assinar());
		System.out.println("Numero total de contrato ja assinados: " + dados_contratos.getNumero_contratos_assinados());
		if (!executou) {
			if (numerosContratosSemAssinar != -1) {
				new Thread() {
					@Override
					public void run() {
						while (notificando) {
						}
						novaNotificacao(
								"Há " + numerosContratosSemAssinar
										+ " contratos com carencia de assinatura na base de dados",
								"/main/java/audio/beep_notificacao.wav", 1);
					}
				}.start();
				executou = true;
			} else {
				System.out.println("Não foi possivel buscar o numero de contratos sem assinar no banco de dados!");
			}
		}
	}

	public void getDadosCarregamento() {
		if (gerenciarDadosCarregamento == null)
			gerenciarDadosCarregamento = new GerenciarBancoContratos();
		double quantidade_total_sacos = gerenciarDadosCarregamento.getQuantidadeSacos();
		double quantidade_total_sacos_carregados = gerenciarDadosCarregamento.getQuantidadeSacosCarregados();
		double quantidade_total_sacos_a_carregar = quantidade_total_sacos - quantidade_total_sacos_carregados;
		dados_carregamentos.setQuantidade_total_sacos(quantidade_total_sacos);
		dados_carregamentos.setQuantidade_total_carregada(quantidade_total_sacos_carregados);
		dados_carregamentos.setQuantidade_total_a_carregar(quantidade_total_sacos_a_carregar);
	}

	public void getDadosRecebimento() {
		if (gerenciarDadosRecebimento == null)
			gerenciarDadosRecebimento = new GerenciarBancoContratos();
		double quantidade_total_sacos = gerenciarDadosRecebimento.getQuantidadeSacos();
		double quantidade_total_sacos_recebidos = gerenciarDadosRecebimento.getQuantidadeSacosRecebidos();
		double quantidade_total_sacos_a_receber = quantidade_total_sacos - quantidade_total_sacos_recebidos;
		dados_recebimentos.setQuantidade_total_sacos(quantidade_total_sacos);
		dados_recebimentos.setQuantidade_total_recebidos(quantidade_total_sacos_recebidos);
		dados_recebimentos.setQuantidade_total_a_receber(quantidade_total_sacos_a_receber);
	}

	
	
	public void ThreadGetDadosCarregamento() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getDadosCarregamento();
					atualizarGraficoCarregamentos();
					getCarregamentoPorPeriodo();
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	
	public void ThreadGetDadosRecebimento() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getDadosRecebimento();
					atualizarGraficoRecebimentos();
					getCarregamentoPorPeriodo();
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}


	public void ThreadGetDadosContratos() {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(40000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true) {
					atualizarGraficoContratos();
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void buscarConexao() {
		new Thread() {
			private URL url = null;

			@Override
			public void run() {
				while (true) {
					try {
						url = new URL("http://www.google.com.br");
						System.out.println("Tentando conexao!");
						URLConnection connection = url.openConnection();
						connection.connect();
						lblnet.setText("Internet: Conectada");
						imgInternet.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/internet_online.png")));
					} catch (IOException f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar a internet!");
						while (notificando) {
						}
						novaNotificacao("Sem conexão com a internet, algumas funções seram limitadas até a reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);
						lblnet.setText("Internet: Desconectada");
						imgInternet.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/internet_offline.png")));
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void buscarConexaoNuvem() {
		new Thread() {
			private URL url = null;

			@Override
			public void run() {
				while (true) {
					try {
						url = new URL("https://www.dropbox.com/");
						System.out.println("Tentando conexao nuvem!");
						URLConnection connection = url.openConnection();
						connection.connect();
						lblNuvem.setText("Nuvem: Conectada");
						imgNuvem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/nuvem_online.png")));
					} catch (IOException f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar ao dropbpx!");
						while (notificando) {
						}
						novaNotificacao("Sem conexão com a nuvem, algumas funções seram limitadas até a reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);
						lblNuvem.setText("Nuvem: Desconectada");
						imgNuvem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/nuvem_offline.png")));
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void buscarConexaoWhatsapp() {
		new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						 Whatsapp zap = new Whatsapp();
						 
						if(zap.status().contains("OK")) {
							//conectado
							lblStatusWhatsapp.setText("Status: Conectado");
							imgWhatsapp.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/zap_online.png")));
						}else {
							//nao conectado
							lblStatusWhatsapp.setText("Status: Desconectado");
							imgWhatsapp.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/zap_offline.png")));
						}
						
					} catch (Exception f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar ao whatsapp!");
						while (notificando) {
						}
						novaNotificacao("Sem conexão com o Whatsapp, algumas funções seram limitadas até a reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);
						lblStatusWhatsapp.setText("Status: Desconectado");
						imgWhatsapp.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/zap_offline.png")));
					}
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void novaNotificacao(String texto, String song, int repeticao) {
		try {
			notificando = true;
			Thread.sleep(1000);
			URL url = TelaPrincipal.class.getResource(song);
			TelaNotificacao tela = new TelaNotificacao();
			new Thread() {
				@Override
				public void run() {
					ReproduzirAudio player = new ReproduzirAudio();
					for (int i = 0; i < repeticao; i++) {
						player.play(url);
					}
				}
			}.start();
			Thread.sleep(2000);
			tela.setVisible(true);
			tela.setMensagem(texto);
			Thread.sleep(5000);
			tela.fechar();
			notificando = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buscaConexaoBanco() {
		new Thread() {
			public void run() {
				while (true) {
					CadastroBaseDados bd;
					bd = configs_globais.getBaseDados();
					String url = "jdbc:mysql://" + bd.getHost() + ":" + bd.getPorta() + "/" + bd.getNome_banco()
							+ "?useTimezone=true&serverTimezone=UTC";
					urlBancoDados.setText(url);
					if (gerenciarBancoPadrao == null)
						gerenciarBancoPadrao = new GerenciarBancoPadrao();
					if (gerenciarBancoPadrao.getConexao()) {
						System.out.println("Banco de Dados OnLine!");
						lblBD.setText("Banco de Dados: Conectada");
						imgBaseDados.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_dados_online.png")));
					} else {
						System.out.println("Banco de Dados Offline!");
						lblBD.setText("Banco de Dados: Desconectada");
						imgBaseDados.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_dados_offline.png")));
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void buscaConexaoServidorArquivos() {
		new Thread() {
			public void run() {
				while (true) {
					CadastroBaseArquivos base = configs_globais.getServidor_arquivos();
					String host = base.getServidor();
					urlBaseArquivos.setText(host);
					lblBaseDeArquivos.setText("Base de Arquivos: Conectada");
					TesteConexao gerenciar = new TesteConexao();
					if (gerenciar.doPing(host)) {
						System.out.println("Banco de Arquivos OnLine!");
						imgBaseArquivos.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_arquivos_online.png")));
					} else {
						lblBaseDeArquivos.setText("Base de Arquivos: Desconectada");
						System.out.println("Banco de Arquivos Offline!");
						imgBaseArquivos.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_arquivos_offline.png")));
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void atualizarGraficoContratos() {
		getDadosContratos();
		lblTotalContratos.setText("Total de Contratos: " + dados_contratos.getNumero_total_contratos());
		lblTotalContratosAssinados.setText("Assinados: " + dados_contratos.getNumero_contratos_assinados());
		lblTotalContratosAssinar.setText("Assinar: " + dados_contratos.getNumero_contratos_assinar());
		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (i <= dados_contratos.getNumero_contratos_assinados()) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoContratos.setDados(dados_contratos.getNumero_total_contratos(), i);
					painelGraficoContratos.repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		}.start();
	}

	public void atualizarGraficoContratos(CadastroSafra safra) {
		getDadosContratos();
		lblTotalContratos.setText("Total de Contratos: " + dados_contratos.getNumero_total_contratos());
		lblTotalContratosAssinados.setText("Assinados: " + dados_contratos.getNumero_contratos_assinados());
		lblTotalContratosAssinar.setText("Assinar: " + dados_contratos.getNumero_contratos_assinar());
		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (i <= dados_contratos.getNumero_contratos_assinados()) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoContratos.setDados(dados_contratos.getNumero_total_contratos(), i);
					painelGraficoContratos.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		}.start();
	}

	public void atualizarGraficoContratos(int num_total, int num_assinar) {
		lblTotalContratos.setText("Total de Contratos: " + num_total);
		lblTotalContratosAssinados.setText("Assinados: " + (num_total - num_assinar));
		lblTotalContratosAssinar.setText("Assinar: " + num_assinar);
		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (i <= num_total - num_assinar) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoContratos.setDados(num_total, i);
					painelGraficoContratos.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		}.start();
	}

	public void atualizarGraficoCarregamentos() {
		lblTotalSacos.setText("Quantidade Total(sacos): " + (int) dados_carregamentos.getQuantidade_total_sacos());
		lblTotalSacosRetirados
				.setText("Carregados(sacos): " + (int) dados_carregamentos.getQuantidade_total_carregada());
		lblTotalSacosRetirar
				.setText("a Carregar(sacos): " + (int) dados_carregamentos.getQuantidade_total_a_carregar());
		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (i <= (int) dados_carregamentos.getQuantidade_total_carregada()) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoCarregamentos.setDados((int) dados_carregamentos.getQuantidade_total_sacos(), i);
					painelGraficoCarregamentos.repaint();
					i++;
				}
			}
		}.start();
	}
	
	public void atualizarGraficoRecebimentos() {
		lblTotalSacosGraficoRecebimento.setText("Quantidade Total(sacos): " + (int) dados_recebimentos.getQuantidade_total_sacos());
		lblTotalSacosRecebidos
				.setText("Recebidos(sacos): " + (int) dados_recebimentos.getQuantidade_total_recebidos());
		lblTotalSacosAReceber
				.setText("a Receber(sacos): " + (int) dados_recebimentos.getQuantidade_total_a_receber());
		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (i <= (int) dados_recebimentos.getQuantidade_total_recebidos()) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoRecebimento.setDados((int) dados_recebimentos.getQuantidade_total_sacos(), i);
					painelGraficoRecebimento.repaint();
					i++;
				}
			}
		}.start();
	}

	public void atualizarGraficoRecebimentos(double quantidade_total, double quantidade_recebida) {
		lblTotalSacosGraficoRecebimento.setText("Quantidade Total(sacos): " + (int) quantidade_total);
		lblTotalSacosRecebidos.setText("Recebidos(sacos): " + (int) quantidade_recebida);
		lblTotalSacosAReceber.setText("a Receber(sacos): " + (int) (quantidade_total - quantidade_recebida));
		new Thread() {
			@Override
			public void run() {
				int i = (int) quantidade_recebida;
				while (i <= (int) quantidade_recebida) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoRecebimento.setDados((int) quantidade_total, i);
					painelGraficoRecebimento.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		}.start();
	}

	
	
	public void atualizarGraficoCarregamentos(double quantidade_total, double quantidade_carregada) {
		lblTotalSacos.setText("Quantidade Total(sacos): " + (int) quantidade_total);
		lblTotalSacosRetirados.setText("Carregados(sacos): " + (int) quantidade_carregada);
		lblTotalSacosRetirar.setText("a Carregar(sacos): " + (int) (quantidade_total - quantidade_carregada));
		new Thread() {
			@Override
			public void run() {
				int i = (int) quantidade_carregada;
				while (i <= (int) quantidade_carregada) {
					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);
					painelGraficoCarregamentos.setDados((int) quantidade_total, i);
					painelGraficoCarregamentos.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		}.start();
	}

	public void setNumeroMensagensNovas() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				lblNovaMensagem.setIcon(
						new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_mensagem_nao_lida.png")));
				lblNovaMensagem.repaint();
				lblNovaMensagem.updateUI();
			}
		});
	}

	public void setNovaNotificacaoMensagem(String mensagem) {
		// if(!telaChat.isVisible())
		try {
			notificando = true;
			TelaNotificacaoSuperior tela = new TelaNotificacaoSuperior();
			tela.setMensagem(mensagem);
			tela.setVisible(true);
			Thread.sleep(5000);
			tela.fechar();
			notificando = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}

	public void atualizarComboBoxContratosPorSafra() {
		cbContratosPorSafra.removeAllItems();
		pesquisarSafras();
		for (CadastroSafra safra : safras) {
			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);
		}
	}

	public void atualizarNumTarefas() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true) {
					if (gerenciarAtualizarTarefas == null)
						gerenciarAtualizarTarefas = new GerenciarBancoContratos();
					int num_agora = gerenciarAtualizarTarefas.getNumTarefas(login.getId());
					lblNumeroTarefas.setText(num_agora + "");
					if (num_tarefas_nesta_secao == -1) {
						if (num_agora > 0) {
							while (notificando == true) {
								//System.out.println("Notificacao em andamento");
							}
							novaNotificacao("Você possui tarefas a concluir", "/main/java/audio/beep_notificacao.wav", 1);
							num_tarefas_nesta_secao = num_agora;
						} else {
							num_tarefas_nesta_secao = 0;
						}
					} else if (num_agora > num_tarefas_nesta_secao) {
						// nova tarefa recebida, notificar
						while (notificando) {
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						num_tarefas_nesta_secao = num_agora;
						novaNotificacao("Nova Tarefa Recebida!", "/main/java/audio/beep_notificacao.wav", 1);
					} else if (num_agora > num_tarefas_nesta_secao) {
						// quantidade de tarefas e a mesma
						num_tarefas_nesta_secao = num_agora;
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, "Erro ao buscar tarefas!");
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public void getCarregamentoPorPeriodo() {
		if (gerenciarCarregamentoPorPeriodo == null)
			gerenciarCarregamentoPorPeriodo = new GerenciarBancoContratos();
		String hoje = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String semana = LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Map<String, Double> lista_cargas = gerenciarCarregamentoPorPeriodo.getCarregamentosPorData(semana, hoje, 0);
		dataset = new DefaultCategoryDataset();
		for (Map.Entry<String, Double> pair : lista_cargas.entrySet()) {
			System.out.println(pair.getKey());
			System.out.println(pair.getValue());
			dataset.addValue(pair.getValue(), "", pair.getKey());
		}
		linha = new GraficoLinha();
		linha.setDataset(dataset);
		chartPanel = linha.getGraficoLinha(400, 200, "Últimos 7 dias", "Romaneios", "Quantidade de Sacos");
		panelGraficoLinha.add(chartPanel);
	}

	public void getCarregamentoPorPeriodoSafra(int id_safra) {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		String hoje = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String semana = LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Map<String, Double> lista_cargas = gerenciar.getCarregamentosPorData(semana, hoje, id_safra);
		dataset = new DefaultCategoryDataset();
		for (Map.Entry<String, Double> pair : lista_cargas.entrySet()) {
			System.out.println(pair.getKey());
			System.out.println(pair.getValue());
			dataset.addValue(pair.getValue(), "", pair.getKey());
		}
		linha = new GraficoLinha();
		linha.setDataset(dataset);
		chartPanel = linha.getGraficoLinha(200, 200, "Últimos 7 dias", "Romaneios", "Quantidade de Sacos");
		panelGraficoLinha.add(chartPanel);
	}

	public void atualizarPainelGraficoLinhasSafra(int id_safra) {
		panelGraficoLinha.removeAll();
		panelGraficoLinha.updateUI();
		panelGraficoLinha.repaint();
		getCarregamentoPorPeriodoSafra(id_safra);
	}

	public void atualizarPainelGraficoLinhasTodasSafras() {
		panelGraficoLinha.removeAll();
		panelGraficoLinha.updateUI();
		panelGraficoLinha.repaint();
		getCarregamentoPorPeriodo();
	}

	public void baixarNotasEmSegundoPlano(CadastroCliente cliente, int mes_inicio, int mes_final, int ano_final) {
		new Thread() {
			@Override
			public void run() {
				BaixarNotasFiscais baixar = new BaixarNotasFiscais(cliente, "VENDA");
				baixar.iniciarPesquisas(mes_inicio, mes_final, ano_final);
			}
		}.start();
	}

	/*public void vigiarRomaneios() {
		TelaNotificacaoSuperiorModoBusca avisos = new TelaNotificacaoSuperiorModoBusca();

		new Thread() {
			@Override
			public void run() {
				new Thread() {
					@Override
					public void run() {
						avisos.setMensagem("Modo de Busca");
						avisos.setVisible(true);
					}
				}.start();
				
				
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						avisos.setMensagem("Modo de Busca");
						String unidade_base_dados = configs_globais.getServidorUnidade();
						String sub_pasta = "E-Contract\\arquivos\\arquivos_comuns";
						String pasta_final = unidade_base_dados + "\\" + sub_pasta;
						ManipularRomaneios manipular = new ManipularRomaneios(pasta_final);
						ArrayList<CadastroRomaneio> romaneios = manipular.tratar();
						GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
						ArrayList<CadastroCliente> clientes_cadastrados = gerenciar_clientes.getClientes(0, 0, "");
						for (CadastroRomaneio roms : romaneios) {
							avisos.setMensagem("Novo Romaneio Encontrado");
							Thread.sleep(3000);
							CadastroCliente remetente = roms.getRemetente();
							CadastroCliente destinatario = roms.getDestinatario();
							boolean remetente_cadastrado = false;
							boolean destinatario_cadastrado = false;
							// verifica se o remetente ja esta cadastrado
							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(remetente.getIe().trim())) {
									remetente_cadastrado = true;
								//	JOptionPane.showMessageDialog(null, "Remetente Cadastrado");
									break;
								}
							}
							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(destinatario.getIe().trim())) {
									destinatario_cadastrado = true;
								//	JOptionPane.showMessageDialog(null, "Destinatario Cadastrado");

									break;
								}
							}
							
							if (destinatario.getTipo_pessoa() == 0) {
							//	JOptionPane.showMessageDialog(null, "Nome destinatario: " + destinatario.getNome_empresarial());
							} else {
							//	JOptionPane.showMessageDialog(null, "Nome destinatario: " + destinatario.getNome_fantaia());
							}
							
							if (remetente.getTipo_pessoa() == 0) {
							//	JOptionPane.showMessageDialog(null, "Nome remetente: " + remetente.getNome_empresarial());
							} else {
							//	JOptionPane.showMessageDialog(null, "Nome remetente: " + remetente.getNome_fantaia());
							}
							
							
							
							
							
							if (remetente_cadastrado && !destinatario_cadastrado) {
								// mover para a pasta do remetente
								ManipularTxt manipular_txt = new ManipularTxt();
								String nome_pasta;
								if (remetente.getTipo_pessoa() == 0) {
									nome_pasta = remetente.getNome_empresarial().toUpperCase();
								} else {

									nome_pasta = remetente.getNome_fantaia().toUpperCase();
								}
								unidade_base_dados = configs_globais.getServidorUnidade();
								sub_pasta = "E-Contract\\arquivos\\clientes";
								ManipularTxt manipular_arq = new ManipularTxt();
								nome_pasta = nome_pasta.trim();
								String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
										+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
										+ roms.getNumero_romaneio() + ".pdf";
								// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
								// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								avisos.setMensagem("Movendo...");
								// primeiro veririca se nao existe um arquivo com esse nome
								File file = new File(caminho_completo_nf);
								if (!file.exists()) {
									boolean mover = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
											caminho_completo_nf);
									if (mover) {
										avisos.setMensagem("Romaneio movido para a pasta do remetente");
										// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
										// remetente");
									} else {
										// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio");
										avisos.setMensagem("Erro ao mover o romaneio para pasta do remetente");
									}
								} else {
									avisos.setMensagem("Romaneio já lido");
									new File(roms.getCaminho_arquivo()).delete();
								}
							} else if (!remetente_cadastrado && destinatario_cadastrado) {
								// mover para a pasta do destinatario
								ManipularTxt manipular_txt = new ManipularTxt();
								String nome_pasta;
								if (destinatario.getTipo_pessoa() == 0) {
									nome_pasta = destinatario.getNome_empresarial().toUpperCase();
								} else {

									nome_pasta = destinatario.getNome_fantaia().toUpperCase();
								}
								unidade_base_dados = configs_globais.getServidorUnidade();
								sub_pasta = "E-Contract\\arquivos\\clientes";
								ManipularTxt manipular_arq = new ManipularTxt();
								nome_pasta = nome_pasta.trim();
								String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
										+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
										+ roms.getNumero_romaneio() + ".pdf";
								// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
								// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
								avisos.setMensagem("Movendo...");
								// primeiro veririca se nao existe um arquivo com esse nome
								File file = new File(caminho_completo_nf);
								if (!file.exists()) {
									boolean mover = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
											caminho_completo_nf);
									if (mover) {
										// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
										// remetente");
										avisos.setMensagem("Romaneio movido para a pasta do remetente");
									} else {
										// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio");
										avisos.setMensagem("Erro ao mover romaneio para a pasta do remetente");
									}
								} else {
									avisos.setMensagem("Romaneio já lido");
									new File(roms.getCaminho_arquivo()).delete();
								}
							} else if (remetente_cadastrado && destinatario_cadastrado) {
								if (remetente.getIe().trim().equals(destinatario.getIe().trim())) {
									// mover para o remetente
									// copiar para o remetente
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									if (remetente.getTipo_pessoa() == 0) {

										nome_pasta = remetente.getNome_empresarial();
									} else {

										nome_pasta = remetente.getNome_fantaia();
									}
									unidade_base_dados = configs_globais.getServidorUnidade();
									sub_pasta = "E-Contract\\arquivos\\clientes";
									ManipularTxt manipular_arq = new ManipularTxt();
									nome_pasta = nome_pasta.trim();
									String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
											+ nome_pasta + "\\" + "ROMANEIOS" + "\\romaneio-"
											+ roms.getNumero_romaneio() + ".pdf";
									// JOptionPane.showMessageDialog(null, "Copiando de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									avisos.setMensagem("Copiando...");
									// primeiro veririca se nao existe um arquivo com esse nome
									File file = new File(caminho_completo_nf);
									if (!file.exists()) {
										boolean copiar = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
												caminho_completo_nf);
										if (copiar) {
											// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
											// remetente");
											avisos.setMensagem("Romaneio movido para a pasta do remetente");
											// mover para a pasta do destinatario
										} else {
											// JOptionPane.showMessageDialog(null, "Romaneio não pode ser movido para a
											// pasta do remetente");
											avisos.setMensagem(
													"Romaneio não pode ser movido para a pasta do remetente");
										}
									} else {
										avisos.setMensagem("Romaneio já lido");
										new File(roms.getCaminho_arquivo()).delete();
									}
								} else {
									//Romaneio com destinatario e remetente diferente
									// copiar para o destinatario
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									
									if (destinatario.getTipo_pessoa() == 0) {
										nome_pasta = destinatario.getNome_empresarial();
									} else {
										nome_pasta = destinatario.getNome_fantaia();
									}
									
									unidade_base_dados = configs_globais.getServidorUnidade();
									sub_pasta = "E-Contract\\arquivos\\clientes";
									ManipularTxt manipular_arq = new ManipularTxt();
									nome_pasta = nome_pasta.trim();
									String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
											+ nome_pasta + "\\" + "ROMANEIOS" + "\\romaneio-"
											+ roms.getNumero_romaneio() + ".pdf";
									avisos.setMensagem("Copiando...");
									// JOptionPane.showMessageDialog(null, "Copiando de :\n" +
									// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
									// primeiro veririca se nao existe um arquivo com esse nome
									File file = new File(caminho_completo_nf);
									if (!file.exists()) {
										boolean copiar = manipular_arq.copiarNFe(roms.getCaminho_arquivo(),
												caminho_completo_nf);
										if (copiar) {
											// JOptionPane.showMessageDialog(null, "Romaneio copiado para a pasta do
											// destinatario");
											avisos.setMensagem("Romaneio copiado para a pasta do destinatario");
											// mover para a pasta do remetente
											
											
											if (remetente.getTipo_pessoa() == 0) {
												nome_pasta = remetente.getNome_empresarial().toUpperCase();
											} else {

												nome_pasta = remetente.getNome_fantaia().toUpperCase();
											}
											unidade_base_dados = configs_globais.getServidorUnidade();
											sub_pasta = "E-Contract\\arquivos\\clientes";
											nome_pasta = nome_pasta.trim();
											caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + "\\"
													+ nome_pasta.toUpperCase() + "\\" + "ROMANEIOS" + "\\romaneio-"
													+ roms.getNumero_romaneio() + ".pdf";
											// JOptionPane.showMessageDialog(null, "Movendo de :\n" +
											// roms.getCaminho_arquivo()+ "\nPara:\n" + caminho_completo_nf);
											avisos.setMensagem("Movendo...");
											boolean mover = manipular_arq.moverArquivo(roms.getCaminho_arquivo(),
													caminho_completo_nf);
											if (mover) {
												// JOptionPane.showMessageDialog(null, "Romaneio movido para a pasta do
												// destinatario");
												avisos.setMensagem("Romaneio movido para a pasta do remetente");
											} else {
												// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio para a
												// pasta do destinatario");
												avisos.setMensagem(
														"Erro ao mover o romaneio para a pasta do remetente");
											}
										} else {
											// JOptionPane.showMessageDialog(null, "Erro ao copiar o romaneio para a
											// pasta
											// do remetente");
											avisos.setMensagem("Erro ao copiar o romaneio para a pasta do remetente");
										}
									} else {
										avisos.setMensagem("Romaneio já lido");
										new File(roms.getCaminho_arquivo()).delete();
									}
								}
							} else {
								// JOptionPane.showMessageDialog(null, "Romaneio lido mas nem o cliente
								// remetente e nem o cliente destinatario estão cadastrado");
								avisos.setMensagem(
										"Romaneio lido mas nem o cliente remetente e nem o cliente destinatario estão cadastrado");
							}
						}
					} catch (Exception e) {
						// JOptionPane.showMessageDialog(null, "Erro ao ler romaneios");
						avisos.setMensagem("Erro ao ler romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
						JOptionPane.showMessageDialog(null, "Erro ao ler romaneios\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}.start();
	}*/
	
	public void vigiarRomaneios() {
		MonitorarRomaneios monitorar = new MonitorarRomaneios();
    	monitorar.vigiarRomaneiosSemTelaAvisos();
	}

	public int getIndexTelaPai() {
		int index = -1;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] telas = ge.getScreenDevices();

		GraphicsDevice esta_tela = ge.getDefaultScreenDevice();
		for (int i = 0; i < telas.length; i++) {
			if (telas[i] == esta_tela) {
				index = i;
				JOptionPane.showMessageDialog(null, "id da tela pai: " + index);
				break;
			}
		}
		return index;
	}
	
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
	
	
	
	
	
	public static class AvisoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int tipo = 0;
		private final int setor = 1;

		private final int mensagem = 2;


		private final String colunas[] = { "Tipo", "Setor", "Mensagem:" };
		private final ArrayList<CadastroAviso> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																			// nfs

		public AvisoTableModel() {

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
			case tipo:
				return String.class;
			case setor:
				return String.class;
			case mensagem:
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
			CadastroAviso aviso = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case tipo:
				return aviso.getTipo();
			case setor:
				return aviso.getSetor();
			case mensagem:
				return aviso.getMensagem();
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
			CadastroAviso nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroAviso getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroAviso nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroAviso nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroAviso> dadosIn) {
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
		public void onRemove(CadastroAviso nota) {
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
	
	
	public void incluir_aviso(CadastroAviso avisar) {
		
				boolean ja_tem_na_lista = false;
				for(CadastroAviso aviso : lista_avisos) {
					
							if(aviso.getMensagem().equals(avisar.getMensagem())) {
								ja_tem_na_lista = true;
								break;
							}
						
				}
				
				if(!ja_tem_na_lista) {
					//JOptionPane.showMessageDialog(isto, avisar.getMensagem());
					
			     lista_avisos.add(avisar);
			 	java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
					modelo_aviso.onAdd(avisar);

					}
				});
				
				}

		
		
	}
	
	
	public double getQuantidadeFinalCarregada(CadastroContrato contrato_local) {
		GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();
		GerenciarBancoRomaneios gerenciar_romaneios = new GerenciarBancoRomaneios();
		
	
		
		ArrayList<CadastroContrato.Carregamento> lista_carregamentos_deste_contrato = gerenciar_contratos.getCarregamentos(contrato_local.getId());
		double quantidade_total_carregada = 0;
		for(CadastroContrato.Carregamento carregamento : lista_carregamentos_deste_contrato) {
			quantidade_total_carregada +=  carregamento.getPeso_romaneio();
		}
		
		GerenciarBancoTransferenciasCarga gerenciar_transferencias = new GerenciarBancoTransferenciasCarga();
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_transferencias_carga_remetente = gerenciar_transferencias
				.getTransferenciasRemetente(contrato_local.getId());
		ArrayList<CadastroContrato.CadastroTransferenciaCarga> lista_transferencias_carga_destinatario = gerenciar_transferencias
				.getTransferenciaDestinatario(contrato_local.getId());
		
		double quantidade_total_transferida_retirada = 0;
		for(CadastroContrato.CadastroTransferenciaCarga transferencia_retirada : lista_transferencias_carga_remetente) {
			quantidade_total_transferida_retirada += Double.parseDouble(transferencia_retirada.getQuantidade());
		}
		
		double quantidade_total_transferencia_recebida = 0;
		for(CadastroContrato.CadastroTransferenciaCarga transferencia_recebida : lista_transferencias_carga_destinatario) {
			quantidade_total_transferencia_recebida += Double.parseDouble(transferencia_recebida.getQuantidade());
		}
		
		
		double quantidade_final = (quantidade_total_carregada + quantidade_total_transferencia_recebida) - quantidade_total_transferida_retirada;
		return quantidade_final;
	}
}
