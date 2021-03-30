package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
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

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroContrato.Recebimento;
import cadastros.CadastroLogin;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;
import cadastros.CadastroRomaneio;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import manipular.ConfiguracoesGlobais;
import manipular.ManipularNotasFiscais;
import manipular.ManipularRomaneios;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.TratarDados;
import tratamento_proprio.Log;

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
import java.awt.Component;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

public class TelaConfirmarRecebimento extends JDialog {

	private JTabbedPane abas = new JTabbedPane();
	private JPanel painelConfirmar = new JPanel();
	private JPanel painelPaiConfirmar = new JPanel();

	private JPanel painelSelecionar = new JPanel();
	private JFrame telaPaiJFrame;
	private TelaConfirmarRecebimento isto;
	private Window telaPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
    private String servidor_unidade ;
	private JTextField entDataRecebimento;
	private JTextField entCodigoNFVenda;
	private JComboBox cBContrato, cBCliente, cbVeiculo, cBTransportador, cBVendedor;
	private CadastroContrato contrato_local;
    private CadastroRomaneio romaneio_recebimento;
	private CadastroCliente transportador_carregamento;
	private CadastroCliente cliente_recebimento;
	private CadastroProduto produto_carregamento;
	private CadastroNFe nota_fiscal_venda_recebimento;
	private CadastroNFe nota_fiscal_remessa_recebimento;
	private CadastroContrato.Recebimento recebimento_global;

	private JTextArea lblNotaFiscalRemessa;
	private CadastroContrato contrato_carregamento;
	private CadastroCliente.Veiculo veiculo_carregamento;
	private CadastroCliente vendedor;
	private JLabel  lblPesoRomaneio, lblCodigoRomaneio, lblDataRecebimento,
			lblClienteRecebimento, lblContratoRecebimento, lblTransportadorRecebimento, lblVeiculoRecebimento, lblCaminhoNFVenda;
	private JCheckBox chkBoxNFVendaNaoAplicavel, chkBoxNFRemessaNaoAplicavel;

	private JCheckBox chkBoxDataHoje;
	private JTextArea lblNotaFiscalVenda;
	private JTextField entRomaneio;
	private JTextField pesoRomaneio;
	private JTextField entPesoNFVenda;
	private JLabel lblVendedorRecebimento, lblCaminhoNFRemessa, lblCaminhoRomaneio;
	private JTextField entCodigoNFRemessa;
	private JButton btnSelecionarNFVenda,btnSelecionarNFRemessa;
	private JTextField entValorNFVenda;
	private JTextField entPesoNFRemessa;
	private JTextField entValorNFRemessa;
	private JTextField entRemetenteNFVenda;
	private JTextField entDestinatarioNFVenda;
	private JTextField entRemetenteNFRemessa;
	private JTextField entDestinatarioNFRemessa;
	public TelaConfirmarRecebimento(int flag_modo_tela, CadastroContrato.Recebimento recebimento_edicao,  CadastroContrato _contrato_local, Window janela_pai) {
		

		isto = this;
		this.contrato_local = _contrato_local;
		recebimento_global = recebimento_edicao;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(flag_modo_tela == 0) {
			setTitle("E-Contract - Novo Recebimento");

		}else {
			setTitle("E-Contract - Editar Recebimento");

		}
		
		getDadosGlobais();

		abas.setBackground(new Color(255, 255, 255));
		abas.setBorder(new EmptyBorder(5, 5, 5, 5));
		abas = new JTabbedPane();
		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelSelecionar.setBackground(new Color(0, 102, 102));

		abas.addTab("Selecionar", painelSelecionar);
		painelSelecionar.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 153, 153));
		panel_1.setLayout(null);
		panel_1.setBounds(0, 0, 1074, 616);
		painelSelecionar.add(panel_1);

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(82, 71, 46, 23);
		panel_1.add(lblNewLabel_3);

		entDataRecebimento = new JTextField();
		entDataRecebimento.setForeground(Color.BLACK);
		entDataRecebimento.setBackground(Color.WHITE);
		entDataRecebimento.setEnabled(false);

		 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		 entDataRecebimento.setText(strLocalDate2);
		
		entDataRecebimento.setEditable(false);
		entDataRecebimento.setColumns(10);
		entDataRecebimento.setBounds(138, 68, 116, 30);
		panel_1.add(entDataRecebimento);

		 chkBoxDataHoje = new JCheckBox("Data Atual");
		chkBoxDataHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	        	  
				if(chkBoxDataHoje.isSelected()) {
					chkBoxDataHoje.setSelected(true);
					entDataRecebimento.setEditable(false);
					 String strLocalDate2   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
					 entDataRecebimento.setText(strLocalDate2);
					
				}else {
					chkBoxDataHoje.setSelected(false);
					entDataRecebimento.setEnabled(true);
					entDataRecebimento.setEditable(true);
				}
			}
		});
		chkBoxDataHoje.setSelected(true);
		chkBoxDataHoje.setBounds(260, 67, 88, 23);
		panel_1.add(chkBoxDataHoje);

		JLabel lblNewLabel_5 = new JLabel("Transportador:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBounds(59, 222, 88, 14);
		panel_1.add(lblNewLabel_5);

		cBTransportador = new JComboBox();
		cBTransportador.setBounds(138, 214, 248, 30);
		panel_1.add(cBTransportador);

		JButton btnSelecionarTransportador = new JButton("Selecionar");
		btnSelecionarTransportador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores selecionar_transportador = new TelaTransportadores(2,isto);
				selecionar_transportador.setTelaPai(isto);
				selecionar_transportador.setVisible(true);
			}
		});
		btnSelecionarTransportador.setBounds(396, 214, 89, 30);
		panel_1.add(btnSelecionarTransportador);

		cbVeiculo = new JComboBox();
		cbVeiculo.setEditable(false);
		cbVeiculo.setBounds(139, 246, 186, 30);
		panel_1.add(cbVeiculo);

		JLabel lblNewLabel_5_1 = new JLabel("Veiculo:");
		lblNewLabel_5_1.setForeground(Color.WHITE);
		lblNewLabel_5_1.setBounds(94, 253, 43, 16);
		panel_1.add(lblNewLabel_5_1);

		JLabel lblNewLabel_8 = new JLabel("Contrato:");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(79, 114, 56, 14);
		panel_1.add(lblNewLabel_8);

		cBContrato = new JComboBox();
		cBContrato.setEnabled(false);
		cBContrato.setBounds(138, 106, 245, 30);
		panel_1.add(cBContrato);

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
		btnSelecionarContrato.setBounds(393, 104, 89, 30);
		panel_1.add(btnSelecionarContrato);

		JLabel lblNewLabel_8_1 = new JLabel("Cliente:");
		lblNewLabel_8_1.setForeground(Color.WHITE);
		lblNewLabel_8_1.setBounds(95, 154, 42, 16);
		panel_1.add(lblNewLabel_8_1);

		cBCliente = new JComboBox();
		cBCliente.setBounds(139, 147, 244, 30);
		panel_1.add(cBCliente);

		JButton btnSelecionarCliente = new JButton("Selecionar");
		btnSelecionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 16, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		CadastroCliente compradores[] = contrato_local.getCompradores();
		setClienteRecebimento(compradores[0]);
		
		
		btnSelecionarCliente.setBounds(393, 145, 89, 30);
		panel_1.add(btnSelecionarCliente);

		JLabel lblNewLabel_9 = new JLabel("Código NF Venda:");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(41, 365, 100, 16);
		panel_1.add(lblNewLabel_9);

		entCodigoNFVenda = new JTextField();
		entCodigoNFVenda.setBackground(Color.WHITE);
		entCodigoNFVenda.setForeground(Color.BLACK);
		entCodigoNFVenda.setColumns(10);
		entCodigoNFVenda.setBounds(141, 359, 174, 27);
		panel_1.add(entCodigoNFVenda);
		
		CadastroProduto prod = contrato_local.getModelo_produto();
		setProduto(prod);
		
		JLabel lblNewLabel_8_1_1 = new JLabel("Vendedor:");
		lblNewLabel_8_1_1.setForeground(Color.WHITE);
		lblNewLabel_8_1_1.setBounds(81, 190, 56, 14);
		panel_1.add(lblNewLabel_8_1_1);
		
		 cBVendedor = new JComboBox();
		cBVendedor.setBounds(138, 182, 248, 30);
		panel_1.add(cBVendedor);
		
		JButton btnSelecionarVendedor = new JButton("Selecionar");
		btnSelecionarVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 17, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarVendedor.setBounds(396, 180, 89, 30);
		panel_1.add(btnSelecionarVendedor);
		
		JLabel lblNewLabel_9_1 = new JLabel("Código Romaneio:");
		lblNewLabel_9_1.setForeground(Color.WHITE);
		lblNewLabel_9_1.setBounds(31, 295, 106, 14);
		panel_1.add(lblNewLabel_9_1);
		
		entRomaneio = new JTextField();
		entRomaneio.setBackground(Color.WHITE);
		entRomaneio.setForeground(Color.BLACK);
		entRomaneio.setColumns(10);
		entRomaneio.setBounds(139, 288, 174, 27);
		panel_1.add(entRomaneio);
		
		JLabel lblNewLabel_9_1_1 = new JLabel("Peso Romaneio:");
		lblNewLabel_9_1_1.setForeground(Color.WHITE);
		lblNewLabel_9_1_1.setBounds(320, 294, 93, 16);
		panel_1.add(lblNewLabel_9_1_1);
		
		pesoRomaneio = new JTextField();
		pesoRomaneio.setBackground(Color.WHITE);
		pesoRomaneio.setForeground(Color.BLACK);
		pesoRomaneio.setColumns(10);
		pesoRomaneio.setBounds(425, 287, 168, 27);
		panel_1.add(pesoRomaneio);
		
		JLabel lblNewLabel_9_1_1_1 = new JLabel("Peso NF Venda:");
		lblNewLabel_9_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_9_1_1_1.setBounds(325, 365, 93, 16);
		panel_1.add(lblNewLabel_9_1_1_1);
		
		entPesoNFVenda = new JTextField();
		entPesoNFVenda.setBackground(Color.WHITE);
		entPesoNFVenda.setForeground(Color.BLACK);
		entPesoNFVenda.setColumns(10);
		entPesoNFVenda.setBounds(421, 359, 164, 27);
		panel_1.add(entPesoNFVenda);
		
		entCodigoNFRemessa = new JTextField();
		entCodigoNFRemessa.setBackground(Color.WHITE);
		entCodigoNFRemessa.setForeground(Color.BLACK);
		entCodigoNFRemessa.setColumns(10);
		entCodigoNFRemessa.setBounds(140, 464, 174, 27);
		panel_1.add(entCodigoNFRemessa);
		
		JLabel lblNewLabel_9_2 = new JLabel("Código NF Remessa:");
		lblNewLabel_9_2.setForeground(Color.WHITE);
		lblNewLabel_9_2.setBounds(20, 470, 120, 16);
		panel_1.add(lblNewLabel_9_2);
		
		JButton btnLerRomaneio = new JButton("Ler Romaneio");
		btnLerRomaneio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
				
				
				
				String destinatario = "";
				
				if(cliente_recebimento.getTipo_pessoa() == 0) {
					destinatario = cliente_recebimento.getNome_empresarial().trim().toUpperCase();
				}else {
					destinatario = cliente_recebimento.getNome_fantaia().trim().toUpperCase();
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
					telaRomaneio.setDadosPesquisa("", "", "ENTRADA".toUpperCase(), produto, entRomaneio.getText());
					telaRomaneio.setTelaPai(isto);
					telaRomaneio.setVisible(true);
				
				
			}
		});
		btnLerRomaneio.setBounds(603, 287, 107, 28);
		panel_1.add(btnLerRomaneio);
		
	 btnSelecionarNFVenda = new JButton("Ler NF Venda");
		btnSelecionarNFVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String destinatario = "";
				
				if(cliente_recebimento.getTipo_pessoa() == 0) {
					destinatario = cliente_recebimento.getNome_empresarial().trim();
				}else {
					destinatario = cliente_recebimento.getNome_fantaia().trim();
				}
				
					String remetente = "";
				
				if(vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim();
				}else {
					remetente = vendedor.getNome_fantaia().trim();
				}
				
				
				String produto = produto_carregamento.getNome_produto();
				
				
				
				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0,1,isto);
					telaTodasNotasFiscais.setTelaPai(isto);
					telaTodasNotasFiscais.limpar();
					telaTodasNotasFiscais.setRetornoGlobal(1);
					telaTodasNotasFiscais.setDadosPesquisa("", "", "VENDA", produto.replaceAll("NON-GMO", "").replaceAll("GMO", "").replaceAll("Não informar", "").replaceAll(" " , ""), entCodigoNFVenda.getText());
					telaTodasNotasFiscais.habilitarBtnSelecionar();
					telaTodasNotasFiscais.pesquisar_notas();

					telaTodasNotasFiscais.setVisible(true);
				
			}
		});
		btnSelecionarNFVenda.setBounds(863, 359, 103, 28);
		panel_1.add(btnSelecionarNFVenda);
		
		 btnSelecionarNFRemessa = new JButton("Ler NF Remessa");
		btnSelecionarNFRemessa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String destinatario = "";
				
				if(cliente_recebimento.getTipo_pessoa() == 0) {
					destinatario = cliente_recebimento.getNome_empresarial().trim();
				}else {
					destinatario = cliente_recebimento.getNome_fantaia().trim();
				}
				
					String remetente = "";
				
				if(vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim();
				}else {
					remetente = vendedor.getNome_fantaia().trim();
				}
				
				
				String produto = produto_carregamento.getNome_produto();
				
				
				
				
				
				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0,1,isto);
					telaTodasNotasFiscais.setTelaPai(isto);
					telaTodasNotasFiscais.limpar();
					telaTodasNotasFiscais.setRetornoGlobal(2);
					telaTodasNotasFiscais.setDadosPesquisa("LD","", "Remessa", produto, entCodigoNFRemessa.getText());
					telaTodasNotasFiscais.habilitarBtnSelecionar();
					telaTodasNotasFiscais.pesquisar_notas();

					telaTodasNotasFiscais.setVisible(true);
				
				
			}
		});
		btnSelecionarNFRemessa.setBounds(914, 464, 123, 28);
		panel_1.add(btnSelecionarNFRemessa);
		
		
		 chkBoxNFVendaNaoAplicavel = new JCheckBox("Não Aplicável");
		 chkBoxNFVendaNaoAplicavel.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(chkBoxNFVendaNaoAplicavel.isSelected()) {
		 			
		 			desativarNFVenda();
		 			
		 	
		 		}else {
		 			ativarNFVenda();

		 			


		 		}
		 	}
		 });
		chkBoxNFVendaNaoAplicavel.setForeground(Color.WHITE);
		chkBoxNFVendaNaoAplicavel.setBounds(141, 327, 104, 18);
		panel_1.add(chkBoxNFVendaNaoAplicavel);
		
		 chkBoxNFRemessaNaoAplicavel = new JCheckBox("Não Aplicável");
		 chkBoxNFRemessaNaoAplicavel.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(chkBoxNFRemessaNaoAplicavel.isSelected()) {
		 			desativarNFRemessa();
		 			
		 		}else {
		 			ativarNFRemessa();

		 		}
		 	}
		 });
		chkBoxNFRemessaNaoAplicavel.setForeground(Color.WHITE);
		chkBoxNFRemessaNaoAplicavel.setBounds(141, 437, 104, 18);
		panel_1.add(chkBoxNFRemessaNaoAplicavel);
		
		JLabel lblNewLabel_9_1_1_2 = new JLabel("Valor NF Venda:");
		lblNewLabel_9_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_9_1_1_2.setBounds(595, 365, 93, 16);
		panel_1.add(lblNewLabel_9_1_1_2);
		
		entValorNFVenda = new JTextField();
		entValorNFVenda.setForeground(Color.BLACK);
		entValorNFVenda.setColumns(10);
		entValorNFVenda.setBackground(Color.WHITE);
		entValorNFVenda.setBounds(686, 359, 168, 27);
		panel_1.add(entValorNFVenda);
		
		JLabel lblNewLabel_9_1_1_1_1 = new JLabel("Peso NF Remessa:");
		lblNewLabel_9_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_9_1_1_1_1.setBounds(328, 470, 109, 16);
		panel_1.add(lblNewLabel_9_1_1_1_1);
		
		entPesoNFRemessa = new JTextField();
		entPesoNFRemessa.setForeground(Color.BLACK);
		entPesoNFRemessa.setColumns(10);
		entPesoNFRemessa.setBackground(Color.WHITE);
		entPesoNFRemessa.setBounds(449, 464, 164, 27);
		panel_1.add(entPesoNFRemessa);
		
		JLabel lblNewLabel_9_1_1_2_1 = new JLabel("Valor NF Remessa:");
		lblNewLabel_9_1_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_9_1_1_2_1.setBounds(623, 470, 108, 16);
		panel_1.add(lblNewLabel_9_1_1_2_1);
		
		entValorNFRemessa = new JTextField();
		entValorNFRemessa.setForeground(Color.BLACK);
		entValorNFRemessa.setColumns(10);
		entValorNFRemessa.setBackground(Color.WHITE);
		entValorNFRemessa.setBounds(736, 464, 168, 27);
		panel_1.add(entValorNFRemessa);
		
		JLabel lblNewLabel_9_3 = new JLabel("Remetente:");
		lblNewLabel_9_3.setForeground(Color.WHITE);
		lblNewLabel_9_3.setBounds(79, 404, 64, 16);
		panel_1.add(lblNewLabel_9_3);
		
		entRemetenteNFVenda = new JTextField();
		entRemetenteNFVenda.setForeground(Color.BLACK);
		entRemetenteNFVenda.setColumns(10);
		entRemetenteNFVenda.setBackground(Color.WHITE);
		entRemetenteNFVenda.setBounds(141, 398, 174, 27);
		panel_1.add(entRemetenteNFVenda);
		
		entDestinatarioNFVenda = new JTextField();
		entDestinatarioNFVenda.setForeground(Color.BLACK);
		entDestinatarioNFVenda.setColumns(10);
		entDestinatarioNFVenda.setBackground(Color.WHITE);
		entDestinatarioNFVenda.setBounds(425, 398, 174, 27);
		panel_1.add(entDestinatarioNFVenda);
		
		JLabel lblNewLabel_9_3_1 = new JLabel("Destinatário:");
		lblNewLabel_9_3_1.setForeground(Color.WHITE);
		lblNewLabel_9_3_1.setBounds(343, 404, 70, 16);
		panel_1.add(lblNewLabel_9_3_1);
		
		entRemetenteNFRemessa = new JTextField();
		entRemetenteNFRemessa.setForeground(Color.BLACK);
		entRemetenteNFRemessa.setColumns(10);
		entRemetenteNFRemessa.setBackground(Color.WHITE);
		entRemetenteNFRemessa.setBounds(144, 503, 174, 27);
		panel_1.add(entRemetenteNFRemessa);
		
		JLabel lblNewLabel_9_3_2 = new JLabel("Remetente:");
		lblNewLabel_9_3_2.setForeground(Color.WHITE);
		lblNewLabel_9_3_2.setBounds(82, 509, 64, 16);
		panel_1.add(lblNewLabel_9_3_2);
		
		JLabel lblNewLabel_9_3_1_1 = new JLabel("Destinatário:");
		lblNewLabel_9_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_9_3_1_1.setBounds(346, 509, 70, 16);
		panel_1.add(lblNewLabel_9_3_1_1);
		
		entDestinatarioNFRemessa = new JTextField();
		entDestinatarioNFRemessa.setForeground(Color.BLACK);
		entDestinatarioNFRemessa.setColumns(10);
		entDestinatarioNFRemessa.setBackground(Color.WHITE);
		entDestinatarioNFRemessa.setBounds(428, 503, 174, 27);
		panel_1.add(entDestinatarioNFRemessa);
		
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		setVendedor(vendedores[0]);
		getContentPane().setLayout(new MigLayout("", "[1079.00px]", "[653.00px]"));

		getContentPane().add(abas, "cell 0 0,grow");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1104, 699);
		painelConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				atualizarDadosConfirmar();
			}
		});

		painelConfirmar.setBackground(new Color(0, 153, 153));
		painelPaiConfirmar.setBackground(new Color(47, 79, 79));

		// adiciona novos paines e suas abas
		abas.addTab("Confirmar", painelPaiConfirmar);
		
		painelPaiConfirmar.add(painelConfirmar);
		painelConfirmar.setLayout(new MigLayout("", "[105px][186px][18px][99px][13px][65px][20px][44px][20px][98px]", "[41px][47px][44px][30px][41px][52px][26px][100px][20px][112px][17px][28px]"));

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel, "cell 0 0,alignx right,aligny bottom");

		lblDataRecebimento = new JLabel("");
		lblDataRecebimento.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblDataRecebimento.setForeground(Color.WHITE);
		lblDataRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblDataRecebimento, "cell 1 0,grow");

		lblClienteRecebimento = new JLabel("");
		lblClienteRecebimento.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblClienteRecebimento.setForeground(Color.WHITE);
		lblClienteRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblClienteRecebimento, "cell 1 1 9 1,grow");

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblCliente.setForeground(Color.WHITE);
		painelConfirmar.add(lblCliente, "cell 0 1,alignx right,aligny center");

		JButton btnSalvar = new JButton("Confirmar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
                Recebimento recebimento = getRecebimentoSalvar(new CadastroContrato.Recebimento());
				
                
                CadastroContrato.Recebimento duplicado = gerenciar.procurarDuplicataRecebimento(recebimento.getCodigo_romaneio());
                if(duplicado != null) {
                	JOptionPane.showMessageDialog(isto,"Já Existe um recebimento associado a este código de romaneio!");

                	if (JOptionPane.showConfirmDialog(isto, 
				            "Abrir", "Deseja ver o recebimento?", 
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						 
                		CadastroContrato contrato_selecionado = new GerenciarBancoContratos().getContrato(duplicado.getId_contrato_recebimento());
        				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado, isto);
        				gerenciar_contrato.setTelaRecebimentos(duplicado.getId_recebimento());

                		
                	}
			   }else {
                
				int retorno = gerenciar.inserirRecebimento(contrato_local.getId(),recebimento);
				if (retorno > 0) {
					JOptionPane.showMessageDialog(isto, "Recebimento Cadastrado!");
					//((TelaGerenciarContrato) telaPai).pesquisar_carregamentos();
					((TelaGerenciarContrato) telaPaiJFrame).pesquisar_recebimentos(true);
	            	  ((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos(true);

					recebimento.setId_recebimento(retorno);
				     recebimento_global =recebimento;
					gerarPastasEArquivos();
					isto.dispose();

				} else {
					JOptionPane.showMessageDialog(isto,
							"Erro ao inserir o recebimento\nConsulte o administrador do sistema!");
					isto.dispose();
				}
                }
			}
		});
		painelConfirmar.add(btnSalvar, "cell 9 11,alignx left,growy");

		JLabel lblNewLabel_1 = new JLabel("Contrato:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1, "cell 0 3,alignx right,aligny center");

		lblContratoRecebimento = new JLabel("");
		lblContratoRecebimento.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblContratoRecebimento.setForeground(Color.WHITE);
		lblContratoRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblContratoRecebimento, "cell 1 3 9 1,grow");

		JLabel lblNewLabel_1_1 = new JLabel("Transportador:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1, "cell 0 4,alignx right,aligny center");

		lblTransportadorRecebimento = new JLabel("");
		lblTransportadorRecebimento.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblTransportadorRecebimento.setForeground(Color.WHITE);
		lblTransportadorRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblTransportadorRecebimento, "cell 1 4 3 1,grow");

		JLabel lblNewLabel_1_1_1 = new JLabel("Veiculo:");
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1, "cell 5 4,alignx right,aligny center");

		lblVeiculoRecebimento = new JLabel("");
		lblVeiculoRecebimento.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblVeiculoRecebimento.setForeground(Color.WHITE);
		lblVeiculoRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblVeiculoRecebimento, "cell 7 4 3 1,grow");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Código Romaneio:");
		lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1_1, "cell 0 5,growx,aligny center");

		lblCodigoRomaneio = new JLabel("");
		lblCodigoRomaneio.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblCodigoRomaneio.setForeground(Color.WHITE);
		lblCodigoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCodigoRomaneio, "cell 1 5,grow");

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Peso Romaneio:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1_1_1, "cell 3 5,alignx left,aligny center");

		lblPesoRomaneio = new JLabel("");
		lblPesoRomaneio.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblPesoRomaneio.setForeground(Color.WHITE);
		lblPesoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblPesoRomaneio, "cell 5 5 5 1,grow");

		JLabel lblNewLabel_2 = new JLabel("NF Venda:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_2.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_2, "cell 0 7,alignx right,aligny top");
		
		lblNotaFiscalVenda = new JTextArea("");
		lblNotaFiscalVenda.setEditable(false);
		lblNotaFiscalVenda.setLineWrap(true);
		lblNotaFiscalVenda.setWrapStyleWord(true);
		lblNotaFiscalVenda.setBorder(new LineBorder(new Color(0, 0, 0)));

		
		JScrollPane scrollPane = new JScrollPane(lblNotaFiscalVenda);
		painelConfirmar.add(scrollPane, "cell 1 7 9 1,grow");
		
		 lblCaminhoNFVenda = new JLabel("");
		 lblCaminhoNFVenda.setForeground(Color.WHITE);
		 lblCaminhoNFVenda.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFVenda, "cell 1 8 9 1,grow");
		
		JLabel lblNewLabel_2_1 = new JLabel("NF Remessa:");
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblNewLabel_2_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_2_1, "cell 0 9,alignx right,aligny center");
		
	
		
		 lblNotaFiscalRemessa = new JTextArea("");
		 lblNotaFiscalRemessa.setEditable(false);
		lblNotaFiscalRemessa.setWrapStyleWord(true);
		lblNotaFiscalRemessa.setLineWrap(true);
		lblNotaFiscalRemessa.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPaneNFRemessa = new JScrollPane(lblNotaFiscalRemessa);
		painelConfirmar.add(scrollPaneNFRemessa, "cell 1 9 9 1,grow");
		
		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblVendedor.setForeground(Color.WHITE);
		painelConfirmar.add(lblVendedor, "cell 0 2,alignx right,aligny center");
		
		 lblVendedorRecebimento = new JLabel("");
		lblVendedorRecebimento.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblVendedorRecebimento.setForeground(Color.WHITE);
		lblVendedorRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblVendedorRecebimento, "cell 1 2 9 1,grow");
		
		 lblCaminhoNFRemessa = new JLabel("");
		lblCaminhoNFRemessa.setForeground(Color.WHITE);
		lblCaminhoNFRemessa.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFRemessa, "cell 1 10 9 1,grow");
		
		
		JButton btnAtualizar = new JButton("Atualizar");
		
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato.Recebimento recebimento = getRecebimentoAtualizar(recebimento_global);
              
				   CadastroContrato.Recebimento duplicado = gerenciar.procurarDuplicataRecebimento(recebimento.getCodigo_romaneio());
	              
				   
				   if(duplicado != null && duplicado.getId_recebimento() != recebimento.getId_recebimento()) {
	                	JOptionPane.showMessageDialog(isto,"Já Existe um recebimento associado a este código de romaneio!");

	                	if (JOptionPane.showConfirmDialog(isto, 
					            "Abrir", "Deseja ver o recebimento?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							 
	                		CadastroContrato contrato_selecionado = new GerenciarBancoContratos().getContrato(duplicado.getId_contrato_recebimento());
	        				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado, isto);
	        				gerenciar_contrato.setTelaRecebimentos(duplicado.getId_recebimento());
	                		
	                	}
				   }else {
				
				boolean atualizou = gerenciar.atualizar_recebimento(recebimento);
               
               if(atualizou) {
            	   JOptionPane.showMessageDialog(isto, "Recebimento Atualizado");
            	   
            	   ((TelaGerenciarContrato) telaPaiJFrame).pesquisar_recebimentos(true);
            	   ((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos(true);

            	   isto.dispose();
               }else {
            	   JOptionPane.showMessageDialog(isto, "Erro ao atualizar o recebimento\nConsulte o Administrador");
            	   isto.dispose();

               }
				
				   }
			}
		});
		painelConfirmar.add(btnAtualizar, "cell 5 11 3 1,alignx right,growy");
		
		lblCaminhoRomaneio  = new JLabel("");
		lblCaminhoRomaneio.setForeground(Color.WHITE);
		lblCaminhoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoRomaneio, "cell 1 6 9 1,grow");
	    //this.setUndecorated(true);
		
		
		if(flag_modo_tela == 0) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}else {
			btnSalvar.setEnabled(false);
			btnSalvar.setVisible(false);
			
			rotinasEdicao();
		}
		
		this.setLocationRelativeTo(janela_pai);

	}

	

	public void setClienteRecebimento(CadastroCliente cliente) {

		this.cliente_recebimento = cliente;

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

	
	public void atualizarDadosConfirmar() {
		lblDataRecebimento.setText(entDataRecebimento.getText());
		String nome_cliente;
		if (cliente_recebimento.getTipo_pessoa() == 0) {
			// pessoa fisica
			nome_cliente = cliente_recebimento.getNome_empresarial().trim().toUpperCase();
		} else {
			nome_cliente = cliente_recebimento.getNome_fantaia().trim().toUpperCase();
		}
		lblClienteRecebimento.setText(nome_cliente);

		lblContratoRecebimento.setText(contrato_local.getCodigo());
		
		String nome_vendedor;
		if(vendedor.getTipo_pessoa() == 0) {
			nome_vendedor = vendedor.getNome_empresarial().trim().toUpperCase();
		}else {
			nome_vendedor = vendedor.getNome_fantaia().trim().toUpperCase();
		}
		
		lblVendedorRecebimento.setText(nome_vendedor);
		
		if(transportador_carregamento != null) {
		if(transportador_carregamento.getTipo_pessoa() == 0) {
		lblTransportadorRecebimento
				.setText(transportador_carregamento.getNome_empresarial());
		}else {
			lblTransportadorRecebimento
			.setText(transportador_carregamento.getNome_fantaia());
		}
		String s_veiculo = cbVeiculo.getSelectedItem().toString();
		String separados[] = s_veiculo.split("-");
		int id_veiculo = Integer.parseInt(separados[0]);
		
		for(CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
			if(veiculo.getId_veiculo() == id_veiculo) {
				veiculo_carregamento = veiculo;
				lblVeiculoRecebimento.setText(veiculo_carregamento.getId_veiculo() + "-" + veiculo_carregamento.getPlaca_trator());
				break;
			}
			
		}
		}
		
		Locale ptBr = new Locale("pt", "BR");

		NumberFormat z = NumberFormat.getNumberInstance();

		if(romaneio_recebimento != null) {
			lblCodigoRomaneio.setText(romaneio_recebimento.getNumero_romaneio() + "");
			lblPesoRomaneio.setText(z.format(romaneio_recebimento.getPeso_liquido()));
			
			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
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
		
		if(!chkBoxNFVendaNaoAplicavel.isSelected()) {
		if(nota_fiscal_venda_recebimento != null) {
			
		String codigo = nota_fiscal_venda_recebimento.getNfe();
		String peso_nf = nota_fiscal_venda_recebimento.getQuantidade();
		String destinatario = nota_fiscal_venda_recebimento.getNome_destinatario();
		String remetente = nota_fiscal_venda_recebimento.getNome_remetente();
		String natureza = nota_fiscal_venda_recebimento.getNatureza();
		String data = nota_fiscal_venda_recebimento.getData().toString();
		String produto = nota_fiscal_venda_recebimento.getProduto();
		String valor = nota_fiscal_venda_recebimento.getValor();
		
		String texto_revisao_nfs = "";
		
		texto_revisao_nfs = "NFe: " + codigo +
				"\nRemetente: " + remetente +
				"\nDestinatario: " + destinatario +
				"\nNatureza: " + natureza +
				"\nData: " + data +
				"\nProduto: " + produto +
				"\nPeso: " + peso_nf +
				"\nValor: " + valor;
		
		lblNotaFiscalVenda.setText(texto_revisao_nfs);
		
		
		String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
		TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
			String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
		
			lblCaminhoNFVenda.setText(caminho_completo_normalizado);
		
		}else {
			String texto = "Código: ";
			if(checkString(entCodigoNFVenda.getText())) {
				texto = texto +  entCodigoNFVenda.getText();
			}else {
				texto = texto + " ";
			}
			 texto = texto + " Peso: ";

			if (checkString(entPesoNFVenda.getText())) {
				texto = texto + z.format(Double.parseDouble(entPesoNFVenda.getText()));
			}else {
				texto = texto + " ";

			}
			 texto = texto + " Valor: ";

			if(checkString(entValorNFVenda.getText())) {
				String valor = entValorNFVenda.getText().replaceAll("[^0-9,]", "").replaceAll(",", ".");
				
				texto = texto + NumberFormat.getCurrencyInstance(ptBr)
				.format(new BigDecimal(valor).doubleValue());
			
			}else {
				texto = texto + " ";

			}
			 texto = texto + " Remetente: ";

			if (checkString(entRemetenteNFVenda.getText())) {
				texto = texto + entRemetenteNFVenda.getText();
			}else {
				texto = texto + " ";

			}
			 texto = texto + " Destinatário: ";
			 if (checkString(entDestinatarioNFVenda.getText())) {
					texto = texto + entDestinatarioNFVenda.getText();
				}else {
					texto = texto + " ";

				}

			lblNotaFiscalVenda.setText(texto);
		

		}
		}else {
			
				lblNotaFiscalVenda.setText("Não Aplicável");
					
		}

		if(!chkBoxNFRemessaNaoAplicavel.isSelected()) {
		if(nota_fiscal_remessa_recebimento != null) {
			String codigo = nota_fiscal_remessa_recebimento.getNfe();
			String peso_nf = nota_fiscal_remessa_recebimento.getQuantidade();
			String destinatario = nota_fiscal_remessa_recebimento.getNome_destinatario();
			String remetente = nota_fiscal_remessa_recebimento.getNome_remetente();
			String natureza = nota_fiscal_remessa_recebimento.getNatureza();
			String data = nota_fiscal_remessa_recebimento.getData().toString();
			String produto = nota_fiscal_remessa_recebimento.getProduto();
			String valor = nota_fiscal_remessa_recebimento.getValor();
			
			String texto_revisao_nfs = "";
			
			texto_revisao_nfs = "NFe: " + codigo +
					"\nRemetente: " + remetente +
					"\nDestinatario: " + destinatario +
					"\nNatureza: " + natureza +
					"\nData: " + data +
					"\nProduto: " + produto +
					"\nPeso: " + peso_nf +
					"\nValor: " + valor
					
					;
			lblNotaFiscalRemessa.setText(texto_revisao_nfs);
			String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf".replace("\"", "\\");
				lblCaminhoNFRemessa.setText(caminho_completo_normalizado);
			
			}else {
				String texto = "Código: ";
				if(checkString(entCodigoNFRemessa.getText())) {
					texto = texto +  entCodigoNFRemessa.getText();
				}else {
					texto = texto + " ";
				}
				 texto = texto + " Peso: ";

				if (checkString(entPesoNFRemessa.getText())) {
					texto = texto + z.format(Double.parseDouble(entPesoNFRemessa.getText()));
				}else {
					texto = texto + " ";

				}
				 texto = texto + " Valor: ";

				 if(checkString(entValorNFRemessa.getText())) {
						String valor = entValorNFRemessa.getText().replaceAll("[^0-9,]", "").replaceAll(",", ".");
						
						texto = texto + NumberFormat.getCurrencyInstance(ptBr)
						.format(new BigDecimal(valor).doubleValue());
					
					}else {
						texto = texto + " ";

					}
					texto = texto + " Remetente: ";

				 if (checkString(entRemetenteNFRemessa.getText())) {
						texto = texto + entRemetenteNFRemessa.getText();
					}else {
						texto = texto + " ";

					}
					 texto = texto + " Destinatário: ";
					 if (checkString(entDestinatarioNFRemessa.getText())) {
							texto = texto + entDestinatarioNFRemessa.getText();
						}else {
							texto = texto + " ";

						}


				lblNotaFiscalRemessa.setText(texto);
			
			}
		}else {
			lblNotaFiscalRemessa.setText("Não Aplicável");

		}

		
	}

	public void setTelaPai(Window dialog) {
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
	
	public void setTelaPai(JFrame tela_pai) {
		this.telaPaiJFrame = tela_pai;
	}	
	
	
	public void setRomaneio(CadastroRomaneio romaneio) {
		
		romaneio_recebimento = romaneio;
		NumberFormat z = NumberFormat.getNumberInstance();

		
		entRomaneio.setText(romaneio.getNumero_romaneio() + "");
		pesoRomaneio.setText(z.format(romaneio.getPeso_liquido()));
	}
	
	public void setNotaFiscalVenda(CadastroNFe _nfe) {
            this.nota_fiscal_venda_recebimento = _nfe;
			entCodigoNFVenda.setText(_nfe.getNfe());
				entPesoNFVenda.setText(_nfe.getQuantidade());
				entValorNFVenda.setText(_nfe.getValor());
				entRemetenteNFVenda.setText(_nfe.getNome_remetente());
				entDestinatarioNFVenda.setText(_nfe.getNome_destinatario());
}
	
	public void setNotaFiscalRemessa(CadastroNFe _nfe) {
	this.nota_fiscal_remessa_recebimento = _nfe;
				entCodigoNFRemessa.setText(_nfe.getNfe());
				entPesoNFRemessa.setText(_nfe.getQuantidade());
				entValorNFRemessa.setText(_nfe.getValor());
				entRemetenteNFRemessa.setText(_nfe.getNome_remetente());
				entDestinatarioNFRemessa.setText(_nfe.getNome_destinatario());
}
	
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ") && txt.length() > 0;
	}
	
	
	public CadastroContrato.Recebimento getRecebimentoAtualizar(CadastroContrato.Recebimento recebimento_a_inserir){
		
		
		//criar a pasta para o recebimento
		boolean pasta_recebimento_contrato1_existe = false;
		boolean pasta_recebimento_contrato2_existe = false;
		
		String caminho_diretorio1 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato();
		String caminho_diretorio2 = "";
		
		if(contrato_local.getCaminho_diretorio_contrato2() != null) {
			caminho_diretorio2  = servidor_unidade + contrato_local.getCaminho_diretorio_contrato2();
		}else {
			caminho_diretorio2 = null;
		}
				
				

		ManipularTxt manipular = new ManipularTxt();
		//cria o diretorio recebimentos no contrato1
		File diretorio_recebimentos_contrato1 = new File( caminho_diretorio1 + "\\recebimentos");
		File diretorio_recebimentos_contrato2 = null;
		if(!diretorio_recebimentos_contrato1.exists()) {
			manipular.criarDiretorio(diretorio_recebimentos_contrato1.getAbsolutePath());
		}
		
		if(caminho_diretorio2 != null) {
			 diretorio_recebimentos_contrato2 = new File( caminho_diretorio2 + "\\recebimentos");
			if(!diretorio_recebimentos_contrato2.exists()) {
				manipular.criarDiretorio(diretorio_recebimentos_contrato2.getAbsolutePath());
			}
		}
		
		//criar diretorio do recebimento na pasta do contrato 1
		File diretorio_este_recebimento_contrato1 = new File( caminho_diretorio1 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());
		if(!diretorio_este_recebimento_contrato1.exists()) {
			boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato1.getAbsolutePath());
			if(criar) {
				//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
				pasta_recebimento_contrato1_existe = true;

			}else {
				
			}
		}else {
			//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja existe");

			pasta_recebimento_contrato1_existe = true;
		}
		
		File diretorio_este_recebimento_contrato2 = new File( caminho_diretorio2 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());

		if(caminho_diretorio2 != null) {
			
			if(!diretorio_este_recebimento_contrato2.exists()) {
				boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato2.getAbsolutePath());
				if(criar) {
					pasta_recebimento_contrato2_existe = true;

				}else {
					
				}
			}else {
				pasta_recebimento_contrato2_existe = true;

			}
		}
		
		
		recebimento_a_inserir.setData_recebimento(lblDataRecebimento.getText());
		recebimento_a_inserir.setId_cliente(cliente_recebimento.getId());
		recebimento_a_inserir.setId_vendedor(vendedor.getId());
		recebimento_a_inserir.setId_contrato_recebimento(contrato_local.getId());
		
		if(transportador_carregamento != null) {
		recebimento_a_inserir.setId_transportador(transportador_carregamento.getId());
		recebimento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
		}
		
		if(!chkBoxNFVendaNaoAplicavel.isSelected()) {
		if(nota_fiscal_venda_recebimento != null) {
			String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				recebimento_a_inserir.setNf_venda_aplicavel(1);

				recebimento_a_inserir.setCodigo_nf_venda(nota_fiscal_venda_recebimento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_venda_recebimento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();

				recebimento_a_inserir.setPeso_nf_venda(peso_nota);

				recebimento_a_inserir.setCaminho_nf_venda(url_final);
				
				String valor_nota = nota_fiscal_venda_recebimento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				recebimento_a_inserir.setValor_nf_venda(new BigDecimal(valor_nota));
				
				//remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_venda(nota_fiscal_venda_recebimento.getNome_remetente());
				recebimento_a_inserir.setNome_destinatario_nf_venda(nota_fiscal_venda_recebimento.getNome_destinatario());

				if(pasta_recebimento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_recebimento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else {
			recebimento_a_inserir.setNf_venda_aplicavel(1);

			recebimento_a_inserir.setCodigo_nf_venda(entCodigoNFVenda.getText());
			try {   
			recebimento_a_inserir.setPeso_nf_venda(Double.parseDouble(entPesoNFVenda.getText()));
			}catch(Exception u) {
				recebimento_a_inserir.setPeso_nf_venda(0);

			}
			try {
				if(checkString(entValorNFVenda.getText()))   {
					String valor_nf_venda = entValorNFVenda.getText().replaceAll("[^0-9,]", "").replaceAll(",", ".");

					recebimento_a_inserir.setValor_nf_venda(new BigDecimal(valor_nf_venda));

				}else {
					recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);

				}
				}catch(Exception e) {
					
				}
			
			//remetente e destinatario
			if(checkString(entRemetenteNFVenda.getText()))   {

				recebimento_a_inserir.setNome_remetente_nf_venda(entRemetenteNFVenda.getText());

			}else {
				recebimento_a_inserir.setNome_remetente_nf_venda("");

			}
			if(checkString(entDestinatarioNFVenda.getText()))   {

				recebimento_a_inserir.setNome_destinatario_nf_venda(entDestinatarioNFVenda.getText());

			}else {
				recebimento_a_inserir.setNome_destinatario_nf_venda("");

			}
		}
		}else {
			recebimento_a_inserir.setNf_venda_aplicavel(0);
			recebimento_a_inserir.setPeso_nf_venda(0);
			recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_remetente_nf_venda("");
			recebimento_a_inserir.setNome_destinatario_nf_venda("");
		}
		
		if(!chkBoxNFRemessaNaoAplicavel.isSelected()) {
		if(nota_fiscal_remessa_recebimento != null) {
			String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				recebimento_a_inserir.setNf_remessa_aplicavel(1);

				recebimento_a_inserir.setCodigo_nf_remessa(nota_fiscal_remessa_recebimento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_remessa_recebimento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				
				recebimento_a_inserir.setPeso_nf_remessa(peso_nota);
			   
				recebimento_a_inserir.setCaminho_nf_remessa(url_final);
				
				String valor_nota = nota_fiscal_remessa_recebimento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				recebimento_a_inserir.setValor_nf_remessa(new BigDecimal(valor_nota));
				
				//remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_remessa(nota_fiscal_remessa_recebimento.getNome_remetente());
				recebimento_a_inserir.setNome_destinatario_nf_remessa(nota_fiscal_remessa_recebimento.getNome_destinatario());

				
				if(pasta_recebimento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_recebimento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else {
			recebimento_a_inserir.setNf_remessa_aplicavel(1);

			recebimento_a_inserir.setCodigo_nf_remessa(entCodigoNFRemessa.getText());
			
			try {   
				recebimento_a_inserir.setPeso_nf_remessa(Double.parseDouble(entPesoNFRemessa.getText()));
				}catch(Exception u) {
					recebimento_a_inserir.setPeso_nf_remessa(0);

				}
			
			   		
			try {
				if(checkString(entValorNFRemessa.getText())) {
					String valor_nf_remessa = entValorNFRemessa.getText().replaceAll("[^0-9,]", "").replaceAll(",", ".");

					recebimento_a_inserir.setValor_nf_remessa(new BigDecimal(valor_nf_remessa));
				}				else {
					recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);

				}
				}catch(Exception e) {
					
				}
			//remetente e destinatario
			if(checkString(entRemetenteNFRemessa.getText()))   {

				recebimento_a_inserir.setNome_remetente_nf_remessa(entRemetenteNFRemessa.getText());

			}else {
				recebimento_a_inserir.setNome_remetente_nf_remessa("");
			}
			if(checkString(entDestinatarioNFRemessa.getText()))   {

				recebimento_a_inserir.setNome_destinatario_nf_remessa(entDestinatarioNFRemessa.getText());

			}else {
				recebimento_a_inserir.setNome_destinatario_nf_remessa("");

			}
		
		}
		}else {
			recebimento_a_inserir.setNf_remessa_aplicavel(0);
			
			recebimento_a_inserir.setPeso_nf_remessa(0);
			recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_remetente_nf_remessa("");

			recebimento_a_inserir.setNome_destinatario_nf_remessa("");

		}
		
		
		if(romaneio_recebimento != null) {
			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				recebimento_a_inserir.setCodigo_romaneio(Integer.toString(romaneio_recebimento.getNumero_romaneio()));
				recebimento_a_inserir.setPeso_romaneio(romaneio_recebimento.getPeso_liquido());
			   
				recebimento_a_inserir.setCaminho_romaneio(url_final);
				if(pasta_recebimento_contrato1_existe) {
					//copiar a nota para esta pasta
					try {
						
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
					    
					} catch (IOException e) {
						//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if(pasta_recebimento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else {
			recebimento_a_inserir.setCodigo_romaneio((entRomaneio.getText()));
			recebimento_a_inserir.setPeso_romaneio(Double.parseDouble(pesoRomaneio.getText()));
		}

		return recebimento_a_inserir;
	}
	
	
	public void rotinasEdicao() {
		
		chkBoxDataHoje.setSelected(false);
		entDataRecebimento.setEnabled(true);
		entDataRecebimento.setEditable(true);
		
		entDataRecebimento.setText(recebimento_global.getData_recebimento());
		String servidor_unidade = configs_globais.getServidorUnidade();

		lblDataRecebimento.setText(recebimento_global.getData_recebimento());
		
		try {
        if(checkString(recebimento_global.getCodigo_romaneio())){
        	//procurar por romaneio
        	if(checkString(recebimento_global.getCaminho_romaneio())){
        		ManipularRomaneios manipular = new ManipularRomaneios("");

            	CadastroRomaneio romaneio = manipular.filtrar(new File(servidor_unidade + recebimento_global.getCaminho_romaneio()));
            	setRomaneio(romaneio);
        	}else {
        		entRomaneio.setText(recebimento_global.getCodigo_romaneio());
    			pesoRomaneio.setText(Double.toString(recebimento_global.getPeso_romaneio()));
        	}
        
        }
		}catch(Exception e) {
			//JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
			entRomaneio.setText(recebimento_global.getCodigo_romaneio());
			pesoRomaneio.setText(Double.toString(recebimento_global.getPeso_romaneio()));
		}
		
		GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
		CadastroCliente cliente = gerenciar_clientes.getCliente(recebimento_global.getId_cliente());
		setClienteRecebimento(cliente);
		
		CadastroCliente vendedor = gerenciar_clientes.getCliente(recebimento_global.getId_vendedor());
		setVendedor(vendedor);
		
		try {
		
		if(recebimento_global.getId_transportador() > 0) {
		CadastroCliente transportador = gerenciar_clientes.getCliente(recebimento_global.getId_transportador());
		
		if(recebimento_global.getId_veiculo() > 0) {
			CadastroCliente.Veiculo veiculo = gerenciar_clientes.getVeiculo(recebimento_global.getId_veiculo());
			setTransportador(transportador,veiculo);
		}
		}
		}catch(Exception t) {
			
		}
		
		
		//nf venda
		if(recebimento_global.getNf_venda_aplicavel() == 1) {
			ativarNFRemessa();

		try {
	        if(checkString(recebimento_global.getCodigo_nf_venda())){
	        	if(recebimento_global.getCaminho_nf_venda().length() > 10) {
	        		//procurar por nf venda
		        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
		        	CadastroNFe nota_fiscal_venda = manipular.filtrar(new File(servidor_unidade + recebimento_global.getCaminho_nf_venda()));
		        	setNotaFiscalVenda(nota_fiscal_venda);
	        	}else {
	        		entCodigoNFVenda.setText(recebimento_global.getCodigo_nf_venda());
					entPesoNFVenda.setText(Double.toString(recebimento_global.getPeso_nf_venda()));
					String valor_nf_venda = recebimento_global.getValor_nf_venda().toString();
					valor_nf_venda = valor_nf_venda.replace(".", ",");
					entValorNFVenda.setText(valor_nf_venda);
					entRemetenteNFVenda.setText(recebimento_global.getNome_remetente_nf_venda());
					entDestinatarioNFVenda.setText(recebimento_global.getNome_destinatario_nf_venda());
	        	}
	        
	        	
	        }
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
				entCodigoNFVenda.setText(recebimento_global.getCodigo_nf_venda());
				entPesoNFVenda.setText(Double.toString(recebimento_global.getPeso_nf_venda()));
				String valor_nf_venda = recebimento_global.getValor_nf_venda().toString();
				valor_nf_venda = valor_nf_venda.replace(".", ",");
				entValorNFVenda.setText(valor_nf_venda);	
				entRemetenteNFVenda.setText(recebimento_global.getNome_remetente_nf_venda());
				entDestinatarioNFVenda.setText(recebimento_global.getNome_destinatario_nf_venda());
				}
		}else {
			desativarNFVenda();
		}
		
		
		//nf remessa
		if(recebimento_global.getNf_remessa_aplicavel() == 1) {
			ativarNFRemessa();

				try {
			        if(checkString(recebimento_global.getCodigo_nf_remessa())){
			        	if(recebimento_global.getCaminho_nf_remessa().length() > 10) {
			        		//procurar por nf remessa
				        	ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
				        	CadastroNFe nota_fiscal_remessa = manipular.filtrar(new File(servidor_unidade + recebimento_global.getCaminho_nf_remessa()));
				        	setNotaFiscalRemessa(nota_fiscal_remessa);
			        	}else {
							entCodigoNFRemessa.setText(recebimento_global.getCodigo_nf_remessa());
							entPesoNFRemessa.setText(Double.toString(recebimento_global.getPeso_nf_remessa()));
							String valor_nf_remessa = recebimento_global.getValor_nf_remessa().toString();
							valor_nf_remessa = valor_nf_remessa.replace(".", ",");
							entValorNFRemessa.setText(valor_nf_remessa);
							entRemetenteNFRemessa.setText(recebimento_global.getNome_remetente_nf_remessa());
							entDestinatarioNFRemessa.setText(recebimento_global.getNome_destinatario_nf_remessa());
			        	}
			        
			        	
			        }
					}catch(Exception e) {
						//JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");
						entCodigoNFRemessa.setText(recebimento_global.getCodigo_nf_remessa());
						entPesoNFRemessa.setText(Double.toString(recebimento_global.getPeso_nf_remessa()));
						String valor_nf_remessa = recebimento_global.getValor_nf_remessa().toString();
						valor_nf_remessa = valor_nf_remessa.replace(".", ",");
						entValorNFRemessa.setText(valor_nf_remessa);
						entRemetenteNFRemessa.setText(recebimento_global.getNome_remetente_nf_remessa());
						entDestinatarioNFRemessa.setText(recebimento_global.getNome_destinatario_nf_remessa());
						}
		}else {
			desativarNFRemessa();

		}
		
		
		
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
				  servidor_unidade = configs_globais.getServidorUnidade();
				  
			
				
	}
	
	public void gerarPastasEArquivos() {
		//criar a pasta para o recebimento
				boolean pasta_recebimento_contrato1_existe = false;
				boolean pasta_recebimento_contrato2_existe = false;
				
				String caminho_diretorio1 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato();
				String caminho_diretorio2 = "";
				
				if(contrato_local.getCaminho_diretorio_contrato2() != null) {
					caminho_diretorio2  = servidor_unidade + contrato_local.getCaminho_diretorio_contrato2();
				}else {
					caminho_diretorio2 = null;
				}
						
						

				ManipularTxt manipular = new ManipularTxt();
				//cria o diretorio recebimentos no contrato1
				File diretorio_recebimentos_contrato1 = new File( caminho_diretorio1 + "\\recebimentos");
				File diretorio_recebimentos_contrato2 = null;
				if(!diretorio_recebimentos_contrato1.exists()) {
					manipular.criarDiretorio(diretorio_recebimentos_contrato1.getAbsolutePath());
				}
				
				if(caminho_diretorio2 != null) {
					 diretorio_recebimentos_contrato2 = new File( caminho_diretorio2 + "\\recebimentos");
					if(!diretorio_recebimentos_contrato2.exists()) {
						manipular.criarDiretorio(diretorio_recebimentos_contrato2.getAbsolutePath());
					}
				}
				
				//criar diretorio do recebimento na pasta do contrato 1
				File diretorio_este_recebimento_contrato1 = new File( caminho_diretorio1 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());
				if(!diretorio_este_recebimento_contrato1.exists()) {
					boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato1.getAbsolutePath());
					if(criar) {
						//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
						pasta_recebimento_contrato1_existe = true;

					}else {
						
					}
				}else {
					//JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja existe");

					pasta_recebimento_contrato1_existe = true;
				}
				
				File diretorio_este_recebimento_contrato2 = new File( caminho_diretorio2 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());

				if(caminho_diretorio2 != null) {
					
					if(!diretorio_este_recebimento_contrato2.exists()) {
						boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato2.getAbsolutePath());
						if(criar) {
							pasta_recebimento_contrato2_existe = true;

						}else {
							
						}
					}else {
						pasta_recebimento_contrato2_existe = true;

					}
				}
				
				if(nota_fiscal_venda_recebimento != null) {
					
					String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
					TratarDados tratar = new TratarDados(caminho_completo);
						String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
						String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
						String conteudo [] = caminho_completo_normalizado.split("\\\\");
						String url_final = "";
						for(String str : conteudo) {
							
							url_final = url_final + str + "\\\\";
						}
						
					if(pasta_recebimento_contrato1_existe) {
						//copiar a nota para esta pasta
						try {
							
							boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
						    
						} catch (IOException e) {
							//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
							e.printStackTrace();
						}
					}
					if(pasta_recebimento_contrato2_existe) {
						try {
							boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				
				
				if(nota_fiscal_remessa_recebimento != null) {

					String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
								TratarDados tratar = new TratarDados(caminho_completo);
									String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
									String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
									String conteudo [] = caminho_completo_normalizado.split("\\\\");
									String url_final = "";
									for(String str : conteudo) {
										
										url_final = url_final + str + "\\\\";
									}

						if(pasta_recebimento_contrato1_existe) {
										//copiar a nota para esta pasta
										try {
											
											boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
										    
										} catch (IOException e) {
											//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
											e.printStackTrace();
										}
									}
									if(pasta_recebimento_contrato2_existe) {
										try {
											boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}

					}
				
				
				if(romaneio_recebimento != null) {

					String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
								TratarDados tratar = new TratarDados(caminho_completo);
									String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
									String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
									String conteudo [] = caminho_completo_normalizado.split("\\\\");
									String url_final = "";
									for(String str : conteudo) {
										
										url_final = url_final + str + "\\\\";
									}

					if(pasta_recebimento_contrato1_existe) {
										//copiar a nota para esta pasta
										try {
											
											boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);
										    
										} catch (IOException e) {
											//JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " + e.getMessage() + "\nCausa: " + e.getCause());
											e.printStackTrace();
										}
									}
									if(pasta_recebimento_contrato2_existe) {
										try {
											boolean copiar = manipular.copiarNFe(servidor_unidade + url_final, diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\" + conteudo[conteudo.length -1]);

										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}

					}
				
				
	}
	
	public CadastroContrato.Recebimento getRecebimentoSalvar(CadastroContrato.Recebimento recebimento_a_inserir){
		
		
		
		
		recebimento_a_inserir.setData_recebimento(lblDataRecebimento.getText());
		recebimento_a_inserir.setId_cliente(cliente_recebimento.getId());
		recebimento_a_inserir.setId_vendedor(vendedor.getId());
		recebimento_a_inserir.setId_contrato_recebimento(contrato_local.getId());
		
		if(transportador_carregamento != null) {
		recebimento_a_inserir.setId_transportador(transportador_carregamento.getId());
		recebimento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
		}
		
		if(!chkBoxNFVendaNaoAplicavel.isSelected()) {
		if(nota_fiscal_venda_recebimento != null) {
			String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				recebimento_a_inserir.setCodigo_nf_venda(nota_fiscal_venda_recebimento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_venda_recebimento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				recebimento_a_inserir.setNf_venda_aplicavel(1);

				recebimento_a_inserir.setPeso_nf_venda(peso_nota);
			   
				recebimento_a_inserir.setCaminho_nf_venda(url_final);
				
				String valor_nota = nota_fiscal_venda_recebimento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				recebimento_a_inserir.setValor_nf_venda(new BigDecimal(valor_nota));
				
				//remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_venda(nota_fiscal_venda_recebimento.getNome_remetente());
				recebimento_a_inserir.setNome_destinatario_nf_venda(nota_fiscal_venda_recebimento.getNome_destinatario());
				
				
		}else {
			recebimento_a_inserir.setNf_venda_aplicavel(1);

			recebimento_a_inserir.setCodigo_nf_venda(entCodigoNFVenda.getText());
			
			try {
			recebimento_a_inserir.setPeso_nf_venda(Double.parseDouble(entPesoNFVenda.getText()));
			}catch(Exception h) {
				recebimento_a_inserir.setPeso_nf_venda(0);

			}
			
			try {
				
				
				
				if(checkString(entValorNFVenda.getText()))   {
					String valor_nf_venda = entValorNFVenda.getText().replace(".", "").replace(",", ".");

					recebimento_a_inserir.setValor_nf_venda(new BigDecimal(valor_nf_venda));
				}
				else {
					recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);

				}
				}catch(Exception e) {
					recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);

				}

			//remetente e destinatario
			if(checkString(entRemetenteNFVenda.getText()))   {

				recebimento_a_inserir.setNome_remetente_nf_venda(entRemetenteNFVenda.getText());

			}
			if(checkString(entDestinatarioNFVenda.getText()))   {

				recebimento_a_inserir.setNome_destinatario_nf_venda(entDestinatarioNFVenda.getText());

			}			
		}
		}else {
			recebimento_a_inserir.setNf_venda_aplicavel(0);
			recebimento_a_inserir.setPeso_nf_venda(0);
			recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_destinatario_nf_venda("");
			recebimento_a_inserir.setNome_destinatario_nf_venda("");
		}
		
		
		if(!chkBoxNFRemessaNaoAplicavel.isSelected()) {
		if(nota_fiscal_remessa_recebimento != null) {
			String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				recebimento_a_inserir.setCodigo_nf_remessa(nota_fiscal_remessa_recebimento.getNfe());
				
				Number number = null;
				NumberFormat z = NumberFormat.getNumberInstance();

				try {
					number = z.parse(nota_fiscal_remessa_recebimento.getQuantidade());
				} catch (ParseException i) {
					// TODO Auto-generated catch block
					i.printStackTrace();
				}
				double peso_nota = number.doubleValue();
				recebimento_a_inserir.setNf_remessa_aplicavel(1);

				recebimento_a_inserir.setPeso_nf_remessa(peso_nota);
			   
				recebimento_a_inserir.setCaminho_nf_remessa(url_final);
				
				String valor_nota = nota_fiscal_remessa_recebimento.getValor().replaceAll("[^0-9,]", "");

				valor_nota = valor_nota.replaceAll(",", ".");

				recebimento_a_inserir.setValor_nf_remessa(new BigDecimal(valor_nota));
				
				//remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_remessa(nota_fiscal_remessa_recebimento.getNome_remetente());
				recebimento_a_inserir.setNome_destinatario_nf_remessa(nota_fiscal_remessa_recebimento.getNome_destinatario());

			
		}else {
			recebimento_a_inserir.setNf_remessa_aplicavel(1);

			recebimento_a_inserir.setCodigo_nf_remessa(entCodigoNFRemessa.getText());
			
			try {
			recebimento_a_inserir.setPeso_nf_remessa(Double.parseDouble(entCodigoNFRemessa.getText()));
			}catch(Exception y) {
				recebimento_a_inserir.setPeso_nf_remessa(0);

			}
			try {
				if(checkString(entValorNFRemessa.getText())) {  
					String valor_nf_remessa = entValorNFRemessa.getText().replace(".", "").replaceAll(",", ".");

					recebimento_a_inserir.setValor_nf_remessa(new BigDecimal(valor_nf_remessa));
				}else {
					recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);

				}
				}catch(Exception e) {
					recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);

				}
			   			
			//remetente e destinatario
			if(checkString(entRemetenteNFRemessa.getText()))   {

				recebimento_a_inserir.setNome_remetente_nf_remessa(entRemetenteNFRemessa.getText());

			}
			if(checkString(entDestinatarioNFRemessa.getText()))   {

				recebimento_a_inserir.setNome_destinatario_nf_remessa(entDestinatarioNFRemessa.getText());

			}
		
		}
		}else {
			recebimento_a_inserir.setNf_remessa_aplicavel(0);
			recebimento_a_inserir.setPeso_nf_remessa(0);
			recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_remetente_nf_remessa("");
			recebimento_a_inserir.setNome_destinatario_nf_remessa("");


		}
		
		
		if(romaneio_recebimento != null) {
			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf")	;
				String caminho_completo_normalizado = "E-Contract" +  caminho_normalizado + "pdf";
				String conteudo [] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for(String str : conteudo) {
					
					url_final = url_final + str + "\\\\";
				}
				recebimento_a_inserir.setCodigo_romaneio(Integer.toString(romaneio_recebimento.getNumero_romaneio()));
				recebimento_a_inserir.setPeso_romaneio(romaneio_recebimento.getPeso_liquido());
			   
				recebimento_a_inserir.setCaminho_romaneio(url_final);
				
		}else {
			recebimento_a_inserir.setCodigo_romaneio((entRomaneio.getText()));
			recebimento_a_inserir.setPeso_romaneio(Double.parseDouble(pesoRomaneio.getText()));
		}

		return recebimento_a_inserir;
	}
	
	public void ativarNFVenda() {
		
		chkBoxNFVendaNaoAplicavel.setSelected(false);

			entCodigoNFVenda.setEnabled(true);
			entCodigoNFVenda.setEditable(true);
			
			entPesoNFVenda.setEnabled(true);
			entPesoNFVenda.setEditable(true);
			
			entValorNFVenda.setEditable(true);
			entValorNFVenda.setEnabled(true);

			entRemetenteNFVenda.setEditable(true);
			entRemetenteNFVenda.setEnabled(true);
			
			entDestinatarioNFVenda.setEditable(true);
			entDestinatarioNFVenda.setEnabled(true);

			btnSelecionarNFVenda.setEnabled(true);
	}
	
	public void desativarNFVenda() {
			
		chkBoxNFVendaNaoAplicavel.setSelected(true);
			entCodigoNFVenda.setEnabled(false);
			entCodigoNFVenda.setEditable(false);
			
			entPesoNFVenda.setEnabled(false);
			entPesoNFVenda.setEditable(false);
			
			entValorNFVenda.setEditable(false);
			entValorNFVenda.setEnabled(false);
			
			entRemetenteNFVenda.setEditable(false);
			entRemetenteNFVenda.setEnabled(false);
			
			entDestinatarioNFVenda.setEditable(false);
			entDestinatarioNFVenda.setEnabled(false);


			btnSelecionarNFVenda.setEnabled(false);
	}
	
	public void ativarNFRemessa() {
		chkBoxNFRemessaNaoAplicavel.setSelected(false);
		entCodigoNFRemessa.setEnabled(true);
		entCodigoNFRemessa.setEditable(true);
		
		entPesoNFRemessa.setEnabled(true);
		entPesoNFRemessa.setEditable(true);
		
		entValorNFRemessa.setEnabled(true);
		entValorNFRemessa.setEditable(true);

		entRemetenteNFRemessa.setEditable(true);
		entRemetenteNFRemessa.setEnabled(true);
		
		entDestinatarioNFRemessa.setEditable(true);
		entDestinatarioNFRemessa.setEnabled(true);
		btnSelecionarNFRemessa.setEnabled(true);
	}
	
	public void desativarNFRemessa() {
		chkBoxNFRemessaNaoAplicavel.setSelected(true);

		entCodigoNFRemessa.setEnabled(false);
		entCodigoNFRemessa.setEditable(false);
		
		entPesoNFRemessa.setEnabled(false);
		entPesoNFRemessa.setEditable(false);
		
		entValorNFRemessa.setEnabled(false);
		entValorNFRemessa.setEditable(false);
		
		entRemetenteNFRemessa.setEditable(false);
		entRemetenteNFRemessa.setEnabled(false);
		
		entDestinatarioNFRemessa.setEditable(false);
		entDestinatarioNFRemessa.setEnabled(false);
		
		btnSelecionarNFRemessa.setEnabled(false);
	}
}
