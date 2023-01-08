
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

public class TelaFazenda
extends JFrame {
    private JPanel painelPrincipal;
    private TelaFazenda isto;
    private TableRowSorter<TelaFinanceiroLancamento.LancamentoTableModel> sorter;
    private GraficoMultiplaLinha linhaMultiplca = null;
    private Log GerenciadorLog;
    private CadastroLogin login;
    private ConfiguracoesGlobais configs_globais;
    private TelaPost telaPost;
    private GerenciarBancoContratos gerenciarAtualizarTarefas;
    private int num_tarefas_nesta_secao = -1;
    private boolean notificando = false;
    private ArrayList<Lancamento> lista_lancamentos = new ArrayList();
    private DefaultCategoryDataset dataset;
    private GraficoLinhaDupla linha = null;
    private ChartPanel chartPanel;

    public TelaFazenda(Window window) {
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
            this.setTitle("Fazendas");
            JMenuBar menuBar = new JMenuBar();
            JMenu mnNewMenu = new JMenu("Cadastros");
            mnNewMenu.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/financeiro_icone_24px.png")));
            mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.setFont(new Font("Segoe UI", 0, 18));
            menuBar.add(mnNewMenu);
            JMenuItem mntmNewMenuItem = new JMenuItem("Cliente/Fornecedor");
            mntmNewMenuItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {


                }
            });
            mntmNewMenuItem.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/equipe.png")));
            mntmNewMenuItem.setMargin(new Insets(0, 14, 0, 0));
            mntmNewMenuItem.setFont(new Font("Segoe UI", 0, 16));
            mnNewMenu.add(mntmNewMenuItem);
            
            JMenu mnNewMenu_1 = new JMenu("Estoque");
            mnNewMenu_1.setIcon(new ImageIcon(TelaFazenda.class.getResource("/imagens/estoque.png")));
            mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            mnNewMenu_1.setMargin(new Insets(0, 14, 0, 0));
            mnNewMenu.add(mnNewMenu_1);
            
            JMenu mnNewMenu_2 = new JMenu("Tabelas Auxiliares");
            mnNewMenu_2.setIcon(new ImageIcon(TelaFazenda.class.getResource("/imagens/tabela.png")));
            mnNewMenu_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            mnNewMenu_1.add(mnNewMenu_2);
            
            JMenuItem mntmNewMenuItem_2 = new JMenuItem("Tipos de Itens");
            mntmNewMenuItem_2.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		
            		TelaFazendaTipoItens tela = new TelaFazendaTipoItens(0, isto);
            		tela.setVisible(true);
            		
            		
            	}
            });
            mntmNewMenuItem_2.setIcon(new ImageIcon(TelaFazenda.class.getResource("/imagens/tipos.png")));
            mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            mnNewMenu_2.add(mntmNewMenuItem_2);
            
            JMenuItem mntmNewMenuItem_1 = new JMenuItem("Itens");
            mnNewMenu_2.add(mntmNewMenuItem_1);
            mntmNewMenuItem_1.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		
            		TelaFazendaItens tela = new TelaFazendaItens(0, isto);
            		tela.setVisible(true);
            		
            	}
            });
            mntmNewMenuItem_1.setIcon(new ImageIcon(TelaFazenda.class.getResource("/imagens/caixa.png")));
            mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            JMenuItem mntmNewMenuItem_876 = new JMenuItem("Fazendas");
            mntmNewMenuItem_876.setFont(new Font("Segoe UI", 0, 16));
            mntmNewMenuItem_876.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/centro_custo_24x24.jpg")));
            mntmNewMenuItem_876.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {


                }
            });
            mntmNewMenuItem_876.setMargin(new Insets(0, 10, 0, 0));
            mnNewMenu.add(mntmNewMenuItem_876);
            this.painelPrincipal = new JPanel();
            this.painelPrincipal.setBackground(Color.WHITE);
            this.painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.setContentPane(this.painelPrincipal);
            this.painelPrincipal.setLayout((LayoutManager)new MigLayout("", "[grow][grow][grow][242px,grow][grow][grow][grow][][][][60px:n][][]", "[78px][grow][grow][grow]"));
            this.painelPrincipal.add((Component)menuBar, "cell 0 0 4 1,alignx left,aligny center");
            GridBagConstraints gbc_panel_1 = new GridBagConstraints();
            gbc_panel_1.gridwidth = 13;
            gbc_panel_1.insets = new Insets(0, 0, 5, 5);
            gbc_panel_1.fill = 1;
            gbc_panel_1.gridx = 24;
            gbc_panel_1.gridy = 0;

            this.setExtendedState(6);
            this.setLocationRelativeTo(window);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

  

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.configs_globais = dados.getConfigs_globais();
        this.login = dados.getLogin();
    }

    



}

