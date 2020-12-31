package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroContrato.CadastroPagamento;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroSafra;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoSafras;
import cadastros.ContaBancaria;
import classesExtras.CBLocalRetiradaPersonalizado;
import classesExtras.CBLocalRetiradaRenderPersonalizado;
import classesExtras.ComboBoxPersonalizado;
import classesExtras.ComboBoxRenderPersonalizado;
import manipular.ArquivoConfiguracoes;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.EditarExcel;
import manipular.EditarWord;
import manipular.Excel;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.GetData;
import tratamento_proprio.Log;
import views_personalizadas.TelaEmEspera;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.SystemColor;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;


public class TelaElaborarNovoContrato extends JDialog{

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	
	private JPanel painelDadosProdutos = new JPanel();

	private JPanel painelEmpresa = new JPanel();
	private JPanel painelFinalizar = new JPanel();
	private JComboBox cBComprador;
	private CadastroContrato novo_contrato = new CadastroContrato();
	private JComboBox cBVendedor1;
	private JTextField entQuantidade;
	private JTextField entPreco;
	private JRadioButton rQuanS, rQuanT, rQuanKG;
	private JLabel lblQuant, lblPreco, lblValorTotal;
	private JLabel mostrar_soma_atual_pagamentos;
	private JRadioButton rBComissaoSim, rBComissaoNao;
	
	private static ArrayList<CadastroSafra> safras = new ArrayList<>();
	private JLabel lblValorRestante;
	
	private  JLabel mostrar_valor_total_contrato;
	private JPanel painel_table_cb;
	private JTable table_cb;
	
	private DefaultTableModel modelo = new DefaultTableModel();
	private DefaultTableModel modelo_cb = new DefaultTableModel();
	private JTextField entDataPagamento, entDataEntrega;
	private JTextField entValorPagamento;
	private JComboBox cBContaBancaria;
	private JComboBox cBSafra;
	private JComboBox cBLocalRetirada;
	
	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private CBLocalRetiradaPersonalizado modelLocalRetirada = new CBLocalRetiradaPersonalizado();
	
	private ContaBancaria conta_atual;
	
	private BigDecimal valor_total;
	private BigDecimal valor_atual = new BigDecimal("0");
	private BigDecimal valor_acumulado = new BigDecimal("0");

    private ArrayList<CadastroContrato.CadastroPagamento> pagamentos = null;
    private JTextField entComissao;
    
	
    private JLabel lblValorTotalComisao1, mostrar_valor_total_comissao; 
	BigDecimal valor_total_comissao = new BigDecimal("0");
    BigDecimal valor_atual_comissao = new BigDecimal("0");
    
    private JLabel lblNomeComprador, lblCpfCnpj, lblEndereco, lblIE, lblBairro, lblEstado, lblMunicipio;
    private JLabel lblNomeVendedor, lblCpfCnpjVendedor, lblEnderecoVendedor, lblIEVendedor, lblBairroVendedor, lblEstadoVendedor, lblMunicipioVendedor;

    private JPanel painelDadosAdicionais = new JPanel();
    private CadastroSafra safra_selecionada;
    private JCheckBox chBoxClausula1, chBoxClausula2, chBoxClausula3, chBoxClausula4, chBoxClausula5, chBoxClausula6;

    
    private JRadioButton rdbtnSobreRodas, rdbtnAgendado, rdbtnAtoCarregamento, rdbtnAVista, rdbtnInfoFavorSim, rdbtnInfoFavorNao;
    
    private ComboBoxRenderPersonalizado cBSafraPersonalizado;
    private String unidadeGlobal = "quilogramas";
    
private static ArrayList<CadastroCliente> armazens = new ArrayList<>();
private JTextField textField;
private JLabel lblData;
private JTextField entDataContrato;

private JCheckBox chbxDataAtual, chBoxClausulaComissao;
private JLabel lbltext;
	
private EditarExcel editar; 
private EditarWord  editarWord;
private JPanel panelDadosVendedor;
private JPanel painelDadosCodigoData;
private JPanel painelDefinirPartes;
private JLabel lblCorretor;
private JComboBox cBCorretor, cBVendedor2;
private JPanel panelDadosVendedor2;
private JLabel lblEstado_1_2;
private JLabel lblBairro2_2;
private JLabel lblIe_2;
private JLabel lblIEVendedor2;
private JLabel lblBairroVendedor2;
private JLabel lblEstadoVendedor2;
private JLabel lblVendedor_2;
private JLabel lblCpfcnpj_2;
private JLabel lblEndereo_3;
private JLabel lblEndereo_1_2;
private JLabel lblMunicipioVendedor2;
private JLabel lblEnderecoVendedor2;
private JLabel lblCpfCnpjVendedor2;
private JLabel lblNomeVendedor2;
private JButton btnPesquisarCorretor;

private JLabel lblNomeCorretor, lblIECorretor, lblBairroCorretor,lblEstadoCorretor,lblCpfCnpjCorretor,lblEnderecoCorretor,lblMunicipioCorretor;
private JTextField entClausula1;
private JTextField entClausula2;
private JTextField entClausula3;
private JTextField entClausula6;
private JTextField entClausula5;
private JTextField entClausula4;
private JPanel panel;
private Log GerenciadorLog;
private CadastroLogin login;
private ConfiguracoesGlobais configs_globais = new ConfiguracoesGlobais();
private CadastroContrato contrato_pai_local;


	public static void pesquisarArmazens()
	{ 
     GerenciarBancoClientes listaArmazens = new GerenciarBancoClientes();
     armazens = listaArmazens.getClientes(-1, -1, null);
    
		
	}
    
	public static void pesquisarSafras()
	{
		  GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		    safras = listaSafras.getSafras();
	}
	TelaElaborarNovoContrato isto = this;
	private JLabel lblCodigoContrato;
	private JLabel lblCodigoContratoComprador;
	private JLabel lblCodigoContratoVendedor;
	private JLabel lblCodigoContratoAleatorio;
	private JLabel lblCodigoSubContrato;
	
	private int flag_edicao_global = -1;

    
	private ArrayList<Integer> pagamento_a_excluir = null;
	
	public TelaElaborarNovoContrato(CadastroModelo modelo, int tipoContrato, CadastroContrato contrato_pai, int flag_edicao) {
		
		getDadosGlobais();

		setModal(true);
		
		flag_edicao_global = flag_edicao;
		
		contrato_pai_local = contrato_pai;
		
		if(flag_edicao == 0) {
			//modo de criacao de novo contrato
			try {
			if(tipoContrato == 0) {
				//é um contrato pai em modo criacao
				setTitle("E-Contract - Novo Contrato");

			}else {
				//é um sub contrato em modo criacao

				setTitle("E-Contract - Novo Sub-Contrato para o contrato " + contrato_pai.getCodigo());

			}
			}catch(NullPointerException nu) {
				setTitle("E-Contract - Novo Contrato");

			}
			

		}else {
		  //modo de edicao de contrato
			novo_contrato = contrato_pai;
			if(contrato_pai.getSub_contrato() == 0)
				//é um contrato pai em modo edicao
				setTitle("E-Contract - Edição de Contrato");
				else
					setTitle("E-Contract - Edição de Sub-Contrato");

		}
		
		setResizable(false);
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	
		
		
		setBounds(100, 100, 993, 669);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();
		
		getContentPane().add(painelPrincipal, BorderLayout.CENTER);
				    //contentPanel.setBackground(new Color(255, 255, 255));
				    //contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				    //setContentPane(contentPanel);
				    //contentPanel.setLayout(null);
				    
				    painelDadosIniciais.setBackground(new Color(255, 255, 255));
				    painelDadosProdutos.setBackground(new Color(255, 255, 255));
				    painelDadosAdicionais.addMouseListener(new MouseAdapter() {
				    	@Override
				    	public void mouseEntered(MouseEvent arg0) {
				    		try {
				    		CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
				    		
		    				//entClausula2.setText(safra.getProduto().getNome_produto() +" acima de 14% de umidade será cobrado uma taxa de despesas para a secagem");
				    		setClausula2(safra.getProduto().getNome_produto());
				    		}
				    		catch(Exception e)
				    		{
				    			
				    		}
				    	}
				    });
				   
				    painelDadosAdicionais.setBackground(new Color(255, 255, 255));

				    		//adiciona novos paines e suas abas
				    		painelPrincipal.addTab("Das Partes Envolvidas", painelDadosIniciais);
				    		painelDadosIniciais.setLayout(null);
				    		
				    		//adiciona o painel de produtos nas abas
				    		painelPrincipal.addTab("Do Produto", painelDadosProdutos);
				    		painelDadosProdutos.setLayout(null);
				    		
				    		//adiciona o painel de informacoes adicionais nas abas
				    		painelPrincipal.addTab("Adicionais", painelDadosAdicionais);
				    		painelDadosAdicionais.setLayout(null);
				    		
				    		JLabel lblClusulasAdicionais = new JLabel("Cláusulas Adicionais:");
				    		lblClusulasAdicionais.setBounds(130, 80, 128, 38);
				    		painelDadosAdicionais.add(lblClusulasAdicionais);
				    		
				    		panel = new JPanel();
				    		
				    		
				    				
				    		panel.setBorder(UIManager.getBorder("TitledBorder.border"));
				    		panel.setBackground(Color.WHITE);
				    		panel.setBounds(19, 130, 1256, 291);
				    		painelDadosAdicionais.add(panel);
				    		panel.setLayout(null);
				    		
				    		
				    		 chBoxClausula1 = new JCheckBox("");
				    		 chBoxClausula1.setBounds(131, 69, 48, 18);
				    		 panel.add(chBoxClausula1);
				    		 chBoxClausula1.setSelected(true);
				    		 chBoxClausula1.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		 chBoxClausula1.setEnabled(false);
				    		 
				    		 entClausula1 = new JTextField();
				    		 entClausula1.setEnabled(false);
				    		 entClausula1.setBounds(185, 62, 681, 28);
				    		 panel.add(entClausula1);
				    		 entClausula1.setEditable(false);
				    		 entClausula1.setText("A quantidade de quilogramas que exceder será negociado com o preço do dia.");
				    		 entClausula1.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		 entClausula1.setColumns(10);
				    		 
				    		 entClausula2 = new JTextField();
				    		 entClausula2.setEnabled(false);
				    		 entClausula2.setBounds(185, 95, 681, 28);
				    		 panel.add(entClausula2);
				    		 entClausula2.setEditable(false);
				    		 entClausula2.setText("Produto acima de 14% de umidade será cobrado uma taxa de despesas para a secagem\r\n");
				    		 entClausula2.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		 entClausula2.setColumns(10);
				    		 
				    		  chBoxClausula2 = new JCheckBox("");
				    		  chBoxClausula2.setBounds(131, 99, 48, 18);
				    		  panel.add(chBoxClausula2);
				    		  chBoxClausula2.setSelected(true);
				    		  chBoxClausula2.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		  chBoxClausula2.setSelected(true);
				    		  chBoxClausula2.setEnabled(false);
				    		  
				    		   chBoxClausula3 = new JCheckBox("");
				    		   chBoxClausula3.setEnabled(false);
				    		   chBoxClausula3.setBounds(131, 129, 48, 18);
				    		   panel.add(chBoxClausula3);
				    		   chBoxClausula3.addActionListener(new ActionListener() {
				    		   	public void actionPerformed(ActionEvent e) {
				    		   		
				    		   		if(chBoxClausula3.isSelected())
				    		   		{
				    		   			chBoxClausula3.setSelected(true);
				    		   			entClausula3.setEditable(true);				    		 		
				    		   			}
				    		   		else
				    		   		{
				    		   			chBoxClausula3.setSelected(false);
				    		   			entClausula3.setEditable(false);
				    		   			entClausula3.setText("");			    		 		

				    		   		}
				    		   		
				    		   	}
				    		   });
				    		   chBoxClausula3.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		   
				    		   entClausula3 = new JTextField();
				    		   entClausula3.setEnabled(false);
				    		   entClausula3.setBounds(185, 125, 681, 28);
				    		   panel.add(entClausula3);
				    		   entClausula3.setEditable(false);
				    		   entClausula3.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		   entClausula3.setColumns(10);
				    		   
				    		   entClausula4 = new JTextField();
				    		   entClausula4.setBounds(185, 159, 681, 28);
				    		   panel.add(entClausula4);
				    		   entClausula4.setEditable(false);
				    		   entClausula4.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		   entClausula4.setColumns(10);
				    		   
				    		    chBoxClausula4 = new JCheckBox("");
				    		    chBoxClausula4.setBounds(131, 159, 48, 18);
				    		    panel.add(chBoxClausula4);
				    		    chBoxClausula4.addActionListener(new ActionListener() {
					    		 	public void actionPerformed(ActionEvent e) {
					    		 		
					    		 		if(chBoxClausula4.isSelected())
					    		 		{
					    		 			chBoxClausula4.setSelected(true);
					    		 			entClausula4.setEditable(true);				    		 		
					    		 			}
					    		 		else
					    		 		{
					    		 			chBoxClausula4.setSelected(false);
					    		 			entClausula4.setEditable(false);
					    		 			entClausula4.setText("");			    		 		

					    		 		}
					    		 		
					    		 	}
					    		 });
				    		    chBoxClausula4.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		    
				    		     chBoxClausula5 = new JCheckBox("");
				    		     chBoxClausula5.setBounds(131, 189, 48, 18);
				    		     panel.add(chBoxClausula5);
				    		     chBoxClausula5.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		     
				    		      chBoxClausula6 = new JCheckBox("");
				    		      chBoxClausula6.setBounds(131, 219, 48, 18);
				    		      panel.add(chBoxClausula6);
				    		      chBoxClausula6.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		      
				    		      entClausula5 = new JTextField();
				    		      entClausula5.setBounds(185, 192, 681, 28);
				    		      panel.add(entClausula5);
				    		      entClausula5.setEditable(false);
				    		      entClausula5.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		      entClausula5.setColumns(10);
				    		      
				    		      entClausula6 = new JTextField();
				    		      entClausula6.setBounds(185, 222, 681, 28);
				    		      panel.add(entClausula6);
				    		      entClausula6.setEditable(false);
				    		      entClausula6.setFont(new Font("SansSerif", Font.PLAIN, 14));
				    		      entClausula6.setColumns(10);
				    		      chBoxClausula6.addActionListener(new ActionListener() {
					    		 	public void actionPerformed(ActionEvent e) {
					    		 		
					    		 		if(chBoxClausula6.isSelected())
					    		 		{
					    		 			chBoxClausula6.setSelected(true);
					    		 			entClausula6.setEditable(true);	

					    		 			}
					    		 		else
					    		 		{
					    		 			chBoxClausula6.setSelected(false);
					    		 			entClausula6.setEditable(false);
					    		 			entClausula6.setText("");			    		 		

					    		 		}
					    		 		
					    		 	}
					    		 });
				    		     chBoxClausula5.addActionListener(new ActionListener() {
					    		 	public void actionPerformed(ActionEvent e) {
					    		 		
					    		 		if(chBoxClausula5.isSelected())
					    		 		{
					    		 			chBoxClausula5.setSelected(true);
					    		 			entClausula5.setEditable(true);				    		 		
					    		 			}
					    		 		else
					    		 		{
					    		 			chBoxClausula5.setSelected(false);
					    		 			entClausula5.setEditable(false);
					    		 			entClausula5.setText("");			    		 		

					    		 		}
					    		 		
					    		 	}
					    		 });
				    		
				    		JLabel lblDoProduto = new JLabel("Do produto");
				    		lblDoProduto.setFont(new Font("Arial Black", Font.BOLD, 14));
				    		lblDoProduto.setBounds(424, 178, 174, 42);
				    		painelDadosProdutos.add(lblDoProduto);
				    		
				    		
				    		
				    		JLabel lblSafra = new JLabel("Safra:");
				    		lblSafra.setFont(new Font("Arial Black", Font.PLAIN, 14));
				    		lblSafra.setBounds(518, 231, 70, 42);
				    		painelDadosProdutos.add(lblSafra);
				    		
				    		JLabel lblLocalRetirada = new JLabel("Local Retirada:");
				    		lblLocalRetirada.setFont(new Font("Arial Black", Font.PLAIN, 14));
				    		lblLocalRetirada.setBounds(450, 268, 126, 42);
				    		painelDadosProdutos.add(lblLocalRetirada);
				    		
				    	    cBLocalRetirada = new JComboBox();
				    		cBLocalRetirada.setBounds(585, 269, 174, 36);
				    		cBLocalRetirada.setModel(modelLocalRetirada);
				    		cBLocalRetirada.setRenderer(new CBLocalRetiradaRenderPersonalizado());
				    		painelDadosProdutos.add(cBLocalRetirada);
				    		
				    		 pesquisarArmazens();
							    
							    for(CadastroCliente armazem : armazens)
							    {
							      if(armazem.getArmazem() == 1)
								    	modelLocalRetirada.addArmazem(armazem);

							    }
				    		
				    		JLabel lblDataEntrega = new JLabel("Data Entrega:");
				    		lblDataEntrega.setFont(new Font("Arial Black", Font.PLAIN, 14));
				    		lblDataEntrega.setBounds(461, 321, 114, 42);
				    		painelDadosProdutos.add(lblDataEntrega);
				    		
				    		entDataEntrega = new JTextField();
				    		entDataEntrega.addKeyListener(new KeyAdapter() {
				    			@Override
				    			public void keyTyped(KeyEvent pp) {
				    				String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
									String texto = entDataEntrega.getText();
									if(!caracteres.contains(pp.getKeyChar()+"")){
										pp.consume();//aciona esse propriedade para eliminar a ação do evento
									}else {
									if(texto.length()==2 && pp.getKeyChar() != '\b'){
										entDataEntrega.setText(entDataEntrega.getText().concat("/"));
									}
									if(texto.length()==5  && pp.getKeyChar() != '\b'){
										entDataEntrega.setText(entDataEntrega.getText().concat("/"));
									}
									
									if(entDataEntrega.getText().length()>=10){
										//if para saber se precisa verificar também o tamanho da string do campo
										// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
										pp.consume();
										entDataEntrega.setText(entDataEntrega.getText().substring(0,10));
									}
								
								}
				    			}
				    		});
				    		entDataEntrega.setColumns(10);
				    		entDataEntrega.setBounds(585, 321, 174, 33);
				    		painelDadosProdutos.add(entDataEntrega);
				    		
				    		JLabel lblQuantidade = new JLabel("Quantidade:");
				    		lblQuantidade.setFont(new Font("Arial Black", Font.PLAIN, 14));
				    		lblQuantidade.setBounds(209, 421, 96, 42);
				    		painelDadosProdutos.add(lblQuantidade);
				    		
				    		entQuantidade = new JTextField();
				    		entQuantidade.addKeyListener(new KeyAdapter() {
				    			@Override
				    			public void keyTyped(KeyEvent o) {
				    				
				    				String caracteres=".0987654321\b";// lista de caracters que não devem ser aceitos
				    				String Spreco, quantidade;
				    				quantidade = entQuantidade.getText();

				    				if(!caracteres.contains(o.getKeyChar()+"")){
				    				o.consume();//aciona esse propriedade para eliminar a ação do evento
				    				
				    				//quantidade = entQuantidade.getText();
				    				}
				    				else
				    				{
				    					quantidade = entQuantidade.getText() + o.getKeyChar();
				    					quantidade = quantidade.replaceAll("[^0-9]+", "");
				    				}
				    				
				    				Spreco = entPreco.getText();

				    				BigDecimal quant = null;
                		BigDecimal valor = null; 

                		System.out.println("preco e " + Spreco);
                		
                		System.out.println("quantidade e " + quantidade  );

				    				//if(quantidade != null && !(quantidade.length() <= 0) && !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) && !Spreco.equals(""))
				    				{
				    					try {
				    						quant = new BigDecimal(quantidade);

	                		
	                		BigDecimal preco = new BigDecimal(Spreco);

	                		
	                		

	                		 valor = quant.multiply(preco);
	                		valor_total = valor;
	                		valor_atual = valor_total;
	                		//valor_atual = valor_total.subtract(valor_atual);

	                		String valorTotal = valor.toPlainString();
	                		lblValorTotal.setText("R$ " + valorTotal);
	                		mostrar_valor_total_contrato.setText("R$ " + valorTotal);
			        		lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());


	                		
				    					}
				    					catch(NumberFormatException n)
				    					{
				    						System.out.println("preco e nulo " + n.getCause());
				    					
		                		BigDecimal preco = new BigDecimal("1.0");
		                		try { valor = quant.multiply(preco);
		                			String valorTotal = valor.toPlainString();
			                		lblValorTotal.setText("R$ " + valorTotal);
			                		mostrar_valor_total_contrato.setText("R$ " + valorTotal);
			                		valor_total = valor;
			                		valor_atual = valor_total;
				    			        		lblValorRestante.setText("R$ "+ valor_total.subtract(valor_acumulado).toPlainString());

			                		//valor_atual = valor_total.subtract(valor_atual);


		                		}
		                		catch(NullPointerException l)
		                		{
				    							System.out.println("quant e nulo " + l.getCause());
			                		lblValorTotal.setText("");
			                		mostrar_valor_total_contrato.setText("");

			                		

		                		}
				    					
				    					}
				    				}
				    			     
				    				
                		
               
				    				
				    			}
				    		});
				    		entQuantidade.setColumns(10);
				    		entQuantidade.setBounds(315, 428, 174, 33);
				    		painelDadosProdutos.add(entQuantidade);
				    		
				    		JLabel lblPreo = new JLabel("Preço:");
				    		lblPreo.setFont(new Font("Arial Black", Font.PLAIN, 14));
				    		lblPreo.setBounds(247, 468, 58, 42);
				    		painelDadosProdutos.add(lblPreo);
				    		
				    		entPreco = new JTextField();
				    		entPreco.addKeyListener(new KeyAdapter() {
				    			@Override
				    			public void keyTyped(KeyEvent p) {
				    				String caracteres=".0987654321\b";// lista de caracters que não devem ser aceitos
				    				String Spreco, quantidade;
				    				Spreco = entPreco.getText();

				    				if(!caracteres.contains(p.getKeyChar()+"")){
				    				p.consume();//aciona esse propriedade para eliminar a ação do evento
				    				
				    				//quantidade = entQuantidade.getText();
				    				}
				    				else
				    				{
				    					Spreco = entPreco.getText() + p.getKeyChar();
				    					Spreco = Spreco.replaceAll("[^0-9.]", "");
				    				}
				    				
				    				quantidade = entQuantidade.getText();

				    				BigDecimal preco = null;
                		BigDecimal valor = null; 

                		System.out.println("preco e " + Spreco);
                		
                		System.out.println("quantidade e " + quantidade  );

				    				//if(quantidade != null && !(quantidade.length() <= 0) && !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) && !Spreco.equals(""))
				    				{
				    					try {
				    						preco = new BigDecimal(Spreco);

	                		
	                		BigDecimal quant = new BigDecimal(quantidade);

	                		
	                		

	                		 valor = preco.multiply(quant);
	                		 
	                		
	                			
	                		String valorTotal = valor.toPlainString();
	                		lblValorTotal.setText("R$ " + valorTotal);
	                		mostrar_valor_total_contrato.setText("R$ " + valorTotal);
	                		valor_total = valor;
                            valor_atual = valor_total;
	                		//valor_atual = valor_total.subtract(valor_atual);
			        		lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());


				    					}
				    					catch(NumberFormatException q)
				    					{
				    						System.out.println("preco e nulo " + q.getCause());
				    					
		                		BigDecimal quant = new BigDecimal("1.0");
		                		try { valor = preco.multiply(quant);
		                			String valorTotal = valor.toPlainString();
			                		lblValorTotal.setText("R$ " + valorTotal);
			                		mostrar_valor_total_contrato.setText("R$ " + valorTotal);
			                		valor_total = valor;
			                		valor_atual = valor_total;
			                		//valor_atual = valor_total.subtract(valor_atual);
				    			        		lblValorRestante.setText( "R$ "+ valor_total.subtract(valor_acumulado).toPlainString());


		                		}
		                		catch(NullPointerException r)
		                		{
				    							System.out.println("quant e nulo " + r.getCause());
			                		lblValorTotal.setText("");
			                		mostrar_valor_total_contrato.setText("");

			                		

		                		}
				    					
				    					}
				    				}
				    			     
				    				
				    				
				    			}
				    		});
				    		entPreco.setColumns(10);
				    		entPreco.setBounds(315, 475, 174, 33);
				    		painelDadosProdutos.add(entPreco);
				    		
			    rQuanKG = new JRadioButton("KG");
			    rQuanKG.setSelected(true);
			    rQuanKG.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		rQuanS.setSelected(false);
			    		rQuanT.setSelected(false);
			    		
			    		lblQuant.setText("Quilos");
			    		lblPreco.setText("por Quilo");
			    		setClausula1("quilogramas");
			    		setClausulaComissao(entComissao.getText().toString().replace(".", ","));
			    		
			    		
			    		unidadeGlobal = "quilogramas";
			    		setClausulaComissao(entComissao.getText().toString().replace(".", ","));
	    			

			    		
			    	}
			    });
			    rQuanKG.setBounds(315, 389, 58, 23);
			    painelDadosProdutos.add(rQuanKG);
			    
			     rQuanS = new JRadioButton("SC");
			     rQuanS.addActionListener(new ActionListener() {
			     	public void actionPerformed(ActionEvent e) {
			     		rQuanKG.setSelected(false);
						rQuanT.setSelected(false);
						
						lblQuant.setText("Sacos");
						lblPreco.setText("por Saco");
	                    setClausula1("sacos");
			    		
			    		unidadeGlobal = "sacos";
			    		setClausulaComissao(entComissao.getText().toString().replace(".", ","));


			     	}
			     });
			     rQuanS.setBounds(383, 389, 47, 23);
			     painelDadosProdutos.add(rQuanS);
			     
			      rQuanT = new JRadioButton("TON");
			      rQuanT.addActionListener(new ActionListener() {
			      	public void actionPerformed(ActionEvent e) {
			      		rQuanKG.setSelected(false);
						rQuanS.setSelected(false);
						
						lblQuant.setText("Toneladas");
						lblPreco.setText("por Tonelada");
	                   setClausula1("toneladas");
			    		
			    		unidadeGlobal = "toneladas";

			    		setClausulaComissao(entComissao.getText().toString().replace(".", ","));

			      	}
			      });
			      rQuanT.setBounds(443, 389, 58, 23);
			      painelDadosProdutos.add(rQuanT);
			      
			       lblQuant = new JLabel("Quilos");
			       lblQuant.setEnabled(false);
			       lblQuant.setFont(new Font("Tahoma", Font.BOLD, 14));
			       lblQuant.setBounds(502, 428, 70, 28);
			       painelDadosProdutos.add(lblQuant);
			       
			        lblPreco = new JLabel("Quilo");
			        lblPreco.setEnabled(false);
			        lblPreco.setFont(new Font("Tahoma", Font.BOLD, 14));
			        lblPreco.setBounds(500, 475, 133, 28);
			        painelDadosProdutos.add(lblPreco);
			        
			        JLabel lblvalortotal = new JLabel("Valor Total:");
			        lblvalortotal.setFont(new Font("Arial Black", Font.PLAIN, 14));
			        lblvalortotal.setBounds(215, 521, 96, 42);
			        painelDadosProdutos.add(lblvalortotal);
			        
			        lblValorTotal = new JLabel("");
			        lblValorTotal.setEnabled(false);
			        lblValorTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
			        lblValorTotal.setBounds(199, 374, 174, 28);
			        painelDadosProdutos.add(lblValorTotal);
			        
	  
		 
			        
			        JSeparator separator_2 = new JSeparator();
			        separator_2.setOrientation(SwingConstants.VERTICAL);
			        separator_2.setBounds(878, 118, -186, -40);
			        painelDadosIniciais.add(separator_2);
			        
			        JPanel painelDadosComprador = new JPanel();
			        painelDadosComprador.setBorder(new CompoundBorder());
			        painelDadosComprador.setBackground(SystemColor.text);
			        painelDadosComprador.setBounds(76, 460, 589, 139);
			        
			        Border lineBorder = BorderFactory.createLineBorder(Color.black);
                    TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Comprador");
                    painelDadosComprador.setBorder(title);
			        
			        painelDadosIniciais.add(painelDadosComprador);
			        painelDadosComprador.setLayout(null);
			        
			        JLabel lblComprador = new JLabel("Nome:");
			        lblComprador.setBounds(43, 36, 83, 14);
			        painelDadosComprador.add(lblComprador);
			        lblComprador.setFont(new Font("Tahoma", Font.PLAIN, 14));
			        
			        JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
			        lblCpfcnpj.setBounds(16, 62, 83, 14);
			        painelDadosComprador.add(lblCpfcnpj);
			        lblCpfcnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
			        
			        JLabel lblEndereo = new JLabel("Endereço:");
			        lblEndereo.setBounds(26, 87, 83, 14);
			        painelDadosComprador.add(lblEndereo);
			        lblEndereo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			        
			        JLabel lblEndereo_1 = new JLabel("Municipio:");
			        lblEndereo_1.setBounds(29, 114, 70, 14);
			        painelDadosComprador.add(lblEndereo_1);
			        lblEndereo_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			        
			         lblMunicipio = new JLabel("");
			         lblMunicipio.setBounds(99, 114, 205, 16);
			         painelDadosComprador.add(lblMunicipio);
			         lblMunicipio.setFont(new Font("Tahoma", Font.PLAIN, 14));
			         
			          lblEndereco = new JLabel("");
			          lblEndereco.setBounds(99, 87, 205, 16);
			          painelDadosComprador.add(lblEndereco);
			          lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
			          
			           lblCpfCnpj = new JLabel("");
			           lblCpfCnpj.setBounds(98, 60, 206, 16);
			           painelDadosComprador.add(lblCpfCnpj);
			           lblCpfCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
			           
			           //
			            lblNomeComprador = new JLabel("");
			            lblNomeComprador.setBounds(113, 33, 448, 16);
			            painelDadosComprador.add(lblNomeComprador);
			            lblNomeComprador.setFont(new Font("Tahoma", Font.PLAIN, 14));
			            
			             lblIE = new JLabel("");
			             lblIE.setBounds(367, 60, 194, 16);
			             painelDadosComprador.add(lblIE);
			             lblIE.setFont(new Font("Tahoma", Font.PLAIN, 14));
			             
			             JLabel lblBairro2 = new JLabel("Bairro:");
			             lblBairro2.setBounds(315, 87, 83, 14);
			             painelDadosComprador.add(lblBairro2);
			             lblBairro2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			             
			              lblBairro = new JLabel("");
			              lblBairro.setBounds(367, 87, 194, 16);
			              painelDadosComprador.add(lblBairro);
			              lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
			              
			               lblEstado = new JLabel("");
			               lblEstado.setBounds(367, 112, 194, 16);
			               painelDadosComprador.add(lblEstado);
			               lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 14));
			               
			               JLabel lblEstado_1 = new JLabel("Estado:");
			               lblEstado_1.setBounds(315, 112, 47, 14);
			               painelDadosComprador.add(lblEstado_1);
			               lblEstado_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			               
			               JLabel lblIe = new JLabel("IE:");
			               lblIe.setBounds(332, 62, 27, 14);
			               painelDadosComprador.add(lblIe);
			               lblIe.setFont(new Font("Tahoma", Font.PLAIN, 14));
			               
			               panelDadosVendedor = new JPanel();
			               panelDadosVendedor.setBackground(SystemColor.text);
			               panelDadosVendedor.setBounds(699, 286, 589, 166);
			               Border lineBorder2 = BorderFactory.createLineBorder(Color.black);
		                    TitledBorder title2 = BorderFactory.createTitledBorder(lineBorder2, "Vendedor");
		                    panelDadosVendedor.setBorder(title2);
			               
			               painelDadosIniciais.add(panelDadosVendedor);
			               panelDadosVendedor.setLayout(null);
			               
			               JLabel lblEstado_1_1 = new JLabel("Estado:");
			               lblEstado_1_1.setBounds(319, 137, 47, 14);
			               panelDadosVendedor.add(lblEstado_1_1);
			               lblEstado_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			               
			               JLabel lblBairro2_1 = new JLabel("Bairro:");
			               lblBairro2_1.setBounds(319, 112, 83, 14);
			               panelDadosVendedor.add(lblBairro2_1);
			               lblBairro2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			               
			               JLabel lblIe_1 = new JLabel("IE:");
			               lblIe_1.setBounds(336, 87, 27, 14);
			               panelDadosVendedor.add(lblIe_1);
			               lblIe_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			               
			                lblIEVendedor = new JLabel("");
			                lblIEVendedor.setBounds(371, 85, 194, 16);
			                panelDadosVendedor.add(lblIEVendedor);
			                lblIEVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                
			                 lblBairroVendedor = new JLabel("");
			                 lblBairroVendedor.setBounds(371, 112, 194, 16);
			                 panelDadosVendedor.add(lblBairroVendedor);
			                 lblBairroVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                 
			                  lblEstadoVendedor = new JLabel("");
			                  lblEstadoVendedor.setBounds(371, 137, 194, 16);
			                  panelDadosVendedor.add(lblEstadoVendedor);
			                  lblEstadoVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                  
			                  JLabel lblVendedor_1 = new JLabel("Nome:");
			                  lblVendedor_1.setBounds(53, 58, 70, 14);
			                  panelDadosVendedor.add(lblVendedor_1);
			                  lblVendedor_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                  
			                  JLabel lblCpfcnpj_1 = new JLabel("CPF/CNPJ:");
			                  lblCpfcnpj_1.setBounds(30, 87, 83, 14);
			                  panelDadosVendedor.add(lblCpfcnpj_1);
			                  lblCpfcnpj_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                  
			                  JLabel lblEndereo_2 = new JLabel("Endereço:");
			                  lblEndereo_2.setBounds(30, 112, 83, 14);
			                  panelDadosVendedor.add(lblEndereo_2);
			                  lblEndereo_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                  
			                  JLabel lblEndereo_1_1 = new JLabel("Municipio:");
			                  lblEndereo_1_1.setBounds(33, 139, 70, 14);
			                  panelDadosVendedor.add(lblEndereo_1_1);
			                  lblEndereo_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                  
			                   lblMunicipioVendedor = new JLabel("");
			                   lblMunicipioVendedor.setBounds(103, 139, 205, 16);
			                   panelDadosVendedor.add(lblMunicipioVendedor);
			                   lblMunicipioVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                   
			                    lblEnderecoVendedor = new JLabel("");
			                    lblEnderecoVendedor.setBounds(103, 112, 205, 16);
			                    panelDadosVendedor.add(lblEnderecoVendedor);
			                    lblEnderecoVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                    
			                     lblCpfCnpjVendedor = new JLabel("");
			                     lblCpfCnpjVendedor.setBounds(102, 85, 206, 16);
			                     panelDadosVendedor.add(lblCpfCnpjVendedor);
			                     lblCpfCnpjVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                     
			                      lblNomeVendedor = new JLabel("");
			                      lblNomeVendedor.setBounds(120, 58, 445, 16);
			                      panelDadosVendedor.add(lblNomeVendedor);
			                      lblNomeVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                      
			                      painelDadosCodigoData = new JPanel();
			                      painelDadosCodigoData.setBackground(SystemColor.text);
			                      painelDadosCodigoData.setBounds(251, 37, 388, 154);
			                      painelDadosIniciais.add(painelDadosCodigoData);
			                      painelDadosCodigoData.setLayout(null);
			                      
			                      lblData = new JLabel("Data:");
			                      lblData.setBounds(53, 101, 52, 42);
			                      painelDadosCodigoData.add(lblData);
			                      lblData.setFont(new Font("Arial Black", Font.PLAIN, 14));
			                      
			                      entDataContrato = new JTextField();
			                      entDataContrato.setBounds(115, 110, 175, 29);
			                      painelDadosCodigoData.add(entDataContrato);
			                      entDataContrato.addKeyListener(new KeyAdapter() {
			                      	@Override
			                      	public void keyTyped(KeyEvent evt) {
			                      		String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
							String texto = entDataContrato.getText();
							if(!caracteres.contains(evt.getKeyChar()+"")){
							evt.consume();//aciona esse propriedade para eliminar a ação do evento
							}else {
							if(texto.length()==2 && evt.getKeyChar() != '\b'){
								entDataContrato.setText(entDataContrato.getText().concat("/"));
							}
							if(texto.length()==5  && evt.getKeyChar() != '\b'){
								entDataContrato.setText(entDataContrato.getText().concat("/"));
							}
							
							if(entDataContrato.getText().length()>=10){
								//if para saber se precisa verificar também o tamanho da string do campo
								// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
								evt.consume();
								entDataContrato.setText(entDataContrato.getText().substring(0,10));
							}
						
						}
			                      	}
			                      });
			                      entDataContrato.setColumns(10);
			                      
			                      
			                      //pega a data atual e salva no entDataContrato
			      
			                      entDataContrato.setText(pegarData());
			                      entDataContrato.setEditable(false);
			                      
			                       chbxDataAtual = new JCheckBox("Data Atual");
			                       chbxDataAtual.setBounds(115, 70, 89, 23);
			                       painelDadosCodigoData.add(chbxDataAtual);
			                       chbxDataAtual.addActionListener(new ActionListener() {
			                       	public void actionPerformed(ActionEvent e) {
			                       		if(chbxDataAtual.isSelected())
			                       		{
			                       			chbxDataAtual.setSelected(true);
			                       		    entDataContrato.setText(pegarData());
						        entDataContrato.setEditable(false);
			                       		}
			                       		else
			                       		{
			                       			chbxDataAtual.setSelected(false);
			                       			entDataContrato.setEditable(true);

			                       		}
			                       		
			                       		
			                       	}
			                       });
			                       chbxDataAtual.setSelected(true);
			                       
			                       lbltext = new JLabel("Código:");
			                       lbltext.setBounds(43, 15, 70, 42);
			                       painelDadosCodigoData.add(lbltext);
			                       lbltext.setFont(new Font("Arial Black", Font.PLAIN, 14));
			                       
			                       lblCodigoContrato = new JLabel("");
			                       lblCodigoContrato.setFont(new Font("Arial", Font.BOLD, 18));
			                       lblCodigoContrato.setBorder(new EmptyBorder(0, 0, 0, 0));
			                       lblCodigoContrato.setBounds(116, 15, 57, 42);
			                       painelDadosCodigoData.add(lblCodigoContrato);
			                       
			                       lblCodigoContratoComprador = new JLabel("");
			                       lblCodigoContratoComprador.setFont(new Font("Arial", Font.BOLD, 18));
			                       lblCodigoContratoComprador.setBorder(new EmptyBorder(0, 0, 0, 0));
			                       lblCodigoContratoComprador.setBounds(179, 15, 39, 42);
			                       painelDadosCodigoData.add(lblCodigoContratoComprador);
			                       
			                       lblCodigoContratoVendedor = new JLabel("");
			                       lblCodigoContratoVendedor.setFont(new Font("Arial", Font.BOLD, 18));
			                       lblCodigoContratoVendedor.setBorder(new EmptyBorder(0, 0, 0, 0));
			                       lblCodigoContratoVendedor.setBounds(228, 15, 52, 42);
			                       painelDadosCodigoData.add(lblCodigoContratoVendedor);
			                       
			                       lblCodigoContratoAleatorio = new JLabel("");
			                       lblCodigoContratoAleatorio.setBounds(290, 15, 52, 42);
			                       painelDadosCodigoData.add(lblCodigoContratoAleatorio);
			                       lblCodigoContratoAleatorio.setFont(new Font("Arial", Font.PLAIN, 18));
			                       lblCodigoContratoAleatorio.setBorder(new EmptyBorder(0, 0, 0, 0));
			                       
			                       if(flag_edicao == 0)
			                       lblCodigoContratoAleatorio.setText(Integer.toString(configs_globais.getCodigoSequencial()));
			                       else
				                       lblCodigoContratoAleatorio.setText( new String(quebrarCodigo()));

			                       lblCodigoSubContrato = new JLabel("");
			                       lblCodigoSubContrato.setFont(new Font("Arial", Font.PLAIN, 18));
			                       lblCodigoSubContrato.setBorder(new EmptyBorder(0, 0, 0, 0));
			                       lblCodigoSubContrato.setBounds(352, 15, 26, 42);
			                       painelDadosCodigoData.add(lblCodigoSubContrato);
			              
			                       
			                       painelDefinirPartes = new JPanel();
			                       painelDefinirPartes.setBackground(SystemColor.text);
			                       painelDefinirPartes.setBounds(649, 37, 688, 193);
			                       
			                       Border lineBorder1 = BorderFactory.createLineBorder(Color.black);
			                       TitledBorder title1 = BorderFactory.createTitledBorder(lineBorder, "");
			                       painelDefinirPartes.setBorder(title1);
			                       
			                       painelDadosIniciais.add(painelDefinirPartes);
			                       painelDefinirPartes.setLayout(null);
			                       
			                       
			                       JLabel lblNewLabel = new JLabel("Comprador:");
			                       lblNewLabel.setBounds(25, 48, 95, 42);
			                       painelDefinirPartes.add(lblNewLabel);
			                       lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
			                       
			                       JLabel lblVendedor = new JLabel("Vendedor:");
			                       lblVendedor.setBounds(35, 93, 83, 42);
			                       painelDefinirPartes.add(lblVendedor);
			                       lblVendedor.setFont(new Font("Arial Black", Font.PLAIN, 14));
			                       
			                       cBVendedor1 = new JComboBox();
			                       
			                      
			                       cBVendedor1.addItemListener(new ItemListener() {
			                       	public void itemStateChanged(ItemEvent e) {
			                       		
			                       		
			                       	}
			                       });
			                       cBVendedor1.addKeyListener(new KeyAdapter() {
			                       	@Override
			                       	public void keyPressed(KeyEvent e) {
			                       		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				                       		TelaCliente clientes = new TelaCliente(0,2);
				                       		clientes.setTelaPai(isto);
				                       		clientes.setVisible(true);
			                       		}
			                       	}
			                       });
			                       cBVendedor1.setBounds(118, 101, 268, 29);
			                       cBVendedor1.addItem("Indefinido");
			                       painelDefinirPartes.add(cBVendedor1);
			                       
			                       
			                       cBVendedor1.addItemListener(new java.awt.event.ItemListener() {
			                    	   public void itemStateChanged(java.awt.event.ItemEvent evt) {
			                    	        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
			                    	        	if(cBVendedor1.getSelectedItem().equals("Indefinido"))
					                       		{
					                       			lblNomeVendedor.setText("");
					                       			lblCpfCnpjVendedor.setText("");
					                       			lblIEVendedor.setText("");
					                       			lblEnderecoVendedor.setText("");
					                       			lblBairroVendedor.setText("");
					                       			lblMunicipioVendedor.setText("");
					                       			lblEstadoVendedor.setText("");
					                       			novo_contrato.adicionarCorretor(0, null);
					                       			cBVendedor1.removeAllItems();
					                       			lblCodigoContratoVendedor.setText("");

					                       		}
			                    	         }

			                    	   } 

			                    	});
			                       
			                       
			                       
			                       cBComprador = new JComboBox();
			                       cBComprador.setBounds(118, 61, 268, 29);
			                       cBComprador.addItem("Indefinido");
			                       painelDefinirPartes.add(cBComprador);
			                       cBComprador.addItemListener(new java.awt.event.ItemListener() {
			                    	   public void itemStateChanged(java.awt.event.ItemEvent evt) {
			                    	        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
			                    	             if(evt.getItem().equals("Indefinido"))
			                    	             {

			                    	        

			                       			lblNomeComprador.setText("");
			                       			lblCpfCnpj.setText("");
			                       			lblIE.setText("");
			                       			lblEndereco.setText("");
			                       			lblBairro.setText("");
			                       			lblMunicipio.setText("");
			                       			lblEstado.setText("");
			                       			//cBComprador.addItem("Indefinido");
			                       			novo_contrato.adicionarComprador(0, null);
			                       			lblCodigoContratoComprador.setText("");
			                       			cBComprador.removeAllItems();
			                       			  }
			                    	         }

			                    	   } 

			                    	});
			                       
			                       
			                    
			                    
			                       cBComprador.addKeyListener(new KeyAdapter() {
			                       	@Override
			                       	public void keyPressed(KeyEvent e) {
			                       		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				                       		TelaCliente clientes = new TelaCliente(0,1);
				                       		clientes.setTelaPai(isto);
				                       		clientes.setVisible(true);
			                       		}

			                       	}
			                       });
			                     
			                       
			                       JButton btnPesquisarComprador = new JButton("Pesquisar Comprador");
			                       btnPesquisarComprador.setBounds(396, 60, 220, 33);
			                       painelDefinirPartes.add(btnPesquisarComprador);
			                       
			                       JButton btnPesquisarVendedor1 = new JButton("Pesquisar Vendedor");
			                       btnPesquisarVendedor1.setBounds(398, 100, 218, 33);
			                       painelDefinirPartes.add(btnPesquisarVendedor1);
			                       
			                       lblCorretor = new JLabel("Corretor:");
			                       lblCorretor.setFont(new Font("Arial Black", Font.PLAIN, 14));
			                       lblCorretor.setBounds(44, 12, 76, 42);
			                       painelDefinirPartes.add(lblCorretor);
			                       
			                       cBCorretor = new JComboBox();
			                       cBCorretor.addKeyListener(new KeyAdapter() {
			                       	@Override
			                       	public void keyPressed(KeyEvent e) {
			                       		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				                       		TelaCliente clientes = new TelaCliente(0,4);
				                       		clientes.setTelaPai(isto);
				                       		clientes.setVisible(true);
			                       		}
			                       	}
			                       });
			                       cBCorretor.setBounds(118, 20, 268, 29);
			                       cBCorretor.addItem("Indefinido");
			                       
			                       cBCorretor.addItemListener(new java.awt.event.ItemListener() {
			                    	   public void itemStateChanged(java.awt.event.ItemEvent evt) {
			                    	        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
			                    	        	if(cBCorretor.getSelectedItem().equals("Indefinido"))
					                       		{
					                       			lblNomeCorretor.setText("");
					                       			lblCpfCnpjCorretor.setText("");
					                       			lblIECorretor.setText("");
					                       			lblEnderecoCorretor.setText("");
					                       			lblBairroCorretor.setText("");
					                       			lblMunicipioCorretor.setText("");
					                       			lblEstadoCorretor.setText("");
					                       			novo_contrato.adicionarCorretor(0,  null);
					                       			cBCorretor.removeAllItems();
					                       		}
			                    	         }

			                    	   } 

			                    	});

			                       painelDefinirPartes.add(cBCorretor);
			                       
			                       JLabel lblVendedor2 = new JLabel("Vendedor:");
			                       lblVendedor2.setFont(new Font("Arial Black", Font.PLAIN, 14));
			                       lblVendedor2.setBounds(35, 133, 83, 42);
			                       painelDefinirPartes.add(lblVendedor2);
			                       
			                       cBVendedor2 = new JComboBox();
			                       cBVendedor2.addKeyListener(new KeyAdapter() {
			                       	@Override
			                       	public void keyPressed(KeyEvent arg0) {
			                       		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				                       		TelaCliente clientes = new TelaCliente(0,3);
				                       		clientes.setTelaPai(isto);
				                       		clientes.setVisible(true);
			                       		}
			                       	}
			                       });
			                       cBVendedor2.setBounds(118, 142, 268, 29);
			                       cBVendedor2.addItem("Indefinido");

			                       painelDefinirPartes.add(cBVendedor2);
			                       cBVendedor2.addItemListener(new java.awt.event.ItemListener() {
			                    	   public void itemStateChanged(java.awt.event.ItemEvent evt) {
			                    	        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
			                    	        	if(cBVendedor2.getSelectedItem().equals("Indefinido"))
					                       		{
					                       			lblNomeVendedor2.setText("");
					                       			lblCpfCnpjVendedor2.setText("");
					                       			lblIEVendedor2.setText("");
					                       			lblEnderecoVendedor2.setText("");
					                       			lblBairroVendedor2.setText("");
					                       			lblMunicipioVendedor2.setText("");
					                       			lblEstadoVendedor2.setText("");

					                       			novo_contrato.adicionarVendedor(1, null);
					                       			cBVendedor2.removeAllItems();
					                       			lblCodigoContratoVendedor.setText("");

					                       		}
			                    	         }

			                    	   } 

			                    	});
			                       JButton btnPesquisarVendedor2 = new JButton("Pesquisar Vendedor");
			                       btnPesquisarVendedor2.addActionListener(new ActionListener() {
			                       	public void actionPerformed(ActionEvent arg0) {
			                       		TelaCliente clientes = new TelaCliente(0,3);
			                       		clientes.setTelaPai(isto);
			                       		clientes.setVisible(true);

			                       	}
			                       });
			                       btnPesquisarVendedor2.setBounds(398, 139, 218, 33);
			                       painelDefinirPartes.add(btnPesquisarVendedor2);
			                       
			                       btnPesquisarCorretor = new JButton("Pesquisar Corretor");
			                       btnPesquisarCorretor.addActionListener(new ActionListener() {
			                       	public void actionPerformed(ActionEvent e) {
			                       		TelaCliente clientes = new TelaCliente(0,4);
			                       		clientes.setTelaPai(isto);
			                       		clientes.setVisible(true);

			                       	}
			                       });
			                       btnPesquisarCorretor.setBounds(396, 18, 220, 33);
			                       painelDefinirPartes.add(btnPesquisarCorretor);
			                       
			                       panelDadosVendedor2 = new JPanel();
			                       panelDadosVendedor2.setLayout(null);
			                       panelDadosVendedor2.setBackground(Color.WHITE);
			                       panelDadosVendedor2.setBounds(699, 474, 589, 166);
			                       Border lineBorder3 = BorderFactory.createLineBorder(Color.black);
				                    TitledBorder title3 = BorderFactory.createTitledBorder(lineBorder3, "Vendedor");
				                    panelDadosVendedor2.setBorder(title3);
			                       painelDadosIniciais.add(panelDadosVendedor2);
			                       
			                       lblEstado_1_2 = new JLabel("Estado:");
			                       lblEstado_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEstado_1_2.setBounds(319, 137, 47, 14);
			                       panelDadosVendedor2.add(lblEstado_1_2);
			                       
			                       lblBairro2_2 = new JLabel("Bairro:");
			                       lblBairro2_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblBairro2_2.setBounds(319, 112, 83, 14);
			                       panelDadosVendedor2.add(lblBairro2_2);
			                       
			                       lblIe_2 = new JLabel("IE:");
			                       lblIe_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblIe_2.setBounds(336, 87, 27, 14);
			                       panelDadosVendedor2.add(lblIe_2);
			                       
			                       lblIEVendedor2 = new JLabel("");
			                       lblIEVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblIEVendedor2.setBounds(371, 85, 194, 16);
			                       panelDadosVendedor2.add(lblIEVendedor2);
			                       
			                       lblBairroVendedor2 = new JLabel("");
			                       lblBairroVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblBairroVendedor2.setBounds(371, 112, 194, 16);
			                       panelDadosVendedor2.add(lblBairroVendedor2);
			                       
			                       lblEstadoVendedor2 = new JLabel("");
			                       lblEstadoVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEstadoVendedor2.setBounds(371, 137, 194, 16);
			                       panelDadosVendedor2.add(lblEstadoVendedor2);
			                       
			                       lblVendedor_2 = new JLabel("Nome:");
			                       lblVendedor_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblVendedor_2.setBounds(53, 58, 70, 14);
			                       panelDadosVendedor2.add(lblVendedor_2);
			                       
			                       lblCpfcnpj_2 = new JLabel("CPF/CNPJ:");
			                       lblCpfcnpj_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblCpfcnpj_2.setBounds(30, 87, 83, 14);
			                       panelDadosVendedor2.add(lblCpfcnpj_2);
			                       
			                       lblEndereo_3 = new JLabel("Endereço:");
			                       lblEndereo_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEndereo_3.setBounds(30, 112, 83, 14);
			                       panelDadosVendedor2.add(lblEndereo_3);
			                       
			                       lblEndereo_1_2 = new JLabel("Municipio:");
			                       lblEndereo_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEndereo_1_2.setBounds(33, 139, 70, 14);
			                       panelDadosVendedor2.add(lblEndereo_1_2);
			                       
			                       lblMunicipioVendedor2 = new JLabel("");
			                       lblMunicipioVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblMunicipioVendedor2.setBounds(103, 139, 205, 16);
			                       panelDadosVendedor2.add(lblMunicipioVendedor2);
			                       
			                       lblEnderecoVendedor2 = new JLabel("");
			                       lblEnderecoVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEnderecoVendedor2.setBounds(103, 112, 205, 16);
			                       panelDadosVendedor2.add(lblEnderecoVendedor2);
			                       
			                       lblCpfCnpjVendedor2 = new JLabel("");
			                       lblCpfCnpjVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblCpfCnpjVendedor2.setBounds(102, 85, 206, 16);
			                       panelDadosVendedor2.add(lblCpfCnpjVendedor2);
			                       
			                       lblNomeVendedor2 = new JLabel("");
			                       lblNomeVendedor2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblNomeVendedor2.setBounds(120, 58, 445, 16);
			                       panelDadosVendedor2.add(lblNomeVendedor2);
			                       
			                       JPanel painelDadosCorretor = new JPanel();
			                       painelDadosCorretor.setLayout(null);
			                       painelDadosCorretor.setBorder(new CompoundBorder());
			                       painelDadosCorretor.setBackground(Color.WHITE);
			                       painelDadosCorretor.setBounds(76, 318, 589, 139);
			                       Border lineBorderCorretor = BorderFactory.createLineBorder(Color.black);
			                       TitledBorder titleCorretor = BorderFactory.createTitledBorder(lineBorder, "Corretor");
			                       painelDadosCorretor.setBorder(titleCorretor);
			                       painelDadosIniciais.add(painelDadosCorretor);
			                       
			                       JLabel lblComprador_1 = new JLabel("Nome:");
			                       lblComprador_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblComprador_1.setBounds(43, 36, 83, 14);
			                       painelDadosCorretor.add(lblComprador_1);
			                       
			                       JLabel lblCpfcnpj_3 = new JLabel("CPF/CNPJ:");
			                       lblCpfcnpj_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblCpfcnpj_3.setBounds(16, 62, 83, 14);
			                       painelDadosCorretor.add(lblCpfcnpj_3);
			                       
			                       JLabel lblEndereo_4 = new JLabel("Endereço:");
			                       lblEndereo_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEndereo_4.setBounds(26, 87, 83, 14);
			                       painelDadosCorretor.add(lblEndereo_4);
			                       
			                       JLabel lblEndereo_1_3 = new JLabel("Municipio:");
			                       lblEndereo_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEndereo_1_3.setBounds(29, 114, 70, 14);
			                       painelDadosCorretor.add(lblEndereo_1_3);
			                       
			                        lblMunicipioCorretor = new JLabel("");
			                       lblMunicipioCorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblMunicipioCorretor.setBounds(99, 114, 205, 16);
			                       painelDadosCorretor.add(lblMunicipioCorretor);
			                       
			                        lblEnderecoCorretor = new JLabel("");
			                       lblEnderecoCorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEnderecoCorretor.setBounds(99, 87, 205, 16);
			                       painelDadosCorretor.add(lblEnderecoCorretor);
			                       
			                        lblCpfCnpjCorretor = new JLabel("");
			                       lblCpfCnpjCorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblCpfCnpjCorretor.setBounds(98, 60, 206, 16);
			                       painelDadosCorretor.add(lblCpfCnpjCorretor);
			                       
			                       
			                        lblNomeCorretor = new JLabel("");
			                       lblNomeCorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblNomeCorretor.setBounds(113, 33, 448, 16);
			                       painelDadosCorretor.add(lblNomeCorretor);
			                       
			                        lblIECorretor = new JLabel("");
			                       lblIECorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblIECorretor.setBounds(367, 60, 194, 16);
			                       painelDadosCorretor.add(lblIECorretor);
			                       
			                       JLabel lblBairro2_3 = new JLabel("Bairro:");
			                       lblBairro2_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblBairro2_3.setBounds(315, 87, 83, 14);
			                       painelDadosCorretor.add(lblBairro2_3);
			                       
			                        lblBairroCorretor = new JLabel("");
			                       lblBairroCorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblBairroCorretor.setBounds(367, 87, 194, 16);
			                       painelDadosCorretor.add(lblBairroCorretor);
			                       
			                        lblEstadoCorretor = new JLabel("");
			                       lblEstadoCorretor.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEstadoCorretor.setBounds(367, 112, 194, 16);
			                       painelDadosCorretor.add(lblEstadoCorretor);
			                       
			                       JLabel lblEstado_1_3 = new JLabel("Estado:");
			                       lblEstado_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblEstado_1_3.setBounds(315, 112, 47, 14);
			                       painelDadosCorretor.add(lblEstado_1_3);
			                       
			                       JLabel lblIe_3 = new JLabel("IE:");
			                       lblIe_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			                       lblIe_3.setBounds(332, 62, 27, 14);
			                       painelDadosCorretor.add(lblIe_3);
			                       
			                    
			                       
			                       btnPesquisarVendedor1.addActionListener(new ActionListener() {
			                       	public void actionPerformed(ActionEvent arg0) {
			                       		TelaCliente clientes = new TelaCliente(0,2);
			                       		clientes.setTelaPai(isto);
			                       		clientes.setVisible(true);
			                       	}
			                       });
			                       btnPesquisarComprador.addActionListener(new ActionListener() {
			                       	public void actionPerformed(ActionEvent e) {
			                       		TelaCliente clientes = new TelaCliente(0,1);
			                       		clientes.setTelaPai(isto);
			                       		clientes.setVisible(true);
			                       	}
			                       });
			        
			        JLabel lblComissao = new JLabel("Comissão:");
			        lblComissao.setFont(new Font("Arial Black", Font.PLAIN, 14));
			        lblComissao.setBounds(664, 389, 96, 42);
			        painelDadosProdutos.add(lblComissao);
			        
			         rBComissaoSim = new JRadioButton("Sim");
			         rBComissaoSim.addActionListener(new ActionListener() {
			         	public void actionPerformed(ActionEvent arg0) {
			         		setComissao();
			         		
			         		
			         	}
			         });
			         rBComissaoSim.setBounds(771, 402, 58, 23);
			         painelDadosProdutos.add(rBComissaoSim);
			         
			          rBComissaoNao = new JRadioButton("Não");
			         
			          rBComissaoNao.addActionListener(new ActionListener() {
			          	public void actionPerformed(ActionEvent e) {
			          		
			          		
			          	      retirarComissao();
			          	}
			          });
			          rBComissaoNao.setSelected(true);
			          rBComissaoNao.setBounds(846, 402, 58, 23);
			          painelDadosProdutos.add(rBComissaoNao);
			          
			          entComissao = new JTextField();
			          entComissao.addKeyListener(new KeyAdapter() {
			          	@Override
			          	public void keyTyped(KeyEvent p) {
			          		
			          		String caracteres=".0987654321\b";// lista de caracters que não devem ser aceitos
							String Spreco, quantidade;
							Spreco = entComissao.getText();

							if(!caracteres.contains(p.getKeyChar()+"")){
							p.consume();//aciona esse propriedade para eliminar a ação do evento
							
							//quantidade = entQuantidade.getText();
							}
							else
							{
								Spreco = entComissao.getText() + p.getKeyChar();
								Spreco = Spreco.replaceAll("[^0-9.]", "");
							}
							
							quantidade = entQuantidade.getText();

							BigDecimal comissao = null;
	                		BigDecimal valor = null; 

	                		System.out.println("comisao e " + Spreco);
	                		
	                		System.out.println("quantidade e " + quantidade  );

							//if(quantidade != null && !(quantidade.length() <= 0) && !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) && !Spreco.equals(""))
							{
								try {
									comissao = new BigDecimal(Spreco);

		                		
		                		BigDecimal quant = new BigDecimal(quantidade);

		                		
		                		

		                		 valor = comissao.multiply(quant);
		                		 
		                		
		                			
		                		String comissao_total = valor.toPlainString();
		                		lblValorTotalComisao1.setText("R$ " + comissao_total);
		                		mostrar_valor_total_comissao.setText("R$ " + comissao_total);
		                		valor_total_comissao = valor;
	                            valor_atual_comissao = valor_total;
		                		

								}
								catch(NumberFormatException q)
								{
									System.out.println("comissao e nulo " + q.getCause());
								
			                		BigDecimal quant = new BigDecimal("1.0");
			                		try { valor = comissao.multiply(quant);
			                			String valorTotal = valor.toPlainString();
			                			lblValorTotalComisao1.setText("R$ " + valorTotal);
			                			mostrar_valor_total_comissao.setText("R$ " + valorTotal);
			                      		valor_total_comissao = valor;
			                      		valor_atual = valor_total;
			                      		//valor_atual = valor_total.subtract(valor_atual);


			                		}
			                		catch(NullPointerException r)
			                		{
										System.out.println("quant e nulo " + r.getCause());
										lblValorTotalComisao1.setText("");
										mostrar_valor_total_comissao.setText("");

			                      		

			                		}
								
								}
							}
						     
							
			          		
			          		
			          	}
			          });
			          entComissao.setColumns(10);
			          entComissao.setBounds(754, 442, 174, 33);
			          painelDadosProdutos.add(entComissao);
			          
			          JLabel lblValorTotal_1 = new JLabel("Valor Total:");
			          lblValorTotal_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
			          lblValorTotal_1.setBounds(663, 483, 96, 42);
			          painelDadosProdutos.add(lblValorTotal_1);
			          
			          lblValorTotalComisao1 = new JLabel("");
			          lblValorTotalComisao1.setFont(new Font("Tahoma", Font.BOLD, 14));
			          lblValorTotalComisao1.setEnabled(false);
			          lblValorTotalComisao1.setBounds(638, 349, 174, 28);
			          painelDadosProdutos.add(lblValorTotalComisao1);
			          
		
			          entComissao.setEditable(false);
			          entComissao.setEnabled(false);
			          
			          JLabel lblValorTotal_1_1 = new JLabel("VAlor Comissão:");
			          lblValorTotal_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
			          lblValorTotal_1_1.setBounds(620, 433, 140, 42);
			          painelDadosProdutos.add(lblValorTotal_1_1);
				    painelEmpresa.setBackground(new Color(255, 255, 255));
				    painelPrincipal.addTab("Do Pagamento", painelEmpresa);
				    painelEmpresa.setLayout(null);
				    
				    
				    cBSafraPersonalizado = new ComboBoxRenderPersonalizado();
				    cBSafra = new JComboBox();
				    cBSafra.setModel(modelSafra);
				    cBSafra.setRenderer(cBSafraPersonalizado);
		    		cBSafra.setBounds(586, 224, 174, 33);
		    		painelDadosProdutos.add(cBSafra);
		    		
		    		cBSafra.addActionListener(new ActionListener() {

		    			@Override
		    			public void actionPerformed(ActionEvent e) {
		                    CadastroSafra produto= (CadastroSafra) modelSafra.getSelectedItem();
		                    lblCodigoContrato.setText(Integer.toString(produto.getCodigo()));
		      				
		    			}
		    			
		    		});
		    		
		    		 chBoxClausulaComissao = new JCheckBox("Criar clausula contratual para comissão");
		    		 chBoxClausulaComissao.addActionListener(new ActionListener() {
		    		 	public void actionPerformed(ActionEvent e) {
		    		 		
		    		 		if(!chBoxClausulaComissao.isSelected())
		    		 		{
		    		 			chBoxClausulaComissao.setSelected(false);
		    		 			try {
				    		 		entClausula3.setText("");
				    		 		chBoxClausula3.setSelected(false);
				    		 		novo_contrato.setClausula_comissao(0);
			    		 			}
			    		 			catch(Exception re)
			    		 			{
			    		 				
			    		 			}
		    		 		}
		    		 		else
		    		 		{
		    		 			chBoxClausulaComissao.setSelected(true);
		    		 			try {
		    		 				setClausulaComissao(entComissao.getText().toString().replace(".", ","));
			    		 		chBoxClausula3.setSelected(true);
			    		 		novo_contrato.setClausula_comissao(1);

		    		 			}
		    		 			catch(Exception re)
		    		 			{
		    		 				
		    		 			}

		    		 		}
		    		 		
		    		 	}
		    		 });
		    		chBoxClausulaComissao.setBounds(937, 449, 242, 18);
		    		painelDadosProdutos.add(chBoxClausulaComissao);
		    		
				   
				    
				    
				    pesquisarSafras();
				   
				    for(CadastroSafra safra : safras)
				    {
				    
				     
				    	//cBSafra.addItem(safra.getProduto().getNome_produto() + " " + safra.getAno_plantio() + "/" + safra.getAno_colheita());
				        //cBSafra.addItem(safra);
				    	modelSafra.addSafra(safra);

				    }
				    
				    
				    JLabel lblOutro = new JLabel("Valor Total:");
				    lblOutro.setFont(new Font("Arial Black", Font.PLAIN, 14));
				    lblOutro.setBounds(50, 32, 89, 42);
				    painelEmpresa.add(lblOutro);
				    
				    mostrar_valor_total_contrato = new JLabel("");
				    mostrar_valor_total_contrato.setFont(new Font("Tahoma", Font.BOLD, 16));
				    mostrar_valor_total_contrato.setBounds(142, 32, 110, 42);
				    painelEmpresa.add(mostrar_valor_total_contrato);

				    
				    painel_table_cb = new JPanel();
					 painel_table_cb.setBounds(102, 94, 808, 151);
					 
					 table_cb = new JTable(modelo_cb);
					 table_cb.setBackground(new Color(255, 255, 255));
					 
					 modelo_cb.addColumn("Id Pagamento");
					 modelo_cb.addColumn("Id Conta Bancaria");

					    modelo_cb.addColumn("CPF");
					    modelo_cb.addColumn("Nome");

					    modelo_cb.addColumn("Banco");


					    modelo_cb.addColumn("Codigo");
						modelo_cb.addColumn("Agência");
                       
						modelo_cb.addColumn("Conta");
						modelo_cb.addColumn("Valor");
						modelo_cb.addColumn("Data Pagamento");

				      
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
				        table_cb.getColumnModel().getColumn(8)
				        .setPreferredWidth(100);
				     
				    
				        painel_table_cb.setLayout(null);
				    	modelo_cb.setNumRows(0);
				        JScrollPane scrollPaneCB = new JScrollPane(table_cb);
				        scrollPaneCB.setBounds(10, 5, 788, 127);
				        scrollPaneCB.setAutoscrolls(true);
				        scrollPaneCB.setBackground(new Color(255, 255, 255));
				        painel_table_cb.add(scrollPaneCB);  
				    
				        painelEmpresa.add(painel_table_cb);
				        
				        JLabel lblContaBancaria = new JLabel("Favorecido:");
				        lblContaBancaria.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblContaBancaria.setBounds(129, 339, 96, 42);
				        painelEmpresa.add(lblContaBancaria);
				        
				         cBContaBancaria = new JComboBox();
				        cBContaBancaria.setBounds(226, 348, 157, 28);
				        painelEmpresa.add(cBContaBancaria);
				        
				        JButton btnPesquisarCB = new JButton("Pesquisar");
				        btnPesquisarCB.addActionListener(new ActionListener() {
				        	public void actionPerformed(ActionEvent arg0) {
				        		TelaContaBancaria tela = new TelaContaBancaria(isto);
				        	}
				        });
				        btnPesquisarCB.setBounds(393, 351, 89, 23);
				        painelEmpresa.add(btnPesquisarCB);
				        
				        JLabel lblDataPagamento = new JLabel("Data Pagamento:");
				        lblDataPagamento.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblDataPagamento.setBounds(102, 455, 135, 42);
				        painelEmpresa.add(lblDataPagamento);
				        
				        entDataPagamento = new JTextField();
				        entDataPagamento.addKeyListener(new KeyAdapter() {
				        	@Override
				        	public void keyTyped(KeyEvent arg) {
				        		String caracteres="0987654321\b";// lista de caracters que não devem ser aceitos
								String texto = entDataPagamento.getText();
								if(!caracteres.contains(arg.getKeyChar()+"")){
									arg.consume();//aciona esse propriedade para eliminar a ação do evento
								}else {
								if(texto.length()==2 && arg.getKeyChar() != '\b'){
									entDataPagamento.setText(entDataPagamento.getText().concat("/"));
								}
								if(texto.length()==5  && arg.getKeyChar() != '\b'){
									entDataPagamento.setText(entDataPagamento.getText().concat("/"));
								}
								
								if(entDataPagamento.getText().length()>=10){
									//if para saber se precisa verificar também o tamanho da string do campo
									// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
									arg.consume();
									entDataPagamento.setText(entDataPagamento.getText().substring(0,10));
								}
							
							}
				        	}
				        });
				        entDataPagamento.setBounds(239, 465, 157, 32);
				        painelEmpresa.add(entDataPagamento);
				        entDataPagamento.setColumns(10);
				        
				        JLabel lblValorPgamento = new JLabel("Valor:");
				        lblValorPgamento.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblValorPgamento.setBounds(184, 501, 53, 42);
				        painelEmpresa.add(lblValorPgamento);
				        
				        entValorPagamento = new JTextField();
				        entValorPagamento.addKeyListener(new KeyAdapter() {
				        	@Override
				        	public void keyTyped(KeyEvent a) {
				        		
				        		String caracteres=".0987654321\b";// lista de caracters que não devem ser aceitos
								String Svalor_pagamento = "";
								BigDecimal valor_pagamento = null;
								BigDecimal valor_restante = null;

								if(!caracteres.contains(a.getKeyChar()+"")){
								a.consume();//aciona esse propriedade para eliminar a ação do evento
								
								//quantidade = entQuantidade.getText();
								}
								else
								{
									Svalor_pagamento = entValorPagamento.getText() + a.getKeyChar();
									Svalor_pagamento = Svalor_pagamento.replaceAll("[^0-9.]", "");
								}
								


		                		System.out.println("Valor pagamento e :" + Svalor_pagamento);
		                		
		                		System.out.println("valor atual e :" + valor_total.subtract(valor_acumulado).toPlainString()  );

								//if(quantidade != null && !(quantidade.length() <= 0) && !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) && !Spreco.equals(""))
								{
									try {
										valor_pagamento = new BigDecimal(Svalor_pagamento);

			                		

			                		
			                		

			                		 valor_restante = valor_total.subtract(valor_acumulado).subtract(valor_pagamento);
			                		
			                			
			                		String Svalor_restante = valor_restante.toPlainString();
			                		//lblValorRestante.setText(Svalor_restante);
			                		lblValorRestante.setText("R$ " + Svalor_restante);

									}
									catch(NumberFormatException b)
									{
										System.out.println("erro ao subtratir");
				                		lblValorRestante.setText("");

										
									}
								}
							     
								
				        		
				        		
				        	}
				        });
				        entValorPagamento.setColumns(10);
				        entValorPagamento.setBounds(239, 508, 157, 32);
				        painelEmpresa.add(entValorPagamento);
				        
				        JButton btnAdicionarPag = new JButton("Adicionar");
				        btnAdicionarPag.addActionListener(new ActionListener() {
				        	public void actionPerformed(ActionEvent e) {
				        		
 	                          	String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, data_pagamento;
 	                          	
 	                          	valor_pagamento = entValorPagamento.getText().toString();
 	                          	
 	                          	//processa dados de data de pagamento
 	                          	
 	                          	if(rdbtnAVista.isSelected())
 	                          	{
 	 	                          	data_pagamento = "A Vista";

 	                          	}
 	                          	else if(rdbtnSobreRodas.isSelected())
 	                          	{
 	 	                          	data_pagamento = "Sobre Rodas";

 	                          	}
 	                          	else if(rdbtnAtoCarregamento.isSelected())
 	                          	{
 	 	                          	data_pagamento = "Ato Carregamento";

 	                          	}
 	                          	else
 	                          	{
 	                          		data_pagamento = entDataPagamento.getText();
 	                          	}
 	                          	
 	                          	
 	                          	if(rdbtnInfoFavorNao.isSelected())
 	                          	{
 	                          		conta_atual = new ContaBancaria();
 	                          		//nao foi informado conta bancaria ppara esse pagamento
 	                          		id = "00";
 							 		cpf = "Há Informar";
 							 		banco = "Há Informar";
 							 		nome = "Há Informar";
 							 		codigo = "Há Informar";
 							 		agencia = "Há Informar";
 							 		conta = "Há Informar";
 							 		
 							 	    
 						       	  	Locale ptBr = new Locale("pt", "BR");
 						       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(valor_pagamento));
 						       	   	System.out.println(valorString);   
 						       		

 									modelo_cb.addRow(new Object[]{"00", id, cpf, nome, banco, 
 										       codigo, agencia, conta, valorString, data_pagamento});
 									
 									conta_atual.setAgencia(agencia);
 									conta_atual.setId_conta(00);
 									conta_atual.setCpf_titular(cpf);
 									conta_atual.setBanco(banco);
 									conta_atual.setConta(conta);
 									conta_atual.setCodigo(codigo);
 									conta_atual.setNome(nome);
 									
 									
 	                          	}
 	                          	else
 	                          	{
						 		id = Integer.toString(conta_atual.getId_conta());
						 		cpf = conta_atual.getCpf_titular();
						 		banco = conta_atual.getBanco();
						 		nome = conta_atual.getNome();
						 		codigo = conta_atual.getCodigo();
						 		agencia = conta_atual.getAgencia();
						 		conta = conta_atual.getConta();

						 		Locale ptBr = new Locale("pt", "BR");
					       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(valor_pagamento));
					       	   	System.out.println(valorString);
					       	   	
								modelo_cb.addRow(new Object[]{"00", id,  cpf, nome, banco, 
									       codigo, agencia, conta, valorString, data_pagamento});
								
 	                          	}
								
								valor_acumulado = valor_acumulado.add(new BigDecimal(valor_pagamento));
								System.out.println("Valor acumulado e: " + valor_acumulado.toPlainString());
								
								
								if(valor_acumulado.compareTo(valor_total) == 0)
								{
									//Font  font new Font("")
									mostrar_soma_atual_pagamentos.setEnabled(true);
									mostrar_soma_atual_pagamentos.setForeground(Color.green);
 

								}
								else {
									mostrar_soma_atual_pagamentos.setEnabled(true);
									mostrar_soma_atual_pagamentos.setForeground(Color.orange);
								}
								
							  	Locale ptBr = new Locale("pt", "BR");
						       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(valor_acumulado.toPlainString()));
						       	   	System.out.println(valorString);  
								mostrar_soma_atual_pagamentos.setText(valorString);

								//valor_atual = valor_total.subtract(valor_acumulado);
								
								cBContaBancaria.removeAllItems();
								entDataPagamento.setText("");
								entValorPagamento.setText("");
								//lblValorRestante.setText("");
								
								CadastroContrato.CadastroPagamento pagamento_atual = new CadastroContrato.CadastroPagamento();

								
							
							    pagamento_atual.setConta(conta_atual);
							   
 	                          
								
								 pagamento_atual.setData_pagamento(data_pagamento);
								 pagamento_atual.setValor(new BigDecimal(valor_pagamento));
								 if(pagamentos == null) {
									 pagamentos = new ArrayList<>();
								     pagamentos.add(pagamento_atual);

								 }else {
								     pagamentos.add(pagamento_atual);

								 }
								
				        		
				        	}
				        });
				        btnAdicionarPag.setBounds(406, 554, 89, 23);
				        painelEmpresa.add(btnAdicionarPag);
				        
				        JButton btnExcluirPag = new JButton("Excluir");
				        btnExcluirPag.addActionListener(new ActionListener() {
				        	public void actionPerformed(ActionEvent arg0) {
				        		int indiceDaLinha = table_cb.getSelectedRow();
								
				        	    
			   
				           		String get_valor_linha = table_cb.getValueAt(indiceDaLinha, 8).toString();
								String Svalor_pagamento = get_valor_linha.replaceAll("R", "").replaceAll(" ", "").replace("$", "").replace(".", "").replace(",", ".");
								
								System.out.println("Svalor_pagamento: " + Svalor_pagamento);
								BigDecimal valor_pagamento = new BigDecimal(Float.parseFloat(Svalor_pagamento));
								
								int id_conta = Integer.parseInt(table_cb.getValueAt(indiceDaLinha, 1).toString());
								
								valor_acumulado = valor_acumulado.subtract(valor_pagamento);
								 //valor_total.subtract(valor_acumulado);
								
								 
								 
								
								if(table_cb.getValueAt(indiceDaLinha, 0).toString() == "00") {
									//esta removendo um pagamento que ainda nao foi cadastrado
									
									
									pagamentos.remove(indiceDaLinha);
									
									
									

								}else {
									//colocar no array a conta para excluir
									if(pagamento_a_excluir == null) {
										pagamento_a_excluir = new ArrayList<>();
									}
									pagamento_a_excluir.add(Integer.parseInt(table_cb.getValueAt(indiceDaLinha, 0).toString()));
									
									//pega o id do pagamento
									int id = Integer.parseInt(table_cb.getValueAt(indiceDaLinha, 0).toString() );
									CadastroContrato.CadastroPagamento pagamento_a_remover = null;
									for(CadastroContrato.CadastroPagamento pagamento : pagamentos)
									{
										if(pagamento.getId() == id) {
											pagamento_a_remover = pagamento;
											break;
										}
									}
									pagamentos.remove(pagamento_a_remover);
									//((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha);

									
									
									
								}

				        		mostrar_soma_atual_pagamentos.setText("R$ " + valor_acumulado.toPlainString());
				        		lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());

				        		
				        		if(valor_acumulado.compareTo(valor_total) == 0)
								{
									//Font  font new Font("")
									mostrar_soma_atual_pagamentos.setEnabled(true);
									mostrar_soma_atual_pagamentos.setForeground(Color.green);
 

								}
								else {
									mostrar_soma_atual_pagamentos.setEnabled(true);
									mostrar_soma_atual_pagamentos.setForeground(Color.orange);
								}
								
				        		
				        		((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha); table_cb.repaint(); table_cb.validate(); 
                                
								
				        		
				        	}
				        });
				        btnExcluirPag.setBounds(821, 317, 89, 23);
				        painelEmpresa.add(btnExcluirPag);
				        
				        JLabel lblValor3 = new JLabel("Valor Restante:");
				        lblValor3.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblValor3.setBounds(102, 542, 135, 42);
				        painelEmpresa.add(lblValor3);
				        
				        lblValorRestante = new JLabel("");
				        lblValorRestante.setFont(new Font("Tahoma", Font.BOLD, 18));
				        lblValorRestante.setEnabled(false);
				        lblValorRestante.setBounds(239, 470, 165, 28);
				        painelEmpresa.add(lblValorRestante);
				        
				        JLabel lblValoresAcumulados = new JLabel("Valor Pagamentos:");
				        lblValoresAcumulados.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblValoresAcumulados.setBounds(561, 256, 157, 42);
				        painelEmpresa.add(lblValoresAcumulados);
				        
				         mostrar_soma_atual_pagamentos = new JLabel("");
				         mostrar_soma_atual_pagamentos.setEnabled(false);
				         mostrar_soma_atual_pagamentos.setForeground(Color.BLACK);
				        mostrar_soma_atual_pagamentos.setFont(new Font("Tahoma", Font.BOLD, 14));
				        mostrar_soma_atual_pagamentos.setBounds(745, 256, 165, 42);
				        painelEmpresa.add(mostrar_soma_atual_pagamentos);
				        
				        JLabel lblComisso = new JLabel("Comissão:");
				        lblComisso.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblComisso.setBounds(329, 32, 89, 42);
				        painelEmpresa.add(lblComisso);
				        
				        mostrar_valor_total_comissao = new JLabel("");
				        mostrar_valor_total_comissao.setFont(new Font("Tahoma", Font.BOLD, 16));
				        mostrar_valor_total_comissao.setBounds(428, 32, 110, 42);
				        painelEmpresa.add(mostrar_valor_total_comissao);
				        
				        JLabel lblFormaPagamento = new JLabel("Forma Pagamento:");
				        lblFormaPagamento.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblFormaPagamento.setBounds(76, 392, 165, 42);
				        painelEmpresa.add(lblFormaPagamento);
				        
				         rdbtnAgendado = new JRadioButton("Agendado");
				         rdbtnAgendado.addActionListener(new ActionListener() {
				         	public void actionPerformed(ActionEvent e) {
				         		
				        		rdbtnAgendado.setSelected(true);

				        		entDataPagamento.setEditable(true);
				        		entDataPagamento.setEnabled(true);

                                rdbtnSobreRodas.setSelected(false);

				        		
				        		rdbtnAtoCarregamento.setSelected(false);

				        		rdbtnAVista.setSelected(false);
				        		
				        		
				         	}
				         });
				        rdbtnAgendado.setSelected(true);
				        rdbtnAgendado.setBounds(239, 392, 83, 23);
				        painelEmpresa.add(rdbtnAgendado);
				        
				        //JRadioButton rdbtnSobreRodas, rdbtnAgendado, rdbtnAtoCarregamento, rdbtnAVista;
				         rdbtnSobreRodas = new JRadioButton("Sobre Rodas");
				        rdbtnSobreRodas.addActionListener(new ActionListener() {
				        	public void actionPerformed(ActionEvent arg0) {
				        		
                                rdbtnSobreRodas.setSelected(true);

				        		
				        		rdbtnAtoCarregamento.setSelected(false);

				        		rdbtnAVista.setSelected(false);
				        		rdbtnAgendado.setSelected(false);
				        		
				        		entDataPagamento.setEditable(false);
				        		entDataPagamento.setEnabled(false);

				        		
				        	}
				        });
				        rdbtnSobreRodas.setBounds(329, 394, 96, 23);
				        painelEmpresa.add(rdbtnSobreRodas);
				        
				        rdbtnAtoCarregamento  = new JRadioButton("Ato Carregamento");
				        rdbtnAtoCarregamento.addActionListener(new ActionListener() {
				        	public void actionPerformed(ActionEvent e) {
				        		
				        		rdbtnAtoCarregamento.setSelected(true);

                                 rdbtnSobreRodas.setSelected(false);
				        		rdbtnAVista.setSelected(false);
				        		rdbtnAgendado.setSelected(false);
				        		
				        		entDataPagamento.setEditable(false);
				        		entDataPagamento.setEnabled(false);

				        	}
				        });
				        rdbtnAtoCarregamento.setBounds(333, 425, 143, 23);
				        painelEmpresa.add(rdbtnAtoCarregamento);
				        
				         rdbtnAVista = new JRadioButton("A Vista");
				         rdbtnAVista.addActionListener(new ActionListener() {
				         	public void actionPerformed(ActionEvent e) {
				         		
				        		rdbtnAVista.setSelected(true);

				        		
				        		rdbtnAtoCarregamento.setSelected(false);

                                 rdbtnSobreRodas.setSelected(false);
				        		rdbtnAgendado.setSelected(false);
				        		
				        		entDataPagamento.setEditable(false);
				        		entDataPagamento.setEnabled(false);
				         	}
				         });
				        rdbtnAVista.setBounds(239, 425, 89, 23);
				        painelEmpresa.add(rdbtnAVista);
				        
				         rdbtnInfoFavorSim = new JRadioButton("Sim");
				         rdbtnInfoFavorSim.setSelected(true);
				         rdbtnInfoFavorSim.addActionListener(new ActionListener() {
				         	public void actionPerformed(ActionEvent arg0) {
				         		rdbtnInfoFavorSim.setSelected(true);
				         		rdbtnInfoFavorNao.setSelected(false);

				         		cBContaBancaria.setEditable(true);
				         		cBContaBancaria.setEnabled(true);
				         		btnPesquisarCB.setEnabled(true);
				         		
				         		
				         	}
				         });
				        rdbtnInfoFavorSim.setBounds(237, 307, 53, 23);
				        painelEmpresa.add(rdbtnInfoFavorSim);
				        
				        JLabel lblInformarFavorecido = new JLabel("Informar Favorecido:");
				        lblInformarFavorecido.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblInformarFavorecido.setBounds(68, 295, 157, 42);
				        painelEmpresa.add(lblInformarFavorecido);
				        
				         rdbtnInfoFavorNao = new JRadioButton("Não");
				         rdbtnInfoFavorNao.addActionListener(new ActionListener() {
				         	public void actionPerformed(ActionEvent e) {
				         		rdbtnInfoFavorNao.setSelected(true);
				         		rdbtnInfoFavorSim.setSelected(false);
				         		cBContaBancaria.setEditable(false);
				         		cBContaBancaria.setEnabled(false);
				         		btnPesquisarCB.setEnabled(false);
				         	}
				         });
				        rdbtnInfoFavorNao.setBounds(314, 307, 53, 23);
				        painelEmpresa.add(rdbtnInfoFavorNao);
				        
				        chBoxClausulaComissao.setEnabled(false);
				        
				        //adiciona o paiel de salvar o contrato
				        painelPrincipal.addTab("Finalizar", painelFinalizar);
				        painelFinalizar.setLayout(null);
				        
				        JButton btnTeste = new JButton("Salvar");
				        btnTeste.addActionListener(new ActionListener() {
				        	public void actionPerformed(ActionEvent e) {
                                    

				        				TelaEmEspera esperar = new TelaEmEspera();
				        				
				        				
				        				new Thread() {
				        					
				        					@Override
				        					public void run() {
				        						esperar.setVisible(true);
				        					}
				        					
				        				}.start();
 
				        				new Thread() {
									        	
				        					@Override
				        					public void run() {
				        						
				        				    
				        				    	
				        				      	 
				                              String produto, medida, quantidade, preco, local_retirada, data_contrato, data_entrega ,codigo;
                                              esperar.setMsg("Reunindo Informações");

 				                             codigo = getCodigoContrato();
                                              
				                             if(codigo == null) {
				                            	 esperar.fechar();
				                             }
				                             {
				                             data_entrega = entDataEntrega.getText();
				                             novo_contrato.setData_entrega(data_entrega);
				                             
				                             //definicoes de subContrato
				                             
				                             if(tipoContrato == 0) {
				                            	 //contrato pai
					                             novo_contrato.setSub_contrato(0);

				                             }else if(tipoContrato == 1) {
				                            	 //sub contrato
					                             novo_contrato.setSub_contrato(1);

				                             }
				                             data_contrato = entDataContrato.getText();
				                             novo_contrato.setData_contrato(data_contrato);
				                             
				                             
				                             novo_contrato.setStatus_contrato(1);
				                             
				                             novo_contrato.setCodigo(codigo);
				                              if(rQuanKG.isSelected())
				                              	medida = "KG";
				                              else if(rQuanS.isSelected())
				                                  medida = "Sacos";
				                              else
				                              	medida = "TON";
				                              novo_contrato.setMedida(medida);
				                              
				                              CadastroSafra safra= (CadastroSafra) modelSafra.getSelectedItem();
				                              novo_contrato.setModelo_safra(safra);
				                              novo_contrato.setModelo_produto(safra.getProduto());
				                              
				                               novo_contrato.setSafra(safra.getAno_plantio() + "/" + safra.getAno_colheita());
				                              
				                               produto = safra.getProduto().getNome_produto();
				                               novo_contrato.setProduto(produto); 
				                               
				                               CadastroCliente localRetirada = (CadastroCliente) modelLocalRetirada.getSelectedItem();
				                               novo_contrato.setLocal_retirada(localRetirada.getNome_fantaia());
				                               novo_contrato.setCliente_retirada(localRetirada);
				                              
				                              
				                              novo_contrato.setQuantidade(Double.parseDouble(entQuantidade.getText()));
				                              novo_contrato.setValor_produto(Double.parseDouble(entPreco.getText()));
				                              novo_contrato.setValor_a_pagar(valor_total);
				                              novo_contrato.setValor_comissao(valor_total_comissao);
				                              
				                              //pagamentos
				                              novo_contrato.setPagamentos(pagamentos);
				                              
				                              //adicionais
				                              
				                             ArrayList<String> clausulas_locais = new ArrayList<>(); 
				                              
				                             clausulas_locais.add(entClausula1.getText().toString()); 
				                             clausulas_locais.add(entClausula2.getText().toString()); 

				                             
				                             if(chBoxClausulaComissao.isSelected()) {
					                             clausulas_locais.add(entClausula3.getText().toString()); 
				                             }
				                             if(chBoxClausula4.isSelected()) {
					                             clausulas_locais.add( entClausula4.getText().toString()); 

				                              }
				                             if(chBoxClausula5.isSelected()) {
					                             clausulas_locais.add( entClausula5.getText().toString()); 

				                              }
				                             if(chBoxClausula6.isSelected()) {
					                             clausulas_locais.add( entClausula6.getText().toString()); 

				                              }
				                            novo_contrato.setClausulas(clausulas_locais);
                                               esperar.setMsg("Elaborando Contrato");
                                               
                                              
                                         

				                              
				                             // editar.abrir();
                                               ByteArrayOutputStream contrato_alterado = null;
                                             try {  
                                            	 System.out.println("tipo do modelo selecionado: " + modelo.getNome_modelo());
                                            	 if(modelo.getNome_modelo().equals("informal"))	{	
       				        	        		  editar = new EditarExcel(modelo, esperar, tipoContrato);
    				                               contrato_alterado = editar.alterar(novo_contrato);
    					                            
    				                               //criar pdf

                                             	  ConverterPdf converter_pdf = new ConverterPdf();
   					                            //  String url = converter_pdf.excel_pdf_file(contrato_alterado);
   					                            //TelaVizualizarPdf  vizualizar =  new TelaVizualizarPdf(url);
   					                              ByteArrayOutputStream pdf_alterado = converter_pdf.excel_pdf_stream(contrato_alterado);
   					                              TelaVizualizarPdf  vizualizar =  new TelaVizualizarPdf(new ByteArrayInputStream (pdf_alterado.toByteArray()), isto, esperar);
   					                              
 				                             
                                            	 }
       				        				    else{
       				        				    	System.out.println("Preparando para elaborar novo contrato formal");
       				        					   editarWord = new EditarWord(modelo, esperar, tipoContrato,novo_contrato);
       				        					   contrato_alterado = editarWord.preparar();
       				        					   
                                              	  ConverterPdf converter_pdf = new ConverterPdf();
   					                              ByteArrayOutputStream pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);

       				        					
       				        				    }
  				                             
				                        
	                                        }catch(Exception e) {
	                                        	esperar.fechar();
	                                        	System.out.println("erro ao elaborar contrato, erro: " + e.getMessage());
	                                        	JOptionPane.showMessageDialog(null, "Erro fatal, consulte o administrador do sistema\nErro: " + e.getMessage());
	                                        	isto.dispose();
	                                        }
				        					}///fin do metodo run
				        					}//fim do senao para codigo
				        					
				        				}.start();
					        	        
                          
                          /* Jasper relatorio = new Jasper();
                           ArrayList<CadastroContrato> contratos = new ArrayList<>();
                           contratos.add(novo_contrato);
               			   try {
							relatorio.imprimir(contratos);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
                            
				        	}
				        });
				        btnTeste.setBounds(847, 561, 89, 23);
				        painelFinalizar.add(btnTeste);
				        
				        JLabel lblStatusInicial = new JLabel("Status Inicial: Assinar");
				        lblStatusInicial.setFont(new Font("Arial Black", Font.PLAIN, 14));
				        lblStatusInicial.setBounds(40, 35, 228, 42);
				        painelFinalizar.add(lblStatusInicial);
				          chBoxClausulaComissao.setVisible(false);


				          
				         if(tipoContrato == 1) {
				        	 //e um subcontrato
				        	 if(flag_edicao == 1) {
				        		 rotinasEdicao();
					        	 setClausulas( );
				        		 //modo de edicao de subcontrato
					        	 setInfoPai();

				        	 }else {
					        	 rotinasSubContrato();
					        	 setInfoPai();

				        	 }
				         }else {
				        	 //e um contrato original
				        	 if(flag_edicao == 1) {
				        		 //se esta no modo edicao
				        		 rotinasEdicao();
					        	 setClausulas( );
				        	 }else {
				        		 //se esta no modo criacao
				        	 }
				         }
				          
				        
		this.setLocationRelativeTo(null);
		this.setBounds (GraphicsEnvironment.getLocalGraphicsEnvironment (). getMaximumWindowBounds ());
		 isto = this;
		 
	

		this.setVisible(true);
		
	 }
	
	
	public void setComprador1(CadastroCliente comprador)
	{
		novo_contrato.adicionarComprador(0, comprador);
		lblCodigoContratoComprador.setText(Integer.toString(comprador.getId()));
		
		cBComprador.removeAllItems();
		
		if(comprador.getTipo_pessoa() == 0) //pessoa fisica
		{
			cBComprador.addItem(comprador.getNome() + " " + comprador.getSobrenome());
			cBComprador.setSelectedItem(comprador.getNome() + " " + comprador.getSobrenome());
			lblNomeComprador.setText(comprador.getNome() + " " + comprador.getSobrenome());
			cBComprador.addItem("Indefinido");

			lblCpfCnpj.setText(comprador.getCpf());
			System.out.println("O comprador selecionado e uma pessoa fisica");

		}
		else //pessoa juridica
		{
			System.out.println("O comprador selecionado e uma pessoa juridica");

			cBComprador.addItem(comprador.getRazao_social());
			cBComprador.setSelectedItem(comprador.getRazao_social());
			cBComprador.addItem("Indefinido");

			lblCpfCnpj.setText(comprador.getCnpj());
			lblNomeComprador.setText(comprador.getRazao_social());



		}
		
		lblIE.setText(comprador.getIe());
		lblEndereco.setText(comprador.getRua());
		lblMunicipio.setText(comprador.getCidade());
		lblEstado.setText(comprador.getUf());
		lblBairro.setText(comprador.getBairro());
	
	}
	
	public String pegarData()
	{
		  
        Date hoje = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(hoje);
	}
	
	public void setContaBancaria(ContaBancaria conta)
	{
		
		cBContaBancaria.removeAllItems();
		cBContaBancaria.addItem(conta.getNome());
		conta_atual = conta;
		
	
		
	}
	
	public void setVendedor1(CadastroCliente vendedor)
	{
		novo_contrato.adicionarVendedor(0, vendedor);
		lblCodigoContratoVendedor.setText(Integer.toString(vendedor.getId()));

		cBVendedor1.removeAllItems();

		
		if(vendedor.getTipo_pessoa() == 0) //pessoa fisica
		{
			cBVendedor1.addItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor1.setSelectedItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor1.addItem("Indefinido");


			lblNomeVendedor.setText(vendedor.getNome() + " " + vendedor.getSobrenome());

			lblCpfCnpjVendedor.setText(vendedor.getCpf());

		}
		else //pessoa juridica
		{
			cBVendedor1.addItem(vendedor.getRazao_social());
			cBVendedor1.setSelectedItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor1.addItem("Indefinido");


			lblCpfCnpjVendedor.setText(vendedor.getCnpj());
			lblNomeVendedor.setText(vendedor.getRazao_social());


		}

		lblIEVendedor.setText(vendedor.getIe());
		lblEnderecoVendedor.setText(vendedor.getRua());
		lblMunicipioVendedor.setText(vendedor.getCidade());
		lblEstadoVendedor.setText(vendedor.getUf());
		lblBairroVendedor.setText(vendedor.getBairro());
	

	}
	
	
	
	public void setCorretor(CadastroCliente corretor)
	{
		novo_contrato.adicionarCorretor(0, corretor);
		
		cBCorretor.removeAllItems();

		
		if(corretor.getTipo_pessoa() == 0) //pessoa fisica
		{
			cBCorretor.addItem(corretor.getNome() + " " + corretor.getSobrenome());
			cBCorretor.setSelectedItem(corretor.getNome() + " " + corretor.getSobrenome());
			cBCorretor.addItem("Indefinido");


			lblNomeCorretor.setText(corretor.getNome() + " " + corretor.getSobrenome());

			lblCpfCnpjCorretor.setText(corretor.getCpf());

		}
		else //pessoa juridica
		{
			cBCorretor.addItem(corretor.getRazao_social());
			cBCorretor.setSelectedItem(corretor.getNome() + " " + corretor.getSobrenome());
			cBCorretor.addItem("Indefinido");


			lblCpfCnpjCorretor.setText(corretor.getCnpj());
			lblNomeCorretor.setText(corretor.getRazao_social());


		}

		lblIECorretor.setText(corretor.getIe());
		lblEnderecoCorretor.setText(corretor.getRua());
		lblMunicipioCorretor.setText(corretor.getCidade());
		lblEstadoCorretor.setText(corretor.getUf());
		lblBairroCorretor.setText(corretor.getBairro());
	

	}
	
	
	public void setVendedor2(CadastroCliente vendedor)
	{
		novo_contrato.adicionarVendedor(1, vendedor);
			lblCodigoContratoVendedor.setText(Integer.toString(vendedor.getId()));

		cBVendedor2.removeAllItems();

		
		if(vendedor.getTipo_pessoa() == 0) //pessoa fisica
		{
			cBVendedor2.addItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor2.setSelectedItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor2.addItem("Indefinido");


			lblNomeVendedor2.setText(vendedor.getNome() + " " + vendedor.getSobrenome());

			lblCpfCnpjVendedor2.setText(vendedor.getCpf());

		}
		else //pessoa juridica
		{
			cBVendedor2.addItem(vendedor.getRazao_social());
			cBVendedor2.setSelectedItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor2.addItem("Indefinido");


			lblCpfCnpjVendedor2.setText(vendedor.getCnpj());
			lblNomeVendedor2.setText(vendedor.getRazao_social());


		}

		lblIEVendedor2.setText(vendedor.getIe());
		lblEnderecoVendedor2.setText(vendedor.getRua());
		lblMunicipioVendedor2.setText(vendedor.getCidade());
		lblEstadoVendedor2.setText(vendedor.getUf());
		lblBairroVendedor2.setText(vendedor.getBairro());
	

	}
	
	
	
	public void salvarArquivo()
	{
		
		int tipo_salvamento = -1;
		if(flag_edicao_global == 0) {
		    //modo de criacao
			tipo_salvamento = 0;
		}else {
			//mode de edicao
	    	 tipo_salvamento = 1;
		}
		int salvou = editar.salvar(tipo_salvamento);
		if( salvou == 1 || salvou == 10 || salvou == 12 || salvou == 14)
		{
			int result = -1;
			
			GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();
			if(novo_contrato.getSub_contrato() == 0) {
				//e um contrato pai
				if(flag_edicao_global == 0) {
					//inserindo novo contrato
					//criar tarefa se inserção de contrato
					ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = new ArrayList<>();
					
					lista_tarefas.add(criarTarefa(1));
					novo_contrato.setLista_tarefas(lista_tarefas);
					result = gerenciarContratos.inserirContrato(novo_contrato, null);

				}else {
					//atualizando contrato existente
					result = gerenciarContratos.atualizarContrato(novo_contrato, pagamento_a_excluir);

				}

			}else {
				//e um sub contrato
				if(flag_edicao_global == 0) {
					//criar tarefa se inserção de contrato
					ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = new ArrayList<>();
					
					lista_tarefas.add(criarTarefa(2));
					novo_contrato.setLista_tarefas(lista_tarefas);
					result = gerenciarContratos.inserirContrato(novo_contrato, contrato_pai_local);

					//inserir novo subcontrato
				}else {
					result = gerenciarContratos.atualizarContrato(novo_contrato, pagamento_a_excluir);

					//atualizar novo contrato
				}

			}
			System.out.println("Result: " + result);
			if(result == 1) {
				JOptionPane.showMessageDialog(null, "Contrato criado e salvo na base de dados");
				//salvar arquivo fisico
				//aumentar mais 1 na sequencia
			 	ArquivoConfiguracoes arquivo = new ArquivoConfiguracoes();
				arquivo.setCodidoSequencial(configs_globais.getCodigoSequencial() + 1);
				arquivo.salvarNovasConfiguragoes();
				
	
				
				isto.dispose();
			}else if (result == 0){
				JOptionPane.showMessageDialog(null, "Contrato não pode ser criado, mas não houve falhas no banco de dados!\nConsulte o administrador");
		        //apagar o diretorio criado
				apagarDiretorio();
				
				isto.dispose();
			}else if(result == -1) {
				JOptionPane.showMessageDialog(null, "Contrato não pode ser criado\nHouve falhas no banco de dados!\nConsulte o administrador");
				apagarDiretorio();

				isto.dispose();
			}else if(result == -2) {
				JOptionPane.showMessageDialog(null, "Contrato não pode ser atualizado\nHouve falhas no banco de dados!\nConsulte o administrador");
		        isto.dispose();
			}
			else if(result == 5) {
				JOptionPane.showMessageDialog(null, "Contrato atualizado e salvo na base de dados");
				DadosGlobais dados = DadosGlobais.getInstance();
				// dados.getTeraGerenciarContratoPai().atualizarContratoLocal();
		        isto.dispose();
			}
		}else {
			JOptionPane.showMessageDialog(null, "Houve um erro é o contrato não pode ser salvo fisicamente\nOperação cancelada!");
			isto.dispose();

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
	
	public String getCodigoContrato() {
		String cod_safra = lblCodigoContrato.getText();
		String cod_comprador = lblCodigoContratoComprador.getText();
		String cod_vendedor = lblCodigoContratoVendedor.getText();
		String cod_sequencial = lblCodigoContratoAleatorio.getText();
		String retorno = null;
		
		if(cod_safra.equals("") || cod_safra.equals(" ") || cod_safra == null || cod_safra.length() <= 3){
			JOptionPane.showMessageDialog(null, "Safra não selecionada!");
			
		}else {
			if(cod_comprador.equals("") || cod_comprador.equals(" ") || cod_comprador == null ){
				JOptionPane.showMessageDialog(null, "Comprador não selecionada!");
				
			}else {
				if(cod_vendedor.equals("") || cod_vendedor.equals(" ") || cod_vendedor == null ){
					JOptionPane.showMessageDialog(null, "Vendedor não selecionada!");
					
				}else {
					retorno = cod_safra + "." + cod_comprador + "." + cod_vendedor + "." + cod_sequencial;
				}
			}
		}
		
		return retorno;
		
	}
	
	public void retirarComissao() {
		rBComissaoSim.setSelected(false);
  		rBComissaoNao.setSelected(true);
  		
  		entComissao.setEnabled(false);
  		entComissao.setEditable(false);
  		entComissao.setText("");
  		
  		mostrar_valor_total_comissao.setText("");
  		lblValorTotalComisao1.setText("");
  		
  		valor_total_comissao = new BigDecimal("0");	
  		chBoxClausulaComissao.setVisible(false);
  		chBoxClausulaComissao.setEnabled(false);
    	chBoxClausula3.setSelected(false);
    	entClausula3.setText("");
    	chBoxClausulaComissao.setSelected(false);
  		novo_contrato.setComissao(0);
  		
	}

	public void setComissao() {
		rBComissaoNao.setSelected(false);
 		rBComissaoSim.setSelected(true);
 		
 		
 		entComissao.setEnabled(true);

 		entComissao.setEditable(true);
 		chBoxClausulaComissao.setVisible(true);
 		chBoxClausulaComissao.setEnabled(true);
 		novo_contrato.setComissao(1);
 		
	}
	
	
	public void setClausula1(String unidade) {
		entClausula1.setText("A quantidade de " + unidade + " que exceder será negociado com o preço do dia.");
		
	}
	public void setClausulaComissao(String valor) {
		if(chBoxClausulaComissao.isSelected())
 		entClausula3.setText("Comissão de R$ " +  valor + " por " +  unidadeGlobal + " pagas ao corretor");

	}
	
	public void setClausula2(String nome_produto) {
		entClausula2.setText(nome_produto +" acima de 14% de umidade será cobrado uma taxa de despesas para a secagem");

	}
	
	public String quebrarCodigo() {
		String texto = 	contrato_pai_local.getCodigo().replaceAll("[^0-9]+", ";");

		System.out.println("codigo para edicao: " + texto);
       String [] textoSeparado = texto.split(";");
      
       String safra = textoSeparado[0];
       
       String comprador = textoSeparado[1];
       String vendedor = textoSeparado[2];
       String sequenciaAleatoria = textoSeparado[3];
       return sequenciaAleatoria ;

	}
	
	public void rotinasEdicao() {
		
		CadastroCliente corretor[] = contrato_pai_local.getCorretores();
		CadastroCliente compradores[] = contrato_pai_local.getCompradores();
		CadastroCliente vendedores[] = contrato_pai_local.getVendedores();

		
		//lblCodigoSubContrato
		
		
		// corretor, compradores e vendedores
	
		if(corretor[0] != null) {
			this.setCorretor(corretor[0]);
		}
		
		if(compradores[0] != null) {
			this.setComprador1(compradores[0]);
		}
		
		if(vendedores[0] != null) {
			this.setVendedor1(vendedores[0]);
		}
		
		if(vendedores[1] != null) {
			this.setVendedor2(vendedores[1]);
		}
		
	 
		
		//data de entrega
		System.out.println("data de entrega contrato pai:" + contrato_pai_local.getData_entrega());
		entDataEntrega.setText(contrato_pai_local.getData_entrega());
		
		//unidade de medida
		if(contrato_pai_local.getMedida().toUpperCase().equals("SACOS")) {
			rQuanKG.setSelected(false);
			rQuanS.setSelected(true);
			rQuanT.setSelected(false);
    		unidadeGlobal = "sacos";
    	//	setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		}
		else if(contrato_pai_local.getMedida().toUpperCase().equals("QUILOGRAMAS")) {
			rQuanKG.setSelected(true);
			rQuanS.setSelected(false);
			rQuanT.setSelected(false);
    		unidadeGlobal = "quilogramas";
    	//	setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		}
		else if(contrato_pai_local.getMedida().toUpperCase().equals("TONELADAS")) {
			rQuanKG.setSelected(false);
			rQuanS.setSelected(false);
			rQuanT.setSelected(true);
    		unidadeGlobal = "toneladas";
    	//	setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		}
		
		//safra
		CadastroSafra safra_contrato_pai_local = contrato_pai_local.getModelo_safra();
		modelSafra.setSelectedItem(safra_contrato_pai_local);
        lblCodigoContrato.setText(Integer.toString(safra_contrato_pai_local.getCodigo()));
		setClausula2(safra_contrato_pai_local.getProduto().getNome_produto());

         //quantidades e preços
	
		entQuantidade.setText(Double.toString(contrato_pai_local.getQuantidade()));
		entPreco.setText(Double.toString(contrato_pai_local.getValor_produto()));
	 
		
		
		 Locale ptBr = new Locale("pt", "BR");
  	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(contrato_pai_local.getValor_a_pagar().toPlainString()));
      
  	   	lblValorTotal.setText(valorString);
		mostrar_valor_total_contrato.setText(valorString);
		  valor_total = contrato_pai_local.getValor_a_pagar() ;
		   valor_atual = new BigDecimal("0");
		 // valor_acumulado =  new BigDecimal("0");
		  
		  BigDecimal valor_tot_comissao = contrato_pai_local.getValor_comissao();
   	      BigDecimal quantidade = new BigDecimal(contrato_pai_local.getQuantidade());
		  //comissao
		  if(contrato_pai_local.getComissao() == 1) {
			  setComissao();
		       if(contrato_pai_local.getClausula_comissao() == 1) {
		    	   
		    	   chBoxClausulaComissao.setSelected(true);
		    	   
		    	  
		    	   System.out.println("Quatidade: " + quantidade + "valor total da comissao: " + valor_tot_comissao);
		    	   String valor_por_unidade = (valor_tot_comissao.divide(quantidade)).toPlainString();
		    	   entComissao.setText(valor_por_unidade);	   
		    	   setClausulaComissao(valor_por_unidade);
		    	   
		       }
		       
		  }
     	   	String valorString2 = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(valor_tot_comissao.toPlainString()));
         
   	            mostrar_valor_total_comissao.setText(valorString2);
   	         lblValorTotalComisao1.setText(valorString2);
   	         valor_total_comissao = valor_tot_comissao;
   	    
		  
		  
   	     setPagamentos();
		  
	}
	
	 public void setPagamentos( ) {
     	System.out.println("Metodo setPagamentos chamado!");

	       	String cpf , banco, codigo, agencia, conta, id, nome, valor_pagamento, data_pagamento;
            float valor_total_pagamentos = 0;

            if(contrato_pai_local.getPagamentos() != null) {
            	System.out.println("Existem pagamentos para edicao!");
	       	for(CadastroContrato.CadastroPagamento pag : contrato_pai_local.getPagamentos()) {
	       		
	       	 if(pag != null) {	
	   			ContaBancaria conta_bc = pag.getConta();
                  float pag_local = 0;
	       		
	       		if(conta_bc != null) {
	       		   id = Integer.toString(conta_bc.getId_conta());
	   	 		cpf = conta_bc.getCpf_titular();
	   	 		banco = conta_bc.getBanco();
	   	 		nome = conta_bc.getNome();
	   	 		codigo = conta_bc.getCodigo();
	   	 		agencia = conta_bc.getAgencia();
	   	 		conta = conta_bc.getConta();
	   	 		
	   	 	valor_total_pagamentos += Float.parseFloat(pag.getValor_string());
       		System.out.println("o valor total agora e: " + valor_total_pagamentos);
       		
       		Locale ptBr = new Locale("pt", "BR");
	       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(Float.parseFloat(pag.getValor_string()));
               
       		modelo_cb.addRow(new Object[]{pag.getId(), id, cpf, nome, banco, 
				       codigo, agencia, conta, valorString, pag.getData_pagamento()});
	       		}/*
	       		else {
	    	    id = "00";
		 		cpf = "Há Informar";
		 		banco = "Há Informar";
		 		nome = "Há Informar";
		 		codigo = "Há Informar";
		 		agencia = "Há Informar";
		 		conta = "Há Informar";
	       		}
	       		*/
	       		
	       	
	       		
	       	 }
	       	}
	    	Locale ptBr = new Locale("pt", "BR");
       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
	       	mostrar_soma_atual_pagamentos.setText(valorString);
            valor_acumulado  = new BigDecimal(valor_total_pagamentos);
            System.out.println("valor acumulado recebido: " + valor_acumulado);
            pagamentos = contrato_pai_local.getPagamentos();
            
	      }
				
	    
	    }
	 
	 
	 public void setClausulas( ) {

		 
		if(contrato_pai_local.getTexto_clausulas() != null) { 
		 int num_clausulas = 1;
		  //clausulas
    	 ArrayList<String> clausulas = new ArrayList<>();
    	 String texto_clausulas = contrato_pai_local.getTexto_clausulas();
    	 String separada []= texto_clausulas.split(";");
    	 for(int i = 0; i < separada.length; i++) {
    		 if(separada[i] != null && !separada[i].equals(""))
    		 {
    			
    				 System.out.println("clausula: " + separada[i]);
        			 clausulas.add(separada[i]);
	            
    		 }
    	 }
    

    	 contrato_pai_local.setClausulas(clausulas);
		 
		 
         for(String termo : contrato_pai_local.getClausulas()) {
        	 if(termo != null && !termo.equals(""))
        	 {
                if(num_clausulas == 1) {
                	entClausula1.setText(termo);
                	chBoxClausula1.setSelected(true);
                	num_clausulas++;
                }else if(num_clausulas == 2) {
                	entClausula2.setText(termo);
                	chBoxClausula2.setSelected(true);

                	num_clausulas++;

                }
                else if(num_clausulas == 3) {
                
                	
                	if(contrato_pai_local.getClausula_comissao() == 1) {
                		entClausula3.setText(termo);
                    	chBoxClausula3.setSelected(true);
                	}else {
                		entClausula4.setText(termo);
                    	chBoxClausula4.setSelected(true);
                    	entClausula4.setEditable(true);
                    	entClausula4.setEnabled(true);

                	}

                	num_clausulas =  num_clausulas +2;

                }
                else if(num_clausulas == 4) {
                	entClausula4.setText(termo);
                	chBoxClausula4.setSelected(true);
                	entClausula4.setEditable(true);
                	entClausula4.setEnabled(true);

                	num_clausulas++;

                }
                else if(num_clausulas == 5) {
                	entClausula5.setText(termo);
                	chBoxClausula5.setSelected(true);
                	entClausula5.setEditable(true);
                	entClausula5.setEnabled(true);


                	num_clausulas++;

                }
                else if(num_clausulas == 6) {
                	entClausula6.setText(termo);
                	chBoxClausula6.setSelected(true);
                	entClausula6.setEditable(true);
                	entClausula6.setEnabled(true);


                	num_clausulas++;

                }else {
                	
                }

        	 }
         }//fim do for
		}
	 }
	 
	 public void rotinasSubContrato() {
		//safra
			CadastroSafra safra_contrato_pai_local = contrato_pai_local.getModelo_safra();
			modelSafra.setSelectedItem(safra_contrato_pai_local);
	        lblCodigoContrato.setText(Integer.toString(safra_contrato_pai_local.getCodigo()));
			setClausula2(safra_contrato_pai_local.getProduto().getNome_produto());
		 
			
			//data de entrega
			System.out.println("data de entrega contrato pai:" + contrato_pai_local.getData_entrega());
			entDataEntrega.setText(contrato_pai_local.getData_entrega());
			
			//unidade de medida
			if(contrato_pai_local.getMedida().toUpperCase().equals("SACOS")) {
				rQuanKG.setSelected(false);
				rQuanS.setSelected(true);
				rQuanT.setSelected(false);
	    		unidadeGlobal = "sacos";
	    	//	setClausulaComissao(entComissao.getText().toString().replace(".", ","));
			}
			else if(contrato_pai_local.getMedida().toUpperCase().equals("QUILOGRAMAS")) {
				rQuanKG.setSelected(true);
				rQuanS.setSelected(false);
				rQuanT.setSelected(false);
	    		unidadeGlobal = "quilogramas";
	    	//	setClausulaComissao(entComissao.getText().toString().replace(".", ","));
			}
			else if(contrato_pai_local.getMedida().toUpperCase().equals("TONELADAS")) {
				rQuanKG.setSelected(false);
				rQuanS.setSelected(false);
				rQuanT.setSelected(true);
	    		unidadeGlobal = "toneladas";
	    	//	setClausulaComissao(entComissao.getText().toString().replace(".", ","));
			}
			
			
	 }
	 
	 
	 public CadastroContrato.CadastroTarefa criarTarefa(int flag) {
		
		 CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();


		 if(flag == 1) {
			 tarefa.setNome_tarefa("Contrato Criado");
		 }else if(flag == 2) {
			 tarefa.setNome_tarefa("Sub contrato Criado");
		 }
			 //cria a tarefa de insercao de contrato
			 tarefa.setId_tarefa(0);
			 tarefa.setDescricao_tarefa("Criação de tarefa");

			 tarefa.setStatus_tarefa(1);
			 tarefa.setMensagem("tarefa criada");
			 
			 GetData data = new GetData();
			 tarefa.setHora(data.getHora());
			 tarefa.setData(data.getData());
			 tarefa.setHora_agendada(data.getHora());
			 tarefa.setData_agendada(data.getData());
			 
			 tarefa.setCriador(login);
             tarefa.setExecutor(login);
             
             tarefa.setPrioridade(1);
			 
		 
		 
		 return tarefa;
		 
	 }
	
	 public boolean apagarDiretorio() {
		 
		try {
			ManipularTxt manipular = new ManipularTxt();
			 manipular.limparDiretorio(new File(novo_contrato.getCaminho_diretorio_contrato()));
			 System.out.println("A pasta do diretorio do contrato foi excuida!");
             return true;
			 
		}catch(Exception f) {
			 System.out.println("Não foi possivel excluir a pasta do diretorio do arquivo!");

			return false;
		}
		 
		 
	 }
	 
	 
	 
	 public void setInfoPai() {
		 
		 CadastroCliente compradores[] = contrato_pai_local.getCompradores();
	    	CadastroCliente vendedores[] = contrato_pai_local.getVendedores();
          
         
         
           JPanel painelInfoPai = new JPanel();
		   painelInfoPai.setBackground(SystemColor.info);
		   painelInfoPai.setBounds(43, 41, 241, 245);
		   painelDadosIniciais.add(painelInfoPai);
		   painelDadosProdutos.add(painelInfoPai);
		   
		              painelInfoPai.setLayout(null);
		              
		              JLabel lblNewLabel_1 = new JLabel("Informações do contrato original");
		              lblNewLabel_1.setBounds(44, 13, 171, 14);
		              painelInfoPai.add(lblNewLabel_1);
		              
		              JLabel lblNewLabel_2 = new JLabel("Comprador:");
		              lblNewLabel_2.setBounds(10, 38, 69, 14);
		              painelInfoPai.add(lblNewLabel_2);
		              
		              JLabel lblNewLabel_2_1 = new JLabel("Vendedor:");
		              lblNewLabel_2_1.setBounds(10, 63, 69, 14);
		              painelInfoPai.add(lblNewLabel_2_1);
		              
		              JLabel lblNewLabel_2_1_1 = new JLabel("Vendedor:");
		              lblNewLabel_2_1_1.setBounds(10, 88, 69, 14);
		              painelInfoPai.add(lblNewLabel_2_1_1);
		              
		              JLabel lblNewLabel_2_1_1_1 = new JLabel("Safra:");
		              lblNewLabel_2_1_1_1.setBounds(10, 113, 69, 14);
		              painelInfoPai.add(lblNewLabel_2_1_1_1);
		              
		              JLabel lblNewLabel_2_1_1_2 = new JLabel("Medida:");
		              lblNewLabel_2_1_1_2.setBounds(10, 138, 69, 14);
		              painelInfoPai.add(lblNewLabel_2_1_1_2);
		              
		              JLabel lblNewLabel_2_1_1_2_1 = new JLabel("Quantidade:");
		              lblNewLabel_2_1_1_2_1.setBounds(10, 166, 69, 14);
		              painelInfoPai.add(lblNewLabel_2_1_1_2_1);
		              
		              JLabel lblNewLabel_2_1_1_2_1_1 = new JLabel("Valor por Medida:");
		              lblNewLabel_2_1_1_2_1_1.setBounds(10, 199, 96, 14);
		              painelInfoPai.add(lblNewLabel_2_1_1_2_1_1);
		              
		              JLabel lblNewLabel_2_1_1_2_1_2 = new JLabel("Valor Total:");
		              lblNewLabel_2_1_1_2_1_2.setBounds(10, 224, 69, 14);
		              painelInfoPai.add(lblNewLabel_2_1_1_2_1_2);
		              
		              JLabel lblInfoComprador = new JLabel("");
		              lblInfoComprador.setBounds(129, 38, 103, 14);
		              painelInfoPai.add(lblInfoComprador);
		              if(compradores[0].getTipo_pessoa() == 0) {
		           	   //pessoa fisica
		                  lblInfoComprador.setText(compradores[0].getNome_empresarial());

		              }else {
		                  lblInfoComprador.setText(compradores[0].getNome_fantaia());
		     
		              }
		              
		              JLabel lblInfoVendedor1 = new JLabel("");
		              lblInfoVendedor1.setBounds(129, 63, 86, 14);
		              painelInfoPai.add(lblInfoVendedor1);
		              if(vendedores[0].getTipo_pessoa() == 0) {
		           	   //pessoa fisica
		                  lblInfoComprador.setText(vendedores[0].getNome_empresarial());

		              }else {
		           	   lblInfoVendedor1.setText(vendedores[0].getNome_fantaia());
		     
		              }
		              
		              
		              JLabel lblInfoVendedor2 = new JLabel("");
		              lblInfoVendedor2.setBounds(129, 88, 86, 14);
		              painelInfoPai.add(lblInfoVendedor2);
		              if(vendedores[1] != null) {
		           	   if(vendedores[1].getTipo_pessoa() == 0) {
		                     
		           	   //pessoa fisica
		                  lblInfoComprador.setText(vendedores[0].getNome_empresarial());
		              }else {
		           	   lblInfoVendedor1.setText(vendedores[0].getNome_fantaia());
		     
		              }

		              }

	 
           JLabel lblInfoSafra = new JLabel("");
           lblInfoSafra.setBounds(129, 113, 90, 14);
           painelInfoPai.add(lblInfoSafra);
           String info_safra = contrato_pai_local.getModelo_safra().getProduto().getNome_produto() + " " + contrato_pai_local.getModelo_safra().getAno_plantio() + "/" + contrato_pai_local.getModelo_safra().getAno_colheita();

           lblInfoSafra.setText(info_safra);
           
           JLabel lblInfoMedida = new JLabel("");
           lblInfoMedida.setBounds(129, 138, 96, 14);
           painelInfoPai.add(lblInfoMedida);
           lblInfoMedida.setText(contrato_pai_local.getMedida());
           
           JLabel lblInfoQuantidade = new JLabel("");
           lblInfoQuantidade.setBounds(129, 166, 90, 14);
           painelInfoPai.add(lblInfoQuantidade);
           lblInfoQuantidade.setText(Double.toString(contrato_pai_local.getQuantidade()) + " " + contrato_pai_local.getMedida());
           
           JLabel lblInfoValorQuantidade = new JLabel("");
           lblInfoValorQuantidade.setBounds(129, 199, 90, 14);
           painelInfoPai.add(lblInfoValorQuantidade);
    	  	Locale ptBr = new Locale("pt", "BR");
	       	   	String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_pai_local.getValor_produto());
	            lblInfoValorQuantidade.setText(valorString);

           JLabel lblInfoValorTotal = new JLabel("");
           lblInfoValorTotal.setBounds(136, 224, 96, 14);
           painelInfoPai.add(lblInfoValorTotal);
      	   	 valorString = NumberFormat.getCurrencyInstance(ptBr).format(Double.parseDouble(contrato_pai_local.getValor_a_pagar().toPlainString()));

           lblInfoValorTotal.setText(valorString);
	 }
}
