package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
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
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoEmprestimo;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
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
import main.java.classesExtras.ComboBoxRenderPersonalizado;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoSafras;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;

public class TelaFinanceiroCadastroPagamentoEmprestimo extends JFrame {

	private final KGradientPanel painelPrincipal = new KGradientPanel();
	private JLabel lblTotalContratosConcluidos, lblTotalContratos, lblTotalContratosAbertos;
	private TelaFinanceiroCadastroPagamentoEmprestimo isto;
	private JDialog telaPai;
	private JTextFieldPersonalizado entValorTotal;
	private JTextFieldPersonalizado entDescricao;
	private JTextFieldPersonalizado entDataPagamento;
	private JTextFieldPersonalizado entIdentificador = new JTextFieldPersonalizado();
	private JEditorPane entObservacao = new JEditorPane();
	private CondicaoPagamento condicao_pagamento;
	private JComboBox cbCondicaoPagamento, cbStatusCondicaoPagamento;
	private InstituicaoBancaria pagador_ib;
	private InstituicaoBancaria recebedor_ib;

	private CadastroCliente pagador_cliente_fornecedor;
	private JComboBox cbPagador, cbRecebedor;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String servidor_unidade;
	private JPanel painelPaiProduto;
	private JTextFieldPersonalizado entEspecie, entQuantidade, entValorUnitario;
	private JRadioButton rdbtnKgs, rdbtnSacos;
	private JComboBox cbObjeto;
	private JRadioButton rdbtnPagadorIB, rdbtnPagadorClienteforncedor,rdbtnRecebedorCliente,rdbtnRecebedorIB;
	private JRadioButton rdbtnFluxoDeCaixaSim, rdbtnFluxoDeCaixaNao;
	private CadastroCliente recebedor_cliente_fornecedor;

	public TelaFinanceiroCadastroPagamentoEmprestimo(int modo_operacao, FinanceiroPagamentoEmprestimo pagamento,
			Lancamento lancamento_pai, Window janela_pai) {

		isto = this;
		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();
		setResizable(true);
		if (modo_operacao == 0)
			setTitle("E-Contract - Cadastro Pagamento de Empréstimo");
		else if (modo_operacao == 1)
			setTitle("E-Contract - Edição Pagamento de Empréstimo");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 809, 700);
		painelPrincipal.kStartColor = new Color(255, 255, 204);
		painelPrincipal.kEndColor = new Color(255, 204, 153);
		painelPrincipal.setBackground(new Color(0, 51, 51));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal
				.setLayout(new MigLayout("", "[grow][grow][][]", "[][][][1px][][][][][][][][][][50px:n,grow][]"));

		JLabel lblFluxoDeCaixa = new JLabel("Fluxo de Caixa?:");
		lblFluxoDeCaixa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblFluxoDeCaixa, "cell 0 0,alignx right");

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBorder(null);
		panel_1.setBackground(Color.BLACK);
		painelPrincipal.add(panel_1, "cell 1 0 2 1,grow");
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));

		rdbtnFluxoDeCaixaSim = new JRadioButton("Sim");
		rdbtnFluxoDeCaixaSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnFluxoDeCaixaSim.setSelected(true);
				rdbtnFluxoDeCaixaNao.setSelected(false);
			}
		});
		rdbtnFluxoDeCaixaSim.setSelected(true);
		rdbtnFluxoDeCaixaSim.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_1.add(rdbtnFluxoDeCaixaSim, "cell 0 0");

		rdbtnFluxoDeCaixaNao = new JRadioButton("Não");
		rdbtnFluxoDeCaixaNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnFluxoDeCaixaSim.setSelected(false);
				rdbtnFluxoDeCaixaNao.setSelected(true);
			}
		});
		rdbtnFluxoDeCaixaNao.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_1.add(rdbtnFluxoDeCaixaNao, "cell 1 0");

		JLabel lblIdentificador = new JLabel("Identificador:");
		lblIdentificador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblIdentificador, "cell 0 1,alignx trailing");

		entIdentificador.setForeground(Color.black);
		painelPrincipal.add(entIdentificador, "cell 1 1 3 1,growx");

		JLabel lblObjeto = new JLabel("Objeto:");
		lblObjeto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblObjeto, "cell 0 2,alignx trailing");
		cbObjeto = new JComboBox();
		cbObjeto = new JComboBox();
		cbObjeto.addItem("Moeda");
		cbObjeto.addItem("Produto");
		cbObjeto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (evt.getItem().equals("Produto")) {
						setPainelProduto();

					} else if (evt.getItem().equals("Moeda")) {
						removerPainel();
					}
				}

			}

		});

		painelPrincipal.add(cbObjeto, "cell 1 2 3 1,growx");
		painelPaiProduto = new JPanel();
		painelPaiProduto.setOpaque(false);
		painelPaiProduto.setBackground(Color.BLACK);
		painelPrincipal.add(painelPaiProduto, "cell 0 3 4 1,alignx center,growy");
		painelPaiProduto.setLayout(new MigLayout("", "[]", "[]"));

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblValor, "cell 0 4,alignx trailing");

		entValorTotal = new JTextFieldPersonalizado();
		entValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(entValorTotal, "cell 1 4 3 1,growx");
		entValorTotal.setForeground(Color.black);
		entValorTotal.setColumns(10);

		JLabel lblDataVencimento = new JLabel("Data Pagamento:");
		lblDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblDataVencimento, "cell 0 5,alignx trailing");

		entDataPagamento = new JTextFieldPersonalizado();
		entDataPagamento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entDataPagamento.setColumns(10);
		entDataPagamento.setForeground(Color.black);

		painelPrincipal.add(entDataPagamento, "cell 1 5 3 1,growx");

		JLabel lblNewLabel = new JLabel("Descrição:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblNewLabel, "cell 0 6,alignx right");

		entDescricao = new JTextFieldPersonalizado();
		entDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		entDescricao.setColumns(10);
		entDescricao.setForeground(Color.black);
		painelPrincipal.add(entDescricao, "cell 1 6 3 1,growx");

		JLabel lblCondioDoPagamento = new JLabel("Condição do Pagamento:");
		lblCondioDoPagamento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblCondioDoPagamento, "cell 0 7,alignx trailing");

		cbCondicaoPagamento = new JComboBox();
		cbCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbCondicaoPagamento, "cell 1 7 2 1,growx");

		JButton btnNewButton_1_1 = new JButton("Selecionar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFinanceiroCondicaoPagamento tela = new TelaFinanceiroCondicaoPagamento(0, 1, isto);
				tela.setVisible(true);
			}
		});
		btnNewButton_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnNewButton_1_1.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnNewButton_1_1, "cell 3 7");

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblStatus, "cell 0 8,alignx trailing");

		cbStatusCondicaoPagamento = new JComboBox();
		cbStatusCondicaoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbStatusCondicaoPagamento, "cell 1 8 2 1,growx");
		cbStatusCondicaoPagamento.addItem("A - Compensar|Realizar|Concluir");
		cbStatusCondicaoPagamento.addItem("Compensado|Realizado|Concluído");

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(null);
		panel.setBackground(Color.BLACK);
		painelPrincipal.add(panel, "cell 1 9,grow");
		panel.setLayout(new MigLayout("", "[][]", "[]"));

		rdbtnPagadorIB = new JRadioButton("Uma Instituição Bancaria?");
		rdbtnPagadorIB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnPagadorIB.setSelected(true);
				rdbtnPagadorClienteforncedor.setSelected(false);
			}
		});
		rdbtnPagadorIB.setSelected(true);
		rdbtnPagadorIB.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(rdbtnPagadorIB, "cell 0 0");

		rdbtnPagadorClienteforncedor = new JRadioButton("Um Cliente/Fornecedor?");
		rdbtnPagadorClienteforncedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnPagadorIB.setSelected(false);
				rdbtnPagadorClienteforncedor.setSelected(true);
			}
		});
		rdbtnPagadorClienteforncedor.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel.add(rdbtnPagadorClienteforncedor, "cell 1 0");

		JLabel lblPagador = new JLabel("Pagador:");
		lblPagador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblPagador, "cell 0 10,alignx trailing");

		cbPagador = new JComboBox();
		cbPagador.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbPagador, "cell 1 10 2 1,growx");

		JButton btnSelecionarPagador = new JButton("Selecionar");
		btnSelecionarPagador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rdbtnPagadorIB.isSelected()) {

					TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(0,1,isto);
					tela.setVisible(true);
					
				} else if (rdbtnPagadorClienteforncedor.isSelected()) {
					// pagador e um cliente

					// pagador e uma instituicao bancaria
					TelaCliente tela = new TelaCliente(0, 31, isto);
					tela.setVisible(true);
				}

			}
		});
		btnSelecionarPagador.setForeground(Color.WHITE);
		btnSelecionarPagador.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSelecionarPagador.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnSelecionarPagador, "cell 3 10");

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBackground(Color.BLACK);
		painelPrincipal.add(panel_2, "cell 1 11,grow");
		panel_2.setLayout(new MigLayout("", "[][]", "[]"));

		 rdbtnRecebedorIB = new JRadioButton("Uma Instituição Bancaria?");
		rdbtnRecebedorIB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   rdbtnRecebedorIB.setSelected(true);
				rdbtnRecebedorCliente.setSelected(false);
				
			}
		});
		rdbtnRecebedorIB.setSelected(true);
		rdbtnRecebedorIB.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_2.add(rdbtnRecebedorIB, "cell 0 0");

		 rdbtnRecebedorCliente = new JRadioButton("Um Cliente/Fornecedor?");
		 rdbtnRecebedorCliente.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		  rdbtnRecebedorIB.setSelected(false);
					rdbtnRecebedorCliente.setSelected(true);
					
		 	}
		 });
		rdbtnRecebedorCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_2.add(rdbtnRecebedorCliente, "cell 1 0");

		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar = new GerenciarBancoFinanceiroPagamentoEmprestimo();
				FinanceiroPagamentoEmprestimo pag = getDadosSalvar(lancamento_pai);
				if (pag != null) {
					int result = gerenciar.inserirFinanceiroPagamento(pag);
					if (result > 0) {
						///// *****************
						// verificar se a pasta do pagamento existe
						ManipularTxt manipular = new ManipularTxt();
						File pasta_de_pagamentos = new File(
								servidor_unidade + lancamento_pai.getDiretorio_lancamento() + "\\pagamento_" + result);
						if (!(pasta_de_pagamentos.exists())) {
							manipular.criarDiretorio(pasta_de_pagamentos.getAbsolutePath());
						}

						// copiar o arquivo para a pasta da parcela
						// antes verificar se o arquivo nao e nulo
						if (pag.getCaminho_arquivo() != null && pag.getCaminho_arquivo().length() > 20) {
							boolean copiar_arquivo = false;
							String extensaoDoArquivo = FilenameUtils.getExtension(pag.getCaminho_arquivo());

							String arquivo_doc_pagamento_final = pasta_de_pagamentos + "\\doc_comprovante_pagamento_"
									+ result + "." + extensaoDoArquivo;
							try {
								copiar_arquivo = manipular.copiarNFe(pag.getCaminho_arquivo(),
										arquivo_doc_pagamento_final);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (copiar_arquivo) {
								// atualiza parcela no banco de dados
								boolean atualizar = gerenciar.atualizarArquivoDoPagamento(
										"doc_comprovante_pagamento_" + result + "." + extensaoDoArquivo, result);
								if (!atualizar)
									JOptionPane.showMessageDialog(isto,
											"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

							} else {
								JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

							}
						}

						//// ***********
						JOptionPane.showMessageDialog(isto, "Pagamento Inserido");
						atualizarValorLancamentoEmprestimo(lancamento_pai);
						((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

					}
				}
			}
		});

		JLabel lblRecebedor = new JLabel("Recebedor:");
		lblRecebedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblRecebedor, "cell 0 12,alignx trailing");

		cbRecebedor = new JComboBox();
		cbRecebedor.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrincipal.add(cbRecebedor, "cell 1 12 2 1,growx");

		JButton btnSelecionarRecebedor = new JButton("Selecionar");
		btnSelecionarRecebedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rdbtnRecebedorIB.isSelected()) {
					TelaFinanceiroInstituicaoBancaria tela = new TelaFinanceiroInstituicaoBancaria(0,2,isto);
					tela.setVisible(true);
				} else if (rdbtnRecebedorCliente.isSelected()) {
					// pagador e um cliente
					
					TelaCliente tela = new TelaCliente(0, 36, isto);
					tela.setVisible(true);
				}
			}
		});
		btnSelecionarRecebedor.setForeground(Color.WHITE);
		btnSelecionarRecebedor.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSelecionarRecebedor.setBackground(new Color(0, 51, 0));
		painelPrincipal.add(btnSelecionarRecebedor, "cell 3 12");

		JLabel lblObservao = new JLabel("Observação:");
		lblObservao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(lblObservao, "cell 0 13,alignx right");

		entObservacao = new JEditorPane();
		painelPrincipal.add(entObservacao, "cell 1 13 2 1,grow");
		btnFinalizar.setBackground(new Color(0, 0, 102));
		btnFinalizar.setForeground(Color.WHITE);
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		painelPrincipal.add(btnFinalizar, "cell 1 14,alignx right");

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar = new GerenciarBancoFinanceiroPagamentoEmprestimo();
				FinanceiroPagamentoEmprestimo pag = getDadosAtualizar(pagamento, lancamento_pai);
				if (pag != null) {
					boolean result = gerenciar.atualizarFinanceiroPagamento(pag);
					if (result) {

						JOptionPane.showMessageDialog(isto, "Pagamento Atualizado");
						((TelaFinanceiroGerenciarLancamento) janela_pai).atualizar_informacoes();
						isto.dispose();
					} else {
						JOptionPane.showMessageDialog(isto, "Erro ao Salvar\nConsulte o Administrador");

					}
				}
			}
		});
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAtualizar.setBackground(new Color(0, 0, 102));
		painelPrincipal.add(btnAtualizar, "cell 2 14");

		if (modo_operacao == 0) {
			// novo cadastro

			btnAtualizar.setEnabled(false);
			btnAtualizar.setVisible(false);
		} else if (modo_operacao == 1) {
			// edicao

			btnFinalizar.setEnabled(false);
			btnFinalizar.setVisible(false);
			rotinasEdicao(pagamento, lancamento_pai);

		}

		if (lancamento_pai.getTipo_lancamento() == 0) {
			// é uma despesa
			lblRecebedor.setEnabled(false);
			lblRecebedor.setVisible(false);

			cbRecebedor.setEnabled(false);
			cbRecebedor.setVisible(false);

			btnSelecionarRecebedor.setEnabled(false);
			btnSelecionarRecebedor.setVisible(false);

		}

		this.setLocationRelativeTo(janela_pai);

	}

	public void rotinasEdicao(FinanceiroPagamentoEmprestimo pagamento, Lancamento lancamento_pai) {
		String s_valor, descricao, observacao, identificador, data_pagamento;
		int status;
		identificador = pagamento.getIdentificador();
		observacao = pagamento.getObservacao();
		descricao = pagamento.getDescricao();
		data_pagamento = pagamento.getData_pagamento();

		GerenciarBancoInstituicaoBancaria gerenciar_ibs = new GerenciarBancoInstituicaoBancaria();
		GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();

		BigDecimal valor = pagamento.getValor();

		entIdentificador.setText(identificador);
		entObservacao.setText(observacao);
		entDescricao.setText(descricao);
		entDataPagamento.setText(data_pagamento);
		entValorTotal.setText(valor.toString());
		cbStatusCondicaoPagamento.setSelectedIndex(pagamento.getStatus_pagamento());

		if (pagamento.getFluxo_caixa() == 1) {
			rdbtnFluxoDeCaixaSim.setSelected(true);
			rdbtnFluxoDeCaixaNao.setSelected(false);

		} else if (pagamento.getFluxo_caixa() == 0) {
			rdbtnFluxoDeCaixaSim.setSelected(false);
			rdbtnFluxoDeCaixaNao.setSelected(true);
		}

		setCondicaoPagamento(
				new GerenciarBancoCondicaoPagamentos().getCondicaoPagamento(pagamento.getId_condicao_pagamento()));

		if (pagamento.getId_pagador() > 0) {
			
			
			if (pagamento.getTipo_pagador() == 0) {
				
				rdbtnPagadorIB.setSelected(true);
				rdbtnPagadorClienteforncedor.setSelected(false);
				// pagador e uma instituicao bancaria
				InstituicaoBancaria ib = gerenciar_ibs.getInstituicaoBancaria(pagamento.getId_pagador());
				if (ib != null)
					setPagadorInstituicaoBancaria(ib);
			
			} else if (pagamento.getTipo_pagador() == 1) {
				
				rdbtnPagadorIB.setSelected(false);
				rdbtnPagadorClienteforncedor.setSelected(true);
				CadastroCliente cli = gerenciar_clientes.getCliente(pagamento.getId_pagador());
				if (cli != null)
					setPagadorClienteFornecedor(cli);
				
				
			}
		}

			if (pagamento.getId_recebedor() > 0) {
				if (pagamento.getTipo_recebedor() == 0) {
					
					rdbtnRecebedorIB.setSelected(true);
					rdbtnRecebedorCliente.setSelected(false);
					
					InstituicaoBancaria ib = gerenciar_ibs.getInstituicaoBancaria(pagamento.getId_recebedor());
					if (ib != null)
						setRecebedorInstituicaoBancaria(ib);
					
					
				} else if (pagamento.getTipo_recebedor() == 1) {
					rdbtnRecebedorIB.setSelected(false);
					rdbtnRecebedorCliente.setSelected(true);
					
					CadastroCliente cli = gerenciar_clientes.getCliente(pagamento.getId_recebedor());
					if (cli != null)
						setRecebedorClienteFornecedor(cli);
				}

			}
		
			int objeto = pagamento.getObjeto();
			
			if(objeto == 1) {
				//produto
				
				cbObjeto.setSelectedIndex(1);
				
				 removerPainel();
				setPainelProduto();
				
				String especie = pagamento.getEspecie();
				String unidade = pagamento.getUnidade_medida();
				double quantidade = pagamento.getQuantidade();
				BigDecimal valor_unitario = pagamento.getValor_unitario();
				
				
				entEspecie.setText(especie);
				if(unidade.equalsIgnoreCase("KG"))
				{
					rdbtnSacos.setSelected(false);
					rdbtnKgs.setSelected(true);
				}else if(unidade.equalsIgnoreCase("Saco")) {
					rdbtnSacos.setSelected(true);
					rdbtnKgs.setSelected(false);
				}
				
				entQuantidade.setText(Double.toString(quantidade));
				
				try {
					entValorUnitario.setText(valor_unitario.toString());

				}catch(Exception e) {
					
				}
				
				
			}

	}

	public FinanceiroPagamentoEmprestimo getDadosSalvar(Lancamento lancamento_pai) {
		FinanceiroPagamentoEmprestimo pagamento = new FinanceiroPagamentoEmprestimo();

		String s_valor, descricao, observacao, identificador, data_pagamento, caminho_arquivo;
		int objeto;
		BigDecimal valor_total = BigDecimal.ZERO;

		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_pagamento = entDataPagamento.getText();

		objeto = cbObjeto.getSelectedIndex();

		pagamento.setObjeto(objeto);
		if (objeto == 0) {
			// moeda
		} else if (objeto == 1) {
			// produto

			String especie, unidade_medida = "", s_valor_unitario;
			double quantidade;
			BigDecimal valor_unitario = BigDecimal.ZERO;

			especie = entEspecie.getText();

			if (rdbtnKgs.isSelected()) {
				unidade_medida = "KG";
			} else if (rdbtnSacos.isSelected()) {
				unidade_medida = "Sacos";
			}

			quantidade = Double.parseDouble(entQuantidade.getText());

			pagamento.setEspecie(especie);
			pagamento.setUnidade_medida(unidade_medida);
			pagamento.setQuantidade(quantidade);

			try {
				s_valor_unitario = entValorUnitario.getText();
				s_valor_unitario = s_valor_unitario.replace("[^0-9,]", "");
				s_valor_unitario = s_valor_unitario.replace(",", ".");
				valor_unitario = new BigDecimal(s_valor_unitario);
				pagamento.setValor_unitario(valor_unitario);

			} catch (Exception e) {
				pagamento.setValor_unitario(BigDecimal.ZERO);
			}

		}

		try {
			s_valor = entValorTotal.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor_total = new BigDecimal(s_valor);
			pagamento.setValor(valor_total);

		} catch (Exception e) {
			pagamento.setValor(BigDecimal.ZERO);
		}

		pagamento.setDescricao(descricao);
		pagamento.setId_lancamento(lancamento_pai.getId_lancamento());

		pagamento.setIdentificador(identificador);
		pagamento.setObservacao(observacao);

		if (rdbtnFluxoDeCaixaSim.isSelected()) {
			pagamento.setFluxo_caixa(1);
		} else if (rdbtnFluxoDeCaixaNao.isSelected()) {
			pagamento.setFluxo_caixa(0);

		}

		
		if (condicao_pagamento != null) {
			pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		} else {
			JOptionPane.showMessageDialog(isto, "Selecione a condição do pagamento");

			return null;
		}

		pagamento.setStatus_pagamento(cbStatusCondicaoPagamento.getSelectedIndex());
		//pagador
		if(rdbtnPagadorIB.isSelected()) {
			pagamento.setTipo_pagador(0);
			if (pagador_ib != null) {
				pagamento.setId_pagador(pagador_ib.getId_instituicao_bancaria());

			} else {
				JOptionPane.showMessageDialog(isto, "Selecione o pagador");

				return null;
			}
			
		}else if(rdbtnPagadorClienteforncedor.isSelected()) {
			pagamento.setTipo_pagador(1);
			if (pagador_cliente_fornecedor != null) {
				pagamento.setId_pagador(pagador_cliente_fornecedor.getId());

			} else {
				JOptionPane.showMessageDialog(isto, "Selecione o pagador");

				return null;
			}
		}
		
		//recebedor

		if(rdbtnRecebedorIB.isSelected()) {
			pagamento.setTipo_recebedor(0);
			if (recebedor_ib != null) {
				pagamento.setId_recebedor(recebedor_ib.getId_instituicao_bancaria());

			} else {
				JOptionPane.showMessageDialog(isto, "Selecione o pagador");

				return null;
			}
			
		}else if(rdbtnRecebedorCliente.isSelected()) {
			pagamento.setTipo_recebedor(1);
			if (recebedor_cliente_fornecedor != null) {
				pagamento.setId_recebedor(recebedor_cliente_fornecedor.getId());

			} else {
				JOptionPane.showMessageDialog(isto, "Selecione o pagador");

				return null;
			}
		}
		
		
		
		try {

			if (!isDateValid(data_pagamento)) {
				JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");

				return null;
			} else {
				pagamento.setData_pagamento(data_pagamento);
				return pagamento;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");
			return null;
		}

	}

	public FinanceiroPagamentoEmprestimo getDadosAtualizar(FinanceiroPagamentoEmprestimo pagamento_antigo,
			Lancamento lancamento_pai) {
		FinanceiroPagamentoEmprestimo pagamento = new FinanceiroPagamentoEmprestimo();
		pagamento.setId_pagamento(pagamento_antigo.getId_pagamento());

		String s_valor, descricao, observacao, identificador, data_pagamento, caminho_arquivo;
		int objeto;
		BigDecimal valor_total = BigDecimal.ZERO;

		identificador = entIdentificador.getText();
		observacao = entObservacao.getText();
		descricao = entDescricao.getText();
		data_pagamento = entDataPagamento.getText();

		objeto = cbObjeto.getSelectedIndex();

		pagamento.setObjeto(objeto);
		if (objeto == 0) {
			// moeda
		} else if (objeto == 1) {
			// produto

			String especie, unidade_medida = "", s_valor_unitario;
			double quantidade;
			BigDecimal valor_unitario = BigDecimal.ZERO;

			especie = entEspecie.getText();

			if (rdbtnKgs.isSelected()) {
				unidade_medida = "KG";
			} else if (rdbtnSacos.isSelected()) {
				unidade_medida = "Sacos";
			}

			quantidade = Double.parseDouble(entQuantidade.getText());

			pagamento.setEspecie(especie);
			pagamento.setUnidade_medida(unidade_medida);
			pagamento.setQuantidade(quantidade);

			try {
				s_valor_unitario = entValorUnitario.getText();
				s_valor_unitario = s_valor_unitario.replace("[^0-9,]", "");
				s_valor_unitario = s_valor_unitario.replace(",", ".");
				valor_unitario = new BigDecimal(s_valor_unitario);
				pagamento.setValor_unitario(valor_unitario);

			} catch (Exception e) {
				pagamento.setValor_unitario(BigDecimal.ZERO);
			}

		}

		try {
			s_valor = entValorTotal.getText();
			s_valor = s_valor.replace("[^0-9,]", "");
			s_valor = s_valor.replace(",", ".");
			valor_total = new BigDecimal(s_valor);
			pagamento.setValor(valor_total);

		} catch (Exception e) {
			pagamento.setValor(BigDecimal.ZERO);
		}

		pagamento.setDescricao(descricao);
		pagamento.setId_lancamento(lancamento_pai.getId_lancamento());

		pagamento.setIdentificador(identificador);
		pagamento.setObservacao(observacao);

		if (rdbtnFluxoDeCaixaSim.isSelected()) {
			pagamento.setFluxo_caixa(1);
		} else if (rdbtnFluxoDeCaixaNao.isSelected()) {
			pagamento.setFluxo_caixa(0);

		}

		if (condicao_pagamento != null) {
			pagamento.setId_condicao_pagamento(condicao_pagamento.getId_condicao_pagamento());
		} else {
			JOptionPane.showMessageDialog(isto, "Selecione a condição do pagamento");

			return null;
		}

		pagamento.setStatus_pagamento(cbStatusCondicaoPagamento.getSelectedIndex());

		//pagador
				if(rdbtnPagadorIB.isSelected()) {
					pagamento.setTipo_pagador(0);
					if (pagador_ib != null) {
						pagamento.setId_pagador(pagador_ib.getId_instituicao_bancaria());

					} else {
						JOptionPane.showMessageDialog(isto, "Selecione o pagador");

						return null;
					}
					
				}else if(rdbtnPagadorClienteforncedor.isSelected()) {
					pagamento.setTipo_pagador(1);
					if (pagador_cliente_fornecedor != null) {
						pagamento.setId_pagador(pagador_cliente_fornecedor.getId());

					} else {
						JOptionPane.showMessageDialog(isto, "Selecione o pagador");

						return null;
					}
				}
				
				//recebedor

				if(rdbtnRecebedorIB.isSelected()) {
					pagamento.setTipo_recebedor(0);
					if (recebedor_ib != null) {
						pagamento.setId_recebedor(recebedor_ib.getId_instituicao_bancaria());

					} else {
						JOptionPane.showMessageDialog(isto, "Selecione o pagador");

						return null;
					}
					
				}else if(rdbtnRecebedorCliente.isSelected()) {
					pagamento.setTipo_recebedor(1);
					if (recebedor_cliente_fornecedor != null) {
						pagamento.setId_recebedor(recebedor_cliente_fornecedor.getId());

					} else {
						JOptionPane.showMessageDialog(isto, "Selecione o pagador");

						return null;
					}
				}
				
				

		try {

			if (!isDateValid(data_pagamento)) {
				JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");

				return null;
			} else {
				pagamento.setData_pagamento(data_pagamento);
				return pagamento;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(isto, "Data do Pagamento Inválida");
			return null;
		}

	}

	public void setTelaPai(JDialog _tela_pai) {
		this.telaPai = _tela_pai;
	}

	public void setCondicaoPagamento(CondicaoPagamento _condicao) {
		this.condicao_pagamento = _condicao;

		cbCondicaoPagamento.removeAllItems();
		cbCondicaoPagamento.addItem(_condicao.getNome_condicao_pagamento());
	}

	public void setPagadorInstituicaoBancaria(InstituicaoBancaria _instituicao_bancaria) {
		pagador_ib = _instituicao_bancaria;

		cbPagador.removeAllItems();
		cbPagador.addItem(_instituicao_bancaria.getNome_instituicao_bancaria());
	}

	public void setPagadorClienteFornecedor(CadastroCliente _cliente_fornecedor) {
		pagador_cliente_fornecedor = _cliente_fornecedor;
		String nome_cliente = "";
		if (pagador_cliente_fornecedor.getTipo_pessoa() == 0) {
			nome_cliente = pagador_cliente_fornecedor.getNome_empresarial();
		} else {
			nome_cliente = pagador_cliente_fornecedor.getNome_fantaia();

		}
		cbPagador.removeAllItems();
		cbPagador.addItem(nome_cliente);

	}

	public void setRecebedorInstituicaoBancaria(InstituicaoBancaria _instituicao_bancaria) {
		recebedor_ib = _instituicao_bancaria;

		cbRecebedor.removeAllItems();
		cbRecebedor.addItem(_instituicao_bancaria.getNome_instituicao_bancaria());
	}

	public void setRecebedorClienteFornecedor(CadastroCliente _cliente_fornecedor) {
		recebedor_cliente_fornecedor = _cliente_fornecedor;
		String nome_cliente = "";
		if (recebedor_cliente_fornecedor.getTipo_pessoa() == 0) {
			nome_cliente = recebedor_cliente_fornecedor.getNome_empresarial();
		} else {
			nome_cliente = recebedor_cliente_fornecedor.getNome_fantaia();

		}
		cbRecebedor.removeAllItems();
		cbRecebedor.addItem(nome_cliente);

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public void setPainelProduto() {

		JPanel painelProduto = new JPanel();
		painelProduto.setBackground(new Color(0, 0, 0));
		painelProduto.setOpaque(false);
		painelProduto.setLayout(new MigLayout("", "[][grow][][grow]", "[][grow][][][]"));
		painelPrincipal.add(painelProduto, "cell 0 0,grow");

		JLabel lblEspecie = new JLabel("Espécie:");
		painelProduto.add(lblEspecie, "cell 0 0,alignx right");
		lblEspecie.setFont(new Font("Tahoma", Font.PLAIN, 16));

		entEspecie = new JTextFieldPersonalizado();
		painelProduto.add(entEspecie, "cell 1 0 3 1,growx");
		entEspecie.setForeground(Color.black);

		JLabel lblUnidadeMedida = new JLabel("Unidade Medida:");
		painelProduto.add(lblUnidadeMedida, "cell 0 1");
		lblUnidadeMedida.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 204));
		painelProduto.add(panel_1, "cell 1 1 3 1,alignx center,growy");
		panel_1.setLayout(new MigLayout("", "[][]", "[]"));

		rdbtnKgs = new JRadioButton("KG");
		rdbtnKgs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnKgs.setSelected(true);
				rdbtnSacos.setSelected(false);

			}
		});
		panel_1.add(rdbtnKgs, "cell 0 0");
		rdbtnKgs.setFont(new Font("SansSerif", Font.BOLD, 16));

		rdbtnSacos = new JRadioButton("Saco");
		rdbtnSacos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				rdbtnSacos.setSelected(true);
				rdbtnKgs.setSelected(false);

			}
		});
		panel_1.add(rdbtnSacos, "cell 1 0");
		rdbtnSacos.setFont(new Font("SansSerif", Font.BOLD, 16));

		JLabel lblQuantidade = new JLabel("Quantidade:");
		painelProduto.add(lblQuantidade, "cell 0 2,alignx right");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 16));

		entQuantidade = new JTextFieldPersonalizado();
		entQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent o) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Squantidade;
				Squantidade = entQuantidade.getText();

				if (!caracteres.contains(o.getKeyChar() + "")) {
					o.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Squantidade = entQuantidade.getText() + o.getKeyChar();
					Squantidade = Squantidade.replaceAll("[^0-9]+", "");
				}

			}

		});
		painelProduto.add(entQuantidade, "cell 1 2 3 1,growx");
		entQuantidade.setForeground(Color.black);

		JLabel lblValorUnitrio = new JLabel("Valor Unitário:");
		painelProduto.add(lblValorUnitrio, "cell 0 3,alignx right");
		lblValorUnitrio.setFont(new Font("Tahoma", Font.PLAIN, 16));

		entValorUnitario = new JTextFieldPersonalizado();
		entValorUnitario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent o) {
				String caracteres = ".0987654321\b";// lista de caracters que não devem ser aceitos
				String Svalor_unitario = null, quantidade;
				quantidade = entQuantidade.getText();

				if (!caracteres.contains(o.getKeyChar() + "")) {
					o.consume();// aciona esse propriedade para eliminar a ação do evento

					// quantidade = entQuantidade.getText();
				} else {
					Svalor_unitario = entValorUnitario.getText() + o.getKeyChar();
					Svalor_unitario = Svalor_unitario.replaceAll("[^0-9.]+", "");
				}

				double quant = Double.parseDouble(quantidade);
				BigDecimal valor_unitario = new BigDecimal(Svalor_unitario);

				// if(quantidade != null && !(quantidade.length() <= 0) &&
				// !quantidade.equals("") && Spreco != null && !(Spreco.length() <= 0) &&
				// !Spreco.equals(""))
				{
					try {

						BigDecimal valor_total = valor_unitario.multiply(new BigDecimal(quantidade));

						String valorTotal = valor_total.toPlainString();
						entValorTotal.setText(valorTotal);

					} catch (NumberFormatException n) {

					}
				}

			}

		});
		painelProduto.add(entValorUnitario, "cell 1 3 3 1,growx");
		entValorUnitario.setForeground(Color.black);

		painelPaiProduto.add(painelProduto);
	}

	public void removerPainel() {
		painelPaiProduto.removeAll();
		painelPaiProduto.repaint();
		painelPaiProduto.updateUI();

	}

	public void atualizarValorLancamentoEmprestimo(Lancamento lancamento_pai) {

		GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_pag = new GerenciarBancoFinanceiroPagamentoEmprestimo();
		BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

		for (FinanceiroPagamentoEmprestimo pagamento : gerenciar_pag
				.getFinanceiroPagamentosPorLancamento(lancamento_pai.getId_lancamento())) {

			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());

		}

		BigDecimal valor_pagamentos_retroativos = valor_total_pagamentos;
		GerenciarBancoParcelasEmprestimo gerenciar = new GerenciarBancoParcelasEmprestimo();

		BigDecimal valor_total_parcelas = BigDecimal.ZERO;

		for (ParcelaEmprestimo parcela : gerenciar.getParcelasPorLancamento(lancamento_pai.getId_lancamento())) {
			BigDecimal valor_parcela = parcela.getValor();
			// é uma receita
			if (valor_pagamentos_retroativos.compareTo(valor_parcela) >= 0) {
				gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());

			} else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
				gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

			}

			valor_pagamentos_retroativos = valor_pagamentos_retroativos.subtract(valor_parcela);
			valor_total_parcelas = valor_total_parcelas.add(valor_parcela);

		}
		new GerenciarBancoLancamento().atualizarValorLancamento(valor_total_parcelas.toString(),
				lancamento_pai.getId_lancamento());

		if (valor_total_pagamentos.compareTo(valor_total_parcelas) >= 0) {
			// pagamento concluido
			// atualizar status do lancmento

			new GerenciarBancoLancamento().atualizarStatusLancamento(3, lancamento_pai.getId_lancamento());

		} else if (valor_total_pagamentos.compareTo(valor_total_parcelas) < 0) {
			// pagamento incompleto

			new GerenciarBancoLancamento().atualizarStatusLancamento(2, lancamento_pai.getId_lancamento());

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

}
