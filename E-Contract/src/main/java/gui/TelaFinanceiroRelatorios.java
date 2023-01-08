/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  keeptoo.KGradientPanel
 *  net.miginfocom.swing.MigLayout
 *  org.jfree.chart.ChartPanel
 *  org.jfree.data.xy.XYDataset
 *  org.jfree.data.xy.XYSeries
 *  org.jfree.data.xy.XYSeriesCollection
 */
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import keeptoo.KGradientPanel;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.DreAgrupado;
import main.java.cadastros.DreSimples;
import main.java.cadastros.InstituicaoBancaria;
import main.java.classesExtras.ComboBoxPersonalizadoCentroCusto;
import main.java.classesExtras.ComboBoxPersonalizadoIB;
import main.java.classesExtras.ComboBoxRenderPersonalizadoCentroCusto;
import main.java.classesExtras.ComboBoxRenderPersonalizadoIB;
import main.java.classesExtras.RenderizadorDreAgrupado;
import main.java.classesExtras.RenderizadorDreSimples;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.graficos.GraficoMultiplaLinha;
import main.java.gui_internal.TelaFinanceiroMostrarReceitasDespesas;
import main.java.outros.GetData;
import main.java.relatoria.RelatorioFinanceiroDRE;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TelaFinanceiroRelatorios
extends JFrame {
    private JPanel painelRelatoriosDre = new JPanel();
    private JTabbedPane painelPrincipal;
    private JPanel painelRelatoriosDreRegimeCompetencia = new JPanel();
    private ArrayList<DreSimples> lista_dre = new ArrayList();
    private JLabel lblSaldoInicialTotal;
    private JLabel lblReceitasTotal;
    private JLabel lblDespesasTotal;
    private JLabel lblValorTotal;
    private JLabel lblLucroTotal;
    private JLabel lblLucratividadeTotal;
    private JLabel lblSaldoInicialTotalRc;
    private JLabel lblReceitasTotalRc;
    private JLabel lblDespesasTotalRc;
    private JLabel lblValorTotalRc;
    private JLabel lblLucroTotalRc;
    private JLabel lblLucratividadeTotalRc;
    private JLabel lblMes1Regp;
    private JLabel lblSaldoInicialTotalRegp;
    private JLabel lblReceitasTotalRegp;
    private JLabel lblDespesasTotalRegp;
    private JLabel lblValorTotalRegp;
    private JLabel lblLucroTotalRegp;
    private JLabel lblLucratividadeTotalRegp;
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
    private DefaultListModel<DreAgrupado> listModelGlobalAgrupadoRegimeLancamento;
    private RenderizadorDreAgrupado renderAgrupadoRegimeLancamento;
    private DefaultListModel<DreAgrupado> listModelGlobalAgrupadoRegimeParcela;
    private RenderizadorDreAgrupado renderAgrupadoRegimeParcela;
    private ChartPanel chartPanel;
    private JComboBox cbCentroCustoRc;
    private JComboBox cbCentroCustoAgrupadoRegimeParcela;
    private JComboBox cbIBRc;
    private ComboBoxPersonalizadoCentroCusto modelCentrosCustos = new ComboBoxPersonalizadoCentroCusto();
    private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizado = new ComboBoxRenderPersonalizadoCentroCusto();
    private ComboBoxPersonalizadoCentroCusto modelCentrosCustosRp = new ComboBoxPersonalizadoCentroCusto();
    private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizadoRp = new ComboBoxRenderPersonalizadoCentroCusto();
    private ComboBoxPersonalizadoCentroCusto modelCentrosCustosAgrupadoRegimeLancamento = new ComboBoxPersonalizadoCentroCusto();
    private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizadoAgrupadoRegimeLancamento = new ComboBoxRenderPersonalizadoCentroCusto();
    private ComboBoxPersonalizadoCentroCusto modelCentrosCustosAgrupadoRegimeParcela = new ComboBoxPersonalizadoCentroCusto();
    private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizadoAgrupadoRegimeParcela = new ComboBoxRenderPersonalizadoCentroCusto();
    private JTextField entAnoFiscalRp;
    private JComboBox cbCentroCustoRp;
    private JComboBox cbCentroCustoAgrupadoRegimeLancamento;
    private ComboBoxPersonalizadoCentroCusto modelCentrosCustosRc = new ComboBoxPersonalizadoCentroCusto();
    private ComboBoxRenderPersonalizadoCentroCusto cbCentroCustoRenderPersonalizadoRc = new ComboBoxRenderPersonalizadoCentroCusto();
    private ComboBoxPersonalizadoIB modelIBRc = new ComboBoxPersonalizadoIB();
    private ComboBoxRenderPersonalizadoIB cbIBRenderPersonalizadoRc = new ComboBoxRenderPersonalizadoIB();
    private JPanel painelGraficoLinha;
    private final JButton btnNewButton = new JButton("Atualizar");
    private JTextField entAnoFiscalRc;
    private JTextField entAnoAgrupadoRegimeLancamento;
    private JTextField entAnoAgrupadoRegimeParcela;

    public TelaFinanceiroRelatorios(Window janela_pai) {
        this.entAnoFiscal.setColumns(10);
        this.isto = this;
        this.setResizable(true);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        System.out.println("Screen width = " + dim.width);
        System.out.println("Screen height = " + dim.height);
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = scrnSize.height - winSize.height;
        System.out.printf("Altura: %d\n", taskBarHeight);
        DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int display_x = display.getWidth();
        int display_y = display.getHeight();
        this.setBounds(0, 0, dim.width, dim.height - taskBarHeight);
        this.setDefaultCloseOperation(2);
        this.setTitle("E-Contract - Relatorios Financeiros");
        UIManager.put("TabbedPane.unselectedForeground", Color.white);
        UIManager.put("TabbedPane.selectedBackground", Color.white);
        this.painelPrincipal = new JTabbedPane();
        this.painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.painelPrincipal.setTabPlacement(2);
        this.painelPrincipal.addTab("Relat\u00f3rios DRE's Detalhados", this.painelRelatoriosDre);
        this.painelRelatoriosDre.setLayout(new BorderLayout(0, 0));
        JTabbedPane abasDre = new JTabbedPane(1);
        this.painelRelatoriosDre.add(abasDre);
        this.painelRelatoriosDreRegimeCompetencia.setBackground(Color.WHITE);
        abasDre.addTab("Relat\u00f3rio DRE Regime de Compet\u00eancia", this.painelRelatoriosDreRegimeCompetencia);
        this.painelRelatoriosDreRegimeCompetencia.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][][grow][grow][grow][][][]"));
        JLabel lblNewLabel_3 = new JLabel("Demonstra\u00e7\u00e3o do Resultado do Exerc\u00edcio Por Data de Lan\u00e7amento(Previsto por Data de Lan\u00e7amento)");
        lblNewLabel_3.setFont(new Font("SansSerif", 3, 16));
        this.painelRelatoriosDreRegimeCompetencia.add((Component)lblNewLabel_3, "cell 0 0,alignx center");
        this.panel.setBackground(Color.WHITE);
        this.painelRelatoriosDreRegimeCompetencia.add((Component)this.panel, "cell 0 1,grow");
        this.panel.setLayout((LayoutManager)new MigLayout("", "[][][][][]", "[]"));
        this.lblNewLabel.setFont(new Font("SansSerif", 1, 16));
        this.panel.add((Component)this.lblNewLabel, "cell 0 0");
        this.cbCentroCusto = new JComboBox();
        this.cbCentroCusto.setModel(this.modelCentrosCustos);
        this.cbCentroCusto.setRenderer(this.cbCentroCustoRenderPersonalizado);
        this.panel.add((Component)this.cbCentroCusto, "cell 1 0");
        this.lblAnoFiscal.setFont(new Font("SansSerif", 1, 16));
        this.panel.add((Component)this.lblAnoFiscal, "cell 2 0,alignx trailing");
        this.panel.add((Component)this.entAnoFiscal, "cell 3 0,growx");
        this.entAnoFiscal.setText(Integer.toString(new GetData().getAnoAtual()));
        this.btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroRelatorios.this.pesquisarDre();
            }
        });
        this.btnNewButton.setForeground(Color.WHITE);
        this.btnNewButton.setFont(new Font("SansSerif", 1, 14));
        this.btnNewButton.setBackground(new Color(0, 0, 102));
        this.panel.add((Component)this.btnNewButton, "cell 4 0");
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout((LayoutManager)new MigLayout("", "[grow]", "[:30px:30px][grow][]"));
        final JList<DreSimples> listaRcompetencia = new JList<DreSimples>();
        panel_1.add(listaRcompetencia, "cell 0 1,growy");
        listaRcompetencia.setOpaque(false);
        listaRcompetencia.setBackground(Color.WHITE);
        this.listModelGlobal = new DefaultListModel();
        this.render = new RenderizadorDreSimples();
        listaRcompetencia.setModel(this.listModelGlobal);
        listaRcompetencia.setCellRenderer(this.render);
        MouseAdapter mouseListener = new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int index;
                if (e.getClickCount() == 2 && (index = listaRcompetencia.locationToIndex(e.getPoint())) >= 0) {
                    DreSimples o = (DreSimples)listaRcompetencia.getModel().getElementAt(index);
                    int mes = o.getMes();
                    int ano = o.getAno();
                    CentroCusto cc = (CentroCusto)TelaFinanceiroRelatorios.this.modelCentrosCustos.getSelectedItem();
                    TelaFinanceiroMostrarReceitasDespesas tela = cc != null ? new TelaFinanceiroMostrarReceitasDespesas(0, cc.getId_centro_custo(), 0, mes, ano, TelaFinanceiroRelatorios.this.isto) : new TelaFinanceiroMostrarReceitasDespesas(0, 0, 0, mes, ano, TelaFinanceiroRelatorios.this.isto);
                    tela.setVisible(true);
                }
            }
        };
        listaRcompetencia.addMouseListener(mouseListener);
        this.scrollDreSimples = new JScrollPane(panel_1);
        this.painelRelatoriosDreRegimeCompetencia.add((Component)this.scrollDreSimples, "cell 0 2 1 3,grow");
        KGradientPanel painelCabecalho = new KGradientPanel();
        panel_1.add((Component)painelCabecalho, "cell 0 0,growx,aligny top");
        painelCabecalho.kEndColor = Color.WHITE;
        painelCabecalho.kStartColor = new Color(255, 255, 255);
        painelCabecalho.setBackground(new Color(153, 153, 102));
        painelCabecalho.setBorder((Border)new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        painelCabecalho.setLayout((LayoutManager)new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px]"));
        JLabel lblMes = new JLabel("     M\u00caS     ", 0);
        lblMes.setOpaque(true);
        lblMes.setBackground(new Color(51, 51, 0));
        lblMes.setForeground(Color.WHITE);
        lblMes.setFont(new Font("SansSerif", 0, 18));
        painelCabecalho.add((Component)lblMes, (Object)"cell 0 0,grow");
        JLabel lblSaldoInicial = new JLabel("SALDO INICIAL", 0);
        lblSaldoInicial.setOpaque(true);
        lblSaldoInicial.setForeground(Color.WHITE);
        lblSaldoInicial.setFont(new Font("SansSerif", 1, 14));
        lblSaldoInicial.setBackground(new Color(255, 153, 0));
        painelCabecalho.add((Component)lblSaldoInicial, (Object)"cell 1 0,grow");
        JLabel lblReceitas = new JLabel("RECEITAS", 0);
        lblReceitas.setOpaque(true);
        lblReceitas.setForeground(Color.WHITE);
        lblReceitas.setFont(new Font("SansSerif", 1, 14));
        lblReceitas.setBackground(new Color(0, 51, 0));
        painelCabecalho.add((Component)lblReceitas, (Object)"cell 2 0,grow");
        JLabel lblDespesas = new JLabel("DESPESAS", 0);
        lblDespesas.setOpaque(true);
        lblDespesas.setForeground(Color.WHITE);
        lblDespesas.setFont(new Font("SansSerif", 1, 14));
        lblDespesas.setBackground(new Color(153, 51, 0));
        painelCabecalho.add((Component)lblDespesas, (Object)"cell 3 0,grow");
        JLabel lblTotal = new JLabel("TOTAL", 0);
        lblTotal.setOpaque(true);
        lblTotal.setForeground(Color.WHITE);
        lblTotal.setFont(new Font("SansSerif", 1, 14));
        lblTotal.setBackground(new Color(0, 102, 204));
        painelCabecalho.add((Component)lblTotal, (Object)"cell 4 0,grow");
        JLabel lblLucro = new JLabel("LUCRO", 0);
        lblLucro.setOpaque(true);
        lblLucro.setForeground(Color.WHITE);
        lblLucro.setFont(new Font("Arial", 1, 14));
        lblLucro.setBackground(new Color(51, 153, 0));
        painelCabecalho.add((Component)lblLucro, (Object)"cell 5 0,grow");
        JLabel lblLucratividade = new JLabel("LUCRATIVIDADE", 0);
        lblLucratividade.setOpaque(true);
        lblLucratividade.setForeground(Color.WHITE);
        lblLucratividade.setFont(new Font("Arial", 1, 14));
        lblLucratividade.setBackground(new Color(0, 0, 102));
        painelCabecalho.add((Component)lblLucratividade, (Object)"cell 6 0,grow");
        KGradientPanel painelRodape = new KGradientPanel();
        painelRodape.kEndColor = Color.WHITE;
        painelRodape.kStartColor = new Color(255, 255, 255);
        painelRodape.setBackground(new Color(153, 153, 102));
        painelRodape.setBorder((Border)new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        painelRodape.setLayout((LayoutManager)new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px][]"));
        JLabel lblMes1 = new JLabel("TOTAIS:", 4);
        lblMes1.setOpaque(true);
        lblMes1.setBackground(new Color(51, 51, 0));
        lblMes1.setForeground(Color.WHITE);
        lblMes1.setFont(new Font("SansSerif", 0, 18));
        painelRodape.add((Component)lblMes1, (Object)"cell 0 0,grow");
        this.lblSaldoInicialTotal = new JLabel("SALDO INICIAL", 0);
        this.lblSaldoInicialTotal.setOpaque(true);
        this.lblSaldoInicialTotal.setForeground(Color.WHITE);
        this.lblSaldoInicialTotal.setFont(new Font("SansSerif", 1, 14));
        this.lblSaldoInicialTotal.setBackground(new Color(255, 153, 0));
        painelRodape.add((Component)this.lblSaldoInicialTotal, (Object)"cell 1 0,grow");
        this.lblReceitasTotal = new JLabel("RECEITAS", 0);
        this.lblReceitasTotal.setOpaque(true);
        this.lblReceitasTotal.setForeground(Color.WHITE);
        this.lblReceitasTotal.setFont(new Font("SansSerif", 1, 14));
        this.lblReceitasTotal.setBackground(new Color(0, 51, 0));
        painelRodape.add((Component)this.lblReceitasTotal, (Object)"cell 2 0,grow");
        this.lblDespesasTotal = new JLabel("DESPESAS", 0);
        this.lblDespesasTotal.setOpaque(true);
        this.lblDespesasTotal.setForeground(Color.WHITE);
        this.lblDespesasTotal.setFont(new Font("SansSerif", 1, 14));
        this.lblDespesasTotal.setBackground(new Color(153, 51, 0));
        painelRodape.add((Component)this.lblDespesasTotal, (Object)"cell 3 0,grow");
        this.lblValorTotal = new JLabel("TOTAL", 0);
        this.lblValorTotal.setOpaque(true);
        this.lblValorTotal.setForeground(Color.WHITE);
        this.lblValorTotal.setFont(new Font("SansSerif", 1, 14));
        this.lblValorTotal.setBackground(new Color(0, 102, 204));
        painelRodape.add((Component)this.lblValorTotal, (Object)"cell 4 0,grow");
        this.lblLucroTotal = new JLabel("LUCRO", 0);
        this.lblLucroTotal.setOpaque(true);
        this.lblLucroTotal.setForeground(Color.WHITE);
        this.lblLucroTotal.setFont(new Font("Arial", 1, 14));
        this.lblLucroTotal.setBackground(new Color(51, 153, 0));
        painelRodape.add((Component)this.lblLucroTotal, (Object)"cell 5 0,grow");
        this.lblLucratividadeTotal = new JLabel("LUCRATIVIDADE", 0);
        this.lblLucratividadeTotal.setOpaque(true);
        this.lblLucratividadeTotal.setForeground(Color.WHITE);
        this.lblLucratividadeTotal.setFont(new Font("Arial", 1, 14));
        this.lblLucratividadeTotal.setBackground(new Color(0, 0, 102));
        painelRodape.add((Component)this.lblLucratividadeTotal, (Object)"cell 6 0,grow");
        panel_1.add((Component)painelRodape, "cell 0 2,grow");
        JPanel painelRelatoriosDreRegimeParcela = new JPanel();
        painelRelatoriosDreRegimeParcela.setBackground(Color.WHITE);
        abasDre.addTab("Relat\u00f3rio DRE Regime de Caixa", null, painelRelatoriosDreRegimeParcela, null);
        painelRelatoriosDreRegimeParcela.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][][grow]"));
        JLabel lblNewLabel_3_1 = new JLabel("Demonstra\u00e7\u00e3o do Resultado do Exerc\u00edcio Por Data de Pagamento(Realizado por Data de Pagamento)");
        lblNewLabel_3_1.setFont(new Font("SansSerif", 3, 16));
        painelRelatoriosDreRegimeParcela.add((Component)lblNewLabel_3_1, "cell 0 0,alignx center");
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        painelRelatoriosDreRegimeParcela.add((Component)panel_2, "cell 0 1,grow");
        panel_2.setLayout((LayoutManager)new MigLayout("", "[][][][][][][]", "[]"));
        JLabel lblNewLabel_1 = new JLabel("Centro de Custo:");
        lblNewLabel_1.setFont(new Font("SansSerif", 1, 16));
        panel_2.add((Component)lblNewLabel_1, "cell 0 0,alignx trailing");
        this.cbCentroCustoRc = new JComboBox();
        panel_2.add((Component)this.cbCentroCustoRc, "flowx,cell 1 0,growx");
        this.cbCentroCustoRc.setModel(this.modelCentrosCustosRc);
        this.cbCentroCustoRc.setRenderer(this.cbCentroCustoRenderPersonalizadoRc);
        JLabel lblNewLabel_1_1 = new JLabel("Institui\u00e7\u00e3o Banc\u00e1ria:");
        lblNewLabel_1_1.setFont(new Font("SansSerif", 1, 16));
        panel_2.add((Component)lblNewLabel_1_1, "cell 2 0,alignx trailing");
        this.cbIBRc = new JComboBox();
        panel_2.add((Component)this.cbIBRc, "cell 3 0,growx");
        this.cbIBRc.setModel(this.modelIBRc);
        this.cbIBRc.setRenderer(this.cbIBRenderPersonalizadoRc);
        JLabel lblAnoFiscal_1 = new JLabel("Ano Fiscal:");
        lblAnoFiscal_1.setFont(new Font("SansSerif", 1, 16));
        panel_2.add((Component)lblAnoFiscal_1, "cell 4 0,alignx trailing");
        this.entAnoFiscalRc = new JTextField();
        this.entAnoFiscalRc.setText(Integer.toString(new GetData().getAnoAtual()));
        this.entAnoFiscalRc.setColumns(10);
        panel_2.add((Component)this.entAnoFiscalRc, "cell 5 0,growx");
        JButton btnNewButton_1 = new JButton("Atualizar");
        btnNewButton_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroRelatorios.this.pesquisarDreRc();
            }
        });
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setFont(new Font("SansSerif", 1, 14));
        btnNewButton_1.setBackground(new Color(0, 0, 102));
        panel_2.add((Component)btnNewButton_1, "cell 6 0");
        JPanel panelPaiRc = new JPanel();
        panelPaiRc.setBackground(Color.WHITE);
        panelPaiRc.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][500px:500px,grow][][500px:500px][]"));
        final JList<DreSimples> listaRc = new JList<DreSimples>();
        panelPaiRc.add(listaRc, "cell 0 1,growy");
        listaRc.setOpaque(false);
        listaRc.setBackground(Color.WHITE);
        MouseAdapter mouseListenerRc = new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int index;
                if (e.getClickCount() == 2 && (index = listaRc.locationToIndex(e.getPoint())) >= 0) {
                    TelaFinanceiroMostrarReceitasDespesas tela;
                    DreSimples o = (DreSimples)listaRc.getModel().getElementAt(index);
                    int mes = o.getMes();
                    int ano = o.getAno();
                    CentroCusto cc = (CentroCusto)TelaFinanceiroRelatorios.this.modelCentrosCustosRc.getSelectedItem();
                    InstituicaoBancaria ib = (InstituicaoBancaria)TelaFinanceiroRelatorios.this.modelIBRc.getSelectedItem();
                    if (cc != null && ib != null) {
                        System.out.println("CC e IB Selecionados");
                        tela = new TelaFinanceiroMostrarReceitasDespesas(1, cc.getId_centro_custo(), ib.getId_instituicao_bancaria(), mes, ano, TelaFinanceiroRelatorios.this.isto);
                    } else if (cc != null && ib == null) {
                        System.out.println("Somente CC Selecionados");
                        tela = new TelaFinanceiroMostrarReceitasDespesas(1, cc.getId_centro_custo(), 0, mes, ano, TelaFinanceiroRelatorios.this.isto);
                    } else if (cc == null && ib != null) {
                        System.out.println("Somente IB Selecionados");
                        tela = new TelaFinanceiroMostrarReceitasDespesas(1, 0, ib.getId_instituicao_bancaria(), mes, ano, TelaFinanceiroRelatorios.this.isto);
                    } else {
                        System.out.println("Nenhum Selecionado");
                        tela = new TelaFinanceiroMostrarReceitasDespesas(1, 0, 0, mes, ano, TelaFinanceiroRelatorios.this.isto);
                    }
                    tela.setVisible(true);
                }
            }
        };
        listaRc.addMouseListener(mouseListenerRc);
        this.listModelGlobalRc = new DefaultListModel();
        this.renderRc = new RenderizadorDreSimples();
        listaRc.setModel(this.listModelGlobalRc);
        listaRc.setCellRenderer(this.renderRc);
        JScrollPane scrollDreSimplesRc = new JScrollPane(panelPaiRc);
        scrollDreSimplesRc.getViewport().setBackground(Color.white);
        painelRelatoriosDreRegimeParcela.add((Component)scrollDreSimplesRc, "cell 0 2,grow");
        KGradientPanel painelCabecalhoRc = new KGradientPanel();
        panelPaiRc.add((Component)painelCabecalhoRc, "cell 0 0,growx,aligny top");
        painelCabecalhoRc.kEndColor = Color.WHITE;
        painelCabecalhoRc.kStartColor = new Color(255, 255, 255);
        painelCabecalhoRc.setBackground(new Color(153, 153, 102));
        painelCabecalhoRc.setBorder((Border)new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        painelCabecalhoRc.setLayout((LayoutManager)new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px]"));
        JLabel lblMesRc = new JLabel("     M\u00caS     ", 0);
        lblMesRc.setOpaque(true);
        lblMesRc.setBackground(new Color(51, 51, 0));
        lblMesRc.setForeground(Color.WHITE);
        lblMesRc.setFont(new Font("SansSerif", 0, 18));
        painelCabecalhoRc.add((Component)lblMesRc, (Object)"cell 0 0,grow");
        JLabel lblSaldoInicialRc = new JLabel("SALDO INICIAL", 0);
        lblSaldoInicialRc.setOpaque(true);
        lblSaldoInicialRc.setForeground(Color.WHITE);
        lblSaldoInicialRc.setFont(new Font("SansSerif", 1, 14));
        lblSaldoInicialRc.setBackground(new Color(255, 153, 0));
        painelCabecalhoRc.add((Component)lblSaldoInicialRc, (Object)"cell 1 0,grow");
        JLabel lblReceitasRc = new JLabel("RECEITAS", 0);
        lblReceitasRc.setOpaque(true);
        lblReceitasRc.setForeground(Color.WHITE);
        lblReceitasRc.setFont(new Font("SansSerif", 1, 14));
        lblReceitasRc.setBackground(new Color(0, 51, 0));
        painelCabecalhoRc.add((Component)lblReceitasRc, (Object)"cell 2 0,grow");
        JLabel lblDespesasRc = new JLabel("DESPESAS", 0);
        lblDespesasRc.setOpaque(true);
        lblDespesasRc.setForeground(Color.WHITE);
        lblDespesasRc.setFont(new Font("SansSerif", 1, 14));
        lblDespesasRc.setBackground(new Color(153, 51, 0));
        painelCabecalhoRc.add((Component)lblDespesasRc, (Object)"cell 3 0,grow");
        JLabel lblTotalRc = new JLabel("TOTAL", 0);
        lblTotalRc.setOpaque(true);
        lblTotalRc.setForeground(Color.WHITE);
        lblTotalRc.setFont(new Font("SansSerif", 1, 14));
        lblTotalRc.setBackground(new Color(0, 102, 204));
        painelCabecalhoRc.add((Component)lblTotalRc, (Object)"cell 4 0,grow");
        JLabel lblLucroRc = new JLabel("LUCRO", 0);
        lblLucroRc.setOpaque(true);
        lblLucroRc.setForeground(Color.WHITE);
        lblLucroRc.setFont(new Font("Arial", 1, 14));
        lblLucroRc.setBackground(new Color(51, 153, 0));
        painelCabecalhoRc.add((Component)lblLucroRc, (Object)"cell 5 0,grow");
        JLabel lblLucratividadeRc = new JLabel("LUCRATIVIDADE", 0);
        lblLucratividadeRc.setOpaque(true);
        lblLucratividadeRc.setForeground(Color.WHITE);
        lblLucratividadeRc.setFont(new Font("Arial", 1, 14));
        lblLucratividadeRc.setBackground(new Color(0, 0, 102));
        painelCabecalhoRc.add((Component)lblLucratividadeRc, (Object)"cell 6 0,grow");
        KGradientPanel painelRodapeRc = new KGradientPanel();
        painelRodapeRc.kEndColor = Color.WHITE;
        painelRodapeRc.kStartColor = new Color(255, 255, 255);
        painelRodapeRc.setBackground(new Color(153, 153, 102));
        painelRodapeRc.setBorder((Border)new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        painelRodapeRc.setLayout((LayoutManager)new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px][]"));
        JLabel lblMes1Rc = new JLabel("TOTAIS:", 4);
        lblMes1Rc.setOpaque(true);
        lblMes1Rc.setBackground(new Color(51, 51, 0));
        lblMes1Rc.setForeground(Color.WHITE);
        lblMes1Rc.setFont(new Font("SansSerif", 0, 18));
        painelRodapeRc.add((Component)lblMes1Rc, (Object)"cell 0 0,grow");
        this.lblSaldoInicialTotalRc = new JLabel("SALDO INICIAL", 0);
        this.lblSaldoInicialTotalRc.setOpaque(true);
        this.lblSaldoInicialTotalRc.setForeground(Color.WHITE);
        this.lblSaldoInicialTotalRc.setFont(new Font("SansSerif", 1, 14));
        this.lblSaldoInicialTotalRc.setBackground(new Color(255, 153, 0));
        painelRodapeRc.add((Component)this.lblSaldoInicialTotalRc, (Object)"cell 1 0,grow");
        this.lblReceitasTotalRc = new JLabel("RECEITAS", 0);
        this.lblReceitasTotalRc.setOpaque(true);
        this.lblReceitasTotalRc.setForeground(Color.WHITE);
        this.lblReceitasTotalRc.setFont(new Font("SansSerif", 1, 14));
        this.lblReceitasTotalRc.setBackground(new Color(0, 51, 0));
        painelRodapeRc.add((Component)this.lblReceitasTotalRc, (Object)"cell 2 0,grow");
        this.lblDespesasTotalRc = new JLabel("DESPESAS", 0);
        this.lblDespesasTotalRc.setOpaque(true);
        this.lblDespesasTotalRc.setForeground(Color.WHITE);
        this.lblDespesasTotalRc.setFont(new Font("SansSerif", 1, 14));
        this.lblDespesasTotalRc.setBackground(new Color(153, 51, 0));
        painelRodapeRc.add((Component)this.lblDespesasTotalRc, (Object)"cell 3 0,grow");
        this.lblValorTotalRc = new JLabel("TOTAL", 0);
        this.lblValorTotalRc.setOpaque(true);
        this.lblValorTotalRc.setForeground(Color.WHITE);
        this.lblValorTotalRc.setFont(new Font("SansSerif", 1, 14));
        this.lblValorTotalRc.setBackground(new Color(0, 102, 204));
        painelRodapeRc.add((Component)this.lblValorTotalRc, (Object)"cell 4 0,grow");
        this.lblLucroTotalRc = new JLabel("LUCRO", 0);
        this.lblLucroTotalRc.setOpaque(true);
        this.lblLucroTotalRc.setForeground(Color.WHITE);
        this.lblLucroTotalRc.setFont(new Font("Arial", 1, 14));
        this.lblLucroTotalRc.setBackground(new Color(51, 153, 0));
        painelRodapeRc.add((Component)this.lblLucroTotalRc, (Object)"cell 5 0,grow");
        this.lblLucratividadeTotalRc = new JLabel("LUCRATIVIDADE", 0);
        this.lblLucratividadeTotalRc.setOpaque(true);
        this.lblLucratividadeTotalRc.setForeground(Color.WHITE);
        this.lblLucratividadeTotalRc.setFont(new Font("Arial", 1, 14));
        this.lblLucratividadeTotalRc.setBackground(new Color(0, 0, 102));
        painelRodapeRc.add((Component)this.lblLucratividadeTotalRc, (Object)"cell 6 0,grow");
        panelPaiRc.add((Component)painelRodapeRc, "cell 0 2,grow");
        JButton btnNewButton_3 = new JButton("Exportar");
        btnNewButton_3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int contador = TelaFinanceiroRelatorios.this.listModelGlobalRc.getSize();
                ArrayList<DreSimples> dreList = new ArrayList<DreSimples>();
                int i = 0;
                while (i < contador) {
                    dreList.add(TelaFinanceiroRelatorios.this.listModelGlobalRc.get(i));
                    ++i;
                }
                CentroCusto cc = (CentroCusto)TelaFinanceiroRelatorios.this.modelCentrosCustosRc.getSelectedItem();
                InstituicaoBancaria ib = (InstituicaoBancaria)TelaFinanceiroRelatorios.this.modelIBRc.getSelectedItem();
                new RelatorioFinanceiroDRE().RelatorioFinanceiroDRERC(TelaFinanceiroRelatorios.this.isto, dreList, cc, ib, Integer.parseInt(TelaFinanceiroRelatorios.this.entAnoFiscalRc.getText()));
            }
        });
        btnNewButton_3.setForeground(Color.WHITE);
        btnNewButton_3.setFont(new Font("SansSerif", 0, 16));
        btnNewButton_3.setBackground(new Color(0, 0, 102));
        painelRodapeRc.add((Component)btnNewButton_3, (Object)"cell 2 1 4 1,alignx center");
        this.painelGraficoLinha = new JPanel();
        this.painelGraficoLinha.setBackground(Color.WHITE);
        panelPaiRc.add((Component)this.painelGraficoLinha, "cell 0 3 1 2,grow");
        this.painelGraficoLinha.setLayout((LayoutManager)new MigLayout("", "[]", "[]"));
        JPanel painelRelatorioParcelas = new JPanel();
        painelRelatorioParcelas.setBackground(Color.WHITE);
        abasDre.addTab("Relat\u00f3rio DRE Regime de Parcelas", null, painelRelatorioParcelas, null);
        painelRelatorioParcelas.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][][grow]"));
        JLabel lblNewLabel_3_1_1 = new JLabel("Demonstra\u00e7\u00e3o do Resultado do Exerc\u00edcio Por Data de Parcelas(Previsto por Data de Parcela)");
        lblNewLabel_3_1_1.setFont(new Font("SansSerif", 3, 16));
        painelRelatorioParcelas.add((Component)lblNewLabel_3_1_1, "cell 0 0,alignx center");
        JPanel panel_2_1 = new JPanel();
        panel_2_1.setBackground(Color.WHITE);
        painelRelatorioParcelas.add((Component)panel_2_1, "cell 0 1,grow");
        panel_2_1.setLayout((LayoutManager)new MigLayout("", "[]", "[]"));
        JLabel lblNewLabel_11 = new JLabel("Centro de Custo:");
        lblNewLabel_11.setFont(new Font("SansSerif", 1, 16));
        panel_2_1.add((Component)lblNewLabel_11, "cell 0 0,alignx trailing");
        this.cbCentroCustoRp = new JComboBox();
        panel_2_1.add((Component)this.cbCentroCustoRp, "flowx,cell 1 0,growx");
        this.cbCentroCustoRp.setModel(this.modelCentrosCustosRp);
        this.cbCentroCustoRp.setRenderer(this.cbCentroCustoRenderPersonalizadoRp);
        JLabel lblAnoFiscal_12 = new JLabel("Ano Fiscal:");
        lblAnoFiscal_12.setFont(new Font("SansSerif", 1, 16));
        panel_2_1.add((Component)lblAnoFiscal_12, "cell 2 0,alignx trailing");
        this.entAnoFiscalRp = new JTextField();
        this.entAnoFiscalRp.setText(Integer.toString(new GetData().getAnoAtual()));
        this.entAnoFiscalRp.setColumns(10);
        panel_2_1.add((Component)this.entAnoFiscalRp, "cell 3 0,growx");
        JButton btnNewButton_12 = new JButton("Atualizar");
        btnNewButton_12.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroRelatorios.this.pesquisarDreRp();
            }
        });
        btnNewButton_12.setForeground(Color.WHITE);
        btnNewButton_12.setFont(new Font("SansSerif", 1, 14));
        btnNewButton_12.setBackground(new Color(0, 0, 102));
        panel_2_1.add((Component)btnNewButton_12, "cell 4 0");
        JPanel painelPaiRp = new JPanel();
        painelPaiRp.setBackground(Color.WHITE);
        JScrollPane scrollPaneRp = new JScrollPane(painelPaiRp);
        scrollPaneRp.getViewport().setBackground(Color.white);
        painelRelatorioParcelas.add((Component)scrollPaneRp, "cell 0 2,grow");
        painelPaiRp.setLayout((LayoutManager)new MigLayout("", "[][1px][1438px]", "[][grow][]"));
        KGradientPanel painelCabecalhoRp = new KGradientPanel();
        painelPaiRp.add((Component)painelCabecalhoRp, "cell 0 0 3 1,grow");
        painelCabecalhoRp.kEndColor = Color.WHITE;
        painelCabecalhoRp.kStartColor = new Color(255, 255, 255);
        painelCabecalhoRp.setBackground(new Color(153, 153, 102));
        painelCabecalhoRp.setBorder((Border)new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        painelCabecalhoRp.setLayout((LayoutManager)new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px]"));
        JLabel lblMesRp = new JLabel("     M\u00caS     ", 0);
        lblMesRp.setOpaque(true);
        lblMesRp.setBackground(new Color(51, 51, 0));
        lblMesRp.setForeground(Color.WHITE);
        lblMesRp.setFont(new Font("SansSerif", 0, 18));
        painelCabecalhoRp.add((Component)lblMesRp, (Object)"cell 0 0,grow");
        JLabel lblSaldoInicialRp = new JLabel("SALDO INICIAL", 0);
        lblSaldoInicialRp.setOpaque(true);
        lblSaldoInicialRp.setForeground(Color.WHITE);
        lblSaldoInicialRp.setFont(new Font("SansSerif", 1, 14));
        lblSaldoInicialRp.setBackground(new Color(255, 153, 0));
        painelCabecalhoRp.add((Component)lblSaldoInicialRp, (Object)"cell 1 0,grow");
        JLabel lblReceitasRp = new JLabel("RECEITAS", 0);
        lblReceitasRp.setOpaque(true);
        lblReceitasRp.setForeground(Color.WHITE);
        lblReceitasRp.setFont(new Font("SansSerif", 1, 14));
        lblReceitasRp.setBackground(new Color(0, 51, 0));
        painelCabecalhoRp.add((Component)lblReceitasRp, (Object)"cell 2 0,grow");
        JLabel lblDespesasRp = new JLabel("DESPESAS", 0);
        lblDespesasRp.setOpaque(true);
        lblDespesasRp.setForeground(Color.WHITE);
        lblDespesasRp.setFont(new Font("SansSerif", 1, 14));
        lblDespesasRp.setBackground(new Color(153, 51, 0));
        painelCabecalhoRp.add((Component)lblDespesasRp, (Object)"cell 3 0,grow");
        JLabel lblTotalRp = new JLabel("TOTAL", 0);
        lblTotalRp.setOpaque(true);
        lblTotalRp.setForeground(Color.WHITE);
        lblTotalRp.setFont(new Font("SansSerif", 1, 14));
        lblTotalRp.setBackground(new Color(0, 102, 204));
        painelCabecalhoRp.add((Component)lblTotalRp, (Object)"cell 4 0,grow");
        JLabel lblLucroRp = new JLabel("LUCRO", 0);
        lblLucroRp.setOpaque(true);
        lblLucroRp.setForeground(Color.WHITE);
        lblLucroRp.setFont(new Font("Arial", 1, 14));
        lblLucroRp.setBackground(new Color(51, 153, 0));
        painelCabecalhoRp.add((Component)lblLucroRp, (Object)"cell 5 0,grow");
        JLabel lblLucratividadeRp = new JLabel("LUCRATIVIDADE", 0);
        lblLucratividadeRp.setOpaque(true);
        lblLucratividadeRp.setForeground(Color.WHITE);
        lblLucratividadeRp.setFont(new Font("Arial", 1, 14));
        lblLucratividadeRp.setBackground(new Color(0, 0, 102));
        painelCabecalhoRp.add((Component)lblLucratividadeRp, (Object)"cell 6 0,grow");
        final JList<DreSimples> listaRpParcelas = new JList<DreSimples>();
        listaRpParcelas.setOpaque(false);
        listaRpParcelas.setBackground(Color.WHITE);
        painelPaiRp.add(listaRpParcelas, "cell 0 1 3 1,grow");
        this.listModelGlobalRp = new DefaultListModel();
        this.renderRp = new RenderizadorDreSimples();
        listaRpParcelas.setModel(this.listModelGlobalRp);
        listaRpParcelas.setCellRenderer(this.renderRp);
        MouseAdapter mouseListenerRparcelas = new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int index;
                if (e.getClickCount() == 2 && (index = listaRpParcelas.locationToIndex(e.getPoint())) >= 0) {
                    DreSimples o = (DreSimples)listaRpParcelas.getModel().getElementAt(index);
                    int mes = o.getMes();
                    int ano = o.getAno();
                    CentroCusto cc = (CentroCusto)TelaFinanceiroRelatorios.this.modelCentrosCustosRp.getSelectedItem();
                    TelaFinanceiroMostrarReceitasDespesas tela = cc != null ? new TelaFinanceiroMostrarReceitasDespesas(2, cc.getId_centro_custo(), 0, mes, ano, TelaFinanceiroRelatorios.this.isto) : new TelaFinanceiroMostrarReceitasDespesas(2, 0, 0, mes, ano, TelaFinanceiroRelatorios.this.isto);
                    tela.setVisible(true);
                }
            }
        };
        listaRpParcelas.addMouseListener(mouseListenerRparcelas);
        KGradientPanel painelRodapeRp = new KGradientPanel();
        painelRodapeRp.kEndColor = Color.WHITE;
        painelRodapeRp.kStartColor = new Color(255, 255, 255);
        painelRodapeRp.setBackground(new Color(153, 153, 102));
        painelRodapeRp.setBorder((Border)new MatteBorder(0, 0, 2, 0, new Color(0, 0, 0)));
        painelRodapeRp.setLayout((LayoutManager)new MigLayout("", "[:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px][:200px:200px]", "[:24px:24px]"));
        JLabel lblMes1Regp = new JLabel("TOTAIS:", 4);
        lblMes1Regp.setOpaque(true);
        lblMes1Regp.setBackground(new Color(51, 51, 0));
        lblMes1Regp.setForeground(Color.WHITE);
        lblMes1Regp.setFont(new Font("SansSerif", 0, 18));
        painelRodapeRp.add((Component)lblMes1Regp, (Object)"cell 0 0,grow");
        this.lblSaldoInicialTotalRegp = new JLabel("SALDO INICIAL", 0);
        this.lblSaldoInicialTotalRegp.setOpaque(true);
        this.lblSaldoInicialTotalRegp.setForeground(Color.WHITE);
        this.lblSaldoInicialTotalRegp.setFont(new Font("SansSerif", 1, 14));
        this.lblSaldoInicialTotalRegp.setBackground(new Color(255, 153, 0));
        painelRodapeRp.add((Component)this.lblSaldoInicialTotalRegp, (Object)"cell 1 0,grow");
        this.lblReceitasTotalRegp = new JLabel("RECEITAS", 0);
        this.lblReceitasTotalRegp.setOpaque(true);
        this.lblReceitasTotalRegp.setForeground(Color.WHITE);
        this.lblReceitasTotalRegp.setFont(new Font("SansSerif", 1, 14));
        this.lblReceitasTotalRegp.setBackground(new Color(0, 51, 0));
        painelRodapeRp.add((Component)this.lblReceitasTotalRegp, (Object)"cell 2 0,grow");
        this.lblDespesasTotalRegp = new JLabel("DESPESAS", 0);
        this.lblDespesasTotalRegp.setOpaque(true);
        this.lblDespesasTotalRegp.setForeground(Color.WHITE);
        this.lblDespesasTotalRegp.setFont(new Font("SansSerif", 1, 14));
        this.lblDespesasTotalRegp.setBackground(new Color(153, 51, 0));
        painelRodapeRp.add((Component)this.lblDespesasTotalRegp, (Object)"cell 3 0,grow");
        this.lblValorTotalRegp = new JLabel("TOTAL", 0);
        this.lblValorTotalRegp.setOpaque(true);
        this.lblValorTotalRegp.setForeground(Color.WHITE);
        this.lblValorTotalRegp.setFont(new Font("SansSerif", 1, 14));
        this.lblValorTotalRegp.setBackground(new Color(0, 102, 204));
        painelRodapeRp.add((Component)this.lblValorTotalRegp, (Object)"cell 4 0,grow");
        this.lblLucroTotalRegp = new JLabel("LUCRO", 0);
        this.lblLucroTotalRegp.setOpaque(true);
        this.lblLucroTotalRegp.setForeground(Color.WHITE);
        this.lblLucroTotalRegp.setFont(new Font("Arial", 1, 14));
        this.lblLucroTotalRegp.setBackground(new Color(51, 153, 0));
        painelRodapeRp.add((Component)this.lblLucroTotalRegp, (Object)"cell 5 0,grow");
        this.lblLucratividadeTotalRegp = new JLabel("LUCRATIVIDADE", 0);
        this.lblLucratividadeTotalRegp.setOpaque(true);
        this.lblLucratividadeTotalRegp.setForeground(Color.WHITE);
        this.lblLucratividadeTotalRegp.setFont(new Font("Arial", 1, 14));
        this.lblLucratividadeTotalRegp.setBackground(new Color(0, 0, 102));
        painelRodapeRp.add((Component)this.lblLucratividadeTotalRegp, (Object)"cell 6 0,grow");
        painelPaiRp.add((Component)painelRodapeRp, "cell 0 2 3 1,grow");
        this.getContentPane().add((Component)this.painelPrincipal, "Center");
        JPanel painelRelatoriosDreAgrupados = new JPanel();
        this.painelPrincipal.addTab("Relat\u00f3rios DRE's Agrupados", null, painelRelatoriosDreAgrupados, null);
        painelRelatoriosDreAgrupados.setLayout(new BorderLayout(0, 0));
        JTabbedPane abasDreAgrupado = new JTabbedPane(1);
        painelRelatoriosDreAgrupados.add(abasDreAgrupado);
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.WHITE);
        abasDreAgrupado.addTab("Relat\u00f3rio DRE's Regime de Competencia", null, panel_3, null);
        panel_3.setLayout((LayoutManager)new MigLayout("", "[][grow]", "[][][grow]"));
        JLabel lblNewLabel_3_2 = new JLabel("Demonstra\u00e7\u00e3o do Resultado do Exerc\u00edcio Por Data de Lan\u00e7amento(Previsto por Data de Lan\u00e7amento)");
        lblNewLabel_3_2.setFont(new Font("SansSerif", 3, 16));
        panel_3.add((Component)lblNewLabel_3_2, "cell 1 0,alignx center");
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        panel_3.add((Component)panel_4, "cell 0 1 2 1,grow");
        panel_4.setLayout((LayoutManager)new MigLayout("", "[][][][][]", "[]"));
        JLabel lblNewLabel_2 = new JLabel("Centro de Custo:");
        panel_4.add((Component)lblNewLabel_2, "flowx,cell 0 0");
        lblNewLabel_2.setFont(new Font("SansSerif", 1, 16));
        this.cbCentroCustoAgrupadoRegimeLancamento = new JComboBox();
        panel_4.add((Component)this.cbCentroCustoAgrupadoRegimeLancamento, "cell 1 0");
        this.cbCentroCustoAgrupadoRegimeLancamento.setModel(this.modelCentrosCustosAgrupadoRegimeLancamento);
        this.cbCentroCustoAgrupadoRegimeLancamento.setRenderer(this.cbCentroCustoRenderPersonalizadoAgrupadoRegimeLancamento);
        JLabel lblAnoFiscal_2 = new JLabel("Ano Fiscal:");
        lblAnoFiscal_2.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)lblAnoFiscal_2, "cell 2 0,alignx trailing");
        this.entAnoAgrupadoRegimeLancamento = new JTextField();
        this.entAnoAgrupadoRegimeLancamento.setText(String.valueOf(new GetData().getAnoAtual()));
        this.entAnoAgrupadoRegimeLancamento.setColumns(10);
        panel_4.add((Component)this.entAnoAgrupadoRegimeLancamento, "cell 3 0,growx");
        JButton btnNewButton_2 = new JButton("Atualizar");
        btnNewButton_2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroRelatorios.this.pesquisarDreAgrupadoRegimeLancamento();
            }
        });
        btnNewButton_2.setForeground(Color.WHITE);
        btnNewButton_2.setFont(new Font("SansSerif", 1, 14));
        btnNewButton_2.setBackground(new Color(0, 0, 102));
        panel_4.add((Component)btnNewButton_2, "cell 4 0");
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        JScrollPane scrollDreAgrupadoRegimeLancamento = new JScrollPane(panel_5);
        panel_3.add((Component)scrollDreAgrupadoRegimeLancamento, "cell 1 2,grow");
        panel_5.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][grow]"));
        JPanel panelCabecalhoAgrupadoRegimeLancamento = new JPanel();
        panelCabecalhoAgrupadoRegimeLancamento.setBackground(Color.WHITE);
        panel_5.add((Component)panelCabecalhoAgrupadoRegimeLancamento, "cell 0 0,grow");
        panelCabecalhoAgrupadoRegimeLancamento.setLayout((LayoutManager)new MigLayout("", "[]", "[]"));
        panelCabecalhoAgrupadoRegimeLancamento.setBackground(Color.WHITE);
        panelCabecalhoAgrupadoRegimeLancamento.setLayout((LayoutManager)new MigLayout("", "[200px:200px:200px][250px:250px:250px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px]", "[]"));
        JLabel lblGrupoContas = new JLabel("Grupo de Contas", 0);
        lblGrupoContas.setOpaque(true);
        lblGrupoContas.setBorder(null);
        lblGrupoContas.setBackground(new Color(0, 51, 204));
        lblGrupoContas.setForeground(Color.WHITE);
        lblGrupoContas.setFont(new Font("Arial", 1, 16));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblGrupoContas, "cell 0 0,grow");
        JLabel lblConta = new JLabel("Conta", 0);
        lblConta.setOpaque(true);
        lblConta.setBorder(null);
        lblConta.setForeground(Color.WHITE);
        lblConta.setFont(new Font("Arial", 1, 16));
        lblConta.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblConta, "cell 1 0,grow");
        JLabel lblJaneiro = new JLabel("Janeiro", 0);
        lblJaneiro.setOpaque(true);
        lblJaneiro.setBorder(null);
        lblJaneiro.setForeground(Color.WHITE);
        lblJaneiro.setFont(new Font("Arial", 1, 16));
        lblJaneiro.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblJaneiro, "cell 2 0,grow");
        JLabel lblFevereiro = new JLabel("Fevereiro", 0);
        lblFevereiro.setOpaque(true);
        lblFevereiro.setBorder(null);
        lblFevereiro.setForeground(Color.WHITE);
        lblFevereiro.setFont(new Font("Arial", 1, 16));
        lblFevereiro.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblFevereiro, "cell 3 0,grow");
        JLabel lblMarco = new JLabel("Marco", 0);
        lblMarco.setOpaque(true);
        lblMarco.setBorder(null);
        lblMarco.setForeground(Color.WHITE);
        lblMarco.setFont(new Font("Arial", 1, 16));
        lblMarco.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblMarco, "cell 4 0,grow");
        JLabel lblAbril = new JLabel("Abril", 0);
        lblAbril.setOpaque(true);
        lblAbril.setBorder(null);
        lblAbril.setForeground(Color.WHITE);
        lblAbril.setFont(new Font("Arial", 1, 16));
        lblAbril.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblAbril, "cell 5 0,grow");
        JLabel lblMaio = new JLabel("Maio", 0);
        lblMaio.setOpaque(true);
        lblMaio.setBorder(null);
        lblMaio.setForeground(Color.WHITE);
        lblMaio.setFont(new Font("Arial", 1, 16));
        lblMaio.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblMaio, "cell 6 0,grow");
        JLabel lblJunho = new JLabel("Junho", 0);
        lblJunho.setOpaque(true);
        lblJunho.setForeground(Color.WHITE);
        lblJunho.setFont(new Font("Arial", 1, 16));
        lblJunho.setBorder(null);
        lblJunho.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblJunho, "cell 7 0,grow");
        JLabel lblJulho = new JLabel("Julho", 0);
        lblJulho.setOpaque(true);
        lblJulho.setForeground(Color.WHITE);
        lblJulho.setFont(new Font("Arial", 1, 16));
        lblJulho.setBorder(null);
        lblJulho.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblJulho, "cell 8 0,grow");
        JLabel lblAgosto = new JLabel("Agosto", 0);
        lblAgosto.setOpaque(true);
        lblAgosto.setForeground(Color.WHITE);
        lblAgosto.setFont(new Font("Arial", 1, 16));
        lblAgosto.setBorder(null);
        lblAgosto.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblAgosto, "cell 9 0,grow");
        JLabel lblSetembro = new JLabel("Setembro", 0);
        lblSetembro.setOpaque(true);
        lblSetembro.setForeground(Color.WHITE);
        lblSetembro.setFont(new Font("Arial", 1, 16));
        lblSetembro.setBorder(null);
        lblSetembro.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblSetembro, "cell 10 0,grow");
        JLabel lblOutubro = new JLabel("Outubro", 0);
        lblOutubro.setOpaque(true);
        lblOutubro.setForeground(Color.WHITE);
        lblOutubro.setFont(new Font("Arial", 1, 16));
        lblOutubro.setBorder(null);
        lblOutubro.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblOutubro, "cell 11 0,grow");
        JLabel lblNovembro = new JLabel("Novembro", 0);
        lblNovembro.setOpaque(true);
        lblNovembro.setForeground(Color.WHITE);
        lblNovembro.setFont(new Font("Arial", 1, 16));
        lblNovembro.setBorder(null);
        lblNovembro.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblNovembro, "cell 12 0,grow");
        JLabel lblDezembro = new JLabel("Dezembro", 0);
        lblDezembro.setOpaque(true);
        lblDezembro.setForeground(Color.WHITE);
        lblDezembro.setFont(new Font("Arial", 1, 16));
        lblDezembro.setBorder(null);
        lblDezembro.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblDezembro, "cell 13 0,grow");
        JLabel lblCabecalhoTotal = new JLabel("Total", 0);
        lblCabecalhoTotal.setOpaque(true);
        lblCabecalhoTotal.setForeground(Color.WHITE);
        lblCabecalhoTotal.setFont(new Font("Arial", 1, 16));
        lblCabecalhoTotal.setBorder(null);
        lblCabecalhoTotal.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeLancamento.add((Component)lblCabecalhoTotal, "cell 14 0,grow");
        JList<DreAgrupado> listDreAgrupadoRegimeLancamento = new JList<DreAgrupado>();
        panel_5.add(listDreAgrupadoRegimeLancamento, "cell 0 1,grow");
        scrollDreAgrupadoRegimeLancamento.getViewport().setBackground(Color.white);
        listDreAgrupadoRegimeLancamento.setOpaque(false);
        listDreAgrupadoRegimeLancamento.setBackground(Color.WHITE);
        this.listModelGlobalAgrupadoRegimeLancamento = new DefaultListModel();
        this.renderAgrupadoRegimeLancamento = new RenderizadorDreAgrupado();
        listDreAgrupadoRegimeLancamento.setModel(this.listModelGlobalAgrupadoRegimeLancamento);
        listDreAgrupadoRegimeLancamento.setCellRenderer(this.renderAgrupadoRegimeLancamento);
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(Color.WHITE);
        abasDreAgrupado.addTab("Relat\u00f3rio DRE Regime de Parcela", null, panel_6, null);
        panel_6.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][][grow]"));
        JLabel lblNewLabel_3_1_1_1 = new JLabel("Demonstra\u00e7\u00e3o do Resultado do Exerc\u00edcio Por Data de Parcelas(Previsto por Data de Parcela)");
        lblNewLabel_3_1_1_1.setFont(new Font("SansSerif", 3, 16));
        panel_6.add((Component)lblNewLabel_3_1_1_1, "cell 0 0,alignx center");
        JPanel panel_4_1 = new JPanel();
        panel_4_1.setBackground(Color.WHITE);
        panel_6.add((Component)panel_4_1, "cell 0 1,grow");
        panel_4_1.setLayout((LayoutManager)new MigLayout("", "[][][][][]", "[]"));
        JLabel lblNewLabel_2_1 = new JLabel("Centro de Custo:");
        lblNewLabel_2_1.setFont(new Font("SansSerif", 1, 16));
        panel_4_1.add((Component)lblNewLabel_2_1, "cell 0 0,alignx trailing");
        this.cbCentroCustoAgrupadoRegimeParcela = new JComboBox();
        this.cbCentroCustoAgrupadoRegimeParcela.setModel(this.modelCentrosCustosAgrupadoRegimeParcela);
        this.cbCentroCustoAgrupadoRegimeParcela.setRenderer(this.cbCentroCustoRenderPersonalizadoAgrupadoRegimeParcela);
        panel_4_1.add((Component)this.cbCentroCustoAgrupadoRegimeParcela, "cell 1 0,growx");
        JLabel lblAnoFiscal_2_1 = new JLabel("Ano Fiscal:");
        lblAnoFiscal_2_1.setFont(new Font("SansSerif", 1, 16));
        panel_4_1.add((Component)lblAnoFiscal_2_1, "cell 2 0,alignx trailing");
        this.entAnoAgrupadoRegimeParcela = new JTextField();
        this.entAnoAgrupadoRegimeParcela.setText(String.valueOf(new GetData().getAnoAtual()));
        this.entAnoAgrupadoRegimeParcela.setColumns(10);
        panel_4_1.add((Component)this.entAnoAgrupadoRegimeParcela, "cell 3 0,growx");
        JButton btnNewButton_2_1 = new JButton("Atualizar");
        btnNewButton_2_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroRelatorios.this.pesquisarDreAgrupadoRegimeParcela();
            }
        });
        btnNewButton_2_1.setForeground(Color.WHITE);
        btnNewButton_2_1.setFont(new Font("SansSerif", 1, 14));
        btnNewButton_2_1.setBackground(new Color(0, 0, 102));
        panel_4_1.add((Component)btnNewButton_2_1, "cell 4 0");
        JPanel panelInternoAgrupadoRegimeParcela = new JPanel();
        panelInternoAgrupadoRegimeParcela.setBackground(Color.WHITE);
        JPanel panelCabecalhoAgrupadoRegimeParcela = new JPanel();
        panelCabecalhoAgrupadoRegimeParcela.setBackground(Color.WHITE);
        panelInternoAgrupadoRegimeParcela.add((Component)panelCabecalhoAgrupadoRegimeParcela, "cell 0 0");
        panelCabecalhoAgrupadoRegimeParcela.setLayout((LayoutManager)new MigLayout("", "[200px:200px:200px][250px:250px:250px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px][100px:100px:100px]", "[]"));
        JLabel lblGrupoContasRegimeParcela = new JLabel("Grupo de Contas", 0);
        lblGrupoContasRegimeParcela.setOpaque(true);
        lblGrupoContasRegimeParcela.setBorder(null);
        lblGrupoContasRegimeParcela.setBackground(new Color(0, 51, 204));
        lblGrupoContasRegimeParcela.setForeground(Color.WHITE);
        lblGrupoContasRegimeParcela.setFont(new Font("Arial", 1, 16));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblGrupoContasRegimeParcela, "cell 0 0,grow");
        JLabel lblContaRegimeParcela = new JLabel("Conta", 0);
        lblContaRegimeParcela.setOpaque(true);
        lblContaRegimeParcela.setBorder(null);
        lblContaRegimeParcela.setForeground(Color.WHITE);
        lblContaRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblContaRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblContaRegimeParcela, "cell 1 0,grow");
        JLabel lblJaneiroRegimeParcela = new JLabel("Janeiro", 0);
        lblJaneiroRegimeParcela.setOpaque(true);
        lblJaneiroRegimeParcela.setBorder(null);
        lblJaneiroRegimeParcela.setForeground(Color.WHITE);
        lblJaneiroRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblJaneiroRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblJaneiroRegimeParcela, "cell 2 0,grow");
        JLabel lblFevereiroRegimeParcela = new JLabel("Fevereiro", 0);
        lblFevereiroRegimeParcela.setOpaque(true);
        lblFevereiroRegimeParcela.setBorder(null);
        lblFevereiroRegimeParcela.setForeground(Color.WHITE);
        lblFevereiroRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblFevereiroRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblFevereiroRegimeParcela, "cell 3 0,grow");
        JLabel lblMarcoRegimeParcela = new JLabel("Marco", 0);
        lblMarcoRegimeParcela.setOpaque(true);
        lblMarcoRegimeParcela.setBorder(null);
        lblMarcoRegimeParcela.setForeground(Color.WHITE);
        lblMarcoRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblMarcoRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblMarcoRegimeParcela, "cell 4 0,grow");
        JLabel lblAbrilRegimeParcela = new JLabel("Abril", 0);
        lblAbrilRegimeParcela.setOpaque(true);
        lblAbrilRegimeParcela.setBorder(null);
        lblAbrilRegimeParcela.setForeground(Color.WHITE);
        lblAbrilRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblAbrilRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblAbrilRegimeParcela, "cell 5 0,grow");
        JLabel lblMaioRegimeParcela = new JLabel("Maio", 0);
        lblMaioRegimeParcela.setOpaque(true);
        lblMaioRegimeParcela.setBorder(null);
        lblMaioRegimeParcela.setForeground(Color.WHITE);
        lblMaioRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblMaioRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblMaioRegimeParcela, "cell 6 0,grow");
        JLabel lblJunhoRegimeParcela = new JLabel("Junho", 0);
        lblJunhoRegimeParcela.setOpaque(true);
        lblJunhoRegimeParcela.setForeground(Color.WHITE);
        lblJunhoRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblJunhoRegimeParcela.setBorder(null);
        lblJunhoRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblJunhoRegimeParcela, "cell 7 0,grow");
        JLabel lblJulhoRegimeParcela = new JLabel("Julho", 0);
        lblJulhoRegimeParcela.setOpaque(true);
        lblJulhoRegimeParcela.setForeground(Color.WHITE);
        lblJulhoRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblJulhoRegimeParcela.setBorder(null);
        lblJulhoRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblJulhoRegimeParcela, "cell 8 0,grow");
        JLabel lblAgostoRegimeParcela = new JLabel("Agosto", 0);
        lblAgostoRegimeParcela.setOpaque(true);
        lblAgostoRegimeParcela.setForeground(Color.WHITE);
        lblAgostoRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblAgostoRegimeParcela.setBorder(null);
        lblAgostoRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblAgostoRegimeParcela, "cell 9 0,grow");
        JLabel lblSetembroRegimeParcela = new JLabel("Setembro", 0);
        lblSetembroRegimeParcela.setOpaque(true);
        lblSetembroRegimeParcela.setForeground(Color.WHITE);
        lblSetembroRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblSetembroRegimeParcela.setBorder(null);
        lblSetembroRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblSetembroRegimeParcela, "cell 10 0,grow");
        JLabel lblOutubroRegimeParcela = new JLabel("Outubro", 0);
        lblOutubroRegimeParcela.setOpaque(true);
        lblOutubroRegimeParcela.setForeground(Color.WHITE);
        lblOutubroRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblOutubroRegimeParcela.setBorder(null);
        lblOutubroRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblOutubroRegimeParcela, "cell 11 0,grow");
        JLabel lblNovembroRegimeParcela = new JLabel("Novembro", 0);
        lblNovembroRegimeParcela.setOpaque(true);
        lblNovembroRegimeParcela.setForeground(Color.WHITE);
        lblNovembroRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblNovembroRegimeParcela.setBorder(null);
        lblNovembroRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblNovembroRegimeParcela, "cell 12 0,grow");
        JLabel lblDezembroRegimeParcela = new JLabel("Dezembro", 0);
        lblDezembroRegimeParcela.setOpaque(true);
        lblDezembroRegimeParcela.setForeground(Color.WHITE);
        lblDezembroRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblDezembroRegimeParcela.setBorder(null);
        lblDezembroRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblDezembroRegimeParcela, "cell 13 0,grow");
        JLabel lblCabecalhoTotalRegimeParcela = new JLabel("Total", 0);
        lblCabecalhoTotalRegimeParcela.setOpaque(true);
        lblCabecalhoTotalRegimeParcela.setForeground(Color.WHITE);
        lblCabecalhoTotalRegimeParcela.setFont(new Font("Arial", 1, 16));
        lblCabecalhoTotalRegimeParcela.setBorder(null);
        lblCabecalhoTotalRegimeParcela.setBackground(new Color(0, 51, 204));
        panelCabecalhoAgrupadoRegimeParcela.add((Component)lblCabecalhoTotalRegimeParcela, "cell 14 0,grow");
        scrollDreAgrupadoRegimeLancamento.getViewport().setBackground(Color.white);
        JScrollPane scrollDreAgrupadoRegimeParcela = new JScrollPane(panelInternoAgrupadoRegimeParcela);
        panelInternoAgrupadoRegimeParcela.setLayout((LayoutManager)new MigLayout("", "[grow][]", "[][grow]"));
        JList<DreAgrupado> listDreAgrupadoRegimeParcela = new JList<DreAgrupado>();
        this.listModelGlobalAgrupadoRegimeParcela = new DefaultListModel();
        this.renderAgrupadoRegimeParcela = new RenderizadorDreAgrupado();
        listDreAgrupadoRegimeParcela.setModel(this.listModelGlobalAgrupadoRegimeParcela);
        listDreAgrupadoRegimeParcela.setCellRenderer(this.renderAgrupadoRegimeParcela);
        panelInternoAgrupadoRegimeParcela.add(listDreAgrupadoRegimeParcela, "cell 0 1,grow");
        scrollDreAgrupadoRegimeParcela.getViewport().setBackground(Color.WHITE);
        panel_6.add((Component)scrollDreAgrupadoRegimeParcela, "cell 0 2,grow");
        boolean teste = true;
        if (teste) {
            this.pesquisar_ccs();
            this.pesquisar_ibs();
            this.pesquisarDre();
            this.pesquisarDreRp();
        }
        this.setExtendedState(6);
        this.setLocationRelativeTo(janela_pai);
        this.setVisible(true);
    }

    public void pesquisarDre() {
        double saldo_inicial;
        Locale ptBr = new Locale("pt", "BR");
        int ano = -1;
        try {
            ano = Integer.parseInt(this.entAnoFiscal.getText());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.isto, "Ano Fiscal Inv\u00e1lido");
            return;
        }
        CentroCusto cc = (CentroCusto)this.modelCentrosCustos.getSelectedItem();
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        int id_cc = -1;
        id_cc = cc != null ? cc.getId_centro_custo() : 0;
        double saldo_inicial_global = saldo_inicial = gerenciar.getSaldoDreSimplesCC(ano - 1, id_cc);
        double despesas_total = 0.0;
        double receitas_total = 0.0;
        double lucro_total = 0.0;
        double valor_total = 0.0;
        double lucratividade_total = 0.0;
        this.listModelGlobal.clear();
        for (DreSimples not : gerenciar.getDreSimplesCC(ano, id_cc)) {
            double despesa = not.getDespesas();
            double receita = not.getReceitas();
            if (despesa < 0.0) {
                despesa = 0.0;
            }
            not.setDespesas(-despesa);
            if (receita < 0.0) {
                receita = 0.0;
            }
            not.setReceitas(receita);
            double total = saldo_inicial + receita - despesa;
            double lucro = receita - despesa;
            double lucratividade = lucro * 100.0 / receita;
            not.setSaldo_inicial(saldo_inicial);
            not.setTotal(total);
            not.setLucro(lucro);
            not.setLucratividade(lucratividade);
            not.setAno(ano);
            saldo_inicial = total;
            this.listModelGlobal.addElement(not);
            receitas_total += receita;
            despesas_total += despesa;
        }
        this.lblSaldoInicialTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial_global));
        this.lblReceitasTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(receitas_total));
        this.lblDespesasTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesas_total));
        valor_total = saldo_inicial_global + receitas_total - despesas_total;
        this.lblValorTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
        lucro_total = receitas_total - despesas_total;
        this.lblLucroTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro_total));
        lucratividade_total = lucro_total * 100.0 / receitas_total;
        DecimalFormat df = new DecimalFormat("#,###.00");
        this.lblLucratividadeTotal.setText(String.valueOf(df.format(lucratividade_total)) + "%");
    }

    public void pesquisarDreRc() {
        Locale ptBr = new Locale("pt", "BR");
        int ano = -1;
        try {
            ano = Integer.parseInt(this.entAnoFiscalRc.getText());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.isto, "Ano Fiscal Inv\u00e1lido");
            return;
        }
        CentroCusto cc = (CentroCusto)this.modelCentrosCustosRc.getSelectedItem();
        InstituicaoBancaria ib = (InstituicaoBancaria)this.modelIBRc.getSelectedItem();
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        int id_cc = -1;
        id_cc = cc != null ? cc.getId_centro_custo() : 0;
        int id_ib = -1;
        id_ib = ib != null ? ib.getId_instituicao_bancaria() : 0;
        int contador_anos = 2015;
        double saldo_total = 0.0;
        while (contador_anos < ano) {
            double saldo_inicial = gerenciar.getSaldoDreSimplesCCRc(contador_anos, id_cc, id_ib);
            saldo_total += saldo_inicial;
            ++contador_anos;
        }
        double saldo_inicial_global = saldo_total;
        double saldo_inicial = saldo_total;
        double despesas_total = 0.0;
        double receitas_total = 0.0;
        double lucro_total = 0.0;
        double valor_total = 0.0;
        double lucratividade_total = 0.0;
        this.listModelGlobalRc.clear();
        for (DreSimples not : gerenciar.getDreSimplesCCRc(ano, id_cc, id_ib)) {
            double despesa = not.getDespesas();
            double receita = not.getReceitas();
            if (despesa < 0.0) {
                despesa = 0.0;
            }
            not.setDespesas(-despesa);
            if (receita < 0.0) {
                receita = 0.0;
            }
            not.setReceitas(receita);
            double total = saldo_inicial + receita - despesa;
            double lucro = receita - despesa;
            double lucratividade = lucro * 100.0 / receita;
            not.setSaldo_inicial(saldo_inicial);
            not.setTotal(total);
            not.setLucro(lucro);
            not.setLucratividade(lucratividade);
            not.setAno(ano);
            saldo_inicial = total;
            this.listModelGlobalRc.addElement(not);
            receitas_total += receita;
            despesas_total += despesa;
        }
        this.lblSaldoInicialTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial_global));
        this.lblReceitasTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(receitas_total));
        this.lblDespesasTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesas_total));
        valor_total = saldo_inicial_global + receitas_total - despesas_total;
        this.lblValorTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
        lucro_total = receitas_total - despesas_total;
        this.lblLucroTotalRc.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro_total));
        lucratividade_total = lucro_total * 100.0 / receitas_total;
        DecimalFormat df = new DecimalFormat("#,###.00");
        this.lblLucratividadeTotalRc.setText(String.valueOf(df.format(lucratividade_total)) + "%");
        this.atualizarGrafico();
    }

    public void pesquisarDreAgrupadoRegimeLancamento() {
        double totais_receitas_janeiro = 0.0;
        double totais_receitas_fevereiro = 0.0;
        double totais_receitas_marco = 0.0;
        double totais_receitas_abril = 0.0;
        double totais_receitas_maio = 0.0;
        double totais_receitas_junho = 0.0;
        double totais_receitas_julho = 0.0;
        double totais_receitas_agosto = 0.0;
        double totais_receitas_setembro = 0.0;
        double totais_receitas_outubro = 0.0;
        double totais_receitas_novembro = 0.0;
        double totais_receitas_dezembro = 0.0;
        double totais_despesas_janeiro = 0.0;
        double totais_despesas_fevereiro = 0.0;
        double totais_despesas_marco = 0.0;
        double totais_despesas_abril = 0.0;
        double totais_despesas_maio = 0.0;
        double totais_despesas_junho = 0.0;
        double totais_despesas_julho = 0.0;
        double totais_despesas_agosto = 0.0;
        double totais_despesas_setembro = 0.0;
        double totais_despesas_outubro = 0.0;
        double totais_despesas_novembro = 0.0;
        double totais_despesas_dezembro = 0.0;
        Locale ptBr = new Locale("pt", "BR");
        int ano = -1;
        try {
            ano = Integer.parseInt(this.entAnoAgrupadoRegimeLancamento.getText());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.isto, "Ano Fiscal Inv\u00e1lido");
            return;
        }
        CentroCusto cc = (CentroCusto)this.modelCentrosCustosAgrupadoRegimeLancamento.getSelectedItem();
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        int id_cc = -1;
        id_cc = cc != null ? cc.getId_centro_custo() : 0;
        this.listModelGlobalAgrupadoRegimeLancamento.clear();
        for (DreAgrupado not : gerenciar.getDreAgrupadoCCRegimeLancamentoReceitas(ano, id_cc)) {
            totais_receitas_janeiro += not.getValor_receitas_janeiro();
            totais_receitas_fevereiro += not.getValor_receitas_fevereiro();
            totais_receitas_marco += not.getValor_receitas_marco();
            totais_receitas_abril += not.getValor_receitas_abril();
            totais_receitas_maio += not.getValor_receitas_maio();
            totais_receitas_junho += not.getValor_receitas_junho();
            totais_receitas_julho += not.getValor_receitas_julho();
            totais_receitas_agosto += not.getValor_receitas_agosto();
            totais_receitas_setembro += not.getValor_receitas_setembro();
            totais_receitas_outubro += not.getValor_receitas_outubro();
            totais_receitas_novembro += not.getValor_receitas_novembro();
            totais_receitas_dezembro += not.getValor_receitas_dezembro();
        }
        for (DreAgrupado not : gerenciar.getDreAgrupadoCCRegimeLancamentoDespesas(ano, id_cc)) {
            totais_despesas_janeiro += not.getValor_despesas_janeiro();
            totais_despesas_fevereiro += not.getValor_despesas_fevereiro();
            totais_despesas_marco += not.getValor_despesas_marco();
            totais_despesas_abril += not.getValor_despesas_abril();
            totais_despesas_maio += not.getValor_despesas_maio();
            totais_despesas_junho += not.getValor_despesas_junho();
            totais_despesas_julho += not.getValor_despesas_julho();
            totais_despesas_agosto += not.getValor_despesas_agosto();
            totais_despesas_setembro += not.getValor_despesas_setembro();
            totais_despesas_outubro += not.getValor_despesas_outubro();
            totais_despesas_novembro += not.getValor_despesas_novembro();
            totais_despesas_dezembro += not.getValor_despesas_dezembro();
        }
        DreAgrupado linha_totais_receitas_cabecalho = new DreAgrupado();
        linha_totais_receitas_cabecalho.setNome_conta("Total Receita: :");
        linha_totais_receitas_cabecalho.setNome_grupo_contas("");
        linha_totais_receitas_cabecalho.setFlag(1);
        linha_totais_receitas_cabecalho.setValor_receitas_janeiro(totais_receitas_janeiro);
        linha_totais_receitas_cabecalho.setValor_receitas_fevereiro(totais_receitas_fevereiro);
        linha_totais_receitas_cabecalho.setValor_receitas_marco(totais_receitas_marco);
        linha_totais_receitas_cabecalho.setValor_receitas_abril(totais_receitas_abril);
        linha_totais_receitas_cabecalho.setValor_receitas_maio(totais_receitas_maio);
        linha_totais_receitas_cabecalho.setValor_receitas_junho(totais_receitas_junho);
        linha_totais_receitas_cabecalho.setValor_receitas_julho(totais_receitas_julho);
        linha_totais_receitas_cabecalho.setValor_receitas_agosto(totais_receitas_agosto);
        linha_totais_receitas_cabecalho.setValor_receitas_setembro(totais_receitas_setembro);
        linha_totais_receitas_cabecalho.setValor_receitas_outubro(totais_receitas_outubro);
        linha_totais_receitas_cabecalho.setValor_receitas_novembro(totais_receitas_novembro);
        linha_totais_receitas_cabecalho.setValor_receitas_dezembro(totais_receitas_dezembro);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_totais_receitas_cabecalho);
        DreAgrupado linha_totais_despesas_cabecalho = new DreAgrupado();
        linha_totais_despesas_cabecalho.setNome_conta("Total Despesas:");
        linha_totais_despesas_cabecalho.setNome_grupo_contas("");
        linha_totais_despesas_cabecalho.setFlag(0);
        linha_totais_despesas_cabecalho.setValor_despesas_janeiro(totais_despesas_janeiro);
        linha_totais_despesas_cabecalho.setValor_despesas_fevereiro(totais_despesas_fevereiro);
        linha_totais_despesas_cabecalho.setValor_despesas_marco(totais_despesas_marco);
        linha_totais_despesas_cabecalho.setValor_despesas_abril(totais_despesas_abril);
        linha_totais_despesas_cabecalho.setValor_despesas_maio(totais_despesas_maio);
        linha_totais_despesas_cabecalho.setValor_despesas_junho(totais_despesas_junho);
        linha_totais_despesas_cabecalho.setValor_despesas_julho(totais_despesas_julho);
        linha_totais_despesas_cabecalho.setValor_despesas_agosto(totais_despesas_agosto);
        linha_totais_despesas_cabecalho.setValor_despesas_setembro(totais_despesas_setembro);
        linha_totais_despesas_cabecalho.setValor_despesas_outubro(totais_despesas_outubro);
        linha_totais_despesas_cabecalho.setValor_despesas_novembro(totais_despesas_novembro);
        linha_totais_despesas_cabecalho.setValor_despesas_dezembro(totais_despesas_dezembro);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_totais_despesas_cabecalho);
        DreAgrupado linha_totais_balanco_cabecalho = new DreAgrupado();
        linha_totais_balanco_cabecalho.setNome_conta("Balan\u00e7o:");
        linha_totais_balanco_cabecalho.setNome_grupo_contas("");
        linha_totais_balanco_cabecalho.setFlag(0);
        linha_totais_balanco_cabecalho.setValor_despesas_janeiro(totais_receitas_janeiro - totais_despesas_janeiro);
        linha_totais_balanco_cabecalho.setValor_despesas_fevereiro(totais_receitas_fevereiro - totais_despesas_fevereiro);
        linha_totais_balanco_cabecalho.setValor_despesas_marco(totais_receitas_marco - totais_despesas_marco);
        linha_totais_balanco_cabecalho.setValor_despesas_abril(totais_receitas_abril - totais_despesas_abril);
        linha_totais_balanco_cabecalho.setValor_despesas_maio(totais_receitas_maio - totais_despesas_maio);
        linha_totais_balanco_cabecalho.setValor_despesas_junho(totais_receitas_junho - totais_despesas_junho);
        linha_totais_balanco_cabecalho.setValor_despesas_julho(totais_receitas_julho - totais_despesas_julho);
        linha_totais_balanco_cabecalho.setValor_despesas_agosto(totais_receitas_agosto - totais_despesas_agosto);
        linha_totais_balanco_cabecalho.setValor_despesas_setembro(totais_receitas_setembro - totais_despesas_setembro);
        linha_totais_balanco_cabecalho.setValor_despesas_outubro(totais_receitas_outubro - totais_despesas_outubro);
        linha_totais_balanco_cabecalho.setValor_despesas_novembro(totais_receitas_novembro - totais_despesas_novembro);
        linha_totais_balanco_cabecalho.setValor_despesas_dezembro(totais_receitas_dezembro - totais_despesas_dezembro);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_totais_balanco_cabecalho);
        DreAgrupado linha_receita = new DreAgrupado();
        linha_receita.setFlag(-1);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_receita);
        for (DreAgrupado not : gerenciar.getDreAgrupadoCCRegimeLancamentoReceitas(ano, id_cc)) {
            this.listModelGlobalAgrupadoRegimeLancamento.addElement(not);
        }
        DreAgrupado linha_totais_receitas = new DreAgrupado();
        linha_totais_receitas.setNome_conta("Totais:");
        linha_totais_receitas.setNome_grupo_contas("");
        linha_totais_receitas.setFlag(1);
        linha_totais_receitas.setValor_receitas_janeiro(totais_receitas_janeiro);
        linha_totais_receitas.setValor_receitas_fevereiro(totais_receitas_fevereiro);
        linha_totais_receitas.setValor_receitas_marco(totais_receitas_marco);
        linha_totais_receitas.setValor_receitas_abril(totais_receitas_abril);
        linha_totais_receitas.setValor_receitas_maio(totais_receitas_maio);
        linha_totais_receitas.setValor_receitas_junho(totais_receitas_junho);
        linha_totais_receitas.setValor_receitas_julho(totais_receitas_julho);
        linha_totais_receitas.setValor_receitas_agosto(totais_receitas_agosto);
        linha_totais_receitas.setValor_receitas_setembro(totais_receitas_setembro);
        linha_totais_receitas.setValor_receitas_outubro(totais_receitas_outubro);
        linha_totais_receitas.setValor_receitas_novembro(totais_receitas_novembro);
        linha_totais_receitas.setValor_receitas_dezembro(totais_receitas_dezembro);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_totais_receitas);
        DreAgrupado linha_despesas = new DreAgrupado();
        linha_despesas.setFlag(-2);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_despesas);
        for (DreAgrupado not : gerenciar.getDreAgrupadoCCRegimeLancamentoDespesas(ano, id_cc)) {
            this.listModelGlobalAgrupadoRegimeLancamento.addElement(not);
        }
        DreAgrupado linha_totais_despesas = new DreAgrupado();
        linha_totais_despesas.setNome_conta("Totais:");
        linha_totais_despesas.setNome_grupo_contas("");
        linha_totais_despesas.setFlag(0);
        linha_totais_despesas.setValor_despesas_janeiro(totais_despesas_janeiro);
        linha_totais_despesas.setValor_despesas_fevereiro(totais_despesas_fevereiro);
        linha_totais_despesas.setValor_despesas_marco(totais_despesas_marco);
        linha_totais_despesas.setValor_despesas_abril(totais_despesas_abril);
        linha_totais_despesas.setValor_despesas_maio(totais_despesas_maio);
        linha_totais_despesas.setValor_despesas_junho(totais_despesas_junho);
        linha_totais_despesas.setValor_despesas_julho(totais_despesas_julho);
        linha_totais_despesas.setValor_despesas_agosto(totais_despesas_agosto);
        linha_totais_despesas.setValor_despesas_setembro(totais_despesas_setembro);
        linha_totais_despesas.setValor_despesas_outubro(totais_despesas_outubro);
        linha_totais_despesas.setValor_despesas_novembro(totais_despesas_novembro);
        linha_totais_despesas.setValor_despesas_dezembro(totais_despesas_dezembro);
        this.listModelGlobalAgrupadoRegimeLancamento.addElement(linha_totais_despesas);
    }

    public void pesquisarDreAgrupadoRegimeParcela() {
        double totais_receitas_janeiro = 0.0;
        double totais_receitas_fevereiro = 0.0;
        double totais_receitas_marco = 0.0;
        double totais_receitas_abril = 0.0;
        double totais_receitas_maio = 0.0;
        double totais_receitas_junho = 0.0;
        double totais_receitas_julho = 0.0;
        double totais_receitas_agosto = 0.0;
        double totais_receitas_setembro = 0.0;
        double totais_receitas_outubro = 0.0;
        double totais_receitas_novembro = 0.0;
        double totais_receitas_dezembro = 0.0;
        double totais_despesas_janeiro = 0.0;
        double totais_despesas_fevereiro = 0.0;
        double totais_despesas_marco = 0.0;
        double totais_despesas_abril = 0.0;
        double totais_despesas_maio = 0.0;
        double totais_despesas_junho = 0.0;
        double totais_despesas_julho = 0.0;
        double totais_despesas_agosto = 0.0;
        double totais_despesas_setembro = 0.0;
        double totais_despesas_outubro = 0.0;
        double totais_despesas_novembro = 0.0;
        double totais_despesas_dezembro = 0.0;
        Locale ptBr = new Locale("pt", "BR");
        int ano = -1;
        try {
            ano = Integer.parseInt(this.entAnoAgrupadoRegimeParcela.getText());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.isto, "Ano Fiscal Inv\u00e1lido");
            return;
        }
        CentroCusto cc = (CentroCusto)this.modelCentrosCustosAgrupadoRegimeParcela.getSelectedItem();
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        int id_cc = -1;
        id_cc = cc != null ? cc.getId_centro_custo() : 0;
        this.listModelGlobalAgrupadoRegimeParcela.clear();
        ArrayList<DreAgrupado> despesas = gerenciar.getDreAgrupadoCCRegimeParcelaDespesas(ano, id_cc);
        ArrayList<DreAgrupado> receitas = gerenciar.getDreAgrupadoCCRegimeParcelaReceitas(ano, id_cc);
        for (DreAgrupado not : receitas) {
            totais_receitas_janeiro += not.getValor_receitas_janeiro();
            totais_receitas_fevereiro += not.getValor_receitas_fevereiro();
            totais_receitas_marco += not.getValor_receitas_marco();
            totais_receitas_abril += not.getValor_receitas_abril();
            totais_receitas_maio += not.getValor_receitas_maio();
            totais_receitas_junho += not.getValor_receitas_junho();
            totais_receitas_julho += not.getValor_receitas_julho();
            totais_receitas_agosto += not.getValor_receitas_agosto();
            totais_receitas_setembro += not.getValor_receitas_setembro();
            totais_receitas_outubro += not.getValor_receitas_outubro();
            totais_receitas_novembro += not.getValor_receitas_novembro();
            totais_receitas_dezembro += not.getValor_receitas_dezembro();
        }
        for (DreAgrupado not : despesas) {
            totais_despesas_janeiro += not.getValor_despesas_janeiro();
            totais_despesas_fevereiro += not.getValor_despesas_fevereiro();
            totais_despesas_marco += not.getValor_despesas_marco();
            totais_despesas_abril += not.getValor_despesas_abril();
            totais_despesas_maio += not.getValor_despesas_maio();
            totais_despesas_junho += not.getValor_despesas_junho();
            totais_despesas_julho += not.getValor_despesas_julho();
            totais_despesas_agosto += not.getValor_despesas_agosto();
            totais_despesas_setembro += not.getValor_despesas_setembro();
            totais_despesas_outubro += not.getValor_despesas_outubro();
            totais_despesas_novembro += not.getValor_despesas_novembro();
            totais_despesas_dezembro += not.getValor_despesas_dezembro();
        }
        DreAgrupado linha_totais_receitas_cabecalho = new DreAgrupado();
        linha_totais_receitas_cabecalho.setNome_conta("Total Receita: :");
        linha_totais_receitas_cabecalho.setNome_grupo_contas("");
        linha_totais_receitas_cabecalho.setFlag(1);
        linha_totais_receitas_cabecalho.setValor_receitas_janeiro(totais_receitas_janeiro);
        linha_totais_receitas_cabecalho.setValor_receitas_fevereiro(totais_receitas_fevereiro);
        linha_totais_receitas_cabecalho.setValor_receitas_marco(totais_receitas_marco);
        linha_totais_receitas_cabecalho.setValor_receitas_abril(totais_receitas_abril);
        linha_totais_receitas_cabecalho.setValor_receitas_maio(totais_receitas_maio);
        linha_totais_receitas_cabecalho.setValor_receitas_junho(totais_receitas_junho);
        linha_totais_receitas_cabecalho.setValor_receitas_julho(totais_receitas_julho);
        linha_totais_receitas_cabecalho.setValor_receitas_agosto(totais_receitas_agosto);
        linha_totais_receitas_cabecalho.setValor_receitas_setembro(totais_receitas_setembro);
        linha_totais_receitas_cabecalho.setValor_receitas_outubro(totais_receitas_outubro);
        linha_totais_receitas_cabecalho.setValor_receitas_novembro(totais_receitas_novembro);
        linha_totais_receitas_cabecalho.setValor_receitas_dezembro(totais_receitas_dezembro);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_totais_receitas_cabecalho);
        DreAgrupado linha_totais_despesas_cabecalho = new DreAgrupado();
        linha_totais_despesas_cabecalho.setNome_conta("Total Despesas:");
        linha_totais_despesas_cabecalho.setNome_grupo_contas("");
        linha_totais_despesas_cabecalho.setFlag(0);
        linha_totais_despesas_cabecalho.setValor_despesas_janeiro(totais_despesas_janeiro);
        linha_totais_despesas_cabecalho.setValor_despesas_fevereiro(totais_despesas_fevereiro);
        linha_totais_despesas_cabecalho.setValor_despesas_marco(totais_despesas_marco);
        linha_totais_despesas_cabecalho.setValor_despesas_abril(totais_despesas_abril);
        linha_totais_despesas_cabecalho.setValor_despesas_maio(totais_despesas_maio);
        linha_totais_despesas_cabecalho.setValor_despesas_junho(totais_despesas_junho);
        linha_totais_despesas_cabecalho.setValor_despesas_julho(totais_despesas_julho);
        linha_totais_despesas_cabecalho.setValor_despesas_agosto(totais_despesas_agosto);
        linha_totais_despesas_cabecalho.setValor_despesas_setembro(totais_despesas_setembro);
        linha_totais_despesas_cabecalho.setValor_despesas_outubro(totais_despesas_outubro);
        linha_totais_despesas_cabecalho.setValor_despesas_novembro(totais_despesas_novembro);
        linha_totais_despesas_cabecalho.setValor_despesas_dezembro(totais_despesas_dezembro);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_totais_despesas_cabecalho);
        DreAgrupado linha_totais_balanco_cabecalho = new DreAgrupado();
        linha_totais_balanco_cabecalho.setNome_conta("Balan\u00e7o:");
        linha_totais_balanco_cabecalho.setNome_grupo_contas("");
        linha_totais_balanco_cabecalho.setFlag(0);
        linha_totais_balanco_cabecalho.setValor_despesas_janeiro(totais_receitas_janeiro - totais_despesas_janeiro);
        linha_totais_balanco_cabecalho.setValor_despesas_fevereiro(totais_receitas_fevereiro - totais_despesas_fevereiro);
        linha_totais_balanco_cabecalho.setValor_despesas_marco(totais_receitas_marco - totais_despesas_marco);
        linha_totais_balanco_cabecalho.setValor_despesas_abril(totais_receitas_abril - totais_despesas_abril);
        linha_totais_balanco_cabecalho.setValor_despesas_maio(totais_receitas_maio - totais_despesas_maio);
        linha_totais_balanco_cabecalho.setValor_despesas_junho(totais_receitas_junho - totais_despesas_junho);
        linha_totais_balanco_cabecalho.setValor_despesas_julho(totais_receitas_julho - totais_despesas_julho);
        linha_totais_balanco_cabecalho.setValor_despesas_agosto(totais_receitas_agosto - totais_despesas_agosto);
        linha_totais_balanco_cabecalho.setValor_despesas_setembro(totais_receitas_setembro - totais_despesas_setembro);
        linha_totais_balanco_cabecalho.setValor_despesas_outubro(totais_receitas_outubro - totais_despesas_outubro);
        linha_totais_balanco_cabecalho.setValor_despesas_novembro(totais_receitas_novembro - totais_despesas_novembro);
        linha_totais_balanco_cabecalho.setValor_despesas_dezembro(totais_receitas_dezembro - totais_despesas_dezembro);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_totais_balanco_cabecalho);
        DreAgrupado linha_receita = new DreAgrupado();
        linha_receita.setFlag(-1);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_receita);
        for (DreAgrupado not : receitas) {
            this.listModelGlobalAgrupadoRegimeParcela.addElement(not);
        }
        DreAgrupado linha_totais_receitas = new DreAgrupado();
        linha_totais_receitas.setNome_conta("Totais:");
        linha_totais_receitas.setNome_grupo_contas("");
        linha_totais_receitas.setFlag(1);
        linha_totais_receitas.setValor_receitas_janeiro(totais_receitas_janeiro);
        linha_totais_receitas.setValor_receitas_fevereiro(totais_receitas_fevereiro);
        linha_totais_receitas.setValor_receitas_marco(totais_receitas_marco);
        linha_totais_receitas.setValor_receitas_abril(totais_receitas_abril);
        linha_totais_receitas.setValor_receitas_maio(totais_receitas_maio);
        linha_totais_receitas.setValor_receitas_junho(totais_receitas_junho);
        linha_totais_receitas.setValor_receitas_julho(totais_receitas_julho);
        linha_totais_receitas.setValor_receitas_agosto(totais_receitas_agosto);
        linha_totais_receitas.setValor_receitas_setembro(totais_receitas_setembro);
        linha_totais_receitas.setValor_receitas_outubro(totais_receitas_outubro);
        linha_totais_receitas.setValor_receitas_novembro(totais_receitas_novembro);
        linha_totais_receitas.setValor_receitas_dezembro(totais_receitas_dezembro);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_totais_receitas);
        DreAgrupado linha_despesas = new DreAgrupado();
        linha_despesas.setFlag(-2);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_despesas);
        for (DreAgrupado not : despesas) {
            this.listModelGlobalAgrupadoRegimeParcela.addElement(not);
        }
        DreAgrupado linha_totais_despesas = new DreAgrupado();
        linha_totais_despesas.setNome_conta("Totais:");
        linha_totais_despesas.setNome_grupo_contas("");
        linha_totais_despesas.setFlag(0);
        linha_totais_despesas.setValor_despesas_janeiro(totais_despesas_janeiro);
        linha_totais_despesas.setValor_despesas_fevereiro(totais_despesas_fevereiro);
        linha_totais_despesas.setValor_despesas_marco(totais_despesas_marco);
        linha_totais_despesas.setValor_despesas_abril(totais_despesas_abril);
        linha_totais_despesas.setValor_despesas_maio(totais_despesas_maio);
        linha_totais_despesas.setValor_despesas_junho(totais_despesas_junho);
        linha_totais_despesas.setValor_despesas_julho(totais_despesas_julho);
        linha_totais_despesas.setValor_despesas_agosto(totais_despesas_agosto);
        linha_totais_despesas.setValor_despesas_setembro(totais_despesas_setembro);
        linha_totais_despesas.setValor_despesas_outubro(totais_despesas_outubro);
        linha_totais_despesas.setValor_despesas_novembro(totais_despesas_novembro);
        linha_totais_despesas.setValor_despesas_dezembro(totais_despesas_dezembro);
        this.listModelGlobalAgrupadoRegimeParcela.addElement(linha_totais_despesas);
    }

    public void pesquisarDreRp() {
        double saldo_inicial;
        Locale ptBr = new Locale("pt", "BR");
        int ano = -1;
        try {
            ano = Integer.parseInt(this.entAnoFiscalRp.getText());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.isto, "Ano Fiscal Inv\u00e1lido");
            return;
        }
        CentroCusto cc = (CentroCusto)this.modelCentrosCustosRp.getSelectedItem();
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        int id_cc = -1;
        id_cc = cc != null ? cc.getId_centro_custo() : 0;
        double saldo_inicial_global = saldo_inicial = gerenciar.getSaldoDreSimplesCCRp(ano - 1, id_cc);
        double despesas_total = 0.0;
        double receitas_total = 0.0;
        double lucro_total = 0.0;
        double valor_total = 0.0;
        double lucratividade_total = 0.0;
        this.listModelGlobalRp.clear();
        for (DreSimples not : gerenciar.getDreSimplesCCRp(ano, id_cc)) {
            double despesa = not.getDespesas();
            double receita = not.getReceitas();
            if (despesa < 0.0) {
                despesa = 0.0;
            }
            not.setDespesas(-despesa);
            if (receita < 0.0) {
                receita = 0.0;
            }
            not.setReceitas(receita);
            double total = saldo_inicial + receita - despesa;
            double lucro = receita - despesa;
            double lucratividade = lucro * 100.0 / receita;
            not.setSaldo_inicial(saldo_inicial);
            not.setTotal(total);
            not.setLucro(lucro);
            not.setLucratividade(lucratividade);
            not.setAno(ano);
            saldo_inicial = total;
            this.listModelGlobalRp.addElement(not);
            receitas_total += receita;
            despesas_total += despesa;
        }
        this.lblSaldoInicialTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial_global));
        this.lblReceitasTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(receitas_total));
        this.lblDespesasTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(despesas_total));
        valor_total = saldo_inicial_global + receitas_total - despesas_total;
        this.lblValorTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
        lucro_total = receitas_total - despesas_total;
        this.lblLucroTotalRegp.setText(NumberFormat.getCurrencyInstance(ptBr).format(lucro_total));
        lucratividade_total = lucro_total * 100.0 / receitas_total;
        DecimalFormat df = new DecimalFormat("#,###.00");
        this.lblLucratividadeTotalRegp.setText(String.valueOf(df.format(lucratividade_total)) + "%");
    }

    public void pesquisar_ccs() {
        GerenciarBancoCentroCustos gerenciar = new GerenciarBancoCentroCustos();
        this.modelCentrosCustos.resetar();
        this.modelCentrosCustosRc.resetar();
        this.modelCentrosCustosRp.resetar();
        this.modelCentrosCustosAgrupadoRegimeLancamento.resetar();
        this.modelCentrosCustosAgrupadoRegimeParcela.resetar();
        CentroCusto todos = new CentroCusto();
        todos.setId_centro_custo(0);
        todos.setNome_centro_custo("TODOS");
        this.modelCentrosCustos.addCC(todos);
        this.modelCentrosCustosRc.addCC(todos);
        this.modelCentrosCustosRp.addCC(todos);
        this.modelCentrosCustosAgrupadoRegimeLancamento.addCC(todos);
        this.modelCentrosCustosAgrupadoRegimeParcela.addCC(todos);
        ArrayList<CentroCusto> lista_ccs = gerenciar.getCentroCustos();
        for (CentroCusto cc : lista_ccs) {
            this.modelCentrosCustos.addCC(cc);
            this.modelCentrosCustosRc.addCC(cc);
            this.modelCentrosCustosRp.addCC(cc);
            this.modelCentrosCustosAgrupadoRegimeLancamento.addCC(cc);
            this.modelCentrosCustosAgrupadoRegimeParcela.addCC(cc);
        }
    }

    public void pesquisar_ibs() {
        GerenciarBancoInstituicaoBancaria gerenciar = new GerenciarBancoInstituicaoBancaria();
        this.modelIBRc.resetar();
        InstituicaoBancaria todos = new InstituicaoBancaria();
        todos.setId_instituicao_bancaria(0);
        todos.setNome_instituicao_bancaria("TODOS");
        this.modelIBRc.addCC(todos);
        ArrayList<InstituicaoBancaria> lista_ibs = gerenciar.getInstituicoesBancariasMaisRapido();
        for (InstituicaoBancaria ib : lista_ibs) {
            this.modelIBRc.addCC(ib);
        }
    }

    public void atualizarGrafico() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    XYSeriesCollection dataset = new XYSeriesCollection();
                    TelaFinanceiroRelatorios.this.painelGraficoLinha.removeAll();
                    int contador = TelaFinanceiroRelatorios.this.listModelGlobalRc.getSize();
                    ArrayList<DreSimples> dreList = new ArrayList<DreSimples>();
                    int i = 0;
                    while (i < contador) {
                        dreList.add(TelaFinanceiroRelatorios.this.listModelGlobalRc.get(i));
                        ++i;
                    }
                    HashMap<Integer, Double> lista_pagamentos_despesas = new HashMap<Integer, Double>();
                    HashMap<Integer, Double> lista_pagamentos_receitas = new HashMap<Integer, Double>();
                    int mes = 1;
                    for (DreSimples dre : dreList) {
                        try {
                            lista_pagamentos_despesas.put(mes, -dre.getDespesas());
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        try {
                            lista_pagamentos_receitas.put(mes, dre.getReceitas());
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        ++mes;
                    }
                   // TreeMap despesas = new TreeMap();
                   
					Map<Integer, Double> despesas = new TreeMap<>(lista_pagamentos_despesas);
                    XYSeries series1 = new XYSeries((Comparable)((Object)"DESPESAS"));

                    
                    for (Map.Entry<Integer, Double> pair : despesas.entrySet()) {

                        series1.add((Number)pair.getKey(), (Number)pair.getValue());

					}
                    
               
                    
					Map<Integer, Double> receitas = new TreeMap<>(lista_pagamentos_receitas);
                    XYSeries series2 = new XYSeries((Comparable)((Object)"Receitas"));
                    for (Map.Entry pair : receitas.entrySet()) {
                        series2.add((Number)pair.getKey(), (Number)pair.getValue());
                    }
                    dataset.addSeries(series1);
                    dataset.addSeries(series2);
                    GraficoMultiplaLinha linhaMultiplca = new GraficoMultiplaLinha();
                    linhaMultiplca.setDataset((XYDataset)dataset);
                    linhaMultiplca.setAplicarSimbolos(true);
                    TelaFinanceiroRelatorios.this.chartPanel = linhaMultiplca.getGraficoLinha(TelaFinanceiroRelatorios.this.painelGraficoLinha.getWidth(), TelaFinanceiroRelatorios.this.painelGraficoLinha.getHeight(), "M\u00eas", "Despesas x Receitas", "Valor em Reais", 3);
                    TelaFinanceiroRelatorios.this.chartPanel.setBackground(Color.white);
                    TelaFinanceiroRelatorios.this.painelGraficoLinha.add((Component)TelaFinanceiroRelatorios.this.chartPanel);
                    TelaFinanceiroRelatorios.this.painelGraficoLinha.repaint();
                    TelaFinanceiroRelatorios.this.painelGraficoLinha.updateUI();
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(TelaFinanceiroRelatorios.this.isto, "Ano Incorreto");
                }
            }
        });
    }

    public static String NomeDoMes(int i, int tipo) {
        String[] mes = new String[]{"janeiro", "fevereiro", "mar\u00e7o", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};
        if (tipo == 0) {
            return mes[i - 1];
        }
        return mes[i - 1].substring(0, 3);
    }
}

