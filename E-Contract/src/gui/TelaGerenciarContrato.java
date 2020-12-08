package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.ContaBancaria;
import conexaoBanco.GerenciarBancoContratos;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import views_personalizadas.TelaEscolha;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.ScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;

public class TelaGerenciarContrato extends JDialog {
	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelCarregamento = new JPanel();
	private JPanel painelSubContratos = new JPanel();
	private final JLabel lblStatusContrato = new JLabel("Status do Contrato:");

	InputStream stream = null;
	private final JButton btnNewButton = new JButton("Editar");
	private JPanel painel_vizualizar;
	private final JButton btnEnviarMsg = new JButton("Enviar");
	private final JLabel lblNewLabel = new JLabel("     Modelos de Pagamento");
	private CadastroContrato contrato_local;
    private ArrayList<CadastroContrato> lista_sub_contratos = new ArrayList<>();
    private SwingController controller;
    private SwingViewBuilder factory;
    private TelaGerenciarContrato isto;
	 DefaultTableModel modelo = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     
     
     DefaultTableModel modelo_sub_contratos = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     
	 private final JLabel lblNewLabel_1 = new JLabel("*Pagamentos apenas informativos, assim como elaborados no contrato");
	 private final JScrollPane scrollPaneSubContratos ;
	 private final JLabel lblSubcontratos = new JLabel("     Sub-Contratos");
	 private final JButton btnNewButton_1 = new JButton("Adicionar");
	 private final JButton btnSelecionar = new JButton("Abrir");
	 private final JLabel lblTipoContrato = new JLabel("Tipo Contrato:");
	 private final JButton btnNewButton_2 = new JButton("Excluir");
     
  
     
     
	public TelaGerenciarContrato(CadastroContrato contrato) {
		
		
		getDadosGlobais();
		getContentPane().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("ganhou foco");
				atualizarContratoLocal();

			}
		});
		
		setModal(true);

		contrato_local = contrato;
		 isto = this;
		
		setResizable(false);
		
		System.out.println("Caminho do arquivo: " + contrato.getCaminho_arquivo());
		try {
			stream = new FileInputStream(contrato.getCaminho_arquivo());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("E-Contract - Gerenciar Contrato");
		setBounds(100, 100, 1083, 626);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		painelPrincipal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("ganhou focu");
				atualizarContratoLocal();
			}
		});
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelPagamentos.setBackground(new Color(255, 255, 255));
		painelCarregamento.setBackground(new Color(255, 255, 255));

		painelSubContratos.setBackground(new Color(255, 255, 255));
		
		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		
		JTable tabela_sub_contratos = new JTable(modelo_sub_contratos);
		//tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_sub_contratos.setBackground(new Color(255, 255, 255));
		 
		modelo_sub_contratos.addColumn("ID");
		modelo_sub_contratos.addColumn("Código");
		modelo_sub_contratos.addColumn("Status");

		modelo_sub_contratos.addColumn("Quantidade");
		modelo_sub_contratos.addColumn("Medida");
		modelo_sub_contratos.addColumn("Produto");
		modelo_sub_contratos.addColumn("Safra");
		modelo_sub_contratos.addColumn("Valor Produto");
		modelo_sub_contratos.addColumn("Valor Total");
		modelo_sub_contratos.addColumn("Compradores");

		modelo_sub_contratos.addColumn("Vendedores");
		modelo_sub_contratos.addColumn("Corretores");
		modelo_sub_contratos.addColumn("Data do Contrato");
       

	      
		tabela_sub_contratos.getColumnModel().getColumn(0)
        .setPreferredWidth(40);
		tabela_sub_contratos.getColumnModel().getColumn(1)
        .setPreferredWidth(90);
		tabela_sub_contratos.getColumnModel().getColumn(2)
        .setPreferredWidth(170);
		tabela_sub_contratos.getColumnModel().getColumn(3)
        .setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(4)
        .setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(5)
        .setPreferredWidth(70);
		tabela_sub_contratos.getColumnModel().getColumn(6)
        .setPreferredWidth(70);
		tabela_sub_contratos.getColumnModel().getColumn(7)
        .setPreferredWidth(90);
		tabela_sub_contratos.getColumnModel().getColumn(8)
        .setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(9)
        .setPreferredWidth(150);
		tabela_sub_contratos.getColumnModel().getColumn(10)
        .setPreferredWidth(150);
		tabela_sub_contratos.getColumnModel().getColumn(11)
        .setPreferredWidth(100);
		tabela_sub_contratos.getColumnModel().getColumn(12)
        .setPreferredWidth(80);
        
	     
      
      			
		
		painelPrincipal.addTab("Sub-Contratos", painelSubContratos);
		painelSubContratos.setLayout(null);
		scrollPaneSubContratos = new JScrollPane(tabela_sub_contratos);
		scrollPaneSubContratos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubContratos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubContratos.setBackground(Color.WHITE);
		scrollPaneSubContratos.setAutoscrolls(true);
		scrollPaneSubContratos.setBounds(26, 83, 1004, 132);
		
		painelSubContratos.add(scrollPaneSubContratos);
		lblSubcontratos.setOpaque(true);
		lblSubcontratos.setForeground(Color.WHITE);
		lblSubcontratos.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSubcontratos.setBackground(new Color(0, 51, 0));
		lblSubcontratos.setBounds(0, 22, 230, 31);
		
		painelSubContratos.add(lblSubcontratos);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(1, contrato, 0);

			}
		});
		btnNewButton_1.setBounds(916, 226, 114, 23);
		
		painelSubContratos.add(btnNewButton_1);
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela_sub_contratos.getSelectedRow();
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(lista_sub_contratos.get(indiceDaLinha));

			}
		});
		btnSelecionar.setBounds(785, 226, 121, 23);
		
		painelSubContratos.add(btnSelecionar);
		
		lblStatusContrato.setBackground(new Color(0, 128, 128));
		lblStatusContrato.setOpaque(true);
		lblStatusContrato.setForeground(Color.WHITE);
		lblStatusContrato.setFont(new Font("Arial", Font.BOLD, 18));
		lblStatusContrato.setBounds(554, 76, 470, 35);
		
		painelDadosIniciais.add(lblStatusContrato);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//	argumentos(CadastroModelo modelo, int tipoContrato, CadastroContrato contrato_pai, int flag_edicao) {
				//TelaEscolhaTipoNovoContrato(int tipoContrato, CadastroContrato contrato_pai, int flag_edicao) {
				   fecharDocumento();

					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato( 0,contrato, 1);
			}
		});
		
		btnNewButton.setBounds(429, 497, 89, 23);
		
		painelDadosIniciais.add(btnNewButton);
		
		int status = contrato.getStatus_contrato();
		if(status== 1) {
			lblStatusContrato.setText("Status do Contrato: " + "Recolher Assinaturas");

		}
		else if(status == 2) {
			lblStatusContrato.setText("Status do Contrato: " + "Assinado");

		}
		else if(status == 3) {
			lblStatusContrato.setText("Status do Contrato: " + "Cumprindo");

		}
		
		
		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(null);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(0, 51, 0));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setBounds(0, 22, 230, 31);
		
		painelPagamentos.add(lblNewLabel);
		
		painelPrincipal.addTab("Carregamento", painelCarregamento);
		painelCarregamento.setLayout(null);
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		
			
				btnEnviarMsg.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TelaEscolha escolher = new TelaEscolha(contrato);
					}
				});
				
				btnEnviarMsg.setBounds(330, 497, 89, 23);
				
				painelDadosIniciais.add(btnEnviarMsg);
				lblTipoContrato.setOpaque(true);
				lblTipoContrato.setForeground(Color.WHITE);
				lblTipoContrato.setFont(new Font("Arial", Font.BOLD, 16));
				lblTipoContrato.setBackground(new Color(102, 0, 102));
				lblTipoContrato.setBounds(554, 122, 470, 29);
				
				painelDadosIniciais.add(lblTipoContrato);
				btnNewButton_2.setBounds(231, 497, 89, 23);
				
				painelDadosIniciais.add(btnNewButton_2);
				
				
				
		
		
				JTable tabela_modelo_pagamentos = new JTable(modelo);
				//tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				tabela_modelo_pagamentos.setBackground(new Color(255, 255, 255));
				 
				modelo.addColumn("Id");
				modelo.addColumn("CPF");
				modelo.addColumn("Nome");

				modelo.addColumn("Banco");


				modelo.addColumn("Codigo");
				modelo.addColumn("Agência");
                  
				modelo.addColumn("Conta");
				modelo.addColumn("Valor");
				modelo.addColumn("Data Pagamento");

			      
				tabela_modelo_pagamentos.getColumnModel().getColumn(0)
			        .setPreferredWidth(40);
				tabela_modelo_pagamentos.getColumnModel().getColumn(1)
			        .setPreferredWidth(90);
				tabela_modelo_pagamentos.getColumnModel().getColumn(2)
			        .setPreferredWidth(170);
				tabela_modelo_pagamentos.getColumnModel().getColumn(3)
			        .setPreferredWidth(80);
				tabela_modelo_pagamentos.getColumnModel().getColumn(4)
			        .setPreferredWidth(80);
				tabela_modelo_pagamentos.getColumnModel().getColumn(5)
			        .setPreferredWidth(70);
				tabela_modelo_pagamentos.getColumnModel().getColumn(6)
			        .setPreferredWidth(70);
				tabela_modelo_pagamentos.getColumnModel().getColumn(7)
			        .setPreferredWidth(90);
				tabela_modelo_pagamentos.getColumnModel().getColumn(8)
		        .setPreferredWidth(90);
			     
		      
		      			

		        JScrollPane scrollPane = new JScrollPane(tabela_modelo_pagamentos);
		    	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		        scrollPane.setBounds(26, 83, 1004, 132);
		        scrollPane.setAutoscrolls(true);
		        scrollPane.setBackground(new Color(255, 255, 255));
		        painelPagamentos.add(scrollPane);
		        lblNewLabel_1.setForeground(new Color(0, 51, 0));
		        lblNewLabel_1.setOpaque(true);
		        lblNewLabel_1.setBackground(Color.ORANGE);
		        lblNewLabel_1.setBounds(585, 231, 445, 14);
		        
		        painelPagamentos.add(lblNewLabel_1);
				
		        
		        setPagamentos(contrato);

		        setSubContratos(contrato_local);
		        
		        if(contrato_local.getSub_contrato() == 1) {
		        	//é um sub contrato
		        	lblTipoContrato.setText("Tipo Contrato: Sub-Contrato");
		        	painelSubContratos.setVisible(false);
		        }else {
		        	lblTipoContrato.setText("Tipo Contrato: Contrato Original");

		        }
		        
		        carregarDocumento( configs_globais.getRaiz() + "\\" + contrato_local.getCaminho_arquivo());
		        
		        
		        
		        
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
	}
	
	
	public void setSubContratos(CadastroContrato contrato_na_funcao) {
		modelo_sub_contratos.setNumRows(0);
	    GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
	    lista_sub_contratos.clear();

	      
	    
	   
	    for (CadastroContrato contrato : gerenciar.getSubContratos(contrato_na_funcao.getId())) {
	    	String cpf, cnpj, nome;
	   
	    	CadastroCliente compradores[] = contrato.getCompradores();
	    	CadastroCliente vendedores[] = contrato.getVendedores();
	    	CadastroCliente corretores[] = contrato.getCorretores();
	    	
	    	
	    	String nome_corretores  = "";
	    	String nome_vendedores  = "";
	    	String nome_compradores  = "";

	    	
	    	
	    	if(corretores[0] != null)
	    	{
	    		if(corretores[0].getTipo_pessoa() == 0)
	    		{
	    			//pessoa fisica
	    			nome_corretores = corretores[0].getNome_empresarial();
	    		}else {
	    			nome_corretores = corretores[0].getNome_fantaia();

	    		}
	    	}
	    	
			contrato.setNomes_corretores(nome_corretores);

	    	if(compradores[0] != null)
	    	{
	    		if(compradores[0].getTipo_pessoa() == 0)
	    		{
	    			//pessoa fisica
	    			nome_compradores = compradores[0].getNome_empresarial();
	    		}else {
	    			nome_compradores = compradores[0].getNome_fantaia();

	    		}
	    	}
			contrato.setNomes_compradores(nome_compradores);

	    	for(CadastroCliente vendedor : contrato.getVendedores()) {
	    		if(vendedor != null) {
	    			if(vendedor.getTipo_pessoa() == 0)
		    		{
		    			//pessoa fisica
	    				nome_vendedores += vendedor.getNome_empresarial();
		    		}else {
	    				nome_vendedores += vendedor.getNome_fantaia();

		    		}
	    		}
	    	}
	    	contrato.setNomes_vendedores(nome_vendedores);


			int status = contrato.getStatus_contrato();
			String text_status = "";
			if(status== 1) {
				text_status=  "Recolher Assinaturas".toUpperCase();

			}
			else if(status == 2) {
				text_status = "Assinado".toUpperCase();

			}
			else if(status == 3) {
				text_status = "Cumprindo".toUpperCase();

			}
			
			/*
			    * 	modelo.addColumn("ID");
			modelo.addColumn("Código");
			 modelo.addColumn("Status");

	        modelo.addColumn("Quantidade");
	        modelo.addColumn("Medida");
	        modelo.addColumn("Produto");
	        modelo.addColumn("Safra");
	        modelo.addColumn("Valor Produto");
	        modelo.addColumn("Valor Total");
	        modelo.addColumn("Vendedores");
	        modelo.addColumn("Compradores");
	        modelo.addColumn("Corretores");
	        modelo.addColumn("Data do Contrato");

			    */
			
			if(contrato.getSub_contrato() == 1) {
				
				modelo_sub_contratos.addRow(new Object[]{contrato.getId(), contrato.getCodigo(), text_status, contrato.getQuantidade(), 
		            		contrato.getMedida().toUpperCase(),
		            contrato.getModelo_safra().getProduto().getNome_produto(),
		            contrato.getModelo_safra().getAno_colheita() + "/" +  contrato.getModelo_safra().getAno_plantio(),
		             "R$ " + contrato.getValor_produto(), 
		            "R$ " + contrato.getValor_a_pagar(), 
		            contrato.getNomes_compradores(), contrato.getNomes_vendedores(), contrato.getNomes_corretores(),
		            contrato.getData_contrato()
		            
		            });
		            lista_sub_contratos.add(contrato);
		    	}
	    }
	}
	
	   
    public void setPagamentos(CadastroContrato contrato) {
       	String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, data_pagamento;

		modelo.setNumRows(0);

       	for(CadastroContrato.CadastroPagamento pag : contrato.getPagamentos()) {
       		
   			ContaBancaria conta_bc = pag.getConta();

       		
       		if(conta_bc != null) {
       		   id = Integer.toString(conta_bc.getId_conta());
   	 		cpf = conta_bc.getCpf_titular();
   	 		banco = conta_bc.getBanco();
   	 		nome = conta_bc.getNome();
   	 		codigo = conta_bc.getCodigo();
   	 		agencia = conta_bc.getAgencia();
   	 		conta = conta_bc.getConta();
       		}
       		else {
    	    id = "00";
	 		cpf = "Há Informar";
	 		banco = "Há Informar";
	 		nome = "Há Informar";
	 		codigo = "Há Informar";
	 		agencia = "Há Informar";
	 		conta = "Há Informar";
       		}
       		
       	
               
	 		modelo.addRow(new Object[]{id, cpf, nome, banco, 
				       codigo, agencia, conta, pag.getValor_string(), pag.getData_pagamento()});
       	}
			
   	 
    }
    
    public void carregarDocumento(String url) {
    	// build a controller
		 controller = new SwingController();

		 PropertiesManager propriedades =  new PropertiesManager (System.getProperties (),ResourceBundle.getBundle (PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
		// Build a SwingViewFactory configured with the controller
		
		 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION,
		         Boolean.FALSE);
		 propriedades.setBoolean (PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR,
		         Boolean.TRUE);
		 propriedades.setBoolean (PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR,
		         Boolean.TRUE);
		 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION,
		         Boolean.FALSE);
		 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV,
		         Boolean.FALSE);
		 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_STATUSBAR,
		         Boolean.FALSE);
		 propriedades.setBoolean (PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT,
		         Boolean.FALSE);
		 
		 propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 0.85f );

		  factory = new SwingViewBuilder(controller, propriedades);
		// Use the factory to build a JPanel that is pre-configured
		//with a complete, active Viewer UI.
		controller.getDocumentViewController().setAnnotationCallback(
			     new org.icepdf.ri.common.MyAnnotationCallback(
			            controller.getDocumentViewController()));


	
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	painel_vizualizar = new JPanel();
		    	
			    painel_vizualizar = factory.buildViewerPanel();
				
				

				
				controller.openDocument(url);
				//viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
				//viewerComponentPanel.setMaximumSize(new Dimension(400, 370));
				painel_vizualizar.setBounds(10, 25, 508, 461);							
				
				painelDadosIniciais.add(painel_vizualizar);
				
				
				
			  } 
		}); 
    }
    
    public void fecharDocumento() {
    	
    
		controller.dispose();
		controller.closeDocument();
		controller.exit();
		controller = null;
    	
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
               painel_vizualizar = new JPanel();
		    	
                painel_vizualizar.setBackground(new Color(0,0,0,0));
				
								//viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
				//viewerComponentPanel.setMaximumSize(new Dimension(400, 370));
				painel_vizualizar.setBounds(10, 25, 508, 461);							
				
				painelDadosIniciais.add(painel_vizualizar);
				
			  } 
		}); 
		
		
    }
    
    public void atualizarContratoLocal() {
    	GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
    		
    	contrato_local = gerenciar.getContrato(contrato_local.getId());
    	setPagamentos(contrato_local);
    	setSubContratos(contrato_local);
    	carregarDocumento(contrato_local.getCaminho_arquivo());
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
