
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
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.FinanceiroPagamentoEmprestimoCompleto;
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

public class TelaFinanceiroPagamentoInternal extends JInternalFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroPagamentoInternal isto;
	private JTable tabela_pagamento;
	private ArrayList<FinanceiroPagamentoCompleto> lista_FinanceiroPagamentoCompletos = new ArrayList<>();
	private PagamentoTableModel modelo_pagamento = new PagamentoTableModel();
	private JDialog telaPai;
	private TableRowSorter<PagamentoTableModel> sorter;
	private 	JLabel lblNumTotalPagamentos ;
	private JLabel entValorTotalPagamentoDespesas, entValorTotalPagamentoReceitas, entBalanco;

	public TelaFinanceiroPagamentoInternal(int flag_tipo_tela, Window janela_pai) {

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

		setBounds(0, 0, 924, 728);

		painelPrinciapl.setBackground(Color.WHITE);
		this.setContentPane(painelPrinciapl);
		painelPrinciapl.setLayout(new MigLayout("", "[][grow][]", "[][100px][grow][][]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 255));
		painelPrinciapl.add(panel, "cell 0 0 3 1,grow");
		panel.setLayout(new MigLayout("", "[269px][]", "[49px]"));

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		if(flag_tipo_tela == 1) {
			lblNewLabel.setText("Pagamentos de Receita");
		}else {
			lblNewLabel.setText("Pagamentos de Despesa");

		}
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny top");

		FinanceiroPagamentoCompletosRender renderer = new FinanceiroPagamentoCompletosRender();

		tabela_pagamento = new JTable(modelo_pagamento);
		tabela_pagamento.setDefaultRenderer(Object.class, renderer);

		
		tabela_pagamento.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabela_pagamento.getColumnModel().getColumn(1).setPreferredWidth(40);
	
		// define o sorter na tablea
		sorter = new TableRowSorter<PagamentoTableModel>(modelo_pagamento);
		tabela_pagamento.setRowSorter(sorter);
		tabela_pagamento.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane(tabela_pagamento);
		painelPrinciapl.add(scrollPane, "cell 0 1 3 2,grow");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrinciapl.add(panel_5, "cell 0 3 3 2,grow");
		panel_5.setLayout(
				new MigLayout("", "[189px][39px][189px][][87.00px][][][][][][][][][][][][][][][][][][]", "[][][18px]"));
		
		JLabel lblNewLabel_1_3 = new JLabel("Núm Total Pagamentos:");
		lblNewLabel_1_3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1_3, "cell 0 0,alignx right");
		
		 lblNumTotalPagamentos = new JLabel("");
		lblNumTotalPagamentos.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_5.add(lblNumTotalPagamentos, "cell 1 0");

		JLabel lblNewLabel_1 = new JLabel("Valor Total Pagamento Despesas:");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		panel_5.add(lblNewLabel_1, "cell 0 2,grow");

		entValorTotalPagamentoDespesas = new JLabel("R$ 100.000.000,00");
		entValorTotalPagamentoDespesas.setFont(new Font("SansSerif", Font.BOLD, 18));
		panel_5.add(entValorTotalPagamentoDespesas, "cell 1 2,alignx center,aligny bottom");

		JLabel lblNewLabel_1_2 = new JLabel("   Valor Total Pagamento Receitas:");
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


	

		
	}

	
	public void filtrar(String filtro, String menorDataPagamento, String  maiorDataPagamento) {

		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
		/*
		 * private final int id = 0; private final int tipo_FinanceiroPagamentoCompleto
		 * = 1; private final int identificador_geral = 2; private final int pagador =
		 * 3; private final int recebedor = 4;
		 * 
		 * private final int data_pagamento = 5; private final int valor =6; private
		 * final int condicao_pagamento = 7; private final int status_condicao_pagamento
		 * =8;
		 * 
		 */

		String data_inicial_filtrar_data_FinanceiroPagamentoCompleto = menorDataPagamento;
		String data_final_filtrar_data_FinanceiroPagamentoCompleto = maiorDataPagamento;

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
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, data_menor, 4));
			datas.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_menor, 4));
			filters.add(RowFilter.orFilter(datas));

			// filters.add( RowFilter.dateFilter(ComparisonType.AFTER, data_menor, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_menor, 5) );

			// filters.add( RowFilter.dateFilter(ComparisonType.BEFORE, data_maior, 5) );
			// filters.add( RowFilter.dateFilter(ComparisonType.EQUAL, data_maior, 5) );
			Set<RowFilter<Object, Object>> datas_maior = new HashSet<>();
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, data_maior, 4));
			datas_maior.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, data_maior, 4));
			filters.add(RowFilter.orFilter(datas_maior));

		}

		filters.add(RowFilter.regexFilter(filtro, 1));
			

		
		sorter.setRowFilter(RowFilter.andFilter(filters));
		calcular();

	}
	
	public void pesquisar(int cc, int mes, int ano, int tipo) {
		GerenciarBancoFinanceiroPagamento gerenciar = new GerenciarBancoFinanceiroPagamento();
		GerenciarBancoFinanceiroPagamentoEmprestimo gerenciar_emprestimos = new GerenciarBancoFinanceiroPagamentoEmprestimo();

		
		lista_FinanceiroPagamentoCompletos.clear();
		modelo_pagamento.onRemoveAll();

		lista_FinanceiroPagamentoCompletos = gerenciar.getTodosFinanceiroPagamentosFiltrados(cc, mes, ano, tipo);
		for (FinanceiroPagamentoCompleto pa : lista_FinanceiroPagamentoCompletos) {
			modelo_pagamento.onAdd(pa);
		}
		
		for (FinanceiroPagamentoCompleto pe : gerenciar_emprestimos.getTodosFinanceiroPagamentosEmprestimosFiltratos(cc, mes, ano, tipo)) {
			modelo_pagamento.onAdd(pe);
		}

		calcular();
	}

	public void calcular() {

		BigDecimal valor_total_despesas = BigDecimal.ZERO;
		BigDecimal valor_total_receitas = BigDecimal.ZERO;
		BigDecimal balanco = BigDecimal.ZERO;
		int num_total_pagamentos = 0;

		for (int row = 0; row < tabela_pagamento.getRowCount(); row++) {

			int index = tabela_pagamento.convertRowIndexToModel(row);
			FinanceiroPagamentoCompleto pag = modelo_pagamento.getValue(index);
			num_total_pagamentos++;
			if (pag.getLancamento().getTipo_lancamento() == 0) {
				// despesas a pagar
				valor_total_despesas = valor_total_despesas.add(pag.getFpag().getValor());
			} else if (pag.getLancamento().getTipo_lancamento() == 1) {
				// receita
				valor_total_receitas = valor_total_receitas.add(pag.getFpag().getValor());

			} else if (pag.getLancamento().getTipo_lancamento() == 3) {
				// emprestimo
				if(pag.getFpag().getTipo_pagamento() == 1) {
					valor_total_despesas = valor_total_despesas.add(pag.getFpag().getValor());

				}else {
				valor_total_receitas = valor_total_receitas.add(pag.getFpag().getValor());
				}
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

	public class PagamentoTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int tipo_lancamento = 1;
		private final int pagador = 2;
		private final int recebedor = 3;

		private final int data_pagamento = 4;
		private final int valor = 5;
		private final int condicao_pagamento = 6;
		private final int status_condicao_pagamento = 7;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Tipo Lançamento", "Pagador", "Recebedor",
				"Data Pagamento", "Valor", "Condições de Pagamento", "Status Condição de Pagamento" };
		private final ArrayList<FinanceiroPagamentoCompleto> dados = new ArrayList<>();// usamos como dados uma lista
																						// genérica de
		// nfs
		private GerenciarBancoCondicaoPagamentos gerenciar = null;
		private ArrayList<CondicaoPagamento> lista_condicoes = null;
		Locale ptBr = new Locale("pt", "BR");

		public PagamentoTableModel() {

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
			case tipo_lancamento:
				return String.class;
			case pagador:
				return String.class;
			case recebedor:
				return String.class;
			case data_pagamento:
				return Date.class;
			case valor:
				return String.class;
			case condicao_pagamento:
				return String.class;
			case status_condicao_pagamento:
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
			FinanceiroPagamentoCompleto dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getFpag().getId_pagamento();
			case tipo_lancamento: {
				int tipo = dado.getLancamento().getTipo_lancamento();
				if (tipo == 0)
					return "D";
				else if (tipo == 1)
					return "R";
				else if (tipo == 2)
					return "T";
				else if (tipo == 3)
					return "E";

			}
			
			case pagador: {
				try {
					return dado.getNome_pagador().toUpperCase();

				} catch (Exception e) {
					return "";
				}
			}
			case recebedor: {
				try {
					return dado.getNome_recebedor().toUpperCase();

				} catch (Exception e) {
					return "";
				}
			}
			case data_pagamento: {
				
				if(dado.getFpag().getData_pagamento() != null && !dado.getFpag().getData_pagamento().equalsIgnoreCase("") ) {
				
				Date data_pag;
				try {
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					data_pag = formato.parse(dado.getFpag().getData_pagamento());
					return data_pag;

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			case valor:
				return NumberFormat.getCurrencyInstance(ptBr).format(dado.getFpag().getValor());

			case condicao_pagamento: {
				try {
					String s_condicao = "";

					int id_condicao_pagamento = dado.getFpag().getId_condicao_pagamento();
					if (id_condicao_pagamento > 0) {

						CondicaoPagamento condicao = null;
						for (CondicaoPagamento cond : lista_condicoes) {
							if (cond.getId_condicao_pagamento() == id_condicao_pagamento) {
								condicao = cond;
								break;
							}
						}

						if (condicao != null)
							;
						s_condicao += (condicao.getNome_condicao_pagamento());
					}

					return s_condicao;

				} catch (Exception e) {
					return "";
				}

			}
			case status_condicao_pagamento: {
				try {
					String retorno = "";
					int id_status = dado.getFpag().getStatus_pagamento();
					if (id_status == 0) {
						retorno += ("A - Compensar|Realizar|Concluir;");
					} else if (id_status == 1) {
						retorno += ("Compensado|Realizado|Concluído;");

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
			FinanceiroPagamentoCompleto ib = dados.get(rowIndex);

		}

		// Métodos abaixo são para manipulação de dados

		/**
		 * retorna o valor da linha indicada
		 * 
		 * @param rowIndex
		 * @return
		 */
		public FinanceiroPagamentoCompleto getValue(int rowIndex) {
			return dados.get(rowIndex);
		}

		/**
		 * retorna o indice do objeto
		 * 
		 * @param empregado
		 * @return
		 */
		public int indexOf(FinanceiroPagamentoCompleto dado) {
			return dados.indexOf(dado);
		}

		/**
		 * add um empregado á lista
		 * 
		 * @param empregado
		 */
		public void onAdd(FinanceiroPagamentoCompleto dado) {
			dados.add(dado);
			fireTableRowsInserted(indexOf(dado), indexOf(dado));
		}

		/**
		 * add uma lista de empregados
		 * 
		 * @param dadosIn
		 */
		public void onAddAll(ArrayList<FinanceiroPagamentoCompleto> dadosIn) {
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
		public void onRemove(FinanceiroPagamentoCompleto dado) {
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

		public FinanceiroPagamentoCompleto onGet(int row) {
			return dados.get(row);
		}
	}

	public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentoCompletosSelecionado() {

		ArrayList<FinanceiroPagamentoCompleto> FinanceiroPagamentoCompletos_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = tabela_pagamento.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela_pagamento.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																										// indice do
																										// model
			FinanceiroPagamentoCompleto FinanceiroPagamentoCompleto_selecionado = lista_FinanceiroPagamentoCompletos
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

	
	class FinanceiroPagamentoCompletosRender extends DefaultTableCellRenderer {

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
		int linhas_selecionadas[] = tabela_pagamento.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela_pagamento.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																										// indice do
																										// model

			Lancamento lancamento_selecionado = lista_FinanceiroPagamentoCompletos.get(indice).getLancamento();
			lancamentos_selecionados.add(lancamento_selecionado);
		}

		return lancamentos_selecionados;
	}
}
