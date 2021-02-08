package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import cadastros.CadastroLogin;
import conexaoBanco.GerenciarBancoLogin;
import manipular.GetDadosGlobais;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import outros.DadosGlobais;
import outros.JPanelTransparent;
import outros.JTextFieldPersonalizado;
import tratamento_proprio.Log;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCadastroUsuario extends JDialog implements GetDadosGlobais {

	// private JTabbedPane painelPrincipal = new JTabbedPane();
	private final JPanel painelPrincipal = new JPanel();

	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelEmpresa = new JPanel();
	private JPanel painelPermissoes = new JPanel();
	private JTextFieldPersonalizado entEmail, entSenhaGmail, entCelular, entLogin, entSenha, entSenha1, entSobrenome,
			entNome;
	private JComboBox cBCargo, cBDireitos, cBGenero;
	private Log GerenciadorLog;
	private CadastroLogin login_edicao;
	private TelaCadastroUsuario isto;
	private JCheckBox chkBoxAlterarApis;

	public TelaCadastroUsuario(int flag_tipo_tela, CadastroLogin _login_edicao) {

		getDadosGlobais();
		// setAlwaysOnTop(true);

		setModal(true);
		isto = this;

		setResizable(false);

		if (flag_tipo_tela == 1) {
			// modo edicao
			setTitle("E-Contract - Edição de Usuário");
			this.login_edicao = _login_edicao;

		} else {
			setTitle("E-Contract - Cadastro Usuário");

		}
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 952, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(null);
		painelPermissoes.setEnabled(false);
		painelPermissoes.setVisible(false);
		painelEmpresa.setVisible(false);
		painelEmpresa.setEnabled(false);

		painelEmpresa.setBackground(new Color(255, 255, 255));
		painelEmpresa.setLayout(null);
		painelEmpresa.setBounds(200, 55, 746, 424);
		painelPrincipal.add(painelEmpresa);
		painelEmpresa.setBackground(new Color(255, 255, 255));
		// painelPrincipal.addTab("Dados Contato", painelEmpresa);
		painelEmpresa.setLayout(null);

		entSenha1 = new JTextFieldPersonalizado();
		entSenha1.setForeground(Color.BLACK);
		entSenha1.setFont(new Font("Arial", Font.BOLD, 20));
		entSenha1.setColumns(10);
		entSenha1.setBounds(256, 146, 242, 28);
		painelEmpresa.add(entSenha1);

		JLabel lblSenha2 = new JLabel("Confirmar Senha:");
		lblSenha2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSenha2.setForeground(Color.BLACK);
		lblSenha2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenha2.setBackground(Color.ORANGE);
		lblSenha2.setBounds(117, 151, 134, 21);
		painelEmpresa.add(lblSenha2);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSenha.setForeground(Color.BLACK);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenha.setBackground(Color.ORANGE);
		lblSenha.setBounds(179, 114, 72, 21);
		painelEmpresa.add(lblSenha);

		entSenha = new JTextFieldPersonalizado();
		entSenha.setForeground(Color.BLACK);
		entSenha.setFont(new Font("Arial", Font.BOLD, 20));
		entSenha.setColumns(10);
		entSenha.setBounds(256, 109, 242, 28);
		painelEmpresa.add(entSenha);

		entLogin = new JTextFieldPersonalizado();
		entLogin.setForeground(Color.BLACK);
		entLogin.setFont(new Font("Arial", Font.BOLD, 20));
		entLogin.setColumns(10);
		entLogin.setBounds(256, 65, 242, 28);
		painelEmpresa.add(entLogin);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLogin.setBackground(Color.ORANGE);
		lblLogin.setBounds(179, 70, 72, 21);
		painelEmpresa.add(lblLogin);

		JLabel lblCadastro_2_1_2 = new JLabel("Dados Login");
		lblCadastro_2_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1_2.setForeground(Color.BLACK);
		lblCadastro_2_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1_2.setBackground(Color.ORANGE);
		lblCadastro_2_1_2.setBounds(292, 21, 120, 33);
		painelEmpresa.add(lblCadastro_2_1_2);

		JLabel lblCadastro_2_1_2_1 = new JLabel("Dados Contato");
		lblCadastro_2_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1_2_1.setBounds(292, 172, 120, 33);
		painelEmpresa.add(lblCadastro_2_1_2_1);

		JLabel Celu = new JLabel("Celular:");
		Celu.setHorizontalAlignment(SwingConstants.TRAILING);
		Celu.setForeground(Color.BLACK);
		Celu.setFont(new Font("Arial", Font.PLAIN, 16));
		Celu.setBackground(Color.ORANGE);
		Celu.setBounds(179, 221, 72, 21);
		painelEmpresa.add(Celu);

		entCelular = new JTextFieldPersonalizado();
		entCelular.setForeground(Color.BLACK);
		entCelular.setFont(new Font("Arial", Font.BOLD, 20));
		entCelular.setColumns(10);
		entCelular.setBounds(256, 216, 242, 28);
		painelEmpresa.add(entCelular);
		entCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				// 120.927.987-00
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCelular.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {

					if (entCelular.getText().length() >= 11) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entCelular.setText(entCelular.getText().substring(0, 11));
					}

				}

			}
		});

		entEmail = new JTextFieldPersonalizado();
		entEmail.setForeground(Color.BLACK);
		entEmail.setFont(new Font("Arial", Font.BOLD, 20));
		entEmail.setColumns(10);
		entEmail.setBounds(256, 258, 242, 28);
		painelEmpresa.add(entEmail);

		JLabel lblEmailgmail = new JLabel("Email(gmail):");
		lblEmailgmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmailgmail.setForeground(Color.BLACK);
		lblEmailgmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmailgmail.setBackground(Color.ORANGE);
		lblEmailgmail.setBounds(159, 263, 92, 21);
		painelEmpresa.add(lblEmailgmail);

		JLabel lblSenhagmail = new JLabel("Senha(gmail):");
		lblSenhagmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSenhagmail.setForeground(Color.BLACK);
		lblSenhagmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenhagmail.setBackground(Color.ORANGE);
		lblSenhagmail.setBounds(144, 302, 107, 21);
		painelEmpresa.add(lblSenhagmail);

		entSenhaGmail = new JTextFieldPersonalizado();
		entSenhaGmail.setForeground(Color.BLACK);
		entSenhaGmail.setFont(new Font("Arial", Font.BOLD, 20));
		entSenhaGmail.setColumns(10);
		entSenhaGmail.setBounds(256, 297, 242, 28);
		painelEmpresa.add(entSenhaGmail);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				concluir(0);

			}
		});
		btnSalvar.setBounds(604, 373, 89, 23);
		painelEmpresa.add(btnSalvar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluir(1);
			}
		});
		btnAtualizar.setBounds(604, 373, 89, 23);
		painelEmpresa.add(btnAtualizar);

		painelDadosIniciais.setBounds(200, 55, 746, 424);
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelDadosIniciais.setLayout(null);

		painelPrincipal.add(painelDadosIniciais);

		JLabel lblCadastro_2_1 = new JLabel("Dados Pessoais");
		lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1.setBounds(123, 94, 120, 33);
		painelDadosIniciais.add(lblCadastro_2_1);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBackground(Color.ORANGE);
		lblNome.setBounds(24, 167, 72, 21);
		painelDadosIniciais.add(lblNome);

		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSobrenome.setForeground(Color.BLACK);
		lblSobrenome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSobrenome.setBackground(Color.ORANGE);
		lblSobrenome.setBounds(10, 204, 86, 21);
		painelDadosIniciais.add(lblSobrenome);

		entNome = new JTextFieldPersonalizado();
		entNome.setForeground(Color.BLACK);
		entNome.setFont(new Font("Arial", Font.BOLD, 20));
		entNome.setColumns(10);
		entNome.setBounds(101, 162, 242, 28);
		painelDadosIniciais.add(entNome);

		entSobrenome = new JTextFieldPersonalizado();
		entSobrenome.setForeground(Color.BLACK);
		entSobrenome.setFont(new Font("Arial", Font.BOLD, 20));
		entSobrenome.setColumns(10);
		entSobrenome.setBounds(101, 199, 242, 28);
		painelDadosIniciais.add(entSobrenome);

		JLabel lblCadastro_2_1_1 = new JLabel("Dados Empresariais");
		lblCadastro_2_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1_1.setForeground(Color.BLACK);
		lblCadastro_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1_1.setBackground(Color.ORANGE);
		lblCadastro_2_1_1.setBounds(464, 101, 152, 33);
		painelDadosIniciais.add(lblCadastro_2_1_1);

		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargo.setForeground(Color.BLACK);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCargo.setBackground(Color.ORANGE);
		lblCargo.setBounds(362, 150, 86, 21);
		painelDadosIniciais.add(lblCargo);

		cBCargo = new JComboBox();
		cBCargo.setBounds(453, 145, 242, 33);
		painelDadosIniciais.add(cBCargo);
		cBCargo.addItem("Auxiliar Administrativo(a)");
		cBCargo.addItem("Secretário(a)");
		cBCargo.addItem("Gerente Financeiro(a)");

		cBDireitos = new JComboBox();
		cBDireitos.setBounds(453, 194, 242, 33);
		painelDadosIniciais.add(cBDireitos);
		cBDireitos.addItem("Administrativos do Sistema");
		cBDireitos.addItem("Financeiros");
		cBDireitos.addItem("Administrativos");

		JLabel lblDireitos = new JLabel("Direitos:");
		lblDireitos.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDireitos.setForeground(Color.BLACK);
		lblDireitos.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDireitos.setBackground(Color.ORANGE);
		lblDireitos.setBounds(362, 199, 86, 21);
		painelDadosIniciais.add(lblDireitos);
		cBGenero = new JComboBox();
		cBGenero.setBounds(101, 246, 242, 33);
		painelDadosIniciais.add(cBGenero);
		cBGenero.addItem("Masculino");
		cBGenero.addItem("Feminino");

		JLabel lblGnero = new JLabel("Gênero:");
		lblGnero.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGnero.setForeground(Color.BLACK);
		lblGnero.setFont(new Font("Arial", Font.PLAIN, 16));
		lblGnero.setBackground(Color.ORANGE);
		lblGnero.setBounds(10, 251, 86, 21);
		painelDadosIniciais.add(lblGnero);

		painelPermissoes.setLayout(null);
		painelPermissoes.setBackground(new Color(255, 255, 255));
		painelPermissoes.setBounds(200, 55, 746, 424);
		painelPrincipal.add(painelPermissoes);
		JLabel lblAlterarApis = new JLabel("Alterar Api's:");
		lblAlterarApis.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAlterarApis.setForeground(Color.BLACK);
		lblAlterarApis.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAlterarApis.setBackground(Color.ORANGE);
		lblAlterarApis.setBounds(89, 90, 121, 21);
		painelPermissoes.add(lblAlterarApis);

		chkBoxAlterarApis = new JCheckBox("Permitir\r\n");
		chkBoxAlterarApis.setBounds(216, 91, 97, 23);
		painelPermissoes.add(chkBoxAlterarApis);

		/*
		 * painelPrincipal = new JTabbedPane();
		 * 
		 * setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 * painelPrincipal.setBackground(new Color(255, 255, 255));
		 * painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		 * 
		 * 
		 * painelDadosIniciais.setBackground(new Color(255, 255, 255));
		 * 
		 * //adiciona novos paines e suas abas painelPrincipal.addTab("Dados Iniciais",
		 * painelDadosIniciais); painelDadosIniciais.setLayout(null);
		 * painelPermissoes.setBackground(new Color(255, 255, 255));
		 * //contentPanel.setLayout(null);
		 * painelPrincipal.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		 * 
		 * painelPrincipal.setTabPlacement(JTabbedPane.LEFT);
		 * painelPrincipal.addTab("Permissões", painelPermissoes);
		 * painelPermissoes.setLayout(null);
		 */

		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 51, 153));
		panel.setBounds(0, 0, 201, 479);
		painelPrincipal.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Usuário");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 43, 181, 38);
		panel.add(lblNewLabel);

		JPanelTransparent panel_2 = new JPanelTransparent();
		panel_2.setTransparencia(2);
		panel_2.setBounds(10, 106, 181, 131);
		panel.add(panel_2);
		panel.repaint();
		panel_2.setLayout(null);

		JLabel btnLogin = new JLabel("Login");
		btnLogin.setBounds(10, 84, 146, 20);
		panel_2.add(btnLogin);

		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBackground(new Color(0, 0, 0, 100));

		JLabel btnPermissoes = new JLabel("Permissões");
		btnPermissoes.setBounds(10, 53, 146, 20);
		panel_2.add(btnPermissoes);

		btnPermissoes.setForeground(Color.WHITE);
		btnPermissoes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPermissoes.setBackground(new Color(0, 0, 0, 100));

		JLabel btnDadosIniciais = new JLabel("Dados Inicias");
		btnDadosIniciais.setBounds(10, 22, 161, 20);
		panel_2.add(btnDadosIniciais);
		btnDadosIniciais.setOpaque(true);
		btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

		btnDadosIniciais.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDadosIniciais.setForeground(Color.WHITE);

		btnDadosIniciais.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				painelPermissoes.setEnabled(false);
				painelPermissoes.setVisible(false);

				painelEmpresa.setEnabled(false);
				painelEmpresa.setVisible(false);

				painelDadosIniciais.setEnabled(true);
				painelDadosIniciais.setVisible(true);

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

		btnPermissoes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelPermissoes.setEnabled(true);
				painelPermissoes.setVisible(true);

				painelEmpresa.setEnabled(false);
				painelEmpresa.setVisible(false);

				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

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

		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelPermissoes.setEnabled(false);
				painelPermissoes.setVisible(false);

				painelEmpresa.setEnabled(true);
				painelEmpresa.setVisible(true);

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

				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(102, 0, 204));
		panel_1.setBounds(201, 0, 745, 55);
		painelPrincipal.add(panel_1);

		if (flag_tipo_tela == 1) {
			// modo edicao esconder botao de salvar
			btnSalvar.setVisible(false);
			btnSalvar.setEnabled(false);
			getDadosCompletosLogin();
			setConfiguracoesEdicao();
		} else {
			// modo salvar, econder botao atualizar
			btnAtualizar.setVisible(false);
			btnAtualizar.setEnabled(false);

		}

		adicionarFocus(isto.getComponents());

		this.setLocationRelativeTo(null);

		this.setVisible(true);

	}

	public void adicionarFocus(Component[] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				if (c instanceof outros.JTextFieldPersonalizado) {

					JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
					caixa_texto.addFocusListener(new FocusAdapter() {
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
				Container novo_container = (Container) c;
				// if (0 < novo_container.getComponents())
				{
					adicionarFocus(novo_container.getComponents());
				}
			}
		}
	}

	public void concluir(int flag) {
		CadastroLogin.Privilegios novos_privilegios = new CadastroLogin.Privilegios();
		CadastroLogin.Preferencias novas_preferencias = new CadastroLogin.Preferencias();

		String nome, sobrenome, cargo, direitos, s_login, senha, senha1, celular, email, senhaEmail, genero;
		String alterar_apis;

		nome = entNome.getText();
		sobrenome = entSobrenome.getText();
		cargo = cBCargo.getSelectedItem().toString();
		direitos = cBDireitos.getSelectedItem().toString();
		s_login = entLogin.getText();
		senha = entSenha.getText();
		senha1 = entSenha1.getText();
		celular = entCelular.getText();
		email = entEmail.getText();
		senhaEmail = entSenhaGmail.getText();
		genero = cBGenero.getSelectedItem().toString();

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

								if (email != null && !email.equals(" ") && email.contains("@gmail.com")) {
									aceitar_cadastro = true;

									if (senhaEmail != null && !senhaEmail.equals(" ") && senhaEmail.length() >= 8) {
										aceitar_cadastro = true;

									} else {
										JOptionPane.showMessageDialog(null,
												"Senha gmail informada não atende aos padrões do\n servidor gmail");
										aceitar_cadastro = false;
									}
								} else {
									JOptionPane.showMessageDialog(null,
											"Necessário um e-mail do servidor gmail válido!");
									aceitar_cadastro = false;
								}

							} else {
								JOptionPane.showMessageDialog(null, "Celular Necessita ser 11 caracteres");
								aceitar_cadastro = false;
							}

						} else {
							JOptionPane.showMessageDialog(null, "Senhas não conferem");
							aceitar_cadastro = false;
						}

					} else {
						JOptionPane.showMessageDialog(null, "Senha de login necessita ter 4 ou mais caracteres ");
						aceitar_cadastro = false;

					}

				} else {
					JOptionPane.showMessageDialog(null, "Login Incorreto");
					aceitar_cadastro = false;
				}

			} else {
				JOptionPane.showMessageDialog(null, "Sobrenome Incorreto");
				aceitar_cadastro = false;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Nome Incorreto");
			aceitar_cadastro = false;

		}

		if (aceitar_cadastro) {
			CadastroLogin novo_usuario = new CadastroLogin();
			novo_usuario.setNome(nome);
			novo_usuario.setSobrenome(sobrenome);
			novo_usuario.setCelular(celular);
			novo_usuario.setCargo(cargo);
			novo_usuario.setLogin(s_login);
			novo_usuario.setEmail(email);
			novo_usuario.setSenha(senha);
			novo_usuario.setSenhaEmail(senhaEmail);
			novo_usuario.setGenero(genero);

			if (direitos.equals("Administrativos do Sistema")) {
				novos_privilegios.setNivel_privilegios(1);
			} else if (direitos.equals("Financeiros")) {
				novos_privilegios.setNivel_privilegios(2);
			} else if (direitos.equals("Administrativos")) {
				novos_privilegios.setNivel_privilegios(3);
			}

			// checagens
			if (chkBoxAlterarApis.isSelected()) {
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
				// salvar
				salvar(novo_usuario);

			} else {
				int id_login_edicao = login_edicao.getId();

				novas_preferencias.setId_preferencias(login_edicao.getConfigs_preferencias().getId_preferencias());
				novos_privilegios.setId_privilegios(login_edicao.getConfigs_privilegios().getId_privilegios());

				novo_usuario.setConfigs_preferencias(novas_preferencias);
				novo_usuario.setConfigs_privilegios(novos_privilegios);

				novo_usuario.setId(id_login_edicao);
				atualizar(novo_usuario);
			}

		}

	}

	public void atualizar(CadastroLogin login_atualizar) {
		GerenciarBancoLogin gerenciarLogin = new GerenciarBancoLogin();
		boolean result = gerenciarLogin.atualizarUsuario(login_atualizar);
		if (result) {
			JOptionPane.showMessageDialog(null, "Usuário Atualizado");
			GerenciadorLog.registrarLogDiario("aviso", "novo usuario cadastrado, login: " + login_atualizar.getLogin());
			isto.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Erro ao Cadastrar Usuário\n Contate o Administrador do Sistema");
			GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: " + login_atualizar.getLogin());

		}

	}

	public void salvar(CadastroLogin novo_usuario) {
		GerenciarBancoLogin gerenciarLogin = new GerenciarBancoLogin();
		int result = gerenciarLogin.inserirLogin(novo_usuario);
		if (result == 1) {
			JOptionPane.showMessageDialog(null, "Usuário Cadastrado");
			GerenciadorLog.registrarLogDiario("aviso", "novo usuario cadastrado, login: " + novo_usuario.getLogin());
			isto.dispose();
		} else if (result == 0) {
			JOptionPane.showMessageDialog(null, "Login já esta cadastrado, tente um diferente!");
			GerenciadorLog.registrarLogDiario("falha",
					"erro ao cadastrar novo usuario: login já existe " + novo_usuario.getLogin());

		} else {
			JOptionPane.showMessageDialog(null, "Erro ao Cadastrar Usuário\n Contate o Administrador do Sistema");
			GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: " + novo_usuario.getLogin());

		}

	}

	public void getDadosCompletosLogin() {
		GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
		login_edicao = gerenciar.buscaLogin(login_edicao.getLogin());

	}

	public void setConfiguracoesEdicao() {

		entNome.setText(login_edicao.getNome());
		entSobrenome.setText(login_edicao.getSobrenome());
		cBCargo.setSelectedItem(login_edicao.getCargo());
		entCelular.setText(login_edicao.getCelular());
		entLogin.setText(login_edicao.getLogin());
		entSenha.setText(login_edicao.getSenha());
		entSenha1.setText(login_edicao.getSenha());
		entEmail.setText(login_edicao.getEmail());
		entSenhaGmail.setText(login_edicao.getSenhaEmail());
		cBGenero.setSelectedItem(login_edicao.getGenero());

		int direito = login_edicao.getConfigs_privilegios().getNivel_privilegios();

		if (direito == 1) {
			cBDireitos.setSelectedItem("Administrativos do Sistema");
		} else if (direito == 2) {
			cBDireitos.setSelectedItem("Financeiros");
		} else if (direito == 3) {
			cBDireitos.setSelectedItem("Administrativos");
		}

		int alterar_api = login_edicao.getConfigs_privilegios().getPrivilegio_alterar_apis();
		if (alterar_api == 1) {
			chkBoxAlterarApis.setSelected(true);
		} else if (direito == 2) {
			chkBoxAlterarApis.setSelected(false);
		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();

		// usuario logado
       //	  login = dados.getLogin();

	}
}
