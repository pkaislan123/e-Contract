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
import outros.JTextFieldPersonalizado;
import tratamento_proprio.Log;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroUsuario extends JDialog implements GetDadosGlobais {

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelEmpresa = new JPanel();
	private JTextFieldPersonalizado entEmail, entSenhaGmail, entCelular, entLogin, entSenha, entSenha1, entSobrenome, entNome;
	private JComboBox cBCargo, cBDireitos , cBGenero;
	private Log GerenciadorLog;
	private CadastroLogin login;

	public TelaCadastroUsuario() {
		
		getDadosGlobais();
		
		setModal(true);

		TelaCadastroUsuario isto = this;
		
		setResizable(false);
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Cadastro Usuário");
		setBounds(100, 100, 993, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelEmpresa.setBackground(new Color(255, 255, 255));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		JLabel lblCadastro_2_1 = new JLabel("Dados Pessoais");
		lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1.setBounds(178, 93, 120, 33);
		painelDadosIniciais.add(lblCadastro_2_1);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNome.setForeground(Color.BLACK);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBackground(Color.ORANGE);
		lblNome.setBounds(79, 166, 72, 21);
		painelDadosIniciais.add(lblNome);
		
		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSobrenome.setForeground(Color.BLACK);
		lblSobrenome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSobrenome.setBackground(Color.ORANGE);
		lblSobrenome.setBounds(65, 203, 86, 21);
		painelDadosIniciais.add(lblSobrenome);
		
		
		
		
		 entNome = new JTextFieldPersonalizado();
		entNome.setForeground(Color.BLACK);
		entNome.setFont(new Font("Arial", Font.BOLD, 20));
		entNome.setColumns(10);
		entNome.setBounds(156, 161, 242, 28);
		painelDadosIniciais.add(entNome);
		

		 entSobrenome = new JTextFieldPersonalizado();
		entSobrenome.setForeground(Color.BLACK);
		entSobrenome.setFont(new Font("Arial", Font.BOLD, 20));
		entSobrenome.setColumns(10);
		entSobrenome.setBounds(156, 198, 242, 28);
		painelDadosIniciais.add(entSobrenome);
		
		JLabel lblCadastro_2_1_1 = new JLabel("Dados Empresariais");
		lblCadastro_2_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1_1.setForeground(Color.BLACK);
		lblCadastro_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1_1.setBackground(Color.ORANGE);
		lblCadastro_2_1_1.setBounds(603, 110, 152, 33);
		painelDadosIniciais.add(lblCadastro_2_1_1);
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCargo.setForeground(Color.BLACK);
		lblCargo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCargo.setBackground(Color.ORANGE);
		lblCargo.setBounds(501, 159, 86, 21);
		painelDadosIniciais.add(lblCargo);
		
		
		
		 cBCargo = new JComboBox();
		cBCargo.setBounds(592, 154, 242, 33);
		painelDadosIniciais.add(cBCargo);
		cBCargo.addItem("Auxiliar Administrativo(a)");
		cBCargo.addItem("Secretário(a)");
		cBCargo.addItem("Gerente Financeiro(a)");


		 cBDireitos = new JComboBox();
		cBDireitos.setBounds(592, 203, 242, 33);
		painelDadosIniciais.add(cBDireitos);
		cBDireitos.addItem("Administrativos do Sistema");
		cBDireitos.addItem("Financeiros");
		cBDireitos.addItem("Administrativos");


		
		JLabel lblDireitos = new JLabel("Direitos:");
		lblDireitos.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDireitos.setForeground(Color.BLACK);
		lblDireitos.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDireitos.setBackground(Color.ORANGE);
		lblDireitos.setBounds(501, 208, 86, 21);
		painelDadosIniciais.add(lblDireitos);
		 cBGenero = new JComboBox();
		cBGenero.setBounds(156, 245, 242, 33);
		painelDadosIniciais.add(cBGenero);
		cBGenero.addItem("Masculino");
		cBGenero.addItem("Feminino");
		
		JLabel lblGnero = new JLabel("Gênero:");
		lblGnero.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGnero.setForeground(Color.BLACK);
		lblGnero.setFont(new Font("Arial", Font.PLAIN, 16));
		lblGnero.setBackground(Color.ORANGE);
		lblGnero.setBounds(65, 250, 86, 21);
		painelDadosIniciais.add(lblGnero);
		painelPrincipal.addTab("Dados Contato", painelEmpresa);
		painelEmpresa.setLayout(null);


		
		 entSenha1 = new JTextFieldPersonalizado();
		entSenha1.setForeground(Color.BLACK);
		entSenha1.setFont(new Font("Arial", Font.BOLD, 20));
		entSenha1.setColumns(10);
		entSenha1.setBounds(196, 230, 242, 28);
		painelEmpresa.add(entSenha1);
		
		JLabel lblSenha2 = new JLabel("Confirmar Senha:");
		lblSenha2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSenha2.setForeground(Color.BLACK);
		lblSenha2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenha2.setBackground(Color.ORANGE);
		lblSenha2.setBounds(57, 235, 134, 21);
		painelEmpresa.add(lblSenha2);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSenha.setForeground(Color.BLACK);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenha.setBackground(Color.ORANGE);
		lblSenha.setBounds(119, 198, 72, 21);
		painelEmpresa.add(lblSenha);
		

		
		 entSenha = new JTextFieldPersonalizado();
		entSenha.setForeground(Color.BLACK);
		entSenha.setFont(new Font("Arial", Font.BOLD, 20));
		entSenha.setColumns(10);
		entSenha.setBounds(196, 193, 242, 28);
		painelEmpresa.add(entSenha);
		

		
		 entLogin = new JTextFieldPersonalizado();
		entLogin.setForeground(Color.BLACK);
		entLogin.setFont(new Font("Arial", Font.BOLD, 20));
		entLogin.setColumns(10);
		entLogin.setBounds(196, 149, 242, 28);
		painelEmpresa.add(entLogin);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLogin.setForeground(Color.BLACK);
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLogin.setBackground(Color.ORANGE);
		lblLogin.setBounds(119, 154, 72, 21);
		painelEmpresa.add(lblLogin);
		
		JLabel lblCadastro_2_1_2 = new JLabel("Dados Login");
		lblCadastro_2_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1_2.setForeground(Color.BLACK);
		lblCadastro_2_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1_2.setBackground(Color.ORANGE);
		lblCadastro_2_1_2.setBounds(229, 83, 120, 33);
		painelEmpresa.add(lblCadastro_2_1_2);
		
		JLabel lblCadastro_2_1_2_1 = new JLabel("Dados Contato");
		lblCadastro_2_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCadastro_2_1_2_1.setForeground(Color.BLACK);
		lblCadastro_2_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCadastro_2_1_2_1.setBackground(Color.ORANGE);
		lblCadastro_2_1_2_1.setBounds(677, 83, 120, 33);
		painelEmpresa.add(lblCadastro_2_1_2_1);
		
		JLabel Celu = new JLabel("Celular:");
		Celu.setHorizontalAlignment(SwingConstants.TRAILING);
		Celu.setForeground(Color.BLACK);
		Celu.setFont(new Font("Arial", Font.PLAIN, 16));
		Celu.setBackground(Color.ORANGE);
		Celu.setBounds(564, 154, 72, 21);
		painelEmpresa.add(Celu);
		

		
		 entCelular = new JTextFieldPersonalizado();
		entCelular.setForeground(Color.BLACK);
		entCelular.setFont(new Font("Arial", Font.BOLD, 20));
		entCelular.setColumns(10);
		entCelular.setBounds(641, 149, 242, 28);
		painelEmpresa.add(entCelular);
		entCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				//120.927.987-00
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCelular.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
				
				
				if(entCelular.getText().length()>=11){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCelular.setText(entCelular.getText().substring(0,11));
				}
			
			}
				
			}
		});
		

		
		entEmail  = new JTextFieldPersonalizado();
		entEmail.setForeground(Color.BLACK);
		entEmail.setFont(new Font("Arial", Font.BOLD, 20));
		entEmail.setColumns(10);
		entEmail.setBounds(641, 191, 242, 28);
		painelEmpresa.add(entEmail);
		
		JLabel lblEmailgmail = new JLabel("Email(gmail):");
		lblEmailgmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmailgmail.setForeground(Color.BLACK);
		lblEmailgmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmailgmail.setBackground(Color.ORANGE);
		lblEmailgmail.setBounds(544, 196, 92, 21);
		painelEmpresa.add(lblEmailgmail);
		
		JLabel lblSenhagmail = new JLabel("Senha(gmail):");
		lblSenhagmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSenhagmail.setForeground(Color.BLACK);
		lblSenhagmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSenhagmail.setBackground(Color.ORANGE);
		lblSenhagmail.setBounds(529, 235, 107, 21);
		painelEmpresa.add(lblSenhagmail);
		
		
		
		 entSenhaGmail = new JTextFieldPersonalizado();
		entSenhaGmail.setForeground(Color.BLACK);
		entSenhaGmail.setFont(new Font("Arial", Font.BOLD, 20));
		entSenhaGmail.setColumns(10);
		entSenhaGmail.setBounds(641, 230, 242, 28);
		painelEmpresa.add(entSenhaGmail);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome, sobrenome, cargo, direitos, login, senha, senha1, celular, email, senhaEmail, genero;
				
				
				nome  = entNome.getText();
				sobrenome = entSobrenome.getText();
				cargo = cBCargo.getSelectedItem().toString();
				direitos = cBDireitos.getSelectedItem().toString();
				login = entLogin.getText();
				senha = entSenha.getText();
				senha1 = entSenha1.getText();
				celular = entCelular.getText();
				email = entEmail.getText();
				senhaEmail = entSenhaGmail.getText();
				genero = cBGenero.getSelectedItem().toString();
				
				
				boolean aceitar_cadastro = false;
				
				if(nome != null && !nome.equals(" ") && nome.length() >= 2){
					aceitar_cadastro = true;
					
					if(sobrenome != null && !sobrenome.equals(" ") && sobrenome.length() >= 2){
						aceitar_cadastro = true;
						
						if(login != null && !login.equals(" ") && login.length() >= 2){
							aceitar_cadastro = true;
							
							if(senha != null && !senha.equals(" ") && senha.length() >= 4){
								
								if(senha1.equals(senha)){
									aceitar_cadastro = true;

									if(celular != null && !celular.equals(" ") && celular.length() == 11){
										aceitar_cadastro = true;
										
										if(email != null && !email.equals(" ") && email.contains("@gmail.com")){
											aceitar_cadastro = true;
											
											if(senhaEmail != null && !senhaEmail.equals(" ") && senhaEmail.length() >= 8){
												aceitar_cadastro = true;
												
											}
											else {
												JOptionPane.showMessageDialog(null, "Senha gmail informada não atende aos padrões do\n servidor gmail");
												aceitar_cadastro = false;
											}
										}
										else {
											JOptionPane.showMessageDialog(null, "Necessário um e-mail do servidor gmail válido!");
											aceitar_cadastro = false;
										}
										
									}else {
										JOptionPane.showMessageDialog(null, "Celular Necessita ser 11 caracteres");
										aceitar_cadastro = false;
									}
									
								}else {
									JOptionPane.showMessageDialog(null, "Senhas não conferem");
									aceitar_cadastro = false;
								}
							
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Senha de login necessita ter 4 ou mais caracteres ");
								aceitar_cadastro = false;

							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Login Incorreto");
							aceitar_cadastro = false;
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Sobrenome Incorreto");
						aceitar_cadastro = false;
					}
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Nome Incorreto");
					aceitar_cadastro = false;

				}
				
				
				if(aceitar_cadastro ) {
					CadastroLogin novo_usuario = new CadastroLogin();
					novo_usuario.setNome(nome);
					novo_usuario.setSobrenome(sobrenome);
					novo_usuario.setCelular(celular);
					novo_usuario.setCargo(cargo);
					novo_usuario.setLogin(login);
					novo_usuario.setEmail(email);
					novo_usuario.setSenha(senha);
					novo_usuario.setSenhaEmail(senhaEmail);
					novo_usuario.setGenero(genero);

					if(direitos.equals("Administrativos do Sistema"))
						novo_usuario.setDireitos(1);
					else if(direitos.equals("Financeiros"))
						novo_usuario.setDireitos(2);
					else if(direitos.equals("Administrativos"))
						novo_usuario.setDireitos(3);
					
					GerenciarBancoLogin gerenciarLogin = new GerenciarBancoLogin();
					int result = gerenciarLogin.inserir(novo_usuario);
					if(result == 1) {
						JOptionPane.showMessageDialog(null, "Usuário Cadastrado");
						GerenciadorLog.registrarLogDiario("aviso", "novo usuario cadastrado, login: " + novo_usuario.getLogin() );
						isto.dispose();
					}else if(result == -1){
						JOptionPane.showMessageDialog(null, "Login já esta cadastrado, tente um diferente!");
						GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: login já existe " + novo_usuario.getLogin() );

					}
					else {
						JOptionPane.showMessageDialog(null, "Erro ao Cadastrar Usuário\n Contate o Administrador do Sistema");
						GerenciadorLog.registrarLogDiario("falha", "erro ao cadastrar novo usuario: " + novo_usuario.getLogin() );

					}
				

				}
				
				
				
			}
		});
		btnSalvar.setBounds(794, 400, 89, 23);
		painelEmpresa.add(btnSalvar);
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		
		 adicionarFocus(isto.getComponents());

		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
	
	
	
	public  void adicionarFocus(Component [] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				 if(c instanceof outros.JTextFieldPersonalizado)
				 {
					 
					 JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
			           caixa_texto.addFocusListener(new FocusAdapter()
							 {
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
	
	public void getDadosGlobais() {
		//gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		 GerenciadorLog = dados.getGerenciadorLog();
		 
		 //usuario logado
		  login = dados.getLogin();

	}
}
