package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;



import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Carregamento;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNFe;
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
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
import main.java.gui.TelaCadastroCliente;
import main.java.gui.TelaMain;
import main.java.gui.TelaRomaneios;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.CopiarArquivo;
import main.java.manipular.GetDadosGlobais;
import main.java.manipular.ManipularNotasFiscais;
import main.java.manipular.ManipularRomaneios;
import main.java.manipular.ManipularTxt;
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
import main.java.classesExtras.ComboBoxPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Window;
import net.miginfocom.swing.MigLayout;


public class TelaConfirmarCarregamento extends JDialog {

	private JTabbedPane abas = new JTabbedPane();
	private JPanel painelConfirmar = new JPanel();
	private JPanel painelSelecionar = new JPanel();
	private JFrame telaPaiJFrame;
	private String servidor_unidade;
	private TelaConfirmarCarregamento isto;
	private JDialog telaPai;
	private JTextField entDataCarregamento;
	private JComboBox cBContrato, cBCliente, cBProduto, cbVeiculo, cBTransportador, cBVendedor;
	private CadastroContrato contrato_local;
	private JTextArea textAreaObs;
	private CadastroCliente transportador_carregamento;
	private CadastroCliente cliente_carregamento;
	private CadastroProduto produto_carregamento;
	private CadastroNFe nota_fiscal_carregamento;
	private CadastroContrato contrato_carregamento;
	private CadastroCliente.Veiculo veiculo_carregamento;
	private CadastroCliente vendedor;
	private JLabel  lblProdutoCarregamento, lblDataCarregamento,
			lblCompradorCarregamento, lblContratoCarregamento, lblTransportadorCarregamento, lblVeiculoCarregamento,lblVendedorCarregamento;
	private JTextField entRomaneio;
	private JTextField entCodigoNFVenda1;
	private JTextField entPesoNFVenda1;
	private JTextField pesoRomaneio;
	private JTextField entCodigoNFInterna;
	private JTextField entPesoNFInterna;
	private JTextField entCodigoNFComplemento;
	private JTextField entPesoNFComplemento;
	private JCheckBox chkBoxDataHoje;
	private Carregamento carregamento_global;
	private JCheckBox chckBoxNFInternaNaoAplicavel, chckBoxNFVenda1NaoAplicavel, chckBoxNFComplementoNaoAplicavel;

	private CadastroRomaneio romaneio_carregamento;
	private CadastroNFe nota_fiscal_venda1_carregamento, nota_fiscal_complemento_carregamento, nota_fiscal_interna_carregamento;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JLabel lblCodigoRomaneio, lblPesoRomaneio,lblCaminhoRomaneio;
	private JTextArea lblNotaFiscalVenda1, lblNotaFiscalComplemento,lblObs, lblNotaFiscalInterna; 
	private JLabel lblCaminhoNFVenda1, lblCaminhoNFComplemento, lblCaminhoNFInterna;
	private JTextField entValorNFVenda1;
	private JTextField entValorNFComplemento;
	private JButton btnLerNfInterna, btnLerNfVenda, btnLerNfComplemento;
	private JTextField entRemetenteNFComplemento;
	private JTextField entDestinatarioNFComplemento;
	private JTextField entRemetenteNFVenda1;
	private JTextField entDestinatarioNFVenda1;

	
	public TelaConfirmarCarregamento(int flag_modo_tela ,CadastroContrato _contrato_local, CadastroContrato.Carregamento _carregamento, Window janela_pai) {
		//setAlwaysOnTop(true);

		//setModal(true);

		isto = this;
		this.contrato_local = _contrato_local;
          getDadosGlobais();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		if(flag_modo_tela == 0) {
			//cricao
		setTitle("E-Contract - Adicionar Carregamento");
		
		}else if(flag_modo_tela == 1){
			setTitle("E-Contract - Editar Carregamento");
			carregamento_global = _carregamento;

		}

		abas.setBackground(new Color(255, 255, 255));
		abas.setBorder(new EmptyBorder(5, 5, 5, 5));
		abas = new JTabbedPane();
		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelSelecionar.setBackground(new Color(255, 255, 255));

		abas.addTab("Selecionar", painelSelecionar);
		painelSelecionar.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 153, 153));
		painelSelecionar.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[150px][117px,grow][6px][65px][23px][7px][31px][10px][62px][4px][170px,grow][9px][100px][5px][169px][147px]", "[31px][30px][32px][32px][30px][30px][30px][31px][18px][30px][18px][28px][35.00px][30px][42.00px][39.00][54.00][17.00]"));

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_3, "cell 0 0,alignx right,aligny center");

		entDataCarregamento = new JTextField();
		entDataCarregamento.setEnabled(false);

		 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 entDataCarregamento.setText(strLocalDate2);
		
		entDataCarregamento.setEditable(false);
		entDataCarregamento.setColumns(10);
		panel_1.add(entDataCarregamento, "cell 1 0,grow");

		 chkBoxDataHoje = new JCheckBox("Data Atual");
		chkBoxDataHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	        	  
				if(chkBoxDataHoje.isSelected()) {
					chkBoxDataHoje.setSelected(true);
					entDataCarregamento.setEditable(false);
					 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					 entDataCarregamento.setText(strLocalDate2);
					
				}else {
					chkBoxDataHoje.setSelected(false);
					entDataCarregamento.setEnabled(true);
					entDataCarregamento.setEditable(true);
				}
			}
		});
		chkBoxDataHoje.setSelected(true);
		panel_1.add(chkBoxDataHoje, "cell 3 0 2 1,growx,aligny top");

		JLabel lblNewLabel_5 = new JLabel("Transportador:");
		lblNewLabel_5.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_5, "cell 0 4,alignx right,aligny center");

		cBTransportador = new JComboBox();
		panel_1.add(cBTransportador, "cell 1 4 6 1,grow");

		JButton btnSelecionarTransportador = new JButton("Selecionar");
		btnSelecionarTransportador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores selecionar_transportador = new TelaTransportadores(2,isto);
				selecionar_transportador.setTelaPai(isto);
				selecionar_transportador.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarTransportador, "cell 8 4 3 1,alignx left,growy");

		cbVeiculo = new JComboBox();
		cbVeiculo.setEditable(false);
		panel_1.add(cbVeiculo, "cell 1 5 3 1,grow");

		JLabel lblNewLabel_5_1 = new JLabel("Veiculo:");
		lblNewLabel_5_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_5_1, "cell 0 5,alignx right,aligny center");

		JLabel lblNewLabel_8 = new JLabel("Contrato:");
		lblNewLabel_8.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_8, "cell 0 1,alignx right,aligny center");

		cBContrato = new JComboBox();
		cBContrato.setEnabled(false);
		panel_1.add(cBContrato, "cell 1 1 6 1,grow");

		JButton btnSelecionarContrato = new JButton("Selecionar");
		btnSelecionarContrato.setEnabled(false);
		btnSelecionarContrato.setVisible(false);
		btnSelecionarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos contrato = new TelaContratos(1, isto);
				contrato.setTelaPai(isto);
				contrato.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarContrato, "cell 8 1 3 1,alignx left,growy");

		JLabel lblNewLabel_8_1 = new JLabel("Comprador:");
		lblNewLabel_8_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_8_1, "cell 0 2,alignx right,aligny center");

		cBCliente = new JComboBox();
		panel_1.add(cBCliente, "cell 1 2 6 1,grow");

		JButton btnSelecionarCliente = new JButton("Selecionar");
		btnSelecionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 5, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		CadastroCliente compradores[] = contrato_local.getCompradores();
		setClienteCarregamento(compradores[0]);
		panel_1.add(btnSelecionarCliente, "cell 8 2 3 1,alignx left,growy");

		JLabel lblNewLabel_9_1 = new JLabel("Produto:");
		lblNewLabel_9_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1, "cell 0 6,alignx right,aligny center");

		cBProduto = new JComboBox();
		panel_1.add(cBProduto, "cell 1 6 4 1,grow");

		
		JButton btnSelecionarProduto = new JButton("Selecionar");
		btnSelecionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaProdutos produto = new TelaProdutos(1,isto);
				produto.setTelaPai(isto);
				produto.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarProduto, "cell 6 6 3 1,alignx left,growy");
		
		CadastroProduto prod = contrato_local.getModelo_produto();
		setProduto(prod);
		
		JLabel lblNewLabel_8_1_1 = new JLabel("Vendedor:");
		lblNewLabel_8_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_8_1_1, "cell 0 3,alignx right,aligny center");
		
		 cBVendedor = new JComboBox();
		panel_1.add(cBVendedor, "cell 1 3 6 1,grow");
		
		JButton btnSelecionarVendedor = new JButton("Selecionar");
		btnSelecionarVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 6, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarVendedor, "cell 8 3 3 1,alignx left,growy");
		
		JLabel lblNewLabel_9_1_1 = new JLabel("Código Romaneio:");
		lblNewLabel_9_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1, "cell 0 7,alignx center,aligny center");
		
		JLabel lblNewLabel_9_2 = new JLabel("Código NF Venda 1:");
		lblNewLabel_9_2.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_2, "cell 0 11,alignx right,aligny center");
		
		entRomaneio = new JTextField();
		entRomaneio.setForeground(Color.BLACK);
		entRomaneio.setColumns(10);
		entRomaneio.setBackground(Color.WHITE);
		panel_1.add(entRomaneio, "cell 1 7 3 1,grow");
		
		entCodigoNFVenda1 = new JTextField();
		entCodigoNFVenda1.setForeground(Color.BLACK);
		entCodigoNFVenda1.setColumns(10);
		entCodigoNFVenda1.setBackground(Color.WHITE);
		panel_1.add(entCodigoNFVenda1, "cell 1 11 3 1,grow");
		
		JLabel lblNewLabel_9_1_1_1 = new JLabel("Peso Romaneio:");
		lblNewLabel_9_1_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_1, "cell 6 7 3 1,growx,aligny center");
		
		JLabel lblNewLabel_9_1_1_1_1 = new JLabel("Peso NF Venda 1:");
		lblNewLabel_9_1_1_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_1_1, "cell 6 11 3 1,growx,aligny center");
		
		entPesoNFVenda1 = new JTextField();
		entPesoNFVenda1.setForeground(Color.BLACK);
		entPesoNFVenda1.setColumns(10);
		entPesoNFVenda1.setBackground(Color.WHITE);
		panel_1.add(entPesoNFVenda1, "cell 10 11,grow");
		
		pesoRomaneio = new JTextField();
		pesoRomaneio.setForeground(Color.BLACK);
		pesoRomaneio.setColumns(10);
		pesoRomaneio.setBackground(Color.WHITE);
		panel_1.add(pesoRomaneio, "cell 10 7,grow");
		
		JButton btnLerRomaneio = new JButton("Ler Romaneio");
		btnLerRomaneio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
				
				
				
				String destinatario = "";
				
				if(cliente_carregamento.getTipo_pessoa() == 0) {
					destinatario = cliente_carregamento.getNome_empresarial().trim().toUpperCase();
				}else {
					destinatario = cliente_carregamento.getNome_fantaia().trim().toUpperCase();
				}
				
					String remetente = "";
				
				if(vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim().toUpperCase();
				}else {
					remetente = vendedor.getNome_fantaia().trim().toUpperCase();
				}
				
				
				String produto = produto_carregamento.getNome_produto().toUpperCase();
				
				 TelaRomaneios telaRomaneio;
                 telaRomaneio = new TelaRomaneios(0,isto);
				 telaRomaneio.limpar();
				 telaRomaneio.setDadosPesquisa("", "", "", produto, entRomaneio.getText());
				 telaRomaneio.setTelaPai(isto);
				 telaRomaneio.pesquisarTodosOsRomaneios();

				 telaRomaneio.setVisible(true);
				
			}
		});
		panel_1.add(btnLerRomaneio, "cell 12 7 3 1,alignx left,growy");
		
		 btnLerNfVenda = new JButton("Ler NF Venda 1");
		btnLerNfVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String destinatario = "";
				
				if(cliente_carregamento.getTipo_pessoa() == 0) {
					destinatario = cliente_carregamento.getNome_empresarial().trim();
				}else {
					destinatario = cliente_carregamento.getNome_fantaia().trim();
				}
				
					String remetente = "";
				
				if(vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim();
				}else {
					remetente = vendedor.getNome_fantaia().trim();
				}
				
				
				String produto = produto_carregamento.getNome_produto();
				
				
				
				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0,2,isto);
					telaTodasNotasFiscais.setTelaPai(isto);
					telaTodasNotasFiscais.limpar();
					telaTodasNotasFiscais.setRetornoGlobal(2);
					telaTodasNotasFiscais.setDadosPesquisa("", "", "Venda", produto, entCodigoNFVenda1.getText());
					telaTodasNotasFiscais.habilitarBtnSelecionar();
					telaTodasNotasFiscais.pesquisar_notas();

					telaTodasNotasFiscais.setVisible(true);
				
			}
		});
		panel_1.add(btnLerNfVenda, "cell 15 11,alignx left,growy");
		
		JLabel lblNewLabel_9_2_1 = new JLabel("Código NF Interna:");
		lblNewLabel_9_2_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_2_1, "cell 0 9,alignx right,aligny center");
		
		entCodigoNFInterna = new JTextField();
		entCodigoNFInterna.setForeground(Color.BLACK);
		entCodigoNFInterna.setColumns(10);
		entCodigoNFInterna.setBackground(Color.WHITE);
		panel_1.add(entCodigoNFInterna, "cell 1 9 3 1,grow");
		
		JLabel lblNewLabel_9_1_1_1_1_1 = new JLabel("Peso NF Interna:");
		lblNewLabel_9_1_1_1_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_1_1_1, "cell 6 9 3 1,alignx center,aligny center");
		
		entPesoNFInterna = new JTextField();
		entPesoNFInterna.setForeground(Color.BLACK);
		entPesoNFInterna.setColumns(10);
		entPesoNFInterna.setBackground(Color.WHITE);
		panel_1.add(entPesoNFInterna, "cell 10 9,grow");
		
		
		 btnLerNfInterna = new JButton("Ler NF Interna");
		btnLerNfInterna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String destinatario = "";
				
				if(cliente_carregamento.getTipo_pessoa() == 0) {
					destinatario = cliente_carregamento.getNome_empresarial().trim();
				}else {
					destinatario = cliente_carregamento.getNome_fantaia().trim();
				}
				
					String remetente = "";
				
				if(vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim();
				}else {
					remetente = vendedor.getNome_fantaia().trim();
				}
				
				
				String produto = produto_carregamento.getNome_produto();
				
				
				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0,2,isto);
				
					telaTodasNotasFiscais.setTelaPai(isto);
					telaTodasNotasFiscais.limpar();
					telaTodasNotasFiscais.setRetornoGlobal(1);
					telaTodasNotasFiscais.setDadosPesquisa("", "", "", produto, entCodigoNFInterna.getText());
					telaTodasNotasFiscais.habilitarBtnSelecionar();
					telaTodasNotasFiscais.pesquisar_notas();

					telaTodasNotasFiscais.setVisible(true);
				
				
			}
		});
		panel_1.add(btnLerNfInterna, "cell 12 9 3 1,alignx left,growy");
		
		JLabel lblNewLabel_9_1_1_1_1_3 = new JLabel("Valor NF Venda 1:");
		lblNewLabel_9_1_1_1_1_3.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_1_1_3, "cell 12 11,growx,aligny center");
		
		entValorNFVenda1 = new JTextField();
		entValorNFVenda1.setForeground(Color.BLACK);
		entValorNFVenda1.setColumns(10);
		entValorNFVenda1.setBackground(Color.WHITE);
		panel_1.add(entValorNFVenda1, "cell 14 11,grow");
		
		 chckBoxNFVenda1NaoAplicavel = new JCheckBox("NF Venda 1 Não Aplicável");
		 chckBoxNFVenda1NaoAplicavel.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(chckBoxNFVenda1NaoAplicavel.isSelected()) {
		 			desativarNFVenda1();
		 		}else {
		 			ativarNFVenda1();

		 		}
		 	}
		 });
		chckBoxNFVenda1NaoAplicavel.setForeground(Color.WHITE);
		panel_1.add(chckBoxNFVenda1NaoAplicavel, "cell 1 10 4 1,grow");
		
		
		 chckBoxNFInternaNaoAplicavel = new JCheckBox("NF Interna Não Aplicável");
		 chckBoxNFInternaNaoAplicavel.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(chckBoxNFInternaNaoAplicavel.isSelected()){
		 			desativarNFInterna();
		 		}else {
		 			ativarNFInterna();

		 		}
		 	}
		 });
		chckBoxNFInternaNaoAplicavel.setForeground(Color.WHITE);
		panel_1.add(chckBoxNFInternaNaoAplicavel, "cell 1 8 4 1,grow");
		 
		  chckBoxNFComplementoNaoAplicavel = new JCheckBox("NF Complemento Não Aplicável");
		  chckBoxNFComplementoNaoAplicavel.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		if(chckBoxNFComplementoNaoAplicavel.isSelected()) {
		  			desativarNFComplemento();
		  		}else {
		  			ativarNFComplemento();
		  		}
		  	}
		  });
		  
		  JLabel lblNewLabel_9_2_2_1_1 = new JLabel("Rementente:");
		  lblNewLabel_9_2_2_1_1.setForeground(Color.WHITE);
		  panel_1.add(lblNewLabel_9_2_2_1_1, "cell 0 12,alignx trailing");
		  
		  entRemetenteNFVenda1 = new JTextField();
		  entRemetenteNFVenda1.setForeground(Color.BLACK);
		  entRemetenteNFVenda1.setColumns(10);
		  entRemetenteNFVenda1.setBackground(Color.WHITE);
		  panel_1.add(entRemetenteNFVenda1, "cell 1 12 8 1,growx,aligny center");
		  
		  JLabel lblNewLabel_9_1_1_1_1_2_1_1 = new JLabel("Destinatário:");
		  lblNewLabel_9_1_1_1_1_2_1_1.setForeground(Color.WHITE);
		  panel_1.add(lblNewLabel_9_1_1_1_1_2_1_1, "cell 10 12,alignx right");
		  
		  entDestinatarioNFVenda1 = new JTextField();
		  entDestinatarioNFVenda1.setForeground(Color.BLACK);
		  entDestinatarioNFVenda1.setColumns(10);
		  entDestinatarioNFVenda1.setBackground(Color.WHITE);
		  panel_1.add(entDestinatarioNFVenda1, "cell 12 12 4 1,growx,aligny center");
		  chckBoxNFComplementoNaoAplicavel.setForeground(Color.WHITE);
		  panel_1.add(chckBoxNFComplementoNaoAplicavel, "cell 1 13 3 1,grow");
		 
		 JLabel lblNewLabel_9_2_2 = new JLabel("Código NF Complemento:");
		 lblNewLabel_9_2_2.setForeground(Color.WHITE);
		 panel_1.add(lblNewLabel_9_2_2, "cell 0 14,growx,aligny center");
		 
		 entCodigoNFComplemento = new JTextField();
		 entCodigoNFComplemento.setForeground(Color.BLACK);
		 entCodigoNFComplemento.setColumns(10);
		 entCodigoNFComplemento.setBackground(Color.WHITE);
		 panel_1.add(entCodigoNFComplemento, "cell 1 14 3 1,growx,aligny center");
		 
		 JLabel lblNewLabel_9_1_1_1_1_2 = new JLabel("Peso NF Complemento:");
		 lblNewLabel_9_1_1_1_1_2.setForeground(Color.WHITE);
		 panel_1.add(lblNewLabel_9_1_1_1_1_2, "cell 4 14 5 1,growx,aligny center");
		 
		 entPesoNFComplemento = new JTextField();
		 entPesoNFComplemento.setForeground(Color.BLACK);
		 entPesoNFComplemento.setColumns(10);
		 entPesoNFComplemento.setBackground(Color.WHITE);
		 panel_1.add(entPesoNFComplemento, "cell 10 14,growx,aligny center");
		 
		 JLabel lblNewLabel_9_1_1_1_1_3_1 = new JLabel("Valor NF Compl:");
		 lblNewLabel_9_1_1_1_1_3_1.setForeground(Color.WHITE);
		 panel_1.add(lblNewLabel_9_1_1_1_1_3_1, "cell 12 14,growx,aligny center");
		 
		 entValorNFComplemento = new JTextField();
		 entValorNFComplemento.setForeground(Color.BLACK);
		 entValorNFComplemento.setColumns(10);
		 entValorNFComplemento.setBackground(Color.WHITE);
		 panel_1.add(entValorNFComplemento, "cell 14 14,growx,aligny center");
		 
		  btnLerNfComplemento = new JButton("Ler NF Complemento");
		  btnLerNfComplemento.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  	
		  		String destinatario = "";
		  		
		  		if(cliente_carregamento.getTipo_pessoa() == 0) {
		  			destinatario = cliente_carregamento.getNome_empresarial().trim();
		  		}else {
		  			destinatario = cliente_carregamento.getNome_fantaia().trim();
		  		}
		  		
		  			String remetente = "";
		  		
		  		if(vendedor.getTipo_pessoa() == 0) {
		  			remetente = vendedor.getNome_empresarial().trim();
		  		}else {
		  			remetente = vendedor.getNome_fantaia().trim();
		  		}
		  		
		  		
		  		String produto = produto_carregamento.getNome_produto();
		  		
		  		
		  		
		  		TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0,2,isto);
		  			telaTodasNotasFiscais.setTelaPai(isto);
		  			telaTodasNotasFiscais.limpar();
		  			telaTodasNotasFiscais.setRetornoGlobal(3);
		  			telaTodasNotasFiscais.setDadosPesquisa(destinatario, "", "Venda", produto, entCodigoNFComplemento.getText());
		  			telaTodasNotasFiscais.habilitarBtnSelecionar();
		  			telaTodasNotasFiscais.pesquisar_notas();

		  			telaTodasNotasFiscais.setVisible(true);
		  		

		  	}
		  });
		  panel_1.add(btnLerNfComplemento, "cell 15 14,grow");
		 
		 JLabel lblNewLabel_9_2_2_1 = new JLabel("Rementente:");
		 lblNewLabel_9_2_2_1.setForeground(Color.WHITE);
		 panel_1.add(lblNewLabel_9_2_2_1, "cell 0 15,alignx trailing");
		 
		 entRemetenteNFComplemento = new JTextField();
		 entRemetenteNFComplemento.setForeground(Color.BLACK);
		 entRemetenteNFComplemento.setColumns(10);
		 entRemetenteNFComplemento.setBackground(Color.WHITE);
		 panel_1.add(entRemetenteNFComplemento, "cell 1 15 8 1,growx,aligny center");
		 
		 JLabel lblNewLabel_9_1_1_1_1_2_1 = new JLabel("Destinatário:");
		 lblNewLabel_9_1_1_1_1_2_1.setForeground(Color.WHITE);
		 panel_1.add(lblNewLabel_9_1_1_1_1_2_1, "cell 9 15 2 1,alignx right");
		 
		 entDestinatarioNFComplemento = new JTextField();
		 entDestinatarioNFComplemento.setForeground(Color.BLACK);
		 entDestinatarioNFComplemento.setColumns(10);
		 entDestinatarioNFComplemento.setBackground(Color.WHITE);
		 panel_1.add(entDestinatarioNFComplemento, "cell 12 15 4 1,growx,aligny center");
		 
		 JLabel lblNewLabel_4 = new JLabel("Observação:");
		 lblNewLabel_4.setForeground(Color.WHITE);
		 panel_1.add(lblNewLabel_4, "cell 0 16,alignx right,aligny top");
		
		 textAreaObs = new JTextArea();
		 textAreaObs.setWrapStyleWord(true);
		 textAreaObs.setLineWrap(true);
		 panel_1.add(textAreaObs, "cell 1 16 13 2,grow");
		
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		setVendedor(vendedores[0]);
		getContentPane().setLayout(new MigLayout("", "[1128px]", "[672px]"));

		getContentPane().add(abas, "cell 0 0,grow");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1150, 717);
		painelConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				atualizarDadosConfirmar();
			}
		});

		painelConfirmar.setBackground(new Color(0, 153, 153));

		// adiciona novos paines e suas abas
		abas.addTab("Confirmar", painelConfirmar);
		painelConfirmar.setLayout(new MigLayout("", "[105px][1px][1px][1px][185px][9px][53px][6px][35px][11px][90px][15px][46px][4px][6px][6px][77px][10px][302px]", "[32px][32px][34px][32px][24.00px][99.00px][29.00px][102.00px][29.00px][91.00px][27.00px][55px][23px]"));

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel, "cell 0 0,alignx right,aligny center");

		lblDataCarregamento = new JLabel("");
		lblDataCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblDataCarregamento.setForeground(Color.WHITE);
		lblDataCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblDataCarregamento, "cell 4 0,grow");

		lblCompradorCarregamento = new JLabel("");
		lblCompradorCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblCompradorCarregamento.setForeground(Color.WHITE);
		lblCompradorCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblCompradorCarregamento, "cell 4 1 7 1,grow");

		JLabel lblCliente = new JLabel("Comprador:");
		lblCliente.setForeground(Color.WHITE);
		painelConfirmar.add(lblCliente, "cell 0 1,alignx right,aligny center");

		JButton btnSalvar = new JButton("Confirmar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				//CadastroContrato.Carregamento carregamento_a_inserir = new CadastroContrato.Carregamento();
				CadastroContrato.Carregamento carregamento_a_inserir= getCarregamentoSalvar(new Carregamento());

				
				int retorno = gerenciar.inserirCarregamento(contrato_local.getId(),
						carregamento_a_inserir);
				if (retorno > 0) {
					JOptionPane.showMessageDialog(isto, "Carregamento Cadastrado!");
	            	   ((TelaGerenciarContrato) telaPaiJFrame).pesquisar_recebimentos(true);

					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos(true);
					carregamento_a_inserir.setId_carregamento(retorno);
				     carregamento_global =carregamento_a_inserir;
					gerarPastasEArquivos();
					
				
					
					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao inserir o carregamento\nConsulte o administrador do sistema!");
					isto.dispose();
				}

			}
		});
		painelConfirmar.add(btnSalvar, "cell 18 12,alignx left,growy");

		JLabel lblNewLabel_1 = new JLabel("Contrato:");
		lblNewLabel_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1, "cell 6 0,alignx left,aligny center");

		lblContratoCarregamento = new JLabel("");
		lblContratoCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblContratoCarregamento.setForeground(Color.WHITE);
		lblContratoCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblContratoCarregamento, "cell 8 0 11 1,grow");

		JLabel lblNewLabel_1_1 = new JLabel("Transportador:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1, "cell 0 2 3 1,alignx right,aligny center");

		lblTransportadorCarregamento = new JLabel("");
		lblTransportadorCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblTransportadorCarregamento.setForeground(Color.WHITE);
		lblTransportadorCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblTransportadorCarregamento, "cell 4 2,grow");

		JLabel lblNewLabel_1_1_1 = new JLabel("Veiculo:");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1, "cell 6 2,alignx right,aligny center");

		lblVeiculoCarregamento = new JLabel("");
		lblVeiculoCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblVeiculoCarregamento.setForeground(Color.WHITE);
		lblVeiculoCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblVeiculoCarregamento, "cell 8 2 3 1,grow");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Produto:");
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1_1, "cell 12 2,alignx left,aligny center");

		lblProdutoCarregamento = new JLabel("");
		lblProdutoCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblProdutoCarregamento.setForeground(Color.WHITE);
		lblProdutoCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblProdutoCarregamento, "cell 14 2 5 1,alignx left,growy");
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Código Romaneio:");
		lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		painelConfirmar.add(lblNewLabel_1_1_1_1_1, "cell 0 3,alignx left,aligny center");
		
		
		 lblCodigoRomaneio = new JLabel("");
		lblCodigoRomaneio.setForeground(Color.WHITE);
		lblCodigoRomaneio.setFont(new Font("Arial", Font.BOLD, 11));
		lblCodigoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCodigoRomaneio, "cell 4 3,grow");
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Peso Romaneio:");
		lblNewLabel_1_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		painelConfirmar.add(lblNewLabel_1_1_1_1_1_1, "cell 6 3 3 1,alignx right,aligny center");
		
		 lblPesoRomaneio = new JLabel("");
		lblPesoRomaneio.setForeground(Color.WHITE);
		lblPesoRomaneio.setFont(new Font("Arial", Font.BOLD, 11));
		lblPesoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblPesoRomaneio, "cell 10 3 7 1,grow");
		
		 lblNotaFiscalInterna = new JTextArea("");
		 lblNotaFiscalInterna.setFont(new Font("Arial", Font.BOLD, 11));
		 lblNotaFiscalInterna.setForeground(Color.BLACK);
		lblNotaFiscalInterna.setWrapStyleWord(true);
		lblNotaFiscalInterna.setLineWrap(true);
		lblNotaFiscalInterna.setEditable(false);
		lblNotaFiscalInterna.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNotaFiscalInterna.setBounds(120, 247, 729, 55);
		
		JLabel lblNewLabel_2 = new JLabel("NF Interna:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 12));
		painelConfirmar.add(lblNewLabel_2, "cell 0 5,alignx right,aligny top");
		
		 lblCaminhoNFInterna = new JLabel("");
		lblCaminhoNFInterna.setForeground(Color.WHITE);
		lblCaminhoNFInterna.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFInterna, "cell 2 6 17 1,grow");
		
		JLabel lblNewLabel_2_1 = new JLabel("NF Venda 1:");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		painelConfirmar.add(lblNewLabel_2_1, "cell 0 7,alignx right,aligny top");
		
		
		
		
		 lblNotaFiscalVenda1 = new JTextArea("");
		 lblNotaFiscalVenda1.setFont(new Font("Arial", Font.BOLD, 11));
		 lblNotaFiscalVenda1.setForeground(Color.BLACK);
		lblNotaFiscalVenda1.setWrapStyleWord(true);
		lblNotaFiscalVenda1.setLineWrap(true);
		lblNotaFiscalVenda1.setEditable(false);
		lblNotaFiscalVenda1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNotaFiscalVenda1.setBounds(120, 335, 729, 55);
		
		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setForeground(Color.WHITE);
		painelConfirmar.add(lblVendedor, "cell 12 1 3 1,alignx left,aligny center");
		
		lblVendedorCarregamento = new JLabel("");
		lblVendedorCarregamento.setFont(new Font("Arial", Font.BOLD, 11));
		lblVendedorCarregamento.setForeground(Color.WHITE);
		lblVendedorCarregamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblVendedorCarregamento, "cell 16 1 3 1,grow");
		
		 lblCaminhoNFVenda1 = new JLabel("");
		lblCaminhoNFVenda1.setForeground(Color.WHITE);
		lblCaminhoNFVenda1.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFVenda1, "cell 4 8 15 1,grow");
		
		JLabel lblNewLabel_2_1_1 = new JLabel("NF Complemento:");
		lblNewLabel_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		painelConfirmar.add(lblNewLabel_2_1_1, "cell 0 9,alignx left,aligny top");
		
		 lblNotaFiscalComplemento = new JTextArea("");
		 lblNotaFiscalComplemento.setFont(new Font("Arial", Font.BOLD, 11));
		 lblNotaFiscalComplemento.setForeground(Color.BLACK);
		lblNotaFiscalComplemento.setWrapStyleWord(true);
		lblNotaFiscalComplemento.setLineWrap(true);
		lblNotaFiscalComplemento.setEditable(false);
		lblNotaFiscalComplemento.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNotaFiscalComplemento.setBounds(122, 432, 727, 55);

		
		 lblCaminhoNFComplemento = new JLabel("");
		lblCaminhoNFComplemento.setForeground(Color.WHITE);
		lblCaminhoNFComplemento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFComplemento, "cell 4 10 15 1,grow");
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Obs:");
		lblNewLabel_2_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		painelConfirmar.add(lblNewLabel_2_1_1_1, "cell 0 11,alignx right,aligny top");
		
		 lblObs = new JTextArea("");
		 lblObs.setFont(new Font("Arial", Font.BOLD, 11));
		 lblObs.setForeground(Color.BLACK);
		lblObs.setWrapStyleWord(true);
		lblObs.setLineWrap(true);
		lblObs.setEditable(false);
		lblObs.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblObs, "cell 4 11 15 1,grow");
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
	               boolean atualizou = gerenciar.atualizar_carregamento(getCarregamentoAtualizar(carregamento_global));
	               
	               if(atualizou) {
	            	   JOptionPane.showMessageDialog(isto, "Carregamento Atualizado");
	            	   
	            	   ((TelaGerenciarContrato) telaPaiJFrame).pesquisar_recebimentos(true);

	            	   ((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos(true);
	            	   isto.dispose();
	               }else {
	            	   JOptionPane.showMessageDialog(isto, "Erro ao atualizar o carregamento\nConsulte o Administrador");
	            	   isto.dispose();

	               }
			}
		});
		painelConfirmar.add(btnAtualizar, "cell 14 12 3 1,grow");
		
		JScrollPane scrollPaneNFInterna = new JScrollPane(lblNotaFiscalInterna);
		painelConfirmar.add(scrollPaneNFInterna, "cell 4 5 15 1,grow");
		
		JScrollPane scrollPaneNFVenda1 = new JScrollPane(lblNotaFiscalVenda1);
		painelConfirmar.add(scrollPaneNFVenda1, "cell 4 7 15 1,grow");
		
		JScrollPane scrollPaneNFComplemento = new JScrollPane(lblNotaFiscalComplemento);
		painelConfirmar.add(scrollPaneNFComplemento, "cell 4 9 15 1,grow");
		
		 lblCaminhoRomaneio = new JLabel("");
		lblCaminhoRomaneio.setForeground(Color.WHITE);
		lblCaminhoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoRomaneio, "cell 2 4 17 1,grow");
		
			
		if(flag_modo_tela == 0) {
			//esconde o botao artualizar
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}else if(flag_modo_tela == 1) {
			//esconde o botaos salvar
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			
			//chama as rotinas de edicao
			rotinasEdicao();
		}
	    //this.setUndecorated(true);
		this.setLocationRelativeTo(janela_pai);

	}

	

	public void setClienteCarregamento(CadastroCliente cliente) {

		this.cliente_carregamento = cliente;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBCliente.removeAllItems();
				cBCliente.repaint();
				cBCliente.updateUI();

				if (cliente.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBCliente.addItem(cliente.getNome_empresarial());

				} else {
					cBCliente.addItem(cliente.getNome_fantaia());

				}

				cBCliente.repaint();
				cBCliente.updateUI();

			}
		});
	}

	public void setContratoCarregamento(CadastroContrato contrato) {

		this.contrato_carregamento = contrato;

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBContrato.removeAllItems();
				cBContrato.repaint();
				cBContrato.updateUI();
				cBContrato.addItem(contrato.getCodigo());

				cBContrato.repaint();
				cBContrato.updateUI();

			}
		});
	}

	public void setProduto(CadastroProduto prod) {

		this.produto_carregamento = prod;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBProduto.removeAllItems();
				cBProduto.repaint();
				cBProduto.updateUI();
				cBProduto.addItem(prod.getNome_produto());

				cBProduto.repaint();
				cBProduto.updateUI();

			}
		});

	}

	public void setTransportador(CadastroCliente _transportador) {

		this.transportador_carregamento = _transportador;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBTransportador.removeAll();
				cBTransportador.removeAllItems();
				cBTransportador.repaint();
				cBTransportador.updateUI();

				if(_transportador.getTipo_pessoa() == 0) {
					//pessoa fisica
					cBTransportador.addItem(_transportador.getNome_empresarial().toUpperCase().trim());

				}else {
					cBTransportador.addItem(_transportador.getNome_fantaia().toUpperCase().trim());

				}

				cBTransportador.repaint();
				cBTransportador.updateUI();

				cbVeiculo.removeAllItems();

				for (CadastroCliente.Veiculo veiculo : _transportador.getVeiculos()) {
					cbVeiculo.addItem(veiculo.getId_veiculo() + "-" + veiculo.getPlaca_trator());
				}

				cbVeiculo.repaint();
				cbVeiculo.updateUI();

				transportador_carregamento = _transportador;
			}
		});

	}

	public void atualizarDadosConfirmar() {
		
		
		lblDataCarregamento.setText(entDataCarregamento.getText());
		String nome_cliente;
		String nome_vendedor;
		if (cliente_carregamento.getTipo_pessoa() == 0) {
			// pessoa fisica
			nome_cliente = cliente_carregamento.getNome_empresarial();
		} else {
			nome_cliente = cliente_carregamento.getNome_fantaia();
		}
		lblCompradorCarregamento.setText(nome_cliente);

		lblContratoCarregamento.setText(contrato_local.getCodigo());
		
		if (vendedor.getTipo_pessoa() == 0) {
			// pessoa fisica
			nome_vendedor = vendedor.getNome_empresarial();
		} else {
			nome_vendedor = vendedor.getNome_fantaia();
		}
		
		lblVendedorCarregamento.setText(nome_vendedor);
		
		if(transportador_carregamento != null) {
		if(transportador_carregamento.getTipo_pessoa() == 0) {
		lblTransportadorCarregamento
				.setText(transportador_carregamento.getNome_empresarial());
		}else {
			lblTransportadorCarregamento
			.setText(transportador_carregamento.getNome_fantaia());
		}
		
		String s_veiculo = cbVeiculo.getSelectedItem().toString();
		String separados[] = s_veiculo.split("-");
		int id_veiculo = Integer.parseInt(separados[0]);
		
		for(CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
			if(veiculo.getId_veiculo() == id_veiculo) {
				veiculo_carregamento = veiculo;
				lblVeiculoCarregamento.setText(veiculo_carregamento.getId_veiculo() + "-" + veiculo_carregamento.getPlaca_trator());
				break;
			}
			
		}
		
		}
		
		NumberFormat z = NumberFormat.getNumberInstance();

	
		
		lblProdutoCarregamento.setText(produto_carregamento.getNome_produto());
		
		if(romaneio_carregamento != null) {
			lblCodigoRomaneio.setText(romaneio_carregamento.getNumero_romaneio() + "");
			lblPesoRomaneio.setText(z.format(romaneio_carregamento.getPeso_liquido()));
			
			String caminho_completo = romaneio_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
			lblCaminhoRomaneio.setText(caminho_completo_normalizado);
		}else {
			if(entRomaneio.getText() != null)
			lblCodigoRomaneio.setText(entRomaneio.getText());
			if(pesoRomaneio.getText() != null && !pesoRomaneio.getText().equals("") && !pesoRomaneio.getText().equals(" "))
			lblPesoRomaneio.setText(z.format(Double.parseDouble(pesoRomaneio.getText())));
		}
		
		if(!chckBoxNFInternaNaoAplicavel.isSelected()) {
		if(nota_fiscal_interna_carregamento != null) {
			
			String codigo = nota_fiscal_interna_carregamento.getNfe();
			String peso_nf = nota_fiscal_interna_carregamento.getQuantidade();
			String destinatario = nota_fiscal_interna_carregamento.getNome_destinatario();
			String remetente = nota_fiscal_interna_carregamento.getNome_remetente();
			String natureza = nota_fiscal_interna_carregamento.getNatureza();
			String data = nota_fiscal_interna_carregamento.getData().toString();
			String produto = nota_fiscal_interna_carregamento.getProduto();
			String valor = nota_fiscal_interna_carregamento.getValor();
			
			String texto_revisao_nfs = "";
			
			texto_revisao_nfs = "NFe: " + codigo +
					"\nRemetente: " + remetente +
					"\nDestinatario: " + destinatario +
					"\nNatureza: " + natureza +
					"\nData: " + data +
					"\nProduto: " + produto +
					"\nPeso: " + peso_nf +
					"\nValor: " + valor;
			
			lblNotaFiscalInterna.setText(texto_revisao_nfs);
			
			String caminho_completo = nota_fiscal_interna_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
			
				lblCaminhoNFInterna.setText(caminho_completo_normalizado);
			
			}else {
				String texto = "Código: ";
				if(checkString(entCodigoNFInterna.getText())){
					texto = texto + entCodigoNFInterna.getText() + " ";
				}else {
					texto = texto + " ";
				}
				texto = texto + "Peso: ";

				if(checkString(entPesoNFInterna.getText())){
					texto = texto + entPesoNFInterna.getText() + " ";
				}else {
					texto = texto + " ";
				}
				
			
				
						
				lblNotaFiscalInterna.setText(texto);
				
			}
		}else {
			lblNotaFiscalInterna.setText("Não Aplicável");
			nota_fiscal_interna_carregamento = null;
		}
		
		if(!chckBoxNFVenda1NaoAplicavel.isSelected()) {
		if(nota_fiscal_venda1_carregamento != null) {
			
		String codigo = nota_fiscal_venda1_carregamento.getNfe();
		String peso_nf = nota_fiscal_venda1_carregamento.getQuantidade();
		String destinatario = nota_fiscal_venda1_carregamento.getNome_destinatario();
		String remetente = nota_fiscal_venda1_carregamento.getNome_remetente();
		String natureza = nota_fiscal_venda1_carregamento.getNatureza();
		String data = nota_fiscal_venda1_carregamento.getData().toString();
		String produto = nota_fiscal_venda1_carregamento.getProduto();
		String valor = nota_fiscal_venda1_carregamento.getValor();
		
		String texto_revisao_nfs = "";
		
		texto_revisao_nfs = "NFe: " + codigo +
				"\nRemetente: " + remetente +
				"\nDestinatario: " + destinatario +
				"\nNatureza: " + natureza +
				"\nData: " + data +
				"\nProduto: " + produto +
				"\nPeso: " + peso_nf +
				"\nValor: " + valor;
		
		lblNotaFiscalVenda1.setText(texto_revisao_nfs);
		
		String caminho_completo = nota_fiscal_venda1_carregamento.getCaminho_arquivo();
		TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
			String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
		
			lblCaminhoNFVenda1.setText(caminho_completo_normalizado);
		
		}else {
			Locale ptBr = new Locale("pt", "BR");
			
			String texto = "";
			if(checkString(entCodigoNFVenda1.getText()) ) {
				texto = texto + " Código: " + entCodigoNFVenda1.getText() ;
			}else {
				texto = texto + " Código: ";

			}
			
			if(checkString(entPesoNFVenda1.getText())) {
				texto = texto + " Peso: " + entPesoNFVenda1.getText() ;

			}else {
				texto = texto + " Peso: ";

			}
			if(checkString(entValorNFVenda1.getText())) {
				texto = texto + " Valor: " +  NumberFormat.getCurrencyInstance(ptBr)
					.format(new BigDecimal(entValorNFVenda1.getText()));

			}else {
				texto = texto + " Valor: ";
			}
			
			 texto = texto + " Remetente: ";

				if (checkString(entRemetenteNFVenda1.getText())) {
					texto = texto + entRemetenteNFVenda1.getText();
				}else {
					texto = texto + " ";

				}
				 texto = texto + " Destinatário: ";
				 if (checkString(entDestinatarioNFVenda1.getText())) {
						texto = texto + entDestinatarioNFVenda1.getText();
					}else {
						texto = texto + " ";

					}
			
			lblNotaFiscalVenda1.setText(texto);
			
			
		}
		}else {
			lblNotaFiscalVenda1.setText("Não Aplicável");
			nota_fiscal_venda1_carregamento = null;
		}

		
        if(!chckBoxNFComplementoNaoAplicavel.isSelected()) {
		if(nota_fiscal_complemento_carregamento != null) {
			
			String codigo = nota_fiscal_complemento_carregamento.getNfe();
			String peso_nf = nota_fiscal_complemento_carregamento.getQuantidade();
			String destinatario = nota_fiscal_complemento_carregamento.getNome_destinatario();
			String remetente = nota_fiscal_complemento_carregamento.getNome_remetente();
			String natureza = nota_fiscal_complemento_carregamento.getNatureza();
			String data = nota_fiscal_complemento_carregamento.getData().toString();
			String produto = nota_fiscal_complemento_carregamento.getProduto();
			String valor = nota_fiscal_complemento_carregamento.getValor();
			
			String texto_revisao_nfs = "";
			
			texto_revisao_nfs = "NFe: " + codigo +
					"\nRemetente: " + remetente +
					"\nDestinatario: " + destinatario +
					"\nNatureza: " + natureza +
					"\nData: " + data +
					"\nProduto: " + produto +
					"\nPeso: " + peso_nf +
					"\nValor: " + valor;
			
			lblNotaFiscalComplemento.setText(texto_revisao_nfs);
			
			String caminho_completo = nota_fiscal_complemento_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
			
				lblCaminhoNFComplemento.setText(caminho_completo_normalizado);
			
			}else {
				Locale ptBr = new Locale("pt", "BR");
				
				String texto = "";
				if(checkString(entCodigoNFComplemento.getText()) ) {
					texto = texto + " Código: " + entCodigoNFComplemento.getText() ;
				}else {
					texto = texto + " Código: ";

				}
				
				if(checkString(entCodigoNFComplemento.getText())) {
					texto = texto + " Peso: " + entCodigoNFComplemento.getText() ;

				}else {
					texto = texto + " Peso: ";

				}
				if(checkString(entCodigoNFComplemento.getText())) {
					texto = texto + " Valor: " +  NumberFormat.getCurrencyInstance(ptBr)
						.format(new BigDecimal(entCodigoNFComplemento.getText()));

				}else {
					texto = texto + " Valor: ";
				}
				
				 texto = texto + " Remetente: ";

					if (checkString(entRemetenteNFComplemento.getText())) {
						texto = texto + entRemetenteNFComplemento.getText();
					}else {
						texto = texto + " ";

					}
					 texto = texto + " Destinatário: ";
					 if (checkString(entDestinatarioNFComplemento.getText())) {
							texto = texto + entDestinatarioNFComplemento.getText();
						}else {
							texto = texto + " ";

						}
				
				lblNotaFiscalComplemento.setText(texto);
				
			}
        }else {
        	nota_fiscal_complemento_carregamento= null;
				lblNotaFiscalComplemento.setText("Não Aplicável");

			}
		
		
		if(checkString(textAreaObs.getText())) {
			lblObs.setText(textAreaObs.getText());
		}
	

	}
	
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
	

	public void setTelaPai(JDialog dialog) {
		this.telaPai = dialog;
	}
	
	public void setVendedor(CadastroCliente _vendedor) {
		this.vendedor = _vendedor;
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//cBVendedor.removeAll();
				cBVendedor.removeAllItems();
				cBVendedor.repaint();
				cBVendedor.updateUI();
				
				String nome;
				if(vendedor.getTipo_pessoa() == 0) {
					//pessoa fisica
					nome = vendedor.getNome_empresarial();
				}else {
					nome = vendedor.getNome_fantaia();

				}

				cBVendedor.addItem(nome);

				cBVendedor.repaint();
				cBVendedor.updateUI();

				
			}
		});
	}
	
	public void setNotaFiscalVenda1(CadastroNFe _nfe) {
        this.nota_fiscal_venda1_carregamento = _nfe;
        entCodigoNFVenda1.setText(_nfe.getNfe());
			entPesoNFVenda1.setText(_nfe.getQuantidade());
			entValorNFVenda1.setText(_nfe.getValor());
			entRemetenteNFVenda1.setText(_nfe.getNome_remetente());
			entDestinatarioNFVenda1.setText(_nfe.getNome_destinatario());
	}
	
	public void setNotaFiscalComplemento(CadastroNFe _nfe) {
        this.nota_fiscal_complemento_carregamento = _nfe;
        entCodigoNFComplemento.setText(_nfe.getNfe());
			entPesoNFComplemento.setText(_nfe.getQuantidade());
			entValorNFComplemento.setText(_nfe.getValor());
			entRemetenteNFComplemento.setText(_nfe.getNome_remetente());
			entDestinatarioNFComplemento.setText(_nfe.getNome_destinatario());
	}
	
	public void setNotaFiscalInterna(CadastroNFe _nfe) {
        this.nota_fiscal_interna_carregamento = _nfe;
        entCodigoNFInterna.setText(_nfe.getNfe());
			entPesoNFInterna.setText(_nfe.getQuantidade());
	}
	
	
	
public void setRomaneio(CadastroRomaneio romaneio) {
		
		romaneio_carregamento = romaneio;
		NumberFormat z = NumberFormat.getNumberInstance();

		
		entRomaneio.setText(romaneio.getNumero_romaneio() + "");
		pesoRomaneio.setText(z.format(romaneio.getPeso_liquido()));
	}
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}	
	
	
	public void gerarPastasEArquivos() {
		//criar a pasta para o recebimento
		boolean pasta_carregamento_contrato1_existe = false;
		boolean pasta_carregamento_contrato2_existe = false;
		
		String caminho_diretorio1 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato();
		String caminho_diretorio2 = "";
		
		if(contrato_local.getCaminho_diretorio_contrato2() != null) {
			caminho_diretorio2  = servidor_unidade + contrato_local.getCaminho_diretorio_contrato2();
		}else {
			caminho_diretorio2 = null;
		}
				
				

		ManipularTxt manipular = new ManipularTxt();
		//cria o diretorio recebimentos no contrato1
		File diretorio_carregamentos_contrato1 = new File( caminho_diretorio1 + "\\carregamentos");
		File diretorio_carregamentos_contrato2 = null;
		if(!diretorio_carregamentos_contrato1.exists()) {
			manipular.criarDiretorio(diretorio_carregamentos_contrato1.getAbsolutePath());
		}
		
		if(caminho_diretorio2 != null) {
			diretorio_carregamentos_contrato2 = new File( caminho_diretorio2 + "\\carregamentos");
			if(!diretorio_carregamentos_contrato2.exists()) {
				manipular.criarDiretorio(diretorio_carregamentos_contrato2.getAbsolutePath());
			}
		}
		
		//criar diretorio do recebimento na pasta do contrato 1
		File diretorio_este_carregamento_contrato1 = new File( caminho_diretorio1 + "\\carregamentos" + "\\carregamento_" + carregamento_global.getId_carregamento());
		if(!diretorio_este_carregamento_contrato1.exists()) {
			boolean criar = manipular.criarDiretorio(diretorio_este_carregamento_contrato1.getAbsolutePath());
			if(criar) {
				//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
				pasta_carregamento_contrato1_existe = true;

			}else {
				
			}
		}else {
			//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja existe");

			pasta_carregamento_contrato1_existe = true;
		}
		
		File diretorio_este_carregamento_contrato2 = new File( caminho_diretorio2 + "\\carregamentos" + "\\carregamento_" + carregamento_global.getId_carregamento());

		if(caminho_diretorio2 != null) {
			
			if(!diretorio_este_carregamento_contrato2.exists()) {
				boolean criar = manipular.criarDiretorio(diretorio_este_carregamento_contrato2.getAbsolutePath());
				if(criar) {
					pasta_carregamento_contrato2_existe = true;

				}else {
					
				}
			}else {
				pasta_carregamento_contrato2_existe = true;

			}
		}
		

		if(romaneio_carregamento != null) {

			String caminho_completo = romaneio_carregamento.getCaminho_arquivo();
					TratarDados tratar = new TratarDados(caminho_completo);
						String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
						String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
						String conteudo [] = caminho_completo_normalizado.split("\\\\");
						String url_final = "";
						for(String str : conteudo) {
							
							url_final = url_final + str + "\\\\";
						}

				if(pasta_carregamento_contrato1_existe) {
							//copiar a nota para esta pasta
							try {
								
								boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
							    
							} catch (IOException e) {
								//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
								e.printStackTrace();
							}
						}
						if(pasta_carregamento_contrato2_existe) {
							try {
								boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

			}
		
		
		if(nota_fiscal_venda1_carregamento != null) {
			String caminho_completo = nota_fiscal_venda1_carregamento.getCaminho_arquivo();
						TratarDados tratar = new TratarDados(caminho_completo);
							String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
							String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
							String conteudo [] = caminho_completo_normalizado.split("\\\\");
							String url_final = "";
							for(String str : conteudo) {
								
								url_final = url_final + str + "\\\\";
							}

				if(pasta_carregamento_contrato1_existe) {
								//copiar a nota para esta pasta
								try {
									
									boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
								    
								} catch (IOException e) {
									//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
									e.printStackTrace();
								}
							}
							if(pasta_carregamento_contrato2_existe) {
								try {
									boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

			}
		
		
		if(nota_fiscal_complemento_carregamento != null) {
			String caminho_completo = nota_fiscal_complemento_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}


	if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

}
		
		
		if(nota_fiscal_interna_carregamento != null) {
			String caminho_completo = nota_fiscal_interna_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}


if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

}
		
		
	}
	
public CadastroContrato.Carregamento getCarregamentoSalvar(Carregamento carregamento_a_inserir){
		
	

	
	carregamento_a_inserir.setData(lblDataCarregamento.getText());
	carregamento_a_inserir.setId_cliente(cliente_carregamento.getId());
	carregamento_a_inserir.setId_vendedor(vendedor.getId());
	carregamento_a_inserir.setId_contrato(contrato_local.getId());

	
	if(transportador_carregamento != null) {
		carregamento_a_inserir.setId_transportador(transportador_carregamento.getId());
		
		if(veiculo_carregamento != null)
		carregamento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
	}
	
	
	carregamento_a_inserir.setId_produto(produto_carregamento.getId_produto());
	
	if(romaneio_carregamento != null) {
		String caminho_completo = romaneio_carregamento.getCaminho_arquivo();
		TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
			String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
			String conteudo [] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for(String str : conteudo) {
				
				url_final = url_final + str + "\\\\";
			}
			carregamento_a_inserir.setCodigo_romaneio(Integer.toString(romaneio_carregamento.getNumero_romaneio()));
			carregamento_a_inserir.setPeso_romaneio(romaneio_carregamento.getPeso_liquido());
		   
			carregamento_a_inserir.setCaminho_romaneio(url_final);
		
	}else {
		if(checkString(entRomaneio.getText()))   
		carregamento_a_inserir.setCodigo_romaneio((entRomaneio.getText()));
		if(checkString(pesoRomaneio.getText()))   
		carregamento_a_inserir.setPeso_romaneio(Double.parseDouble(pesoRomaneio.getText()));
	}
	
	
      if(!chckBoxNFVenda1NaoAplicavel.isSelected()) {
		if(nota_fiscal_venda1_carregamento != null) {
			String caminho_completo = nota_fiscal_venda1_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				carregamento_a_inserir.setNf_venda1_aplicavel(1);
				carregamento_a_inserir.setCodigo_nf_venda1(nota_fiscal_venda1_carregamento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_venda1_carregamento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				carregamento_a_inserir.setPeso_nf_venda1(peso_nota);
			   
				carregamento_a_inserir.setCaminho_nf_venda1(url_final);
			
				String valor_nota = nota_fiscal_venda1_carregamento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				carregamento_a_inserir.setValor_nf_venda1(new BigDecimal(valor_nota));
				
				//remetente e destinatario
				carregamento_a_inserir.setNome_remetente_nf_venda1(nota_fiscal_venda1_carregamento.getNome_remetente());
				carregamento_a_inserir.setNome_destinatario_nf_venda1(nota_fiscal_venda1_carregamento.getNome_destinatario());
				
		}else {
			carregamento_a_inserir.setNf_venda1_aplicavel(1);

			if(checkString(entCodigoNFVenda1.getText())) {   
			carregamento_a_inserir.setCodigo_nf_venda1(entCodigoNFVenda1.getText());
			}else {
				carregamento_a_inserir.setCodigo_nf_venda1("");

			}
			
			if(checkString(entPesoNFVenda1.getText()))   {
			carregamento_a_inserir.setPeso_nf_venda1(Double.parseDouble(entPesoNFVenda1.getText()));
			}else {
				carregamento_a_inserir.setPeso_nf_venda1(0.0);

			}
			try {
			if(checkString(entValorNFVenda1.getText()))   
				carregamento_a_inserir.setValor_nf_venda1(new BigDecimal(entValorNFVenda1.getText()));
			else {
				carregamento_a_inserir.setValor_nf_venda1(BigDecimal.ZERO);

			}
			}catch(Exception e) {
				
			}
			//remetente e destinatario
			if(checkString(entRemetenteNFVenda1.getText()))   {

				carregamento_a_inserir.setNome_remetente_nf_venda1(entRemetenteNFVenda1.getText());

			}
			if(checkString(entDestinatarioNFVenda1.getText()))   {

				carregamento_a_inserir.setNome_destinatario_nf_venda1(entDestinatarioNFVenda1.getText());

			}		
			
		}
      }else {
			nota_fiscal_venda1_carregamento = null;
			carregamento_a_inserir.setCaminho_nf_venda1("");
			carregamento_a_inserir.setNf_venda1_aplicavel(0);
			carregamento_a_inserir.setValor_nf_venda1(BigDecimal.ZERO);
			carregamento_a_inserir.setPeso_nf_venda1(0.0);
			carregamento_a_inserir.setNome_destinatario_nf_venda1("");
			carregamento_a_inserir.setNome_destinatario_nf_venda1("");
      }
      
      
		if(!chckBoxNFComplementoNaoAplicavel.isSelected()) {
		if(nota_fiscal_complemento_carregamento != null) {
			String caminho_completo = nota_fiscal_complemento_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				carregamento_a_inserir.setNf_complemento_aplicavel(1);

				carregamento_a_inserir.setCodigo_nf_complemento(nota_fiscal_complemento_carregamento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_complemento_carregamento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				carregamento_a_inserir.setPeso_nf_complemento(peso_nota);
			   
				carregamento_a_inserir.setCaminho_nf_complemento(url_final);
			
				String valor_nota = nota_fiscal_complemento_carregamento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				carregamento_a_inserir.setValor_nf_complemento(new BigDecimal(valor_nota));
				
				//remetente e destinatario
				carregamento_a_inserir.setNome_remetente_nf_complemento(nota_fiscal_complemento_carregamento.getNome_remetente());
				carregamento_a_inserir.setNome_destinatario_nf_complemento(nota_fiscal_complemento_carregamento.getNome_destinatario());

		}else {
			carregamento_a_inserir.setNf_complemento_aplicavel(1);

			if(checkString(entCodigoNFComplemento.getText()))   
			carregamento_a_inserir.setCodigo_nf_complemento(entCodigoNFComplemento.getText());
			else {
				carregamento_a_inserir.setCodigo_nf_complemento("");

			}
			if(checkString(entPesoNFComplemento.getText()))   
			carregamento_a_inserir.setPeso_nf_complemento(Double.parseDouble(entPesoNFComplemento.getText()));
			else {
				carregamento_a_inserir.setPeso_nf_complemento(0.0);

			}
			try {
			if(checkString(entValorNFComplemento.getText()))   
				carregamento_a_inserir.setValor_nf_complemento(new BigDecimal(entValorNFComplemento.getText()));
			else {
				carregamento_a_inserir.setValor_nf_complemento(BigDecimal.ZERO);

			}
			}catch(Exception e) {
				
			}
			//remetente e destinatario
			if(checkString(entRemetenteNFComplemento.getText()))   {

				carregamento_a_inserir.setNome_remetente_nf_complemento(entRemetenteNFComplemento.getText());

			}
			if(checkString(entDestinatarioNFComplemento.getText()))   {

				carregamento_a_inserir.setNome_destinatario_nf_complemento(entDestinatarioNFComplemento.getText());

			}
		
		}
		}else {
			nota_fiscal_complemento_carregamento = null;
			carregamento_a_inserir.setNf_complemento_aplicavel(0);
			carregamento_a_inserir.setValor_nf_complemento(BigDecimal.ZERO);
			carregamento_a_inserir.setPeso_nf_complemento(0.0);
			carregamento_a_inserir.setCaminho_nf_complemento("");
			carregamento_a_inserir.setNome_remetente_nf_complemento("");
			carregamento_a_inserir.setNome_destinatario_nf_complemento("");

		}
		
		if(!chckBoxNFInternaNaoAplicavel.isSelected()) {
		if(nota_fiscal_interna_carregamento != null) {
			String caminho_completo = nota_fiscal_interna_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				carregamento_a_inserir.setNf_interna_aplicavel(1);

				carregamento_a_inserir.setCodigo_nf_interna(nota_fiscal_interna_carregamento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_interna_carregamento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				carregamento_a_inserir.setPeso_nf_interna(peso_nota);
			   
				carregamento_a_inserir.setCaminho_nf_interna(url_final);
				
		}else {
			carregamento_a_inserir.setNf_interna_aplicavel(1);

			if(checkString(entCodigoNFInterna.getText()))   
			carregamento_a_inserir.setCodigo_nf_interna(entCodigoNFInterna.getText());
			else {
				carregamento_a_inserir.setCodigo_nf_interna("");

			}
			if(checkString(entPesoNFInterna.getText()))   
			carregamento_a_inserir.setPeso_nf_interna(Double.parseDouble(entPesoNFInterna.getText()));
			else {
				carregamento_a_inserir.setPeso_nf_interna(0.0);

			}
		}
		}else {
			nota_fiscal_interna_carregamento = null;
			carregamento_a_inserir.setNf_interna_aplicavel(0);
			carregamento_a_inserir.setPeso_nf_interna(0.0);
			carregamento_a_inserir.setCaminho_nf_interna("");

		}
		
		carregamento_a_inserir.setObservacao(textAreaObs.getText());
		

		return carregamento_a_inserir;
	}



public CadastroContrato.Carregamento getCarregamentoAtualizar(Carregamento carregamento_a_inserir){
		
	
	//criar a pasta para o recebimento
			boolean pasta_carregamento_contrato1_existe = false;
			boolean pasta_carregamento_contrato2_existe = false;
			
			String caminho_diretorio1 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato();
			String caminho_diretorio2 = "";
			
			if(contrato_local.getCaminho_diretorio_contrato2() != null) {
				caminho_diretorio2  = servidor_unidade + contrato_local.getCaminho_diretorio_contrato2();
			}else {
				caminho_diretorio2 = null;
			}
					
					

			ManipularTxt manipular = new ManipularTxt();
			//cria o diretorio recebimentos no contrato1
			File diretorio_carregamentos_contrato1 = new File( caminho_diretorio1 + "\\carregamentos");
			File diretorio_carregamentos_contrato2 = null;
			if(!diretorio_carregamentos_contrato1.exists()) {
				manipular.criarDiretorio(diretorio_carregamentos_contrato1.getAbsolutePath());
			}
			
			if(caminho_diretorio2 != null) {
				diretorio_carregamentos_contrato2 = new File( caminho_diretorio2 + "\\carregamentos");
				if(!diretorio_carregamentos_contrato2.exists()) {
					manipular.criarDiretorio(diretorio_carregamentos_contrato2.getAbsolutePath());
				}
			}
			
			//criar diretorio do recebimento na pasta do contrato 1
			File diretorio_este_carregamento_contrato1 = new File( caminho_diretorio1 + "\\carregamentos" + "\\carregamento_" + carregamento_global.getId_carregamento());
			if(!diretorio_este_carregamento_contrato1.exists()) {
				boolean criar = manipular.criarDiretorio(diretorio_este_carregamento_contrato1.getAbsolutePath());
				if(criar) {
					//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
					pasta_carregamento_contrato1_existe = true;

				}else {
					
				}
			}else {
				//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja existe");

				pasta_carregamento_contrato1_existe = true;
			}
			
			File diretorio_este_carregamento_contrato2 = new File( caminho_diretorio2 + "\\carregamentos" + "\\carregamento_" + carregamento_global.getId_carregamento());

			if(caminho_diretorio2 != null) {
				
				if(!diretorio_este_carregamento_contrato2.exists()) {
					boolean criar = manipular.criarDiretorio(diretorio_este_carregamento_contrato2.getAbsolutePath());
					if(criar) {
						pasta_carregamento_contrato2_existe = true;

					}else {
						
					}
				}else {
					pasta_carregamento_contrato2_existe = true;

				}
			}
			
	
	
	
	carregamento_a_inserir.setData(lblDataCarregamento.getText());
	carregamento_a_inserir.setId_cliente(cliente_carregamento.getId());
	carregamento_a_inserir.setId_vendedor(vendedor.getId());
	carregamento_a_inserir.setId_contrato(contrato_local.getId());

	
	if(transportador_carregamento != null) {
		carregamento_a_inserir.setId_transportador(transportador_carregamento.getId());
		
		if(veiculo_carregamento != null)
		carregamento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
	}
	
	
	carregamento_a_inserir.setId_produto(produto_carregamento.getId_produto());
	
	if(romaneio_carregamento != null) {
		String caminho_completo = romaneio_carregamento.getCaminho_arquivo();
		TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
			String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
			String conteudo [] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for(String str : conteudo) {
				
				url_final = url_final + str + "\\\\";
			}
			carregamento_a_inserir.setCodigo_romaneio(Integer.toString(romaneio_carregamento.getNumero_romaneio()));
			carregamento_a_inserir.setPeso_romaneio(romaneio_carregamento.getPeso_liquido());
		   
			carregamento_a_inserir.setCaminho_romaneio(url_final);
			if(pasta_carregamento_contrato1_existe) {
				//copiar a nota para esta pasta
				try {
					
					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
				    
				} catch (IOException e) {
					//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
					e.printStackTrace();
				}
			}
			if(pasta_carregamento_contrato2_existe) {
				try {
					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}else {
		if(checkString(entRomaneio.getText()))   
		carregamento_a_inserir.setCodigo_romaneio((entRomaneio.getText()));
		if(checkString(pesoRomaneio.getText()))   
		carregamento_a_inserir.setPeso_romaneio(Double.parseDouble(pesoRomaneio.getText()));
	}

	  if(!chckBoxNFVenda1NaoAplicavel.isSelected()) {
		if(nota_fiscal_venda1_carregamento != null) {
			String caminho_completo = nota_fiscal_venda1_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				carregamento_a_inserir.setNf_venda1_aplicavel(1);

				carregamento_a_inserir.setCodigo_nf_venda1(nota_fiscal_venda1_carregamento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_venda1_carregamento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				carregamento_a_inserir.setPeso_nf_venda1(peso_nota);
			   
				carregamento_a_inserir.setCaminho_nf_venda1(url_final);
				if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String valor_nota = nota_fiscal_venda1_carregamento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				carregamento_a_inserir.setValor_nf_venda1(new BigDecimal(valor_nota));
				//remetente e destinatario
				carregamento_a_inserir.setNome_destinatario_nf_venda1(nota_fiscal_venda1_carregamento.getNome_remetente());
				carregamento_a_inserir.setNome_destinatario_nf_venda1(nota_fiscal_venda1_carregamento.getNome_destinatario());

				
				
		}else {
			carregamento_a_inserir.setNf_venda1_aplicavel(1);

			if(checkString(entCodigoNFVenda1.getText())) {   
			carregamento_a_inserir.setCodigo_nf_venda1(entCodigoNFVenda1.getText());
			}else {
				carregamento_a_inserir.setCodigo_nf_venda1("");

			}
			
			if(checkString(entPesoNFVenda1.getText()))   {
			carregamento_a_inserir.setPeso_nf_venda1(Double.parseDouble(entPesoNFVenda1.getText()));
			}else {
				carregamento_a_inserir.setPeso_nf_venda1(0.0);

			}
			try {
			if(checkString(entValorNFVenda1.getText()))   
				carregamento_a_inserir.setValor_nf_venda1(new BigDecimal(entValorNFVenda1.getText()));
			else {
				carregamento_a_inserir.setValor_nf_venda1(BigDecimal.ZERO);

			}
			}catch(Exception e) {
				
			}
			//remetente e destinatario
			if(checkString(entRemetenteNFVenda1.getText()))   {

				carregamento_a_inserir.setNome_remetente_nf_venda1(entRemetenteNFVenda1.getText());

			}else {
				carregamento_a_inserir.setNome_remetente_nf_venda1("");

			}
			if(checkString(entDestinatarioNFVenda1.getText()))   {

				carregamento_a_inserir.setNome_destinatario_nf_venda1(entDestinatarioNFVenda1.getText());

			}else {
				carregamento_a_inserir.setNome_destinatario_nf_venda1("");

			}
		}
	  }
	  else {
		  nota_fiscal_venda1_carregamento = null;
			carregamento_a_inserir.setNf_venda1_aplicavel(0);
			carregamento_a_inserir.setValor_nf_venda1(BigDecimal.ZERO);
			carregamento_a_inserir.setPeso_nf_venda1(0.0);
			carregamento_a_inserir.setCaminho_nf_venda1("");
			carregamento_a_inserir.setNome_remetente_nf_venda1("");
			carregamento_a_inserir.setNome_destinatario_nf_venda1("");
			

    }
		

		if(!chckBoxNFComplementoNaoAplicavel.isSelected()) {
		if(nota_fiscal_complemento_carregamento != null) {
			String caminho_completo = nota_fiscal_complemento_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				carregamento_a_inserir.setNf_complemento_aplicavel(1);

				carregamento_a_inserir.setCodigo_nf_complemento(nota_fiscal_complemento_carregamento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_complemento_carregamento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				carregamento_a_inserir.setPeso_nf_complemento(peso_nota);
			   
				carregamento_a_inserir.setCaminho_nf_complemento(url_final);
				if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String valor_nota = nota_fiscal_complemento_carregamento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				carregamento_a_inserir.setValor_nf_complemento(new BigDecimal(valor_nota));
				//remetente e destinatario
				carregamento_a_inserir.setNome_remetente_nf_complemento(nota_fiscal_complemento_carregamento.getNome_remetente());
				carregamento_a_inserir.setNome_destinatario_nf_complemento(nota_fiscal_complemento_carregamento.getNome_destinatario());

				
		}else {
			carregamento_a_inserir.setNf_complemento_aplicavel(1);

			if(checkString(entCodigoNFComplemento.getText()))   
			carregamento_a_inserir.setCodigo_nf_complemento(entCodigoNFComplemento.getText());
			else {
				carregamento_a_inserir.setCodigo_nf_complemento("");

			}
			if(checkString(entPesoNFComplemento.getText()))   
			carregamento_a_inserir.setPeso_nf_complemento(Double.parseDouble(entPesoNFComplemento.getText()));
			else {
				carregamento_a_inserir.setPeso_nf_complemento(0.0);

			}
			try {
			if(checkString(entValorNFComplemento.getText()))   
				carregamento_a_inserir.setValor_nf_complemento(new BigDecimal(entValorNFComplemento.getText()));
			else {
				carregamento_a_inserir.setValor_nf_complemento(BigDecimal.ZERO);

			}
			
			
			}catch(Exception e) {
				
			}
			
			//remetente e destinatario
			if(checkString(entRemetenteNFComplemento.getText()))   {

				carregamento_a_inserir.setNome_remetente_nf_complemento(entRemetenteNFComplemento.getText());

			}else {
				carregamento_a_inserir.setNome_remetente_nf_complemento("");

			}
			if(checkString(entDestinatarioNFComplemento.getText()))   {

				carregamento_a_inserir.setNome_destinatario_nf_complemento(entDestinatarioNFComplemento.getText());

			}else {
				carregamento_a_inserir.setNome_destinatario_nf_complemento("");

			}
			
		}
		}else {
			nota_fiscal_complemento_carregamento = null;
			carregamento_a_inserir.setNf_complemento_aplicavel(0);
			carregamento_a_inserir.setValor_nf_complemento(BigDecimal.ZERO);
			carregamento_a_inserir.setPeso_nf_complemento(0.0);
			carregamento_a_inserir.setCaminho_nf_complemento("");
			carregamento_a_inserir.setNome_remetente_nf_complemento("");
			carregamento_a_inserir.setNome_destinatario_nf_complemento("");

		}
		
		if(!chckBoxNFInternaNaoAplicavel.isSelected()) {
		if(nota_fiscal_interna_carregamento != null) {
			String caminho_completo = nota_fiscal_interna_carregamento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				carregamento_a_inserir.setCodigo_nf_interna(nota_fiscal_interna_carregamento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_interna_carregamento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				carregamento_a_inserir.setNf_interna_aplicavel(1);

				carregamento_a_inserir.setPeso_nf_interna(peso_nota);
			   
				carregamento_a_inserir.setCaminho_nf_interna(url_final);
				if(pasta_carregamento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_carregamento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_carregamento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else {
			carregamento_a_inserir.setNf_interna_aplicavel(1);

			if(checkString(entCodigoNFInterna.getText()))   
			carregamento_a_inserir.setCodigo_nf_interna(entCodigoNFInterna.getText());
			else {
				carregamento_a_inserir.setCodigo_nf_interna("");

			}
			if(checkString(entPesoNFInterna.getText()))   
			carregamento_a_inserir.setPeso_nf_interna(Double.parseDouble(entPesoNFInterna.getText()));
			else {
				carregamento_a_inserir.setPeso_nf_interna(0.0);

			}
		}

		}else {
			nota_fiscal_interna_carregamento = null;
			carregamento_a_inserir.setNf_interna_aplicavel(0);
			carregamento_a_inserir.setPeso_nf_interna(0.0);
			carregamento_a_inserir.setCaminho_nf_interna("");

		}
		
		
		carregamento_a_inserir.setObservacao(textAreaObs.getText());
		

		return carregamento_a_inserir;
	}


public void setTransportador(CadastroCliente _transportador, CadastroCliente.Veiculo veiculo) {

	this.transportador_carregamento = _transportador;
	java.awt.EventQueue.invokeLater(new Runnable() {
		public void run() {
			cBTransportador.removeAll();
			cBTransportador.removeAllItems();
			cBTransportador.repaint();
			cBTransportador.updateUI();

			if(_transportador.getTipo_pessoa() == 0) {
				//pessoa fisica
				cBTransportador.addItem(_transportador.getNome_empresarial().toUpperCase().trim());

			}else {
				cBTransportador.addItem(_transportador.getNome_fantaia().toUpperCase().trim());

			}

			cBTransportador.repaint();
			cBTransportador.updateUI();

			cbVeiculo.removeAllItems();

			
			cbVeiculo.addItem(veiculo.getId_veiculo() + "-" + veiculo.getPlaca_trator());
			

			cbVeiculo.repaint();
			cbVeiculo.updateUI();

			transportador_carregamento = _transportador;
		}
	});

}


public void rotinasEdicao() {
	
	chkBoxDataHoje.setSelected(false);
	entDataCarregamento.setEnabled(true);
	entDataCarregamento.setEditable(true);
	
	entDataCarregamento.setText(carregamento_global.getData());
	String servidor_unidade = configs_globais.getServidorUnidade();

	lblDataCarregamento.setText(carregamento_global.getData());
	
	try {
    if(checkString(carregamento_global.getCodigo_romaneio())){
    	//procurar por romaneio
    	if(checkString(carregamento_global.getCaminho_romaneio())){
    		ManipularRomaneios manipular = new ManipularRomaneios("");

        	CadastroRomaneio romaneio = manipular.filtrar(new File(servidor_unidade + carregamento_global.getCaminho_romaneio()));
        	setRomaneio(romaneio);
    	}else {
    		entRomaneio.setText(carregamento_global.getCodigo_romaneio());
			pesoRomaneio.setText(Double.toString(carregamento_global.getPeso_romaneio()));
    	}
    
    }
	}catch(Exception e) {
		//JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
		entRomaneio.setText(carregamento_global.getCodigo_romaneio());
		pesoRomaneio.setText(Double.toString(carregamento_global.getPeso_romaneio()));
	}
	
	GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
	CadastroCliente cliente = gerenciar_clientes.getCliente(carregamento_global.getId_cliente());
	setClienteCarregamento(cliente);
	
	CadastroCliente vendedor = gerenciar_clientes.getCliente(carregamento_global.getId_vendedor());
	setVendedor(vendedor);
	
	try {
	
	if(carregamento_global.getId_transportador() > 0) {
	CadastroCliente transportador = gerenciar_clientes.getCliente(carregamento_global.getId_transportador());
	
	if(carregamento_global.getId_veiculo() > 0) {
		CadastroCliente.Veiculo veiculo = gerenciar_clientes.getVeiculo(carregamento_global.getId_veiculo());
		setTransportador(transportador,veiculo);
	}
	}
	}catch(Exception t) {
		
	}
	
	
	//nf venda 1
	if(carregamento_global.getNf_venda1_aplicavel() == 1) {
		ativarNFVenda1();

	try {
        if(checkString(carregamento_global.getCodigo_nf_venda1())){
        	if(carregamento_global.getCaminho_nf_venda1().length() > 10) {
        		//procurar por nf venda
	        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
	        	CadastroNFe nota_fiscal_venda = manipular.filtrar(new File(servidor_unidade + carregamento_global.getCaminho_nf_venda1()));
	        	setNotaFiscalVenda1(nota_fiscal_venda);
        	}else {
        		 if(checkString(carregamento_global.getCodigo_nf_venda1()))
        				entCodigoNFVenda1.setText(carregamento_global.getCodigo_nf_venda1());
        				 
        				 if(checkString(Double.toString(carregamento_global.getPeso_nf_venda1())))
        				entPesoNFVenda1.setText(Double.toString(carregamento_global.getPeso_nf_venda1()));
        				 
        				 if(checkString(carregamento_global.getValor_nf_venda1().toString()))
        				entValorNFVenda1.setText(carregamento_global.getValor_nf_venda1().toString());
        				 
        				 if(checkString(carregamento_global.getNome_remetente_nf_venda1().toString()))
        					 entRemetenteNFVenda1.setText(carregamento_global.getNome_remetente_nf_venda1().toString());
        				
        				 if(checkString(carregamento_global.getNome_destinatario_nf_venda1().toString()))
        					 entDestinatarioNFVenda1.setText(carregamento_global.getNome_destinatario_nf_venda1().toString());
        			
        	}
        
        	
        }
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
			 if(checkString(carregamento_global.getCodigo_nf_venda1()))
			entCodigoNFVenda1.setText(carregamento_global.getCodigo_nf_venda1());
			 
			 if(checkString(Double.toString(carregamento_global.getPeso_nf_venda1())))
			entPesoNFVenda1.setText(Double.toString(carregamento_global.getPeso_nf_venda1()));
			 
			 if(checkString(carregamento_global.getValor_nf_venda1().toString()))
			entValorNFVenda1.setText(carregamento_global.getValor_nf_venda1().toString());
			 
			 //remente e destinatario
			 
			 if(checkString(carregamento_global.getNome_remetente_nf_venda1()))
				 entRemetenteNFVenda1.setText(carregamento_global.getNome_remetente_nf_venda1());
			
			 if(checkString(carregamento_global.getNome_destinatario_nf_venda1()))
				 entDestinatarioNFVenda1.setText(carregamento_global.getNome_destinatario_nf_venda1());
		}
	}else {
		desativarNFVenda1();
	}
	
	
	//nf interna1
	if(carregamento_global.getNf_interna_aplicavel() == 1) {
		ativarNFInterna();

			try {
		        if(checkString(carregamento_global.getCodigo_nf_interna())){
		        	if(carregamento_global.getCaminho_nf_interna().length() > 10) {
		        		//procurar por nf remessa
			        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
			        	CadastroNFe nota_fiscal_remessa = manipular.filtrar(new File(servidor_unidade + carregamento_global.getCaminho_nf_interna()));
			        	setNotaFiscalInterna(nota_fiscal_remessa);
		        	}else {
						 if(checkString(carregamento_global.getCodigo_nf_interna()))
		        		entCodigoNFInterna.setText(carregamento_global.getCodigo_nf_interna());
		        		
						 if(checkString(Double.toString(carregamento_global.getPeso_nf_interna())))
						entPesoNFInterna.setText(Double.toString(carregamento_global.getPeso_nf_interna()));
		        	}
		        
		        
		        	
		        }
				}catch(Exception e) {
					//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");
					 if(checkString(carregamento_global.getCodigo_nf_interna()))
					entCodigoNFInterna.setText(carregamento_global.getCodigo_nf_interna());
					 else
							entCodigoNFInterna.setText("");

					 
					 if(checkString(Double.toString(carregamento_global.getPeso_nf_interna())))
					entPesoNFInterna.setText(Double.toString(carregamento_global.getPeso_nf_interna()));
					 else
							entPesoNFInterna.setText("0.0");

				}
	}else {
		desativarNFInterna();
	}
	
			//nf complemento
	if(carregamento_global.getNf_complemento_aplicavel() == 1) {
		ativarNFComplemento();

			try {
		        
		        	if(carregamento_global.getCaminho_nf_complemento().length() > 10) {
		        		//procurar por nf remessa
			        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
			        	CadastroNFe nota_fiscal_complemento = manipular.filtrar(new File(servidor_unidade + carregamento_global.getCaminho_nf_complemento()));
			        	setNotaFiscalComplemento(nota_fiscal_complemento);
		        	}else {
						 if(checkString(carregamento_global.getCodigo_nf_complemento()))
		        		entCodigoNFComplemento.setText(carregamento_global.getCodigo_nf_complemento());
						 
						 if(checkString(Double.toString(carregamento_global.getPeso_nf_complemento())))
						entPesoNFComplemento.setText(Double.toString(carregamento_global.getPeso_nf_complemento()));
						 
						 if(checkString(carregamento_global.getValor_nf_complemento().toPlainString()))
						entValorNFComplemento.setText(carregamento_global.getValor_nf_complemento().toPlainString());
		        	
						 if(checkString(carregamento_global.getNome_remetente_nf_complemento()))
							 entRemetenteNFComplemento.setText(carregamento_global.getNome_remetente_nf_complemento());
						
						 if(checkString(carregamento_global.getNome_destinatario_nf_complemento()))
							 entDestinatarioNFComplemento.setText(carregamento_global.getNome_destinatario_nf_complemento());
					
		        	}
		        
		        
		        	
		        
				}catch(Exception e) {
					//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");
					 if(checkString(carregamento_global.getCodigo_nf_complemento()))
					entCodigoNFComplemento.setText(carregamento_global.getCodigo_nf_complemento());
					 
					 if(checkString(Double.toString(carregamento_global.getPeso_nf_complemento())))
					entPesoNFComplemento.setText(Double.toString(carregamento_global.getPeso_nf_complemento()));
					 
					 if(checkString(carregamento_global.getValor_nf_complemento().toPlainString()))
					entValorNFComplemento.setText(carregamento_global.getValor_nf_complemento().toPlainString());

					 if(checkString(carregamento_global.getNome_remetente_nf_complemento()))
						 entRemetenteNFComplemento.setText(carregamento_global.getNome_remetente_nf_complemento());
					
					 if(checkString(carregamento_global.getNome_destinatario_nf_complemento()))
						 entDestinatarioNFComplemento.setText(carregamento_global.getNome_destinatario_nf_complemento());
				
					 
				}
	}else {
		desativarNFComplemento();
	}
	
	
			 if(checkString(carregamento_global.getObservacao())){
				 textAreaObs.setText(carregamento_global.getObservacao());
			 }
			
}

public void desativarNFVenda1() {
	chckBoxNFVenda1NaoAplicavel.setSelected(true);

	 entCodigoNFVenda1.setEnabled(false);
	 entPesoNFVenda1.setEnabled(false);
	 entValorNFVenda1.setEnabled(false);
	 btnLerNfVenda.setEnabled(false);
	 
	 entCodigoNFVenda1.setEditable(false);
	 entPesoNFVenda1.setEditable(false);
	 entValorNFVenda1.setEditable(false);
	 
	 entRemetenteNFVenda1.setEditable(false);
	 entRemetenteNFVenda1.setEnabled(false);
	 
	 entDestinatarioNFVenda1.setEditable(false);
	 entDestinatarioNFVenda1.setEnabled(false);

}

public void ativarNFVenda1() {
	chckBoxNFVenda1NaoAplicavel.setSelected(false);

	 entCodigoNFVenda1.setEnabled(true);
	 entPesoNFVenda1.setEnabled(true);
	 entValorNFVenda1.setEnabled(true);
	 btnLerNfVenda.setEnabled(true);
	 
	 entCodigoNFVenda1.setEditable(true);
	 entPesoNFVenda1.setEditable(true);
	 entValorNFVenda1.setEditable(true);
	 
	 entRemetenteNFVenda1.setEditable(true);
	 entRemetenteNFVenda1.setEnabled(true);
	 
	 entDestinatarioNFVenda1.setEditable(true);
	 entDestinatarioNFVenda1.setEnabled(true);
}


public void desativarNFInterna() {
	chckBoxNFInternaNaoAplicavel.setSelected(true);

	 entCodigoNFInterna.setEnabled(false);
	 entPesoNFInterna.setEnabled(false);
	 btnLerNfInterna.setEnabled(false);

	 

	 entCodigoNFInterna.setEditable(false);
	 entPesoNFInterna.setEditable(false);

}

public void ativarNFInterna() {
	chckBoxNFInternaNaoAplicavel.setSelected(false);

	 entCodigoNFInterna.setEnabled(true);
	 entPesoNFInterna.setEnabled(true);
	 btnLerNfInterna.setEnabled(true);

	 

	 entCodigoNFInterna.setEditable(true);
	 entPesoNFInterna.setEditable(true);

}

public void desativarNFComplemento() {

chckBoxNFComplementoNaoAplicavel.setSelected(true);

 entCodigoNFComplemento.setEnabled(false);
 entPesoNFComplemento.setEnabled(false);
 entValorNFComplemento.setEnabled(false);
 btnLerNfComplemento.setEnabled(false);
 
 entCodigoNFComplemento.setEditable(false);
 entPesoNFComplemento.setEditable(false);
 entValorNFComplemento.setEditable(false);
 
 entRemetenteNFComplemento.setEditable(false);
 entRemetenteNFComplemento.setEnabled(false);
 
 entDestinatarioNFComplemento.setEditable(false);
 entDestinatarioNFComplemento.setEnabled(false);
 
}

public void ativarNFComplemento() {
	chckBoxNFComplementoNaoAplicavel.setSelected(false);

	 entCodigoNFComplemento.setEnabled(true);
	 entPesoNFComplemento.setEnabled(true);
	 entValorNFComplemento.setEnabled(true);
	 btnLerNfComplemento.setEnabled(true);
	 
	 entCodigoNFComplemento.setEditable(true);
	 entPesoNFComplemento.setEditable(true);
	 entValorNFComplemento.setEditable(true);
	 entRemetenteNFComplemento.setEditable(true);
	 entRemetenteNFComplemento.setEnabled(true);
	 
	 entDestinatarioNFComplemento.setEditable(true);
	 entDestinatarioNFComplemento.setEnabled(true);
}



public void getDadosGlobais() {
	//gerenciador de log
			DadosGlobais dados = DadosGlobais.getInstance();
			 configs_globais = dados.getConfigs_globais();
			 servidor_unidade  = configs_globais.getServidorUnidade();
			 //usuario logado
			  login = dados.getLogin();
		
			 
}
}
