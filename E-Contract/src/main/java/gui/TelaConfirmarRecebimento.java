package main.java.gui;

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

import main.java.cadastros.CadastroAviso;
import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamento;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
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
import main.java.views_personalizadas.TelaOpcoes;
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
import java.awt.Component;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

public class TelaConfirmarRecebimento extends JDialog {

	private JTabbedPane abas = new JTabbedPane();
	private JPanel painelConfirmar = new JPanel();
	private JPanel painelPaiConfirmar = new JPanel();
	private boolean prosseguir_mesmo_duplicado = false;
	private JPanel painelSelecionar = new JPanel();
	private JFrame telaPaiJFrame;
	private TelaConfirmarRecebimento isto;
	private Window telaPai;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
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
	private Recebimento recebimento_global;

	private JTextArea lblNotaFiscalRemessa;
	private CadastroContrato contrato_carregamento;
	private CadastroCliente.Veiculo veiculo_carregamento;
	private CadastroCliente vendedor;
	private JLabel lblPesoRomaneio, lblCodigoRomaneio, lblDataRecebimento, lblClienteRecebimento,
			lblContratoRecebimento, lblTransportadorRecebimento, lblVeiculoRecebimento, lblCaminhoNFVenda;
	private JCheckBox chkBoxNFVendaNaoAplicavel, chkBoxNFRemessaNaoAplicavel;

	private JCheckBox chkBoxDataHoje;
	private JTextArea lblNotaFiscalVenda;
	private JTextField entRomaneio;
	private JTextField pesoRomaneio;
	private JTextField entPesoNFVenda;
	private JLabel lblVendedorRecebimento, lblCaminhoNFRemessa, lblCaminhoRomaneio;
	private JTextField entCodigoNFRemessa;
	private JButton btnSelecionarNFVenda, btnSelecionarNFRemessa;
	private JTextField entValorNFVenda;
	private JTextField entPesoNFRemessa;
	private JTextField entValorNFRemessa;
	private JTextField entRemetenteNFVenda;
	private JTextField entDestinatarioNFVenda;
	private JTextField entRemetenteNFRemessa;
	private JTextField entDestinatarioNFRemessa;

	public TelaConfirmarRecebimento(int flag_modo_tela, CadastroContrato.Recebimento recebimento_edicao,
			CadastroContrato _contrato_local, Window janela_pai) {

		isto = this;
		this.contrato_local = _contrato_local;
		recebimento_global = recebimento_edicao;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (flag_modo_tela == 0) {
			setTitle("E-Contract - Novo Recebimento");

		} else {
			setTitle("E-Contract - Editar Recebimento");

		}

		getDadosGlobais();

		abas.setBackground(new Color(255, 255, 255));
		abas.setBorder(new EmptyBorder(5, 5, 5, 5));
		abas = new JTabbedPane();
		abas.setBackground(new Color(0, 102, 102));
		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelSelecionar.setBackground(new Color(0, 102, 102));

		abas.addTab("Selecionar", painelSelecionar);
		painelSelecionar.setLayout(new MigLayout("", "[1074px]", "[grow]"));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 102, 102));
		painelSelecionar.add(panel_1, "cell 0 0,alignx center,aligny center");
		panel_1.setLayout(new MigLayout("", "[117px][1px][2px][1px][2px][1px][110px][6px][58px][2px][5px][61px][7px][25px][3px][16px][12px][144px][2px][7px][1px][10px][10px][108px][5px][118px][9px][41px][10px][123px]", "[31px][32px][32px][32px][30px][30px][28px][18px][28px][27px][18px][28px][27px]"));

		JLabel lblNewLabel_3 = new JLabel("Data:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_3.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_3, "cell 0 0 6 1,alignx right,aligny center");

		entDataRecebimento = new JTextField();
		entDataRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		entDataRecebimento.setForeground(Color.BLACK);
		entDataRecebimento.setBackground(Color.WHITE);
		entDataRecebimento.setEnabled(false);

		String strLocalDate2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		entDataRecebimento.setText(strLocalDate2);

		entDataRecebimento.setEditable(false);
		entDataRecebimento.setColumns(10);
		panel_1.add(entDataRecebimento, "cell 6 0,grow");

		chkBoxDataHoje = new JCheckBox("Data Atual");
		chkBoxDataHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxDataHoje.isSelected()) {
					chkBoxDataHoje.setSelected(true);
					entDataRecebimento.setEditable(false);
					String strLocalDate2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
					entDataRecebimento.setText(strLocalDate2);

				} else {
					chkBoxDataHoje.setSelected(false);
					entDataRecebimento.setEnabled(true);
					entDataRecebimento.setEditable(true);
				}
			}
		});
		chkBoxDataHoje.setSelected(true);
		panel_1.add(chkBoxDataHoje, "cell 8 0 4 1,alignx left,aligny top");

		JLabel lblNewLabel_5 = new JLabel("Transportador:");
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_5.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_5, "cell 0 4 6 1,alignx right,aligny center");

		cBTransportador = new JComboBox();
		cBTransportador.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_1.add(cBTransportador, "cell 6 4 6 1,grow");

		JButton btnSelecionarTransportador = new JButton("Selecionar");
		btnSelecionarTransportador.setBackground(new Color(0, 0, 102));
		btnSelecionarTransportador.setForeground(Color.WHITE);
		btnSelecionarTransportador.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarTransportador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTransportadores selecionar_transportador = new TelaTransportadores(2, isto);
				selecionar_transportador.setTelaPai(isto);
				selecionar_transportador.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarTransportador, "cell 13 4 5 1,alignx left,growy");

		cbVeiculo = new JComboBox();
		cbVeiculo.setFont(new Font("Dialog", Font.BOLD, 16));
		cbVeiculo.setEditable(false);
		panel_1.add(cbVeiculo, "cell 6 5 5 1,grow");

		JLabel lblNewLabel_5_1 = new JLabel("Veiculo:");
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_5_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_5_1, "cell 0 5 6 1,alignx right,aligny center");

		JLabel lblNewLabel_8 = new JLabel("Contrato:");
		lblNewLabel_8.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_8.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_8, "cell 0 1 6 1,alignx right,aligny center");

		cBContrato = new JComboBox();
		cBContrato.setFont(new Font("Dialog", Font.BOLD, 16));
		cBContrato.setEnabled(false);
		panel_1.add(cBContrato, "cell 6 1 6 1,grow");

		JButton btnSelecionarContrato = new JButton("Selecionar");
		btnSelecionarContrato.setBackground(new Color(0, 0, 102));
		btnSelecionarContrato.setForeground(Color.WHITE);
		btnSelecionarContrato.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarContrato.setEnabled(false);
		btnSelecionarContrato.setVisible(false);
		btnSelecionarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContratos contrato = new TelaContratos(1, isto);
				contrato.setTelaPai(isto);
				contrato.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarContrato, "cell 13 1 5 1,alignx left,aligny top");

		JLabel lblNewLabel_8_1 = new JLabel("Cliente:");
		lblNewLabel_8_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_8_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_8_1, "cell 0 2 6 1,alignx right,aligny center");

		cBCliente = new JComboBox();
		cBCliente.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_1.add(cBCliente, "cell 6 2 6 1,grow");

		JButton btnSelecionarCliente = new JButton("Selecionar");
		btnSelecionarCliente.setBackground(new Color(0, 0, 102));
		btnSelecionarCliente.setForeground(Color.WHITE);
		btnSelecionarCliente.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 16, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		CadastroCliente compradores[] = contrato_local.getCompradores();
		setClienteRecebimento(compradores[0]);
		panel_1.add(btnSelecionarCliente, "cell 13 2 5 1,alignx left,aligny top");

		JLabel lblNewLabel_9 = new JLabel("Código NF Venda:");
		lblNewLabel_9.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9, "cell 0 8 6 1,alignx right,aligny center");

		entCodigoNFVenda = new JTextField();
		entCodigoNFVenda.setFont(new Font("Dialog", Font.BOLD, 16));
		entCodigoNFVenda.setBackground(Color.WHITE);
		entCodigoNFVenda.setForeground(Color.BLACK);
		entCodigoNFVenda.setColumns(10);
		panel_1.add(entCodigoNFVenda, "cell 6 8 3 1,growx,aligny top");

		CadastroProduto prod = contrato_local.getModelo_produto();
		setProduto(prod);

		JLabel lblNewLabel_8_1_1 = new JLabel("Vendedor:");
		lblNewLabel_8_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_8_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_8_1_1, "cell 0 3 6 1,alignx right,aligny center");

		cBVendedor = new JComboBox();
		cBVendedor.setFont(new Font("Dialog", Font.BOLD, 16));
		panel_1.add(cBVendedor, "cell 6 3 6 1,grow");

		JButton btnSelecionarVendedor = new JButton("Selecionar");
		btnSelecionarVendedor.setBackground(new Color(0, 0, 102));
		btnSelecionarVendedor.setForeground(Color.WHITE);
		btnSelecionarVendedor.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarVendedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0, 17, isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		panel_1.add(btnSelecionarVendedor, "cell 13 3 5 1,alignx left,aligny top");

		JLabel lblNewLabel_9_1 = new JLabel("Código Romaneio:");
		lblNewLabel_9_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1, "cell 0 6 6 1,alignx right,aligny center");

		entRomaneio = new JTextField();
		entRomaneio.setFont(new Font("Dialog", Font.BOLD, 16));
		entRomaneio.setBackground(Color.WHITE);
		entRomaneio.setForeground(Color.BLACK);
		entRomaneio.setColumns(10);
		panel_1.add(entRomaneio, "cell 6 6 3 1,growx,aligny top");

		JLabel lblNewLabel_9_1_1 = new JLabel("Peso Romaneio:");
		lblNewLabel_9_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1, "cell 10 6 7 1,alignx right,aligny center");

		pesoRomaneio = new JTextField();
		pesoRomaneio.setFont(new Font("Dialog", Font.BOLD, 16));
		pesoRomaneio.setBackground(Color.WHITE);
		pesoRomaneio.setForeground(Color.BLACK);
		pesoRomaneio.setColumns(10);
		panel_1.add(pesoRomaneio, "cell 17 6 6 1,growx,aligny top");

		JLabel lblNewLabel_9_1_1_1 = new JLabel("Peso NF Venda:");
		lblNewLabel_9_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_1_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_1, "cell 11 8 6 1,alignx right,aligny center");

		entPesoNFVenda = new JTextField();
		entPesoNFVenda.setFont(new Font("Dialog", Font.BOLD, 16));
		entPesoNFVenda.setBackground(Color.WHITE);
		entPesoNFVenda.setForeground(Color.BLACK);
		entPesoNFVenda.setColumns(10);
		panel_1.add(entPesoNFVenda, "cell 17 8 6 1,growx,aligny top");

		entCodigoNFRemessa = new JTextField();
		entCodigoNFRemessa.setFont(new Font("Dialog", Font.BOLD, 16));
		entCodigoNFRemessa.setBackground(Color.WHITE);
		entCodigoNFRemessa.setForeground(Color.BLACK);
		entCodigoNFRemessa.setColumns(10);
		panel_1.add(entCodigoNFRemessa, "cell 6 11 3 1,growx,aligny top");

		JLabel lblNewLabel_9_2 = new JLabel("Código NF Remessa:");
		lblNewLabel_9_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_2.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_2, "cell 0 11 6 1,alignx right,aligny center");

		JButton btnLerRomaneio = new JButton("Ler Romaneio");
		btnLerRomaneio.setBackground(new Color(0, 0, 102));
		btnLerRomaneio.setForeground(Color.WHITE);
		btnLerRomaneio.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnLerRomaneio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();

				String destinatario = "";

				if (cliente_recebimento.getTipo_pessoa() == 0) {
					destinatario = cliente_recebimento.getNome_empresarial().trim().toUpperCase();
				} else {
					destinatario = cliente_recebimento.getNome_fantaia().trim().toUpperCase();
				}

				String remetente = "";

				if (vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim().toUpperCase();
				} else {
					remetente = vendedor.getNome_fantaia().trim().toUpperCase();
				}

				String produto = produto_carregamento.getNome_produto().toUpperCase();
				TelaRomaneios telaRomaneio;
				telaRomaneio = new TelaRomaneios(0, isto);
				telaRomaneio.setDadosPesquisa("", "", "ENTRADA".toUpperCase(), produto, entRomaneio.getText());
				telaRomaneio.setTelaPai(isto);
				telaRomaneio.setVisible(true);

			}
		});
		panel_1.add(btnLerRomaneio, "cell 23 6,alignx left,aligny top");

		btnSelecionarNFVenda = new JButton("Ler NF Venda");
		btnSelecionarNFVenda.setBackground(new Color(0, 0, 102));
		btnSelecionarNFVenda.setForeground(Color.WHITE);
		btnSelecionarNFVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarNFVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String destinatario = "";

				if (cliente_recebimento.getTipo_pessoa() == 0) {
					destinatario = cliente_recebimento.getNome_empresarial().trim();
				} else {
					destinatario = cliente_recebimento.getNome_fantaia().trim();
				}

				String remetente = "";

				if (vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim();
				} else {
					remetente = vendedor.getNome_fantaia().trim();
				}

				String produto = produto_carregamento.getNome_produto();

				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0, 1, isto);
				telaTodasNotasFiscais.setTelaPai(isto);
				telaTodasNotasFiscais.limpar();
				telaTodasNotasFiscais.setRetornoGlobal(1);
				telaTodasNotasFiscais
						.setDadosPesquisa(
								"", "", "VENDA", produto.replaceAll("NON-GMO", "").replaceAll("GMO", "")
										.replaceAll("Não informar", "").replaceAll(" ", ""),
								entCodigoNFVenda.getText());
				telaTodasNotasFiscais.habilitarBtnSelecionar();
				telaTodasNotasFiscais.pesquisar_notas();

				telaTodasNotasFiscais.setVisible(true);

			}
		});
		panel_1.add(btnSelecionarNFVenda, "cell 29 8,alignx left,aligny top");

		btnSelecionarNFRemessa = new JButton("Ler NF Remessa");
		btnSelecionarNFRemessa.setBackground(new Color(0, 0, 102));
		btnSelecionarNFRemessa.setForeground(Color.WHITE);
		btnSelecionarNFRemessa.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSelecionarNFRemessa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String destinatario = "";

				if (cliente_recebimento.getTipo_pessoa() == 0) {
					destinatario = cliente_recebimento.getNome_empresarial().trim();
				} else {
					destinatario = cliente_recebimento.getNome_fantaia().trim();
				}

				String remetente = "";

				if (vendedor.getTipo_pessoa() == 0) {
					remetente = vendedor.getNome_empresarial().trim();
				} else {
					remetente = vendedor.getNome_fantaia().trim();
				}

				String produto = produto_carregamento.getNome_produto();

				TelaTodasNotasFiscais telaTodasNotasFiscais = new TelaTodasNotasFiscais(0, 1, isto);
				telaTodasNotasFiscais.setTelaPai(isto);
				telaTodasNotasFiscais.limpar();
				telaTodasNotasFiscais.setRetornoGlobal(2);
				telaTodasNotasFiscais.setDadosPesquisa("LD", "", "Remessa", produto, entCodigoNFRemessa.getText());
				telaTodasNotasFiscais.habilitarBtnSelecionar();
				telaTodasNotasFiscais.pesquisar_notas();

				telaTodasNotasFiscais.setVisible(true);

			}
		});
		panel_1.add(btnSelecionarNFRemessa, "cell 29 11,alignx left,aligny top");

		chkBoxNFVendaNaoAplicavel = new JCheckBox("Não Aplicável");
		chkBoxNFVendaNaoAplicavel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		chkBoxNFVendaNaoAplicavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkBoxNFVendaNaoAplicavel.isSelected()) {

					desativarNFVenda();

				} else {
					ativarNFVenda();

				}
			}
		});
		chkBoxNFVendaNaoAplicavel.setForeground(Color.WHITE);
		panel_1.add(chkBoxNFVendaNaoAplicavel, "cell 6 7,alignx left,aligny top");

		chkBoxNFRemessaNaoAplicavel = new JCheckBox("Não Aplicável");
		chkBoxNFRemessaNaoAplicavel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		chkBoxNFRemessaNaoAplicavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkBoxNFRemessaNaoAplicavel.isSelected()) {
					desativarNFRemessa();

				} else {
					ativarNFRemessa();

				}
			}
		});
		chkBoxNFRemessaNaoAplicavel.setForeground(Color.WHITE);
		panel_1.add(chkBoxNFRemessaNaoAplicavel, "cell 6 10,alignx left,aligny top");

		JLabel lblNewLabel_9_1_1_2 = new JLabel("Valor NF Venda:");
		lblNewLabel_9_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_1_1_2.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_2, "cell 23 8,alignx right,aligny center");

		entValorNFVenda = new JTextField();
		entValorNFVenda.setFont(new Font("Dialog", Font.BOLD, 16));
		entValorNFVenda.setForeground(Color.BLACK);
		entValorNFVenda.setColumns(10);
		entValorNFVenda.setBackground(Color.WHITE);
		panel_1.add(entValorNFVenda, "cell 25 8 3 1,growx,aligny top");

		JLabel lblNewLabel_9_1_1_1_1 = new JLabel("Peso NF Remessa:");
		lblNewLabel_9_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_1_1_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_1_1, "cell 11 11 6 1,alignx right,aligny center");

		entPesoNFRemessa = new JTextField();
		entPesoNFRemessa.setFont(new Font("Dialog", Font.BOLD, 16));
		entPesoNFRemessa.setForeground(Color.BLACK);
		entPesoNFRemessa.setColumns(10);
		entPesoNFRemessa.setBackground(Color.WHITE);
		panel_1.add(entPesoNFRemessa, "cell 17 11 6 1,growx,aligny top");

		JLabel lblNewLabel_9_1_1_2_1 = new JLabel("Valor NF Remessa:");
		lblNewLabel_9_1_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_1_1_2_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_1_1_2_1, "cell 23 11,alignx right,aligny center");

		entValorNFRemessa = new JTextField();
		entValorNFRemessa.setFont(new Font("Dialog", Font.BOLD, 16));
		entValorNFRemessa.setForeground(Color.BLACK);
		entValorNFRemessa.setColumns(10);
		entValorNFRemessa.setBackground(Color.WHITE);
		panel_1.add(entValorNFRemessa, "cell 25 11 3 1,growx,aligny top");

		JLabel lblNewLabel_9_3 = new JLabel("Remetente:");
		lblNewLabel_9_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_3.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_3, "cell 0 9 6 1,alignx right,aligny center");

		entRemetenteNFVenda = new JTextField();
		entRemetenteNFVenda.setFont(new Font("Dialog", Font.BOLD, 16));
		entRemetenteNFVenda.setForeground(Color.BLACK);
		entRemetenteNFVenda.setColumns(10);
		entRemetenteNFVenda.setBackground(Color.WHITE);
		panel_1.add(entRemetenteNFVenda, "cell 6 9 11 1,grow");

		entDestinatarioNFVenda = new JTextField();
		entDestinatarioNFVenda.setFont(new Font("Dialog", Font.BOLD, 16));
		entDestinatarioNFVenda.setForeground(Color.BLACK);
		entDestinatarioNFVenda.setColumns(10);
		entDestinatarioNFVenda.setBackground(Color.WHITE);
		panel_1.add(entDestinatarioNFVenda, "cell 23 9 7 1,grow");

		JLabel lblNewLabel_9_3_1 = new JLabel("Destinatário:");
		lblNewLabel_9_3_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_3_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_3_1, "cell 17 9 6 1,alignx right,aligny center");

		entRemetenteNFRemessa = new JTextField();
		entRemetenteNFRemessa.setFont(new Font("Dialog", Font.BOLD, 16));
		entRemetenteNFRemessa.setForeground(Color.BLACK);
		entRemetenteNFRemessa.setColumns(10);
		entRemetenteNFRemessa.setBackground(Color.WHITE);
		panel_1.add(entRemetenteNFRemessa, "cell 6 12 11 1,grow");

		JLabel lblNewLabel_9_3_2 = new JLabel("Remetente:");
		lblNewLabel_9_3_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_3_2.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_3_2, "cell 0 12 6 1,alignx right,aligny center");

		JLabel lblNewLabel_9_3_1_1 = new JLabel("Destinatário:");
		lblNewLabel_9_3_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_9_3_1_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_9_3_1_1, "cell 17 12 6 1,alignx right,aligny center");

		entDestinatarioNFRemessa = new JTextField();
		entDestinatarioNFRemessa.setFont(new Font("Dialog", Font.BOLD, 16));
		entDestinatarioNFRemessa.setForeground(Color.BLACK);
		entDestinatarioNFRemessa.setColumns(10);
		entDestinatarioNFRemessa.setBackground(Color.WHITE);
		panel_1.add(entDestinatarioNFRemessa, "cell 23 12 7 1,grow");

		CadastroCliente vendedores[] = contrato_local.getVendedores();
		setVendedor(vendedores[0]);
		getContentPane().setLayout(new MigLayout("", "[1079.00px]", "[grow]"));

		getContentPane().add(abas, "cell 0 0,grow");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1104, 737);
		painelConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				atualizarDadosConfirmar();
			}
		});

		painelConfirmar.setBackground(new Color(0, 102, 102));
		painelPaiConfirmar.setBackground(new Color(0, 102, 102));

		// adiciona novos paines e suas abas
		abas.addTab("Confirmar", painelPaiConfirmar);
		painelPaiConfirmar.setLayout(new MigLayout("", "[grow]", "[grow]"));

		painelPaiConfirmar.add(painelConfirmar, "cell 0 0,alignx center,aligny center");
		painelConfirmar.setLayout(new MigLayout("", "[105px][186px][18px][99px][13px][65px][20px][44px][20px][98px]",
				"[41px][47px][44px][30px][41px][52px][26px][100px][20px][112px][17px][28px]"));

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel, "cell 0 0,alignx right,aligny bottom");

		lblDataRecebimento = new JLabel("");
		lblDataRecebimento.setBackground(Color.WHITE);
		lblDataRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		lblDataRecebimento.setForeground(Color.WHITE);
		lblDataRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblDataRecebimento, "cell 1 0,grow");

		lblClienteRecebimento = new JLabel("");
		lblClienteRecebimento.setBackground(Color.WHITE);
		lblClienteRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		lblClienteRecebimento.setForeground(Color.WHITE);
		lblClienteRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblClienteRecebimento, "cell 1 1 9 1,grow");

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblCliente.setForeground(Color.WHITE);
		painelConfirmar.add(lblCliente, "cell 0 1,alignx right,aligny center");

		JButton btnSalvar = new JButton("Confirmar");
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				Recebimento recebimento = getRecebimentoSalvar(new CadastroContrato.Recebimento());

				CadastroContrato.Recebimento duplicado = gerenciar
						.procurarDuplicataRecebimento(recebimento.getCodigo_romaneio());
				if (duplicado != null) {
					JOptionPane.showMessageDialog(isto,
							"Já Existe um recebimento associado a este código de romaneio!");

					if (JOptionPane.showConfirmDialog(isto, "Abrir", "Deseja ver o recebimento?",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						CadastroContrato contrato_selecionado = new GerenciarBancoContratos()
								.getContrato(duplicado.getId_contrato_recebimento());
						TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(contrato_selecionado,
								isto);
						gerenciar_contrato.setTelaRecebimentos(duplicado.getId_recebimento());

					}
				} else {

					int retorno = gerenciar.inserirRecebimento(contrato_local.getId(), recebimento);
					if (retorno > 0) {
						JOptionPane.showMessageDialog(isto, "Recebimento Cadastrado!");
						// ((TelaGerenciarContrato) telaPai).pesquisar_carregamentos();
						((TelaGerenciarContrato) telaPaiJFrame).pesquisar_recebimentos(true);
						((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos(true);

						recebimento.setId_recebimento(retorno);
						recebimento_global = recebimento;
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
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1, "cell 0 3,alignx right,aligny center");

		lblContratoRecebimento = new JLabel("");
		lblContratoRecebimento.setBackground(Color.WHITE);
		lblContratoRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		lblContratoRecebimento.setForeground(Color.WHITE);
		lblContratoRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblContratoRecebimento, "cell 1 3 9 1,grow");

		JLabel lblNewLabel_1_1 = new JLabel("Transportador:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1, "cell 0 4,alignx right,aligny center");

		lblTransportadorRecebimento = new JLabel("");
		lblTransportadorRecebimento.setBackground(Color.WHITE);
		lblTransportadorRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		lblTransportadorRecebimento.setForeground(Color.WHITE);
		lblTransportadorRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConfirmar.add(lblTransportadorRecebimento, "cell 1 4 3 1,grow");

		JLabel lblNewLabel_1_1_1 = new JLabel("Veiculo:");
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1, "cell 5 4,alignx right,aligny center");

		lblVeiculoRecebimento = new JLabel("");
		lblVeiculoRecebimento.setBackground(Color.WHITE);
		lblVeiculoRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		lblVeiculoRecebimento.setForeground(Color.WHITE);
		lblVeiculoRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblVeiculoRecebimento, "cell 7 4 3 1,grow");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Código Romaneio:");
		lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1_1, "cell 0 5,growx,aligny center");

		lblCodigoRomaneio = new JLabel("");
		lblCodigoRomaneio.setBackground(Color.WHITE);
		lblCodigoRomaneio.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCodigoRomaneio.setForeground(Color.WHITE);
		lblCodigoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCodigoRomaneio, "cell 1 5,grow");

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Peso Romaneio:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_1_1_1_1_1, "cell 3 5,alignx left,aligny center");

		lblPesoRomaneio = new JLabel("");
		lblPesoRomaneio.setBackground(Color.WHITE);
		lblPesoRomaneio.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPesoRomaneio.setForeground(Color.WHITE);
		lblPesoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblPesoRomaneio, "cell 5 5 5 1,grow");

		JLabel lblNewLabel_2 = new JLabel("NF Venda:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_2.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_2, "cell 0 7,alignx right,aligny top");

		lblNotaFiscalVenda = new JTextArea("");
		lblNotaFiscalVenda.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNotaFiscalVenda.setEditable(false);
		lblNotaFiscalVenda.setLineWrap(true);
		lblNotaFiscalVenda.setWrapStyleWord(true);
		lblNotaFiscalVenda.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPane = new JScrollPane(lblNotaFiscalVenda);
		painelConfirmar.add(scrollPane, "cell 1 7 9 1,grow");

		lblCaminhoNFVenda = new JLabel("");
		lblCaminhoNFVenda.setBackground(Color.WHITE);
		lblCaminhoNFVenda.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCaminhoNFVenda.setForeground(Color.WHITE);
		lblCaminhoNFVenda.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFVenda, "cell 1 8 9 1,grow");

		JLabel lblNewLabel_2_1 = new JLabel("NF Remessa:");
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_2_1.setForeground(Color.WHITE);
		painelConfirmar.add(lblNewLabel_2_1, "cell 0 9,alignx right,aligny top");

		lblNotaFiscalRemessa = new JTextArea("");
		lblNotaFiscalRemessa.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNotaFiscalRemessa.setEditable(false);
		lblNotaFiscalRemessa.setWrapStyleWord(true);
		lblNotaFiscalRemessa.setLineWrap(true);
		lblNotaFiscalRemessa.setBorder(new LineBorder(new Color(0, 0, 0)));

		JScrollPane scrollPaneNFRemessa = new JScrollPane(lblNotaFiscalRemessa);
		painelConfirmar.add(scrollPaneNFRemessa, "cell 1 9 9 1,grow");

		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblVendedor.setForeground(Color.WHITE);
		painelConfirmar.add(lblVendedor, "cell 0 2,alignx right,aligny center");

		lblVendedorRecebimento = new JLabel("");
		lblVendedorRecebimento.setBackground(Color.WHITE);
		lblVendedorRecebimento.setFont(new Font("Dialog", Font.BOLD, 16));
		lblVendedorRecebimento.setForeground(Color.WHITE);
		lblVendedorRecebimento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblVendedorRecebimento, "cell 1 2 9 1,grow");

		lblCaminhoNFRemessa = new JLabel("");
		lblCaminhoNFRemessa.setBackground(Color.WHITE);
		lblCaminhoNFRemessa.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCaminhoNFRemessa.setForeground(Color.WHITE);
		lblCaminhoNFRemessa.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoNFRemessa, "cell 1 10 9 1,grow");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBackground(new Color(0, 0, 102));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));

		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean prosseguir = false;
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				CadastroContrato.Recebimento recebimento = getRecebimentoAtualizar(recebimento_global);

				CadastroContrato.Recebimento duplicado = gerenciar
						.procurarDuplicataRecebimento(recebimento.getCodigo_romaneio());

				if (duplicado != null && duplicado.getId_recebimento() != recebimento.getId_recebimento()) {

					TelaOpcoes tela = new TelaOpcoes(2, duplicado.getId_contrato_recebimento(), isto);
					tela.setVisible(true);
					
				} else {
					prosseguir = true;
					

				}
				
				if(prosseguir || prosseguir_mesmo_duplicado) {
					boolean atualizou = gerenciar.atualizar_recebimento(recebimento);

					if (atualizou) {
						JOptionPane.showMessageDialog(isto, "Recebimento Atualizado");

						((TelaGerenciarContrato) telaPaiJFrame).pesquisar_recebimentos(true);
						((TelaGerenciarContrato) telaPaiJFrame).pesquisar_carregamentos(true);

						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao atualizar o recebimento\nConsulte o Administrador");
						isto.dispose();

					}
				}
			}
		});
		painelConfirmar.add(btnAtualizar, "cell 5 11 3 1,alignx right,growy");

		lblCaminhoRomaneio = new JLabel("");
		lblCaminhoRomaneio.setBackground(Color.WHITE);
		lblCaminhoRomaneio.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCaminhoRomaneio.setForeground(Color.WHITE);
		lblCaminhoRomaneio.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelConfirmar.add(lblCaminhoRomaneio, "cell 1 6 9 1,grow");
		// this.setUndecorated(true);

		if (flag_modo_tela == 0) {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else {
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

				if (_transportador.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBTransportador.addItem(_transportador.getNome_empresarial().toUpperCase().trim());

				} else {
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

				if (_transportador.getTipo_pessoa() == 0) {
					// pessoa fisica
					cBTransportador.addItem(_transportador.getNome_empresarial().toUpperCase().trim());

				} else {
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
		if (vendedor.getTipo_pessoa() == 0) {
			nome_vendedor = vendedor.getNome_empresarial().trim().toUpperCase();
		} else {
			nome_vendedor = vendedor.getNome_fantaia().trim().toUpperCase();
		}

		lblVendedorRecebimento.setText(nome_vendedor);

		if (transportador_carregamento != null) {
			if (transportador_carregamento.getTipo_pessoa() == 0) {
				lblTransportadorRecebimento.setText(transportador_carregamento.getNome_empresarial());
			} else {
				lblTransportadorRecebimento.setText(transportador_carregamento.getNome_fantaia());
			}
			String s_veiculo = cbVeiculo.getSelectedItem().toString();
			String separados[] = s_veiculo.split("-");
			int id_veiculo = Integer.parseInt(separados[0]);

			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
				if (veiculo.getId_veiculo() == id_veiculo) {
					veiculo_carregamento = veiculo;
					lblVeiculoRecebimento.setText(
							veiculo_carregamento.getId_veiculo() + "-" + veiculo_carregamento.getPlaca_trator());
					break;
				}

			}
		}

		Locale ptBr = new Locale("pt", "BR");

		NumberFormat z = NumberFormat.getNumberInstance();

		if (romaneio_recebimento != null) {
			lblCodigoRomaneio.setText(romaneio_recebimento.getNumero_romaneio() + "");
			lblPesoRomaneio.setText(z.format(romaneio_recebimento.getPeso_liquido()));

			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
			String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf".replace("\"", "\\");
			lblCaminhoRomaneio.setText(caminho_completo_normalizado);

		} else {
			if (entRomaneio.getText() != null)
				lblCodigoRomaneio.setText(entRomaneio.getText());
			if (pesoRomaneio.getText() != null && !pesoRomaneio.getText().equals("")
					&& !pesoRomaneio.getText().equals(" "))
				lblPesoRomaneio.setText(z.format(Double.parseDouble(pesoRomaneio.getText())));
		}

		if (!chkBoxNFVendaNaoAplicavel.isSelected()) {
			if (nota_fiscal_venda_recebimento != null) {

				String codigo = nota_fiscal_venda_recebimento.getNfe();
				String peso_nf = nota_fiscal_venda_recebimento.getQuantidade();
				String destinatario = nota_fiscal_venda_recebimento.getNome_destinatario();
				String remetente = nota_fiscal_venda_recebimento.getNome_remetente();
				String natureza = nota_fiscal_venda_recebimento.getNatureza();
				String data = nota_fiscal_venda_recebimento.getData().toString();
				String produto = nota_fiscal_venda_recebimento.getProduto();
				String valor = nota_fiscal_venda_recebimento.getValor();

				String texto_revisao_nfs = "";

				texto_revisao_nfs = "NFe: " + codigo + "\nRemetente: " + remetente + "\nDestinatario: " + destinatario
						+ "\nNatureza: " + natureza + "\nData: " + data + "\nProduto: " + produto + "\nPeso: " + peso_nf
						+ "\nValor: " + valor;

				lblNotaFiscalVenda.setText(texto_revisao_nfs);

				String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
				String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf".replace("\"", "\\");

				lblCaminhoNFVenda.setText(caminho_completo_normalizado);

			} else {
				String texto = "Código: ";
				if (checkString(entCodigoNFVenda.getText())) {
					texto = texto + entCodigoNFVenda.getText();
				} else {
					texto = texto + " ";
				}
				texto = texto + " Peso: ";

				if (checkString(entPesoNFVenda.getText())) {
					texto = texto + z.format(Double.parseDouble(entPesoNFVenda.getText()));
				} else {
					texto = texto + " ";

				}
				texto = texto + " Valor: ";

				if (checkString(entValorNFVenda.getText())) {
					String valor = entValorNFVenda.getText().replaceAll("[^0-9,]", "").replaceAll(",", ".");

					texto = texto + NumberFormat.getCurrencyInstance(ptBr).format(new BigDecimal(valor).doubleValue());

				} else {
					texto = texto + " ";

				}
				texto = texto + " Remetente: ";

				if (checkString(entRemetenteNFVenda.getText())) {
					texto = texto + entRemetenteNFVenda.getText();
				} else {
					texto = texto + " ";

				}
				texto = texto + " Destinatário: ";
				if (checkString(entDestinatarioNFVenda.getText())) {
					texto = texto + entDestinatarioNFVenda.getText();
				} else {
					texto = texto + " ";

				}

				lblNotaFiscalVenda.setText(texto);

			}
		} else {

			lblNotaFiscalVenda.setText("Não Aplicável");

		}

		if (!chkBoxNFRemessaNaoAplicavel.isSelected()) {
			if (nota_fiscal_remessa_recebimento != null) {
				String codigo = nota_fiscal_remessa_recebimento.getNfe();
				String peso_nf = nota_fiscal_remessa_recebimento.getQuantidade();
				String destinatario = nota_fiscal_remessa_recebimento.getNome_destinatario();
				String remetente = nota_fiscal_remessa_recebimento.getNome_remetente();
				String natureza = nota_fiscal_remessa_recebimento.getNatureza();
				String data = nota_fiscal_remessa_recebimento.getData().toString();
				String produto = nota_fiscal_remessa_recebimento.getProduto();
				String valor = nota_fiscal_remessa_recebimento.getValor();

				String texto_revisao_nfs = "";

				texto_revisao_nfs = "NFe: " + codigo + "\nRemetente: " + remetente + "\nDestinatario: " + destinatario
						+ "\nNatureza: " + natureza + "\nData: " + data + "\nProduto: " + produto + "\nPeso: " + peso_nf
						+ "\nValor: " + valor

				;
				lblNotaFiscalRemessa.setText(texto_revisao_nfs);
				String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
				String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf".replace("\"", "\\");
				lblCaminhoNFRemessa.setText(caminho_completo_normalizado);

			} else {
				String texto = "Código: ";
				if (checkString(entCodigoNFRemessa.getText())) {
					texto = texto + entCodigoNFRemessa.getText();
				} else {
					texto = texto + " ";
				}
				texto = texto + " Peso: ";

				if (checkString(entPesoNFRemessa.getText())) {
					texto = texto + z.format(Double.parseDouble(entPesoNFRemessa.getText()));
				} else {
					texto = texto + " ";

				}
				texto = texto + " Valor: ";

				if (checkString(entValorNFRemessa.getText())) {
					String valor = entValorNFRemessa.getText().replaceAll("[^0-9,]", "").replaceAll(",", ".");

					texto = texto + NumberFormat.getCurrencyInstance(ptBr).format(new BigDecimal(valor).doubleValue());

				} else {
					texto = texto + " ";

				}
				texto = texto + " Remetente: ";

				if (checkString(entRemetenteNFRemessa.getText())) {
					texto = texto + entRemetenteNFRemessa.getText();
				} else {
					texto = texto + " ";

				}
				texto = texto + " Destinatário: ";
				if (checkString(entDestinatarioNFRemessa.getText())) {
					texto = texto + entDestinatarioNFRemessa.getText();
				} else {
					texto = texto + " ";

				}

				lblNotaFiscalRemessa.setText(texto);

			}
		} else {
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
				// cBVendedor.removeAll();
				cBVendedor.removeAllItems();
				cBVendedor.repaint();
				cBVendedor.updateUI();

				String nome;
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome = vendedor.getNome_empresarial();
				} else {
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

	public CadastroContrato.Recebimento getRecebimentoAtualizar(CadastroContrato.Recebimento recebimento_a_inserir) {

		// criar a pasta para o recebimento
		boolean pasta_recebimento_contrato1_existe = false;
		boolean pasta_recebimento_contrato2_existe = false;

		String caminho_diretorio1 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato();
		String caminho_diretorio2 = "";

		if (contrato_local.getCaminho_diretorio_contrato2() != null) {
			caminho_diretorio2 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato2();
		} else {
			caminho_diretorio2 = null;
		}

		ManipularTxt manipular = new ManipularTxt();
		// cria o diretorio recebimentos no contrato1
		File diretorio_recebimentos_contrato1 = new File(caminho_diretorio1 + "\\recebimentos");
		File diretorio_recebimentos_contrato2 = null;
		if (!diretorio_recebimentos_contrato1.exists()) {
			manipular.criarDiretorio(diretorio_recebimentos_contrato1.getAbsolutePath());
		}

		if (caminho_diretorio2 != null) {
			diretorio_recebimentos_contrato2 = new File(caminho_diretorio2 + "\\recebimentos");
			if (!diretorio_recebimentos_contrato2.exists()) {
				manipular.criarDiretorio(diretorio_recebimentos_contrato2.getAbsolutePath());
			}
		}

		// criar diretorio do recebimento na pasta do contrato 1
		File diretorio_este_recebimento_contrato1 = new File(
				caminho_diretorio1 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());
		if (!diretorio_este_recebimento_contrato1.exists()) {
			boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato1.getAbsolutePath());
			if (criar) {
				// JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
				pasta_recebimento_contrato1_existe = true;

			} else {

			}
		} else {
			// JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja
			// existe");

			pasta_recebimento_contrato1_existe = true;
		}

		File diretorio_este_recebimento_contrato2 = new File(
				caminho_diretorio2 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());

		if (caminho_diretorio2 != null) {

			if (!diretorio_este_recebimento_contrato2.exists()) {
				boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato2.getAbsolutePath());
				if (criar) {
					pasta_recebimento_contrato2_existe = true;

				} else {

				}
			} else {
				pasta_recebimento_contrato2_existe = true;

			}
		}

		recebimento_a_inserir.setData_recebimento(lblDataRecebimento.getText());
		recebimento_a_inserir.setId_cliente(cliente_recebimento.getId());
		recebimento_a_inserir.setId_vendedor(vendedor.getId());
		recebimento_a_inserir.setId_contrato_recebimento(contrato_local.getId());

		if (transportador_carregamento != null) {
			recebimento_a_inserir.setId_transportador(transportador_carregamento.getId());
			recebimento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
		}

		if (!chkBoxNFVendaNaoAplicavel.isSelected()) {
			if (nota_fiscal_venda_recebimento != null) {
				String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
				String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
				String conteudo[] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for (String str : conteudo) {

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

				// remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_venda(nota_fiscal_venda_recebimento.getNome_remetente());
				recebimento_a_inserir
						.setNome_destinatario_nf_venda(nota_fiscal_venda_recebimento.getNome_destinatario());

				if (pasta_recebimento_contrato1_existe) {
					// copiar a nota para esta pasta
					try {

						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
								diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\"
										+ conteudo[conteudo.length - 1]);

					} catch (IOException e) {
						// JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " +
						// e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if (pasta_recebimento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
								diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\"
										+ conteudo[conteudo.length - 1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				recebimento_a_inserir.setNf_venda_aplicavel(1);

				recebimento_a_inserir.setCodigo_nf_venda(entCodigoNFVenda.getText());
				try {
					recebimento_a_inserir.setPeso_nf_venda(Double.parseDouble(entPesoNFVenda.getText()));
				} catch (Exception u) {
					recebimento_a_inserir.setPeso_nf_venda(0);

				}
				try {
					if (checkString(entValorNFVenda.getText())) {
						String valor_nf_venda = entValorNFVenda.getText().replaceAll("[^0-9,]", "").replaceAll(",",
								".");

						recebimento_a_inserir.setValor_nf_venda(new BigDecimal(valor_nf_venda));

					} else {
						recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);

					}
				} catch (Exception e) {

				}

				// remetente e destinatario
				if (checkString(entRemetenteNFVenda.getText())) {

					recebimento_a_inserir.setNome_remetente_nf_venda(entRemetenteNFVenda.getText());

				} else {
					recebimento_a_inserir.setNome_remetente_nf_venda("");

				}
				if (checkString(entDestinatarioNFVenda.getText())) {

					recebimento_a_inserir.setNome_destinatario_nf_venda(entDestinatarioNFVenda.getText());

				} else {
					recebimento_a_inserir.setNome_destinatario_nf_venda("");

				}
			}
		} else {
			recebimento_a_inserir.setNf_venda_aplicavel(0);
			recebimento_a_inserir.setPeso_nf_venda(0);
			recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_remetente_nf_venda("");
			recebimento_a_inserir.setNome_destinatario_nf_venda("");
		}

		if (!chkBoxNFRemessaNaoAplicavel.isSelected()) {
			if (nota_fiscal_remessa_recebimento != null) {
				String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
				String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
				String conteudo[] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for (String str : conteudo) {

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

				// remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_remessa(nota_fiscal_remessa_recebimento.getNome_remetente());
				recebimento_a_inserir
						.setNome_destinatario_nf_remessa(nota_fiscal_remessa_recebimento.getNome_destinatario());

				if (pasta_recebimento_contrato1_existe) {
					// copiar a nota para esta pasta
					try {

						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
								diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\"
										+ conteudo[conteudo.length - 1]);

					} catch (IOException e) {
						// JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " +
						// e.getMessage() + "\nCausa: " + e.getCause());
						e.printStackTrace();
					}
				}
				if (pasta_recebimento_contrato2_existe) {
					try {
						boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
								diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\"
										+ conteudo[conteudo.length - 1]);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				recebimento_a_inserir.setNf_remessa_aplicavel(1);

				recebimento_a_inserir.setCodigo_nf_remessa(entCodigoNFRemessa.getText());

				try {
					recebimento_a_inserir.setPeso_nf_remessa(Double.parseDouble(entPesoNFRemessa.getText()));
				} catch (Exception u) {
					recebimento_a_inserir.setPeso_nf_remessa(0);

				}

				try {
					if (checkString(entValorNFRemessa.getText())) {
						String valor_nf_remessa = entValorNFRemessa.getText().replaceAll("[^0-9,]", "").replaceAll(",",
								".");

						recebimento_a_inserir.setValor_nf_remessa(new BigDecimal(valor_nf_remessa));
					} else {
						recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);

					}
				} catch (Exception e) {

				}
				// remetente e destinatario
				if (checkString(entRemetenteNFRemessa.getText())) {

					recebimento_a_inserir.setNome_remetente_nf_remessa(entRemetenteNFRemessa.getText());

				} else {
					recebimento_a_inserir.setNome_remetente_nf_remessa("");
				}
				if (checkString(entDestinatarioNFRemessa.getText())) {

					recebimento_a_inserir.setNome_destinatario_nf_remessa(entDestinatarioNFRemessa.getText());

				} else {
					recebimento_a_inserir.setNome_destinatario_nf_remessa("");

				}

			}
		} else {
			recebimento_a_inserir.setNf_remessa_aplicavel(0);

			recebimento_a_inserir.setPeso_nf_remessa(0);
			recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_remetente_nf_remessa("");

			recebimento_a_inserir.setNome_destinatario_nf_remessa("");

		}

		if (romaneio_recebimento != null) {
			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
			String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
			String conteudo[] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for (String str : conteudo) {

				url_final = url_final + str + "\\\\";
			}
			recebimento_a_inserir.setCodigo_romaneio(Integer.toString(romaneio_recebimento.getNumero_romaneio()));
			recebimento_a_inserir.setPeso_romaneio(romaneio_recebimento.getPeso_liquido());

			recebimento_a_inserir.setCaminho_romaneio(url_final);
			if (pasta_recebimento_contrato1_existe) {
				// copiar a nota para esta pasta
				try {

					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " +
					// e.getMessage() + "\nCausa: " + e.getCause());
					e.printStackTrace();
				}
			}
			if (pasta_recebimento_contrato2_existe) {
				try {
					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
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
			if (checkString(recebimento_global.getCodigo_romaneio())) {
				// procurar por romaneio
				if (checkString(recebimento_global.getCaminho_romaneio())) {
					ManipularRomaneios manipular = new ManipularRomaneios("");

					CadastroRomaneio romaneio = manipular
							.filtrar(new File(servidor_unidade + recebimento_global.getCaminho_romaneio()));
					setRomaneio(romaneio);
				} else {
					entRomaneio.setText(recebimento_global.getCodigo_romaneio());
					pesoRomaneio.setText(Double.toString(recebimento_global.getPeso_romaneio()));
				}

			}
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(isto, "Romaneio não Localizado");
			entRomaneio.setText(recebimento_global.getCodigo_romaneio());
			pesoRomaneio.setText(Double.toString(recebimento_global.getPeso_romaneio()));
		}

		GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
		CadastroCliente cliente = gerenciar_clientes.getCliente(recebimento_global.getId_cliente());
		setClienteRecebimento(cliente);

		CadastroCliente vendedor = gerenciar_clientes.getCliente(recebimento_global.getId_vendedor());
		setVendedor(vendedor);

		try {

			if (recebimento_global.getId_transportador() > 0) {
				CadastroCliente transportador = gerenciar_clientes.getCliente(recebimento_global.getId_transportador());

				if (recebimento_global.getId_veiculo() > 0) {
					CadastroCliente.Veiculo veiculo = gerenciar_clientes.getVeiculo(recebimento_global.getId_veiculo());
					setTransportador(transportador, veiculo);
				}
			}
		} catch (Exception t) {

		}

		// nf venda
		if (recebimento_global.getNf_venda_aplicavel() == 1) {
			ativarNFRemessa();

			try {
				if (checkString(recebimento_global.getCodigo_nf_venda())) {
					if (recebimento_global.getCaminho_nf_venda().length() > 10) {
						// procurar por nf venda
						ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						CadastroNFe nota_fiscal_venda = manipular
								.filtrar(new File(servidor_unidade + recebimento_global.getCaminho_nf_venda()));
						setNotaFiscalVenda(nota_fiscal_venda);
					} else {
						entCodigoNFVenda.setText(recebimento_global.getCodigo_nf_venda());
						entPesoNFVenda.setText(Double.toString(recebimento_global.getPeso_nf_venda()));
						String valor_nf_venda = recebimento_global.getValor_nf_venda().toString();
						valor_nf_venda = valor_nf_venda.replace(".", ",");
						entValorNFVenda.setText(valor_nf_venda);
						entRemetenteNFVenda.setText(recebimento_global.getNome_remetente_nf_venda());
						entDestinatarioNFVenda.setText(recebimento_global.getNome_destinatario_nf_venda());
					}

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(isto, "Nota Fiscal de venda não Localizado");
				entCodigoNFVenda.setText(recebimento_global.getCodigo_nf_venda());
				entPesoNFVenda.setText(Double.toString(recebimento_global.getPeso_nf_venda()));
				String valor_nf_venda = recebimento_global.getValor_nf_venda().toString();
				valor_nf_venda = valor_nf_venda.replace(".", ",");
				entValorNFVenda.setText(valor_nf_venda);
				entRemetenteNFVenda.setText(recebimento_global.getNome_remetente_nf_venda());
				entDestinatarioNFVenda.setText(recebimento_global.getNome_destinatario_nf_venda());
			}
		} else {
			desativarNFVenda();
		}

		// nf remessa
		if (recebimento_global.getNf_remessa_aplicavel() == 1) {
			ativarNFRemessa();

			try {
				if (checkString(recebimento_global.getCodigo_nf_remessa())) {
					if (recebimento_global.getCaminho_nf_remessa().length() > 10) {
						// procurar por nf remessa
						ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
						CadastroNFe nota_fiscal_remessa = manipular
								.filtrar(new File(servidor_unidade + recebimento_global.getCaminho_nf_remessa()));
						setNotaFiscalRemessa(nota_fiscal_remessa);
					} else {
						entCodigoNFRemessa.setText(recebimento_global.getCodigo_nf_remessa());
						entPesoNFRemessa.setText(Double.toString(recebimento_global.getPeso_nf_remessa()));
						String valor_nf_remessa = recebimento_global.getValor_nf_remessa().toString();
						valor_nf_remessa = valor_nf_remessa.replace(".", ",");
						entValorNFRemessa.setText(valor_nf_remessa);
						entRemetenteNFRemessa.setText(recebimento_global.getNome_remetente_nf_remessa());
						entDestinatarioNFRemessa.setText(recebimento_global.getNome_destinatario_nf_remessa());
					}

				}
			} catch (Exception e) {
				// JOptionPane.showMessageDialog(isto, "Nota Fiscal de remessa não Localizado");
				entCodigoNFRemessa.setText(recebimento_global.getCodigo_nf_remessa());
				entPesoNFRemessa.setText(Double.toString(recebimento_global.getPeso_nf_remessa()));
				String valor_nf_remessa = recebimento_global.getValor_nf_remessa().toString();
				valor_nf_remessa = valor_nf_remessa.replace(".", ",");
				entValorNFRemessa.setText(valor_nf_remessa);
				entRemetenteNFRemessa.setText(recebimento_global.getNome_remetente_nf_remessa());
				entDestinatarioNFRemessa.setText(recebimento_global.getNome_destinatario_nf_remessa());
			}
		} else {
			desativarNFRemessa();

		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();
		servidor_unidade = configs_globais.getServidorUnidade();

	}

	public void gerarPastasEArquivos() {
		// criar a pasta para o recebimento
		boolean pasta_recebimento_contrato1_existe = false;
		boolean pasta_recebimento_contrato2_existe = false;

		String caminho_diretorio1 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato();
		String caminho_diretorio2 = "";

		if (contrato_local.getCaminho_diretorio_contrato2() != null) {
			caminho_diretorio2 = servidor_unidade + contrato_local.getCaminho_diretorio_contrato2();
		} else {
			caminho_diretorio2 = null;
		}

		ManipularTxt manipular = new ManipularTxt();
		// cria o diretorio recebimentos no contrato1
		File diretorio_recebimentos_contrato1 = new File(caminho_diretorio1 + "\\recebimentos");
		File diretorio_recebimentos_contrato2 = null;
		if (!diretorio_recebimentos_contrato1.exists()) {
			manipular.criarDiretorio(diretorio_recebimentos_contrato1.getAbsolutePath());
		}

		if (caminho_diretorio2 != null) {
			diretorio_recebimentos_contrato2 = new File(caminho_diretorio2 + "\\recebimentos");
			if (!diretorio_recebimentos_contrato2.exists()) {
				manipular.criarDiretorio(diretorio_recebimentos_contrato2.getAbsolutePath());
			}
		}

		// criar diretorio do recebimento na pasta do contrato 1
		File diretorio_este_recebimento_contrato1 = new File(
				caminho_diretorio1 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());
		if (!diretorio_este_recebimento_contrato1.exists()) {
			boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato1.getAbsolutePath());
			if (criar) {
				// JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado");
				pasta_recebimento_contrato1_existe = true;

			} else {

			}
		} else {
			// JOptionPane.showMessageDialog(isto, "diretorio recebimento_id criado ja
			// existe");

			pasta_recebimento_contrato1_existe = true;
		}

		File diretorio_este_recebimento_contrato2 = new File(
				caminho_diretorio2 + "\\recebimentos" + "\\recebimento_" + recebimento_global.getId_recebimento());

		if (caminho_diretorio2 != null) {

			if (!diretorio_este_recebimento_contrato2.exists()) {
				boolean criar = manipular.criarDiretorio(diretorio_este_recebimento_contrato2.getAbsolutePath());
				if (criar) {
					pasta_recebimento_contrato2_existe = true;

				} else {

				}
			} else {
				pasta_recebimento_contrato2_existe = true;

			}
		}

		if (nota_fiscal_venda_recebimento != null) {

			String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
			String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
			String conteudo[] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for (String str : conteudo) {

				url_final = url_final + str + "\\\\";
			}

			if (pasta_recebimento_contrato1_existe) {
				// copiar a nota para esta pasta
				try {

					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " +
					// e.getMessage() + "\nCausa: " + e.getCause());
					e.printStackTrace();
				}
			}
			if (pasta_recebimento_contrato2_existe) {
				try {
					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (nota_fiscal_remessa_recebimento != null) {

			String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
			String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
			String conteudo[] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for (String str : conteudo) {

				url_final = url_final + str + "\\\\";
			}

			if (pasta_recebimento_contrato1_existe) {
				// copiar a nota para esta pasta
				try {

					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " +
					// e.getMessage() + "\nCausa: " + e.getCause());
					e.printStackTrace();
				}
			}
			if (pasta_recebimento_contrato2_existe) {
				try {
					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		if (romaneio_recebimento != null) {

			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
			String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
			String conteudo[] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for (String str : conteudo) {

				url_final = url_final + str + "\\\\";
			}

			if (pasta_recebimento_contrato1_existe) {
				// copiar a nota para esta pasta
				try {

					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato1.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// JOptionPane.showMessageDialog(isto, "erro ao copiar arquivo\nerro: " +
					// e.getMessage() + "\nCausa: " + e.getCause());
					e.printStackTrace();
				}
			}
			if (pasta_recebimento_contrato2_existe) {
				try {
					boolean copiar = manipular.copiarNFe(servidor_unidade + url_final,
							diretorio_este_recebimento_contrato2.getAbsolutePath() + "\\"
									+ conteudo[conteudo.length - 1]);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public CadastroContrato.Recebimento getRecebimentoSalvar(CadastroContrato.Recebimento recebimento_a_inserir) {

		recebimento_a_inserir.setData_recebimento(lblDataRecebimento.getText());
		recebimento_a_inserir.setId_cliente(cliente_recebimento.getId());
		recebimento_a_inserir.setId_vendedor(vendedor.getId());
		recebimento_a_inserir.setId_contrato_recebimento(contrato_local.getId());

		if (transportador_carregamento != null) {
			recebimento_a_inserir.setId_transportador(transportador_carregamento.getId());
			recebimento_a_inserir.setId_veiculo(veiculo_carregamento.getId_veiculo());
		}

		if (!chkBoxNFVendaNaoAplicavel.isSelected()) {
			if (nota_fiscal_venda_recebimento != null) {
				String caminho_completo = nota_fiscal_venda_recebimento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
				String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
				String conteudo[] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for (String str : conteudo) {

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

				// remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_venda(nota_fiscal_venda_recebimento.getNome_remetente());
				recebimento_a_inserir
						.setNome_destinatario_nf_venda(nota_fiscal_venda_recebimento.getNome_destinatario());

			} else {
				recebimento_a_inserir.setNf_venda_aplicavel(1);

				recebimento_a_inserir.setCodigo_nf_venda(entCodigoNFVenda.getText());

				try {
					recebimento_a_inserir.setPeso_nf_venda(Double.parseDouble(entPesoNFVenda.getText()));
				} catch (Exception h) {
					recebimento_a_inserir.setPeso_nf_venda(0);

				}

				try {

					if (checkString(entValorNFVenda.getText())) {
						String valor_nf_venda = entValorNFVenda.getText().replace(".", "").replace(",", ".");

						recebimento_a_inserir.setValor_nf_venda(new BigDecimal(valor_nf_venda));
					} else {
						recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);

					}
				} catch (Exception e) {
					recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);

				}

				// remetente e destinatario
				if (checkString(entRemetenteNFVenda.getText())) {

					recebimento_a_inserir.setNome_remetente_nf_venda(entRemetenteNFVenda.getText());

				}
				if (checkString(entDestinatarioNFVenda.getText())) {

					recebimento_a_inserir.setNome_destinatario_nf_venda(entDestinatarioNFVenda.getText());

				}
			}
		} else {
			recebimento_a_inserir.setNf_venda_aplicavel(0);
			recebimento_a_inserir.setPeso_nf_venda(0);
			recebimento_a_inserir.setValor_nf_venda(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_destinatario_nf_venda("");
			recebimento_a_inserir.setNome_destinatario_nf_venda("");
		}

		if (!chkBoxNFRemessaNaoAplicavel.isSelected()) {
			if (nota_fiscal_remessa_recebimento != null) {
				String caminho_completo = nota_fiscal_remessa_recebimento.getCaminho_arquivo();
				TratarDados tratar = new TratarDados(caminho_completo);
				String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
				String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
				String conteudo[] = caminho_completo_normalizado.split("\\\\");
				String url_final = "";
				for (String str : conteudo) {

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

				// remetente e destinatario
				recebimento_a_inserir.setNome_remetente_nf_remessa(nota_fiscal_remessa_recebimento.getNome_remetente());
				recebimento_a_inserir
						.setNome_destinatario_nf_remessa(nota_fiscal_remessa_recebimento.getNome_destinatario());

			} else {
				recebimento_a_inserir.setNf_remessa_aplicavel(1);

				recebimento_a_inserir.setCodigo_nf_remessa(entCodigoNFRemessa.getText());

				try {
					recebimento_a_inserir.setPeso_nf_remessa(Double.parseDouble(entPesoNFRemessa.getText()));
				} catch (Exception y) {
					recebimento_a_inserir.setPeso_nf_remessa(0);

				}
				try {
					if (checkString(entValorNFRemessa.getText())) {
						String valor_nf_remessa = entValorNFRemessa.getText().replace(".", "").replaceAll(",", ".");

						recebimento_a_inserir.setValor_nf_remessa(new BigDecimal(valor_nf_remessa));
					} else {
						recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);

					}
				} catch (Exception e) {
					recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);

				}

				// remetente e destinatario
				if (checkString(entRemetenteNFRemessa.getText())) {

					recebimento_a_inserir.setNome_remetente_nf_remessa(entRemetenteNFRemessa.getText());

				}
				if (checkString(entDestinatarioNFRemessa.getText())) {

					recebimento_a_inserir.setNome_destinatario_nf_remessa(entDestinatarioNFRemessa.getText());

				}

			}
		} else {
			recebimento_a_inserir.setNf_remessa_aplicavel(0);
			recebimento_a_inserir.setPeso_nf_remessa(0);
			recebimento_a_inserir.setValor_nf_remessa(BigDecimal.ZERO);
			recebimento_a_inserir.setNome_remetente_nf_remessa("");
			recebimento_a_inserir.setNome_destinatario_nf_remessa("");

		}

		if (romaneio_recebimento != null) {
			String caminho_completo = romaneio_recebimento.getCaminho_arquivo();
			TratarDados tratar = new TratarDados(caminho_completo);
			String caminho_normalizado = tratar.tratar("E-Contract", "pdf");
			String caminho_completo_normalizado = "E-Contract" + caminho_normalizado + "pdf";
			String conteudo[] = caminho_completo_normalizado.split("\\\\");
			String url_final = "";
			for (String str : conteudo) {

				url_final = url_final + str + "\\\\";
			}
			recebimento_a_inserir.setCodigo_romaneio(Integer.toString(romaneio_recebimento.getNumero_romaneio()));
			recebimento_a_inserir.setPeso_romaneio(romaneio_recebimento.getPeso_liquido());

			recebimento_a_inserir.setCaminho_romaneio(url_final);

		} else {
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

	public void setProsseguir(boolean opcao) {
		this.prosseguir_mesmo_duplicado = opcao;
	}

}
