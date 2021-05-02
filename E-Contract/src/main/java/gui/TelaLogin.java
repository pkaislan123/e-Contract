package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.cadastros.CadastroAditivo;
import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.CadastroTarefa;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroLogin.Mensagem;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroNota;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ArquivoConfiguracoes;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.EditarAditivo;
import main.java.manipular.EditarExcel;
import main.java.manipular.EditarWord;
import main.java.manipular.Email;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularArquivoTerceiros;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JPasswordFieldPersonalizado;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import java.awt.Toolkit;

public class TelaLogin extends JFrame implements GetDadosGlobais {

	private JPanelBackground contentPane;
	private JTextFieldPersonalizado entUser;
	private JLabel lblBemvindoDeVolta;
	private JPasswordField entSenha;
	private JPanelTransparent panel;
	private JLabel lblMostrar, lblEsconder, lblResult;
	private char previo;
	private Log GerenciadorLog = new Log();
	private CadastroLogin login;
	private JLabel lblNewLabel_1;

	public TelaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaLogin.class.getResource("/imagens/logo_icone4.png")));

		getDadosGlobais();

		TelaLogin isto = this;
		setTitle("E-Contract - Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1026, 586);

		contentPane = new JPanelBackground();
		contentPane.setBackground(new Color(139, 0, 139));
		URL url = getClass().getResource("/imagens/contrato" + 1 + ".jpg");
		ImageIcon img = new ImageIcon(url);
		contentPane.setImg(img);
		contentPane.repaint();
		/*
		 * new Thread() {
		 * 
		 * @Override public void run() { int i = 1; while(i <= 5) {
		 * 
		 * URL url = getClass().getResource("/imagens/contrato"+i+".jpg"); ImageIcon img
		 * = new ImageIcon(url); contentPane.setImg(img); contentPane.repaint(); try {
		 * Thread.sleep(5000); } catch (InterruptedException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } i++; if( i == 6) { i = 1; }
		 * 
		 * }
		 * 
		 * } }.start();
		 */

		// contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new JPanelTransparent();
		panel.setTransparencia(1);

		panel.setBackground(new Color(123, 104, 238));
		panel.setBounds(529, 158, 451, 353);
		panel.repaint();
		// URL url2 = getClass().getResource("fundo.jpg");
		// ImageIcon img2 = new ImageIcon(url2);
		contentPane.setLayout(null);
		// panel.setImg(img2);
		// panel.setOpaque(false);

		contentPane.add(panel);
		panel.setLayout(null);

		entUser = new JTextFieldPersonalizado();

		entUser.setBounds(48, 111, 277, 38);

		panel.add(entUser);
		entUser.setColumns(10);
		entUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String user = entUser.getText().toString();
					String senha = new String(entSenha.getPassword());
					int result = logar();
					if (result == 0) {
						GerenciadorLog.registrarLogDiario("aviso",
								"erro de login: usuario: " + user + " senha: " + senha + "classe:TelaLogin");

						lblResult.setText("Usuário ou senha Incorretos");
					} else {
						informarLogon();
						isto.dispose();
						TelaMain tela = new TelaMain(isto);
					}
				}

			}
		});

		entSenha = new JPasswordFieldPersonalizado(10);
		entSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String user = entUser.getText().toString();
					String senha = new String(entSenha.getPassword());
					int result = logar();
					if (result == 0) {
						GerenciadorLog.registrarLogDiario("aviso",
								"erro de login: usuario: " + user + " senha: " + senha + "classe:TelaLogin");

						lblResult.setText("Usuário ou senha Incorretos");
					} else {
						informarLogon();

						isto.dispose();
						TelaMain tela = new TelaMain(isto);
					}
				}

			}
		});

		entSenha.setBounds(48, 216, 277, 38);

		panel.add(entSenha);

		lblBemvindoDeVolta = new JLabel("Login");
		lblBemvindoDeVolta.setForeground(new Color(255, 255, 255));
		lblBemvindoDeVolta.setBounds(61, 37, 74, 26);
		panel.add(lblBemvindoDeVolta);
		lblBemvindoDeVolta.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
		lblBemvindoDeVolta.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemvindoDeVolta.setFont(new Font("Dialog", Font.BOLD, 22));

		JLabel lblUsurio = new JLabel("Usuário");
		lblUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsurio.setForeground(Color.WHITE);
		lblUsurio.setFont(new Font("Dialog", Font.BOLD, 14));
		lblUsurio.setBounds(76, 74, 74, 26);
		panel.add(lblUsurio);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSenha.setBounds(61, 179, 106, 26);
		panel.add(lblSenha);

		JButton btnLogin = new JButton("Entrar");

		btnLogin.setBackground(new Color(148, 0, 211));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBounds(225, 279, 100, 38);
		btnLogin.setText("Entrar");
		panel.add(btnLogin);

		lblResult = new JLabel("");
		lblResult.setForeground(new Color(255, 0, 0));
		lblResult.setBounds(48, 265, 277, 14);
		panel.add(lblResult);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaLogin.class.getResource("/imagens/fundoverde.jpg")));
		lblNewLabel_3.setBounds(48, 35, 277, 38);
		panel.add(lblNewLabel_3);

		lblMostrar = new JLabel("");
		lblMostrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblMostrar.setVisible(false);
				lblEsconder.setVisible(true);
				mostrarSenha();
			}
		});
		lblMostrar.setIcon(new ImageIcon(TelaLogin.class.getResource("/imagens/mostrar_senha.png")));
		lblMostrar.setBounds(335, 222, 32, 32);
		panel.add(lblMostrar);

		lblEsconder = new JLabel("");
		lblEsconder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblMostrar.setVisible(true);
				lblEsconder.setVisible(false);
				esconderSenha();
			}
		});
		lblEsconder.setIcon(new ImageIcon(TelaLogin.class.getResource("/imagens/esconder_senha.png")));
		lblEsconder.setBounds(335, 222, 32, 32);
		lblEsconder.setVisible(false);
		panel.add(lblEsconder);

		JLabel lblNewLabel = new JLabel("*Copyright Todos os Direitos Reservados E-Contract Titaniwm " + new GetData().getAnoAtual());
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setBounds(329, 522, 451, 14);
		contentPane.add(lblNewLabel);

		JLabel lblEcontract = new JLabel("e-Contract");
		lblEcontract.setForeground(new Color(255, 255, 255));
		lblEcontract.setFont(new Font("Arial", Font.BOLD, 30));
		lblEcontract.setBounds(193, 184, 182, 39);
		contentPane.add(lblEcontract);

		JLabel lblEficincia = new JLabel("Gestão de contratos");
		lblEficincia.setForeground(new Color(255, 255, 255));
		lblEficincia.setFont(new Font("Arial", Font.BOLD, 18));
		lblEficincia.setBounds(237, 219, 196, 29);
		contentPane.add(lblEficincia);

		JTextArea txtrNsDaTitaniwm = new JTextArea();
		txtrNsDaTitaniwm.setEditable(false);
		txtrNsDaTitaniwm.setFont(new Font("Arial", Font.BOLD, 18));
		txtrNsDaTitaniwm.setText(
				"Nós da Titaniwm damos total atenção a você eos dados da sua empresa!\r\n\r\nPor isso, tenha mais tempo pra você e sua      empresa e deixe seus contratos conosco.\r\n                                                                                                 \r\n                                                                                 \r\n\r\n");
		txtrNsDaTitaniwm.setForeground(Color.WHITE);
		txtrNsDaTitaniwm.setBackground(new Color(0, 0, 0, 0));
		txtrNsDaTitaniwm.setBorder(null);
		txtrNsDaTitaniwm.setOpaque(false);
		txtrNsDaTitaniwm.setBounds(78, 293, 409, 149);
		txtrNsDaTitaniwm.setLineWrap(true);
		contentPane.add(txtrNsDaTitaniwm);

		JLabel lblEquipeTitaniwm = new JLabel("Equipe Titaniwm");
		lblEquipeTitaniwm.setForeground(Color.WHITE);
		lblEquipeTitaniwm.setFont(new Font("Arial", Font.BOLD, 18));
		lblEquipeTitaniwm.setBounds(326, 426, 161, 29);
		contentPane.add(lblEquipeTitaniwm);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaLogin.class.getResource("/imagens/logo.png")));
		lblNewLabel_1.setBounds(51, 77, 217, 160);
		contentPane.add(lblNewLabel_1);
		
		JButton btnSair = new JButton("X");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
			}
		});
		btnSair.setForeground(Color.WHITE);
		btnSair.setBackground(new Color(102, 0, 153));
		btnSair.setBounds(981, 11, 35, 28);
		contentPane.add(btnSair);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = entUser.getText().toString();
				String senha = new String(entSenha.getPassword());
				int result = logar();
				if (result == 0) {
					lblResult.setText("Usuário ou senha Incorretos");

				} else {
					GerenciadorLog.registrarLogDiario("aviso", "usuario logou classe: TelaLogin");
					informarLogon();

					isto.dispose();
					TelaMain tela = new TelaMain(isto);

				}
			}
		});

		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					String user = entUser.getText().toString();
					String senha = new String(entSenha.getPassword());
					int result = logar();
					if (result == 0) {
						GerenciadorLog.registrarLogDiario("aviso",
								"erro de login: usuario: " + user + " senha: " + senha + "classe:TelaLogin");

						lblResult.setText("Usuário ou senha Incorretos");
					} else {
						informarLogon();

						isto.dispose();
						TelaMain tela = new TelaMain(isto);
					}
				}

			}
		});

		this.setLocationRelativeTo(null);
		this.setUndecorated(true);

		this.setVisible(true);
	}

	public int logar() {
		CadastroLogin logon = new CadastroLogin();
		GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
		String user = entUser.getText().toString();
		String senha = new String(entSenha.getPassword());
		int result = -1;

		logon = gerenciar.buscaLogin(user);

		if (user.length() <= 0 || user.equals(" ") || user.equals("") || user == null) {
			// lblResult.setText("Usuário ou senha Incorretos");
			result = 0;

		} else {

			if (senha.length() <= 0 || senha.equals(" ") || senha.equals("") || senha == null) {
				// lblResult.setText("Usuário ou senha Incorretos");
				result = 0;

			} else {
				if (logon != null) {
					try {
						if (logon.getSenha().equals(senha)) {
							DadosGlobais userGlobal = DadosGlobais.getInstance();
							userGlobal.setLogin(logon);

							result = 1;
							// isto.dispose();
							// TelaMain tela = new TelaMain();

						} else {
							// JOptionPane.showMessageDialog(null, "Usu�rio ou senha Incorretos");
							// lblResult.setText("Usuário ou senha Incorretos");
							result = 0;

						}
					} catch (Exception e) {
						// lblResult.setText("Usuário ou senha Incorretos");
						result = 0;

					}

				} else {
					// lblResult.setText("Usuário ou senha Incorretos");
					result = 0;

				}
			}

		}
		return result;

	}

	public void esconderSenha() {
		entSenha.setEchoChar(previo);

	}

	public void mostrarSenha() {
		if (entSenha.getEchoChar() != '\u0000')
			previo = entSenha.getEchoChar();
		entSenha.setEchoChar('\u0000');
	}

	@Override
	public void getDadosGlobais() {
		DadosGlobais userGlobal = DadosGlobais.getInstance();
		userGlobal.setGerenciadorLog(GerenciadorLog);
		login = userGlobal.getLogin();
	}

	public void informarLogon() {
		// informar ao banco que estou logando passando meu ip
		try {
			
			String ip = Inet4Address.getLocalHost().getHostAddress(); //seta o ip com o endereço da primeira interface (caminho infeliz)
			String nomehost = Inet4Address.getLocalHost().getHostName(); //pega o nome do host
			
			System.out.println("Nome do host: " + nomehost);

			
			for(InetAddress inet: InetAddress.getAllByName(nomehost)) //captura todos os endereços disponíveis de todas as interfaces
			{
				System.out.println("Nome da interface: " + (NetworkInterface.getByInetAddress(inet).getName()));

			if(NetworkInterface.getByInetAddress(inet).getName().contains("eth")) //pega o nome da interface em que está setado o endereço da vez e verifica
			//o nome da interface
			{
			ip = inet.getHostAddress(); //caso seja a de interesse, seta ip e pára
			break;
			}
			}			
			
			System.out.println(ip);
			// nome da maquina.

			GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();

			DadosGlobais userGlobal = DadosGlobais.getInstance();

			boolean informou_login = gerenciar.informarLogado(ip, userGlobal.getLogin().getId());
			if (informou_login) {
				System.out.println("logado para a base de dados");

			} else {
				System.out.println("nao foi possivel informar logado ao banco de dados");

			}

			String nomeDaMaquina = InetAddress.getLocalHost().getHostName();
			System.out.println(nomeDaMaquina);
		} catch (UnknownHostException | SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("nao foi possivel informar logado ao banco de dados");

			e.printStackTrace();
		}

	}
}
