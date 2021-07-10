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
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.graficos.GraficoLinhaDupla;
import main.java.gui.TelaFinanceiroLancamento.LancamentoTableModel;
import main.java.gui.TelaFinanceiroLancamento.LancamentosRender;
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
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

public class TelaFinanceiro extends JFrame {

	private JPanel painelPrincipal;
	private TelaFinanceiro isto;
	private TableRowSorter<LancamentoTableModel> sorter;

	private JLabel lblUser, lblDireitos;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private TelaPost telaPost;
	private GerenciarBancoContratos gerenciarAtualizarTarefas;
	private int num_tarefas_nesta_secao = -1;
	private boolean notificando = false;
	private JPanel grafico_despesas, grafico_receitas;
	private ArrayList<Lancamento> lista_lancamentos = new ArrayList<>();
	private LancamentoTableModel modelo_lancamentos = new LancamentoTableModel();
	private JLabel lblSomatoriaValorTotalDespesasAPagar, lblSomatoriaValorTotalReceitasAReceber;
	private JPanel painelGraficoLinha;
	private DefaultCategoryDataset dataset;
	private GraficoLinhaDupla linha = null;

	private ChartPanel chartPanel;

	public TelaFinanceiro(Window window) {
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

		setTitle("Finanças");

		JMenuBar menuBar = new JMenuBar();

		JMenu mnNewMenu = new JMenu("Cadastros");
		mnNewMenu.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/financeiro_icone_24px.png")));
		mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem234567 = new JMenuItem("Instituição Bancária");
		mntmNewMenuItem234567.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_ibs_32px.png")));
		mntmNewMenuItem234567.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem234567.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem234567.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(-1, -1, isto);
				tela.setVisible(true);

			}
		});

		JMenuItem mntmNewMenuItem = new JMenuItem("Cliente/Fornecedor");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente clientes = new TelaCliente(1, 0, isto);
				clientes.setVisible(true);
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/equipe.png")));
		mntmNewMenuItem.setMargin(new Insets(0, 14, 0, 0));
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(mntmNewMenuItem234567);

		JMenuItem mntmNewMenuItem_876 = new JMenuItem("Centro de Custo");
		mntmNewMenuItem_876.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_876.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/centro_custo_24x24.jpg")));
		mntmNewMenuItem_876.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCentroCusto tela = new TelaFinanceiroCentroCusto(-1, -1, isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_876.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_876);

		JMenuItem mntmNewMenuItem_26566 = new JMenuItem("Condição de Pagamento");
		mntmNewMenuItem_26566.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_cp_32px.png")));
		mntmNewMenuItem_26566.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_26566.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(-1, -1, isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_26566.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_26566);

		JMenuItem mntmNewMenuItem_234 = new JMenuItem("Grupo de Contas");
		mntmNewMenuItem_234
				.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_grupo_contas_24px.png")));
		mntmNewMenuItem_234.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_234.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroGrupoConta tela = new TelaFinanceiroGrupoConta(-1, -1, isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_234.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_234);

		JMenuItem mntmNewMenuItem_42345 = new JMenuItem("Contas");
		mntmNewMenuItem_42345
				.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/conta-bancaria_24x24.png")));
		mntmNewMenuItem_42345.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroConta tela = new TelaFinanceiroConta(-1, -1, isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_42345.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_42345.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_42345);

		JMenu mnNewMenu_1 = new JMenu("Lançamentos");
		mnNewMenu_1.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_lancamentos_36px.png")));
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_1);

		JMenuItem lancamentos = new JMenuItem("Lançamentos");
		lancamentos.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/caixa-registradora.png")));
		lancamentos.setMargin(new Insets(0, 10, 0, 0));
		lancamentos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lancamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroLancamento tela = new TelaFinanceiroLancamento(-1, -1, isto);
				tela.setVisible(true);
			}
		});
		mnNewMenu_1.add(lancamentos);

		JMenuItem mntmPagamentos = new JMenuItem("Pagamentos");
		mntmPagamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFinanceiroPagamento tela = new TelaFinanceiroPagamento(-1, -1, isto);
				tela.setVisible(true);

			}

		});

		JMenuItem mntmParcelas = new JMenuItem("Parcelas");
		mntmParcelas.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/parcela.png")));
		mntmParcelas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroParcelas tela = new TelaFinanceiroParcelas(-1, -1, isto);
				tela.setVisible(true);

			}
		});
		mntmParcelas.setMargin(new Insets(0, 10, 0, 0));
		mntmParcelas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmParcelas);
		mntmPagamentos.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/pagamento.png")));
		mntmPagamentos.setMargin(new Insets(0, 10, 0, 0));
		mntmPagamentos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmPagamentos);

		JMenuItem mntmRelatoria = new JMenuItem("Relatoria");
		mntmRelatoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFinanceiroRelatorios tela = new TelaFinanceiroRelatorios(isto);
				tela.setVisible(true);

			}
		});
		mntmRelatoria.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/relatorio.png")));
		mntmRelatoria.setMargin(new Insets(0, 10, 0, 0));
		mntmRelatoria.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmRelatoria);

		JMenuItem mntmRecibosEEmprstimos = new JMenuItem("Recibos e Empréstimos");
		mntmRecibosEEmprstimos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(isto, "Construindo...");

			}
		});
		mntmRecibosEEmprstimos.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/emprestimo.png")));
		mntmRecibosEEmprstimos.setMargin(new Insets(0, 10, 0, 0));
		mntmRecibosEEmprstimos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmRecibosEEmprstimos);

		JMenuItem mntmComprovantes = new JMenuItem("Comprovantes");
		mntmComprovantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(isto, "Construindo...");

			}
		});
		mntmComprovantes.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/comprovante.png")));
		mntmComprovantes.setMargin(new Insets(0, 10, 0, 0));
		mntmComprovantes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu_1.add(mntmComprovantes);
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

		LancamentosRender renderer = new LancamentosRender();

		sorter = new TableRowSorter<LancamentoTableModel>(modelo_lancamentos);

		logar();

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		painelPrincipal.add(panel_5, "cell 0 1 10 1,grow");
		panel_5.setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));

		JPanel painelPaiDespesas = new JPanel();
		panel_5.add(painelPaiDespesas, "cell 0 0");
		painelPaiDespesas.setBackground(Color.WHITE);
		painelPaiDespesas.setLayout(new MigLayout("", "[][grow][grow][grow]", "[grow][grow][grow]"));

		grafico_despesas = new JPanel();
		grafico_despesas.setBorder(new LineBorder(new Color(0, 0, 0)));
		grafico_despesas.setBackground(Color.WHITE);
		painelPaiDespesas.add(grafico_despesas, "cell 0 1 3 1,grow");
		grafico_despesas.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPaiDespesas.add(panel, "cell 3 1,grow");
		panel.setLayout(new MigLayout("", "[grow][]", "[][][][][][][][grow]"));

		JLabel lblNewLabel = new JLabel("Opções de Dados:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblNewLabel, "cell 0 0,alignx center");

		JButton btnAgruparPorCon = new JButton("Conta");
		btnAgruparPorCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						grafico_despesas.removeAll();
						grafico_despesas.add(criarGrafico1(0, 0, 0));
						grafico_despesas.repaint();
						grafico_despesas.updateUI();
					}
				});

			}
		});

		JButton btnNewButton = new JButton("Grupo");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 102));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						grafico_despesas.removeAll();
						grafico_despesas.add(criarGrafico1(0, 1, 0));
						grafico_despesas.repaint();
						grafico_despesas.updateUI();
					}
				});

			}
		});

		JLabel lblAgruparPor = new JLabel("Agrupar por:");
		lblAgruparPor.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblAgruparPor, "cell 0 2");
		panel.add(btnNewButton, "cell 0 3,alignx center");
		btnAgruparPorCon.setForeground(Color.WHITE);
		btnAgruparPorCon.setBackground(new Color(0, 0, 153));
		btnAgruparPorCon.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnAgruparPorCon, "cell 0 4,alignx center");

		JLabel lblSomatorias = new JLabel("Somatória ");
		lblSomatorias.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblSomatorias, "cell 0 5");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, "cell 0 6,grow");
		panel_3.setLayout(new MigLayout("", "[][]", "[]"));

		JLabel lblValorTotal = new JLabel("Valor Total:");
		panel_3.add(lblValorTotal, "cell 0 0");
		lblValorTotal.setFont(new Font("Tahoma", Font.BOLD, 16));

		lblSomatoriaValorTotalDespesasAPagar = new JLabel("R$ 000.000.000,00");
		lblSomatoriaValorTotalDespesasAPagar.setForeground(Color.RED);
		panel_3.add(lblSomatoriaValorTotalDespesasAPagar, "cell 1 0");
		lblSomatoriaValorTotalDespesasAPagar.setFont(new Font("Tahoma", Font.BOLD, 16));

		JPanel painelPaiReceitas = new JPanel();
		panel_5.add(painelPaiReceitas, "cell 1 0");
		painelPaiReceitas.setBackground(Color.WHITE);
		painelPaiReceitas.setLayout(new MigLayout("", "[grow][][]", "[]"));

		grafico_receitas = new JPanel();
		painelPaiReceitas.add(grafico_receitas, "cell 0 0");
		grafico_receitas.setBorder(new LineBorder(new Color(0, 0, 0)));
		grafico_receitas.setBackground(new Color(0, 0, 0));
		grafico_receitas.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		painelPaiReceitas.add(panel_4, "cell 1 0,growy");
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));

		JLabel lblNewLabel_3 = new JLabel("Opções de Dados:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_4.add(lblNewLabel_3, "cell 0 0");

		JButton btnAgruparPorCon_1 = new JButton("Conta");
		btnAgruparPorCon_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {

					public void run() {
						grafico_receitas.removeAll();
						grafico_receitas.add(criarGrafico2(1, 0, 2));
						grafico_receitas.repaint();
						grafico_receitas.updateUI();
					}
				});
			}

		});

		JButton btnNewButton_1 = new JButton("Grupo");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						grafico_receitas.removeAll();
						grafico_receitas.add(criarGrafico2(1, 1, 2));
						grafico_receitas.repaint();
						grafico_receitas.updateUI();
					}
				});

			}

		});

		JLabel lblNewLabel_3_1 = new JLabel("Agrupar por:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_4.add(lblNewLabel_3_1, "cell 0 2");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBackground(new Color(0, 0, 102));
		panel_4.add(btnNewButton_1, "cell 0 3,alignx center");
		btnAgruparPorCon_1.setForeground(Color.WHITE);
		btnAgruparPorCon_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAgruparPorCon_1.setBackground(new Color(0, 0, 153));
		panel_4.add(btnAgruparPorCon_1, "cell 0 4,alignx center");

		JLabel lblSomatorias_1 = new JLabel("Somatória ");
		lblSomatorias_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_4.add(lblSomatorias_1, "cell 0 5");

		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBackground(Color.WHITE);
		panel_4.add(panel_3_1, "cell 0 6,grow");
		panel_3_1.setLayout(new MigLayout("", "[][]", "[]"));

		JLabel lblValorTotal_1 = new JLabel("Valor Total:");
		lblValorTotal_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_3_1.add(lblValorTotal_1, "cell 0 0");

		lblSomatoriaValorTotalReceitasAReceber = new JLabel("R$ 0,00");
		lblSomatoriaValorTotalReceitasAReceber.setForeground(new Color(50, 205, 50));
		lblSomatoriaValorTotalReceitasAReceber.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_3_1.add(lblSomatoriaValorTotalReceitasAReceber, "cell 1 0");

	//	tratamentoAvisos();
		painelGraficoLinha = new JPanel();
		painelGraficoLinha.setBackground(Color.WHITE);
		painelPrincipal.add(painelGraficoLinha, "cell 0 2 5 1,grow");
		painelGraficoLinha.setLayout(new MigLayout("", "[grow]", "[grow]"));

		grafico_despesas.add(criarGrafico1(0, 1, 0));

		grafico_receitas.add(criarGrafico2(1, 1, 2));

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		painelPrincipal.add(panel_6, "cell 5 2 5 1,grow");
		panel_6.setLayout(new MigLayout("", "[]", "[]"));

		// atualizarGrafico();

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

	
	
	public ChartPanel criarGrafico1(int flag_despesa_receita, int flag_conta_grupo_contas, int flag_status) {

		DefaultPieDataset pizza = new DefaultPieDataset();
		BigDecimal valor_total = BigDecimal.ZERO;
		BigDecimal valor_parcial = BigDecimal.ZERO;
		ArrayList<Lancamento> lancamentos = new GerenciarBancoLancamento()
				.buscaLancamentosCompletosGrafico(flag_despesa_receita, flag_conta_grupo_contas, flag_status);
		for (Lancamento lancamento : lancamentos) {

			if (flag_conta_grupo_contas == 0) {
				if (lancamento.getNome_conta() != null) {
					valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
					pizza.setValue(lancamento.getNome_conta(), valor_parcial);
				}

			} else if (flag_conta_grupo_contas == 1) {
				if (lancamento.getNome_grupo_contas() != null) {
					valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());

					pizza.setValue(lancamento.getNome_grupo_contas(), valor_parcial);
				}
			}
			valor_total = valor_total.add(valor_parcial);

		}
		Locale ptBr = new Locale("pt", "BR");

		lblSomatoriaValorTotalDespesasAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));

		JFreeChart grafico = null;
		if (flag_status == 0) {
			grafico = ChartFactory.createPieChart("Lançamentos de Despesas a Pagar", pizza, true, true, true);

		} else if (flag_status == 2) {
			grafico = ChartFactory.createPieChart("Lançamentos de Receitas a Receber", pizza, true, true, true);

		}

		grafico.setBackgroundPaint(Color.WHITE);

		ChartPanel painel = new ChartPanel(grafico);
		painel.setBackground(Color.white);
		painel.setBounds(0, 0, 350, 350);

		PiePlot plotagem = (PiePlot) grafico.getPlot();
		plotagem.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));// define porcentagem no gráfico
		plotagem.setLabelBackgroundPaint(new Color(220, 220, 220));
		plotagem.setBackgroundPaint(Color.WHITE);

		return painel;

	}

	public ChartPanel criarGrafico2(int flag_despesa_receita, int flag_conta_grupo_contas, int flag_status) {

		DefaultPieDataset pizza = new DefaultPieDataset();
		BigDecimal valor_total = BigDecimal.ZERO;
		BigDecimal valor_parcial = BigDecimal.ZERO;

		ArrayList<Lancamento> lancamentos = new GerenciarBancoLancamento()
				.buscaLancamentosCompletosGrafico(flag_despesa_receita, flag_conta_grupo_contas, flag_status);
		for (Lancamento lancamento : lancamentos) {

			if (flag_conta_grupo_contas == 0) {
				if (lancamento.getNome_conta() != null) {
					valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
					pizza.setValue(lancamento.getNome_conta(), valor_parcial);

				}
			} else if (flag_conta_grupo_contas == 1) {
				if (lancamento.getNome_grupo_contas() != null) {
					valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());

					pizza.setValue(lancamento.getNome_grupo_contas(), valor_parcial);
				}
			}
			valor_total = valor_total.add(valor_parcial);

		}

		Locale ptBr = new Locale("pt", "BR");

		lblSomatoriaValorTotalReceitasAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));

		JFreeChart grafico = null;
		if (flag_status == 0) {
			grafico = ChartFactory.createPieChart("Lançamentos de Despesas a Pagar", pizza, true, true, true);

		} else if (flag_status == 2) {
			grafico = ChartFactory.createPieChart("Lançamentos de Receitas a Receber", pizza, true, true, true);

		}

		grafico.setBackgroundPaint(Color.WHITE);

		ChartPanel painel = new ChartPanel(grafico);
		painel.setBackground(Color.white);
		painel.setBounds(0, 0, 350, 350);

		PiePlot plotagem = (PiePlot) grafico.getPlot();
		plotagem.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));// define porcentagem no gráfico
		plotagem.setLabelBackgroundPaint(new Color(220, 220, 220));
		plotagem.setBackgroundPaint(Color.WHITE);

		return painel;

	}

	public void tratamentoAvisos() {

		lista_lancamentos.clear();
		modelo_lancamentos.onRemoveAll();
		GerenciarBancoLancamento gerenciar_lancamentos = new GerenciarBancoLancamento();
		ArrayList<Lancamento> lancamentos = gerenciar_lancamentos.buscaLancamentosCompletos();

		for (Lancamento lancamento : lancamentos) {
			// lancamento alta prioridade
			lista_lancamentos.add(lancamento);
			modelo_lancamentos.onAdd(lancamento);

		}

		// filtroRapidoDespesaAtrazo(7);

	}

	public class LancamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int tipo_lancamento = 1;
		private final int prioridade = 2;
		private final int valor_a_pagar = 3;
		private final int data_vencimento = 4;
		private final int status = 5;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Tipo", "Prioridade", "Valor a Pagar/Receber",
				"Data Próximo Vencimento", "Status" };
		private final ArrayList<Lancamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																		// nfs

		public LancamentoTableModel() {

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

			case tipo_lancamento:
				return String.class;
			case prioridade:
				return String.class;
			case valor_a_pagar:
				return String.class;
			case data_vencimento:
				return Date.class;
			case status:
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
			Lancamento dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getId_lancamento();
			case tipo_lancamento: {
				if (dado.getTipo_lancamento() == 0) {
					return "DESPESAS";
				} else if (dado.getTipo_lancamento() == 1) {
					return "RECEITAS";
				}
			}
			case prioridade: {
				int i_prioridade = dado.getPrioridade();
				if (i_prioridade == 0) {
					return "Alta Prioridade - Ainda esta semana";
				} else if (i_prioridade == 1) {
					return "Média Prioridade - Em menos de 15 dias";
				} else if (i_prioridade == 2) {
					return "Prioridade Leve - Ainda este mês";
				} else if (i_prioridade == 3) {
					return "Baixa Prioridade - Ainda este ano";
				}

			}

			case valor_a_pagar: {
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(dado.getValor().subtract(dado.getValor_ja_pago()));
				return valorString;

			}
			case data_vencimento: {
				Date data_menor;
				try {
					if (dado.getData_vencimento() != null) {
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						data_menor = formato.parse(dado.getData_vencimento());
						return data_menor;
					} else
						return "";

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "";
				}

			}

			case status: {
				int status = dado.getStatus();
				if (status == 0) {
					return ("A Pagar");

				} else if (status == 1) {
					return ("Pago");

				} else if (status == 2) {
					return ("A Receber");

				} else if (status == 3) {
					return ("Recebido");

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
			Lancamento ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public Lancamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(Lancamento dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(Lancamento dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<Lancamento> dadosIn) {
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
		public void onRemove(Lancamento dado) {
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

		public Lancamento onGet(int row) {
			return dados.get(row);
		}
	}

	
	

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	class LancamentosRender extends DefaultTableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			((JLabel) renderer).setOpaque(true);
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			if (value instanceof Date) {
				value = f.format(value);
			}

			int status = -1;
			String s_status = (String) table.getValueAt(row, 5);
			if (s_status.equalsIgnoreCase("A Pagar")) {
				status = 0;
			} else if (s_status.equalsIgnoreCase("Pago")) {
				status = 1;
			} else if (s_status.equalsIgnoreCase("A Receber")) {
				status = 2;
			} else if (s_status.equalsIgnoreCase("Recebido")) {
				status = 3;
			}

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom

			} else {
				if (status == 0) {
					// return ("A Pagar");
					renderer.setBackground(Color.red);
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Arial", Font.BOLD, 16));

				} else if (status == 1) {
					// return ("Pago");
					renderer.setBackground(Color.orange);
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Arial", Font.BOLD, 16));
				} else if (status == 2) {
					// return ("A Receber");
					renderer.setBackground(Color.yellow);
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Arial", Font.BOLD, 16));
				} else if (status == 3) {
					// return ("Recebido");
					renderer.setBackground(new Color(0, 51, 0));
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Arial", Font.BOLD, 16));
				}

			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			// return renderer;
		}
	}

	public void atualizarGrafico() {

		painelGraficoLinha.removeAll();
		Map<Integer, Double> lista_lancamentos = new GerenciarBancoLancamento().busca_lancamentos_grafico_linha(0, -1);
		lista_lancamentos = new TreeMap<>(lista_lancamentos);

		dataset = new DefaultCategoryDataset();
		for (Map.Entry<Integer, Double> pair : lista_lancamentos.entrySet()) {

			dataset.addValue(pair.getValue(), "", NomeDoMes(pair.getKey(), 0));

		}

		
		linha = new GraficoLinhaDupla();
		linha.setDataset(dataset);
		chartPanel = linha.getGraficoLinha(painelGraficoLinha.getWidth(), painelGraficoLinha.getHeight(), "Mês",
				"Despesas x Receitas", "Valor em Reais");
		chartPanel.setBackground(Color.white);
		painelGraficoLinha.add(chartPanel);
	}

	public static String NomeDoMes(int i, int tipo) {
		String mes[] = { "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro",
				"outubro", "novembro", "dezembro" };
		if (tipo == 0)
			return (mes[i - 1]);
		else
			return (mes[i - 1].substring(0, 3));
	}
}
