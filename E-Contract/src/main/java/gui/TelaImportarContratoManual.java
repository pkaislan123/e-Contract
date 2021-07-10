package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.JDialog;
import javax.swing.ListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

import org.codehaus.groovy.runtime.dgmimpl.arrays.BooleanArrayGetAtMetaMethod;



import javafx.stage.FileChooser;

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
import main.java.conexaoBanco.GerenciarBancoPontuacao;
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
import outros.ValidaCNPj;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;

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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class TelaImportarContratoManual extends JFrame {
	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private int tipoContrato_global;
	private JPanel painelDadosProdutos = new JPanel();
	private JPanel painelDadosProdutosPai = new JPanel();

	
	private JButton btnPesquisarComprador;
	private JPanel painelEmpresa = new JPanel();
	private JPanel painelFinalizar = new JPanel();
	private JComboBox cBComprador, cBFrete, cBArmazenagem;
	private JRadioButton rBPostoSobreRodas, rBJaDepositada;
	private ModeloPagamentoTableModel modelo_pagamentos = new ModeloPagamentoTableModel();

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
	private String diretorio_pasta_bkp1 = null,  diretorio_pasta_bkp2 = null;

	private JLabel mostrar_valor_total_contrato;
	private JPanel painel_table_cb;
	private JTable table_cb;
	private String codigo_antigo;
	private DefaultTableModel modelo = new DefaultTableModel();
	private DefaultTableModel modelo_cb = new DefaultTableModel();
	private JTextField entDataPagamento, entDataEntrega;
	private JTextField entValorPagamento;
	private JComboBox cBContaBancaria;
	private JComboBox cBSafra;
	private JComboBox cBLocalRetirada;

	private ComboBoxPersonalizado modelSafra = new ComboBoxPersonalizado();
	private CBLocalRetiradaPersonalizado modelLocalRetirada = new CBLocalRetiradaPersonalizado();
	private CBLocalRetiradaRenderPersonalizado cBLocalRetiradaPersonalizado;
	private JLabel lblStatusInicial;
	private ContaBancaria conta_atual;
	private JComboBox cBTipoContrato;
	private BigDecimal valor_total;
	private BigDecimal valor_atual = new BigDecimal("0");
	private BigDecimal valor_acumulado = new BigDecimal("0");

	private ArrayList<CadastroContrato.CadastroPagamento> pagamentos = null;
	private JTextField entComissao;

	private JLabel lblValorTotalComisao1, mostrar_valor_total_comissao;
	BigDecimal valor_total_comissao = new BigDecimal("0");
	BigDecimal valor_atual_comissao = new BigDecimal("0");

	private JLabel lblNomeComprador, lblCpfCnpj, lblEndereco, lblIE, lblBairro, lblEstado, lblMunicipio;
	private JLabel lblNomeVendedor, lblCpfCnpjVendedor, lblEnderecoVendedor, lblIEVendedor, lblBairroVendedor,
			lblEstadoVendedor, lblMunicipioVendedor;

	private JPanel painelDadosAdicionais = new JPanel();
	private CadastroSafra safra_selecionada;
	private JCheckBox chBoxClausula1, chBoxClausula2, chBoxClausula3, chBoxClausula4, chBoxClausula5, chBoxClausula6;

	private JRadioButton rdbtnSobreRodas, rdbtnAgendado, rdbtnAtoCarregamento, rdbtnAVista, rdbtnInfoFavorSim,
			rdbtnInfoFavorNao;

	private ComboBoxRenderPersonalizado cBSafraPersonalizado;
	private String unidadeGlobal = "quilogramas";
	private CadastroModelo modelo_global;

	private static ArrayList<CadastroCliente> armazens = new ArrayList<>();
	private JTextField textField;
	private JLabel lblData;
	private JTextField entDataContrato;

	private JCheckBox chbxDataAtual, chBoxClausulaComissao;
	private JLabel lbltext;

	private EditarExcel editar;
	private EditarWord editarWord;
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
	private JButton btnPesquisarCorretor, btnPesquisarVendedor1, btnPesquisarVendedor2;

	private JLabel lblNomeCorretor, lblIECorretor, lblBairroCorretor, lblEstadoCorretor, lblCpfCnpjCorretor,
			lblEnderecoCorretor, lblMunicipioCorretor;
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

	private JCheckBox chkBoxAntecipadoSim, chkBoxAntecipadoNao, chkBoxClausulaArmazenagem, chkBoxClausulaFrete;

    private String servidor_unidade;



	public static void pesquisarArmazens() {
		GerenciarBancoClientes listaArmazens = new GerenciarBancoClientes();
		armazens = listaArmazens.getClientes(-1, -1, null);

	}

	public static void pesquisarSafras() {
		GerenciarBancoSafras listaSafras = new GerenciarBancoSafras();
		safras = listaSafras.getSafras();
	}

	TelaImportarContratoManual isto = this;

	private int flag_edicao_global = -1;

	private ArrayList<Integer> pagamento_a_excluir = null;
	private JLabel lblValorRestante_1;
	private JLabel mostrar_soma_atual_restante;
	private JTextField entClausulaFrete;
	private JTextField entClausulaArmazenagem;
	private JPanel painelInfoTelaPrincipal;
	private JTextField entCodigoContrato;
	private JTable tabela_pagamentos_elaborados;
	private JLabel lblCodigoContratoInfo;
	private JLabel lblCorretorInfo;
	private JLabel lblCompradorInfo;
	private JLabel lblVendedor1Info;
	private JLabel lblVendedor2Info;
	private JLabel lblProdutoInfo;
	private JLabel lblTransgeniaInfo;
	private JLabel lblSafraInfo;
	private JLabel lblQuantidadeInfo;
	private JLabel lblUnidadeInfo;
	private JLabel lblValorUnidadeInfo;
	private JLabel lblValorTotalInfo;
	private JLabel lblComissaoInfo;
	private JLabel lblValorTotalComissaoInfo;
	private JLabel lblFreteInfo;
	private JLabel lblArmazenagemInfo;
	private JLabel lblNewLabel_1_3;
	private JLabel lblDataInfo;
	private JLabel lblNewLabel_1_1_1_1_1_1_2;
	private JLabel lblDataEntregaInfo;
	private JLabel lblNewLabel_1_1_1_1_1_1_1_1_3_2;
	private JLabel lblNewLabel_1_1_1_1_1_1_1_1_3_3;
	private JLabel lblLocalRetiradaInfo;
	private JLabel lblRecebimentoInfo;
	private JLabel lblNewLabel_4;
	private JLabel lblValorTotalJaPagoInfo;
	private JLabel lblNewLabel_5;
	private JLabel lblValorTotalHaPagarInfo;
	private File file_global;
	private JLabel lblTipoContrato;
	private JLabel lblTipo;
	private JButton btnPesquisarComprador_2;
	private JComboBox cBComprador2;
	private JLabel lblComprador_2;
	private JPanel painelDadosComprador2;
	private JLabel lblComprador_3;
	private JLabel lblCpfcnpj_4;
	private JLabel lblEndereo_5;
	private JLabel lblEndereo_6;
	private JLabel lblMunicipioComprador2;
	private JLabel lblEnderecoComprador2;
	private JLabel lblCpfCnpjComprador2;
	private JLabel lblNomeComprador2;
	private JLabel lblIEComprador2;
	private JLabel lblBairro2_4;
	private JLabel lblBairroComprador2;
	private JLabel lblEstadoComprador2;
	private JLabel lblEstado_3;
	private JLabel lblIe_4;
	private JLabel lblNewLabel_1_4;
	private JLabel lblCompradorInfo2;
	private JPanel panel_3;

	public TelaImportarContratoManual(int tipoContrato, CadastroContrato contrato_pai, int flag_edicao, File arquivo,
			Window janela_pai) {

		file_global = arquivo;
		getDadosGlobais();
		tipoContrato_global = tipoContrato;

		// setAlwaysOnTop(true);

		// setModal(true);
		isto = this;
		flag_edicao_global = flag_edicao;

		contrato_pai_local = contrato_pai;

		if (flag_edicao == 0) {
			// modo criacao
			if (tipoContrato == 4) {
				setTitle("E-Contract - Importação de Contrato Manual");
				novo_contrato.setSub_contrato(4);

			} else if (tipoContrato == 5) {
				setTitle("E-Contract - Importação de Sub-Contrato Manual");
				novo_contrato.setSub_contrato(5);

			}

		} else {
			// modo de edicao de contrato
			novo_contrato = contrato_pai;
			if (contrato_pai.getSub_contrato() == 4) {
				// é um contrato pai em modo edicao
				setTitle("E-Contract - Edição de Atributos de Contrato  Importado Manualmente");
			}else if(contrato_pai.getSub_contrato() == 5)
			{
				setTitle("E-Contract - Edição de Atributos de Sub-Contrato  Importado Manualmente");

			}
		}


		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		System.out.println("Screen width = " + dim.width);
		System.out.println("Screen height = " + dim.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();
		setBounds(0, 0, dim.width, dim.height - taskBarHeight);
		
		
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();

		getContentPane().add(painelPrincipal, BorderLayout.CENTER);
		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelDadosProdutos.setBackground(new Color(255, 255, 255));
		painelDadosAdicionais.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				try {
					CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();

					// entClausula2.setText(safra.getProduto().getNome_produto() +" acima de 14% de
					// umidade será cobrado uma taxa de despesas para a secagem");
					setClausula2(safra.getProduto().getNome_produto());
				} catch (Exception e) {

				}
			}
		});

		painelDadosAdicionais.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		painelPrincipal.addTab("Das Partes Envolvidas", painelDadosIniciais);

		// adiciona o painel de produtos nas abas
		//painelPrincipal.addTab("Do Produto", painelDadosProdutos);
		painelPrincipal.addTab("Do Produto", painelDadosProdutosPai);
		painelDadosProdutosPai.setLayout(new MigLayout("", "[grow][1345px,grow][grow]", "[grow][661px,grow]"));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelDadosProdutosPai.add(panel_5, "cell 1 0,grow");
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		painelDadosProdutosPai.add(panel_3, "cell 0 0 1 2,grow");
		
		painelDadosProdutosPai.add(painelDadosProdutos, "cell 1 1,grow");
		// adiciona o painel de informacoes adicionais nas abas
		painelPrincipal.addTab("Adicionais", painelDadosAdicionais);
		painelDadosAdicionais.setLayout(new MigLayout("", "[grow]", "[38px][grow]"));

		panel = new JPanel();

		panel.setBorder(UIManager.getBorder("TitledBorder.border"));
		panel.setBackground(Color.WHITE);
		painelDadosAdicionais.add(panel, "cell 0 1,growx,aligny center");
		panel.setLayout(new MigLayout("", "[143px][48px][681px][150px]", "[][28px][28px][28px][28px][18px][12px][1px][2px][28px][26px][2px][3px][2px][32px]"));
		
				JLabel lblClusulasAdicionais = new JLabel("Cláusulas Adicionais:");
				panel.add(lblClusulasAdicionais, "cell 0 0");
				lblClusulasAdicionais.setFont(new Font("SansSerif", Font.BOLD, 16));

		chBoxClausula1 = new JCheckBox("");
		panel.add(chBoxClausula1, "cell 1 1,alignx center,aligny center");
		chBoxClausula1.setSelected(true);
		chBoxClausula1.setFont(new Font("SansSerif", Font.BOLD, 14));
		chBoxClausula1.setEnabled(false);

		entClausula1 = new JTextField();
		entClausula1.setEnabled(false);
		panel.add(entClausula1, "cell 2 1,grow");
		entClausula1.setEditable(false);
		entClausula1.setText("A quantidade de quilogramas que exceder será negociado com o preço do dia.");
		entClausula1.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausula1.setColumns(10);

		entClausula2 = new JTextField();
		entClausula2.setEnabled(false);
		panel.add(entClausula2, "cell 2 2,grow");
		entClausula2.setEditable(false);
		entClausula2.setText("Produto acima de 14% de umidade será cobrado uma taxa de despesas para a secagem\r\n");
		entClausula2.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausula2.setColumns(10);

		chBoxClausula2 = new JCheckBox("");
		panel.add(chBoxClausula2, "cell 1 2,alignx center,aligny center");
		chBoxClausula2.setSelected(true);
		chBoxClausula2.setFont(new Font("SansSerif", Font.BOLD, 14));
		chBoxClausula2.setSelected(true);
		chBoxClausula2.setEnabled(false);

		chBoxClausula3 = new JCheckBox("");
		chBoxClausula3.setEnabled(false);
		panel.add(chBoxClausula3, "cell 1 3,alignx center,aligny center");
		chBoxClausula3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chBoxClausula3.isSelected()) {
					chBoxClausula3.setSelected(true);
					entClausula3.setEditable(true);
				} else {
					chBoxClausula3.setSelected(false);
					entClausula3.setEditable(false);
					entClausula3.setText("");

				}

			}
		});
		chBoxClausula3.setFont(new Font("SansSerif", Font.BOLD, 14));

		entClausula3 = new JTextField();
		panel.add(entClausula3, "cell 2 3,grow");
		entClausula3.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausula3.setColumns(10);

		entClausula4 = new JTextField();
		panel.add(entClausula4, "cell 2 4,grow");
		entClausula4.setEditable(false);
		entClausula4.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausula4.setColumns(10);

		chBoxClausula4 = new JCheckBox("");
		panel.add(chBoxClausula4, "cell 1 4,alignx center,aligny top");
		chBoxClausula4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chBoxClausula4.isSelected()) {
					chBoxClausula4.setSelected(true);
					entClausula4.setEditable(true);
				} else {
					chBoxClausula4.setSelected(false);
					entClausula4.setEditable(false);
					entClausula4.setText("");

				}

			}
		});
		chBoxClausula4.setFont(new Font("SansSerif", Font.BOLD, 14));

		chBoxClausula5 = new JCheckBox("");
		panel.add(chBoxClausula5, "cell 1 5,alignx center,aligny top");
		chBoxClausula5.setFont(new Font("SansSerif", Font.BOLD, 14));

		chBoxClausula6 = new JCheckBox("");
		panel.add(chBoxClausula6, "cell 1 7 1 3,alignx center,aligny top");
		chBoxClausula6.setFont(new Font("SansSerif", Font.BOLD, 14));

		entClausula5 = new JTextField();
		panel.add(entClausula5, "cell 2 5 1 3,growx,aligny top");
		entClausula5.setEditable(false);
		entClausula5.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausula5.setColumns(10);

		entClausula6 = new JTextField();
		panel.add(entClausula6, "cell 2 9,grow");
		entClausula6.setEditable(false);
		entClausula6.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausula6.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Clausula Frete:");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(lblNewLabel_3, "cell 0 10,alignx right,aligny center");

		JLabel lblNewLabel_3_1 = new JLabel("Clausula Armazenagem:");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel.add(lblNewLabel_3_1, "cell 0 12 1 3,alignx right,aligny top");

		cBFrete = new JComboBox();
		cBFrete.setEnabled(false);
		cBFrete.addItem("Comprador");
		cBFrete.addItem("Vendedor");

		panel.add(cBFrete, "cell 3 10,growx,aligny top");

		cBArmazenagem = new JComboBox();
		cBArmazenagem.setEnabled(false);
		cBArmazenagem.addItem("Comprador");
		cBArmazenagem.addItem("Vendedor");
		panel.add(cBArmazenagem, "cell 3 14,growx,aligny top");

		chkBoxClausulaFrete = new JCheckBox("");
		chkBoxClausulaFrete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (chkBoxClausulaFrete.isSelected()) {

					chkBoxClausulaFrete.setSelected(true);
					entClausulaFrete.setEnabled(true);
					entClausulaFrete.setEditable(true);
					cBFrete.setEnabled(true);

				} else {
					chkBoxClausulaFrete.setSelected(false);
					entClausulaFrete.setEnabled(false);
					entClausulaFrete.setEditable(false);

					cBFrete.setEnabled(false);
				}

			}
		});
		chkBoxClausulaFrete.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel.add(chkBoxClausulaFrete, "cell 1 10,alignx center,aligny center");

		chkBoxClausulaArmazenagem = new JCheckBox("");
		chkBoxClausulaArmazenagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkBoxClausulaArmazenagem.isSelected()) {
					chkBoxClausulaArmazenagem.setSelected(true);
					entClausulaArmazenagem.setEnabled(true);
					entClausulaArmazenagem.setEditable(true);
					cBArmazenagem.setEnabled(true);

				} else {
					chkBoxClausulaArmazenagem.setSelected(false);
					entClausulaArmazenagem.setEnabled(false);
					entClausulaArmazenagem.setEditable(false);
					cBArmazenagem.setEnabled(false);

				}
			}
		});
		chkBoxClausulaArmazenagem.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel.add(chkBoxClausulaArmazenagem, "cell 1 14,alignx center,aligny center");

		entClausulaFrete = new JTextField();
		entClausulaFrete.setEnabled(false);
		entClausulaFrete.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausulaFrete.setEditable(false);
		entClausulaFrete.setColumns(10);
		panel.add(entClausulaFrete, "cell 2 10 1 3,growx,aligny top");

		entClausulaArmazenagem = new JTextField();
		entClausulaArmazenagem.setEnabled(false);
		entClausulaArmazenagem.setFont(new Font("SansSerif", Font.BOLD, 14));
		entClausulaArmazenagem.setEditable(false);
		entClausulaArmazenagem.setColumns(10);
		panel.add(entClausulaArmazenagem, "cell 2 14,growx,aligny bottom");
		chBoxClausula6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chBoxClausula6.isSelected()) {
					chBoxClausula6.setSelected(true);
					entClausula6.setEditable(true);

				} else {
					chBoxClausula6.setSelected(false);
					entClausula6.setEditable(false);
					entClausula6.setText("");

				}

			}
		});
		chBoxClausula5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chBoxClausula5.isSelected()) {
					chBoxClausula5.setSelected(true);
					entClausula5.setEditable(true);
				} else {
					chBoxClausula5.setSelected(false);
					entClausula5.setEditable(false);
					entClausula5.setText("");

				}

			}
		});
		painelDadosProdutos.setLayout(null);

		JLabel lblDoProduto = new JLabel("Do produto");
		lblDoProduto.setBounds(207, 7, 247, 42);
		lblDoProduto.setFont(new Font("Arial Black", Font.BOLD, 14));
		painelDadosProdutos.add(lblDoProduto);

		JLabel lblSafra = new JLabel("Safra:");
		lblSafra.setBounds(380, 53, 44, 57);
		lblSafra.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblSafra);

		JLabel lblLocalRetirada = new JLabel("Local Retirada:");
		lblLocalRetirada.setBounds(275, 105, 149, 50);
		lblLocalRetirada.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblLocalRetirada);

		cBLocalRetiradaPersonalizado = new CBLocalRetiradaRenderPersonalizado();
		cBLocalRetirada = new JComboBox();
		cBLocalRetirada.setBounds(441, 105, 191, 50);
		cBLocalRetirada.setModel(modelLocalRetirada);
		cBLocalRetirada.setEditable(false);
		cBLocalRetirada.setRenderer(cBLocalRetiradaPersonalizado);

		painelDadosProdutos.add(cBLocalRetirada);

		pesquisarArmazens();

		for (CadastroCliente armazem : armazens) {
			if (armazem.getArmazem() == 1) {
				modelLocalRetirada.addArmazem(armazem);

			}

		}
		


		JLabel lblDataEntrega = new JLabel("Data Entrega:");
		lblDataEntrega.setBounds(321, 159, 103, 42);
		lblDataEntrega.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblDataEntrega);

		entDataEntrega = new JTextField();
		entDataEntrega.setBounds(441, 159, 191, 42);
		entDataEntrega.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent pp) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entDataEntrega.getText();
				if (!caracteres.contains(pp.getKeyChar() + "")) {
					pp.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && pp.getKeyChar() != '\b') {
						entDataEntrega.setText(entDataEntrega.getText().concat("/"));
					}
					if (texto.length() == 5 && pp.getKeyChar() != '\b') {
						entDataEntrega.setText(entDataEntrega.getText().concat("/"));
					}

					if (entDataEntrega.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						pp.consume();
						entDataEntrega.setText(entDataEntrega.getText().substring(0, 10));
					}

				}
			}
		});
		entDataEntrega.setColumns(10);
		painelDadosProdutos.add(entDataEntrega);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(18, 264, 91, 62);
		lblQuantidade.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblQuantidade);

		entQuantidade = new JTextField();
		entQuantidade.setBounds(131, 279, 190, 47);
		entQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent o) {

				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Spreco, quantidade;
				quantidade = entQuantidade.getText();

				if (!caracteres.contains(o.getKeyChar() + "")) {
					o.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					quantidade = entQuantidade.getText() + o.getKeyChar();
					quantidade = quantidade.replaceAll("[^0-9]+", "");
				}

				Spreco = entPreco.getText();

				BigDecimal quant = null;
				BigDecimal valor = null;

				System.out.println("preco e " + Spreco);

				System.out.println("quantidade e " + quantidade);

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
				{
					try {
						quant = new BigDecimal(quantidade);

						BigDecimal preco = new BigDecimal(Spreco);

						valor = quant.multiply(preco);
						valor_total = valor;
						valor_atual = valor_total;
						// valor_atual = valor_total.subtract(valor_atual);

						String valorTotal = valor.toPlainString();
						lblValorTotal.setText("R$ " + valorTotal);
						mostrar_valor_total_contrato.setText("R$ " + valorTotal);
						lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());

					} catch (NumberFormatException n) {
						System.out.println("preco e nulo " + n.getCause());

						BigDecimal preco = new BigDecimal("1.0");
						try {
							valor = quant.multiply(preco);
							String valorTotal = valor.toPlainString();
							lblValorTotal.setText("R$ " + valorTotal);
							mostrar_valor_total_contrato.setText("R$ " + valorTotal);
							valor_total = valor;
							valor_atual = valor_total;
							lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());

							// valor_atual = valor_total.subtract(valor_atual);

						} catch (NullPointerException l) {
							System.out.println("quant e nulo " + l.getCause());
							lblValorTotal.setText("");
							mostrar_valor_total_contrato.setText("");

						}

					}
				}

			}
		});
		entQuantidade.setColumns(10);
		painelDadosProdutos.add(entQuantidade);

		JLabel lblPreo = new JLabel("Preço:");
		lblPreo.setBounds(67, 335, 48, 50);
		lblPreo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblPreo);

		entPreco = new JTextField();
		entPreco.setBounds(131, 346, 190, 39);
		entPreco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent p) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Spreco, quantidade;
				Spreco = entPreco.getText();

				if (!caracteres.contains(p.getKeyChar() + "")) {
					p.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Spreco = entPreco.getText() + p.getKeyChar();
					Spreco = Spreco.replaceAll("[^0-9.]", "");
				}

				quantidade = entQuantidade.getText();

				BigDecimal preco = null;
				BigDecimal valor = null;

				System.out.println("preco e " + Spreco);

				System.out.println("quantidade e " + quantidade);

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
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
						// valor_atual = valor_total.subtract(valor_atual);
						lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());

					} catch (NumberFormatException q) {
						System.out.println("preco e nulo " + q.getCause());

						BigDecimal quant = new BigDecimal("1.0");
						try {
							valor = preco.multiply(quant);
							String valorTotal = valor.toPlainString();
							lblValorTotal.setText("R$ " + valorTotal);
							mostrar_valor_total_contrato.setText("R$ " + valorTotal);
							valor_total = valor;
							valor_atual = valor_total;
							// valor_atual = valor_total.subtract(valor_atual);
							lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());

						} catch (NullPointerException r) {
							System.out.println("quant e nulo " + r.getCause());
							lblValorTotal.setText("");
							mostrar_valor_total_contrato.setText("");

						}

					}
				}

			}
		});
		entPreco.setColumns(10);
		painelDadosProdutos.add(entPreco);

		rQuanKG = new JRadioButton("KG");
		rQuanKG.setBounds(131, 224, 58, 23);
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
		painelDadosProdutos.add(rQuanKG);

		rQuanS = new JRadioButton("SC");
		rQuanS.setBounds(207, 224, 47, 23);
		rQuanS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					double quantidade_atual = Double.parseDouble(entQuantidade.getText());
					quantidade_atual = quantidade_atual / 60;
					entQuantidade.setText(Double.toString(quantidade_atual));

					rQuanS.setSelected(true);
				}
			}
		});
		rQuanS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rQuanKG.setSelected(false);
				rQuanT.setSelected(false);
				rQuanS.setSelected(true);

				lblQuant.setText("Sacos");
				lblPreco.setText("por Saco");
				setClausula1("sacos");

				unidadeGlobal = "sacos";
				setClausulaComissao(entComissao.getText().toString().replace(".", ","));

			}
		});
		painelDadosProdutos.add(rQuanS);

		rQuanT = new JRadioButton("TON");
		rQuanT.setBounds(275, 224, 66, 23);
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
		painelDadosProdutos.add(rQuanT);

		lblQuant = new JLabel("Quilos");
		lblQuant.setBounds(350, 279, 74, 17);
		lblQuant.setEnabled(false);
		lblQuant.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelDadosProdutos.add(lblQuant);

		lblPreco = new JLabel("Quilo");
		lblPreco.setBounds(340, 346, 157, 17);
		lblPreco.setEnabled(false);
		lblPreco.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelDadosProdutos.add(lblPreco);

		JLabel lblvalortotal = new JLabel("Valor Total:");
		lblvalortotal.setBounds(30, 400, 89, 42);
		lblvalortotal.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblvalortotal);

		lblValorTotal = new JLabel("");
		lblValorTotal.setBounds(7, 205, 182, 28);
		lblValorTotal.setEnabled(false);
		lblValorTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelDadosProdutos.add(lblValorTotal);
		painelDadosIniciais.setLayout(new MigLayout("", "[632px,grow][2px][4px][689px,grow]", "[175px,grow][18px,grow][68px,grow][11px,grow][61px,grow][18px,grow][89px,grow][28px,grow][23px,grow][19px,grow][137px,grow]"));

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		painelDadosIniciais.add(separator_2, "cell 1 0,alignx left,aligny center");

		JPanel painelDadosComprador = new JPanel();
		painelDadosComprador.setBorder(new CompoundBorder());
		painelDadosComprador.setBackground(SystemColor.text);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		TitledBorder title = BorderFactory.createTitledBorder(lineBorder, "Comprador 1");
		painelDadosComprador.setBorder(title);

		painelDadosIniciais.add(painelDadosComprador, "cell 0 6 1 3,grow");
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
		Border lineBorder2 = BorderFactory.createLineBorder(Color.black);
		TitledBorder title2 = BorderFactory.createTitledBorder(lineBorder2, "Vendedor");
		panelDadosVendedor.setBorder(title2);

		painelDadosIniciais.add(panelDadosVendedor, "cell 1 4 3 3,grow");
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
		painelDadosCodigoData.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelDadosCodigoData.setBackground(SystemColor.text);
		painelDadosIniciais.add(painelDadosCodigoData, "cell 0 0,grow");
		painelDadosCodigoData.setLayout(null);

		lblData = new JLabel("Data:");
		lblData.setBounds(52, 76, 52, 42);
		painelDadosCodigoData.add(lblData);
		lblData.setFont(new Font("Arial Black", Font.PLAIN, 14));

		entDataContrato = new JTextField();
		entDataContrato.setBounds(114, 85, 175, 29);
		painelDadosCodigoData.add(entDataContrato);
		entDataContrato.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entDataContrato.getText();
				if (!caracteres.contains(evt.getKeyChar() + "")) {
					evt.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && evt.getKeyChar() != '\b') {
						entDataContrato.setText(entDataContrato.getText().concat("/"));
					}
					if (texto.length() == 5 && evt.getKeyChar() != '\b') {
						entDataContrato.setText(entDataContrato.getText().concat("/"));
					}

					if (entDataContrato.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						evt.consume();
						entDataContrato.setText(entDataContrato.getText().substring(0, 10));
					}

				}
			}
		});
		entDataContrato.setColumns(10);

		// pega a data atual e salva no entDataContrato

		entDataContrato.setText(pegarData());
		entDataContrato.setEditable(false);

		chbxDataAtual = new JCheckBox("Data Atual");
		chbxDataAtual.setBounds(115, 54, 89, 23);
		painelDadosCodigoData.add(chbxDataAtual);
		chbxDataAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbxDataAtual.isSelected()) {
					chbxDataAtual.setSelected(true);
					entDataContrato.setText(pegarData());
					entDataContrato.setEditable(false);
				} else {
					chbxDataAtual.setSelected(false);
					entDataContrato.setEditable(true);

				}

			}
		});
		chbxDataAtual.setSelected(true);

		lbltext = new JLabel("Código:");
		lbltext.setBounds(42, 6, 70, 42);
		painelDadosCodigoData.add(lbltext);
		lbltext.setFont(new Font("Arial Black", Font.PLAIN, 14));

		entCodigoContrato = new JTextField();
		entCodigoContrato.setFont(new Font("Tahoma", Font.BOLD, 14));
		entCodigoContrato.setBounds(117, 19, 172, 23);
		painelDadosCodigoData.add(entCodigoContrato);
		entCodigoContrato.setColumns(10);

		lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblTipo.setBounds(52, 119, 52, 42);
		painelDadosCodigoData.add(lblTipo);

		cBTipoContrato = new JComboBox();
		cBTipoContrato.setFont(new Font("Tahoma", Font.BOLD, 14));
		cBTipoContrato.setBounds(114, 131, 270, 27);
		painelDadosCodigoData.add(cBTipoContrato);
		if(tipoContrato == 4) {
		cBTipoContrato.addItem("Contrato Original de Terceiros");
		cBTipoContrato.addItem("Contrato Original Proprietario");
		}else if(tipoContrato == 5) {
			cBTipoContrato.addItem("Sub-Contrato Original de Terceiros");
			cBTipoContrato.addItem("Sub-Contrato Original Proprietario");
		}

		painelDefinirPartes = new JPanel();
		painelDefinirPartes.setBackground(SystemColor.text);

		Border lineBorder1 = BorderFactory.createLineBorder(Color.black);
		TitledBorder title1 = BorderFactory.createTitledBorder(lineBorder, "");
		painelDefinirPartes.setBorder(title1);

		painelDadosIniciais.add(painelDefinirPartes, "cell 3 0 1 3,grow");
		painelDefinirPartes.setLayout(null);

		JLabel lblNewLabel = new JLabel("Comprador 1:");
		lblNewLabel.setBounds(14, 64, 101, 21);
		painelDefinirPartes.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));

		JLabel lblVendedor = new JLabel("Vendedor 1:");
		lblVendedor.setBounds(25, 149, 90, 21);
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					TelaCliente clientes = new TelaCliente(0, 2, isto);
					clientes.setTelaPai(isto);
					clientes.setVisible(true);
				}
			}
		});
		cBVendedor1.setBounds(118, 146, 268, 29);
		cBVendedor1.addItem("Indefinido");
		painelDefinirPartes.add(cBVendedor1);

		cBVendedor1.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (cBVendedor1.getSelectedItem().equals("Indefinido")) {
						lblNomeVendedor.setText("");
						lblCpfCnpjVendedor.setText("");
						lblIEVendedor.setText("");
						lblEnderecoVendedor.setText("");
						lblBairroVendedor.setText("");
						lblMunicipioVendedor.setText("");
						lblEstadoVendedor.setText("");
						novo_contrato.adicionarCorretor(0, null);
						cBVendedor1.removeAllItems();

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
					if (evt.getItem().equals("Indefinido")) {

						lblNomeComprador.setText("");
						lblCpfCnpj.setText("");
						lblIE.setText("");
						lblEndereco.setText("");
						lblBairro.setText("");
						lblMunicipio.setText("");
						lblEstado.setText("");
						// cBComprador.addItem("Indefinido");
						novo_contrato.adicionarComprador(0, null);
						cBComprador.removeAllItems();
					}
				}

			}

		});

		cBComprador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					TelaCliente clientes = new TelaCliente(0, 1, isto);
					clientes.setTelaPai(isto);
					clientes.setVisible(true);
				}

			}
		});

		btnPesquisarComprador = new JButton("Pesquisar Comprador 1");
		btnPesquisarComprador.setBounds(396, 60, 220, 33);
		painelDefinirPartes.add(btnPesquisarComprador);

		btnPesquisarVendedor1 = new JButton("Pesquisar Vendedor 1");
		btnPesquisarVendedor1.setBounds(398, 145, 218, 33);
		painelDefinirPartes.add(btnPesquisarVendedor1);

		lblCorretor = new JLabel("Corretor:");
		lblCorretor.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblCorretor.setBounds(44, 12, 76, 42);
		painelDefinirPartes.add(lblCorretor);

		cBCorretor = new JComboBox();
		cBCorretor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					TelaCliente clientes = new TelaCliente(0, 4, isto);
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
					if (cBCorretor.getSelectedItem().equals("Indefinido")) {
						lblNomeCorretor.setText("");
						lblCpfCnpjCorretor.setText("");
						lblIECorretor.setText("");
						lblEnderecoCorretor.setText("");
						lblBairroCorretor.setText("");
						lblMunicipioCorretor.setText("");
						lblEstadoCorretor.setText("");
						novo_contrato.adicionarCorretor(0, null);
						cBCorretor.removeAllItems();
					}
				}

			}

		});

		painelDefinirPartes.add(cBCorretor);

		JLabel lblVendedor2 = new JLabel("Vendedor 2:");
		lblVendedor2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblVendedor2.setBounds(25, 190, 90, 21);
		painelDefinirPartes.add(lblVendedor2);

		cBVendedor2 = new JComboBox();
		cBVendedor2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					TelaCliente clientes = new TelaCliente(0, 3, isto);
					clientes.setTelaPai(isto);
					clientes.setVisible(true);
				}
			}
		});
		cBVendedor2.setBounds(118, 187, 268, 29);
		cBVendedor2.addItem("Indefinido");

		painelDefinirPartes.add(cBVendedor2);
		cBVendedor2.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (cBVendedor2.getSelectedItem().equals("Indefinido")) {
						lblNomeVendedor2.setText("");
						lblCpfCnpjVendedor2.setText("");
						lblIEVendedor2.setText("");
						lblEnderecoVendedor2.setText("");
						lblBairroVendedor2.setText("");
						lblMunicipioVendedor2.setText("");
						lblEstadoVendedor2.setText("");

						novo_contrato.adicionarVendedor(1, null);
						cBVendedor2.removeAllItems();

					}
				}

			}

		});
		btnPesquisarVendedor2 = new JButton("Pesquisar Vendedor 2");
		btnPesquisarVendedor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente clientes = new TelaCliente(0, 15, isto);
				clientes.setTelaPai(isto);
				clientes.setVisible(true);

			}
		});
		btnPesquisarVendedor2.setBounds(398, 184, 218, 33);
		painelDefinirPartes.add(btnPesquisarVendedor2);

		btnPesquisarCorretor = new JButton("Pesquisar Corretor");
		btnPesquisarCorretor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente clientes = new TelaCliente(0, 12, isto);
				clientes.setTelaPai(isto);
				clientes.setVisible(true);

			}
		});
		btnPesquisarCorretor.setBounds(396, 18, 220, 33);
		painelDefinirPartes.add(btnPesquisarCorretor);

		btnPesquisarComprador_2 = new JButton("Pesquisar Comprador 2");
		btnPesquisarComprador_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente clientes = new TelaCliente(0, 20, isto);
				clientes.setTelaPai(isto);
				clientes.setVisible(true);
			}
		});
		btnPesquisarComprador_2.setBounds(396, 101, 220, 33);
		painelDefinirPartes.add(btnPesquisarComprador_2);

		cBComprador2 = new JComboBox();
		cBComprador2.addItem("Indefinido");

		cBComprador2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					TelaCliente clientes = new TelaCliente(0, 20, isto);
					clientes.setTelaPai(isto);
					clientes.setVisible(true);
				}
			}
		});
		cBComprador2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (e.getItem().equals("Indefinido")) {

						lblNomeComprador2.setText("");
						lblCpfCnpjComprador2.setText("");
						lblIEComprador2.setText("");
						lblEnderecoComprador2.setText("");
						lblBairroComprador2.setText("");
						lblMunicipioComprador2.setText("");
						lblEstadoComprador2.setText("");
						// cBComprador.addItem("Indefinido");
						novo_contrato.adicionarComprador(1, null);

						cBComprador2.removeAllItems();

					}
				}
			}
		});
		cBComprador2.setBounds(118, 102, 268, 29);
		painelDefinirPartes.add(cBComprador2);

		lblComprador_2 = new JLabel("Comprador 2:");
		lblComprador_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblComprador_2.setBounds(14, 106, 101, 21);
		painelDefinirPartes.add(lblComprador_2);

		panelDadosVendedor2 = new JPanel();
		panelDadosVendedor2.setLayout(null);
		panelDadosVendedor2.setBackground(Color.WHITE);
		Border lineBorder3 = BorderFactory.createLineBorder(Color.black);
		TitledBorder title3 = BorderFactory.createTitledBorder(lineBorder3, "Vendedor");
		panelDadosVendedor2.setBorder(title3);
		painelDadosIniciais.add(panelDadosVendedor2, "cell 1 8 3 3,grow");

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
		Border lineBorderCorretor = BorderFactory.createLineBorder(Color.black);
		TitledBorder titleCorretor = BorderFactory.createTitledBorder(lineBorder, "Corretor");
		painelDadosCorretor.setBorder(titleCorretor);
		painelDadosIniciais.add(painelDadosCorretor, "cell 0 2 1 3,grow");

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

		painelDadosComprador2 = new JPanel();
		painelDadosComprador2.setLayout(null);
		painelDadosComprador2.setBorder(new CompoundBorder());
		painelDadosComprador2.setBackground(Color.WHITE);
		painelDadosIniciais.add(painelDadosComprador2, "cell 0 10,grow");

		Border lineBorderComprador2 = BorderFactory.createLineBorder(Color.black);
		TitledBorder titleComprador2 = BorderFactory.createTitledBorder(lineBorderComprador2, "Comprador 2");
		painelDadosComprador2.setBorder(titleComprador2);

		lblComprador_3 = new JLabel("Nome:");
		lblComprador_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblComprador_3.setBounds(43, 36, 83, 14);
		painelDadosComprador2.add(lblComprador_3);

		lblCpfcnpj_4 = new JLabel("CPF/CNPJ:");
		lblCpfcnpj_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpfcnpj_4.setBounds(16, 62, 83, 14);
		painelDadosComprador2.add(lblCpfcnpj_4);

		lblEndereo_5 = new JLabel("Endereço:");
		lblEndereo_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereo_5.setBounds(26, 87, 83, 14);
		painelDadosComprador2.add(lblEndereo_5);

		lblEndereo_6 = new JLabel("Municipio:");
		lblEndereo_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereo_6.setBounds(29, 114, 70, 14);
		painelDadosComprador2.add(lblEndereo_6);

		lblMunicipioComprador2 = new JLabel("");
		lblMunicipioComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMunicipioComprador2.setBounds(99, 114, 205, 16);
		painelDadosComprador2.add(lblMunicipioComprador2);

		lblEnderecoComprador2 = new JLabel("");
		lblEnderecoComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnderecoComprador2.setBounds(99, 87, 205, 16);
		painelDadosComprador2.add(lblEnderecoComprador2);

		lblCpfCnpjComprador2 = new JLabel("");
		lblCpfCnpjComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpfCnpjComprador2.setBounds(98, 60, 206, 16);
		painelDadosComprador2.add(lblCpfCnpjComprador2);

		lblNomeComprador2 = new JLabel("");
		lblNomeComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeComprador2.setBounds(113, 33, 448, 16);
		painelDadosComprador2.add(lblNomeComprador2);

		lblIEComprador2 = new JLabel("");
		lblIEComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIEComprador2.setBounds(367, 60, 194, 16);
		painelDadosComprador2.add(lblIEComprador2);

		lblBairro2_4 = new JLabel("Bairro:");
		lblBairro2_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBairro2_4.setBounds(315, 87, 83, 14);
		painelDadosComprador2.add(lblBairro2_4);

		lblBairroComprador2 = new JLabel("");
		lblBairroComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBairroComprador2.setBounds(367, 87, 194, 16);
		painelDadosComprador2.add(lblBairroComprador2);

		lblEstadoComprador2 = new JLabel("");
		lblEstadoComprador2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstadoComprador2.setBounds(367, 112, 194, 16);
		painelDadosComprador2.add(lblEstadoComprador2);

		lblEstado_3 = new JLabel("Estado:");
		lblEstado_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstado_3.setBounds(315, 112, 47, 14);
		painelDadosComprador2.add(lblEstado_3);

		lblIe_4 = new JLabel("IE:");
		lblIe_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIe_4.setBounds(332, 62, 27, 14);
		painelDadosComprador2.add(lblIe_4);

		btnPesquisarVendedor1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente clientes = new TelaCliente(0, 14, null);
				clientes.setTelaPai(isto);
				clientes.setVisible(true);
			}
		});
		btnPesquisarComprador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente clientes = new TelaCliente(0, 13, null);
				clientes.setTelaPai(isto);
				clientes.setVisible(true);
			}
		});

		JLabel lblComissao = new JLabel("Comissão:");
		lblComissao.setBounds(535, 205, 97, 77);
		lblComissao.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblComissao);

		rBComissaoSim = new JRadioButton("Sim");
		rBComissaoSim.setBounds(645, 237, 64, 23);
		rBComissaoSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setComissao();

			}
		});
		painelDadosProdutos.add(rBComissaoSim);

		rBComissaoNao = new JRadioButton("Não");
		rBComissaoNao.setBounds(734, 237, 58, 23);

		rBComissaoNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				retirarComissao();
			}
		});
		rBComissaoNao.setSelected(true);
		painelDadosProdutos.add(rBComissaoNao);

		entComissao = new JTextField();
		entComissao.setBounds(630, 292, 194, 50);
		entComissao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent p) {

				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Spreco, quantidade;
				Spreco = entComissao.getText();

				if (!caracteres.contains(p.getKeyChar() + "")) {
					p.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Spreco = entComissao.getText() + p.getKeyChar();
					Spreco = Spreco.replaceAll("[^0-9.]", "");
				}

				quantidade = entQuantidade.getText();

				BigDecimal comissao = null;
				BigDecimal valor = null;

				System.out.println("comisao e " + Spreco);

				System.out.println("quantidade e " + quantidade);

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
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

					} catch (NumberFormatException q) {
						System.out.println("comissao e nulo " + q.getCause());

						BigDecimal quant = new BigDecimal("1.0");
						try {
							valor = comissao.multiply(quant);
							String valorTotal = valor.toPlainString();
							lblValorTotalComisao1.setText("R$ " + valorTotal);
							mostrar_valor_total_comissao.setText("R$ " + valorTotal);
							valor_total_comissao = valor;
							valor_atual = valor_total;
							// valor_atual = valor_total.subtract(valor_atual);

						} catch (NullPointerException r) {
							System.out.println("quant e nulo " + r.getCause());
							lblValorTotalComisao1.setText("");
							mostrar_valor_total_comissao.setText("");

						}

					}
				}

			}
		});
		entComissao.setColumns(10);
		painelDadosProdutos.add(entComissao);

		JLabel lblValorTotal_1 = new JLabel("Valor Total:");
		lblValorTotal_1.setBounds(535, 384, 91, 21);
		lblValorTotal_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblValorTotal_1);

		lblValorTotalComisao1 = new JLabel("");
		lblValorTotalComisao1.setBounds(630, 346, 2, 96);
		lblValorTotalComisao1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValorTotalComisao1.setEnabled(false);
		painelDadosProdutos.add(lblValorTotalComisao1);

		entComissao.setEditable(false);
		entComissao.setEnabled(false);

		JLabel lblValorTotal_1_1 = new JLabel("VAlor Comissão:");
		lblValorTotal_1_1.setBounds(484, 292, 148, 50);
		lblValorTotal_1_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		painelDadosProdutos.add(lblValorTotal_1_1);
		painelEmpresa.setBackground(new Color(255, 255, 255));
		painelPrincipal.addTab("Do Pagamento", painelEmpresa);
		painelEmpresa.setLayout(null);

		cBSafraPersonalizado = new ComboBoxRenderPersonalizado();
		cBSafra = new JComboBox();
		cBSafra.setBounds(441, 53, 383, 33);
		cBSafra.setModel(modelSafra);
		cBSafra.setEditable(false);

		cBSafra.setRenderer(cBSafraPersonalizado);

		painelDadosProdutos.add(cBSafra);

		chBoxClausulaComissao = new JCheckBox("Criar clausula contratual para comissão");
		chBoxClausulaComissao.setBounds(841, 303, 242, 23);
		chBoxClausulaComissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!chBoxClausulaComissao.isSelected()) {
					chBoxClausulaComissao.setSelected(false);
					try {
						entClausula3.setText("");
						chBoxClausula3.setSelected(false);
						novo_contrato.setClausula_comissao(0);
					} catch (Exception re) {

					}
				} else {
					chBoxClausulaComissao.setSelected(true);
					try {
						setClausulaComissao(entComissao.getText().toString().replace(".", ","));
						chBoxClausula3.setSelected(true);
						novo_contrato.setClausula_comissao(1);

					} catch (Exception re) {

					}

				}

			}
		});
		painelDadosProdutos.add(chBoxClausulaComissao);

		pesquisarSafras();

		for (CadastroSafra safra : safras) {

			// cBSafra.addItem(safra.getProduto().getNome_produto() + " " +
			// safra.getAno_plantio() + "/" + safra.getAno_colheita());
			// cBSafra.addItem(safra);
			modelSafra.addSafra(safra);

		}
		

		JLabel lblOutro = new JLabel("Valor Total:");
		lblOutro.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblOutro.setBounds(50, 32, 89, 42);
		painelEmpresa.add(lblOutro);

		mostrar_valor_total_contrato = new JLabel("");
		mostrar_valor_total_contrato.setFont(new Font("Tahoma", Font.BOLD, 16));
		mostrar_valor_total_contrato.setBounds(142, 32, 177, 42);
		painelEmpresa.add(mostrar_valor_total_contrato);

		painel_table_cb = new JPanel();
		painel_table_cb.setBounds(102, 94, 1109, 151);

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
		modelo_cb.addColumn("Antecipado");
		modelo_cb.addColumn("Cobre");

		modelo_cb.addColumn("Data Pagamento");

		table_cb.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_cb.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_cb.getColumnModel().getColumn(1).setPreferredWidth(20);
		table_cb.getColumnModel().getColumn(3).setPreferredWidth(130);
		table_cb.getColumnModel().getColumn(4).setPreferredWidth(30);
		table_cb.getColumnModel().getColumn(5).setPreferredWidth(30);
		table_cb.getColumnModel().getColumn(6).setPreferredWidth(100);
		table_cb.getColumnModel().getColumn(8).setPreferredWidth(100);

		painel_table_cb.setLayout(null);
		modelo_cb.setNumRows(0);
		JScrollPane scrollPaneCB = new JScrollPane(table_cb);
		scrollPaneCB.setBounds(10, 5, 1089, 127);
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
		btnPesquisarCB.setBackground(new Color(0, 0, 51));
		btnPesquisarCB.setForeground(Color.WHITE);
		btnPesquisarCB.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnPesquisarCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaContaBancaria tela = new TelaContaBancaria(isto);
				tela.setTelaPai(isto);
				tela.setVisible(true);
			}
		});
		btnPesquisarCB.setBounds(395, 343, 105, 33);
		painelEmpresa.add(btnPesquisarCB);

		JLabel lblDataPagamento = new JLabel("Data Pagamento:");
		lblDataPagamento.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblDataPagamento.setBounds(102, 455, 135, 42);
		painelEmpresa.add(lblDataPagamento);

		entDataPagamento = new JTextField();
		entDataPagamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg) {
				String caracteres = "0987654321\b";// lista de caracters que não devem ser aceitos
				String texto = entDataPagamento.getText();
				if (!caracteres.contains(arg.getKeyChar() + "")) {
					arg.consume();// aciona esse propriedade para eliminar a ação do evento
				} else {
					if (texto.length() == 2 && arg.getKeyChar() != '\b') {
						entDataPagamento.setText(entDataPagamento.getText().concat("/"));
					}
					if (texto.length() == 5 && arg.getKeyChar() != '\b') {
						entDataPagamento.setText(entDataPagamento.getText().concat("/"));
					}

					if (entDataPagamento.getText().length() >= 10) {
						// if para saber se precisa verificar também o tamanho da string do campo
						// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
						arg.consume();
						entDataPagamento.setText(entDataPagamento.getText().substring(0, 10));
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

				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Svalor_pagamento = "";
				BigDecimal valor_pagamento = null;
				BigDecimal valor_restante = null;

				if (!caracteres.contains(a.getKeyChar() + "")) {
					a.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Svalor_pagamento = entValorPagamento.getText() + a.getKeyChar();
					Svalor_pagamento = Svalor_pagamento.replaceAll("[^0-9.]", "");
				}

				System.out.println("Valor pagamento e :" + Svalor_pagamento);

				System.out.println("valor atual e :" + valor_total.subtract(valor_acumulado).toPlainString());

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
				{
					try {
						valor_pagamento = new BigDecimal(Svalor_pagamento);

						valor_restante = valor_total.subtract(valor_acumulado).subtract(valor_pagamento);

						String Svalor_restante = valor_restante.toPlainString();
						// lblValorRestante.setText(Svalor_restante);
						lblValorRestante.setText("R$ " + Svalor_restante);

					} catch (NumberFormatException b) {
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
		btnAdicionarPag.setBackground(new Color(0, 51, 51));
		btnAdicionarPag.setForeground(Color.WHITE);
		btnAdicionarPag.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAdicionarPag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, data_pagamento, antecipado,
						cobertura = "";

				int i_antecipado = -1;
				valor_pagamento = entValorPagamento.getText().toString();

				if (chkBoxAntecipadoSim.isSelected()) {
					// é um pagamento antecipado
					antecipado = "SIM";
					i_antecipado = 1;

				} else {
					antecipado = "NÃO";
					i_antecipado = 0;

				}

				// calculo de cobertura de peso
				/*
				 *
				 * cobertura = quantidade * valor_pagamento quantidade * preco_por_unidade
				 * 
				 * 
				 * 
				 * 
				 */

				// processa dados de data de pagamento

				if (rdbtnAVista.isSelected()) {
					data_pagamento = "A Vista";

				} else if (rdbtnSobreRodas.isSelected()) {
					data_pagamento = "Sobre Rodas";

				} else if (rdbtnAtoCarregamento.isSelected()) {
					data_pagamento = "Ato Carregamento";

				} else {
					data_pagamento = entDataPagamento.getText();
				}

				if (rdbtnInfoFavorNao.isSelected()) {
					conta_atual = new ContaBancaria();
					// nao foi informado conta bancaria ppara esse pagamento
					id = "00";
					cpf = "Há Informar";
					banco = "Há Informar";
					nome = "Há Informar";
					codigo = "Há Informar";
					agencia = "Há Informar";
					conta = "Há Informar";

					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(Float.parseFloat(valor_pagamento));
					System.out.println(valorString);

					cobertura = calculoCobertura(Double.parseDouble(valor_pagamento));

					NumberFormat formatter = new DecimalFormat("#0,00");
					System.out.println(formatter.format(Double.parseDouble(cobertura)));

					modelo_cb.addRow(new Object[] { "00", id, cpf, nome, banco, codigo, agencia, conta, valorString,
							antecipado, cobertura, data_pagamento });

					conta_atual.setAgencia(agencia);
					conta_atual.setId_conta(00);
					conta_atual.setCpf_titular(cpf);
					conta_atual.setBanco(banco);
					conta_atual.setConta(conta);
					conta_atual.setCodigo(codigo);
					conta_atual.setNome(nome);

				} else {
					id = Integer.toString(conta_atual.getId_conta());
					cpf = conta_atual.getCpf_titular();
					banco = conta_atual.getBanco();
					nome = conta_atual.getNome();
					codigo = conta_atual.getCodigo();
					agencia = conta_atual.getAgencia();
					conta = conta_atual.getConta();

					cobertura = calculoCobertura(Double.parseDouble(valor_pagamento));

					NumberFormat formatter = new DecimalFormat("#0,00");
					System.out.println(formatter.format(Double.parseDouble(cobertura)));

					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(Float.parseFloat(valor_pagamento));
					System.out.println(valorString);

					modelo_cb.addRow(new Object[] { "00", id, cpf, nome, banco, codigo, agencia, conta, valorString,
							antecipado, cobertura, data_pagamento });

				}

				valor_acumulado = valor_acumulado.add(new BigDecimal(valor_pagamento));
				System.out.println("Valor acumulado e: " + valor_acumulado.toPlainString());

				if (valor_acumulado.compareTo(valor_total) == 0) {
					// Font font new Font("")
					mostrar_soma_atual_pagamentos.setEnabled(true);
					mostrar_soma_atual_pagamentos.setForeground(Color.green);

				} else {
					mostrar_soma_atual_pagamentos.setEnabled(true);
					mostrar_soma_atual_pagamentos.setForeground(Color.orange);
				}

				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Float.parseFloat(valor_acumulado.toPlainString()));
				System.out.println(valorString);
				mostrar_soma_atual_pagamentos.setText(valorString);

				BigDecimal valor_restante = valor_total.subtract(valor_acumulado);
				valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Float.parseFloat(valor_restante.toPlainString()));
				mostrar_soma_atual_restante.setText(valorString);

				// valor_atual = valor_total.subtract(valor_acumulado);

				cBContaBancaria.removeAllItems();
				entDataPagamento.setText("");
				entValorPagamento.setText("");
				// lblValorRestante.setText("");

				CadastroContrato.CadastroPagamento pagamento_atual = new CadastroContrato.CadastroPagamento();

				pagamento_atual.setConta(conta_atual);

				pagamento_atual.setData_pagamento(data_pagamento);
				pagamento_atual.setValor(new BigDecimal(valor_pagamento));
				pagamento_atual.setPagamento_adiantado(i_antecipado);
				if (pagamentos == null) {
					pagamentos = new ArrayList<>();
					pagamentos.add(pagamento_atual);

				} else {
					pagamentos.add(pagamento_atual);

				}

				chkBoxAntecipadoSim.setSelected(false);
				chkBoxAntecipadoNao.setSelected(true);

			}
		});
		btnAdicionarPag.setBounds(278, 619, 118, 34);
		painelEmpresa.add(btnAdicionarPag);

		JButton btnExcluirPag = new JButton("Excluir");
		btnExcluirPag.setBackground(new Color(102, 0, 0));
		btnExcluirPag.setForeground(Color.WHITE);
		btnExcluirPag.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExcluirPag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = table_cb.getSelectedRow();

				String get_valor_linha = table_cb.getValueAt(indiceDaLinha, 8).toString();
				String Svalor_pagamento = get_valor_linha.replaceAll("R", "").replace("$", "");

				System.out.println("Svalor_pagamento: " + Svalor_pagamento);
				Svalor_pagamento = Svalor_pagamento.replaceAll("[^0-9.,]", "");

				NumberFormat z = NumberFormat.getNumberInstance();

				Number number = null;
				try {
					number = z.parse(Svalor_pagamento);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double Dvalor_pagamento = number.doubleValue();

				BigDecimal valor_pagamento = new BigDecimal(Dvalor_pagamento);
				int id_conta = Integer.parseInt(table_cb.getValueAt(indiceDaLinha, 1).toString());

				valor_acumulado = valor_acumulado.subtract(valor_pagamento);
				// valor_total.subtract(valor_acumulado);

				if (table_cb.getValueAt(indiceDaLinha, 0).toString() == "00") {
					// esta removendo um pagamento que ainda nao foi cadastrado

					pagamentos.remove(indiceDaLinha);

				} else {
					// colocar no array a conta para excluir
					if (pagamento_a_excluir == null) {
						pagamento_a_excluir = new ArrayList<>();
					}
					pagamento_a_excluir.add(Integer.parseInt(table_cb.getValueAt(indiceDaLinha, 0).toString()));

					// pega o id do pagamento
					int id = Integer.parseInt(table_cb.getValueAt(indiceDaLinha, 0).toString());
					CadastroContrato.CadastroPagamento pagamento_a_remover = null;
					for (CadastroContrato.CadastroPagamento pagamento : pagamentos) {
						if (pagamento.getId() == id) {
							pagamento_a_remover = pagamento;
							break;
						}
					}
					pagamentos.remove(pagamento_a_remover);
					// ((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha);

				}

				mostrar_soma_atual_pagamentos.setText("R$ " + valor_acumulado.toPlainString());
				lblValorRestante.setText("R$ " + valor_total.subtract(valor_acumulado).toPlainString());

				if (valor_acumulado.compareTo(valor_total) == 0) {
					// Font font new Font("")
					mostrar_soma_atual_pagamentos.setEnabled(true);
					mostrar_soma_atual_pagamentos.setForeground(Color.green);

				} else {
					mostrar_soma_atual_pagamentos.setEnabled(true);
					mostrar_soma_atual_pagamentos.setForeground(Color.orange);
				}

				((DefaultTableModel) table_cb.getModel()).removeRow(indiceDaLinha);
				table_cb.repaint();
				table_cb.validate();

				Locale ptBr = new Locale("pt", "BR");

				BigDecimal valor_restante = valor_total.subtract(valor_acumulado);
				String valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Float.parseFloat(valor_restante.toPlainString()));
				mostrar_soma_atual_restante.setText(valorString);

			}
		});
		btnExcluirPag.setBounds(1115, 256, 96, 28);
		painelEmpresa.add(btnExcluirPag);

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

		// JRadioButton rdbtnSobreRodas, rdbtnAgendado, rdbtnAtoCarregamento,
		// rdbtnAVista;
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

		rdbtnAtoCarregamento = new JRadioButton("Ato Carregamento");
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

		JLabel lblAntecipado = new JLabel("Antecipado?");
		lblAntecipado.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblAntecipado.setBounds(115, 554, 122, 42);
		painelEmpresa.add(lblAntecipado);

		chkBoxAntecipadoSim = new JCheckBox("Sim");
		chkBoxAntecipadoSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (chkBoxAntecipadoSim.isSelected()) {
					chkBoxAntecipadoNao.setSelected(false);

				} else {
					chkBoxAntecipadoSim.setSelected(true);
					chkBoxAntecipadoNao.setSelected(false);

				}

			}
		});
		chkBoxAntecipadoSim.setBounds(239, 566, 61, 23);
		painelEmpresa.add(chkBoxAntecipadoSim);

		chkBoxAntecipadoNao = new JCheckBox("Não");
		chkBoxAntecipadoNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkBoxAntecipadoNao.isSelected()) {
					chkBoxAntecipadoSim.setSelected(false);

				} else {
					chkBoxAntecipadoNao.setSelected(true);
					chkBoxAntecipadoSim.setSelected(false);

				}

			}
		});
		chkBoxAntecipadoNao.setSelected(true);
		chkBoxAntecipadoNao.setBounds(322, 566, 61, 23);
		painelEmpresa.add(chkBoxAntecipadoNao);

		lblValorRestante_1 = new JLabel("Valor Restante:");
		lblValorRestante_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblValorRestante_1.setBounds(571, 311, 157, 42);
		painelEmpresa.add(lblValorRestante_1);

		mostrar_soma_atual_restante = new JLabel("");
		mostrar_soma_atual_restante.setForeground(Color.BLACK);
		mostrar_soma_atual_restante.setFont(new Font("Tahoma", Font.BOLD, 14));
		mostrar_soma_atual_restante.setEnabled(false);
		mostrar_soma_atual_restante.setBounds(745, 311, 165, 42);
		painelEmpresa.add(mostrar_soma_atual_restante);

		chBoxClausulaComissao.setEnabled(false);

		rBPostoSobreRodas = new JRadioButton("\"Posto sobre rodas\"");
		rBPostoSobreRodas.setBounds(645, 125, 147, 23);
		rBPostoSobreRodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rBPostoSobreRodas.isSelected()) {
					rBPostoSobreRodas.setSelected(true);
					rBJaDepositada.setSelected(false);
				} else {
					rBPostoSobreRodas.setSelected(false);
					rBJaDepositada.setSelected(true);

				}
			}
		});
		rBPostoSobreRodas.setSelected(true);
		painelDadosProdutos.add(rBPostoSobreRodas);

		rBJaDepositada = new JRadioButton("\"Já Depositada\"");
		rBJaDepositada.setBounds(803, 125, 101, 23);
		rBJaDepositada.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (rBJaDepositada.isSelected()) {
					rBPostoSobreRodas.setSelected(false);
					rBJaDepositada.setSelected(true);
				} else {
					rBPostoSobreRodas.setSelected(true);
					rBJaDepositada.setSelected(false);

				}
			}
		});
		painelDadosProdutos.add(rBJaDepositada);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		painelDadosProdutosPai.add(panel_4, "cell 2 0 1 2,grow");
		painelFinalizar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		painelFinalizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				getInformacoes();
				atualizarPainelSalvar();
			}
		});
		painelFinalizar.setBackground(Color.WHITE);

		// adiciona o paiel de salvar o contrato
		painelPrincipal.addTab("Finalizar", painelFinalizar);
		painelFinalizar.setLayout(null);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(0, 51, 0));
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(getDadosSalvar() != null) {

					// perguntar se quer salvar
					if (JOptionPane.showConfirmDialog(isto, "Confirmar Informações e Importar o Contrato?",
							"Importar Contrato", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						if(salvar()) {
							isto.dispose();
						}else {
							JOptionPane.showMessageDialog(isto, "Consulte o administrador sobre este erro!");
						}

					}
				}

			}

		});
		btnSalvar.setBounds(593, 619, 76, 33);
		painelFinalizar.add(btnSalvar);

		lblStatusInicial = new JLabel("Status Inicial: ");

		lblStatusInicial.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblStatusInicial.setBounds(652, 58, 564, 42);
		painelFinalizar.add(lblStatusInicial);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 153, 153));
		panel_1.setBounds(143, 11, 489, 580);
		painelFinalizar.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[][][][][]", "[][][][][][][][][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel_1_2_1 = new JLabel("Informações Contratuais");
		lblNewLabel_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblNewLabel_1_2_1, "cell 1 0");

		JLabel lblNewLabel_1_2 = new JLabel("Código:");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_2, "cell 0 1,alignx right");

		lblCodigoContratoInfo = new JLabel("");
		lblCodigoContratoInfo.setForeground(Color.WHITE);
		lblCodigoContratoInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblCodigoContratoInfo, "cell 1 1 4 1");

		lblNewLabel_1_3 = new JLabel("Data:");
		lblNewLabel_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_3, "cell 0 2,alignx right");

		lblDataInfo = new JLabel("");
		lblDataInfo.setForeground(Color.WHITE);
		lblDataInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblDataInfo, "cell 1 2 3 1");

		JLabel lblNewLabel_1 = new JLabel("Corretor:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1, "cell 0 3,alignx right");

		lblCorretorInfo = new JLabel("");
		lblCorretorInfo.setForeground(Color.WHITE);
		lblCorretorInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblCorretorInfo, "cell 1 3 4 1,alignx center");

		JLabel lblNewLabel_1_1 = new JLabel("Comprador 1:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1, "cell 0 4,alignx right");

		lblCompradorInfo = new JLabel("");
		lblCompradorInfo.setForeground(Color.WHITE);
		lblCompradorInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblCompradorInfo, "cell 1 4 4 1");

		lblNewLabel_1_4 = new JLabel("Comprador 2:");
		lblNewLabel_1_4.setForeground(Color.WHITE);
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_4, "cell 0 5,alignx right");

		lblCompradorInfo2 = new JLabel("");
		lblCompradorInfo2.setForeground(Color.WHITE);
		lblCompradorInfo2.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblCompradorInfo2, "cell 1 5 2 1");

		JLabel lblNewLabel_1_1_1 = new JLabel("Vendedor1:");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1, "cell 0 6,alignx right");

		lblVendedor1Info = new JLabel("");
		lblVendedor1Info.setForeground(Color.WHITE);
		lblVendedor1Info.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblVendedor1Info, "cell 1 6 4 1");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Vendedor2:");
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1, "cell 0 7,alignx right");

		lblVendedor2Info = new JLabel("");
		lblVendedor2Info.setForeground(Color.WHITE);
		lblVendedor2Info.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblVendedor2Info, "cell 1 7 4 1");

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Produto:");
		lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1, "cell 0 9,alignx right");

		lblProdutoInfo = new JLabel("");
		lblProdutoInfo.setForeground(Color.WHITE);
		lblProdutoInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblProdutoInfo, "cell 1 9 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Transgenia:");
		lblNewLabel_1_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1, "cell 0 10,alignx right");

		lblTransgeniaInfo = new JLabel("");
		lblTransgeniaInfo.setForeground(Color.WHITE);
		lblTransgeniaInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblTransgeniaInfo, "cell 1 10 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Safra:");
		lblNewLabel_1_1_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1, "cell 0 11,alignx right");

		lblSafraInfo = new JLabel("");
		lblSafraInfo.setForeground(Color.WHITE);
		lblSafraInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblSafraInfo, "cell 1 11 4 1");

		lblNewLabel_1_1_1_1_1_1_2 = new JLabel("Data Entrega:");
		lblNewLabel_1_1_1_1_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_2, "cell 0 12,alignx right");

		lblDataEntregaInfo = new JLabel("");
		lblDataEntregaInfo.setForeground(Color.WHITE);
		lblDataEntregaInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblDataEntregaInfo, "cell 1 12");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_2 = new JLabel("Quantidade:");
		lblNewLabel_1_1_1_1_1_1_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_2, "cell 0 13,alignx right");

		lblQuantidadeInfo = new JLabel("");
		lblQuantidadeInfo.setForeground(Color.WHITE);
		lblQuantidadeInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblQuantidadeInfo, "cell 1 13 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_2_1_2 = new JLabel("Unidade:");
		lblNewLabel_1_1_1_1_1_1_1_1_2_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_2_1_2, "cell 0 14,alignx right");

		lblUnidadeInfo = new JLabel("");
		lblUnidadeInfo.setForeground(Color.WHITE);
		lblUnidadeInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblUnidadeInfo, "cell 1 14 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_2_1 = new JLabel("Valor Unidade:");
		lblNewLabel_1_1_1_1_1_1_1_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_2_1, "cell 0 15,alignx right");

		lblValorUnidadeInfo = new JLabel("");
		lblValorUnidadeInfo.setForeground(Color.WHITE);
		lblValorUnidadeInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblValorUnidadeInfo, "cell 1 15 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_2_1_1 = new JLabel("Valor Total:");
		lblNewLabel_1_1_1_1_1_1_1_1_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_2_1_1, "cell 0 16,alignx right");

		lblValorTotalInfo = new JLabel("");
		lblValorTotalInfo.setForeground(Color.WHITE);
		lblValorTotalInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblValorTotalInfo, "cell 1 16 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_3 = new JLabel("Comissão:");
		lblNewLabel_1_1_1_1_1_1_1_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_3, "cell 0 17,alignx right");

		lblComissaoInfo = new JLabel("");
		lblComissaoInfo.setForeground(Color.WHITE);
		lblComissaoInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblComissaoInfo, "cell 1 17 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_3_1 = new JLabel("Valor Total Comissão:");
		lblNewLabel_1_1_1_1_1_1_1_1_3_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_3_1, "cell 0 18");

		lblValorTotalComissaoInfo = new JLabel("");
		lblValorTotalComissaoInfo.setForeground(Color.WHITE);
		lblValorTotalComissaoInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblValorTotalComissaoInfo, "cell 1 18 4 1");

		lblNewLabel_1_1_1_1_1_1_1_1_3_2 = new JLabel("Local Retirada:");
		lblNewLabel_1_1_1_1_1_1_1_1_3_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_3_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_3_2, "cell 0 19,alignx right");

		lblLocalRetiradaInfo = new JLabel("");
		lblLocalRetiradaInfo.setForeground(Color.WHITE);
		lblLocalRetiradaInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblLocalRetiradaInfo, "cell 1 19");

		lblNewLabel_1_1_1_1_1_1_1_1_3_3 = new JLabel("Recebimento:");
		lblNewLabel_1_1_1_1_1_1_1_1_3_3.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_3_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_3_3, "cell 0 20,alignx right");

		lblRecebimentoInfo = new JLabel("");
		lblRecebimentoInfo.setForeground(Color.WHITE);
		lblRecebimentoInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblRecebimentoInfo, "cell 1 20");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("Frete:");
		lblNewLabel_1_1_1_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1, "cell 0 21,alignx right");

		lblFreteInfo = new JLabel("");
		lblFreteInfo.setForeground(Color.WHITE);
		lblFreteInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblFreteInfo, "cell 1 21 4 1");

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_1 = new JLabel("Armazenagem:");
		lblNewLabel_1_1_1_1_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1_1_1_1_1_1_1_1_1, "cell 0 22,alignx right");

		lblArmazenagemInfo = new JLabel("");
		lblArmazenagemInfo.setForeground(Color.WHITE);
		lblArmazenagemInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblArmazenagemInfo, "cell 1 22 4 1");

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 153, 0));
		panel_2.setBounds(633, 219, 656, 371);
		painelFinalizar.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 121, 152, 210, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 25, 303, 17, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JLabel lblNewLabel_1_1_1_1_1_1_1_1_1_1 = new JLabel("Informações de Pagamento:");
		lblNewLabel_1_1_1_1_1_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_1_1_1_1_1_1_1_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1_1_1_1_1_1_1_1_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1_1_1_1_1_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1_1_1_1_1_1_1_1_1.gridx = 2;
		gbc_lblNewLabel_1_1_1_1_1_1_1_1_1_1.gridy = 0;
		panel_2.add(lblNewLabel_1_1_1_1_1_1_1_1_1_1, gbc_lblNewLabel_1_1_1_1_1_1_1_1_1_1);

		tabela_pagamentos_elaborados = new JTable(modelo_pagamentos);
		tabela_pagamentos_elaborados.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_pagamentos_elaborados);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_2.add(scrollPane, gbc_scrollPane);

		lblNewLabel_4 = new JLabel("Total Valor Já Pago:");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 2;
		panel_2.add(lblNewLabel_4, gbc_lblNewLabel_4);

		lblValorTotalJaPagoInfo = new JLabel("New label");
		lblValorTotalJaPagoInfo.setForeground(Color.WHITE);
		lblValorTotalJaPagoInfo.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblValorTotalJaPagoInfo = new GridBagConstraints();
		gbc_lblValorTotalJaPagoInfo.fill = GridBagConstraints.VERTICAL;
		gbc_lblValorTotalJaPagoInfo.insets = new Insets(0, 0, 0, 5);
		gbc_lblValorTotalJaPagoInfo.anchor = GridBagConstraints.WEST;
		gbc_lblValorTotalJaPagoInfo.gridx = 1;
		gbc_lblValorTotalJaPagoInfo.gridy = 2;
		panel_2.add(lblValorTotalJaPagoInfo, gbc_lblValorTotalJaPagoInfo);

		lblNewLabel_5 = new JLabel("Total Valor Restante:");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_5.gridx = 2;
		gbc_lblNewLabel_5.gridy = 2;
		panel_2.add(lblNewLabel_5, gbc_lblNewLabel_5);

		lblValorTotalHaPagarInfo = new JLabel("New label");
		lblValorTotalHaPagarInfo.setForeground(Color.WHITE);
		lblValorTotalHaPagarInfo.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblValorTotalHaPagarInfo = new GridBagConstraints();
		gbc_lblValorTotalHaPagarInfo.anchor = GridBagConstraints.WEST;
		gbc_lblValorTotalHaPagarInfo.gridx = 3;
		gbc_lblValorTotalHaPagarInfo.gridy = 2;
		panel_2.add(lblValorTotalHaPagarInfo, gbc_lblValorTotalHaPagarInfo);

		lblTipoContrato = new JLabel("Tipo Contrato:");
		lblTipoContrato.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblTipoContrato.setBounds(652, 124, 564, 42);
		painelFinalizar.add(lblTipoContrato);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBackground(new Color(0, 0, 51));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
				// atualizar o contrato
				// excluir o arquivo

			}
		});
		btnAtualizar.setBounds(694, 619, 95, 33);
		painelFinalizar.add(btnAtualizar);

		chBoxClausulaComissao.setVisible(false);

		if (tipoContrato == 6) {
			// e um subcontrato
			if (flag_edicao == 1) {
				rotinasEdicao();
				setClausulas();
				// modo de edicao de subcontrato

				// setInfoPai();

			} else {
				rotinasSubContrato();

			}

			PainelInformativo painel_informacoes1 = new PainelInformativo();
			PainelInformativo painel_informacoes2 = new PainelInformativo();

			JPanel panelInformativoAbaPrincipal = new JPanel();
			JPanel panelInformativoAbaproduto = new JPanel();

			panelInformativoAbaPrincipal.setBounds(0, 0, 411, 335);
			panelInformativoAbaPrincipal.add(painel_informacoes1);

			panelInformativoAbaproduto.setBounds(0, 0, 411, 335);
			panelInformativoAbaproduto.add(painel_informacoes2);

			painelDadosIniciais.add(panelInformativoAbaPrincipal);
			painelDadosProdutos.add(panelInformativoAbaproduto);

			panelInformativoAbaPrincipal.setBackground(Color.white);
			panelInformativoAbaPrincipal.setLayout(null);

			panelInformativoAbaproduto.setBackground(Color.white);
			panelInformativoAbaproduto.setLayout(null);

		} else if (tipoContrato == 4 || tipoContrato == 5) {
			// e um contrato original de terceiros passivel de edicao
			if (flag_edicao == 1) {
				btnSalvar.setEnabled(false);
				btnSalvar.setVisible(false);
				// se esta no modo edicao, pode se editar os atributos deste contrato
				rotinasEdicao();
				setClausulas();
			} else {

				btnAtualizar.setEnabled(false);
				btnAtualizar.setVisible(false);
				// se esta no modo criacao
			}
		}

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);
		

		this.setResizable(true);
		this.setVisible(true);

	}

	public void setComprador1(CadastroCliente comprador) {
		novo_contrato.adicionarComprador(0, comprador);

		cBComprador.removeAllItems();

		if (comprador.getTipo_pessoa() == 0) // pessoa fisica
		{
			cBComprador.addItem(comprador.getNome() + " " + comprador.getSobrenome());
			cBComprador.setSelectedItem(comprador.getNome() + " " + comprador.getSobrenome());
			lblNomeComprador.setText(comprador.getNome() + " " + comprador.getSobrenome());
			cBComprador.addItem("Indefinido");

			lblCpfCnpj.setText(comprador.getCpf());
			System.out.println("O comprador selecionado e uma pessoa fisica");

		} else // pessoa juridica
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

	public String pegarData() {

		Date hoje = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(hoje);
	}

	public void setContaBancaria(ContaBancaria conta) {

		cBContaBancaria.removeAllItems();
		cBContaBancaria.addItem(conta.getNome());
		conta_atual = conta;

	}

	public void setVendedor1(CadastroCliente vendedor) {
		novo_contrato.adicionarVendedor(0, vendedor);

		cBVendedor1.removeAllItems();

		if (vendedor.getTipo_pessoa() == 0) // pessoa fisica
		{
			cBVendedor1.addItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor1.setSelectedItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor1.addItem("Indefinido");

			lblNomeVendedor.setText(vendedor.getNome() + " " + vendedor.getSobrenome());

			lblCpfCnpjVendedor.setText(vendedor.getCpf());

		} else // pessoa juridica
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

	public void setCorretor(CadastroCliente corretor) {
		novo_contrato.adicionarCorretor(0, corretor);

		cBCorretor.removeAllItems();

		if (corretor.getTipo_pessoa() == 0) // pessoa fisica
		{
			cBCorretor.addItem(corretor.getNome() + " " + corretor.getSobrenome());
			cBCorretor.setSelectedItem(corretor.getNome() + " " + corretor.getSobrenome());
			cBCorretor.addItem("Indefinido");

			lblNomeCorretor.setText(corretor.getNome() + " " + corretor.getSobrenome());

			lblCpfCnpjCorretor.setText(corretor.getCpf());

		} else // pessoa juridica
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

	public void setVendedor2(CadastroCliente vendedor) {
		novo_contrato.adicionarVendedor(1, vendedor);
		cBVendedor2.removeAllItems();

		if (vendedor.getTipo_pessoa() == 0) // pessoa fisica
		{
			cBVendedor2.addItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor2.setSelectedItem(vendedor.getNome() + " " + vendedor.getSobrenome());
			cBVendedor2.addItem("Indefinido");

			lblNomeVendedor2.setText(vendedor.getNome() + " " + vendedor.getSobrenome());

			lblCpfCnpjVendedor2.setText(vendedor.getCpf());

		} else // pessoa juridica
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

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();
		servidor_unidade = configs_globais.getServidorUnidade();

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
		try {
			if (chBoxClausulaComissao.isSelected()) {

				String valor_texto = new PorExtenso(Double.parseDouble(valor.replace(",", "."))).toString();
				double _valor = Double.parseDouble(valor.replace(",", "."));

				String s_quantidade = entQuantidade.getText();
				double quantidade = Double.parseDouble(s_quantidade);

				double valor_total = quantidade * _valor;

				String valor_total_extenso = new PorExtenso(valor_total).toString();

				entClausula3.setText("Comissão de R$ " + _valor + " ( " + valor_texto.toLowerCase() + " ) por "
						+ unidadeGlobal + " pagas ao CORRETOR do CONTRATO, no valor total de R$ " + valor_total + " ("
						+ valor_total_extenso.toLowerCase() + " ).");

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Informe a quantidade e o valor da comissão corretamente!");
		}
	}

	public void setClausula2(String nome_produto) {
		entClausula2
				.setText(nome_produto + " acima de 14% de umidade será cobrado uma taxa de despesas para a secagem");

	}

	public void setClausulaFrete() {

	}

	public String quebrarCodigo() {
		String texto = contrato_pai_local.getCodigo().replaceAll("[^0-9]+", ";");

		System.out.println("codigo para edicao: " + texto);
		String[] textoSeparado = texto.split(";");

		String safra = textoSeparado[0];

		String comprador = textoSeparado[1];
		String vendedor = textoSeparado[2];
		String sequenciaAleatoria = textoSeparado[3];
		return sequenciaAleatoria;

	}

	public void rotinasEdicao() {

		CadastroCliente corretor[] = contrato_pai_local.getCorretores();
		CadastroCliente compradores[] = contrato_pai_local.getCompradores();
		CadastroCliente vendedores[] = contrato_pai_local.getVendedores();

		codigo_antigo = contrato_pai_local.getCodigo();
		// lblCodigoSubContrato

		// corretor, compradores e vendedores

		if (corretor[0] != null) {
			this.setCorretor(corretor[0]);
		}

		if (compradores[0] != null) {
			this.setComprador1(compradores[0]);
		}
		

		if (compradores[1] != null) {
			this.setComprador2(compradores[1]);
		}

		if (vendedores[0] != null) {
			this.setVendedor1(vendedores[0]);
		}

		if (vendedores[1] != null) {
			this.setVendedor2(vendedores[1]);
		}

		// data de entrega
		System.out.println("data de entrega contrato pai:" + contrato_pai_local.getData_entrega());
		entDataEntrega.setText(contrato_pai_local.getData_entrega());

		// unidade de medida
		if (contrato_pai_local.getMedida().toUpperCase().equals("SACOS")) {
			rQuanKG.setSelected(false);
			rQuanS.setSelected(true);
			rQuanT.setSelected(false);
			unidadeGlobal = "sacos";
			// setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		} else if (contrato_pai_local.getMedida().toUpperCase().equals("QUILOGRAMAS")) {
			rQuanKG.setSelected(true);
			rQuanS.setSelected(false);
			rQuanT.setSelected(false);
			unidadeGlobal = "quilogramas";
			// setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		} else if (contrato_pai_local.getMedida().toUpperCase().equals("TONELADAS")) {
			rQuanKG.setSelected(false);
			rQuanS.setSelected(false);
			rQuanT.setSelected(true);
			unidadeGlobal = "toneladas";
			// setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		}

		// safra
		CadastroSafra safra_contrato_pai_local = contrato_pai_local.getModelo_safra();
		modelSafra.setSelectedItem(safra_contrato_pai_local);
		setClausula2(safra_contrato_pai_local.getProduto().getNome_produto());

		// quantidades e preços

		entQuantidade.setText(Double.toString(contrato_pai_local.getQuantidade()));
		entPreco.setText(Double.toString(contrato_pai_local.getValor_produto()));

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr)
				.format(Float.parseFloat(contrato_pai_local.getValor_a_pagar().toPlainString()));

		lblValorTotal.setText(valorString);
		mostrar_valor_total_contrato.setText(valorString);
		valor_total = contrato_pai_local.getValor_a_pagar();
		valor_atual = new BigDecimal("0");
		// valor_acumulado = new BigDecimal("0");

		BigDecimal valor_tot_comissao = contrato_pai_local.getValor_comissao();
		BigDecimal quantidade = new BigDecimal(contrato_pai_local.getQuantidade());
		// comissao
		if (contrato_pai_local.getComissao() == 1) {
			setComissao();
			if (contrato_pai_local.getClausula_comissao() == 1) {

				chBoxClausulaComissao.setSelected(true);

				System.out.println("Quatidade: " + quantidade + "valor total da comissao: " + valor_tot_comissao);
				String valor_por_unidade = (valor_tot_comissao.divide(quantidade)).toPlainString();
				entComissao.setText(valor_por_unidade);
				setClausulaComissao(valor_por_unidade);

			}

			String valorString2 = NumberFormat.getCurrencyInstance(ptBr)
					.format(Float.parseFloat(valor_tot_comissao.toPlainString()));

			mostrar_valor_total_comissao.setText(valorString2);
			lblValorTotalComisao1.setText(valorString2);
			valor_total_comissao = valor_tot_comissao;

		}

		if (contrato_pai_local.getTipo_entrega() == 1) {
			rBPostoSobreRodas.setSelected(true);
			rBJaDepositada.setSelected(false);

		} else if (contrato_pai_local.getTipo_entrega() == 2) {
			rBJaDepositada.setSelected(true);
			rBPostoSobreRodas.setSelected(false);

		}

		CadastroCliente localRetirada = new GerenciarBancoClientes()
				.getCliente(contrato_pai_local.getId_local_retirada());
		int indice = -1;
		for (int i = 0; i < modelLocalRetirada.getSize(); i++) {
			if (modelLocalRetirada.getElementAt(i).getId() == localRetirada.getId()) {
				indice = i;
			}
		}

		modelLocalRetirada.setSelectedItem(localRetirada);



		entCodigoContrato.setText(contrato_pai_local.getCodigo());

		setPagamentos();

	}

	public void setPagamentos() {
		System.out.println("Metodo setPagamentos chamado!");

		String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, data_pagamento, antecipado, cobre = null;
		float valor_total_pagamentos = 0;

		if (contrato_pai_local.getPagamentos() != null) {
			System.out.println("Existem pagamentos para edicao!");
			for (CadastroContrato.CadastroPagamento pag : contrato_pai_local.getPagamentos()) {

				if (pag != null) {
					ContaBancaria conta_bc = pag.getConta();
					float pag_local = 0;

					if (conta_bc != null) {
						id = Integer.toString(conta_bc.getId_conta());
						cpf = conta_bc.getCpf_titular();
						banco = conta_bc.getBanco();
						nome = conta_bc.getNome();
						codigo = conta_bc.getCodigo();
						agencia = conta_bc.getAgencia();
						conta = conta_bc.getConta();

						if (pag.getPagamento_adiantado() == 1) {
							antecipado = "SIM";
						} else {
							antecipado = "NÂO";
						}

						cobre = calculoCobertura(Double.parseDouble(pag.getValor().toPlainString()));

						valor_total_pagamentos += Float.parseFloat(pag.getValor_string());
						System.out.println("o valor total agora e: " + valor_total_pagamentos);

						Locale ptBr = new Locale("pt", "BR");
						String valorString = NumberFormat.getCurrencyInstance(ptBr)
								.format(Float.parseFloat(pag.getValor_string()));

						modelo_cb.addRow(new Object[] { pag.getId(), id, cpf, nome, banco, codigo, agencia, conta,
								valorString, antecipado, cobre, pag.getData_pagamento() });
					} /*
						 * else { id = "00"; cpf = "Há Informar"; banco = "Há Informar"; nome =
						 * "Há Informar"; codigo = "Há Informar"; agencia = "Há Informar"; conta =
						 * "Há Informar"; }
						 */

				}
			}
			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
			mostrar_soma_atual_pagamentos.setText(valorString);
			valor_acumulado = new BigDecimal(valor_total_pagamentos);
			System.out.println("valor acumulado recebido: " + valor_acumulado);
			pagamentos = contrato_pai_local.getPagamentos();

		}

	}

	public void setClausulas() {

		if (contrato_pai_local.getFrete() != null) {
			if (!contrato_pai_local.getFrete().equals("") && !contrato_pai_local.getFrete().equals(" ")
					&& contrato_pai_local.getFrete().length() > 4) {
				if (contrato_pai_local.getFrete().equalsIgnoreCase("Comprador")) {
					cBFrete.setSelectedItem("Comprador");

				} else {
					cBFrete.setSelectedItem("Vendedor");

				}

				chkBoxClausulaFrete.setSelected(true);
				entClausulaFrete.setEnabled(true);
				entClausulaFrete.setEditable(true);
				cBFrete.setEnabled(true);
				entClausulaFrete.setText(contrato_pai_local.getClausula_frete());
			}
		}

		if (contrato_pai_local.getArmazenagem() != null) {
			if (!contrato_pai_local.getArmazenagem().equals("") && !contrato_pai_local.getArmazenagem().equals(" ")
					&& contrato_pai_local.getArmazenagem().length() > 4) {
				if (contrato_pai_local.getArmazenagem().equalsIgnoreCase("Comprador")) {
					cBArmazenagem.setSelectedItem("Comprador");

				} else {
					cBArmazenagem.setSelectedItem("Vendedor");

				}

				chkBoxClausulaArmazenagem.setSelected(true);
				entClausulaArmazenagem.setEnabled(true);
				entClausulaArmazenagem.setEditable(true);
				cBArmazenagem.setEnabled(true);
				entClausulaArmazenagem.setText(contrato_pai_local.getClausula_armazenagem());
			}
		}

		if (contrato_pai_local.getTexto_clausulas() != null) {
			int num_clausulas = 1;
			// clausulas
			ArrayList<String> clausulas = new ArrayList<>();
			String texto_clausulas = contrato_pai_local.getTexto_clausulas();
			String separada[] = texto_clausulas.split(";");
			for (int i = 0; i < separada.length; i++) {
				if (separada[i] != null && !separada[i].equals("")) {

					System.out.println("clausula: " + separada[i]);
					clausulas.add(separada[i]);

				}
			}

			contrato_pai_local.setClausulas(clausulas);

			for (String termo : contrato_pai_local.getClausulas()) {
				if (termo != null && !termo.equals("")) {
					if (num_clausulas == 1) {
						entClausula1.setText(termo);
						chBoxClausula1.setSelected(true);
						num_clausulas++;
					} else if (num_clausulas == 2) {
						entClausula2.setText(termo);
						chBoxClausula2.setSelected(true);

						num_clausulas++;

					} else if (num_clausulas == 3) {

						if (contrato_pai_local.getClausula_comissao() == 1) {
							entClausula3.setText(termo);
							chBoxClausula3.setSelected(true);
						} else {
							entClausula4.setText(termo);
							chBoxClausula4.setSelected(true);
							entClausula4.setEditable(true);
							entClausula4.setEnabled(true);

						}

						num_clausulas = num_clausulas + 2;

					} else if (num_clausulas == 4) {
						entClausula4.setText(termo);
						chBoxClausula4.setSelected(true);
						entClausula4.setEditable(true);
						entClausula4.setEnabled(true);

						num_clausulas++;

					} else if (num_clausulas == 5) {
						entClausula5.setText(termo);
						chBoxClausula5.setSelected(true);
						entClausula5.setEditable(true);
						entClausula5.setEnabled(true);

						num_clausulas++;

					} else if (num_clausulas == 6) {
						entClausula6.setText(termo);
						chBoxClausula6.setSelected(true);
						entClausula6.setEditable(true);
						entClausula6.setEnabled(true);

						num_clausulas++;

					}
					// set clausula de armazenagem
					else if (num_clausulas == contrato_pai_local.getClausulas().size() - 1) {
						// verifica a ultima clausula
						if (contrato_pai_local.getFrete() != null) {
							if (contrato_pai_local.getFrete().equalsIgnoreCase("Comprador")) {
								cBFrete.setSelectedItem("Comprador");
								entClausulaFrete.setText(termo);
							} else {
								cBFrete.setSelectedItem("Vendedor");
								entClausulaFrete.setText(termo);

							}
						}
					}

				}
			} // fim do for
		}
	}

	public void rotinasSubContrato() {
		// safra
		CadastroSafra safra_contrato_pai_local = contrato_pai_local.getModelo_safra();
		modelSafra.setSelectedItem(safra_contrato_pai_local);
		setClausula2(safra_contrato_pai_local.getProduto().getNome_produto());

		// data de entrega
		System.out.println("data de entrega contrato pai:" + contrato_pai_local.getData_entrega());
		entDataEntrega.setText(contrato_pai_local.getData_entrega());

		// unidade de medida
		if (contrato_pai_local.getMedida().toUpperCase().equals("SACOS")) {
			rQuanKG.setSelected(false);
			rQuanS.setSelected(true);
			rQuanT.setSelected(false);
			unidadeGlobal = "sacos";
			// setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		} else if (contrato_pai_local.getMedida().toUpperCase().equals("QUILOGRAMAS")) {
			rQuanKG.setSelected(true);
			rQuanS.setSelected(false);
			rQuanT.setSelected(false);
			unidadeGlobal = "quilogramas";
			// setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		} else if (contrato_pai_local.getMedida().toUpperCase().equals("TONELADAS")) {
			rQuanKG.setSelected(false);
			rQuanS.setSelected(false);
			rQuanT.setSelected(true);
			unidadeGlobal = "toneladas";
			// setClausulaComissao(entComissao.getText().toString().replace(".", ","));
		}

	}

	public CadastroContrato.CadastroTarefa criarTarefa(int status, String nome_tarefa, String descricao, String msg,
			CadastroLogin executor, int prioridade) {

		CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

		tarefa.setNome_tarefa(nome_tarefa);

		// cria a tarefa de insercao de contrato
		tarefa.setId_tarefa(0);
		tarefa.setDescricao_tarefa(descricao);

		tarefa.setStatus_tarefa(status);
		tarefa.setMensagem(msg);

		GetData data = new GetData();
		tarefa.setHora(data.getHora());
		tarefa.setData(data.getData());
		tarefa.setHora_agendada(data.getHora());
		tarefa.setData_agendada(data.getData());

		tarefa.setCriador(login);
		tarefa.setExecutor(executor);

		tarefa.setPrioridade(prioridade);

		return tarefa;

	}

	public boolean apagarDiretorio() {

		try {
			ManipularTxt manipular = new ManipularTxt();
			manipular.limparDiretorio(new File(novo_contrato.getCaminho_diretorio_contrato()));
			System.out.println("A pasta do diretorio do contrato foi excuida!");
			return true;

		} catch (Exception f) {
			System.out.println("Não foi possivel excluir a pasta do diretorio do arquivo!");

			return false;
		}

	}

	public String calculoCobertura(double valor_pagamento) {
		String retorno = "";
		// quantidade
		double quantidade = Double.parseDouble(entQuantidade.getText());
		double preco = Double.parseDouble(entPreco.getText());

		// unidade de medida
		if (rQuanT.isSelected()) {
			// unidade em toneladas

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);

		} else if (rQuanS.isSelected()) {
			// unidade em sacos

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);

		} else if (rQuanKG.isSelected()) {
			// unidade em quilogramas

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);
		}

		return retorno;
	}

	public class PainelInformativo extends JPanel {

		public PainelInformativo() {
			CadastroCliente compradores[] = contrato_pai_local.getCompradores();
			CadastroCliente vendedores[] = contrato_pai_local.getVendedores();

			JPanel panel = this;
			panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel.setBackground(new Color(255, 255, 255));
			panel.setBounds(0, 0, 349, 271);
			panel.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][][][][][]"));

			JLabel lblInformaesDoContrato = new JLabel("Informações do Contrato Original");
			lblInformaesDoContrato.setFont(new Font("Dialog", Font.BOLD, 10));
			panel.add(lblInformaesDoContrato, "cell 1 0,alignx left");

			JLabel lblNewLabel_2_1_1_2_1_2_1_1 = new JLabel("Data Contrato:");
			lblNewLabel_2_1_1_2_1_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_2_1_2_1_1, "cell 0 1,alignx left");

			JLabel lblInfoDataContrato = new JLabel("01/01/01");
			lblInfoDataContrato.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoDataContrato, "cell 1 1,alignx left");

			JLabel lblNewLabel_2 = new JLabel("Comprador:");
			lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2, "cell 0 2,alignx left");

			JLabel lblInfoComprador = new JLabel("Marcos Alexandre Andrade de Carvalho e Outros");
			lblInfoComprador.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoComprador, "cell 1 2,alignx left");

			JLabel lblNewLabel_2_1 = new JLabel("Vendedor:");
			lblNewLabel_2_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1, "cell 0 3,alignx left");

			JLabel lblInfoVendedor1 = new JLabel("Vendedor1");
			lblInfoVendedor1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoVendedor1, "cell 1 3,alignx left");

			JLabel lblNewLabel_2_1_1 = new JLabel("Vendedor:");
			lblNewLabel_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1, "cell 0 4,alignx left");

			JLabel lblInfoVendedor2 = new JLabel(" ");
			lblInfoVendedor2.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoVendedor2, "cell 1 4,alignx left");

			JLabel lblNewLabel_2_1_1_1 = new JLabel("Safra:");
			lblNewLabel_2_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_1, "cell 0 5,alignx left");

			JLabel lblInfoSafra = new JLabel("<dynamic> 0/0");
			lblInfoSafra.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoSafra, "cell 1 5,alignx left");

			JLabel lblNewLabel_2_1_1_2 = new JLabel("Medida:");
			lblNewLabel_2_1_1_2.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_2, "cell 0 6,alignx left");

			JLabel lblInfoMedida = new JLabel("Sacos");
			lblInfoMedida.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoMedida, "cell 1 6,alignx left");

			JLabel lblNewLabel_2_1_1_2_1 = new JLabel("Quantidade:");
			lblNewLabel_2_1_1_2_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_2_1, "cell 0 7,alignx left");

			JLabel lblInfoQuantidade = new JLabel("0.0 <dynamic>");
			lblInfoQuantidade.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoQuantidade, "cell 1 7,alignx left");

			JLabel lblNewLabel_2_1_1_2_1_1 = new JLabel("Valor por Medida:");
			lblNewLabel_2_1_1_2_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_2_1_1, "cell 0 8,alignx left");

			JLabel lblInfoValorMedida = new JLabel("R$ 0,00");
			lblInfoValorMedida.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoValorMedida, "cell 1 8,alignx left");

			JLabel lblNewLabel_2_1_1_2_1_2 = new JLabel("Valor Total:");
			lblNewLabel_2_1_1_2_1_2.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_2_1_2, "cell 0 9,alignx left");

			JLabel lblInfoValorTotal = new JLabel("R$ 0,00");
			lblInfoValorTotal.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoValorTotal, "cell 1 9,alignx left");

			JLabel lblNewLabel_2_1_1_2_1_2_1 = new JLabel("Data Entrega:");
			lblNewLabel_2_1_1_2_1_2_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewLabel_2_1_1_2_1_2_1, "cell 0 10,alignx left");

			JLabel lblInfoDataEntrega = new JLabel("R$ 0,00");
			lblInfoDataEntrega.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoDataEntrega, "cell 1 10,alignx left");

			JLabel lblNewComissao = new JLabel("Comissão:");
			lblNewComissao.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblNewComissao, "cell 0 11,alignx left");

			JLabel lblInfoComissao = new JLabel("Sim");
			lblInfoComissao.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblInfoComissao, "cell 1 11,alignx left");

			JLabel lblValorComisso1 = new JLabel("Valor Comissão:");
			lblValorComisso1.setFont(new Font("Dialog", Font.PLAIN, 12));
			panel.add(lblValorComisso1, "cell 0 12,alignx left");

			JLabel lblValorComissao = new JLabel("R$ 0,00");
			lblValorComissao.setFont(new Font("Dialog", Font.PLAIN, 12));
			lblValorComissao.setToolTipText("");
			panel.add(lblValorComissao, "cell 1 12,alignx left");

			lblInfoDataContrato.setText(contrato_pai_local.getData_contrato());
			lblInfoDataEntrega.setText(contrato_pai_local.getData_entrega());

			if (contrato_pai_local.getComissao() == 1) {

				// tem comissao
				lblInfoComissao.setText("SIM");

				lblValorComissao.setText(contrato_pai_local.getValor_comissao().toPlainString());

			} else {
				lblInfoComissao.setText("NÃO");
				lblValorComissao.setText("");

			}

			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				lblInfoComprador.setText(compradores[0].getNome_empresarial());

			} else {
				lblInfoComprador.setText(compradores[0].getNome_fantaia());

			}

			if (vendedores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				lblInfoVendedor1.setText(vendedores[0].getNome_empresarial());

			} else {
				lblInfoVendedor1.setText(vendedores[0].getNome_fantaia());

			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0) {

					// pessoa fisica
					lblInfoVendedor2.setText(vendedores[0].getNome_empresarial());
				} else {
					lblInfoVendedor2.setText(vendedores[0].getNome_fantaia());

				}

			}

			String info_safra = contrato_pai_local.getModelo_safra().getProduto().getNome_produto() + " "
					+ contrato_pai_local.getModelo_safra().getAno_plantio() + "/"
					+ contrato_pai_local.getModelo_safra().getAno_colheita();

			lblInfoSafra.setText(info_safra);

			lblInfoMedida.setText(contrato_pai_local.getMedida());

			lblInfoQuantidade.setText(
					Double.toString(contrato_pai_local.getQuantidade()) + " " + contrato_pai_local.getMedida());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_pai_local.getValor_produto());
			lblInfoValorMedida.setText(valorString);

			valorString = NumberFormat.getCurrencyInstance(ptBr)
					.format(Double.parseDouble(contrato_pai_local.getValor_a_pagar().toPlainString()));

			lblInfoValorTotal.setText(valorString);
		}

	}

	public class ModeloPagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int favorecido = 1;
		private final int data_pagamento = 2;
		private final int antecipado = 3;
		private final int valor_pagamento = 4;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Favorecido", "Data Pag:", "Antecipação:", "Valor Pagamento:" };
		private final ArrayList<CadastroContrato.CadastroPagamento> dados = new ArrayList<>();// usamos como dados uma
																								// lista genérica de
		// nfs

		public ModeloPagamentoTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na tabela
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case favorecido:
				return String.class;
			case data_pagamento:
				return String.class;
			case antecipado:
				return String.class;
			case valor_pagamento:
				return String.class;

			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public String getColumnName(int columnIndex) {
			return colunas[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// retorna o valor conforme a coluna e linha
			Locale ptBr = new Locale("pt", "BR");
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroContrato.CadastroPagamento modelo_pagamento = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return modelo_pagamento.getId();
			case favorecido: {
				ContaBancaria conta = modelo_pagamento.getConta();
				if (conta != null) {
					return conta.getNome() + " " + conta.getCpf_titular() + " " + conta.getAgencia() + " "
							+ conta.getConta();
				} else {
					return "Há Informar";
				}
			}
			case data_pagamento:
				return modelo_pagamento.getData_pagamento();
			case antecipado: {
				if (modelo_pagamento.getPagamento_adiantado() == 1) {
					return "SIM";

				} else {
					return "NÃO";

				}

			}
			case valor_pagamento: {

				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(modelo_pagamento.getValor());
				return valorString;

			}
			default:
				throw new IndexOutOfBoundsException("Coluna Inválida!!!");
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// metodo identifica qual coluna é editavel

			// só iremos editar a coluna BENEFICIO,
			// que será um checkbox por ser boolean

			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroContrato.CadastroPagamento modelo_pagamento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroContrato.CadastroPagamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroContrato.CadastroPagamento contrato) {
			return dados.indexOf(contrato);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroContrato.CadastroPagamento contrato) {
			dados.add(contrato);
			fireTableRowsInserted(indexOf(contrato), indexOf(contrato));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroContrato.CadastroPagamento> dadosIn) {
			dados.addAll(dadosIn);
			fireTableDataChanged();
		}

		/**
		 * remove um registro da lista, através do indice
		 * 
		 * @param rowIndex
		 */
		public void onRemove(int rowIndex) {
			dados.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		/**
		 * remove um registro da lista, através do objeto
		 * 
		 * @param empregado
		 */
		public void onRemove(CadastroContrato.CadastroPagamento contrato) {
			int indexBefore = indexOf(contrato);// pega o indice antes de apagar
			dados.remove(contrato);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroContrato.CadastroPagamento onGet(int row) {
			return dados.get(row);
		}
	}

	public void atualizarPainelSalvar() {

		try {
			CadastroCliente corretores[] = novo_contrato.getCorretores();

			CadastroCliente compradores[] = novo_contrato.getCompradores();
			CadastroCliente vendedores[] = novo_contrato.getVendedores();

			String nome_corretor = "";
			String nome_vendedor1 = "";
			String nome_vendedor2 = "";

			String nome_comprador = "";
			String nome_comprador2 = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretor = corretores[0].getNome_empresarial();
				} else {
					nome_corretor = corretores[0].getNome_fantaia();

				}
			} else {
				nome_corretor = "";
			}

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_comprador = compradores[0].getNome_empresarial();
				} else {
					nome_comprador = compradores[0].getNome_fantaia();

				}
			} else {
				nome_comprador = "";
			}

			if (compradores[1] != null) {
				if (compradores[1].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_comprador2 = compradores[1].getNome_empresarial();
				} else {
					nome_comprador2 = compradores[1].getNome_fantaia();

				}
			} else {
				nome_comprador2 = "";
			}

			if (vendedores[0] != null) {
				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedor1 = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedor1 = vendedores[0].getNome_fantaia();
				}
			} else {
				nome_vendedor1 = "";
			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedor2 = vendedores[1].getNome_empresarial();
				} else {
					nome_vendedor2 = vendedores[1].getNome_fantaia();
				}
			} else {
				nome_vendedor2 = "";
			}

			lblCorretorInfo.setText(nome_corretor);
			lblCompradorInfo.setText(nome_comprador);
			lblCompradorInfo2.setText(nome_comprador2);
			lblVendedor1Info.setText(nome_vendedor1);
			if (vendedores[1] != null)
				lblVendedor2Info.setText(nome_vendedor2);
			else {
				lblVendedor2Info.setText("");

			}

		} catch (Exception e) {

		}
	}

	public void getInformacoes() {

		lblStatusInicial.setText("Status Inicial: Recolher Assinatura");
		novo_contrato.setStatus_contrato(1);

		Locale ptBr = new Locale("pt", "BR");
		NumberFormat z = NumberFormat.getNumberInstance();

		String valorString = "";

		String produto, medida, quantidade, preco, local_retirada, data_contrato, data_entrega, codigo, transgenia;

		codigo = entCodigoContrato.getText();
		novo_contrato.setCodigo(codigo);
		lblCodigoContratoInfo.setText(novo_contrato.getCodigo());

		data_contrato = entDataContrato.getText();
		novo_contrato.setData_contrato(data_contrato);
		lblDataInfo.setText(novo_contrato.getData_contrato());

		// definicoes de subContrato
		int i_tipo_contrato = cBTipoContrato.getSelectedIndex();

		if(tipoContrato_global == 4) {
		if (i_tipo_contrato == 0) {
			// contrato pai de terceiros
			novo_contrato.setSub_contrato(4);
			lblTipoContrato.setText("Tipo de Contrato: Contrato Original de Terceiros");
		} else {
			// contrato pai proprietario
			novo_contrato.setSub_contrato(5);
			lblTipoContrato.setText("Tipo de Contrato: Contrato Original Proprietario");

		}
		}else if(tipoContrato_global == 5) {
			if (i_tipo_contrato == 0) {
				// contrato pai de terceiros
				novo_contrato.setSub_contrato(6);
				lblTipoContrato.setText("Tipo de Contrato: Sub-Contrato de Terceiros");
			} else {
				// contrato pai proprietario
				novo_contrato.setSub_contrato(7);
				lblTipoContrato.setText("Tipo de Contrato: Sub-Contrato Proprietario");

			}
		}

		data_entrega = entDataEntrega.getText();
		novo_contrato.setData_entrega(data_entrega);
		lblDataEntregaInfo.setText(novo_contrato.getData_entrega());

		novo_contrato.setStatus_contrato(1); // aprovado
		lblStatusInicial.setText("Status Inicial: Recolher Assinatura");

		if (rQuanKG.isSelected())
			medida = "KG";
		else if (rQuanS.isSelected())
			medida = "Sacos";
		else
			medida = "TON";
		novo_contrato.setMedida(medida);
		lblUnidadeInfo.setText(novo_contrato.getMedida());

		CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
		novo_contrato.setModelo_safra(safra);
		novo_contrato.setModelo_produto(safra.getProduto());

		novo_contrato.setSafra(safra.getAno_plantio() + "/" + safra.getAno_colheita());
		lblSafraInfo.setText(novo_contrato.getSafra());

		produto = safra.getProduto().getNome_produto();
		transgenia = safra.getProduto().getTransgenia();
		novo_contrato.setProduto(produto);
		lblProdutoInfo.setText(novo_contrato.getProduto());
		lblTransgeniaInfo.setText(transgenia);

		CadastroCliente localRetirada = (CadastroCliente) modelLocalRetirada.getSelectedItem();
		novo_contrato.setLocal_retirada(localRetirada.getNome_fantaia());

		lblLocalRetiradaInfo.setText(novo_contrato.getLocal_retirada());

		novo_contrato.setCliente_retirada(localRetirada);
		novo_contrato.setId_local_retirada(novo_contrato.getCliente_retirada().getId());
		int tipo_entrega = -1;

		if (rBJaDepositada.isSelected()) {
			tipo_entrega = 2;
			lblRecebimentoInfo.setText("Já Depositada");
		} else if (rBPostoSobreRodas.isSelected()) {
			tipo_entrega = 1;
			lblRecebimentoInfo.setText("Sobre Rodas");

		}

		novo_contrato.setTipo_entrega(tipo_entrega);

		novo_contrato.setQuantidade(Double.parseDouble(entQuantidade.getText()));

		lblQuantidadeInfo.setText(z.format((Double.parseDouble(entQuantidade.getText()))));

		novo_contrato.setValor_produto(Double.parseDouble(entPreco.getText()));
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_produto());
		lblValorUnidadeInfo.setText(valorString);

		novo_contrato.setValor_a_pagar(valor_total);
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_a_pagar());
		lblValorTotalInfo.setText(valorString);

		if (rBComissaoSim.isSelected()) {
			lblComissaoInfo.setText("Sim");
		} else {
			lblComissaoInfo.setText("Não");

		}

		novo_contrato.setValor_comissao(valor_total_comissao);
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(novo_contrato.getValor_comissao());
		lblValorTotalComissaoInfo.setText(valorString);

		// pagamentos
		novo_contrato.setPagamentos(pagamentos);

		// adicionais
		ArrayList<String> clausulas_locais = new ArrayList<>();

		clausulas_locais.add(entClausula1.getText().toString());
		clausulas_locais.add(entClausula2.getText().toString());

		if (chBoxClausulaComissao.isSelected()) {
			clausulas_locais.add(entClausula3.getText().toString());
		}

		if (chBoxClausula4.isSelected()) {
			clausulas_locais.add(entClausula4.getText().toString());

		}
		if (chBoxClausula5.isSelected()) {
			clausulas_locais.add(entClausula5.getText().toString());

		}
		if (chBoxClausula6.isSelected()) {
			clausulas_locais.add(entClausula6.getText().toString());

		}

		novo_contrato.setClausulas(clausulas_locais);

		// clausula frete

		if (chkBoxClausulaFrete.isSelected()) {

			novo_contrato.setFrete(cBFrete.getSelectedItem().toString());
			novo_contrato.setClausula_frete(entClausulaFrete.getText());
			lblFreteInfo.setText(cBFrete.getSelectedItem().toString());

		} else {
			novo_contrato.setFrete("");
			lblFreteInfo.setText("Não Especificado");
		}

		// clausula armazenagem

		if (chkBoxClausulaArmazenagem.isSelected()) {
			novo_contrato.setArmazenagem(cBArmazenagem.getSelectedItem().toString());
			novo_contrato.setClausula_armazenagem(entClausulaArmazenagem.getText());
			lblArmazenagemInfo.setText(cBArmazenagem.getSelectedItem().toString());

		} else {
			novo_contrato.setArmazenagem("");
			lblArmazenagemInfo.setText("Não Especificado");
		}

		// pagamentos
		modelo_pagamentos.onRemoveAll();
		BigDecimal valor_total_ja_pago = BigDecimal.ZERO;
		BigDecimal valor_taotal_ha_pagar = BigDecimal.ZERO;

		for (CadastroContrato.CadastroPagamento modelo_pagamento : novo_contrato.getPagamentos()) {
			addModeloPagamentoInfo(modelo_pagamento);
			if (modelo_pagamento.getPagamento_adiantado() == 1) {
				valor_total_ja_pago = valor_total_ja_pago.add(modelo_pagamento.getValor());
			} else {
				valor_taotal_ha_pagar = valor_taotal_ha_pagar.add(modelo_pagamento.getValor());
			}
		}
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ja_pago);
		lblValorTotalJaPagoInfo.setText(valorString);

		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_taotal_ha_pagar);
		lblValorTotalHaPagarInfo.setText(valorString);

	}

	public void addModeloPagamentoInfo(CadastroContrato.CadastroPagamento pagamento) {

		modelo_pagamentos.onAdd(pagamento);

	}

	public boolean salvar() {

		boolean ja_existe = false;
        boolean retorno_global = false;
		// verifica pelo codigo do contrato se ele ja nao esta cadastrado
		GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
		ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();

		for (CadastroContrato ctr : contratos) {
			if (ctr.getCodigo().equals(novo_contrato.getCodigo())) {
				if (ctr.getCodigo().equals(codigo_antigo)) {
					// é o proprio contrato que esta em edicao
					ja_existe = false;
					break;
				} else {
					ja_existe = true;
					break;
				}

			}
		}

		if (!ja_existe) {

			// contrato nao existe, cadastrar
			//JOptionPane.showMessageDialog(null, "Hapto para realizar o cadastro do contrato!");

			// adicionar a tarefa

			ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
			CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

			tarefa.setNome_tarefa("Contrato Importado Manualmente");

			// cria a tarefa de insercao de contrato
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

			tarefas.add(tarefa);

			novo_contrato.setLista_tarefas(tarefas);

			// copiar o arquivo para a pasta do comprador
			CadastroCliente corretores[] = novo_contrato.getCorretores();

			CadastroCliente compradores[] = novo_contrato.getCompradores();
			CadastroCliente vendedores[] = novo_contrato.getVendedores();

			String nome_corretor = "";
			String nome_vendedor1 = "";
			String nome_vendedor2 = "";

			String nome_comprador1 = "";
			String nome_comprador2 = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretor = corretores[0].getNome_empresarial().trim();
				} else {
					nome_corretor = corretores[0].getNome_fantaia().trim();

				}
			}

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_comprador1 = compradores[0].getNome_empresarial().trim();
				} else {
					nome_comprador1 = compradores[0].getNome_fantaia().trim();

				}
			}

			if (compradores[1] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_comprador2 = compradores[1].getNome_empresarial().trim();
				} else {
					nome_comprador2 = compradores[1].getNome_fantaia().trim();

				}
			}

			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedor1 = vendedores[0].getNome_empresarial().trim();
			} else {
				nome_vendedor1 = vendedores[0].getNome_fantaia().trim();
			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedor2 = vendedores[1].getNome_empresarial().trim();
				} else {
					nome_vendedor2 = vendedores[1].getNome_fantaia().trim();
				}
			}

			boolean arquivos_comprador1_criado = false;
			boolean arquivos_comprador2_criado = false;

			boolean arquivos_vendedor1_criado = false;
			boolean arquivos_vendedor2_criado = false;

			String nome_comprador1_arquivo = null;
			String nome_comprador2_arquivo = null;

			String nome_vendedor1_arquivo = null;
			String nome_vendedor2_arquivo = null;

			if (compradores[0].getTipo_pessoa() == 0)
				nome_comprador1_arquivo = compradores[0].getNome_empresarial().trim();
			else
				nome_comprador1_arquivo = compradores[0].getNome_fantaia().trim();

			if (compradores[1] != null) {
				if (compradores[1].getTipo_pessoa() == 0)
					nome_comprador2_arquivo = compradores[1].getNome_empresarial().trim();
				else
					nome_comprador2_arquivo = compradores[1].getNome_fantaia().trim();

			}

			if (vendedores[0] != null) {
				if (vendedores[0].getTipo_pessoa() == 0)
					nome_vendedor1_arquivo = vendedores[0].getNome_empresarial().trim();
				else
					nome_vendedor1_arquivo = vendedores[0].getNome_fantaia().trim();

			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0)
					nome_vendedor2_arquivo = vendedores[1].getNome_empresarial().trim();
				else
					nome_vendedor2_arquivo = vendedores[1].getNome_fantaia().trim();
			} else
				nome_vendedor2_arquivo = null;

			String servidor_unidade = configs_globais.getServidorUnidade();

			String data_entrega = entDataEntrega.getText();
			String data_quebrada[] = data_entrega.split("/");
			String dia = data_quebrada[0];
			String mes = data_quebrada[1];
			String ano = data_quebrada[2];

			if (novo_contrato.getSub_contrato() == 5 || novo_contrato.getSub_contrato() == 4
					|| novo_contrato.getSub_contrato() == 3) {
				// copiar o arquivo para a pasta do comprador, pois e um contrato original
				// é um comprato pai, salvar na pasta do comprador

				if (compradores[0] != null) {
					String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
							+ nome_comprador1_arquivo + "\\contratos" + "\\compra\\" + ano + "\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
					System.out.println(
							"caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);
					String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
							+ nome_comprador1_arquivo + "\\\\contratos" + "\\\\compra\\\\" + ano + "\\\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					String nome_pasta_arquivo = novo_contrato.getCodigo();

					String nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador1_arquivo;

					if (nome_comprador2_arquivo != null) {
						nome_arquivo = nome_arquivo + " E " + nome_comprador2_arquivo;

					}
					nome_arquivo += " X ";

					if (nome_vendedor1_arquivo != null) {
						nome_arquivo = nome_arquivo + nome_vendedor1_arquivo;

					}

					if (nome_vendedor2_arquivo != null) {
						nome_arquivo = nome_arquivo + " E " + nome_vendedor2_arquivo;

					}

					String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo
							+ "\\" + nome_arquivo;

					String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
					String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo;

					// criar o diretorio
					ManipularTxt manipular = new ManipularTxt();
					if (manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
						System.out.println("diretorio criado para o novo contrato");
						// criar pastas documentos e comprovantes
						if (manipular.criarDiretorio(
								caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
							// criar diretorio documentos
							if (manipular.criarDiretorio(
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

							} else {

							}

						} else {

						}

						// copiar arquivo para o novo diretorio
						boolean copiar = false;
						try {
							copiar = manipular.copiarNFe(file_global.getAbsolutePath(),
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (copiar) {
							//JOptionPane.showMessageDialog(null, "Arquivo copiado para a pasta do comprador 1!");
							// inserir o contrato no banco de dados
							novo_contrato.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
							novo_contrato.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo
									+ "\\\\" + nome_arquivo + ".pdf");
							novo_contrato.setNome_arquivo(nome_arquivo + ".pdf");
							
							//salvar na nuvem
							 Nuvem nuvem = new Nuvem();
			            	 nuvem.abrir();
			                 nuvem.testar();
			                
			                boolean retorno = nuvem.carregar(caminho_completo_salvar_contrato_no_hd + ".pdf", nome_arquivo + ".pdf");
			                 if(retorno) {
			                	 // JOptionPane.showMessageDialog(null, "Arquivo salvo na nuvem");
			                	 
			                    	 novo_contrato.setNome_arquivo(nome_arquivo + ".pdf" );
			                	 
			                	 
			                 }else {
			                	 JOptionPane.showMessageDialog(null, "Arquivo não salvo na nuvem");

			                 }

							// copiar para o segundo comprador2

							if (compradores[1] != null) {
								caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
										+ nome_comprador2_arquivo + "\\contratos" + "\\compra\\" + ano + "\\"
										+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
								System.out.println("caminho para salvar o contrato do comprador no hd: "
										+ caminho_salvar_contrato__no_hd);
								caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
										+ nome_comprador2_arquivo + "\\\\contratos" + "\\\\compra\\\\" + ano + "\\\\"
										+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

								nome_pasta_arquivo = novo_contrato.getCodigo();

								nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador2_arquivo;

								if (nome_comprador1_arquivo != null) {
									nome_arquivo = nome_arquivo + " E " + nome_comprador1_arquivo;

								}
								nome_arquivo += " X ";

								if (nome_vendedor1_arquivo != null) {
									nome_arquivo = nome_arquivo + nome_vendedor1_arquivo;

								}

								if (nome_vendedor2_arquivo != null) {
									nome_arquivo = nome_arquivo + " E " + nome_vendedor2_arquivo;

								}

								caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd
										+ nome_pasta_arquivo + "\\" + nome_arquivo;

								caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
										+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
								nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
										+ nome_pasta_arquivo;

								// criar o diretorio
								manipular = new ManipularTxt();
								if (manipular
										.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
									System.out.println("diretorio criado para o novo contrato");
									// criar pastas documentos e comprovantes
									if (manipular.criarDiretorio(
											caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
										// criar diretorio documentos
										if (manipular.criarDiretorio(
												caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

										} else {

										}

									} else {

									}

									// copiar arquivo para o novo diretorio
									copiar = false;
									try {
										copiar = manipular.copiarNFe(file_global.getAbsolutePath(),
												caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
														+ nome_arquivo + ".pdf");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if (copiar) {
										////JOptionPane.showMessageDialog(null,
										////		"Arquivo copiado para a pasta do comprador 2!");
										// inserir o contrato no banco de dados
										novo_contrato.setCaminho_diretorio_contrato2(nome_diretorio_arquivo_contrato);
										novo_contrato.setCaminho_arquivo2(caminho_salvar_contrato_no_banco_dados
												+ nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
										novo_contrato.setNome_arquivo2(nome_arquivo + ".pdf");

										//salvar na nuvem
										  nuvem = new Nuvem();
						            	 nuvem.abrir();
						                 nuvem.testar();
						                
						                 retorno = nuvem.carregar(caminho_completo_salvar_contrato_no_hd + ".pdf", nome_arquivo + ".pdf");
						                 if(retorno) {
						                	 //// JOptionPane.showMessageDialog(null, "Arquivo salvo na nuvem");
						                	 
						                    	 novo_contrato.setNome_arquivo2(nome_arquivo + ".pdf" );
						                	 
						                	 
						                 }else {
						                	 JOptionPane.showMessageDialog(null, "Arquivo não salvo na nuvem");

						                 }
						                 
										GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

										int insercao = gerenciarContratos.inserirContrato(novo_contrato, null);
										if (insercao == 1) {
											JOptionPane.showMessageDialog(null,
													"O Contrato foi salvo no banco de dados");
											retorno_global= true;

										} else {
											JOptionPane.showMessageDialog(null,
													"O Contrato não foi salvo no banco de dados\nConsulte o administrador");
											retorno_global= false;
										}

									} else {
										JOptionPane.showMessageDialog(null,
												"O arquivo não foi copiado para  nova pasta, tente novamente");
										JOptionPane.showMessageDialog(null,
												"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
														+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
														+ nome_arquivo + ".pdf");

									}

								} else {
									System.out.println("erro ao criar diretorio para o contrato ");
									JOptionPane.showMessageDialog(null,
											"O arquivo não foi copiado para  nova pasta, tente novamente");
									JOptionPane.showMessageDialog(null,
											"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
													+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
													+ nome_arquivo + ".pdf");

								}

							} else {
								GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

								int insercao = gerenciarContratos.inserirContrato(novo_contrato, null);
								if (insercao == 1) {
									JOptionPane.showMessageDialog(null, "O Contrato foi salvo no banco de dados");
									retorno_global= true;

								} else {
             						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados\nConsulte o administrador");
									retorno_global= false;
								}

							}

						} else {
							JOptionPane.showMessageDialog(null,
									"O arquivo não foi copiado para  nova pasta, tente novamente");
							JOptionPane.showMessageDialog(null,
									"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
											+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo
											+ ".pdf");
						}

					} else {
						System.out.println("erro ao criar diretorio para o contrato ");
						JOptionPane.showMessageDialog(null,
								"O arquivo não foi copiado para  nova pasta, tente novamente");
						JOptionPane.showMessageDialog(null, "Origem: " + file_global.getAbsolutePath() + "\ndestino: "
								+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
					}

				}else {
				}

			} else if (novo_contrato.getSub_contrato() == 6 || novo_contrato.getSub_contrato() == 7) {
				// copiar o arquivo para a pasta do vendedor pois e um subcontrato
				if (vendedores[0] != null) {
					String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
							+ nome_vendedor1_arquivo + "\\contratos" + "\\venda\\" + ano + "\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
					System.out.println(
							"caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);
					String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
							+ nome_vendedor1_arquivo + "\\\\contratos" + "\\\\venda\\\\" + ano + "\\\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					String nome_pasta_arquivo = novo_contrato.getCodigo();

					String nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador1_arquivo;

					if (nome_comprador2_arquivo != null) {
						nome_arquivo = nome_arquivo + " E " + nome_comprador2_arquivo;

					}
					nome_arquivo += " X ";

					if (nome_vendedor1_arquivo != null) {
						nome_arquivo = nome_arquivo + nome_vendedor1_arquivo;

					}

					if (nome_vendedor2_arquivo != null) {
						nome_arquivo = nome_arquivo + " E " + nome_vendedor2_arquivo;

					}

					String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo
							+ "\\" + nome_arquivo;

					String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
					String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo;

					// criar o diretorio
					ManipularTxt manipular = new ManipularTxt();
					if (manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
						System.out.println("diretorio criado para o novo contrato");
						// criar pastas documentos e comprovantes
						if (manipular.criarDiretorio(
								caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
							// criar diretorio documentos
							if (manipular.criarDiretorio(
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

							} else {

							}

						} else {

						}

						// copiar arquivo para o novo diretorio
						boolean copiar = false;
						try {
							copiar = manipular.copiarNFe(file_global.getAbsolutePath(),
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (copiar) {
							//JOptionPane.showMessageDialog(null, "Arquivo copiado para a pasta do comprador 1!");
							// inserir o contrato no banco de dados
							novo_contrato.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
							novo_contrato.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo
									+ "\\\\" + nome_arquivo + ".pdf");
							novo_contrato.setNome_arquivo(nome_arquivo + ".pdf");
							
							//salvar na nuvem
							 Nuvem nuvem = new Nuvem();
			            	 nuvem.abrir();
			                 nuvem.testar();
			                
			                boolean retorno = nuvem.carregar(caminho_completo_salvar_contrato_no_hd + ".pdf", nome_arquivo + ".pdf");
			                 if(retorno) {
			                	 // JOptionPane.showMessageDialog(null, "Arquivo salvo na nuvem");
			                	 
			                    	 novo_contrato.setNome_arquivo(nome_arquivo + ".pdf" );
			                	 
			                	 
			                 }else {
			                	 JOptionPane.showMessageDialog(null, "Arquivo não salvo na nuvem");

			                 }

							// copiar para o segundo comprador2

							if (vendedores[1] != null) {
								caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
										+ nome_vendedor2_arquivo + "\\contratos" + "\\venda\\" + ano + "\\"
										+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
								System.out.println("caminho para salvar o contrato do comprador no hd: "
										+ caminho_salvar_contrato__no_hd);
								caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
										+ nome_vendedor2_arquivo + "\\\\contratos" + "\\\\venda\\\\" + ano + "\\\\"
										+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

								nome_pasta_arquivo = novo_contrato.getCodigo();

								nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador2_arquivo;

								if (nome_comprador1_arquivo != null) {
									nome_arquivo = nome_arquivo + " E " + nome_comprador1_arquivo;

								}
								nome_arquivo += " X ";

								if (nome_vendedor1_arquivo != null) {
									nome_arquivo = nome_arquivo + nome_vendedor1_arquivo;

								}

								if (nome_vendedor2_arquivo != null) {
									nome_arquivo = nome_arquivo + " E " + nome_vendedor2_arquivo;

								}

								caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd
										+ nome_pasta_arquivo + "\\" + nome_arquivo;

								caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
										+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
								nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
										+ nome_pasta_arquivo;

								// criar o diretorio
								manipular = new ManipularTxt();
								if (manipular
										.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
									System.out.println("diretorio criado para o novo contrato");
									// criar pastas documentos e comprovantes
									if (manipular.criarDiretorio(
											caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
										// criar diretorio documentos
										if (manipular.criarDiretorio(
												caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

										} else {

										}

									} else {

									}

									// copiar arquivo para o novo diretorio
									copiar = false;
									try {
										copiar = manipular.copiarNFe(file_global.getAbsolutePath(),
												caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
														+ nome_arquivo + ".pdf");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if (copiar) {
										////JOptionPane.showMessageDialog(null,
										////		"Arquivo copiado para a pasta do comprador 2!");
										// inserir o contrato no banco de dados
										novo_contrato.setCaminho_diretorio_contrato2(nome_diretorio_arquivo_contrato);
										novo_contrato.setCaminho_arquivo2(caminho_salvar_contrato_no_banco_dados
												+ nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
										novo_contrato.setNome_arquivo2(nome_arquivo + ".pdf");

										//salvar na nuvem
										  nuvem = new Nuvem();
						            	 nuvem.abrir();
						                 nuvem.testar();
						                
						                 retorno = nuvem.carregar(caminho_completo_salvar_contrato_no_hd + ".pdf", nome_arquivo + ".pdf");
						                 if(retorno) {
						                	 //// JOptionPane.showMessageDialog(null, "Arquivo salvo na nuvem");
						                	 
						                    	 novo_contrato.setNome_arquivo2(nome_arquivo + ".pdf" );
						                	 
						                	 
						                 }else {
						                	 JOptionPane.showMessageDialog(null, "Arquivo não salvo na nuvem");

						                 }
						                 
										GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

										int insercao = gerenciarContratos.inserirContrato(novo_contrato, null);
										if (insercao == 1) {
											JOptionPane.showMessageDialog(null,
													"O Contrato foi salvo no banco de dados");
											retorno_global= true;

										} else {
											JOptionPane.showMessageDialog(null,
													"O Contrato não foi salvo no banco de dados\nConsulte o administrador");
											retorno_global= false;
										}

									} else {
										JOptionPane.showMessageDialog(null,
												"O arquivo não foi copiado para  nova pasta, tente novamente");
										JOptionPane.showMessageDialog(null,
												"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
														+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
														+ nome_arquivo + ".pdf");

									}

								} else {
									System.out.println("erro ao criar diretorio para o contrato ");
									JOptionPane.showMessageDialog(null,
											"O arquivo não foi copiado para  nova pasta, tente novamente");
									JOptionPane.showMessageDialog(null,
											"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
													+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
													+ nome_arquivo + ".pdf");

								}

							} else {
								GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

								int insercao = -1;
								
								if(contrato_pai_local != null) {
									 insercao = gerenciarContratos.inserirContrato(novo_contrato, contrato_pai_local);

								}else {
									 insercao = gerenciarContratos.inserirContrato(novo_contrato, null);
								}
								if (insercao == 1) {
									JOptionPane.showMessageDialog(null, "O Contrato foi salvo no banco de dados");
									retorno_global= true;

								} else {
             						JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados\nConsulte o administrador");
									retorno_global= false;
								}

							}

						} else {
							JOptionPane.showMessageDialog(null,
									"O arquivo não foi copiado para  nova pasta, tente novamente");
							JOptionPane.showMessageDialog(null,
									"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
											+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo
											+ ".pdf");
						}

					} else {
						System.out.println("erro ao criar diretorio para o contrato ");
						JOptionPane.showMessageDialog(null,
								"O arquivo não foi copiado para  nova pasta, tente novamente");
						JOptionPane.showMessageDialog(null, "Origem: " + file_global.getAbsolutePath() + "\ndestino: "
								+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
					}

				}else {
				}
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"O Contrato de codigo " + novo_contrato.getCodigo() + " já esta cadastrado!");
			retorno_global= true;

		}
		return retorno_global;

	}

	
	
	
	public void atualizar() {
		//realiza o backup dos compradores
		int retorno_final = -1;
		boolean proceder = false;
		
		
			JOptionPane.showMessageDialog(null, "Esta em modo edicao");
			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			//pegar os caminhso antigos
			CadastroContrato contrato_antigo = gerenciar.getContrato(novo_contrato.getId());
			
			//esta em edicao, fazer o backup dos comprovantes e documentos
			String caminho_diretorio = contrato_antigo.getCaminho_diretorio_contrato();
			String caminho_diretorio2 = contrato_antigo.getCaminho_diretorio_contrato2();
			
			String caminho_arquivo = contrato_antigo.getCaminho_arquivo();
			String caminho_arquivo2 = contrato_antigo.getCaminho_arquivo2();
			
			String nome_arquivo = contrato_antigo.getNome_arquivo();
			String nome_arquivo2 = contrato_antigo.getNome_arquivo2();
			
			if(caminho_diretorio != null && nome_arquivo != null) {
          
				
				ManipularTxt manipular_bkp = new ManipularTxt();
				 diretorio_pasta_bkp1 = "C:\\ProgramData\\E-Contract\\bkp\\" + novo_contrato.getCodigo() + new Random().nextInt();
				boolean bkp_criado = manipular_bkp.criar_bkp_diretorio(servidor_unidade + caminho_diretorio, diretorio_pasta_bkp1);
				if(bkp_criado) {
					
				String BaseDados_DiretorioArquivo =  servidor_unidade + caminho_arquivo;
				System.out.println("caminho do arquivo completo para apagar: " + BaseDados_DiretorioArquivo);
				ManipularTxt manipular_apagar = new ManipularTxt();
						//apagar o diretorio
						if(manipular_apagar.limparDiretorio(new File(servidor_unidade + caminho_diretorio))) {
						  //Diretorio foi excluido
							Nuvem nuvem = new Nuvem();
					         nuvem.abrir();
					         nuvem.testar();
					         nuvem.listar();
					         nuvem.deletarArquivo(nome_arquivo);
					       JOptionPane.showMessageDialog(null, "Os arquivos do alvo 1 foram apagados da memoria e da nuvem!");
						
					       proceder = true;
						}
						else {
							proceder = false;
							System.out.println("Erro ao excluir o direrorio do contrato");

						}
					
				
			}
				
			}
			if(caminho_diretorio2 != null && caminho_diretorio2.length() > 20 && nome_arquivo2 != null ) {

				ManipularTxt manipular_bkp = new ManipularTxt();
				diretorio_pasta_bkp2 =  "C:\\ProgramData\\E-Contract\\bkp\\" + novo_contrato.getCodigo() + new Random().nextInt();
				boolean bkp_criado = manipular_bkp.criar_bkp_diretorio(servidor_unidade + caminho_diretorio2, diretorio_pasta_bkp2);
				if(bkp_criado) {
					
				String BaseDados_DiretorioArquivo =  servidor_unidade + caminho_arquivo2;
				System.out.println("caminho do arquivo completo para apagar: " + BaseDados_DiretorioArquivo);
				ManipularTxt manipular_apagar = new ManipularTxt();
						
						//apagar o diretorio
						if(manipular_apagar.limparDiretorio(new File(servidor_unidade + caminho_diretorio2))) {
						  //Diretorio foi excluido
							Nuvem nuvem = new Nuvem();
					         nuvem.abrir();
					         nuvem.testar();
					         nuvem.listar();
					         nuvem.deletarArquivo(nome_arquivo2);
						       JOptionPane.showMessageDialog(null, "Os arquivos do alvo 2 foram apagados da memoria e da nuvem!");
						
					       proceder = true;
						}
						else {
							proceder = false;
							System.out.println("Erro ao excluir o direrorio do contrato");

						}
					
				
			}
			}
			
			//gerar as novas pastas
			if(rotinasAtualizacao()) {
				//recuperar os backups
				if(novo_contrato.getCompradores()[0] != null && novo_contrato.getCompradores()[1] != null) {
				   ManipularTxt restaurar_bkp = new ManipularTxt();
				   restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\comprovantes", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato() + "\\comprovantes");
				   restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\documentos", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato() + "\\documentos");
				

				   restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\comprovantes", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato2() + "\\comprovantes");
				   restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\documentos", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato2() + "\\documentos");
				  //salvar na nuvem
				
				}else if(novo_contrato.getCompradores()[0] != null && novo_contrato.getCompradores()[1] == null) {
					   ManipularTxt restaurar_bkp = new ManipularTxt();
   
					restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\comprovantes", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato() + "\\comprovantes");
					   restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\documentos", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato() + "\\documentos");
					
				}
				else if(novo_contrato.getCompradores()[0] == null && novo_contrato.getCompradores()[1] != null) {
					   ManipularTxt restaurar_bkp = new ManipularTxt();

					restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\comprovantes", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato2() + "\\comprovantes");
					   restaurar_bkp.criar_bkp_diretorio(diretorio_pasta_bkp1 + "\\documentos", servidor_unidade + novo_contrato.getCaminho_diretorio_contrato2() + "\\documentos");
					
				}
				
				
				DadosGlobais dados = DadosGlobais.getInstance();
				 dados.getTeraGerenciarContratoPai().atualizarContratoLocal(true);
				isto.dispose();
				
				
			}else {
				JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o Administrador");
			}
		
		
		
		
	}
	
	
/*
	public void atualizar() {

		boolean resultado = false;
		File arquivo_temporario = excluir_contrato();

		if (arquivo_temporario != null) {

			boolean ja_existe = false;

			// verifica pelo codigo do contrato se ele ja nao esta cadastrado
			GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();

			for (CadastroContrato ctr : contratos) {
				if (ctr.getCodigo().equals(novo_contrato.getCodigo())) {
					if (ctr.getCodigo().equals(codigo_antigo)) {
						// é o proprio contrato que esta em edicao
						ja_existe = false;
						break;
					} else {
						ja_existe = true;
						break;
					}

				}
			}

			if (!ja_existe) {

				// contrato nao existe, cadastrar

				// adicionar a tarefa

				ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
				CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

				tarefa.setNome_tarefa("Contrato Importado Manualmente atualizou");

				// cria a tarefa de insercao de contrato
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

				tarefas.add(tarefa);

				novo_contrato.setLista_tarefas(tarefas);

				// copiar o arquivo para a pasta do comprador
				CadastroCliente corretores[] = novo_contrato.getCorretores();

				CadastroCliente compradores[] = novo_contrato.getCompradores();
				CadastroCliente vendedores[] = novo_contrato.getVendedores();

				String nome_corretor = "";
				String nome_vendedor1 = "";
				String nome_vendedor2 = "";

				String nome_comprador = "";

				if (corretores[0] != null) {
					if (corretores[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_corretor = corretores[0].getNome_empresarial();
					} else {
						nome_corretor = corretores[0].getNome_fantaia();

					}
				}

				if (compradores[0] != null) {
					if (compradores[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_comprador = compradores[0].getNome_empresarial();
					} else {
						nome_comprador = compradores[0].getNome_fantaia();

					}
				}

				if (vendedores[0].getTipo_pessoa() == 0) {
					nome_vendedor1 = vendedores[0].getNome_empresarial();
				} else {
					nome_vendedor1 = vendedores[0].getNome_fantaia();
				}

				if (vendedores[1] != null) {
					if (vendedores[1].getTipo_pessoa() == 0) {
						nome_vendedor2 = vendedores[1].getNome_empresarial();
					} else {
						nome_vendedor2 = vendedores[1].getNome_fantaia();
					}
				}

				String nome_comprador_arquivo;
				String nome_vendedor_arquivo;

				if (compradores[0].getTipo_pessoa() == 0)
					nome_comprador_arquivo = compradores[0].getNome_empresarial();
				else
					nome_comprador_arquivo = compradores[0].getNome_fantaia();

				if (vendedores[0].getTipo_pessoa() == 0)
					nome_vendedor_arquivo = vendedores[0].getNome_empresarial();
				else
					nome_vendedor_arquivo = vendedores[0].getNome_fantaia();

				String servidor_unidade = configs_globais.getServidorUnidade();

				String data_entrega = entDataEntrega.getText();
				String data_quebrada[] = data_entrega.split("/");
				String dia = data_quebrada[0];
				String mes = data_quebrada[1];
				String ano = data_quebrada[2];

				if (novo_contrato.getSub_contrato() == 5 || novo_contrato.getSub_contrato() == 4
						|| novo_contrato.getSub_contrato() == 3) {
					// copiar o arquivo para a pasta do comprador, pois e um contrato original
					// é um comprato pai, salvar na pasta do comprador

					String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
							+ nome_comprador_arquivo + "\\contratos" + "\\compra\\" + ano + "\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
					System.out.println(
							"caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);
					String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
							+ nome_comprador_arquivo + "\\\\contratos" + "\\\\compra\\\\" + ano + "\\\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					String nome_pasta_arquivo = novo_contrato.getCodigo();

					String nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador_arquivo + " x "
							+ nome_vendedor_arquivo;

					String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo
							+ "\\" + nome_arquivo;

					String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
					String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo;

					// criar o diretorio
					ManipularTxt manipular = new ManipularTxt();
					if (manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
						System.out.println("diretorio criado para o novo contrato");
						// criar pastas documentos e comprovantes
						if (manipular.criarDiretorio(
								caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
							// criar diretorio documentos
							if (manipular.criarDiretorio(
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

							} else {

							}

						} else {

						}

						// copiar arquivo para o novo diretorio
						boolean copiar = false;
						try {
							copiar = manipular.moverArquivo(arquivo_temporario.getAbsolutePath(),
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (copiar) {
							JOptionPane.showMessageDialog(null, "Arquivo copiado para a nova pasta!");
							// inserir o contrato no banco de dados
							novo_contrato.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
							novo_contrato.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo
									+ "\\\\" + nome_arquivo + ".pdf");
							novo_contrato.setNome_arquivo(nome_arquivo + ".pdf");

							GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

							int atualizacao = gerenciarContratos.atualizarContrato(novo_contrato, pagamento_a_excluir);
							if (atualizacao == 5) {
								JOptionPane.showMessageDialog(null, "O Contrato foi atualizado no banco de dados");

								// inserir o contrato na nuvem
								Nuvem nuvem = new Nuvem();
								nuvem.abrir();
								nuvem.testar();

								boolean retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo
										+ "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
								if (retorno) {
									JOptionPane.showMessageDialog(null, "O Contrato  foi salvo na nuvem");
									isto.dispose();
								} else {
									JOptionPane.showMessageDialog(null, "O Contrato não foi salvo na nuvem");

								}

							} else {
								JOptionPane.showMessageDialog(null, "O Contrato não foi salvo no banco de dados");

							}

						} else {
							JOptionPane.showMessageDialog(null,
									"O arquivo não foi copiado para  nova pasta, tente novamente");
							JOptionPane.showMessageDialog(null,
									"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
											+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo
											+ ".pdf");

						}

					} else {
						System.out.println("erro ao criar diretorio para o contrato ");
						JOptionPane.showMessageDialog(null,
								"O arquivo não foi copiado para  nova pasta, tente novamente");
						JOptionPane.showMessageDialog(null, "Origem: " + file_global.getAbsolutePath() + "\ndestino: "
								+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");

					}

				} else if (novo_contrato.getSub_contrato() == 6) {
					// copiar o arquivo para a pasta do vendedor pois e um subcontrato
				}

			} else {
				JOptionPane.showMessageDialog(null,
						"O Contrato de codigo " + novo_contrato.getCodigo() + " já esta cadastrado!");

			}

		} else {
			JOptionPane.showMessageDialog(isto,
					"Houve erro ao excluir os arquivos antigos\nPossivel Corrupção nas tabelas do BD\nConsulte o administrador");
		}
	}
*/
	public void setComprador2(CadastroCliente comprador) {
		novo_contrato.adicionarComprador(1, comprador);
		// lblCodigoContratoComprador.setText(Integer.toString(comprador.getId()));

		cBComprador2.removeAllItems();

		if (comprador.getTipo_pessoa() == 0) // pessoa fisica
		{
			cBComprador2.addItem(comprador.getNome() + " " + comprador.getSobrenome());
			cBComprador2.setSelectedItem(comprador.getNome() + " " + comprador.getSobrenome());
			lblNomeComprador2.setText(comprador.getNome() + " " + comprador.getSobrenome());
			cBComprador2.addItem("Indefinido");

			lblCpfCnpjComprador2.setText(comprador.getCpf());
			System.out.println("O comprador selecionado e uma pessoa fisica");

		} else // pessoa juridica
		{
			System.out.println("O comprador selecionado e uma pessoa juridica");

			cBComprador2.addItem(comprador.getRazao_social());
			cBComprador2.setSelectedItem(comprador.getRazao_social());
			cBComprador2.addItem("Indefinido");

			lblCpfCnpjComprador2.setText(comprador.getCnpj());
			lblNomeComprador2.setText(comprador.getRazao_social());

		}

		lblIEComprador2.setText(comprador.getIe());
		lblEnderecoComprador2.setText(comprador.getRua());
		lblMunicipioComprador2.setText(comprador.getCidade());
		lblEstadoComprador2.setText(comprador.getUf());
		lblBairroComprador2.setText(comprador.getBairro());

	}

	
	public boolean rotinasAtualizacao() {
		boolean ja_existe = false;
        boolean retorno_global = false;
        CadastroContrato contrato_antigo = new GerenciarBancoContratos().getContrato(novo_contrato.getId());
		String nome_arquivo1_antes_atualizar = contrato_antigo.getNome_arquivo();
		String nome_arquivo2_antes_atualizar = contrato_antigo.getNome_arquivo2();

		
		// verifica pelo codigo do contrato se ele ja nao esta cadastrado
		GerenciarBancoContratos gerenciar_cts = new GerenciarBancoContratos();
		ArrayList<CadastroContrato> contratos = gerenciar_cts.getContratos();

		for (CadastroContrato ctr : contratos) {
			if (ctr.getCodigo().equals(novo_contrato.getCodigo())) {
				if (ctr.getCodigo().equals(codigo_antigo)) {
					// é o proprio contrato que esta em edicao
					ja_existe = false;
					break;
				} else {
					ja_existe = true;
					break;
				}

			}
		}

		if (!ja_existe) {

			// contrato nao existe, cadastrar
			JOptionPane.showMessageDialog(null, "Hapto para realizar a atualização do contrato!");

			// adicionar a tarefa

			ArrayList<CadastroContrato.CadastroTarefa> tarefas = new ArrayList<>();
			CadastroContrato.CadastroTarefa tarefa = new CadastroContrato.CadastroTarefa();

			tarefa.setNome_tarefa("Contrato Importado Manualmente atualizado");

			// cria a tarefa de insercao de contrato
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

			tarefas.add(tarefa);

			novo_contrato.setLista_tarefas(tarefas);
			novo_contrato.setGrupo_particular(0);
			// copiar o arquivo para a pasta do comprador
			CadastroCliente corretores[] = novo_contrato.getCorretores();

			CadastroCliente compradores[] = novo_contrato.getCompradores();
			CadastroCliente vendedores[] = novo_contrato.getVendedores();

			String nome_corretor = "";
			String nome_vendedor1 = "";
			String nome_vendedor2 = "";

			String nome_comprador1 = "";
			String nome_comprador2 = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretor = corretores[0].getNome_empresarial().trim();
				} else {
					nome_corretor = corretores[0].getNome_fantaia().trim();

				}
			}

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_comprador1 = compradores[0].getNome_empresarial().trim();
				} else {
					nome_comprador1 = compradores[0].getNome_fantaia().trim();

				}
			}

			if (compradores[1] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_comprador2 = compradores[1].getNome_empresarial().trim();
				} else {
					nome_comprador2 = compradores[1].getNome_fantaia().trim();

				}
			}

			if (vendedores[0].getTipo_pessoa() == 0) {
				nome_vendedor1 = vendedores[0].getNome_empresarial().trim();
			} else {
				nome_vendedor1 = vendedores[0].getNome_fantaia().trim();
			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0) {
					nome_vendedor2 = vendedores[1].getNome_empresarial().trim();
				} else {
					nome_vendedor2 = vendedores[1].getNome_fantaia().trim();
				}
			}

			boolean arquivos_comprador1_criado = false;
			boolean arquivos_comprador2_criado = false;

			boolean arquivos_vendedor1_criado = false;
			boolean arquivos_vendedor2_criado = false;

			String nome_comprador1_arquivo = null;
			String nome_comprador2_arquivo = null;

			String nome_vendedor1_arquivo = null;
			String nome_vendedor2_arquivo = null;

			if (compradores[0].getTipo_pessoa() == 0)
				nome_comprador1_arquivo = compradores[0].getNome_empresarial().trim();
			else
				nome_comprador1_arquivo = compradores[0].getNome_fantaia().trim();

			if (compradores[1] != null) {
				if (compradores[1].getTipo_pessoa() == 0)
					nome_comprador2_arquivo = compradores[1].getNome_empresarial().trim();
				else
					nome_comprador2_arquivo = compradores[1].getNome_fantaia().trim();

			}

			if (vendedores[0] != null) {
				if (vendedores[0].getTipo_pessoa() == 0)
					nome_vendedor1_arquivo = vendedores[0].getNome_empresarial().trim();
				else
					nome_vendedor1_arquivo = vendedores[0].getNome_fantaia().trim();

			}

			if (vendedores[1] != null) {
				if (vendedores[1].getTipo_pessoa() == 0)
					nome_vendedor2_arquivo = vendedores[1].getNome_empresarial().trim();
				else
					nome_vendedor2_arquivo = vendedores[1].getNome_fantaia().trim();
			} else
				nome_vendedor2_arquivo = null;

			String servidor_unidade = configs_globais.getServidorUnidade();

			String data_entrega = entDataEntrega.getText();
			String data_quebrada[] = data_entrega.split("/");
			String dia = data_quebrada[0];
			String mes = data_quebrada[1];
			String ano = data_quebrada[2];

			if (novo_contrato.getSub_contrato() == 5 || novo_contrato.getSub_contrato() == 4
					|| novo_contrato.getSub_contrato() == 3) {
				// copiar o arquivo para a pasta do comprador, pois e um contrato original
				// é um comprato pai, salvar na pasta do comprador

				if (compradores[0] != null) {
					String caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
							+ nome_comprador1_arquivo + "\\contratos" + "\\compra\\" + ano + "\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
					System.out.println(
							"caminho para salvar o contrato do comprador no hd: " + caminho_salvar_contrato__no_hd);
					String caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
							+ nome_comprador1_arquivo + "\\\\contratos" + "\\\\compra\\\\" + ano + "\\\\"
							+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

					String nome_pasta_arquivo = novo_contrato.getCodigo();

					String nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador1_arquivo;

					if (nome_comprador2_arquivo != null) {
						nome_arquivo = nome_arquivo + " E " + nome_comprador2_arquivo;

					}
					nome_arquivo += " X ";

					if (nome_vendedor1_arquivo != null) {
						nome_arquivo = nome_arquivo + nome_vendedor1_arquivo;

					}

					if (nome_vendedor2_arquivo != null) {
						nome_arquivo = nome_arquivo + " E " + nome_vendedor2_arquivo;

					}

					String caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd + nome_pasta_arquivo
							+ "\\" + nome_arquivo;

					String caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
					String nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
							+ nome_pasta_arquivo;

					// criar o diretorio
					ManipularTxt manipular = new ManipularTxt();
					if (manipular.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
						System.out.println("diretorio criado para o novo contrato");
						// criar pastas documentos e comprovantes
						if (manipular.criarDiretorio(
								caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
							// criar diretorio documentos
							if (manipular.criarDiretorio(
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

							} else {

							}

						} else {

						}

						// copiar arquivo para o novo diretorio
						boolean copiar = false;
						try {
							
							
									
							
							copiar = manipular.copiarNFe(diretorio_pasta_bkp1 + "\\" + nome_arquivo1_antes_atualizar,
									caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (copiar) {
							JOptionPane.showMessageDialog(null, "Arquivo copiado para a pasta do comprador 1!");
							// inserir o contrato no banco de dados
							novo_contrato.setCaminho_diretorio_contrato(nome_diretorio_arquivo_contrato);
							novo_contrato.setCaminho_arquivo(caminho_salvar_contrato_no_banco_dados + nome_pasta_arquivo
									+ "\\\\" + nome_arquivo + ".pdf");
							novo_contrato.setNome_arquivo(nome_arquivo + ".pdf");
							
                             //savalar na nuvem
							// inserir o contrato na nuvem
							Nuvem nuvem = new Nuvem();
							nuvem.abrir();
							nuvem.testar();

							boolean retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
							if (retorno) {
								JOptionPane.showMessageDialog(null, "O Contrato do comprador 1 foi salvo na nuvem");
								isto.dispose();
							} else {
								JOptionPane.showMessageDialog(null, "O Contrato do comprador 2 não foi salvo na nuvem");

							}
							// copiar para o segundo comprador2

							if (compradores[1] != null) {
								caminho_salvar_contrato__no_hd = servidor_unidade + "E-contract\\arquivos\\clientes\\"
										+ nome_comprador2_arquivo + "\\contratos" + "\\compra\\" + ano + "\\"
										+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\";
								System.out.println("caminho para salvar o contrato do comprador no hd: "
										+ caminho_salvar_contrato__no_hd);
								caminho_salvar_contrato_no_banco_dados = "E-contract\\\\arquivos\\\\clientes\\\\"
										+ nome_comprador2_arquivo + "\\\\contratos" + "\\\\compra\\\\" + ano + "\\\\"
										+ novo_contrato.getModelo_safra().getProduto().getNome_produto() + "\\\\";

								nome_pasta_arquivo = novo_contrato.getCodigo();

								nome_arquivo = novo_contrato.getCodigo() + " " + nome_comprador2_arquivo;

								if (nome_comprador1_arquivo != null) {
									nome_arquivo = nome_arquivo + " E " + nome_comprador1_arquivo;

								}
								nome_arquivo += " X ";

								if (nome_vendedor1_arquivo != null) {
									nome_arquivo = nome_arquivo + nome_vendedor1_arquivo;

								}

								if (nome_vendedor2_arquivo != null) {
									nome_arquivo = nome_arquivo + " E " + nome_vendedor2_arquivo;

								}

								caminho_completo_salvar_contrato_no_hd = caminho_salvar_contrato__no_hd
										+ nome_pasta_arquivo + "\\" + nome_arquivo;

								caminho_completo_salvar_contrato_no_bando_dados = caminho_salvar_contrato_no_banco_dados
										+ nome_pasta_arquivo + "\\\\" + nome_arquivo;
								nome_diretorio_arquivo_contrato = caminho_salvar_contrato_no_banco_dados
										+ nome_pasta_arquivo;

								// criar o diretorio
								manipular = new ManipularTxt();
								if (manipular
										.criarDiretorio(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\")) {
									System.out.println("diretorio criado para o novo contrato");
									// criar pastas documentos e comprovantes
									if (manipular.criarDiretorio(
											caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\comprovantes")) {
										// criar diretorio documentos
										if (manipular.criarDiretorio(
												caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\documentos")) {

										} else {

										}

									} else {

									}

									// copiar arquivo para o novo diretorio
									copiar = false;
									try {
										
										

										String fonte = diretorio_pasta_bkp2 + "\\" + nome_arquivo2_antes_atualizar;
										
										if(fonte != null && fonte.length() > 20) {
									
											String destino = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
												+ nome_arquivo + ".pdf";
											copiar = manipular.copiarNFe(fonte,destino);

										
										}else {
											try {
												
												 fonte = diretorio_pasta_bkp1 + "\\" + nome_arquivo1_antes_atualizar;
												String destino = caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
														+ nome_arquivo + ".pdf";

												copiar = manipular.copiarNFe(fonte,destino);
											} catch (Exception e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
										
									} catch (Exception e) {
										

									}
									if (copiar) {
										JOptionPane.showMessageDialog(null,
												"Arquivo copiado para a pasta do comprador 2!");
										// inserir o contrato no banco de dados
										novo_contrato.setCaminho_diretorio_contrato2(nome_diretorio_arquivo_contrato);
										novo_contrato.setCaminho_arquivo2(caminho_salvar_contrato_no_banco_dados
												+ nome_pasta_arquivo + "\\\\" + nome_arquivo + ".pdf");
										novo_contrato.setNome_arquivo2(nome_arquivo + ".pdf");

										 nuvem = new Nuvem();
										nuvem.abrir();
										nuvem.testar();

										 retorno = nuvem.carregar(caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf", nome_arquivo + ".pdf");
										if (retorno) {
											JOptionPane.showMessageDialog(null, "O Contrato do comprador 2 foi salvo na nuvem");
											isto.dispose();
										} else {
											JOptionPane.showMessageDialog(null, "O Contrato do comprador 2 não foi salvo na nuvem");

										}
									
						                 
										GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

										int insercao = gerenciarContratos.atualizarContrato(contrato_pai_local, pagamento_a_excluir);
										if (insercao == 5) {
											JOptionPane.showMessageDialog(null,
													"O Contrato foi atualizado no banco de dados");
											retorno_global= true;

										} else {
											JOptionPane.showMessageDialog(null,
													"O Contrato não foi atualizado no banco de dados\nConsulte o administrador");
											retorno_global= false;
										}

									} else {
										JOptionPane.showMessageDialog(null,
												"O arquivo não foi copiado para  nova pasta, tente novamente");
										

									}

								} else {
									System.out.println("erro ao criar diretorio para o contrato ");
									JOptionPane.showMessageDialog(null,
											"O arquivo não foi copiado para  nova pasta, tente novamente");
									JOptionPane.showMessageDialog(null,
											"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
													+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\"
													+ nome_arquivo + ".pdf");

								}

							} else {
								novo_contrato.setCaminho_arquivo2(null);
								novo_contrato.setNome_arquivo2(null);
								novo_contrato.setCaminho_diretorio_contrato2(null);

								GerenciarBancoContratos gerenciarContratos = new GerenciarBancoContratos();

								int insercao = gerenciarContratos.atualizarContrato(novo_contrato, pagamento_a_excluir);
								if (insercao == 5) {
									JOptionPane.showMessageDialog(null, "O Contrato foi atualizado no banco de dados");
									retorno_global= true;

								} else {
             						JOptionPane.showMessageDialog(null, "O Contrato não foi atualizado no banco de dados\nConsulte o administrador");
									retorno_global= false;
								}

							}

						} else {
							JOptionPane.showMessageDialog(null,
									"O arquivo não foi copiado para  nova pasta, tente novamente");
							JOptionPane.showMessageDialog(null,
									"Origem: " + file_global.getAbsolutePath() + "\ndestino: "
											+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo
											+ ".pdf");
						}

					} else {
						System.out.println("erro ao criar diretorio para o contrato ");
						JOptionPane.showMessageDialog(null,
								"O arquivo não foi copiado para  nova pasta, tente novamente");
						JOptionPane.showMessageDialog(null, "Origem: " + file_global.getAbsolutePath() + "\ndestino: "
								+ caminho_salvar_contrato__no_hd + nome_pasta_arquivo + "\\" + nome_arquivo + ".pdf");
					}

				}else {
				}

			} else if (novo_contrato.getSub_contrato() == 6) {
				// copiar o arquivo para a pasta do vendedor pois e um subcontrato
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"O Contrato de codigo " + novo_contrato.getCodigo() + " já esta cadastrado!");
			retorno_global= true;

		}
		return retorno_global;
	}
	
	
	public CadastroContrato getDadosSalvar() {
		CadastroCliente compradores[] = novo_contrato.getCompradores();
		CadastroCliente vendedores[] = novo_contrato.getVendedores();

		if (compradores[0] == null || vendedores[0] == null) {
			JOptionPane.showMessageDialog(isto, "Comprador 1 e Vendedor 1 não podem ser nulos");
			return null;
		} else {

			String produto, medida, quantidade, preco, local_retirada, data_contrato, data_entrega, codigo;

			try {
			codigo = entCodigoContrato.getText();
			
			if(codigo != null && codigo.length() > 8) {
				
			}else {
				JOptionPane.showMessageDialog(isto, "Código do contrato com formato inválido, use (ANO-CODIGO)");
				return null;
			}
			
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Código do contrato Inválido!");
				return null;
			}
			
			
			data_entrega = entDataEntrega.getText();
			
			try {
				LocalDate data_ctr = LocalDate.parse(data_entrega, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				
				}catch(Exception e) {
					JOptionPane.showMessageDialog(isto, "Data da Entrega Inválida!");
					return null;
				}
			
			
			
			novo_contrato.setData_entrega(data_entrega);
			int i_tipo_contrato = cBTipoContrato.getSelectedIndex();

			if(tipoContrato_global == 4) {
			// definicoes de contrato
			if (i_tipo_contrato == 0) {
				// contrato pai de terceiros
				novo_contrato.setSub_contrato(4);
			} else {
				// contrato pai proprietario
				novo_contrato.setSub_contrato(5);

			}}else if(tipoContrato_global == 5) {
				//sub contratos
				if (i_tipo_contrato == 0) {
					// sub contrato  de terceiros
					novo_contrato.setSub_contrato(6);
				} else {
					// sub contrato pai proprietario
					novo_contrato.setSub_contrato(7);
			}
			}

			data_contrato = entDataContrato.getText();

			try {
			LocalDate data_ctr = LocalDate.parse(data_contrato, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Data do contrato Inválida!");
				return null;
			}
			
			novo_contrato.setData_contrato(data_contrato);

			novo_contrato.setStatus_contrato(1); // aprovado
			lblStatusInicial.setText("Status Inicial: Recolher Assinatura");

			novo_contrato.setCodigo(codigo);
			if (rQuanKG.isSelected())
				medida = "KG";
			else if (rQuanS.isSelected())
				medida = "Sacos";
			else
				medida = "TON";
			novo_contrato.setMedida(medida);

			try {
			CadastroSafra safra = (CadastroSafra) modelSafra.getSelectedItem();
			novo_contrato.setModelo_safra(safra);
			novo_contrato.setModelo_produto(safra.getProduto());

			novo_contrato.setSafra(safra.getAno_plantio() + "/" + safra.getAno_colheita());

			produto = safra.getProduto().getNome_produto();
			novo_contrato.setProduto(produto);
			
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Safra inválida");
				return null;
			}
			

			CadastroCliente localRetirada = (CadastroCliente) modelLocalRetirada.getSelectedItem();
			novo_contrato.setLocal_retirada(localRetirada.getNome_fantaia());

			novo_contrato.setCliente_retirada(localRetirada);
			novo_contrato.setId_local_retirada(novo_contrato.getCliente_retirada().getId());
			int tipo_entrega = -1;

			if (rBJaDepositada.isSelected()) {
				tipo_entrega = 2;
			} else if (rBPostoSobreRodas.isSelected()) {
				tipo_entrega = 1;
			}

			novo_contrato.setTipo_entrega(tipo_entrega);

			try {
			novo_contrato.setQuantidade(Double.parseDouble(entQuantidade.getText()));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Quantidade inválida!");
				return null;
			}
			
			try {
			novo_contrato.setValor_produto(Double.parseDouble(entPreco.getText()));
			}catch(Exception e) {
				JOptionPane.showMessageDialog(isto, "Preço inválido!");
				return null;
			}
			
			
			novo_contrato.setValor_a_pagar(valor_total);
			
			
			novo_contrato.setValor_comissao(valor_total_comissao);

			// pagamentos
			novo_contrato.setPagamentos(pagamentos);

			// adicionais
			ArrayList<String> clausulas_locais = new ArrayList<>();

			clausulas_locais.add(entClausula1.getText().toString());
			clausulas_locais.add(entClausula2.getText().toString());

			if (chBoxClausulaComissao.isSelected()) {
				clausulas_locais.add(entClausula3.getText().toString());
			}

			if (chBoxClausula4.isSelected()) {
				clausulas_locais.add(entClausula4.getText().toString());

			}
			if (chBoxClausula5.isSelected()) {
				clausulas_locais.add(entClausula5.getText().toString());

			}
			if (chBoxClausula6.isSelected()) {
				clausulas_locais.add(entClausula6.getText().toString());

			}

			novo_contrato.setClausulas(clausulas_locais);

			// clausula frete

			if (chkBoxClausulaFrete.isSelected()) {

				novo_contrato.setFrete(cBFrete.getSelectedItem().toString());
				novo_contrato.setClausula_frete(entClausulaFrete.getText());

			} else {
				novo_contrato.setFrete("");
			}

			// clausula armazenagem

			if (chkBoxClausulaArmazenagem.isSelected()) {
				novo_contrato.setArmazenagem(cBArmazenagem.getSelectedItem().toString());
				novo_contrato.setClausula_armazenagem(entClausulaArmazenagem.getText());

			} else {
				novo_contrato.setArmazenagem("");
			}

			try {

			} catch (Exception i) {
				System.out.println("erro ao elaborar contrato, erro: " + i.getMessage());
				JOptionPane.showMessageDialog(null,
						"Erro fatal, consulte o administrador do sistema\nErro: " + i.getMessage());
				isto.dispose();
				DadosGlobais dados = DadosGlobais.getInstance();
				dados.atualizarGraficosTelaPrincipal();

			}
			
			return novo_contrato;
		}
	}
}
