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
import java.util.Date;
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
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroParcelaCompleto;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
import main.java.gui.TelaFinanceiroGerenciarLancamento;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEscolhaRelatorioParcelas;
import net.miginfocom.swing.MigLayout;

public class TelaFinanceiroParcelas
extends JFrame {
    private JComboBox cbGrupoConta;
    private JComboBox cbConta;
    private final JPanel painelPrinciapl = new JPanel();
    private TelaFinanceiroParcelas isto;
    private JTable tabela_parcela;
    private ArrayList<FinanceiroParcelaCompleto> lista_FinanceiroParcelasCompletos = new ArrayList();
    private ParcelaTableModel modelo_parcela = new ParcelaTableModel();
    private JDialog telaPai;
    private TableRowSorter<ParcelaTableModel> sorter;
    private JComboBox cbStatus;
    private JComboBox cbFluxoCaixa;
    private JComboBox cbTipoFinanceiroParcelaCompleto;
    private JTextField menorDataPagamento;
    private JTextField maiorDataPagamento;
    private JTextField entIdentificadorGeral;
    private JLabel lblNumTotalPagamentos;
    private JLabel entValorTotalPagamentoDespesas;
    private JLabel entValorTotalPagamentoReceitas;
    private JLabel entBalanco;
    private JComboBox cbCentroCusto;
    private JTextField entClienteFornecedor;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private JLabel entValorTotalPagamentoTransferencia;
    private JLabel entValorTotalPagamentoEmprestimoDespesas;

    public TelaFinanceiroParcelas(int flag_modo_operacao, int flag_retorno, Window janela_pai) {
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
        this.setContentPane(this.painelPrinciapl);
        this.painelPrinciapl.setLayout((LayoutManager)new MigLayout("", "[][grow][]", "[][100px][grow][][]"));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 255));
        this.painelPrinciapl.add((Component)panel, "cell 0 0 3 1,grow");
        panel.setLayout((LayoutManager)new MigLayout("", "[269px][]", "[49px]"));
        JLabel lblNewLabel = new JLabel("Parcelas");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", 1, 40));
        panel.add((Component)lblNewLabel, "cell 0 0,alignx left,aligny top");
        JPanel panel_1 = new JPanel();
        this.painelPrinciapl.add((Component)panel_1, "cell 0 1 3 1,alignx center,aligny top");
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBackground(Color.WHITE);
        panel_1.setLayout((LayoutManager)new MigLayout("", "[116px][119px,grow][140px][441px,grow][][grow][][][125px][8px][141px]", "[][28px][33px][33px][]"));
        JLabel lblNewLabel_1_1_2_1_1_2 = new JLabel("Identificador Geral:");
        lblNewLabel_1_1_2_1_1_2.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_2_1_1_2, "cell 0 0,alignx right,aligny center");
        this.entIdentificadorGeral = new JTextField();
        this.entIdentificadorGeral.setFont(new Font("Arial", 1, 16));
        this.entIdentificadorGeral.setColumns(10);
        panel_1.add((Component)this.entIdentificadorGeral, "cell 1 0,growx,aligny bottom");
        JLabel lblNewLabel_1_1_4_1_1_1 = new JLabel("Per\u00edodo de Vencimento:");
        panel_1.add((Component)lblNewLabel_1_1_4_1_1_1, "cell 7 0 4 1,alignx center,aligny center");
        lblNewLabel_1_1_4_1_1_1.setFont(new Font("SansSerif", 0, 15));
        JLabel lblNewLabel_1_1 = new JLabel("Tipo de Lan\u00e7amento:");
        lblNewLabel_1_1.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1, "cell 0 1,alignx left,aligny center");
        this.cbTipoFinanceiroParcelaCompleto = new JComboBox();
        this.cbTipoFinanceiroParcelaCompleto.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbTipoFinanceiroParcelaCompleto, "cell 1 1,alignx left,aligny center");
        this.cbTipoFinanceiroParcelaCompleto.addItem("TODOS");
        this.cbTipoFinanceiroParcelaCompleto.addItem("DESPESAS");
        this.cbTipoFinanceiroParcelaCompleto.addItem("RECEITAS");
        this.cbTipoFinanceiroParcelaCompleto.addItem("EMPRESTIMOS MUTUADOS");
        this.cbTipoFinanceiroParcelaCompleto.addItem("EMPRESTIMOS TOMADOS");
        this.cbTipoFinanceiroParcelaCompleto.addItem("TRANSFERENCIAS");
        JLabel lblNewLabel_1_1_4_2_1 = new JLabel("Status Parcela:");
        lblNewLabel_1_1_4_2_1.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_4_2_1, "cell 2 1,alignx right,aligny center");
        this.cbStatus = new JComboBox();
        this.cbStatus.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbStatus, "cell 3 1,growx,aligny center");
        this.cbStatus.addItem("TODOS");
        this.cbStatus.addItem("PAGO");
        this.cbStatus.addItem("A PAGAR");
        JLabel lblNewLabel_8_1_1 = new JLabel("De");
        panel_1.add((Component)lblNewLabel_8_1_1, "cell 7 1,alignx left,aligny center");
        lblNewLabel_8_1_1.setFont(new Font("SansSerif", 1, 14));
        this.menorDataPagamento = new JTextField();
        this.menorDataPagamento.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.menorDataPagamento, "cell 8 1,alignx left,aligny top");
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
        JLabel lblNewLabel_1_1_1 = new JLabel("Centro de Custo:");
        lblNewLabel_1_1_1.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_1, "cell 0 2,alignx trailing");
        this.cbCentroCusto = new JComboBox();
        this.cbCentroCusto.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbCentroCusto, "cell 1 2,growx");
        this.cbCentroCusto.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    TelaFinanceiroParcelas.this.filtrar();
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        });
        JLabel lblNewLabel_1_1_1_1 = new JLabel("Cliente/Fornecedor:");
        lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_1_1, "cell 2 2,alignx trailing");
        this.entClienteFornecedor = new JTextField();
        this.entClienteFornecedor.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.entClienteFornecedor, "cell 3 2,growx");
        this.entClienteFornecedor.setColumns(10);
        JButton btnFiltar = new JButton("Filtrar");
        btnFiltar.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)btnFiltar, "cell 8 2,growx,aligny top");
        btnFiltar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroParcelas.this.filtrar();
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
                TelaFinanceiroParcelas.this.pesquisar();
            }
        });
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(0, 0, 102));
        JLabel lblNewLabel_1_1_1_3 = new JLabel("Fluxo de Caixa?:");
        lblNewLabel_1_1_1_3.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_1_3, "cell 0 3,alignx trailing");
        this.cbFluxoCaixa = new JComboBox();
        this.cbFluxoCaixa.addItem("TODOS");
        this.cbFluxoCaixa.addItem("N\u00c3O");
        this.cbFluxoCaixa.addItem("SIM");
        this.cbFluxoCaixa.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbFluxoCaixa, "cell 1 3,growx");
        JLabel lblNewLabel_1_1_1_2 = new JLabel("Grupo Contas:");
        lblNewLabel_1_1_1_2.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_1_2, "cell 2 3,alignx trailing");
        this.cbGrupoConta = new JComboBox();
        this.cbGrupoConta.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbGrupoConta, "cell 3 3,growx");
        JButton btnLimparBusca = new JButton("Limpar Busca");
        btnLimparBusca.setFont(new Font("SansSerif", 0, 16));
        panel_1.add((Component)btnLimparBusca, "cell 8 3,alignx left,aligny top");
        btnLimparBusca.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroParcelas.this.limpar();
            }
        });
        btnLimparBusca.setBackground(new Color(204, 0, 0));
        btnLimparBusca.setForeground(Color.WHITE);
        btnLimparBusca.setHorizontalAlignment(4);
        JButton btnLimparCampos = new JButton("Limpar Campos");
        btnLimparCampos.setFont(new Font("SansSerif", 0, 16));
        btnLimparCampos.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroParcelas.this.sorter.setRowFilter(RowFilter.regexFilter("", new int[0]));
                TelaFinanceiroParcelas.this.cbTipoFinanceiroParcelaCompleto.setSelectedIndex(0);
                TelaFinanceiroParcelas.this.cbGrupoConta.setSelectedIndex(0);
                TelaFinanceiroParcelas.this.cbConta.setSelectedIndex(0);
                TelaFinanceiroParcelas.this.cbCentroCusto.setSelectedIndex(0);
                TelaFinanceiroParcelas.this.entClienteFornecedor.setText("");
                TelaFinanceiroParcelas.this.cbFluxoCaixa.setSelectedIndex(0);
                TelaFinanceiroParcelas.this.pegarDatas();
                TelaFinanceiroParcelas.this.calcular();
            }
        });
        btnLimparCampos.setHorizontalAlignment(4);
        btnLimparCampos.setForeground(Color.WHITE);
        btnLimparCampos.setBackground(new Color(255, 51, 0));
        panel_1.add((Component)btnLimparCampos, "cell 10 3,alignx left,aligny top");
        JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Conta:");
        lblNewLabel_1_1_1_2_1.setFont(new Font("SansSerif", 0, 15));
        panel_1.add((Component)lblNewLabel_1_1_1_2_1, "cell 2 4,alignx trailing");
        this.cbConta = new JComboBox();
        this.cbConta.setFont(new Font("Arial", 1, 16));
        panel_1.add((Component)this.cbConta, "cell 3 4,growx");
        FinanceiroParcelaCompletosRender renderer = new FinanceiroParcelaCompletosRender();
        this.tabela_parcela = new JTable(this.modelo_parcela);
        this.tabela_parcela.setDefaultRenderer(Object.class, renderer);
        this.sorter = new TableRowSorter<ParcelaTableModel>(this.modelo_parcela);
        this.tabela_parcela.setRowSorter(this.sorter);
        this.tabela_parcela.setRowHeight(30);
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItemVizualizar = new JMenuItem();
        jMenuItemVizualizar.setText("Vizualizar");
        jMenuItemVizualizar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceDaLinha = TelaFinanceiroParcelas.this.tabela_parcela.getSelectedRow();
                int indice = TelaFinanceiroParcelas.this.tabela_parcela.getRowSorter().convertRowIndexToModel(indiceDaLinha);
                FinanceiroParcelaCompleto parc = TelaFinanceiroParcelas.this.modelo_parcela.getValue(indice);
                String nome_pasta = "lancamento_" + parc.getLancamento().getId_lancamento();
                String nome_arquivo = parc.getFpc().getCaminho_arquivo();
                String unidade_base_dados = TelaFinanceiroParcelas.this.configs_globais.getServidorUnidade();
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
        this.tabela_parcela.setComponentPopupMenu(jPopupMenu);
        JScrollPane scrollPane = new JScrollPane(this.tabela_parcela);
        this.painelPrinciapl.add((Component)scrollPane, "cell 0 2 3 1,grow");
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        this.painelPrinciapl.add((Component)panel_5, "cell 0 3 3 2,grow");
        panel_5.setLayout((LayoutManager)new MigLayout("", "[189px][][189px][][87.00px][][][][][][][][][][][][][][][][][][]", "[][][][][][][]"));
        JButton btnAbrirLancamento = new JButton("Abrir");
        btnAbrirLancamento.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Lancamento> lancamentos_selecionados = TelaFinanceiroParcelas.this.getLancamentosSelecionado();
                if (lancamentos_selecionados.size() == 1) {
                    Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
                    TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar, TelaFinanceiroParcelas.this.isto);
                    tela.setVisible(true);
                }
            }
        });
        JLabel lblNewLabel_1_3 = new JLabel("N\u00fam Total Parcelas:");
        lblNewLabel_1_3.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_3, "cell 0 0,alignx right");
        this.lblNumTotalPagamentos = new JLabel("");
        this.lblNumTotalPagamentos.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.lblNumTotalPagamentos, "cell 1 0");
        JButton btnExportar = new JButton("Exportar");
        btnExportar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<FinanceiroParcelaCompleto> parcelas_selecionados = new ArrayList<FinanceiroParcelaCompleto>();
                int[] linhas_selecionadas = TelaFinanceiroParcelas.this.tabela_parcela.getSelectedRows();
                int i = 0;
                while (i < linhas_selecionadas.length) {
                    int indice = linhas_selecionadas[i];
                    int indexRowModel = TelaFinanceiroParcelas.this.tabela_parcela.getRowSorter().convertRowIndexToModel(indice);
                    FinanceiroParcelaCompleto pagamento = TelaFinanceiroParcelas.this.modelo_parcela.getValue(indexRowModel);
                    parcelas_selecionados.add(pagamento);
                    ++i;
                }
                TelaEscolhaRelatorioParcelas escolha_opcoes = new TelaEscolhaRelatorioParcelas(parcelas_selecionados, TelaFinanceiroParcelas.this.isto);
                escolha_opcoes.setVisible(true);
            }
        });
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("SansSerif", 1, 16));
        btnExportar.setBackground(new Color(0, 0, 153));
        panel_5.add((Component)btnExportar, "cell 15 0 3 2,alignx right,growy");
        btnAbrirLancamento.setForeground(Color.WHITE);
        btnAbrirLancamento.setFont(new Font("SansSerif", 1, 16));
        btnAbrirLancamento.setBackground(new Color(0, 51, 0));
        panel_5.add((Component)btnAbrirLancamento, "cell 18 0 5 2,grow");
        JLabel lblNewLabel_1 = new JLabel("Valor Total Parcelas Despesas:");
        lblNewLabel_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1, "cell 0 2 1 2,alignx right,growy");
        this.entValorTotalPagamentoDespesas = new JLabel("R$ 100.000.000,00");
        this.entValorTotalPagamentoDespesas.setForeground(new Color(153, 0, 0));
        this.entValorTotalPagamentoDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoDespesas, "cell 1 2,alignx center,growy");
        JLabel lblNewLabel_1_2 = new JLabel("   Valor Total Parcelas Receitas:");
        lblNewLabel_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2, "cell 2 2 1 2,alignx right,growy");
        this.entValorTotalPagamentoReceitas = new JLabel("R$ 100.000.000,00");
        this.entValorTotalPagamentoReceitas.setForeground(new Color(0, 51, 0));
        this.entValorTotalPagamentoReceitas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoReceitas, "cell 3 2 1 2");
        JLabel lblNewLabel_1_2_1 = new JLabel("Balan\u00e7o:");
        lblNewLabel_1_2_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_2_1, "cell 4 2 1 2,alignx right,growy");
        this.entBalanco = new JLabel("R$ 100.000.000,00");
        this.entBalanco.setForeground(new Color(0, 153, 0));
        this.entBalanco.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entBalanco, "cell 5 2 1 2");
        JLabel lblNewLabel_1_4 = new JLabel("Valor Total Parcelas Transfer\u00eancia:");
        lblNewLabel_1_4.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4, "cell 0 4 1 2,alignx right");
        this.entValorTotalPagamentoTransferencia = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoTransferencia.setForeground(new Color(0, 0, 153));
        this.entValorTotalPagamentoTransferencia.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoTransferencia, "cell 1 4,growy");
        JLabel lblNewLabel_1_4_1 = new JLabel("Valor Total Parcelas Empr\u00e9stimo(Despesas):");
        lblNewLabel_1_4_1.setFont(new Font("SansSerif", 0, 16));
        panel_5.add((Component)lblNewLabel_1_4_1, "cell 0 6,alignx right");
        this.entValorTotalPagamentoEmprestimoDespesas = new JLabel("R$\u00a00,00");
        this.entValorTotalPagamentoEmprestimoDespesas.setForeground(new Color(102, 0, 0));
        this.entValorTotalPagamentoEmprestimoDespesas.setFont(new Font("SansSerif", 1, 18));
        panel_5.add((Component)this.entValorTotalPagamentoEmprestimoDespesas, "cell 1 6,growy");
        this.popular_grupo_contas();
        this.popular_contas();
        this.popular_centros_custo();
        this.pesquisar();
        boolean pegar_datas = true;
        if (pegar_datas) {
            this.pegarDatas();
        }
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

		Map<String, String> datas = new HashMap<String, String>();
		datas = gerenciar.pegarDatasPagamento();

		menorDataPagamento.setText(datas.get("menor_data_pagamento"));
		maiorDataPagamento.setText(datas.get("maior_data_pagamento"));
	}

    public void pesquisar() {
        GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
        GerenciarBancoParcelasEmprestimo gerenciar_emprestimos = new GerenciarBancoParcelasEmprestimo();
        this.lista_FinanceiroParcelasCompletos.clear();
        this.modelo_parcela.onRemoveAll();
        this.lista_FinanceiroParcelasCompletos = gerenciar.getTodasParcelas();
        for (FinanceiroParcelaCompleto cc : this.lista_FinanceiroParcelasCompletos) {
            this.modelo_parcela.onAdd(cc);
        }
        this.calcular();
    }

    public void filtrar() {
        ArrayList filters = new ArrayList(2);
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
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 8));
            datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 8));
            filters.add(RowFilter.orFilter(datas));
            HashSet datas_maior = new HashSet();
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 8));
            datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 8));
            filters.add(RowFilter.orFilter(datas_maior));
        }
        try {
            String s_centro_custo = "";
            if (this.checkString(this.cbCentroCusto.getSelectedItem().toString()) && !(s_centro_custo = this.cbCentroCusto.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_centro_custo, 5));
            }
        }
        catch (Exception s_centro_custo) {
            // empty catch block
        }
        if (this.entClienteFornecedor.getText() != null) {
            String s_cliente_servidor = "";
            if (this.checkString(this.entClienteFornecedor.getText()) && !(s_cliente_servidor = this.entClienteFornecedor.getText().toUpperCase()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_cliente_servidor, 6));
            }
        }
        if (this.cbGrupoConta.getSelectedItem().toString() != null) {
            String s_grupo_contas = "";
            if (this.checkString(this.cbGrupoConta.getSelectedItem().toString()) && !(s_grupo_contas = this.cbGrupoConta.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_grupo_contas, 3));
            }
        }
        if (this.cbConta.getSelectedItem().toString() != null) {
            String s_contas = "";
            if (this.checkString(this.cbConta.getSelectedItem().toString()) && !(s_contas = this.cbConta.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_contas, 4));
            }
        }
        if (this.cbTipoFinanceiroParcelaCompleto.getSelectedItem().toString() != null) {
            String s_tipo_FinanceiroPagamentoCompleto = "";
            if (this.checkString(this.cbTipoFinanceiroParcelaCompleto.getSelectedItem().toString()) && !(s_tipo_FinanceiroPagamentoCompleto = this.cbTipoFinanceiroParcelaCompleto.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_tipo_FinanceiroPagamentoCompleto, 1));
            }
        }
        if (this.entIdentificadorGeral.getText() != null) {
            String s_id_geral = "";
            if (this.checkString(this.entIdentificadorGeral.getText())) {
                s_id_geral = this.entIdentificadorGeral.getText().toUpperCase();
                filters.add(RowFilter.regexFilter(s_id_geral, 7));
            }
        }
        if (this.cbStatus.getSelectedItem().toString() != null) {
            String s_status_condicao = "";
            if (this.checkString(this.cbStatus.getSelectedItem().toString()) && !(s_status_condicao = this.cbStatus.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_status_condicao, 10));
            }
        }
        if (this.cbFluxoCaixa.getSelectedItem().toString() != null) {
            String s_fluxo = "";
            if (this.checkString(this.cbFluxoCaixa.getSelectedItem().toString()) && !(s_fluxo = this.cbFluxoCaixa.getSelectedItem().toString()).equalsIgnoreCase("TODOS")) {
                filters.add(RowFilter.regexFilter(s_fluxo, 11));
            }
        }
        this.sorter.setRowFilter(RowFilter.andFilter(filters));
        this.calcular();
    }

    public void calcular() {
        BigDecimal valor_total_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_receitas = BigDecimal.ZERO;
        BigDecimal balanco = BigDecimal.ZERO;
        BigDecimal valor_total_transferencias = BigDecimal.ZERO;
        BigDecimal balanco_emprestimo = BigDecimal.ZERO;
        BigDecimal valor_total_pagamentos_emprestimo_despesas = BigDecimal.ZERO;
        int num_total_pagamentos = 0;
        int row = 0;
        while (row < this.tabela_parcela.getRowCount()) {
            int index = this.tabela_parcela.convertRowIndexToModel(row);
            FinanceiroParcelaCompleto pag = this.modelo_parcela.getValue(index);
            ++num_total_pagamentos;
            if (pag.getLancamento().getTipo_lancamento() == 0) {
                valor_total_despesas = valor_total_despesas.add(pag.getFpc().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 1) {
                valor_total_receitas = valor_total_receitas.add(pag.getFpc().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 2) {
                valor_total_transferencias = valor_total_transferencias.add(pag.getFpc().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 3) {
                valor_total_pagamentos_emprestimo_despesas = valor_total_pagamentos_emprestimo_despesas.add(pag.getFpc().getValor());
            } else if (pag.getLancamento().getTipo_lancamento() == 4) {
                valor_total_pagamentos_emprestimo_despesas = valor_total_pagamentos_emprestimo_despesas.add(pag.getFpc().getValor());
            }
            ++row;
        }
        balanco = valor_total_receitas.subtract(valor_total_despesas);
        Locale ptBr = new Locale("pt", "BR");
        this.lblNumTotalPagamentos.setText(String.valueOf(num_total_pagamentos));
        this.entValorTotalPagamentoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
        this.entValorTotalPagamentoReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
        this.entBalanco.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco));
        this.entValorTotalPagamentoTransferencia.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias));
        this.entValorTotalPagamentoEmprestimoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_emprestimo_despesas));
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public ArrayList<FinanceiroParcelaCompleto> getFinanceiroParecelaCompletosSelecionado() {
        ArrayList<FinanceiroParcelaCompleto> FinanceiroPagamentoCompletos_selecionados = new ArrayList<FinanceiroParcelaCompleto>();
        int[] linhas_selecionadas = this.tabela_parcela.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_parcela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            FinanceiroParcelaCompleto FinanceiroPagamentoCompleto_selecionado = this.lista_FinanceiroParcelasCompletos.get(indice);
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
        int[] linhas_selecionadas = this.tabela_parcela.getSelectedRows();
        int i = 0;
        while (i < linhas_selecionadas.length) {
            int indice = this.tabela_parcela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);
            Lancamento lancamento_selecionado = this.lista_FinanceiroParcelasCompletos.get(indice).getLancamento();
            lancamentos_selecionados.add(lancamento_selecionado);
            ++i;
        }
        return lancamentos_selecionados;
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

    public void popular_contas() {
        ArrayList<FinanceiroConta> lista_contas = new GerenciarBancoFinanceiroConta().getFinanceiroContas();
        this.cbConta.removeAllItems();
        this.cbConta.addItem("TODOS");
        for (FinanceiroConta contas : lista_contas) {
            this.cbConta.addItem(contas.getNome());
        }
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    class FinanceiroParcelaCompletosRender
    extends DefaultTableCellRenderer {
        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

        FinanceiroParcelaCompletosRender() {
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

    public class ParcelaTableModel
    extends AbstractTableModel {
        private final int id = 0;
        private final int tipo_lancamento = 1;
        private final int id_lancamento = 2;
        private final int grupo_contas = 3;
        private final int contas = 4;
        private final int centro_custo = 5;
        private final int cliente_fornecedor = 6;
        private final int identificador_geral = 7;
        private final int data_vencimento = 8;
        private final int valor = 9;
        private final int status = 10;
        private final int fluxo = 11;
        List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);
        private final String[] colunas = new String[]{"ID Parc", "Tipo Lan\u00e7amento", "ID Lanc", "Grupo de Contas", "Contas", "Centro Custo", "Cliente/Fornecedor", "Identificador Geral", "Data Vencimento", "Valor", "Status", "Fluxo Caixa"};
        private final ArrayList<FinanceiroParcelaCompleto> dados = new ArrayList();
        Locale ptBr = new Locale("pt", "BR");

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
                    return Integer.class;
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
                    return Date.class;
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
            FinanceiroParcelaCompleto dado = this.dados.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return dado.getFpc().getId_parcela();
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
                    return dado.getLancamento().getId_lancamento();
                }
                case 3: {
                    return dado.getLancamento().getNome_grupo_contas();
                }
                case 4: {
                    return dado.getLancamento().getNome_conta();
                }
                case 5: {
                    return dado.getLancamento().getNome_centro_custo();
                }
                case 6: {
                    return dado.getLancamento().getNome_cliente_fornecedor();
                }
                case 7: {
                    return dado.getFpc().getIdentificador();
                }
                case 8: {
                    if (dado.getFpc().getData_vencimento() != null && !dado.getFpc().getData_vencimento().equalsIgnoreCase("")) {
                        try {
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            Date data_pag = formato.parse(dado.getFpc().getData_vencimento());
                            return data_pag;
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                case 9: {
                    return NumberFormat.getCurrencyInstance(this.ptBr).format(dado.getFpc().getValor());
                }
                case 10: {
                    try {
                        String retorno = "";
                        int id_status = dado.getFpc().getStatus();
                        if (id_status == 0) {
                            retorno = "A PAGAR";
                        } else if (id_status == 1) {
                            retorno = "PAGO";
                        }
                        return retorno;
                    }
                    catch (Exception e) {
                        return "";
                    }
                }
                case 11: {
                    int flux = dado.getFpc().getFluxo_caixa();
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
            FinanceiroParcelaCompleto ib = this.dados.get(rowIndex);
        }

        public FinanceiroParcelaCompleto getValue(int rowIndex) {
            return this.dados.get(rowIndex);
        }

        public int indexOf(FinanceiroParcelaCompleto dado) {
            return this.dados.indexOf(dado);
        }

        public void onAdd(FinanceiroParcelaCompleto dado) {
            this.dados.add(dado);
            this.fireTableRowsInserted(this.indexOf(dado), this.indexOf(dado));
        }

        public void onAddAll(ArrayList<FinanceiroParcelaCompleto> dadosIn) {
            this.dados.addAll(dadosIn);
            this.fireTableDataChanged();
        }

        public void onRemove(int rowIndex) {
            this.dados.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void onRemove(FinanceiroParcelaCompleto dado) {
            int indexBefore = this.indexOf(dado);
            this.dados.remove(dado);
            this.fireTableRowsDeleted(indexBefore, indexBefore);
        }

        public void onRemoveAll() {
            this.dados.clear();
            this.fireTableDataChanged();
        }

        public FinanceiroParcelaCompleto onGet(int row) {
            return this.dados.get(row);
        }
    }
}

