
package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
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
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroRomaneio;
import main.java.cadastros.CentroCusto;
import main.java.cadastros.CondicaoPagamento;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroRecibo;
import main.java.cadastros.FinanceiroPagamentoEmprestimoCompleto;
import main.java.cadastros.Parcela;
import main.java.cadastros.FinanceiroGrupoContas;
import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.InstituicaoBancaria;
import main.java.cadastros.Lancamento;
import main.java.cadastros.PagamentoCompleto;
import main.java.cadastros.CadastroRecibo;
import main.java.cadastros.CadastroRecibo;
import main.java.conexaoBanco.GerenciarBancoCentroCustos;
import main.java.conexaoBanco.GerenciarBancoClientes;
import main.java.conexaoBanco.GerenciarBancoCondicaoPagamentos;
import main.java.conexaoBanco.GerenciarBancoFinanceiroConta;
import main.java.conexaoBanco.GerenciarBancoParcelas;
import main.java.conexaoBanco.GerenciarBancoRecibos;
import main.java.gui.TelaContratos.EvenOddRenderer;
import main.java.manipular.ConfiguracoesGlobais;
import main.java.conexaoBanco.GerenciarBancoFinanceiroGrupoContas;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamento;
import main.java.conexaoBanco.GerenciarBancoFinanceiroPagamentoEmprestimo;
import main.java.conexaoBanco.GerenciarBancoInstituicaoBancaria;
import main.java.conexaoBanco.GerenciarBancoLancamento;
import main.java.outros.DadosGlobais;
import main.java.outros.JTextFieldPersonalizado;
import main.java.tratamento_proprio.Log;
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
import java.io.File;
import java.io.IOException;
import java.awt.event.ItemEvent;

public class TelaFinanceiroRecibos extends JFrame {

	private final JPanel painelPrinciapl = new JPanel();
	private TelaFinanceiroRecibos isto;
	private JTable tabela_recibos;
	private ArrayList<CadastroRecibo> lista_CadastroRecibos = new ArrayList<>();
	private ReciboTableModel modelo_recibos = new ReciboTableModel();
	private JDialog telaPai;
	private TableRowSorter<ReciboTableModel> sorter;
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;

	public TelaFinanceiroRecibos( Window janela_pai) {

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
		panel.setBackground(new Color(0, 0, 102));
		painelPrinciapl.add(panel, "cell 0 0 3 1,grow");
		panel.setLayout(new MigLayout("", "[269px][]", "[49px]"));

		JLabel lblNewLabel = new JLabel("Recibos");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		panel.add(lblNewLabel, "cell 0 0,alignx left,aligny top");

		CadastroRecibosRender renderer = new CadastroRecibosRender();

		tabela_recibos = new JTable(modelo_recibos);
		tabela_recibos.setDefaultRenderer(Object.class, renderer);

		// define o sorter na tablea
		sorter = new TableRowSorter<ReciboTableModel>(modelo_recibos);
		tabela_recibos.setRowSorter(sorter);
		tabela_recibos.setRowHeight(30);

		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem jMenuItemVizualizar = new JMenuItem();

		jMenuItemVizualizar.setText("Vizualizar");

		jMenuItemVizualizar.addActionListener(new java.awt.event.ActionListener() {
			// Importe a classe java.awt.event.ActionEvent
			public void actionPerformed(ActionEvent e) {

				int indiceDaLinha = tabela_recibos.getSelectedRow();

				int indice = tabela_recibos.getRowSorter().convertRowIndexToModel(indiceDaLinha);

				CadastroRecibo rec = (CadastroRecibo) modelo_recibos.getValue(indice);

				String nome_pasta = "lancamento_" + rec.getId_lancamento_pai();
				String nome_arquivo = rec.getNome_arquivo();

				String unidade_base_dados = configs_globais.getServidorUnidade();
				String caminho_completo = unidade_base_dados + "\\" + "E-Contract\\arquivos\\financas\\lancamentos\\"
						+ nome_pasta + "\\documentos\\" + nome_arquivo;

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

		jPopupMenu.add(jMenuItemVizualizar);

		tabela_recibos.setComponentPopupMenu(jPopupMenu);

		JScrollPane scrollPane = new JScrollPane(tabela_recibos);
		painelPrinciapl.add(scrollPane, "cell 0 1 3 2,grow");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		painelPrinciapl.add(panel_5, "cell 0 3 3 2,alignx right,growy");

		JButton btnAbrirLancamento = new JButton("Abrir");
		btnAbrirLancamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Lancamento lancamento_gerenciar = new GerenciarBancoLancamento().getLancamento(getIdLancamento());
				TelaFinanceiroGerenciarLancamento tela = new TelaFinanceiroGerenciarLancamento(lancamento_gerenciar,
						isto);
				tela.setVisible(true);

			}
		});
		panel_5.setLayout(new MigLayout("", "[65px]", "[33px]"));
		btnAbrirLancamento.setForeground(Color.WHITE);
		btnAbrirLancamento.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAbrirLancamento.setBackground(new Color(0, 0, 153));
		panel_5.add(btnAbrirLancamento, "cell 0 0,alignx left,aligny top");

		pesquisar();

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(janela_pai);

	}

	public void pesquisar() {
		GerenciarBancoRecibos gerenciar = new GerenciarBancoRecibos();

		lista_CadastroRecibos.clear();
		modelo_recibos.onRemoveAll();

		lista_CadastroRecibos = gerenciar.getRecibos();
		for (CadastroRecibo cc : lista_CadastroRecibos) {
			modelo_recibos.onAdd(cc);
		}

	}

	public boolean checkString(String txt) {
		return txt != null && !txt.equals("") && !txt.equals(" ") && !txt.equals("  ");
	}

	public class ReciboTableModel extends AbstractTableModel {

		// constantes p/identificar colunas
		private final int id = 0;
		private final int tipo = 1;
		private final int data = 2;
		private final int status = 3;
		private final int lancamento = 4;

		List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

		private final String colunas[] = { "ID", "Tipo", "Data", "Status", "Lançamento" };
		private final ArrayList<CadastroRecibo> dados = new ArrayList<>();
		Locale ptBr = new Locale("pt", "BR");

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
			case id:
				return Integer.class;
			case tipo:
				return String.class;
			case data:
				return String.class;
			case status:
				return String.class;
			case lancamento:
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
			CadastroRecibo dado = dados.get(rowIndex);

			switch (columnIndex) {
			case id:
				return dado.getId_recibo();
			case tipo:{
				int tp = dado.getTipo_recibo();
				if(tp == 1) {
					return "Recibo de Pagamento";
				}else if(tp == 2) {
					return "Contrato de Empréstimo";
				}
			}
			case data:
				return dado.getData_recibo();
			case status: {
				int stu = dado.getStatus_recibo();
				if (stu == 0)
					return "ASSINAR";
				else if (stu == 1)
					return "ASSINADO";
			}
			case lancamento:
				return dado.getId_lancamento_pai();

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

	public ArrayList<CadastroRecibo> getCadastroRecibosSelecionado() {

		ArrayList<CadastroRecibo> CadastroRecibos_selecionados = new ArrayList<>();
		int linhas_selecionadas[] = tabela_recibos.getSelectedRows();// pega o indice da linha na tabela

		for (int i = 0; i < linhas_selecionadas.length; i++) {

			int indice = tabela_recibos.getRowSorter().convertRowIndexToModel(linhas_selecionadas[i]);// converte pro
																										// indice do
																										// model
			CadastroRecibo CadastroRecibo_selecionado = lista_CadastroRecibos.get(indice);
			CadastroRecibos_selecionados.add(CadastroRecibo_selecionado);
		}

		return CadastroRecibos_selecionados;
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

	class CadastroRecibosRender extends DefaultTableCellRenderer {

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

	public int getIdLancamento() {

		GerenciarBancoLancamento gerenciar = new GerenciarBancoLancamento();
		int linhas_selecionada = tabela_recibos.getSelectedRow();// pega o indice da linha na tabela

		int indice = tabela_recibos.getRowSorter().convertRowIndexToModel(linhas_selecionada);// converte pro
																								// indice do
																								// model

		return lista_CadastroRecibos.get(indice).getId_lancamento_pai();

	}

	public void getDadosGlobais() {
		// gerenciador de log
		DadosGlobais dados = DadosGlobais.getInstance();
		GerenciadorLog = dados.getGerenciadorLog();
		configs_globais = dados.getConfigs_globais();
		// usuario logado
		login = dados.getLogin();

	}
}
