package main.java.gui;

import java.applet.Applet;


import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.Component;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class TelaPrincipal extends JFrame implements GetDadosGlobais {
	private JPanelBackground contentPane;
	private JFrame isto;
	private JLabel lblUser;
	private JLabel lblDireitos;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private JPanelGrafico painelGraficoContratos;
	private JPanelGraficoCarregamento painelGraficoCarregamentos;
	private JPanel panelGraficoLinha;
	private JLabel lblTotalContratos, lblTotalContratosAssinar, lblTotalContratosAssinados;
	private boolean executou = false;
	private DadosContratos dados_contratos = new DadosContratos();
	private DadosCarregamento dados_carregamentos = new DadosCarregamento();
	private JComboBox cbCarregamentoPorSafra;
	private int num_tarefas_nesta_secao = -1;
	private JLabel lblNumeroTarefas;
	private JLabel lblBD, lblBaseDeArquivos, lblNuvem, lblnet, urlBancoDados, imgBaseDados, urlBaseArquivos,
			imgBaseArquivos, urlInternet, imgInternet, urlNuvem, imgNuvem, lblNovaMensagem;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<CadastroLogin> usuarios = new ArrayList<>();
	private JComboBox cbContratosPorSafra;
	private boolean notificando = false;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JLabel lblTotalSacosRetirar, lblTotalSacos, lblTotalSacosRetirados;
	private GraficoLinha linha = null;
	TelaPost telaPost;
	TelaTarefas tela_tarefas;
	private GerenciarBancoContratos gerenciarAtualizarTarefas, gerenciarDadosCarregamento, gerenciarDadosContrato,
			gerenciarCarregamentoPorPeriodo;
	private GerenciarBancoPadrao gerenciarBancoPadrao;
	DefaultTableModel modelo_usuarios = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private ChartPanel chartPanel;
	private TelaChat telaChat;
	private DefaultCategoryDataset dataset;

	public TelaPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/imagens/logo_icone4.png")));
		getDadosGlobais();
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
	
		setTitle("E-Contract");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, d.width, d.height - taskBarHeight);
		
		
		lblDireitos = new JLabel();
		lblDireitos.setBackground(Color.WHITE);
		lblDireitos.setForeground(Color.BLACK);
		lblUser = new JLabel();
		lblUser.setBackground(Color.WHITE);
		lblUser.setForeground(Color.BLACK);
		
		
		contentPane = new JPanelBackground();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panelGraficoLinha = new JPanel();
		panelGraficoLinha.setBackground(Color.WHITE);
		panelGraficoLinha.setBounds(0, 409, 369, 280);
		contentPane.add(panelGraficoLinha);
		panelGraficoLinha.setLayout(null);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(0, 0, 842, 56);
		menuBar.setUI(new BasicMenuBarUI() {
			public void paint(Graphics g, JComponent c) {
				g.setColor(new Color(102, 204, 204));
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
			}
		});
		menuBar.setForeground(Color.WHITE);
		menuBar.setOpaque(true);
		contentPane.add(menuBar);
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
				TelaArmazem tela = new TelaArmazem(0,isto);
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
				TelaContratos telaContratos = new TelaContratos(0, isto);
				telaContratos.setVisible(true);
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
				monitor.vigilante_todos_os_romaneios();
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

				if (tela_tarefas == null) {
					tela_tarefas = new TelaTarefas(isto);
					tela_tarefas.getTarefas();
					tela_tarefas.setVisible(true);
				} else {
					tela_tarefas.setVisible(true);
				}
			}
		});
		mntmNewMenuItem_5.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_tarefas.png")));
		mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_5);
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
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUser.setBounds(1146, 11, 194, 27);
		lblUser.setText(login.getNome() + " " + login.getSobrenome());
		contentPane.add(lblUser);
		lblDireitos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDireitos.setBounds(1169, 37, 171, 19);
		contentPane.add(lblDireitos);
		JPanel painelInfoConexao = new JPanel();
		painelInfoConexao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelInfoConexao.setBackground(Color.WHITE);
		painelInfoConexao.setBounds(1099, 109, 251, 209);
		contentPane.add(painelInfoConexao);
		painelInfoConexao.setLayout(null);
		JLabel lblInfo = new JLabel("Informações de Conexão");
		lblInfo.setBounds(0, 0, 230, 14);
		painelInfoConexao.add(lblInfo);
		lblnet = new JLabel("Internet:");
		lblnet.setBounds(55, 25, 175, 14);
		painelInfoConexao.add(lblnet);
		lblBaseDeArquivos = new JLabel("Base de Arquivos:");
		lblBaseDeArquivos.setBounds(55, 118, 175, 14);
		painelInfoConexao.add(lblBaseDeArquivos);
		lblBD = new JLabel("Banco de Dados:");
		lblBD.setBounds(55, 162, 175, 14);
		painelInfoConexao.add(lblBD);
		urlBancoDados = new JLabel("erro");
		urlBancoDados.setBounds(55, 180, 135, 14);
		painelInfoConexao.add(urlBancoDados);
		urlBaseArquivos = new JLabel("erro");
		urlBaseArquivos.setBounds(55, 137, 135, 14);
		painelInfoConexao.add(urlBaseArquivos);
		urlInternet = new JLabel("http://www.google.com.br");
		urlInternet.setBounds(55, 43, 186, 14);
		painelInfoConexao.add(urlInternet);
		imgBaseDados = new JLabel("New label");
		imgBaseDados.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_dados_offline.png")));
		imgBaseDados.setBounds(13, 162, 32, 32);
		painelInfoConexao.add(imgBaseDados);
		imgBaseArquivos = new JLabel("");
		imgBaseArquivos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_arquivos_offline.png")));
		imgBaseArquivos.setBounds(13, 118, 32, 32);
		painelInfoConexao.add(imgBaseArquivos);
		imgInternet = new JLabel("");
		imgInternet.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/internet_offline.png")));
		imgInternet.setBounds(13, 25, 32, 32);
		painelInfoConexao.add(imgInternet);
		imgNuvem = new JLabel("");
		imgNuvem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/nuvem_offline.png")));
		imgNuvem.setBounds(13, 75, 32, 32);
		painelInfoConexao.add(imgNuvem);
		lblNuvem = new JLabel("Nuvem");
		lblNuvem.setBounds(55, 75, 175, 14);
		painelInfoConexao.add(lblNuvem);
		urlNuvem = new JLabel("https://www.dropbox.com/");
		urlNuvem.setBounds(55, 93, 186, 14);
		painelInfoConexao.add(urlNuvem);
		JButton btnSair = new JButton("Sair");
		
		btnSair.setBounds(1251, 70, 89, 23);
		contentPane.add(btnSair);
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
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
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
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(102, 204, 204));
		panel_2.setBounds(1099, 627, 251, 62);
		contentPane.add(panel_2);
		JLabel lblNewLabel_3 = new JLabel("     Mensagens");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_3.setBackground(new Color(0, 0, 153));
		lblNewLabel_3.setBounds(133, 17, 161, 28);
		panel_2.add(lblNewLabel_3);
		lblNovaMensagem = new JLabel("");
		lblNovaMensagem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNovaMensagem.setForeground(Color.WHITE);
		lblNovaMensagem.setBounds(33, 17, 32, 32);
		panel_2.add(lblNovaMensagem);
		JPanel panelContratos = new JPanel();
		panelContratos.setBackground(Color.WHITE);
		panelContratos.setBounds(23, 67, 1025, 298);
		contentPane.add(panelContratos);
		panelContratos.setLayout(null);
		painelGraficoContratos = new JPanelGrafico(0, 0);
		painelGraficoContratos.setBounds(10, 11, 493, 269);
		panelContratos.add(painelGraficoContratos);
		painelGraficoContratos.setLayout(null);
		lblTotalContratosAssinados = new JLabel("");
		lblTotalContratosAssinados.setBounds(10, 57, 173, 24);
		painelGraficoContratos.add(lblTotalContratosAssinados);
		lblTotalContratosAssinados.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratosAssinados.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratosAssinar = new JLabel("");
		lblTotalContratosAssinar.setBounds(10, 92, 173, 24);
		painelGraficoContratos.add(lblTotalContratosAssinar);
		lblTotalContratosAssinar.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratosAssinar.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratos = new JLabel("");
		lblTotalContratos.setBounds(10, 22, 173, 24);
		painelGraficoContratos.add(lblTotalContratos);
		lblTotalContratos.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratos.setBorder(new EmptyBorder(0, 0, 0, 0));
		JLabel lblNewLabel_5_2 = new JLabel("");
		lblNewLabel_5_2.setBounds(419, 70, 64, 64);
		painelGraficoContratos.add(lblNewLabel_5_2);
		lblNewLabel_5_2.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_contrato.png")));
		JLabel lblNewLabel = new JLabel("Safra:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(507, 55, 46, 14);
		panelContratos.add(lblNewLabel);
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
		cbContratosPorSafra.setBounds(552, 41, 320, 34);
		cbContratosPorSafra.setModel(modelSafra);
		cbContratosPorSafra.setRenderer(cBSafraPersonalizado);
		panelContratos.add(cbContratosPorSafra);
		JLabel lblNewLabel_6 = new JLabel("TODAS AS SAFRAS", JLabel.CENTER);
		lblNewLabel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atualizarGraficoContratos();
			}
		});
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBackground(new Color(0, 206, 209));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBounds(552, 11, 320, 22);
		panelContratos.add(lblNewLabel_6);
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(722, 86, 303, 214);
		panelContratos.add(lblNewLabel_7);
		lblNewLabel_7.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_silos.png")));
		pesquisarSafras();
		for (CadastroSafra safra : safras) {
			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);
		}
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tela_tarefas == null) {
					tela_tarefas = new TelaTarefas(isto);
					tela_tarefas.getTarefas();
					tela_tarefas.setVisible(true);
				} else {
					tela_tarefas.setVisible(true);
				}
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_tarefa.png")));
		lblNewLabel_1.setBounds(973, 0, 64, 64);
		contentPane.add(lblNewLabel_1);
		JLabel lblNewLabel_2 = new JLabel("Você tem:");
		lblNewLabel_2.setBounds(1032, 9, 76, 36);
		contentPane.add(lblNewLabel_2);
		lblNumeroTarefas = new JLabel("0");
		lblNumeroTarefas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNumeroTarefas.setBounds(1042, 37, 46, 27);
		contentPane.add(lblNumeroTarefas);
		JLabel lblNewLabel_4 = new JLabel("tarefas");
		lblNewLabel_4.setBounds(1062, 56, 64, 37);
		contentPane.add(lblNewLabel_4);
		JButton btnNewButton = new JButton("Vizualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaVizualizarGrafico tela = new TelaVizualizarGrafico(dataset);
			}
		});
		btnNewButton.setBounds(33, 376, 89, 23);
		contentPane.add(btnNewButton);
		JLabel lblNewLabel_5_1 = new JLabel("");
		lblNewLabel_5_1.setOpaque(true);
		lblNewLabel_5_1.setBackground(Color.BLACK);
		lblNewLabel_5_1.setBounds(10, 366, 1350, 2);
		contentPane.add(lblNewLabel_5_1);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(379, 376, 868, 287);
		contentPane.add(panel);
		panel.setLayout(null);
		painelGraficoCarregamentos = new JPanelGraficoCarregamento(0, 0);
		painelGraficoCarregamentos.setLayout(null);
		painelGraficoCarregamentos.setBounds(0, 0, 491, 287);
		panel.add(painelGraficoCarregamentos);
		lblTotalSacosRetirar = new JLabel("Carregados: 0 ");
		lblTotalSacosRetirar.setBounds(0, 71, 253, 24);
		painelGraficoCarregamentos.add(lblTotalSacosRetirar);
		lblTotalSacosRetirar.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosRetirar.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacosRetirados = new JLabel("a Carregar: 0");
		lblTotalSacosRetirados.setBounds(0, 35, 253, 24);
		painelGraficoCarregamentos.add(lblTotalSacosRetirados);
		lblTotalSacosRetirados.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacosRetirados.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalSacos = new JLabel("Quantidade Total: 0");
		lblTotalSacos.setBounds(0, 0, 253, 24);
		painelGraficoCarregamentos.add(lblTotalSacos);
		lblTotalSacos.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalSacos.setBorder(new EmptyBorder(0, 0, 0, 0));
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(408, 65, 64, 64);
		painelGraficoCarregamentos.add(lblNewLabel_5);
		lblNewLabel_5.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_caminhao.png")));
		cbCarregamentoPorSafra = new JComboBox();
		cbCarregamentoPorSafra.setBounds(494, 44, 326, 34);
		panel.add(cbCarregamentoPorSafra);
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
		JLabel lblNewLabel_6_1 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6_1.setBounds(494, 11, 326, 22);
		panel.add(lblNewLabel_6_1);
		lblNewLabel_6_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getDadosCarregamento();
				atualizarGraficoCarregamentos();
				atualizarPainelGraficoLinhasTodasSafras();
			}
		});
		lblNewLabel_6_1.setOpaque(true);
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6_1.setBackground(new Color(0, 206, 209));
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (telaPost == null) {
					telaPost = new TelaPost(isto);
					telaPost.setVisible(true);
					telaPost.procurarNotas(true);
				} else {
					telaPost.setVisible(true);
				}
			}
		});
		lblNewLabel_8
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_anotacoes_tela_principal.png")));
		lblNewLabel_8.setBounds(862, 0, 64, 64);
		
		contentPane.add(lblNewLabel_8);
		//atualizarNumTarefas();
		//getDadosCarregamento();
		//atualizarGraficoCarregamentos();
		//getDadosContratos();
		//atualizarGraficoContratos();
		//getCarregamentoPorPeriodo();
		//buscarConexao();
		//buscaConexaoBanco();
		//buscaConexaoServidorArquivos();
		//buscarConexaoNuvem();
		//ThreadGetDadosContratos();
		//ThreadGetDadosCarregamento();
		if (telaChat == null) {
			telaChat = new TelaChat(isto);
			telaChat.setTelaPai(isto);
			telaChat.setVisible(false);
		} else {
			telaChat.setVisible(true);
		}
		// vigiarRomaneios();
		/*
		new Thread() {
			@Override
			public void run() {
				if (telaPost == null) {
					telaPost = new TelaPost(isto);
					telaPost.procurarNotas(true);
					telaPost.setVisible(true);
				} else {
					telaPost.setVisible(true);
				}
			}
		}.start();
		*/
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	@Override
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
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
								"/audio/beep_notificacao.wav", 1);
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
								"/audio/beep_erro_net.wav", 2);
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
								"/audio/beep_erro_net.wav", 2);
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
								System.out.println("Notificacao em andamento");
							}
							novaNotificacao("Você possui tarefas a concluir", "/audio/beep_notificacao.wav", 1);
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
						novaNotificacao("Nova Tarefa Recebida!", "/audio/beep_notificacao.wav", 1);
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
		chartPanel = linha.getGraficoLinha(200, 200, "Últimos 7 dias", "Romaneios", "Quantidade de Sacos");
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

	public void vigiarRomaneios() {
		new Thread() {
			@Override
			public void run() {
				TelaNotificacaoSuperiorModoBusca avisos = new TelaNotificacaoSuperiorModoBusca();
				new Thread() {
					@Override
					public void run() {
						avisos.setMensagem("Modo de Busca");
						avisos.setVisible(true);
					}
				}.start();
				while (true) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						avisos.setMensagem("Modo de Busca");
						String unidade_base_dados = configs_globais.getServidorUnidade();
						String sub_pasta = "E-Contract\\arquivos\\romaneios";
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
							boolean remetente_cadastrado = true;
							boolean destinatario_cadastrado = true;
							// verifica se o remetente ja esta cadastrado
							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(remetente.getIe().trim())) {
									remetente_cadastrado = true;
									break;
								}
							}
							for (CadastroCliente cliente : clientes_cadastrados) {
								if (cliente.getIe().trim().equals(destinatario.getIe().trim())) {
									destinatario_cadastrado = true;
									break;
								}
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
									if (destinatario.getTipo_pessoa() == 0) {
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
									// copiar para o remetente
									ManipularTxt manipular_txt = new ManipularTxt();
									String nome_pasta;
									if (destinatario.getTipo_pessoa() == 0) {
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
											// remetente");
											avisos.setMensagem("Romaneio copiado para a pasta do remetente");
											// mover para a pasta do destinatario
											if (destinatario.getTipo_pessoa() == 0) {
												nome_pasta = destinatario.getNome_empresarial().toUpperCase();
											} else {
												nome_pasta = destinatario.getNome_fantaia().toUpperCase();
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
												avisos.setMensagem("Romaneio movido para a pasta do destinatario");
											} else {
												// JOptionPane.showMessageDialog(null, "Erro ao mover o romaneio para a
												// pasta do destinatario");
												avisos.setMensagem(
														"Erro ao mover o romaneio para a pasta do destinatario");
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
						avisos.setMensagem("Erro ao ler romaneios");
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}.start();
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
}