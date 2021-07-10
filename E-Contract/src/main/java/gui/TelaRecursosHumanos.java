
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.EventoGlobal;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroPontoDiario;
import main.java.cadastros.RegistroPontoDiarioCompleto;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoEventoGlobal;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoRotina;
import main.java.graficos.GraficoLinhaDupla;
import main.java.gui.TelaFinanceiroLancamento.LancamentoTableModel;
import main.java.gui.TelaFinanceiroLancamento.LancamentosRender;
import main.java.gui.TelaGerenciarFuncionario.CellRenderRPDiario;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.ReproduzirAudio;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.JTextField;

public class TelaRecursosHumanos extends JFrame {

	private JPanel painelPrincipal;
	private TelaRecursosHumanos isto;
	private JLabel imgRfid, lblStatusRelogioPonto;

	private JLabel lblUser, lblDireitos;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private TelaPost telaPost;
	private GerenciarBancoContratos gerenciarAtualizarTarefas;
	private int num_tarefas_nesta_secao = -1;
	private boolean notificando = false;
	private JTextField entDataRegistrosPontos;
	private RegistroPontoDiarioTableModel modeloRps = new RegistroPontoDiarioTableModel();
	private JLabel lblTotalColaboradores;
	private JTable tabela_rps;
	private JLabel lblRegistradoCorretamente, lblFeriado,lblDS, lblFaltou, lblNaoCompleto, lblIsencaoPonto, lblFolga, lblFerias,
			lblLicencas;

	public TelaRecursosHumanos(Window window) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		getDadosGlobais();
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

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);
		isto = this;

		setTitle("Recursos Humanos");

		JMenuBar menuBar = new JMenuBar();

		JMenu mnNewMenu = new JMenu("Cadastros");
		mnNewMenu.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/financeiro_icone_24px.png")));
		mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Colaboradores");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFuncionarios clientes = new TelaFuncionarios(1, isto);
				clientes.setVisible(true);
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/equipe.png")));
		mntmNewMenuItem.setMargin(new Insets(0, 14, 0, 0));
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmNewMenuItem);

		JMenu mnNewMenu_1 = new JMenu("Tabelas Auxiliares");
		mnNewMenu_1.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/tabela.png")));
		mnNewMenu_1.setMargin(new Insets(0, 14, 0, 0));
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_1_2_1 = new JMenuItem("Cartões de Ponto");
		mntmNewMenuItem_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionariosCartoesPonto tela = new TelaFuncionariosCartoesPonto(0, isto);
				tela.setVisible(true);

			}
		});
		mntmNewMenuItem_1_2_1.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/cracha.png")));
		mntmNewMenuItem_1_2_1.setMargin(new Insets(0, 14, 0, 0));
		mntmNewMenuItem_1_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmNewMenuItem_1_2_1);

		JMenuItem mntmNewMenuItem_1_2 = new JMenuItem("Salário Mínimo");
		mntmNewMenuItem_1_2.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/salario.png")));
		mntmNewMenuItem_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionariosSalarioMinimo tela = new TelaFuncionariosSalarioMinimo(0, isto);
				tela.setVisible(true);

			}
		});
		mntmNewMenuItem_1_2.setMargin(new Insets(0, 14, 0, 0));
		mntmNewMenuItem_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmNewMenuItem_1_2);
		painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(
				new MigLayout("", "[grow][grow][242px,grow][grow][grow][grow][grow][][][]", "[78px][][400px:n,grow]"));

		painelPrincipal.add(menuBar, "cell 0 0 3 1,alignx left,aligny center");

		JMenu mnFerramentas = new JMenu("Ferramentas");
		mnFerramentas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/ferramentas-de-reparacao.png")));
		mnFerramentas.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnFerramentas);
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
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaCalendario tela = new TelaCalendario(isto);
				tela.setVisible(true);
				
			}
		});
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
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 13;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 24;
		gbc_panel_1.gridy = 0;

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		painelPrincipal.add(panel_2, "cell 4 0 6 1,grow");
		panel_2.setLayout(new MigLayout("", "[]", "[]"));

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

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 0 1 10 2,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[grow][][grow][grow]"));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, "flowx,cell 0 0");
		panel_3.setLayout(new MigLayout("", "[][][]", "[]"));

		JLabel lblNewLabel = new JLabel("Data:");
		panel_3.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));

		entDataRegistrosPontos = new JTextField();
		panel_3.add(entDataRegistrosPontos, "cell 1 0");
		entDataRegistrosPontos.setFont(new Font("Tahoma", Font.BOLD, 20));
		entDataRegistrosPontos.setColumns(10);

		entDataRegistrosPontos.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		JButton btnNewButton = new JButton("Pesquisar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_rps();
			}
		});
		btnNewButton.setBackground(new Color(153, 0, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnNewButton, "cell 2 0");

		tabela_rps = new JTable(modeloRps);
		tabela_rps.setRowHeight(30);

		CellRenderRPDiario renderer = new CellRenderRPDiario();
		tabela_rps.setDefaultRenderer(Object.class, renderer);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel.add(panel_5, "cell 1 0,alignx right,growy");
		panel_5.setLayout(new MigLayout("", "[][][][][][][][][][][][][][]", "[][][]"));

		JLabel lblRelgioDePonto = new JLabel("Relógio de Ponto:");
		lblRelgioDePonto.setForeground(Color.BLACK);
		lblRelgioDePonto.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(lblRelgioDePonto, "cell 13 0");

		imgRfid = new JLabel("");
		imgRfid.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/rfid_offline.png")));
		panel_5.add(imgRfid, "cell 12 0 1 3,alignx right");

		lblStatusRelogioPonto = new JLabel("Status:");
		lblStatusRelogioPonto.setForeground(Color.BLACK);
		lblStatusRelogioPonto.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_5.add(lblStatusRelogioPonto, "cell 13 1");

		JScrollPane scrollPane = new JScrollPane(tabela_rps);
		panel.add(scrollPane, "cell 0 1 2 2,grow");

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel.add(panel_4, "cell 0 3,grow");
		panel_4.setLayout(new MigLayout("", "[][][][]", "[][][][][][][][][][][][]"));

		JLabel lblNewLabel_3_1 = new JLabel("Total de Colaboradores Ativos:");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblNewLabel_3_1, "cell 0 0");

		lblTotalColaboradores = new JLabel("Registrou Ponto Corretamente");
		lblTotalColaboradores.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblTotalColaboradores, "cell 1 0 3 1");

		JLabel lblNewLabel_3 = new JLabel("Legenda:");
		panel_4.add(lblNewLabel_3, "cell 0 2,alignx right");

		JLabel lblNewLabel_5 = new JLabel("aaaa");
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBackground(new Color(0, 51, 0));
		lblNewLabel_5.setForeground(new Color(0, 51, 0));
		panel_4.add(lblNewLabel_5, "cell 0 3,alignx right,growy");

		JLabel lblRegistroPontoCompleto = new JLabel("RP Completo:");
		lblRegistroPontoCompleto.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblRegistroPontoCompleto, "cell 1 3,alignx right");

		lblRegistradoCorretamente = new JLabel("Registrou Ponto Corretamente");
		lblRegistradoCorretamente.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblRegistradoCorretamente, "cell 2 3 2 1");

		JLabel lblNewLabel_5_1 = new JLabel("aaaa");
		lblNewLabel_5_1.setOpaque(true);
		lblNewLabel_5_1.setForeground(new Color(153, 0, 0));
		lblNewLabel_5_1.setBackground(new Color(153, 0, 0));
		panel_4.add(lblNewLabel_5_1, "cell 0 4,alignx right");

		JLabel lblFaltou_1 = new JLabel("Faltou:");
		lblFaltou_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFaltou_1, "cell 1 4,alignx right");

		lblFaltou = new JLabel("Faltou");
		lblFaltou.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblFaltou, "cell 2 4 2 1");

		JLabel lblNewLabel_5_1_1 = new JLabel("aaaa");
		lblNewLabel_5_1_1.setOpaque(true);
		lblNewLabel_5_1_1.setForeground(new Color(255, 153, 0));
		lblNewLabel_5_1_1.setBackground(new Color(255, 153, 0));
		panel_4.add(lblNewLabel_5_1_1, "cell 0 5,alignx right");

		JLabel lblRpIncompleto = new JLabel("RP Incompleto:");
		lblRpIncompleto.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblRpIncompleto, "cell 1 5,alignx right");

		lblNaoCompleto = new JLabel("Não Completou o Registro de Ponto");
		lblNaoCompleto.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblNaoCompleto, "cell 2 5 2 1");
		
		JLabel lblNewLabel_5_1_1_2 = new JLabel("aaaa");
		lblNewLabel_5_1_1_2.setOpaque(true);
		lblNewLabel_5_1_1_2.setForeground(new Color(0, 102, 51));
		lblNewLabel_5_1_1_2.setBackground(new Color(0, 102, 0));
		panel_4.add(lblNewLabel_5_1_1_2, "cell 0 6,alignx right");
		
		JLabel lblFolga_1_2 = new JLabel("Descanso Semanal:");
		lblFolga_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFolga_1_2, "cell 1 6,alignx right");
		
		 lblDS = new JLabel("0");
		lblDS.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblDS, "cell 2 6 2 1");
		
		JLabel lblNewLabel_5_1_1_2_1 = new JLabel("aaaa");
		lblNewLabel_5_1_1_2_1.setOpaque(true);
		lblNewLabel_5_1_1_2_1.setForeground(new Color(0, 51, 51));
		lblNewLabel_5_1_1_2_1.setBackground(new Color(0, 51, 51));
		panel_4.add(lblNewLabel_5_1_1_2_1, "cell 0 7,alignx right");
		
		JLabel lblFolga_1_2_1 = new JLabel("Feriado:");
		lblFolga_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFolga_1_2_1, "cell 1 7,alignx right");
		
		 lblFeriado = new JLabel("0");
		lblFeriado.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblFeriado, "cell 2 7 2 1");

		JLabel lblNewLabel_5_1_1_1 = new JLabel("aaaa");
		lblNewLabel_5_1_1_1.setOpaque(true);
		lblNewLabel_5_1_1_1.setForeground(new Color(0, 102, 153));
		lblNewLabel_5_1_1_1.setBackground(new Color(0, 102, 153));
		panel_4.add(lblNewLabel_5_1_1_1, "cell 0 8,alignx right");

		JLabel lblFolga_1 = new JLabel("Folga:");
		lblFolga_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFolga_1, "cell 1 8,alignx right");

		lblFolga = new JLabel("Folga");
		lblFolga.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblFolga, "cell 2 8 2 1");

		JLabel lblNewLabel_5_1_1_1_1 = new JLabel("aaaa");
		lblNewLabel_5_1_1_1_1.setOpaque(true);
		lblNewLabel_5_1_1_1_1.setForeground(new Color(153, 102, 0));
		lblNewLabel_5_1_1_1_1.setBackground(new Color(153, 102, 0));
		panel_4.add(lblNewLabel_5_1_1_1_1, "cell 0 9,alignx right");

		JLabel lblFolga_1_1 = new JLabel("Férias:");
		lblFolga_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFolga_1_1, "cell 1 9,alignx right");

		lblFerias = new JLabel("Férias");
		lblFerias.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblFerias, "cell 2 9 2 1");

		JLabel lblNewLabel_5_1_1_1_1_1 = new JLabel("aaaa");
		lblNewLabel_5_1_1_1_1_1.setOpaque(true);
		lblNewLabel_5_1_1_1_1_1.setForeground(new Color(102, 51, 51));
		lblNewLabel_5_1_1_1_1_1.setBackground(new Color(102, 51, 51));
		panel_4.add(lblNewLabel_5_1_1_1_1_1, "cell 0 10,alignx right");

		JLabel lblFolga_1_1_1 = new JLabel("Licença:");
		lblFolga_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFolga_1_1_1, "cell 1 10,alignx right");

		lblLicencas = new JLabel("0");
		lblLicencas.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblLicencas, "cell 2 10 2 1");

		JLabel lblNewLabel_5_1_1_1_1_1_1 = new JLabel("aaaa");
		lblNewLabel_5_1_1_1_1_1_1.setOpaque(true);
		lblNewLabel_5_1_1_1_1_1_1.setForeground(new Color(51, 51, 153));
		lblNewLabel_5_1_1_1_1_1_1.setBackground(new Color(51, 51, 153));
		panel_4.add(lblNewLabel_5_1_1_1_1_1_1, "cell 0 11,alignx right");

		JLabel lblFolga_1_1_1_1 = new JLabel("Isenção de Ponto:");
		lblFolga_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_4.add(lblFolga_1_1_1_1, "cell 1 11,alignx right");

		lblIsencaoPonto = new JLabel("0");
		lblIsencaoPonto.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_4.add(lblIsencaoPonto, "cell 2 11 2 1");

		buscarConexaoPontoRelogio();
		logar();

		pesquisar_rps();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(window);
	}

	public void logar() {
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
	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
	}


	



	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public class RegistroPontoDiarioTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int data = 0;
		private final int dia_semana = 1;
		private final int colaborador = 2;
		private final int entrada1 = 3;
		private final int saida1 = 4;
		private final int entrada2 = 5;
		private final int saida2 = 6;
		private final int entrada3 = 7;
		private final int saida3 = 8;

		private final String colunas[] = { "DATA", "DIA DA SEMANA", "COLABORADOR", "ENTRADA 1", "SAÍDA 1", "ENTRADA 2",
				"SAÍDA 2", "ENTRADA 3", "SAÍDA 3" };

		private final ArrayList<RegistroPontoDiarioCompleto> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public RegistroPontoDiarioTableModel() {

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

			case data:
				return Integer.class;
			case dia_semana:
				return String.class;
			case colaborador:
				return String.class;
			case entrada1:
				return String.class;
			case saida1:
				return Integer.class;
			case entrada2:
				return String.class;
			case saida2:
				return Integer.class;
			case entrada3:
				return String.class;
			case saida3:
				return Integer.class;

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

			// pega o dados corrente da linha
			RegistroPontoDiarioCompleto rp = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case data: {
				return rp.getData();

			}
			case dia_semana: {

				DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));

				LocalDate data = LocalDate.parse(rp.getData(), formatter.ofPattern("dd/MM/yyyy"));
				DayOfWeek dia_s = data.getDayOfWeek();
				return dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();

			}
			case colaborador:
				return rp.getNome_colaborador();
			case entrada1: {
				return rp.getEntrada1();
			}
			case saida1:
				return rp.getSaida1();
			case entrada2:
				return rp.getEntrada2();

			case saida2:
				return rp.getSaida2();
			case entrada3:
				return rp.getEntrada3();
			case saida3:
				return rp.getSaida3();

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
			RegistroPontoDiarioCompleto recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public RegistroPontoDiarioCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(RegistroPontoDiario nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(RegistroPontoDiarioCompleto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<RegistroPontoDiarioCompleto> dadosIn) {
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
		public void onRemove(RegistroPontoDiarioCompleto nota) {
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

	class CellRenderRPDiario extends DefaultTableCellRenderer {

		public CellRenderRPDiario() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setHorizontalAlignment(CENTER);

			String entrada1 = (String) tabela_rps.getValueAt(row, 3);
			String saida1 = (String) tabela_rps.getValueAt(row, 4);
			String entrada2 = (String) tabela_rps.getValueAt(row, 5);
			String saida2 = (String) tabela_rps.getValueAt(row, 6);

			if (entrada1 != null && !entrada1.equalsIgnoreCase("FALTA") && !entrada1.equalsIgnoreCase("FOLGA")
					&& !entrada1.equalsIgnoreCase("FÉRIAS") && !entrada1.equalsIgnoreCase("ISENÇÃO DE PONTO")
					&& !entrada1.equalsIgnoreCase("LICENÇA") && !entrada1.equalsIgnoreCase("DESCANSO SEMANAL")
					&& !entrada1.equalsIgnoreCase("FERIADO")) {

				if (saida1 == null || entrada2 == null || saida2 == null) {
					// deu entrada nesse dia, mas nao completou ponto
					setBackground(new Color(255, 153, 0));// laranja
					setForeground(Color.white);
				} else {
					// rp completo
					setBackground(new Color(0, 51, 0)); // verde
					setForeground(Color.white);
				}

			} else if (entrada1.equalsIgnoreCase("FALTA")) {
				// faltou
				setBackground(new Color(153, 0, 0));// vermelho
				setForeground(Color.white);
			} else if (entrada1.equalsIgnoreCase("FOLGA")) {
				setBackground(new Color(0, 102, 153));// azul
				setForeground(Color.white);
			} else if (entrada1.equalsIgnoreCase("FÉRIAS")) {
				setBackground(new Color(153, 102, 0));// vermelho
				setForeground(Color.white);
			} else if (entrada1.equalsIgnoreCase("ISENÇÃO DE PONTO")) {
				setBackground(new Color(51, 51, 153));// azul
				setForeground(Color.white);
			} else if (entrada1.equalsIgnoreCase("LICENÇA")) {
				setBackground(new Color(102, 51, 51));// azul
				setForeground(Color.white);
			}else if(entrada1.equalsIgnoreCase("DESCANSO SEMANAL")) {
				setBackground(new Color(0, 102, 0));// azul
				setForeground(Color.white);
			}else if(entrada1.equalsIgnoreCase("FERIADO")) {
				setBackground(new Color(0, 51, 51));// azul
				setForeground(Color.white);
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	public void pesquisar_rps() {
		
	
		
		modeloRps.onRemoveAll();

		String data = entDataRegistrosPontos.getText();

		int dia_semana = -1;
		int total_cts_ativos = 0;

		try {
			LocalDate localDate6 = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			dia_semana = localDate6.getDayOfWeek().getValue();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Data Inválida");
			return;
		}

		GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
		GerenciarBancoRotina gerenciar_rotina = new GerenciarBancoRotina();

		ArrayList<CadastroFuncionario> lista_funcionarios = gerenciar.getfuncionarios();


		int total_falta = 0, total_folga = 0, total_completo = 0, total_isencao = 0, total_licenca = 0,
				total_incompleto = 0, total_ferias = 0, total_descanso = 0, total_feriado = 0;

		GerenciarBancoRegistroPonto gerenciar_rp = new GerenciarBancoRegistroPonto();
		GerenciarBancoFuncionariosContratoTrabalho gerenciar_ct = new GerenciarBancoFuncionariosContratoTrabalho();

		for (CadastroFuncionario cf : lista_funcionarios) {
			
			
			
			CadastroFuncionarioAdmissao ct = new CadastroFuncionarioAdmissao();
			ct = gerenciar_ct.getcontratoAtivoPorFuncionario(cf.getId_funcionario());

			if (ct != null) {
				total_cts_ativos++;

			RegistroPontoDiarioCompleto rp = gerenciar_rp.getDemonstrativoFuncionarioData(cf.getId_funcionario(), data);

			if (rp != null) {

				if (rp.getEntrada1() != null && rp.getEntrada2() != null && rp.getSaida1() != null
						&& rp.getSaida2() != null) {
					total_completo++;
				} else {
					total_incompleto++;
				}

				modeloRps.onAdd(rp);

			}

			else {

				// verificar por evento de folga ou ferias
				GerenciarBancoFuncionariosEventos gerenciar_eventos = new GerenciarBancoFuncionariosEventos();
				ArrayList<CadastroFuncionarioEvento> eventos = gerenciar_eventos
						.getEventosPorColaborador(cf.getId_funcionario());
				
				GerenciarBancoEventoGlobal gerenciar_eventos_global = new GerenciarBancoEventoGlobal();
				ArrayList<EventoGlobal> listaEventosGlobais = gerenciar_eventos_global.getEventosPorData(data);

				boolean tem_folga = false;
				boolean tem_ferias = false;
				boolean tem_isencao = false;
				boolean tem_licenca = false;
				boolean tem_descanso = false;
				boolean tem_feriado = false;

				for (CadastroFuncionarioEvento evt : eventos) {

					if (evt.getTipo_evento() == 0) {
						// folga
						if (evt.getData_folga().equalsIgnoreCase(data)) {
							// tem folga nesse dia

							tem_folga = true;
							break;
						}
					} else if (evt.getTipo_evento() == 3) {
						LocalDate hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						if (hoje.isEqual(dataInicialFerias)) {
							tem_ferias = true;
							break;

						} else if (hoje.isEqual(dataFinalFerias)) {
							tem_ferias = true;
							break;
						} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
							tem_ferias = true;
							break;
						}

					} else if (evt.getTipo_evento() == 4) {
						// isencao de ponto
						LocalDate hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						if (hoje.isEqual(dataInicialFerias)) {
							tem_isencao = true;
							break;

						} else if (hoje.isEqual(dataFinalFerias)) {
							tem_isencao = true;
							break;
						} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
							tem_isencao = true;
							break;
						}
					} else if (evt.getTipo_evento() == 5) {
						// licenca
						LocalDate hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						LocalDate dataInicialFerias = LocalDate.parse(evt.getData_ferias_ida(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						LocalDate dataFinalFerias = LocalDate.parse(evt.getData_ferias_volta(),
								DateTimeFormatter.ofPattern("dd/MM/yyyy"));

						if (hoje.isEqual(dataInicialFerias)) {
							tem_licenca = true;
							break;

						} else if (hoje.isEqual(dataFinalFerias)) {
							tem_licenca = true;
							break;
						} else if (hoje.isAfter(dataInicialFerias) && hoje.isBefore(dataFinalFerias)) {
							tem_licenca = true;
							break;
						}
					}

				}
				
				
				for(EventoGlobal evt: listaEventosGlobais) {
					if(evt.getTipo_evento() == 0) {
						//tem feriado
						tem_feriado = true;
						break;
					}
				}

				RegistroPontoDiarioCompleto avulso = new RegistroPontoDiarioCompleto();

				if(tem_isencao) {
					// tem isencao
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("ISENÇÃO DE PONTO");
					avulso.setSaida1("ISENÇÃO DE PONTO");
					avulso.setEntrada2("ISENÇÃO DE PONTO");
					avulso.setSaida2("ISENÇÃO DE PONTO");
					total_isencao++;
					modeloRps.onAdd(avulso);

				}else {
				
				// verifica a rotina do dia
				CadastroFuncionarioRotinaTrabalho rt = gerenciar_rotina.getRotinaDiaSemana(cf.getId_funcionario(),
						dia_semana);

				if(rt != null) {
				if (rt.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL") || rt.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rt.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL")
						|| rt.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")

				) {
					
					if (rt.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL") && rt.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL")
							&& rt.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL")
							&& rt.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")

					) {
						
						avulso.setData(data);
						avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
						avulso.setEntrada1(rt.getHora_entrada1());
						avulso.setSaida1(rt.getHora_saida1());
						avulso.setEntrada2(rt.getHora_entrada2());
						avulso.setSaida2(rt.getHora_saida2());
						total_descanso++;
					}else {
						avulso.setData(data);
						avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
						
						if(!rt.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL"))
						 avulso.setEntrada1("FALTA");
						else
						 avulso.setEntrada1("DESCANSO SEMANAL");
						
						if(!rt.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL"))
							 avulso.setSaida1("FALTA");
							else
							 avulso.setSaida1("DESCANSO SEMANAL");
						
						
						if(!rt.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL"))
							 avulso.setEntrada2("FALTA");
							else
							 avulso.setEntrada2("DESCANSO SEMANAL");
						
						
						if(!rt.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL"))
							 avulso.setSaida2("FALTA");
							else
							 avulso.setSaida2("DESCANSO SEMANAL");

					
						total_falta++;
					}
					
				
				}else {
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("FALTA");
					avulso.setSaida1("FALTA");
					avulso.setEntrada2("FALTA");
					avulso.setSaida2("FALTA");
					total_falta++;
				}
				}
				else if (!tem_folga && !tem_ferias && !tem_isencao && !tem_licenca && !tem_feriado) {
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("FALTA");
					avulso.setSaida1("FALTA");
					avulso.setEntrada2("FALTA");
					avulso.setSaida2("FALTA");
					total_falta++;

				} else if (tem_folga && !tem_ferias && !tem_feriado) {
					// tem folga
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("FOLGA");
					avulso.setSaida1("FOLGA");
					avulso.setEntrada2("FOLGA");
					avulso.setSaida2("FOLGA");
					total_folga++;

				} else if (!tem_feriado && !tem_folga && tem_ferias) {
					// tem ferias
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("FÉRIAS");
					avulso.setSaida1("FÉRIAS");
					avulso.setEntrada2("FÉRIAS");
					avulso.setSaida2("FÉRIAS");
					total_ferias++;
				} else if (tem_isencao) {
					// tem isencao
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("ISENÇÃO DE PONTO");
					avulso.setSaida1("ISENÇÃO DE PONTO");
					avulso.setEntrada2("ISENÇÃO DE PONTO");
					avulso.setSaida2("ISENÇÃO DE PONTO");
					total_isencao++;
				} else if (tem_licenca) {
					// tem licenca
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("LICENÇA");
					avulso.setSaida1("LICENÇA");
					avulso.setEntrada2("LICENÇA");
					avulso.setSaida2("LICENÇA");
					total_licenca++;
				}else if(tem_feriado) {
					avulso.setData(data);
					avulso.setNome_colaborador(cf.getNome() + " " + cf.getSobrenome());
					avulso.setEntrada1("FERIADO");
					avulso.setSaida1("FERIADO");
					avulso.setEntrada2("FERIADO");
					avulso.setSaida2("FERIADO");
					total_feriado++;
				}
				modeloRps.onAdd(avulso);
				}
			}
		}//finaliza o for
		}

		lblFaltou.setText(total_falta + "");
		lblRegistradoCorretamente.setText(total_completo + "");
		lblNaoCompleto.setText(total_incompleto + "");
		lblFolga.setText(total_folga + "");
		lblFerias.setText(total_ferias + "");
		lblLicencas.setText(total_licenca + "");
		lblIsencaoPonto.setText(total_isencao + "");
		lblDS.setText(total_descanso + "");
		lblFeriado.setText(total_feriado + "");
		lblTotalColaboradores.setText(total_cts_ativos + "");

	}

	public void buscarConexaoPontoRelogio() {
		new Thread() {

			@Override
			public void run() {

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Buscando conexao ao relogio");

				while (true) {
					int status = DadosGlobais.getInstance().getStatus_relogio();

					if (status == 1) {

						lblStatusRelogioPonto.setText("Relógio Conectado!");
						imgRfid.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_online.png")));

					} else {

						System.out.println("erro ao se conectar ao relogio");
						lblStatusRelogioPonto.setText("Relógio Desconectado!");
						imgRfid.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_offline.png")));

					}

				}
			}
		}.start();
	}

}
