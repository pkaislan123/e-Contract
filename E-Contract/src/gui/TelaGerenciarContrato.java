package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.ImageIcon;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cadastros.CadastroAditivo;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroContrato.CadastroTarefa;
import cadastros.CadastroDocumento;
import cadastros.CadastroLogin;
import cadastros.CadastroModelo;
import cadastros.CadastroNFe;
import cadastros.CadastroPontuacao;
import cadastros.CadastroProduto;
import cadastros.ContaBancaria;
import cadastros.Registros;
import classesExtras.CBProdutoPersonalizado;
import classesExtras.Carregamento;
import conexaoBanco.GerenciarBancoAditivos;
import conexaoBanco.GerenciarBancoClientes;
import conexaoBanco.GerenciarBancoContratos;
import conexaoBanco.GerenciarBancoDocumento;
import conexaoBanco.GerenciarBancoLogin;
import conexaoBanco.GerenciarBancoPontuacao;
import conexaoBanco.GerenciarBancoProdutos;
import conexaoBanco.GerenciarBancoTransferencias;
import manipular.ConfiguracoesGlobais;
import manipular.ConverterPdf;
import manipular.EditarWord;
import manipular.ManipularNotasFiscais;
import manipular.ManipularTxt;
import manipular.Nuvem;
import outros.DadosGlobais;
import outros.GetData;
import outros.JPanelTransparent;
import outros.JTextFieldPersonalizado;
import relatoria.RelatorioContratoIndividual;
import tratamento_proprio.Log;
import views_personalizadas.TelaEscolha;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.ScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.PopupMenu;

import graficos.JPanelGrafico;
import graficos.JPanelGraficoPadrao;
import javax.swing.JTree;
import javax.swing.JRadioButton;

public class TelaGerenciarContrato extends JDialog {

	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	private JTabbedPane painelPrincipal = new JTabbedPane();
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelRecebimentoEntrada = new JPanel();

	private JPanel painelCarregamento = new JPanel();
	private JPanel painelListaTarefas = new JPanel();
	private JPanel painelComprovantes = new JPanel();
	private JTree arvore_documentos;
	private JTree arvore_contratos;
	private Registros.RegistroPagamento registro_pagamento_global;
	private String caminho_salvar_comprovante_pagamento;
	DefaultMutableTreeNode no_assinaturas;
	DefaultMutableTreeNode no_pagamentos;
	DefaultMutableTreeNode no_carregamentos;
	DefaultMutableTreeNode no_outros;
	DefaultMutableTreeNode no_contratos;
	DefaultMutableTreeNode no_sub_contratos;
	private DefaultMutableTreeNode no_contrato_selecionado;

	private DefaultMutableTreeNode no_selecionado;
	private final JLabel lblStatusContrato = new JLabel("Status do Contrato:");
	private final JLabel lblValorTotalPagamentos, lblTotalTransferenciasRecebidas, lblTotalTransferenciasRetiradas;
	InputStream stream = null;
	private final JButton btnEditarContrato = new JButton("Editar");
	private JPanel painel_vizualizar;
	private final JButton btnEnviarMsg = new JButton("Enviar"), btnCriarAditivo, btnRevogarAssinatura;
	private final JLabel lblNewLabel = new JLabel("     Modelos de Pagamento");
	private CadastroContrato contrato_local;
	private ArrayList<CadastroContrato> lista_sub_contratos = new ArrayList<>();
	private SwingController controller = null;
	private SwingViewBuilder factory;
	private TelaGerenciarContrato isto;
	private String servidor_unidade;

	private CadastroCliente cliente_carregamento;
	private CadastroContrato contrato_carregamento;

	private JPanel painelSubContratos = new JPanel();
	private Registros.RegistroCarregamento registro_carregamento_global;
	private CadastroNFe nota_fiscal;
	private CadastroCliente transportador = new CadastroCliente();
	private CadastroProduto produto = new CadastroProduto();
	private Carregamento carregamento_confirmar = new Carregamento();

	private JButton btnReabrir;
	private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas = null;
	private ArrayList<CadastroContrato.Carregamento> lista_carregamentos = null;
	private ArrayList<CadastroContrato.CadastroPagamentoContratual> lista_pagamentos_contratuais = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_remetente = null;
	private ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> lista_transferencias_contratuais_destinatario = null;
	private JPopupMenu jPopupMenuTabelCarregamento;
	private JPopupMenu jPopupMenuTabelPagamentos;
	private JPopupMenu jPopupMenuDocumentos;
	private JPopupMenu  jPopupMenuTabelAditivos;
	private Double peso_total_cargas_nfe = 0.0;
	private Double peso_total_cargas = 0.0;
	private JLabel lblPesoTotalRealCargas, lblPesoTotalNotasFiscais, lblPesoTotal, lblPesoTotalRealRestante,
			lblPesoTotalNotasFiscaisRestante, lblNoSelecionado;

	private JPanelGraficoPadrao painelGraficoCarregamento, painelGraficoNFs, painelGraficoPagamentos;

	private JTextArea entDescricaoDocumento;
	DefaultTableModel modelo = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_aditivos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_sub_contratos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_tarefas = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_carregamentos = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	DefaultTableModel modelo_pagamentos_contratuais = new DefaultTableModel() {
		public boolean isCellEditable(int linha, int coluna) {
			return false;
		}
	};

	private final JLabel lblNewLabel_1 = new JLabel(
			"*Pagamentos apenas informativos, assim como elaborados no contrato");

	private final JLabel lblTipoContrato = new JLabel("Tipo Contrato:");
	private final JButton btnExcluirContrato = new JButton("Excluir");
	private JTable table_tarefas;
	private final JButton btnAdicionarCarregamento = new JButton("Adicionar");
	private JButton btnAssinarContrato, btnConcluir;
	private final JScrollPane scrollPaneCarregamento;
	private final JLabel lblNewLabel_4 = new JLabel("     Carregamento");
	private JTable table_carregamento;

	private JLabel lblDataContrato, lblCorretor, lblCompradores, lblVendedores, lblValorSaco, lblQuantidade,
			lblValorTotal, lblSafra, lblProduto;
	private JTable table_pagamentos_contratuais;

	private JLabel lblTotalPagamentosRestantes, lblTotalPagamentosEfetuados, lblTotalPagamentos;

	private PainelInformativo painel_informacoes_tab_principal, painel_informacoes_tab_pagamentos;
	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private JComboBox cBTipoDocumento;
	private JTable table_aditivos;
	private ArrayList<CadastroAditivo> lista_aditivos = new ArrayList<>();

	public TelaGerenciarContrato(CadastroContrato contrato) {

		getDadosGlobais();
		servidor_unidade = configs_globais.getServidorUnidade();

		setModal(true);
		// setAlwaysOnTop(true);

		contrato_local = contrato;
		isto = this;

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setarTituloJanela();

		setBounds(100, 100, 1371, 722);
		painelPrincipal.setBackground(new Color(255, 255, 255));
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelPrincipal = new JTabbedPane();

		// contentPanel.setBackground(new Color(255, 255, 255));
		// contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPanel);
		// contentPanel.setLayout(null);

		painelDadosIniciais.setBackground(new Color(255, 255, 255));

		// adiciona novos paines e suas abas
		painelPrincipal.addTab("Contrato", painelDadosIniciais);
		painelDadosIniciais.setLayout(null);
		painelRecebimentoEntrada.setBackground(Color.WHITE);
		
	
		
		painelDadosIniciais.setLayout(null);

		if (contrato.getSub_contrato() == 0) {
			// não é um subcontrato
			// criarAbaSubContrato();
			painelSubContratos.setBackground(new Color(255, 255, 255));
			painelPrincipal.addTab("Sub-Contratos", painelSubContratos);
			painelSubContratos.setLayout(null);
			criarAbaSubContrato();

		}

		lblStatusContrato.setBackground(new Color(0, 128, 128));
		lblStatusContrato.setOpaque(true);
		lblStatusContrato.setForeground(Color.WHITE);
		lblStatusContrato.setFont(new Font("Arial", Font.BOLD, 18));
		lblStatusContrato.setBounds(554, 76, 470, 35);

		painelDadosIniciais.add(lblStatusContrato);
		btnEditarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// argumentos(CadastroModelo modelo, int tipoContrato, CadastroContrato
				// contrato_pai, int flag_edicao) {
				// TelaEscolhaTipoNovoContrato(int tipoContrato, CadastroContrato contrato_pai,
				// int flag_edicao) {

				fecharDocumento();
				DadosGlobais dados = DadosGlobais.getInstance();
				dados.setTeraGerenciarContratoPai(isto);
				if (contrato.getSub_contrato() == 0) {
					// e um contrato pai, abre a tela em modo de edicao
					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato(0, contrato_local, 1);
				} else {
					// e um subcontrato, o tipo do contrato e 1, e entra no modo de edicao
					TelaEscolhaTipoNovoContrato tela = new TelaEscolhaTipoNovoContrato(1, contrato_local, 1);

				}
			}
		});

		
		//adiciona o painel de recebimento
		painelPrincipal.addTab("Recebimento de Entrada", painelRecebimentoEntrada);
		painelRecebimentoEntrada.setLayout(null);
		
		JLabel lblNewLabel_4_1 = new JLabel("     Recebimento");
		lblNewLabel_4_1.setOpaque(true);
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_4_1.setBounds(0, 22, 158, 31);
		painelRecebimentoEntrada.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_28 = new JLabel("");
		lblNewLabel_28.setIcon(new ImageIcon(TelaGerenciarContrato.class.getResource("/imagens/icone_caminhao_descarregando4.png")));
		lblNewLabel_28.setBounds(30, 52, 183, 77);
		painelRecebimentoEntrada.add(lblNewLabel_28);
		
		btnEditarContrato.setBounds(331, 497, 89, 23);

		painelDadosIniciais.add(btnEditarContrato);

		GetData data = new GetData();

		getContentPane().add(painelPrincipal, BorderLayout.CENTER);

		btnEnviarMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaEscolha escolher = new TelaEscolha(1, contrato, null);
			}
		});

		btnEnviarMsg.setBounds(232, 497, 89, 23);

		painelDadosIniciais.add(btnEnviarMsg);
		lblTipoContrato.setOpaque(true);
		lblTipoContrato.setForeground(Color.WHITE);
		lblTipoContrato.setFont(new Font("Arial", Font.BOLD, 16));
		lblTipoContrato.setBackground(new Color(102, 0, 102));
		lblTipoContrato.setBounds(554, 122, 470, 29);

		painelDadosIniciais.add(lblTipoContrato);
		btnExcluirContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean permitir_exclusao = false;
				// Excluir o contrato
				// verificar se ha pagamentos contratuais relacionados a este contrato
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				ArrayList<CadastroContrato.CadastroPagamentoContratual> pagamentos = gerenciar
						.getPagamentosContratuais(contrato_local.getId());

				if (pagamentos.size() > 0) {
					permitir_exclusao = false;
					JOptionPane.showMessageDialog(null,
							"Exclusão não permitida!\nHá pagamentos contratuais adicionados a este contrato\nExclua os antes de tentar a exclusão!");
				} else {
					permitir_exclusao = true;

					// verificar se ha carregamentos relacionados a este contrato
					ArrayList<CadastroContrato.Carregamento> carregamentos = gerenciar
							.getCarregamentos(contrato_local.getId());
					if (carregamentos.size() > 0) {
						permitir_exclusao = false;
						JOptionPane.showMessageDialog(null,
								"Exclusão não permitida!\nHá carregamentos adicionados a este contrato\nExclua os antes de tentar a exclusão!");
					} else {
						permitir_exclusao = true;
						// verificar se ha subcontratos para este contrato

						ArrayList<CadastroContrato> sub_contratos = gerenciar.getSubContratos(contrato_local.getId());
						if (sub_contratos.size() > 0) {
							permitir_exclusao = false;
							JOptionPane.showMessageDialog(null,
									"Exclusão não permitida!\nHá sub-contratos adicionados a este contrato\nExclua os antes de tentar a exclusão!");
						} else {
							permitir_exclusao = true;
							// JOptionPane.showMessageDialog(null, "Exclusão permitida!");

							excluir_contrato();

						}

					}

				}

			}
		});
		btnExcluirContrato.setBounds(133, 497, 89, 23);

		painelDadosIniciais.add(btnExcluirContrato);

		btnAssinarContrato = new JButton("Assinar");
		btnAssinarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				assinar();
			}
		});
		btnAssinarContrato.setBounds(34, 497, 89, 23);
		painelDadosIniciais.add(btnAssinarContrato);

		JButton btnVizualizar = new JButton("Vizualizar");
		btnVizualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vizualizarContrato();
			}
		});
		btnVizualizar.setBounds(426, 494, 90, 28);
		painelDadosIniciais.add(btnVizualizar);

		btnCriarAditivo = new JButton("Criar Aditivo");
		btnCriarAditivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCriarAditivo tela = new TelaCriarAditivo(contrato_local);

				tela.setTelaPai(isto);
				tela.setVisible(true);

			}
		});
		btnCriarAditivo.setBounds(640, 178, 136, 23);
		painelComprovantes.add(btnCriarAditivo);

		JPanel panelInformativoPrincipal = new JPanel();
		painel_informacoes_tab_principal = new PainelInformativo();
		panelInformativoPrincipal.add(painel_informacoes_tab_principal);
		panelInformativoPrincipal.setBounds(554, 162, 470, 200);
		painelDadosIniciais.add(panelInformativoPrincipal);
		panelInformativoPrincipal.setLayout(null);

		btnRevogarAssinatura = new JButton("Revogar");
		btnRevogarAssinatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revogarAssinatura();
			}
		});
		btnRevogarAssinatura.setBounds(34, 497, 89, 23);
		painelDadosIniciais.add(btnRevogarAssinatura);

		btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				concluir_contrato();

			}
		});
		btnConcluir.setBounds(427, 533, 89, 23);
		painelDadosIniciais.add(btnConcluir);

		btnReabrir = new JButton("Desbloquear");
		btnReabrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(isto, "Deseja desbloquear o contrato?", "Desbloquear Aditivo",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					boolean atualizou = gerenciar.atualizarStatusContrato(contrato_local.getId(), 2);
					if (atualizou) {
						JOptionPane.showMessageDialog(null, "Contrato Desbloqueado!");
						//retirar pontucao
						GerenciarBancoPontuacao gerenciar_pontuacao = new GerenciarBancoPontuacao();


							// primeiro verifica se existe uma pontuacao para esta combinacao contrato
							// cliente
							ArrayList<CadastroPontuacao> lista_pontuacao = gerenciar_pontuacao
									.getPontuacaoPorContrato(contrato_local.getId());
							if (lista_pontuacao.size() > 0) {
								for (CadastroPontuacao pontos_antigos : lista_pontuacao) {

									boolean removido = gerenciar_pontuacao
											.removerPontuacao(pontos_antigos.getId_pontuacao());
									if (removido) {
									} else {
										JOptionPane.showMessageDialog(null,
												"Erro ao remover pontuacao antiga\nConsulte o administrador!");
										break;
									}

								}
							}
						
						
						
						setarInformacoesPainelPrincipal();
						destravarContrato();
					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao desbloquear o contrato, tente novamente!\nSe o erro persistir, consulte o administrador");
					}

				} else {

				}

			}
		});
		btnReabrir.setBounds(426, 568, 100, 28);
		painelDadosIniciais.add(btnReabrir);
		
		JButton btnNewButton = new JButton("Visão Geral");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaVisaoGeralContrato tela = new TelaVisaoGeralContrato(contrato_local);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBounds(934, 385, 92, 28);
		painelDadosIniciais.add(btnNewButton);

		modelo.addColumn("Id Pagamento");
		modelo.addColumn("Id Conta");
		modelo.addColumn("CPF");
		modelo.addColumn("Nome");

		modelo.addColumn("Banco");

		modelo.addColumn("Codigo");
		modelo.addColumn("Agência");

		modelo.addColumn("Conta");
		modelo.addColumn("Valor");
		modelo.addColumn("Antecipado");
		modelo.addColumn("Cobre");

		modelo.addColumn("Data Pagamento");

		modelo_tarefas.addColumn("Id Tarefas");
		modelo_tarefas.addColumn("Status");

		modelo_tarefas.addColumn("Nome");
		modelo_tarefas.addColumn("Descrição");
		modelo_tarefas.addColumn("Mensagem");
		modelo_tarefas.addColumn("Resposta");

		modelo_tarefas.addColumn("Data");

		modelo_tarefas.addColumn("Hora");
		modelo_tarefas.addColumn("Criador");

		modelo_tarefas.addColumn("Executor");

		modelo_tarefas.addColumn("Hora Agendada");
		modelo_tarefas.addColumn("Data Agendada");
		modelo_tarefas.addColumn("Prioridade");

		painelCarregamento.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Carregamento de Saída", painelCarregamento);
		painelCarregamento.setLayout(null);

		modelo_carregamentos.addColumn("Id Carregamento");
		modelo_carregamentos.addColumn("Data");
		modelo_carregamentos.addColumn("Contrato Destinado");
		modelo_carregamentos.addColumn("Cliente");
		modelo_carregamentos.addColumn("Vendedor");

		modelo_carregamentos.addColumn("Transportador");
		modelo_carregamentos.addColumn("Veiculo");
		modelo_carregamentos.addColumn("Produto");
		modelo_carregamentos.addColumn("Peso Carga");
		modelo_carregamentos.addColumn("Peso Nota");
		modelo_carregamentos.addColumn("Peso Restante Nota");
		modelo_carregamentos.addColumn("Nota Fiscal");
		modelo_carregamentos.addColumn("Caminho Nota Fiscal");

		lblPesoTotalRealCargas = new JLabel("");
		lblPesoTotalRealCargas.setBounds(111, 451, 193, 23);
		painelCarregamento.add(lblPesoTotalRealCargas);
		lblPesoTotalRealCargas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealCargas.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_3 = new JLabel("Cargas:");
		lblNewLabel_3.setBounds(44, 382, 76, 23);
		painelCarregamento.add(lblNewLabel_3);

		JLabel lblNewLabel_13 = new JLabel("Total Carregado:");
		lblNewLabel_13.setBounds(10, 457, 101, 14);
		painelCarregamento.add(lblNewLabel_13);

		lblPesoTotal = new JLabel("0.0 KG");
		lblPesoTotal.setBounds(111, 416, 193, 23);
		painelCarregamento.add(lblPesoTotal);
		lblPesoTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotal.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_13_1 = new JLabel("Restante:");
		lblNewLabel_13_1.setBounds(46, 493, 65, 14);
		painelCarregamento.add(lblNewLabel_13_1);

		lblPesoTotalRealRestante = new JLabel("0.0 Kg");
		lblPesoTotalRealRestante.setBounds(111, 487, 193, 23);
		painelCarregamento.add(lblPesoTotalRealRestante);
		lblPesoTotalRealRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalRealRestante.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblNewLabel_12 = new JLabel("Total:");
		lblNewLabel_12.setBounds(53, 422, 46, 14);
		painelCarregamento.add(lblNewLabel_12);

		table_carregamento = new JTable(modelo_carregamentos);
		table_carregamento.setBackground(Color.WHITE);

		setMenuCarregamento();

		table_carregamento.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuTabelCarregamento.show(table_carregamento, e.getX(), e.getY());
				}
			}
		});

		scrollPaneCarregamento = new JScrollPane(table_carregamento);
		scrollPaneCarregamento.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCarregamento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCarregamento.setBackground(Color.WHITE);
		scrollPaneCarregamento.setAutoscrolls(true);
		scrollPaneCarregamento.setBounds(45, 232, 1304, 139);

		painelCarregamento.add(scrollPaneCarregamento);

		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_4.setBackground(new Color(0, 51, 0));
		lblNewLabel_4.setBounds(0, 22, 158, 31);

		painelCarregamento.add(lblNewLabel_4);

		JLabel lblNewLabel_7 = new JLabel("Carregamentos desse contrato:");
		lblNewLabel_7.setBounds(71, 207, 278, 14);
		painelCarregamento.add(lblNewLabel_7);

		JButton btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(isto, "Excluir", "Deseja excluir o carregamento?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					try {
						int indiceDaLinha = table_carregamento.getSelectedRow();

						int id_carregamento_selecionado = Integer
								.parseInt(table_carregamento.getValueAt(indiceDaLinha, 0).toString());
						GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

						if (gerenciar.removerCarregamento(contrato_local.getId(), id_carregamento_selecionado)) {
							JOptionPane.showMessageDialog(null, "Carregamento Excluido!");
							pesquisar_carregamentos();

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao remover o carregamento selecionado\nConsulte o administrador do sistema!");
						}
					} catch (NumberFormatException n) {
						JOptionPane.showMessageDialog(null, "Nenhum carregamento selecionado para excluir");

					}

				} else {

				}

			}
		});
		btnNewButton_1.setBounds(1226, 382, 123, 23);
		painelCarregamento.add(btnNewButton_1);
		btnAdicionarCarregamento.setBounds(1226, 602, 123, 23);
		painelCarregamento.add(btnAdicionarCarregamento);

		JLabel lblNewLabel_3_1 = new JLabel("Notas Fiscais:");
		lblNewLabel_3_1.setBounds(639, 382, 79, 16);
		painelCarregamento.add(lblNewLabel_3_1);

		lblPesoTotalNotasFiscais = new JLabel("");
		lblPesoTotalNotasFiscais.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalNotasFiscais.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalNotasFiscais.setBounds(721, 413, 185, 23);
		painelCarregamento.add(lblPesoTotalNotasFiscais);

		JPanel lbl_produto = new JPanel();
		lbl_produto.setBackground(Color.WHITE);
		lbl_produto.setBounds(372, 60, 772, 139);
		painelCarregamento.add(lbl_produto);
		lbl_produto.setLayout(
				new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px][]"));

		JLabel lblNewLabel_5 = new JLabel("Data:");
		lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
		lblNewLabel_5.setBorder(null);
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_5, "cell 0 0,alignx right,aligny center");

		lblDataContrato = new JLabel("data contrato");
		lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblDataContrato, "cell 1 0,alignx center,aligny center");

		JLabel lblNewLabel_9 = new JLabel("Corretor:");
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_9, "cell 0 1,alignx right,growy");

		lblCorretor = new JLabel("corretor");
		lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblCorretor, "cell 1 1,alignx center,aligny center");

		JLabel lblNewLabel_6 = new JLabel("Compradores:");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_6, "cell 0 2,alignx right,growy");

		lblCompradores = new JLabel("compradores");
		lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblCompradores, "cell 1 2,alignx center,aligny center");

		JLabel lblNewLabel_8 = new JLabel("Vendedores:");
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_8, "cell 0 3,alignx right,aligny center");

		lblVendedores = new JLabel("vendedores");
		lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblVendedores, "cell 1 3,alignx center,aligny center");

		JLabel lblnewvalorsaco = new JLabel("Valor Unidade:");
		lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblnewvalorsaco, "flowy,cell 0 4,alignx right,aligny center");

		lblValorSaco = new JLabel("valor saco");
		lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblValorSaco, "cell 1 4,alignx center,aligny center");

		JLabel lblNewLabel_10 = new JLabel("Quantidade:");
		lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_10, "cell 2 4,alignx right,aligny center");

		lblQuantidade = new JLabel("quantidade");
		lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblQuantidade, "cell 3 4,alignx center,aligny center");

		JLabel lblNewLabel_11 = new JLabel("Valor Total:");
		lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblNewLabel_11, "cell 4 4,alignx right,aligny center");

		lblValorTotal = new JLabel("valor total do pagamento do contrato longo");
		lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblValorTotal, "cell 5 4,alignx center,aligny center");

		JLabel lblProdutonm = new JLabel("Produto:");
		lblProdutonm.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblProdutonm, "cell 0 5,alignx right");

		lblProduto = new JLabel("valor produto");
		lblProduto.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblProduto, "cell 1 5,alignx center");

		JLabel lblSafran = new JLabel("Safra:");
		lblSafran.setFont(new Font("Arial", Font.PLAIN, 12));
		lbl_produto.add(lblSafran, "cell 2 5,alignx right");

		lblSafra = new JLabel("valor produto");
		lblSafra.setFont(new Font("Arial", Font.BOLD, 14));
		lbl_produto.add(lblSafra, "cell 3 5,alignx center");

		JLabel lblNewLabel_13_2 = new JLabel("Total Carregado:");
		lblNewLabel_13_2.setBounds(626, 415, 92, 16);
		painelCarregamento.add(lblNewLabel_13_2);

		JLabel lblNewLabel_13_2_1 = new JLabel("Restante:");
		lblNewLabel_13_2_1.setBounds(665, 448, 53, 16);
		painelCarregamento.add(lblNewLabel_13_2_1);

		lblPesoTotalNotasFiscaisRestante = new JLabel("0 Kg | 0 Sacos");
		lblPesoTotalNotasFiscaisRestante.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesoTotalNotasFiscaisRestante.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPesoTotalNotasFiscaisRestante.setBounds(721, 447, 185, 23);
		painelCarregamento.add(lblPesoTotalNotasFiscaisRestante);

		painelGraficoCarregamento = new JPanelGraficoPadrao(0, 0, "Carregado:", "a Carregar:");
		painelGraficoCarregamento.setLayout(null);
		painelGraficoCarregamento.setBounds(304, 375, 317, 250);
		painelCarregamento.add(painelGraficoCarregamento);

		painelGraficoNFs = new JPanelGraficoPadrao(0, 0, "Total NF:", "Falta Tirar:");
		painelGraficoNFs.setLayout(null);
		painelGraficoNFs.setBounds(916, 382, 300, 250);
		painelCarregamento.add(painelGraficoNFs);
		
		JLabel lblNewLabel_29 = new JLabel("");
		lblNewLabel_29.setIcon(new ImageIcon(TelaGerenciarContrato.class.getResource("/imagens/icone_caminhao_carregado2.png")));
		lblNewLabel_29.setBounds(87, 48, 130, 97);
		painelCarregamento.add(lblNewLabel_29);

		painelPagamentos.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(null);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(0, 51, 0));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setBounds(0, 22, 230, 31);

		painel_informacoes_tab_pagamentos = new PainelInformativo();
		JPanel panelInformativoAbaPagamentos = new JPanel();
		panelInformativoAbaPagamentos.setBounds(843, 53, 445, 200);
		panelInformativoAbaPagamentos.add(painel_informacoes_tab_pagamentos);
		painelPagamentos.add(panelInformativoAbaPagamentos);
		panelInformativoAbaPagamentos.setLayout(null);

		JLabel lblNewLabel_14_2 = new JLabel("Restante:");
		lblNewLabel_14_2.setBounds(135, 603, 68, 14);
		painelPagamentos.add(lblNewLabel_14_2);

		lblTotalPagamentosEfetuados = new JLabel("");
		lblTotalPagamentosEfetuados.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentosEfetuados.setBounds(199, 487, 143, 23);
		painelPagamentos.add(lblTotalPagamentosEfetuados);

		JLabel lblNewLabel_14_1 = new JLabel("Efetuados:");
		lblNewLabel_14_1.setBounds(125, 493, 68, 14);
		painelPagamentos.add(lblNewLabel_14_1);

		lblTotalPagamentos = new JLabel("");
		lblTotalPagamentos.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentos.setBounds(199, 453, 143, 23);
		painelPagamentos.add(lblTotalPagamentos);

		JLabel lblNewLabel_14 = new JLabel("Total:");
		lblNewLabel_14.setBounds(148, 462, 45, 14);
		painelPagamentos.add(lblNewLabel_14);

		lblTotalPagamentosRestantes = new JLabel("");
		lblTotalPagamentosRestantes.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalPagamentosRestantes.setBounds(199, 594, 143, 23);
		painelPagamentos.add(lblTotalPagamentosRestantes);

		painelPagamentos.add(lblNewLabel);

		JTable tabela_modelo_pagamentos = new JTable(modelo);
		// tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_modelo_pagamentos.setBackground(new Color(255, 255, 255));

		tabela_modelo_pagamentos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela_modelo_pagamentos.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela_modelo_pagamentos.getColumnModel().getColumn(2).setPreferredWidth(170);
		tabela_modelo_pagamentos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabela_modelo_pagamentos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabela_modelo_pagamentos.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela_modelo_pagamentos.getColumnModel().getColumn(6).setPreferredWidth(70);
		tabela_modelo_pagamentos.getColumnModel().getColumn(7).setPreferredWidth(90);
		tabela_modelo_pagamentos.getColumnModel().getColumn(8).setPreferredWidth(90);

		JScrollPane scrollPane = new JScrollPane(tabela_modelo_pagamentos);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(38, 85, 721, 132);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(new Color(255, 255, 255));
		painelPagamentos.add(scrollPane);
		lblNewLabel_1.setForeground(new Color(0, 51, 0));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.ORANGE);
		lblNewLabel_1.setBounds(314, 60, 445, 14);

		painelPagamentos.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Valor Total dos pagamentos:");
		lblNewLabel_2.setBounds(495, 238, 166, 14);
		painelPagamentos.add(lblNewLabel_2);

		lblValorTotalPagamentos = new JLabel("");
		lblValorTotalPagamentos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorTotalPagamentos.setBounds(658, 228, 101, 31);
		painelPagamentos.add(lblValorTotalPagamentos);

		JLabel lblNewLabel_7_1 = new JLabel("Pagamentos desse contrato:");
		lblNewLabel_7_1.setBounds(125, 264, 278, 14);
		painelPagamentos.add(lblNewLabel_7_1);

		table_pagamentos_contratuais = new JTable(modelo_pagamentos_contratuais);

		setMenuPagamentos();

		table_pagamentos_contratuais.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent t) {
				// Se o botão direito do mouse foi pressionado
				if (t.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuTabelPagamentos.show(table_pagamentos_contratuais, t.getX(), t.getY());
				}
			}
		});

		modelo_pagamentos_contratuais.addColumn("Id Pagamento");
		modelo_pagamentos_contratuais.addColumn("Descrição");

		modelo_pagamentos_contratuais.addColumn("Data");

		modelo_pagamentos_contratuais.addColumn("Valor");
		modelo_pagamentos_contratuais.addColumn("Depositante");
		modelo_pagamentos_contratuais.addColumn("Conta Depositante");
		modelo_pagamentos_contratuais.addColumn("Favorecido");
		modelo_pagamentos_contratuais.addColumn("Conta Favorecido");

		JScrollPane scrollPanePagamentos = new JScrollPane(table_pagamentos_contratuais);
		scrollPanePagamentos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanePagamentos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanePagamentos.setAutoscrolls(true);
		scrollPanePagamentos.setBackground(new Color(255, 255, 255));
		scrollPanePagamentos.setBounds(125, 289, 976, 111);
		painelPagamentos.add(scrollPanePagamentos);

		JButton btnExcluirPagamento = new JButton("Excluir");
		btnExcluirPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					int indiceDaLinha = table_pagamentos_contratuais.getSelectedRow();

					int id_pagamento_selecionado = Integer
							.parseInt(table_pagamentos_contratuais.getValueAt(indiceDaLinha, 0).toString());

					String descricao = table_pagamentos_contratuais.getValueAt(indiceDaLinha, 1).toString();

					if (!descricao.equalsIgnoreCase("+Transferencia")
							&& !descricao.equalsIgnoreCase("-Transferencia")) {

						if (JOptionPane.showConfirmDialog(isto, "Excluir Pagamento?", "Deseja excluir o pagamento?",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

							GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();

							// verifica se esse pagamento esta anexado a uma transferencia
							GerenciarBancoTransferencias gerenciar_trans = new GerenciarBancoTransferencias();
							boolean tem_transferencia_anexada = false;
							ArrayList<CadastroContrato.CadastroTransferenciaPagamentoContratual> transferencias = gerenciar_trans
									.getTransferencias();
							for (CadastroContrato.CadastroTransferenciaPagamentoContratual trans : transferencias) {
								if (trans.getId_pagamento_contratual() == id_pagamento_selecionado) {
									tem_transferencia_anexada = true;
									break;
								}

							}

							if (contrato_local.getSub_contrato() == 1) {

								// excluiir somente a relacao
								if (gerenciar.remover_contrato_pagamento_contratual(contrato_local.getId(),
										id_pagamento_selecionado)) {
									JOptionPane.showMessageDialog(null, "Pagamento Excluido!");

									pesquisar_pagamentos();

								} else {
									JOptionPane.showMessageDialog(null,
											"Erro ao remover o relação do pagamento selecionado\nConsulte o administrador do sistema!");
								}

							} else {
								if (!tem_transferencia_anexada) {
									// excluiir somente a relacao
									if (gerenciar.removerPagamentoContratual(contrato_local.getId(),
											id_pagamento_selecionado)) {
										JOptionPane.showMessageDialog(null, "Pagamento Excluido!");

										boolean tem_replica = false;
										// verifica se esse pagamento esta replicado
										Map<Integer, Integer> pags = gerenciar
												.getRelacaoReplica(id_pagamento_selecionado);
										if (pags.size() > 0) {
											// este pagamento esta replicado
											tem_replica = true;

										}

										if (tem_replica) {
											// apagar a relacao
											for (Map.Entry<Integer, Integer> pair : pags.entrySet()) {

												System.out.println(pair.getKey());
												System.out.println(pair.getValue());

												boolean remover_replica = gerenciar
														.removerPagamentoContratual(pair.getKey(), pair.getValue());
												if (remover_replica) {
													JOptionPane.showMessageDialog(null,
															"A replica deste pagamento foi excluida para manter a coesão das operações");

												} else {
													JOptionPane.showMessageDialog(null,
															"Erro ao excluir a replica deste pagamento\nBanco de Dados Corrompido\nConsulte o administrador!");
												}

											}

										} else {

										}

										pesquisar_pagamentos();

									} else {
										JOptionPane.showMessageDialog(null,
												"Erro ao remover o pagamento selecionado\nConsulte o administrador do sistema!");
									}
								} else {
									JOptionPane.showMessageDialog(null,
											"Esse pagamento tem uma tranferencia anexada a ele, remova ha antes!");
								}
							}

						} else {

						}
					}

					else if (descricao.equalsIgnoreCase("-Transferencia")) {
						if (JOptionPane.showConfirmDialog(isto, "Excluir Transferencia?",
								"Deseja excluir essa transferencia?", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							// excluir transferencia
							int id_transferencia_selecionada = Integer
									.parseInt(table_pagamentos_contratuais.getValueAt(indiceDaLinha, 0).toString());
							GerenciarBancoTransferencias gerenciar = new GerenciarBancoTransferencias();

							if (gerenciar.removerTransferencia(id_transferencia_selecionada)) {
								JOptionPane.showMessageDialog(null, "Transferencia removida!");
								pesquisar_pagamentos();

							} else {
								JOptionPane.showMessageDialog(null,
										"Erro ao remover a transferencia selecionada\nConsulte o administrador do sistema!");
							}
						} else {

						}
					}

					else {
						JOptionPane.showMessageDialog(null, "Remova essa transfência no contrato de origem");
					}
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Nenhum pagamento selecionado para excluir");

				}

			}
		});
		btnExcluirPagamento.setBounds(1012, 411, 89, 23);
		painelPagamentos.add(btnExcluirPagamento);

		JButton btnAdicionarPagamento = new JButton("Novo Pagamento");
		btnAdicionarPagamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConfirmarPagamentoContratual tela_confirmar = new TelaConfirmarPagamentoContratual(contrato_local);
				tela_confirmar.setTelaPai(isto);
				tela_confirmar.setVisible(true);
			}
		});
		btnAdicionarPagamento.setBounds(948, 540, 153, 23);
		painelPagamentos.add(btnAdicionarPagamento);

		painelGraficoPagamentos = new JPanelGraficoPadrao(0, 0, "Pago: ", "a Pagar: ");
		painelGraficoPagamentos.setLayout(null);
		painelGraficoPagamentos.setBounds(344, 404, 300, 250);
		painelPagamentos.add(painelGraficoPagamentos);

		lblTotalTransferenciasRetiradas = new JLabel("");
		lblTotalTransferenciasRetiradas.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalTransferenciasRetiradas.setBounds(199, 519, 143, 23);
		painelPagamentos.add(lblTotalTransferenciasRetiradas);

		JLabel lblNewLabel_14_1_1 = new JLabel("Transferencias:(-)");
		lblNewLabel_14_1_1.setBounds(92, 525, 101, 14);
		painelPagamentos.add(lblNewLabel_14_1_1);

		lblTotalTransferenciasRecebidas = new JLabel("");
		lblTotalTransferenciasRecebidas.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotalTransferenciasRecebidas.setBounds(199, 553, 143, 23);
		painelPagamentos.add(lblTotalTransferenciasRecebidas);

		JLabel lblNewLabel_14_1_1_1 = new JLabel("Transferencias:(+)");
		lblNewLabel_14_1_1_1.setBounds(92, 559, 101, 14);
		painelPagamentos.add(lblNewLabel_14_1_1_1);

		painelListaTarefas.setBackground(new Color(255, 255, 255));

		painelPrincipal.addTab("Lista Tarefas", painelListaTarefas);
		painelListaTarefas.setLayout(null);

		table_tarefas = new JTable(modelo_tarefas);
		table_tarefas.setBackground(Color.WHITE);

		table_tarefas.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_tarefas.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_tarefas.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_tarefas.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_tarefas.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_tarefas.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_tarefas.getColumnModel().getColumn(6).setPreferredWidth(70);
		table_tarefas.getColumnModel().getColumn(7).setPreferredWidth(90);

		JScrollPane scrollPaneTarefas = new JScrollPane(table_tarefas);
		scrollPaneTarefas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setBackground(Color.WHITE);
		scrollPaneTarefas.setAutoscrolls(true);
		scrollPaneTarefas.setBounds(28, 55, 1310, 351);
		painelListaTarefas.add(scrollPaneTarefas);

		JButton btnAdcionarTarefa = new JButton("Adicionar");
		btnAdcionarTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCriarTarefa tarefa = new TelaCriarTarefa(contrato_local);
				tarefa.setTelaPai(isto);
				tarefa.setVisible(true);
			}
		});
		btnAdcionarTarefa.setBounds(1249, 430, 79, 28);
		painelListaTarefas.add(btnAdcionarTarefa);

		JButton btnExcluirTarefa = new JButton("Excluir");
		btnExcluirTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int indiceDaLinha = 0;
				indiceDaLinha = table_tarefas.getSelectedRow();
				
				CadastroTarefa tarefa_selecionada = lista_tarefas.get(indiceDaLinha);
				JOptionPane.showMessageDialog(null, "Id da tarefa selecionada: " + tarefa_selecionada.getId_tarefa());
				
				if(login.getId() == tarefa_selecionada.getCriador().getId()) {
					//verifica se a tarefa ja foi concluida
					if(tarefa_selecionada.getStatus_tarefa() != 1) {
						if (JOptionPane.showConfirmDialog(isto, 
					            "Deseja excluir a tarefa selecionada", "Excluir Tarefa", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							
							
							GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
							//remover relacao contrato tarefa
							boolean remover_relacao = gerenciar.remover_contrato_tarefa(contrato_local.getId(), tarefa_selecionada.getId_tarefa());
							if(remover_relacao) {
								boolean remover_tarefa = gerenciar.remover_tarefa(tarefa_selecionada.getId_tarefa());
								if(remover_tarefa) {
									JOptionPane.showMessageDialog(null, "Tarefa Excluida com sucesso");
									getTarefas();

								}else {
									JOptionPane.showMessageDialog(null, "Erro ao a tarefa!\nBanco Corrompido\nConsulte o administrador");
									getTarefas();

								}
								
							}else {
								JOptionPane.showMessageDialog(null, "Erro ao remover tarefa!\nConsulte o administrador");
							}
							
							
							
						}else {
							
						}

					}else {
						JOptionPane.showMessageDialog(null, "Uma tarefa já concluida não pode ser excluida");

					}

				}else {
					JOptionPane.showMessageDialog(null, "Apenas o criador da tarefa pode excluí-la!");
				}
				
			}
		});
		btnExcluirTarefa.setBounds(1072, 430, 64, 28);
		painelListaTarefas.add(btnExcluirTarefa);
		
		JButton btnResponder = new JButton("Responder");
		btnResponder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indiceDaLinha = 0;
				indiceDaLinha = table_tarefas.getSelectedRow();
				
				CadastroTarefa tarefa_selecionada = lista_tarefas.get(indiceDaLinha);
				JOptionPane.showMessageDialog(null, "Id da tarefa selecionada: " + tarefa_selecionada.getId_tarefa());
				
				if(tarefa_selecionada.getExecutor().getId() == login.getId()) {
					
					if(tarefa_selecionada.getStatus_tarefa() == 2) {
					
					JOptionPane.showMessageDialog(null, "Uma tarefa após ser respondida não pode ser editada nem excluida!");
					TelaCriarTarefaResposta responder = new TelaCriarTarefaResposta(tarefa_selecionada);
					responder.setTelaPai(isto);
					responder.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Tarefa já concluida");
 
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Você só pode responder tarefas designadas a seu usuário!");

				}
				
			}
		});
		btnResponder.setBounds(1148, 430, 90, 28);
		painelListaTarefas.add(btnResponder);

		painelPrincipal.addTab("Documentos, Aditivos e Relatórios", painelComprovantes);
		painelComprovantes.setLayout(null);

		btnAdicionarCarregamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaConfirmarCarregamento tela_confirmar = new TelaConfirmarCarregamento(contrato_local);
				tela_confirmar.setTelaPai(isto);
				tela_confirmar.setVisible(true);

			}
		});

		setPagamentos(contrato);

		if (contrato_local.getSub_contrato() == 1) {
			// é um sub contrato
			lblTipoContrato.setText("Tipo Contrato: Sub-Contrato");
		} else {
			lblTipoContrato.setText("Tipo Contrato: Contrato Original");

		}

		if (contrato_local.getSub_contrato() == 3) {
			// nao permitir edica
			lblTipoContrato.setText("Tipo Contrato: Contrato Original de Terceiros");

			btnEditarContrato.setEnabled(false);
			btnEditarContrato.setVisible(false);

			// desativar aba de subcontratos

		}

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);
		getTarefas();

		
		 setarInformacoesPainelPrincipal(); setarInformacoesPainelCarregamentos();
		 pesquisar_carregamentos(); pesquisar_pagamentos();
		 
		
		
		setSubContratos(contrato_local);
		if (contrato_local.getSub_contrato() == 0) {
			setarPainelGanhosPotenciais();

		}
		setInformacoesDocumentos();

		setInformacoesAditivos();
		
		travarContrato();
	
		this.setLocationRelativeTo(null);

		this.setVisible(true);

	}

	public void getTarefas() {
		modelo_tarefas.setNumRows(0);

		if (lista_tarefas != null) {
			lista_tarefas.clear();
		} else {
			lista_tarefas = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_tarefas = gerenciar.getTarefas(contrato_local.getId());

		/*
		 * modelo_tarefas.addColumn("Id Tarefas"); modelo_tarefas.addColumn("Status");
		 * 
		 * modelo_tarefas.addColumn("Nome"); modelo_tarefas.addColumn("Descrição");
		 * modelo_tarefas.addColumn("Mensagem");
		 * 
		 * modelo_tarefas.addColumn("Data");
		 * 
		 * 
		 * modelo_tarefas.addColumn("Hora"); modelo_tarefas.addColumn("Criador");
		 * 
		 * modelo_tarefas.addColumn("Executor");
		 * 
		 * modelo_tarefas.addColumn("Hora Agendada");
		 * modelo_tarefas.addColumn("Data Agendada");
		 * modelo_tarefas.addColumn("Prioridade");
		 * 
		 */
		for (CadastroContrato.CadastroTarefa tarefa : lista_tarefas) {

			String status_tarefa = "";
			String prioridade = "";
			String resposta = "";

			
			if (tarefa.getStatus_tarefa() == 1) {
				status_tarefa = "Concluida";
				resposta = tarefa.getResposta();
			} else if (tarefa.getStatus_tarefa() == 2) {
				status_tarefa = "Em Andamento";
				 resposta = "Executor da tarefa ainda não respondeu";


			}

			if (tarefa.getPrioridade() == 1) {
				prioridade = "Imediata - Neste Momento";
			} else if (tarefa.getPrioridade() == 2) {
				prioridade = "Urgente - Nesta Hora";
			} else if (tarefa.getPrioridade() == 3) {
				prioridade = "Quanto Antes - Ainda Hoje";
			} else if (tarefa.getPrioridade() == 4) {
				prioridade = "Média - Ainda essa semana";
			} else if (tarefa.getPrioridade() == 5) {
				prioridade = "Leve - Ainda esse mês";
			}
			

			GerenciarBancoLogin gerenciarUsuarios = new GerenciarBancoLogin();
			CadastroLogin criador = gerenciarUsuarios.getLogin(tarefa.getCriador().getId());
			CadastroLogin executor = gerenciarUsuarios.getLogin(tarefa.getExecutor().getId());

			modelo_tarefas.addRow(new Object[] { tarefa.getId_tarefa(), status_tarefa, tarefa.getNome_tarefa(),
					tarefa.getDescricao_tarefa(), tarefa.getMensagem(), resposta, tarefa.getData(), tarefa.getHora(),
					criador.getNome(), executor.getNome(), tarefa.getHora_agendada(), tarefa.getData_agendada(),
					prioridade

			});
		}

	}

	public void setSubContratos(CadastroContrato contrato_na_funcao) {
		modelo_sub_contratos.setNumRows(0);
		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_sub_contratos.clear();

		ArrayList<CadastroContrato> lista_sub_contratos_ = gerenciar.getSubContratos(contrato_na_funcao.getId());

		if (lista_sub_contratos_ != null && lista_sub_contratos_.size() > 0) {
			for (CadastroContrato contrato : lista_sub_contratos_) {
				String cpf, cnpj, nome;

				CadastroCliente compradores[] = contrato.getCompradores();
				CadastroCliente vendedores[] = contrato.getVendedores();
				CadastroCliente corretores[] = contrato.getCorretores();

				String nome_corretores = "";
				String nome_vendedores = "";
				String nome_compradores = "";

				if (corretores[0] != null) {
					if (corretores[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_corretores = corretores[0].getNome_empresarial();
					} else {
						nome_corretores = corretores[0].getNome_fantaia();

					}
				}

				contrato.setNomes_corretores(nome_corretores);

				if (compradores[0] != null) {
					if (compradores[0].getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_compradores = compradores[0].getNome_empresarial();
					} else {
						nome_compradores = compradores[0].getNome_fantaia();

					}
				}
				contrato.setNomes_compradores(nome_compradores);

				for (CadastroCliente vendedor : contrato.getVendedores()) {
					if (vendedor != null) {
						if (vendedor.getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_vendedores += vendedor.getNome_empresarial();
						} else {
							nome_vendedores += vendedor.getNome_fantaia();

						}
					}
				}
				contrato.setNomes_vendedores(nome_vendedores);

				int status = contrato.getStatus_contrato();
				String text_status = "";
				if (status == 1) {
					text_status = "Recolher Assinaturas".toUpperCase();

				} else if (status == 2) {
					text_status = "Assinado".toUpperCase();

				} else if (status == 3) {
					text_status = "Cumprindo".toUpperCase();

				}

				/*
				 * modelo.addColumn("ID"); modelo.addColumn("Código");
				 * modelo.addColumn("Status");
				 * 
				 * modelo.addColumn("Quantidade"); modelo.addColumn("Medida");
				 * modelo.addColumn("Produto"); modelo.addColumn("Safra");
				 * modelo.addColumn("Valor Produto"); modelo.addColumn("Valor Total");
				 * modelo.addColumn("Vendedores"); modelo.addColumn("Compradores");
				 * modelo.addColumn("Corretores"); modelo.addColumn("Data do Contrato");
				 * 
				 */

				if (contrato.getSub_contrato() == 1) {

					modelo_sub_contratos.addRow(new Object[] { contrato.getId(), contrato.getCodigo(), text_status,
							contrato.getQuantidade(), contrato.getMedida().toUpperCase(),
							contrato.getModelo_safra().getProduto().getNome_produto(),
							contrato.getModelo_safra().getAno_colheita() + "/"
									+ contrato.getModelo_safra().getAno_plantio(),
							"R$ " + contrato.getValor_produto(), "R$ " + contrato.getValor_a_pagar(),
							contrato.getNomes_compradores(), contrato.getNomes_vendedores(),
							contrato.getNomes_corretores(), contrato.getData_contrato()

					});
					lista_sub_contratos.add(contrato);
				}
			}
		}

	}

	public void setPagamentos(CadastroContrato contrato) {
		String cpf, banco, codigo, agencia, conta, id, nome, valor_pagamento, antecipado, cobre = "", data_pagamento;

		modelo.setNumRows(0);
		float valor_total_pagamentos = 0;

		if (contrato.getPagamentos() != null) {
			for (CadastroContrato.CadastroPagamento pag : contrato.getPagamentos()) {

				ContaBancaria conta_bc = pag.getConta();

				if (conta_bc != null) {
					id = Integer.toString(conta_bc.getId_conta());
					cpf = conta_bc.getCpf_titular();
					banco = conta_bc.getBanco();
					nome = conta_bc.getNome();
					codigo = conta_bc.getCodigo();
					agencia = conta_bc.getAgencia();
					conta = conta_bc.getConta();
				} else {
					id = "00";
					cpf = "Há Informar";
					banco = "Há Informar";
					nome = "Há Informar";
					codigo = "Há Informar";
					agencia = "Há Informar";
					conta = "Há Informar";
				}

				if (pag.getPagamento_adiantado() == 1) {
					antecipado = "SIM";
				} else {
					antecipado = "NÃO";
				}

				cobre = calculoCobertura(Double.parseDouble(pag.getValor().toPlainString()));
				valor_total_pagamentos += Float.parseFloat(pag.getValor_string());
				System.out.println("o valor total agora e: " + valor_total_pagamentos);

				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr)
						.format(Float.parseFloat(pag.getValor_string()));
				System.out.println(valorString);

				modelo.addRow(new Object[] { pag.getId(), id, cpf, nome, banco, codigo, agencia, conta, valorString,
						antecipado, cobre, pag.getData_pagamento() });
			}
		}

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);
		System.out.println(valorString);

		lblValorTotalPagamentos.setText(valorString);

	}

	public void carregarDocumento(String url) {
		// build a controller

		if (controller == null) {

			controller = new SwingController();

			PropertiesManager propriedades = new PropertiesManager(System.getProperties(),
					ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
			// Build a SwingViewFactory configured with the controller

			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR, Boolean.TRUE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR, Boolean.TRUE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, Boolean.FALSE);

			propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 0.85f);

			factory = new SwingViewBuilder(controller, propriedades);
			// Use the factory to build a JPanel that is pre-configured
			// with a complete, active Viewer UI.

			/*
			 * controller.getDocumentViewController().setAnnotationCallback( new
			 * org.icepdf.ri.common.MyAnnotationCallback(controller.
			 * getDocumentViewController()));
			 */
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				if (painel_vizualizar == null) {

					painel_vizualizar = new JPanel();
					painel_vizualizar = factory.buildViewerPanel();
					controller.openDocument(url);
					// viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
					// viewerComponentPanel.setMaximumSize(new Dimension(400, 370));

					controller.openDocument(url);
					painel_vizualizar.setBounds(10, 25, 508, 461);

					painelDadosIniciais.add(painel_vizualizar);
				} else {
					controller.openDocument(url);
					painel_vizualizar.repaint();
					painel_vizualizar.updateUI();
					painelDadosIniciais.add(painel_vizualizar);

				}

			}
		});
	}

	public void fecharDocumento() {

		if (controller != null) {
			controller.closeDocument();
		}

	}

	public void atualizarContratoLocal() {

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		contrato_local = gerenciar.getContrato(contrato_local.getId());
		setPagamentos(contrato_local);
		setSubContratos(contrato_local);

		// criar acopia temporaria

		String url_original = servidor_unidade + contrato_local.getCaminho_arquivo();
		carregarDocumento(url_original);

		// atualiza os paineis
		setarInformacoesPainelPrincipal();
		setarInformacoesPainelCarregamentos();

		// atualiza os subcontratos
		setSubContratos(contrato_local);
		atualizarArvoreContratos();
		painel_informacoes_tab_principal.setarInformacoesPainelCarregamentosInformativos();
		painel_informacoes_tab_pagamentos.setarInformacoesPainelCarregamentosInformativos();

	}

	public void getDadosGlobais() {

		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();

		// usuario logado
		login = dados.getLogin();

	}

	public String criarCopiaTemporaria(String url, String codigo) throws IOException {
		ManipularTxt manipular = new ManipularTxt();
		return manipular.copiar(url, codigo);
	}

	public void pesquisar_carregamentos() {

		registro_carregamento_global = new Registros.RegistroCarregamento();
		ArrayList<CadastroNFe> notas_fiscais_carregamentos = new ArrayList<>();
		modelo_carregamentos.setNumRows(0);
		peso_total_cargas_nfe = 0.0;
		peso_total_cargas = 0.0;

		if (lista_carregamentos != null) {
			lista_carregamentos.clear();
		} else {
			lista_carregamentos = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_carregamentos = gerenciar.getCarregamentos(contrato_local.getId());

		/*
		 * modelo_carregamentos.addColumn("Id Carregamento");
		 * modelo_carregamentos.addColumn("Data");
		 * modelo.addColumn("Contrato Destinado"); modelo.addColumn("Cliente");
		 * modelo.addColumn("Transportador"); modelo.addColumn("Veiculo");
		 * modelo.addColumn("Produto"); modelo.addColumn("Peso Real Carga");
		 * modelo.addColumn("Nota Fiscal");
		 * 
		 */
		for (CadastroContrato.Carregamento carregamento : lista_carregamentos) {

			// pegar dados do contrato
			CadastroContrato contrato_destinatario = gerenciar.getContrato(carregamento.getId_contrato());

			// pegar cliente
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente cliente_carregamento = gerenciar_clientes.getCliente(carregamento.getId_cliente());
			String nome_cliente;
			if (cliente_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_cliente = cliente_carregamento.getNome_empresarial();
			} else {
				nome_cliente = cliente_carregamento.getNome_fantaia();
			}

			// pegar vendedor
			CadastroCliente vendedor_carregamento = gerenciar_clientes.getCliente(carregamento.getId_vendedor());
			String nome_vendedor;
			if (vendedor_carregamento.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_vendedor = vendedor_carregamento.getNome_empresarial();
			} else {
				nome_vendedor = vendedor_carregamento.getNome_fantaia();
			}

			// pegar transportador e veiculo
			CadastroCliente transportador_carregamento = gerenciar_clientes
					.getCliente(carregamento.getId_transportador());
			CadastroCliente.Veiculo veiculo_carregamento = null;
			for (CadastroCliente.Veiculo veiculo : transportador_carregamento.getVeiculos()) {
				if (veiculo.getId_veiculo() == carregamento.getId_veiculo()) {
					veiculo_carregamento = veiculo;
					break;
				}
			}

			// pegar o produto
			GerenciarBancoProdutos gerenciar_produtos = new GerenciarBancoProdutos();
			CadastroProduto produto_carregamento = gerenciar_produtos.getProduto(carregamento.getId_produto());

			NumberFormat z = NumberFormat.getNumberInstance();
			// pegar a nota
			ManipularNotasFiscais manipular = new ManipularNotasFiscais("");
			CadastroNFe nota = manipular.getNotaFiscal(carregamento.getCodigo_nota_fiscal());

			// definir peso carregamento
			double peso_carregado = carregamento.getPeso_real_carga();
			// definir peso da nota
			
		    Number number = null;
			try {
				number = z.parse(nota.getQuantidade());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double peso_nota = number.doubleValue();
			// definir peso restante para nota
			double peso_nota_restante = peso_carregado - peso_nota;

			modelo_carregamentos.addRow(new Object[] { carregamento.getId_carregamento(), carregamento.getData(),
					contrato_destinatario.getCodigo(), nome_cliente, nome_vendedor,
					transportador_carregamento.getNome() + " " + transportador_carregamento.getSobrenome(),
					veiculo_carregamento.getPlaca_trator(), produto_carregamento.getNome_produto(),
					z.format(peso_carregado) + " Kg", z.format(peso_nota) + " Kg", z.format(peso_nota_restante) + " Kg",
					carregamento.getCodigo_nota_fiscal(), carregamento.getCaminho_nota_fiscal()

			});

			notas_fiscais_carregamentos.add(nota);

			peso_total_cargas += carregamento.getPeso_real_carga();

		}

		// faz a soma dos pesos das notas fiscais
		for (CadastroNFe nfe : notas_fiscais_carregamentos) {

			if (nfe != null) {
				peso_total_cargas_nfe += Double.parseDouble(nfe.getQuantidade().replace(".", "").replace(",", "."));
			}

		}

		double peso_total_kg = 0, peso_total_sacos = 0, peso_carregado_kg = 0, peso_carregado_sacos = 0,
				peso_restante_kg = 0, peso_restante_sacos = 0;

		NumberFormat z = NumberFormat.getNumberInstance();

		if (contrato_local.getMedida().equals("KG")) {
			peso_total_kg = contrato_local.getQuantidade();
			peso_total_sacos = peso_total_kg / 60;

			peso_carregado_kg = peso_total_cargas;
			peso_carregado_sacos = peso_total_cargas / 60;

			peso_restante_kg = peso_total_kg - peso_carregado_kg;
			peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;

		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			peso_total_sacos = contrato_local.getQuantidade();
			peso_total_kg = peso_total_sacos * 60;

			peso_carregado_sacos = peso_total_cargas / 60;
			peso_carregado_kg = peso_total_cargas;

			peso_restante_sacos = peso_total_sacos - peso_carregado_sacos;
			peso_restante_kg = peso_total_kg - peso_carregado_kg;

		}

		lblPesoTotal.setText(z.format(peso_total_kg) + " Kg " + " | " + z.format(peso_total_sacos) + " Sacos");
		registro_carregamento_global.setQuantidade_total(peso_total_sacos);

		// peso total das cargas
		lblPesoTotalRealCargas
				.setText(z.format(peso_carregado_kg) + " Kg" + " | " + z.format(peso_carregado_sacos) + " Sacos");
		// peso restante
		lblPesoTotalRealRestante
				.setText(z.format(peso_restante_kg) + " Kg" + " | " + z.format(peso_restante_sacos) + " Sacos");
		registro_carregamento_global.setQuantidade_restante(peso_restante_sacos);

		double peso_total_nf_kg = peso_total_cargas_nfe;
		double peso_total_nf_sacos = peso_total_nf_kg / 60;

		double peso_total_nf_kg_restante = peso_carregado_kg - peso_total_nf_kg;
		double peso_total_nf_sacos_restante = peso_carregado_sacos - peso_total_nf_sacos;

		lblPesoTotalNotasFiscais
				.setText(z.format(peso_total_nf_kg) + " Kg" + " | " + z.format(peso_total_nf_sacos) + " Sacos");
		registro_carregamento_global.setQuantidade_total_nf(peso_total_nf_sacos);

		lblPesoTotalNotasFiscaisRestante.setText(z.format(peso_total_nf_kg_restante) + " Kg" + " | "
				+ z.format(peso_total_nf_sacos_restante) + " Sacos");
		registro_carregamento_global.setQuantidade_restante_nf(peso_total_nf_sacos_restante);

		int n1 = (int) peso_total_sacos;
		int n2 = ((int) peso_carregado_sacos);

		atualizarGraficoContratos(n1, n2);

		int n3 = (int) peso_total_nf_sacos;
		int n4 = n3 - ((int) peso_total_nf_sacos_restante);

		atualizarGraficoNFs(n3, n4);

	}

	public void pesquisar_pagamentos() {

		modelo_pagamentos_contratuais.setNumRows(0);
		registro_pagamento_global = new Registros.RegistroPagamento();
		double valor_total_pagamentos = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());
		double valor_total_pagamentos_efetuados = 0;
		double valor_total_transferencias_retiradas = 0;
		double valor_total_transferencias_recebidas = 0;
		double valor_total_pagamentos_restantes = 0;

		if (lista_pagamentos_contratuais != null) {
			lista_pagamentos_contratuais.clear();
		} else {
			lista_pagamentos_contratuais = new ArrayList<>();
		}

		if (lista_transferencias_contratuais_remetente != null) {
			lista_transferencias_contratuais_remetente.clear();
		} else {
			lista_transferencias_contratuais_remetente = new ArrayList<>();
		}

		if (lista_transferencias_contratuais_destinatario != null) {
			lista_transferencias_contratuais_destinatario.clear();
		} else {
			lista_transferencias_contratuais_destinatario = new ArrayList<>();
		}

		GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
		lista_pagamentos_contratuais = gerenciar.getPagamentosContratuais(contrato_local.getId());

		GerenciarBancoTransferencias gerenciar_transferencias = new GerenciarBancoTransferencias();
		lista_transferencias_contratuais_remetente = gerenciar_transferencias
				.getTransferenciasRemetente(contrato_local.getId());
		lista_transferencias_contratuais_destinatario = gerenciar_transferencias
				.getTransferenciaDestinatario(contrato_local.getId());

		/*
		 * 
		 * modelo_pagamentos_contratuais.addColumn("Id Pagamento");
		 * modelo_pagamentos_contratuais.addColumn("Descrição");
		 * modelo_pagamentos_contratuais.addColumn("Data");
		 * modelo_pagamentos_contratuais.addColumn("Valor");
		 * modelo_pagamentos_contratuais.addColumn("Depositante");
		 * modelo_pagamentos_contratuais.addColumn("Conta Depositante");
		 * modelo_pagamentos_contratuais.addColumn("Favorecido");
		 * modelo_pagamentos_contratuais.addColumn("Conta Favorecido");
		 */
		for (CadastroContrato.CadastroPagamentoContratual pagamento : lista_pagamentos_contratuais) {

			// pegar data do pagmento
			String data = pagamento.getData_pagamento();
			double valor_pagamento = pagamento.getValor_pagamento();

			// pegar depositante
			GerenciarBancoClientes gerenciar_clientes = new GerenciarBancoClientes();
			CadastroCliente depositante = gerenciar_clientes.getCliente(pagamento.getId_depositante());
			String nome_depositante = "";
			if (depositante.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_depositante = depositante.getNome_empresarial();
			} else {
				nome_depositante = depositante.getNome_fantaia();
			}

			// pegar conta do depositante
			ContaBancaria conta_depositante = gerenciar_clientes.getConta(pagamento.getId_conta_depositante());

			// pegar favorecido
			CadastroCliente favorecido = gerenciar_clientes.getCliente(pagamento.getId_favorecido());
			String nome_favorecido = "";
			if (favorecido.getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_favorecido = favorecido.getNome_empresarial();
			} else {
				nome_favorecido = favorecido.getNome_fantaia();
			}

			// pegar conta do favorecido
			ContaBancaria conta_favorecido = gerenciar_clientes.getConta(pagamento.getId_conta_favorecido());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

			modelo_pagamentos_contratuais
					.addRow(new Object[] { pagamento.getId_pagamento(), pagamento.getDescricao(), data, valorString,
							nome_depositante, conta_depositante.getNome(), nome_favorecido, conta_favorecido.getNome()

					});

			if(pagamento.getTipo() == 1)
			valor_total_pagamentos_efetuados += valor_pagamento;

		}

		for (CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia : lista_transferencias_contratuais_remetente) {

			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

			CadastroContrato.CadastroPagamentoContratual pag_transferencia = null;

			// pegar o pagamento
			pag_transferencia = gerenciar_contratos.getPagamentoContratual(transferencia.getId_pagamento_contratual());

			// pegar data do pagmento
			String data = transferencia.getData();
			double valor_pagamento = pag_transferencia.getValor_pagamento();

			// pegar o destinatario
			CadastroContrato destinatario = gerenciar_contratos
					.getContrato(transferencia.getId_contrato_destinatario());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

			modelo_pagamentos_contratuais.addRow(new Object[] { transferencia.getId_transferencia(), "-Transferencia",
					data, valorString, contrato_local.getCodigo(), "", destinatario.getCodigo(), ""

			});

			valor_total_transferencias_retiradas += valor_pagamento;
			// valor_total_pagamentos_efetuados -= valor_pagamento;

		}

		for (CadastroContrato.CadastroTransferenciaPagamentoContratual transferencia : lista_transferencias_contratuais_destinatario) {
			GerenciarBancoContratos gerenciar_contratos = new GerenciarBancoContratos();

			CadastroContrato.CadastroPagamentoContratual pag_transferencia = null;

			// pegar o pagamento
			pag_transferencia = gerenciar_contratos.getPagamentoContratual(transferencia.getId_pagamento_contratual());

			// pegar data do pagmento
			String data = transferencia.getData();
			double valor_pagamento = pag_transferencia.getValor_pagamento();

			// pegar o destinatario
			CadastroContrato remetente = gerenciar_contratos.getContrato(transferencia.getId_contrato_remetente());

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_pagamento);

			modelo_pagamentos_contratuais.addRow(new Object[] { transferencia.getId_transferencia(), "+Transferencia",
					data, valorString, remetente.getCodigo(), "", contrato_local.getCodigo(), ""

			});
			valor_total_transferencias_recebidas += valor_pagamento;
			// valor_total_pagamentos_efetuados += valor_pagamento;

		}

		Locale ptBr = new Locale("pt", "BR");
		String valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos);

		lblTotalPagamentos.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_efetuados);

		lblTotalPagamentosEfetuados.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_retiradas);

		lblTotalTransferenciasRetiradas.setText(valor);

		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_transferencias_recebidas);

		lblTotalTransferenciasRecebidas.setText(valor);

		valor_total_pagamentos_restantes = valor_total_pagamentos - valor_total_pagamentos_efetuados
				+ valor_total_transferencias_retiradas - valor_total_transferencias_recebidas;
		valor = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos_restantes);

		lblTotalPagamentosRestantes.setText(valor);

		int n1 = (int) valor_total_pagamentos;
		int n2 = n1 - ((int) valor_total_pagamentos_restantes);
		atualizarGraficoPagamentos(n1, n2);

		registro_pagamento_global.setValor_total_pagamentos(valor_total_pagamentos);
		registro_pagamento_global.setValor_total_pagamentos_efetuados(valor_total_pagamentos_efetuados);
		registro_pagamento_global.setValor_total_pagamentos_restantes(valor_total_pagamentos_restantes);
		registro_pagamento_global.setValor_total_transferencias_recebidas(valor_total_transferencias_recebidas);
		registro_pagamento_global.setValor_total_transferencias_retiradas(valor_total_transferencias_retiradas);

	}

	public void setarInformacoesPainelCarregamentos() {

		lblDataContrato.setText(contrato_local.getData_contrato());

		CadastroCliente compradores[] = contrato_local.getCompradores();
		CadastroCliente vendedores[] = contrato_local.getVendedores();
		CadastroCliente corretores[] = contrato_local.getCorretores();

		String nome_corretores = "";
		String nome_vendedores = "";
		String nome_compradores = "";

		if (corretores[0] != null) {
			if (corretores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_corretores = corretores[0].getNome_empresarial();
			} else {
				nome_corretores = corretores[0].getNome_fantaia();

			}
		}
		lblCorretor.setText(nome_corretores);

		if (compradores[0] != null) {
			if (compradores[0].getTipo_pessoa() == 0) {
				// pessoa fisica
				nome_compradores = compradores[0].getNome_empresarial();
			} else {
				nome_compradores = compradores[0].getNome_fantaia();

			}
		}
		lblCompradores.setText(nome_compradores);

		for (CadastroCliente vendedor : vendedores) {
			if (vendedor != null) {
				if (vendedor.getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_vendedores += vendedor.getNome_empresarial();
				} else {
					nome_vendedores += vendedor.getNome_fantaia();

				}
				nome_vendedores += " ,";

			}
		}
		lblVendedores.setText(nome_vendedores);

		Locale ptBr = new Locale("pt", "BR");
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

		lblValorSaco.setText(valorString);

		NumberFormat z = NumberFormat.getNumberInstance();

		lblQuantidade.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

		String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
		lblValorTotal.setText(valor_total);

		String safra = contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato_local.getModelo_safra().getAno_plantio() + "/"
				+ contrato_local.getModelo_safra().getAno_colheita();

		lblSafra.setText(safra);
		lblProduto.setText(contrato_local.getModelo_safra().getProduto().getNome_produto());

	}

	public class PainelInformativo extends JPanel {

		private JLabel _lblDataContrato, _lblCorretor, _lblCompradores, _lblVendedores, _lblValorSaco, _lblQuantidade,
				_lblValorTotal;

		public PainelInformativo() {

			setBackground(Color.WHITE);
			setBounds(0, 0, 500, 190);
			setLayout(new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px]"));

			JLabel _lblNewLabel_5 = new JLabel("Data:");
			_lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
			_lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
			_lblNewLabel_5.setBorder(null);
			_lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_5, "cell 0 0,alignx right,aligny center");

			_lblDataContrato = new JLabel("data contrato");
			_lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblDataContrato, "cell 1 0,alignx center,aligny center");

			JLabel _lblNewLabel_9 = new JLabel("Corretor:");
			_lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_9, "cell 0 1,alignx right,growy");

			_lblCorretor = new JLabel("corretor");
			_lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCorretor, "cell 1 1,alignx center,aligny center");

			JLabel _lblNewLabel_6 = new JLabel("Compradores:");
			_lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_6, "cell 0 2,alignx right,growy");

			_lblCompradores = new JLabel("compradores");
			_lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCompradores, "cell 1 2,alignx center,aligny center");

			JLabel _lblNewLabel_8 = new JLabel("Vendedores:");
			_lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_8, "cell 0 3,alignx right,aligny center");

			_lblVendedores = new JLabel("vendedores");
			_lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblVendedores, "cell 1 3,alignx center,aligny center");

			JLabel _lblnewvalorsaco = new JLabel("Valor Unidade:");
			_lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblnewvalorsaco, "cell 0 4,alignx right,aligny center");

			_lblValorSaco = new JLabel("valor saco");
			_lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorSaco, "cell 1 4,alignx center,aligny center");

			JLabel _lblNewLabel_10 = new JLabel("Quantidade:");
			_lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_10, "cell 0 5,alignx right,aligny center");

			_lblQuantidade = new JLabel("quantidade");
			_lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblQuantidade, "cell 1 5,alignx center,aligny center");

			JLabel _lblNewLabel_11 = new JLabel("Valor Total:");
			_lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_11, "cell 0 6,alignx right,aligny center");

			_lblValorTotal = new JLabel("valortotal");
			_lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorTotal, "cell 1 6,alignx center,aligny center");

			setarInformacoesPainelCarregamentosInformativos();
		}

		public void setarInformacoesPainelCarregamentosInformativos() {

			_lblDataContrato.setText(contrato_local.getData_contrato());

			CadastroCliente compradores[] = contrato_local.getCompradores();
			CadastroCliente vendedores[] = contrato_local.getVendedores();
			CadastroCliente corretores[] = contrato_local.getCorretores();

			String nome_corretores = "";
			String nome_vendedores = "";
			String nome_compradores = "";

			if (corretores[0] != null) {
				if (corretores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_corretores = corretores[0].getNome_empresarial();
				} else {
					nome_corretores = corretores[0].getNome_fantaia();

				}
			}
			_lblCorretor.setText(nome_corretores);

			if (compradores[0] != null) {
				if (compradores[0].getTipo_pessoa() == 0) {
					// pessoa fisica
					nome_compradores = compradores[0].getNome_empresarial();
				} else {
					nome_compradores = compradores[0].getNome_fantaia();

				}
			}
			_lblCompradores.setText(nome_compradores);

			for (CadastroCliente vendedor : vendedores) {
				if (vendedor != null) {
					if (vendedor.getTipo_pessoa() == 0) {
						// pessoa fisica
						nome_vendedores += vendedor.getNome_empresarial();
					} else {
						nome_vendedores += vendedor.getNome_fantaia();

					}
					nome_vendedores += " ,";

				}
			}
			_lblVendedores.setText(nome_vendedores);

			Locale ptBr = new Locale("pt", "BR");
			String valorString = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_produto());

			_lblValorSaco.setText(valorString);

			NumberFormat z = NumberFormat.getNumberInstance();

			_lblQuantidade.setText(z.format(contrato_local.getQuantidade()) + " " + contrato_local.getMedida());

			String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(contrato_local.getValor_a_pagar());
			_lblValorTotal.setText(valor_total);

		}
	}

	public void excluir_contrato() {

		if (JOptionPane.showConfirmDialog(isto, "Deseja realmente excluir o contrato?", "Excluir",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			boolean excluido = false;
			if (contrato_local.getSub_contrato() == 0 || contrato_local.getSub_contrato() == 3) {
				// é um contrato pai, e um contrato pai tereceirizado
				excluido = gerenciar.remover_contrato_rotina(contrato_local.getId());

			} else if (contrato_local.getSub_contrato() == 1) {
				excluido = gerenciar.remover_sub_contrato_rotina(contrato_local.getId());

			}
			if (excluido) {

				// excluir diretorio do arquivo
				ManipularTxt manipular = new ManipularTxt();

				boolean exclusao_diretorio = manipular.limparDiretorio(new File(
						configs_globais.getServidorUnidade() + contrato_local.getCaminho_diretorio_contrato()));
				if (exclusao_diretorio) {
					JOptionPane.showMessageDialog(null, "Contrato Excluido!");

					// excluir diretorio da nuvem
					Nuvem nuvem = new Nuvem();
					nuvem.abrir();
					nuvem.testar();
					nuvem.listar();
					boolean exclusao_nuvem = nuvem.deletarArquivo(contrato_local.getNome_arquivo());
					if (exclusao_nuvem) {
						JOptionPane.showMessageDialog(null, "Contrato excluido");
						isto.dispose();

					} else {
						JOptionPane.showMessageDialog(null,
								"Contrato excluido, diretorio local apagado, mas o arquivo ainda está na nuvem\nConsulte o administrador!");

					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Contrato excluido, mas o diretorio nao pode ser apagado!\nConsulte o administrador");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Erro ao excluir o contrato\nConsulte o administrador!");

			}

		}

	}

	public String calculoCobertura(double valor_pagamento) {
		String retorno = "";
		// quantidade
		double quantidade = contrato_local.getQuantidade();
		double preco = contrato_local.getValor_produto();

		// unidade de medida
		if (contrato_local.getMedida().equalsIgnoreCase("TON")) {
			// unidade em toneladas

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);

		} else if (contrato_local.getMedida().equalsIgnoreCase("SACOS")) {
			// unidade em sacos

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);

		} else if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			// unidade em quilogramas

			double resultado = valor_pagamento / preco;
			retorno = Double.toString(resultado);
		}

		return retorno;
	}

	public void assinar() {
		JOptionPane.showMessageDialog(null,
				"Na próxima tela, importe o arquivo digitalizado\ncom assinatura de ambas as partes");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo .PDF", "pdf");
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String nome_arquivo = "comprovante_assinatura_" + contrato_local.getCodigo() + ".pdf";
			;

			String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				// atualizar status do contrato
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				boolean assinou = gerenciar.atualizarStatusContrato(contrato_local.getId(), 2);
				if (assinou) {

					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Comprovante de Assinatura de Contrato");
					novo_documento.setNome("comprovante_assinatura");

					String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();

					novo_documento.setTipo(1);
					novo_documento.setId_pai(0);
					novo_documento.setNome_arquivo(nome_arquivo);
					novo_documento.setId_contrato_pai(contrato_local.getId());

					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
					int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
					if (cadastrar > 0) {

						JOptionPane.showMessageDialog(null, "Arquivo copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\nStatus do Contrato Atualizado");

						atualizarArvoreDocumentos();
					} else {
						JOptionPane.showMessageDialog(null,
								"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
						// cancelar operacao e excluir o arquivo
						if (manipular.apagarArquivo(caminho_completo)) {

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Status do Contrato NÃO Atualizado");
					JOptionPane.showMessageDialog(null,
							"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}

		} catch (Exception e) {

		}

		setarInformacoesPainelPrincipal();
	}

	public void assinarAditivo(int id_aditivo) {
		JOptionPane.showMessageDialog(null,
				"Na próxima tela, importe o arquivo digitalizado\ncom assinatura de ambas as partes");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String nome_arquivo = "comprovante_assinatura_aditivo_" + id_aditivo + "_" + contrato_local.getCodigo()
					+ ".pdf";
			;

			String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				// atualizar status do contrato
				GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
				boolean assinou = gerenciar.atualizarStatusAditivo(id_aditivo, 2);
				if (assinou) {

					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Comprovante de Assinatura de Aditivo");
					novo_documento.setNome("comprovante_aditivo");

					novo_documento.setTipo(4);
					novo_documento.setId_pai(id_aditivo);
					novo_documento.setNome_arquivo(nome_arquivo);
					novo_documento.setId_contrato_pai(contrato_local.getId());

					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
					int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
					if (cadastrar > 0) {

						JOptionPane.showMessageDialog(null, "Arquivo copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\nStatus do Contrato Atualizado");

						atualizarArvoreDocumentos();
					} else {
						JOptionPane.showMessageDialog(null,
								"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
						// cancelar operacao e excluir o arquivo
						if (manipular.apagarArquivo(caminho_completo)) {

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Status do Contrato NÃO Atualizado");
					JOptionPane.showMessageDialog(null,
							"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}

		} catch (Exception e) {

		}

		setarInformacoesPainelPrincipal();
	}

	public void importarComprovanteCarregamento(int id_carregamento) {
		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo \ncomprovante!");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();
			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();
			String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);

			String nome_arquivo = "comprovante_carregamento_" + id_carregamento + "_" + contrato_local.getCodigo() + "."
					+ extensaoDoArquivo;
			;

			String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
			boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

			if (movido) {

				CadastroDocumento novo_documento = new CadastroDocumento();
				novo_documento.setDescricao("Comprovante de Carregamento id: " + id_carregamento + "do contrato "
						+ contrato_local.getCodigo());
				novo_documento.setNome("comprovante_assinatura");

				novo_documento.setTipo(3);
				novo_documento.setId_pai(id_carregamento);
				novo_documento.setNome_arquivo(nome_arquivo);
				novo_documento.setId_contrato_pai(contrato_local.getId());

				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
				if (cadastrar > 0) {

					JOptionPane.showMessageDialog(null,
							"Arquivo copiado\nOrigem: " + caminho_arquivo + "\nDestino: " + caminho_completo);

					atualizarArvoreDocumentos();
				} else {
					JOptionPane.showMessageDialog(null,
							"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}

		} catch (Exception e) {

		}

		setarInformacoesPainelPrincipal();
	}

	public void importarComprovantePagamento(int id_pagamento) {
		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo \ncomprovante!");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();

		TelaRecortarImagem2 tela = new TelaRecortarImagem2(caminho_arquivo);
		tela.setTelaPai(isto);
		tela.setVisible(true);

		try {
			// copiar o arquivo para a pasta do contrato
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();
			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato();

			String nome_arquivo = "comprovante_pagamento_" + id_pagamento + "_" + contrato_local.getCodigo() + "."
					+ "png";
			;

			String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
			boolean movido = manipular.copiarNFe(caminho_salvar_comprovante_pagamento, caminho_completo);

			if (movido) {

				CadastroDocumento novo_documento = new CadastroDocumento();
				novo_documento.setDescricao(
						"Comprovante de Pagamento id: " + id_pagamento + "do contrato " + contrato_local.getCodigo());
				novo_documento.setNome("comprovante_assinatura");

				novo_documento.setTipo(2);
				novo_documento.setId_pai(id_pagamento);
				novo_documento.setNome_arquivo(nome_arquivo);
				novo_documento.setId_contrato_pai(contrato_local.getId());

				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
				if (cadastrar > 0) {

					JOptionPane.showMessageDialog(null,
							"Arquivo copiado\nOrigem: " + caminho_arquivo + "\nDestino: " + caminho_completo);

					atualizarArvoreDocumentos();
				} else {
					JOptionPane.showMessageDialog(null,
							"Arquivo copiado, mas não pode ser salvo na base de dados\nConsulte o adiministrador do sistema!");
					// cancelar operacao e excluir o arquivo
					if (manipular.apagarArquivo(caminho_completo)) {

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
						+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

			}

		} catch (Exception e) {

		}

		setarInformacoesPainelPrincipal();
	}

	public void setarInformacoesPainelPrincipal() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
				contrato_local = gerenciar.getContrato(contrato_local.getId());

				int status = contrato_local.getStatus_contrato();
				if (status == 1) {
					lblStatusContrato.setText("Status do Contrato: " + "Recolher Assinaturas");

					btnCriarAditivo.setEnabled(false);
					btnCriarAditivo.setVisible(false);

					btnRevogarAssinatura.setEnabled(false);
					btnRevogarAssinatura.setVisible(false);

					btnConcluir.setEnabled(false);
					btnConcluir.setVisible(false);

					if (contrato_local.getSub_contrato() == 3) {
						btnEditarContrato.setVisible(false);
						btnEditarContrato.setEnabled(false);
					} else {
						btnEditarContrato.setVisible(true);
						btnEditarContrato.setEnabled(true);
					}

					btnExcluirContrato.setVisible(true);
					btnExcluirContrato.setEnabled(true);

					btnAssinarContrato.setVisible(true);
					btnAssinarContrato.setEnabled(true);

					btnReabrir.setEnabled(false);
					btnReabrir.setVisible(false);

				} else if (status == 2) {
					lblStatusContrato.setText("Status do Contrato: " + "Assinado");
					btnCriarAditivo.setEnabled(true);
					btnCriarAditivo.setVisible(true);

					btnRevogarAssinatura.setEnabled(true);
					btnRevogarAssinatura.setVisible(true);

					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);

					btnConcluir.setEnabled(true);
					btnConcluir.setVisible(true);

					btnExcluirContrato.setVisible(false);
					btnExcluirContrato.setEnabled(false);

					btnAssinarContrato.setVisible(false);
					btnAssinarContrato.setEnabled(false);

					btnReabrir.setEnabled(false);
					btnReabrir.setVisible(false);

				} else if (status == 3) {
					lblStatusContrato.setText("Status do Contrato: " + "Cumprindo");
					btnEditarContrato.setVisible(false);
					btnEditarContrato.setEnabled(false);

					btnExcluirContrato.setVisible(false);
					btnExcluirContrato.setEnabled(false);

					btnAssinarContrato.setEnabled(false);
					btnAssinarContrato.setVisible(false);

					btnCriarAditivo.setEnabled(false);
					btnCriarAditivo.setVisible(false);

					btnRevogarAssinatura.setEnabled(false);
					btnRevogarAssinatura.setVisible(false);

					btnConcluir.setEnabled(false);
					btnConcluir.setVisible(false);

					btnReabrir.setEnabled(true);
					btnReabrir.setVisible(true);

				}

				/*
				 * DadosGlobais dados = DadosGlobais.getInstance(); JFrame telaPrincipal =
				 * dados.getTelaPrincipal(); ((TelaPrincipal)
				 * telaPrincipal).atualizarGraficoContratos();
				 */
			}
		});

	}

	public void atualizarGraficoContratos(int num_total, int num_carregado) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_carregado) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoCarregamento.setDados(num_total, i);
					painelGraficoCarregamento.repaint();

					i++;
				}

			}
		}.start();

	}

	public void atualizarGraficoNFs(int num_total, int num_tirado) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_tirado) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoNFs.setDados(num_total, i);
					painelGraficoNFs.repaint();

					i++;
				}

			}
		}.start();

	}

	public void atualizarGraficoPagamentos(int num_total, int num_ja_pago) {

		new Thread() {

			@Override
			public void run() {

				int i = 0;
				while (i <= num_ja_pago) {

					// System.out.printf("Disponivel e %d\n ", disponivel);
					// System.out.printf("Usado e %d\n", usado);

					painelGraficoPagamentos.setDados(num_total, i);
					painelGraficoPagamentos.repaint();

					i++;
				}

			}
		}.start();

	}

	public void vizualizarContrato() {

		String unidade_base_dados = configs_globais.getServidorUnidade();

		String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_arquivo();
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop desktop = Desktop.getDesktop();
				File myFile = new File(caminho_completo);
				desktop.open(myFile);
			} catch (IOException ex) {
			}
		}
	}

	public void setarTituloJanela() {
		// definir o nome da janela
		String safra = contrato_local.getModelo_safra().getProduto().getNome_produto() + " "
				+ contrato_local.getModelo_safra().getAno_plantio() + "/"
				+ contrato_local.getModelo_safra().getAno_colheita();
		double quantidade = 0;

		if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade = contrato_local.getQuantidade() / 60;
		} else if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade = contrato_local.getQuantidade();

		}

		double valor_produto = contrato_local.getValor_produto();
		BigDecimal valor_a_pagar = contrato_local.getValor_a_pagar();

		Locale ptBr = new Locale("pt", "BR");
		String valor_produto_reais = NumberFormat.getCurrencyInstance(ptBr).format(valor_produto);

		String valor_total_a_pagar_reais = NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar);

		String nome_comprador = "";
		String nome_vendedor1 = "";
		String nome_vendedor2 = null;
		CadastroCliente compradores[] = contrato_local.getCompradores();

		CadastroCliente vendedores[] = contrato_local.getVendedores();

		if (compradores[0].getTipo_pessoa() == 0) {
			nome_comprador = compradores[0].getNome_empresarial();
		} else {
			nome_comprador = compradores[0].getNome_fantaia();
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

		String de_para = "";
		if (nome_vendedor2 != null) {
			de_para = "de " + nome_vendedor1 + " e " + nome_vendedor2 + " para " + nome_comprador;
		} else {
			de_para = "de " + nome_vendedor1 + " para " + nome_comprador;

		}

		if (contrato_local.getSub_contrato() == 0) {

			isto.setTitle("E-Contract - Gerenciar Contrato " + contrato_local.getCodigo() + " em quantidade de "
					+ quantidade + " sacos de " + contrato_local.getModelo_safra().getProduto().getNome_produto()
					+ " da safra " + safra + " no valor de " + valor_produto_reais + " por saco," + de_para
					+ " no valor total de " + valor_total_a_pagar_reais);

		} else {

			isto.setTitle("E-Contract - Gerenciar Sub-Contrato " + contrato_local.getCodigo() + " em quantidade de "
					+ quantidade + " sacos de " + contrato_local.getModelo_safra().getProduto().getNome_produto()
					+ " da safra " + safra + " no valor de " + valor_produto_reais + " por saco," + de_para
					+ " no valor total de " + valor_total_a_pagar_reais);

		}

	}

	public void revogarAssinatura() {

		if (JOptionPane.showConfirmDialog(isto, "Revogar Assinatura", "Deseja revogar a assinatura?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			boolean revogado = gerenciar.atualizarStatusContrato(contrato_local.getId(), 1);

			if (revogado) {
				JOptionPane.showMessageDialog(null, "Assinatura do Contrato Retirada");

			} else {
				JOptionPane.showMessageDialog(null, "Erro ao revogar a assinatura!\nConsulte o administrador!");

			}

			atualizarContratoLocal();
			//setarInformacoesPainelPrincipal();

		} else {

		}

	}

	public void revogarAssinaturaAditivo(int id_aditivo) {

		if (JOptionPane.showConfirmDialog(isto, "Revogar Assinatura", "Deseja revogar a assinatura deste aditivo?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
			boolean revogado = gerenciar.atualizarStatusAditivo(id_aditivo, 1);

			if (revogado) {
				JOptionPane.showMessageDialog(null, "Assinatura do aditivo Retirada");

			} else {
				JOptionPane.showMessageDialog(null, "Erro ao revogar a assinatura!\nConsulte o administrador!");

			}

			atualizarContratoLocal();
			setarInformacoesPainelPrincipal();

		} else {

		}

	}

	public void selecionarDocumento() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 600));
		fileChooser.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo .PDF", "pdf");
		fileChooser.addChoosableFileFilter(filter);

		int result = fileChooser.showOpenDialog(isto);

		String caminho_arquivo = fileChooser.getSelectedFile().toString();
		entCaminhoDocumento.setText(caminho_arquivo);

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

			String caminho_salvar = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato() + "\\"
					+ "comprovantes";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
					nome_arquivo = contrato_local.getCodigo() + "_" + nome + "_" + horaString.replaceAll(":", "_") + "."
							+ extensaoDoArquivo;

					String caminho_completo = caminho_salvar + "\\" + nome_arquivo;

					boolean movido = manipular.copiarNFe(caminho_arquivo, caminho_completo);

					if (movido) {

						// atualizar status do contrato
						CadastroDocumento novo_documento = new CadastroDocumento();
						novo_documento.setDescricao(descricao);
						novo_documento.setNome(nome);

						String s_tipo_documento = cBTipoDocumento.getSelectedItem().toString();
						int tipo_documento = -1;

						if (s_tipo_documento.equalsIgnoreCase("Assinaturas")) {
							tipo_documento = 1;
						} else if (s_tipo_documento.equalsIgnoreCase("Pagamentos")) {
							tipo_documento = 2;
						} else if (s_tipo_documento.equalsIgnoreCase("Carregamentos")) {
							tipo_documento = 3;
						} else if (s_tipo_documento.equalsIgnoreCase("Outros")) {
							tipo_documento = 4;
						}

						novo_documento.setTipo(tipo_documento);
						novo_documento.setId_pai(0);
						novo_documento.setNome_arquivo(nome_arquivo);
						novo_documento.setId_contrato_pai(contrato_local.getId());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserir_documento_padrao(novo_documento);
						if (cadastrar > 0) {
							JOptionPane.showMessageDialog(null, "Arquivo copiado e salvo na base de dados\nOrigem: "
									+ caminho_arquivo + "\nDestino: " + caminho_completo);

							entNomeDocumento.setText("");
							entDescricaoDocumento.setText("");
							entCaminhoDocumento.setText("");

							atualizarArvoreDocumentos();
						} else {
							JOptionPane.showMessageDialog(null,
									"Arquivo copiado, mas não pode ser salvo\nConsulte o adiministrador do sistema!");
							// cancelar operacao e excluir o arquivo
							if (manipular.apagarArquivo(caminho_completo)) {

							} else {
								JOptionPane.showMessageDialog(null,
										"Erro ao excluir arquivo!\nConsulte o administrador do sistema");
							}
						}

					} else {
						JOptionPane.showMessageDialog(null, "Arquivo  não pode ser copiado\nOrigem: " + caminho_arquivo
								+ "\nDestino: " + caminho_completo + "\n Consulte o administrador!");

					}
				} else {
					JOptionPane.showMessageDialog(null, "Nome do arquivo invalido!");

				}
			} else {
				JOptionPane.showMessageDialog(null, "Caminho do arquivo invalido!");
			}

		} catch (IOException f) {

		}

	}

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentos(contrato_local.getId());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				JPanel panel = new JPanel();
				panel.setBounds(24, 260, 431, 302);
				painelComprovantes.add(panel);
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JLabel lblNewLabel_18 = new JLabel("Documentos deste contrato:");
				lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel_18, "cell 0 0");

				// create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				no_assinaturas = new DefaultMutableTreeNode("Assinaturas");
				no_pagamentos = new DefaultMutableTreeNode("Pagamentos");
				no_carregamentos = new DefaultMutableTreeNode("Carregamentos");
				no_outros = new DefaultMutableTreeNode("Outros");

				// add the child nodes to the root node
				root.add(no_assinaturas);
				root.add(no_pagamentos);
				root.add(no_carregamentos);
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

				setMenuDocumentos();

				

				arvore_documentos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_assinatura = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_assinatura.png"));
					ImageIcon icone_pagamentos = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_pagamentos.png"));
					ImageIcon icone_carregamentos = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_carregamentos.png"));
					ImageIcon icone_outros = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_outros.png"));

					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
							boolean expanded, boolean isLeaf, int row, boolean focused) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
						String s = node.getUserObject().toString();
						if ("Assinaturas".equals(s)) {
							setOpenIcon(icone_assinatura);
							setClosedIcon(icone_assinatura);

						} else if ("Carregamentos".equals(s)) {
							setOpenIcon(icone_carregamentos);
							setClosedIcon(icone_carregamentos);

						} else if ("Pagamentos".equals(s)) {
							setOpenIcon(icone_pagamentos);
							setClosedIcon(icone_pagamentos);

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
							no_assinaturas.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 2) {
							// pagamentos
							no_pagamentos.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 3) {
							// carregamentos
							no_carregamentos.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						} else if (doc.getTipo() == 4) {
							// outros
							no_outros.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}

					}
				}

				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

				lblNoSelecionado = new JLabel("");
				lblNoSelecionado.setBounds(24, 347, 431, 14);
				painelComprovantes.add(lblNoSelecionado);

				JPanel panel_1 = new JPanel();
				panel_1.setBounds(24, 36, 752, 132);
				painelComprovantes.add(panel_1);
				panel_1.setLayout(null);

				modelo_aditivos.addColumn("Id");
				modelo_aditivos.addColumn("Status");
				modelo_aditivos.addColumn("Data");
				modelo_aditivos.addColumn("Texto");
				modelo_aditivos.addColumn("Nome Arquivo");

				table_aditivos = new JTable(modelo_aditivos);

				setMenuAditivos();

				JScrollPane scrollPane = new JScrollPane(table_aditivos);
				scrollPane.setBounds(10, 11, 732, 110);
				panel_1.add(scrollPane);

				JPanel panel_2 = new JPanel();
				panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
				panel_2.setBounds(1006, 36, 284, 434);
				painelComprovantes.add(panel_2);
				panel_2.setLayout(null);

				JLabel lblNewLabel_25 = new JLabel("Relatorios");
				lblNewLabel_25.setBounds(102, 17, 72, 14);
				panel_2.add(lblNewLabel_25);
				lblNewLabel_25.setFont(new Font("Sitka Small", Font.PLAIN, 14));

				JButton btnSimplificado = new JButton("Gerar");
				btnSimplificado.setBounds(207, 371, 59, 28);
				panel_2.add(btnSimplificado);
				
				JLabel lblNewLabel_26 = new JLabel("Tipo:");
				lblNewLabel_26.setFont(new Font("SansSerif", Font.PLAIN, 14));
				lblNewLabel_26.setBounds(6, 45, 32, 19);
				panel_2.add(lblNewLabel_26);
				JCheckBox chckbxExterno = new JCheckBox("Externo");
				JCheckBox chkbxInterno = new JCheckBox("Interno");
				JCheckBox chckbxIncluirGanhosPotenciais = new JCheckBox("Incluir Ganhos Potenciais");
				JCheckBox chckbxIncluirComprovantesPagamentos = new JCheckBox("Incluir Comprovantes");
				chckbxIncluirComprovantesPagamentos.setEnabled(false);
				JCheckBox chckbxIncluirTransferencias = new JCheckBox("Incluir Transferencias");
				JCheckBox chckbxIncluirPagamentos = new JCheckBox("Incluir Pagamentos");

				JPanel painelOpcaoInternas = new JPanel();
				painelOpcaoInternas.setBorder(new LineBorder(new Color(0, 0, 0)));

				chckbxExterno.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxExterno.isSelected()) {
							chkbxInterno.setSelected(false);
							chckbxExterno.setSelected(true);
							painelOpcaoInternas.setVisible(false);
							chckbxIncluirTransferencias.setEnabled(false);
							chckbxIncluirTransferencias.setSelected(false);

						}else {
							chkbxInterno.setSelected(true);
							chckbxExterno.setSelected(false);
							painelOpcaoInternas.setVisible(true);
							
							if(chckbxIncluirPagamentos.isSelected()) {
								chckbxIncluirTransferencias.setEnabled(true);


							}else {
								chckbxIncluirTransferencias.setEnabled(false);

							}

							
						}
					}
				});

				chkbxInterno.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chkbxInterno.isSelected()) {
							chkbxInterno.setSelected(true);
							chckbxExterno.setSelected(false);
							
							painelOpcaoInternas.setVisible(true);
							
						}else {
							chkbxInterno.setSelected(false);
							chckbxExterno.setSelected(true);
							painelOpcaoInternas.setVisible(false);

						}
					}
				});
				chkbxInterno.setBounds(50, 46, 60, 18);
				panel_2.add(chkbxInterno);
				
				chckbxExterno.setBounds(122, 46, 63, 18);
				panel_2.add(chckbxExterno);
				
				painelOpcaoInternas.setBounds(16, 76, 251, 162);
				panel_2.add(painelOpcaoInternas);
				painelOpcaoInternas.setLayout(null);
				
				JCheckBox chckbxIncluirSubContratos = new JCheckBox("Incluir Sub-Contratos");
				chckbxIncluirSubContratos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(chckbxIncluirSubContratos.isSelected()) {
							chckbxIncluirSubContratos.setSelected(true);
							chckbxIncluirGanhosPotenciais.setEnabled(true);
						}else {
							chckbxIncluirSubContratos.setSelected(false);
							chckbxIncluirGanhosPotenciais.setEnabled(false);
						}
						
						
					}
				});
				chckbxIncluirSubContratos.setBounds(16, 63, 138, 18);
				painelOpcaoInternas.add(chckbxIncluirSubContratos);
				
				JCheckBox chckbxIncluirComisso = new JCheckBox("Incluir Comissão");
				chckbxIncluirComisso.setBounds(16, 36, 138, 18);
				painelOpcaoInternas.add(chckbxIncluirComisso);
				
				chckbxIncluirGanhosPotenciais.setEnabled(false);
				chckbxIncluirGanhosPotenciais.setBounds(44, 93, 163, 18);
				painelOpcaoInternas.add(chckbxIncluirGanhosPotenciais);
				
				JLabel lblNewLabel_27 = new JLabel("Opções relatorio interno:");
				lblNewLabel_27.setBounds(21, 8, 135, 16);
				painelOpcaoInternas.add(lblNewLabel_27);
				
				JCheckBox chckbxIncluirCarregamento = new JCheckBox("Incluir Carregamentos");
				chckbxIncluirCarregamento.setBounds(37, 250, 163, 18);
				panel_2.add(chckbxIncluirCarregamento);
				
				chckbxIncluirPagamentos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						if(chckbxIncluirPagamentos.isSelected()) {
							chckbxIncluirPagamentos.setSelected(true);
							chckbxIncluirComprovantesPagamentos.setEnabled(true);
							
							if(chkbxInterno.isSelected()) {
							chckbxIncluirTransferencias.setEnabled(true);
							}else {
								chckbxIncluirTransferencias.setEnabled(false);
								chckbxIncluirTransferencias.setSelected(false);

							}
							
						}else {
							chckbxIncluirPagamentos.setSelected(false);
							chckbxIncluirComprovantesPagamentos.setEnabled(false);
							chckbxIncluirTransferencias.setEnabled(false);
							chckbxIncluirTransferencias.setSelected(false);

						}
						
					}
				});
				chckbxIncluirPagamentos.setBounds(37, 278, 163, 18);
				panel_2.add(chckbxIncluirPagamentos);
				
				chckbxIncluirComprovantesPagamentos.setBounds(81, 308, 139, 18);
				panel_2.add(chckbxIncluirComprovantesPagamentos);
				
				chckbxIncluirTransferencias.setEnabled(false);
				chckbxIncluirTransferencias.setBounds(81, 338, 141, 18);
				panel_2.add(chckbxIncluirTransferencias);

				entNomeDocumento = new JTextField();
				entNomeDocumento.setBounds(546, 293, 330, 27);
				painelComprovantes.add(entNomeDocumento);
				entNomeDocumento.setColumns(10);

				JLabel lblNewLabel_15 = new JLabel("Nome:");
				lblNewLabel_15.setBounds(505, 296, 46, 14);
				painelComprovantes.add(lblNewLabel_15);

				JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
				lblNewLabel_16_1.setBounds(490, 343, 70, 14);
				painelComprovantes.add(lblNewLabel_16_1);

				cBTipoDocumento = new JComboBox();
				cBTipoDocumento.setBounds(546, 339, 330, 22);
				painelComprovantes.add(cBTipoDocumento);
				cBTipoDocumento.addItem("Assinaturas");
				cBTipoDocumento.addItem("Pagamentos");
				cBTipoDocumento.addItem("Carregamentos");
				cBTipoDocumento.addItem("Outros");

				JLabel lblNewLabel_16 = new JLabel("Descrição:");
				lblNewLabel_16.setBounds(490, 390, 70, 14);
				painelComprovantes.add(lblNewLabel_16);

				entDescricaoDocumento = new JTextArea();
				entDescricaoDocumento.setBounds(546, 385, 330, 85);
				painelComprovantes.add(entDescricaoDocumento);

				JLabel lblNewLabel_17 = new JLabel("Arquivo:");
				lblNewLabel_17.setBounds(490, 495, 46, 14);
				painelComprovantes.add(lblNewLabel_17);

				entCaminhoDocumento = new JTextField();
				entCaminhoDocumento.setBounds(546, 483, 231, 39);
				painelComprovantes.add(entCaminhoDocumento);
				entCaminhoDocumento.setColumns(10);

				JButton btnSelecionarDocumento = new JButton("Selecionar");
				btnSelecionarDocumento.setBounds(787, 490, 89, 24);
				painelComprovantes.add(btnSelecionarDocumento);

				JButton btnAdicionarDocumento = new JButton("Adicionar");
				btnAdicionarDocumento.setBounds(787, 539, 89, 23);
				painelComprovantes.add(btnAdicionarDocumento);
				btnAdicionarDocumento.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if(contrato_local.getStatus_contrato() != 3)
						adicionarNovoDocumento();

					}
				});
				btnSelecionarDocumento.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(contrato_local.getStatus_contrato() != 3)
						selecionarDocumento();
					}
				});
				btnSimplificado.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RelatorioContratoIndividual relatorio = new RelatorioContratoIndividual(contrato_local);

						
					   if(chkbxInterno.isSelected()) {
						   relatorio.setExterno(false);
						   relatorio.setInterno(true);
						   
						  
						   
						   if(chckbxIncluirSubContratos.isSelected()) {
							   relatorio.setIncluir_sub_contratos(true);
							   
							   if(chckbxIncluirGanhosPotenciais.isSelected()) {
								   relatorio.setIncluir_ganhos_potenciais(true);
							   }else {
								   relatorio.setIncluir_ganhos_potenciais(false);

							   }
							   
							   
						   }else {
							   relatorio.setIncluir_sub_contratos(false);
							   relatorio.setIncluir_ganhos_potenciais(false);

						   }
						   
						   
						   if(chckbxIncluirComisso.isSelected()) {
							   relatorio.setIncluir_comissao(true);
						   }else {
							   relatorio.setIncluir_comissao(false);

						   }
						   
						   
					   }else if(chckbxExterno.isSelected()) {
						   relatorio.setExterno(true);
						   relatorio.setInterno(false);
						   
						   relatorio.setIncluir_comissao(false);
						   relatorio.setIncluir_ganhos_potenciais(false);
						   relatorio.setIncluir_sub_contratos(false);

						   
					   }
					   
					   if(chckbxIncluirCarregamento.isSelected()) {
						   relatorio.setIncluir_carregamento(true);
					   }else {
						   relatorio.setIncluir_carregamento(false);

					   }

					   
					   if(chckbxIncluirPagamentos.isSelected()) {
						   relatorio.setIncluir_pagamento(true);
						   
						   if(chckbxIncluirComprovantesPagamentos.isSelected()) {
							   relatorio.setIncluir_comprovantes_pagamentos(true);

						   }else {
							   relatorio.setIncluir_comprovantes_pagamentos(false);

						   }
						   
						   if(chckbxIncluirTransferencias.isSelected()) {
							   relatorio.setIncluir_transferencias(true);

						   }else {
							   relatorio.setIncluir_transferencias(false);

						   }
						   
						   
						   

						   
					   }else {
						   relatorio.setIncluir_pagamento(false);
						   relatorio.setIncluir_comprovantes_pagamentos(false);
						   relatorio.setIncluir_transferencias(false);

					   }
					   


					   
						
						Date hoje = new Date();
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
						contrato_local.setData_contrato(df.format(hoje));
						System.out.println("Preparando para elaborar novo relatorio contrato individual");
						ByteArrayOutputStream contrato_alterado = relatorio.preparar();

						ConverterPdf converter_pdf = new ConverterPdf();
						String pdf_alterado = converter_pdf.word_pdf_stream(contrato_alterado);
						TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, contrato_local);
					}
				});

				setInformacoesAditivos();

			}
		});

	}

	public void atualizarArvoreDocumentos() {

		new Thread() {
			@Override
			public void run() {
				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				ArrayList<CadastroDocumento> lista_docs = gerenciar_doc.getDocumentos(contrato_local.getId());

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

						root.removeAllChildren();

						no_assinaturas.removeAllChildren();
						no_pagamentos.removeAllChildren();
						no_carregamentos.removeAllChildren();
						no_outros.removeAllChildren();

						no_assinaturas = new DefaultMutableTreeNode("Assinaturas");
						no_pagamentos = new DefaultMutableTreeNode("Pagamentos");
						no_carregamentos = new DefaultMutableTreeNode("Carregamentos");
						no_outros = new DefaultMutableTreeNode("Outros");

						root.add(no_assinaturas);
						root.add(no_pagamentos);
						root.add(no_carregamentos);
						root.add(no_outros);

						if (lista_docs != null && lista_docs.size() > 0) {
							for (CadastroDocumento doc : lista_docs) {
								if (doc.getTipo() == 1) {
									// model.insertNodeInto(new DefaultMutableTreeNode(doc.getNome()), root,
									// root.getChildCount());

									no_assinaturas.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 2) {
									// pagamentos
									no_pagamentos.add(new DefaultMutableTreeNode(
											doc.getId_documento() + "@" + doc.getNome_arquivo()));

								} else if (doc.getTipo() == 3) {
									// carregamentos
									no_carregamentos.add(new DefaultMutableTreeNode(
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

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

	public void criarAbaSubContrato() {

		JTable tabela_sub_contratos = new JTable(modelo_sub_contratos);
		// tabela_modelo_pagamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela_sub_contratos.setBackground(new Color(255, 255, 255));

		modelo_sub_contratos.addColumn("ID");
		modelo_sub_contratos.addColumn("Código");
		modelo_sub_contratos.addColumn("Status");

		modelo_sub_contratos.addColumn("Quantidade");
		modelo_sub_contratos.addColumn("Medida");
		modelo_sub_contratos.addColumn("Produto");
		modelo_sub_contratos.addColumn("Safra");
		modelo_sub_contratos.addColumn("Valor Produto");
		modelo_sub_contratos.addColumn("Valor Total");
		modelo_sub_contratos.addColumn("Compradores");

		modelo_sub_contratos.addColumn("Vendedores");
		modelo_sub_contratos.addColumn("Corretores");
		modelo_sub_contratos.addColumn("Data do Contrato");

		tabela_sub_contratos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela_sub_contratos.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabela_sub_contratos.getColumnModel().getColumn(2).setPreferredWidth(170);
		tabela_sub_contratos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabela_sub_contratos.getColumnModel().getColumn(6).setPreferredWidth(70);
		tabela_sub_contratos.getColumnModel().getColumn(7).setPreferredWidth(90);
		tabela_sub_contratos.getColumnModel().getColumn(8).setPreferredWidth(80);
		tabela_sub_contratos.getColumnModel().getColumn(9).setPreferredWidth(150);
		tabela_sub_contratos.getColumnModel().getColumn(10).setPreferredWidth(150);
		tabela_sub_contratos.getColumnModel().getColumn(11).setPreferredWidth(100);
		tabela_sub_contratos.getColumnModel().getColumn(12).setPreferredWidth(80);

		JLabel lblSubcontratos = new JLabel("     Sub-Contratos");
		JButton btnAdicionarSubContrato = new JButton("Adicionar");
		JButton btnSelecionarSubContrato = new JButton("Abrir");

		JScrollPane scrollPaneSubContratos;

		scrollPaneSubContratos = new JScrollPane(tabela_sub_contratos);
		scrollPaneSubContratos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubContratos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneSubContratos.setBackground(Color.WHITE);
		scrollPaneSubContratos.setAutoscrolls(true);
		scrollPaneSubContratos.setBounds(207, 82, 1004, 132);

		painelSubContratos.add(scrollPaneSubContratos);
		lblSubcontratos.setOpaque(true);
		lblSubcontratos.setForeground(Color.WHITE);
		lblSubcontratos.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSubcontratos.setBackground(new Color(0, 51, 0));
		lblSubcontratos.setBounds(0, 22, 230, 31);

		painelSubContratos.add(lblSubcontratos);
		btnAdicionarSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DadosGlobais dados = DadosGlobais.getInstance();
				dados.setTeraGerenciarContratoPai(isto);
				TelaEscolhaTipoNovoContrato telaNovoCadastro = new TelaEscolhaTipoNovoContrato(1, contrato_local, 0);

			}
		});
		btnAdicionarSubContrato.setBounds(1097, 225, 114, 23);

		painelSubContratos.add(btnAdicionarSubContrato);
		btnSelecionarSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indiceDaLinha = tabela_sub_contratos.getSelectedRow();

				// abre a tela de gerenciar o contrato selecionado na lista de sub contratos
				TelaGerenciarContrato gerenciar_contrato = new TelaGerenciarContrato(
						lista_sub_contratos.get(indiceDaLinha));

			}
		});
		btnSelecionarSubContrato.setBounds(966, 225, 121, 23);

		painelSubContratos.add(btnSelecionarSubContrato);

		JPanel panelPaiArvoreDocumentos = new JPanel();
		panelPaiArvoreDocumentos.setBounds(27, 298, 374, 286);
		painelSubContratos.add(panelPaiArvoreDocumentos);
		panelPaiArvoreDocumentos.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		JLabel lblNewLabel_19 = new JLabel("Arvoré de Contratos");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelPaiArvoreDocumentos.add(lblNewLabel_19, "cell 0 0");

		JPanel panelInformativoSubContrato = new JPanel();
		panelInformativoSubContrato.setBounds(430, 347, 500, 190);
		painelSubContratos.add(panelInformativoSubContrato);

		PainelInformativoSubContratos painelInfo = new PainelInformativoSubContratos();
		panelInformativoSubContrato.add(painelInfo);
		panelInformativoSubContrato.setLayout(null);

		setInformacoesArvoreContratos(panelPaiArvoreDocumentos, painelInfo);

		painelSubContratos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSubContratos(contrato_local);
			}
		});
	}

	public void setInformacoesArvoreContratos(JPanel painel_pai, PainelInformativoSubContratos painelInformacoes) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {


				// create the root node
				DefaultMutableTreeNode root_contratos = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				no_contratos = new DefaultMutableTreeNode("Contrato");
				no_sub_contratos = new DefaultMutableTreeNode("Sub-Contratos");

				// add the child nodes to the root node
				root_contratos.add(no_contratos);
				root_contratos.add(no_sub_contratos);

				// create the tree by passing in the root node
				arvore_contratos = new JTree(root_contratos);

				arvore_contratos.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						no_contrato_selecionado = (DefaultMutableTreeNode) arvore_contratos
								.getLastSelectedPathComponent();
						String nome_arquivo = no_contrato_selecionado.getUserObject().toString();
						String quebra[] = nome_arquivo.split("@");
						String id = quebra[0];
						int i_id = Integer.parseInt(id);

						boolean sub_contrato_encontrado = false;

						CadastroContrato cadastro_mostrar = null;

						for (CadastroContrato sub : lista_sub_contratos) {
							if (sub.getId() == i_id) {
								cadastro_mostrar = sub;
								sub_contrato_encontrado = true;
								break;
							}
						}

						if (!sub_contrato_encontrado) {
							painelInformacoes.setInformacoesSubContrato(contrato_local);

						} else {
							painelInformacoes.setInformacoesSubContrato(cadastro_mostrar);

						}
					}
				});

				arvore_contratos.setCellRenderer(new DefaultTreeCellRenderer() {
					ImageIcon icone_assinatura = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_assinatura.png"));
					ImageIcon icone_outros = new ImageIcon(
							TelaGerenciarContrato.class.getResource("/imagens/icone_outros.png"));

					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
							boolean expanded, boolean isLeaf, int row, boolean focused) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
						String s = node.getUserObject().toString();
						if ("Contrato".equals(s)) {
							setOpenIcon(icone_assinatura);
							setClosedIcon(icone_assinatura);

						} else if ("Sub-Contratos".equals(s)) {
							setOpenIcon(icone_outros);
							setClosedIcon(icone_outros);

						}

						super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, hasFocus);

						return this;
					}

				});

				arvore_contratos.setShowsRootHandles(true);
				arvore_contratos.setRootVisible(false);

				no_contratos.add(new DefaultMutableTreeNode(contrato_local.getId() + "@" + contrato_local.getCodigo()));

				for (CadastroContrato sub : lista_sub_contratos) {
					no_sub_contratos.add(new DefaultMutableTreeNode(sub.getId() + "@" + sub.getCodigo()));

				}

				painel_pai.add(arvore_contratos, "cell 0 1,grow");

				expandAllNodes(arvore_contratos, 0, arvore_contratos.getRowCount());
				
				painelInformacoes.setInformacoesSubContrato(contrato_local);


			}
		});

	}

	public void atualizarArvoreContratos() {

		new Thread() {
			@Override
			public void run() {

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						DefaultTreeModel model = (DefaultTreeModel) arvore_contratos.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

						root.removeAllChildren();

						no_contratos.removeAllChildren();
						no_sub_contratos.removeAllChildren();

						no_contratos = new DefaultMutableTreeNode("Contrato");
						no_sub_contratos = new DefaultMutableTreeNode("Sub-Contratos");

						root.add(no_contratos);
						root.add(no_sub_contratos);

						no_contratos.add(
								new DefaultMutableTreeNode(contrato_local.getId() + "@" + contrato_local.getCodigo()));

						for (CadastroContrato sub : lista_sub_contratos) {
							no_sub_contratos.add(new DefaultMutableTreeNode(sub.getId() + "@" + sub.getCodigo()));

						}

						model.reload(); // this notifies the listeners and changes the GUI

						expandAllNodes(arvore_contratos, 0, arvore_contratos.getRowCount());

					}

				});

			}
		}.start();

	}

	public class PainelInformativoSubContratos extends JPanel {

		private JLabel _lblDataContrato, _lblCorretor, _lblCompradores, _lblVendedores, _lblValorSaco, _lblQuantidade,
				_lblValorTotal;

		private JPanel isto;

		public PainelInformativoSubContratos() {

			setBackground(Color.WHITE);
			setBounds(0, 0, 500, 190);
			setLayout(new MigLayout("", "[99px][93px][85px][79px][78px][67px]", "[17px][17px][14px][17px][17px]"));

			JLabel _lblNewLabel_5 = new JLabel("Data:");
			_lblNewLabel_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
			_lblNewLabel_5.setAlignmentY(Component.TOP_ALIGNMENT);
			_lblNewLabel_5.setBorder(null);
			_lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_5, "cell 0 0,alignx right,aligny center");

			_lblDataContrato = new JLabel("data contrato");
			_lblDataContrato.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblDataContrato, "cell 1 0,alignx center,aligny center");

			JLabel _lblNewLabel_9 = new JLabel("Corretor:");
			_lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_9, "cell 0 1,alignx right,growy");

			_lblCorretor = new JLabel("corretor");
			_lblCorretor.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCorretor, "cell 1 1,alignx center,aligny center");

			JLabel _lblNewLabel_6 = new JLabel("Compradores:");
			_lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_6, "cell 0 2,alignx right,growy");

			_lblCompradores = new JLabel("compradores");
			_lblCompradores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblCompradores, "cell 1 2,alignx center,aligny center");

			JLabel _lblNewLabel_8 = new JLabel("Vendedores:");
			_lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_8, "cell 0 3,alignx right,aligny center");

			_lblVendedores = new JLabel("vendedores");
			_lblVendedores.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblVendedores, "cell 1 3,alignx center,aligny center");

			JLabel _lblnewvalorsaco = new JLabel("Valor Unidade:");
			_lblnewvalorsaco.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblnewvalorsaco, "cell 0 4,alignx right,aligny center");

			_lblValorSaco = new JLabel("valor saco");
			_lblValorSaco.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorSaco, "cell 1 4,alignx center,aligny center");

			JLabel _lblNewLabel_10 = new JLabel("Quantidade:");
			_lblNewLabel_10.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_10, "cell 0 5,alignx right,aligny center");

			_lblQuantidade = new JLabel("quantidade");
			_lblQuantidade.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblQuantidade, "cell 1 5,alignx center,aligny center");

			JLabel _lblNewLabel_11 = new JLabel("Valor Total:");
			_lblNewLabel_11.setFont(new Font("Arial", Font.PLAIN, 12));
			add(_lblNewLabel_11, "cell 0 6,alignx right,aligny center");

			_lblValorTotal = new JLabel("valortotal");
			_lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
			add(_lblValorTotal, "cell 1 6,alignx center,aligny center");

			isto = this;

			// setarInformacoesPainelCarregamentosInformativos();
		}

		public void setInformacoesSubContrato(CadastroContrato sub_contrato) {

			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {

					_lblDataContrato.setText(sub_contrato.getData_contrato());

					CadastroCliente compradores[] = sub_contrato.getCompradores();
					CadastroCliente vendedores[] = sub_contrato.getVendedores();
					CadastroCliente corretores[] = sub_contrato.getCorretores();

					String nome_corretores = "";
					String nome_vendedores = "";
					String nome_compradores = "";

					if (corretores[0] != null) {
						if (corretores[0].getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_corretores = corretores[0].getNome_empresarial();
						} else {
							nome_corretores = corretores[0].getNome_fantaia();

						}
					}
					_lblCorretor.setText(nome_corretores);

					if (compradores[0] != null) {
						if (compradores[0].getTipo_pessoa() == 0) {
							// pessoa fisica
							nome_compradores = compradores[0].getNome_empresarial();
						} else {
							nome_compradores = compradores[0].getNome_fantaia();

						}
					}
					_lblCompradores.setText(nome_compradores);

					for (CadastroCliente vendedor : vendedores) {
						if (vendedor != null) {
							if (vendedor.getTipo_pessoa() == 0) {
								// pessoa fisica
								nome_vendedores += vendedor.getNome_empresarial();
							} else {
								nome_vendedores += vendedor.getNome_fantaia();

							}
							nome_vendedores += " ,";

						}
					}
					_lblVendedores.setText(nome_vendedores);

					Locale ptBr = new Locale("pt", "BR");
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(sub_contrato.getValor_produto());

					_lblValorSaco.setText(valorString);

					NumberFormat z = NumberFormat.getNumberInstance();

					_lblQuantidade.setText(z.format(sub_contrato.getQuantidade()) + " " + sub_contrato.getMedida());

					String valor_total = NumberFormat.getCurrencyInstance(ptBr).format(sub_contrato.getValor_a_pagar());
					_lblValorTotal.setText(valor_total);

					isto.repaint();
					isto.updateUI();

				}
			});
		}
	}

	public ChartPanel criarGrafico() {

		DefaultPieDataset pizza = new DefaultPieDataset();

		ArrayList<Double> somatoria_sub_contratos = new ArrayList<>();

		for (CadastroContrato sub : lista_sub_contratos) {

			double quantidade_sacos = 0;
			double quantidade_quilogramas = 0;

			if (sub.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = sub.getQuantidade();
				quantidade_quilogramas = sub.getQuantidade() * 60;
			} else if (sub.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas = sub.getQuantidade();
				quantidade_sacos = sub.getQuantidade() / 60;

			}
			somatoria_sub_contratos.add(quantidade_sacos);
		}

		double somatoria_total = 0;
		for (Double i : somatoria_sub_contratos) {
			somatoria_total += i;
		}

		// aquantidade total do contrato original
		double quantidade_sacos_contrato_original = 0;
		double quantidade_quilogramas_contrato_original = 0;

		if (contrato_local.getMedida().equalsIgnoreCase("Sacos")) {
			quantidade_sacos_contrato_original = contrato_local.getQuantidade();
			quantidade_quilogramas_contrato_original = contrato_local.getQuantidade() * 60;
		} else if (contrato_local.getMedida().equalsIgnoreCase("KG")) {
			quantidade_quilogramas_contrato_original = contrato_local.getQuantidade();
			quantidade_sacos_contrato_original = contrato_local.getQuantidade() / 60;

		}

		double diferenca = 0;
		if (somatoria_total == quantidade_sacos_contrato_original) {

		} else {
			diferenca = quantidade_sacos_contrato_original - somatoria_total;
		}

		for (CadastroContrato sub : lista_sub_contratos) {

			double quantidade_sacos = 0;
			double quantidade_quilogramas = 0;

			if (sub.getMedida().equalsIgnoreCase("Sacos")) {
				quantidade_sacos = sub.getQuantidade();
				quantidade_quilogramas = sub.getQuantidade() * 60;
			} else if (sub.getMedida().equalsIgnoreCase("KG")) {
				quantidade_quilogramas = sub.getQuantidade();
				quantidade_sacos = sub.getQuantidade() / 60;

			}
			pizza.setValue(sub.getCodigo(), quantidade_sacos);
		}

		if (diferenca > 0) {
			pizza.setValue("0000000", diferenca);

		}

		JFreeChart grafico = ChartFactory.createPieChart("Sub-Contratos", pizza, true, true, false);
		ChartPanel painel = new ChartPanel(grafico);
		painel.setBackground(Color.white);
		painel.setBounds(0, 0, 360, 280);

		return painel;

	}

	public void setarPainelGanhosPotenciais() {

		double valor_total_contrato_original = Double.parseDouble(contrato_local.getValor_a_pagar().toPlainString());
		double valor_total_sub_contratos = 0;

		for (CadastroContrato sub : lista_sub_contratos) {

			double valor_local = Double.parseDouble(sub.getValor_a_pagar().toPlainString());
			valor_total_sub_contratos += valor_local;
		}
		double valor_total_diferenca_contratos = 0;
		if (valor_total_sub_contratos == 0) {
			valor_total_diferenca_contratos = 0;

		} else {
			valor_total_diferenca_contratos = valor_total_contrato_original - valor_total_sub_contratos;

		}

		double valor_total_comissoes = 0;

		if (contrato_local.getComissao() == 1) {
			double valor_local = Double.parseDouble(contrato_local.getValor_comissao().toPlainString());
			valor_total_comissoes += valor_local;
		}

		for (CadastroContrato sub : lista_sub_contratos) {

			if (sub.getComissao() == 1) {
				double valor_local = Double.parseDouble(sub.getValor_comissao().toPlainString());
				valor_total_comissoes += valor_local;
			}

		}

		double valor_total_ganhos_potenciais = valor_total_diferenca_contratos + valor_total_comissoes;

		Locale ptBr = new Locale("pt", "BR");

		JPanel painelInfoGanhos = new JPanel();
		painelInfoGanhos.setBackground(Color.WHITE);
		painelInfoGanhos.setForeground(Color.WHITE);
		painelInfoGanhos.setBounds(964, 347, 360, 165);
		painelSubContratos.add(painelInfoGanhos);
		painelInfoGanhos.setLayout(new MigLayout("", "[][]", "[][][][][][]"));

		JLabel lblNewLabel_20 = new JLabel("Valor Total Contrato Original:");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_20, "cell 0 0");

		JLabel lblValorTotalContrato = new JLabel("R$ 1.000.000.000.00");
		lblValorTotalContrato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_contrato_original);
		lblValorTotalContrato.setText(valorString);
		painelInfoGanhos.add(lblValorTotalContrato, "cell 1 0");

		JLabel lblNewLabel_22 = new JLabel("Valor Total Sub-Contratos:");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_22, "cell 0 1");

		JLabel lblValorTotalSubContratos = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalSubContratos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalSubContratos, "cell 1 1");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_sub_contratos);
		lblValorTotalSubContratos.setText(valorString);

		JLabel lblNewLabel_21 = new JLabel("Diferença entre Contratos:");
		lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_21, "cell 0 2,alignx left");

		JLabel lblValorTotalDiferencaContratos = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalDiferencaContratos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalDiferencaContratos, "cell 1 2");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_diferenca_contratos);
		lblValorTotalDiferencaContratos.setText(valorString);

		JLabel lblNewLabel_23 = new JLabel("Valor Total das Comissões:");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_23, "cell 0 3");

		JLabel lblValorTotalComissoes = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalComissoes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalComissoes, "cell 1 3,alignx left");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_comissoes);
		lblValorTotalComissoes.setText(valorString);

		JLabel lblNewLabel_24 = new JLabel("Ganhos em potenciais:");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblNewLabel_24, "cell 0 5");

		JLabel lblValorTotalGanhosPotenciais = new JLabel("R$ 1.000.000.000,00");
		lblValorTotalGanhosPotenciais.setFont(new Font("Tahoma", Font.PLAIN, 14));
		painelInfoGanhos.add(lblValorTotalGanhosPotenciais, "cell 1 5");
		valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor_total_ganhos_potenciais);
		lblValorTotalGanhosPotenciais.setText(valorString);
	}

	public void setInformacoesAditivos() {

		modelo_aditivos.setNumRows(0);
		GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();

		lista_aditivos = gerenciar.getAditivos(contrato_local.getId());

		/*
		 * modelo_aditivos.addColumn("Id"); modelo_aditivos.addColumn("Status");
		 * modelo_aditivos.addColumn("Data"); modelo_aditivos.addColumn("Texto");
		 * 
		 */
		for (CadastroAditivo aditivo : lista_aditivos) {
			String data = "", texto = "", nome_arquivo;

			int id = aditivo.getId_aditivo();
			int status = aditivo.getStatus();
			data = aditivo.getData();
			texto = aditivo.getTexto();
			nome_arquivo = aditivo.getNome_arquivo();

			String sts = "";
			if (status == 1) {
				sts = "Recolher Assinatura";
			} else {
				sts = "Assinado";
			}

			modelo_aditivos.addRow(new Object[] { id, sts, data, texto, nome_arquivo });

		}
	}

	

	public void transferirPagamentoContratual(int id_pagamento_contratual) {

		TelaConfirmarTransferenciaPagamentoContratual tela = new TelaConfirmarTransferenciaPagamentoContratual(
				contrato_local, id_pagamento_contratual);
		tela.setTelaPag(isto);
		tela.setVisible(true);

	}

	public void caminho_salvar_comprovante_pagamento(String caminho) {
		this.caminho_salvar_comprovante_pagamento = caminho;
	}

	public void atualizarListaTarefas() {
		getTarefas();
	}

	public void concluir_contrato() {

		if (contrato_local.getSub_contrato() != 1) {
			// verifique se os sub_contratos desde contratos estao finalizados
			GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
			ArrayList<CadastroContrato> sub_contratos = gerenciar.getSubContratos(contrato_local.getId());

			boolean tem_contratos_nao_finalizados = false;

			for (CadastroContrato sub : sub_contratos) {
				if (sub.getStatus_contrato() != 3) {
					tem_contratos_nao_finalizados = true;
					break;
				}

			}

			if (!tem_contratos_nao_finalizados) {

				concluir_travar_contrato();

			} else {

				JOptionPane.showMessageDialog(null,
						"Há sub-contratos deste contrato ainda em abertos!\nFinalize-os para poder concluir o contrato original");

			}
		} else {
			concluir_travar_contrato();
		}

	}

	public void concluir_travar_contrato() {
		if (JOptionPane.showConfirmDialog(isto, "Concluir o contrato e bloquea-ló?", "Concluir",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			boolean pagamento_concluido = false;
			boolean carregamento_concluido = false;
			boolean nf_concluido = false;

			if (registro_pagamento_global.getValor_total_pagamentos_restantes() > 0) {
				pagamento_concluido = false;
			} else {
				pagamento_concluido = true;
			}
			// verifica se o carregamento foi concluido
			if (registro_carregamento_global.getQuantidade_restante() > 0) {
				carregamento_concluido = false;
			} else {
				carregamento_concluido = true;
			}

			// verifica se as nf foram concluidas
			if (registro_carregamento_global.getQuantidade_restante_nf() > 0) {
				nf_concluido = false;
			} else {
				nf_concluido = true;
			}

			if (pagamento_concluido && carregamento_concluido && nf_concluido) {

				TelaFinalizarContrato tela = new TelaFinalizarContrato();
				tela.setTelaPai(isto);
				tela.apresentarContratoCompleto(contrato_local, registro_pagamento_global,
						registro_carregamento_global);
				tela.setVisible(true);
			} else{
				
				if (JOptionPane.showConfirmDialog(isto, "Requisitos do contrato não cumpridos, deseja continuar?", "Concluir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				
					TelaFinalizarContratoIncompleto tela = new TelaFinalizarContratoIncompleto();
					tela.setTelaPai(isto);
					tela.apresentarContratoIncompleto(contrato_local, registro_pagamento_global, registro_carregamento_global);
					tela.setVisible(true);
					
				}else {
					
				}
				
			}

		} else {

		}

	}

	public void travarContrato() {
		atualizarContratoLocal();
		if (contrato_local.getStatus_contrato() == 3) {

			for (int i = 0; i < painelCarregamento.getComponentCount(); i++) {

				Component c = painelCarregamento.getComponent(i);
				if (c instanceof JButton)
					c.setEnabled(false);

			}

			for (int i = 0; i < painelPagamentos.getComponentCount(); i++) {

				Component c = painelPagamentos.getComponent(i);
				if (c instanceof JButton)
					c.setEnabled(false);

			}

			for (int i = 0; i < painelListaTarefas.getComponentCount(); i++) {

				Component c = painelListaTarefas.getComponent(i);
				if (c instanceof JButton)
					c.setEnabled(false);

			}

			for (int i = 0; i < painelComprovantes.getComponentCount(); i++) {

				Component c = painelComprovantes.getComponent(i);
				if (c instanceof JButton)
					c.setEnabled(false);

			}

			if (contrato_local.getSub_contrato() != 1) {
				for (int i = 0; i < painelSubContratos.getComponentCount(); i++) {

					Component c = painelSubContratos.getComponent(i);
					if (c instanceof JButton) {
					  if( !((JButton) c).getText().equals("Abrir")  )
					 	c.setEnabled(false);
						
					}

				}
			}

			jPopupMenuTabelCarregamento = null;

			jPopupMenuDocumentos = null;

			jPopupMenuTabelPagamentos = null;
			
			jPopupMenuTabelAditivos = null;

		}
		atualizarContratoLocal();

		//setarInformacoesPainelPrincipal();

	}

	public void destravarContrato() {
		atualizarContratoLocal();
		//setarInformacoesPainelPrincipal();

		for (int i = 0; i < painelCarregamento.getComponentCount(); i++) {

			Component c = painelCarregamento.getComponent(i);
			if (c instanceof JButton)
				c.setEnabled(true);

		}

		for (int i = 0; i < painelPagamentos.getComponentCount(); i++) {

			Component c = painelPagamentos.getComponent(i);
			if (c instanceof JButton)
				c.setEnabled(true);

		}

		for (int i = 0; i < painelListaTarefas.getComponentCount(); i++) {

			Component c = painelListaTarefas.getComponent(i);
			if (c instanceof JButton)
				c.setEnabled(true);

		}

		for (int i = 0; i < painelComprovantes.getComponentCount(); i++) {

			Component c = painelComprovantes.getComponent(i);
			if (c instanceof JButton)
				c.setEnabled(true);

		}

		if (contrato_local.getSub_contrato() != 1) {
			for (int i = 0; i < painelSubContratos.getComponentCount(); i++) {

				Component c = painelSubContratos.getComponent(i);
				if (c instanceof JButton)
					c.setEnabled(true);

			}
		}

		setMenuCarregamento();

		setMenuDocumentos();

		setMenuPagamentos();
		
		setMenuAditivos();

	}

	public void setMenuDocumentos() {
		
		if(contrato_local.getStatus_contrato() != 3) {
		jPopupMenuDocumentos = new JPopupMenu();
		JMenuItem jMenuItemVizualizar = new JMenuItem();
		JMenuItem jMenuItemExcluir = new JMenuItem();
		JMenuItem jMenuItemEnviar = new JMenuItem();
		jMenuItemEnviar.setText("Enviar");
		
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

				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato()
						+ "\\" + "comprovantes\\" + nome_official;
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop desktop = Desktop.getDesktop();
						File myFile = new File(caminho_completo);
						desktop.open(myFile);
					} catch (IOException ex) {
					}
				}
			}
		});

		jMenuItemExcluir.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este comprovante", "Exclusão",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					String nome_arquivo = no_selecionado.getUserObject().toString();
					String quebra[] = nome_arquivo.split("@");
					String id = quebra[0];
					int i_id = Integer.parseInt(id);

					String nome_official = "";
					for (int i = 1; i < quebra.length; i++) {
						nome_official += quebra[i];
					}
					String unidade_base_dados = configs_globais.getServidorUnidade();
					String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato()
							+ "\\" + "comprovantes\\" + nome_official;

					boolean excluido = new ManipularTxt().apagarArquivo(caminho_completo);
					if (excluido) {

						GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
						boolean excluir_documento = gerenciar_docs.removerDocumento(i_id);

						if (excluir_documento) {
							JOptionPane.showMessageDialog(null, "Comprovante Excluido!");

						} else {
							JOptionPane.showMessageDialog(null,
									"Arquivo fisico apagado, mas as informações\ndeste comprovante ainda estão no \nConsulte o administrador");

						}

						atualizarArvoreDocumentos();

					} else {
						JOptionPane.showMessageDialog(null, "Erro ao excluir o comprovante\nConsulte o administrador!");
					}

				} else {

				}
			}
		});
		
		jMenuItemEnviar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				String nome_arquivo = no_selecionado.getUserObject().toString();

				String quebra[] = nome_arquivo.split("@");

				String nome_official = "";
				for (int i = 1; i < quebra.length; i++) {
					nome_official += quebra[i];
				}

				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_completo = unidade_base_dados + "\\" + contrato_local.getCaminho_diretorio_contrato()
						+ "\\" + "comprovantes\\" + nome_official;
				
				   File doc = new File(caminho_completo);
				    
				    TelaEscolha tela = new TelaEscolha(2, contrato_local, doc);
				    tela.setVisible(true);
			}
		});

		
		
		jPopupMenuDocumentos.add(jMenuItemVizualizar);
		jPopupMenuDocumentos.add(jMenuItemExcluir);
		jPopupMenuDocumentos.add(jMenuItemEnviar);
		
		arvore_documentos.addMouseListener(new java.awt.event.MouseAdapter() {
			// Importe a classe java.awt.event.MouseEvent
			public void mouseClicked(MouseEvent e) {
				// Se o botão direito do mouse foi pressionado
				if (e.getButton() == MouseEvent.BUTTON3) {
					// Exibe o popup menu na posição do mouse.
					jPopupMenuDocumentos.show(arvore_documentos, e.getX(), e.getY());
				}
			}
		});
		
		}
	}

	public void setMenuPagamentos() {
		jPopupMenuTabelPagamentos = new JPopupMenu();
		JMenuItem jMenuItemInserirComprovantePagamento = new JMenuItem();
		JMenuItem jMenuItemInserirTransferirPagamento = new JMenuItem();
		JMenuItem jMenuItemReplicarPagamento = new JMenuItem();

		jMenuItemInserirComprovantePagamento.setText("Inserir Comprovante");
		jMenuItemInserirTransferirPagamento.setText("Transferir Pagamento");
		jMenuItemReplicarPagamento.setText("Replicar Pagamento");

		jMenuItemInserirComprovantePagamento.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				int index = table_pagamentos_contratuais.getSelectedRow();
				String id = table_pagamentos_contratuais.getValueAt(index, 0).toString();

				importarComprovantePagamento(Integer.parseInt(id));
			}
		});

		jMenuItemReplicarPagamento.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				if (contrato_local.getSub_contrato() != 1) {
					int index = table_pagamentos_contratuais.getSelectedRow();

					String id = table_pagamentos_contratuais.getValueAt(index, 0).toString();
					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					CadastroContrato.CadastroPagamentoContratual pagamento = gerenciar
							.getPagamentoContratual(Integer.parseInt(id));

					if (pagamento != null) {
						TelaReplicarPagamento replicar = new TelaReplicarPagamento(contrato_local, pagamento);
						replicar.setTelaPai(isto);
						replicar.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao consultar este pagamento!\nConsulte o administrador do sistema!");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Não é possivel replicar um pagamento a partir de um sub-contrato");

				}

			}
		});

		jMenuItemInserirTransferirPagamento.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				if (contrato_local.getSub_contrato() != 1) {
					int index = table_pagamentos_contratuais.getSelectedRow();
					String id = table_pagamentos_contratuais.getValueAt(index, 0).toString();
					int i_id = Integer.parseInt(id);
					transferirPagamentoContratual(i_id);
				} else {
					JOptionPane.showMessageDialog(null, "Não é possivel transferir um pagamento de sub-contrato");
				}

			}
		});

		jPopupMenuTabelPagamentos.add(jMenuItemInserirComprovantePagamento);
		jPopupMenuTabelPagamentos.add(jMenuItemInserirTransferirPagamento);
		jPopupMenuTabelPagamentos.add(jMenuItemReplicarPagamento);
	}

	public void setMenuCarregamento() {
		jPopupMenuTabelCarregamento = new JPopupMenu();
		JMenuItem jMenuItemInserirComprovante = new JMenuItem();
		JMenuItem jMenuItemVizualizarNFAe = new JMenuItem();
		JMenuItem jMenuItemReplicarCarregamento = new JMenuItem();

		jMenuItemInserirComprovante.setText("Inserir Comprovante");
		jMenuItemVizualizarNFAe.setText("Vizualizar NFA-e");
		jMenuItemReplicarCarregamento.setText("Replicar");
		
		
		jMenuItemInserirComprovante.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				int index = table_carregamento.getSelectedRow();
				String id = table_carregamento.getValueAt(index, 0).toString();

				importarComprovanteCarregamento(Integer.parseInt(id));
			}
		});

		jMenuItemReplicarCarregamento.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {

				if (contrato_local.getSub_contrato() != 1) {
					int index = table_carregamento.getSelectedRow();
					String id = table_carregamento.getValueAt(index, 0).toString();

					GerenciarBancoContratos gerenciar = new GerenciarBancoContratos();
					CadastroContrato.Carregamento carregamento = gerenciar.getCarregamento(Integer.parseInt(id));

					if (carregamento != null) {
						TelaReplicarCarregamento replicar = new TelaReplicarCarregamento(contrato_local, carregamento);
						replicar.setTelaPai(isto);
						replicar.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro ao consultar este carregamento!\nConsulte o administrador do sistema!");
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Não é possivel replicar um carregamento apartir de um sub-contrato");

				}
			}
		});

		jMenuItemVizualizarNFAe.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {
				int index = table_carregamento.getSelectedRow();
				String id = table_carregamento.getValueAt(index, 0).toString();
				String caminho_nota = table_carregamento.getValueAt(index, 12).toString();
				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_abrir = unidade_base_dados + "\\" + caminho_nota;

				if (Desktop.isDesktopSupported()) {
					try {
						Desktop desktop = Desktop.getDesktop();
						File myFile = new File(caminho_abrir);
						desktop.open(myFile);
					} catch (IOException ex) {
					}
				}

			}
		});

		
		jPopupMenuTabelCarregamento.add(jMenuItemInserirComprovante);
		jPopupMenuTabelCarregamento.add(jMenuItemVizualizarNFAe);
		jPopupMenuTabelCarregamento.add(jMenuItemReplicarCarregamento);

		
	}
	
	public void setMenuAditivos() {
		if(contrato_local.getStatus_contrato() != 3) {
		 jPopupMenuTabelAditivos = new JPopupMenu();
			JMenuItem jMenuItemAssinarAditivo = new JMenuItem();
			JMenuItem jMenuItemRevogarAssinatura = new JMenuItem();

			JMenuItem jMenuItemVizualizarAditivo = new JMenuItem();
			JMenuItem jMenuItemExcluirAditivo = new JMenuItem();

			jMenuItemAssinarAditivo.setText("Assinar");
			jMenuItemVizualizarAditivo.setText("Vizualizar");
			jMenuItemExcluirAditivo.setText("Excluir");
			jMenuItemExcluirAditivo.setText("Excluir");
			jMenuItemRevogarAssinatura.setText("Revogar");

			jMenuItemRevogarAssinatura.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();
					String status = table_aditivos.getValueAt(index, 1).toString();
					String id = table_aditivos.getValueAt(index, 0).toString();

					int i_id = Integer.parseInt(id);

					if (status.equals("Recolher Assinatura")) {

					} else if (status.equals("Assinado")) {
						revogarAssinaturaAditivo(i_id);
						setInformacoesAditivos();
					}

				}
			});

			jMenuItemAssinarAditivo.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();
					String id = table_aditivos.getValueAt(index, 0).toString();
					int i_id = Integer.parseInt(id);

					assinarAditivo(i_id);
				}
			});

			jMenuItemVizualizarAditivo.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();

					String unidade_base_dados = configs_globais.getServidorUnidade();
					String nome_arquivo = table_aditivos.getValueAt(index, 4).toString();
					String caminho_salvar = unidade_base_dados + "\\"
							+ contrato_local.getCaminho_diretorio_contrato();

					String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop desktop = Desktop.getDesktop();
							File myFile = new File(caminho_completo);
							desktop.open(myFile);
						} catch (IOException ex) {
						}
					}

				}
			});

			jMenuItemExcluirAditivo.addActionListener(new java.awt.event.ActionListener() {
				// Importe a classe java.awt.event.ActionEvent
				public void actionPerformed(ActionEvent e) {
					int index = table_aditivos.getSelectedRow();
					String id = table_aditivos.getValueAt(index, 0).toString();
					int i_id = Integer.parseInt(id);

					if (JOptionPane.showConfirmDialog(isto, "Deseja Excluir este Aditivo?", "Excluir aditivo",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

						GerenciarBancoAditivos gerenciar = new GerenciarBancoAditivos();
						boolean excluir = gerenciar.removerAditivo(contrato_local.getId(), i_id);

						if (excluir) {
							// excluir o arquivo
							String unidade_base_dados = configs_globais.getServidorUnidade();
							String nome_arquivo = table_aditivos.getValueAt(index, 4).toString();
							String caminho_salvar = unidade_base_dados + "\\"
									+ contrato_local.getCaminho_diretorio_contrato();

							String caminho_completo = caminho_salvar + "\\comprovantes\\" + nome_arquivo;

							ManipularTxt manipular = new ManipularTxt();
							boolean arquivo_excluido = manipular.apagarArquivo(caminho_completo);
							if (arquivo_excluido) {
								JOptionPane.showMessageDialog(null, "O aditivo foi excluido com sucesso!");
								setInformacoesAditivos();

							} else {
								JOptionPane.showMessageDialog(null,
										"O aditivo foi excluido, porem o arquivo fisico ainda esta salvo\nConsulte o adiministrador");

							}

						} else {
							JOptionPane.showMessageDialog(null,
									"Erro ao excluir o aditivo\nConsulte o adiministrador");

						}

					} else {

					}

				}
			});

			jPopupMenuTabelAditivos.add(jMenuItemAssinarAditivo);
			jPopupMenuTabelAditivos.add(jMenuItemVizualizarAditivo);
			jPopupMenuTabelAditivos.add(jMenuItemExcluirAditivo);
			jPopupMenuTabelAditivos.add(jMenuItemRevogarAssinatura);

			table_aditivos.addMouseListener(new java.awt.event.MouseAdapter() {
				// Importe a classe java.awt.event.MouseEvent
				public void mouseClicked(MouseEvent e) {
					// Se o botão direito do mouse foi pressionado
					if (e.getButton() == MouseEvent.BUTTON3) {
						// Exibe o popup menu na posição do mouse.
						jPopupMenuTabelAditivos.show(table_aditivos, e.getX(), e.getY());
					}
				}
			});
	}
	}
	
	public void atualizarTabelaTarefas() {
		getTarefas();
	}
	
}
