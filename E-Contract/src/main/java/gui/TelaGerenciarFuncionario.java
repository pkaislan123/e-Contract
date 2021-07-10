
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTree;
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

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
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
import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioBancoHoras;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;
import main.java.cadastros.CadastroFuncionarioSalario;
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
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.Contato;
import main.java.cadastros.DadosCarregamento;
import main.java.cadastros.DadosContratos;
import main.java.cadastros.CalculoCompleto;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FuncionarioContaAssociada;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.RegistroPonto;
import main.java.cadastros.RegistroPontoDiario;
import main.java.cadastros.RegistroPontoDiarioCompleto;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.classesExtras.Endereco;
import main.java.classesExtras.RenderizadorContato;
import main.java.conexaoBanco.GerenciarBancoAditivos;
import main.java.conexaoBanco.GerenciarBancoCartaoPonto;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFuncionarioBancoHoras;
import main.java.conexaoBanco.GerenciarBancoFuncionarioSalarios;
import main.java.conexaoBanco.GerenciarBancoFuncionarios;
import main.java.conexaoBanco.GerenciarBancoFuncionariosContratoTrabalho;
import main.java.conexaoBanco.GerenciarBancoFuncionariosCalculos;
import main.java.conexaoBanco.GerenciarBancoFuncionariosEventos;
import main.java.conexaoBanco.GerenciarBancoNotasFiscais;
import main.java.conexaoBanco.GerenciarBancoPadrao;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoPontuacao;
import main.java.conexaoBanco.GerenciarBancoProdutos;
import main.java.conexaoBanco.GerenciarBancoRegistroPonto;
import main.java.conexaoBanco.GerenciarBancoRomaneios;
import main.java.conexaoBanco.GerenciarBancoRotina;
import main.java.conexaoBanco.GerenciarBancoSafras;
import main.java.conexaoBanco.GerenciarBancoTarefaGeral;
import main.java.conexaoBanco.GerenciarBancoTransferencias;
import main.java.conexaoBanco.GerenciarBancoTransferenciasCarga;
import main.java.conexoes.TesteConexao;
import main.java.graficos.GraficoLinha;
import main.java.graficos.JPanelGrafico;
import main.java.graficos.JPanelGraficoCarregamento;
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
import main.java.cadastros.CartaoPontoCompleto;
import main.java.cadastros.CondicaoPagamento;
import main.java.outros.DadosGlobais;
import main.java.cadastros.CadastroFuncionario;
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
import javax.swing.JTabbedPane;
import javax.swing.JSeparator;

public class TelaGerenciarFuncionario extends JFrame {

	private final JPanel painelPrincipal = new JPanel();
	private TelaGerenciarFuncionario isto;
	private KGradientPanel menu_lateral;
	private ArrayList<CadastroTarefaGeral> lista_tarefas = new ArrayList<>();
	private TarefaTableModel modelo_tarefa = new TarefaTableModel();
	private JTable table_tarefas, tabela_lancamentos;
	private JTable tabela_banco_horas;
	private JPanel panel_docs;
	private JTree arvore_documentos;
	private CadastroFuncionarioEventoTableModel modeloEventos = new CadastroFuncionarioEventoTableModel();
	private JTable tabela_rp_diario, tabela_contas_associadas, tabela_cartoes;
	private JComboBox cbMes;
	private JornadaTrabalhoTableModel modeloJornadaTrabalho = new JornadaTrabalhoTableModel();
	private ArrayList<CadastroFuncionarioRotinaTrabalho> listaJornadaTrabalho = new ArrayList<>();
	private LancamentoTableModel modelLancamentos = new LancamentoTableModel();
	DefaultMutableTreeNode no_comprovantes;
	DefaultMutableTreeNode no_docs_pessoais;
	DefaultMutableTreeNode no_outros;
	private DefaultMutableTreeNode no_selecionado;
	private CadastroFuncionario funcionario_local;
	private JComboBox cBTipoDocumento;
	private TelaTodasNotasFiscais telaTodasNotasFiscais;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private CartaoTableModel modelo_cartoes = new CartaoTableModel();
	private ArrayList<CartaoPontoCompleto> lista_cartoes_completos = new ArrayList<>();
	private JTextArea entDescricaoDocumento;
	private ContratosTrabalhoTableModel modelo_contratos = new ContratosTrabalhoTableModel();
	private TelaRomaneios telaRomaneio;
	private JLabel lblTipoPessoa, lblTipoIdentificacao, lblCpf, lblStatus, lblEndereco;
	private JLabel lblTotalContratosConcluidosComprador, lblTotalContratosComprador, lblTotalContratosAbertosComprador;
	private JLabel lblNome;
	private JLabel lblTotalContratosConcluidosVendedor, lblTotalContratosVendedor, lblTotalContratosAbertosVendedor,
			lblNivel;
	private JLabel lblDataAdmissao, lblSalario, lblCargo, lblFuncao, lblTipoContratoAtivo;
	private RegistroPontoTableModel modeloRp = new RegistroPontoTableModel();
	private JTable tabela_contratos;
	private ArrayList<CadastroFuncionarioAdmissao> lista_contratos = new ArrayList<>();
	private JTable tabela_registros_ponto;
	private BancoHorasTableModel modeloBancoHoras = new BancoHorasTableModel();
	private JTable table_jornada_trabalho;
	private RegistroPontoDiarioTableModel modeloRpDiario = new RegistroPontoDiarioTableModel();
	private JTextField entAno;
	private JTable tabela_eventos;
	Locale ptBr = new Locale("pt", "BR");
	private JTable tabela_salarios;
	private SalarioTableModel modelSalarios = new SalarioTableModel();
	private JComboBox cBMesLancamentos;
	private JTextField entAnoLancamentos;
	private ClienteTableModel modelClientes = new ClienteTableModel();

	public TelaGerenciarFuncionario(CadastroFuncionario funcionario, Window janela_pai) {
		// setModal(true);
		CellRenderRPDiario renderer = new CellRenderRPDiario();

		getDadosGlobais();

		isto = this;
		funcionario_local = funcionario;

		setResizable(true);
		setTitle("E-Contract - Gerenciar Colaborador");

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1305, 724);
		painelPrincipal.setForeground(Color.BLACK);
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);
		painelPrincipal.setLayout(null);

		panel_docs = new JPanel();
		panel_docs.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_docs.setBackground(Color.WHITE);

		JPanel painelDocumentos = new JPanel();
		painelDocumentos.setBackground(new Color(0, 102, 153));
		painelDocumentos.setVisible(false);

		JPanel painelFinancas = new JPanel();
		painelFinancas.setBackground(new Color(0, 102, 153));
		painelFinancas.setBounds(197, 150, 1091, 535);
		painelPrincipal.add(painelFinancas);
		painelFinancas.setLayout(new MigLayout("", "[grow][]", "[grow][]"));

		JTabbedPane abasFinancas = new JTabbedPane(JTabbedPane.TOP);
		abasFinancas.setBackground(new Color(0, 102, 153));
		abasFinancas.setBounds(0, 0, 1, 1);
		painelFinancas.add(abasFinancas, "cell 0 0 2 2,grow");

		JPanel painelSalarios = new JPanel();
		painelSalarios.setBackground(new Color(0, 102, 153));
		abasFinancas.addTab("Sálarios", null, painelSalarios, null);
		painelSalarios.setLayout(new MigLayout("", "[grow]", "[][grow][]"));

		tabela_salarios = new JTable(modelSalarios);
		tabela_salarios.setRowHeight(30);

		JScrollPane scrollSalarios = new JScrollPane(tabela_salarios);
		painelSalarios.add(scrollSalarios, "cell 0 0 1 2,grow");
		scrollSalarios.getViewport().setBackground(Color.white);
		JButton btnNovoPagamento = new JButton("Novo Pagamento");
		painelSalarios.add(btnNovoPagamento, "cell 0 2,alignx right");
		btnNovoPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();

				TelaFuncionariosCadastroSalario tela = new TelaFuncionariosCadastroSalario(funcionario_local,
						cbMes.getSelectedIndex(), entAno.getText(), isto);
				tela.setVisible(true);

			}
		});
		btnNovoPagamento.setBackground(new Color(0, 51, 0));
		btnNovoPagamento.setForeground(Color.WHITE);
		btnNovoPagamento.setFont(new Font("SansSerif", Font.BOLD, 16));

		JPanel panel_6 = new JPanel();
		abasFinancas.addTab("Lançamentos", null, panel_6, null);
		panel_6.setLayout(new MigLayout("", "[grow]", "[][][grow][]"));

		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.WHITE);
		panel_6.add(panel_12, "cell 0 0,grow");
		panel_12.setLayout(new MigLayout("", "[34px][128px][32px][142px][95px]", "[1px][33px]"));

		JLabel lblNewLabel_1_3 = new JLabel("");
		lblNewLabel_1_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_12.add(lblNewLabel_1_3, "cell 1 0,alignx left,aligny top");

		JLabel lblNewLabel_1 = new JLabel("Mês:");
		panel_12.add(lblNewLabel_1, "cell 0 1,alignx left,aligny center");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		cBMesLancamentos = new JComboBox();
		cBMesLancamentos.addItem("TODOS");
		cBMesLancamentos.addItem("JANEIRO");
		cBMesLancamentos.addItem("FEVEREIRO");
		cBMesLancamentos.addItem("MARÇO");
		cBMesLancamentos.addItem("ABRIL");
		cBMesLancamentos.addItem("MAIO");
		cBMesLancamentos.addItem("JUNHO");
		cBMesLancamentos.addItem("JULHO");
		cBMesLancamentos.addItem("AGOSTO");
		cBMesLancamentos.addItem("SETEMBRO");
		cBMesLancamentos.addItem("OUTUBRO");
		cBMesLancamentos.addItem("NOVEMBRO");
		cBMesLancamentos.addItem("DEZEMBRO");

		int mes = new GetData().getMes();
		cBMesLancamentos.setSelectedIndex(mes - 1);

		cBMesLancamentos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_12.add(cBMesLancamentos, "cell 1 1,alignx left,aligny center");

		JLabel lblNewLabel_1_1 = new JLabel("Ano:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_12.add(lblNewLabel_1_1, "cell 2 1,alignx left,aligny center");

		entAnoLancamentos = new JTextField();
		entAnoLancamentos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_12.add(entAnoLancamentos, "cell 3 1,alignx left,aligny top");
		entAnoLancamentos.setColumns(10);
		entAnoLancamentos.setText(Integer.toString(new GetData().getAnoAtual()));

		JButton btnNewButton = new JButton("Atualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_contas_associadas();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 102));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_12.add(btnNewButton, "cell 4 1,alignx left,aligny top");

		tabela_lancamentos = new JTable(modelLancamentos);
		tabela_lancamentos.setRowHeight(30);
		tabela_lancamentos.setDefaultRenderer(Object.class, new LancamentosRender());

		JScrollPane scrollLancamentos = new JScrollPane(tabela_lancamentos);
		panel_6.add(scrollLancamentos, "cell 0 1 1 2,grow");
		
		JButton btnNewButton_1_2 = new JButton("Visão Completa");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int mes = cBMesLancamentos.getSelectedIndex();

				int ano = 0;
				try {
					ano = Integer.parseInt(entAnoLancamentos.getText());
				} catch (Exception t) {
					JOptionPane.showMessageDialog(isto, "Ano Inválido!");
					return;
				}
				
				FuncionarioContaAssociada cliente = (FuncionarioContaAssociada) modelClientes.getValue(0);

				if(cliente.getCliente() != null) {
				
					String nome=  "";
					if(cliente.getCliente().getTipo_pessoa() == 0)
						nome = cliente.getCliente().getNome_empresarial();
					else
						nome = cliente.getCliente().getNome_fantaia();
					
				TelaFinanceiroLancamento tela = new TelaFinanceiroLancamento(3, -1,  isto);
				tela.setPesquisaPersonalizada(nome,mes, ano);
				tela.filtrarPersonalizado();
				tela.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(isto, "Nenhum cliente associado a este colaborador");
				}
			}
		});
		btnNewButton_1_2.setBackground(new Color(0, 0, 102));
		btnNewButton_1_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton_1_2.setForeground(Color.WHITE);
		panel_6.add(btnNewButton_1_2, "flowx,cell 0 3");
		
		JButton btnNewButton_1 = new JButton("Gerenciar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Lancamento lancamento_gerenciar = getLancamentoSelecionado();
				TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar,
						isto);
				tela.setVisible(true);
			}
		});
		btnNewButton_1.setBackground(new Color(255, 153, 0));
		btnNewButton_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton_1.setForeground(Color.WHITE);
		panel_6.add(btnNewButton_1, "cell 0 3");
		
		JButton btnNewButton_1_1_1 = new JButton("Excluir");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir o Lançamento?", "Excluir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {


						Lancamento lancamento_excluir = getLancamentoSelecionado();

						ArrayList<FinanceiroPagamento> lista_pagamentos = new GerenciarBancoFinanceiroPagamento()
								.getFinanceiroPagamentosPorLancamento(lancamento_excluir.getId_lancamento());

						if (lista_pagamentos.size() > 0) {
							JOptionPane.showMessageDialog(isto,
									"O lançamento selecionado possui pagamentos, exclua os primeiro");

						} else {
							boolean prosseguir = true;
							// excluir primeiro as parcelas
							GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
							ArrayList<Parcela> lista_parcelas = gerenciar
									.getParcelasPorLancamento(lancamento_excluir.getId_lancamento());
							for (Parcela parcela : lista_parcelas) {
								boolean remover_parcela = gerenciar.removerParcela(parcela.getId_parcela());
								if (!remover_parcela) {
									prosseguir = false;
									JOptionPane.showMessageDialog(isto,
											"Erro ao excluir parcela do lançamento\nBanco de dados Corrompido\nConsulte o administrador");

									break;
								}
							}
							if (prosseguir) {

								boolean exclusao = new GerenciarBancoLancamento()
										.removerLancamento(lancamento_excluir.getId_lancamento());
								if (exclusao) {
									JOptionPane.showMessageDialog(isto, "Cadastro Excluído");
									pesquisar_contas_associadas();
								} else {
									JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

								}
							}
						}


				}
			}
		});
		btnNewButton_1_1_1.setBackground(new Color(102, 0, 0));
		btnNewButton_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton_1_1_1.setForeground(Color.WHITE);
		panel_6.add(btnNewButton_1_1_1, "cell 0 3");
		
		JButton btnNewButton_1_1 = new JButton("Novo Lançamento");
		btnNewButton_1_1.setBackground(new Color(0, 51, 51));
		btnNewButton_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton_1_1.setForeground(Color.WHITE);
		panel_6.add(btnNewButton_1_1, "cell 0 3");

		JPanel panel_13 = new JPanel();
		abasFinancas.addTab("Contas Associadas", null, panel_13, null);
		panel_13.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		tabela_contas_associadas = new JTable(modelClientes);
		tabela_contas_associadas.setRowHeight(30);

		JScrollPane scrollPaneContasAssociadas = new JScrollPane(tabela_contas_associadas);
		panel_13.add(scrollPaneContasAssociadas, "cell 0 0,grow");

		JButton btnExcluirAssociacaoConta = new JButton("Excluir");
		btnExcluirAssociacaoConta.setBackground(new Color(102, 0, 0));
		btnExcluirAssociacaoConta.setForeground(Color.WHITE);
		btnExcluirAssociacaoConta.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_13.add(btnExcluirAssociacaoConta, "flowx,cell 0 1,alignx right");

		JButton btnAdicionarNovaAssociacao = new JButton("Adicionar");
		btnAdicionarNovaAssociacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionariosAssosiacaoFuncionarioCliente tela = new TelaFuncionariosAssosiacaoFuncionarioCliente(
						funcionario, isto);
				tela.setVisible(true);
			}
		});
		btnAdicionarNovaAssociacao.setBackground(new Color(0, 0, 102));
		btnAdicionarNovaAssociacao.setForeground(Color.WHITE);
		btnAdicionarNovaAssociacao.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_13.add(btnAdicionarNovaAssociacao, "cell 0 1,alignx right");
		scrollPaneContasAssociadas.getViewport().setBackground(Color.white);

		scrollLancamentos.getViewport().setBackground(Color.white);
		scrollSalarios.getViewport().setBackground(Color.white);

		painelDocumentos.setEnabled(false);
		painelDocumentos.setBounds(198, 153, 1091, 468);
		painelPrincipal.add(painelDocumentos);
		painelDocumentos.setLayout(new MigLayout("", "[grow][551px]", "[grow]"));

		painelDocumentos.add(panel_docs, "cell 0 0,grow");

		JPanel painelInserirDocumento = new JPanel();
		painelInserirDocumento.setBackground(new Color(0, 102, 102));
		painelInserirDocumento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelDocumentos.add(painelInserirDocumento, "cell 1 0,growx,aligny center");
		painelInserirDocumento
				.setLayout(new MigLayout("", "[46px][10px][grow][10px][]", "[27px][22px][85px][39px][23px]"));

		JLabel lblNewLabel_15 = new JLabel("Nome:");
		lblNewLabel_15.setForeground(Color.WHITE);
		lblNewLabel_15.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelInserirDocumento.add(lblNewLabel_15, "cell 0 0 2 1,alignx right,aligny center");

		JLabel lblNewLabel_16 = new JLabel("Descrição:");
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelInserirDocumento.add(lblNewLabel_16, "cell 0 2 2 1,alignx right,aligny center");

		entDescricaoDocumento = new JTextArea();
		entDescricaoDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(entDescricaoDocumento, "cell 2 2 3 1,grow");

		JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		lblNewLabel_17.setForeground(Color.WHITE);
		lblNewLabel_17.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelInserirDocumento.add(lblNewLabel_17, "cell 0 3 2 1,alignx right,aligny center");

		entCaminhoDocumento = new JTextField();
		entCaminhoDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCaminhoDocumento.setColumns(10);
		painelInserirDocumento.add(entCaminhoDocumento, "cell 2 3 2 1,grow");

		entNomeDocumento = new JTextField();
		entNomeDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNomeDocumento.setColumns(10);
		painelInserirDocumento.add(entNomeDocumento, "cell 2 0 3 1,grow");

		JButton btnSelecionarDocumento = new JButton("Selecionar");
		btnSelecionarDocumento.setBackground(new Color(0, 0, 102));
		btnSelecionarDocumento.setForeground(Color.WHITE);
		btnSelecionarDocumento.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSelecionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selecionarDocumento();
			}
		});
		painelInserirDocumento.add(btnSelecionarDocumento, "cell 4 3,growx,aligny center");

		JButton btnAdicionarDocumento = new JButton("Adicionar");
		btnAdicionarDocumento.setBackground(new Color(0, 51, 0));
		btnAdicionarDocumento.setForeground(Color.WHITE);
		btnAdicionarDocumento.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnAdicionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarNovoDocumento();
			}
		});
		painelInserirDocumento.add(btnAdicionarDocumento, "cell 4 4,grow");

		JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		lblNewLabel_16_1.setForeground(Color.WHITE);
		lblNewLabel_16_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelInserirDocumento.add(lblNewLabel_16_1, "cell 0 1 2 1,alignx right,aligny center");

		cBTipoDocumento = new JComboBox();
		cBTipoDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(cBTipoDocumento, "cell 2 1 3 1,grow");
		cBTipoDocumento.addItem("Documento Pessoal");
		cBTipoDocumento.addItem("Comprovantes");
		cBTipoDocumento.addItem("Outros");

		menu_lateral = new KGradientPanel();
		menu_lateral.kStartColor = new Color(0, 255, 204);
		menu_lateral.kEndColor = Color.BLUE;
		menu_lateral.setBounds(0, 0, 200, 685);
		painelPrincipal.add(menu_lateral);
		menu_lateral.setLayout(null);

		JPanelTransparent panel = new JPanelTransparent();
		panel.setLayout(null);
		panel.setBounds(10, 167, 181, 181);
		menu_lateral.add(panel);

		JLabel btnDocumentos = new JLabel("Documentos");
		btnDocumentos.setForeground(Color.WHITE);
		btnDocumentos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDocumentos.setBackground(new Color(0, 0, 0, 100));
		btnDocumentos.setBounds(10, 53, 146, 20);
		panel.add(btnDocumentos);

		JLabel btnDadosIniciais = new JLabel("Dados Inicias");
		btnDadosIniciais.setOpaque(true);
		btnDadosIniciais.setForeground(Color.WHITE);
		btnDadosIniciais.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));
		btnDadosIniciais.setBounds(10, 22, 161, 20);
		panel.add(btnDadosIniciais);

		JLabel btnContratos = new JLabel("Contratos");
		btnContratos.setForeground(Color.WHITE);
		btnContratos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnContratos.setBackground(new Color(0, 0, 0, 100));
		btnContratos.setBounds(10, 84, 146, 20);
		panel.add(btnContratos);

		KGradientPanel panelTopo = new KGradientPanel();
		panelTopo.kStartColor = new Color(51, 0, 51);
		panelTopo.kEndColor = new Color(0, 51, 51);
		panelTopo.setBounds(172, 65, 1117, 90);
		painelPrincipal.add(panelTopo);
		panelTopo.setLayout(null);

		lblNome = new JLabel("New label");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("SansSerif", Font.BOLD, 24));
		lblNome.setBounds(54, 33, 1007, 37);
		panelTopo.add(lblNome);

		JLabel btnDeposito = new JLabel("Registro de Ponto");
		btnDeposito.setForeground(Color.WHITE);
		btnDeposito.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDeposito.setBackground(new Color(0, 0, 0, 100));
		btnDeposito.setBounds(10, 116, 146, 20);
		panel.add(btnDeposito);

		JLabel btnFinancas = new JLabel("Finanças");
		btnFinancas.setForeground(Color.WHITE);
		btnFinancas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnFinancas.setBackground(new Color(0, 0, 0, 100));
		btnFinancas.setBounds(10, 155, 146, 20);
		panel.add(btnFinancas);

		JPanel painelRegistrodePonto = new JPanel();
		painelRegistrodePonto.setBackground(new Color(0, 102, 153));
		painelRegistrodePonto.setVisible(false);

		painelRegistrodePonto.setEnabled(false);
		painelRegistrodePonto.setBounds(198, 153, 1091, 468);
		painelPrincipal.add(painelRegistrodePonto);
		painelRegistrodePonto.setLayout(new MigLayout("", "[grow][grow]", "[grow][][grow]"));

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		painelRegistrodePonto.add(tabbedPane_1, "cell 0 0 2 1,grow");

		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		panel_4.setBackground(new Color(0, 102, 153));
		tabbedPane_1.addTab("Registro de Ponto", null, panel_4, null);
		panel_4.setLayout(new MigLayout("", "[grow][grow]", "[grow][]"));

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBackground(new Color(0, 102, 153));
		tabbedPane_2.setOpaque(true);
		panel_4.add(tabbedPane_2, "cell 0 0 2 1,grow");

		JPanel panel_7 = new JPanel();
		panel_7.setOpaque(false);
		tabbedPane_2.addTab("Por Dia", null, panel_7, null);
		panel_7.setLayout(new MigLayout("", "[grow]", "[grow][grow][]"));

		tabela_rp_diario = new JTable(modeloRpDiario);
		tabela_rp_diario.setRowHeight(30);
		tabela_rp_diario.setDefaultRenderer(Object.class, renderer);

		JScrollPane scrollPanePorDia = new JScrollPane(tabela_rp_diario);
		scrollPanePorDia.getViewport().setBackground(Color.white);

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		panel_7.add(panel_9, "cell 0 0,grow");
		panel_9.setLayout(new MigLayout("", "[][][][][]", "[][]"));

		JLabel lblNewLabel = new JLabel("Mês:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_9.add(lblNewLabel, "cell 0 0,alignx trailing");

		cbMes = new JComboBox();
		cbMes.addItem("JANEIRO");
		cbMes.addItem("FEVEREIRO");
		cbMes.addItem("MARÇO");
		cbMes.addItem("ABRIL");
		cbMes.addItem("MAIO");
		cbMes.addItem("JUNHO");
		cbMes.addItem("JULHO");
		cbMes.addItem("AGOSTO");
		cbMes.addItem("SETEMBRO");
		cbMes.addItem("OUTUBRO");
		cbMes.addItem("NOVEMBRO");
		cbMes.addItem("DEZEMBRO");

		panel_9.add(cbMes, "cell 1 0,growx");

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_9.add(lblAno, "cell 2 0,alignx trailing");

		entAno = new JTextField();
		panel_9.add(entAno, "cell 3 0,growx");
		entAno.setColumns(10);
		entAno.setText(Integer.toString(new GetData().getAnoAtual()));

		JButton btnAtualizar = new JButton("Pesquisar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				pesquisar_rp_diario();
			}
		});
		btnAtualizar.setBackground(new Color(0, 51, 0));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_9.add(btnAtualizar, "cell 4 0,aligny bottom");
		panel_7.add(scrollPanePorDia, "cell 0 1,grow");

		JPanel panel_8 = new JPanel();
		panel_8.setOpaque(false);
		tabbedPane_2.addTab("Por Ponto", null, panel_8, null);
		panel_8.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		tabela_registros_ponto = new JTable(modeloRp);
		tabela_registros_ponto.setRowHeight(30);

		JScrollPane scrollPaneRegistrosPonto = new JScrollPane(tabela_registros_ponto);
		panel_8.add(scrollPaneRegistrosPonto, "cell 0 0,grow");

		JButton btnInserirPonto = new JButton("Inserir Ponto");
		btnInserirPonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFuncionariosCadastroRp tela = new TelaFuncionariosCadastroRp(funcionario_local, isto);
				tela.setVisible(true);

			}
		});

		JButton btnExcluirPonto = new JButton("Excluir Ponto");
		btnExcluirPonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir o Ponto?", "Excluir Ponto",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					GerenciarBancoRegistroPonto gerenciar_rp = new GerenciarBancoRegistroPonto();
					RegistroPonto rp = getRpSelecionado();
					boolean remover = gerenciar_rp.removerRegistroPonto(rp.getId_registro_ponto());
					if (remover) {

						JOptionPane.showMessageDialog(isto, "Registro de Ponto  Excluído!");
						// Criar tarefa
						GerenciarBancoTarefaGeral gerenciar_tarefa = new GerenciarBancoTarefaGeral();
						CadastroTarefaGeral tarefa = new CadastroTarefaGeral();

						tarefa.setNome_tarefa("Exclusão Manual de Registro de Ponto");
						tarefa.setDescricao_tarefa(
								"O Registro de Ponto id: " + rp.getId_registro_ponto() + " Foi excluido manualmente");
						tarefa.setMensagem(
								"O Registro de Ponto id: " + rp.getId_registro_ponto() + " Foi excluido manualmente");
						tarefa.setCriador(login);
						tarefa.setExecutor(login);
						tarefa.setStatus_tarefa(1);
						tarefa.setPrioridade(1);
						tarefa.setTipo(5);
						tarefa.setId_funcionario_pai(funcionario_local.getId_funcionario());

						GetData data = new GetData();
						tarefa.setHora(data.getHora());
						tarefa.setData(data.getData());
						tarefa.setHora_agendada(data.getHora());
						tarefa.setData_agendada(data.getData());

						boolean inseriu_tarefa = gerenciar_tarefa.inserirTarefaGeral(tarefa);
						if (inseriu_tarefa) {

						} else {
							JOptionPane.showMessageDialog(isto, "Tarefa Não Inserida, Consulte o administrador");

						}

						pesquisar_rp_diario();
						pesquisar_registros_ponto();
						pesquisar_tarefas();
					} else {
						JOptionPane.showMessageDialog(isto,
								"Erro ao remover Registro de Ponto\nConsulte o Administrador");
					}

				} else {

				}

			}
		});
		btnExcluirPonto.setForeground(Color.WHITE);
		btnExcluirPonto.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExcluirPonto.setBackground(new Color(153, 0, 0));
		panel_8.add(btnExcluirPonto, "flowx,cell 0 1,alignx right");
		btnInserirPonto.setForeground(Color.WHITE);
		btnInserirPonto.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnInserirPonto.setBackground(new Color(0, 0, 102));
		panel_8.add(btnInserirPonto, "cell 0 1,alignx right");
		scrollPaneRegistrosPonto.getViewport().setBackground(Color.white);

		scrollPaneRegistrosPonto.getViewport().setBackground(Color.white);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(0, 102, 153));
		tabbedPane_1.addTab("Cartões de Ponto", null, panel_5, null);
		panel_5.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		tabela_cartoes = new JTable(modelo_cartoes);
		tabela_cartoes.setRowHeight(30);

		JScrollPane scrollPaneCartoes = new JScrollPane(tabela_cartoes);
		panel_5.add(scrollPaneCartoes, "cell 0 0,grow");
		scrollPaneCartoes.getViewport().setBackground(Color.white);

		JButton btnExcluirAssociacao = new JButton("Excluir Associação");
		btnExcluirAssociacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir a associação?", "Excluir Associação",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					GerenciarBancoCartaoPonto gerenciar_evts = new GerenciarBancoCartaoPonto();
					boolean remover = gerenciar_evts.removerRelacaoFuncionarioCartaoPonto(
							funcionario_local.getId_funcionario(), getAssociacaoSelecionada());
					if (remover) {

						pesquisar_cartoes();
					} else {
						JOptionPane.showMessageDialog(isto, "Associação Não Excluída!\nConsulte o Administrador");

					}

				}

			}
		});
		btnExcluirAssociacao.setForeground(Color.WHITE);
		btnExcluirAssociacao.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnExcluirAssociacao.setBackground(new Color(153, 0, 0));
		panel_5.add(btnExcluirAssociacao, "flowx,cell 0 1,alignx right");

		JButton btnNovaAssociacao = new JButton("Nova Associação");
		btnNovaAssociacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaAplicarAssociacaoFuncionarioCartao tela = new TelaAplicarAssociacaoFuncionarioCartao(
						funcionario_local, isto);
				tela.setVisible(true);

			}
		});
		btnNovaAssociacao.setForeground(Color.WHITE);
		btnNovaAssociacao.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNovaAssociacao.setBackground(new Color(0, 51, 0));
		panel_5.add(btnNovaAssociacao, "cell 0 1,alignx right");
		scrollPaneCartoes.getViewport().setBackground(Color.white);

		cbMes.setSelectedIndex((new GetData().getMes()) - 1);

		JPanel panel_11 = new JPanel();
		tabbedPane_2.addTab("Lista Tarefas", null, panel_11, null);
		panel_11.setLayout(new MigLayout("", "[grow]", "[grow]"));

		table_tarefas = new JTable(modelo_tarefa);
		table_tarefas.setRowHeight(30);

		JScrollPane scrollPaneTarefas = new JScrollPane(table_tarefas);
		scrollPaneTarefas.getViewport().setBackground(Color.white);
		panel_11.add(scrollPaneTarefas, "cell 0 0,grow");

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane_1.addTab("Banco de Horas", null, panel_2, null);
		panel_2.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		tabela_banco_horas = new JTable(modeloBancoHoras);
		tabela_banco_horas.setRowHeight(30);
		tabela_banco_horas.setDefaultRenderer(Object.class, new CellRenderBancoHoras());

		JScrollPane scrollBancoHoras = new JScrollPane(tabela_banco_horas);
		panel_2.add(scrollBancoHoras, "cell 0 0,grow");

		JButton btnRemoverBancoHoras = new JButton("Remover");
		btnRemoverBancoHoras.setBackground(new Color(255, 0, 0));
		btnRemoverBancoHoras.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnRemoverBancoHoras.setForeground(Color.WHITE);
		panel_2.add(btnRemoverBancoHoras, "flowx,cell 0 1,alignx right");

		JButton btnEditar_1 = new JButton("Editar");
		btnEditar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFuncionarioCadastroBancoHoras tela = new TelaFuncionarioCadastroBancoHoras(1, funcionario_local,
						getBancoHorasSelecionado(), isto);
				tela.setVisible(true);

			}
		});
		btnEditar_1.setBackground(new Color(0, 0, 153));
		btnEditar_1.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditar_1.setForeground(Color.WHITE);
		panel_2.add(btnEditar_1, "cell 0 1,alignx right");

		JButton btnAdicionarBancoHoras = new JButton("Adicionar");
		btnAdicionarBancoHoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionarioCadastroBancoHoras tela = new TelaFuncionarioCadastroBancoHoras(0, funcionario_local,
						null, isto);
				tela.setVisible(true);

			}
		});
		btnAdicionarBancoHoras.setBackground(new Color(0, 51, 0));
		btnAdicionarBancoHoras.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAdicionarBancoHoras.setForeground(Color.WHITE);
		panel_2.add(btnAdicionarBancoHoras, "cell 0 1,alignx right");
		scrollBancoHoras.getViewport().setBackground(Color.white);

		JButton btnNewButton_4 = new JButton("Demonstrativo de Ponto");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaFuncionarioDemonstrativoDePonto tela = new TelaFuncionarioDemonstrativoDePonto(isto);
				tela.setFuncionario(funcionario_local, cbMes.getSelectedIndex(), entAno.getText());
				tela.setVisible(true);

			}
		});
		btnNewButton_4.setBackground(new Color(0, 51, 51));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelRegistrodePonto.add(btnNewButton_4, "cell 0 1");

		JPanel painelContratos = new JPanel();
		painelContratos.setVisible(false);

		painelContratos.setBackground(Color.WHITE);
		painelContratos.setForeground(Color.WHITE);
		painelContratos.setBounds(198, 154, 1091, 467);
		painelPrincipal.add(painelContratos);
		painelContratos.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		painelContratos.add(tabbedPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 102, 153));
		tabbedPane.addTab("Contratos de Trabalho", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[grow][grow][][]", "[grow][grow][]"));

		tabela_contratos = new JTable(modelo_contratos);

		tabela_contratos.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_contratos);
		scrollPane.getViewport().setBackground(Color.white);
		panel_1.add(scrollPane, "cell 0 0 4 1,grow");

		JButton btnNewButton_2 = new JButton("Adicionar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// verifica se existe contratos ativos para este funcionario

				int num_contratos = new GerenciarBancoFuncionariosContratoTrabalho()
						.getNumContratosAtivos(funcionario_local.getId_funcionario());

				if (num_contratos <= 0) {
					TelaFuncionariosCadastroContratoTrabalho tela = new TelaFuncionariosCadastroContratoTrabalho(0,
							funcionario_local, null, isto);
					tela.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(isto,
							"Já Existe um Contrato Ativo para este Colaborador\nNão é possível ter mais de um contrato ativo por Colaborador");
				}

			}
		});
		btnNewButton_2.setBackground(new Color(0, 51, 0));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_1.add(btnNewButton_2, "cell 3 2");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 102, 153));
		tabbedPane.addTab("Jornada de Trabalho", null, panel_3, null);
		panel_3.setLayout(new MigLayout("", "[grow]", "[grow][]"));

		table_jornada_trabalho = new JTable(modeloJornadaTrabalho);
		table_jornada_trabalho.setRowHeight(30);

		JScrollPane scrollPane_1 = new JScrollPane(table_jornada_trabalho);
		scrollPane_1.getViewport().setBackground(Color.white);
		panel_3.add(scrollPane_1, "cell 0 0,grow");

		JButton btnNewButton_3 = new JButton("Nova Jornada");
		btnNewButton_3.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.setBackground(new Color(0, 0, 153));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();
				CadastroFuncionarioAdmissao ct = new CadastroFuncionarioAdmissao();
				ct = gerenciar.getcontratoAtivoPorFuncionario(funcionario_local.getId_funcionario());

				if (ct == null) {
					JOptionPane.showMessageDialog(isto, "Adicione um Contrato de Trabalho para prosseguir");
				} else {

					// verifica se existe jornada de trabalho para o ultimi contrato ativo

					GerenciarBancoRotina gerenciar_rotina = new GerenciarBancoRotina();
					int num_rotinas = gerenciar_rotina.getNumRotinas(ct.getId_contrato());
					if (num_rotinas > 0) {
						JOptionPane.showMessageDialog(isto,
								"Já Existe uma Jornada de Trabalho para o atual contrato ativo");
					} else {
						TelaFuncionariosCadastroJornadaTrabalho tela = new TelaFuncionariosCadastroJornadaTrabalho(
								funcionario_local, isto);
						tela.setVisible(true);
					}
				}

			}
		});
		panel_3.add(btnNewButton_3, "cell 0 1,alignx right");

		JPanel panel_10 = new JPanel();
		panel_10.setBackground(new Color(0, 102, 153));
		tabbedPane.addTab("Eventos", null, panel_10, null);

		tabela_eventos = new JTable(modeloEventos);
		tabela_eventos.setRowHeight(30);
		JScrollPane scrollEventos = new JScrollPane(tabela_eventos);
		scrollEventos.getViewport().setBackground(Color.white);
		panel_10.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		panel_10.add(scrollEventos, "cell 0 0,grow");

		JButton btnRegistrarEvento = new JButton("Registrar Evento");
		btnRegistrarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int num_contratos = new GerenciarBancoFuncionariosContratoTrabalho()
						.getNumContratosAtivos(funcionario_local.getId_funcionario());

				if (num_contratos <= 0) {
					JOptionPane.showMessageDialog(isto, "Adicione um Contrato de Trabalho para prosseguir");
				} else {

					TelaFuncionariosCadastroEvento tela = new TelaFuncionariosCadastroEvento(funcionario_local, isto);
					tela.setVisible(true);

				}

			}
		});

		JButton btnExcluirEvento = new JButton("Excluir Evento");
		btnExcluirEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroFuncionarioEvento evt = getEventoSelecionado();

				if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir o Evento?", "Excluir Evento",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					GerenciarBancoFuncionariosEventos gerenciar_evts = new GerenciarBancoFuncionariosEventos();
					boolean remover = gerenciar_evts.removerevento(evt.getId_evento());
					if (remover) {
						JOptionPane.showMessageDialog(isto, "Evento Excluído");

						pesquisar_eventos();
					} else {
						JOptionPane.showMessageDialog(isto, "Evento Não Excluído!\nConsulte o Administrador");

					}

				} else {

				}

			}
		});
		btnExcluirEvento.setForeground(Color.WHITE);
		btnExcluirEvento.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnExcluirEvento.setBackground(new Color(153, 0, 51));
		panel_10.add(btnExcluirEvento, "flowx,cell 0 1,alignx right");
		btnRegistrarEvento.setBackground(new Color(0, 51, 204));
		btnRegistrarEvento.setForeground(Color.WHITE);
		btnRegistrarEvento.setFont(new Font("SansSerif", Font.BOLD, 16));
		panel_10.add(btnRegistrarEvento, "cell 0 1,alignx right");

		JPanel painelDadosIniciais = new JPanel();
		painelDadosIniciais.setBackground(new Color(0, 102, 153));
		painelDadosIniciais.setBounds(198, 153, 1091, 468);
		painelPrincipal.add(painelDadosIniciais);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEditar.setBackground(new Color(0, 51, 0));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFuncionariosCadastroFuncionario telaEdicao = new TelaFuncionariosCadastroFuncionario(1,
						funcionario_local, isto);
				telaEdicao.setVisible(true);
			}
		});
		painelDadosIniciais.setLayout(new MigLayout("", "[grow]", "[grow][28px]"));
		painelDadosIniciais.add(btnEditar, "cell 0 1,alignx right,growy");

		JPanel painelInfo = new JPanel();
		painelInfo.setBackground(new Color(0, 102, 153));
		painelDadosIniciais.add(painelInfo, "cell 0 0,grow");
		painelInfo.setLayout(new MigLayout("", "[][]", "[][][][][][][][][][][][]"));

		lblTipoIdentificacao = new JLabel("CPF:");
		lblTipoIdentificacao.setForeground(Color.WHITE);
		lblTipoIdentificacao.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblTipoIdentificacao, "cell 0 1,alignx right");

		lblCpf = new JLabel("");
		lblCpf.setForeground(Color.WHITE);
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblCpf, "cell 1 1");

		JLabel lblEndereo = new JLabel("Endereço:");
		lblEndereo.setForeground(Color.WHITE);
		lblEndereo.setFont(new Font("Tahoma", Font.BOLD, 14));
		painelInfo.add(lblEndereo, "cell 0 3,alignx right");

		lblEndereco = new JLabel("");
		lblEndereco.setForeground(Color.WHITE);
		lblEndereco.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblEndereco, "cell 1 3");

		lblStatus = new JLabel("");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblStatus, "cell 1 4,alignx left");

		JLabel lblContratoDeTrabalho = new JLabel("Contrato de Trabalho Ativo");
		lblContratoDeTrabalho.setForeground(Color.WHITE);
		lblContratoDeTrabalho.setFont(new Font("Tahoma", Font.BOLD, 24));
		painelInfo.add(lblContratoDeTrabalho, "cell 0 5 2 1");

		JLabel lblTipoContrato = new JLabel("Tipo Contrato:");
		lblTipoContrato.setForeground(Color.WHITE);
		lblTipoContrato.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblTipoContrato, "cell 0 7,alignx right");

		lblTipoContratoAtivo = new JLabel("tipo contrato");
		lblTipoContratoAtivo.setForeground(Color.WHITE);
		lblTipoContratoAtivo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblTipoContratoAtivo, "cell 1 7");

		JLabel lblCargo123 = new JLabel("Cargo:");
		lblCargo123.setForeground(Color.WHITE);
		lblCargo123.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblCargo123, "cell 0 8,alignx right");

		lblCargo = new JLabel("cargo");
		lblCargo.setForeground(Color.WHITE);
		lblCargo.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblCargo, "cell 1 8");

		JLabel lblFuno = new JLabel("Função:");
		lblFuno.setForeground(Color.WHITE);
		lblFuno.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblFuno, "cell 0 9,alignx right");

		lblFuncao = new JLabel("funcao");
		lblFuncao.setForeground(Color.WHITE);
		lblFuncao.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblFuncao, "cell 1 9");

		JLabel lblDataAdmisso = new JLabel("Data Admissão:");
		lblDataAdmisso.setForeground(Color.WHITE);
		lblDataAdmisso.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblDataAdmisso, "cell 0 10,alignx right");

		lblDataAdmissao = new JLabel("00/00/0000");
		lblDataAdmissao.setForeground(Color.WHITE);
		lblDataAdmissao.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblDataAdmissao, "cell 1 10");

		JLabel lblltimoSalrio = new JLabel("Último Salário:");
		lblltimoSalrio.setForeground(Color.WHITE);
		lblltimoSalrio.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblltimoSalrio, "cell 0 11,alignx right");

		lblSalario = new JLabel("R$ 2.000,00");
		lblSalario.setForeground(Color.WHITE);
		lblSalario.setFont(new Font("Tahoma", Font.BOLD, 16));
		painelInfo.add(lblSalario, "cell 1 11");

		btnDadosIniciais.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

				painelDadosIniciais.setEnabled(true);
				painelDadosIniciais.setVisible(true);

				painelContratos.setEnabled(false);
				painelContratos.setVisible(false);

				painelRegistrodePonto.setEnabled(false);
				painelRegistrodePonto.setVisible(false);

				btnDadosIniciais.setOpaque(true);
				btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

				btnDadosIniciais.repaint();
				btnDadosIniciais.updateUI();

				btnDocumentos.setOpaque(false);
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

			}
		});

		btnDocumentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

				painelDocumentos.setEnabled(true);
				painelDocumentos.setVisible(true);

				painelRegistrodePonto.setEnabled(false);
				painelRegistrodePonto.setVisible(false);

				painelFinancas.setEnabled(false);
				painelFinancas.setVisible(false);

				painelContratos.setEnabled(false);
				painelContratos.setVisible(false);

				btnDadosIniciais.setOpaque(false);
				btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

				btnDadosIniciais.repaint();
				btnDadosIniciais.updateUI();

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

				btnFinancas.setOpaque(false);
				btnFinancas.setBackground(new Color(0, 0, 0, 100));

				btnFinancas.repaint();
				btnFinancas.updateUI();

			}
		});

		btnContratos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				painelContratos.setEnabled(true);
				painelContratos.setVisible(true);

				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

				painelRegistrodePonto.setEnabled(false);
				painelRegistrodePonto.setVisible(false);

				btnContratos.setOpaque(true);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				btnDadosIniciais.setOpaque(false);
				btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

				btnDadosIniciais.repaint();
				btnDadosIniciais.updateUI();

				btnDeposito.setOpaque(false);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();

				btnDocumentos.setOpaque(false);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

				painelFinancas.setEnabled(false);
				painelFinancas.setVisible(false);

				btnFinancas.setOpaque(false);
				btnFinancas.setBackground(new Color(0, 0, 0, 100));

				btnFinancas.repaint();
				btnFinancas.updateUI();

			}
		});

		btnDeposito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

				painelContratos.setEnabled(false);
				painelContratos.setVisible(false);

				painelRegistrodePonto.setEnabled(true);
				painelRegistrodePonto.setVisible(true);

				btnDeposito.setOpaque(true);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();

				btnDadosIniciais.setOpaque(false);
				btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

				btnDadosIniciais.repaint();
				btnDadosIniciais.updateUI();

				btnDocumentos.setOpaque(false);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

				btnContratos.setOpaque(false);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				painelFinancas.setEnabled(false);
				painelFinancas.setVisible(false);

				btnFinancas.setOpaque(false);
				btnFinancas.setBackground(new Color(0, 0, 0, 100));

				btnFinancas.repaint();
				btnFinancas.updateUI();

			}
		});

		btnFinancas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				painelDocumentos.setEnabled(false);
				painelDocumentos.setVisible(false);

				painelDadosIniciais.setEnabled(false);
				painelDadosIniciais.setVisible(false);

				painelContratos.setEnabled(false);
				painelContratos.setVisible(false);

				painelRegistrodePonto.setEnabled(false);
				painelRegistrodePonto.setVisible(false);

				btnDeposito.setOpaque(false);
				btnDeposito.setBackground(new Color(0, 0, 0, 100));

				btnDeposito.repaint();
				btnDeposito.updateUI();

				btnDadosIniciais.setOpaque(false);
				btnDadosIniciais.setBackground(new Color(0, 0, 0, 100));

				btnDadosIniciais.repaint();
				btnDadosIniciais.updateUI();

				btnDocumentos.setOpaque(false);
				btnDocumentos.setBackground(new Color(0, 0, 0, 100));

				btnDocumentos.repaint();
				btnDocumentos.updateUI();

				btnContratos.setOpaque(false);
				btnContratos.setBackground(new Color(0, 0, 0, 100));

				btnContratos.repaint();
				btnContratos.updateUI();

				painelFinancas.setEnabled(true);
				painelFinancas.setVisible(true);

				btnFinancas.setOpaque(true);
				btnFinancas.setBackground(new Color(0, 0, 0, 100));

				btnFinancas.repaint();
				btnFinancas.updateUI();

			}
		});

		setInfo();

		pesquisar_banco_horas();
		setInformacoesDocumentos();
		pesquisar_cartoes();
		pesquisar_contratos();
		pesquisar_registros_ponto();
		pesquisarRotinaTrabalho();
		pesquisar_eventos();
		pesquisar_tarefas();
		pesquisar_rp_diario();
		setDadosUltimoContrato();
		pesquisar_salarios();
		pesquisar_contas_associadas();

		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar_contratos() {

		modelo_contratos.onRemoveAll();
		lista_contratos.clear();

		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();

		lista_contratos = gerenciar.getcontratosPorColaborador(funcionario_local.getId_funcionario());

		for (CadastroFuncionarioAdmissao cad : lista_contratos) {
			modelo_contratos.onAdd(cad);
		}

	}

	public void pesquisar_cartoes() {

		modelo_cartoes.onRemoveAll();
		lista_cartoes_completos.clear();

		GerenciarBancoCartaoPonto gerenciar = new GerenciarBancoCartaoPonto();

		lista_cartoes_completos = gerenciar.getCartoesPorFuncionario(funcionario_local.getId_funcionario());

		for (CartaoPontoCompleto cad : lista_cartoes_completos) {
			modelo_cartoes.onAdd(cad);
		}

	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc
				.getDocumentosPorFuncionario(funcionario_local.getId_funcionario());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				panel_docs.setLayout(new MigLayout("", "[grow]", "[grow]"));

				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				panel_docs.add(panel, "cell 0 0,grow");
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JLabel lblNewLabel_18 = new JLabel("Documentos deste Cliente:");
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

						String nome_pasta = "colaborador_" + funcionario_local.getCpf();

						String unidade_base_dados = configs_globais.getServidorUnidade();
						String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\colaboradores\\"
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
						if (JOptionPane.showConfirmDialog(isto, 
					            "Deseja Excluir este Documento", "Exclusão", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						
						String nome_arquivo = no_selecionado.getUserObject().toString();

						String quebra[] = nome_arquivo.split("@");

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}

						String nome_pasta = "colaborador_" + funcionario_local.getCpf();

						String unidade_base_dados = configs_globais.getServidorUnidade();
						String caminho_completo = unidade_base_dados + "\\" + "E-Contract\\arquivos\\colaboradores\\"
								+ nome_pasta + "\\documentos\\" + nome_official;

						boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
						if(excluido) {
							
						
							
							GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
							boolean excluir_documento = gerenciar_docs.removerDocumento(Integer.parseInt(quebra[0])  );
							
							if(excluir_documento) {
								JOptionPane.showMessageDialog(null, "Documento Excluido!");

							}else {
								JOptionPane.showMessageDialog(null, "Arquivo fisico apagado, mas as informações\ndeste documento ainda estão no banco de dados\nConsulte o administrador");

							}
							
                            atualizarArvoreDocumentos();
							
						}else {
							JOptionPane.showMessageDialog(null, "Erro ao excluir o documento\nConsulte o administrador!");
						}
						
				        
						
						}
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

				atualizarArvoreDocumentos();

			}
		});

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
			String nome_pasta = "colaborador_" + funcionario_local.getCpf();

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\colaboradores\\" + nome_pasta
					+ "\\documentos";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
					nome_arquivo = funcionario_local.getApelido() + "_" + nome + "_" + horaString.replaceAll(":", "_")
							+ "." + extensaoDoArquivo;

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
						novo_documento.setId_funcionario(funcionario_local.getId_funcionario());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao_funcionario(novo_documento);
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
				ArrayList<CadastroDocumento> lista_docs = gerenciar_doc
						.getDocumentosPorFuncionario(funcionario_local.getId_funcionario());

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

	public void atualizarFuncionario() {
		funcionario_local = new GerenciarBancoFuncionarios().getfuncionario(funcionario_local.getId_funcionario());

	}

	public class ContratosTrabalhoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int status = 1;
		private final int data_admissao = 2;
		private final int tipo_contrato = 3;
		private final int cargo = 4;
		private final int funcao = 5;
		private final int salario = 6;

		Locale ptBr = new Locale("pt", "BR");

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Status", "Data Admissão", "Tipo Contrato", "Cargo", "Função",
				"Salário" };
		private final ArrayList<CadastroFuncionarioAdmissao> dados = new ArrayList<>();// usamos como dados uma lista
																						// genérica de
		// nfs

		public ContratosTrabalhoTableModel() {

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
			case status:
				return String.class;
			case data_admissao:
				return String.class;
			case tipo_contrato:
				return String.class;
			case cargo:
				return String.class;
			case funcao:
				return String.class;
			case salario:
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
			CadastroFuncionarioAdmissao dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getId_contrato();
			case status: {

				if (dado.getStatus() == 0) {
					return "INATIVO";
				} else if (dado.getStatus() == 1) {
					return "ATIVO";
				}

			}
			case data_admissao: {
				return dado.getData_admissao();

			}
			case tipo_contrato: {
				return dado.getTipo_contrato();
			}
			case cargo:
				return dado.getCargo();
			case funcao:
				return dado.getFuncao();
			case salario: {

				return NumberFormat.getCurrencyInstance(ptBr).format(dado.getSalario());

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
			CadastroFuncionarioAdmissao ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioAdmissao getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioAdmissao dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioAdmissao dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioAdmissao> dadosIn) {
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
		public void onRemove(CadastroFuncionarioAdmissao dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public CadastroFuncionarioAdmissao getContratoSelecionado() {
		int indiceDaLinha = tabela_contratos.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_contratos.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoFuncionariosContratoTrabalho gerenciar_cont = new GerenciarBancoFuncionariosContratoTrabalho();
		return gerenciar_cont.getcontrato(id_selecionado);

	}

	public void setInfo() {
		lblCpf.setText(funcionario_local.getCpf());

		lblNome.setText(funcionario_local.getNome() + " " + funcionario_local.getSobrenome());
		lblEndereco.setText(funcionario_local.getRua() + ", Número: " + funcionario_local.getNumero() + ", Bairro: "
				+ funcionario_local.getBairro() + ", Cidade: " + funcionario_local.getCidade() + ", CEP: "
				+ funcionario_local.getCep() + ", Estado: " + funcionario_local.getEstado_endereco());

	}

	public static String formatarCpf(String texto) throws ParseException {
		MaskFormatter mf = new MaskFormatter("###.###.###-##");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(texto);
	}

	public static String formatarCNPJ(String texto) throws ParseException {
		MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(texto);
	}

	public static String formatarCep(String texto) throws ParseException {
		MaskFormatter mf = new MaskFormatter("#####-###");
		mf.setValueContainsLiteralCharacters(false);
		return mf.valueToString(texto);
	}

	public class CartaoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_funcionario = 0;
		private final int nome_funcionario = 1;

		private final int id_cartao = 2;
		private final int uid_cartao = 3;

		private final String colunas[] = { "ID Colaborador", "Nome Funcionário", "ID Cartão", "UID CARTÃO" };

		private final ArrayList<CartaoPontoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																				// genérica de
		// nfs

		public CartaoTableModel() {

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

			case id_funcionario:
				return Integer.class;
			case nome_funcionario:
				return String.class;
			case id_cartao:
				return Integer.class;
			case uid_cartao:
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
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CartaoPontoCompleto cartao = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_funcionario:
				return cartao.getId_funcionario();
			case nome_funcionario:
				return cartao.getNome_funcionario();
			case id_cartao:
				return cartao.getId_cartao();
			case uid_cartao:
				return cartao.getUid();

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
			CartaoPontoCompleto recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CartaoPontoCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CartaoPontoCompleto nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CartaoPontoCompleto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CartaoPontoCompleto> dadosIn) {
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
		public void onRemove(CartaoPontoCompleto nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public class RegistroPontoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int id_registro = 0;
		private final int dia_semana = 1;
		private final int data = 2;
		private final int hora = 3;
		private final int movimentacao = 4;
		private final int motivo = 5;

		private final String colunas[] = { "ID REGISTRO", "DIA DA SEMANA", "DATA", "HORA", "MOVIMENTAÇÃO", "MOTIVO" };

		private final ArrayList<RegistroPonto> dados = new ArrayList<>();// usamos como dados uma lista
																			// genérica de
		// nfs

		public RegistroPontoTableModel() {

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

			case id_registro:
				return Integer.class;
			case dia_semana:
				return String.class;
			case data:
				return String.class;
			case hora:
				return Integer.class;
			case movimentacao:
				return String.class;
			case motivo:
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

			// pega o dados corrente da linha
			RegistroPonto rp = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_registro:
				return rp.getId_registro_ponto();
			case dia_semana: {

				DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));

				LocalDate data = LocalDate.parse(rp.getData(), formatter.ofPattern("dd/MM/yyyy"));
				DayOfWeek dia_s = data.getDayOfWeek();
				return dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();

			}
			case data: {
				return rp.getData();

			}
			case hora: {
				return rp.getHora();
			}
			case movimentacao: {
				int mov = rp.getMovimentacao();

				if (mov == 1) {
					return "ENTRADA 1";
				} else if (mov == 2) {
					return "SAÍDA 1";
				} else if (mov == 3) {
					return "ENTRADA 2";
				} else if (mov == 4) {
					return "SAÍDA 2";
				} else if (mov == 5) {
					return "ENTRADA 3";
				} else if (mov == 6) {
					return "SAÍDA 3";
				}

			}
			case motivo: {
				return rp.getMotivo();
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
			RegistroPonto recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public RegistroPonto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(RegistroPonto nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(RegistroPonto nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<RegistroPonto> dadosIn) {
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
		public void onRemove(RegistroPonto nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public class RegistroPontoDiarioTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int data = 0;
		private final int dia_semana = 1;
		private final int entrada1 = 2;
		private final int saida1 = 3;
		private final int entrada2 = 4;
		private final int saida2 = 5;
		private final int entrada3 = 6;
		private final int saida3 = 7;

		private final String colunas[] = { "DATA", "DIA DA SEMANA", "ENTRADA 1", "SAÍDA 1", "ENTRADA 2", "SAÍDA 2",
				"ENTRADA 3", "SAÍDA 3" };

		private final ArrayList<RegistroPontoDiario> dados = new ArrayList<>();// usamos como dados uma lista
																				// genérica de
		// nfs

		public RegistroPontoDiarioTableModel() {

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

			case data:
				return Integer.class;
			case dia_semana:
				return String.class;
			case entrada1:
				return String.class;
			case saida1:
				return Integer.class;
			case entrada2:
				return String.class;
			case saida2:
				return Integer.class;
			case entrada3:
				return String.class;
			case saida3:
				return Integer.class;

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

			// pega o dados corrente da linha
			RegistroPontoDiario rp = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case data: {
				return rp.getData();

			}
			case dia_semana: {

				DateTimeFormatter formatter = new DateTimeFormatterBuilder().toFormatter(new Locale("pt", "BR"));

				LocalDate data = LocalDate.parse(rp.getData(), formatter.ofPattern("dd/MM/yyyy"));
				DayOfWeek dia_s = data.getDayOfWeek();
				return dia_s.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")).toUpperCase();

			}
			case entrada1: {
				return rp.getEntrada1();
			}
			case saida1:
				return rp.getSaida1();
			case entrada2:
				return rp.getEntrada2();

			case saida2:
				return rp.getSaida2();
			case entrada3:
				return rp.getEntrada3();
			case saida3:
				return rp.getSaida3();

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
			RegistroPontoDiario recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public RegistroPontoDiario getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(RegistroPontoDiario nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(RegistroPontoDiario nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<RegistroPontoDiario> dadosIn) {
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
		public void onRemove(RegistroPontoDiario nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public class CadastroFuncionarioEventoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas

		private final int id_evento = 0;
		private final int data_evento = 1;
		private final int tipo = 2;
		private final int alteracao = 3;

		private final String colunas[] = { "ID", "DATA", "TIPO", "ALTERAÇÃO" };

		private final ArrayList<CadastroFuncionarioEvento> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public CadastroFuncionarioEventoTableModel() {

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

			case id_evento:
				return Integer.class;
			case data_evento:
				return String.class;
			case tipo:
				return String.class;
			case alteracao:
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

			// pega o dados corrente da linha
			CadastroFuncionarioEvento rp = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_evento:
				return rp.getId_evento();
			case data_evento:
				return rp.getData_evento();
			case tipo: {
				int tipo_event = rp.getTipo_evento();

				if (tipo_event == 0) {
					// folga
					return "FOLGA";
				} else if (tipo_event == 1) {
					// alteracao salarial
					return "ALTERAÇÃO SALARIAL";
				} else if (tipo_event == 2) {
					// demissao
					return "ENCERRAMENTO DE CONTRATO";
				} else if (tipo_event == 3) {
					// ferias
					return "FÉRIAS";
				} else if (tipo_event == 4) {
					// insecao de rp
					return "ISENÇÃO DE PONTO";
				}

			}
			case alteracao: {

				int tipo_event = rp.getTipo_evento();

				if (tipo_event == 0) {
					// folga
					return "FOLGA para a data de: " + rp.getData_folga();
				} else if (tipo_event == 1) {
					// alteracao salarial
					return "ALTERAÇÃO SALARIAL para o novo valor de: " + rp.getNovo_valor_salarial();
				} else if (tipo_event == 2) {

					int motivo = rp.getMotivo_demissao();
					String s_motivo = "";
					if (motivo == 0) {
						s_motivo = "ENCERRAMENTO DE CONTRATO";
					} else if (motivo == 1) {
						s_motivo = "JUSTA CAUSA";
					} else if (motivo == 2) {
						s_motivo = "SEM JUSTA CAUSA";
					} else if (motivo == 3) {
						s_motivo = "PEDIDO DE DISPENSA PELO COLABORADOR";
					}

					// demissao
					return "ENCERRAMENTO DE CONTRATO pelo motivo de: " + s_motivo;
				} else if (tipo_event == 3) {
					// ferias
					return "FÉRIAS de " + rp.getData_ferias_ida() + " até " + rp.getData_ferias_volta();
				} else if (tipo_event == 4) {
					return "ISENÇÃO DE PONTO de " + rp.getData_ferias_ida() + " até " + rp.getData_ferias_volta();

				} else if (tipo_event == 5) {
					return "LICENÇA de " + rp.getData_ferias_ida() + " até " + rp.getData_ferias_volta();

				}
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
			CadastroFuncionarioEvento recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioEvento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioEvento nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioEvento nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioEvento> dadosIn) {
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
		public void onRemove(CadastroFuncionarioEvento nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public void pesquisar_registros_ponto() {
		GerenciarBancoRegistroPonto gerenciar = new GerenciarBancoRegistroPonto();
		modeloRp.onRemoveAll();

		ArrayList<RegistroPonto> list_rp = new ArrayList<>();
		list_rp = gerenciar.getRegistrosPontosPorColaborador(funcionario_local.getId_funcionario());
		for (RegistroPonto rp : list_rp) {
			modeloRp.onAdd(rp);
		}

	}

	class CellRenderRPDiario extends DefaultTableCellRenderer {

		public CellRenderRPDiario() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setHorizontalAlignment(CENTER);

			String entrada1 = (String) tabela_rp_diario.getValueAt(row, 2);
			String saida1 = (String) tabela_rp_diario.getValueAt(row, 3);
			String entrada2 = (String) tabela_rp_diario.getValueAt(row, 4);
			String saida2 = (String) tabela_rp_diario.getValueAt(row, 5);

			if (entrada1 != null) {

				if (saida1 == null || entrada2 == null || saida2 == null) {
					// deu entrada nesse dia
					setBackground(Color.red);
					setForeground(Color.white);
				} else {
					setBackground(Color.white);
					setForeground(Color.black);
				}

			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	public void pesquisar_eventos() {
		GerenciarBancoFuncionariosEventos gerenciar = new GerenciarBancoFuncionariosEventos();

		modeloEventos.onRemoveAll();

		for (CadastroFuncionarioEvento evt : gerenciar
				.getEventosPorColaborador(funcionario_local.getId_funcionario())) {
			modeloEventos.onAdd(evt);
		}

	}

	public CadastroFuncionarioEvento getEventoSelecionado() {
		return (CadastroFuncionarioEvento) modeloEventos.getValue(tabela_eventos.getSelectedRow());
	}

	public void pesquisar_rp_diario() {

		int ano = -1;
		try {
			ano = Integer.parseInt(entAno.getText());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ano Inválido");
			return;
		}

		GerenciarBancoRegistroPonto gerenciar = new GerenciarBancoRegistroPonto();
		modeloRpDiario.onRemoveAll();

		ArrayList<RegistroPontoDiario> listRpDiario = new ArrayList<>();
		listRpDiario = gerenciar.getDemonstrativoFuncionarioMes(funcionario_local.getId_funcionario(),
				cbMes.getSelectedIndex() + 1, ano);
		for (RegistroPontoDiario rp : listRpDiario) {
			modeloRpDiario.onAdd(rp);
		}
	}

	public RegistroPonto getRpSelecionado() {
		return modeloRp.getValue(tabela_registros_ponto.getSelectedRow());
	}

	public class TarefaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_tarefa = 0;
		private final int status = 1;
		private final int nome = 2;
		private final int descricao = 3;
		private final int mensagem = 4;
		private final int resposta = 5;
		private final int data = 6;
		private final int hora = 7;
		private final int criador = 8;
		private final int executor = 9;

		private final int hora_agendada = 10;

		private final int data_agendada = 11;
		private final int prioridade = 12;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Status", "Nome", "Descrição", "Mensagem", "Resposta", "Data", "Hora",
				"Criador", "Executor", "Hora Agendada", "Data Agendada", "Prioridade" };

		private final ArrayList<CadastroTarefaGeral> dados = new ArrayList<>();// usamos como dados uma lista genérica
																				// de
																				// nfs

		public TarefaTableModel() {

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
			case id_tarefa: {
				return Integer.class;

			}
			case status: {
				return String.class;
			}
			case nome: {
				return String.class;
			}
			case descricao: {
				return String.class;
			}
			case mensagem: {
				return String.class;
			}
			case resposta: {
				return String.class;
			}
			case data: {
				return String.class;
			}
			case hora: {
				return String.class;
			}
			case criador: {
				return String.class;
			}
			case executor: {
				return String.class;
			}

			case hora_agendada: {
				return String.class;
			}
			case data_agendada: {
				return String.class;
			}
			case prioridade: {
				return String.class;
			}
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
			CadastroTarefaGeral dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_tarefa: {
				dado.getId_tarefa();
			}
			case status: {
				if (dado.getStatus_tarefa() == 1) {
					return "Concluida";

				} else if (dado.getStatus_tarefa() == 2) {
					return "Em Andamento";

				}
			}
			case nome: {
				return dado.getNome_tarefa();
			}
			case descricao: {
				return dado.getDescricao_tarefa();
			}
			case mensagem: {
				return dado.getMensagem();
			}
			case resposta: {
				return dado.getResposta();
			}
			case data: {
				return dado.getData();
			}
			case hora: {
				return dado.getHora();
			}
			case criador: {
				return dado.getNome_criador();
			}
			case executor: {
				return dado.getNome_executor();
			}

			case hora_agendada: {
				return dado.getHora_agendada();
			}
			case data_agendada: {
				return dado.getData_agendada();
			}
			case prioridade: {

				if (dado.getPrioridade() == 1) {
					return "Imediata - Neste Momento";
				} else if (dado.getPrioridade() == 2) {
					return "Urgente - Nesta Hora";
				} else if (dado.getPrioridade() == 3) {
					return "Quanto Antes - Ainda Hoje";
				} else if (dado.getPrioridade() == 4) {
					return "Média - Ainda essa semana";
				} else if (dado.getPrioridade() == 5) {
					return "Leve - Ainda esse mês";
				}

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

			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroTarefaGeral ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroTarefaGeral getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroTarefaGeral dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroTarefaGeral dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroTarefaGeral> dadosIn) {
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
		public void onRemove(CadastroTarefaGeral dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroTarefaGeral onGet(int row) {
			return dados.get(row);
		}
	}

	public class JornadaTrabalhoTableModel extends AbstractTableModel {
		/*
		 * modelo_tarefas.addColumn("Id Tarefas"); modelo_tarefas.addColumn("Status");
		 * 
		 * modelo_tarefas.addColumn("Nome"); modelo_tarefas.addColumn("Descrição");
		 * modelo_tarefas.addColumn("Mensagem"); modelo_tarefas.addColumn("Resposta");
		 * 
		 * modelo_tarefas.addColumn("Data");
		 * 
		 * modelo_tarefas.addColumn("Hora"); modelo_tarefas.addColumn("Criador");
		 * 
		 * modelo_tarefas.addColumn("Executor");
		 * 
		 * modelo_tarefas.addColumn("Hora Agendada");
		 * modelo_tarefas.addColumn("Data Agendada");
		 * modelo_tarefas.addColumn("Prioridade");
		 */
		// constantes p/identificar colunas
		private final int id_rotina = 0;
		private final int id_ct_trabalho = 1;
		private final int tipo_rotina = 2;
		private final int dia_da_semana = 3;
		private final int hora_entrada1 = 4;
		private final int hora_saida1 = 5;
		private final int hora_entrada2 = 6;
		private final int hora_saida2 = 7;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "CT Trabalho", "Tipo Rotina", "Dia da Semana", "Entrada 1", "Saída 1",
				"Entrada 2", "Saída 2" };

		private final ArrayList<CadastroFuncionarioRotinaTrabalho> dados = new ArrayList<>();// usamos como dados uma
																								// lista genérica
		// de
		// nfs

		public JornadaTrabalhoTableModel() {

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
			case id_rotina: {
				return Integer.class;

			}
			case id_ct_trabalho: {
				return Integer.class;
			}
			case tipo_rotina: {
				return String.class;
			}
			case dia_da_semana: {
				return String.class;
			}
			case hora_entrada1: {
				return String.class;
			}
			case hora_saida1: {
				return String.class;
			}
			case hora_entrada2: {
				return String.class;
			}
			case hora_saida2: {
				return String.class;
			}

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
			CadastroFuncionarioRotinaTrabalho dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_rotina: {
				return dado.getId_rotina();

			}
			case id_ct_trabalho: {
				return dado.getId_ct_trabalho();
			}
			case tipo_rotina: {
				if (dado.getTipo_rotina() == 0) {
					return "Turno Fixo";
				}
			}
			case dia_da_semana: {

				int dia_semana = dado.getDia_da_semana();

				if (dia_semana == 1) {
					return "SEGUNDA-FEIRA";
				} else if (dia_semana == 2) {
					return "TERÇA-FEIRA";
				} else if (dia_semana == 3) {
					return "QUARTA-FEIRA";
				} else if (dia_semana == 4) {
					return "QUINTA-FEIRA";
				} else if (dia_semana == 5) {
					return "SEXTA-FEIRA";
				}

				else if (dia_semana == 6) {
					return "SÁBADO";
				}

				else if (dia_semana == 7) {
					return "DOMINGO";
				}

			}
			case hora_entrada1: {
				return dado.getHora_entrada1();
			}
			case hora_saida1: {
				return dado.getHora_saida1();
			}
			case hora_entrada2: {
				return dado.getHora_entrada2();
			}
			case hora_saida2: {
				return dado.getHora_saida2();
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

			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CadastroFuncionarioRotinaTrabalho ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioRotinaTrabalho getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioRotinaTrabalho dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioRotinaTrabalho dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioRotinaTrabalho> dadosIn) {
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
		public void onRemove(CadastroFuncionarioRotinaTrabalho dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public CadastroFuncionarioRotinaTrabalho onGet(int row) {
			return dados.get(row);
		}
	}

	public void pesquisar_tarefas() {
		GerenciarBancoTarefaGeral gerenciar = new GerenciarBancoTarefaGeral();
		lista_tarefas.clear();
		modelo_tarefa.onRemoveAll();

		for (CadastroTarefaGeral tarefa : gerenciar
				.consultaTarefasFuncionarios(funcionario_local.getId_funcionario())) {

			modelo_tarefa.onAdd(tarefa);
			lista_tarefas.add(tarefa);

		}
	}

	public void pesquisarRotinaTrabalho() {
		GerenciarBancoRotina gerenciar = new GerenciarBancoRotina();
		listaJornadaTrabalho.clear();
		modeloJornadaTrabalho.onRemoveAll();

		for (CadastroFuncionarioAdmissao ct : lista_contratos) {

			for (CadastroFuncionarioRotinaTrabalho rotinas : gerenciar.getRotinas(ct.getId_contrato(),
					funcionario_local.getId_funcionario())) {

				listaJornadaTrabalho.add(rotinas);

				modeloJornadaTrabalho.onAdd(rotinas);

			}

		}
	}

	public void setDadosUltimoContrato() {
		GerenciarBancoFuncionariosContratoTrabalho gerenciar = new GerenciarBancoFuncionariosContratoTrabalho();
		CadastroFuncionarioAdmissao ct = new CadastroFuncionarioAdmissao();
		ct = gerenciar.getcontratoAtivoPorFuncionario(funcionario_local.getId_funcionario());

		if (ct != null) {
			lblDataAdmissao.setText(ct.getData_admissao());
			lblCargo.setText(ct.getCargo());
			lblFuncao.setText(ct.getFuncao());
			lblTipoContratoAtivo.setText(ct.getTipo_contrato());

			GerenciarBancoFuncionariosEventos ger_fun = new GerenciarBancoFuncionariosEventos();
			double salario = ger_fun.getUltimoValorSalarial(funcionario_local.getId_funcionario());
			lblSalario.setText(NumberFormat.getCurrencyInstance(ptBr).format(salario));
		} else {
			lblDataAdmissao.setText("Sem Contrato Ativo");
			lblCargo.setText("Sem Contrato Ativo");
			lblFuncao.setText("Sem Contrato Ativo");
			lblTipoContratoAtivo.setText("Sem Contrato Ativo");

			lblSalario.setText("Sem Contrato Ativo");

		}
	}

	public String formatHora(long minutos) {
		Duration duracao_total_horas_normais = Duration.ofMinutes(minutos);
		return String.format("%d:%02d", duracao_total_horas_normais.toHours(),
				duracao_total_horas_normais.toMinutesPart());

	}

	public class BancoHorasTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_banco = 0;
		private final int mes_referencia = 1;
		private final int tipo_banco = 2;
		private final int quantidade_horas = 3;

		private final String colunas[] = { "ID", "Mês Referência", "Tipo do Banco", "Quantidade de Horas", };

		private final ArrayList<CadastroFuncionarioBancoHoras> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public BancoHorasTableModel() {

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

			case id_banco:
				return Integer.class;
			case mes_referencia:
				return String.class;
			case tipo_banco:
				return String.class;
			case quantidade_horas:
				return Integer.class;

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
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroFuncionarioBancoHoras dado = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {

			case id_banco:
				return dado.getId_banco();
			case mes_referencia: {
				int mes = dado.getMes_referencia() + 1;
				if (mes == 1) {
					return "JANEIRO";
				} else if (mes == 2) {
					return "FEVEREIRO";
				} else if (mes == 3) {
					return "MARÇO";
				} else if (mes == 4) {
					return "ABRIL";
				} else if (mes == 5) {
					return "MAIO";
				} else if (mes == 6) {
					return "JUNHO";
				} else if (mes == 7) {
					return "JULHO";
				} else if (mes == 8) {
					return "AGOSTO";
				} else if (mes == 9) {
					return "SETEMBRO";
				} else if (mes == 10) {
					return "OUTUBRO";
				} else if (mes == 11) {
					return "NOVEMBRO";
				} else if (mes == 12) {
					return "DEZEMBRO";
				}
			}
			case tipo_banco: {
				if (dado.getTipo_banco() == 0) {
					return "NEGATIVO";
				} else if (dado.getTipo_banco() == 1) {
					return "POSITIVO";
				}
			}
			case quantidade_horas: {
				String quanti = dado.getQuantidade_horas();
				long minutos = Long.parseLong(quanti);
				return formatHora(minutos);

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
			CadastroFuncionarioBancoHoras recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioBancoHoras getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioBancoHoras nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioBancoHoras nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioBancoHoras> dadosIn) {
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
		public void onRemove(CadastroFuncionarioBancoHoras nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public class SalarioTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_salario = 0;
		private final int id_ct_trabalho = 1;

		private final int mes = 2;
		private final int ano = 3;
		private final int salario_base = 4;
		private final int total_descontos = 5;
		private final int total_acrescimos = 6;
		private final int total_horas_extras = 7;
		private final int bruto = 8;
		private final int liquido = 9;

		private final String colunas[] = { "ID", "CT de Trabalho", "Mês", "Ano", "Sálario Base", "Total Descontos",
				"Total Acréscimos", "Total Horas Extras", "Bruto", "Líquido" };

		private final ArrayList<CadastroFuncionarioSalario> dados = new ArrayList<>();// usamos como dados uma lista
		// genérica de
		// nfs

		public SalarioTableModel() {

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

			case id_salario:
				return Integer.class;
			case id_ct_trabalho:
				return String.class;
			case mes:
				return String.class;
			case ano:
				return String.class;
			case salario_base:
				return String.class;
			case total_descontos:
				return String.class;
			case total_acrescimos:
				return String.class;
			case total_horas_extras:
				return String.class;
			case bruto:
				return String.class;
			case liquido:
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
			NumberFormat z = NumberFormat.getNumberInstance();

			// pega o dados corrente da linha
			CadastroFuncionarioSalario dado = dados.get(rowIndex);

			// retorna o valor da coluna
			switch (columnIndex) {
			case id_salario:
				return dado.getId_salario();
			case id_ct_trabalho:
				return dado.getId_ct_trabalho();
			case mes: {
				int mes = dado.getMes() + 1;
				if (mes == 1) {
					return "JANEIRO";
				} else if (mes == 2) {
					return "FEVEREIRO";
				} else if (mes == 3) {
					return "MARÇO";
				} else if (mes == 4) {
					return "ABRIL";
				} else if (mes == 5) {
					return "MAIO";
				} else if (mes == 6) {
					return "JUNHO";
				} else if (mes == 7) {
					return "JULHO";
				} else if (mes == 8) {
					return "AGOSTO";
				} else if (mes == 9) {
					return "SETEMBRO";
				} else if (mes == 10) {
					return "OUTUBRO";
				} else if (mes == 11) {
					return "NOVEMBRO";
				} else if (mes == 12) {
					return "DEZEMBRO";
				}
			}
			case ano: {
				return dado.getAno();
			}
			case salario_base: {
				return formatarValor(dado.getSalario_base());
			}
			case total_descontos: {
				return formatarValor(dado.getTotal_descontos());

			}
			case total_acrescimos: {
				return formatarValor(dado.getTotal_acrescimos());

			}
			case total_horas_extras: {
				return formatarValor(dado.getTotal_hora_extras());

			}
			case bruto: {
				return formatarValor(dado.getSalario_base() + dado.getTotal_hora_extras() + dado.getTotal_acrescimos());

			}
			case liquido: {
				return formatarValor(dado.getSalario_base() + dado.getTotal_hora_extras() - dado.getTotal_descontos());

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
			CadastroFuncionarioSalario recebimento = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroFuncionarioSalario getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroFuncionarioSalario nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroFuncionarioSalario nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroFuncionarioSalario> dadosIn) {
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
		public void onRemove(CadastroFuncionarioSalario nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public String formatarValor(double valor) {
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}

	public void pesquisar_salarios() {
		GerenciarBancoFuncionarioSalarios gerenciar = new GerenciarBancoFuncionarioSalarios();

		modelSalarios.onRemoveAll();

		ArrayList<CadastroFuncionarioSalario> lista_salarios = gerenciar
				.getSalariosPorFuncionario(funcionario_local.getId_funcionario());
		for (CadastroFuncionarioSalario sal : lista_salarios) {
			modelSalarios.onAdd(sal);
		}

	}

	public void pesquisar_banco_horas() {
		GerenciarBancoFuncionarioBancoHoras gerenciar = new GerenciarBancoFuncionarioBancoHoras();

		modeloBancoHoras.onRemoveAll();

		ArrayList<CadastroFuncionarioBancoHoras> lista_banco_horas = gerenciar
				.getBancoHorasPorFuncionario(funcionario_local.getId_funcionario());
		for (CadastroFuncionarioBancoHoras banco : lista_banco_horas) {
			modeloBancoHoras.onAdd(banco);
		}

	}

	class CellRenderBancoHoras extends DefaultTableCellRenderer {

		public CellRenderBancoHoras() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.setHorizontalAlignment(CENTER);
			this.setFont(new Font("Arial", Font.PLAIN, 16));
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	public CadastroFuncionarioBancoHoras getBancoHorasSelecionado() {
		int indiceDaLinha = tabela_banco_horas.getSelectedRow();

		int id_selecionado = Integer.parseInt(tabela_banco_horas.getValueAt(indiceDaLinha, 0).toString());
		GerenciarBancoFuncionarioBancoHoras gerenciar_cont = new GerenciarBancoFuncionarioBancoHoras();
		return gerenciar_cont.getBancoHorasPorId(id_selecionado);

	}

	public int getAssociacaoSelecionada() {
		int indiceDaLinha = tabela_cartoes.getSelectedRow();

		return Integer.parseInt(tabela_cartoes.getValueAt(indiceDaLinha, 2).toString());

	}

	public class LancamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int data = 1;
		private final int tipo_lancamento = 2;
		private final int centro_custo = 3;
		private final int cliente_fornecedor = 4;
		private final int grupo_conta = 5;
		private final int conta = 6;
		private final int valor = 7;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Data Lançamento", "Tipo", "Centro de Custo", "Cliente/Fornecedor",
				"Grupo de Contas", "Conta", "Valor Total" };
		private final ArrayList<Lancamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																		// nfs
		private GerenciarBancoCondicaoPagamentos gerenciar = null;
		private ArrayList<CondicaoPagamento> lista_condicoes = null;
		Locale ptBr = new Locale("pt", "BR");

		public LancamentoTableModel() {

			gerenciar = new GerenciarBancoCondicaoPagamentos();

			lista_condicoes = gerenciar.getCondicaoPagamentos();

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
			case data:
				return Date.class;
			case tipo_lancamento:
				return String.class;

			case centro_custo:
				return String.class;

			case cliente_fornecedor:
				return String.class;
			case grupo_conta:
				return String.class;
			case conta:
				return String.class;
			case valor:
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
			Lancamento dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getId_lancamento();
			case tipo_lancamento: {
				if (dado.getTipo_lancamento() == 0) {
					return "DESPESAS";
				} else if (dado.getTipo_lancamento() == 1) {
					return "RECEITAS";

				} else if (dado.getTipo_lancamento() == 2) {
					return "TRANSFERENCIAS";

				} else if (dado.getTipo_lancamento() == 3) {
					return "EMPRESTIMOS";

				}
			}

			case data: {
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					data_menor = formato.parse(dado.getData_lancamento());
					return data_menor;

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			case centro_custo: {

				if (dado.getNome_centro_custo() != null)
					return dado.getNome_centro_custo();
				else
					return "INDEFINIDO";
			}

			case cliente_fornecedor: {

				if (dado.getNome_cliente_fornecedor() != null)
					return dado.getNome_cliente_fornecedor().toUpperCase();
				else
					return "INDEFINIDO";

			}
			case grupo_conta: {
				return (dado.getNome_grupo_contas() != null) ? dado.getNome_grupo_contas().toUpperCase() : "";

			}
			case conta: {
				return (dado.getNome_conta() != null) ? dado.getNome_conta().toUpperCase() : "";

			}

			case valor: {
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
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
			Lancamento ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public Lancamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(Lancamento dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(Lancamento dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<Lancamento> dadosIn) {
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
		public void onRemove(Lancamento dado) {
			int indexBefore = indexOf(dado);// pega o indice antes de apagar
			dados.remove(dado);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

		public Lancamento onGet(int row) {
			return dados.get(row);
		}
	}

	public static class ClienteTableModel extends AbstractTableModel {

		private final int id = 0;
		private final int ie = 1;
		private final int apelido = 2;
		private final int cpf_cnpj = 3;
		private final int nome = 4;

		private final String colunas[] = { "ID:", "IE:", "Apelido:", "CPF/CNPJ:", "Nome:" };
		private final ArrayList<FuncionarioContaAssociada> dados = new ArrayList<>();// usamos como dados uma lista
																						// genérica de nfs

		public ClienteTableModel() {

		}

		@Override
		public int getColumnCount() {
			// retorna o total de colunas
			return colunas.length;
		}

		@Override
		public int getRowCount() {
			// retorna o total de linhas na table
			return dados.size();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// retorna o tipo de dado, para cada coluna
			switch (columnIndex) {
			case id:
				return Integer.class;
			case ie:
				return String.class;
			case apelido:
				return String.class;
			case cpf_cnpj:
				return String.class;
			case nome:
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

			// pega o dados corrente da linha
			CadastroCliente nota = dados.get(rowIndex).getCliente();

			// retorna o valor da coluna
			switch (columnIndex) {
			case id:
				return nota.getId();
			case ie:
				return nota.getIe();
			case apelido:
				return nota.getApelido().toUpperCase();
			case cpf_cnpj: {
				if (nota.getTipo_pessoa() == 0)
					return nota.getCpf();
				else
					return nota.getCnpj();

			}
			case nome:
				if (nota.getTipo_pessoa() == 0)
					return nota.getNome_empresarial().toUpperCase();
				else
					return nota.getNome_fantaia().toUpperCase();

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
			FuncionarioContaAssociada nota = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FuncionarioContaAssociada getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FuncionarioContaAssociada nota) {
			return dados.indexOf(nota);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FuncionarioContaAssociada nota) {
			dados.add(nota);
			fireTableRowsInserted(indexOf(nota), indexOf(nota));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FuncionarioContaAssociada> dadosIn) {
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
		public void onRemove(FuncionarioContaAssociada nota) {
			int indexBefore = indexOf(nota);// pega o indice antes de apagar
			dados.remove(nota);
			fireTableRowsDeleted(indexBefore, indexBefore);
		}

		/**
		 * remove todos registros da lista
		 */
		public void onRemoveAll() {
			dados.clear();
			fireTableDataChanged();
		}

	}

	public void pesquisar_contas_associadas() {
		
		modelClientes.onRemoveAll();
		modelLancamentos.onRemoveAll();

		GerenciarBancoFuncionarios gerenciar = new GerenciarBancoFuncionarios();

		int mes = cBMesLancamentos.getSelectedIndex();
		int ano = 0;
		try {
			ano = Integer.parseInt(entAnoLancamentos.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(isto, "Ano Inválido!");
			return;
		}
		ArrayList<FuncionarioContaAssociada> lista_contas = new ArrayList<>();
		lista_contas = gerenciar.buscarContasAssociadas(funcionario_local.getId_funcionario());
		for (FuncionarioContaAssociada fca : lista_contas) {
			modelClientes.onAdd(fca);
		}

		GerenciarBancoLancamento gerenciar_lanc = new GerenciarBancoLancamento();
		for (FuncionarioContaAssociada fca : lista_contas) {
			ArrayList<Lancamento> lista_lancamentos = gerenciar_lanc
					.buscaLancamentosCompletosPorCliente(fca.getCliente().getId(), mes, ano);
			for (Lancamento lanc : lista_lancamentos) {
				modelLancamentos.onAdd(lanc);
			}
		}

	}

	public Lancamento getLancamentoSelecionado() {

		int indice = tabela_lancamentos.getSelectedRow();// pega o indice da linha na tabela

		return modelLancamentos.getValue(indice);
	}

	class LancamentosRender extends DefaultTableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			((JLabel) renderer).setOpaque(true);
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			if (value instanceof Date) {
				value = f.format(value);
			}

			int status = -1;

			String tipo_lancamento = (String) table.getValueAt(row, 2);

			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom
			}
			else{

				if (tipo_lancamento.equalsIgnoreCase("TRANSFERENCIAS")) {
					// e uma transferencia
					renderer.setBackground(new Color(51, 0, 255));
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 16));
				} else if(tipo_lancamento.equalsIgnoreCase("DESPESAS")) {
					renderer.setBackground(Color.red);
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 16));
				}else if(tipo_lancamento.equalsIgnoreCase("RECEITAS")) {
					renderer.setBackground(Color.GREEN);
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 16));
				}

			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			// return renderer;
		}
	}

}
