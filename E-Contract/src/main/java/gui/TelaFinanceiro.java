
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableRowSorter;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.SaldoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.graficos.GraficoLinhaDupla;
import main.java.graficos.GraficoMultiplaLinha;
import main.java.gui.TelaCliente;
import main.java.gui.TelaFinanceiroCentroCusto;
import main.java.gui.TelaFinanceiroCondicaoPagamento;
import main.java.gui.TelaFinanceiroConta;
import main.java.gui.TelaFinanceiroGrupoConta;
import main.java.gui.TelaFinanceiroInstituicaoBancaria;
import main.java.gui.TelaFinanceiroLancamento;
import main.java.gui.TelaFinanceiroPagamento;
import main.java.gui.TelaFinanceiroParcelas;
import main.java.gui.TelaFinanceiroRecibos;
import main.java.gui.TelaFinanceiroRelatorios;
import main.java.gui.TelaMain;
import main.java.gui.TelaNotas;
import main.java.gui.TelaPost;
import main.java.gui.TelaTarefas;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TelaFinanceiro
extends JFrame {
    private JPanel painelPrincipal;
    private TelaFinanceiro isto;
    private TableRowSorter<TelaFinanceiroLancamento.LancamentoTableModel> sorter;
    private GraficoMultiplaLinha linhaMultiplca = null;
    private JLabel lblUser;
    private JLabel lblDireitos;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private TelaPost telaPost;
    private GerenciarBancoContratos gerenciarAtualizarTarefas;
    private int num_tarefas_nesta_secao = -1;
    private boolean notificando = false;
    private JPanel grafico_despesas;
    private JPanel grafico_receitas;
    private ArrayList<Lancamento> lista_lancamentos = new ArrayList();
    private JLabel lblSomatoriaValorTotalDespesasAPagar;
    private JLabel lblSomatoriaValorTotalReceitasAReceber;
    private JPanel painelGraficoLinha;
    private DefaultCategoryDataset dataset;
    private GraficoLinhaDupla linha = null;
    private ChartPanel chartPanel;
    private JTextField entAno;
    private JPanel painelGraficoSaldoBancario;
    private JLabel lblSaldoFinal;

    public TelaFinanceiro(Window window) {
        try {
            this.setDefaultCloseOperation(2);
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
            this.isto = this;
            this.setTitle("Finan\u00e7as");
            JMenuBar menuBar = new JMenuBar();
            JMenu mnNewMenu = new JMenu("Cadastros");
            mnNewMenu.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/financeiro_icone_24px.png")));
            mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.setFont(new Font("Segoe UI", 0, 18));
            menuBar.add(mnNewMenu);
            JMenuItem mntmNewMenuItem234567 = new JMenuItem("Institui\u00e7\u00e3o Banc\u00e1ria");
            mntmNewMenuItem234567.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_ibs_32px.png")));
            mntmNewMenuItem234567.setFont(new Font("Segoe UI", 0, 16));
            mntmNewMenuItem234567.setMargin(new Insets(0, 10, 0, 0));
            mntmNewMenuItem234567.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            JMenuItem mntmNewMenuItem = new JMenuItem("Cliente/Fornecedor");
            mntmNewMenuItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaCliente clientes = new TelaCliente(1, 0, TelaFinanceiro.this.isto);
                    clientes.setVisible(true);
                }
            });
            mntmNewMenuItem.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/equipe.png")));
            mntmNewMenuItem.setMargin(new Insets(0, 14, 0, 0));
            mntmNewMenuItem.setFont(new Font("Segoe UI", 0, 16));
            mnNewMenu.add(mntmNewMenuItem);
            mnNewMenu.add(mntmNewMenuItem234567);
            JMenuItem mntmNewMenuItem_876 = new JMenuItem("Centro de Custo");
            mntmNewMenuItem_876.setFont(new Font("Segoe UI", 0, 16));
            mntmNewMenuItem_876.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/centro_custo_24x24.jpg")));
            mntmNewMenuItem_876.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroCentroCusto tela = new TelaFinanceiroCentroCusto(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmNewMenuItem_876.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.add(mntmNewMenuItem_876);
            JMenuItem mntmNewMenuItem_26566 = new JMenuItem("Condi\u00e7\u00e3o de Pagamento");
            mntmNewMenuItem_26566.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_cp_32px.png")));
            mntmNewMenuItem_26566.setFont(new Font("Segoe UI", 0, 16));
            mntmNewMenuItem_26566.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmNewMenuItem_26566.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.add(mntmNewMenuItem_26566);
            JMenuItem mntmNewMenuItem_234 = new JMenuItem("Grupo de Contas");
            mntmNewMenuItem_234.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_grupo_contas_24px.png")));
            mntmNewMenuItem_234.setFont(new Font("Segoe UI", 0, 16));
            mntmNewMenuItem_234.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroGrupoConta tela = new TelaFinanceiroGrupoConta(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmNewMenuItem_234.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.add(mntmNewMenuItem_234);
            JMenuItem mntmNewMenuItem_42345 = new JMenuItem("Contas");
            mntmNewMenuItem_42345.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/conta-bancaria_24x24.png")));
            mntmNewMenuItem_42345.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroConta tela = new TelaFinanceiroConta(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmNewMenuItem_42345.setFont(new Font("Segoe UI", 0, 16));
            mntmNewMenuItem_42345.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.add(mntmNewMenuItem_42345);
            JMenu mnNewMenu_1 = new JMenu("Lan\u00e7amentos");
            mnNewMenu_1.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_lancamentos_36px.png")));
            mnNewMenu_1.setFont(new Font("Segoe UI", 0, 18));
            menuBar.add(mnNewMenu_1);
            JMenuItem lancamentos = new JMenuItem("Lan\u00e7amentos");
            lancamentos.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/caixa-registradora.png")));
            lancamentos.setMargin(new Insets(0, 10, 0, 0));
            lancamentos.setFont(new Font("Segoe UI", 0, 16));
            lancamentos.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroLancamento tela = new TelaFinanceiroLancamento(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mnNewMenu_1.add(lancamentos);
            JMenuItem mntmPagamentos = new JMenuItem("Pagamentos");
            mntmPagamentos.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroPagamento tela = new TelaFinanceiroPagamento(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            JMenuItem mntmParcelas = new JMenuItem("Parcelas");
            mntmParcelas.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/parcela.png")));
            mntmParcelas.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroParcelas tela = new TelaFinanceiroParcelas(-1, -1, TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmParcelas.setMargin(new Insets(0, 10, 0, 0));
            mntmParcelas.setFont(new Font("Segoe UI", 0, 16));
            mnNewMenu_1.add(mntmParcelas);
            mntmPagamentos.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/pagamento.png")));
            mntmPagamentos.setMargin(new Insets(0, 10, 0, 0));
            mntmPagamentos.setFont(new Font("Segoe UI", 0, 16));
            mnNewMenu_1.add(mntmPagamentos);
            JMenuItem mntmRelatoria = new JMenuItem("Relatoria");
            mntmRelatoria.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroRelatorios tela = new TelaFinanceiroRelatorios((Window)TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmRelatoria.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/relatorio.png")));
            mntmRelatoria.setMargin(new Insets(0, 10, 0, 0));
            mntmRelatoria.setFont(new Font("Segoe UI", 0, 16));
            mnNewMenu_1.add(mntmRelatoria);
            JMenuItem mntmRecibosEEmprstimos = new JMenuItem("Recibos");
            mntmRecibosEEmprstimos.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiroRecibos tela = new TelaFinanceiroRecibos((Window)TelaFinanceiro.this.isto);
                    tela.setVisible(true);
                }
            });
            mntmRecibosEEmprstimos.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/emprestimo.png")));
            mntmRecibosEEmprstimos.setMargin(new Insets(0, 10, 0, 0));
            mntmRecibosEEmprstimos.setFont(new Font("Segoe UI", 0, 16));
            mnNewMenu_1.add(mntmRecibosEEmprstimos);
            this.painelPrincipal = new JPanel();
            this.painelPrincipal.setBackground(Color.WHITE);
            this.painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.setContentPane(this.painelPrincipal);
            this.painelPrincipal.setLayout((LayoutManager)new MigLayout("", "[grow][grow][grow][242px,grow][grow][grow][grow][][][][60px:n][][]", "[78px][grow][grow][grow]"));
            this.painelPrincipal.add((Component)menuBar, "cell 0 0 4 1,alignx left,aligny center");
            JMenu mnFerramentas = new JMenu("Ferramentas");
            mnFerramentas.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/ferramentas-de-reparacao.png")));
            mnFerramentas.setMargin(new Insets(0, 10, 0, 0));
            mnFerramentas.setFont(new Font("Arial", 0, 18));
            menuBar.add(mnFerramentas);
            JMenuItem mntmNewMenuItem_3 = new JMenuItem("Anota\u00e7\u00f5es");
            mntmNewMenuItem_3.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (TelaNotas.instance == null) {
                        TelaNotas.instance = new TelaNotas((Window)TelaFinanceiro.this.isto);
                        TelaNotas.instance.setVisible(true);
                    } else {
                        TelaNotas.instance.setVisible(true);
                    }
                }
            });
            mntmNewMenuItem_3.setMargin(new Insets(0, 10, 0, 0));
            mntmNewMenuItem_3.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_notas.png")));
            mnFerramentas.add(mntmNewMenuItem_3);
            JMenuItem mntmNewMenuItem_4 = new JMenuItem("Calend\u00e1rio");
            mntmNewMenuItem_4.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_calendario.png")));
            mntmNewMenuItem_4.setMargin(new Insets(0, 10, 0, 0));
            mnFerramentas.add(mntmNewMenuItem_4);
            JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tarefas");
            mntmNewMenuItem_5.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaTarefas tela_tarefas = new TelaTarefas((Window)TelaFinanceiro.this.isto);
                    tela_tarefas.setVisible(true);
                }
            });
            mntmNewMenuItem_5.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_tarefas.png")));
            mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
            mnFerramentas.add(mntmNewMenuItem_5);
            GridBagConstraints gbc_panel_1 = new GridBagConstraints();
            gbc_panel_1.gridwidth = 13;
            gbc_panel_1.insets = new Insets(0, 0, 5, 5);
            gbc_panel_1.fill = 1;
            gbc_panel_1.gridx = 24;
            gbc_panel_1.gridy = 0;
            JPanel panel_2 = new JPanel();
            panel_2.setBackground(new Color(51, 153, 255));
            this.painelPrincipal.add((Component)panel_2, "cell 5 0 8 1,grow");
            panel_2.setLayout((LayoutManager)new MigLayout("", "[]", "[]"));
            this.lblUser = new JLabel();
            panel_2.add((Component)this.lblUser, "cell 0 0,alignx left,aligny top");
            this.lblUser.setText("<dynamic> <dynamic>");
            this.lblUser.setForeground(Color.BLACK);
            this.lblUser.setFont(new Font("Tahoma", 0, 18));
            this.lblUser.setBackground(Color.WHITE);
            this.lblDireitos = new JLabel();
            panel_2.add((Component)this.lblDireitos, "cell 0 1");
            this.lblDireitos.setText("Administrador do Sistema");
            this.lblDireitos.setForeground(Color.BLACK);
            this.lblDireitos.setFont(new Font("Tahoma", 0, 15));
            this.lblDireitos.setBackground(Color.WHITE);
            this.logar();
            JPanel painelConteudoGraficos = new JPanel();
            painelConteudoGraficos.setBackground(Color.WHITE);
            painelConteudoGraficos.setLayout((LayoutManager)new MigLayout("", "[1050px:1050px,grow][grow][::70px][]", "[::600px,grow][][grow]"));
            JScrollPane scrollPane_1 = new JScrollPane(painelConteudoGraficos);
            this.painelPrincipal.add((Component)scrollPane_1, "cell 0 1 13 1,grow");
            JLabel lblNewLabel_1 = new JLabel("Ano:");
            painelConteudoGraficos.add((Component)lblNewLabel_1, "cell 1 1,aligny top");
            lblNewLabel_1.setFont(new Font("SansSerif", 0, 16));
            this.entAno = new JTextField();
            painelConteudoGraficos.add((Component)this.entAno, "cell 2 1,aligny top");
            this.entAno.setFont(new Font("SansSerif", 1, 18));
            this.entAno.setColumns(10);
            this.entAno.setText(String.valueOf(new GetData().getAnoAtual()));
            this.entAno.addKeyListener(new KeyAdapter(){

                @Override
                public void keyTyped(KeyEvent e) {
                    String caracteres = "0987654321\b";
                    String s_valor = "";
                    if (!caracteres.contains(String.valueOf(e.getKeyChar()))) {
                        e.consume();
                    }
                }
            });
            JButton btnNewButton_2 = new JButton("Atualizar");
            painelConteudoGraficos.add((Component)btnNewButton_2, "cell 3 1,aligny top");
            btnNewButton_2.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    TelaFinanceiro.this.atualizarGrafico();
                }
            });
            btnNewButton_2.setBackground(new Color(51, 51, 0));
            btnNewButton_2.setForeground(Color.WHITE);
            btnNewButton_2.setFont(new Font("SansSerif", 1, 18));
            JPanel panel_1 = new JPanel();
            panel_1.setBackground(Color.WHITE);
            painelConteudoGraficos.add((Component)panel_1, "cell 0 2 4 1,grow");
            panel_1.setLayout((LayoutManager)new MigLayout("", "[grow][]", "[500px:500px,grow][grow]"));
            this.painelGraficoLinha = new JPanel();
            panel_1.add((Component)this.painelGraficoLinha, "cell 0 0,grow");
            this.painelGraficoLinha.setBackground(Color.WHITE);
            this.painelGraficoLinha.setLayout((LayoutManager)new MigLayout("", "[grow]", "[grow]"));
            this.painelGraficoSaldoBancario = new JPanel();
            this.painelGraficoSaldoBancario.setBackground(Color.WHITE);
            panel_1.add((Component)this.painelGraficoSaldoBancario, "cell 0 1,grow");
            JPanel panel_5 = new JPanel();
            panel_5.setBackground(Color.WHITE);
            panel_1.add((Component)panel_5, "cell 1 1,grow");
            panel_5.setLayout((LayoutManager)new MigLayout("", "[][]", "[][]"));
            JLabel lblNewLabel_2 = new JLabel("Saldo Final:");
            lblNewLabel_2.setFont(new Font("SansSerif", 0, 22));
            panel_5.add((Component)lblNewLabel_2, "cell 0 0,alignx center");
            this.lblSaldoFinal = new JLabel("R$ 0,00");
            this.lblSaldoFinal.setFont(new Font("SansSerif", 1, 32));
            panel_5.add((Component)this.lblSaldoFinal, "cell 0 1,alignx center");
            JPanel painelGraficosSuperiores = new JPanel();
            painelGraficosSuperiores.setBackground(Color.BLACK);
            painelConteudoGraficos.add((Component)painelGraficosSuperiores, "cell 0 0 4 1,grow");
            painelGraficosSuperiores.setLayout((LayoutManager)new MigLayout("", "[625px:625px,grow][625px:625px,grow]", "[350px:350px:350px][]"));
            JPanel painelPaiDespesas = new JPanel();
            painelGraficosSuperiores.add((Component)painelPaiDespesas, "cell 0 0");
            painelPaiDespesas.setBackground(Color.WHITE);
            painelPaiDespesas.setLayout((LayoutManager)new MigLayout("", "[][grow][grow][grow]", "[grow][grow][grow]"));
            this.grafico_despesas = new JPanel();
            this.grafico_despesas.setBorder(new LineBorder(new Color(0, 0, 0)));
            this.grafico_despesas.setBackground(Color.WHITE);
            painelPaiDespesas.add((Component)this.grafico_despesas, "cell 0 1 3 1,grow");
            this.grafico_despesas.setLayout(new BorderLayout(0, 0));
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            painelPaiDespesas.add((Component)panel, "cell 3 1,grow");
            panel.setLayout((LayoutManager)new MigLayout("", "[grow][]", "[][][][][][][][grow]"));
            JLabel lblNewLabel = new JLabel("Op\u00e7\u00f5es de Dados:");
            lblNewLabel.setFont(new Font("Tahoma", 1, 16));
            panel.add((Component)lblNewLabel, "cell 0 0,alignx center");
            JButton btnAgruparPorCon = new JButton("Conta");
            btnAgruparPorCon.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                            grafico_despesas.removeAll();
                            grafico_despesas.add((Component)TelaFinanceiro.this.criarGrafico1(0, 0, 0));
                           grafico_despesas.repaint();
                            grafico_despesas.updateUI();
                        }
                    });
                }
            });
            JButton btnNewButton = new JButton("Grupo");
            btnNewButton.setForeground(Color.WHITE);
            btnNewButton.setBackground(new Color(0, 0, 102));
            btnNewButton.setFont(new Font("Tahoma", 0, 16));
            btnNewButton.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                           grafico_despesas.removeAll();
                           grafico_despesas.add((Component)TelaFinanceiro.this.criarGrafico1(0, 1, 0));
                            grafico_despesas.repaint();
                           grafico_despesas.updateUI();
                        }
                    });
                }
            });
            JLabel lblAgruparPor = new JLabel("Agrupar por:");
            lblAgruparPor.setFont(new Font("Tahoma", 1, 16));
            panel.add((Component)lblAgruparPor, "cell 0 2");
            panel.add((Component)btnNewButton, "cell 0 3,alignx center");
            btnAgruparPorCon.setForeground(Color.WHITE);
            btnAgruparPorCon.setBackground(new Color(0, 0, 153));
            btnAgruparPorCon.setFont(new Font("Tahoma", 0, 16));
            panel.add((Component)btnAgruparPorCon, "cell 0 4,alignx center");
            JLabel lblSomatorias = new JLabel("Somat\u00f3ria ");
            lblSomatorias.setFont(new Font("Tahoma", 1, 16));
            panel.add((Component)lblSomatorias, "cell 0 5");
            JPanel panel_3 = new JPanel();
            panel_3.setBackground(Color.WHITE);
            panel.add((Component)panel_3, "cell 0 6,grow");
            panel_3.setLayout((LayoutManager)new MigLayout("", "[][]", "[]"));
            JLabel lblValorTotal = new JLabel("Valor Total:");
            panel_3.add((Component)lblValorTotal, "cell 0 0");
            lblValorTotal.setFont(new Font("Tahoma", 1, 16));
            this.lblSomatoriaValorTotalDespesasAPagar = new JLabel("R$ 000.000.000,00");
            this.lblSomatoriaValorTotalDespesasAPagar.setForeground(Color.RED);
            panel_3.add((Component)this.lblSomatoriaValorTotalDespesasAPagar, "cell 1 0");
            this.lblSomatoriaValorTotalDespesasAPagar.setFont(new Font("Tahoma", 1, 16));
            JPanel painelPaiReceitas = new JPanel();
            painelGraficosSuperiores.add((Component)painelPaiReceitas, "cell 1 0");
            painelPaiReceitas.setBackground(Color.WHITE);
            painelPaiReceitas.setLayout((LayoutManager)new MigLayout("", "[grow][][]", "[]"));
            this.grafico_receitas = new JPanel();
            painelPaiReceitas.add((Component)this.grafico_receitas, "cell 0 0");
            this.grafico_receitas.setBorder(new LineBorder(new Color(0, 0, 0)));
            this.grafico_receitas.setBackground(new Color(0, 0, 0));
            this.grafico_receitas.setLayout(new BorderLayout(0, 0));
            JPanel panel_4 = new JPanel();
            painelPaiReceitas.add((Component)panel_4, "cell 1 0,growy");
            panel_4.setBackground(Color.WHITE);
            panel_4.setLayout((LayoutManager)new MigLayout("", "[grow]", "[][][][][][][grow]"));
            JLabel lblNewLabel_3 = new JLabel("Op\u00e7\u00f5es de Dados:");
            lblNewLabel_3.setFont(new Font("Tahoma", 1, 16));
            panel_4.add((Component)lblNewLabel_3, "cell 0 0");
            JButton btnAgruparPorCon_1 = new JButton("Conta");
            btnAgruparPorCon_1.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                            grafico_receitas.removeAll();
                         grafico_receitas.add((Component)TelaFinanceiro.this.criarGrafico2(1, 0, 2));
                          grafico_receitas.repaint();
                           grafico_receitas.updateUI();
                        }
                    });
                }
            });
            JButton btnNewButton_1 = new JButton("Grupo");
            btnNewButton_1.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    EventQueue.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                           grafico_receitas.removeAll();
                           grafico_receitas.add((Component)TelaFinanceiro.this.criarGrafico2(1, 1, 2));
                            grafico_receitas.repaint();
                           grafico_receitas.updateUI();
                        }
                    });
                }
            });
            JLabel lblNewLabel_3_1 = new JLabel("Agrupar por:");
            lblNewLabel_3_1.setFont(new Font("Tahoma", 1, 16));
            panel_4.add((Component)lblNewLabel_3_1, "cell 0 2");
            btnNewButton_1.setForeground(Color.WHITE);
            btnNewButton_1.setFont(new Font("Tahoma", 0, 16));
            btnNewButton_1.setBackground(new Color(0, 0, 102));
            panel_4.add((Component)btnNewButton_1, "cell 0 3,alignx center");
            btnAgruparPorCon_1.setForeground(Color.WHITE);
            btnAgruparPorCon_1.setFont(new Font("Tahoma", 0, 16));
            btnAgruparPorCon_1.setBackground(new Color(0, 0, 153));
            panel_4.add((Component)btnAgruparPorCon_1, "cell 0 4,alignx center");
            JLabel lblSomatorias_1 = new JLabel("Somat\u00f3ria ");
            lblSomatorias_1.setFont(new Font("Tahoma", 1, 16));
            panel_4.add((Component)lblSomatorias_1, "cell 0 5");
            JPanel panel_3_1 = new JPanel();
            panel_3_1.setBackground(Color.WHITE);
            panel_4.add((Component)panel_3_1, "cell 0 6,grow");
            panel_3_1.setLayout((LayoutManager)new MigLayout("", "[][]", "[]"));
            JLabel lblValorTotal_1 = new JLabel("Valor Total:");
            lblValorTotal_1.setFont(new Font("Tahoma", 1, 16));
            panel_3_1.add((Component)lblValorTotal_1, "cell 0 0");
            this.lblSomatoriaValorTotalReceitasAReceber = new JLabel("R$\u00a00,00");
            this.lblSomatoriaValorTotalReceitasAReceber.setForeground(new Color(50, 205, 50));
            this.lblSomatoriaValorTotalReceitasAReceber.setFont(new Font("Tahoma", 1, 16));
            panel_3_1.add((Component)this.lblSomatoriaValorTotalReceitasAReceber, "cell 1 0");
            this.grafico_despesas.add((Component)this.criarGrafico1(0, 1, 0));
            this.grafico_receitas.add((Component)this.criarGrafico2(1, 1, 2));
            this.criarGraficoSaldoCaixa();
            this.setExtendedState(6);
            this.setLocationRelativeTo(window);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logar() {
        this.lblUser.setText(String.valueOf(this.login.getNome()) + " " + this.login.getSobrenome());
        if (this.login.getConfigs_privilegios().getNivel_privilegios() == 1) {
            if (this.login.getGenero().equals("Masculino")) {
                this.lblDireitos.setText("Administrador do Sistema");
            } else {
                this.lblDireitos.setText("Administradora do Sistema");
            }
        } else if (this.login.getConfigs_privilegios().getNivel_privilegios() == 2) {
            if (this.login.getGenero().equals("Masculino")) {
                this.lblDireitos.setText("Gerente Financeiro");
            } else {
                this.lblDireitos.setText("Gerente Financeira");
            }
        } else if (this.login.getConfigs_privilegios().getNivel_privilegios() == 3) {
            if (this.login.getGenero().equals("Masculino")) {
                this.lblDireitos.setText("Auxiliar Administrativo");
            } else {
                this.lblDireitos.setText("Auxiliar Administrativo");
            }
        }
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    public ChartPanel criarGrafico1(int flag_despesa_receita, int flag_conta_grupo_contas, int flag_status) {
        DefaultPieDataset pizza = new DefaultPieDataset();
        BigDecimal valor_total = BigDecimal.ZERO;
        BigDecimal valor_parcial = BigDecimal.ZERO;
        ArrayList<Lancamento> lancamentos = new GerenciarBancoLancamento().buscaLancamentosCompletosGrafico(flag_despesa_receita, flag_conta_grupo_contas, flag_status);
        for (Lancamento lancamento : lancamentos) {
            if (flag_conta_grupo_contas == 0) {
                if (lancamento.getNome_conta() != null) {
                    valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
                    pizza.setValue((Comparable)((Object)lancamento.getNome_conta()), (Number)valor_parcial);
                }
            } else if (flag_conta_grupo_contas == 1 && lancamento.getNome_grupo_contas() != null) {
                valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
                pizza.setValue((Comparable)((Object)lancamento.getNome_grupo_contas()), (Number)valor_parcial);
            }
            valor_total = valor_total.add(valor_parcial);
        }
        Locale ptBr = new Locale("pt", "BR");
        this.lblSomatoriaValorTotalDespesasAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
        JFreeChart grafico = null;
        if (flag_status == 0) {
            grafico = ChartFactory.createPieChart((String)"Lan\u00e7amentos de Despesas a Pagar", (PieDataset)pizza, (boolean)true, (boolean)true, (boolean)true);
        } else if (flag_status == 2) {
            grafico = ChartFactory.createPieChart((String)"Lan\u00e7amentos de Receitas a Receber", (PieDataset)pizza, (boolean)true, (boolean)true, (boolean)true);
        }
        grafico.setBackgroundPaint((Paint)Color.WHITE);
        ChartPanel painel = new ChartPanel(grafico);
        painel.setBackground(Color.white);
        painel.setBounds(0, 0, 350, 350);
        PiePlot plotagem = (PiePlot)grafico.getPlot();
        plotagem.setLabelGenerator((PieSectionLabelGenerator)new StandardPieSectionLabelGenerator("{0} ({2})"));
        plotagem.setLabelBackgroundPaint((Paint)new Color(220, 220, 220));
        plotagem.setBackgroundPaint((Paint)Color.WHITE);
        return painel;
    }

    public ChartPanel criarGrafico2(int flag_despesa_receita, int flag_conta_grupo_contas, int flag_status) {
        DefaultPieDataset pizza = new DefaultPieDataset();
        BigDecimal valor_total = BigDecimal.ZERO;
        BigDecimal valor_parcial = BigDecimal.ZERO;
        ArrayList<Lancamento> lancamentos = new GerenciarBancoLancamento().buscaLancamentosCompletosGrafico(flag_despesa_receita, flag_conta_grupo_contas, flag_status);
        for (Lancamento lancamento : lancamentos) {
            if (flag_conta_grupo_contas == 0) {
                if (lancamento.getNome_conta() != null) {
                    valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
                    pizza.setValue((Comparable)((Object)lancamento.getNome_conta()), (Number)valor_parcial);
                }
            } else if (flag_conta_grupo_contas == 1 && lancamento.getNome_grupo_contas() != null) {
                valor_parcial = lancamento.getValor().subtract(lancamento.getValor_ja_pago());
                pizza.setValue((Comparable)((Object)lancamento.getNome_grupo_contas()), (Number)valor_parcial);
            }
            valor_total = valor_total.add(valor_parcial);
        }
        Locale ptBr = new Locale("pt", "BR");
        this.lblSomatoriaValorTotalReceitasAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total));
        JFreeChart grafico = null;
        if (flag_status == 0) {
            grafico = ChartFactory.createPieChart((String)"Lan\u00e7amentos de Despesas a Pagar", (PieDataset)pizza, (boolean)true, (boolean)true, (boolean)true);
        } else if (flag_status == 2) {
            grafico = ChartFactory.createPieChart((String)"Lan\u00e7amentos de Receitas a Receber", (PieDataset)pizza, (boolean)true, (boolean)true, (boolean)true);
        }
        grafico.setBackgroundPaint((Paint)Color.WHITE);
        ChartPanel painel = new ChartPanel(grafico);
        painel.setBackground(Color.white);
        painel.setBounds(0, 0, 350, 350);
        PiePlot plotagem = (PiePlot)grafico.getPlot();
        plotagem.setLabelGenerator((PieSectionLabelGenerator)new StandardPieSectionLabelGenerator("{0} ({2})"));
        plotagem.setLabelBackgroundPaint((Paint)new Color(220, 220, 220));
        plotagem.setBackgroundPaint((Paint)Color.WHITE);
        return painel;
    }

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public void atualizarGrafico() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    int ano = Integer.parseInt(TelaFinanceiro.this.entAno.getText());
                    XYSeriesCollection dataset = new XYSeriesCollection();
                    TelaFinanceiro.this.painelGraficoLinha.removeAll();
                    Map<Integer, Double> lista_lancamentos_despesas = new GerenciarBancoLancamento().busca_lancamentos_grafico_linha_despesa_receita(0, ano);
                    TreeMap<Integer, Double> despesas = new TreeMap<Integer, Double>(lista_lancamentos_despesas);
                    XYSeries series1 = new XYSeries((Comparable)((Object)"DESPESAS"));
                    for (Map.Entry pair : despesas.entrySet()) {
                        series1.add((Number)pair.getKey(), (Number)pair.getValue());
                    }
                    Map<Integer, Double> lista_lancamentos_receitas = new GerenciarBancoLancamento().busca_lancamentos_grafico_linha_despesa_receita(1, ano);
                    TreeMap<Integer, Double> receitas = new TreeMap<Integer, Double>(lista_lancamentos_receitas);
                    XYSeries series2 = new XYSeries((Comparable)((Object)"Receitas"));
                    for (Map.Entry pair : receitas.entrySet()) {
                        series2.add((Number)pair.getKey(), (Number)pair.getValue());
                    }
                    dataset.addSeries(series1);
                    dataset.addSeries(series2);
                    GraficoMultiplaLinha linhaMultiplca = new GraficoMultiplaLinha();
                    linhaMultiplca.setDataset((XYDataset)dataset);
                    linhaMultiplca.setAplicarSimbolos(true);
                    TelaFinanceiro.this.chartPanel = linhaMultiplca.getGraficoLinha(TelaFinanceiro.this.painelGraficoLinha.getWidth(), TelaFinanceiro.this.painelGraficoLinha.getHeight(), "M\u00eas", "Despesas x Receitas", "Valor em Reais", 3);
                    TelaFinanceiro.this.chartPanel.setBackground(Color.white);
                    TelaFinanceiro.this.painelGraficoLinha.add((Component)TelaFinanceiro.this.chartPanel);
                    TelaFinanceiro.this.painelGraficoLinha.repaint();
                    TelaFinanceiro.this.painelGraficoLinha.updateUI();
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(TelaFinanceiro.this.isto, "Ano Incorreto");
                }
            }
        });
    }

    public HashMap<String, Double> pesquisarSaldoCaixas() {
        GerenciarBancoInstituicaoBancaria gerenciar_caixas = new GerenciarBancoInstituicaoBancaria();
        ArrayList<InstituicaoBancaria> caixas = gerenciar_caixas.getInstituicoesBancarias();
        GerenciarBancoFinanceiroPagamento gerenciar_fin = new GerenciarBancoFinanceiroPagamento();
        HashMap<String, Double> saldo_caixas_bancarios = new HashMap<String, Double>();
        double saldo_total = 0.0;
        for (InstituicaoBancaria caixa_local : caixas) {
            Map<String, String> datas = gerenciar_fin.pegarDatasPagamento(caixa_local.getId_instituicao_bancaria());
            String maior_data = datas.get("maior_data_pagamento");
            SaldoInstituicaoBancaria saldo = gerenciar_fin.getSaldoPorPeriodo(caixa_local.getId_instituicao_bancaria(), maior_data);
            double positivo = caixa_local.getSaldo_inicial().doubleValue() + saldo.getTotal_receita() + saldo.getTotal_receita_transferencia() + saldo.getTotal_emprestimos();
            double negativo = saldo.getTotal_despesa() + saldo.getTotal_despesa_transferencia() + saldo.getTotal_despesa_emprestimo();
            double saldo_final = positivo - negativo;
            saldo_total += saldo_final;
            System.out.println("Caixa: " + caixa_local.getNome_instituicao_bancaria() + " Saldo: " + saldo_final);
            saldo_caixas_bancarios.put(caixa_local.getNome_instituicao_bancaria(), saldo_final);
        }
        Locale ptBr = new Locale("pt", "BR");
        this.lblSaldoFinal.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_total));
        return saldo_caixas_bancarios;
    }

    public void criarGraficoSaldoCaixa() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                TelaFinanceiro.this.painelGraficoSaldoBancario.removeAll();
                TelaFinanceiro.this.painelGraficoSaldoBancario.repaint();
                TelaFinanceiro.this.painelGraficoSaldoBancario.updateUI();
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                HashMap<String, Double> saldos_caixas_bancarios = TelaFinanceiro.this.pesquisarSaldoCaixas();
                for (Map.Entry<String, Double> caixa : saldos_caixas_bancarios.entrySet()) {
                    dataset.addValue((Number)caixa.getValue(), (Comparable)((Object)caixa.getKey()), (Comparable)((Object)caixa.getKey()));
                }
                JFreeChart chart = ChartFactory.createBarChart((String)"Saldo de Caixas Banc\u00e1rios", (String)"Caixa", (String)"Valor em R$", (CategoryDataset)dataset, (PlotOrientation)PlotOrientation.VERTICAL, (boolean)true, (boolean)true, (boolean)false);
                chart.setBackgroundPaint((Paint)Color.white);
                CategoryPlot plot = chart.getCategoryPlot();
                plot.setBackgroundPaint((Paint)Color.white);
                plot.setDomainGridlinePaint((Paint)Color.white);
                plot.setRangeGridlinePaint((Paint)Color.white);
                plot.setOutlineVisible(false);
                NumberAxis range = (NumberAxis)plot.getRangeAxis();
                range.setTickUnit(new NumberTickUnit(1000000.0));
                BarRenderer renderer = (BarRenderer)plot.getRenderer();
                int contador_series = 0;
                Color[] cores_azul = new Color[10];
                Color[] cores_armarela = new Color[10];
                Color[] cores_vermelha = new Color[10];
                cores_azul[0] = new Color(25, 25, 112);
                cores_azul[1] = new Color(0, 0, 128);
                cores_azul[2] = new Color(0, 0, 139);
                cores_azul[3] = new Color(70, 130, 180);
                cores_azul[4] = new Color(0, 0, 205);
                cores_azul[5] = new Color(0, 0, 255);
                cores_azul[6] = new Color(30, 144, 255);
                cores_armarela[0] = new Color(240, 230, 140);
                cores_armarela[1] = new Color(255, 165, 0);
                cores_armarela[2] = new Color(255, 215, 0);
                cores_armarela[3] = new Color(255, 255, 0);
                cores_armarela[4] = new Color(253, 245, 230);
                cores_armarela[5] = new Color(255, 228, 196);
                cores_vermelha[0] = new Color(250, 128, 114);
                cores_vermelha[1] = new Color(255, 99, 71);
                cores_vermelha[2] = new Color(138, 43, 226);
                cores_vermelha[3] = new Color(255, 0, 0);
                cores_vermelha[4] = new Color(255, 127, 80);
                cores_vermelha[5] = new Color(165, 42, 42);
                int indice_cor_azul = 0;
                int indice_cor_amarela = 0;
                int indice_cor_vermelha = 0;
                renderer.setDefaultItemLabelGenerator((CategoryItemLabelGenerator)new StandardCategoryItemLabelGenerator());
                renderer.setDefaultItemLabelsVisible(true);
                for (Map.Entry<String, Double> caixa : saldos_caixas_bancarios.entrySet()) {
                    if (caixa.getValue() < 0.0) {
                        renderer.setSeriesPaint(contador_series, (Paint)cores_vermelha[indice_cor_vermelha]);
                        ++indice_cor_vermelha;
                    } else if (caixa.getValue() > 0.0 && caixa.getValue() < 100000.0) {
                        renderer.setSeriesPaint(contador_series, (Paint)cores_armarela[indice_cor_amarela]);
                        ++indice_cor_amarela;
                    } else {
                        renderer.setSeriesPaint(contador_series, (Paint)cores_azul[indice_cor_azul]);
                        ++indice_cor_azul;
                    }
                    ++contador_series;
                }
                plot.setRenderer((CategoryItemRenderer)renderer);
                CategoryAxis domainAxis = plot.getDomainAxis();
                domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions((double)0.5235987755982988));
                ChartPanel painel = new ChartPanel(chart);
                painel.setBackground(Color.white);
                painel.setBounds(0, 0, 1000, 1000);
                TelaFinanceiro.this.painelGraficoSaldoBancario.add((Component)painel);
                TelaFinanceiro.this.painelGraficoSaldoBancario.repaint();
                TelaFinanceiro.this.painelGraficoSaldoBancario.updateUI();
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

