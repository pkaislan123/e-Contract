package main.java.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
//import javax.swing.JTextFieldPersonalizado;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;



import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ManipularTxt;
import main.java.outros.BuscarCep;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.outros.GetHttp;
import main.java.outros.GetSintegra;
import main.java.outros.JPanelBackground;
import main.java.outros.JPanelTransparent;
import main.java.outros.JTextFieldPersonalizado;
import main.java.outros.MyFileVisitor;
import main.java.outros.TratarDados;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.cadastros.CadastroProduto;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.classesExtras.CBProdutoPersonalizado;
import main.java.classesExtras.CBProdutoRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import java.io.IOException;
import java.net.URL;

import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;



public class TelaCadastroTransportadores extends JDialog {
	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	
	private final JPanelBackground contentPanel = new JPanelBackground();

	//painel pai
	private JTabbedPane painelPrincipal;
	
	
	//painel filho1JPanelTransparent
	private JPanelBackground painelDadosIniciais = new JPanelBackground();

	//painel dinamico, dentro de filho1
    private JPanelTransparent panelDinamico = new JPanelTransparent();;
    
    
    //painel pessoa fisica e juridico
    private JPanelTransparent panelPessoaFisica = new JPanelTransparent();
	private JPanelTransparent panelPessoaJuridica = new JPanelTransparent();
	
	//outros paineis
	
	private JPanel painelEmpresa = new JPanel();
	private JPanel painelDadosBancarios = new JPanel();
	private JPanel painelContato = new JPanel();
	private JPanel painelFinalizar = new JPanel();
	private JPanel painelVeiculos = new JPanel();

	
	
	private JTextFieldPersonalizado entLogradouro;
	private JTextFieldPersonalizado entNum;
	private JTextFieldPersonalizado entBairro;

	
	private String logradouro, bairro, cidade, estado;
	private int num, cep;
	private JTextFieldPersonalizado entCep;
	private JTextFieldPersonalizado entCidade;
	private JTextFieldPersonalizado entEstado;
	private JTextFieldPersonalizado entIE;
	private JTextFieldPersonalizado entBanco;
	private JTextFieldPersonalizado entCodBanco;
	private JTextFieldPersonalizado entConta;
	private JTextFieldPersonalizado entAgencia;
	private JTextFieldPersonalizado entCpfTitular;
	private JTextFieldPersonalizado entNome;
	private JTextFieldPersonalizado entSobrenome;
	private JTextFieldPersonalizado entNascimento;
	private JTextFieldPersonalizado entCpf;
	private JTextFieldPersonalizado entRg;
	private JTextFieldPersonalizado entOcupacao;
	private JTextFieldPersonalizado entCNPJ;
	private JTextFieldPersonalizado entRazaoSocial;
	private JTextFieldPersonalizado entNomeFantasia;
	private JTextFieldPersonalizado entStatus;
	private JTextFieldPersonalizado entDescricao;
	private JTextFieldPersonalizado entAtividadesPri;
	private JTextFieldPersonalizado entAtividadeSec;
	private JTextFieldPersonalizado entStatusIE;
	
    private String uf;
    private JTextFieldPersonalizado entNomeEmpresarial;
    private JTextFieldPersonalizado entPorte;
    private JTextFieldPersonalizado entCnae;
	private String tipoIdentificacao;
	private JTextFieldPersonalizado entApelido;
	private JTextFieldPersonalizado entCelularContato;
	private JTextFieldPersonalizado entNomeContato;
	private JTextFieldPersonalizado entFixoContato;
	private JTextFieldPersonalizado entEmailContato;
	private JTable table;
    JComboBox cBPessoa = new JComboBox();

	private JPanel painel_table_cb;
	private JTable table_cb;
	private JTable table_veiculos;

	
	private DefaultTableModel modelo = new DefaultTableModel();
	private DefaultTableModel modelo_veiculos = new DefaultTableModel();

	private DefaultTableModel modelo_cb = new DefaultTableModel();
	private JTextFieldPersonalizado entDescricaoContato;
	private JTextFieldPersonalizado entObservacaoContato;
	private JTextFieldPersonalizado entNomeContaBancaria;

	
	private JTextFieldPersonalizado entRegistroTrator ,entPlacaTrator, entMunicipioTrator, entEstadoTrator,
	entEstadoReboque1, entMunicipioReboque1, entPlacaReboque1, entRegistroReboque1,
	entRegistroReboque2, entPlacaReboque2, entMunicipioReboque2 , entEstadoReboque2,
	entRegistroTransportador;
	private JCheckBox chkBoxReboque2, chkBoxReboque1;


	private JPanel panelReboque1, panelReboque2; 
	  
	
	 
	 
	private JComboBox cBEixosTrator, cBTipoTrator,   cBTipoReboque1, cBEixosReboque1, cBTipoReboque2, cBEixosReboque2;
	 
	private JButton btnSalvar;

	ArrayList<Integer> contatos_excluir = new ArrayList<>();
	ArrayList<Integer> contas_excluir = new ArrayList<>();
	
	ArrayList<Integer> veiculos_excluir = new ArrayList<>();
	
	
	CadastroCliente cliente_cadastrar = new CadastroCliente();
	CadastroCliente cliente_atualizar = new CadastroCliente();
	
	private JLabel lblCodigoGerado, lblCodigo;
	private TelaCadastroTransportadores isto;
	private CadastroCliente cliente_global;
	public TelaCadastroTransportadores(int flag_tipo_tela, CadastroCliente cliente, TelaTransportadores telaPai, Window janela_pai) {
		
		cliente_global = cliente;
		getContentPane().setFont(new Font("Arial", Font.BOLD, 18));
		getContentPane().setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.BOLD, 18));
		setForeground(new Color(255, 255, 255));
		

		getDadosGlobais();

		//setModal(true);

		TelaCadastroTransportadores isto = this;
		
		setResizable(false);
	
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(flag_tipo_tela == 0)
		setTitle("E-Contract - Novo Transportador");
		else if(flag_tipo_tela == 1)
			setTitle("E-Contract - Editar Transportador");
		
			
			
		setBounds(100, 100, 810, 589);
		
		
		//configuracao de paineis
		//painel pai
		painelPrincipal = new JTabbedPane();
		painelPrincipal.setFont(new Font("Arial", Font.BOLD, 14));
		//painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		//painelPrincipal.setBackground(Color.BLACK);
		painelPrincipal.setForeground(Color.BLACK);
		painelDadosIniciais.setBackground(Color.WHITE);
		
		
		painelDadosIniciais.setLayout(null);
		/*URL url = getClass().getResource("/imagens/fundo_azul_escuro.jpg");
	 		ImageIcon img = new ImageIcon(url);
	 		painelDadosIniciais.setImg(img);
	 		painelDadosIniciais.repaint();
	 		*/
	 		
		
		//painel filho1
		//painelDadosIniciais.setBackground(Color.WHITE);
		
		
		//painel de dados bancarios
         painelDadosBancarios.setBackground(new Color(255, 255, 255));
		 painelDadosBancarios.setLayout(null);

		 //painel finalizar
		 painelFinalizar.setBackground(new Color(255, 255, 255));
		 painelFinalizar.setLayout(null);

		 //painel de contatos
		 painelContato.setBackground(new Color(255, 255, 255));
		 painelContato.setLayout(null);
		 
		 //painel de veiculos
		 painelVeiculos.setBackground(new Color(255, 255, 255));
		 painelVeiculos.setLayout(null);

		//adiciona o painel filho1 no painel principal
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		 
	 //adiciona o painel de enderecos
		 painelEmpresa.setBackground(new Color(255, 255, 255));
		 painelEmpresa.setLayout(null);
		 		//adiciona o painel empresa no painel principal
		 painelPrincipal.addTab("Empresa", painelEmpresa);
		//adiciona o painel de dados ao painel principal
		 painelPrincipal.addTab("Dados Bancarios", painelDadosBancarios);
		 
		 //adicionar o painel de contato ao painel principal
		 painelPrincipal.addTab("Dados Contato", painelContato);
		 
		 //adicionar o painel de veiculos ao painel principal
		 painelPrincipal.addTab("Veiculos", painelVeiculos);
		 
		 JPanel panelComportaVeiculos = new JPanel();
		 panelComportaVeiculos.setBounds(36, 11, 743, 132);
		 painelVeiculos.add(panelComportaVeiculos);
		 
		 
		 
			table_veiculos = new JTable(modelo_veiculos);
			table_veiculos.setBackground(new Color(255, 255, 255));
			
			modelo_veiculos.addColumn("ID");
			modelo_veiculos.addColumn("RNTRC");
			modelo_veiculos.addColumn("Placa");
			modelo_veiculos.addColumn("Eixos");
			modelo_veiculos.addColumn("Tipo");
			modelo_veiculos.addColumn("Cidade");
			modelo_veiculos.addColumn("Estado");

	        
	      

			table_veiculos.getColumnModel().getColumn(0)
	        .setPreferredWidth(20);
			table_veiculos.getColumnModel().getColumn(1)
	        .setPreferredWidth(20);
			table_veiculos.getColumnModel().getColumn(2)
	        .setPreferredWidth(130);
			table_veiculos.getColumnModel().getColumn(3)
	        .setPreferredWidth(30);
			table_veiculos.getColumnModel().getColumn(4)
	        .setPreferredWidth(30);
			table_veiculos.getColumnModel().getColumn(5)
	        .setPreferredWidth(100);
	    
	    	modelo_veiculos.setNumRows(0);
		 panelComportaVeiculos.setLayout(null);
	    	
		 JScrollPane scrollPaneVeiculos = new JScrollPane(table_veiculos);
		 scrollPaneVeiculos.setBounds(10, 5, 723, 116);
		 panelComportaVeiculos.add(scrollPaneVeiculos);
		 
		 JPanel panel_1 = new JPanel();
		 panel_1.setBackground(Color.WHITE);
		 panel_1.setBounds(10, 188, 769, 320);
		 painelVeiculos.add(panel_1);
		 panel_1.setLayout(null);
		 
		 JLabel lblNome_1_2_1_1 = new JLabel("Dados do Trator");
		 lblNome_1_2_1_1.setBounds(0, 0, 181, 33);
		 panel_1.add(lblNome_1_2_1_1);
		 lblNome_1_2_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2_1_1.setForeground(Color.BLACK);
		 lblNome_1_2_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2_1_1.setBackground(Color.ORANGE);
		 
		 JLabel lblNome_1_2 = new JLabel("Rntrc:");
		 lblNome_1_2.setBounds(10, 33, 65, 33);
		 panel_1.add(lblNome_1_2);
		 lblNome_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2.setForeground(Color.BLACK);
		 lblNome_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2.setBackground(Color.ORANGE);
		 
		 JLabel lblNome_1_2_1 = new JLabel("Placa:");
		 lblNome_1_2_1.setBounds(10, 72, 65, 33);
		 panel_1.add(lblNome_1_2_1);
		 lblNome_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2_1.setForeground(Color.BLACK);
		 lblNome_1_2_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2_1.setBackground(Color.ORANGE);
		 
		 JLabel lblNome_1_2_1_2_1 = new JLabel("Eixos:");
		 lblNome_1_2_1_2_1.setBounds(18, 116, 57, 33);
		 panel_1.add(lblNome_1_2_1_2_1);
		 lblNome_1_2_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2_1_2_1.setForeground(Color.BLACK);
		 lblNome_1_2_1_2_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2_1_2_1.setBackground(Color.ORANGE);
		 
		 JLabel lblNome_1_2_1_2 = new JLabel("Tipo:");
		 lblNome_1_2_1_2.setBounds(18, 149, 57, 33);
		 panel_1.add(lblNome_1_2_1_2);
		 lblNome_1_2_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2_1_2.setForeground(Color.BLACK);
		 lblNome_1_2_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2_1_2.setBackground(Color.ORANGE);
		 
		 JLabel lblNome_1_2_1_2_1_1 = new JLabel("Cidade:");
		 lblNome_1_2_1_2_1_1.setBounds(18, 185, 57, 33);
		 panel_1.add(lblNome_1_2_1_2_1_1);
		 lblNome_1_2_1_2_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2_1_2_1_1.setForeground(Color.BLACK);
		 lblNome_1_2_1_2_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2_1_2_1_1.setBackground(Color.ORANGE);
		 
		 JLabel lblNome_1_2_1_2_1_1_1 = new JLabel("UF:");
		 lblNome_1_2_1_2_1_1_1.setBounds(18, 229, 57, 33);
		 panel_1.add(lblNome_1_2_1_2_1_1_1);
		 lblNome_1_2_1_2_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1_2_1_2_1_1_1.setForeground(Color.BLACK);
		 lblNome_1_2_1_2_1_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1_2_1_2_1_1_1.setBackground(Color.ORANGE);
		 
		 
		  entRegistroTrator = new JTextFieldPersonalizado();
		  entRegistroTrator.setForeground(Color.black);
		 entRegistroTrator.setBounds(81, 33, 153, 33);
		 panel_1.add(entRegistroTrator);
		 
		  entPlacaTrator = new JTextFieldPersonalizado();
		  entPlacaTrator.setForeground(Color.black);
		 entPlacaTrator.setBounds(81, 75, 153, 33);
		 panel_1.add(entPlacaTrator);
		 
		
		  cBEixosTrator = new JComboBox();
		 cBEixosTrator.setBounds(81, 123, 153, 22);
		 for(int i = 2; i<=9; i++) {
		 cBEixosTrator.addItem(Integer.toString(i));
		 
		 }

		 panel_1.add(cBEixosTrator);
		 
		  cBTipoTrator = new JComboBox();
		  cBTipoTrator.setBounds(81, 156, 153, 22);
		  cBTipoTrator.addItem("Graneleiro");
		  cBTipoTrator.addItem("Caçamba");
		  cBTipoTrator.addItem("Trator");


		 panel_1.add(cBTipoTrator);
		 
		  entMunicipioTrator = new JTextFieldPersonalizado();
		 entMunicipioTrator.setBounds(81, 185, 153, 33);
		 entMunicipioTrator.setForeground(Color.black);
		 panel_1.add(entMunicipioTrator);
		 
		 JButton btnAdicionarVeiculo = new JButton("Adicionar");
		 btnAdicionarVeiculo.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		String id = "0000";
		 		String registro_trator, placa_trator, eixos_trator, tipo_trator, cidade_trator, uf_trator;
		 		String registro_reboque1, placa_reboque1, eixos_reboque1, tipo_reboque1, cidade_reboque1, uf_reboque1;
		 		String registro_reboque2, placa_reboque2, eixos_reboque2, tipo_reboque2, cidade_reboque2, uf_reboque2;
		 	     CadastroCliente.Veiculo veiculo = new CadastroCliente.Veiculo();

		 		
			

		 		registro_trator = entRegistroTrator.getText();
		 		placa_trator = entPlacaTrator.getText();
		 		eixos_trator = cBEixosTrator.getSelectedItem().toString();
		 		tipo_trator = cBTipoTrator.getSelectedItem().toString();
		 		cidade_trator = entRegistroTrator.getText();
		 		uf_trator = entEstadoTrator.getText();
		 		
		 		veiculo.setId_veiculo(0);
		 		veiculo.setRegistro_trator(registro_trator);
		 		veiculo.setPlaca_trator(placa_trator);
		 		veiculo.setEixos_trator(eixos_trator);
		 		veiculo.setTipo_trator(tipo_trator);
		 		veiculo.setCidade_trator(cidade_trator);
		 		veiculo.setUf_trator(uf_trator);
		 		
		 		if(chkBoxReboque1.isSelected()) {
		 			registro_reboque1 = entRegistroReboque1.getText();
			 		placa_reboque1 = entPlacaReboque1.getText();
			 		eixos_reboque1 = cBEixosReboque1.getSelectedItem().toString();
			 		tipo_reboque1 = cBTipoReboque1.getSelectedItem().toString();
			 		cidade_reboque1 = entRegistroReboque1.getText();
			 		uf_reboque1 = entEstadoReboque1.getText();
			 		
					
			 		veiculo.setRegistro_reboque1(registro_reboque1);
			 		veiculo.setPlaca_reboque1(placa_reboque1);
			 		veiculo.setEixos_reboque1(eixos_reboque1);
			 		veiculo.setTipo_reboque1(tipo_reboque1);
			 		veiculo.setCidade_reboque1(cidade_reboque1);
			 		veiculo.setUf_reboque1(uf_reboque1);
			 		
		 		}
		 		
		 		if(chkBoxReboque2.isSelected()) {
		 			registro_reboque2 = entRegistroReboque2.getText();
			 		placa_reboque2 = entPlacaReboque2.getText();
			 		eixos_reboque2 = cBEixosReboque2.getSelectedItem().toString();
			 		tipo_reboque2 = cBTipoReboque2.getSelectedItem().toString();
			 		cidade_reboque2 = entRegistroReboque2.getText();
			 		uf_reboque2 = entEstadoReboque2.getText();
			 		

			 		veiculo.setRegistro_reboque2(registro_reboque2);
			 		veiculo.setPlaca_reboque2(placa_reboque2);
			 		veiculo.setEixos_reboque2(eixos_reboque2);
			 		veiculo.setTipo_reboque2(tipo_reboque2);
			 		veiculo.setCidade_reboque2(cidade_reboque2);
			 		veiculo.setUf_reboque2(uf_reboque2);
		 		}
		 		
		 		
		 	
				modelo_veiculos.addRow(new Object[]{id, registro_trator, placa_trator, 
						eixos_trator, tipo_trator, cidade_trator, uf_trator});
		 		
		 	     
		 	     

		 		
		 		
		 		
		 	}
		 });
		 btnAdicionarVeiculo.setBounds(645, 286, 89, 23);
		 panel_1.add(btnAdicionarVeiculo);
		 
		  entEstadoTrator = new JTextFieldPersonalizado();
		 entEstadoTrator.setBounds(81, 229, 153, 33);
		 entEstadoTrator.setForeground(Color.black);
		 panel_1.add(entEstadoTrator);
		 
		 chkBoxReboque2  = new JCheckBox("Reboque 2");
		 chkBoxReboque2.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		

         		if(chkBoxReboque2.isSelected()) {
         			
         			
         			panelReboque2.setVisible(true);

         		}else {
         			
         			
         			panelReboque2.setVisible(false);
         	
         		}
		 		
		 	}
		 });
		 chkBoxReboque2.setBounds(558, 7, 105, 23);
		 chkBoxReboque2.setVisible(false);
		 panel_1.add(chkBoxReboque2);
		 
		  panelReboque1 = new JPanel();
		 panelReboque1.setBackground(Color.WHITE);
		 panelReboque1.setBounds(244, 33, 241, 247);
		 panel_1.add(panelReboque1);
		 panelReboque1.setVisible(false);
		 panelReboque1.setEnabled(false);
		 panelReboque1.setLayout(null);
		  
		  JLabel lblNome_1_2_2 = new JLabel("Rntrc:");
		  lblNome_1_2_2.setBounds(10, 11, 65, 33);
		  panelReboque1.add(lblNome_1_2_2);
		  lblNome_1_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		  lblNome_1_2_2.setForeground(Color.BLACK);
		  lblNome_1_2_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		  lblNome_1_2_2.setBackground(Color.ORANGE);
		  
		   entRegistroReboque1 = new JTextFieldPersonalizado();
		   entRegistroReboque1.setBounds(81, 11, 153, 33);
		   panelReboque1.add(entRegistroReboque1);
		   entRegistroReboque1.setForeground(Color.black);
		   
		   JLabel lblNome_1_2_1_3 = new JLabel("Placa:");
		   lblNome_1_2_1_3.setBounds(10, 50, 65, 33);
		   panelReboque1.add(lblNome_1_2_1_3);
		   lblNome_1_2_1_3.setHorizontalAlignment(SwingConstants.TRAILING);
		   lblNome_1_2_1_3.setForeground(Color.BLACK);
		   lblNome_1_2_1_3.setFont(new Font("Arial Black", Font.PLAIN, 14));
		   lblNome_1_2_1_3.setBackground(Color.ORANGE);
		   
		   
	
		    entPlacaReboque1 = new JTextFieldPersonalizado();
		    entPlacaReboque1.setBounds(81, 53, 153, 33);
		    panelReboque1.add(entPlacaReboque1);
		    entPlacaReboque1.setForeground(Color.black);
		    
		    JLabel lblNome_1_2_1_2_1_2 = new JLabel("Eixos:");
		    lblNome_1_2_1_2_1_2.setBounds(10, 94, 65, 33);
		    panelReboque1.add(lblNome_1_2_1_2_1_2);
		    lblNome_1_2_1_2_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		    lblNome_1_2_1_2_1_2.setForeground(Color.BLACK);
		    lblNome_1_2_1_2_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		    lblNome_1_2_1_2_1_2.setBackground(Color.ORANGE);
		    
		     cBEixosReboque1 = new JComboBox();
		     cBEixosReboque1.setBounds(81, 101, 153, 22);
		     for(int i = 2; i<=9; i++) {
		    	 cBEixosReboque1.addItem(Integer.toString(i));
				 
				 }

		     panelReboque1.add(cBEixosReboque1);
		     
		     JLabel lblNome_1_2_1_2_2 = new JLabel("Tipo:");
		     lblNome_1_2_1_2_2.setBounds(10, 127, 65, 33);
		     panelReboque1.add(lblNome_1_2_1_2_2);
		     lblNome_1_2_1_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		     lblNome_1_2_1_2_2.setForeground(Color.BLACK);
		     lblNome_1_2_1_2_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		     lblNome_1_2_1_2_2.setBackground(Color.ORANGE);
		     
		      cBTipoReboque1 = new JComboBox();
		      cBTipoReboque1.setBounds(81, 134, 153, 22);
		      cBTipoReboque1.addItem("Graneleiro");
		      cBTipoReboque1.addItem("Caçamba");
		      panelReboque1.add(cBTipoReboque1);
		      
		      JLabel lblNome_1_2_1_2_1_1_2 = new JLabel("Cidade:");
		      lblNome_1_2_1_2_1_1_2.setBounds(10, 163, 65, 33);
		      panelReboque1.add(lblNome_1_2_1_2_1_1_2);
		      lblNome_1_2_1_2_1_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		      lblNome_1_2_1_2_1_1_2.setForeground(Color.BLACK);
		      lblNome_1_2_1_2_1_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		      lblNome_1_2_1_2_1_1_2.setBackground(Color.ORANGE);
		      
		       entMunicipioReboque1 = new JTextFieldPersonalizado();
		       entMunicipioReboque1.setBounds(81, 163, 153, 33);
		       panelReboque1.add(entMunicipioReboque1);
		       entMunicipioReboque1.setForeground(Color.black);
		       
		
		        entEstadoReboque1 = new JTextFieldPersonalizado();
		        entEstadoReboque1.setBounds(81, 207, 153, 33);
		        panelReboque1.add(entEstadoReboque1);
		        entEstadoReboque1.setForeground(Color.black);
		        
		        JLabel lblNome_1_2_1_2_1_1_1_1 = new JLabel("UF:");
		        lblNome_1_2_1_2_1_1_1_1.setBounds(10, 207, 65, 33);
		        panelReboque1.add(lblNome_1_2_1_2_1_1_1_1);
		        lblNome_1_2_1_2_1_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		        lblNome_1_2_1_2_1_1_1_1.setForeground(Color.BLACK);
		        lblNome_1_2_1_2_1_1_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		        lblNome_1_2_1_2_1_1_1_1.setBackground(Color.ORANGE);
		        
		        
		         chkBoxReboque1 = new JCheckBox("Reboque 1");
		         chkBoxReboque1.setBounds(287, 7, 175, 23);
		         panel_1.add(chkBoxReboque1);
		         chkBoxReboque1.setSelected(false);
		         
		          panelReboque2 = new JPanel();
		         panelReboque2.setBounds(495, 33, 257, 247);
		         panel_1.add(panelReboque2);
		         panelReboque2.setVisible(false);
		         panelReboque2.setBackground(Color.WHITE);
		         panelReboque2.setLayout(null);
		         
		         JLabel lblNome_1_2_1_2_1_1_1_1_1 = new JLabel("UF:");
		         lblNome_1_2_1_2_1_1_1_1_1.setBounds(10, 207, 65, 33);
		         panelReboque2.add(lblNome_1_2_1_2_1_1_1_1_1);
		         lblNome_1_2_1_2_1_1_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		         lblNome_1_2_1_2_1_1_1_1_1.setForeground(Color.BLACK);
		         lblNome_1_2_1_2_1_1_1_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		         lblNome_1_2_1_2_1_1_1_1_1.setBackground(Color.ORANGE);
		         
		          entMunicipioReboque2 = new JTextFieldPersonalizado();
		          entMunicipioReboque2.setBounds(81, 163, 153, 33);
		          panelReboque2.add(entMunicipioReboque2);
		          entMunicipioReboque2.setForeground(Color.black);
		          
		           entEstadoReboque2 = new JTextFieldPersonalizado();
		           entEstadoReboque2.setBounds(81, 207, 153, 33);
		           panelReboque2.add(entEstadoReboque2);
		           
		           		  entEstadoReboque2.setForeground(Color.black);
		           		  
		           		  JLabel lblNome_1_2_1_2_1_1_2_1 = new JLabel("Cidade:");
		           		  lblNome_1_2_1_2_1_1_2_1.setBounds(10, 163, 65, 33);
		           		  panelReboque2.add(lblNome_1_2_1_2_1_1_2_1);
		           		  lblNome_1_2_1_2_1_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		           		  lblNome_1_2_1_2_1_1_2_1.setForeground(Color.BLACK);
		           		  lblNome_1_2_1_2_1_1_2_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		           		  lblNome_1_2_1_2_1_1_2_1.setBackground(Color.ORANGE);
		           		  
		           		  JLabel lblNome_1_2_1_2_2_1 = new JLabel("Tipo:");
		           		  lblNome_1_2_1_2_2_1.setBounds(10, 127, 65, 33);
		           		  panelReboque2.add(lblNome_1_2_1_2_2_1);
		           		  lblNome_1_2_1_2_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		           		  lblNome_1_2_1_2_2_1.setForeground(Color.BLACK);
		           		  lblNome_1_2_1_2_2_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		           		  lblNome_1_2_1_2_2_1.setBackground(Color.ORANGE);
		           		  
		           		  JLabel lblNome_1_2_1_2_1_2_1 = new JLabel("Eixos:");
		           		  lblNome_1_2_1_2_1_2_1.setBounds(10, 94, 65, 33);
		           		  panelReboque2.add(lblNome_1_2_1_2_1_2_1);
		           		  lblNome_1_2_1_2_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		           		  lblNome_1_2_1_2_1_2_1.setForeground(Color.BLACK);
		           		  lblNome_1_2_1_2_1_2_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		           		  lblNome_1_2_1_2_1_2_1.setBackground(Color.ORANGE);
		           		  
		           		  JLabel lblNome_1_2_1_3_1 = new JLabel("Placa:");
		           		  lblNome_1_2_1_3_1.setBounds(10, 50, 65, 33);
		           		  panelReboque2.add(lblNome_1_2_1_3_1);
		           		  lblNome_1_2_1_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		           		  lblNome_1_2_1_3_1.setForeground(Color.BLACK);
		           		  lblNome_1_2_1_3_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		           		  lblNome_1_2_1_3_1.setBackground(Color.ORANGE);
		           		  
		           		  JLabel lblNome_1_2_2_1 = new JLabel("Rntrc:");
		           		  lblNome_1_2_2_1.setBounds(10, 11, 65, 33);
		           		  panelReboque2.add(lblNome_1_2_2_1);
		           		  lblNome_1_2_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		           		  lblNome_1_2_2_1.setForeground(Color.BLACK);
		           		  lblNome_1_2_2_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		           		  lblNome_1_2_2_1.setBackground(Color.ORANGE);
		           		  
		           		   entRegistroReboque2 = new JTextFieldPersonalizado();
		           		   entRegistroReboque2.setBounds(81, 11, 153, 33);
		           		   panelReboque2.add(entRegistroReboque2);
		           		   entRegistroReboque2.setForeground(Color.black);
		           		   
		           		    entPlacaReboque2 = new JTextFieldPersonalizado();
		           		    entPlacaReboque2.setBounds(81, 53, 153, 33);
		           		    panelReboque2.add(entPlacaReboque2);
		           		    
		           		    		  entPlacaReboque2.setForeground(Color.black);
		           		    		  
		           		    		   cBEixosReboque2 = new JComboBox();
		           		    		   cBEixosReboque2.setBounds(81, 101, 153, 22);
		           		    		  for(int i = 2; i<=9; i++) {
		           		    			cBEixosReboque2.addItem(Integer.toString(i));
		           						 
		           						 }

		           		    		   panelReboque2.add(cBEixosReboque2);
		           		    		   
		           		    		    cBTipoReboque2 = new JComboBox();
		           		    		    cBTipoReboque2.setBounds(81, 134, 153, 22);
		           		    		 cBTipoReboque2.addItem("Graneleiro");
		           		    		cBTipoReboque2.addItem("Caçamba");
		           		    		    panelReboque2.add(cBTipoReboque2);
		           		    		    
		         chkBoxReboque1.addActionListener(new ActionListener() {
		         	public void actionPerformed(ActionEvent e) {
		         		
		         		if(chkBoxReboque1.isSelected()) {
		         			
		         			chkBoxReboque1.setSelected(true);
		         			panelReboque1.setVisible(true);
		         			chkBoxReboque2.setVisible(true);

		         		}else {
		         			
		         			chkBoxReboque1.setSelected(false);
		         			panelReboque1.setVisible(false);
		         			chkBoxReboque2.setVisible(false);
		         			panelReboque2.setVisible(false);
		         		}
		         		
		         		
		         	}
		         });
		    
		 JButton btnExcluirVeiculo = new JButton("Excluir");
		 btnExcluirVeiculo.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		//veiculos_excluir
		 		int indiceDaLinha = table_veiculos.getSelectedRow();
				if(flag_tipo_tela == 1)
				{
					
						
						String id_excluir = table_veiculos.getValueAt(indiceDaLinha, 0).toString();
						if(id_excluir.equals("0000"))
						{
						}
						else
						{
							//adiciona um veiculo ja existe na lista de veiculos para excluir
							JOptionPane.showMessageDialog(isto, "Veiculo adicionado para excluosao");
							veiculos_excluir.add(Integer.parseInt(id_excluir));

						}
						

						((DefaultTableModel) table_veiculos.getModel()).removeRow(indiceDaLinha); table_veiculos.repaint(); table_veiculos.validate(); 
						
					
				}
				else {
			
				((DefaultTableModel) table_veiculos.getModel()).removeRow(indiceDaLinha); table_veiculos.repaint(); table_veiculos.validate(); 
				
				}
			}
		 	
		 });
		 btnExcluirVeiculo.setBounds(690, 154, 89, 23);
		 painelVeiculos.add(btnExcluirVeiculo);


		 JLabel lblCelular = new JLabel("Celular:");
		 lblCelular.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblCelular.setForeground(Color.BLACK);
		 lblCelular.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblCelular.setBackground(Color.ORANGE);
		 lblCelular.setBounds(13, 307, 93, 33);
		 painelContato.add(lblCelular);
		 
		 entCelularContato = new JTextFieldPersonalizado();
		 entCelularContato.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyTyped(KeyEvent evt) {
		 		String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entCelularContato.getText();
				if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();//aciona esse propriedade para eliminar a ação do evento
				}else {
				if(texto.length()==1 && evt.getKeyChar() != '\b'){
					entCelularContato.setText("(" + entCelularContato.getText());
				}
				if(texto.length()==3  && evt.getKeyChar() != '\b'){
					entCelularContato.setText(entCelularContato.getText().concat(") "));
				}
				
				if(texto.length()==6 && evt.getKeyChar() != '\b'){
					entCelularContato.setText(entCelularContato.getText().concat(" "));
				}
				
				if(texto.length()==11 && evt.getKeyChar() != '\b'){
					entCelularContato.setText(entCelularContato.getText().concat("-"));
				}
				
				if(entCelularContato.getText().length()>=16){
					//if para saber se precisa verificar também o tamanho da string do campo
					// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
					evt.consume();
					entCelularContato.setText(entCelularContato.getText().substring(0,16));
				}
			
			}
		 	}
		 });
		 
		
		 entCelularContato.setForeground(Color.BLACK);
		 entCelularContato.setColumns(10);
		 entCelularContato.setBounds(116, 309, 220, 33);
		 painelContato.add(entCelularContato);
		 
		 JLabel lblNome_1 = new JLabel("Nome:");
		 lblNome_1.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblNome_1.setForeground(Color.BLACK);
		 lblNome_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblNome_1.setBackground(Color.ORANGE);
		 lblNome_1.setBounds(20, 233, 93, 33);
		 painelContato.add(lblNome_1);
		 
		 JLabel lblCargo = new JLabel("Cargo:");
		 lblCargo.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblCargo.setForeground(Color.BLACK);
		 lblCargo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblCargo.setBackground(Color.ORANGE);
		 lblCargo.setBounds(13, 272, 93, 33);
		 painelContato.add(lblCargo);
		 
		 entNomeContato = new JTextFieldPersonalizado();
		 entNomeContato.setForeground(Color.BLACK);
		 entNomeContato.setColumns(10);
		 entNomeContato.setBounds(116, 235, 220, 33);
		 painelContato.add(entNomeContato);
		 
		 JComboBox cBCargo = new JComboBox();
		 cBCargo.setBounds(116, 274, 220, 31);
		 cBCargo.addItem("Secretaria");
		 cBCargo.addItem("Auxiliar de Escritorio");
		 cBCargo.addItem("Contato Particular");
		 painelContato.add(cBCargo);
		 
		 JLabel lblFixo = new JLabel("Fixo:");
		 lblFixo.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblFixo.setForeground(Color.BLACK);
		 lblFixo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblFixo.setBackground(Color.ORANGE);
		 lblFixo.setBounds(13, 351, 93, 33);
		 painelContato.add(lblFixo);
		 
		 entFixoContato = new JTextFieldPersonalizado();
		 entFixoContato.setForeground(Color.BLACK);
		 entFixoContato.setColumns(10);
		 entFixoContato.setBounds(116, 351, 220, 33);
		 painelContato.add(entFixoContato);
		 
		 JLabel lblEmail = new JLabel("E-mail:");
		 lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblEmail.setForeground(Color.BLACK);
		 lblEmail.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblEmail.setBackground(Color.ORANGE);
		 lblEmail.setBounds(13, 395, 93, 33);
		 painelContato.add(lblEmail);
		 
		 entEmailContato = new JTextFieldPersonalizado();
		 entEmailContato.setForeground(Color.BLACK);
		 entEmailContato.setColumns(10);
		 entEmailContato.setBounds(116, 395, 220, 33);
		 painelContato.add(entEmailContato);
		 
		 JPanel panel = new JPanel();
		 panel.setBounds(38, 65, 724, 138);
		 painelContato.add(panel);
		 

			table = new JTable(modelo);
			table.setBackground(new Color(255, 255, 255));
			modelo.addColumn("id");
			modelo.addColumn("Nome");
	        modelo.addColumn("Cargo");
	        modelo.addColumn("Celular");
	        modelo.addColumn("Fixo");
	        modelo.addColumn("E-mail");
	        modelo.addColumn("Descrição");
	        modelo.addColumn("Observação");

	        
	      

	        table.getColumnModel().getColumn(0)
	        .setPreferredWidth(20);
	        table.getColumnModel().getColumn(1)
	        .setPreferredWidth(20);
	        table.getColumnModel().getColumn(2)
	        .setPreferredWidth(130);
	        table.getColumnModel().getColumn(3)
	        .setPreferredWidth(30);
	        table.getColumnModel().getColumn(4)
	        .setPreferredWidth(30);
	        table.getColumnModel().getColumn(5)
	        .setPreferredWidth(100);
	    
	        panel.setLayout(null);
	    	modelo.setNumRows(0);
	    	
	    	
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(10, 5, 704, 122);
	        scrollPane.setAutoscrolls(true);
	        scrollPane.setBackground(new Color(255, 255, 255));
			panel.add(scrollPane);
			
			JButton btnAdicionarNovoContato = new JButton("Adicionar");
			btnAdicionarNovoContato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String nome, cargo, celular, fixo, email, observacao, descricao, id;
					
					id = "0000";
					nome = entNomeContato.getText().toString();
					cargo = cBCargo.getSelectedItem().toString();
					celular = entCelularContato.getText().toString();
					fixo = entFixoContato.getText().toString();
					email = entEmailContato.getText().toString();
					observacao = entObservacaoContato.getText().toString();
					descricao = entDescricaoContato.getText().toString();
					
				      celular = celular.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
					if( celular.length() != 11) {
						JOptionPane.showMessageDialog(isto, "Contato com número de celular incorreto");
					}else {
						
						modelo.addRow(new Object[]{id, nome, cargo, 
							       celular, fixo, email, descricao, observacao});
					}
					
				
				
					
					
				}
			});
			btnAdicionarNovoContato.setBounds(673, 415, 89, 33);
			painelContato.add(btnAdicionarNovoContato);
			
			JButton btnExcluirContato = new JButton("Excluir");
			btnExcluirContato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int indiceDaLinha = table.getSelectedRow();
					if(flag_tipo_tela == 0 || flag_tipo_tela == 6)
					{
						
							
							String id_excluir = table.getValueAt(indiceDaLinha, 0).toString();
							if(id_excluir.equals("0000"))
							{
							}
							else
							{
								contatos_excluir.add(Integer.parseInt(id_excluir));

							}
							

							((DefaultTableModel) table.getModel()).removeRow(indiceDaLinha); table.repaint(); table.validate(); 
							
						
					}
					else {
				
					((DefaultTableModel) table.getModel()).removeRow(indiceDaLinha); table.repaint(); table.validate(); 
					
					}
				}
			});
			btnExcluirContato.setHorizontalAlignment(SwingConstants.LEADING);
			btnExcluirContato.setBounds(699, 210, 63, 23);
			painelContato.add(btnExcluirContato);
			
			JLabel lblNome_1_1 = new JLabel("Descrição:");
			lblNome_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNome_1_1.setForeground(Color.BLACK);
			lblNome_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lblNome_1_1.setBackground(Color.ORANGE);
			lblNome_1_1.setBounds(345, 233, 93, 33);
			painelContato.add(lblNome_1_1);
			
			entDescricaoContato = new JTextFieldPersonalizado();
			entDescricaoContato.setForeground(Color.BLACK);
			entDescricaoContato.setColumns(10);
			entDescricaoContato.setBounds(444, 233, 220, 72);
			painelContato.add(entDescricaoContato);
			
			JLabel lblNome_1_1_1 = new JLabel("Observação:");
			lblNome_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNome_1_1_1.setForeground(Color.BLACK);
			lblNome_1_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lblNome_1_1_1.setBackground(Color.ORANGE);
			lblNome_1_1_1.setBounds(332, 318, 107, 33);
			painelContato.add(lblNome_1_1_1);
			
			entObservacaoContato = new JTextFieldPersonalizado();
			entObservacaoContato.setForeground(Color.BLACK);
			entObservacaoContato.setColumns(10);
			entObservacaoContato.setBounds(444, 326, 220, 72);
			painelContato.add(entObservacaoContato);
			
			JLabel lblCadastro_1 = new JLabel(" ----- Cadastro / Dados de Contatos");
			lblCadastro_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblCadastro_1.setForeground(Color.BLACK);
			lblCadastro_1.setFont(new Font("Arial", Font.PLAIN, 14));
			lblCadastro_1.setBackground(Color.ORANGE);
			lblCadastro_1.setBounds(0, 0, 247, 33);
			lblCadastro_1.setHorizontalAlignment(JLabel.LEFT);

			painelContato.add(lblCadastro_1);

		//adiciona o painel finalizar no painel principal
		 painelPrincipal.addTab("Finalizar Cadastro", painelFinalizar);
		 
		 lblCodigoGerado = new JLabel("Codigo Gerado:");
		 lblCodigoGerado.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblCodigoGerado.setForeground(Color.BLACK);
		 lblCodigoGerado.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblCodigoGerado.setBackground(Color.ORANGE);
		 lblCodigoGerado.setBounds(77, 97, 150, 33);
		 painelFinalizar.add(lblCodigoGerado);
		 
		 JLabel lblNewLabel = new JLabel("Revise os dados nas telas anteriores, quando tiver tudo pronto, clique em 'Salvar' para realizar o cadastro no Banco de Dados");
		 lblNewLabel.setBounds(22, 11, 753, 32);
		 painelFinalizar.add(lblNewLabel);
		 
		 JLabel lblApelido = new JLabel("Apelido:");
		 lblApelido.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblApelido.setForeground(Color.BLACK);
		 lblApelido.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblApelido.setBackground(Color.ORANGE);
		 lblApelido.setBounds(77, 188, 150, 33);
		 painelFinalizar.add(lblApelido);
		 
		 entApelido = new JTextFieldPersonalizado();
		 entApelido.setForeground(Color.BLACK);
		 entApelido.setColumns(10);
		 entApelido.setBounds(237, 188, 220, 33);
		 painelFinalizar.add(entApelido);
		 
		  lblCodigo = new JLabel("0000000000");
		 lblCodigo.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblCodigo.setForeground(Color.BLACK);
		 lblCodigo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblCodigo.setBackground(Color.ORANGE);
		 lblCodigo.setBounds(237, 97, 150, 33);
		 painelFinalizar.add(lblCodigo);
		 
		  btnSalvar = new JButton("Salvar");
		 btnSalvar.setBounds(963, 502, 89, 23);
		 painelFinalizar.add(btnSalvar);
		 
		 JButton btnFinalizarCadastro = new JButton("Salvar");
		 btnFinalizarCadastro.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 	  
		 	 	if(salvar()) {
		 	 		JOptionPane.showMessageDialog(isto, "Transportador Cadastrado");
		 	 		isto.dispose();
		 	 	}else {
		 	 		//JOptionPane.showMessageDialog(null, "Erro ao cadastrar\nConsulte o Administrador do Sistema!");
		 	 		

		 	 	}
		 	}
		 });
		 btnFinalizarCadastro.setBounds(654, 410, 89, 23);
		 painelFinalizar.add(btnFinalizarCadastro);
		 
		 JButton btnAtualizar = new JButton("Atualizar");
		 btnAtualizar.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		if(atualizar(flag_tipo_tela) == true)
		 		{
		 			//telaPai.atualizaTabela();
		 			gerarPastasAtualizar();
		 			
		 			isto.dispose();
		 		}
		 	}
		 });
		 btnAtualizar.setBounds(555, 410, 89, 23);
		 painelFinalizar.add(btnAtualizar);
		 
		  entRegistroTransportador = new JTextFieldPersonalizado();
		 entRegistroTransportador.setBounds(237, 141, 220, 33);
		 entRegistroTransportador.setForeground(Color.black);
		 painelFinalizar.add(entRegistroTransportador);
		 
		 JLabel lblRntrc = new JLabel("RNTRC:");
		 lblRntrc.setHorizontalAlignment(SwingConstants.TRAILING);
		 lblRntrc.setForeground(Color.BLACK);
		 lblRntrc.setFont(new Font("Arial Black", Font.PLAIN, 14));
		 lblRntrc.setBackground(Color.ORANGE);
		 lblRntrc.setBounds(77, 141, 150, 33);
		 painelFinalizar.add(lblRntrc);
		
		if(flag_tipo_tela == 0)
		{
			btnAtualizar.setVisible(false);
			btnAtualizar.setEnabled(false);
		}
		else if(flag_tipo_tela == 1)
		{
			btnFinalizarCadastro.setVisible(false);
			btnFinalizarCadastro.setEnabled(false);
		}
				//painel fisico e juridico
		
				//panelPessoaJuridica.setBackground(Color.WHITE);
				panelPessoaJuridica.setBounds(20, 70, 924, 355);
				panelPessoaJuridica.setLayout(null);
				panelPessoaJuridica.setBounds(22, 71, 1020, 471);

				
				//panelPessoaFisica.setBackground(new Color(139, 0, 139));
				
				panelPessoaFisica.setBounds(22, 71, 1020, 471);

				panelDinamico.setBounds(22, 71, 1020, 543);
				panelDinamico.setLayout(new CardLayout());
				
				//adiciona o painel pessoa fisica no painel dinamico
				panelDinamico.add(panelPessoaFisica, "PessoaFisica");
	
				//adiciona o painel pessoa juridic no painel dinamico
				panelDinamico.add(panelPessoaJuridica,"PessoaJuridica");
				
				JLabel lblCNPJ = new JLabel("CNPJ:");
				lblCNPJ.setHorizontalAlignment(SwingConstants.TRAILING);
				lblCNPJ.setForeground(Color.BLACK);
				lblCNPJ.setFont(new Font("Arial", Font.PLAIN, 16));
				lblCNPJ.setBackground(Color.ORANGE);
				lblCNPJ.setBounds(36, 126, 64, 33);
				lblCNPJ.setHorizontalAlignment(JLabel.LEFT);

				panelPessoaJuridica.add(lblCNPJ);
				
				entCNPJ = new JTextFieldPersonalizado();
				entCNPJ.setForeground(Color.BLACK);
				entCNPJ.setFont(new Font("Arial", Font.BOLD, 18));
				entCNPJ.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent evt) {
						//00.969.790/0005-41
						String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
						String texto = entCNPJ.getText();
						if(!caracteres.contains(evt.getKeyChar()+"")){
						evt.consume();//aciona esse propriedade para eliminar a ação do evento
						}else {
						if(texto.length()==2 && evt.getKeyChar() != '\b'){
							entCNPJ.setText(entCNPJ.getText().concat("."));
						}
						if(texto.length()==6  && evt.getKeyChar() != '\b'){
							entCNPJ.setText(entCNPJ.getText().concat("."));
						}
						
						if(texto.length()==10  && evt.getKeyChar() != '\b'){
							entCNPJ.setText(entCNPJ.getText().concat("/"));
						}
						
						if(texto.length()==15  && evt.getKeyChar() != '\b'){
							entCNPJ.setText(entCNPJ.getText().concat("-"));
						}
						
						
						if(entCNPJ.getText().length()>=18){
							//if para saber se precisa verificar também o tamanho da string do campo
							// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
							evt.consume();
							entCNPJ.setText(entCNPJ.getText().substring(0,18));
						}
					
					}
					}
				});
				entCNPJ.setColumns(10);
				entCNPJ.setBounds(99, 126, 440, 33);
				panelPessoaJuridica.add(entCNPJ);
				
				JLabel lblRazoSocial = new JLabel("Razão Social:");
				lblRazoSocial.setHorizontalAlignment(SwingConstants.TRAILING);
				lblRazoSocial.setForeground(Color.BLACK);
				lblRazoSocial.setFont(new Font("Arial", Font.PLAIN, 16));
				lblRazoSocial.setBackground(Color.ORANGE);
				lblRazoSocial.setBounds(36, 170, 153, 33);
				lblRazoSocial.setHorizontalAlignment(JLabel.LEFT);

				panelPessoaJuridica.add(lblRazoSocial);
				
				JLabel lblNomeFantasia = new JLabel("Nome Fantasia:");
				lblNomeFantasia.setHorizontalAlignment(SwingConstants.TRAILING);
				lblNomeFantasia.setForeground(Color.BLACK);
				lblNomeFantasia.setFont(new Font("Arial", Font.PLAIN, 16));
				lblNomeFantasia.setBackground(Color.ORANGE);
				lblNomeFantasia.setBounds(36, 214, 138, 33);
				lblNomeFantasia.setHorizontalAlignment(JLabel.LEFT);

				panelPessoaJuridica.add(lblNomeFantasia);
				
				entRazaoSocial = new JTextFieldPersonalizado();
				entRazaoSocial.setForeground(Color.BLACK);
				entRazaoSocial.setFont(new Font("Arial", Font.BOLD, 18));
				entRazaoSocial.setColumns(10);
				entRazaoSocial.setBounds(146, 170, 558, 33);
				panelPessoaJuridica.add(entRazaoSocial);
				
				entNomeFantasia = new JTextFieldPersonalizado();
				entNomeFantasia.setForeground(Color.BLACK);
				entNomeFantasia.setFont(new Font("Arial", Font.BOLD, 18));
				entNomeFantasia.setColumns(10);
				entNomeFantasia.setBounds(158, 214, 546, 33);
				panelPessoaJuridica.add(entNomeFantasia);
				
				JButton btnVerificarCNPJ = new JButton("Verificar");
				btnVerificarCNPJ.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String cnpj = entCNPJ.getText();
						cnpj = cnpj.replaceAll("[^0-9]+", "");
						ValidaCNPj valida = new ValidaCNPj();
						
						if(cnpj.length() != 14)
						{
				            JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");

						}
						else {
							if(valida.isCNPJ(cnpj))
							{
								
								String cnpj_formatado = cnpj;
								//24.986679000106
								//24.986.679/0001-06
								StringBuilder stringBuilder = new StringBuilder(cnpj_formatado);
								stringBuilder.insert(cnpj_formatado.length() -12 , '.');
								stringBuilder.insert(cnpj_formatado.length() -8 , '.');
								stringBuilder.insert(cnpj_formatado.length() -4, '/');
								stringBuilder.insert(cnpj_formatado.length() +1 , '-');

								
								entCNPJ.setText(stringBuilder.toString());	
								
								
					  String entcnpj = cnpj;
					  String result = null;
					  try {
						GetHttp buscarCnpj = new GetHttp(entcnpj);
					  
						 result = buscarCnpj.captura();
					  }	catch(Exception e)
					  {
						  
					  }
					  if(result != null) {
						result = result.replaceAll("\"","");
						result = result.replaceAll("\n","&");


						System.out.println(result);

						TratarDados separar = new TratarDados(result);
						
						String nome = separar.tratar("name:", "&al");	
                        String nomeFantasia = separar.tratar("alias:", "&");

                        if(!nome.equals("null"))
                        		entRazaoSocial.setText(nome);
                        
                        
						if(!nomeFantasia.equals("null"))
								entNomeFantasia.setText(nomeFantasia);
						else
							entNomeFantasia.setText(nome);


						
						
						//tratando dados de enderecos
						String bairro = separar.tratar("neighborhood:", "&");
						entBairro.setText(bairro);
						String cep = separar.tratar("zip:", "&");
						entCep.setText(cep);
						String estado = separar.tratar("state:", "&");
						entEstado.setText(estado);
						String cidade = separar.tratar("city:", "&");
						entCidade.setText(cidade);
						String numero = separar.tratar("number:", "&");
						entNum.setText(numero);
						String endereco = separar.tratar("street:", "&");
						entLogradouro.setText(endereco);
						
						
						
						//tratando dados de atividades primaria
						String at_pri = separar.tratar("primary_activity:", "secondary");
						//System.out.println(at_pri);
						TratarDados atividades = new TratarDados(at_pri); 
						at_pri = atividades.tratar("description:", "}&");
						entAtividadesPri.setText("Primária: " + at_pri);
						
						String at_sec = separar.tratar("secondary_activities:", "membership");
						System.out.println(at_sec);
						TratarDados sec_atividades = new TratarDados(at_sec); 
						at_sec = sec_atividades.tratar("description:", "}&");
						entAtividadeSec.setText("Secundária: "  + at_sec);
						
						//tratando dados status
						String status = separar.tratar("federal_entity:", "}");
						//System.out.println(status);
						TratarDados status_ = new TratarDados(status); 
						status = status_.tratar("status:", "&status_date");
						entStatus.setText(status);
						
						//tratando dados descricao
						String descricao = separar.tratar("legal_nature:", "&pri");
						System.out.println(descricao);
						TratarDados descricao_ = new TratarDados(descricao); 
						descricao = descricao_.tratar("description:", "}");
						entDescricao.setText(descricao);
						
						//tratar dados de inscricao estadual
						String inscricao_estadual = separar.tratar("registrations:", "]");
						System.out.println(inscricao_estadual);
						TratarDados inscricao_estadual_ = new TratarDados(inscricao_estadual); 
						inscricao_estadual = inscricao_estadual_.tratar("number:", "&");
						entIE.setText(inscricao_estadual);
						
						String statusIE = inscricao_estadual_.tratar("enabled:", "}");
                        if(statusIE.equals("true"))
                        	entStatusIE.setText("Ativo");
                        else
                        	entStatusIE.setText("Não Habilitado");

						}
					  else
					  {
				            System.out.println("Erro ao realizar a consulta \n Tente Novamente!");

					  }
						
							}
						else
				            JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");
						}
					}
				});
				btnVerificarCNPJ.setBounds(549, 126, 155, 33);
				panelPessoaJuridica.add(btnVerificarCNPJ);
				
				JLabel lblStatus = new JLabel("Status:");
				lblStatus.setHorizontalAlignment(SwingConstants.TRAILING);
				lblStatus.setForeground(Color.BLACK);
				lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
				lblStatus.setBackground(Color.ORANGE);
				lblStatus.setBounds(36, 258, 64, 33);
				lblStatus.setHorizontalAlignment(JLabel.LEFT);

				panelPessoaJuridica.add(lblStatus);
				
				entStatus = new JTextFieldPersonalizado();
				entStatus.setForeground(Color.BLACK);
				entStatus.setFont(new Font("Arial", Font.BOLD, 18));
				entStatus.setColumns(10);
				entStatus.setBounds(99, 258, 220, 33);
				panelPessoaJuridica.add(entStatus);
				
				JLabel lblDescrio = new JLabel("Descrição:");
				lblDescrio.setHorizontalAlignment(SwingConstants.TRAILING);
				lblDescrio.setForeground(Color.BLACK);
				lblDescrio.setFont(new Font("Arial", Font.PLAIN, 16));
				lblDescrio.setBackground(Color.ORANGE);
				lblDescrio.setBounds(329, 258, 119, 33);
				lblDescrio.setHorizontalAlignment(JLabel.LEFT);

				panelPessoaJuridica.add(lblDescrio);
				
				entDescricao = new JTextFieldPersonalizado();
				entDescricao.setForeground(Color.BLACK);
				entDescricao.setFont(new Font("Arial", Font.BOLD, 18));
				entDescricao.setColumns(10);
				entDescricao.setBounds(414, 258, 290, 33);
				panelPessoaJuridica.add(entDescricao);
				
				JLabel lblAtividades = new JLabel("Atividades:");
				lblAtividades.setHorizontalAlignment(SwingConstants.TRAILING);
				lblAtividades.setForeground(Color.BLACK);
				lblAtividades.setFont(new Font("Arial", Font.PLAIN, 16));
				lblAtividades.setBackground(Color.ORANGE);
				lblAtividades.setBounds(36, 302, 119, 33);
				lblAtividades.setHorizontalAlignment(JLabel.LEFT);

				panelPessoaJuridica.add(lblAtividades);
				
				entAtividadesPri = new JTextFieldPersonalizado();
				entAtividadesPri.setForeground(Color.BLACK);
				entAtividadesPri.setFont(new Font("Arial", Font.BOLD, 18));
				entAtividadesPri.setColumns(10);
				entAtividadesPri.setBounds(131, 302, 573, 33);
				panelPessoaJuridica.add(entAtividadesPri);
				
				entAtividadeSec = new JTextFieldPersonalizado();
				entAtividadeSec.setForeground(Color.BLACK);
				entAtividadeSec.setFont(new Font("Arial", Font.BOLD, 18));
				entAtividadeSec.setColumns(10);
				entAtividadeSec.setBounds(131, 345, 573, 33);
				panelPessoaJuridica.add(entAtividadeSec);

			
				
						//adiciona o painel dinamico no painel filho 1
						painelDadosIniciais.add(panelDinamico);
						panelPessoaFisica.setLayout(null);
		
		
		
						JLabel lblNome = new JLabel("Nome:");
						lblNome.setHorizontalAlignment(SwingConstants.TRAILING);
						lblNome.setForeground(Color.BLACK);
						lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
						lblNome.setBackground(Color.ORANGE);
						lblNome.setBounds(21, 156, 72, 21);
						lblNome.setHorizontalAlignment(JLabel.LEFT);
						panelPessoaFisica.add(lblNome);
						
						entNome = new JTextFieldPersonalizado();
						entNome.setForeground(Color.BLACK);
						entNome.setFont(new Font("Arial", Font.BOLD, 20));
						entNome.setColumns(10);
						entNome.setBounds(74, 151, 242, 28);
						panelPessoaFisica.add(entNome);
						
						JLabel lblSobrenome = new JLabel("Sobrenome:");
						lblSobrenome.setHorizontalAlignment(SwingConstants.TRAILING);
						lblSobrenome.setForeground(Color.BLACK);
						lblSobrenome.setFont(new Font("Arial", Font.PLAIN, 16));
						lblSobrenome.setBackground(Color.ORANGE);
						lblSobrenome.setBounds(326, 156, 103, 21);
						lblSobrenome.setHorizontalAlignment(JLabel.LEFT);
						panelPessoaFisica.add(lblSobrenome);
						

						entSobrenome = new JTextFieldPersonalizado();
						entSobrenome.setForeground(Color.BLACK);
						entSobrenome.setFont(new Font("Arial", Font.BOLD, 20));
						entSobrenome.setColumns(10);
						entSobrenome.setBounds(430, 151, 267, 28);
						panelPessoaFisica.add(entSobrenome);
						
						entNascimento = new JTextFieldPersonalizado();
						entNascimento.setForeground(Color.BLACK);
						entNascimento.setFont(new Font("Arial", Font.BOLD, 20));
						entNascimento.addKeyListener(new KeyAdapter() {
							@Override
							public void keyTyped(KeyEvent evt) {
								String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
								String texto = entNascimento.getText();
								if(!caracteres.contains(evt.getKeyChar()+"")){
								evt.consume();//aciona esse propriedade para eliminar a ação do evento
								}else {
								if(texto.length()==2 && evt.getKeyChar() != '\b'){
									entNascimento.setText(entNascimento.getText().concat("/"));
								}
								if(texto.length()==5  && evt.getKeyChar() != '\b'){
									entNascimento.setText(entNascimento.getText().concat("/"));
								}
								
								if(entNascimento.getText().length()>=10){
									//if para saber se precisa verificar também o tamanho da string do campo
									// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
									evt.consume();
									entNascimento.setText(entNascimento.getText().substring(0,10));
								}
							
							}
								
							}
						});
						entNascimento.setToolTipText("Data de Nascimento, somente números");
						entNascimento.setColumns(10);
						entNascimento.setBounds(124, 190, 242, 28);
						panelPessoaFisica.add(entNascimento);
						
						JLabel lblDataNascimento = new JLabel("Nascimento:");
						lblDataNascimento.setHorizontalAlignment(SwingConstants.TRAILING);
						lblDataNascimento.setForeground(Color.BLACK);
						lblDataNascimento.setFont(new Font("Arial", Font.PLAIN, 16));
						lblDataNascimento.setBackground(Color.ORANGE);
						lblDataNascimento.setBounds(21, 195, 112, 21);
						lblDataNascimento.setHorizontalAlignment(JLabel.LEFT);
						panelPessoaFisica.add(lblDataNascimento);
						
						JLabel lblCpf = new JLabel("CPF:");
						lblCpf.setHorizontalAlignment(SwingConstants.TRAILING);
						lblCpf.setForeground(Color.BLACK);
						lblCpf.setFont(new Font("Arial", Font.PLAIN, 16));
						lblCpf.setBackground(Color.ORANGE);
						lblCpf.setHorizontalAlignment(JLabel.RIGHT);
						lblCpf.setBounds(22, 118, 43, 24);
						panelPessoaFisica.add(lblCpf);
						
						entCpf = new JTextFieldPersonalizado();
					
					
						
						
						
					
						entCpf.setForeground(Color.BLACK);
						entCpf.setFont(new Font("Arial", Font.BOLD, 20));
						entCpf.addKeyListener(new KeyAdapter() {
							@Override
							public void keyTyped(KeyEvent evt) {
								//120.927.987-00
								String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
								String texto = entCpf.getText();
								if(!caracteres.contains(evt.getKeyChar()+"")){
								evt.consume();//aciona esse propriedade para eliminar a ação do evento
								}else {
								if(texto.length()==3 && evt.getKeyChar() != '\b'){
									entCpf.setText(entCpf.getText().concat("."));
								}
								if(texto.length()==7  && evt.getKeyChar() != '\b'){
									entCpf.setText(entCpf.getText().concat("."));
								}
								
								if(texto.length()==11  && evt.getKeyChar() != '\b'){
									entCpf.setText(entCpf.getText().concat("-"));
								}
								
								if(entCpf.getText().length()>=14){
									//if para saber se precisa verificar também o tamanho da string do campo
									// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
									evt.consume();
									entCpf.setText(entCpf.getText().substring(0,14));
								}
							
							}
								
							}
						});
						entCpf.setColumns(10);
						entCpf.setBounds(74, 112, 347, 28);
						
						 
						
						panelPessoaFisica.add(entCpf);
						
						entRg = new JTextFieldPersonalizado();
						entRg.setForeground(Color.BLACK);
						entRg.setFont(new Font("Arial", Font.BOLD, 20));
						entRg.setColumns(10);
						entRg.setBounds(412, 190, 285, 28);
						panelPessoaFisica.add(entRg);
						
						JLabel lblRg = new JLabel("RG:");
						lblRg.setHorizontalAlignment(SwingConstants.TRAILING);
						lblRg.setForeground(Color.BLACK);
						lblRg.setFont(new Font("Arial", Font.PLAIN, 16));
						lblRg.setBackground(Color.ORANGE);
						lblRg.setBounds(376, 195, 43, 21);
						lblRg.setHorizontalAlignment(JLabel.LEFT);
						panelPessoaFisica.add(lblRg);
						
						JLabel lblOcupao = new JLabel("Ocupação:");
						lblOcupao.setHorizontalAlignment(SwingConstants.TRAILING);
						lblOcupao.setForeground(Color.BLACK);
						lblOcupao.setFont(new Font("Arial", Font.PLAIN, 16));
						lblOcupao.setBackground(Color.ORANGE);
						lblOcupao.setBounds(399, 234, 91, 21);
						lblOcupao.setHorizontalAlignment(JLabel.LEFT);

						panelPessoaFisica.add(lblOcupao);
						
						entOcupacao = new JTextFieldPersonalizado();
						entOcupacao.setForeground(Color.BLACK);
						entOcupacao.setFont(new Font("Arial", Font.BOLD, 20));
						entOcupacao.setColumns(10);
						entOcupacao.setBounds(486, 229, 211, 28);
						panelPessoaFisica.add(entOcupacao);
						
						JButton btnPesquisarCPF = new JButton("Pesquisar");
						btnPesquisarCPF.setEnabled(false);
						btnPesquisarCPF.setVisible(false);
						btnPesquisarCPF.setFont(new Font("Arial", Font.BOLD, 16));
						btnPesquisarCPF.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String cpf = entCpf.getText().toString();
						 		CPFValidator cpfValidator = new CPFValidator(); 
						 		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
						 		if(erros.size() > 0) {
						            JOptionPane.showMessageDialog(isto, "CPF Inválido!");
						 		}
						 		else
						 		{
						           // JOptionPane.showMessageDialog(null, "CPF Válido!");
						 			cpf = cpf.replace(".", "");
						 			cpf = cpf.replace("-", "");
						 			System.out.println(uf);
						 			System.out.println(cpf);
									
						 			GetSintegra sintegra = new GetSintegra(cpf, uf, 0);
									String result = sintegra.captura();
									System.out.println(result);
                                   
									//result = result.replaceAll ("{", "");
									result = result.replaceAll("\"", "");
									result = result.replaceAll (",", "&");
									System.out.println(result);

									TratarDados separar = new TratarDados(result);
									
									String code = separar.tratar("code:", "&s");
									System.out.println("Codigo: "+code);
		                            if(code.equals("0"))
							{
										String nome_empresarial = separar.tratar("nome_empresarial:", "&");
										System.out.println(nome_empresarial);
				                        entNomeEmpresarial.setText(nome_empresarial);
								        
								        String ie = separar.tratar("inscricao_estadual:", "&");
								        System.out.println(ie);
				                        entIE.setText(ie);
								       
				                        String status = separar.tratar("situacao_ie:", "&");
								        System.out.println(status);
				                        entStatusIE.setText(status);
							            
				                        String nome_fantasia = separar.tratar("nome_fantasia:", "&");
								        System.out.println(nome_fantasia);
				                        entNomeFantasia.setText(nome_fantasia);
				                        
				                        String porte = separar.tratar("porte_empresa:", "&");
								        System.out.println(porte);
				                        entPorte.setText(porte);
				                        
				                        String cnae = separar.tratar("text:", "&");
								        System.out.println(cnae);
				                        entCnae.setText(cnae);
				                        
				                        
				                        String ocupacao = separar.tratar("tipo_inscricao:", "&");
								        System.out.println(ocupacao);
				                        entOcupacao.setText(ocupacao);
				                        
				                      //tratando dados de enderecos
										String bairro = separar.tratar("bairro:", "&");
										entBairro.setText(bairro);
										String cep = separar.tratar("cep:", "&");
										entCep.setText(cep);
										//String estado = separar.tratar("state:", "&");
										entEstado.setText(uf);
										String cidade = separar.tratar("municipio:", "&log");
										entCidade.setText(cidade);
										String numero = separar.tratar("numero:", "&");
										entNum.setText(numero);
										String endereco = separar.tratar("logradouro:", "&");
										entLogradouro.setText(endereco);
							}
		                            	else
		                            		{
		                            			JOptionPane.showMessageDialog(isto, "Erro ao consultar dados no Sintegra");

		                            		}
									
									
									
			                        
			                        
						 		}
							}
						});
						btnPesquisarCPF.setBounds(550, 113, 147, 34);
						panelPessoaFisica.add(btnPesquisarCPF);
						
						JLabel lblUf = new JLabel("UF:");
						lblUf.setHorizontalAlignment(SwingConstants.TRAILING);
						lblUf.setForeground(Color.BLACK);
						lblUf.setFont(new Font("Arial", Font.PLAIN, 16));
						lblUf.setBackground(Color.ORANGE);
						lblUf.setBounds(430, 116, 35, 29);
						lblUf.setHorizontalAlignment(JLabel.LEFT);

						panelPessoaFisica.add(lblUf);
						
						JComboBox cBUF = new JComboBox();
						cBUF.setFont(new Font("Arial", Font.BOLD, 14));
						cBUF.addItem("MG");
						cBUF.addItem("SP");
						cBUF.addItem("GO");
						cBUF.addItem("MA");
						cBUF.setBounds(468, 114, 72, 35);
						cBUF.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
								uf = cBUF.getSelectedItem().toString();
								
							}
						});
						panelPessoaFisica.add(cBUF);
						
						JLabel lblNomeEmpresarial = new JLabel("Nome Empresarial:");
						lblNomeEmpresarial.setHorizontalAlignment(SwingConstants.TRAILING);
						lblNomeEmpresarial.setForeground(Color.BLACK);
						lblNomeEmpresarial.setFont(new Font("Arial", Font.PLAIN, 16));
						lblNomeEmpresarial.setBackground(Color.ORANGE);
						lblNomeEmpresarial.setBounds(21, 227, 153, 34);
						lblNomeEmpresarial.setHorizontalAlignment(JLabel.LEFT);
						panelPessoaFisica.add(lblNomeEmpresarial);
						
						entNomeEmpresarial = new JTextFieldPersonalizado();
						entNomeEmpresarial.setForeground(Color.BLACK);
						entNomeEmpresarial.setFont(new Font("Arial", Font.BOLD, 20));
						entNomeEmpresarial.setColumns(10);
						entNomeEmpresarial.setBounds(171, 229, 215, 28);
						panelPessoaFisica.add(entNomeEmpresarial);
						
						JLabel lblPorte = new JLabel("Porte:");
						lblPorte.setHorizontalAlignment(SwingConstants.TRAILING);
						lblPorte.setForeground(Color.BLACK);
						lblPorte.setFont(new Font("Arial", Font.PLAIN, 16));
						lblPorte.setBackground(Color.ORANGE);
						lblPorte.setBounds(21, 272, 60, 28);
						lblPorte.setHorizontalAlignment(JLabel.LEFT);

						panelPessoaFisica.add(lblPorte);
						
						entPorte = new JTextFieldPersonalizado();
						entPorte.setForeground(Color.BLACK);
						entPorte.setFont(new Font("Arial", Font.BOLD, 20));
						entPorte.setToolTipText("Data de Nascimento, somente números");
						entPorte.setColumns(10);
						entPorte.setBounds(74, 271, 623, 28);
						panelPessoaFisica.add(entPorte);
						
						JLabel lblCnae = new JLabel("Atividades:");
						lblCnae.setHorizontalAlignment(SwingConstants.TRAILING);
						lblCnae.setForeground(Color.BLACK);
						lblCnae.setFont(new Font("Arial", Font.PLAIN, 16));
						lblCnae.setBackground(Color.ORANGE);
						lblCnae.setBounds(21, 324, 91, 21);
						lblCnae.setHorizontalAlignment(JLabel.LEFT);

						panelPessoaFisica.add(lblCnae);
						
						entCnae = new JTextFieldPersonalizado();
						entCnae.setForeground(Color.BLACK);
						entCnae.setFont(new Font("Arial", Font.BOLD, 20));
						entCnae.setColumns(10);
						entCnae.setBounds(110, 322, 587, 61);
						panelPessoaFisica.add(entCnae);
						cBPessoa.setBounds(364, 33, 126, 35);
						painelDadosIniciais.add(cBPessoa);
						cBPessoa.addItem("Física");
						cBPessoa.addItem("Jurídica");
						
						
						JLabel lblPessoa = new JLabel("Pessoa:");
						lblPessoa.setBounds(290, 32, 64, 33);
						painelDadosIniciais.add(lblPessoa);
						lblPessoa.setHorizontalAlignment(SwingConstants.TRAILING);
						lblPessoa.setForeground(Color.BLACK);
						lblPessoa.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblPessoa.setBackground(Color.ORANGE);
						
						JLabel lblCadastro_2 = new JLabel(" ----- Cadastro / Dados de Pessoa");
						lblCadastro_2.setHorizontalAlignment(SwingConstants.TRAILING);
						lblCadastro_2.setForeground(Color.BLACK);
						lblCadastro_2.setFont(new Font("Arial", Font.PLAIN, 14));
						lblCadastro_2.setBackground(Color.ORANGE);
						lblCadastro_2.setHorizontalAlignment(JLabel.LEFT);

						lblCadastro_2.setBounds(0, 0, 250, 33);

						painelDadosIniciais.add(lblCadastro_2);
						
						cBPessoa.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(cBPessoa.getSelectedItem()=="Física")
								{
									CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
                    cardLayout.show(panelDinamico, "PessoaFisica");
								
									
								}
								if(cBPessoa.getSelectedItem()=="Jurídica")
								{
									CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
									cardLayout.show(panelDinamico, "PessoaJuridica");
								
								}
								
								
							}
						});
						
						
						
		//configura widgets no painel Empresa
						entLogradouro = new JTextFieldPersonalizado();
						entLogradouro.setForeground(Color.BLACK);
						entLogradouro.setColumns(10);
						entLogradouro.setBounds(108, 291, 220, 33);
						painelEmpresa.add(entLogradouro);
						
						JLabel lblLogradouro = new JLabel("Rua:");
						lblLogradouro.setHorizontalAlignment(SwingConstants.TRAILING);
						lblLogradouro.setForeground(Color.BLACK);
						lblLogradouro.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblLogradouro.setBackground(Color.ORANGE);
						lblLogradouro.setBounds(23, 289, 75, 33);
						painelEmpresa.add(lblLogradouro);
						
						JLabel lblN = new JLabel("N.\u00BA:");
						lblN.setHorizontalAlignment(SwingConstants.TRAILING);
						lblN.setForeground(Color.BLACK);
						lblN.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblN.setBackground(Color.ORANGE);
						lblN.setBounds(33, 333, 64, 33);
						painelEmpresa.add(lblN);
						
						entNum = new JTextFieldPersonalizado();
						entNum.setForeground(Color.BLACK);
						entNum.addKeyListener(new KeyAdapter() {
							@Override
							public void keyTyped(KeyEvent evt) {
								
								String caracteres="0987654321";// lista de caracters que não devem ser aceitos
								if(!caracteres.contains(evt.getKeyChar()+"")){
								evt.consume();//aciona esse propriedade para eliminar a ação do evento
								}
								if(entNum.getText().length()>=6){
									//if para saber se precisa verificar também o tamanho da string do campo
									// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
									evt.consume();
									entNum.setText(entNum.getText().substring(0,8));
							
							}
							}
						});
						entNum.setColumns(10);
						entNum.setBounds(108, 335, 220, 33);
						painelEmpresa.add(entNum);
						
						JLabel lblBairro = new JLabel("Bairro:");
						lblBairro.setHorizontalAlignment(SwingConstants.TRAILING);
						lblBairro.setForeground(Color.BLACK);
						lblBairro.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblBairro.setBackground(Color.ORANGE);
						lblBairro.setBounds(34, 377, 64, 33);
						painelEmpresa.add(lblBairro);
						
						entBairro = new JTextFieldPersonalizado();
						entBairro.setForeground(Color.BLACK);
						entBairro.setColumns(10);
						entBairro.setBounds(108, 379, 220, 33);
						painelEmpresa.add(entBairro);
						
						JLabel lblCidade = new JLabel("Cidade:");
						lblCidade.setHorizontalAlignment(SwingConstants.TRAILING);
						lblCidade.setForeground(Color.BLACK);
						lblCidade.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblCidade.setBackground(Color.ORANGE);
						lblCidade.setBounds(338, 333, 64, 33);
						painelEmpresa.add(lblCidade);
						
						JLabel lblBairro_1_1 = new JLabel("Estado:");
						lblBairro_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
						lblBairro_1_1.setForeground(Color.BLACK);
						lblBairro_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblBairro_1_1.setBackground(Color.ORANGE);
						lblBairro_1_1.setBounds(338, 377, 64, 33);
						painelEmpresa.add(lblBairro_1_1);
						
						JLabel lblBairro_1_2 = new JLabel("CEP:");
						lblBairro_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
						lblBairro_1_2.setForeground(Color.BLACK);
						lblBairro_1_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblBairro_1_2.setBackground(Color.ORANGE);
						lblBairro_1_2.setBounds(338, 289, 64, 33);
						painelEmpresa.add(lblBairro_1_2);
						
						entCep = new JTextFieldPersonalizado();
						entCep.setForeground(Color.BLACK);
						entCep.addKeyListener(new KeyAdapter() {
							@Override
							public void keyTyped(KeyEvent evt) {
								
								String caracteres="0987654321";// lista de caracters que não devem ser aceitos
								if(!caracteres.contains(evt.getKeyChar()+"")){
								evt.consume();//aciona esse propriedade para eliminar a ação do evento
								}
								if(entCep.getText().length()>=8){
									//if para saber se precisa verificar também o tamanho da string do campo
									// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
									evt.consume();
									entCep.setText(entCep.getText().substring(0,8));
							
							}
							}
						});
						entCep.setColumns(10);
						entCep.setBounds(412, 291, 220, 33);
						painelEmpresa.add(entCep);
						
						JButton btnLocalizar = new JButton("Localizar");
						btnLocalizar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								try {
								int cep = Integer.parseInt(entCep.getText().toString());
								if(entCep.getText().toString().length() != 8)
								{
						            JOptionPane.showMessageDialog(isto, "Cep Invalido!");

								}
								else
								{
									BuscarCep busca  = new BuscarCep(cep);
									Endereco endereco = busca.buscar();
									entLogradouro.setText(endereco.getLogradouro());
									entBairro.setText(endereco.getBairro());
									entCidade.setText(endereco.getCidade());
									entEstado.setText(endereco.getUf());
									
									
								}
								
								
								}catch(Exception e1)
								{
									
								}
								
								
							}
						});
						btnLocalizar.setToolTipText("Buscar Cep");
						//btnLocalizar.setIcon(new ImageIcon(TelaNovoCliente.class.getResource("/imagens/procurar.png")));
						btnLocalizar.setBounds(642, 291, 125, 33);
						painelEmpresa.add(btnLocalizar);
						
						entCidade = new JTextFieldPersonalizado();
						entCidade.setForeground(Color.BLACK);
						entCidade.setColumns(10);
						entCidade.setBounds(412, 335, 220, 33);
						painelEmpresa.add(entCidade);
						
						entEstado = new JTextFieldPersonalizado();
						entEstado.setForeground(Color.BLACK);
						entEstado.setColumns(10);
						entEstado.setBounds(412, 379, 220, 33);
						painelEmpresa.add(entEstado);
						
						JLabel lblIe = new JLabel("IE:");
						lblIe.setHorizontalAlignment(SwingConstants.TRAILING);
						lblIe.setForeground(Color.BLACK);
						lblIe.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblIe.setBackground(Color.ORANGE);
						lblIe.setBounds(220, 100, 64, 33);
						painelEmpresa.add(lblIe);
						
						entIE = new JTextFieldPersonalizado();
						entIE.setForeground(Color.BLACK);
						entIE.setColumns(10);
						entIE.setBounds(294, 102, 220, 33);
						painelEmpresa.add(entIE);
						
						JLabel lblStatus_1 = new JLabel("Status:");
						lblStatus_1.setHorizontalAlignment(SwingConstants.TRAILING);
						lblStatus_1.setForeground(Color.BLACK);
						lblStatus_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblStatus_1.setBackground(Color.ORANGE);
						lblStatus_1.setBounds(220, 144, 64, 33);
						painelEmpresa.add(lblStatus_1);
						
						entStatusIE = new JTextFieldPersonalizado();
						entStatusIE.setForeground(Color.BLACK);
						entStatusIE.setColumns(10);
						entStatusIE.setBounds(294, 146, 220, 33);
						painelEmpresa.add(entStatusIE);
						
						JLabel lblLogradouro_1 = new JLabel("Logradouro:");
						lblLogradouro_1.setHorizontalAlignment(SwingConstants.TRAILING);
						lblLogradouro_1.setForeground(Color.BLACK);
						lblLogradouro_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
						lblLogradouro_1.setBackground(Color.ORANGE);
						lblLogradouro_1.setBounds(23, 210, 110, 33);
						painelEmpresa.add(lblLogradouro_1);
						
						JLabel lblCadastro_2_1 = new JLabel(" ----- Cadastro / Dados da Empresa");
						lblCadastro_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
						lblCadastro_2_1.setForeground(Color.BLACK);
						lblCadastro_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
						lblCadastro_2_1.setBackground(Color.ORANGE);
						lblCadastro_2_1.setBounds(0, 0, 250, 33);
						lblCadastro_2_1.setHorizontalAlignment(JLabel.LEFT);

						painelEmpresa.add(lblCadastro_2_1);
		
					
	    //configura os widgets no painel de dados Bancarios
						
						
						 JLabel lblBanco = new JLabel("Banco:");
						 lblBanco.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblBanco.setForeground(Color.BLACK);
						 lblBanco.setFont(new Font("Arial Black", Font.PLAIN, 14));
						 lblBanco.setBackground(Color.ORANGE);
						 lblBanco.setBounds(54, 323, 64, 33);
						 painelDadosBancarios.add(lblBanco);
						 
						 entBanco = new JTextFieldPersonalizado();
						 entBanco.setForeground(Color.BLACK);
						 entBanco.setColumns(10);
						 entBanco.setBounds(128, 325, 220, 33);
						 painelDadosBancarios.add(entBanco);
						 
						 JLabel lblCdigo = new JLabel("Código:");
						 lblCdigo.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblCdigo.setForeground(Color.BLACK);
						 lblCdigo.setFont(new Font("Arial Black", Font.PLAIN, 14));
						 lblCdigo.setBackground(Color.ORANGE);
						 lblCdigo.setBounds(54, 367, 64, 33);
						 painelDadosBancarios.add(lblCdigo);
						 
						 JLabel lblConta = new JLabel("Conta:");
						 lblConta.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblConta.setForeground(Color.BLACK);
						 lblConta.setFont(new Font("Arial Black", Font.PLAIN, 14));
						 lblConta.setBackground(Color.ORANGE);
						 lblConta.setBounds(54, 455, 64, 33);
						 painelDadosBancarios.add(lblConta);
						 
						 JLabel lblAgncia = new JLabel("Agência:");
						 lblAgncia.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblAgncia.setForeground(Color.BLACK);
						 lblAgncia.setFont(new Font("Arial Black", Font.PLAIN, 14));
						 lblAgncia.setBackground(Color.ORANGE);
						 lblAgncia.setBounds(44, 411, 80, 33);
						 painelDadosBancarios.add(lblAgncia);
						 
						 entCodBanco = new JTextFieldPersonalizado();
						 entCodBanco.setForeground(Color.BLACK);
						 entCodBanco.setColumns(10);
						 entCodBanco.setBounds(128, 369, 220, 33);
						 painelDadosBancarios.add(entCodBanco);
						 
						 entConta = new JTextFieldPersonalizado();
						 entConta.setForeground(Color.BLACK);
						 entConta.setColumns(10);
						 entConta.setBounds(128, 457, 220, 33);
						 painelDadosBancarios.add(entConta);
						 
						 entAgencia = new JTextFieldPersonalizado();
						 entAgencia.setForeground(Color.BLACK);
						 entAgencia.setColumns(10);
						 entAgencia.setBounds(128, 413, 220, 33);
						 painelDadosBancarios.add(entAgencia);
						 
						 JLabel lblCpfTitular = new JLabel("CPF Titular:");
						 lblCpfTitular.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblCpfTitular.setForeground(Color.BLACK);
						 lblCpfTitular.setFont(new Font("Arial Black", Font.PLAIN, 14));
						 lblCpfTitular.setBackground(Color.ORANGE);
						 lblCpfTitular.setBounds(31, 229, 90, 33);
						 painelDadosBancarios.add(lblCpfTitular);
						 
						 entCpfTitular = new JTextFieldPersonalizado();
						 entCpfTitular.setForeground(Color.BLACK);
						 entCpfTitular.setColumns(10);
						 entCpfTitular.setBounds(128, 231, 220, 33);
						 painelDadosBancarios.add(entCpfTitular);
						 
						 painel_table_cb = new JPanel();
						 //painel_table_cb.setBounds(10, 48, 769, 162);
						 painel_table_cb.setBounds(38, 65, 724, 138);

						 
						 table_cb = new JTable(modelo_cb);
						 table_cb.setBackground(new Color(255, 255, 255));
						 
						 modelo_cb.addColumn("Id");
						    modelo_cb.addColumn("CPF");
						    modelo_cb.addColumn("Nome");

						    modelo_cb.addColumn("Banco");


						    modelo_cb.addColumn("Codigo");
							modelo_cb.addColumn("Agência");
                            
							modelo_cb.addColumn("Conta");
					      
							table_cb.getColumnModel().getColumn(0)
					        .setPreferredWidth(20);
							   table_cb.getColumnModel().getColumn(2)
						        .setPreferredWidth(100);
					        table_cb.getColumnModel().getColumn(1)
					        .setPreferredWidth(20);
					        table_cb.getColumnModel().getColumn(3)
					        .setPreferredWidth(130);
					        table_cb.getColumnModel().getColumn(4)
					        .setPreferredWidth(30);
					        table_cb.getColumnModel().getColumn(5)
					        .setPreferredWidth(30);
					        table_cb.getColumnModel().getColumn(6)
					        .setPreferredWidth(100);
					     
					    
					        painel_table_cb.setLayout(null);
					    	modelo_cb.setNumRows(0);
					        JScrollPane scrollPaneCB = new JScrollPane(table_cb);
					        scrollPaneCB.setBounds(10, 5, 704, 122);
					        scrollPaneCB.setAutoscrolls(true);
					        scrollPaneCB.setBackground(new Color(255, 255, 255));
					        painel_table_cb.add(scrollPaneCB);
							
						
						 painelDadosBancarios.add(painel_table_cb);
						 
						 JButton btnAdicionarCB = new JButton("Adicionar");
						 btnAdicionarCB.addActionListener(new ActionListener() {
						 	public void actionPerformed(ActionEvent arg0) {
						 		String cpf, banco, codigo, agencia, conta, id, nome;
						 		
						 		id = "0000";
						 		cpf = entCpfTitular.getText().toString();
						 		banco = entBanco.getText().toString();
						 		nome = entNomeContaBancaria.getText().toString();
						 		codigo = entCodBanco.getText().toString();
						 		agencia = entAgencia.getText().toString();
						 		conta = entConta.getText().toString();

								modelo_cb.addRow(new Object[]{id, cpf, nome, banco, 
									       codigo, agencia, conta});
						 		
						 	}
						 });
						 btnAdicionarCB.setBounds(369, 462, 89, 23);
						 painelDadosBancarios.add(btnAdicionarCB);
						 
						 JButton btnExcluirCB = new JButton("Excluir");
						 btnExcluirCB.addActionListener(new ActionListener() {
						 	public void actionPerformed(ActionEvent e) {
						 		
						 		int indiceDaLinha = table_cb.getSelectedRow();
						 	
						 if(flag_tipo_tela == 0 || flag_tipo_tela == 6)
								{
										
										String id_excluir = table_cb.getValueAt(indiceDaLinha, 0).toString();
										if(id_excluir.equals("0000"))
										{
										}
										else
										{
											contas_excluir.add(Integer.parseInt(id_excluir));

										}
										

										((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha); table_cb.repaint(); table_cb.validate(); 
										
									
								}
								else {
							
								((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha); table_cb.repaint(); table_cb.validate(); 
								
								}
						 	
						 	}
						 });
						 btnExcluirCB.setBounds(690, 211, 89, 23);
						 painelDadosBancarios.add(btnExcluirCB);
						 
						 JLabel lblNomeTitular = new JLabel("Nome Titular:");
						 lblNomeTitular.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblNomeTitular.setForeground(Color.BLACK);
						 lblNomeTitular.setFont(new Font("Arial Black", Font.PLAIN, 14));
						 lblNomeTitular.setBackground(Color.ORANGE);
						 lblNomeTitular.setBounds(20, 279, 104, 33);
						 painelDadosBancarios.add(lblNomeTitular);
						 
						 entNomeContaBancaria = new JTextFieldPersonalizado();
						 entNomeContaBancaria.setForeground(Color.BLACK);
						 entNomeContaBancaria.setColumns(10);
						 entNomeContaBancaria.setBounds(128, 275, 220, 33);
						 painelDadosBancarios.add(entNomeContaBancaria);
						 
						 JLabel lblCadastro = new JLabel(" ----- Cadastro / Dados Bancários");
						 lblCadastro.setHorizontalAlignment(SwingConstants.TRAILING);
						 lblCadastro.setForeground(Color.BLACK);
						 lblCadastro.setFont(new Font("Arial", Font.PLAIN, 14));
						 lblCadastro.setBackground(Color.ORANGE);
						 lblCadastro.setBounds(0, 0, 246, 33);
						 lblCadastro.setHorizontalAlignment(JLabel.LEFT);

						 painelDadosBancarios.add(lblCadastro);
						
						 
		uf = cBUF.getSelectedItem().toString();				 
						 
						 
		//configura widgets no painel finalizar
						
		 getContentPane().add(painelPrincipal, BorderLayout.CENTER);
		 
		
		
		 
		 if(flag_tipo_tela == 1) //0 novo cliente //1 atualizar
		 {
			 btnFinalizarCadastro.setVisible(false);

			 btnFinalizarCadastro.setEnabled(false);
			 lblCodigo.setText(Integer.toString(cliente.getId()));
			 entRegistroTransportador.setText(cliente.getRntrc());
			 getVeiculos() ;

			 if(cliente.getTipo_pessoa() == 1)
			 {
				 //pesoa juridica
				 cBPessoa.setSelectedItem("Jurídica");
				 cBPessoa.setEnabled(false);
				
				 entCNPJ.setText(cliente.getCnpj());
				 entCNPJ.setEditable(false);
				 btnVerificarCNPJ.setEnabled(false);
				 entRazaoSocial.setText(cliente.getRazao_social());
				 entAtividadesPri.setText(cliente.getAt_primaria());
				 entAtividadeSec.setText(cliente.getAt_secundaria());
				 entStatus.setText(cliente.getStatus());
				 entNomeFantasia.setText(cliente.getNome_fantaia());
				 entDescricao.setText(cliente.getDescricao());
				 
				
				 
				
				
			 }else {
				 cBPessoa.setSelectedItem("Física");
				 cBPessoa.setEnabled(false);
				 if(cliente.getCpf() == null || cliente.getCpf().equalsIgnoreCase("null")) {
					 entCpf.setEditable(true);

				 }else {
				 entCpf.setText(cliente.getCpf());
				 entCpf.setEditable(false);

				 }
				 cBUF.setSelectedItem(cliente.getUf());
				 cBUF.setEnabled(false);
				 btnPesquisarCPF.setEnabled(false);
				 
				 entOcupacao.setText(cliente.getOcupacao());
				 entNome.setText(cliente.getNome());
				 entSobrenome.setText(cliente.getSobrenome());
				 entNascimento.setText(cliente.getNascimento());
				 entNomeEmpresarial.setText(cliente.getNome_empresarial());
				 entPorte.setText(cliente.getPorte());
				 entCnae.setText(cliente.getAtividade());
				 entRg.setText(cliente.getRg());
				 
				 
			 }
			 entIE.setText(cliente.getIe());
			 entStatusIE.setText(cliente.getStatus_ie());
			 
			 entLogradouro.setText(cliente.getRua());
			 entNum.setText(cliente.getNumero());
			 entBairro.setText(cliente.getBairro());
			 entCidade.setText(cliente.getCidade());
			 entEstado.setText(cliente.getUf());
			 entCep.setText(cliente.getCep());
			 
			
			 
			 entApelido.setText(cliente.getApelido());
			 
			 //dados contatos
			 
			 ArrayList<Contato> contatos = new ArrayList<>();
			 GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
			 contatos = gerenciar.getContatos(cliente.getId());
			 
			 for(Contato contato : contatos)
			 {
			 String nome, cargo, celular, fixo, email, observacao, descricao, id;
				
			    id = Integer.toString(contato.getId());
				nome = contato.getNome();
				cargo = contato.getCargo();
				celular = contato.getCelular();
				fixo = contato.getFixo();
				email = contato.getE_mail();
				observacao = contato.getObservacao();
				descricao = contato.getDescricao();
				
				
			
				modelo.addRow(new Object[]{id, nome, cargo, 
					       celular, fixo, email, descricao, observacao});
			
			 }
			 

			 //dados conta bancaria
			 
			 ArrayList<ContaBancaria> contas_bancaria = new ArrayList<>();
			 contas_bancaria = gerenciar.getContas(cliente.getId());
			 
			 for(ContaBancaria conta_bancaria : contas_bancaria)
			 {
			 
			 String cpf, banco, codigo, agencia, conta, id, nome;
		 		
			    id = Integer.toString(conta_bancaria.getId_conta());
		 		cpf = conta_bancaria.getCpf_titular();
		 		nome = conta_bancaria.getNome();
		 		banco = conta_bancaria.getBanco();
		 		codigo = conta_bancaria.getCodigo();
		 		agencia = conta_bancaria.getAgencia();
		 		conta = conta_bancaria.getConta();

				modelo_cb.addRow(new Object[]{id, cpf,nome, banco, 
					       codigo, agencia, conta});
			 }
			 
			 
		 }
		 
		 else
		 {
			
			
		
		 }
		 
		 
		 if(flag_tipo_tela == 5)
		 {
			 
			 cBPessoa.setSelectedItem("Jurídica");
			 cBPessoa.setEnabled(false);
			 CardLayout cardLayout = (CardLayout) panelDinamico.getLayout();
		     cardLayout.show(panelDinamico, "PessoaJuridica");
			 
		    
			 
		 }
		 
		 /*
		 for(int i = 0; i < isto.getComponents().length; i++)
		 {
		
			 
			 if(isto.getComponent(i) instanceof outros.JTextFieldPersonalizado)
			 {
				 
				 JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) panelPessoaFisica.getComponent(i);
		           caixa_texto.addFocusListener(new FocusAdapter()
						 {
						@Override
						public void focusGained(FocusEvent e) {
							System.out.println("Ganhou focu");
					           caixa_texto.setFocusGained();

                              
						}
						
						@Override
						public void focusLost(FocusEvent e) {

					           caixa_texto.setFocusLost();
						}
						 });
		 }
		 }*/
		 if(flag_tipo_tela == 1) {
			 //travar para nao alterar os campos nomefantasia e nomeempresarial
			 if(cliente_atualizar.getTipo_pessoa() == 0) {
				 //pessoa fisica
				 entNomeEmpresarial.setEditable(false);
			 }else {
				 entNomeFantasia.setEditable(false);
			 }
			 
		 }
		 adicionarFocus(isto.getComponents());
		 
		 this.setLocationRelativeTo(janela_pai);
		this.setVisible(true);
		
	}


	
	
	public void getVeiculos() {
		
 	     
 	     GerenciarBancoClientes clientes = new GerenciarBancoClientes();
          ArrayList< CadastroCliente.Veiculo> veiculos = clientes.getVeiculos(cliente_global.getId());
		
		if(veiculos != null && veiculos.size() > 0) {
      for(CadastroCliente.Veiculo veiculo : veiculos) {
    	  
    	  int id = veiculo.getId_veiculo();
   		String registro_trator = veiculo.getRegistro_trator()
   		, placa_trator = veiculo.getPlaca_trator(), eixos_trator = veiculo.getEixos_trator(), tipo_trator = veiculo.getTipo_trator(), cidade_trator = veiculo.getCidade_trator(), uf_trator = veiculo.getUf_trator();
   		String registro_reboque1 = veiculo.getRegistro_reboque1(), placa_reboque1 = veiculo.getPlaca_reboque1(), eixos_reboque1 = veiculo.getEixos_reboque1(), tipo_reboque1 = veiculo.getTipo_reboque1(), cidade_reboque1 = veiculo.getCidade_reboque1(), uf_reboque1 = veiculo.getUf_reboque1();
   		String registro_reboque2 = veiculo.getRegistro_reboque2(), placa_reboque2 = veiculo.getPlaca_reboque2(), eixos_reboque2  = veiculo.getEixos_reboque2(), tipo_reboque2 = veiculo.getTipo_reboque2(), cidade_reboque2 = veiculo.getCidade_reboque2(), uf_reboque2 = veiculo.getUf_reboque2();
   		
   		
		modelo_veiculos.addRow(new Object[]{id, registro_trator, placa_trator, 
				eixos_trator, tipo_trator, cidade_trator, uf_trator});
      }
		}
	}

	public  void adicionarFocus(Component [] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				 if(c instanceof JTextFieldPersonalizado)
				 {
					 
					 JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
			           caixa_texto.addFocusListener(new FocusAdapter()
							 {
							@Override
							public void focusGained(FocusEvent e) {
								System.out.println("Ganhou focu");
						           caixa_texto.setFocusGained();

	                              
							}
							
							@Override
							public void focusLost(FocusEvent e) {

						           caixa_texto.setFocusLost();
							}
							 });
			 }
			} else {
				     Container novo_container = (Container) c;
                               // if (0 < novo_container.getComponents())
				     {
                                	adicionarFocus(novo_container.getComponents());
                                }
                        }
		}
	}
	
	public void getDadosBancarios(CadastroCliente cadastro)
	{
		
 	//dados conta bancaria
 	  int num_row_table_bc = table_cb.getRowCount();
	 	
	 	   System.out.println("Numero de contas bancarias: "+ num_row_table_bc);
	 	   ArrayList<ContaBancaria> contas = new ArrayList<>();
	 	   for(int i = 0; i<num_row_table_bc;i++)
	 	   {
	 		   String id = table_cb.getValueAt(i, 0).toString();
	 		   String cpf = table_cb.getValueAt(i, 1).toString();
	 		   String nome = table_cb.getValueAt(i, 2).toString();
	 		   String banco =  table_cb.getValueAt(i, 3).toString();
	 		   String codigo =  table_cb.getValueAt(i, 4).toString();
	 		   String agencia =  table_cb.getValueAt(i, 5).toString();
	 		   String conta =  table_cb.getValueAt(i, 6).toString();
          
	 		  if(!id.equals("0000")) { 
	 			  
	 		  }else {
	 		   ContaBancaria ct = new ContaBancaria();
	 		   
	 		   ct.setNome(nome);
	 		   ct.setCpf_titular(cpf);
	 		   ct.setBanco(banco);
	 		   ct.setCodigo(codigo);
	 		   ct.setAgencia(agencia);
	 		   ct.setConta(conta);
	 		   
	 		   contas.add(ct);
	 		  }
	 		   
	 	   }
	 	   //CadastroCliente cliente : listaClientes.getClientes()
	 	  for(ContaBancaria conta : contas)
	 	   {
	 		   System.out.println("CPF: "+ conta.getCpf_titular());
	 		   System.out.println("Banco: "+ conta.getBanco());
	 		   System.out.println("Codigo: "+ conta.getCodigo());
	 		   System.out.println("Agencia: "+ conta.getAgencia());
	 		   System.out.println("Conta: "+ conta.getConta());

	 		   
	 	   }
	 	   
	 	 cadastro.setContas(contas);
	 	
 	
	}
	
	public void getDadosVeiculos(CadastroCliente cadastro)
	{
		ArrayList<CadastroCliente.Veiculo> veiculos = new ArrayList<>();
 	//dados conta bancaria
 	  int num_row_table_veiculos = table_veiculos.getRowCount();
	 	
	 	   System.out.println("Numero de contas veiculos: "+ num_row_table_veiculos);
	 	   ArrayList<CadastroCliente.Veiculo> contas = new ArrayList<>();
	 	   for(int i = 0; i<num_row_table_veiculos;i++)
	 	   {
	 		   String id = table_veiculos.getValueAt(i, 0).toString();
	 		   String registro_trator = table_veiculos.getValueAt(i, 1).toString();
	 		   String placa_trator = table_veiculos.getValueAt(i, 2).toString();
	 		   String eixos_trator =  table_veiculos.getValueAt(i, 3).toString();
	 		   String tipo_trator =  table_veiculos.getValueAt(i, 4).toString();
	 		   String cidade_trator =  table_veiculos.getValueAt(i, 5).toString();
	 		   String uf_trator =  table_veiculos.getValueAt(i, 6).toString();
          
	 		  if(!id.equals("0000")) { 
	 			  
	 		  }else {
	 			 CadastroCliente.Veiculo ct = new CadastroCliente.Veiculo();
	 		   
	 		   ct.setRegistro_trator(registro_trator);
	 		   ct.setPlaca_trator(placa_trator);
	 		   ct.setEixos_trator(eixos_trator);
	 		   ct.setTipo_trator(tipo_trator);
	 		   ct.setCidade_trator(cidade_trator);
	 		   ct.setUf_trator(uf_trator);
	 		   
	 		   veiculos.add(ct);
	 		  }
	 		   
	 	   }
	 	   //CadastroCliente cliente : listaClientes.getClientes()
	 	  for( CadastroCliente.Veiculo veiculo : veiculos)
	 	   {
	 		
	 		   
	 	   }
	 	   
	 	 cadastro.setVeiculos(veiculos);
 	
	}
	
	
	public boolean atualizar(int flag_armazem)
	{
         boolean permitir_cadastro = false;
 		
 		
 		if(flag_armazem == 5 || flag_armazem == 6)
 			cliente_atualizar.setArmazem(1);
 		else
 			cliente_atualizar.setArmazem(0);

 		
 		if(cBPessoa.getSelectedItem().equals("Física"))
 		{
 		
		 		 permitir_cadastro = getDadosPessoaFisica(cliente_atualizar);			
		 	     System.out.println("Dados de pessoa fisica foram adicionados para atualizar? - " + permitir_cadastro);
	 		
 		}
 		else //dados pessoa juridica
 		{
	 		 permitir_cadastro = getDadosPessoaJuridica(cliente_atualizar);			
	 	     System.out.println("Dados de pessoa juridica foram adicionados para atualizar? - " + permitir_cadastro);

 		}//fim de dados pessoa juridica
 		
 		
 	if(permitir_cadastro)
 	{
 		getDadosContato(cliente_atualizar);
 		getDadosEmpresa(cliente_atualizar);
 		getDadosBancarios(cliente_atualizar);
 		getDadosVeiculos(cliente_atualizar);
 	}
 	//dados de apelido e finalizar contrato
 	
  	permitir_cadastro = getDadosFinais(cliente_atualizar);
     System.out.println("Dados finais foram adicionados para atualizar? - " + permitir_cadastro);
	   
     GerenciarBancoClientes atualizar = new GerenciarBancoClientes();

 	
    if(permitir_cadastro)
 	   {
 		   //excluir contatos que tiver para excluir
 		  for( Integer id_contato : contatos_excluir)
 		  {
 			  if ( atualizar.deleteContato(id_contato, cliente_atualizar.getId()) == false)
 			  {
 	               JOptionPane.showMessageDialog(isto, "Erro ao deletar os contatos, corrupção no banco de dados!");
 	              permitir_cadastro = false;                 
 				  break;
 			  }
 			  else
 			  {
 				 permitir_cadastro = true;

 			  }
 		  }
		   //excluir contas que tiver para excluir

 		  if(permitir_cadastro)
 		  {
 			 for( Integer id_conta : contas_excluir)
 	 		  {
 	 			  if ( atualizar.deleteConta(id_conta, cliente_atualizar.getId()) == false)
 	 			  {
 	 	               JOptionPane.showMessageDialog(isto, "Erro ao deletar os contatos, corrupção no banco de dados!");
 	 	             permitir_cadastro = false;                 
 	 				  break;
 	 			  }
 	 			  else
 	 			  {
 	 				permitir_cadastro = true;

 	 			  }
 	 		  }
 		  }
 		  
 		  
 		  //excluir veiculos que tiver pra excluir
 		 if(permitir_cadastro)
		  {
			 for( Integer id_veiculo : veiculos_excluir)
	 		  {
	 			  if ( atualizar.deleteVeiculo(cliente_atualizar.getId(), id_veiculo) == false)
	 			  {
	 	               JOptionPane.showMessageDialog(isto, "Erro ao deletar os veiculo, corrupção no banco de dados!");
	 	             permitir_cadastro = false;                 
	 				  break;
	 			  }
	 			  else
	 			  {
	 				permitir_cadastro = true;
	 	               JOptionPane.showMessageDialog(isto, "Veiculo Excluido");

	 			  }
	 		  }
		  }
		  
 		  //adicionar veiculos que tiver pra atualizar

		  if(permitir_cadastro)
		  {
			 if(cliente_atualizar.getVeiculos().size() > 0 && cliente_atualizar != null) {
				//ha novos veiculos a serem inseridos
	 			  if ( atualizar.inserirVeiculos(cliente_atualizar.getVeiculos(), cliente_atualizar.getId()) == 1)
	 			  {
	 	              
	 				permitir_cadastro = true;
	 	               JOptionPane.showMessageDialog(isto, "Veiculo Adicionado");
	 			  }
	 			  else
	 			  {
	 				 JOptionPane.showMessageDialog(isto, "Erro ao deletar incluir veiculos, corrupção no banco de dados!");
	 	             permitir_cadastro = false;                 

	 			  }
			 }
	 		  
		  }
 		  
 		 //adicionar contas bancarias
 		  if(permitir_cadastro)
 		  {
 			  if(cliente_atualizar.getContas().size() > 0) {
              GerenciarBancoClientes.RegistroAdicionarContaBancaria adicionar_contas = null;
              adicionar_contas = atualizar.adicionarContaBancaria(cliente_atualizar.getContas(), cliente_atualizar.getId());
              if(adicionar_contas.isResposta() == true && adicionar_contas.ids_contas.size() > 0) {
            	  permitir_cadastro = true;
               }
               else {
            	   permitir_cadastro = false;
               }
 			  }
 		  }
 		  
 		  //adicionar contatos
 		 if(permitir_cadastro)
		  {
 			 if(cliente_atualizar.getContatos().size() > 0) {
             GerenciarBancoClientes.RegistroAdicionarContato adicionar_contatos = null;
             adicionar_contatos = atualizar.adicionarContato(cliente_atualizar.getContatos(), cliente_atualizar.getId());
             if(adicionar_contatos.isResposta() == true && adicionar_contatos.ids_contatos.size() > 0) {
           	  permitir_cadastro = true;
              }
              else {
           	   permitir_cadastro = false;
              }
 			 }
		  }
 		 
 		 if(permitir_cadastro) { 
 		   System.out.println("tentando atualizar cliente");	 
 		   JOptionPane.showMessageDialog(null, "RNTRC: " + cliente_atualizar.getRntrc() + " Status RNTRC: " + cliente_atualizar.getStatus_cadastro());
 		   boolean atualizou = atualizar.atualizarClienteTransportador(cliente_atualizar);
 		   if(atualizou) {
               JOptionPane.showMessageDialog(isto, "Cadastro Atualizado!");
               //isto.dispose();
               permitir_cadastro =  true;

 		   }
 		   else
 		   {
               JOptionPane.showMessageDialog(isto, "Erro ao atualizar");
               permitir_cadastro =  false;

 		   }
      
 	   }
 	   }
    
	return permitir_cadastro;
 	
		
	}
	

	public boolean getDadosFinais(CadastroCliente cadastro)
	{
		boolean retorno;
	 	
	 		String apelido = entApelido.getText().toString();
	 		String registro_transportador = entRegistroTransportador.getText();
	 		
	 	  cadastro.setRntrc(registro_transportador);
	 		if(apelido == null || apelido.equals("") || apelido.equals(" "))
	 		{
	 			retorno = false;
	               JOptionPane.showMessageDialog( isto, "Informe um Alias para o novo Transportador");

	 		}
	 		else
	 		{
	 			String id_cliente = lblCodigo.getText();
	 			int id = Integer.parseInt(id_cliente);
	 			if(id > 0)
	 				cadastro.setId(id);
	 			
	 			cadastro.setApelido(apelido);
	 			
	 			retorno = true;

	 		}
	 	
	 	
	 	return retorno;
	}
	
	public boolean getDadosPessoaFisica(CadastroCliente cadastro)
	{
		
		boolean retorno = false;
		
		
		
		String cpf = entCpf.getText().toString();
 		
		if(cpf.length() == 0) {
			//cpf em branco, permite o cadastro
			
 			cadastro.setTipo_pessoa(0);
 			
 			String nome_empresarial = entNomeEmpresarial.getText().toString();
 			String nome = entNome.getText().toString();
				String sobrenome = entSobrenome.getText().toString();
				String data_nascimento = entNascimento.getText().toString();
				String rg = entRg.getText().toString();
				String ocupacao = entOcupacao.getText().toString();
				String porte = entPorte.getText().toString();
				String cnae = entCnae.getText().toString();

 			
				cadastro.setNome_empresarial(nome_empresarial);
 			
				cadastro.setNome(nome);
				cadastro.setSobrenome(sobrenome);
 				
 				
				cadastro.setNascimento(data_nascimento);	
 			
				cadastro.setRg(rg);
 				
 				
				cadastro.setOcupacao(ocupacao);
 					
				cadastro.setPorte(porte);
	 					
				cadastro.setAtividade(cnae);
				retorno = true;
			
		}else {
		
		CPFValidator cpfValidator = new CPFValidator(); 
 		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
 		if(erros.size() > 0) {
            JOptionPane.showMessageDialog(isto, "CPF Inválido!");
            retorno = false;
 		}
 		else
 		{
 			cpf = cpf.replace(".", "");
 			cpf = cpf.replace("-", "");
 			System.out.println(cpf);
 			cadastro.setCpf(cpf);
 			cadastro.setTipo_pessoa(0);
 			
 			String nome_empresarial = entNomeEmpresarial.getText().toString();
 			String nome = entNome.getText().toString();
				String sobrenome = entSobrenome.getText().toString();
				String data_nascimento = entNascimento.getText().toString();
				String rg = entRg.getText().toString();
				String ocupacao = entOcupacao.getText().toString();
				String porte = entPorte.getText().toString();
				String cnae = entCnae.getText().toString();

 			
				cadastro.setNome_empresarial(nome_empresarial);
 			
				cadastro.setNome(nome);
				cadastro.setSobrenome(sobrenome);
 				
 				
				cadastro.setNascimento(data_nascimento);	
 			
				cadastro.setRg(rg);
 				
 				
				cadastro.setOcupacao(ocupacao);
 					
				cadastro.setPorte(porte);
	 					
				cadastro.setAtividade(cnae);
				retorno = true;
		 	}
		}
 		
 		return retorno;
	}
	
	public boolean getDadosPessoaJuridica(CadastroCliente cadastro)
	{
		boolean retorno = false;
		String cnpj = entCNPJ.getText();
		cnpj = cnpj.replaceAll("[^0-9]+", "");
		ValidaCNPj valida = new ValidaCNPj();
		
		if(cnpj.length() != 14)
		{
            JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");
			retorno = false;

		}
		else {
			if(valida.isCNPJ(cnpj))
			{
				cadastro.setTipo_pessoa(1);

				cadastro.setCnpj(cnpj.replaceAll("[^0-9]", ""));
				String razao_social = entRazaoSocial.getText().toString();
				String nome_fantasia = entNomeFantasia.getText().toString();
				String status = entStatus.getText().toString();
				String descricao = entDescricao.getText().toString();
				String ati_pri = entAtividadesPri.getText().toString();
				String ati_sec = entAtividadeSec.getText().toString();

				cadastro.setRazao_social(razao_social);
					
				cadastro.setNome_fantaia(nome_fantasia);
						
				cadastro.setStatus(status);
							
				cadastro.setDescricao(descricao);
								
				cadastro.setAt_primaria(ati_pri);
				cadastro.setAt_secundaria(ati_sec);
				retorno = true;
				
			}
			else
			{
	            JOptionPane.showMessageDialog(isto, "CNPJ Invalido!");
	            retorno = false;

			}
		}
			return retorno;
	}
	
	

	public void getDadosContato(CadastroCliente cadastro )
	{
		//dados contato
	 		 int num_row_table = table.getRowCount();
		 	   System.out.println("Numero de Contatos : "+ num_row_table);
		 	   ArrayList<Contato> contatos = new ArrayList<>();
		 	   for(int i = 0; i<num_row_table;i++)
		 	   {
		 		
                   String id = table.getValueAt(i, 0).toString();
		 		   String nome = table.getValueAt(i, 1).toString();
		 		   String cargo =  table.getValueAt(i, 2).toString();
		 		   String celular =  table.getValueAt(i, 3).toString();
		 		   String fixo =  table.getValueAt(i, 4).toString();
		 		   String e_mail =  table.getValueAt(i, 5).toString();
		 		   String descricao =  table.getValueAt(i,6).toString();
		 		   String observacao =  table.getValueAt(i, 7).toString();

               if(!id.equals("0000")) {
            	   
               }else {
		 		   Contato ct = new Contato();
		 		   ct.setId(0000);
		 		   ct.setNome(nome);
		 		   ct.setCargo(cargo);
		 		   ct.setCelular(celular);
		 		   ct.setFixo(fixo);
		 		   ct.setE_mail(e_mail);
		 		   ct.setDescricao(descricao);
		 		   ct.setObservacao(observacao);
		 		   
		 		  contatos.add(ct);
               }
		 		   
		 	   }
		 	   //CadastroCliente cliente : listaClientes.getClientes()
		 	  for(Contato contato : contatos)
		 	   {
		 		   System.out.println("Nome: "+ contato.getNome());
		 		   System.out.println("Cargo: "+ contato.getCargo());
		 		   System.out.println("Celular: "+ contato.getCelular());
		 		   System.out.println("Fixo: "+ contato.getFixo());
		 		   System.out.println("E-mail: "+ contato.getE_mail());
		 		   System.out.println("Descrição: "+ contato.getDescricao());
		 		   System.out.println("Observação: "+ contato.getObservacao());


		 		   
		 	   }
		 	   
		 	 cadastro.setContatos(contatos);
		 	   
	 	
	}
	
	public void getDadosEmpresa(CadastroCliente cadastro)
	{
		//dados da empresa
	 		String ie = entIE.getText().toString();
	 		
	 		cadastro.setIe(ie);
	 			String status_ie = entStatusIE.getText().toString();
	 			
	 			cadastro.setStatus_ie(status_ie);
	 				String rua = entLogradouro.getText().toString();
	 				
	 				cadastro.setRua(rua);
	 					String numero = entNum.getText().toString();
	 					
	 					cadastro.setNumero(numero);
	 						String cep = entCep.getText().toString();
	 						
	 						cadastro.setCep(cep);
	 							String bairro = entBairro.getText().toString();
	 							
	 							cadastro.setBairro(bairro);
	 								String cidade = entCidade.getText().toString();
	 							
	 								cadastro.setCidade(cidade);
	 									String uf = entEstado.getText().toString();
	 									
	 									cadastro.setUf(uf);


	}
	
	public boolean salvar()
	{
 		
	 	boolean retorno = false;

 		boolean permitir_cadastro = false;
 		
 		
 		
 			cliente_cadastrar.setArmazem(0);
 			cliente_cadastrar.setTransportador(1);
 			
 	

 		
 		if(cBPessoa.getSelectedItem().equals("Física"))
 		{
 		
		 		 permitir_cadastro = getDadosPessoaFisica(cliente_cadastrar);			
		 				
	 		
 		}
 		else //dados pessoa juridica
 		{
	 		 permitir_cadastro = getDadosPessoaJuridica(cliente_cadastrar);			

 		}//fim de dados pessoa juridica
 		
 		
 	if(permitir_cadastro)
 	{
 		getDadosContato(cliente_cadastrar);
 		getDadosEmpresa(cliente_cadastrar);
 		getDadosBancarios(cliente_cadastrar);
 		getDadosVeiculos(cliente_cadastrar);
 		
 		//dados de apelido e finalizar contrato
 	 	
 	 	permitir_cadastro = getDadosFinais(cliente_cadastrar);
 	 	
 	    if(permitir_cadastro)
 	 	   {
 	 		   GerenciarBancoClientes cadastrar = new GerenciarBancoClientes();
 	 		   boolean cadastrou = cadastrar.inserir(cliente_cadastrar);
 	 		   if(cadastrou) {
 	               JOptionPane.showMessageDialog(isto, "Cadastro Completo!");
 	               //isto.dispose();
 	               retorno =  true;

 	 		   }
 	 		   else
 	 		   {
 	               JOptionPane.showMessageDialog(isto, "Erro ao Cadastrar");
 	               retorno =  false;

 	 		   }
 	      
 	 	   }

 	}else {
 		retorno = false;
 	}
 	
	return retorno;
 	
 	}

	
	public void gerarPastas() {
		ManipularTxt manipularArquivos = new ManipularTxt();
		String nome_pasta;
		
		if(cliente_cadastrar.getTipo_pessoa() == 0) {
			nome_pasta = cliente_cadastrar.getNome_empresarial().toUpperCase();
		}else {
			nome_pasta = cliente_cadastrar.getNome_fantaia().toUpperCase();
		}
		
	
		int ano_atual  = new GetData().getAnoAtual();
		
		String ano_passado_pasta = Integer.toString(ano_atual - 1);
		String ano_atual_pasta = Integer.toString(ano_atual);
		String ano_que_vem_pasta = Integer.toString(ano_atual + 1);

		
		DadosGlobais dados = DadosGlobais.getInstance();
		 configs_globais = dados.getConfigs_globais();
		 
		
		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta= "E-Contract\\arquivos\\clientes";
		
		String compra = "COMPRA";
		String venda = "VENDA";
		
		String soja = "SOJA";
		String sorgo = "SORGO";
		String milho = "MILHO";
		
		String compra_venda[] = {"COMPRA", "VENDA"};
		String produtos[] = {"SOJA", "MILHO", "SORGO"};
		String anos[] = {ano_passado_pasta, ano_atual_pasta, ano_que_vem_pasta};
		
		for(int cv = 0; cv <= 1 ; cv++) {
			for(int ano = 0 ; ano <= 2; ano++) {
			  for(int produto = 0; produto <= 2; produto++) {
				  String caminho_completo = unidade_base_dados + "\\" + sub_pasta + 
						  "\\" + nome_pasta.toUpperCase() + "\\" +"CONTRATOS" 
						  + "\\" + compra_venda[cv] + "\\" 
						  + anos[ano] + "\\"
						  + produtos[produto]; 
					manipularArquivos.criarDiretorio(caminho_completo);
			  }
				 
			}
		}
		
		
		//criar diretorio de dados pessoas
		
		  String caminho_completo = unidade_base_dados + "\\" + sub_pasta + 
				  "\\" + nome_pasta.toUpperCase() + "\\" + "DADOS PESSOAS";
	  
			manipularArquivos.criarDiretorio(caminho_completo);

			//criar diretorio de notas fiscais
			  String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + 
					  "\\" + nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS";
		  
				manipularArquivos.criarDiretorio(caminho_completo_nf);
		
	}
	
	public void gerarPastasAtualizar() {
		ManipularTxt manipularArquivos = new ManipularTxt();
		String nome_pasta;
		
		if(cliente_atualizar.getTipo_pessoa() == 0) {
			nome_pasta = cliente_atualizar.getNome_empresarial().toUpperCase();
		}else {
			nome_pasta = cliente_atualizar.getNome_fantaia().toUpperCase();
		}
		
	
		int ano_atual  = new GetData().getAnoAtual();
		
		String ano_passado_pasta = Integer.toString(ano_atual - 1);
		String ano_atual_pasta = Integer.toString(ano_atual);
		String ano_que_vem_pasta = Integer.toString(ano_atual + 1);

		
		DadosGlobais dados = DadosGlobais.getInstance();
		 configs_globais = dados.getConfigs_globais();
		 
		
		String unidade_base_dados = configs_globais.getServidorUnidade();
		String sub_pasta= "E-Contract\\arquivos\\clientes";
		
		String compra = "COMPRA";
		String venda = "VENDA";
		
		String soja = "SOJA";
		String sorgo = "SORGO";
		String milho = "MILHO";
		
		String compra_venda[] = {"COMPRA", "VENDA"};
		String produtos[] = {"SOJA", "MILHO", "SORGO"};
		String anos[] = {ano_passado_pasta, ano_atual_pasta, ano_que_vem_pasta};
		
		for(int cv = 0; cv <= 1 ; cv++) {
			for(int ano = 0 ; ano <= 2; ano++) {
			  for(int produto = 0; produto <= 2; produto++) {
				  String caminho_completo = unidade_base_dados + "\\" + sub_pasta + 
						  "\\" + nome_pasta.toUpperCase() + "\\" +"CONTRATOS" 
						  + "\\" + compra_venda[cv] + "\\" 
						  + anos[ano] + "\\"
						  + produtos[produto]; 
					manipularArquivos.criarDiretorio(caminho_completo);
			  }
				 
			}
		}
		
		
		//criar diretorio de dados pessoas
		
		  String caminho_completo = unidade_base_dados + "\\" + sub_pasta + 
				  "\\" + nome_pasta.toUpperCase() + "\\" + "DADOS PESSOAS";
	  
			manipularArquivos.criarDiretorio(caminho_completo);

		//criar diretorio de notas fiscais
			  String caminho_completo_nf = unidade_base_dados + "\\" + sub_pasta + 
					  "\\" + nome_pasta.toUpperCase() + "\\" + "NOTAS FISCAIS";
		  
				manipularArquivos.criarDiretorio(caminho_completo_nf);

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
