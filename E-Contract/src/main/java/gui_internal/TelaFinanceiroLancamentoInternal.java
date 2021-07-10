package main.java.gui_internal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import main.java.cadastros.Lancamento;
import main.java.cadastros.Parcela;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.gui.TelaFinanceiroCadastroEmprestimo;
import main.java.gui.TelaFinanceiroCadastroLancamento;
import main.java.gui.TelaFinanceiroCadastroTransferencia;
import main.java.gui.TelaFinanceiroGerenciarLancamento;
import main.java.gui.TelaFinanceiroGerenciarLancamentoMultiplo;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.views_personalizadas.TelaEscolhaRelatorioLancamentos;
import main.java.views_personalizadas.TelaEscolhaRelatorioRomaneios;
import main.java.conexaoBanco.GerenciarBancoLancamento;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaFinanceiroLancamentoInternal extends JInternalFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroLancamentoInternal isto;
	private JTable tabela_lancamentos;
	private ArrayList<Lancamento> lista_lancamentos = new ArrayList<>();
	private LancamentoTableModel modelo_lancamentos = new LancamentoTableModel();
	private JDialog telaPai;
	private JLabel lblValorAReceber, lblValorRestanteAPagar, lblValorRecebido, lblValorReceitas;

	private JLabel lblValorTotalJurosPago, lblValorVencerAPagar;
	private JLabel lblValorPago, lblValorTotalDespesas;
	private TableRowSorter<LancamentoTableModel> sorter;
	private JLabel lblDespesasAPagar, lblDespesasPago, lblTotalDespesas, lblReceitasRecebido, lblTotalReceitas,
	lblReceitasAReceber;
	private JLabel lblValorTotalJurosRecebido, lblValorVencerAReceber;

	public TelaFinanceiroLancamentoInternal(int flag_tipo_tela, Window janela_pai) {

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
		painelPrinciapl.setLayout(new MigLayout("", "[][grow][]", "[][100px][grow][][][]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0 3 1,grow");
		panel.setLayout(new MigLayout("", "[269px][]", "[49px]"));

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		if(flag_tipo_tela == 0) {
			lblNewLabel.setText("Lançamentos de Despesa");
		}else {
			lblNewLabel.setText("Lançamentos de Receita");

		}
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny top");

		LancamentosRender renderer = new LancamentosRender();

		tabela_lancamentos = new JTable(modelo_lancamentos);
		tabela_lancamentos.setDefaultRenderer(Object.class, renderer);

		// define o sorter na tablea
		sorter = new TableRowSorter<LancamentoTableModel>(modelo_lancamentos);
		tabela_lancamentos.setRowSorter(sorter);
		tabela_lancamentos.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_lancamentos);
		painelPrinciapl.add(scrollPane, "cell 0 1 3 2,grow");
		
		JButton btnNewButton = new JButton("Gerar Relatório");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<>();
				int linhas_selecionadas[] = tabela_lancamentos.getSelectedRows();// pega o indice da linha na tabela

				for (int i = 0; i < linhas_selecionadas.length; i++) {

					int indice = linhas_selecionadas[i];//
					int indexRowModel = tabela_lancamentos.getRowSorter().convertRowIndexToModel(indice);

					Lancamento lancamento = lista_lancamentos.get(indexRowModel);
					lancamentos_selecionados.add(lancamento);
				}

				TelaEscolhaRelatorioLancamentos escolha_opcoes = new TelaEscolhaRelatorioLancamentos(
						lancamentos_selecionados, isto);
				escolha_opcoes.setVisible(true);
				
			}
		});
		btnNewButton.setBackground(new Color(0, 0, 51));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		painelPrinciapl.add(btnNewButton, "flowx,cell 1 3,alignx right");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrinciapl.add(panel_5, "cell 0 4 3 1,grow");
		panel_5.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));

		JPanel painelDespesas = new JPanel();
		painelDespesas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		painelDespesas.setBackground(Color.WHITE);
		panel_5.add(painelDespesas, "cell 0 0,grow");
		painelDespesas.setLayout(new MigLayout("", "[62px][109px][8px][91px][51px]", "[16px][20px][20px][][20px][]"));

		JLabel lblNewLabel_1 = new JLabel("Despesas:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
		painelDespesas.add(lblNewLabel_1, "cell 0 0,alignx left,aligny top");

		JLabel lblNewLabel_3_1 = new JLabel("N.º de Despesas:");
		lblNewLabel_3_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblNewLabel_3_1, "cell 1 1,alignx left,aligny bottom");

		lblTotalDespesas = new JLabel("0");
		painelDespesas.add(lblTotalDespesas, "cell 2 1,alignx left,aligny top");
		lblTotalDespesas.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblValorDespesaslbl = new JLabel("Valor Total:");
		lblValorDespesaslbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblValorDespesaslbl, "cell 3 1,alignx right,aligny bottom");

		lblValorTotalDespesas = new JLabel("0");
		lblValorTotalDespesas.setFont(new Font("SansSerif", Font.BOLD, 15));
		painelDespesas.add(lblValorTotalDespesas, "cell 4 1,alignx left,aligny top");

		JLabel lblNewLabel_5_1 = new JLabel("        ");
		painelDespesas.add(lblNewLabel_5_1, "cell 0 2,alignx right,aligny center");
		lblNewLabel_5_1.setOpaque(true);
		lblNewLabel_5_1.setForeground(Color.BLACK);
		lblNewLabel_5_1.setBackground(Color.ORANGE);

		JLabel lblNewLabel_4 = new JLabel("Pago:");
		painelDespesas.add(lblNewLabel_4, "cell 1 2,alignx left,aligny bottom");
		lblNewLabel_4.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblDespesasPago = new JLabel("0");
		painelDespesas.add(lblDespesasPago, "cell 2 2,alignx left,aligny top");
		lblDespesasPago.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblValorPagolbl = new JLabel("Valor Pago:");
		lblValorPagolbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblValorPagolbl, "cell 3 2,alignx right,aligny bottom");

		lblValorPago = new JLabel("0");
		lblValorPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		painelDespesas.add(lblValorPago, "cell 4 2,alignx left,aligny top");

		JLabel lblValorJurosPago = new JLabel("Valor Juros Pago:");
		lblValorJurosPago.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblValorJurosPago, "cell 3 3,alignx right");

		lblValorTotalJurosPago = new JLabel("R$ 0,00");
		lblValorTotalJurosPago.setFont(new Font("SansSerif", Font.BOLD, 15));
		painelDespesas.add(lblValorTotalJurosPago, "cell 4 3");

		JLabel lblNewLabel_5 = new JLabel("        ");
		painelDespesas.add(lblNewLabel_5, "cell 0 4,alignx right,aligny center");
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBackground(Color.RED);
		lblNewLabel_5.setForeground(Color.BLACK);

		JLabel lblNewLabel_3 = new JLabel("A Pagar:");
		painelDespesas.add(lblNewLabel_3, "cell 1 4,alignx left,aligny bottom");
		lblNewLabel_3.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblDespesasAPagar = new JLabel("0");
		painelDespesas.add(lblDespesasAPagar, "cell 2 4,alignx left,aligny top");
		lblDespesasAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblValorAPagar_1_1 = new JLabel("Valor a Pagar:");
		lblValorAPagar_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblValorAPagar_1_1, "cell 3 4,alignx right,aligny bottom");

		lblValorRestanteAPagar = new JLabel("0");
		lblValorRestanteAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		painelDespesas.add(lblValorRestanteAPagar, "cell 4 4,alignx left,aligny top");

		JLabel lblValorAPagar_1_1_1 = new JLabel("Valor a Vencer:");
		lblValorAPagar_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		painelDespesas.add(lblValorAPagar_1_1_1, "cell 3 5,alignx right");

		lblValorVencerAPagar = new JLabel("R$ 0,00");
		lblValorVencerAPagar.setFont(new Font("SansSerif", Font.BOLD, 15));
		painelDespesas.add(lblValorVencerAPagar, "cell 4 5");

	
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_7.setBackground(Color.WHITE);
		panel_5.add(panel_7, "cell 1 0,grow");
		panel_7.setLayout(new MigLayout("", "[52px][81px][24px][107px][51px]", "[][20px][20px][][20px][]"));

		JLabel lblNewLabel_1_2 = new JLabel("Receitas:");
		lblNewLabel_1_2.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		panel_7.add(lblNewLabel_1_2, "cell 0 0,alignx left,aligny center");

		JLabel lblNewLabel_7_1 = new JLabel("N.º Receitas:");
		panel_7.add(lblNewLabel_7_1, "cell 1 1,alignx left,aligny bottom");
		lblNewLabel_7_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblTotalReceitas = new JLabel("0");
		panel_7.add(lblTotalReceitas, "cell 2 1,alignx left,aligny top");
		lblTotalReceitas.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblValorReceitaslbl = new JLabel("Valor Total:");
		lblValorReceitaslbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblValorReceitaslbl, "cell 3 1,alignx right,aligny bottom");

		lblValorReceitas = new JLabel("R$ 0,00");
		lblValorReceitas.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel_7.add(lblValorReceitas, "cell 4 1,alignx left,aligny top");

		JLabel lblNewLabel_5_2_1 = new JLabel("        ");
		panel_7.add(lblNewLabel_5_2_1, "cell 0 2,alignx right,aligny center");
		lblNewLabel_5_2_1.setOpaque(true);
		lblNewLabel_5_2_1.setForeground(Color.BLACK);
		lblNewLabel_5_2_1.setBackground(new Color(0, 51, 0));

		JLabel lblNewLabel_4_1_1 = new JLabel("Recebido:");
		panel_7.add(lblNewLabel_4_1_1, "cell 1 2,alignx left,aligny bottom");
		lblNewLabel_4_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblReceitasRecebido = new JLabel("0");
		panel_7.add(lblReceitasRecebido, "cell 2 2,alignx left,aligny top");
		lblReceitasRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblValorRecebidolbl = new JLabel("Valor Recebido:");
		lblValorRecebidolbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblValorRecebidolbl, "cell 3 2,alignx right,aligny bottom");

		lblValorRecebido = new JLabel("R$ 0,00");
		lblValorRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel_7.add(lblValorRecebido, "cell 4 2,alignx left,aligny top");

		JLabel lblValorJurosRecebido = new JLabel("Valor Juros Recebido:");
		lblValorJurosRecebido.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblValorJurosRecebido, "cell 3 3,alignx right");

		lblValorTotalJurosRecebido = new JLabel("R$ 0,00");
		lblValorTotalJurosRecebido.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel_7.add(lblValorTotalJurosRecebido, "cell 4 3");

		JLabel lblNewLabel_5_2 = new JLabel("        ");
		panel_7.add(lblNewLabel_5_2, "cell 0 4,alignx right,aligny center");
		lblNewLabel_5_2.setOpaque(true);
		lblNewLabel_5_2.setForeground(Color.BLACK);
		lblNewLabel_5_2.setBackground(Color.YELLOW);

		JLabel lblNewLabel_4_1 = new JLabel("A Receber:");
		panel_7.add(lblNewLabel_4_1, "cell 1 4,alignx left,aligny bottom");
		lblNewLabel_4_1.setFont(new Font("SansSerif", Font.PLAIN, 14));

		lblReceitasAReceber = new JLabel("0");
		panel_7.add(lblReceitasAReceber, "cell 2 4,alignx left,aligny top");
		lblReceitasAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblValorAReceberlbl = new JLabel("Valor a Receber:");
		lblValorAReceberlbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblValorAReceberlbl, "cell 3 4,alignx right,aligny bottom");

		lblValorAReceber = new JLabel("R$ 0,00");
		lblValorAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel_7.add(lblValorAReceber, "cell 4 4,alignx left,aligny top");

		JLabel lblValorAPagar_1_1_1_1 = new JLabel("Valor a Vencer:");
		lblValorAPagar_1_1_1_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		panel_7.add(lblValorAPagar_1_1_1_1, "cell 3 5,alignx right");

		lblValorVencerAReceber = new JLabel("R$ 0,00");
		lblValorVencerAReceber.setFont(new Font("SansSerif", Font.BOLD, 15));
		panel_7.add(lblValorVencerAReceber, "cell 4 5");
		
		JButton btnNewButton_1 = new JButton("Abrir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<Lancamento> lancamentos_selecionados = getLancamentosSelecionado();

				if (lancamentos_selecionados.size() == 1) {

					Lancamento lancamento_gerenciar = lancamentos_selecionados.get(0);
					TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar,
							null);
					tela.setVisible(true);

				} else if (lancamentos_selecionados.size() > 1) {
					boolean prosseguir = true;
					for (Lancamento lancamento : lancamentos_selecionados) {
						if (lancamento.getTipo_lancamento() != 0 && lancamento.getTipo_lancamento() != 2) {
							prosseguir = false;
							break;
						}
					}

					if (prosseguir) {
						TelaFinanceiroGerenciarLancamentoMultiplo tela = new TelaFinanceiroGerenciarLancamentoMultiplo(
								lancamentos_selecionados, null);
						tela.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null,
								"Para gerenciar multiplos lançamentos,\ntodos devem ser do tipo 'Despesa' ");

					}
				}

			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 51, 0));
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		painelPrinciapl.add(btnNewButton_1, "cell 1 3 2 1");

	
		
	}

	

	public void pesquisar(int cc, int mes, int ano, int tipo) {
		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		lista_lancamentos.clear();
		modelo_lancamentos.onRemoveAll();

		lista_lancamentos = gerenciar.buscaLancamentosCompletosFiltrados(cc, mes, ano, tipo);
		for (Lancamento la : lista_lancamentos) {
			modelo_lancamentos.onAdd(la);
		}

		calcular();
	}

	public void calcular() {

		int numero_lancamentos = 0;
		int numero_despesas = 0;
		int numero_despesas_a_pagar = 0;
		int numero_despesas_pago = 0;
		int numero_receitas = 0;
		int numero_receitas_a_receber = 0;
		int numero_receitas_recebido = 0;

		// despesas
		BigDecimal valor_total_despesas = BigDecimal.ZERO;
		BigDecimal valor_a_pagar = BigDecimal.ZERO;
		BigDecimal valor_pago = BigDecimal.ZERO;
		BigDecimal valor_total_juros_pago = BigDecimal.ZERO;
		BigDecimal valor_total_vencer_pagar = BigDecimal.ZERO;

		// receitas
		BigDecimal valor_total_receitas = BigDecimal.ZERO;
		BigDecimal valor_a_receber = BigDecimal.ZERO;
		BigDecimal valor_recebido = BigDecimal.ZERO;
		BigDecimal valor_total_juros_recebido = BigDecimal.ZERO;
		BigDecimal valor_total_vencer_receber = BigDecimal.ZERO;

		for (int row = 0; row < tabela_lancamentos.getRowCount(); row++) {

			int index = tabela_lancamentos.convertRowIndexToModel(row);
			Lancamento lancamento = modelo_lancamentos.getValue(index);

			if (lancamento.getStatus() == 0) {
				// despesas a pagar
				valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
				valor_a_pagar = valor_a_pagar.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));

				valor_pago = valor_pago.add(lancamento.getValor_ja_pago());
				numero_despesas_a_pagar++;

				BigDecimal valor_total = lancamento.getValor();
				BigDecimal valor__ja_pago = lancamento.getValor_ja_pago();

				BigDecimal valor_restante = valor__ja_pago.subtract(valor_total);

				if (valor__ja_pago.compareTo(valor_total) > 0)
					valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
				else
					valor_total_vencer_pagar = valor_total_vencer_pagar
							.add(lancamento.getValor_proximo_pagamento_a_vencer());

			} else if (lancamento.getStatus() == 1) {
				// despesas ja paga
				valor_total_despesas = valor_total_despesas.add(lancamento.getValor());
				valor_pago = valor_pago.add(lancamento.getValor());

				BigDecimal valor_total = lancamento.getValor();
				BigDecimal valor__ja_pago = lancamento.getValor_ja_pago();

				BigDecimal valor_restante = valor__ja_pago.subtract(valor_total);

				if (valor__ja_pago.compareTo(valor_total) > 0)
					valor_total_juros_pago = valor_total_juros_pago.add(valor__ja_pago.subtract(valor_total));
				else
					valor_total_vencer_pagar = valor_total_vencer_pagar
							.add(lancamento.getValor_proximo_pagamento_a_vencer());

				numero_despesas_pago++;
			} else if (lancamento.getStatus() == 2) {
				// receitas a receber
				valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
				valor_a_receber = valor_a_receber.add(lancamento.getValor().subtract(lancamento.getValor_ja_pago()));

				valor_recebido = valor_recebido.add(lancamento.getValor_ja_pago());

				BigDecimal valor_total_a_receber = lancamento.getValor();
				BigDecimal valor__ja_recebido = lancamento.getValor_ja_pago();

				BigDecimal valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);

				if (valor__ja_recebido.compareTo(valor_total_a_receber) > 0)
					valor_total_juros_recebido = valor_total_juros_recebido
							.add(valor__ja_recebido.subtract(valor_total_a_receber));
				else
					valor_total_vencer_receber = valor_total_vencer_receber
							.add(lancamento.getValor_proximo_pagamento_a_vencer());

				numero_receitas_a_receber++;
			} else if (lancamento.getStatus() == 3) {
				// receitas recebidas
				valor_total_receitas = valor_total_receitas.add(lancamento.getValor());
				valor_recebido = valor_recebido.add(lancamento.getValor());

				BigDecimal valor_total_a_receber = lancamento.getValor();
				BigDecimal valor__ja_recebido = lancamento.getValor_ja_pago();

				BigDecimal valor_restante = valor__ja_recebido.subtract(valor_total_a_receber);

				if (valor__ja_recebido.compareTo(valor_total_a_receber) > 0)
					valor_total_juros_recebido = valor_total_juros_recebido
							.add(valor__ja_recebido.subtract(valor_total_a_receber));
				else
					valor_total_vencer_receber = valor_total_vencer_receber
							.add(lancamento.getValor_proximo_pagamento_a_vencer());

				numero_receitas_recebido++;
			}

		}

		lblDespesasAPagar.setText(numero_despesas_a_pagar + "");
		lblDespesasPago.setText(numero_despesas_pago + "");
		lblTotalDespesas.setText((numero_despesas_a_pagar + numero_despesas_pago) + "");
		lblReceitasAReceber.setText(numero_receitas_a_receber + "");
		lblReceitasRecebido.setText(numero_receitas_recebido + "");
		lblTotalReceitas.setText((numero_receitas_a_receber + numero_receitas_recebido) + "");

		// valores
		Locale ptBr = new Locale("pt", "BR");
		lblValorRestanteAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_pagar));
		lblValorPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_pago));
		lblValorTotalDespesas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_despesas));
		lblValorTotalJurosPago.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_pago));
		lblValorVencerAPagar.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_pagar));

		// receitas
		lblValorAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_a_receber));
		lblValorRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_recebido));
		lblValorReceitas.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_receitas));
		lblValorTotalJurosRecebido.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_juros_recebido));
		lblValorVencerAReceber.setText(NumberFormat.getCurrencyInstance(ptBr).format(valor_total_vencer_receber));

	}
	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public class LancamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int data = 1;
		private final int tipo = 2;
		private final int centro_custo = 3;
		private final int cliente_fornecedor =4;
		private final int grupo_conta = 5;
		private final int conta = 6;
		private final int valor = 7;
		private final int valor_pago = 8;
		private final int valor_a_pagar = 9;
		private final int status = 10;
		

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Data Lançamento","Tipo", "Centro de Custo",
				 "Cliente/Fornecedor", "Grupo de Contas", "Conta",
				"Valor Total", "Valor Pago/Recebido", "Valor a Pagar/Receber",  "Status" };
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
			case tipo:
				return Date.class;
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
			case valor_pago:
				return String.class;
			case valor_a_pagar:
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
			Lancamento dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getId_lancamento();
			
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
			case tipo: {
				if (dado.getTipo_lancamento() == 0) {
					return "D";
				} else if (dado.getTipo_lancamento() == 1) {
					return "R";

				} else if (dado.getTipo_lancamento() == 2) {
					return "T";

				} else if (dado.getTipo_lancamento() == 3) {
					return "E";

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
			case valor_pago: {
				if(dado.getTipo_lancamento() !=2){
				String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor_ja_pago());
				return valorString;
				}else {
					String valorString = NumberFormat.getCurrencyInstance(ptBr).format(dado.getValor());
					return valorString;
				}
			}
			case valor_a_pagar: {
				if(dado.getTipo_lancamento() != 2) {
				BigDecimal valor_total = dado.getValor();
				BigDecimal valor_pago = dado.getValor_ja_pago();

				if (valor_pago.compareTo(valor_total) >= 0) {
					// esta quitado
					return ("R$ 0.00");
				} else {
					String valorString = NumberFormat.getCurrencyInstance(ptBr)
							.format(dado.getValor().subtract(dado.getValor_ja_pago()));
					return valorString;
				}
				}else {
					return ("R$ 0.00");

				}

			}
			case status: {

				if (dado.getTipo_lancamento() != 2) {
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
				} else {
					return "PAGO";
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

	public ArrayList<Lancamento> getLancamentosSelecionado() {

		ArrayList<Lancamento> lancamentos_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = tabela_lancamentos.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela_lancamentos.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte
																											// pro
																											// indice do
																											// model
			Lancamento lancamento_selecionado = lista_lancamentos.get(indice);
			lancamentos_selecionados.add(lancamento_selecionado);
		}

		return lancamentos_selecionados;
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
			String s_status = (String) table.getValueAt(row, 10);
			int status = -1;
			if (s_status.equalsIgnoreCase("A Pagar")) {
				status = 0;
			} else if (s_status.equalsIgnoreCase("Pago")) {
				status = 1;
			} else if (s_status.equalsIgnoreCase("A Receber")) {
				status = 2;
			} else if (s_status.equalsIgnoreCase("Recebido")) {
				status = 3;
			}
		
			if (isSelected) {
				renderer.setBackground(new Color(139, 69, 19)); // marrom

			} else {
				if (status == 0) {
					// return ("A Pagar");
					renderer.setBackground(Color.red);
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));

				} else if (status == 1) {
					// return ("Pago");
					renderer.setBackground(Color.orange);
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));
				} else if (status == 2) {
					// return ("A Receber");
					renderer.setBackground(Color.yellow);
					renderer.setForeground(Color.black);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));
				} else if (status == 3) {
					// return ("Recebido");
					renderer.setBackground(new Color(0, 51, 0));
					renderer.setForeground(Color.white);
					renderer.setFont(new Font("Tahoma", Font.BOLD, 12));
				}

			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			// return renderer;
		}
	}

	

	public void filtrar(String filtro, String menor_data_lancamento, String maior_data_lancamento) {

		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
		/*
		 * private final int id = 0; private final int data = 1; private final int
		 * tipo_lancamento = 2; private final int prioridade = 3;
		 * 
		 * private final int centro_custo = 4; private final int identificador_geral =
		 * 5; private final int destinatario_nf = 6; private final int
		 * cliente_fornecedor = 7; private final int grupo_conta = 8; private final int
		 * conta = 9; private final int valor = 10; private final int valor_pago = 11;
		 * private final int valor_a_pagar = 12; private final int
		 * valor_proxima_parcela_a_vencer = 13;
		 * 
		 * private final int data_vencimento = 14; private final int data_pagamento =
		 * 15; private final int status = 16; private final int situacao = 17; private
		 * final int condicao_pagamento = 18; private final int
		 * status_condicao_pagamento = 19; private final int status_contador = 20;
		 * 
		 */

		String data_inicial_filtrar_data_lancamento = menor_data_lancamento;
		String data_final_filtrar_data_lancamento = maior_data_lancamento;

		if (checkString(data_inicial_filtrar_data_lancamento) && checkString(data_final_filtrar_data_lancamento)) {
			Date data_menor = null;
			Date data_maior = null;
			try {
				data_menor = new SimpleDateFormat("dd/MM/yyyy").parse(data_inicial_filtrar_data_lancamento);
				data_maior = new SimpleDateFormat("dd/MM/yyyy").parse(data_final_filtrar_data_lancamento);

			} catch (ParseException i) {
				// TODO Auto-generated catch block
				i.printStackTrace();
			}

			Set<RowFilter<Object, Object>> datas = new HashSet<>();
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 1));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 1));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 1));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 1));
			filters.add(RowFilter.orFilter(datas_maior));

		}

		

		filters.add(RowFilter.regexFilter(filtro, 2));
				

		

		sorter.setRowFilter(RowFilter.andFilter(filters));
		calcular();

	}

	
}
