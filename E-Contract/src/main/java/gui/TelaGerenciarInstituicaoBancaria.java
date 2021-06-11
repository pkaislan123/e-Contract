
package main.java.gui;

import java.util.Map;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.util.HashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import java.awt.Point;
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
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.cadastros.SaldoInstituicaoBancaria;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.classesExtras.RenderizadorExtrato;
import main.java.classesExtras.RenderizadorNotas;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
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
import main.java.cadastros.CondicaoPagamento;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoLancamento;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TelaGerenciarInstituicaoBancaria extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private TelaGerenciarInstituicaoBancaria isto;
	private KGradientPanel menu_lateral;
	private JPanel panel_docs;
	private JTree arvore_documentos;
	private InstituicaoBancaria caixa_local;
	ArrayList<FinanceiroPagamentoCompleto> lista_extrato = new ArrayList<FinanceiroPagamentoCompleto>();
	ArrayList<FinanceiroPagamentoCompleto> lista_extrato_emprestimo = new ArrayList<FinanceiroPagamentoCompleto>();

	private DefaultListModel<FinanceiroPagamentoCompleto> listModelGlobal;
	private JComboBox cbStatusCondicaoPagamento, cbTipoLancamento;
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
	private JComboBox cbCondicaoPagamento;
	private JTextField entNomePagador;
	private JTextField entNomeRecebedor;
	private JTextField entMenorData;
	private JTextField entMaiorData;

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
		painelAreaTransferencia.setLayout(new MigLayout("", "[][]", "[grow][]"));

		JPanel painelExtratos = new JPanel();
		painelExtratos.setAlignmentY(Component.TOP_ALIGNMENT);
		painelExtratos.setAlignmentX(Component.LEFT_ALIGNMENT);
		painelExtratos.setOpaque(false);
		painelExtratos.setBorder(null);
		painelExtratos.setVisible(false);
		painelExtratos.setBackground(Color.WHITE);
		painelExtratos.setForeground(Color.WHITE);
		painelExtratos.setLayout(new MigLayout("", "[][648.00]", "[][][grow]"));

		JPanel panel_3 = new JPanel();
		painelExtratos.add(panel_3, "cell 0 0");
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBackground(Color.WHITE);
		panel_3.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][]"));

		JLabel lblNewLabel_2 = new JLabel("Filtros");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_2, "cell 0 0");

		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Tipo Lançamento:");
		lblNewLabel_1_2_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1, "cell 0 2,alignx right");

		cbTipoLancamento = new JComboBox();
		cbTipoLancamento.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_3.add(cbTipoLancamento, "cell 1 2 2 1,growx");
		cbTipoLancamento.addItem("TODOS");
		cbTipoLancamento.addItem("DESPESAS");
		cbTipoLancamento.addItem("RECEITAS");
		cbTipoLancamento.addItem("TRANSFERENCIAS");
		cbTipoLancamento.addItem("EMPRESTIMOS");


		JLabel lblNewLabel_1_2_1_1_1_1 = new JLabel("Condição do Pagamento:");
		lblNewLabel_1_2_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1, "cell 0 3,alignx trailing");

		cbCondicaoPagamento = new JComboBox();
		cbCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_3.add(cbCondicaoPagamento, "cell 1 3 2 1,growx");

		JLabel lblNewLabel_1_2_1_1_1_1_1 = new JLabel("Status Pagamento:");
		lblNewLabel_1_2_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1_1, "cell 0 4,alignx trailing");

		cbStatusCondicaoPagamento = new JComboBox();
		cbStatusCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 18));
		cbStatusCondicaoPagamento.addItem("TODOS");
		cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
		cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");

		panel_3.add(cbStatusCondicaoPagamento, "cell 1 4 2 1,growx");

		JLabel lblNewLabel_1_2_1_1_1_1_1_1 = new JLabel("Pagador:");
		lblNewLabel_1_2_1_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1_1_1, "cell 0 5,alignx trailing");

		entNomePagador = new JTextField();
		entNomePagador.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_3.add(entNomePagador, "cell 1 5 2 1,growx");
		entNomePagador.setColumns(10);

		JLabel lblNewLabel_1_2_1_1_1_1_1_1_1 = new JLabel("Recebedor:");
		lblNewLabel_1_2_1_1_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1_1_1_1, "cell 0 6,alignx trailing");

		entNomeRecebedor = new JTextField();
		entNomeRecebedor.setFont(new Font("SansSerif", Font.PLAIN, 18));
		entNomeRecebedor.setColumns(10);
		panel_3.add(entNomeRecebedor, "cell 1 6 2 1,growx");
		
		JLabel lblNewLabel_1_2_1_1_1_1_1_1_1_1 = new JLabel("Período:");
		lblNewLabel_1_2_1_1_1_1_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_3.add(lblNewLabel_1_2_1_1_1_1_1_1_1_1, "cell 0 7,alignx trailing");
		
		JLabel lblNewLabel_3 = new JLabel("de ");
		panel_3.add(lblNewLabel_3, "cell 1 7,alignx trailing");
		
		entMenorData = new JTextField();
		entMenorData.setFont(new Font("SansSerif", Font.PLAIN, 18));
		entMenorData.setColumns(10);
		panel_3.add(entMenorData, "flowx,cell 2 7,growx");

		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnLimparCampos.setBackground(new Color(204, 0, 0));
		panel_3.add(btnLimparCampos, "flowx,cell 2 8,alignx right");

		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limparCampos();

			}
		});

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnLimpar.setBackground(new Color(153, 51, 0));
		panel_3.add(btnLimpar, "cell 2 8,alignx right");

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limpar();

			}
		});

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				filtrar();

			}
		});
		btnFiltrar.setBackground(new Color(0, 51, 51));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_3.add(btnFiltrar, "cell 2 8,alignx right");
		
		JLabel lblNewLabel_3_1 = new JLabel("até");
		panel_3.add(lblNewLabel_3_1, "cell 2 7");
		
		entMaiorData = new JTextField();
		entMaiorData.setFont(new Font("SansSerif", Font.PLAIN, 18));
		entMaiorData.setColumns(10);
		panel_3.add(entMaiorData, "cell 2 7");

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		painelExtratos.add(panel_2, "cell 0 1 1 2,grow");
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

		JList lista = new JList<>();
		lista.setOpaque(false);
		lista.setBackground(Color.WHITE);
		
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {


		           FinanceiroPagamentoCompleto selectedItem = (FinanceiroPagamentoCompleto) lista.getSelectedValue();
		          
		           Lancamento lancamento_selecionado = selectedItem.getLancamento();
		       	TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_selecionado, isto);
				tela.setVisible(true);		           

		         }
		    }
		};
	
		lista.addMouseListener(mouseListener);

		JScrollPane scrollPaneListaExtrato = new JScrollPane(lista);
		/*
		 scrollPaneListaExtrato.getVerticalScrollBar().setBackground(Color.WHITE);
		 scrollPaneListaExtrato.getHorizontalScrollBar().setBackground(Color.WHITE);
		 scrollPaneListaExtrato.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		 
		  @Override protected void configureScrollBarColors() { this.thumbColor =
		  Color.BLUE; } });
		 */
		painelExtratos.add(scrollPaneListaExtrato, "cell 1 0 1 3,grow");
		scrollPaneListaExtrato.getViewport().setBackground(Color.white);
		scrollPaneListaExtrato.getViewport().setOpaque(false);
		listModelGlobal = new DefaultListModel<FinanceiroPagamentoCompleto>();

		render = new RenderizadorExtrato();

		lista.setModel(listModelGlobal);
		lista.setCellRenderer(render);

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

	

		panel_docs = new JPanel();
		panel_docs.setBackground(new Color(255, 255, 204));

		JPanel painelDocumentos = new JPanel();
		painelDocumentos.setBackground(new Color(0, 51, 0));
		painelDocumentos.setVisible(false);
		painelDocumentos.setEnabled(false);
		painelDocumentos.setLayout(new MigLayout("", "[600px:n,grow][431px]", "[grow]"));

		painelDocumentos.add(panel_docs, "cell 0 0,grow");

		JPanel painelInserirDocumento = new JPanel();
		painelInserirDocumento.setBackground(new Color(0, 51, 0));
		painelDocumentos.add(painelInserirDocumento, "cell 1 0,growx,aligny center");
		painelInserirDocumento.setLayout(new MigLayout("", "[46px][10px][231px][10px][89px]", "[27px][22px][85px][39px][23px]"));

		JLabel lblNewLabel_15 = new JLabel("Nome:");
		lblNewLabel_15.setForeground(Color.WHITE);
		lblNewLabel_15.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(lblNewLabel_15, "cell 0 0 2 1,alignx right,aligny top");

		JLabel lblNewLabel_16 = new JLabel("Descrição:");
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(lblNewLabel_16, "cell 0 2 2 1,alignx right,aligny top");

		entDescricaoDocumento = new JTextArea();
		entDescricaoDocumento.setFont(new Font("Arial", Font.BOLD, 18));
		painelInserirDocumento.add(entDescricaoDocumento, "cell 2 2 3 1,grow");

		JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		lblNewLabel_17.setForeground(Color.WHITE);
		lblNewLabel_17.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(lblNewLabel_17, "cell 0 3 2 1,alignx right,aligny center");

		entCaminhoDocumento = new JTextField();
		entCaminhoDocumento.setFont(new Font("Arial", Font.BOLD, 18));
		entCaminhoDocumento.setColumns(10);
		painelInserirDocumento.add(entCaminhoDocumento, "cell 2 3,grow");

		entNomeDocumento = new JTextField();
		entNomeDocumento.setFont(new Font("Arial", Font.BOLD, 18));
		entNomeDocumento.setColumns(10);
		painelInserirDocumento.add(entNomeDocumento, "cell 2 0 3 1,grow");

		JButton btnSelecionarDocumento = new JButton("Selecionar");
		btnSelecionarDocumento.setBackground(new Color(0, 0, 153));
		btnSelecionarDocumento.setForeground(Color.WHITE);
		btnSelecionarDocumento.setFont(new Font("Arial", Font.BOLD, 18));
		btnSelecionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selecionarDocumento();
			}
		});
		painelInserirDocumento.add(btnSelecionarDocumento, "cell 4 3,growx,aligny center");

		JButton btnAdicionarDocumento = new JButton("Adicionar");
		btnAdicionarDocumento.setBackground(new Color(102, 0, 153));
		btnAdicionarDocumento.setForeground(Color.WHITE);
		btnAdicionarDocumento.setFont(new Font("Arial", Font.BOLD, 18));
		btnAdicionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarNovoDocumento();
			}
		});
		painelInserirDocumento.add(btnAdicionarDocumento, "cell 4 4,grow");

		JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		lblNewLabel_16_1.setForeground(Color.WHITE);
		lblNewLabel_16_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(lblNewLabel_16_1, "cell 0 1 2 1,alignx right,aligny center");

		cBTipoDocumento = new JComboBox();
		cBTipoDocumento.setFont(new Font("Arial", Font.BOLD, 18));
		painelInserirDocumento.add(cBTipoDocumento, "cell 2 1 3 1,grow");
		cBTipoDocumento.addItem("Documento Pessoal");
		cBTipoDocumento.addItem("Comprovantes");
		cBTipoDocumento.addItem("Outros");

		btnContratos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelExtratos.setEnabled(true);
				painelExtratos.setVisible(true);

				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

			

				btnContratos.setOpaque(true);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

			
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

			

				painelExtratos.setEnabled(false);
				painelExtratos.setVisible(false);

				btnDocumentos.setOpaque(true);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

			

				btnContratos.setOpaque(false);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				painelAreaTransferencia.removeAll();
				painelAreaTransferencia.add(painelDocumentos, "cell 0 0 2 2,grow");
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
			popular_condicao_pagamento();

			/*
			GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
			GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimo = new GerenciarBancoFinanceiroPagamentoEmprestimo();

			lista_extrato = gerenciar
					.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());
			lista_extrato_emprestimo = gerenciar_emprestimo
					.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());
			
			
			lista_extrato.addAll(lista_extrato_emprestimo);
			
			//ordernar lista por data
			Collections.sort(lista_extrato, new Comparator<FinanceiroPagamentoCompleto>() {
				  public int compare(FinanceiroPagamentoCompleto o1, FinanceiroPagamentoCompleto o2) {
				      if (o1.getFpag().getData_pagamento() == null || o2.getFpag().getData_pagamento() == null)
				        return 0;
				      LocalDate data_menor = LocalDate.parse(o1.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						 LocalDate data_maior = LocalDate.parse( o2.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				      return data_menor.compareTo(data_maior);
				  }
				});
			
			
			pesquisarExtrato(lista_extrato, caixa_local.getSaldo_inicial());
			*/
			filtrar();

		}
		
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		
		 Map<String,String> datas = gerenciar.pegarDatasPagamento(caixa_local.getId_instituicao_bancaria());

			entMenorData.setText(datas.get("menor_data_pagamento"));
			entMaiorData.setText(datas.get("maior_data_pagamento"));
		          
	
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisarExtrato(ArrayList<FinanceiroPagamentoCompleto> lista_extrato, BigDecimal saldo_inicial) {


		listModelGlobal.clear();

		for (FinanceiroPagamentoCompleto not : lista_extrato) {
			int tipo_lancamento = not.getLancamento().getTipo_lancamento();
			if (tipo_lancamento == 0) {
				// despesa
				saldo_inicial = saldo_inicial.subtract(not.getFpag().getValor());
			} else if (tipo_lancamento == 1) {
				// receita
				saldo_inicial = saldo_inicial.add(not.getFpag().getValor());

			}
			 else if (tipo_lancamento == 2) {
					// transferencias
				 
				 if(not.getFpag().getId_pagador() == caixa_local.getId_instituicao_bancaria()) {
					 //pagador, subtrai
						saldo_inicial = saldo_inicial.subtract(not.getFpag().getValor());

				 }else if(not.getFpag().getId_pagador() != caixa_local.getId_instituicao_bancaria()) {
					//recebedor	
					 saldo_inicial = saldo_inicial.add(not.getFpag().getValor());

				 }

				}
			else if (tipo_lancamento == 3) {
				// emprestimo
				saldo_inicial = saldo_inicial.add(not.getFpag().getValor());

			}
			not.setSaldo_atual(saldo_inicial);
			not.setId_caixa(caixa_local.getId_instituicao_bancaria());
			listModelGlobal.addElement(not);
		}

	}

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosPorIb(caixa_local.getId_instituicao_bancaria());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				
			
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
				panel_docs.add(arvore_documentos);

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

			String caminho_salvar = unidade_base_dados + "\\E-Contract\\arquivos\\financas\\ibs\\" + nome_pasta
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
						novo_documento.setId_ib(caixa_local.getId_instituicao_bancaria());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao_ib(novo_documento);
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
				ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentosPorIb(caixa_local.getId_instituicao_bancaria());
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
				- saldo.getTotal_despesa() + saldo.getTotal_emprestimos() + saldo.getTotal_receita_transferencia() - saldo.getTotal_despesa_transferencia();
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(saldo_final);

		lblSaldo.setText(valorString);

		lblSaldoInicial.setText(NumberFormat.getCurrencyInstance(ptBr).format(caixa.getSaldo_inicial()));
		lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo.getTotal_despesa()));
		lblValorTotalReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo.getTotal_receita()));
		lblSaldoAtual.setText(valorString);

	}
	
	
	
	public void popular_condicao_pagamento() {
		ArrayList<CondicaoPagamento> lista_condicao_pagamentos = new GerenciarBancoCondicaoPagamentos()
				.getCondicaoPagamentos();
		cbCondicaoPagamento.removeAllItems();
		cbCondicaoPagamento.addItem("TODOS");
		for (CondicaoPagamento cp : lista_condicao_pagamentos) {
			cbCondicaoPagamento.addItem(cp.getId_condicao_pagamento() + "-" + cp.getNome_condicao_pagamento());
		}

	}

	public void filtrar() {
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimo = new GerenciarBancoFinanceiroPagamentoEmprestimo();

		
		lista_extrato = gerenciar.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());
		lista_extrato_emprestimo = gerenciar_emprestimo.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());
		
		lista_extrato.addAll(lista_extrato_emprestimo);
		
		//ordernar lista por data
		Collections.sort(lista_extrato, new Comparator<FinanceiroPagamentoCompleto>() {
			  public int compare(FinanceiroPagamentoCompleto o1, FinanceiroPagamentoCompleto o2) {
			      if (o1.getFpag().getData_pagamento() == null || o2.getFpag().getData_pagamento() == null)
			        return 0;
			      LocalDate data_menor = LocalDate.parse(o1.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					 LocalDate data_maior = LocalDate.parse( o2.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			      return data_menor.compareTo(data_maior);
			  }
			});
		List<FinanceiroPagamentoCompleto> lista_filtrada = lista_extrato;

		int tipo_lancamento_procurado = cbTipoLancamento.getSelectedIndex();

		if (tipo_lancamento_procurado != 0) {

			lista_filtrada = lista_extrato.stream()
					.filter(p -> p.getLancamento().getTipo_lancamento() == tipo_lancamento_procurado - 1)
					.collect(Collectors.toList());

			lista_extrato = (ArrayList<FinanceiroPagamentoCompleto>) lista_filtrada;

		}

		int status_condicao_pagamento = cbStatusCondicaoPagamento.getSelectedIndex();

		if (status_condicao_pagamento != 0) {
			lista_filtrada = lista_extrato.stream()
					.filter(p -> p.getFpag().getStatus_pagamento() == status_condicao_pagamento - 1)
					.collect(Collectors.toList());
			lista_extrato = (ArrayList<FinanceiroPagamentoCompleto>) lista_filtrada;

		}

		int condicao_pagamento = cbCondicaoPagamento.getSelectedIndex();

		if (condicao_pagamento != 0) {

			String condicao_completa = cbCondicaoPagamento.getSelectedItem().toString();
			String split[] = condicao_completa.split("-");
			int id_condicao = Integer.parseInt(split[0]);

			lista_filtrada = lista_extrato.stream().filter(p -> p.getFpag().getId_condicao_pagamento() == id_condicao)
					.collect(Collectors.toList());
			lista_extrato = (ArrayList<FinanceiroPagamentoCompleto>) lista_filtrada;

		}

		String nome_pagador = entNomePagador.getText();
		if (checkString(nome_pagador)) {
			lista_filtrada = lista_extrato.stream()
					.filter(p -> p.getNome_pagador().toUpperCase().contains(nome_pagador.toUpperCase()))
					.collect(Collectors.toList());
			lista_extrato = (ArrayList<FinanceiroPagamentoCompleto>) lista_filtrada;

		}

		String nome_recebedor = entNomeRecebedor.getText();
		if (checkString(nome_recebedor)) {
			lista_filtrada = lista_extrato.stream()
					.filter(p -> p.getNome_recebedor().toUpperCase().contains(nome_recebedor.toUpperCase()))
					.collect(Collectors.toList());
			lista_extrato = (ArrayList<FinanceiroPagamentoCompleto>) lista_filtrada;

		}


		String s_data_menor = entMenorData.getText();
		String s_data_maior = entMaiorData.getText();
		
		try {


		 LocalDate data_menor = LocalDate.parse(s_data_menor, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		 LocalDate data_maior = LocalDate.parse(s_data_maior, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		
		lista_filtrada = lista_extrato.stream()
				.filter(p ->  LocalDate.parse(p.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).compareTo(data_menor) >= 0
						 &&  
						 LocalDate.parse(p.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).compareTo(data_maior) <= 0
						 
						)
				.collect(Collectors.toList());
		
	
		
		
		}catch(Exception f) {
			//JOptionPane.showMessageDialog(isto, "Datas Inválidas");
		}
		
	
		
		pesquisarExtrato((ArrayList) lista_filtrada, getSaldo(s_data_menor));
		calcular((ArrayList) lista_filtrada, s_data_menor);

	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public void limpar() {

		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		lista_extrato = gerenciar.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());
		
		GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimos = new GerenciarBancoFinanceiroPagamentoEmprestimo();
		lista_extrato_emprestimo = gerenciar_emprestimos.getFinanceiroPagamentosLancamentosPorCaixa(caixa_local.getId_instituicao_bancaria());
		
		lista_extrato.addAll(lista_extrato_emprestimo);
		
		//ordernar lista por data
		Collections.sort(lista_extrato, new Comparator<FinanceiroPagamentoCompleto>() {
			  public int compare(FinanceiroPagamentoCompleto o1, FinanceiroPagamentoCompleto o2) {
			      if (o1.getFpag().getData_pagamento() == null || o2.getFpag().getData_pagamento() == null)
			        return 0;
			      LocalDate data_menor = LocalDate.parse(o1.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					 LocalDate data_maior = LocalDate.parse( o2.getFpag().getData_pagamento(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			      return data_menor.compareTo(data_maior);
			  }
			});
		
		
		pesquisarExtrato(lista_extrato, getSaldo("01/01/2010"));
		calcular(lista_extrato, "01/01/2010");
	}
	
	
	
	
	

	public void limparCampos() {
		
		
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		
		 Map<String,String> datas = gerenciar.pegarDatasPagamento(caixa_local.getId_instituicao_bancaria());

			entMenorData.setText(datas.get("menor_data_pagamento"));
			entMaiorData.setText(datas.get("maior_data_pagamento"));
		         
		  
		cbCondicaoPagamento.setSelectedIndex(0);
		cbTipoLancamento.setSelectedIndex(0);
		cbStatusCondicaoPagamento.setSelectedIndex(0);
		entNomePagador.setText("");
		entNomeRecebedor.setText("");
		
		   

		limpar();
	}

	public BigDecimal getSaldo(String data) {
		GerenciarBancoFinanceiroPagamento gerenciar_fin = new GerenciarBancoFinanceiroPagamento();
		SaldoInstituicaoBancaria saldo = gerenciar_fin.getSaldoPorPeriodo(caixa_local.getId_instituicao_bancaria(),data);

		Locale ptBr = new Locale("pt", "BR");

		double saldo_inicial = caixa_local.getSaldo_inicial().doubleValue() + saldo.getTotal_receita()
				- saldo.getTotal_despesa() + saldo.getTotal_emprestimos() + saldo.getTotal_receita_transferencia() - saldo.getTotal_despesa_transferencia();
		
		return new BigDecimal(saldo_inicial);
	}
	
	public void calcular(ArrayList<FinanceiroPagamentoCompleto> lista_extrato,String data) {

		
		Locale ptBr = new Locale("pt", "BR");


		BigDecimal saldo_inicial = getSaldo(data);
		BigDecimal valor_total_despesas = BigDecimal.ZERO;
		BigDecimal valor_total_receitas = BigDecimal.ZERO;
		BigDecimal saldo_atual = BigDecimal.ZERO;

		for (FinanceiroPagamentoCompleto pag_completo : lista_extrato) {
			if (pag_completo.getLancamento().getTipo_lancamento() == 0) {
				// despesa
				valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());
			} else if (pag_completo.getLancamento().getTipo_lancamento() == 1) {
				// receita

				valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());

			}
			 else if (pag_completo.getLancamento().getTipo_lancamento() == 2) {
					// receita
				 	if(pag_completo.getFpag().getId_pagador() == caixa_local.getId_instituicao_bancaria()) {
						valor_total_despesas = valor_total_despesas.add(pag_completo.getFpag().getValor());

				 	}else {
						valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());

				 	}

				} 
			else if (pag_completo.getLancamento().getTipo_lancamento() == 3) {
				// emprestimo
				valor_total_receitas = valor_total_receitas.add(pag_completo.getFpag().getValor());

			}
		}

		saldo_atual = saldo_inicial.add(valor_total_receitas).subtract(valor_total_despesas);
		
		lblSaldoInicial.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_inicial));
		lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
		lblValorTotalReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
		lblSaldoAtual.setText(NumberFormat.getCurrencyInstance(ptBr).format(saldo_atual));
	}

	
	
}
