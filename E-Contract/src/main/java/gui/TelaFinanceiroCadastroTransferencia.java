
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
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
import main.java.cadastros.DadosRecebimento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
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
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoParcelas;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;

public class TelaFinanceiroCadastroTransferencia extends JDialog {

	private final JPanel painelPrincipal = new JPanel();
	private final JPanel painelOdin = new JPanel();

	private TelaFinanceiroCadastroTransferencia isto;
	private JDialog telaPai;
	private final JButton btnCadastrar = new JButton("Cadastrar");
	private final JButton btnAtualizar = new JButton("Atualizar");
	private final JPanel painelTitulo = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Cadastro de Transferência");
	private final JPanel painelBotoes = new JPanel();
	private final JPanel painelPilar = new JPanel();
	private CadastroCliente cliente_selecionado = null;
	private JPanel painelAsPartes = new JPanel();
	private JPanel painelConcluir = new JPanel();
	private CentroCusto centro_custo;
	private InstituicaoBancaria instituicao_bancaria;
	private CondicaoPagamento condicao_pagamento;
	private FinanceiroConta financeiro_conta;
	private CadastroCliente cliente;

	private final JTabbedPane abas = new JTabbedPane(JTabbedPane.TOP);
	private final JLabel lblNewLabel_1 = new JLabel("Instituição Bancária:");
	private final JComboBox cbRemetente = new JComboBox();
	private final JLabel lblNewLabel_3 = new JLabel("Remetente");
	private final JLabel lblNewLabel_4 = new JLabel("Destinatário");
	private final JLabel lblNewLabel_1_1 = new JLabel("Instituição Bancária:");
	private final JPanel panel = new JPanel();
	private final JButton btnSelecionar = new JButton("Selecionar");
	private final JComboBox cbDestinatario = new JComboBox();
	private final JButton btnSelecionarCentroCusto = new JButton("Selecionar");
	private final JLabel lblNewLabel_4_1_2 = new JLabel("Dados da Transferência:");
	private final JLabel lblNewLabel_1_2_2_1 = new JLabel("Observação:");
	private final JLabel lblNewLabel_1_2_2_1_1 = new JLabel("Descrição:");
	private final JLabel lblNewLabel_5 = new JLabel("New label");
	private final JLabel lblNewLabel_5_2 = new JLabel("New label");
	private final JLabel lblNewLabel_5_2_1 = new JLabel("Data desta Transferência:");
	private final JEditorPane entObservacao = new JEditorPane();
	private final JEditorPane entDescricao = new JEditorPane();
	private final JLabel lblNewLabel_5_3 = new JLabel("New label");
	private final JLabel lblNewLabel_5_4 = new JLabel("New label");
	private final JLabel lblNewLabel_5_5 = new JLabel("New label");
	private final JTextFieldPersonalizado entDataLancamento = new JTextFieldPersonalizado();
	private final JPanel painelDespesa = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JRadioButton rdbtnDespesa = new JRadioButton("Transferência");
	private final JLabel lblNewLabel_6 = new JLabel("Tipo de Lançamento:");
	private final JLabel lblNewLabel_6_1 = new JLabel("Prioridade:");
	private final JComboBox cbPrioridade = new JComboBox();

	private String servidor_unidade;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final JLabel lblNewLabel_1_2_1_2 = new JLabel("Identificador Geral:");
	private final JTextFieldPersonalizado entIdentificador = new JTextFieldPersonalizado();
	private final JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Valor Total:");
	private final JTextFieldPersonalizado entValor = new JTextFieldPersonalizado();

	private InstituicaoBancaria remetente, destinatario;

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();
		servidor_unidade = configs_globais.getServidorUnidade();
	}

	public TelaFinanceiroCadastroTransferencia(int modo_operacao, Lancamento lancamento, Window janela_pai) {

		isto = this;
		getDadosGlobais();
		if (modo_operacao == 0)
			setTitle("E-Contract - Cadastro Transferência");
		else if (modo_operacao == 1)
			setTitle("E-Contract - Edição de Transferência");

		setContentPane(painelOdin);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 772, 709);
		painelOdin.setLayout(new BorderLayout(0, 0));
		painelOdin.add(painelPrincipal);

		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal.setLayout(new MigLayout("", "[50px,grow][379px,grow][74px][8px][83px,grow][grow][100px]",
				"[46px][16px][100px][grow][28px,grow]"));
		painelTitulo.setForeground(Color.WHITE);
		painelTitulo.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(painelTitulo, "cell 0 0 7 2,grow");
		painelTitulo.setLayout(new MigLayout("", "[617px]", "[20px][]"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);

		painelTitulo.add(lblNewLabel, "cell 0 0 1 2,alignx left,aligny center");
		painelPilar.setBackground(new Color(0, 0, 102));

		painelPrincipal.add(painelPilar, "cell 6 2 1 3,grow");

		painelPrincipal.add(abas, "cell 0 2 6 1,grow");
		painelAsPartes.setBackground(Color.WHITE);

		abas.addTab("As Partes", painelAsPartes);
		painelAsPartes.setLayout(new MigLayout("", "[][grow]", "[grow][][][][][][][][][]"));
		panel_1.setBackground(Color.WHITE);

		painelAsPartes.add(panel_1, "cell 0 0 2 1,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[][][grow][]", "[grow][grow][][10px][][]"));
		lblNewLabel_6.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_1.add(lblNewLabel_6, "cell 0 0 3 1,alignx center");
		rdbtnDespesa.setEnabled(false);
		rdbtnDespesa.setSelected(true);
		rdbtnDespesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnDespesa.setSelected(true);
				setPainelDespesa();

			}
		});
		rdbtnDespesa.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_1.add(rdbtnDespesa, "cell 2 1,alignx center");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_1.add(lblNewLabel_6_1, "cell 1 4,alignx trailing");
		cbPrioridade.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cbPrioridade.addItem("Alta Prioridade - Ainda esta semana");
		cbPrioridade.addItem("Média Prioridade - Em menos de 15 dias");
		cbPrioridade.addItem("Prioridade Leve - Ainda este mês");
		cbPrioridade.addItem("Baixa Prioridade - Ainda este ano");

		panel_1.add(cbPrioridade, "cell 2 4 2 1,growx");

		painelAsPartes.add(painelDespesa, "cell 0 1 2 1");

		painelDespesa.setBackground(Color.WHITE);
		painelDespesa.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][]"));
		painelDespesa.add(lblNewLabel_5, "cell 0 0");
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_3, "cell 0 1 3 1,growx");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(255, 102, 51));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelDespesa.add(lblNewLabel_5_3, "cell 0 2");
		lblNewLabel_5_3.setVisible(false);
		lblNewLabel_5_3.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_1, "cell 0 3,alignx right");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(cbRemetente, "cell 1 3,growx");
		cbRemetente.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(btnSelecionarCentroCusto, "cell 2 3");
		btnSelecionarCentroCusto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(0, 10, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarCentroCusto.setForeground(Color.WHITE);
		btnSelecionarCentroCusto.setBackground(new Color(0, 0, 153));
		painelDespesa.add(lblNewLabel_5_4, "cell 0 5");
		lblNewLabel_5_4.setVisible(false);
		lblNewLabel_5_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_4, "cell 0 6 3 1,growx");
		lblNewLabel_4.setBackground(new Color(0, 153, 153));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelDespesa.add(lblNewLabel_5_5, "cell 0 7");
		lblNewLabel_5_5.setVisible(false);
		lblNewLabel_5_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_1_1, "cell 0 8,alignx right");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(panel, "cell 1 8");
		panel.setBackground(new Color(0, 153, 153));
		panel.setLayout(new MigLayout("", "[192px][87px][][][][][][]", "[28px]"));
		cbDestinatario.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel.add(cbDestinatario, "cell 0 0 8 1,growx,aligny center");
		painelDespesa.add(btnSelecionar, "cell 2 8");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(0, 11, isto);
				tela.setVisible(true);
			}
		});
		btnSelecionar.setBackground(new Color(0, 0, 153));
		btnSelecionar.setForeground(Color.WHITE);
		painelDespesa.setVisible(true);
		Date hoje = new Date();
		SimpleDateFormat df;
		df = new SimpleDateFormat("dd/MM/yyyy");
		painelConcluir.setBackground(Color.WHITE);
		abas.addTab("Concluir", painelConcluir);
		painelConcluir.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][grow][grow]"));
		lblNewLabel_5_2.setVisible(false);
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 18));

		painelConcluir.add(lblNewLabel_5_2, "cell 0 0");
		lblNewLabel_4_1_2.setOpaque(true);
		lblNewLabel_4_1_2.setForeground(Color.WHITE);
		lblNewLabel_4_1_2.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_4_1_2.setBackground(new Color(51, 0, 255));

		painelConcluir.add(lblNewLabel_4_1_2, "cell 0 1 3 1,growx");
		lblNewLabel_1_2_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelConcluir.add(lblNewLabel_1_2_1_2, "cell 0 2,alignx trailing");

		painelConcluir.add(entIdentificador, "cell 1 2,growx");
		entIdentificador.setForeground(Color.black);
		lblNewLabel_1_2_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelConcluir.add(lblNewLabel_1_2_1_1_1, "cell 0 3,alignx trailing");

		painelConcluir.add(entValor, "cell 1 3,growx");
		entValor.setForeground(Color.black);
		lblNewLabel_5_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelConcluir.add(lblNewLabel_5_2_1, "cell 0 4,alignx trailing");

		painelConcluir.add(entDataLancamento, "cell 1 4 2 1,growx");
		entDataLancamento.setText(df.format(hoje));
		entDataLancamento.setForeground(Color.black);
		lblNewLabel_1_2_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelConcluir.add(lblNewLabel_1_2_2_1, "cell 0 5,alignx trailing,aligny top");
		entObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		entObservacao.setFont(new Font("SansSerif", Font.BOLD, 14));

		painelConcluir.add(entObservacao, "cell 1 5 1 3,grow");
		lblNewLabel_1_2_2_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelConcluir.add(lblNewLabel_1_2_2_1_1, "cell 0 8,alignx trailing,aligny top");
		entDescricao.setFont(new Font("SansSerif", Font.BOLD, 14));
		entDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));

		painelConcluir.add(entDescricao, "cell 1 8 2 1,grow");
		painelBotoes.setBackground(Color.WHITE);

		painelPrincipal.add(painelBotoes, "cell 5 4,alignx right,growy");
		painelBotoes.setLayout(new MigLayout("", "[][]", "[]"));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
				Lancamento lancamento_atualizar = getDadosAtualizar(lancamento);
				if (lancamento_atualizar != null) {
					boolean result = gerenciar.atualizarLancamento(getDadosAtualizar(lancamento));
					if (result) {

						JOptionPane.showMessageDialog(isto, "Atualizado");
						((TelaFinanceiroLancamento) janela_pai).pesquisar();
						isto.dispose();

					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Atualizar\nConsulte o Administrador");

					}
				}
			}
		});
		painelBotoes.add(btnAtualizar, "cell 0 0,growx");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCadastrar.setBackground(new Color(0, 0, 102));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
				Lancamento lancamento = getDadosSalvar();
				if (lancamento != null) {
					int result = gerenciar.inserirLancamento(lancamento);
					if (result > 0) {

						// adicionar parcelas
						GerenciarBancoParcelas gerenciar_parcelas = new GerenciarBancoParcelas();

						Parcela parcela = new Parcela();
						parcela.setValor(lancamento.getValor());
						parcela.setDescricao("Parcela Número " + 1 + " gerada automaticamente");
						parcela.setIdentificador(1 + "");
						parcela.setObservacao(
								"Um número aleatorio foi criado para o identificador desta parcela, troque o se necessário: "
										+ 1);
						String data_desta_parcela_formatada = lancamento.getData_lancamento();
						parcela.setData_vencimento(data_desta_parcela_formatada);
						parcela.setFluxo_caixa(1);
						parcela.setId_lancamento_pai(result);

						gerenciar_parcelas.inserirParcela(parcela);

						// inserir pagamento
						GerenciarBancoFinanceiroPagamento gerenciar_pags = new GerenciarBancoFinanceiroPagamento();

						FinanceiroPagamento pagamento = new FinanceiroPagamento();
						pagamento.setValor(lancamento.getValor());

						pagamento.setDescricao("Pagamento referente a transferência");
						pagamento.setId_lancamento(lancamento.getId_lancamento());

						pagamento.setIdentificador("");
						pagamento.setObservacao("");

						pagamento.setFluxo_caixa(1);

						pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());

						pagamento.setStatus_pagamento(0);

						// pagador
						pagamento.setTipo_pagador(0);
						pagamento.setId_pagador(lancamento.getId_centro_custo());

						// recebedor

						pagamento.setTipo_recebedor(0);
						pagamento.setId_recebedor(lancamento.getId_cliente_fornecedor());

						pagamento.setData_pagamento(lancamento.getData_pagamento());

						int insercao = gerenciar_pags.inserirFinanceiroPagamento(pagamento);
						if (insercao > 0) {

						}

						// criar pasta, mover arquivo
						ManipularTxt manipular = new ManipularTxt();
						String caminho_diretorio = "\\E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
								+ result;
						String caminho_diretorio_bd = "\\\\E-Contract\\\\arquivos\\\\financas\\\\lancamentos\\\\lancamento_"
								+ result;
						boolean criar_diretorio = manipular.criarDiretorio(servidor_unidade + caminho_diretorio);
						if (criar_diretorio) {

							boolean atualizar_diretorio = gerenciar.atualizarDiretorioLancamento(caminho_diretorio_bd,
									result);
							if (atualizar_diretorio) {

								// copiar o arquivo selecionado

								if (lancamento.getCaminho_arquivo() != null
										&& lancamento.getCaminho_arquivo().length() > 20) {
									boolean copiar_arquivo = false;
									String extensaoDoArquivo = FilenameUtils
											.getExtension(lancamento.getCaminho_arquivo());

									String arquivo_doc_documento_final = servidor_unidade + caminho_diretorio
											+ "\\doc_lancamento_" + result + "." + extensaoDoArquivo;
									try {
										copiar_arquivo = manipular.copiarNFe(lancamento.getCaminho_arquivo(),
												arquivo_doc_documento_final);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									if (copiar_arquivo) {
										// atualiza parcela no banco de dados
										boolean atualizar = gerenciar.atualizarCaminhoLancamento(
												"doc_lancamento_" + result + "." + extensaoDoArquivo, result);
										if (!atualizar)
											JOptionPane.showMessageDialog(isto,
													"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

									} else {
										JOptionPane.showMessageDialog(isto,
												"Arquivo não copiado, consulte o administrador");

									}
								}

								JOptionPane.showMessageDialog(isto, "Cadastro Concluído");
								((TelaFinanceiroLancamento) janela_pai).pesquisar();
								isto.dispose();

							}

						} else {
							JOptionPane.showMessageDialog(isto,
									"Cadastro Conluido, mas houve erro ao criar o diretorio\nConsulte o Administrador");

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

					}
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

		int prioridade = lancamento.getPrioridade();
		cbPrioridade.setSelectedIndex(prioridade);

		entIdentificador.setText(lancamento.getIdentificacao());

		entValor.setText(lancamento.getValor().toString());

		// tela concluir
		entDataLancamento.setText(lancamento.getData_lancamento());

		entObservacao.setText(lancamento.getObservacao());
		entDescricao.setText(lancamento.getDescricao());

		setRemetente(new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(lancamento.getId_centro_custo()));
		setDestinatario(
				new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(lancamento.getId_cliente_fornecedor()));

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

	public Lancamento getDadosAtualizar(Lancamento lancamento_antigo) {

		Lancamento lancamento = new Lancamento();
		lancamento.setId_lancamento(lancamento_antigo.getId_lancamento());

		int status, prioridade, gerar_parcelas, intervalo;
		String identificacao, observacao, descricao, caminho_arquivo, data_lancamento, data_vencimento, data_pagamento,
				s_valor = null;
		BigDecimal valor = BigDecimal.ZERO;
		// tela as partes
		if (remetente != null) {
			lancamento.setId_centro_custo(remetente.getId_instituicao_bancaria());
		} else {
			JOptionPane.showMessageDialog(isto, "Selecione o Remetente");
			return null;
		}

		if (destinatario != null) {
			lancamento.setId_cliente_fornecedor(destinatario.getId_instituicao_bancaria());
		} else {
			JOptionPane.showMessageDialog(isto, "Selecione o Destinatário");
			return null;
		}

		try {
			s_valor = entValor.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor = new BigDecimal(s_valor);
			lancamento.setValor(valor);

		} catch (Exception e) {
			lancamento.setValor(BigDecimal.ZERO);
			JOptionPane.showMessageDialog(isto, "Valor Inválido!");

			return null;
		}

		// tela concluir
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();

		lancamento.setObservacao(observacao);
		lancamento.setDescricao(descricao);

		lancamento.setTipo_lancamento(2);
		lancamento.setPrioridade(cbPrioridade.getSelectedIndex());

		lancamento.setIdentificacao(entIdentificador.getText());

		data_lancamento = entDataLancamento.getText();

		try {

			if (!isDateValid(data_lancamento)) {
				JOptionPane.showMessageDialog(isto, "Data da Transferência Inválida");

				return null;
			} else {

				lancamento.setData_lancamento(data_lancamento);
				return lancamento;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Datas Inválidas");
			return null;
		}
	}

	public Lancamento getDadosSalvar() {

		Lancamento lancamento = new Lancamento();
		int status, prioridade, gerar_parcelas, intervalo;
		String identificacao, observacao, descricao, caminho_arquivo, data_lancamento, data_vencimento, data_pagamento,
				s_valor = null;
		BigDecimal valor = BigDecimal.ZERO;
		// tela as partes
		if (remetente != null) {
			lancamento.setId_centro_custo(remetente.getId_instituicao_bancaria());
		} else {
			JOptionPane.showMessageDialog(isto, "Selecione o Remetente");
			return null;
		}

		if (destinatario != null) {
			lancamento.setId_cliente_fornecedor(destinatario.getId_instituicao_bancaria());
		} else {
			JOptionPane.showMessageDialog(isto, "Selecione o Destinatário");
			return null;
		}

		try {
			s_valor = entValor.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor = new BigDecimal(s_valor);
			lancamento.setValor(valor);

		} catch (Exception e) {
			lancamento.setValor(BigDecimal.ZERO);
			JOptionPane.showMessageDialog(isto, "Valor Inválido!");

			return null;
		}

		// tela concluir
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();

		lancamento.setObservacao(observacao);
		lancamento.setDescricao(descricao);

		lancamento.setTipo_lancamento(2);
		lancamento.setPrioridade(cbPrioridade.getSelectedIndex());

		lancamento.setIdentificacao(entIdentificador.getText());

		data_lancamento = entDataLancamento.getText();

		try {

			if (!isDateValid(data_lancamento)) {
				JOptionPane.showMessageDialog(isto, "Data da Transferência Inválida");

				return null;
			} else {

				lancamento.setData_lancamento(data_lancamento);
				return lancamento;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Datas Inválidas");
			return null;
		}

	}

	public static boolean isDateValid(String strDate) {
		String dateFormat = "dd/MM/uuuu";

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
				.withResolverStyle(ResolverStyle.STRICT);
		try {
			LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public void setPainelDespesa() {

		painelDespesa.setBackground(Color.WHITE);
		painelDespesa.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][]"));
		painelDespesa.add(lblNewLabel_5, "cell 0 0");
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_3, "cell 0 1 3 1,growx");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(255, 102, 51));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelDespesa.add(lblNewLabel_5_3, "cell 0 2");
		lblNewLabel_5_3.setVisible(false);
		lblNewLabel_5_3.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_1, "cell 0 3,alignx right");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(cbRemetente, "cell 1 3,growx");
		cbRemetente.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(btnSelecionarCentroCusto, "cell 2 3");

		btnSelecionarCentroCusto.setForeground(Color.WHITE);
		btnSelecionarCentroCusto.setBackground(new Color(0, 0, 153));

		painelDespesa.add(lblNewLabel_5_4, "cell 0 5");
		lblNewLabel_5_4.setVisible(false);
		lblNewLabel_5_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_4, "cell 0 6 3 1,growx");
		lblNewLabel_4.setBackground(new Color(0, 153, 153));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelDespesa.add(lblNewLabel_5_5, "cell 0 7");
		lblNewLabel_5_5.setVisible(false);
		lblNewLabel_5_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_1_1, "cell 0 8,alignx right");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(panel, "cell 1 8");
		panel.setBackground(new Color(0, 153, 153));
		panel.setLayout(new MigLayout("", "[192px][87px][][][][][][]", "[28px]"));
		cbDestinatario.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel.add(cbDestinatario, "cell 0 0 8 1,growx,aligny center");
		painelDespesa.add(btnSelecionar, "cell 2 8");

		btnSelecionar.setBackground(new Color(0, 0, 153));
		btnSelecionar.setForeground(Color.WHITE);
		painelDespesa.setVisible(true);

	}

	public void setPainelReceita() {
		// painelAsPartes.add(painelDespesa, "cell 0 1 2 1");

		painelDespesa.setBackground(Color.WHITE);
		painelDespesa.setLayout(new MigLayout("", "[][][]", "[][][][][][][][][][][][][]"));
		painelDespesa.add(lblNewLabel_5, "cell 0 0");
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_3, "cell 0 1 3 1,growx");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(255, 102, 51));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelDespesa.add(lblNewLabel_5_3, "cell 0 2");
		lblNewLabel_5_3.setVisible(false);
		lblNewLabel_5_3.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_1_1, "cell 0 3,alignx right");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(panel, "cell 1 3");
		panel.setBackground(new Color(0, 153, 153));
		panel.setLayout(new MigLayout("", "[192px][87px][][][][][][]", "[28px]"));
		cbDestinatario.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel.add(cbDestinatario, "cell 0 0 8 1,growx,aligny center");
		painelDespesa.add(btnSelecionar, "cell 2 3");

		btnSelecionar.setBackground(new Color(0, 0, 153));
		btnSelecionar.setForeground(Color.WHITE);
		painelDespesa.add(lblNewLabel_5_4, "cell 0 6");
		lblNewLabel_5_4.setVisible(false);
		lblNewLabel_5_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_4, "cell 0 7 3 1,growx");
		lblNewLabel_4.setBackground(new Color(0, 153, 153));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("SansSerif", Font.BOLD, 18));
		painelDespesa.add(lblNewLabel_5_5, "cell 0 8");
		lblNewLabel_5_5.setVisible(false);
		lblNewLabel_5_5.setFont(new Font("SansSerif", Font.PLAIN, 18));
		painelDespesa.add(lblNewLabel_1, "cell 0 9,alignx right");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(cbRemetente, "cell 1 9,growx");
		cbRemetente.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelDespesa.add(btnSelecionarCentroCusto, "cell 2 9");

		btnSelecionarCentroCusto.setForeground(Color.WHITE);
		btnSelecionarCentroCusto.setBackground(new Color(0, 0, 153));

		painelDespesa.setVisible(true);

	}

	public boolean converterPdfESalvar(String caminho_arquivo) {
		Document document = new Document(PageSize.A4, 20, 20, 20, 20);
		try {
			PdfWriter.getInstance(document, new FileOutputStream("C:/test.pdf"));
			document.open();
			Image image = Image.getInstance(getClass().getResource(caminho_arquivo));
			document.add(image);
			document.close();

			return true;
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void setRemetente(InstituicaoBancaria _remetente) {
		this.remetente = _remetente;

		cbRemetente.removeAllItems();
		cbRemetente.addItem(_remetente.getNome_instituicao_bancaria());
	}

	public void setDestinatario(InstituicaoBancaria _destinatario) {
		this.destinatario = _destinatario;

		cbDestinatario.removeAllItems();
		cbDestinatario.addItem(_destinatario.getNome_instituicao_bancaria());

	}

}
