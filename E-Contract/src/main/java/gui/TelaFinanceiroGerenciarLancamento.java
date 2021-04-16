package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoContratos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

public class TelaFinanceiroGerenciarLancamento extends JFrame  {

	private JTabbedPane painelPrincipal ;
	private JPanel painelDadosIniciais = new JPanel();
	private JPanel painelPagamentos = new JPanel();
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Tipo Lançamento:");
	private final JLabel lblNewLabel_1 = new JLabel("Centro de Custo:");
	private final JLabel lblNewLabel_1_1 = new JLabel("Instituição Bancária:");
	private final JLabel lblNewLabel_1_1_1 = new JLabel("Cliente/Fornecedor:");
	private final JLabel entTipoLancamento = new JLabel("Tipo Lançamento");
	private final JLabel entCentroCusto = new JLabel("Centro Custo");
	private final JLabel entInstituicaoBancaria = new JLabel("Instituição Bancaria");
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
	private JPanel painelVizualizarRomaneio;
	 private JPanel painel_vizualizar;
	    private SwingController controller = null;
		private SwingViewBuilder factory;
		private String servidor_unidade;
		private final JLabel lblNewLabel_1_1_1_1_2_1_1_1 = new JLabel("Numero de Parcelas:");
		private final JLabel lblNewLabel_1_1_1_1_2_1_1_1_1_1 = new JLabel("Intervalo:");
		private final JLabel entNumeroParcelas = new JLabel("Número de Parcelas");
		private final JLabel entIntervalo = new JLabel("Intervalo");
		private final JLabel lblPrioridade = new JLabel("Prioridade:");
		private final JLabel entPrioridade = new JLabel("Prioridade");
		private final JPanel panel_1 = new JPanel();
		private final JLabel lblNewLabel_2 = new JLabel("Parcelas");
		private final JScrollPane scrollParcelas ;
		private final JPanel panel_2 = new JPanel();
		private final JScrollPane scrollPagamentos;
		private final JLabel lblNewLabel_4 = new JLabel("Pagamentos");
		private JTable tabela_pagamentos, tabela_parcelas;
		private final JButton btnInserirParcela = new JButton("Inserir");
		private ParcelaTableModel modelo_parcelas = new ParcelaTableModel();
		private ArrayList<Parcela> lista_parcelas = new ArrayList<>();
		private PagamentoTableModel modelo_pagamentos = new PagamentoTableModel();
		private ArrayList<FinanceiroPagamento> lista_pagamentos = new ArrayList<>();
		
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
		private final JPanel painel_vizualizar_parcela = new JPanel();
		private final JButton btnEditar = new JButton("Editar");
		private final JButton btnInserirParcela_1_1 = new JButton("Excluir");
		private final JPanel panel_10 = new JPanel();
		private final JPanel painel_vizualizar_parcela_1 = new JPanel();
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
		
		
	public TelaFinanceiroGerenciarLancamento(Lancamento lancamento, Window janela_pai) {

		TelaFinanceiroGerenciarLancamento isto = this;
		
		setResizable(true);
		
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
		
		DisplayMode display =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		
		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);
	
				
		setTitle("E-Contract - Gerenciar Lançamento");
		painelPrincipal = new JTabbedPane();
		setContentPane(painelPrincipal);
		painelDadosIniciais.setBackground(new Color(255, 255, 255));
		painelPagamentos.setBackground(new Color(255, 255, 255));

		//adiciona novos paines e suas abas
		painelPrincipal.addTab("Dados Iniciais", painelDadosIniciais);
		painelDadosIniciais.setLayout(new BorderLayout(0, 0));
		panel.setBackground(Color.WHITE);
		
		painelDadosIniciais.add(panel);
		panel.setLayout(new MigLayout("", "[363px,grow][951px,grow]", "[30px:n,grow][155px,grow][5px][215px,grow][7px][55px,grow][12px][120px:n,grow][18px,grow][47px,grow]"));
		
		panel.add(lblNewLabel_8, "cell 0 0 2 1");
		panel_6.setBackground(new Color(0, 153, 153));
		
		panel.add(panel_6, "cell 0 1,grow");
		panel_6.setLayout(new MigLayout("", "[][]", "[][][][]"));
		lblStatus.setForeground(Color.WHITE);
		panel_6.add(lblStatus, "cell 0 0");
		lblStatus.setBackground(Color.WHITE);
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_6.add(entStatus, "cell 1 0");
		entStatus.setOpaque(true);
		entStatus.setForeground(Color.WHITE);
		entStatus.setBackground(new Color(0, 0, 51));
		entStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblData.setForeground(Color.WHITE);
		panel_6.add(lblData, "cell 0 1");
		lblData.setBackground(Color.WHITE);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));
		entData.setForeground(Color.WHITE);
		panel_6.add(entData, "cell 1 1");
		entData.setBackground(Color.WHITE);
		entData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPrioridade.setForeground(Color.WHITE);
		panel_6.add(lblPrioridade, "cell 0 2");
		lblPrioridade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPrioridade.setBackground(Color.WHITE);
		entPrioridade.setForeground(Color.WHITE);
		panel_6.add(entPrioridade, "cell 1 2");
		entPrioridade.setFont(new Font("Tahoma", Font.PLAIN, 18));
		entPrioridade.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.WHITE);
		panel_6.add(lblNewLabel, "cell 0 3");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_6.add(entTipoLancamento, "cell 1 3");
		entTipoLancamento.setForeground(Color.WHITE);
		entTipoLancamento.setOpaque(true);
		entTipoLancamento.setBackground(new Color(204, 0, 0));
		entTipoLancamento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		painelVizualizarDocumento.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		painelVizualizarDocumento.setBackground(Color.WHITE);
		
		panel.add(painelVizualizarDocumento, "cell 1 1 1 5,grow");
		painelVizualizarDocumento.setLayout(new BorderLayout(0, 0));
		//*
		
		
		//*
		panel_5.setBackground(new Color(0, 0, 102));
		
		panel.add(panel_5, "cell 0 5 1 5,growx,aligny top");
		panel_5.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));
		lblNewLabel_3_1.setBackground(new Color(0, 0, 0));
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
		lblNewLabel_1_1_1_1_2_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1_1, "cell 0 3,alignx right");
		lblNewLabel_1_1_1_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entDataVencimento.setForeground(Color.WHITE);
		panel_5.add(entDataVencimento, "cell 1 3,alignx left");
		entDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1, "cell 0 4,alignx right");
		lblNewLabel_1_1_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entValor.setForeground(Color.WHITE);
		panel_5.add(entValor, "cell 1 4,alignx left");
		entValor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1_1_1, "cell 0 5,alignx right");
		lblNewLabel_1_1_1_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entNumeroParcelas.setForeground(Color.WHITE);
		panel_5.add(entNumeroParcelas, "cell 1 5,alignx left");
		entNumeroParcelas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_2_1_1_1_1_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1_1_1_1_2_1_1_1_1_1, "cell 0 7,alignx right");
		lblNewLabel_1_1_1_1_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entIntervalo.setForeground(Color.WHITE);
		panel_5.add(entIntervalo, "cell 1 7,alignx left");
		entIntervalo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_7.setBackground(new Color(255, 255, 204));
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel.add(panel_7, "cell 1 7,growx,aligny top");
		panel_7.setLayout(new MigLayout("", "[][grow]", "[50px:n,grow][50px:n,grow]"));
		lblNewLabel_7.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_7.add(lblNewLabel_7, "cell 0 0");
		entDescricaoLancamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_7.add(entDescricaoLancamento, "cell 1 0,grow");
		lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_7.add(lblNewLabel_7_1, "cell 0 1");
		entObservacaoLancamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_7.add(entObservacaoLancamento, "cell 1 1,grow");
		panel_9.setBackground(Color.WHITE);
		
		panel.add(panel_9, "cell 1 9,alignx center,aligny top");
		panel_9.setLayout(new MigLayout("", "[73px]", "[33px]"));
		panel_9.add(btnNewButton, "cell 0 0,alignx left,aligny top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaFinanceiroCadastroLancamento tela = new TelaFinanceiroCadastroLancamento(1, lancamento_global, isto);
				tela.setVisible(true);
			}
		});
		btnNewButton.setBackground(new Color(255, 153, 0));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrincipal.addTab("Pagamentos", painelPagamentos);
		painelPagamentos.setLayout(new MigLayout("", "[][][][][200px:n][1px:n:1px][][grow]", "[][][][grow][grow][grow][grow][grow]"));
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelPagamentos.add(lblNewLabel_2, "cell 0 2,alignx center");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		painelPagamentos.add(lblNewLabel_4, "cell 6 2 2 1,alignx center");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		
		painelPagamentos.add(panel_1, "cell 0 3 5 1,grow");
		panel_1.setLayout(new BorderLayout(0, 0));
		
		tabela_parcelas = new JTable();
		tabela_parcelas.setRowHeight(30);
		tabela_parcelas.setModel(modelo_parcelas);
		scrollParcelas = new JScrollPane(tabela_parcelas);
		
		tabela_parcelas.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

		    public void valueChanged(ListSelectionEvent e) {

		        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		        if(!lsm.isSelectionEmpty()){
		        	int rowSel = tabela_parcelas.getSelectedRow();//pega o indice da linha na tabela
		        	Parcela parcela = modelo_parcelas.getValue(rowSel);
		        	entObservacaoParcela.setText(parcela.getObservacao());
		        }
		    }
		});
		
		
		panel_1.add(scrollParcelas);
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		painelPagamentos.add(panel_2, "cell 6 3 2 1,grow");
		panel_2.setLayout(new BorderLayout(0, 0));
		
		tabela_pagamentos = new JTable(modelo_pagamentos);
		 scrollPagamentos = new JScrollPane(tabela_pagamentos);
		 tabela_pagamentos.setRowHeight(30);
		 
		 tabela_pagamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			    public void valueChanged(ListSelectionEvent e) {

			        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			        if(!lsm.isSelectionEmpty()){
			        	int rowSel = tabela_pagamentos.getSelectedRow();//pega o indice da linha na tabela
			        	FinanceiroPagamento pagamento = modelo_pagamentos.getValue(rowSel);
			        	entObservacaoPagamento.setText(pagamento.getObservacao());
			        }
			    }
			});
			
		 
		 
		panel_2.add(scrollPagamentos, BorderLayout.NORTH);
		painelObservacao.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelObservacao.setBackground(Color.WHITE);
		
		painelPagamentos.add(painelObservacao, "cell 0 4,grow");
		painelObservacao.setLayout(new MigLayout("", "[][grow]", "[100px:n,grow]"));
		lblNewLabel_5.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelObservacao.add(lblNewLabel_5, "cell 0 0");
		entObservacaoParcela.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		entObservacaoParcela.setBorder(new LineBorder(new Color(0, 0, 0)));
		entObservacaoParcela.setEditable(false);
		
		painelObservacao.add(entObservacaoParcela, "cell 1 0,grow");
		painel_vizualizar_parcela.setBackground(new Color(0, 0, 128));
		
		painelPagamentos.add(painel_vizualizar_parcela, "cell 1 4 4 2,grow");
		painel_vizualizar_parcela.setLayout(new BorderLayout(0, 0));
		panel_10.setBackground(Color.WHITE);
		
		painelPagamentos.add(panel_10, "cell 6 4 2 2,grow");
		panel_10.setLayout(new MigLayout("", "[260px][grow]", "[84px][4px][114px]"));
		panel_11.setBackground(Color.WHITE);
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_10.add(panel_11, "cell 0 0,grow");
		panel_11.setLayout(new MigLayout("", "[][grow]", "[100px:n,grow]"));
		lblNewLabel_5_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_11.add(lblNewLabel_5_2, "cell 0 0");
		entObservacaoPagamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_11.add(entObservacaoPagamento, "cell 1 0,grow");
		painel_vizualizar_parcela_1.setBackground(new Color(0, 0, 128));
		
		panel_10.add(painel_vizualizar_parcela_1, "cell 1 0 1 3,grow");
		painel_vizualizar_parcela_1.setLayout(new BorderLayout(0, 0));
		panel_12.setBackground(Color.WHITE);
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_10.add(panel_12, "cell 0 2,grow");
		panel_12.setLayout(new MigLayout("", "[175px,grow][40px,grow]", "[24px][24px]"));
		lblNewLabel_5_1_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		
		panel_12.add(lblNewLabel_5_1_1, "cell 0 0 2 1,alignx center,aligny top");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		panel_12.add(lblNewLabel_6_1_1, "cell 0 1,alignx left,aligny center");
		lblValorTotalDosPagamentos.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		panel_12.add(lblValorTotalDosPagamentos, "cell 1 1,alignx left,aligny top");
		painelCalculos.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelCalculos.setBackground(Color.WHITE);
		
		painelPagamentos.add(painelCalculos, "cell 0 5,grow");
		painelCalculos.setLayout(new MigLayout("", "[175px][40px]", "[24px][24px]"));
		lblNewLabel_5_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		
		painelCalculos.add(lblNewLabel_5_1, "cell 0 0,alignx center,aligny top");
		lblNewLabel_6_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		painelCalculos.add(lblNewLabel_6_1, "cell 0 1,alignx left,aligny center");
		lblValorTotalDasParcelas.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		painelCalculos.add(lblValorTotalDasParcelas, "cell 1 1,alignx left,aligny top");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				public TelaCadastroParcela(int modo_operacao, Parcela parcela, int id_lancamento_pai, Window janela_pai) {
				int rowSel = tabela_parcelas.getSelectedRow();//pega o indice da linha na tabela
				
				TelaCadastroParcela tela = new TelaCadastroParcela(1, modelo_parcelas.getValue(rowSel), lancamento_global.getId_lancamento(), isto);
				tela.setVisible(true);
			}
		});
		btnInserirParcela_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(isto, 
			            "Deseja excluir a Parcela?", "Excluir", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					int rowSel = tabela_parcelas.getSelectedRow();//pega o indice da linha na tabela

						boolean exclusao = new GerenciarBancoParcelas().removerParcela(modelo_parcelas.getValue(rowSel).getId_parcela());
						if(exclusao) {
							JOptionPane.showMessageDialog(isto, "Parcela Excluída");
						}else {
							JOptionPane.showMessageDialog(isto, "Erro ao excluir\nConsulte o administrador");

						}

				
			        }
				
			}
		});
		btnInserirParcela_1_1.setForeground(Color.WHITE);
		btnInserirParcela_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInserirParcela_1_1.setBackground(new Color(204, 0, 0));
		
		painelPagamentos.add(btnInserirParcela_1_1, "cell 0 7,alignx right,aligny center");
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnEditar.setBackground(new Color(0, 0, 139));
		
		painelPagamentos.add(btnEditar, "cell 1 7 2 1,aligny center");
		btnInserirParcela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	public TelaCadastroParcela(int modo_operacao, Parcela parcela, int id_lancamento_pai, Window janela_pai) {

				TelaCadastroParcela tela = new TelaCadastroParcela(0, null, lancamento_global.getId_lancamento(), isto);
				tela.setVisible(true);
			}
		});
		btnInserirParcela.setBackground(new Color(0, 0, 51));
		btnInserirParcela.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnInserirParcela.setForeground(Color.WHITE);
		
		painelPagamentos.add(btnInserirParcela, "cell 3 7,alignx right,aligny center");
		panel_13.setBackground(Color.WHITE);
		
		painelPagamentos.add(panel_13, "cell 6 7 2 1,alignx center,growy");
		panel_13.setLayout(new MigLayout("", "[64px][60px][90px]", "[28px]"));
		btnNewButton_3.setBackground(new Color(204, 0, 0));
		btnNewButton_3.setForeground(Color.WHITE);
		btnNewButton_3.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_13.add(btnNewButton_3, "cell 0 0,alignx left,aligny top");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					int rowSel = tabela_pagamentos.getSelectedRow();//pega o indice da linha na tabela
					//TelaFinanceiroCadastroPagamento tela = new TelaFinanceiroCadastroPagamento(1, ,lancamento_global.getId_lancamento(), isto);

					TelaFinanceiroCadastroPagamento tela = new TelaFinanceiroCadastroPagamento(1, modelo_pagamentos.getValue(rowSel), lancamento_global.getId_lancamento(), isto);
					tela.setVisible(true);
			}
		});
		btnNewButton_2.setBackground(new Color(0, 0, 153));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 14));
		
		panel_13.add(btnNewButton_2, "cell 1 0,alignx left,aligny top");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelaFinanceiroCadastroPagamento tela = new TelaFinanceiroCadastroPagamento(-1, null, lancamento_global.getId_lancamento(), isto);
				tela.setVisible(true);
			
			}
		});
		
		panel_13.add(btnNewButton_1, "cell 2 0,growx,aligny top");
		

		boolean chamar = true;
		if(chamar)
		 rotinas();
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
	
	
	public void rotinas() {
		
	  CentroCusto cc = new CentroCusto();
	  InstituicaoBancaria ib = new InstituicaoBancaria();
	  CadastroCliente cliente = new CadastroCliente();
	  FinanceiroGrupoContas fgc = new FinanceiroGrupoContas();
	  FinanceiroConta fc = new FinanceiroConta();
	  
	 
		
		
	  if(lancamento_global.getTipo_lancamento() == 0) {
		  entTipoLancamento.setText("Despesa");
		  setPainelAsPartesDespesa();
	  }else if(lancamento_global.getTipo_lancamento() == 1) {
		  entTipoLancamento.setText("Receita");
		  setPainelAsPartesReceita();

	  }
	  entData.setText(lancamento_global.getData_lancamento());
		int status = lancamento_global.getStatus();
		if(status == 0) {
			entStatus.setText("A Pagar");

		}else if(status == 1) {
			entStatus.setText("Pago");

		}else if(status == 2) {
			entStatus.setText("A Receber");

		}else if(status == 3) {
			entStatus.setText("Recebido");

		}
	
	  
		
		
	  cc = new GerenciarBancoCentroCustos().getCentroCusto(lancamento_global.getId_centro_custo());
	  entCentroCusto.setText(cc.getNome_centro_custo());
	  
	  ib = new GerenciarBancoInstituicaoBancaria().getInstituicaoBancaria(lancamento_global.getId_instituicao_bancaria());
	  entInstituicaoBancaria.setText(ib.getNome_instituicao_bancaria());
	  
	  cliente = new GerenciarBancoClientes().getCliente(lancamento_global.getId_cliente_fornecedor());
	  String nome_cliente = "";
	  if(cliente.getTipo_pessoa() == 0) {
		  nome_cliente = cliente.getNome_empresarial();
	  }else
		  nome_cliente = cliente.getNome_fantaia();
	  entClienteFornecedor.setText(nome_cliente);
	  
	  
	  fc = new GerenciarBancoFinanceiroConta().getFinanceiroConta(lancamento_global.getId_conta());
	  entConta.setText(fc.getNome());
	  
	  fgc = new GerenciarBancoFinanceiroGrupoContas().getFinanceiroGrupoContas(fc.getId_grupo_contas());
	  entGrupoContas.setText(fgc.getNome());
	  
	
	 
	 entValor.setText(NumberFormat.getCurrencyInstance(ptBr).format(lancamento_global.getValor()));
	 entDataVencimento.setText(lancamento_global.getData_vencimento());
	 entNumeroParcelas.setText(lancamento_global.getNumero_parcelas() + "");
	 entIntervalo.setText(lancamento_global.getIntervalo() + "");
	 
	 int prioridade = lancamento_global.getPrioridade();
	 /*cbPrioridade.addItem("Alta Prioridade - Ainda esta semana");
		cbPrioridade.addItem("Média Prioridade - Em menos de 15 dias");
		cbPrioridade.addItem("Prioridade Leve - Ainda este mês");
		cbPrioridade.addItem("Baixa Prioridade - Ainda este ano");
		*/
	 if(prioridade == 0) {
		 entPrioridade.setText("Alta Prioridade - Ainda esta semana");
	 }else if(prioridade == 1) {
		 entPrioridade.setText("Média Prioridade - Em menos de 15 dias");

	 }else if(prioridade == 2) {
		 entPrioridade.setText("Prioridade Leve - Ainda este mês");

	 }else if(prioridade == 3) {
		 entPrioridade.setText("Baixa Prioridade - Ainda este ano");

	 }
	 entObservacaoLancamento.setText(lancamento_global.getObservacao());
	 entDescricaoLancamento.setText(lancamento_global.getDescricao());

	 if(lancamento_global.getCaminho_arquivo() != null) {
		 if(lancamento_global.getCaminho_arquivo().length() > 30) {
			 carregarDocumento(lancamento_global.getCaminho_arquivo());
		 }
	 }
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

		private final String colunas[] = { "ID", "Identificador", "Descrição", "Data Vencimento", "Valor", "Status"};
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
			case descricao:{
				return dado.getDescricao();
			}
			case data_vencimento:{
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
			case valor:{
				Locale ptBr = new Locale("pt", "BR");
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
				return valorString;
				
			}
			case status:{
				int status = dado.getStatus();
				if(status == 0) {
					return ("A Pagar");

				}else if(status == 1) {
					return ("Pago");

				}else if(status == 2) {
					return ("A Receber");

				}else if(status == 3) {
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
					controller.openDocument(servidor_unidade + url);
					// viewerComponentPanel.setPreferredSize(new Dimension(400, 370));
					// viewerComponentPanel.setMaximumSize(new Dimension(400, 370));

					painel_vizualizar.setBounds(0, 0, painel_vizualizar.getWidth(), painel_vizualizar.getHeight());
					painelVizualizarDocumento.add(painel_vizualizar);
				} else {
					controller.openDocument(servidor_unidade + url);
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
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
		lista_parcelas.clear();
		modelo_parcelas.onRemoveAll();
		
		BigDecimal valor_total_parcelas =  BigDecimal.ZERO;
		
		for (Parcela parcela : gerenciar.getParcelasPorLancamento(lancamento_global.getId_lancamento())) {

			modelo_parcelas.onAdd(parcela);
			valor_total_parcelas = valor_total_parcelas.add(parcela.getValor());
			
	      }
		
		lblValorTotalDasParcelas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_parcelas));

	}
	
	public void pesquisar_pagamentos() {
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		lista_pagamentos.clear();
		modelo_pagamentos.onRemoveAll();
		
		BigDecimal valor_total_pagamentos =  BigDecimal.ZERO;
		
		for (FinanceiroPagamento pagamento : gerenciar.getFinanceiroPagamentosPorLancamento(lancamento_global.getId_lancamento())) {

			modelo_pagamentos.onAdd(pagamento);
			valor_total_pagamentos = valor_total_pagamentos.add(pagamento.getValor());
			
	      }
		
		lblValorTotalDosPagamentos.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_pagamentos));
		
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
		lblNewLabel_1_1.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_1_1, "cell 0 2,alignx right");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		entInstituicaoBancaria.setForeground(Color.WHITE);
		panel_3.add(entInstituicaoBancaria, "cell 1 2");
		entInstituicaoBancaria.setFont(new Font("Tahoma", Font.PLAIN, 18));
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
		panel_4.add(lblNewLabel_1_1, "cell 0 2");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		panel_4.add(entInstituicaoBancaria, "cell 1 2");
		entInstituicaoBancaria.setForeground(Color.WHITE);
		entInstituicaoBancaria.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}
	
	public class PagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id_pagamento = 0;
		private final int identificador = 1;
		private final int descricao = 2;
		private final int data_pagamento = 3;
		private final int valor = 4;
		
		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Identificador", "Descrição", "Data Pagamento", "Valor"};
		private final ArrayList<FinanceiroPagamento> dados = new ArrayList<>();// usamos como dados uma lista genérica de
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
			case descricao:
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
			case id_pagamento:
				return dado.getId_pagamento();
			case identificador:
				return dado.getIdentificador();
			case descricao:{
				return dado.getDescricao();
			}
			case data_pagamento:{
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
			case valor:{
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
}
