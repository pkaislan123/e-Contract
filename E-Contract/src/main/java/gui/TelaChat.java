package main.java.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
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
import main.java.classesExtras.RenderizadorChat;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
import main.java.outros.BaixarNotasFiscais;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.ReproduzirAudio;
import main.java.outros.TratarDados;
import main.java.relatoria.RelatorioContratoComprador;
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLogin;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;
import net.miginfocom.swing.MigLayout;



import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TelaChat extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<CadastroLogin> usuarios = new ArrayList<>();

	private GerenciarBancoLogin gerenciarUsuarios, gerenciarMensagens;
	DefaultTableModel modelo_usuarios = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private JTabbedPane abaConversas;
	private JTable tabela_usuarios_online;
	private int contadorMensagens[];
	private ArrayList<RegistroMensagem> registro_mensagens = new ArrayList<>();
	private ArrayList<DefaultListModel<CadastroLogin.Mensagem>> listModels = new ArrayList<DefaultListModel<CadastroLogin.Mensagem>>();
	private JFrame telaPrincipal;
	private TelaChat isto;
	public TelaChat(Window janela_pai) {

		getDadosGlobais();
		// setModal(true);

		 isto = this;

		setResizable(false);
		setTitle("E-Contract - Mensagens\r\n");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 678);
		painelPrincipal.setBackground(new Color(0, 0, 0));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		JPanel painelMensagens = new JPanel();
		painelMensagens.setBounds(10, 229, 417, 395);
		painelMensagens.setBackground(new Color(51, 51, 0));
		painelPrincipal.add(painelMensagens);

		modelo_usuarios.addColumn("Usuario");
		modelo_usuarios.addColumn("Ip");
		modelo_usuarios.addColumn("Status");
		painelMensagens.setLayout(null);

		abaConversas = new JTabbedPane(JTabbedPane.TOP);
		abaConversas.setBackground(new Color(51, 51, 0));
		abaConversas.setBounds(0, 0, 417, 395);
		painelMensagens.add(abaConversas);

		JPanel panel = new JPanel();
		panel.setBounds(10, 51, 417, 167);
		painelPrincipal.add(panel);

		tabela_usuarios_online = new JTable(modelo_usuarios);
		tabela_usuarios_online.setForeground(Color.WHITE);
		tabela_usuarios_online.setBackground(new Color(0, 51, 102));
		tabela_usuarios_online.setRowHeight(30);
		tabela_usuarios_online.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					CadastroLogin login_selecionado;
					int indiceDaLinha = tabela_usuarios_online.getSelectedRow();
					login_selecionado = usuarios.get(indiceDaLinha);

					

				}
			}
		});
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tabela_usuarios_online);
		scrollPane.setBounds(0, 0, 417, 167);
		panel.add(scrollPane);

		JLabel lblNewLabel = new JLabel("Minimizar");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(348, 23, 64, 17);
		painelPrincipal.add(lblNewLabel);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isto.setVisible(false);
			}
		});
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		
		

		   Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	        java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	        int taskBarHeight = scrnSize.height - winSize.height;
	        System.out.printf("Altura: %d\n", taskBarHeight);
	        

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		java.awt.Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - getWidth();
		int y = (int) rect.getMaxY() - getHeight() - taskBarHeight;

		this.setLocation(x, y);
		
		

	 
		
		
		setAlwaysOnTop(true);
		
		procuraUsuariosOnline();
		//procurarMensagens();
		abrirChat();
		//procurarMensagens();
		//this.setVisible(true);

	}
	
	public void mostrar(int index_tela_pai) {
		
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	JOptionPane.showMessageDialog(isto, "id da tela pai: " + index_tela_pai);

	    GraphicsDevice[] gd = ge.getScreenDevices();
	    Rectangle rect = gd[index_tela_pai].getDefaultConfiguration().getBounds();
		rect = gd[index_tela_pai].getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - getWidth();
		int y = (int) rect.getMaxY() - getHeight() - taskBarHeight;
	    this.setLocation(x, y);
	    this.setVisible(true);
	}
	
	
	
	
	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void procuraUsuariosOnline() {

		new Thread() {

			@Override
			public void run() {
				while (true) {
					if(gerenciarUsuarios == null)
					 gerenciarUsuarios = new GerenciarBancoLogin();
					ArrayList<CadastroLogin> usuarios_encontrados = gerenciarUsuarios.getUsuarios();

					// verifica se esse usuario tem um ip

					for (CadastroLogin user : usuarios_encontrados) {
						if (user != null && !user.getLogin().equals(login.getLogin())) {
							String ip = user.getIp_ativo();
							int indice = -1;

							System.out.println("usuarios_encontrados: " + user.getLogin() + " Ip: " + ip);
							boolean contem = false;

							int num_row_table = tabela_usuarios_online.getRowCount();
							System.out.println("Numero de usuarios: " + num_row_table);
							if (num_row_table > 0) {
								for (int i = 0; i < num_row_table; i++) {
									String login = tabela_usuarios_online.getValueAt(i, 0).toString();
									if (login.equals(user.getLogin())) {
										contem = true;
										indice = i;

										break;

									}

								}

							}

							if (ip != null) {
								TesteConexao testar = new TesteConexao();
								if (testar.doPing(ip)) {
									if (!contem) {
										modelo_usuarios.addRow(new Object[] { user.getLogin(), ip, "Online" });
										usuarios.add(user);
									} else {
										tabela_usuarios_online.setValueAt("Online", indice, 2);

									}
								} else {
									if (!contem) {
										modelo_usuarios.addRow(new Object[] { user.getLogin(), ip, "Offline" });
										usuarios.add(user);
									} else {
										tabela_usuarios_online.setValueAt("Offline", indice, 2);

									}
								}

							} else {

								if (!contem) {
									modelo_usuarios.addRow(new Object[] { user.getLogin(), "Sem IP", "Offline" });
									usuarios.add(user);
								} else {
									tabela_usuarios_online.setValueAt("Offline", indice, 2);

								}

							}

						}

					}

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();

	}

	public void procurarMensagens() {

		new Thread() {

			public void run() {

				while (true) {

					ArrayList<CadastroLogin.Mensagem> mensagens = null;

					if (usuarios != null && usuarios.size() > 0) {
						for (CadastroLogin login_selecionado : usuarios) {

							// pega as mensagens
							//JOptionPane.showMessageDialog(null, "procurando mensagens da conversa entre "
								//	+ login.getLogin() + " e " + login_selecionado.getLogin());
							 gerenciarMensagens = new GerenciarBancoLogin();
							mensagens = gerenciarMensagens.getMensagens(login.getId(), login_selecionado.getId());

							RegistroMensagem registro = new RegistroMensagem();
							registro.setLogin_selecionado(login_selecionado);
							/*JOptionPane.showMessageDialog(null,
									"numero de mensagens encontradas:  " + mensagens.size());
*/
							registro.setNum_mensagens_atual(mensagens.size());

							boolean ja_registrado = false;
							int indice = -1;

							for (int i = 0; i < registro_mensagens.size(); i++) {
								if (registro_mensagens.get(i) != null) {
									if (registro_mensagens.get(i).getLogin_selecionado().getId() == login_selecionado
											.getId()) {
										// este login ja tem registro

										ja_registrado = true;
										indice = i;
										break;
									}
								}
							}

							if (!ja_registrado) {
								// se este login ainda nao estiver registro, adiciona ele no array

								registro_mensagens.add(registro);
								//JOptionPane.showMessageDialog(null, "Nova mensagem recebida!");

							} else {
								// se este login ja estiver registrado, verifique se chegou uma
								// mensagem nova verificando o numero de mensagens antigo com o lido agora
								/*JOptionPane.showMessageDialog(null, "Este login ja tem registro de mensagens, login : "
										+ login_selecionado.getLogin());
*/
								if (registro_mensagens.get(indice).getNum_mensagens_atual() < registro
										.getNum_mensagens_atual()) {
									//atualizar listmodel
									
									//JOptionPane.showMessageDialog(null, "Nova mensagem recebida!");
									

									for(DefaultListModel<CadastroLogin.Mensagem> listModel : listModels) {
					
									
										if(listModel.get(0).getId_remetente() == mensagens.get(0).getId_remetente()) {
											listModel.clear();
											
											for (CadastroLogin.Mensagem msg : mensagens) {

												listModel.addElement(msg);

											}

										}
									}
								}

								registro_mensagens.add(indice, registro);
							}

						}
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

	
	
	public void abrirChat() {
		
		new Thread() {
			
			public void run() {
				
				while(true) {
		
	 if(gerenciarUsuarios == null)				
		 gerenciarUsuarios = new GerenciarBancoLogin();
		ArrayList<CadastroLogin> usuarios_encontrados = gerenciarUsuarios.getUsuarios();
		
	    for(CadastroLogin login_selecionado : usuarios_encontrados) {
	    	if(login_selecionado.getId() != login.getId()) {
				// cria uma abas para este usuario
				boolean ja_existe = false;

				for (int i = 0; i < abaConversas.getComponents().length; i++) {
					if (abaConversas.getComponent(i) instanceof JPanel) {

						System.out.println("Nome do componenet: " + abaConversas.getComponent(i).getName());
						if (abaConversas.getComponent(i).getName().equals(login_selecionado.getLogin())) {
							ja_existe = true;
							break;
						}

					}
				}

				if (!ja_existe) {

					// criar a modelo da lista
					DefaultListModel<CadastroLogin.Mensagem> listModel = new DefaultListModel<>();
					// cria o redenrizador
					RenderizadorChat render = new RenderizadorChat();
					render.setServidor(login.getId());

					// recupera as mensagens

					if(gerenciarUsuarios == null)
					 gerenciarUsuarios = new GerenciarBancoLogin();
					// ArrayList<CadastroLogin.Mensagem> mensagens =
					// gerenciar.getMensagens(login.getId(),login_selecionado.getId());
					ArrayList<CadastroLogin.Mensagem> mensagens = null;

					// pega as mensagens
					mensagens = gerenciarUsuarios.getMensagens(login.getId(), login_selecionado.getId());

					RegistroMensagem registro = new RegistroMensagem();
					registro.setLogin_selecionado(login_selecionado);
					registro.setNum_mensagens_atual(mensagens.size());

					boolean ja_registrado = false;
					int indice = -1;

					for (int i = 0; i < registro_mensagens.size(); i++) {
						if (registro_mensagens.get(i) != null) {
							if (registro_mensagens.get(i).getLogin_selecionado()
									.getId() == login_selecionado.getId()) {
								// este login ja tem registro
								ja_registrado = true;
								indice = i;
								break;
							}
						}
					}

					if (!ja_registrado) {
						// se este login ainda nao estiver registro, adiciona ele no array

						registro_mensagens.add(registro);
					} else {
						// se este login ja estiver registrado, verifique se chegou uma
						// mensagem nova verificando o numero de mensagens antigo com o lido agora

						if (registro_mensagens.get(indice).getNum_mensagens_atual() < registro
								.getNum_mensagens_atual()) {
							//JOptionPane.showMessageDialog(isto, "Nova mensagem recebida!");
						}

						registro_mensagens.add(indice, registro);
					}

					// adiciona as mensagens na lista
					for (CadastroLogin.Mensagem msg : mensagens) {

						listModel.addElement(msg);

					}


					String nameAba = login_selecionado.getLogin();

					JPanel panelGuiaMensagens = new JPanel();
					panelGuiaMensagens.setBounds(0, 0, 314, 338);
					panelGuiaMensagens.setName(nameAba);
					panelGuiaMensagens.setBackground(new Color(51, 51, 0));
					panelGuiaMensagens.setLayout(new MigLayout("", "[grow][]", "[grow][]"));
					abaConversas.addTab(login_selecionado.getLogin(), panelGuiaMensagens);

					JList<CadastroLogin.Mensagem> lista_mensagens = new JList<>(listModel);

					JScrollPane scrollPane = new JScrollPane(lista_mensagens);
					panelGuiaMensagens.add(scrollPane, "cell 0 0 2 1,grow");

					JTextArea enviar_mensagem = new JTextArea();
					panelGuiaMensagens.add(enviar_mensagem, "cell 0 1,growx");
					enviar_mensagem.setLineWrap(true);

					JButton btnEnviarMensagem = new JButton("Enviar");
					panelGuiaMensagens.add(btnEnviarMensagem, "cell 1 1");

					lista_mensagens.setCellRenderer(render);

					// scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					// scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    listModels.add(listModel);

					btnEnviarMensagem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							String mensagem = enviar_mensagem.getText();

							GetData data = new GetData();
							String agora = data.getData();
							String hora = data.getHora();

							CadastroLogin.Mensagem mensagem_enviar = new CadastroLogin.Mensagem();
							mensagem_enviar.setConteudo(mensagem);
							mensagem_enviar.setData(agora);
							mensagem_enviar.setHora(hora);
							mensagem_enviar.setId_destinatario(login_selecionado.getId());
							mensagem_enviar.setId_remetente(login.getId());

							if(gerenciarMensagens == null)
							 gerenciarMensagens = new GerenciarBancoLogin();
							boolean enviado = gerenciarMensagens.enviarMensagem(mensagem_enviar);
							if (enviado)
								listModel.addElement(mensagem_enviar);

							enviar_mensagem.setText("");

						}
					});
					
					
					
					//cria uma thread que fica escutando se recebeu novas mensagens
					new Thread() {

						public void run() {

							while (true) {

								        ArrayList<CadastroLogin.Mensagem> mensagens = null;


										// pega as mensagens
										//JOptionPane.showMessageDialog(null, "procurando mensagens da conversa entre "
											//	+ login.getLogin() + " e " + login_selecionado.getLogin());
										if(gerenciarMensagens == null)
								        gerenciarMensagens = new GerenciarBancoLogin();
										mensagens = gerenciarMensagens.getMensagens(login.getId(), login_selecionado.getId());

										RegistroMensagem registro = new RegistroMensagem();
										registro.setLogin_selecionado(login_selecionado);
										/*JOptionPane.showMessageDialog(null,
												"numero de mensagens encontradas:  " + mensagens.size());
			*/
										registro.setNum_mensagens_atual(mensagens.size());

										boolean ja_registrado = false;
										int indice = -1;

										for (int i = 0; i < registro_mensagens.size(); i++) {
											if (registro_mensagens.get(i) != null) {
												if (registro_mensagens.get(i).getLogin_selecionado().getId() == login_selecionado
														.getId()) {
													// este login ja tem registro

													ja_registrado = true;
													indice = i;
													break;
												}
											}
										}

										if (!ja_registrado) {
											// se este login ainda nao estiver registro, adiciona ele no array

											registro_mensagens.add(registro);
											//JOptionPane.showMessageDialog(null, "Nova mensagem recebida!");

										} else {
											// se este login ja estiver registrado, verifique se chegou uma
											// mensagem nova verificando o numero de mensagens antigo com o lido agora
											/*JOptionPane.showMessageDialog(null, "Este login ja tem registro de mensagens, login : "
													+ login_selecionado.getLogin());
			*/
											if (registro_mensagens.get(indice).getNum_mensagens_atual() < registro
													.getNum_mensagens_atual()) {
												//atualizar listmodel
												if(isto.isVisible()) {
													
												}else {
													((TelaMain) telaPrincipal).setNumeroMensagensNovas();
												}
												//JOptionPane.showMessageDialog(null, "Nova mensagem recebida!");
												
												
										
											
								
														listModel.clear();
														
														for (CadastroLogin.Mensagem msg : mensagens) {

															listModel.addElement(msg);

														}
														//cria uma nova notificacao de mensagem
                                                         
														CadastroLogin.Mensagem ultima_mensagem = listModel.get(listModel.size() - 1);
														
														if(!(ultima_mensagem.getId_remetente() == login.getId())) {
														CadastroLogin login_msg = gerenciarMensagens.getLogin(ultima_mensagem.getId_remetente());
														String text = login_msg.getLogin() + " diz: \n             " + ultima_mensagem.getConteudo();
														((TelaMain) telaPrincipal).setNovaNotificacaoMensagem(text);

														int maxValue = scrollPane.getVerticalScrollBar().getMaximum();
														scrollPane.getViewport().setViewPosition(new Point(0,maxValue));
														}
											}

											registro_mensagens.add(indice, registro);
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
	    }
	}
	    

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//fecha o while
}//fecha a run
}.start();//fecha a thread
		
	}
	
	public static class RegistroMensagem {

		private CadastroLogin login_selecionado;
		private int num_mensagens_atual;

		public CadastroLogin getLogin_selecionado() {
			return login_selecionado;
		}

		public void setLogin_selecionado(CadastroLogin login_selecionado) {
			this.login_selecionado = login_selecionado;
		}

		public int getNum_mensagens_atual() {
			return num_mensagens_atual;
		}

		public void setNum_mensagens_atual(int num_mensagens_atual) {
			this.num_mensagens_atual = num_mensagens_atual;
		}

	}
	
	public void setTelaPai(JFrame pai) {
		this.telaPrincipal = pai;
	}

}
