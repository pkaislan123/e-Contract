/*
 * Decompiled with CFR 0.151.
 */
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import main.java.cadastros.CadastroLogin;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.gui.TelaCadastroUsuario;
import main.java.gui.TelaCriarTarefa;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class TelaUsuarios
extends JFrame {
    private final JPanel contentPanel;
    private static ArrayList<CadastroLogin> usuarios_pesquisados = new ArrayList();
    private static ArrayList<CadastroLogin> usuarios_disponiveis = new ArrayList();
    private CadastroLogin login;
    private CadastroLogin usuarioSelecionado;
    private Log GerenciadorLog;
    private JDialog telaPai;
    private JTable tabela;
    private JPopupMenu jPopupMenuStatus;
    DefaultTableModel modelo = new DefaultTableModel(){

        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    private final JPanel panel_2 = new JPanel();
    private TelaUsuarios isto;

    public void pesquisar() {
        this.modelo.setNumRows(0);
        GerenciarBancoLogin listaUsuarios = new GerenciarBancoLogin();
        usuarios_pesquisados = listaUsuarios.getUsuarios();
        usuarios_disponiveis.clear();
        for (CadastroLogin usuario : listaUsuarios.getUsuarios()) {
            int id = usuario.getId();
            String login = usuario.getLogin();
            String nome = usuario.getNome();
            String email = usuario.getEmail();
            String senha = usuario.getSenha();
            int status = usuario.getStatus();
            String s_status = "";
            s_status = status == 0 ? "Inativo" : "Ativo";
            this.modelo.addRow(new Object[]{id, s_status, login, nome, email});
            usuarios_disponiveis.add(usuario);
        }
    }

    public TelaUsuarios(final int flag_modo_tela, Window janela_pai) {
        this.getDadosGlobais();
        this.isto = this;
        final TelaUsuarios isto = this;
        this.setTitle("Usu\u00e1rios");
        this.getContentPane().setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 255));
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 857, 500);
        this.contentPanel = new JPanel();
        this.contentPanel.setBackground(new Color(255, 255, 255));
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPanel);
        this.contentPanel.setLayout(null);
        this.panel_2.setBackground(new Color(51, 0, 102));
        this.panel_2.setBounds(0, 0, 126, 523);
        this.contentPanel.add(this.panel_2);
        this.panel_2.setLayout(null);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(138, 115, 691, 270);
        panel.setLayout(null);
        this.contentPanel.add(panel);
        this.tabela = new JTable(this.modelo);
        this.tabela.setBackground(new Color(255, 255, 255));
        this.modelo.addColumn("Id");
        this.modelo.addColumn("Status");
        this.modelo.addColumn("Login");
        this.modelo.addColumn("Nome");
        this.modelo.addColumn("E-mail");
        this.tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        this.tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.pesquisar();
        panel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(this.tabela);
        scrollPane.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent arg0) {
                TelaUsuarios.this.pesquisar();
            }
        });
        scrollPane.setBounds(0, 0, 691, 270);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBackground(Color.WHITE);
        panel.add(scrollPane);
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(102, 0, 255));
        panel_1.setBounds(124, 25, 705, 77);
        this.contentPanel.add(panel_1);
        panel_1.setLayout(null);
        JLabel lblNewLabel = new JLabel("Gerenciar Usu\u00e1rios");
        lblNewLabel.setBackground(new Color(102, 0, 102));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", 0, 22));
        lblNewLabel.setBounds(25, 11, 397, 46);
        panel_1.add(lblNewLabel);
        JButton btnUsurio = new JButton("+ Usu\u00e1rio");
        btnUsurio.setForeground(Color.WHITE);
        btnUsurio.setBackground(new Color(0, 51, 0));
        btnUsurio.setBounds(678, 410, 120, 23);
        this.contentPanel.add(btnUsurio);
        btnUsurio.setFont(new Font("SansSerif", 0, 14));
        btnUsurio.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/imagens/add_usuario.png")));
        JButton btnEditar = new JButton("Editar");
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setBackground(Color.ORANGE);
        btnEditar.setBounds(544, 410, 120, 23);
        this.contentPanel.add(btnEditar);
        btnEditar.setFont(new Font("SansSerif", 0, 14));
        btnEditar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int indiceDaLinha = 0;
                indiceDaLinha = TelaUsuarios.this.tabela.getSelectedRow();
                String s_status = TelaUsuarios.this.modelo.getValueAt(indiceDaLinha, 1).toString();
                if (TelaUsuarios.this.login.getConfigs_privilegios().getNivel_privilegios() > 2) {
                    JOptionPane.showMessageDialog(null, "Requer Eleva\u00e7\u00e3o de Direitos \n Reportado ao Administrador");
                    TelaUsuarios.this.GerenciadorLog.registrarLogDiario("aviso", "tentativa de cria\u00e7\u00e3o de novo usu\u00e1rio");
                } else if (s_status.equalsIgnoreCase("Inativo")) {
                    JOptionPane.showMessageDialog(isto, "Desbloqueio o usu\u00e1rio para edi\u00e7\u00e3o");
                } else {
                    TelaCadastroUsuario telaCadastroUsuario = new TelaCadastroUsuario(1, usuarios_disponiveis.get(indiceDaLinha), isto);
                }
            }
        });
        btnEditar.setIcon(new ImageIcon(TelaUsuarios.class.getResource("/imagens/editar.png")));
        JButton btnSelecionar = new JButton("Selecionar");
        btnSelecionar.setBackground(new Color(0, 0, 102));
        btnSelecionar.setForeground(Color.WHITE);
        btnSelecionar.setBounds(403, 410, 120, 23);
        this.contentPanel.add(btnSelecionar);
        btnSelecionar.setFont(new Font("SansSerif", 0, 14));
        btnSelecionar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag_modo_tela == 1) {
                    int indiceDaLinha = TelaUsuarios.this.tabela.getSelectedRow();
                    TelaUsuarios.this.usuarioSelecionado = usuarios_disponiveis.get(indiceDaLinha);
                    ((TelaCriarTarefa)TelaUsuarios.this.telaPai).setExecutor(TelaUsuarios.this.usuarioSelecionado);
                    isto.dispose();
                }
            }
        });
        btnUsurio.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (TelaUsuarios.this.login.getConfigs_privilegios().getNivel_privilegios() > 2) {
                    JOptionPane.showMessageDialog(null, "Requer Eleva\u00e7\u00e3o de Direitos \n Reportado ao Administrador");
                    TelaUsuarios.this.GerenciadorLog.registrarLogDiario("aviso", "tentativa de cria\u00e7\u00e3o de novo usu\u00e1rio");
                } else {
                    TelaCadastroUsuario telaCadastroUsuario = new TelaCadastroUsuario(0, null, isto);
                }
            }
        });
        if (flag_modo_tela == 0) {
            btnSelecionar.setEnabled(false);
            btnSelecionar.setVisible(false);
        }
        this.setMenuStatus();
        this.setLocationRelativeTo(janela_pai);
    }

    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
        this.login = dados.getLogin();
    }

    public void setTelaPai(JDialog tela_pai) {
        this.telaPai = tela_pai;
    }

    public void setMenuStatus() {
        this.jPopupMenuStatus = new JPopupMenu();
        JMenuItem jMenuItemBloquear = new JMenuItem();
        JMenuItem jMenuItemDesloquear = new JMenuItem();
        jMenuItemBloquear.setText("Bloquear");
        jMenuItemDesloquear.setText("Desloquear");
        jMenuItemBloquear.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceDaLinha = TelaUsuarios.this.tabela.getSelectedRow();
                String s_status = TelaUsuarios.this.modelo.getValueAt(indiceDaLinha, 1).toString();
                if (TelaUsuarios.this.login.getConfigs_privilegios().getNivel_privilegios() > 2) {
                    JOptionPane.showMessageDialog(null, "Requer Eleva\u00e7\u00e3o de Direitos \n Reportado ao Administrador");
                    TelaUsuarios.this.GerenciadorLog.registrarLogDiario("aviso", "tentativa de cria\u00e7\u00e3o de novo usu\u00e1rio");
                } else if (s_status.equals("Inativo")) {
                    JOptionPane.showMessageDialog(TelaUsuarios.this.isto, "Usu\u00e1rio j\u00e1 esta bloqueado!");
                } else {
                    GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
                    int id = Integer.parseInt(TelaUsuarios.this.modelo.getValueAt(indiceDaLinha, 0).toString());
                    if (gerenciar.bloquearUsuario(id)) {
                        JOptionPane.showMessageDialog(TelaUsuarios.this.isto, "Usu\u00e1rio foi bloqueado!");
                        TelaUsuarios.this.pesquisar();
                    } else {
                        JOptionPane.showMessageDialog(TelaUsuarios.this.isto, "Erro ao bloquear, consulte o administrador!");
                    }
                }
            }
        });
        jMenuItemDesloquear.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceDaLinha = TelaUsuarios.this.tabela.getSelectedRow();
                String s_status = TelaUsuarios.this.modelo.getValueAt(indiceDaLinha, 1).toString();
                if (TelaUsuarios.this.login.getConfigs_privilegios().getNivel_privilegios() > 2) {
                    JOptionPane.showMessageDialog(null, "Requer Eleva\u00e7\u00e3o de Direitos \n Reportado ao Administrador");
                    TelaUsuarios.this.GerenciadorLog.registrarLogDiario("aviso", "tentativa de cria\u00e7\u00e3o de novo usu\u00e1rio");
                } else if (s_status.equals("Ativo")) {
                    JOptionPane.showMessageDialog(TelaUsuarios.this.isto, "Usu\u00e1rio j\u00e1 esta Ativo!");
                } else {
                    GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
                    int id = Integer.parseInt(TelaUsuarios.this.modelo.getValueAt(indiceDaLinha, 0).toString());
                    if (gerenciar.desbloquearUsuario(id)) {
                        JOptionPane.showMessageDialog(TelaUsuarios.this.isto, "Usu\u00e1rio foi desbloqueado!");
                        TelaUsuarios.this.pesquisar();
                    } else {
                        JOptionPane.showMessageDialog(TelaUsuarios.this.isto, "Erro ao desbloquear, consulte o administrador!");
                    }
                }
            }
        });
        this.jPopupMenuStatus.add(jMenuItemBloquear);
        this.jPopupMenuStatus.add(jMenuItemDesloquear);
        this.tabela.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    TelaUsuarios.this.jPopupMenuStatus.show(TelaUsuarios.this.tabela, e.getX(), e.getY());
                }
            }
        });
    }
}

