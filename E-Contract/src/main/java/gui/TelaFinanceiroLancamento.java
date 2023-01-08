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
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.gui.TelaFinanceiroCadastroEmprestimo;
import main.java.gui.TelaFinanceiroCadastroLancamento;
import main.java.gui.TelaFinanceiroCadastroTransferencia;
import main.java.gui.TelaFinanceiroGerenciarLancamento;
import main.java.gui.TelaFinanceiroGerenciarLancamentoMultiplo;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.JTextFieldPersonalizado;
import main.java.views_personalizadas.TelaEscolhaRelatorioLancamentos;
import net.miginfocom.swing.MigLayout;

public class TelaFinanceiroLancamento
extends JFrame {
    private final JPanel painelPrinciapl = new JPanel();
    private final JPanel painelOdin = new JPanel();
    private TelaFinanceiroLancamento isto;
    private JTable tabela_lancamentos;
    private ArrayList<Lancamento> lista_lancamentos = new ArrayList();
    private LancamentoTableModel modelo_lancamentos = new LancamentoTableModel();
    private JDialog telaPai;
    private JLabel lblValorTotalJurosRecebido;
    private JLabel lblValorTotalJurosPago;
    private JLabel lblValorVencerAPagar;
    private JLabel lblValorVencerAReceber;
    private JLabel lblValorPago;
    private JLabel lblValorTotalDespesas;
    private TableRowSorter<LancamentoTableModel> sorter;
    private JComboBox cbStatusLancamento;
    private JComboBox cbStatusCondicaoPagamento;
    private JComboBox cbStatusAoContador;
    private JComboBox cbGrupoConta;
    private JComboBox cbCondicaoPagamento;
    private JComboBox cbTipoLancamento;
    private JComboBox cbConta;
    private JComboBox cbCentroCusto;
    private JComboBox cbInstituicaoBancaria;
    private JTextField entClienteFornecedor;
    private JLabel lblDespesasAPagar;
    private JLabel lblDespesasPago;
    private JLabel lblTotalDespesas;
    private JLabel lblReceitasRecebido;
    private JLabel lblTotalReceitas;
    private JLabel lblReceitasAReceber;
    private JTextField menorDataLancamento;
    private JTextField maiorDataLancamento;
    private JTextField menorDataVencimento;
    private JTextField maiorDataVencimento;
    private JTextField menorDataPagamento;
    private JTextField maiorDataPagamento;
    private JLabel lblValorAReceber;
    private JLabel lblValorRestanteAPagar;
    private JLabel lblValorRecebido;
    private JLabel lblValorReceitas;
    private JComboBox cbPrioridade;
    private JComboBox cbSituacao;
    private JTextField entIdentificadorGeral;
    private JTextField entDestinatarioNF;
    private JTextField entIdLancamento;
    private GerenciarBancoCondicaoPagamentos gerenciar_condicoes = null;
    private ArrayList<CondicaoPagamento> lista_condicoes = null;
    private String id_filtro;
    private int CC_filtro;
    private String cliente_fornecedor_filtro;
    private String entidentificadorGeral_filtro;
    private String entdestinatarioNF_filtro;
    private int cbtipoLancamento_filtro;
    private int cbstatusLancamento_filtro;
    private int cbgrupoConta_filtro;
    private int cbconta_filtro;
    private int cbstatusAoContador_filtro;
    private int cbinstituicaoBancaria_filtro;
    private int cbcondicaoPagamento_filtro;
    private int cbstatusCondicaoPagamento_filtro;
    private int cbprioridade_filtro;
    private int cbsituacao_filtro;
    private String menordataLancamento_filtro;
    private String maiordataLancamento_filtro;
    private String menordataVencimento_filtro;
    private String maiordataVencimento_filtro;
    private String menordataPagamento_filtro;
    private String maiordataPagamento_filtro;

    public TelaFinanceiroLancamento(int flag_modo_operacao, int flag_retorno, Window janela_pai) {
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
        this.painelPrinciapl.setLayout((LayoutManager)new MigLayout("", "[grow][][][][]", "[][][][][grow][][][]"));
        this.painelOdin.setBackground(Color.WHITE);
        this.painelOdin.setLayout((LayoutManager)new MigLayout("", "[grow][][][][]", "[][][][][grow][][][]"));
        this.painelOdin.add((Component)this.painelPrinciapl, "cell 0 0,grow");
        JScrollPane scrollPaneOdin = new JScrollPane(this.painelOdin);
        this.setContentPane(scrollPaneOdin);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 255));
        this.painelPrinciapl.add((Component)panel, "cell 0 0,grow");
        panel.setLayout((LayoutManager)new MigLayout("", "[grow]", "[49px]"));
        JLabel lblNewLabel = new JLabel("Lan\u00e7amentos");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", 1, 40));
        panel.add((Component)lblNewLabel, "cell 0 0,alignx left,aligny top");
        JPanel panel_1 = new JPanel();
        this.painelPrinciapl.add((Component)panel_1, "cell 0 1,alignx center,growy");
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout((LayoutManager)new MigLayout("", "[][][][][][][][]", "[][][][][][grow][][][grow][]"));
        JLabel lblNewLabel_1_1_2_2 = new JLabel("ID Lan\u00e7amento:");
        lblNewLabel_1_1_2_2.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2_2, "cell 0 0,alignx trailing");
        this.entIdLancamento = new JTextField();
        this.entIdLancamento.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                String caracteres = "0987654321\b";
                String texto = TelaFinanceiroLancamento.this.entIdLancamento.getText();
                if (!caracteres.contains(String.valueOf(evt.getKeyChar()))) {
                    evt.consume();
                }
            }
        });
        this.entIdLancamento.setColumns(10);
        panel_1.add((Component)this.entIdLancamento, "cell 1 0,growx");
        JLabel lblNewLabel_1_1 = new JLabel("Tipo:");
        lblNewLabel_1_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1, "cell 2 0,alignx trailing,aligny center");
        this.cbTipoLancamento = new JComboBox();
        this.cbTipoLancamento.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbTipoLancamento, "cell 3 0,growx");
        this.cbTipoLancamento.addItem("TODOS");
        this.cbTipoLancamento.addItem("DESPESAS");
        this.cbTipoLancamento.addItem("RECEITAS");
        this.cbTipoLancamento.addItem("EMPRESTIMOS MUTUADOS");
        this.cbTipoLancamento.addItem("EMPRESTIMOS TOMADOS");
        this.cbTipoLancamento.addItem("TRANSFERENCIAS");
        JLabel lblNewLabel_1_1_2_1 = new JLabel("Institui\u00e7\u00e3o Banc\u00e1ria:");
        lblNewLabel_1_1_2_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2_1, "cell 4 0,alignx trailing");
        this.cbInstituicaoBancaria = new JComboBox();
        panel_1.add((Component)this.cbInstituicaoBancaria, "cell 5 0,growx");
        JLabel lblNewLabel_1_1_3_1 = new JLabel("Situa\u00e7\u00e3o:");
        lblNewLabel_1_1_3_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_3_1, "cell 6 0,alignx right");
        this.cbSituacao = new JComboBox();
        this.cbSituacao.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbSituacao, "cell 7 0,growx");
        this.cbSituacao.addItem("TODOS");
        this.cbSituacao.addItem("Atrazado");
        this.cbSituacao.addItem("Em dias");
        this.cbSituacao.addItem("Datas Invalidas");
        JLabel lblNewLabel_1_1_2 = new JLabel("Centro de Custo:");
        lblNewLabel_1_1_2.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2, "cell 0 1,alignx trailing");
        this.cbCentroCusto = new JComboBox();
        panel_1.add((Component)this.cbCentroCusto, "cell 1 1,growx");
        JLabel lblNewLabel_1_1_4 = new JLabel("Status:");
        lblNewLabel_1_1_4.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_4, "cell 2 1,alignx right");
        this.cbStatusLancamento = new JComboBox();
        this.cbStatusLancamento.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbStatusLancamento, "cell 3 1,growx,aligny top");
        this.cbStatusLancamento.addItem("TODOS");
        this.cbStatusLancamento.addItem("DESPESAS A PAGAR");
        this.cbStatusLancamento.addItem("DESPESAS PAGAS");
        this.cbStatusLancamento.addItem("RECEITAS A RECEBER");
        this.cbStatusLancamento.addItem("RECEITAS RECEBIDAS");
        JLabel lblNewLabel_1_1_3 = new JLabel("Condi\u00e7\u00e3o do Pagamento:");
        lblNewLabel_1_1_3.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_3, "cell 4 1,alignx trailing");
        this.cbCondicaoPagamento = new JComboBox();
        this.cbCondicaoPagamento.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbCondicaoPagamento, "cell 5 1,growx");
        JLabel lblNewLabel_1_1_2_1_1 = new JLabel("Cliente/Fornecedor:");
        lblNewLabel_1_1_2_1_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1, "cell 0 2,alignx trailing");
        this.entClienteFornecedor = new JTextField();
        panel_1.add((Component)this.entClienteFornecedor, "cell 1 2,growx");
        this.entClienteFornecedor.setColumns(10);
        JLabel lblNewLabel_1_1_1 = new JLabel("GP de Contas:");
        lblNewLabel_1_1_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_1, "cell 2 2,alignx right,aligny center");
        this.cbGrupoConta = new JComboBox();
        this.cbGrupoConta.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbGrupoConta, "cell 3 2,grow");
        JLabel lblNewLabel_1_1_4_2_1 = new JLabel("Status Pagamento:");
        lblNewLabel_1_1_4_2_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_4_2_1, "cell 4 2,alignx trailing");
        this.cbStatusCondicaoPagamento = new JComboBox();
        this.cbStatusCondicaoPagamento.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbStatusCondicaoPagamento, "cell 5 2,growx");
        this.cbStatusCondicaoPagamento.addItem("TODOS");
        this.cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
        this.cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Conclu\u00eddo");
        JLabel lblNewLabel_1_1_2_1_1_2 = new JLabel("Identificador Geral:");
        lblNewLabel_1_1_2_1_1_2.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_2, "cell 0 3,alignx trailing");
        this.entIdentificadorGeral = new JTextField();
        this.entIdentificadorGeral.setColumns(10);
        panel_1.add((Component)this.entIdentificadorGeral, "cell 1 3,growx");
        JLabel lblNewLabel_1_1_1_1 = new JLabel("Conta:");
        lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_1_1, "cell 2 3,alignx right,aligny center");
        this.cbConta = new JComboBox();
        this.cbConta.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)this.cbConta, "cell 3 3,growx,aligny center");
        JLabel lblNewLabel_1_1_4_2 = new JLabel("Prioridade:");
        lblNewLabel_1_1_4_2.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_4_2, "cell 4 3,alignx trailing");
        this.cbPrioridade = new JComboBox();
        this.cbPrioridade.addItem("TODOS");
        this.cbPrioridade.addItem("Alta Prioridade - Ainda esta semana");
        this.cbPrioridade.addItem("M\u00e9dia Prioridade - Em menos de 15 dias");
        this.cbPrioridade.addItem("Prioridade Leve - Ainda este m\u00eas");
        this.cbPrioridade.addItem("Baixa Prioridade - Ainda este ano");
        panel_1.add((Component)this.cbPrioridade, "cell 5 3,growx");
        JLabel lblNewLabel_1_1_2_1_1_2_1 = new JLabel("Destinat\u00e1rio NF:");
        lblNewLabel_1_1_2_1_1_2_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_2_1, "cell 0 5,alignx trailing");
        this.entDestinatarioNF = new JTextField();
        this.entDestinatarioNF.setColumns(10);
        panel_1.add((Component)this.entDestinatarioNF, "cell 1 5,growx");
        JLabel lblNewLabel_1_1_2_1_1_1 = new JLabel("Contador:");
        lblNewLabel_1_1_2_1_1_1.setFont(new Font("SansSerif", 0, 12));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_1, "cell 2 5,alignx trailing");
        this.cbStatusAoContador = new JComboBox();
        panel_1.add((Component)this.cbStatusAoContador, "cell 3 5,growx");
        this.cbStatusAoContador.addItem("TODOS");
        this.cbStatusAoContador.addItem("N\u00e3o se aplica".toUpperCase());
        this.cbStatusAoContador.addItem("N\u00e3o Enviado ao contador".toUpperCase());
        this.cbStatusAoContador.addItem("Enviado ao contador".toUpperCase());
        JPanel panel_7 = new JPanel();
        panel_7.setBackground(Color.WHITE);
        panel_1.add((Component)panel_7, "cell 4 5 4 1,grow");
        panel_7.setLayout(new FlowLayout(1, 5, 5));
        JButton btnLimparBusca = new JButton("Limpar Busca");
        panel_7.add(btnLimparBusca);
        btnLimparBusca.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.limpar();
            }
        });
        btnLimparBusca.setBackground(new Color(255, 102, 0));
        btnLimparBusca.setForeground(Color.WHITE);
        btnLimparBusca.setHorizontalAlignment(4);
        JButton btnLimparCampos = new JButton("Limpar Campos");
        panel_7.add(btnLimparCampos);
        btnLimparCampos.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.limpar_campos();
            }
        });
        btnLimparCampos.setHorizontalAlignment(4);
        btnLimparCampos.setForeground(Color.WHITE);
        btnLimparCampos.setBackground(new Color(204, 0, 0));
        JButton btnFiltrarAvanado = new JButton("filtrar avan\u00e7ado");
        panel_7.add(btnFiltrarAvanado);
        btnFiltrarAvanado.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtrarAvancado();
            }
        });
        btnFiltrarAvanado.setForeground(Color.WHITE);
        btnFiltrarAvanado.setBackground(new Color(0, 0, 102));
        JButton btnPesquisaAvanada = new JButton("pesquisa avan\u00e7ada");
        panel_7.add(btnPesquisaAvanada);
        btnPesquisaAvanada.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.pesquisar();
            }
        });
        btnPesquisaAvanada.setForeground(Color.WHITE);
        btnPesquisaAvanada.setBackground(new Color(0, 51, 0));
        LancamentosRender renderer = new LancamentosRender();
        this.tabela_lancamentos = new JTable(this.modelo_lancamentos);
        this.tabela_lancamentos.setDefaultRenderer(Object.class, renderer);
        this.sorter = new TableRowSorter<LancamentoTableModel>(this.modelo_lancamentos);
        JPanel panel_2 = new JPanel();
        this.painelPrinciapl.add((Component)panel_2, "cell 0 2,alignx center");
        panel_2.setBackground(Color.WHITE);
        panel_2.setLayout((LayoutManager)new MigLayout("", "[][][]", "[grow][]"));
        JPanel panel_4 = new JPanel();
        panel_2.add((Component)panel_4, "cell 0 0,grow");
        panel_4.setBackground(Color.WHITE);
        panel_4.setLayout((LayoutManager)new MigLayout("", "[][][][][][][][]", "[]"));
        JLabel lblNewLabel_1_1_4_1 = new JLabel("Per\u00edodo Lan\u00e7amento:");
        panel_4.add((Component)lblNewLabel_1_1_4_1, "cell 0 0");
        lblNewLabel_1_1_4_1.setFont(new Font("SansSerif", 0, 12));
        JLabel lblNewLabel_8 = new JLabel("De");
        lblNewLabel_8.setFont(new Font("SansSerif", 1, 14));
        panel_4.add((Component)lblNewLabel_8, "cell 1 0,alignx trailing");
        this.menorDataLancamento = new JTextField();
        panel_4.add((Component)this.menorDataLancamento, "cell 2 0 3 1,growx");
        this.menorDataLancamento.setColumns(10);
        this.menorDataLancamento.setText(this.pegarDataMenos(1));
        JLabel lblNewLabel_6 = new JLabel("a");
        lblNewLabel_6.setFont(new Font("SansSerif", 1, 14));
        panel_4.add((Component)lblNewLabel_6, "cell 5 0,alignx trailing");
        this.maiorDataLancamento = new JTextField();
        panel_4.add((Component)this.maiorDataLancamento, "cell 6 0 2 1,growx");
        this.maiorDataLancamento.setColumns(10);
        this.maiorDataLancamento.setText(this.pegarDataMais(1));
        JPanel panel_4_1 = new JPanel();
        panel_2.add((Component)panel_4_1, "cell 1 0,grow");
        panel_4_1.setBackground(Color.WHITE);
        panel_4_1.setLayout((LayoutManager)new MigLayout("", "[][][][][]", "[][]"));
        JLabel lblNewLabel_1_1_4_1_1 = new JLabel("Per\u00edodo Vencimento:");
        panel_4_1.add((Component)lblNewLabel_1_1_4_1_1, "cell 0 0");
        lblNewLabel_1_1_4_1_1.setFont(new Font("SansSerif", 0, 12));
        JLabel lblNewLabel_8_1 = new JLabel("De");
        lblNewLabel_8_1.setFont(new Font("SansSerif", 1, 14));
        panel_4_1.add((Component)lblNewLabel_8_1, "cell 1 0,alignx trailing");
        this.menorDataVencimento = new JTextField();
        this.menorDataVencimento.setText(null);
        this.menorDataVencimento.setColumns(10);
        this.menorDataVencimento.setText(this.pegarDataMenos(1));
        panel_4_1.add((Component)this.menorDataVencimento, "cell 2 0,growx");
        JLabel lblNewLabel_6_1 = new JLabel("a");
        lblNewLabel_6_1.setFont(new Font("SansSerif", 1, 14));
        panel_4_1.add((Component)lblNewLabel_6_1, "cell 3 0,alignx trailing");
        this.maiorDataVencimento = new JTextField();
        this.maiorDataVencimento.setText(null);
        this.maiorDataVencimento.setColumns(10);
        this.maiorDataVencimento.setText(this.pegarDataMais(1));
        panel_4_1.add((Component)this.maiorDataVencimento, "cell 4 0,growx");
        JPanel panel_4_1_1 = new JPanel();
        panel_2.add((Component)panel_4_1_1, "cell 2 0,grow");
        panel_4_1_1.setBackground(Color.WHITE);
        panel_4_1_1.setLayout((LayoutManager)new MigLayout("", "[][][][][]", "[]"));
        JLabel lblNewLabel_1_1_4_1_1_1 = new JLabel("Per\u00edodo Pagamento:");
        lblNewLabel_1_1_4_1_1_1.setFont(new Font("SansSerif", 0, 12));
        panel_4_1_1.add((Component)lblNewLabel_1_1_4_1_1_1, "cell 0 0");
        JLabel lblNewLabel_8_1_1 = new JLabel("De");
        lblNewLabel_8_1_1.setFont(new Font("SansSerif", 1, 14));
        panel_4_1_1.add((Component)lblNewLabel_8_1_1, "cell 1 0,alignx trailing");
        this.menorDataPagamento = new JTextField();
        this.menorDataPagamento.setText(null);
        this.menorDataPagamento.setColumns(10);
        this.menorDataPagamento.setText(this.pegarDataMenos(1));
        panel_4_1_1.add((Component)this.menorDataPagamento, "cell 2 0,growx");
        JLabel lblNewLabel_6_1_1 = new JLabel("a");
        lblNewLabel_6_1_1.setFont(new Font("SansSerif", 1, 14));
        panel_4_1_1.add((Component)lblNewLabel_6_1_1, "cell 3 0,alignx trailing");
        this.maiorDataPagamento = new JTextField();
        this.maiorDataPagamento.setText(null);
        this.maiorDataPagamento.setColumns(10);
        this.maiorDataPagamento.setText(this.pegarDataMais(1));
        panel_4_1_1.add((Component)this.maiorDataPagamento, "cell 4 0,growx");
        JPanel panel_6 = new JPanel();
        this.painelPrinciapl.add((Component)panel_6, "cell 0 3,alignx center");
        panel_6.setBackground(new Color(255, 255, 204));
        panel_6.setLayout((LayoutManager)new MigLayout("", "[][][][][][][][][][]", "[][][]"));
        JLabel lblNewLabel_1_1_4_1_2 = new JLabel("Filtros R\u00e1pidos:");
        lblNewLabel_1_1_4_1_2.setFont(new Font("SansSerif", 3, 14));
        panel_6.add((Component)lblNewLabel_1_1_4_1_2, "cell 0 0 1 2");
        JButton btnNewButton_5 = new JButton("Atrazados a uma semana");
        btnNewButton_5.setBackground(new Color(255, 153, 0));
        btnNewButton_5.setForeground(Color.WHITE);
        btnNewButton_5.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaAtrazo(7);
            }
        });
        JLabel lblNewLabel_2 = new JLabel("Despesas:");
        panel_6.add((Component)lblNewLabel_2, "cell 1 0");
        panel_6.add((Component)btnNewButton_5, "cell 2 0");
        JButton btnNewButton_5_1 = new JButton("Atrazados a 15 dias");
        btnNewButton_5_1.setBackground(new Color(255, 51, 0));
        btnNewButton_5_1.setForeground(Color.WHITE);
        btnNewButton_5_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaAtrazo(15);
            }
        });
        panel_6.add((Component)btnNewButton_5_1, "cell 3 0");
        JButton btnNewButton_5_1_1 = new JButton("Atrazados a 1 M\u00eas");
        btnNewButton_5_1_1.setBackground(new Color(204, 0, 0));
        btnNewButton_5_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaAtrazo(30);
            }
        });
        panel_6.add((Component)btnNewButton_5_1_1, "cell 4 0");
        JButton btnNewButton_5_1_1_1 = new JButton("Atrazados > 1 m\u00eas");
        btnNewButton_5_1_1_1.setBackground(new Color(102, 0, 0));
        btnNewButton_5_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaAtrazo(600);
            }
        });
        panel_6.add((Component)btnNewButton_5_1_1_1, "cell 5 0");
        JButton btnNewButton_5_1_1_1_1 = new JButton("Vencem essa semana");
        btnNewButton_5_1_1_1_1.setBackground(new Color(255, 51, 0));
        btnNewButton_5_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaEmDias(7);
            }
        });
        panel_6.add((Component)btnNewButton_5_1_1_1_1, "cell 6 0");
        JButton btnNewButton_5_1_1_1_1_1 = new JButton("Vencem em 15 dias");
        btnNewButton_5_1_1_1_1_1.setBackground(new Color(255, 51, 0));
        btnNewButton_5_1_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaEmDias(15);
            }
        });
        panel_6.add((Component)btnNewButton_5_1_1_1_1_1, "cell 7 0");
        JButton btnNewButton_5_1_1_1_1_1_1 = new JButton("Vencem em 1 m\u00eas");
        btnNewButton_5_1_1_1_1_1_1.setBackground(new Color(255, 51, 0));
        btnNewButton_5_1_1_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaEmDias(30);
            }
        });
        panel_6.add((Component)btnNewButton_5_1_1_1_1_1_1, "cell 8 0");
        JButton btnNewButton_5_1_1_1_1_1_1_1 = new JButton("Vence em > 1 M\u00eas");
        btnNewButton_5_1_1_1_1_1_1_1.setBackground(new Color(255, 51, 0));
        btnNewButton_5_1_1_1_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1_1_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1_1_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoDespesaEmDias(365);
            }
        });
        panel_6.add((Component)btnNewButton_5_1_1_1_1_1_1_1, "cell 9 0");
        JButton btnNewButton_5_2 = new JButton("Vencem essa semana");
        btnNewButton_5_2.setBackground(new Color(0, 153, 0));
        btnNewButton_5_2.setForeground(Color.WHITE);
        btnNewButton_5_2.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaEmDias(7);
            }
        });
        JButton btnNewButton_5_3 = new JButton("Atrazados a uma semana");
        btnNewButton_5_3.setBackground(new Color(51, 255, 153));
        btnNewButton_5_3.setForeground(new Color(0, 0, 0));
        btnNewButton_5_3.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaAtrazo(7);
            }
        });
        JLabel lblNewLabel_2_1 = new JLabel("Receitas:");
        panel_6.add((Component)lblNewLabel_2_1, "cell 1 1,alignx right");
        panel_6.add((Component)btnNewButton_5_3, "cell 2 1");
        JButton btnNewButton_5_3_1 = new JButton("Atrazados a 15 dias");
        btnNewButton_5_3_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaAtrazo(15);
            }
        });
        btnNewButton_5_3_1.setForeground(Color.WHITE);
        btnNewButton_5_3_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_3_1.setBackground(new Color(0, 153, 153));
        panel_6.add((Component)btnNewButton_5_3_1, "cell 3 1");
        JButton btnNewButton_5_3_1_1 = new JButton("Atrazados a 1 M\u00eas");
        btnNewButton_5_3_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaAtrazo(30);
            }
        });
        btnNewButton_5_3_1_1.setForeground(Color.WHITE);
        btnNewButton_5_3_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_3_1_1.setBackground(new Color(0, 153, 102));
        panel_6.add((Component)btnNewButton_5_3_1_1, "cell 4 1");
        JButton btnNewButton_5_3_1_1_1 = new JButton("Atrazados > 1 M\u00eas");
        btnNewButton_5_3_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaAtrazo(365);
            }
        });
        btnNewButton_5_3_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_3_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_3_1_1_1.setBackground(new Color(0, 51, 51));
        panel_6.add((Component)btnNewButton_5_3_1_1_1, "cell 5 1");
        panel_6.add((Component)btnNewButton_5_2, "cell 6 1,growx");
        JButton btnNewButton_5_3_1_1_1_1 = new JButton("Vencem em 15 dias");
        btnNewButton_5_3_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaEmDias(15);
            }
        });
        btnNewButton_5_3_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_3_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_3_1_1_1_1.setBackground(new Color(51, 51, 0));
        panel_6.add((Component)btnNewButton_5_3_1_1_1_1, "cell 7 1");
        JButton btnNewButton_5_3_1_1_1_1_1 = new JButton("Vencem em 1 m\u00eas");
        btnNewButton_5_3_1_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaEmDias(30);
            }
        });
        btnNewButton_5_3_1_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_3_1_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_3_1_1_1_1_1.setBackground(new Color(0, 51, 102));
        panel_6.add((Component)btnNewButton_5_3_1_1_1_1_1, "cell 8 1");
        JButton btnNewButton_5_1_1_1_1_1_1_1_1 = new JButton("Vence em > 1 m\u00eas");
        btnNewButton_5_1_1_1_1_1_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroLancamento.this.filtroRapidoReceitaEmDias(30);
            }
        });
        btnNewButton_5_1_1_1_1_1_1_1_1.setForeground(Color.WHITE);
        btnNewButton_5_1_1_1_1_1_1_1_1.setFont(new Font("SansSerif", 3, 12));
        btnNewButton_5_1_1_1_1_1_1_1_1.setBackground(new Color(0, 51, 0));
        panel_6.add((Component)btnNewButton_5_1_1_1_1_1_1_1_1, "cell 9 1");
        this.tabela_lancamentos.setRowSorter(this.sorter);
        this.tabela_lancamentos.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(this.tabela_lancamentos);
        this.painelPrinciapl.add((Component)scrollPane, "cell 0 4,grow");
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        this.painelPrinciapl.add((Component)panel_5, "cell 0 5,alignx center,growy");
        panel_5.setLayout((LayoutManager)new MigLayout("", "[][][]", "[][]"));
        JPanel painelDespesas = new JPanel();
        panel_5.add((Component)painelDespesas, "cell 0 0");
        painelDespesas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        painelDespesas.setBackground(Color.WHITE);
        painelDespesas.setLayout((LayoutManager)new MigLayout("", "[][109px][8px][91px][grow]", "[16px][20px][20px][][20px][]"));
        JLabel lblNewLabel_1 = new JLabel("Despesas:");
        lblNewLabel_1.setFont(new Font("SansSerif", 3, 12));
        painelDespesas.add((Component)lblNewLabel_1, "cell 0 0 5 1,alignx left,aligny top");
        JLabel lblNewLabel_3_1 = new JLabel("N.\u00ba de Despesas:");
        lblNewLabel_3_1.setFont(new Font("SansSerif", 0, 14));
        painelDespesas.add((Component)lblNewLabel_3_1, "cell 1 1,alignx left,aligny bottom");
        this.lblTotalDespesas = new JLabel("0");
        painelDespesas.add((Component)this.lblTotalDespesas, "cell 2 1,alignx left,aligny top");
        this.lblTotalDespesas.setFont(new Font("SansSerif", 1, 15));
        JLabel lblValorDespesaslbl = new JLabel("Valor Total:");
        lblValorDespesaslbl.setFont(new Font("SansSerif", 0, 14));
        painelDespesas.add((Component)lblValorDespesaslbl, "cell 3 1,alignx right,aligny bottom");
        this.lblValorTotalDespesas = new JLabel("0");
        this.lblValorTotalDespesas.setFont(new Font("SansSerif", 1, 15));
        painelDespesas.add((Component)this.lblValorTotalDespesas, "cell 4 1,alignx left,aligny top");
        JLabel lblNewLabel_5_1 = new JLabel("        ");
        painelDespesas.add((Component)lblNewLabel_5_1, "cell 0 2,alignx right,aligny center");
        lblNewLabel_5_1.setOpaque(true);
        lblNewLabel_5_1.setForeground(Color.BLACK);
        lblNewLabel_5_1.setBackground(Color.ORANGE);
        JLabel lblNewLabel_4 = new JLabel("Pago:");
        painelDespesas.add((Component)lblNewLabel_4, "cell 1 2,alignx left,aligny bottom");
        lblNewLabel_4.setFont(new Font("SansSerif", 0, 14));
        this.lblDespesasPago = new JLabel("0");
        painelDespesas.add((Component)this.lblDespesasPago, "cell 2 2,alignx left,aligny top");
        this.lblDespesasPago.setFont(new Font("SansSerif", 1, 15));
        JLabel lblValorPagolbl = new JLabel("Valor Pago:");
        lblValorPagolbl.setFont(new Font("SansSerif", 0, 14));
        painelDespesas.add((Component)lblValorPagolbl, "cell 3 2,alignx right,aligny bottom");
        this.lblValorPago = new JLabel("0");
        this.lblValorPago.setFont(new Font("SansSerif", 1, 15));
        painelDespesas.add((Component)this.lblValorPago, "cell 4 2,alignx left,aligny top");
        JLabel lblValorJurosPago = new JLabel("Valor Juros Pago:");
        lblValorJurosPago.setFont(new Font("SansSerif", 0, 14));
        painelDespesas.add((Component)lblValorJurosPago, "cell 3 3,alignx right");
        this.lblValorTotalJurosPago = new JLabel("R$\u00a00,00");
        this.lblValorTotalJurosPago.setFont(new Font("SansSerif", 1, 15));
        painelDespesas.add((Component)this.lblValorTotalJurosPago, "cell 4 3");
        JLabel lblNewLabel_5 = new JLabel("        ");
        painelDespesas.add((Component)lblNewLabel_5, "cell 0 4,alignx right,aligny center");
        lblNewLabel_5.setOpaque(true);
        lblNewLabel_5.setBackground(Color.RED);
        lblNewLabel_5.setForeground(Color.BLACK);
        JLabel lblNewLabel_3 = new JLabel("A Pagar:");
        painelDespesas.add((Component)lblNewLabel_3, "cell 1 4,alignx left,aligny bottom");
        lblNewLabel_3.setFont(new Font("SansSerif", 0, 14));
        this.lblDespesasAPagar = new JLabel("0");
        painelDespesas.add((Component)this.lblDespesasAPagar, "cell 2 4,alignx left,aligny top");
        this.lblDespesasAPagar.setFont(new Font("SansSerif", 1, 15));
        JLabel lblValorAPagar_1_1 = new JLabel("Valor a Pagar:");
        lblValorAPagar_1_1.setFont(new Font("SansSerif", 0, 14));
        painelDespesas.add((Component)lblValorAPagar_1_1, "cell 3 4,alignx right,aligny bottom");
        this.lblValorRestanteAPagar = new JLabel("0");
        this.lblValorRestanteAPagar.setFont(new Font("SansSerif", 1, 15));
        painelDespesas.add((Component)this.lblValorRestanteAPagar, "cell 4 4,alignx left,aligny top");
        JLabel lblValorAPagar_1_1_1 = new JLabel("Valor a Vencer:");
        lblValorAPagar_1_1_1.setFont(new Font("SansSerif", 0, 14));
        painelDespesas.add((Component)lblValorAPagar_1_1_1, "cell 3 5,alignx right");
        this.lblValorVencerAPagar = new JLabel("R$\u00a00,00");
        this.lblValorVencerAPagar.setFont(new Font("SansSerif", 1, 15));
        painelDespesas.add((Component)this.lblValorVencerAPagar, "cell 4 5");
        JPanel panelReceitas = new JPanel();
        panel_5.add((Component)panelReceitas, "cell 1 0");
        panelReceitas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        panelReceitas.setBackground(Color.WHITE);
        panelReceitas.setLayout((LayoutManager)new MigLayout("", "[][81px][24px][107px][grow]", "[][20px][20px][][20px][]"));
        JLabel lblNewLabel_1_2 = new JLabel("Receitas:");
        lblNewLabel_1_2.setFont(new Font("SansSerif", 3, 14));
        panelReceitas.add((Component)lblNewLabel_1_2, "cell 0 0 5 1,alignx left,aligny center");
        JLabel lblNewLabel_7_1 = new JLabel("N.\u00ba Receitas:");
        panelReceitas.add((Component)lblNewLabel_7_1, "cell 1 1,alignx left,aligny bottom");
        lblNewLabel_7_1.setFont(new Font("SansSerif", 0, 14));
        this.lblTotalReceitas = new JLabel("0");
        panelReceitas.add((Component)this.lblTotalReceitas, "cell 2 1,alignx left,aligny top");
        this.lblTotalReceitas.setFont(new Font("SansSerif", 1, 15));
        JLabel lblValorReceitaslbl = new JLabel("Valor Total:");
        lblValorReceitaslbl.setFont(new Font("SansSerif", 0, 14));
        panelReceitas.add((Component)lblValorReceitaslbl, "cell 3 1,alignx right,aligny bottom");
        this.lblValorReceitas = new JLabel("R$\u00a00,00");
        this.lblValorReceitas.setFont(new Font("SansSerif", 1, 15));
        panelReceitas.add((Component)this.lblValorReceitas, "cell 4 1,alignx left,aligny top");
        JLabel lblNewLabel_5_2_1 = new JLabel("        ");
        panelReceitas.add((Component)lblNewLabel_5_2_1, "cell 0 2,alignx right,aligny center");
        lblNewLabel_5_2_1.setOpaque(true);
        lblNewLabel_5_2_1.setForeground(Color.BLACK);
        lblNewLabel_5_2_1.setBackground(new Color(0, 51, 0));
        JLabel lblNewLabel_4_1_1 = new JLabel("Recebido:");
        panelReceitas.add((Component)lblNewLabel_4_1_1, "cell 1 2,alignx left,aligny bottom");
        lblNewLabel_4_1_1.setFont(new Font("SansSerif", 0, 14));
        this.lblReceitasRecebido = new JLabel("0");
        panelReceitas.add((Component)this.lblReceitasRecebido, "cell 2 2,alignx left,aligny top");
        this.lblReceitasRecebido.setFont(new Font("SansSerif", 1, 15));
        JLabel lblValorRecebidolbl = new JLabel("Valor Recebido:");
        lblValorRecebidolbl.setFont(new Font("SansSerif", 0, 14));
        panelReceitas.add((Component)lblValorRecebidolbl, "cell 3 2,alignx right,aligny bottom");
        this.lblValorRecebido = new JLabel("R$\u00a00,00");
        this.lblValorRecebido.setFont(new Font("SansSerif", 1, 15));
        panelReceitas.add((Component)this.lblValorRecebido, "cell 4 2,alignx left,aligny top");
        JLabel lblValorJurosRecebido = new JLabel("Valor Juros Recebido:");
        lblValorJurosRecebido.setFont(new Font("SansSerif", 0, 14));
        panelReceitas.add((Component)lblValorJurosRecebido, "cell 3 3,alignx right");
        this.lblValorTotalJurosRecebido = new JLabel("R$\u00a00,00");
        this.lblValorTotalJurosRecebido.setFont(new Font("SansSerif", 1, 15));
        panelReceitas.add((Component)this.lblValorTotalJurosRecebido, "cell 4 3");
        JLabel lblNewLabel_5_2 = new JLabel("        ");
        panelReceitas.add((Component)lblNewLabel_5_2, "cell 0 4,alignx right,aligny center");
        lblNewLabel_5_2.setOpaque(true);
        lblNewLabel_5_2.setForeground(Color.BLACK);
        lblNewLabel_5_2.setBackground(Color.YELLOW);
        JLabel lblNewLabel_4_1 = new JLabel("A Receber:");
        panelReceitas.add((Component)lblNewLabel_4_1, "cell 1 4,alignx left,aligny bottom");
        lblNewLabel_4_1.setFont(new Font("SansSerif", 0, 14));
        this.lblReceitasAReceber = new JLabel("0");
        panelReceitas.add((Component)this.lblReceitasAReceber, "cell 2 4,alignx left,aligny top");
        this.lblReceitasAReceber.setFont(new Font("SansSerif", 1, 15));
        JLabel lblValorAReceberlbl = new JLabel("Valor a Receber:");
        lblValorAReceberlbl.setFont(new Font("SansSerif", 0, 14));
        panelReceitas.add((Component)lblValorAReceberlbl, "cell 3 4,alignx right,aligny bottom");
        this.lblValorAReceber = new JLabel("R$\u00a00,00");
        this.lblValorAReceber.setFont(new Font("SansSerif", 1, 15));
        panelReceitas.add((Component)this.lblValorAReceber, "cell 4 4,alignx left,aligny top");
        JLabel lblValorAPagar_1_1_1_1 = new JLabel("Valor a Vencer:");
        lblValorAPagar_1_1_1_1.setFont(new Font("SansSerif", 0, 14));
        panelReceitas.add((Component)lblValorAPagar_1_1_1_1, "cell 3 5,alignx right");
        this.lblValorVencerAReceber = new JLabel("R$\u00a00,00");
        this.lblValorVencerAReceber.setFont(new Font("SansSerif", 1, 15));
        panelReceitas.add((Component)this.lblValorVencerAReceber, "cell 4 5");
        JPanel panel_3 = new JPanel();
        panel_5.add((Component)panel_3, "cell 2 0");
        panel_3.setBackground(Color.WHITE);
        JButton btnNewButton_1 = new JButton("Novo Lan\u00e7amento");
        btnNewButton_1.setBackground(new Color(0, 51, 0));
        btnNewButton_1.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroCadastroLancamento tela = new TelaFinanceiroCadastroLancamento(0, null, TelaFinanceiroLancamento.this.isto);
                tela.setVisible(true);
            }
        });
        panel_3.setLayout((LayoutManager)new MigLayout("", "[][][][]", "[23px][][]"));
        JButton btnNewButton_3 = new JButton("Selecionar");
        btnNewButton_3.setBackground(new Color(0, 0, 51));
        btnNewButton_3.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_3.setForeground(Color.WHITE);
        btnNewButton_3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_3.add((Component)btnNewButton_3, "cell 0 0,alignx left,aligny top");
        JButton btnNewButton_2 = new JButton("Gerenciar");
        btnNewButton_2.setBackground(new Color(51, 0, 102));
        btnNewButton_2.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_2.setForeground(Color.WHITE);
        btnNewButton_2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Lancamento> lancamentos_selecionados = TelaFinanceiroLancamento.this.getLancamentosSelecionado();
                if (lancamentos_selecionados.size() == 1) {
                    Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
                    TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar, TelaFinanceiroLancamento.this.isto);
                    tela.setVisible(true);
                } else if (lancamentos_selecionados.size() > 1) {
                    boolean prosseguir = true;
                    for (Lancamento lancamento : lancamentos_selecionados) {
                        if (lancamento.getTipo_lancamento() == 0 || lancamento.getTipo_lancamento() == 2) continue;
                        prosseguir = false;
                        break;
                    }
                    if (prosseguir) {
                        TelaFinanceiroGerenciarLancamentoMultiplo tela = new TelaFinanceiroGerenciarLancamentoMultiplo(lancamentos_selecionados, TelaFinanceiroLancamento.this.isto);
                        tela.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Para gerenciar multiplos lan\u00e7amentos,\ntodos devem ser do tipo 'Despesa' ");
                    }
                }
            }
        });
        panel_3.add((Component)btnNewButton_2, "cell 1 0,alignx left,aligny top");
        panel_3.add((Component)btnNewButton_1, "cell 2 0 2 1,alignx left,aligny top");
        JButton btnNewButton_1_1 = new JButton("Novo Empr\u00e9stimo");
        btnNewButton_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroCadastroEmprestimo tela = new TelaFinanceiroCadastroEmprestimo(-1, null, TelaFinanceiroLancamento.this.isto);
                tela.setVisible(true);
            }
        });
        JButton btnNewButton_3_1 = new JButton("Exportar");
        btnNewButton_3_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<Lancamento>();
                int[] linhas_selecionadas = TelaFinanceiroLancamento.this.tabela_lancamentos.getSelectedRows();
                int i = 0;
                while (i < linhas_selecionadas.length) {
                    int indice = linhas_selecionadas[i];
                    int indexRowModel = TelaFinanceiroLancamento.this.tabela_lancamentos.getRowSorter().convertRowIndexToModel(indice);
                    Lancamento lancamento = TelaFinanceiroLancamento.this.lista_lancamentos.get(indexRowModel);
                    lancamentos_selecionados.add(lancamento);
                    ++i;
                }
                TelaEscolhaRelatorioLancamentos escolha_opcoes = new TelaEscolhaRelatorioLancamentos(lancamentos_selecionados, TelaFinanceiroLancamento.this.isto);
                escolha_opcoes.setVisible(true);
            }
        });
        btnNewButton_3_1.setForeground(Color.WHITE);
        btnNewButton_3_1.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_3_1.setBackground(new Color(51, 0, 51));
        panel_3.add((Component)btnNewButton_3_1, "cell 0 1,growx");
        btnNewButton_1_1.setForeground(Color.WHITE);
        btnNewButton_1_1.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_1_1.setBackground(new Color(0, 0, 102));
        panel_3.add((Component)btnNewButton_1_1, "cell 2 1 2 1");
        JButton btnNewButton_1_1_1 = new JButton("Nova Transfer\u00eancia");
        btnNewButton_1_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroCadastroTransferencia tela = new TelaFinanceiroCadastroTransferencia(0, null, TelaFinanceiroLancamento.this.isto);
                tela.setVisible(true);
            }
        });
        JButton btnNewButton_4 = new JButton("Excluir");
        btnNewButton_4.setBackground(new Color(204, 0, 0));
        btnNewButton_4.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_4.setForeground(Color.WHITE);
        btnNewButton_4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(TelaFinanceiroLancamento.this.isto, "Deseja excluir o Lan\u00e7amento?", "Excluir", 0, 3) == 0) {
                    ArrayList<Lancamento> lancamentos_selecionados = TelaFinanceiroLancamento.this.getLancamentosSelecionado();
                    if (lancamentos_selecionados.size() == 1) {
                        Lancamento lancamento_excluir = lancamentos_selecionados.get(0);
                        ArrayList<FinanceiroPagamento> lista_pagamentos = new GerenciarBancoFinanceiroPagamento().getFinanceiroPagamentosPorLancamento(lancamento_excluir.getId_lancamento());
                        if (lista_pagamentos.size() > 0) {
                            JOptionPane.showMessageDialog(TelaFinanceiroLancamento.this.isto, "O lan\u00e7amento selecionado possui pagamentos, exclua os primeiro");
                        } else {
                            boolean prosseguir = true;
                            GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
                            ArrayList<Parcela> lista_parcelas = gerenciar.getParcelasPorLancamento(lancamento_excluir.getId_lancamento());
                            for (Parcela parcela : lista_parcelas) {
                                boolean remover_parcela = gerenciar.removerParcela(parcela.getId_parcela());
                                if (remover_parcela) continue;
                                prosseguir = false;
                                JOptionPane.showMessageDialog(TelaFinanceiroLancamento.this.isto, "Erro ao excluir parcela do lan\u00e7amento\nBanco de dados Corrompido\nConsulte o administrador");
                                break;
                            }
                            if (prosseguir) {
                                boolean exclusao = new GerenciarBancoLancamento().removerLancamento(lancamento_excluir.getId_lancamento());
                                if (exclusao) {
                                    JOptionPane.showMessageDialog(TelaFinanceiroLancamento.this.isto, "Cadastro Exclu\u00eddo");
                                } else {
                                    JOptionPane.showMessageDialog(TelaFinanceiroLancamento.this.isto, "Erro ao excluir\nConsulte o administrador");
                                }
                            }
                        }
                        TelaFinanceiroLancamento.this.pesquisar();
                    } else {
                        JOptionPane.showMessageDialog(TelaFinanceiroLancamento.this.isto, "Para exclus\u00e3o, selecione somente um lan\u00e7amento por vez");
                    }
                }
            }
        });
        panel_3.add((Component)btnNewButton_4, "cell 1 2,alignx left,aligny top");
        btnNewButton_1_1_1.setForeground(Color.WHITE);
        btnNewButton_1_1_1.setFont(new Font("SansSerif", 1, 16));
        btnNewButton_1_1_1.setBackground(new Color(204, 153, 0));
        panel_3.add((Component)btnNewButton_1_1_1, "cell 2 2 2 1");
        this.popular_centros_custo();
        this.popular_instituicao_bancaria();
        this.popular_grupo_contas();
        this.popular_contas();
        this.popular_condicao_pagamento();
        boolean pegar_datas = true;
        if (pegar_datas) {
            this.pegarDatas(flag_modo_operacao);
        }
        if (flag_modo_operacao == 3) {
            this.filtrarPersonalizado();
        }
        this.pesquisar();
        this.setExtendedState(6);
        this.setResizable(true);
        this.setLocationRelativeTo(janela_pai);
    }

    public void limpar() {
        this.sorter.setRowFilter(RowFilter.regexFilter("", new int[0]));
        this.calcular();
    }

    public void pegarDatas(int flag) {
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        Map<String, String> datas = new HashMap();
        datas = gerenciar.pegarDatas();
        if (datas == null) {
            String hj = new GetData().getData();
            this.menorDataLancamento.setText(hj);
            this.maiorDataLancamento.setText(hj);
            this.menorDataVencimento.setText(hj);
            this.maiorDataVencimento.setText(hj);
            this.menorDataPagamento.setText(hj);
            this.maiorDataPagamento.setText(hj);
        } else {
            if (flag != 3) {
                this.menorDataLancamento.setText((String)datas.get("menor_data_lancamento"));
            }
            this.maiorDataLancamento.setText((String)datas.get("maior_data_lancamento"));
            this.menorDataVencimento.setText((String)datas.get("menor_data_vencimento"));
            this.maiorDataVencimento.setText((String)datas.get("maior_data_vencimento"));
            this.menorDataPagamento.setText((String)datas.get("menor_data_pagamento"));
            this.maiorDataPagamento.setText((String)datas.get("maior_data_pagamento"));
        }
    }

    public void salvarEstadoFiltros() {
        this.id_filtro = this.entIdLancamento.getText();
        this.CC_filtro = this.cbCentroCusto.getSelectedIndex();
        this.cliente_fornecedor_filtro = this.entClienteFornecedor.getText();
        this.entidentificadorGeral_filtro = this.entIdentificadorGeral.getText();
        this.entdestinatarioNF_filtro = this.entDestinatarioNF.getText();
        this.cbtipoLancamento_filtro = this.cbTipoLancamento.getSelectedIndex();
        this.cbstatusLancamento_filtro = this.cbStatusLancamento.getSelectedIndex();
        this.cbgrupoConta_filtro = this.cbGrupoConta.getSelectedIndex();
        this.cbconta_filtro = this.cbConta.getSelectedIndex();
        this.cbstatusAoContador_filtro = this.cbStatusAoContador.getSelectedIndex();
        this.cbinstituicaoBancaria_filtro = this.cbInstituicaoBancaria.getSelectedIndex();
        this.cbcondicaoPagamento_filtro = this.cbCondicaoPagamento.getSelectedIndex();
        this.cbstatusCondicaoPagamento_filtro = this.cbStatusCondicaoPagamento.getSelectedIndex();
        this.cbprioridade_filtro = this.cbPrioridade.getSelectedIndex();
        this.cbsituacao_filtro = this.cbSituacao.getSelectedIndex();
        this.menordataLancamento_filtro = this.menorDataLancamento.getText();
        this.maiordataLancamento_filtro = this.maiorDataLancamento.getText();
        this.menordataVencimento_filtro = this.menorDataVencimento.getText();
        this.maiordataVencimento_filtro = this.maiorDataVencimento.getText();
        this.menordataPagamento_filtro = this.menorDataPagamento.getText();
        this.maiordataPagamento_filtro = this.maiorDataPagamento.getText();
    }

    public void recolherEstadoFiltros() {
        this.entIdLancamento.setText(this.id_filtro);
        this.cbCentroCusto.setSelectedIndex(this.CC_filtro);
        this.entClienteFornecedor.setText(this.cliente_fornecedor_filtro);
        this.entIdentificadorGeral.setText(this.entidentificadorGeral_filtro);
        this.entDestinatarioNF.setText(this.entdestinatarioNF_filtro);
        this.cbTipoLancamento.setSelectedIndex(this.cbtipoLancamento_filtro);
        this.cbStatusLancamento.setSelectedIndex(this.cbstatusLancamento_filtro);
        this.cbGrupoConta.setSelectedIndex(this.cbgrupoConta_filtro);
        this.cbConta.setSelectedIndex(this.cbconta_filtro);
        this.cbStatusAoContador.setSelectedIndex(this.cbstatusAoContador_filtro);
        this.cbInstituicaoBancaria.setSelectedIndex(this.cbinstituicaoBancaria_filtro);
        this.cbCondicaoPagamento.setSelectedIndex(this.cbcondicaoPagamento_filtro);
        this.cbStatusCondicaoPagamento.setSelectedIndex(this.cbstatusCondicaoPagamento_filtro);
        this.cbPrioridade.setSelectedIndex(this.cbprioridade_filtro);
        this.cbSituacao.setSelectedIndex(this.cbsituacao_filtro);
        this.menorDataLancamento.setText(this.menordataLancamento_filtro);
        this.maiorDataLancamento.setText(this.maiordataLancamento_filtro);
        this.menorDataVencimento.setText(this.menordataVencimento_filtro);
        this.maiorDataVencimento.setText(this.maiordataVencimento_filtro);
        this.menorDataPagamento.setText(this.menordataPagamento_filtro);
        this.maiorDataPagamento.setText(this.maiordataPagamento_filtro);
    }

    public void pesquisar() {
        this.salvarEstadoFiltros();
        this.limpar_campos();
        GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
        this.lista_lancamentos.clear();
        this.modelo_lancamentos.onRemoveAll();
        this.lista_lancamentos = gerenciar.buscaLancamentosCompletos();
        for (Lancamento cc : this.lista_lancamentos) {
            this.modelo_lancamentos.onAdd(cc);
        }
        this.recolherEstadoFiltros();
        this.filtrarAvancado();
        this.calcular();
    }

    public void filtrarAvancado() {
        ArrayList filters = new ArrayList(2);
        String data_inicial_filtrar_data_lancamento = this.menorDataLancamento.getText().replace(" ", "");
        String data_final_filtrar_data_lancamento = this.maiorDataLancamento.getText().replace(" ", "");
        if (this.checkString(data_inicial_filtrar_data_lancamento) && this.checkString(data_final_filtrar_data_lancamento)) {
            Date data_menor = null;
            Date data_maior = null;
            try {
                data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_lancamento);
                data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_lancamento);
            }
            catch (ParseException i) {
                i.printStackTrace();
            }
            HashSet datas = new HashSet();
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 1));
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 1));
            filters.add(RowFilter.orFilter(datas));
            HashSet datas_maior = new HashSet();
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 1));
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 1));
            filters.add(RowFilter.orFilter(datas_maior));
        }
        String data_inicial_filtrar_data_vencimento = this.menorDataVencimento.getText().replace(" ", "");
        String data_final_filtrar_data_vencimento = this.maiorDataVencimento.getText().replace(" ", "");
        if (this.checkString(data_inicial_filtrar_data_vencimento) && this.checkString(data_final_filtrar_data_vencimento)) {
            Date data_menor = null;
            Date data_maior = null;
            try {
                data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_vencimento);
                data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_vencimento);
            }
            catch (ParseException i) {
                i.printStackTrace();
            }
            HashSet datas = new HashSet();
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 15));
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 15));
            filters.add(RowFilter.orFilter(datas));
            HashSet datas_maior = new HashSet();
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 15));
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 15));
            filters.add(RowFilter.orFilter(datas_maior));
        }
        try {
            String s_centro_custo = "";
            if (this.checkString(this.cbCentroCusto.getSelectedItem().toString()) && !(s_centro_custo = this.cbCentroCusto.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_centro_custo, 4));
            }
        }
        catch (Exception t) {
            t.printStackTrace();
        }
        if (this.cbTipoLancamento.getSelectedItem().toString() != null) {
            String s_tipo_lancamento = "";
            if (this.checkString(this.cbTipoLancamento.getSelectedItem().toString()) && !(s_tipo_lancamento = this.cbTipoLancamento.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_tipo_lancamento, 2));
            }
        }
        if (this.cbPrioridade.getSelectedItem().toString() != null) {
            String s_prioridade = "";
            if (this.checkString(this.cbPrioridade.getSelectedItem().toString()) && !(s_prioridade = this.cbPrioridade.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_prioridade, 3));
            }
        }
        if (this.entClienteFornecedor.getText() != null) {
            String s_cliente_servidor = "";
            if (this.checkString(this.entClienteFornecedor.getText()) && !(s_cliente_servidor = this.entClienteFornecedor.getText().toUpperCase()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_cliente_servidor, 7));
            }
        }
        if (this.entIdentificadorGeral.getText() != null) {
            String s_id_geral = "";
            if (this.checkString(this.entIdentificadorGeral.getText())) {
                s_id_geral = this.entIdentificadorGeral.getText().toUpperCase();
                filters.add(RowFilter.regexFilter(s_id_geral, 5));
            }
        }
        if (this.entDestinatarioNF.getText() != null) {
            String s_dest_nf = "";
            if (this.checkString(this.entDestinatarioNF.getText())) {
                s_dest_nf = this.entDestinatarioNF.getText().toUpperCase();
                filters.add(RowFilter.regexFilter(s_dest_nf, 6));
            }
        }
        if (this.entIdLancamento.getText() != null) {
            String id_lanc = "";
            if (this.checkString(this.entIdLancamento.getText()) && Integer.parseInt(id_lanc = this.entIdLancamento.getText()) > 0) {
                filters.add(RowFilter.regexFilter(id_lanc, 0));
            }
        }
        if (this.cbGrupoConta.getSelectedItem().toString() != null) {
            String s_grupo_contas = "";
            if (this.checkString(this.cbGrupoConta.getSelectedItem().toString()) && !(s_grupo_contas = this.cbGrupoConta.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_grupo_contas, 8));
            }
        }
        if (this.cbConta.getSelectedItem().toString() != null) {
            String s_contas = "";
            if (this.checkString(this.cbConta.getSelectedItem().toString()) && !(s_contas = this.cbConta.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_contas, 9));
            }
        }
        String s_tipo_conta = "";
        if (this.cbStatusLancamento.getSelectedIndex() == 1) {
            s_tipo_conta = "A Pagar";
            if (this.checkString(s_tipo_conta)) {
                filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
            }
        } else if (this.cbStatusLancamento.getSelectedIndex() == 2) {
            s_tipo_conta = "Pago";
            if (this.checkString(s_tipo_conta)) {
                filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
            }
        } else if (this.cbStatusLancamento.getSelectedIndex() == 3) {
            s_tipo_conta = "A Receber";
            if (this.checkString(s_tipo_conta)) {
                filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
            }
        } else if (this.cbStatusLancamento.getSelectedIndex() == 4 && this.checkString(s_tipo_conta = "Recebido")) {
            filters.add(RowFilter.regexFilter(s_tipo_conta, 17));
        }
        if (this.cbSituacao.getSelectedItem().toString() != null) {
            String s_situacao = "";
            if (this.checkString(this.cbSituacao.getSelectedItem().toString()) && !(s_situacao = this.cbSituacao.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_situacao, 18));
            }
        }
        if (this.cbCondicaoPagamento.getSelectedItem().toString() != null) {
            String s_condicao = "";
            if (this.checkString(this.cbCondicaoPagamento.getSelectedItem().toString()) && !(s_condicao = this.cbCondicaoPagamento.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_condicao, 19));
            }
        }
        if (this.cbStatusCondicaoPagamento.getSelectedItem().toString() != null) {
            String s_status_condicao = "";
            if (this.checkString(this.cbStatusCondicaoPagamento.getSelectedItem().toString()) && !(s_status_condicao = this.cbStatusCondicaoPagamento.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_status_condicao, 20));
            }
        }
        if (this.cbStatusAoContador.getSelectedItem().toString() != null) {
            String s_status_contador = "";
            if (this.checkString(this.cbStatusAoContador.getSelectedItem().toString()) && !(s_status_contador = this.cbStatusAoContador.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_status_contador, 21));
            }
        }
        this.sorter.setRowFilter(RowFilter.andFilter(filters));
        this.calcular();
    }

    public void filtrarPersonalizado() {
        ArrayList filters = new ArrayList(2);
        String data_inicial_filtrar_data_lancamento = this.menorDataLancamento.getText().replace(" ", "");
        String data_final_filtrar_data_lancamento = this.maiorDataLancamento.getText().replace(" ", "");
        if (this.checkString(data_inicial_filtrar_data_lancamento) && this.checkString(data_final_filtrar_data_lancamento)) {
            Date data_menor = null;
            Date data_maior = null;
            try {
                data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_lancamento);
                data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_lancamento);
            }
            catch (ParseException i) {
                i.printStackTrace();
            }
            HashSet datas = new HashSet();
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 1));
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 1));
            filters.add(RowFilter.orFilter(datas));
            HashSet datas_maior = new HashSet();
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 1));
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 1));
            filters.add(RowFilter.orFilter(datas_maior));
        }
        if (this.entClienteFornecedor.getText() != null) {
            String s_cliente_servidor = "";
            if (this.checkString(this.entClienteFornecedor.getText()) && !(s_cliente_servidor = this.entClienteFornecedor.getText().toUpperCase()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_cliente_servidor, 7));
            }
        }
        this.sorter.setRowFilter(RowFilter.andFilter(filters));
        this.calcular();
    }

    public void calcular() {
        boolean numero_lancamentos = false;
        boolean numero_despesas = false;
        int numero_despesas_a_pagar = 0;
        int numero_despesas_pago = 0;
        boolean numero_receitas = false;
        int numero_receitas_a_receber = 0;
        int numero_receitas_recebido = 0;
        
        BigDecimal valor_total_despesas = BigDecimal.ZERO;
        BigDecimal valor_a_pagar = BigDecimal.ZERO;
        BigDecimal valor_pago = BigDecimal.ZERO;
        BigDecimal valor_total_juros_pago = BigDecimal.ZERO;
        BigDecimal valor_total_vencer_pagar = BigDecimal.ZERO;
        BigDecimal valor_total_receitas = BigDecimal.ZERO;
        BigDecimal valor_a_receber = BigDecimal.ZERO;
        BigDecimal valor_recebido = BigDecimal.ZERO;
        BigDecimal valor_total_juros_recebido = BigDecimal.ZERO;
        BigDecimal valor_total_vencer_receber = BigDecimal.ZERO;
      
        
        
        
        int row = 0;
        while (row < this.tabela_lancamentos.getRowCount()) {
            int index = this.tabela_lancamentos.convertRowIndexToModel(row);
            Lancamento lancamento = this.modelo_lancamentos.getValue(index);
            if (lancamento.getTipo_lancamento() != 3 && lancamento.getTipo_lancamento() != 4) {
                if (lancamento.getStatus() == 0) {
                    valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
                    valor_a_pagar = valor_a_pagar.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
                    valor_pago = valor_pago.add(lancamento.getValor_ja_pago());
                    ++numero_despesas_a_pagar;
                    BigDecimal  valor_total = lancamento.getValor();
                    BigDecimal  valor__ja_pago = lancamento.getValor_ja_pago();
                    BigDecimal  valor_restante = valor__ja_pago.subtract(valor_total);
                    if (valor__ja_pago.compareTo(valor_total) > 0) {
                        valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
                    } else {
                    	BigDecimal  valor_a_pagar_lancamento = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
                        valor_total_vencer_pagar = lancamento.getValor_proximo_pagamento_a_vencer().compareTo(valor_a_pagar_lancamento) > 0 ? valor_total_vencer_pagar.add(valor_a_pagar_lancamento) : valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
                    }
                } else if (lancamento.getStatus() == 1) {
                    valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
                    valor_pago = valor_pago.add(lancamento.getValor());
                    BigDecimal   valor_total = lancamento.getValor();
                    BigDecimal  valor__ja_pago = lancamento.getValor_ja_pago();
                    BigDecimal   valor_restante = valor__ja_pago.subtract(valor_total);
                    if (valor__ja_pago.compareTo(valor_total) > 0) {
                        valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
                    } else {
                        valor_total_vencer_pagar = valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
                    }
                    ++numero_despesas_pago;
                } else if (lancamento.getStatus() == 2) {
                    valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
                    valor_a_receber = valor_a_receber.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
                    valor_recebido = valor_recebido.add(lancamento.getValor_ja_pago());
                    BigDecimal  valor_total_a_receber = lancamento.getValor();
                    BigDecimal  valor__ja_recebido = lancamento.getValor_ja_pago();
                    BigDecimal  valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);
                    if (valor__ja_recebido.compareTo(valor_total_a_receber) > 0) {
                        valor_total_juros_recebido = valor_total_juros_recebido.add(valor__ja_recebido.subtract(valor_total_a_receber));
                    } else {
                        valor_total_vencer_receber = valor_total_vencer_receber.add(lancamento.getValor_proximo_pagamento_a_vencer());
                    }
                    ++numero_receitas_a_receber;
                } else if (lancamento.getStatus() == 3) {
                    valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
                    valor_recebido = valor_recebido.add(lancamento.getValor());
                    BigDecimal  valor_total_a_receber = lancamento.getValor();
                    BigDecimal  valor__ja_recebido = lancamento.getValor_ja_pago();
                    BigDecimal  valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);
                    if (valor__ja_recebido.compareTo(valor_total_a_receber) > 0) {
                        valor_total_juros_recebido = valor_total_juros_recebido.add(valor__ja_recebido.subtract(valor_total_a_receber));
                    } else {
                        valor_total_vencer_receber = valor_total_vencer_receber.add(lancamento.getValor_proximo_pagamento_a_vencer());
                    }
                    ++numero_receitas_recebido;
                }
            } else {
                valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
                valor_a_pagar = valor_a_pagar.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));
                valor_pago = valor_pago.add(lancamento.getValor_ja_pago());
                ++numero_despesas_a_pagar;
                BigDecimal  valor_total = lancamento.getValor();
                BigDecimal  valor__ja_pago = lancamento.getValor_ja_pago();
                BigDecimal  valor_restante = valor__ja_pago.subtract(valor_total);
                if (valor__ja_pago.compareTo(valor_total) > 0) {
                    valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
                } else {
                	BigDecimal  valor_a_pagar_lancamento = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
                    valor_total_vencer_pagar = lancamento.getValor_proximo_pagamento_a_vencer().compareTo(valor_a_pagar_lancamento) > 0 ? valor_total_vencer_pagar.add(valor_a_pagar_lancamento) : valor_total_vencer_pagar.add(lancamento.getValor_proximo_pagamento_a_vencer());
                }
            }
            ++row;
        }
        
        
        this.lblDespesasAPagar.setText(String.valueOf(numero_despesas_a_pagar));
        this.lblDespesasPago.setText(String.valueOf(numero_despesas_pago));
        this.lblTotalDespesas.setText(String.valueOf(numero_despesas_a_pagar + numero_despesas_pago));
        this.lblReceitasAReceber.setText(String.valueOf(numero_receitas_a_receber));
        this.lblReceitasRecebido.setText(String.valueOf(numero_receitas_recebido));
        this.lblTotalReceitas.setText(String.valueOf(numero_receitas_a_receber + numero_receitas_recebido));
        Locale ptBr = new Locale("pt", "BR");
        this.lblValorRestanteAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar));
        this.lblValorPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago));
        this.lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
        this.lblValorTotalJurosPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_pago));
       
        this.lblValorVencerAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_pagar));
       
        
        this.lblValorAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_receber));
        this.lblValorRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_recebido));
        this.lblValorReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
        this.lblValorTotalJurosRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_recebido));
        this.lblValorVencerAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_receber));
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public ArrayList<Lancamento> getLancamentosSelecionado() {
        ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<Lancamento>();
        int[] linhas_selecionadas = this.tabela_lancamentos.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_lancamentos.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            Lancamento lancamento_selecionado = this.lista_lancamentos.get(indice);
            lancamentos_selecionados.add(lancamento_selecionado);
            ++i;
        }
        return lancamentos_selecionados;
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

    public void popular_grupo_contas() {
        ArrayList<FinanceiroGrupoContas> lista_grupo_contas = new GerenciarBancoFinanceiroGrupoContas().getFinanceiroGrupoContass();
        this.cbGrupoConta.removeAllItems();
        this.cbGrupoConta.addItem("TODOS");
        for (FinanceiroGrupoContas grupo_contas : lista_grupo_contas) {
            this.cbGrupoConta.addItem(grupo_contas.getNome());
        }
    }

    public void popular_centros_custo() {
        ArrayList<CentroCusto> lista_centro_custos = new GerenciarBancoCentroCustos().getCentroCustos();
        this.cbCentroCusto.removeAllItems();
        this.cbCentroCusto.addItem("TODOS");
        for (CentroCusto cc : lista_centro_custos) {
            this.cbCentroCusto.addItem(cc.getNome_centro_custo());
        }
    }

    public void popular_instituicao_bancaria() {
        ArrayList<InstituicaoBancaria> lista_ibs = new GerenciarBancoInstituicaoBancaria().getInstituicoesBancarias();
        this.cbInstituicaoBancaria.removeAllItems();
        this.cbInstituicaoBancaria.addItem("TODOS");
        for (InstituicaoBancaria ib : lista_ibs) {
            this.cbInstituicaoBancaria.addItem(ib.getNome_instituicao_bancaria());
        }
    }

    public void popular_contas() {
        ArrayList<FinanceiroConta> lista_contas = new GerenciarBancoFinanceiroConta().getFinanceiroContas();
        this.cbConta.removeAllItems();
        this.cbConta.addItem("TODOS");
        for (FinanceiroConta contas : lista_contas) {
            this.cbConta.addItem(contas.getNome());
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

    public void filtroRapidoDespesaAtrazo(int dias) {
        this.cbStatusLancamento.setSelectedIndex(1);
        this.cbSituacao.setSelectedIndex(1);
        LocalDate hoje = LocalDate.now();
        String s_hoje = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.maiorDataVencimento.setText(s_hoje);
        LocalDate menor_data = hoje.minusDays(dias);
        String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.menorDataVencimento.setText(s_menor_data);
        this.filtrarAvancado();
    }

    public void filtroRapidoDespesaEmDias(int dias) {
        this.cbStatusLancamento.setSelectedIndex(1);
        this.cbSituacao.setSelectedIndex(2);
        LocalDate hoje = LocalDate.now();
        String s_hoje = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.menorDataVencimento.setText(s_hoje);
        LocalDate menor_data = hoje.plusDays(dias);
        String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.maiorDataVencimento.setText(s_menor_data);
        this.filtrarAvancado();
    }

    public void filtroRapidoReceitaAtrazo(int dias) {
        this.cbStatusLancamento.setSelectedIndex(3);
        this.cbSituacao.setSelectedIndex(1);
        LocalDate hoje = LocalDate.now();
        String s_hoje = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.maiorDataVencimento.setText(s_hoje);
        LocalDate menor_data = hoje.minusDays(dias);
        String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.menorDataVencimento.setText(s_menor_data);
        this.filtrarAvancado();
    }

    public void filtroRapidoReceitaEmDias(int dias) {
        this.cbStatusLancamento.setSelectedIndex(3);
        this.cbSituacao.setSelectedIndex(2);
        LocalDate hoje = LocalDate.now();
        String s_hoje = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.menorDataVencimento.setText(s_hoje);
        LocalDate menor_data = hoje.plusDays(dias);
        String s_menor_data = menor_data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.maiorDataVencimento.setText(s_menor_data);
        this.filtrarAvancado();
    }

    public void setPesquisaPersonalizada(String cliente, int mes, int ano) {
        GregorianCalendar datas = new GregorianCalendar();
        datas.set(2, mes - 1);
        int ultimo_dia = ((Calendar)datas).getActualMaximum(5);
        String s_mes = "";
        s_mes = mes <= 9 ? "0" + mes : String.valueOf(mes);
        String menor_data = "01/" + s_mes + "/" + ano;
        String maior_data = String.valueOf(ultimo_dia) + "/" + s_mes + "/" + ano;
        this.menorDataLancamento.setText(menor_data);
        this.maiorDataLancamento.setText(maior_data);
        this.entClienteFornecedor.setText(cliente);
    }

    public void limpar_campos() {
        this.sorter.setRowFilter(RowFilter.regexFilter("", new int[0]));
        this.cbPrioridade.setSelectedIndex(0);
        this.cbSituacao.setSelectedIndex(0);
        this.cbStatusLancamento.setSelectedIndex(0);
        this.cbCondicaoPagamento.setSelectedIndex(0);
        this.cbCentroCusto.setSelectedIndex(0);
        this.cbGrupoConta.setSelectedIndex(0);
        this.cbConta.setSelectedIndex(0);
        this.cbInstituicaoBancaria.setSelectedIndex(0);
        this.cbTipoLancamento.setSelectedIndex(0);
        this.entClienteFornecedor.setText("");
        this.pegarDatas(0);
    }

    public class LancamentoTableModel
    extends AbstractTableModel {
        private final int id = 0;
        private final int data = 1;
        private final int tipo_lancamento = 2;
        private final int prioridade = 3;
        private final int centro_custo = 4;
        private final int identificador_geral = 5;
        private final int destinatario_nf = 6;
        private final int cliente_fornecedor = 7;
        private final int grupo_conta = 8;
        private final int conta = 9;
        private final int valor = 10;
        private final int valor_pago = 11;
        private final int valor_a_pagar = 12;
        private final int valor_proxima_parcela_a_vencer = 13;
        private final int juros = 14;
        private final int data_vencimento = 15;
        private final int data_pagamento = 16;
        private final int status = 17;
        private final int situacao = 18;
        private final int condicao_pagamento = 19;
        private final int status_condicao_pagamento = 20;
        private final int status_contador = 21;
        List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);
        private final String[] colunas = new String[]{"ID", "Data Lan\u00e7amento", "Tipo", "Prioridade", "Centro de Custo", "Identificador Geral", "Destinat\u00e1rio da NF", "Cliente/Fornecedor", "Grupo de Contas", "Conta", "Valor Total", "Valor Pago/Recebido", "Valor a Pagar/Receber", "Valor a Vencer", "Juros", "Data Pr\u00f3ximo Vencimento", "Data \u00daltimo Pagamento", "Status", "Situa\u00e7\u00e3o", "Condi\u00e7\u00f5es de Pagamento", "Status Condi\u00e7\u00e3o de Pagamento", "Status Contador"};
        private final ArrayList<Lancamento> dados = new ArrayList();
        private GerenciarBancoCondicaoPagamentos gerenciar = null;
        private ArrayList<CondicaoPagamento> lista_condicoes = null;
        Locale ptBr = new Locale("pt", "BR");

        public LancamentoTableModel() {
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
                    return Date.class;
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
                    return String.class;
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
                case 12: {
                    return String.class;
                }
                case 13: {
                    return String.class;
                }
                case 14: {
                    return String.class;
                }
                case 15: {
                    return Date.class;
                }
                case 16: {
                    return Date.class;
                }
                case 17: {
                    return String.class;
                }
                case 18: {
                    return String.class;
                }
                case 19: {
                    return String.class;
                }
                case 20: {
                    return String.class;
                }
                case 21: {
                    return String.class;
                }
            }
            throw new IndexOutOfBoundsException("Coluna Inv\u00e1lida!!!");
        }

        @Override
        public String getColumnName(int columnIndex) {
            return this.colunas[columnIndex];
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
        	Lancamento   dado = dados.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return dado.getId_lancamento();
                }
                case 2: {
                    if (dado.getTipo_lancamento() == 0) {
                        return "DESPESAS";
                    }
                    if (dado.getTipo_lancamento() == 1) {
                        return "RECEITAS";
                    }
                    if (dado.getTipo_lancamento() == 2) {
                        return "TRANSFERENCIAS";
                    }
                    if (dado.getTipo_lancamento() == 3) {
                        return "EMPRESTIMOS MUTUADOS";
                    }
                    if (dado.getTipo_lancamento() == 4) {
                        return "EMPRESTIMOS TOMADOS";
                    }
                }
                case 3: {
                    int i_prioridade = dado.getPrioridade();
                    if (i_prioridade == 0) {
                        return "Alta Prioridade - Ainda esta semana";
                    }
                    if (i_prioridade == 1) {
                        return "M\u00e9dia Prioridade - Em menos de 15 dias";
                    }
                    if (i_prioridade == 2) {
                        return "Prioridade Leve - Ainda este m\u00eas";
                    }
                    if (i_prioridade == 3) {
                        return "Baixa Prioridade - Ainda este ano";
                    }
                }
                case 1: {
                	Date data_menor;
                    try {
                    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        data_menor = formato.parse(dado.getData_lancamento());
                        return data_menor;
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                case 4: {
                    if (dado.getNome_centro_custo() != null) {
                        return dado.getNome_centro_custo();
                    }
                    return "INDEFINIDO";
                }
                case 5: {
                    return dado.getIdentificacao() != null ? dado.getIdentificacao().toUpperCase() : "";
                }
                case 6: {
                    return dado.getNome_destinatario_nf() != null ? dado.getNome_destinatario_nf().toUpperCase() : "";
                }
                case 7: {
                    if (dado.getNome_cliente_fornecedor() != null) {
                        return dado.getNome_cliente_fornecedor().toUpperCase();
                    }
                    return "INDEFINIDO";
                }
                case 8: {
                    return dado.getNome_grupo_contas() != null ? dado.getNome_grupo_contas().toUpperCase() : "";
                }
                case 9: {
                    return dado.getNome_conta() != null ? dado.getNome_conta().toUpperCase() : "";
                }
                case 10: {
                   String valorString = NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getValor());
                    return valorString;
                }
                case 11: {
                	String valorString = NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getValor_ja_pago());
                    return valorString;
                }
                case 12: {
                	BigDecimal valor_total = dado.getValor();
                	BigDecimal valor_pago = dado.getValor_ja_pago();
                    if (valor_pago.compareTo(valor_total) >= 0) {
                        return "R$ 0.00";
                    }
                    String valorString = NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getValor().subtract(dado.getValor_ja_pago()));
                    return valorString;
                }
                case 13: {
                    BigDecimal valor_a_pagar_lancamento = dado.getValor().subtract(dado.getValor_ja_pago());
                    if (dado.getValor_proximo_pagamento_a_vencer().compareTo(valor_a_pagar_lancamento) > 0) {
                        String valorString = NumberFormat.getCurrencyInstance(this.ptBr).format(valor_a_pagar_lancamento.doubleValue());
                        return valorString;
                    }
                    String valorString = NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getValor_proximo_pagamento_a_vencer().doubleValue());
                    return valorString;
                }
                case 14: {
                	BigDecimal valor_total = dado.getValor();
                	BigDecimal valor_pago = dado.getValor_ja_pago();
                	BigDecimal valor_restante = valor_pago.subtract(valor_total);
                    if (valor_pago.compareTo(valor_total) > 0) {
                        return NumberFormat.getCurrencyInstance(this.ptBr).format(valor_total.subtract(valor_pago));
                    }
                    return "R$ 0.00";
                }
                case 15: {
    				Date data_menor;
    				Date data_menor2;
                    try {
                    	SimpleDateFormat  formato = new SimpleDateFormat("dd/MM/yyyy");
                        data_menor = formato.parse(dado.getData_vencimento());
                        return data_menor;
                    }
                    catch (Exception e) {
                        try {
                        	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            data_menor2 = formato.parse(dado.getData_lancamento());
                            return data_menor2;
                        }
                        catch (ParseException h) {
                            e.printStackTrace();
                        }
                    }
                }
                case 16: {
                	Date data_menor;
    				Date data_menor2;
                    try {
                    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        data_menor = formato.parse(dado.getData_pagamento());
                        return data_menor;
                    }
                    catch (Exception e) {
                        if (dado.getTipo_lancamento() != 2) 
                        try {
                        	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            data_menor2 = formato.parse(dado.getData_lancamento());
                            return data_menor2;
                        }
                        catch (ParseException h) {
                            e.printStackTrace();
                        }
                    }
                }

                // 3 sources

                case 17: {
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


                case 18: {
                	try {

    					if (dado.getTipo_lancamento() != 2) {
    						// data hoje
    						LocalDate hoje = LocalDate.now();

    						// data vencimento
    						Date data_vencimento = null;
    						try {
    							SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    							data_vencimento = formato.parse(dado.getData_vencimento());

    							try {
    								LocalDate ld_data_vencimento = data_vencimento.toInstant()
    										.atZone(ZoneId.systemDefault()).toLocalDate();

    								if (ld_data_vencimento.isAfter(hoje)) {
    									return "Em dias";
    								} else {
    									return "Atrazado";

    								}
    							} catch (Exception e) {
    								return "Datas Invalidas";
    							}

    						} catch (NullPointerException e) {
    							return "Datas Invalidas";

    						} catch (Exception e) {
    							return "Datas Invalidas";

    						}
    					}

    				} catch (Exception e) {
    					return "Datas Invalidas";

    				}
                }

                // 2 sources

                case 19: {
                	try {
    					String condicoes = "";
    					String array_condicoes_pagamento = dado.getIds_forma_pagamento();
    					String ids[] = array_condicoes_pagamento.split(",");
    					for (String id : ids) {
    						int id_condicao_pagamento = Integer.parseInt(id);
    						if (id_condicao_pagamento > 0) {

    							CondicaoPagamento condicao = null;
    							for (CondicaoPagamento cond : lista_condicoes) {
    								if (cond.getId_condicao_pagamento() == id_condicao_pagamento) {
    									condicao = cond;
    									break;
    								}
    							}

    							if (condicao != null)
    								;
    							condicoes += (condicao.getNome_condicao_pagamento() + "|");
    						}

    					}
    					return condicoes;

    				} catch (Exception e) {
    					return "";
    				}
                }
                case 20: {
                	try {
    					String retorno = "";
    					String array_status = dado.getStatus_forma_pagamento();
    					String status[] = array_status.split(",");
    					for (String id : status) {
    						int id_status = Integer.parseInt(id);
    						if (id_status == 0) {
    							// cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
    							// cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");
    							retorno += ("A - Compensar|Realizar|Concluir;");
    						} else if (id_status == 1) {
    							retorno += ("Compensado|Realizado|Concluído;");

    						}

    					}
    					return retorno;

    				} catch (Exception e) {
    					return "";
    				}
                }
                case 21: {

                	int status = dado.getContador();

    				if (status == 0) {
    					return "Não se aplica".toUpperCase();
    				} else if (status == 1) {
    					return "Não Enviado ao contador".toUpperCase();

    				} else if (status == 2) {
    					return "Enviado ao contador".toUpperCase();
    				}
                }
            }
            throw new IndexOutOfBoundsException("Coluna Inválida!!!");
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Lancamento ib = this.dados.get(rowIndex);
        }

        public Lancamento getValue(int rowIndex) {
            return this.dados.get(rowIndex);
        }

        public int indexOf(Lancamento dado) {
            return this.dados.indexOf(dado);
        }

        public void onAdd(Lancamento dado) {
            this.dados.add(dado);
            this.fireTableRowsInserted(this.indexOf(dado), this.indexOf(dado));
        }

        public void onAddAll(ArrayList<Lancamento> dadosIn) {
            this.dados.addAll(dadosIn);
            this.fireTableDataChanged();
        }

        public void onRemove(int rowIndex) {
            this.dados.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void onRemove(Lancamento dado) {
            int indexBefore = this.indexOf(dado);
            this.dados.remove(dado);
            this.fireTableRowsDeleted(indexBefore, indexBefore);
        }

        public void onRemoveAll() {
            this.dados.clear();
            this.fireTableDataChanged();
        }

        public Lancamento onGet(int row) {
            return this.dados.get(row);
        }
    }

    class LancamentosRender
    extends DefaultTableCellRenderer {
        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

        LancamentosRender() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ((JLabel)renderer).setOpaque(true);
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            if (value instanceof Date) {
                value = f.format(value);
            }
            int status = -1;
            String tipo_lancamento = (String)table.getValueAt(row, 2);
            if (!tipo_lancamento.equalsIgnoreCase("TRANSFERENCIAS")) {
                String s_status = (String)table.getValueAt(row, 17);
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
                    renderer.setBackground(new Color(139, 69, 19));
                } else if (status == 0) {
                    renderer.setBackground(Color.red);
                    renderer.setForeground(Color.white);
                    renderer.setFont(new Font("Tahoma", 1, 12));
                } else if (status == 1) {
                    renderer.setBackground(Color.orange);
                    renderer.setForeground(Color.black);
                    renderer.setFont(new Font("Tahoma", 1, 12));
                } else if (status == 2) {
                    renderer.setBackground(Color.yellow);
                    renderer.setForeground(Color.black);
                    renderer.setFont(new Font("Tahoma", 1, 12));
                } else if (status == 3) {
                    renderer.setBackground(new Color(0, 51, 0));
                    renderer.setForeground(Color.white);
                    renderer.setFont(new Font("Tahoma", 1, 12));
                }
            } else {
                renderer.setBackground(new Color(51, 0, 255));
                renderer.setForeground(Color.white);
                renderer.setFont(new Font("Tahoma", 1, 12));
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}

