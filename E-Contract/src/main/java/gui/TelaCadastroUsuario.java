/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 */
package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.java.cadastros.CadastroLogin;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.gui.TelaUsuarios;
import main.java.manipular.Email2;
import main.java.manipular.GetDadosGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.tratamento_proprio.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TelaCadastroUsuario
extends JFrame
implements GetDadosGlobais {
    private final JPanel painelPrincipal = new JPanel();
    private JPanel painelDadosIniciais = new JPanel();
    private JPanel painelEmpresa = new JPanel();
    private JPanel painelPermissoes = new JPanel();
    private JTextFieldPersonalizado entEmail1;
    private JTextFieldPersonalizado entSenhaEmail1;
    private JTextFieldPersonalizado entCelular;
    private JTextFieldPersonalizado entLogin;
    private JTextFieldPersonalizado entSenha;
    private JTextFieldPersonalizado entSenha1;
    private JTextFieldPersonalizado entSobrenome;
    private JTextFieldPersonalizado entNome;
    private JTextFieldPersonalizado entSenhaEmail2;
    private JTextFieldPersonalizado entEmail2;
    private JComboBox cBCargo;
    private JComboBox cBDireitos;
    private JComboBox cBGenero;
    private Log GerenciadorLog;
    private CadastroLogin login_edicao;
    private TelaCadastroUsuario isto;
    private JCheckBox chkBoxAlterarApis;

    public TelaCadastroUsuario(int flag_tipo_tela, CadastroLogin _login_edicao, final Window janela_pai) {
        this.getDadosGlobais();
        this.isto = this;
        this.setResizable(false);
        if (flag_tipo_tela == 1) {
            this.setTitle("E-Contract - Edi\u00e7\u00e3o de Usu\u00e1rio");
            this.login_edicao = _login_edicao;
        } else {
            this.setTitle("E-Contract - Cadastro Usu\u00e1rio");
        }
        this.setBackground(new Color(255, 255, 255));
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 952, 562);
        this.painelPrincipal.setBackground(new Color(255, 255, 255));
        this.painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.painelPrincipal.setLayout(null);
        this.painelPermissoes.setEnabled(false);
        this.painelPermissoes.setVisible(false);
        this.painelEmpresa.setVisible(false);
        this.painelEmpresa.setEnabled(false);
        this.painelEmpresa.setBackground(new Color(255, 255, 255));
        this.painelEmpresa.setLayout(null);
        this.painelEmpresa.setBounds(200, 55, 746, 478);
        this.painelPrincipal.add(this.painelEmpresa);
        this.painelEmpresa.setBackground(new Color(255, 255, 255));
        this.painelEmpresa.setLayout(null);
        this.entSenha1 = new JTextFieldPersonalizado();
        this.entSenha1.setForeground(Color.BLACK);
        this.entSenha1.setFont(new Font("Arial", 1, 20));
        this.entSenha1.setColumns(10);
        this.entSenha1.setBounds(256, 146, 247, 36);
        this.painelEmpresa.add(this.entSenha1);
        JLabel lblSenha2 = new JLabel("Confirmar Senha:");
        lblSenha2.setHorizontalAlignment(11);
        lblSenha2.setForeground(Color.BLACK);
        lblSenha2.setFont(new Font("Arial", 0, 16));
        lblSenha2.setBackground(Color.ORANGE);
        lblSenha2.setBounds(117, 151, 134, 21);
        this.painelEmpresa.add(lblSenha2);
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(11);
        lblSenha.setForeground(Color.BLACK);
        lblSenha.setFont(new Font("Arial", 0, 16));
        lblSenha.setBackground(Color.ORANGE);
        lblSenha.setBounds(179, 114, 72, 21);
        this.painelEmpresa.add(lblSenha);
        this.entSenha = new JTextFieldPersonalizado();
        this.entSenha.setForeground(Color.BLACK);
        this.entSenha.setFont(new Font("Arial", 1, 20));
        this.entSenha.setColumns(10);
        this.entSenha.setBounds(256, 109, 247, 36);
        this.painelEmpresa.add(this.entSenha);
        this.entLogin = new JTextFieldPersonalizado();
        this.entLogin.setForeground(Color.BLACK);
        this.entLogin.setFont(new Font("Arial", 1, 20));
        this.entLogin.setColumns(10);
        this.entLogin.setBounds(256, 65, 247, 36);
        this.painelEmpresa.add(this.entLogin);
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setHorizontalAlignment(11);
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setFont(new Font("Arial", 0, 16));
        lblLogin.setBackground(Color.ORANGE);
        lblLogin.setBounds(179, 70, 72, 21);
        this.painelEmpresa.add(lblLogin);
        JLabel lblCadastro_2_1_2 = new JLabel("Dados Login");
        lblCadastro_2_1_2.setHorizontalAlignment(11);
        lblCadastro_2_1_2.setForeground(Color.BLACK);
        lblCadastro_2_1_2.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1_2.setBackground(Color.ORANGE);
        lblCadastro_2_1_2.setBounds(292, 21, 120, 33);
        this.painelEmpresa.add(lblCadastro_2_1_2);
        JLabel lblCadastro_2_1_2_1 = new JLabel("Dados Contato");
        lblCadastro_2_1_2_1.setHorizontalAlignment(11);
        lblCadastro_2_1_2_1.setForeground(Color.BLACK);
        lblCadastro_2_1_2_1.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1_2_1.setBackground(Color.ORANGE);
        lblCadastro_2_1_2_1.setBounds(292, 198, 120, 33);
        this.painelEmpresa.add(lblCadastro_2_1_2_1);
        JLabel Celu = new JLabel("Celular:");
        Celu.setHorizontalAlignment(11);
        Celu.setForeground(Color.BLACK);
        Celu.setFont(new Font("Arial", 0, 16));
        Celu.setBackground(Color.ORANGE);
        Celu.setBounds(179, 247, 72, 21);
        this.painelEmpresa.add(Celu);
        this.entCelular = new JTextFieldPersonalizado();
        this.entCelular.setForeground(Color.BLACK);
        this.entCelular.setFont(new Font("Arial", 1, 20));
        this.entCelular.setColumns(10);
        this.entCelular.setBounds(256, 242, 247, 36);
        this.painelEmpresa.add(this.entCelular);
        this.entCelular.addKeyListener(new KeyAdapter(){

            @Override
            public void keyTyped(KeyEvent evt) {
                String caracteres = "0987654321\b";
                String texto = TelaCadastroUsuario.this.entCelular.getText();
                if (!caracteres.contains(String.valueOf(evt.getKeyChar()))) {
                    evt.consume();
                } else if (TelaCadastroUsuario.this.entCelular.getText().length() >= 11) {
                    evt.consume();
                    TelaCadastroUsuario.this.entCelular.setText(TelaCadastroUsuario.this.entCelular.getText().substring(0, 11));
                }
            }
        });
        this.entEmail1 = new JTextFieldPersonalizado();
        this.entEmail1.setForeground(Color.BLACK);
        this.entEmail1.setFont(new Font("Arial", 1, 20));
        this.entEmail1.setColumns(10);
        this.entEmail1.setBounds(85, 298, 247, 36);
        this.painelEmpresa.add(this.entEmail1);
        JLabel lblEmailgmail = new JLabel("Email 1:");
        lblEmailgmail.setHorizontalAlignment(11);
        lblEmailgmail.setForeground(Color.BLACK);
        lblEmailgmail.setFont(new Font("Arial", 0, 16));
        lblEmailgmail.setBackground(Color.ORANGE);
        lblEmailgmail.setBounds(16, 308, 57, 19);
        this.painelEmpresa.add(lblEmailgmail);
        JLabel lblSenhagmail = new JLabel("Senha 1:");
        lblSenhagmail.setHorizontalAlignment(11);
        lblSenhagmail.setForeground(Color.BLACK);
        lblSenhagmail.setFont(new Font("Arial", 0, 16));
        lblSenhagmail.setBackground(Color.ORANGE);
        lblSenhagmail.setBounds(11, 347, 62, 19);
        this.painelEmpresa.add(lblSenhagmail);
        this.entSenhaEmail1 = new JTextFieldPersonalizado();
        this.entSenhaEmail1.setForeground(Color.BLACK);
        this.entSenhaEmail1.setFont(new Font("Arial", 1, 20));
        this.entSenhaEmail1.setColumns(10);
        this.entSenhaEmail1.setBounds(85, 337, 247, 36);
        this.painelEmpresa.add(this.entSenhaEmail1);
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                TelaCadastroUsuario.this.concluir(0, janela_pai);
            }
        });
        btnSalvar.setBounds(604, 434, 89, 23);
        this.painelEmpresa.add(btnSalvar);
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCadastroUsuario.this.concluir(1, janela_pai);
            }
        });
        btnAtualizar.setBounds(604, 434, 89, 23);
        this.painelEmpresa.add(btnAtualizar);
        JLabel lblEmailgmail_1 = new JLabel("Email 2:");
        lblEmailgmail_1.setHorizontalAlignment(11);
        lblEmailgmail_1.setForeground(Color.BLACK);
        lblEmailgmail_1.setFont(new Font("Arial", 0, 16));
        lblEmailgmail_1.setBackground(Color.ORANGE);
        lblEmailgmail_1.setBounds(355, 308, 57, 19);
        this.painelEmpresa.add(lblEmailgmail_1);
        JLabel lblSenhagmail_1 = new JLabel("Senha 2:");
        lblSenhagmail_1.setHorizontalAlignment(11);
        lblSenhagmail_1.setForeground(Color.BLACK);
        lblSenhagmail_1.setFont(new Font("Arial", 0, 16));
        lblSenhagmail_1.setBackground(Color.ORANGE);
        lblSenhagmail_1.setBounds(350, 347, 62, 19);
        this.painelEmpresa.add(lblSenhagmail_1);
        this.entSenhaEmail2 = new JTextFieldPersonalizado();
        this.entSenhaEmail2.setForeground(Color.BLACK);
        this.entSenhaEmail2.setFont(new Font("Arial", 1, 20));
        this.entSenhaEmail2.setBounds(426, 337, 247, 36);
        this.painelEmpresa.add(this.entSenhaEmail2);
        this.entEmail2 = new JTextFieldPersonalizado();
        this.entEmail2.setBounds(426, 298, 247, 36);
        this.entEmail2.setForeground(Color.BLACK);
        this.entEmail2.setFont(new Font("Arial", 1, 20));
        this.painelEmpresa.add(this.entEmail2);
        JButton btnNewButton = new JButton("Testar");
        btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String email1 = TelaCadastroUsuario.this.entEmail1.getText();
                String senha1 = TelaCadastroUsuario.this.entSenhaEmail1.getText();
                Email2 email = new Email2();
                email.logar(email1, senha1);
                boolean teste = email.abrirEmail();
                if (teste) {
                    JOptionPane.showMessageDialog(TelaCadastroUsuario.this.isto, "Logado com sucesso");
                } else {
                    JOptionPane.showMessageDialog(TelaCadastroUsuario.this.isto, "Erro ao logar no e-mail informado!\nVerifique email e senha e tente novamente\nSe o erro persistir, consulte o administrador do sistema");
                }
            }
        });
        btnNewButton.setBounds(156, 384, 89, 23);
        this.painelEmpresa.add(btnNewButton);
        JButton btnNewButton_1 = new JButton("Testar");
        btnNewButton_1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String email1 = TelaCadastroUsuario.this.entEmail2.getText();
                String senha1 = TelaCadastroUsuario.this.entSenhaEmail2.getText();
                Email2 email = new Email2();
                email.logar(email1, senha1);
                boolean teste = email.abrirEmail();
                if (teste) {
                    JOptionPane.showMessageDialog(TelaCadastroUsuario.this.isto, "Logado com sucesso");
                } else {
                    JOptionPane.showMessageDialog(TelaCadastroUsuario.this.isto, "Erro ao logar no e-mail informado!\nVerifique email e senha e tente novamente\nSe o erro persistir, consulte o administrador do sistema");
                }
            }
        });
        btnNewButton_1.setBounds(506, 384, 63, 23);
        this.painelEmpresa.add(btnNewButton_1);
        this.painelDadosIniciais.setBounds(200, 55, 746, 424);
        this.painelDadosIniciais.setBackground(new Color(255, 255, 255));
        this.painelDadosIniciais.setLayout(null);
        this.painelPrincipal.add(this.painelDadosIniciais);
        JLabel lblCadastro_2_1 = new JLabel("Dados Pessoais");
        lblCadastro_2_1.setHorizontalAlignment(11);
        lblCadastro_2_1.setForeground(Color.BLACK);
        lblCadastro_2_1.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1.setBackground(Color.ORANGE);
        lblCadastro_2_1.setBounds(123, 94, 120, 33);
        this.painelDadosIniciais.add(lblCadastro_2_1);
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(11);
        lblNome.setForeground(Color.BLACK);
        lblNome.setFont(new Font("Arial", 0, 16));
        lblNome.setBackground(Color.ORANGE);
        lblNome.setBounds(24, 167, 72, 21);
        this.painelDadosIniciais.add(lblNome);
        JLabel lblSobrenome = new JLabel("Sobrenome:");
        lblSobrenome.setHorizontalAlignment(11);
        lblSobrenome.setForeground(Color.BLACK);
        lblSobrenome.setFont(new Font("Arial", 0, 16));
        lblSobrenome.setBackground(Color.ORANGE);
        lblSobrenome.setBounds(10, 204, 86, 21);
        this.painelDadosIniciais.add(lblSobrenome);
        this.entNome = new JTextFieldPersonalizado();
        this.entNome.setForeground(Color.BLACK);
        this.entNome.setFont(new Font("Arial", 1, 20));
        this.entNome.setColumns(10);
        this.entNome.setBounds(101, 162, 242, 28);
        this.painelDadosIniciais.add(this.entNome);
        this.entSobrenome = new JTextFieldPersonalizado();
        this.entSobrenome.setForeground(Color.BLACK);
        this.entSobrenome.setFont(new Font("Arial", 1, 20));
        this.entSobrenome.setColumns(10);
        this.entSobrenome.setBounds(101, 199, 242, 28);
        this.painelDadosIniciais.add(this.entSobrenome);
        JLabel lblCadastro_2_1_1 = new JLabel("Dados Empresariais");
        lblCadastro_2_1_1.setHorizontalAlignment(11);
        lblCadastro_2_1_1.setForeground(Color.BLACK);
        lblCadastro_2_1_1.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1_1.setBackground(Color.ORANGE);
        lblCadastro_2_1_1.setBounds(464, 101, 152, 33);
        this.painelDadosIniciais.add(lblCadastro_2_1_1);
        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setHorizontalAlignment(11);
        lblCargo.setForeground(Color.BLACK);
        lblCargo.setFont(new Font("Arial", 0, 16));
        lblCargo.setBackground(Color.ORANGE);
        lblCargo.setBounds(362, 150, 86, 21);
        this.painelDadosIniciais.add(lblCargo);
        this.cBCargo = new JComboBox();
        this.cBCargo.setBounds(453, 145, 242, 33);
        this.painelDadosIniciais.add(this.cBCargo);
        this.cBCargo.addItem("Auxiliar Administrativo(a)");
        this.cBCargo.addItem("Secret\u00e1rio(a)");
        this.cBCargo.addItem("Gerente Financeiro(a)");
        this.cBDireitos = new JComboBox();
        this.cBDireitos.setBounds(453, 194, 242, 33);
        this.painelDadosIniciais.add(this.cBDireitos);
        this.cBDireitos.addItem("Administrativos do Sistema");
        this.cBDireitos.addItem("Financeiros");
        this.cBDireitos.addItem("Administrativos");
        JLabel lblDireitos = new JLabel("Direitos:");
        lblDireitos.setHorizontalAlignment(11);
        lblDireitos.setForeground(Color.BLACK);
        lblDireitos.setFont(new Font("Arial", 0, 16));
        lblDireitos.setBackground(Color.ORANGE);
        lblDireitos.setBounds(362, 199, 86, 21);
        this.painelDadosIniciais.add(lblDireitos);
        this.cBGenero = new JComboBox();
        this.cBGenero.setBounds(101, 246, 242, 33);
        this.painelDadosIniciais.add(this.cBGenero);
        this.cBGenero.addItem("Masculino");
        this.cBGenero.addItem("Feminino");
        JLabel lblGnero = new JLabel("G\u00eanero:");
        lblGnero.setHorizontalAlignment(11);
        lblGnero.setForeground(Color.BLACK);
        lblGnero.setFont(new Font("Arial", 0, 16));
        lblGnero.setBackground(Color.ORANGE);
        lblGnero.setBounds(10, 251, 86, 21);
        this.painelDadosIniciais.add(lblGnero);
        this.painelPermissoes.setLayout(null);
        this.painelPermissoes.setBackground(new Color(255, 255, 255));
        this.painelPermissoes.setBounds(200, 55, 746, 424);
        this.painelPrincipal.add(this.painelPermissoes);
        JLabel lblAlterarApis = new JLabel("Alterar Api's:");
        lblAlterarApis.setHorizontalAlignment(11);
        lblAlterarApis.setForeground(Color.BLACK);
        lblAlterarApis.setFont(new Font("Arial", 0, 16));
        lblAlterarApis.setBackground(Color.ORANGE);
        lblAlterarApis.setBounds(89, 90, 121, 21);
        this.painelPermissoes.add(lblAlterarApis);
        this.chkBoxAlterarApis = new JCheckBox("Permitir\r\n");
        this.chkBoxAlterarApis.setBounds(216, 91, 97, 23);
        this.painelPermissoes.add(this.chkBoxAlterarApis);
        this.getContentPane().add((Component)this.painelPrincipal, "Center");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 51, 153));
        panel.setBounds(0, 0, 201, 533);
        this.painelPrincipal.add(panel);
        panel.setLayout(null);
        JLabel lblNewLabel = new JLabel("Cadastro de Usu\u00e1rio");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", 1, 18));
        lblNewLabel.setBounds(10, 43, 181, 38);
        panel.add(lblNewLabel);
        JPanelTransparent panel_2 = new JPanelTransparent();
        panel_2.setTransparencia(2);
        panel_2.setBounds(10, 106, 181, 131);
        panel.add(panel_2);
        panel.repaint();
        panel_2.setLayout(null);
        final JLabel btnLogin = new JLabel("Login");
        btnLogin.setBounds(10, 84, 146, 20);
        panel_2.add(btnLogin);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", 0, 16));
        btnLogin.setBackground(new Color(0, 0, 0, 100));
        final JLabel btnPermissoes = new JLabel("Permiss\u00f5es");
        btnPermissoes.setBounds(10, 53, 146, 20);
        panel_2.add(btnPermissoes);
        btnPermissoes.setForeground(Color.WHITE);
        btnPermissoes.setFont(new Font("Tahoma", 0, 16));
        btnPermissoes.setBackground(new Color(0, 0, 0, 100));
        final JLabel btnDadosIniciais = new JLabel("Dados Inicias");
        btnDadosIniciais.setBounds(10, 22, 161, 20);
        panel_2.add(btnDadosIniciais);
        btnDadosIniciais.setOpaque(true);
        btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
        btnDadosIniciais.setFont(new Font("Tahoma", 0, 16));
        btnDadosIniciais.setForeground(Color.WHITE);
        btnDadosIniciais.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                TelaCadastroUsuario.this.painelPermissoes.setEnabled(false);
                TelaCadastroUsuario.this.painelPermissoes.setVisible(false);
                TelaCadastroUsuario.this.painelEmpresa.setEnabled(false);
                TelaCadastroUsuario.this.painelEmpresa.setVisible(false);
                TelaCadastroUsuario.this.painelDadosIniciais.setEnabled(true);
                TelaCadastroUsuario.this.painelDadosIniciais.setVisible(true);
                btnDadosIniciais.setOpaque(true);
                btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
                btnDadosIniciais.repaint();
                btnDadosIniciais.updateUI();
                btnLogin.setOpaque(false);
                btnLogin.setBackground(new Color(0, 0, 0, 100));
                btnLogin.repaint();
                btnLogin.updateUI();
                btnPermissoes.setOpaque(false);
                btnPermissoes.setBackground(new Color(0, 0, 0, 100));
                btnPermissoes.repaint();
                btnPermissoes.updateUI();
            }
        });
        btnPermissoes.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                TelaCadastroUsuario.this.painelPermissoes.setEnabled(true);
                TelaCadastroUsuario.this.painelPermissoes.setVisible(true);
                TelaCadastroUsuario.this.painelEmpresa.setEnabled(false);
                TelaCadastroUsuario.this.painelEmpresa.setVisible(false);
                TelaCadastroUsuario.this.painelDadosIniciais.setEnabled(false);
                TelaCadastroUsuario.this.painelDadosIniciais.setVisible(false);
                btnLogin.setOpaque(false);
                btnLogin.setBackground(new Color(0, 0, 0, 100));
                btnDadosIniciais.setOpaque(false);
                btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
                btnPermissoes.setBackground(new Color(0, 0, 0, 100));
                btnPermissoes.setOpaque(true);
                btnLogin.repaint();
                btnLogin.updateUI();
                btnPermissoes.repaint();
                btnPermissoes.updateUI();
                btnDadosIniciais.repaint();
                btnDadosIniciais.updateUI();
            }
        });
        btnLogin.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                TelaCadastroUsuario.this.painelPermissoes.setEnabled(false);
                TelaCadastroUsuario.this.painelPermissoes.setVisible(false);
                TelaCadastroUsuario.this.painelEmpresa.setEnabled(true);
                TelaCadastroUsuario.this.painelEmpresa.setVisible(true);
                btnLogin.setOpaque(true);
                btnLogin.setBackground(new Color(0, 0, 0, 100));
                btnLogin.repaint();
                btnLogin.updateUI();
                btnPermissoes.setOpaque(false);
                btnPermissoes.setBackground(new Color(0, 0, 0, 100));
                btnPermissoes.repaint();
                btnPermissoes.updateUI();
                btnDadosIniciais.setOpaque(false);
                btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
                btnDadosIniciais.repaint();
                btnDadosIniciais.updateUI();
                TelaCadastroUsuario.this.painelDadosIniciais.setEnabled(false);
                TelaCadastroUsuario.this.painelDadosIniciais.setVisible(false);
            }
        });
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(102, 0, 204));
        panel_1.setBounds(201, 0, 745, 55);
        this.painelPrincipal.add(panel_1);
        if (flag_tipo_tela == 1) {
            btnSalvar.setVisible(false);
            btnSalvar.setEnabled(false);
            this.getDadosCompletosLogin();
            this.setConfiguracoesEdicao();
        } else {
            btnAtualizar.setVisible(false);
            btnAtualizar.setEnabled(false);
        }
        this.adicionarFocus(this.isto.getComponents());
        this.setLocationRelativeTo(janela_pai);
        this.setVisible(true);
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

    public void concluir(int flag, Window janela_pai) {
        CadastroLogin.Privilegios novos_privilegios = new CadastroLogin.Privilegios();
        CadastroLogin.Preferencias novas_preferencias = new CadastroLogin.Preferencias();
        String nome = this.entNome.getText();
        String sobrenome = this.entSobrenome.getText();
        String cargo = this.cBCargo.getSelectedItem().toString();
        String direitos = this.cBDireitos.getSelectedItem().toString();
        String s_login = this.entLogin.getText();
        String senha = this.entSenha.getText();
        String senha1 = this.entSenha1.getText();
        String celular = this.entCelular.getText();
        String email1 = this.entEmail1.getText();
        String senhaEmail1 = this.entSenhaEmail1.getText();
        String email2 = this.entEmail2.getText();
        String senhaEmail2 = this.entSenhaEmail2.getText();
        String genero = this.cBGenero.getSelectedItem().toString();
        boolean aceitar_cadastro = false;
        if (nome != null && !nome.equals(" ") && nome.length() >= 2) {
            aceitar_cadastro = true;
            if (sobrenome != null && !sobrenome.equals(" ") && sobrenome.length() >= 2) {
                aceitar_cadastro = true;
                if (s_login != null && !s_login.equals(" ") && s_login.length() >= 2) {
                    aceitar_cadastro = true;
                    if (senha != null && !senha.equals(" ") && senha.length() >= 4) {
                        if (senha1.equals(senha)) {
                            aceitar_cadastro = true;
                            if (celular != null && !celular.equals(" ") && celular.length() == 11) {
                                aceitar_cadastro = true;
                                if (email1 != null && !email1.equals(" ")) {
                                    aceitar_cadastro = true;
                                    if (senhaEmail1 != null && !senhaEmail1.equals(" ") && senhaEmail1.length() >= 8) {
                                        aceitar_cadastro = true;
                                    } else {
                                        JOptionPane.showMessageDialog(this.isto, "Senha email informada n\u00e3o atende aos padr\u00f5es do\n servidor de email");
                                        aceitar_cadastro = false;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this.isto, "Necess\u00e1rio um e-mail do servidor gmail ou hotmail v\u00e1lido!");
                                    aceitar_cadastro = false;
                                }
                            } else {
                                JOptionPane.showMessageDialog(this.isto, "Celular Necessita ser 11 caracteres");
                                aceitar_cadastro = false;
                            }
                        } else {
                            JOptionPane.showMessageDialog(this.isto, "Senhas n\u00e3o conferem");
                            aceitar_cadastro = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(this.isto, "Senha de login necessita ter 4 ou mais caracteres ");
                        aceitar_cadastro = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(this.isto, "Login Incorreto");
                    aceitar_cadastro = false;
                }
            } else {
                JOptionPane.showMessageDialog(this.isto, "Sobrenome Incorreto");
                aceitar_cadastro = false;
            }
        } else {
            JOptionPane.showMessageDialog(this.isto, "Nome Incorreto");
            aceitar_cadastro = false;
        }
        if (aceitar_cadastro) {
            CadastroLogin novo_usuario = new CadastroLogin();
            novo_usuario.setNome(nome);
            novo_usuario.setSobrenome(sobrenome);
            novo_usuario.setCelular(celular);
            novo_usuario.setCargo(cargo);
            novo_usuario.setLogin(s_login);
            novo_usuario.setEmail(email1);
            novo_usuario.setSenha(senha);
            novo_usuario.setSenhaEmail(senhaEmail1);
            novo_usuario.setGenero(genero);
            novo_usuario.setEmail2(email2);
            novo_usuario.setSenhaEmail2(senhaEmail2);
            novo_usuario.setSenhaWeb(this.codificarSenha(novo_usuario.getSenha()));
            if (direitos.equals("Administrativos do Sistema")) {
                novos_privilegios.setNivel_privilegios(1);
            } else if (direitos.equals("Financeiros")) {
                novos_privilegios.setNivel_privilegios(2);
            } else if (direitos.equals("Administrativos")) {
                novos_privilegios.setNivel_privilegios(3);
            }
            if (this.chkBoxAlterarApis.isSelected()) {
                novos_privilegios.setPrivilegio_alterar_apis(1);
            } else {
                novos_privilegios.setPrivilegio_alterar_apis(0);
            }
            novas_preferencias.setApi_exato(0);
            novas_preferencias.setApi_sintegra(0);
            novas_preferencias.setApi_whatsapp(0);
            if (flag == 0) {
                novo_usuario.setConfigs_preferencias(novas_preferencias);
                novo_usuario.setConfigs_privilegios(novos_privilegios);
                this.salvar(novo_usuario, janela_pai);
            } else {
                int id_login_edicao = this.login_edicao.getId();
                novas_preferencias.setId_preferencias(this.login_edicao.getConfigs_preferencias().getId_preferencias());
                novos_privilegios.setId_privilegios(this.login_edicao.getConfigs_privilegios().getId_privilegios());
                novo_usuario.setConfigs_preferencias(novas_preferencias);
                novo_usuario.setConfigs_privilegios(novos_privilegios);
                novo_usuario.setId(id_login_edicao);
                this.atualizar(novo_usuario, janela_pai);
            }
        }
    }

    public String codificarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode((CharSequence)senha);
    }

    public void atualizar(CadastroLogin login_atualizar, Window janela_pai) {
        GerenciarBancoLogin gerenciarLogin = new GerenciarBancoLogin();
        boolean result = gerenciarLogin.atualizarUsuario(login_atualizar);
        if (result) {
            JOptionPane.showMessageDialog(this.isto, "Usu\u00e1rio Atualizado");
            this.GerenciadorLog.registrarLogDiario("aviso", "novo usuario cadastrado, login: " + login_atualizar.getLogin());
            ((TelaUsuarios)janela_pai).pesquisar();
            this.isto.dispose();
        } else {
            JOptionPane.showMessageDialog(this.isto, "Erro ao Cadastrar Usu\u00e1rio\n Contate o Administrador do Sistema");
            this.GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: " + login_atualizar.getLogin());
        }
    }

    public void salvar(CadastroLogin novo_usuario, Window janela_pai) {
        GerenciarBancoLogin gerenciarLogin = new GerenciarBancoLogin();
        int result = gerenciarLogin.inserirLogin(novo_usuario);
        if (result == 1) {
            JOptionPane.showMessageDialog(this.isto, "Usu\u00e1rio Cadastrado");
            this.GerenciadorLog.registrarLogDiario("aviso", "novo usuario cadastrado, login: " + novo_usuario.getLogin());
            ((TelaUsuarios)janela_pai).pesquisar();
            this.isto.dispose();
        } else if (result == 0) {
            JOptionPane.showMessageDialog(this.isto, "Login j\u00e1 esta cadastrado, tente um diferente!");
            this.GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: login j\u00e1 existe " + novo_usuario.getLogin());
        } else {
            JOptionPane.showMessageDialog(this.isto, "Erro ao Cadastrar Usu\u00e1rio\n Contate o Administrador do Sistema");
            this.GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: " + novo_usuario.getLogin());
        }
    }

    public void getDadosCompletosLogin() {
        GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
        this.login_edicao = gerenciar.buscaLogin(this.login_edicao.getLogin());
    }

    public void setConfiguracoesEdicao() {
        this.entNome.setText(this.login_edicao.getNome());
        this.entSobrenome.setText(this.login_edicao.getSobrenome());
        this.cBCargo.setSelectedItem(this.login_edicao.getCargo());
        this.entCelular.setText(this.login_edicao.getCelular());
        this.entLogin.setText(this.login_edicao.getLogin());
        this.entSenha.setText(this.login_edicao.getSenha());
        this.entSenha1.setText(this.login_edicao.getSenha());
        this.entEmail1.setText(this.login_edicao.getEmail());
        this.entSenhaEmail1.setText(this.login_edicao.getSenhaEmail());
        this.entEmail2.setText(this.login_edicao.getEmail2());
        this.entSenhaEmail2.setText(this.login_edicao.getSenhaEmail2());
        this.cBGenero.setSelectedItem(this.login_edicao.getGenero());
        int direito = this.login_edicao.getConfigs_privilegios().getNivel_privilegios();
        if (direito == 1) {
            this.cBDireitos.setSelectedItem("Administrativos do Sistema");
        } else if (direito == 2) {
            this.cBDireitos.setSelectedItem("Financeiros");
        } else if (direito == 3) {
            this.cBDireitos.setSelectedItem("Administrativos");
        }
        int alterar_api = this.login_edicao.getConfigs_privilegios().getPrivilegio_alterar_apis();
        if (alterar_api == 1) {
            this.chkBoxAlterarApis.setSelected(true);
        } else if (direito == 2) {
            this.chkBoxAlterarApis.setSelected(false);
        }
    }

    @Override
    public void getDadosGlobais() {
        DadosGlobais dados = DadosGlobais.getInstance();
        this.GerenciadorLog = dados.getGerenciadorLog();
    }
}

