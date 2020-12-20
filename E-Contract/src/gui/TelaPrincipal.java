package gui;

import java.applet.Applet;
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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import conexaoBanco.GerenciarBancoContratos;
import manipular.EditarWord;
import manipular.GetDadosGlobais;
import outros.DadosGlobais;
import outros.JPanelBackground;
import outros.ReproduzirAudio;
import tratamento_proprio.Log;
import views_personalizadas.TelaNotificacao;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.Insets;
import javax.swing.JButton;

public class TelaPrincipal extends JFrame implements GetDadosGlobais{

	private JPanelBackground contentPane;

	private JFrame isto;
	private JLabel lblUser;
	private JLabel lblDireitos;
	private Log GerenciadorLog;
	private CadastroLogin login;

	
	public TelaPrincipal() {
		
		getDadosGlobais();
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if ((e.getNewState() & isto.MAXIMIZED_BOTH) == isto.MAXIMIZED_BOTH){
					//pega a resolucao da tela
					 Toolkit tk = Toolkit.getDefaultToolkit();
					    Dimension d = tk.getScreenSize();
					    System.out.println("Screen width = " + d.width);
					    System.out.println("Screen height = " + d.height);
					
					//pega o tamanho da barra de tarefas
					    Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
				        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
				        int taskBarHeight = scrnSize.height - winSize.height;
				        System.out.printf("Altura: %d\n", taskBarHeight);
				   }
			}
		});
		
		 Toolkit tk = Toolkit.getDefaultToolkit();
		    Dimension d = tk.getScreenSize();
		    System.out.println("Screen width = " + d.width);
		    System.out.println("Screen height = " + d.height);
		
		//pega o tamanho da barra de tarefas
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
		
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
					TelaCliente clientes = new TelaCliente(1,0, null);
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
					
					TelaProdutos tela = new TelaProdutos();
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
					TelaTransportadores tela = new TelaTransportadores();
				}
			});
			Dados.add(mntmNewMenuItem);
			
			JMenu mnContratos = new JMenu("Contratos");
			mnContratos.setFont(new Font("Arial", Font.PLAIN, 18));
			menuBar.add(mnContratos);
			
			JMenuItem mntmContratos = new JMenuItem("Contratos");
			mntmContratos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					TelaContratos telaContratos = new TelaContratos();
				}
			});
			mnContratos.add(mntmContratos);
			
			JMenuItem mntmNovoModeloContrato = new JMenuItem("Modelos");
			mntmNovoModeloContrato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				//TelaModelosContratos telaModelosContratos = new TelaModelosContratos();
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
			
			JPanel panel = new JPanel();
			panel.setBounds(36, 95, 509, 271);
			contentPane.add(panel);
			
			
			
			//Cria um dataSet para inserir os dados que serão passados para a criação do gráfico tipo Pie
			DefaultCategoryDataset barra = new DefaultCategoryDataset();
			 
			//Adiciona os dados ao dataSet deve somar um total de 100%
			barra.setValue( 500, "Contratos de Soja", "");
			barra.setValue(500, "Contratos de Sorgo", "");
			barra.setValue(500,"Contratos de Milho", "");
			 
			//Cria um objeto JFreeChart passando os seguintes parametros
			JFreeChart grafico = ChartFactory.createBarChart("A", "B",
			"C", //Titulo do grafico
			barra, //DataSet
			PlotOrientation.VERTICAL,
			true, //Para mostrar ou não a legenda
			true, //Para mostrar ou não os tooltips
			false);
			 
			panel.add( new ChartPanel( grafico ) );
			
			JPanel painelInfoConexao = new JPanel();
			painelInfoConexao.setBounds(944, 104, 406, 165);
			contentPane.add(painelInfoConexao);
			painelInfoConexao.setLayout(null);
			
			JLabel lblInfo = new JLabel("Informações de Conexão");
			lblInfo.setBounds(0, 0, 230, 14);
			painelInfoConexao.add(lblInfo);
			
			JLabel lblnet = new JLabel("Internet:");
			lblnet.setBounds(87, 25, 58, 14);
			painelInfoConexao.add(lblnet);
			
			JLabel lblNewLabel = new JLabel("Intranet:");
			lblNewLabel.setBounds(89, 50, 51, 14);
			painelInfoConexao.add(lblNewLabel);
			
			JLabel lblBaseDeArquivos = new JLabel("Base de Arquivos:");
			lblBaseDeArquivos.setBounds(47, 75, 98, 14);
			painelInfoConexao.add(lblBaseDeArquivos);
			
			JLabel lblBD = new JLabel("Banco de Dados:");
			lblBD.setBounds(59, 124, 98, 14);
			painelInfoConexao.add(lblBD);
			
			JLabel lblBaseDeArquivosnuvem = new JLabel("Base de Arquivos(Nuvem):");
			lblBaseDeArquivosnuvem.setBounds(10, 100, 135, 14);
			painelInfoConexao.add(lblBaseDeArquivosnuvem);
			
			JLabel urlBancoDados = new JLabel("erro");
			urlBancoDados.setBounds(145, 124, 156, 14);
			painelInfoConexao.add(urlBancoDados);
			
			JLabel urlNuvem = new JLabel("erro");
			urlNuvem.setBounds(145, 99, 156, 14);
			painelInfoConexao.add(urlNuvem);
			
			JLabel urlBaseArquivos = new JLabel("erro");
			urlBaseArquivos.setBounds(145, 74, 156, 14);
			painelInfoConexao.add(urlBaseArquivos);
			
			JLabel urlRoteador = new JLabel("erro");
			urlRoteador.setBounds(145, 50, 156, 14);
			painelInfoConexao.add(urlRoteador);
			
			JLabel urlInternet = new JLabel("http://www.google.com.br");
			urlInternet.setBounds(145, 25, 156, 14);
			painelInfoConexao.add(urlInternet);
			
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
			
			
			if(login.getConfigs_privilegios().getNivel_privilegios() == 1) {
				if(login.getGenero().equals("Masculino"))
				 lblDireitos.setText("Administrador do Sistema");
				else
					 lblDireitos.setText("Administradora do Sistema");
			}
				else if (login.getConfigs_privilegios().getNivel_privilegios() == 2) {
					if(login.getGenero().equals("Masculino"))
						 lblDireitos.setText("Gerente Financeiro");
						else
							 lblDireitos.setText("Gerente Financeira");				}
				else if (login.getConfigs_privilegios().getNivel_privilegios() == 3) {
					if(login.getGenero().equals("Masculino"))
						 lblDireitos.setText("Auxiliar Administrativo");
						else
							 lblDireitos.setText("Auxiliar Administrativo");				}
			
			
			getDadosContratos();
			buscarConexao();
		this.setLocationRelativeTo(null);

		
		 
		 
		this.setVisible(true);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}


	@Override
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
	
	
	
	
	public void getDadosContratos() {
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		int numerosContratosSemAssinar = gerenciar.getNumeroContratosParaAssinar();
		
		if(numerosContratosSemAssinar != -1) {
			new Thread() {
				@Override 
				public void run() {
					
					
								novaNotificacao("Há " + numerosContratosSemAssinar + " contratos com carencia de assinatura na base de dados" 
										,"/audio/beep_notificacao.wav", 1);
							
				}
			}.start();
		
	}else {
		System.out.println("Não foi possivel buscar o numero de contratos sem assinar no banco de dados!");
	}
	
}
	
	public void buscarConexao() {
		new Thread() {
			private URL url = null;
			@Override
			public void run() {
			while(true) {	
				try {
				     Thread.sleep(10000);
						url = new URL("http://www.google.com.br");
					
					System.out.println("Tentando conexao!");


					URLConnection connection = url.openConnection();
					connection.connect();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("erro ao se conectar a internet!");
					novaNotificacao("Sem conexão com a internet, algumas funções seram limitadas até a reconexão!", "/audio/beep_erro_net.wav", 2);
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
	            for(int i = 0; i < repeticao; i++) {
	            player.play(url);
	            }
					}
				}.start();
			
		    	Thread.sleep(2000);

		      	tela.setVisible(true);
	             tela.setMensagem(texto );

	       
		

	        Thread.sleep(5000);
             tela.fechar();
	    	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
