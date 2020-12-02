package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cadastros.CadastroLogin;
import manipular.GetDadosGlobais;
import outros.DadosGlobais;
import outros.JPanelBackground;
import tratamento_proprio.Log;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.Insets;

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
			
			if(login.getDireitos() == 1) {
				if(login.getGenero().equals("Masculino"))
				 lblDireitos.setText("Administrador do Sistema");
				else
					 lblDireitos.setText("Administradora do Sistema");
			}
				else if (login.getDireitos() == 2) {
					if(login.getGenero().equals("Masculino"))
						 lblDireitos.setText("Gerente Financeiro");
						else
							 lblDireitos.setText("Gerente Financeira");				}
				else if (login.getDireitos() == 3) {
					if(login.getGenero().equals("Masculino"))
						 lblDireitos.setText("Auxiliar Administrativo");
						else
							 lblDireitos.setText("Auxiliar Administrativo");				}
			
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
}
