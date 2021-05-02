

package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

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
import main.java.classesExtras.RenderizadorContato;
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
import main.java.manipular.Nuvem;
import main.java.manipular.PorExtenso;
import main.java.manipular.Whatsapp;
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
import main.java.conexaoBanco.GerenciarBancoNotas;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBLocalRetiradaPersonalizado;
import main.java.classesExtras.CBLocalRetiradaRenderPersonalizado;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.classesExtras.ComboBoxContato;
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



public class TelaEnviarMsgWhatsappDocs extends JDialog {

	JPanel painelPrincipal = new JPanel();
	JPanelBackground rodape = new JPanelBackground();
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField etNumero;
	private JCheckBox chkOutroContato;
	private JComboBox cBContato;
	private JLabel lblNaoinclua;
	private String mensagem_notificacao_global, mensagem_anexo_global, mensagem_anexo_comprador, mensagem_notificacao_comprador, mensagem_anexo_vendedor1, mensagem_notificacao_vendedor1,mensagem_anexo_vendedor2, mensagem_notificacao_vendedor2, nome_vendedores_global,nome_compradores_global;
	private CadastroContrato contrato_local;
	private JTextArea textArea ;
	private TelaEnviarMsgWhatsappDocs isto;
	private JLabel lblNaoAcentue;
	private ArrayList<Contato> todos_contatos_comprador = new ArrayList<>();
	private ArrayList<Contato> todos_contatos_vendedor1 = new ArrayList<>();
	private ArrayList<Contato> todos_contatos_vendedor2 = new ArrayList<>();

	private JCheckBox chckbxContatosComprador, chckbxContatosVendedor1, chckbxContatosVendedor2; 

	private ComboBoxContato modelContato = new ComboBoxContato();
	private RenderizadorContato renderContato ;
	
	String mensagem_envio_documento = "";
	String mensagem_envio_relatorio = "";
	public void pesquisarContatos(int flag, int id_cliente) {
		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ArrayList<Contato> lista_contatos =  gerenciar.getContatos(id_cliente);
		
		for(Contato contato : lista_contatos) {
			if(contato != null) {
				String celular = contato.getCelular();
			   if(celular != null && celular.length() == 11) {
				    //celular aceito
				   System.out.println("Celular:" + celular);
                    if(flag == 1) {
                    	//adiciona os contatos ao comprador
                    	todos_contatos_comprador.add(contato);
                    }else if(flag == 2) {
                    	todos_contatos_vendedor1.add(contato);

                    }
                    else if(flag == 3) {
                    	todos_contatos_vendedor2.add(contato);

                    }
				 //  cBContato.addItem(contato.getCargo() + " " + celular);
				   
			   }
			}
		}
		
	}
	 CadastroCliente compradores[] = null;
     CadastroCliente vendedores[] = null;
		
		
		
		
	
	public TelaEnviarMsgWhatsappDocs(CadastroContrato contrato, File doc, Window janela_pai) {
		//setAlwaysOnTop(true);

		
		contrato_local = contrato;
		
		 if(contrato_local != null) {
			
			 compradores = contrato_local.getCompradores();
			 vendedores = contrato_local.getVendedores();
				System.out.println("Nome do comprador: " + compradores[0].getNome_empresarial() + "outro nome: " + compradores[0].getNome_fantaia());
				
				
				if(compradores[0].getTipo_pessoa() == 0) {
					//pessoa fisica
					nome_compradores_global = compradores[0].getNome_empresarial();

				}else {
					//pessoa juridica
					nome_compradores_global = compradores[0].getNome_fantaia();

				}
				
				if(compradores[1] != null) {
				if(compradores[1].getTipo_pessoa() == 0) {
					//pessoa fisica
					nome_compradores_global = compradores[0].getNome_empresarial();

				}else {
					//pessoa juridica
					nome_compradores_global = compradores[0].getNome_fantaia();

				}
				}
				
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
					nome_vendedores_global = vendedores[1].getNome_empresarial();

				}else {
					//pessoa juridica
					nome_vendedores_global = vendedores[1].getNome_fantaia();

				}
				}
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
		
		renderContato = new RenderizadorContato();
		cBContato = new JComboBox();
		cBContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(chckbxContatosComprador.isSelected()) {
					//comprador selecionado
					
					
				}else if(chckbxContatosVendedor1.isSelected()) {
					
				}else if(chckbxContatosVendedor2.isSelected()) {
					
				}
				
			}
		});
		cBContato.setModel(modelContato);
		cBContato.setRenderer(renderContato);
				cBContato.setBounds(119, 93, 297, 23);
		painelPrincipal.add(cBContato);
		
		JLabel lblNewLabel_1 = new JLabel("       Enviar documento via whatsapp");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBackground(new Color(51, 0, 153));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(0, 11, 259, 35);
		painelPrincipal.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBounds(87, 626, 341, 37);
		panel.setBackground(new Color(51, 0, 153));
		painelPrincipal.add(panel);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isto.dispose();
			}
		});
		
		JButton btnNewButton_1 = new JButton("Enviar");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, 
			            "Enviar?", "Deseja enviar a mensagem?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					enviar_Mensagem(doc.getAbsolutePath());

			        }
				else
				{
					
				}
				
			 	
				
				
				
				
			
			}
		});
		panel.setLayout(new MigLayout("", "[grow][grow]", "[23px]"));
		panel.add(btnNewButton_1, "cell 0 0,grow");
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.setBackground(new Color(0, 102, 102));
		panel.add(btnNewButton, "cell 1 0,grow");
		btnNewButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton.setOpaque(false);
		btnNewButton.setBackground(new Color(51, 0, 102));
		
		 chkOutroContato = new JCheckBox("Outro Contato:");
		chkOutroContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
					
					chamar_outro_contato();
				
				
				
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
			
				if(texto.length()==6 && evt.getKeyChar() != '\b'){
					etNumero.setText(etNumero.getText().concat(" "));
				}
				
				if(texto.length()==11 && evt.getKeyChar() != '\b'){
					etNumero.setText(etNumero.getText().concat("-"));
				}
				
				if(etNumero.getText().length()>=16){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					etNumero.setText(etNumero.getText().substring(0,16));
				}
			
			}
			}
		});
		etNumero.setEnabled(false);
		etNumero.setEditable(false);
		etNumero.setBounds(173, 127, 243, 33);
		painelPrincipal.add(etNumero);
		etNumero.setColumns(10);
		
		 lblNaoinclua = new JLabel("Formato: (38) 9 XXXX-XXXX");
		 lblNaoinclua.setVisible(false);
		lblNaoinclua.setForeground(Color.ORANGE);
		lblNaoinclua.setBounds(173, 171, 243, 14);
		painelPrincipal.add(lblNaoinclua);
			
			JLabel lblTipo = new JLabel("Tipo:");
			lblTipo.setForeground(Color.WHITE);
			lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
			lblTipo.setBounds(74, 195, 44, 37);
			painelPrincipal.add(lblTipo);
			
			lblNaoAcentue = new JLabel("Não acentue as palavras.");
			lblNaoAcentue.setForeground(Color.ORANGE);
			lblNaoAcentue.setBounds(119, 563, 266, 14);
			painelPrincipal.add(lblNaoAcentue);
			
			 chckbxContatosComprador = new JCheckBox("Comprador");
			 chckbxContatosComprador.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		if(vendedores != null)
			 	 pesquisar_comprador(vendedores);
			 		 
			 	}
			 	
			 });
			chckbxContatosComprador.setBounds(119, 63, 97, 23);
			
			
			
			painelPrincipal.add(chckbxContatosComprador);
			
			 chckbxContatosVendedor1 = new JCheckBox("Vendedor1");
			 chckbxContatosVendedor1.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		if(vendedores != null)
			 		pesquisar_vendedor1(vendedores);
			 	}
			 });
			chckbxContatosVendedor1.setBounds(231, 63, 97, 23);
			painelPrincipal.add(chckbxContatosVendedor1);
			
			chckbxContatosVendedor2 = new JCheckBox("Vendedor2");
			chckbxContatosVendedor2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				      pesquisar_vendedor2(vendedores);
				}
			});
			chckbxContatosVendedor2.setBounds(337, 63, 97, 23);
			painelPrincipal.add(chckbxContatosVendedor2);
			
			JLabel lblDestinatrio = new JLabel("Destinatário:");
			lblDestinatrio.setForeground(Color.WHITE);
			lblDestinatrio.setFont(new Font("Arial", Font.BOLD, 14));
			lblDestinatrio.setBounds(20, 65, 98, 17);
			painelPrincipal.add(lblDestinatrio);
			
			JButton btnNewButton_2 = new JButton("Msg1");
			btnNewButton_2.setBackground(new Color(0, 0, 0));
			btnNewButton_2.setForeground(Color.WHITE);
			btnNewButton_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(mensagem_envio_relatorio);
				}
			});
			btnNewButton_2.setBounds(119, 199, 89, 23);
			painelPrincipal.add(btnNewButton_2);
			
			JButton btnNewButton_2_1 = new JButton("Msg2");
			btnNewButton_2_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textArea.setText(mensagem_envio_relatorio);

				}
			});
			btnNewButton_2_1.setForeground(Color.WHITE);
			btnNewButton_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
			btnNewButton_2_1.setBackground(Color.BLACK);
			btnNewButton_2_1.setBounds(228, 198, 89, 23);
			painelPrincipal.add(btnNewButton_2_1);
			
			
			if(compradores != null)
			pesquisarContatos(1, compradores[0].getId());
			if(vendedores != null)
			pesquisarContatos(2, vendedores[0].getId());

			if(vendedores != null) {
			if(vendedores[1] != null) {
				pesquisarContatos(3, vendedores[1].getId());

			}
			}
			
				
				  
				if(todos_contatos_comprador.size() > 0) {
					//comprador possui contatos
					pesquisar_comprador(vendedores);
					cBContato.setSelectedIndex(0);
					  set_msg_notificacao(1);

					
				}else if(todos_contatos_vendedor1.size() > 0) {
					//vendedor1 possui contatos
					pesquisar_vendedor1(vendedores);
					cBContato.setSelectedIndex(0);
					  set_msg_notificacao(2);

				}else if(todos_contatos_vendedor2.size() > 0) {
					//vendedor2 possui contatos
					pesquisar_vendedor2(vendedores);
					cBContato.setSelectedIndex(0);
					  set_msg_notificacao(3);
				}else {
					//nao ha contato
					chkOutroContato.setSelected(true);
					
				}
                  
				  
				mensagem_envio_documento = "Anexado a esta mensagem esta o documento como solicitado";
               
				if(vendedores != null && compradores != null) {
				mensagem_envio_relatorio = "Em anexo a esta mensagem envio o relatorio referente ao contrato " + contrato_local.getCodigo() + " entre o comprador " + nome_compradores_global + " e o(s) vendedor(es) " + nome_vendedores_global + " na quantidade de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato();  
				}
				  
		this.setLocationRelativeTo(janela_pai);
		this.setUndecorated(true);
		if(vendedores != null) {
		if(vendedores[1] != null) {
			chckbxContatosVendedor2.setEnabled(true);
			chckbxContatosVendedor2.setVisible(true);
		
		}else {
			chckbxContatosVendedor2.setEnabled(false);
			chckbxContatosVendedor2.setVisible(false);

		}
		}
		this.setVisible(true);
		
		
		
	}
	
	
	public void set_msg_notificacao(int flag_cliente) {
		String mensagem = "";
		
		if(flag_cliente == 1) {
			mensagem = "Ola! \\n \\nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \\n\\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_compradores_global + " , assim sendo, envio essa mensagem para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato()+ " ainda encontra na nossa base de dados com a carencia de assinatura" + ".\\n \\n \\nQualquer duvida entrar em contato  conosco! \\natenciosamente; \\n" + login.getNome() + " " 
			            + login.getSobrenome() + "\\nE-mail: " + login.getEmail() + "\\n\\n LD Armazens Gerais!";				
			
		}else if(flag_cliente == 2) {
			//mensagem ao vendedor1
			mensagem = "Ola! \\n \\nSou " + login.getNome() + " " + login.getSobrenome() +","
		    		+ "  represento a LD Armazens de Guarda-Mor/MG. \\n\\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
		    		+ nome_vendedores_global + " , assim sendo, envio essa mensagem para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
		    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
		    		+ contrato_local.getData_contrato()+ " ainda encontra na nossa base de dados com a carencia de assinatura" + ".\\n \\n \\nQualquer duvida entrar em contato  conosco! \\natenciosamente; \\n" + login.getNome() + " " 
		            + login.getSobrenome() + "\\nE-mail: " + login.getEmail() + "\\n\\n LD Armazens Gerais!";				
		
		}else if(flag_cliente == 3) {
			//mensagem ao vendedor2
			mensagem = "Ola! \\n \\nSou " + login.getNome() + " " + login.getSobrenome() +","
		    		+ "  represento a LD Armazens de Guarda-Mor/MG. \\n\\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
		    		+ nome_vendedores_global + " , assim sendo, envio essa mensagem para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
		    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
		    		+ contrato_local.getData_contrato()+ " ainda encontra na nossa base de dados com a carencia de assinatura" + ".\\n \\n \\nQualquer duvida entrar em contato  conosco! \\natenciosamente; \\n" + login.getNome() + " " 
		            + login.getSobrenome() + "\\nE-mail: " + login.getEmail() + "\\n\\n LD Armazens Gerais!";				
		
			
		}
		
		
		textArea.setText(mensagem);
		textArea.setEditable(false);
		lblNaoAcentue.setVisible(false);
		mensagem_notificacao_global = mensagem;

		
	}
	
	
	public void set_msg_anexo(int flag_cliente) {
		
	String mensagem = "";
		
		if(flag_cliente == 1) {
			 
			mensagem = "Ola! \\n \\nSou " + login.getNome() + " " + login.getSobrenome() +","
		    		+ "  represento a LD Armazens de Guarda-Mor/MG. \\n\\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
		    		+ nome_compradores_global + " , assim sendo, envio essa mensagem   para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
		    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
		    		+ contrato_local.getData_contrato() + " ainda encontra na nossa base de dados com a carencia de assinatura"+ ".\\n \\n \\nQualquer duvida entrar em contato  conosco! \\natenciosamente; \\n" + login.getNome() + " " 
		            + login.getSobrenome() + "\\nE-mail: " + login.getEmail() + "\\n\\n LD Armazens Gerais!";
		
			  
			
			
		}else if(flag_cliente == 2) {
			//mensagem ao vendedor1
			
			mensagem = "Ola! \\n \\nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \\n\\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_vendedores_global + " , assim sendo, envio essa mensagem   para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato() + " ainda encontra na nossa base de dados com a carencia de assinatura"+ ".\\n \\n \\nQualquer duvida entrar em contato  conosco! \\natenciosamente; \\n" + login.getNome() + " " 
			            + login.getSobrenome() + "\\nE-mail: " + login.getEmail() + "\\n\\n LD Armazens Gerais!";
			
				  
		}else if(flag_cliente == 3) {
			//mensagem ao vendedor2
			mensagem = "Ola! \\n \\nSou " + login.getNome() + " " + login.getSobrenome() +","
			    		+ "  represento a LD Armazens de Guarda-Mor/MG. \\n\\nEste  numero esta cadastrado em nossa base de dados para entrar em contato com o produtor rural "
			    		+ nome_vendedores_global + " , assim sendo, envio essa mensagem   para notifica-lo que o contrato de " + contrato_local.getQuantidade() + " " 
			    		+ contrato_local.getMedida() + " de " + contrato_local.getModelo_safra().getProduto().getNome_produto() +   " firmado na data de " 
			    		+ contrato_local.getData_contrato() + " ainda encontra na nossa base de dados com a carencia de assinatura"+ ".\\n \\n \\nQualquer duvida entrar em contato  conosco! \\natenciosamente; \\n" + login.getNome() + " " 
			            + login.getSobrenome() + "\\nE-mail: " + login.getEmail() + "\\n\\n LD Armazens Gerais!";
			
				  
			
		}
		
		
		textArea.setText(mensagem);
		textArea.setEditable(false);
		lblNaoAcentue.setVisible(false);
 
		mensagem_anexo_global = mensagem;
	}
	
	public void set_msg_livre() {
		textArea.setText("");
		textArea.setEditable(true);
	
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
	
	public void enviar_Mensagem(String url_arquivo) {

        //ZapMessenger zap = new ZapMessenger();
        //zap.logar();
		Nuvem nuvem = new Nuvem();
		Whatsapp zap = new Whatsapp();
		
		
		nuvem.abrir();
		nuvem.testar();
		nuvem.listar();
		nuvem.deletarDoc("doc_relatorio_temporario.pdf");

		boolean carregar_arquivo = nuvem.carregarDoc(url_arquivo, "doc_relatorio_temporario.pdf");
		if(carregar_arquivo) {
		
		String url = nuvem.getUrlArquivo("/docs_temps/" + "doc_relatorio_temporario.pdf");
		System.out.println("link do arquivo para enviar via zap: " + url);
		 String mensagem_enviar = "";
		  mensagem_enviar = textArea.getText().toString();
		  mensagem_enviar= Normalizer.normalize(mensagem_enviar, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		  
		  String mensagem_enviar_quebrada[] = mensagem_enviar.split("\n");
		  String mensagem_enviar_final  = "";
		  for(int i = 0; i < mensagem_enviar_quebrada.length; i++) {
			  mensagem_enviar_final = mensagem_enviar_final + mensagem_enviar_quebrada[i] + "\\n";
		  }
		  mensagem_enviar = mensagem_enviar_final;
	
		if(chkOutroContato.isSelected()) {
			//checkBox esta selecionado
			String celular = etNumero.getText();
			String celular_reformado = celular.replaceAll("[^0-9]+", "");
			
			if(celular_reformado.length() == 11) {
				//celular aceito
				 celular_reformado =  celular_reformado;
				
				 boolean retorno = zap.enviarArquivo( "relatorio.pdf", celular, url);
		    	  boolean retorno2 = zap.enviarMensagem(celular, mensagem_enviar);

					//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

					if(retorno && retorno2) {
						JOptionPane.showMessageDialog(null, "Mensagem Enviada!" );
						isto.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Erro, mensagem não enviada!" );
	                      
					}
			    
				
			}else {
				JOptionPane.showMessageDialog(null, "Número de celular incorreto!");
			}
			
		}else {
			//enviar mensagem belo combobox
			
			Contato contato = (Contato) modelContato.getSelectedItem();
			
			String celular = contato.getCelular().replaceAll("[^0-9]", "");
				System.out.println("Celular do combobox selecionado: " + celular);
				//celular aceito
				celular =  celular;
					
				 boolean retorno = zap.enviarArquivo( "relatorio.pdf", celular, url);
		    	  boolean retorno2 = zap.enviarMensagem(celular, mensagem_enviar);

					//String retorno = enviar.enviarMensagem(mensagem, "38999280886");

					if(retorno && retorno2) {
						JOptionPane.showMessageDialog(null, "Mensagem Enviada!" );
						isto.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Erro, mensagem não enviada!" );
	                      
					}
			    
		
		}
		nuvem.deletarDoc("doc_relatorio_temporario.pdf");
		}else {
			JOptionPane.showMessageDialog(null, "Erro ao fazer upload do arquivo!\nConsulte o administrador!");

		}
	}
	
	public void pesquisar_vendedor2(CadastroCliente [] vendedores) {
		if(chkOutroContato.isSelected()) {
			
		}else {
		if(vendedores[1] != null) {
			chckbxContatosVendedor1.setSelected(false);
 			chckbxContatosComprador.setSelected(false);
 			chckbxContatosVendedor2.setSelected(true);

 			
 			
 			modelContato.resetar();
				cBContato.removeAll();
				cBContato.setSelectedItem("");
 			for(Contato contato : todos_contatos_vendedor2) {
 				
 				if(contato != null) {
 					modelContato.addContato(contato);
 				}
 			}
 			cBContato.setSelectedIndex(0);
			
			
		}
		}
	}
	
	public void pesquisar_vendedor1(CadastroCliente [] vendedores) {

 		if(chkOutroContato.isSelected()) {
 			
 		}else {
 		
 			chckbxContatosVendedor1.setSelected(true);
 			chckbxContatosComprador.setSelected(false);
 			if(vendedores[1] != null)
 			chckbxContatosVendedor2.setSelected(false);

 			
 			
 			modelContato.resetar();
				cBContato.removeAll();
				cBContato.setSelectedItem("");
 			for(Contato contato : todos_contatos_vendedor1) {
 				
 				if(contato != null) {
 					modelContato.addContato(contato);
 				}
 			}
 			cBContato.setSelectedIndex(0);

 			
 	}
	}

	public void pesquisar_comprador(CadastroCliente [] vendedores) {
		if(chkOutroContato.isSelected()) {
 			//nao atualiza contatos, apenas troca a mensagem
 			
 			
 		}else {
 			chckbxContatosComprador.setSelected(true);
 			chckbxContatosVendedor1.setSelected(false);
 			if(vendedores[1] != null)
 			chckbxContatosVendedor2.setSelected(false);

 			
 			modelContato.resetar();
				cBContato.removeAll();
				cBContato.setSelectedItem("");

 			for(Contato contato : todos_contatos_comprador) {
 				
 				if(contato != null) {
 					modelContato.addContato(contato);
 				}
 			}
 			
 			cBContato.setSelectedIndex(0);

 	
 	
 		
 	}
	}
	
	public void chamar_outro_contato() {
		chkOutroContato.setSelected(true);
		
		etNumero.setEditable(true);
		etNumero.setEnabled(true);
		cBContato.setEnabled(false);
		lblNaoinclua.setVisible(true);
	}
}
