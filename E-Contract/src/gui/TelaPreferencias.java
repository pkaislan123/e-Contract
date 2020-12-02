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

public class TelaPreferencias extends JDialog {

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelEmpresa = new JPanel();
	private JPanel painelNotificacoes = new JPanel();
	private JPanel painelContratos = new JPanel();

	private JTextField caminhoRaiz;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String caminhoAntigo, codigoSequencialAntigo;
	private final JLabel lblNewLabel_1 = new JLabel("Codigo Sequencial:");
	private final JTextField entCodigoSequencial = new JTextField();
	private final JButton btnAplicarConfigsBD = new JButton("Aplicar");
	private final JButton btnAplicarConfigsContratos = new JButton("Aplicar");
	
	
	
	 
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
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelEmpresa.setBackground(new Color(255, 255, 255));
		painelNotificacoes.setBackground(new Color(255, 255, 255));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		painelPrincipal.addTab("Base de Dados", painelEmpresa);
		painelEmpresa.setLayout(null);
		
		painelPrincipal.addTab("Notificações", painelNotificacoes);
		painelNotificacoes.setLayout(null);
		
		painelPrincipal.addTab("Contratos", painelContratos);
		painelContratos.setLayout(null);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(36, 37, 125, 30);
		
		painelContratos.add(lblNewLabel_1);
		
		painelContratos.add(entCodigoSequencial);
		
		JLabel lblNewLabel = new JLabel("Caminho Raíz da Base de dados:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(25, 45, 239, 14);
		painelEmpresa.add(lblNewLabel);
		
		caminhoRaiz = new JTextField();
		caminhoRaiz.setBounds(254, 37, 585, 33);
		painelEmpresa.add(caminhoRaiz);
		caminhoRaiz.setColumns(10);
		caminhoRaiz.setText(configs_globais.getRaiz());
		btnAplicarConfigsBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarConfigsBaseDados();
			}
		});
		btnAplicarConfigsBD.setBounds(783, 440, 89, 23);
		
		painelEmpresa.add(btnAplicarConfigsBD);
		caminhoAntigo = configs_globais.getRaiz();
		
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
		entCodigoSequencial.setText(Integer.toString(configs_globais.getCodigoSequencial()));
		btnAplicarConfigsContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarConfigsContratos();
			}
		});
		btnAplicarConfigsContratos.setBounds(796, 440, 89, 23);
		
		painelContratos.add(btnAplicarConfigsContratos);
		codigoSequencialAntigo = Integer.toString(configs_globais.getCodigoSequencial());
		
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
	
	
	public void aplicarConfigsContratos() {
		String codigoSequencialAtual = entCodigoSequencial.getText().replace("^[0987654321\b ]", "");
		System.out.println("Codigo sequencial digitado: " + codigoSequencialAtual);

		if(login.getDireitos() == 3) {
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
		String caminho_atual = caminhoRaiz.getText();
		
		if(login.getDireitos() == 1) {
		if(caminhoAntigo.equals(caminho_atual)) {
			//caminhoes iguais, nao alterar nada
		}else {
			if(caminho_atual != null && !caminho_atual.equals("") && !caminho_atual.equals(" ") ){
				//caminho aceito
				System.out.println("Caminho atual: " + caminhoAntigo + " Caminho novo: " + caminho_atual);
				
				ArquivoConfiguracoes arquivo = new ArquivoConfiguracoes();
				arquivo.setPastaRaiz(caminho_atual);
				arquivo.salvarNovasConfiguragoes();
				
				
			}else {
					JOptionPane.showMessageDialog(null, "O caminho para a base de dados está incorreto");

				}
				
	
		
		}
		}else {
			JOptionPane.showMessageDialog(null, "Por segurança, apenas o usuario administrador\npode fazer alterações direcionadas \na base de dados");
			caminhoRaiz.setText(configs_globais.getRaiz().replaceAll("\"", ""));
		}
		
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
