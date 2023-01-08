/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.miginfocom.swing.MigLayout
 */
package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.EventoGlobal;
import main.java.cadastros.RegistroPontoDiario;
import main.java.cadastros.RegistroPontoDiarioCompleto;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoEventoGlobal;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoRotina;
import main.java.gui.TelaCalendario;
import main.java.gui.TelaFinanceiro;
import main.java.gui.TelaFuncionarios;
import main.java.gui.TelaFuncionariosAnalise;
import main.java.gui.TelaFuncionariosCartoesPonto;
import main.java.gui.TelaFuncionariosDepartamentos;
import main.java.gui.TelaFuncionariosSalarioMinimo;
import main.java.gui.TelaMain;
import main.java.gui.TelaNotas;
import main.java.gui.TelaPost;
import main.java.gui.TelaTarefas;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;

public class TelaRecursosHumanos
extends JFrame {
    private JPanel painelPrincipal;
    private TelaRecursosHumanos isto;
    private JLabel imgRfid;
    private JLabel lblStatusRelogioPonto;
    private JLabel lblUser;
    private JLabel lblDireitos;
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
    private JLabel lblRegistradoCorretamente;
    private JLabel lblFeriado;
    private JLabel lblDS;
    private JLabel lblFaltou;
    private JLabel lblNaoCompleto;
    private JLabel lblIsencaoPonto;
    private JLabel lblFolga;
    private JLabel lblFerias;
    private JLabel lblLicencas;
    private JLabel lblAtestadoMedico;

    public TelaRecursosHumanos(Window window) {
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
        this.setTitle("Recursos Humanos");
        JMenuBar menuBar = new JMenuBar();
        JMenu mnNewMenu = new JMenu("Cadastros");
        mnNewMenu.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/financeiro_icone_24px.png")));
        mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
        mnNewMenu.setFont(new Font("Segoe UI", 0, 18));
        menuBar.add(mnNewMenu);
        JMenuItem mntmNewMenuItem = new JMenuItem("Colaboradores");
        mntmNewMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionarios clientes = new TelaFuncionarios(1, TelaRecursosHumanos.this.isto);
                clientes.setVisible(true);
            }
        });
        mntmNewMenuItem.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/equipe.png")));
        mntmNewMenuItem.setMargin(new Insets(0, 14, 0, 0));
        mntmNewMenuItem.setFont(new Font("Segoe UI", 0, 16));
        mnNewMenu.add(mntmNewMenuItem);
        JMenu mnNewMenu_1 = new JMenu("Tabelas Auxiliares");
        mnNewMenu_1.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/tabela.png")));
        mnNewMenu_1.setMargin(new Insets(0, 14, 0, 0));
        mnNewMenu_1.setFont(new Font("Segoe UI", 0, 16));
        mnNewMenu.add(mnNewMenu_1);
        JMenuItem mntmNewMenuItem_1_2_1 = new JMenuItem("Cart\u00f5es de Ponto");
        mntmNewMenuItem_1_2_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionariosCartoesPonto tela = new TelaFuncionariosCartoesPonto(0, TelaRecursosHumanos.this.isto);
                tela.setVisible(true);
            }
        });
        mntmNewMenuItem_1_2_1.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/cracha.png")));
        mntmNewMenuItem_1_2_1.setMargin(new Insets(0, 14, 0, 0));
        mntmNewMenuItem_1_2_1.setFont(new Font("Segoe UI", 0, 16));
        mnNewMenu_1.add(mntmNewMenuItem_1_2_1);
        JMenuItem mntmNewMenuItem_1_2 = new JMenuItem("Sal\u00e1rio M\u00ednimo");
        mntmNewMenuItem_1_2.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/salario.png")));
        mntmNewMenuItem_1_2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionariosSalarioMinimo tela = new TelaFuncionariosSalarioMinimo(0, TelaRecursosHumanos.this.isto);
                tela.setVisible(true);
            }
        });
        mntmNewMenuItem_1_2.setMargin(new Insets(0, 14, 0, 0));
        mntmNewMenuItem_1_2.setFont(new Font("Segoe UI", 0, 16));
        mnNewMenu_1.add(mntmNewMenuItem_1_2);
        JMenuItem mntmNewMenuItem_1_2_1_1 = new JMenuItem("Departamentos");
        mntmNewMenuItem_1_2_1_1.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/departamentos.png")));
        mntmNewMenuItem_1_2_1_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFuncionariosDepartamentos tela = new TelaFuncionariosDepartamentos(0, TelaRecursosHumanos.this.isto);
                tela.setVisible(true);
            }
        });
        mntmNewMenuItem_1_2_1_1.setMargin(new Insets(0, 14, 0, 0));
        mntmNewMenuItem_1_2_1_1.setFont(new Font("Segoe UI", 0, 16));
        mnNewMenu_1.add(mntmNewMenuItem_1_2_1_1);
        this.painelPrincipal = new JPanel();
        this.painelPrincipal.setBackground(Color.WHITE);
        this.painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.painelPrincipal);
        this.painelPrincipal.setLayout((LayoutManager)new MigLayout("", "[grow][grow][242px,grow][grow][grow][grow][grow][][][]", "[78px][][400px:n,grow]"));
        this.painelPrincipal.add((Component)menuBar, "cell 0 0 3 1,alignx left,aligny center");
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
                    TelaNotas.instance = new TelaNotas((Window)TelaRecursosHumanos.this.isto);
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
        mntmNewMenuItem_4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCalendario tela = new TelaCalendario((Window)TelaRecursosHumanos.this.isto);
                tela.setVisible(true);
            }
        });
        mntmNewMenuItem_4.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_calendario.png")));
        mntmNewMenuItem_4.setMargin(new Insets(0, 10, 0, 0));
        mnFerramentas.add(mntmNewMenuItem_4);
        JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tarefas");
        mntmNewMenuItem_5.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaTarefas tela_tarefas = new TelaTarefas((Window)TelaRecursosHumanos.this.isto);
                tela_tarefas.setVisible(true);
            }
        });
        mntmNewMenuItem_5.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_menu_tarefas.png")));
        mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
        mnFerramentas.add(mntmNewMenuItem_5);
        JMenuItem mntmNewMenuItem_5_1 = new JMenuItem("An\u00e1lise");
        mntmNewMenuItem_5_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (TelaRecursosHumanos.this.login != null) {
                    if (TelaRecursosHumanos.this.login.getConfigs_privilegios().getNivel_privilegios() <= 2) {
                        TelaFuncionariosAnalise tela = new TelaFuncionariosAnalise((Window)TelaRecursosHumanos.this.isto);
                        tela.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaRecursosHumanos.this.isto, "Requer Eleva\u00e7\u00e3o de Direitos");
                }
            }
        });
        mntmNewMenuItem_5_1.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/analise.png")));
        mntmNewMenuItem_5_1.setMargin(new Insets(0, 10, 0, 0));
        mnFerramentas.add(mntmNewMenuItem_5_1);
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 13;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = 1;
        gbc_panel_1.gridx = 24;
        gbc_panel_1.gridy = 0;
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(51, 153, 255));
        this.painelPrincipal.add((Component)panel_2, "cell 4 0 6 1,grow");
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
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        this.painelPrincipal.add((Component)panel, "cell 0 1 10 2,grow");
        panel.setLayout((LayoutManager)new MigLayout("", "[grow][grow]", "[grow][][grow][grow]"));
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(Color.WHITE);
        panel.add((Component)panel_3, "flowx,cell 0 0");
        panel_3.setLayout((LayoutManager)new MigLayout("", "[][][]", "[]"));
        JLabel lblNewLabel = new JLabel("Data:");
        panel_3.add((Component)lblNewLabel, "cell 0 0");
        lblNewLabel.setFont(new Font("Tahoma", 0, 24));
        this.entDataRegistrosPontos = new JTextField();
        panel_3.add((Component)this.entDataRegistrosPontos, "cell 1 0");
        this.entDataRegistrosPontos.setFont(new Font("Tahoma", 1, 20));
        this.entDataRegistrosPontos.setColumns(10);
        this.entDataRegistrosPontos.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JButton btnNewButton = new JButton("Pesquisar");
        btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaRecursosHumanos.this.pesquisar_rps();
            }
        });
        btnNewButton.setBackground(new Color(153, 0, 0));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setFont(new Font("SansSerif", 1, 16));
        panel_3.add((Component)btnNewButton, "cell 2 0");
        this.tabela_rps = new JTable(this.modeloRps);
        this.tabela_rps.setRowHeight(30);
        CellRenderRPDiario renderer = new CellRenderRPDiario();
        this.tabela_rps.setDefaultRenderer(Object.class, renderer);
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(Color.WHITE);
        panel.add((Component)panel_5, "cell 1 0,alignx right,growy");
        panel_5.setLayout((LayoutManager)new MigLayout("", "[][][][][][][][][][][][][][]", "[][][]"));
        JLabel lblRelgioDePonto = new JLabel("Rel\u00f3gio de Ponto:");
        lblRelgioDePonto.setForeground(Color.BLACK);
        lblRelgioDePonto.setFont(new Font("SansSerif", 1, 14));
        panel_5.add((Component)lblRelgioDePonto, "cell 13 0");
        this.imgRfid = new JLabel("");
        this.imgRfid.setIcon(new ImageIcon(TelaRecursosHumanos.class.getResource("/imagens/rfid_offline.png")));
        panel_5.add((Component)this.imgRfid, "cell 12 0 1 3,alignx right");
        this.lblStatusRelogioPonto = new JLabel("Status:");
        this.lblStatusRelogioPonto.setForeground(Color.BLACK);
        this.lblStatusRelogioPonto.setFont(new Font("SansSerif", 1, 14));
        panel_5.add((Component)this.lblStatusRelogioPonto, "cell 13 1");
        JScrollPane scrollPane = new JScrollPane(this.tabela_rps);
        panel.add((Component)scrollPane, "cell 0 1 2 2,grow");
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        panel.add((Component)panel_4, "cell 0 3,grow");
        panel_4.setLayout((LayoutManager)new MigLayout("", "[][][][]", "[][][][][][][][][][][][][]"));
        JLabel lblNewLabel_3_1 = new JLabel("Total de Colaboradores Ativos:");
        lblNewLabel_3_1.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)lblNewLabel_3_1, "cell 0 0");
        this.lblTotalColaboradores = new JLabel("Registrou Ponto Corretamente");
        this.lblTotalColaboradores.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblTotalColaboradores, "cell 1 0 3 1");
        JLabel lblNewLabel_3 = new JLabel("Legenda:");
        panel_4.add((Component)lblNewLabel_3, "cell 0 2,alignx right");
        JLabel lblNewLabel_5 = new JLabel("aaaa");
        lblNewLabel_5.setOpaque(true);
        lblNewLabel_5.setBackground(new Color(0, 51, 0));
        lblNewLabel_5.setForeground(new Color(0, 51, 0));
        panel_4.add((Component)lblNewLabel_5, "cell 0 3,alignx right,growy");
        JLabel lblRegistroPontoCompleto = new JLabel("RP Completo:");
        lblRegistroPontoCompleto.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblRegistroPontoCompleto, "cell 1 3,alignx right");
        this.lblRegistradoCorretamente = new JLabel("Registrou Ponto Corretamente");
        this.lblRegistradoCorretamente.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblRegistradoCorretamente, "cell 2 3 2 1");
        JLabel lblNewLabel_5_1 = new JLabel("aaaa");
        lblNewLabel_5_1.setOpaque(true);
        lblNewLabel_5_1.setForeground(new Color(153, 0, 0));
        lblNewLabel_5_1.setBackground(new Color(153, 0, 0));
        panel_4.add((Component)lblNewLabel_5_1, "cell 0 4,alignx right");
        JLabel lblFaltou_1 = new JLabel("Faltou:");
        lblFaltou_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFaltou_1, "cell 1 4,alignx right");
        this.lblFaltou = new JLabel("Faltou");
        this.lblFaltou.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblFaltou, "cell 2 4 2 1");
        JLabel lblNewLabel_5_1_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1.setOpaque(true);
        lblNewLabel_5_1_1.setForeground(new Color(255, 153, 0));
        lblNewLabel_5_1_1.setBackground(new Color(255, 153, 0));
        panel_4.add((Component)lblNewLabel_5_1_1, "cell 0 5,alignx right");
        JLabel lblRpIncompleto = new JLabel("RP Incompleto:");
        lblRpIncompleto.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblRpIncompleto, "cell 1 5,alignx right");
        this.lblNaoCompleto = new JLabel("N\u00e3o Completou o Registro de Ponto");
        this.lblNaoCompleto.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblNaoCompleto, "cell 2 5 2 1");
        JLabel lblNewLabel_5_1_1_2 = new JLabel("aaaa");
        lblNewLabel_5_1_1_2.setOpaque(true);
        lblNewLabel_5_1_1_2.setForeground(new Color(0, 102, 51));
        lblNewLabel_5_1_1_2.setBackground(new Color(0, 102, 0));
        panel_4.add((Component)lblNewLabel_5_1_1_2, "cell 0 6,alignx right");
        JLabel lblFolga_1_2 = new JLabel("Descanso Semanal:");
        lblFolga_1_2.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1_2, "cell 1 6,alignx right");
        this.lblDS = new JLabel("0");
        this.lblDS.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblDS, "cell 2 6 2 1");
        JLabel lblNewLabel_5_1_1_2_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1_2_1.setOpaque(true);
        lblNewLabel_5_1_1_2_1.setForeground(new Color(0, 51, 51));
        lblNewLabel_5_1_1_2_1.setBackground(new Color(0, 51, 51));
        panel_4.add((Component)lblNewLabel_5_1_1_2_1, "cell 0 7,alignx right");
        JLabel lblFolga_1_2_1 = new JLabel("Feriado:");
        lblFolga_1_2_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1_2_1, "cell 1 7,alignx right");
        this.lblFeriado = new JLabel("0");
        this.lblFeriado.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblFeriado, "cell 2 7 2 1");
        JLabel lblNewLabel_5_1_1_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1_1.setOpaque(true);
        lblNewLabel_5_1_1_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_5_1_1_1.setBackground(new Color(0, 102, 153));
        panel_4.add((Component)lblNewLabel_5_1_1_1, "cell 0 8,alignx right");
        JLabel lblFolga_1 = new JLabel("Folga:");
        lblFolga_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1, "cell 1 8,alignx right");
        this.lblFolga = new JLabel("Folga");
        this.lblFolga.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblFolga, "cell 2 8 2 1");
        JLabel lblNewLabel_5_1_1_1_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1_1_1.setOpaque(true);
        lblNewLabel_5_1_1_1_1.setForeground(new Color(153, 102, 0));
        lblNewLabel_5_1_1_1_1.setBackground(new Color(153, 102, 0));
        panel_4.add((Component)lblNewLabel_5_1_1_1_1, "cell 0 9,alignx right");
        JLabel lblFolga_1_1 = new JLabel("F\u00e9rias:");
        lblFolga_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1_1, "cell 1 9,alignx right");
        this.lblFerias = new JLabel("F\u00e9rias");
        this.lblFerias.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblFerias, "cell 2 9 2 1");
        JLabel lblNewLabel_5_1_1_1_1_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1_1_1_1.setOpaque(true);
        lblNewLabel_5_1_1_1_1_1.setForeground(new Color(102, 51, 51));
        lblNewLabel_5_1_1_1_1_1.setBackground(new Color(102, 51, 51));
        panel_4.add((Component)lblNewLabel_5_1_1_1_1_1, "cell 0 10,alignx right");
        JLabel lblFolga_1_1_1 = new JLabel("Licen\u00e7a:");
        lblFolga_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1_1_1, "cell 1 10,alignx right");
        this.lblLicencas = new JLabel("0");
        this.lblLicencas.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblLicencas, "cell 2 10 2 1");
        JLabel lblNewLabel_5_1_1_1_1_1_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1_1_1_1_1.setOpaque(true);
        lblNewLabel_5_1_1_1_1_1_1.setForeground(new Color(51, 51, 153));
        lblNewLabel_5_1_1_1_1_1_1.setBackground(new Color(51, 51, 153));
        panel_4.add((Component)lblNewLabel_5_1_1_1_1_1_1, "cell 0 11,alignx right");
        JLabel lblFolga_1_1_1_1 = new JLabel("Isen\u00e7\u00e3o de Ponto:");
        lblFolga_1_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1_1_1_1, "cell 1 11,alignx right");
        this.lblIsencaoPonto = new JLabel("0");
        this.lblIsencaoPonto.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblIsencaoPonto, "cell 2 11 2 1");
        JLabel lblNewLabel_5_1_1_1_1_1_1_1 = new JLabel("aaaa");
        lblNewLabel_5_1_1_1_1_1_1_1.setOpaque(true);
        lblNewLabel_5_1_1_1_1_1_1_1.setForeground(new Color(153, 153, 102));
        lblNewLabel_5_1_1_1_1_1_1_1.setBackground(new Color(153, 153, 102));
        panel_4.add((Component)lblNewLabel_5_1_1_1_1_1_1_1, "cell 0 12,alignx right");
        JLabel lblFolga_1_1_1_1_1 = new JLabel("Atestado M\u00e9dico:");
        lblFolga_1_1_1_1_1.setFont(new Font("SansSerif", 0, 16));
        panel_4.add((Component)lblFolga_1_1_1_1_1, "cell 1 12,alignx right");
        this.lblAtestadoMedico = new JLabel("0");
        this.lblAtestadoMedico.setFont(new Font("SansSerif", 1, 16));
        panel_4.add((Component)this.lblAtestadoMedico, "cell 2 12");
        new Thread(){

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Buscando conexao ao relogio");
                    int status = DadosGlobais.getInstance().getStatus_relogio();
                    if (status == 1) {
                        TelaRecursosHumanos.this.lblStatusRelogioPonto.setText("Rel\u00f3gio Conectado!");
                        TelaRecursosHumanos.this.imgRfid.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_online.png")));
                        continue;
                    }
                    System.out.println("erro ao se conectar ao relogio");
                    TelaRecursosHumanos.this.lblStatusRelogioPonto.setText("Rel\u00f3gio Desconectado!");
                    TelaRecursosHumanos.this.imgRfid.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/rfid_offline.png")));
                }
            }
        }.start();
        this.logar();
        this.pesquisar_rps();
        this.setExtendedState(6);
        this.setLocationRelativeTo(window);
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

    public boolean checkString(String txt) {
        return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
    }

    public void pesquisar_rps() {
        this.modeloRps.onRemoveAll();
        String data = this.entDataRegistrosPontos.getText();
        int dia_semana = -1;
        int total_cts_ativos = 0;
        CadastroFuncionarioEvento evt_global = null;
        try {
            LocalDate localDate6 = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            dia_semana = localDate6.getDayOfWeek().getValue();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.isto, "Data Inv\u00e1lida");
            return;
        }
        GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();
        GerenciarBancoRotina gerenciar_rotina = new GerenciarBancoRotina();
        ArrayList<CadastroFuncionario> lista_funcionarios = gerenciar.getfuncionarios();
        int total_falta = 0;
        int total_folga = 0;
        int total_completo = 0;
        int total_isencao = 0;
        int total_licenca = 0;
        int total_atestado = 0;
        int total_incompleto = 0;
        int total_ferias = 0;
        int total_descanso = 0;
        int total_feriado = 0;
        GerenciarBancoRegistroPonto gerenciar_rp = new GerenciarBancoRegistroPonto();
        GerenciarBancoFuncionariosContratoTrabalho gerenciar_ct = new GerenciarBancoFuncionariosContratoTrabalho();
        for (CadastroFuncionario cf : lista_funcionarios) {
            CadastroFuncionarioAdmissao ct = new CadastroFuncionarioAdmissao();
            ct = gerenciar_ct.getcontratoAtivoPorFuncionario(cf.getId_funcionario());
            if (ct == null) continue;
            ++total_cts_ativos;
            RegistroPontoDiarioCompleto rp = gerenciar_rp.getDemonstrativoFuncionarioData(cf.getId_funcionario(), data);
            GerenciarBancoFuncionariosEventos gerenciar_eventos = new GerenciarBancoFuncionariosEventos();
            ArrayList<CadastroFuncionarioEvento> eventos = gerenciar_eventos.getEventosPorColaborador(cf.getId_funcionario());
            GerenciarBancoEventoGlobal gerenciar_eventos_global = new GerenciarBancoEventoGlobal();
            ArrayList<EventoGlobal> listaEventosGlobais = gerenciar_eventos_global.getEventosPorData(data);
            boolean tem_folga = false;
            boolean tem_ferias = false;
            boolean tem_isencao = false;
            boolean tem_licenca = false;
            boolean tem_descanso = false;
            boolean tem_feriado = false;
            boolean tem_atestado = false;
            for (CadastroFuncionarioEvento cadastroFuncionarioEvento : eventos) {
                LocalDate dataFinalFerias;
                LocalDate dataInicialFerias;
                LocalDate hoje;
                if (cadastroFuncionarioEvento.getTipo_evento() == 0) {
                    if (!cadastroFuncionarioEvento.getData_folga().equalsIgnoreCase(data)) continue;
                    tem_folga = true;
                    break;
                }
                if (cadastroFuncionarioEvento.getTipo_evento() == 3) {
                    hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataInicialFerias = LocalDate.parse(cadastroFuncionarioEvento.getData_ferias_ida(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataFinalFerias = LocalDate.parse(cadastroFuncionarioEvento.getData_ferias_volta(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (hoje.isEqual(dataInicialFerias)) {
                        tem_ferias = true;
                        break;
                    }
                    if (hoje.isEqual(dataFinalFerias)) {
                        tem_ferias = true;
                        break;
                    }
                    if (!hoje.isAfter(dataInicialFerias) || !hoje.isBefore(dataFinalFerias)) continue;
                    tem_ferias = true;
                    break;
                }
                if (cadastroFuncionarioEvento.getTipo_evento() == 4) {
                    hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataInicialFerias = LocalDate.parse(cadastroFuncionarioEvento.getData_ferias_ida(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataFinalFerias = LocalDate.parse(cadastroFuncionarioEvento.getData_ferias_volta(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (hoje.isEqual(dataInicialFerias)) {
                        tem_isencao = true;
                        break;
                    }
                    if (hoje.isEqual(dataFinalFerias)) {
                        tem_isencao = true;
                        break;
                    }
                    if (!hoje.isAfter(dataInicialFerias) || !hoje.isBefore(dataFinalFerias)) continue;
                    tem_isencao = true;
                    break;
                }
                if (cadastroFuncionarioEvento.getTipo_evento() == 5) {
                    hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataInicialFerias = LocalDate.parse(cadastroFuncionarioEvento.getData_ferias_ida(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    dataFinalFerias = LocalDate.parse(cadastroFuncionarioEvento.getData_ferias_volta(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (hoje.isEqual(dataInicialFerias)) {
                        tem_licenca = true;
                        break;
                    }
                    if (hoje.isEqual(dataFinalFerias)) {
                        tem_licenca = true;
                        break;
                    }
                    if (!hoje.isAfter(dataInicialFerias) || !hoje.isBefore(dataFinalFerias)) continue;
                    tem_licenca = true;
                    break;
                }
                if (cadastroFuncionarioEvento.getTipo_evento() != 7) continue;
                hoje = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String data_saida = cadastroFuncionarioEvento.getData_saida();
                LocalDate date_saida = LocalDate.parse(data_saida, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (!date_saida.isEqual(hoje)) continue;
                tem_atestado = true;
                evt_global = cadastroFuncionarioEvento;
            }
            for (EventoGlobal eventoGlobal : listaEventosGlobais) {
                if (eventoGlobal.getTipo_evento() != 0) continue;
                tem_feriado = true;
                break;
            }
            if (rp != null && !tem_atestado) {
                if (!(rp.getEntrada1() == null || rp.getEntrada1().equalsIgnoreCase("") || rp.getEntrada2() == null || rp.getEntrada2().equalsIgnoreCase("") || rp.getSaida1() == null || rp.getSaida1().equalsIgnoreCase("") || rp.getSaida2() == null || rp.getSaida2().equalsIgnoreCase(""))) {
                    ++total_completo;
                } else {
                    ++total_incompleto;
                }
                rp.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(rp);
                continue;
            }
            RegistroPontoDiarioCompleto registroPontoDiarioCompleto = new RegistroPontoDiarioCompleto();
            if (tem_isencao) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("ISEN\u00c7\u00c3O DE PONTO");
                registroPontoDiarioCompleto.setSaida1("ISEN\u00c7\u00c3O DE PONTO");
                registroPontoDiarioCompleto.setEntrada2("ISEN\u00c7\u00c3O DE PONTO");
                registroPontoDiarioCompleto.setSaida2("ISEN\u00c7\u00c3O DE PONTO");
                ++total_isencao;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (!(tem_folga || tem_ferias || tem_isencao || tem_licenca || tem_feriado || tem_atestado)) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("FALTA");
                registroPontoDiarioCompleto.setSaida1("FALTA");
                registroPontoDiarioCompleto.setEntrada2("FALTA");
                registroPontoDiarioCompleto.setSaida2("FALTA");
                ++total_falta;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (tem_folga && !tem_ferias && !tem_feriado) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("FOLGA");
                registroPontoDiarioCompleto.setSaida1("FOLGA");
                registroPontoDiarioCompleto.setEntrada2("FOLGA");
                registroPontoDiarioCompleto.setSaida2("FOLGA");
                ++total_folga;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (!tem_feriado && !tem_folga && tem_ferias) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("F\u00c9RIAS");
                registroPontoDiarioCompleto.setSaida1("F\u00c9RIAS");
                registroPontoDiarioCompleto.setEntrada2("F\u00c9RIAS");
                registroPontoDiarioCompleto.setSaida2("F\u00c9RIAS");
                ++total_ferias;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (tem_isencao) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("ISEN\u00c7\u00c3O DE PONTO");
                registroPontoDiarioCompleto.setSaida1("ISEN\u00c7\u00c3O DE PONTO");
                registroPontoDiarioCompleto.setEntrada2("ISEN\u00c7\u00c3O DE PONTO");
                registroPontoDiarioCompleto.setSaida2("ISEN\u00c7\u00c3O DE PONTO");
                ++total_isencao;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (tem_licenca) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("LICEN\u00c7A");
                registroPontoDiarioCompleto.setSaida1("LICEN\u00c7A");
                registroPontoDiarioCompleto.setEntrada2("LICEN\u00c7A");
                registroPontoDiarioCompleto.setSaida2("LICEN\u00c7A");
                ++total_licenca;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (tem_feriado) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                registroPontoDiarioCompleto.setEntrada1("FERIADO");
                registroPontoDiarioCompleto.setSaida1("FERIADO");
                registroPontoDiarioCompleto.setEntrada2("FERIADO");
                registroPontoDiarioCompleto.setSaida2("FERIADO");
                ++total_feriado;
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            if (tem_atestado) {
                registroPontoDiarioCompleto.setData(data);
                registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                String hora1 = evt_global.getHora_entrada();
                String hora2 = evt_global.getHora_saida();
                ++total_atestado;
                if (evt_global.getMovimentacao() == 0) {
                    registroPontoDiarioCompleto.setEntrada1(String.valueOf(hora1) + " ATS. MED.");
                    registroPontoDiarioCompleto.setSaida1(String.valueOf(hora2) + " ATS. MED.");
                    if (rp.getEntrada2().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setEntrada2("FALTA");
                    } else {
                        registroPontoDiarioCompleto.setEntrada2(rp.getEntrada2());
                    }
                    if (rp.getSaida2().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setSaida2("FALTA");
                    } else {
                        registroPontoDiarioCompleto.setSaida2(rp.getSaida2());
                    }
                    if (rp.getEntrada3().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setEntrada3("");
                    } else {
                        registroPontoDiarioCompleto.setEntrada3(rp.getEntrada3());
                    }
                    if (rp.getSaida3().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setSaida3("");
                    } else {
                        registroPontoDiarioCompleto.setSaida3(rp.getSaida3());
                    }
                } else {
                    if (rp.getEntrada1().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setEntrada1("FALTA");
                    } else {
                        registroPontoDiarioCompleto.setEntrada1(rp.getEntrada1());
                    }
                    if (rp.getSaida1().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setSaida1("FALTA");
                    } else {
                        registroPontoDiarioCompleto.setSaida1(rp.getSaida1());
                    }
                    registroPontoDiarioCompleto.setEntrada2(String.valueOf(hora1) + " ATS. MED.");
                    registroPontoDiarioCompleto.setSaida2(String.valueOf(hora2) + " ATS. MED.");
                    if (rp.getEntrada3().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setEntrada3("");
                    } else {
                        registroPontoDiarioCompleto.setEntrada3(rp.getEntrada3());
                    }
                    if (rp.getSaida3().equalsIgnoreCase("")) {
                        registroPontoDiarioCompleto.setSaida3("");
                    } else {
                        registroPontoDiarioCompleto.setSaida3(rp.getSaida3());
                    }
                }
                registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
                this.modeloRps.onAdd(registroPontoDiarioCompleto);
                continue;
            }
            CadastroFuncionarioRotinaTrabalho rt = gerenciar_rotina.getRotinaDiaSemana(cf.getId_funcionario(), dia_semana);
            if (rt != null) {
                if (rt.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL") || rt.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL") || rt.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL") || rt.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")) {
                    if (rt.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL") && rt.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL") && rt.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL") && rt.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")) {
                        registroPontoDiarioCompleto.setData(data);
                        registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                        registroPontoDiarioCompleto.setEntrada1(rt.getHora_entrada1());
                        registroPontoDiarioCompleto.setSaida1(rt.getHora_saida1());
                        registroPontoDiarioCompleto.setEntrada2(rt.getHora_entrada2());
                        registroPontoDiarioCompleto.setSaida2(rt.getHora_saida2());
                        ++total_descanso;
                    } else {
                        registroPontoDiarioCompleto.setData(data);
                        registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                        if (!rt.getHora_entrada1().equalsIgnoreCase("DESCANSO SEMANAL")) {
                            registroPontoDiarioCompleto.setEntrada1("FALTA");
                        } else {
                            registroPontoDiarioCompleto.setEntrada1("DESCANSO SEMANAL");
                        }
                        if (!rt.getHora_saida1().equalsIgnoreCase("DESCANSO SEMANAL")) {
                            registroPontoDiarioCompleto.setSaida1("FALTA");
                        } else {
                            registroPontoDiarioCompleto.setSaida1("DESCANSO SEMANAL");
                        }
                        if (!rt.getHora_entrada2().equalsIgnoreCase("DESCANSO SEMANAL")) {
                            registroPontoDiarioCompleto.setEntrada2("FALTA");
                        } else {
                            registroPontoDiarioCompleto.setEntrada2("DESCANSO SEMANAL");
                        }
                        if (!rt.getHora_saida2().equalsIgnoreCase("DESCANSO SEMANAL")) {
                            registroPontoDiarioCompleto.setSaida2("FALTA");
                        } else {
                            registroPontoDiarioCompleto.setSaida2("DESCANSO SEMANAL");
                        }
                        ++total_falta;
                    }
                } else {
                    registroPontoDiarioCompleto.setData(data);
                    registroPontoDiarioCompleto.setNome_colaborador(String.valueOf(cf.getNome()) + " " + cf.getSobrenome());
                    registroPontoDiarioCompleto.setEntrada1("FALTA");
                    registroPontoDiarioCompleto.setSaida1("FALTA");
                    registroPontoDiarioCompleto.setEntrada2("FALTA");
                    registroPontoDiarioCompleto.setSaida2("FALTA");
                    ++total_falta;
                }
            }
            registroPontoDiarioCompleto.setNome_departamento(ct.getNome_departamento());
            this.modeloRps.onAdd(registroPontoDiarioCompleto);
        }
        this.lblFaltou.setText(String.valueOf(total_falta));
        this.lblRegistradoCorretamente.setText(String.valueOf(total_completo));
        this.lblNaoCompleto.setText(String.valueOf(total_incompleto));
        this.lblFolga.setText(String.valueOf(total_folga));
        this.lblFerias.setText(String.valueOf(total_ferias));
        this.lblLicencas.setText(String.valueOf(total_licenca));
        this.lblIsencaoPonto.setText(String.valueOf(total_isencao));
        this.lblDS.setText(String.valueOf(total_descanso));
        this.lblFeriado.setText(String.valueOf(total_feriado));
        this.lblAtestadoMedico.setText(String.valueOf(total_atestado));
        this.lblTotalColaboradores.setText(String.valueOf(total_cts_ativos));
    }

    class CellRenderRPDiario
    extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setHorizontalAlignment(0);
            String entrada1 = (String)TelaRecursosHumanos.this.tabela_rps.getValueAt(row, 4);
            String saida1 = (String)TelaRecursosHumanos.this.tabela_rps.getValueAt(row, 5);
            String entrada2 = (String)TelaRecursosHumanos.this.tabela_rps.getValueAt(row, 6);
            String saida2 = (String)TelaRecursosHumanos.this.tabela_rps.getValueAt(row, 7);
            if (!(entrada1 == null || entrada1.equalsIgnoreCase("FALTA") || entrada1.equalsIgnoreCase("FOLGA") || entrada1.equalsIgnoreCase("F\u00c9RIAS") || entrada1.equalsIgnoreCase("ISEN\u00c7\u00c3O DE PONTO") || entrada1.equalsIgnoreCase("LICEN\u00c7A") || entrada1.equalsIgnoreCase("DESCANSO SEMANAL") || entrada1.equalsIgnoreCase("FERIADO") || entrada1.contains("ATS. MED.") || entrada2.contains("ATS. MED."))) {
                if (saida1 == null || saida1.equalsIgnoreCase("") || entrada2 == null || entrada2.equalsIgnoreCase("") || saida2 == null || saida2.equalsIgnoreCase("")) {
                    this.setBackground(new Color(255, 153, 0));
                    this.setForeground(Color.white);
                } else {
                    this.setBackground(new Color(0, 51, 0));
                    this.setForeground(Color.white);
                }
            } else if (entrada1.equalsIgnoreCase("FALTA")) {
                this.setBackground(new Color(153, 0, 0));
                this.setForeground(Color.white);
            } else if (entrada1.equalsIgnoreCase("FOLGA")) {
                this.setBackground(new Color(0, 102, 153));
                this.setForeground(Color.white);
            } else if (entrada1.equalsIgnoreCase("F\u00c9RIAS")) {
                this.setBackground(new Color(153, 102, 0));
                this.setForeground(Color.white);
            } else if (entrada1.equalsIgnoreCase("ISEN\u00c7\u00c3O DE PONTO")) {
                this.setBackground(new Color(51, 51, 153));
                this.setForeground(Color.white);
            } else if (entrada1.equalsIgnoreCase("LICEN\u00c7A")) {
                this.setBackground(new Color(102, 51, 51));
                this.setForeground(Color.white);
            } else if (entrada1.equalsIgnoreCase("DESCANSO SEMANAL")) {
                this.setBackground(new Color(0, 102, 0));
                this.setForeground(Color.white);
            } else if (entrada1.equalsIgnoreCase("FERIADO")) {
                this.setBackground(new Color(0, 51, 51));
                this.setForeground(Color.white);
            } else if (entrada1.contains("ATS. MED.") || entrada2.contains("ATS. MED.")) {
                this.setBackground(new Color(153, 153, 102));
                this.setForeground(Color.white);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public class RegistroPontoDiarioTableModel
    extends AbstractTableModel {
        private final int data = 0;
        private final int dia_semana = 1;
        private final int colaborador = 2;
        private final int departamento = 3;
        private final int entrada1 = 4;
        private final int saida1 = 5;
        private final int entrada2 = 6;
        private final int saida2 = 7;
        private final int entrada3 = 8;
        private final int saida3 = 9;
        private final String[] colunas = new String[]{"DATA", "DIA DA SEMANA", "COLABORADOR", "DEPARTAMENTO", "ENTRADA 1", "SA\u00cdDA 1", "ENTRADA 2", "SA\u00cdDA 2", "ENTRADA 3", "SA\u00cdDA 3"};
        private final ArrayList<RegistroPontoDiarioCompleto> dados = new ArrayList();

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
                    return Integer.class;
                }
                case 6: {
                    return String.class;
                }
                case 7: {
                    return Integer.class;
                }
                case 8: {
                    return String.class;
                }
                case 9: {
                    return Integer.class;
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
            RegistroPontoDiarioCompleto rp = this.dados.get(rowIndex);
            switch (columnIndex) {
                case 0: {
                    return rp.getData();
                }
                case 1: {
                    DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));
                    LocalDate data = LocalDate.parse(rp.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    DayOfWeek dia_s = data.getDayOfWeek();
                    return dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();
                }
                case 2: {
                    return rp.getNome_colaborador();
                }
                case 3: {
                    return rp.getNome_departamento();
                }
                case 4: {
                    return rp.getEntrada1();
                }
                case 5: {
                    return rp.getSaida1();
                }
                case 6: {
                    return rp.getEntrada2();
                }
                case 7: {
                    return rp.getSaida2();
                }
                case 8: {
                    return rp.getEntrada3();
                }
                case 9: {
                    return rp.getSaida3();
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
            RegistroPontoDiarioCompleto recebimento = this.dados.get(rowIndex);
        }

        public RegistroPontoDiarioCompleto getValue(int rowIndex) {
            return this.dados.get(rowIndex);
        }

        public int indexOf(RegistroPontoDiario nota) {
            return this.dados.indexOf(nota);
        }

        public void onAdd(RegistroPontoDiarioCompleto nota) {
            this.dados.add(nota);
            this.fireTableRowsInserted(this.indexOf(nota), this.indexOf(nota));
        }

        public void onAddAll(ArrayList<RegistroPontoDiarioCompleto> dadosIn) {
            this.dados.addAll(dadosIn);
            this.fireTableDataChanged();
        }

        public void onRemove(int rowIndex) {
            this.dados.remove(rowIndex);
            this.fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void onRemove(RegistroPontoDiarioCompleto nota) {
            int indexBefore = this.indexOf(nota);
            this.dados.remove(nota);
            this.fireTableRowsDeleted(indexBefore, indexBefore);
        }

        public void onRemoveAll() {
            this.dados.clear();
            this.fireTableDataChanged();
        }
    }
}

