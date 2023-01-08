/*
 * Decompiled with CFR 0.151.
 */
package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.outros.JTextFieldPersonalizado;

public class TelaCadastroUsuario2
extends JDialog {
    private final JPanel contentPanel = new JPanel();

    public TelaCadastroUsuario2() {
        this.getContentPane().setBackground(Color.WHITE);
        TelaCadastroUsuario2 isto = this;
        this.setTitle("E-Contract - Cadastro Usu\u00e1rio");
        this.setBounds(100, 100, 450, 300);
        this.setBounds(100, 100, 810, 589);
        this.getContentPane().setLayout(null);
        JLabel lblCadastro_2 = new JLabel("----- Cadastro / Dados de Usu\u00e1rio");
        lblCadastro_2.setHorizontalAlignment(11);
        lblCadastro_2.setForeground(Color.BLACK);
        lblCadastro_2.setFont(new Font("Arial", 0, 14));
        lblCadastro_2.setBackground(Color.ORANGE);
        lblCadastro_2.setBounds(0, 0, 219, 33);
        this.getContentPane().add(lblCadastro_2);
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(11);
        lblNome.setForeground(Color.BLACK);
        lblNome.setFont(new Font("Arial", 0, 16));
        lblNome.setBackground(Color.ORANGE);
        lblNome.setBounds(44, 177, 72, 21);
        this.getContentPane().add(lblNome);
        JTextFieldPersonalizado entNome = new JTextFieldPersonalizado();
        entNome.setForeground(Color.BLACK);
        entNome.setFont(new Font("Arial", 1, 20));
        entNome.setColumns(10);
        entNome.setBounds(121, 172, 242, 28);
        this.getContentPane().add(entNome);
        JLabel lblSobrenome = new JLabel("Sobrenome:");
        lblSobrenome.setHorizontalAlignment(11);
        lblSobrenome.setForeground(Color.BLACK);
        lblSobrenome.setFont(new Font("Arial", 0, 16));
        lblSobrenome.setBackground(Color.ORANGE);
        lblSobrenome.setBounds(30, 214, 86, 21);
        this.getContentPane().add(lblSobrenome);
        JTextFieldPersonalizado entSobrenome = new JTextFieldPersonalizado();
        entSobrenome.setForeground(Color.BLACK);
        entSobrenome.setFont(new Font("Arial", 1, 20));
        entSobrenome.setColumns(10);
        entSobrenome.setBounds(121, 209, 242, 28);
        this.getContentPane().add(entSobrenome);
        JLabel lblCadastro_2_1 = new JLabel("Dados Pessoais");
        lblCadastro_2_1.setHorizontalAlignment(11);
        lblCadastro_2_1.setForeground(Color.BLACK);
        lblCadastro_2_1.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1.setBackground(Color.ORANGE);
        lblCadastro_2_1.setBounds(143, 104, 120, 33);
        this.getContentPane().add(lblCadastro_2_1);
        JLabel lblCadastro_2_1_1 = new JLabel("Dados Empresariais");
        lblCadastro_2_1_1.setHorizontalAlignment(11);
        lblCadastro_2_1_1.setForeground(Color.BLACK);
        lblCadastro_2_1_1.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1_1.setBackground(Color.ORANGE);
        lblCadastro_2_1_1.setBounds(144, 361, 152, 33);
        this.getContentPane().add(lblCadastro_2_1_1);
        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setHorizontalAlignment(11);
        lblCargo.setForeground(Color.BLACK);
        lblCargo.setFont(new Font("Arial", 0, 16));
        lblCargo.setBackground(Color.ORANGE);
        lblCargo.setBounds(30, 410, 86, 21);
        this.getContentPane().add(lblCargo);
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(121, 405, 242, 33);
        this.getContentPane().add(comboBox);
        JLabel lblCadastro_2_1_2 = new JLabel("Dados Login");
        lblCadastro_2_1_2.setHorizontalAlignment(11);
        lblCadastro_2_1_2.setForeground(Color.BLACK);
        lblCadastro_2_1_2.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1_2.setBackground(Color.ORANGE);
        lblCadastro_2_1_2.setBounds(540, 104, 120, 33);
        this.getContentPane().add(lblCadastro_2_1_2);
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setHorizontalAlignment(11);
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setFont(new Font("Arial", 0, 16));
        lblLogin.setBackground(Color.ORANGE);
        lblLogin.setBounds(430, 175, 72, 21);
        this.getContentPane().add(lblLogin);
        JTextFieldPersonalizado entLogin = new JTextFieldPersonalizado();
        entLogin.setForeground(Color.BLACK);
        entLogin.setFont(new Font("Arial", 1, 20));
        entLogin.setColumns(10);
        entLogin.setBounds(507, 170, 242, 28);
        this.getContentPane().add(entLogin);
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(11);
        lblSenha.setForeground(Color.BLACK);
        lblSenha.setFont(new Font("Arial", 0, 16));
        lblSenha.setBackground(Color.ORANGE);
        lblSenha.setBounds(430, 219, 72, 21);
        this.getContentPane().add(lblSenha);
        JTextFieldPersonalizado entSenha = new JTextFieldPersonalizado();
        entSenha.setForeground(Color.BLACK);
        entSenha.setFont(new Font("Arial", 1, 20));
        entSenha.setColumns(10);
        entSenha.setBounds(507, 214, 242, 28);
        this.getContentPane().add(entSenha);
        JLabel lblSenha2 = new JLabel("Confirmar Senha:");
        lblSenha2.setHorizontalAlignment(11);
        lblSenha2.setForeground(Color.BLACK);
        lblSenha2.setFont(new Font("Arial", 0, 16));
        lblSenha2.setBackground(Color.ORANGE);
        lblSenha2.setBounds(368, 256, 134, 21);
        this.getContentPane().add(lblSenha2);
        JTextFieldPersonalizado entSenha1 = new JTextFieldPersonalizado();
        entSenha1.setForeground(Color.BLACK);
        entSenha1.setFont(new Font("Arial", 1, 20));
        entSenha1.setColumns(10);
        entSenha1.setBounds(507, 251, 242, 28);
        this.getContentPane().add(entSenha1);
        JLabel lblCadastro_2_1_1_1 = new JLabel("Dados Contato");
        lblCadastro_2_1_1_1.setHorizontalAlignment(11);
        lblCadastro_2_1_1_1.setForeground(Color.BLACK);
        lblCadastro_2_1_1_1.setFont(new Font("Arial", 0, 14));
        lblCadastro_2_1_1_1.setBackground(Color.ORANGE);
        lblCadastro_2_1_1_1.setBounds(540, 361, 152, 33);
        this.getContentPane().add(lblCadastro_2_1_1_1);
        JLabel lblCelular = new JLabel("Login:");
        lblCelular.setHorizontalAlignment(11);
        lblCelular.setForeground(Color.BLACK);
        lblCelular.setFont(new Font("Arial", 0, 16));
        lblCelular.setBackground(Color.ORANGE);
        lblCelular.setBounds(430, 415, 72, 21);
        this.getContentPane().add(lblCelular);
        JTextFieldPersonalizado entCelular = new JTextFieldPersonalizado();
        entCelular.setForeground(Color.BLACK);
        entCelular.setFont(new Font("Arial", 1, 20));
        entCelular.setColumns(10);
        entCelular.setBounds(507, 410, 242, 28);
        this.getContentPane().add(entCelular);
        this.setDefaultCloseOperation(2);
        this.setModal(true);
        this.setResizable(true);
        this.adicionarFocus(isto.getComponents());
        this.setLocationRelativeTo(null);
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
}

