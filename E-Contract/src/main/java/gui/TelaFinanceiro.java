package main.java.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.cadastros.CadastroLogin;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.outros.ReproduzirAudio;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;

public class TelaFinanceiro extends JFrame {

	private JPanel painelPrincipal;
	private TelaFinanceiro isto;
	private TelaTarefas tela_tarefas;
	private JLabel lblUser, lblDireitos,lblNumeroTarefas;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private TelaPost telaPost;
	private GerenciarBancoContratos gerenciarAtualizarTarefas;
	private int num_tarefas_nesta_secao = -1;
	private boolean notificando = false;

	
	public TelaFinanceiro(Window window) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getDadosGlobais();
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
		DadosGlobais dados = DadosGlobais.getInstance();
		
		DisplayMode display =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);
		isto = this;
		
		setTitle("Finanças");
		
		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu mnNewMenu = new JMenu("Cadastros");
		mnNewMenu.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/financeiro_icone_24px.png")));
		mnNewMenu.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem234567 = new JMenuItem("Instituição Bancária");
		mntmNewMenuItem234567.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_ibs_32px.png")));
		mntmNewMenuItem234567.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem234567.setMargin(new Insets(0, 10, 0, 0));
		mntmNewMenuItem234567.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(-1,-1,isto);
				tela.setVisible(true);
				
			}
		});
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Cliente/Fornecedor");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente clientes = new TelaCliente(1, 0, isto);
				clientes.setVisible(true);
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/equipe.png")));
		mntmNewMenuItem.setMargin(new Insets(0, 14, 0, 0));
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnNewMenu.add(mntmNewMenuItem);
		mnNewMenu.add(mntmNewMenuItem234567);
		
		JMenuItem mntmNewMenuItem_876 = new JMenuItem("Centro de Custo");
		mntmNewMenuItem_876.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_876.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/centro_custo_24x24.jpg")));
		mntmNewMenuItem_876.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCentroCusto tela = new TelaFinanceiroCentroCusto(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_876.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_876);
		
		JMenuItem mntmNewMenuItem_26566 = new JMenuItem("Condição de Pagamento");
		mntmNewMenuItem_26566.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_cp_32px.png")));
		mntmNewMenuItem_26566.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_26566.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_26566.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_26566);
		
		JMenuItem mntmNewMenuItem_234 = new JMenuItem("Grupo de Contas");
		mntmNewMenuItem_234.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_grupo_contas_24px.png")));
		mntmNewMenuItem_234.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_234.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroGrupoConta tela = new TelaFinanceiroGrupoConta(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_234.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_234);
		
		JMenuItem mntmNewMenuItem_42345 = new JMenuItem("Contas");
		mntmNewMenuItem_42345.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/conta-bancaria_24x24.png")));
		mntmNewMenuItem_42345.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroConta tela = new TelaFinanceiroConta(-1,-1,isto);
				tela.setVisible(true);
			}
		});
		mntmNewMenuItem_42345.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmNewMenuItem_42345.setMargin(new Insets(0, 10, 0, 0));
		mnNewMenu.add(mntmNewMenuItem_42345);
		
		JMenu mnNewMenu_1 = new JMenu("Lançamentos");
		mnNewMenu_1.setIcon(new ImageIcon(TelaFinanceiro.class.getResource("/imagens/icone_lancamentos_36px.png")));
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem lancamentos = new JMenuItem("Lançamentos");
		lancamentos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lancamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   TelaFinanceiroLancamento tela = new TelaFinanceiroLancamento(-1,-1,isto);
			   tela.setVisible(true);
			}
		});
		mnNewMenu_1.add(lancamentos);
		painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[242px,grow][][::300px,grow][]", "[78px][grow]"));
		
		painelPrincipal.add(menuBar, "cell 0 0,alignx left,aligny center");
		
		JMenu mnFerramentas = new JMenu("Ferramentas");
		mnFerramentas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/ferramentas-de-reparacao.png")));
		mnFerramentas.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(mnFerramentas);
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

				
				if (tela_tarefas == null) {
					tela_tarefas = new TelaTarefas(isto);
					tela_tarefas.getTarefas();
					tela_tarefas.setVisible(true);
				} else {
					tela_tarefas.setVisible(true);
				}
			}
		});
		mntmNewMenuItem_5.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/imagens/icone_menu_tarefas.png")));
		mntmNewMenuItem_5.setMargin(new Insets(0, 10, 0, 0));
		mnFerramentas.add(mntmNewMenuItem_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 153, 51));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 13;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 24;
		gbc_panel_1.gridy = 0;
		painelPrincipal.add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new MigLayout("", "[][][][][][][]", "[][-18.00][]"));
		
		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (telaPost == null) {
					telaPost = new TelaPost(isto);
				} else {
					telaPost.setVisible(true);
				}
			}
		});
		panel_1.add(lblNewLabel_8, "cell 0 0");
		lblNewLabel_8.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_anotacoes_tela_principal.png")));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tela_tarefas == null) {
					tela_tarefas = new TelaTarefas(isto);
					tela_tarefas.getTarefas();
					tela_tarefas.setVisible(true);
				} else {
					tela_tarefas.setVisible(true);
				}
			}
		});
		panel_1.add(lblNewLabel_1, "cell 1 0");
		lblNewLabel_1.setIcon(new ImageIcon(TelaMain.class.getResource("/imagens/icone_tarefa.png")));
		
		JLabel lblNewLabel_2 = new JLabel("Você tem:");
		panel_1.add(lblNewLabel_2, "cell 2 0");
		
		 lblNumeroTarefas = new JLabel("0");
		 panel_1.add(lblNumeroTarefas, "cell 3 0");
		 lblNumeroTarefas.setFont(new Font("Tahoma", Font.BOLD, 18));
		 
		 JLabel lblNewLabel_4 = new JLabel("tarefas");
		 panel_1.add(lblNewLabel_4, "cell 4 0");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		painelPrincipal.add(panel_2, "cell 2 0 2 1,grow");
		panel_2.setLayout(new MigLayout("", "[]", "[]"));
		
	
		
		 lblUser = new JLabel();
			panel_2.add(lblUser, "cell 0 0,alignx left,aligny top");
			lblUser.setText("<dynamic> <dynamic>");
			lblUser.setForeground(Color.BLACK);
			lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblUser.setBackground(Color.WHITE);
			
			 lblDireitos = new JLabel();
			panel_2.add(lblDireitos, "cell 0 1");
			lblDireitos.setText("Administrador do Sistema");
			lblDireitos.setForeground(Color.BLACK);
			lblDireitos.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblDireitos.setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		painelPrincipal.add(panel, "cell 0 1 4 1,grow");
		logar();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		atualizarNumTarefas();
		this.setLocationRelativeTo(window);
	}

	
	public void logar() {
		lblUser.setText(login.getNome() + " " + login.getSobrenome());
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
	}
	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
	}
	public void setNovaNotificacaoMensagem(String mensagem) {
		// if(!telaChat.isVisible())
		try {
			notificando = true;
			TelaNotificacaoSuperior tela = new TelaNotificacaoSuperior();
			tela.setMensagem(mensagem);
			tela.setVisible(true);
			Thread.sleep(5000);
			tela.fechar();
			notificando = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void atualizarNumTarefas() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true) {
					if (gerenciarAtualizarTarefas == null)
						gerenciarAtualizarTarefas = new GerenciarBancoContratos();
					int num_agora = gerenciarAtualizarTarefas.getNumTarefas(login.getId());
					lblNumeroTarefas.setText(num_agora + "");
					if (num_tarefas_nesta_secao == -1) {
						if (num_agora > 0) {
							while (notificando == true) {
								//System.out.println("Notificacao em andamento");
							}
							novaNotificacao("Você possui tarefas a concluir", "/main/java/audio/beep_notificacao.wav", 1);
							num_tarefas_nesta_secao = num_agora;
						} else {
							num_tarefas_nesta_secao = 0;
						}
					} else if (num_agora > num_tarefas_nesta_secao) {
						// nova tarefa recebida, notificar
						while (notificando) {
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						num_tarefas_nesta_secao = num_agora;
						novaNotificacao("Nova Tarefa Recebida!", "/main/java/audio/beep_notificacao.wav", 1);
					} else if (num_agora > num_tarefas_nesta_secao) {
						// quantidade de tarefas e a mesma
						num_tarefas_nesta_secao = num_agora;
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, "Erro ao buscar tarefas!");
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void novaNotificacao(String texto, String song, int repeticao) {
		try {
			notificando = true;
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
			notificando = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
