package gui;

import java.applet.Applet;
import graficos.JPanelGrafico;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;


import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import cadastros.CadastroBaseArquivos;
import cadastros.CadastroBaseDados;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.Contato;
import cadastros.DadosContratos;
import chat.Cliente;
import chat.Servidor;
import classesExtras.RenderizadorChat;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoLogin;
import conexaoBanco.GerenciarBancoPadrao;
import conexoes.TesteConexao;
import manipular.ConfiguracoesGlobais;
import manipular.EditarWord;
import manipular.GetDadosGlobais;
import outros.DadosGlobais;
import outros.GetData;
import outros.JPanelBackground;
import graficos.JPanelGrafico;
import outros.ReproduzirAudio;
import principal.MainTeste;
import tratamento_proprio.Log;
import views_personalizadas.TelaMensagens;
import views_personalizadas.TelaNotificacao;
import views_personalizadas.TelaNotificacaoSuperior;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;

public class TelaPrincipal extends JFrame implements GetDadosGlobais {

	private JPanelBackground contentPane;

	private JFrame isto;
	private JLabel lblUser;
	private JLabel lblDireitos;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private JPanelGrafico painelGrafico;
	private JLabel lblTotalContratos, lblTotalContratosAssinar, lblTotalContratosAssinados;
	private boolean executou = false;
	private DadosContratos dados_contratos = new DadosContratos();

	private JLabel lblBD, lblBaseDeArquivos, lblNuvem, lblnet, urlBancoDados, imgBaseDados, urlBaseArquivos,
			imgBaseArquivos, urlInternet, imgInternet, urlNuvem, imgNuvem, lblNovaMensagem;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<CadastroLogin> usuarios = new ArrayList<>();

	DefaultTableModel modelo_usuarios = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	
	
	private TelaChat telaChat;

	public TelaPrincipal() {

		getDadosGlobais();

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & isto.MAXIMIZED_BOTH) == isto.MAXIMIZED_BOTH) {
					// pega a resolucao da tela
					Toolkit tk = Toolkit.getDefaultToolkit();
					Dimension d = tk.getScreenSize();
					System.out.println("Screen width = " + d.width);
					System.out.println("Screen height = " + d.height);

					// pega o tamanho da barra de tarefas
					Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
					java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
							.getMaximumWindowBounds();
					int taskBarHeight = scrnSize.height - winSize.height;
					System.out.printf("Altura: %d\n", taskBarHeight);
				}
			}
		});

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		lblDireitos = new JLabel();
		lblDireitos.setBackground(Color.WHITE);
		lblDireitos.setForeground(Color.BLACK);

		lblUser = new JLabel();
		lblUser.setBackground(Color.WHITE);
		lblUser.setForeground(Color.BLACK);

		isto = this;
		setResizable(true);

		DadosGlobais dados = DadosGlobais.getInstance();
		dados.setTelaPrincipal(this);

		setTitle("E-Contract");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, d.width, d.height - taskBarHeight);
		contentPane = new JPanelBackground();

		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setBounds(0, 0, 1129, 56);
		contentPane.add(menuBar);

		JMenu Dados = new JMenu("Cadastros");
		Dados.setBackground(Color.WHITE);
		Dados.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(Dados);

		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.setMargin(new Insets(0, 10, 0, 0));
		mntmClientes.setBackground(Color.WHITE);
		mntmClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/equipe.png")));
		mntmClientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente clientes = new TelaCliente(1, 0);
				clientes.setVisible(true);
			}
		});
		Dados.add(mntmClientes);

		JMenuItem mntmArmazns = new JMenuItem("Armazéns");
		mntmArmazns.setMargin(new Insets(0, 10, 0, 0));
		mntmArmazns.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/silo(1).png")));
		mntmArmazns.setBackground(Color.WHITE);
		mntmArmazns.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmArmazns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				TelaArmazem tela = new TelaArmazem();

			}
		});
		Dados.add(mntmArmazns);

		JMenuItem mntmSafra = new JMenuItem("Safra");
		mntmSafra.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/cultivo.png")));
		mntmSafra.setMargin(new Insets(0, 10, 0, 0));
		mntmSafra.setBackground(Color.WHITE);
		mntmSafra.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmSafra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSafra safra = new TelaSafra();
			}
		});
		Dados.add(mntmSafra);

		JMenuItem mntmProdutos = new JMenuItem("Produtos");
		mntmProdutos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/comida.png")));
		mntmProdutos.setMargin(new Insets(0, 10, 0, 0));
		mntmProdutos.setBackground(Color.WHITE);
		mntmProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				TelaProdutos tela = new TelaProdutos(0);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmProdutos);

		JMenuItem mntmUsurios = new JMenuItem("Usuários");
		mntmUsurios.setMargin(new Insets(0, 10, 0, 0));
		mntmUsurios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/usuarios.png")));
		mntmUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaUsuarios usuarios = new TelaUsuarios();
			}
		});
		Dados.add(mntmUsurios);

		JMenuItem mntmNewMenuItem = new JMenuItem("Transportadores");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores tela = new TelaTransportadores(0);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmNewMenuItem);

		JMenu mnContratos = new JMenu("Contratos");
		mnContratos.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnContratos);

		JMenuItem mntmContratos = new JMenuItem("Contratos");
		mntmContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos telaContratos = new TelaContratos(0);
				telaContratos.setVisible(true);
			}
		});
		mnContratos.add(mntmContratos);

		JMenuItem mntmNovoModeloContrato = new JMenuItem("Modelos");
		mntmNovoModeloContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TelaModelosContratos telaModelosContratos = new TelaModelosContratos();
				TelaImportarModelo telaModelosContratos = new TelaImportarModelo();
			}
		});
		mnContratos.add(mntmNovoModeloContrato);

		JMenu mnFerramentas = new JMenu("Ferramentas");
		mnFerramentas.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnFerramentas);

		JMenu mnPlanilhasDeControle = new JMenu("Planilhas de Controle");
		mnFerramentas.add(mnPlanilhasDeControle);

		JMenuItem mntmAPartirDe = new JMenuItem("a partir de NFe Siare");
		mntmAPartirDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPlanilhaNFe tela = new TelaPlanilhaNFe();
			}
		});
		mnPlanilhasDeControle.add(mntmAPartirDe);

		JMenuItem mntmAPartirDe_1 = new JMenuItem("a partir de NFe Interna");
		mntmAPartirDe_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPlanilhaNFeInternas tela = new TelaPlanilhaNFeInternas();

			}
		});
		mnPlanilhasDeControle.add(mntmAPartirDe_1);

		JMenuItem mntmBaixarNotas = new JMenuItem("Baixar Notas");
		mntmBaixarNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaBaixarNotas tela = new TelaBaixarNotas();
			}
		});
		mnFerramentas.add(mntmBaixarNotas);

		JMenu mnNewMenu = new JMenu("Configurações");
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);

		JMenuItem mntmPastas = new JMenuItem("Preferências");
		mntmPastas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPreferencias tela = new TelaPreferencias();
			}
		});
		mntmPastas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmPastas);

		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUser.setBounds(1146, 11, 194, 27);
		lblUser.setText(login.getNome() + " " + login.getSobrenome());
		contentPane.add(lblUser);

		lblDireitos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDireitos.setBounds(1169, 37, 171, 19);
		contentPane.add(lblDireitos);

		JPanel painelInfoConexao = new JPanel();
		painelInfoConexao.setBackground(Color.WHITE);
		painelInfoConexao.setBounds(1099, 109, 251, 209);
		contentPane.add(painelInfoConexao);
		painelInfoConexao.setLayout(null);

		JLabel lblInfo = new JLabel("Informações de Conexão");
		lblInfo.setBounds(0, 0, 230, 14);
		painelInfoConexao.add(lblInfo);

		lblnet = new JLabel("Internet:");
		lblnet.setBounds(55, 25, 175, 14);
		painelInfoConexao.add(lblnet);

		lblBaseDeArquivos = new JLabel("Base de Arquivos:");
		lblBaseDeArquivos.setBounds(55, 118, 175, 14);
		painelInfoConexao.add(lblBaseDeArquivos);

		lblBD = new JLabel("Banco de Dados:");
		lblBD.setBounds(55, 162, 175, 14);
		painelInfoConexao.add(lblBD);

		urlBancoDados = new JLabel("erro");
		urlBancoDados.setBounds(55, 180, 135, 14);
		painelInfoConexao.add(urlBancoDados);

		urlBaseArquivos = new JLabel("erro");
		urlBaseArquivos.setBounds(55, 137, 135, 14);
		painelInfoConexao.add(urlBaseArquivos);

		urlInternet = new JLabel("http://www.google.com.br");
		urlInternet.setBounds(55, 43, 186, 14);
		painelInfoConexao.add(urlInternet);

		imgBaseDados = new JLabel("New label");
		imgBaseDados.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_dados_offline.png")));
		imgBaseDados.setBounds(13, 162, 32, 32);
		painelInfoConexao.add(imgBaseDados);

		imgBaseArquivos = new JLabel("");
		imgBaseArquivos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_arquivos_offline.png")));
		imgBaseArquivos.setBounds(13, 118, 32, 32);
		painelInfoConexao.add(imgBaseArquivos);

		imgInternet = new JLabel("");
		imgInternet.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/internet_offline.png")));
		imgInternet.setBounds(13, 25, 32, 32);
		painelInfoConexao.add(imgInternet);

		imgNuvem = new JLabel("");
		imgNuvem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/nuvem_offline.png")));
		imgNuvem.setBounds(13, 75, 32, 32);
		painelInfoConexao.add(imgNuvem);

		lblNuvem = new JLabel("Nuvem");
		lblNuvem.setBounds(55, 75, 175, 14);
		painelInfoConexao.add(lblNuvem);

		urlNuvem = new JLabel("https://www.dropbox.com/");
		urlNuvem.setBounds(55, 93, 186, 14);
		painelInfoConexao.add(urlNuvem);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isto.dispose();
				TelaLogin entrada = new TelaLogin();
				entrada.setVisible(true);
			}
		});
		btnSair.setBounds(1251, 70, 89, 23);
		contentPane.add(btnSair);

		if (login.getConfigs_privilegios().getNivel_privilegios() == 1) {
			if (login.getGenero().equals("Masculino"))
				lblDireitos.setText("Administrador do Sistema");
			else
				lblDireitos.setText("Administradora do Sistema");
		} else if (login.getConfigs_privilegios().getNivel_privilegios() == 2) {
			if (login.getGenero().equals("Masculino"))
				lblDireitos.setText("Gerente Financeiro");
			else
				lblDireitos.setText("Gerente Financeira");
		} else if (login.getConfigs_privilegios().getNivel_privilegios() == 3) {
			if (login.getGenero().equals("Masculino"))
				lblDireitos.setText("Auxiliar Administrativo");
			else
				lblDireitos.setText("Auxiliar Administrativo");
		}

		painelGrafico = new JPanelGrafico(0, 0);
		painelGrafico.setBounds(10, 62, 487, 269);
		contentPane.add(painelGrafico);
		painelGrafico.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Total de Contratos:");
		lblNewLabel_1.setBounds(22, 31, 117, 14);
		painelGrafico.add(lblNewLabel_1);

		lblTotalContratos = new JLabel("");
		lblTotalContratos.setBounds(149, 31, 46, 14);
		painelGrafico.add(lblTotalContratos);

		JLabel lblNewLabel_1_1 = new JLabel("Assinar:");
		lblNewLabel_1_1.setBounds(22, 56, 94, 14);
		painelGrafico.add(lblNewLabel_1_1);

		lblTotalContratosAssinar = new JLabel("");
		lblTotalContratosAssinar.setBounds(149, 56, 46, 14);
		painelGrafico.add(lblTotalContratosAssinar);

		JLabel lblNewLabel_1_2 = new JLabel("Assinados:");
		lblNewLabel_1_2.setBounds(22, 81, 94, 14);
		painelGrafico.add(lblNewLabel_1_2);

		lblTotalContratosAssinados = new JLabel("");
		lblTotalContratosAssinados.setBounds(149, 81, 46, 14);
		painelGrafico.add(lblTotalContratosAssinados);

		modelo_usuarios.addColumn("Usuario");
		modelo_usuarios.addColumn("Ip");
		modelo_usuarios.addColumn("Status");
		
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(telaChat == null) {
				  telaChat = new TelaChat();
				  telaChat.setTelaPai(isto);
				}
				else {
					telaChat.setVisible(true);
					java.awt.EventQueue.invokeLater(new Runnable() { 
					    public void run() { 
					lblNovaMensagem.setIcon(null);
					
					lblNovaMensagem.repaint();
			    	lblNovaMensagem.updateUI();
					    } 
					}); 
				
				}
				
			}
		});
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(102, 204, 204));
		panel_2.setBounds(1099, 627, 251, 62);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_3 = new JLabel("     Mensagens");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_3.setBackground(new Color(0, 0, 153));
		lblNewLabel_3.setBounds(133, 17, 161, 28);
		panel_2.add(lblNewLabel_3);
		
		 lblNovaMensagem = new JLabel("");
		lblNovaMensagem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNovaMensagem.setForeground(Color.WHITE);
		lblNovaMensagem.setBounds(33, 17, 32, 32);
		panel_2.add(lblNovaMensagem);

		getDadosContratos();
		atualizarGraficoContratos();
		buscarConexao();
		buscaConexaoBanco();
		buscaConexaoServidorArquivos();
		buscarConexaoNuvem();
         
		if(telaChat == null) {
			  telaChat = new TelaChat();
			  telaChat.setTelaPai(isto);
		      telaChat.setVisible(false);
		}
			else {
				telaChat.setVisible(true);
			}
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	@Override
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void getDadosContratos() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		int numeroTotalContratos = gerenciar.getNumeroTotalContratos();
		int numerosContratosSemAssinar = gerenciar.getNumeroContratosParaAssinar();

		dados_contratos.setNumero_total_contratos(numeroTotalContratos);
		dados_contratos.setNumero_contratos_assinar(numerosContratosSemAssinar);
		dados_contratos.setNumero_contratos_assinados(numeroTotalContratos - numerosContratosSemAssinar);

		System.out.println("Numero total de contrato: " + dados_contratos.getNumero_total_contratos());
		System.out.println("Numero total de contratos para assinar: " + dados_contratos.getNumero_contratos_assinar());
		System.out.println("Numero total de contrato ja assinados: " + dados_contratos.getNumero_contratos_assinados());

		if (!executou) {
			if (numerosContratosSemAssinar != -1) {
				new Thread() {
					@Override
					public void run() {

						novaNotificacao(
								"Há " + numerosContratosSemAssinar
										+ " contratos com carencia de assinatura na base de dados",
								"/audio/beep_notificacao.wav", 1);

					}
				}.start();

			} else {
				System.out.println("Não foi possivel buscar o numero de contratos sem assinar no banco de dados!");
			}
		}

	}

	public void buscarConexao() {
		new Thread() {
			private URL url = null;

			@Override
			public void run() {
				while (true) {
					try {
						url = new URL("http://www.google.com.br");

						System.out.println("Tentando conexao!");

						URLConnection connection = url.openConnection();
						connection.connect();
						lblnet.setText("Internet: Conectada");
						imgInternet.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/internet_online.png")));

					} catch (IOException f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar a internet!");
						novaNotificacao("Sem conexão com a internet, algumas funções seram limitadas até a reconexão!",
								"/audio/beep_erro_net.wav", 2);

						lblnet.setText("Internet: Desconectada");

						imgInternet.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/internet_offline.png")));

					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	public void buscarConexaoNuvem() {
		new Thread() {
			private URL url = null;

			@Override
			public void run() {
				while (true) {
					try {
						url = new URL("https://www.dropbox.com/");

						System.out.println("Tentando conexao nuvem!");

						URLConnection connection = url.openConnection();
						connection.connect();
						lblNuvem.setText("Nuvem: Conectada");
						imgNuvem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/nuvem_online.png")));

					} catch (IOException f) {
						f.printStackTrace();
						System.out.println("erro ao se conectar ao dropbpx!");
						novaNotificacao("Sem conexão com a nuvem, algumas funções seram limitadas até a reconexão!",
								"/audio/beep_erro_net.wav", 2);
						lblNuvem.setText("Nuvem: Desconectada");

						imgNuvem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/nuvem_offline.png")));

					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	public void novaNotificacao(String texto, String song, int repeticao) {
		try {
			Thread.sleep(1000);
			URL url = TelaPrincipal.class.getResource(song);
			TelaNotificacao tela = new TelaNotificacao();

			new Thread() {
				@Override
				public void run() {
					ReproduzirAudio player = new ReproduzirAudio();
					for (int i = 0; i < repeticao; i++) {
						player.play(url);
					}
				}
			}.start();

			Thread.sleep(2000);

			tela.setVisible(true);
			tela.setMensagem(texto);

			Thread.sleep(5000);
			tela.fechar();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buscaConexaoBanco() {

		new Thread() {
			public void run() {

				while (true) {

					CadastroBaseDados bd;

					bd = configs_globais.getBaseDados();

					String url = "jdbc:mysql://" + bd.getHost() + ":" + bd.getPorta() + "/" + bd.getNome_banco()
							+ "?useTimezone=true&serverTimezone=UTC";

					urlBancoDados.setText(url);
					GerenciarBancoPadrao gerenciar = new GerenciarBancoPadrao();
					if (gerenciar.getConexao()) {
						System.out.println("Banco de Dados OnLine!");
						lblBD.setText("Banco de Dados: Conectada");
						imgBaseDados.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_dados_online.png")));

					} else {
						System.out.println("Banco de Dados Offline!");
						lblBD.setText("Banco de Dados: Desconectada");

						imgBaseDados.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_dados_offline.png")));

					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();
	}

	public void buscaConexaoServidorArquivos() {

		new Thread() {
			public void run() {

				while (true) {

					CadastroBaseArquivos base = configs_globais.getServidor_arquivos();

					String host = base.getServidor();

					urlBaseArquivos.setText(host);
					lblBaseDeArquivos.setText("Base de Arquivos: Conectada");
					TesteConexao gerenciar = new TesteConexao();
					if (gerenciar.doPing(host)) {
						System.out.println("Banco de Arquivos OnLine!");
						imgBaseArquivos.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_arquivos_online.png")));

					} else {
						lblBaseDeArquivos.setText("Base de Arquivos: Desconectada");

						System.out.println("Banco de Arquivos Offline!");
						imgBaseArquivos.setIcon(
								new ImageIcon(TelaPrincipal.class.getResource("/imagens/base_arquivos_offline.png")));

					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();
	}

	public void atualizarGraficoContratos() {

		getDadosContratos();
		lblTotalContratos.setText(dados_contratos.getNumero_total_contratos() + "");
		lblTotalContratosAssinados.setText(dados_contratos.getNumero_contratos_assinados() + "");
		lblTotalContratosAssinar.setText(dados_contratos.getNumero_contratos_assinar() + "");

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= dados_contratos.getNumero_contratos_assinados()) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGrafico.setDados(dados_contratos.getNumero_total_contratos(), i);
					painelGrafico.repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					i++;
				}

			}
		}.start();

	}

	
	public void setNumeroMensagensNovas() {
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	lblNovaMensagem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_mensagem_nao_lida.png")));

		    	lblNovaMensagem.repaint();
		    	lblNovaMensagem.updateUI();
						   
		    } 
		}); 
	}
	
	
	public void setNovaNotificacaoMensagem(String mensagem) {
		
		//if(!telaChat.isVisible())
			try {
				TelaNotificacaoSuperior tela = new TelaNotificacaoSuperior();

				tela.setMensagem(mensagem);
				tela.setVisible(true);

				Thread.sleep(5000);
				tela.fechar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
	
}
