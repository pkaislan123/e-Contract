
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import keeptoo.KGradientPanel;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JComboBox;

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
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.cadastros.SaldoInstituicaoBancaria;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.classesExtras.RenderizadorExtrato;
import main.java.classesExtras.RenderizadorNotas;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
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

public class TelaGerenciarInstituicaoBancaria extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private TelaGerenciarInstituicaoBancaria isto;
	private KGradientPanel menu_lateral;
	private JPanel panel_docs;
	private JTree arvore_documentos;
	private InstituicaoBancaria caixa_local;
	ArrayList<FinanceiroPagamentoCompleto> lista_extrato = new ArrayList<FinanceiroPagamentoCompleto>();
	private  DefaultListModel<FinanceiroPagamentoCompleto> listModelGlobal;

	DefaultMutableTreeNode no_comprovantes;
	DefaultMutableTreeNode no_docs_pessoais;
	DefaultMutableTreeNode no_outros;
	private DefaultMutableTreeNode no_selecionado;
	private JComboBox cBTipoDocumento;
	private TelaTodasNotasFiscais telaTodasNotasFiscais;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private JTextArea entDescricaoDocumento;
	private TelaRomaneios telaRomaneio;
	private JLabel lblDadosBanco, lblSaldo, lblDadosConta, lblDadosTitular;
	private JLabel lblSaldoInicial, lblValorTotalDespesas, lblValorTotalReceitas, lblSaldoAtual;
	private RenderizadorExtrato render;

	public TelaGerenciarInstituicaoBancaria(InstituicaoBancaria caixa, Window janela_pai) {
		// setModal(true);

		isto = this;
		caixa_local = caixa;
		setResizable(true);
		setTitle("E-Contract - Gerenciar Conta Bancária");

		setBackground(new Color(255, 255, 255));
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
		
		
		painelPrincipal.setForeground(Color.BLACK);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(new MigLayout("", "[200px:n][grow][][grow][119px][113px]", "[grow][][11px][34px]"));

		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.setBorder(null);
		panel_1.setMinimumSize(new Dimension(0, 0));
		panel_1.setBackground(Color.WHITE);
		painelPrincipal.add(panel_1, "cell 1 0 5 4,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		KGradientPanel panelTopo = new KGradientPanel();
		panel_1.add(panelTopo, "cell 0 0,grow");
		panelTopo.kStartColor = new Color(0, 51, 0);
		panelTopo.kEndColor = new Color(0, 51, 0);
		panelTopo.setLayout(
				new MigLayout("", "[263px][][][][][][][][][][][][][][][][][][][][][][][][][]", "[31px][][][][][]"));

		JLabel lblNewLabel = new JLabel("Minha Conta:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.WHITE);
		panelTopo.add(lblNewLabel, "cell 0 0,grow");

		lblDadosBanco = new JLabel("");
		lblDadosBanco.setForeground(Color.WHITE);
		lblDadosBanco.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelTopo.add(lblDadosBanco, "cell 0 1");

		JLabel lblNewLabel_1_1 = new JLabel("Saldo:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 32));
		panelTopo.add(lblNewLabel_1_1, "cell 9 1 5 2,alignx right");

		lblSaldo = new JLabel("R$ 100.000.000,00");
		lblSaldo.setForeground(Color.WHITE);
		lblSaldo.setFont(new Font("SansSerif", Font.BOLD, 36));
		panelTopo.add(lblSaldo, "cell 14 1 12 2,alignx left");

		lblDadosConta = new JLabel("");
		lblDadosConta.setForeground(Color.WHITE);
		lblDadosConta.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelTopo.add(lblDadosConta, "cell 0 2");

		lblDadosTitular = new JLabel("");
		lblDadosTitular.setForeground(Color.WHITE);
		lblDadosTitular.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panelTopo.add(lblDadosTitular, "cell 0 3");

		JPanel painelAreaTransferencia = new JPanel();
		painelAreaTransferencia.setBackground(Color.WHITE);
		panel_1.add(painelAreaTransferencia, "cell 0 1,grow");
		painelAreaTransferencia.setLayout(new MigLayout("", "[][]", "[grow]"));

		JPanel painelExtratos = new JPanel();
		painelExtratos.setAlignmentY(Component.TOP_ALIGNMENT);
		painelExtratos.setAlignmentX(Component.LEFT_ALIGNMENT);
		painelExtratos.setOpaque(false);
		painelExtratos.setBorder(null);
		// panel_1.add(painelExtratos, "cell 1 1,grow");
		painelExtratos.setVisible(false);
		painelExtratos.setBackground(Color.WHITE);
		painelExtratos.setForeground(Color.WHITE);
		painelExtratos.setLayout(new MigLayout("", "[][648.00]", "[][grow]"));

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		painelExtratos.add(panel_2, "cell 0 0 1 2,grow");
		panel_2.setBackground(Color.WHITE);
		panel_2.setLayout(new MigLayout("", "[grow][]", "[][][][][][:20px:20px,grow][grow]"));

		JLabel lblNewLabel_1 = new JLabel("Saldo Inicial:");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_2.add(lblNewLabel_1, "cell 0 0,alignx right");

		lblSaldoInicial = new JLabel("R$ 100.00.000.0,00");
		lblSaldoInicial.setForeground(Color.BLACK);
		lblSaldoInicial.setFont(new Font("Arial", Font.BOLD, 24));
		panel_2.add(lblSaldoInicial, "cell 1 0");

		JLabel lblNewLabel_1_2 = new JLabel("Valor Total Despesas:");
		lblNewLabel_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_2.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_2.add(lblNewLabel_1_2, "cell 0 1,alignx right");

		lblValorTotalDespesas = new JLabel("R$ 100.00.000.0,00");
		lblValorTotalDespesas.setForeground(Color.BLACK);
		lblValorTotalDespesas.setFont(new Font("Arial", Font.BOLD, 24));
		panel_2.add(lblValorTotalDespesas, "cell 1 1");

		JLabel lblNewLabel_1_2_1 = new JLabel("Valor Total Receitas:");
		lblNewLabel_1_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_2.add(lblNewLabel_1_2_1, "cell 0 2,alignx right");

		lblValorTotalReceitas = new JLabel("R$ 100.00.000.0,00");
		lblValorTotalReceitas.setForeground(Color.BLACK);
		lblValorTotalReceitas.setFont(new Font("Arial", Font.BOLD, 24));
		panel_2.add(lblValorTotalReceitas, "cell 1 2");

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Saldo Atual:");
		lblNewLabel_1_2_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_2.add(lblNewLabel_1_2_1_1, "cell 0 4,alignx right");

		lblSaldoAtual = new JLabel("R$ 100.00.000.0,00");
		lblSaldoAtual.setForeground(Color.BLACK);
		lblSaldoAtual.setFont(new Font("Arial", Font.BOLD, 24));
		panel_2.add(lblSaldoAtual, "cell 1 4");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBackground(Color.WHITE);
		panel_2.add(panel_3, "cell 0 6 2 1,grow");
		panel_3.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lblNewLabel_2 = new JLabel("Filtros");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_2, "cell 0 0");
		
		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Tipo Lançamento:");
		lblNewLabel_1_2_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1, "cell 0 2,alignx right");
		
		JComboBox cbTipoLancamento = new JComboBox();
		cbTipoLancamento.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_3.add(cbTipoLancamento, "cell 1 2,growx");
		
		JLabel lblNewLabel_1_2_1_1_1_1 = new JLabel("Condição do Pagamento:");
		lblNewLabel_1_2_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1, "cell 0 3,alignx trailing");
		
		JComboBox cBCondicaoPagamento = new JComboBox();
		cBCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_3.add(cBCondicaoPagamento, "cell 1 3,growx");
		
		JLabel lblNewLabel_1_2_1_1_1_1_1 = new JLabel("Status Pagamento:");
		lblNewLabel_1_2_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1_1, "cell 0 4,alignx trailing");
		
		JComboBox cBStatusPagamento = new JComboBox();
		cBStatusPagamento.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_3.add(cBStatusPagamento, "cell 1 4,growx");

		JList lista = new JList<>();
		lista.setOpaque(false);
		lista.setBackground(Color.WHITE);

		JScrollPane scrollPaneListaExtrato = new JScrollPane(lista);
		/*
		scrollPaneListaExtrato.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPaneListaExtrato.getHorizontalScrollBar().setBackground(Color.WHITE);
		scrollPaneListaExtrato.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Color.BLUE;
		    }
		});
		*/
		painelExtratos.add(scrollPaneListaExtrato, "cell 1 1,grow");
		scrollPaneListaExtrato.getViewport().setBackground(Color.white);
		scrollPaneListaExtrato.getViewport().setOpaque(false);
		listModelGlobal = new DefaultListModel<FinanceiroPagamentoCompleto>();

		render = new RenderizadorExtrato();

		pesquisarExtrato();
		lista.setModel(listModelGlobal);
		lista.setCellRenderer(render);

		// painelPrincipal.add(painelDadosIniciais, "flowx,cell 3 0 3 1,grow");

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(0, 0, 153));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroInstituicaoBancaria tela = new TelaFinanceiroCadastroInstituicaoBancaria(1, caixa,
						isto);
				tela.setVisible(true);
			}
		});
		panelTopo.add(btnEditar, "cell 0 5,alignx right,growy");

		menu_lateral = new KGradientPanel();
		menu_lateral.kStartColor = new Color(0, 51, 51);
		menu_lateral.kEndColor = new Color(0, 51, 0);
		painelPrincipal.add(menu_lateral, "cell 0 0 1 4,grow");
		menu_lateral.setLayout(new MigLayout("", "[181px]", "[grow][]"));

		JPanelTransparent panel = new JPanelTransparent();
		menu_lateral.add(panel, "cell 0 0 1 2,growx,aligny center");
		panel.setLayout(new MigLayout("", "[161px]", "[20px][20px][20px][20px]"));

		JLabel btnContratos = new JLabel("Extrato");
		btnContratos.setForeground(Color.WHITE);
		btnContratos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnContratos.setBackground(new Color(0, 0, 0, 100));
		panel.add(btnContratos, "cell 0 1,growx,aligny top");

		JLabel btnDocumentos = new JLabel("Documentos");
		btnDocumentos.setForeground(Color.WHITE);
		btnDocumentos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDocumentos.setBackground(new Color(0, 0, 0, 100));
		panel.add(btnDocumentos, "cell 0 3,growx,aligny top");

		JLabel btnDeposito = new JLabel("Lançamentos Futuros");
		btnDeposito.setForeground(Color.WHITE);
		btnDeposito.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDeposito.setBackground(new Color(0, 0, 0, 100));
		panel.add(btnDeposito, "cell 0 2,growx,aligny top");

		JPanel painelDeposito = new JPanel();
		painelDeposito.setBackground(Color.WHITE);
		painelDeposito.setVisible(false);
		painelDeposito.setEnabled(false);
		painelDeposito.setLayout(null);

		panel_docs = new JPanel();
		panel_docs.setBackground(Color.WHITE);
		panel_docs.setBounds(43, 11, 315, 336);

		JPanel painelDocumentos = new JPanel();
		painelDocumentos.setVisible(false);
		painelDocumentos.setEnabled(false);
		// painelPrincipal.add(painelDocumentos, "cell 3 0 3 1,grow");
		painelDocumentos.setLayout(null);

		painelDocumentos.add(panel_docs);

		JPanel painelInserirDocumento = new JPanel();
		painelInserirDocumento.setLayout(null);
		painelInserirDocumento.setBounds(381, 35, 431, 288);
		painelDocumentos.add(painelInserirDocumento);

		JLabel lblNewLabel_15 = new JLabel("Nome:");
		lblNewLabel_15.setBounds(34, 11, 46, 14);
		painelInserirDocumento.add(lblNewLabel_15);

		JLabel lblNewLabel_16 = new JLabel("Descrição:");
		lblNewLabel_16.setBounds(19, 105, 70, 14);
		painelInserirDocumento.add(lblNewLabel_16);

		entDescricaoDocumento = new JTextArea();
		entDescricaoDocumento.setBounds(75, 100, 330, 85);
		painelInserirDocumento.add(entDescricaoDocumento);

		JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		lblNewLabel_17.setBounds(19, 210, 46, 14);
		painelInserirDocumento.add(lblNewLabel_17);

		entCaminhoDocumento = new JTextField();
		entCaminhoDocumento.setColumns(10);
		entCaminhoDocumento.setBounds(75, 198, 231, 39);
		painelInserirDocumento.add(entCaminhoDocumento);

		entNomeDocumento = new JTextField();
		entNomeDocumento.setColumns(10);
		entNomeDocumento.setBounds(75, 8, 330, 27);
		painelInserirDocumento.add(entNomeDocumento);

		JButton btnSelecionarDocumento = new JButton("Selecionar");
		btnSelecionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selecionarDocumento();
			}
		});
		btnSelecionarDocumento.setBounds(316, 205, 89, 24);
		painelInserirDocumento.add(btnSelecionarDocumento);

		JButton btnAdicionarDocumento = new JButton("Adicionar");
		btnAdicionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarNovoDocumento();
			}
		});
		btnAdicionarDocumento.setBounds(316, 254, 89, 23);
		painelInserirDocumento.add(btnAdicionarDocumento);

		JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		lblNewLabel_16_1.setBounds(19, 58, 70, 14);
		painelInserirDocumento.add(lblNewLabel_16_1);

		cBTipoDocumento = new JComboBox();
		cBTipoDocumento.setBounds(75, 54, 330, 22);
		painelInserirDocumento.add(cBTipoDocumento);
		cBTipoDocumento.addItem("Documento Pessoal");
		cBTipoDocumento.addItem("Comprovantes");
		cBTipoDocumento.addItem("Outros");

		btnDeposito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

				painelExtratos.setEnabled(false);
				painelExtratos.setVisible(false);

				painelDeposito.setEnabled(true);
				painelDeposito.setVisible(true);

				btnDeposito.setOpaque(true);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();

				btnDocumentos.setOpaque(false);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

				btnContratos.setOpaque(false);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				painelAreaTransferencia.removeAll();
				painelAreaTransferencia.add(painelDeposito);
				painelAreaTransferencia.repaint();
				painelAreaTransferencia.updateUI();
			}
		});

		btnContratos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelExtratos.setEnabled(true);
				painelExtratos.setVisible(true);

				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

				painelDeposito.setEnabled(false);
				painelDeposito.setVisible(false);

				btnContratos.setOpaque(true);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				btnDeposito.setOpaque(false);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();

				btnDocumentos.setOpaque(false);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

				painelAreaTransferencia.removeAll();
				painelAreaTransferencia.add(painelExtratos, "alignx center,growy");
				painelAreaTransferencia.repaint();
				painelAreaTransferencia.updateUI();
			}
		});

		btnDocumentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelDocumentos.setEnabled(true);
				painelDocumentos.setVisible(true);

				painelDeposito.setEnabled(false);
				painelDeposito.setVisible(false);

				painelExtratos.setEnabled(false);
				painelExtratos.setVisible(false);

				btnDocumentos.setOpaque(true);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

				btnDeposito.setOpaque(false);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();

				btnContratos.setOpaque(false);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				painelAreaTransferencia.removeAll();
				painelAreaTransferencia.add(painelDocumentos);
				painelAreaTransferencia.repaint();
				painelAreaTransferencia.updateUI();
			}
		});

		boolean c = true;
		if (c) {
			setInfoConta(caixa.getId_conta());
			getDadosGlobais();
			setInformacoesDocumentos();
			pesquisar_saldo(caixa);

		}
		painelAreaTransferencia.add(painelExtratos, "alignx center,growy");

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisarExtrato() {
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		lista_extrato = gerenciar.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());

		BigDecimal saldo_inicial = caixa_local.getSaldo_inicial();

		for (FinanceiroPagamentoCompleto not : lista_extrato) {
			int tipo_lancamento = not.getLancamento().getTipo_lancamento();
			if (tipo_lancamento == 0) {
				// despesa
				saldo_inicial = saldo_inicial.subtract(not.getFpag().getValor());
			} else if (tipo_lancamento == 1) {
				// receita
				saldo_inicial = saldo_inicial.add(not.getFpag().getValor());

			} else if (tipo_lancamento == 3) {
				// emprestimo
				saldo_inicial = saldo_inicial.add(not.getFpag().getValor());

			}
			not.setSaldo_atual(saldo_inicial);
			listModelGlobal.addElement(not);
		}

	}

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(caixa_local.getId_conta());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				panel_docs.setLayout(null);

				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel.setBounds(10, 5, 295, 320);
				panel_docs.add(panel);
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JLabel lblNewLabel_18 = new JLabel("Documentos desta Instituição Bancária:");
				lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel_18, "cell 0 0");

				// create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
				no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
				no_outros = new DefaultMutableTreeNode("Outros");

				// add the child nodes to the root node
				root.add(no_docs_pessoais);
				root.add(no_comprovantes);
				root.add(no_outros);

				// create the tree by passing in the root node
				arvore_documentos = new JTree(root);

				arvore_documentos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						no_selecionado = (DefaultMutableTreeNode) arvore_documentos.getLastSelectedPathComponent();
						// lblNoSelecionado.setText(no_selecionado.getUserObject().toString());
					}
				});
				JPopupMenu jPopupMenu = new JPopupMenu();
				JMenuItem jMenuItemVizualizar = new JMenuItem();
				JMenuItem jMenuItemExcluir = new JMenuItem();

				jMenuItemVizualizar.setText("Vizualizar");
				jMenuItemExcluir.setText("Excluir");

				jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
					// Importe a classe java.awt.event.ActionEvent
					public void actionPerformed(ActionEvent e) {
						String nome_arquivo = no_selecionado.getUserObject().toString();

						String quebra[] = nome_arquivo.split("@");

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}

						String nome_pasta = caixa_local.getNome_instituicao_bancaria();

						String unidade_base_dados = configs_globais.getServidorUnidade();
						String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\ibs\\"
								+ nome_pasta + "\\documentos\\" + nome_official;

						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(caminho_salvar);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}
					}
				});

				jMenuItemExcluir.addActionListener(new java.awt.event.ActionListener() {
					// Importe a classe java.awt.event.ActionEvent
					public void actionPerformed(ActionEvent e) {
						/*
						 * if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este comprovante",
						 * "Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==
						 * JOptionPane.YES_OPTION){
						 * 
						 * String nome_arquivo = no_selecionado.getUserObject().toString(); String
						 * quebra [] = nome_arquivo.split("@"); String id = quebra[0]; int i_id =
						 * Integer.parseInt(id);
						 * 
						 * String nome_official = ""; for(int i = 1; i < quebra.length ; i++) {
						 * nome_official += quebra[i]; } String unidade_base_dados =
						 * configs_globais.getServidorUnidade(); String caminho_completo =
						 * unidade_base_dados +
						 * "\\" + contrato_local.getCaminho_diretorio_contrato() + "\\" + "comprovantes\
						 * \" + nome_official;
						 * 
						 * boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
						 * if(excluido) {
						 * 
						 * 
						 * 
						 * GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
						 * boolean excluir_documento = gerenciar_docs.removerDocumento(i_id);
						 * 
						 * if(excluir_documento) { JOptionPane.showMessageDialog(null,
						 * "Comprovante Excluido!");
						 * 
						 * }else { JOptionPane.showMessageDialog(null,
						 * "Arquivo fisico apagado, mas as informações\ndeste comprovante ainda estão no \nConsulte o administrador"
						 * );
						 * 
						 * }
						 * 
						 * atualizarArvoreDocumentos();
						 * 
						 * }else { JOptionPane.showMessageDialog(null,
						 * "Erro ao excluir o comprovante\nConsulte o administrador!"); }
						 * 
						 * } else {
						 * 
						 * 
						 * }
						 */
					}

				});

				jPopupMenu.add(jMenuItemVizualizar);
				jPopupMenu.add(jMenuItemExcluir);

				arvore_documentos.addMouseListener(new java.awt.event.MouseAdapter() {
					// Importe a classe java.awt.event.MouseEvent
					public void mouseClicked(MouseEvent e) {
						// Se o botão direito do mouse foi pressionado
						if (e.getButton() == MouseEvent.BUTTON3) {
							// Exibe o popup menu na posição do mouse.
							jPopupMenu.show(arvore_documentos, e.getX(), e.getY());
						}
					}
				});

				arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_docs_pessoais = new ImageIcon(
							TelaGerenciarCliente.class.getResource("/imagens/icone_docs_pessoais.png"));
					ImageIcon icone_comprovantes = new ImageIcon(
							TelaGerenciarCliente.class.getResource("/imagens/icone_comprovantes.png"));
					ImageIcon icone_outros = new ImageIcon(
							TelaGerenciarCliente.class.getResource("/imagens/icone_outros.png"));

					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
							boolean expanded, boolean isLeaf, int row, boolean focused) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
						String s = node.getUserObject().toString();
						if ("Documentos Pessoais".equals(s)) {
							setOpenIcon(icone_docs_pessoais);
							setClosedIcon(icone_docs_pessoais);

						} else if ("Comprovantes".equals(s)) {
							setOpenIcon(icone_comprovantes);
							setClosedIcon(icone_comprovantes);

						} else if ("Outros".equals(s)) {
							setOpenIcon(icone_outros);
							setClosedIcon(icone_outros);

						}
						super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, hasFocus);

						return this;
					}

				});

				arvore_documentos.setShowsRootHandles(true);
				arvore_documentos.setRootVisible(false);
				panel.add(arvore_documentos, "cell 0 1,grow");

				if (lista_docs != null && lista_docs.size() > 0) {
					for (CadastroDocumento doc : lista_docs) {
						if (doc.getTipo() == 1) {
							no_docs_pessoais.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_comprovantes.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// outros
							no_outros.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}

				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

			}
		});

	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public void selecionarDocumento() {

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o documento a anexar!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				entCaminhoDocumento.setText(caminho_arquivo);
				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}
		});
	}

	public void adicionarNovoDocumento() {

		String nome, descricao, nome_arquivo, caminho_arquivo;
		int id_contrato_pai;

		nome = entNomeDocumento.getText();
		descricao = entDescricaoDocumento.getText();
		caminho_arquivo = entCaminhoDocumento.getText();

		String nome_arquivo_original_conteudo[] = caminho_arquivo.split("\"");
		String nome_arquivo_original = nome_arquivo_original_conteudo[nome_arquivo_original_conteudo.length - 1];
		String extensaoDoArquivo = FilenameUtils.getExtension(nome_arquivo_original);

		// copiar o arquivo para a nova pasta

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			// pegar nome da pasta
			String nome_pasta = nome_pasta = caixa_local.getNome_instituicao_bancaria();

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\ibs\\" + nome_pasta
					+ "\\documentos";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
					nome_arquivo = caixa_local.getNome_instituicao_bancaria() + "_" + nome + "_"
							+ horaString.replaceAll(":", "_") + "." + extensaoDoArquivo;

					String caminho_completo = caminho_salvar + "\\" + nome_arquivo;

					boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

					if (movido) {

						// atualizar status do contrato
						CadastroDocumento novo_documento = new CadastroDocumento();
						novo_documento.setDescricao(descricao);
						novo_documento.setNome(nome);

						String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();
						int tipo_documento = -1;

						if (s_tipo_documento.equalsIgnoreCase("Documento Pessoal")) {
							tipo_documento = 1;
						} else if (s_tipo_documento.equalsIgnoreCase("Comprovantes")) {
							tipo_documento = 2;
						} else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
							tipo_documento = 3;
						}

						novo_documento.setTipo(tipo_documento);
						novo_documento.setId_pai(0);
						novo_documento.setNome_arquivo(nome_arquivo);
						novo_documento.setId_cliente(caixa_local.getId_conta());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao_cliente(novo_documento);
						if (cadastrar > 0) {
							JOptionPane.showMessageDialog(isto, "Arquivo copiado e salvo na base de dados\nOrigem: "
									+ caminho_arquivo + "\nDestino: " + caminho_completo);

							entNomeDocumento.setText("");
							entDescricaoDocumento.setText("");
							entCaminhoDocumento.setText("");

							atualizarArvoreDocumentos();
						} else {
							JOptionPane.showMessageDialog(isto,
									"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
							// cancelar operacao e excluir o arquivo
							if (manipular.apagarArquivo(caminho_completo)) {

							} else {
								JOptionPane.showMessageDialog(isto,
										"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
							}
						}

					} else {
						JOptionPane.showMessageDialog(isto, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

					}
				} else {
					JOptionPane.showMessageDialog(isto, "Nome do arquivo invalido!");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

		}

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

		// telaRomaneios
		telaRomaneio = dados.getTelaRomaneios();

		// telaTodasNotasFiscais
		telaTodasNotasFiscais = dados.getTelaTodasNotasFiscais();
	}

	public void atualizarArvoreDocumentos() {

		new Thread() {
			@Override
			public void run() {
				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosCliente(caixa_local.getId_conta());

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

						root.removeAllChildren();

						no_docs_pessoais.removeAllChildren();
						no_comprovantes.removeAllChildren();
						no_outros.removeAllChildren();

						no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
						no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
						no_outros = new DefaultMutableTreeNode("Outros");

						root.add(no_docs_pessoais);
						root.add(no_comprovantes);
						root.add(no_outros);

						if (lista_docs != null && lista_docs.size() > 0) {
							for (CadastroDocumento doc : lista_docs) {
								if (doc.getTipo() == 1) {
									// model.insertNodeInto(new DefaultMutableTreeNode(doc.getNome()), root,
									// root.getChildCount());

									no_docs_pessoais.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 2) {
									// pagamentos
									no_comprovantes.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 3) {
									// carregamentos
									no_outros.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 4) {
									// outros
									no_outros.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								}

							}
						}
						model.reload(); // this notifies the listeners and changes the GUI

						expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

					}

				});

			}
		}.start();

	}

	public void setInfoConta(int id_conta) {

		GerenciarBancoClientes gerenciar = new GerenciarBancoClientes();
		ContaBancaria conta = gerenciar.getConta(id_conta);

		String texto_banco = "Banco: " + conta.getBanco() + " Código: " + conta.getCodigo();
		lblDadosBanco.setText(texto_banco);
		String texto_conta = "Agência: " + conta.getAgencia() + " Conta: " + conta.getConta();
		lblDadosConta.setText(texto_conta);
		String texto_titular = "CPF: " + conta.getCpf_titular() + " Nome: " + conta.getNome();
		lblDadosTitular.setText(texto_titular);

	}

	public void pesquisar_saldo(InstituicaoBancaria caixa) {
		GerenciarBancoFinanceiroPagamento gerenciar_fin = new GerenciarBancoFinanceiroPagamento();
		SaldoInstituicaoBancaria saldo = gerenciar_fin.getTotalPagamentosDespesa(caixa.getId_instituicao_bancaria());

		Locale ptBr = new Locale("pt", "BR");

		double saldo_final = caixa.getSaldo_inicial().doubleValue() + saldo.getTotal_receita()
				- saldo.getTotal_despesa();
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(saldo_final);

		lblSaldo.setText(valorString);

		lblSaldoInicial.setText(NumberFormat.getCurrencyInstance(ptBr).format(caixa.getSaldo_inicial()));
		lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo.getTotal_despesa()));
		lblValorTotalReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo.getTotal_receita()));
		lblSaldoAtual.setText(valorString);

	}

}
