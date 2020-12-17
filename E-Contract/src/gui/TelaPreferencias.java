package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import cadastros.CadastroLogin;
import cadastros.CadastroLogin.Preferencias;
import conexaoBanco.GerenciarBancoLogin;
import manipular.ArquivoConfiguracoes;
import manipular.ConfiguracoesGlobais;
import outros.DadosGlobais;
import tratamento_proprio.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class TelaPreferencias extends JDialog {

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelEmpresa = new JPanel();
	private JPanel painelNotificacoes = new JPanel();
	private JPanel painelContratos = new JPanel();
	private JPanel painelFerramentasTerceirizadas = new JPanel();

	private JTextField entHostBaseArquivos;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String caminhoAntigo, codigoSequencialAntigo;
	private final JLabel lblNewLabel_1 = new JLabel("Codigo Sequencial:");
	private final JTextField entCodigoSequencial = new JTextField();
	private final JButton btnAplicarConfigsBD = new JButton("Aplicar");
	private final JButton btnAplicarConfigsContratos = new JButton("Aplicar");
	private JTextField entUnidade;
	private JTextField entHostBancoDados;
	private JTextField entPorta;
	private JTextField entUsuario;
	private JTextField entSenha;
	private JTextField entNomeBancoDados;
	JCheckBox chkBoxWhatsapp, chkBoxExatoAtivado, chkBoxSintegraAtivado;

	 
	public TelaPreferencias() {
	
		setModal(true);
		 getDadosGlobais();
		TelaPreferencias isto = this;
		
		setResizable(false);
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Tela Padrao Abas");
		setBounds(100, 100, 993, 508);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(null);
		painelPrincipal = new JTabbedPane();
		painelPrincipal.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		painelPrincipal.setBounds(0, 0, 987, 479);
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		painelPrincipal.setTabPlacement(JTabbedPane.LEFT);		
		painelDadosIniciais.setBackground(new Color(51, 102, 102));
		painelEmpresa.setBackground(new Color(255, 255, 204));
		painelNotificacoes.setBackground(new Color(255, 255, 255));

		
		painelFerramentasTerceirizadas.setBackground(new Color(255, 255, 255));
		
		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		painelPrincipal.addTab("Base de Dados", painelEmpresa);
		painelEmpresa.setLayout(null);
		
		painelPrincipal.addTab("Notificações", painelNotificacoes);
		painelNotificacoes.setLayout(null);
		
		painelPrincipal.addTab("Contratos", painelContratos);
		painelContratos.setLayout(null);
		
		
		painelPrincipal.addTab("Ferramentas Terceirizadas", painelFerramentasTerceirizadas);
		painelFerramentasTerceirizadas.setLayout(null);
		
		JLabel lbl123 = new JLabel("Api Sintegra AWS:");
		lbl123.setFont(new Font("Arial", Font.BOLD, 14));
		lbl123.setBounds(48, 58, 141, 23);
		painelFerramentasTerceirizadas.add(lbl123);
		
		 chkBoxSintegraAtivado = new JCheckBox("Ativado");
		chkBoxSintegraAtivado.setBounds(195, 59, 97, 23);
		painelFerramentasTerceirizadas.add(chkBoxSintegraAtivado);
		
		JLabel lblApiWhatsapp = new JLabel("Api WhatsApp:");
		lblApiWhatsapp.setFont(new Font("Arial", Font.BOLD, 14));
		lblApiWhatsapp.setBounds(48, 92, 141, 23);
		painelFerramentasTerceirizadas.add(lblApiWhatsapp);
		
		 chkBoxWhatsapp = new JCheckBox("Ativado");
		chkBoxWhatsapp.setBounds(195, 93, 97, 23);
		painelFerramentasTerceirizadas.add(chkBoxWhatsapp);
		
		JLabel lblApiExatoDigital = new JLabel("Api Exato Digital:");
		lblApiExatoDigital.setFont(new Font("Arial", Font.BOLD, 14));
		lblApiExatoDigital.setBounds(48, 130, 141, 23);
		painelFerramentasTerceirizadas.add(lblApiExatoDigital);
		
		 chkBoxExatoAtivado = new JCheckBox("Ativado");
		chkBoxExatoAtivado.setBounds(195, 131, 97, 23);
		painelFerramentasTerceirizadas.add(chkBoxExatoAtivado);
		
		JButton btnNewButton = new JButton("Atualizar Preferências");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//atualizar configuracoes de preferencias
				if(login.getConfigs_privilegios().getPrivilegio_alterar_apis() == 1) {
					atualizarConfiguracoesPreferencias();

				}else {
					JOptionPane.showMessageDialog(null, "Para alterar essas configurações\né necessário elevação de direitos");
				}
			}
		});
		btnNewButton.setBounds(609, 415, 190, 23);
		painelFerramentasTerceirizadas.add(btnNewButton);
		
		
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(36, 37, 125, 30);
		
		painelContratos.add(lblNewLabel_1);
		
		painelContratos.add(entCodigoSequencial);
		
		JLabel lblNewLabel = new JLabel("Host Base de Arquivos:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 49, 180, 23);
		painelEmpresa.add(lblNewLabel);
		
		entHostBaseArquivos = new JTextField();
		entHostBaseArquivos.setBounds(189, 45, 585, 33);
		painelEmpresa.add(entHostBaseArquivos);
		entHostBaseArquivos.setColumns(10);
		btnAplicarConfigsBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarConfigsBaseDados();
			}
		});
		btnAplicarConfigsBD.setBounds(783, 440, 89, 23);
		
		painelEmpresa.add(btnAplicarConfigsBD);
		
		JLabel lblUnidade = new JLabel("Unidade:");
		lblUnidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblUnidade.setBounds(115, 83, 75, 23);
		painelEmpresa.add(lblUnidade);
		
		entUnidade = new JTextField();
		entUnidade.setColumns(10);
		entUnidade.setBounds(189, 83, 585, 33);
		painelEmpresa.add(entUnidade);
		
		JLabel lblArquivos = new JLabel("Arquivos");
		lblArquivos.setFont(new Font("Arial", Font.BOLD, 14));
		lblArquivos.setBounds(369, 11, 75, 23);
		painelEmpresa.add(lblArquivos);
		
		JLabel lblBancoDeDados = new JLabel("Banco de Dados");
		lblBancoDeDados.setFont(new Font("Arial", Font.BOLD, 14));
		lblBancoDeDados.setBounds(341, 145, 152, 23);
		painelEmpresa.add(lblBancoDeDados);
		
		entHostBancoDados = new JTextField();
		entHostBancoDados.setColumns(10);
		entHostBancoDados.setBounds(189, 186, 585, 33);
		painelEmpresa.add(entHostBancoDados);
		
		JLabel lblNewLabel_2 = new JLabel("Host Base de Arquivos:");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 190, 180, 23);
		painelEmpresa.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Porta:");
		lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(127, 234, 52, 23);
		painelEmpresa.add(lblNewLabel_2_1);
		
		entPorta = new JTextField();
		entPorta.setColumns(10);
		entPorta.setBounds(189, 230, 585, 33);
		painelEmpresa.add(entPorta);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Usuário:");
		lblNewLabel_2_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2_1_1.setBounds(110, 280, 69, 23);
		painelEmpresa.add(lblNewLabel_2_1_1);
		
		entUsuario = new JTextField();
		entUsuario.setColumns(10);
		entUsuario.setBounds(189, 276, 585, 33);
		painelEmpresa.add(entUsuario);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Senha:");
		lblNewLabel_2_1_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2_1_1_1.setBounds(122, 324, 57, 23);
		painelEmpresa.add(lblNewLabel_2_1_1_1);
		
		entSenha = new JTextField();
		entSenha.setColumns(10);
		entSenha.setBounds(189, 320, 585, 33);
		painelEmpresa.add(entSenha);
		
		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("Nome Banco:");
		lblNewLabel_2_1_1_1_1.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel_2_1_1_1_1.setBounds(77, 368, 102, 23);
		painelEmpresa.add(lblNewLabel_2_1_1_1_1);
		
		entNomeBancoDados = new JTextField();
		entNomeBancoDados.setColumns(10);
		entNomeBancoDados.setBounds(189, 364, 585, 33);
		painelEmpresa.add(entNomeBancoDados);
		
		getContentPane().add(painelPrincipal);

		
		entCodigoSequencial.setBounds(158, 39, 152, 30);
		entCodigoSequencial.setColumns(10);
		entCodigoSequencial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				//120.927.987-00
				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCodigoSequencial.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
			
				if(entCodigoSequencial.getText().length()>=4){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCodigoSequencial.setText(entCodigoSequencial.getText().substring(0,4));
				}
			
			}
				
			}
		});
		
		btnAplicarConfigsContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarConfigsContratos();
			}
		});
		btnAplicarConfigsContratos.setBounds(796, 440, 89, 23);
		
		painelContratos.add(btnAplicarConfigsContratos);
		
		getConfiguracoesBase();
		
		this.setLocationRelativeTo(null);
		setarConfigsUsuario();

		this.setVisible(true);
		
		
	}
	
	
	public void getConfiguracoesBase() {
		
		
		entHostBaseArquivos.setText(configs_globais.getServidor_arquivos().getServidor());
		entUnidade.setText(configs_globais.getServidor_arquivos().getUnidade());
		
		entHostBancoDados.setText(configs_globais.getBaseDados().getHost());
		entPorta.setText(configs_globais.getBaseDados().getPorta());
		entUsuario.setText(configs_globais.getBaseDados().getNome_usuario());
		entSenha.setText(configs_globais.getBaseDados().getSenha());
		entNomeBancoDados.setText(configs_globais.getBaseDados().getNome_banco());

		
		
	}
	
	public void aplicarConfigsContratos() {
		String codigoSequencialAtual = entCodigoSequencial.getText().replace("^[0987654321\b ]", "");
		System.out.println("Codigo sequencial digitado: " + codigoSequencialAtual);

		if(login.getConfigs_privilegios().getNivel_privilegios() == 3) {
			JOptionPane.showMessageDialog(null, "Por segurança, apenas o usuario administrador ou o gerente financeiro\npode fazer alterações direcionadas \na códigos de contratos");
			entCodigoSequencial.setText(Integer.toString(configs_globais.getCodigoSequencial()));
		}else {
			if(codigoSequencialAtual.equals(codigoSequencialAntigo)) {
				//codigos iguais, nao alterar nada
			}else {
				if(codigoSequencialAtual != null && !codigoSequencialAtual.equals("") && !codigoSequencialAtual.equals(" ") ){
					//codigo  aceito
					
						
						if(codigoSequencialAtual.length() != 3) {
							
							try {
								
								int codigo_sequencial_inteiro = Integer.parseInt(codigoSequencialAtual);
							
							    if(codigo_sequencial_inteiro <= 1000 || codigo_sequencial_inteiro > 9999 ) {
									 JOptionPane.showMessageDialog(null, "O código sequencial para  está incorreto!");

							    }else {
							    	ArquivoConfiguracoes arquivo = new ArquivoConfiguracoes();
									arquivo.setCodidoSequencial(Integer.parseInt(codigoSequencialAtual));
									arquivo.salvarNovasConfiguragoes();
							    }
							
							}catch(Exception t) {
								 JOptionPane.showMessageDialog(null, "O código sequencial para  está incorreto!");

							}
							
						}else {
							 JOptionPane.showMessageDialog(null, "O código sequencial para  está incorreto!");

						}
						
											
				
					
				}else {
						JOptionPane.showMessageDialog(null, "O código sequencial para  está incorreto!");

					}
					
		
			
			}
		}
	}
	
	public void aplicarConfigsBaseDados() {
		String caminho_atual = entHostBaseArquivos.getText();
		
		if(login.getConfigs_privilegios().getNivel_privilegios() == 1) {
		if(caminhoAntigo.equals(caminho_atual)) {
			//caminhoes iguais, nao alterar nada
		}else {
			if(caminho_atual != null && !caminho_atual.equals("") && !caminho_atual.equals(" ") ){
				//caminho aceito
				System.out.println("Caminho atual: " + caminhoAntigo + " Caminho novo: " + caminho_atual);
				
				ArquivoConfiguracoes arquivo = new ArquivoConfiguracoes();
				arquivo.salvarNovasConfiguragoes();
				
				
			}else {
					JOptionPane.showMessageDialog(null, "O caminho para a base de dados está incorreto");

				}
				
	
		
		}
		}else {
			JOptionPane.showMessageDialog(null, "Por segurança, apenas o usuario administrador\npode fazer alterações direcionadas \na base de dados");
		}
		
	}
	
	public void setarConfigsUsuario() {
        getDadosGlobais();

		if(login.getConfigs_preferencias().getApi_exato() == 1) {
			chkBoxExatoAtivado.setSelected(true);
		}else
		{
			chkBoxExatoAtivado.setSelected(false);

		}
		
		if(login.getConfigs_preferencias().getApi_whatsapp() == 1) {
			chkBoxWhatsapp.setSelected(true);
		}else
		{
			chkBoxWhatsapp.setSelected(false);

		}
		
		if(login.getConfigs_preferencias().getApi_sintegra() == 1) {
			chkBoxSintegraAtivado.setSelected(true);
		}else
		{
			chkBoxSintegraAtivado.setSelected(false);

		}
		
		
  }
	
	public void atualizarConfiguracoesPreferencias() {
		GerenciarBancoLogin gerenciarBancoLogin = new GerenciarBancoLogin();
		CadastroLogin login_atualizar = login;
		CadastroLogin.Preferencias novas_preferencias = new CadastroLogin.Preferencias();
		
		//fazer checagens
		if(chkBoxWhatsapp.isSelected()) {
			novas_preferencias.setApi_whatsapp(1);
		}else {
			novas_preferencias.setApi_whatsapp(0);

		}
		
		if(chkBoxSintegraAtivado.isSelected()) {
			novas_preferencias.setApi_sintegra(1);
		}else {
			novas_preferencias.setApi_sintegra(0);

		}
		
		if(chkBoxExatoAtivado.isSelected()) {
			novas_preferencias.setApi_exato(1);;
		}else {
			novas_preferencias.setApi_exato(0);;

		}
		novas_preferencias.setId_preferencias(login.getConfigs_preferencias().getId_preferencias());
		
		login_atualizar.setConfigs_preferencias(novas_preferencias);
		
		if(gerenciarBancoLogin.atualizarUsuario(login_atualizar)) {
			JOptionPane.showMessageDialog(null, "Preferências de usuário atualizadas");
		}else {
			JOptionPane.showMessageDialog(null, "Erro ao  Atualizadar preferências de usuário\nConsulte o Administrador");

		}
		
		//setar novas configs de login
		DadosGlobais dados = DadosGlobais.getInstance();
         dados.setLogin(gerenciarBancoLogin.buscaLogin(login_atualizar.getLogin()));
         setarConfigsUsuario();
         
	 
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
