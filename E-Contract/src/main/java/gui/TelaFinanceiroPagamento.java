/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.miginfocom.swing.MigLayout
 */
package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.gui.TelaFinanceiroGerenciarLancamento;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.JTextFieldPersonalizado;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEscolhaRelatorioPagamentos;
import net.miginfocom.swing.MigLayout;

public class TelaFinanceiroPagamento
extends JFrame {
    private final JPanel painelPrinciapl = new JPanel();
    private final JPanel painelOdin = new JPanel();
    private TelaFinanceiroPagamento isto;
    private JTable tabela_pagamento;
    private ArrayList<FinanceiroPagamentoCompleto> lista_FinanceiroPagamentoCompletos = new ArrayList();
    private PagamentoTableModel modelo_pagamento = new PagamentoTableModel();
    private JDialog telaPai;
    private TableRowSorter<PagamentoTableModel> sorter;
    private JComboBox cbStatusCondicaoPagamento;
    private JComboBox cbCondicaoPagamento;
    private JComboBox cbExtrato;
    private JComboBox cbFluxoCaixa;
    private JComboBox cbTipoFinanceiroPagamentoCompleto;
    private JTextField entNomePagador;
    private JTextField menorDataPagamento;
    private JTextField maiorDataPagamento;
    private JTextField entIdentificadorGeral;
    private JTextField entNomeRecebedor;
    private JLabel lblNumTotalPagamentos;
    private JLabel entValorTotalPagamentoDespesas;
    private JLabel entValorTotalPagamentoReceitas;
    private JLabel entBalancoNormal;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private JComboBox cbCentroCusto;
    private JRadioButton filtroOu;
    private JRadioButton filtroE;
    private JLabel entValorTotalPagamentoTransferenciaDespesas;
    private JLabel entBalancoTotal;
    private JLabel entBalancoEmprestimo;
    private JLabel entValorTotalPagamentoEmprestimoDespesas;
    private JLabel entValorTotalPagamentoEmprestimoReceitas;
    private JLabel entValorTotalPagamentoTransferenciaReceitas;
    private JLabel entBalancoTransferencia;
    private JLabel entTotalDespesas;
    private JLabel entTotalReceitas;
    private JTextField entAno;

    public TelaFinanceiroPagamento(int flag_modo_operacao, int flag_retorno, Window janela_pai) {
        this.getDadosGlobais();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        System.out.println("Screen width = " + d.width);
        System.out.println("Screen height = " + d.height);
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = scrnSize.height - winSize.height;
        System.out.printf("Altura: %d\n", taskBarHeight);
        this.isto = this;
        this.setResizable(true);
        DadosGlobais dados = DadosGlobais.getInstance();
        DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        int display_x = display.getWidth();
        int display_y = display.getHeight();
        this.setBounds(0, 0, d.width, d.height - taskBarHeight);
        this.painelPrinciapl.setBackground(Color.WHITE);
        this.painelOdin.setLayout((LayoutManager)new MigLayout("", "[][grow][]", "[][100px][grow][][][]"));
        this.painelPrinciapl.setLayout((LayoutManager)new MigLayout("", "[][grow][]", "[][100px][grow][][][]"));
        this.painelOdin.add((Component)this.painelPrinciapl, "cell 0 0 3 1,grow");
        JScrollPane scrollPaneOdin = new JScrollPane(this.painelOdin);
        this.setContentPane(scrollPaneOdin);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 255));
        this.painelPrinciapl.add((Component)panel, "cell 0 0 3 1,grow");
        panel.setLayout((LayoutManager)new MigLayout("", "[269px][]", "[49px]"));
        JLabel lblNewLabel = new JLabel("Pagamentos");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", 1, 40));
        panel.add((Component)lblNewLabel, "cell 0 0,alignx left,aligny top");
        JPanel panel_1 = new JPanel();
        this.painelPrinciapl.add((Component)panel_1, "cell 0 1 3 1,alignx center,aligny top");
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout((LayoutManager)new MigLayout("", "[116px][grow][][140px][::350px][][19px,grow][][][][141px]", "[28px][33px][33px][][grow]"));
        JLabel lblNewLabel_1_1 = new JLabel("Tipo de Lan\u00e7amento:");
        lblNewLabel_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1, "cell 0 0,alignx left,aligny center");
        this.cbTipoFinanceiroPagamentoCompleto = new JComboBox();
        this.cbTipoFinanceiroPagamentoCompleto.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbTipoFinanceiroPagamentoCompleto, "cell 1 0,growx,aligny center");
        this.cbTipoFinanceiroPagamentoCompleto.addItem("TODOS");
        this.cbTipoFinanceiroPagamentoCompleto.addItem("DESPESAS");
        this.cbTipoFinanceiroPagamentoCompleto.addItem("RECEITAS");
        this.cbTipoFinanceiroPagamentoCompleto.addItem("EMPRESTIMOS MUTUADOS");
        this.cbTipoFinanceiroPagamentoCompleto.addItem("EMPRESTIMOS TOMADOS");
        this.cbTipoFinanceiroPagamentoCompleto.addItem("TRANSFERENCIAS");
        JLabel lblNewLabel_1_1_3 = new JLabel("Condi\u00e7\u00e3o do Pagamento:");
        lblNewLabel_1_1_3.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_3, "cell 3 0,alignx left,aligny center");
        this.cbCondicaoPagamento = new JComboBox();
        this.cbCondicaoPagamento.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbCondicaoPagamento, "cell 4 0 2 1,growx,aligny center");
        JLabel lblNewLabel_1_1_4_1_1_1 = new JLabel("Per\u00edodo de Pagamento:");
        panel_1.add((Component)lblNewLabel_1_1_4_1_1_1, "cell 6 0 5 1,alignx center,aligny center");
        lblNewLabel_1_1_4_1_1_1.setFont(new Font("SansSerif", 0, 16));
        JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Pagador:");
        lblNewLabel_1_1_2_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1, "cell 0 1,alignx right,aligny center");
        this.entNomePagador = new JTextField();
        this.entNomePagador.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.entNomePagador, "cell 1 1,growx,aligny bottom");
        this.entNomePagador.setColumns(10);
        this.filtroE = new JRadioButton("E");
        this.filtroE.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TelaFinanceiroPagamento.this.filtroE.setSelected(true);
                TelaFinanceiroPagamento.this.filtroOu.setSelected(false);
            }
        });
        this.filtroE.setSelected(true);
        this.filtroE.setFont(new Font("SansSerif", 1, 16));
        panel_1.add((Component)this.filtroE, "cell 2 1");
        JLabel lblNewLabel_1_1_4_2_1 = new JLabel("Status Pagamento:");
        lblNewLabel_1_1_4_2_1.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_4_2_1, "cell 3 1,alignx right,aligny center");
        this.cbStatusCondicaoPagamento = new JComboBox();
        this.cbStatusCondicaoPagamento.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbStatusCondicaoPagamento, "cell 4 1 2 1,growx,aligny center");
        this.cbStatusCondicaoPagamento.addItem("TODOS");
        this.cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
        this.cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Conclu\u00eddo");
        JLabel lblNewLabel_8_1_1 = new JLabel("De");
        panel_1.add((Component)lblNewLabel_8_1_1, "cell 6 1,alignx left,aligny center");
        lblNewLabel_8_1_1.setFont(new Font("SansSerif", 1, 14));
        this.menorDataPagamento = new JTextField();
        this.menorDataPagamento.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.menorDataPagamento, "cell 7 1 2 1,alignx left,aligny top");
        this.menorDataPagamento.setText(null);
        this.menorDataPagamento.setColumns(10);
        this.menorDataPagamento.setText(this.pegarDataMenos(1));
        JLabel lblNewLabel_6_1_1 = new JLabel("a");
        panel_1.add((Component)lblNewLabel_6_1_1, "cell 9 1,alignx left,aligny center");
        lblNewLabel_6_1_1.setFont(new Font("SansSerif", 1, 14));
        this.maiorDataPagamento = new JTextField();
        this.maiorDataPagamento.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.maiorDataPagamento, "cell 10 1,alignx left,aligny top");
        this.maiorDataPagamento.setText(null);
        this.maiorDataPagamento.setColumns(10);
        this.maiorDataPagamento.setText(this.pegarDataMais(1));
        JLabel lblNewLabel_1_1_2_1_1_1 = new JLabel("Recebedor:");
        lblNewLabel_1_1_2_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_1, "cell 0 2,alignx right,aligny center");
        this.entNomeRecebedor = new JTextField();
        this.entNomeRecebedor.setFont(new Font("Arial", 1, 16));
        this.entNomeRecebedor.setColumns(10);
        panel_1.add((Component)this.entNomeRecebedor, "cell 1 2,growx,aligny bottom");
        this.filtroOu = new JRadioButton("OU");
        this.filtroOu.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.filtroOu.setSelected(true);
                TelaFinanceiroPagamento.this.filtroE.setSelected(false);
            }
        });
        this.filtroOu.setFont(new Font("SansSerif", 1, 16));
        panel_1.add((Component)this.filtroOu, "cell 2 2");
        JLabel lblNewLabel_1_1_2_1_1_2 = new JLabel("Identificador Geral:");
        lblNewLabel_1_1_2_1_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_2, "cell 3 2,alignx right,aligny center");
        this.entIdentificadorGeral = new JTextField();
        this.entIdentificadorGeral.setFont(new Font("Arial", 1, 16));
        this.entIdentificadorGeral.setColumns(10);
        panel_1.add((Component)this.entIdentificadorGeral, "cell 4 2,growx,aligny bottom");
        JButton btnFiltar = new JButton("Filtrar");
        btnFiltar.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)btnFiltar, "cell 7 2 2 1,growx,aligny top");
        btnFiltar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.filtrar();
            }
        });
        btnFiltar.setBackground(new Color(0, 51, 0));
        btnFiltar.setForeground(Color.WHITE);
        JButton btnNewButton = new JButton("pesquisar");
        btnNewButton.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)btnNewButton, "cell 10 2,growx,aligny top");
        btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.pesquisar();
            }
        });
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(0, 0, 102));
        JLabel lblNewLabel_1_1_2_1_1_1_1 = new JLabel("Extrato?:");
        lblNewLabel_1_1_2_1_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_1_1, "cell 0 3,alignx trailing");
        this.cbExtrato = new JComboBox();
        this.cbExtrato.addItem("TODOS");
        this.cbExtrato.addItem("SIM");
        this.cbExtrato.addItem("N\u00c3O");
        this.cbExtrato.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbExtrato, "cell 1 3,growx");
        JLabel lblNewLabel_1_1_2_1_1_1_1_1 = new JLabel("Fluxo de Caixa?:");
        lblNewLabel_1_1_2_1_1_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_1_1_1, "cell 3 3,alignx trailing");
        this.cbFluxoCaixa = new JComboBox();
        this.cbFluxoCaixa.addItem("TODOS");
        this.cbFluxoCaixa.addItem("SIM");
        this.cbFluxoCaixa.addItem("N\u00c3O");
        this.cbFluxoCaixa.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbFluxoCaixa, "cell 4 3 2 1,growx");
        JButton btnLimparCampos = new JButton("Limpar Campos");
        btnLimparCampos.setFont(new Font("SansSerif", 0, 16));
        btnLimparCampos.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.sorter.setRowFilter(RowFilter.regexFilter("", new int[0]));
                TelaFinanceiroPagamento.this.cbCondicaoPagamento.setSelectedIndex(0);
                TelaFinanceiroPagamento.this.cbTipoFinanceiroPagamentoCompleto.setSelectedIndex(0);
                TelaFinanceiroPagamento.this.entNomePagador.setText("");
                TelaFinanceiroPagamento.this.cbFluxoCaixa.setSelectedIndex(0);
                TelaFinanceiroPagamento.this.cbCentroCusto.setSelectedIndex(0);
                TelaFinanceiroPagamento.this.cbExtrato.setSelectedIndex(0);
                TelaFinanceiroPagamento.this.entNomeRecebedor.setText("");
                TelaFinanceiroPagamento.this.filtroE.setSelected(true);
                TelaFinanceiroPagamento.this.filtroOu.setSelected(false);
                TelaFinanceiroPagamento.this.pegarDatas();
                TelaFinanceiroPagamento.this.calcular();
            }
        });
        JButton btnLimparBusca = new JButton("Limpar Busca");
        btnLimparBusca.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)btnLimparBusca, "cell 7 3 2 1,grow");
        btnLimparBusca.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.limpar();
            }
        });
        btnLimparBusca.setBackground(new Color(204, 0, 0));
        btnLimparBusca.setForeground(Color.WHITE);
        btnLimparBusca.setHorizontalAlignment(4);
        btnLimparCampos.setHorizontalAlignment(4);
        btnLimparCampos.setForeground(Color.WHITE);
        btnLimparCampos.setBackground(new Color(255, 51, 0));
        panel_1.add((Component)btnLimparCampos, "cell 10 3,alignx left,aligny top");
        JLabel lblNewLabel_1_1_2_1_1_1_1_2 = new JLabel("Centro de Custo:");
        lblNewLabel_1_1_2_1_1_1_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_1_1_2, "cell 0 4,alignx trailing");
        this.cbCentroCusto = new JComboBox();
        this.cbCentroCusto.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbCentroCusto, "cell 1 4,growx");
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.WHITE);
        panel_1.add((Component)panel_2, "cell 6 4 5 1,grow");
        panel_2.setLayout((LayoutManager)new MigLayout("", "[grow][grow]", "[][grow][][]"));
        JLabel lblNewLabel_2 = new JLabel("FIltro R\u00e1pido de Datas: Ano:");
        lblNewLabel_2.setFont(new Font("SansSerif", 0, 16));
        panel_2.add((Component)lblNewLabel_2, "cell 0 0,alignx trailing");
        this.entAno = new JTextField();
        panel_2.add((Component)this.entAno, "cell 1 0,growx");
        this.entAno.setColumns(10);
        this.entAno.setText(String.valueOf(new GetData().getAnoAtual()));
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.WHITE);
        panel_2.add((Component)panel_3, "cell 0 1 2 1,grow");
        panel_3.setLayout((LayoutManager)new MigLayout("", "[][][][][][][][]", "[][][]"));
        JButton btnJan = new JButton("Jan");
        btnJan.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("01");
            }
        });
        btnJan.setBackground(new Color(0, 0, 153));
        btnJan.setForeground(Color.WHITE);
        btnJan.setFont(new Font("SansSerif", 1, 12));
        panel_3.add((Component)btnJan, "cell 0 0");
        JButton btnFev = new JButton("Fev");
        btnFev.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("02");
            }
        });
        btnFev.setBackground(new Color(0, 0, 153));
        btnFev.setForeground(Color.WHITE);
        btnFev.setFont(new Font("SansSerif", 1, 12));
        panel_3.add((Component)btnFev, "cell 2 0");
        JButton btnMarc = new JButton("Marc");
        btnMarc.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("03");
            }
        });
        btnMarc.setBackground(new Color(0, 0, 153));
        btnMarc.setForeground(Color.WHITE);
        btnMarc.setFont(new Font("SansSerif", 1, 12));
        panel_3.add((Component)btnMarc, "cell 3 0");
        JButton btnAbr = new JButton("Abr");
        btnAbr.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("04");
            }
        });
        btnAbr.setBackground(new Color(0, 0, 153));
        btnAbr.setForeground(Color.WHITE);
        btnAbr.setFont(new Font("SansSerif", 1, 12));
        panel_3.add((Component)btnAbr, "cell 4 0");
        JButton btnMai = new JButton("Mai");
        btnMai.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("05");
            }
        });
        btnMai.setBackground(new Color(0, 0, 153));
        btnMai.setForeground(Color.WHITE);
        btnMai.setFont(new Font("SansSerif", 1, 12));
        panel_3.add((Component)btnMai, "cell 5 0");
        JButton btnJun = new JButton("Jun");
        btnJun.setBackground(new Color(0, 0, 153));
        btnJun.setForeground(Color.WHITE);
        btnJun.setFont(new Font("SansSerif", 1, 12));
        btnJun.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("06");
            }
        });
        panel_3.add((Component)btnJun, "cell 6 0");
        JButton btnLuj_1 = new JButton("Jul");
        btnLuj_1.setBackground(new Color(0, 0, 153));
        btnLuj_1.setForeground(Color.WHITE);
        btnLuj_1.setFont(new Font("SansSerif", 1, 12));
        btnLuj_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("07");
            }
        });
        panel_3.add((Component)btnLuj_1, "cell 0 1");
        JButton btnAgo = new JButton("Ago");
        btnAgo.setBackground(new Color(0, 0, 153));
        btnAgo.setForeground(Color.WHITE);
        btnAgo.setFont(new Font("SansSerif", 1, 12));
        btnAgo.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("08");
            }
        });
        panel_3.add((Component)btnAgo, "cell 2 1");
        JButton btnSet = new JButton("Set");
        btnSet.setBackground(new Color(0, 0, 153));
        btnSet.setForeground(Color.WHITE);
        btnSet.setFont(new Font("SansSerif", 1, 12));
        btnSet.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("09");
            }
        });
        panel_3.add((Component)btnSet, "cell 3 1");
        JButton btnOut = new JButton("Out");
        btnOut.setBackground(new Color(0, 0, 153));
        btnOut.setForeground(Color.WHITE);
        btnOut.setFont(new Font("SansSerif", 1, 12));
        btnOut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("10");
            }
        });
        panel_3.add((Component)btnOut, "cell 4 1");
        JButton btnNov = new JButton("Nov");
        btnNov.setBackground(new Color(0, 0, 153));
        btnNov.setForeground(Color.WHITE);
        btnNov.setFont(new Font("SansSerif", 1, 12));
        btnNov.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("11");
            }
        });
        panel_3.add((Component)btnNov, "cell 5 1");
        JButton btnDez = new JButton("Dez");
        btnDez.setBackground(new Color(0, 0, 153));
        btnDez.setForeground(Color.WHITE);
        btnDez.setFont(new Font("SansSerif", 1, 12));
        btnDez.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroPagamento.this.usarFiltroDatas("12");
            }
        });
        panel_3.add((Component)btnDez, "cell 6 1");
        this.cbCentroCusto.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    TelaFinanceiroPagamento.this.filtrar();
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        });
        FinanceiroPagamentoCompletosRender renderer = new FinanceiroPagamentoCompletosRender();
        this.tabela_pagamento = new JTable(this.modelo_pagamento);
        this.tabela_pagamento.setDefaultRenderer(Object.class, renderer);
        this.sorter = new TableRowSorter<PagamentoTableModel>(this.modelo_pagamento);
        this.tabela_pagamento.setRowSorter(this.sorter);
        this.tabela_pagamento.setRowHeight(30);
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItemVizualizar = new JMenuItem();
        jMenuItemVizualizar.setText("Vizualizar");
        jMenuItemVizualizar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceDaLinha = TelaFinanceiroPagamento.this.tabela_pagamento.getSelectedRow();
                int indice = TelaFinanceiroPagamento.this.tabela_pagamento.getRowSorter().convertRowIndexToModel(indiceDaLinha);
                FinanceiroPagamentoCompleto pag = TelaFinanceiroPagamento.this.modelo_pagamento.getValue(indice);
                String nome_pasta = "lancamento_" + pag.getLancamento().getId_lancamento();
                String nome_arquivo = pag.getFpag().getCaminho_arquivo();
                String unidade_base_dados = TelaFinanceiroPagamento.this.configs_globais.getServidorUnidade();
                String caminho_completo = String.valueOf(unidade_base_dados) + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\" + nome_pasta + "\\documentos\\" + nome_arquivo;
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        File myFile = new File(caminho_completo);
                        desktop.open(myFile);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
        });
        jPopupMenu.add(jMenuItemVizualizar);
        this.tabela_pagamento.setComponentPopupMenu(jPopupMenu);
        JScrollPane scrollPane = new JScrollPane(this.tabela_pagamento);
        this.painelPrinciapl.add((Component)scrollPane, "cell 0 2 3 1,grow");
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        this.painelPrinciapl.add((Component)panel_5, "cell 0 3 3 2,grow");
        panel_5.setLayout((LayoutManager)new MigLayout("", "[189px][39px][189px][][87.00px][][][][][][][][][][][][][][][][][][]", "[][][18px][][][][][][]"));
        JButton btnAbrirLancamento = new JButton("Abrir");
        btnAbrirLancamento.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Lancamento> lancamentos_selecionados = TelaFinanceiroPagamento.this.getLancamentosSelecionado();
                if (lancamentos_selecionados.size() == 1) {
                    Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
                    TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar, TelaFinanceiroPagamento.this.isto);
                    tela.setVisible(true);
                }
            }
        });
        JLabel lblNewLabel_1_3 = new JLabel("N\u00fam Total Pagamentos:");
        lblNewLabel_1_3.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_3, "cell 0 0,alignx right");
        this.lblNumTotalPagamentos = new JLabel("");
        this.lblNumTotalPagamentos.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.lblNumTotalPagamentos, "cell 1 0");
        JButton btnNewButton_1 = new JButton("Exportar");
        btnNewButton_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<FinanceiroPagamentoCompleto> pagamentos_selecionados = new ArrayList<FinanceiroPagamentoCompleto>();
                int[] linhas_selecionadas = TelaFinanceiroPagamento.this.tabela_pagamento.getSelectedRows();
                int i = 0;
                while (i < linhas_selecionadas.length) {
                    int indice = linhas_selecionadas[i];
                    int indexRowModel = TelaFinanceiroPagamento.this.tabela_pagamento.getRowSorter().convertRowIndexToModel(indice);
                    FinanceiroPagamentoCompleto pagamento = TelaFinanceiroPagamento.this.modelo_pagamento.getValue(indexRowModel);
                    pagamentos_selecionados.add(pagamento);
                    ++i;
                }
                TelaEscolhaRelatorioPagamentos escolha_opcoes = new TelaEscolhaRelatorioPagamentos(pagamentos_selecionados, TelaFinanceiroPagamento.this.isto);
                escolha_opcoes.setVisible(true);
            }
        });
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setBackground(new Color(0, 0, 102));
        btnNewButton_1.setFont(new Font("SansSerif", 1, 16));
        panel_5.add((Component)btnNewButton_1, "cell 10 0 5 1,alignx center,growy");
        btnAbrirLancamento.setForeground(Color.WHITE);
        btnAbrirLancamento.setFont(new Font("SansSerif", 1, 16));
        btnAbrirLancamento.setBackground(new Color(0, 51, 0));
        panel_5.add((Component)btnAbrirLancamento, "cell 15 0 4 2,grow");
        JLabel lblNewLabel_1 = new JLabel("Valor Total Pagamento Despesas:");
        lblNewLabel_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1, "cell 0 2 1 2,alignx right,growy");
        this.entValorTotalPagamentoDespesas = new JLabel("R$ 100.000.000,00");
        this.entValorTotalPagamentoDespesas.setForeground(new Color(153, 0, 0));
        this.entValorTotalPagamentoDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoDespesas, "cell 1 2 1 2,alignx center,aligny bottom");
        JLabel lblNewLabel_1_2 = new JLabel("   Valor Total Pagamento Receitas:");
        lblNewLabel_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2, "cell 2 2 1 2,alignx right,growy");
        this.entValorTotalPagamentoReceitas = new JLabel("R$ 100.000.000,00");
        this.entValorTotalPagamentoReceitas.setForeground(new Color(0, 51, 0));
        this.entValorTotalPagamentoReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoReceitas, "cell 3 2 1 2");
        JLabel lblNewLabel_1_2_1 = new JLabel("Balan\u00e7o Normal:");
        lblNewLabel_1_2_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1, "cell 4 2 1 2,alignx right,growy");
        this.entBalancoNormal = new JLabel("R$ 100.000.000,00");
        this.entBalancoNormal.setForeground(new Color(0, 153, 0));
        this.entBalancoNormal.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalancoNormal, "cell 5 2 1 2");
        JLabel lblNewLabel_1_4 = new JLabel("Valor Total Pagamento Transfer\u00eancia(Despesas):");
        lblNewLabel_1_4.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4, "cell 0 4 1 2,alignx right");
        this.entValorTotalPagamentoTransferenciaDespesas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoTransferenciaDespesas.setForeground(new Color(0, 0, 153));
        this.entValorTotalPagamentoTransferenciaDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoTransferenciaDespesas, "cell 1 4 1 2");
        JLabel lblNewLabel_1_4_2 = new JLabel("Valor Total Pagamento Transfer\u00eancia(Receitas):");
        lblNewLabel_1_4_2.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_2, "cell 2 5");
        this.entValorTotalPagamentoTransferenciaReceitas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoTransferenciaReceitas.setForeground(new Color(0, 51, 0));
        this.entValorTotalPagamentoTransferenciaReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoTransferenciaReceitas, "cell 3 5");
        JLabel lblNewLabel_1_2_1_2 = new JLabel("Balan\u00e7o Transf\u00earencias:");
        lblNewLabel_1_2_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1_2, "cell 4 5,alignx right");
        this.entBalancoTransferencia = new JLabel("R$\u00a00,00");
        this.entBalancoTransferencia.setForeground(new Color(0, 153, 0));
        this.entBalancoTransferencia.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalancoTransferencia, "cell 5 5");
        JLabel lblNewLabel_1_4_1 = new JLabel("Valor Total Pagamento Empr\u00e9stimo(Despesas):");
        lblNewLabel_1_4_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1, "cell 0 6,alignx right");
        this.entValorTotalPagamentoEmprestimoDespesas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoEmprestimoDespesas.setForeground(new Color(102, 0, 0));
        this.entValorTotalPagamentoEmprestimoDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoEmprestimoDespesas, "cell 1 6");
        JLabel lblNewLabel_1_4_1_1 = new JLabel("Valor Total Pagamento Empr\u00e9stimo(Receita):");
        lblNewLabel_1_4_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1_1, "cell 2 6");
        this.entValorTotalPagamentoEmprestimoReceitas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoEmprestimoReceitas.setForeground(new Color(0, 51, 0));
        this.entValorTotalPagamentoEmprestimoReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoEmprestimoReceitas, "cell 3 6");
        JLabel lblNewLabel_1_2_1_1 = new JLabel("Balan\u00e7o Empr\u00e9stimos:");
        lblNewLabel_1_2_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1_1, "cell 4 6,alignx right");
        this.entBalancoEmprestimo = new JLabel("R$\u00a00,00");
        this.entBalancoEmprestimo.setForeground(new Color(0, 153, 0));
        this.entBalancoEmprestimo.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalancoEmprestimo, "cell 5 6");
        JLabel lblNewLabel_1_4_1_2 = new JLabel("Total Despesas:");
        lblNewLabel_1_4_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1_2, "cell 0 8,alignx right");
        this.entTotalDespesas = new JLabel("R$\u00a00,00");
        this.entTotalDespesas.setForeground(new Color(102, 0, 0));
        this.entTotalDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entTotalDespesas, "cell 1 8");
        JLabel lblNewLabel_1_4_1_2_1 = new JLabel("Total Receitas:");
        lblNewLabel_1_4_1_2_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1_2_1, "cell 2 8,alignx right");
        this.entTotalReceitas = new JLabel("R$\u00a00,00");
        this.entTotalReceitas.setForeground(new Color(0, 51, 0));
        this.entTotalReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entTotalReceitas, "cell 3 8");
        JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Balan\u00e7o Total:");
        lblNewLabel_1_2_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1_1_1, "cell 4 8,alignx right");
        this.entBalancoTotal = new JLabel("R$\u00a00,00");
        this.entBalancoTotal.setForeground(new Color(0, 153, 0));
        this.entBalancoTotal.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalancoTotal, "cell 5 8");
        this.popular_condicao_pagamento();
        this.popular_centros_custo();
        boolean pegar_datas = true;
        if (pegar_datas) {
            this.pegarDatas();
        }
        this.pesquisar();
        this.limpar();
        this.calcular();
        this.setExtendedState(6);
        this.setResizable(true);
        this.setLocationRelativeTo(janela_pai);
    }

    public void limpar() {
        this.sorter.setRowFilter(RowFilter.regexFilter("", new int[0]));
        this.calcular();
    }

    public void pegarDatas() { 
    	
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        Map<String, String> datas = new HashMap();
        datas = gerenciar.pegarDatasPagamento();
        this.menorDataPagamento.setText((String)datas.get("menor_data_pagamento"));
        this.maiorDataPagamento.setText((String)datas.get("maior_data_pagamento"));
    }

    public void popular_centros_custo() {
        ArrayList<CentroCusto> lista_centro_custos = new GerenciarBancoCentroCustos().getCentroCustos();
        this.cbCentroCusto.removeAllItems();
        this.cbCentroCusto.addItem("TODOS");
        for (CentroCusto cc : lista_centro_custos) {
            this.cbCentroCusto.addItem(cc.getNome_centro_custo());
        }
    }

    public void pesquisar() {
        System.out.println("Pesquisa Iniciada...");
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimos = new GerenciarBancoFinanceiroPagamentoEmprestimo();
        this.lista_FinanceiroPagamentoCompletos.clear();
        this.modelo_pagamento.onRemoveAll();
        this.lista_FinanceiroPagamentoCompletos = gerenciar.getTodosFinanceiroPagamentos();
        System.out.println("Lista de Pagamentos Pesquisada");
        this.modelo_pagamento.onAddAll(this.lista_FinanceiroPagamentoCompletos);
        System.out.println("Modelo de Pagamentos Preenchida");
        ArrayList<FinanceiroPagamentoCompleto> lista_FinanceiroPagamentoCompletosEmprestimo = gerenciar_emprestimos.getTodosFinanceiroPagamentosEmprestimos();
        System.out.println("Lista de Pagamentos de Emprestimo Pesquisada");
        this.modelo_pagamento.onAddAll(lista_FinanceiroPagamentoCompletosEmprestimo);
        System.out.println("Modelo de Pagamentos Com Emprestimos Preenchida");
        System.out.println("Pesquisa Finalizada...");
        this.calcular();
    }

    public void filtrar() {
        ArrayList todos_filtros = new ArrayList(2);
        ArrayList demais_filters = new ArrayList(2);
        ArrayList filtros_pagador_recebedor = new ArrayList(2);
        String data_inicial_filtrar_data_FinanceiroPagamentoCompleto = this.menorDataPagamento.getText().replace(" ", "");
        String data_final_filtrar_data_FinanceiroPagamentoCompleto = this.maiorDataPagamento.getText().replace(" ", "");
        if (this.checkString(data_inicial_filtrar_data_FinanceiroPagamentoCompleto) && this.checkString(data_final_filtrar_data_FinanceiroPagamentoCompleto)) {
            Date data_menor = null;
            Date data_maior = null;
            try {
                data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_FinanceiroPagamentoCompleto);
                data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_FinanceiroPagamentoCompleto);
            }
            catch (ParseException i) {
                i.printStackTrace();
            }
            HashSet datas = new HashSet();
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 6));
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 6));
            demais_filters.add(RowFilter.orFilter(datas));
            HashSet datas_maior = new HashSet();
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 6));
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 6));
            demais_filters.add(RowFilter.orFilter(datas_maior));
        }
        try {
            String s_centro_custo = "";
            if (this.checkString(this.cbCentroCusto.getSelectedItem().toString()) && !(s_centro_custo = this.cbCentroCusto.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                demais_filters.add(RowFilter.regexFilter(s_centro_custo, 3));
            }
        }
        catch (Exception s_centro_custo) {
            // empty catch block
        }
        if (this.cbTipoFinanceiroPagamentoCompleto.getSelectedItem().toString() != null) {
            String s_tipo_FinanceiroPagamentoCompleto = "";
            if (this.checkString(this.cbTipoFinanceiroPagamentoCompleto.getSelectedItem().toString()) && !(s_tipo_FinanceiroPagamentoCompleto = this.cbTipoFinanceiroPagamentoCompleto.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                demais_filters.add(RowFilter.regexFilter(s_tipo_FinanceiroPagamentoCompleto, 1));
            }
        }
        if (this.entIdentificadorGeral.getText() != null) {
            String s_id_geral = "";
            if (this.checkString(this.entIdentificadorGeral.getText())) {
                s_id_geral = this.entIdentificadorGeral.getText().toUpperCase();
                demais_filters.add(RowFilter.regexFilter(s_id_geral, 2));
            }
        }
        if (this.cbCondicaoPagamento.getSelectedItem().toString() != null) {
            String s_condicao = "";
            if (this.checkString(this.cbCondicaoPagamento.getSelectedItem().toString()) && !(s_condicao = this.cbCondicaoPagamento.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                demais_filters.add(RowFilter.regexFilter(s_condicao, 8));
            }
        }
        if (this.cbStatusCondicaoPagamento.getSelectedItem().toString() != null) {
            String s_status_condicao = "";
            if (this.checkString(this.cbStatusCondicaoPagamento.getSelectedItem().toString()) && !(s_status_condicao = this.cbStatusCondicaoPagamento.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                demais_filters.add(RowFilter.regexFilter(s_status_condicao, 9));
            }
        }
        if (this.cbExtrato.getSelectedItem().toString() != null) {
            String s_extrato = "";
            if (this.checkString(this.cbExtrato.getSelectedItem().toString()) && !(s_extrato = this.cbExtrato.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                demais_filters.add(RowFilter.regexFilter(s_extrato, 10));
            }
        }
        if (this.cbFluxoCaixa.getSelectedItem().toString() != null) {
            String s_fluxo = "";
            if (this.checkString(this.cbFluxoCaixa.getSelectedItem().toString()) && !(s_fluxo = this.cbFluxoCaixa.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                demais_filters.add(RowFilter.regexFilter(s_fluxo, 11));
            }
        }
        if (this.entNomePagador.getText() != null) {
            String s_nome_pagador = "";
            if (this.checkString(this.entNomePagador.getText())) {
                s_nome_pagador = this.entNomePagador.getText().toUpperCase();
                filtros_pagador_recebedor.add(RowFilter.regexFilter(s_nome_pagador, 4));
            }
        }
        if (this.entNomeRecebedor.getText() != null) {
            String s_nome_recebedor = "";
            if (this.checkString(this.entNomeRecebedor.getText())) {
                s_nome_recebedor = this.entNomeRecebedor.getText().toUpperCase();
                filtros_pagador_recebedor.add(RowFilter.regexFilter(s_nome_recebedor, 5));
            }
        }
        if (this.filtroE.isSelected()) {
            todos_filtros.add(RowFilter.andFilter(filtros_pagador_recebedor));
        } else {
            todos_filtros.add(RowFilter.orFilter(filtros_pagador_recebedor));
        }
        todos_filtros.add(RowFilter.andFilter(demais_filters));
        this.sorter.setRowFilter(RowFilter.andFilter(todos_filtros));
        this.calcular();
    }

    public void calcular() {
        BigDecimal valor_total_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_receitas = BigDecimal.ZERO;
        BigDecimal balanco = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_transferencias_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_transferencias_receitas = BigDecimal.ZERO;
        BigDecimal balanco_transferencia = BigDecimal.ZERO;
        BigDecimal balanco_emprestimo = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_emprestimo_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_emprestimo_receitas = BigDecimal.ZERO;
        BigDecimal total_despesas = BigDecimal.ZERO;
        BigDecimal total_receitas = BigDecimal.ZERO;
        BigDecimal balanco_total = BigDecimal.ZERO;
        int num_total_pagamentos = 0;
        int row = 0;
        while (row < this.tabela_pagamento.getRowCount()) {
            int index = this.tabela_pagamento.convertRowIndexToModel(row);
            FinanceiroPagamentoCompleto pag = this.modelo_pagamento.getValue(index);
            ++num_total_pagamentos;
            if (pag.getLancamento().getTipo_lancamento() == 0) {
                valor_total_despesas = valor_total_despesas.add(pag.getFpag().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 1) {
                valor_total_receitas = valor_total_receitas.add(pag.getFpag().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 2) {
                if (this.checkString(this.entNomePagador.getText().toString()) || this.checkString(this.entNomeRecebedor.getText().toString())) {
                    if (this.checkString(this.entNomePagador.getText().toString()) && pag.getNome_pagador().toUpperCase().contains(this.entNomePagador.getText().toUpperCase())) {
                        valor_total_pagamentos_transferencias_despesas = valor_total_pagamentos_transferencias_despesas.add(pag.getFpag().getValor());
                    }
                    if (this.checkString(this.entNomeRecebedor.getText().toString()) && pag.getNome_recebedor().toUpperCase().contains(this.entNomeRecebedor.getText().toUpperCase())) {
                        valor_total_pagamentos_transferencias_receitas = valor_total_pagamentos_transferencias_receitas.add(pag.getFpag().getValor());
                    }
                }
            } else if (pag.getLancamento().getTipo_lancamento() == 3) {
                if (pag.getFpag().getTipo_pagamento() == 1) {
                    valor_total_pagamentos_emprestimo_despesas = valor_total_pagamentos_emprestimo_despesas.add(pag.getFpag().getValor());
                } else {
                    valor_total_pagamentos_emprestimo_receitas = valor_total_pagamentos_emprestimo_receitas.add(pag.getFpag().getValor());
                }
            } else if (pag.getLancamento().getTipo_lancamento() == 4) {
                if (pag.getFpag().getTipo_pagamento() == 1) {
                    valor_total_pagamentos_emprestimo_receitas = valor_total_pagamentos_emprestimo_receitas.add(pag.getFpag().getValor());
                } else {
                    valor_total_pagamentos_emprestimo_despesas = valor_total_pagamentos_emprestimo_despesas.add(pag.getFpag().getValor());
                }
            }
            ++row;
        }
        total_despesas = total_despesas.add(valor_total_despesas).add(valor_total_pagamentos_transferencias_despesas).add(valor_total_pagamentos_emprestimo_despesas);
        total_receitas = total_receitas.add(valor_total_receitas).add(valor_total_pagamentos_transferencias_receitas).add(valor_total_pagamentos_emprestimo_receitas);
        balanco = valor_total_receitas.subtract(valor_total_despesas);
        balanco_emprestimo = valor_total_pagamentos_emprestimo_receitas.subtract(valor_total_pagamentos_emprestimo_despesas);
        balanco_transferencia = valor_total_pagamentos_transferencias_receitas.subtract(valor_total_pagamentos_transferencias_despesas);
        balanco_total = balanco_total.add(balanco);
        balanco_total = balanco_total.add(balanco_emprestimo);
        balanco_total = balanco_total.add(balanco_transferencia);
        Locale ptBr = new Locale("pt", "BR");
        this.lblNumTotalPagamentos.setText(String.valueOf(num_total_pagamentos));
        this.entValorTotalPagamentoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
        this.entValorTotalPagamentoReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
        this.entBalancoNormal.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco));
        this.entValorTotalPagamentoTransferenciaDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_transferencias_despesas));
        this.entValorTotalPagamentoTransferenciaReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_transferencias_receitas));
        this.entBalancoTransferencia.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco_transferencia));
        this.entValorTotalPagamentoEmprestimoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_emprestimo_despesas));
        this.entValorTotalPagamentoEmprestimoReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_emprestimo_receitas));
        this.entBalancoEmprestimo.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco_emprestimo));
        this.entTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(total_despesas));
        this.entTotalReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(total_receitas));
        this.entBalancoTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco_total));
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentoCompletosSelecionado() {
        ArrayList<FinanceiroPagamentoCompleto> FinanceiroPagamentoCompletos_selecionados = new ArrayList<FinanceiroPagamentoCompleto>();
        int[] linhas_selecionadas = this.tabela_pagamento.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_pagamento.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            FinanceiroPagamentoCompleto FinanceiroPagamentoCompleto_selecionado = this.lista_FinanceiroPagamentoCompletos.get(indice);
            FinanceiroPagamentoCompletos_selecionados.add(FinanceiroPagamentoCompleto_selecionado);
            ++i;
        }
        return FinanceiroPagamentoCompletos_selecionados;
    }

    public void setTelaPai(JDialog _telaPai) {
        this.telaPai = _telaPai;
    }

    public void adicionarFocus(Component[] components) {
        Component[] componentArray = components;
        int n = components.length;
        int n2 = 0;
        while (n2 < n) {
            Component c = componentArray[n2];
            if (c instanceof JTextFieldPersonalizado) {
                if (c instanceof JTextFieldPersonalizado) {
                    final JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado)c;
                    caixa_texto.addFocusListener(new FocusAdapter(){

                        @Override
                        public void focusGained(FocusEvent e) {
                            System.out.println("Ganhou focu");
                            caixa_texto.setFocusGained();
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            caixa_texto.setFocusLost();
                        }
                    });
                }
            } else {
                Container novo_container = (Container)c;
                this.adicionarFocus(novo_container.getComponents());
            }
            ++n2;
        }
    }

    public void popular_condicao_pagamento() {
        ArrayList<CondicaoPagamento> lista_condicao_pagamentos = new GerenciarBancoCondicaoPagamentos().getCondicaoPagamentos();
        this.cbCondicaoPagamento.removeAllItems();
        this.cbCondicaoPagamento.addItem("TODOS");
        for (CondicaoPagamento cp : lista_condicao_pagamentos) {
            this.cbCondicaoPagamento.addItem(cp.getNome_condicao_pagamento());
        }
    }

    public String pegarDataHoje() {
        LocalDate hoje = LocalDate.now();
        String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return df.replace(" ", "");
    }

    public String pegarDataMais(int anos) {
        LocalDate hoje = LocalDate.now().plusYears(1L);
        String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return df.replace(" ", "");
    }

    public String pegarDataMenos(int anos) {
        LocalDate hoje = LocalDate.now().minusYears(1L);
        String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return df.replace(" ", "");
    }

    public ArrayList<Lancamento> getLancamentosSelecionado() {
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<Lancamento>();
        int[] linhas_selecionadas = this.tabela_pagamento.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_pagamento.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            Lancamento lancamento_selecionado = this.modelo_pagamento.getValue(indice).getLancamento();
            lancamentos_selecionados.add(lancamento_selecionado);
            ++i;
        }
        return lancamentos_selecionados;
    }

    public void usarFiltroDatas(String mes_opcao) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ano = this.entAno.getText();
            Date data_menor = new SimpleDateFormat("dd/MM/yyyy").parse("01/" + mes_opcao + "/" + ano);
            this.menorDataPagamento.setText(dateFormat.format(data_menor));
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(data_menor);
            int dia = cal.getActualMaximum(5);
            Date data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(dia) + "/" + mes_opcao + "/" + ano);
            this.maiorDataPagamento.setText(dateFormat.format(data_maior));
            this.filtrar();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    class FinanceiroPagamentoCompletosRender
    extends DefaultTableCellRenderer {
        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

        FinanceiroPagamentoCompletosRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ((JLabel)renderer).setOpaque(true);
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            if (value instanceof Date) {
                value = f.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public class PagamentoTableModel
    extends AbstractTableModel {
        private final int id = 0;
        private final int tipo_lancamento = 1;
        private final int identificador_geral = 2;
        private final int centro_custo = 3;
        private final int pagador = 4;
        private final int recebedor = 5;
        private final int data_pagamento = 6;
        private final int valor = 7;
        private final int condicao_pagamento = 8;
        private final int status_condicao_pagamento = 9;
        private final int extrato = 10;
        private final int fluxo = 11;
        List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);
        private final String[] colunas = new String[]{"ID", "Tipo Lan\u00e7amento", "Identificador Geral", "Centro de Custo", "Pagador", "Recebedor", "Data Pagamento", "Valor", "Condi\u00e7\u00f5es de Pagamento", "Status Condi\u00e7\u00e3o de Pagamento", "Extrato", "Fluxo"};
        private final ArrayList<FinanceiroPagamentoCompleto> dados = new ArrayList();
        private GerenciarBancoCondicaoPagamentos gerenciar = null;
        private ArrayList<CondicaoPagamento> lista_condicoes = null;
        Locale ptBr = new Locale("pt", "BR");

        public PagamentoTableModel() {
            this.gerenciar = new GerenciarBancoCondicaoPagamentos();
            this.lista_condicoes = this.gerenciar.getCondicaoPagamentos();
        }

        @Override
        public int getColumnCount() {
            return this.colunas.length;
        }

        @Override
        public int getRowCount() {
            return this.dados.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: {
                    return Integer.class;
                }
                case 1: {
                    return String.class;
                }
                case 2: {
                    return String.class;
                }
                case 3: {
                    return String.class;
                }
                case 4: {
                    return String.class;
                }
                case 5: {
                    return String.class;
                }
                case 6: {
                    return Date.class;
                }
                case 7: {
                    return String.class;
                }
                case 8: {
                    return String.class;
                }
                case 9: {
                    return String.class;
                }
                case 10: {
                    return String.class;
                }
                case 11: {
                    return String.class;
                }
            }
            throw new IndexOutOfBoundsException("Coluna Inv\u00e1lida!!!");
        }

        @Override
        public String getColumnName(int columnIndex) {
            return this.colunas[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            FinanceiroPagamentoCompleto dado = this.dados.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return dado.getFpag().getId_pagamento();
                }
                case 1: {
                    int tipo = dado.getLancamento().getTipo_lancamento();
                    if (tipo == 0) {
                        return "DESPESAS";
                    }
                    if (tipo == 1) {
                        return "RECEITAS";
                    }
                    if (tipo == 2) {
                        return "TRANSFERENCIAS";
                    }
                    if (tipo == 3) {
                        return "EMPRESTIMOS MUTUADOS";
                    }
                    if (tipo == 4) {
                        return "EMPRESTIMOS TOMADOS";
                    }
                }
                case 2: {
                    return dado.getFpag().getIdentificador();
                }
                case 3: {
                    return dado.getLancamento().getNome_centro_custo();
                }
                case 4: {
                    try {
                        return dado.getNome_pagador().toUpperCase();
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 5: {
                    try {
                        return dado.getNome_recebedor().toUpperCase();
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 6: {
                    if (dado.getFpag().getData_pagamento() != null && !dado.getFpag().getData_pagamento().equalsIgnoreCase("")) {
                        try {
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            Date data_pag = formato.parse(dado.getFpag().getData_pagamento());
                            return data_pag;
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                case 7: {
                    return NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getFpag().getValor());
                }
                case 8: {
                    try {
                        String s_condicao = "";
                        int id_condicao_pagamento = dado.getFpag().getId_condicao_pagamento();
                        if (id_condicao_pagamento > 0) {
                            CondicaoPagamento condicao = null;
                            for (CondicaoPagamento cond : this.lista_condicoes) {
                                if (cond.getId_condicao_pagamento() != id_condicao_pagamento) continue;
                                condicao = cond;
                                break;
                            }
                            if (condicao != null) {
                                // empty if block
                            }
                            s_condicao = String.valueOf(s_condicao) + condicao.getNome_condicao_pagamento();
                        }
                        return s_condicao;
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 9: {
                    try {
                        String retorno = "";
                        int id_status = dado.getFpag().getStatus_pagamento();
                        if (id_status == 0) {
                            retorno = String.valueOf(retorno) + "A - Compensar|Realizar|Concluir;";
                        } else if (id_status == 1) {
                            retorno = String.valueOf(retorno) + "Compensado|Realizado|Conclu\u00eddo;";
                        }
                        return retorno;
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 10: {
                    int ext = dado.getFpag().getExtrato();
                    if (ext == 0) {
                        return "N\u00c3O";
                    }
                    if (ext == 1) {
                        return "SIM";
                    }
                }
                case 11: {
                    int flux = dado.getFpag().getFluxo_caixa();
                    if (flux == 0) {
                        return "N\u00c3O";
                    }
                    if (flux != 1) break;
                    return "SIM";
                }
            }
            throw new IndexOutOfBoundsException("Coluna Inv\u00e1lida!!!");
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            FinanceiroPagamentoCompleto ib = this.dados.get(rowIndex);
        }

        public FinanceiroPagamentoCompleto getValue(int rowIndex) {
            return this.dados.get(rowIndex);
        }

        public int indexOf(FinanceiroPagamentoCompleto dado) {
            return this.dados.indexOf(dado);
        }

        public void onAdd(FinanceiroPagamentoCompleto dado) {
            this.dados.add(dado);
            this.fireTableRowsInserted(this.indexOf(dado), this.indexOf(dado));
        }

        public void onAddAll(ArrayList<FinanceiroPagamentoCompleto> dadosIn) {
            this.dados.addAll(dadosIn);
            this.fireTableDataChanged();
        }

        public void onRemove(int rowIndex) {
            this.dados.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void onRemove(FinanceiroPagamentoCompleto dado) {
            int indexBefore = this.indexOf(dado);
            this.dados.remove(dado);
            this.fireTableRowsDeleted(indexBefore, indexBefore);
        }

        public void onRemoveAll() {
            this.dados.clear();
            this.fireTableDataChanged();
        }

        public FinanceiroPagamentoCompleto onGet(int row) {
            return this.dados.get(row);
        }
    }
}

