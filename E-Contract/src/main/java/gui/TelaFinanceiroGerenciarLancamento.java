package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroDocumento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroRecibo;
import main.java.cadastros.CadastroTarefaGeral;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoEmprestimo;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoDocumento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoParcelasEmprestimo;
import main.java.conexaoBanco.GerenciarBancoRecibos;
import main.java.conexaoBanco.GerenciarBancoTarefaGeral;
import main.java.gui.TelaRomaneios.TarefaTableModel;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.manipular.ConverterPdf;
import main.java.manipular.EditarContratoEmprestimo;
import main.java.manipular.ManipularTxt;
import main.java.outros.DadosGlobais;
import main.java.outros.GetData;
import main.java.relatoria.RelatorioLancamento;
import main.java.tratamento_proprio.Log;
import main.java.views_personalizadas.TelaEscolha;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaFinanceiroGerenciarLancamento extends JFrame {

	private String caminho_global;
	private JTabbedPane painelPrincipal;
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private JPanel painelTarefas = new JPanel();
	private JTable table_tarefas;
	private JComboBox cBTipoDocumento;
	private JTree arvore_documentos;
	private EditarContratoEmprestimo editar_word;
	private JPanel panel_docs;
	DefaultMutableTreeNode no_comprovantes;
	DefaultMutableTreeNode no_docs_pessoais;
	DefaultMutableTreeNode no_docs_originais;
	private JButton btnEmitirContrato;
	DefaultMutableTreeNode no_outros;
	private DefaultMutableTreeNode no_selecionado;
	private JScrollPane scrollPaneTarefas;
	private TarefaTableModel modelo_tarefa = new TarefaTableModel();
	private ArrayList<CadastroTarefaGeral> lista_tarefas = new ArrayList<>();
	private ArrayList<CadastroRecibo> lista_recibos = new ArrayList<>();

	private JTextField entCaminhoDocumento;
	private JTextField entNomeDocumento;
	private JTextArea entDescricaoDocumento;
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Tipo Lançamento:");
	private final JLabel lblNewLabel_1 = new JLabel("Centro de Custo:");
	private final JLabel lblNewLabel_1_1_1 = new JLabel("Cliente/Fornecedor:");
	private final JLabel entTipoLancamento = new JLabel("");
	private final JLabel entCentroCusto = new JLabel("Centro Custo");
	private final JLabel entClienteFornecedor = new JLabel("Cliente/Fornecedor");
	private final JLabel lblNewLabel_3 = new JLabel("As partes:");
	private final JLabel lblNewLabel_3_1 = new JLabel("A Conta:");
	private final JLabel lblNewLabel_1_1_1_1 = new JLabel("Grupo Contas:");
	private final JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Conta:");
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private final JLabel lblData = new JLabel("Data:");
	private final JLabel lblNewLabel_1_1_1_1_2_1 = new JLabel("Valor:");
	private final JLabel lblNewLabel_1_1_1_1_2_1_1 = new JLabel("Data Primeiro Vencimento:");
	private final JLabel lblStatus = new JLabel("Status:");
	private final JLabel entStatus = new JLabel("Status");
	private final JLabel entData = new JLabel("Data");
	private final JLabel entGrupoContas = new JLabel("Grupo Contas");
	private final JLabel entConta = new JLabel("Conta");
	private final JLabel entValor = new JLabel("Valor");
	private final JLabel entDataVencimento = new JLabel("Data Vencimento");
	private Lancamento lancamento_global;
	private final JButton btnNewButton = new JButton("Editar");
	Locale ptBr = new Locale("pt", "BR");
	private final JLabel lblNewLabel_3_2 = new JLabel("Devedor:");
	private final JLabel lblNewLabel_3_2_1 = new JLabel("Recebedor:");
	private SwingController controllerParcela = null;
	private SwingViewBuilder factoryParcela;
	private String servidor_unidade;
	private final JLabel lblNewLabel_1_1_1_1_2_1_1_1 = new JLabel("Numero de Parcelas:");
	private final JLabel lblNewLabel_1_1_1_1_2_1_1_1_1_1 = new JLabel("Intervalo:");
	private final JLabel entNumeroParcelas = new JLabel("Número de Parcelas");
	private final JLabel entIntervalo = new JLabel("Intervalo");
	private final JLabel lblPrioridade = new JLabel("Prioridade:");
	private final JLabel entPrioridade = new JLabel("Prioridade");
	private final JPanel panel_1 = new JPanel();
	private final JLabel lblNewLabel_2 = new JLabel("Parcelas");
	private final JScrollPane scrollParcelas;
	private final JPanel panel_2 = new JPanel();
	private final JScrollPane scrollPagamentos;
	private final JLabel lblNewLabel_4 = new JLabel("Pagamentos");
	private JTable tabela_pagamentos, tabela_parcelas;
	private final JButton btnInserirParcela = new JButton("Inserir");
	private ParcelaTableModel modelo_parcelas = new ParcelaTableModel();
	private ParcelaEmprestimoTableModel modelo_parcelas_emprestimo = new ParcelaEmprestimoTableModel();

	private ArrayList<Parcela> lista_parcelas = new ArrayList<>();
	private ArrayList<ParcelaEmprestimo> lista_parcelas_emprestimo = new ArrayList<>();

	private PagamentoTableModel modelo_pagamentos = new PagamentoTableModel();
	private ArrayList<FinanceiroPagamento> lista_pagamentos = new ArrayList<>();

	private PagamentoEmprestimoTableModel modelo_pagamentos_emprestimo = new PagamentoEmprestimoTableModel();
	private ArrayList<FinanceiroPagamentoEmprestimo> lista_pagamentos_emprestimo = new ArrayList<>();
	private CadastroCliente destinatario_nf;
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final JPanel panel_6 = new JPanel();
	private final JPanel painelVizualizarDocumento = new JPanel();
	private final JPanel panel_8 = new JPanel();
	private final JPanel panel_9 = new JPanel();
	private final JPanel painelObservacao = new JPanel();
	private final JPanel painelCalculos = new JPanel();
	private final JLabel lblNewLabel_5 = new JLabel("Observação:");
	private final JTextArea entObservacaoParcela = new JTextArea();
	private final JLabel lblNewLabel_5_1 = new JLabel("Cálculos:");
	private final JLabel lblNewLabel_6_1 = new JLabel("Valor Total das Parcelas:");
	private final JLabel lblValorTotalDasParcelas = new JLabel("0000");
	private final JPanel panel_7 = new JPanel();
	private final JLabel lblNewLabel_7 = new JLabel("Descrição:");
	private final JEditorPane entDescricaoLancamento = new JEditorPane();
	private final JLabel lblNewLabel_7_1 = new JLabel("Observação:");
	private final JEditorPane entObservacaoLancamento = new JEditorPane();
	private final JLabel lblNewLabel_8 = new JLabel(" ");
	private final JButton btnEditar = new JButton("Editar");
	private final JButton btnInserirParcela_1_1 = new JButton("Excluir");
	private final JPanel panel_10 = new JPanel();
	private final JPanel panel_11 = new JPanel();
	private final JPanel panel_12 = new JPanel();
	private final JLabel lblNewLabel_5_2 = new JLabel("Observação:");
	private final JTextArea entObservacaoPagamento = new JTextArea();
	private final JLabel lblNewLabel_5_1_1 = new JLabel("Cálculos:");
	private final JLabel lblNewLabel_6_1_1 = new JLabel("Valor Total dos Pagamentos:");
	private final JLabel lblValorTotalDosPagamentos = new JLabel("0000");
	private final JPanel panel_13 = new JPanel();
	private final JButton btnNewButton_1 = new JButton("Inserir");
	private final JButton btnNewButton_2 = new JButton("Editar");
	private final JButton btnNewButton_3 = new JButton("Excluir");
	private final JPanel panel_14 = new JPanel();
	private final JPanel panel_15 = new JPanel();
	private final JPanel panel_16 = new JPanel();
	private final JPanel panel_17 = new JPanel();
	private final JLabel lblNewLabel_6_1_1_1 = new JLabel("Valor Total Restante:");
	private final JLabel lblValorTotalRestante = new JLabel("0000");
	private final JLabel lblNewLabel_1_1_1_1_2_1_1_2 = new JLabel("Identificador Geral:");
	private final JLabel entIdentificadorGeral = new JLabel("Data Vencimento");
	private JPanel painel_vizualizar;
	private final JButton btnNewButton_4 = new JButton("Vizualizar");
	private final JButton btnNewButton_2_1 = new JButton("Vizualizar");
	private final JButton btnVizualizar = new JButton("Vizualizar");
	private SwingController controller = null;
	private SwingViewBuilder factory;
	private final JButton btnMudarArquivo = new JButton("Mudar Arquivo");
	private final TelaFinanceiroGerenciarLancamento isto;
	private final JButton btnMudarArquivo_1 = new JButton("Mudar Arquivo");
	private final JButton btnNewButton_2_1_1 = new JButton("Mudar Arquivo");
	private final JPanel painelAsPartes = new JPanel();
	private final JLabel lblNewLabel_7_1_1 = new JLabel("Ao Contador:");
	private final JComboBox cbStatusAoContador = new JComboBox();
	private final JPanel panel_19 = new JPanel();
	private final JPanel panel_20 = new JPanel();
	private final JButton btnRelatrio = new JButton("Relatório");
	private final JLabel lblNewLabel_7_1_1_1 = new JLabel("Destinatário da NF:");
	private final JComboBox cBDestinatarioDaNF = new JComboBox();
	private final JButton btnSelecionarDestinatario = new JButton("Selecionar");
	private final JLabel lblNewLabel_8_1 = new JLabel("Lista de Tarefas");
	private final JButton btnEnviarDiretoAo = new JButton("Enviar Direto ao Contador");
	private final JLabel lblNewLabel_6 = new JLabel("Recibos:");
	private final JButton btnNewButton_6 = new JButton("Emitir Recibo");
	private final JPanel panel_21 = new JPanel();
	private JScrollPane scrollPaneRecibos ;
	private  JTable table_recibos;
	private  ReciboTableModel modelo_recibos = new ReciboTableModel();
	private final JPanel panel_22 = new JPanel();
	private final JButton btnNewButton_7 = new JButton("Excluir");
	private final JButton btnNewButton_7_1 = new JButton("Atualizar Status");
	private final JLabel lblNewLabel_18_1 = new JLabel("Adicionar Novo Documento:");
	private final JLabel lblNewLabel_6_1_1_2 = new JLabel("Juros Total:");
	private final JLabel lblValorTotalJuros = new JLabel("0000");

	public TelaFinanceiroGerenciarLancamento(Lancamento lancamento, Window janela_pai) {

		setResizable(true);
		isto = this;
		lancamento_global = lancamento;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		getDadosGlobais();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		DadosGlobais dados = DadosGlobais.getInstance();

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);

		if (lancamento.getTipo_lancamento() == 0) {
			setTitle("E-Contract - Gerenciar Lançamento ID: " + lancamento.getId_lancamento() +" - DESPESA");

		} else if (lancamento.getTipo_lancamento() == 1) {
			setTitle("E-Contract - Gerenciar Lançamento ID: " + lancamento.getId_lancamento() + " - RECEITA");

		} else if (lancamento.getTipo_lancamento() == 3) {
			setTitle("E-Contract - Gerenciar EMPRÉSTIMO ID: " + lancamento.getId_lancamento());

		}
		painelPrincipal = new JTabbedPane();
		setContentPane(painelPrincipal);
		painelDadosIniciais.setBackground(new Color(0, 51, 0));
		painelPagamentos.setBackground(new Color(255, 255, 255));

		painelTarefas.setBackground(new Color(0, 102, 102));
		painelPrincipal.addTab("Tarefas", painelTarefas);
		painelTarefas.setLayout(new MigLayout("", "[][][grow]", "[][grow][]"));

		table_tarefas = new JTable(modelo_tarefa);
		table_tarefas.setBackground(Color.WHITE);
		table_tarefas.setRowHeight(30);

		table_tarefas.setBackground(Color.white);

		scrollPaneTarefas = new JScrollPane(table_tarefas);
		scrollPaneTarefas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setOpaque(true);
		scrollPaneTarefas.getViewport().setBackground(Color.white);
		lblNewLabel_8_1.setForeground(Color.WHITE);
		lblNewLabel_8_1.setFont(new Font("SansSerif", Font.BOLD, 24));

		painelTarefas.add(lblNewLabel_8_1, "cell 0 0 2 1");
		scrollPaneTarefas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTarefas.setBackground(Color.WHITE);
		scrollPaneTarefas.setAutoscrolls(true);
		painelTarefas.add(scrollPaneTarefas, "cell 0 1 3 1,grow");

		// adiciona novos paines e suas abas
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel.setBackground(Color.WHITE);

		painelDadosIniciais.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[3px][][grow]", "[grow][8px][]"));

		panel.add(lblNewLabel_8, "cell 0 0,alignx left,aligny top");
		panel_7.setBackground(new Color(255, 255, 204));
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel.add(panel_7, "cell 2 2,grow");
		panel_7.setLayout(new MigLayout("", "[][grow][grow]", "[50px:n][100px:n][][][]"));
		lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_7.add(lblNewLabel_7, "cell 0 0");
		entDescricaoLancamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel_7.add(entDescricaoLancamento, "cell 1 0 2 1,grow");
		lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_7.add(lblNewLabel_7_1, "cell 0 1");
		entObservacaoLancamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel_7.add(entObservacaoLancamento, "cell 1 1 2 1,grow");
		lblNewLabel_7_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_7.add(lblNewLabel_7_1_1_1, "cell 0 2,alignx trailing");
		cBDestinatarioDaNF.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					if (evt.getItem().equals("Indefinido")) {

						destinatario_nf = null;

						cBDestinatarioDaNF.removeAllItems();
					}
				}
			}
		});

		panel_7.add(cBDestinatarioDaNF, "cell 1 2,growx");
		btnSelecionarDestinatario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TelaCliente tela = new TelaCliente(0, 35, isto);
				tela.setVisible(true);

			}
		});
		btnSelecionarDestinatario.setForeground(Color.WHITE);
		btnSelecionarDestinatario.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnSelecionarDestinatario.setBackground(new Color(0, 51, 102));

		panel_7.add(btnSelecionarDestinatario, "cell 2 2");
		lblNewLabel_7_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_7.add(lblNewLabel_7_1_1, "cell 0 3,alignx trailing");

		panel_7.add(cbStatusAoContador, "cell 1 3,growx");
		cbStatusAoContador.addItem("Não se aplica");
		cbStatusAoContador.addItem("Não Enviado ao contador");
		cbStatusAoContador.addItem("Enviado ao contador");

		JButton btnNewButton_5 = new JButton("Atualizar");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int id_destinatario_nf = -1;

				if (destinatario_nf != null) {
					id_destinatario_nf = destinatario_nf.getId();
				} else
					id_destinatario_nf = 0;
				
				
				GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
				boolean atualizar = gerenciar.atualizarContadorLancamento(cbStatusAoContador.getSelectedIndex(),
						id_destinatario_nf, lancamento_global.getId_lancamento());

				if (atualizar) {
					
					   //Criar tarefa
       				GerenciarBancoTarefaGeral gerenciar_tarefa = new GerenciarBancoTarefaGeral();
       				CadastroTarefaGeral tarefa = new CadastroTarefaGeral();
       				
       				tarefa.setNome_tarefa("Alteração manual de status de envio de documento ao contador");
       				tarefa.setDescricao_tarefa("Envio de documento ao contador: Lançamento id: " + lancamento_global.getId_lancamento() );
       				String status = "";
       				if(cbStatusAoContador.getSelectedIndex() == 0) {
       					status = "Não se aplica";
       				}else if(cbStatusAoContador.getSelectedIndex() == 1) {
       					status = "Não Enviado ao contador";
       				}else if(cbStatusAoContador.getSelectedIndex() == 2) {
       					status = "Enviado ao contador";
       				}
       				
       			 
       				tarefa.setMensagem("Foi alterado manualmente o status de envio de lancamento ao contador, o status agora é: " + status);
       				tarefa.setCriador(login);
       				tarefa.setExecutor(login);
       				tarefa.setStatus_tarefa(1);
       				tarefa.setPrioridade(1);
       				tarefa.setTipo(2);
       				tarefa.setId_lancamento_pai( lancamento_global.getId_lancamento());
       				
       				GetData data = new GetData();
       				tarefa.setHora(data.getHora());
       				tarefa.setData(data.getData());
       				tarefa.setHora_agendada(data.getHora());
       				tarefa.setData_agendada(data.getData());
       				
       				boolean inseriu_tarefa = gerenciar_tarefa.inserirTarefaGeral(tarefa);
       				if(inseriu_tarefa) {
       					pesquisar_tarefas();
       				}else {
       					JOptionPane.showMessageDialog(isto, "Tarefa Não Inserida, Consulte o administrador");

       				}
       				
					JOptionPane.showMessageDialog(isto, "Feito");
					
					
					
				} else {
					JOptionPane.showMessageDialog(isto, "Erro\nConsulte o administrador");

				}

			}
		});
		btnNewButton_5.setBackground(new Color(0, 51, 0));
		btnNewButton_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnNewButton_5.setForeground(Color.WHITE);
		panel_7.add(btnNewButton_5, "flowx,cell 1 4,alignx right");
		panel_7.add(panel_9, "cell 0 4 3 1,alignx right");
		panel_9.setBackground(new Color(255, 255, 204));
		panel_9.setLayout(new MigLayout("", "[73px]", "[33px]"));
		panel_9.add(btnNewButton, "cell 0 0,alignx left,aligny top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1) {
				TelaFinanceiroCadastroLancamento tela = new TelaFinanceiroCadastroLancamento(1, lancamento_global,
						isto);
				tela.setVisible(true);
				}else if(lancamento_global.getTipo_lancamento() == 2){
					TelaFinanceiroCadastroTransferencia tela = new TelaFinanceiroCadastroTransferencia(1, lancamento_global, isto);
					tela.setVisible(true);
				}
				else if(lancamento_global.getTipo_lancamento() == 3){
					TelaFinanceiroCadastroEmprestimo tela = new TelaFinanceiroCadastroEmprestimo(1, lancamento_global, isto);
					tela.setVisible(true);
				}
			}
		});
		btnNewButton.setBackground(new Color(255, 153, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));

		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_18.setBackground(new Color(0, 51, 0));
		panel.add(panel_18, "cell 2 0,grow");
		panel_18.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		panel_19.setBackground(Color.WHITE);

		panel_18.add(panel_19, "cell 0 1,alignx right,aligny top");
		panel_19.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[]"));
		btnEnviarDiretoAo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TelaEnviarMsgWhatsappDocs enviar = new TelaEnviarMsgWhatsappDocs
				if (lancamento_global.getCaminho_arquivo() != null
						&& lancamento_global.getCaminho_arquivo().length() > 10) {
				      String url = servidor_unidade + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
								+  lancamento_global.getId_lancamento() + "\\documentos\\"
							+ lancamento_global.getCaminho_arquivo();
				      
				      TelaEnviarAoContador enviar = new TelaEnviarAoContador(lancamento_global.getId_lancamento(), url, isto);
					enviar.setContatosContadores();
					enviar.setVisible(true);

				      
				      
				}else {
					JOptionPane.showMessageDialog(isto, "Nenhum arquivo anexado para este lançamento");
				}
			}
		});
		
		
		if(lancamento.getTipo_lancamento() == 3) 
		{
			btnEmitirContrato = new JButton("Emitir Contrato");
		btnEmitirContrato.setForeground(Color.WHITE);
		btnEmitirContrato.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEmitirContrato.setBackground(new Color(0, 51, 0));
		
		btnEmitirContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 editar_word = new EditarContratoEmprestimo(lancamento_global);
				String doc_relatorio = editar_word.preparar();
				ConverterPdf converter_pdf = new ConverterPdf();
				String pdf_alterado = converter_pdf.word_pdf_file2(doc_relatorio);

				TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);
				vizualizar.setContrato_emprestimo(true);

			}
		});
		panel_19.add(btnEmitirContrato, "cell 22 0");

		
		}
		
		
		btnEnviarDiretoAo.setForeground(Color.WHITE);
		btnEnviarDiretoAo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnEnviarDiretoAo.setBackground(new Color(102, 0, 0));

		panel_19.add(btnEnviarDiretoAo, "cell 23 0");
		btnRelatrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RelatorioLancamento relatorio = new RelatorioLancamento(lancamento_global);
				String doc_relatorio = relatorio.preparar();
				ConverterPdf converter_pdf = new ConverterPdf();
				String pdf_alterado = converter_pdf.word_pdf_file2(doc_relatorio);

				TelaVizualizarPdf vizualizar = new TelaVizualizarPdf(null, isto, null, pdf_alterado, null, isto);

			}
		});
		btnRelatrio.setForeground(Color.WHITE);
		btnRelatrio.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnRelatrio.setBackground(new Color(102, 51, 0));

		panel_19.add(btnRelatrio, "cell 26 0");
		panel_19.add(btnMudarArquivo, "cell 27 0");
		btnMudarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selecionarDocumentoGlobal();

			}
		});
		btnMudarArquivo.setForeground(Color.WHITE);
		btnMudarArquivo.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnMudarArquivo.setBackground(new Color(0, 0, 153));
		panel_19.add(btnVizualizar, "cell 28 0,alignx right");
		btnVizualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lancamento_global.getCaminho_arquivo() != null
						&& lancamento_global.getCaminho_arquivo().length() > 10) {
				      String url = servidor_unidade + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
								+  lancamento_global.getId_lancamento() + "\\documentos\\"
							+ lancamento_global.getCaminho_arquivo();
				      

					
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop desktop = Desktop.getDesktop();
							File myFile = new File(url);
							desktop.open(myFile);
						} catch (IOException ex) {
						}
					}

				}

			}
		});
		btnVizualizar.setForeground(Color.WHITE);
		btnVizualizar.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnVizualizar.setBackground(new Color(0, 102, 51));
		panel_18.add(painelVizualizarDocumento, "cell 0 0,grow");
		painelVizualizarDocumento.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		painelVizualizarDocumento.setBackground(Color.WHITE);
		painelVizualizarDocumento.setLayout(new BorderLayout(0, 0));
		panel_20.setBackground(new Color(0, 51, 0));

		panel.add(panel_20, "cell 1 0 1 3,growx,aligny center");
		panel_20.setLayout(new MigLayout("", "[356px]", "[grow][grow][grow]"));
		panel_20.add(panel_6, "cell 0 0,growx,aligny center");
		panel_6.setBackground(new Color(0, 153, 153));
		panel_6.setLayout(new MigLayout("", "[][]", "[][][][]"));
		lblStatus.setForeground(Color.WHITE);
		panel_6.add(lblStatus, "cell 0 0,alignx right");
		lblStatus.setBackground(Color.WHITE);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_6.add(entStatus, "cell 1 0");
		entStatus.setOpaque(true);
		entStatus.setForeground(Color.WHITE);
		entStatus.setBackground(new Color(0, 0, 51));
		entStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblData.setForeground(Color.WHITE);
		panel_6.add(lblData, "cell 0 1,alignx right");
		lblData.setBackground(Color.WHITE);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));
		entData.setForeground(Color.WHITE);
		panel_6.add(entData, "cell 1 1");
		entData.setBackground(Color.WHITE);
		entData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPrioridade.setForeground(Color.WHITE);
		panel_6.add(lblPrioridade, "cell 0 2,alignx right");
		lblPrioridade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPrioridade.setBackground(Color.WHITE);
		entPrioridade.setForeground(Color.WHITE);
		panel_6.add(entPrioridade, "cell 1 2");
		entPrioridade.setFont(new Font("Tahoma", Font.PLAIN, 18));
		entPrioridade.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.WHITE);
		panel_6.add(lblNewLabel, "cell 0 3,alignx right");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_6.add(entTipoLancamento, "cell 1 3");
		entTipoLancamento.setForeground(Color.WHITE);
		entTipoLancamento.setOpaque(true);
		entTipoLancamento.setBackground(new Color(204, 0, 0));
		entTipoLancamento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_20.add(painelAsPartes, "cell 0 1,growx,aligny center");
		painelAsPartes.setBackground(new Color(0, 51, 0));
		painelAsPartes.setLayout(new MigLayout("", "[]", "[]"));
		panel_20.add(panel_5, "cell 0 2,growx,aligny top");
		// *

		// *
		panel_5.setBackground(new Color(0, 51, 0));
		panel_5.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][]"));
		lblNewLabel_3_1.setBackground(new Color(102, 51, 0));
		lblNewLabel_3_1.setOpaque(true);
		lblNewLabel_3_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_3_1, "cell 0 0 2 1,growx");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1, "cell 0 1,alignx right");
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entGrupoContas.setForeground(Color.WHITE);
		panel_5.add(entGrupoContas, "cell 1 1,alignx left");
		entGrupoContas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_1, "cell 0 2,alignx right");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entConta.setForeground(Color.WHITE);
		panel_5.add(entConta, "cell 1 2,alignx left");
		entConta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_2_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));

		panel_5.add(lblNewLabel_1_1_1_1_2_1_1_2, "cell 0 3,alignx right");
		entIdentificadorGeral.setForeground(Color.WHITE);
		entIdentificadorGeral.setFont(new Font("Tahoma", Font.PLAIN, 18));

		panel_5.add(entIdentificadorGeral, "cell 1 3");
		lblNewLabel_1_1_1_1_2_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1_1, "cell 0 4,alignx right");
		lblNewLabel_1_1_1_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entDataVencimento.setForeground(Color.WHITE);
		panel_5.add(entDataVencimento, "cell 1 4,alignx left");
		entDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1, "cell 0 5,alignx right");
		lblNewLabel_1_1_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entValor.setForeground(Color.WHITE);
		panel_5.add(entValor, "cell 1 5,alignx left");
		entValor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1_1_1, "cell 0 6,alignx right");
		lblNewLabel_1_1_1_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entNumeroParcelas.setForeground(Color.WHITE);
		panel_5.add(entNumeroParcelas, "cell 1 6,alignx left");
		entNumeroParcelas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1_1_1_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1_1_1_1_1, "cell 0 8,alignx right");
		lblNewLabel_1_1_1_1_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entIntervalo.setForeground(Color.WHITE);
		panel_5.add(entIntervalo, "cell 1 8,alignx left");
		entIntervalo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
		panel_14.setBackground(new Color(255, 102, 0));

		painelPagamentos.add(panel_14, "cell 0 0,grow");
		panel_14.setLayout(new MigLayout("", "[grow][]", "[][grow][grow][]"));
		panel_14.add(lblNewLabel_2, "cell 0 0,alignx center");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_14.add(panel_1, "cell 0 1 2 1,grow");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(new BorderLayout(0, 0));

		painelPrincipal.addTab("Tarefas", painelTarefas);

		tabela_parcelas = new JTable();
		tabela_parcelas.setRowHeight(30);
		if (lancamento.getTipo_lancamento() == 3)
			tabela_parcelas.setModel(modelo_parcelas_emprestimo);
		else
			tabela_parcelas.setModel(modelo_parcelas);

		scrollParcelas = new JScrollPane(tabela_parcelas);

		tabela_parcelas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				if (!lsm.isSelectionEmpty()) {
					int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela
					if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1) {
						Parcela parcela = new GerenciarBancoParcelas()
								.getParcela(modelo_parcelas.getValue(rowSel).getId_parcela());
						entObservacaoParcela.setText(parcela.getObservacao());
					} else if (lancamento_global.getTipo_lancamento() == 3) {
						ParcelaEmprestimo parcela = new GerenciarBancoParcelasEmprestimo()
								.getParcela(modelo_parcelas_emprestimo.getValue(rowSel).getId_parcela());
						entObservacaoParcela.setText(parcela.getObservacao());
					}

				}
			}
		});

		panel_1.add(scrollParcelas);
		panel_15.setBackground(Color.WHITE);

		panel_14.add(panel_15, "cell 0 2 2 1,grow");
		panel_15.setLayout(new MigLayout("", "[351.00][grow]", "[::100px,grow][::50px,grow][::50px,grow][grow]"));
		panel_15.add(painelObservacao, "cell 0 0 2 1,grow");
		painelObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelObservacao.setBackground(Color.WHITE);
		painelObservacao.setLayout(new MigLayout("", "[][grow]", "[100px:n,grow]"));
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelObservacao.add(lblNewLabel_5, "cell 0 0");
		entObservacaoParcela.setLineWrap(true);
		entObservacaoParcela.setWrapStyleWord(true);
		entObservacaoParcela.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		entObservacaoParcela.setBorder(new LineBorder(new Color(0, 0, 0)));
		entObservacaoParcela.setEditable(false);

		painelObservacao.add(entObservacaoParcela, "cell 1 0,grow");

		panel_15.add(painelCalculos, "cell 0 1 2 3,grow");
		painelCalculos.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelCalculos.setBackground(Color.WHITE);
		painelCalculos.setLayout(new MigLayout("", "[grow][40px,grow]", "[24px][24px]"));
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));

		painelCalculos.add(lblNewLabel_5_1, "cell 0 0 2 1,alignx center,aligny top");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		painelCalculos.add(lblNewLabel_6_1, "cell 0 1,alignx right,aligny center");
		lblValorTotalDasParcelas.setFont(new Font("SansSerif", Font.BOLD, 18));

		painelCalculos.add(lblValorTotalDasParcelas, "cell 1 1,alignx left,aligny top");
		panel_16.setBackground(Color.WHITE);
		panel_14.add(panel_16, "cell 0 3 2 1,alignx center");
		panel_16.setLayout(new MigLayout("", "[69px][][][65px][67px]", "[31px]"));
		panel_16.add(btnInserirParcela_1_1, "cell 0 0,alignx left,aligny top");
		btnInserirParcela_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir a Parcela?", "Excluir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela

					if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
						boolean exclusao = new GerenciarBancoParcelas()
								.removerParcela(modelo_parcelas.getValue(rowSel).getId_parcela());
						if (exclusao) {
							JOptionPane.showMessageDialog(isto, "Parcela Excluída");
							atualizarValorLancamento(lancamento_global);
							atualizarRotinas();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}
					} else {
						boolean exclusao = new GerenciarBancoParcelasEmprestimo()
								.removerParcela(modelo_parcelas_emprestimo.getValue(rowSel).getId_parcela());
						if (exclusao) {
							JOptionPane.showMessageDialog(isto, "Parcela Excluída");
							atualizarValorLancamentoEmprestimo(lancamento_global);
							atualizarRotinas();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}
					}

				}

			}
		});
		btnInserirParcela_1_1.setForeground(Color.WHITE);
		btnInserirParcela_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInserirParcela_1_1.setBackground(new Color(204, 0, 0));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela
				if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
					Parcela parcela = new GerenciarBancoParcelas()
							.getParcela(modelo_parcelas.getValue(rowSel).getId_parcela());
					if (parcela.getCaminho_arquivo() != null && parcela.getCaminho_arquivo().length() > 10) {
						
						
						
						 String url = servidor_unidade + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
									+  lancamento_global.getId_lancamento() + "\\documentos\\"
								+ parcela.getCaminho_arquivo();
					      
						
						
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(url);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}

					}
				} else if (lancamento_global.getTipo_lancamento() == 3) {
					ParcelaEmprestimo parcela = new GerenciarBancoParcelasEmprestimo()
							.getParcela(modelo_parcelas_emprestimo.getValue(rowSel).getId_parcela());

					if (parcela.getCaminho_arquivo() != null && parcela.getCaminho_arquivo().length() > 5) {
						
						 String url = servidor_unidade + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
									+  lancamento_global.getId_lancamento() + "\\documentos\\"
								+ parcela.getCaminho_arquivo();
					      
					
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(url);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}

					}
				}

			}
		});
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setBackground(new Color(0, 51, 0));
		btnNewButton_4.setFont(new Font("SansSerif", Font.PLAIN, 14));

		panel_16.add(btnNewButton_4, "cell 1 0");
		btnMudarArquivo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarDocumentoParcela();
			}
		});
		btnMudarArquivo_1.setForeground(Color.WHITE);
		btnMudarArquivo_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnMudarArquivo_1.setBackground(new Color(153, 51, 0));

		panel_16.add(btnMudarArquivo_1, "cell 2 0");
		panel_16.add(btnEditar, "cell 3 0,alignx left,aligny top");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (lancamento_global.getTipo_lancamento() == 0 || lancamento.getTipo_lancamento() == 1 || lancamento.getTipo_lancamento() == 2) {
					int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela

					TelaFinanceiroCadastroParcela tela = new TelaFinanceiroCadastroParcela(1,
							new GerenciarBancoParcelas().getParcela(modelo_parcelas.getValue(rowSel).getId_parcela()),
							lancamento_global, isto);
					tela.setVisible(true);
				} else if (lancamento.getTipo_lancamento() == 3) {
					int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela

					TelaFinanceiroCadastroParcelaEmprestimo tela = new TelaFinanceiroCadastroParcelaEmprestimo(1,
							new GerenciarBancoParcelasEmprestimo()
									.getParcela(modelo_parcelas_emprestimo.getValue(rowSel).getId_parcela()),
							lancamento_global, isto);
					tela.setVisible(true);
				}
			}
		});
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnEditar.setBackground(new Color(0, 0, 139));
		panel_16.add(btnInserirParcela, "cell 4 0,growx,aligny top");
		btnInserirParcela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// public TelaCadastroParcela(int modo_operacao, Parcela parcela, int
				// id_lancamento_pai, Window janela_pai) {
				if (lancamento_global.getTipo_lancamento() == 3) {
					TelaFinanceiroCadastroParcelaEmprestimo tela = new TelaFinanceiroCadastroParcelaEmprestimo(0, null,
							lancamento_global, isto);
					tela.setVisible(true);
				} else {
					TelaFinanceiroCadastroParcela tela = new TelaFinanceiroCadastroParcela(0, null, lancamento_global,
							isto);
					tela.setVisible(true);
				}
			}
		});
		btnInserirParcela.setBackground(new Color(0, 0, 51));
		btnInserirParcela.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInserirParcela.setForeground(Color.WHITE);
		panel_17.setBackground(new Color(0, 153, 153));

		painelPagamentos.add(panel_17, "cell 1 0,grow");
		panel_17.setLayout(new MigLayout("", "[grow]", "[][402.00,grow][193.00,grow][]"));
		panel_17.add(lblNewLabel_4, "cell 0 0,alignx center");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		panel_17.add(panel_2, "cell 0 1,grow");
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));

		tabela_pagamentos = new JTable();
		tabela_pagamentos.setRowHeight(30);

		if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2)
			tabela_pagamentos.setModel(modelo_pagamentos);
		else if (lancamento_global.getTipo_lancamento() == 3)
			tabela_pagamentos.setModel(modelo_pagamentos_emprestimo);

		scrollPagamentos = new JScrollPane(tabela_pagamentos);

		tabela_pagamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				if (!lsm.isSelectionEmpty()) {
					if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
						int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
						FinanceiroPagamento pagamento = modelo_pagamentos.getValue(rowSel);
						entObservacaoPagamento.setText(pagamento.getObservacao());
					} else if (lancamento_global.getTipo_lancamento() == 3) {
						int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
						FinanceiroPagamentoEmprestimo pagamento = modelo_pagamentos_emprestimo.getValue(rowSel);
						entObservacaoPagamento.setText(pagamento.getObservacao());
					}
				}
			}
		});
		panel_2.setLayout(new BorderLayout(0, 0));

		panel_2.add(scrollPagamentos);
		panel_17.add(panel_10, "cell 0 2,grow");
		panel_10.setBackground(Color.WHITE);
		panel_10.setLayout(new MigLayout("", "[260px][grow]", "[84px,grow][4px][114px,grow][]"));
		panel_11.setBackground(Color.WHITE);
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel_10.add(panel_11, "cell 0 0 2 2,grow");
		panel_11.setLayout(new MigLayout("", "[][grow]", "[100px:n,grow]"));
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_11.add(lblNewLabel_5_2, "cell 0 0");
		entObservacaoPagamento.setFont(new Font("SansSerif", Font.BOLD, 14));
		entObservacaoPagamento.setLineWrap(true);
		entObservacaoPagamento.setWrapStyleWord(true);
		entObservacaoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel_11.add(entObservacaoPagamento, "cell 1 0,grow");
		panel_12.setBackground(Color.WHITE);
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel_10.add(panel_12, "cell 0 2 2 2,grow");
		panel_12.setLayout(new MigLayout("", "[175px,grow][40px,grow]", "[24px][24px][]"));
		lblNewLabel_5_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));

		panel_12.add(lblNewLabel_5_1_1, "cell 0 0 2 1,alignx center,aligny top");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_12.add(lblNewLabel_6_1_1, "cell 0 1,alignx right,aligny center");
		lblValorTotalDosPagamentos.setFont(new Font("SansSerif", Font.BOLD, 18));

		panel_12.add(lblValorTotalDosPagamentos, "flowx,cell 1 1,alignx left,aligny top");
		lblNewLabel_6_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));

		panel_12.add(lblNewLabel_6_1_1_1, "cell 0 2,alignx right");
		lblValorTotalRestante.setFont(new Font("SansSerif", Font.BOLD, 18));

		panel_12.add(lblValorTotalRestante, "cell 1 2");
		lblNewLabel_6_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_12.add(lblNewLabel_6_1_1_2, "cell 1 1,alignx right");
		lblValorTotalJuros.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		panel_12.add(lblValorTotalJuros, "cell 1 1");
		panel_17.add(panel_13, "cell 0 3,alignx center");
		panel_13.setBackground(Color.WHITE);
		panel_13.setLayout(new MigLayout("", "[64px][][][][][][60px][90px]", "[28px]"));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, "Deseja excluir o Pagamento?", "Excluir",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
						int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela

						boolean exclusao = new GerenciarBancoFinanceiroPagamento()
								.removerFinanceiroPagamento(modelo_pagamentos.getValue(rowSel).getId_pagamento());
						if (exclusao) {
							JOptionPane.showMessageDialog(isto, "Pagamento Excluído");
							atualizarValorLancamento(lancamento_global);
							rotinas();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}

					} else if (lancamento_global.getTipo_lancamento() == 3) {
						int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela

						boolean exclusao = new GerenciarBancoFinanceiroPagamentoEmprestimo().removerFinanceiroPagamento(
								modelo_pagamentos_emprestimo.getValue(rowSel).getId_pagamento());
						if (exclusao) {
							JOptionPane.showMessageDialog(isto, "Pagamento Excluído");
							atualizarValorLancamentoEmprestimo(lancamento_global);
							rotinas();
						} else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}
					}

				}
			}
		});
		btnNewButton_3.setBackground(new Color(204, 0, 0));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_13.add(btnNewButton_3, "cell 0 0,alignx left,aligny top");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (lancamento.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
					int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
					TelaFinanceiroCadastroPagamento tela = new TelaFinanceiroCadastroPagamento(1,
							modelo_pagamentos.getValue(rowSel), lancamento_global, isto);
					tela.setVisible(true);
				} else if (lancamento.getTipo_lancamento() == 3) {
					int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
					TelaFinanceiroCadastroPagamentoEmprestimo tela = new TelaFinanceiroCadastroPagamentoEmprestimo(1,
							modelo_pagamentos_emprestimo.getValue(rowSel), lancamento_global, isto);
					tela.setVisible(true);
				}
			}
		});
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela

				if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
					FinanceiroPagamento pag = new GerenciarBancoFinanceiroPagamento()
							.getFinanceiroPagamento(modelo_pagamentos.getValue(rowSel).getId_pagamento());
					if (pag.getCaminho_arquivo() != null && pag.getCaminho_arquivo().length() > 10) {
						
					
						 String url = servidor_unidade + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
									+  lancamento_global.getId_lancamento() + "\\documentos\\"
								+ pag.getCaminho_arquivo();
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(url);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}

					}
				} else if (lancamento_global.getTipo_lancamento() == 3) {
					FinanceiroPagamentoEmprestimo pag = new GerenciarBancoFinanceiroPagamentoEmprestimo()
							.getFinanceiroPagamento(modelo_pagamentos_emprestimo.getValue(rowSel).getId_pagamento());
					if (pag.getCaminho_arquivo() != null && pag.getCaminho_arquivo().length() > 10) {
					
						
						 String url = servidor_unidade + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
									+  lancamento_global.getId_lancamento() + "\\documentos\\"
								+ pag.getCaminho_arquivo();
						
					
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File myFile = new File(url);
								desktop.open(myFile);
							} catch (IOException ex) {
							}
						}

					}
				}

			}
		});
		btnNewButton_2_1.setForeground(Color.WHITE);
		btnNewButton_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_2_1.setBackground(new Color(0, 51, 51));

		panel_13.add(btnNewButton_2_1, "cell 1 0");
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarDocumentoPagamento();
			}
		});
		btnNewButton_2_1_1.setForeground(Color.WHITE);
		btnNewButton_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_2_1_1.setBackground(new Color(153, 51, 0));

		panel_13.add(btnNewButton_2_1_1, "cell 2 0 3 1,alignx center");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (lancamento.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
					int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela

					FinanceiroPagamento pag =  modelo_pagamentos.getValue(rowSel);
					TelaCriarRecibo tela = new TelaCriarRecibo(pag,lancamento_global, isto);
					tela.setVisible(true);
					
				} else if (lancamento.getTipo_lancamento() == 3) {
					int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
					FinanceiroPagamentoEmprestimo pag =  modelo_pagamentos_emprestimo.getValue(rowSel);
					TelaCriarRecibo tela = new TelaCriarRecibo(pag, lancamento_global, isto);
					tela.setVisible(true);
				}
				
			}
		});
		btnNewButton_6.setForeground(Color.WHITE);
		btnNewButton_6.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnNewButton_6.setBackground(new Color(51, 51, 0));
		
		panel_13.add(btnNewButton_6, "cell 5 0");
		btnNewButton_2.setBackground(new Color(0, 0, 153));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 14));

		panel_13.add(btnNewButton_2, "cell 6 0,alignx left,aligny top");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
					TelaFinanceiroCadastroPagamento tela = new TelaFinanceiroCadastroPagamento(0, null,
							lancamento_global, isto);
					tela.setVisible(true);
				} else if (lancamento_global.getTipo_lancamento() == 3) {
					TelaFinanceiroCadastroPagamentoEmprestimo tela = new TelaFinanceiroCadastroPagamentoEmprestimo(0,
							null, lancamento_global, isto);
					tela.setVisible(true);
				}

			}
		});

		panel_13.add(btnNewButton_1, "cell 7 0,growx,aligny top");

		JPanel painelDocumentos = new JPanel();
		painelDocumentos.setBackground(new Color(51, 153, 153));
		painelDocumentos.setVisible(false);
		painelDocumentos.setEnabled(false);
		painelDocumentos.setBounds(198, 153, 864, 358);
		painelPrincipal.add("Documentos", painelDocumentos);
		painelDocumentos.setLayout(new MigLayout("", "[359px][975px,grow]", "[][grow][grow][][645px]"));
		lblNewLabel_6.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		painelDocumentos.add(lblNewLabel_6, "cell 1 0");

		panel_docs = new JPanel();
		panel_docs.setBackground(new Color(0, 153, 153));
		painelDocumentos.add(panel_docs, "cell 0 0 1 5,grow");
		panel_21.setBackground(Color.WHITE);
		panel_21.setOpaque(false);
		
		painelDocumentos.add(panel_21, "cell 1 1,grow");
		panel_21.setLayout(new BorderLayout(0, 0));
		
		table_recibos = new JTable(modelo_recibos);
		table_recibos.setRowHeight(30);
		
		scrollPaneRecibos = new JScrollPane(table_recibos);
		scrollPaneRecibos.getViewport().setBackground(Color.white);
		
		panel_21.add(scrollPaneRecibos, BorderLayout.NORTH);
		panel_22.setOpaque(false);
		panel_22.setBackground(Color.BLACK);
		
		painelDocumentos.add(panel_22, "cell 1 2,grow");
		panel_22.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]", "[][]"));
		btnNewButton_7_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CadastroRecibo recibo_atualizar = getReciboSelecionado();
				GerenciarBancoRecibos gerenciar = new GerenciarBancoRecibos();
				int status_atualizar = -1;
				
				if(recibo_atualizar.getStatus_recibo() == 0) {
					status_atualizar = 1;
				}else if(recibo_atualizar.getStatus_recibo() == 1) {
					status_atualizar = 0;
				}
				
				boolean atualizado = gerenciar.atualizarStatusrecibo(recibo_atualizar.getId_recibo(), status_atualizar);
				if(atualizado) {
					//excluir documento
					JOptionPane.showMessageDialog(isto, "Status do Recibo atualizado");
					pesquisar_recibos();
					
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao atualizar\nConsulte o administrador");
				}
				
			}
		});
		btnNewButton_7_1.setBackground(new Color(0, 51, 0));
		btnNewButton_7_1.setForeground(Color.WHITE);
		btnNewButton_7_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		panel_22.add(btnNewButton_7_1, "cell 29 0,alignx right");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroRecibo recibo_excluir = getReciboSelecionado();
				GerenciarBancoRecibos gerenciar = new GerenciarBancoRecibos();
				boolean excluido = gerenciar.removerRecibo(recibo_excluir.getId_recibo());
				if(excluido) {
					//excluir documento
					JOptionPane.showMessageDialog(isto, "Recibo Excluido");
					pesquisar_recibos();
					
				}else {
					JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");
				}
				
			}
		});
		btnNewButton_7.setBackground(Color.RED);
		btnNewButton_7.setForeground(Color.WHITE);
		btnNewButton_7.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		panel_22.add(btnNewButton_7, "cell 30 0,alignx right");
		

		JPanel painelInserirDocumento = new JPanel();
		painelInserirDocumento.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelInserirDocumento.setBackground(new Color(0, 153, 153));
		painelDocumentos.add(painelInserirDocumento, "cell 1 4,alignx left,aligny center");
		painelInserirDocumento
				.setLayout(new MigLayout("", "[46px][10px][231px][10px][89px]", "[][][27px][22px][85px][39px][23px]"));
		lblNewLabel_18_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		
		painelInserirDocumento.add(lblNewLabel_18_1, "cell 0 0 5 1");

		JLabel lblNewLabel_15 = new JLabel("Nome:");
		lblNewLabel_15.setForeground(new Color(0, 0, 0));
		lblNewLabel_15.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelInserirDocumento.add(lblNewLabel_15, "cell 0 2 2 1,alignx right,aligny top");

		JLabel lblNewLabel_16 = new JLabel("Descrição:");
		lblNewLabel_16.setForeground(new Color(0, 0, 0));
		lblNewLabel_16.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelInserirDocumento.add(lblNewLabel_16, "cell 0 4 2 1,alignx right,aligny top");

		entDescricaoDocumento = new JTextArea();
		entDescricaoDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(entDescricaoDocumento, "cell 2 4 3 1,grow");

		JLabel lblNewLabel_17 = new JLabel("Arquivo:");
		lblNewLabel_17.setForeground(new Color(0, 0, 0));
		lblNewLabel_17.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelInserirDocumento.add(lblNewLabel_17, "cell 0 5 2 1,alignx right,aligny center");

		entCaminhoDocumento = new JTextField();
		entCaminhoDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		entCaminhoDocumento.setColumns(10);
		painelInserirDocumento.add(entCaminhoDocumento, "cell 2 5,grow");

		entNomeDocumento = new JTextField();
		entNomeDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		entNomeDocumento.setColumns(10);
		painelInserirDocumento.add(entNomeDocumento, "cell 2 2 3 1,grow");

		JButton btnSelecionarDocumento = new JButton("Selecionar");
		btnSelecionarDocumento.setBackground(new Color(0, 0, 102));
		btnSelecionarDocumento.setForeground(Color.WHITE);
		btnSelecionarDocumento.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSelecionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selecionarDocumento();
			}
		});
		painelInserirDocumento.add(btnSelecionarDocumento, "cell 4 5,growx,aligny center");

		JButton btnAdicionarDocumento = new JButton("Adicionar");
		btnAdicionarDocumento.setForeground(Color.WHITE);
		btnAdicionarDocumento.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnAdicionarDocumento.setBackground(new Color(0, 51, 0));
		btnAdicionarDocumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarNovoDocumento();
			}
		});
		painelInserirDocumento.add(btnAdicionarDocumento, "cell 4 6,grow");

		JLabel lblNewLabel_16_1 = new JLabel("Tipo:");
		lblNewLabel_16_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_16_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		painelInserirDocumento.add(lblNewLabel_16_1, "cell 0 3 2 1,alignx right,aligny center");

		cBTipoDocumento = new JComboBox();
		cBTipoDocumento.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelInserirDocumento.add(cBTipoDocumento, "cell 2 3 3 1,grow");
		cBTipoDocumento.addItem("Documento Pessoal");
		cBTipoDocumento.addItem("Comprovantes");
		cBTipoDocumento.addItem("Outros");
		cBTipoDocumento.addItem("Documento Original");

		pesquisar_tarefas();
		pesquisar_recibos();
		setInformacoesDocumentos();
		boolean chamar = true;
		if (chamar)
			rotinas();
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLocationRelativeTo(janela_pai);

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

	public void atualizarRotinas() {
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		lancamento_global = gerenciar.getLancamento(lancamento_global.getId_lancamento());
		rotinas();
	}

	public void rotinas() {

		lancamento_global = new GerenciarBancoLancamento().getLancamento(lancamento_global.getId_lancamento());

		CentroCusto cc = new CentroCusto();
		CadastroCliente cliente = new CadastroCliente();
		FinanceiroGrupoContas fgc = new FinanceiroGrupoContas();
		FinanceiroConta fc = new FinanceiroConta();

		if (lancamento_global.getTipo_lancamento() == 0) {
			entTipoLancamento.setText("Despesa");
			setPainelAsPartesDespesa();
		} else if (lancamento_global.getTipo_lancamento() == 1) {
			entTipoLancamento.setText("Receita");
			setPainelAsPartesDespesa();

		} 
		else if (lancamento_global.getTipo_lancamento() == 2) {
			entTipoLancamento.setText("Transferência");
			setPainelAsPartesDespesa();
		}
		else if (lancamento_global.getTipo_lancamento() == 3) {
			entTipoLancamento.setText("Empréstimo");
			setPainelAsPartesReceita();
		}

		entData.setText(lancamento_global.getData_lancamento());
		int status = lancamento_global.getStatus();
		if (status == 0) {
			entStatus.setText("A Pagar");

		} else if (status == 1) {
			entStatus.setText("Pago");

		} else if (status == 2) {
			entStatus.setText("A Receber");

		} else if (status == 3) {
			entStatus.setText("Recebido");

		}

		if(lancamento_global.getTipo_lancamento() != 2) {
			String nome_cliente = "";

			cc = new GerenciarBancoCentroCustos().getCentroCusto(lancamento_global.getId_centro_custo());
			
			try {
				cliente = new GerenciarBancoClientes().getCliente(lancamento_global.getId_cliente_fornecedor());
				if (cliente.getTipo_pessoa() == 0) {
					nome_cliente = cliente.getNome_empresarial();
				} else
					nome_cliente = cliente.getNome_fantaia();
			} catch (Exception e) {

			}

		

		entCentroCusto.setText(cc.getNome_centro_custo());
		entClienteFornecedor.setText(nome_cliente);

		
		

		fc = new GerenciarBancoFinanceiroConta().getFinanceiroConta(lancamento_global.getId_conta());
		entConta.setText(fc.getNome());

		fgc = new GerenciarBancoFinanceiroGrupoContas().getFinanceiroGrupoContas(fc.getId_grupo_contas());
		entGrupoContas.setText(fgc.getNome());

		
		entDataVencimento.setText(lancamento_global.getData_vencimento());
		entNumeroParcelas.setText(lancamento_global.getNumero_parcelas() + "");
		entIntervalo.setText(lancamento_global.getIntervalo() + "");
		
		int id_destinatario_nf = lancamento_global.getId_detinatario_nf();
		if (id_destinatario_nf > 0) {
			destinatario_nf = new GerenciarBancoClientes().getCliente(id_destinatario_nf);
			setDestinatarioNF(destinatario_nf);
		} else {
			cBDestinatarioDaNF.addItem("Indefinido");
		}
		
		
		}else {
			//transferencia
			
			InstituicaoBancaria pagador = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(lancamento_global.getId_centro_custo());
			entCentroCusto.setText(pagador.getNome_instituicao_bancaria());

		
			InstituicaoBancaria recebedor = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(lancamento_global.getId_cliente_fornecedor());
			entClienteFornecedor.setText(recebedor.getNome_instituicao_bancaria());
		

		}
		
		
		entIdentificadorGeral.setText(lancamento_global.getIdentificacao());
		entValor.setText(NumberFormat.getCurrencyInstance(ptBr).format(lancamento_global.getValor()));
		
		int prioridade = lancamento_global.getPrioridade();
		/*
		 * cbPrioridade.addItem("Alta Prioridade - Ainda esta semana");
		 * cbPrioridade.addItem("Média Prioridade - Em menos de 15 dias");
		 * cbPrioridade.addItem("Prioridade Leve - Ainda este mês");
		 * cbPrioridade.addItem("Baixa Prioridade - Ainda este ano");
		 */
		if (prioridade == 0) {
			entPrioridade.setText("Alta Prioridade - Ainda esta semana");
		} else if (prioridade == 1) {
			entPrioridade.setText("Média Prioridade - Em menos de 15 dias");

		} else if (prioridade == 2) {
			entPrioridade.setText("Prioridade Leve - Ainda este mês");

		} else if (prioridade == 3) {
			entPrioridade.setText("Baixa Prioridade - Ainda este ano");

		}
		entObservacaoLancamento.setText(lancamento_global.getObservacao());
		entDescricaoLancamento.setText(lancamento_global.getDescricao());

		fecharDocumento();

		
		if (lancamento_global.getCaminho_arquivo() != null) {
			if (lancamento_global.getCaminho_arquivo().length() > 5) {
				carregarDocumento(servidor_unidade +"E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
						+ lancamento_global.getId_lancamento() + "\\documentos\\" + lancamento_global.getCaminho_arquivo());
			}
		}

		cbStatusAoContador.setSelectedIndex(lancamento_global.getContador());

		

		pesquisar_parcelas();
		pesquisar_pagamentos();

	}

	public class ParcelaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_parcela = 0;
		private final int identificador = 1;
		private final int descricao = 2;
		private final int data_vencimento = 3;
		private final int valor = 4;
		private final int status = 5;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Descrição", "Data Vencimento", "Valor", "Status" };
		private final ArrayList<Parcela> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																	// nfs

		public ParcelaTableModel() {

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
			case id_parcela:
				return Integer.class;
			case identificador:
				return String.class;
			case descricao:
				return String.class;
			case data_vencimento:
				return Date.class;
			case valor:
				return String.class;
			case status:
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
			Parcela dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_parcela:
				return dado.getId_parcela();
			case identificador:
				return dado.getIdentificador();
			case descricao: {
				return dado.getDescricao();
			}
			case data_vencimento: {
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					data_menor = formato.parse(dado.getData_vencimento());
					return data_menor;

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			case valor: {
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;

			}
			case status: {
				int status = dado.getStatus();
				if (status == 0) {
					return ("A Pagar");

				} else if (status == 1) {
					return ("Pago");

				} else if (status == 2) {
					return ("A Receber");

				} else if (status == 3) {
					return ("Recebido");

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
			Parcela ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public Parcela getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(Parcela dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(Parcela dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<Parcela> dadosIn) {
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
		public void onRemove(Parcela dado) {
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

		public Parcela onGet(int row) {
			return dados.get(row);
		}
	}

	public class ParcelaEmprestimoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_parcela = 0;
		private final int identificador = 1;
		private final int descricao = 2;
		private final int data_vencimento = 3;
		private final int especie = 4;
		private final int quantidade = 5;
		private final int medida = 6;
		private final int valor_unitario = 7;

		private final int valor = 8;
		private final int status = 9;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Descrição", "Data Vencimento", "Espécie",
				"Quantidade", "Medida", "Valor Unitário", "Valor Total", "Status" };
		private final ArrayList<ParcelaEmprestimo> dados = new ArrayList<>();// usamos como dados uma lista genérica de
																				// nfs

		public ParcelaEmprestimoTableModel() {

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
			case id_parcela:
				return Integer.class;
			case identificador:
				return String.class;
			case descricao:
				return String.class;
			case data_vencimento:
				return Date.class;
			case especie:
				return String.class;
			case quantidade:
				return String.class;
			case medida:
				return String.class;
			case valor_unitario:
				return String.class;
			case valor:
				return String.class;
			case status:
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
			ParcelaEmprestimo dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_parcela:
				return dado.getId_parcela();
			case identificador:
				return dado.getIdentificador();
			case descricao: {
				return dado.getDescricao();
			}
			case data_vencimento: {
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					data_menor = formato.parse(dado.getData_vencimento());
					return data_menor;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "Data Inválida";
				}

			}
			case especie: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else {
					return dado.getEspecie();
				}
			}
			case quantidade: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else
					return dado.getQuantidade();
			}
			case medida: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else
					return dado.getUnidade_medida();

			}
			case valor_unitario: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else
					return dado.getValor_unitario();
			}
			case valor: {
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;

			}
			case status: {
				int status = dado.getStatus();
				if (status == 0) {
					return ("A Pagar");

				} else if (status == 1) {
					return ("Pago");

				} else if (status == 2) {
					return ("A Receber");

				} else if (status == 3) {
					return ("Recebido");

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
			ParcelaEmprestimo ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public ParcelaEmprestimo getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(ParcelaEmprestimo dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(ParcelaEmprestimo dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<ParcelaEmprestimo> dadosIn) {
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
		public void onRemove(ParcelaEmprestimo dado) {
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

		public ParcelaEmprestimo onGet(int row) {
			return dados.get(row);
		}
	}

	public void carregarDocumento(String url) {
		// build a controller

		if (controller == null) {

			controller = new SwingController();

			PropertiesManager propriedades = new PropertiesManager(System.getProperties(),
					ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
			// Build a SwingViewFactory configured with the controller

			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDEMENUBAR, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_VIEWPREF_HIDETOOLBAR, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR, Boolean.FALSE);
			propriedades.setBoolean(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, Boolean.FALSE);

			propriedades.setFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);

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

					painel_vizualizar.setBounds(0, 0, painel_vizualizar.getWidth(), painel_vizualizar.getHeight());
					painelVizualizarDocumento.add(painel_vizualizar);
				} else {
					controller.openDocument(url);
					painel_vizualizar.repaint();
					painel_vizualizar.updateUI();
					painelVizualizarDocumento.add(painel_vizualizar);

				}

			}
		});
	}

	public void fecharDocumento() {

		if (controller != null) {
			controller.closeDocument();
		}

	}

	public void atualizar_informacoes() {
		rotinas();
	}

	public void pesquisar_parcelas() {
		if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
			GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
			lista_parcelas.clear();
			modelo_parcelas.onRemoveAll();

			BigDecimal valor_total_parcelas = BigDecimal.ZERO;

			for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_global.getId_lancamento())) {

				modelo_parcelas.onAdd(parcela);
				lista_parcelas.add(parcela);
				valor_total_parcelas = valor_total_parcelas.add(parcela.getValor());

			}

			lblValorTotalDasParcelas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_parcelas));
		} else if (lancamento_global.getTipo_lancamento() == 3) {
			GerenciarBancoParcelasEmprestimo gerenciar = new GerenciarBancoParcelasEmprestimo();
			lista_parcelas_emprestimo.clear();
			modelo_parcelas_emprestimo.onRemoveAll();

			BigDecimal valor_total_parcelas = BigDecimal.ZERO;

			for (ParcelaEmprestimo parcela : gerenciar.getParcelasPorLancamento(lancamento_global.getId_lancamento())) {

				modelo_parcelas_emprestimo.onAdd(parcela);
				lista_parcelas_emprestimo.add(parcela);
				valor_total_parcelas = valor_total_parcelas.add(parcela.getValor());

			}

			lblValorTotalDasParcelas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_parcelas));

		}

	}

	public void pesquisar_pagamentos() {
		if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2) {
			GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
			lista_pagamentos.clear();
			modelo_pagamentos.onRemoveAll();

			BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

			for (FinanceiroPagamento pagamento : gerenciar
					.getFinanceiroPagamentosPorLancamentoMaisRapido(lancamento_global.getId_lancamento())) {

				modelo_pagamentos.onAdd(pagamento);
				valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());

			}

			BigDecimal valor_restante = lancamento_global.getValor().subtract(valor_total_pagamentos);
			
			
			if(valor_total_pagamentos.compareTo(lancamento_global.getValor()) > 0) {
				//pagou juros
				lblValorTotalJuros.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_restante));
			}

			lblValorTotalDosPagamentos.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos));
			lblValorTotalRestante.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_restante));
			
			
			
			
			
		} else if (lancamento_global.getTipo_lancamento() == 3) {
			GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar = new GerenciarBancoFinanceiroPagamentoEmprestimo();
			lista_pagamentos_emprestimo.clear();
			modelo_pagamentos_emprestimo.onRemoveAll();

			BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

			for (FinanceiroPagamentoEmprestimo pagamento : gerenciar
					.getFinanceiroPagamentosPorLancamentoMaisRapido(lancamento_global.getId_lancamento())) {

				modelo_pagamentos_emprestimo.onAdd(pagamento);
				valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());

			}

			BigDecimal valor_restante = lancamento_global.getValor().subtract(valor_total_pagamentos);

			lblValorTotalDosPagamentos.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos));
			lblValorTotalRestante.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_restante));

		}

	}

	public void setPainelAsPartesDespesa() {
		panel_8.setBackground(new Color(0, 0, 0));

		panel.add(panel_8, "cell 0 3,grow");
		panel_8.setLayout(new MigLayout("", "[grow]", "[][grow][grow]"));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBackground(new Color(0, 0, 51));
		panel_8.add(lblNewLabel_3, "cell 0 0,growx");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_3.setBorder(new LineBorder(Color.WHITE));
		panel_8.add(panel_3, "cell 0 1,growx");
		panel_3.setBackground(new Color(204, 0, 0));
		panel_3.setLayout(new MigLayout("", "[][]", "[][][]"));
		panel_3.add(lblNewLabel_3_2, "cell 0 0");
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setBackground(new Color(204, 0, 0));
		lblNewLabel_3_2.setOpaque(true);
		lblNewLabel_3_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_1, "cell 0 1,alignx right");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entCentroCusto.setForeground(Color.WHITE);
		panel_3.add(entCentroCusto, "cell 1 1");
		entCentroCusto.setFont(new Font("Tahoma", Font.PLAIN, 18));

		panel_4.setBorder(new LineBorder(Color.WHITE));
		panel_8.add(panel_4, "cell 0 2,growx");
		panel_4.setBackground(new Color(0, 51, 0));
		panel_4.setLayout(new MigLayout("", "[][]", "[][]"));
		panel_4.add(lblNewLabel_3_2_1, "cell 0 0");
		lblNewLabel_3_2_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_3_2_1.setForeground(Color.WHITE);
		lblNewLabel_3_2_1.setOpaque(true);
		lblNewLabel_3_2_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		panel_4.add(lblNewLabel_1_1_1, "cell 0 1");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entClienteFornecedor.setForeground(Color.WHITE);
		panel_4.add(entClienteFornecedor, "cell 1 1");
		entClienteFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		painelAsPartes.add(panel_8);
	}

	public void setPainelAsPartesReceita() {
		panel_8.setBackground(new Color(0, 0, 0));

		panel.add(panel_8, "cell 0 3,grow");
		panel_8.setLayout(new MigLayout("", "[grow]", "[][grow][grow]"));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBackground(new Color(0, 0, 51));
		panel_8.add(lblNewLabel_3, "cell 0 0,growx");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_3.setBorder(new LineBorder(Color.WHITE));
		panel_8.add(panel_3, "cell 0 1,growx");
		panel_3.setBackground(new Color(204, 0, 0));
		panel_3.setLayout(new MigLayout("", "[][]", "[][][][]"));
		panel_3.add(lblNewLabel_3_2, "cell 0 0");
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setBackground(new Color(204, 0, 0));
		lblNewLabel_3_2.setOpaque(true);
		lblNewLabel_3_2.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_3.add(lblNewLabel_1_1_1, "cell 0 1");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_3.add(entClienteFornecedor, "cell 1 1");
		entClienteFornecedor.setForeground(Color.WHITE);
		entClienteFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.setBorder(new LineBorder(Color.WHITE));
		panel_8.add(panel_4, "cell 0 2,growx");
		panel_4.setBackground(new Color(0, 51, 0));
		panel_4.setLayout(new MigLayout("", "[][]", "[][][][]"));
		panel_4.add(lblNewLabel_3_2_1, "cell 0 0");
		lblNewLabel_3_2_1.setBackground(new Color(0, 51, 0));
		lblNewLabel_3_2_1.setForeground(Color.WHITE);
		lblNewLabel_3_2_1.setOpaque(true);
		lblNewLabel_3_2_1.setFont(new Font("SansSerif", Font.BOLD, 14));
		panel_4.add(lblNewLabel_1, "cell 0 1");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_4.add(entCentroCusto, "cell 1 1");
		entCentroCusto.setForeground(Color.WHITE);
		entCentroCusto.setFont(new Font("Tahoma", Font.PLAIN, 18));

		if(lancamento_global.getTipo_lancamento() == 3) {
			//emprestimo
			lblNewLabel_3_2.setText("Tomador:");
			lblNewLabel_3_2_1.setText("Mutuante");
		}
	
		painelAsPartes.add(panel_8);
	}

	public class PagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_pagamento = 0;
		private final int identificador = 1;
		private final int pagador = 2;
		private final int recebedor = 3;
		private final int descricao = 4;
		private final int forma_pagamento = 5;
		private final int status = 6;
		private final int data_pagamento = 7;
		private final int valor = 8;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Pagador", "Recebedor", "Descrição",
				"Forma de Pagamento", "Status", "Data Pagamento", "Valor" };
		private final ArrayList<FinanceiroPagamento> dados = new ArrayList<>();// usamos como dados uma lista genérica
																				// de
																				// nfs

		public PagamentoTableModel() {

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
			case id_pagamento:
				return Integer.class;
			case identificador:
				return String.class;
			case pagador:
				return String.class;
			case recebedor:
				return String.class;
			case descricao:
				return String.class;
			case forma_pagamento:
				return String.class;
			case status:
				return String.class;
			case data_pagamento:
				return Date.class;
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
			FinanceiroPagamento dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_pagamento:{
				return dado.getId_pagamento();
				
			}
			case identificador:
				return dado.getIdentificador();
			case pagador: {

				return dado.getNome_pagador();
				
			}
			case recebedor: {

				return dado.getNome_recebedor();
			}
			case descricao: {
				if(dado.getDescricao() != null)
				return dado.getDescricao();
				else
				 return "";	
			}
			case forma_pagamento: {
				return dado.getNome_forma_pagamento();


			}
			case status: {
				if (dado.getStatus_pagamento() == 0) {
					return "A - Compensar|Realizar|Concluir";

				} else if (dado.getStatus_pagamento() == 1) {
					return "Compensado|Realizado|Concluído";
				}
			}
			case data_pagamento: {
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					if (dado.getData_pagamento() != null) {
						if (dado.getData_pagamento().length() == 10) {
							data_menor = formato.parse(dado.getData_pagamento());
							return data_menor;
						} else {
							return "Data Inválida";
						}
					} else {
						return "Data Inválida";
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "Data Inválida";
				}

			}
			case valor: {
				Locale ptBr = new Locale("pt", "BR");
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
			FinanceiroPagamento ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FinanceiroPagamento getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FinanceiroPagamento dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FinanceiroPagamento dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FinanceiroPagamento> dadosIn) {
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
		public void onRemove(FinanceiroPagamento dado) {
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

		public FinanceiroPagamento onGet(int row) {
			return dados.get(row);
		}
	}

	public class PagamentoEmprestimoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_pagamento = 0;
		private final int identificador = 1;
		private final int pagador = 2;
		private final int recebedor = 3;
		private final int descricao = 4;
		private final int forma_pagamento = 5;
		private final int status = 6;
		private final int data_pagamento = 7;
		private final int especie = 8;
		private final int quantidade = 9;
		private final int medida = 10;
		private final int valor_unitario = 11;
		private final int valor = 12;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Pagador", "Recebedor", "Descrição",
				"Forma de Pagamento", "Status", "Data Pagamento", "Espécie", "Quantidade", "Medida", "Valor Unitário",
				"Valor Total" };
		private final ArrayList<FinanceiroPagamentoEmprestimo> dados = new ArrayList<>();// usamos como dados uma lista
																							// genérica de
		// nfs
	
		public PagamentoEmprestimoTableModel() {

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
			case id_pagamento:
				return Integer.class;
			case identificador:
				return String.class;
			case pagador:
				return String.class;
			case recebedor:
				return String.class;
			case descricao:
				return String.class;
			case forma_pagamento:
				return String.class;
			case status:
				return String.class;
			case data_pagamento:
				return Date.class;
			case especie:
				return String.class;
			case quantidade:
				return String.class;
			case medida:
				return String.class;
			case valor_unitario:
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
			FinanceiroPagamentoEmprestimo dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id_pagamento:
				return dado.getId_pagamento();
			case identificador:
				return dado.getIdentificador();
			case pagador: {
				return dado.getNome_pagador();
				
			}
			case recebedor: {
				return dado.getNome_recebedor();


			}
			case descricao: {
				return dado.getDescricao();
			}
			case forma_pagamento: {
				return dado.getNome_forma_pagamento();


			}
			case status: {
				if (dado.getStatus_pagamento() == 0) {
					return "A - Compensar|Realizar|Concluir";

				} else if (dado.getStatus_pagamento() == 1) {
					return "Compensado|Realizado|Concluído";
				}
			}
			case data_pagamento: {
				Date data_menor;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					data_menor = formato.parse(dado.getData_pagamento());
					return data_menor;

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			case especie: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else {
					return dado.getEspecie();
				}
			}
			case quantidade: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else
					return dado.getQuantidade();
			}
			case medida: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else
					return dado.getUnidade_medida();

			}
			case valor_unitario: {
				if (dado.getObjeto() == 0) {
					return "MOEDA";
				} else
					return dado.getValor_unitario();
			}
			case valor: {
				Locale ptBr = new Locale("pt", "BR");
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
			FinanceiroPagamentoEmprestimo ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FinanceiroPagamentoEmprestimo getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FinanceiroPagamentoEmprestimo dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FinanceiroPagamentoEmprestimo dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FinanceiroPagamentoEmprestimo> dadosIn) {
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
		public void onRemove(FinanceiroPagamentoEmprestimo dado) {
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

		public FinanceiroPagamentoEmprestimo onGet(int row) {
			return dados.get(row);
		}
	}

	public void selecionarDocumentoGlobal() {

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo referente a este lançamento!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				substituirDocumentoGlobal(caminho_arquivo);
				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}
		});
	}

	public void substituirDocumentoGlobal(String caminho_arquivo) {
		/********/
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		ManipularTxt manipular = new ManipularTxt();
		String extensaoDoArquivoAntigo = FilenameUtils.getExtension(lancamento_global.getCaminho_arquivo());

		File pasta_documento = new File(servidor_unidade + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
				+ lancamento_global.getId_lancamento() + "\\documentos");
		manipular.criarDiretorio(pasta_documento.getAbsolutePath());

		String arquivo_doc_final = pasta_documento.getAbsolutePath() + "\\doc_lancamento_"
				+ lancamento_global.getId_lancamento() + "." + extensaoDoArquivoAntigo;

		// verifica se existe algum arquivo do documento anterior
		File documento = new File(arquivo_doc_final);
		if (documento.exists()) {

			// excluir o arquivo antigo
			manipular.apagarArquivo(arquivo_doc_final);
		}

		// copiar o arquivo selecionado

		if (caminho_arquivo != null && caminho_arquivo.length() > 10) {
			boolean copiar_arquivo = false;
			String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);

			String arquivo_doc_documento_final = servidor_unidade + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
					+ lancamento_global.getId_lancamento() + "\\documentos\\doc_lancamento_" + lancamento_global.getId_lancamento() + "." + extensaoDoArquivo;
			try {
				copiar_arquivo = manipular.copiarNFe(caminho_arquivo, arquivo_doc_documento_final);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (copiar_arquivo) {
				// atualiza parcela no banco de dados
				boolean atualizar = gerenciar.atualizarCaminhoLancamento(
						"doc_lancamento_" + lancamento_global.getId_lancamento() + "." + extensaoDoArquivo,
						lancamento_global.getId_lancamento());
				if (atualizar) {

					// salvar como documento no banco de dados
					// atualizar status do contrato
					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();

					//excluir documento anteriror
					if(lancamento_global.getId_documento() > 0) {
						gerenciar_doc.removerDocumento(lancamento_global.getId_documento());
					}
					
					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Arquivo Original do Lançamento");
					novo_documento.setNome("doc_lancamento_" + lancamento_global.getId_lancamento());

					novo_documento.setTipo(5);
					novo_documento.setId_pai(lancamento_global.getId_lancamento());
					novo_documento.setNome_arquivo("doc_lancamento_" + lancamento_global.getId_lancamento() + "." + extensaoDoArquivo);
					novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

					int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
					if (cadastrar > 0) {
						if(gerenciar.atualizarIdDocuemnto(cadastrar, lancamento_global.getId_lancamento())) {
							atualizarArvoreDocumentos();

						}else {
							JOptionPane.showMessageDialog(isto, "Id do documento não atualizado no lançamento");

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

					}

					JOptionPane.showMessageDialog(isto, "Arquivo atualizado");
					rotinas();
				} else {
					JOptionPane.showMessageDialog(isto,
							"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

			}
		}
		/********/
	}

	public void selecionarDocumentoParcela() {

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo referente a parcela selecionada!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2)
					substituirDocumentoParcela(caminho_arquivo);
				else if (lancamento_global.getTipo_lancamento() == 3)
					substituirDocumentoParcelaEmprestimo(caminho_arquivo);

				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}
		});
	}

	public void substituirDocumentoParcela(String caminho_arquivo) {

		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
		int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela
		Parcela parcela_atualizar = gerenciar.getParcela(modelo_parcelas.getValue(rowSel).getId_parcela());

		/*********/
		// verificar se a pasta da parcela existe
		ManipularTxt manipular = new ManipularTxt();
		File pasta_de_parcelas = new File(servidor_unidade + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
				+ lancamento_global.getId_lancamento() + "\\documentos");
		manipular.criarDiretorio(pasta_de_parcelas.getAbsolutePath());

		String extensaoDoArquivoAntigo = FilenameUtils.getExtension(parcela_atualizar.getCaminho_arquivo());

		String arquivo_doc_parcela_final = pasta_de_parcelas + "\\doc_parcela_" + parcela_atualizar.getId_parcela()
				+ "." + extensaoDoArquivoAntigo;

		// verifica se existe algum arquivo de parcela anteriror
		File arquivo_parcela = new File(arquivo_doc_parcela_final);
		if (arquivo_parcela.exists()) {
			// excluir parcela
			boolean excluido = manipular.apagarArquivo(arquivo_parcela.getAbsolutePath());

		}

		// copiar o arquivo para a pasta da parcela
		// antes verificar se o arquivo nao e nulo
		String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);

		if (caminho_arquivo != null && caminho_arquivo.length() > 20) {
			boolean copiar_arquivo = false;
			try {
				copiar_arquivo = manipular.copiarNFe(caminho_arquivo, pasta_de_parcelas + "\\doc_parcela_"
						+ parcela_atualizar.getId_parcela() + "." + extensaoDoArquivo);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (copiar_arquivo) {
				// atualiza parcela no banco de dados

				boolean atualizar = gerenciar.atualizarArquivoDaParcela(
						"doc_parcela_" + parcela_atualizar.getId_parcela() + "." + extensaoDoArquivo,
						parcela_atualizar.getId_parcela());
				if (atualizar) {
					// salvar como documento no banco de dados
					// atualizar status do contrato
					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();

					//excluir documento anteriror
					if(parcela_atualizar.getId_documento() > 0) {
						gerenciar_doc.removerDocumento(parcela_atualizar.getId_documento());
					}
					
					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Arquivo de Parcela Original");
					novo_documento.setNome("doc_parcela_" + parcela_atualizar.getId_parcela());

					novo_documento.setTipo(5);
					novo_documento.setId_pai(parcela_atualizar.getId_parcela());
					novo_documento.setNome_arquivo(
							"doc_parcela_" + parcela_atualizar.getId_parcela() + "." + extensaoDoArquivo);
					novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

					int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
					if (cadastrar > 0) {
						if(gerenciar.atualizarIdDocumento(cadastrar, parcela_atualizar.getId_parcela())) {
							atualizarArvoreDocumentos();

						}else {
							JOptionPane.showMessageDialog(isto, "Id do documento não atualizado no lançamento");

						}

					} else {
						JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

					}

					JOptionPane.showMessageDialog(isto, "Arquivo da parcela atualizado");
				} else {
					JOptionPane.showMessageDialog(isto,
							"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

			}
		}
		rotinas();

		/***********/
	}

	public void substituirDocumentoParcelaEmprestimo(String caminho_arquivo) {

		GerenciarBancoParcelasEmprestimo gerenciar = new GerenciarBancoParcelasEmprestimo();
		int rowSel = tabela_parcelas.getSelectedRow();// pega o indice da linha na tabela
		ParcelaEmprestimo parcela_atualizar = gerenciar
				.getParcela(modelo_parcelas_emprestimo.getValue(rowSel).getId_parcela());

		/*********/
		// verificar se a pasta da parcela existe
		ManipularTxt manipular = new ManipularTxt();
		File pasta_de_parcelas = new File(servidor_unidade + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
				+ lancamento_global.getId_lancamento() + "\\documentos");
		manipular.criarDiretorio(pasta_de_parcelas.getAbsolutePath());

		String extensaoDoArquivoAntigo = FilenameUtils.getExtension(parcela_atualizar.getCaminho_arquivo());

		String arquivo_doc_parcela_final = pasta_de_parcelas + "\\doc_parcela_" + parcela_atualizar.getId_parcela()
				+ "." + extensaoDoArquivoAntigo;

		// verifica se existe algum arquivo de parcela anteriror
		File arquivo_parcela = new File(arquivo_doc_parcela_final);
		if (arquivo_parcela.exists()) {
			// excluir parcela
			boolean excluido = manipular.apagarArquivo(arquivo_parcela.getAbsolutePath());

		}

		// copiar o arquivo para a pasta da parcela
		// antes verificar se o arquivo nao e nulo
		String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);

		if (caminho_arquivo != null && caminho_arquivo.length() > 20) {
			boolean copiar_arquivo = false;
			try {
				copiar_arquivo = manipular.copiarNFe(caminho_arquivo, pasta_de_parcelas + "\\doc_parcela_"
						+ parcela_atualizar.getId_parcela() + "." + extensaoDoArquivo);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (copiar_arquivo) {
				// atualiza parcela no banco de dados

				boolean atualizar = gerenciar.atualizarArquivoDaParcela(
						"doc_parcela_" + parcela_atualizar.getId_parcela() + "." + extensaoDoArquivo,
						parcela_atualizar.getId_parcela());
				if (atualizar) {

					// salvar como documento no banco de dados
					// atualizar status do contrato
					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();

					//excluir documento anteriror
					if(parcela_atualizar.getId_documento() > 0) {
						gerenciar_doc.removerDocumento(parcela_atualizar.getId_documento());
					}
					
					
					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Arquivo de Parcela Original");
					novo_documento.setNome("doc_parcela_" + parcela_atualizar.getId_parcela());

					novo_documento.setTipo(5);
					novo_documento.setId_pai(parcela_atualizar.getId_parcela());
					novo_documento.setNome_arquivo(
							"doc_parcela_" + parcela_atualizar.getId_parcela() + "." + extensaoDoArquivo);
					novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

					int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
					if (cadastrar > 0) {
						if(gerenciar.atualizarIdDocumento(cadastrar, parcela_atualizar.getId_parcela())) {
							atualizarArvoreDocumentos();

						}else {
							JOptionPane.showMessageDialog(isto, "Id do documento não atualizado no lançamento");

						}
					} else {
						JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

					}

					JOptionPane.showMessageDialog(isto, "Arquivo da parcela atualizado");
				} else {
					JOptionPane.showMessageDialog(isto,
							"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

			}
		}
		rotinas();

		/***********/
	}

	public void selecionarDocumentoPagamento() {

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo referente ao pagamento selecionado!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				if (lancamento_global.getTipo_lancamento() == 0 || lancamento_global.getTipo_lancamento() == 1 || lancamento_global.getTipo_lancamento() == 2)
					substituirDocumentoPagamento(caminho_arquivo);
				else if (lancamento_global.getTipo_lancamento() == 3)
					substituirDocumentoPagamentoEmprestimo(caminho_arquivo);
				// JOptionPane.showMessageDialog(isto, "CAminho do arquivo selecionado: " +
				// file.getAbsolutePath());
			}
		});
	}

	public void substituirDocumentoPagamento(String caminho_arquivo) {

		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
		FinanceiroPagamento pag = gerenciar
				.getFinanceiroPagamento(modelo_pagamentos.getValue(rowSel).getId_pagamento());

		/*********/
		// verificar se a pasta da parcela existe
		ManipularTxt manipular = new ManipularTxt();
		File pasta_de_pagamentos = new File(
				servidor_unidade + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
						+ lancamento_global.getId_lancamento() + "\\documentos");
		manipular.criarDiretorio(pasta_de_pagamentos.getAbsolutePath());

		String extensaoDoArquivoAntigo = FilenameUtils.getExtension(pag.getCaminho_arquivo());
		String arquivo_doc_pagamento_final = pasta_de_pagamentos + "\\doc_comprovante_pagamento_"
				+ pag.getId_pagamento() + "." + extensaoDoArquivoAntigo;

		// verifica se existe algum arquivo de parcela anteriror
		File arquivo_pagamento = new File(arquivo_doc_pagamento_final);
		if (arquivo_pagamento.exists()) {
			// excluir parcela
			manipular.apagarArquivo(arquivo_pagamento.getAbsolutePath());
		}

		// copiar o arquivo para a pasta da parcela
		// antes verificar se o arquivo nao e nulo
		String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);
		if (caminho_arquivo != null && caminho_arquivo.length() > 20) {
			boolean copiar_arquivo = false;
			try {
				copiar_arquivo = manipular.copiarNFe(caminho_arquivo, pasta_de_pagamentos
						+ "\\doc_comprovante_pagamento_" + pag.getId_pagamento() + "." + extensaoDoArquivo);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (copiar_arquivo) {
				// atualiza parcela no banco de dados
				boolean atualizar = gerenciar.atualizarArquivoDoPagamento(
						"doc_comprovante_pagamento_" + pag.getId_pagamento() + "." + extensaoDoArquivo,
						pag.getId_pagamento());
				if (!atualizar)
					JOptionPane.showMessageDialog(isto,
							"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");
				else {
					// salvar como documento no banco de dados
					// atualizar status do contrato
					
					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();

					//excluir documento anteriror
					if(pag.getId_documento() > 0) {
						gerenciar_doc.removerDocumento(pag.getId_documento());
					}
					
					
					
					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Arquivo de Comprovante de Pagamento");
					novo_documento.setNome("doc_comprovante_pagamento_" + pag.getId_pagamento());

					novo_documento.setTipo(2);
					novo_documento.setId_pai(pag.getId_pagamento());
					novo_documento.setNome_arquivo(
							"doc_comprovante_pagamento_" + pag.getId_pagamento() + "." + extensaoDoArquivo);
					novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

					int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
					if (cadastrar > 0 ) {
						if(gerenciar.atualizarIdDoDocumento(cadastrar,pag.getId_pagamento())) {
							atualizarArvoreDocumentos();

						}else {
							JOptionPane.showMessageDialog(isto, "Id do documento não atualizado no lançamento");

						}
					} else {
						JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

					}

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

			}
		}

		/***********/
		rotinas();

		/***********/
	}

	public void substituirDocumentoPagamentoEmprestimo(String caminho_arquivo) {

		GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar = new GerenciarBancoFinanceiroPagamentoEmprestimo();
		int rowSel = tabela_pagamentos.getSelectedRow();// pega o indice da linha na tabela
		FinanceiroPagamentoEmprestimo pag = gerenciar
				.getFinanceiroPagamento(modelo_pagamentos_emprestimo.getValue(rowSel).getId_pagamento());

		/*********/
		// verificar se a pasta da parcela existe
		ManipularTxt manipular = new ManipularTxt();

		File pasta_de_pagamentos = new File(
				servidor_unidade + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
						+ lancamento_global.getId_lancamento() + "\\documentos");
		manipular.criarDiretorio(pasta_de_pagamentos.getAbsolutePath());

		String extensaoDoArquivoAntigo = FilenameUtils.getExtension(pag.getCaminho_arquivo());
		String arquivo_doc_pagamento_final = pasta_de_pagamentos + "\\doc_comprovante_pagamento_"
				+ pag.getId_pagamento() + "." + extensaoDoArquivoAntigo;

		// verifica se existe algum arquivo de parcela anteriror
		File arquivo_pagamento = new File(arquivo_doc_pagamento_final);
		if (arquivo_pagamento.exists()) {
			// excluir parcela
			manipular.apagarArquivo(arquivo_pagamento.getAbsolutePath());
		}

		// copiar o arquivo para a pasta da parcela
		// antes verificar se o arquivo nao e nulo
		String extensaoDoArquivo = FilenameUtils.getExtension(caminho_arquivo);
		if (caminho_arquivo != null && caminho_arquivo.length() > 20) {
			boolean copiar_arquivo = false;
			try {
				copiar_arquivo = manipular.copiarNFe(caminho_arquivo, pasta_de_pagamentos
						+ "\\doc_comprovante_pagamento_" + pag.getId_pagamento() + "." + extensaoDoArquivo);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (copiar_arquivo) {
				// atualiza parcela no banco de dados
				boolean atualizar = gerenciar.atualizarArquivoDoPagamento(
						"doc_comprovante_pagamento_" + pag.getId_pagamento() + "." + extensaoDoArquivo,
						pag.getId_pagamento());
				if (!atualizar)
					JOptionPane.showMessageDialog(isto,
							"Arquivo Copiado, Mas não atualizado no Banco de dados\nConsulte o administrador");
				else {
					// salvar como documento no banco de dados
					// atualizar status do contrato

					GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();

					//excluir documento anteriror
					if(pag.getId_documento() > 0) {
						gerenciar_doc.removerDocumento(pag.getId_documento());
					}
					
					
					CadastroDocumento novo_documento = new CadastroDocumento();
					novo_documento.setDescricao("Arquivo de Comprovante de Pagamento");
					novo_documento.setNome("doc_comprovante_pagamento_" + pag.getId_pagamento());

					novo_documento.setTipo(2);
					novo_documento.setId_pai(pag.getId_pagamento());
					novo_documento.setNome_arquivo(
							"doc_comprovante_pagamento_" + pag.getId_pagamento() + "." + extensaoDoArquivo);
					novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

					int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
					if (cadastrar > 0) {
						if(gerenciar.atualizarIdDocumento(cadastrar,pag.getId_pagamento())) {
							atualizarArvoreDocumentos();

						}else {
							JOptionPane.showMessageDialog(isto, "Id do documento não atualizado no lançamento");

						}
					} else {
						JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

					}

				}
			} else {
				JOptionPane.showMessageDialog(isto, "Arquivo não copiado, consulte o administrador");

			}
		}

		/***********/
		rotinas();

		/***********/
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

	public void atualizarValorLancamento(Lancamento lancamento_pai) {

		GerenciarBancoFinanceiroPagamento gerenciar_pag = new GerenciarBancoFinanceiroPagamento();
		BigDecimal valor_total_pagamentos = BigDecimal.ZERO;

		for (FinanceiroPagamento pagamento : gerenciar_pag
				.getFinanceiroPagamentosPorLancamento(lancamento_pai.getId_lancamento())) {

			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());

		}

		BigDecimal valor_pagamentos_retroativos = valor_total_pagamentos;
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();

		BigDecimal valor_total_parcelas = BigDecimal.ZERO;

		for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_pai.getId_lancamento())) {
			BigDecimal valor_parcela = parcela.getValor();
			if (lancamento_pai.getTipo_lancamento() == 0) {
				// é uma despesa
				if (valor_pagamentos_retroativos.compareTo(valor_parcela) >= 0) {
					gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());

				} else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
					gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

				}
			} else if (lancamento_pai.getTipo_lancamento() == 1) {
				// é uma receita
				if (valor_pagamentos_retroativos.compareTo(valor_parcela) >= 0) {
					gerenciar.atualizarStatusParcela(1, parcela.getId_parcela());

				} else if (valor_pagamentos_retroativos.compareTo(valor_parcela) < 0) {
					gerenciar.atualizarStatusParcela(0, parcela.getId_parcela());

				}
			}

			valor_pagamentos_retroativos = valor_pagamentos_retroativos.subtract(valor_parcela);
			valor_total_parcelas = valor_total_parcelas.add(valor_parcela);

		}
		new GerenciarBancoLancamento().atualizarValorLancamento(valor_total_parcelas.toString(),
				lancamento_pai.getId_lancamento());

		if (valor_total_pagamentos.compareTo(valor_total_parcelas) >= 0) {
			// pagamento concluido
			// atualizar status do lancmento
			if (lancamento_pai.getTipo_lancamento() == 0) // é uma despesa
				new GerenciarBancoLancamento().atualizarStatusLancamento(1, lancamento_pai.getId_lancamento());
			else if (lancamento_pai.getTipo_lancamento() == 1)// é uma receita
				new GerenciarBancoLancamento().atualizarStatusLancamento(3, lancamento_pai.getId_lancamento());

		} else if (valor_total_pagamentos.compareTo(valor_total_parcelas) < 0) {
			// pagamento incompleto
			if (lancamento_pai.getTipo_lancamento() == 0) // é uma despesa
				new GerenciarBancoLancamento().atualizarStatusLancamento(0, lancamento_pai.getId_lancamento());
			else if (lancamento_pai.getTipo_lancamento() == 1)// é uma receita
				new GerenciarBancoLancamento().atualizarStatusLancamento(2, lancamento_pai.getId_lancamento());

		}

	}

	public void setDestinatarioNF(CadastroCliente _cliente) {
		destinatario_nf = _cliente;

		cBDestinatarioDaNF.removeAll();
		if (destinatario_nf.getTipo_pessoa() == 0) {
			cBDestinatarioDaNF.addItem(destinatario_nf.getNome_empresarial());

		} else {
			cBDestinatarioDaNF.addItem(destinatario_nf.getNome_fantaia());

		}
		cBDestinatarioDaNF.addItem("Indefinido");

	}

	public class ReciboTableModel extends AbstractTableModel {
		
		// constantes p/identificar colunas
		private final int id = 0;
		private final int tipo = 1;
		private final int status = 2;
		private final int data = 3;
		private final int refere_se = 4;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Tipo", "Status", "Data", "Refere-se" };

		private final ArrayList<CadastroRecibo> dados = new ArrayList<>();// usamos como dados uma lista genérica
																				// de
																				// nfs

		public ReciboTableModel() {

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
			case id: {
				return Integer.class;

			}
			case tipo: {
				return String.class;
			}
			case status: {
				return String.class;
			}
			case data: {
				return String.class;
			}
			case refere_se: {
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
			CadastroRecibo dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id: {
				return dado.getId_recibo();

			}
			case tipo: {
				int tipo_recibo = dado.getTipo_recibo();
				if(tipo_recibo == 0) {
					//recibo de pagamento
					return ("Recibo de Pagamento");
				}
			}
			case status: {
				if(dado.getStatus_recibo() == 0) {
					return "Assinar";

				}else if(dado.getStatus_recibo() ==  1) {
					return "Assinado";

				}
			}
			case data: {
				return dado.getData_recibo();
			}
			case refere_se: {
				int id_pai = dado.getId_pai();
				if(dado.getTipo_recibo() == 0) {
					FinanceiroPagamento pag = new FinanceiroPagamento();
					if(id_pai > 0) {
					
						pag = new GerenciarBancoFinanceiroPagamento().getFinanceiroPagamento(id_pai);
						return "Recibo do Pagamento " + pag.getId_pagamento();
						
					}
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
			CadastroRecibo ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public CadastroRecibo getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(CadastroRecibo dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(CadastroRecibo dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<CadastroRecibo> dadosIn) {
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
		public void onRemove(CadastroRecibo dado) {
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

		public CadastroRecibo onGet(int row) {
			return dados.get(row);
		}
	}
	
	public class TarefaTableModel extends AbstractTableModel {
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

			return false;
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

	public void pesquisar_tarefas() {
		GerenciarBancoTarefaGeral gerenciar = new GerenciarBancoTarefaGeral();
		lista_tarefas.clear();
		modelo_tarefa.onRemoveAll();

		for (CadastroTarefaGeral tarefa : gerenciar.consultaTarefasLancamentos(lancamento_global.getId_lancamento())) {

			modelo_tarefa.onAdd(tarefa);
			lista_tarefas.add(tarefa);

		}
	}

	public void pesquisar_recibos() {
		GerenciarBancoRecibos gerenciar = new GerenciarBancoRecibos();
		lista_recibos.clear();
		modelo_recibos.onRemoveAll();

		for (CadastroRecibo recibo : gerenciar.getRecibosPorLancamentoPai(lancamento_global.getId_lancamento())) {

			modelo_recibos.onAdd(recibo);
			lista_recibos.add(recibo);

		}
	}

	
	public void criarTarefa() {
		// Criar tarefa
		GerenciarBancoTarefaGeral gerenciar_tarefa = new GerenciarBancoTarefaGeral();
		CadastroTarefaGeral tarefa = new CadastroTarefaGeral();

		tarefa.setNome_tarefa("Envio de lançamento ao contador");
		tarefa.setDescricao_tarefa("");
		tarefa.setMensagem("");
		tarefa.setCriador(login);
		tarefa.setExecutor(login);
		tarefa.setStatus_tarefa(1);
		tarefa.setPrioridade(1);
		tarefa.setTipo(1);

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

		pesquisar_tarefas();
	}

	public void atualizarArvoreDocumentos() {

		new Thread() {
			@Override
			public void run() {
				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
				ArrayList<CadastroDocumento> lista_docs = gerenciar_doc
						.getDocumentosLancamento(lancamento_global.getId_lancamento());

				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {

						DefaultTreeModel model = (DefaultTreeModel) arvore_documentos.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

						root.removeAllChildren();

						no_docs_pessoais.removeAllChildren();
						no_docs_originais.removeAllChildren();
						no_comprovantes.removeAllChildren();
						no_outros.removeAllChildren();

						no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
						no_docs_originais = new DefaultMutableTreeNode("Documentos Originais");
						no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
						no_outros = new DefaultMutableTreeNode("Outros");

						root.add(no_docs_pessoais);
						root.add(no_docs_originais);
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

								} else if (doc.getTipo() == 5) {
									no_docs_originais.add(new DefaultMutableTreeNode(
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

	public void selecionarDocumento() {

		JOptionPane.showMessageDialog(null, "Na próxima tela, importe o arquivo referente ao pagamento selecionado!");

		new JFXPanel();
		Platform.runLater(() -> {
			FileChooser d = new FileChooser();
			File file = d.showOpenDialog(null);
			String caminho_arquivo = "";
			if (file != null) {
				caminho_arquivo = file.getAbsolutePath();

				entCaminhoDocumento.setText(caminho_arquivo);
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
			// copiar o arquivo para a pasta do lancamento
			ManipularTxt manipular = new ManipularTxt();
			String unidade_base_dados = configs_globais.getServidorUnidade();

			// pegar nome da pasta
			String nome_pasta = "lancamento_" + lancamento_global.getId_lancamento();

			String caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\"
					+ nome_pasta + "\\documentos";
			manipular.criarDiretorio(caminho_salvar);

			GetData dados = new GetData();
			String dataString = dados.getData();
			String horaString = dados.getHora();

			if (caminho_arquivo.length() > 10) {
				if (nome.length() != 0 && !nome.equals("") && !nome.equals(" ") && !nome.equals("          ")) {
					nome_arquivo = nome + "_" + horaString.replaceAll(":", "_") + "." + extensaoDoArquivo;

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
						} else if (s_tipo_documento.equalsIgnoreCase("Documento Original")) {
							tipo_documento = 5;
						}

						novo_documento.setTipo(tipo_documento);
						novo_documento.setId_pai(0);
						novo_documento.setNome_arquivo(nome_arquivo);
						novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

						GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
						int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
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

	public void setInformacoesDocumentos() {

		// pega a lista de documentos
		GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		ArrayList<CadastroDocumento> lista_docs = gerenciar_doc
				.getDocumentosLancamento(lancamento_global.getId_lancamento());
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				panel_docs.setLayout(new MigLayout("", "[grow]", "[grow]"));

				JPanel panel = new JPanel();
				panel.setBackground(new Color(0, 153, 153));
				panel_docs.add(panel, "cell 0 0,grow");
				panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

				JLabel lblNewLabel_18 = new JLabel("Documentos deste Lançamento:");
				lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
				panel.add(lblNewLabel_18, "cell 0 0");

				// create the root node
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
				// create the child nodes
				no_docs_pessoais = new DefaultMutableTreeNode("Documentos Pessoais");
				no_docs_originais = new DefaultMutableTreeNode("Documentos Originais");

				no_comprovantes = new DefaultMutableTreeNode("Comprovantes");
				no_outros = new DefaultMutableTreeNode("Outros");

				// add the child nodes to the root node
				root.add(no_docs_pessoais);
				root.add(no_docs_originais);
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
				JMenuItem jMenuItemEnviar = new JMenuItem();


				jMenuItemVizualizar.setText("Vizualizar");
				jMenuItemExcluir.setText("Excluir");
				jMenuItemEnviar.setText("Enviar");
				
				jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
					// Importe a classe java.awt.event.ActionEvent
					public void actionPerformed(ActionEvent e) {
						String nome_arquivo = no_selecionado.getUserObject().toString();
						String caminho_salvar = "";
						String quebra[] = nome_arquivo.split("@");

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}

						String nome_pasta = "lancamento_" + lancamento_global.getId_lancamento();

						String unidade_base_dados = configs_globais.getServidorUnidade();
						caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\"
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
						String nome_arquivo = no_selecionado.getUserObject().toString();
						String caminho_salvar = "";
						String quebra[] = nome_arquivo.split("@");

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}

						String nome_pasta = "lancamento_" + lancamento_global.getId_lancamento();

						String unidade_base_dados = configs_globais.getServidorUnidade();
						caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\"
								+ nome_pasta + "\\documentos\\" + nome_official;

						GerenciarBancoDocumento gerenciar_docs = new GerenciarBancoDocumento();
						boolean excluido = gerenciar_docs.removerDocumento(Integer.parseInt(quebra[0]));
						if(excluido) {
							JOptionPane.showMessageDialog(isto, "Documento Excluído");
							atualizarArvoreDocumentos();

						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir o documento\nConsulte o administrador");

						}
						
					}
				});
				
				jMenuItemEnviar.addActionListener(new java.awt.event.ActionListener() {
					// Importe a classe java.awt.event.ActionEvent
					public void actionPerformed(ActionEvent e) {
						String nome_arquivo = no_selecionado.getUserObject().toString();
						String caminho_salvar = "";
						String quebra[] = nome_arquivo.split("@");

						String nome_official = "";
						for (int i = 1; i < quebra.length; i++) {
							nome_official += quebra[i];
						}

						String nome_pasta = "lancamento_" + lancamento_global.getId_lancamento();

						String unidade_base_dados = configs_globais.getServidorUnidade();
						caminho_salvar = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\"
								+ nome_pasta + "\\documentos\\" + nome_official;

						
						TelaEscolha tela = new TelaEscolha(2, caminho_salvar, isto);
						tela.setVisible(true);
						
					}
				});

			
			

				jPopupMenu.add(jMenuItemVizualizar);
				jPopupMenu.add(jMenuItemExcluir);
				jPopupMenu.add(jMenuItemEnviar);


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

						} else if (doc.getTipo() == 5) {
							// outros
							no_docs_originais.add(
									new DefaultMutableTreeNode(doc.getId_documento() + "@" + doc.getNome_arquivo()));

						}
					}
				}

				expandAllNodes(arvore_documentos, 0, arvore_documentos.getRowCount());

			}
		});

	}
	
	public CadastroRecibo getReciboSelecionado() {
		int indiceDaLinha = table_recibos.getSelectedRow();
		CadastroRecibo recibo = modelo_recibos.onGet(indiceDaLinha);
		return recibo;
		
	}
	
public void salvar(String caminho_arquivo_temp) {
		
		
		GetData hj = new GetData();
		String hora =  hj.getHora().replace(":", " ");
		
	     //criar o documento
		String unidade_base_dados = configs_globais.getServidorUnidade();
        String nome_arquivo = "doc_contrato_emprestimo_" + hj.getAnoAtual() +"_"+ lancamento_global.getId_lancamento() +  "_" + hora;
		//criar pasta documentos
        ManipularTxt manipular = new ManipularTxt();
        
        
        String caminho_salvar = unidade_base_dados + "E-Contract\\arquivos\\financas\\lancamentos\\lancamento_"
        + lancamento_global.getId_lancamento() + "\\documentos\\";
		manipular.criarDiretorio(caminho_salvar);
        
        String caminho_completo = caminho_salvar  + nome_arquivo;
		boolean salvar = editar_word.criarArquivo(caminho_completo);

		if(salvar) {

			CadastroRecibo recibo = new CadastroRecibo();
			recibo.setData_recibo(new GetData().getData());
			recibo.setStatus_recibo(0);
			recibo.setId_pai( lancamento_global.getId_lancamento());
			recibo.setId_lancamento_pai( lancamento_global.getId_lancamento());

			recibo.setNome_arquivo(nome_arquivo + ".pdf");
			
			GerenciarBancoRecibos gerenciar = new GerenciarBancoRecibos();
			int guardar = gerenciar.inserirrecibo(recibo);
			
			if(guardar > 0) {
				
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
				JOptionPane.showMessageDialog(isto, "Recibo Criado com sucesso");
				//((TelaGerenciarContrato) telaPai).setInformacoesrecibos();
				//salvar documento
				GerenciarBancoDocumento gerenciar_doc = new GerenciarBancoDocumento();
		
		
				CadastroDocumento novo_documento = new CadastroDocumento();
				novo_documento.setDescricao("Arquivo de Contrato de Empréstimo");
				novo_documento.setNome("doc_contrato_emprestimo_" +  lancamento_global.getId_lancamento());

				novo_documento.setTipo(5);
				novo_documento.setId_pai(lancamento_global.getId_lancamento());
				novo_documento.setNome_arquivo(nome_arquivo +   ".pdf");
				novo_documento.setId_lancamento(lancamento_global.getId_lancamento());

				int cadastrar = gerenciar_doc.inserirDocumentoLancamento(novo_documento);
				if (cadastrar > 0 ) {
					atualizarArvoreDocumentos();
					

				} else {
					JOptionPane.showMessageDialog(isto, "Documento não inserido no banco de dados");

				}
				pesquisar_recibos();

				isto.dispose();

			}else {
				JOptionPane.showMessageDialog(isto, "Erro ao guardar o recibo na base de dados!\nConsulte o administrador");
                boolean deletar_arquivo = manipular.apagarArquivo(caminho_completo + ".docx");
                boolean deletar_pdf = manipular.apagarArquivo(caminho_completo + ".pdf");

			}
			
			
			
			
		}else {
			JOptionPane.showMessageDialog(isto, "Erro ao salvar o arquivo fisico!\nConsulte o administrador");

		}
		
		
	}
}
