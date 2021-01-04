package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
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

import cadastros.CadastroLogin;
import cadastros.CadastroLogin.Mensagem;
import chat.Servidor;
import classesExtras.RenderizadorChat;
import conexaoBanco.GerenciarBancoLogin;
import conexoes.TesteConexao;
import manipular.ConfiguracoesGlobais;
import net.miginfocom.swing.MigLayout;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TelaChat extends JDialog{

	private final JPanel painelPrincipal = new JPanel();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private ArrayList<CadastroLogin> usuarios = new ArrayList<>();


	DefaultTableModel modelo_usuarios = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};
	private JTabbedPane abaConversas;
	private JTable tabela_usuarios_online;
	private int contadorMensagens[];
	private ArrayList<RegistroMensagem> registro_mensagens;

	public TelaChat() {
		
		getDadosGlobais();
	//	setModal(true);

		TelaChat isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Tela Padrao");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 575);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JPanel painelMensagens = new JPanel();
		painelMensagens.setBounds(0, 141, 522, 395);
		painelMensagens.setBackground(Color.WHITE);
		painelPrincipal.add(painelMensagens);
		
		

		modelo_usuarios.addColumn("Usuario");
		modelo_usuarios.addColumn("Ip");
		modelo_usuarios.addColumn("Status");
		 painelMensagens.setLayout(null);
		
		 abaConversas = new JTabbedPane(JTabbedPane.TOP);
		 abaConversas.setBounds(0, 0, 344, 395);
		painelMensagens.add(abaConversas);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 28, 344, 108);
		painelPrincipal.add(panel);
		
		tabela_usuarios_online = new JTable(modelo_usuarios);
		tabela_usuarios_online.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					CadastroLogin login_selecionado;
					int indiceDaLinha = tabela_usuarios_online.getSelectedRow();
					login_selecionado = usuarios.get(indiceDaLinha);
					
					java.awt.EventQueue.invokeLater(new Runnable() { 
					    public void run() { 
					    	//cria uma abas para este usuario
					    	boolean ja_existe  = false;
					    	
					    	for(int i = 0; i < abaConversas.getComponents().length; i++) {
					   		 if(abaConversas.getComponent(i) instanceof JPanel) {
					   			 
					   			 System.out.println("Nome do componenet: " +  abaConversas.getComponent(i).getName());
					   			 if(abaConversas.getComponent(i).getName().equals(login_selecionado.getLogin()) ) {
					   				 ja_existe = true;
					   				 break;
					   			 }
					   			 
					   		 }
					    	}
					    	
					    	if(!ja_existe) {
					    	
					    		//criar a modelo da lista
					    		DefaultListModel<CadastroLogin.Mensagem> listModel = new DefaultListModel<>();
					    		
					    		//cria o redenrizador
					    		RenderizadorChat render = new RenderizadorChat();
					    		render.setServidor(login.getId());
					    		
					    		//recupera as mensagens
					    		
					    		GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
					    		//ArrayList<CadastroLogin.Mensagem> mensagens = gerenciar.getMensagens(login.getId(),login_selecionado.getId());
					    		ArrayList<CadastroLogin.Mensagem> mensagens = null;
					    		
					    		for(CadastroLogin login_selecionado : usuarios) {

					                //pega as mensagens
					    			mensagens = gerenciar.getMensagens(login.getId(),login_selecionado.getId());
					        		
					        		RegistroMensagem registro = new RegistroMensagem();
					        		registro.setLogin_selecionado(login_selecionado);
					        		registro.setNum_mensagens_atual(mensagens.size());
					        		
					        		boolean ja_registrado = false;
					        		int indice = -1;
					        		
					        	   for(int i = 0; i <  registro_mensagens.size(); i++ ) {	
					        		if(registro_mensagens.get(i) != null) {
					        			if(registro_mensagens.get(i).getLogin_selecionado().getId() == login_selecionado.getId()) {
					        				//este login ja tem registro
					        				ja_registrado = true;
					        				indice = i;
					        				break;
					        			}
					        		}
					        	   }
					        	   
					        	   if(!ja_registrado) {
					        		   //se este login ainda nao estiver registro, adiciona ele no array
					        		   
					        		registro_mensagens.add(registro);
					        	   }else {
					        		   //se este login ja estiver registrado, verifique se chegou uma
					        		   //mensagem nova verificando o numero de mensagens antigo com o lido agora
					        		   
					        		   if(registro_mensagens.get(indice).getNum_mensagens_atual() < registro.getNum_mensagens_atual() ) {
					        			   JOptionPane.showMessageDialog(null, "Nova mensagem recebida!");
					        		   }
					        		   
					        		   registro_mensagens.add(indice, registro);
					        	   }
					        	   
					        	 
					      
					    			
					    		}
					    		
					    		
					    		//adiciona as mensagens na lista
					    		for(CadastroLogin.Mensagem msg : mensagens) {
					    			
					    			
							            listModel.addElement(msg);

					    			
					    			

					    		}
					    		
					    		//cria a lista


					    	
					    		String nameAba = login_selecionado.getLogin();
					
						


				    		JPanel panelGuiaMensagens = new JPanel();
				    		panelGuiaMensagens.setBounds(0, 0, 314, 338);
				    		panelGuiaMensagens.setName(nameAba);
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

							//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
							//scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
							
				
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
									
				
									
									
									GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
									boolean enviado = gerenciar.enviarMensagem(mensagem_enviar);
						            if(enviado)
						            	listModel.addElement(mensagem_enviar);
            
						            
						            enviar_mensagem.setText("");
							
								}
							});
					    
					    	}
					    	
					    	
					    
					    	
					    	
					    	

									   
					    } 
					}); 
					
					
					
					
				}
			}
		});
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(tabela_usuarios_online);
		scrollPane.setBounds(7, 7, 337, 85);
		panel.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Minimizar");
		lblNewLabel.setBounds(446, 0, 64, 17);
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
	        int x = (int) rect.getMaxX() - getWidth( ) ;
	        int y = (int) rect.getMaxY() - getHeight() - taskBarHeight  ;
		

           this.setLocation(x, y);
			setAlwaysOnTop(true);
			procuraUsuariosOnline();
			abrirServidorChat();
			procurarMensagens();
			
     
		   this.setVisible(true);
   
		
		
		
	}

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}


	public void procuraUsuariosOnline() {

		new Thread() {

			@Override
			public void run() {
				while (true) {
					GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
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
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();

	}

	public void abrirServidorChat() {

		new Thread() {

			@Override
			public void run() {

				try {
					// Cria os objetos necessário para instânciar o servidor

					ServerSocket server = new ServerSocket(1234);
					System.out.println("Servidor IP: " + login.getIp_ativo() + " ativo na porta: " + 1234);

					while (true) {
						System.out.println("Aguardando conexão...");
						Socket con = server.accept();
						System.out.println("Cliente conectado...");
						Thread t = new Servidor(con);
						t.start();
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}.start();
	}
	
	
	
	public ArrayList<CadastroLogin.Mensagem> procurarMensagens() {
		ArrayList<CadastroLogin.Mensagem> mensagens = null;
		
		for(CadastroLogin login_selecionado : usuarios) {

            //pega as mensagens
			GerenciarBancoLogin gerenciar = new GerenciarBancoLogin();
			mensagens = gerenciar.getMensagens(login.getId(),login_selecionado.getId());
    		
    		RegistroMensagem registro = new RegistroMensagem();
    		registro.setLogin_selecionado(login_selecionado);
    		registro.setNum_mensagens_atual(mensagens.size());
    		
    		boolean ja_registrado = false;
    		int indice = -1;
    		
    	   for(int i = 0; i <  registro_mensagens.size(); i++ ) {	
    		if(registro_mensagens.get(i) != null) {
    			if(registro_mensagens.get(i).getLogin_selecionado().getId() == login_selecionado.getId()) {
    				//este login ja tem registro
    				ja_registrado = true;
    				indice = i;
    				break;
    			}
    		}
    	   }
    	   
    	   if(!ja_registrado) {
    		   //se este login ainda nao estiver registro, adiciona ele no array
    		   
    		registro_mensagens.add(registro);
    	   }else {
    		   //se este login ja estiver registrado, verifique se chegou uma
    		   //mensagem nova verificando o numero de mensagens antigo com o lido agora
    		   
    		   if(registro_mensagens.get(indice).getNum_mensagens_atual() < registro.getNum_mensagens_atual() ) {
    			   JOptionPane.showMessageDialog(null, "Nova mensagem recebida!");
    		   }
    		   
    		   registro_mensagens.add(indice, registro);
    	   }
    	   
    	 
  
			
		}
		
		return mensagens;

		
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
	
	
}
	

