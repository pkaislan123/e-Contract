/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.embed.swing.JFXPanel
 *  javafx.stage.FileChooser
 *  keeptoo.KGradientPanel
 *  net.miginfocom.swing.MigLayout
 *  org.apache.commons.io.FilenameUtils
 */
package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
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
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import keeptoo.KGradientPanel;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.SaldoInstituicaoBancaria;
import main.java.classesExtras.RenderizadorExtrato;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.gui.TelaFinanceiroCadastroInstituicaoBancaria;
import main.java.gui.TelaFinanceiroGerenciarLancamento;
import main.java.gui.TelaGerenciarCliente;
import main.java.gui.TelaRomaneios;
import main.java.gui.TelaTodasNotasFiscais;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.JPanelTransparent;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FilenameUtils;

public class TelaGerenciarInstituicaoBancaria
extends JFrame {
    private final JPanel painelPrincipal = new JPanel();
    private TelaGerenciarInstituicaoBancaria isto;
    private KGradientPanel menu_lateral;
    private JPanel panel_docs;
    private JTree arvore_documentos;
    private InstituicaoBancaria caixa_local;
    ArrayList<FinanceiroPagamentoCompleto> lista_extrato = new ArrayList();
    ArrayList<FinanceiroPagamentoCompleto> lista_extrato_emprestimo = new ArrayList();
    private DefaultListModel<FinanceiroPagamentoCompleto> listModelGlobal;
    private JComboBox cbStatusCondicaoPagamento;
    private JComboBox cbTipoLancamento;
    DefaultMutableTreeNode no_comprovantes;
    DefaultMutableTreeNode no_docs_pessoais;
    DefaultMutableTreeNode no_outros;
    private DefaultMutableTreeNode no_selecionado;
    private JComboBox cBTipoDocumento;
    private TelaTodasNotasFiscais telaTodasNotasFiscais;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private JTextField entCaminhoDocumento;
    private JTextField entNomeDocumento;
    private JTextArea entDescricaoDocumento;
    private TelaRomaneios telaRomaneio;
    private JLabel lblDadosBanco;
    private JLabel lblSaldo;
    private JLabel lblDadosConta;
    private JLabel lblDadosTitular;
    private JLabel lblSaldoInicial;
    private JLabel lblValorTotalDespesas;
    private JLabel lblValorTotalReceitas;
    private JLabel lblSaldoAtual;
    private RenderizadorExtrato render;
    private JComboBox cbCondicaoPagamento;
    private JTextField entNomePagador;
    private JTextField entNomeRecebedor;
    private JTextField entMenorData;
    private JTextField entMaiorData;

    public TelaGerenciarInstituicaoBancaria(final InstituicaoBancaria caixa, Window janela_pai) {
        this.isto = this;
        this.caixa_local = caixa;
        this.setResizable(true);
        this.setTitle("E-Contract - Gerenciar Conta Banc\u00e1ria");
        this.setBackground(new Color(255, 255, 255));
        this.setDefaultCloseOperation(2);
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
        this.painelPrincipal.setForeground(Color.BLACK);
        this.painelPrincipal.setBackground(Color.WHITE);
        this.painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.painelPrincipal);
        this.painelPrincipal.setLayout((LayoutManager)new MigLayout("", "[200px:n][grow][][grow][119px][113px]", "[grow][][11px][34px]"));
        JPanel panel_1 = new JPanel();
        panel_1.setAlignmentY(0.0f);
        panel_1.setAlignmentX(0.0f);
        panel_1.setBorder(null);
        panel_1.setMinimumSize(new Dimension(0, 0));
        panel_1.setBackground(Color.WHITE);
        this.painelPrincipal.add((Component)panel_1, "cell 1 0 5 4,grow");
        panel_1.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][grow]"));
        KGradientPanel panelTopo = new KGradientPanel();
        panel_1.add((Component)panelTopo, "cell 0 0,grow");
        panelTopo.kStartColor = new Color(0, 51, 0);
        panelTopo.kEndColor = new Color(0, 51, 0);
        panelTopo.setLayout((LayoutManager)new MigLayout("", "[263px][][][][][][][][][][][][][][][][][][][][][][][][][]", "[31px][][][][][]"));
        JLabel lblNewLabel = new JLabel("Minha Conta:");
        lblNewLabel.setFont(new Font("SansSerif", 0, 14));
        lblNewLabel.setForeground(Color.WHITE);
        panelTopo.add((Component)lblNewLabel, (Object)"cell 0 0,grow");
        this.lblDadosBanco = new JLabel("");
        this.lblDadosBanco.setForeground(Color.WHITE);
        this.lblDadosBanco.setFont(new Font("SansSerif", 0, 14));
        panelTopo.add((Component)this.lblDadosBanco, (Object)"cell 0 1");
        JLabel lblNewLabel_1_1 = new JLabel("Saldo:");
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("SansSerif", 1, 32));
        panelTopo.add((Component)lblNewLabel_1_1, (Object)"cell 9 1 5 2,alignx right");
        this.lblSaldo = new JLabel("R$ 100.000.000,00");
        this.lblSaldo.setForeground(Color.WHITE);
        this.lblSaldo.setFont(new Font("SansSerif", 1, 36));
        panelTopo.add((Component)this.lblSaldo, (Object)"cell 14 1 12 2,alignx left");
        this.lblDadosConta = new JLabel("");
        this.lblDadosConta.setForeground(Color.WHITE);
        this.lblDadosConta.setFont(new Font("SansSerif", 0, 14));
        panelTopo.add((Component)this.lblDadosConta, (Object)"cell 0 2");
        this.lblDadosTitular = new JLabel("");
        this.lblDadosTitular.setForeground(Color.WHITE);
        this.lblDadosTitular.setFont(new Font("SansSerif", 0, 14));
        panelTopo.add((Component)this.lblDadosTitular, (Object)"cell 0 3");
        final JPanel painelAreaTransferencia = new JPanel();
        painelAreaTransferencia.setBackground(Color.WHITE);
        panel_1.add((Component)painelAreaTransferencia, "cell 0 1,grow");
        painelAreaTransferencia.setLayout((LayoutManager)new MigLayout("", "[][]", "[grow][]"));
        final JPanel painelExtratos = new JPanel();
        painelExtratos.setAlignmentY(0.0f);
        painelExtratos.setAlignmentX(0.0f);
        painelExtratos.setOpaque(false);
        painelExtratos.setBorder(null);
        painelExtratos.setVisible(false);
        painelExtratos.setBackground(Color.WHITE);
        painelExtratos.setForeground(Color.WHITE);
        painelExtratos.setLayout((LayoutManager)new MigLayout("", "[][648.00]", "[][][grow]"));
        JPanel panel_3 = new JPanel();
        painelExtratos.add((Component)panel_3, "cell 0 0");
        panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_3.setBackground(Color.WHITE);
        panel_3.setLayout((LayoutManager)new MigLayout("", "[][][grow]", "[][][][][][][][][]"));
        JLabel lblNewLabel_2 = new JLabel("Filtros");
        lblNewLabel_2.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_2, "cell 0 0");
        JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Tipo Lan\u00e7amento:");
        lblNewLabel_1_2_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1_1.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_1_2_1_1_1, "cell 0 2,alignx right");
        this.cbTipoLancamento = new JComboBox();
        this.cbTipoLancamento.setFont(new Font("SansSerif", 0, 18));
        panel_3.add((Component)this.cbTipoLancamento, "cell 1 2 2 1,growx");
        this.cbTipoLancamento.addItem("TODOS");
        this.cbTipoLancamento.addItem("DESPESAS");
        this.cbTipoLancamento.addItem("RECEITAS");
        this.cbTipoLancamento.addItem("TRANSFERENCIAS");
        this.cbTipoLancamento.addItem("EMPRESTIMOS MUTUADOS");
        this.cbTipoLancamento.addItem("EMPRESTIMOS TOMADOS");
        JLabel lblNewLabel_1_2_1_1_1_1 = new JLabel("Condi\u00e7\u00e3o do Pagamento:");
        lblNewLabel_1_2_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1_1_1.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_1_2_1_1_1_1, "cell 0 3,alignx trailing");
        this.cbCondicaoPagamento = new JComboBox();
        this.cbCondicaoPagamento.setFont(new Font("SansSerif", 0, 18));
        panel_3.add((Component)this.cbCondicaoPagamento, "cell 1 3 2 1,growx");
        JLabel lblNewLabel_1_2_1_1_1_1_1 = new JLabel("Status Pagamento:");
        lblNewLabel_1_2_1_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1_1_1_1.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_1_2_1_1_1_1_1, "cell 0 4,alignx trailing");
        this.cbStatusCondicaoPagamento = new JComboBox();
        this.cbStatusCondicaoPagamento.setFont(new Font("SansSerif", 0, 18));
        this.cbStatusCondicaoPagamento.addItem("TODOS");
        this.cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
        this.cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Conclu\u00eddo");
        panel_3.add((Component)this.cbStatusCondicaoPagamento, "cell 1 4 2 1,growx");
        JLabel lblNewLabel_1_2_1_1_1_1_1_1 = new JLabel("Pagador:");
        lblNewLabel_1_2_1_1_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1_1_1_1_1.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_1_2_1_1_1_1_1_1, "cell 0 5,alignx trailing");
        this.entNomePagador = new JTextField();
        this.entNomePagador.setFont(new Font("SansSerif", 0, 18));
        panel_3.add((Component)this.entNomePagador, "cell 1 5 2 1,growx");
        this.entNomePagador.setColumns(10);
        JLabel lblNewLabel_1_2_1_1_1_1_1_1_1 = new JLabel("Recebedor:");
        lblNewLabel_1_2_1_1_1_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1_1_1_1_1_1.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_1_2_1_1_1_1_1_1_1, "cell 0 6,alignx trailing");
        this.entNomeRecebedor = new JTextField();
        this.entNomeRecebedor.setFont(new Font("SansSerif", 0, 18));
        this.entNomeRecebedor.setColumns(10);
        panel_3.add((Component)this.entNomeRecebedor, "cell 1 6 2 1,growx");
        JLabel lblNewLabel_1_2_1_1_1_1_1_1_1_1 = new JLabel("Per\u00edodo:");
        lblNewLabel_1_2_1_1_1_1_1_1_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1_1_1_1_1_1_1.setFont(new Font("Arial", 0, 18));
        panel_3.add((Component)lblNewLabel_1_2_1_1_1_1_1_1_1_1, "cell 0 7,alignx trailing");
        JLabel lblNewLabel_3 = new JLabel("de ");
        panel_3.add((Component)lblNewLabel_3, "cell 1 7,alignx trailing");
        this.entMenorData = new JTextField();
        this.entMenorData.setFont(new Font("SansSerif", 0, 18));
        this.entMenorData.setColumns(10);
        panel_3.add((Component)this.entMenorData, "flowx,cell 2 7,growx");
        JButton btnLimparCampos = new JButton("Limpar Campos");
        btnLimparCampos.setForeground(Color.WHITE);
        btnLimparCampos.setFont(new Font("SansSerif", 1, 16));
        btnLimparCampos.setBackground(new Color(204, 0, 0));
        panel_3.add((Component)btnLimparCampos, "flowx,cell 2 8,alignx right");
        btnLimparCampos.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaGerenciarInstituicaoBancaria.this.limparCampos();
            }
        });
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFont(new Font("SansSerif", 1, 16));
        btnLimpar.setBackground(new Color(153, 51, 0));
        panel_3.add((Component)btnLimpar, "cell 2 8,alignx right");
        btnLimpar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaGerenciarInstituicaoBancaria.this.limpar();
            }
        });
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaGerenciarInstituicaoBancaria.this.filtrar();
            }
        });
        btnFiltrar.setBackground(new Color(0, 51, 51));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFont(new Font("SansSerif", 1, 16));
        panel_3.add((Component)btnFiltrar, "cell 2 8,alignx right");
        JLabel lblNewLabel_3_1 = new JLabel("at\u00e9");
        panel_3.add((Component)lblNewLabel_3_1, "cell 2 7");
        this.entMaiorData = new JTextField();
        this.entMaiorData.setFont(new Font("SansSerif", 0, 18));
        this.entMaiorData.setColumns(10);
        panel_3.add((Component)this.entMaiorData, "cell 2 7");
        JPanel panel_2 = new JPanel();
        panel_2.setOpaque(false);
        painelExtratos.add((Component)panel_2, "cell 0 1 1 2,grow");
        panel_2.setBackground(Color.WHITE);
        panel_2.setLayout((LayoutManager)new MigLayout("", "[grow][]", "[][][][][][:20px:20px,grow][grow]"));
        JLabel lblNewLabel_1 = new JLabel("Saldo Inicial:");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Arial", 0, 18));
        panel_2.add((Component)lblNewLabel_1, "cell 0 0,alignx right");
        this.lblSaldoInicial = new JLabel("R$ 100.00.000.0,00");
        this.lblSaldoInicial.setForeground(Color.BLACK);
        this.lblSaldoInicial.setFont(new Font("Arial", 1, 24));
        panel_2.add((Component)this.lblSaldoInicial, "cell 1 0");
        JLabel lblNewLabel_1_2 = new JLabel("Valor Total Despesas:");
        lblNewLabel_1_2.setForeground(Color.BLACK);
        lblNewLabel_1_2.setFont(new Font("Arial", 0, 18));
        panel_2.add((Component)lblNewLabel_1_2, "cell 0 1,alignx right");
        this.lblValorTotalDespesas = new JLabel("R$ 100.00.000.0,00");
        this.lblValorTotalDespesas.setForeground(Color.BLACK);
        this.lblValorTotalDespesas.setFont(new Font("Arial", 1, 24));
        panel_2.add((Component)this.lblValorTotalDespesas, "cell 1 1");
        JLabel lblNewLabel_1_2_1 = new JLabel("Valor Total Receitas:");
        lblNewLabel_1_2_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1.setFont(new Font("Arial", 0, 18));
        panel_2.add((Component)lblNewLabel_1_2_1, "cell 0 2,alignx right");
        this.lblValorTotalReceitas = new JLabel("R$ 100.00.000.0,00");
        this.lblValorTotalReceitas.setForeground(Color.BLACK);
        this.lblValorTotalReceitas.setFont(new Font("Arial", 1, 24));
        panel_2.add((Component)this.lblValorTotalReceitas, "cell 1 2");
        JLabel lblNewLabel_1_2_1_1 = new JLabel("Saldo Atual:");
        lblNewLabel_1_2_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_2_1_1.setFont(new Font("Arial", 0, 18));
        panel_2.add((Component)lblNewLabel_1_2_1_1, "cell 0 4,alignx right");
        this.lblSaldoAtual = new JLabel("R$ 100.00.000.0,00");
        this.lblSaldoAtual.setForeground(Color.BLACK);
        this.lblSaldoAtual.setFont(new Font("Arial", 1, 24));
        panel_2.add((Component)this.lblSaldoAtual, "cell 1 4");
        final JList<FinanceiroPagamentoCompleto> lista = new JList<FinanceiroPagamentoCompleto>();
        lista.setOpaque(false);
        lista.setBackground(Color.WHITE);
        MouseAdapter mouseListener = new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    FinanceiroPagamentoCompleto selectedItem = (FinanceiroPagamentoCompleto)lista.getSelectedValue();
                    Lancamento lancamento_selecionado = selectedItem.getLancamento();
                    TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_selecionado, TelaGerenciarInstituicaoBancaria.this.isto);
                    tela.setVisible(true);
                }
            }
        };
        lista.addMouseListener(mouseListener);
        JScrollPane scrollPaneListaExtrato = new JScrollPane(lista);
        scrollPaneListaExtrato.getVerticalScrollBar().setValue(scrollPaneListaExtrato.getVerticalScrollBar().getMaximum());
        painelExtratos.add((Component)scrollPaneListaExtrato, "cell 1 0 1 3,grow");
        scrollPaneListaExtrato.getViewport().setBackground(Color.white);
        scrollPaneListaExtrato.getViewport().setOpaque(false);
        this.listModelGlobal = new DefaultListModel();
        this.render = new RenderizadorExtrato();
        lista.setModel(this.listModelGlobal);
        lista.setCellRenderer(this.render);
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(0, 0, 153));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFont(new Font("SansSerif", 1, 14));
        btnEditar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinanceiroCadastroInstituicaoBancaria tela = new TelaFinanceiroCadastroInstituicaoBancaria(1, caixa, TelaGerenciarInstituicaoBancaria.this.isto);
                tela.setVisible(true);
            }
        });
        panelTopo.add((Component)btnEditar, (Object)"cell 0 5,alignx right,growy");
        this.menu_lateral = new KGradientPanel();
        this.menu_lateral.kStartColor = new Color(0, 51, 51);
        this.menu_lateral.kEndColor = new Color(0, 51, 0);
        this.painelPrincipal.add((Component)this.menu_lateral, "cell 0 0 1 4,grow");
        this.menu_lateral.setLayout((LayoutManager)new MigLayout("", "[181px]", "[grow][]"));
        JPanelTransparent panel = new JPanelTransparent();
        this.menu_lateral.add((Component)panel, (Object)"cell 0 0 1 2,growx,aligny center");
        panel.setLayout((LayoutManager)new MigLayout("", "[161px]", "[20px][20px][20px][20px]"));
        final JLabel btnContratos = new JLabel("Extrato");
        btnContratos.setForeground(Color.WHITE);
        btnContratos.setFont(new Font("Tahoma", 0, 16));
        btnContratos.setBackground(new Color(0, 0, 0, 100));
        panel.add((Component)btnContratos, "cell 0 1,growx,aligny top");
        final JLabel btnDocumentos = new JLabel("Documentos");
        btnDocumentos.setForeground(Color.WHITE);
        btnDocumentos.setFont(new Font("Tahoma", 0, 16));
        btnDocumentos.setBackground(new Color(0, 0, 0, 100));
        panel.add((Component)btnDocumentos, "cell 0 3,growx,aligny top");
        this.panel_docs = new JPanel();
        this.panel_docs.setBackground(Color.WHITE);
        final JPanel painelDocumentos = new JPanel();
        painelDocumentos.setBackground(new Color(0, 51, 0));
        painelDocumentos.setVisible(false);
        painelDocumentos.setEnabled(false);
        painelDocumentos.setLayout((LayoutManager)new MigLayout("", "[600px:n,grow][431px]", "[grow]"));
        painelDocumentos.add((Component)this.panel_docs, "cell 0 0,grow");
        JPanel painelInserirDocumento = new JPanel();
        painelInserirDocumento.setBackground(new Color(0, 51, 0));
        painelDocumentos.add((Component)painelInserirDocumento, "cell 1 0,growx,aligny center");
        painelInserirDocumento.setLayout((LayoutManager)new MigLayout("", "[46px][10px][231px][10px][89px]", "[27px][22px][85px][39px][23px]"));
        JLabel lblNewLabel_15 = new JLabel("Nome:");
        lblNewLabel_15.setForeground(Color.WHITE);
        lblNewLabel_15.setFont(new Font("SansSerif", 1, 16));
        painelInserirDocumento.add((Component)lblNewLabel_15, "cell 0 0 2 1,alignx right,aligny top");
        JLabel lblNewLabel_16 = new JLabel("Descri\u00e7\u00e3o:");
        lblNewLabel_16.setForeground(Color.WHITE);
        lblNewLabel_16.setFont(new Font("SansSerif", 1, 16));
        painelInserirDocumento.add((Component)lblNewLabel_16, "cell 0 2 2 1,alignx right,aligny top");
        this.entDescricaoDocumento = new JTextArea();
        this.entDescricaoDocumento.setFont(new Font("Arial", 1, 18));
        painelInserirDocumento.add((Component)this.entDescricaoDocumento, "cell 2 2 3 1,grow");
        JLabel lblNewLabel_17 = new JLabel("Arquivo:");
        lblNewLabel_17.setForeground(Color.WHITE);
        lblNewLabel_17.setFont(new Font("SansSerif", 1, 16));
        painelInserirDocumento.add((Component)lblNewLabel_17, "cell 0 3 2 1,alignx right,aligny center");
        this.entCaminhoDocumento = new JTextField();
        this.entCaminhoDocumento.setFont(new Font("Arial", 1, 18));
        this.entCaminhoDocumento.setColumns(10);
        painelInserirDocumento.add((Component)this.entCaminhoDocumento, "cell 2 3,grow");
        this.entNomeDocumento = new JTextField();
        this.entNomeDocumento.setFont(new Font("Arial", 1, 18));
        this.entNomeDocumento.setColumns(10);
        painelInserirDocumento.add((Component)this.entNomeDocumento, "cell 2 0 3 1,grow");
        JButton btnSelecionarDocumento = new JButton("Selecionar");
        btnSelecionarDocumento.setBackground(new Color(0, 0, 153));
        btnSelecionarDocumento.setForeground(Color.WHITE);
        btnSelecionarDocumento.setFont(new Font("Arial", 1, 18));
        btnSelecionarDocumento.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaGerenciarInstituicaoBancaria.this.selecionarDocumento();
            }
        });
        painelInserirDocumento.add((Component)btnSelecionarDocumento, "cell 4 3,growx,aligny center");
        JButton btnAdicionarDocumento = new JButton("Adicionar");
        btnAdicionarDocumento.setBackground(new Color(102, 0, 153));
        btnAdicionarDocumento.setForeground(Color.WHITE);
        btnAdicionarDocumento.setFont(new Font("Arial", 1, 18));
        btnAdicionarDocumento.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaGerenciarInstituicaoBancaria.this.adicionarNovoDocumento();
            }
        });
        painelInserirDocumento.add((Component)btnAdicionarDocumento, "cell 4 4,grow");
        JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
        lblNewLabel_16_1.setForeground(Color.WHITE);
        lblNewLabel_16_1.setFont(new Font("SansSerif", 1, 16));
        painelInserirDocumento.add((Component)lblNewLabel_16_1, "cell 0 1 2 1,alignx right,aligny center");
        this.cBTipoDocumento = new JComboBox();
        this.cBTipoDocumento.setFont(new Font("Arial", 1, 18));
        painelInserirDocumento.add((Component)this.cBTipoDocumento, "cell 2 1 3 1,grow");
        this.cBTipoDocumento.addItem("Documento Pessoal");
        this.cBTipoDocumento.addItem("Comprovantes");
        this.cBTipoDocumento.addItem("Outros");
        btnContratos.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                painelExtratos.setEnabled(true);
                painelExtratos.setVisible(true);
                painelDocumentos.setEnabled(false);
                painelDocumentos.setVisible(false);
                btnContratos.setOpaque(true);
                btnContratos.setBackground(new Color(0, 0, 0, 100));
                btnContratos.repaint();
                btnContratos.updateUI();
                btnDocumentos.setOpaque(false);
                btnDocumentos.setBackground(new Color(0, 0, 0, 100));
                btnDocumentos.repaint();
                btnDocumentos.updateUI();
                painelAreaTransferencia.removeAll();
                painelAreaTransferencia.add((Component)painelExtratos, "alignx center,growy");
                painelAreaTransferencia.repaint();
                painelAreaTransferencia.updateUI();
            }
        });
        btnDocumentos.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                painelDocumentos.setEnabled(true);
                painelDocumentos.setVisible(true);
                painelExtratos.setEnabled(false);
                painelExtratos.setVisible(false);
                btnDocumentos.setOpaque(true);
                btnDocumentos.setBackground(new Color(0, 0, 0, 100));
                btnDocumentos.repaint();
                btnDocumentos.updateUI();
                btnContratos.setOpaque(false);
                btnContratos.setBackground(new Color(0, 0, 0, 100));
                btnContratos.repaint();
                btnContratos.updateUI();
                painelAreaTransferencia.removeAll();
                painelAreaTransferencia.add((Component)painelDocumentos, "cell 0 0 2 2,grow");
                painelAreaTransferencia.repaint();
                painelAreaTransferencia.updateUI();
            }
        });
        boolean c = true;
        if (c) {
            this.setInfoConta(caixa.getId_conta());
            this.getDadosGlobais();
            this.setInformacoesDocumentos();
            this.popular_condicao_pagamento();
            GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
            Map<String, String> datas = gerenciar.pegarDatasPagamento(this.caixa_local.getId_instituicao_bancaria());
            this.entMenorData.setText(datas.get("menor_data_pagamento"));
            this.entMaiorData.setText(datas.get("maior_data_pagamento"));
            this.pesquisar_saldo(this.caixa_local);
            this.filtrar();
        }
        this.setExtendedState(6);
        this.setLocationRelativeTo(janela_pai);
    }

    public void pesquisarExtrato(ArrayList<FinanceiroPagamentoCompleto> lista_extrato, BigDecimal saldo_inicial) {
        this.listModelGlobal.clear();
        for (FinanceiroPagamentoCompleto not : lista_extrato) {
            int tipo_lancamento = not.getLancamento().getTipo_lancamento();
            if (tipo_lancamento == 0) {
                saldo_inicial = saldo_inicial.subtract(not.getFpag().getValor());
            } else if (tipo_lancamento == 1) {
                saldo_inicial = saldo_inicial.add(not.getFpag().getValor());
            } else if (tipo_lancamento == 2) {
                if (not.getFpag().getId_pagador() == this.caixa_local.getId_instituicao_bancaria()) {
                    saldo_inicial = saldo_inicial.subtract(not.getFpag().getValor());
                } else if (not.getFpag().getId_pagador() != this.caixa_local.getId_instituicao_bancaria()) {
                    saldo_inicial = saldo_inicial.add(not.getFpag().getValor());
                }
            } else if (tipo_lancamento == 3) {
                saldo_inicial = not.getFpag().getTipo_pagamento() == 1 ? saldo_inicial.subtract(not.getFpag().getValor()) : saldo_inicial.add(not.getFpag().getValor());
            } else if (tipo_lancamento == 4) {
                saldo_inicial = not.getFpag().getTipo_pagamento() == 0 ? saldo_inicial.subtract(not.getFpag().getValor()) : saldo_inicial.add(not.getFpag().getValor());
            }
            not.setSaldo_atual(saldo_inicial);
            not.setId_caixa(this.caixa_local.getId_instituicao_bancaria());
            this.listModelGlobal.addElement(not);
        }
    }

    public void setInformacoesDocumentos() {
        GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
        final ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosPorIb(this.caixa_local.getId_instituicao_bancaria());
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Ra\u00edz");
                TelaGerenciarInstituicaoBancaria.this.no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
                TelaGerenciarInstituicaoBancaria.this.no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
                TelaGerenciarInstituicaoBancaria.this.no_outros = new DefaultMutableTreeNode("Outros");
                root.add(TelaGerenciarInstituicaoBancaria.this.no_docs_pessoais);
                root.add(TelaGerenciarInstituicaoBancaria.this.no_comprovantes);
                root.add(TelaGerenciarInstituicaoBancaria.this.no_outros);
                TelaGerenciarInstituicaoBancaria.this.arvore_documentos = new JTree(root);
                TelaGerenciarInstituicaoBancaria.this.arvore_documentos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener(){

                    @Override
                    public void valueChanged(TreeSelectionEvent e) {
                        no_selecionado = (DefaultMutableTreeNode) arvore_documentos.getLastSelectedPathComponent();
                    }
                });
                final JPopupMenu jPopupMenu = new JPopupMenu();
                JMenuItem jMenuItemVizualizar = new JMenuItem();
                JMenuItem jMenuItemExcluir = new JMenuItem();
                jMenuItemVizualizar.setText("Vizualizar");
                jMenuItemExcluir.setText("Excluir");
                jMenuItemVizualizar.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nome_arquivo = no_selecionado.getUserObject().toString();
                        String[] quebra = nome_arquivo.split("@");
                        String nome_official = "";
                        int i = 1;
                        while (i < quebra.length) {
                            nome_official = String.valueOf(nome_official) + quebra[i];
                            ++i;
                        }
                        String nome_pasta = caixa_local.getNome_instituicao_bancaria();
                        String unidade_base_dados = configs_globais.getServidorUnidade();
                        String caminho_salvar = String.valueOf(unidade_base_dados) + "\\" + "E-Contract\\arquivos\\financas\\ibs\\" + nome_pasta + "\\documentos\\" + nome_official;
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop desktop = Desktop.getDesktop();
                                File myFile = new File(caminho_salvar);
                                desktop.open(myFile);
                            }
                            catch (IOException iOException) {
                                // empty catch block
                            }
                        }
                    }
                });
                jMenuItemExcluir.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este Documento", "Exclus\u00e3o", 0, 3) == 0) {
                            String nome_arquivo = no_selecionado.getUserObject().toString();
                            String[] quebra = nome_arquivo.split("@");
                            String nome_official = "";
                            int i = 1;
                            while (i < quebra.length) {
                                nome_official = String.valueOf(nome_official) + quebra[i];
                                ++i;
                            }
                            String nome_pasta = caixa_local.getNome_instituicao_bancaria();
                            String unidade_base_dados = configs_globais.getServidorUnidade();
                            String caminho_completo = String.valueOf(unidade_base_dados) + "\\" + "E-Contract\\arquivos\\financas\\ibs\\" + nome_pasta + "\\documentos\\" + nome_official;
                            boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
                            if (excluido) {
                                GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
                                boolean excluir_documento = gerenciar_docs.removerDocumento(Integer.parseInt(quebra[0]));
                                if (excluir_documento) {
                                    JOptionPane.showMessageDialog(null, "Documento Excluido!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Arquivo fisico apagado, mas as informa\u00e7\u00f5es\ndeste documento ainda est\u00e3o no banco de dados\nConsulte o administrador");
                                }
                                TelaGerenciarInstituicaoBancaria.this.atualizarArvoreDocumentos();
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao excluir o documento\nConsulte o administrador!");
                            }
                        }
                    }
                });
                jPopupMenu.add(jMenuItemVizualizar);
                jPopupMenu.add(jMenuItemExcluir);
                TelaGerenciarInstituicaoBancaria.this.arvore_documentos.addMouseListener(new MouseAdapter(){

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == 3) {
                            jPopupMenu.show(arvore_documentos, e.getX(), e.getY());
                        }
                    }
                });
                TelaGerenciarInstituicaoBancaria.this.panel_docs.setLayout((LayoutManager)new MigLayout("", "[grow]", "[grow]"));
                TelaGerenciarInstituicaoBancaria.this.arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer(){
                    ImageIcon icone_docs_pessoais = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_docs_pessoais.png"));
                    ImageIcon icone_comprovantes = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_comprovantes.png"));
                    ImageIcon icone_outros = new ImageIcon(TelaGerenciarCliente.class.getResource("/imagens/icone_outros.png"));

                    @Override
                    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                        String s = node.getUserObject().toString();
                        if ("Documentos Pessoais".equals(s)) {
                            this.setOpenIcon(this.icone_docs_pessoais);
                            this.setClosedIcon(this.icone_docs_pessoais);
                        } else if ("Comprovantes".equals(s)) {
                            this.setOpenIcon(this.icone_comprovantes);
                            this.setClosedIcon(this.icone_comprovantes);
                        } else if ("Outros".equals(s)) {
                            this.setOpenIcon(this.icone_outros);
                            this.setClosedIcon(this.icone_outros);
                        }
                        super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, this.hasFocus);
                        return this;
                    }
                });
                TelaGerenciarInstituicaoBancaria.this.arvore_documentos.setShowsRootHandles(true);
                TelaGerenciarInstituicaoBancaria.this.arvore_documentos.setRootVisible(false);
                TelaGerenciarInstituicaoBancaria.this.panel_docs.add((Component)TelaGerenciarInstituicaoBancaria.this.arvore_documentos, "cell 0 0,grow");
                if (lista_docs != null && lista_docs.size() > 0) {
                    for (CadastroDocumento doc : lista_docs) {
                        if (doc.getTipo() == 1) {
                            TelaGerenciarInstituicaoBancaria.this.no_docs_pessoais.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                            continue;
                        }
                        if (doc.getTipo() == 2) {
                            TelaGerenciarInstituicaoBancaria.this.no_comprovantes.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                            continue;
                        }
                        if (doc.getTipo() != 3) continue;
                        TelaGerenciarInstituicaoBancaria.this.no_outros.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                    }
                }
                TelaGerenciarInstituicaoBancaria.this.expandAllNodes(TelaGerenciarInstituicaoBancaria.this.arvore_documentos, 0, TelaGerenciarInstituicaoBancaria.this.arvore_documentos.getRowCount());
            }
        });
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        int i = startingIndex;
        while (i < rowCount) {
            tree.expandRow(i);
            ++i;
        }
        if (tree.getRowCount() != rowCount) {
            this.expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    public void selecionarDocumento() {
        JOptionPane.showMessageDialog(null, "Na pr\u00f3xima tela, importe o documento a anexar!");
        new JFXPanel();
        Platform.runLater(() -> {
            FileChooser d = new FileChooser();
            File file = d.showOpenDialog(null);
            String caminho_arquivo = "";
            if (file != null) {
                caminho_arquivo = file.getAbsolutePath();
                this.entCaminhoDocumento.setText(caminho_arquivo);
            }
        });
    }

    public void adicionarNovoDocumento() {
        String nome = this.entNomeDocumento.getText();
        String descricao = this.entDescricaoDocumento.getText();
        String caminho_arquivo = this.entCaminhoDocumento.getText();
        String[] nome_arquivo_original_conteudo = caminho_arquivo.split("\"");
        String nome_arquivo_original = nome_arquivo_original_conteudo[nome_arquivo_original_conteudo.length - 1];
        String extensaoDoArquivo = FilenameUtils.getExtension((String)nome_arquivo_original);
        try {
            ManipularTxt manipular = new ManipularTxt();
            String unidade_base_dados = this.configs_globais.getServidorUnidade();
            String nome_pasta = this.caixa_local.getNome_instituicao_bancaria();
            String caminho_salvar = String.valueOf(unidade_base_dados) + "\\E-Contract\\arquivos\\financas\\ibs\\" + nome_pasta + "\\documentos";
            manipular.criarDiretorio(caminho_salvar);
            GetData dados = new GetData();
            String dataString = dados.getData();
            String horaString = dados.getHora();
            if (caminho_arquivo.length() > 10) {
                if (!(nome.length() == 0 || nome.equals("") || nome.equals(" ") || nome.equals("          "))) {
                    String nome_arquivo = String.valueOf(this.caixa_local.getNome_instituicao_bancaria()) + "_" + nome + "_" + horaString.replaceAll(":", "_") + "." + extensaoDoArquivo;
                    String caminho_completo = String.valueOf(caminho_salvar) + "\\" + nome_arquivo;
                    boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);
                    if (movido) {
                        CadastroDocumento novo_documento = new CadastroDocumento();
                        novo_documento.setDescricao(descricao);
                        novo_documento.setNome(nome);
                        String s_tipo_documento = this.cBTipoDocumento.getSelectedItem().toString();
                        int tipo_documento = -1;
                        if (s_tipo_documento.equalsIgnoreCase("Documento Pessoal")) {
                            tipo_documento = 1;
                        } else if (s_tipo_documento.equalsIgnoreCase("Comprovantes")) {
                            tipo_documento = 2;
                        } else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
                            tipo_documento = 3;
                        }
                        novo_documento.setTipo(tipo_documento);
                        novo_documento.setId_pai(0);
                        novo_documento.setNome_arquivo(nome_arquivo);
                        novo_documento.setId_ib(this.caixa_local.getId_instituicao_bancaria());
                        GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
                        int cadastrar = gerenciar_doc.inserir_documento_padrao_ib(novo_documento);
                        if (cadastrar > 0) {
                            JOptionPane.showMessageDialog(this.isto, "Arquivo copiado e salvo na base de dados\nOrigem: " + caminho_arquivo + "\nDestino: " + caminho_completo);
                            this.entNomeDocumento.setText("");
                            this.entDescricaoDocumento.setText("");
                            this.entCaminhoDocumento.setText("");
                            this.atualizarArvoreDocumentos();
                        } else {
                            JOptionPane.showMessageDialog(this.isto, "Arquivo copiado, mas n\u00e3o pode ser salvo\nConsulte o adiministrador do sistema!");
                            if (!manipular.apagarArquivo(caminho_completo)) {
                                JOptionPane.showMessageDialog(this.isto, "Erro ao excluir arquivo!\nConsulte o administrador do sistema");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this.isto, "Arquivo  n\u00e3o pode ser copiado\nOrigem: " + caminho_arquivo + "\nDestino: " + caminho_completo + "\n Consulte o administrador!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this.isto, "Nome do arquivo invalido!");
                }
            } else {
                JOptionPane.showMessageDialog(this.isto, "Caminho do arquivo invalido!");
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    public void atualizarArvoreDocumentos() {
        new Thread(){

            @Override
            public void run() {
                GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
                final ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosPorIb(TelaGerenciarInstituicaoBancaria.this.caixa_local.getId_instituicao_bancaria());
                EventQueue.invokeLater(new Runnable(){

                    @Override
                    public void run() {
                        DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                        root.removeAllChildren();
                       no_docs_pessoais.removeAllChildren();
                        no_comprovantes.removeAllChildren();
                        no_outros.removeAllChildren();
                        no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
                       no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
                        no_outros = new DefaultMutableTreeNode("Outros");
                        root.add(no_docs_pessoais);
                        root.add(no_comprovantes);
                        root.add(no_outros);
                        if (lista_docs != null && lista_docs.size() > 0) {
                            for (CadastroDocumento doc : lista_docs) {
                                if (doc.getTipo() == 1) {
                                    no_docs_pessoais.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                                    continue;
                                }
                                if (doc.getTipo() == 2) {
                                    no_comprovantes.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                                    continue;
                                }
                                if (doc.getTipo() == 3) {
                                    no_outros.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                                    continue;
                                }
                                if (doc.getTipo() != 4) continue;
                                no_outros.add(new DefaultMutableTreeNode(String.valueOf(doc.getId_documento()) + "@" + doc.getNome_arquivo()));
                            }
                        }
                        model.reload();
                        expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());
                    }
                });
            }
        }.start();
    }

    public void setInfoConta(int id_conta) {
        GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
        ContaBancaria conta = gerenciar.getConta(id_conta);
        String texto_banco = "Banco: " + conta.getBanco() + " C\u00f3digo: " + conta.getCodigo();
        this.lblDadosBanco.setText(texto_banco);
        String texto_conta = "Ag\u00eancia: " + conta.getAgencia() + " Conta: " + conta.getConta();
        this.lblDadosConta.setText(texto_conta);
        String texto_titular = "CPF: " + conta.getCpf_titular() + " Nome: " + conta.getNome();
        this.lblDadosTitular.setText(texto_titular);
    }

    public void pesquisar_saldo(InstituicaoBancaria caixa) {
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        Map<String, String> datas = gerenciar.pegarDatasPagamento(this.caixa_local.getId_instituicao_bancaria());
        String maior_data = datas.get("maior_data_pagamento");
        GerenciarBancoFinanceiroPagamento gerenciar_fin = new GerenciarBancoFinanceiroPagamento();
        SaldoInstituicaoBancaria saldo = gerenciar_fin.getSaldoPorPeriodo(caixa.getId_instituicao_bancaria(), maior_data);
        Locale ptBr = new Locale("pt", "BR");
        double positivo = caixa.getSaldo_inicial().doubleValue() + saldo.getTotal_receita() + saldo.getTotal_receita_transferencia() + saldo.getTotal_emprestimos();
        double negativo = saldo.getTotal_despesa() + saldo.getTotal_despesa_transferencia() + saldo.getTotal_despesa_emprestimo();
        double saldo_final = positivo - negativo;
        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(saldo_final);
        this.lblSaldo.setText(valorString);
        this.lblSaldoInicial.setText(NumberFormat.getCurrencyInstance(ptBr).format(caixa.getSaldo_inicial()));
        this.lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(negativo));
        this.lblValorTotalReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(positivo));
        this.lblSaldoAtual.setText(valorString);
        System.out.println("Na fun\u00e7\u00e3o pesquisar_saldo\n Saldo Inicial: " + NumberFormat.getCurrencyInstance(ptBr).format(caixa.getSaldo_inicial().doubleValue()) + " Total Receitas: " + NumberFormat.getCurrencyInstance(ptBr).format(positivo) + " Total Despesas: " + NumberFormat.getCurrencyInstance(ptBr).format(negativo) + " Saldo Atual: " + valorString);
    }

    public void popular_condicao_pagamento() {
        ArrayList<CondicaoPagamento> lista_condicao_pagamentos = new GerenciarBancoCondicaoPagamentos().getCondicaoPagamentos();
        this.cbCondicaoPagamento.removeAllItems();
        this.cbCondicaoPagamento.addItem("TODOS");
        for (CondicaoPagamento cp : lista_condicao_pagamentos) {
            this.cbCondicaoPagamento.addItem(String.valueOf(cp.getId_condicao_pagamento()) + "-" + cp.getNome_condicao_pagamento());
        }
    }

    public void filtrar() {
        String nome_recebedor;
        String nome_pagador;
        int condicao_pagamento;
        int status_condicao_pagamento;
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimo = new GerenciarBancoFinanceiroPagamentoEmprestimo();
        this.lista_extrato = gerenciar.getFinanceiroPagamentosLancamentosPorCaixa(this.caixa_local.getId_instituicao_bancaria());
        this.lista_extrato_emprestimo = gerenciar_emprestimo.getFinanceiroPagamentosLancamentosPorCaixa(this.caixa_local.getId_instituicao_bancaria());
        this.lista_extrato.addAll(this.lista_extrato_emprestimo);
        Collections.sort(this.lista_extrato, new Comparator<FinanceiroPagamentoCompleto>(){

            @Override
            public int compare(FinanceiroPagamentoCompleto o1, FinanceiroPagamentoCompleto o2) {
                if (o1.getFpag().getData_pagamento() == null || o2.getFpag().getData_pagamento() == null) {
                    return 0;
                }
                LocalDate data_menor = LocalDate.parse(o1.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate data_maior = LocalDate.parse(o2.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return data_menor.compareTo(data_maior);
            }
        });
        List<FinanceiroPagamentoCompleto> lista_filtrada = this.lista_extrato;
        int tipo_lancamento_procurado = this.cbTipoLancamento.getSelectedIndex();
        if (tipo_lancamento_procurado != 0) {
            lista_filtrada = lista_extrato.stream().filter(p -> p.getLancamento().getTipo_lancamento() == tipo_lancamento_procurado - 1).collect(Collectors.toList());
        }
        if ((status_condicao_pagamento = this.cbStatusCondicaoPagamento.getSelectedIndex()) != 0) {
            lista_filtrada = this.lista_extrato.stream().filter(p -> p.getFpag().getStatus_pagamento() == status_condicao_pagamento - 1).collect(Collectors.toList());
        }
        if ((condicao_pagamento = this.cbCondicaoPagamento.getSelectedIndex()) != 0) {
            String condicao_completa = this.cbCondicaoPagamento.getSelectedItem().toString();
            String[] split = condicao_completa.split("-");
            int id_condicao = Integer.parseInt(split[0]);
           lista_filtrada = this.lista_extrato.stream().filter(p -> p.getFpag().getId_condicao_pagamento() == id_condicao).collect(Collectors.toList());
        }
        if (this.checkString(nome_pagador = this.entNomePagador.getText())) {
           lista_filtrada = this.lista_extrato.stream().filter(p -> p.getNome_pagador().toUpperCase().contains(nome_pagador.toUpperCase())).collect(Collectors.toList());
        }
        if (this.checkString(nome_recebedor = this.entNomeRecebedor.getText())) {
           lista_filtrada = this.lista_extrato.stream().filter(p -> p.getNome_recebedor().toUpperCase().contains(nome_recebedor.toUpperCase())).collect(Collectors.toList());
        }
        String s_data_menor = this.entMenorData.getText();
        String s_data_maior = this.entMaiorData.getText();
        try {
            LocalDate data_menor = LocalDate.parse(s_data_menor, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate data_maior = LocalDate.parse(s_data_maior, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            lista_filtrada = this.lista_extrato.stream().filter(p -> LocalDate.parse(p.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).compareTo(data_menor) >= 0 && LocalDate.parse(p.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).compareTo(data_maior) <= 0).collect(Collectors.toList());
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.pesquisarExtrato((ArrayList<FinanceiroPagamentoCompleto>)lista_filtrada, this.getSaldo(s_data_menor));
        this.calcular((ArrayList<FinanceiroPagamentoCompleto>)lista_filtrada, s_data_menor);
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public void limpar() {
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        this.lista_extrato = gerenciar.getFinanceiroPagamentosLancamentosPorCaixa(this.caixa_local.getId_instituicao_bancaria());
        GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimos = new GerenciarBancoFinanceiroPagamentoEmprestimo();
        this.lista_extrato_emprestimo = gerenciar_emprestimos.getFinanceiroPagamentosLancamentosPorCaixa(this.caixa_local.getId_instituicao_bancaria());
        this.lista_extrato.addAll(this.lista_extrato_emprestimo);
        Collections.sort(this.lista_extrato, new Comparator<FinanceiroPagamentoCompleto>(){

            @Override
            public int compare(FinanceiroPagamentoCompleto o1, FinanceiroPagamentoCompleto o2) {
                if (o1.getFpag().getData_pagamento() == null || o2.getFpag().getData_pagamento() == null) {
                    return 0;
                }
                LocalDate data_menor = LocalDate.parse(o1.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate data_maior = LocalDate.parse(o2.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return data_menor.compareTo(data_maior);
            }
        });
        this.pesquisarExtrato(this.lista_extrato, this.getSaldo("01/01/2010"));
        this.calcular(this.lista_extrato, "01/01/2010");
    }

    public void limparCampos() {
        GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
        Map<String, String> datas = gerenciar.pegarDatasPagamento(this.caixa_local.getId_instituicao_bancaria());
        this.entMenorData.setText(datas.get("menor_data_pagamento"));
        this.entMaiorData.setText(datas.get("maior_data_pagamento"));
        this.cbCondicaoPagamento.setSelectedIndex(0);
        this.cbTipoLancamento.setSelectedIndex(0);
        this.cbStatusCondicaoPagamento.setSelectedIndex(0);
        this.entNomePagador.setText("");
        this.entNomeRecebedor.setText("");
        this.limpar();
    }

    public BigDecimal getSaldo(String data) {
        GerenciarBancoFinanceiroPagamento gerenciar_fin = new GerenciarBancoFinanceiroPagamento();
        SaldoInstituicaoBancaria saldo = gerenciar_fin.getSaldoPorPeriodoInicial(this.caixa_local.getId_instituicao_bancaria(), data);
        Locale ptBr = new Locale("pt", "BR");
        double positivo = this.caixa_local.getSaldo_inicial().doubleValue() + saldo.getTotal_receita() + saldo.getTotal_receita_transferencia() + saldo.getTotal_emprestimos();
        double negativo = saldo.getTotal_despesa() + saldo.getTotal_despesa_transferencia() + saldo.getTotal_despesa_emprestimo();
        double saldo_final = positivo - negativo;
        System.out.println("Na fun\u00e7\u00e3o getSaldo\n Saldo Inicial: " + NumberFormat.getCurrencyInstance(ptBr).format(this.caixa_local.getSaldo_inicial().doubleValue()) + " Total Receitas: " + NumberFormat.getCurrencyInstance(ptBr).format(positivo) + " Total Despesas: " + NumberFormat.getCurrencyInstance(ptBr).format(negativo) + " Saldo Atual: " + NumberFormat.getCurrencyInstance(ptBr).format(saldo_final));
        return new BigDecimal(saldo_final);
    }

    public void calcular(ArrayList<FinanceiroPagamentoCompleto> lista_extrato, String data) {
        Locale ptBr = new Locale("pt", "BR");
        BigDecimal saldo_inicial = this.getSaldo(data);
        BigDecimal valor_total_despesas = BigDecimal.ZERO;
        BigDecimal valor_total_receitas = BigDecimal.ZERO;
        for (FinanceiroPagamentoCompleto pag_completo : lista_extrato) {
            if (pag_completo.getLancamento().getTipo_lancamento() == 0) {
                if (pag_completo.getFpag().getId_pagador() == this.caixa_local.getId_instituicao_bancaria()) {
                    valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());
                    continue;
                }
                valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());
                continue;
            }
            if (pag_completo.getLancamento().getTipo_lancamento() == 1) {
                if (pag_completo.getFpag().getId_pagador() == this.caixa_local.getId_instituicao_bancaria()) {
                    valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());
                    continue;
                }
                valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());
                continue;
            }
            if (pag_completo.getLancamento().getTipo_lancamento() == 2) {
                if (pag_completo.getFpag().getId_pagador() == this.caixa_local.getId_instituicao_bancaria()) {
                    valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());
                    continue;
                }
                valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());
                continue;
            }
            if (pag_completo.getLancamento().getTipo_lancamento() == 3) {
                if (pag_completo.getFpag().getTipo_pagamento() == 1) {
                    valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());
                    continue;
                }
                valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());
                continue;
            }
            if (pag_completo.getLancamento().getTipo_lancamento() != 4) continue;
            if (pag_completo.getFpag().getTipo_pagamento() == 0) {
                valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());
                continue;
            }
            valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());
        }
        BigDecimal saldo_atual = BigDecimal.ZERO;
        saldo_atual = saldo_inicial.add(valor_total_receitas).subtract(valor_total_despesas);
        this.lblSaldoInicial.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial));
        this.lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
        this.lblValorTotalReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
        this.lblSaldoAtual.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_atual));
        System.out.println("Na fun\u00e7\u00e3o calcular\n Saldo Inicial: " + NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial) + " Total Receitas: " + NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas) + " Total Despesas: " + NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas) + " Saldo Atual: " + NumberFormat.getCurrencyInstance(ptBr).format(saldo_atual));
    }
}

