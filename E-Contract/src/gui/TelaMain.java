package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import outros.DadosGlobais;

import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import conexaoBanco.GerenciarBancoContratos;

import javax.swing.border.LineBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import graficos.JPanelGrafico;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;



public class TelaMain extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
    private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
    private TelaMain isto;
    private JDialog telaPai;

	public TelaMain(Window janela_pai) {

		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		
		isto = this;
		setResizable(true);

		

		setTitle("E-Contract");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, d.width, d.height - taskBarHeight);
		
		setBackground(new Color(255, 255, 255));
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[580.00px,grow][][][][][][][][][][][][][][][][][][][][grow]", "[14px][][][307.00,grow][263.00,grow][14.00px][]"));
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_anotacoes_tela_principal.png")));
		painelPrincipal.add(lblNewLabel_8, "cell 2 0");
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_tarefa.png")));
		painelPrincipal.add(lblNewLabel_1, "cell 4 0");
		
		JLabel lblNewLabel_2 = new JLabel("Você tem:");
		painelPrincipal.add(lblNewLabel_2, "cell 6 0");
		
		JLabel lblNumeroTarefas = new JLabel("0");
		lblNumeroTarefas.setFont(new Font("Tahoma", Font.BOLD, 18));
		painelPrincipal.add(lblNumeroTarefas, "cell 7 0");
		
		JLabel lblUser = new JLabel();
		lblUser.setText("<dynamic> <dynamic>");
		lblUser.setForeground(Color.BLACK);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUser.setBackground(Color.WHITE);
		painelPrincipal.add(lblUser, "cell 20 0");
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.WHITE);
		painelPrincipal.add(menuBar, "cell 0 0,grow");
		
		JMenu Dados = new JMenu("Cadastros");
		Dados.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_cadastro_menu.png")));
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
				TelaCliente clientes = new TelaCliente(1, 0, isto);
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

				TelaProdutos tela = new TelaProdutos(0, isto);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmProdutos);

		JMenuItem mntmUsurios = new JMenuItem("Usuários");
		mntmUsurios.setMargin(new Insets(0, 10, 0, 0));
		mntmUsurios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/usuarios.png")));
		mntmUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaUsuarios usuarios = new TelaUsuarios(0);
				usuarios.setVisible(true);
			}
		});
		Dados.add(mntmUsurios);

		JMenuItem mntmNewMenuItem = new JMenuItem("Transportadores");
		mntmNewMenuItem.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/caminhao.png")));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores tela = new TelaTransportadores(0, null);
				tela.setVisible(true);
			}
		});
		Dados.add(mntmNewMenuItem);

		JMenu mnContratos = new JMenu("Contratos");
		mnContratos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_contrato_menu.png")));
		mnContratos.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnContratos);

		JMenuItem mntmContratos = new JMenuItem("Contratos");
		mntmContratos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/contrato.png")));
		mntmContratos.setMargin(new Insets(0, 10, 0, 0));
		mntmContratos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos telaContratos = new TelaContratos(0, isto);
				telaContratos.setVisible(true);
			}
		});
		mnContratos.add(mntmContratos);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Relatoria");
		mntmNewMenuItem_1.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/grafico.png")));
		mntmNewMenuItem_1.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaRelatoriaContratos tela = new TelaRelatoriaContratos();

			}
		});
		mnContratos.add(mntmNewMenuItem_1);

		JMenu mnFerramentas = new JMenu("Ferramentas");
		mnFerramentas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/ferramentas-de-reparacao.png")));
		mnFerramentas.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnFerramentas);

		JMenu mnPlanilhasDeControle = new JMenu("Planilhas de Controle");
		mnPlanilhasDeControle
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/aplicativo-de-planilha.png")));
		mnPlanilhasDeControle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnPlanilhasDeControle.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mnPlanilhasDeControle);

		JMenuItem mntmAPartirDe = new JMenuItem("a partir de NFe Siare");
		mntmAPartirDe.setMargin(new Insets(0, 10, 0, 0));
		mntmAPartirDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPlanilhaNFe tela = new TelaPlanilhaNFe();
			}
		});
		mnPlanilhasDeControle.add(mntmAPartirDe);

		JMenuItem mntmAPartirDe_1 = new JMenuItem("a partir de NFe Interna");
		mntmAPartirDe_1.setMargin(new Insets(0, 10, 0, 0));
		mntmAPartirDe_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPlanilhaNFeInternas tela = new TelaPlanilhaNFeInternas();

			}
		});
		mnPlanilhasDeControle.add(mntmAPartirDe_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Monitoria");
		mntmNewMenuItem_2.setMargin(new Insets(0, 10, 2, 0));
		mntmNewMenuItem_2
				.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/aplicativo-de-monitoria.png")));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaMonitoria monitor = new TelaMonitoria();
				monitor.setVisible(true);
				monitor.vigilante_todos_os_romaneios();

			}
		});
		mnFerramentas.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Anotações");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaNotas notas;

				if (TelaNotas.instance == null) {
					TelaNotas.instance = new TelaNotas(isto);
					TelaNotas.instance.setVisible(true);
				} else {
					TelaNotas.instance.setVisible(true);
				}

			}

		});
		mntmNewMenuItem_3.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem_3.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_notas.png")));
		mnFerramentas.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Calendário");
		mntmNewMenuItem_4.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_calendario.png")));
		mntmNewMenuItem_4.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Tarefas");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
			}
		});
		mntmNewMenuItem_5.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_tarefas.png")));
		mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_5);

		JMenu mnNewMenu = new JMenu("Configurações");
		mnNewMenu.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/preferencias.png")));
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);

		JMenuItem mntmPastas = new JMenuItem("Preferências");
		mntmPastas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/definicoes.png")));
		mntmPastas.setMargin(new Insets(0, 10, 0, 0));
		mntmPastas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaPreferencias tela = new TelaPreferencias();
			}
		});
		mntmPastas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmPastas);
		
		JLabel lblNewLabel_4 = new JLabel("tarefas");
		painelPrincipal.add(lblNewLabel_4, "cell 8 1");
		
		JButton btnSair = new JButton("Sair");
		painelPrincipal.add(btnSair, "cell 20 2,alignx right");
		
		JPanel panelContratos = new JPanel();
		panelContratos.setLayout(null);
		panelContratos.setBackground(Color.WHITE);
		painelPrincipal.add(panelContratos, "cell 0 3 9 1,grow");
		
		JPanelGrafico painelGraficoContratos = new JPanelGrafico(0, 0);
		painelGraficoContratos.setLayout(null);
		painelGraficoContratos.setBounds(10, 11, 493, 269);
		panelContratos.add(painelGraficoContratos);
		
		JLabel lblTotalContratosAssinados = new JLabel("Assinados: 0");
		lblTotalContratosAssinados.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratosAssinados.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratosAssinados.setBounds(10, 57, 173, 24);
		painelGraficoContratos.add(lblTotalContratosAssinados);
		
		JLabel lblTotalContratosAssinar = new JLabel("Assinar: 0");
		lblTotalContratosAssinar.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratosAssinar.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratosAssinar.setBounds(10, 92, 173, 24);
		painelGraficoContratos.add(lblTotalContratosAssinar);
		
		JLabel lblTotalContratos_1 = new JLabel("Total de Contratos: 0");
		lblTotalContratos_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblTotalContratos_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblTotalContratos_1.setBounds(10, 22, 173, 24);
		painelGraficoContratos.add(lblTotalContratos_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("");
		lblNewLabel_5_2.setBounds(419, 70, 64, 64);
		painelGraficoContratos.add(lblNewLabel_5_2);
		
		JLabel lblNewLabel = new JLabel("Safra:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(507, 55, 46, 14);
		panelContratos.add(lblNewLabel);
		
		JComboBox cbContratosPorSafra = new JComboBox();
		cbContratosPorSafra.setFont(new Font("Tahoma", Font.BOLD, 10));
		cbContratosPorSafra.setBounds(552, 41, 320, 34);
		panelContratos.add(cbContratosPorSafra);
		
		JLabel lblNewLabel_6 = new JLabel("Todas as Safras", SwingConstants.CENTER);
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_6.setBackground(new Color(0, 206, 209));
		lblNewLabel_6.setBounds(552, 11, 320, 22);
		panelContratos.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(752, 0, 313, 280);
		panelContratos.add(lblNewLabel_7);
		
		JPanel painelInfoConexao = new JPanel();
		painelInfoConexao.setLayout(null);
		painelInfoConexao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelInfoConexao.setBackground(Color.WHITE);
		painelPrincipal.add(painelInfoConexao, "cell 14 3 7 1,grow");
		
		JLabel lblInfo = new JLabel("Informações de Conexão");
		lblInfo.setBounds(0, 0, 230, 14);
		painelInfoConexao.add(lblInfo);
		
		JLabel lblnet = new JLabel("Internet:");
		lblnet.setBounds(55, 25, 175, 14);
		painelInfoConexao.add(lblnet);
		
		JLabel lblBaseDeArquivos = new JLabel("Base de Arquivos:");
		lblBaseDeArquivos.setBounds(55, 118, 175, 14);
		painelInfoConexao.add(lblBaseDeArquivos);
		
		JLabel lblBD = new JLabel("Banco de Dados:");
		lblBD.setBounds(55, 162, 175, 14);
		painelInfoConexao.add(lblBD);
		
		JLabel urlBancoDados = new JLabel("erro");
		urlBancoDados.setBounds(55, 180, 135, 14);
		painelInfoConexao.add(urlBancoDados);
		
		JLabel urlBaseArquivos = new JLabel("erro");
		urlBaseArquivos.setBounds(55, 137, 135, 14);
		painelInfoConexao.add(urlBaseArquivos);
		
		JLabel urlInternet = new JLabel("http://www.google.com.br");
		urlInternet.setBounds(55, 43, 186, 14);
		painelInfoConexao.add(urlInternet);
		
		JLabel imgBaseDados = new JLabel("New label");
		imgBaseDados.setBounds(13, 162, 32, 32);
		painelInfoConexao.add(imgBaseDados);
		
		JLabel imgBaseArquivos = new JLabel("");
		imgBaseArquivos.setBounds(13, 118, 32, 32);
		painelInfoConexao.add(imgBaseArquivos);
		
		JLabel imgInternet = new JLabel("");
		imgInternet.setBounds(13, 25, 32, 32);
		painelInfoConexao.add(imgInternet);
		
		JLabel imgNuvem = new JLabel("");
		imgNuvem.setBounds(13, 75, 32, 32);
		painelInfoConexao.add(imgNuvem);
		
		JLabel lblNuvem = new JLabel("Nuvem");
		lblNuvem.setBounds(55, 75, 175, 14);
		painelInfoConexao.add(lblNuvem);
		
		JLabel urlNuvem = new JLabel("https://www.dropbox.com/");
		urlNuvem.setBounds(55, 93, 186, 14);
		painelInfoConexao.add(urlNuvem);
		
		JPanel panelGraficoLinha = new JPanel();
		panelGraficoLinha.setLayout(null);
		panelGraficoLinha.setBackground(Color.WHITE);
		painelPrincipal.add(panelGraficoLinha, "cell 0 4,alignx left,growy");

		
		JLabel lblDireitos = new JLabel();
		lblDireitos.setForeground(Color.BLACK);
		lblDireitos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDireitos.setBackground(Color.WHITE);
		painelPrincipal.add(lblDireitos, "cell 20 5");
		

		this.setLocationRelativeTo(janela_pai);

		
		
		
	}
	
	
	
	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}
}
