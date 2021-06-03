

package main.java.gui;

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
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
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
import main.java.manipular.PorExtenso;
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
import main.java.relatoria.RelatorioContratoRecebimentoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
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
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Window;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;



public class TelaEnviarMsgEmailDocsGeral extends JFrame {

	JPanel painelPrincipal = new JPanel();
	private CadastroCliente cliente_global;
	JPanelBackground rodape = new JPanelBackground();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entEmailDestino;
	private JCheckBox chkOutroContato, chkAnexarContrato;
	private JComboBox cBContato, cBEmailRemetente;
	private String nome_comprador_global, mensagem_anexo, mensagem_envio_contador, mensagem_notificacao, mensagem_assinatura, mensagem_assunto, mensagem_saudacao;
	private JRadioButton rdLivre;
	private JTextArea textArea, textAreaAssinatura ;
	private TelaEnviarMsgEmailDocsGeral isto;
	private JLabel newll;
	private JLabel lblAssunto_1;
	private JTextField lblAssunto;
	private JLabel lblAssunto_2;
	private JTextField lblSaudacao;
	private JLabel lblAssunto_3;
    private int flag_global;
    private String url_global;
    private String mensagem_envio_documento= "";
    private String mensagem_envio_relatorio= "";
	String nome_vendedores_global = "";
	private JButton btnMsg1;
	private JButton btnMsg2;
	private JButton btnNewButton_2;

	


	public TelaEnviarMsgEmailDocsGeral(int flag_tipo_envio , String  url,Window janela_pai) {
		//setAlwaysOnTop(true);

		this.flag_global = flag_tipo_envio;
		this.url_global = url;
		
		getDadosGlobais();
		
		
		 isto = this;
		
		setResizable(true);
		setTitle("E-Contract - Enviar Documento");

		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1063, 650);
		
		
		
		painelPrincipal.setBackground(new Color(0, 51, 102));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		 
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[168px][6px][348px][][12px][100px][12px][342px]", "[35px][29px][33px][37px][33px][220px][86px][23px][37px]"));
		
		btnNewButton_2 = new JButton("Selecionar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaCliente tela = new TelaCliente(0,37, isto);
				tela.setVisible(true);
				
			}
		});
		btnNewButton_2.setBackground(new Color(51, 0, 153));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelPrincipal.add(btnNewButton_2, "cell 3 2");
		
		JLabel lblNewLabel = new JLabel("Mensagem:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		painelPrincipal.add(lblNewLabel, "cell 0 5,alignx right,growy");
		
		 textArea = new JTextArea();
		 textArea.setFont(new Font("SansSerif", Font.BOLD, 18));
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.setOpaque(false);
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		textArea.setLineWrap(true);
		painelPrincipal.add(textArea, "cell 2 5 6 1,grow");
	
		
      
		JLabel lblContato = new JLabel("Email Destinatario:");
		lblContato.setForeground(Color.WHITE);
		lblContato.setFont(new Font("Arial", Font.BOLD, 14));
		painelPrincipal.add(lblContato, "cell 0 2,alignx right,aligny center");
		
		 cBContato = new JComboBox();
		 cBContato.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelPrincipal.add(cBContato, "cell 2 2,growx,aligny top");
		
		JLabel lblNewLabel_1 = new JLabel("       Enviar documento via E-mail");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBackground(new Color(51, 0, 153));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		painelPrincipal.add(lblNewLabel_1, "cell 0 0 3 1,alignx left,growy");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 0, 153));
		painelPrincipal.add(panel, "cell 5 8 3 1,grow");
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
		painelPrincipal.add(chkOutroContato, "cell 5 2,alignx left,aligny center");
		
		entEmailDestino = new JTextField();
		entEmailDestino.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		entEmailDestino.setEnabled(false);
		entEmailDestino.setEditable(false);
		painelPrincipal.add(entEmailDestino, "cell 7 2,grow");
		entEmailDestino.setColumns(10);
		
		
		
		
		  
			
			 chkAnexarContrato = new JCheckBox("Anexar Contrato na Mensagem?");
			 chkAnexarContrato.setFont(new Font("Arial", Font.PLAIN, 14));
			 chkAnexarContrato.setVisible(false);
			 chkAnexarContrato.setOpaque(false);
			 chkAnexarContrato.setBackground(new Color(0,0,0,0));
			chkAnexarContrato.setForeground(Color.MAGENTA);
			painelPrincipal.add(chkAnexarContrato, "cell 2 7,grow");
			
			JLabel lblTipo = new JLabel("Tipo:");
			lblTipo.setForeground(Color.WHITE);
			lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
			painelPrincipal.add(lblTipo, "cell 2 3,alignx left,growy");
			
			
			 rdLivre = new JRadioButton("Personalizada");
			 rdLivre.setEnabled(false);
			 rdLivre.setSelected(true);
			
			rdLivre.setOpaque(false);
			rdLivre.setForeground(Color.WHITE);
			rdLivre.setFont(new Font("Arial", Font.PLAIN, 14));
			rdLivre.setBackground(new Color(0, 0, 0, 0));
			painelPrincipal.add(rdLivre, "cell 2 3,alignx left,aligny center");
			
			newll = new JLabel("Remetente:");
			newll.setForeground(Color.WHITE);
			newll.setFont(new Font("Arial", Font.BOLD, 14));
			painelPrincipal.add(newll, "cell 0 1,alignx right,aligny center");
			
			
			
			lblAssunto_1 = new JLabel("Assunto:");
			lblAssunto_1.setForeground(Color.WHITE);
			lblAssunto_1.setFont(new Font("Arial", Font.BOLD, 14));
			painelPrincipal.add(lblAssunto_1, "cell 0 4,alignx right,aligny center");
			
			lblAssunto = new JTextField();
			lblAssunto.setText(mensagem_assunto);
			lblAssunto.setFont(new Font("SansSerif", Font.PLAIN, 14));
			lblAssunto.setEnabled(false);
			lblAssunto.setEditable(false);
			lblAssunto.setColumns(10);
			painelPrincipal.add(lblAssunto, "cell 2 4,grow");
			
			lblAssunto_2 = new JLabel("Saudação:");
			lblAssunto_2.setForeground(Color.WHITE);
			lblAssunto_2.setFont(new Font("Arial", Font.BOLD, 14));
			painelPrincipal.add(lblAssunto_2, "cell 5 4,alignx right,aligny center");
			
			lblSaudacao = new JTextField();
			lblSaudacao.setText(mensagem_saudacao);
			lblSaudacao.setFont(new Font("SansSerif", Font.PLAIN, 14));
			lblSaudacao.setEnabled(false);
			lblSaudacao.setEditable(false);
			lblSaudacao.setColumns(10);
			painelPrincipal.add(lblSaudacao, "cell 7 4,grow");
			
			lblAssunto_3 = new JLabel("Assinatura:");
			lblAssunto_3.setForeground(Color.WHITE);
			lblAssunto_3.setFont(new Font("Arial", Font.BOLD, 14));
			painelPrincipal.add(lblAssunto_3, "cell 0 6,alignx right,aligny top");
			
			 textAreaAssinatura = new JTextArea();
			textAreaAssinatura.setOpaque(false);
			textAreaAssinatura.setLineWrap(true);
			textAreaAssinatura.setForeground(Color.WHITE);
			textAreaAssinatura.setEditable(false);
			textAreaAssinatura.setBorder(new LineBorder(new Color(0, 0, 0)));
			textAreaAssinatura.setBackground(new Color(0, 0, 0, 0));
			painelPrincipal.add(textAreaAssinatura, "cell 2 6 6 1,grow");
			
			btnMsg1 = new JButton("Ex msg 1");
			btnMsg1.setEnabled(false);
			btnMsg1.setVisible(false);
			btnMsg1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(mensagem_envio_documento);
				}
			});
			painelPrincipal.add(btnMsg1, "cell 5 3,growx,aligny center");
			
			btnMsg2 = new JButton("Ex msg 2");
			btnMsg2.setVisible(false);
			btnMsg2.setEnabled(false);
			btnMsg2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(mensagem_envio_relatorio);
				}
			});
			painelPrincipal.add(btnMsg2, "flowx,cell 7 3,alignx left,aligny center");
			
			 cBEmailRemetente = new JComboBox();
			
			cBEmailRemetente.setFont(new Font("SansSerif", Font.PLAIN, 14));
			painelPrincipal.add(cBEmailRemetente, "cell 2 1 2 1,growx,aligny top");
			
			if(checkEmail(login.getEmail())) {
				cBEmailRemetente.addItem(login.getEmail());
			}
			try {
			if(checkEmail(login.getEmail2())) {
				cBEmailRemetente.addItem(login.getEmail2());
			}
			}catch(Exception e) {
				
			}
			
			
				  mensagem_assinatura = login.getNome() + " " + login.getSobrenome() +"," + "\nContato: " + login.getCelular() + "\n" + "LD Armazens Gerais";
			
				  
				  mensagem_assunto = "Envio de Documento como Solicitado";
				  mensagem_saudacao = "Olá! Como vai? Espero que bem!\n";

					mensagem_envio_documento = "Anexado a esta mensagem esta o documento como solicitado";
                  

				  
				  set_msg_notificacao();

				  if(flag_global == 2) {
					//enviar documento
					
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
			   String remetente = "";
		        if(cBEmailRemetente.getSelectedItem().toString().equals(login.getEmail())) {
			        mail.logar( login.getEmail(), login.getSenhaEmail().trim());
		        	remetente = login.getEmail();

		        }else if(cBEmailRemetente.getSelectedItem().toString().equals(login.getEmail2())) {
		        	remetente = login.getEmail2();
		        	mail.logar( login.getEmail2(), login.getSenhaEmail2().trim());

		        }
		        
		        
		        
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
		        
		       if(rdLivre.isSelected()) {
		        	//mensagem editavel
		        	mensagem = "\n\n" + textArea.getText() + "\n\n";

		        	if(chkAnexarContrato.isSelected()) {
		        	    if(checkEmail(destinatario)) {
		        		//incluir anexo
		        		  ArrayList<File> arquivos = new ArrayList<>();

		        		if(flag_global == 2) {
				     		arquivos.add(new File(url_global));

		        		}
				     		
				     		
		                  boolean enviado = mail.enviarAnexo(remetente, destinatario, assunto, saudacao, mensagem, assinatura, arquivos);
		       		    if(enviado == true) {
		       		    	JOptionPane.showMessageDialog(isto, "E-mail enviado");
		       		    	isto.dispose();

		       		    }else {
		       		    	JOptionPane.showMessageDialog(isto, "E-mail não enviado\nConsulte o administrador");
		       		    }
		               
		        	    } else {
	            	    	JOptionPane.showMessageDialog(isto, "Destinatario Invalido!");

	                    }
		        		
		               }else {
		        		//nao incluir anexo
		        	    if(checkEmail(destinatario)) {
		                    //  public boolean enviar(String remetente, String destinatario, String assunto, String saudacao, String mensagem, String assinatura) {

		                    boolean enviado = mail.enviar(remetente, destinatario, assunto,saudacao, mensagem, assinatura);
		            		    if(enviado == true) {
		            		    	JOptionPane.showMessageDialog(isto, "E-mail enviado");
		            		    	isto.dispose();

		            		    }else {
		            		    	JOptionPane.showMessageDialog(isto, "E-mail não enviado\nConsulte o administrador");
		            		    }
		                    }else {
		            	    	JOptionPane.showMessageDialog(isto, "Destinatario Invalido!");

		                    }
		        		
		        	}
		        }
		      
    
		
	}
	
	public boolean checkEmail(String email) {
		return !email.equals("") && !email.equals(" ") && email.length() > 5 && email.contains("@");
	}
	
	
	
	public void setContatosContadores() {
		
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();		
		ArrayList<Contato> lista_contatos =  gerenciar.getContatosContadores();
		
		cBContato.removeAllItems();
		
		for(Contato contato : lista_contatos) {
			if(contato != null) {
				String celular = contato.getCelular();
			   if(celular != null && celular.length() == 11) {
				    //celular aceito
				   System.out.println("Celular:" + celular);
				   
				   
			   }
			   cBContato.addItem(contato.getE_mail());

			}
		}
		
	}

	
	public void setCliente(CadastroCliente cli) {
		this.cliente_global = cli;
		pesquisarContatos(cli.getId());
	}
	

	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	public void pesquisarContatos(int id_cliente) {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<Contato> lista_contatos =  gerenciar.getContatos(id_cliente);
		
		cBContato.removeAllItems();
		
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
}
