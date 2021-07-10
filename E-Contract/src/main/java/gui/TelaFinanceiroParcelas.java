
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.FinanceiroPagamentoEmprestimoCompleto;
import main.java.cadastros.FinanceiroParcelaCompleto;
import main.java.cadastros.Parcela;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.views_personalizadas.TelaEscolhaRelatorioRomaneios;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaFinanceiroParcelas extends JFrame {
	private JComboBox cbGrupoConta, cbConta;

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroParcelas isto;
	private JTable tabela_parcela;
	private ArrayList<FinanceiroParcelaCompleto> lista_FinanceiroParcelasCompletos = new ArrayList<>();
	private ParcelaTableModel modelo_parcela = new ParcelaTableModel();
	private JDialog telaPai;
	private TableRowSorter<ParcelaTableModel> sorter;
	private JComboBox cbStatus;
	private JComboBox cbTipoFinanceiroParcelaCompleto;
	private JTextField menorDataPagamento;
	private JTextField maiorDataPagamento;
	private JTextField entIdentificadorGeral;
	private JLabel lblNumTotalPagamentos;
	private JLabel entValorTotalPagamentoDespesas, entValorTotalPagamentoReceitas, entBalanco;
	private JComboBox cbCentroCusto;
	private JTextField entClienteFornecedor;

	public TelaFinanceiroParcelas(int flag_modo_operacao, int flag_retorno, Window janela_pai) {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		System.out.println("Screen width = " + d.width);
		System.out.println("Screen height = " + d.height);

		// pega o tamanho da barra de tarefas
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		java.awt.Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int taskBarHeight = scrnSize.height - winSize.height;
		System.out.printf("Altura: %d\n", taskBarHeight);

		isto = this;
		setResizable(true);
		DadosGlobais dados = DadosGlobais.getInstance();

		DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode();

		int display_x = display.getWidth();
		int display_y = display.getHeight();

		setBounds(0, 0, d.width, d.height - taskBarHeight);

		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[][grow][]", "[][100px][grow][][]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0 3 1,grow");
		panel.setLayout(new MigLayout("", "[269px][]", "[49px]"));

		JLabel lblNewLabel = new JLabel("Parcelas");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny top");

		JPanel panel_1 = new JPanel();
		painelPrinciapl.add(panel_1, "cell 0 1 3 1,growx,aligny top");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(
				new MigLayout("", "[116px][119px,grow][140px][441px,grow][][grow][113px][19px,grow][125px][8px][141px]",
						"[28px][33px][33px]"));

		JLabel lblNewLabel_1_1 = new JLabel("Tipo de Lançamento:");
		lblNewLabel_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1, "cell 0 0,alignx left,aligny center");

		cbTipoFinanceiroParcelaCompleto = new JComboBox();
		cbTipoFinanceiroParcelaCompleto.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbTipoFinanceiroParcelaCompleto, "cell 1 0,alignx left,aligny center");
		cbTipoFinanceiroParcelaCompleto.addItem("TODOS");
		cbTipoFinanceiroParcelaCompleto.addItem("DESPESAS");
		cbTipoFinanceiroParcelaCompleto.addItem("RECEITAS");
		cbTipoFinanceiroParcelaCompleto.addItem("EMPRESTIMOS");
		cbTipoFinanceiroParcelaCompleto.addItem("TRANSFERENCIAS");

		JLabel lblNewLabel_1_1_4_2_1 = new JLabel("Status Parcela:");
		lblNewLabel_1_1_4_2_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_4_2_1, "cell 2 0,alignx right,aligny center");

		cbStatus = new JComboBox();
		cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(cbStatus, "cell 3 0,growx,aligny center");
		cbStatus.addItem("TODOS");
		cbStatus.addItem("PAGO");
		cbStatus.addItem("A PAGAR");

		JLabel lblNewLabel_1_1_2_1_1_2 = new JLabel("Identificador Geral:");
		lblNewLabel_1_1_2_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_2_1_1_2, "cell 4 0,alignx right,aligny center");

		entIdentificadorGeral = new JTextField();
		entIdentificadorGeral.setColumns(10);
		panel_1.add(entIdentificadorGeral, "cell 5 0,growx,aligny bottom");

		JLabel lblNewLabel_1_1_4_1_1_1 = new JLabel("Período Vencimento");
		panel_1.add(lblNewLabel_1_1_4_1_1_1, "cell 6 0,alignx left,aligny center");
		lblNewLabel_1_1_4_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));

		JLabel lblNewLabel_8_1_1 = new JLabel("De");
		panel_1.add(lblNewLabel_8_1_1, "cell 7 0,alignx left,aligny center");
		lblNewLabel_8_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));

		menorDataPagamento = new JTextField();
		panel_1.add(menorDataPagamento, "cell 8 0,alignx left,aligny top");
		menorDataPagamento.setText((String) null);
		menorDataPagamento.setColumns(10);
		menorDataPagamento.setText(pegarDataMenos(1));

		JLabel lblNewLabel_6_1_1 = new JLabel("a");
		panel_1.add(lblNewLabel_6_1_1, "cell 9 0,alignx left,aligny center");
		lblNewLabel_6_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));

		maiorDataPagamento = new JTextField();
		panel_1.add(maiorDataPagamento, "cell 10 0,alignx left,aligny top");
		maiorDataPagamento.setText((String) null);
		maiorDataPagamento.setColumns(10);
		maiorDataPagamento.setText(pegarDataMais(1));

		JLabel lblNewLabel_1_1_1 = new JLabel("Centro de Custo:");
		lblNewLabel_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1, "cell 0 1,alignx trailing");

		cbCentroCusto = new JComboBox();
		panel_1.add(cbCentroCusto, "cell 1 1,growx");

		cbCentroCusto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					filtrar();
				} catch (NullPointerException t) {

				}
			}
		});

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Cliente/Fornecedor:");
		lblNewLabel_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1_1, "cell 2 1,alignx trailing");

		entClienteFornecedor = new JTextField();
		panel_1.add(entClienteFornecedor, "cell 3 1,growx");
		entClienteFornecedor.setColumns(10);

		JLabel lblNewLabel_1_1_1_2 = new JLabel("Grupo Contas:");
		lblNewLabel_1_1_1_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1_2, "cell 4 1,alignx trailing");

		cbGrupoConta = new JComboBox();
		panel_1.add(cbGrupoConta, "cell 5 1,growx");

		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Conta:");
		lblNewLabel_1_1_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1_1_1_2_1, "cell 6 1,alignx trailing");

		cbConta = new JComboBox();
		panel_1.add(cbConta, "cell 7 1,growx");

		JButton btnFiltar = new JButton("Filtrar");
		btnFiltar.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(btnFiltar, "cell 8 1,growx,aligny top");
		btnFiltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltar.setBackground(new Color(0, 51, 0));
		btnFiltar.setForeground(Color.WHITE);

		JButton btnNewButton = new JButton("pesquisar");
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(btnNewButton, "cell 10 1,growx,aligny top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 0, 102));

		JButton btnLimparBusca = new JButton("Limpar Busca");
		btnLimparBusca.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_1.add(btnLimparBusca, "cell 8 2,alignx left,aligny top");
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimparBusca.setBackground(new Color(204, 0, 0));
		btnLimparBusca.setForeground(Color.WHITE);
		btnLimparBusca.setHorizontalAlignment(SwingConstants.RIGHT);

		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(RowFilter.regexFilter(""));
				cbTipoFinanceiroParcelaCompleto.setSelectedIndex(0);
				cbGrupoConta.setSelectedIndex(0);
				cbConta.setSelectedIndex(0);
				cbCentroCusto.setSelectedIndex(0);
				entClienteFornecedor.setText("");

				pegarDatas();

				calcular();
			}
		});
		btnLimparCampos.setHorizontalAlignment(SwingConstants.RIGHT);
		btnLimparCampos.setForeground(Color.WHITE);
		btnLimparCampos.setBackground(new Color(255, 51, 0));
		panel_1.add(btnLimparCampos, "cell 10 2,alignx left,aligny top");

		FinanceiroParcelaCompletosRender renderer = new FinanceiroParcelaCompletosRender();

		tabela_parcela = new JTable(modelo_parcela);
		tabela_parcela.setDefaultRenderer(Object.class, renderer);

		// define o sorter na tablea
		sorter = new TableRowSorter<ParcelaTableModel>(modelo_parcela);
		tabela_parcela.setRowSorter(sorter);
		tabela_parcela.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_parcela);
		painelPrinciapl.add(scrollPane, "cell 0 2 3 1,grow");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrinciapl.add(panel_5, "cell 0 3 3 2,grow");
		panel_5.setLayout(
				new MigLayout("", "[189px][39px][189px][][87.00px][][][][][][][][][][][][][][][][][][]", "[][][18px]"));

		JButton btnAbrirLancamento = new JButton("Abrir");
		btnAbrirLancamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Lancamento> lancamentos_selecionados = getLancamentosSelecionado();

				if (lancamentos_selecionados.size() == 1) {
					Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
					TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar,
							isto);
					tela.setVisible(true);
				}

			}
		});

		JLabel lblNewLabel_1_3 = new JLabel("Núm Total Parcelas:");
		lblNewLabel_1_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_3, "cell 0 0,alignx right");

		lblNumTotalPagamentos = new JLabel("");
		lblNumTotalPagamentos.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_5.add(lblNumTotalPagamentos, "cell 1 0");
		btnAbrirLancamento.setForeground(Color.WHITE);
		btnAbrirLancamento.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAbrirLancamento.setBackground(new Color(0, 0, 153));
		panel_5.add(btnAbrirLancamento, "cell 22 1");

		JLabel lblNewLabel_1 = new JLabel("Valor Total Parcelas Despesas:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1, "cell 0 2,grow");

		entValorTotalPagamentoDespesas = new JLabel("R$ 100.000.000,00");
		entValorTotalPagamentoDespesas.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_5.add(entValorTotalPagamentoDespesas, "cell 1 2,alignx center,aligny bottom");

		JLabel lblNewLabel_1_2 = new JLabel("   Valor Total Parcelas Receitas:");
		lblNewLabel_1_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2, "cell 2 2,grow");

		entValorTotalPagamentoReceitas = new JLabel("R$ 100.000.000,00");
		entValorTotalPagamentoReceitas.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_5.add(entValorTotalPagamentoReceitas, "cell 3 2");

		JLabel lblNewLabel_1_2_1 = new JLabel("Balanço:");
		lblNewLabel_1_2_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_2_1, "cell 4 2,alignx right,growy");

		entBalanco = new JLabel("R$ 100.000.000,00");
		entBalanco.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_5.add(entBalanco, "cell 5 2");
		popular_grupo_contas();
		popular_contas();
		popular_centros_custo();
		pesquisar();

		boolean pegar_datas = true;
		if (pegar_datas) {
			pegarDatas();
		}
		calcular();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void limpar() {
		sorter.setRowFilter(RowFilter.regexFilter(""));
		calcular();
	}

	public void pegarDatas() {
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();

		Map<String, String> datas = new HashMap<String, String>();
		datas = gerenciar.pegarDatasPagamento();

		menorDataPagamento.setText(datas.get("menor_data_pagamento"));
		maiorDataPagamento.setText(datas.get("maior_data_pagamento"));
	}

	public void pesquisar() {
		GerenciarBancoParcelas gerenciar = new GerenciarBancoParcelas();
		// GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimos = new
		// GerenciarBancoFinanceiroPagamentoEmprestimo();

		lista_FinanceiroParcelasCompletos.clear();
		modelo_parcela.onRemoveAll();

		lista_FinanceiroParcelasCompletos = gerenciar.getTodasParcelas();
		for (FinanceiroParcelaCompleto cc : lista_FinanceiroParcelasCompletos) {
			modelo_parcela.onAdd(cc);
		}
		/*
		 * for (FinanceiroPagamentoCompleto cc :
		 * gerenciar_emprestimos.getTodosFinanceiroPagamentosEmprestimos()) {
		 * modelo_parcela.onAdd(cc); }
		 */

		calcular();
	}

	public void filtrar() {
		/*
		private final int id = 0;
		private final int tipo_lancamento = 1;
		private final int id_lancamento = 2;
		private final int grupo_contas = 3;
		private final int contas = 4;

		private final int centro_custo = 5;
		private final int cliente_fornecedor = 6;
		private final int identificador_geral = 7;

		private final int data_vencimento = 8;
		private final int valor = 9;
		private final int status = 10;
		 */
		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);

		String data_inicial_filtrar_data_FinanceiroPagamentoCompleto = menorDataPagamento.getText().replace(" ", "");
		String data_final_filtrar_data_FinanceiroPagamentoCompleto = maiorDataPagamento.getText().replace(" ", "");

		if (checkString(data_inicial_filtrar_data_FinanceiroPagamentoCompleto)
				&& checkString(data_final_filtrar_data_FinanceiroPagamentoCompleto)) {
			Date data_menor = null;
			Date data_maior = null;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy")
						.parse(data_inicial_filtrar_data_FinanceiroPagamentoCompleto);
				data_maior = new SimpleDateFormat("dd/MM/yyyy")
						.parse(data_final_filtrar_data_FinanceiroPagamentoCompleto);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}

			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 8));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 8));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 8));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 8));
			filters.add(RowFilter.orFilter(datas_maior));

		}

		try {
			String s_centro_custo = "";
			if (checkString(cbCentroCusto.getSelectedItem().toString())) {

				s_centro_custo = cbCentroCusto.getSelectedItem().toString();
				if (!(s_centro_custo.equalsIgnoreCase("TODOS")))
					filters.add(RowFilter.regexFilter(s_centro_custo, 5));

			}
		} catch (Exception t) {

		}

		if (entClienteFornecedor.getText() != null) {
			String s_cliente_servidor = "";
			if (checkString(entClienteFornecedor.getText())) {
				s_cliente_servidor = entClienteFornecedor.getText().toUpperCase();
				if (!(s_cliente_servidor.equalsIgnoreCase("TODOS")))
					filters.add(RowFilter.regexFilter(s_cliente_servidor, 6));
			}
		}

		if (cbGrupoConta.getSelectedItem().toString() != null) {
			String s_grupo_contas = "";
			if (checkString(cbGrupoConta.getSelectedItem().toString())) {
				s_grupo_contas = cbGrupoConta.getSelectedItem().toString();
				if (!(s_grupo_contas.equalsIgnoreCase("TODOS")))
					filters.add(RowFilter.regexFilter(s_grupo_contas,3));

			}
		}

		if (cbConta.getSelectedItem().toString() != null) {
			String s_contas = "";
			if (checkString(cbConta.getSelectedItem().toString())) {
				s_contas = cbConta.getSelectedItem().toString();
				if (!(s_contas.equalsIgnoreCase("TODOS"))) {
					filters.add(RowFilter.regexFilter(s_contas,4));
				}
			}
		}

		if (cbTipoFinanceiroParcelaCompleto.getSelectedItem().toString() != null) {
			String s_tipo_FinanceiroPagamentoCompleto = "";
			if (checkString(cbTipoFinanceiroParcelaCompleto.getSelectedItem().toString())) {
				s_tipo_FinanceiroPagamentoCompleto = cbTipoFinanceiroParcelaCompleto.getSelectedItem().toString();
				if (!(s_tipo_FinanceiroPagamentoCompleto.equalsIgnoreCase("TODOS"))) {
					filters.add(RowFilter.regexFilter(s_tipo_FinanceiroPagamentoCompleto, 1));
				}
			}
		}

		if (entIdentificadorGeral.getText() != null) {
			String s_id_geral = "";
			if (checkString(entIdentificadorGeral.getText())) {
				s_id_geral = entIdentificadorGeral.getText().toUpperCase();

				filters.add(RowFilter.regexFilter(s_id_geral, 7));
			}
		}

		if (cbStatus.getSelectedItem().toString() != null) {
			String s_status_condicao = "";
			if (checkString(cbStatus.getSelectedItem().toString())) {
				s_status_condicao = cbStatus.getSelectedItem().toString();
				if (!(s_status_condicao.equalsIgnoreCase("TODOS"))) {
					filters.add(RowFilter.regexFilter(s_status_condicao, 10));
				}
			}

		}

		sorter.setRowFilter(RowFilter.andFilter(filters));
		calcular();

	}

	public void calcular() {

		BigDecimal valor_total_despesas = BigDecimal.ZERO;
		BigDecimal valor_total_receitas = BigDecimal.ZERO;
		BigDecimal balanco = BigDecimal.ZERO;
		int num_total_pagamentos = 0;

		for (int row = 0; row < tabela_parcela.getRowCount(); row++) {

			int index = tabela_parcela.convertRowIndexToModel(row);
			FinanceiroParcelaCompleto pag = modelo_parcela.getValue(index);
			num_total_pagamentos++;
			if (pag.getLancamento().getTipo_lancamento() == 0) {
				// despesas a pagar
				valor_total_despesas = valor_total_despesas.add(pag.getFpc().getValor());
			} else if (pag.getLancamento().getTipo_lancamento() == 1) {
				// receita
				valor_total_receitas = valor_total_receitas.add(pag.getFpc().getValor());

			} else if (pag.getLancamento().getTipo_lancamento() == 3) {
				// emprestimo

				valor_total_receitas = valor_total_receitas.add(pag.getFpc().getValor());

			}

		}

		balanco = valor_total_receitas.subtract(valor_total_despesas);

		Locale ptBr = new Locale("pt", "BR");

		entValorTotalPagamentoDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
		entValorTotalPagamentoReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
		entBalanco.setText(NumberFormat.getCurrencyInstance(ptBr).format(balanco));
		lblNumTotalPagamentos.setText(num_total_pagamentos + "");
	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public class ParcelaTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int tipo_lancamento = 1;
		private final int id_lancamento = 2;
		private final int grupo_contas = 3;
		private final int contas = 4;

		private final int centro_custo = 5;
		private final int cliente_fornecedor = 6;
		private final int identificador_geral = 7;

		private final int data_vencimento = 8;
		private final int valor = 9;
		private final int status = 10;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID Parc", "Tipo Lançamento", "ID Lanc", "Grupo de Contas", "Contas",
				"Centro Custo", "Cliente/Fornecedor", "Identificador Geral", "Data Vencimento", "Valor", "Status" };
		private final ArrayList<FinanceiroParcelaCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																						// genérica de
		// nfs
		Locale ptBr = new Locale("pt", "BR");

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
			case id:
				return Integer.class;
			case tipo_lancamento:
				return String.class;
			case id_lancamento:
				return Integer.class;
			case grupo_contas:
				return String.class;
			case contas:
				return String.class;
			case centro_custo:
				return String.class;
			case cliente_fornecedor:
				return String.class;
			case identificador_geral:
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
			FinanceiroParcelaCompleto dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getFpc().getId_parcela();
			case tipo_lancamento: {
				int tipo = dado.getLancamento().getTipo_lancamento();
				if (tipo == 0)
					return "DESPESAS";
				else if (tipo == 1)
					return "RECEITAS";
				else if (tipo == 2)
					return "TRANSFERENCIAS";
				else if (tipo == 3)
					return "EMPRESTIMOS";

			}
			case id_lancamento: {
				return dado.getLancamento().getId_lancamento();
			}
			case grupo_contas:
				return dado.getLancamento().getNome_grupo_contas();
			case contas:
				return dado.getLancamento().getNome_conta();
			case centro_custo:
				return dado.getLancamento().getNome_centro_custo();
			case cliente_fornecedor:
				return dado.getLancamento().getNome_cliente_fornecedor();
			case identificador_geral:
				return dado.getFpc().getIdentificador();

			case data_vencimento: {

				if (dado.getFpc().getData_vencimento() != null
						&& !dado.getFpc().getData_vencimento().equalsIgnoreCase("")) {

					Date data_pag;
					try {
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						data_pag = formato.parse(dado.getFpc().getData_vencimento());
						return data_pag;

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			case valor:
				return NumberFormat.getCurrencyInstance(ptBr).format(dado.getFpc().getValor());

			case status: {
				try {
					String retorno = "";
					int id_status = dado.getFpc().getStatus();
					if (id_status == 0) {
						retorno = ("A PAGAR");
					} else if (id_status == 1) {
						retorno = ("PAGO");

					}

					return retorno;

				} catch (Exception e) {
					return "";
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
			FinanceiroParcelaCompleto ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FinanceiroParcelaCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FinanceiroParcelaCompleto dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FinanceiroParcelaCompleto dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FinanceiroParcelaCompleto> dadosIn) {
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
		public void onRemove(FinanceiroParcelaCompleto dado) {
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

		public FinanceiroParcelaCompleto onGet(int row) {
			return dados.get(row);
		}
	}

	public ArrayList<FinanceiroParcelaCompleto> getFinanceiroParecelaCompletosSelecionado() {

		ArrayList<FinanceiroParcelaCompleto> FinanceiroPagamentoCompletos_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = tabela_parcela.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela_parcela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																										// indice do
																										// model
			FinanceiroParcelaCompleto FinanceiroPagamentoCompleto_selecionado = lista_FinanceiroParcelasCompletos
					.get(indice);
			FinanceiroPagamentoCompletos_selecionados.add(FinanceiroPagamentoCompleto_selecionado);
		}

		return FinanceiroPagamentoCompletos_selecionados;
	}

	public void setTelaPai(JDialog _telaPai) {
		this.telaPai = _telaPai;
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

	class FinanceiroParcelaCompletosRender extends DefaultTableCellRenderer {

		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			((JLabel) renderer).setOpaque(true);
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			if (value instanceof Date) {
				value = f.format(value);
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			// return renderer;
		}
	}

	public String pegarDataHoje() {

		LocalDate hoje = LocalDate.now();
		String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return df.replace(" ", "");
	}

	public String pegarDataMais(int anos) {

		LocalDate hoje = LocalDate.now().plusYears(1);
		String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return df.replace(" ", "");
	}

	public String pegarDataMenos(int anos) {

		LocalDate hoje = LocalDate.now().minusYears(1);
		String df = hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return df.replace(" ", "");
	}

	public ArrayList<Lancamento> getLancamentosSelecionado() {

		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = tabela_parcela.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela_parcela.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																										// indice do
																										// model

			Lancamento lancamento_selecionado = lista_FinanceiroParcelasCompletos.get(indice).getLancamento();
			lancamentos_selecionados.add(lancamento_selecionado);
		}

		return lancamentos_selecionados;
	}

	public void popular_grupo_contas() {
		ArrayList<FinanceiroGrupoContas> lista_grupo_contas = new GerenciarBancoFinanceiroGrupoContas()
				.getFinanceiroGrupoContass();
		cbGrupoConta.removeAllItems();
		cbGrupoConta.addItem("TODOS");
		for (FinanceiroGrupoContas grupo_contas : lista_grupo_contas) {
			cbGrupoConta.addItem(grupo_contas.getNome());
		}

	}

	public void popular_centros_custo() {
		ArrayList<CentroCusto> lista_centro_custos = new GerenciarBancoCentroCustos().getCentroCustos();
		cbCentroCusto.removeAllItems();
		cbCentroCusto.addItem("TODOS");
		for (CentroCusto cc : lista_centro_custos) {
			cbCentroCusto.addItem(cc.getNome_centro_custo());
		}

	}

	public void popular_contas() {
		ArrayList<FinanceiroConta> lista_contas = new GerenciarBancoFinanceiroConta().getFinanceiroContas();
		cbConta.removeAllItems();
		cbConta.addItem("TODOS");
		for (FinanceiroConta contas : lista_contas) {
			cbConta.addItem(contas.getNome());
		}

	}

}
