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
import manipular.Email;
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
import java.io.File;

import javax.swing.JRadioButton;



public class TelaEnviarMsgMail extends JDialog {

	JPanel painelPrincipal = new JPanel();
	JPanelBackground rodape = new JPanelBackground();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entEmailDestino;
	private JCheckBox chkOutroContato, chkAnexarContrato;
	private JComboBox cBContato;
	private String nome_comprador_global, mensagem_anexo, mensagem_notificacao, mensagem_assinatura, mensagem_assunto, mensagem_saudacao;
	private JRadioButton rdLivre, rdAnexo, rdNot;
	private CadastroContrato contrato_local;
	private JTextArea textArea, textAreaAssinatura ;
	private TelaEnviarMsgMail isto;
	private JLabel newll;
	private JLabel lblRemetente;
	private String remetente_global;
	private JLabel lblAssunto_1;
	private JTextField lblAssunto;
	private JLabel lblAssunto_2;
	private JTextField lblSaudacao;
	private JLabel lblAssunto_3;
    private int flag_global;
    private File documento_global;
    private String mensagem_envio_documento= "";
    private String mensagem_envio_relatorio= "";
	String nome_vendedores_global = "";
	private JButton btnMsg1;
	private JButton btnMsg2;

	
	public void pesquisarContatos(int id_cliente) {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<Contato> lista_contatos =  gerenciar.getContatos(id_cliente);
		
		for(Contato contato : lista_contatos) {
			if(contato != null) {
				String celular = contato.getCelular();
			   if(celular != null && celular.length() == 11) {
				    //celular aceito
				   System.out.println("Celular:" + celular);
				   
				   cBContato.addItem(contato.getE_mail());
				   
			   }
			}
		}
		
	}


	public TelaEnviarMsgMail(int flag_tipo_envio, CadastroContrato contrato, File documento,JFrame janela_pai) {
		//setAlwaysOnTop(true);

		this.flag_global = flag_tipo_envio;
		this.documento_global = documento;
		getDadosGlobais();
		setModal(true);
		
		contrato_local = contrato;
        CadastroCliente compradores[] = contrato.getCompradores();
		
		
		if(compradores[0].getTipo_pessoa() == 0) {
			//pessoa fisica
			nome_comprador_global = compradores[0].getNome_empresarial();

		}else {
			//pessoa juridica
			nome_comprador_global = compradores[0].getNome_fantaia();

		}
		
		 CadastroCliente vendedores[] = contrato.getVendedores();
			
			
			if(vendedores[0].getTipo_pessoa() == 0) {
				//pessoa fisica
				nome_vendedores_global = vendedores[0].getNome_empresarial();

			}else {
				//pessoa juridica
				nome_vendedores_global = vendedores[0].getNome_fantaia();

			}
			
			if(vendedores[1] != null) {
				if(vendedores[1].getTipo_pessoa() == 0) {
					//pessoa fisica
					nome_vendedores_global = nome_vendedores_global + " | " + vendedores[1].getNome_empresarial();

				}else {
					//pessoa juridica
					nome_vendedores_global = nome_vendedores_global + " | " + vendedores[1].getNome_fantaia();

				}
			}
			
			
			
		
		getDadosGlobais();
		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Enviar Contrato");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1063, 650);
		
		
		
		painelPrincipal.setBackground(new Color(0, 51, 102));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		 
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mensagem:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(73, 232, 89, 199);
		painelPrincipal.add(lblNewLabel);
		
		 textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(new Color(0,0,0,0));
		textArea.setOpaque(false);
		textArea.setBounds(174, 232, 814, 220);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		textArea.setLineWrap(true);
		painelPrincipal.add(textArea);
	
		
      
		JLabel lblContato = new JLabel("Email Destinatario:");
		lblContato.setForeground(Color.WHITE);
		lblContato.setFont(new Font("Arial", Font.BOLD, 14));
		lblContato.setBounds(38, 101, 130, 17);
		painelPrincipal.add(lblContato);
		
		 cBContato = new JComboBox();
		 cBContato.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cBContato.setBounds(180, 94, 342, 29);
		painelPrincipal.add(cBContato);
		
		JLabel lblNewLabel_1 = new JLabel("       Enviar contrato via gmail");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBackground(new Color(51, 0, 153));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(0, 11, 259, 35);
		painelPrincipal.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(595, 606, 393, 37);
		panel.setBackground(new Color(51, 0, 153));
		painelPrincipal.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isto.dispose();
			}
		});
		btnNewButton.setBounds(333, 4, 54, 30);
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
					
					
					 enviar_email();
					
                  
			        }
				else
				{
					
				}
				
			 	
				
				
				
				
			
			}
		});
		btnNewButton_1.setBounds(261, 4, 60, 30);
		panel.add(btnNewButton_1);
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.setBackground(new Color(0, 102, 102));
		
		 chkOutroContato = new JCheckBox("Outro Email:");
		chkOutroContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!chkOutroContato.isSelected()) {
					//esta selecionado, desmarque

					chkOutroContato.setSelected(false);
					
					entEmailDestino.setEditable(false);
					entEmailDestino.setEnabled(false);
					cBContato.setEnabled(true);
					
					
				}else {
					//nao esta selecionado
					
					chkOutroContato.setSelected(true);
					
					entEmailDestino.setEditable(true);
					entEmailDestino.setEnabled(true);
					cBContato.setEnabled(false);
					
				}
				
			}
		});
		chkOutroContato.setOpaque(false);
		chkOutroContato.setForeground(Color.WHITE);
		chkOutroContato.setFont(new Font("Arial", Font.PLAIN, 14));
		chkOutroContato.setBounds(534, 102, 100, 18);
		painelPrincipal.add(chkOutroContato);
		
		entEmailDestino = new JTextField();
		entEmailDestino.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		entEmailDestino.setEnabled(false);
		entEmailDestino.setEditable(false);
		entEmailDestino.setBounds(646, 94, 342, 33);
		painelPrincipal.add(entEmailDestino);
		entEmailDestino.setColumns(10);
		
		
		
		
		  
			
			 chkAnexarContrato = new JCheckBox("Anexar Contrato na Mensagem?");
			 chkAnexarContrato.setFont(new Font("Arial", Font.PLAIN, 14));
			 chkAnexarContrato.setVisible(false);
			 chkAnexarContrato.setOpaque(false);
			 chkAnexarContrato.setBackground(new Color(0,0,0,0));
			chkAnexarContrato.setForeground(Color.MAGENTA);
			chkAnexarContrato.setBounds(174, 577, 297, 23);
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
						
						lblAssunto.setEnabled(false);
						lblAssunto.setEditable(false);
						lblAssunto.setText("Notificação de Contrato");
						btnMsg1.setEnabled(false);
						btnMsg1.setVisible(false);
						
						btnMsg2.setEnabled(false);
						btnMsg2.setVisible(false);
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
			rdNot.setBounds(219, 137, 98, 23);
			painelPrincipal.add(rdNot);
			
			JLabel lblTipo = new JLabel("Tipo:");
			lblTipo.setForeground(Color.WHITE);
			lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
			lblTipo.setBounds(174, 130, 44, 37);
			painelPrincipal.add(lblTipo);
			

			 rdAnexo = new JRadioButton("Anexo");
			 rdAnexo.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
	                    if(rdAnexo.isSelected()) {
						

						rdNot.setSelected(false);	
						rdAnexo.setSelected(true);
						rdLivre.setSelected(false);
						
						chkAnexarContrato.setVisible(false);
						lblAssunto.setEnabled(false);
						lblAssunto.setEditable(false);
						lblAssunto.setText("Notificação de Contrato");
						btnMsg1.setEnabled(false);
						btnMsg1.setVisible(false);
						
						btnMsg2.setEnabled(false);
						btnMsg2.setVisible(false);
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
			rdAnexo.setBounds(319, 138, 72, 23);
			painelPrincipal.add(rdAnexo);
			
			
			 rdLivre = new JRadioButton("Personalizada");
			 rdLivre.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
                        if(rdLivre.isSelected()) {
						

						rdNot.setSelected(false);	
						rdAnexo.setSelected(false);
						rdLivre.setSelected(true);
						
						lblAssunto.setEnabled(true);
						lblAssunto.setEditable(true);
						lblAssunto.setText("");
						chkAnexarContrato.setVisible(true);
						set_msg_livre();
						btnMsg1.setEnabled(true);
						btnMsg1.setVisible(true);
						
						btnMsg2.setEnabled(true);
						btnMsg2.setVisible(true);
						
					}else {
						rdLivre.setSelected(true);	

						btnMsg1.setEnabled(false);
						btnMsg1.setVisible(false);
						
						btnMsg2.setEnabled(false);
						btnMsg2.setVisible(false);
					}
			 	}
			 });
			rdLivre.setOpaque(false);
			rdLivre.setForeground(Color.WHITE);
			rdLivre.setFont(new Font("Arial", Font.PLAIN, 14));
			rdLivre.setBackground(new Color(0, 0, 0, 0));
			rdLivre.setBounds(393, 138, 123, 23);
			painelPrincipal.add(rdLivre);
			
			newll = new JLabel("Remetente:");
			newll.setForeground(Color.WHITE);
			newll.setFont(new Font("Arial", Font.BOLD, 14));
			newll.setBounds(87, 58, 81, 17);
			painelPrincipal.add(newll);
			
			
			lblRemetente = new JLabel();
			if(login.getEmail() != null) {
				lblRemetente.setText(login.getEmail());
				remetente_global = login.getEmail();
			}
			lblRemetente.setFont(new Font("SansSerif", Font.BOLD, 14));
			lblRemetente.setForeground(Color.WHITE);
			lblRemetente.setBounds(178, 58, 212, 19);
			painelPrincipal.add(lblRemetente);
			
			lblAssunto_1 = new JLabel("Assunto:");
			lblAssunto_1.setForeground(Color.WHITE);
			lblAssunto_1.setFont(new Font("Arial", Font.BOLD, 14));
			lblAssunto_1.setBounds(101, 185, 61, 17);
			painelPrincipal.add(lblAssunto_1);
			
			lblAssunto = new JTextField();
			lblAssunto.setText(mensagem_assunto);
			lblAssunto.setFont(new Font("SansSerif", Font.PLAIN, 14));
			lblAssunto.setEnabled(false);
			lblAssunto.setEditable(false);
			lblAssunto.setColumns(10);
			lblAssunto.setBounds(174, 176, 342, 33);
			painelPrincipal.add(lblAssunto);
			
			lblAssunto_2 = new JLabel("Saudação:");
			lblAssunto_2.setForeground(Color.WHITE);
			lblAssunto_2.setFont(new Font("Arial", Font.BOLD, 14));
			lblAssunto_2.setBounds(562, 185, 72, 17);
			painelPrincipal.add(lblAssunto_2);
			
			lblSaudacao = new JTextField();
			lblSaudacao.setText(mensagem_saudacao);
			lblSaudacao.setFont(new Font("SansSerif", Font.PLAIN, 14));
			lblSaudacao.setEnabled(false);
			lblSaudacao.setEditable(false);
			lblSaudacao.setColumns(10);
			lblSaudacao.setBounds(646, 176, 342, 33);
			painelPrincipal.add(lblSaudacao);
			
			lblAssunto_3 = new JLabel("Assinatura:");
			lblAssunto_3.setForeground(Color.WHITE);
			lblAssunto_3.setFont(new Font("Arial", Font.BOLD, 14));
			lblAssunto_3.setBounds(84, 468, 78, 17);
			painelPrincipal.add(lblAssunto_3);
			
			 textAreaAssinatura = new JTextArea();
			textAreaAssinatura.setOpaque(false);
			textAreaAssinatura.setLineWrap(true);
			textAreaAssinatura.setForeground(Color.WHITE);
			textAreaAssinatura.setEditable(false);
			textAreaAssinatura.setBorder(new LineBorder(new Color(0, 0, 0)));
			textAreaAssinatura.setBackground(new Color(0, 0, 0, 0));
			textAreaAssinatura.setBounds(174, 464, 814, 86);
			painelPrincipal.add(textAreaAssinatura);
			
			btnMsg1 = new JButton("Ex msg 1");
			btnMsg1.setEnabled(false);
			btnMsg1.setVisible(false);
			btnMsg1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(mensagem_envio_documento);
				}
			});
			btnMsg1.setBounds(544, 134, 90, 28);
			painelPrincipal.add(btnMsg1);
			
			btnMsg2 = new JButton("Ex msg 2");
			btnMsg2.setVisible(false);
			btnMsg2.setEnabled(false);
			btnMsg2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(mensagem_envio_relatorio);
				}
			});
			btnMsg2.setBounds(656, 134, 90, 28);
			painelPrincipal.add(btnMsg2);
			
			
			
			
			pesquisarContatos(compradores[0].getId());
			  mensagem_anexo = "\n\nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \n\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_comprador_global.toUpperCase() + " , assim sendo, envio essa mensagem para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato()+ " ainda encontra na nossa base de dados com a carencia de assinatura" + ".\n \n \nQualquer duvida entrar em contato  conosco! \natenciosamente; \n\n";	
				 
				  mensagem_notificacao = "\n\nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \n\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_comprador_global.toUpperCase() + " , assim sendo, envio essa mensagem   para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato() + " ainda encontra na nossa base de dados com a carencia de assinatura"+ ".\n \n \nQualquer duvida entrar em contato  conosco! \natenciosamente; \n\n";
			
				  mensagem_assinatura = login.getNome() + " " + login.getSobrenome() +"," + "\nContato: " + login.getCelular() + "\n" + "LD Armazens Gerais";
			
				  
				  mensagem_assunto = "Notificação de Contrato";
				  mensagem_saudacao = "Olá! Como vai? Espero que bem!\n";

					mensagem_envio_documento = "Anexado a esta mensagem esta o documento como solicitado";
                    mensagem_envio_relatorio = "Em anexo envio a esta mensagem envio o relatorio referente ao contrato " + contrato_local.getCodigo() + " entre o comprador " + nome_comprador_global + " e o(s) vendedor(es) " + nome_vendedores_global + " na quantidade de " + contrato_local.getQuantidade() + " " 
    			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
    			    		+ contrato_local.getData_contrato();
    				 
				  
				  set_msg_notificacao();

				  if(flag_global == 2) {
					//enviar documento
						rdNot.setEnabled(false);
						rdNot.setSelected(false);
						rdAnexo.setEnabled(false);
						rdAnexo.setSelected(false);

						rdLivre.setSelected(true);
						
						lblAssunto.setEditable(true);
						lblAssunto.setEnabled(true);
						lblAssunto.setText("Envio de Documento como solicitado");

						lblSaudacao.setEditable(true);
						lblSaudacao.setEnabled(true);
						lblSaudacao.setText(mensagem_saudacao);
						
						textAreaAssinatura.setEditable(true);
						textAreaAssinatura.setEnabled(true);
						textAreaAssinatura.setText(mensagem_assinatura);
						
						textArea.setEditable(true);
						textArea.setEnabled(true);
						textArea.setText("");
						
						chkAnexarContrato.setVisible(true);
						chkAnexarContrato.setEnabled(false);
						chkAnexarContrato.setSelected(true);
						
						btnMsg1.setEnabled(true);
						btnMsg1.setVisible(true);
						
						btnMsg2.setEnabled(true);
						btnMsg2.setVisible(true);
				  }
				  
		this.setLocationRelativeTo(janela_pai);
		this.setUndecorated(true);

		//this.setVisible(true);
		
		
		
		
	}
	
	
	public void set_msg_notificacao() {
		
		textArea.setText(mensagem_notificacao);
		textArea.setEditable(false);
		textAreaAssinatura.setText(mensagem_assinatura);
		textAreaAssinatura.setEditable(false);

		lblSaudacao.setEditable(false);
		lblSaudacao.setText(mensagem_saudacao);
		
		lblAssunto.setEditable(false);
		lblAssunto.setText(mensagem_assunto);

	}
	public void set_msg_anexo() {
		
		textArea.setText(mensagem_anexo);
		textArea.setEditable(false);

		textAreaAssinatura.setText(mensagem_assinatura);
		textAreaAssinatura.setEditable(false);
		
		lblSaudacao.setEditable(false);
		lblSaudacao.setText(mensagem_saudacao);
		
		lblAssunto.setEditable(false);
		lblAssunto.setText(mensagem_assunto);
}

public void set_msg_livre() {
	
	textArea.setEditable(true);
	chkAnexarContrato.setVisible(true);
	chkAnexarContrato.setSelected(false);
	
	textAreaAssinatura.setEditable(true);

	lblSaudacao.setEditable(true);
	lblSaudacao.setEnabled(true);
	lblSaudacao.setText(mensagem_saudacao);

	lblAssunto.setEditable(true);
	lblAssunto.setText(mensagem_assunto);



}

	
	public void enviar_email() {
	
		
			//enviar contrato fixo
			 Email mail = new Email();
		        mail.logar(remetente_global.trim(), login.getSenhaEmail().trim());
		        
		        String remetente = remetente_global.trim();
		        
		        String destinatario = "";
		       if( chkOutroContato.isSelected()) {
		    	   destinatario = entEmailDestino.getText();
		    	   
		       }else {
		    	   //pega o email fixo
		    	   destinatario = cBContato.getSelectedItem().toString();
		    	   
		       }
		        
		       String assunto = lblAssunto.getText() + "\n\n";
		        String saudacao = lblSaudacao.getText() + "\n\n";
		        String assinatura ="\n\n" + textAreaAssinatura.getText();
		        
		        String mensagem = "";
		        
		        if(rdNot.isSelected()) {
		        	//mensagem fixa sem anexo
		        	
		        	mensagem = textArea.getText();
		        	
		            if(checkEmail(destinatario)) {
		                //  public boolean enviar(String remetente, String destinatario, String assunto, String saudacao, String mensagem, String assinatura) {

		                boolean enviado = mail.enviar(remetente_global, destinatario, assunto,saudacao, mensagem, assinatura);
		        		    if(enviado == true) {
		        		    	JOptionPane.showMessageDialog(null, "E-mail enviado");
		        		    	isto.dispose();

		        		    }else {
		        		    	JOptionPane.showMessageDialog(null, "E-mail não enviado\nConsulte o administrador");
		        		    }
		                }else {
		        	    	JOptionPane.showMessageDialog(null, "Destinatario Invalido!");

		                }
		        	
		        }else if(rdAnexo.isSelected()) {
		        	//mensagem fixa com anexo
		        	// enviarAnexo(String remetente, String destinatario, String assunto, String saudacao, String mensagem, String assinatura, ArrayList<File> anexos) {
		             ArrayList<File> arquivos = new ArrayList<>();
		             String unidade_base_dados = configs_globais.getServidorUnidade();

		     		String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_arquivo();
		     		File contrato = new File(caminho_completo);
		     		arquivos.add(contrato);
		             mensagem = textArea.getText();
		        	
		            if(checkEmail(destinatario)) {
		                
		                boolean enviado = mail.enviarAnexo(remetente_global, destinatario, assunto, saudacao, mensagem, assinatura, arquivos);
		        		    if(enviado == true) {
		        		    	JOptionPane.showMessageDialog(null, "E-mail enviado");
		        		    	isto.dispose();

		        		    }else {
		        		    	JOptionPane.showMessageDialog(null, "E-mail não enviado\nConsulte o administrador");
		        		    }
		                }else {
		        	    	JOptionPane.showMessageDialog(null, "Destinatario Invalido!");

		                }
		        	
		        }else if(rdLivre.isSelected()) {
		        	//mensagem editavel
		        	mensagem = "\n\n" + textArea.getText() + "\n\n";

		        	if(chkAnexarContrato.isSelected()) {
		        	    if(checkEmail(destinatario)) {
		        		//incluir anexo
		        		  ArrayList<File> arquivos = new ArrayList<>();

		        		if(flag_global == 1) {
		                  String unidade_base_dados = configs_globais.getServidorUnidade();
		                  String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_arquivo();
				     		File contrato = new File(caminho_completo);
				     		arquivos.add(contrato);
		        		}else if(flag_global == 2) {
				     		arquivos.add(documento_global);

		        		}
				     		
				     		
		                  boolean enviado = mail.enviarAnexo(remetente_global, destinatario, assunto, saudacao, mensagem, assinatura, arquivos);
		       		    if(enviado == true) {
		       		    	JOptionPane.showMessageDialog(null, "E-mail enviado");
		       		    	isto.dispose();

		       		    }else {
		       		    	JOptionPane.showMessageDialog(null, "E-mail não enviado\nConsulte o administrador");
		       		    }
		               
		        	    } else {
	            	    	JOptionPane.showMessageDialog(null, "Destinatario Invalido!");

	                    }
		        		
		               }else {
		        		//nao incluir anexo
		        	    if(checkEmail(destinatario)) {
		                    //  public boolean enviar(String remetente, String destinatario, String assunto, String saudacao, String mensagem, String assinatura) {

		                    boolean enviado = mail.enviar(remetente_global, destinatario, assunto,saudacao, mensagem, assinatura);
		            		    if(enviado == true) {
		            		    	JOptionPane.showMessageDialog(null, "E-mail enviado");
		            		    	isto.dispose();

		            		    }else {
		            		    	JOptionPane.showMessageDialog(null, "E-mail não enviado\nConsulte o administrador");
		            		    }
		                    }else {
		            	    	JOptionPane.showMessageDialog(null, "Destinatario Invalido!");

		                    }
		        		
		        	}
		        }
		      
    
		
	}
	
	public boolean checkEmail(String email) {
		return !email.equals("") && !email.equals(" ") && email.length() > 5 && email.contains("@");
	}
	

	
	
	

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
}
