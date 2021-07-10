package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import keeptoo.KGradientPanel;
import main.java.cadastros.CadastroTransgenia;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.DreSimples;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.classesExtras.ComboBoxPersonalizadoCentroCusto;
import main.java.classesExtras.ComboBoxPersonalizadoClassificador;
import main.java.classesExtras.ComboBoxRenderPersonalizadoCentroCusto;
import main.java.classesExtras.ComboBoxRenderPersonalizadoTransgenia;
import main.java.classesExtras.RenderizadorDreSimples;
import main.java.classesExtras.RenderizadorExtrato;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoTransgenia;
import main.java.gui_internal.TelaFinanceiroMostrarReceitasDespesas;
import main.java.outros.GetData;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class TelaFinanceiroRelatorios extends JFrame {

	private JPanel painelRelatoriosDre = new JPanel();
	private JTabbedPane painelPrincipal;

	private JPanel painelRelatoriosDreRegimeCompetencia = new JPanel();
	private ArrayList<DreSimples> lista_dre = new ArrayList<>();
	private JLabel lblSaldoInicialTotal, lblReceitasTotal,lblDespesasTotal,lblValorTotal, lblLucroTotal, lblLucratividadeTotal;
	private JLabel lblSaldoInicialTotalRc, lblReceitasTotalRc,lblDespesasTotalRc,lblValorTotalRc, lblLucroTotalRc, lblLucratividadeTotalRc;
	private JLabel lblMes1Regp , lblSaldoInicialTotalRegp ,lblReceitasTotalRegp ,lblDespesasTotalRegp ,lblValorTotalRegp ,
	lblLucroTotalRegp ,lblLucratividadeTotalRegp ;
	
	private TelaFinanceiroRelatorios isto;
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Centro de Custo:");
	private JComboBox cbCentroCusto;
	private final JLabel lblAnoFiscal = new JLabel("Ano Fiscal:");
	private final JTextField entAnoFiscal = new JTextField();
	private final JScrollPane scrollDreSimples;
	private DefaultListModel<DreSimples> listModelGlobal;
	private RenderizadorDreSimples render;
	
	private DefaultListModel<DreSimples> listModelGlobalRc;
	private RenderizadorDreSimples renderRc;
	
	private DefaultListModel<DreSimples> listModelGlobalRp;
	private RenderizadorDreSimples renderRp;
	
	private JComboBox cbCentroCustoRc;
	private ComboBoxPersonalizadoCentroCusto modelCentrosCustos = new ComboBoxPersonalizadoCentroCusto();
	private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizado = new ComboBoxRenderPersonalizadoCentroCusto();
	
	
	private ComboBoxPersonalizadoCentroCusto modelCentrosCustosRp = new ComboBoxPersonalizadoCentroCusto();
	private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizadoRp = new ComboBoxRenderPersonalizadoCentroCusto();
	
	
	private JTextField entAnoFiscalRp;
	private JComboBox cbCentroCustoRp;
	
	private ComboBoxPersonalizadoCentroCusto modelCentrosCustosRc = new ComboBoxPersonalizadoCentroCusto();
	private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizadoRc = new ComboBoxRenderPersonalizadoCentroCusto();
	
	
	private final JButton btnNewButton = new JButton("Atualizar");
	private JTextField entAnoFiscalRc;

	public TelaFinanceiroRelatorios(Window janela_pai) {
		entAnoFiscal.setColumns(10);

		isto = this;

		setResizable(true);

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

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Relatorios Financeiros");

		UIManager.put("TabbedPane.unselectedForeground", Color.white);
		UIManager.put("TabbedPane.selectedBackground", Color.white);
		painelPrincipal = new JTabbedPane();

		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		painelPrincipal.setTabPlacement(2);

		// adiciona novos paines e suas abas

		painelPrincipal.addTab("Relatórios DRE's", painelRelatoriosDre);

		painelRelatoriosDre.setLayout(new BorderLayout(0, 0));

		JTabbedPane abasDre = new JTabbedPane(JTabbedPane.TOP);
		painelRelatoriosDre.add(abasDre);
		painelRelatoriosDreRegimeCompetencia.setBackground(Color.WHITE);

		abasDre.addTab("Relatório DRE Regime de Competência", painelRelatoriosDreRegimeCompetencia);
		painelRelatoriosDreRegimeCompetencia.setLayout(new MigLayout("", "[grow]", "[][grow][grow][grow]"));
		panel.setBackground(Color.WHITE);

		painelRelatoriosDreRegimeCompetencia.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][][][][]", "[]"));
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

		panel.add(lblNewLabel, "cell 0 0");
		cbCentroCusto = new JComboBox();

		cbCentroCusto.setModel(modelCentrosCustos);
		cbCentroCusto.setRenderer(cbCentroCustoRenderPersonalizado);

		panel.add(cbCentroCusto, "cell 1 0");
		lblAnoFiscal.setFont(new Font("SansSerif", Font.BOLD, 16));

		panel.add(lblAnoFiscal, "cell 2 0,alignx trailing");

		panel.add(entAnoFiscal, "cell 3 0,growx");
		entAnoFiscal.setText(Integer.toString(new GetData().getAnoAtual()));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarDre();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton.setBackground(new Color(0, 0, 102));

		panel.add(btnNewButton, "cell 4 0");

		

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new MigLayout("", "[grow]", "[:30px:30px][grow][]"));

		JList listaRcompetencia = new JList<>();
		panel_1.add(listaRcompetencia, "cell 0 1,growy");
		listaRcompetencia.setOpaque(false);
		listaRcompetencia.setBackground(Color.WHITE);

		listModelGlobal = new DefaultListModel<DreSimples>();
		render = new RenderizadorDreSimples();

		listaRcompetencia.setModel(listModelGlobal);
		listaRcompetencia.setCellRenderer(render);

		 MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 2) {
		             
		             int index = listaRcompetencia.locationToIndex(e.getPoint());
		             if (index >= 0) {
		            	 DreSimples o = (DreSimples) listaRcompetencia.getModel().getElementAt(index);
		               int mes = o.getMes();
		               int ano = o.getAno();
		       		CentroCusto cc = (CentroCusto) modelCentrosCustos.getSelectedItem();

		       	 TelaFinanceiroMostrarReceitasDespesas tela;
		       		if(cc != null)
			               tela = new TelaFinanceiroMostrarReceitasDespesas(0, cc.getId_centro_custo(), mes,ano,isto);
		       		else	
		       			tela = new TelaFinanceiroMostrarReceitasDespesas(0, 0, mes,ano,isto);

		               tela.setVisible(true);
		             }
		          }
		     }
		 };
		 listaRcompetencia.addMouseListener(mouseListener);

		
		scrollDreSimples = new JScrollPane(panel_1);
		painelRelatoriosDreRegimeCompetencia.add(scrollDreSimples, "cell 0 1 1 3,grow");

		KGradientPanel painelCabecalho = new KGradientPanel();
		panel_1.add(painelCabecalho, "cell 0 0,growx,aligny top");
		painelCabecalho.kEndColor = Color.WHITE;
		painelCabecalho.kStartColor = new Color(255, 255, 255);
		painelCabecalho.setBackground(new Color(153, 153, 102));
		painelCabecalho.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		painelCabecalho.setLayout(new MigLayout("",
				"[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]",
				"[:24px:24px]"));

		JLabel lblMes = new JLabel("     MÊS     ", SwingConstants.CENTER);
		lblMes.setOpaque(true);
		lblMes.setBackground(new Color(51, 51, 0));
		lblMes.setForeground(Color.WHITE);
		lblMes.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelCabecalho.add(lblMes, "cell 0 0,grow");

		JLabel lblSaldoInicial = new JLabel("SALDO INICIAL", SwingConstants.CENTER);
		lblSaldoInicial.setOpaque(true);
		lblSaldoInicial.setForeground(Color.WHITE);
		lblSaldoInicial.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSaldoInicial.setBackground(new Color(255, 153, 0));
		painelCabecalho.add(lblSaldoInicial, "cell 1 0,grow");

		JLabel lblReceitas = new JLabel("RECEITAS", SwingConstants.CENTER);
		lblReceitas.setOpaque(true);
		lblReceitas.setForeground(Color.WHITE);
		lblReceitas.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblReceitas.setBackground(new Color(0, 51, 0));
		painelCabecalho.add(lblReceitas, "cell 2 0,grow");

		JLabel lblDespesas = new JLabel("DESPESAS", SwingConstants.CENTER);
		lblDespesas.setOpaque(true);
		lblDespesas.setForeground(Color.WHITE);
		lblDespesas.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDespesas.setBackground(new Color(153, 51, 0));
		painelCabecalho.add(lblDespesas, "cell 3 0,grow");

		JLabel lblTotal = new JLabel("TOTAL", SwingConstants.CENTER);
		lblTotal.setOpaque(true);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotal.setBackground(new Color(0, 102, 204));
		painelCabecalho.add(lblTotal, "cell 4 0,grow");

		JLabel lblLucro = new JLabel("LUCRO", SwingConstants.CENTER);
		lblLucro.setOpaque(true);
		lblLucro.setForeground(Color.WHITE);
		lblLucro.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucro.setBackground(new Color(51, 153, 0));
		painelCabecalho.add(lblLucro, "cell 5 0,grow");

		JLabel lblLucratividade = new JLabel("LUCRATIVIDADE", SwingConstants.CENTER);
		lblLucratividade.setOpaque(true);
		lblLucratividade.setForeground(Color.WHITE);
		lblLucratividade.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucratividade.setBackground(new Color(0, 0, 102));
		painelCabecalho.add(lblLucratividade, "cell 6 0,grow");
		
		KGradientPanel painelRodape = new KGradientPanel();
		painelRodape.kEndColor = Color.WHITE;
		painelRodape.kStartColor = new Color(255, 255, 255);
		painelRodape.setBackground(new Color(153, 153, 102));
		painelRodape.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		painelRodape.setLayout(new MigLayout("",
				"[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]",
				"[:24px:24px]"));

		JLabel lblMes1 = new JLabel("TOTAIS:", SwingConstants.RIGHT);
		lblMes1.setOpaque(true);
		lblMes1.setBackground(new Color(51, 51, 0));
		lblMes1.setForeground(Color.WHITE);
		lblMes1.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelRodape.add(lblMes1, "cell 0 0,grow");

		
		 lblSaldoInicialTotal = new JLabel("SALDO INICIAL", SwingConstants.CENTER);
		lblSaldoInicialTotal.setOpaque(true);
		lblSaldoInicialTotal.setForeground(Color.WHITE);
		lblSaldoInicialTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSaldoInicialTotal.setBackground(new Color(255, 153, 0));
		painelRodape.add(lblSaldoInicialTotal, "cell 1 0,grow");

		 lblReceitasTotal = new JLabel("RECEITAS", SwingConstants.CENTER);
		lblReceitasTotal.setOpaque(true);
		lblReceitasTotal.setForeground(Color.WHITE);
		lblReceitasTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblReceitasTotal.setBackground(new Color(0, 51, 0));
		painelRodape.add(lblReceitasTotal, "cell 2 0,grow");

		 lblDespesasTotal = new JLabel("DESPESAS", SwingConstants.CENTER);
		lblDespesasTotal.setOpaque(true);
		lblDespesasTotal.setForeground(Color.WHITE);
		lblDespesasTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDespesasTotal.setBackground(new Color(153, 51, 0));
		painelRodape.add(lblDespesasTotal, "cell 3 0,grow");

		 lblValorTotal = new JLabel("TOTAL", SwingConstants.CENTER);
		lblValorTotal.setOpaque(true);
		lblValorTotal.setForeground(Color.WHITE);
		lblValorTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblValorTotal.setBackground(new Color(0, 102, 204));
		painelRodape.add(lblValorTotal, "cell 4 0,grow");

		 lblLucroTotal = new JLabel("LUCRO", SwingConstants.CENTER);
		lblLucroTotal.setOpaque(true);
		lblLucroTotal.setForeground(Color.WHITE);
		lblLucroTotal.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucroTotal.setBackground(new Color(51, 153, 0));
		painelRodape.add(lblLucroTotal, "cell 5 0,grow");

		 lblLucratividadeTotal = new JLabel("LUCRATIVIDADE", SwingConstants.CENTER);
		lblLucratividadeTotal.setOpaque(true);
		lblLucratividadeTotal.setForeground(Color.WHITE);
		lblLucratividadeTotal.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucratividadeTotal.setBackground(new Color(0, 0, 102));
		painelRodape.add(lblLucratividadeTotal, "cell 6 0,grow");
		
		panel_1.add(painelRodape, "cell 0 2,grow");
		
		JPanel painelRelatoriosDreRegimeCaixa = new JPanel();
		abasDre.addTab("Relatório DRE Regime de Caixa", null, painelRelatoriosDreRegimeCaixa, null);
		painelRelatoriosDreRegimeCaixa.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		painelRelatoriosDreRegimeCaixa.add(panel_2, "cell 0 0,grow");
		panel_2.setLayout(new MigLayout("", "[][][][][]", "[]"));
		
		JLabel lblNewLabel_1 = new JLabel("Centro de Custo:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblNewLabel_1, "cell 0 0,alignx trailing");
		
		 cbCentroCustoRc = new JComboBox();
		panel_2.add(cbCentroCustoRc, "flowx,cell 1 0,growx");
		cbCentroCustoRc.setModel(modelCentrosCustosRc);
		cbCentroCustoRc.setRenderer(cbCentroCustoRenderPersonalizadoRc);

		JLabel lblAnoFiscal_1 = new JLabel("Ano Fiscal:");
		lblAnoFiscal_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2.add(lblAnoFiscal_1, "cell 2 0,alignx trailing");
		
		entAnoFiscalRc = new JTextField();
		entAnoFiscalRc.setText(Integer.toString(new GetData().getAnoAtual()));
		entAnoFiscalRc.setColumns(10);
		panel_2.add(entAnoFiscalRc, "cell 3 0,growx");
		
		JButton btnNewButton_1 = new JButton("Atualizar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarDreRc();
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton_1.setBackground(new Color(0, 0, 102));
		panel_2.add(btnNewButton_1, "cell 4 0");
		
		
		JPanel panelPaiRc = new JPanel();
		panelPaiRc.setBackground(Color.WHITE);
		panelPaiRc.setLayout(new MigLayout("", "[grow]", "[][grow][]"));


		JList listaRc = new JList<>();
		panelPaiRc.add(listaRc, "cell 0 1,growy");
		listaRc.setOpaque(false);
		listaRc.setBackground(Color.WHITE);

		 MouseListener mouseListenerRc = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 2) {
		             
		             int index = listaRc.locationToIndex(e.getPoint());
		             if (index >= 0) {
		            	 DreSimples o = (DreSimples) listaRc.getModel().getElementAt(index);
		               int mes = o.getMes();
		               int ano = o.getAno();
			       		CentroCusto cc = (CentroCusto) modelCentrosCustosRc.getSelectedItem();

			       		TelaFinanceiroMostrarReceitasDespesas tela;
			       		if(cc != null)
			                tela = new TelaFinanceiroMostrarReceitasDespesas(1, cc.getId_centro_custo(), mes, ano,isto);

			       		else	
			                tela = new TelaFinanceiroMostrarReceitasDespesas(1, 0, mes, ano,isto);

		               tela.setVisible(true);
		             }
		          }
		     }
		 };
		
		 listaRc.addMouseListener(mouseListenerRc);
		
		listModelGlobalRc = new DefaultListModel<DreSimples>();
		renderRc = new RenderizadorDreSimples();

		listaRc.setModel(listModelGlobalRc);
		listaRc.setCellRenderer(renderRc);
		
		JScrollPane scrollDreSimplesRc = new JScrollPane(panelPaiRc);
		scrollDreSimplesRc.getViewport().setBackground(Color.white);
		painelRelatoriosDreRegimeCaixa.add(scrollDreSimplesRc, "cell 0 1,grow");
		
		KGradientPanel painelCabecalhoRc = new KGradientPanel();
		panelPaiRc.add(painelCabecalhoRc, "cell 0 0,growx,aligny top");
		painelCabecalhoRc.kEndColor = Color.WHITE;
		painelCabecalhoRc.kStartColor = new Color(255, 255, 255);
		painelCabecalhoRc.setBackground(new Color(153, 153, 102));
		painelCabecalhoRc.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		painelCabecalhoRc.setLayout(new MigLayout("",
				"[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]",
				"[:24px:24px]"));
	



		JLabel lblMesRc = new JLabel("     MÊS     ", SwingConstants.CENTER);
		lblMesRc.setOpaque(true);
		lblMesRc.setBackground(new Color(51, 51, 0));
		lblMesRc.setForeground(Color.WHITE);
		lblMesRc.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelCabecalhoRc.add(lblMesRc, "cell 0 0,grow");

		JLabel lblSaldoInicialRc = new JLabel("SALDO INICIAL", SwingConstants.CENTER);
		lblSaldoInicialRc.setOpaque(true);
		lblSaldoInicialRc.setForeground(Color.WHITE);
		lblSaldoInicialRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSaldoInicialRc.setBackground(new Color(255, 153, 0));
		painelCabecalhoRc.add(lblSaldoInicialRc, "cell 1 0,grow");

		JLabel lblReceitasRc = new JLabel("RECEITAS", SwingConstants.CENTER);
		lblReceitasRc.setOpaque(true);
		lblReceitasRc.setForeground(Color.WHITE);
		lblReceitasRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblReceitasRc.setBackground(new Color(0, 51, 0));
		painelCabecalhoRc.add(lblReceitasRc, "cell 2 0,grow");

		JLabel lblDespesasRc = new JLabel("DESPESAS", SwingConstants.CENTER);
		lblDespesasRc.setOpaque(true);
		lblDespesasRc.setForeground(Color.WHITE);
		lblDespesasRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDespesasRc.setBackground(new Color(153, 51, 0));
		painelCabecalhoRc.add(lblDespesasRc, "cell 3 0,grow");

		JLabel lblTotalRc = new JLabel("TOTAL", SwingConstants.CENTER);
		lblTotalRc.setOpaque(true);
		lblTotalRc.setForeground(Color.WHITE);
		lblTotalRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotalRc.setBackground(new Color(0, 102, 204));
		painelCabecalhoRc.add(lblTotalRc, "cell 4 0,grow");

		JLabel lblLucroRc = new JLabel("LUCRO", SwingConstants.CENTER);
		lblLucroRc.setOpaque(true);
		lblLucroRc.setForeground(Color.WHITE);
		lblLucroRc.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucroRc.setBackground(new Color(51, 153, 0));
		painelCabecalhoRc.add(lblLucroRc, "cell 5 0,grow");

		JLabel lblLucratividadeRc = new JLabel("LUCRATIVIDADE", SwingConstants.CENTER);
		lblLucratividadeRc.setOpaque(true);
		lblLucratividadeRc.setForeground(Color.WHITE);
		lblLucratividadeRc.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucratividadeRc.setBackground(new Color(0, 0, 102));
		painelCabecalhoRc.add(lblLucratividadeRc, "cell 6 0,grow");
		
		KGradientPanel painelRodapeRc = new KGradientPanel();
		painelRodapeRc.kEndColor = Color.WHITE;
		painelRodapeRc.kStartColor = new Color(255, 255, 255);
		painelRodapeRc.setBackground(new Color(153, 153, 102));
		painelRodapeRc.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		painelRodapeRc.setLayout(new MigLayout("",
				"[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]",
				"[:24px:24px]"));

		JLabel lblMes1Rc = new JLabel("TOTAIS:", SwingConstants.RIGHT);
		lblMes1Rc.setOpaque(true);
		lblMes1Rc.setBackground(new Color(51, 51, 0));
		lblMes1Rc.setForeground(Color.WHITE);
		lblMes1Rc.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelRodapeRc.add(lblMes1Rc, "cell 0 0,grow");

		
		 lblSaldoInicialTotalRc = new JLabel("SALDO INICIAL", SwingConstants.CENTER);
		lblSaldoInicialTotalRc.setOpaque(true);
		lblSaldoInicialTotalRc.setForeground(Color.WHITE);
		lblSaldoInicialTotalRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSaldoInicialTotalRc.setBackground(new Color(255, 153, 0));
		painelRodapeRc.add(lblSaldoInicialTotalRc, "cell 1 0,grow");

		 lblReceitasTotalRc = new JLabel("RECEITAS", SwingConstants.CENTER);
		lblReceitasTotalRc.setOpaque(true);
		lblReceitasTotalRc.setForeground(Color.WHITE);
		lblReceitasTotalRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblReceitasTotalRc.setBackground(new Color(0, 51, 0));
		painelRodapeRc.add(lblReceitasTotalRc, "cell 2 0,grow");

		 lblDespesasTotalRc = new JLabel("DESPESAS", SwingConstants.CENTER);
		lblDespesasTotalRc.setOpaque(true);
		lblDespesasTotalRc.setForeground(Color.WHITE);
		lblDespesasTotalRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDespesasTotalRc.setBackground(new Color(153, 51, 0));
		painelRodapeRc.add(lblDespesasTotalRc, "cell 3 0,grow");

		 lblValorTotalRc = new JLabel("TOTAL", SwingConstants.CENTER);
		lblValorTotalRc.setOpaque(true);
		lblValorTotalRc.setForeground(Color.WHITE);
		lblValorTotalRc.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblValorTotalRc.setBackground(new Color(0, 102, 204));
		painelRodapeRc.add(lblValorTotalRc, "cell 4 0,grow");

		 lblLucroTotalRc = new JLabel("LUCRO", SwingConstants.CENTER);
		lblLucroTotalRc.setOpaque(true);
		lblLucroTotalRc.setForeground(Color.WHITE);
		lblLucroTotalRc.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucroTotalRc.setBackground(new Color(51, 153, 0));
		painelRodapeRc.add(lblLucroTotalRc, "cell 5 0,grow");

		 lblLucratividadeTotalRc = new JLabel("LUCRATIVIDADE", SwingConstants.CENTER);
		lblLucratividadeTotalRc.setOpaque(true);
		lblLucratividadeTotalRc.setForeground(Color.WHITE);
		lblLucratividadeTotalRc.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucratividadeTotalRc.setBackground(new Color(0, 0, 102));
		painelRodapeRc.add(lblLucratividadeTotalRc, "cell 6 0,grow");
		
		panelPaiRc.add(painelRodapeRc, "cell 0 2,grow");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		abasDre.addTab("Relatório DRE Regime de Parcelas", null, panel_3, null);
		panel_3.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(Color.WHITE);
		panel_3.add(panel_2_1, "cell 0 0,grow");
		panel_2_1.setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel lblNewLabel_11 = new JLabel("Centro de Custo:");
		lblNewLabel_11.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2_1.add(lblNewLabel_11, "cell 0 0,alignx trailing");
		
		
		
		
		 cbCentroCustoRp = new JComboBox();
		 panel_2_1.add(cbCentroCustoRp, "flowx,cell 1 0,growx");
		 cbCentroCustoRp.setModel(modelCentrosCustosRp);
		 cbCentroCustoRp.setRenderer(cbCentroCustoRenderPersonalizadoRp);

		JLabel lblAnoFiscal_12 = new JLabel("Ano Fiscal:");
		lblAnoFiscal_12.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_2_1.add(lblAnoFiscal_12, "cell 2 0,alignx trailing");
		
		entAnoFiscalRp = new JTextField();
		entAnoFiscalRp.setText(Integer.toString(new GetData().getAnoAtual()));
		entAnoFiscalRp.setColumns(10);
		panel_2_1.add(entAnoFiscalRp, "cell 3 0,growx");
		
		JButton btnNewButton_12 = new JButton("Atualizar");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarDreRp();
			}
		});
		btnNewButton_12.setForeground(Color.WHITE);
		btnNewButton_12.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton_12.setBackground(new Color(0, 0, 102));
		panel_2_1.add(btnNewButton_12, "cell 4 0");
		
		
		JPanel painelPaiRp = new JPanel();
		painelPaiRp.setBackground(Color.WHITE);
		
		JScrollPane scrollPaneRp = new JScrollPane(painelPaiRp);
		scrollPaneRp.getViewport().setBackground(Color.white);
		panel_3.add(scrollPaneRp, "cell 0 1,grow");
		painelPaiRp.setLayout(new MigLayout("", "[][1px][1438px]", "[][grow][]"));
		
		KGradientPanel painelCabecalhoRp = new KGradientPanel();
		painelPaiRp.add(painelCabecalhoRp, "cell 0 0 3 1,grow");
		painelCabecalhoRp.kEndColor = Color.WHITE;
		painelCabecalhoRp.kStartColor = new Color(255, 255, 255);
		painelCabecalhoRp.setBackground(new Color(153, 153, 102));
		painelCabecalhoRp.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		painelCabecalhoRp.setLayout(new MigLayout("",
				"[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]",
				"[:24px:24px]"));

		JLabel lblMesRp = new JLabel("     MÊS     ", SwingConstants.CENTER);
		lblMesRp.setOpaque(true);
		lblMesRp.setBackground(new Color(51, 51, 0));
		lblMesRp.setForeground(Color.WHITE);
		lblMesRp.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelCabecalhoRp.add(lblMesRp, "cell 0 0,grow");

		JLabel lblSaldoInicialRp = new JLabel("SALDO INICIAL", SwingConstants.CENTER);
		lblSaldoInicialRp.setOpaque(true);
		lblSaldoInicialRp.setForeground(Color.WHITE);
		lblSaldoInicialRp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSaldoInicialRp.setBackground(new Color(255, 153, 0));
		painelCabecalhoRp.add(lblSaldoInicialRp, "cell 1 0,grow");

		JLabel lblReceitasRp = new JLabel("RECEITAS", SwingConstants.CENTER);
		lblReceitasRp.setOpaque(true);
		lblReceitasRp.setForeground(Color.WHITE);
		lblReceitasRp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblReceitasRp.setBackground(new Color(0, 51, 0));
		painelCabecalhoRp.add(lblReceitasRp, "cell 2 0,grow");

		JLabel lblDespesasRp = new JLabel("DESPESAS", SwingConstants.CENTER);
		lblDespesasRp.setOpaque(true);
		lblDespesasRp.setForeground(Color.WHITE);
		lblDespesasRp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDespesasRp.setBackground(new Color(153, 51, 0));
		painelCabecalhoRp.add(lblDespesasRp, "cell 3 0,grow");

		JLabel lblTotalRp = new JLabel("TOTAL", SwingConstants.CENTER);
		lblTotalRp.setOpaque(true);
		lblTotalRp.setForeground(Color.WHITE);
		lblTotalRp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTotalRp.setBackground(new Color(0, 102, 204));
		painelCabecalhoRp.add(lblTotalRp, "cell 4 0,grow");

		JLabel lblLucroRp = new JLabel("LUCRO", SwingConstants.CENTER);
		lblLucroRp.setOpaque(true);
		lblLucroRp.setForeground(Color.WHITE);
		lblLucroRp.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucroRp.setBackground(new Color(51, 153, 0));
		painelCabecalhoRp.add(lblLucroRp, "cell 5 0,grow");

		JLabel lblLucratividadeRp = new JLabel("LUCRATIVIDADE", SwingConstants.CENTER);
		lblLucratividadeRp.setOpaque(true);
		lblLucratividadeRp.setForeground(Color.WHITE);
		lblLucratividadeRp.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucratividadeRp.setBackground(new Color(0, 0, 102));
		painelCabecalhoRp.add(lblLucratividadeRp, "cell 6 0,grow");


		JList listaRp = new JList<>();
		listaRp.setOpaque(false);
		listaRp.setBackground(Color.WHITE);
		painelPaiRp.add(listaRp, "cell 0 1 3 1,grow");

		 MouseListener mouseListenerRp = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 2) {
		             
		           
		          }
		     }
		 };
		
		 listaRp.addMouseListener(mouseListenerRp);
		
		listModelGlobalRp = new DefaultListModel<DreSimples>();
		renderRp = new RenderizadorDreSimples();

		listaRp.setModel(listModelGlobalRp);
		listaRp.setCellRenderer(renderRp);
		
		
		KGradientPanel painelRodapeRp = new KGradientPanel();
		painelRodapeRp.kEndColor = Color.WHITE;
		painelRodapeRp.kStartColor = new Color(255, 255, 255);
		painelRodapeRp.setBackground(new Color(153, 153, 102));
		painelRodapeRp.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		painelRodapeRp.setLayout(new MigLayout("",
				"[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]",
				"[:24px:24px]"));

		JLabel lblMes1Regp = new JLabel("TOTAIS:", SwingConstants.RIGHT);
		lblMes1Regp.setOpaque(true);
		lblMes1Regp.setBackground(new Color(51, 51, 0));
		lblMes1Regp.setForeground(Color.WHITE);
		lblMes1Regp.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelRodapeRp.add(lblMes1Regp, "cell 0 0,grow");

		
		 lblSaldoInicialTotalRegp = new JLabel("SALDO INICIAL", SwingConstants.CENTER);
		lblSaldoInicialTotalRegp.setOpaque(true);
		lblSaldoInicialTotalRegp.setForeground(Color.WHITE);
		lblSaldoInicialTotalRegp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblSaldoInicialTotalRegp.setBackground(new Color(255, 153, 0));
		painelRodapeRp.add(lblSaldoInicialTotalRegp, "cell 1 0,grow");

		 lblReceitasTotalRegp = new JLabel("RECEITAS", SwingConstants.CENTER);
		lblReceitasTotalRegp.setOpaque(true);
		lblReceitasTotalRegp.setForeground(Color.WHITE);
		lblReceitasTotalRegp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblReceitasTotalRegp.setBackground(new Color(0, 51, 0));
		painelRodapeRp.add(lblReceitasTotalRegp, "cell 2 0,grow");

		 lblDespesasTotalRegp = new JLabel("DESPESAS", SwingConstants.CENTER);
		lblDespesasTotalRegp.setOpaque(true);
		lblDespesasTotalRegp.setForeground(Color.WHITE);
		lblDespesasTotalRegp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblDespesasTotalRegp.setBackground(new Color(153, 51, 0));
		painelRodapeRp.add(lblDespesasTotalRegp, "cell 3 0,grow");

		 lblValorTotalRegp = new JLabel("TOTAL", SwingConstants.CENTER);
		lblValorTotalRegp.setOpaque(true);
		lblValorTotalRegp.setForeground(Color.WHITE);
		lblValorTotalRegp.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblValorTotalRegp.setBackground(new Color(0, 102, 204));
		painelRodapeRp.add(lblValorTotalRegp, "cell 4 0,grow");

		 lblLucroTotalRegp = new JLabel("LUCRO", SwingConstants.CENTER);
		lblLucroTotalRegp.setOpaque(true);
		lblLucroTotalRegp.setForeground(Color.WHITE);
		lblLucroTotalRegp.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucroTotalRegp.setBackground(new Color(51, 153, 0));
		painelRodapeRp.add(lblLucroTotalRegp, "cell 5 0,grow");

		 lblLucratividadeTotalRegp = new JLabel("LUCRATIVIDADE", SwingConstants.CENTER);
		lblLucratividadeTotalRegp.setOpaque(true);
		lblLucratividadeTotalRegp.setForeground(Color.WHITE);
		lblLucratividadeTotalRegp.setFont(new Font("Arial", Font.BOLD, 14));
		lblLucratividadeTotalRegp.setBackground(new Color(0, 0, 102));
		painelRodapeRp.add(lblLucratividadeTotalRegp, "cell 6 0,grow");
		
	
		painelPaiRp.add(painelRodapeRp, "cell 0 2 3 1,grow");

		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		boolean teste = true;
		if(teste) {
		pesquisar_ccs();
		pesquisarDre();
		pesquisarDreRc();
		pesquisarDreRp();
		}
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

		this.setVisible(true);

	}

	public void pesquisarDre() {

		
		Locale ptBr = new Locale("pt", "BR");

		
		int ano = -1;
		try {
			ano = Integer.parseInt(entAnoFiscal.getText());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ano Fiscal Inválido");
			return;
		}

		CentroCusto cc = (CentroCusto) modelCentrosCustos.getSelectedItem();

		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();

		int id_cc = -1;
		if (cc != null)
			id_cc = cc.getId_centro_custo();
		else
			id_cc = 0;

		double saldo_inicial = gerenciar.getSaldoDreSimplesCC(ano - 1, id_cc);
		double saldo_inicial_global = saldo_inicial;

		double despesas_total = 0, receitas_total = 0, lucro_total = 0, valor_total = 0, lucratividade_total = 0;
		
		listModelGlobal.clear();

		for (DreSimples not : gerenciar.getDreSimplesCC(ano, id_cc)) {

			double despesa = not.getDespesas();
			double receita = not.getReceitas();

			if (despesa < 0) {
				despesa = 0;
			}
			not.setDespesas(-despesa);

			if (receita < 0) {
				receita = 0;
			}

			not.setReceitas(receita);

			double total = saldo_inicial + receita - despesa;
			double lucro =  receita - despesa;
			double lucratividade =  ((lucro * 100) / receita ) ;

			not.setSaldo_inicial(saldo_inicial);
			not.setTotal(total);
			not.setLucro(lucro);
			not.setLucratividade(lucratividade);
			not.setAno(ano);
			saldo_inicial = total;
			

			listModelGlobal.addElement(not);
			
			receitas_total += receita;
			despesas_total += despesa;
		}

		 lblSaldoInicialTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial_global));
		 lblReceitasTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(receitas_total));
		 lblDespesasTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesas_total));
		 
		 valor_total = saldo_inicial_global + receitas_total - despesas_total;

		 lblValorTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
		 
		 lucro_total = receitas_total  - despesas_total;
		 lblLucroTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro_total));
		 
		 lucratividade_total =  ((lucro_total * 100) / receitas_total );
		 DecimalFormat df = new DecimalFormat("#,###.00");
		 lblLucratividadeTotal.setText(df.format(lucratividade_total) + "%");

	}
	
public void pesquisarDreRc() {

		
		Locale ptBr = new Locale("pt", "BR");

		
		int ano = -1;
		try {
			ano = Integer.parseInt(entAnoFiscalRc.getText());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ano Fiscal Inválido");
			return;
		}

		CentroCusto cc = (CentroCusto) modelCentrosCustosRc.getSelectedItem();

		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();

		int id_cc = -1;
		if (cc != null)
			id_cc = cc.getId_centro_custo();
		else
			id_cc = 0;

		double saldo_inicial = gerenciar.getSaldoDreSimplesCCRc(ano - 1, id_cc);
		double saldo_inicial_global = saldo_inicial;

		double despesas_total = 0, receitas_total = 0, lucro_total = 0, valor_total = 0, lucratividade_total = 0;
		
		listModelGlobalRc.clear();

		for (DreSimples not : gerenciar.getDreSimplesCCRc(ano, id_cc)) {

			double despesa = not.getDespesas();
			double receita = not.getReceitas();

			if (despesa < 0) {
				despesa = 0;
			}
			not.setDespesas(-despesa);

			if (receita < 0) {
				receita = 0;
			}

			not.setReceitas(receita);

			double total = saldo_inicial + receita - despesa;
			double lucro =  receita - despesa;
			double lucratividade =  ((lucro * 100) / receita ) ;

			not.setSaldo_inicial(saldo_inicial);
			not.setTotal(total);
			not.setLucro(lucro);
			not.setLucratividade(lucratividade);
			not.setAno(ano);

			saldo_inicial = total;
			

			listModelGlobalRc.addElement(not);
			
			receitas_total += receita;
			despesas_total += despesa;
		}

		 lblSaldoInicialTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial_global));
		 lblReceitasTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(receitas_total));
		 lblDespesasTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesas_total));
		 
		 valor_total = saldo_inicial_global + receitas_total - despesas_total;

		 lblValorTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
		 
		 lucro_total = receitas_total  - despesas_total;
		 lblLucroTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro_total));
		 
		 lucratividade_total =  ((lucro_total * 100) / receitas_total );
		 DecimalFormat df = new DecimalFormat("#,###.00");
		 lblLucratividadeTotalRc.setText(df.format(lucratividade_total) + "%");

	}
	
	
public void pesquisarDreRp() {

	
	Locale ptBr = new Locale("pt", "BR");

	
	int ano = -1;
	try {
		ano = Integer.parseInt(entAnoFiscalRp.getText());

	} catch (Exception e) {
		JOptionPane.showMessageDialog(isto, "Ano Fiscal Inválido");
		return;
	}

	CentroCusto cc = (CentroCusto) modelCentrosCustosRp.getSelectedItem();

	GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();

	int id_cc = -1;
	if (cc != null)
		id_cc = cc.getId_centro_custo();
	else
		id_cc = 0;

	double saldo_inicial = gerenciar.getSaldoDreSimplesCCRp(ano - 1, id_cc);
	double saldo_inicial_global = saldo_inicial;

	double despesas_total = 0, receitas_total = 0, lucro_total = 0, valor_total = 0, lucratividade_total = 0;
	
	listModelGlobalRp.clear();

	for (DreSimples not : gerenciar.getDreSimplesCCRp(ano, id_cc)) {

		double despesa = not.getDespesas();
		double receita = not.getReceitas();

		if (despesa < 0) {
			despesa = 0;
		}
		not.setDespesas(-despesa);

		if (receita < 0) {
			receita = 0;
		}

		not.setReceitas(receita);

		double total = saldo_inicial + receita - despesa;
		double lucro =  receita - despesa;
		double lucratividade =  ((lucro * 100) / receita ) ;

		not.setSaldo_inicial(saldo_inicial);
		not.setTotal(total);
		not.setLucro(lucro);
		not.setLucratividade(lucratividade);
		not.setAno(ano);

		saldo_inicial = total;
		

		listModelGlobalRp.addElement(not);
		
		receitas_total += receita;
		despesas_total += despesa;
	}

	 lblSaldoInicialTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial_global));
	 lblReceitasTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(receitas_total));
	 lblDespesasTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesas_total));
	 
	 valor_total = saldo_inicial_global + receitas_total - despesas_total;

	 lblValorTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
	 
	 lucro_total = receitas_total  - despesas_total;
	 lblLucroTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro_total));
	 
	 lucratividade_total =  ((lucro_total * 100) / receitas_total );
	 DecimalFormat df = new DecimalFormat("#,###.00");
	 lblLucratividadeTotalRegp.setText(df.format(lucratividade_total) + "%");

}

	public void pesquisar_ccs() {

		GerenciarBancoCentroCustos gerenciar = new GerenciarBancoCentroCustos();
		modelCentrosCustos.resetar();
		modelCentrosCustosRc.resetar();
		modelCentrosCustosRp.resetar();

		CentroCusto todos = new CentroCusto();
		todos.setId_centro_custo(0);
		todos.setNome_centro_custo("TODOS");
		modelCentrosCustos.addCC(todos);
		modelCentrosCustosRc.addCC(todos);
		modelCentrosCustosRp.addCC(todos);

		ArrayList<CentroCusto> lista_ccs = gerenciar.getCentroCustos();
		for (CentroCusto cc : lista_ccs) {
			modelCentrosCustos.addCC(cc);
			modelCentrosCustosRc.addCC(cc);
			modelCentrosCustosRp.addCC(cc);

		}

	}
}
