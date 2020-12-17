package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FileUtils;
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
import conexaoBanco.GerenciarBancoLogin;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import tratamento_proprio.Log;
import views_personalizadas.TelaEscolha;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class TelaGerenciarContrato extends JDialog {
	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelCarregamento = new JPanel();
	private JPanel painelListaTarefas = new JPanel();

	private final JLabel lblStatusContrato = new JLabel("Status do Contrato:");
    private final JLabel lblValorTotalPagamentos ;
	InputStream stream = null;
	private final JButton btnEditarContrato = new JButton("Editar");
	private JPanel painel_vizualizar;
	private final JButton btnEnviarMsg = new JButton("Enviar");
	private final JLabel lblNewLabel = new JLabel("     Modelos de Pagamento");
	private CadastroContrato contrato_local;
    private ArrayList<CadastroContrato> lista_sub_contratos = new ArrayList<>();
    private SwingController controller = null;
    private SwingViewBuilder factory;
    private TelaGerenciarContrato isto;
    private String servidor_unidade ;
    
    private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;
    
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
     
     DefaultTableModel modelo_tarefas = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     
     DefaultTableModel modelo_carregamentos = new DefaultTableModel(){
         public boolean isCellEditable(int linha, int coluna) {  
             return false;
         }  
     };
     
     
	 private final JLabel lblNewLabel_1 = new JLabel("*Pagamentos apenas informativos, assim como elaborados no contrato");
	
	 private final JLabel lblTipoContrato = new JLabel("Tipo Contrato:");
	 private final JButton btnExcluirContrato = new JButton("Excluir");
	 private JTable table_tarefas;
	 private final JButton btnNewButton = new JButton("New button");
	 private final JScrollPane scrollPane_1 = new JScrollPane();
	 private final JLabel lblNewLabel_3 = new JLabel("New label");
     
  
     
     
	public TelaGerenciarContrato(CadastroContrato contrato) {
		
		
		getDadosGlobais();
	    servidor_unidade = configs_globais.getServidorUnidade();
		
		
		setModal(true);

		contrato_local = contrato;
		 isto = this;
		
		setResizable(false);
		
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		if(contrato.getSub_contrato() == 0) {
			this.setTitle("E-Contract - Gerenciar Contrato");


		}else {
			this.setTitle("E-Contract - Gerenciar Sub-Contrato");

		}
		
		setBounds(100, 100, 1083, 626);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		
		//contentPanel.setBackground(new Color(255, 255, 255));
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPanel);
		//contentPanel.setLayout(null);
		
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelPagamentos.setBackground(new Color(255, 255, 255));
		painelCarregamento.setBackground(new Color(255, 255, 255));
		
		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		
		
		 if(contrato.getSub_contrato() == 0){
	    	  //não é um subcontrato
	      
		 JPanel painelSubContratos = new JPanel();

		painelSubContratos.setBackground(new Color(255, 255, 255));

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
        
	     
		 JLabel lblSubcontratos = new JLabel("     Sub-Contratos");
		 JButton btnAdicionarSubContrato = new JButton("Adicionar");
		JButton btnSelecionarSubContrato = new JButton("Abrir");
      			
		
		painelPrincipal.addTab("Sub-Contratos", painelSubContratos);
		painelSubContratos.setLayout(null);
		 JScrollPane scrollPaneSubContratos ;

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
		btnAdicionarSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DadosGlobais dados = DadosGlobais.getInstance();
				 dados.setTeraGerenciarContratoPai(isto);
					TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(1, contrato, 0);
					
			


			}
		});
		btnAdicionarSubContrato.setBounds(916, 226, 114, 23);
		
		painelSubContratos.add(btnAdicionarSubContrato);
		btnSelecionarSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela_sub_contratos.getSelectedRow();
				
				//abre a tela de gerenciar o contrato selecionado na lista de sub contratos
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(lista_sub_contratos.get(indiceDaLinha));

			}
		});
		btnSelecionarSubContrato.setBounds(785, 226, 121, 23);
		
		painelSubContratos.add(btnSelecionarSubContrato);
		
	 }
		
		lblStatusContrato.setBackground(new Color(0, 128, 128));
		lblStatusContrato.setOpaque(true);
		lblStatusContrato.setForeground(Color.WHITE);
		lblStatusContrato.setFont(new Font("Arial", Font.BOLD, 18));
		lblStatusContrato.setBounds(554, 76, 470, 35);
	  
		
		 
		 
		painelDadosIniciais.add(lblStatusContrato);
		btnEditarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//	argumentos(CadastroModelo modelo, int tipoContrato, CadastroContrato contrato_pai, int flag_edicao) {
				//TelaEscolhaTipoNovoContrato(int tipoContrato, CadastroContrato contrato_pai, int flag_edicao) {
				  
				fecharDocumento();
				DadosGlobais dados = DadosGlobais.getInstance();
				 dados.setTeraGerenciarContratoPai(isto);
           	  if(contrato.getSub_contrato() == 0) {
           		  //e um contrato pai, abre a tela em modo de edicao
				TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato( 0,contrato_local, 1);
           	  }
           	  else {
           		  //e um subcontrato, o tipo do contrato e 1, e entra no modo de edicao
  				TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato( 1,contrato_local, 1);

           	  }
			}
		});
		
		 
		 
		btnEditarContrato.setBounds(429, 497, 89, 23);
		
		painelDadosIniciais.add(btnEditarContrato);
		
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
		painelCarregamento.setLayout(new MigLayout("", "[][][grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[][][][][grow][][][][][][][][][][][]"));
		
		painelCarregamento.add(lblNewLabel_3, "cell 2 2");
		
		painelCarregamento.add(scrollPane_1, "cell 2 4 27 7,grow");
		
		painelCarregamento.add(btnNewButton, "cell 28 12");
		
		
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
				btnExcluirContrato.setBounds(231, 497, 89, 23);
				
				painelDadosIniciais.add(btnExcluirContrato);
				
				JPanel panel = new JPanel();
				panel.setBounds(554, 271, 161, 128);
				painelDadosIniciais.add(panel);
				
				
				
		
		
				JTable tabela_modelo_pagamentos = new JTable(modelo);
				//tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				tabela_modelo_pagamentos.setBackground(new Color(255, 255, 255));
				 
				modelo.addColumn("Id Pagamento");
				modelo.addColumn("Id Conta");
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
		        lblNewLabel_1.setBounds(585, 255, 445, 14);
		        
		        painelPagamentos.add(lblNewLabel_1);
		        
		        JLabel lblNewLabel_2 = new JLabel("Valor Total dos pagamentos:");
		        lblNewLabel_2.setBounds(670, 215, 166, 14);
		        painelPagamentos.add(lblNewLabel_2);
		        
		         lblValorTotalPagamentos = new JLabel("");
		         lblValorTotalPagamentos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		        lblValorTotalPagamentos.setBounds(835, 213, 101, 31);
		        painelPagamentos.add(lblValorTotalPagamentos);
		        painelListaTarefas.setBackground(new Color(255, 255, 255));
		        
		        
		        painelPrincipal.addTab("Lista Tarefas", painelListaTarefas);
		        painelListaTarefas.setLayout(null);
		     
		        table_tarefas = new JTable(modelo_tarefas);
		        table_tarefas.setBackground(Color.WHITE);
				
		        
		        modelo_tarefas.addColumn("Id Tarefas");
		        modelo_tarefas.addColumn("Status");

		        modelo_tarefas.addColumn("Nome");
		        modelo_tarefas.addColumn("Descrição");
		        modelo_tarefas.addColumn("Mensagem");

		        modelo_tarefas.addColumn("Data");


		        modelo_tarefas.addColumn("Hora");
		        modelo_tarefas.addColumn("Criador");

		        modelo_tarefas.addColumn("Executor");
		        
		        modelo_tarefas.addColumn("Hora Agendada");
		        modelo_tarefas.addColumn("Data Agendada");
		        modelo_tarefas.addColumn("Prioridade");

				table_tarefas.getColumnModel().getColumn(0)
			        .setPreferredWidth(40);
				table_tarefas.getColumnModel().getColumn(1)
			        .setPreferredWidth(90);
				table_tarefas.getColumnModel().getColumn(2)
			        .setPreferredWidth(170);
				table_tarefas.getColumnModel().getColumn(3)
			        .setPreferredWidth(80);
				table_tarefas.getColumnModel().getColumn(4)
			        .setPreferredWidth(80);
				table_tarefas.getColumnModel().getColumn(5)
			        .setPreferredWidth(70);
				table_tarefas.getColumnModel().getColumn(6)
			        .setPreferredWidth(70);
				table_tarefas.getColumnModel().getColumn(7)
			        .setPreferredWidth(90);
				
				   
		        JScrollPane scrollPaneTarefas = new JScrollPane(table_tarefas);
		        scrollPaneTarefas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		        scrollPaneTarefas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		        scrollPaneTarefas.setBackground(Color.WHITE);
		        scrollPaneTarefas.setAutoscrolls(true);
		        scrollPaneTarefas.setBounds(28, 55, 1002, 470);
		        painelListaTarefas.add(scrollPaneTarefas);
		        
		        
		        setPagamentos(contrato);

		        setSubContratos(contrato_local);
		        
		        if(contrato_local.getSub_contrato() == 1) {
		        	//é um sub contrato
		        	lblTipoContrato.setText("Tipo Contrato: Sub-Contrato");
		        }else {
		        	lblTipoContrato.setText("Tipo Contrato: Contrato Original");

		        }
		        
		        String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		        carregarDocumento(url_original);
		        getTarefas();
		 
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		
		
		
	}
	

	public void getTarefas() {
		modelo_tarefas.setNumRows(0);

		if(lista_tarefas != null) {
			lista_tarefas.clear();
		}else {
			lista_tarefas = new ArrayList<>();
		}
		
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_tarefas = gerenciar.getTarefas(contrato_local.getId());
		
		/*
		 *   modelo_tarefas.addColumn("Id Tarefas");
		        modelo_tarefas.addColumn("Status");

		        modelo_tarefas.addColumn("Nome");
		        modelo_tarefas.addColumn("Descrição");
		        modelo_tarefas.addColumn("Mensagem");

		        modelo_tarefas.addColumn("Data");


		        modelo_tarefas.addColumn("Hora");
		        modelo_tarefas.addColumn("Criador");

		        modelo_tarefas.addColumn("Executor");
		        
		        modelo_tarefas.addColumn("Hora Agendada");
		        modelo_tarefas.addColumn("Data Agendada");
		        modelo_tarefas.addColumn("Prioridade");

		 */
		for(CadastroContrato.CadastroTarefa tarefa: lista_tarefas) {
			
			String status_tarefa = "";
			String prioridade = "";
			
			if(tarefa.getStatus_tarefa() == 1) {
				status_tarefa = "Concluida";
			}
			
			if(tarefa.getPrioridade() == 1) {
				prioridade = "Imediata";
			}
			
			GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
			CadastroLogin criador = gerenciarUsuarios.getLogin(tarefa.getCriador().getId());
			CadastroLogin executor = gerenciarUsuarios.getLogin(tarefa.getExecutor().getId());

			
			
			 modelo_tarefas.addRow(new Object[]{tarefa.getId_tarefa(), status_tarefa, tarefa.getNome_tarefa(), 
	            		tarefa.getDescricao_tarefa(),
	                    tarefa.getMensagem(),
	                    tarefa.getData(),
	                    tarefa.getHora(),
	                    criador.getNome(),
	                    executor.getNome(),
	                    tarefa.getHora_agendada(),
	                    tarefa.getData_agendada(),
	                    prioridade
	            
	            });
		}
		
		
		
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
        float valor_total_pagamentos = 0;
    
         if(contrato.getPagamentos() != null) {
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
     	 	valor_total_pagamentos += Float.parseFloat(pag.getValor_string());
       		System.out.println("o valor total agora e: " + valor_total_pagamentos);
       	
       	    
       	    
       	  	Locale ptBr = new Locale("pt", "BR");
       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(pag.getValor_string()));
       	   	System.out.println(valorString);   
       		
	 		modelo.addRow(new Object[]{pag.getId(), id,  cpf, nome, banco, 
				       codigo, agencia, conta, valorString, pag.getData_pagamento()});
       	}
    }
			
       	Locale ptBr = new Locale("pt", "BR");
       	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
       	System.out.println(valorString);
   	 
       	lblValorTotalPagamentos.setText(valorString);
    }
    
    public void carregarDocumento(String url) {
    	// build a controller
    	
    	if(controller == null) {

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

    	}
	
		java.awt.EventQueue.invokeLater(new Runnable() { 
		    public void run() { 
		    	
		    	                 if(painel_vizualizar == null) {
 
		                			 painel_vizualizar = new JPanel();
		                			 painel_vizualizar = factory.buildViewerPanel();
		                			 controller.openDocument(url);
							//viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
							//viewerComponentPanel.setMaximumSize(new Dimension(400, 370));
		                			
	                			 controller.openDocument(url);
	                			 painel_vizualizar.setBounds(10, 25, 508, 461);							
	 							
	                			 painelDadosIniciais.add(painel_vizualizar);
		    	                 }else {
		                			 controller.openDocument(url);
		                			 painel_vizualizar.repaint();
		                			 painel_vizualizar.updateUI();
		                			 painelDadosIniciais.add(painel_vizualizar);

		                			
			 							
		    	                 }
	                			 
	                			 
		                	 	
		                	
				
			  } 
		}); 
    }
    
    public void fecharDocumento() {
    	
    if(controller != null) {
		controller.closeDocument();
    }
    	
		

		
    }
    
    public void atualizarContratoLocal() {
    	
    
    			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
    	    	contrato_local = gerenciar.getContrato(contrato_local.getId());
    	    	setPagamentos(contrato_local);
    	    	setSubContratos(contrato_local);
    	    	
    	    	//criar acopia temporaria
    	    	
    	    	String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
    	        carregarDocumento(url_original);
    		
    
    }
    
    public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
    
    public String criarCopiaTemporaria(String url, String codigo) throws IOException {
    	ManipularTxt manipular = new ManipularTxt();
    	return manipular.copiar(url, codigo);
    }
}
