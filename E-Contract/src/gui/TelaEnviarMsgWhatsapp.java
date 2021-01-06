package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.Contato;
import conexaoBanco.GerenciarBancoClientes;
import manipular.ConfiguracoesGlobais;
import manipular.Nuvem;
import manipular.Whatsapp;
import manipular.ZapMessenger;
import outros.DadosGlobais;
import outros.JPanelBackground;
import tratamento_proprio.Log;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;



public class TelaEnviarMsgWhatsapp extends JDialog {

	JPanel painelPrincipal = new JPanel();
	JPanelBackground rodape = new JPanelBackground();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField etNumero;
	private JCheckBox chkOutroContato, chkAnexarContrato;
	private JComboBox cBContato;
	private JLabel lblNaoinclua;
	private String nome_comprador_global, mensagem_anexo, mensagem_notificacao;
	private JRadioButton rdLivre, rdAnexo, rdNot;
	private CadastroContrato contrato_local;
	private JTextArea textArea ;
	private TelaEnviarMsgWhatsapp isto;
	private JLabel lblNaoAcentue;

		
	
	public void pesquisarContatos(int id_cliente) {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<Contato> lista_contatos =  gerenciar.getContatos(id_cliente);
		
		for(Contato contato : lista_contatos) {
			if(contato != null) {
				String celular = contato.getCelular();
			   if(celular != null && celular.length() == 11) {
				    //celular aceito
				   System.out.println("Celular:" + celular);
				   
				   cBContato.addItem(contato.getCargo() + " " + celular);
				   
			   }
			}
		}
		
	}


	public TelaEnviarMsgWhatsapp(CadastroContrato contrato) {
		//setAlwaysOnTop(true);

		//setModal(true);
		
		contrato_local = contrato;
        CadastroCliente compradores[] = contrato.getCompradores();
		
		System.out.println("Nome do comprador: " + compradores[0].getNome_empresarial() + "outro nome: " + compradores[0].getNome_fantaia());
		
		if(compradores[0].getTipo_pessoa() == 0) {
			//pessoa fisica
			nome_comprador_global = compradores[0].getNome_empresarial();

		}else {
			//pessoa juridica
			nome_comprador_global = compradores[0].getNome_fantaia();

		}
		
		
		getDadosGlobais();
		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Enviar Contrato");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 688);
		
		
		
		painelPrincipal.setBackground(new Color(0, 51, 102));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		 
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mensagem:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(29, 226, 89, 37);
		painelPrincipal.add(lblNewLabel);
		
		 textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(new Color(0,0,0,0));
		textArea.setOpaque(false);
		textArea.setBounds(119, 233, 297, 327);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		textArea.setLineWrap(true);
		painelPrincipal.add(textArea);
	
		
      
		JLabel lblContato = new JLabel("Contato:");
		lblContato.setForeground(Color.WHITE);
		lblContato.setFont(new Font("Arial", Font.BOLD, 14));
		lblContato.setBounds(53, 85, 65, 37);
		painelPrincipal.add(lblContato);
		
		 cBContato = new JComboBox();
		cBContato.setBounds(119, 93, 297, 23);
		painelPrincipal.add(cBContato);
		
		JLabel lblNewLabel_1 = new JLabel("       Enviar contrato via whatsapp");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBackground(new Color(51, 0, 153));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(0, 11, 259, 35);
		painelPrincipal.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(87, 640, 393, 37);
		panel.setBackground(new Color(51, 0, 153));
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isto.dispose();
			}
		});
		btnNewButton.setBounds(284, 4, 54, 30);
		panel.add(btnNewButton);
		btnNewButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton.setOpaque(false);
		btnNewButton.setBackground(new Color(51, 0, 102));
		
		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, 
			            "Enviar?", "Deseja enviar a mensagem?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					enviar_Mensagem();

			        }
				else
				{
					
				}
				
			 	
				
				
				
				
			
			}
		});
		btnNewButton_1.setBounds(212, 4, 60, 30);
		panel.add(btnNewButton_1);
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.setBackground(new Color(0, 102, 102));
		
		 chkOutroContato = new JCheckBox("Outro Contato:");
		chkOutroContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!chkOutroContato.isSelected()) {
					//esta selecionado, desmarque

					chkOutroContato.setSelected(false);
					
					etNumero.setEditable(false);
					etNumero.setEnabled(false);
					lblNaoinclua.setVisible(false);
					cBContato.setEnabled(true);
					
					
				}else {
					//nao esta selecionado
					
					chkOutroContato.setSelected(true);
					
					etNumero.setEditable(true);
					etNumero.setEnabled(true);
					cBContato.setEnabled(false);
					lblNaoinclua.setVisible(true);
					
				}
				
			}
		});
		chkOutroContato.setOpaque(false);
		chkOutroContato.setForeground(Color.WHITE);
		chkOutroContato.setFont(new Font("Arial", Font.PLAIN, 14));
		chkOutroContato.setBounds(29, 131, 124, 23);
		painelPrincipal.add(chkOutroContato);
		
		etNumero = new JTextField();
		etNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = etNumero.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
				if(texto.length()==1 && evt.getKeyChar() != '\b'){
					etNumero.setText("(" + etNumero.getText());
				}
				if(texto.length()==3  && evt.getKeyChar() != '\b'){
					etNumero.setText(etNumero.getText().concat(") "));
				}
			
				
				if(texto.length()==9 && evt.getKeyChar() != '\b'){
					etNumero.setText(etNumero.getText().concat("-"));
				}
				
				if(etNumero.getText().length()>=14){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					etNumero.setText(etNumero.getText().substring(0,14));
				}
			
			}
			}
		});
		etNumero.setEnabled(false);
		etNumero.setEditable(false);
		etNumero.setBounds(173, 127, 243, 33);
		painelPrincipal.add(etNumero);
		etNumero.setColumns(10);
		
		 lblNaoinclua = new JLabel("Não inclua o novo digito");
		 lblNaoinclua.setVisible(false);
		lblNaoinclua.setForeground(Color.ORANGE);
		lblNaoinclua.setBounds(173, 171, 243, 14);
		painelPrincipal.add(lblNaoinclua);
		
		
		
		
		  
			
			 chkAnexarContrato = new JCheckBox("Anexar Contrato na Mensagem?");
			 chkAnexarContrato.setFont(new Font("Arial", Font.PLAIN, 14));
			 chkAnexarContrato.setVisible(false);
			 chkAnexarContrato.setOpaque(false);
			 chkAnexarContrato.setBackground(new Color(0,0,0,0));
			chkAnexarContrato.setForeground(Color.MAGENTA);
			chkAnexarContrato.setBounds(119, 599, 297, 23);
			painelPrincipal.add(chkAnexarContrato);
			

			 rdNot = new JRadioButton("Notificação");
			rdNot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					
					if(rdNot.isSelected()) {
						

						rdNot.setSelected(true);	
						rdAnexo.setSelected(false);
						rdLivre.setSelected(false);
						
						chkAnexarContrato.setVisible(false);
						set_msg_notificacao();
						
					}else {
						rdNot.setSelected(true);	

					}
					
				}
			});
			rdNot.setSelected(true);
			rdNot.setForeground(Color.WHITE);
			rdNot.setBackground(new Color(0,0,0,0));
			rdNot.setOpaque(false);
			rdNot.setFont(new Font("Arial", Font.PLAIN, 14));
			rdNot.setBounds(119, 203, 98, 23);
			painelPrincipal.add(rdNot);
			
			JLabel lblTipo = new JLabel("Tipo:");
			lblTipo.setForeground(Color.WHITE);
			lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
			lblTipo.setBounds(74, 195, 44, 37);
			painelPrincipal.add(lblTipo);
			

			 rdAnexo = new JRadioButton("Anexo");
			 rdAnexo.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
	                    if(rdAnexo.isSelected()) {
						

						rdNot.setSelected(false);	
						rdAnexo.setSelected(true);
						rdLivre.setSelected(false);
						
						chkAnexarContrato.setVisible(false);
						set_msg_anexo();
						
						
						
					}else {
						rdAnexo.setSelected(true);	

					}
					
			 	}
			 });
			rdAnexo.setOpaque(false);
			rdAnexo.setForeground(Color.WHITE);
			rdAnexo.setFont(new Font("Arial", Font.PLAIN, 14));
			rdAnexo.setBackground(new Color(0, 0, 0, 0));
			rdAnexo.setBounds(219, 203, 72, 23);
			painelPrincipal.add(rdAnexo);
			
			
			 rdLivre = new JRadioButton("Personalizada");
			 rdLivre.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
                        if(rdLivre.isSelected()) {
						

						rdNot.setSelected(false);	
						rdAnexo.setSelected(false);
						rdLivre.setSelected(true);
						
						chkAnexarContrato.setVisible(true);
						set_msg_livre();
						
						
						
					}else {
						rdLivre.setSelected(true);	

					}
			 	}
			 });
			rdLivre.setOpaque(false);
			rdLivre.setForeground(Color.WHITE);
			rdLivre.setFont(new Font("Arial", Font.PLAIN, 14));
			rdLivre.setBackground(new Color(0, 0, 0, 0));
			rdLivre.setBounds(293, 203, 123, 23);
			painelPrincipal.add(rdLivre);
			
			lblNaoAcentue = new JLabel("Não acentue as palavras.");
			lblNaoAcentue.setForeground(Color.ORANGE);
			lblNaoAcentue.setBounds(119, 563, 266, 14);
			painelPrincipal.add(lblNaoAcentue);
			
			
			
			pesquisarContatos(compradores[0].getId());
			  mensagem_anexo = "Ola! \n \nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \n\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_comprador_global + " , assim sendo, envio essa mensagem para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato()+ " ainda encontra na nossa base de dados com a carencia de assinatura" + ".\n \n \nQualquer duvida entrar em contato  conosco! \natenciosamente; \n" + login.getNome() + " " 
			            + login.getSobrenome() + "\nE-mail: " + login.getEmail() + "\n\n LD Armazens Gerais!";	
				 
				  mensagem_notificacao = "Ola! \n \nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \n\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_comprador_global + " , assim sendo, envio essa mensagem   para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato() + " ainda encontra na nossa base de dados com a carencia de assinatura"+ ".\n \n \nQualquer duvida entrar em contato  conosco! \natenciosamente; \n" + login.getNome() + " " 
			            + login.getSobrenome() + "\nE-mail: " + login.getEmail() + "\n\n LD Armazens Gerais!";
			set_msg_notificacao();

		this.setLocationRelativeTo(null);
		this.setUndecorated(true);

		this.setVisible(true);
		
		
		
	}
	
	
	public void set_msg_notificacao() {
		
		textArea.setText(mensagem_notificacao);
		textArea.setEditable(false);
		lblNaoAcentue.setVisible(false);

		
	}
	
	
	public void set_msg_anexo() {
		
			textArea.setText(mensagem_anexo);
			textArea.setEditable(false);
			lblNaoAcentue.setVisible(false);

	}
	
	public void set_msg_livre() {
		textArea.setText("");
		textArea.setEditable(true);
		chkAnexarContrato.setVisible(true);
		chkAnexarContrato.setSelected(false);
		lblNaoAcentue.setVisible(true);

	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	public void enviar_Mensagem() {

        ZapMessenger zap = new ZapMessenger();
        zap.logar();
		Nuvem nuvem = new Nuvem();
		
		nuvem.abrir();
		nuvem.testar();
		nuvem.listar();
		String url = nuvem.getUrlArquivo("/contratos/" + contrato_local.getNome_arquivo());
		System.out.println("link do arquivo para enviar via zap: " + url);
		 String mensagem_enviar = "";
		 
		  if(rdNot.isSelected()) {
			  mensagem_enviar = mensagem_notificacao;
		  }else if(rdAnexo.isSelected()) {
			  mensagem_enviar =  mensagem_anexo;
		  }else if(rdLivre.isSelected()){
			  
			  mensagem_enviar = textArea.getText().toString();
			  mensagem_enviar= Normalizer.normalize(mensagem_enviar, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

			  
		  }		
		
		if(chkOutroContato.isSelected()) {
			//checkBox esta selecionado
			String celular = etNumero.getText();
			String celular_reformado = celular.replaceAll("[^0-9]+", "");
			
			if(celular_reformado.length() == 10) {
				//celular aceito
				 celular_reformado = "+55" + celular_reformado;
					
			      if(rdLivre.isSelected() && chkAnexarContrato.isSelected()) {
			    	  boolean retorno = zap.enviarArquivo(celular_reformado, mensagem_enviar, url);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }else if(rdLivre.isSelected() && !chkAnexarContrato.isSelected()){
			    	  //envia somente a mensagem
			    	  boolean retorno = zap.enviarMensagem(celular_reformado, mensagem_enviar);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }else if(rdNot.isSelected()) {
			    	//envia somente a mensagem
			    	  boolean retorno = zap.enviarMensagem(celular_reformado, mensagem_enviar);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }else if(rdAnexo.isSelected()) {
			    	//envia somente a mensagem
			    	  boolean retorno = zap.enviarArquivo(celular_reformado, mensagem_enviar, url);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }
				
			}else {
				JOptionPane.showMessageDialog(null, "Número de celular incorreto!");
			}
			
		}else {
			//enviar mensagem belo combobox
			
			String celular = cBContato.getSelectedItem().toString().replaceAll("[^0-9]+", "");
			celular = celular.replaceFirst("9", "");
				System.out.println("Celular do combobox selecionado: " + celular);
				//celular aceito
				celular = "+55" + celular;
					
			      if(rdLivre.isSelected() && chkAnexarContrato.isSelected()) {
			    	  boolean retorno = zap.enviarArquivo(celular, mensagem_enviar, url);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }else if(rdLivre.isSelected() && !chkAnexarContrato.isSelected()){
			    	  //envia somente a mensagem
			    	  boolean retorno = zap.enviarMensagem(celular, mensagem_enviar);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }else if(rdNot.isSelected()) {
			    	//envia somente a mensagem
			    	  boolean retorno = zap.enviarMensagem(celular, mensagem_enviar);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }else if(rdAnexo.isSelected()) {
			    	//envia somente a mensagem
			    	  boolean retorno = zap.enviarArquivo(celular, mensagem_enviar, url);
						//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

						if(retorno) {
							JOptionPane.showMessageDialog(null, "Contrato enviado!" );
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Erro, Contrato não enviado!" );
		                      
						}
			      }
		
		}
	}
	
}
