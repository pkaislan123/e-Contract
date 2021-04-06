package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

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
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Lancamento;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
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
import main.java.graficos.JPanelGraficoRecebimento;
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
import main.java.manipular.MonitorarRomaneios;
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
import main.java.relatoria.RelatorioContratoSimplificado;
import main.java.relatoria.RelatorioContratos;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEmEspera;
import main.java.views_personalizadas.TelaNotificacao;
import main.java.views_personalizadas.TelaNotificacaoSuperior;
import main.java.views_personalizadas.TelaNotificacaoSuperiorModoBusca;
import net.miginfocom.swing.MigLayout;
import outros.ValidaCNPJ;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroPontuacao;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroGrupo;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoGrupos;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
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
import main.java.classesExtras.ComboBoxPersonalizadoContaBancaria;
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.classesExtras.ComboBoxRenderPersonalizadoContaBancaria;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

public class TelaFinanceiroCadastroLancamento extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

	private TelaFinanceiroCadastroLancamento isto;
	private JDialog telaPai;
	private final JButton btnCadastrar = new JButton("Cadastrar");
	private final JButton btnAtualizar = new JButton("Atualizar");
	private final JPanel painelTitulo = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Cadastro de Lançamento");
	private final JPanel painelBotoes = new JPanel();
	private final JPanel painelPilar = new JPanel();
	private CadastroCliente cliente_selecionado = null;
	private JPanel painelAsPartes = new JPanel();
	private JPanel painelConta = new JPanel();
	private JPanel painelConcluir = new JPanel();
	private CentroCusto centro_custo;
	private InstituicaoBancaria instituicao_bancaria;
	private CondicaoPagamento condicao_pagamento;
	private FinanceiroConta financeiro_conta;
	private CadastroCliente cliente;
	
	

	private final JTabbedPane abas = new JTabbedPane(JTabbedPane.TOP);
	private final JLabel lblNewLabel_1 = new JLabel("Centro de Custo:");
	private final JLabel lblNewLabel_2 = new JLabel("Instituição Bancária:");
	private final JComboBox cbCentroCusto = new JComboBox();
	private final JComboBox cbInstituicaoBancaria = new JComboBox();
	private final JLabel lblNewLabel_3 = new JLabel("Pagador:");
	private final JLabel lblNewLabel_4 = new JLabel("Recebedor");
	private final JLabel lblNewLabel_1_1 = new JLabel("Cliente/Fornecedor:");
	private final JPanel panel = new JPanel();
	private final JButton btnSelecionar = new JButton("Selecionar");
	private final JComboBox cbCliente = new JComboBox();
	private final JLabel lblNewLabel_4_1 = new JLabel("Conta a pagar:");
	private final JButton btnSelecionarCentroCusto = new JButton("Selecionar");
	private final JLabel lblNewLabel_1_2 = new JLabel("Conta");
	private final JComboBox cbConta = new JComboBox();
	private final JButton btnSelecionarConta = new JButton("Selecionar");
	private final JLabel lblNewLabel_4_1_1 = new JLabel("Pagamento:");
	private final JLabel lblNewLabel_1_2_1 = new JLabel("Condição de Pagamento:");
	private final JComboBox cbCondicaoPagamento = new JComboBox();
	private final JButton btnSelecionarCondicaoPagamento = new JButton("Selecionar");
	private final JLabel lblNewLabel_1_2_1_1 = new JLabel("Valor:");
	private final JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Data Vencimento:");
	private final JLabel lblNewLabel_1_2_1_1_1_1 = new JLabel("Data Pagamento:");
	private final JTextFieldPersonalizado entValor = new JTextFieldPersonalizado();
	private final JTextFieldPersonalizado entDataVencimento = new JTextFieldPersonalizado();
	private final JTextFieldPersonalizado entDataPagamento = new JTextFieldPersonalizado();
	private final JLabel lblNewLabel_4_1_2 = new JLabel("Concluir:");
	private final JLabel lblNewLabel_1_2_2 = new JLabel("Status:");
	private final JTextFieldPersonalizado entStatus = new JTextFieldPersonalizado();
	private final JLabel lblNewLabel_1_2_2_1 = new JLabel("Observação:");
	private final JLabel lblNewLabel_1_2_2_1_1 = new JLabel("Descrição:");
	private final JButton btnSelecionarInstituicaoBancaria = new JButton("Selecionar");
	private final JLabel lblNewLabel_5 = new JLabel("New label");
	private final JLabel lblNewLabel_5_1 = new JLabel("New label");
	private final JLabel lblNewLabel_5_2 = new JLabel("New label");
	private final JLabel lblNewLabel_5_2_1 = new JLabel("Data deste lançamento:");
	private final JEditorPane entObservacao = new JEditorPane();
	private final JEditorPane entDescricao = new JEditorPane();
	private final JLabel lblNewLabel_5_2_1_1 = new JLabel("New label");
	private final JLabel lblNewLabel_5_2_1_2 = new JLabel("New label");
	private final JLabel lblNewLabel_5_2_1_3 = new JLabel("New label");
	private final JLabel lblNewLabel_5_3 = new JLabel("New label");
	private final JLabel lblNewLabel_5_4 = new JLabel("New label");
	private final JLabel lblNewLabel_5_5 = new JLabel("New label");
	private final JTextFieldPersonalizado entDataLancamento = new JTextFieldPersonalizado();

	public TelaFinanceiroCadastroLancamento(int modo_operacao, Lancamento lancamento, Window janela_pai) {

		isto = this;
		if (modo_operacao == 0)
			setTitle("E-Contract - Cadastro Lançamento");
		else
			setTitle("E-Contract - Edição de Lançamento");

		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 598);
		painelOdin.setLayout(new BorderLayout(0, 0));
		painelOdin.add(painelPrincipal);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[50px,grow][379px][74px][8px][83px,grow][grow][100px]", "[46px][16px][433px,grow][28px,grow]"));
		painelTitulo.setForeground(Color.WHITE);
		painelTitulo.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(painelTitulo, "cell 0 0 7 2,grow");
		painelTitulo.setLayout(new MigLayout("", "[617px]", "[20px][]"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		
				painelTitulo.add(lblNewLabel, "cell 0 0 1 2,alignx left,aligny center");
		painelPilar.setBackground(new Color(0, 0, 102));

		painelPrincipal.add(painelPilar, "cell 6 2 1 2,grow");

		painelPrincipal.add(abas, "cell 0 2 6 1,grow");
		painelAsPartes.setBackground(Color.WHITE);

		abas.addTab("As Partes", painelAsPartes);
		painelAsPartes.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelAsPartes.add(lblNewLabel_5, "cell 0 0");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(255, 102, 51));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 18));

		painelAsPartes.add(lblNewLabel_3, "cell 0 1 2 1,growx");
		lblNewLabel_5_3.setVisible(false);
		lblNewLabel_5_3.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelAsPartes.add(lblNewLabel_5_3, "cell 0 2");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelAsPartes.add(lblNewLabel_1, "cell 0 3,alignx trailing");
		cbCentroCusto.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelAsPartes.add(cbCentroCusto, "flowx,cell 1 3,growx");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelAsPartes.add(lblNewLabel_2, "cell 0 4,alignx trailing");
		cbInstituicaoBancaria.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelAsPartes.add(cbInstituicaoBancaria, "flowx,cell 1 4,growx");
		lblNewLabel_5_4.setVisible(false);
		lblNewLabel_5_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelAsPartes.add(lblNewLabel_5_4, "cell 0 5");
		lblNewLabel_4.setBackground(new Color(0, 153, 153));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.BOLD, 18));

		painelAsPartes.add(lblNewLabel_4, "cell 0 6 2 1,growx");
		lblNewLabel_5_5.setVisible(false);
		lblNewLabel_5_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelAsPartes.add(lblNewLabel_5_5, "cell 0 7");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelAsPartes.add(lblNewLabel_1_1, "cell 0 8,alignx right");
		panel.setBackground(new Color(0, 153, 153));

		painelAsPartes.add(panel, "cell 1 8,grow");
		panel.setLayout(new MigLayout("", "[192px][87px][][][][][][]", "[28px]"));
		cbCliente.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel.add(cbCliente, "cell 0 0 6 1,growx,aligny center");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente tela = new TelaCliente(0,27, isto);
				tela.setVisible(true);
			}
		});
		btnSelecionar.setBackground(new Color(0, 0, 153));
		btnSelecionar.setForeground(Color.WHITE);

		panel.add(btnSelecionar, "cell 7 0,alignx left,aligny top");
		btnSelecionarCentroCusto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaFinanceiroCentroCusto(0, 1, isto).setVisible(true);
			}
		});
		btnSelecionarCentroCusto.setForeground(Color.WHITE);
		btnSelecionarCentroCusto.setBackground(new Color(0, 0, 153));

		painelAsPartes.add(btnSelecionarCentroCusto, "cell 1 3");
		btnSelecionarInstituicaoBancaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(0,1, isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarInstituicaoBancaria.setForeground(Color.WHITE);
		btnSelecionarInstituicaoBancaria.setBackground(new Color(0, 0, 153));
		
		painelAsPartes.add(btnSelecionarInstituicaoBancaria, "cell 1 4");
		painelConta.setBackground(Color.WHITE);
		abas.addTab("A Conta", painelConta);
		painelConta.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][][]"));
		lblNewLabel_5_1.setVisible(false);
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelConta.add(lblNewLabel_5_1, "cell 0 0");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_4_1.setBackground(new Color(51, 0, 255));

		painelConta.add(lblNewLabel_4_1, "cell 0 1 2 1,growx");
		lblNewLabel_5_2_1_2.setVisible(false);
		lblNewLabel_5_2_1_2.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelConta.add(lblNewLabel_5_2_1_2, "cell 0 2");
		lblNewLabel_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(lblNewLabel_1_2, "cell 0 3,alignx trailing");
		cbConta.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(cbConta, "flowx,cell 1 3,growx");
		btnSelecionarConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroConta tela = new TelaFinanceiroConta(0,1, isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarConta.setBackground(new Color(0, 0, 204));
		btnSelecionarConta.setForeground(Color.WHITE);
		
		painelConta.add(btnSelecionarConta, "cell 1 3");
		lblNewLabel_5_2_1_1.setVisible(false);
		lblNewLabel_5_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelConta.add(lblNewLabel_5_2_1_1, "cell 0 4");
		lblNewLabel_4_1_1.setOpaque(true);
		lblNewLabel_4_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_1_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_4_1_1.setBackground(new Color(0, 153, 153));
		
		painelConta.add(lblNewLabel_4_1_1, "cell 0 5 2 1,growx");
		lblNewLabel_5_2_1_3.setVisible(false);
		lblNewLabel_5_2_1_3.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelConta.add(lblNewLabel_5_2_1_3, "cell 0 6");
		lblNewLabel_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(lblNewLabel_1_2_1, "cell 0 7,alignx trailing");
		cbCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(cbCondicaoPagamento, "flowx,cell 1 7,growx");
		btnSelecionarCondicaoPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(0,1, isto);
				tela.setVisible(true);
			}
		});
		btnSelecionarCondicaoPagamento.setBackground(new Color(0, 0, 204));
		btnSelecionarCondicaoPagamento.setForeground(Color.WHITE);
		
		painelConta.add(btnSelecionarCondicaoPagamento, "cell 1 7");
		lblNewLabel_1_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(lblNewLabel_1_2_1_1, "cell 0 8,alignx trailing");
		
		painelConta.add(entValor, "cell 1 8,growx");
		entValor.setForeground(Color.black);
		lblNewLabel_1_2_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(lblNewLabel_1_2_1_1_1, "cell 0 9,alignx trailing");
		
		painelConta.add(entDataVencimento, "cell 1 9,growx");
		entDataVencimento.setForeground(Color.black);
		lblNewLabel_1_2_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConta.add(lblNewLabel_1_2_1_1_1_1, "cell 0 10,alignx trailing");
		
		painelConta.add(entDataPagamento, "cell 1 10,growx");
		entDataPagamento.setForeground(Color.black);
		painelConcluir.setBackground(Color.WHITE);
		abas.addTab("Concluir", painelConcluir);
		painelConcluir.setLayout(new MigLayout("", "[][grow]", "[][][][][grow][grow]"));
		lblNewLabel_5_2.setVisible(false);
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelConcluir.add(lblNewLabel_5_2, "cell 0 0");
		lblNewLabel_4_1_2.setOpaque(true);
		lblNewLabel_4_1_2.setForeground(Color.WHITE);
		lblNewLabel_4_1_2.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_4_1_2.setBackground(new Color(51, 0, 255));
		
		painelConcluir.add(lblNewLabel_4_1_2, "cell 0 1 2 1,growx");
		lblNewLabel_5_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConcluir.add(lblNewLabel_5_2_1, "cell 0 2,alignx trailing");
		
		painelConcluir.add(entDataLancamento, "cell 1 2,growx");
		entDataLancamento.setForeground(Color.black);
		lblNewLabel_1_2_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConcluir.add(lblNewLabel_1_2_2, "cell 0 3,alignx trailing");
		
		painelConcluir.add(entStatus, "cell 1 3,growx");
		entStatus.setForeground(Color.black);
		lblNewLabel_1_2_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConcluir.add(lblNewLabel_1_2_2_1, "cell 0 4,alignx trailing,aligny top");
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		entObservacao.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		painelConcluir.add(entObservacao, "cell 1 4,grow");
		lblNewLabel_1_2_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelConcluir.add(lblNewLabel_1_2_2_1_1, "cell 0 5,alignx trailing,aligny top");
		entDescricao.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		painelConcluir.add(entDescricao, "cell 1 5,grow");
		painelBotoes.setBackground(Color.WHITE);

		painelPrincipal.add(painelBotoes, "cell 5 3,alignx right,growy");
		painelBotoes.setLayout(new MigLayout("", "[][]", "[]"));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
				boolean result = gerenciar.atualizarLancamento(getDadosAtualizar(lancamento));
				if (result) {
					JOptionPane.showMessageDialog(isto, "Cadastro Atualizado");
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Atualizar\nConsulte o Administrador");

				}
			}
		});
		painelBotoes.add(btnAtualizar, "cell 0 0,growx");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
				int result = gerenciar.inserirLancamento(getDadosSalvar());
				if (result > 0) {
					JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
					isto.dispose();
				} else {
					JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

				}

			}
		});
		painelBotoes.add(btnCadastrar, "cell 1 0,growx");

		if (modo_operacao == 1) {
			rotinasEdicao(lancamento);

			btnCadastrar.setEnabled(false);
			btnCadastrar.setVisible(false);
		} else {
			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		}

		adicionarFocus(isto.getComponents());
		this.setResizable(false);
		this.setLocationRelativeTo(janela_pai);

	}

	public void rotinasEdicao(Lancamento lancamento) {

		//as partes
		
		if(lancamento.getId_centro_custo() > 0) {
			setCentroCusto(new GerenciarBancoCentroCustos().getCentroCusto(lancamento.getId_centro_custo()));
		}
		
		if(lancamento.getId_instituicao_bancaria() > 0) {
			setInstituicaoBancaria(new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(lancamento.getId_instituicao_bancaria()));
		}
		
		if(lancamento.getId_cliente_fornecedor() > 0) {
			setCliente(new GerenciarBancoClientes().getCliente(lancamento.getId_cliente_fornecedor()));
		}
		
		//tela conta
		
		if(lancamento.getId_conta() > 0) {
			setConta(new GerenciarBancoFinanceiroConta().getFinanceiroConta(lancamento.getId_conta()));
		}
		
		if(lancamento.getId_condicao_pagamento() > 0) {
			setCondicaoPagamento(new GerenciarBancoCondicaoPagamentos().getCondicaoPagamento(lancamento.getId_condicao_pagamento()));
		}
		
		entValor.setText(lancamento.getValor().toString().replaceAll("[^0-9.,]", ""));
		entDataPagamento.setText(lancamento.getData_pagamento());
		entDataVencimento.setText(lancamento.getData_vencimento());
		
		//tela concluir
		entDataLancamento.setText(lancamento.getData_lancamento());
		entStatus.setText(lancamento.getStatus());
		entObservacao.setText(lancamento.getObservacao());
		entDescricao.setText(lancamento.getDescricao());
		
		
	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

	public void adicionarFocus(Component[] components) {
		for (Component c : components) {
			if (c instanceof JTextFieldPersonalizado) {
				if (c instanceof JTextFieldPersonalizado) {

					JTextFieldPersonalizado caixa_texto = (JTextFieldPersonalizado) c;
					caixa_texto.addFocusListener(new FocusAdapter() {
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

	public void setCliente(CadastroCliente cliente) {
		cliente_selecionado = cliente;
		
		cbCliente.removeAll();
		if(cliente.getTipo_pessoa() == 0) {
			cbCliente.addItem(cliente.getNome_empresarial());

		}else {
			cbCliente.addItem(cliente.getNome_fantaia());

		}

	}

	public void setCentroCusto(CentroCusto _centro_custo) {
		centro_custo = _centro_custo;

		cbCentroCusto.removeAllItems();
		cbCentroCusto.addItem(_centro_custo.getNome_centro_custo());
	}

	public void setInstituicaoBancaria(InstituicaoBancaria _instituicao_bancaria) {
		instituicao_bancaria = _instituicao_bancaria;

		cbInstituicaoBancaria.removeAllItems();
		cbInstituicaoBancaria.addItem(_instituicao_bancaria.getNome_instituicao_bancaria());
	}
	
	public void setCondicaoPagamento(CondicaoPagamento _condicao_pagamento) {
		condicao_pagamento = _condicao_pagamento;

		cbCondicaoPagamento.removeAllItems();
		cbCondicaoPagamento.addItem(_condicao_pagamento.getNome_condicao_pagamento());
	}
	
	public void setConta(FinanceiroConta _financeiro_conta) {
		financeiro_conta = _financeiro_conta;

		cbConta.removeAllItems();
		cbConta.addItem(_financeiro_conta.getNome());
	}



	public Lancamento getDadosAtualizar(Lancamento lancamento_antigo) {

		Lancamento lancamento = new Lancamento();
		lancamento.setId_lancamento(lancamento_antigo.getId_lancamento());

		String status, observacao, descricao, data_lancamento, data_vencimento, data_pagamento, s_valor = null;
		BigDecimal valor = BigDecimal.ZERO;
		//tela as partes
		if(centro_custo != null) {
			lancamento.setId_centro_custo(centro_custo.getId_centro_custo());
		}
		
		if(instituicao_bancaria != null) {
			lancamento.setId_instituicao_bancaria(instituicao_bancaria.getId_instituicao_bancaria());
		}
		
		if(cliente_selecionado != null) {
			lancamento.setId_cliente_fornecedor(cliente_selecionado.getId());
		}
		
		//tela pagamento
		if(financeiro_conta != null) {
			lancamento.setId_conta(financeiro_conta.getId());
		}
		
		if(condicao_pagamento != null) {
			lancamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		}
		
		try {
			s_valor = entValor.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor = new BigDecimal(s_valor);
			lancamento.setValor(valor);

		}catch(Exception e) {
			lancamento.setValor(BigDecimal.ZERO);
		}
		
		
		data_pagamento = entDataPagamento.getText();
		data_vencimento = entDataVencimento.getText();
		
		lancamento.setData_pagamento(data_pagamento);
		lancamento.setData_vencimento(data_vencimento);
		
		//tela concluir
		data_lancamento = entDataLancamento.getText();
		status = entStatus.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		
		lancamento.setStatus(status);
		lancamento.setObservacao(observacao);
		lancamento.setDescricao(descricao);
		lancamento.setData_lancamento(data_lancamento);
		
		
		return lancamento;
	}

	public Lancamento getDadosSalvar() {

		Lancamento lancamento = new Lancamento();

		String status, observacao, descricao, data_lancamento, data_vencimento, data_pagamento, s_valor = null;
		BigDecimal valor = BigDecimal.ZERO;
		//tela as partes
		if(centro_custo != null) {
			lancamento.setId_centro_custo(centro_custo.getId_centro_custo());
		}
		
		if(instituicao_bancaria != null) {
			lancamento.setId_instituicao_bancaria(instituicao_bancaria.getId_instituicao_bancaria());
		}
		
		if(cliente_selecionado != null) {
			lancamento.setId_cliente_fornecedor(cliente_selecionado.getId());
		}
		
		//tela pagamento
		if(financeiro_conta != null) {
			lancamento.setId_conta(financeiro_conta.getId());
		}
		
		if(condicao_pagamento != null) {
			lancamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		}
		
		try {
			s_valor = entValor.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor = new BigDecimal(s_valor);
			lancamento.setValor(valor);

		}catch(Exception e) {
			lancamento.setValor(BigDecimal.ZERO);
		}
		
		
		data_pagamento = entDataPagamento.getText();
		data_vencimento = entDataVencimento.getText();
		
		lancamento.setData_pagamento(data_pagamento);
		lancamento.setData_vencimento(data_vencimento);
		
		//tela concluir
		data_lancamento = entDataLancamento.getText();
		status = entStatus.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		
		lancamento.setStatus(status);
		lancamento.setObservacao(observacao);
		lancamento.setDescricao(descricao);
		lancamento.setData_lancamento(data_lancamento);
		
		
		return lancamento;
	}

}
