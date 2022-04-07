package main.java.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Font;
import java.awt.GradientPaint;
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

import main.java.cadastros.CadastroAcessoTemporario;
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
import main.java.cadastros.CadastroSafrasEvidencias;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAcessoTemporario;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoSafrasEvidencias;
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
import main.java.views_personalizadas.TelaInformarNotificacao;
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
import main.java.conexaoBanco.GerenciarBancoLancamento;
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
import java.awt.Paint;
import java.awt.Point;

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
	private JLabel lblUser;
	private JLabel lblDireitos;
	private TelaContratos telaContratos;
	private JPanel painelGraficoCarregamentos;
	private TelaMonitoria monitor = null;
	private boolean executou = false;
	private DadosContratos dados_contratos = new DadosContratos();
	private DadosCarregamento dados_carregamentos = new DadosCarregamento();
	private DadosRecebimento dados_recebimentos = new DadosRecebimento();
	private int num_tarefas_nesta_secao = -1;
	private JLabel lblNumeroTarefas;
	private ArrayList<CadastroLogin> usuarios = new ArrayList<>();
	private JComboBox cbContratosPorSafra;
	private boolean notificando = false;
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private String ids_safras_em_evidencia = "";
	private GraficoLinha linha = null;
	TelaPost telaPost;
	private TelaTodasNotasFiscais telaTodasNotasFiscais;
	private String url_lbl_avisos;

	public JLabel getLblAvisos() {
		return lblAvisos;
	}

	private JPanel painelInfoConexao;

	public void setLblAvisos(JLabel lblAvisos) {
		this.lblAvisos = lblAvisos;
	}

	private GerenciarBancoNotas gerenciarAnotacoes;
	private GerenciarBancoContratos gerenciarAtualizarTarefas, gerenciarDadosContrato, gerenciarCarregamentoPorPeriodo;
	private GerenciarBancoPadrao gerenciarBancoPadrao;
	DefaultTableModel modelo_usuarios = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private JLabel lblAvisos;

	private TelaAvisosSistema tela_avisos;
	private JLabel imgBaseDados, imgBaseArquivos, imgInternet, imgNuvem, imgWhatsapp, imgRelogioPonto, lblNovaMensagem;;
	private TelaChat telaChat;
	private TelaMain isto;
	private JDialog telaPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private JPanel painelGraficoContratos;
	private JPanel painelGraficoRecebimento;
	private JTextArea textAreaSafrasEvidencia;

	public TelaMain(Window janela_pai) {

		getDadosGlobais();
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaMain.class.getResource("/imagens/logo_icone4.png")));
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
		dados.setTelaMain(this);
		setTitle("E-Contract");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, d.width, d.height - taskBarHeight);
		// setBounds(0, 0, 1382, 761);

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBackground(new Color(255, 255, 255));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[grow][grow][grow][][grow]", "[86px][350px,grow]"));

		JPanel panel = new JPanel();
		painelPrincipal.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[86px]"));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.WHITE);
		panel.add(menuBar, "cell 0 0,grow");

		JMenu Dados = new JMenu("Cadastros");
		Dados.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_cadastro_menu.png")));
		Dados.setBackground(Color.WHITE);
		Dados.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(Dados);
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.setMargin(new Insets(0, 10, 0, 0));
		mntmClientes.setBackground(Color.WHITE);
		mntmClientes.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/equipe.png")));
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
		mntmArmazns.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/armazem.png")));
		mntmArmazns.setBackground(Color.WHITE);
		mntmArmazns.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmArmazns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaArmazem tela = new TelaArmazem(0, isto);
			}
		});
		Dados.add(mntmArmazns);
		JMenuItem mntmSafra = new JMenuItem("Safra");
		mntmSafra.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/cultivo.png")));
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
		mntmProdutos.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/comida.png")));
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
		mntmUsurios.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmUsurios.setMargin(new Insets(0, 10, 0, 0));
		mntmUsurios.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/usuarios.png")));
		mntmUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaUsuarios usuarios = new TelaUsuarios(0, isto);
				usuarios.setVisible(true);
			}
		});
		Dados.add(mntmUsurios);
		JMenuItem mntmNewMenuItem = new JMenuItem("Transportadores");
		mntmNewMenuItem.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmNewMenuItem.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/caminhao.png")));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores tela = new TelaTransportadores(0, null);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmNewMenuItem);

		JMenuItem mntmSilos = new JMenuItem("Silos");
		mntmSilos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmSilos.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/silos.png")));
		mntmSilos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSilos tela = new TelaSilos(0, isto);
				tela.setVisible(true);
			}
		});
		mntmSilos.setMargin(new Insets(0, 10, 0, 0));
		Dados.add(mntmSilos);

		JMenuItem mntmClassificadores = new JMenuItem("Classificadores");
		mntmClassificadores.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/genetica.png")));
		mntmClassificadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaClassificadores tela = new TelaClassificadores(0, isto);
				tela.setVisible(true);

			}
		});
		mntmClassificadores.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmClassificadores.setMargin(new Insets(0, 10, 0, 0));
		Dados.add(mntmClassificadores);

		JMenuItem mntmTransgnese = new JMenuItem("Transgênese");
		mntmTransgnese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaTransgenias tela = new TelaTransgenias(0, isto);
				tela.setVisible(true);

			}
		});
		mntmTransgnese.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/gmo.png")));
		mntmTransgnese.setMargin(new Insets(0, 10, 0, 0));
		mntmTransgnese.setFont(new Font("SansSerif", Font.PLAIN, 16));
		Dados.add(mntmTransgnese);
		
		JMenuItem mntmCotaes = new JMenuItem("Cotações");
		mntmCotaes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCotacoes tela = new TelaCotacoes(isto);
				tela.setVisible(true);
				
			}
		});
		mntmCotaes.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/cotacao.png")));
		mntmCotaes.setMargin(new Insets(0, 10, 0, 0));
		mntmCotaes.setFont(new Font("SansSerif", Font.PLAIN, 16));
		Dados.add(mntmCotaes);
		JMenu mnContratos = new JMenu("Contratos");
		mnContratos.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_contrato_menu.png")));
		mnContratos.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnContratos);
		JMenuItem mntmContratos = new JMenuItem("Contratos");
		mntmContratos.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/contrato.png")));
		mntmContratos.setMargin(new Insets(0, 10, 0, 0));
		mntmContratos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos telaContratos = null;

				if (telaContratos == null) {
					telaContratos = new TelaContratos(0, isto);
					telaContratos.setVisible(true);

				} else
					telaContratos.setVisible(true);

			}
		});
		mnContratos.add(mntmContratos);
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Relatoria");
		mntmNewMenuItem_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/grafico.png")));
		mntmNewMenuItem_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRelatoriaContratos tela = new TelaRelatoriaContratos(isto);
			}
		});

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Recebimentos");
		mntmNewMenuItem_7.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/cacamba_descarga.jpg")));
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRecebimentos tela = new TelaRecebimentos(isto);
				tela.setVisible(true);
			}
		});

		JMenuItem mntmNewMenuItem_7_1_1 = new JMenuItem("Aditivos");
		mntmNewMenuItem_7_1_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/aditivos.png")));
		mntmNewMenuItem_7_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAditivos tela = new TelaAditivos(isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_7_1_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_7_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_7_1_1);

		JMenuItem mntmNewMenuItem_7_1_1_2 = new JMenuItem("Distratos");
		mntmNewMenuItem_7_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaDistratos tela = new TelaDistratos(isto);
				tela.setVisible(true);

			}
		});
		mntmNewMenuItem_7_1_1_2.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/distrato.jpg")));
		mntmNewMenuItem_7_1_1_2.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_7_1_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_7_1_1_2);
		mntmNewMenuItem_7.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_7.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_7);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Pagamentos");
		mntmNewMenuItem_8.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/pagamento.png")));
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPagamentos tela = new TelaPagamentos(isto);
				tela.setVisible(true);
			}
		});

		JMenuItem mntmNewMenuItem_7_1_1_1 = new JMenuItem("Carregamentos");
		mntmNewMenuItem_7_1_1_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/cacamba.png")));
		mntmNewMenuItem_7_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCarregamentos tela = new TelaCarregamentos(isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_7_1_1_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_7_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_7_1_1_1);
		mntmNewMenuItem_8.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_8.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_8);
		mnContratos.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_1_1 = new JMenuItem("Notas Fiscais Recebimentos");
		mntmNewMenuItem_1_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/taxa.png")));
		mntmNewMenuItem_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaControleNotasFiscaisRecebimento tela = new TelaControleNotasFiscaisRecebimento(isto);
				tela.setVisible(true);

			}
		});
		mntmNewMenuItem_1_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_1_1);
		
		JMenuItem mntmNewMenuItem_1_1_1 = new JMenuItem("Notas Fiscais Carregamentos");
		mntmNewMenuItem_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaControleNotasFiscaisCarregamento tela = new TelaControleNotasFiscaisCarregamento(isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_1_1_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/taxa.png")));
		mntmNewMenuItem_1_1_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContratos.add(mntmNewMenuItem_1_1_1);

		JMenu mnNewMenu_1 = new JMenu("Financeiro");
		mnNewMenu_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/financa.png")));
		mnNewMenu_1.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Finanças");
		mnNewMenu_1.add(mntmNewMenuItem_6);
		mntmNewMenuItem_6.setFont(new Font("Arial", Font.PLAIN, 16));
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (login != null) {
					if (login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
						TelaFinanceiro tela = new TelaFinanceiro(isto);

						tela.setVisible(true);
						tela.atualizarGrafico();
					} else {

						// verifica se tem acesso temporario
						GerenciarBancoAcessoTemporario gerenciar = new GerenciarBancoAcessoTemporario();
						ArrayList<CadastroAcessoTemporario> acessos = gerenciar
								.getAcessosTemporariosPorExecutor(login.getId());

						boolean tem_acesso = false;

						for (CadastroAcessoTemporario acesso : acessos) {

							int modulo = acesso.getModulo();
							if (modulo == 1) {
								// modulo e recursos humanos
								LocalDateTime inicio = LocalDateTime.parse(
										acesso.getData_inicial() + " " + acesso.getHora_inicial(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
								LocalDateTime fim = LocalDateTime.parse(
										acesso.getData_final() + " " + acesso.getHora_final(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

								LocalDateTime agora = LocalDateTime.now();

								if (agora.isAfter(inicio) && agora.isBefore(fim)) {
									tem_acesso = true;
									break;
								}

							}

						}

						if (!tem_acesso)
							JOptionPane.showMessageDialog(isto, "Requer Elevação de Direitos");
						else {
							TelaFinanceiro tela = new TelaFinanceiro(isto);

							tela.setVisible(true);
							tela.atualizarGrafico();
						}
					}

				}

			}
		});
		mntmNewMenuItem_6.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/financa.png")));
		mntmNewMenuItem_6.setMargin(new Insets(0, 10, 0, 0));

		JMenu mnNewMenu_2 = new JMenu("RH");
		mnNewMenu_2.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/recursos-humanos.png")));
		mnNewMenu_2.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_6_1 = new JMenuItem("Recursos Humanos");
		mnNewMenu_2.add(mntmNewMenuItem_6_1);
		mntmNewMenuItem_6_1.setFont(new Font("Arial", Font.PLAIN, 16));
		mntmNewMenuItem_6_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/recursos-humanos.png")));
		mntmNewMenuItem_6_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaRecursosHumanos tela = new TelaRecursosHumanos(isto);
				tela.setVisible(true);

			}
		});
		mntmNewMenuItem_6_1.setMargin(new Insets(0, 10, 0, 0));
		JMenu mnFerramentas = new JMenu("Ferramentas");
		mnFerramentas.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/ferramentas-de-reparacao.png")));
		mnFerramentas.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnFerramentas);
		JMenu mnPlanilhasDeControle = new JMenu("Planilhas de Controle");
		mnPlanilhasDeControle.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/aplicativo-de-planilha.png")));
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
		mntmNewMenuItem_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmNewMenuItem_2.setMargin(new Insets(0, 10, 2, 0));
		mntmNewMenuItem_2.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/aplicativo-de-monitoria.png")));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (monitor == null) {
					monitor = new TelaMonitoria(isto);
					monitor.setVisible(true);
				} else {
					try {
						monitor.setVisible(true);
					} catch (Exception t) {
						JOptionPane.showMessageDialog(isto, "Erro ao abrir a tela de monitoria");
					}
				}
			}
		});
		mnFerramentas.add(mntmNewMenuItem_2);
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Anotações");
		mntmNewMenuItem_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
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
		mntmNewMenuItem_3.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_notas.png")));
		mnFerramentas.add(mntmNewMenuItem_3);
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Calendário");
		mntmNewMenuItem_4.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmNewMenuItem_4.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_calendario.png")));
		mntmNewMenuItem_4.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_4);
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tarefas");
		mntmNewMenuItem_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaTarefas tela_tarefas = new TelaTarefas(isto);
				tela_tarefas.setVisible(true);

			}
		});
		mntmNewMenuItem_5.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_tarefas.png")));
		mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_5);

		JMenu mnNewMenu_3 = new JMenu("Segurança");
		mnNewMenu_3.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/seguranca.png")));
		mnNewMenu_3.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mnFerramentas.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_5_1 = new JMenuItem("Acesso Temporário");
		mntmNewMenuItem_5_1
				.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/seguranca_acesso_temporario.png")));
		mntmNewMenuItem_5_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (login != null) {
					if (login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
						TelaSeguranca tela = new TelaSeguranca(isto);
						tela.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(isto, "Requer Elevação de Direitos");
					}

				}

			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_5_1);
		mntmNewMenuItem_5_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mntmNewMenuItem_5_1.setMargin(new Insets(0, 10, 0, 0));
		JMenu mnNewMenu = new JMenu("Configurações");
		mnNewMenu.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/preferencias.png")));
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);

		JMenuItem mntmPastas = new JMenuItem("Preferências");
		mntmPastas.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/definicoes.png")));
		mntmPastas.setMargin(new Insets(0, 10, 0, 0));
		mntmPastas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPreferencias tela = new TelaPreferencias();
			}
		});
		mntmPastas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmPastas);

		JMenu mnNewMenu_4 = new JMenu("Utilitário");
		mnNewMenu_4.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_utilidades.png")));
		mnNewMenu_4.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_4);

		JMenuItem mntmNewMenuItem_3_1 = new JMenuItem("Controle de Embarque/Desembarque");
		mntmNewMenuItem_3_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/fila.png")));
		mntmNewMenuItem_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFila tela = new TelaFila(0, isto);
				tela.setVisible(true);

			}
		});
		mntmNewMenuItem_3_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_3_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		mnNewMenu_4.add(mntmNewMenuItem_3_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		painelPrincipal.add(panel_2, "cell 1 0 2 1,grow");
		panel_2.setLayout(new MigLayout("", "[][][][grow]", "[grow][][grow]"));

		lblUser = new JLabel();
		panel_2.add(lblUser, "cell 0 0 4 1,alignx center,growy");
		lblUser.setText("<dynamic> <dynamic>");
		lblUser.setForeground(Color.BLACK);
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblUser.setBackground(Color.WHITE);

		lblDireitos = new JLabel();
		panel_2.add(lblDireitos, "cell 0 1 4 2,alignx center,growy");
		lblDireitos.setText("Administrador do Sistema");
		lblDireitos.setForeground(Color.BLACK);
		lblDireitos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDireitos.setBackground(Color.WHITE);

		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();

		pesquisarSafras();
		for (CadastroSafra safra : safras) {
			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);
		}

		lblUser.setText(login.getNome() + " " + login.getSobrenome());

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[grow]", "[][grow][]"));

		painelPrincipal.add(panel_3, "cell 0 1 3 1,grow");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 102, 102));
		panel_3.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(new MigLayout("", "[][][grow][][][500px:n][]", "[][]"));

		JLabel lblNewLabel_11 = new JLabel("SAFRAS EM EVIDÊNCIA ->");
		lblNewLabel_11.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_11, "cell 0 0 2 1,alignx right");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 16));

		textAreaSafrasEvidencia = new JTextArea();
		panel_5.add(textAreaSafrasEvidencia, "cell 2 0,grow");
		textAreaSafrasEvidencia.setEditable(false);
		textAreaSafrasEvidencia.setFont(new Font("SansSerif", Font.BOLD, 14));
		textAreaSafrasEvidencia.setWrapStyleWord(true);
		textAreaSafrasEvidencia.setLineWrap(true);

		JButton btnNewButton_1 = new JButton("Alterar");
		panel_5.add(btnNewButton_1, "cell 3 0");
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaEditarSafrasEvidencia tela = new TelaEditarSafrasEvidencia(isto);
				tela.setVisible(true);

			}
		});
		btnNewButton_1.setBackground(new Color(0, 0, 51));
		btnNewButton_1.setForeground(Color.WHITE);
		cbContratosPorSafra = new JComboBox();
		panel_5.add(cbContratosPorSafra, "cell 4 0 2 1,growx");
		cbContratosPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbContratosPorSafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
					criarGraficoContratos(safra.getId_safra() + "");
					criarGraficoRecebimento(safra.getId_safra() + "");
					criarGraficoCarregamento(safra.getId_safra() + "");

				} catch (Exception t) {
				}
			}
		});
		cbContratosPorSafra.setModel(modelSafra);
		cbContratosPorSafra.setRenderer(cBSafraPersonalizado);
		cbContratosPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));

		JButton btnTodasAsSafras = new JButton("Todas as Safras");
		panel_5.add(btnTodasAsSafras, "cell 6 0");

		btnTodasAsSafras.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnTodasAsSafras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				criarGraficoContratos("0");
				criarGraficoRecebimento("0");
				criarGraficoCarregamento("0");

			}
		});
		btnTodasAsSafras.setBackground(new Color(0, 0, 102));
		btnTodasAsSafras.setForeground(Color.WHITE);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new MigLayout("", "[600px,grow][600px,grow][600px,grow]", "[350px,grow]"));

		JScrollPane scrollPane = new JScrollPane(panel_1);
		panel_3.add(scrollPane, "cell 0 1,grow");

		painelGraficoContratos = new JPanel();
		panel_1.add(painelGraficoContratos, "cell 0 0");
		painelGraficoContratos.setBackground(Color.WHITE);
		painelGraficoContratos.setLayout(new MigLayout("", "[]", "[]"));

		painelGraficoRecebimento = new JPanel();
		panel_1.add(painelGraficoRecebimento, "cell 1 0");
		painelGraficoRecebimento.setBackground(Color.WHITE);
		painelGraficoRecebimento.setLayout(new MigLayout("", "[]", "[]"));

		painelGraficoCarregamentos = new JPanel();
		panel_1.add(painelGraficoCarregamentos, "cell 2 0");
		painelGraficoCarregamentos.setBackground(Color.WHITE);
		painelGraficoCarregamentos.setLayout(new MigLayout("", "[]", "[]"));

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

		imgBaseArquivos = new JLabel("");
		imgBaseArquivos.setBounds(21, 152, 32, 32);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 102, 102));
		painelPrincipal.add(panel_4, "cell 3 0 1 2,grow");
		panel_4.setLayout(new MigLayout("", "[]", "[][][][][][][][grow][]"));

		lblNovaMensagem = new JLabel("");
		panel_4.add(lblNovaMensagem, "cell 0 0,alignx center");
		lblNovaMensagem.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/mensagens.png")));
		lblNovaMensagem.setForeground(Color.WHITE);
		lblNovaMensagem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNovaMensagem.setBounds(33, 17, 32, 32);

		lblNovaMensagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (telaChat == null) {
					telaChat = new TelaChat(isto);
					telaChat.setTelaPai(isto);
				} else {
					telaChat.setVisible(true);
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							lblNovaMensagem
									.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/mensagens.png")));
							lblNovaMensagem.repaint();
							lblNovaMensagem.updateUI();
						}
					});
				}
			}
		});

		JLabel lblNewLabel_8 = new JLabel("");
		panel_4.add(lblNewLabel_8, "cell 0 1");
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
		lblNewLabel_8.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_anotacoes_tela_principal.png")));

		JLabel lblNewLabel_1 = new JLabel("");
		panel_4.add(lblNewLabel_1, "cell 0 2");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				TelaTarefas tela_tarefas = new TelaTarefas(isto);
				tela_tarefas.setVisible(true);

			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_tarefa.png")));

		JLabel lblNewLabel_2 = new JLabel("Você tem:");
		lblNewLabel_2.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_2, "cell 0 3,alignx center");

		lblNumeroTarefas = new JLabel("0");
		lblNumeroTarefas.setForeground(Color.WHITE);
		panel_4.add(lblNumeroTarefas, "cell 0 4,alignx center");
		lblNumeroTarefas.setFont(new Font("Tahoma", Font.BOLD, 18));

		JLabel lblNewLabel_4 = new JLabel("tarefas");
		lblNewLabel_4.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_4, "cell 0 5,alignx center");

		painelInfoConexao = new JPanel();
		panel_4.add(painelInfoConexao, "cell 0 7,alignx center,aligny center");
		painelInfoConexao.setBackground(new Color(0, 102, 102));
		painelInfoConexao.setLayout(new MigLayout("", "[]", "[][][][][][]"));

		painelInfoConexao.setBorder(new LineBorder(new Color(0, 0, 0)));

		imgInternet = new JLabel("");
		imgInternet.setBounds(21, 59, 32, 32);
		painelInfoConexao.add(imgInternet, "cell 0 1");

		imgBaseDados = new JLabel("");
		imgBaseDados.setBounds(21, 196, 32, 32);
		painelInfoConexao.add(imgBaseDados, "cell 0 2");

		imgRelogioPonto = new JLabel("");
		imgRelogioPonto.setBounds(21, 302, 32, 32);
		painelInfoConexao.add(imgRelogioPonto, "cell 0 3");

		imgWhatsapp = new JLabel("");
		imgWhatsapp.setBounds(21, 245, 32, 32);
		painelInfoConexao.add(imgWhatsapp, "cell 0 4");

		imgNuvem = new JLabel("");
		imgNuvem.setBounds(21, 109, 32, 32);
		painelInfoConexao.add(imgNuvem, "cell 0 5");

		lblAvisos = new JLabel("");
		lblAvisos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tela_avisos.setVisible(true);
			}
		});
		lblAvisos.setForeground(Color.WHITE);
		lblAvisos.setBackground(new Color(0, 0, 0));
		lblAvisos.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_sem_avisos.png")));
		url_lbl_avisos = "/imagens/icone_sem_avisos.png";
		panel_4.add(lblAvisos, "cell 0 8,grow");

		modelo_usuarios.addColumn("Usuario");
		modelo_usuarios.addColumn("Ip");
		modelo_usuarios.addColumn("Status");

		if (telaChat == null) {
			telaChat = new TelaChat(isto);
			telaChat.setTelaPai(isto);

		}

		if (tela_avisos == null) {
			tela_avisos = new TelaAvisosSistema(isto);

		}

		pesquisarSafrasEvidencias();
		if (gerenciarDadosContrato == null)
			gerenciarDadosContrato = new GerenciarBancoContratos();

		ThreadGlobal();
		vigiarRomaneios();

		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);

	}

	public TelaAvisosSistema getTela_avisos() {
		return tela_avisos;
	}

	public void setTela_avisos(TelaAvisosSistema tela_avisos) {
		this.tela_avisos = tela_avisos;
	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();

		// telaTodasNotasFiscais
		telaTodasNotasFiscais = dados.getTelaTodasNotasFiscais();
	}

	public void novaNotificacao(String texto, String song, int repeticao) {
		try {
			notificando = true;
			Thread.sleep(1000);
			URL url = TelaMain.class.getResource(song);
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

	public void novoAvisoNotificacao(CadastroNota nota, String song, int repeticao) {
		try {
			Thread.sleep(1000);
			URL url = TelaMain.class.getResource(song);
			TelaInformarNotificacao tela = new TelaInformarNotificacao();
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
			tela.setMensagem(nota);
			Thread.sleep(10000);
			tela.fechar();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ThreadGlobal() {
		new Thread() {
			public void run() {
				while (true) {

					// dados de contrato

					// atualizarGraficoContratos(ids_safras_em_evidencia);
					criarGraficoContratos(ids_safras_em_evidencia);

					// dados de recebimento
					criarGraficoRecebimento(ids_safras_em_evidencia);

					// dados de carregamento
					criarGraficoCarregamento(ids_safras_em_evidencia);

					// busca anotaçoes

					if (gerenciarAnotacoes == null)
						gerenciarAnotacoes = new GerenciarBancoNotas();
					ArrayList<CadastroNota> anotacoes = gerenciarAnotacoes.getnotasNotificar(login.getId());
					String data = new GetData().getDataHora();

					LocalDateTime data_hora_atual = LocalDateTime.parse(data,
							DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

					for (CadastroNota anotacao : anotacoes) {
						try {

							if (anotacao.getUltima_notificacao() != null) {

								LocalDateTime ultima_notificacao = LocalDateTime.parse(anotacao.getUltima_notificacao(),
										DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

								int unidade_tempo = anotacao.getUni_tempo();
								long intervalo = ChronoUnit.MINUTES.between(ultima_notificacao, data_hora_atual);
								boolean prosseguir = false;

								if (unidade_tempo == 1) {
									// minutos
									if (intervalo >= anotacao.getTempo_notificacao()) {
										// ja passou x minutos
										prosseguir = true;

									}

								} else if (unidade_tempo == 2) {
									// horas
									if (intervalo >= (anotacao.getTempo_notificacao() * 60)) {
										// ja passou x horas
										prosseguir = true;

									}
								} else if (unidade_tempo == 3) {
									// dias
									if (intervalo >= (anotacao.getTempo_notificacao() * 1440)) {
										// ja passou x dias
										prosseguir = true;

									}
								}

								if (prosseguir) {
									System.out.println("notificar");

									novoAvisoNotificacao(anotacao, "/main/java/audio/avisar_notificacao.wav", 2);
									// atualizar notificacao

									if (anotacao.getId_usuario_pai() == login.getId()) {
										anotacao.setUltima_notificacao(data);
										boolean atualizou = gerenciarAnotacoes.atualizarUltimaNotificacao(anotacao);
										if (atualizou) {

										} else {
											CadastroAviso avisar = new CadastroAviso();
											avisar.setMensagem("Erro ao atualizar horario da ultima notificadao");
											avisar.setSetor("Notificações");
											avisar.setTipo("Erro");
											tela_avisos.incluir_aviso(avisar);
										}
									}
								}

							}
						} catch (Exception e) {

						}
					}

					/***********************************************************************/

					// busca conexao com o servidor
					CadastroBaseArquivos base = configs_globais.getServidor_arquivos();
					String host = base.getServidor();
					TesteConexao gerenciar = new TesteConexao();
					if (gerenciar.doPing(host)) {
						System.out.println("Banco de Arquivos OnLine!");
						imgBaseArquivos.setIcon(
								new ImageIcon(TelaMain.class.getResource("/imagens/base_arquivos_online.png")));
					} else {
						System.out.println("Banco de Arquivos Offline!");
						imgBaseArquivos.setIcon(
								new ImageIcon(TelaMain.class.getResource("/imagens/base_arquivos_offline.png")));
					}

					/*************************************************/
					// busca conexao com o banco
					CadastroBaseDados bd;
					bd = configs_globais.getBaseDados();
					String url = "jdbc:mysql://" + bd.getHost() + ":" + bd.getPorta() + "/" + bd.getNome_banco()
							+ "?useTimezone=true&serverTimezone=UTC";
					if (gerenciarBancoPadrao == null)
						gerenciarBancoPadrao = new GerenciarBancoPadrao();
					if (gerenciarBancoPadrao.getConexao()) {
						System.out.println("Banco de Dados OnLine!");
						imgBaseDados
								.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/base_dados_online.png")));
					} else {
						System.out.println("Banco de Dados Offline!");
						imgBaseDados
								.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/base_dados_offline.png")));
					}

					/**************************************************************/
					// busca conexao whatsapp
					try {
						Whatsapp zap = new Whatsapp();

						if (zap.status().contains("OK")) {
							// conectado
							imgWhatsapp.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/zap_online.png")));
						} else {
							// nao conectado
							imgWhatsapp.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/zap_offline.png")));
						}

					} catch (Exception f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar ao whatsapp!");
						while (notificando) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						novaNotificacao("Sem conexão com o Whatsapp, algumas funções seram limitadas até a reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);
						imgWhatsapp.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/zap_offline.png")));
					}

					/*****************************************************/

					// conexao com a internet
					try {
						URL url3 = new URL("http://www.google.com.br");
						System.out.println("Tentando conexao!");
						URLConnection connection = url3.openConnection();
						connection.connect();
						imgInternet.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/internet_online.png")));
					} catch (IOException f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar a internet!");
						while (notificando) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						novaNotificacao("Sem conexão com a internet, algumas funções seram limitadas até a reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);
						imgInternet.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/internet_offline.png")));
					}
					/*****************************************************************/
					// busca conexao com o relogio de ponto
					try {
						InetAddress address = InetAddress.getByName(configs_globais.getIp_relogio());
						boolean reachable = address.isReachable(5000);
						if (reachable) {
							DadosGlobais.getInstance().setStatus_relogio(1);

							imgRelogioPonto
									.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_online.png")));

						} else {
							DadosGlobais.getInstance().setStatus_relogio(0);

							System.out.println("erro ao se conectar ao relogio");
							imgRelogioPonto
									.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_offline.png")));

							while (notificando) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							novaNotificacao("Sem conexão com o relógio de ponto, aguardando reconexão!",
									"/main/java/audio/beep_erro_net.wav", 2);

						}

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("erro ao se conectar ao relogio");
						while (notificando) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
						DadosGlobais.getInstance().setStatus_relogio(0);

						imgRelogioPonto.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_offline.png")));

						novaNotificacao("Sem conexão com o relógio de ponto, aguardando reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);

					}

					/**********************************************************************/
					// busca conexao com a nuvem
					try {
						URL url2 = new URL("http://www.dropbox.com");
						System.out.println("Tentando conexao nuvem!");
						URLConnection connection = url2.openConnection();
						connection.connect();
						imgNuvem.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/nuvem_online.png")));
					} catch (IOException f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar ao dropbpx!");
						while (notificando) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						novaNotificacao("Sem conexão com a nuvem, algumas funções seram limitadas até a reconexão!",
								"/main/java/audio/beep_erro_net.wav", 2);
						imgNuvem.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/nuvem_offline.png")));
					}
					/***********************************************************/
					// busca tarefas
					try {
						if (gerenciarAtualizarTarefas == null)
							gerenciarAtualizarTarefas = new GerenciarBancoContratos();
						int num_agora = gerenciarAtualizarTarefas.getNumTarefas(login.getId());
						lblNumeroTarefas.setText(num_agora + "");
						if (num_tarefas_nesta_secao == -1) {
							if (num_agora > 0) {
								while (notificando == true) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								novaNotificacao("Você possui tarefas a concluir",
										"/main/java/audio/beep_notificacao.wav", 1);
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
					} catch (Exception e) {

					}
					/***********************************************************************/

					if (!executou) {

						int assinar = dados_contratos.getNumero_total_contratos()
								- dados_contratos.getNumero_contratos_assinados();

						if (assinar != -1) {

							while (notificando) {
							}
							novaNotificacao("Há " + assinar + " documentos com carência de assinatura na base de dados",
									"/main/java/audio/beep_notificacao.wav", 1);

							executou = true;
						} else {
							System.out.println(
									"Não foi possivel buscar o numero de contratos sem assinar no banco de dados!");
						}
					}
					try {
						Thread.sleep(45000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();
	}

	public void setNumeroMensagensNovas() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				lblNovaMensagem.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/nova_mensagem.png")));
				lblNovaMensagem.repaint();
				lblNovaMensagem.updateUI();
			}
		});
	}

	public void setNovaNotificacaoMensagem(String mensagem) {
		// if(!telaChat.isVisible())
		try {
			if (!telaChat.isVisible()) {
				notificando = true;
				TelaNotificacaoSuperior tela = new TelaNotificacaoSuperior();
				tela.setMensagem(mensagem);
				tela.setVisible(true);
				new Thread() {
					@Override
					public void run() {
						ReproduzirAudio player = new ReproduzirAudio();
						URL url = TelaMain.class.getResource("/main/java/audio/nova_mensagem.wav");

						for (int i = 0; i < 1; i++) {
							player.play(url);
						}
					}
				}.start();
				Thread.sleep(5000);
				tela.fechar();
				notificando = false;
			}
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
								// System.out.println("Notificacao em andamento");
							}
							novaNotificacao("Você possui tarefas a concluir", "/main/java/audio/beep_notificacao.wav",
									1);
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
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, "Erro ao buscar tarefas!");
						e.printStackTrace();
					}
				}
			}
		}.start();
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

	public void pesquisarSafrasEvidencias() {

		GerenciarBancoSafrasEvidencias gerenciar = new GerenciarBancoSafrasEvidencias();
		ArrayList<CadastroSafrasEvidencias> safras_envidencia = gerenciar.getSafrasEvidenciaPorUsuario(login.getId());

		GerenciarBancoSafras gerenciar_safras = new GerenciarBancoSafras();

		ids_safras_em_evidencia = "";

		if (safras_envidencia != null) {
			if (safras_envidencia.size() > 0) {

				for (CadastroSafrasEvidencias cad : safras_envidencia) {
					ids_safras_em_evidencia += (cad.getIds_safras() + ",");

				}

			}
		}

		String descricao_safras_evidencia = "";

		if (safras_envidencia != null) {
			if (safras_envidencia.size() > 0) {

				for (CadastroSafrasEvidencias cad : safras_envidencia) {
					String ids_safras = cad.getIds_safras();
					if (ids_safras.length() > 0) {

						String ids_separados[] = ids_safras.split(",");
						for (String id : ids_separados) {

							try {

								int int_id = Integer.parseInt(id);
								CadastroSafra safra = gerenciar_safras.getSafra(int_id);
								if (safra != null) {

									String produto_l = safra.getProduto().getNome_produto() + " "
											+ safra.getProduto().getTransgenia();
									String safra_l = safra.getAno_plantio() + "/" + safra.getAno_colheita();

									String descricao_local = produto_l + " " + safra_l + "  ";

									descricao_safras_evidencia += descricao_local;
								}

							} catch (Exception e) {

							}

						}

					}
				}

			}
		}

		textAreaSafrasEvidencia.setText(descricao_safras_evidencia);

	}

	public void criarGraficoContratos(String ids_safras) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				painelGraficoContratos.removeAll();
				painelGraficoContratos.repaint();
				painelGraficoContratos.updateUI();

				DefaultPieDataset pizza = new DefaultPieDataset();
				dados_contratos = gerenciarDadosContrato.getNumeroTotalContratos(ids_safras);

				pizza.setValue("CTR p/ ass",
						dados_contratos.getNum_cts_originais() - dados_contratos.getNum_cts_originais_assinados());
				pizza.setValue("SCTR p/ ass",
						dados_contratos.getNum_sub_cts() - dados_contratos.getNum_sub_cts_assinados());
				pizza.setValue("ADT p/ ass",
						dados_contratos.getNum_total_aditivos() - dados_contratos.getNum_total_aditivos_assinados());
				pizza.setValue("DTR p/ ass",
						dados_contratos.getNum_total_distratos() - dados_contratos.getNum_total_distratos_assinados());

				pizza.setValue("CTR Ass", dados_contratos.getNum_cts_originais_assinados());
				pizza.setValue("SCTR Ass", dados_contratos.getNum_sub_cts_assinados());
				pizza.setValue("ADT Ass", dados_contratos.getNum_total_aditivos_assinados());
				pizza.setValue("DTR Ass", dados_contratos.getNum_total_distratos_assinados());

				pizza.setValue("CTR can", dados_contratos.getNum_cts_cancelados());

				RingPlot plot = new RingPlot(pizza);
				// StringBuffer chartFileName = new
				// StringBuffer(Integer.toString(generatedCharts)).append(Long.toString(System.currentTimeMillis())).append(".png");

				JFreeChart grafico = new JFreeChart("Informações de Documentos", JFreeChart.DEFAULT_TITLE_FONT, plot,
						true);

				grafico.setBackgroundPaint(new GradientPaint(new Point(0, 0), new Color(20, 20, 20),
						new Point(350, 500), Color.DARK_GRAY));

				TextTitle t = grafico.getTitle();
				t.setHorizontalAlignment(HorizontalAlignment.CENTER);
				t.setPaint(new Color(0, 51, 0));
				t.setFont(new Font("Arial", Font.BOLD, 18));

				plot.setBackgroundPaint(null);
				plot.setOutlineVisible(false);
				plot.setSectionDepth(0.5);
				plot.setSectionOutlinesVisible(false);
				plot.setShadowPaint(null);
				plot.setOuterSeparatorExtension(0);
				plot.setInnerSeparatorExtension(0);

				plot.setInteriorGap((double) 0.0001);
				plot.setMaximumLabelWidth(0.2);
				// plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}",new
				// DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} {0}"));// define porcentagem no gráfico

				plot.setLabelOutlinePaint(null);

				plot.setSectionPaint("CTR Ass", new Color(0, 51, 0));
				plot.setSectionPaint("SCTR Ass", new Color(0, 51, 51));
				plot.setSectionPaint("ADT Ass", new Color(0, 102, 0));
				plot.setSectionPaint("DTR Ass", new Color(0, 102, 102));

				plot.setSectionPaint("CTR p/ ass", new Color(153, 0, 0));
				plot.setSectionPaint("SCTR p/ ass", new Color(204, 0, 0));
				plot.setSectionPaint("ADT p/ ass", new Color(255, 0, 2));
				plot.setSectionPaint("DTR p/ ass", new Color(255, 102, 102));
				plot.setSectionPaint("CTR can", new Color(255, 102, 0));

				plot.setNoDataMessage("Nenhuma Safra Selecionada");

				Font font = new Font("", 0, 12);
				plot.setLabelFont(font);
				plot.setLabelPaint(java.awt.Color.BLACK);
				plot.setLabelBackgroundPaint(null);
				plot.setLabelOutlinePaint(null);
				plot.setLabelBackgroundPaint(java.awt.Color.WHITE);

				plot.setSeparatorStroke(new BasicStroke(1));
				plot.setSeparatorPaint(Color.white);
				plot.setExplodePercent("CTR p/ ass", 0.2);
				plot.setExplodePercent("SCTR p/ ass", 0.1);

				grafico.getLegend().setFrame(BlockBorder.NONE);
				grafico.getLegend().setPosition(RectangleEdge.LEFT);
				grafico.setBackgroundPaint(java.awt.Color.white);
				grafico.setPadding(new RectangleInsets(0, 0, 0, 0));

				ChartPanel painel = new ChartPanel(grafico);
				painel.setBackground(Color.white);
				painel.setBounds(0, 0, 350, 500);

				painelGraficoContratos.add(painel);
				painelGraficoContratos.repaint();
				painelGraficoContratos.updateUI();

			}
		});
	}

	public void criarGraficoRecebimento(String ids_safras) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				painelGraficoRecebimento.removeAll();
				painelGraficoRecebimento.repaint();
				painelGraficoRecebimento.updateUI();

				DefaultPieDataset pizza = new DefaultPieDataset();
				dados_recebimentos = gerenciarDadosContrato.getInfoRecebimento(ids_safras);

				pizza.setValue("SCs Recebidos", dados_recebimentos.getQuantidade_total_recebidos());
				pizza.setValue("SCs p/ Receber", dados_recebimentos.getQuantidade_total_sacos()
						- dados_recebimentos.getQuantidade_total_recebidos());

				RingPlot plot = new RingPlot(pizza);
				// StringBuffer chartFileName = new
				// StringBuffer(Integer.toString(generatedCharts)).append(Long.toString(System.currentTimeMillis())).append(".png");

				JFreeChart grafico = new JFreeChart("Informações de Recebimento", JFreeChart.DEFAULT_TITLE_FONT, plot,
						true);

				grafico.setBackgroundPaint(new GradientPaint(new Point(0, 0), new Color(20, 20, 20),
						new Point(350, 500), Color.DARK_GRAY));

				TextTitle t = grafico.getTitle();
				t.setHorizontalAlignment(HorizontalAlignment.CENTER);
				t.setPaint(new Color(0, 0, 51));
				t.setFont(new Font("Arial", Font.BOLD, 18));

				plot.setBackgroundPaint(null);
				plot.setOutlineVisible(false);
				plot.setSectionDepth(0.5);
				plot.setSectionOutlinesVisible(false);
				plot.setShadowPaint(null);
				plot.setOuterSeparatorExtension(0);
				plot.setInnerSeparatorExtension(0);

				plot.setInteriorGap((double) 0.0001);
				plot.setMaximumLabelWidth(0.2);
				// plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}",new
				// DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} {0}"));// define porcentagem no gráfico

				plot.setLabelOutlinePaint(null);

				plot.setSectionPaint("SCs Recebidos", new Color(51, 0, 255));
				plot.setSectionPaint("SCs p/ Receber", new Color(255, 102, 102));

				plot.setNoDataMessage("Nenhuma Safra Selecionada");

				Font font = new Font("", 0, 12);
				plot.setLabelFont(font);
				plot.setLabelPaint(java.awt.Color.BLACK);
				plot.setLabelBackgroundPaint(null);
				plot.setLabelOutlinePaint(null);
				plot.setLabelBackgroundPaint(java.awt.Color.WHITE);

				plot.setSeparatorStroke(new BasicStroke(1));
				plot.setSeparatorPaint(Color.white);

				plot.setExplodePercent("SCs p/ Receber", 0.1);

				grafico.getLegend().setFrame(BlockBorder.NONE);
				grafico.getLegend().setPosition(RectangleEdge.BOTTOM);
				grafico.setBackgroundPaint(java.awt.Color.white);
				grafico.setPadding(new RectangleInsets(0, 0, 0, 0));

				ChartPanel painel = new ChartPanel(grafico);
				painel.setBackground(Color.white);
				painel.setBounds(0, 0, 350, 500);

				painelGraficoRecebimento.add(painel);
				painelGraficoRecebimento.repaint();
				painelGraficoRecebimento.updateUI();
			}
		});
	}

	public void criarGraficoCarregamento(String ids_safras) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				painelGraficoCarregamentos.removeAll();
				painelGraficoCarregamentos.repaint();
				painelGraficoCarregamentos.updateUI();

				DefaultPieDataset pizza = new DefaultPieDataset();
				dados_carregamentos = gerenciarDadosContrato.getInfoCarregamento(ids_safras);

				pizza.setValue("SCs Carregados", dados_carregamentos.getQuantidade_total_carregada());
				pizza.setValue("SCs p/ Carregar", dados_carregamentos.getQuantidade_total_recebidos()
						- dados_carregamentos.getQuantidade_total_carregada());

				RingPlot plot = new RingPlot(pizza);
				// StringBuffer chartFileName = new
				// StringBuffer(Integer.toString(generatedCharts)).append(Long.toString(System.currentTimeMillis())).append(".png");

				JFreeChart grafico = new JFreeChart("Informações de Carregamento", JFreeChart.DEFAULT_TITLE_FONT, plot,
						true);

				grafico.setBackgroundPaint(new GradientPaint(new Point(0, 0), new Color(20, 20, 20),
						new Point(350, 500), Color.DARK_GRAY));

				TextTitle t = grafico.getTitle();
				t.setHorizontalAlignment(HorizontalAlignment.CENTER);
				t.setPaint(new Color(102, 51, 0));
				t.setFont(new Font("Arial", Font.BOLD, 18));

				plot.setBackgroundPaint(null);
				plot.setOutlineVisible(false);
				plot.setSectionDepth(0.5);
				plot.setSectionOutlinesVisible(false);
				plot.setShadowPaint(null);
				plot.setOuterSeparatorExtension(0);
				plot.setInnerSeparatorExtension(0);

				plot.setInteriorGap((double) 0.0001);
				plot.setMaximumLabelWidth(0.2);
				// plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}",new
				// DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} {0}"));// define porcentagem no gráfico

				plot.setLabelOutlinePaint(null);

				plot.setSectionPaint("SCs Carregados", new Color(51, 51, 0));
				plot.setSectionPaint("SCs p/ Carregar", new Color(255, 102, 0));

				plot.setNoDataMessage("Nenhuma Safra Selecionada");

				Font font = new Font("", 0, 12);
				plot.setLabelFont(font);
				plot.setLabelPaint(java.awt.Color.BLACK);
				plot.setLabelBackgroundPaint(null);
				plot.setLabelOutlinePaint(null);
				plot.setLabelBackgroundPaint(java.awt.Color.WHITE);

				plot.setSeparatorStroke(new BasicStroke(1));
				plot.setSeparatorPaint(Color.white);

				grafico.getLegend().setFrame(BlockBorder.NONE);
				grafico.getLegend().setPosition(RectangleEdge.RIGHT);
				grafico.setBackgroundPaint(java.awt.Color.white);
				grafico.setPadding(new RectangleInsets(0, 0, 0, 0));

				ChartPanel painel = new ChartPanel(grafico);
				painel.setBackground(Color.white);
				painel.setBounds(0, 0, 350, 500);
				plot.setExplodePercent("SCs p/ Carregar", 0.1);

				painelGraficoCarregamentos.add(painel);
				painelGraficoCarregamentos.repaint();
				painelGraficoCarregamentos.updateUI();
			}
		});

	}

	public void setarIconeAvisos(String url_imagem) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (!url_lbl_avisos.equalsIgnoreCase(url_imagem)) {

					lblAvisos.setIcon(new ImageIcon(TelaMain.class.getResource(url_imagem)));
					lblAvisos.repaint();
					lblAvisos.updateUI();

					url_lbl_avisos = url_imagem;

				}

			}
		});
	}

}
